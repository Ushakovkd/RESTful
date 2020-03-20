package com.web.audio.entity;

import com.web.audio.entity.field.Genre;

import javax.persistence.*;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "song")
public class Song implements Identifiable, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "song_id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;
    @Column(name = "title")
    private String title;
    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genre genre;
    @Column(name = "price")
    private BigDecimal price;
    @ManyToMany(mappedBy = "songs")
    private List<MusicCollection> collections = new ArrayList<>();
    @ManyToMany(mappedBy = "songs")
    private List<User> users = new ArrayList<>();

    public Song() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public Artist getArtist() {
        return artist;
    }

    public void setArtist(Artist artist) {
        this.artist = artist;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Genre getGenre() {
        return genre;
    }

    public void setGenre(Genre genre) {
        this.genre = genre;
    }

    public List<MusicCollection> getCollections() {
        return collections;
    }

    public void setCollections(List<MusicCollection> collections) {
        this.collections = collections;
    }

    public List<User> getUsers() {
        return users;
    }

    public void setUsers(List<User> users) {
        this.users = users;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        Song song = (Song) object;
        if (id != song.id) {
            return false;
        }
        if (!artist.equals(song.artist)) {
            return false;
        }
        if (!title.equals(song.title)) {
            return false;
        }
        if (!genre.equals(song.genre)) {
            return false;
        }
        if (!price.equals(song.price)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 31 * id + artist.hashCode() + title.hashCode() + genre.hashCode() + price.hashCode();
    }

    @Override
    public String toString() {
        return "Song{" + "id='" + id + "', artist='" + artist + "', title='" + title +
                "', genre='" + genre + "', price='" + price + "'}";
    }
}
