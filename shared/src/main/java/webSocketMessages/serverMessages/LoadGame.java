package webSocketMessages.serverMessages;

import chess.ChessGame;
import ui.DrawChessBoard;

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
    public void drawBoard(ChessGame.TeamColor color){
        new DrawChessBoard(game.getBoard()).drawBoard(color);
    }

    public ChessGame getGame() {
        return game;
    }
}
