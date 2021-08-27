package com.syouketu.modules.project.mapper;

import com.syouketu.modules.project.dto.output.DatabaseView;
import com.syouketu.modules.project.entity.Database;
import com.syouketu.modules.project.dto.input.DatabaseQueryPara;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 项目数据库信息表 Mapper 接口
 * </p>
 *
 * @author xiaojie
 * @since 2019-07-08
 */
public interface DatabaseMapper extends BaseMapper<Database> {

    /**
     * 列表分页
     *
     * @param page
     * @param filter
     * @return
     */
    List<DatabaseView> selectDatabases(Pagination page, @Param("filter") DatabaseQueryPara filter);

    /**
     * 列表
     *
     * @param filter
     * @return
     */
    List<DatabaseView> selectDatabases(@Param("filter") DatabaseQueryPara filter);

    /**
     * 根据数字库名检索信息
     *
     * @param dbName
     * @return
     */
    Database selectByName(String dbName);
}