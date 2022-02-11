package org.joe.ocw.service.sms.controller.api;

import io.swagger.annotations.Api;
import org.joe.ocw.common.base.result.R;
import org.joe.ocw.common.base.result.ResultCodeEnum;
import org.joe.ocw.common.base.util.RandomUtils;
import org.joe.ocw.service.base.exception.GuliException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/sms")
@Api(description = "短信管理")
// @CrossOrigin //跨域
public class ApiSmsController {

    @Autowired
    private RedisTemplate redisTemplate;

    @GetMapping("/send/{mobile}")
    public R getCode(@PathVariable String mobile) {

        //校验手机号是否合法
        if(StringUtils.isEmpty(mobile)) {
            throw new GuliException(ResultCodeEnum.LOGIN_PHONE_ERROR);
        }

        //生成验证码
        String checkCode = RandomUtils.getFourBitRandom();

        // 发送验证码

        // 将验证码存入redis缓存
        redisTemplate.opsForValue().set(mobile, checkCode, 5, TimeUnit.MINUTES);

        return R.ok().message("短信发送成功");
    }
}
