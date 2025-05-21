package ru.bmstu.schema.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@Table(name = "users")
public class Users {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false, nullable = false)
    public long id;

    @Column(name = "login", nullable = false, unique = true, length = 45)
    public String login;

    @JsonIgnore
    @Column(name = "password", length = 65)
    public String password;

    @Column(name = "email", nullable = false, unique = true, length = 45)
    public String email;

    @JsonIgnore
    @Column(name = "sait", length = 65)
    public String sait;

    @Column(name = "token", length = 256)
    public String token;

    @Column(name = "activity", length = 65)
    public LocalDateTime activity;

    @ManyToMany(mappedBy = "users")
    public Set<Museum> museums = new HashSet<>();

    public void addMuseum(Museum m) {
        this.museums.add(m);
        m.users.add(this);
    }

    public void removeMuseum(Museum m) {
        this.museums.remove(m);
        m.users.remove(this);
    }
}
