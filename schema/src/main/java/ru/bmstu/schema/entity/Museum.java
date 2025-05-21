package ru.bmstu.schema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "museum")
public class Museum {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public long id;

    @Column(name = "name", nullable = false, length = 128)
    public String name;

    @Column(name = "location", nullable = false, length = 128)
    public String location;

    @JsonIgnore
    @OneToMany(mappedBy = "museum")
    public List<Painting> paintings = new ArrayList<>();

    @ManyToMany
    @JoinTable(name = "usersmuseums", joinColumns = @JoinColumn(name = "museums_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    @JsonIgnore
    public Set<Users> users = new HashSet<>();
}
