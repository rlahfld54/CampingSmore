package com.green.campingsmore.community.comment;

import com.green.campingsmore.community.board.model.BoardSelRes;
import com.green.campingsmore.community.comment.model.*;
import com.green.campingsmore.config.security.AuthenticationFacade;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.constraints.Min;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "댓글")
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService service;
    private final AuthenticationFacade FACADE;

    @PostMapping
    @Operation(summary = "댓글 생성")
    public Long insComment(@RequestBody CommentInsDto dto) {
        return service.insComment(dto);
    }
    @PutMapping
    @Operation(summary = "댓글 수정")
    public Long updComment(@RequestBody CommentUpdDto dto){
        CommentEntity entity = new CommentEntity();
        entity.setIcomment(dto.getIcomment());
        entity.setCtnt(dto.getCtnt());
        return service.updComment(entity);
    }
    @PutMapping("/comment")
    @Operation(summary = "댓글 삭제")
    public Long delComment(@RequestBody CommentDelDto dto){
        CommentEntity entity = new CommentEntity();
        entity.setIcomment(dto.getIcomment());
        return service.delComment(entity);

    }
//    @GetMapping("/{iboard}/cmt")
//    @Operation(summary = "게시글에 있는 댓글 보기")
//    public CommentRes selComment(@PathVariable Long iboard,@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "15") @Min(value = 15) int row){
//        CommentPageDto dto = new CommentPageDto();
//        dto.setIboard(iboard);
//        dto.setPage(page);
//        dto.setRow(row);
//        return service.selComment(dto);
    }



