package ru.itis.websockets.handlers;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.handler.TextWebSocketHandler;
import ru.itis.websockets.dto.Message;
import ru.itis.websockets.models.User;
import ru.itis.websockets.security.UserDetailsImpl;

import java.util.HashMap;
import java.util.Map;

@Component
@EnableWebSocket
public class WebSocketMessagesHandler extends TextWebSocketHandler {

    private static final Map<String, WebSocketSession> sessions = new HashMap<>();

    @Autowired
    private ObjectMapper objectMapper;

    public void handleMessage(WebSocketSession session, WebSocketMessage<?> message) throws Exception {
        System.out.println(session.getPrincipal());
        User user = ((UserDetailsImpl)((UsernamePasswordAuthenticationToken)session.getPrincipal()).getPrincipal()).getUser();
        String messageText = (String) message.getPayload();
        System.out.println(messageText);
        Message messageFromJson = objectMapper.readValue(messageText, Message.class);
        messageFromJson.setName(user.getName());
        WebSocketMessage result = new TextMessage(objectMapper.writeValueAsString(messageFromJson));
        for (WebSocketSession currentSession : sessions.values()) {
            currentSession.sendMessage(result);
        }
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.put(session.getId(), session);
    }

    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception{
        sessions.remove(session.getId());
    }
}
