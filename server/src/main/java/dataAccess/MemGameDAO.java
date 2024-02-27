package dataAccess;

import requests.AuthData;
import requests.GameData;

import java.util.HashMap;
import java.util.Map;

public class MemGameDAO implements GameDAO {
    //make a map of games should it be <String, GSON>??
    private static Map<Integer, GameData> games = new HashMap<>();

    public void clear() {
        Map<Integer,GameData> games = new HashMap<>();
    }

    @Override
    public int getSize()  {
        return games.size();
    }

    @Override
    public void addGame(int id, GameData gamedata) {
        games.put(gamedata.gameID(), gamedata);
    }

    //return map
    public Map returnGames(){
        return games;
    }

}
