package fr.uga.miage.m1.my_projet_g1_10.mysql;


import com.example.demo.core.domain.model.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameJpaRepository extends JpaRepository<Game, Long> {
    List<Game> findByState(String state);
}
