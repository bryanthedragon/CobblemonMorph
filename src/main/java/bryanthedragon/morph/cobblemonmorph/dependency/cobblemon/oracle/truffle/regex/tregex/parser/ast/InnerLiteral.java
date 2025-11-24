
package com.oracle.truffle.regex.tregex.parser.ast;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.tregex.string.AbstractString;

public class InnerLiteral {
    private final AbstractString literal;
    private final AbstractString mask;
    private final int maxPrefixSize;
    private final TruffleString literalTString;
    private final TruffleString.WithMask maskTString;

    public InnerLiteral(AbstractString literal, AbstractString mask, int maxPrefixSize) {
        this.literal = literal;
        this.mask = mask;
        this.maxPrefixSize = maxPrefixSize;
        this.literalTString = literal.asTString();
        this.maskTString = mask == null ? null : mask.asTStringMask(this.literalTString);
    }

    public AbstractString getLiteral() {
        return this.literal;
    }

    public Object getLiteralContent(boolean tString) {
        CompilerAsserts.partialEvaluationConstant(tString);
        return tString ? this.literalTString : this.literal.content();
    }

    public AbstractString getMask() {
        return this.mask;
    }

    public Object getMaskContent(boolean tString) {
        CompilerAsserts.partialEvaluationConstant(tString);
        return this.mask == null ? null : (tString ? this.maskTString : this.mask.content());
    }

    public int getMaxPrefixSize() {
        return this.maxPrefixSize;
    }
}

