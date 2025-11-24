
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.source.SourceSection;
import org.graalvm.polyglot.impl.AbstractPolyglotImpl;

class PolyglotSourceSectionDispatch
extends AbstractPolyglotImpl.AbstractSourceSectionDispatch {
    protected PolyglotSourceSectionDispatch(AbstractPolyglotImpl engineImpl) {
        super(engineImpl);
    }

    @Override
    public boolean isAvailable(Object impl) {
        SourceSection section = (SourceSection)impl;
        return section.isAvailable();
    }

    @Override
    public boolean hasLines(Object impl) {
        SourceSection section = (SourceSection)impl;
        return section.hasLines();
    }

    @Override
    public boolean hasColumns(Object impl) {
        SourceSection section = (SourceSection)impl;
        return section.hasColumns();
    }

    @Override
    public boolean hasCharIndex(Object impl) {
        SourceSection section = (SourceSection)impl;
        return section.hasCharIndex();
    }

    @Override
    public int getStartLine(Object impl) {
        SourceSection section = (SourceSection)impl;
        return section.getStartLine();
    }

    @Override
    public int getStartColumn(Object impl) {
        SourceSection section = (SourceSection)impl;
        return section.getStartColumn();
    }

    @Override
    public int getEndLine(Object impl) {
        SourceSection section = (SourceSection)impl;
        return section.getEndLine();
    }

    @Override
    public int getEndColumn(Object impl) {
        SourceSection section = (SourceSection)impl;
        return section.getEndColumn();
    }

    @Override
    public int getCharIndex(Object impl) {
        SourceSection section = (SourceSection)impl;
        return section.getCharIndex();
    }

    @Override
    public int getCharLength(Object impl) {
        SourceSection section = (SourceSection)impl;
        return section.getCharLength();
    }

    @Override
    public int getCharEndIndex(Object impl) {
        SourceSection section = (SourceSection)impl;
        return section.getCharEndIndex();
    }

    @Override
    public CharSequence getCode(Object impl) {
        SourceSection section = (SourceSection)impl;
        return section.getCharacters();
    }

    @Override
    public String toString(Object impl) {
        SourceSection section = (SourceSection)impl;
        return section.toString();
    }

    @Override
    public int hashCode(Object impl) {
        SourceSection section = (SourceSection)impl;
        return section.hashCode();
    }

    @Override
    public boolean equals(Object impl, Object obj) {
        SourceSection section = (SourceSection)impl;
        return section.equals(obj);
    }
}

