package ru.bmstu.schema.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.bmstu.schema.entity.Museum;
import ru.bmstu.schema.entity.Painting;
import ru.bmstu.schema.repository.MuseumRepository;

import java.util.*;

@Service
@Data
public class MuseumService {

    @Autowired
    MuseumRepository museumRepository;

    public List findAllMuseums(){
        return museumRepository.findAll();
    }

    public Optional<Museum> findById(Long id){
        return museumRepository.findById(id);
    }

    public ResponseEntity<Object> createMuseum(Museum museum) {
        try {
            Museum nc = museumRepository.save(museum);
            return ResponseEntity.ok(nc);
        } catch (Exception ex) {
            String error;
            error = "undefinederror";
            Map<String, String> map = new HashMap<>();
            map.put("error", error);
            return ResponseEntity.ok(map);
        }
    }

    public ResponseEntity<Museum> updateMuseum(Long museumId, Museum museumDetails){
        Optional<Museum> cc = museumRepository.findById(museumId);
        if (cc.isPresent()) {
            Museum museum = entityForUpdate(museumId, museumDetails);
            museumRepository.save(museum);
            return ResponseEntity.ok(museum);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "museum not found");
        }
    }

    public ResponseEntity<Object> deleteMuseum(Long museumId) {
        Optional<Museum> country = museumRepository.findById(museumId);
        Map<String, Boolean> resp = new HashMap<>();
        if (country.isPresent()) {
            museumRepository.delete(country.get());
            resp.put("deleted", Boolean.TRUE);
        }
        else
            resp.put("deleted", Boolean.FALSE);
        return ResponseEntity.ok(resp);
    }

    private Museum entityForUpdate(Long museumId, Museum museumDetails){
        Optional<Museum> cc = museumRepository.findById(museumId);
        Museum museum = cc.get();
        museum.name = museumDetails.name;
        museum.location = museumDetails.location;
        return museum;
    }

    public ResponseEntity<List<Painting>> getMuseumPainting(Long museumId) {
        Optional<Museum> cc = museumRepository.findById(museumId);
        if (cc.isPresent()) {
            return ResponseEntity.ok(cc.get().paintings);
        }
        return ResponseEntity.ok(new ArrayList<>());
    }
}
