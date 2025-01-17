package fr.uga.miage.m1.my_projet_g1_10.core.repository;


import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Game;

import java.util.Optional;

public interface GameRepository {

    Game save(Game game);

    Optional<Game> findById(Long gameId);
}
