package com.coma.snack.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.coma.snack.dao.ApplicationDao;
import com.coma.snack.dao.EventDao;
import com.coma.snack.dao.OptionDao;
import com.coma.snack.dto.ApplyRequest;
import com.coma.snack.dto.ApplyResponse;
import com.coma.snack.dto.EventDto;
import com.coma.snack.dto.EventStatus;
import com.coma.snack.dto.OptionDto;
import com.coma.snack.exception.ApplyException;
import com.coma.snack.exception.ErrorCode;

@Service
public class ApplyService {

    @Autowired
    private EventDao eventDao;

    @Autowired
    private OptionDao optionDao;

    @Autowired
    private ApplicationDao applicationDao;

    @Autowired
    private EventService eventService;

    public ApplyResponse apply(Long eventId, ApplyRequest req) {
        // 1. 행사가 있는지
        EventDto event = eventDao.findById(eventId);
        if (event == null) {
            throw new ApplyException(ErrorCode.EVENT_NOT_FOUND);
        }

        // 2. 옵션이 있는지, 이 행사의 옵션이 맞는지
        OptionDto option = optionDao.findById(req.getOptionId());
        if (option == null || !option.getEventId().equals(eventId)) {
            throw new ApplyException(ErrorCode.OPTION_NOT_FOUND);
        }

        // 3. 신청 기간인지
        EventStatus status = eventService.getStatus(event, LocalDateTime.now());
        if (status == EventStatus.UPCOMING) {
            throw new ApplyException(ErrorCode.NOT_OPEN);
        }
        if (status == EventStatus.CLOSED) {
            throw new ApplyException(ErrorCode.CLOSED);
        }

        return null; // 작업 2에서 완성
    }
}