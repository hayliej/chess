package webSocketMessages.serverMessages;

import chess.ChessGame;
import ui.DrawChessBoard;

public class LoadGame extends ServerMessage {
    ChessGame game;
    public LoadGame(ChessGame g) {
        super(ServerMessageType.LOAD_GAME);
        this.game = g;
    }

    public DrawChessBoard getGame(){
        return new DrawChessBoard(game.getBoard());
    }
}
