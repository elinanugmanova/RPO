package ru.bmstu.schema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bmstu.schema.entity.Museum;

@Repository
public interface MuseumRepository extends JpaRepository<Museum, Long> {
}
