package org.joe.ocw.service.edu.service;

import org.joe.ocw.service.edu.entity.ChapterPart;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author Joe He
 * @since 2021-12-29
 */
public interface ChapterPartService extends IService<ChapterPart> {

    void removeMediaVideoById(String id);

    void removeMediaVideoByChapterId(String chapterId);

    void removeMediaVideoByCourseId(String courseId);

    void removeNoteByChapterId(String chapterId);

    void removeNoteByCourseId(String courseId);

    void removeNoteById(String id);
}
