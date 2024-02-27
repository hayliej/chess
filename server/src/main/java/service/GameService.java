package service;
import dataAccess.*;
import requests.AuthNewGame;
import requests.CreateGameResult;
import requests.GameData;
import requests.LogoutResult;

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
    //getGame
    //createGame
    public CreateGameResult createGame(AuthNewGame newAuth) throws DataAccessException {
        if (!(ADataAccess.returnAuths().containsKey(newAuth.authToken()))){
            return new CreateGameResult("Error: unauthorized", 0);
        } else if (newAuth.authToken()==null || newAuth.gameName()==null){
            return new CreateGameResult("Error: bad request", 0);
        }
        int id = GDataAccess.getSize()+1;
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

