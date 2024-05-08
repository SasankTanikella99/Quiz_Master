package com.tanikella_springboot.Projectquiz.Controllers;


import com.tanikella_springboot.Projectquiz.Model.Question;
import com.tanikella_springboot.Projectquiz.Model.QuestionWrapper;
import com.tanikella_springboot.Projectquiz.Model.Response;
import com.tanikella_springboot.Projectquiz.Service.Quiz_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
    public ResponseEntity<String> createQuiz(@RequestParam String category, @RequestParam int numQ, @RequestParam String title){
        return quizService.createQuiz(category, numQ, title);
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
