package com.cassey.house.common.jdbc;

import java.util.Iterator;

public class SQLBuilder {
    private StringBuilder builder = new StringBuilder();

    public SQLBuilder eq(String field, Object value) {
        return condition(field, "=", value);
    }

    public SQLBuilder lt(String field, Object value) {
        return condition(field, "<", value);
    }

    public SQLBuilder le(String field, Object value) {
        return condition(field, "<=", value);
    }

    public SQLBuilder ne(String field, Object value) {
        return condition(field, "!=", value);
    }

    public SQLBuilder gt(String field, Object value) {
        return condition(field, ">", value);
    }

    public SQLBuilder ge(String field, Object value) {
        return condition(field, ">=", value);
    }

    public SQLBuilder in(String field, Object... values) {
        this.builder.append(field).append(" in (");
        this.appendValue(values[0]);

        for (int i = 1; i < values.length; i++) {
            this.builder.append(",");
            this.appendValue(values[i]);
        }

        this.builder.append(")");
        return this;
    }

    public SQLBuilder in(String field, Iterable values) {
        this.builder.append(field).append(" in (");
        Iterator iterator = values.iterator();
        this.appendValue(iterator.next());

        while (iterator.hasNext()) {
            this.builder.append(",");
            this.appendValue(iterator.next());
        }

        this.builder.append(")");
        return this;
    }

    public SQLBuilder like(String field, String value) {
        return condition(field, " like ", value);
    }

    public SQLBuilder isNull(String field) {
        return condition(field, " is null", null);
    }

    public SQLBuilder notNull(String field) {
        return condition(field, " is not null", null);
    }

    public SQLBuilder and() {
        return connector(" and ");
    }

    public SQLBuilder or() {
        return connector(" or ");
    }

    public SQLBuilder bracketsStart() {
        return connector("(");
    }

    public SQLBuilder bracketsEnd() {
        return connector(")");
    }

    public SQLBuilder condition(String field, String operator, Object value) {
        builder.append(field).append(operator);
        this.appendValue(value);
        return this;
    }

    public SQLBuilder connector(String connector) {
        builder.append(connector);
        return this;
    }

    private StringBuilder appendValue(Object value) {
        if (value instanceof Number) {
            builder.append(value);
        } else {
            builder.append("'").append(value).append("'");
        }
        return builder;
    }

    @Override
    public String toString() {
        return builder.toString();
    }
}
