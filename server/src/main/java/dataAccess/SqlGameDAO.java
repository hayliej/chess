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
        var statement = "INSERT INTO games (gameID, whiteUsername, blackUsername, game) VALUES (?, ?, ?, ?)";
        try (PreparedStatement state = DatabaseManager.getConnection().prepareStatement(statement)) {
            state.setInt(1, gamedata.gameID());
            state.setString(2,gamedata.whiteUsername());
            state.setString(3, gamedata.blackUsername());
            state.setString(3, gamedata.gameName()); //JSON?????
            state.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
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