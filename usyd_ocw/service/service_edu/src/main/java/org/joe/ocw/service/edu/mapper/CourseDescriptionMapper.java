package org.joe.ocw.service.edu.mapper;

import org.joe.ocw.service.edu.entity.CourseDescription;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 课程简介 Mapper 接口
 * </p>
 *
 * @author Joe He
 * @since 2021-12-29
 */
@Repository // 防止idea报错
public interface CourseDescriptionMapper extends BaseMapper<CourseDescription> {

}
