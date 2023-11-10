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

    /**
     * Get log isEnabled.
     * @return boolean
     */
    public boolean isEnabled() {
        return enabled;
    }

    /**
     * Set log enabled.
     * @param enabled true or false
     */
    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    /**
     * Get isSsnEnabled
     * @return boolean
     */
    public boolean isSsnEnabled() {
        return ssnEnabled;
    }

    /**
     * Set ssn enabled
     * @param ssnEnabled true or false
     */
    public void setSsnEnabled(boolean ssnEnabled) {
        this.ssnEnabled = ssnEnabled;
    }

    /**
     * Get isDurationEnabled
     * @return boolean
     */
    public boolean isDurationEnabled() {
        return durationEnabled;
    }

    /**
     * Set duration enabled.
     * @param durationEnabled true or false
     */
    public void setDurationEnabled(boolean durationEnabled) {
        this.durationEnabled = durationEnabled;
    }
}
