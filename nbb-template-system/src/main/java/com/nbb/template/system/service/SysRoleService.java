package com.nbb.template.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.dto.RoleAddDTO;
import com.nbb.template.system.domain.dto.RolePageDTO;
import com.nbb.template.system.domain.dto.RoleUpdateDTO;
import com.nbb.template.system.domain.entity.SysRoleDO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

/**
 * @author 胡鹏
 */
public interface SysRoleService extends IService<SysRoleDO> {


    /**
     * 通过角色id查询对应的菜单id
     * @param id 角色id
     * @return
     */
    Set<Long> listMenuIdById(Long id);

    /**
     * 通过角色id查询对应的菜单id
     * @param roleIds 角色Id列表
     * @return
     */
    Set<Long> listMenuIdByIds(Set<Long> roleIds);

    PageResult<SysRoleDO> listPageRole(RolePageDTO dto);

    /**
     * 新增保存角色信息
     *
     * @param roleAddDTO 角色信息
     */
    @Transactional
    void addRole(RoleAddDTO roleAddDTO);

    boolean isRoleNameUnique(String roleName, Long excludeId);

    boolean isRoleKeyUnique(String roleName, Long excludeId);


    /**
     * 修改保存角色信息
     *
     * @param roleUpdateDTO 角色信息
     */
    @Transactional
    void updateRole(RoleUpdateDTO roleUpdateDTO);

    /**
     * 批量删除角色信息
     *
     * @param roleIds 需要删除的角色ID
     * @return 结果
     */
    @Transactional
    void deleteByRoleIds(List<Long> roleIds);

    /**
     * 清除角色所关联的菜单缓存信息
     * @param roleIds 角色id列表
     */
    void cacheEvictMenuInfoByRoleIds(List<Long> roleIds);
}
