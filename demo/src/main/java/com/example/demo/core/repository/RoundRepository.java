package com.example.demo.core.repository;


import com.example.demo.core.domain.model.Game;

import java.util.Optional;

public interface GameRepository {

    void save(Game game);

    Optional<Game> findById(Long gameId);
}
