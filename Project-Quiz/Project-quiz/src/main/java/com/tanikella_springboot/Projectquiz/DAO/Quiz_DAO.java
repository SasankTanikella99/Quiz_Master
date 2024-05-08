package com.tanikella_springboot.Projectquiz.DAO;

import com.tanikella_springboot.Projectquiz.Model.Quiz;
import org.springframework.data.jpa.repository.JpaRepository;

public interface Quiz_DAO extends JpaRepository<Quiz, Integer> {

}
