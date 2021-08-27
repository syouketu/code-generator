package com.syouketu.modules.project.mapper;

import com.syouketu.modules.project.entity.Project;
import com.syouketu.modules.project.dto.input.ProjectQueryPara;
import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

/**
 * <p>
  * 项目表 Mapper 接口
 * </p>
 *
 * @author xiaojie
 * @since 2019-07-08
 */
public interface ProjectMapper extends BaseMapper<Project> {

    /**
     * 列表分页
     *
     * @param page
     * @param filter
     * @return
     */
    List<Project> selectProjects(Pagination page, @Param("filter") ProjectQueryPara filter);

    /**
     * 列表
     *
     * @param filter
     * @return
     */
    List<Project> selectProjects(@Param("filter") ProjectQueryPara filter);
}