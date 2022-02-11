package org.joe.ocw.service.edu.service.impl;

import org.joe.ocw.feign.OssFileService;
import org.joe.ocw.feign.VodMediaService;
import org.joe.ocw.service.edu.entity.ChapterPart;
import org.joe.ocw.service.edu.mapper.ChapterPartMapper;
import org.joe.ocw.service.edu.service.ChapterPartService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 课程视频 服务实现类
 * </p>
 *
 * @author Joe He
 * @since 2021-12-29
 */
@Service
public class ChapterPartServiceImpl extends ServiceImpl<ChapterPartMapper, ChapterPart> implements ChapterPartService {

    @Autowired
    private VodMediaService vodMediaService;
    @Autowired
    private OssFileService ossFileService;

    @Override
    public void removeMediaVideoById(String id) {
        // 根据videoId找到视频id
        ChapterPart chapterPart = baseMapper.selectById(id);
        String videoSourceId = chapterPart.getVideoSourceId();
        vodMediaService.removeVideo(videoSourceId);
    }

    @Override
    public void removeMediaVideoByChapterId(String chapterId) {
        QueryWrapper<ChapterPart> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("video_source_id");
        queryWrapper.eq("chapter_id", chapterId);

        List<Map<String, Object>> maps = baseMapper.selectMaps(queryWrapper);
        List<String> videoSourceIdList = this.getVideoSourceIdList(maps);
        vodMediaService.removeVideoByIdList(videoSourceIdList);
    }

    @Override
    public void removeMediaVideoByCourseId(String courseId) {
        QueryWrapper<ChapterPart> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("video_source_id");
        queryWrapper.eq("course_id", courseId);

        List<Map<String, Object>> maps = baseMapper.selectMaps(queryWrapper);
        List<String> videoSourceIdList = this.getVideoSourceIdList(maps);
        vodMediaService.removeVideoByIdList(videoSourceIdList);
    }

    @Override
    public void removeNoteByChapterId(String chapterId) {
        QueryWrapper<ChapterPart> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("note_url");
        queryWrapper.eq("chapter_id", chapterId);

        List<Map<String, Object>> maps = baseMapper.selectMaps(queryWrapper);
        List<String> noteUrlList = this.getNoteUrlList(maps);
        ossFileService.removeFilesByUrlList(noteUrlList);
    }

    @Override
    public void removeNoteByCourseId(String courseId) {
        QueryWrapper<ChapterPart> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("note_url");
        queryWrapper.eq("course_id", courseId);

        List<Map<String, Object>> maps = baseMapper.selectMaps(queryWrapper);
        List<String> noteUrlList = this.getNoteUrlList(maps);
        ossFileService.removeFilesByUrlList(noteUrlList);
    }

    @Override
    public void removeNoteById(String id) {
        // 根据videoId找到视频id
        ChapterPart chapterPart = baseMapper.selectById(id);
        String noteUrl = chapterPart.getNoteUrl();
        ossFileService.removeFile(noteUrl);
    }

    /**
     * 获取阿里云视频id列表
     */
    private List<String> getVideoSourceIdList(List<Map<String, Object>> maps) {
        List<String> videoSourceIdList = new ArrayList<>();

        for (Map<String, Object> map : maps) {
            String videoSourceId = (String) map.get("video_source_id");
            videoSourceIdList.add(videoSourceId);
        }
        return videoSourceIdList;
    }

    private List<String> getNoteUrlList(List<Map<String, Object>> maps) {
        List<String> NoteUrlList = new ArrayList<>();

        for (Map<String, Object> map : maps) {
            String videoSourceId = (String) map.get("note_url");
            NoteUrlList.add(videoSourceId);
        }
        return NoteUrlList;
    }
}
