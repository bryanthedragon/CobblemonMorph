
package com.oracle.truffle.js.runtime.util;

import com.oracle.truffle.api.nodes.NodeCloneable;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.Strings;

public final class StringBuilderProfile
extends NodeCloneable {
    private static final int MAX_INT_STRING_LENGTH = 11;
    private static final int MAX_LONG_STRING_LENGTH = 20;
    private final int stringLengthLimit;
    private final BranchProfile errorBranch;

    private StringBuilderProfile(int stringLengthLimit) {
        this.stringLengthLimit = stringLengthLimit;
        this.errorBranch = BranchProfile.create();
    }

    public static StringBuilderProfile create(int stringLengthLimit) {
        return new StringBuilderProfile(stringLengthLimit);
    }

    public TruffleStringBuilder newStringBuilder() {
        return Strings.builderCreate();
    }

    public TruffleStringBuilder newStringBuilder(int capacity) {
        return Strings.builderCreate(capacity);
    }

    public static TruffleString toString(TruffleStringBuilder.ToStringNode node, TruffleStringBuilder builder) {
        return Strings.builderToString(node, builder);
    }

    public void append(TruffleStringBuilder.AppendStringNode node, TruffleStringBuilder builder, TruffleString str) {
        if (Strings.builderLength(builder) + Strings.length(str) > this.stringLengthLimit) {
            this.errorBranch.enter();
            throw Errors.createRangeErrorInvalidStringLength();
        }
        Strings.builderAppend(node, builder, str);
    }

    public void append(TruffleStringBuilder.AppendCharUTF16Node node, TruffleStringBuilder builder, char c) {
        if (Strings.builderLength(builder) + 1 > this.stringLengthLimit) {
            this.errorBranch.enter();
            throw Errors.createRangeErrorInvalidStringLength();
        }
        Strings.builderAppend(node, builder, c);
    }

    public void append(TruffleStringBuilder.AppendIntNumberNode node, TruffleStringBuilder builder, int intValue) {
        if (Strings.builderLength(builder) + 11 > this.stringLengthLimit) {
            this.errorBranch.enter();
            throw Errors.createRangeErrorInvalidStringLength();
        }
        Strings.builderAppend(node, builder, intValue);
    }

    public void append(TruffleStringBuilder.AppendLongNumberNode node, TruffleStringBuilder builder, long longValue) {
        if (Strings.builderLength(builder) + 20 > this.stringLengthLimit) {
            this.errorBranch.enter();
            throw Errors.createRangeErrorInvalidStringLength();
        }
        Strings.builderAppend(node, builder, longValue);
    }

    public void append(TruffleStringBuilder.AppendSubstringByteIndexNode node, TruffleStringBuilder builder, TruffleString charSequence, int start2, int end2) {
        assert (start2 <= end2);
        int length = end2 - start2;
        if (Strings.builderLength(builder) + length > this.stringLengthLimit) {
            this.errorBranch.enter();
            throw Errors.createRangeErrorInvalidStringLength();
        }
        Strings.builderAppend(node, builder, charSequence, start2, end2);
    }

    public static int length(TruffleStringBuilder builder) {
        return Strings.builderLength(builder);
    }

    @Override
    protected Object clone() {
        return new StringBuilderProfile(this.stringLengthLimit);
    }
}

