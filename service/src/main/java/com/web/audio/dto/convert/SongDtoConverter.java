package com.web.audio.dto.convert;

import com.web.audio.dto.SongDto;
import com.web.audio.entity.Artist;
import com.web.audio.entity.Song;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class SongDtoConverter {

    public Song convertDtoToSong(SongDto dto, Artist artist) {

        Song song = new Song();

        song.setArtist(artist);
        song.setTitle(dto.getTitle());
        song.setGenre(dto.getGenre());
        song.setPrice(dto.getPrice());

        return song;
    }

    public SongDto convertSongToDto(Song song) {

        SongDto dto = new SongDto();

        dto.setSongId(song.getId());
        dto.setArtistName(song.getArtist().getName());
        dto.setTitle(song.getTitle());
        dto.setGenre(song.getGenre());
        dto.setPrice(song.getPrice());

        return dto;
    }

    public List<SongDto> convertSongListToSongDtoList(List<Song> songs) {
        List<SongDto> dtos = new ArrayList<>();
        for (Song song : songs) {
            SongDto dto = convertSongToDto(song);
            dtos.add(dto);
        }
        return dtos;
    }

}
