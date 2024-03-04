package serviceTests;
import dataAccess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.*;
import results.CreateGameResult;
import results.ListGamesResult;
import results.LogoutResult;
import results.RegisterResult;
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

    //CLEAR

    //user clear positive 1
    @Test
    public void clearU1(){
        uService.clear();
        Map<String, UserData> umap = uService.getMap();
        Map<String, UserData> test = new HashMap<String, UserData>();
        assertEquals(umap, test);
    }

    //USER SERVICE
    //getUser (register) positive
    @Test
    public void getUserPositive(){
        Map<String, UserData> umap = uService.getMap();
        assertNotNull(umap);
    }
    //getUser (register) negative
    @Test
    public void getUserNegative(){
        UserData newUser = new UserData(null, "password", "email@byu.edu");
        try {
            RegisterResult result = uService.getUser(newUser);
            RegisterResult test = new RegisterResult("Error: bad request", null, null);
            assertEquals(result, test);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //createUser positive
    @Test
    public void createUserPositive(){
        uService.createUser(new UserData("u2", "p2", "email2@byu.edu"));
        Map<String, UserData> umap = uService.getMap();
        assertEquals(2, umap.size());
    }
    //createUser negative
    @Test
    public void createUserNegative(){
        uService.createUser(new UserData(null, "p2", "email2@byu.edu"));
        Map<String, UserData> umap = uService.getMap();
        assertEquals(1, umap.size());
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
    public void createGamePositive(){
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
    public void createGameNegative(){
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
    @Test
    public void joinGamePositive(){
        UserData u = uService.getMap().get("username");
        RegisterResult loggedIn = null;
        try {
            loggedIn = aService.login(new LoginRequest(u.username(), u.password()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            gService.createGame(new AuthNewGame(loggedIn.authToken(),"gameName"));
            LogoutResult result = gService.joinGame(new AuthJoinGame(loggedIn.authToken(),"WHITE", 1));
            assertEquals(new LogoutResult(null), result);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
    //joinGame negative
    @Test
    public void joinGameNegative(){
        UserData u = uService.getMap().get("username");
        RegisterResult loggedIn = null;
        try {
            loggedIn = aService.login(new LoginRequest(u.username(), u.password()));
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
        try {
            gService.createGame(new AuthNewGame(loggedIn.authToken(),"gameName"));
            LogoutResult result = gService.joinGame(new AuthJoinGame("loggedIn.authToken()","WHITE", 1));
            assertEquals(new LogoutResult("Error: unauthorized"), result);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
