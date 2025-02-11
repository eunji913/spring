package com.example.spring;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {
    "file:src/main/webapp/WEB-INF/spring/root-context.xml"  // classpath로 경로 지정
})
@WebAppConfiguration  // ServletContext를 설정하도록 추가
public class DatabaseConnectionTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testDatabaseConnection() {
        // 간단한 쿼리로 데이터베이스 연결을 확인
        String query = "SELECT 1";
        Integer result = jdbcTemplate.queryForObject(query, Integer.class);

        // 결과가 1이면 성공
        Assert.assertEquals(Integer.valueOf(1), result);
    }
}
