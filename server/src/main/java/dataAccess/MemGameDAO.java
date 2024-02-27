package dataAccess;

import requests.AuthData;

import java.util.HashMap;
import java.util.Map;

public class MemGameDAO implements GameDAO {
    //make a map of games should it be <String, GSON>??
    private static Map<String,String> games = new HashMap<>();

    public void clear() {
        Map<String,String> games = new HashMap<>();
    }

//    @Override
//    public void addGame(GameData gamedata) {
//        games.put(gamedata);
//    }

    //return map
    public Map returnGames(){
        return games;
    }

}
