package com.example.apifutebol;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class ApiFootballClient {
    private static final String API_KEY = "b41536f90777ce5e864a6cecac47b9d9";
    private static final String BASE_URL = "https://v3.football.api-sports.io/";

    public String buscarUltimosJogos(String leagueId, String season) throws IOException, InterruptedException {
        String fromDate = "2024-05-10";
        String toDate = "2024-05-17";
                
        String endpoint = "fixtures?league=" + leagueId + "&season=" + season + "&from=" + fromDate + "&to=" + toDate;

        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(BASE_URL + endpoint))
                .header("x-apisports-key", API_KEY)
                .header("accept", "application/json")
                .build();

        HttpClient client = HttpClient.newHttpClient();
        HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

        return response.body();
    }

    public String buscarEventosDoJogo(String fixtureId) throws IOException {
    String url = "https://v3.football.api-sports.io/fixtures/events?fixture=" + fixtureId;
    return fazerRequisicao(url);
}

    private String fazerRequisicao(String url) throws IOException {
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(url))
                .header("x-apisports-key", API_KEY)
                .header("accept", "application/json")
                .build();

        HttpClient client = HttpClient.newHttpClient();
        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt(); // Restaura o estado de interrupção
            throw new IOException("A requisição foi interrompida", e);
        }
    }
}
