package ru.bmstu.schema.service;

import lombok.Data;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.bmstu.schema.entity.Artist;
import ru.bmstu.schema.entity.Country;
import ru.bmstu.schema.repository.CountryRepository;

import java.util.*;

@Service
@Data
public class CountryService {

    @Autowired
    CountryRepository countryRepository;

    public List findAllCountries(){
        return countryRepository.findAll();
    }

    public Optional<Country> findById(Long id){
        return countryRepository.findById(id);
    }

    public ResponseEntity<Object> createCountry(Country country) {
        try {
            Country nc = countryRepository.save(country);
            return ResponseEntity.ok(nc);
        } catch (Exception ex) {
            String error;
            if (ex.getMessage().contains("countries.name_UNIQUE"))
                error = "countyalreadyexists";
            else
                error = "undefinederror";
            Map<String, String> map = new HashMap<>();
            map.put("error", error);
            return ResponseEntity.ok(map);
        }
    }

    public ResponseEntity<Country> updateCountry(Long countryId, Country countryDetails){
        Optional<Country> cc = countryRepository.findById(countryId);
        if (cc.isPresent()) {
            Country country = entityForUpdate(countryId, countryDetails);
            countryRepository.save(country);
            return ResponseEntity.ok(country);
        } else {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "country not found");
        }
    }

    public ResponseEntity<Object> deleteCountry(Long countryId) {
        Optional<Country> country = countryRepository.findById(countryId);
        Map<String, Boolean> resp = new HashMap<>();
        if (country.isPresent()) {
            countryRepository.delete(country.get());
            resp.put("deleted", Boolean.TRUE);
        }
        else
            resp.put("deleted", Boolean.FALSE);
        return ResponseEntity.ok(resp);
    }

    private Country entityForUpdate(Long countryId, Country countryDetails){
        Optional<Country> cc = countryRepository.findById(countryId);
        Country country = cc.get();
        country.name = countryDetails.name;
        return country;
    }

    public ResponseEntity<List<Artist>> getCountryArtists(Long countryId) {
        Optional<Country> cc = countryRepository.findById(countryId);
        if (cc.isPresent()) {
            return ResponseEntity.ok(cc.get().artists);
        }
        return ResponseEntity.ok(new ArrayList<>());
    }
}
