package fr.uga.miage.m1.my_projet_g1_10.mysql;


import com.example.demo.core.domain.model.Player;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerJpaRepository extends JpaRepository<Player, Long> {
    // Ajouter des méthodes de recherche si nécessaire
    Player findPlayerById(long id);
}
