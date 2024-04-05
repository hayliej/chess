package webSocketMessages.userCommands;

import chess.ChessGame;

public class JoinObserver extends UserGameCommand{
    Integer gameID;

    public JoinObserver(String authToken, Integer gameID) {
        super(authToken);
        this.gameID = gameID;
    }
}
