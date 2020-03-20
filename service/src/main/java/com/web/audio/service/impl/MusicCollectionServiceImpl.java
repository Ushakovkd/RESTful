package com.web.audio.service.impl;

import com.web.audio.dao.MusicCollectionDao;
import com.web.audio.dto.MusicCollectionDto;
import com.web.audio.dto.SongDto;
import com.web.audio.dto.convert.CollectionDtoConverter;
import com.web.audio.dto.convert.SongDtoConverter;
import com.web.audio.entity.Artist;
import com.web.audio.entity.MusicCollection;
import com.web.audio.entity.Song;
import com.web.audio.entity.field.CollectionType;
import com.web.audio.entity.field.Genre;
import com.web.audio.exception.ServiceException;
import com.web.audio.service.MusicCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Service
@Transactional
public class MusicCollectionServiceImpl implements MusicCollectionService {

    private MusicCollectionDao musicCollectionDao;
    private CollectionDtoConverter collectionDtoConverter;
    private SongDtoConverter songDtoConverter;

    @Autowired
    public MusicCollectionServiceImpl(MusicCollectionDao musicCollectionDao, CollectionDtoConverter collectionDtoConverter,
                                      SongDtoConverter songDtoConverter) {
        this.musicCollectionDao = musicCollectionDao;
        this.collectionDtoConverter = collectionDtoConverter;
        this.songDtoConverter = songDtoConverter;
    }

    @Override
    public MusicCollectionDto createCollection(MusicCollectionDto dto, Artist artist) throws ServiceException {
        MusicCollection foundCollection = findSuchOneCollectionByDto(dto);
        if (foundCollection != null) {
            throw new ServiceException("That collection already exists");
        }
        MusicCollection collection = collectionDtoConverter.convertDtoToMusicCollection(dto, artist);
        int createdCollectionId = musicCollectionDao.saveCollection(collection);
        dto.setCollectionId(createdCollectionId);
        return dto;
    }

    @Override
    public List<MusicCollectionDto> getAllCollections() {
        List<MusicCollection> collections = musicCollectionDao.getAllCollections();
        return collectionDtoConverter.convertMusicCollectionListToDtoList(collections);
    }

    @Override
    public MusicCollectionDto findCollectionById(int id) {
        MusicCollection collection = musicCollectionDao.findCollectionById(id);
        return collectionDtoConverter.convertMusicCollectionToDto(collection);
    }

    @Override
    public List<MusicCollectionDto> findCollectionsByVariableNumberParameters(Map<String, String> params) {
        String typeStr = params.get("type");
        CollectionType type = null;
        if (typeStr != null) {
            type = CollectionType.valueOf(typeStr);
        }
        String artistName = params.get("artistName");
        String title = params.get("title");
        String genreStr = params.get("genre");
        Genre genre = null;
        if (genreStr != null) {
            genre = Genre.valueOf(genreStr);
        }
        List<MusicCollection> collections = musicCollectionDao.findMusicCollectionsByVariableNumberParameters(type,
                artistName, title, genre);
        return collectionDtoConverter.convertMusicCollectionListToDtoList(collections);
    }


    @Override
    public void updateCollectionTitleGenreById(int id, MusicCollectionDto dto) throws ServiceException {
        MusicCollection collection = findSuchOneCollectionByDto(dto);
        if (collection != null) {
            int foundSongId = collection.getId();
            if (id != foundSongId) {
                throw new ServiceException("That collection already exists");
            }
        }
        String title = dto.getTitle();
        Genre genre = dto.getGenre();
        musicCollectionDao.updateCollectionTitleGenreById(id, title, genre);
    }

    @Override
    public void deleteCollectionById(int id) {
        MusicCollection collection = musicCollectionDao.findCollectionById(id);
        musicCollectionDao.removeCollection(collection);
    }

    @Override
    public List<SongDto> getCollectionSongsById(int id) {
        MusicCollection collection = musicCollectionDao.findCollectionById(id);
        List<Song> songs = collection.getSongs();
        return songDtoConverter.convertSongListToSongDtoList(songs);
    }

    @Override
    public void deleteSongFromCollection(int collectionId, Song song) {
        MusicCollection collection = musicCollectionDao.findCollectionById(collectionId);
        List<Song> songs = collection.getSongs();
        songs.remove(song);
        musicCollectionDao.saveCollection(collection);
    }

    @Override
    public boolean hasCollectionTheSong(int collectionId, Song song) {
        MusicCollection collection = musicCollectionDao.findCollectionById(collectionId);
        List<Song> songs = collection.getSongs();

        int songId = song.getId();
        for (Song collectionSong : songs) {
            int collectionSongId = collectionSong.getId();
            if (collectionSongId == songId) {
                return true;
            }
        }
        return false;
    }

    @Override
    public void addSongToCollection(int collectionId, Song song) {
        MusicCollection collection = musicCollectionDao.findCollectionById(collectionId);
        List<Song> songs = collection.getSongs();
        songs.add(song);
        musicCollectionDao.saveCollection(collection);
    }

    private MusicCollection findSuchOneCollectionByDto(MusicCollectionDto dto) {
        CollectionType type = dto.getType();
        String artistName = dto.getArtistName();
        String title = dto.getTitle();
        Genre genre = dto.getGenre();

        List<MusicCollection> collections = musicCollectionDao.findMusicCollectionsByVariableNumberParameters(type,
                artistName, title, genre);
        if (collections.size() > 1) {
            throw new IllegalArgumentException();
        }
        if (collections.isEmpty()) {
            return null;
        }
        return collections.get(0);
    }
}
