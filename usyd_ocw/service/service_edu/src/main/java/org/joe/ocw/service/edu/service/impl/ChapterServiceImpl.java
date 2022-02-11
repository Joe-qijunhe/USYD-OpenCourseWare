package org.joe.ocw.service.edu.service.impl;

import org.joe.ocw.service.edu.entity.Chapter;
import org.joe.ocw.service.edu.entity.ChapterPart;
import org.joe.ocw.service.edu.entity.vo.ChapterVo;
import org.joe.ocw.service.edu.entity.vo.VideoVo;
import org.joe.ocw.service.edu.mapper.ChapterMapper;
import org.joe.ocw.service.edu.mapper.ChapterPartMapper;
import org.joe.ocw.service.edu.service.ChapterService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 课程 服务实现类
 * </p>
 *
 * @author Joe He
 * @since 2021-12-29
 */
@Service
public class ChapterServiceImpl extends ServiceImpl<ChapterMapper, Chapter> implements ChapterService {

    @Autowired
    private ChapterPartMapper chapterPartMapper;

    @Transactional(rollbackFor = Exception.class)
    @Override
    public boolean removeChapterById(String id) {
        //删除章节的Video
        QueryWrapper<ChapterPart> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("chapter_id", id);
        chapterPartMapper.delete(videoQueryWrapper);

        // 删除章节
        return this.removeById(id);
    }

    @Override
    public List<ChapterVo> nestedList(String courseId) {
        // 通过course_id获取章节列表信息列表
        QueryWrapper<Chapter> chapterQueryWrapper = new QueryWrapper<>();
        chapterQueryWrapper.eq("course_id", courseId);
        chapterQueryWrapper.orderByAsc("sort", "id");
        List<Chapter> chapterList = baseMapper.selectList(chapterQueryWrapper);

        // 通过course_id获取课时信息列表
        QueryWrapper<ChapterPart> videoQueryWrapper = new QueryWrapper<>();
        videoQueryWrapper.eq("course_id", courseId);
        videoQueryWrapper.orderByAsc("sort", "id");
        List<ChapterPart> chapterPartList = chapterPartMapper.selectList(videoQueryWrapper);

        // 组装章节列表
        List<ChapterVo> chapterVoList = new ArrayList<>();

        for (Chapter chapter : chapterList) {
            // 创建chapterVo对象
            ChapterVo chapterVo = new ChapterVo();
            BeanUtils.copyProperties(chapter, chapterVo);
            chapterVoList.add(chapterVo);

            //填充列表数据：Video列表
            List<VideoVo> videoVoList = new ArrayList<>();
            for (ChapterPart chapterPart : chapterPartList) {
                if (chapter.getId().equals(chapterPart.getChapterId())) {
                    VideoVo videoVo = new VideoVo();
                    BeanUtils.copyProperties(chapterPart, videoVo);
                    videoVoList.add(videoVo);
                }
            }

            chapterVo.setChildren(videoVoList);
        }
        return chapterVoList;
    }
}
