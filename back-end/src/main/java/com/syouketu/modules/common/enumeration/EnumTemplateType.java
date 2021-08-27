package com.syouketu.modules.common.enumeration;

/**
 * 代码模板类型
 *
 * @author: JXI
 * @description:
 * @date: Created on 7/8/2019
 */
public enum EnumTemplateType {
    ENTITY("JAVA实体类", 1),
    MAPPER_XML("Mybatis映射XML", 2),
    MAPPER("Mybatis映射接口", 3),
    SERVICE("Spring服务接口", 4),
    SERVICE_IMPL("Spring服务实现", 5),
    CONTROLLER("Spring控制器", 6),
    QUERY_FORM("查询用表单", 7),
    VUE_LIST("Vue列表页", 8),
    VUE_DETAIL("Vue详情页", 9),
    VUE_API("Vue接口JS", 10),
    SAVE_FORM("保存用表单", 11),
    QUERY_VO("查询视图", 12);

    private String name;
    private Integer type;

    EnumTemplateType(String name, Integer type) {
        this.name = name;
        this.type = type;
    }

    public static EnumTemplateType getEnum(Integer type) {
        for (EnumTemplateType enumTemplateType : EnumTemplateType.values()) {
            if (enumTemplateType.getType().equals(type)) {
                return enumTemplateType;
            }
        }
        return ENTITY;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }
}
