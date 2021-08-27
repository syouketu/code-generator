package com.syouketu.modules.system.dto.input;

import com.syouketu.modules.common.dto.input.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 默认代码模板表查询参数
 *
 * @author: JXI
 * @description:
 * @date: Created on 2019-07-08
 */
@Data
@ApiModel(description = "默认代码模板表查询参数")
public class BsTemplateQueryPara extends BasePageQuery {
    @ApiModelProperty(value = "id")
    private Integer id;
}
