package service;
import chess.*;
import dataAccess.*;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.LoginRequest;
import requests.RegisterResult;
import requests.UserData;
import service.*;

import java.util.HashMap;
import java.util.Map;

import static org.junit.jupiter.api.Assertions.*;

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

    //CLEAR positive
    @Test
    public void clearCheck(){
        uService.clear();
        Map<String, UserData> umap = uService.getMap();
        Map<String, UserData> test = new HashMap<String, UserData>();
        assertEquals(umap, test);
    }

    //USER SERVICE
    //getUser (register) positive
    @Test
    public void registerPositive(){
        Map<String, UserData> umap = uService.getMap();
        assertNotNull(umap);
    }
    //getUser (register) negative
    @Test
    public void registerNegative(){
        UserData newUser = new UserData(null, "password", "email@byu.edu");
        try {
            RegisterResult result = uService.getUser(newUser);
            RegisterResult test = new RegisterResult("Error: bad request", null, null);
            assertEquals(result, test);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }


    //AUTH SERVICE
    //login positive
    @Test
    public void loginPositive(){
        try {
            aService.login(new LoginRequest("username", "password"));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        Map<Object, Object> amap = aService.getMap();
        assertNotNull(amap);
    }
    //login negative
    @Test
    public void loginNegative(){
        try {
            RegisterResult result = aService.login(new LoginRequest("username", null));
            RegisterResult test = new RegisterResult("Error: unauthorized", null, null);
            assertEquals(result, test);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //logout positive

    //logout negative


    //GAME SERVICE
    //listGames positive
    //listGames negative
    //createGame positive
    //createGame negative
    //joinGame positive
    //joinGame negative
}
