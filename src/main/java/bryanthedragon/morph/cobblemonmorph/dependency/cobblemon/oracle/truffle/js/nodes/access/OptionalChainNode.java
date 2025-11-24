
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.ControlFlowException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import com.oracle.truffle.js.nodes.control.DeletePropertyNode;
import com.oracle.truffle.js.nodes.unary.JSIsNullOrUndefinedNode;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public final class OptionalChainNode
extends JavaScriptNode {
    @Node.Child
    private JavaScriptNode accessNode;
    private final Object result;

    protected OptionalChainNode(JavaScriptNode chainNode, Object result) {
        this.accessNode = chainNode;
        this.result = result;
    }

    public static JavaScriptNode createTarget(JavaScriptNode chainNode) {
        Object result;
        Object object = result = chainNode instanceof DeletePropertyNode ? Boolean.TRUE : Undefined.instance;
        if (chainNode instanceof JSTargetableNode) {
            return new OptionalTargetableNode((JSTargetableNode)chainNode, result);
        }
        return new OptionalChainNode(chainNode, result);
    }

    public static JavaScriptNode createShortCircuit(JavaScriptNode expressionNode) {
        if (expressionNode instanceof JSTargetableNode) {
            return new ShortCircuitTargetableNode((JSTargetableNode)expressionNode);
        }
        return new ShortCircuitNode(expressionNode);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        try {
            return this.accessNode.execute(frame);
        }
        catch (ShortCircuitException ex) {
            return this.result;
        }
    }

    @Override
    public void executeVoid(VirtualFrame frame) {
        try {
            this.accessNode.executeVoid(frame);
        }
        catch (ShortCircuitException shortCircuitException) {
            // empty catch block
        }
    }

    @Override
    public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        try {
            return this.accessNode.executeInt(frame);
        }
        catch (ShortCircuitException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnexpectedResultException(this.result);
        }
    }

    @Override
    public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
        try {
            return this.accessNode.executeDouble(frame);
        }
        catch (ShortCircuitException ex) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnexpectedResultException(this.result);
        }
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new OptionalChainNode(OptionalChainNode.cloneUninitialized(this.accessNode, materializedTags), this.result);
    }

    public JavaScriptNode getAccessNode() {
        return this.accessNode;
    }

    private static final class ShortCircuitException
    extends ControlFlowException {
        private static final long serialVersionUID = 7544707953047299660L;
        private static final ShortCircuitException INSTANCE = new ShortCircuitException();

        private ShortCircuitException() {
        }

        static ShortCircuitException instance() {
            return INSTANCE;
        }
    }

    public static final class ShortCircuitTargetableNode
    extends JSTargetableNode {
        @Node.Child
        private JSTargetableNode expressionNode;
        @Node.Child
        JSIsNullOrUndefinedNode isNullOrUndefinedNode = JSIsNullOrUndefinedNode.create();
        private final ConditionProfile isNullish = ConditionProfile.createCountingProfile();

        ShortCircuitTargetableNode(JSTargetableNode expressionNode) {
            this.expressionNode = expressionNode;
        }

        private boolean isNullish(Object targetValue) {
            return this.isNullish.profile(this.isNullOrUndefinedNode.executeBoolean(targetValue));
        }

        @Override
        public JavaScriptNode getTarget() {
            return this.expressionNode.getTarget();
        }

        @Override
        public Object execute(VirtualFrame frame) {
            Object result = this.expressionNode.execute(frame);
            if (this.isNullish(result)) {
                throw ShortCircuitException.instance();
            }
            return result;
        }

        @Override
        public void executeVoid(VirtualFrame frame) {
            Object result = this.expressionNode.execute(frame);
            if (this.isNullish(result)) {
                throw ShortCircuitException.instance();
            }
        }

        @Override
        public Object executeWithTarget(VirtualFrame frame, Object target) {
            Object result = this.expressionNode.executeWithTarget(frame, target);
            if (this.isNullish(result)) {
                throw ShortCircuitException.instance();
            }
            return result;
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new ShortCircuitTargetableNode(ShortCircuitTargetableNode.cloneUninitialized(this.expressionNode, materializedTags));
        }
    }

    public static final class ShortCircuitNode
    extends JavaScriptNode {
        @Node.Child
        private JavaScriptNode expressionNode;
        @Node.Child
        JSIsNullOrUndefinedNode isNullOrUndefinedNode = JSIsNullOrUndefinedNode.create();
        private final ConditionProfile isNullish = ConditionProfile.createCountingProfile();

        ShortCircuitNode(JavaScriptNode expressionNode) {
            this.expressionNode = expressionNode;
        }

        private boolean isNullish(Object targetValue) {
            return this.isNullish.profile(this.isNullOrUndefinedNode.executeBoolean(targetValue));
        }

        @Override
        public Object execute(VirtualFrame frame) {
            Object result = this.expressionNode.execute(frame);
            if (this.isNullish(result)) {
                throw ShortCircuitException.instance();
            }
            return result;
        }

        @Override
        public void executeVoid(VirtualFrame frame) {
            Object result = this.expressionNode.execute(frame);
            if (this.isNullish(result)) {
                throw ShortCircuitException.instance();
            }
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new ShortCircuitNode(ShortCircuitNode.cloneUninitialized(this.expressionNode, materializedTags));
        }
    }

    public static final class OptionalTargetableNode
    extends JSTargetableNode {
        private static final Object SHORT_CIRCUIT_MARKER = new Object();
        @Node.Child
        private JSTargetableNode delegateNode;
        private final Object result;

        protected OptionalTargetableNode(JSTargetableNode delegateNode, Object result) {
            this.delegateNode = delegateNode;
            this.result = result;
        }

        @Override
        public JavaScriptNode getTarget() {
            return this.delegateNode.getTarget();
        }

        @Override
        public Object evaluateTarget(VirtualFrame frame) {
            try {
                return this.delegateNode.evaluateTarget(frame);
            }
            catch (ShortCircuitException ex) {
                return SHORT_CIRCUIT_MARKER;
            }
        }

        @Override
        public Object executeWithTarget(VirtualFrame frame, Object target) {
            if (target == SHORT_CIRCUIT_MARKER) {
                return this.result;
            }
            return this.delegateNode.executeWithTarget(frame, target);
        }

        @Override
        public Object execute(VirtualFrame frame) {
            try {
                return this.delegateNode.execute(frame);
            }
            catch (ShortCircuitException ex) {
                return this.result;
            }
        }

        @Override
        protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new OptionalTargetableNode(OptionalTargetableNode.cloneUninitialized(this.delegateNode, materializedTags), this.result);
        }

        public JavaScriptNode getDelegateNode() {
            return this.delegateNode;
        }
    }
}

