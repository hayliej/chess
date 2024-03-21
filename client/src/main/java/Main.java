import chess.*;
import ui.PostLoginRepl;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Chess! Type help to start.");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")){
            printPrompt();

            String input = scanner.nextLine();
            switch (input){
                case "help" -> {
                    help();
                }
                case "quit" -> {
                    break;
                }
                case "login" -> {
                    login();
                }
                case "register" -> {
                    register();
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
    private static void login() {
        // call login from server facade
        ServerFacade.login();
        // send to post-login repl
        new PostLoginRepl().run();
    }

    //register
    private static void register() {
        // call register from server facade
        //send to post-login repl
    }

    private static void printPrompt() {
        System.out.print("[LOGGED_OUT] " + ">>> ");
    }
}