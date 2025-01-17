package fr.uga.miage.m1.my_projet_g1_10.core.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Decision;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Strategie;
import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Player {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private int score = 0;

    @JsonIgnore // Hide from JSON response
    private Decision currentMove;

    private Strategie strategie;
    private Boolean present = true;

    @ElementCollection
    private List<Decision> moveHistory = new ArrayList<>();

    // Getter and Setter for hasPlayed

    @ManyToOne
    @JoinColumn(name = "game_id")
    @JsonIgnore
    private Game game;

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public int getScore() { return score; }
    public void incrementScore(int points) { this.score += points; }

    public Decision getCurrentMove() { return currentMove; }
    public void setCurrentMove(Decision move) { this.currentMove = move; }

    public Strategie getStrategie() { return strategie; }
    public void setStrategie(Strategie strategie) { this.strategie = strategie; }

    public Boolean getPresent() { return present; }
    public void setPresent(Boolean present) { this.present = present; }

    public List<Decision> getMoveHistory() { return moveHistory; }
    public void addMove(Decision decision) { this.moveHistory.add(decision); }

    public Game getGame() { return game; }
    public void setGame(Game game) { this.game = game; }

    public void resetMove() {
        this.currentMove = null;
    }
    public boolean hasPlayed() {
        return this.currentMove != null;
    }
}