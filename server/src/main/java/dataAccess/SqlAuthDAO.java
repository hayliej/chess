package dataAccess;

import requests.AuthData;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.Map;

public class SqlAuthDAO implements AuthDAO{


    @Override
    public void clear() throws DataAccessException {
        var statement = "TRUNCATE auths";
        try (PreparedStatement state = DatabaseManager.getConnection().prepareStatement(statement)) {
            state.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void addAuth(AuthData authdata) throws DataAccessException {
        var statement = "INSERT INTO auths (authToken, username) VALUES (?, ?)";
        try (PreparedStatement state = DatabaseManager.getConnection().prepareStatement(statement)) {
            state.setString(1, authdata.authToken());
            state.setString(2, authdata.username());
            state.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public void removeAuth(String authToken) {

    }

    @Override
    public Map<Object, Object> returnAuths() {
        return null;
    }

    @Override
    public String getVal(String auth) {
        return null;
    }


}
