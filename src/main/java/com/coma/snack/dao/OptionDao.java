package com.coma.snack.dao;

import org.springframework.stereotype.Repository;

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
}
