package com.web.audio.controller;

import com.web.audio.dto.MusicCollectionDto;
import com.web.audio.dto.SongDto;
import com.web.audio.entity.Artist;
import com.web.audio.exception.ServiceException;
import com.web.audio.service.ArtistService;
import com.web.audio.service.MusicCollectionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Map;

@Produces(MediaType.APPLICATION_JSON_VALUE)
@RestController
@RequestMapping("/collections")
public class MusicCollectionController {

    private MusicCollectionService collectionService;
    private ArtistService artistService;

    @Autowired
    public MusicCollectionController(MusicCollectionService collectionService, ArtistService artistService) {
        this.collectionService = collectionService;
        this.artistService = artistService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public MusicCollectionDto createCollection(@Valid @RequestBody MusicCollectionDto collectionDto) throws ServiceException {
        String artistName = collectionDto.getArtistName();
        Artist artist = artistService.getArtistByName(artistName);
        return collectionService.createCollection(collectionDto, artist);
    }

    @GetMapping("/{id}")
    public MusicCollectionDto findCollectionById(@PathVariable int id) {
        return collectionService.findCollectionById(id);
    }

    @GetMapping("/{id}/songs")
    public List<SongDto> findCollectionSongsById(@PathVariable int id) {
        return collectionService.getCollectionSongsById(id);
    }

    @GetMapping
    public List<MusicCollectionDto> findCollectionsByVariableNumberParameters(@RequestParam(required = false) Map<String, String> params) {
        if (params.isEmpty()) {
            return collectionService.getAllCollections();
        }
        return collectionService.findCollectionsByVariableNumberParameters(params);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void updateCollectionTitleGenreById(@PathVariable int id, @RequestBody MusicCollectionDto collectionDto) throws ServiceException {
        collectionService.updateCollectionTitleGenreById(id, collectionDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteCollectionById(@PathVariable int id) {
        collectionService.deleteCollectionById(id);
    }

}
