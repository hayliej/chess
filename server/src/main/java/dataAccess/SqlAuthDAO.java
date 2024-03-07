package dataAccess;

import requests.AuthData;
import requests.UserData;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

public class SqlAuthDAO implements AuthDAO{
    private static final String[] createStatements = {
            """
            CREATE TABLE IF NOT EXISTS  auths (
              `authToken` varchar(256) NOT NULL,
              `username` varchar(256) NOT NULL,
              PRIMARY KEY (`authToken`)
            );
            """
    };
    private static void configureDatabase() throws DataAccessException {
        DatabaseManager.createDatabase();
        try (var conn = DatabaseManager.getConnection()) {
            for (var statement : createStatements) {
                try (var preparedStatement = conn.prepareStatement(statement)) {
                    preparedStatement.executeUpdate();
                }
            }
        } catch (SQLException ex) {
            throw new DataAccessException(String.format("Unable to configure database: %s", ex.getMessage()));
        }
    }

    public SqlAuthDAO() throws DataAccessException {
        configureDatabase();
    }

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
    public void removeAuth(String authToken) throws DataAccessException {
        var statement = "DELETE FROM auths WHERE authToken=?";
        try (PreparedStatement state = DatabaseManager.getConnection().prepareStatement(statement)) {
            state.setString(1, authToken);
            state.executeUpdate();
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

    @Override
    public Map<Object, Object> returnAuths() throws DataAccessException {
        Map<Object, Object> authMap = new HashMap<Object, Object>();
        var statement = "SELECT * FROM auths";
        try (PreparedStatement state = DatabaseManager.getConnection().prepareStatement(statement)) {
            var rs = state.executeQuery();
            while (rs.next()) {
                String at = rs.getString("authToken");
                String un = rs.getString("username");
                AuthData authAdd = new AuthData(at, un);
                authMap.put(un, authAdd);
            }
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
        return authMap;
    }

    @Override
    public String getVal(String auth) throws DataAccessException {
        var statement = "SELECT authToken FROM auths WHERE authToken=?";
        try (PreparedStatement state = DatabaseManager.getConnection().prepareStatement(statement)) {
            state.setString(1, auth);
            var rs = state.executeQuery();
            String at = rs.getString("authToken");
            return at;
        } catch (SQLException e) {
            throw new DataAccessException(e.getMessage());
        }
    }

}
