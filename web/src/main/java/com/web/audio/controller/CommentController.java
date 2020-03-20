package com.web.audio.controller;

import com.web.audio.dto.CommentDto;
import com.web.audio.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;

@Produces(MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/comments")
public class CommentController {

    private CommentService commentService;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @PostMapping("/")
    public void createComment(@Valid @RequestBody CommentDto commentDto) {
        commentService.addComment(commentDto);
    }

    @GetMapping("/{id}")
    public CommentDto findCommentById(@PathVariable int id) {
        return commentService.findCommentById(id);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteComment(@PathVariable int id) {
        commentService.deleteComment(id);
    }
}
