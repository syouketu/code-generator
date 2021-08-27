package com.syouketu.modules.common.generator;

import com.baomidou.mybatisplus.generator.config.po.TableInfo;
import lombok.Data;

/**
 *
 */
@Data
public class MyTableInfo extends TableInfo {

    private String htmlName;

    private String queryFormName;

    private String saveFormName;

    private String queryVoName;
}
