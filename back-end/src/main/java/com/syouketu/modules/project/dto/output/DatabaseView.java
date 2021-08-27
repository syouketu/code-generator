package com.syouketu.modules.project.dto.output;

import com.syouketu.modules.project.entity.Database;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 项目数据库信息展示视图
 *
 * @author: JXI
 * @description:
 * @date: Created on 2019-07-08
 */
@Data
@ApiModel(description = "项目数据库信息展示视图")
public class DatabaseView extends Database {

    @ApiModelProperty(value = "项目名")
    private String project;
}
