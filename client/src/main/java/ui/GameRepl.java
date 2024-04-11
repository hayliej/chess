package ui;

import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import com.google.gson.Gson;
import dataAccess.DataAccessException;
import webSocketMessages.serverMessages.Error;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;


import java.util.Scanner;

public class GameRepl implements NotificationHandler {

    static String authToken = "";
    static Integer gameID;
    static ChessGame game;
    static String color;
    public static void setAuth(String auth){
        authToken = auth;
    }
    public static void setGameID(Integer id) { gameID = id; }
    public static void setColor(String c) { color = c; }
    public static void setGame(ChessGame g) { game = g; }
    public GameRepl() throws Exception {
        this.wsf = new WebSocketFacade(this);
    }

    WebSocketFacade wsf;

    public void joinObserver() throws Exception {
        JoinObserver mm = new JoinObserver(authToken, gameID);
        String msg = new Gson().toJson(mm);
        wsf.send(msg);
    }

    public void joinPlayer() throws Exception {
        ChessGame.TeamColor c = null;
        if (color.equals("WHITE")){
            c = ChessGame.TeamColor.WHITE;
        } else if (color.equals("BLACK")){
            c = ChessGame.TeamColor.BLACK;
        }
        JoinPlayer mm = new JoinPlayer(authToken, gameID, c);
        String msg = new Gson().toJson(mm);
        wsf.send(msg);
    }

    public void run() throws DataAccessException {
        //loadGame
        if (!(game==null)){
            redraw();
        }

        Scanner scanner = new Scanner(System.in);
        var line = "";
        while (!line.equals("leave")){
            printPrompt();

            line = scanner.nextLine();
            try {
                if (line.startsWith("help")) {
                    help();
                } else if (line.startsWith("move")) {
                    makeMove(line);
                } else if (line.startsWith("leave")) {
                    leave();
                    //send back to post-login (main)
                    break;
                } else if (line.startsWith("resign")) {
                    resign();
                } else if (line.startsWith("redraw")){
                    redraw();
                } else if (line.startsWith("highlight")){
                    highlight();
                }
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }

        }
        System.out.println();
    }


    private static void help() {
        System.out.print("\tredraw - chessboard \n");
        System.out.print("\tleave - the game \n");
        System.out.print("\tmove <start position> <end position> <piece type lowercase>- a piece \n");
        System.out.print("\tresign - forfeit the game \n");
        System.out.print("\thighlight - legal moves \n");
        System.out.print("\thelp - with possible commands \n");
    }

    //SEND THE MESSAGES FROM HERE. THIS IS WHERE THE TYPE COMES FROM.
    private void makeMove(String input) throws Exception { //fix this later
        //parse out input to get ID, start/end positions, pieceType
        String[] in = input.split(" <");
        String s = in[1];
        String start = s.replace(">", "");
        start = coordToPosition(start);
        String[] startP = start.split("");
        Integer startRow = Integer.valueOf(startP[0]);
        Integer startCol = Integer.valueOf(startP[1]);
        ChessPosition cps = new ChessPosition(startRow, startCol);
        String e = in[2];
        String end = e.replace(">", "");
        end = coordToPosition(end);
        String[] endP = end.split("");
        Integer endRow = Integer.valueOf(endP[0]);
        Integer endCol = Integer.valueOf(endP[1]);
        ChessPosition cpe = new ChessPosition(endRow, endCol);
        String pt = in[3];
        String pieceType = pt.replace(">", "");
        ChessPiece.PieceType ptype = null;

        if (pieceType.equals("king")){
            ptype = ChessPiece.PieceType.KING;
        } else if (pieceType.equals("knight")){
            ptype = ChessPiece.PieceType.KNIGHT;
        } else if (pieceType.equals("queen")){
            ptype = ChessPiece.PieceType.QUEEN;
        } else if (pieceType.equals("rook")){
            ptype = ChessPiece.PieceType.ROOK;
        } else if (pieceType.equals("pawn")){
            ptype = ChessPiece.PieceType.PAWN;
        } else if (pieceType.equals("bishop")){
            ptype = ChessPiece.PieceType.BISHOP;
        }

        ChessMove cm = new ChessMove(cps, cpe, ptype);
        MakeMove mm = new MakeMove(authToken, gameID, cm);
        String msg = new Gson().toJson(mm);
        wsf.send(msg);
    }

    public String coordToPosition(String in){
        String a = in.replace("a", "1");
        String b = a.replace("b", "2");
        String c = b.replace("c", "3");
        String d = c.replace("d", "4");
        String e = d.replace("e", "5");
        String f = e.replace("f", "6");
        String g = f.replace("g", "7");
        String h = g.replace("h", "8");
        return h;
    }

    private void leave() throws Exception {
        Leave lm = new Leave(authToken, gameID);
        String msg = new Gson().toJson(lm);
        wsf.send(msg);
    }

    private void resign() throws Exception {
        Resign rm = new Resign(authToken, gameID);
        String msg = new Gson().toJson(rm);
        wsf.send(msg);
    }

    public void drawBoard(LoadGame lg){
        ChessGame.TeamColor c = ChessGame.TeamColor.WHITE;
        if (color.equals("BLACK")){
            c = ChessGame.TeamColor.BLACK;
        }
        setGame(lg.getGame());
        new DrawChessBoard(game.getBoard()).drawBoard(c);
    }

    private void redraw() {
        ChessGame.TeamColor c = ChessGame.TeamColor.WHITE;
        if (color.equals("BLACK")){
            c = ChessGame.TeamColor.BLACK;
        }
        LoadGame lgm = new LoadGame(game, c);
        loadGame(lgm);
    }

    private static void highlight() {
    }

    private static void printPrompt() {
        System.out.print("[GAMEPLAY] " + ">>> ");
    }

    @Override
    public void notify(ServerMessage notification) {
        switch (notification.getServerMessageType()){
            case LOAD_GAME -> loadGame((LoadGame) notification);
            case NOTIFICATION -> notification((Notification) notification);
            case ERROR -> error((Error) notification);
        }
    }
    public void error(Error msg){
        System.out.println(msg.getErrorMessage());
    }

    public void loadGame(LoadGame msg){
        ChessGame.TeamColor color = msg.getColor();
        if (msg.getColor() == null){
            color = ChessGame.TeamColor.WHITE;
        }
        setGame(msg.getGame());
        new DrawChessBoard(game.getBoard()).drawBoard(color);
    }

    public void notification(Notification msg){
        System.out.println(msg.getNotification());
    }
}
