package com.oracle.truffle.regex.tregex.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.nfa.NFA;
import com.oracle.truffle.regex.tregex.nfa.NFAState;
import com.oracle.truffle.regex.tregex.nfa.NFAStateTransition;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.stream.Collectors;

public final class NFAExport {
   private final NFA nfa;
   private final BufferedWriter writer;
   private final boolean forward;
   private final boolean fullLabels;
   private final boolean mergeFinalStates;
   private int nextStateNumber = 1;
   private final HashMap<NFAState, Integer> stateNumberMap = new HashMap<>();

   private NFAExport(NFA nfa, BufferedWriter writer, boolean forward, boolean fullLabels, boolean mergeFinalStates) {
      this.nfa = nfa;
      this.writer = writer;
      this.forward = forward;
      this.fullLabels = fullLabels;
      this.mergeFinalStates = mergeFinalStates;
   }

   @CompilerDirectives.TruffleBoundary
   public static void exportDot(NFA nfa, TruffleFile path, boolean fullLabels, boolean mergeFinalStates) {
      try {
         try (BufferedWriter writer = path.newBufferedWriter(StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            new NFAExport(nfa, writer, true, fullLabels, mergeFinalStates).exportDot();
         }
      } catch (IOException var9) {
         throw new RuntimeException(var9);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static void exportDotReverse(NFA nfa, TruffleFile path, boolean fullLabels, boolean mergeFinalStates) {
      try {
         try (BufferedWriter writer = path.newBufferedWriter(StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            new NFAExport(nfa, writer, false, fullLabels, mergeFinalStates).exportDot();
         }
      } catch (IOException var9) {
         throw new RuntimeException(var9);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static void exportLaTex(NFA nfa, TruffleFile path, boolean fullLabels, boolean mergeFinalStates) {
      try {
         try (BufferedWriter writer = path.newBufferedWriter(StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            new NFAExport(nfa, writer, true, fullLabels, mergeFinalStates).exportLaTex();
         }
      } catch (IOException var9) {
         throw new RuntimeException(var9);
      }
   }

   private void exportDot() throws IOException {
      // $VF: Couldn't be decompiled
      // Please report this to the Vineflower issue tracker, at https://github.com/Vineflower/vineflower/issues with a copy of the class file (if you have the rights to distribute it!)
      // java.lang.OutOfMemoryError: Java heap space
      //   at org.jetbrains.java.decompiler.util.collections.SFormsFastMapDirect.<init>(SFormsFastMapDirect.java:25)
      //   at org.jetbrains.java.decompiler.util.collections.SFormsFastMapDirect.<init>(SFormsFastMapDirect.java:28)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SFormsConstructor.mergeInVarMaps(SFormsConstructor.java:238)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SFormsConstructor.ssaStatements(SFormsConstructor.java:107)
      //   at org.jetbrains.java.decompiler.modules.decompiler.sforms.SFormsConstructor.splitVariables(SFormsConstructor.java:94)
      //   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarVersionsProcessor.setVarVersions(VarVersionsProcessor.java:58)
      //   at org.jetbrains.java.decompiler.modules.decompiler.vars.VarProcessor.setVarVersions(VarProcessor.java:47)
      //   at org.jetbrains.java.decompiler.main.rels.MethodProcessor.codeToJava(MethodProcessor.java:302)
      //
      // Bytecode:
      // 00: aload 0
      // 01: getfield com/oracle/truffle/regex/tregex/util/NFAExport.writer Ljava/io/BufferedWriter;
      // 04: ldc "digraph finite_state_machine {"
      // 06: invokevirtual java/io/BufferedWriter.write (Ljava/lang/String;)V
      // 09: aload 0
      // 0a: getfield com/oracle/truffle/regex/tregex/util/NFAExport.writer Ljava/io/BufferedWriter;
      // 0d: invokevirtual java/io/BufferedWriter.newLine ()V
      // 10: aload 0
      // 11: getfield com/oracle/truffle/regex/tregex/util/NFAExport.writer Ljava/io/BufferedWriter;
      // 14: invokevirtual java/io/BufferedWriter.newLine ()V
      // 17: aload 0
      // 18: getfield com/oracle/truffle/regex/tregex/util/NFAExport.nfa Lcom/oracle/truffle/regex/tregex/nfa/NFA;
      // 1b: invokevirtual com/oracle/truffle/regex/tregex/nfa/NFA.getStates ()[Lcom/oracle/truffle/regex/tregex/nfa/NFAState;
      // 1e: astore 1
      // 1f: aload 1
      // 20: arraylength
      // 21: istore 2
      // 22: bipush 0
      // 23: istore 3
      // 24: iload 3
      // 25: iload 2
      // 26: if_icmpge 49
      // 29: aload 1
      // 2a: iload 3
      // 2b: aaload
      // 2c: astore 4
      // 2e: aload 0
      // 2f: aload 4
      // 31: invokevirtual com/oracle/truffle/regex/tregex/util/NFAExport.showState (Lcom/oracle/truffle/regex/tregex/nfa/NFAState;)Z
      // 34: ifeq 43
      // 37: aload 0
      // 38: aload 4
      // 3a: aload 0
      // 3b: aload 4
      // 3d: invokevirtual com/oracle/truffle/regex/tregex/util/NFAExport.getDotStateStyle (Lcom/oracle/truffle/regex/tregex/nfa/NFAState;)Ljava/lang/String;
      // 40: invokevirtual com/oracle/truffle/regex/tregex/util/NFAExport.setDotNodeStyle (Lcom/oracle/truffle/regex/tregex/nfa/NFAState;Ljava/lang/String;)V
      // 43: iinc 3 1
      // 46: goto 24
      // 49: aload 0
      // 4a: getfield com/oracle/truffle/regex/tregex/util/NFAExport.writer Ljava/io/BufferedWriter;
      // 4d: invokevirtual java/io/BufferedWriter.newLine ()V
      // 50: aload 0
      // 51: getfield com/oracle/truffle/regex/tregex/util/NFAExport.nfa Lcom/oracle/truffle/regex/tregex/nfa/NFA;
      // 54: invokevirtual com/oracle/truffle/regex/tregex/nfa/NFA.getStates ()[Lcom/oracle/truffle/regex/tregex/nfa/NFAState;
      // 57: astore 1
      // 58: aload 1
      // 59: arraylength
      // 5a: istore 2
      // 5b: bipush 0
      // 5c: istore 3
      // 5d: iload 3
      // 5e: iload 2
      // 5f: if_icmpge d0
      // 62: aload 1
      // 63: iload 3
      // 64: aaload
      // 65: astore 4
      // 67: aload 0
      // 68: aload 4
      // 6a: invokevirtual com/oracle/truffle/regex/tregex/util/NFAExport.showState (Lcom/oracle/truffle/regex/tregex/nfa/NFAState;)Z
      // 6d: ifeq ca
      // 70: bipush 0
      // 71: istore 5
      // 73: iload 5
      // 75: aload 4
      // 77: aload 0
      // 78: getfield com/oracle/truffle/regex/tregex/util/NFAExport.forward Z
      // 7b: invokevirtual com/oracle/truffle/regex/tregex/nfa/NFAState.getSuccessors (Z)[Lcom/oracle/truffle/regex/tregex/automaton/AbstractTransition;
      // 7e: checkcast [Lcom/oracle/truffle/regex/tregex/nfa/NFAStateTransition;
      // 81: arraylength
      // 82: if_icmpge ca
      // 85: aload 4
      // 87: aload 0
      // 88: getfield com/oracle/truffle/regex/tregex/util/NFAExport.forward Z
      // 8b: invokevirtual com/oracle/truffle/regex/tregex/nfa/NFAState.getSuccessors (Z)[Lcom/oracle/truffle/regex/tregex/automaton/AbstractTransition;
      // 8e: checkcast [Lcom/oracle/truffle/regex/tregex/nfa/NFAStateTransition;
      // 91: iload 5
      // 93: aaload
      // 94: astore 6
      // 96: aload 0
      // 97: getfield com/oracle/truffle/regex/tregex/util/NFAExport.writer Ljava/io/BufferedWriter;
      // 9a: aload 0
      // 9b: aload 6
      // 9d: aload 0
      // 9e: getfield com/oracle/truffle/regex/tregex/util/NFAExport.forward Z
      // a1: invokevirtual com/oracle/truffle/regex/tregex/nfa/NFAStateTransition.getSource (Z)Lcom/oracle/truffle/regex/tregex/nfa/NFAState;
      // a4: bipush 1
      // a5: invokevirtual com/oracle/truffle/regex/tregex/util/NFAExport.labelState (Lcom/oracle/truffle/regex/tregex/nfa/NFAState;Z)Ljava/lang/String;
      // a8: aload 0
      // a9: aload 6
      // ab: aload 0
      // ac: getfield com/oracle/truffle/regex/tregex/util/NFAExport.forward Z
      // af: invokevirtual com/oracle/truffle/regex/tregex/nfa/NFAStateTransition.getTarget (Z)Lcom/oracle/truffle/regex/tregex/automaton/AbstractState;
      // b2: checkcast com/oracle/truffle/regex/tregex/nfa/NFAState
      // b5: bipush 1
      // b6: invokevirtual com/oracle/truffle/regex/tregex/util/NFAExport.labelState (Lcom/oracle/truffle/regex/tregex/nfa/NFAState;Z)Ljava/lang/String;
      // b9: aload 0
      // ba: aload 6
      // bc: iload 5
      // be: invokevirtual com/oracle/truffle/regex/tregex/util/NFAExport.labelTransition (Lcom/oracle/truffle/regex/tregex/nfa/NFAStateTransition;I)Ljava/lang/String;
      // c1: invokestatic com/oracle/truffle/regex/tregex/util/DotExport.printConnection (Ljava/io/BufferedWriter;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)V
      // c4: iinc 5 1
      // c7: goto 73
      // ca: iinc 3 1
      // cd: goto 5d
      // d0: aload 0
      // d1: getfield com/oracle/truffle/regex/tregex/util/NFAExport.writer Ljava/io/BufferedWriter;
      // d4: ldc "}"
      // d6: invokevirtual java/io/BufferedWriter.write (Ljava/lang/String;)V
      // d9: aload 0
      // da: getfield com/oracle/truffle/regex/tregex/util/NFAExport.writer Ljava/io/BufferedWriter;
      // dd: invokevirtual java/io/BufferedWriter.newLine ()V
      // e0: return
   }

   private String getDotStateStyle(NFAState state) {
      switch (this.getStateStyle(state)) {
         case ANCHORED_FINAL:
            return "Mcircle";
         case UN_ANCHORED_FINAL:
            return "doublecircle";
         case ANCHORED_INITIAL:
         case UN_ANCHORED_INITIAL:
         case REGULAR:
            return "circle";
         default:
            throw CompilerDirectives.shouldNotReachHere();
      }
   }

   private void setDotNodeStyle(NFAState state, String style) throws IOException {
      this.writer.write(String.format("    node [shape = %s]; \"%s\";", style, DotExport.escape(this.labelState(state, true))));
      this.writer.newLine();
   }

   private void exportLaTex() throws IOException {
      StateSet<NFA, NFAState> visited = StateSet.create(this.nfa);
      this.writer
         .write(
            "\\documentclass{standalone}\n\\usepackage[utf8]{inputenc}\n\\usepackage[T1]{fontenc}\n\\usepackage{tikz}\n\n\\usetikzlibrary{calc}\n\\usetikzlibrary{automata}\n\\usetikzlibrary{arrows.meta}\n\n\\tikzset{\n\tregex automaton/.style={\n\t\tauto, \n\t\tnode distance=2cm,\n\t\tevery state/.style={\n\t\t\tsemithick,\n\t\t\tfill=gray!5,\n\t\t\tfont=\\footnotesize\\ttfamily,\n\t\t},\n\t\tdouble distance=1.5pt,  % Adjust appearance of accept states\n\t\tinitial text={start},   % label on inital state arrow\n\t\tevery edge/.style={\n\t\t\tdraw,\n\t\t\tfont=\\footnotesize\\ttfamily,\n\t\t\t-Stealth,\n\t\t\tshorten >=1pt,\n\t\t\tauto,\n\t\t\tsemithick\n\t\t},\n\t\tevery loop/.style={\n\t\t\tdraw,\n\t\t\tfont=\\footnotesize\\ttfamily,\n\t\t\t-Stealth,\n\t\t\tshorten >=1pt,\n\t\t\tauto,\n\t\t\tsemithick\n\t\t}\n\t},\n\tanchored/.style={\n\t\tpath picture={\n\t\t\t\\draw[semithick] ($(path picture bounding box.north west)-(0,0.2)$) -- ($(path picture bounding box.north east)-(0,0.2)$);\n\t\t\t\\draw[semithick] ($(path picture bounding box.south west)+(0,0.2)$) -- ($(path picture bounding box.south east)+(0,0.2)$);\n\t\t}\n\t}\n}\n\n\\begin{document}\n\\begin{tikzpicture}[regex automaton]\n\n"
         );
      ArrayList<NFAState> curStates = new ArrayList<>();
      ArrayList<NFAState> nextStates = new ArrayList<>();
      int entryOffset = this.nfa.getAnchoredEntry().length - 1;
      NFAState lastAnchoredEntry = this.nfa.getAnchoredEntry()[entryOffset].getTarget();
      NFAState lastUnAnchoredEntry = this.nfa.getUnAnchoredEntry()[entryOffset].getTarget();
      visited.add(lastAnchoredEntry);
      visited.add(lastUnAnchoredEntry);
      curStates.add(lastAnchoredEntry);
      this.printLaTexState(lastAnchoredEntry, null, null);
      if (lastAnchoredEntry != lastUnAnchoredEntry) {
         curStates.add(lastUnAnchoredEntry);
         this.printLaTexState(lastUnAnchoredEntry, lastAnchoredEntry, "below");
      }

      entryOffset--;

      while (!curStates.isEmpty()) {
         for (NFAState s : curStates) {
            for (NFAStateTransition t : s.getSuccessors()) {
               if ((!this.mergeFinalStates || !t.getTarget().isFinalState(this.forward)) && visited.add(t.getTarget())) {
                  nextStates.add(t.getTarget());
               }
            }
         }

         if (entryOffset >= 0) {
            NFAState anchoredEntry = this.nfa.getAnchoredEntry()[entryOffset].getTarget();
            if (visited.add(anchoredEntry)) {
               nextStates.add(anchoredEntry);
            }

            NFAState unAnchoredEntry = this.nfa.getUnAnchoredEntry()[entryOffset].getTarget();
            if (visited.add(unAnchoredEntry)) {
               nextStates.add(unAnchoredEntry);
            }

            entryOffset--;
         }

         NFAState relativeTo = null;

         for (NFAState nextState : nextStates) {
            this.printLaTexState(nextState, relativeTo == null ? curStates.get(0) : relativeTo, relativeTo == null ? "right" : "below");
            relativeTo = nextState;
         }

         ArrayList<NFAState> tmp = curStates;
         curStates = nextStates;
         nextStates = tmp;
         tmp.clear();
      }

      this.writer.newLine();
      this.writer.write("\\path[->]");
      this.writer.newLine();

      for (NFAState s : this.nfa.getStates()) {
         if (s != null) {
            for (int i = 0; i < ((NFAStateTransition[])s.getSuccessors()).length; i++) {
               NFAStateTransition tx = s.getSuccessors()[i];
               if (visited.contains(s) && visited.contains(tx.getTarget())) {
                  this.printLaTexTransition(tx, i);
               }
            }
         }
      }

      this.writer.write(";");
      this.writer.newLine();
      this.writer.write("\\end{tikzpicture}");
      this.writer.newLine();
      this.writer.write("\\end{document}");
      this.writer.newLine();
   }

   private void printLaTexState(NFAState state, NFAState relativeTo, String direction) throws IOException {
      String offset = "";
      if (relativeTo != null) {
         offset = String.format("%s of=%s", direction, this.getLaTexStateID(relativeTo));
      }

      this.writer
         .write(
            String.format(
               "\\node[%s] (%s) [%s] {%s};",
               this.getLaTexStateStyle(state),
               this.getLaTexStateID(state),
               offset,
               LaTexExport.escape(this.labelState(state, false))
            )
         );
      this.writer.newLine();
   }

   private void printLaTexTransition(NFAStateTransition t, int priority) throws IOException {
      ArrayList<String> options = new ArrayList<>();
      if (t.getSource() == t.getTarget()) {
         options.add("loop above");
      }

      this.writer
         .write(
            String.format(
               "(%s) edge [%s] node {%s} (%s)",
               this.getLaTexStateID(t.getSource()),
               options.stream().collect(Collectors.joining(", ")),
               LaTexExport.escape(this.labelTransition(t, priority)),
               this.getLaTexStateID(t.getTarget())
            )
         );
      this.writer.newLine();
   }

   private String getLaTexStateID(NFAState state) {
      if (state.isAnchoredFinalState(this.forward)) {
         return "af";
      } else if (state.isUnAnchoredFinalState(this.forward)) {
         return "f";
      } else if (this.nfa.isEntry(state, this.forward)) {
         String lbl = this.nfa.isUnAnchoredEntry(state, this.forward) ? "i" : "ai";
         return lbl
            + (
               this.nfa.isUnAnchoredEntry(state, this.forward)
                  ? this.nfa.getUnAnchoredEntryOffset(state, this.forward)
                  : this.nfa.getAnchoredEntryOffset(state, this.forward)
            );
      } else {
         return "s" + this.stateNumberMap.computeIfAbsent(state, x -> this.nextStateNumber++);
      }
   }

   private String getLaTexStateStyle(NFAState state) {
      switch (this.getStateStyle(state)) {
         case ANCHORED_FINAL:
            return "anchored,accepting,state";
         case UN_ANCHORED_FINAL:
            return "accepting,state";
         case ANCHORED_INITIAL:
            return "anchored,initial,state";
         case UN_ANCHORED_INITIAL:
            return "initial,state";
         case REGULAR:
            return "state";
         default:
            throw CompilerDirectives.shouldNotReachHere();
      }
   }

   private boolean showState(NFAState state) {
      if (state == null || state == this.nfa.getDummyInitialState()) {
         return false;
      } else if (this.nfa.isEntry(state, this.forward)) {
         return state.getSuccessors(this.forward).length > 0;
      } else {
         return state.isFinalState(this.forward) ? state.getPredecessors(this.forward).length > 0 : true;
      }
   }

   private NFAExport.StateStyle getStateStyle(NFAState state) {
      if (this.nfa.isEntry(state, this.forward)) {
         return this.nfa.isAnchoredEntry(state, this.forward) && !this.nfa.isUnAnchoredEntry(state, this.forward)
            ? NFAExport.StateStyle.ANCHORED_INITIAL
            : NFAExport.StateStyle.UN_ANCHORED_INITIAL;
      } else if ((!this.mergeFinalStates || !state.hasTransitionToAnchoredFinalState(this.forward) || state.hasTransitionToUnAnchoredFinalState(this.forward))
         && !state.isAnchoredFinalState(this.forward)) {
         return !state.isFinalState(this.forward) && (!this.mergeFinalStates || !state.hasTransitionToUnAnchoredFinalState(this.forward))
            ? NFAExport.StateStyle.REGULAR
            : NFAExport.StateStyle.UN_ANCHORED_FINAL;
      } else {
         return NFAExport.StateStyle.ANCHORED_FINAL;
      }
   }

   private String labelState(NFAState state, boolean markAnchored) {
      StringBuilder sb = new StringBuilder();
      if (this.nfa.isAnchoredEntry(state, this.forward) && !this.nfa.isUnAnchoredEntry(state, this.forward)) {
         sb.append("I");
         if (markAnchored) {
            sb.append("^");
         }

         if (this.forward) {
            sb.append(this.nfa.getAnchoredEntryOffset(state, true));
         }
      } else if (this.nfa.isUnAnchoredEntry(state, this.forward)) {
         sb.append("I");
         if (this.forward) {
            sb.append(this.nfa.getUnAnchoredEntryOffset(state, true));
         }
      } else if (state.isAnchoredFinalState(this.forward)) {
         sb.append("F");
         if (markAnchored) {
            sb.append("$");
         }
      } else if (state.isUnAnchoredFinalState(this.forward)) {
         sb.append("F");
      } else if (this.fullLabels) {
         sb.append("S").append(state.idToString());
      } else {
         sb.append(this.stateNumberMap.computeIfAbsent(state, x -> this.nextStateNumber++));
      }

      if (this.fullLabels && state.hasPossibleResults()) {
         sb.append("_r").append(state.getPossibleResults());
      }

      if (this.fullLabels && state.isMustAdvance()) {
         sb.append("_ma");
      }

      return sb.toString();
   }

   private String labelTransition(NFAStateTransition transition, int priority) {
      StringBuilder sb = new StringBuilder();
      if (!transition.getTarget(this.forward).isFinalState(this.forward)) {
         sb.append(transition.getCodePointSet());
      }

      if (this.fullLabels) {
         sb.append(", p").append(priority).append(", ").append(transition.getGroupBoundaries());
      }

      return sb.toString();
   }

   private static enum StateStyle {
      ANCHORED_INITIAL,
      UN_ANCHORED_INITIAL,
      ANCHORED_FINAL,
      UN_ANCHORED_FINAL,
      REGULAR;
   }
}
