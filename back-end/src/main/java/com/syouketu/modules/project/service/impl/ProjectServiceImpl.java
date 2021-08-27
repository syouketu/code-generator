package com.syouketu.modules.project.service.impl;

import com.baomidou.mybatisplus.enums.IdType;
import com.baomidou.mybatisplus.generator.config.DataSourceConfig;
import com.baomidou.mybatisplus.generator.config.GlobalConfig;
import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.generator.config.StrategyConfig;
import com.baomidou.mybatisplus.generator.config.rules.DbType;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.syouketu.modules.common.enumeration.EnumDatabaseType;
import com.syouketu.modules.common.generator.MyGenerator;
import com.syouketu.modules.common.service.IFileService;
import com.syouketu.modules.project.dto.input.CodeGenerateInput;
import com.syouketu.modules.project.dto.input.ProjectQueryPara;
import com.syouketu.modules.project.dto.input.TableConfigQueryPara;
import com.syouketu.modules.project.dto.input.TemplateQueryPara;
import com.syouketu.modules.project.dto.model.TableInfo;
import com.syouketu.modules.project.dto.output.TemplateView;
import com.syouketu.modules.project.entity.Database;
import com.syouketu.modules.project.entity.Project;
import com.syouketu.modules.project.entity.TableConfig;
import com.syouketu.modules.project.mapper.DatabaseMapper;
import com.syouketu.modules.project.mapper.ProjectMapper;
import com.syouketu.modules.project.mapper.TableConfigMapper;
import com.syouketu.modules.project.mapper.TemplateMapper;
import com.syouketu.modules.project.service.IProjectService;
import com.syouketu.utils.FileUtils;
import com.syouketu.utils.ZipFileUtils;
import org.apache.tools.zip.ZipOutputStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * 项目表 服务实现类
 * </p>
 *
 * @author jxi
 * @since 2019-07-08
 */
@Service
@Transactional
public class ProjectServiceImpl extends ServiceImpl<ProjectMapper, Project> implements IProjectService {

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    DatabaseMapper databaseMapper;

    @Autowired
    TableConfigMapper tableConfigMapper;

    @Autowired
    TemplateMapper templateMapper;

    @Autowired
    IFileService fileService;

    @Override
    public void listPage(Page<Project> page, ProjectQueryPara filter) {
        page.setRecords(projectMapper.selectProjects(page, filter));
    }

    @Override
    public List<Project> list(ProjectQueryPara filter) {
        return projectMapper.selectProjects(filter);
    }

    @Override
    public Integer save(Project para) {
        if (para.getId() != null) {
            projectMapper.updateById(para);
        } else {
            projectMapper.insert(para);
        }
        return para.getId();
    }

    @Override
    public String generateCode(CodeGenerateInput input) throws IOException {
        Database database = databaseMapper.selectById(input.getTableInfo().getDataBaseId());
        TableConfig tableConfig = saveTableConfig(database, input.getTableInfo());
        generate(database, input.getTableInfo(), input.getPackageConfig(), tableConfig);
        return zipCode();
    }

    private TableConfig saveTableConfig(Database database, TableInfo tableInfo) {
        if (tableInfo.getQueryColumns() == null || tableInfo.getQueryColumns().length <= 0) {
            return null;
        }

        StringBuilder sb = new StringBuilder();
        int size = tableInfo.getQueryColumns().length;
        for (int i = 0; i < size; i++) {
            if (i == size - 1) {
                sb.append(tableInfo.getQueryColumns()[i]);
            } else {
                sb.append(tableInfo.getQueryColumns()[i]).append(",");
            }
        }

        List<TableConfig> tableConfigList = tableConfigMapper.selectTableConfigs(TableConfigQueryPara.builder()
                .projectId(database.getProjectId())
                .tableName(tableInfo.getTableName())
                .build());
        TableConfig tableConfig;
        if (CollectionUtils.isEmpty(tableConfigList)) {
            tableConfig = new TableConfig();
            tableConfig.setProjectId(database.getProjectId());
            tableConfig.setTableName(tableInfo.getTableName());
            tableConfig.setQueryColumns(sb.toString());
            tableConfigMapper.insert(tableConfig);
        } else {
            tableConfig = tableConfigList.get(0);
            tableConfig.setQueryColumns(sb.toString());
            tableConfigMapper.updateAllColumnById(tableConfig);
        }
        return tableConfig;
    }

    /**
     * 调用代码生成器引擎生成代码
     *
     * @param database
     * @param tableInfo
     * @param packageConfig
     */
    private void generate(Database database, TableInfo tableInfo, PackageConfig packageConfig, TableConfig tableConfig) {
        Project project = projectMapper.selectById(database.getProjectId());
        List<TemplateView> templateList = templateMapper.selectTemplates(TemplateQueryPara.builder().projectId(database.getProjectId()).build());
        String model = tableInfo.getComments().substring(tableInfo.getComments().indexOf("#") + 1);
        MyGenerator mpg = new MyGenerator();
        // 全局配置
        GlobalConfig gc = new GlobalConfig();
        gc.setOutputDir("/tmp/upload/code");
        gc.setFileOverride(true);
        gc.setActiveRecord(true);
        gc.setEnableCache(false);// XML 二级缓存
        gc.setBaseResultMap(true);// XML ResultMap
        gc.setBaseColumnList(false);// XML columList
        gc.setAuthor("jxi");
        gc.setOpen(false);
        gc.setIdType(IdType.AUTO);
        mpg.setGlobalConfig(gc);
        DataSourceConfig dsc = new DataSourceConfig();
        EnumDatabaseType enumDatabaseType = EnumDatabaseType.getEnum(database.getDbType());
        switch (enumDatabaseType) {
            case MySQL:
                dsc.setDbType(DbType.MYSQL);
                break;
            case Oracle:
                dsc.setDbType(DbType.ORACLE);
                break;
        }
        dsc.setDriverName(database.getDriver());
        dsc.setUsername(database.getUser());
        dsc.setPassword(database.getPasswd());
        dsc.setUrl(database.getUrl());
        //数据源信息
        mpg.setDataSource(dsc);
        // 策略配置
        StrategyConfig strategy = new StrategyConfig();
        String tablePrefix = "";
        if (project != null && 0 == project.getWithPrefix()) {
            if (tableInfo.getTableName().contains("T_")) {
                tablePrefix = tableInfo.getTableName().replace("T_", "");
                tablePrefix = "T_" + tablePrefix.substring(0, tablePrefix.indexOf("_"));

            } else if (tableInfo.getTableName().contains("_")) {
                tablePrefix = tableInfo.getTableName();
                tablePrefix = tablePrefix.substring(0, tablePrefix.indexOf("_"));
            }
        }
        strategy.setTablePrefix(tablePrefix);

        strategy.entityTableFieldAnnotationEnable(true);
        strategy.setNaming(NamingStrategy.underline_to_camel);// 表名生成策略
        strategy.setInclude(tableInfo.getTableName()); // 需要生成的表
        strategy.setDbColumnUnderline(true);//表名和字段名是否使用下划线命名
        mpg.setStrategy(strategy);
        // 包配置
        mpg.setPackageInfo(packageConfig);
        //模板列表
        mpg.setTemplateList(templateList);
        //生成页面配置参数
        mpg.setMyTableConfig(tableConfig);
        // 执行生成
        mpg.execute();
    }

    /**
     * 下载生成好的代码
     */
    private String zipCode() {
        try {
            String outZipFileName = "/tmp/upload/src.zip";
            File zipFile = new File(outZipFileName);
            if (!zipFile.exists()) {
                zipFile.createNewFile();
            }
            ZipFileUtils zip = new ZipFileUtils();
            ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(zipFile));
            String fileName = "/tmp/upload/code";
            File ff = new File(fileName);
            if (!ff.exists()) {
                ff.mkdirs();
            }
            zip.zip(ff, zos, "");

            zos.flush();
            zos.close();

            //删除目录
            FileUtils.DeleteFolder(fileName);
            return fileService.uploadFile("code.zip", zipFile);
//            return myProperties.getUpload().getStaticDomain() + outZipFileName;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
