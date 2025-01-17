package fr.uga.miage.m1.my_projet_g1_10.core.repository;


import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Round;

import java.util.Optional;

public interface RoundRepository {

    Round save(Round round);

    Optional<Round> findById(Long roundId);
}
