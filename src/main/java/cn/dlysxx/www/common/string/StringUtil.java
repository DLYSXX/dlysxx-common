package cn.dlysxx.www.common.string;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import org.apache.commons.lang3.StringUtils;

/**
 * StringUtil.
 *
 * @author shuai
 **/
public class StringUtil {

    private static final String CHARSET_NAME = "UTF-8";

    /**
     * File input stream convert to String.
     *
     * @param inputStream file stream
     * @return string
     * @throws IOException IOException
     */
    public static String conversion(InputStream inputStream) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, CHARSET_NAME))) {
            StringBuilder sb = new StringBuilder();
            while (true) {
                String line = reader.readLine();
                if (line == null) {
                    break;
                }
                sb.append(line);
            }
            return sb.toString();
        }
    }

    /**
     * Mask a string.
     *
     * @param str       string.
     * @param range     range of output plain text.
     * @param direction direction of mask. {@code true} : left, ***4567, {@code false} : right, 1234***.
     * @param mask      mask character.
     * @param allow     exceptional allow string (regular expression).
     * @return the masked string, or empty string if null or empty string inputs.
     */
    public static String mask(String str, int range, boolean direction, char mask, String allow) {
        if (StringUtils.isEmpty(str)) {
            return "";
        }
        if (str.length() <= range || range <= 0) {
            return str;
        }
        StringBuilder sb = new StringBuilder();
        if (direction) {
            for (int i = 0; i < str.length() - range; i++) {
                String c = String.valueOf(str.charAt(i));
                if (!StringUtils.isEmpty(allow) && c.matches(allow)) {
                    sb.append(c);
                } else {
                    sb.append(mask);
                }
            }
            sb.append(str.substring(str.length() - range));
        } else {
            sb.append(str.substring(0, range));
            for (int i = range; i < str.length(); i++) {
                String c = String.valueOf(str.charAt(i));
                if (!StringUtils.isEmpty(allow) && c.matches(allow)) {
                    sb.append(c);
                } else {
                    sb.append(mask);
                }
            }
        }
        return sb.toString();
    }
}
