
package com.oracle.truffle.js.nodes;

import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.decorators.DecoratorListEvaluationNode;
import com.oracle.truffle.js.nodes.JSFrameDescriptor;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.nodes.access.ConstantVariableWriteNode;
import com.oracle.truffle.js.nodes.access.DeclareGlobalNode;
import com.oracle.truffle.js.nodes.access.GetIteratorNode;
import com.oracle.truffle.js.nodes.access.IteratorToArrayNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.access.ObjectLiteralNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.access.WritePropertyNode;
import com.oracle.truffle.js.nodes.control.AbstractBlockNode;
import com.oracle.truffle.js.nodes.control.BreakNode;
import com.oracle.truffle.js.nodes.control.BreakTarget;
import com.oracle.truffle.js.nodes.control.ContinueNode;
import com.oracle.truffle.js.nodes.control.ContinueTarget;
import com.oracle.truffle.js.nodes.control.ContinueTargetNode;
import com.oracle.truffle.js.nodes.control.DirectBreakTargetNode;
import com.oracle.truffle.js.nodes.control.IfNode;
import com.oracle.truffle.js.nodes.control.LabelNode;
import com.oracle.truffle.js.nodes.control.ReturnNode;
import com.oracle.truffle.js.nodes.control.ReturnTargetNode;
import com.oracle.truffle.js.nodes.control.StatementNode;
import com.oracle.truffle.js.nodes.control.SwitchNode;
import com.oracle.truffle.js.nodes.function.AbstractBodyNode;
import com.oracle.truffle.js.nodes.function.AbstractFunctionArgumentsNode;
import com.oracle.truffle.js.nodes.function.BlockScopeNode;
import com.oracle.truffle.js.nodes.function.ConstructorRootNode;
import com.oracle.truffle.js.nodes.function.FunctionBodyNode;
import com.oracle.truffle.js.nodes.function.FunctionRootNode;
import com.oracle.truffle.js.nodes.function.IterationScopeNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.function.JSFunctionExpressionNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.util.InternalSlotId;
import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@GeneratedBy(value=NodeFactory.class)
public class NodeFactoryProxyGen
extends NodeFactory {
    private final InvocationHandler handler;
    private final Method[] methods = new Method[198];

    private NodeFactoryProxyGen(InvocationHandler handler) {
        this.handler = handler;
    }

    public static NodeFactory create(InvocationHandler handler) {
        return new NodeFactoryProxyGen(handler);
    }

    @Override
    public JavaScriptNode createUnary(NodeFactory.UnaryOperation arg0, JavaScriptNode arg1) {
        Method method = this.methods[0];
        if (method == null) {
            try {
                this.methods[0] = method = NodeFactory.class.getMethod("createUnary", NodeFactory.UnaryOperation.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createLocalVarInc(NodeFactory.UnaryOperation arg0, JSFrameSlot arg1, boolean arg2, ScopeFrameNode arg3) {
        Method method = this.methods[1];
        if (method == null) {
            try {
                this.methods[1] = method = NodeFactory.class.getMethod("createLocalVarInc", NodeFactory.UnaryOperation.class, JSFrameSlot.class, Boolean.TYPE, ScopeFrameNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createToNumericOperand(JavaScriptNode arg0) {
        Method method = this.methods[2];
        if (method == null) {
            try {
                this.methods[2] = method = NodeFactory.class.getMethod("createToNumericOperand", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createDual(JSContext arg0, JavaScriptNode arg1, JavaScriptNode arg2) {
        Method method = this.methods[3];
        if (method == null) {
            try {
                this.methods[3] = method = NodeFactory.class.getMethod("createDual", JSContext.class, JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createBinary(JSContext arg0, NodeFactory.BinaryOperation arg1, JavaScriptNode arg2, JavaScriptNode arg3) {
        Method method = this.methods[4];
        if (method == null) {
            try {
                this.methods[4] = method = NodeFactory.class.getMethod("createBinary", JSContext.class, NodeFactory.BinaryOperation.class, JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createTypeofIdentical(JavaScriptNode arg0, TruffleString arg1) {
        Method method = this.methods[5];
        if (method == null) {
            try {
                this.methods[5] = method = NodeFactory.class.getMethod("createTypeofIdentical", JavaScriptNode.class, TruffleString.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createLogicalOr(JavaScriptNode arg0, JavaScriptNode arg1) {
        Method method = this.methods[6];
        if (method == null) {
            try {
                this.methods[6] = method = NodeFactory.class.getMethod("createLogicalOr", JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createNotUndefinedOr(JavaScriptNode arg0, JavaScriptNode arg1) {
        Method method = this.methods[7];
        if (method == null) {
            try {
                this.methods[7] = method = NodeFactory.class.getMethod("createNotUndefinedOr", JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createConstant(Object arg0) {
        Method method = this.methods[8];
        if (method == null) {
            try {
                this.methods[8] = method = NodeFactory.class.getMethod("createConstant", Object.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createConstantBoolean(boolean arg0) {
        Method method = this.methods[9];
        if (method == null) {
            try {
                this.methods[9] = method = NodeFactory.class.getMethod("createConstantBoolean", Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createConstantInteger(int arg0) {
        Method method = this.methods[10];
        if (method == null) {
            try {
                this.methods[10] = method = NodeFactory.class.getMethod("createConstantInteger", Integer.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createConstantSafeInteger(long arg0) {
        Method method = this.methods[11];
        if (method == null) {
            try {
                this.methods[11] = method = NodeFactory.class.getMethod("createConstantSafeInteger", Long.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createConstantNumericUnit() {
        Method method = this.methods[12];
        if (method == null) {
            try {
                this.methods[12] = method = NodeFactory.class.getMethod("createConstantNumericUnit", new Class[0]);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createConstantDouble(double arg0) {
        Method method = this.methods[13];
        if (method == null) {
            try {
                this.methods[13] = method = NodeFactory.class.getMethod("createConstantDouble", Double.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createConstantString(TruffleString arg0) {
        Method method = this.methods[14];
        if (method == null) {
            try {
                this.methods[14] = method = NodeFactory.class.getMethod("createConstantString", TruffleString.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createConstantUndefined() {
        Method method = this.methods[15];
        if (method == null) {
            try {
                this.methods[15] = method = NodeFactory.class.getMethod("createConstantUndefined", new Class[0]);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createConstantNull() {
        Method method = this.methods[16];
        if (method == null) {
            try {
                this.methods[16] = method = NodeFactory.class.getMethod("createConstantNull", new Class[0]);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IfNode createIf(JavaScriptNode arg0, JavaScriptNode arg1, JavaScriptNode arg2) {
        Method method = this.methods[17];
        if (method == null) {
            try {
                this.methods[17] = method = NodeFactory.class.getMethod("createIf", JavaScriptNode.class, JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (IfNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public SwitchNode createSwitch(JavaScriptNode[] arg0, JavaScriptNode[] arg1, int[] arg2, JavaScriptNode[] arg3) {
        Method method = this.methods[18];
        if (method == null) {
            try {
                this.methods[18] = method = NodeFactory.class.getMethod("createSwitch", JavaScriptNode[].class, JavaScriptNode[].class, int[].class, JavaScriptNode[].class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (SwitchNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LoopNode createLoopNode(RepeatingNode arg0) {
        Method method = this.methods[19];
        if (method == null) {
            try {
                this.methods[19] = method = NodeFactory.class.getMethod("createLoopNode", RepeatingNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (LoopNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RepeatingNode createWhileDoRepeatingNode(JavaScriptNode arg0, JavaScriptNode arg1) {
        Method method = this.methods[20];
        if (method == null) {
            try {
                this.methods[20] = method = NodeFactory.class.getMethod("createWhileDoRepeatingNode", JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (RepeatingNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createWhileDo(LoopNode arg0) {
        Method method = this.methods[21];
        if (method == null) {
            try {
                this.methods[21] = method = NodeFactory.class.getMethod("createWhileDo", LoopNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AbstractBlockNode fixBlockNodeChild(AbstractBlockNode arg0, int arg1, JavaScriptNode arg2) {
        Method method = this.methods[22];
        if (method == null) {
            try {
                this.methods[22] = method = NodeFactory.class.getMethod("fixBlockNodeChild", AbstractBlockNode.class, Integer.TYPE, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (AbstractBlockNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public Node fixNodeChild(Node arg0, Node arg1, Node arg2) {
        Method method = this.methods[23];
        if (method == null) {
            try {
                this.methods[23] = method = NodeFactory.class.getMethod("fixNodeChild", Node.class, Node.class, Node.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (Node)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RepeatingNode createDoWhileRepeatingNode(JavaScriptNode arg0, JavaScriptNode arg1) {
        Method method = this.methods[24];
        if (method == null) {
            try {
                this.methods[24] = method = NodeFactory.class.getMethod("createDoWhileRepeatingNode", JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (RepeatingNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createDoWhile(LoopNode arg0) {
        Method method = this.methods[25];
        if (method == null) {
            try {
                this.methods[25] = method = NodeFactory.class.getMethod("createDoWhile", LoopNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createDesugaredFor(LoopNode arg0) {
        Method method = this.methods[26];
        if (method == null) {
            try {
                this.methods[26] = method = NodeFactory.class.getMethod("createDesugaredFor", LoopNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createDesugaredForOf(LoopNode arg0) {
        Method method = this.methods[27];
        if (method == null) {
            try {
                this.methods[27] = method = NodeFactory.class.getMethod("createDesugaredForOf", LoopNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createDesugaredForIn(LoopNode arg0) {
        Method method = this.methods[28];
        if (method == null) {
            try {
                this.methods[28] = method = NodeFactory.class.getMethod("createDesugaredForIn", LoopNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createDesugaredForAwaitOf(LoopNode arg0) {
        Method method = this.methods[29];
        if (method == null) {
            try {
                this.methods[29] = method = NodeFactory.class.getMethod("createDesugaredForAwaitOf", LoopNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public RepeatingNode createForRepeatingNode(JavaScriptNode arg0, JavaScriptNode arg1, JavaScriptNode arg2, FrameDescriptor arg3, JavaScriptNode arg4, JavaScriptNode arg5, JSFrameSlot arg6) {
        Method method = this.methods[30];
        if (method == null) {
            try {
                this.methods[30] = method = NodeFactory.class.getMethod("createForRepeatingNode", JavaScriptNode.class, JavaScriptNode.class, JavaScriptNode.class, FrameDescriptor.class, JavaScriptNode.class, JavaScriptNode.class, JSFrameSlot.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
        try {
            return (RepeatingNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public StatementNode createFor(LoopNode arg0) {
        Method method = this.methods[31];
        if (method == null) {
            try {
                this.methods[31] = method = NodeFactory.class.getMethod("createFor", LoopNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (StatementNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IterationScopeNode createIterationScope(FrameDescriptor arg0, JSFrameSlot arg1) {
        Method method = this.methods[32];
        if (method == null) {
            try {
                this.methods[32] = method = NodeFactory.class.getMethod("createIterationScope", FrameDescriptor.class, JSFrameSlot.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (IterationScopeNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public BreakNode createBreak(BreakTarget arg0) {
        Method method = this.methods[33];
        if (method == null) {
            try {
                this.methods[33] = method = NodeFactory.class.getMethod("createBreak", BreakTarget.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (BreakNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ContinueNode createContinue(ContinueTarget arg0) {
        Method method = this.methods[34];
        if (method == null) {
            try {
                this.methods[34] = method = NodeFactory.class.getMethod("createContinue", ContinueTarget.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (ContinueNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public LabelNode createLabel(JavaScriptNode arg0, BreakTarget arg1) {
        Method method = this.methods[35];
        if (method == null) {
            try {
                this.methods[35] = method = NodeFactory.class.getMethod("createLabel", JavaScriptNode.class, BreakTarget.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (LabelNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createEmpty() {
        Method method = this.methods[36];
        if (method == null) {
            try {
                this.methods[36] = method = NodeFactory.class.getMethod("createEmpty", new Class[0]);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createVoidBlock(JavaScriptNode ... arg0) {
        Method method = this.methods[37];
        if (method == null) {
            try {
                this.methods[37] = method = NodeFactory.class.getMethod("createVoidBlock", JavaScriptNode[].class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createExprBlock(JavaScriptNode ... arg0) {
        Method method = this.methods[38];
        if (method == null) {
            try {
                this.methods[38] = method = NodeFactory.class.getMethod("createExprBlock", JavaScriptNode[].class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReturnTargetNode createReturnTarget(JavaScriptNode arg0) {
        Method method = this.methods[39];
        if (method == null) {
            try {
                this.methods[39] = method = NodeFactory.class.getMethod("createReturnTarget", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (ReturnTargetNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReturnTargetNode createFrameReturnTarget(JavaScriptNode arg0, JavaScriptNode arg1) {
        Method method = this.methods[40];
        if (method == null) {
            try {
                this.methods[40] = method = NodeFactory.class.getMethod("createFrameReturnTarget", JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (ReturnTargetNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ContinueTargetNode createContinueTarget(JavaScriptNode arg0, ContinueTarget arg1) {
        Method method = this.methods[41];
        if (method == null) {
            try {
                this.methods[41] = method = NodeFactory.class.getMethod("createContinueTarget", JavaScriptNode.class, ContinueTarget.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (ContinueTargetNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DirectBreakTargetNode createDirectBreakTarget(JavaScriptNode arg0) {
        Method method = this.methods[42];
        if (method == null) {
            try {
                this.methods[42] = method = NodeFactory.class.getMethod("createDirectBreakTarget", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (DirectBreakTargetNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createDebugger() {
        Method method = this.methods[43];
        if (method == null) {
            try {
                this.methods[43] = method = NodeFactory.class.getMethod("createDebugger", new Class[0]);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createLocal(JSFrameSlot arg0, int arg1, int arg2) {
        Method method = this.methods[44];
        if (method == null) {
            try {
                this.methods[44] = method = NodeFactory.class.getMethod("createLocal", JSFrameSlot.class, Integer.TYPE, Integer.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createReadFrameSlot(JSFrameSlot arg0, ScopeFrameNode arg1) {
        Method method = this.methods[45];
        if (method == null) {
            try {
                this.methods[45] = method = NodeFactory.class.getMethod("createReadFrameSlot", JSFrameSlot.class, ScopeFrameNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createReadFrameSlot(JSFrameSlot arg0, ScopeFrameNode arg1, boolean arg2) {
        Method method = this.methods[46];
        if (method == null) {
            try {
                this.methods[46] = method = NodeFactory.class.getMethod("createReadFrameSlot", JSFrameSlot.class, ScopeFrameNode.class, Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createReadCurrentFrameSlot(JSFrameSlot arg0) {
        Method method = this.methods[47];
        if (method == null) {
            try {
                this.methods[47] = method = NodeFactory.class.getMethod("createReadCurrentFrameSlot", JSFrameSlot.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSWriteFrameSlotNode createWriteFrameSlot(JSFrameSlot arg0, ScopeFrameNode arg1, JavaScriptNode arg2) {
        Method method = this.methods[48];
        if (method == null) {
            try {
                this.methods[48] = method = NodeFactory.class.getMethod("createWriteFrameSlot", JSFrameSlot.class, ScopeFrameNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JSWriteFrameSlotNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSWriteFrameSlotNode createWriteFrameSlot(JSFrameSlot arg0, ScopeFrameNode arg1, JavaScriptNode arg2, boolean arg3) {
        Method method = this.methods[49];
        if (method == null) {
            try {
                this.methods[49] = method = NodeFactory.class.getMethod("createWriteFrameSlot", JSFrameSlot.class, ScopeFrameNode.class, JavaScriptNode.class, Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (JSWriteFrameSlotNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSWriteFrameSlotNode createWriteCurrentFrameSlot(JSFrameSlot arg0, JavaScriptNode arg1) {
        Method method = this.methods[50];
        if (method == null) {
            try {
                this.methods[50] = method = NodeFactory.class.getMethod("createWriteCurrentFrameSlot", JSFrameSlot.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JSWriteFrameSlotNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ScopeFrameNode createScopeFrame(int arg0, int arg1, JSFrameSlot arg2) {
        Method method = this.methods[51];
        if (method == null) {
            try {
                this.methods[51] = method = NodeFactory.class.getMethod("createScopeFrame", Integer.TYPE, Integer.TYPE, JSFrameSlot.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (ScopeFrameNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createReadLexicalGlobal(TruffleString arg0, boolean arg1, JSContext arg2) {
        Method method = this.methods[52];
        if (method == null) {
            try {
                this.methods[52] = method = NodeFactory.class.getMethod("createReadLexicalGlobal", TruffleString.class, Boolean.TYPE, JSContext.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createGlobalScope(JSContext arg0) {
        Method method = this.methods[53];
        if (method == null) {
            try {
                this.methods[53] = method = NodeFactory.class.getMethod("createGlobalScope", JSContext.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createGlobalScopeTDZCheck(JSContext arg0, TruffleString arg1, boolean arg2) {
        Method method = this.methods[54];
        if (method == null) {
            try {
                this.methods[54] = method = NodeFactory.class.getMethod("createGlobalScopeTDZCheck", JSContext.class, TruffleString.class, Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createGlobalVarWrapper(TruffleString arg0, JavaScriptNode arg1, JavaScriptNode arg2, JSTargetableNode arg3) {
        Method method = this.methods[55];
        if (method == null) {
            try {
                this.methods[55] = method = NodeFactory.class.getMethod("createGlobalVarWrapper", TruffleString.class, JavaScriptNode.class, JavaScriptNode.class, JSTargetableNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createClearFrameSlots(ScopeFrameNode arg0, int[] arg1) {
        Method method = this.methods[56];
        if (method == null) {
            try {
                this.methods[56] = method = NodeFactory.class.getMethod("createClearFrameSlots", ScopeFrameNode.class, int[].class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createClearFrameSlotRange(ScopeFrameNode arg0, int arg1, int arg2) {
        Method method = this.methods[57];
        if (method == null) {
            try {
                this.methods[57] = method = NodeFactory.class.getMethod("createClearFrameSlotRange", ScopeFrameNode.class, Integer.TYPE, Integer.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createThrow(JSContext arg0, JavaScriptNode arg1) {
        Method method = this.methods[58];
        if (method == null) {
            try {
                this.methods[58] = method = NodeFactory.class.getMethod("createThrow", JSContext.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createTryCatch(JSContext arg0, JavaScriptNode arg1, JavaScriptNode arg2, JavaScriptNode arg3, BlockScopeNode arg4, JavaScriptNode arg5, JavaScriptNode arg6) {
        Method method = this.methods[59];
        if (method == null) {
            try {
                this.methods[59] = method = NodeFactory.class.getMethod("createTryCatch", JSContext.class, JavaScriptNode.class, JavaScriptNode.class, JavaScriptNode.class, BlockScopeNode.class, JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createTryFinally(JavaScriptNode arg0, JavaScriptNode arg1) {
        Method method = this.methods[60];
        if (method == null) {
            try {
                this.methods[60] = method = NodeFactory.class.getMethod("createTryFinally", JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createFunctionCall(JSContext arg0, JavaScriptNode arg1, JavaScriptNode[] arg2) {
        Method method = this.methods[61];
        if (method == null) {
            try {
                this.methods[61] = method = NodeFactory.class.getMethod("createFunctionCall", JSContext.class, JavaScriptNode.class, JavaScriptNode[].class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createFunctionCallWithNewTarget(JSContext arg0, JavaScriptNode arg1, JavaScriptNode[] arg2) {
        Method method = this.methods[62];
        if (method == null) {
            try {
                this.methods[62] = method = NodeFactory.class.getMethod("createFunctionCallWithNewTarget", JSContext.class, JavaScriptNode.class, JavaScriptNode[].class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public AbstractFunctionArgumentsNode createFunctionArguments(JSContext arg0, JavaScriptNode[] arg1) {
        Method method = this.methods[63];
        if (method == null) {
            try {
                this.methods[63] = method = NodeFactory.class.getMethod("createFunctionArguments", JSContext.class, JavaScriptNode[].class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (AbstractFunctionArgumentsNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createNew(JSContext arg0, JavaScriptNode arg1, AbstractFunctionArgumentsNode arg2) {
        Method method = this.methods[64];
        if (method == null) {
            try {
                this.methods[64] = method = NodeFactory.class.getMethod("createNew", JSContext.class, JavaScriptNode.class, AbstractFunctionArgumentsNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createAccessThis() {
        Method method = this.methods[65];
        if (method == null) {
            try {
                this.methods[65] = method = NodeFactory.class.getMethod("createAccessThis", new Class[0]);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createAccessCallee(int arg0) {
        Method method = this.methods[66];
        if (method == null) {
            try {
                this.methods[66] = method = NodeFactory.class.getMethod("createAccessCallee", Integer.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createAccessLexicalThis() {
        Method method = this.methods[67];
        if (method == null) {
            try {
                this.methods[67] = method = NodeFactory.class.getMethod("createAccessLexicalThis", new Class[0]);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createAccessArgument(int arg0) {
        Method method = this.methods[68];
        if (method == null) {
            try {
                this.methods[68] = method = NodeFactory.class.getMethod("createAccessArgument", Integer.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createAccessVarArgs(int arg0) {
        Method method = this.methods[69];
        if (method == null) {
            try {
                this.methods[69] = method = NodeFactory.class.getMethod("createAccessVarArgs", Integer.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createAccessRestArgument(JSContext arg0, int arg1) {
        Method method = this.methods[70];
        if (method == null) {
            try {
                this.methods[70] = method = NodeFactory.class.getMethod("createAccessRestArgument", JSContext.class, Integer.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createAccessNewTarget() {
        Method method = this.methods[71];
        if (method == null) {
            try {
                this.methods[71] = method = NodeFactory.class.getMethod("createAccessNewTarget", new Class[0]);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createAccessFrameArgument(ScopeFrameNode arg0, int arg1) {
        Method method = this.methods[72];
        if (method == null) {
            try {
                this.methods[72] = method = NodeFactory.class.getMethod("createAccessFrameArgument", ScopeFrameNode.class, Integer.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createAccessHomeObject(JSContext arg0) {
        Method method = this.methods[73];
        if (method == null) {
            try {
                this.methods[73] = method = NodeFactory.class.getMethod("createAccessHomeObject", JSContext.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReadElementNode createReadElementNode(JSContext arg0, JavaScriptNode arg1, JavaScriptNode arg2) {
        Method method = this.methods[74];
        if (method == null) {
            try {
                this.methods[74] = method = NodeFactory.class.getMethod("createReadElementNode", JSContext.class, JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (ReadElementNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WriteElementNode createWriteElementNode(JavaScriptNode arg0, JavaScriptNode arg1, JavaScriptNode arg2, JSContext arg3, boolean arg4) {
        Method method = this.methods[75];
        if (method == null) {
            try {
                this.methods[75] = method = NodeFactory.class.getMethod("createWriteElementNode", JavaScriptNode.class, JavaScriptNode.class, JavaScriptNode.class, JSContext.class, Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
        try {
            return (WriteElementNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WriteElementNode createCompoundWriteElementNode(JavaScriptNode arg0, JavaScriptNode arg1, JavaScriptNode arg2, JSWriteFrameSlotNode arg3, JSContext arg4, boolean arg5) {
        Method method = this.methods[76];
        if (method == null) {
            try {
                this.methods[76] = method = NodeFactory.class.getMethod("createCompoundWriteElementNode", JavaScriptNode.class, JavaScriptNode.class, JavaScriptNode.class, JSWriteFrameSlotNode.class, JSContext.class, Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
        try {
            return (WriteElementNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSTargetableNode createReadProperty(JSContext arg0, JavaScriptNode arg1, TruffleString arg2) {
        Method method = this.methods[77];
        if (method == null) {
            try {
                this.methods[77] = method = NodeFactory.class.getMethod("createReadProperty", JSContext.class, JavaScriptNode.class, TruffleString.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JSTargetableNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSTargetableNode createReadProperty(JSContext arg0, JavaScriptNode arg1, TruffleString arg2, boolean arg3) {
        Method method = this.methods[78];
        if (method == null) {
            try {
                this.methods[78] = method = NodeFactory.class.getMethod("createReadProperty", JSContext.class, JavaScriptNode.class, TruffleString.class, Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (JSTargetableNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WritePropertyNode createWriteProperty(JavaScriptNode arg0, TruffleString arg1, JavaScriptNode arg2, JSContext arg3, boolean arg4) {
        Method method = this.methods[79];
        if (method == null) {
            try {
                this.methods[79] = method = NodeFactory.class.getMethod("createWriteProperty", JavaScriptNode.class, TruffleString.class, JavaScriptNode.class, JSContext.class, Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
        try {
            return (WritePropertyNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public WritePropertyNode createWriteProperty(JavaScriptNode arg0, TruffleString arg1, JavaScriptNode arg2, JSContext arg3, boolean arg4, boolean arg5, boolean arg6) {
        Method method = this.methods[80];
        if (method == null) {
            try {
                this.methods[80] = method = NodeFactory.class.getMethod("createWriteProperty", JavaScriptNode.class, TruffleString.class, JavaScriptNode.class, JSContext.class, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
        try {
            return (WritePropertyNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ConstantVariableWriteNode createWriteConstantVariable(JavaScriptNode arg0, boolean arg1) {
        Method method = this.methods[81];
        if (method == null) {
            try {
                this.methods[81] = method = NodeFactory.class.getMethod("createWriteConstantVariable", JavaScriptNode.class, Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (ConstantVariableWriteNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSTargetableNode createReadGlobalProperty(JSContext arg0, TruffleString arg1) {
        Method method = this.methods[82];
        if (method == null) {
            try {
                this.methods[82] = method = NodeFactory.class.getMethod("createReadGlobalProperty", JSContext.class, TruffleString.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JSTargetableNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSTargetableNode createDeleteProperty(JavaScriptNode arg0, JavaScriptNode arg1, boolean arg2, JSContext arg3) {
        Method method = this.methods[83];
        if (method == null) {
            try {
                this.methods[83] = method = NodeFactory.class.getMethod("createDeleteProperty", JavaScriptNode.class, JavaScriptNode.class, Boolean.TYPE, JSContext.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (JSTargetableNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FunctionRootNode createFunctionRootNode(AbstractBodyNode arg0, FrameDescriptor arg1, JSFunctionData arg2, SourceSection arg3, TruffleString arg4) {
        Method method = this.methods[84];
        if (method == null) {
            try {
                this.methods[84] = method = NodeFactory.class.getMethod("createFunctionRootNode", AbstractBodyNode.class, FrameDescriptor.class, JSFunctionData.class, SourceSection.class, TruffleString.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
        try {
            return (FunctionRootNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FunctionRootNode createModuleRootNode(AbstractBodyNode arg0, AbstractBodyNode arg1, FrameDescriptor arg2, JSFunctionData arg3, SourceSection arg4, TruffleString arg5) {
        Method method = this.methods[85];
        if (method == null) {
            try {
                this.methods[85] = method = NodeFactory.class.getMethod("createModuleRootNode", AbstractBodyNode.class, AbstractBodyNode.class, FrameDescriptor.class, JSFunctionData.class, SourceSection.class, TruffleString.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
        try {
            return (FunctionRootNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ConstructorRootNode createConstructorRootNode(JSFunctionData arg0, CallTarget arg1, boolean arg2) {
        Method method = this.methods[86];
        if (method == null) {
            try {
                this.methods[86] = method = NodeFactory.class.getMethod("createConstructorRootNode", JSFunctionData.class, CallTarget.class, Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (ConstructorRootNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public FunctionBodyNode createFunctionBody(JavaScriptNode arg0) {
        Method method = this.methods[87];
        if (method == null) {
            try {
                this.methods[87] = method = NodeFactory.class.getMethod("createFunctionBody", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (FunctionBodyNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSFunctionExpressionNode createFunctionExpression(JSFunctionData arg0, FunctionRootNode arg1, JSFrameSlot arg2) {
        Method method = this.methods[88];
        if (method == null) {
            try {
                this.methods[88] = method = NodeFactory.class.getMethod("createFunctionExpression", JSFunctionData.class, FunctionRootNode.class, JSFrameSlot.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JSFunctionExpressionNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSFunctionExpressionNode createFunctionExpressionLexicalThis(JSFunctionData arg0, FunctionRootNode arg1, JSFrameSlot arg2, JavaScriptNode arg3) {
        Method method = this.methods[89];
        if (method == null) {
            try {
                this.methods[89] = method = NodeFactory.class.getMethod("createFunctionExpressionLexicalThis", JSFunctionData.class, FunctionRootNode.class, JSFrameSlot.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (JSFunctionExpressionNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createPrepareThisBinding(JSContext arg0, JavaScriptNode arg1) {
        Method method = this.methods[90];
        if (method == null) {
            try {
                this.methods[90] = method = NodeFactory.class.getMethod("createPrepareThisBinding", JSContext.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createGlobalObject() {
        Method method = this.methods[91];
        if (method == null) {
            try {
                this.methods[91] = method = NodeFactory.class.getMethod("createGlobalObject", new Class[0]);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createArgumentsObjectNode(JSContext arg0, boolean arg1, int arg2) {
        Method method = this.methods[92];
        if (method == null) {
            try {
                this.methods[92] = method = NodeFactory.class.getMethod("createArgumentsObjectNode", JSContext.class, Boolean.TYPE, Integer.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createThrowError(JSErrorType arg0, TruffleString arg1) {
        Method method = this.methods[93];
        if (method == null) {
            try {
                this.methods[93] = method = NodeFactory.class.getMethod("createThrowError", JSErrorType.class, TruffleString.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createObjectLiteral(JSContext arg0, ArrayList<ObjectLiteralNode.ObjectLiteralMemberNode> arg1) {
        Method method = this.methods[94];
        if (method == null) {
            try {
                this.methods[94] = method = NodeFactory.class.getMethod("createObjectLiteral", JSContext.class, ArrayList.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createArrayLiteral(JSContext arg0, JavaScriptNode[] arg1) {
        Method method = this.methods[95];
        if (method == null) {
            try {
                this.methods[95] = method = NodeFactory.class.getMethod("createArrayLiteral", JSContext.class, JavaScriptNode[].class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createArrayLiteralWithSpread(JSContext arg0, JavaScriptNode[] arg1) {
        Method method = this.methods[96];
        if (method == null) {
            try {
                this.methods[96] = method = NodeFactory.class.getMethod("createArrayLiteralWithSpread", JSContext.class, JavaScriptNode[].class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectLiteralNode.ObjectLiteralMemberNode createAccessorMember(TruffleString arg0, boolean arg1, boolean arg2, JavaScriptNode arg3, JavaScriptNode arg4) {
        Method method = this.methods[97];
        if (method == null) {
            try {
                this.methods[97] = method = NodeFactory.class.getMethod("createAccessorMember", TruffleString.class, Boolean.TYPE, Boolean.TYPE, JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
        try {
            return (ObjectLiteralNode.ObjectLiteralMemberNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectLiteralNode.ObjectLiteralMemberNode createDataMember(TruffleString arg0, boolean arg1, boolean arg2, JavaScriptNode arg3, boolean arg4) {
        Method method = this.methods[98];
        if (method == null) {
            try {
                this.methods[98] = method = NodeFactory.class.getMethod("createDataMember", TruffleString.class, Boolean.TYPE, Boolean.TYPE, JavaScriptNode.class, Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
        try {
            return (ObjectLiteralNode.ObjectLiteralMemberNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectLiteralNode.ObjectLiteralMemberNode createAutoAccessor(TruffleString arg0, boolean arg1, boolean arg2, JavaScriptNode arg3) {
        Method method = this.methods[99];
        if (method == null) {
            try {
                this.methods[99] = method = NodeFactory.class.getMethod("createAutoAccessor", TruffleString.class, Boolean.TYPE, Boolean.TYPE, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (ObjectLiteralNode.ObjectLiteralMemberNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectLiteralNode.ObjectLiteralMemberNode createComputedAutoAccessor(JavaScriptNode arg0, boolean arg1, boolean arg2, JavaScriptNode arg3) {
        Method method = this.methods[100];
        if (method == null) {
            try {
                this.methods[100] = method = NodeFactory.class.getMethod("createComputedAutoAccessor", JavaScriptNode.class, Boolean.TYPE, Boolean.TYPE, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (ObjectLiteralNode.ObjectLiteralMemberNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectLiteralNode.ObjectLiteralMemberNode createProtoMember(TruffleString arg0, boolean arg1, JavaScriptNode arg2) {
        Method method = this.methods[101];
        if (method == null) {
            try {
                this.methods[101] = method = NodeFactory.class.getMethod("createProtoMember", TruffleString.class, Boolean.TYPE, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (ObjectLiteralNode.ObjectLiteralMemberNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectLiteralNode.ObjectLiteralMemberNode createComputedDataMember(JavaScriptNode arg0, boolean arg1, boolean arg2, JavaScriptNode arg3, boolean arg4, boolean arg5) {
        Method method = this.methods[102];
        if (method == null) {
            try {
                this.methods[102] = method = NodeFactory.class.getMethod("createComputedDataMember", JavaScriptNode.class, Boolean.TYPE, Boolean.TYPE, JavaScriptNode.class, Boolean.TYPE, Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
        try {
            return (ObjectLiteralNode.ObjectLiteralMemberNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectLiteralNode.ObjectLiteralMemberNode createComputedAccessorMember(JavaScriptNode arg0, boolean arg1, boolean arg2, JavaScriptNode arg3, JavaScriptNode arg4) {
        Method method = this.methods[103];
        if (method == null) {
            try {
                this.methods[103] = method = NodeFactory.class.getMethod("createComputedAccessorMember", JavaScriptNode.class, Boolean.TYPE, Boolean.TYPE, JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
        try {
            return (ObjectLiteralNode.ObjectLiteralMemberNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectLiteralNode.ObjectLiteralMemberNode createSpreadObjectMember(boolean arg0, JavaScriptNode arg1) {
        Method method = this.methods[104];
        if (method == null) {
            try {
                this.methods[104] = method = NodeFactory.class.getMethod("createSpreadObjectMember", Boolean.TYPE, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (ObjectLiteralNode.ObjectLiteralMemberNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectLiteralNode.ObjectLiteralMemberNode createStaticBlockMember(JavaScriptNode arg0) {
        Method method = this.methods[105];
        if (method == null) {
            try {
                this.methods[105] = method = NodeFactory.class.getMethod("createStaticBlockMember", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (ObjectLiteralNode.ObjectLiteralMemberNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createClassDefinition(JSContext arg0, JSFunctionExpressionNode arg1, JavaScriptNode arg2, ObjectLiteralNode.ObjectLiteralMemberNode[] arg3, JSWriteFrameSlotNode arg4, JSWriteFrameSlotNode arg5, JavaScriptNode[] arg6, DecoratorListEvaluationNode[] arg7, TruffleString arg8, int arg9, int arg10, boolean arg11, JSFrameSlot arg12) {
        Method method = this.methods[106];
        if (method == null) {
            try {
                this.methods[106] = method = NodeFactory.class.getMethod("createClassDefinition", JSContext.class, JSFunctionExpressionNode.class, JavaScriptNode.class, ObjectLiteralNode.ObjectLiteralMemberNode[].class, JSWriteFrameSlotNode.class, JSWriteFrameSlotNode.class, JavaScriptNode[].class, DecoratorListEvaluationNode[].class, TruffleString.class, Integer.TYPE, Integer.TYPE, Boolean.TYPE, JSFrameSlot.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createMakeMethod(JSContext arg0, JavaScriptNode arg1) {
        Method method = this.methods[107];
        if (method == null) {
            try {
                this.methods[107] = method = NodeFactory.class.getMethod("createMakeMethod", JSContext.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createSpreadArgument(JSContext arg0, GetIteratorNode arg1) {
        Method method = this.methods[108];
        if (method == null) {
            try {
                this.methods[108] = method = NodeFactory.class.getMethod("createSpreadArgument", JSContext.class, GetIteratorNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createSpreadArray(JSContext arg0, GetIteratorNode arg1) {
        Method method = this.methods[109];
        if (method == null) {
            try {
                this.methods[109] = method = NodeFactory.class.getMethod("createSpreadArray", JSContext.class, GetIteratorNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReturnNode createReturn(JavaScriptNode arg0) {
        Method method = this.methods[110];
        if (method == null) {
            try {
                this.methods[110] = method = NodeFactory.class.getMethod("createReturn", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (ReturnNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReturnNode createFrameReturn(JavaScriptNode arg0) {
        Method method = this.methods[111];
        if (method == null) {
            try {
                this.methods[111] = method = NodeFactory.class.getMethod("createFrameReturn", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (ReturnNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ReturnNode createTerminalPositionReturn(JavaScriptNode arg0) {
        Method method = this.methods[112];
        if (method == null) {
            try {
                this.methods[112] = method = NodeFactory.class.getMethod("createTerminalPositionReturn", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (ReturnNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSFunctionData createFunctionData(JSContext arg0, int arg1, TruffleString arg2, boolean arg3, boolean arg4, boolean arg5, boolean arg6, boolean arg7, boolean arg8, boolean arg9, boolean arg10, boolean arg11, boolean arg12) {
        Method method = this.methods[113];
        if (method == null) {
            try {
                this.methods[113] = method = NodeFactory.class.getMethod("createFunctionData", JSContext.class, Integer.TYPE, TruffleString.class, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9, arg10, arg11, arg12};
        try {
            return (JSFunctionData)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createAwait(JSContext arg0, JSFrameSlot arg1, JavaScriptNode arg2, JSReadFrameSlotNode arg3, JSReadFrameSlotNode arg4) {
        Method method = this.methods[114];
        if (method == null) {
            try {
                this.methods[114] = method = NodeFactory.class.getMethod("createAwait", JSContext.class, JSFrameSlot.class, JavaScriptNode.class, JSReadFrameSlotNode.class, JSReadFrameSlotNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createYield(JSContext arg0, JSFrameSlot arg1, JavaScriptNode arg2, JavaScriptNode arg3, boolean arg4, ReturnNode arg5, JSWriteFrameSlotNode arg6) {
        Method method = this.methods[115];
        if (method == null) {
            try {
                this.methods[115] = method = NodeFactory.class.getMethod("createYield", JSContext.class, JSFrameSlot.class, JavaScriptNode.class, JavaScriptNode.class, Boolean.TYPE, ReturnNode.class, JSWriteFrameSlotNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createAsyncGeneratorYield(JSContext arg0, JSFrameSlot arg1, JavaScriptNode arg2, JSReadFrameSlotNode arg3, JSReadFrameSlotNode arg4, ReturnNode arg5) {
        Method method = this.methods[116];
        if (method == null) {
            try {
                this.methods[116] = method = NodeFactory.class.getMethod("createAsyncGeneratorYield", JSContext.class, JSFrameSlot.class, JavaScriptNode.class, JSReadFrameSlotNode.class, JSReadFrameSlotNode.class, ReturnNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createAsyncGeneratorYieldStar(JSContext arg0, JSFrameSlot arg1, JSFrameSlot arg2, JavaScriptNode arg3, JSReadFrameSlotNode arg4, JSReadFrameSlotNode arg5, ReturnNode arg6) {
        Method method = this.methods[117];
        if (method == null) {
            try {
                this.methods[117] = method = NodeFactory.class.getMethod("createAsyncGeneratorYieldStar", JSContext.class, JSFrameSlot.class, JSFrameSlot.class, JavaScriptNode.class, JSReadFrameSlotNode.class, JSReadFrameSlotNode.class, ReturnNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createAsyncFunctionBody(JSContext arg0, JavaScriptNode arg1, JSWriteFrameSlotNode arg2, JSReadFrameSlotNode arg3, JSWriteFrameSlotNode arg4) {
        Method method = this.methods[118];
        if (method == null) {
            try {
                this.methods[118] = method = NodeFactory.class.getMethod("createAsyncFunctionBody", JSContext.class, JavaScriptNode.class, JSWriteFrameSlotNode.class, JSReadFrameSlotNode.class, JSWriteFrameSlotNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createGeneratorBody(JSContext arg0, JavaScriptNode arg1, JSWriteFrameSlotNode arg2, JSReadFrameSlotNode arg3) {
        Method method = this.methods[119];
        if (method == null) {
            try {
                this.methods[119] = method = NodeFactory.class.getMethod("createGeneratorBody", JSContext.class, JavaScriptNode.class, JSWriteFrameSlotNode.class, JSReadFrameSlotNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createAsyncGeneratorBody(JSContext arg0, JavaScriptNode arg1, JSWriteFrameSlotNode arg2, JSReadFrameSlotNode arg3, JSWriteFrameSlotNode arg4, JSReadFrameSlotNode arg5) {
        Method method = this.methods[120];
        if (method == null) {
            try {
                this.methods[120] = method = NodeFactory.class.getMethod("createAsyncGeneratorBody", JSContext.class, JavaScriptNode.class, JSWriteFrameSlotNode.class, JSReadFrameSlotNode.class, JSWriteFrameSlotNode.class, JSReadFrameSlotNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createGeneratorWrapper(JavaScriptNode arg0, JSFrameSlot arg1) {
        Method method = this.methods[121];
        if (method == null) {
            try {
                this.methods[121] = method = NodeFactory.class.getMethod("createGeneratorWrapper", JavaScriptNode.class, JSFrameSlot.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createGeneratorVoidBlock(JavaScriptNode[] arg0, JSFrameSlot arg1) {
        Method method = this.methods[122];
        if (method == null) {
            try {
                this.methods[122] = method = NodeFactory.class.getMethod("createGeneratorVoidBlock", JavaScriptNode[].class, JSFrameSlot.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createGeneratorExprBlock(JavaScriptNode[] arg0, JSFrameSlot arg1) {
        Method method = this.methods[123];
        if (method == null) {
            try {
                this.methods[123] = method = NodeFactory.class.getMethod("createGeneratorExprBlock", JavaScriptNode[].class, JSFrameSlot.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createBlockScope(JavaScriptNode arg0, JSFrameSlot arg1, FrameDescriptor arg2, JSFrameSlot arg3, boolean arg4, boolean arg5, boolean arg6, boolean arg7, int arg8, int arg9) {
        Method method = this.methods[124];
        if (method == null) {
            try {
                this.methods[124] = method = NodeFactory.class.getMethod("createBlockScope", JavaScriptNode.class, JSFrameSlot.class, FrameDescriptor.class, JSFrameSlot.class, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Boolean.TYPE, Integer.TYPE, Integer.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5, arg6, arg7, arg8, arg9};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createVirtualBlockScope(JavaScriptNode arg0, int arg1, int arg2) {
        Method method = this.methods[125];
        if (method == null) {
            try {
                this.methods[125] = method = NodeFactory.class.getMethod("createVirtualBlockScope", JavaScriptNode.class, Integer.TYPE, Integer.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createTemplateObject(JSContext arg0, JavaScriptNode arg1, JavaScriptNode arg2) {
        Method method = this.methods[126];
        if (method == null) {
            try {
                this.methods[126] = method = NodeFactory.class.getMethod("createTemplateObject", JSContext.class, JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createToString(JavaScriptNode arg0) {
        Method method = this.methods[127];
        if (method == null) {
            try {
                this.methods[127] = method = NodeFactory.class.getMethod("createToString", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createRegExpLiteral(JSContext arg0, TruffleString arg1, TruffleString arg2) {
        Method method = this.methods[128];
        if (method == null) {
            try {
                this.methods[128] = method = NodeFactory.class.getMethod("createRegExpLiteral", JSContext.class, TruffleString.class, TruffleString.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public GetIteratorNode createGetIterator(JSContext arg0, JavaScriptNode arg1) {
        Method method = this.methods[129];
        if (method == null) {
            try {
                this.methods[129] = method = NodeFactory.class.getMethod("createGetIterator", JSContext.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (GetIteratorNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createGetAsyncIterator(JSContext arg0, JavaScriptNode arg1) {
        Method method = this.methods[130];
        if (method == null) {
            try {
                this.methods[130] = method = NodeFactory.class.getMethod("createGetAsyncIterator", JSContext.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createEnumerate(JSContext arg0, JavaScriptNode arg1, boolean arg2) {
        Method method = this.methods[131];
        if (method == null) {
            try {
                this.methods[131] = method = NodeFactory.class.getMethod("createEnumerate", JSContext.class, JavaScriptNode.class, Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createIteratorNext(JavaScriptNode arg0) {
        Method method = this.methods[132];
        if (method == null) {
            try {
                this.methods[132] = method = NodeFactory.class.getMethod("createIteratorNext", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createIteratorComplete(JSContext arg0, JavaScriptNode arg1) {
        Method method = this.methods[133];
        if (method == null) {
            try {
                this.methods[133] = method = NodeFactory.class.getMethod("createIteratorComplete", JSContext.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createIteratorGetNextValue(JSContext arg0, JavaScriptNode arg1, JavaScriptNode arg2, boolean arg3, boolean arg4) {
        Method method = this.methods[134];
        if (method == null) {
            try {
                this.methods[134] = method = NodeFactory.class.getMethod("createIteratorGetNextValue", JSContext.class, JavaScriptNode.class, JavaScriptNode.class, Boolean.TYPE, Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createIteratorSetDone(JavaScriptNode arg0, JavaScriptNode arg1) {
        Method method = this.methods[135];
        if (method == null) {
            try {
                this.methods[135] = method = NodeFactory.class.getMethod("createIteratorSetDone", JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createIteratorIsDone(JavaScriptNode arg0) {
        Method method = this.methods[136];
        if (method == null) {
            try {
                this.methods[136] = method = NodeFactory.class.getMethod("createIteratorIsDone", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createAsyncIteratorNext(JSContext arg0, JSFrameSlot arg1, JavaScriptNode arg2, JSReadFrameSlotNode arg3, JSReadFrameSlotNode arg4) {
        Method method = this.methods[137];
        if (method == null) {
            try {
                this.methods[137] = method = NodeFactory.class.getMethod("createAsyncIteratorNext", JSContext.class, JSFrameSlot.class, JavaScriptNode.class, JSReadFrameSlotNode.class, JSReadFrameSlotNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createIteratorValue(JavaScriptNode arg0) {
        Method method = this.methods[138];
        if (method == null) {
            try {
                this.methods[138] = method = NodeFactory.class.getMethod("createIteratorValue", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createAsyncIteratorCloseWrapper(JSContext arg0, JSFrameSlot arg1, JavaScriptNode arg2, JavaScriptNode arg3, JSReadFrameSlotNode arg4, JSReadFrameSlotNode arg5) {
        Method method = this.methods[139];
        if (method == null) {
            try {
                this.methods[139] = method = NodeFactory.class.getMethod("createAsyncIteratorCloseWrapper", JSContext.class, JSFrameSlot.class, JavaScriptNode.class, JavaScriptNode.class, JSReadFrameSlotNode.class, JSReadFrameSlotNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4, arg5};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createIteratorCloseIfNotDone(JSContext arg0, JavaScriptNode arg1, JavaScriptNode arg2) {
        Method method = this.methods[140];
        if (method == null) {
            try {
                this.methods[140] = method = NodeFactory.class.getMethod("createIteratorCloseIfNotDone", JSContext.class, JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IteratorToArrayNode createIteratorToArray(JSContext arg0, JavaScriptNode arg1) {
        Method method = this.methods[141];
        if (method == null) {
            try {
                this.methods[141] = method = NodeFactory.class.getMethod("createIteratorToArray", JSContext.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (IteratorToArrayNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createGetPrototype(JavaScriptNode arg0) {
        Method method = this.methods[142];
        if (method == null) {
            try {
                this.methods[142] = method = NodeFactory.class.getMethod("createGetPrototype", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSTargetableNode createSuperPropertyReference(JavaScriptNode arg0, JavaScriptNode arg1) {
        Method method = this.methods[143];
        if (method == null) {
            try {
                this.methods[143] = method = NodeFactory.class.getMethod("createSuperPropertyReference", JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JSTargetableNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSTargetableNode createTargetableWrapper(JavaScriptNode arg0, JavaScriptNode arg1) {
        Method method = this.methods[144];
        if (method == null) {
            try {
                this.methods[144] = method = NodeFactory.class.getMethod("createTargetableWrapper", JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JSTargetableNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createWith(JavaScriptNode arg0, JavaScriptNode arg1) {
        Method method = this.methods[145];
        if (method == null) {
            try {
                this.methods[145] = method = NodeFactory.class.getMethod("createWith", JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createWithVarWrapper(TruffleString arg0, JavaScriptNode arg1, JSTargetableNode arg2, JavaScriptNode arg3) {
        Method method = this.methods[146];
        if (method == null) {
            try {
                this.methods[146] = method = NodeFactory.class.getMethod("createWithVarWrapper", TruffleString.class, JavaScriptNode.class, JSTargetableNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createWithTarget(JSContext arg0, TruffleString arg1, JavaScriptNode arg2) {
        Method method = this.methods[147];
        if (method == null) {
            try {
                this.methods[147] = method = NodeFactory.class.getMethod("createWithTarget", JSContext.class, TruffleString.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptRootNode createNewTargetConstruct(JSContext arg0, CallTarget arg1) {
        Method method = this.methods[148];
        if (method == null) {
            try {
                this.methods[148] = method = NodeFactory.class.getMethod("createNewTargetConstruct", JSContext.class, CallTarget.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptRootNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptRootNode createNewTargetCall(JSContext arg0, CallTarget arg1) {
        Method method = this.methods[149];
        if (method == null) {
            try {
                this.methods[149] = method = NodeFactory.class.getMethod("createNewTargetCall", JSContext.class, CallTarget.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptRootNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptRootNode createDropNewTarget(JSContext arg0, CallTarget arg1) {
        Method method = this.methods[150];
        if (method == null) {
            try {
                this.methods[150] = method = NodeFactory.class.getMethod("createDropNewTarget", JSContext.class, CallTarget.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptRootNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptRootNode createConstructorRequiresNewRoot(JSFunctionData arg0, SourceSection arg1) {
        Method method = this.methods[151];
        if (method == null) {
            try {
                this.methods[151] = method = NodeFactory.class.getMethod("createConstructorRequiresNewRoot", JSFunctionData.class, SourceSection.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptRootNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createDerivedConstructorResult(JavaScriptNode arg0, JavaScriptNode arg1) {
        Method method = this.methods[152];
        if (method == null) {
            try {
                this.methods[152] = method = NodeFactory.class.getMethod("createDerivedConstructorResult", JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createDerivedConstructorThis(JavaScriptNode arg0) {
        Method method = this.methods[153];
        if (method == null) {
            try {
                this.methods[153] = method = NodeFactory.class.getMethod("createDerivedConstructorThis", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createDefaultDerivedConstructorSuperCall(JavaScriptNode arg0) {
        Method method = this.methods[154];
        if (method == null) {
            try {
                this.methods[154] = method = NodeFactory.class.getMethod("createDefaultDerivedConstructorSuperCall", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createRequireObjectCoercible(JavaScriptNode arg0) {
        Method method = this.methods[155];
        if (method == null) {
            try {
                this.methods[155] = method = NodeFactory.class.getMethod("createRequireObjectCoercible", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSFrameDescriptor createFunctionFrameDescriptor() {
        Method method = this.methods[156];
        if (method == null) {
            try {
                this.methods[156] = method = NodeFactory.class.getMethod("createFunctionFrameDescriptor", new Class[0]);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{};
        try {
            return (JSFrameDescriptor)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JSFrameDescriptor createBlockFrameDescriptor() {
        Method method = this.methods[157];
        if (method == null) {
            try {
                this.methods[157] = method = NodeFactory.class.getMethod("createBlockFrameDescriptor", new Class[0]);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{};
        try {
            return (JSFrameDescriptor)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DeclareGlobalNode createDeclareGlobalVariable(TruffleString arg0, boolean arg1) {
        Method method = this.methods[158];
        if (method == null) {
            try {
                this.methods[158] = method = NodeFactory.class.getMethod("createDeclareGlobalVariable", TruffleString.class, Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (DeclareGlobalNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DeclareGlobalNode createDeclareGlobalFunction(TruffleString arg0, boolean arg1, JavaScriptNode arg2) {
        Method method = this.methods[159];
        if (method == null) {
            try {
                this.methods[159] = method = NodeFactory.class.getMethod("createDeclareGlobalFunction", TruffleString.class, Boolean.TYPE, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (DeclareGlobalNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public DeclareGlobalNode createDeclareGlobalLexicalVariable(TruffleString arg0, boolean arg1) {
        Method method = this.methods[160];
        if (method == null) {
            try {
                this.methods[160] = method = NodeFactory.class.getMethod("createDeclareGlobalLexicalVariable", TruffleString.class, Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (DeclareGlobalNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createGlobalDeclarationInstantiation(JSContext arg0, List<DeclareGlobalNode> arg1) {
        Method method = this.methods[161];
        if (method == null) {
            try {
                this.methods[161] = method = NodeFactory.class.getMethod("createGlobalDeclarationInstantiation", JSContext.class, List.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode copy(JavaScriptNode arg0) {
        Method method = this.methods[162];
        if (method == null) {
            try {
                this.methods[162] = method = NodeFactory.class.getMethod("copy", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createToObject(JSContext arg0, JavaScriptNode arg1) {
        Method method = this.methods[163];
        if (method == null) {
            try {
                this.methods[163] = method = NodeFactory.class.getMethod("createToObject", JSContext.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createToObjectFromWith(JSContext arg0, JavaScriptNode arg1, boolean arg2) {
        Method method = this.methods[164];
        if (method == null) {
            try {
                this.methods[164] = method = NodeFactory.class.getMethod("createToObjectFromWith", JSContext.class, JavaScriptNode.class, Boolean.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createAccessArgumentsArrayDirectly(JavaScriptNode arg0, JavaScriptNode arg1, int arg2) {
        Method method = this.methods[165];
        if (method == null) {
            try {
                this.methods[165] = method = NodeFactory.class.getMethod("createAccessArgumentsArrayDirectly", JavaScriptNode.class, JavaScriptNode.class, Integer.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createCallApplyArguments(JSFunctionCallNode arg0) {
        Method method = this.methods[166];
        if (method == null) {
            try {
                this.methods[166] = method = NodeFactory.class.getMethod("createCallApplyArguments", JSFunctionCallNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createGuardDisconnectedArgumentRead(int arg0, ReadElementNode arg1, JavaScriptNode arg2, JSFrameSlot arg3) {
        Method method = this.methods[167];
        if (method == null) {
            try {
                this.methods[167] = method = NodeFactory.class.getMethod("createGuardDisconnectedArgumentRead", Integer.TYPE, ReadElementNode.class, JavaScriptNode.class, JSFrameSlot.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createGuardDisconnectedArgumentWrite(int arg0, WriteElementNode arg1, JavaScriptNode arg2, JavaScriptNode arg3, JSFrameSlot arg4) {
        Method method = this.methods[168];
        if (method == null) {
            try {
                this.methods[168] = method = NodeFactory.class.getMethod("createGuardDisconnectedArgumentWrite", Integer.TYPE, WriteElementNode.class, JavaScriptNode.class, JavaScriptNode.class, JSFrameSlot.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createModuleBody(JavaScriptNode arg0) {
        Method method = this.methods[169];
        if (method == null) {
            try {
                this.methods[169] = method = NodeFactory.class.getMethod("createModuleBody", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createModuleInitializeEnvironment(JavaScriptNode arg0) {
        Method method = this.methods[170];
        if (method == null) {
            try {
                this.methods[170] = method = NodeFactory.class.getMethod("createModuleInitializeEnvironment", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createModuleYield() {
        Method method = this.methods[171];
        if (method == null) {
            try {
                this.methods[171] = method = NodeFactory.class.getMethod("createModuleYield", new Class[0]);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createTopLevelAsyncModuleBody(JSContext arg0, JavaScriptNode arg1, JSWriteFrameSlotNode arg2, JSWriteFrameSlotNode arg3) {
        Method method = this.methods[172];
        if (method == null) {
            try {
                this.methods[172] = method = NodeFactory.class.getMethod("createTopLevelAsyncModuleBody", JSContext.class, JavaScriptNode.class, JSWriteFrameSlotNode.class, JSWriteFrameSlotNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createImportMeta(JavaScriptNode arg0) {
        Method method = this.methods[173];
        if (method == null) {
            try {
                this.methods[173] = method = NodeFactory.class.getMethod("createImportMeta", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createResolveStarImport(JSContext arg0, JavaScriptNode arg1, Module.ModuleRequest arg2, JSWriteFrameSlotNode arg3) {
        Method method = this.methods[174];
        if (method == null) {
            try {
                this.methods[174] = method = NodeFactory.class.getMethod("createResolveStarImport", JSContext.class, JavaScriptNode.class, Module.ModuleRequest.class, JSWriteFrameSlotNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createResolveNamedImport(JSContext arg0, JavaScriptNode arg1, Module.ModuleRequest arg2, TruffleString arg3, JSWriteFrameSlotNode arg4) {
        Method method = this.methods[175];
        if (method == null) {
            try {
                this.methods[175] = method = NodeFactory.class.getMethod("createResolveNamedImport", JSContext.class, JavaScriptNode.class, Module.ModuleRequest.class, TruffleString.class, JSWriteFrameSlotNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createReadImportBinding(JavaScriptNode arg0) {
        Method method = this.methods[176];
        if (method == null) {
            try {
                this.methods[176] = method = NodeFactory.class.getMethod("createReadImportBinding", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createImportCall(JSContext arg0, JavaScriptNode arg1, JavaScriptNode arg2) {
        Method method = this.methods[177];
        if (method == null) {
            try {
                this.methods[177] = method = NodeFactory.class.getMethod("createImportCall", JSContext.class, JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createImportCall(JSContext arg0, JavaScriptNode arg1, JavaScriptNode arg2, JavaScriptNode arg3) {
        Method method = this.methods[178];
        if (method == null) {
            try {
                this.methods[178] = method = NodeFactory.class.getMethod("createImportCall", JSContext.class, JavaScriptNode.class, JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createRestObject(JSContext arg0, JavaScriptNode arg1, JavaScriptNode arg2) {
        Method method = this.methods[179];
        if (method == null) {
            try {
                this.methods[179] = method = NodeFactory.class.getMethod("createRestObject", JSContext.class, JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createInitializeInstanceElements(JSContext arg0, JavaScriptNode arg1, JavaScriptNode arg2) {
        Method method = this.methods[180];
        if (method == null) {
            try {
                this.methods[180] = method = NodeFactory.class.getMethod("createInitializeInstanceElements", JSContext.class, JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createNewPrivateName(TruffleString arg0) {
        Method method = this.methods[181];
        if (method == null) {
            try {
                this.methods[181] = method = NodeFactory.class.getMethod("createNewPrivateName", TruffleString.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createPrivateFieldGet(JSContext arg0, JavaScriptNode arg1, JavaScriptNode arg2) {
        Method method = this.methods[182];
        if (method == null) {
            try {
                this.methods[182] = method = NodeFactory.class.getMethod("createPrivateFieldGet", JSContext.class, JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createPrivateFieldSet(JSContext arg0, JavaScriptNode arg1, JavaScriptNode arg2, JavaScriptNode arg3) {
        Method method = this.methods[183];
        if (method == null) {
            try {
                this.methods[183] = method = NodeFactory.class.getMethod("createPrivateFieldSet", JSContext.class, JavaScriptNode.class, JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectLiteralNode.ObjectLiteralMemberNode createPrivateFieldMember(JavaScriptNode arg0, boolean arg1, JavaScriptNode arg2, JSWriteFrameSlotNode arg3) {
        Method method = this.methods[184];
        if (method == null) {
            try {
                this.methods[184] = method = NodeFactory.class.getMethod("createPrivateFieldMember", JavaScriptNode.class, Boolean.TYPE, JavaScriptNode.class, JSWriteFrameSlotNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (ObjectLiteralNode.ObjectLiteralMemberNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectLiteralNode.ObjectLiteralMemberNode createPrivateMethodMember(TruffleString arg0, boolean arg1, JavaScriptNode arg2, JSWriteFrameSlotNode arg3, int arg4) {
        Method method = this.methods[185];
        if (method == null) {
            try {
                this.methods[185] = method = NodeFactory.class.getMethod("createPrivateMethodMember", TruffleString.class, Boolean.TYPE, JavaScriptNode.class, JSWriteFrameSlotNode.class, Integer.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
        try {
            return (ObjectLiteralNode.ObjectLiteralMemberNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public ObjectLiteralNode.ObjectLiteralMemberNode createPrivateAccessorMember(boolean arg0, JavaScriptNode arg1, JavaScriptNode arg2, JSWriteFrameSlotNode arg3, int arg4) {
        Method method = this.methods[186];
        if (method == null) {
            try {
                this.methods[186] = method = NodeFactory.class.getMethod("createPrivateAccessorMember", Boolean.TYPE, JavaScriptNode.class, JavaScriptNode.class, JSWriteFrameSlotNode.class, Integer.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3, arg4};
        try {
            return (ObjectLiteralNode.ObjectLiteralMemberNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createPrivateBrandCheck(JavaScriptNode arg0, JavaScriptNode arg1) {
        Method method = this.methods[187];
        if (method == null) {
            try {
                this.methods[187] = method = NodeFactory.class.getMethod("createPrivateBrandCheck", JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createGetPrivateBrand(JSContext arg0, JavaScriptNode arg1) {
        Method method = this.methods[188];
        if (method == null) {
            try {
                this.methods[188] = method = NodeFactory.class.getMethod("createGetPrivateBrand", JSContext.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createToPropertyKey(JavaScriptNode arg0) {
        Method method = this.methods[189];
        if (method == null) {
            try {
                this.methods[189] = method = NodeFactory.class.getMethod("createToPropertyKey", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createOptionalChain(JavaScriptNode arg0) {
        Method method = this.methods[190];
        if (method == null) {
            try {
                this.methods[190] = method = NodeFactory.class.getMethod("createOptionalChain", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createOptionalChainShortCircuit(JavaScriptNode arg0) {
        Method method = this.methods[191];
        if (method == null) {
            try {
                this.methods[191] = method = NodeFactory.class.getMethod("createOptionalChainShortCircuit", JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createNamedEvaluation(JavaScriptNode arg0, JavaScriptNode arg1) {
        Method method = this.methods[192];
        if (method == null) {
            try {
                this.methods[192] = method = NodeFactory.class.getMethod("createNamedEvaluation", JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public IfNode copyIfWithCondition(IfNode arg0, JavaScriptNode arg1) {
        Method method = this.methods[193];
        if (method == null) {
            try {
                this.methods[193] = method = NodeFactory.class.getMethod("copyIfWithCondition", IfNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (IfNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createDebugScope(JSContext arg0, JavaScriptNode arg1) {
        Method method = this.methods[194];
        if (method == null) {
            try {
                this.methods[194] = method = NodeFactory.class.getMethod("createDebugScope", JSContext.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createDebugVarWrapper(TruffleString arg0, JavaScriptNode arg1, JavaScriptNode arg2, JSTargetableNode arg3) {
        Method method = this.methods[195];
        if (method == null) {
            try {
                this.methods[195] = method = NodeFactory.class.getMethod("createDebugVarWrapper", TruffleString.class, JavaScriptNode.class, JavaScriptNode.class, JSTargetableNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1, arg2, arg3};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public InternalSlotId createInternalSlotId(TruffleString arg0, int arg1) {
        Method method = this.methods[196];
        if (method == null) {
            try {
                this.methods[196] = method = NodeFactory.class.getMethod("createInternalSlotId", TruffleString.class, Integer.TYPE);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (InternalSlotId)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public JavaScriptNode createPrivateFieldIn(JavaScriptNode arg0, JavaScriptNode arg1) {
        Method method = this.methods[197];
        if (method == null) {
            try {
                this.methods[197] = method = NodeFactory.class.getMethod("createPrivateFieldIn", JavaScriptNode.class, JavaScriptNode.class);
            }
            catch (NoSuchMethodException e) {
                throw new AssertionError((Object)e);
            }
        }
        Object[] args = new Object[]{arg0, arg1};
        try {
            return (JavaScriptNode)this.handler.invoke(this, method, args);
        }
        catch (Error | RuntimeException e) {
            throw e;
        }
        catch (Throwable e) {
            throw new RuntimeException(e);
        }
    }
}

