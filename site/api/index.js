import request from '~/utils/request'
export default {

  getTopBannerAdList() {
    // 这块adType id为硬编码
    return request({
      // baseURL: 'http://localhost:8140',
      url: '/api/cms/ad/list/1489752644317626369',
      method: 'get'
    })
  },

  getIndexData() {
    return request({
      url: '/api/edu/index',
      method: 'get'
    })
  }
}
