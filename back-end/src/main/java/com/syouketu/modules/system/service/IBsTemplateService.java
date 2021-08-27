package com.syouketu.modules.system.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.syouketu.modules.project.dto.input.TemplateQueryPara;
import com.syouketu.modules.project.entity.Template;
import com.syouketu.modules.system.dto.input.BsTemplateQueryPara;
import com.syouketu.modules.system.entity.BsTemplate;

import java.util.List;

/**
 * <p>
 * 项目代码模板表 服务类
 * </p>
 *
 * @author jxi
 * @since 2019-07-08
 */
public interface IBsTemplateService extends IService<BsTemplate> {

    /**
     * 项目代码模板表列表分页
     *
     * @param page
     * @param filter
     * @return
     */
    void listPage(Page<BsTemplate> page, BsTemplateQueryPara filter);

    /**
     * 保存项目代码模板表
     *
     * @param input
     */
    Integer save(BsTemplate input);

    /**
     * 项目代码模板表列表
     *
     * @param filter
     * @return
     */
    List<BsTemplate> list(BsTemplateQueryPara filter);
}
