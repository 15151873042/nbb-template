package com.nbb.template.system.service.impl;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.exceptions.ExceptionUtil;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.ObjectUtil;
import com.google.code.kaptcha.Producer;
import com.nbb.template.system.core.constant.CoreCacheConstants;
import com.nbb.template.system.core.utils.ServiceExceptionUtil;
import com.nbb.template.system.core.utils.StrUtil;
import com.nbb.template.system.domain.vo.CaptchaImageVO;
import com.nbb.template.system.properties.SystemConfig;
import com.nbb.template.system.service.SysCaptchaService;
import com.nbb.template.system.service.SysConfigService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;
import org.springframework.util.FastByteArrayOutputStream;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

@Service
public class SysCaptchaServiceImpl implements SysCaptchaService {

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Resource
    private SysConfigService configService;

    @Resource
    private RedissonClient redissonClient;


    @Override
    public CaptchaImageVO getCode() {
        boolean captchaEnabled = configService.isCaptchaEnabled();
        if (!captchaEnabled) {
            return CaptchaImageVO.builder().captchaEnabled(false).build();
        }

        // 保存验证码信息
        String uuid = IdUtil.randomUUID();

        String capStr, code;
        BufferedImage image;

        // 生成验证码
        String captchaType = SystemConfig.getCaptchaType();
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        } else {
            throw ServiceExceptionUtil.exception("验证码类型有误");
        }

        // 将验证码值保存在缓存
        this.cacheSetCode(code, uuid);

        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            throw ExceptionUtil.wrapRuntime(e);
        }

        return CaptchaImageVO.builder()
                .captchaEnabled(true)
                .uuid(uuid)
                .img(Base64.encode(os.toByteArray()))
                .build();
    }

    @Override
    public void verifyCode(String verifyCode, String uuid) {
        boolean captchaEnabled = configService.isCaptchaEnabled();
        if (!captchaEnabled) {
            return;
        }

        // 获取缓存中的验证码值
        String originalCode = this.cacheGetCode(uuid);

        if (ObjectUtil.isNull(originalCode)) {
            throw ServiceExceptionUtil.exception("验证码已失效");
        }
        if (!StrUtil.equalsIgnoreCase(originalCode, verifyCode)) {
            throw ServiceExceptionUtil.exception("验证码错误");
        }
    }

    private String getCacheKey(String uuid) {
        return CoreCacheConstants.CAPTCHA_CODE_KEY + uuid;
    }

    private void cacheSetCode(String code, String uuid) {
        RBucket<String> bucket = redissonClient.getBucket(this.getCacheKey(uuid));
        bucket.set(code, 2, TimeUnit.MINUTES);
    }

    private String cacheGetCode(String uuid) {
        RBucket<String> bucket = redissonClient.getBucket(this.getCacheKey(uuid));
        return bucket.getAndDelete();
    }



}
