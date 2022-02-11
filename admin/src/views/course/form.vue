<template>
  <div class="app-container">
    <!-- 步骤导航 -->
    <h2 style="text-align: center;">发布新课程</h2>
    <el-steps :active="active" finish-status="success" simple style="margin-bottom: 40px">
      <el-step title="填写课程基本信息" />
      <el-step title="创建课程大纲" />
      <el-step title="发布课程" />
    </el-steps>

    <!-- 假如组件名是大小写组合的，写成CourseInfo或者course-info -->

    <!-- Step1：填写课程基本信息 Info -->
    <info v-if="active === 0" />

    <!-- Step2：创建课程大纲 ChapterIndex -->
    <chapter v-if="active === 1" />

    <!-- Step3：发布课程 Publish -->
    <publish v-if="active === 2 || active === 3" />

  </div>
</template>

<script>
// 引入子组件
import Info from '@/views/course/components/Info'
// 会先去找有没有chapter.vue，没有的话就找Chapter文件夹下的index.vue
import Chapter from '@/views/course/components/Chapter'
import Publish from '@/views/course/components/Publish'

export default {
  components: { Info, Chapter, Publish }, // 注册子组件

  data() {
    return {
      active: 0,
      courseId: null
    }
  },

  created() {
    // 通过获取路由名称，判断进入哪个步骤
    if (this.$route.name === 'CourseInfoEdit') {
      this.courseId = this.$route.params.id
      this.active = 0
    } else if (this.$route.name === 'CourseChapterEdit') {
      this.courseId = this.$route.params.id
      this.active = 1
    }
  }
}
</script>
