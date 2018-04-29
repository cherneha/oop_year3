package game;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Random;

import jdk.nashorn.api.scripting.JSObject;
import com.google.gson.*;

@ServerEndpoint("/game")
public class GameEndpoint {

    ArrayList<Dot> allApples;

    @OnMessage
    public void sendApples(Session session, String msg) {
        try {
            if (session.isOpen()) {
                int numOfApples = Integer.parseInt(msg);
                if(numOfApples > 20 || numOfApples < 1){
                    numOfApples = 6;
                }
                Random random;
                Collections.shuffle(allApples);
                JsonArray xArray = new JsonArray();
                JsonArray yArray = new JsonArray();
                for(int i = 0; i < numOfApples; i++){
                    JsonObject obj1 = new JsonObject();
                    obj1.addProperty("x", allApples.get(i).x);
                    xArray.add(obj1);
                    JsonObject obj2 = new JsonObject();
                    obj2.addProperty("y", allApples.get(i).y);
                    yArray.add(obj2);
                }
                JsonObject dots = new JsonObject();
                dots.add("x", xArray);
                dots.add("y", yArray);
                session.getBasicRemote().sendText(dots.toString());
            }
            else{
                session.getBasicRemote().sendText("not opened!!!");
            }
        } catch (IOException e) {
            try {
                session.close();
            } catch (IOException e1) {
                // Ignore
            }
        }
    }

    @OnOpen
    public void prepareApples(){
        allApples = new ArrayList();
        for(double x = -7.5; x < 8; x += 0.5){
            for(double y = -4.5; y < 3; y += 0.5){
                Dot dot = new Dot(Double.toString(x), Double.toString(y));
                allApples.add(dot);
            }
        }
    }
}