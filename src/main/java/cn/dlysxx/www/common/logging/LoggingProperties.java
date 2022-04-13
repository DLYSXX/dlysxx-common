package cn.dlysxx.www.common.logging;

import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties of DLYSXX logging.
 *
 * @author shuai
 */
@ConfigurationProperties(prefix = "dlysxx.logging")
public class LoggingProperties {
    private boolean enabled = false;
    private boolean ssnEnabled = true;
    private boolean durationEnabled = true;

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public boolean isSsnEnabled() {
        return ssnEnabled;
    }

    public void setSsnEnabled(boolean ssnEnabled) {
        this.ssnEnabled = ssnEnabled;
    }

    public boolean isDurationEnabled() {
        return durationEnabled;
    }

    public void setDurationEnabled(boolean durationEnabled) {
        this.durationEnabled = durationEnabled;
    }
}
