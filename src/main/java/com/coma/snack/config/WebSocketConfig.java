package com.coma.snack.config;

import org.springframework.context.annotation.Configuration;

/**
 * WebSocket 설정 (담당: 오지우)
 *
 * TODO
 * - @EnableWebSocket 추가, WebSocketConfigurer 구현
 * - "/ws/stock" 주소에 StockWebSocketHandler 등록
 *
 * 설계: docs/design-server-api.md 4-6
 */
@Configuration
public class WebSocketConfig {
}
