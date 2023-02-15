package com.iefihz.log4j2;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.Test;

/**
 * 测试只是用Log4j2的情况
 */
public class Log4j2Test01 {

    /**
     * 默认日志级别为ERROR
     */
    @Test
    public void test01() {
        Logger logger = LogManager.getLogger(Log4j2Test01.class);
        printAllLevelLog(logger);
    }

    private static final void printAllLevelLog(Logger logger) {
        logger.fatal("fatal日志");
        logger.error("error日志");
        logger.warn("warn日志");
        logger.info("info日志");
        logger.debug("debug日志");
        logger.trace("trace日志");
    }
}
