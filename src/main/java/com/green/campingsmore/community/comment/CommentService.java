package com.green.campingsmore.community.comment;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CommentService {
    private final CommentMapper mapper;
@Autowired
    public CommentService(CommentMapper mapper) {
        this.mapper = mapper;
    }
}
