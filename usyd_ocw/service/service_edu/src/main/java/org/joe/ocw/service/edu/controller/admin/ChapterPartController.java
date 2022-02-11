package org.joe.ocw.service.edu.controller.admin;


import org.joe.ocw.common.base.result.R;
import org.joe.ocw.service.edu.entity.ChapterPart;
import org.joe.ocw.service.edu.service.ChapterPartService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * <p>
 * 课程视频 前端控制器
 * </p>
 *
 * @author Joe He
 * @since 2021-12-29
 */
// @CrossOrigin // 允许跨域
@Api(description = "课时管理")
@RestController
@RequestMapping("/admin/edu/chapter-part")
public class ChapterPartController {

    @Autowired
    private ChapterPartService chapterPartService;

    @ApiOperation("新增课时")
    @RequestMapping("/save")
    public R save(
            @ApiParam(value = "课时对象",required = true)
            @RequestBody ChapterPart chapterPart) {

        boolean result = chapterPartService.save(chapterPart);
        if (result) {
            return R.ok().message("保存成功");
        } else {
            return R.error().message("保存失败");
        }
    }

    @ApiOperation("根据id查询课时")
    @RequestMapping("/get/{id}")
    public R getById(
            @ApiParam(value = "课时id",required = true)
            @PathVariable String id){

        ChapterPart chapterPart = chapterPartService.getById(id);
        if (chapterPart != null) {
            return R.ok().data("item", chapterPart);
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("根据id修改课时")
    @PutMapping("/update")
    public R updateById(
            @ApiParam(value="课时对象", required = true)
            @RequestBody ChapterPart chapterPart){

        boolean result = chapterPartService.updateById(chapterPart);
        if (result) {
            return R.ok().message("修改成功");
        } else {
            return R.error().message("数据不存在");
        }
    }

    @ApiOperation("根据ID删除课时")
    @DeleteMapping("/remove/{id}")
    public R removeById(
            @ApiParam(value = "课时ID", required = true)
            @PathVariable String id){

        // 删除视频
        chapterPartService.removeMediaVideoById(id);

        // 删除笔记
        chapterPartService.removeNoteById(id);

        boolean result = chapterPartService.removeById(id);
        if (result) {
            return R.ok().message("删除成功");
        } else {
            return R.error().message("数据不存在");
        }
    }
}

