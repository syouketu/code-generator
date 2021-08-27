package com.syouketu.modules.common.enumeration;

/**
 * 数据库类型
 *
 * @author: JXI
 * @description:
 * @date: Created on 7/8/2019
 */
public enum EnumDatabaseType {
    MySQL("com.mysql.cj.jdbc.Driver", 1),
    Oracle("oracle.jdbc.driver.OracleDriver", 2);

    private String driver;
    private Integer type;

    EnumDatabaseType(String driver, Integer type) {
        this.driver = driver;
        this.type = type;
    }

    public static EnumDatabaseType getEnum(Integer type) {
        for (EnumDatabaseType enumDatabaseType : EnumDatabaseType.values()) {
            if (enumDatabaseType.getType().equals(type)) {
                return enumDatabaseType;
            }
        }
        return MySQL;
    }

    public String getDriver() {
        return driver;
    }

    public Integer getType() {
        return type;
    }
}
