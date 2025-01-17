package fr.uga.miage.m1.my_projet_g1_10.core.domain.model;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;
import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ElementCollection
    @CollectionTable(name = "round_scores", joinColumns = @JoinColumn(name = "round_id"))
    @MapKeyColumn(name = "player_id")
    @Column(name = "score")
    private Map<Long, Integer> scores = new HashMap<>();

    @ElementCollection
    @CollectionTable(name = "round_decisions", joinColumns = @JoinColumn(name = "round_id"))
    @MapKeyColumn(name = "player_id")
    @Column(name = "decision")
    @Enumerated(EnumType.STRING)
    private Map<Long, Decision> decisions = new HashMap<>();

    @ManyToOne
    @JsonBackReference
    private Game game;

   
    public Round() {
    }

   
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Long, Integer> getScores() {
        return scores;
    }

    public void setScores(Map<Long, Integer> scores) {
        this.scores = scores;
    }

    public Map<Long, Decision> getDecisions() {
        return decisions;
    }

    public void setDecisions(Map<Long, Decision> decisions) {
        this.decisions = decisions;
    }

    public Game getGame() {
        return game;
    }

    public void setGame(Game game) {
        this.game = game;
    }

   
    public void addDecision(Long playerId, Decision decision) {
        decisions.put(playerId, decision);
    }

    public void addScore(Long playerId, int score) {
        scores.put(playerId, scores.getOrDefault(playerId, 0) + score);
    }
}
