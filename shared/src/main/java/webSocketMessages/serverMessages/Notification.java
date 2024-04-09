package webSocketMessages.serverMessages;

public class Notification extends ServerMessage{
    String message;
    public Notification(String m) {
        super(ServerMessageType.NOTIFICATION);
        message = m;
    }

    public String getNotification(){
        return message;
    }
}
