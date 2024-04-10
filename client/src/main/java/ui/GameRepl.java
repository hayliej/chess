package ui;

import dataAccess.DataAccessException;


import java.util.Scanner;

public class GameRepl {
    public void run() throws DataAccessException {
        //loadGame
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
                    resign(line);
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

    static String authToken = "";
    public static void setAuth(String auth){
        authToken = auth;
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
    private static void makeMove(String input) { //fix this later
        //parse out input to get ID and color
//        String[] in = input.split(" <");
//        String g = in[1];
//        String gID = g.replace(">", "");
//        Integer gIDNum = Integer.valueOf(gID);
//        String s = in[2];
//        String start = s.replace(">", "");
//        String e = in[2];
//        String end = e.replace(">", "");
//        String pt = in[2];
//        String pieceType = pt.replace(">", "");
//        ChessMove cm = new ChessMove(start, end, pieceType);
//        UserGameCommand ugc = new MakeMove(authToken, gIDNum, cm);
//        //need to make into gson for server?
//        new WSServer(session, ugc);
    }

    private void leave() {
        //WebSocketFacade.send(msg);
    }

    private static void resign(String line) {
    }

    private static void redraw() {
    }

    private static void highlight() {
    }

    private static void printPrompt() {
        System.out.print("[GAMEPLAY] " + ">>> ");
    }
}
