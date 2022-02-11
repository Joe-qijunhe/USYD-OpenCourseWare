<template>
  <div id="aCoursesList" class="bg-fa of">
    <!-- 讲师介绍 开始 -->
    <section class="container">
      <header class="comm-title">
        <h2 class="fl tac">
          <span class="c-333">Teacher Introduction</span>
        </h2>
      </header>
      <div class="t-infor-wrap">
        <!-- 讲师基本信息 开始 -->
        <section class="fl t-infor-box c-desc-content">
          <div class="mt20 ml20">
            <section class="t-infor-pic">
              <img :src="teacher.avatar" :alt="teacher.name">
            </section>
            <h3 class="hLh30">
              <span class="fsize24 c-333">{{ teacher.name }}
                &nbsp;
                {{ teacher.level===1?'Lecturer':'Tutor' }}
              </span>
            </h3>
            <section class="mt10">
              <span class="t-tag-bg">{{ teacher.intro }}</span>
            </section>
            <section class="t-infor-txt">
              <p class="mt20">{{ teacher.career }}</p>
            </section>
            <div class="clear"/>
          </div>
        </section>
        <!-- /讲师基本信息 结束 -->
        <div class="clear"/>
      </div>
      <section class="mt30">
        <div>
          <header class="comm-title all-teacher-title c-course-content">
            <h2 class="fl tac">
              <span class="c-333">Taught Courses</span>
            </h2>
            <section class="c-tab-title">
              <a href="javascript: void(0)">&nbsp;</a>
            </section>
          </header>
          <!-- 无数据提示 开始-->
          <section v-if="courseList.length===0" class="no-data-wrap">
            <em class="icon30 no-data-ico">&nbsp;</em>
            <span class="c-666 fsize14 ml10 vam">No Infomation Yet</span>
          </section>
          <!-- /无数据提示 结束-->

          <!-- 课程列表 开始-->
          <article class="comm-course-list">
            <ul class="of">
              <li v-for="course in courseList" :key="course.id">
                <div class="cc-l-wrap">
                  <section class="course-img">
                    <img :src="course.cover" class="img-responsive">
                    <div class="cc-mask">
                      <a :href="'/course/'+course.id" title="开始学习" target="_blank" class="comm-btn c-btn-1">Study Now</a>
                    </div>
                  </section>
                  <h3 class="hLh30 txtOf mt10">
                    <a
                      :href="'/course/'+course.id"
                      :title="course.title"
                      class="course-title fsize18 c-333">{{ course.title }}</a>
                  </h3>
                </div>
              </li>
            </ul>
            <div class="clear"/>
          </article>
          <!-- /课程列表 结束-->
        </div>
      </section>
    </section>
    <!-- /讲师介绍 结束 -->
  </div>
</template>

<script>
import teacherApi from '~/api/teacher'

export default {
  /**
   * teacherApi.getById是个异步方法
   * 之前在guli_admin里都是在回调里拿数据，所以肯定拿的到response
   * 如果不想在回调里拿，而想调用多个api方法，为了确保拿到返回的数据，
   * 需要把这个方法改成同步的 --- 加async和await
   */

  // 在这个方法被调用的时候，第一个参数被设定为当前页面的上下文对象，
  async asyncData(page) {
    const response = await teacherApi.getById(page.route.params.id)
    return {
      teacher: response.data.teacher,
      courseList: response.data.courseList
    }
  }
}
</script>
