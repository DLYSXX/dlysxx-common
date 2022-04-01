package cn.dlysxx.www.common.date;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.temporal.TemporalAccessor;
import java.time.temporal.TemporalQueries;
import java.util.Calendar;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import lombok.experimental.UtilityClass;
import org.apache.commons.lang3.StringUtils;

/**
 * Utility for {@link Date}.
 *
 * @author lin.duan
 */
@UtilityClass
public class DateUtil {

    /** ISO_ZONED_DATE_TIME (e.g. 2016-04-20T16:42:25.611+09:00[Asia/Tokyo]) */
    public static final String ISO_ZONED_DATE_TIME = "uuuu-MM-dd'T'HH:mm:ss.SSSxxx'['VV']'";
    /** ISO_LOCAL_DATE_TIME (e.g. 2016-04-20T16:42:25.611) */
    public static final String ISO_LOCAL_DATE_TIME = "uuuu-MM-dd'T'HH:mm:ss.SSS";
    /** ISO_LOCAL_DATE (e.g. 2016-04-20) */
    public static final String ISO_LOCAL_DATE = "uuuu-MM-dd";
    /** ISO_LOCAL_TIME (e.g. 16:42:25.611) */
    public static final String ISO_LOCAL_TIME = "HH:mm:ss.SSS";

    /** uu (e.g. 16) */
    public static final String UU = "uu";
    /** uuuu (e.g. 2016) */
    public static final String UUUU = "uuuu";
    /** MM (e.g. 04) */
    public static final String MM = "MM";
    /** uuMM (e.g. 1604) */
    public static final String UUMM = "uuMM";
    /** uuuuMM (e.g. 201604) */
    public static final String UUUUMM = "uuuuMM";
    /** uuuu-MM (e.g. 2016-04) */
    public static final String UUUUMM_HYPHEN = "uuuu-MM";
    /** uuuu/MM (e.g. 2016/04) */
    public static final String UUUUMM_SLASH = "uuuu/MM";
    /** uuuuMMdd (e.g. 20160420) */
    public static final String UUUUMMDD = "uuuuMMdd";
    /** uuuu/MM/dd (e.g. 2016/04/20) */
    public static final String UUUUMMDD_SLASH = "uuuu/MM/dd";
    /** uuuuMMddHHmm (e.g. 201604201642) */
    public static final String UUUUMMDDHHMM = "uuuuMMddHHmm";
    /** uuuu-MM-dd HHmm (e.g. 2016-04-20 16:42) */
    public static final String UUUUMMDDHHMM_HYPHEN = "uuuu-MM-dd HH:mm";
    /** uuuu/MM/dd HHmm (e.g. 2016/04/20 16:42) */
    public static final String UUUUMMDDHHMM_SLASH = "uuuu/MM/dd HH:mm";
    /** uuuuMMddHHmmss (e.g. 20160420164225) */
    public static final String UUUUMMDDHHMMSS = "uuuuMMddHHmmss";
    /** uuuu-MM-dd HH:mm:ss (e.g. 2016-04-20 16:42:25) */
    public static final String UUUUMMDDHHMMSS_HYPHEN = "uuuu-MM-dd HH:mm:ss";
    /** uuuu/MM/dd HH:mm:ss (e.g. 2016/04/20 16:42:25) */
    public static final String UUUUMMDDHHMMSS_SLASH = "uuuu/MM/dd HH:mm:ss";
    /** uuuuMMddHHmmssSSS (e.g. 20160420164225611) */
    public static final String UUUUMMDDHHMMSSSSS = "uuuuMMddHHmmssSSS";
    /** uuuu-MM-dd HH:mm:ss.SSS (e.g. 2016-04-20 16:42:25.611) */
    public static final String UUUUMMDDHHMMSSSSS_HYPHEN = "uuuu-MM-dd HH:mm:ss.SSS";
    /** uuuu/MM/dd HH:mm:ss.SSS (e.g. 2016/04/20 16:42:25.611) */
    public static final String UUUUMMDDHHMMSSSSS_SLASH = "uuuu/MM/dd HH:mm:ss.SSS";

    /** HH:mm:ss (e.g. 16:42:25) */
    public static final String HHMMSS_TIME = "HH:mm:ss";
    /** HHmmss (e.g. 164225) */
    public static final String HHMMSS = "HHmmss";
    /** HHmmssSSS (e.g. 164225611) */
    public static final String HHMMSSSSS = "HHmmssSSS";

    /**
     * Caches for DateTimeFormatter
     *
     * <p>
     * Instance of DateTimeFormatter takes high cost, and supports thread safe.
     * That's why is cached DateTimeFormatter instance.
     * </p>
     */
    private static final Map<String, DateTimeFormatter> CACHE_DATE_TIME_FORMATTER = new ConcurrentHashMap<>();

    /**
     * Get DateTimeFormatter. ({@link DateTimeFormatter})
     *
     * @param format
     *            {@link DateTimeFormatter#ofPattern} (e.g. "uuuu/MM/dd HH:mm:ss")
     * @return {@link DateTimeFormatter}
     */
    private static DateTimeFormatter getDateTimeFormatter(String format) {
        return CACHE_DATE_TIME_FORMATTER.computeIfAbsent(
            format, v -> DateTimeFormatter.ofPattern(format).withResolverStyle(ResolverStyle.STRICT));
    }

    /**
     * Get system date. ({@link ZonedDateTime})
     *
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. UTC)
     * @return {@link ZonedDateTime}
     */
    public static ZonedDateTime nowZonedDateTime(String timeZone) {
        return ZonedDateTime.now(ZoneId.of(timeZone, ZoneId.SHORT_IDS));
    }

    /**
     * Get system date. ({@link LocalDateTime})
     *
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. UTC)
     * @return {@link LocalDateTime}
     */
    public static LocalDateTime nowLocalDateTime(String timeZone) {
        return nowZonedDateTime(timeZone).toLocalDateTime();
    }

    /**
     * Get system date. ({@link LocalDate})
     *
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. UTC)
     * @return {@link LocalDate}
     */
    public static LocalDate nowLocalDate(String timeZone) {
        return nowZonedDateTime(timeZone).toLocalDate();
    }

    /**
     * Get system date. ({@link LocalTime})
     *
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. UTC)
     * @return {@link LocalTime}
     */
    public static LocalTime nowLocalTime(String timeZone) {
        return nowZonedDateTime(timeZone).toLocalTime();
    }

    /**
     * Get system date. ({@link Date})
     *
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. UTC)
     * @return {@link Date}
     */
    public static Date nowDate(String timeZone) {
        ZonedDateTime zoned = nowZonedDateTime(timeZone);
        return Date.from(zoned.toLocalDateTime().toInstant(ZonedDateTime.now().getOffset()));
    }

    /**
     * Get system date. ({@link Calendar})
     *
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. UTC)
     * @return {@link Calendar}
     */
    public static Calendar nowCalendar(String timeZone) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(nowDate(timeZone));
        return cal;
    }

    /**
     * Get system date. ({@link YearMonth})
     *
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. UTC)
     * @return {@link YearMonth}
     */
    public static YearMonth nowYearMonth(String timeZone) {
        return YearMonth.from(nowLocalDate(timeZone));
    }

    /**
     * Get system date. ({@link String})
     *
     * @param format
     *            {@link DateTimeFormatter#ofPattern} (e.g. "uuuu/MM/dd HH:mm:ss")
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. UTC)
     * @return {@link String}
     */
    public static String nowString(String format, String timeZone) {
        ZonedDateTime zoned = nowZonedDateTime(timeZone);
        return toString(zoned, format);
    }

    /**
     * Convert {@link ZonedDateTime} to {@link ZonedDateTime}.
     *
     * @param zonedDateTime
     *            {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00 +0000)
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. JST)
     * @return {@link ZonedDateTime} (e.g. 2016/04/20 08:00:00 +9000)
     */
    public static ZonedDateTime toZonedDateTime(ZonedDateTime zonedDateTime, String timeZone) {
        if (zonedDateTime == null) {
            return null;
        }
        return zonedDateTime.withZoneSameInstant(ZoneId.of(timeZone, ZoneId.SHORT_IDS));
    }

    /**
     * Convert {@link LocalDateTime} to {@link ZonedDateTime}.
     *
     * @param localDateTime
     *            {@link LocalDateTime} (e.g. 2016/04/19 23:00:00)
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. UTC)
     * @return {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00 +0000)
     */
    public static ZonedDateTime toZonedDateTime(LocalDateTime localDateTime, String timeZone) {
        if (localDateTime == null) {
            return null;
        }
        return ZonedDateTime.of(localDateTime, ZoneId.of(timeZone, ZoneId.SHORT_IDS));
    }

    /**
     * Convert {@link LocalDate} to {@link ZonedDateTime}.
     *
     * @param localDate
     *            {@link LocalDate} (e.g. 2016/04/19)
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. UTC)
     * @return {@link ZonedDateTime} (e.g. 2016/04/19 00:00:00 +0000)
     */
    public static ZonedDateTime toZonedDateTime(LocalDate localDate, String timeZone) {
        if (localDate == null) {
            return null;
        }
        return ZonedDateTime.of(localDate.atStartOfDay(), ZoneId.of(timeZone, ZoneId.SHORT_IDS));
    }

    /**
     * Convert {@link LocalTime} to {@link ZonedDateTime}.
     *
     * @param localTime
     *            {@link LocalTime} (e.g. 23:00:00)
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. UTC)
     * @return {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00 +0000, Date = LocalDate.now())
     */
    public static ZonedDateTime toZonedDateTime(LocalTime localTime, String timeZone) {
        if (localTime == null) {
            return null;
        }
        return ZonedDateTime.of(localTime.atDate(nowLocalDate(timeZone)), ZoneId.of(timeZone, ZoneId.SHORT_IDS));
    }

    /**
     * Convert {@link Date} to {@link ZonedDateTime}.
     *
     * <p>
     * e.g.
     * If parameters are Date = 2016/04/19 23:00:00, timeZone = "UTC",
     * returns 2016/04/19 23:00:00 +0000 (UTC)
     * </p>
     *
     * @param date
     *            {@link Date} (e.g. 2016/04/19 23:00:00)
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. UTC)
     * @return {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00)
     */
    public static ZonedDateTime toZonedDateTime(Date date, String timeZone) {
        if (date == null) {
            return null;
        }
        LocalDateTime localDateTime = LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
        return ZonedDateTime.of(localDateTime, ZoneId.of(timeZone, ZoneId.SHORT_IDS));
    }

    /**
     * Convert {@link Calendar} to {@link ZonedDateTime}.
     *
     * @param cal
     *            {@link Calendar} (e.g. 2016/04/19 23:00:00)
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. UTC)
     * @return {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00 +0000)
     */
    public static ZonedDateTime toZonedDateTime(Calendar cal, String timeZone) {
        if (cal == null) {
            return null;
        }
        return toZonedDateTime(cal.getTime(), timeZone);
    }

    /**
     * Convert {@link String} to {@link ZonedDateTime}.
     *
     * @param str
     *            {@link String} (e.g. 2016/04/19 23:00:00 +0000)
     * @param formatWithTimeZone
     *            {@link DateTimeFormatter#ofPattern} (e.g. "uuuu/MM/dd HH:mm:ss Z")
     * @return {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00 +0000)
     */
    public static ZonedDateTime toZonedDateTime(String str, String formatWithTimeZone) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return ZonedDateTime.from(getDateTimeFormatter(formatWithTimeZone).parse(str));
    }

    /**
     * Convert {@link String} to {@link ZonedDateTime}.
     *
     * @param str
     *            {@link String} (e.g. 2016/04/19 23:00:00)
     * @param formatWithoutTimeZone
     *            {@link DateTimeFormatter#ofPattern} (e.g. "uuuu/MM/dd HH:mm:ss")
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. UTC)
     * @return {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00 +0000)
     */
    public static ZonedDateTime toZonedDateTime(String str, String formatWithoutTimeZone, String timeZone) {
        if (StringUtils.isEmpty(str)) {
            return null;
        }
        return toZonedDateTime(toLocalDateTime(str, formatWithoutTimeZone), timeZone);
    }

    /**
     * Convert {@link ZonedDateTime} to {@link LocalDateTime}.
     *
     * @param zonedDateTime
     *            {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00 +0000)
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. JST)
     * @return {@link LocalDateTime} (e.g. 2016/04/20 08:00:00)
     */
    public static LocalDateTime toLocalDateTime(ZonedDateTime zonedDateTime, String timeZone) {
        if (zonedDateTime == null) {
            return null;
        }
        return zonedDateTime.withZoneSameInstant(ZoneId.of(timeZone, ZoneId.SHORT_IDS)).toLocalDateTime();
    }

    /**
     * Convert {@link ZonedDateTime} to {@link LocalDateTime}.
     *
     * @param zonedDateTime
     *            {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00 +0000)
     * @return {@link LocalDateTime} (e.g. 2016/04/19 23:00:00)
     */
    public static LocalDateTime toLocalDateTime(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return null;
        }
        return zonedDateTime.toLocalDateTime();
    }

    /**
     * Convert {@link LocalDateTime} to {@link LocalDateTime}.
     *
     * @param localDateTime
     *            {@link java.time.LocalDateTime} (e.g. 2016/04/19 23:00:00)
     * @param from
     *            {@link ZoneId} like UTC.
     * @param to
     *            {@link ZoneId} like JST.
     * @return {@link LocalDateTime} (e.g. 2016/04/19 08:00:00)
     */
    public static LocalDateTime toLocalDateTime(LocalDateTime localDateTime, ZoneId from, ZoneId to) {
        if (localDateTime == null) {
            return null;
        }
        return ZonedDateTime.ofInstant(ZonedDateTime.of(localDateTime, from).toInstant(), to).toLocalDateTime();
    }

    /**
     * Convert {@link LocalDate} to {@link LocalDateTime}.
     *
     * @param localDate
     *            {@link LocalDate} (e.g. 2016/04/19)
     * @return {@link LocalDateTime} (e.g. 2016/04/19 00:00:00)
     */
    public static LocalDateTime toLocalDateTime(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return localDate.atStartOfDay();
    }

    /**
     * Convert {@link LocalTime} to {@link LocalDateTime}.
     *
     * @param localTime
     *            {@link LocalTime} (e.g. 23:00:00)
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. JST)
     * @return {@link LocalDateTime} (e.g. 2016/04/19 23:00:00, Date = nowLocalDate())
     */
    public static LocalDateTime toLocalDateTime(LocalTime localTime, String timeZone) {
        if (localTime == null) {
            return null;
        }
        return localTime.atDate(nowLocalDate(timeZone));
    }

    /**
     * Convert {@link Date} to {@link LocalDateTime}.
     *
     * @param date
     *            {@link Date} (e.g. 2016/04/19 23:00:00)
     * @return {@link LocalDateTime} (e.g. 2016/04/19 23:00:00)
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTime.ofInstant(date.toInstant(), ZoneId.systemDefault());
    }

    /**
     * Convert {@link Calendar} to {@link LocalDateTime}.
     *
     * @param cal
     *            {@link Calendar} (e.g. 2016/04/19 23:00:00)
     * @return {@link LocalDateTime} (e.g. 2016/04/19 23:00:00)
     */
    public static LocalDateTime toLocalDateTime(Calendar cal) {
        if (cal == null) {
            return null;
        }
        return toLocalDateTime(cal.getTime());
    }

    /**
     * Convert {@link String} to {@link LocalDateTime}.
     *
     * @param str
     *            {@link String} (e.g. "2016/04/19 23:00:00")
     * @param format
     *            {@link DateTimeFormatter#ofPattern} (e.g. "uuuu/MM/dd HH:mm:ss")
     * @return {@link LocalDateTime} (e.g. 2016/04/19 23:00:00)
     */
    public static LocalDateTime toLocalDateTime(String str, String format) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return LocalDateTime.from(getDateTimeFormatter(format).parse(str));
    }

    /**
     * Convert {@link ZonedDateTime} to {@link LocalDate}.
     *
     * @param zonedDateTime
     *            {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00 +0000)
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. JST)
     * @return {@link LocalDate} (e.g. 2016/04/20)
     */
    public static LocalDate toLocalDate(ZonedDateTime zonedDateTime, String timeZone) {
        if (zonedDateTime == null) {
            return null;
        }
        return toLocalDateTime(zonedDateTime, timeZone).toLocalDate();
    }

    /**
     * Convert {@link ZonedDateTime} to {@link LocalDate}.
     *
     * @param zonedDateTime
     *            {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00 +0000)
     * @return {@link LocalDate} (e.g. 2016/04/19)
     */
    public static LocalDate toLocalDate(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return null;
        }
        return toLocalDateTime(zonedDateTime).toLocalDate();
    }

    /**
     * Convert {@link LocalDateTime} to {@link LocalDate}.
     *
     * @param localDateTime
     *            {@link LocalDateTime} (e.g. 2016/04/19 23:00:00)
     * @return {@link LocalDate} (e.g. 2016/04/19)
     */
    public static LocalDate toLocalDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.toLocalDate();
    }

    /**
     * Convert {@link Date} to {@link LocalDate}.
     *
     * @param date
     *            {@link Date} (e.g. 2016/04/19)
     * @return {@link LocalDate} (e.g. 2016/04/19)
     */
    public static LocalDate toLocalDate(Date date) {
        if (date == null) {
            return null;
        }
        return toLocalDateTime(date).toLocalDate();
    }

    /**
     * Convert {@link Calendar} to {@link LocalDate}.
     *
     * @param cal
     *            {@link Calendar} (e.g. 2016/04/19)
     * @return {@link LocalDate} (e.g. 2016/04/19)
     */
    public static LocalDate toLocalDate(Calendar cal) {
        if (cal == null) {
            return null;
        }
        return toLocalDate(cal.getTime());
    }

    /**
     * Convert {@link String} to {@link LocalDate}.
     *
     * @param str
     *            {@link String} (e.g. "2016/04/19")
     * @param format
     *            {@link DateTimeFormatter#ofPattern} (e.g. "uuuu/MM/dd")
     * @return {@link LocalDate} (e.g. 2016/04/19)
     */
    public static LocalDate toLocalDate(String str, String format) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return LocalDate.from(getDateTimeFormatter(format).parse(str));
    }

    /**
     * Convert {@link ZonedDateTime} to {@link LocalTime}.
     *
     * @param zonedDateTime
     *            {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00 +0000)
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. JST)
     * @return {@link LocalTime} (e.g. 08:00:00)
     */
    public static LocalTime toLocalTime(ZonedDateTime zonedDateTime, String timeZone) {
        if (zonedDateTime == null) {
            return null;
        }
        return toLocalDateTime(zonedDateTime, timeZone).toLocalTime();
    }

    /**
     * Convert {@link ZonedDateTime} to {@link LocalTime}.
     *
     * @param zonedDateTime
     *            {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00 +0000)
     * @return {@link LocalTime} (e.g. 23:00:00)
     */
    public static LocalTime toLocalTime(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return null;
        }
        return toLocalDateTime(zonedDateTime).toLocalTime();
    }

    /**
     * Convert {@link LocalDateTime} to {@link LocalTime}.
     *
     * @param localDateTime
     *            {@link LocalDateTime} (e.g. 2016/04/19 23:00:00)
     * @return {@link LocalTime} (e.g. 23:00:00)
     */
    public static LocalTime toLocalTime(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return localDateTime.toLocalTime();
    }

    /**
     * Convert {@link Date} to {@link LocalTime}.
     *
     * @param date
     *            {@link Date} (e.g. 2016/04/19 23:00:00)
     * @return {@link LocalTime} (e.g. 23:00:00)
     */
    public static LocalTime toLocalTime(Date date) {
        if (date == null) {
            return null;
        }
        return toLocalDateTime(date).toLocalTime();
    }

    /**
     * Convert {@link String} to {@link LocalTime}.
     *
     * @param str
     *            {@link String} (e.g. "2016/04/19 23:00:00")
     * @param format
     *            {@link DateTimeFormatter#ofPattern} (e.g. "HH:mm:ss")
     * @return {@link LocalTime} (e.g. 23:00:00)
     */
    public static LocalTime toLocalTime(String str, String format) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return LocalTime.from(getDateTimeFormatter(format).parse(str));
    }

    /**
     * Convert {@link ZonedDateTime} to {@link Date}.
     *
     * @param zonedDateTime
     *            {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00 +0000)
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. JST)
     * @return {@link Date} (e.g. 2016/04/20 08:00:00)
     */
    public static Date toDate(ZonedDateTime zonedDateTime, String timeZone) {
        if (zonedDateTime == null) {
            return null;
        }
        return toDate(toLocalDateTime(zonedDateTime, timeZone));
    }

    /**
     * Convert {@link ZonedDateTime} to {@link Date}.
     *
     * @param zonedDateTime
     *            {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00 +0000)
     * @return {@link Date} (e.g. 2016/04/19 23:00:00)
     */
    public static Date toDate(ZonedDateTime zonedDateTime) {
        if (zonedDateTime == null) {
            return null;
        }
        return toDate(toLocalDateTime(zonedDateTime));
    }

    /**
     * Convert {@link LocalDateTime} to {@link Date}.
     *
     * @param localDateTime
     *            {@link LocalDateTime} (e.g. 2016/04/19 23:00:00)
     * @return {@link Date} (e.g. 2016/04/19 23:00:00)
     */
    public static Date toDate(LocalDateTime localDateTime) {
        if (localDateTime == null) {
            return null;
        }
        return Date.from(localDateTime.toInstant(ZonedDateTime.now().getOffset()));
    }

    /**
     * Convert {@link LocalDate} to {@link Date}.
     *
     * @param localDate
     *            {@link LocalDate} (e.g. 2016/04/19)
     * @return {@link Date} (e.g. 2016/04/19 00:00:00)
     */
    public static Date toDate(LocalDate localDate) {
        if (localDate == null) {
            return null;
        }
        return Date.from(localDate.atStartOfDay().toInstant(ZonedDateTime.now().getOffset()));
    }

    /**
     * Convert {@link LocalTime} to {@link Date}.
     *
     * @param localTime
     *            {@link LocalTime} (e.g. 23:00:00)
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. JST)
     * @return {@link Date} (e.g. 2016/04/19 23:00:00, Date = nowLocalDate())
     */
    public static Date toDate(LocalTime localTime, String timeZone) {
        if (localTime == null) {
            return null;
        }
        return Date.from(localTime.atDate(nowLocalDate(timeZone)).toInstant(ZonedDateTime.now().getOffset()));
    }

    /**
     * Convert {@link Calendar} to {@link Date}.
     *
     * @param cal
     *            {@link Calendar} (e.g. 2016/04/19 23:00:00)
     * @return {@link Date} (e.g. 2016/04/19 23:00:00)
     */
    public static Date toDate(Calendar cal) {
        if (cal == null) {
            return null;
        }
        return cal.getTime();
    }

    /**
     * Convert {@link String} to {@link Date}.
     *
     * @param str
     *            {@link String} (e.g. "2016/04/19 23:00:00")
     * @param format
     *            {@link DateTimeFormatter#ofPattern} (e.g. "uuuu/MM/dd HH:mm:ss")
     * @return {@link Date} (e.g. 2016/04/19 23:00:00)
     */
    public static Date toDate(String str, String format) {
        if (StringUtils.isBlank(str)) {
            return null;
        }

        TemporalAccessor temporal = getDateTimeFormatter(format).parse(str);
        LocalDate date = temporal.query(TemporalQueries.localDate());
        LocalTime time = temporal.query(TemporalQueries.localTime());

        if (time != null) {
            return toDate(LocalDateTime.of(date, time));
        }

        return toDate(date.atStartOfDay());
    }

    /**
     * Convert {@link ZonedDateTime} to {@link Calendar}.
     *
     * @param zonedDateTime
     *            {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00 +0000)
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. JST)
     * @return {@link Calendar} (e.g. 2016/04/20 08:00:00)
     */
    public static Calendar toCalendar(ZonedDateTime zonedDateTime, String timeZone) {
        if (zonedDateTime == null) {
            return null;
        }
        return toCalendar(toDate(zonedDateTime, timeZone));
    }

    /**
     * Convert {@link ZonedDateTime} to {@link Calendar}.
     *
     * @param zonedDateTime
     *            {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00 +0000)
     * @return {@link Calendar} (e.g. 2016/04/19 23:00:00)
     */
    public static Calendar toCalendar(ZonedDateTime zonedDateTime) {
        return toCalendar(toDate(zonedDateTime));
    }

    /**
     * Convert {@link LocalDateTime} to {@link Calendar}.
     *
     * @param localDateTime
     *            {@link LocalDateTime} (e.g. 2016/04/19 23:00:00)
     * @return {@link Calendar} (e.g. 2016/04/19 23:00:00)
     */
    public static Calendar toCalendar(LocalDateTime localDateTime) {
        return toCalendar(toDate(localDateTime));
    }

    /**
     * Convert {@link LocalDate} to {@link Calendar}.
     *
     * @param localDate
     *            {@link LocalDate} (e.g. 2016/04/19)
     * @return {@link Calendar} (e.g. 2016/04/19 00:00:00)
     */
    public static Calendar toCalendar(LocalDate localDate) {
        return toCalendar(toDate(localDate));
    }

    /**
     * Convert {@link LocalTime} to {@link Calendar}.
     *
     * @param localTime
     *            {@link LocalTime} (e.g. 23:00:00)
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. JST)
     * @return {@link Calendar} (e.g. 2016/04/19 23:00:00, Date = nowLocalDate())
     */
    public static Calendar toCalendar(LocalTime localTime, String timeZone) {
        return toCalendar(toDate(localTime, timeZone));
    }

    /**
     * Convert {@link Date} to {@link Calendar}.
     *
     * @param date
     *            {@link Date} (e.g. 2016/04/19 23:00:00)
     * @return {@link Calendar} (e.g. 2016/04/19 23:00:00)
     */
    public static Calendar toCalendar(Date date) {
        if (date == null) {
            return null;
        }
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal;
    }

    /**
     * Convert {@link String} to {@link Calendar}.
     *
     * @param str
     *            {@link String} (e.g. "2016/04/19 23:00:00")
     * @param format
     *            {@link DateTimeFormatter#ofPattern} (e.g. "uuuu/MM/dd HH:mm:ss")
     * @return {@link Calendar} (e.g. 2016/04/19 23:00:00)
     */
    public static Calendar toCalendar(String str, String format) {
        return toCalendar(toDate(str, format));
    }

    /**
     * Convert {@link Calendar} to {@link YearMonth}.
     *
     * @param cal
     *            {@link Calendar} (e.g. 2016/04/19 23:00:00)
     * @return {@link YearMonth} (e.g. 2016/04)
     */
    public static YearMonth toYearMonth(Calendar cal) {
        if (cal == null) {
            return null;
        }
        return YearMonth.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1);
    }

    /**
     * Convert {@link String} to {@link YearMonth}.
     *
     * @param str
     *            {@link String} (e.g. "2016/04")
     * @param format
     *            {@link DateTimeFormatter#ofPattern} (e.g. "uuuu/MM")
     * @return {@link YearMonth} (e.g. 2016/04)
     */
    public static YearMonth toYearMonth(String str, String format) {
        if (StringUtils.isBlank(str)) {
            return null;
        }
        return YearMonth.from(getDateTimeFormatter(format).parse(str));
    }

    /**
     * Convert {@link ZonedDateTime} to {@link String}.
     *
     * @param zonedDateTime
     *            {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00 +0000)
     * @param timeZone
     *            {@link ZoneId} like UTC, JST, ECT, or Asia/Tokyo, Europe/Paris... (e.g. JST)
     * @param format
     *            {@link DateTimeFormatter#ofPattern} (e.g. "uuuu/MM/dd HH:mm:ss Z")
     * @return {@link String} (e.g. "2016/04/20 08:00:00 +0000")
     */
    public static String toString(ZonedDateTime zonedDateTime, String timeZone, String format) {
        if (zonedDateTime == null) {
            return null;
        }
        return toString(toZonedDateTime(zonedDateTime, timeZone), format);
    }

    /**
     * Convert {@link ZonedDateTime} to {@link String}.
     *
     * @param zonedDateTime
     *            {@link ZonedDateTime} (e.g. 2016/04/19 23:00:00 +0000)
     * @param format
     *            {@link DateTimeFormatter#ofPattern} (e.g. "uuuu/MM/dd HH:mm:ss Z")
     * @return {@link String} (e.g. "2016/04/19 23:00:00 +0000")
     */
    public static String toString(ZonedDateTime zonedDateTime, String format) {
        if (zonedDateTime == null) {
            return null;
        }
        return getDateTimeFormatter(format).format(zonedDateTime);
    }

    /**
     * Convert {@link LocalDateTime} to {@link String}.
     *
     * @param localDateTime
     *            {@link LocalDateTime} (e.g. 2016/04/19 23:00:00)
     * @param format
     *            {@link DateTimeFormatter#ofPattern} (e.g. "uuuu/MM/dd HH:mm:ss")
     * @return {@link String} (e.g. "2016/04/19 23:00:00")
     */
    public static String toString(LocalDateTime localDateTime, String format) {
        if (localDateTime == null) {
            return null;
        }
        return getDateTimeFormatter(format).format(localDateTime);
    }

    /**
     * Convert {@link LocalDate} to {@link String}.
     *
     * @param localDate
     *            {@link LocalDate} (e.g. 2016/04/19)
     * @param format
     *            {@link DateTimeFormatter#ofPattern} (e.g. "uuuu/MM/dd HH:mm:ss")
     * @return {@link String} (e.g. "2016/04/19 00:00:00")
     */
    public static String toString(LocalDate localDate, String format) {
        if (localDate == null) {
            return null;
        }
        return getDateTimeFormatter(format).format(localDate.atStartOfDay());
    }

    /**
     * Convert {@link LocalTime} to {@link String}.
     *
     * @param localTime
     *            {@link LocalTime} (e.g. 2016/04/19)
     * @param format
     *            {@link DateTimeFormatter#ofPattern} (e.g. "uuuu/MM/dd HH:mm:ss")
     * @return {@link String} (e.g. "2016/04/19 00:00:00")
     */
    public static String toString(LocalTime localTime, String format) {
        if (localTime == null) {
            return null;
        }
        return getDateTimeFormatter(format).format(localTime);
    }

    /**
     * Convert {@link Date} to {@link String}.
     *
     * @param date
     *            {@link Date} (e.g. 2016/04/19 23:00:00)
     * @param format
     *            {@link DateTimeFormatter#ofPattern} (e.g. "uuuu/MM/dd HH:mm:ss")
     * @return {@link String} (e.g. "2016/04/19 23:00:00")
     */
    public static String toString(Date date, String format) {
        if (date == null) {
            return null;
        }
        return toString(toLocalDateTime(date), format);
    }

    /**
     * Convert {@link Calendar} to {@link String}.
     *
     * @param cal
     *            {@link Calendar} (e.g. 2016/04/19 23:00:00)
     * @param format
     *            {@link DateTimeFormatter#ofPattern} (e.g. "uuuu/MM/dd HH:mm:ss")
     * @return {@link String} (e.g. "2016/04/19 23:00:00")
     */
    public static String toString(Calendar cal, String format) {
        if (cal == null) {
            return null;
        }
        return toString(toLocalDateTime(cal), format);
    }

    /**
     * Convert {@link YearMonth} to {@link String}.
     *
     * @param yearMonth
     *            {@link YearMonth} (e.g. 2016/04)
     * @param format
     *            {@link DateTimeFormatter#ofPattern} (e.g. "uuuu/MM")
     * @return {@link String} (e.g. "2016/04")
     */
    public static String toString(YearMonth yearMonth, String format) {
        if (yearMonth == null) {
            return null;
        }
        return getDateTimeFormatter(format).format(yearMonth);
    }

    /**
     * Check if the date is in the specified period.
     *
     * @param start
     *            start date
     * @param end
     *            end date
     * @param target
     *            target date
     * @return intime：true, out of time：false
     */
    public static boolean isIntime(Date start, Date end, Date target) {
        return target.equals(start) || target.equals(end) || target.after(start) && target.before(end);
    }

}
