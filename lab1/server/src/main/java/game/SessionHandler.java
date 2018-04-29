package game;

import javax.websocket.Session;
import java.util.HashSet;
import java.util.Set;

public class SessionHandler {
    private final Set<Session> sessions = new HashSet();
    public void addSession(Session session) {
        sessions.add(session);
    }

    public void removeSession(Session session) {
        sessions.remove(session);
    }
}