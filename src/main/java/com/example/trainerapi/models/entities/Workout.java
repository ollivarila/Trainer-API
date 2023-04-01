package com.example.trainerapi.models.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.Date;
import java.util.List;
import java.util.UUID;

// todo sä et välttämättä tarvi näitä kaikkia annotaatiotoita @Entity on tärkein jos tämä on sun db taulu
@Getter
@Setter
@NoArgsConstructor
@Data
@Entity
public class Workout {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;
    private String name;
    private Date workoutStarted;
    private Date workoutEnded;
    private boolean isPreset;
    @JsonIgnore
    @ManyToOne
    private User user;
    @OneToMany(cascade = CascadeType.ALL)
    private List<Exercise> exercises;
    @Override
    public String toString(){
        return String.format("Workout[id=%s, name='%s']", id, name);
    }
}
