package com.coma.snack.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.jdbc.core.JdbcTemplate;

import com.coma.snack.dao.EventDao;
import com.coma.snack.dto.ApplyRequest;
import com.coma.snack.exception.ApplyException;
import com.coma.snack.exception.ErrorCode;

@SpringBootTest
class ApplyServiceTest {

    @Autowired
    private ApplyService applyService;

    @Autowired
    private EventDao eventDao;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    private static final Long EVENT_ID = 1L;
    private static final Long OPTION_ID = 1L;

    private LocalDate pickupDate;
    private AtomicInteger success;
    private AtomicInteger soldOut;
    private AtomicInteger duplicate;

    // 테스트마다 실행 전: 신청 내역 비우기, 행사를 신청 기간으로, 테스트 학생 10명 등록
    @BeforeEach
    void setUp() {
        jdbcTemplate.update("DELETE FROM application WHERE event_id = ?", EVENT_ID);
        jdbcTemplate.update("UPDATE event SET open_at = '2026-01-01 00:00:00', "
                + "close_at = '2099-12-31 23:59:59', closed = false WHERE event_id = ?", EVENT_ID);
        for (int i = 0; i < 10; i++) {
            jdbcTemplate.update("INSERT IGNORE INTO member (student_id, name) VALUES (?, ?)",
                    "990000" + i, "테스트" + i);
        }
        pickupDate = eventDao.findPickupDates(EVENT_ID).get(0);

        success = new AtomicInteger();
        soldOut = new AtomicInteger();
        duplicate = new AtomicInteger();
    }

    // 테스트마다 실행 후: 테스트 학생과 신청 내역 지우기
    @AfterEach
    void tearDown() {
        jdbcTemplate.update("DELETE FROM application WHERE student_id LIKE '99%'");
        jdbcTemplate.update("DELETE FROM member WHERE student_id LIKE '99%'");
    }

    private ApplyRequest makeRequest(String studentId) {
        ApplyRequest req = new ApplyRequest();
        req.setStudentId(studentId);
        req.setName("테스트");
        req.setPhone("010-0000-0000");
        req.setOptionId(OPTION_ID);
        req.setPickupDate(pickupDate);
        return req;
    }

    private int getRemaining() {
        return jdbcTemplate.queryForObject(
                "SELECT remaining FROM event_option WHERE option_id = ?", Integer.class, OPTION_ID);
    }

    // 학번마다 스레드를 하나씩 만들어서 동시에 신청
    private void applyAtOnce(String[] studentIds) throws InterruptedException {
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(studentIds.length);

        for (String studentId : studentIds) {
            Thread t = new Thread(() -> {
                try {
                    start.await();   // 출발 신호 기다리기
                    applyService.apply(EVENT_ID, makeRequest(studentId));
                    success.incrementAndGet();
                } catch (ApplyException e) {
                    if (e.getErrorCode() == ErrorCode.SOLD_OUT) {
                        soldOut.incrementAndGet();
                    }
                    if (e.getErrorCode() == ErrorCode.DUPLICATE) {
                        duplicate.incrementAndGet();
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    done.countDown();   // 끝났다고 알리기
                }
            });
            t.start();
        }

        start.countDown();   // 출발 신호: 모든 스레드가 동시에 출발
        done.await();        // 모든 스레드가 끝날 때까지 기다리기
    }
}