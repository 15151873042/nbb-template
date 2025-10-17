package com.nbb.template.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.dto.DictTypeAddDTO;
import com.nbb.template.system.domain.dto.DictTypePageDTO;
import com.nbb.template.system.domain.dto.DictTypeUpdateDTO;
import com.nbb.template.system.domain.entity.SysDictDataDO;
import com.nbb.template.system.domain.entity.SysDictTypeDO;

import java.util.List;

/**
 * @author 胡鹏
 */
public interface SysDictTypeService extends IService<SysDictTypeDO> {

    /**
     * 分页查询字典类型列表
     *
     * @param queryDTO 查询参数
     * @return 字典类型列表
     */
    PageResult<SysDictTypeDO> listPageDictType(DictTypePageDTO queryDTO);

    /**
     * 根据字典类型查询信息
     *
     * @param dictType 字典类型
     * @return 字典类型列表
     */
    List<SysDictDataDO> selectDictDataByType(String dictType);

    /**
     * 根据字典类型ID查询信息
     *
     * @param id 字典类型ID
     * @return 字典类型
     */
    SysDictTypeDO selectDictTypeById(Long id);

    /**
     * 校验字典类型称是否唯一
     *
     * @param dictType 字典类型
     * @param excludeId 排除的字典类型ID
     * @return 结果
     */
    boolean isDictTypeUnique(String dictType, Long excludeId);


    /**
     * 新增保存字典类型信息
     *
     * @param addDTO 字典类型信息
     * @return 结果
     */
    int addDictType(DictTypeAddDTO addDTO);

    /**
     * 修改保存字典类型信息
     *
     * @param updateDTO 字典类型信息
     * @return 结果
     */
    int updateDictType(DictTypeUpdateDTO updateDTO);

    /**
     * 批量删除字典信息
     *
     * @param ids 需要删除的字典ID
     */
    int deleteDictTypeByIds(List<Long> ids);

    /**
     * 获得全部字典类型列表
     *
     * @return 字典类型列表
     */
    List<SysDictTypeDO> listDictType();
}
