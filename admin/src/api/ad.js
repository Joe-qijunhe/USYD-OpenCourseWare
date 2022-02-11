// @ 符号在build/webpack.base.conf.js 中配置 表示 'src' 路径
import request from '@/utils/request'

export default {

  pageList(page, limit) {
    return request({
      // baseURL: 'http://127.0.0.1:8140',
      url: `/admin/cms/ad/list/${page}/${limit}`,
      method: 'get'
    })
  },

  removeById(id) {
    return request({
      // baseURL: 'http://127.0.0.1:8140',
      url: `/admin/cms/ad/remove/${id}`,
      method: 'delete'
    })
  },

  save(ad) {
    return request({
      // baseURL: 'http://127.0.0.1:8140',
      url: '/admin/cms/ad/save',
      method: 'post',
      data: ad
    })
  },

  getById(id) {
    return request({
      // baseURL: 'http://127.0.0.1:8140',
      url: `/admin/cms/ad/get/${id}`,
      method: 'get'
    })
  },

  updateById(ad) {
    return request({
      // baseURL: 'http://127.0.0.1:8140',
      url: '/admin/cms/ad/update',
      method: 'put',
      data: ad
    })
  }
}
