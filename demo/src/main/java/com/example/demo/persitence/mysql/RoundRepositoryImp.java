package com.example.demo.persitence.mysql;


import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Player;
import fr.uga.miage.m1.my_projet_g1_10.core.repositories.PlayerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@RequiredArgsConstructor
@Repository
@Primary
public class PlayerRepositoryImp implements PlayerRepository {

    private final PlayerJpaRepository playerJpaRepository;

    @Override
    public void save(Player player) {
        playerJpaRepository.save(player);
    }

    @Override
    public Optional<Player> findById(Long playerId) {
        return playerJpaRepository.findById(playerId);
    }
}
