import chess.*;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        System.out.println("Welcome to Chess! Type help to start.");

        Scanner scanner = new Scanner(System.in);
        var result = "";
        while (!result.equals("quit")){
            //print prompt

            String input = scanner.nextLine();
            switch (input){
                case "help" -> {
                    //print help thing
                }
                case "quit" -> {
                    //break out of loop
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
    //put method calls to other repls here:
    //login
    //register
}