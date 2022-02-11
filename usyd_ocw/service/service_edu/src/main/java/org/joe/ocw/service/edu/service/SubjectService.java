package org.joe.ocw.service.edu.service;

import org.joe.ocw.service.edu.entity.Subject;
import org.joe.ocw.service.edu.entity.vo.SubjectVo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.io.InputStream;
import java.util.List;

/**
 * <p>
 * 课程科目 服务类
 * </p>
 *
 * @author Joe He
 * @since 2021-12-29
 */
public interface SubjectService extends IService<Subject> {

    void batchImport(InputStream inputStream);

    List<SubjectVo> nestedList();
}
