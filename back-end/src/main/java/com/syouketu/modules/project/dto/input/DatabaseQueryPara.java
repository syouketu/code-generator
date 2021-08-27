package com.syouketu.modules.project.dto.input;

import com.syouketu.modules.common.dto.input.BasePageQuery;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 项目数据库信息表查询参数
 *
 * @author: JXI
 * @description:
 * @date: Created on 2019-07-08
 */
@Data
@ApiModel(description = "项目数据库信息表查询参数")
public class DatabaseQueryPara extends BasePageQuery {
    @ApiModelProperty(value = "数据库ID")
    private Integer id;

    @ApiModelProperty(value = "项目ID")
    private Integer projectId;

    @ApiModelProperty(value = "数据表名")
    private String tableName;
}
