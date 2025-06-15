package ru.bmstu.rpo.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.rpo.entity.Painting;
import ru.bmstu.rpo.service.PaintingService;
import ru.bmstu.rpo.tools.DataValidationException;

import java.util.List;

@RequestMapping("/api/v1/paintings")
@CrossOrigin(origins = "http://localhost:3000")
@Data
@RestController
public class PaintingController {

    @Autowired
    PaintingService paintingService;

    @GetMapping("")
    public Page<Painting> getAllPaintings(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        return paintingService.getAllPaintings(page, limit);
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

    @GetMapping("/{id}")
    public HttpEntity<Painting> ResponseEntityGetPainting(@PathVariable(value = "id") Long paintingId) throws DataValidationException {
        Painting painting = paintingService.findById(paintingId)
                .orElseThrow(()->new DataValidationException("Страна с таким индексом не найдена"));
        return ResponseEntity.ok(painting);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createPaintingRest(@Validated @RequestBody Painting painting) throws DataValidationException {
        return paintingService.createPaintingRest(painting);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Painting> updatePaintingRest(@PathVariable(value = "id") Long paintingId, @Validated @RequestBody Painting paintingDetails) throws DataValidationException {
        return paintingService.updatePaintingRest(paintingId, paintingDetails);
    }

    @PostMapping("/deletepaintings")
    public ResponseEntity deletePaintingsRest(@Validated @RequestBody List<Painting> paintings) {
        return paintingService.deletePaintingsRest(paintings);
    }
}
