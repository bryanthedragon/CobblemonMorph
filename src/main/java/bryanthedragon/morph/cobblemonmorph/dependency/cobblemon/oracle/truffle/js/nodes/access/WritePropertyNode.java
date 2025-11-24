
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.GlobalScopeNode;
import com.oracle.truffle.js.nodes.access.HasPropertyCacheNode;
import com.oracle.truffle.js.nodes.access.JSTargetableWriteNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.access.SuperPropertyReferenceNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTaggedExecutionNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.instrumentation.NodeObjectDescriptor;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Set;

public class WritePropertyNode
extends JSTargetableWriteNode {
    @Node.Child
    protected JavaScriptNode targetNode;
    @Node.Child
    protected JavaScriptNode rhsNode;
    @Node.Child
    protected PropertySetNode cache;
    @CompilerDirectives.CompilationFinal
    private byte valueState;
    private static final byte VALUE_INT = 1;
    private static final byte VALUE_DOUBLE = 2;
    private static final byte VALUE_OBJECT = 3;
    private final boolean verifyHasProperty;
    @CompilerDirectives.CompilationFinal
    private BranchProfile referenceErrorBranch;
    @Node.Child
    protected HasPropertyCacheNode hasProperty;

    protected WritePropertyNode(JavaScriptNode target, JavaScriptNode rhs, Object propertyKey, boolean isGlobal, JSContext context, boolean isStrict, boolean verifyHasProperty) {
        this.targetNode = target;
        this.rhsNode = rhs;
        boolean superProperty = target instanceof SuperPropertyReferenceNode;
        this.cache = PropertySetNode.createImpl(propertyKey, isGlobal, context, isStrict, false, JSAttributes.getDefault(), false, superProperty);
        this.verifyHasProperty = verifyHasProperty;
        if (verifyHasProperty) {
            this.referenceErrorBranch = BranchProfile.create();
            if (isStrict || !isGlobal) {
                this.hasProperty = HasPropertyCacheNode.create(propertyKey, context);
            }
        }
    }

    public static WritePropertyNode create(JavaScriptNode target, Object propertyKey, JavaScriptNode rhs, JSContext ctx, boolean isStrict) {
        return WritePropertyNode.create(target, propertyKey, rhs, ctx, isStrict, false, false);
    }

    public static WritePropertyNode create(JavaScriptNode target, Object propertyKey, JavaScriptNode rhs, JSContext ctx, boolean isStrict, boolean isGlobal, boolean verifyHasProperty) {
        return new WritePropertyNode(target, rhs, propertyKey, isGlobal, ctx, isStrict, verifyHasProperty);
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if ((tag == JSTags.WriteVariableTag.class || tag == StandardTags.WriteVariableTag.class) && this.isScopeAccess()) {
            return true;
        }
        if (tag == JSTags.WritePropertyTag.class && !this.isScopeAccess()) {
            return true;
        }
        if (tag == JSTags.InputNodeTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    private boolean isScopeAccess() {
        return this.targetNode instanceof GlobalScopeNode;
    }

    @Override
    public Object getNodeObject() {
        if (this.isScopeAccess()) {
            NodeObjectDescriptor descriptor = JSTags.createNodeObjectDescriptor("name", this.getKey());
            descriptor.addProperty("writeVariableName", this.getKey());
            return descriptor;
        }
        NodeObjectDescriptor descriptor = JSTags.createNodeObjectDescriptor("key", this.getKey());
        return descriptor;
    }

    @Override
    public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
        if (materializedTags.contains(JSTags.WritePropertyTag.class) && !this.isScopeAccess() && this.materializationNeeded()) {
            JavaScriptNode clonedRhs;
            JavaScriptNode clonedTarget = this.targetNode == null || this.targetNode.hasSourceSection() ? this.targetNode : JSTaggedExecutionNode.createForInput(this.targetNode, this, materializedTags);
            JavaScriptNode javaScriptNode = clonedRhs = this.rhsNode == null || this.rhsNode.hasSourceSection() ? this.rhsNode : JSTaggedExecutionNode.createForInput(this.rhsNode, this, materializedTags);
            if (clonedTarget == this.targetNode && clonedRhs == this.rhsNode) {
                return this;
            }
            if (clonedTarget == this.targetNode) {
                clonedTarget = WritePropertyNode.cloneUninitialized(this.targetNode, materializedTags);
            }
            if (clonedRhs == this.rhsNode) {
                clonedRhs = WritePropertyNode.cloneUninitialized(this.rhsNode, materializedTags);
            }
            WritePropertyNode clone = WritePropertyNode.create(clonedTarget, this.cache.getKey(), clonedRhs, this.cache.getContext(), this.cache.isStrict(), this.cache.isGlobal(), this.verifyHasProperty);
            WritePropertyNode.transferSourceSectionAndTags(this, clone);
            return clone;
        }
        return this;
    }

    private boolean materializationNeeded() {
        return this.targetNode != null && !this.targetNode.hasSourceSection() || this.rhsNode != null && !this.rhsNode.hasSourceSection();
    }

    @Override
    public final JavaScriptNode getTarget() {
        return this.targetNode;
    }

    @Override
    public final JavaScriptNode getRhs() {
        return this.rhsNode;
    }

    public final Object getKey() {
        return this.cache.getKey();
    }

    public final boolean isGlobal() {
        return this.cache.isGlobal();
    }

    public final Object executeWithValue(Object obj, Object value2) {
        this.verifyBindingStillExists(obj);
        this.cache.setValue(obj, value2);
        return value2;
    }

    public final int executeIntWithValue(Object obj, int value2) {
        this.verifyBindingStillExists(obj);
        this.cache.setValueInt(obj, value2);
        return value2;
    }

    public final double executeDoubleWithValue(Object obj, double value2) {
        this.verifyBindingStillExists(obj);
        this.cache.setValueDouble(obj, value2);
        return value2;
    }

    private void executeEvaluated(Object obj, Object value2, Object receiver) {
        this.verifyBindingStillExists(obj);
        this.cache.setValue(obj, value2, receiver);
    }

    private int executeIntEvaluated(Object obj, int value2, Object receiver) {
        this.verifyBindingStillExists(obj);
        this.cache.setValueInt(obj, value2, receiver);
        return value2;
    }

    private double executeDoubleEvaluated(Object obj, double value2, Object receiver) {
        this.verifyBindingStillExists(obj);
        this.cache.setValueDouble(obj, value2, receiver);
        return value2;
    }

    @Override
    public final Object execute(VirtualFrame frame) {
        Object target = this.evaluateTarget(frame);
        Object receiver = WritePropertyNode.evaluateReceiver(this.targetNode, frame, target);
        Object value2 = this.rhsNode.execute(frame);
        this.executeEvaluated(target, value2, receiver);
        return value2;
    }

    @Override
    public final int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        Object target = this.evaluateTarget(frame);
        Object receiver = WritePropertyNode.evaluateReceiver(this.targetNode, frame, target);
        try {
            int value2 = this.rhsNode.executeInt(frame);
            return this.executeIntEvaluated(target, value2, receiver);
        }
        catch (UnexpectedResultException e) {
            this.executeEvaluated(target, e.getResult(), receiver);
            throw e;
        }
    }

    @Override
    public final double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
        Object target = this.evaluateTarget(frame);
        Object receiver = WritePropertyNode.evaluateReceiver(this.targetNode, frame, target);
        try {
            double value2 = this.rhsNode.executeDouble(frame);
            return this.executeDoubleEvaluated(target, value2, receiver);
        }
        catch (UnexpectedResultException e) {
            this.executeEvaluated(target, e.getResult(), receiver);
            throw e;
        }
    }

    @Override
    public final void executeVoid(VirtualFrame frame) {
        Object target = this.evaluateTarget(frame);
        Object receiver = WritePropertyNode.evaluateReceiver(this.targetNode, frame, target);
        byte vs = this.valueState;
        if (vs == 0) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(frame, target, receiver);
            return;
        }
        if (vs == 1) {
            try {
                int value2 = this.rhsNode.executeInt(frame);
                this.executeIntEvaluated(target, value2, receiver);
            }
            catch (UnexpectedResultException e) {
                this.valueState = (byte)3;
                this.executeEvaluated(target, e.getResult(), receiver);
            }
        } else if (vs == 2) {
            try {
                double value3 = this.rhsNode.executeDouble(frame);
                this.executeDoubleEvaluated(target, value3, receiver);
            }
            catch (UnexpectedResultException e) {
                this.valueState = (byte)3;
                this.executeEvaluated(target, e.getResult(), receiver);
            }
        } else {
            assert (vs == 3);
            Object value4 = this.rhsNode.execute(frame);
            this.executeEvaluated(target, value4, receiver);
        }
    }

    private void executeAndSpecialize(VirtualFrame frame, Object target, Object receiver) {
        CompilerAsserts.neverPartOfCompilation();
        Object value2 = this.rhsNode.execute(frame);
        if (value2 instanceof Integer) {
            this.valueState = 1;
            this.executeIntEvaluated(target, (Integer)value2, receiver);
        } else if (value2 instanceof Double) {
            this.valueState = (byte)2;
            this.executeDoubleEvaluated(target, (Double)value2, receiver);
        } else {
            this.valueState = (byte)3;
            this.executeEvaluated(target, value2, receiver);
        }
    }

    @Override
    public final void executeWrite(VirtualFrame frame, Object value2) {
        Object target = this.evaluateTarget(frame);
        Object receiver = WritePropertyNode.evaluateReceiver(this.targetNode, frame, target);
        this.executeEvaluated(target, value2, receiver);
    }

    @Override
    public final Object executeWithTarget(VirtualFrame frame, Object target) {
        Object value2 = this.rhsNode.execute(frame);
        this.executeEvaluated(target, value2, target);
        return value2;
    }

    @Override
    public final Object evaluateTarget(VirtualFrame frame) {
        Object target = this.targetNode.execute(frame);
        return this.verifyHasProperty && this.isUnresolvableReference(target) ? null : target;
    }

    private boolean isUnresolvableReference(Object target) {
        assert (this.verifyHasProperty);
        return !this.bindingExists(target);
    }

    private void verifyBindingStillExists(Object target) {
        if (this.verifyHasProperty && (target == null || !this.bindingExists(target))) {
            this.unresolvablePropertyInStrictMode(target);
        }
    }

    private boolean bindingExists(Object target) {
        assert (this.verifyHasProperty);
        if (!this.cache.isStrict() && this.cache.isGlobal() && this.cache.getContext().getGlobalObjectPristineAssumption().isValid()) {
            return true;
        }
        if (this.hasProperty == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.hasProperty = this.insert(HasPropertyCacheNode.create(this.cache.getKey(), this.cache.getContext()));
        }
        return this.hasProperty.hasProperty(target) || !this.cache.isStrict();
    }

    private void unresolvablePropertyInStrictMode(Object thisObj) {
        this.referenceErrorBranch.enter();
        assert (!this.cache.isGlobal() || thisObj == null || JSDynamicObject.isJSDynamicObject(thisObj) && thisObj == this.getRealm().getGlobalObject());
        throw Errors.createReferenceErrorNotDefined(this.cache.getContext(), this.getKey(), this);
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return WritePropertyNode.create(WritePropertyNode.cloneUninitialized(this.targetNode, materializedTags), this.cache.getKey(), WritePropertyNode.cloneUninitialized(this.rhsNode, materializedTags), this.cache.getContext(), this.cache.isStrict(), this.cache.isGlobal(), this.verifyHasProperty);
    }

    @Override
    public boolean isResultAlwaysOfType(Class<?> clazz) {
        return this.getRhs().isResultAlwaysOfType(clazz);
    }
}

