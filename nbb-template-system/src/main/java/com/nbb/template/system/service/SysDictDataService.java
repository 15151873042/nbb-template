package com.nbb.template.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.dto.DictDataAddDTO;
import com.nbb.template.system.domain.dto.DictDataPageDTO;
import com.nbb.template.system.domain.dto.DictDataUpdateDTO;
import com.nbb.template.system.domain.entity.SysDictDataDO;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional
    void addDictData(DictDataAddDTO addDTO);

    @Transactional
    void updateDictData(DictDataUpdateDTO updateDTO);

    /**
     * 批量删除字典值
     *
     * @param ids 需要删除的字典ID
     */
    @Transactional
    void deleteDictDataByIds(List<Long> ids);

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型列表
     */
    List<SysDictDataDO> listDictDataByDictType(String dictType);

    void cacheEvitDictDataByDicType(String dictTye);


}
