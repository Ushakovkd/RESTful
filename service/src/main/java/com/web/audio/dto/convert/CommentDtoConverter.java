package com.web.audio.dto.convert;

import com.web.audio.dto.CommentDto;
import com.web.audio.entity.Comment;
import com.web.audio.entity.User;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CommentDtoConverter {

    public Comment convertDtoToComment(CommentDto dto, User user) {
        Comment comment = new Comment();

        comment.setId(dto.getCommentId());
        comment.setSongId(dto.getSongId());
        comment.setText(dto.getText());
        comment.setUser(user);

        return comment;
    }

    public CommentDto convertCommentToDto(Comment comment) {
        CommentDto dto = new CommentDto();

        dto.setCommentId(comment.getId());
        dto.setSongId(comment.getSongId());
        User user = comment.getUser();
        dto.setUserName(user.getLogin());
        dto.setText(comment.getText());

        return dto;
    }

    public List<CommentDto> convertCommentListToDtoList(List<Comment> comments) {
        List<CommentDto> dtos = new ArrayList<>();
        for (Comment comment : comments) {
            CommentDto dto = convertCommentToDto(comment);
            dtos.add(dto);
        }
        return dtos;
    }

}
