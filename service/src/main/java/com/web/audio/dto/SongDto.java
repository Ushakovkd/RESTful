package com.web.audio.dto;

import com.web.audio.entity.field.Genre;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;
import java.math.BigDecimal;

public class SongDto implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer songId;
    @NotNull
    private String artistName;
    @NotNull
    private String title;
    @NotNull
    private Genre genre;
    @NotNull
    @Positive
    @Max(value = 1000, message = "should not be greater than 1000")
    private BigDecimal price;

    public SongDto() {
    }

    public Integer getSongId() {
        return songId;
    }

    public void setSongId(Integer songId) {
        this.songId = songId;
    }

    public String getArtistName() {
        return artistName;
    }

    public void setArtistName(String artistName) {
        this.artistName = artistName;
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

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

}
