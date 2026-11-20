package com.coma.snack.service;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.coma.snack.dto.EventSummaryDto;

@Service
public class EventService {
	
    // TODO 서버 3번: EventDao, OptionDao로 조회하고 getStatus()로 상태 계산
    public List<EventSummaryDto> getEvents() {
        return new ArrayList<>();
    }
    
}
