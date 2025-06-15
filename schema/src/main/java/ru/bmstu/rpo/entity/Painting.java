package ru.bmstu.rpo.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
@Table(name = "paintings")
public class Painting {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public long id;


    @Column(name = "name", nullable = false, length = 45)
    public String name;

    @Column(name = "year")
    public int year;

    @ManyToOne()
    @JoinColumn(name = "artist_id")
    public Artist artist;

    @ManyToOne()
    @JoinColumn(name = "museum_id")
    public Museum museum;
}
