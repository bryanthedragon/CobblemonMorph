
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ReadNode;
import com.oracle.truffle.js.nodes.access.GlobalScopeNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTaggedExecutionNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.instrumentation.NodeObjectDescriptor;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import java.util.Objects;
import java.util.Set;

public class PropertyNode
extends JSTargetableNode
implements ReadNode {
    @Node.Child
    private JavaScriptNode target;
    @Node.Child
    private PropertyGetNode cache;

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if ((tag == JSTags.ReadVariableTag.class || tag == StandardTags.ReadVariableTag.class) && this.isScopeAccess()) {
            return true;
        }
        if (tag == JSTags.ReadPropertyTag.class && !this.isScopeAccess()) {
            return true;
        }
        if (tag == JSTags.InputNodeTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    private boolean isScopeAccess() {
        return this.target instanceof GlobalScopeNode;
    }

    @Override
    public Object getNodeObject() {
        if (this.isScopeAccess()) {
            NodeObjectDescriptor descriptor = JSTags.createNodeObjectDescriptor("name", this.getPropertyKey());
            descriptor.addProperty("readVariableName", this.getPropertyKey());
            return descriptor;
        }
        return JSTags.createNodeObjectDescriptor("key", this.getPropertyKey());
    }

    @Override
    public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
        if (materializedTags.contains(JSTags.ReadPropertyTag.class) && !this.isScopeAccess()) {
            if (this.target == null || this.target.hasSourceSection()) {
                return this;
            }
            JavaScriptNode clonedTarget = JSTaggedExecutionNode.createForInput(this.target, this.target.hasSourceSection() ? this.target : this, materializedTags);
            if (clonedTarget == this.target) {
                return this;
            }
            PropertyNode propertyNode = new PropertyNode(this.cache.getContext(), clonedTarget, this.cache.getKey(), this.cache.isOwnProperty(), this.cache.isMethod());
            PropertyNode.transferSourceSectionAndTags(this, propertyNode);
            return propertyNode;
        }
        return this;
    }

    protected PropertyNode(JSContext context, JavaScriptNode target, Object propertyKey, boolean getOwnProperty, boolean method) {
        this.target = target;
        this.cache = PropertyGetNode.create(propertyKey, false, context, getOwnProperty, method);
    }

    public static PropertyNode createProperty(JSContext ctx, JavaScriptNode target, Object propertyKey, boolean method) {
        assert (JSRuntime.isPropertyKey(propertyKey));
        return new PropertyNode(ctx, target, propertyKey, false, method);
    }

    public static PropertyNode createProperty(JSContext ctx, JavaScriptNode target, Object propertyKey) {
        return PropertyNode.createProperty(ctx, target, propertyKey, false);
    }

    public static PropertyNode createMethod(JSContext ctx, JavaScriptNode target, Object propertyKey) {
        return PropertyNode.createProperty(ctx, target, propertyKey, true);
    }

    public static PropertyNode createGetHidden(JSContext ctx, JavaScriptNode target, HiddenKey hiddenKey) {
        return new PropertyNode(ctx, target, hiddenKey, true, false);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object targetValue = this.evaluateTarget(frame);
        return this.executeWithTarget(targetValue, PropertyNode.evaluateReceiver(this.target, frame, targetValue));
    }

    @Override
    public Object executeWithTarget(VirtualFrame frame, Object targetValue) {
        return this.executeWithTarget(targetValue, targetValue);
    }

    public Object executeWithTarget(Object targetValue) {
        return this.executeWithTarget(targetValue, targetValue);
    }

    public Object executeWithTarget(Object targetValue, Object receiverValue) {
        return this.cache.getValueOrUndefined(targetValue, receiverValue);
    }

    @Override
    public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        Object targetValue = this.evaluateTarget(frame);
        return this.executeInt(targetValue, PropertyNode.evaluateReceiver(this.target, frame, targetValue));
    }

    public int executeInt(Object targetValue) throws UnexpectedResultException {
        return this.executeInt(targetValue, targetValue);
    }

    public int executeInt(Object targetValue, Object receiverValue) throws UnexpectedResultException {
        return this.cache.getValueInt(targetValue, receiverValue);
    }

    @Override
    public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
        Object targetValue = this.evaluateTarget(frame);
        return this.executeDouble(targetValue, PropertyNode.evaluateReceiver(this.target, frame, targetValue));
    }

    public double executeDouble(Object targetValue) throws UnexpectedResultException {
        return this.executeDouble(targetValue, targetValue);
    }

    public double executeDouble(Object targetValue, Object receiverValue) throws UnexpectedResultException {
        return this.cache.getValueDouble(targetValue, receiverValue);
    }

    @Override
    public final Object evaluateTarget(VirtualFrame frame) {
        return this.target.execute(frame);
    }

    @Override
    public JavaScriptNode getTarget() {
        return this.target;
    }

    public Object getPropertyKey() {
        return this.cache.getKey();
    }

    @Override
    @CompilerDirectives.TruffleBoundary
    public String toString() {
        return super.toString() + " property = " + this.cache.getKey();
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new PropertyNode(this.cache.getContext(), PropertyNode.cloneUninitialized(this.target, materializedTags), this.cache.getKey(), this.cache.isOwnProperty(), this.cache.isMethod());
    }

    @Override
    public String expressionToString() {
        if (this.target != null) {
            return Objects.toString(this.target.expressionToString(), "(intermediate value)") + "." + this.getPropertyKey();
        }
        return null;
    }

    public final boolean isOwnProperty() {
        return this.cache.isOwnProperty();
    }

    public final boolean isMethod() {
        return this.cache.isMethod();
    }

    public JSContext getContext() {
        return this.cache.getContext();
    }
}

