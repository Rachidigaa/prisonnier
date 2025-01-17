package fr.uga.miage.m1.my_projet_g1_10.core.repository;


import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Player;

import java.util.Optional;

public interface PlayerRepository {

    Player save(Player player);

    Optional<Player> findById(Long playerId);

    void delete(Player player);
}
