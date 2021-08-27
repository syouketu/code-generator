package com.syouketu.modules.project.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import com.syouketu.modules.common.entity.BaseEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * <p>
 * 项目表
 * </p>
 *
 * @author jxi
 * @since 2019-07-08
 */
@Data
@ApiModel(description = "项目表")
@TableName("pj_project")
public class Project extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 项目ID
     */
    @ApiModelProperty(value = "项目ID")
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    /**
     * 项目名称
     */
    @ApiModelProperty(value = "项目名称")
    @TableField("name")
    private String name;

    /**
     * 父包名。如果为空，将下面子包名必须写全部， 否则就只需写子包名
     */
    @ApiModelProperty(value = "父包名")
    @TableField("parent_package")
    private String parentPackage;

    /**
     * 父包模块名
     */
    @ApiModelProperty(value = "父包模块名")
    @TableField("module_name")
    private String moduleName;

    /**
     * Entity包名
     */
    @ApiModelProperty(value = "Entity包名")
    @TableField("entity")
    private String entity;

    /**
     * Service包名
     */
    @ApiModelProperty(value = "Service包名")
    @TableField("service")
    private String service;

    /**
     * Service Impl包名
     */
    @ApiModelProperty(value = "Service Impl包名")
    @TableField("service_impl ")
    private String serviceImpl ;

    /**
     * Mapper包名
     */
    @ApiModelProperty(value = "Mapper包名")
    @TableField("mapper")
    private String mapper;

    /**
     * Mapper XML包名
     */
    @ApiModelProperty(value = "Mapper XML包名")
    @TableField("mapper_xml ")
    private String mapperXml ;

    /**
     * Controller包名
     */
    @ApiModelProperty(value = "Controller包名")
    @TableField("controller")
    private String controller;

    /**
     * 是否生成前缀
     */
    @ApiModelProperty(value = "是否生成前缀")
    @TableField("with_prefix")
    private Integer withPrefix;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

}
