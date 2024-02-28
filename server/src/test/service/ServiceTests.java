package service;
import chess.*;
import dataAccess.*;
import org.eclipse.jetty.server.Authentication;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
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
        Map<String, UserData> umap = uService.getMap();
        assertNotNull(umap);
    }


    //AUTH SERVICE
    //login positive
    //login negative
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
