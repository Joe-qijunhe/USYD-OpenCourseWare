package org.joe.ocw.service.ucenter.controller.api;


import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.joe.ocw.common.base.result.R;
import org.joe.ocw.common.base.result.ResultCodeEnum;
import org.joe.ocw.common.base.util.JwtInfo;
import org.joe.ocw.common.base.util.JwtUtils;
import org.joe.ocw.service.base.exception.GuliException;
import org.joe.ocw.service.ucenter.entity.vo.LoginVo;
import org.joe.ocw.service.ucenter.entity.vo.RegisterVo;
import org.joe.ocw.service.ucenter.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

/**
 * <p>
 * 会员表 前端控制器
 * </p>
 *
 * @author Joe He
 * @since 2022-02-06
 */
@Api(description = "会员管理")
// @CrossOrigin
@RestController
@RequestMapping("/api/ucenter/member")
public class ApiMemberController {

    @Autowired
    private MemberService memberService;

    @ApiOperation(value = "会员注册")
    @PostMapping("/register")
    public R register(@RequestBody RegisterVo registerVo) {

        memberService.register(registerVo);
        return R.ok();
    }

    @ApiOperation(value = "会员登录")
    @PostMapping("/login")
    public R login(@RequestBody LoginVo loginVo) {

        String token = memberService.login(loginVo);
        return R.ok().data("token", token).message("登录成功");
    }

    @ApiOperation(value = "根据token获取登录信息")
    @GetMapping("/get-login-info")
    public R getLoginInfo(HttpServletRequest request){

        try{
            JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
            return R.ok().data("userInfo", jwtInfo);
        }catch (Exception e){
            throw new GuliException(ResultCodeEnum.FETCH_USERINFO_ERROR);
        }
    }
}

