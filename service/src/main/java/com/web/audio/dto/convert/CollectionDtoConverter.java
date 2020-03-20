package com.web.audio.dto.convert;

import com.web.audio.dto.MusicCollectionDto;
import com.web.audio.entity.Artist;
import com.web.audio.entity.MusicCollection;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class CollectionDtoConverter {

    public MusicCollection convertDtoToMusicCollection(MusicCollectionDto dto, Artist artist) {
        MusicCollection collection = new MusicCollection();
        collection.setType(dto.getType());
        collection.setArtist(artist);
        collection.setTitle(dto.getTitle());
        collection.setGenre(dto.getGenre());

        return collection;
    }

    public MusicCollectionDto convertMusicCollectionToDto(MusicCollection collection) {
        MusicCollectionDto dto = new MusicCollectionDto();

        dto.setCollectionId(collection.getId());
        dto.setType(collection.getType());
        Artist artist = collection.getArtist();
        if (artist != null) {
            dto.setArtistName(artist.getName());
        }
        dto.setTitle(collection.getTitle());
        dto.setGenre(collection.getGenre());

        return dto;
    }

    public List<MusicCollectionDto> convertMusicCollectionListToDtoList(List<MusicCollection> collections) {
        List<MusicCollectionDto> dtos = new ArrayList<>();
        for (MusicCollection collection : collections) {
            MusicCollectionDto dto = convertMusicCollectionToDto(collection);
            dtos.add(dto);
        }
        return dtos;
    }
}
