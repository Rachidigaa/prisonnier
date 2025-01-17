package fr.uga.miage.m1.my_projet_g1_10.persistence.mysql;


import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Game;
import fr.uga.miage.m1.my_projet_g1_10.core.repository.GameRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public class GameRepositoryImp implements GameRepository {


    private final GameJpaRepository gameJpaRepository;

    public GameRepositoryImp(GameJpaRepository gameJpaRepository)
    {
        this.gameJpaRepository=gameJpaRepository;
    }
    @Override
    public Game save(Game game) {
        return gameJpaRepository.save(game);
    }

    @Override
    public Optional<Game> findById(Long gameId) {
        return gameJpaRepository.findById(gameId);
    }
}
