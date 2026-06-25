package com.nbb.template.system.framework.mybatis.interceptor;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.date.LocalDateTimeUtil;
import com.baomidou.mybatisplus.core.toolkit.PluginUtils;
import com.baomidou.mybatisplus.extension.parser.JsqlParserSupport;
import com.baomidou.mybatisplus.extension.plugins.inner.InnerInterceptor;
import com.nbb.template.system.core.utils.StrUtil;
import net.sf.jsqlparser.expression.Expression;
import net.sf.jsqlparser.expression.LongValue;
import net.sf.jsqlparser.expression.StringValue;
import net.sf.jsqlparser.schema.Column;
import net.sf.jsqlparser.statement.update.Update;
import net.sf.jsqlparser.statement.update.UpdateSet;
import org.apache.ibatis.executor.statement.StatementHandler;
import org.apache.ibatis.mapping.MappedStatement;
import org.apache.ibatis.mapping.SqlCommandType;
import org.springframework.stereotype.Component;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 逻辑删除语句执行时候，自动更新更新人和更新时间
 */
public class LogicDeleteDBFieldInterceptor extends JsqlParserSupport implements InnerInterceptor {

    @Override
    public void beforePrepare(StatementHandler sh, Connection connection, Integer transactionTimeout) {
        PluginUtils.MPStatementHandler mpSh = PluginUtils.mpStatementHandler(sh);
        MappedStatement ms = mpSh.mappedStatement();

        // 当数据库表有逻辑删除字段时，实际调用删除方法时候，对应的SqlCommandType会编程Update
        if (SqlCommandType.UPDATE ==  ms.getSqlCommandType()
                && CollUtil.getLast(StrUtil.split(ms.getId(), ".")).startsWith("delete")) {
            PluginUtils.MPBoundSql mpBs = mpSh.mPBoundSql();
            mpBs.sql(this.parserMulti(mpBs.sql(), null));
        }
    }

    @Override
    protected void processUpdate(Update update, int index, String sql, Object obj) {
        List<UpdateSet> updateSets = update.getUpdateSets();
        String tableName = update.getTable().getName();

        Set<String> updateColumnNames = updateSets.stream()
                .flatMap(updateSet -> updateSet.getColumns().stream().map(Column::getColumnName))
                .collect(Collectors.toSet());
        if (!updateColumnNames.contains("update_time")) {
            Column column = new Column(tableName + "." + "update_time");
            Expression expression = new StringValue(LocalDateTimeUtil.format(LocalDateTime.now(), "yyyy-MM-dd HH:mm:ss"));
            UpdateSet updateSet = new UpdateSet(column, expression);
            updateSets.add(updateSet);
        }
        if (!updateColumnNames.contains("update_id") && StpUtil.isLogin()) {
            Column column = new Column(tableName + "." + "update_id");
            Expression expression = new LongValue(StpUtil.getLoginIdAsLong());
            UpdateSet updateSet = new UpdateSet(column, expression);
            updateSets.add(updateSet);
        }

    }

}
