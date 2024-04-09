package ui;
import chess.ChessGame;
import com.google.gson.Gson;
import webSocketMessages.serverMessages.*;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.userCommands.UserGameCommand;

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
                //figure out type of ServerMessage
                ServerMessage msg = new Gson().fromJson(message, ServerMessage.class);
                if (msg.getServerMessageType().equals(ServerMessage.ServerMessageType.ERROR)){
                    Error mesg = new Gson().fromJson(message, Error.class);
                    error(mesg);
                } else if (msg.getServerMessageType().equals(ServerMessage.ServerMessageType.LOAD_GAME)){
                    LoadGame mesg = new Gson().fromJson(message, LoadGame.class);
                    loadGame(mesg);
                } else if (msg.getServerMessageType().equals(ServerMessage.ServerMessageType.NOTIFICATION)) {
                    Notification mesg = new Gson().fromJson(message, Notification.class);
                    notification(mesg);
                }
                //send to game ui (if needed) to handle
//                System.out.println(message);
            }
        });
    }

    public void error(Error msg){
        System.out.println(msg.getErrorMessage());
    }

    public void loadGame(LoadGame msg){
        ChessGame.TeamColor color = ChessGame.TeamColor.WHITE;
        //for each person in/observing game draw the board, if it's the black user change color to black
        System.out.println(msg.getGame().main(color););
    }

    public void notification(Notification msg){

    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
        //???
    }
}
