package org.joe.ocw.service.edu.controller.api;

import org.joe.ocw.common.base.result.R;
import org.joe.ocw.service.edu.entity.Course;
import org.joe.ocw.service.edu.entity.vo.ChapterVo;
import org.joe.ocw.service.edu.entity.vo.WebCourseQueryVo;
import org.joe.ocw.service.edu.entity.vo.WebCourseVo;
import org.joe.ocw.service.edu.service.ChapterService;
import org.joe.ocw.service.edu.service.CourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @CrossOrigin
@Api(description="课程")
@RestController
@RequestMapping("/api/edu/course")
public class ApiCourseController {

    @Autowired
    private CourseService courseService;
    @Autowired
    private ChapterService chapterService;

    @ApiOperation("课程列表")
    @GetMapping("/list")
    public R list(
            @ApiParam(value = "查询对象", required = false)
                    WebCourseQueryVo webCourseQueryVo){
        List<Course> courseList = courseService.webSelectList(webCourseQueryVo);
        return  R.ok().data("courseList", courseList);
    }

    @ApiOperation("根据ID查询课程")
    @GetMapping("/get/{courseId}")
    public R getById(
            @ApiParam(value = "课程ID", required = true)
            @PathVariable String courseId){

        //查询课程信息和讲师信息
        WebCourseVo webCourseVo = courseService.selectWebCourseVoById(courseId);

        //查询当前课程的章节信息
        List<ChapterVo> chapterVoList = chapterService.nestedList(courseId);

        return R.ok().data("course", webCourseVo).data("chapterVoList", chapterVoList);
    }
}
