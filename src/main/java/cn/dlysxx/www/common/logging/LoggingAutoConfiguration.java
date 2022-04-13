package cn.dlysxx.www.common.logging;

import cn.dlysxx.www.common.config.AutoConfigOrder;
import org.springframework.boot.autoconfigure.AutoConfigureOrder;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Auto configuration for common logging.
 *
 * @author shuai
 */
@AutoConfigureOrder(AutoConfigOrder.SECOND)
@Configuration
@EnableConfigurationProperties(LoggingProperties.class)
@ConditionalOnProperty(value = "dlysxx.logging.enabled", havingValue = "true")
public class LoggingAutoConfiguration {

    @Bean
    public LoggingAspect loggingAspect(LoggingProperties loggingProperties) {
        return new LoggingAspect(loggingProperties);
    }

    @Bean
    public LoggingProperties loggingProperties(LoggingProperties loggingProperties) {
        return loggingProperties;
    }
}
