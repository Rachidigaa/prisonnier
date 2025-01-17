package com.example.demo.core.repository;


import com.example.demo.core.domain.model.Round;

import java.util.Optional;

public interface RoundRepository {

    Round save(Round round);

    Optional<Round> findById(Long roundId);
}
