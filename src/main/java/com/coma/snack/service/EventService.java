package com.coma.snack.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coma.snack.dao.EventDao;
import com.coma.snack.dao.OptionDao;
import com.coma.snack.dto.EventDto;
import com.coma.snack.dto.EventStatus;
import com.coma.snack.dto.EventSummaryDto;


@Service
public class EventService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private OptionDao optionDao;

    // GET /api/events
    public List<EventSummaryDto> getEvents() {
        LocalDateTime now = LocalDateTime.now();
        List<EventSummaryDto> result = new ArrayList<>();

        for (EventDto event : eventDao.findAll()) {
            EventSummaryDto summary = new EventSummaryDto();
            summary.setEventId(event.getEventId());
            summary.setName(event.getName());
            summary.setOpenAt(event.getOpenAt());
            summary.setCloseAt(event.getCloseAt());
            summary.setStatus(getStatus(event, now));
            summary.setOptions(optionDao.findByEventId(event.getEventId()));
            result.add(summary);
        }
        return result;
    }

    // 행사 상태
    public EventStatus getStatus(EventDto event, LocalDateTime now) {
        if (event.isClosed()) {
            return EventStatus.CLOSED;     // 품절되어 마감
        }
        if (now.isBefore(event.getOpenAt())) {
            return EventStatus.UPCOMING;   // 오픈 전
        }
        if (!now.isBefore(event.getCloseAt())) {
            return EventStatus.CLOSED;     // 마감 시각이 지남
        }
        return EventStatus.OPEN;           // 진행 중
    }
}