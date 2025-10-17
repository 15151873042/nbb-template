package com.nbb.template.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.dto.DictDataAddDTO;
import com.nbb.template.system.domain.dto.DictDataPageDTO;
import com.nbb.template.system.domain.dto.DictDataUpdateDTO;
import com.nbb.template.system.domain.entity.SysDictDataDO;

import java.util.List;

/**
 * @author 胡鹏
 */
public interface SysDictDataService extends IService<SysDictDataDO> {

    /**
     * 根据条件分页查询字典数据
     *
     * @param dto 字典数据信息
     * @return 字典数据集合信息
     */
    PageResult<SysDictDataDO> listPage(DictDataPageDTO dto);

    int addDictData(DictDataAddDTO addDTO);

    int updateDictData(DictDataUpdateDTO updateDTO);

    /**
     * 批量删除字典值
     *
     * @param ids 需要删除的字典ID
     */
    int deleteDictDataByIds(List<Long> ids, String dictType);

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型列表
     */
    List<SysDictDataDO> listDictDataByDictType(String dictType);
}
