package ru.bmstu.rpo.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.bmstu.rpo.entity.Artist;
import ru.bmstu.rpo.entity.Country;
import ru.bmstu.rpo.entity.Painting;
import ru.bmstu.rpo.repository.ArtistRepository;
import ru.bmstu.rpo.tools.DataValidationException;

import java.util.*;

@Service
@Data
public class ArtistService {

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    CountryService countryService;

    public Page<Artist> getAllArtists(int page, int limit) {
        return artistRepository.findAll(PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "name")));
    }

    public Optional<Artist> findById(Long id){
        return artistRepository.findById(id);
    }

    public ResponseEntity<Object> createArtist(Artist artist) {
        try {
            Optional<Country> cc = countryService.findById(artist.country.id);
            cc.ifPresent(country -> artist.country = country);
            Artist nc = artistRepository.save(artist);
            return ResponseEntity.ok(nc);
        } catch (Exception ex) {
            String error;
            if (ex.getMessage().contains("artists.name_UNIQUE"))
                error = "countyalreadyexists";
            else
                error = "undefinederror";
            Map<String, String> map = new HashMap<>();
            map.put("error", error);
            return ResponseEntity.ok(map);
        }
    }

    public ResponseEntity<Artist> updateArtist(Long artistId, Artist artistDetails) {
        Optional<Artist> cc = artistRepository.findById(artistId);
        if (cc.isPresent()) {
            Artist artist = entityForUpdate(artistId, artistDetails);
            artistRepository.save(artist);
            return ResponseEntity.ok(artist);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "artist not found");
        }
    }

    public ResponseEntity<Object> deleteArtist(Long artistId) {
        Optional<Artist> artist = artistRepository.findById(artistId);
        Map<String, Boolean> resp = new HashMap<>();
        if (artist.isPresent()) {
            artistRepository.delete(artist.get());
            resp.put("deleted", Boolean.TRUE);
        } else
            resp.put("deleted", Boolean.FALSE);
        return ResponseEntity.ok(resp);
    }

    private Artist entityForUpdate(Long artistId, Artist artistDetails){
        Optional<Artist> cc = artistRepository.findById(artistId);
        Artist artist = cc.get();
        artist.name = artistDetails.name;
        artist.century = artistDetails.century;
        Optional<Country> country = countryService.findById(artistDetails.country.id);
        country.ifPresent(c -> artist.country = c);
        return artist;
    }

    public ResponseEntity<List<Painting>> getArtistPainting(Long artistId) {
        Optional<Artist> cc = artistRepository.findById(artistId);
        if (cc.isPresent()) {
            return ResponseEntity.ok(cc.get().paintings);
        }
        return ResponseEntity.ok(new ArrayList<>());
    }

    public ResponseEntity<Object> createArtistRest(Artist artist) throws DataValidationException {
        try {
            Optional<Country> cc = countryService.findById(artist.country.id);
            cc.ifPresent(country -> artist.country = country);
            Artist nc = artistRepository.save(artist);
            return ResponseEntity.ok(nc);
        }
        catch(Exception ex) {
            throw new DataValidationException("Неизвестная ошибка");
        }
    }

    public ResponseEntity<Artist> updateArtistRest(Long artistId, Artist artistDetails) throws DataValidationException {
        try {
            Optional<Artist> cc = Optional.ofNullable(artistRepository.findById(artistId)
                    .orElseThrow(() -> new DataValidationException("Артист с таким индексом не найдена")));
            if (cc.isPresent()) {
                Artist artist = entityForUpdate(artistId, artistDetails);
                artistRepository.save(artist);
                return ResponseEntity.ok(artist);
            }
        }
        catch (Exception ex) {
            throw new DataValidationException("Неизвестная ошибка");
        }
        return null;
    }

    public ResponseEntity deleteArtistsRest(List<Artist> artists) {
        artistRepository.deleteAll(artists);
        return new ResponseEntity(HttpStatus.OK);
    }
}
