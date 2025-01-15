package fr.uga.miage.m1.my_projet_g1_10.controlle;

import fr.uga.miage.m1.my_projet_g1_10.core.domain.enums.Strategie;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.service.GameService;
import fr.uga.miage.m1.my_projet_g1_10.core.usecases.GameControllerPort;
import fr.uga.miage.m1.my_projet_g1_10.core.usecases.GameServicePort;
import fr.uga.miage.m1.my_projet_g1_10.presentation.controller.GameController;
import fr.uga.miage.m1.my_projet_g1_10.core.domain.model.Game;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.boot.test.mock.mockito.SpyBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class GameControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @SpyBean
    @Qualifier("gameService")
    private GameServicePort gameService;

    private Game game;

    @BeforeEach
    void setUp() {
        game = new Game();
        game.setId(1L);
        game.setRounds(5);
    }

    @Test
    void testCreateGame() throws Exception {
        // Stubbing de la méthode `createGame` de `gameService`
        when(gameService.createGame("John", 5)).thenReturn(game);

        // Exécution de la requête POST
        mockMvc.perform(post("/game/create")
                        .param("playerName", "John")
                        .param("rounds", "5")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.rounds").value(5));

        // Vérification que la méthode a été appelée avec les bons arguments
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

    @Test
    void testPlayRound() throws Exception {
        // Mock the response of playRound
        Map<String, Object> response = new HashMap<>();
        response.put("message", "Round played successfully.");
        response.put("scores", Map.of("Player1", 3, "Player2", 5));

        when(gameService.playRound(anyLong(), anyLong(), anyString())).thenReturn(response);

        // Perform the POST request
        mockMvc.perform(post("/game/play")
                        .param("gameId", "1")
                        .param("playerId", "2")
                        .param("move", "COOPERER")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.message").value("Round played successfully."))
                .andExpect(jsonPath("$.scores.Player1").value(3))
                .andExpect(jsonPath("$.scores.Player2").value(5));

        // Verify the interaction
        Mockito.verify(gameService).playRound(1L, 2L, "COOPERER");
    }


    @Test
    void testGetGameStatus() throws Exception {
        // Mock the response of getGameStatus
        Map<String, Object> response = new HashMap<>();
        response.put("status", "STARTED");
        response.put("currentRound", 2);
        response.put("rounds", 5);

        when(gameService.getGameStatus(anyLong())).thenReturn(response);

        // Perform the GET request
        mockMvc.perform(get("/game/1/status")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.status").value("STARTED"))
                .andExpect(jsonPath("$.currentRound").value(2))
                .andExpect(jsonPath("$.rounds").value(5));

        // Verify the interaction
        Mockito.verify(gameService).getGameStatus(1L);
    }



}
