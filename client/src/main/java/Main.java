import ui.PostLoginRepl;
import ui.ServerFacade;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Chess! Type help to start.");

        Scanner scanner = new Scanner(System.in);
        String line = "";
        while (!line.equals("quit")){
            printPrompt();

            line = scanner.nextLine();
            if (line.startsWith("help")){
                help();
            } else if (line.startsWith("login")){
                login(line);
            } else if (line.startsWith("register")){
                register(line);
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
        //parse out input to get username and password
        String[] in = input.split(" <");
        String u = in[1];
        String username = u.replace(">", "");
        String p = in[2];
        String password = p.replace(">", "");

        // call login from server facade
        new ServerFacade("http://localhost:8080").login(username, password);
        // send to post-login repl
        new PostLoginRepl().run();
    }

    //register
    private static void register(String input) {
        //parse input
        String[] in = input.split(" <");
        String u = in[1];
        String username = u.replace(">", "");
        String p = in[2];
        String password = p.replace(">", "");
        String e = in[3];
        String email = p.replace(">", "");
        // call register from server facade
        new ServerFacade("http://localhost:8080").register(username, password, email);
        //send to post-login repl
        new PostLoginRepl().run();
    }

    private static void printPrompt() {
        System.out.print("[LOGGED_OUT] " + ">>> ");
    }
}