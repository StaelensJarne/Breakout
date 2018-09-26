/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.sockets;

import be.howest.breakout.data.Repositories;
import be.howest.breakout.domain.Ball;
import be.howest.breakout.domain.Player;
import be.howest.breakout.util.BreakoutException;
import be.howest.breakout.util.JSONMessages;
import be.howest.breakout.util.StringConstants;
import java.io.IOException;
import java.util.*;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

/**
 *
 * @author jarne
 */
@ServerEndpoint("/index")
public class MenuEndpoint {

    GameEndpoint gameEndpoint = new GameEndpoint();

    private static final Map<Session, String> users = Collections.synchronizedMap(new HashMap<Session, String>());
    private static int uniekId = 0;

    @OnOpen
    public void onOpen(Session userSession) {
        String id = generateNewId();
        users.put(userSession, id);
        String jsonMsg = String.format(JSONMessages.connected, id);
        for (Session out : userSession.getOpenSessions()) {
            if (out != userSession) {
                sendToSession(out, jsonMsg);
            }
        }
    }

    @OnMessage
    public String onMessage(String message, Session in) {
        //System.out.println("Ontvangen: " + message);
        for (Session out : in.getOpenSessions()) {
            if (out == in) { // out != in ????????????????????
                if (message.startsWith("player")) {
                    return addPlayer(message);
                } else if (message.startsWith("difficulty")) {
                    return setDifficulty(message);
                } else if (message.equals("highscores")) {
                    return displayHighscore(message);
                } else {
                    throw new BreakoutException(StringConstants.SOCKET_UNKNOW_MESSAGE);
                }
            }
        }
        throw new BreakoutException(StringConstants.SOCKET_UNKNOW_MESSAGE);
    }

    @OnClose
    public void onClose(Session oldSession) {
        String id = users.get(oldSession);
        users.remove(oldSession);
        String jsonMsg = String.format(JSONMessages.disconnected, id);
        for (Session out : oldSession.getOpenSessions()) {
            if (out != oldSession) {
                sendToSession(out, jsonMsg);
            }
        }
    }

    @OnError
    public void onError(Session session, Throwable t) {
        throw new BreakoutException(StringConstants.SOCKET_ON_ERROR, (Exception) t);
    }

    private synchronized String generateNewId() {
        uniekId++;
        return "" + uniekId;
    }

    private void sendToSession(Session out, String message) {
        try {
            if (out.isOpen()) {
                out.getBasicRemote().sendText(message);
            }
        } catch (IOException ex) {
            throw new BreakoutException(StringConstants.SOCKET_SEND_TO_SESSION, ex);
        }
    }

    private String addPlayer(String message) {
        System.out.println(message);
        Player newPlayer = null;
        //try {
        String playerName = message.split(" ")[1];
        newPlayer = new Player(playerName);
        //}catch(ArrayIndexOutOfBoundsException ex){}
        Repositories.getPlayerRepository().insertPlayer(newPlayer);
        //if(newPlayer != null){
        return String.format(JSONMessages.playerAdded, newPlayer.getNaam());
        //}
        //throw new BreakoutException(StringConstants.UNABLE_TO_ADD_PLAYER);  
    }

    private String setDifficulty(String message) {
        String difficulty = message.split(" ")[1];
        Ball b = new Ball(difficulty);
        String speed = "{\"speedX\":" + b.getSpeedX() + ",\"speedY\":" + b.getSpeedY() + "}";
        return speed;
    }

    private String displayHighscore(String message) {
        List<Player> players = Repositories.getPlayerRepository().getPlayers();
        int[] singlePlayerScore = new int[players.size()];
        int[] multiPlayerScore = new int[players.size()];
        for (int i = 0; i < players.size(); i++) {
            singlePlayerScore[i] = players.get(i).getSinglePlayerScore();
            multiPlayerScore[i] = players.get(i).getMultiPlayerScore();
        }
        String scores = "[";
        for (int i = 0; i < players.size(); i++) {
            scores += "{ \"p\":" + "\"" + players.get(i).getNaam() + "\",";
            scores += "\"s\":" + "\"" + singlePlayerScore[i] + "\",";
            scores += "\"m\":" + "\"" + multiPlayerScore[i] + "\"}";
            if (players.size() - 1 != i) {
                scores += ",";
            }
        }
        scores += "]";
        System.out.println(scores);
        return scores;
        //return String.format(JSONMessages.showScores);
    }

}
