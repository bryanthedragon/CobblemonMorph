package com.oracle.truffle.regex.tregex.util.json;

import com.oracle.truffle.api.CompilerDirectives;

public interface JsonConvertible {
   @CompilerDirectives.TruffleBoundary
   JsonValue toJson();
}
