package server;

import passoffTests.testClasses.TestException;
import spark.*;

public class Server {

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

    private Object clear(Request req, Response res) {
        //clear db
        req.();
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
