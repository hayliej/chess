package webSocketMessages.serverMessages;

import chess.ChessGame;

public class LoadGame extends ServerMessage {
    ChessGame game;
    public LoadGame(ChessGame g) {
        super(ServerMessageType.LOAD_GAME);
        this.game = g;
    }
}
