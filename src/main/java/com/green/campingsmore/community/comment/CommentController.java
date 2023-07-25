package com.green.campingsmore.community.comment;

import com.green.campingsmore.community.comment.model.CommentEntity;
import com.green.campingsmore.community.comment.model.CommentInsDto;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService service;

    @PostMapping
    @Operation(summary = "댓글 생성")
    public Long insComment(@RequestBody CommentInsDto dto) {
        return service.insComment(dto);
    }
}

