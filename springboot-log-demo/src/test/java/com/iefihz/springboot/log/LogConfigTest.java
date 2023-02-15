package com.iefihz.springboot.log;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@SpringBootTest
@RunWith(SpringRunner.class)
public class LogConfigTest {

    @Autowired
    private Logger logger;

    @Test
    public void test01() {
        for (int i = 0; i < 2000; i++) {
            printAllLevelLog(logger);
        }
        for (int i = 1; i <= 1000; i++) {
            System.out.println("=====================" + i);
        }
    }

    @Test
    public void test02() {
        Logger logger2 = LoggerFactory.getLogger("ApiLogger");
        for (int i = 0; i < 2000; i++) {
            printAllLevelLog(logger2);
        }
        for (int i = 1; i <= 1000; i++) {
            System.out.println("=====================" + i);
        }
    }

    private static final void printAllLevelLog(Logger logger) {
        logger.error("error日志");
        logger.warn("warn日志");
        logger.info("info日志");
        logger.debug("debug日志");
        logger.trace("trace日志");
    }
}
