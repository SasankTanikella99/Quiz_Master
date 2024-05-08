package com.tanikella_springboot.microservices.quizservice.DAO;

import com.tanikella_springboot.microservices.quizservice.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Quiz_DAO extends JpaRepository<Quiz, Integer> {

}
