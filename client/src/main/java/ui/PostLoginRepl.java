package ui;

import java.util.Scanner;

public class PostLoginRepl {
    public void run() {
        System.out.println("You are logged in. Type help for options.");

        Scanner scanner = new Scanner(System.in);
        var line = "";
        while (!line.equals("quit")){
            printPrompt();

            line = scanner.nextLine();
            switch (line){
                case "help" -> {
                    help();
                }
                case "logout" -> {
                    //logout(line);
                }
                case "create game" -> {
                    //createGame(line);
                }
                case "join game" -> {
                    //joinGame(line);
                }
                case "join observer" -> {
                    //joinObserver(line);
                }
            }
        }
        System.out.println();
    }

    private static void help() {
        System.out.print("\tcreate <NAME> - a game \n");
        System.out.print("\tlist - games \n");
        System.out.print("\tjoin <ID> [WHITE|BLACK|<empty>] - a game \n");
        System.out.print("\tobserve <ID> - a game \n");
        System.out.print("\tlogout - when you are done \n");
        System.out.print("\tquit - playing chess \n");
        System.out.print("\thelp - with possible commands \n");
    }

    private static void logout() {
        // call logout from server facade
        //send to pre-login repl
    }

    private static void createGame() {
        // call create game from server facade
        //send to game repl (next phase)
    }

    private static void joinGame() {
        // call join game from server facade
    }

    private static void joinGameObserver() {
        // call join game observer from server facade
    }

    private static void printPrompt() {
        System.out.print("[LOGGED_IN] " + ">>> ");
    }
}
