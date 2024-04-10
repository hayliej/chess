package webSocketMessages.serverMessages;

import chess.ChessGame;

public class LoadGame extends ServerMessage {
    ChessGame game;
    ChessGame.TeamColor color;
    public LoadGame(ChessGame g, ChessGame.TeamColor c) {
        super(ServerMessageType.LOAD_GAME);
        this.game = g;
        this.color = c;
    }

    public ChessGame.TeamColor getColor(){
        return color;
    }

    public ChessGame getGame() {
        return game;
    }
}
