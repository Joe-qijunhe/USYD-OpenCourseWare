package org.joe.ocw.service.edu.service.impl;

import org.joe.ocw.service.edu.entity.CourseDescription;
import org.joe.ocw.service.edu.mapper.CourseDescriptionMapper;
import org.joe.ocw.service.edu.service.CourseDescriptionService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 课程简介 服务实现类
 * </p>
 *
 * @author Joe He
 * @since 2021-12-29
 */
@Service
public class CourseDescriptionServiceImpl extends ServiceImpl<CourseDescriptionMapper, CourseDescription> implements CourseDescriptionService {

}
