package com.antoniuk.hackerrank_prep.client;

import org.springframework.stereotype.Service;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.stream.Collectors;
import org.json.JSONObject;


@Service
public class FootballMatchApiClient {

    private static final String BASE_URL = "https://jsonmock.hackerrank.com/api/football_matches";

    //PROBLEM 1
    public JSONObject fetchDrawMatchesPageByYear(int year, int page, int score) throws IOException {

        String stringUrl = BASE_URL
                + "?year=" + year
                //+ "&team1=Barcelona"
                + "&page=" + page
                + "&team1goals=" + score
                + "&team2goals=" + score
                ;

        URL url = new URL(stringUrl);
        HttpURLConnection conn = (HttpURLConnection)url.openConnection();
        conn.setRequestMethod("GET");

        int statusCode = conn.getResponseCode();

        if (statusCode != 200) {
            throw new RuntimeException("Failed to get data from remote API. Status: " + statusCode);
        }
        InputStreamReader streamReader = new InputStreamReader(conn.getInputStream());
        BufferedReader in  = new BufferedReader(streamReader);

        return new JSONObject(in.lines().collect(Collectors.joining()));

    }
    //<-END PROBLEM 1

    //PROBLEM 2
    public JSONObject fetchMatchesByYearTeam(int year, String team, String teamType, int page ) throws IOException {

        String stringUrl = BASE_URL +
                "?year=" +
                year +
                "&page=" +
                page +
                "&" + teamType + "=" + URLEncoder.encode(team, StandardCharsets.UTF_8);


        URL url = new URL(stringUrl);
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        conn.setRequestMethod("GET");

        BufferedReader in = new BufferedReader(
                new InputStreamReader(conn.getInputStream())
        );

        String responseBody = in.lines().collect(Collectors.joining());

        return new JSONObject(responseBody);

    }
    //<-END PROBLEM 2

}
