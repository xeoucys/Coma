package com.coma.snack.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.coma.snack.dto.*;

import com.coma.snack.service.EventService;

@RestController
@RequestMapping("/api/events")
public class EventController {
	
	@Autowired
    private EventService eventService;

    // 행사 목록과 옵션별 남은 수량 (index.html)
    @GetMapping
    public List<EventSummaryDto> getEvents() {
        return eventService.getEvents();
    }
    
 // 신청 화면용 행사 정보 (event.html)
    @GetMapping("/{eventId}")
    public EventDetailDto getEvent(@PathVariable("eventId") Long eventId) {
        return eventService.getEventDetail(eventId);
    }
    
}
