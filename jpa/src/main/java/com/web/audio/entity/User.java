package com.web.audio.entity;

import com.web.audio.entity.field.Role;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Entity
@Table(name = "users")
public class User implements Identifiable, Serializable {

    private static final long serialVersionUID = 1L;
    @Enumerated(EnumType.STRING)
    @Column(name = "role")
    private Role role;
    @Id
    @Column(name = "user_id")
    private int id;
    @Column(name = "login")
    private String login;
    @Column(name = "password")
    private String password;
    @Column(name = "discount")
    private int discount;
    @Column(name = "block")
    private boolean block;
    @ManyToMany
    @JoinTable(
            name = "user_songs",
            joinColumns = {@JoinColumn(name = "user_id")},
            inverseJoinColumns = {@JoinColumn(name = "song_id")}
    )
    private List<Song> songs = new ArrayList<>();

    public User() {
    }

    public String getLogin() {
        return login;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public int getDiscount() {
        return discount;
    }

    public void setDiscount(int discount) {
        this.discount = discount;
    }

    public boolean isBlock() {
        return block;
    }

    public void setBlock(boolean block) {
        this.block = block;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public Role getRole() {
        return role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        User user = (User) object;
        if (this.id != user.id) {
            return false;
        }
        if (!this.login.equals(user.login)) {
            return false;
        }
        if (this.role != user.role) {
            return false;
        }
        if (this.discount != user.discount) {
            return false;
        }
        if (this.block != user.block) {
            return false;
        }
        if (!this.password.equals(user.password)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 31 * id + login.hashCode() + role.hashCode() +
                discount + Objects.hashCode(block);
    }

    @Override
    public String toString() {
        return "User{role='" + role + "', login='" + login + "', id='"
                + id + "', discount='" + discount + "', block='" + block + "'}";
    }
}
