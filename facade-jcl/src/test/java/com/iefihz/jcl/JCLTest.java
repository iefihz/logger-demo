package com.iefihz.jcl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.Test;

public class JCLTest {

    /**
     * 分别在有没有导入log4j依赖和有导入的情况下测试，Log主要实现有四个，优先级由高到低如下所示：
     * org.apache.commons.logging.impl.Log4JLogger      （log4j的实现）
     * org.apache.commons.logging.impl.Jdk14Logger（jul的实现）
     * org.apache.commons.logging.impl.Jdk13LumberjackLogger    （jdk1.3以前的jul实现）
     * org.apache.commons.logging.impl.SimpleLog    （jcl自带的简单实现）
     */
    @Test
    public void test01() {
        Log log = LogFactory.getLog(JCLTest.class);
        log.info("===== test01 =====");
    }
}
