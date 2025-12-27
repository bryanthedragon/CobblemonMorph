package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSNodeUtil;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTaggedExecutionNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import java.util.Set;

@NodeInfo(shortName = "switch")
public final class SwitchNode extends StatementNode implements ResumableNode.WithObjectState {
   @Node.Children
   private final JavaScriptNode[] declarations;
   @Node.Children
   private final JavaScriptNode[] caseExpressions;
   @Node.Children
   private final JavaScriptNode[] statements;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final int[] jumptable;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final ConditionProfile[] conditionProfiles;
   private final boolean ordered;

   private SwitchNode(JavaScriptNode[] declarations, JavaScriptNode[] caseExpressions, int[] jumptable, JavaScriptNode[] statements) {
      assert caseExpressions.length == jumptable.length - 1;

      this.declarations = declarations;
      this.caseExpressions = caseExpressions;
      this.statements = statements;
      this.jumptable = jumptable;
      this.ordered = isMonotonicallyIncreasing(jumptable);
      this.conditionProfiles = createConditionProfiles(caseExpressions.length);
   }

   private static boolean isMonotonicallyIncreasing(int[] table) {
      for (int i = 0; i < table.length - 1; i++) {
         int start = table[i];
         int end = table[i + 1];
         if (start > end) {
            return false;
         }
      }

      return true;
   }

   private static ConditionProfile[] createConditionProfiles(int length) {
      ConditionProfile[] a = new ConditionProfile[length];

      for (int i = 0; i < length; i++) {
         a[i] = ConditionProfile.createCountingProfile();
      }

      return a;
   }

   public static SwitchNode create(JavaScriptNode[] declarations, JavaScriptNode[] caseExpressions, int[] jumptable, JavaScriptNode[] statements) {
      return new SwitchNode(declarations, caseExpressions, jumptable, statements);
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      return tag == JSTags.ControlFlowRootTag.class ? true : super.hasTag(tag);
   }

   @Override
   public Object getNodeObject() {
      return JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowRootTag.Type.Conditional.name());
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      if (materializedTags.contains(JSTags.ControlFlowRootTag.class) && this.needsMaterialization()) {
         JavaScriptNode[] newCaseExpressions = new JavaScriptNode[this.caseExpressions.length];
         boolean wasChanged = false;

         for (int i = 0; i < this.caseExpressions.length; i++) {
            InstrumentableNode materialized = this.caseExpressions[i].materializeInstrumentableNodes(materializedTags);
            newCaseExpressions[i] = JSTaggedExecutionNode.createForInput(
               (JavaScriptNode)materialized,
               JSTags.ControlFlowBranchTag.class,
               JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowBranchTag.Type.Condition.name()),
               materializedTags
            );
            if (newCaseExpressions[i] != this.caseExpressions[i]) {
               wasChanged = true;
            }
         }

         JavaScriptNode[] newStatements = new JavaScriptNode[this.statements.length];

         for (int ix = 0; ix < this.statements.length; ix++) {
            InstrumentableNode materialized = this.statements[ix].materializeInstrumentableNodes(materializedTags);
            newStatements[ix] = JSTaggedExecutionNode.createFor((JavaScriptNode)materialized, JSTags.ControlFlowBlockTag.class, materializedTags);
            if (newStatements[ix] != this.statements[ix]) {
               wasChanged = true;
            }
         }

         if (!wasChanged) {
            return this;
         } else {
            for (int ixx = 0; ixx < this.caseExpressions.length; ixx++) {
               if (newCaseExpressions[ixx] == this.caseExpressions[ixx]) {
                  newCaseExpressions[ixx] = cloneUninitialized(this.caseExpressions[ixx], materializedTags);
               }
            }

            for (int ixxx = 0; ixxx < this.statements.length; ixxx++) {
               if (newStatements[ixxx] == this.statements[ixxx]) {
                  newStatements[ixxx] = cloneUninitialized(this.statements[ixxx], materializedTags);
               }
            }

            SwitchNode materialized = create(cloneUninitialized(this.declarations, materializedTags), newCaseExpressions, this.jumptable, newStatements);
            transferSourceSectionAndTags(this, materialized);
            return materialized;
         }
      } else {
         return this;
      }
   }

   private boolean needsMaterialization() {
      boolean needsMaterialization = false;

      for (int i = 0; i < this.caseExpressions.length && !needsMaterialization; i++) {
         if (!JSNodeUtil.isTaggedNode(this.caseExpressions[i])) {
            needsMaterialization = true;
         }
      }

      for (int ix = 0; ix < this.statements.length && !needsMaterialization; ix++) {
         if (!JSNodeUtil.isTaggedNode(this.statements[ix])) {
            needsMaterialization = true;
         }
      }

      return needsMaterialization;
   }

   @Override
   public Object execute(VirtualFrame frame) {
      this.executeDeclarations(frame);
      return this.ordered ? this.executeOrdered(frame) : this.executeDefault(frame);
   }

   @ExplodeLoop
   private void executeDeclarations(VirtualFrame frame) {
      for (int i = 0; i < this.declarations.length; i++) {
         this.declarations[i].execute(frame);
      }
   }

   private Object executeDefault(VirtualFrame frame) {
      int statementStartIndex = this.identifyTargetCase(frame, 0, -1);
      return this.executeStatements(frame, statementStartIndex);
   }

   @Override
   public Object resume(VirtualFrame frame, int stateSlot) {
      Object maybeState = this.getState(frame, stateSlot);
      int caseIndex;
      int statementIndex;
      Object resumptionResult;
      if (maybeState instanceof SwitchNode.SwitchResumptionRecord) {
         this.resetState(frame, stateSlot);
         SwitchNode.SwitchResumptionRecord state = (SwitchNode.SwitchResumptionRecord)maybeState;
         caseIndex = state.caseIndex;
         statementIndex = state.statementIndex;
         resumptionResult = state.result;
      } else {
         caseIndex = 0;
         statementIndex = 0;
         resumptionResult = EMPTY;
         this.executeDeclarations(frame);
      }

      if (caseIndex >= 0) {
         statementIndex = this.identifyTargetCase(frame, caseIndex, stateSlot);
      }

      return this.executeStatements(frame, statementIndex, resumptionResult, stateSlot);
   }

   @ExplodeLoop
   private int identifyTargetCase(VirtualFrame frame, int firstCase, int stateSlot) {
      int i = 0;

      try {
         while (i < this.caseExpressions.length && (i < firstCase || !executeConditionAsBoolean(frame, this.caseExpressions[i]))) {
            i++;
         }

         int statementStartIndex = this.jumptable[i];
         CompilerAsserts.partialEvaluationConstant(statementStartIndex);
         return statementStartIndex;
      } catch (YieldException var6) {
         this.setState(frame, stateSlot, new SwitchNode.SwitchResumptionRecord(i, -1, null));
         throw var6;
      }
   }

   private Object executeStatements(VirtualFrame frame, int statementStartIndex) {
      return this.executeStatements(frame, statementStartIndex, EMPTY, -1);
   }

   @ExplodeLoop
   private Object executeStatements(VirtualFrame frame, int statementStartIndex, Object initialResult, int stateSlot) {
      int statementIndex = 0;
      Object result = initialResult;

      try {
         for (; statementIndex < this.statements.length; statementIndex++) {
            if (statementIndex >= statementStartIndex) {
               result = this.statements[statementIndex].execute(frame);
            }
         }

         return result;
      } catch (YieldException var8) {
         this.setState(frame, stateSlot, new SwitchNode.SwitchResumptionRecord(-1, statementIndex, result));
         throw var8;
      }
   }

   @ExplodeLoop
   private Object executeOrdered(VirtualFrame frame) {
      JavaScriptNode[] caseExpressionsLocal = this.caseExpressions;
      JavaScriptNode[] statementsLocal = this.statements;
      int[] jumptableLocal = this.jumptable;
      ConditionProfile[] conditionProfilesLocal = this.conditionProfiles;
      boolean caseFound = false;
      Object result = EMPTY;

      int jumptableIdx;
      for (jumptableIdx = 0; jumptableIdx < caseExpressionsLocal.length; jumptableIdx++) {
         if (caseFound || executeConditionAsBoolean(frame, caseExpressionsLocal[jumptableIdx])) {
            caseFound = true;
         }

         int statementStartIndex = jumptableLocal[jumptableIdx];
         int statementEndIndex = jumptableLocal[jumptableIdx + 1];
         CompilerAsserts.partialEvaluationConstant(statementStartIndex);
         CompilerAsserts.partialEvaluationConstant(statementEndIndex);
         if (statementStartIndex != statementEndIndex && conditionProfilesLocal[jumptableIdx].profile(caseFound)) {
            for (int statementIndex = statementStartIndex; statementIndex < statementEndIndex; statementIndex++) {
               result = statementsLocal[statementIndex].execute(frame);
            }
         }
      }

      int statementStartIndex = jumptableLocal[jumptableIdx];
      CompilerAsserts.partialEvaluationConstant(statementStartIndex);

      for (int statementIndex = statementStartIndex; statementIndex < statementsLocal.length; statementIndex++) {
         result = statementsLocal[statementIndex].execute(frame);
      }

      return result;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(
         cloneUninitialized(this.declarations, materializedTags),
         cloneUninitialized(this.caseExpressions, materializedTags),
         this.jumptable,
         cloneUninitialized(this.statements, materializedTags)
      );
   }

   private static class SwitchResumptionRecord {
      private final Object result;
      private final int caseIndex;
      private final int statementIndex;

      SwitchResumptionRecord(int resumptionCaseIndex, int statementIndex, Object result) {
         this.result = result;
         this.caseIndex = resumptionCaseIndex;
         this.statementIndex = statementIndex;
      }
   }
}
