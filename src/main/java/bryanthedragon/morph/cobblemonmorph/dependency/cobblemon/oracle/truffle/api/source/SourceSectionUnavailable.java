
package com.oracle.truffle.api.source;

import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;

final class SourceSectionUnavailable
extends SourceSection {
    SourceSectionUnavailable(Source source) {
        super(source);
    }

    @Override
    public boolean isAvailable() {
        return false;
    }

    @Override
    public boolean hasLines() {
        return false;
    }

    @Override
    public boolean hasColumns() {
        return false;
    }

    @Override
    public boolean hasCharIndex() {
        return false;
    }

    @Override
    boolean isValid() {
        return false;
    }

    @Override
    public int getStartLine() {
        return 1;
    }

    @Override
    public int getStartColumn() {
        return 1;
    }

    @Override
    public int getEndLine() {
        return 1;
    }

    @Override
    public int getEndColumn() {
        return 1;
    }

    @Override
    public int getCharIndex() {
        return 0;
    }

    @Override
    public int getCharLength() {
        return 0;
    }

    @Override
    public int getCharEndIndex() {
        return 0;
    }

    @Override
    public CharSequence getCharacters() {
        return "";
    }

    @Override
    public int hashCode() {
        return System.identityHashCode(this);
    }

    @Override
    public boolean equals(Object obj) {
        return this == obj;
    }
}

