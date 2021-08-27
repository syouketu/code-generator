package com.syouketu.modules.project.dao;

import com.syouketu.modules.project.dto.model.TableInfo;
import com.syouketu.modules.project.entity.Database;

import java.util.List;

/**
 * JDBC方式访问接口
 *
 * @author: JXI
 * @description:
 * @date: Created on 7/9/2019
 */
public interface CodeJdbcDao {

    /**
     * 修改表字段注释
     *
     * @param tableInfo
     * @param database
     */
    void saveComment(TableInfo tableInfo, Database database);

    /**
     * 获取数据库下所有表信息
     *
     * @param database
     * @param tableName
     * @return
     */
    List<TableInfo> getAllTables(Database database, String tableName);

    /**
     * 获取表的所有字段信息
     *
     * @param tableName
     * @param database
     * @return
     */
    TableInfo getAllColumns(Database database, String tableName);

}
