<template>
  <div class="app-container">

    <!-- 课程信息表单 -->
    <el-form label-width="120px">

      <el-form-item label="课程标题">
        <el-input v-model="courseInfo.title" placeholder=" 示例: COMP2123: Data Structures and Algorithms"/>
      </el-form-item>

      <!-- 课程讲师 -->
      <el-form-item label="课程讲师">
        <el-select
          v-model="courseInfo.teacherId"
          placeholder="请选择">
          <el-option
            v-for="teacher in teacherList"
            :key="teacher.id"
            :label="teacher.name"
            :value="teacher.id"/>
        </el-select>
      </el-form-item>

      <!-- 所属分类：级联下拉列表 -->
      <el-form-item label="课程类别">
        <!-- 一级分类 -->
        <el-select
          v-model="courseInfo.subjectParentId"
          placeholder="请选择"
          @change="subjectChanged">
          <el-option
            v-for="subject in subjectList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
        <!-- 二级分类 -->
        <el-select v-model="courseInfo.subjectId" placeholder="请选择">
          <el-option
            v-for="subject in subjectLevelTwoList"
            :key="subject.id"
            :label="subject.title"
            :value="subject.id"/>
        </el-select>
      </el-form-item>

      <el-form-item label="总课时">
        <el-input-number :min="0" v-model="courseInfo.lessonNum" controls-position="right" placeholder="请填写课程的总课时数"/>
      </el-form-item>

      <!-- 课程简介-->
      <el-form-item label="课程简介">
        <tinymce :height="300" v-model="courseInfo.description"/>
      </el-form-item>

      <!-- 课程封面 -->
      <el-form-item label="课程封面">
        <el-upload
          :show-file-list="false"
          :on-success="handleCoverSuccess"
          :before-upload="beforeCoverUpload"
          :on-error="handleCoverError"
          :action="BASE_API+'/admin/oss/file/upload?module=cover'"
          class="cover-uploader">
          <img v-if="courseInfo.cover" :src="courseInfo.cover">
          <i v-else class="el-icon-plus avatar-uploader-icon"/>
        </el-upload>
      </el-form-item>

      <div style="text-align:center">
        <el-button :disabled="saveBtnDisabled" type="primary" @click="saveAndNext()">保存并下一步</el-button>
      </div>
  </el-form></div>
</template>

<script>

import courseApi from '@/api/course'
import teacherApi from '@/api/teacher'
import subjectApi from '@/api/subject'
// 富文本编辑器
import Tinymce from '@/components/Tinymce'

export default {
  components: { Tinymce },

  data() {
    return {
      saveBtnDisabled: false, // 按钮是否禁用
      courseInfo: {// 课程基本信息
        lessonNum: 0,
        // 以下解决表单数据不全时insert语句非空校验
        teacherId: '',
        subjectId: '',
        subjectParentId: '',
        cover: '',
        description: ''
      },
      teacherList: [], // 讲师列表
      subjectList: [], // 一级分类列表
      subjectLevelTwoList: [], // 二级分类列表
      BASE_API: process.env.BASE_API
    }
  },

  created() {
    // courseId来源父组件
    if (this.$parent.courseId) {
      // 回显，渲染一级和二级类别
      this.fetchCourseInfoById(this.$parent.courseId)
    } else {
      // 新增的话，只需要渲染一级类别
      this.initSubjectList()
    }
    // 获取讲师列表
    this.initTeacherList()
  },

  methods: {
    // 根据id获取课程基本信息（用于回显）
    fetchCourseInfoById(id) {
      courseApi.getCourseInfoById(id).then(response => {
        // 课程信息
        this.courseInfo = response.data.item

        // 初始化分类列表
        subjectApi.getNestedTreeList().then(response => {
          // 填充一级菜单
          this.subjectList = response.data.items

          // 填充二级菜单
          this.subjectList.forEach(subject => {
            // 找到和courseInfo.subjectParentId一致的父类别记录
            if (subject.id === this.courseInfo.subjectParentId) {
              // 拿到当前类别下的子类别列表，将子类别列表填入二级下拉菜单列表
              this.subjectLevelTwoList = subject.children
            }
          })
        })
      })
    },

    initTeacherList() {
      teacherApi.list().then(response => {
        this.teacherList = response.data.items
      })
    },

    initSubjectList() {
      subjectApi.getNestedTreeList().then(response => {
        this.subjectList = response.data.items
      })
    },

    // 切换一级类别下拉列表
    subjectChanged(value) {
      // 遍历一级类别
      this.subjectList.forEach(subject => {
        // 判断当前选中的一级类别是否和当前遍历的一级类别的id一致
        if (subject.id === value) {
          // 清空当前值（不清的话，再次切换一级类别时，二级标题会保持不变）
          this.courseInfo.subjectId = ''
          // 如果一致，则将当前遍历的一级类别的子类别绑定在页面的二级类别列表中
          this.subjectLevelTwoList = subject.children
        }
      })
    },

    // 保存并下一步
    saveAndNext() {
      this.saveBtnDisabled = true
      if (!this.$parent.courseId) {
        this.saveData()
      } else {
        this.updateData()
      }
    },

    // 保存课程信息
    saveData() {
      courseApi.saveCourseInfo(this.courseInfo).then(response => {
        this.$message.success(response.message)
        this.$parent.courseId = response.data.courseId // 获取courseId
        this.$parent.active = 1 // 下一步
      })
    },

    // 更新课程信息
    updateData() {
      courseApi.updateCourseInfoById(this.courseInfo).then(response => {
        this.$message.success(response.message)
        this.$parent.active = 1 // 下一步
      })
    },

    // 上传成功回调
    handleCoverSuccess(res, file) {
      if (res.success) {
        this.courseInfo.cover = res.data.url
        // 强制重新渲染，图片才会显示出来
        this.$forceUpdate()
      } else {
        this.$message.error('上传失败1(非20000)')
      }
    },

    // 上传校验
    beforeCoverUpload(file) {
      const isJPG = file.type === 'image/jpeg'
      const isLt2M = file.size / 1024 / 1024 < 2

      if (!isJPG) {
        this.$message.error('上传头像图片只能是 JPG 格式!')
      }
      if (!isLt2M) {
        this.$message.error('上传头像图片大小不能超过 2MB!')
      }
      return isJPG && isLt2M
    },

    // 错误处理
    handleCoverError() {
      this.$message.error('上传失败(http错误)')
    }
  }
}
</script>

<style>
.tinymce-container {
  position: relative;
  line-height: normal;
}
.cover-uploader .el-upload {
  border: 1px dashed #d9d9d9;
  border-radius: 6px;
  cursor: pointer;
  position: relative;
  overflow: hidden;
}
.cover-uploader .el-upload:hover {
  border-color: #409EFF;
}
.cover-uploader .avatar-uploader-icon {
  font-size: 28px;
  color: #8c939d;
  width: 640px;
  height: 357px;
  line-height: 357px;
  text-align: center;
}
.cover-uploader img {
  width: 640px;
  height: 357px;
  display: block;
}
</style>
