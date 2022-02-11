<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 课程列表 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">All Courses</span>
        </h2>
      </header>
      <section class="c-sort-box">
        <section class="c-s-dl">
          <dl>
            <dt>
              <span class="c-888 fsize12">Classification</span>
            </dt>
            <dd class="c-s-dl-li">
              <!-- 一级类别 开始-->
              <ul class="clearfix">
                <li
                  :class="{current:!$route.query.subjectParentId}">
                  <a
                    title="全部"
                    href="javascript:void(0);"
                    @click="searchSubjectLevelOne('')">All</a>
                </li>
                <li
                  v-for="item in subjectNestedList"
                  :key="item.id"
                  :class="{current:$route.query.subjectParentId===item.id}">
                  <a
                    :title="item.title"
                    href="javascript:void(0);"
                    @click="searchSubjectLevelOne(item.id)">{{ item.title }}</a>
                </li>
              </ul>
              <!-- /一级类别 结束-->
            </dd>
          </dl>
          <dl>
            <dt>
              <span class="c-999 fsize14"/>
            </dt>
            <dd class="c-s-dl-li">
              <!-- 二级类别 开始-->
              <ul v-if="$route.query.subjectParentId" class="clearfix">
                <li :class="{current:!$route.query.subjectId}">
                  <a
                    title="全部"
                    href="javascript:void(0);"
                    @click="searchSubjectLevelTwo('')">All</a>
                </li>
                <li
                  v-for="item in subSubjectList"
                  :key="item.id"
                  :class="{current:$route.query.subjectId===item.id}">
                  <a
                    :title="item.title"
                    href="javascript:void(0);"
                    @click="searchSubjectLevelTwo(item.id)">{{ item.title }}</a>
                </li>
              </ul>
              <!-- /二级类别 结束-->
            </dd>
          </dl>
          <div class="clear"/>
        </section>
        <div class="js-wrap">
          <section class="fr">
            <span class="c-ccc">
              <i class="c-master f-fM">1</i>/
              <i class="c-666 f-fM">1</i>
            </span>
          </section>
          <section class="fl">
            <!-- 排序 开始-->
            <ol class="js-tap clearfix">
              <li :class="{'current bg-green': $route.query.gmtCreateSort}">
                <a title="最新" href="javascript:void(0);" @click="searchGmtCreate()">Latest
                  <span v-if="$route.query.gmtCreateSort">↓</span>
                </a>
              </li>
            </ol>
            <!-- /排序 结束-->
          </section>
        </div>
        <div class="mt40">
          <!-- /无数据提示 开始-->
          <section v-if="courseList.length===0" class="no-data-wrap">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">No Infomation Yet</span>
          </section>
          <!-- /无数据提示 结束-->

          <!-- 数据列表 开始-->
          <article v-if="courseList.length>0" class="comm-course-list">
            <ul id="bna" class="of">
              <li v-for="item in courseList" :key="item.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="item.cover" :alt="item.title" class="img-responsive">
                    <div class="cc-mask">
                      <a :href="'/course/'+item.id" title="开始学习" class="comm-btn c-btn-1">Study Now</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a :href="'/course/'+item.id" :title="item.title" class="course-title fsize18 c-333">{{ item.title }}</a>
                  </h3>
                  <section class="mt10 hLh20 of">
                    <span class="fl jgAttr c-ccc f-fA">
                      <i class="c-999 f-fA">{{ item.viewCount }}People has viewed</i>
                    </span>
                  </section>
                </div>
              </li>
            </ul>
            <div class="clear"/>
          </article>
          <!-- /数据列表 结束-->
        </div>
      </section>
    </section>
    <!-- /课程列表 结束 -->
  </div>
</template>

<script>
import courseApi from '~/api/course'
import querystring from 'querystring' // url参数拼接工具

export default {

  async asyncData(page) {
    /**
     * guli_admin里搜索参数都是通过浏览器发ajax，再渲染的，url没有请求参数
     * 但guli_site这种前台的，为了方便用户分享/保存url，搜索参数需要放在url地址中
     * 步骤：
     * 1. 点击查询条件时：在url地址栏中组装查询参数
     * 2. 在url地址栏中获取查询参数，对页面中响应部分的内容进行高亮显示
     */

    // 组装查询参数
    const searchObj = {}
    // 从url地址栏中获取查询参数
    const query = page.route.query
    searchObj.subjectParentId = query.subjectParentId || ''
    searchObj.subjectId = query.subjectId || ''
    searchObj.gmtCreateSort = query.gmtCreateSort || ''

    // 课程列表的查询
    const courseListResponse = await courseApi.getList(searchObj)
    const courseList = courseListResponse.data.courseList

    // 获取课程分类嵌套列表
    const subjectNestedListResponse = await courseApi.getSubjectNestedList()
    const subjectNestedList = subjectNestedListResponse.data.items
    // 创建课程分类子列表
    let subSubjectList = []
    // 遍历一级分类
    for (let i = 0; i < subjectNestedList.length; i++) {
      // 如果查询参数中的一级分类id和当前一级分类id一致
      if (subjectNestedList[i].id === searchObj.subjectParentId) {
        // 则获取二级分类列表
        subSubjectList = subjectNestedList[i].children
      }
    }

    return {
      courseList, // 课程列表
      subjectNestedList, // 课程分类列表
      subSubjectList, // 课程二级分类列表
      searchObj// 查询参数
    }
  },

  methods: {
    // 选择一级分类
    searchSubjectLevelOne(subjectParentId) {
      // 让页面根据新的url地址刷新
      window.location = `course?subjectParentId=${subjectParentId}`
    },

    // 选择二级分类
    searchSubjectLevelTwo(subjectId) {
    // window.location = 'course?subjectId=' + subjectId + '&subjectParentId=' + this.searchObj.subjectParentId
    // 自动组装queryString
      const obj = {
        subjectParentId: this.searchObj.subjectParentId,
        subjectId: subjectId
      }
      const querys = querystring.stringify(obj)
      window.location = '/course?' + querys
    },

    // 选择按创建时间倒序
    searchGmtCreate() {
      // 自动组装queryString
      const obj = {
        subjectParentId: this.searchObj.subjectParentId,
        subjectId: this.searchObj.subjectId,
        gmtCreateSort: 1
      }
      const querys = querystring.stringify(obj)
      window.location = '/course?' + querys
    }
  }
}

</script>
