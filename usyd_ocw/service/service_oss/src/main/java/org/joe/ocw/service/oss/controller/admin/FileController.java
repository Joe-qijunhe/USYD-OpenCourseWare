package org.joe.ocw.service.oss.controller.admin;


import org.joe.ocw.common.base.result.R;
import org.joe.ocw.common.base.result.ResultCodeEnum;
import org.joe.ocw.service.base.exception.GuliException;
import org.joe.ocw.service.oss.service.FileService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.List;

@Api(description = "阿里云文件管理")
// @CrossOrigin
@RestController
@RequestMapping("/admin/oss/file")
public class FileController {

    @Autowired
    private FileService fileService;

    @ApiOperation("文件上传")
    @PostMapping("/upload")
    public R upload(
            @ApiParam(value = "文件", required = true)
            @RequestParam("file") MultipartFile file,

            @ApiParam(value = "上传到的模块", required = true)
            @RequestParam("module") String module) {

        try {
            InputStream inputStream = file.getInputStream();
            String originalFilename = file.getOriginalFilename();
            String uploadUrl = fileService.upload(inputStream, module, originalFilename);

            return R.ok().message("文件上传成功").data("url", uploadUrl);
        } catch (Exception e) {
            throw new GuliException(ResultCodeEnum.FILE_UPLOAD_ERROR);
        }
    }

    @ApiOperation("文件删除")
    @DeleteMapping("/remove")
    public R removeFile(
            @ApiParam(value = "要删除的文件url路径", required = true)
            @RequestBody String url) {

        fileService.removeFile(url);
        return R.ok().message("文件删除成功");

    }

    @ApiOperation("列表文件删除")
    @DeleteMapping("/removeList")
    public R removeFilesByUrlList(@RequestBody List<String> noteUrlList) {
        fileService.removeListFiles(noteUrlList);
        return R.ok().message("文件删除成功");
    }

    @RequestMapping("/test")
    public void testFeign() {
        System.out.println("=====>远程调用<======");
    }
}
