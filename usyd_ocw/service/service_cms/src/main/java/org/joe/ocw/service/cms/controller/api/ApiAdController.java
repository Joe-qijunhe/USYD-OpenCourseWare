package org.joe.ocw.service.cms.controller.api;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.joe.ocw.common.base.result.R;
import org.joe.ocw.service.cms.entity.Ad;
import org.joe.ocw.service.cms.service.AdService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.List;

// @CrossOrigin //解决跨域问题
@Api(description = "广告推荐")
@RestController
@RequestMapping("/api/cms/ad")
public class ApiAdController {

    @Autowired
    private AdService adService;

    @ApiOperation("根据推荐位id显示广告推荐")
    @GetMapping("list/{adTypeId}")
    public R listByAdTypeId(@ApiParam(value = "推荐位id", required = true) @PathVariable String adTypeId) {

        List<Ad> ads = adService.selectByAdTypeId(adTypeId);
        return R.ok().data("items", ads);
    }

}
