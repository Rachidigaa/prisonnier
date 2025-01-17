package fr.uga.miage.m1.my_projet_g1_10.mysql;


import com.example.demo.core.domain.model.Round;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundJpaRepository extends JpaRepository<Round, Long> {
    // Ajouter des méthodes de recherche si nécessaire
    Round findRoundById(long id);
}
