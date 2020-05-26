package com.test.SpringTest;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.transaction.support.TransactionCallbackWithoutResult;
import org.springframework.transaction.support.TransactionTemplate;

@Service
public class TestService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private PlatformTransactionManager transactionManager;

    @Transactional(rollbackFor = Exception.class)
    public void test() throws Exception {

        jdbcTemplate.batchUpdate("INSERT INTO TB_MYTEST(MYKEY, MYVALUE) VALUES ('start', 'start')");

        ExecutorService executorService = Executors.newFixedThreadPool(5);

        for (int i = 1; i <= 5; i++) {

            executorService.submit(new TestRunner(i));

        }

        executorService.shutdown();
        executorService.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);

        jdbcTemplate.batchUpdate("INSERT INTO TB_MYTEST(MYKEY, MYVALUE) VALUES ('end', 'end')");
    }

    private class TestRunner implements Runnable {

        private Integer id;

        public TestRunner(Integer id) {

            this.id = id;

        }

        @Override
        public void run() {

            TransactionTemplate transactionTemplate = new TransactionTemplate(transactionManager);

            transactionTemplate.execute(new TransactionCallbackWithoutResult() {

                @Override
                protected void doInTransactionWithoutResult(TransactionStatus status) {

                    String sqlString = String.format("INSERT INTO TB_MYTEST(MYKEY, MYVALUE) VALUES ('%d', '%d')", id, id);
                    jdbcTemplate.batchUpdate(sqlString);

                    if (id % 2 == 0) {

                        jdbcTemplate.batchUpdate("INSERT INTO UNKNOWN_TABLE(MYKEY, MYVALUE) VALUES ('no', 'no')");

                    }

                }

            });

        }

    }

}
