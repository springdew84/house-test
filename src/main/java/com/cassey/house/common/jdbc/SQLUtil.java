package com.cassey.house.common.jdbc;

import java.util.Iterator;

public class SQLUtil {
    public static String format(String sql, Object... args) {
        if (args == null || args.length == 0) {
            return sql;
        } else {
            char[] chars = sql.toCharArray();

            int argIndex = 0;
            Iterator argIterator = null;
            Object[] array = null;
            int innerIndex = 0;
            StringBuilder builder = new StringBuilder();
            for (int i = 0; i < chars.length; i++) {
                char c = chars[i];
                if (c == '?') {
                    Object arg = args[argIndex];
                    if (arg instanceof Number) {
                        builder.append(arg);
                        argIndex++;
                    } else if (arg instanceof Iterable) {
                        if (argIterator == null) {
                            Iterable collection = (Iterable) arg;
                            argIterator = collection.iterator();
                        }
                        Object a = argIterator.next();
                        if (a instanceof Number) {
                            builder.append(a);
                        } else {
                            builder.append("'").append(a).append("'");
                        }
                        if (!argIterator.hasNext()) {
                            argIndex++;
                            argIterator = null;
                        }
                    } else if (arg.getClass().isArray()) {
                        if (array == null) {
                            array = (Object[]) arg;
                        }
                        Object a = array[innerIndex++];
                        if (a instanceof Number) {
                            builder.append(a);
                        } else {
                            builder.append("'").append(a).append("'");
                        }
                        if (innerIndex == array.length) {
                            argIndex++;
                            innerIndex = 0;
                            array = null;
                        }
                    } else {
                        builder.append("'").append(arg).append("'");
                        argIndex++;
                    }
                } else {
                    builder.append(c);
                }

                if (argIndex == args.length) {
                    builder.append(sql, i + 1, sql.length());
                    break;
                }
            }

            return builder.toString();
        }
    }
}
