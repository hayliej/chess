package dataAccess;

import requests.GameData;

import java.util.Map;

public interface GameDAO {
    //make a map of games should it be <String, GSON>??

    //clear
    public void clear();

    //createGame
    public void addGame(String id, GameData gamedata) throws DataAccessException;

    //getGame

    //listGames

    //updateGame


    //length map
    int getSize();

    //return map
    Map<Integer, GameData> returnGames();
}
