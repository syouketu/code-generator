package com.syouketu.modules.project.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.annotations.TableName;
import com.baomidou.mybatisplus.enums.IdType;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * <p>
 * 项目数据库信息表
 * </p>
 *
 * @author jxi
 * @since 2019-07-08
 */
@Data
@ApiModel(description = "项目数据库信息表")
@TableName("pj_table_config")
public class TableConfig extends Model<TableConfig> {

    private static final long serialVersionUID = 1L;

    /**
     * ID
     */
	@ApiModelProperty(value = "ID")
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 所属项目ID
     */
	@ApiModelProperty(value = "所属项目ID")
	@TableField("project_id")
	private Integer projectId;
    /**
     * 表名
     */
	@ApiModelProperty(value = "表名")
	@TableField("table_name")
	private String tableName;
    /**
     * 用户检索的字段
     */
	@ApiModelProperty(value = "用户检索的字段")
	@TableField("query_columns")
	private String queryColumns;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
