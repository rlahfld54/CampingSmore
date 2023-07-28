package com.green.campingsmore.community.comment;

import com.green.campingsmore.community.board.model.BoardListVo;
import com.green.campingsmore.community.comment.model.*;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

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
    public Long updComment(CommentEntity entity){
        return mapper.updComment(entity);
    }
    public Long delComment(CommentDelDto dto){
        return mapper.delComment(dto);
    }
    public CommentRes selComment(CommentPageDto dto){
        int page = dto.getPage() -1;
        dto.setStartIdx(page*dto.getRow());
        List<CommentVo> list = mapper.selComment(dto);
        Long maxpage = mapper.maxComment();
        int mc=(int) Math.ceil((double) maxpage/dto.getRow());

        int isMore = mc <dto.getPage() ? 0:1;
        int num = mc - dto.getPage();
        return CommentRes.builder()
                .isMore(isMore)
                .row(dto.getRow())
                .maxpage(mc)
                .list(list)
                .nowPage(dto.getPage())
                .midPage(num)
                .build();
    }


}
