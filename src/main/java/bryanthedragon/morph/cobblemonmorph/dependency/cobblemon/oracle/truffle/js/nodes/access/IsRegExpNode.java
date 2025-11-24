
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsJSClassNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.IsRegExpNodeGen;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSRegExp;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

public abstract class IsRegExpNode
extends JavaScriptBaseNode {
    @Node.Child
    private PropertyGetNode getSymbolMatchNode;

    IsRegExpNode(JSContext context) {
        this.getSymbolMatchNode = this.insert(PropertyGetNode.create(Symbol.SYMBOL_MATCH, false, context));
    }

    public abstract boolean executeBoolean(Object var1);

    @Specialization
    boolean doIsObject(JSDynamicObject obj, @Cached(value="create()") IsJSObjectNode isObjectNode, @Cached(value="create()") JSToBooleanNode toBooleanNode, @Cached(value="createIsJSRegExpNode()") IsJSClassNode isJSRegExpNode, @Cached(value="createBinaryProfile()") ConditionProfile hasMatchSymbol) {
        if (!isObjectNode.executeBoolean(obj)) {
            return false;
        }
        Object isRegExp = this.getSymbolMatchNode.getValue(obj);
        if (hasMatchSymbol.profile(isRegExp != Undefined.instance)) {
            return toBooleanNode.executeBoolean(isRegExp);
        }
        return isJSRegExpNode.executeBoolean(obj);
    }

    @Fallback
    boolean doNonObject(Object obj) {
        return false;
    }

    static IsJSClassNode createIsJSRegExpNode() {
        return IsJSClassNode.create(JSRegExp.INSTANCE);
    }

    public static IsRegExpNode create(JSContext context) {
        return IsRegExpNodeGen.create(context);
    }
}

