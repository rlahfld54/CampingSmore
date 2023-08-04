package com.green.campingsmore.community.comment;

import com.green.campingsmore.community.board.model.BoardListVo;
import com.green.campingsmore.community.comment.model.*;
import com.green.campingsmore.config.security.AuthenticationFacade;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.ceil;

@Service
@RequiredArgsConstructor
public class CommentService {
    private final CommentMapper mapper;
    private final AuthenticationFacade FACADE;

    public Long insComment(CommentInsDto dto){
        CommentEntity entity = new CommentEntity();
        entity.setIboard(dto.getIboard());
        entity.setIuser(FACADE.getLoginUserPk());
        entity.setCtnt(dto.getCtnt());
        return mapper.insComment(entity);
    }
    public Long updComment(CommentEntity entity){
        entity.setIuser(FACADE.getLoginUserPk());
        return mapper.updComment(entity);
    }
    public Long delComment(CommentEntity entity){
        entity.setIuser(FACADE.getLoginUserPk());

        return mapper.delComment(entity);
    }
    public CommentRes selComment(CommentPageDto dto){
        int page = dto.getPage() -1;
        dto.setStartIdx(page*dto.getRow());
        List<CommentVo> list = mapper.selComment(dto);
        double maxpage = mapper.maxComment(dto);
        int mc=(int) ceil( maxpage/dto.getRow());

        int isMore = mc <dto.getPage() ? 0:1;
        return CommentRes.builder()
                .isMore(isMore)
                .row(dto.getRow())
                .maxpage(mc)
                .nowPage(dto.getPage())
                .list(list)
                .build();
    }
}
