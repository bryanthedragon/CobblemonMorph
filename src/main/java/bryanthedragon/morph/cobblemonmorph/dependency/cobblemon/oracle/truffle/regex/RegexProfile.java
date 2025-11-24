
package com.oracle.truffle.regex;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.tregex.parser.Counter;

public final class RegexProfile {
    private static final int EVALUATION_TRIP_POINT = 512;
    private final Counter.ThreadSafeCounter calls = new Counter.ThreadSafeCounter();
    private final Counter.ThreadSafeCounter matches = new Counter.ThreadSafeCounter();
    private final Counter.ThreadSafeCounter captureGroupAccesses = new Counter.ThreadSafeCounter();
    private final Counter.ThreadSafeCounter processedCharacters = new Counter.ThreadSafeCounter();
    private double avgMatchLength = 0.0;
    private double avgMatchedPortionOfSearchSpace = 0.0;

    public void incCalls() {
        this.calls.inc();
    }

    public void resetCalls() {
        this.calls.reset();
    }

    public void incMatches() {
        this.matches.inc();
    }

    public void profileCaptureGroupAccess(int matchLength, int numberOfCharsTraversed) {
        this.captureGroupAccesses.inc();
        this.avgMatchLength += ((double)matchLength - this.avgMatchLength) / (double)this.captureGroupAccesses.getCount();
        double matchedPortion = (double)matchLength / (double)numberOfCharsTraversed;
        this.avgMatchedPortionOfSearchSpace += (matchedPortion - this.avgMatchedPortionOfSearchSpace) / (double)this.captureGroupAccesses.getCount();
    }

    public boolean atEvaluationTripPoint() {
        return this.calls.getCount() > 0 && this.calls.getCount() % 512 == 0;
    }

    private double matchRatio() {
        assert (this.calls.getCount() > 0);
        return (double)this.matches.getCount() / (double)this.calls.getCount();
    }

    private double cgAccessRatio() {
        assert (this.matches.getCount() > 0);
        return (double)this.captureGroupAccesses.getCount() / (double)this.matches.getCount();
    }

    public boolean shouldGenerateDFA(int inputLength) {
        return this.calls.getCount() >= 100 || Integer.toUnsignedLong(this.processedCharacters.getCount() + inputLength) >= 2000000L;
    }

    public void incProcessedCharacters(int numberOfCharacters) {
        this.processedCharacters.inc(numberOfCharacters);
    }

    public boolean shouldUseEagerMatching() {
        return this.matchRatio() > 0.5 && this.cgAccessRatio() > 0.5 && this.avgMatchedPortionOfSearchSpace > 0.4;
    }

    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return String.format("calls: %d, matches: %d (%.2f%%), cg accesses: %d (%.2f%%), avg matched portion of search space: %.2f%%", this.calls.getCount(), this.matches.getCount(), this.matchRatio() * 100.0, this.captureGroupAccesses.getCount(), this.cgAccessRatio() * 100.0, this.avgMatchedPortionOfSearchSpace * 100.0);
    }

    public static interface TracksRegexProfile {
        public RegexProfile getRegexProfile();
    }
}

