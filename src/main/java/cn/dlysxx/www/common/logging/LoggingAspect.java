package cn.dlysxx.www.common.logging;

import cn.dlysxx.www.common.aspect.AspectUtil;
import java.lang.reflect.Method;
import java.time.Duration;
import java.time.Instant;
import java.util.Arrays;
import java.util.stream.Collectors;
import org.apache.logging.log4j.spi.StandardLevel;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;


/**
 * Aspect for output logs before/after those methods in controller and mapper, and other annotated methods.
 *
 * @author shuai
 */
@Aspect
public class LoggingAspect {

    private static final Logger logger = LoggerFactory.getLogger(LoggingAspect.class);

    private String description;
    private StandardLevel level;
    private boolean argsEnabled;
    private boolean returnValEnabled;
    private Instant startTime;
    private Instant endTime;

    private final LoggingProperties loggingProperties;

    public LoggingAspect(LoggingProperties loggingProperties) {
        this.loggingProperties = loggingProperties;
    }

    @Pointcut("@annotation(cn.dlysxx.www.common.logging.Logging)")
    public void loggingClasses() {
    }


    /**
     * Invoke before.
     *
     * @param joinPoint join point
     */
    @Before("loggingClasses()")
    public void invokeBefore(JoinPoint joinPoint) {
        getLoggingInfo(joinPoint);
        outputLogBefore(joinPoint);
        startTime = Instant.now();
    }


    /**
     * Invoke after.
     *
     * @param joinPoint join point
     * @param returnValue return value
     */
    @AfterReturning(pointcut = "loggingClasses()", returning = "returnValue")
    public void invokeAfter(JoinPoint joinPoint, Object returnValue) {
        endTime = Instant.now();
        outputLogAfterReturning(joinPoint, returnValue);
    }


    /**
     * Invoke after throw exception.
     *
     * @param joinPoint join point
     * @param e throwable
     */
    @AfterThrowing(value = "loggingClasses()", throwing = "e")
    public void invokeAfterThrowing(JoinPoint joinPoint, Throwable e) {
        endTime = Instant.now();
        outputLogAfterThrowing(joinPoint, e);
    }

    private void outputLogBefore(JoinPoint joinPoint) {
        String logMessage = "[SSN:" + getSessionId() + "][SIG:" + getSignature(joinPoint) + "]\tDESC:"
            + this.description + " START\tParams:" + getArguments(joinPoint);
        outputLog(logMessage);
    }

    private void outputLogAfterReturning(JoinPoint joinPoint, Object returnValue) {
        String logMessage = "[SSN:" + getSessionId() + "][SIG:" + getSignature(joinPoint) + "]\tDESC:" + this.description
            + " END\tResult:" + getReturnValue(returnValue) + "\tDuration:" + getDurationMillis() + "ms";
        outputLog(logMessage);
    }

    private void outputLogAfterThrowing(JoinPoint joinPoint, Throwable e) {
        String logMessage = "[SSN:" + getSessionId() + "][SIG::" + getSignature(joinPoint) + "]\tDESC::"
            + this.description + " ERROR\tException::" + e.getClass().toString() + ":" + e.getMessage()
            + "Duration::" + getDurationMillis() + "ms";
        outputLog(logMessage);
    }

    private void outputLog(String logMessage) {
        switch (this.level) {
            case ERROR:
                logger.error(logMessage);
                break;
            case WARN:
                logger.warn(logMessage);
                break;
            default:
                logger.info(logMessage);
        }
    }

    private String getSessionId() {
        if (loggingProperties.isSsnEnabled() && RequestContextHolder.getRequestAttributes() != null) {
            return ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getSessionId();
        } else {
            return "";
        }
    }

    private String getSignature(JoinPoint joinPoint) {
        String packageName = joinPoint.getTarget().getClass().getPackageName();
        String simpleClassName = joinPoint.getTarget().getClass().getSimpleName();
        String simplePkgName = Arrays.stream(packageName.split("\\."))
            .map(pkg -> pkg.substring(0, 1))
            .collect(Collectors.joining("."));
        return simplePkgName + "." + simpleClassName + "." + joinPoint.getSignature().getName();
    }

    private String getArguments(JoinPoint joinPoint) {
        if (argsEnabled) {
            return AspectUtil.getArguments(joinPoint);
        } else {
            return "";
        }
    }

    private String getReturnValue(Object returnValue) {
        if (returnValEnabled) {
            return (returnValue != null) ? returnValue.toString() : "return value is null";
        } else {
            return "";
        }
    }

    private void getLoggingInfo(JoinPoint point) {
        MethodSignature methodSignature = (MethodSignature) point.getSignature();
        Method method = methodSignature.getMethod();
        Logging annotation = method.getAnnotation(Logging.class);
        if (annotation != null) {
            this.description = annotation.description();
            this.returnValEnabled = annotation.returnValEnabled();
            this.argsEnabled = annotation.argsEnabled();
            this.level = annotation.level();
        } else {
            this.description = "";
            this.returnValEnabled = false;
            this.argsEnabled = false;
            this.level = StandardLevel.INFO;
        }
    }

    private String getDurationMillis() {
        if (loggingProperties.isDurationEnabled()) {
            return String.valueOf(Duration.between(startTime, endTime).toMillis());
        } else {
            return "";
        }
    }
}
