package ui;
import webSocketMessages.serverMessages.*;
import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

public class WebSocketFacade extends Endpoint {
    public static void main(String[] args) throws Exception {
        var ws = new WebSocketFacade();
        Scanner scanner = new Scanner(System.in);

        while (true) ws.send(scanner.nextLine());
    }

    public Session session;

    public WebSocketFacade() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                //figure out type
//                ServerMessage msg = new ServerMessage(message); // needs type, but don't know type :/ ugh
//                if (msg.getServerMessageType().equals(LOAD_GAME)){
//                }
                //send to game ui (if needed) to handle
                System.out.println(message);
            }
        });
    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
        //???
    }
}
