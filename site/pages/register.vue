<template>
  <div class="main">
    <div class="title">
      <a href="/login">Login</a>
      <span>·</span>
      <a class="active" href="/register">Register</a>
    </div>

    <div class="sign-up-container">
      <form action="register">
        <div class="input-prepend restyle">
          <input
            v-model="member.nickname"
            type="text"
            placeholder="Nickname">
          <i class="iconfont icon-user"/>
        </div>

        <div class="input-prepend">
          <input
            v-model="member.password"
            type="password"
            placeholder="Password">
          <i class="iconfont icon-password"/>
        </div>

        <div class="input-prepend restyle no-radius">
          <input
            v-model="member.mobile"
            type="text"
            placeholder="Phone Number">
          <i class="iconfont icon-phone"/>
        </div>

        <div class="input-prepend restyle no-radius" style="position:relative">
          <input
            v-model="member.code"
            type="text"
            placeholder="Code">
          <i class="iconfont icon-yanzhengma"/>
          <a
            href="javascript:"
            type="button"
            style="position:absolute;right: 10px;top: 15px;"
            @click="getCodeFun()">{{ codeText }}</a>
        </div>
        <div class="btn">
          <input
            type="button"
            class="sign-up-button"
            value="Register"
            @click="submitRegister()">
        </div>
        <p class="sign-up-msg">
          By clicking "Register" you agree to abide by
          <br>
          <a target="_blank" href="http://www.jianshu.com/p/c44d171298ce">User Agreement</a>
          and
          <a target="_blank" href="http://www.jianshu.com/p/2ov8x3">Privacy Policy</a> 。
        </p>
      </form>
    </div>
  </div>
</template>

<script>
import '~/assets/css/sign.css'
import '~/assets/css/iconfont.css'
import registerApi from '~/api/register'

export default {
  layout: 'sign',

  data() {
    return {
      member: {
        mobile: '',
        code: '',
        nickname: '',
        password: ''
      },
      sending: false, // 是否发送验证码
      second: 60, // 倒计时间
      codeText: 'Get verification code'
    }
  },
  methods: {

    // 获取验证码
    getCodeFun() {
      // this.sending原为false，点击后立即使 this.sending == true，防止有人多次点击
      if (this.sending) { return }
      this.sending = true
      registerApi.sendMessage(this.member.mobile).then(response => {
        // 开始倒计时
        this.timeDown()
        this.$message({
          type: 'success',
          message: 'SMS sent successfully'
        })
      })
    },

    // 倒计时
    timeDown() {
      // 定义计时器
      const timer = setInterval(() => {
        // 剩余秒数
        this.codeText = this.second
        this.second--
        if (this.second < 0) {
          // 清空计时器
          clearInterval(timer)
          this.sending = false
          this.second = 60
          this.codeText = 'Get verification code'
        }
      }, 1000) // 一秒执行一次函数
    },

    // 注册
    submitRegister() {
      registerApi.register(this.member).then(response => {
        // 提示注册成功
        this.$message({
          type: 'success',
          message: 'Register Success'
        })
        this.$router.push({ path: '/login' })
      })
    }
  }
}
</script>
