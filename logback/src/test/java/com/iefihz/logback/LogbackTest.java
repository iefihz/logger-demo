package com.iefihz.logback;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LogbackTest {

    /**
     * 异步日志添加方式（原理是新开一条线程进行日志输出）：
     * <appender name="asyncAppender" class="ch.qos.logback.classic.AsyncAppender">
     *     <appender-ref ref="consoleAppender"/>        // 需要添加异步的appender，然后再把asyncAppender添加到root
     * </appender>
     */
    @Test
    public void test01() {
        Logger logger = LoggerFactory.getLogger(LogbackTest.class);
        for (int i = 0; i < 2000; i++) {
            logger.error("error日志");
            logger.warn("warn日志");
            logger.info("info日志");
            logger.debug("debug日志");
            logger.trace("trace日志");
        }
        for (int i = 1; i <= 1000; i++) {
            System.out.println("=================== " + i + " ===================");
        }
    }

    /**
     * 测试自定义logger
     */
    @Test
    public void test02() {
        Logger logger = LoggerFactory.getLogger("AccessLogger");
        logger.error("error日志");
        logger.warn("warn日志");
        logger.info("info日志");
        logger.debug("debug日志");
        logger.trace("trace日志");
    }

}
