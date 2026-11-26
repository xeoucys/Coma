/**
 * event 테이블 접근 (담당: 변연경)
 *
 * TODO (JdbcTemplate 사용)
 * - findAll(): List<EventDto>
 * - findById(Long eventId): EventDto
 * - findPickupDates(Long eventId): List<LocalDate>
 */
package com.coma.snack.dao;
import java.time.LocalDate;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
// import ...dto.EventDto;  (Alt+Enter로 자동 import)

import com.coma.snack.dto.EventDto;

@Repository
public class EventDao {

    private static final String SELECT_EVENT =
            "SELECT event_id, name, open_at, close_at, closed FROM event ";

    private static final RowMapper<EventDto> EVENT_MAPPER =
            new BeanPropertyRowMapper<>(EventDto.class);

    private final JdbcTemplate jdbcTemplate;

    public EventDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    public List<EventDto> findAll() {
        return jdbcTemplate.query(SELECT_EVENT + "ORDER BY open_at DESC", EVENT_MAPPER);
    }
    
    public EventDto findById(Long eventId) {
    	List<EventDto> list = jdbcTemplate.query(
                SELECT_EVENT + "WHERE event_id = ?", EVENT_MAPPER, eventId);
    	return list.isEmpty() ? null : list.get(0);
    }
    
    public List<LocalDate> findPickupDates(Long eventId) {
        return jdbcTemplate.query(
                "SELECT pickup_date FROM event_pickup_date "
                        + "WHERE event_id = ? ORDER BY pickup_date",
                (rs, rowNum) -> rs.getDate("pickup_date").toLocalDate(),
                eventId);
    }
}
