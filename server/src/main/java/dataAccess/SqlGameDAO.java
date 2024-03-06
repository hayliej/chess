package dataAccess;

import requests.GameData;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class SqlGameDAO implements GameDAO{
    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE games";
        try (PreparedStatement state = DatabaseManager.getConnection().prepareStatement(statement)) {
            state.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
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
