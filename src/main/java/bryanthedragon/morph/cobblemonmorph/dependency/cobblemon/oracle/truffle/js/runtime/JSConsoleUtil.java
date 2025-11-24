
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import java.util.HashMap;
import java.util.Map;

public class JSConsoleUtil {
    private Map<TruffleString, Integer> countMap;
    private Map<TruffleString, Long> timeMap;
    private int consoleIndentation = 0;

    public Map<TruffleString, Integer> getCountMap() {
        CompilerAsserts.neverPartOfCompilation();
        if (this.countMap == null) {
            this.countMap = new HashMap<TruffleString, Integer>();
        }
        return this.countMap;
    }

    public Map<TruffleString, Long> getTimeMap() {
        CompilerAsserts.neverPartOfCompilation();
        if (this.timeMap == null) {
            this.timeMap = new HashMap<TruffleString, Long>();
        }
        return this.timeMap;
    }

    public int getConsoleIndentation() {
        return this.consoleIndentation;
    }

    public void incConsoleIndentation() {
        ++this.consoleIndentation;
    }

    public void decConsoleIndentation() {
        if (this.consoleIndentation > 0) {
            --this.consoleIndentation;
        }
    }

    @CompilerDirectives.TruffleBoundary
    public String getConsoleIndentationString() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < this.consoleIndentation; ++i) {
            sb.append("  ");
        }
        return sb.toString();
    }
}

