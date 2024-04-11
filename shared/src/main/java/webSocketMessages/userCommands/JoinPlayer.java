package webSocketMessages.userCommands;

import chess.ChessGame;

import static webSocketMessages.userCommands.UserGameCommand.CommandType.JOIN_PLAYER;

public class JoinPlayer extends UserGameCommand{
    Integer gameID;
    ChessGame.TeamColor playerColor;

    public JoinPlayer(String authToken, Integer gameID, ChessGame.TeamColor playerColor) {
        super(authToken,JOIN_PLAYER);
        this.gameID = gameID;
        this.playerColor = playerColor;
    }
    public Integer getID(){
        return this.gameID;
    }

    public ChessGame.TeamColor getColor(){
        return this.playerColor;
    }
}
