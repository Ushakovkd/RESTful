package com.web.audio.service;

import com.web.audio.entity.Artist;

public interface ArtistService {

    int getArtistId(String artistName);

    Artist getArtistByName(String artistName);
}
