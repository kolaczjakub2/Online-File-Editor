package observers;

import websocket.WebSocketSubject;

import java.util.HashMap;
import java.util.Map;

public interface Observer {
    Map<String,WebSocketSubject> subjects=new HashMap<>();
    void update(String type,Object message);
    void addSubject(String type,WebSocketSubject subject);
}
