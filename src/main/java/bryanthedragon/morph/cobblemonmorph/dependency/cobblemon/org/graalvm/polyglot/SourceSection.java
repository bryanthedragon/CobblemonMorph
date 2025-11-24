
package org.graalvm.polyglot;

import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

public final class SourceSection {
    final Source source;
    final AbstractPolyglotImpl.AbstractSourceSectionDispatch dispatch;
    final Object receiver;

    SourceSection(Source source, AbstractPolyglotImpl.AbstractSourceSectionDispatch dispatch, Object receiver) {
        this.source = source;
        this.dispatch = dispatch;
        this.receiver = receiver;
    }

    public boolean isAvailable() {
        return this.dispatch.isAvailable(this.receiver);
    }

    public boolean hasLines() {
        return this.dispatch.hasLines(this.receiver);
    }

    public boolean hasColumns() {
        return this.dispatch.hasColumns(this.receiver);
    }

    public boolean hasCharIndex() {
        return this.dispatch.hasCharIndex(this.receiver);
    }

    public Source getSource() {
        return this.source;
    }

    public int getStartLine() {
        return this.dispatch.getStartLine(this.receiver);
    }

    public int getStartColumn() {
        return this.dispatch.getStartColumn(this.receiver);
    }

    public int getEndLine() {
        return this.dispatch.getEndLine(this.receiver);
    }

    public int getEndColumn() {
        return this.dispatch.getEndColumn(this.receiver);
    }

    public int getCharIndex() {
        return this.dispatch.getCharIndex(this.receiver);
    }

    public int getCharLength() {
        return this.dispatch.getCharLength(this.receiver);
    }

    public int getCharEndIndex() {
        return this.dispatch.getCharEndIndex(this.receiver);
    }

    @Deprecated(since="19.0")
    public CharSequence getCode() {
        return this.dispatch.getCode(this.receiver);
    }

    public CharSequence getCharacters() {
        return this.dispatch.getCode(this.receiver);
    }

    public String toString() {
        return this.dispatch.toString(this.receiver);
    }

    public int hashCode() {
        return this.dispatch.hashCode(this.receiver);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        Object otherImpl = obj;
        if (otherImpl instanceof SourceSection) {
            otherImpl = ((SourceSection)obj).receiver;
        }
        return this.dispatch.equals(this.receiver, otherImpl);
    }
}

