package com.web.audio.entity;

import com.web.audio.entity.field.CollectionType;
import com.web.audio.entity.field.Genre;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "collection")
public class MusicCollection implements Identifiable, Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "collection_id")
    private int id;
    @ManyToOne
    @JoinColumn(name = "artist_id")
    private Artist artist;
    @Column(name = "title")
    private String title;
    @Enumerated(EnumType.STRING)
    @Column(name = "genre")
    private Genre genre;
    @ManyToMany
    @JoinTable(
            name = "collection_songs",
            joinColumns = {@JoinColumn(name = "collection_id")},
            inverseJoinColumns = {@JoinColumn(name = "song_id")}
    )
    private List<Song> songs = new ArrayList<>();
    @Enumerated(EnumType.STRING)
    @Column(name = "type")
    private CollectionType type;

    public MusicCollection() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<Song> getSongs() {
        return songs;
    }

    public void setSongs(List<Song> songs) {
        this.songs = songs;
    }

    public CollectionType getType() {
        return type;
    }

    public void setType(CollectionType type) {
        this.type = type;
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) {
            return true;
        }
        if (object == null || this.getClass() != object.getClass()) {
            return false;
        }
        MusicCollection collection = (MusicCollection) object;

        if (id != collection.id) {
            return false;
        }
        if (!this.artist.equals(collection.artist)) {
            return false;
        }
        if (!this.title.equals(collection.title)) {
            return false;
        }
        if (!this.genre.equals(collection.genre)) {
            return false;
        }
        if (!this.songs.equals(collection.songs)) {
            return false;
        }
        if (!this.type.equals(collection.type)) {
            return false;
        }
        return true;
    }

    @Override
    public int hashCode() {
        return 31 * id + artist.hashCode() + title.hashCode() + genre.hashCode()
                + songs.hashCode() + type.hashCode();
    }

    @Override
    public String toString() {
        return "MusicCollection{" +
                "id=" + id +
                ", artist=" + artist +
                ", title='" + title + '\'' +
                ", genre=" + genre +
                ", type='" + type + '\'' +
                '}';
    }
}
