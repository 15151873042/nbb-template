package com.nbb.template.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.lock.LockInfo;
import com.baomidou.lock.LockTemplate;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nbb.template.system.core.constant.CoreCacheConstants;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.core.exception.ServiceException;
import com.nbb.template.system.core.utils.ServiceExceptionUtil;
import com.nbb.template.system.core.utils.StrUtil;
import com.nbb.template.system.domain.dto.RoleAddDTO;
import com.nbb.template.system.domain.dto.RolePageDTO;
import com.nbb.template.system.domain.dto.RoleUpdateDTO;
import com.nbb.template.system.domain.entity.SysRoleDO;
import com.nbb.template.system.domain.entity.SysRoleMenuDO;
import com.nbb.template.system.domain.entity.SysUserRoleDO;
import com.nbb.template.system.framework.mybatis.query.LambdaQueryWrapperX;
import com.nbb.template.system.mapper.SysRoleMapper;
import com.nbb.template.system.mapper.SysRoleMenuMapper;
import com.nbb.template.system.mapper.SysUserRoleMapper;
import com.nbb.template.system.service.SysRoleService;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import javax.annotation.Resource;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.nbb.template.system.core.enums.CoreErrorCodeConstants.FAIL_ACQUIRE_LOCK;

/**
 * @author 胡鹏
 */
@Service
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRoleDO> implements SysRoleService {


    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Resource
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Resource
    private CacheManager cacheManager;


    @Resource
    private LockTemplate lockTemplate;
    @Resource
    private SysRoleMapper sysRoleMapper;

    @Override
    public Set<String> listRolePermissionByUserId(Long userId) {
        MPJLambdaWrapper<SysUserRoleDO> wrapper = new MPJLambdaWrapper<SysUserRoleDO>()
                .innerJoin(SysRoleDO.class, SysRoleDO::getId, SysUserRoleDO::getRoleId)
                .select(SysRoleDO::getRoleKey)
                .eq(SysUserRoleDO::getUserId, userId)
                .eq(SysRoleDO::getStatus, "0")
                .distinct();

        List<SysRoleDO> roleList = userRoleMapper.selectJoinList(SysRoleDO.class, wrapper);
        return roleList.stream().map(SysRoleDO::getRoleKey).collect(Collectors.toSet());
    }



    @Override
    public PageResult<SysRoleDO> listPageRole(RolePageDTO dto) {
        return getBaseMapper().listPageRole(dto);
    }

    @Override
    public void addRole(RoleAddDTO roleAddDTO) {
        this.checkRoleUnique(() -> this.doAddRole(roleAddDTO),
                roleAddDTO.getRoleName(),
                roleAddDTO.getRoleKey(),
                null);
    }

    private void doAddRole(RoleAddDTO roleAddDTO) {
        // 角色表新增数据
        SysRoleDO sysRoleDO = BeanUtil.copyProperties(roleAddDTO, SysRoleDO.class);
        this.getBaseMapper().insert(sysRoleDO);

        if (CollUtil.isEmpty(roleAddDTO.getMenuIds())) {
            return;
        }

        // 角色菜单中间表新增数据
        List<SysRoleMenuDO> roleMenuDOList = roleAddDTO.getMenuIds().stream()
                .map(menuId -> new SysRoleMenuDO(sysRoleDO.getId(), menuId))
                .collect(Collectors.toList());
        sysRoleMenuMapper.insert(roleMenuDOList);

    }

    @Override
    public boolean isRoleNameUnique(String roleName, Long excludeId) {
        LambdaQueryWrapperX<SysRoleDO> queryWrapper = new LambdaQueryWrapperX<SysRoleDO>()
                .eq(SysRoleDO::getRoleName, roleName)
                .neIfPresent(SysRoleDO::getId, excludeId);

        Long count = getBaseMapper().selectCount(queryWrapper);
        return count == 0;
    }

    @Override
    public boolean isRoleKeyUnique(String roleKey, Long excludeId) {
        LambdaQueryWrapperX<SysRoleDO> queryWrapper = new LambdaQueryWrapperX<SysRoleDO>()
                .eq(SysRoleDO::getRoleKey, roleKey)
                .neIfPresent(SysRoleDO::getId, excludeId);

        Long count = getBaseMapper().selectCount(queryWrapper);
        return count == 0;
    }

    @Override
    @CacheEvict(cacheNames = {
            CoreCacheConstants.ROLE_MENU_ID_KEY,
            CoreCacheConstants.ROLE_MENU_PERMS_KEY},
            key = "#roleUpdateDTO.id")
    public void updateRole(RoleUpdateDTO roleUpdateDTO) {
        this.checkRoleUnique(() -> this.doUpdateRole(roleUpdateDTO),
                roleUpdateDTO.getRoleName(),
                roleUpdateDTO.getRoleKey(),
                roleUpdateDTO.getId());
    }

    @Override
    public void deleteByRoleIds(List<Long> roleIds) {
        // 校验角色是否还关联用户，如果关联用户则不可删除
        // FIXME 此处无需加锁校验，总不能每次保存数据的时候，都加锁判断关联数据是否存在
        // FIXME 数据库查询操作严禁在parallelStream中操作，会造成数据库连接池耗尽
        for (Long roleId : roleIds) {
            int userCount = userRoleMapper.countUserByRoleId(roleId);
            if (userCount > 0) {
                SysRoleDO sysRoleDO = super.getById(roleId);
                throw ServiceExceptionUtil.linkedDataException(StrUtil.format("角色【{}】任然关联用户，不能删除", sysRoleDO.getRoleName()));
            }
        }

        // 删除角色与菜单关联
        sysRoleMenuMapper.deleteByRoleIds(roleIds);
        // 批量删除角色
        sysRoleMapper.deleteByIds(roleIds);
    }

    private void doUpdateRole(RoleUpdateDTO roleUpdateDTO) {
        // 角色表新增数据
        SysRoleDO sysRoleDO = BeanUtil.copyProperties(roleUpdateDTO, SysRoleDO.class);
        this.getBaseMapper().updateById(sysRoleDO);

        // 删除原有角色菜单中间表数据
        sysRoleMenuMapper.deleteByRoleId(roleUpdateDTO.getId());

        if (CollUtil.isEmpty(roleUpdateDTO.getMenuIds())) {
            return;
        }

        // 角色菜单中间表新增数据
        List<SysRoleMenuDO> roleMenuDOList = roleUpdateDTO.getMenuIds().stream()
                .map(menuId -> new SysRoleMenuDO(sysRoleDO.getId(), menuId))
                .collect(Collectors.toList());
        sysRoleMenuMapper.insert(roleMenuDOList);

    }

    private void checkRoleUnique(Runnable runnable,
                                 String roleName,
                                 String roleKey,
                                 Long excludeRoleId) {
        String nameLockKey = "role_name_unique:" + roleName;
        String keyLockKey = "role_key_unique:" + roleKey;

        LockInfo nameLock = lockTemplate.lock(nameLockKey);
        if (nameLock == null) {
            throw new ServiceException(FAIL_ACQUIRE_LOCK.getCode(), "角色名称正在占用，请稍后重试");
        }

        try {
            LockInfo keyLock = lockTemplate.lock(keyLockKey);
            if (keyLock == null) {
                throw new ServiceException(FAIL_ACQUIRE_LOCK.getCode(), "角色权限标识正在占用，请稍后重试");
            }
            try {
                // 判断角色名称是否存在
                boolean roleNameUnique = this.isRoleNameUnique(roleName, excludeRoleId);
                if (!roleNameUnique) {
                    throw ServiceExceptionUtil.dataExistException("角色名称已存在");
                }
                // 判断角色权限标识是否存在
                boolean roleKeyUnique = this.isRoleKeyUnique(roleKey, excludeRoleId);
                if (!roleKeyUnique) {
                    throw ServiceExceptionUtil.dataExistException("角色权限标识已存在");
                }

                // 执行业务代码
                runnable.run();

            } finally {
                TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                    @Override
                    public void afterCompletion(int status) {
                        lockTemplate.releaseLock(keyLock);
                    }
                });
            }
        } finally {
            TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
                @Override
                public void afterCompletion(int status) {
                    lockTemplate.releaseLock(nameLock);
                }
            });

        }
    }

}
