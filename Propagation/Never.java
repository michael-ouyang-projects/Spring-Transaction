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
    public void goMethod() {

        jdbcTemplate.batchUpdate("INSERT INTO TB_MYTEST(MYKEY, MYVALUE) VALUES ('start', 'start')");

        for (int i = 1; i <= 5; i++) {

            secondService.subMethod(i);

        }

        jdbcTemplate.batchUpdate("INSERT INTO TB_MYTEST(MYKEY, MYVALUE) VALUES ('end', 'end')");

    }

}

-------------------------------------------------------------------------------------------------------------------

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

    @Transactional(propagation = Propagation.NEVER, rollbackFor = Exception.class)
    public void subMethod(int i) {

        String sqlString = String.format("INSERT INTO TB_MYTEST(MYKEY, MYVALUE) VALUES ('%d', '%d')", i, i);
        jdbcTemplate.batchUpdate(sqlString);

    }

}

-------------------------------------------------------------------------------------------------------------------

/*
 *  org.springframework.transaction.IllegalTransactionStateException: Existing transaction found for transaction marked with propagation 'never'
 *  
 *  Result:
 *  | MYKEY | MYVALUE |
 * 
 */
