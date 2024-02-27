package server;

import service.*;
import spark.*;

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
        //Spark.post("/user", this::register);
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

//    private Object login(Request req, Response res) {
//        return "";
//    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
