
package com.oracle.truffle.regex.tregex.nfa;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.regex.charset.CodePointSet;
import com.oracle.truffle.regex.tregex.automaton.AbstractTransition;
import com.oracle.truffle.regex.tregex.nfa.NFAState;
import com.oracle.truffle.regex.tregex.parser.ast.GroupBoundaries;
import com.oracle.truffle.regex.tregex.util.json.Json;
import com.oracle.truffle.regex.tregex.util.json.JsonConvertible;
import com.oracle.truffle.regex.tregex.util.json.JsonValue;

public final class NFAStateTransition
implements AbstractTransition<NFAState, NFAStateTransition>,
JsonConvertible {
    private final short id;
    @CompilerDirectives.CompilationFinal
    private NFAState source;
    private final NFAState target;
    private final CodePointSet codePointSet;
    private final GroupBoundaries groupBoundaries;

    public NFAStateTransition(short id, NFAState source, NFAState target, CodePointSet codePointSet, GroupBoundaries groupBoundaries) {
        this.id = id;
        this.source = source;
        this.target = target;
        this.codePointSet = codePointSet;
        this.groupBoundaries = groupBoundaries;
    }

    @Override
    public int getId() {
        return this.id;
    }

    @Override
    public NFAState getSource() {
        return this.source;
    }

    public void setSource(NFAState source) {
        this.source = source;
    }

    @Override
    public NFAState getTarget() {
        return this.target;
    }

    public NFAState getSource(boolean forward) {
        return forward ? this.source : this.target;
    }

    public CodePointSet getCodePointSet() {
        return this.codePointSet;
    }

    public GroupBoundaries getGroupBoundaries() {
        return this.groupBoundaries;
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson() {
        return Json.obj(Json.prop("id", this.id), Json.prop("source", this.source.getId()), Json.prop("target", this.target.getId()), Json.prop("matcherBuilder", this.codePointSet.toString()), Json.prop("groupBoundaries", this.groupBoundaries), Json.prop("sourceSections", this.groupBoundaries.indexUpdateSourceSectionsToJson(this.source.getStateSet().getStateIndex())));
    }

    @CompilerDirectives.TruffleBoundary
    public JsonValue toJson(boolean forward) {
        return Json.obj(Json.prop("id", this.id), Json.prop("source", this.getSource(forward).getId()), Json.prop("target", ((NFAState)this.getTarget(forward)).getId()), Json.prop("matcherBuilder", this.codePointSet.toString()), Json.prop("groupBoundaries", this.groupBoundaries), Json.prop("sourceSections", this.groupBoundaries.indexUpdateSourceSectionsToJson(this.source.getStateSet().getStateIndex())));
    }
}

