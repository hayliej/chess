package webSocketMessages.userCommands;

public class Resign extends UserGameCommand{
    Integer gameID;
    public Resign(String authToken, Integer gID) {
        super(authToken, CommandType.RESIGN);
        this.gameID = gID;
    }

    public Integer getID(){
        return this.gameID;
    }
}
