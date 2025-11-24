
package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.strings.TruffleString;
import java.util.ArrayList;
import java.util.List;

public class JSONData {
    protected List<Object> stack = new ArrayList<Object>();
    private int indent;
    private final TruffleString gap;
    private final List<Object> propertyList;
    private final Object replacerFnObj;
    private static final int MAX_STACK_SIZE = 1000;

    public JSONData(TruffleString gap, Object replacerFnObj, List<Object> replacerList) {
        this.gap = gap;
        this.replacerFnObj = replacerFnObj;
        this.propertyList = replacerList;
    }

    public TruffleString getGap() {
        return this.gap;
    }

    public int getIndent() {
        return this.indent;
    }

    public void setIndent(int indentCount) {
        this.indent = indentCount;
    }

    public List<Object> getPropertyList() {
        return this.propertyList;
    }

    public Object getReplacerFnObj() {
        return this.replacerFnObj;
    }

    public void pushStack(Object value2) {
        this.stack.add(value2);
    }

    public boolean stackTooDeep() {
        return this.stack.size() > 1000;
    }

    public void popStack() {
        this.stack.remove(this.stack.size() - 1);
    }
}

