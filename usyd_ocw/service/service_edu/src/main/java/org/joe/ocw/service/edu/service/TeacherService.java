package org.joe.ocw.service.edu.service;

import org.joe.ocw.service.edu.entity.Teacher;
import org.joe.ocw.service.edu.entity.vo.TeacherQueryVo;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 讲师 服务类
 * </p>
 *
 * @author Joe He
 * @since 2021-12-29
 */
public interface TeacherService extends IService<Teacher> {

    IPage<Teacher> selectPage(Page<Teacher> pageParam, TeacherQueryVo teacherQueryVo);

    List<Map<String, Object>> selectNameList(String key);

    boolean removeAvatarById(String id);

    /**
     * 根据讲师id获取讲师详情页数据
     * @param id
     * @return
     */
    Map<String, Object> selectTeacherInfoById(String id);

    List<Teacher> selectHotTeacher();
}
