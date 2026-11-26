package com.coma.snack.service;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

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

    @Transactional(isolation = Isolation.READ_COMMITTED)
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

        // 4. 이미 신청한 학생인지
        if (applicationDao.exists(eventId, req.getStudentId())) {
            throw new ApplyException(ErrorCode.DUPLICATE);
        }

        // 5. 정원 차감: 남은 수량이 있을 때만 1 줄어듦
        int updated = optionDao.decreaseRemaining(req.getOptionId());
        if (updated == 0) {
            throw new ApplyException(ErrorCode.SOLD_OUT);
        }

        // 6. 저장
        Long applicationId;
        try {
            applicationId = applicationDao.insert(eventId, req);
        } catch (DuplicateKeyException e) {
            // UNIQUE(student_id, event_id) 위반
            throw new ApplyException(ErrorCode.DUPLICATE);
        } catch (DataIntegrityViolationException e) {
            // 외래키 위반 -- 행사에 없는 수령일, member에 없는 학번
            throw new ApplyException(ErrorCode.INVALID_INPUT);
        }

        // 7. 성공 응답
        ApplyResponse response = new ApplyResponse();
        response.setSuccess(true);
        response.setCode("SUCCESS");
        response.setMessage("신청이 완료되었습니다.");
        response.setApplicationId(applicationId);
        response.setOptionId(option.getOptionId());
        OptionDto after = optionDao.findById(req.getOptionId());
        response.setRemaining(after.getRemaining());
        return response;
    }
}