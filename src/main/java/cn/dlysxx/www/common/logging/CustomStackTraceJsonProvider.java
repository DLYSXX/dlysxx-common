package cn.dlysxx.www.common.logging;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.classic.spi.IThrowableProxy;
import com.fasterxml.jackson.core.JsonGenerator;
import java.io.IOException;
import java.util.LinkedHashMap;
import java.util.Map;
import net.logstash.logback.composite.JsonWritingUtils;
import net.logstash.logback.composite.loggingevent.StackTraceJsonProvider;

/**
 * CustomStackTraceJsonProvider.
 *
 * @author shuai
 */
public class CustomStackTraceJsonProvider extends StackTraceJsonProvider {

    /** Custom field stack trace */
    public static final String CUSTOM_FIELD_STACK_TRACE = "stacktrace";

    /**
     * Constructor.
     */
    public CustomStackTraceJsonProvider() {
        super();
        setFieldName(CUSTOM_FIELD_STACK_TRACE);
    }

    /**
     * Write stack trace into json.
     * @param generator json generator
     * @param event log event
     * @throws IOException exception
     */
    @Override
    public void writeTo(JsonGenerator generator, ILoggingEvent event) throws IOException {
        IThrowableProxy throwableProxy = event.getThrowableProxy();
        if (throwableProxy != null) {
            String msg = getThrowableConverter().convert(event);
            String[] lines = msg.split("\\n(\\t)?");
            Map<String, String> lineMap = new LinkedHashMap<>();
            for (int i = 0; i < lines.length; i++) {
                lineMap.put(String.valueOf(i + 1), lines[i]);
            }
            JsonWritingUtils.writeMapStringFields(generator, CUSTOM_FIELD_STACK_TRACE, lineMap);
        }
    }
}
