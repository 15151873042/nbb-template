package com.nbb.template.system.service;

import com.nbb.template.system.domain.dto.LoginDTO;
import com.nbb.template.system.domain.vo.LoginVO;

/**
 * @author 胡鹏
 */
public interface LoginService {
    LoginVO login(LoginDTO dto);
}
