import request from '@/utils/request'

export default {

  save(chapterPart) {
    return request({
      url: '/admin/edu/chapter-part/save',
      method: 'post',
      data: chapterPart
    })
  },

  getById(id) {
    return request({
      url: `/admin/edu/chapter-part/get/${id}`,
      method: 'get'
    })
  },

  updateById(chapterPart) {
    return request({
      url: '/admin/edu/chapter-part/update',
      method: 'put',
      data: chapterPart
    })
  },

  removeById(id) {
    return request({
      url: `/admin/edu/chapter-part/remove/${id}`,
      method: 'delete'
    })
  }
}
