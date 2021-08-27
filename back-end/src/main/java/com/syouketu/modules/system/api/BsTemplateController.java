package com.syouketu.modules.system.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.syouketu.modules.common.dto.output.ApiResult;
import com.syouketu.modules.system.dto.input.BsTemplateQueryPara;
import com.syouketu.modules.system.entity.BsTemplate;
import com.syouketu.modules.system.service.IBsTemplateService;
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
@RequestMapping("/api/system/bsTemplate")
@Api(description = "项目代码模板表接口")
public class BsTemplateController {

    @Autowired
    IBsTemplateService bsTemplateService;

    @RequestMapping(value = "/listPage", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取项目代码模板表列表分页", httpMethod = "POST", response = BsTemplate.class, notes = "获取项目代码模板表列表分页")
    public ApiResult listPage(@RequestBody BsTemplateQueryPara filter) {
        Page<BsTemplate> page = new Page<>(filter.getPage(), filter.getLimit());
        bsTemplateService.listPage(page, filter);
        return ApiResult.ok("获取项目代码模板表列表分页成功", page);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取项目代码模板表列表", httpMethod = "POST", response = BsTemplate.class, notes = "获取项目代码模板表列表")
    public ApiResult list(@RequestBody BsTemplateQueryPara filter) {
        List result = bsTemplateService.list(filter);
        return ApiResult.ok("获取项目代码模板表列表成功", result);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "保存项目代码模板表", httpMethod = "POST", response = ApiResult.class, notes = "保存项目代码模板表")
    public ApiResult save(@RequestBody BsTemplate input) {
        Integer id = bsTemplateService.save(input);
        return ApiResult.ok("保存项目代码模板表成功", id);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "删除项目代码模板表", httpMethod = "POST", response = ApiResult.class, notes = "删除项目代码模板表")
    public ApiResult delete(@RequestBody BsTemplateQueryPara input) {
        bsTemplateService.deleteById(input.getId());
        return ApiResult.ok("删除项目代码模板表成功");
    }

    @RequestMapping(value = "/getById", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取项目代码模板表信息", httpMethod = "POST", response = BsTemplate.class, notes = "获取项目代码模板表信息")
    public ApiResult getById(@RequestBody BsTemplateQueryPara input) {
        BsTemplate entity = bsTemplateService.selectById(input.getId());
        return ApiResult.ok("获取项目代码模板表信息成功", entity);
    }
}