package ru.bmstu.schema.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.schema.entity.Artist;
import ru.bmstu.schema.entity.Country;
import ru.bmstu.schema.service.CountryService;

import java.util.List;

@Data
@RestController
@RequestMapping("/api/v1/countries")
public class CountryController {

    @Autowired
    CountryService countryService;

    @GetMapping("/")
    public List findAllCountries() {
        return countryService.findAllCountries();
    }

    @PostMapping("/create")
    public ResponseEntity<Object> createCountry(@RequestBody Country country) {
        return countryService.createCountry(country);
    }

    @PostMapping("/update/{id}")
    public ResponseEntity<Country> updateCountry(@PathVariable(value = "id") Long countryId, @RequestBody Country countryDetails) {
        return countryService.updateCountry(countryId, countryDetails);
    }

    @PostMapping("/delete/{id}")
    public ResponseEntity<Object> deleteCountry(@PathVariable(value = "id") Long countryId) {
        return countryService.deleteCountry(countryId);
    }

    @GetMapping("/{id}/artists")
    public ResponseEntity<List<Artist>> getCountryArtists(@PathVariable(value = "id") Long countryId) {
        return countryService.getCountryArtists(countryId);
    }
}

