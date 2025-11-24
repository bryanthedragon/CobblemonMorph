
package com.oracle.truffle.regex.tregex.parser;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public class RegexProperties
implements JsonConvertible {
    private static final int FLAG_ALTERNATIONS = 1;
    private static final int FLAG_CAPTURE_GROUPS = 2;
    private static final int FLAG_CHAR_CLASSES = 4;
    private static final int FLAG_LONE_SURROGATES = 8;
    private static final int FLAG_QUANTIFIERS = 16;
    private static final int FLAG_LOOK_AHEAD_ASSERTIONS = 32;
    private static final int FLAG_NEGATIVE_LOOK_AHEAD_ASSERTIONS = 64;
    private static final int FLAG_LOOK_BEHIND_ASSERTIONS = 128;
    private static final int FLAG_NON_LITERAL_LOOK_BEHIND_ASSERTIONS = 256;
    private static final int FLAG_NEGATIVE_LOOK_BEHIND_ASSERTIONS = 512;
    private static final int FLAG_LARGE_COUNTED_REPETITIONS = 1024;
    private static final int FLAG_CHAR_CLASSES_CAN_BE_MATCHED_WITH_MASK = 2048;
    private static final int FLAG_FIXED_CODEPOINT_WIDTH = 4096;
    private static final int FLAG_CAPTURE_GROUPS_IN_LOOK_AROUND_ASSERTIONS = 8192;
    private static final int FLAG_EMPTY_CAPTURE_GROUPS = 16384;
    private static final int FLAG_ATOMIC_GROUPS = 32768;
    private static final int FLAG_BACK_REFERENCES = 65536;
    private int flags = 6144;
    private int innerLiteralStart = -1;
    private int innerLiteralEnd = -1;

    protected boolean getFlag(int flag) {
        return (this.flags & flag) != 0;
    }

    private void setFlag(int flag) {
        this.flags |= flag;
    }

    private void clearFlag(int flag) {
        this.flags &= ~flag;
    }

    public boolean hasAlternations() {
        return this.getFlag(1);
    }

    public void setAlternations() {
        this.setFlag(1);
    }

    public boolean hasCaptureGroups() {
        return this.getFlag(2);
    }

    public void setCaptureGroups() {
        this.setFlag(2);
    }

    public boolean hasEmptyCaptureGroups() {
        return this.getFlag(16384);
    }

    public void setEmptyCaptureGroups() {
        this.setFlag(16384);
    }

    public boolean hasAtomicGroups() {
        return this.getFlag(32768);
    }

    public void setAtomicGroups() {
        this.setFlag(32768);
    }

    public boolean hasCharClasses() {
        return this.getFlag(4);
    }

    public void setCharClasses() {
        this.setFlag(4);
    }

    public boolean hasLoneSurrogates() {
        return this.getFlag(8);
    }

    public void setLoneSurrogates() {
        this.setFlag(8);
    }

    public boolean hasQuantifiers() {
        return this.getFlag(16);
    }

    public void setQuantifiers() {
        this.setFlag(16);
    }

    public boolean hasLookAroundAssertions() {
        return this.getFlag(736);
    }

    public boolean hasLookAheadAssertions() {
        return this.getFlag(32);
    }

    public void setLookAheadAssertions() {
        this.setFlag(32);
    }

    public boolean hasNegativeLookAheadAssertions() {
        return this.getFlag(64);
    }

    public void setNegativeLookAheadAssertions() {
        this.setFlag(64);
    }

    public boolean hasLookBehindAssertions() {
        return this.getFlag(128);
    }

    public void setLookBehindAssertions() {
        this.setFlag(128);
    }

    public boolean hasNonLiteralLookBehindAssertions() {
        return this.getFlag(256);
    }

    public void setNonLiteralLookBehindAssertions() {
        this.setFlag(256);
    }

    public boolean hasNegativeLookBehindAssertions() {
        return this.getFlag(512);
    }

    public void setNegativeLookBehindAssertions() {
        this.setFlag(512);
    }

    public boolean hasLargeCountedRepetitions() {
        return this.getFlag(1024);
    }

    public void setLargeCountedRepetitions() {
        this.setFlag(1024);
    }

    public boolean charClassesCanBeMatchedWithMask() {
        return this.getFlag(2048);
    }

    public void unsetCharClassesCanBeMatchedWithMask() {
        this.clearFlag(2048);
    }

    public boolean isFixedCodePointWidth() {
        return this.getFlag(4096);
    }

    public void unsetFixedCodePointWidth() {
        this.clearFlag(4096);
    }

    public void setInnerLiteral(int start2, int end2) {
        this.innerLiteralStart = start2;
        this.innerLiteralEnd = end2;
    }

    public boolean hasInnerLiteral() {
        return this.innerLiteralStart >= 0;
    }

    public int getInnerLiteralStart() {
        return this.innerLiteralStart;
    }

    public int getInnerLiteralEnd() {
        return this.innerLiteralEnd;
    }

    public boolean hasCaptureGroupsInLookAroundAssertions() {
        return this.getFlag(8192);
    }

    public void setCaptureGroupsInLookAroundAssertions() {
        this.setFlag(8192);
    }

    public boolean hasBackReferences() {
        return this.getFlag(65536);
    }

    public void setBackReferences() {
        this.setFlag(65536);
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        return Json.obj(Json.prop("alternations", this.hasAlternations()), Json.prop("charClasses", this.hasCharClasses()), Json.prop("captureGroups", this.hasCaptureGroups()), Json.prop("lookAheadAssertions", this.hasLookAheadAssertions()), Json.prop("negativeLookAheadAssertions", this.hasNegativeLookAheadAssertions()), Json.prop("lookBehindAssertions", this.hasLookBehindAssertions()), Json.prop("nonLiteralLookBehindAssertions", this.hasNonLiteralLookBehindAssertions()), Json.prop("negativeLookBehindAssertions", this.hasNegativeLookBehindAssertions()), Json.prop("largeCountedRepetitions", this.hasLargeCountedRepetitions()), Json.prop("captureGroupsInLookAroundAssertions", this.hasCaptureGroupsInLookAroundAssertions()), Json.prop("backReferences", this.hasBackReferences()));
    }
}

