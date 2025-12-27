package com.oracle.truffle.regex.tregex.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.regex.tregex.automaton.BasicState;
import com.oracle.truffle.regex.tregex.dfa.DFAGenerator;
import com.oracle.truffle.regex.tregex.dfa.DFAStateNodeBuilder;
import com.oracle.truffle.regex.tregex.dfa.DFAStateTransitionBuilder;
import com.oracle.truffle.regex.tregex.matchers.AnyMatcher;
import com.oracle.truffle.regex.tregex.matchers.BitSetMatcher;
import com.oracle.truffle.regex.tregex.matchers.CharMatcher;
import com.oracle.truffle.regex.tregex.matchers.EmptyMatcher;
import com.oracle.truffle.regex.tregex.matchers.SingleCharMatcher;
import com.oracle.truffle.regex.tregex.matchers.SingleRangeMatcher;
import com.oracle.truffle.regex.tregex.nodes.dfa.DFAStateNode;
import com.oracle.truffle.regex.tregex.nodes.dfa.SequentialMatchers;
import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.StandardOpenOption;
import java.util.Map;
import java.util.TreeSet;
import java.util.stream.Collectors;

public class DFAExport {
   @CompilerDirectives.TruffleBoundary
   public static void exportDot(DFAGenerator dfaGenerator, TruffleFile path, boolean shortLabels) {
      DFAStateNodeBuilder[] entryStates = dfaGenerator.getEntryStates();
      Map<DFAStateNodeBuilder, DFAStateNodeBuilder> stateMap = dfaGenerator.getStateMap();
      TreeSet<Integer> entryIDs = new TreeSet<>();

      for (DFAStateNodeBuilder s : entryStates) {
         if (s != null) {
            entryIDs.add(s.getId());
         }
      }

      try {
         try (BufferedWriter writer = path.newBufferedWriter(StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING)) {
            writer.write("digraph finite_state_machine {");
            writer.newLine();
            String finalStates = stateMap.values()
               .stream()
               .filter(DFAStateNodeBuilder::isUnAnchoredFinalState)
               .map(sx -> DotExport.escape(dotState(sx, shortLabels)))
               .collect(Collectors.joining("\" \""));
            if (!finalStates.isEmpty()) {
               writer.write(String.format("    node [shape = doublecircle]; \"%s\";", finalStates));
               writer.newLine();
            }

            String anchoredFinalStates = stateMap.values()
               .stream()
               .filter(BasicState::isAnchoredFinalState)
               .map(sx -> DotExport.escape(dotState(sx, shortLabels)))
               .collect(Collectors.joining("\" \""));
            if (!anchoredFinalStates.isEmpty()) {
               writer.write(String.format("    node [shape = Mcircle]; \"%s\";", anchoredFinalStates));
               writer.newLine();
            }

            writer.write("    node [shape = circle];");
            writer.newLine();

            for (DFAStateNodeBuilder state : stateMap.values()) {
               if (entryIDs.contains(state.getId())) {
                  for (int i = 0; i < entryStates.length; i++) {
                     if (entryStates[i] == state) {
                        String initStateLabel;
                        if (i < entryStates.length / 2) {
                           initStateLabel = "I^" + i;
                        } else {
                           initStateLabel = "I" + (i - entryStates.length / 2);
                        }

                        DotExport.printConnection(writer, initStateLabel, dotState(state, shortLabels), "");
                        break;
                     }
                  }
               }

               for (DFAStateTransitionBuilder t : state.getSuccessors()) {
                  DotExport.printConnection(writer, dotState(state, shortLabels), dotState(t.getTarget(), shortLabels), t.getCodePointSet().toString());
               }
            }

            writer.write("}");
            writer.newLine();
         }
      } catch (IOException var17) {
         throw new RuntimeException(var17);
      }
   }

   private static String dotState(DFAStateNodeBuilder state, boolean shortLabels) {
      return "S" + (shortLabels ? state.getId() : state.stateSetToString());
   }

   @CompilerDirectives.TruffleBoundary
   public static void exportUnitTest(DFAStateNode entry, DFAStateNode[] states) {
      System.out.printf("int initialState = %d;\n", entry.getId());
      System.out.printf("DFAStateNode[] states = createStates(%d);\n", states.length);

      for (DFAStateNode state : states) {
         System.out.printf("states[%d].setSuccessors(new int[] { %d", state.getId(), state.getSuccessors()[0]);

         for (int i = 1; i < state.getSuccessors().length; i++) {
            System.out.printf(", %d", state.getSuccessors()[i]);
         }

         System.out.println(" });");
         System.out.printf("states[%d].setMatchers(new ByteMatcher[] {\n    ", state.getId());
         printMatcher(((SequentialMatchers.SimpleSequentialMatchers)state.getSequentialMatchers()).getMatchers()[0]);

         for (int i = 1; i < state.getSequentialMatchers().size(); i++) {
            System.out.print(",\n    ");
            printMatcher(((SequentialMatchers.SimpleSequentialMatchers)state.getSequentialMatchers()).getMatchers()[i]);
         }

         System.out.println("\n});");
         if (state.isFinalState()) {
            System.out.printf("states[%d].setFinalState();\n", state.getId());
         }
      }
   }

   private static void printMatcher(CharMatcher matcher) {
      if (matcher instanceof EmptyMatcher) {
         System.out.print("EmptyByteMatcher.create()");
      }

      if (matcher instanceof SingleCharMatcher) {
         System.out.printf("SingleByteMatcher.create(0x%02x)", ((SingleCharMatcher)matcher).getChar());
      }

      if (matcher instanceof SingleRangeMatcher) {
         System.out.printf("RangeByteMatcher.create(0x%02x, 0x%02x)", ((SingleRangeMatcher)matcher).getLo(), ((SingleRangeMatcher)matcher).getHi());
      }

      if (matcher instanceof BitSetMatcher) {
         long[] words = ((BitSetMatcher)matcher).getBitSet();
         System.out.printf("MultiByteMatcher.create(new CompilationFinalBitSet(new long[] {\n        0x%016xL", words[0]);

         for (int i = 1; i < words.length; i++) {
            System.out.printf(", 0x%016xL", words[i]);
         }

         System.out.print("}))");
      }

      if (matcher instanceof AnyMatcher) {
         System.out.print("AnyByteMatcher.create()");
      }
   }
}
