
package com.oracle.truffle.api.source;

import com.oracle.truffle.api.source.Source;

public abstract class SourceSection {
    final Source source;

    SourceSection(Source source) {
        assert (source != null);
        this.source = source;
    }

    public abstract boolean isAvailable();

    abstract boolean isValid();

    public abstract boolean hasLines();

    public abstract boolean hasColumns();

    public abstract boolean hasCharIndex();

    public final Source getSource() {
        return this.source;
    }

    public abstract int getStartLine();

    public abstract int getStartColumn();

    public abstract int getEndLine();

    public abstract int getEndColumn();

    public abstract int getCharIndex();

    public abstract int getCharLength();

    public abstract int getCharEndIndex();

    public abstract CharSequence getCharacters();

    public final String toString() {
        StringBuilder b = new StringBuilder();
        b.append("SourceSection(source=").append(this.getSource().getName());
        if (this.isAvailable()) {
            if (this.hasLines()) {
                b.append(" [").append(this.getStartLine());
                if (this.hasColumns()) {
                    b.append(':').append(this.getStartColumn());
                }
                b.append(" - ").append(this.getEndLine());
                if (this.hasColumns()) {
                    b.append(':').append(this.getEndColumn());
                }
                b.append("]");
            }
            if (this.hasCharIndex()) {
                b.append(", index=").append(this.getCharIndex());
                b.append(", length=").append(this.getCharLength());
            }
            if (this.isValid()) {
                if (this.source.hasCharacters()) {
                    b.append(", characters=").append(this.getCharacters().toString().replaceAll("\\n", "\\\\n"));
                } else {
                    b.append(", characters not known");
                }
            } else {
                b.append(", valid=false");
            }
        } else {
            b.append(" available=false");
        }
        b.append(")");
        return b.toString();
    }

    public abstract int hashCode();

    public abstract boolean equals(Object var1);
}

