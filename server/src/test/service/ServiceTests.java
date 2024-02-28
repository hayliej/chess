package service;
import chess.*;
import dataAccess.*;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.*;
import service.*;

import java.util.ArrayList;
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
        try {
            uService.getUser(user);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
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
    @Test
    public void logoutPositive(){
        UserData u = uService.getMap().get("username");
        try {
            RegisterResult loggedIn = aService.login(new LoginRequest(u.username(), u.password()));
            LogoutResult result = aService.logout(loggedIn.authToken());
            assertEquals(new LogoutResult(""),result);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    //logout negative
    @Test
    public void logoutNegative(){
        UserData u = uService.getMap().get("username");
        try {
            RegisterResult loggedIn = aService.login(new LoginRequest(u.username(), u.password()));
            LogoutResult result = aService.logout("loggedIn.authToken()");
            assertEquals(new LogoutResult("Error: unauthorized"),result);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }


    //GAME SERVICE
    //listGames positive
    @Test
    public void listGamesPositive(){
        UserData u = uService.getMap().get("username");
        RegisterResult loggedIn = null;
        try {
            loggedIn = aService.login(new LoginRequest(u.username(), u.password()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            ListGamesResult result = gService.listGames(loggedIn.authToken());
            assertEquals(new ListGamesResult(null, new ArrayList()), result);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    //listGames negative
    @Test
    public void listGamesNegative(){
        UserData u = uService.getMap().get("username");
        RegisterResult loggedIn = null;
        try {
            loggedIn = aService.login(new LoginRequest(u.username(), u.password()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            ListGamesResult result = gService.listGames("loggedIn.authToken()");
            assertEquals(new ListGamesResult("Error: unauthorized", null), result);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    //createGame positive
    @Test
    public void createGamesPositive(){
        UserData u = uService.getMap().get("username");
        RegisterResult loggedIn = null;
        try {
            loggedIn = aService.login(new LoginRequest(u.username(), u.password()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            CreateGameResult result = gService.createGame(new AuthNewGame(loggedIn.authToken(),"gameName"));
            assertEquals(new CreateGameResult(null, 1), result);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    //createGame negative
    @Test
    public void createGamesNegative(){
        UserData u = uService.getMap().get("username");
        RegisterResult loggedIn = null;
        try {
            loggedIn = aService.login(new LoginRequest(u.username(), u.password()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            CreateGameResult result = gService.createGame(new AuthNewGame(loggedIn.authToken(),null));
            assertEquals(new CreateGameResult("Error: bad request", null), result);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //joinGame positive

    //joinGame negative

}
