package webSocketMessages.serverMessages;

import chess.ChessGame;

public class LoadGame extends ServerMessage {
    ChessGame game;
    public LoadGame(ServerMessageType type, ChessGame g) {
        super(type);
        this.game = g;
    }
}
