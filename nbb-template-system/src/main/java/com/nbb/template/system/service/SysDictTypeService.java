package com.nbb.template.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.dto.DictTypePageDTO;
import com.nbb.template.system.domain.entity.SysDictDataDO;
import com.nbb.template.system.domain.entity.SysDictTypeDO;

import java.util.List;

/**
 * @author 胡鹏
 */
public interface SysDictTypeService extends IService<SysDictTypeDO> {

    PageResult<SysDictTypeDO> listDictTypePage(DictTypePageDTO dto);

    List<SysDictDataDO> selectDictDataByType(String dictType);


}
