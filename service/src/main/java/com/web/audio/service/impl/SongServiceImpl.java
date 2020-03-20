package com.web.audio.service.impl;

import com.web.audio.dao.SongDao;
import com.web.audio.dto.SongDto;
import com.web.audio.dto.convert.SongDtoConverter;
import com.web.audio.entity.Artist;
import com.web.audio.entity.Song;
import com.web.audio.entity.field.Genre;
import com.web.audio.exception.ServiceException;
import com.web.audio.service.SongService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Transactional
public class SongServiceImpl implements SongService {

    private static final Logger LOGGER = LogManager.getLogger(SongServiceImpl.class);
    private static final String SPLITTER = " - ";

    private SongDao songDao;
    private SongDtoConverter dtoConverter;

    @Autowired
    public SongServiceImpl(SongDao songDao, SongDtoConverter dtoConverter) {
        this.songDao = songDao;
        this.dtoConverter = dtoConverter;
    }

    @Override
    public SongDto createSong(SongDto songDto, Artist artist) throws ServiceException {
        Song foundSong = findSuchOneSongByDto(songDto);
        if (foundSong != null) {
            throw new ServiceException("That song already exists");
        }
        Song song = dtoConverter.convertDtoToSong(songDto, artist);
        int songId = songDao.saveSong(song);
        songDto.setSongId(songId);
        return songDto;
    }

    @Override
    public List<SongDto> getAllSongs() {
        List<Song> songs = songDao.getAllSongs();
        return dtoConverter.convertSongListToSongDtoList(songs);
    }

    @Override
    public SongDto findSongById(int songId) {
        Optional<Song> optionalSong = songDao.findSongById(songId);
        if (optionalSong.isEmpty()) {
            throw new IllegalArgumentException("Song not found by id");
        }
        Song song = optionalSong.get();
        return dtoConverter.convertSongToDto(song);
    }

    @Override
    public List<SongDto> findSongByVariableNumberParameters(Map<String, String> params) {
        String artistName = params.get("artistName");
        String title = params.get("title");
        String genreStr = params.get("genre");
        Genre genre = null;
        if (genreStr != null) {
            genre = Genre.valueOf(genreStr);
        }
        List<Song> songs = songDao.findSongsByVariableNumberParameters(artistName, title, genre);
        return dtoConverter.convertSongListToSongDtoList(songs);
    }

    @Override
    public void updateSongTitleGenrePriceById(int id, SongDto songDto) throws ServiceException {
        Song song = findSuchOneSongByDto(songDto);

        if (song != null) {
            int foundSongId = song.getId();
            if (id != foundSongId) {
                throw new ServiceException("That song already exists");
            } else {
                throw new ServiceException("Nothing to update");
            }
        }
        String title = songDto.getTitle();
        Genre genre = songDto.getGenre();
        BigDecimal price = songDto.getPrice();
        songDao.updateSongTitleGenrePriceById(id, title, genre, price);
    }

    @Override
    public void removeSong(int id) {
        Optional<Song> optionalSong = songDao.findSongById(id);
        if (optionalSong.isEmpty()) {
            throw new IllegalArgumentException();
        }
        Song song = optionalSong.get();
        songDao.removeSong(song);
    }

    @Override
    public List<String> getAllSongsNames() {
        List<Song> allSongs = songDao.getAllSongs();
        List<String> names = new ArrayList<>();
        for (Song song : allSongs) {
            String name = song.getArtist().getName() + SPLITTER + song.getTitle();
            names.add(name);
        }
        return names;
    }

    private Song findSuchOneSongByDto(SongDto dto) {
        String artistName = dto.getArtistName();
        String title = dto.getTitle();
        Genre genre = dto.getGenre();

        List<Song> songs = songDao.findSongsByVariableNumberParameters(artistName, title, genre);
        if (songs.size() > 1) {
            LOGGER.debug("Found more than one song");
        }
        if (songs.isEmpty()) {
            return null;
        }
        return songs.get(0);
    }
}
