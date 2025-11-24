
package com.oracle.truffle.regex.tregex.util.json;

import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.stream.Stream;

public class JsonArray
extends JsonValue {
    private final ArrayList<JsonConvertible> values;

    JsonArray(ArrayList<JsonConvertible> values) {
        this.values = values;
    }

    JsonArray(JsonConvertible ... values) {
        this(new ArrayList<JsonConvertible>());
        if (values != null) {
            Collections.addAll(this.values, values);
        }
    }

    JsonArray(Iterable<? extends JsonConvertible> values) {
        this(new ArrayList<JsonConvertible>());
        if (values != null) {
            for (JsonConvertible jsonConvertible : values) {
                this.values.add(jsonConvertible);
            }
        }
    }

    JsonArray(Stream<? extends JsonConvertible> values) {
        this(new ArrayList<JsonConvertible>());
        if (values != null) {
            values.forEach(this.values::add);
        }
    }

    public JsonArray append(JsonConvertible value2) {
        this.values.add(value2);
        return this;
    }

    @Override
    public void dump(PrintWriter writer, int indent) {
        writer.print("[");
        boolean first = true;
        for (JsonConvertible v : this.values) {
            if (first) {
                first = false;
            } else {
                writer.print(",");
            }
            writer.println();
            JsonArray.printIndent(writer, indent + 2);
            if (v == null) {
                Json.nullValue().dump(writer, indent + 2);
                continue;
            }
            v.toJson().dump(writer, indent + 2);
        }
        if (!first) {
            writer.println();
            JsonArray.printIndent(writer, indent);
        }
        writer.print("]");
    }
}

