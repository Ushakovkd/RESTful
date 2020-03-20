package com.web.audio.controller;

import com.web.audio.dto.CommentDto;
import com.web.audio.dto.SongDto;
import com.web.audio.entity.Artist;
import com.web.audio.exception.ServiceException;
import com.web.audio.service.ArtistService;
import com.web.audio.service.CommentService;
import com.web.audio.service.SongService;
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
@RequestMapping("/songs")
public class SongController {

    private SongService songService;
    private ArtistService artistService;
    private CommentService commentService;

    @Autowired
    public SongController(SongService songService, ArtistService artistService, CommentService commentService) {
        this.songService = songService;
        this.artistService = artistService;
        this.commentService = commentService;
    }

    @PostMapping
    @PreAuthorize("hasAuthority('ADMIN')")
    public SongDto createSong(@Valid @RequestBody SongDto songDto) throws ServiceException {
        String artistName = songDto.getArtistName();
        Artist artist = artistService.getArtistByName(artistName);
        return songService.createSong(songDto, artist);
    }

    @GetMapping
    public List<SongDto> findSongsByVariableNumberParameters(@RequestParam(required = false) Map<String, String> params) {
        if (params.isEmpty()) {
            return songService.getAllSongs();
        }
        return songService.findSongByVariableNumberParameters(params);
    }

    @GetMapping("/{id}")
    public SongDto getSongById(@PathVariable int id) {
        return songService.findSongById(id);
    }

    @GetMapping("/{id}/comments")
    public List<CommentDto> getSongComments(@PathVariable int id) {
        return commentService.findSongCommentsBySongId(id);
    }

    @PatchMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void updateSongTitleGenrePriceById(@PathVariable int id, @RequestBody SongDto songDto) throws ServiceException {
        songService.updateSongTitleGenrePriceById(id, songDto);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteSong(@PathVariable int id) {
        songService.removeSong(id);
    }
}
