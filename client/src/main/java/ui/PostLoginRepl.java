package ui;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Scanner;
import dataAccess.DataAccessException;
import webSocketMessages.userCommands.JoinObserver;

public class PostLoginRepl {

    public void run() throws DataAccessException {
        System.out.println("You are logged in. Type help for options.");

        Scanner scanner = new Scanner(System.in);
        var line = "";
        while (!line.equals("quit")){
            printPrompt();

            line = scanner.nextLine();
            try {
                if (line.startsWith("help")) {
                    help();
                } else if (line.startsWith("logout")) {
                    logout();
                    //send back to pre-login (main)
                    break;
                } else if (line.startsWith("create")) {
                    createGame(line);
                } else if (line.startsWith("join")) {
                    joinGame(line);
                } else if (line.startsWith("observe")) {
                    joinGameObserver(line);
                } else if (line.startsWith("list")) {
                    list();
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
        System.out.print("\tcreate <NAME> - a game \n");
        System.out.print("\tlist - games \n");
        System.out.print("\tjoin <ID no decimal> [<WHITE>|<BLACK>|<empty>] - a game \n");
        System.out.print("\tobserve <ID> - a game \n");
        System.out.print("\tlogout - when you are done \n");
        System.out.print("\tquit - playing chess \n");
        System.out.print("\thelp - with possible commands \n");
    }

    private static void list() throws DataAccessException {
        String auth = authToken;
        // call logout from server facade
        ServerFacade sf = new ServerFacade("http://localhost:8080");
        for (Object game : sf.listGames(auth).games()){
            System.out.print(game);
            System.out.println();
        }
    }

    private static void logout() throws DataAccessException {
        String auth = authToken;
        // call logout from server facade
        new ServerFacade("http://localhost:8080").logout(auth);
    }

    private static void createGame(String input) throws DataAccessException {
        //parse input to get game name
        String[] in = input.split(" <");
        String g = in[1];
        String gameName = g.replace(">", "");
        // call create game from server facade
        new ServerFacade("http://localhost:8080").createGame(authToken, gameName);
        //send to game repl (next phase) ??
    }



    private static void joinGame(String input) throws Exception {
        //parse out input to get ID and color
        String[] in = input.split(" <");
        String i = in[1];
        String id = i.replace(">", "");
        Integer idNum = Integer.valueOf(id);
        String c = in[2];
        String color = c.replace(">", "");
        GameRepl.setGameID(idNum);
        GameRepl.setColor(color);
        // call join game from server facade
        new ServerFacade("http://localhost:8080").joinGame(authToken, idNum, color);
        //DrawChessBoard.main(null);//get rid of this once ws implemented
        GameRepl gpr = new GameRepl();
        gpr.joinPlayer();
        gpr.run();
    }

    private static void joinGameObserver(String input) throws Exception {
        //parse out input to get ID and color
        String[] in = input.split(" <");
        String i = in[1];
        String id = i.replace(">", "");
        Integer idNum = Integer.valueOf(id);
        GameRepl.setGameID(idNum);
        GameRepl.setColor("white");
        // call join game observer from server facade
        new ServerFacade("http://localhost:8080").observeGame(authToken, idNum);
        //DrawChessBoard.main(null);//get rid of this once ws implemented
        //wsf.joinObserver(new JoinObserver(authToken, idNum), wsf); //what's this doing??
        GameRepl gpr = new GameRepl();
        gpr.joinObserver();
        gpr.run();
    }

    private static void printPrompt() {
        System.out.print("[LOGGED_IN] " + ">>> ");
    }
}