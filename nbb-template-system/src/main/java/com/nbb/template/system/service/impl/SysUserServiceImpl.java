package com.nbb.template.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.collection.CollUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.yulichang.wrapper.MPJLambdaWrapper;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.dto.RoleAllocatedUserPageDTO;
import com.nbb.template.system.domain.dto.UserPageDTO;
import com.nbb.template.system.domain.entity.SysUserDO;
import com.nbb.template.system.domain.entity.SysUserRoleDO;
import com.nbb.template.system.domain.qo.UserPageQO;
import com.nbb.template.system.framework.mybatis.query.LambdaQueryWrapperX;
import com.nbb.template.system.mapper.SysDeptMapper;
import com.nbb.template.system.mapper.SysUserMapper;
import com.nbb.template.system.mapper.SysUserRoleMapper;
import com.nbb.template.system.service.SysUserService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 胡鹏
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUserDO> implements SysUserService {

    @Resource
    private SysUserMapper userMapper;

    @Resource
    private SysUserRoleMapper userRoleMapper;

    @Resource
    private SysDeptMapper deptMapper;

    @Override
    public SysUserDO selectUserByUserName(String userName) {
        return userMapper.selectOne(SysUserDO::getUserName, userName);
    }

    @Override
    public SysUserDO getUserBasicInfo(Long id) {
        LambdaQueryWrapper<SysUserDO> queryWrapper = new LambdaQueryWrapperX<SysUserDO>()
                .eq(SysUserDO::getId, id)
                .select(SysUserDO::getId, SysUserDO::getUserName, SysUserDO::getNickName, SysUserDO::getAvatar);
        return this.getOne(queryWrapper);
    }

    @Override
    public PageResult<SysUserDO> selectAllocatedList(RoleAllocatedUserPageDTO pageDTO) {
        List<Long> allocatedUserIds = userRoleMapper.listUserIdByRoleId(pageDTO.getRoleId());

        if (CollUtil.isEmpty(allocatedUserIds)) {
            return PageResult.empty();
        }
        LambdaQueryWrapper<SysUserDO> queryWrapper = new LambdaQueryWrapperX<SysUserDO>()
                .likeIfPresent(SysUserDO::getUserName, pageDTO.getUserName())
                .likeIfPresent(SysUserDO::getPhonenumber, pageDTO.getPhonenumber())
                .in(SysUserDO::getId, allocatedUserIds);

        return userMapper.selectPage(pageDTO, queryWrapper);

//        MPJLambdaWrapper<SysUserRoleDO> wrapper = new MPJLambdaWrapper<SysUserRoleDO>()
//                .innerJoin(SysUserDO.class, SysUserDO::getId, SysUserRoleDO::getUserId)
//                .selectAll(SysUserDO.class)
//                .eq(SysUserRoleDO::getRoleId, pageDTO.getRoleId())
//                .likeIfExists(SysUserDO::getUserName, pageDTO.getUserName())
//                .likeIfExists(SysUserDO::getPhonenumber, pageDTO.getPhonenumber())
//                .distinct();
//
//        return userRoleMapper.selectJoinPage(pageDTO, SysUserDO.class, wrapper);
    }

    @Override
    public PageResult<SysUserDO> selectUnallocatedList(RoleAllocatedUserPageDTO pageDTO) {
        List<Long> allocatedUserIds = userRoleMapper.listUserIdByRoleId(pageDTO.getRoleId());

        LambdaQueryWrapperX<SysUserDO> queryWrapper = new LambdaQueryWrapperX<SysUserDO>()
                .notInIfPresent(SysUserDO::getId, allocatedUserIds)
                .likeIfPresent(SysUserDO::getUserName, pageDTO.getUserName())
                .likeIfPresent(SysUserDO::getPhonenumber, pageDTO.getPhonenumber());

        return userMapper.selectPage(pageDTO, queryWrapper);
    }

    @Override
    public PageResult<SysUserDO> selectUserList(UserPageDTO dto) {
        UserPageQO qo = BeanUtil.copyProperties(dto, UserPageQO.class);

        // 查询部门下的所有子孙部门
        if (null != dto.getDeptId()) {
            List<Long> deptIds = deptMapper.getAllChildrenIdByDeptId(dto.getDeptId());
            qo.setDeptIds(deptIds);
        }

        return userMapper.listPageUser(qo);
    }
}
