package com.nbb.template.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbb.template.system.core.constant.CoreCacheConstants;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.dto.DictDataAddDTO;
import com.nbb.template.system.domain.dto.DictDataPageDTO;
import com.nbb.template.system.domain.dto.DictDataUpdateDTO;
import com.nbb.template.system.domain.entity.SysDictDataDO;
import com.nbb.template.system.framework.mybatis.LambdaQueryWrapperX;
import com.nbb.template.system.mapper.SysDictDataMapper;
import com.nbb.template.system.service.SysDictDataService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.nbb.template.system.constant.SystemDictConstants.STATUS_ENABLE;

/**
 * @author 胡鹏
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictDataDO> implements SysDictDataService {
    @Override
    public PageResult<SysDictDataDO> listPage(DictDataPageDTO dto) {
        LambdaQueryWrapper<SysDictDataDO> queryWrapper = new LambdaQueryWrapperX<SysDictDataDO>()
                .eqIfPresent(SysDictDataDO::getDictType, dto.getDictType())
                .eqIfPresent(SysDictDataDO::getStatus, dto.getStatus())
                .likeIfPresent(SysDictDataDO::getDictLabel, dto.getDictLabel())
                .orderByAsc(SysDictDataDO::getDictSort);
        return getBaseMapper().selectPage(dto, queryWrapper);
    }

    @Override
    @CacheEvict(cacheNames = CoreCacheConstants.SYS_DICT_KEY, key = "#addDTO.dictType")
    public int addDictData(DictDataAddDTO addDTO) {
        SysDictDataDO sysDictDataDO = BeanUtil.copyProperties(addDTO, SysDictDataDO.class);

        return this.getBaseMapper().insert(sysDictDataDO);
    }

    @Override
    @CacheEvict(cacheNames = CoreCacheConstants.SYS_DICT_KEY, key = "#updateDTO.dictType")
    public int updateDictData(DictDataUpdateDTO updateDTO) {
        SysDictDataDO sysDictDataDO = BeanUtil.copyProperties(updateDTO, SysDictDataDO.class);
        return this.getBaseMapper().updateEdit(sysDictDataDO);
    }

    @Override
    @CacheEvict(cacheNames = CoreCacheConstants.SYS_DICT_KEY, key = "#dictType")
    public int deleteDictDataByIds(List<Long> ids, String dictType) {
        return getBaseMapper().deleteByIds(ids);
    }

    @Override
    @Cacheable(cacheNames = CoreCacheConstants.SYS_DICT_KEY, key = "#dictType", unless = "#result == null || #result.size == 0")
    public List<SysDictDataDO> listDictDataByDictType(String dictType) {
        LambdaQueryWrapper<SysDictDataDO> queryWrapper = new LambdaQueryWrapperX<SysDictDataDO>()
                .eq(SysDictDataDO::getDictType, dictType)
                .eq(SysDictDataDO::getStatus, STATUS_ENABLE)
                .orderByAsc(SysDictDataDO::getDictSort);

        return getBaseMapper().selectList(queryWrapper);
    }
}
