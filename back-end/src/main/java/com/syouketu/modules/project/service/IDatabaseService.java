package com.syouketu.modules.project.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.syouketu.modules.project.dto.input.DatabaseQueryPara;
import com.syouketu.modules.project.dto.model.TableInfo;
import com.syouketu.modules.project.dto.output.DatabaseView;
import com.syouketu.modules.project.entity.Database;

import java.util.List;

/**
 * <p>
 * 项目数据库信息表 服务类
 * </p>
 *
 * @author jxi
 * @since 2019-07-08
 */
public interface IDatabaseService extends IService<Database> {

    /**
     * 项目数据库信息表列表分页
     *
     * @param page
     * @param filter
     * @return
     */
    void listPage(Page<DatabaseView> page, DatabaseQueryPara filter);

    /**
     * 保存项目数据库信息表
     *
     * @param input
     */
    Integer save(Database input);

    /**
     * 项目数据库信息表列表
     *
     * @param filter
     * @return
     */
    List<DatabaseView> list(DatabaseQueryPara filter);

    /**
     * 数据库表列表
     *
     * @param filter
     * @return
     */
    List<TableInfo> tableList(DatabaseQueryPara filter);

    /**
     * 获取数据表字段信息
     *
     * @param filter
     * @return
     */
    TableInfo columnList(DatabaseQueryPara filter);

    /**
     * 保存表信息
     *
     * @param input
     */
    void saveTable(TableInfo input);
}
