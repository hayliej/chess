package dataAccessTests;

import chess.ChessGame;
import dataAccess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.AuthData;
import requests.GameData;
import requests.UserData;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

public class DataAccessTests {
    static GameDAO gDAO;

    static {
        try {
            gDAO = new SqlGameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static AuthDAO aDAO;

    static {
        try {
            aDAO = new SqlAuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    static UserDAO uDAO;

    static {
        try {
            uDAO = new SqlUserDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @BeforeEach
    public void setup() throws DataAccessException {
        gDAO.clear();
        aDAO.clear();
        uDAO.clear();

        UserData user = new UserData("username", "password", "email@byu.edu");
        try {
            uDAO.addUser(user);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }

        String authToken = "testAuth";
        AuthData auth = new AuthData(authToken, user.username());
        try {
            aDAO.addAuth(auth);
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    //CLEAR
    @Test
    public void clear() throws DataAccessException {
        uDAO.clear();
        Map<String, UserData> umap = uDAO.returnUsers();
        Map<String, UserData> test = new HashMap<String, UserData>();
        assertEquals(umap, test);
    }


    //USER
    //addUser positive
    @Test
    public void addUserPositive() throws DataAccessException {
        Map<String, UserData> testmap = new HashMap<>();
        testmap.put("username", new UserData("username", "password", "email@byu.edu"));
        Map<String, UserData> umap = uDAO.returnUsers();
        assertEquals(testmap, umap);
    }

    //addUser negative
    @Test
    public void addUserNegative() throws DataAccessException {
        UserData newUser = new UserData(null, "password", "email@byu.edu");
        assertThrows(DataAccessException.class, ()->uDAO.addUser(newUser));
    }

    //returnUsers positive
    @Test
    public void returnUserPositive() throws DataAccessException {
        Map<String, UserData> testmap = new HashMap<>();
        testmap.put("username", new UserData("username", "password", "email@byu.edu"));
        Map<String, UserData> returnmap = uDAO.returnUsers();
        assertEquals(testmap, returnmap);
    }

//    //returnUsers negative
    @Test
    public void returnUserNegative() throws DataAccessException {
        uDAO.clear();
        Map<String, UserData> returnMap = uDAO.returnUsers();
        assertTrue(returnMap.isEmpty());
    }

    //AUTH
    //addAuth positive
    @Test
    public void addAuthPositive() throws DataAccessException {
        String authToken = "testAuth";
        AuthData testauth = new AuthData(authToken, "username");
        Map<Object, Object> testmap = new HashMap<>();
        testmap.put(authToken, testauth);
        Map<Object, Object> amap = aDAO.returnAuths();
        assertEquals(testmap, amap);
    }

    //addAuth negative
    @Test
    public void addAuthNegative() throws DataAccessException {
        AuthData newAuth = new AuthData(null, "username");
        boolean thrown = false;
        try {
            aDAO.addAuth(newAuth);
        } catch (DataAccessException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    //removeAuth positive
    @Test
    public void removeAuthPositive() throws DataAccessException {
        aDAO.removeAuth("testAuth");
        Map<Object, Object> amap = aDAO.returnAuths();
        Map<Object, Object> testmap = new HashMap<>();
        assertEquals(testmap, amap);
    }

    //removeAuth negative
    @Test
    public void removeAuthNegative() throws DataAccessException {
        aDAO.removeAuth("notPresent");
        assertEquals(1, aDAO.returnAuths().size());
    }

    //returnAuths positive
    @Test
    public void returnAuthsPositive() throws DataAccessException {
        Map<Object, Object> testmap = new HashMap<>();
        testmap.put("testAuth", new AuthData("testAuth", "username"));
        Map<Object, Object> returnmap = aDAO.returnAuths();
        assertEquals(testmap, returnmap);
    }

    //returnAuths negative
    @Test
    public void returnAuthNegative() throws DataAccessException {
        aDAO.clear();
        Map<Object, Object> returnMap = aDAO.returnAuths();
        assertTrue(returnMap.isEmpty());
    }
    //getVal positive
    @Test
    public void getValPositive() throws DataAccessException {
        String username = aDAO.getVal("testAuth");
        String test = "username";
        assertEquals(test, username);
    }

    //getVal negative
    @Test
    public void getValNegative() throws DataAccessException {
        assertNull(aDAO.getVal("NotHere"));
    }


    //GAME
    //addGame positive
    @Test
    public void addGamePositive() throws DataAccessException {
        Map<Integer, GameData> testmap = new HashMap<>();
        GameData gdata = new GameData(1, "w", "b", "name", null);
        testmap.put(1, gdata);
        gDAO.addGame(1, gdata);
        Map<Integer, GameData> gmap = gDAO.returnGames();
        assertEquals(testmap, gmap);
    }

    //addGame negative
    @Test
    public void addGameNegative() throws DataAccessException {
        GameData gdata = new GameData(1, "w", "b", null, null);
        boolean thrown = false;
        try {
            gDAO.addGame(1, gdata);
        } catch (DataAccessException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    //updateGames positive
    @Test
    public void updateGamesPositive() throws DataAccessException {
        Map<Integer, GameData> testmap = new HashMap<>();
        GameData gdata = new GameData(1, null, "b", "name", null);
        gDAO.addGame(1, gdata);
        GameData gdata2 = new GameData(1, "w", "b", "name", null);
        testmap.put(1, gdata2);
        gDAO.updateGames(1, "white", "w");
        Map<Integer, GameData> gmap = gDAO.returnGames();
        assertEquals(testmap, gmap);
    }

    //updateGames negative
    @Test
    public void updateGamesNegative() throws DataAccessException {
        GameData gdata = new GameData(1, null, "b", "name", null);
        gDAO.addGame(1, gdata);
        gDAO.updateGames(2, "white", "w;");
        assertNull(gDAO.returnGames().get(1).whiteUsername());
    }

    //getSize positive
    @Test
    public void getSizePositive() throws DataAccessException {
        GameData gdata = new GameData(1, null, "b", "name", null);
        gDAO.addGame(1, gdata);
        Integer g = gDAO.getSize();
        assertEquals(1, g);
    }

    //getSize negative
    @Test
    public void getSizeNegative() throws DataAccessException {
        GameData gdata = new GameData(1, null, "b", "name", null);
        gDAO.addGame(1, gdata);
        gDAO.clear();
        assertEquals(0, gDAO.getSize());
    }

    //returnGames positive
    @Test
    public void returnGamesPositive() throws DataAccessException {
        GameData gdata = new GameData(1, null, "b", "name", null);
        gDAO.addGame(1, gdata);
        Map<Integer, GameData> testmap = new HashMap<>();
        testmap.put(1, gdata);
        Map<Integer, GameData> returnmap = gDAO.returnGames();
        assertEquals(testmap, returnmap);
    }

    //returnGames negative
    @Test
    public void returnGamesNegative() throws DataAccessException {
        gDAO.clear();
        Map<Integer, GameData> returnMap = gDAO.returnGames();
        assertTrue(returnMap.isEmpty());
    }}
