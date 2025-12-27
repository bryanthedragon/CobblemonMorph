package com.oracle.truffle.regex.tregex.nodes.dfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.regex.RegexLanguage;
import com.oracle.truffle.regex.RegexOptions;
import com.oracle.truffle.regex.tregex.dfa.DFAGenerator;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;
import java.util.ArrayList;
import java.util.List;

public final class TRegexDFAExecutorDebugRecorder implements JsonConvertible {
   private final DFAGenerator dfa;
   private List<TRegexDFAExecutorDebugRecorder.Recording> recordings = new ArrayList<>();

   @CompilerDirectives.TruffleBoundary
   public static TRegexDFAExecutorDebugRecorder create(RegexOptions options, DFAGenerator dfaGenerator) {
      return options.isStepExecution() ? new TRegexDFAExecutorDebugRecorder(dfaGenerator) : null;
   }

   private TRegexDFAExecutorDebugRecorder(DFAGenerator dfa) {
      this.dfa = dfa;
   }

   @CompilerDirectives.TruffleBoundary
   public void startRecording(TRegexDFAExecutorLocals locals) {
      this.recordings
         .add(new TRegexDFAExecutorDebugRecorder.Recording(locals.getInput().toString(), locals.getFromIndex(), locals.getIndex(), locals.getMaxIndex()));
   }

   @CompilerDirectives.TruffleBoundary
   private TRegexDFAExecutorDebugRecorder.Recording curRecording() {
      return this.recordings.get(this.recordings.size() - 1);
   }

   @CompilerDirectives.TruffleBoundary
   public void setInitialIndex(int initialIndex) {
      this.curRecording().setInitialIndex(initialIndex);
   }

   @CompilerDirectives.TruffleBoundary
   public void recordTransition(int currentIndex, short stateNodeID, int transitionIndex) {
      int transitionID = this.dfa.getState(stateNodeID).getSuccessors()[transitionIndex].getId();
      this.curRecording().recordTransition(currentIndex, transitionID);
   }

   @CompilerDirectives.TruffleBoundary
   public void recordCGPartialTransition(int currentIndex, int cgPartialTransitionIndex) {
      this.curRecording().recordCGPartialTransition(currentIndex, cgPartialTransitionIndex);
   }

   @CompilerDirectives.TruffleBoundary
   public void finishRecording() {
      TruffleFile file = RegexLanguage.RegexContext.get(null)
         .getEnv()
         .getPublicTruffleFile(
            "tregex_"
               + this.dfa.getDebugDumpName()
               + "_"
               + this.dfa.getNfa().getAst().getSource().toFileName()
               + "_recording"
               + this.recordings.size()
               + ".json"
         );
      Json.obj(Json.prop("dfa", this.dfa), Json.prop("recording", this.curRecording())).dump(file);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JsonValue toJson() {
      return Json.obj(Json.prop("recordings", this.recordings));
   }

   private static final class RecordedTransition implements JsonConvertible {
      private final int currentIndex;
      private int transitionID = -1;
      private int cgPartialTransitionID = -1;

      @CompilerDirectives.TruffleBoundary
      private RecordedTransition(int currentIndex) {
         this.currentIndex = currentIndex;
      }

      @CompilerDirectives.TruffleBoundary
      public void setTransitionID(int transitionID) {
         this.transitionID = transitionID;
      }

      @CompilerDirectives.TruffleBoundary
      public void setCgPartialTransitionID(int cgPartialTransitionID) {
         assert this.cgPartialTransitionID == -1 || this.cgPartialTransitionID == 0;

         this.cgPartialTransitionID = cgPartialTransitionID;
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public JsonValue toJson() {
         return Json.obj(
            Json.prop("currentIndex", this.currentIndex),
            Json.prop("transitionID", this.transitionID),
            Json.prop("cgPartialTransitionID", this.cgPartialTransitionID)
         );
      }
   }

   private static final class Recording implements JsonConvertible {
      private final String input;
      private final int fromIndex;
      private int initialIndex;
      private final int maxIndex;
      private final List<TRegexDFAExecutorDebugRecorder.RecordedTransition> transitions;

      private Recording(String input, int fromIndex, int initialIndex, int maxIndex) {
         this.input = input;
         this.fromIndex = fromIndex;
         this.initialIndex = initialIndex;
         this.maxIndex = maxIndex;
         this.transitions = new ArrayList<>();
      }

      @CompilerDirectives.TruffleBoundary
      public void setInitialIndex(int initialIndex) {
         this.initialIndex = initialIndex;
      }

      @CompilerDirectives.TruffleBoundary
      private int getLowestIndex() {
         return this.initialIndex < this.maxIndex ? this.initialIndex : this.maxIndex;
      }

      @CompilerDirectives.TruffleBoundary
      private void initUpToIndex(int currentIndex) {
         for (int i = this.transitions.size(); i <= currentIndex - this.getLowestIndex(); i++) {
            this.transitions.add(new TRegexDFAExecutorDebugRecorder.RecordedTransition(this.getLowestIndex() + i));
         }
      }

      @CompilerDirectives.TruffleBoundary
      private TRegexDFAExecutorDebugRecorder.RecordedTransition getTransition(int currentIndex) {
         TRegexDFAExecutorDebugRecorder.RecordedTransition transition = this.transitions.get(currentIndex - this.getLowestIndex());

         assert transition.currentIndex == currentIndex;

         return transition;
      }

      @CompilerDirectives.TruffleBoundary
      public void recordTransition(int currentIndex, int transitionID) {
         this.initUpToIndex(currentIndex);
         this.getTransition(currentIndex).setTransitionID(transitionID);
      }

      @CompilerDirectives.TruffleBoundary
      public void recordCGPartialTransition(int currentIndex, int cgPartialTransitionIndex) {
         this.initUpToIndex(currentIndex);
         this.getTransition(currentIndex).setCgPartialTransitionID(cgPartialTransitionIndex);
      }

      @CompilerDirectives.TruffleBoundary
      @Override
      public JsonValue toJson() {
         return Json.obj(
            Json.prop("input", this.input),
            Json.prop("fromIndex", this.fromIndex),
            Json.prop("initialIndex", this.initialIndex),
            Json.prop("maxIndex", this.maxIndex),
            Json.prop("transitions", this.transitions)
         );
      }
   }
}
