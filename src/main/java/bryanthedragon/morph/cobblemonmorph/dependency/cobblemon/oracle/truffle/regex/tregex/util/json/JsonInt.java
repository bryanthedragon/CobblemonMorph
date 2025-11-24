
package com.oracle.truffle.regex.tregex.util.json;

import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.io.PrintWriter;

public class JsonInt
extends JsonValue {
    private final long value;

    JsonInt(int value2) {
        this.value = value2;
    }

    JsonInt(long value2) {
        this.value = value2;
    }

    @Override
    public void dump(PrintWriter writer, int indent) {
        writer.print(this.value);
    }
}

