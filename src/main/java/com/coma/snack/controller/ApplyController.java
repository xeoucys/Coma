package com.coma.snack.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.coma.snack.dto.ApplyRequest;
import com.coma.snack.dto.ApplyResponse;
import com.coma.snack.service.ApplyService;

@RestController
@RequestMapping("/api")
public class ApplyController {

    @Autowired
    private ApplyService applyService;

    // 간식 신청 (event.html)
    @PostMapping("/events/{eventId}/applications")
    public ApplyResponse apply(@PathVariable("eventId") Long eventId,
                               @RequestBody ApplyRequest req) {
        return applyService.apply(eventId, req);
    }
}