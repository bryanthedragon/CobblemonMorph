
package com.oracle.truffle.regex.tregex.util.json;

import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.io.PrintWriter;

public class JsonString
extends JsonValue {
    private final String value;

    JsonString(String value2) {
        this.value = value2;
    }

    @Override
    public void dump(PrintWriter writer, int indent) {
        writer.print("\"");
        for (int i = 0; i < this.value.length(); ++i) {
            char ch = this.value.charAt(i);
            if (ch < ' ') {
                if (ch == '\b') {
                    writer.print("\\b");
                    continue;
                }
                if (ch == '\f') {
                    writer.print("\\f");
                    continue;
                }
                if (ch == '\n') {
                    writer.print("\\n");
                    continue;
                }
                if (ch == '\r') {
                    writer.print("\\r");
                    continue;
                }
                if (ch == '\t') {
                    writer.print("\\t");
                    continue;
                }
                writer.print("\\u00");
                writer.print(Character.forDigit(ch >> 4 & 0xF, 16));
                writer.print(Character.forDigit(ch & 0xF, 16));
                continue;
            }
            if (ch == '\\') {
                writer.print("\\\\");
                continue;
            }
            if (ch == '\"') {
                writer.print("\\\"");
                continue;
            }
            writer.print(ch);
        }
        writer.print("\"");
    }
}

