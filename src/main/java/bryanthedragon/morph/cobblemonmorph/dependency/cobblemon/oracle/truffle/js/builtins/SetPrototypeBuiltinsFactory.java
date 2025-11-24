
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.builtins.SetPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSSetObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=SetPrototypeBuiltins.class)
public final class SetPrototypeBuiltinsFactory {

    @GeneratedBy(value=SetPrototypeBuiltins.CreateSetIteratorNode.class)
    public static final class CreateSetIteratorNodeGen
    extends SetPrototypeBuiltins.CreateSetIteratorNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private CreateSetIteratorNodeGen(JSContext context, JSBuiltin builtin, int iterationKind, JavaScriptNode[] arguments) {
            super(context, builtin, iterationKind);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
                JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
                return this.doSet(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                return this.doIncompatibleReceiver(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSSetObject) {
                JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doSet(arguments0Value_);
            }
            if (!JSGuards.isJSSet(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doIncompatibleReceiver(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doSet";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doIncompatibleReceiver";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static SetPrototypeBuiltins.CreateSetIteratorNode create(JSContext context, JSBuiltin builtin, int iterationKind, JavaScriptNode[] arguments) {
            return new CreateSetIteratorNodeGen(context, builtin, iterationKind, arguments);
        }
    }

    @GeneratedBy(value=SetPrototypeBuiltins.JSSetForEachNode.class)
    public static final class JSSetForEachNodeGen
    extends SetPrototypeBuiltins.JSSetForEachNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private IsCallableNode isCallable;
        @Node.Child
        private JSFunctionCallNode forEachFunction_callNode_;

        private JSSetForEachNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSSetObject) {
                    JSDynamicObject arguments1Value__;
                    JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
                    if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSDynamicObject && this.isCallable.executeBoolean(arguments1Value__ = (JSDynamicObject)arguments1Value_)) {
                        return this.forEachFunction(arguments0Value__, arguments1Value__, arguments2Value_, this.isCallable, this.forEachFunction_callNode_);
                    }
                    if ((state_0 & 2) != 0 && !this.isCallable.executeBoolean(arguments1Value_)) {
                        return SetPrototypeBuiltins.JSSetForEachNode.forEachFunctionNoFunction(arguments0Value__, arguments1Value_, arguments2Value_, this.isCallable);
                    }
                }
                if ((state_0 & 4) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                    return SetPrototypeBuiltins.JSSetForEachNode.forEachFunctionNoSet(arguments0Value_, arguments1Value_, arguments2Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSSetObject) {
                    IsCallableNode forEachFunctionNoFunction_isCallable__;
                    JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
                    if (arguments1Value instanceof JSDynamicObject) {
                        Object forEachFunction_isCallable__;
                        JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
                        boolean ForEachFunction_duplicateFound_ = false;
                        if ((state_0 & 1) != 0 && this.isCallable.executeBoolean(arguments1Value_)) {
                            ForEachFunction_duplicateFound_ = true;
                        }
                        if (!ForEachFunction_duplicateFound_ && ((IsCallableNode)(forEachFunction_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable))).executeBoolean(arguments1Value_) && (state_0 & 1) == 0) {
                            if (this.isCallable == null) {
                                IsCallableNode forEachFunction_isCallable___check = (IsCallableNode)super.insert(forEachFunction_isCallable__);
                                if (forEachFunction_isCallable___check == null) {
                                    throw new AssertionError((Object)"Specialization 'forEachFunction(JSSetObject, JSDynamicObject, Object, IsCallableNode, JSFunctionCallNode)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                                }
                                this.isCallable = forEachFunction_isCallable___check;
                            }
                            this.forEachFunction_callNode_ = super.insert(JSFunctionCallNode.createCall());
                            this.state_0_ = state_0 |= 1;
                            ForEachFunction_duplicateFound_ = true;
                        }
                        if (ForEachFunction_duplicateFound_) {
                            lock.unlock();
                            hasLock = false;
                            forEachFunction_isCallable__ = this.forEachFunction(arguments0Value_, arguments1Value_, arguments2Value, this.isCallable, this.forEachFunction_callNode_);
                            return forEachFunction_isCallable__;
                        }
                    }
                    boolean ForEachFunctionNoFunction_duplicateFound_ = false;
                    if ((state_0 & 2) != 0 && !this.isCallable.executeBoolean(arguments1Value)) {
                        ForEachFunctionNoFunction_duplicateFound_ = true;
                    }
                    if (!ForEachFunctionNoFunction_duplicateFound_ && !(forEachFunctionNoFunction_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable)).executeBoolean(arguments1Value) && (state_0 & 2) == 0) {
                        if (this.isCallable == null) {
                            IsCallableNode forEachFunctionNoFunction_isCallable___check = super.insert(forEachFunctionNoFunction_isCallable__);
                            if (forEachFunctionNoFunction_isCallable___check == null) {
                                throw new AssertionError((Object)"Specialization 'forEachFunctionNoFunction(JSSetObject, Object, Object, IsCallableNode)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isCallable = forEachFunctionNoFunction_isCallable___check;
                        }
                        this.state_0_ = state_0 |= 2;
                        ForEachFunctionNoFunction_duplicateFound_ = true;
                    }
                    if (ForEachFunctionNoFunction_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        Object object = SetPrototypeBuiltins.JSSetForEachNode.forEachFunctionNoFunction(arguments0Value_, arguments1Value, arguments2Value, this.isCallable);
                        return object;
                    }
                }
                if (!JSGuards.isJSSet(arguments0Value)) {
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Object object = SetPrototypeBuiltins.JSSetForEachNode.forEachFunctionNoSet(arguments0Value, arguments1Value, arguments2Value);
                    return object;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value);
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
            ArrayList<List<JavaScriptBaseNode>> cached;
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "forEachFunction";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<JavaScriptBaseNode>>();
                cached.add(Arrays.asList(this.isCallable, this.forEachFunction_callNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "forEachFunctionNoFunction";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.isCallable));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "forEachFunctionNoSet";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static SetPrototypeBuiltins.JSSetForEachNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSSetForEachNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=SetPrototypeBuiltins.JSSetIsDisjointFromNode.class)
    public static final class JSSetIsDisjointFromNodeGen
    extends SetPrototypeBuiltins.JSSetIsDisjointFromNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSSetIsDisjointFromNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
                    JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
                    return this.isDisjointFrom(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                    return this.notSet(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
                    JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
                    return this.isDisjointFrom(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                    return this.notSet(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            try {
                this.executeBoolean(frameValue);
                return;
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return;
            }
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSSetObject) {
                JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.isDisjointFrom(arguments0Value_, arguments1Value);
            }
            if (!JSGuards.isJSSet(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.notSet(arguments0Value, arguments1Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "isDisjointFrom";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "notSet";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static SetPrototypeBuiltins.JSSetIsDisjointFromNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSSetIsDisjointFromNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=SetPrototypeBuiltins.JSSetIsSupersetOfNode.class)
    public static final class JSSetIsSupersetOfNodeGen
    extends SetPrototypeBuiltins.JSSetIsSupersetOfNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSSetIsSupersetOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
                    JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
                    return this.isSupersetOf(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                    return this.notSet(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
                    JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
                    return this.isSupersetOf(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                    return this.notSet(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            try {
                this.executeBoolean(frameValue);
                return;
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return;
            }
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSSetObject) {
                JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.isSupersetOf(arguments0Value_, arguments1Value);
            }
            if (!JSGuards.isJSSet(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.notSet(arguments0Value, arguments1Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "isSupersetOf";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "notSet";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static SetPrototypeBuiltins.JSSetIsSupersetOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSSetIsSupersetOfNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=SetPrototypeBuiltins.JSSetIsSubsetOfNode.class)
    public static final class JSSetIsSubsetOfNodeGen
    extends SetPrototypeBuiltins.JSSetIsSubsetOfNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSSetIsSubsetOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
                    JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
                    return this.isSubsetOf(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                    return this.notSet(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
                    JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
                    return this.isSubsetOf(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                    return this.notSet(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            try {
                this.executeBoolean(frameValue);
                return;
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return;
            }
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSSetObject) {
                JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.isSubsetOf(arguments0Value_, arguments1Value);
            }
            if (!JSGuards.isJSSet(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.notSet(arguments0Value, arguments1Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "isSubsetOf";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "notSet";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static SetPrototypeBuiltins.JSSetIsSubsetOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSSetIsSubsetOfNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=SetPrototypeBuiltins.JSSetSymmetricDifferenceNode.class)
    public static final class JSSetSymmetricDifferenceNodeGen
    extends SetPrototypeBuiltins.JSSetSymmetricDifferenceNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSSetSymmetricDifferenceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
                    JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
                    return this.symmetricDifference(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                    return this.notSet(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                return this.notSet(arguments0Value_, arguments1Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            try {
                if ((state_0 & 1) == 0 && state_0 != 0) {
                    this.executeBoolean(frameValue);
                    return;
                }
                this.execute(frameValue);
                return;
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return;
            }
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSSetObject) {
                JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.symmetricDifference(arguments0Value_, arguments1Value);
            }
            if (!JSGuards.isJSSet(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.notSet(arguments0Value, arguments1Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "symmetricDifference";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "notSet";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static SetPrototypeBuiltins.JSSetSymmetricDifferenceNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSSetSymmetricDifferenceNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=SetPrototypeBuiltins.JSSetDifferenceNode.class)
    public static final class JSSetDifferenceNodeGen
    extends SetPrototypeBuiltins.JSSetDifferenceNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSSetDifferenceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
                    JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
                    return this.difference(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                    return this.notSet(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                return this.notSet(arguments0Value_, arguments1Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            try {
                if ((state_0 & 1) == 0 && state_0 != 0) {
                    this.executeBoolean(frameValue);
                    return;
                }
                this.execute(frameValue);
                return;
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return;
            }
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSSetObject) {
                JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.difference(arguments0Value_, arguments1Value);
            }
            if (!JSGuards.isJSSet(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.notSet(arguments0Value, arguments1Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "difference";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "notSet";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static SetPrototypeBuiltins.JSSetDifferenceNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSSetDifferenceNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=SetPrototypeBuiltins.JSSetIntersectionNode.class)
    public static final class JSSetIntersectionNodeGen
    extends SetPrototypeBuiltins.JSSetIntersectionNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSSetIntersectionNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
                    JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
                    return this.intersection(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                    return this.notSet(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                return this.notSet(arguments0Value_, arguments1Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            try {
                if ((state_0 & 1) == 0 && state_0 != 0) {
                    this.executeBoolean(frameValue);
                    return;
                }
                this.execute(frameValue);
                return;
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return;
            }
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSSetObject) {
                JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.intersection(arguments0Value_, arguments1Value);
            }
            if (!JSGuards.isJSSet(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.notSet(arguments0Value, arguments1Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "intersection";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "notSet";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static SetPrototypeBuiltins.JSSetIntersectionNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSSetIntersectionNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=SetPrototypeBuiltins.JSSetUnionNode.class)
    public static final class JSSetUnionNodeGen
    extends SetPrototypeBuiltins.JSSetUnionNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSSetUnionNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
                    JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
                    return this.union(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                    return this.notSet(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                return this.notSet(arguments0Value_, arguments1Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            try {
                if ((state_0 & 1) == 0 && state_0 != 0) {
                    this.executeBoolean(frameValue);
                    return;
                }
                this.execute(frameValue);
                return;
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return;
            }
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSSetObject) {
                JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.union(arguments0Value_, arguments1Value);
            }
            if (!JSGuards.isJSSet(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.notSet(arguments0Value, arguments1Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "union";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "notSet";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static SetPrototypeBuiltins.JSSetUnionNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSSetUnionNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=SetPrototypeBuiltins.JSSetHasNode.class)
    public static final class JSSetHasNodeGen
    extends SetPrototypeBuiltins.JSSetHasNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSSetHasNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
                    JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
                    return this.has(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                    return this.hasNoObject(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
                    JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
                    return this.has(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                    return this.hasNoObject(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSSetObject) {
                JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.has(arguments0Value_, arguments1Value);
            }
            if (!JSGuards.isJSSet(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.hasNoObject(arguments0Value, arguments1Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "has";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "hasNoObject";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static SetPrototypeBuiltins.JSSetHasNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSSetHasNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=SetPrototypeBuiltins.JSSetAddNode.class)
    public static final class JSSetAddNodeGen
    extends SetPrototypeBuiltins.JSSetAddNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSSetAddNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
                    JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
                    return this.add(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                    return SetPrototypeBuiltins.JSSetAddNode.notSet(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSSetObject) {
                JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.add(arguments0Value_, arguments1Value);
            }
            if (!JSGuards.isJSSet(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return SetPrototypeBuiltins.JSSetAddNode.notSet(arguments0Value, arguments1Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "add";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "notSet";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static SetPrototypeBuiltins.JSSetAddNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSSetAddNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=SetPrototypeBuiltins.JSSetDeleteNode.class)
    public static final class JSSetDeleteNodeGen
    extends SetPrototypeBuiltins.JSSetDeleteNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSSetDeleteNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
                    JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
                    return this.delete(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                    return SetPrototypeBuiltins.JSSetDeleteNode.notSet(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
                    JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
                    return this.delete(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                    return SetPrototypeBuiltins.JSSetDeleteNode.notSet(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSSetObject) {
                JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.delete(arguments0Value_, arguments1Value);
            }
            if (!JSGuards.isJSSet(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return SetPrototypeBuiltins.JSSetDeleteNode.notSet(arguments0Value, arguments1Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "delete";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "notSet";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static SetPrototypeBuiltins.JSSetDeleteNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSSetDeleteNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=SetPrototypeBuiltins.JSSetClearNode.class)
    public static final class JSSetClearNodeGen
    extends SetPrototypeBuiltins.JSSetClearNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSSetClearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
                JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
                return SetPrototypeBuiltins.JSSetClearNode.clear(arguments0Value__);
            }
            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
                return SetPrototypeBuiltins.JSSetClearNode.notSet(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSSetObject) {
                JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return SetPrototypeBuiltins.JSSetClearNode.clear(arguments0Value_);
            }
            if (!JSGuards.isJSSet(arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return SetPrototypeBuiltins.JSSetClearNode.notSet(arguments0Value);
            }
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "clear";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "notSet";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static SetPrototypeBuiltins.JSSetClearNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSSetClearNodeGen(context, builtin, arguments);
        }
    }
}

