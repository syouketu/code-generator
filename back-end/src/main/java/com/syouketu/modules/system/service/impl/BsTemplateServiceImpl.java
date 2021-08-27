package com.syouketu.modules.system.service.impl;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.syouketu.modules.system.dto.input.BsTemplateQueryPara;
import com.syouketu.modules.system.entity.BsTemplate;
import com.syouketu.modules.system.mapper.BsTemplateMapper;
import com.syouketu.modules.system.service.IBsTemplateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
public class BsTemplateServiceImpl extends ServiceImpl<BsTemplateMapper, BsTemplate> implements IBsTemplateService {

    @Autowired
    BsTemplateMapper bsTemplateMapper;

    @Override
    public void listPage(Page<BsTemplate> page, BsTemplateQueryPara filter) {
        page.setRecords(bsTemplateMapper.selectBsTemplates(page, filter));
    }

    @Override
    public List<BsTemplate> list(BsTemplateQueryPara filter) {
        return bsTemplateMapper.selectBsTemplates(filter);
    }

    @Override
    public Integer save(BsTemplate para) {
        if (para.getId() != null) {
            bsTemplateMapper.updateById(para);
        } else {
            bsTemplateMapper.insert(para);
        }
        return para.getId();
    }
}
