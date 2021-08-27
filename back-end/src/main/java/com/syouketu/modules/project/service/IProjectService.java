package com.syouketu.modules.project.service;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.syouketu.modules.project.dto.input.CodeGenerateInput;
import com.syouketu.modules.project.dto.input.ProjectQueryPara;
import com.syouketu.modules.project.entity.Project;

import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 项目表 服务类
 * </p>
 *
 * @author jxi
 * @since 2019-07-08
 */
public interface IProjectService extends IService<Project> {

    /**
     * 项目表列表分页
     *
     * @param page
     * @param filter
     * @return
     */
    void listPage(Page<Project> page, ProjectQueryPara filter);

    /**
     * 保存项目表
     *
     * @param input
     */
    Integer save(Project input);

    /**
     * 项目表列表
     *
     * @param filter
     * @return
     */
    List<Project> list(ProjectQueryPara filter);

    /**
     * 生成代码
     *
     * @param input
     * @return
     */
    String generateCode(CodeGenerateInput input) throws IOException;
}
