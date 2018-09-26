/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package be.howest.breakout.sockets;

import be.howest.breakout.data.Repositories;
import be.howest.breakout.domain.Block;
import be.howest.breakout.domain.Boss;
import be.howest.breakout.domain.Player;
import be.howest.breakout.domain.Power;
import be.howest.breakout.util.BreakoutException;
import be.howest.breakout.util.JSONMessages;
import be.howest.breakout.util.StringConstants;
import java.io.IOException;
import java.util.*;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import static java.lang.Integer.parseInt;

@ServerEndpoint("/game")
public class GameEndpoint {
    
    private String objectsWithPowers;

    @OnOpen
    public void onOpen(Session session) {
        generateArrayBlocks(session);
    }


    private void generatePallet(Session session) {       
//        List<Block> blocksList = Repositories.getBlockRepository().getBlocks();
//        String blocksInJSON = "[";
//        for (int half = 0; half < blocksList.size() * 2; half++) {
//            Collections.shuffle(blocksList);
//            blocksInJSON += blocksList + ",";
//        }
//        blocksInJSON = blocksInJSON.substring(0, blocksInJSON.length() - 1);
//        blocksInJSON += "]";
//
//        sendToSession(session, blocksInJSON, "generateBlocks");
    }

    @OnMessage
    public String onMessage(String message, Session in) {
        for (Session out : in.getOpenSessions()) {
            if (out == in) {
                if (message.equals("giveObjectsWithPowers")) {
                    objectsWithPowers(in);
                } else if (message.equals("generatePowerUpDowns")) {
                    return generateArrayOfPowers();
                } else if (message.equals("generateBosses")) {
                    return generateArrayOfBosses();
                } else if (message.startsWith("generateBoss")) {
                    return generateBoss(message);
                } else if (message.startsWith("updateHealth")) {
                    return updateBossHealth(message);
                } else if (message.startsWith("singlePlayerScore")) {
                    return setSinglePlayerScore(message);
                } else if (message.startsWith("multiPlayerScore")) {
                    return setMultiPlayerScore(message);
                } else if (message.equals("detectCollision")) {
                    return "todo";
                } else {
                    putRandomPowerInBlock(message, in);
                }

            }
        }
        return null;
    };

    @OnError
    public void onError(Session session, Throwable t) {
        System.out.println(t);
        //throw new BreakoutException(StringConstants.SOCKET_ON_ERROR, (Exception) t);
    }

    private void sendToSession(Session out, String message, String type) {
        try {
            if (out.isOpen()) {
                out.getBasicRemote().sendText(type + "/" + message);
            }
        } catch (IOException ex) {
            throw new BreakoutException(StringConstants.SOCKET_SEND_TO_SESSION, ex);
        }
    }
    
    private void putRandomPowerInBlock(String message, Session session) {
        message = message.substring(0, message.length() - 1);

        Random random = new Random();
        int randomPowerId = random.nextInt(8) + 1;
        Power power = Repositories.getPowerRepository().getPowerById(randomPowerId);

        objectsWithPowers += message + power+"},";
    }

    
    private void objectsWithPowers(Session session) {
        String arrayWithObjectsWithPowers = "[" + objectsWithPowers;
        arrayWithObjectsWithPowers
                = arrayWithObjectsWithPowers.substring(0, arrayWithObjectsWithPowers.length() - 1);

        arrayWithObjectsWithPowers+="]";
        sendToSession(session, arrayWithObjectsWithPowers, "BlocksWithPowers");
    }


    private void generateArrayBlocks(Session session) {
        List<Block> blocksList = Repositories.getBlockRepository().getBlocks();
        String blocksInJSON = "[";
        for (int half = 0; half < blocksList.size() * 2; half++) {
            Collections.shuffle(blocksList);
            blocksInJSON += blocksList + ",";
        }
        blocksInJSON = blocksInJSON.substring(0, blocksInJSON.length() - 1);
        blocksInJSON += "]";

        sendToSession(session, blocksInJSON, "generateBlocks");
    }

    private String generateArrayOfPowers() { // waarden zijn niet uniek ????
        List<Power> powers = Repositories.getPowerRepository().getPowers();
        String powersInJSON = "[";
        for (int i = 0; i < powers.size(); i++) {
            powersInJSON += "{\"name\":\"" + powers.get(i).getName() + "\" , \"id\":";
            ArrayList<Integer> list = new ArrayList<>();
            for (int j = 0; j < 10; j++) {
                list.add(j);
            }
            Collections.shuffle(list);
            for (int j = 0; j < 1; j++) {
                powersInJSON += list.get(j);
            }
            powersInJSON += "}";
            if (powers.size() - 1 != i) {
                powersInJSON += ",";
            }
        }
        powersInJSON += "]";
        return powersInJSON;
        //return String.format(JSONMessages.genereateArrayOfPowers, powersInJSON );
    }

    public String generateArrayOfBosses() {
        String bosses = "[";
        for (int i = 1; i < 4; i++) {
            Boss b = Repositories.getBossRepository().getSpecificBoss(i);
            bosses += "{ \"id\":" + b.getId() + ", \"name\":\"" + b.getName() + "\", \"health\":" + b.getHealth() + " }";
            if (4 - 1 != i) {
                bosses += ",";
            }
        }
        bosses += "]";
        return bosses;
    }

    private String generateBoss(String message) {  // als boss niet bestaat
        String name = message.split(" ")[1];
        Boss b = Repositories.getBossRepository().getSpecificBoss(1);
        return String.format(JSONMessages.generateBoss, b.getName());
    }

    public String updateBossHealth(String message) {
        String name = message.split(" ")[1];
        int damage = parseInt(message.split(" ")[2]);
        try {
            Repositories.getBossRepository().updateHealth(new Boss(name), damage);
        } catch (BreakoutException ex) {
            System.out.println(ex);
        }

        return String.format(JSONMessages.updateHealth, name, damage);
    }

    private String setSinglePlayerScore(String message) {
        String name = message.split(" ")[1];
        int score = parseInt(message.split(" ")[2]);
        Player p = Repositories.getPlayerRepository().getSpecifickPlayer(name);
        Repositories.getPlayerRepository().setSinglePlayerScore(p, score);
        return String.format(JSONMessages.spScore, name, score);
    }

    private String setMultiPlayerScore(String message) {
        String name = message.split(" ")[1];
        int score = parseInt(message.split(" ")[2]);
        Player p = Repositories.getPlayerRepository().getSpecifickPlayer(name);
        Repositories.getPlayerRepository().setMultiPlayerScore(p, score);
        return String.format(JSONMessages.mtScore, name, score);

    }
}
