package com.nbb.template.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbb.template.system.domain.entity.SysConfigDO;

/**
 * @author 胡鹏
 */
public interface SysConfigService extends IService<SysConfigDO> {

    /**
     * 获取验证码开关
     *
     * @return true开启，false关闭
     */
    boolean isCaptchaEnabled();

    /**
     * 根据键名查询参数配置信息
     *
     * @param configKey 参数键名
     * @return 参数键值
     */
    String selectConfigByKey(String configKey);
}
