package com.web.audio.dao;

import com.web.audio.entity.Comment;

import java.util.List;

public interface CommentDao {

    Comment findCommentById(int id);

    List<Comment> findSongCommentsBySongId(int songId);

    void createComment(Comment comment);

    void removeComment(Comment comment);

    void updateCommentTextById(String text, int id);
}
