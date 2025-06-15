package ru.bmstu.rpo.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.rpo.entity.Country;
import ru.bmstu.rpo.entity.Museum;
import ru.bmstu.rpo.entity.Painting;
import ru.bmstu.rpo.service.MuseumService;
import ru.bmstu.rpo.tools.DataValidationException;

import java.util.List;

@RequestMapping("/api/v1/museums")
@CrossOrigin(origins = "http://localhost:3000")
@Data
@RestController
public class MuseumController {

    @Autowired
    MuseumService museumService;

    @GetMapping("")
    public Page<Museum> getAllMuseums(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        return museumService.getAllMuseums(page, limit);
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createMuseum(@RequestBody Museum museum) {
        return museumService.createMuseum(museum);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Museum> updateMuseum(@PathVariable(value = "id") Long museumId, @RequestBody Museum museumDetails) {
        return museumService.updateMuseum(museumId, museumDetails);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> deleteMuseum(@PathVariable(value = "id") Long museumId) {
        return museumService.deleteMuseum(museumId);
    }

    @GetMapping("/{id}/paintings")
    public ResponseEntity<List<Painting>> getMuseumPainting(@PathVariable(value = "id") Long museumId) {
        return museumService.getMuseumPainting(museumId);
    }

    @GetMapping("/{id}")
    public HttpEntity<Museum> ResponseEntityGetMuseum(@PathVariable(value = "id") Long museumId) throws DataValidationException {
        Museum museum = museumService.findById(museumId)
                .orElseThrow(()->new DataValidationException("Музей с таким индексом не найдена"));
        return ResponseEntity.ok(museum);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createMuseumRest(@Validated @RequestBody Museum museum) throws DataValidationException {
        return museumService.createMuseumRest(museum);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Museum> updateMuseumRest(@PathVariable(value = "id") Long museumId, @Validated @RequestBody Museum museumDetails) throws DataValidationException {
        return museumService.updateMuseumRest(museumId, museumDetails);
    }

    @PostMapping("/deletemuseums")
    public ResponseEntity deleteMuseumsRest(@Validated @RequestBody List<Museum> museums) {
        return museumService.deleteMuseumsRest(museums);
    }
}
