package dataAccess;

import requests.GameData;

import java.util.Map;

public interface GameDAO {
    //make a map of games should it be <String, GSON>??

    //clear
    public void clear() throws DataAccessException;

    //createGame
    public void addGame(Integer id, GameData gamedata) throws DataAccessException;

    //getGame

    //listGames

    //updateGame


    //length map
    int getSize() throws DataAccessException;

    //return map
    Map<Integer, GameData> returnGames() throws DataAccessException;

    void updateGames(Integer gameID, String color, String username) throws DataAccessException;
}
