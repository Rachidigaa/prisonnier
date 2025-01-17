package fr.uga.miage.m1.my_projet_g1_10.core.domain.model;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;
import jakarta.persistence.*;

import java.util.HashMap;
import java.util.Map;

@Entity
public class Round {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int roundNumber;

    @ElementCollection
    @CollectionTable(name = "round_moves", joinColumns = @JoinColumn(name = "round_id"))
    @MapKeyColumn(name = "player_id")
    @Column(name = "move")
    private Map<Long, Decision> playerMoves = new HashMap<>();

    private String result;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    // Constructor
    public Round() {}

    public Round(int roundNumber, Map<Long, Decision> playerMoves, String result) {
        this.roundNumber = roundNumber;
        this.playerMoves = playerMoves;
        this.result = result;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public int getRoundNumber() { return roundNumber; }
    public void setRoundNumber(int roundNumber) { this.roundNumber = roundNumber; }

    public Map<Long, Decision> getPlayerMoves() { return playerMoves; }
    public void setPlayerMoves(Map<Long, Decision> playerMoves) { this.playerMoves = playerMoves; }

    public String getResult() { return result; }
    public void setResult(String result) { this.result = result; }

    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }
}