package service;
import dataAccess.*;
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

    //getGame
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

    //clearDB
    public void clear(){
        GDataAccess.clear();
        ADataAccess.clear();
        UDataAccess.clear();
    }

    //^NEED TO THROW EXCEPTIONS?
}

