import axios from 'axios'
import cookie from 'js-cookie'
import { Message } from 'element-ui'

// 创建axios实例
const service = axios.create({
  baseURL: 'http://localhost:9110',
  timeout: 12000 // 请求超时时间
})

// http request 拦截器
service.interceptors.request.use(
  config => {
    // 如果cookie中包含guli_token，则发送后端api请求的时候携带token
    if (cookie.get('ocw_jwt_token')) {
      config.headers['token'] = cookie.get('ocw_jwt_token')
    }
    return config
  },
  error => {
    return Promise.reject(error)
  }
)

// response 拦截器
service.interceptors.response.use(
  response => {
    /**
       * code为非20000是抛错 可结合自己业务进行修改
       */
    const res = response.data
    // 成功
    if (res.code === 20000) {
      return response.data
    } else if (res.code === 23004) { // 获取用户信息失败
      // 清除cookie
      cookie.set('ocw_jwt_token', '', { domain: 'localhost' })
      return response.data // 不显示错误信息
    } else if (res.code === 28004) { // 鉴权失败
      // 跳转到登录页面
      window.location.href = '/login'
      return
    } else {
      Message({
        message: res.message,
        type: 'error',
        duration: 5 * 1000
      })
      return Promise.reject('error')
    }
  },
  error => {
    console.log('err: ' + error) // for debug
    Message({
      message: error.message,
      type: 'error',
      duration: 5 * 1000
    })
    return Promise.reject(error)
  }
)

export default service
