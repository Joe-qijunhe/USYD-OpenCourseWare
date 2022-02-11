package org.joe.ocw.service.edu.controller.admin;


import org.joe.ocw.common.base.result.R;
import org.joe.ocw.service.edu.entity.Teacher;
import org.joe.ocw.service.edu.entity.vo.TeacherQueryVo;
import org.joe.ocw.service.edu.service.TeacherService;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 前端控制器
 * </p>
 *
 * @author Joe He
 * @since 2021-12-29
 */
// @CrossOrigin // 允许跨域
@Api(description = "讲师管理")
@RestController
@RequestMapping("/admin/edu/teacher")
public class TeacherController {

    @Autowired
    private TeacherService teacherService;

    @ApiOperation("所有讲师列表")
    @GetMapping("/list")
    public R listAll(HttpServletRequest request) {
        // 简单的业务功能可以在Controller层直接调用service的方法，因为service继承了IService
        List<Teacher> teacherList = teacherService.list();
        return R.ok().data("items", teacherList);
    }

    @ApiOperation(value = "根据id删除讲师", notes = "根据id删除讲师，逻辑删除")
    @DeleteMapping("/remove/{id}")
    public R removeById(
            @ApiParam(value = "讲师id", required = true)
            @PathVariable String id) {
        // 删除讲师头像
        teacherService.removeAvatarById(id);

        // 删除讲师
        boolean result = teacherService.removeById(id);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation(value = "根据id列表删除讲师")
    @DeleteMapping("batch-remove")
    public R removeRows(
            @ApiParam(value = "讲师id列表", required = true)
            @RequestBody List<String> idList) {
        boolean result = teacherService.removeByIds(idList);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("讲师分页列表")
    @GetMapping("/list/{page}/{limit}")
    public R listPage(
            @ApiParam(value = "当前页码", required = true) @PathVariable Long page,
            @ApiParam(value = "每页记录数", required = true) @PathVariable Long limit,
            @ApiParam("讲师列表查询对象") TeacherQueryVo teacherQueryVo) {

        Page<Teacher> pageParam = new Page<>(page, limit);
        IPage<Teacher> pageModel = teacherService.selectPage(pageParam, teacherQueryVo);
        List<Teacher> records = pageModel.getRecords();
        long total = pageModel.getTotal();
        return R.ok().data("total", total).data("rows", records);
    }

    @ApiOperation("新增讲师")
    @PostMapping("/save")
    public R save(@ApiParam("讲师对象") @RequestBody Teacher teacher) {
        teacherService.save(teacher);
        return R.ok().message("保存成功");
    }

    @ApiOperation("更新讲师")
    @PutMapping("/update")
    public R updateById(@ApiParam("讲师对象") @RequestBody Teacher teacher) {
        boolean result = teacherService.updateById(teacher);
        if (result) {
            return R.ok().message("更新成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("根据id获取讲师信息")
    @GetMapping("/get/{id}")
    public R getById(@ApiParam("讲师对象") @PathVariable String id) {
        Teacher teacher = teacherService.getById(id);
        if (teacher != null) {
            return R.ok().data("item", teacher);
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("根据关键字查询讲师名列表（输入建议功能）")
    @GetMapping("/list/name/{key}")
    public R selectNameListByKey(
            @ApiParam(value = "关键字", required = true)
            @PathVariable String key) {
        // 需要返回 [{"name": "何其骏"},{"name": "何老师"}]
        List<Map<String, Object>> nameList = teacherService.selectNameList(key);
        return R.ok().data("nameList", nameList);
    }

}

