package com.web.audio.dto;

import com.web.audio.entity.field.CollectionType;
import com.web.audio.entity.field.Genre;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

public class MusicCollectionDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer collectionId;
    @NotNull
    private String artistName;
    @NotNull
    private String title;
    @NotNull
    private Genre genre;
    @NotNull
    private CollectionType type;

    public MusicCollectionDto() {
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
    }

    public Integer getCollectionId() {
        return collectionId;
    }

    public void setCollectionId(Integer collectionId) {
        this.collectionId = collectionId;
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

    public CollectionType getType() {
        return type;
    }

    public void setType(CollectionType type) {
        this.type = type;
    }
}
