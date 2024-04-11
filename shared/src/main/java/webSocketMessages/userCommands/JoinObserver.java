package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinObserver extends UserGameCommand{
    Integer gameID;

    public JoinObserver(String authToken, Integer gameID) {
        super(authToken, CommandType.JOIN_OBSERVER);
        this.gameID = gameID;
    }
    public Integer getID(){
        return this.gameID;
    }
}
