package dataAccess;

import java.util.Map;

public interface GameDAO {
    //make a map of games should it be <String, GSON>??

    //clear
    public void clear();

    //createGame
    //public void addGame(GameData gamedata) throws DataAccessException;

    //getGame

    //listGames

    //updateGame

    //return map
    Map<Object, Object> returnGames();
}
