package com.coma.snack.dao;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.Statement;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Repository;

import com.coma.snack.dto.ApplyRequest;

/**
 * application 테이블 접근 (담당: 변연경)
 *
 * TODO (JdbcTemplate 사용)
 * - insert(Long eventId, ApplyRequest req): Long
 * - exists(Long eventId, String studentId): boolean
 */
@Repository
public class ApplicationDao {
	
	private final JdbcTemplate jdbcTemplate;

    public ApplicationDao(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    // 신청 저장 후, 새로 생긴 신청 번호(application_id) 반환
    public Long insert(Long eventId, ApplyRequest req) {
        String sql = "INSERT INTO application "
                + "(event_id, option_id, student_id, name, phone, pickup_date) "
                + "VALUES (?, ?, ?, ?, ?, ?)";

        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
            ps.setLong(1, eventId);
            ps.setLong(2, req.getOptionId());
            ps.setString(3, req.getStudentId());
            ps.setString(4, req.getName());
            ps.setString(5, req.getPhone());
            ps.setDate(6, Date.valueOf(req.getPickupDate()));
            return ps;
        }, keyHolder);

        return keyHolder.getKey().longValue();
    }
    public boolean exists(Long eventId, String studentId) {
        String sql = "SELECT COUNT(*) FROM application WHERE event_id = ? AND student_id = ?";
        Integer count = jdbcTemplate.queryForObject(sql, Integer.class, eventId, studentId);
        return count != null && count > 0;
    }
}
