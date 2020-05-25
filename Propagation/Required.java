package com.test.SpringTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FirstService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private SecondService secondService;


    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void goMethod() throws Exception {

        jdbcTemplate.batchUpdate("INSERT INTO TB_MYTEST(MYKEY, MYVALUE) VALUES ('start', 'start')");

        for (int i = 1; i <= 5; i++) {

            try {

                secondService.subMethod(i);

            } catch (Exception e) {

                System.err.println(e);

            }

        }

        jdbcTemplate.batchUpdate("INSERT INTO TB_MYTEST(MYKEY, MYVALUE) VALUES ('end', 'end')");

    }

}

----------------------------------------------------------------------------------------------------------
    
package com.test.SpringTest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SecondService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(propagation = Propagation.REQUIRED, rollbackFor = Exception.class)
    public void subMethod(int i) throws Exception {

        String sqlString = String.format("INSERT INTO TB_MYTEST(MYKEY, MYVALUE) VALUES ('%d', '%d')", i, i);
        jdbcTemplate.batchUpdate(sqlString);

        if (i % 2 == 0) {

            throw new Exception("Failure"); // rollback transaction

        }

    }

}

----------------------------------------------------------------------------------------------------------
    
/*
 *  java.lang.Exception: Failure
 *  java.lang.Exception: Failure
 *  Transaction rolled back because it has been marked as rollback-only
 *
 *  Result: 
 *  | MYKEY | MYVALUE |
 * 
 */
