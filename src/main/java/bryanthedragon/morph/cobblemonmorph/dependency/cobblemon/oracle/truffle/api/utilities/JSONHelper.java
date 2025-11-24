
package com.oracle.truffle.api.utilities;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public final class JSONHelper {
    private JSONHelper() {
    }

    private static String quote(CharSequence value2) {
        StringBuilder builder = new StringBuilder(value2.length() + 2);
        builder.append('\"');
        block9: for (int i = 0; i < value2.length(); ++i) {
            char c = value2.charAt(i);
            switch (c) {
                case '\"': {
                    builder.append("\\\"");
                    continue block9;
                }
                case '\\': {
                    builder.append("\\\\");
                    continue block9;
                }
                case '\b': {
                    builder.append("\\b");
                    continue block9;
                }
                case '\f': {
                    builder.append("\\f");
                    continue block9;
                }
                case '\n': {
                    builder.append("\\n");
                    continue block9;
                }
                case '\r': {
                    builder.append("\\r");
                    continue block9;
                }
                case '\t': {
                    builder.append("\\t");
                    continue block9;
                }
                default: {
                    if (c < ' ') {
                        builder.append("\\u00");
                        builder.append(Character.forDigit(c >> 4 & 0xF, 16));
                        builder.append(Character.forDigit(c & 0xF, 16));
                        continue block9;
                    }
                    builder.append(c);
                }
            }
        }
        builder.append('\"');
        return builder.toString();
    }

    public static JSONObjectBuilder object() {
        return new JSONObjectBuilder();
    }

    public static JSONArrayBuilder array() {
        return new JSONArrayBuilder();
    }

    public static final class JSONArrayBuilder
    extends JSONStringBuilder {
        private final List<Object> contents = new ArrayList<Object>();

        private JSONArrayBuilder() {
        }

        public JSONArrayBuilder add(String value2) {
            this.contents.add(value2);
            return this;
        }

        public JSONArrayBuilder add(Number value2) {
            this.contents.add(value2);
            return this;
        }

        public JSONArrayBuilder add(Boolean value2) {
            this.contents.add(value2);
            return this;
        }

        public JSONArrayBuilder add(JSONStringBuilder value2) {
            this.contents.add(value2);
            return this;
        }

        @Override
        protected void appendTo(StringBuilder sb) {
            sb.append("[");
            boolean comma = false;
            for (Object value2 : this.contents) {
                if (comma) {
                    sb.append(", ");
                }
                JSONArrayBuilder.appendValue(sb, value2);
                comma = true;
            }
            sb.append("]");
        }
    }

    public static final class JSONObjectBuilder
    extends JSONStringBuilder {
        private final Map<String, Object> contents = new LinkedHashMap<String, Object>();

        private JSONObjectBuilder() {
        }

        public JSONObjectBuilder add(String key, String value2) {
            this.contents.put(key, value2);
            return this;
        }

        public JSONObjectBuilder add(String key, Number value2) {
            this.contents.put(key, value2);
            return this;
        }

        public JSONObjectBuilder add(String key, Boolean value2) {
            this.contents.put(key, value2);
            return this;
        }

        public JSONObjectBuilder add(String key, JSONStringBuilder value2) {
            this.contents.put(key, value2);
            return this;
        }

        @Override
        protected void appendTo(StringBuilder sb) {
            sb.append("{");
            boolean comma = false;
            for (Map.Entry<String, Object> entry : this.contents.entrySet()) {
                if (comma) {
                    sb.append(", ");
                }
                sb.append(JSONHelper.quote(entry.getKey()));
                sb.append(": ");
                JSONObjectBuilder.appendValue(sb, entry.getValue());
                comma = true;
            }
            sb.append("}");
        }
    }

    public static abstract class JSONStringBuilder {
        private JSONStringBuilder() {
        }

        public final String toString() {
            StringBuilder sb = new StringBuilder();
            this.appendTo(sb);
            return sb.toString();
        }

        protected abstract void appendTo(StringBuilder var1);

        protected static void appendValue(StringBuilder sb, Object value2) {
            if (value2 instanceof JSONStringBuilder) {
                ((JSONStringBuilder)value2).appendTo(sb);
            } else if (value2 instanceof Integer || value2 instanceof Boolean || value2 == null) {
                sb.append(value2);
            } else {
                sb.append(JSONHelper.quote(String.valueOf(value2)));
            }
        }
    }
}

