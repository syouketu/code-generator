package com.syouketu.modules.project.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.syouketu.modules.project.dto.input.TableConfigQueryPara;
import com.syouketu.modules.project.entity.TableConfig;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 项目数据表配置信息表 Mapper 接口
 * </p>
 *
 * @author xiaojie
 * @since 2019-07-08
 */
public interface TableConfigMapper extends BaseMapper<TableConfig> {

    /**
     * 列表分页
     *
     * @param page
     * @param filter
     * @return
     */
    List<TableConfig> selectTableConfigs(Pagination page, @Param("filter") TableConfigQueryPara filter);

    /**
     * 列表
     *
     * @param filter
     * @return
     */
    List<TableConfig> selectTableConfigs(@Param("filter") TableConfigQueryPara filter);

}