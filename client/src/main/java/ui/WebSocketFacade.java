package ui;
import com.google.gson.Gson;
import webSocketMessages.serverMessages.*;
import webSocketMessages.serverMessages.Error;

import javax.websocket.*;
import java.net.URI;

public class WebSocketFacade extends Endpoint {
    public Session session;

    public WebSocketFacade(NotificationHandler notificationHandler) throws Exception {
        URI uri = new URI("ws://localhost:8080/connect");
        WebSocketContainer container = ContainerProvider.getWebSocketContainer();
        this.session = container.connectToServer(this, uri);

        this.session.addMessageHandler(new MessageHandler.Whole<String>() {
            public void onMessage(String message) {
                //figure out type of ServerMessage
                ServerMessage msg = new Gson().fromJson(message, ServerMessage.class);
                if (msg.getServerMessageType().equals(ServerMessage.ServerMessageType.ERROR)){
                    Error mesg = new Gson().fromJson(message, Error.class);
                    notificationHandler.notify(mesg);
                } else if (msg.getServerMessageType().equals(ServerMessage.ServerMessageType.LOAD_GAME)){
                    LoadGame mesg = new Gson().fromJson(message, LoadGame.class);
                    notificationHandler.notify(mesg);
                } else if (msg.getServerMessageType().equals(ServerMessage.ServerMessageType.NOTIFICATION)) {
                    Notification mesg = new Gson().fromJson(message, Notification.class);
                    notificationHandler.notify(mesg);
                }
//                System.out.println(message);
            }
        });
    }

    public void send(String msg) throws Exception {
        this.session.getBasicRemote().sendText(msg);
    }

    public void onOpen(Session session, EndpointConfig endpointConfig) {
    }
}
