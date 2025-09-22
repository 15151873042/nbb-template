package com.nbb.template.system.service.impl;

import cn.hutool.core.convert.Convert;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbb.template.system.domain.entity.SysConfigDO;
import com.nbb.template.system.framework.mybatis.LambdaQueryWrapperX;
import com.nbb.template.system.mapper.SysConfigMapper;
import com.nbb.template.system.service.SysConfigService;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author 胡鹏
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigMapper, SysConfigDO> implements SysConfigService {
    @Override
    public boolean isCaptchaEnabled() {
        String captchaEnabled = selectConfigByKey("sys.account.captchaEnabled");
        if (StrUtil.isEmpty(captchaEnabled)) {
            return true;
        }
        return Convert.toBool(captchaEnabled);
    }

    @Override
    public String selectConfigByKey(String configKey) {
        LambdaQueryWrapper<SysConfigDO> queryWrapper = new LambdaQueryWrapperX<SysConfigDO>()
                .eq(SysConfigDO::getConfigKey, configKey)
                .select(SysConfigDO::getConfigValue);
        SysConfigDO sysConfigDO = getBaseMapper().selectOne(queryWrapper);

        return Optional.ofNullable(sysConfigDO).map(SysConfigDO::getConfigValue).orElse(StrUtil.EMPTY);
    }
}
