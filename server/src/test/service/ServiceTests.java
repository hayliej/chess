package service;
import chess.*;
import dataAccess.*;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.BeforeEach;
import requests.UserData;
import service.*;
public class ServiceTests {
    static GameService gService = new GameService();
    static UserService uService = new UserService();
    static AuthService aService = new AuthService();

    @BeforeEach
    public void setup(){
        gService.clear();
        aService.clear();
        uService.clear();

        UserData user = new UserData("username", "password", "email@byu.edu");
        uService.createUser(user);
    }
}
