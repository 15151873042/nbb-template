package com.nbb.template.system.framework.mybatis.handler;

import cn.dev33.satoken.stp.StpUtil;
import com.baomidou.mybatisplus.core.handlers.MetaObjectHandler;
import com.nbb.template.system.core.domain.BaseDO;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 通用参数填充实现类
 * 如果没有显式的对通用参数进行赋值，这里会对通用参数进行填充、赋值
 *
 * @author 胡鹏
 */
@Component
public class DefaultDBFieldHandler implements MetaObjectHandler {

    @Override
    public void insertFill(MetaObject metaObject) {
        if (Objects.nonNull(metaObject) && metaObject.getOriginalObject() instanceof BaseDO) {
            BaseDO baseDO = (BaseDO) metaObject.getOriginalObject();

            LocalDateTime current = LocalDateTime.now();
            // 创建时间为空，则以当前时间为插入时间
            if (Objects.isNull(baseDO.getCreateTime())) {
                baseDO.setCreateTime(current);
            }
            // 更新时间为空，则以当前时间为更新时间
            if (Objects.isNull(baseDO.getUpdateTime())) {
                baseDO.setUpdateTime(current);
            }

            boolean isLogin = StpUtil.isLogin();
            // 当前登录用户不为空，创建人为空，则当前登录用户为创建人
            if (isLogin && Objects.isNull(baseDO.getCreateId())) {
                baseDO.setCreateId(StpUtil.getLoginIdAsLong());
            }
            // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
            if (isLogin && Objects.isNull(baseDO.getUpdateId())) {
                baseDO.setUpdateId(StpUtil.getLoginIdAsLong());
            }
        }
    }

    @Override
    public void updateFill(MetaObject metaObject) {
        // 更新时间为空，则以当前时间为更新时间
        Object modifyTime = getFieldValByName("updateTime", metaObject);
        if (Objects.isNull(modifyTime)) {
            setFieldValByName("updateTime", LocalDateTime.now(), metaObject);
        }

        // 当前登录用户不为空，更新人为空，则当前登录用户为更新人
        Object updateId = getFieldValByName("updateId", metaObject);
        boolean isLogin = StpUtil.isLogin();
        if (isLogin && Objects.isNull(updateId)) {
            setFieldValByName("updateId", StpUtil.getLoginIdAsLong(), metaObject);
        }
    }
}
