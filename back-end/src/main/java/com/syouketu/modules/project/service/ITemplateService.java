package com.syouketu.modules.project.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.syouketu.modules.project.dto.output.TemplateView;
import com.syouketu.modules.project.entity.Template;
import com.syouketu.modules.project.dto.input.TemplateQueryPara;

import java.util.List;

/**
 * <p>
 * 项目代码模板表 服务类
 * </p>
 *
 * @author jxi
 * @since 2019-07-08
 */
public interface ITemplateService extends IService<Template> {

    /**
     * 项目代码模板表列表分页
     *
     * @param page
     * @param filter
     * @return
     */
    void listPage(Page<TemplateView> page, TemplateQueryPara filter);

    /**
     * 保存项目代码模板表
     *
     * @param input
     */
    Integer save(Template input);

    /**
     * 项目代码模板表列表
     *
     * @param filter
     * @return
     */
    List<TemplateView> list(TemplateQueryPara filter);

    /**
     * 生成项目代码模板
     *
     * @param projectId
     */
    void generateTemplate(Integer projectId);
}
