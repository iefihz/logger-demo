package com.iefihz.log4j;

import org.apache.log4j.BasicConfigurator;
import org.apache.log4j.Logger;
import org.junit.Test;

public class Log4jTest {

    /**
     * 默认输出到控制台、日志级别为debug
     */
    @Test
    public void test01() {
        // 加载初始化配置（在没有额外配置文件时测试）
        BasicConfigurator.configure();
        Logger logger = Logger.getLogger(Log4jTest.class);
        logger.info("=== test01 ===");
        printAllLevelLog(logger);
    }

    /**
     * 自定义配置
     * 查看Logger.getLogger(xxx.class)源码，找到org.apache.log4j.LogManager的static代码块，里面的这行代码
     * OptionConverter.selectAndConfigure(url, configuratorClassName, getLoggerRepository())其下的
     * ((Configurator)configurator).doConfigure(url, hierarchy)说明了加载配置的过程。

     持久化到数据库表结构（需预先创建表结构）：
     CREATE TABLE `t_log4j_logs` (
         `id` bigint(20) NOT NULL AUTO_INCREMENT,
         `project_name` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '项目名称',
         `create_time` datetime NOT NULL COMMENT '创建时间',
         `level` varchar(5) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '日志级别',
         `thread` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '当前线程',
         `category` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '日志分类',
         `filename` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '源文件名',
         `message` varchar(255) COLLATE utf8mb4_unicode_ci NOT NULL DEFAULT '' COMMENT '日志信息',
         PRIMARY KEY (`id`)
     ) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

     */
    @Test
    public void test02() {
        // Logger记录系统的日志，LogLog记录Logger的日志，如果要查看Logger自身的日志，则需要打开这项
//        LogLog.setInternalDebugging(true);

        Logger logger = Logger.getLogger(Log4jTest.class);

        for (int i = 0; i < 100; i++) {
            printAllLevelLog(logger);
        }
    }

    /**
     * 测试自定义Logger
     */
    @Test
    public void test03() {
        Logger logger = Logger.getLogger("AccessLogger");
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
