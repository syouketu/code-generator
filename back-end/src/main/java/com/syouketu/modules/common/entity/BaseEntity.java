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
public abstract class BaseEntity<T extends Model> extends BaseAddEntity {

    @TableField(value = "date_modify", fill = FieldFill.INSERT_UPDATE)
    protected Date dateModify; // 更新日期

}
