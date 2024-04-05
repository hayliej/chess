package server;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import spark.Spark;

@WebSocket
public class WSServer {

    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        //user game commands
        System.out.printf("Received: %s", message);
        session.getRemote().sendString("WebSocket response: " + message);
    }
}