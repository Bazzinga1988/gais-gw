import ch.qos.logback.classic.encoder.PatternLayoutEncoder
import ch.qos.logback.core.rolling.FixedWindowRollingPolicy
import ch.qos.logback.core.rolling.RollingFileAppender
import ch.qos.logback.core.rolling.SizeBasedTriggeringPolicy

import static ch.qos.logback.classic.Level.*

def PATH_TO_SERVICE = System.getProperty("PATH_TO_SERVICE")
def LOG_NAME = "gais-gateway"

if (PATH_TO_SERVICE == null || PATH_TO_SERVICE.isEmpty())
    PATH_TO_SERVICE = "."

appender("STDOUT", ConsoleAppender) {
    encoder(PatternLayoutEncoder) {
        pattern = "%d{dd.MM.yyyy HH:mm:ss} %-5level %-24logger{0} -> %m%n"
    }
}

appender("FILE", RollingFileAppender) {
    file = "${PATH_TO_SERVICE}/${LOG_NAME}.log"
    rollingPolicy(FixedWindowRollingPolicy) {
        fileNamePattern = "${PATH_TO_SERVICE}/${LOG_NAME}.%i.log.zip"
        minIndex = 1
        maxIndex = 10
    }
    triggeringPolicy(SizeBasedTriggeringPolicy) {
        maxFileSize = "10MB"
    }
    encoder(PatternLayoutEncoder) {
        Pattern = "%d %level %thread %mdc %logger - %m%n"
    }
}

// ALL, TRACE, DEBUG, INFO, WARN, ERROR, OFF

logger("org.springframework", OFF)
logger("org.springframework.boot", OFF)
logger("org.springframework.boot.autoconfigure", OFF)

logger("ru.glosav.gais.gateway", DEBUG)
logger("org.springframework.web", DEBUG)

root(INFO, ["STDOUT"])
root(DEBUG, ["FILE"])
