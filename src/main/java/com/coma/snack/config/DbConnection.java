package com.coma.snack.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

@Component
public class DbConnection implements CommandLineRunner {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Override
    public void run(String... args) throws Exception {
        System.out.println("DB 연결 확인 시작!");

        try {
            String dbName = jdbcTemplate.queryForObject("SELECT DATABASE()", String.class);
            String version = jdbcTemplate.queryForObject("SELECT VERSION()", String.class);
            Integer tableCount = jdbcTemplate.queryForObject(
                    "SELECT COUNT(*) FROM information_schema.tables WHERE table_schema = DATABASE()",
                    Integer.class);

            System.out.println("DB 연결 성공!");
            System.out.println("DB 이름 : " + dbName);
            System.out.println("MariaDB 버전 : " + version);
            System.out.println("테이블 수 : " + tableCount);
        } catch (Exception e) {
            System.out.println("DB 연결 실패! application-local.properties의 아이디와 비밀번호를 확인하세요.");
            System.out.println("원인 : " + e.getMessage());
        }
    }
}