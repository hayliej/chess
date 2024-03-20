import chess.*;

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
                    //call login method
                }
                case "register" -> {
                    //call register method
                }
            }
        }

    }

    private static void help() {
        System.out.print("\tregister <USERNAME> <PASSWORD> <EMAIL> ");
        System.out.print("- to create an account \n");
        System.out.print("\tlogin <USERNAME> <PASSWORD> ");
        System.out.print("- to play chess \n");
        System.out.print("\tquit ");
        System.out.print("- playing chess \n");
        System.out.print("\thelp ");
        System.out.print("- with possible commands \n");
    }

    //login
    private static void login() {
        //send to post-login repl
    }

    //register
    private static void register() {
        //send to post-login repl
    }

    private static void printPrompt() {
        System.out.print("[LOGGED_OUT] " + ">>> ");
    }
}