
package com.oracle.truffle.regex.tregex.util.json;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public interface JsonConvertible {
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson();
}

