package com.syouketu.modules.system.entity;

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
 * 项目代码模板表
 * </p>
 *
 * @author jxi
 * @since 2019-07-08
 */
@Data
@ApiModel(description = "项目代码模板表")
@TableName("bs_template")
public class BsTemplate extends Model<BsTemplate> {

    private static final long serialVersionUID = 1L;

    /**
     * 模板ID
     */
	@ApiModelProperty(value = "模板ID")
	@TableId(value="id", type= IdType.AUTO)
	private Integer id;
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
