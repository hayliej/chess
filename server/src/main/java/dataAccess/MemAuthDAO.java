package dataAccess;

import java.util.HashMap;
import java.util.Map;

public class MemAuthDAO implements AuthDAO {
    //make map of auths
    public static Map<String,String> auths = new HashMap<>();

    //clear
    public void clear() {
        Map<String,String> auths = new HashMap<>();
    }

    //return map
    public Map returnAuths(){
        return auths;
    }

}
