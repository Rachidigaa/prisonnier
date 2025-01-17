package com.example.demo.core.repository;


import com.example.demo.core.domain.model.Player;

import java.util.Optional;

public interface PlayerRepository {

    Player save(Player player);

    Optional<Player> findById(Long playerId);

    void delete(Player player);
}
