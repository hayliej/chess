package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import results.CreateGameResult;
import results.ListGamesResult;
import results.LogoutResult;
import results.RegisterResult;
import service.*;
import spark.*;
import requests.*;

public class Server {
    private static UserService userService = new UserService();
    private static GameService gameService = new GameService();
    private static AuthService authService = new AuthService();
    public Server(){
        this.userService = userService;
        this.gameService = gameService;
        this.authService = authService;
    }

    public int run(int desiredPort) {
        Spark.port(desiredPort);
        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.delete("/db", this::clear);
        Spark.post("/user", this::register);
        Spark.post("/session", this::login);
        Spark.delete("/session", this::logout);
        Spark.get("/game", this::listGames);
        Spark.post("/game", this::createGame);
        Spark.put("/game", this::joinGame);

        Spark.awaitInitialization();
        return Spark.port();
    }

    //FOR ALL OF THE ABOVE, TAKE INPUT JSON CONVERT TO GSON, CALL SERVICE METHOD ON IT, RETURN TO CLIENT

    private Object clear(Request req, Response res) throws DataAccessException {
        //clear db
        userService.clear();
        gameService.clear();
        authService.clear();
        //need to add exceptions/errors??
        return "{}";
    }

    private Object register(Request req, Response res){
        UserData user = new Gson().fromJson(req.body(), UserData.class);
        RegisterResult response = null;
        try {
            response = userService.getUser(user);
            res.status(200); //set this for corresponding message that you get back from service
        } catch (DataAccessException e) {
            res.status(500);
            response = new RegisterResult("Error: error occurred", null, null);
        }
        if (response.message()!=null) {
            if (response.equals(new RegisterResult("Error: already taken", null, null))) {
                res.status(403);
            } else if (response.message().equals("Error: bad request")) {
                res.status(400);
            }
        }
        return new Gson().toJson(response);
    }

    private Object login(Request req, Response res) {
        LoginRequest loginReq = new Gson().fromJson(req.body(), LoginRequest.class);
        RegisterResult response = null;
        try {
            response = authService.login(loginReq);
            res.status(200);
        } catch (DataAccessException e) {
            res.status(500);
            response = new RegisterResult("Error: error occurred", null, null);
        }
        if (response.message()!=null) {
            if (response.message().equals("Error: unauthorized")) {
                res.status(401);
            }
        }
        return new Gson().toJson(response);
    }

    private Object logout(Request req, Response res) {
        String auth = req.headers("authorization");
        LogoutResult response = null;
        try {
            response = authService.logout(auth);
            res.status(200);
        } catch (DataAccessException e) {
            res.status(500);
            response = new LogoutResult("Error: error occurred");
        }
        if (response.message()!=null) {
            if (response.message().equals("Error: unauthorized")) {
                res.status(401);
            }
        }
        return new Gson().toJson(response);
    }

    private Object listGames(Request req, Response res) {
        String auth = req.headers("authorization"); //should this be .headers() ??
        ListGamesResult response = null;
        try {
            response = gameService.listGames(auth);
            res.status(200);
        } catch (DataAccessException e) {
            res.status(500);
            response = new ListGamesResult("Error: error occurred", null);
        }
        if (response.message()!=null) {
            if (response.message().equals("Error: unauthorized")) {
                res.status(401);
            }
        }
        return new Gson().toJson(response);
    }

    private Object createGame(Request req, Response res) {
        AuthNewGame auth = new Gson().fromJson(req.body(), AuthNewGame.class);
        String head = req.headers("authorization");
        auth = new AuthNewGame(head, auth.gameName());
        CreateGameResult response = null;
        try {
            response = gameService.createGame(auth);
            res.status(200);
        } catch (DataAccessException e) {
            res.status(500);
            response = new CreateGameResult("Error: error occurred", null);
        }
        if (response.message()!=null) {
            if (response.message().equals("Error: unauthorized")) {
                res.status(401);
            } else if (response.message().equals("Error: bad request")) {
                res.status(400);
            }
        }
        return new Gson().toJson(response);
    }

    private Object joinGame(Request req, Response res) {
        AuthJoinGame join = new Gson().fromJson(req.body(), AuthJoinGame.class); //should this be .headers() ??
        String head = req.headers("authorization");
        join = new AuthJoinGame(head, join.playerColor(), join.gameID());
        LogoutResult response = null;
        try {
            response = gameService.joinGame(join);
            res.status(200);
        } catch (DataAccessException e) {
            res.status(500);
            response = new LogoutResult("Error: error occurred");
        }
        if (response.message()!=null) {
            if (response.message().equals("Error: unauthorized")) {
                res.status(401);
            } else if (response.message().equals("Error: bad request")) {
                res.status(400);
            } else if (response.message().equals("Error: already taken")) {
                res.status(403);
            }
        }
        return new Gson().toJson(response);
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
