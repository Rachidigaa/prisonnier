package fr.uga.miage.m1.my_projet_g1_10.mysql;


import com.example.demo.core.domain.model.Game;
import com.example.demo.core.repository.GameRepository;
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
