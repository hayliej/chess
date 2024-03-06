package dataAccess;

import requests.GameData;

import java.util.Map;

public class SqlGameDAO implements GameDAO{
    @Override
    public void clear() {

    }

    @Override
    public void addGame(Integer id, GameData gamedata) throws DataAccessException {

    }

    @Override
    public int getSize() {
        return 0;
    }

    @Override
    public Map<Integer, GameData> returnGames() {
        return null;
    }
}
