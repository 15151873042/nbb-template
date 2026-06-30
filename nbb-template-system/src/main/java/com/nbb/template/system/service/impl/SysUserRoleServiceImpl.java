package com.nbb.template.system.service.impl;

import com.nbb.template.system.mapper.SysUserRoleMapper;
import com.nbb.template.system.service.SysUserRoleService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author 胡鹏
 */
@Service
public class SysUserRoleServiceImpl implements SysUserRoleService {

    @Resource
    SysUserRoleMapper sysUserRoleMapper;
}
