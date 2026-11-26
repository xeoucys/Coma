package com.coma.snack.websocket;

import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class StockWebSocketHandler extends TextWebSocketHandler {

    // 지금 접속해 있는 화면 목록
    private final Set<WebSocketSession> sessions = ConcurrentHashMap.newKeySet();

    // 화면이 처음 연결됐을 때
    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        sessions.add(session);
        System.out.println("WebSocket 연결: " + session.getId() + " (접속 " + sessions.size() + "명)");
    }

    // 화면을 닫거나 연결이 끊겼을 때
    @Override
    public void afterConnectionClosed(WebSocketSession session, CloseStatus status) {
        sessions.remove(session);
        System.out.println("WebSocket 종료: " + session.getId() + " (접속 " + sessions.size() + "명)");
    }

    // 통신 중 오류가 났을 때
    @Override
    public void handleTransportError(WebSocketSession session, Throwable exception) {
        sessions.remove(session);
    }
}