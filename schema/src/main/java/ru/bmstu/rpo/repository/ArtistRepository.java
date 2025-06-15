package ru.bmstu.rpo.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import ru.bmstu.rpo.entity.Artist;

@Repository
public interface ArtistRepository extends JpaRepository<Artist, Long> {
}
