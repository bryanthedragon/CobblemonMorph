
package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.access.JSHasPropertyNode;
import com.oracle.truffle.js.nodes.access.JSProxyHasPropertyNode;
import com.oracle.truffle.js.nodes.binary.InNodeGen;
import com.oracle.truffle.js.nodes.binary.JSBinaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Set;

public abstract class InNode
extends JSBinaryNode {
    protected final JSContext context;
    @Node.Child
    private JSHasPropertyNode hasPropertyNode;

    protected InNode(JSContext context, JavaScriptNode left, JavaScriptNode right) {
        super(left, right);
        this.context = context;
    }

    public static InNode create(JSContext context, JavaScriptNode left, JavaScriptNode right) {
        return InNodeGen.create(context, left, right);
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return clazz == Boolean.TYPE;
    }

    @Specialization(guards={"isJSObject(haystack)", "!isJSProxy(haystack)"})
    protected boolean doObject(Object needle, JSDynamicObject haystack) {
        return this.getHasPropertyNode().executeBoolean((Object)haystack, needle);
    }

    @Specialization(guards={"isJSProxy(haystack)"})
    protected boolean doProxy(Object needle, JSDynamicObject haystack, @Cached(value="create(context)") JSProxyHasPropertyNode proxyHasPropertyNode) {
        return proxyHasPropertyNode.executeWithTargetAndKeyBoolean(haystack, needle);
    }

    @Specialization(guards={"isForeignObject(haystack)"})
    protected boolean doForeign(Object needle, Object haystack, @Cached IsObjectNode isObjectNode, @Cached BranchProfile errorBranch) {
        if (isObjectNode.executeBoolean(haystack)) {
            return this.getHasPropertyNode().executeBoolean(haystack, needle);
        }
        errorBranch.enter();
        throw Errors.createTypeErrorNotAnObject(haystack, this);
    }

    @Specialization(guards={"isNullOrUndefined(haystack)"})
    protected static Object doNullOrUndefined(Object needle, Object haystack) {
        throw Errors.createTypeErrorNotAnObject(haystack);
    }

    @Specialization
    protected static Object doSymbol(Object needle, Symbol haystack) {
        throw Errors.createTypeErrorNotAnObject(haystack);
    }

    @Specialization
    protected static Object doTString(Object needle, TruffleString haystack) {
        throw Errors.createTypeErrorNotAnObject(haystack);
    }

    @Specialization
    protected static Object doSafeInteger(Object needle, SafeInteger haystack) {
        throw Errors.createTypeErrorNotAnObject(haystack);
    }

    @Specialization
    protected static Object doBigInt(Object needle, BigInt haystack) {
        throw Errors.createTypeErrorNotAnObject(haystack);
    }

    @Specialization(guards={"!isTruffleObject(haystack)"})
    protected static Object doNotTruffleObject(Object needle, Object haystack) {
        throw Errors.createTypeErrorNotAnObject(haystack);
    }

    private JSHasPropertyNode getHasPropertyNode() {
        if (this.hasPropertyNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.hasPropertyNode = this.insert(JSHasPropertyNode.create());
        }
        return this.hasPropertyNode;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return InNodeGen.create(this.context, InNode.cloneUninitialized(this.getLeft(), materializedTags), InNode.cloneUninitialized(this.getRight(), materializedTags));
    }
}

