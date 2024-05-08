package com.tanikella_springboot.Projectquiz.Service;

import com.tanikella_springboot.Projectquiz.DAO.Question_DAO;
import com.tanikella_springboot.Projectquiz.DAO.Quiz_DAO;
import com.tanikella_springboot.Projectquiz.Model.Question;
import com.tanikella_springboot.Projectquiz.Model.QuestionWrapper;
import com.tanikella_springboot.Projectquiz.Model.Quiz;
import com.tanikella_springboot.Projectquiz.Model.Response;
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
    Question_DAO questionDao;


    public ResponseEntity<String> createQuiz(String category, int numQ, String title) {

        List<Question> questions = questionDao.findRandomQuestionsByCategory(category, numQ);

        Quiz quiz = new Quiz();
        quiz.setTitle(title);
        quiz.setQuestions(questions);
        quizDao.save(quiz);

        return new ResponseEntity<>("created successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuizQuestions(Integer id) {
        Optional<Quiz> quiz = quizDao.findById(id);
        List<Question> questionsFromDB = quiz.get().getQuestions();
        // since the list is of Question Wrapper, we have to convert Question into QuestionWrapper
        List<QuestionWrapper> questionsForUser = new ArrayList<>();
        for(Question q: questionsFromDB){
            QuestionWrapper questionWrapper = new QuestionWrapper(q.getId(), q.getQuestionTitle(), q.getOption1(), q.getOption2(), q.getOption3(), q.getOption4());
            questionsForUser.add(questionWrapper);
        }
        return new ResponseEntity<>(questionsForUser, HttpStatus.OK);
    }

    public ResponseEntity<Integer> calculateResult(Integer id, List<Response> response) {
        Quiz quiz = quizDao.findById(id).get();
        if (quiz == null) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        List<Question> questions = quiz.getQuestions();
        if (response.size() != questions.size()) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        int right = 0;
        int i = 0;
        for (Response resp : response) {
            if (resp.getResponse().equals(questions.get(i).getRightAnswer())) {
                right++;
            }
            i++;
        }
        return new ResponseEntity<>(right, HttpStatus.OK);
    }

}
