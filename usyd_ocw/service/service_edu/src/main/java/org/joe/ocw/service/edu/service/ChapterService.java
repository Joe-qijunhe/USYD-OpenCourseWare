package org.joe.ocw.service.edu.service;

import org.joe.ocw.service.edu.entity.Chapter;
import org.joe.ocw.service.edu.entity.vo.ChapterVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程 服务类
 * </p>
 *
 * @author Joe He
 * @since 2021-12-29
 */
public interface ChapterService extends IService<Chapter> {

    boolean removeChapterById(String id);

    List<ChapterVo> nestedList(String courseId);
}
