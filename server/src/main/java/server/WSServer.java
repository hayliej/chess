package server;
import com.google.gson.Gson;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import spark.Spark;
import webSocketMessages.userCommands.*;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebSocket
public class WSServer {
    HashMap<String, Session> sessions = new HashMap<>();
    HashMap<Integer, ArrayList<String>> games = new HashMap<>();
    ArrayList<String> gamePeople = new ArrayList<>();



    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        //user game commands
        UserGameCommand msg = new Gson().fromJson(message, UserGameCommand.class);
        if (msg.getCommandType().equals(UserGameCommand.CommandType.JOIN_OBSERVER)){
            JoinObserver joinO = new Gson().fromJson(message, JoinObserver.class);
            sessions.put(joinO.getAuthString(),session);
            if (games.containsKey(joinO.getID())){
                games.get(joinO.getID()).add(joinO.getAuthString());
            } else {
                games.put(joinO.getID(), new ArrayList<>());
                games.get(joinO.getID()).add(joinO.getAuthString());
            }

            joinObserver(joinO, session);
        }
        else if (msg.getCommandType().equals(UserGameCommand.CommandType.JOIN_PLAYER)) {
            JoinPlayer joinP = new Gson().fromJson(message, JoinPlayer.class);
            sessions.put(joinP.getAuthString(), session);
            if (games.containsKey(joinP.getID())){
                games.get(joinP.getID()).add(joinP.getAuthString());
            } else {
                games.put(joinP.getID(), new ArrayList<>());
                games.get(joinP.getID()).add(joinP.getAuthString());
            }

            joinPlayer(joinP, session);
        }
        else if (msg.getCommandType().equals(UserGameCommand.CommandType.MAKE_MOVE)) {
            MakeMove makeM = new Gson().fromJson(message, MakeMove.class);
            //do stuff
        }
        else if (msg.getCommandType().equals(UserGameCommand.CommandType.LEAVE)) {
            Leave leave = new Gson().fromJson(message, Leave.class);
            //do stuff
        }
        else if (msg.getCommandType().equals(UserGameCommand.CommandType.RESIGN)) {
            Resign resign = new Gson().fromJson(message, Resign.class);
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

    //send loadgame object to the observer that joined
    //for auth in games loop through all except observer that joined and send join notification
    public void joinObserver(JoinObserver jo, Session session) throws IOException {
        //send LoadGame to root client
        ArrayList<String> people = games.get(jo.getID());
        for (String person : people){
            if (!person.equals(jo.getAuthString())) {
                session.getRemote().sendString("WebSocket response: " + "username" + " has joined as an observer");
                //GET ACTUAL USERNAME FROM DB^^^
            }
        }
    }

    public void joinPlayer(JoinPlayer jp, Session session) throws IOException {
//send LoadGame to root client
        ArrayList<String> people = games.get(jp.getID());
        for (String person : people){
            if (!person.equals(jp.getAuthString())) {
                session.getRemote().sendString("WebSocket response: " + "username" + " has joined as " + jp.getColor());
                //GET ACTUAL USERNAME FROM DB^^^
            }
        }    }

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