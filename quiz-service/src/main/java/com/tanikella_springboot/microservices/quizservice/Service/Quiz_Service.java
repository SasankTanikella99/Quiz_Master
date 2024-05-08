package com.tanikella_springboot.microservices.quizservice.Service;


import com.tanikella_springboot.microservices.quizservice.DAO.Quiz_DAO;
import com.tanikella_springboot.microservices.quizservice.Feign.Quiz_Interface;
import com.tanikella_springboot.microservices.quizservice.Model.Question;
import com.tanikella_springboot.microservices.quizservice.Model.QuestionWrapper;
import com.tanikella_springboot.microservices.quizservice.Model.Quiz;
import com.tanikella_springboot.microservices.quizservice.Model.Response;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class Quiz_Service {

    @Autowired
    Quiz_DAO quizDao;

    @Autowired
    Quiz_Interface quiz_interface;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        // quiz-service interacts with question-service- calling generate url using RestTemplate
        List<Integer> questions = quiz_interface.getQuestionsForQuiz(category, numQ).getBody();

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestionsId(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Integer> questionsFromDB = quiz.get().getQuestionsId();
        ResponseEntity<List<QuestionWrapper>> questions = quiz_interface.getQuestionsFromId(questionsFromDB);
        return questions;
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> response) {
        ResponseEntity<Integer> score = quiz_interface.getScore(response);
       return score;
    }

}
