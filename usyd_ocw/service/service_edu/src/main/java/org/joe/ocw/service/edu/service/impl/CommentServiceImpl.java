package org.joe.ocw.service.edu.service.impl;

import org.joe.ocw.service.edu.entity.Comment;
import org.joe.ocw.service.edu.mapper.CommentMapper;
import org.joe.ocw.service.edu.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 评论 服务实现类
 * </p>
 *
 * @author Joe He
 * @since 2021-12-29
 */
@Service
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

}
