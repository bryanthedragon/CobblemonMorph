
package com.oracle.truffle.regex.tregex.util;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.regex.tregex.automaton.StateSet;
import com.oracle.truffle.regex.tregex.nfa.NFA;
import com.oracle.truffle.regex.tregex.nfa.NFAState;
import com.oracle.truffle.regex.tregex.nfa.NFAStateTransition;
import com.oracle.truffle.regex.tregex.util.DotExport;
import com.oracle.truffle.regex.tregex.util.LaTexExport;
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
    private final HashMap<NFAState, Integer> stateNumberMap = new HashMap();

    private NFAExport(NFA nfa, BufferedWriter writer, boolean forward, boolean fullLabels, boolean mergeFinalStates) {
        this.nfa = nfa;
        this.writer = writer;
        this.forward = forward;
        this.fullLabels = fullLabels;
        this.mergeFinalStates = mergeFinalStates;
    }

    @CompilerDirectives.TruffleBoundary
    public static void exportDot(NFA nfa, TruffleFile path, boolean fullLabels, boolean mergeFinalStates) {
        try (BufferedWriter writer = path.newBufferedWriter(StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);){
            new NFAExport(nfa, writer, true, fullLabels, mergeFinalStates).exportDot();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static void exportDotReverse(NFA nfa, TruffleFile path, boolean fullLabels, boolean mergeFinalStates) {
        try (BufferedWriter writer = path.newBufferedWriter(StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);){
            new NFAExport(nfa, writer, false, fullLabels, mergeFinalStates).exportDot();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static void exportLaTex(NFA nfa, TruffleFile path, boolean fullLabels, boolean mergeFinalStates) {
        try (BufferedWriter writer = path.newBufferedWriter(StandardOpenOption.CREATE, StandardOpenOption.WRITE, StandardOpenOption.TRUNCATE_EXISTING);){
            new NFAExport(nfa, writer, true, fullLabels, mergeFinalStates).exportLaTex();
        }
        catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void exportDot() throws IOException {
        this.writer.write("digraph finite_state_machine {");
        this.writer.newLine();
        this.writer.newLine();
        for (NFAState state : this.nfa.getStates()) {
            if (!this.showState(state)) continue;
            this.setDotNodeStyle(state, this.getDotStateStyle(state));
        }
        this.writer.newLine();
        for (NFAState state : this.nfa.getStates()) {
            if (!this.showState(state)) continue;
            for (int i = 0; i < ((NFAStateTransition[])state.getSuccessors(this.forward)).length; ++i) {
                NFAStateTransition transition = ((NFAStateTransition[])state.getSuccessors(this.forward))[i];
                DotExport.printConnection(this.writer, this.labelState(transition.getSource(this.forward), true), this.labelState((NFAState)transition.getTarget(this.forward), true), this.labelTransition(transition, i));
            }
        }
        this.writer.write("}");
        this.writer.newLine();
    }

    private String getDotStateStyle(NFAState state) {
        switch (this.getStateStyle(state)) {
            case ANCHORED_FINAL: {
                return "Mcircle";
            }
            case UN_ANCHORED_FINAL: {
                return "doublecircle";
            }
            case ANCHORED_INITIAL: 
            case UN_ANCHORED_INITIAL: 
            case REGULAR: {
                return "circle";
            }
        }
        throw CompilerDirectives.shouldNotReachHere();
    }

    private void setDotNodeStyle(NFAState state, String style) throws IOException {
        this.writer.write(String.format("    node [shape = %s]; \"%s\";", style, DotExport.escape(this.labelState(state, true))));
        this.writer.newLine();
    }

    private void exportLaTex() throws IOException {
        StateSet visited = StateSet.create(this.nfa);
        this.writer.write("\\documentclass{standalone}\n\\usepackage[utf8]{inputenc}\n\\usepackage[T1]{fontenc}\n\\usepackage{tikz}\n\n\\usetikzlibrary{calc}\n\\usetikzlibrary{automata}\n\\usetikzlibrary{arrows.meta}\n\n\\tikzset{\n\tregex automaton/.style={\n\t\tauto, \n\t\tnode distance=2cm,\n\t\tevery state/.style={\n\t\t\tsemithick,\n\t\t\tfill=gray!5,\n\t\t\tfont=\\footnotesize\\ttfamily,\n\t\t},\n\t\tdouble distance=1.5pt,  % Adjust appearance of accept states\n\t\tinitial text={start},   % label on inital state arrow\n\t\tevery edge/.style={\n\t\t\tdraw,\n\t\t\tfont=\\footnotesize\\ttfamily,\n\t\t\t-Stealth,\n\t\t\tshorten >=1pt,\n\t\t\tauto,\n\t\t\tsemithick\n\t\t},\n\t\tevery loop/.style={\n\t\t\tdraw,\n\t\t\tfont=\\footnotesize\\ttfamily,\n\t\t\t-Stealth,\n\t\t\tshorten >=1pt,\n\t\t\tauto,\n\t\t\tsemithick\n\t\t}\n\t},\n\tanchored/.style={\n\t\tpath picture={\n\t\t\t\\draw[semithick] ($(path picture bounding box.north west)-(0,0.2)$) -- ($(path picture bounding box.north east)-(0,0.2)$);\n\t\t\t\\draw[semithick] ($(path picture bounding box.south west)+(0,0.2)$) -- ($(path picture bounding box.south east)+(0,0.2)$);\n\t\t}\n\t}\n}\n\n\\begin{document}\n\\begin{tikzpicture}[regex automaton]\n\n");
        ArrayList<NFAState> curStates = new ArrayList<NFAState>();
        ArrayList<Object> nextStates = new ArrayList<Object>();
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
        --entryOffset;
        while (!curStates.isEmpty()) {
            Object unAnchoredEntry;
            for (NFAState s : curStates) {
                for (NFAStateTransition t : (NFAStateTransition[])s.getSuccessors()) {
                    if (this.mergeFinalStates && t.getTarget().isFinalState(this.forward) || !visited.add(t.getTarget())) continue;
                    nextStates.add(t.getTarget());
                }
            }
            if (entryOffset >= 0) {
                NFAState anchoredEntry = this.nfa.getAnchoredEntry()[entryOffset].getTarget();
                if (visited.add(anchoredEntry)) {
                    nextStates.add(anchoredEntry);
                }
                if (visited.add(unAnchoredEntry = this.nfa.getUnAnchoredEntry()[entryOffset].getTarget())) {
                    nextStates.add(unAnchoredEntry);
                }
                --entryOffset;
            }
            NFAState relativeTo = null;
            unAnchoredEntry = nextStates.iterator();
            while (unAnchoredEntry.hasNext()) {
                NFAState nextState = (NFAState)unAnchoredEntry.next();
                this.printLaTexState(nextState, relativeTo == null ? (NFAState)curStates.get(0) : relativeTo, relativeTo == null ? "right" : "below");
                relativeTo = nextState;
            }
            ArrayList<NFAState> tmp = curStates;
            curStates = nextStates;
            nextStates = tmp;
            nextStates.clear();
        }
        this.writer.newLine();
        this.writer.write("\\path[->]");
        this.writer.newLine();
        for (NFAState s : this.nfa.getStates()) {
            if (s == null) continue;
            for (int i = 0; i < ((NFAStateTransition[])s.getSuccessors()).length; ++i) {
                NFAStateTransition t;
                t = ((NFAStateTransition[])s.getSuccessors())[i];
                if (!visited.contains(s) || !visited.contains(t.getTarget())) continue;
                this.printLaTexTransition(t, i);
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
        this.writer.write(String.format("\\node[%s] (%s) [%s] {%s};", this.getLaTexStateStyle(state), this.getLaTexStateID(state), offset, LaTexExport.escape(this.labelState(state, false))));
        this.writer.newLine();
    }

    private void printLaTexTransition(NFAStateTransition t, int priority) throws IOException {
        ArrayList<String> options = new ArrayList<String>();
        if (t.getSource() == t.getTarget()) {
            options.add("loop above");
        }
        this.writer.write(String.format("(%s) edge [%s] node {%s} (%s)", this.getLaTexStateID(t.getSource()), options.stream().collect(Collectors.joining(", ")), LaTexExport.escape(this.labelTransition(t, priority)), this.getLaTexStateID(t.getTarget())));
        this.writer.newLine();
    }

    private String getLaTexStateID(NFAState state) {
        if (state.isAnchoredFinalState(this.forward)) {
            return "af";
        }
        if (state.isUnAnchoredFinalState(this.forward)) {
            return "f";
        }
        if (this.nfa.isEntry(state, this.forward)) {
            String lbl = this.nfa.isUnAnchoredEntry(state, this.forward) ? "i" : "ai";
            return lbl + (this.nfa.isUnAnchoredEntry(state, this.forward) ? this.nfa.getUnAnchoredEntryOffset(state, this.forward) : this.nfa.getAnchoredEntryOffset(state, this.forward));
        }
        return "s" + this.stateNumberMap.computeIfAbsent(state, x -> this.nextStateNumber++);
    }

    private String getLaTexStateStyle(NFAState state) {
        switch (this.getStateStyle(state)) {
            case ANCHORED_INITIAL: {
                return "anchored,initial,state";
            }
            case UN_ANCHORED_INITIAL: {
                return "initial,state";
            }
            case ANCHORED_FINAL: {
                return "anchored,accepting,state";
            }
            case UN_ANCHORED_FINAL: {
                return "accepting,state";
            }
            case REGULAR: {
                return "state";
            }
        }
        throw CompilerDirectives.shouldNotReachHere();
    }

    private boolean showState(NFAState state) {
        if (state == null || state == this.nfa.getDummyInitialState()) {
            return false;
        }
        if (this.nfa.isEntry(state, this.forward)) {
            return ((NFAStateTransition[])state.getSuccessors(this.forward)).length > 0;
        }
        if (state.isFinalState(this.forward)) {
            return ((NFAStateTransition[])state.getPredecessors(this.forward)).length > 0;
        }
        return true;
    }

    private StateStyle getStateStyle(NFAState state) {
        if (this.nfa.isEntry(state, this.forward)) {
            if (this.nfa.isAnchoredEntry(state, this.forward) && !this.nfa.isUnAnchoredEntry(state, this.forward)) {
                return StateStyle.ANCHORED_INITIAL;
            }
            return StateStyle.UN_ANCHORED_INITIAL;
        }
        if (this.mergeFinalStates && state.hasTransitionToAnchoredFinalState(this.forward) && !state.hasTransitionToUnAnchoredFinalState(this.forward) || state.isAnchoredFinalState(this.forward)) {
            return StateStyle.ANCHORED_FINAL;
        }
        if (state.isFinalState(this.forward) || this.mergeFinalStates && state.hasTransitionToUnAnchoredFinalState(this.forward)) {
            return StateStyle.UN_ANCHORED_FINAL;
        }
        return StateStyle.REGULAR;
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
        if (!((NFAState)transition.getTarget(this.forward)).isFinalState(this.forward)) {
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

