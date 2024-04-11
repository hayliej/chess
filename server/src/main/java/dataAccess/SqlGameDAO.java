package dataAccess;

import chess.ChessGame;
import com.google.gson.Gson;
import requests.GameData;
import requests.UserData;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SqlGameDAO implements GameDAO{
    private static final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS games (
              `gameID` INT NOT NULL,
              `whiteUsername` varchar(256),
              `blackUsername` varchar(256),
              `gameName` varchar(256) NOT NULL,
              `game` TEXT,
              PRIMARY KEY (`gameID`)
            );
            """
    };
    private static void configureDatabase() throws DataAccessException {
        SqlUserDAO.configureDatabase(createStatements);
    }

    public SqlGameDAO() throws DataAccessException {
        configureDatabase();
    }

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
        var statement = "INSERT INTO games (gameID, whiteUsername, blackUsername, gameName, game) VALUES (?, ?, ?, ?, ?)";
        try (PreparedStatement state = DatabaseManager.getConnection().prepareStatement(statement)) {
            state.setInt(1, gamedata.gameID());
            state.setString(2,gamedata.whiteUsername());
            state.setString(3, gamedata.blackUsername());
            state.setString(4, gamedata.gameName());
            state.setString(5, new Gson().toJson(gamedata.game()));
            state.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    public void updateGames(Integer gameID, String color, String username) throws DataAccessException {
        var whiteUpdate = "UPDATE games SET whiteUsername=? WHERE gameID=?";
        var blackUpdate = "UPDATE games SET blackUsername=? WHERE gameID=?";
        if (color.equals("white")) {
            try (PreparedStatement state = DatabaseManager.getConnection().prepareStatement(whiteUpdate)) {
                state.setString(1,username);
                state.setInt(2, gameID);
                state.executeUpdate();
            }catch (SQLException e) {
                throw new DataAccessException(e.getMessage());
            }
        } else if (color.equals("black")) {
            try (PreparedStatement state = DatabaseManager.getConnection().prepareStatement(blackUpdate)) {
                state.setString(1,username);
                state.setInt(2, gameID);
                state.executeUpdate();
            }catch (SQLException e) {
                throw new DataAccessException(e.getMessage());
            }
        }
    }

    public void updateWholeGame(GameData gameData) throws DataAccessException {
        var update = "UPDATE games SET game=? WHERE gameID=?";
        try (PreparedStatement state = DatabaseManager.getConnection().prepareStatement(update)) {
            String game = new Gson().toJson(gameData.game());
            state.setString(1,game);
            state.setInt(2, gameData.gameID());
            state.executeUpdate();
        }catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public int getSize() throws DataAccessException {
        return  returnGames().size();
    }

    @Override
    public Map<Integer, GameData> returnGames() throws DataAccessException {
        Map<Integer, GameData> gameMap = new HashMap<Integer, GameData>();
        var statement = "SELECT * FROM games";
        try (PreparedStatement state = DatabaseManager.getConnection().prepareStatement(statement)) {
            var rs = state.executeQuery();
            while (rs.next()) {
                Integer id = rs.getInt("gameID");
                String wu = rs.getString("whiteUsername");
                String bu = rs.getString("blackUsername");
                String gn = rs.getString("gameName");
                ChessGame game = new Gson().fromJson(rs.getString("game"), ChessGame.class);
                GameData gameAdd = new GameData(id, wu, bu, gn, game);
                gameMap.put(id, gameAdd);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return gameMap;
    }
}
