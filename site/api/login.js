import request from '~/utils/request'
// import cookie from 'js-cookie'

export default {

  submitLogin(user) {
    return request({
      // baseURL: 'http://localhost:8160',
      url: '/api/ucenter/member/login',
      method: 'post',
      data: user
    })
  },

  getLoginInfo() {
    return request({
      // baseURL: 'http://localhost:8160',
      url: '/api/ucenter/member/get-login-info',
      method: 'get'
      // 通过请求头发送token
      // 因为前端和后端是跨域的，访问不了cookie，所以把token放在header里
      // headers: { 'token': cookie.get('ocw_jwt_token') }
    })
  }
}
