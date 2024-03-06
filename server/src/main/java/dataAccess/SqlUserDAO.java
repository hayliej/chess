package dataAccess;

import requests.UserData;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class SqlUserDAO implements UserDAO {
    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE users";
        try (PreparedStatement state = DatabaseManager.getConnection().prepareStatement(statement)) {
            state.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void addUser(UserData userdata) throws DataAccessException {
        var statement = "INSERT INTO users (username, password, email) VALUES (?, ?, ?)";
        try (PreparedStatement state = DatabaseManager.getConnection().prepareStatement(statement)) {
            state.setString(1, userdata.username());
            state.setString(2,userdata.password());
            state.setString(3, userdata.email());
            state.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public Map<String, UserData> returnUsers() {
        return null;
    }
}
