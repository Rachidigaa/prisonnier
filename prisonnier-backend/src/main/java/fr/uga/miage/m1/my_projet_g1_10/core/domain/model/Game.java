package com.example.demo.core.domain.model;

import com.example.demo.core.domain.enums.GameState;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Game {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int totalRounds;

    private int currentRound = 0;

    @Enumerated(EnumType.STRING)
    private GameState state;

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @OrderBy("id ASC") // Ensures rounds are ordered by their ID
    private List<Round> rounds = new ArrayList<>();

    @OneToMany(mappedBy = "game", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    private List<Player> players = new ArrayList<>();

    // Default constructor
    public Game() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getCurrentRound() {
        return currentRound;
    }
    public void setCurrentRound(int value) {
        this.currentRound=value;
    }
    public int getTotalRounds() {
        return totalRounds;
    }

    public void setTotalRounds(int totalRounds) {
        this.totalRounds = totalRounds;
    }

    public GameState getState() {
        return state;
    }

    public void setState(GameState state) {
        this.state = state;
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public List<Round> getRounds() {
        return rounds;
    }

    public void setRounds(List<Round> rounds) {
        this.rounds = rounds;
    }

    // Utility methods
    public void addPlayer(Player player) {
        players.add(player);
        player.setGame(this);
    }

    public void removePlayer(Player player) {
        players.remove(player);
        player.setGame(null);
    }

    public void addRound(Round round) {
        rounds.add(round);
        round.setGame(this);
    }
    public void incrementCurrentRound()
    {
        currentRound++;
    }
    public void removeRound(Round round) {
        rounds.remove(round);
        round.setGame(null);
    }
}
