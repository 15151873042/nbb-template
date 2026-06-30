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
     * 根据用户ID查询角色权限
     *
     * @param userId 用户ID
     * @return 权限列表
     */
    Set<String> listRolePermissionByUserId(Long userId);


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

}
