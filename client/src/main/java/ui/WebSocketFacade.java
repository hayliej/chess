package ui;
import javax.websocket.*;
import java.net.URI;
import java.util.Scanner;

public class WebSocketFacade {
    public static void main(String[] args) throws Exception {
        var ws = new WebSocketFacade();
        Scanner scanner = new Scanner(System.in);

        System.out.println("echo");
        while (true) ws.send(scanner.nextLine());
    }

    public Session session;

    public WebSocketFacade() throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
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

    //USER GAME COMMANDS:
    //joinPlayer
    //join Observer
    //makeMove
    //leave
    //resign

    //SERVER MESSAGES:
    //error
    //loadGame
    //notification
}
