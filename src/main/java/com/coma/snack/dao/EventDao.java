/**
 * event 테이블 접근 (담당: 변연경)
 *
 * TODO (JdbcTemplate 사용)
 * - findAll(): List<EventDto>
 * - findById(Long eventId): EventDto
 * - findPickupDates(Long eventId): List<LocalDate>
 */
package com.coma.snack.dao;
import java.util.List;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
// import ...dto.EventDto;  (Alt+Enter로 자동 import)

import com.coma.snack.dto.EventDto;

@Repository
public class EventDao {

    private static final String SELECT_EVENT =
            "SELECT event_id, name, open_at, close_at, closed FROM event ";

    private static final RowMapper<EventDto> EVENT_MAPPER = (rs, rowNum) -> new EventDto(
            rs.getLong("event_id"),
            rs.getString("name"),
            rs.getTimestamp("open_at").toLocalDateTime(),
            rs.getTimestamp("close_at").toLocalDateTime(),
            rs.getBoolean("closed")
    );

    private final JdbcTemplate jdbcTemplate;

    public EventDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<EventDto> findAll() {
        return jdbcTemplate.query(SELECT_EVENT, EVENT_MAPPER);
    }
}
