package com.coma.snack.websocket;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.coma.snack.dto.StockMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class StockNotifier {

    @Autowired
    private StockWebSocketHandler stockWebSocketHandler;

    @Autowired
    private ObjectMapper objectMapper;

    // 남은 수량이 바뀌었다고 모든 화면에 알리기
    public void notifyStock(Long eventId, Long optionId, int remaining) {
        StockMessage message = new StockMessage();
        message.setType("STOCK");
        message.setEventId(eventId);
        message.setOptionId(optionId);
        message.setRemaining(remaining);

        try {
            String json = objectMapper.writeValueAsString(message); //객체->JSON
            stockWebSocketHandler.broadcast(json);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }
}