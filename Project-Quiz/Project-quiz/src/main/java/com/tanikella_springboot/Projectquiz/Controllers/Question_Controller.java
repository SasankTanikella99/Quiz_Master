package com.tanikella_springboot.Projectquiz.Controllers;

import java.util.*;

import com.tanikella_springboot.Projectquiz.Model.Question;
import com.tanikella_springboot.Projectquiz.Service.Question_Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
// for accepting requests
@RequestMapping("question")
// this controller handles all incoming requests related to "question"
public class Question_Controller {

    @Autowired // creating a reference of Service
    Question_Service questionService;

    @GetMapping("allQuestions")
    public ResponseEntity<List<Question>> getAllQuestions(){
        return questionService.getAllQuestions();
    }

    @GetMapping("category/{category}") // the route is category/?= value - {category} is stored in the parameter but {} is because we want the value to be dynamic , since the name category is same, we need not mention but for example category/{cat} then we have to mention the name - @PathVariable("cat")
    public ResponseEntity<List<Question>> getQuestionsByCategory(@PathVariable String category){
        return questionService.getQuestionsByCategory(category);
    }

    @PostMapping("add")
    // we are adding new questions from the client side, so all we need to add is a question with a type Question
    // for that we need RequestBody annotation i.e., sending a request from client side
    public ResponseEntity<String> addQuestion(@RequestBody Question question){
        return questionService.addQuestion(question);
    }

    @PutMapping("update/{id}") // Update method
    public ResponseEntity<String> updateQuestion(@PathVariable int id, @RequestBody Question question){
        return questionService.updateQuestion(id, question);
    }

    @DeleteMapping("delete/{id}") // Delete method
    public ResponseEntity<String> deleteQuestion(@PathVariable int id){
        return questionService.deleteQuestion(id);
    }
}
