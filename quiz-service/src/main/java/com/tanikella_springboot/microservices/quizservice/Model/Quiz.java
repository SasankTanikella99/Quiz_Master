package com.tanikella_springboot.microservices.quizservice.Model;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class Quiz {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String title;

    @ElementCollection
    // Element Collection is used beacuse we are saving number in a list, it is not an entity.
    private List<Integer> questionsId;
}
