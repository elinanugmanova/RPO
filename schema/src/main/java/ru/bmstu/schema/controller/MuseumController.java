package ru.bmstu.schema.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.schema.entity.Museum;
import ru.bmstu.schema.entity.Painting;
import ru.bmstu.schema.service.MuseumService;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/museums")
public class MuseumController {

    @Autowired
    MuseumService museumService;

    @GetMapping("/")
    public List findAllMuseums() {
        return museumService.findAllMuseums();
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
}
