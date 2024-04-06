package server;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import spark.Spark;
import webSocketMessages.userCommands.*;

import java.util.ArrayList;
import java.util.HashMap;

@WebSocket
public class WSServer {
    HashMap<String, Session> sessions = new HashMap<>();
    HashMap<Integer, ArrayList<String>> games = new HashMap<>();


    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        //user game commands
        UserGameCommand msg = new Gson().fromJson(message, UserGameCommand.class);
        if (msg.getCommandType().equals(UserGameCommand.CommandType.JOIN_OBSERVER)){
            JoinObserver joinO = new Gson().fromJson(message, JoinObserver.class);
            sessions.put(joinO.getAuthString(),session);
            games.put(joinO.getID(), new ArrayList<>()); //fix adding auth

            //function pass in joinO
                //send loadgame object to the observer that joined
                //for auth in games loop through all except player that joined and send join notification
        } else if (msg.getCommandType().equals(UserGameCommand.CommandType.JOIN_PLAYER)) {
            //do stuff
        } else if (msg.getCommandType().equals(UserGameCommand.CommandType.MAKE_MOVE)) {
            //do stuff
        } else if (msg.getCommandType().equals(UserGameCommand.CommandType.LEAVE)) {
            //do stuff
        } else if (msg.getCommandType().equals(UserGameCommand.CommandType.RESIGN)) {
            //do stuff
        }
        //each of these test the type using ugc
        //then they turn that into the correct type
        //add their data to the appropriate map
        //call function that I'll make outside this method that will send the right thing (LG, E, N) to WSFacade
        //function will send correct messages for each player depending on what's specified for each action
            //deal with facade stuff later, (WebSocketTests just tests this file's functionality, use it!)
            //deal with repl later too

        System.out.printf("Received: %s", message);
        session.getRemote().sendString("WebSocket response: " + message); //this sends notifications
    }

    public void joinObserver(){
        //do stuff
    }

    public void joinPlayer(){
        //do stuff
    }

    public void makeMove(){
        //do stuff
    }

    public void leave(){
        //do stuff
    }

    public void resign(){
        //do stuff
    }
}