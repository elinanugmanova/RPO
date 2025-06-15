package ru.bmstu.rpo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bmstu.rpo.entity.Painting;

@Repository
public interface PaintingRepository extends JpaRepository<Painting, Long> {
}
