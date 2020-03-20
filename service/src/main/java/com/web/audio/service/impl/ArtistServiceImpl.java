package com.web.audio.service.impl;

import com.web.audio.dao.ArtistDao;
import com.web.audio.entity.Artist;
import com.web.audio.service.ArtistService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional
public class ArtistServiceImpl implements ArtistService {

    private ArtistDao artistDao;

    @Autowired
    public ArtistServiceImpl(ArtistDao artistDao) {
        this.artistDao = artistDao;
    }

    @Override
    public int getArtistId(String artistName) {
        Optional<Artist> optionalArtist = artistDao.findArtistByName(artistName);
        if (optionalArtist.isEmpty()) {
            Artist artist = new Artist(artistName);
            return artistDao.createArtist(artist);
        }
        Artist artist = optionalArtist.get();
        return artist.getId();
    }

    @Override
    public Artist getArtistByName(String artistName) {
        Optional<Artist> optionalArtist = artistDao.findArtistByName(artistName);
        if (optionalArtist.isEmpty()) {
            Artist artist = new Artist(artistName);
            int createdArtistId = artistDao.createArtist(artist);
            artist.setId(createdArtistId);
            return artist;
        }
        return optionalArtist.get();
    }
}
