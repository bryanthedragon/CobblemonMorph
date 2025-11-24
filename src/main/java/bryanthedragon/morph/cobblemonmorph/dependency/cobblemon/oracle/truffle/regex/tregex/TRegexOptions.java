
package com.oracle.truffle.regex.tregex;

public class TRegexOptions {
    public static final int TRegexGenerateDFAThresholdCalls = 100;
    public static final int TRegexGenerateDFAThresholdCharacters = 2000000;
    public static final boolean TRegexEnableTraceFinder = true;
    public static final int TRegexTraceFinderMaxNumberOfResults = 254;
    public static final boolean TRegexEnableNodeSplitter = false;
    public static final int TRegexMaxDFASizeAfterNodeSplitting = 4000;
    public static final int TRegexRangeToBitSetConversionThreshold = 3;
    public static final int TRegexParserTreeMaxSize = Integer.MAX_VALUE;
    public static final int TRegexParserTreeMaxNumberOfSequencesInGroup = Short.MAX_VALUE;
    public static final int TRegexParserTreeMaxNumberOfTermsInSequence = Short.MAX_VALUE;
    public static final int TRegexMaxParseTreeSizeForDFA = 4000;
    public static final int TRegexMaxNFASize = 3500;
    public static final int TRegexMaxNumberOfASTSuccessorsInOneASTStep = Short.MAX_VALUE;
    public static final int TRegexMaxDFASize = 2400;
    public static final int TRegexMaxDFATransitions = 3000;
    public static final int TRegexMaxDFACGPartialTransitions = 3000;
    public static final int TRegexQuantifierUnrollThresholdSingleCC = 20;
    public static final int TRegexQuantifierUnrollThresholdGroup = 6;
    public static final int TRegexMaxNumberOfCaptureGroups = Short.MAX_VALUE;
    public static final int TRegexMaxNumberOfCaptureGroupsForDFA = 127;
    public static final int TRegexMaxNumberOfNFAStatesInOneDFATransition = 255;
    public static final int TRegexMaxPureNFASize = 1000000;
    public static final int TRegexMaxPureNFATransitions = 1000000;
    public static final int TRegexMaxBackTrackerMergeExplodeSize = 4000;
    public static final int TRegexMaxTransitionsInTrivialExecutor = 100;
}

