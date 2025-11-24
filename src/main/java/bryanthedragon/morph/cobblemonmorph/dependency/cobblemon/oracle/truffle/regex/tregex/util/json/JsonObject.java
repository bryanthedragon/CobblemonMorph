
package com.oracle.truffle.regex.tregex.util.json;

import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Collections;

public class JsonObject
extends JsonValue {
    private final ArrayList<JsonObjectProperty> properties = new ArrayList();

    JsonObject(JsonObjectProperty ... props) {
        Collections.addAll(this.properties, props);
    }

    public JsonObject append(JsonObjectProperty ... props) {
        Collections.addAll(this.properties, props);
        return this;
    }

    @Override
    public void dump(PrintWriter writer, int indent) {
        writer.println("{");
        boolean first = true;
        for (JsonObjectProperty p : this.properties) {
            if (first) {
                first = false;
            } else {
                writer.println(",");
            }
            JsonObject.printIndent(writer, indent + 2);
            writer.print('\"');
            writer.print(p.name);
            writer.print("\": ");
            if (p.value == null) {
                Json.nullValue().dump(writer, indent + 2);
                continue;
            }
            p.value.toJson().dump(writer, indent + 2);
        }
        writer.println();
        JsonObject.printIndent(writer, indent);
        writer.print("}");
    }

    public static class JsonObjectProperty {
        private final String name;
        private final JsonConvertible value;

        JsonObjectProperty(String name, JsonConvertible value2) {
            this.name = name;
            this.value = value2;
        }
    }
}

