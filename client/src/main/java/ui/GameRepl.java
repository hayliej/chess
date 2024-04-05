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
                if (line.startsWith("move")) {
                    makeMove();
                } else if (line.startsWith("leave")) {
                    leave();
                    //send back to post-login (main)
                    break;
                } else if (line.startsWith("resign")) {
                    resign(line);
                }
            } catch (Exception e) {
                System.out.print(e.getMessage());
            }

        }
        System.out.println();
    }

    private static void makeMove() {
    }

    private static void leave() {
    }

    private static void resign(String line) {
    }

    private static void printPrompt() {
        System.out.print("[GAMEPLAY] " + ">>> ");
    }
}
