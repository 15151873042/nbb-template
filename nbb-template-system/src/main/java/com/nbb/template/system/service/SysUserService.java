package com.nbb.template.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.dto.RoleAllocatedUserPageDTO;
import com.nbb.template.system.domain.entity.SysConfigDO;
import com.nbb.template.system.domain.entity.SysUserDO;
import com.nbb.template.system.mapper.SysUserMapper;

import java.util.Set;

/**
 * @author 胡鹏
 */
public interface SysUserService extends IService<SysUserDO> {

    /**
     * 通过用户名查询用户
     *
     * @param userName 用户名
     * @return 用户对象信息
     */
    SysUserDO selectUserByUserName(String userName);


    /**
     * 获取用户基础信息
     * @param id
     * @return
     */
    SysUserDO getUserBasicInfo(Long id);

    /**
     * 根据条件分页查询角色关联的用户列表
     *
     * @param pageDTO 角色信息
     * @return 用户信息集合信息
     */
    PageResult<SysUserDO> selectAllocatedList(RoleAllocatedUserPageDTO pageDTO);

    /**
     * 根据条件分页查询未分配用户角色列表
     *
     * @param pageDTO 角色信息
     * @return 用户信息集合信息
     */
    PageResult<SysUserDO> selectUnallocatedList(RoleAllocatedUserPageDTO pageDTO);
}
