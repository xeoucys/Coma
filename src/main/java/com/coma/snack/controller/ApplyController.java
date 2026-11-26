package com.coma.snack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coma.snack.dto.ApplyRequest;
import com.coma.snack.dto.ApplyResponse;
import com.coma.snack.exception.ApplyException;
import com.coma.snack.exception.ErrorCode;
import com.coma.snack.service.ApplyService;
import com.coma.snack.websocket.StockNotifier;

@RestController
@RequestMapping("/api")
public class ApplyController {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private StockNotifier stockNotifier;

    // 간식 신청 (event.html)
    @PostMapping("/events/{eventId}/applications")
    public ApplyResponse apply(@PathVariable("eventId") Long eventId,
                               @RequestBody ApplyRequest req) {
    	// 입력값 검사: 빈 값이나 형식이 틀린 값이면 INVALID_INPUT
        if (req.getStudentId() == null || !req.getStudentId().matches("\\d{7}")) {
            throw new ApplyException(ErrorCode.INVALID_INPUT);
        }
        if (req.getName() == null || req.getName().isBlank()) {
            throw new ApplyException(ErrorCode.INVALID_INPUT);
        }
        if (req.getPhone() == null || !req.getPhone().matches("010-?\\d{4}-?\\d{4}")) {
            throw new ApplyException(ErrorCode.INVALID_INPUT);
        }
        if (req.getOptionId() == null || req.getPickupDate() == null) {
            throw new ApplyException(ErrorCode.INVALID_INPUT);
        }
        ApplyResponse response = applyService.apply(eventId, req);

        // 신청이 commit된 뒤에 남은 수량 알림
        stockNotifier.notifyStock(eventId, response.getOptionId(), response.getRemaining());
        return response;
    }
}