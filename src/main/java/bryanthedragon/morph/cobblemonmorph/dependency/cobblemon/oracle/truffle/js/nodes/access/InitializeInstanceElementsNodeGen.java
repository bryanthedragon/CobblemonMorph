
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.InitializeInstanceElementsNode;
import com.oracle.truffle.js.nodes.access.PrivateFieldAddNode;
import com.oracle.truffle.js.nodes.function.ClassElementDefinitionRecord;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=InitializeInstanceElementsNode.class)
public final class InitializeInstanceElementsNodeGen
extends InitializeInstanceElementsNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @Node.Child
    private PrivateFieldAddNode privateBrandAdd;
    @Node.Children
    private InitializeInstanceElementsNode.DefineFieldNode[] withFields_fieldNodes_;
    @Node.Child
    private JSFunctionCallNode withFields_callInit_;

    private InitializeInstanceElementsNodeGen(JSContext context, JavaScriptNode targetNode, JavaScriptNode constructorNode) {
        super(context, targetNode, constructorNode);
    }

    @Override
    protected Object executeEvaluated(Object targetNodeValue, Object constructorNodeValue, ClassElementDefinitionRecord[] fieldsNodeValue, JSFunctionObject[] initializersNodeValue, Object brandNodeValue) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
                return InitializeInstanceElementsNode.withFields(targetNodeValue, constructorNodeValue, fieldsNodeValue, initializersNodeValue, brandNodeValue, this.privateBrandAdd, this.withFields_fieldNodes_, this.withFields_callInit_);
            }
            if ((state_0 & 2) != 0) {
                return InitializeInstanceElementsNode.privateBrandAdd(targetNodeValue, constructorNodeValue, fieldsNodeValue, initializersNodeValue, brandNodeValue, this.privateBrandAdd);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(targetNodeValue, constructorNodeValue, fieldsNodeValue, initializersNodeValue, brandNodeValue);
    }

    @Override
    public Object execute(VirtualFrame frameValue) {
        int state_0 = this.state_0_;
        Object targetNodeValue_ = this.targetNode.execute(frameValue);
        Object constructorNodeValue_ = this.constructorNode.execute(frameValue);
        Object fieldsNodeValue_ = this.fieldsNode.executeWithTarget(frameValue, constructorNodeValue_);
        Object initializersNodeValue_ = this.initializersNode.executeWithTarget(frameValue, constructorNodeValue_);
        Object brandNodeValue_ = this.brandNode.executeWithTarget(frameValue, constructorNodeValue_);
        if (state_0 != 0) {
            if ((state_0 & 1) != 0 && fieldsNodeValue_ instanceof ClassElementDefinitionRecord[]) {
                ClassElementDefinitionRecord[] fieldsNodeValue__ = (ClassElementDefinitionRecord[])fieldsNodeValue_;
                if (initializersNodeValue_ instanceof JSFunctionObject[]) {
                    JSFunctionObject[] initializersNodeValue__ = (JSFunctionObject[])initializersNodeValue_;
                    return InitializeInstanceElementsNode.withFields(targetNodeValue_, constructorNodeValue_, fieldsNodeValue__, initializersNodeValue__, brandNodeValue_, this.privateBrandAdd, this.withFields_fieldNodes_, this.withFields_callInit_);
                }
            }
            if ((state_0 & 2) != 0) {
                return InitializeInstanceElementsNode.privateBrandAdd(targetNodeValue_, constructorNodeValue_, fieldsNodeValue_, initializersNodeValue_, brandNodeValue_, this.privateBrandAdd);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(targetNodeValue_, constructorNodeValue_, fieldsNodeValue_, initializersNodeValue_, brandNodeValue_);
    }

    @Override
    public void executeVoid(VirtualFrame frameValue) {
        this.execute(frameValue);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(Object targetNodeValue, Object constructorNodeValue, Object fieldsNodeValue, Object initializersNodeValue, Object brandNodeValue) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            if (fieldsNodeValue instanceof ClassElementDefinitionRecord[]) {
                ClassElementDefinitionRecord[] fieldsNodeValue_ = (ClassElementDefinitionRecord[])fieldsNodeValue;
                if (initializersNodeValue instanceof JSFunctionObject[]) {
                    JSFunctionObject[] initializersNodeValue_ = (JSFunctionObject[])initializersNodeValue;
                    this.privateBrandAdd = super.insert(this.privateBrandAdd == null ? InitializeInstanceElementsNode.createBrandAddNode(brandNodeValue, this.context) : this.privateBrandAdd);
                    this.withFields_fieldNodes_ = super.insert(InitializeInstanceElementsNode.createFieldNodes(fieldsNodeValue_, this.context));
                    this.withFields_callInit_ = super.insert(JSFunctionCallNode.createCall());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = InitializeInstanceElementsNode.withFields(targetNodeValue, constructorNodeValue, fieldsNodeValue_, initializersNodeValue_, brandNodeValue, this.privateBrandAdd, this.withFields_fieldNodes_, this.withFields_callInit_);
                    return object;
                }
            }
            this.privateBrandAdd = super.insert(this.privateBrandAdd == null ? InitializeInstanceElementsNode.createBrandAddNode(brandNodeValue, this.context) : this.privateBrandAdd);
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            Object object = InitializeInstanceElementsNode.privateBrandAdd(targetNodeValue, constructorNodeValue, fieldsNodeValue, initializersNodeValue, brandNodeValue, this.privateBrandAdd);
            return object;
        }
        finally {
            if (hasLock) {
                lock.unlock();
            }
        }
    }

    @Override
    public NodeCost getCost() {
        int state_0 = this.state_0_;
        if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
        }
        if ((state_0 & state_0 - 1) == 0) {
            return NodeCost.MONOMORPHIC;
        }
        return NodeCost.POLYMORPHIC;
    }

    @Override
    public Introspection getIntrospectionData() {
        ArrayList<List<Cloneable>> cached;
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "withFields";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Cloneable>>();
            cached.add(Arrays.asList(this.privateBrandAdd, this.withFields_fieldNodes_, this.withFields_callInit_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "privateBrandAdd";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.privateBrandAdd));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static InitializeInstanceElementsNode create(JSContext context, JavaScriptNode targetNode, JavaScriptNode constructorNode) {
        return new InitializeInstanceElementsNodeGen(context, targetNode, constructorNode);
    }
}

