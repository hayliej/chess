import chess.*;
import ui.PostLoginRepl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Chess! Type help to start.");

        Scanner scanner = new Scanner(System.in);
        String line = "";
        while (!line.equals("quit")){
            printPrompt();

            line = scanner.nextLine();
            switch (line){
                case "help" -> {
                    help();
                }
                case "quit" -> {
                    break;
                }
                case "login" -> {
                    login(line);
                }
                case "register" -> {
                    register(line);
                }
            }
        }

    }

    private static void help() {
        System.out.print("\tregister <USERNAME> <PASSWORD> <EMAIL> - to create an account \n");
        System.out.print("\tlogin <USERNAME> <PASSWORD> - to play chess \n");
        System.out.print("\tquit - playing chess \n");
        System.out.print("\thelp - with possible commands \n");
    }

    //login
    private static void login(String input) {
        //parse out input to get username and password!!!!

        String username = "";
        String password = "";
        // call login from server facade
        new ServerFacade("http://localhost:8080").login(username, password);
        // send to post-login repl
        new PostLoginRepl().run();
    }

    //register
    private static void register(String input) {
        // call register from server facade
        //send to post-login repl
    }

    private static void printPrompt() {
        System.out.print("[LOGGED_OUT] " + ">>> ");
    }
}