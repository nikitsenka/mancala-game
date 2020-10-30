package com.mancala.api.integration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.mancala.api.game.MancalaGame;
import com.mancala.api.repository.GameData;
import com.mancala.api.repository.GameRepository;
import com.mancala.api.service.PlayCommand;
import cucumber.api.java.After;
import cucumber.api.java.Before;
import cucumber.api.java.en.Given;
import cucumber.api.java.en.Then;
import cucumber.api.java.en.When;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;


@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class CucumberContextLoader {

    @Autowired
    private GameRepository gameRepository;
    @Autowired
    private ObjectMapper objectMapper;


    @LocalServerPort
    int port;
    private HttpClient httpClient;
    private String baseUrl;

    @Before
    public void setupTestContext() {
        httpClient = HttpClient.newHttpClient();
        baseUrl = "http://localhost:" + port;
        gameRepository.deleteAll();
    }

    @After
    public void cleanUpTestContext() {
    }

    @Given("the game exist")
    public void gameExist() throws IOException, InterruptedException {
        post("/api/mancala/game/init", "{}");
    }

    @When("player makes a move")
    public void playerMove() throws IOException, InterruptedException {
        PlayCommand command = new PlayCommand();
        command.setGameId(1l);
        command.setPlayerNumber(1);
        command.setPit(3);
        post("/api/mancala/play", objectMapper.writeValueAsString(command));
    }

    @Then("the game data updated")
    public void gameDataUpdated() {
        GameData gameData = gameRepository.findById(1l).get();
        assertNotNull(gameData);
        assertArrayEquals(List.of(0,4,4,0,5,5,5,1,4,4,4,4,4,4).toArray(), gameData.getPits().toArray());
    }

    private HttpResponse<String> get(String urlPath) throws IOException, InterruptedException {
        return httpClient
                .send(HttpRequest.newBuilder()
                                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                                .uri(URI.create(baseUrl + urlPath))
                                .GET()
                                .build(),
                        HttpResponse.BodyHandlers.ofString()
                );
    }

    private HttpResponse<String> post(String urlPath, String body) throws IOException, InterruptedException {
        return httpClient
                .send(HttpRequest.newBuilder()
                                .header(HttpHeaders.CONTENT_TYPE, APPLICATION_JSON_VALUE)
                                .uri(URI.create(baseUrl + urlPath))
                                .POST(HttpRequest.BodyPublishers.ofString(body))
                                .build(),
                        HttpResponse.BodyHandlers.ofString()
                );
    }
}
