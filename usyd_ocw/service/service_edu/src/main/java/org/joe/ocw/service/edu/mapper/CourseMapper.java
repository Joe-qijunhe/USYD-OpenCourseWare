package org.joe.ocw.service.edu.mapper;

import org.joe.ocw.service.edu.entity.Course;
import org.joe.ocw.service.edu.entity.vo.CoursePublishVo;
import org.joe.ocw.service.edu.entity.vo.CourseVo;
import org.joe.ocw.service.edu.entity.vo.WebCourseVo;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 * 课程 Mapper 接口
 * </p>
 *
 * @author Joe He
 * @since 2021-12-29
 */
@Repository
public interface CourseMapper extends BaseMapper<Course> {

    List<CourseVo> selectPageByCourseQueryVo(
            // mp会自动组装分页参数
            Page<CourseVo> pageParam,
            // mp会自动组装queryWrapper。生成QueryWrapper的sqlSegment属性拼接了不带where的筛选条件sql，
            // 而customSqlSegment属性拼接了完整的where语句
            // @Param(Constants.WRAPPER)等同于@Param("ew")，和 xml文件中的 ${ew.customSqlSegment} 对应
            @Param(Constants.WRAPPER) QueryWrapper<CourseVo> queryWrapper);

    CoursePublishVo selectCoursePublishVoById(String id);

    WebCourseVo selectWebCourseVoById(String courseId);

}
