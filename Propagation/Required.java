package com.test.SpringTest;

import java.sql.SQLException;

import org.springframework.aop.framework.AopContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class MyService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRED)
    public void test() throws SQLException {

        jdbcTemplate.batchUpdate("INSERT INTO TB_MYTEST(MYKEY, MYVALUE) VALUES ('start', 'start')");

        for (int i = 1; i <= 5; i++) {

            ((MyService) AopContext.currentProxy()).subMethod(i);

        }

        jdbcTemplate.batchUpdate("INSERT INTO TB_MYTEST(MYKEY, MYVALUE) VALUES ('end', 'end')");

    }

    @Transactional(propagation = Propagation.REQUIRED)
    private void subMethod(int i) throws SQLException {

        String sqlString = String.format("INSERT INTO TB_MYTEST(MYKEY, MYVALUE) VALUES ('%d', '%d')", i, i);
        jdbcTemplate.batchUpdate(sqlString);

        if (i % 2 == 0) {

            throw new SQLException();

        }

    }

}

/*
 *  Result:
 *  | MYKEY | MYVALUE |
 * 
 */
