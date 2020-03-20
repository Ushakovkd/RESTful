package com.web.audio.dao;

import com.web.audio.entity.MusicCollection;
import com.web.audio.entity.field.CollectionType;
import com.web.audio.entity.field.Genre;

import java.util.List;

public interface MusicCollectionDao {

    Integer saveCollection(MusicCollection collection);

    List<MusicCollection> getAllCollections();

    MusicCollection findCollectionById(int id);

    List<MusicCollection> findMusicCollectionsByVariableNumberParameters(CollectionType type, String artistName,
                                                                         String title, Genre genre);

    void updateCollectionTitleGenreById(int id, String title, Genre genre);

    void removeCollection(MusicCollection collection);
}
