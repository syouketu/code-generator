package com.syouketu.modules.project.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.syouketu.modules.project.dto.input.TemplateQueryPara;
import com.syouketu.modules.project.dto.output.TemplateView;
import com.syouketu.modules.project.entity.Template;
import com.syouketu.modules.project.mapper.TemplateMapper;
import com.syouketu.modules.project.service.ITemplateService;
import com.syouketu.modules.system.entity.BsTemplate;
import com.syouketu.modules.system.mapper.BsTemplateMapper;
import com.syouketu.utils.BusinessException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * <p>
 * 项目代码模板表 服务实现类
 * </p>
 *
 * @author jxi
 * @since 2019-07-08
 */
@Service
@Transactional
public class TemplateServiceImpl extends ServiceImpl<TemplateMapper, Template> implements ITemplateService {

    @Autowired
    TemplateMapper templateMapper;

    @Autowired
    BsTemplateMapper bsTemplateMapper;

    @Override
    public void listPage(Page<TemplateView> page, TemplateQueryPara filter) {
        page.setRecords(templateMapper.selectTemplates(page, filter));
    }

    @Override
    public List<TemplateView> list(TemplateQueryPara filter) {
        return templateMapper.selectTemplates(filter);
    }

    @Override
    public Integer save(Template para) {
        if (para.getId() != null) {
            templateMapper.updateById(para);
        } else {
            templateMapper.insert(para);
        }
        return para.getId();
    }

    @Override
    public void generateTemplate(Integer projectId) {
        if (projectId == null) {
            throw new BusinessException("请先选择项目！");
        }
        List<BsTemplate> bsTemplates = bsTemplateMapper.selectList(null);
        if (!CollectionUtils.isEmpty(bsTemplates)) {
            bsTemplates.forEach(bsTemplate -> {
                Template template = new Template();
                template.setProjectId(projectId);
                template.setType(bsTemplate.getType());
                template.setContent(bsTemplate.getContent());
                templateMapper.insert(template);
            });
        }
    }
}
