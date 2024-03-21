package ui;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import com.google.gson.Gson;
import requests.*;
import results.CreateGameResult;
import results.ListGamesResult;
import results.LogoutResult;
import results.RegisterResult;

import java.io.*;
import java.net.*;
import java.util.List;

public class ServerFacade {
    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    String authToken;

    public RegisterResult login(String username, String password) {
        var path = "/session";
        LoginRequest logOb = new LoginRequest(username, password);
        RegisterResult res = makeRequest("POST", path, logOb, RegisterResult.class);
        authToken=res.authToken();
        return res;
    }

    public RegisterResult register(String username, String password, String email) {
        var path = "/user";
        UserData regOb = new UserData(username, password, email);
        RegisterResult res = makeRequest("POST", path, regOb, RegisterResult.class);
        authToken=res.authToken();
        return res;
    }

    public LogoutResult logout(String authT) {
        var path = "/session";
        String auth = authT;
        return makeRequest("DELETE", path, null, LogoutResult.class);
    }

    public CreateGameResult createGame(String auth, String gameName) {
        var path = "/game";
        GameData creOb = new GameData(null, null, null, gameName, null);
        return makeRequest("POST", path, creOb, CreateGameResult.class);
    }

    public LogoutResult joinGame(String auth, Integer id, String color) {
        var path = "/game";
        AuthJoinGame joiOb = new AuthJoinGame(null, color, id);
        return makeRequest("PUT", path, joiOb, LogoutResult.class);
    }

    public LogoutResult observeGame(String auth, Integer id) {
        var path = "/game";
        AuthJoinGame joiOb = new AuthJoinGame(null, null, id);
        return makeRequest("PUT", path, joiOb, LogoutResult.class);
    }

    public ListGamesResult listGames(String authT) {
        var path = "/game";
        String auth = authT;
        return makeRequest("GET", path, null, ListGamesResult.class);
    }

    public ListGamesResult clear() {
        var path = "/db";
        return makeRequest("DELETE", path, null, ListGamesResult.class);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            http.setRequestProperty("authorization", authToken);
            http.setDoOutput(true);

            writeBody(request, http);
            http.connect();
            //throwIfNotSuccessful(http);
            return readBody(http, responseClass);
        } catch (Exception ex) {
            return (T) "Error";
        }
    }

    private static void writeBody(Object request, HttpURLConnection http) {
        if (request != null) {
            http.addRequestProperty("Content-Type", "application/json");
            String reqData = new Gson().toJson(request);
            try (OutputStream reqBody = http.getOutputStream()) {
                reqBody.write(reqData.getBytes());
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    private static <T> T readBody(HttpURLConnection http, Class<T> responseClass) throws IOException {
        T response = null;
        if (http.getContentLength() < 0) {
            if (http.getResponseCode()==200) {
                try (InputStream respBody = http.getInputStream()) {
                    InputStreamReader reader = new InputStreamReader(respBody);
                    if (responseClass != null) {
                        response = new Gson().fromJson(reader, responseClass);
                    }
                }
            } else {
                try (InputStream respBody = http.getErrorStream()) {
                    InputStreamReader reader = new InputStreamReader(respBody);
                    if (responseClass != null) {
                        response = new Gson().fromJson(reader, responseClass);
                    }
                }
            }
        }
        return response;
    }

}
