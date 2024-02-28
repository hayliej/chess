package service;
import dataAccess.*;
import org.eclipse.jetty.util.log.Log;
import requests.*;

public class GameService {
    private static UserDAO UDataAccess = new MemUserDAO();
    private static AuthDAO ADataAccess = new MemAuthDAO();
    private static GameDAO GDataAccess = new MemGameDAO();


    public GameService() {
        this.UDataAccess = UDataAccess;
        this.ADataAccess = ADataAccess;
        this.GDataAccess = GDataAccess;
    }

    //THINGS SERVICE NEEDS TO DO:
    //get/listGames
    public ListGamesResult listGames(AuthToken auth) throws DataAccessException {
        if (!(ADataAccess.returnAuths().containsKey(auth.authToken()))){
            return new ListGamesResult("Error: unauthorized", null);
        }
        return new ListGamesResult(null, GDataAccess.returnGames());
    }

    //createGame
    public CreateGameResult createGame(AuthNewGame newAuth) throws DataAccessException {
        if (!(ADataAccess.returnAuths().containsKey(newAuth.authToken()))){
            return new CreateGameResult("Error: unauthorized", null);
        } else if (newAuth.authToken()==null || newAuth.gameName()==null){
            return new CreateGameResult("Error: bad request", null);
        }
        String id = Integer.toString(GDataAccess.getSize()+1);
        GDataAccess.addGame(id, new GameData(id, null, null, newAuth.gameName()));
        return new CreateGameResult(null, id);
    }

    //join game
    public LogoutResult joinGame(AuthJoinGame join) throws DataAccessException {
        if (!(ADataAccess.returnAuths().containsKey(join.authToken()))){
            return new LogoutResult("Error: unauthorized");
        } else if (join.authToken()==null || join.gameID()==null || join.color()==null){
            return new LogoutResult("Error: bad request");
        }
        if (join.color().equals("WHITE")) {
            if (!(GDataAccess.returnGames().get(join.gameID()).whiteUsername()==null)){
                return new LogoutResult("Error: already taken");
            }
        }
        if (join.color().equals("BLACK")) {
            if (!(GDataAccess.returnGames().get(join.gameID()).blackUsername()==null)){
                return new LogoutResult("Error: already taken");
            }
        }
        return new LogoutResult(null);
    }

    //clearDB
    public void clear(){
        GDataAccess.clear();
        ADataAccess.clear();
        UDataAccess.clear();
    }

    //^NEED TO THROW EXCEPTIONS?
}

