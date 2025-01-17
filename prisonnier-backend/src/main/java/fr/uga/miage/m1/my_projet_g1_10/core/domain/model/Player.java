package com.example.demo.core.domain.model;

import com.example.demo.core.domain.enums.Strategie;
import com.example.demo.core.domain.strategies.IStrategie;
import com.example.demo.core.domain.strategiesCreators.StrategieFactory;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String username;

    private int score;

    private boolean hasPlayed;

    @Enumerated(EnumType.STRING)
    private Strategie strategy;

    @ManyToOne
    @JsonBackReference
    private Game game;

    @Transient
    @JsonIgnore
    private IStrategie strategyImplementation;

    // Default constructor
    public Player() {
    }

    // Getters and setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public boolean isHasPlayed() {
        return hasPlayed;
    }

    public void setHasPlayed(boolean hasPlayed) {
        this.hasPlayed = hasPlayed;
    }

    public Strategie getStrategy() {
        return strategy;
    }

    public void setStrategy(Strategie strategy) {
        this.strategy = strategy;
        this.strategyImplementation = StrategieFactory.getStrategie(strategy);
    }

    public IStrategie getStrategyImplementation() {
        return strategyImplementation;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}