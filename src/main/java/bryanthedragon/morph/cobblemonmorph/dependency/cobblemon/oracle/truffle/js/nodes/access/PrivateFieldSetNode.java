
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import com.oracle.truffle.js.nodes.access.PrivateFieldSetNodeGen;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public abstract class PrivateFieldSetNode
extends JSTargetableNode {
    @Node.Child
    @Executed
    protected JavaScriptNode targetNode;
    @Node.Child
    @Executed
    protected JavaScriptNode keyNode;
    @Node.Child
    @Executed
    protected JavaScriptNode valueNode;
    protected final JSContext context;

    public static PrivateFieldSetNode create(JavaScriptNode targetNode, JavaScriptNode keyNode, JavaScriptNode valueNode, JSContext context) {
        return PrivateFieldSetNodeGen.create(targetNode, keyNode, valueNode, context);
    }

    protected PrivateFieldSetNode(JavaScriptNode targetNode, JavaScriptNode keyNode, JavaScriptNode valueNode, JSContext context) {
        this.targetNode = targetNode;
        this.keyNode = keyNode;
        this.valueNode = valueNode;
        this.context = context;
    }

    @Specialization(limit="3")
    Object doField(JSObject target, HiddenKey key, Object value2, @CachedLibrary(value="target") DynamicObjectLibrary access, @Cached BranchProfile errorBranch) {
        if (!Properties.putIfPresent(access, target, key, value2)) {
            errorBranch.enter();
            this.missing(target, key, value2);
        }
        return value2;
    }

    @Specialization
    Object doAccessor(JSObject target, Accessor accessor, Object value2, @Cached(value="createCall()") JSFunctionCallNode callNode, @Cached BranchProfile errorBranch) {
        Object setter = accessor.getSetter();
        if (setter == Undefined.instance) {
            errorBranch.enter();
            throw Errors.createTypeErrorCannotSetAccessorProperty(this.keyAsString(), target);
        }
        callNode.executeCall(JSArguments.createOneArg(target, setter, value2));
        return value2;
    }

    @CompilerDirectives.TruffleBoundary
    @Fallback
    Object missing(Object target, Object key, Object value2) {
        throw Errors.createTypeErrorCannotSetPrivateMember(this.keyAsString(), this);
    }

    @CompilerDirectives.TruffleBoundary
    private Object keyAsString() {
        return Strings.fromJavaString(this.keyNode.expressionToString());
    }

    @Override
    public final JavaScriptNode getTarget() {
        return this.targetNode;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return PrivateFieldSetNode.create(PrivateFieldSetNode.cloneUninitialized(this.targetNode, materializedTags), PrivateFieldSetNode.cloneUninitialized(this.keyNode, materializedTags), PrivateFieldSetNode.cloneUninitialized(this.valueNode, materializedTags), this.context);
    }
}

