import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/apples")
public class GameEndpoint {

    SessionHandler sessionHandler;

    GameEndpoint(){
        sessionHandler = new SessionHandler();
    }

    @OnMessage
    public void pesponseMessage(String message, Session session){
        System.out.println("onMessage::From=" + session.getId() + " Message=" + message);

        try {
            session.getBasicRemote().sendText("Hello Client " + session.getId() + "!");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @OnOpen
    public void open(Session session) {
        sessionHandler.addSession(session);
    }

    @OnClose
    public void close(Session session){
        sessionHandler.removeSession(session);
    }
}
