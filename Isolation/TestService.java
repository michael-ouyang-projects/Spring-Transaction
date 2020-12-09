package com.test.SpringTest;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TestService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Transactional(isolation = Isolation.SERIALIZABLE)
    public void testA() throws Exception {
        // jdbcTemplate.execute("INSERT INTO TT_USER (NAME, AGE) VALUES ('AAA', '100')");
        jdbcTemplate.execute("UPDATE TT_USER SET AGE = '200' WHERE NAME = 'AAA'");
        List<User> users = jdbcTemplate.query("SELECT NAME, AGE FROM TT_USER", new BeanPropertyRowMapper<User>(User.class));
        users.forEach(user -> {
            System.out.println(String.format("%s: %s, %s", "A", user.getName(), user.getAge()));
        });

        Thread.sleep(10000);
        System.out.println("--------------------------");

        // jdbcTemplate.execute("INSERT INTO TT_USER (NAME, AGE) VALUES ('BBB', '100')");
        jdbcTemplate.execute("UPDATE TT_USER SET AGE = '200' WHERE NAME = 'BBB'");
        List<User> usersb = jdbcTemplate.query("SELECT NAME, AGE FROM TT_USER", new BeanPropertyRowMapper<User>(User.class));
        usersb.forEach(user -> {
            System.out.println(String.format("%s: %s, %s", "A", user.getName(), user.getAge()));
        });
    }

    @Transactional(isolation = Isolation.READ_COMMITTED)
    public void testB() throws Exception {
     // jdbcTemplate.execute("INSERT INTO TT_USER (NAME, AGE) VALUES ('AAA', '100')");
        jdbcTemplate.execute("UPDATE TT_USER SET AGE = '200' WHERE NAME = 'CCC'");
        List<User> users = jdbcTemplate.query("SELECT NAME, AGE FROM TT_USER", new BeanPropertyRowMapper<User>(User.class));
        users.forEach(user -> {
            System.out.println(String.format("%s: %s, %s", "A", user.getName(), user.getAge()));
        });

        Thread.sleep(10000);
        System.out.println("--------------------------");

        // jdbcTemplate.execute("INSERT INTO TT_USER (NAME, AGE) VALUES ('BBB', '100')");
        jdbcTemplate.execute("UPDATE TT_USER SET AGE = '200' WHERE NAME = 'DDD'");
        List<User> usersb = jdbcTemplate.query("SELECT NAME, AGE FROM TT_USER", new BeanPropertyRowMapper<User>(User.class));
        usersb.forEach(user -> {
            System.out.println(String.format("%s: %s, %s", "A", user.getName(), user.getAge()));
        });
    }

    // @Transactional(isolation = Isolation.SERIALIZABLE)
    // public void testA() throws Exception {
    // // jdbcTemplate.execute("INSERT INTO TT_USER (NAME, AGE) VALUES ('AAA', '100')");
    // jdbcTemplate.execute("UPDATE TT_USER SET AGE = '200' WHERE NAME = 'AAA'");
    // List<User> users = jdbcTemplate.query("SELECT NAME, AGE FROM TT_USER", new BeanPropertyRowMapper<User>(User.class));
    // users.forEach(user -> {
    // System.out.println(String.format("%s: %s, %s", "A", user.getName(), user.getAge()));
    // });
    //
    // Thread.sleep(10000);
    // System.out.println("--------------------------");
    //
    // // jdbcTemplate.execute("INSERT INTO TT_USER (NAME, AGE) VALUES ('BBB', '100')");
    // jdbcTemplate.execute("UPDATE TT_USER SET AGE = '200' WHERE NAME = 'BBB'");
    // List<User> usersb = jdbcTemplate.query("SELECT NAME, AGE FROM TT_USER", new BeanPropertyRowMapper<User>(User.class));
    // usersb.forEach(user -> {
    // System.out.println(String.format("%s: %s, %s", "A", user.getName(), user.getAge()));
    // });
    // }
    //
    // @Transactional(isolation = Isolation.SERIALIZABLE)
    // public void testB() throws Exception {
    // // jdbcTemplate.execute("INSERT INTO TT_USER (NAME, AGE) VALUES ('CCC', '100')");
    // jdbcTemplate.execute("UPDATE TT_USER SET AGE = '200' WHERE NAME = 'CCC'");
    // List<User> users = jdbcTemplate.query("SELECT NAME, AGE FROM TT_USER", new BeanPropertyRowMapper<User>(User.class));
    // users.forEach(user -> {
    // System.out.println(String.format("%s: %s, %s", "B", user.getName(), user.getAge()));
    // });
    //
    // Thread.sleep(5000);
    // System.out.println("--------------------------");
    //
    // // jdbcTemplate.execute("INSERT INTO TT_USER (NAME, AGE) VALUES ('DDD', '100')");
    // jdbcTemplate.execute("UPDATE TT_USER SET AGE = '200' WHERE NAME = 'DDD'");
    // List<User> usersb = jdbcTemplate.query("SELECT NAME, AGE FROM TT_USER", new BeanPropertyRowMapper<User>(User.class));
    // usersb.forEach(user -> {
    // System.out.println(String.format("%s: %s, %s", "B", user.getName(), user.getAge()));
    // });
    // }

    protected String testMockA() {
        return "old";
    }

    public String testMockB() {
        if ("B" == "B") {
            return "testing Mock B!";
        } else {
            return null;
        }
    }

}
