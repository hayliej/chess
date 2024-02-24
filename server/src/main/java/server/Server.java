package server;

import spark.*;

public class Server {

    public int run(int desiredPort) {
        Spark.port(desiredPort);
        Spark.staticFiles.location("web");

        // Register your endpoints and handle exceptions here.
        Spark.post("/login", this::login);

        Spark.awaitInitialization();
        return Spark.port();
    }

    private Object login(Request req, Response res) {
        return "";
    }

    public void stop() {
        Spark.stop();
        Spark.awaitStop();
    }
}
