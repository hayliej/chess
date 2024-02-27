package dataAccess;

import java.util.HashMap;
import java.util.Map;

public class MemGameDAO implements GameDAO {
    //make a map of games should it be <String, GSON>??
    Map<String,String> games = new HashMap<>();

    public void clear() {
        Map<String,String> games = new HashMap<>();
    }

}
