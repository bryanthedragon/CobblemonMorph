
package com.oracle.truffle.js.nodes;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Introspectable;
import com.oracle.truffle.api.dsl.TypeSystemReference;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSNodeUtil;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.runtime.JSRealm;

@NodeInfo(language="JavaScript", description="The abstract base node for all JavaScript nodes")
@TypeSystemReference(value=JSTypes.class)
@ImportStatic(value={JSGuards.class})
@Introspectable
public abstract class JavaScriptBaseNode
extends Node {
    public JavaScriptBaseNode() {
        JSNodeUtil.NODE_CREATE_COUNT.inc();
    }

    @Override
    public JavaScriptBaseNode copy() {
        JSNodeUtil.NODE_CREATE_COUNT.inc();
        return (JavaScriptBaseNode)super.copy();
    }

    @Override
    protected void onReplace(Node newNode, CharSequence reason) {
        super.onReplace(newNode, reason);
        JSNodeUtil.NODE_REPLACE_COUNT.inc();
    }

    protected final JSRealm getRealm() {
        return JSRealm.get(this);
    }

    protected final JavaScriptLanguage getLanguage() {
        return JavaScriptLanguage.get(this);
    }

    protected final boolean hasOverloadedOperators(Object obj) {
        assert (!JSGuards.hasOverloadedOperators(obj) || this.getLanguage().getJSContext().getContextOptions().isOperatorOverloading());
        return (CompilerDirectives.inInterpreter() || this.getLanguage().getJSContext().getContextOptions().isOperatorOverloading()) && JSGuards.hasOverloadedOperators(obj);
    }
}

