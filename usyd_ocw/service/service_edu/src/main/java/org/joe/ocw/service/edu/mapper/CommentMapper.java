package org.joe.ocw.service.edu.mapper;

import org.joe.ocw.service.edu.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

/**
 * <p>
 * 评论 Mapper 接口
 * </p>
 *
 * @author Joe He
 * @since 2021-12-29
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

}
