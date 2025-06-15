package ru.bmstu.rpo.controller;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpEntity;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import ru.bmstu.rpo.entity.Artist;
import ru.bmstu.rpo.entity.Country;
import ru.bmstu.rpo.service.CountryService;
import ru.bmstu.rpo.tools.DataValidationException;

import java.util.List;

@RequestMapping("/api/v1/countries")
@Data
@RestController
public class CountryController {

    @Autowired
    CountryService countryService;

    @GetMapping("")
    public Page<Country> getAllCountries(@RequestParam("page") int page, @RequestParam("limit") int limit) {
        return countryService.getAllCountries(page, limit);
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

    @GetMapping("/{id}")
    public HttpEntity<Country> ResponseEntityGetCountry(@PathVariable(value = "id") Long countryId) throws DataValidationException {
        Country country = countryService.findById(countryId)
                .orElseThrow(()->new DataValidationException("Страна с таким индексом не найдена"));
        return ResponseEntity.ok(country);
    }

    @PostMapping("/")
    public ResponseEntity<Object> createCountryRest(@Validated @RequestBody Country country) throws DataValidationException {
        return countryService.createCountryRest(country);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Country> updateCountryRest(
            @PathVariable(value = "id") String idString,
            @Validated @RequestBody Country countryDetails
    ) throws DataValidationException {
        try {
            Long countryId = Long.parseLong(idString);
            return countryService.updateCountryRest(countryId, countryDetails);
        } catch (NumberFormatException e) {
            throw new DataValidationException("ID страны должен быть числом");
        }
    }

    @PostMapping("/deletecountries")
    public ResponseEntity deleteCountriesRest(@Validated @RequestBody List<Country> countries) {
        return countryService.deleteCountriesRest(countries);
    }
}