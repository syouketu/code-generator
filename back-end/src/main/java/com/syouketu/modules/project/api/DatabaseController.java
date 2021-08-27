package com.syouketu.modules.project.api;

import com.baomidou.mybatisplus.plugins.Page;
import com.syouketu.modules.common.dto.output.ApiResult;
import com.syouketu.modules.project.dto.input.DatabaseQueryPara;
import com.syouketu.modules.project.dto.model.TableInfo;
import com.syouketu.modules.project.dto.output.DatabaseView;
import com.syouketu.modules.project.entity.Database;
import com.syouketu.modules.project.service.IDatabaseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 项目数据库信息表 接口
 * </p>
 *
 * @author: JXI
 * @description:
 * @date: Created on 2019-07-08
 */
@RestController
@RequestMapping("/api/project/database")
@Api(description = "项目数据库信息表接口")
public class DatabaseController {

    @Autowired
    IDatabaseService databaseService;

    @RequestMapping(value = "/listPage", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取项目数据库信息表列表分页", httpMethod = "POST", response = DatabaseView.class, notes = "获取项目数据库信息表列表分页")
    public ApiResult listPage(@RequestBody DatabaseQueryPara filter) {
        Page<DatabaseView> page = new Page<>(filter.getPage(), filter.getLimit());
        databaseService.listPage(page, filter);
        return ApiResult.ok("获取项目数据库信息表列表分页成功", page);
    }

    @RequestMapping(value = "/list", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "获取项目数据库信息表列表", httpMethod = "POST", response = DatabaseView.class, notes = "获取项目数据库信息表列表")
    public ApiResult list(@RequestBody DatabaseQueryPara filter) {
        List<DatabaseView> result = databaseService.list(filter);
        return ApiResult.ok("获取项目数据库信息表列表成功", result);
    }

    @RequestMapping(value = "/save", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "保存项目数据库信息表", httpMethod = "POST", response = ApiResult.class, notes = "保存项目数据库信息表")
    public ApiResult save(@RequestBody Database input) {
        Integer id = databaseService.save(input);
        return ApiResult.ok("保存项目数据库信息表成功", id);
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "删除项目数据库信息表", httpMethod = "POST", response = ApiResult.class, notes = "删除项目数据库信息表")
    public ApiResult delete(@RequestBody DatabaseQueryPara input) {
        databaseService.deleteById(input.getId());
        return ApiResult.ok("删除项目数据库信息表成功");
    }

    @RequestMapping(value = "/tableList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "数据表分页列表", httpMethod = "POST", response = TableInfo.class, notes = "数据库分页列表")
    public ApiResult tableList(@RequestBody(required = false) DatabaseQueryPara filter) throws IOException {
        List<TableInfo> tableInfoList = databaseService.tableList(filter);
        return ApiResult.ok("获取数据表分页列表成功", tableInfoList);
    }

    @RequestMapping(value = "/columnList", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "表字段列表", httpMethod = "POST", response = TableInfo.class, notes = "表字段列表")
    public ApiResult columnList(@RequestBody(required = false) DatabaseQueryPara filter) throws IOException {
        TableInfo tableInfo = databaseService.columnList(filter);
        return ApiResult.ok("获取表字段成功", tableInfo);
    }

    @RequestMapping(value = "/saveTable", method = RequestMethod.POST, produces = "application/json;charset=utf-8")
    @ResponseBody
    @ApiOperation(value = "保存表字段信息", httpMethod = "POST", response = TableInfo.class, notes = "保存表字段信息")
    public ApiResult saveTable(@RequestBody(required = false) TableInfo input) throws IOException {
        databaseService.saveTable(input);
        return ApiResult.ok("保存表字段信息成功");
    }
}