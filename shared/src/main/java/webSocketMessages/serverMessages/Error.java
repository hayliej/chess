package webSocketMessages.serverMessages;

public class Error extends ServerMessage{
    String errorMessage;
    public Error(String em) {
        super(ServerMessageType.ERROR);
        errorMessage = em;
    }
    public String getErrorMessage(){
        return errorMessage;
    }
}
