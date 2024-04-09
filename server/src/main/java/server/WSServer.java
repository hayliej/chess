package server;
import chess.ChessGame;
import chess.ChessMove;
import chess.ChessPiece;
import chess.ChessPosition;
import com.google.gson.Gson;
import dataAccess.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import service.GameService;
import spark.Spark;
import webSocketMessages.serverMessages.LoadGame;
import webSocketMessages.serverMessages.Notification;
import webSocketMessages.serverMessages.ServerMessage;
import webSocketMessages.userCommands.*;
import requests.GameData;
import webSocketMessages.serverMessages.Error;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;

@WebSocket
public class WSServer {
    HashMap<String, Session> sessions = new HashMap<>();
    HashMap<Integer, ArrayList<String>> games = new HashMap<>();
    ArrayList<String> gamePeople = new ArrayList<>();
    private static AuthDAO aDataAccess;

    static {
        try {
            aDataAccess = new SqlAuthDAO();
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


    @OnWebSocketMessage
    public void onMessage(Session session, String message) throws Exception {
        //user game commands
        UserGameCommand msg = new Gson().fromJson(message, UserGameCommand.class);
        //for each of these, test the type using ugc
            //then they turn that into the correct type
            //add their data to the appropriate map
        if (msg.getCommandType().equals(UserGameCommand.CommandType.JOIN_OBSERVER)){
            JoinObserver joinO = new Gson().fromJson(message, JoinObserver.class);
            sessions.put(joinO.getAuthString(),session);
            if (games.containsKey(joinO.getID())){
                games.get(joinO.getID()).add(joinO.getAuthString());
            } else {
                games.put(joinO.getID(), gamePeople);
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
                games.put(joinP.getID(), gamePeople);
                games.get(joinP.getID()).add(joinP.getAuthString());
            }

            joinPlayer(joinP, session);
        }
        else if (msg.getCommandType().equals(UserGameCommand.CommandType.MAKE_MOVE)) {
            MakeMove makeM = new Gson().fromJson(message, MakeMove.class);
            //do stuff
            makeMove(makeM, session);
        }
        else if (msg.getCommandType().equals(UserGameCommand.CommandType.LEAVE)) {
            Leave leave = new Gson().fromJson(message, Leave.class);
            //do stuff
            leave(leave, session);
        }
        else if (msg.getCommandType().equals(UserGameCommand.CommandType.RESIGN)) {
            Resign resign = new Gson().fromJson(message, Resign.class);
            //do stuff
            resign(resign, session);
        }

        //call function that I'll make outside this method that will send the right thing (LG, E, N) to WSFacade
        //function will send correct messages for each player depending on what's specified for each action
            //deal with facade stuff later, (WebSocketTests just tests this file's functionality, use it!)
            //deal with repl later too

//        System.out.printf("Received: %s", message);
    }


    public void joinObserver(JoinObserver jo, Session session) throws IOException, DataAccessException {
        if (!aDataAccess.returnAuths().containsKey(jo.getAuthString())){
            Error er = new Error("Invalid Auth Token");
            String error = new Gson().toJson(er);
            session.getRemote().sendString(error);
        }
        else if (!gDataAccess.returnGames().containsKey(jo.getID())){
            Error er = new Error("Game Does Not Exist");
            String error = new Gson().toJson(er);
            session.getRemote().sendString(error);
        }
        else {

            //send LoadGame to root client
            GameData game = gDataAccess.returnGames().get(jo.getID());
            LoadGame lgame = new LoadGame(game.game());
            String lg = new Gson().toJson(lgame);
            session.getRemote().sendString(lg);

            //send notification to others
            ArrayList<String> people = games.get(jo.getID());
            String user = getUsername(jo.getAuthString());
            for (String person : people) {
                if (!person.equals(jo.getAuthString())) {
                    String sm = new Gson().toJson(new Notification(user + " has joined as an observer"));
                    sessions.get(person).getRemote().sendString(sm);
                }
            }
        }
    }

    public void joinPlayer(JoinPlayer jp, Session session) throws IOException, DataAccessException {
        String username  = String.valueOf(aDataAccess.returnAuths().get(jp.getAuthString()));
        String whitePlayer = gDataAccess.returnGames().get(jp.getID()).whiteUsername();
        String blackPlayer = gDataAccess.returnGames().get(jp.getID()).blackUsername();

        if (!aDataAccess.returnAuths().containsKey(jp.getAuthString())){
            Error er = new Error("Invalid Auth Token");
            String error = new Gson().toJson(er);
            session.getRemote().sendString(error);
        }
        else if (!gDataAccess.returnGames().containsKey(jp.getID())){
            Error er = new Error("Game Does Not Exist");
            String error = new Gson().toJson(er);
            session.getRemote().sendString(error);
        }
        else if (jp.getColor().equals(ChessGame.TeamColor.WHITE)){
            if (whitePlayer==null){
                Error er = new Error("No white player");
                String error = new Gson().toJson(er);
                session.getRemote().sendString(error);
            } else if (!whitePlayer.equals(username)){
                Error er = new Error("Incorrect player color");
                String error = new Gson().toJson(er);
                session.getRemote().sendString(error);
            }
        }
        else if (jp.getColor().equals(ChessGame.TeamColor.BLACK)){
            if (blackPlayer==null){
                Error er = new Error("No black player");
                String error = new Gson().toJson(er);
                session.getRemote().sendString(error);
            } else if (!blackPlayer.equals(username)){
                Error er = new Error("Incorrect player color");
                String error = new Gson().toJson(er);
                session.getRemote().sendString(error);
            }
        }
        else {
            if (jp.getColor().equals(ChessGame.TeamColor.WHITE)){
                gDataAccess.updateGames(jp.getID(), "white", getUsername(jp.getAuthString()));
            } else if (jp.getColor().equals(ChessGame.TeamColor.BLACK)){
                gDataAccess.updateGames(jp.getID(), "black", getUsername(jp.getAuthString()));
            }

            //send LoadGame to root client
            GameData game = gDataAccess.returnGames().get(jp.getID());
            LoadGame lgame = new LoadGame(game.game());
            String lg = new Gson().toJson(lgame);
            session.getRemote().sendString(lg);

            //send notification to all others
            ArrayList<String> people = games.get(jp.getID());
            String user = getUsername(jp.getAuthString());
            for (String person : people) {
                if (!person.equals(jp.getAuthString())) {
                    String sm = new Gson().toJson(new Notification(user + " has joined as " + jp.getColor()));
                    sessions.get(person).getRemote().sendString(sm);
                }
            }
        }
    }

    public void makeMove(MakeMove move, Session session) throws IOException, DataAccessException {
        String username  = String.valueOf(aDataAccess.returnAuths().get(move.getAuthString()));
        ChessGame game = gDataAccess.returnGames().get(move.getID()).game();
        String whitePlayer = gDataAccess.returnGames().get(move.getID()).whiteUsername();
        String blackPlayer = gDataAccess.returnGames().get(move.getID()).blackUsername();
        ChessGame.TeamColor color;
        if (whitePlayer.equals(username)){
            color = ChessGame.TeamColor.WHITE;
        } else {
            color = ChessGame.TeamColor.BLACK;
        }

        ChessPosition start = move.getMove().getStartPosition();
        Collection<ChessMove> validMoves = ChessGame.validMoves(start);

        //check correct turn
        if (!(game.getTeamTurn().equals(ChessGame.TeamColor.WHITE) && whitePlayer.equals(username))){
            Error er = new Error("Incorrect team turn, black's move");
            String error = new Gson().toJson(er);
            session.getRemote().sendString(error);
        } else if (!(game.getTeamTurn().equals(ChessGame.TeamColor.BLACK) && blackPlayer.equals(username))){
            Error er = new Error("Incorrect team turn, white's move");
            String error = new Gson().toJson(er);
            session.getRemote().sendString(error);
        }
        //check right team
        else if (!game.getBoard().getPiece(start).getTeamColor().equals(color)) {
            Error er = new Error("Incorrect team piece, move a " + color + " piece");
            String error = new Gson().toJson(er);
            session.getRemote().sendString(error);
        }
        //check valid moves
        else if (!validMoves.contains(move.getMove())){
            Error er = new Error("Invalid move");
            String error = new Gson().toJson(er);
            session.getRemote().sendString(error);
        }


        //validate move
        //ChessPiece.PieceType type = move.getMove().getStartPosition();
        //update game to represent move
        //update game in DB

        ArrayList<String> people = games.get(move.getID());
        String user = getUsername(move.getAuthString());
        for (String person : people){
            //send LoadGame to everyone
            GameData gameD = gDataAccess.returnGames().get(move.getID());
            LoadGame lgame = new LoadGame(gameD.game());
            String lg = new Gson().toJson(lgame);
            session.getRemote().sendString(lg);

            //send notification to others
            if (!person.equals(move.getAuthString())) {
                String sm = new Gson().toJson(new Notification(user + " has moved: " + move.getMove().toString()));
                sessions.get(person).getRemote().sendString(sm);
            }
        }
        //notify if anyone in check
        //or check mate
    }

    public void leave(Leave leave, Session session) throws IOException, DataAccessException {
        //remove root client
        //update game in DB

        //send notification to everyone
        ArrayList<String> people = games.get(leave.getID());
        String user = getUsername(leave.getAuthString());
        for (String person : people){
            if (!person.equals(leave.getAuthString())) {
                String sm = new Gson().toJson(new Notification(user + " has left the game"));
                sessions.get(person).getRemote().sendString(sm);
            }
        }
    }

    public void resign(Resign resign, Session session) throws IOException, DataAccessException {
        //mark game as over -- no more moves can be made
        //update game in DB

        //send ze notification
        ArrayList<String> people = games.get(resign.getID());
        String user = getUsername(resign.getAuthString());
        for (String person : people){
            if (!person.equals(resign.getAuthString())) {
                String sm = new Gson().toJson(new Notification(user + " has resigned"));
                sessions.get(person).getRemote().sendString(sm);
            }
        }
    }

    public String getUsername(String auth) throws DataAccessException {
        return aDataAccess.getVal(auth);
    }
}