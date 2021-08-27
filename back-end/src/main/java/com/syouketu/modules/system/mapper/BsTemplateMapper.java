package com.syouketu.modules.system.mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;
import com.syouketu.modules.system.dto.input.BsTemplateQueryPara;
import com.syouketu.modules.system.entity.BsTemplate;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 * 默认模板表 Mapper 接口
 * </p>
 *
 * @author xiaojie
 * @since 2019-07-08
 */
public interface BsTemplateMapper extends BaseMapper<BsTemplate> {

    /**
     * 列表分页
     *
     * @param page
     * @param filter
     * @return
     */
    List<BsTemplate> selectBsTemplates(Pagination page, @Param("filter") BsTemplateQueryPara filter);

    /**
     * 列表
     *
     * @param filter
     * @return
     */
    List<BsTemplate> selectBsTemplates(@Param("filter") BsTemplateQueryPara filter);
}