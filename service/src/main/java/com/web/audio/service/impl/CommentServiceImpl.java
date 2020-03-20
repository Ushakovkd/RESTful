package com.web.audio.service.impl;

import com.web.audio.dao.CommentDao;
import com.web.audio.dao.UserDao;
import com.web.audio.dto.CommentDto;
import com.web.audio.dto.convert.CommentDtoConverter;
import com.web.audio.entity.Comment;
import com.web.audio.entity.User;
import com.web.audio.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class CommentServiceImpl implements CommentService {

    private CommentDao commentDao;
    private UserDao userDao;
    private CommentDtoConverter dtoConverter;

    @Autowired
    public CommentServiceImpl(CommentDao commentDao, UserDao userDao, CommentDtoConverter dtoConverter) {
        this.commentDao = commentDao;
        this.userDao = userDao;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public void addComment(CommentDto dto) {
        String login = dto.getUserName();
        Optional<User> optionalUser = userDao.findUserByLogin(login);
        if (optionalUser.isEmpty()) {
            throw new IllegalArgumentException();
        }
        User user = optionalUser.get();
        Comment comment = dtoConverter.convertDtoToComment(dto, user);
        commentDao.createComment(comment);
    }

    @Override
    public CommentDto findCommentById(int id) {
        Comment comment = commentDao.findCommentById(id);
        return dtoConverter.convertCommentToDto(comment);
    }

    @Override
    public List<CommentDto> findSongCommentsBySongId(int songId) {
        List<Comment> comments = commentDao.findSongCommentsBySongId(songId);
        return dtoConverter.convertCommentListToDtoList(comments);
    }

    @Override
    public void deleteComment(int id) {
        Comment comment = commentDao.findCommentById(id);
        commentDao.removeComment(comment);
    }
}
