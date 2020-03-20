package com.web.audio.service;

import com.web.audio.dto.MusicCollectionDto;
import com.web.audio.dto.SongDto;
import com.web.audio.entity.Artist;
import com.web.audio.entity.Song;
import com.web.audio.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface MusicCollectionService {

    MusicCollectionDto createCollection(MusicCollectionDto collectionDto, Artist artist) throws ServiceException;

    List<MusicCollectionDto> getAllCollections();

    MusicCollectionDto findCollectionById(int id);

    List<MusicCollectionDto> findCollectionsByVariableNumberParameters(Map<String, String> params);

    void updateCollectionTitleGenreById(int id, MusicCollectionDto collectionDto) throws ServiceException;

    void deleteCollectionById(int id);

    List<SongDto> getCollectionSongsById(int id);

    void deleteSongFromCollection(int collectionId, Song song);

    boolean hasCollectionTheSong(int collectionId, Song song);

    void addSongToCollection(int collectionId, Song song);
}
