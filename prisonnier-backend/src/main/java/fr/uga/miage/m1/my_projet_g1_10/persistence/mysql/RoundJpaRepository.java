package fr.uga.miage.m1.my_projet_g1_10.persistence.mysql;


import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Round;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RoundJpaRepository extends JpaRepository<Round, Long> {
   
    Round findRoundById(long id);
}
