package com.tanikella_springboot.microservices.quizservice.Model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

// JPA takes care of the naming, naming in CamelCase, jpa converts it and maps it accordingly to the column name in database which follows snake-case

@Data // this removes all boilerplate code (getters and setters)
@Entity // table should be mapped to the class
public class Question {

    @Id // specifying id as primary key
    @GeneratedValue(strategy = GenerationType.IDENTITY) // id is auto generated
    private Integer id;
    private String questionTitle;
    private String option1;
    private String option2;
    private String option3;
    private String option4;
    private String rightAnswer;
    private String difficultyLevel;
    private String category;
}
