<template>
  <!-- 公共头 -->
  <header id="header">
    <section class="container">
      <h1 id="logo">
        <a href="#" title="USYD">
          <img src="~/assets/img/usyd.png" width="100%" alt="USYD">
        </a>
      </h1>
      <div class="h-r-nsl">
        <ul class="nav">
          <router-link to="/" tag="li" active-class="current" exact>
            <a>Home</a>
          </router-link>
          <router-link to="/course" tag="li" active-class="current">
            <a>Courses</a>
          </router-link>
          <router-link to="/teacher" tag="li" active-class="current">
            <a>Teachers</a>
          </router-link>
        </ul>
        <!-- / nav -->
        <ul class="h-r-login">
          <li v-if="!userInfo" id="no-login">
            <a href="/login" title="Login">
              <em class="icon18 login-icon">&nbsp;</em>
              <span class="vam ml5">Login</span>
            </a>
            |
            <a href="/register" title="Register">
              <span class="vam ml5">Register</span>
            </a>
          </li>
          <li v-if="userInfo" id="is-login-one" class="mr10">
            <a id="headerMsgCountId" href="#" title="Message">
              <em class="icon18 news-icon">&nbsp;</em>
            </a>
            <q class="red-point">&nbsp;</q>
          </li>
          <li v-if="userInfo" id="is-login-two" class="h-r-user">
            <a href="/ucenter" title>
              <img
                :src="userInfo.avatar"
                width="30"
                height="30"
                class="vam picImg"
                alt>
              <span id="userName" class="vam disIb">{{ userInfo.nickname }}</span>
            </a>
            <a href="javascript:void(0);" title="Logout" class="ml5" @click="logout()">Logout</a>
          </li>
          <!-- /未登录显示第1 li；登录后显示第2，3 li -->
        </ul>
        <aside class="h-r-search">
          <form action="#" method="post">
            <label class="h-r-s-box">
              <input type="text" placeholder="Search Course" name="queryCourse.courseName" value>
              <button type="submit" class="s-btn">
                <em class="icon18">&nbsp;</em>
              </button>
            </label>
          </form>
        </aside>
      </div>
      <aside class="mw-nav-btn">
        <div class="mw-nav-icon"/>
      </aside>
      <div class="clear"/>
    </section>
  </header>
  <!-- /公共头 -->
</template>

<script>
import loginApi from '~/api/login'
import cookie from 'js-cookie'

export default {

  data() {
    return {
      userInfo: null
    }
  },

  created() {
    this.getUserInfo()
  },

  methods: {
    getUserInfo() {
      // 如果cookie中token不存在，则不显示用户信息
      if (!cookie.get('ocw_jwt_token')) {
        return
      }
      // 如果token存在，则根据token解析用户登录信息
      loginApi.getLoginInfo().then(response => {
        // 渲染页面
        this.userInfo = response.data.userInfo
      })
    },

    logout() {
      cookie.set('ocw_jwt_token', '', { domain: 'localhost' })
      // 跳转页面
      window.location.href = '/'
    }
  }
}
</script>
