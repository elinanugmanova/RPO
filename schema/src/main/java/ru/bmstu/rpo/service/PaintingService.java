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
import ru.bmstu.rpo.entity.Museum;
import ru.bmstu.rpo.entity.Painting;
import ru.bmstu.rpo.repository.PaintingRepository;
import ru.bmstu.rpo.tools.DataValidationException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@Data
public class PaintingService {

    @Autowired
    PaintingRepository paintingRepository;
    @Autowired
    ArtistService artistService;
    @Autowired
    MuseumService museumService;

    public Page<Painting> getAllPaintings(int page, int limit) {
        return paintingRepository.findAll(PageRequest.of(page, limit, Sort.by(Sort.Direction.ASC, "name")));
    }

    public Optional<Painting> findById(Long id){
        return paintingRepository.findById(id);
    }

    public ResponseEntity<Object> createPainting(Painting painting) {
        try {
            Optional<Artist> artist = artistService.findById(painting.artist.id);
            artist.ifPresent(a -> painting.artist = a);
            Optional<Museum> museum = museumService.findById(painting.museum.id);
            museum.ifPresent(m -> painting.museum = m);
            Painting nc = paintingRepository.save(painting);
            return ResponseEntity.ok(nc);
        } catch (Exception ex) {
            String error;
            error = "undefinederror";
            Map<String, String> map = new HashMap<>();
            map.put("error", error);
            return ResponseEntity.ok(map);
        }
    }

    public ResponseEntity<Painting> updatePainting(Long paintingId, Painting paintingDetails){
        Optional<Painting> cc = paintingRepository.findById(paintingId);
        if (cc.isPresent()) {
            Painting painting = entityForUpdate(paintingId, paintingDetails);
            paintingRepository.save(painting);
            return ResponseEntity.ok(painting);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "painting not found");
        }
    }

    public ResponseEntity<Object> deletePainting(Long paintingId) {
        Optional<Painting> painting = paintingRepository.findById(paintingId);
        Map<String, Boolean> resp = new HashMap<>();
        if (painting.isPresent()) {
            paintingRepository.delete(painting.get());
            resp.put("deleted", Boolean.TRUE);
        }
        else
            resp.put("deleted", Boolean.FALSE);
        return ResponseEntity.ok(resp);
    }

    private Painting entityForUpdate(Long paintingId, Painting paintingDetails){
        Optional<Painting> cc = paintingRepository.findById(paintingId);
        Painting painting = cc.get();
        painting.name = paintingDetails.name;
        painting.year = paintingDetails.year;
        Optional<Artist> artist = artistService.findById(painting.artist.id);
        artist.ifPresent(a -> painting.artist = a);
        Optional<Museum> museum = museumService.findById(painting.museum.id);
        museum.ifPresent(m -> painting.museum = m);
        return painting;
    }

    public ResponseEntity<Object> createPaintingRest(Painting painting) throws DataValidationException  {
        try {
            Optional<Artist> artist = artistService.findById(painting.artist.id);
            artist.ifPresent(a -> painting.artist = a);
            Optional<Museum> museum = museumService.findById(painting.museum.id);
            museum.ifPresent(m -> painting.museum = m);
            Painting nc = paintingRepository.save(painting);
            return new ResponseEntity<Object>(nc, HttpStatus.OK);
        }
        catch(Exception ex) {
            throw new DataValidationException("Неизвестная ошибка");
        }
    }

    public ResponseEntity<Painting> updatePaintingRest(Long paintingId, Painting paintingDetails) throws DataValidationException {
        try {
            Optional<Painting> cc = paintingRepository.findById(paintingId);
            if (cc.isPresent()) {
                Painting painting = entityForUpdate(paintingId, paintingDetails);
                paintingRepository.save(painting);
                return ResponseEntity.ok(painting);
            }
            else {
                throw new DataValidationException("Картина с таким индексом не найдена");
            }
        }
        catch (Exception ex) {
            throw new DataValidationException("Неизвестная ошибка");
        }
    }

    public ResponseEntity deletePaintingsRest(List<Painting> paintings) {
        paintingRepository.deleteAll(paintings);
        return new ResponseEntity(HttpStatus.OK);
    }
}
