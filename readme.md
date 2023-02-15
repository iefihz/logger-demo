# 1.JUL(java.util.logging)
## 1.1 简介
​		Java Util Logging，java原生日志框架，不需要引入第三方类库，使用方便，比较简单，主要使用在小型应用中。

## 1.2 组件
​		大致流程：应用app =》日志记录器Logger =》日志处理器Handler =》完成日志输出。其中，Logger与Handler过程中，还穿插着日志过滤器Filter =》日志格式化组件Formatter =》日志输出级别Level等组件。

| 组件                        | 描述                                                         |
| --------------------------- | ------------------------------------------------------------ |
| Logger（日志记录器）        | 访问日志系统的入口程序。                                     |
| Handler（日志处理器）       | 每个Logger都会关联一个或多个Handler，Logger把日志交给Handler处理，控制日志输出（控制台输出、文件输出等等）。 |
| Filter（日志过滤器）        | 制定日志被记录的规则，哪些日志会被记录，哪些会被忽略。       |
| Formatter（日志格式化组件） | 对日志的数据信息进行格式化，决定了日志输出的格式。           |
| Level（日志输出级别）       | 每条日志都有对应的级别，根据不同需求指定不同的日志级别。数值越大，级别越高，显示的信息越少，OFF关闭日志记录，ALL启用所有日志记录。值得注意的是，只会记录与设定优先级相同或更高级别的日志。 |

​		JUL的日志级别（默认INFO），可在java.util.logging.Level中查看：

```java
public static final Level OFF = new Level("OFF",Integer.MAX_VALUE, defaultBundle);
public static final Level SEVERE = new Level("SEVERE",1000, defaultBundle);
public static final Level WARNING = new Level("WARNING", 900, defaultBundle);
public static final Level INFO = new Level("INFO", 800, defaultBundle);
public static final Level CONFIG = new Level("CONFIG", 700, defaultBundle);
public static final Level FINE = new Level("FINE", 500, defaultBundle);
public static final Level FINER = new Level("FINER", 400, defaultBundle);
public static final Level FINEST = new Level("FINEST", 300, defaultBundle);
public static final Level ALL = new Level("ALL", Integer.MIN_VALUE, defaultBundle);
```

## 1.3 自定义配置说明

​		默认读取配置jre/lib/logging.properties，具体说明：

```properties
# RootLogger使用的处理器，如果想添加多个，则用逗号割开
handlers= java.util.logging.ConsoleHandler

# RootLogger日志级别，默认INFO
.level= INFO

# 文件处理器属性
# 日志文件位置，%h为当前用户根路径，%u为第几个文件，从0开始
java.util.logging.FileHandler.pattern = %h/java%u.log
# 限制单个文件大小为50000字节
java.util.logging.FileHandler.limit = 50000
# 限制日志文件数1个
java.util.logging.FileHandler.count = 1
# 日志格式，默认XML
java.util.logging.FileHandler.formatter = java.util.logging.XMLFormatter
# 新日志追加到文件内，而不是覆盖，默认false
java.util.logging.FileHandler.append = true

# 控制台输出属性
# 控制台日志级别默认INFO
java.util.logging.ConsoleHandler.level = INFO
# 控制台日志格式
java.util.logging.ConsoleHandler.formatter = java.util.logging.SimpleFormatter

# 自定义某个包的日志级别、处理器等
com.iefihz.logger.level = CONFIG
com.iefihz.logger.handlers = java.util.logging.FileHandler
com.iefihz.logger.useParentHandlers = false
```

# 2.Log4j

## 2.1 简介

​		Log4j主要由Logger（日志记录器）、Appender（输出控制器）和Layout（日志格式化器）组成。

## 2.2 依赖管理

```xml
<!-- log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
<!-- 若持久化到数据库 -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.49</version>
</dependency>
```

## 2.3 组件

| 组件                   | 描述                                                         |
| ---------------------- | ------------------------------------------------------------ |
| Logger（日志记录器）   | 负责日志收集，log4j1.2版本后，Logger类取代了Category类。对于熟悉早期版本的开发人员来说，可把Logger类视为Category类。 |
| Appender（输出控制器） | 指定日志输出方式，可输出到控制台（ConsoleAppender）、单一文件（FileAppender）、按文件大小拆分日志（RollingFileAppender）、按日志时间拆分日志（DailyRollingFileAppender）、数据库（JDBCAppender）。 |
| Layout（日志格式化器） | 指定日志格式，常用格式：HTML样式（HTMLLayout）、简单格式（SimpleLayout默认只打印INFO级别信息）、自定义格式（PatternLayout） |

​		Log4j的日志级别，可在org.apache.log4j.Level中查看，日志级别越高，显示的信息越少：

```java
public static final Level OFF = new Level(2147483647, "OFF", 0);
public static final Level FATAL = new Level(50000, "FATAL", 0);
public static final Level ERROR = new Level(40000, "ERROR", 3);
public static final Level WARN = new Level(30000, "WARN", 4);
public static final Level INFO = new Level(20000, "INFO", 6);
public static final Level DEBUG = new Level(10000, "DEBUG", 7);
public static final Level TRACE = new Level(5000, "TRACE", 7);
public static final Level ALL = new Level(-2147483648, "ALL", 7);
```

## 2.4 自定义配置说明

​		在resources下创建log4j.xml或者log4j.properties，如果都存在，则以xml为准。如下为PatternLayout自定义日志输出格式说明：

```
%c	输出Logger全名，通常为类全名。假如Logger定义名为aaa.bbb，%c{1}会输出bbb，%c{2}会输出aaa.bbb。
%C	输出Java类全名。假如类名为com.iefihz.logger.TestLogger，%C{1}会输出TestLogger。
%d	输出服务器当前时间，默认格式ISO8601。可通过如%d{yyyy年MM月dd日 HH:mm:ss.SSS}指定。
%F	输出Java源文件名。
%l	输出日志事件的发生位置，包括类目名、发生的线程，以及在代码中的行数。举例：Testlog4.main(Test Log4.java:10)。
%L	输出代码中的行号。
%m	输出代码中指定的日志信息。
%M	输出日志打印位置的方法名。
%n	换行符。
%p	输出日志优先级，如：INFO、DEBUG等。
%r	输出从应用启动后到日志输出时经过的毫秒数。
%t	输出产生该日志的线程名。
%%	输出字符%。
```

​		通过可选格式修饰符修改每个数据字段的最小和最大宽度以及对齐方式，其放置在百分号和转换字符或单词之间，如下所示：

| Format modifier | Left justify | Minimum width | Maximum width | Comment                                                      |
| --------------- | ------------ | ------------- | ------------- | ------------------------------------------------------------ |
| %20c            | false        | 20            | none          | logger名长度小于20，左边用空格补全。                         |
| %-20c           | true         | 20            | none          | logger名长度小于20，右边用空格补全。                         |
| %.30c           | NA           | none          | 30            | logger名长度大于30，从开始把超出部分截取掉。                 |
| %20.30c         | false        | 20            | 30            | logger名长度小于20，左边用空格补全。但是，logger名长度大于30，从开始把超出部分截取掉。 |
| %-20.30c        | true         | 20            | 30            | logger名长度小于20，右边用空格补全。但是，logger名长度大于30，从开始把超出部分截取掉。 |

### 2.4.1 log4j.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE log4j:configuration SYSTEM "log4j.dtd">
<log4j:configuration xmlns:log4j='http://jakarta.apache.org/log4j/' >
    <!-- 控制台输出 -->
    <appender name="console" class="org.apache.log4j.ConsoleAppender">
        <param name="target" value="System.out"/>
        <layout class="org.apache.log4j.PatternLayout">
            <param name="conversionPattern" value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%-5p] %t [%c] - %m%n"/>
        </layout>
        <param name="encoding" value="UTF-8"/>
    </appender>

    <!-- FileAppender示例 -->
    <appender name="exampleFile" class="org.apache.log4j.FileAppender">
        <!-- 日志文件保存位置 -->
        <param name="file" value="logs/log4j-exampleFile.log"/>
        <!-- 保存的日志级别，包含设置在内以及更高等级的日志。不指定则使用rootLogger设置的日志级别 -->
        <param name="threshold" value="info"/>
        <!-- 指定日志格式化器 -->
        <layout class="org.apache.log4j.PatternLayout">
            <!-- 日志格式 -->
            <param name="conversionPattern" value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%-5p] %t [%c] - %m%n"/>
        </layout>
        <!-- 是否以追加的方式记录日志，默认也是true -->
        <param name="append" value="true"/>
        <!-- 如果日志中有中文，设置编码 -->
        <param name="encoding" value="UTF-8"/>
    </appender>

    <!-- RollingFileAppender示例 -->
    <appender name="exampleRollingFile" class="org.apache.log4j.RollingFileAppender">
        <param name="file" value="logs/log4j-exampleRollingFile.log"/>
        <param name="threshold" value="info"/>
        <layout class="org.apache.log4j.PatternLayout">
            <param name="conversionPattern" value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%-5p] %t [%c] - %m%n"/>
        </layout>
        <param name="append" value="true"/>
        <param name="encoding" value="UTF-8"/>
        <!-- 独有项 -->
        <!-- 文件大小(KB/MB/GB) -->
        <param name="maxFileSize" value="1KB"/>
        <!-- 文件备份数量（文件备份数量超过指定数量后，则新的日志覆盖最旧的那个日志文件） -->
        <param name="maxBackupIndex" value="5"/>
    </appender>

    <!-- DailyRollingFileAppender示例 -->
    <appender name="exampleDailyRollingFile" class="org.apache.log4j.DailyRollingFileAppender">
        <param name="file" value="logs/log4j-exampleDailyRollingFile.log"/>
        <param name="threshold" value="info"/>
        <layout class="org.apache.log4j.PatternLayout">
            <param name="conversionPattern" value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%-5p] %t [%c] - %m%n"/>
        </layout>
        <param name="append" value="true"/>
        <param name="encoding" value="UTF-8"/>
        <!-- 独有项 -->
        <!-- 示例按照秒来拆分，在实际中通常按照天来拆分 -->
        <param name="datePattern" value="'.'yyyy-MM-dd HH_mm_ss"/>
    </appender>

    <!-- 持久化到数据库，数据库的那几个连接属性要注意大小写 -->
    <appender name="logDB" class="org.apache.log4j.jdbc.JDBCAppender">
        <layout class="org.apache.log4j.PatternLayout"/>
        <param name="threshold" value="error"/>
        <param name="Driver" value="com.mysql.jdbc.Driver"/>
        <param name="URL" value="jdbc:mysql://localhost:3306/logger-demo"/>
        <param name="User" value="root"/>
        <param name="Password" value="123456"/>
        <param name="Sql" value="INSERT INTO t_log4j_logs (project_name, create_time, level, thread, category, filename, message) VALUES ('log4j', '%d{yyyy-MM-dd HH:mm:ss}', '%p', '%t', '%c', '%F', '%m')"/>
    </appender>

    <!-- 自定义Logger之AccessLogger -->
    <appender name="accessLogger" class="org.apache.log4j.FileAppender">
        <param name="file" value="logs/log4j-accessLogger.log"/>
        <param name="threshold" value="info"/>
        <layout class="org.apache.log4j.PatternLayout">
            <param name="conversionPattern" value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%-5p] %t [%c] - %m%n"/>
        </layout>
        <param name="append" value="true"/>
        <param name="encoding" value="UTF-8"/>
    </appender>

    <!-- 避免向上传递，导致rootLogger也处理一遍这个日志，注意logger名称和appender名称的区别 -->
    <logger name="AccessLogger" additivity="false">
        <level value="info"/>
        <appender-ref ref="console"/>
        <appender-ref ref="accessLogger" />
    </logger>

    <root>
        <level value="trace"/>
        <appender-ref ref="console"/>
<!--        <appender-ref ref="exampleFile"/>-->
<!--        <appender-ref ref="exampleRollingFile"/>-->
        <appender-ref ref="exampleDailyRollingFile"/>
<!--        <appender-ref ref="logDB"/>-->
    </root>

</log4j:configuration>
```

### 2.4.2 log4j.properties

```properties
### eg: [level], appenderName1, appenderName2, ... ###
log4j.rootLogger=trace, console, logDB

### 控制台输出 ###
log4j.appender.console=org.apache.log4j.ConsoleAppender
log4j.appender.console.target=System.out
log4j.appender.console.layout=org.apache.log4j.PatternLayout
log4j.appender.console.layout.conversionPattern=%d{yyyy-MM-dd HH:mm:ss.SSS} [%-5p] %t [%c] - %m%n
log4j.appender.console.encoding=UTF-8

### FileAppender示例 ###
log4j.appender.exampleFile=org.apache.log4j.FileAppender
# 日志文件保存位置
log4j.appender.exampleFile.file=logs/log4j-exampleFile.log
# 保存的日志级别，包含设置在内以及更高等级的日志。不指定则使用第一行设置的日志级别
log4j.appender.exampleFile.threshold=info
# 指定日志格式化器
log4j.appender.exampleFile.layout=org.apache.log4j.PatternLayout
# 日志格式
log4j.appender.exampleFile.layout.conversionPattern=%d{yyyy-MM-dd HH:mm:ss.SSS} [%-5p] %t [%c] - %m%n
# 是否以追加的方式记录日志，默认也是true
log4j.appender.exampleFile.append=true
# 如果日志中有中文，设置编码
log4j.appender.exampleFile.encoding=UTF-8

### RollingFileAppender示例 ###
log4j.appender.exampleRollingFile=org.apache.log4j.RollingFileAppender
log4j.appender.exampleRollingFile.file=logs/log4j-exampleRollingFile.log
log4j.appender.exampleRollingFile.threshold=info
log4j.appender.exampleRollingFile.layout=org.apache.log4j.PatternLayout
log4j.appender.exampleRollingFile.layout.conversionPattern=%d{yyyy-MM-dd HH:mm:ss.SSS} [%-5p] %t [%c] - %m%n
log4j.appender.exampleRollingFile.append=true
log4j.appender.exampleRollingFile.encoding=UTF-8
# 独有项
# 文件大小(KB/MB/GB)
log4j.appender.exampleRollingFile.maxFileSize=1KB
# 文件备份数量（文件备份数量超过指定数量后，则新的日志覆盖最旧的那个日志文件）
log4j.appender.exampleRollingFile.maxBackupIndex=5

### DailyRollingFileAppender示例 ###
log4j.appender.exampleDailyRollingFile=org.apache.log4j.DailyRollingFileAppender
log4j.appender.exampleDailyRollingFile.file=logs/log4j-exampleDailyRollingFile.log
log4j.appender.exampleDailyRollingFile.threshold=info
log4j.appender.exampleDailyRollingFile.layout=org.apache.log4j.PatternLayout
log4j.appender.exampleDailyRollingFile.layout.conversionPattern=%d{yyyy-MM-dd HH:mm:ss.SSS} [%-5p] %t [%c] - %m%n
log4j.appender.exampleDailyRollingFile.append=true
log4j.appender.exampleDailyRollingFile.encoding=UTF-8
# 独有项
# 示例按照秒来拆分，在实际中通常按照天来拆分
log4j.appender.exampleDailyRollingFile.datePattern='.'yyyy-MM-dd HH_mm_ss

### 持久化到数据库，数据库的那几个连接属性要注意大小写 ###
log4j.appender.logDB=org.apache.log4j.jdbc.JDBCAppender
log4j.appender.logDB.layout=org.apache.log4j.PatternLayout
log4j.appender.logDB.threshold=error
log4j.appender.logDB.Driver=com.mysql.jdbc.Driver
log4j.appender.logDB.URL=jdbc:mysql://localhost:3306/logger-demo
log4j.appender.logDB.User=root
log4j.appender.logDB.Password=123456
log4j.appender.logDB.Sql=INSERT INTO t_log4j_logs (project_name, create_time, level, thread, category, filename, message) VALUES ('log4j', '%d{yyyy-MM-dd HH:mm:ss}', '%p', '%t', '%c', '%F', '%m')

### 自定义logger，格式：log4j.logger.logger名称=[level], appenderName1, appenderName2, ... ###
### mybatis显示sql ###
#log4j.logger.com.ibatis=debug
#log4j.logger.com.ibatis.common.jdbc.SimpleDataSource=debug
#log4j.logger.com.ibatis.common.jdbc.ScriptRunner=debug
#log4j.logger.com.ibatis.sqlmap.engine.impl.SqlMapClientDelegate=debug
#
#log4j.logger.java.sql.Connection=debug
#log4j.logger.java.sql.Statement=debug
#log4j.logger.java.sql.PreparedStatement=debug

### 自定义Logger之AccessLogger ###
log4j.logger.AccessLogger=info, console, accessLogger
# 避免向上传递，导致rootLogger也处理一遍这个日志，注意是log4j.additivity.logger名，不是appenderName
log4j.additivity.AccessLogger=false
log4j.appender.accessLogger=org.apache.log4j.FileAppender
log4j.appender.accessLogger.file=logs/log4j-accessLogger.log
log4j.appender.accessLogger.threshold=info
log4j.appender.accessLogger.layout=org.apache.log4j.PatternLayout
log4j.appender.accessLogger.layout.conversionPattern=%d{yyyy-MM-dd HH:mm:ss.SSS} [%-5p] %t [%c] - %m%n
log4j.appender.accessLogger.append=true
log4j.appender.accessLogger.encoding=UTF-8
```

# 3.Log4j2

## 3.1 简介

​		Apache Log4j 2是对Log4j的升级，它比其前身Log4j 1.x 提供了重大改进，并提供了Logback中可用的许多改进，同时修复了Logback架构中的一些问题。被誉为是目前最优秀的Java日志框架。

​		另外，Log4j2日志级别（默认ERROR）和Log4j 1.x的一样，日志格式参考Logback，这里均不再赘述。

## 3.2 特征

- 性能提升：Log4j2包含基于LMAX Disuptor库的下一代异步记录器。在多线程场景中，异步记录器的吞吐量比Log4j 1.x和Logback高18倍，延迟低。

- 自动重新加载配置：与Logback一样，Log4j2 可以在修改时自动重新加载其配置。与Logback不同，它会在

  重新配置发生时不会丢失日志事件。

- 高级过滤：与Logback一样，Log4j2 支持基于Log事件中的上下文数据，标记，正则表达式和其他组件进行过滤。此外，过滤器还可以与记录器关联。与Logback不同，Log4j2 可以在任何这些情况下使用通用的Filter类。
- 插件架构：Log4j使用插件模式配置组件。因此，您无需编写代码来创建和配置Appender，Layout，Pattern Converter等。在配置了的情况下，Log4j 自动识别插件并使用它们。
- 无垃圾机制：在稳态日志记录期间，Log4j2在独立应用程序中是无垃圾的，在Web应用程序中是低垃圾。这减少了垃圾收集器的压力，并且可以提供更好的响应性能。

​		目前市面上最主流的日志门面就是SLF4J，虽然Log4j2也是日志门面，因为它的日志实现功能非常强大，性能优越。所以我们一般情况下还是将Log4j2 看作是日志的实现。SLF4j + Log4j2 的组合，是市场上最强大的日志功能实现方式，绝对是未来的主流趋势。

## 3.3 异步日志

​		异步日志是log4j2最大的特色，其性能的提升主要也是从异步日志中受益。
​		Log4j2提供了两种实现日志的方式，一个是通过AsyncAppender，一个是通过AsyncLogger，分别对应前面我们说的Appender组件和Logger组件。
​		注意这是两种不同的实现方式，在设计和源码上都是不同的体现。

1. AsyncAppender方式

​		是通过引用别的Appender来实现的，当有日志事件到达时，会开启另外一个线程来处理它们。需要注意的是，如果在Appender的时候出现异常，对应用来说是无法感知的。AsyncAppender应该在它引用的Appender之后配置，默认使用java.util.concurrent.ArrayBlockingQueue实现，而不需要其它外部的类库。当使用此Appender的时候，在多线程的环境下需要注意，阻塞队列容易受到锁争用的影响，这可能会对性能产生影响。这时候，我们应该者虑使用无锁的异步记录器(AsyncLogger)。

2. AsyncLogger方式

​		AsyncLogger才是log4j2实现异步最重要的功能体现，也是官方推荐的异步方式。它可以使得调用Logger.log返回的更快。你可以有两种选择：全局异步和混合异步。
​		全局异步：所有的日志都异步的记录，在配置文件上不用做任何改动，只需要在jvm启动的时候增加一个参数即可实现。
​		混合异步：你可以在应用中同时使用同步日志和异步日志，这使得日志的配置方式更加灵活。虽然Log4j2提供以一套异常处理机制，可以覆盖大部分的状态，但是还是会有一小部分的特殊情况是无法完全处理的，比如我们如果是记录审计日志(特殊情况之一)，那么官方就推荐使用同步日志的方式，而对于其他的一些仅仅是记录一个程序日志的地方，使用异步日志将大幅提升性能，减少对应用本身的影响。混合异步的方式需要通过修改配置文件来实现，使用AsyncLogger标记配置。

## 3.4 依赖管理

- 仅使用log4j2

```xml
<dependencies>
    <!-- 升级到新版本，如：2.17.0，log2j-2.X ~ log2j-2.14.1 均存在漏洞 -->
	<!-- log4j2日志门面 -->
	<dependency>
		<groupId>org.apache.logging.log4j</groupId>
		<artifactId>log4j-api</artifactId>
		<version>2.17.0</version>
	</dependency>
	<!-- log4j2日志实现 -->
	<dependency>
		<groupId>org.apache.logging.log4j</groupId>
		<artifactId>log4j-core</artifactId>
		<version>2.17.0</version>
	</dependency>
</dependencies>
```

- 使用 SLF4j + Log4j2 的组合（包含异步日志依赖）

```xml
<dependencies>
    <!-- log4j2.x需要升级到新版本，如：2.17.0，log2j-2.X ~ log2j-2.14.1 均存在漏洞 -->
    <!-- slf4j日志门面的核心依赖 -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.25</version>
    </dependency>
    <!-- log4j2适配器 -->
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-slf4j-impl</artifactId>
        <version>2.17.0</version>
    </dependency>
    <!-- log4j2日志门面 -->
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-api</artifactId>
        <version>2.17.0</version>
    </dependency>
    <!-- log4j2日志实现 -->
    <dependency>
        <groupId>org.apache.logging.log4j</groupId>
        <artifactId>log4j-core</artifactId>
        <version>2.17.0</version>
    </dependency>
    <!-- 异步日志依赖 -->
    <dependency>
        <groupId>com.lmax</groupId>
        <artifactId>disruptor</artifactId>
        <version>3.4.4</version>
    </dependency>
</dependencies>
```

## 3.5 自定义配置说明

​		日志格式参考Logback，示例log4j2.xml：

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<Configuration>
    <!-- Configuration标签中status="debug" monitorInterval="5"前者表示日志框架自身的日志输出级别，后者自动加载配置文件间隔时间不低于5秒 -->
    <!-- 注意：log4j2的配置文件标签首字母需要大写，除了properties标签 -->

    <!-- 可引用参数配置 -->
    <properties>
        <!-- 配置日志文件的输出路径，通常用绝对路径，这里为了方便管理而用先对路径 -->
        <property name="logDir">./logs</property>
        <!-- 设置日志格式 -->
        <property name="pattern">%d{yyyy-MM-dd HH:mm:ss.SSS} [%-5level] %thread [%logger] - %m%n</property>
    </properties>

    <!-- 配置appender -->
    <Appenders>
        <!-- 控制台appender -->
        <Console name="consoleAppender" target="SYSTEM_OUT">
            <PatternLayout charset="UTF-8" pattern="${pattern}"/>

            <!-- 过滤器，控制台只输出level及其以上级别的信息(onMatch) ，其他的直接拒绝(onMismatch) -->
            <!--<ThresholdFilter level="info" onMatch="ACCEPT" onMismatch="DENY"/>-->
        </Console>

        <!-- 普通配置文件appender -->
        <File name="fileAppender" fileName="${logDir}/log4j2.log">
            <!-- 日志格式 -->
            <PatternLayout charset="UTF-8" pattern="${pattern}"/>
        </File>

        <!-- 按照指定规则拆分文件appender，fileName日志文件名字，filePattern日志文件拆分后命名规则（这里$$可引用作路径） -->
        <RollingFile name="rollingFileAppender" fileName="${logDir}/rollingFile.log"
                     filePattern="${logDir}/$${date:yyyy-MM-dd}/rollingFile.%d{yyyy-MM-dd_HH-mm}.part%i.log">
            <!-- 日志格式 -->
            <PatternLayout charset="UTF-8" pattern="${pattern}"/>
            <Policies>
                <!-- 系统启动时，触发拆分规则 -->
                <OnStartupTriggeringPolicy/>
                <!-- 按照文件大小拆分 -->
                <SizeBasedTriggeringPolicy size="10KB"/>
                <!-- 按照时间节点进行拆分，规则在RollingFile标签的filePattern已经给出 -->
                <TimeBasedTriggeringPolicy/>
            </Policies>
            <!-- 文件个数的限制（%i最大值，从1开始），超出这个数值，则覆盖从最旧的开始覆盖 -->
            <DefaultRolloverStrategy max="30"/>
        </RollingFile>

        <!-- 异步日志appender -->
        <!--<Async name="asyncAppender">
            &lt;!&ndash; 引用appender &ndash;&gt;
            <AppenderRef ref="consoleAppender"/>
        </Async>-->
    </Appenders>

    <!-- 配置logger -->
    <Loggers>
        <!-- 自定义异步日志logger，includeLocation="false"去掉行号，行号影响效率；additivity="false"不继承root logger； -->
        <AsyncLogger name="com.iefihz.log4j2" level="trace" includeLocation="false" additivity="false">
            <!-- 引用appender -->
            <AppenderRef ref="consoleAppender"/>
        </AsyncLogger>

        <!-- 配置root logger -->
        <Root level="trace">
            <!-- 引用具体appender -->
            <AppenderRef ref="consoleAppender"/>
            <!--<AppenderRef ref="fileAppender"/>-->
            <!--<AppenderRef ref="rollingFileAppender"/>-->
            <!--<AppenderRef ref="asyncAppender"/>-->
        </Root>
    </Loggers>

</Configuration>
```

# 4.Logback

## 4.1 简介

主要的三个模块：logback-core, logback-classic, logback-access

1. logback-core：核心基础模块，其他两个模块的基础。
2. logback-classic：log4j 1.x的一个改良进化版本。其完整实现了SLF4J API，便于与其他日志框架来回切换，如log4j 1.x或java.util.logging(JUL)。
3. logback-access：访问模块与Servlet容器集成提供通过http来访问日志的功能。

## 4.2 依赖管理

```xml
<!-- slf4j核心依赖 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.25</version>
</dependency>
<!-- logback依赖，导入logback-classic，由于传递性也会导入logback-core -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.2.11</version>
</dependency>
```

## 4.3 组件

| 组件                   | 描述                                                         |
| ---------------------- | ------------------------------------------------------------ |
| Logger（日志记录器）   | 存放日志对象、定义日志类型和日志级别                         |
| Appender（输出控制器） | 指定日志输出方式，可输出到控制台（ConsoleAppender）、单一文件（FileAppender）、Html文件、可归档文件（RollingFileAppender）等等。 |
| Layout（日志格式化器） | 格式化日志输出信息，在Logback中，Layout对象被封装在encoder中。也就是说，在配置使用中的encoder可以看作成Layout。 |

​		Logback的日志级别，日志级别越高，显示的信息越少：OFF > ERROR > WARN > INFO > DEBUG（默认） > TRACE > ALL 。

## 4.4 自定义配置说明

​		在resources下创建logback.xml或者logback-test.xml（结合多环节配置使用）或者logback.groovy等。如下为自定义日志输出格式说明，大体上与log4j 1.x的配置含义类似，更多查看官网 https://logback.qos.ch/manual/layouts.html#ClassicPatternLayout

| 字符（使用时，前面要加百分号，如%logger）,斜杠/代表或者的意思。 | 含义                                                         |
| ------------------------------------------------------------ | ------------------------------------------------------------ |
| c{length} / lo{length} / logger{length}                      | 输出logger名称，可指定长度，如%logger{length}，超过这个长度，左边的会被缩写（被缩写的那一段只有一个字符），如：<br/>%logger{10}，logger名为mainPackage.sub.sample.Bar缩写后为m.s.s.Bar<br/>%logger{16}，logger名为mainPackage.sub.sample.Bar缩写后为m.sub.sample.Bar。 |
| C{length} / class{length}                                    | 输出类全名称，可指定长度，与%logger{length}类似。            |
| d                                                            | 输出服务器当前时间，默认格式ISO8601。可通过如%d{yyyy年MM月dd日 HH:mm:ss.SSS}指定。 |
| F / file                                                     | 输出Java源文件名。                                           |
| L / line                                                     | 输出代码中的行号。                                           |
| m / msg / message                                            | 输出代码中指定的日志信息。                                   |
| M / method                                                   | 输出日志打印位置的方法名。                                   |
| n                                                            | 换行符。                                                     |
| p / le / level                                               | 输出日志优先级，如：INFO、DEBUG等。                          |
| r / relative                                                 | 输出从应用启动后到日志输出时经过的毫秒数。                   |
| t / thread                                                   | 输出产生该日志的线程名。                                     |
| %                                                            | 输出字符%。                                                  |

​		通过可选格式修饰符修改每个数据字段的最小和最大宽度以及对齐方式，其放置在百分号和转换字符或单词之间，如下所示：

| Format modifier | Left justify | Minimum width | Maximum width | Comment                                                      |
| --------------- | ------------ | ------------- | ------------- | ------------------------------------------------------------ |
| %20logger       | false        | 20            | none          | logger名长度小于20，左边用空格补全。                         |
| %-20logger      | true         | 20            | none          | logger名长度小于20，右边用空格补全。                         |
| %.30logger      | NA           | none          | 30            | logger名长度大于30，从开始把超出部分截取掉。                 |
| %20.30logger    | false        | 20            | 30            | logger名长度小于20，左边用空格补全。但是，logger名长度大于30，从开始把超出部分截取掉。 |
| %-20.30logger   | true         | 20            | 30            | logger名长度小于20，右边用空格补全。但是，logger名长度大于30，从开始把超出部分截取掉。 |
| %.-30logger     | NA           | none          | 30            | logger名长度大于30，从末端把超出部分截取掉。如%.-1level只显示日志级别的首字母T , D , I , W , E。 |

​		示例logback.xml：

```xml
<?xml version="1.0" encoding="UTF-8"?>
<configuration>
    <!-- property为自定义变量，可通过${}获取 -->
    <!-- 设置日志格式 -->
    <property name="pattern" value="%d{yyyy-MM-dd HH:mm:ss.SSS} [%-5level] %thread [%logger] - %m%n"/>
    <!-- 设置html日志文件格式（把多余的无语义字符串和换行符去掉，否则多出几列难看的分隔符） -->
    <property name="pattern4Html" value="%d{yyyy-MM-dd HH:mm:ss.SSS}%-5level%thread%logger%m"/>
    <!-- 配置日志文件的输出路径，通常用绝对路径，这里为了方便管理而用先对路径 -->
    <property name="logDir" value="./logs"/>

    <!-- 配置控制台appender -->
    <appender name="consoleAppender" class="ch.qos.logback.core.ConsoleAppender">
        <!-- 日志输出目标：System.out（黑色，默认）；System.err（红色） -->
        <target>System.out</target>
        <!-- 配置日志输出格式 -->
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!-- 配置格式 -->
            <pattern>${pattern}</pattern>
            <!-- 配置字符编码 -->
            <charset>UTF-8</charset>
        </encoder>

        <!-- 配置过滤器（细粒度的日志输出） -->
        <!--<filter class="ch.qos.logback.classic.filter.LevelFilter">
            &lt;!&ndash; 设置日志输出级别 &ndash;&gt;
            <level>INFO</level>
            &lt;!&ndash; ACCEPT:等于level中设置的级别，则打印日志 &ndash;&gt;
            <onMatch>ACCEPT</onMatch>
            &lt;!&ndash; DENY:不等于level中设置的级别，则屏蔽日志 &ndash;&gt;
            <onMismatch>DENY</onMismatch>
        </filter>-->
    </appender>

    <!-- 对控制台进行异步日志输出，输出到文件的不再作演示，生产环境建议配异步日志 -->
    <appender name="asyncAppender" class="ch.qos.logback.classic.AsyncAppender">
        <!-- 需要异步的appender -->
        <appender-ref ref="consoleAppender"/>
        <!-- 队列剩余容量小于discardingThreshold，则会丢弃TRACT、DEBUG、INFO级别的日志；默认值-1，为queueSize的20%；0不丢失日志 -->
        <discardingThreshold>0</discardingThreshold>
        <!-- 队列的最大容量，该值会影响性能，默认值为256 -->
        <queueSize>256</queueSize>
    </appender>

    <!-- 配置文件appender，默认是追加的方式 -->
    <appender name="fileAppender" class="ch.qos.logback.core.FileAppender">
        <!-- 配置日志保存文件 -->
        <file>${logDir}/logback.log</file>
        <!-- 配置日志输出格式 -->
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!-- 配置格式 -->
            <pattern>${pattern}</pattern>
            <!-- 配置字符编码 -->
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <!-- 配置html文件appender -->
    <appender name="htmlFileAppender" class="ch.qos.logback.core.FileAppender">
        <!-- 配置日志保存文件 -->
        <file>${logDir}/logback.html</file>
        <!-- 配置日志输出格式 -->
        <encoder class="ch.qos.logback.core.encoder.LayoutWrappingEncoder">
            <layout class="ch.qos.logback.classic.html.HTMLLayout">
                <!-- 配置格式 -->
                <pattern>${pattern4Html}</pattern>
            </layout>
            <!-- 配置字符编码 -->
            <charset>UTF-8</charset>
        </encoder>
    </appender>

    <!-- 配置可拆分归档文件appender -->
    <appender name="rollingFileAppender" class="ch.qos.logback.core.rolling.RollingFileAppender">
        <!-- 配置日志保存文件 -->
        <file>${logDir}/logback-rolling.log</file>
        <!-- 配置日志输出格式 -->
        <encoder class="ch.qos.logback.classic.encoder.PatternLayoutEncoder">
            <!-- 配置格式 -->
            <pattern>${pattern}</pattern>
            <!-- 配置字符编码 -->
            <charset>UTF-8</charset>
        </encoder>
        <!-- 配置拆分规则 -->
        <rollingPolicy class="ch.qos.logback.core.rolling.SizeAndTimeBasedRollingPolicy">
            <!-- 按照时间和压缩格式指定文件名，压缩格式为gz（若不想压缩则把.gz去掉，gz会产生很多tmp文件） -->
            <fileNamePattern>${logDir}/logback-rolling.%d{yyyy-MM-dd}.part%i.log.gz</fileNamePattern>
            <!-- 单文件大小 -->
            <maxFileSize>1KB</maxFileSize>
            <!-- 启动时扫描清除过期日志 -->
            <cleanHistoryOnStart>true</cleanHistoryOnStart>
            <!-- 总文件大小，超出则删除旧日志 -->
            <totalSizeCap>20GB</totalSizeCap>
            <!-- 保留最近n天日志 -->
            <maxHistory>90</maxHistory>
        </rollingPolicy>
    </appender>

    <!-- 自定义logger，additivity="false"表示不向上传递日志，避免root logger不会重复处理日志 -->
    <logger name="AccessLogger" level="INFO" additivity="false">
        <appender-ref ref="consoleAppender"/>
    </logger>

    <!-- 配置root logger -->
    <root level="ALL">
<!--        <appender-ref ref="consoleAppender"/>-->
<!--        <appender-ref ref="fileAppender"/>-->
<!--        <appender-ref ref="htmlFileAppender"/>-->
        <appender-ref ref="rollingFileAppender"/>
        <appender-ref ref="asyncAppender"/>
    </root>

</configuration>
```

# 5.门面技术

​		作用：解决多个日志框架需要单独维护各自api的问题，降低日志框架与应用程序的耦合性。有了日志门面技术后，对于程序员来说，无论底层使用哪种日志框架，应用程序不需要修改任何代码即可完成切换。
## 5.1 JCL

- JCL全称为Jakarta Commons Logging，是Apache提供的一个通用日志API。有两个关键抽象类Log（日志记录器），LogFactory（日志工厂，负责Log实例的创建）。Log主要实现有四个，优先级由高到低如下所示：


```
org.apache.commons.logging.impl.Log4JLogger      （log4j的实现）
org.apache.commons.logging.impl.Jdk14Logger（jul的实现）
org.apache.commons.logging.impl.Jdk13LumberjackLogger    （jdk1.3以前的jul实现）
org.apache.commons.logging.impl.SimpleLog    （jcl自带的简单实现）
```

- 优点：

1. 面向接口开发，不需要依赖具体的实现类（原理分析请看测试代码），减低代码耦合度。

2. 可根据需求，灵活切换日志框架。
3. 统一API，方便学习和维护。

- 依赖


```
<dependency>
    <groupId>commons-logging</groupId>
    <artifactId>commons-logging</artifactId>
    <version>1.2</version>
</dependency>
```

- 源码分析，查看各个日志框架之间切换的原理

```
LogFactory.getLog(JCLTest.class);
======================>
getFactory().getInstance(clazz);
======================>
org.apache.commons.logging.impl.LogFactoryImpl#getInstance(java.lang.String)
======================>
instance = this.newInstance(name);
======================>
instance = this.discoverLogImplementation(name);
======================>
for(int i = 0; i < classesToDiscover.length && result == null; ++i) {
    result = this.createLogFromClass(classesToDiscover[i], logCategory, true);
}
由此可以看出各个实现类的优先级。
======================>
c = Class.forName(logAdapterClassName, true, currentCL);
constructor = c.getConstructor(this.logConstructorSignature);
Object o = constructor.newInstance(params);
通过反射创建Log对应不同日志框架实现的实例，如果没有导入log4j的依赖，在通过反射创建时，会提示ClassNotFoundException，从而实现日志种类的切换。源码中的classesToDiscover数组是写死了日志实现类的种类的，找不到会继续遍历。
```

## 5.2 SLF4J

### 5.2.1 概述

- 简单日志门面(Simple Logging Facade For Java) SLF4J主要是为了给Java日志访问提供一套标准、规范的API框架，其主要意义在于提供接口，具体的实现可以交给其他日志框架，例如log4j和logback等。当然SLF4J自己也提供了功能较为简单的实现，但是一般很少用到。对于一般的Java项目而言，日志框架会选择slf4j-api作为门面，配上具体的实现框架（log4j、logback等），中间使用桥接器完成桥接。所以我们可以得出SLF4J最重要的两个功能就是对日志框架的绑定和日志框架的桥接。


- 桥接技术：通常，我们依赖的某些组件依赖于SLF4J以外的日志API。我们可能还假设这些组件在不久的将来不会切换到SLF4J。为了避免这种情况，SLF4J附带了几个桥接模块，这些模块会将对log4j，JUL和JCL的API调用进行重定向，就好像是对SLF4J API进行操作一样。


- 常见的日志框架有JUL、log4j、logback、log4j2，常见的日志门面JCL、SLF4J。出现的先后顺序 log4j --> JUL --> JCL --> SLF4J --> logback --> log4j2。因此，在使用SLF4J时，晚于SLF4J出现的技术（slf4j-simple、logback-classic和logback-core、slf4j-nop）遵循了SLF4J的API标准，不需要使用适配器，而早于SLF4J出现的日志框架需增加适配器，具体对应关系：


| 使用的日志框架 | 适配器依赖包      |
| -------------- | ----------------- |
| log4j          | slf4j-log4j12.jar |
| JUL            | slf4j-jdk14.jar   |

- SLF4J门面通用的五个日志级别，由高到低：error、warn、info（默认）、debug、trace。

### 5.2.2 依赖管理（不需要适配器）

1. SLF4J 结合 slf4j-simple 依赖

```xml
<!-- slf4j核心依赖 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.25</version>
</dependency>
<!-- slf4j自带的简单日志实现 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-simple</artifactId>
    <version>1.7.25</version>
</dependency>
```

2. SLF4J 结合 logback 依赖

```xml
<!-- slf4j核心依赖 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.25</version>
</dependency>
<!-- logback依赖，导入logback-classic，由于传递性也会导入logback-core -->
<dependency>
    <groupId>ch.qos.logback</groupId>
    <artifactId>logback-classic</artifactId>
    <version>1.2.11</version>
</dependency>
```

3. SLF4J 结合 slf4j-nop 依赖

```xml
<!-- slf4j核心依赖 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.25</version>
</dependency>
<!-- slf4j-nop表示不记录日志 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-nop</artifactId>
    <version>1.7.25</version>
</dependency>
```

### 5.2.3 依赖管理（需要适配器）

1. SLF4J 结合 log4j 依赖（别忘了要添加log4j配置文件）

```xml
<!-- slf4j核心依赖 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.25</version>
</dependency>
<!-- log4j -->
<dependency>
    <groupId>log4j</groupId>
    <artifactId>log4j</artifactId>
    <version>1.2.17</version>
</dependency>
<!-- log4j适配器依赖 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-log4j12</artifactId>
    <version>1.7.25</version>
</dependency>
```

2. SLF4J 结合 JUL 依赖

```xml
<!-- slf4j核心依赖 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-api</artifactId>
    <version>1.7.25</version>
</dependency>
<!-- JUL适配器 -->
<dependency>
    <groupId>org.slf4j</groupId>
    <artifactId>slf4j-jdk14</artifactId>
    <version>1.7.25</version>
</dependency>
```

### 5.2.4 同时导入多个日志框架与源码分析

- 同时配置多个日志框架，会出现如下提示（这里以slf4j-simple和logback为例）


```
SLF4J: Class path contains multiple SLF4J bindings.
SLF4J: Found binding in [jar:file:/D:/mvn_repo/org/slf4j/slf4j-simple/1.7.25/slf4j-simple-1.7.25.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: Found binding in [jar:file:/D:/mvn_repo/ch/qos/logback/logback-classic/1.2.11/logback-classic-1.2.11.jar!/org/slf4j/impl/StaticLoggerBinder.class]
SLF4J: See http://www.slf4j.org/codes.html#multiple_bindings for an explanation.
SLF4J: Actual binding is of type [org.slf4j.impl.SimpleLoggerFactory]
```

- 源码分析，查看各个日志框架之间切换的原理以及适配器的作用原理

```
LoggerFactory.getLogger(SLF4JTest.class);
======================>
Logger logger = getLogger(clazz.getName());
======================>
ILoggerFactory iLoggerFactory = getILoggerFactory();
======================>
if (INITIALIZATION_STATE == UNINITIALIZED) {
    synchronized (LoggerFactory.class) {
        if (INITIALIZATION_STATE == UNINITIALIZED) {
            INITIALIZATION_STATE = ONGOING_INITIALIZATION;
            performInitialization();
        }
    }
}
case SUCCESSFUL_INITIALIZATION:
	return StaticLoggerBinder.getSingleton().getLoggerFactory();	此方法返回加载的日志框架对应的日志工厂（如果是使用了适配器且使用的是此框架，如slf4j-log4j12，返回的工厂是Log4jLoggerFactory工厂）
进入具体初始化方法 performInitialization()
======================>
bind();
======================>
if (!isAndroid()) {
    staticLoggerBinderPathSet = findPossibleStaticLoggerBinderPathSet();     查找导入日志框架的数量
    reportMultipleBindingAmbiguity(staticLoggerBinderPathSet);       公布导入多个日志框架（一个没有警告消息）
}
StaticLoggerBinder.getSingleton();       初始化框架的StaticLoggerBinder实例
reportActualBinding(staticLoggerBinderPathSet);      公布实际绑定的日志框架
这里着重分析 findPossibleStaticLoggerBinderPathSet() 和 StaticLoggerBinder.getSingleton()
======================>
分析 findPossibleStaticLoggerBinderPathSet()：
paths = loggerFactoryClassLoader.getResources(STATIC_LOGGER_BINDER_PATH);
STATIC_LOGGER_BINDER_PATH的值为org/slf4j/impl/StaticLoggerBinder.class，这里的意思是通过类加载器去加载org.slf4j.impl.StaticLoggerBinder，因为比SLF4J更晚出的日志框架已经按照其要求，在各自的日志框架内均提供了名为org.slf4j.impl.StaticLoggerBinder的实现类。因此，如果类加载到多个这个类，就说明引入了多个日志框架。

分析 StaticLoggerBinder.getSingleton()：
虽然类加载检测到了两个StaticLoggerBinder，但实际只加载了第一个（同载一个类加器下面全限定名类的唯一性），所以这行代码初始化的是第一个被加载的日志框架的类，为后续通过其工厂获取对应的Logger做了铺垫（较为简单，略）。
```

总结：导入多个日志框架，实际上使用的是最先导入的那个日志框架。在真实生产环境中，导入一个日志框架即可，避免产生没必要的警告信息。

### 5.2.5 桥接器（更换日志框架）

重构需求（在facade-jcl演示）：企业老项目一直使用的是jcl + log4j，现在要在不更改java源代码的前提，切换到slf4j + logback结合使用。

桥接器与原日志框架的对应关系：

| 原项目日志框架 | 需要去掉的依赖      | 需要增加的桥接器（版本与slf4j-api保持一致） |
| -------------- | ------------------- | ------------------------------------------- |
| jcl            | commons-logging.jar | jcl-over-slf4j.jar                          |
| log4j          | log4j.jar           | log4j-over-slf4j.jar                        |
| jul            | 不用去掉jdk自带     | jul-to-slf4j.jar                            |

改造后依赖：

```xml
<dependencies>
    <!--<dependency>
        <groupId>commons-logging</groupId>
        <artifactId>commons-logging</artifactId>
        <version>1.2</version>
    </dependency>-->
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>1.2.17</version>
    </dependency>
    <!-- 以下依赖仅作测试桥接器时，打开使用。示例：将jul+log4j 改造成 slf4j+logback -->
    <!-- slf4j核心依赖 -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>slf4j-api</artifactId>
        <version>1.7.25</version>
    </dependency>
    <!-- logback依赖，导入logback-classic，由于传递性也会导入logback-core -->
    <dependency>
        <groupId>ch.qos.logback</groupId>
        <artifactId>logback-classic</artifactId>
        <version>1.2.11</version>
    </dependency>
    <!-- jcl-over-slf4j桥接器 -->
    <dependency>
        <groupId>org.slf4j</groupId>
        <artifactId>jcl-over-slf4j</artifactId>
        <version>1.7.25</version>
    </dependency>
</dependencies>
```

注意：添加桥接器后，需要去除适配器，如果保留适配器在其上方，不会执行桥接器，没任何意义；如果适配器在其下方，运行报错，故需要去除适配器。

