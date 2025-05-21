package ru.bmstu.schema.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.bmstu.schema.entity.Artist;
import ru.bmstu.schema.entity.Country;
import ru.bmstu.schema.entity.Painting;
import ru.bmstu.schema.repository.ArtistRepository;

import java.util.*;

@Service
@Data
public class ArtistService {

    @Autowired
    ArtistRepository artistRepository;

    @Autowired
    CountryService countryService;

    public List findAllArtists() {
        return artistRepository.findAll();
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
}
