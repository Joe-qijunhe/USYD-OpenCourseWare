package org.joe.ocw.service.edu.entity;

import com.baomidou.mybatisplus.annotation.*;
import org.joe.ocw.service.base.model.BaseEntity;

import java.util.Date;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 讲师
 * </p>
 *
 * @author Joe He
 * @since 2021-12-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
// 指定表名，不指定的话默认是teacher表
@TableName("edu_teacher")
@ApiModel(value = "Teacher对象", description = "讲师")
public class Teacher extends BaseEntity {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "讲师姓名", example = "何老师")
    private String name;

    @ApiModelProperty(value = "讲师简介")
    private String intro;

    @ApiModelProperty(value = "讲师资历,一句话说明讲师")
    private String career;

    @ApiModelProperty(value = "头衔 1高级讲师 2首席讲师")
    private Integer level;

    @ApiModelProperty(value = "讲师头像")
    private String avatar;

    @ApiModelProperty(value = "排序")
    private Integer sort;

    @ApiModelProperty(value = "入驻时间", example = "2021-12-30")
    // 覆盖json的全局时间格式配置
    @JsonFormat(timezone = "GMT+8", pattern = "yyyy-MM-dd")
    private Date joinDate;

    @ApiModelProperty(value = "逻辑删除 1（true）已删除， 0（false）未删除")
    // 阿里规范：POJO 类中的任何布尔类型的变量，都不要加 is 前缀
    // 解决对象中的属性名和字段名不一致的问题（非驼峰）
    @TableField("is_deleted")
    // 设置为逻辑删除
    @TableLogic
    private Boolean deleted;


}
