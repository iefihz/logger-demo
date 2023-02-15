package com.iefihz.log4j2;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 测试 SLF4j + Log4j2 的情况，执行原理：
 * slf4j门面调用的是log4j2的门面，再由log4j2的门面调用log4j2的实现。
 */
public class Log4j2Test02 {

    @Test
    public void test01() {
        Logger logger = LoggerFactory.getLogger(Log4j2Test02.class);
        printAllLevelLog(logger);
    }

    /**
     * 测试文件拆分、异步日志
     *
     * 异步日志说明：
     * 1.AsyncAppender方式
     *      配置此类的appender，引用其它appender，最后在root logger中引用
     *      <Async name="asyncAppender">
     *          <AppenderRef ref="consoleAppender"/>
     *      </Async>
     *
     * 2.AsyncLogger方式（单独分配线程做日志输出。把所有的AsyncAppender注释掉，两者不要混用）
     *      i.全局异步
     *          创建resources/log4j2.component.properties文件，添加内容：
     *          Log4jContextSelector=org.apache.logging.log4j.core.async.AsyncLoggerContextSelector
     *
     *      ii.混合异步（常用），配置如下logger
     *          <AsyncLogger name="com.iefihz.log4j2" level="trace" includeLocation="false" additivity="false">
     *              <AppenderRef ref="consoleAppender"/>
     *          </AsyncLogger>
     * 注意：AsyncAppender方式和AsyncLogger方式不要混合使用，AsyncLogger方式的全局和混合模式也不要混合使用，效果不会叠加。
     */
    @Test
    public void test02() {
        Logger logger = LoggerFactory.getLogger(Log4j2Test02.class);
        for (int i = 0; i < 2000; i++) {
            printAllLevelLog(logger);
        }
        for (int i = 1; i <= 1000; i++) {
            System.out.println("=====================" + i);
        }
    }

    /**
     * 测试自定义logger不会走混合异步的情况，因为配置的异步logger名为com.iefihz.log4j2，而这里实际的是com.iefihz.log4j2_2
     */
    @Test
    public void test03() {
        Logger logger = LoggerFactory.getLogger("com.iefihz.log4j2_2");
        for (int i = 0; i < 2000; i++) {
            printAllLevelLog(logger);
        }
        for (int i = 1; i <= 1000; i++) {
            System.out.println("=====================" + i);
        }
    }

    private static final void printAllLevelLog(Logger logger) {
        // 注意结合了SLF4J后，没有fatal日志的API
        // logger.fatal("fatal日志");
        logger.error("error日志");
        logger.warn("warn日志");
        logger.info("info日志");
        logger.debug("debug日志");
        logger.trace("trace日志");
    }
}
