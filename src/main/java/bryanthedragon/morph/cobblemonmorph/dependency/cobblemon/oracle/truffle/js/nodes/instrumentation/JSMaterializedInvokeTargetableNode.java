
package com.oracle.truffle.js.nodes.instrumentation;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.GlobalConstantNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import com.oracle.truffle.js.nodes.access.OptionalChainNode;
import com.oracle.truffle.js.nodes.access.PrivateFieldGetNode;
import com.oracle.truffle.js.nodes.access.PropertyNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.WithVarWrapperNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.Set;

public abstract class JSMaterializedInvokeTargetableNode
extends JSTargetableNode {
    public static JSTargetableNode createFor(JSTargetableNode target) {
        if (target instanceof PropertyNode) {
            return new MaterializedTargetablePropertyNode((PropertyNode)target);
        }
        if (target instanceof ReadElementNode) {
            return new MaterializedTargetableReadElementNode((ReadElementNode)target);
        }
        if (target instanceof WithVarWrapperNode || target instanceof GlobalConstantNode || target instanceof PrivateFieldGetNode || target instanceof OptionalChainNode.ShortCircuitTargetableNode || target instanceof OptionalChainNode.OptionalTargetableNode) {
            return target;
        }
        throw Errors.shouldNotReachHere("Unsupported materialization node type: " + target.getClass());
    }

    public static class EchoTargetValueNode
    extends JSTargetableNode {
        public static JSTargetableNode create() {
            return new EchoTargetValueNode();
        }

        @Override
        public Object executeWithTarget(VirtualFrame frame, Object target) {
            return target;
        }

        @Override
        public Object execute(VirtualFrame frame) {
            throw Errors.shouldNotReachHere("Must use executeWithTarget()");
        }

        @Override
        public boolean isInstrumentable() {
            return true;
        }

        @Override
        public boolean hasTag(Class<? extends Tag> tag) {
            return tag == JSTags.InputNodeTag.class;
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new EchoTargetValueNode();
        }
    }

    private static class MaterializedTargetablePropertyNode
    extends PropertyNode {
        protected MaterializedTargetablePropertyNode(JSContext context, JavaScriptNode target, Object propertyKey, boolean getOwnProperty, boolean method) {
            super(context, target, propertyKey, getOwnProperty, method);
        }

        MaterializedTargetablePropertyNode(PropertyNode target) {
            this(target.getContext(), new EchoTargetValueNode(), target.getPropertyKey(), target.isOwnProperty(), target.isMethod());
        }

        @Override
        public Object executeWithTarget(VirtualFrame frame, Object targetValue) {
            ((JSTargetableNode)this.getTarget()).executeWithTarget(frame, targetValue);
            return super.executeWithTarget(frame, targetValue);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            throw Errors.shouldNotReachHere("Must use executeWithTarget()");
        }

        @Override
        public boolean isInstrumentable() {
            return true;
        }

        @Override
        public boolean hasTag(Class<? extends Tag> tag) {
            if (tag == JSTags.InputNodeTag.class) {
                return true;
            }
            return super.hasTag(tag);
        }

        @Override
        public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
            return this;
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new MaterializedTargetablePropertyNode(this.getContext(), MaterializedTargetablePropertyNode.cloneUninitialized(this.getTarget(), materializedTags), this.getPropertyKey(), this.isOwnProperty(), this.isMethod());
        }
    }

    private static class MaterializedTargetableReadElementNode
    extends ReadElementNode {
        protected MaterializedTargetableReadElementNode(JavaScriptNode targetNode, JavaScriptNode indexNode, JSContext context) {
            super(targetNode, indexNode, context);
        }

        MaterializedTargetableReadElementNode(ReadElementNode from) {
            this(new EchoTargetValueNode(), from.getElement(), from.getContext());
        }

        @Override
        public Object executeWithTarget(VirtualFrame frame, Object targetValue) {
            ((JSTargetableNode)this.getTarget()).executeWithTarget(frame, targetValue);
            return super.executeWithTarget(frame, targetValue);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            throw Errors.shouldNotReachHere("Must use executeWithTarget()");
        }

        @Override
        public boolean isInstrumentable() {
            return true;
        }

        @Override
        public boolean hasTag(Class<? extends Tag> tag) {
            if (tag == JSTags.InputNodeTag.class) {
                return true;
            }
            return super.hasTag(tag);
        }

        @Override
        public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
            return this;
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new MaterializedTargetableReadElementNode(MaterializedTargetableReadElementNode.cloneUninitialized(this.getTarget(), materializedTags), MaterializedTargetableReadElementNode.cloneUninitialized(this.getIndexNode(), materializedTags), this.context);
        }
    }
}

