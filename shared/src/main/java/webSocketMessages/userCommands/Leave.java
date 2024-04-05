package webSocketMessages.userCommands;

public class Leave extends UserGameCommand{
    Integer gameID;
    public Leave(String authToken, Integer gID) {
        super(authToken);
        this.gameID = gID;
    }
}
