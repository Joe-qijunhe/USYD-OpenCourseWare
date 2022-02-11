package org.joe.ocw.service.ucenter.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import org.joe.ocw.common.base.result.ResultCodeEnum;
import org.joe.ocw.common.base.util.JwtInfo;
import org.joe.ocw.common.base.util.JwtUtils;
import org.joe.ocw.common.base.util.MD5;
import org.joe.ocw.service.base.exception.GuliException;
import org.joe.ocw.service.ucenter.entity.Member;
import org.joe.ocw.service.ucenter.entity.vo.LoginVo;
import org.joe.ocw.service.ucenter.entity.vo.RegisterVo;
import org.joe.ocw.service.ucenter.mapper.MemberMapper;
import org.joe.ocw.service.ucenter.service.MemberService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * <p>
 * 会员表 服务实现类
 * </p>
 *
 * @author Joe He
 * @since 2022-02-06
 */
@Service
public class MemberServiceImpl extends ServiceImpl<MemberMapper, Member> implements MemberService {

    @Autowired
    private RedisTemplate redisTemplate;

    @Override
    public void register(RegisterVo registerVo) {

        String nickname = registerVo.getNickname();
        String mobile = registerVo.getMobile();
        String password = registerVo.getPassword();
        String code = registerVo.getCode();

        //校验参数
        if (StringUtils.isEmpty(mobile)
                || StringUtils.isEmpty(password)
                || StringUtils.isEmpty(code)
                || StringUtils.isEmpty(nickname)) {
            throw new GuliException(ResultCodeEnum.PARAM_ERROR);
        }

        // 校验验证码
        String checkCode = (String) redisTemplate.opsForValue().get(mobile);
        if (!code.equals(checkCode)) {
            throw new GuliException(ResultCodeEnum.CODE_ERROR);
        }

        // 检查是否被注册
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        Integer count = baseMapper.selectCount(queryWrapper);
        if (count > 0) {
            throw new GuliException(ResultCodeEnum.REGISTER_ERROR);
        }

        // 注册
        Member member = new Member();
        member.setNickname(nickname);
        member.setMobile(mobile);
        member.setPassword(MD5.encrypt(password));
        member.setDisabled(false);
        member.setAvatar("https://guli-file-joe-1.oss-ap-southeast-2.aliyuncs.com/avatar/2022/01/11/default.png");
        baseMapper.insert(member);
    }

    @Override
    public String login(LoginVo loginVo) {

        String mobile = loginVo.getMobile();
        String password = loginVo.getPassword();

        // 校验参数
        if (StringUtils.isEmpty(mobile) || StringUtils.isEmpty(password)) {
            throw new GuliException(ResultCodeEnum.PARAM_ERROR);
        }

        // 校验手机号
        QueryWrapper<Member> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("mobile", mobile);
        Member member = baseMapper.selectOne(queryWrapper);
        if(member == null){
            throw new GuliException(ResultCodeEnum.LOGIN_MOBILE_ERROR);
        }

        // 校验密码
        if(!MD5.encrypt(password).equals(member.getPassword())){
            throw new GuliException(ResultCodeEnum.LOGIN_PASSWORD_ERROR);
        }

        // 检验用户是否被禁用
        if(member.getDisabled()){
            throw new GuliException(ResultCodeEnum.LOGIN_DISABLED_ERROR);
        }

        // 生成token
        JwtInfo jwtInfo = new JwtInfo();
        jwtInfo.setId(member.getId());
        jwtInfo.setNickname(member.getNickname());
        jwtInfo.setAvatar(member.getAvatar());
        String jwtToken = JwtUtils.getJwtToken(jwtInfo, 1800);

        return jwtToken;
    }
}
