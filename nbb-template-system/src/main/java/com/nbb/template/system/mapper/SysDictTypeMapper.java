package com.nbb.template.system.mapper;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.nbb.template.system.domain.dto.DictTypeUpdateDTO;
import com.nbb.template.system.domain.entity.SysDictTypeDO;
import com.nbb.template.system.framework.mybatis.BaseMapperX;

/**
 * @author 胡鹏
 */
public interface SysDictTypeMapper extends BaseMapperX<SysDictTypeDO> {

    default int updateEdit(SysDictTypeDO dictTypeDO) {
        LambdaUpdateWrapper<SysDictTypeDO> updateWrapper = new LambdaUpdateWrapper<SysDictTypeDO>()
                .set(SysDictTypeDO::getDictType, dictTypeDO.getDictType())
                .set(SysDictTypeDO::getDictName, dictTypeDO.getDictName())
                .set(SysDictTypeDO::getStatus, dictTypeDO.getStatus())
                .set(SysDictTypeDO::getRemark, dictTypeDO.getRemark())
                .eq(SysDictTypeDO::getId, dictTypeDO.getId());
        return update(dictTypeDO, updateWrapper);
    }
}
