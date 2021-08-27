package com.syouketu.modules.project.dto.output;

import com.syouketu.modules.project.entity.Template;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 项目模板表展示师徒
 *
 * @author: JXI
 * @description:
 * @date: Created on 2019-07-08
 */
@Data
@ApiModel(description = "项目模板表展示师徒")
public class TemplateView extends Template {

    @ApiModelProperty(value = "项目名")
    private String project;
}
