package websocket;

import com.google.gson.Gson;
import observers.Observer;
import org.springframework.messaging.simp.stomp.*;
import org.springframework.util.SerializationUtils;
import org.springframework.util.concurrent.ListenableFuture;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.socket.WebSocketHttpHeaders;
import org.springframework.web.socket.client.standard.StandardWebSocketClient;
import org.springframework.web.socket.messaging.WebSocketStompClient;
import org.springframework.web.socket.sockjs.client.SockJsClient;
import org.springframework.web.socket.sockjs.client.Transport;
import org.springframework.web.socket.sockjs.client.WebSocketTransport;
import org.springframework.web.socket.sockjs.frame.Jackson2SockJsMessageCodec;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ExecutionException;

public class WebSocketSubject {

    private final WebSocketHttpHeaders headers = new WebSocketHttpHeaders();
    private final String urlToListen;
    private final String urlToSend;
    private final String type;
    StompSession stompSession;
    Gson g;
    private List<Observer> observers = new ArrayList<Observer>();

    public WebSocketSubject(String urlToListen, String urlToSend, String type) {
        g = new Gson();
        this.urlToListen = urlToListen;
        this.urlToSend = urlToSend;
        this.type = type;
        try {
            ListenableFuture<StompSession> f = connect();
            stompSession = f.get();
            subscribe(stompSession);
        } catch (InterruptedException | ExecutionException e) {
            System.out.println("Nie mozna nawiazac polaczenie Websocket z Serwerem");
        }

    }

    public void attach(Observer observer) {
        observers.add(observer);
        observer.addSubject(type, this);
    }

    public void notifyAllObservers(Object message) {
        for (Observer observer : observers) {
            observer.update(type, message);
        }
    }

    public ListenableFuture<StompSession> connect() {

        Transport webSocketTransport = new WebSocketTransport(new StandardWebSocketClient());
        List<Transport> transports = Collections.singletonList(webSocketTransport);

        SockJsClient sockJsClient = new SockJsClient(transports);
        sockJsClient.setMessageCodec(new Jackson2SockJsMessageCodec());

        WebSocketStompClient stompClient = new WebSocketStompClient(sockJsClient);

        String url = "ws://{host}:{port}/socket";
        try {
            ListenableFuture<StompSession> listenableFuture = stompClient.connect(url, headers, new WebSocketSubject.MyHandler(), "localhost", 8080);
            return listenableFuture;
        } catch (ResourceAccessException e) {
            return null;
        }

    }

    public void subscribe(StompSession stompSession) {
        stompSession.subscribe(urlToListen, new StompFrameHandler() {

            public Type getPayloadType(StompHeaders stompHeaders) {
                return byte[].class;
            }

            public void handleFrame(StompHeaders stompHeaders, Object o) {
                System.out.println(o);
                notifyAllObservers(o);
            }
        });
    }

    public boolean sendMessage(Object message) {
        if (stompSession != null) {
            try {
                String data = g.toJson(message);
                stompSession.send(urlToSend, data.getBytes());
            } catch (IllegalStateException ignored) {
                return true;
            }
            return true;
        }
        return false;
    }

    private class MyHandler extends StompSessionHandlerAdapter {
        @Override
        public void handleException(StompSession session, StompCommand command, StompHeaders headers, byte[] payload, Throwable exception) {
        }
    }

}
