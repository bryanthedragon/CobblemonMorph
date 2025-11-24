
package com.oracle.truffle.api.source;

import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;

final class SourceSectionLoaded
extends SourceSection {
    final int charIndex;
    final int charLength;

    SourceSectionLoaded(Source source, int charIndex, int charLength) {
        super(source);
        this.charIndex = charIndex;
        this.charLength = charLength;
    }

    @Override
    public boolean isAvailable() {
        return true;
    }

    @Override
    public boolean hasLines() {
        return true;
    }

    @Override
    public boolean hasColumns() {
        return true;
    }

    @Override
    public boolean hasCharIndex() {
        return true;
    }

    @Override
    boolean isValid() {
        return this.charIndex + this.charLength <= this.getSource().getCharacters().length();
    }

    @Override
    public int getStartLine() {
        if (!this.isValid()) {
            return 1;
        }
        return this.source.getLineNumber(this.getCharIndex());
    }

    @Override
    public int getStartColumn() {
        if (!this.isValid()) {
            return 1;
        }
        return this.source.getColumnNumber(this.getCharIndex());
    }

    @Override
    public int getEndLine() {
        if (!this.isValid()) {
            return 1;
        }
        return this.source.getLineNumber(this.getCharIndex() + Math.max(0, this.getCharLength() - 1));
    }

    @Override
    public int getEndColumn() {
        if (!this.isValid()) {
            return 1;
        }
        return this.source.getColumnNumber(this.getCharIndex() + Math.max(0, this.getCharLength() - 1));
    }

    @Override
    public int getCharIndex() {
        return this.charIndex;
    }

    @Override
    public int getCharLength() {
        return this.charLength;
    }

    @Override
    public int getCharEndIndex() {
        return this.getCharIndex() + this.getCharLength();
    }

    @Override
    public CharSequence getCharacters() {
        if (!this.isValid()) {
            return "";
        }
        return this.source.getCharacters().subSequence(this.getCharIndex(), this.getCharEndIndex());
    }

    @Override
    public int hashCode() {
        if (!this.isAvailable()) {
            return System.identityHashCode(this);
        }
        int prime = 31;
        int result = 1;
        result = 31 * result + this.charIndex;
        result = 31 * result + this.charLength;
        result = 31 * result + this.source.hashCode();
        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null) {
            return false;
        }
        if (obj.getClass() != SourceSectionLoaded.class) {
            return false;
        }
        SourceSectionLoaded other = (SourceSectionLoaded)obj;
        if (this.charIndex != other.charIndex) {
            return false;
        }
        if (this.charLength != other.charLength) {
            return false;
        }
        return !(this.source == null ? other.source != null : !this.source.equals(other.source));
    }
}

