package webSocketMessages.userCommands;

public class Leave extends UserGameCommand{
    Integer gameID;
    public Leave(String authToken, Integer gID) {
        super(authToken, CommandType.LEAVE);
        this.gameID = gID;
    }

    public Integer getID(){
        return this.gameID;
    }
}
