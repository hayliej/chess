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
        if (color.equals("white")){
            c = ChessGame.TeamColor.WHITE;
        } else if (color.equals("black")){
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
        while (!line.equals("quit")){
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
                    //send back to post-login (main)
                    break;
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
        System.out.print("\tmove <gameID no decimal> <start position> <end position> <PIECE TYPE>- a piece \n");
        System.out.print("\tresign - forfeit the game \n");
        System.out.print("\thighlight - legal moves \n");
        System.out.print("\thelp - with possible commands \n");
    }

    //SEND THE MESSAGES FROM HERE. THIS IS WHERE THE TYPE COMES FROM.
    private void makeMove(String input) throws Exception { //fix this later
        //parse out input to get ID, start/end positions, pieceType
        String[] in = input.split(" <");
        String g = in[1];
        String gID = g.replace(">", "");
        Integer gIDNum = Integer.valueOf(gID);
        String s = in[2];
        String start = s.replace(">", "");
        start = coordToPosition(start);
        ChessPosition cps = new ChessPosition(start.charAt(0), start.charAt(1));
        String e = in[2];
        String end = e.replace(">", "");
        end = coordToPosition(end);
        ChessPosition cpe = new ChessPosition(end.charAt(0), end.charAt(1));
        String pt = in[2];
        String pieceType = pt.replace(">", "");
        ChessPiece.PieceType ptype = null;
        switch (pieceType){
            case "king":
               ptype = ChessPiece.PieceType.KING;
            case "knight":
                ptype = ChessPiece.PieceType.KNIGHT;
            case "queen":
                ptype = ChessPiece.PieceType.QUEEN;
            case "rook":
                ptype = ChessPiece.PieceType.ROOK;
            case "pawn":
                ptype = ChessPiece.PieceType.PAWN;
            case "bishop":
                ptype = ChessPiece.PieceType.BISHOP;
        }

        ChessMove cm = new ChessMove(cps, cpe, ptype);
        MakeMove mm = new MakeMove(authToken, gIDNum, cm);
        String msg = new Gson().toJson(mm);
        wsf.send(msg);
    }

    public String coordToPosition(String in){
        String out = in.replace("a", "1");
        out = in.replace("b", "2");
        out = in.replace("c", "3");
        out = in.replace("d", "4");
        out = in.replace("e", "5");
        out = in.replace("f", "6");
        out = in.replace("g", "7");
        out = in.replace("h", "8");
        return out;
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
        if (color.equals("black")){
            c = ChessGame.TeamColor.BLACK;
        }
        setGame(lg.getGame());
        new DrawChessBoard(game.getBoard()).drawBoard(c);
    }

    private void redraw() {
        ChessGame.TeamColor c = ChessGame.TeamColor.WHITE;
        if (color.equals("black")){
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
