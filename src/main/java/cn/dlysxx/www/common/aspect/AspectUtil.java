package cn.dlysxx.www.common.aspect;

import org.aspectj.lang.JoinPoint;

/**
 * Utilities for aspect.
 * @author shuai
 */
public final class AspectUtil {

    /**
     * Get request arguments
     * @param joinPoint target joinPoint
     * @return String
     */
    public static String getArguments(JoinPoint joinPoint) {
        if (joinPoint.getArgs() == null) {
            return "argument is null";
        }

        Object[] arguments = joinPoint.getArgs();
        StringBuilder argumentStrings = new StringBuilder();

        for (Object argument : arguments) {
            if (argument != null) {
                argumentStrings.append(argument.toString())
                    .append(",");
            }
        }

        if(argumentStrings.length() > 0) {
            argumentStrings.deleteCharAt(argumentStrings.length() - 1);
        }

        return argumentStrings.toString();
    }
}
