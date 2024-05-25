package websocket.example.chat;

import com.google.gson.Gson;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class WebSocketHandler extends TextWebSocketHandler {

    private final Map<String, WebSocketSession> sessions = new ConcurrentHashMap<>();
    private final Gson gson = new Gson();

    @Override
    public void afterConnectionEstablished(WebSocketSession session) throws Exception {
        String sessionId = session.getId();
        sessions.put(sessionId, session);

        Message message = Message.builder().sender(sessionId).receiver("all").build();
        message.newConnection();

        sessions.values().forEach(s -> {
            if(!sessionId.equals(s.getId())) {
                try {
                    s.sendMessage(new TextMessage(gson.toJson(message)));
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });
    }

    @Override
    protected void handleTextMessage(WebSocketSession session, TextMessage textMessage) throws Exception {
        System.out.println("textMessage.getPayload() = " + textMessage.getPayload());
        Message message = gson.fromJson(textMessage.getPayload(), Message.class);
        message.setSender(session.getId());

        WebSocketSession receiver = sessions.get(message.getReceiver());

        if(receiver != null && receiver.isOpen()) {
            receiver.sendMessage(new TextMessage(gson.toJson(message)));
        }

    }

    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) throws Exception {
        super.handleTransportError(session, exception);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {
        String sessionId = session.getId();
        sessions.remove(sessionId);

        Message message = new Message();
        message.closeConnection();
        message.setSender(sessionId);

        sessions.values().forEach(s -> {
            try {
                s.sendMessage(new TextMessage(gson.toJson(message)));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
