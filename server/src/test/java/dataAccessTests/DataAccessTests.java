package dataAccessTests;

import dataAccess.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import requests.AuthData;
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

        String authToken = UUID.randomUUID().toString();
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
        Map<String, UserData> umap = uDAO.returnUsers();
        assertNotNull(umap);
    }

    //addUser negative
    @Test
    public void addUserNegative() throws DataAccessException {
        UserData newUser = new UserData(null, "password", "email@byu.edu");
        boolean thrown = false;
        try {
            uDAO.addUser(newUser);
        } catch (DataAccessException e) {
            thrown = true;
        }
        assertTrue(thrown);
    }

    //returnUsers positive

    //returnUsers negative


    //AUTH
    //addAuth positive
    //addAuth negative

    //removeAuth positive
    //removeAuth negative

    //returnAuths positive
    //returnAuths negative

    //getVal positive
    //getVal negative


    //GAME
    //addGame positive
    //addGame negative

    //updateGames positive
    //updateGames negative

    //getSize positive
    //getSize negative

    //returnGames positive
    //returnGames negative

}
