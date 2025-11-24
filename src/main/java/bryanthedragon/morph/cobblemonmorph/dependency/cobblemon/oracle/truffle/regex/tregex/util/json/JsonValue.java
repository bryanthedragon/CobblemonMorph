
package com.oracle.truffle.regex.tregex.util.json;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.nio.file.StandardOpenOption;

public abstract class JsonValue
implements JsonConvertible {
    @Override
    public JsonValue toJson() {
        return this;
    }

    @CompilerDirectives.TruffleBoundary
    public void dump(TruffleFile path) {
        try (PrintWriter writer = new PrintWriter(path.newBufferedWriter(StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING));){
            this.dump(writer, 0);
            writer.flush();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public abstract void dump(PrintWriter var1, int var2);

    static void printIndent(PrintWriter writer, int indent) {
        for (int i = 0; i < indent; ++i) {
            writer.print(' ');
        }
    }

    public String toString() {
        StringWriter stringWriter = new StringWriter();
        this.dump(new PrintWriter(stringWriter), 0);
        return stringWriter.toString();
    }
}

