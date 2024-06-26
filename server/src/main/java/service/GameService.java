package service;
import chess.ChessGame;
import dataAccess.*;
import requests.*;
import results.CreateGameResult;
import results.ListGamesResult;
import results.LogoutResult;

import java.util.ArrayList;
import java.util.List;

public class GameService {
    private static UserDAO uDataAccessGame;

    static {
        try {
            uDataAccessGame = new SqlUserDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static AuthDAO aDataAccessGame;

    static {
        try {
            aDataAccessGame = new SqlAuthDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }

    private static GameDAO gDataAccess;

    static {
        try {
            gDataAccess = new SqlGameDAO();
        } catch (DataAccessException e) {
            throw new RuntimeException(e);
        }
    }


    //listGames
    public ListGamesResult listGames(String auth) throws DataAccessException {
        if (!(aDataAccessGame.returnAuths().containsKey(auth))){
            return new ListGamesResult("Error: unauthorized", null);
        }
        List games = new ArrayList();
        for (Integer i = 1; i<= gDataAccess.getSize(); i++){
            games.add(gDataAccess.returnGames().get(i));
        }
        return new ListGamesResult(null, games);
    }

    //createGame
    public CreateGameResult createGame(AuthNewGame newAuth) throws DataAccessException {
        if (!(aDataAccessGame.returnAuths().containsKey(newAuth.authToken()))){
            return new CreateGameResult("Error: unauthorized", null);
        } else if (newAuth.authToken()==null || newAuth.gameName()==null){
            return new CreateGameResult("Error: bad request", null);
        }
        Integer id = gDataAccess.getSize()+1;
        ChessGame b = new ChessGame();
        b.getBoard().resetBoard();
        gDataAccess.addGame(id, new GameData(id, null, null, newAuth.gameName(), b));
        return new CreateGameResult(null, id);
    }

    //join game
    public LogoutResult joinGame(AuthJoinGame join) throws DataAccessException {
        if (!(aDataAccessGame.returnAuths().containsKey(join.authToken()))){
            return new LogoutResult("Error: unauthorized");
        } else if (join.authToken()==null || join.gameID()==null){
            return new LogoutResult("Error: bad request");
        }
        if ((gDataAccess.returnGames().get(join.gameID())==null)){
            return new LogoutResult("Error: bad request");
        }
        if (join.playerColor()==null){
            return new LogoutResult(null);
        }
        if (join.playerColor().equals("WHITE")) {
            if (!(gDataAccess.returnGames().get(join.gameID()).whiteUsername()==null)){
                return new LogoutResult("Error: already taken");
            }
            GameData old = gDataAccess.returnGames().get(join.gameID());
//            GameData replace = new GameData(old.gameID(), ADataAccess.getVal(join.authToken()),
//                    null, GDataAccess.returnGames().get(join.gameID()).gameName());
            GameData newgd = new GameData(old.gameID(), aDataAccessGame.getVal(join.authToken()), old.blackUsername(), old.gameName(), old.game());
            gDataAccess.updateGames(join.gameID(), "white", aDataAccessGame.getVal(join.authToken()));
        }
        if (join.playerColor().equals("BLACK")) {
            if (!(gDataAccess.returnGames().get(join.gameID()).blackUsername()==null)){
                return new LogoutResult("Error: already taken");
            }
            GameData old = gDataAccess.returnGames().get(join.gameID());
//            GameData replace = new GameData(join.gameID(), null, ADataAccess.getVal(join.authToken()),
//                    GDataAccess.returnGames().get(join.gameID()).gameName());
            GameData newgd = new GameData(old.gameID(), old.whiteUsername(), aDataAccessGame.getVal(join.authToken()), old.gameName(), old.game());
            gDataAccess.updateGames(join.gameID(), "black", aDataAccessGame.getVal(join.authToken()));
        }
        return new LogoutResult(null);
    }

    //clearDB
    public void clear() throws DataAccessException {
        gDataAccess.clear();
        aDataAccessGame.clear();
        uDataAccessGame.clear();
    }
}