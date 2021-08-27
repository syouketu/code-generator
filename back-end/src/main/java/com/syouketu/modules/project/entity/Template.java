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
 * 项目代码模板表
 * </p>
 *
 * @author jxi
 * @since 2019-07-08
 */
@Data
@ApiModel(description = "项目代码模板表")
@TableName("pj_template")
public class Template extends Model<Template> {

    private static final long serialVersionUID = 1L;

    /**
     * 模板ID
     */
	@ApiModelProperty(value = "模板ID")
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
    /**
     * 项目ID
     */
	@ApiModelProperty(value = "项目ID")
	@TableField("project_id")
	private Integer projectId;
    /**
     * 模板类型
     */
	@ApiModelProperty(value = "模板类型")
	@TableField("type")
	private Integer type;
    /**
     * 模板内容
     */
	@ApiModelProperty(value = "模板内容")
	@TableField("content")
	private String content;

	@Override
	protected Serializable pkVal() {
		return this.id;
	}

}
