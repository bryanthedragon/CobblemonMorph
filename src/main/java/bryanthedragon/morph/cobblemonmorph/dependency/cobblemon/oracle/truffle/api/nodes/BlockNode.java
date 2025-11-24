
package com.oracle.truffle.api.nodes;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeAccessor;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import java.util.Objects;

public abstract class BlockNode<T extends Node>
extends Node {
    public static final int NO_ARGUMENT = 0;
    @Node.Children
    private final T[] elements;

    protected BlockNode(T[] elements2) {
        this.elements = elements2;
        assert (this.getClass().getName().equals("com.oracle.truffle.api.impl.DefaultBlockNode") || this.getClass().getName().equals("org.graalvm.compiler.truffle.runtime.OptimizedBlockNode")) : "Custom block implementations are not allowed.";
    }

    public abstract void executeVoid(VirtualFrame var1, int var2);

    public abstract Object executeGeneric(VirtualFrame var1, int var2);

    public abstract byte executeByte(VirtualFrame var1, int var2) throws UnexpectedResultException;

    public abstract short executeShort(VirtualFrame var1, int var2) throws UnexpectedResultException;

    public abstract int executeInt(VirtualFrame var1, int var2) throws UnexpectedResultException;

    public abstract char executeChar(VirtualFrame var1, int var2) throws UnexpectedResultException;

    public abstract float executeFloat(VirtualFrame var1, int var2) throws UnexpectedResultException;

    public abstract double executeDouble(VirtualFrame var1, int var2) throws UnexpectedResultException;

    public abstract long executeLong(VirtualFrame var1, int var2) throws UnexpectedResultException;

    public abstract boolean executeBoolean(VirtualFrame var1, int var2) throws UnexpectedResultException;

    public final T[] getElements() {
        return this.elements;
    }

    @Override
    public final NodeCost getCost() {
        return NodeCost.NONE;
    }

    public static <T extends Node> BlockNode<T> create(T[] elements2, ElementExecutor<T> executor) {
        Objects.requireNonNull(elements2);
        Objects.requireNonNull(executor);
        if (elements2.length == 0) {
            throw new IllegalArgumentException("Empty blocks are not allowed.");
        }
        return NodeAccessor.RUNTIME.createBlockNode((Node[])elements2, executor);
    }

    public static interface ElementExecutor<T extends Node> {
        public void executeVoid(VirtualFrame var1, T var2, int var3, int var4);

        default public Object executeGeneric(VirtualFrame frame, T node, int index, int argument) {
            this.executeVoid(frame, node, index, argument);
            return null;
        }

        default public boolean executeBoolean(VirtualFrame frame, T node, int index, int argument) throws UnexpectedResultException {
            Object result = this.executeGeneric(frame, node, index, argument);
            if (result instanceof Boolean) {
                return (Boolean)result;
            }
            throw new UnexpectedResultException(result);
        }

        default public byte executeByte(VirtualFrame frame, T node, int index, int argument) throws UnexpectedResultException {
            Object result = this.executeGeneric(frame, node, index, argument);
            if (result instanceof Byte) {
                return (Byte)result;
            }
            throw new UnexpectedResultException(result);
        }

        default public short executeShort(VirtualFrame frame, T node, int index, int argument) throws UnexpectedResultException {
            Object result = this.executeGeneric(frame, node, index, argument);
            if (result instanceof Short) {
                return (Short)result;
            }
            throw new UnexpectedResultException(result);
        }

        default public char executeChar(VirtualFrame frame, T node, int index, int argument) throws UnexpectedResultException {
            Object result = this.executeGeneric(frame, node, index, argument);
            if (result instanceof Character) {
                return ((Character)result).charValue();
            }
            throw new UnexpectedResultException(result);
        }

        default public int executeInt(VirtualFrame frame, T node, int index, int argument) throws UnexpectedResultException {
            Object result = this.executeGeneric(frame, node, index, argument);
            if (result instanceof Integer) {
                return (Integer)result;
            }
            throw new UnexpectedResultException(result);
        }

        default public long executeLong(VirtualFrame frame, T node, int index, int argument) throws UnexpectedResultException {
            Object result = this.executeGeneric(frame, node, index, argument);
            if (result instanceof Long) {
                return (Long)result;
            }
            throw new UnexpectedResultException(result);
        }

        default public float executeFloat(VirtualFrame frame, T node, int index, int argument) throws UnexpectedResultException {
            Object result = this.executeGeneric(frame, node, index, argument);
            if (result instanceof Float) {
                return ((Float)result).floatValue();
            }
            throw new UnexpectedResultException(result);
        }

        default public double executeDouble(VirtualFrame frame, T node, int index, int argument) throws UnexpectedResultException {
            Object result = this.executeGeneric(frame, node, index, argument);
            if (result instanceof Double) {
                return (Double)result;
            }
            throw new UnexpectedResultException(result);
        }
    }
}

