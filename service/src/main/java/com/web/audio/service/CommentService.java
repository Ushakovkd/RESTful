package com.web.audio.service;

import com.web.audio.dto.CommentDto;

import java.util.List;

public interface CommentService {

    void addComment(CommentDto dto);

    CommentDto findCommentById(int id);

    List<CommentDto> findSongCommentsBySongId(int songId);

    void deleteComment(int id);
}
