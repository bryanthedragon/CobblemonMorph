
package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSTrimWhitespaceNodeGen;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;

public abstract class JSTrimWhitespaceNode
extends JavaScriptBaseNode {
    public static JSTrimWhitespaceNode create() {
        return JSTrimWhitespaceNodeGen.create();
    }

    public abstract TruffleString executeString(TruffleString var1);

    protected boolean startsOrEndsWithWhitespace(TruffleString.ReadCharUTF16Node readRawNode, TruffleString string, ConditionProfile isFastNonWhitespace, ConditionProfile isFastWhitespace) {
        assert (Strings.length(string) > 0);
        return JSTrimWhitespaceNode.isWhiteSpace(readRawNode, string, 0, isFastNonWhitespace, isFastWhitespace) || JSTrimWhitespaceNode.isWhiteSpace(readRawNode, string, Strings.length(string) - 1, isFastNonWhitespace, isFastWhitespace);
    }

    @Specialization(guards={"stringLength(string) == 0"})
    protected static TruffleString doStringZero(TruffleString string) {
        return string;
    }

    @Specialization(guards={"stringLength(string) > 0", "!startsOrEndsWithWhitespace(readRawNode, string, isFastNonWhitespace, isFastWhitespace)"}, limit="1")
    protected static TruffleString doStringNoWhitespace(TruffleString string, @Cached @Cached.Shared(value="readChar") TruffleString.ReadCharUTF16Node readRawNode, @Cached @Cached.Shared(value="isFastNonWhitespace") ConditionProfile isFastNonWhitespace, @Cached @Cached.Shared(value="isFastWhitespace") ConditionProfile isFastWhitespace) {
        return string;
    }

    @Specialization(guards={"stringLength(string) > 0", "startsOrEndsWithWhitespace(readRawNode, string, isFastNonWhitespace, isFastWhitespace)"}, limit="1")
    protected final TruffleString doString(TruffleString string, @Cached @Cached.Shared(value="readChar") TruffleString.ReadCharUTF16Node readRawNode, @Cached @Cached.Shared(value="isFastNonWhitespace") ConditionProfile isFastNonWhitespace, @Cached @Cached.Shared(value="isFastWhitespace") ConditionProfile isFastWhitespace, @Cached TruffleString.SubstringByteIndexNode substringNode, @Cached BranchProfile startsWithWhitespaceBranch, @Cached BranchProfile endsWithWhitespaceBranch, @Cached ConditionProfile isEmpty) {
        int len = Strings.length(string);
        int firstIdx = 0;
        if (JSTrimWhitespaceNode.isWhiteSpace(readRawNode, string, 0, isFastNonWhitespace, isFastWhitespace)) {
            startsWithWhitespaceBranch.enter();
            firstIdx = JSRuntime.firstNonWhitespaceIndex(string, false, readRawNode);
        }
        int lastIdx = len - 1;
        if (JSTrimWhitespaceNode.isWhiteSpace(readRawNode, string, len - 1, isFastNonWhitespace, isFastWhitespace)) {
            endsWithWhitespaceBranch.enter();
            lastIdx = JSRuntime.lastNonWhitespaceIndex(string, false, readRawNode);
        }
        if (isEmpty.profile(firstIdx > lastIdx)) {
            return Strings.EMPTY_STRING;
        }
        return Strings.substring(JavaScriptLanguage.get(this).getJSContext(), substringNode, string, firstIdx, lastIdx + 1 - firstIdx);
    }

    private static boolean isWhiteSpace(TruffleString.ReadCharUTF16Node readRawNode, TruffleString str, int index, ConditionProfile isFastNonWhitespace, ConditionProfile isFastWhitespace) {
        char c = Strings.charAt(readRawNode, str, index);
        if (isFastNonWhitespace.profile(' ' < c && c < '\u00a0')) {
            return false;
        }
        if (isFastWhitespace.profile(c == ' ' || c == '\n' || c == '\r' || c == '\t')) {
            return true;
        }
        return JSRuntime.isWhiteSpace(c);
    }
}

