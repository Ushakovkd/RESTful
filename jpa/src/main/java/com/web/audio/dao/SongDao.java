package com.web.audio.dao;

import com.web.audio.entity.Song;
import com.web.audio.entity.field.Genre;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

public interface SongDao {

    Integer saveSong(Song song);

    Optional<Song> findSongById(int songId);

    void updateSongTitleGenrePriceById(int id, String title, Genre genre, BigDecimal price);

    void removeSong(Song song);

    List<Song> findSongsByVariableNumberParameters(String artistName, String title, Genre genre);

    List<Song> getAllSongs();
}
