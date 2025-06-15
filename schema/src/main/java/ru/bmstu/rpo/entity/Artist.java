package ru.bmstu.rpo.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "artists")
public class Artist {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public long id;

    @Column(name = "name", nullable = false, unique = true, length = 128)
    public String name;

    @Column(name = "century", nullable = false, length = 45)
    public String century;

    @ManyToOne()
    @JoinColumn(name = "country_id")
    public Country country;

    @JsonIgnore
    @OneToMany(mappedBy = "artist")
    public List<Painting> paintings = new ArrayList<>();
}
