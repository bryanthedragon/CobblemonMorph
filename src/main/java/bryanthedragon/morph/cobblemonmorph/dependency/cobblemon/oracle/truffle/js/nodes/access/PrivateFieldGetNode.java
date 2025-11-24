
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
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ReadNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import com.oracle.truffle.js.nodes.access.PrivateFieldGetNodeGen;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Objects;
import java.util.Set;

public abstract class PrivateFieldGetNode
extends JSTargetableNode
implements ReadNode {
    @Node.Child
    @Executed
    protected JavaScriptNode targetNode;
    @Node.Child
    @Executed
    protected JavaScriptNode keyNode;
    protected final JSContext context;

    public static PrivateFieldGetNode create(JavaScriptNode targetNode, JavaScriptNode keyNode, JSContext context) {
        return PrivateFieldGetNodeGen.create(targetNode, keyNode, context);
    }

    protected PrivateFieldGetNode(JavaScriptNode targetNode, JavaScriptNode keyNode, JSContext context) {
        this.targetNode = targetNode;
        this.keyNode = keyNode;
        this.context = context;
    }

    @Specialization(limit="3")
    Object doField(JSObject target, HiddenKey key, @CachedLibrary(value="target") DynamicObjectLibrary access, @Cached BranchProfile errorBranch) {
        if (Properties.containsKey(access, target, key)) {
            return Properties.getOrDefault(access, target, key, Undefined.instance);
        }
        errorBranch.enter();
        return this.missing(target, key);
    }

    @Specialization
    Object doMethod(JSObject target, JSFunctionObject method) {
        return method;
    }

    @Specialization
    Object doAccessor(JSObject target, Accessor accessor, @Cached(value="createCall()") JSFunctionCallNode callNode, @Cached BranchProfile errorBranch) {
        Object getter = accessor.getGetter();
        if (getter == Undefined.instance) {
            errorBranch.enter();
            throw Errors.createTypeErrorCannotGetAccessorProperty(this.keyAsString(), target, this);
        }
        return callNode.executeCall(JSArguments.createZeroArg(target, getter));
    }

    @CompilerDirectives.TruffleBoundary
    @Fallback
    Object missing(Object target, Object key) {
        boolean fieldAccess = key instanceof HiddenKey;
        throw Errors.createTypeErrorCannotGetPrivateMember(fieldAccess, this.keyAsString(), this);
    }

    @CompilerDirectives.TruffleBoundary
    private TruffleString keyAsString() {
        return Strings.fromJavaString(this.keyNode.expressionToString());
    }

    @Override
    public final JavaScriptNode getTarget() {
        return this.targetNode;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return PrivateFieldGetNode.create(PrivateFieldGetNode.cloneUninitialized(this.targetNode, materializedTags), PrivateFieldGetNode.cloneUninitialized(this.keyNode, materializedTags), this.context);
    }

    @Override
    public String expressionToString() {
        if (this.targetNode != null && this.keyNode != null) {
            return Objects.toString(this.targetNode.expressionToString(), "(intermediate value)") + "." + Objects.toString(this.keyAsString(), "(intermediate value)");
        }
        return null;
    }
}

