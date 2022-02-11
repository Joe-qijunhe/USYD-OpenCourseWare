package org.joe.ocw.service.edu.controller.api;

import org.joe.ocw.common.base.result.R;
import org.joe.ocw.service.edu.entity.Teacher;
import org.joe.ocw.service.edu.service.TeacherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

// @CrossOrigin
@Api(description="讲师")
@RestController
@RequestMapping("/api/edu/teacher")
public class ApiTeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation(value="所有讲师列表")
    @GetMapping("/list")
    public R listAll(){
        List<Teacher> list = teacherService.list();
        return R.ok().data("items", list).message("获取讲师列表成功");
    }

    @ApiOperation(value = "获取讲师")
    @GetMapping("/get/{id}")
    public R get(
            @ApiParam(value = "讲师ID", required = true)
            @PathVariable String id) {
        Map<String, Object> map = teacherService.selectTeacherInfoById(id);
        return R.ok().data(map);
    }
}
