package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.regex.RegexRootNode;
import com.oracle.truffle.regex.RegexSource;
import com.oracle.truffle.regex.tregex.matchers.CharMatcher;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorLocals;
import com.oracle.truffle.regex.tregex.nodes.TRegexExecutorNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputIndexOfNode;
import com.oracle.truffle.regex.tregex.nodes.input.InputIndexOfStringNode;
import java.util.Arrays;

public final class TRegexDFAExecutorNode extends TRegexExecutorNode {
   private static final int IP_TRANSITION_MARKER = 32768;
   public static final int NO_MATCH = -2;
   private final TRegexDFAExecutorProperties props;
   private final int maxNumberOfNFAStates;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final DFAAbstractStateNode[] states;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final int[] cgResultOrder;
   private final TRegexDFAExecutorDebugRecorder debugRecorder;
   @Node.Child
   private InputIndexOfNode indexOfNode;
   @Node.Child
   private InputIndexOfStringNode indexOfStringNode;
   @Node.Child
   private TRegexDFAExecutorNode innerLiteralPrefixMatcher;

   public TRegexDFAExecutorNode(
      RegexSource source,
      TRegexDFAExecutorProperties props,
      int numberOfCaptureGroups,
      int maxNumberOfNFAStates,
      DFAAbstractStateNode[] states,
      TRegexDFAExecutorDebugRecorder debugRecorder,
      TRegexDFAExecutorNode innerLiteralPrefixMatcher
   ) {
      this(
         source,
         props,
         numberOfCaptureGroups,
         calcNumberOfTransitions(states),
         maxNumberOfNFAStates,
         states,
         props.isGenericCG() && maxNumberOfNFAStates > 1 ? initResultOrder(maxNumberOfNFAStates, numberOfCaptureGroups, props) : null,
         debugRecorder,
         innerLiteralPrefixMatcher
      );
   }

   public TRegexDFAExecutorNode(
      RegexSource source,
      TRegexDFAExecutorProperties props,
      int numberOfCaptureGroups,
      int numberOfTransitions,
      int maxNumberOfNFAStates,
      DFAAbstractStateNode[] states,
      int[] cgResultOrder,
      TRegexDFAExecutorDebugRecorder debugRecorder,
      TRegexDFAExecutorNode innerLiteralPrefixMatcher
   ) {
      super(source, numberOfCaptureGroups, numberOfTransitions);
      this.props = props;
      this.maxNumberOfNFAStates = maxNumberOfNFAStates;
      this.states = states;
      this.cgResultOrder = cgResultOrder;
      this.debugRecorder = debugRecorder;
      this.innerLiteralPrefixMatcher = innerLiteralPrefixMatcher;
   }

   private TRegexDFAExecutorNode(TRegexDFAExecutorNode copy, TRegexDFAExecutorNode innerLiteralPrefixMatcher) {
      this(
         copy.getSource(),
         copy.props,
         copy.getNumberOfCaptureGroups(),
         copy.getNumberOfTransitions(),
         copy.maxNumberOfNFAStates,
         copy.states,
         copy.cgResultOrder,
         copy.debugRecorder,
         innerLiteralPrefixMatcher
      );
   }

   public TRegexDFAExecutorNode shallowCopy() {
      return new TRegexDFAExecutorNode(this, this.innerLiteralPrefixMatcher == null ? null : this.innerLiteralPrefixMatcher.shallowCopy());
   }

   private DFAInitialStateNode getInitialState() {
      return (DFAInitialStateNode)this.states[0];
   }

   public int getPrefixLength() {
      return this.getInitialState().getPrefixLength();
   }

   public boolean isAnchored() {
      return !this.getInitialState().hasUnAnchoredEntry();
   }

   @Override
   public String getName() {
      return "dfa";
   }

   @Override
   public boolean isForward() {
      return this.props.isForward();
   }

   public boolean isBackward() {
      return !this.props.isForward();
   }

   public boolean isSearching() {
      return this.props.isSearching();
   }

   public boolean isSimpleCG() {
      return this.props.isSimpleCG();
   }

   public boolean isGenericCG() {
      return this.props.isGenericCG();
   }

   public boolean isRegressionTestMode() {
      return this.props.isRegressionTestMode();
   }

   public int getNumberOfStates() {
      return this.states.length;
   }

   private static int calcNumberOfTransitions(DFAAbstractStateNode[] states) {
      int sum = 0;

      for (DFAAbstractStateNode state : states) {
         sum += state.getSuccessors().length;
         if (state instanceof DFAStateNode
            && !((DFAStateNode)state).treeTransitionMatching()
            && ((DFAStateNode)state).getSequentialMatchers().getNoMatchSuccessor() >= 0) {
            sum++;
         }
      }

      return sum;
   }

   public boolean recordExecution() {
      return this.debugRecorder != null;
   }

   public TRegexDFAExecutorDebugRecorder getDebugRecorder() {
      return this.debugRecorder;
   }

   InputIndexOfNode getIndexOfNode() {
      if (this.indexOfNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.indexOfNode = this.insert(InputIndexOfNode.create());
      }

      return this.indexOfNode;
   }

   InputIndexOfStringNode getIndexOfStringNode() {
      if (this.indexOfStringNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.indexOfStringNode = this.insert(InputIndexOfStringNode.create());
      }

      return this.indexOfStringNode;
   }

   @Override
   public TRegexExecutorLocals createLocals(Object input, int fromIndex, int index, int maxIndex) {
      return new TRegexDFAExecutorLocals(input, fromIndex, index, maxIndex, this.createCGData());
   }

   @Override
   public boolean writesCaptureGroups() {
      return this.isSimpleCG();
   }

   private DFACaptureGroupTrackingData createCGData() {
      if (this.isSimpleCG()) {
         return new DFACaptureGroupTrackingData(
            null, createResultsArray(this.resultLength()), this.props.isSimpleCGMustCopy() ? new int[this.resultLength()] : null
         );
      } else {
         return this.isGenericCG()
            ? new DFACaptureGroupTrackingData(
               this.maxNumberOfNFAStates == 1 ? null : Arrays.copyOf(this.cgResultOrder, this.cgResultOrder.length),
               createResultsArray(this.maxNumberOfNFAStates * this.resultLength()),
               new int[this.resultLength()]
            )
            : null;
      }
   }

   private int resultLength() {
      return this.getNumberOfCaptureGroups() * 2 + (this.props.tracksLastGroup() ? 1 : 0);
   }

   private static int[] createResultsArray(int length) {
      int[] results = new int[length];
      Arrays.fill(results, -1);
      return results;
   }

   @ExplodeLoop(kind = ExplodeLoop.LoopExplosionKind.MERGE_EXPLODE)
   @Override
   public Object execute(VirtualFrame frame, final TRegexExecutorLocals abstractLocals, final TruffleString.CodeRange codeRange, boolean tString) {
      TRegexDFAExecutorLocals locals = (TRegexDFAExecutorLocals)abstractLocals;
      CompilerDirectives.ensureVirtualized(locals);
      CompilerAsserts.partialEvaluationConstant(this.states);
      CompilerAsserts.partialEvaluationConstant(this.states.length);
      CompilerAsserts.partialEvaluationConstant(codeRange);
      if (!this.validArgs(locals)) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         throw new IllegalArgumentException(
            String.format("Got illegal args! (fromIndex %d, initialIndex %d, maxIndex %d)", locals.getFromIndex(), locals.getIndex(), locals.getMaxIndex())
         );
      } else {
         if (this.isGenericCG() || this.isSimpleCG()) {
            CompilerDirectives.ensureVirtualized(locals.getCGData());
         }

         if (this.props.getMinResultLength() > 0
            && (this.isForward() ? locals.getMaxIndex() - locals.getIndex() : locals.getIndex() - Math.max(0, locals.getFromIndex() - this.getPrefixLength()))
               < this.props.getMinResultLength()) {
            return !this.isGenericCG() && !this.isSimpleCG() ? -2 : null;
         } else {
            if (this.recordExecution()) {
               this.debugRecorder.startRecording(locals);
            }

            if (this.isBackward()) {
               locals.setCurMinIndex(locals.getFromIndex());
            }

            int ip = 0;

            label596:
            while (true) {
               if (CompilerDirectives.inInterpreter()) {
                  RegexRootNode.checkThreadInterrupted();
               }

               CompilerAsserts.partialEvaluationConstant(ip);
               if (ip >= 0) {
                  DFAAbstractStateNode curState = this.states[ip & 32767];
                  CompilerAsserts.partialEvaluationConstant(curState);
                  short[] successors = curState.getSuccessors();
                  CompilerAsserts.partialEvaluationConstant(successors);
                  CompilerAsserts.partialEvaluationConstant(successors.length);
                  if (curState instanceof DFAInitialStateNode) {
                     boolean atBegin;
                     if (this.isSearching()) {
                        assert this.isForward();

                        for (int i = 0; i < this.getPrefixLength(); i++) {
                           if (locals.getIndex() <= 0) {
                              this.initNextIndex(locals);
                              ip = this.initialStateSuccessor(locals, curState, successors, i);
                              continue label596;
                           }

                           this.inputSkipIntl(locals, false);
                        }

                        this.initNextIndex(locals);
                        atBegin = this.inputAtBegin(locals);
                     } else {
                        this.initNextIndex(locals);
                        atBegin = this.inputAtBegin(locals);

                        for (int i = 0; i < this.getPrefixLength(); i++) {
                           assert this.isForward();

                           if (locals.getIndex() >= locals.getFromIndex()) {
                              if (atBegin) {
                                 ip = this.initialStateSuccessor(locals, curState, successors, i);
                              } else {
                                 ip = this.initialStateSuccessor(locals, curState, successors, i + successors.length / 2);
                              }
                              continue label596;
                           }

                           this.inputSkipIntl(locals, true);
                        }
                     }

                     if (atBegin) {
                        ip = this.initialStateSuccessor(locals, curState, successors, this.getPrefixLength());
                     } else {
                        ip = this.initialStateSuccessor(locals, curState, successors, this.getPrefixLength() + successors.length / 2);
                     }
                     continue;
                  }

                  if (curState instanceof DFAStateNode) {
                     DFAStateNode state = (DFAStateNode)curState;
                     if (ip > 32768) {
                        int i = ip >> 16;
                        ip = this.execTransition(locals, state, i);
                        continue;
                     }

                     if (CompilerDirectives.hasNextTier()) {
                        locals.incLoopCount(this);
                     }

                     this.inputAdvance(locals);
                     state.beforeFindSuccessor(locals, this);
                     if (this.isForward() && state.canDoIndexOf() && this.inputHasNext(locals)) {
                        int indexOfResult = state.indexOfCall
                           .execute(this, locals.getInput(), locals.getIndex(), this.getMaxIndex(locals), this.getEncoding(), tString);
                        int postLoopIndex = indexOfResult < 0 ? this.getMaxIndex(locals) : indexOfResult;
                        state.afterIndexOf(locals, this, locals.getIndex(), postLoopIndex);

                        assert locals.getIndex() == postLoopIndex;

                        if (successors.length == 2 && indexOfResult >= 0) {
                           int successor = state.getLoopToSelf() + 1 & 1;
                           CompilerAsserts.partialEvaluationConstant(successor);
                           this.inputIncNextIndexRaw(locals, state.indexOfCall.encodedLength());
                           ip = this.execTransition(locals, state, successor);
                           continue;
                        }
                     }

                     if (!this.inputHasNext(locals)) {
                        state.atEnd(locals, this);
                        if (this.isBackward() && state.hasBackwardPrefixState() && locals.getIndex() > 0) {
                           assert locals.getIndex() == locals.getFromIndex();

                           locals.setCurMinIndex(0);
                           ip = transitionMatch(state, ((BackwardDFAStateNode)state).getBackwardPrefixStateIndex());
                           continue;
                        }
                     } else {
                        if (!state.treeTransitionMatching()) {
                           Matchers matchers = state.getSequentialMatchers();
                           CompilerAsserts.partialEvaluationConstant(matchers);
                           if (matchers instanceof SequentialMatchers.SimpleSequentialMatchers) {
                              int c = this.inputReadAndDecode(locals);
                              CharMatcher[] cMatchers = ((SequentialMatchers.SimpleSequentialMatchers)matchers).getMatchers();
                              if (cMatchers != null) {
                                 for (int i = 0; i < cMatchers.length; i++) {
                                    if (match(cMatchers, i, c)) {
                                       ip = transitionMatch(state, i);
                                       continue label596;
                                    }
                                 }
                              }
                           } else if (matchers instanceof SequentialMatchers.UTF8SequentialMatchers) {
                              SequentialMatchers.UTF8SequentialMatchers utf8Matchers = (SequentialMatchers.UTF8SequentialMatchers)matchers;
                              CharMatcher[] ascii = utf8Matchers.getAscii();
                              CharMatcher[] enc2 = utf8Matchers.getEnc2();
                              CharMatcher[] enc3 = utf8Matchers.getEnc3();
                              CharMatcher[] enc4 = utf8Matchers.getEnc4();
                              int maxBytes = utf8Matchers.getMaxBytes();
                              CompilerAsserts.partialEvaluationConstant(maxBytes);
                              int c = this.inputReadRaw(locals);
                              if (codeRange != TruffleString.CodeRange.ASCII && c >= 128) {
                                 this.getBMPProfile().enter();
                                 int codepoint = 0;
                                 if (this.isBackward()) {
                                    codepoint = c & 63;

                                    assert c >> 6 == 2;

                                    for (int ix = 1; ix < 4; ix++) {
                                       c = this.inputReadRaw(locals, locals.getIndex() - ix);
                                       if (ix >= 3 || c >> 6 != 2) {
                                          break;
                                       }

                                       codepoint |= (c & 63) << 6 * ix;
                                    }
                                 }

                                 int nBytes = Integer.numberOfLeadingZeros(~(c << 24));

                                 assert 1 < nBytes && nBytes < 5 : nBytes;

                                 this.inputIncNextIndexRaw(locals, nBytes);
                                 if (maxBytes > 1 && nBytes <= maxBytes) {
                                    if (this.isBackward()) {
                                       codepoint |= (c & 255 >>> nBytes) << 6 * (nBytes - 1);
                                    }

                                    if (this.isForward()) {
                                       int index = locals.getIndex();
                                       codepoint = (c & 255 >>> nBytes) << 6 | this.inputReadRaw(locals, ++index) & 63;
                                       if (maxBytes > 2 && nBytes > 2) {
                                          codepoint = codepoint << 6 | this.inputReadRaw(locals, ++index) & 63;
                                       }

                                       if (maxBytes > 3 && nBytes > 3) {
                                          this.getAstralProfile().enter();
                                          codepoint = codepoint << 6 | this.inputReadRaw(locals, ++index) & 63;
                                       }
                                    }

                                    switch (nBytes - 2) {
                                       case 0:
                                          if (enc2 != null) {
                                             for (int ixxx = 0; ixxx < enc2.length; ixxx++) {
                                                if (match(enc2, ixxx, codepoint)) {
                                                   ip = transitionMatch(state, ixxx);
                                                   continue label596;
                                                }
                                             }
                                          }
                                          break;
                                       case 1:
                                          if (enc3 != null) {
                                             for (int ixx = 0; ixx < enc3.length; ixx++) {
                                                if (match(enc3, ixx, codepoint)) {
                                                   ip = transitionMatch(state, ixx);
                                                   continue label596;
                                                }
                                             }
                                          }
                                          break;
                                       case 2:
                                          if (enc4 != null) {
                                             this.getAstralProfile().enter();

                                             for (int ix = 0; ix < enc4.length; ix++) {
                                                if (match(enc4, ix, codepoint)) {
                                                   ip = transitionMatch(state, ix);
                                                   continue label596;
                                                }
                                             }
                                          }
                                    }
                                 }
                              } else {
                                 this.inputIncNextIndexRaw(locals);
                                 if (ascii != null) {
                                    for (int ixxxx = 0; ixxxx < ascii.length; ixxxx++) {
                                       if (match(ascii, ixxxx, c)) {
                                          ip = transitionMatch(state, ixxxx);
                                          continue label596;
                                       }
                                    }
                                 }
                              }
                           } else if (matchers instanceof SequentialMatchers.UTF16RawSequentialMatchers) {
                              int c = this.inputReadAndDecode(locals);
                              CharMatcher[] ascii = ((SequentialMatchers.UTF16RawSequentialMatchers)matchers).getAscii();
                              CharMatcher[] latin1 = ((SequentialMatchers.UTF16RawSequentialMatchers)matchers).getLatin1();
                              CharMatcher[] bmp = ((SequentialMatchers.UTF16RawSequentialMatchers)matchers).getBmp();
                              if (latin1 != null && (bmp == null || codeRange.isSubsetOf(TruffleString.CodeRange.LATIN_1) || c < 256)) {
                                 CharMatcher[] byteMatchers = asciiOrLatin1Matchers(codeRange, ascii, latin1);

                                 for (int ixxxxx = 0; ixxxxx < byteMatchers.length; ixxxxx++) {
                                    if (match(byteMatchers, ixxxxx, c)) {
                                       ip = transitionMatch(state, ixxxxx);
                                       continue label596;
                                    }
                                 }
                              } else if (bmp != null) {
                                 this.getBMPProfile().enter();

                                 for (int ixxxxxx = 0; ixxxxxx < bmp.length; ixxxxxx++) {
                                    if (match(bmp, ixxxxxx, c)) {
                                       ip = transitionMatch(state, ixxxxxx);
                                       continue label596;
                                    }
                                 }
                              }
                           } else {
                              assert matchers instanceof SequentialMatchers.UTF16Or32SequentialMatchers;

                              SequentialMatchers.UTF16Or32SequentialMatchers utf16Or32Matchers = (SequentialMatchers.UTF16Or32SequentialMatchers)matchers;
                              CharMatcher[] ascii = utf16Or32Matchers.getAscii();
                              CharMatcher[] latin1 = utf16Or32Matchers.getLatin1();
                              CharMatcher[] bmp = utf16Or32Matchers.getBmp();
                              CharMatcher[] astral = utf16Or32Matchers.getAstral();
                              int c = this.inputReadRaw(locals);
                              this.inputIncNextIndexRaw(locals);
                              if (this.isUTF16()) {
                                 if (codeRange.isSupersetOf(TruffleString.CodeRange.VALID)
                                    && state.utf16MustDecode()
                                    && this.inputUTF16IsHighSurrogate(c)
                                    && (codeRange == TruffleString.CodeRange.VALID || this.inputHasNext(locals, locals.getNextIndex()))) {
                                    this.getAstralProfile().enter();
                                    int c2 = this.inputReadRaw(locals, locals.getNextIndex());
                                    if (codeRange == TruffleString.CodeRange.VALID || this.inputUTF16IsLowSurrogate(c2)) {
                                       assert this.inputUTF16IsLowSurrogate(c2);

                                       locals.setNextIndex(this.inputIncRaw(locals.getNextIndex()));
                                       if (astral != null) {
                                          c = this.inputUTF16ToCodePoint(c, c2);
                                       }
                                    }

                                    if (astral != null) {
                                       for (int ixxxxxxx = 0; ixxxxxxx < astral.length; ixxxxxxx++) {
                                          if (match(astral, ixxxxxxx, c)) {
                                             ip = transitionMatch(state, ixxxxxxx);
                                             continue label596;
                                          }
                                       }
                                    }
                                 } else if (latin1 != null && (bmp == null || codeRange.isSubsetOf(TruffleString.CodeRange.LATIN_1) || c < 256)) {
                                    CharMatcher[] byteMatchers = asciiOrLatin1Matchers(codeRange, ascii, latin1);

                                    for (int ixxxxxxxx = 0; ixxxxxxxx < byteMatchers.length; ixxxxxxxx++) {
                                       if (match(byteMatchers, ixxxxxxxx, c)) {
                                          ip = transitionMatch(state, ixxxxxxxx);
                                          continue label596;
                                       }
                                    }
                                 } else if (bmp != null && codeRange.isSupersetOf(TruffleString.CodeRange.BMP)) {
                                    this.getBMPProfile().enter();

                                    for (int ixxxxxxxxx = 0; ixxxxxxxxx < bmp.length; ixxxxxxxxx++) {
                                       if (match(bmp, ixxxxxxxxx, c)) {
                                          ip = transitionMatch(state, ixxxxxxxxx);
                                          continue label596;
                                       }
                                    }
                                 }
                              } else {
                                 assert this.isUTF32();

                                 if (latin1 != null && (codeRange.isSubsetOf(TruffleString.CodeRange.LATIN_1) || c < 256)) {
                                    CharMatcher[] byteMatchers = asciiOrLatin1Matchers(codeRange, ascii, latin1);

                                    for (int ixxxxxxxxxx = 0; ixxxxxxxxxx < byteMatchers.length; ixxxxxxxxxx++) {
                                       if (match(byteMatchers, ixxxxxxxxxx, c)) {
                                          ip = transitionMatch(state, ixxxxxxxxxx);
                                          continue label596;
                                       }
                                    }
                                 } else if (bmp != null
                                    && (
                                       codeRange == TruffleString.CodeRange.BMP
                                          || c <= 65535 && (codeRange == TruffleString.CodeRange.VALID || !Character.isSurrogate((char)c))
                                    )) {
                                    this.getBMPProfile().enter();

                                    for (int ixxxxxxxxxxx = 0; ixxxxxxxxxxx < bmp.length; ixxxxxxxxxxx++) {
                                       if (match(bmp, ixxxxxxxxxxx, c)) {
                                          ip = transitionMatch(state, ixxxxxxxxxxx);
                                          continue label596;
                                       }
                                    }
                                 } else if (astral != null && codeRange.isSupersetOf(TruffleString.CodeRange.VALID)) {
                                    this.getAstralProfile().enter();

                                    for (int ixxxxxxxxxxxx = 0; ixxxxxxxxxxxx < astral.length; ixxxxxxxxxxxx++) {
                                       if (match(astral, ixxxxxxxxxxxx, c)) {
                                          ip = transitionMatch(state, ixxxxxxxxxxxx);
                                          continue label596;
                                       }
                                    }
                                 }
                              }
                           }

                           ip = transitionNoMatch(state);
                           continue;
                        }

                        int c = this.inputReadAndDecode(locals);
                        int treeSuccessor = state.getTreeMatcher().checkMatchTree(c);

                        for (int ixxxxxxxxxxxxx = 0; ixxxxxxxxxxxxx < successors.length; ixxxxxxxxxxxxx++) {
                           if (ixxxxxxxxxxxxx == treeSuccessor) {
                              ip = transitionMatch(state, ixxxxxxxxxxxxx);
                              continue label596;
                           }
                        }
                     }
                  } else {
                     assert curState instanceof DFAFindInnerLiteralStateNode;

                     assert this.isForward();

                     DFAFindInnerLiteralStateNode statex = (DFAFindInnerLiteralStateNode)curState;

                     while (this.inputHasNext(locals)) {
                        locals.setIndex(statex.executeInnerLiteralSearch(locals, this, tString));
                        if (locals.getIndex() < 0) {
                           break;
                        }

                        if (this.innerLiteralPrefixMatcher == null || prefixMatcherMatches(frame, this.innerLiteralPrefixMatcher, locals, codeRange, tString)) {
                           if (this.innerLiteralPrefixMatcher == null && this.isSimpleCG()) {
                              locals.getCGData().results[0] = locals.getIndex();
                           }

                           this.inputIncRaw(locals, statex.getInnerLiteral().getLiteral().encodedLength());
                           locals.setNextIndex(locals.getIndex());
                           ip = successors[0];
                           continue label596;
                        }

                        this.inputIncRaw(locals);
                     }
                  }
               }

               if (this.recordExecution()) {
                  this.debugRecorder.finishRecording();
               }

               if (this.isSimpleCG()) {
                  int[] result = this.props.isSimpleCGMustCopy() ? locals.getCGData().currentResult : locals.getCGData().results;
                  return locals.getResultInt() == 0 ? result : null;
               }

               if (this.isGenericCG()) {
                  return locals.getResultInt() == 0 ? locals.getCGData().currentResult : null;
               }

               return locals.getResultInt();
            }
         }
      }
   }

   private static boolean prefixMatcherMatches(
      VirtualFrame frame, TRegexDFAExecutorNode prefixMatcher, TRegexDFAExecutorLocals locals, TruffleString.CodeRange codeRange, boolean tString
   ) {
      Object result = prefixMatcher.execute(frame, locals.toInnerLiteralBackwardLocals(), codeRange, tString);
      return prefixMatcher.isSimpleCG() ? result != null : (Integer)result != -2;
   }

   private static CharMatcher[] asciiOrLatin1Matchers(TruffleString.CodeRange codeRange, CharMatcher[] ascii, CharMatcher[] latin1) {
      return codeRange == TruffleString.CodeRange.ASCII && ascii != null ? ascii : latin1;
   }

   private short initialStateSuccessor(TRegexDFAExecutorLocals locals, DFAAbstractStateNode curState, short[] successors, int i) {
      if (this.isGenericCG()) {
         locals.setLastIndex();
         short lastTransition = ((DFAInitialStateNode)curState).getCgLastTransition()[i];
         if (lastTransition >= 0) {
            locals.setLastTransition(lastTransition);
         }
      }

      return successors[i];
   }

   private void initNextIndex(TRegexDFAExecutorLocals locals) {
      locals.setNextIndex(locals.getIndex());
      if (this.recordExecution()) {
         this.getDebugRecorder().setInitialIndex(locals.getIndex());
      }
   }

   private static boolean match(CharMatcher[] matchers, int i, final int c) {
      return matchers[i] != null && matchers[i].match(c);
   }

   private static int transitionMatch(DFAStateNode state, int i) {
      CompilerAsserts.partialEvaluationConstant(state);
      return state.getId() | 32768 | i << 16;
   }

   private static int transitionNoMatch(DFAStateNode state) {
      CompilerAsserts.partialEvaluationConstant(state);
      return state.getId() | 32768 | state.getSequentialMatchers().getNoMatchSuccessor() << 16;
   }

   private int execTransition(TRegexDFAExecutorLocals locals, DFAStateNode state, int i) {
      CompilerAsserts.partialEvaluationConstant(state);
      CompilerAsserts.partialEvaluationConstant(i);
      if (this.recordExecution()) {
         this.debugRecorder.recordTransition(locals.getIndex(), state.getId(), i);
      }

      state.successorFound(locals, this, i);
      return state.successors[i];
   }

   @Override
   public int getMinIndex(TRegexExecutorLocals locals) {
      return this.isForward() ? super.getMinIndex(locals) : ((TRegexDFAExecutorLocals)locals).getCurMinIndex();
   }

   private boolean validArgs(TRegexDFAExecutorLocals locals) {
      int initialIndex = locals.getIndex();
      int inputLength = this.getInputLength(locals);
      int fromIndex = locals.getFromIndex();
      int maxIndex = locals.getMaxIndex();
      return inputLength >= 0
         && inputLength < 2147483627
         && fromIndex >= 0
         && fromIndex <= inputLength
         && initialIndex >= 0
         && initialIndex <= maxIndex
         && maxIndex >= fromIndex
         && maxIndex <= inputLength;
   }

   private static int[] initResultOrder(int maxNumberOfNFAStates, int numberOfCaptureGroups, TRegexDFAExecutorProperties props) {
      int[] resultOrder = new int[maxNumberOfNFAStates];

      for (int i = 0; i < maxNumberOfNFAStates; i++) {
         resultOrder[i] = i * (numberOfCaptureGroups * 2 + (props.tracksLastGroup() ? 1 : 0));
      }

      return resultOrder;
   }

   public TRegexDFAExecutorProperties getProperties() {
      return this.props;
   }

   public int getMaxNumberOfNFAStates() {
      return this.maxNumberOfNFAStates;
   }
}
