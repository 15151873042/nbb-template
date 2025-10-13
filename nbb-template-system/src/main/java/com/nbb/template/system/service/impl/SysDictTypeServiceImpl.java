package com.nbb.template.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.nbb.template.system.core.constant.CoreCacheConstants;
import com.nbb.template.system.core.domain.PageResult;
import com.nbb.template.system.domain.dto.DictTypePageDTO;
import com.nbb.template.system.domain.entity.SysDictDataDO;
import com.nbb.template.system.domain.entity.SysDictTypeDO;
import com.nbb.template.system.framework.mybatis.LambdaQueryWrapperX;
import com.nbb.template.system.mapper.SysDictDataMapper;
import com.nbb.template.system.mapper.SysDictTypeMapper;
import com.nbb.template.system.service.SysDictTypeService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

import static com.nbb.template.system.constant.SystemDictConstants.STATUS_ENABLE;

/**
 * @author 胡鹏
 */
@Service
public class SysDictTypeServiceImpl extends ServiceImpl<SysDictTypeMapper, SysDictTypeDO> implements SysDictTypeService {

    @Resource
    private SysDictDataMapper dictDataMapper;

    @Override
    public PageResult<SysDictTypeDO> listDictTypePage(DictTypePageDTO dto) {
        LambdaQueryWrapper<SysDictTypeDO> queryWrapper = new LambdaQueryWrapperX<SysDictTypeDO>()
                .likeIfPresent(SysDictTypeDO::getDictName, dto.getDictName())
                .likeIfPresent(SysDictTypeDO::getDictType, dto.getDictType())
                .eqIfPresent(SysDictTypeDO::getStatus, dto.getStatus())
                .geIfPresent(SysDictTypeDO::getCreateTime, dto.getBeginTime())
                .leIfPresent(SysDictTypeDO::getCreateTime, dto.getEndTime())
                .orderByAsc(SysDictTypeDO::getCreateTime, SysDictTypeDO::getId);
        PageResult<SysDictTypeDO> pageResult = getBaseMapper().selectPage(dto, queryWrapper);
        return pageResult;
    }

    @Override
    @Cacheable(cacheNames = CoreCacheConstants.SYS_DICT_KEY, key = "#dictType", unless = "#result == null || #result.size == 0")
    public List<SysDictDataDO> selectDictDataByType(String dictType) {
        LambdaQueryWrapper<SysDictDataDO> queryWrapper = new LambdaQueryWrapperX<SysDictDataDO>()
                .eq(SysDictDataDO::getDictType, dictType)
                .eq(SysDictDataDO::getStatus, STATUS_ENABLE)
                .orderByAsc(SysDictDataDO::getDictSort);

        return dictDataMapper.selectList(queryWrapper);
    }
}
