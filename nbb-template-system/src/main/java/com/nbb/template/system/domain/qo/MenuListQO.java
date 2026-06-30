package com.nbb.template.system.domain.qo;

import com.nbb.template.system.domain.dto.MenuListDTO;
import lombok.Data;

/**
 * @author 胡鹏
 */
@Data
public class MenuListQO extends MenuListDTO {

    /** 用户id */
    private Long userId;

}
