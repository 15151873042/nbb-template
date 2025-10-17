package com.nbb.template.system.service.impl;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ObjectUtil;
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
import org.springframework.aop.framework.AopContext;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.stereotype.Service;
import org.springframework.transaction.support.TransactionSynchronization;
import org.springframework.transaction.support.TransactionSynchronizationManager;

import java.util.List;

import static com.nbb.template.system.constant.SystemDictConstants.STATUS_ENABLE;

/**
 * @author 胡鹏
 */
@Service
public class SysDictDataServiceImpl extends ServiceImpl<SysDictDataMapper, SysDictDataDO> implements SysDictDataService {
    private final DataSourceTransactionManager transactionManager;

    public SysDictDataServiceImpl(DataSourceTransactionManager transactionManager) {
        this.transactionManager = transactionManager;
    }

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
    public void addDictData(DictDataAddDTO addDTO) {
        SysDictDataDO sysDictDataDO = BeanUtil.copyProperties(addDTO, SysDictDataDO.class);
        this.getBaseMapper().insert(sysDictDataDO);

        this.cacheEvictDictDataById(sysDictDataDO.getId());
    }

    @Override
    public void updateDictData(DictDataUpdateDTO updateDTO) {
        SysDictDataDO sysDictDataDO = BeanUtil.copyProperties(updateDTO, SysDictDataDO.class);
        this.getBaseMapper().updateEdit(sysDictDataDO);

        this.cacheEvictDictDataById(sysDictDataDO.getId());
    }

    @Override
    public void deleteDictDataByIds(List<Long> ids) {
        this.cacheEvictDictDataById(ids.get(0));

        getBaseMapper().deleteByIds(ids);
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

    @Override
    @CacheEvict(cacheNames = CoreCacheConstants.SYS_DICT_KEY, key = "#dictType")
    public void cacheEvitDictDataByDicType(String dictType) {

    }

    private void cacheEvictDictDataById(Long id) {
        SysDictDataDO sysDictDataDO = getById(id);
        if (ObjectUtil.isNull(sysDictDataDO)) {
            return;
        }

        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
            @Override
            public void afterCommit() {
                ((SysDictDataService)AopContext.currentProxy()).cacheEvitDictDataByDicType(sysDictDataDO.getDictType());
            }
        });
    }

//    private void processAfterCommit(Runnable runnable) {
//        TransactionSynchronizationManager.registerSynchronization(new TransactionSynchronization() {
//            @Override
//            public void afterCommit() {
//                runnable.run();
//            }
//        });
//    }
}
