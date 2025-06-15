package ru.bmstu.rpo.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.rpo.entity.Artist;
import ru.bmstu.rpo.entity.Painting;
import ru.bmstu.rpo.service.ArtistService;
import ru.bmstu.rpo.tools.DataValidationException;

import java.util.List;

@RequestMapping("/api/v1/artists")
@Data
@RestController
public class ArtistController {

    @Autowired
    ArtistService artistService;

    @GetMapping("")
    public Page<Artist> getAllArtists(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        return artistService.getAllArtists(page, limit);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createArtist(@RequestBody Artist artist) {
        return artistService.createArtist(artist);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Artist> updateArtist(@PathVariable(value = "id") Long artistId, @RequestBody Artist artistDetails) {
        return artistService.updateArtist(artistId, artistDetails);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> deleteArtist(@PathVariable(value = "id") Long artistId) {
        return artistService.deleteArtist(artistId);
    }

    @GetMapping("/{id}/paintings")
    public ResponseEntity<List<Painting>> getArtistPainting(@PathVariable(value = "id") Long artistId) {
        return artistService.getArtistPainting(artistId);
    }

    @GetMapping("/{id}")
    public HttpEntity<Artist> ResponseEntityGetArtist(@PathVariable(value = "id") Long artistId) throws DataValidationException {
        Artist artist = artistService.findById(artistId)
                .orElseThrow(()->new DataValidationException("Страна с таким индексом не найдена"));
        return ResponseEntity.ok(artist);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createArtistRest(@Validated @RequestBody Artist artist) throws DataValidationException {
        return artistService.createArtistRest(artist);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Artist> updateArtistRest(@PathVariable(value = "id") Long artistId, @Validated @RequestBody Artist artistDetails) throws DataValidationException {
        return artistService.updateArtistRest(artistId, artistDetails);
    }

    @PostMapping("/deleteartists")
    public ResponseEntity deleteArtistsRest(@Validated @RequestBody List<Artist> artists) {
        return artistService.deleteArtistsRest(artists);
    }
}