package com.nbb.template.system.controller;

import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.IdUtil;
import com.google.code.kaptcha.Producer;
import com.nbb.template.system.core.constant.CoreCacheConstants;
import com.nbb.template.system.core.domain.CommonResult;
import com.nbb.template.system.domain.vo.CaptchaImageVO;
import com.nbb.template.system.properties.SysConfig;
import com.nbb.template.system.service.SysConfigService;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/**
 * 验证码操作处理
 *
 * @author ruoyi
 */
@RestController
public class CaptchaController {
    @Resource(name = "captchaProducer")
    private Producer captchaProducer;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Autowired
    private SysConfigService configService;

    @Resource
    private RedissonClient redissonClient;


    /**
     * 生成验证码
     */
    @GetMapping("/captchaImage")
    public CommonResult<CaptchaImageVO> getCode() {
        boolean captchaEnabled = configService.isCaptchaEnabled();
        if (!captchaEnabled) {
            CaptchaImageVO vo = CaptchaImageVO.builder().captchaEnabled(false).build();
            return CommonResult.success(vo);
        }

        // 保存验证码信息
        String uuid = IdUtil.randomUUID();
        String verifyKey = CoreCacheConstants.CAPTCHA_CODE_KEY + uuid;

        String capStr, code = null;
        BufferedImage image = null;

        // 生成验证码
        String captchaType = SysConfig.getCaptchaType();
        if ("math".equals(captchaType)) {
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        } else if ("char".equals(captchaType)) {
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }


        RBucket<Object> bucket = redissonClient.getBucket(verifyKey);
        bucket.set(code, 2, TimeUnit.MINUTES);

        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try {
            ImageIO.write(image, "jpg", os);
        } catch (IOException e) {
            return CommonResult.error(e.getMessage());
        }


        CaptchaImageVO vo = CaptchaImageVO.builder()
                .captchaEnabled(true)
                .uuid(uuid)
                .img(Base64.encode(os.toByteArray()))
                .build();

        return CommonResult.success(vo);
    }
}
