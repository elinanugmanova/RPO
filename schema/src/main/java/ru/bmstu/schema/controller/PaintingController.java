package ru.bmstu.schema.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.schema.entity.Painting;
import ru.bmstu.schema.service.PaintingService;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/paintings")
public class PaintingController {

    @Autowired
    PaintingService paintingService;

    @GetMapping("/")
    public List findAllPainting() {
        return paintingService.findAllPainting();
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createPainting(@RequestBody Painting painting) {
        return paintingService.createPainting(painting);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Painting> updatePainting(@PathVariable(value = "id") Long paintingId, @RequestBody Painting paintingDetails) {
        return paintingService.updatePainting(paintingId, paintingDetails);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> deletePainting(@PathVariable(value = "id") Long paintingId) {
        return paintingService.deletePainting(paintingId);
    }
}
