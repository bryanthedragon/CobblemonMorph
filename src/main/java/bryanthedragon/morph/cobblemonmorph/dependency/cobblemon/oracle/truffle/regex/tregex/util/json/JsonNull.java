
package com.oracle.truffle.regex.tregex.util.json;

import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.io.PrintWriter;

public final class JsonNull
extends JsonValue {
    public static final JsonNull INSTANCE = new JsonNull();

    private JsonNull() {
    }

    @Override
    public void dump(PrintWriter writer, int indent) {
        writer.print("null");
    }
}

