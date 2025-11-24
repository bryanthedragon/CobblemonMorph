
package com.oracle.truffle.regex.tregex.util.json;

import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.io.PrintWriter;

public class JsonBool
extends JsonValue {
    private final boolean value;

    JsonBool(boolean value2) {
        this.value = value2;
    }

    @Override
    public void dump(PrintWriter writer, int indent) {
        writer.print(this.value);
    }
}

