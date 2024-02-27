package service;
import dataAccess.*;

public class GameService {
    //what do we do with this??
    private final MemGameDAO dataAccess;

    public GameService(MemGameDAO dataAc) {
        this.dataAccess = dataAc;
    }

    //THINGS SERVICE NEEDS TO DO:
    //get/listGames
    //getGame
    //createGame
    //clearDB
    public void clear(){ MemGameDAO.clear(); }

    //^NEED TO THROW EXCEPTIONS?
}

