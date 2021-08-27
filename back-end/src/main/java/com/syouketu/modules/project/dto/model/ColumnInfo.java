package com.syouketu.modules.project.dto.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * 字段信息
 *
 * @author: JXI
 * @description:
 * @date: Created on 2018/6/1
 */
@Data
@ApiModel(description = "字段信息")
public class ColumnInfo implements Serializable {
    private static final long serialVersionUID = 3148176768559230877L;

    @ApiModelProperty(value = "字段名")
    private String columnName;

    @ApiModelProperty(value = "字段类型")
    private String columnType;

    @ApiModelProperty(value = "注解")
    private String comments;

    @ApiModelProperty(value = "是否可为空")
    private boolean isNullable;

    @ApiModelProperty(value = "是否是主键")
    private boolean isPrimaryKey;

    @ApiModelProperty(value = "是否自增长")
    private boolean isAutoIncrement;
}

