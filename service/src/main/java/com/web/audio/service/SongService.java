package com.web.audio.service;

import com.web.audio.dto.SongDto;
import com.web.audio.entity.Artist;
import com.web.audio.exception.ServiceException;

import java.util.List;
import java.util.Map;

public interface SongService {

    SongDto createSong(SongDto songDto, Artist artist) throws ServiceException;

    List<SongDto> getAllSongs();

    SongDto findSongById(int songId);

    List<SongDto> findSongByVariableNumberParameters(Map<String, String> params);

    void updateSongTitleGenrePriceById(int id, SongDto songDto) throws ServiceException;

    void removeSong(int id);

    List<String> getAllSongsNames();

}
