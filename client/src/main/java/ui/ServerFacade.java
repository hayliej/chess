package ui;

import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import com.google.gson.Gson;
import requests.LoginRequest;
import requests.UserData;

import java.io.*;
import java.net.*;

public class ServerFacade {
    private final String serverUrl;

    public ServerFacade(String url) {
        serverUrl = url;
    }

    public Object login(String username, String password) {
        var path = "/session";
        LoginRequest logOb = new LoginRequest(username, password);
        return makeRequest("POST", path, logOb, Object.class);
    }

    public Object register(String username, String password, String email) {
        var path = "/user";
        UserData regOb = new UserData(username, password, email);
        return makeRequest("POST", path, regOb, Object.class);
    }

    public Object logout(String username) {
        var path = "/session";
        String logOb = username;
        return makeRequest("DELETE", path, logOb, Object.class);
    }

    private <T> T makeRequest(String method, String path, Object request, Class<T> responseClass) {
        try {
            URL url = (new URI(serverUrl + path)).toURL();
            HttpURLConnection http = (HttpURLConnection) url.openConnection();
            http.setRequestMethod(method);
            //http.setDoOutput(true);

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
            try (InputStream respBody = http.getInputStream()) {
                InputStreamReader reader = new InputStreamReader(respBody);
                if (responseClass != null) {
                    response = new Gson().fromJson(reader, responseClass);
                }
            }
        }
        return response;
    }

}
