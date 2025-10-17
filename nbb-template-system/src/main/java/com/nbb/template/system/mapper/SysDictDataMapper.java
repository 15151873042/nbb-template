package com.nbb.template.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nbb.template.system.domain.entity.SysDictDataDO;
import com.nbb.template.system.framework.mybatis.LambdaQueryWrapperX;

/**
 * @author 胡鹏
 */
public interface SysDictDataMapper extends BaseMapper<SysDictDataDO> {


    default long countByDictType(String dictType) {
        LambdaQueryWrapperX<SysDictDataDO> queryWrapper = new LambdaQueryWrapperX<SysDictDataDO>()
                .eq(SysDictDataDO::getDictType, dictType);

        return selectCount(queryWrapper);
    }


}
