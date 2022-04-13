package cn.dlysxx.www.common.logging;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import org.apache.logging.log4j.spi.StandardLevel;

/**
 * Marks a method as to be intercepted by DLYSXX LoggingAspect.
 *
 * @author shuai
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Logging {

    /**
     * description.
     *
     * @return string
     */
    String description() default "";

    /**
     * level.
     *
     * @return standardLevel
     */
    StandardLevel level() default StandardLevel.INFO;

    /**
     * argsEnabled.
     *
     * @return boolean
     */
    boolean argsEnabled() default false;

    /**
     * returnValEnabled.
     *
     * @return boolean
     */
    boolean returnValEnabled() default false;
}
