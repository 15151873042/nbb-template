package com.nbb.template.system.mapper;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.nbb.template.system.domain.entity.SysDictDataDO;
import com.nbb.template.system.domain.entity.SysDictTypeDO;
import com.nbb.template.system.framework.mybatis.BaseMapperX;
import com.nbb.template.system.framework.mybatis.LambdaQueryWrapperX;

/**
 * @author 胡鹏
 */
public interface SysDictDataMapper extends BaseMapperX<SysDictDataDO> {


    default long countByDictType(String dictType) {
        LambdaQueryWrapperX<SysDictDataDO> queryWrapper = new LambdaQueryWrapperX<SysDictDataDO>()
                .eq(SysDictDataDO::getDictType, dictType);

        return selectCount(queryWrapper);
    }

    default int updateEdit(SysDictDataDO dictDataDO) {
        LambdaUpdateWrapper<SysDictDataDO> updateWrapper = new LambdaUpdateWrapper<SysDictDataDO>()
                .set(SysDictDataDO::getDictLabel, dictDataDO.getDictLabel())
                .set(SysDictDataDO::getDictValue, dictDataDO.getDictValue())
                .set(SysDictDataDO::getDictSort, dictDataDO.getDictSort())
                .set(SysDictDataDO::getStatus, dictDataDO.getStatus())
                .set(SysDictDataDO::getListClass, dictDataDO.getListClass())
                .set(SysDictDataDO::getCssClass, dictDataDO.getCssClass())
                .set(SysDictDataDO::getRemark, dictDataDO.getRemark())
                .eq(SysDictDataDO::getId, dictDataDO.getId());
        return update(dictDataDO, updateWrapper);
    }


}
