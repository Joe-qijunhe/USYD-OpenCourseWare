<template>
  <!-- 添加和修改课时表单 -->
  <el-dialog :visible="dialogVisible" title="添加课时" @close="close()">
    <el-form :model="chapterPart" label-width="120px">
      <el-form-item label="课时标题">
        <el-input v-model="chapterPart.title"/>
      </el-form-item>
      <el-form-item label="课时排序">
        <el-input-number v-model="chapterPart.sort" :min="0" />
      </el-form-item>
      <!-- 上传视频 -->
      <el-form-item label="上传视频">
        <el-upload
          ref="upload"
          :auto-upload="false"
          :on-success="handleUploadSuccess"
          :on-error="handleUploadError"
          :on-exceed="handleUploadExceed"
          :before-remove="handleBeforeRemove"
          :on-remove="handleOnRemove"
          :file-list="fileList"
          :limit="1"
          :action="BASE_API+'/admin/vod/media/upload'">
          <el-button slot="trigger" size="small" type="primary">选择视频</el-button>
          <el-button
            :disabled="uploadBtnDisabled"
            style="margin-left: 10px;"
            size="small"
            type="success"
            @click="submitUpload()">上传</el-button>
        </el-upload>
      </el-form-item>

      <el-form-item>
        <el-upload
          :on-success="handleUploadNoteSuccess"
          :action="BASE_API+'/admin/oss/file/upload?module=notes'">
          <el-button size="small" type="primary">点击上传课件</el-button>
        </el-upload>
      </el-form-item>

    </el-form>
    <div slot="footer" class="dialog-footer">
      <el-button @click="close()">取 消</el-button>
      <el-button type="primary" @click="saveOrUpdate()">确 定</el-button>
    </div>
  </el-dialog>
</template>

<script>
import chapterPartApi from '@/api/chapterPart'
import vodApi from '@/api/vod'

export default {

  data() {
    return {
      dialogVisible: false,
      chapterPart: {
        sort: 0
      },
      fileList: [], // 上传文件列表
      uploadBtnDisabled: false,
      BASE_API: process.env.BASE_API
    }
  },

  methods: {
    // 上传多于一个文件
    handleUploadExceed(files, fileList) {
      this.$message.warning('想要重新上传文件，请先删除已上传的文件')
    },

    // 上传
    submitUpload() {
      this.uploadBtnDisabled = true // 禁用按钮
      this.$refs.upload.submit() // 提交上传请求
    },

    // 视频上传成功的回调
    handleUploadSuccess(response, file, fileList) {
      this.uploadBtnDisabled = false
      if (response.success) {
        this.chapterPart.videoSourceId = response.data.videoId
      } else {
        this.$message.error('上传失败（非20000）')
      }
    },

    // 笔记上传成功的回调
    handleUploadNoteSuccess(response, file, fileList) {
      if (response.success) {
        this.chapterPart.noteUrl = response.data.url
      } else {
        this.$message.error('上传失败（非20000）')
      }
    },

    // 失败回调
    handleUploadError() {
      this.uploadBtnDisabled = false
      this.$message.error('上传失败（http）')
    },

    // 删除视频文件确认
    handleBeforeRemove(file, fileList) {
      return this.$confirm(`确定移除 ${file.name}？`)
    },

    // 执行视频文件的删除
    handleOnRemove(file, fileList) {
      // 还没有点过上传视频，不需要删除云上的视频
      if (!this.chapterPart.videoSourceId) {
        return
      }
      vodApi.removeByVodId(this.chapterPart.videoSourceId).then(response => {
        this.chapterPart.videoSourceId = ''
        this.chapterPart.videoOriginalName = ''
        // 假如用户点击删除了视频，但是没点表单的确定，而是直接关掉了表单，虽然云上的视频已经被删了，但数据库里的videoSourceId和videoOriginalName就没删掉
        // 所以这里要强制更新下数据库的video信息
        chapterPartApi.updateById(this.chapterPart)
        this.$message.success(response.message)
      })
    },

    open(chapterId, videoId) {
      this.dialogVisible = true
      this.chapterPart.chapterId = chapterId
      // 有videoId的话就是课时的编辑，需要回显。没有的话就是课时的新增
      if (videoId) {
        chapterPartApi.getById(videoId).then(response => {
          this.chapterPart = response.data.item
          // 回显视频名称
          if (this.chapterPart.videoOriginalName) {
            this.fileList = [{ 'name': this.chapterPart.videoOriginalName }]
          }
        })
      }
    },

    close() {
      this.dialogVisible = false
      // 重置表单
      this.resetForm()
    },

    resetForm() {
      this.chapterPart = {
        sort: 0
      }
      // 重置视频上传列表
      this.fileList = []
    },

    saveOrUpdate() {
      if (!this.chapterPart.id) {
        this.save()
      } else {
        this.update()
      }
    },

    save() {
      this.chapterPart.courseId = this.$parent.$parent.courseId
      chapterPartApi.save(this.chapterPart).then(response => {
        this.$message.success(response.message)
        // 关闭组件
        this.close()
        // 刷新列表
        this.$parent.fetchNodeList()
      })
    },

    update() {
      chapterPartApi.updateById(this.chapterPart).then(response => {
        this.$message.success(response.message)
        // 关闭组件
        this.close()
        // 刷新列表
        this.$parent.fetchNodeList()
      })
    }
  }
}
</script>
