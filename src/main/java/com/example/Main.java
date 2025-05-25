package com.example.apifutebol;

import static spark.Spark.*;

public class Main {
    public static void main(String[] args) {
        port(4567); // http://localhost:4567

        staticFiles.location("/public"); // pasta /resources/public para HTML/CSS/JS

        get("/", (req, res) -> {
            res.redirect("/index.html");
            return null;
        });

        get("/ultimos-jogos", (req, res) -> {
            ApiFootballClient api = new ApiFootballClient();
            String json = api.buscarUltimosJogos("39", "2023");
            res.type("application/json");
            return json;
        });

        // endpoint para buscar eventos de uma partida
        get("/eventos-jogo", (req, res) -> {
            String fixtureId = req.queryParams("fixture");
            if (fixtureId == null || fixtureId.isEmpty()) {
                res.status(400);
                return "{\"erro\":\"ID da partida ausente\"}";
            }

            ApiFootballClient api = new ApiFootballClient();
            String json = api.buscarEventosDoJogo(fixtureId);
            res.type("application/json");
            return json;
        });
    }
}
