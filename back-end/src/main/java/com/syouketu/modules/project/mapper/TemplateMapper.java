package com.syouketu.modules.project.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.syouketu.modules.project.dto.input.TemplateQueryPara;
import com.syouketu.modules.project.dto.output.TemplateView;
import com.syouketu.modules.project.entity.Template;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 项目代码模板表 Mapper 接口
 * </p>
 *
 * @author xiaojie
 * @since 2019-07-08
 */
public interface TemplateMapper extends BaseMapper<Template> {

    /**
     * 列表分页
     *
     * @param page
     * @param filter
     * @return
     */
    List<TemplateView> selectTemplates(Pagination page, @Param("filter") TemplateQueryPara filter);

    /**
     * 列表
     *
     * @param filter
     * @return
     */
    List<TemplateView> selectTemplates(@Param("filter") TemplateQueryPara filter);
}