package com.coma.snack.websocket;

import org.springframework.stereotype.Component;

/**
 * 수량 변경 알림 (담당: 오지우)
 *
 * TODO
 * - notifyStock(Long eventId, Long optionId, int remaining)
 *   → {"type":"STOCK","eventId":1,"optionId":1,"remaining":11}
 *
 * 설계: docs/design-server-api.md 4-6
 */
@Component
public class StockNotifier {
}
