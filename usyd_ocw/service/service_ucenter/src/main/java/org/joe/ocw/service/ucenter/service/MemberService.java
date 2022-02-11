package org.joe.ocw.service.ucenter.service;

import org.joe.ocw.service.ucenter.entity.Member;
import com.baomidou.mybatisplus.extension.service.IService;
import org.joe.ocw.service.ucenter.entity.vo.LoginVo;
import org.joe.ocw.service.ucenter.entity.vo.RegisterVo;

/**
 * <p>
 * 会员表 服务类
 * </p>
 *
 * @author Joe He
 * @since 2022-02-06
 */
public interface MemberService extends IService<Member> {

    void register(RegisterVo registerVo);

    String login(LoginVo loginVo);
}
