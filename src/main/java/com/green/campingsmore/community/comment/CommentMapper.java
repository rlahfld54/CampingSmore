package com.green.campingsmore.community.comment;

import com.green.campingsmore.community.comment.model.CommentEntity;
import com.green.campingsmore.community.comment.model.CommentInsDto;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface CommentMapper {
    Long insComment(CommentEntity entity);

}
