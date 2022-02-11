<template>
  <div class="main">
    <div class="title">
      <a class="active" href="/login">Login</a>
      <span>·</span>
      <a href="/register">Register</a>
    </div>

    <div class="sign-up-container">
      <form action="register">
        <div class="input-prepend restyle">
          <input
            v-model="user.mobile"
            type="text"
            placeholder="Mobile Number">
          <i class="iconfont icon-phone"/>
        </div>
        <div class="input-prepend">
          <input
            v-model="user.password"
            type="password"
            placeholder="Password">
          <i class="iconfont icon-password"/>
        </div>
        <div class="btn">
          <input
            type="button"
            class="sign-in-button"
            value="登录"
            @click="submitLogin()">
        </div>
      </form>
    </div>
  </div>
</template>

<script>
import '~/assets/css/sign.css'
import '~/assets/css/iconfont.css'
import cookie from 'js-cookie'
import loginApi from '~/api/login'

export default {
  layout: 'sign',

  data() {
    return {
      user: {
        mobile: '',
        password: ''
      }
    }
  },

  methods: {
    // 登录
    submitLogin() {
      // 执行登录
      loginApi.submitLogin(this.user).then(response => {
        // 登录成功后将jwtToken写入cookie
        // 为了让cookie跨域（比如password.jd.com和order.jd.com），需要存在父域名（jd.com）上
        cookie.set('ocw_jwt_token', response.data.token, { domain: 'localhost' })

        // 如果上一页是注册页，就跳转到首页
        if (document.referrer.indexOf('register') !== -1) {
          window.location.href = '/'
        } else {
          // 跳转到上一个刚刚访问的页面
          history.go(-1)
        }
      })
    }
  }
}
</script>
