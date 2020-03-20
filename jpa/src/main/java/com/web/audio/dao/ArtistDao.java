package com.web.audio.dao;

import com.web.audio.entity.Artist;

import java.util.Optional;

public interface ArtistDao {

    Optional<Artist> findArtistById(int id);

    Optional<Artist> findArtistByName(String name);

    Integer createArtist(Artist artist);

    void removeArtist(Artist artist);

    void updateArtistNameById(String name, int id);
}
