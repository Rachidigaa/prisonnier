package fr.uga.miage.m1.my_projet_g1_10.core.domain.model;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Strategie;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.strategies.IStrategie;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.strategiesCreators.StrategieFactory;
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
    private boolean isAutoPlayer = false;
    @Enumerated(EnumType.STRING)
    private Strategie strategy;

    @ManyToOne
    @JsonBackReference
    private Game game;

    @Transient
    @JsonIgnore
    private IStrategie strategyImplementation;

   
    public Player() {
    }

   
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
    public boolean isAutoPlayer() {
        return isAutoPlayer;
    }

    public void setAutoPlayer(boolean autoPlayer) {
        isAutoPlayer = autoPlayer;
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
        if (strategyImplementation == null && strategy != null) {
            this.strategyImplementation = StrategieFactory.getStrategie(strategy);
        }
        return strategyImplementation;
    }

    public void setGame(Game game) {
        this.game = game;
    }

    public Game getGame() {
        return game;
    }
}