package com.tanikella_springboot.Projectquiz.Service;

import com.tanikella_springboot.Projectquiz.DAO.Question_DAO;
import com.tanikella_springboot.Projectquiz.Model.Question;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service //- handling objects
public class Question_Service {

    @Autowired
    Question_DAO questionDao;

    public ResponseEntity<List<Question>> getAllQuestions() {
        try{
            return new ResponseEntity<>(questionDao.findAll(), HttpStatus.OK); // this returns the status 200 response to client. To handle exception we use try-catch block
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
         // gives you a list of all questions
    }

    public ResponseEntity<List<Question>> getQuestionsByCategory(String category) {
        try{
            return new ResponseEntity<>(questionDao.findByCategory(category), HttpStatus.OK); // this returns the status 200 response to client. To handle exception we use try-catch block
        }catch (Exception e){
            e.printStackTrace();
        }
        return new ResponseEntity<>(new ArrayList<>(), HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<String> addQuestion(Question question) {
        questionDao.save(question);
        return new ResponseEntity<>("question added successfully", HttpStatus.CREATED);
    }

    public ResponseEntity<String> updateQuestion(int id, Question question) {
        try {
            Question existingQuestion = questionDao.findById(id).orElse(null);
            if (existingQuestion != null) {
                existingQuestion.setQuestionTitle(question.getQuestionTitle());
                existingQuestion.setOption1(question.getOption1());
                existingQuestion.setOption2(question.getOption2());
                existingQuestion.setOption3(question.getOption3());
                existingQuestion.setOption4(question.getOption4());
                existingQuestion.setRightAnswer(question.getRightAnswer());
                existingQuestion.setDifficultyLevel(question.getDifficultyLevel());
                existingQuestion.setCategory(question.getCategory());
                questionDao.save(existingQuestion);
                return new ResponseEntity<>("question updated successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("question not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while updating the question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    public ResponseEntity<String> deleteQuestion(int id) {
        try {
            if (questionDao.existsById(id)) {
                questionDao.deleteById(id);
                return new ResponseEntity<>("question deleted successfully", HttpStatus.OK);
            } else {
                return new ResponseEntity<>("question not found", HttpStatus.NOT_FOUND);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ResponseEntity<>("An error occurred while deleting the question", HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }


//    public String updateQuestion(int id, Question question) {
//        Question existingQuestion = questionDao.findById(id).orElse(null);
//        if(existingQuestion != null) {
//            existingQuestion.setQuestionTitle(question.getQuestionTitle());
//            existingQuestion.setOption1(question.getOption1());
//            existingQuestion.setOption2(question.getOption2());
//            existingQuestion.setOption3(question.getOption3());
//            existingQuestion.setOption4(question.getOption4());
//            existingQuestion.setRightAnswer(question.getRightAnswer());
//            existingQuestion.setDifficultyLevel(question.getDifficultyLevel());
//            existingQuestion.setCategory(question.getCategory());
//            questionDao.save(existingQuestion);
//            return "question updated successfully";
//        } else {
//            return "question not found";
//        }
//    }
//
//    public String deleteQuestion(int id) {
//        if (questionDao.existsById(id)) {
//            questionDao.deleteById(id);
//            return "question deleted successfully";
//        } else {
//            return "question not found";
//        }
//    }



}
