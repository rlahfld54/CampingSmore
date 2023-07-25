package com.green.campingsmore.community.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/comment")
public class CommentController {
    private final CommentService service;
    @Autowired

    public CommentController(CommentService service) {
        this.service = service;
    }
}
