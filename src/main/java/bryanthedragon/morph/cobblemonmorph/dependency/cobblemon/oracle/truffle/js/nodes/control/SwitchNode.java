
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
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.control.StatementNode;
import com.oracle.truffle.js.nodes.control.YieldException;
import com.oracle.truffle.js.nodes.instrumentation.JSTaggedExecutionNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import java.util.Set;

@NodeInfo(shortName="switch")
public final class SwitchNode
extends StatementNode
implements ResumableNode.WithObjectState {
    @Node.Children
    private final JavaScriptNode[] declarations;
    @Node.Children
    private final JavaScriptNode[] caseExpressions;
    @Node.Children
    private final JavaScriptNode[] statements;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final int[] jumptable;
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private final ConditionProfile[] conditionProfiles;
    private final boolean ordered;

    private SwitchNode(JavaScriptNode[] declarations, JavaScriptNode[] caseExpressions, int[] jumptable, JavaScriptNode[] statements) {
        assert (caseExpressions.length == jumptable.length - 1);
        this.declarations = declarations;
        this.caseExpressions = caseExpressions;
        this.statements = statements;
        this.jumptable = jumptable;
        this.ordered = SwitchNode.isMonotonicallyIncreasing(jumptable);
        this.conditionProfiles = SwitchNode.createConditionProfiles(caseExpressions.length);
    }

    private static boolean isMonotonicallyIncreasing(int[] table) {
        for (int i = 0; i < table.length - 1; ++i) {
            int start2 = table[i];
            int end2 = table[i + 1];
            if (start2 <= end2) continue;
            return false;
        }
        return true;
    }

    private static ConditionProfile[] createConditionProfiles(int length) {
        ConditionProfile[] a = new ConditionProfile[length];
        for (int i = 0; i < length; ++i) {
            a[i] = ConditionProfile.createCountingProfile();
        }
        return a;
    }

    public static SwitchNode create(JavaScriptNode[] declarations, JavaScriptNode[] caseExpressions, int[] jumptable, JavaScriptNode[] statements) {
        return new SwitchNode(declarations, caseExpressions, jumptable, statements);
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.ControlFlowRootTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public Object getNodeObject() {
        return JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowRootTag.Type.Conditional.name());
    }

    @Override
    public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
        if (materializedTags.contains(JSTags.ControlFlowRootTag.class) && this.needsMaterialization()) {
            int i;
            JavaScriptNode[] newCaseExpressions = new JavaScriptNode[this.caseExpressions.length];
            boolean wasChanged = false;
            for (int i2 = 0; i2 < this.caseExpressions.length; ++i2) {
                InstrumentableNode materialized = this.caseExpressions[i2].materializeInstrumentableNodes(materializedTags);
                newCaseExpressions[i2] = JSTaggedExecutionNode.createForInput((JavaScriptNode)materialized, JSTags.ControlFlowBranchTag.class, JSTags.createNodeObjectDescriptor("type", JSTags.ControlFlowBranchTag.Type.Condition.name()), materializedTags);
                if (newCaseExpressions[i2] == this.caseExpressions[i2]) continue;
                wasChanged = true;
            }
            JavaScriptNode[] newStatements = new JavaScriptNode[this.statements.length];
            for (i = 0; i < this.statements.length; ++i) {
                InstrumentableNode materialized = this.statements[i].materializeInstrumentableNodes(materializedTags);
                newStatements[i] = JSTaggedExecutionNode.createFor((JavaScriptNode)materialized, JSTags.ControlFlowBlockTag.class, materializedTags);
                if (newStatements[i] == this.statements[i]) continue;
                wasChanged = true;
            }
            if (!wasChanged) {
                return this;
            }
            for (i = 0; i < this.caseExpressions.length; ++i) {
                if (newCaseExpressions[i] != this.caseExpressions[i]) continue;
                newCaseExpressions[i] = SwitchNode.cloneUninitialized(this.caseExpressions[i], materializedTags);
            }
            for (i = 0; i < this.statements.length; ++i) {
                if (newStatements[i] != this.statements[i]) continue;
                newStatements[i] = SwitchNode.cloneUninitialized(this.statements[i], materializedTags);
            }
            SwitchNode materialized = SwitchNode.create(SwitchNode.cloneUninitialized(this.declarations, materializedTags), newCaseExpressions, this.jumptable, newStatements);
            SwitchNode.transferSourceSectionAndTags(this, materialized);
            return materialized;
        }
        return this;
    }

    private boolean needsMaterialization() {
        int i;
        boolean needsMaterialization = false;
        for (i = 0; i < this.caseExpressions.length && !needsMaterialization; ++i) {
            if (JSNodeUtil.isTaggedNode(this.caseExpressions[i])) continue;
            needsMaterialization = true;
        }
        for (i = 0; i < this.statements.length && !needsMaterialization; ++i) {
            if (JSNodeUtil.isTaggedNode(this.statements[i])) continue;
            needsMaterialization = true;
        }
        return needsMaterialization;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        this.executeDeclarations(frame);
        if (this.ordered) {
            return this.executeOrdered(frame);
        }
        return this.executeDefault(frame);
    }

    @ExplodeLoop
    private void executeDeclarations(VirtualFrame frame) {
        for (int i = 0; i < this.declarations.length; ++i) {
            this.declarations[i].execute(frame);
        }
    }

    private Object executeDefault(VirtualFrame frame) {
        int statementStartIndex = this.identifyTargetCase(frame, 0, -1);
        return this.executeStatements(frame, statementStartIndex);
    }

    @Override
    public Object resume(VirtualFrame frame, int stateSlot) {
        Object resumptionResult;
        int statementIndex;
        int caseIndex;
        Object maybeState = this.getState(frame, stateSlot);
        if (maybeState instanceof SwitchResumptionRecord) {
            this.resetState(frame, stateSlot);
            SwitchResumptionRecord state = (SwitchResumptionRecord)maybeState;
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
        int i;
        try {
            for (i = 0; !(i >= this.caseExpressions.length || i >= firstCase && SwitchNode.executeConditionAsBoolean(frame, this.caseExpressions[i])); ++i) {
            }
            int statementStartIndex = this.jumptable[i];
            CompilerAsserts.partialEvaluationConstant(statementStartIndex);
            return statementStartIndex;
        }
        catch (YieldException e) {
            this.setState(frame, stateSlot, new SwitchResumptionRecord(i, -1, null));
            throw e;
        }
    }

    private Object executeStatements(VirtualFrame frame, int statementStartIndex) {
        return this.executeStatements(frame, statementStartIndex, EMPTY, -1);
    }

    @ExplodeLoop
    private Object executeStatements(VirtualFrame frame, int statementStartIndex, Object initialResult, int stateSlot) {
        int statementIndex;
        Object result = initialResult;
        try {
            for (statementIndex = 0; statementIndex < this.statements.length; ++statementIndex) {
                if (statementIndex < statementStartIndex) continue;
                result = this.statements[statementIndex].execute(frame);
            }
            return result;
        }
        catch (YieldException e) {
            this.setState(frame, stateSlot, new SwitchResumptionRecord(-1, statementIndex, result));
            throw e;
        }
    }

    @ExplodeLoop
    private Object executeOrdered(VirtualFrame frame) {
        int statementStartIndex;
        int jumptableIdx;
        JavaScriptNode[] caseExpressionsLocal = this.caseExpressions;
        JavaScriptNode[] statementsLocal = this.statements;
        int[] jumptableLocal = this.jumptable;
        ConditionProfile[] conditionProfilesLocal = this.conditionProfiles;
        boolean caseFound = false;
        Object result = EMPTY;
        for (jumptableIdx = 0; jumptableIdx < caseExpressionsLocal.length; ++jumptableIdx) {
            if (caseFound || SwitchNode.executeConditionAsBoolean(frame, caseExpressionsLocal[jumptableIdx])) {
                caseFound = true;
            }
            statementStartIndex = jumptableLocal[jumptableIdx];
            int statementEndIndex = jumptableLocal[jumptableIdx + 1];
            CompilerAsserts.partialEvaluationConstant(statementStartIndex);
            CompilerAsserts.partialEvaluationConstant(statementEndIndex);
            if (statementStartIndex == statementEndIndex || !conditionProfilesLocal[jumptableIdx].profile(caseFound)) continue;
            for (int statementIndex = statementStartIndex; statementIndex < statementEndIndex; ++statementIndex) {
                result = statementsLocal[statementIndex].execute(frame);
            }
        }
        statementStartIndex = jumptableLocal[jumptableIdx];
        CompilerAsserts.partialEvaluationConstant(statementStartIndex);
        for (int statementIndex = statementStartIndex; statementIndex < statementsLocal.length; ++statementIndex) {
            result = statementsLocal[statementIndex].execute(frame);
        }
        return result;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return SwitchNode.create(SwitchNode.cloneUninitialized(this.declarations, materializedTags), SwitchNode.cloneUninitialized(this.caseExpressions, materializedTags), this.jumptable, SwitchNode.cloneUninitialized(this.statements, materializedTags));
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

