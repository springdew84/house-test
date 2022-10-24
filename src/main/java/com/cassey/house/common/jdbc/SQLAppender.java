package com.cassey.house.common.jdbc;

import java.util.Map;
import java.util.Set;

public class SQLAppender {
    public static void appendInClause(StringBuilder sqlBuilder, String field, int count) {
        sqlBuilder.append(" ").append(field).append(" in (");
        for (int i = 0; i < count; i++) {
            sqlBuilder.append("?,");
        }
        sqlBuilder.deleteCharAt(sqlBuilder.length() - 1).append(")");
    }

    public static String insert(String table, Map<String, Object> map) {
        return insert(table, map, null);
    }

    public static String insert(String table, Map<String, Object> map, Set<String> fieldSet) {
        StringBuilder builder = new StringBuilder().append("insert into ").append(table).append("(");
        StringBuilder valueBuilder = new StringBuilder();
        map.forEach((field, value) -> {
            if (fieldSet == null || fieldSet.contains(field)) {
                if (valueBuilder.length() > 0) {
                    builder.append(",");
                    valueBuilder.append(",");
                }

                builder.append(field);

                if (value == null) {
                    valueBuilder.append("null");
                } else if (value instanceof Number) {
                    valueBuilder.append(value);
                } else {
                    valueBuilder.append("'").append(value.toString().replace("'", "\\'")).append("'");
                }
            }
        });
        builder.deleteCharAt(builder.length() - 1).append(") values (");
        builder.append(valueBuilder, 0, valueBuilder.length() - 1).append(");");
        return builder.toString();
    }

    public static String update(String table, Map<String, Object> map) {
        StringBuilder builder = new StringBuilder().append("update ").append(table).append(" set ");
        map.forEach((field, value) -> {
            builder.append(field).append("=");
            appendValue(builder, value);
            builder.append(",");
        });

        builder.append(" where ").append(");");
        return builder.toString();
    }

    private static void appendValue(StringBuilder builder, Object v) {
        if (v instanceof Number) {
            builder.append(v);
        } else {
            builder.append("'").append(v).append("'");
        }
    }

    public static void inClause(StringBuilder builder, String field, int count) {
        builder.append(" ").append(field).append(" in (?");
        for (int i = 1; i < count; i++) {
            builder.append(",?");
        }
        builder.append(")");
    }
}
