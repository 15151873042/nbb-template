package com.nbb.template.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.lock.annotation.Lock4j;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbb.template.system.constant.ErrorCodeConstants;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.core.utils.ServiceExceptionUtil;
import com.nbb.template.system.domain.dto.DictTypeAddDTO;
import com.nbb.template.system.domain.dto.DictTypePageDTO;
import com.nbb.template.system.domain.dto.DictTypeUpdateDTO;
import com.nbb.template.system.domain.entity.SysDictTypeDO;
import com.nbb.template.system.framework.mybatis.LambdaQueryWrapperX;
import com.nbb.template.system.mapper.SysDictDataMapper;
import com.nbb.template.system.mapper.SysDictTypeMapper;
import com.nbb.template.system.service.SysDictTypeService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author 胡鹏
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictTypeDO> implements SysDictTypeService {

    @Resource
    private SysDictDataMapper dictDataMapper;

    @Override
    public PageResult<SysDictTypeDO> listPageDictType(DictTypePageDTO queryDTO) {
        LambdaQueryWrapper<SysDictTypeDO> queryWrapper = new LambdaQueryWrapperX<SysDictTypeDO>()
                .likeIfPresent(SysDictTypeDO::getDictName, queryDTO.getDictName())
                .likeIfPresent(SysDictTypeDO::getDictType, queryDTO.getDictType())
                .eqIfPresent(SysDictTypeDO::getStatus, queryDTO.getStatus())
                .geIfPresent(SysDictTypeDO::getCreateTime, queryDTO.getBeginTime())
                .leIfPresent(SysDictTypeDO::getCreateTime, queryDTO.getEndTime())
                .orderByDesc(SysDictTypeDO::getCreateTime)
                .orderByAsc(SysDictTypeDO::getId);
        PageResult<SysDictTypeDO> pageResult = getBaseMapper().selectPage(queryDTO, queryWrapper);
        return pageResult;
    }


    @Override
    public SysDictTypeDO selectDictTypeById(Long id) {
        return this.getBaseMapper().selectById(id);
    }

    @Override
    public boolean isDictTypeUnique(String dictType, Long excludeId) {
        LambdaQueryWrapperX<SysDictTypeDO> queryWrapper = new LambdaQueryWrapperX<SysDictTypeDO>()
                .eq(SysDictTypeDO::getDictType, dictType)
                .neIfPresent(SysDictTypeDO::getId, excludeId);

        Long count = getBaseMapper().selectCount(queryWrapper);
        return count == 0;
    }

    @Override
    @Lock4j(name="unique:sys_dict_type", keys = {"#addDTO.dictType"})
    public int addDictType(DictTypeAddDTO addDTO) {
        // 校验数据是否存在
        this.checkDictTypeUnique(addDTO.getDictType(), null);

        // 新增
        SysDictTypeDO dictTypeDO = BeanUtil.copyProperties(addDTO, SysDictTypeDO.class);
        return this.getBaseMapper().insert(dictTypeDO);
    }

    @Override
    @Lock4j(name="unique:sys_dict_type", keys = {"#updateDTO.dictType"})
    public int updateDictType(DictTypeUpdateDTO updateDTO) {
        // 校验数据是否存在
        this.checkDictTypeUnique(updateDTO.getDictType(), updateDTO.getId());

        // 修改
        SysDictTypeDO sysDictTypeDO = BeanUtil.copyProperties(updateDTO, SysDictTypeDO.class);
        return this.getBaseMapper().updateEdit(sysDictTypeDO);
    }

    @Override
    public int deleteDictTypeByIds(List<Long> ids) {
        // 1. 校验是否有字典数据
        List<SysDictTypeDO> dictTypeDOList = getBaseMapper().selectByIds(ids);
        dictTypeDOList.forEach(dictTypeDO -> {
            long dictDataCount = dictDataMapper.countByDictType(dictTypeDO.getDictType());
            if (dictDataCount > 0) {
                throw ServiceExceptionUtil.exception(ErrorCodeConstants.DICT_TYPE_HAS_CHILDREN, dictTypeDO.getDictName());
            }
        });

        // 2. 批量删除字典类型
        return getBaseMapper().deleteByIds(ids);
    }

    @Override
    public List<SysDictTypeDO> listDictType() {
        return super.list();
    }


    void checkDictTypeUnique(String dictType, Long excludeId) {
        boolean unique = this.isDictTypeUnique(dictType, excludeId);
        if (!unique) {
            throw ServiceExceptionUtil.dataExistException();
        }
    }

}
