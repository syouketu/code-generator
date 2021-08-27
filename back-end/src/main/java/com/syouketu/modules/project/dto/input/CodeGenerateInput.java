package com.syouketu.modules.project.dto.input;

import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.syouketu.modules.project.dto.model.TableInfo;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * 代码生成传入参数
 *
 * @author: JXI
 * @description:
 * @date: Created on 2018/4/9
 */
@Data
@ApiModel(description = "代码生成传入参数")
public class CodeGenerateInput {

    @ApiModelProperty(value = "表信息")
    private TableInfo tableInfo;

    @ApiModelProperty(value = "包配置")
    private PackageConfig packageConfig;

}
