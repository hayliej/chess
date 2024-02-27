package server;

import com.google.gson.Gson;
import dataAccess.DataAccessException;
import service.*;
import spark.*;
import requests.*;

public class Server {
    private UserService userService = new UserService();
    private GameService gameService = new GameService();
    private AuthService authService = new AuthService();
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
        //Spark.post("/session", this::login);
        //Spark.delete("/session", this::logout);
        //Spark.get("/game", this::listGames);
        //Spark.post("/game", this::createGame);
        //Spark.put("/game", this::joinGame);

        Spark.awaitInitialization();
        return Spark.port();
    }

    //FOR ALL OF THE ABOVE, TAKE INPUT JSON CONVERT TO GSON, CALL SERVICE METHOD ON IT, RETURN TO CLIENT ???

    private Object clear(Request req, Response res) {
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
            response = UserService.getUser(user);
        } catch (DataAccessException e) {
            res.status(500);
            response = new RegisterResult("Error: error occurred", null, null);
            return new Gson().toJson(response);
        }
        if (response.equals(new RegisterResult("Error: already taken", null, null))) {
            res.status(403);
            return new Gson().toJson(response);
        } else if (response.message().equals("Error: bad request")){
            res.status(400);
            return new Gson().toJson(response);
        }
        res.status(200);//set this for corresponding message that you get back from service
        return new Gson().toJson(response);
    }

//    private Object login(Request req, Response res) {
//        return "";
//    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
