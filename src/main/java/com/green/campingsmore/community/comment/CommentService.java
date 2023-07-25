package com.green.campingsmore.community.comment;

import com.green.campingsmore.community.comment.model.CommentEntity;
import com.green.campingsmore.community.comment.model.CommentInsDto;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper mapper;

    public Long insComment(CommentInsDto dto){
        CommentEntity entity = new CommentEntity();
        entity.setIboard(dto.getIboard());
        entity.setIuser(dto.getIuser());
        entity.setCtnt(dto.getCtnt());
        return mapper.insComment(entity);
    }

}
