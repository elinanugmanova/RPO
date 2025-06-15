package ru.bmstu.rpo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bmstu.rpo.entity.Museum;

@Repository
public interface MuseumRepository extends JpaRepository<Museum, Long> {
}
