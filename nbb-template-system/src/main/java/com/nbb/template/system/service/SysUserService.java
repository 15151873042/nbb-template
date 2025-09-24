package com.nbb.template.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbb.template.system.domain.entity.SysConfigDO;
import com.nbb.template.system.domain.entity.SysUserDO;
import com.nbb.template.system.mapper.SysUserMapper;

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

}
