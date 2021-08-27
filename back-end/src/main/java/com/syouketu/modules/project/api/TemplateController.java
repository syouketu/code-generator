package com.syouketu.modules.project.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.syouketu.modules.common.dto.output.ApiResult;
import com.syouketu.modules.project.dto.input.TemplateQueryPara;
import com.syouketu.modules.project.dto.output.TemplateView;
import com.syouketu.modules.project.entity.Project;
import com.syouketu.modules.project.entity.Template;
import com.syouketu.modules.project.service.ITemplateService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 项目代码模板表 接口
 * </p>
 *
 * @author: JXI
 * @description:
 * @date: Created on 2019-07-08
 */
@RestController
@RequestMapping("/api/project/template")
@Api(description = "项目代码模板表接口")
public class TemplateController {

    @Autowired
    ITemplateService templateService;

    @RequestMapping(value = "/listPage", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取项目代码模板表列表分页", httpMethod = "POST", response = TemplateView.class, notes = "获取项目代码模板表列表分页")
    public ApiResult listPage(@RequestBody TemplateQueryPara filter) {
        Page<TemplateView> page = new Page<>(filter.getPage(), filter.getLimit());
        templateService.listPage(page, filter);
        return ApiResult.ok("获取项目代码模板表列表分页成功", page);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取项目代码模板表列表", httpMethod = "POST", response = TemplateView.class, notes = "获取项目代码模板表列表")
    public ApiResult list(@RequestBody TemplateQueryPara filter) {
        List<TemplateView> result = templateService.list(filter);
        return ApiResult.ok("获取项目代码模板表列表成功", result);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "保存项目代码模板表", httpMethod = "POST", response = ApiResult.class, notes = "保存项目代码模板表")
    public ApiResult save(@RequestBody Template input) {
        Integer id = templateService.save(input);
        return ApiResult.ok("保存项目代码模板表成功", id);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "删除项目代码模板表", httpMethod = "POST", response = ApiResult.class, notes = "删除项目代码模板表")
    public ApiResult delete(@RequestBody TemplateQueryPara input) {
        templateService.deleteById(input.getId());
        return ApiResult.ok("删除项目代码模板表成功");
    }

    @RequestMapping(value = "/getById", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取项目代码模板表信息", httpMethod = "POST", response = Template.class, notes = "获取项目代码模板表信息")
    public ApiResult getById(@RequestBody TemplateQueryPara input) {
        Template entity = templateService.selectById(input.getId());
        return ApiResult.ok("获取项目代码模板表信息成功", entity);
    }


    @RequestMapping(value = "/generateTemplate", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "生成项目代码模板", httpMethod = "POST", response = Project.class, notes = "生成项目代码模板")
    public ApiResult generateTemplate(@RequestBody TemplateQueryPara input) {
        templateService.generateTemplate(input.getProjectId());
        return ApiResult.ok("生成项目代码模板成功");
    }
}