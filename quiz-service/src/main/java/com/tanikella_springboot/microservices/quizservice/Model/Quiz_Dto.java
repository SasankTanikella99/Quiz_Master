package com.tanikella_springboot.microservices.quizservice.Model;

import lombok.Data;

@Data
public class Quiz_Dto {
    String category;
    int numQ;
    String title;
}
