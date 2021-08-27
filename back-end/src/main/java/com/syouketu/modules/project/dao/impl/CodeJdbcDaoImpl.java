package com.syouketu.modules.project.dao.impl;

import com.syouketu.modules.common.enumeration.EnumDatabaseType;
import com.syouketu.modules.project.dao.CodeJdbcDao;
import com.syouketu.modules.project.dto.model.ColumnInfo;
import com.syouketu.modules.project.dto.model.TableInfo;
import com.syouketu.modules.project.entity.Database;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Repository;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * JDBC方式访问实现
 *
 * @author: JXI
 * @description:
 * @date: Created on 7/9/2019
 */
@Repository
public class CodeJdbcDaoImpl implements CodeJdbcDao {

    private final String ALTER_TABLE_COMMENT_MYSQL = "ALTER TABLE %s COMMENT '%s';";
    private final String ALTER_FIELD_COMMENT_MYSQL = "ALTER TABLE %s MODIFY %s %s %s %s COMMENT '%s';";

    private final String ALTER_TABLE_COMMENT_ORACLE = "ALTER TABLE %s COMMENT '%s';";

    @Override
    public void saveComment(TableInfo tableInfo, Database dbConfig) {
        Connection conn = getConnection(dbConfig);
        try {
            Statement stmt = conn.createStatement();
            String strSql = "";
            String isNullable = "";
            String isAutoIncrement = "";

            if (dbConfig.getUrl().indexOf("mysql") > 0) {
                strSql = String.format(ALTER_TABLE_COMMENT_MYSQL, tableInfo.getTableName(), tableInfo.getComments());
                stmt.executeUpdate(strSql);
                //stmt.executeUpdate("use information_schema;");
                for (ColumnInfo item : tableInfo.getColumnList()) {
                    isNullable = item.isNullable() ? "" : "NOT NULL";
                    isAutoIncrement = item.isAutoIncrement() ? "AUTO_INCREMENT" : "";
                    strSql = String.format(ALTER_FIELD_COMMENT_MYSQL, tableInfo.getTableName(), item.getColumnName(), item.getColumnType(), isNullable, isAutoIncrement, item.getComments());
                    System.out.println(">>>>>>>>>>>" + strSql);
                    //strSql = "update information_schema.COLUMNS t set t.column_comment='"+item.getComments()+"' where t.TABLE_SCHEMA='数据库名' and t.table_name='"+tableInfo.getTableName()+"' and t.COLUMN_NAME='"+item.getColName()+"';");
                    stmt.executeUpdate(strSql);
                }
            } else {
                strSql = "COMMENT ON TABLE " + tableInfo.getTableName() + " IS '#" + tableInfo.getComments() + "'";
                stmt.executeUpdate(strSql);
                for (ColumnInfo item : tableInfo.getColumnList()) {
                    strSql = "COMMENT ON COLUMN " + tableInfo.getTableName() + "." + item.getColumnName() + " IS '" + item.getComments() + "'";
                    stmt.executeUpdate(strSql);
                }
            }
            if (stmt != null) {   // 关闭声明
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("execute sql occer error", e);
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }


    @Override
    public List<TableInfo> getAllTables(Database dbConfig, String tableName) {
        List<TableInfo> tableList = new ArrayList<TableInfo>();

        Connection conn = getConnection(dbConfig);
        try {
            Statement stmt = conn.createStatement();
            String strSql = "";
            if (dbConfig.getUrl().indexOf("mysql") > 0) {
                strSql = "select table_name,TABLE_COMMENT from information_schema.tables where table_schema='" + dbConfig.getDbSchema() + "'";
                if (StringUtils.isNotBlank(tableName)) {
                    strSql += " AND table_name LIKE '%" + tableName + "%'";
                }
            } else {
                strSql = "select table_name,comments from user_tab_comments where table_type='TABLE'";
                if (StringUtils.isNotBlank(tableName)) {
                    strSql += " AND table_name LIKE '%" + tableName + "%'";
                }
                strSql += " order by table_name";
            }
            System.out.println(">>>>>>>>>>>>" + strSql);
            ResultSet rs = stmt.executeQuery(strSql);
            while (rs.next()) {
                TableInfo table = new TableInfo();
                table.setTableName(rs.getString(1));
                table.setComments(rs.getString(2));
                tableList.add(table);
            }

            if (stmt != null) {   // 关闭声明
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("execute sql occer error", e);
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return tableList;
    }

    @Override
    public TableInfo getAllColumns(Database dbConfig, String tableName) {
        TableInfo tableInfo = new TableInfo();
        tableInfo.setTableName(tableName);

        Connection conn = getConnection(dbConfig);
        try {
            Statement stmt = conn.createStatement();
            String strSql = "";
            //得到表注解
            if (dbConfig.getUrl().indexOf("mysql") > 0) {
                strSql = "select TABLE_COMMENT from information_schema.tables where table_name='" + tableName + "' and table_schema='" + dbConfig.getDbSchema() + "'";
            } else {
                strSql = "select comments from user_tab_comments where table_name='" + tableName + "'";
            }
            ResultSet rs = stmt.executeQuery(strSql);
            while (rs.next()) {
                tableInfo.setComments(rs.getString(1));
            }

            //得到字段注解
            if (dbConfig.getUrl().indexOf("mysql") > 0) {
                strSql = "select column_name,column_comment,column_type,IS_NULLABLE,COLUMN_KEY,EXTRA from Information_schema.columns where table_Name = '" + tableName + "' and table_schema='" + dbConfig.getDbSchema() + "'";
            } else {
                strSql = "select z.COLUMN_NAME,c.comments,z.data_type from user_tab_columns z,user_col_comments c where z.TABLE_NAME=c.table_name and z.COLUMN_NAME=c.column_name and z.Table_Name='" + tableName + "'";
            }
            System.out.println(strSql);
            List<ColumnInfo> colList = new ArrayList<ColumnInfo>();
            rs = stmt.executeQuery(strSql);
            while (rs.next()) {
                ColumnInfo colInfo = new ColumnInfo();
                colInfo.setColumnName(rs.getString(1));
                colInfo.setComments(rs.getString(2));
                if (dbConfig.getUrl().indexOf("mysql") > 0) {
//                    if ("varchar".equalsIgnoreCase(rs.getString(3))) {
                    colInfo.setColumnType(rs.getString(3));
//                    }
                    colInfo.setNullable("YES".equals(rs.getString(4)) ? true : false);
                    colInfo.setPrimaryKey(StringUtils.isNotBlank(rs.getString(5)) ? true : false);
                    colInfo.setAutoIncrement(StringUtils.isNotBlank(rs.getString(6)) ? true : false);
                } else {
                    colInfo.setColumnType(rs.getString(3));
                }
                colList.add(colInfo);
            }
            tableInfo.setColumnList(colList);

            if (stmt != null) {   // 关闭声明
                try {
                    stmt.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            throw new RuntimeException("execute sql occer error", e);
        } finally {
            try {
                conn.close();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }

        return tableInfo;
    }

    /* 获取数据库连接的函数*/
    private Connection getConnection(Database dbConfig) {
        Connection con = null;  //创建用于连接数据库的Connection对象
        try {
            EnumDatabaseType enumDatabaseType = EnumDatabaseType.getEnum(dbConfig.getDbType());
            Class.forName(dbConfig.getDriver());// 加载数据库驱动
            switch (enumDatabaseType) {
                case Oracle:
                    con = DriverManager.getConnection(dbConfig.getUrl(), dbConfig.getUser(), dbConfig.getPasswd());// 创建数据连接
                    break;
                case MySQL:
                    con = DriverManager.getConnection(dbConfig.getUrl() + "?useUnicode=true&characterEncoding=UTF8", dbConfig.getUser(), dbConfig.getPasswd());// 创建数据连接
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("数据库连接失败" + e.getMessage());
        }
        return con; //返回所建立的数据库连接
    }


}
