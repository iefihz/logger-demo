package com.iefihz.slf4j;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SLF4JTest {

    /**
     * 通用的五个日志级别：error、warn、info（默认）、debug、trace
     */
    @Test
    public void test01() {
        Logger logger = LoggerFactory.getLogger(SLF4JTest.class);
        printAllLevelLog(logger);
    }

    /**
     * 日志输出信息，字符串可通过直接拼接、类似JUL的占位符、不定参数拼接（推荐）。
     * 如果是对象，例如异常信息对象，不需要占位符。
     */
    @Test
    public void test02() {
        Logger logger = LoggerFactory.getLogger(SLF4JTest.class);

        // 输出字符串
        String name = "张三";
        int age = 18;
        logger.info("姓名：" + name + "，年龄：" + age);
        logger.info("姓名：{}，姓名：{}", new Object[]{name, age});
        logger.info("姓名：{}，姓名：{}", name, age);

        // 输出异常信息对象
        try {
            int i = 1/0;
        } catch (Exception e) {
            logger.error("此处发生异常，具体如下：", e);
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
