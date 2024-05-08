package com.tanikella_springboot.microservices.quizservice.Controllers;



import com.tanikella_springboot.microservices.quizservice.Model.QuestionWrapper;
import com.tanikella_springboot.microservices.quizservice.Model.Quiz_Dto;
import com.tanikella_springboot.microservices.quizservice.Model.Response;
import com.tanikella_springboot.microservices.quizservice.Service.Quiz_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("quiz")
public class Quiz_Controller {
    // say Admin is creating a quiz

    @Autowired
    Quiz_Service quizService;

    @PostMapping("create")
    public ResponseEntity<String> createQuiz(@RequestBody Quiz_Dto quiz_dto){
        return quizService.createQuiz(quiz_dto.getCategory(), quiz_dto.getNumQ(), quiz_dto.getTitle());
    }

    @GetMapping("get/{id}")
    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(@PathVariable Integer id){
        return quizService.getQuizQuestions(id);
    }

    @PostMapping("submit/{id}")
    public ResponseEntity<Integer> submitQuiz(@PathVariable Integer id, @RequestBody List<Response> response){
        return quizService.calculateResult(id, response);
    }
}
