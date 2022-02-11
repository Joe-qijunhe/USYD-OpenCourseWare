package org.joe.ocw.service.edu.service.impl;

import org.joe.ocw.common.base.result.R;
import org.joe.ocw.feign.OssFileService;
import org.joe.ocw.service.edu.entity.*;
import org.joe.ocw.service.edu.entity.form.CourseInfoForm;
import org.joe.ocw.service.edu.entity.vo.*;
import org.joe.ocw.service.edu.mapper.*;
import org.joe.ocw.service.edu.service.CourseService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Joe He
 * @since 2021-12-29
 */
@Service
public class CourseServiceImpl extends ServiceImpl<CourseMapper, Course> implements CourseService {
    @Autowired
    private ChapterPartMapper chapterPartMapper;
    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private CommentMapper commentMapper;
    @Autowired
    private CourseCollectMapper courseCollectMapper;
    @Autowired
    private CourseDescriptionMapper courseDescriptionMapper;
    @Autowired
    private OssFileService ossFileService;

    @Transactional(rollbackFor = Exception.class) // 配置类得开启@EnableTransactionManagement
    @Override
    public String saveCourseInfo(CourseInfoForm courseInfoForm) {
        // 保存Course
        Course course = new Course();
        // 拷贝同名的属性
        BeanUtils.copyProperties(courseInfoForm, course);
        course.setStatus(Course.COURSE_DRAFT);
        baseMapper.insert(course);

        // 保存CourseDescription
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(course.getId());
        courseDescriptionMapper.insert(courseDescription);

        return course.getId();
    }

    @Override
    public CourseInfoForm getCourseInfoById(String id) {
        // 根据id获取Course
        Course course = baseMapper.selectById(id);
        if (course == null) {
            return null;
        }

        // 根据id获取CourseDescription
        CourseDescription courseDescription = courseDescriptionMapper.selectById(id);

        // 组装成CourseInfoForm
        CourseInfoForm courseInfoForm = new CourseInfoForm();
        BeanUtils.copyProperties(course, courseInfoForm);
        courseInfoForm.setDescription(courseDescription.getDescription());
        return courseInfoForm;
    }

    @Override
    public void updateCourseInfoById(CourseInfoForm courseInfoForm) {
        //保存课程基本信息
        Course course = new Course();
        BeanUtils.copyProperties(courseInfoForm, course);
        baseMapper.updateById(course);

        //保存课程详情信息
        CourseDescription courseDescription = new CourseDescription();
        courseDescription.setDescription(courseInfoForm.getDescription());
        courseDescription.setId(course.getId());
        courseDescriptionMapper.updateById(courseDescription);
    }

    @Override
    public IPage<CourseVo> selectPage(Long page, Long limit, CourseQueryVo courseQueryVo) {

        QueryWrapper<CourseVo> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("c.gmt_create");

        String title = courseQueryVo.getTitle();
        String teacherId = courseQueryVo.getTeacherId();
        String subjectParentId = courseQueryVo.getSubjectParentId();
        String subjectId = courseQueryVo.getSubjectId();

        if (!StringUtils.isEmpty(title)) {
            queryWrapper.like("c.title", title);
        }

        if (!StringUtils.isEmpty(teacherId) ) {
            queryWrapper.eq("c.teacher_id", teacherId);
        }

        if (!StringUtils.isEmpty(subjectParentId)) {
            queryWrapper.eq("c.subject_parent_id", subjectParentId);
        }

        if (!StringUtils.isEmpty(subjectId)) {
            queryWrapper.eq("c.subject_id", subjectId);
        }

        Page<CourseVo> pageParam = new Page<>(page, limit);
        //放入分页参数和查询条件参数，mp会自动组装
        List<CourseVo> records = baseMapper.selectPageByCourseQueryVo(pageParam, queryWrapper);
        // mp会帮忙封装total(mp先执行select count(1) from ...，再执行select...limit ?,?)，
        // 但records需要自己封装
        pageParam.setRecords(records);
        return pageParam;
    }

    @Override
    public boolean removeCoverById(String id) {
        Course course = baseMapper.selectById(id);
        if (course != null) {
            String cover = course.getCover();
            if (!StringUtils.isEmpty(cover)) {
                R r = ossFileService.removeFile(cover);
                return r.getSuccess();
            }
        }
        return false;
    }

    /**
     * 数据库中的外键约束的设置
     * 互联网分布式项目中不允许使用外键与级联更新，一切涉及级联的操作不要依赖数据库层，要在业务层解决
     *
     * 业务层解决级联删除时，先删除子表的数据，再删除父表的数据
     */
    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeCourseById(String id) {
        // 根据courseId删除Video
        QueryWrapper<ChapterPart> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", id);
        chapterPartMapper.delete(videoQueryWrapper);

        // 根据courseId删除Chapter
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", id);
        chapterMapper.delete(chapterQueryWrapper);

        // 根据courseId删除Comment
        QueryWrapper<Comment> commentQueryWrapper = new QueryWrapper<>();
        commentQueryWrapper.eq("course_id", id);
        commentMapper.delete(commentQueryWrapper);

        // 根据courseId删除Comment
        QueryWrapper<CourseCollect> collectQueryWrapper = new QueryWrapper<>();
        collectQueryWrapper.eq("course_id", id);
        courseCollectMapper.delete(collectQueryWrapper);

        // 根据courseId删除Description
        courseDescriptionMapper.deleteById(id);

        // 删除course
        return this.removeById(id);
    }

    @Override
    public CoursePublishVo getCoursePublishVoById(String id) {
        return baseMapper.selectCoursePublishVoById(id);
    }

    @Override
    public boolean publishCourseById(String id) {
        Course course = new Course();
        course.setId(id);
        course.setStatus(Course.COURSE_NORMAL);
        return this.updateById(course);
    }

    @Override
    public List<Course> webSelectList(WebCourseQueryVo webCourseQueryVo) {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();

        //查询已发布的课程
        queryWrapper.eq("status", Course.COURSE_NORMAL);

        if (!StringUtils.isEmpty(webCourseQueryVo.getSubjectParentId())) {
            queryWrapper.eq("subject_parent_id", webCourseQueryVo.getSubjectParentId());
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getSubjectId())) {
            queryWrapper.eq("subject_id", webCourseQueryVo.getSubjectId());
        }

        if (!StringUtils.isEmpty(webCourseQueryVo.getGmtCreateSort())) {
            queryWrapper.orderByDesc("gmt_create");
        }

        return baseMapper.selectList(queryWrapper);
    }

    @Transactional(rollbackFor = Exception.class)
    @Override
    public WebCourseVo selectWebCourseVoById(String id) {
        // 更新课程浏览数
        Course course = baseMapper.selectById(id);
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);

        // 获取课程信息
        return baseMapper.selectWebCourseVoById(id);
    }

    @Cacheable(value = "index", key = "'selectHotCourse'")
    @Override
    public List<Course> selectHotCourse() {
        QueryWrapper<Course> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("view_count");
        queryWrapper.last("limit 8");
        return baseMapper.selectList(queryWrapper);
    }
}
