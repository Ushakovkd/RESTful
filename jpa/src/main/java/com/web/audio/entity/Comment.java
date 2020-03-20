package com.web.audio.entity;

import javax.persistence.*;

@Entity
@Table(name = "comment")
public class Comment implements Identifiable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comment_id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    @Column(name = "song_id")
    private int songId;
    @Column(name = "text")
    private String text;

    public Comment() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getSongId() {
        return songId;
    }

    public void setSongId(int songId) {
        this.songId = songId;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || getClass() != object.getClass()) {
            return false;
        }
        Comment comment = (Comment) object;
        if (id != comment.id) {
            return false;
        }
        if (!user.equals(comment.user)) {
            return false;
        }
        if (songId != (comment.songId)) {
            return false;
        }
        if (!text.equals(comment.text)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 31 * id + songId + user.hashCode() + text.hashCode();
    }

    @Override
    public String toString() {
        return "Comment{" + "id='" + id + "', user='" + user + "', text='" + text + "'}";
    }

}
