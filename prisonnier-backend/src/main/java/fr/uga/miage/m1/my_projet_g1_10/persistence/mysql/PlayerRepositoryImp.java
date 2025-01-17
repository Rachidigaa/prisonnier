package fr.uga.miage.m1.my_projet_g1_10.persistence.mysql;


import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Player;
import fr.uga.miage.m1.my_projet_g1_10.core.repository.PlayerRepository;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Primary
public class PlayerRepositoryImp implements PlayerRepository {

    private final PlayerJpaRepository playerJpaRepository;

    public PlayerRepositoryImp(PlayerJpaRepository playerJpaRepository)
    {
        this.playerJpaRepository=playerJpaRepository;
    }
    @Override
    public Player save(Player player) {
        return playerJpaRepository.save(player);
    }

    @Override
    public Optional<Player> findById(Long playerId) {
        return playerJpaRepository.findById(playerId);
    }

    @Override
    public void delete(Player player) {
        playerJpaRepository.delete(player);
    }
}
