
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSGuardDisconnectedArgumentWriteNodeGen;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.access.WriteNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.instrumentation.NodeObjectDescriptor;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsObject;
import java.util.Set;

public abstract class JSGuardDisconnectedArgumentWrite
extends JavaScriptNode
implements WriteNode {
    private final int argumentIndex;
    @Node.Child
    @Executed
    JavaScriptNode argumentsArrayNode;
    @Node.Child
    @Executed
    JavaScriptNode rhsNode;
    @Node.Child
    private WriteElementNode writeArgumentsElementNode;
    private final TruffleString name;

    JSGuardDisconnectedArgumentWrite(int index, WriteElementNode argumentsArrayAccess, JavaScriptNode argumentsArray, JavaScriptNode rhs, TruffleString name) {
        this.argumentIndex = index;
        this.argumentsArrayNode = argumentsArray;
        this.rhsNode = rhs;
        this.writeArgumentsElementNode = argumentsArrayAccess;
        this.name = name;
    }

    public static JSGuardDisconnectedArgumentWrite create(int index, WriteElementNode argumentsArrayAccess, JavaScriptNode argumentsArray, JavaScriptNode rhs, TruffleString name) {
        return JSGuardDisconnectedArgumentWriteNodeGen.create(index, argumentsArrayAccess, argumentsArray, rhs, name);
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.WriteVariableTag.class || tag == StandardTags.WriteVariableTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public Object getNodeObject() {
        NodeObjectDescriptor descriptor = JSTags.createNodeObjectDescriptor("name", this.name);
        descriptor.addProperty("writeVariableName", (Object)this.name);
        return descriptor;
    }

    @Specialization(guards={"!isArgumentsDisconnected(argumentsArray)"})
    public Object doObject(JSArgumentsObject argumentsArray, Object value2, @Cached(value="createBinaryProfile()") @Cached.Shared(value="unconnected") ConditionProfile unconnected) {
        assert (JSArgumentsArray.isJSArgumentsObject(argumentsArray));
        if (unconnected.profile(this.argumentIndex >= JSAbstractArgumentsArray.getConnectedArgumentCount(argumentsArray))) {
            JSAbstractArgumentsArray.disconnectIndex(argumentsArray, this.argumentIndex, value2);
        } else {
            this.writeArgumentsElementNode.executeWithTargetAndIndexAndValue((Object)argumentsArray, this.argumentIndex, value2);
        }
        return value2;
    }

    @Specialization(guards={"isArgumentsDisconnected(argumentsArray)"})
    public Object doObjectDisconnected(JSArgumentsObject argumentsArray, Object value2, @Cached(value="createBinaryProfile()") ConditionProfile wasDisconnected, @Cached(value="createBinaryProfile()") @Cached.Shared(value="unconnected") ConditionProfile unconnected) {
        assert (JSArgumentsArray.isJSArgumentsObject(argumentsArray));
        if (wasDisconnected.profile(JSAbstractArgumentsArray.wasIndexDisconnected(argumentsArray, this.argumentIndex))) {
            JSAbstractArgumentsArray.setDisconnectedIndexValue(argumentsArray, this.argumentIndex, value2);
        } else if (unconnected.profile(this.argumentIndex >= JSAbstractArgumentsArray.getConnectedArgumentCount(argumentsArray))) {
            JSAbstractArgumentsArray.disconnectIndex(argumentsArray, this.argumentIndex, value2);
        } else {
            this.writeArgumentsElementNode.executeWithTargetAndIndexAndValue((Object)argumentsArray, this.argumentIndex, value2);
        }
        return value2;
    }

    @Override
    public final void executeWrite(VirtualFrame frame, Object value2) {
        this.executeWrite(frame, this.argumentsArrayNode.execute(frame), value2);
    }

    protected abstract void executeWrite(VirtualFrame var1, Object var2, Object var3);

    @Override
    public JavaScriptNode getRhs() {
        return this.rhsNode;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return JSGuardDisconnectedArgumentWriteNodeGen.create(this.argumentIndex, JSGuardDisconnectedArgumentWrite.cloneUninitialized(this.writeArgumentsElementNode, materializedTags), JSGuardDisconnectedArgumentWrite.cloneUninitialized(this.argumentsArrayNode, materializedTags), JSGuardDisconnectedArgumentWrite.cloneUninitialized(this.rhsNode, materializedTags), this.name);
    }
}

