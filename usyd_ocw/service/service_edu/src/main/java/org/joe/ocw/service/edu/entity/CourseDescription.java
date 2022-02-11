package org.joe.ocw.service.edu.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import org.joe.ocw.service.base.model.BaseEntity;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * <p>
 * 课程简介
 * </p>
 *
 * @author Joe He
 * @since 2021-12-29
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Accessors(chain = true)
@TableName("edu_course_description")
@ApiModel(value="CourseDescription对象", description="课程简介")
public class CourseDescription extends BaseEntity {

    private static final long serialVersionUID=1L;

    @ApiModelProperty(value = "ID")
    // CourseDescription的id跟Course是主键关联的，Course的id是分布式id的话，CourseDescription不应该有自己的id策略
    // BaseEntity中的id主键策略是分布式id生成，这里定义id覆盖BaseEntity的id
    @TableId(value = "id", type = IdType.NONE)
    private String id;

    @ApiModelProperty(value = "课程简介")
    private String description;


}
