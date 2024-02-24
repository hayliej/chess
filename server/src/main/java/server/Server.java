package server;

import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);
        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        //Spark.post("/user", this::register);
        //Spark.post("/session", this::login);
        //Spark.delete("/session", this::logout);
        //Spark.get("/game", this::listGames);
        //Spark.post("/game", this::newGame);
        //Spark.put("/game", this::joinGame);
        Spark.delete("/db", this::clear);

        Spark.awaitInitialization();
        return Spark.port();
    }

    private Object clear(Request req, Response res) {
        //clear db
        return ""; //return cleared db
    }

//    private Object login(Request req, Response res) {
//        return "";
//    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
