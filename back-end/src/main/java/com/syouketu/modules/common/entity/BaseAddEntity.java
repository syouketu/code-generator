package com.syouketu.modules.common.entity;

import com.baomidou.mybatisplus.activerecord.Model;
import com.baomidou.mybatisplus.annotations.TableField;
import com.baomidou.mybatisplus.enums.FieldFill;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * @author: JXI
 * @description:
 * @date: Created on 2019/1/28.
 */
@Getter
@Setter
public abstract class BaseAddEntity<T extends Model> extends Model {

    @TableField(value = "date_add", fill = FieldFill.INSERT)
    protected Date dateAdd; // 创建日期

}
