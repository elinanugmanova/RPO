package ru.bmstu.rpo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bmstu.rpo.entity.Country;

@Repository
public interface CountryRepository extends JpaRepository<Country, Long> {
}