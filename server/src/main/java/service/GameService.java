package service;
import dataAccess.*;

public class GameService {
    //what do we do with this??
    private MemGameDAO dataAccess = new MemGameDAO();

    public GameService() {
        this.dataAccess = dataAccess;
    }

    //THINGS SERVICE NEEDS TO DO:
    //get/listGames
    //getGame
    //createGame
    //clearDB
    public void clear(){ dataAccess.clear(); }

    //^NEED TO THROW EXCEPTIONS?
}

