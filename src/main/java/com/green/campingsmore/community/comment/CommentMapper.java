package com.green.campingsmore.community.comment;

import com.green.campingsmore.community.comment.model.*;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface CommentMapper {
    Long insComment(CommentEntity entity);
    Long updComment(CommentEntity entity);
    Long delComment(CommentDelDto dto);
    List<CommentVo> selComment(CommentPageDto dto);
    Long maxComment(CommentPageDto dto);

}
