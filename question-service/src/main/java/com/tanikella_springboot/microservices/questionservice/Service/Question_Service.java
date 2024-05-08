package com.tanikella_springboot.microservices.questionservice.Service;


import com.tanikella_springboot.microservices.questionservice.DAO.Question_DAO;
import com.tanikella_springboot.microservices.questionservice.Model.Question;
import com.tanikella_springboot.microservices.questionservice.Model.QuestionWrapper;
import com.tanikella_springboot.microservices.questionservice.Model.Response;
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

    public ResponseEntity<List<Integer>> getQuestionsForQuiz(String category, Integer numQ) {
        List<Integer> questions = questionDao.findRandomQuestionsByCategory(category, numQ);
        return new ResponseEntity<>(questions, HttpStatus.OK);
    }

    public ResponseEntity<List<QuestionWrapper>> getQuestionsFromId(List<Integer> questionId) {
        List<QuestionWrapper> wrapper = new ArrayList<>();
        List<Question> questions = new ArrayList<>();

        // getting questions from DAO
        for(Integer id: questionId){
            questions.add(questionDao.findById(id).get());
        }
        // into wrapper (no right answers)
        for(Question q: questions) {
            QuestionWrapper wrapper1 = new QuestionWrapper();
            wrapper1.setId(q.getId());
            wrapper1.setQuestionTitle(q.getQuestionTitle());
            wrapper1.setOption1(q.getOption1());
            wrapper1.setOption2(q.getOption2());
            wrapper1.setOption3(q.getOption3());
            wrapper1.setOption4(q.getOption4());
            // since we are creating 1 wrapper1 at a time, we need to add them to the list
            wrapper.add(wrapper1);
        }

        return new ResponseEntity<>(wrapper, HttpStatus.OK);
    }

    public ResponseEntity<Integer> getScore(List<Response> responses) {

        int right = 0;
        for (Response resp : responses) {
            Question question = questionDao.findById(resp.getId()).get();
            if (resp.getResponse().equals(question.getRightAnswer())) {
                right++;
            }

        }
        return new ResponseEntity<>(right, HttpStatus.OK);
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
