package ru.bmstu.schema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bmstu.schema.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}