package fr.uga.miage.m1.my_projet_g1_10.persistence.mysql;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Game;
import fr.uga.miage.m1.my_projet_g1_10.core.repositories.GameRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Primary
public class GameRepositoryImp implements GameRepository {

    private final GameJpaRepository gameJpaRepository;

    @Override
    public void save(Game game) {
        gameJpaRepository.save(game);
    }

    @Override
    public Optional<Game> findById(Long gameId) {
        return gameJpaRepository.findById(gameId);
    }
}
