package server;
import chess.*;
import com.google.gson.Gson;
import dataAccess.*;
import org.eclipse.jetty.websocket.api.annotations.*;
import org.eclipse.jetty.websocket.api.*;
import requests.AuthData;
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

//        System.out.printf("Received: %s", message);
    }


    public void joinObserver(JoinObserver jo, Session session) throws IOException, DataAccessException {
        if (!aDataAccess.returnAuths().containsKey(jo.getAuthString())){
            Error er = new Error("Error: Invalid Auth Token");
            String error = new Gson().toJson(er);
            session.getRemote().sendString(error);
            return;
        }
        else if (!gDataAccess.returnGames().containsKey(jo.getID())){
            Error er = new Error("Error: Game Does Not Exist");
            String error = new Gson().toJson(er);
            session.getRemote().sendString(error);
            return;
        }

            //send LoadGame to root client
            GameData game = gDataAccess.returnGames().get(jo.getID());
            LoadGame lgame = new LoadGame(game.game(), null);
            String lg = new Gson().toJson(lgame);
            session.getRemote().sendString(lg);

            //send notification to others
            ArrayList<String> people = games.get(jo.getID());
            String user = getUsername(jo.getAuthString());
            for (String person : people) {
                if (!person.equals(jo.getAuthString())) {
                    if (sessions.containsKey(person)) {
                        if (sessions.get(person).isOpen()) {
                            String sm = new Gson().toJson(new Notification(user + " has joined as an observer"));
                            sessions.get(person).getRemote().sendString(sm);
                        } else {
                            sessions.remove(person);
                        }
                    }
                }
            }

    }

    public void joinPlayer(JoinPlayer jp, Session session) throws IOException, DataAccessException {

        String whitePlayer;
        String blackPlayer;
        if (!aDataAccess.returnAuths().containsKey(jp.getAuthString())){
            Error er = new Error("Error: Invalid Auth Token");
            String error = new Gson().toJson(er);
            session.getRemote().sendString(error);
            return;
        }

        if (!gDataAccess.returnGames().containsKey(jp.getID())) {
            Error er = new Error("Error: Game Does Not Exist");
            String error = new Gson().toJson(er);
            session.getRemote().sendString(error);
            return;
        } else {
            whitePlayer = gDataAccess.returnGames().get(jp.getID()).whiteUsername();
            blackPlayer = gDataAccess.returnGames().get(jp.getID()).blackUsername();
        }



        AuthData user  = (AuthData) aDataAccess.returnAuths().get(jp.getAuthString());
        String username = user.username();
         if (jp.getColor().equals(ChessGame.TeamColor.WHITE)){
            if (whitePlayer==null){
                Error er = new Error("Error: No white player");
                String error = new Gson().toJson(er);
                session.getRemote().sendString(error);
                return;
            } else if (!whitePlayer.equals(username)){
                Error er = new Error("Error: Incorrect player color");
                String error = new Gson().toJson(er);
                session.getRemote().sendString(error);
                return;
            }
        }
        else if (jp.getColor().equals(ChessGame.TeamColor.BLACK)){
            if (blackPlayer==null){
                Error er = new Error("Error: No black player");
                String error = new Gson().toJson(er);
                session.getRemote().sendString(error);
                return;
            } else if (!blackPlayer.equals(username)){
                Error er = new Error("Error: Incorrect player color");
                String error = new Gson().toJson(er);
                session.getRemote().sendString(error);
                return;
            }
        }

            if (jp.getColor().equals(ChessGame.TeamColor.WHITE)){
                gDataAccess.updateGames(jp.getID(), "white", getUsername(jp.getAuthString()));
            } else if (jp.getColor().equals(ChessGame.TeamColor.BLACK)){
                gDataAccess.updateGames(jp.getID(), "black", getUsername(jp.getAuthString()));
            }

            //send LoadGame to root client
            GameData game = gDataAccess.returnGames().get(jp.getID());
            LoadGame lgame = new LoadGame(game.game(), jp.getColor());
            String lg = new Gson().toJson(lgame);
            session.getRemote().sendString(lg);

            //send notification to all others
            ArrayList<String> people = games.get(jp.getID());
            String user2 = getUsername(jp.getAuthString());
            for (String person : people) {
                if (!person.equals(jp.getAuthString())) {
                    if (sessions.containsKey(person)) {
                        if (sessions.get(person).isOpen()) {
                            String sm = new Gson().toJson(new Notification(user2 + " has joined as " + jp.getColor()));
                            sessions.get(person).getRemote().sendString(sm);
                        } else {
                            sessions.remove(person);
                        }
                    }
                }
            }

    }

    public void makeMove(MakeMove move, Session session) throws IOException, DataAccessException {
        AuthData user  = (AuthData) aDataAccess.returnAuths().get(move.getAuthString());
        String username = user.username();
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

        //check game over
        if (!game.isInProgress()){
            Error er = new Error("Error: Game over, cannot move");
            String error = new Gson().toJson(er);
            session.getRemote().sendString(error);
            return;
        }

        //not observer
        if (!whitePlayer.equals(username) && !blackPlayer.equals(username)){
            Error er = new Error("Error: Observer cannot move");
            String error = new Gson().toJson(er);
            session.getRemote().sendString(error);
            return;
        }
        //not wrong team
        if (!(game.getBoard().getPiece(start)==null)) {
            if (!(game.getBoard().getPiece(start).getTeamColor().equals(color))) {
                Error er = new Error("Error: Incorrect team piece, move a " + color + " piece");
                String error = new Gson().toJson(er);
                session.getRemote().sendString(error);
                return;
            }
        }

        //validate move
        try {
            game.makeMove(move.getMove());
            //update game to represent move, update game in DB
            String name = gDataAccess.returnGames().get(move.getID()).gameName();
            gDataAccess.updateWholeGame( new GameData(move.getID(), whitePlayer, blackPlayer, name, game));
        } catch (InvalidMoveException e) {
            Error er = new Error("Error: Invalid move");
            String error = new Gson().toJson(er);
            session.getRemote().sendString(error);
            return;
        }

        ArrayList<String> people = games.get(move.getID());
        String user2 = getUsername(move.getAuthString());
        for (String person : people){
            //send LoadGame to everyone
            GameData gameD = gDataAccess.returnGames().get(move.getID());
            LoadGame lgame = new LoadGame(gameD.game(), null);
            if (aDataAccess.returnAuths().containsKey(person)) {
                if (aDataAccess.returnAuths().get(person).equals(blackPlayer)) {
                    lgame = new LoadGame(gameD.game(), ChessGame.TeamColor.BLACK);
                } else if (aDataAccess.returnAuths().get(person).equals(whitePlayer)) {
                    lgame = new LoadGame(gameD.game(), ChessGame.TeamColor.WHITE);
                }
            }

            if (sessions.containsKey(person)) {
                if (sessions.get(person).isOpen()) {
                    String lg = new Gson().toJson(lgame);
                    sessions.get(person).getRemote().sendString(lg);
                } else {
                    sessions.remove(person);
                }
            }

            //send notification to others
            if (!person.equals(move.getAuthString())) {
                if (sessions.containsKey(person)) {
                    if (sessions.get(person).isOpen()) {
                        String sm = new Gson().toJson(new Notification(user2 + " has moved: " + move.getMove().toString()));
                        sessions.get(person).getRemote().sendString(sm);
                    } else {
                        sessions.remove(person);
                    }
                }
            }

            //notify if anyone in check or checkmate
            sendCheckNotification(game, person, whitePlayer, blackPlayer);
        }
    }

    public void sendCheckNotification(ChessGame game, String person, String whitePlayer, String blackPlayer) throws IOException {
        if (game.isInCheck(ChessGame.TeamColor.WHITE)) {
            if (game.isInCheckmate(ChessGame.TeamColor.WHITE)) {
                if (sessions.containsKey(person)) {
                    if (sessions.get(person).isOpen()) {
                        String sm = new Gson().toJson(new Notification(whitePlayer + " is in checkmate"));
                        sessions.get(person).getRemote().sendString(sm);
                    } else {
                        sessions.remove(person);
                    }
                }
            } else {
                if (sessions.containsKey(person)) {
                    if (sessions.get(person).isOpen()) {
                        String sm = new Gson().toJson(new Notification(whitePlayer + " is in check"));
                        sessions.get(person).getRemote().sendString(sm);
                    } else {
                        sessions.remove(person);
                    }
                }
            }
        }
        if (game.isInCheck(ChessGame.TeamColor.BLACK)) {
            if (game.isInCheckmate(ChessGame.TeamColor.BLACK)) {
                if (sessions.containsKey(person)) {
                    if (sessions.get(person).isOpen()) {
                        String sm = new Gson().toJson(new Notification(blackPlayer + " is in checkmate"));
                        sessions.get(person).getRemote().sendString(sm);
                    } else {
                        sessions.remove(person);
                    }
                }
            } else {
                if (sessions.containsKey(person)) {
                    if (sessions.get(person).isOpen()) {
                        String sm = new Gson().toJson(new Notification(blackPlayer + " is in check"));
                        sessions.get(person).getRemote().sendString(sm);
                    } else {
                        sessions.remove(person);
                    }
                }
            }
        }
    }

    public void leave(Leave leave, Session session) throws IOException, DataAccessException {
        String user = getUsername(leave.getAuthString());
        //remove root client
        games.get(leave.getID()).remove(leave.getAuthString());
        //update game in DB
        GameData gd = gDataAccess.returnGames().get(leave.getID());
        if (gd.whiteUsername().equals(user)){
            gd = new GameData(gd.gameID(), null, gd.blackUsername(), gd.gameName(), gd.game());
        }
        if (gd.blackUsername().equals(user)){
            gd = new GameData(gd.gameID(), gd.whiteUsername(), null, gd.gameName(), gd.game());
        }
        gDataAccess.updateWholeGame(gd);

        //send notification to everyone
        ArrayList<String> people = games.get(leave.getID());
        for (String person : people){
            if (!person.equals(leave.getAuthString())) {
                if (sessions.containsKey(person)) {
                    if (sessions.get(person).isOpen()) {
                        String sm = new Gson().toJson(new Notification(user + " has left the game"));
                        sessions.get(person).getRemote().sendString(sm);
                    } else {
                        sessions.remove(person);
                    }
                }
            }
        }
    }

    public void resign(Resign resign, Session session) throws IOException, DataAccessException {
        String user = getUsername(resign.getAuthString());
        ChessGame game = gDataAccess.returnGames().get(resign.getID()).game();
        String whitePlayer = gDataAccess.returnGames().get(resign.getID()).whiteUsername();
        String blackPlayer = gDataAccess.returnGames().get(resign.getID()).blackUsername();

        //not observer
        if (!whitePlayer.equals(user) && !blackPlayer.equals(user)){
            Error er = new Error("Error: Observer cannot resign");
            String error = new Gson().toJson(er);
            session.getRemote().sendString(error);
            return;
        }

        //check gameInProgress
        if (!game.isInProgress()){
            Error er = new Error("Error: Game not in progress, cannot resign");
            String error = new Gson().toJson(er);
            session.getRemote().sendString(error);
            return;
        }

        //mark game as over -- no more moves can be made
        game.resign();

        //update game in DB
        GameData gd = gDataAccess.returnGames().get(resign.getID());
        if (gd.whiteUsername().equals(user)){
            gd = new GameData(gd.gameID(), null, gd.blackUsername(), gd.gameName(), game);
        }
        if (gd.blackUsername().equals(user)){
            gd = new GameData(gd.gameID(), gd.whiteUsername(), null, gd.gameName(), game);
        }
        gDataAccess.updateWholeGame(gd);

        //send ze notification
        ArrayList<String> people = games.get(resign.getID());
        for (String person : people){
            if (sessions.containsKey(person)) {
                if (sessions.get(person).isOpen()) {
                    String sm = new Gson().toJson(new Notification(user + " has resigned"));
                    sessions.get(person).getRemote().sendString(sm);
                } else {
                    sessions.remove(person);
                }
            }
        }
    }

    public String getUsername(String auth) throws DataAccessException {
        return aDataAccess.getVal(auth);
    }
}