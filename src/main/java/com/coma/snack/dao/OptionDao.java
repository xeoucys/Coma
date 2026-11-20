package com.coma.snack.dao;
import java.util.List;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import com.coma.snack.dto.*;
//import ...dto.OptionDto;

/**
 * event_option 테이블 접근 (담당: 변연경)
 *
 * TODO (JdbcTemplate 사용)
 * - findByEventId(Long eventId): List<OptionDto>
 * - findById(Long optionId): OptionDto
 * - decreaseRemaining(Long optionId): int   ← 바뀐 행 수 반환
 *   UPDATE event_option SET remaining = remaining - 1
 *   WHERE option_id = ? AND remaining > 0
 */



@Repository
public class OptionDao {

 private static final String SELECT_OPTION =
         "SELECT option_id, event_id, name, capacity, remaining FROM event_option ";

 private static final RowMapper<OptionDto> OPTION_MAPPER =
         new BeanPropertyRowMapper<>(OptionDto.class);

 private final JdbcTemplate jdbcTemplate;

 public OptionDao(JdbcTemplate jdbcTemplate) {
     this.jdbcTemplate = jdbcTemplate;
 }

 public List<OptionDto> findByEventId(Long eventId) {
     return jdbcTemplate.query(SELECT_OPTION + "WHERE event_id = ? ORDER BY option_id", OPTION_MAPPER, eventId);
 }

 public OptionDto findById(Long optionId) {
     List<OptionDto> list =
             jdbcTemplate.query(SELECT_OPTION + "WHERE option_id = ?", OPTION_MAPPER, optionId);
     return list.isEmpty() ? null : list.get(0);
 }
}