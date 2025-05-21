package ru.bmstu.schema.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bmstu.schema.entity.Painting;

@Repository
public interface PaintingRepository extends JpaRepository<Painting, Long> {
}
