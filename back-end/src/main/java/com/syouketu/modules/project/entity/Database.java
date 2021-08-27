package com.syouketu.modules.project.entity;

import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.annotations.TableId;
import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
@TableName("pj_database")
public class Database extends Model<Database> {

    private static final long serialVersionUID = 1L;

    /**
     * 项目数据库ID
     */
	@ApiModelProperty(value = "项目数据库ID")
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 所属项目ID
     */
	@ApiModelProperty(value = "所属项目ID")
	@TableField("project_id")
	private Integer projectId;
    /**
     * 数据库名称
     */
	@ApiModelProperty(value = "数据库名称")
	@TableField("name")
	private String name;
    /**
     * 数据库连接
     */
	@ApiModelProperty(value = "数据库连接")
	@TableField("url")
	private String url;
    /**
     * 用户名
     */
	@ApiModelProperty(value = "用户名")
	@TableField("user")
	private String user;
    /**
     * 密码
     */
	@ApiModelProperty(value = "密码")
	@TableField("passwd")
	private String passwd;
    /**
     * 数据库
     */
	@ApiModelProperty(value = "数据库")
	@TableField("db_schema")
	private String dbSchema;
    /**
     * 数据库类型1mysql 2oracle
     */
	@ApiModelProperty(value = "数据库类型1mysql 2oracle")
	@TableField("db_type")
	private Integer dbType;
	/**
	 * 驱动
	 */
	@ApiModelProperty(value = "驱动")
	@TableField("driver")
	private String driver;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
