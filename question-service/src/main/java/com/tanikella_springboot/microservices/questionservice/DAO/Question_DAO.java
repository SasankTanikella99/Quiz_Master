package com.tanikella_springboot.microservices.questionservice.DAO;

import com.tanikella_springboot.microservices.questionservice.Model.Question;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface Question_DAO extends JpaRepository<Question, Integer> {
    // JDBC 7-steps is done via JpaRepository

    List<Question> findByCategory(String category);

    @Query(value = "SELECT q.id FROM Question q WHERE q.category = ?1 ORDER BY RANDOM() LIMIT ?2", nativeQuery = true)
    List<Integer> findRandomQuestionsByCategory(String category, int numQ);

}
