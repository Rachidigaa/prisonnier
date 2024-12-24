package fr.uga.miage.m1.my_projet_g1_10.controlle;

import fr.uga.miage.m1.my_projet_g1_10.controller.GameController;
import fr.uga.miage.m1.my_projet_g1_10.model.Game;
import fr.uga.miage.m1.my_projet_g1_10.service.GameService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(GameController.class)
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private GameService gameService;

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.setId(1L);
        game.setRounds(5);
    }

    @Test
    void testCreateGame() throws Exception {
        when(gameService.createGame(anyString(), anyInt())).thenReturn(game);

        mockMvc.perform(post("/game/create")
                        .param("playerName", "John")
                        .param("rounds", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rounds").value(5));

        Mockito.verify(gameService).createGame("John", 5);
    }

    @Test
    void testJoinGame() throws Exception {
        when(gameService.joinGame(anyLong(), anyString())).thenReturn(Optional.of(game));

        mockMvc.perform(post("/game/join")
                        .param("gameId", "1")
                        .param("playerName", "Jane")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rounds").value(5));

        Mockito.verify(gameService).joinGame(1L, "Jane");
    }

    /*@Test
    void testPlayRound() throws Exception {
        when(gameService.playRound(anyLong(), anyLong(), anyString()));

        mockMvc.perform(post("/game/play")
                        .param("gameId", "1")
                        .param("playerId", "2")
                        .param("move", "COOPERATE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("Round played"));

        Mockito.verify(gameService).playRound(1L, 2L, "COOPERATE");
    }*/

    @Test
    void testQuittePartie() throws Exception {
        when(gameService.quitterPartie(anyLong(), anyString())).thenReturn("Player quit with strategy");

        mockMvc.perform(post("/game/quitte")
                        .param("playerId", "2")
                        .param("strategie", "DEFENSIVE")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string("{\"message\":\"Player quit with strategy\"}"));

        Mockito.verify(gameService).quitterPartie(2L, "DEFENSIVE");
    }
}
