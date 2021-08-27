package com.syouketu.config;

import com.baomidou.mybatisplus.mapper.MetaObjectHandler;
import org.apache.ibatis.reflection.MetaObject;
import org.springframework.stereotype.Component;

import java.util.Date;

/**
 * 自定义填充公共 name 字段
 */
@Component
public class MyMetaObjectHandler extends MetaObjectHandler {


    /**
     * 创建时间
     */
    @Override
    public void insertFill(MetaObject metaObject) {
        if (metaObject.hasGetter("dateAdd") && metaObject.hasGetter("dateModify")) {
            setFieldValByName("dateAdd", new Date(), metaObject);
            setFieldValByName("dateModify", new Date(), metaObject);
        }
    }

    /**
     * 最后一次更新时间
     */
    @Override
    public void updateFill(MetaObject metaObject) {
        if (metaObject.hasGetter("dateModify")) {
            setFieldValByName("dateModify", new Date(), metaObject);
        }
    }
}