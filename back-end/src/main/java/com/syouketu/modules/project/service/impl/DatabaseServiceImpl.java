package com.syouketu.modules.project.service.impl;

import com.baomidou.mybatisplus.generator.config.PackageConfig;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.syouketu.modules.project.dao.CodeJdbcDao;
import com.syouketu.modules.project.dto.input.DatabaseQueryPara;
import com.syouketu.modules.project.dto.input.TableConfigQueryPara;
import com.syouketu.modules.project.dto.model.TableInfo;
import com.syouketu.modules.project.dto.output.DatabaseView;
import com.syouketu.modules.project.entity.Database;
import com.syouketu.modules.project.entity.Project;
import com.syouketu.modules.project.entity.TableConfig;
import com.syouketu.modules.project.mapper.DatabaseMapper;
import com.syouketu.modules.project.mapper.ProjectMapper;
import com.syouketu.modules.project.mapper.TableConfigMapper;
import com.syouketu.modules.project.service.IDatabaseService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 项目数据库信息表 服务实现类
 * </p>
 *
 * @author jxi
 * @since 2019-07-08
 */
@Service
@Transactional
public class DatabaseServiceImpl extends ServiceImpl<DatabaseMapper, Database> implements IDatabaseService {

    @Autowired
    DatabaseMapper databaseMapper;

    @Autowired
    ProjectMapper projectMapper;

    @Autowired
    TableConfigMapper tableConfigMapper;

    @Autowired
    CodeJdbcDao codeJdbcDao;

    @Override
    public void listPage(Page<DatabaseView> page, DatabaseQueryPara filter) {
        page.setRecords(databaseMapper.selectDatabases(page, filter));
    }

    @Override
    public List<DatabaseView> list(DatabaseQueryPara filter) {
        return databaseMapper.selectDatabases(filter);
    }

    @Override
    public Integer save(Database para) {
        if (para.getId() != null) {
            databaseMapper.updateById(para);
        } else {
            databaseMapper.insert(para);
        }
        return para.getId();
    }

    @Override
    public List<TableInfo> tableList(DatabaseQueryPara filter) {
        Database database = selectById(filter.getId());
        return codeJdbcDao.getAllTables(database, filter.getTableName());
    }

    @Override
    public TableInfo columnList(DatabaseQueryPara filter) {
        Database database = selectById(filter.getId());
        TableInfo tableInfo = codeJdbcDao.getAllColumns(database, filter.getTableName());
        if (tableInfo != null && database != null) {
            tableInfo.setDataBaseId(database.getId());
            tableInfo.setProjectId(database.getProjectId());
            List<TableConfig> tableConfigList = tableConfigMapper.selectTableConfigs(TableConfigQueryPara.builder()
                    .projectId(database.getProjectId())
                    .tableName(filter.getTableName())
                    .build());
            if (!CollectionUtils.isEmpty(tableConfigList) && !StringUtils.isEmpty(tableConfigList.get(0).getQueryColumns())) {
                tableInfo.setQueryColumns(tableConfigList.get(0).getQueryColumns().split(","));
            }
            Project project = projectMapper.selectById(database.getProjectId());
            TableInfo.PackageConfig packageConfig = new TableInfo.PackageConfig();
            packageConfig.setParent(project.getParentPackage());
            packageConfig.setModuleName(project.getModuleName());
            packageConfig.setEntity(project.getEntity());
            packageConfig.setMapper(project.getMapper());
            packageConfig.setXml(project.getMapperXml());
            packageConfig.setService(project.getService());
            packageConfig.setServiceImpl(project.getServiceImpl());
            packageConfig.setController(project.getController());
            tableInfo.setPackageConfig(packageConfig);
        }
        return tableInfo;
    }

    @Override
    public void saveTable(TableInfo input) {
        Database database = databaseMapper.selectById(input.getDataBaseId());
        if (database != null) {
            codeJdbcDao.saveComment(input, database);
        }
    }
}
