package service;
import dataAccess.*;
import org.eclipse.jetty.util.log.Log;
import requests.*;

import java.util.ArrayList;
import java.util.List;

public class GameService {
    private static UserDAO UDataAccess = new MemUserDAO();
    private static AuthDAO ADataAccess = new MemAuthDAO();
    private static GameDAO GDataAccess = new MemGameDAO();


    public GameService() {
        this.UDataAccess = UDataAccess;
        this.ADataAccess = ADataAccess;
        this.GDataAccess = GDataAccess;
    }

    //listGames
    public ListGamesResult listGames(String auth) throws DataAccessException {
        if (!(ADataAccess.returnAuths().containsKey(auth))){
            return new ListGamesResult("Error: unauthorized", null);
        }
        List games = new ArrayList();
        for (Integer i=1;i<=GDataAccess.getSize(); i++){
            games.add(GDataAccess.returnGames().get(i));
        }
        return new ListGamesResult(null, games);
    }

    //createGame
    public CreateGameResult createGame(AuthNewGame newAuth) throws DataAccessException {
        if (!(ADataAccess.returnAuths().containsKey(newAuth.authToken()))){
            return new CreateGameResult("Error: unauthorized", null);
        } else if (newAuth.authToken()==null || newAuth.gameName()==null){
            return new CreateGameResult("Error: bad request", null);
        }
        Integer id = GDataAccess.getSize()+1;
        GDataAccess.addGame(id, new GameData(id, null, null, newAuth.gameName()));
        return new CreateGameResult(null, id);
    }

    //join game
    public LogoutResult joinGame(AuthJoinGame join) throws DataAccessException {
        if (!(ADataAccess.returnAuths().containsKey(join.authToken()))){
            return new LogoutResult("Error: unauthorized");
        } else if (join.authToken()==null || join.gameID()==null){
            return new LogoutResult("Error: bad request");
        }
        if ((GDataAccess.returnGames().get(join.gameID())==null)){
            return new LogoutResult("Error: bad request");
        }
        if (join.playerColor()==null){
            return new LogoutResult(null);
        }
        if (join.playerColor().equals("WHITE")) {
            if (!(GDataAccess.returnGames().get(join.gameID()).whiteUsername()==null)){
                return new LogoutResult("Error: already taken");
            }
            GameData replace = new GameData(join.gameID(), ADataAccess.getVal(join.authToken()),
                    null, GDataAccess.returnGames().get(join.gameID()).gameName());
            GDataAccess.returnGames().replace(join.gameID(), replace);
        }
        if (join.playerColor().equals("BLACK")) {
            if (!(GDataAccess.returnGames().get(join.gameID()).blackUsername()==null)){
                return new LogoutResult("Error: already taken");
            }
            GameData replace = new GameData(join.gameID(), null, ADataAccess.getVal(join.authToken()),
                    GDataAccess.returnGames().get(join.gameID()).gameName());
            GDataAccess.returnGames().replace(join.gameID(), replace);
        }
        return new LogoutResult(null);
    }

    //clearDB
    public void clear(){
        GDataAccess.clear();
        ADataAccess.clear();
        UDataAccess.clear();
    }
}

