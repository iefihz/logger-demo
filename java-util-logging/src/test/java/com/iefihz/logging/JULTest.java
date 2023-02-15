package com.iefihz.logging;

import org.junit.Test;

import java.io.File;
import java.io.InputStream;
import java.util.logging.*;

public class JULTest {

    /**
     * 日志输出的两种方法：
     * 1.logger.info(msg)
     * 2.logger.log(level, msg)
     */
    @Test
    public void test01() {
        Logger logger = Logger.getLogger("com.iefihz.logging.JULTest");
        logger.info("================ logger.info(msg) ================");
        logger.log(Level.INFO, "================ logger.log(level, msg) ================");
    }

    /**
     * 处理动态日志信息的两种方式：
     *1.字符串拼接
     * 2.占位符
     */
    @Test
    public void test02() {
        Logger logger = Logger.getLogger("com.iefihz.logging.JULTest");
        String uno = "10001";
        String username = "张三";
        logger.info("================ 字符串拼接 =》学号：" + uno + "，姓名：" + username + " ================");
        logger.log(Level.INFO, "================ 占位符 =》学号：{0}，姓名：{1} ================", new Object[] {uno, username});
    }

    /**
     * 默认日志级别为info，且单独设置等级不会起效，需要搭配Handler共同设置
     */
    @Test
    public void test03() {
        Logger logger = Logger.getLogger("com.iefihz.logging.JULTest");
        logger.setLevel(Level.CONFIG);
        printAllLevelLog(logger);
    }

    /**
     * 自定义日志级别，搭配Handler使用，使其生效
     */
    @Test
    public void test04() {
        Logger logger = Logger.getLogger("com.iefihz.logging.JULTest");

        /**
         * 日志输出是否发送给父类的Handler去处理，需要关掉，日志有”继承关系“（树形结构，也即包路径的关系），比如：
         * com.iefihz.logging 与 com.iefihz.logging.* 存在父子关系
         */
        logger.setUseParentHandlers(false);

        // 组合Logger的Handler和Handler的Formatter
        ConsoleHandler handler = new ConsoleHandler();
        SimpleFormatter formatter = new SimpleFormatter();
        handler.setFormatter(formatter);
        logger.addHandler(handler);

        // Logger的级别必须与Handler设定的级别一致，才能达到相应的效果，否则，最终的效果是输出优先级高的，也即显示的内容较少的优先级
        handler.setLevel(Level.CONFIG);
        logger.setLevel(Level.CONFIG);

        printAllLevelLog(logger);
    }

    /**
     * 日志输出到控制台（fine级别）+持久化到日志文件（info级别）
     */
    @Test
    public void test05() throws Exception {
        Logger logger = Logger.getLogger("com.iefihz.logging.JULTest");
        logger.setUseParentHandlers(false);

        SimpleFormatter formatter = new SimpleFormatter();
        logger.setLevel(Level.FINE);

        // 控制台输出
        ConsoleHandler consoleHandler = new ConsoleHandler();
        consoleHandler.setFormatter(formatter);
        logger.addHandler(consoleHandler);
        consoleHandler.setLevel(Level.FINE);

        // 文件不存在且其父级目录也不存在，创建目录及文件
        String logFileName = "D:\\codes\\github\\logger-demo\\java-util-logger\\logs\\jul.log";
        File logFile = new File(logFileName);
        if (!logFile.exists()) {
            File parentFile = logFile.getParentFile();
            if (!parentFile.exists()) {
                parentFile.mkdirs();
            }
            logFile.createNewFile();
        }
        // 文件输出
        FileHandler fileHandler = new FileHandler(logFileName);
        fileHandler.setFormatter(formatter);
        logger.addHandler(fileHandler);
        fileHandler.setLevel(Level.INFO);

        printAllLevelLog(logger);
    }

    /**
     * Logger的父子关系，所有Logger的顶层Logger为RootLogger，名称为空白字符串。
     * Logger.getLogger的源码中：
     *      // Create and retain Logger for the root of the namespace.
     *      owner.rootLogger = owner.new RootLogger();
     *      owner.addLogger(owner.rootLogger);
     * 可发现，每个Logger创建时，都会添加一个顶层Logger作为所有Logger的顶层Logger
     */
    @Test
    public void test06() {
        Logger logger = Logger.getLogger("com.iefihz.logging.JULTest");
        Logger parentLogger = Logger.getLogger("com.iefihz.logging");

        System.out.println("logger引用：" + logger);
        System.out.println("logger名称：" + logger.getName());
        System.out.println("logger父引用：" + logger.getParent());
        System.out.println("logger父名称：" + logger.getParent().getName());
        System.out.println("===============================");
        System.out.println("parentLogger引用：" + parentLogger);
        System.out.println("parentLogger名称：" + parentLogger.getName());
        System.out.println("parentLogger父引用：" + parentLogger.getParent());
        System.out.println("parentLogger父名称：" + parentLogger.getParent().getName());
        System.out.println("===============================");
        System.out.println("logger父引用 == parentLogger引用？" + (logger.getParent() == parentLogger ? true : false));
        System.out.println("parentLogger的父引用（在这里为顶层Logger，java.util.logging.LogManager$RootLogger）：" + parentLogger.getParent());
        System.out.println("parentLogger的父名称（在这里为顶层Logger，名称为空白字符串）：" + parentLogger.getParent().getName());
    }

    /**
     * 父Logger设置同时也会作用于子Logger
     */
    @Test
    public void test07() {
        Logger logger = Logger.getLogger("com.iefihz.logging.JULTest");
        Logger parentLogger = Logger.getLogger("com.iefihz.logging");

        // 父Logger做设置
        parentLogger.setUseParentHandlers(false);
        ConsoleHandler handler = new ConsoleHandler();
        handler.setFormatter(new SimpleFormatter());
        handler.setLevel(Level.ALL);
        parentLogger.addHandler(handler);
        parentLogger.setLevel(Level.ALL);

        // 子Logger做输出
        printAllLevelLog(logger);
    }

    /**
     * 读取自定义properties，默认读取（jre（java.home的值）-> lib -> logging.properties），源码：
     *      // Read configuration.
     *      owner.readPrimordialConfiguration();
     *      readConfiguration();
     *      fname = System.getProperty("java.home");
     *      File f = new File(fname, "lib");
     *      f = new File(f, "logging.properties");
     */
    @Test
    public void test08() throws Exception {
        LogManager logManager = LogManager.getLogManager();
        InputStream is = JULTest.class.getClassLoader().getResourceAsStream("my-logging.properties");
        logManager.readConfiguration(is);
        Logger logger = Logger.getLogger("com.iefihz.logging.JULTest");
        printAllLevelLog(logger);
    }

    /**
     * 输出所有日志级别的日志
     *
     * @param logger
     */
    private static final void printAllLevelLog(Logger logger) {
        logger.severe("severe日志");
        logger.warning("warning日志");
        logger.info("info日志");
        logger.config("config日志");
        logger.fine("fine日志");
        logger.finer("finer日志");
        logger.finest("finest日志");
    }
}
