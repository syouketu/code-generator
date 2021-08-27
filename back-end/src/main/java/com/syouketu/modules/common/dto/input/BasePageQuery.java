package com.syouketu.modules.common.dto.input;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 基础分页检索参数
 *
 * @author: JXI
 * @description:
 * @date: Created on 2017/12/13.
 */
@Data
@ApiModel
public class BasePageQuery {
    @ApiModelProperty(value = "当前页")
    private Integer page;
    @ApiModelProperty(value = "每页显示数量")
    private Integer limit;
}
