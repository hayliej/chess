package webSocketMessages.userCommands;

import chess.ChessMove;

public class MakeMove extends UserGameCommand{
    Integer gameID;
    ChessMove move;

    public MakeMove(String authToken, Integer gameID, ChessMove move) {
        super(authToken);
        this.gameID = gameID;
        this.move = move;
    }

    public Integer getID(){
        return this.gameID;
    }

    public ChessMove getMove(){
        return this.move;
    }

}
