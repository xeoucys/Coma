package com.coma.snack.dao;

import org.springframework.stereotype.Repository;

/**
 * event 테이블 접근 (담당: 변연경)
 *
 * TODO (JdbcTemplate 사용)
 * - findAll(): List<EventDto>
 * - findById(Long eventId): EventDto
 * - findPickupDates(Long eventId): List<LocalDate>
 */
@Repository
public class EventDao {
}
