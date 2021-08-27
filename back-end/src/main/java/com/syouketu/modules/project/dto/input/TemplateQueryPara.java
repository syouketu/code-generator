package com.syouketu.modules.project.dto.input;

import com.syouketu.modules.common.dto.input.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 项目代码模板表查询参数
 *
 * @author: JXI
 * @description:
 * @date: Created on 2019-07-08
 */
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@ApiModel(description = "项目代码模板表查询参数")
public class TemplateQueryPara extends BasePageQuery {
    @ApiModelProperty(value = "模板ID")
    private Integer id;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;
}
