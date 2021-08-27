package com.syouketu.modules.project.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.syouketu.modules.common.dto.output.ApiResult;
import com.syouketu.modules.project.dto.input.CodeGenerateInput;
import com.syouketu.modules.project.dto.input.ProjectQueryPara;
import com.syouketu.modules.project.entity.Project;
import com.syouketu.modules.project.service.IProjectService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 项目表 接口
 * </p>
 *
 * @author: JXI
 * @description:
 * @date: Created on 2019-07-08
 */
@RestController
@RequestMapping("/api/project/project")
@Api(description = "项目表接口")
public class ProjectController {

    @Autowired
    IProjectService projectService;

    @RequestMapping(value = "/listPage", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取项目表列表分页", httpMethod = "POST", response = Project.class, notes = "获取项目表列表分页")
    public ApiResult listPage(@RequestBody ProjectQueryPara filter) {
        Page<Project> page = new Page<>(filter.getPage(), filter.getLimit());
        projectService.listPage(page, filter);
        return ApiResult.ok("获取项目表列表分页成功", page);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取项目表列表", httpMethod = "POST", response = Project.class, notes = "获取项目表列表")
    public ApiResult list(@RequestBody ProjectQueryPara filter) {
        List result = projectService.list(filter);
        return ApiResult.ok("获取项目表列表成功", result);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "保存项目表", httpMethod = "POST", response = ApiResult.class, notes = "保存项目表")
    public ApiResult save(@RequestBody Project input) {
        Integer id = projectService.save(input);
        return ApiResult.ok("保存项目表成功", id);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "删除项目表", httpMethod = "POST", response = ApiResult.class, notes = "删除项目表")
    public ApiResult delete(@RequestBody ProjectQueryPara input) {
        projectService.deleteById(input.getId());
        return ApiResult.ok("删除项目表成功");
    }

    @RequestMapping(value = "/generate", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "生成代码", httpMethod = "POST", response = ApiResult.class, notes = "生成代码")
    public ApiResult generate(@RequestBody CodeGenerateInput input) throws IOException {
        //代码生成到指定目录
        return ApiResult.ok("生成代码成功", projectService.generateCode(input));
    }
}