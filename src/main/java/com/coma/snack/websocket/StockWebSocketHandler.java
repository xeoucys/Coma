package com.coma.snack.websocket;

import org.springframework.stereotype.Component;

/**
 * WebSocket 접속자 관리, 브로드캐스트 (담당: 오지우)
 *
 * TODO
 * - TextWebSocketHandler 상속
 * - 접속자 목록: ConcurrentHashMap.newKeySet()
 * - afterConnectionEstablished / afterConnectionClosed / handleTransportError
 * - broadcast(String json): 모든 접속자에게 전송
 *
 * 설계: docs/design-server-api.md 4-1, 4-6
 */
@Component
public class StockWebSocketHandler {
}
