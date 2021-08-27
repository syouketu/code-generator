package com.syouketu.modules.project.dto.input;

import com.syouketu.modules.common.dto.input.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

/**
 * 项目表配置表查询参数
 *
 * @author: JXI
 * @description:
 * @date: Created on 2019-07-08
 */
@AllArgsConstructor
@Builder
@Data
@ApiModel(description = "项目表配置表查询参数")
public class TableConfigQueryPara extends BasePageQuery {
    @ApiModelProperty(value = "项目表配置ID")
    private Integer id;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "数据表名")
    private String tableName;
}
