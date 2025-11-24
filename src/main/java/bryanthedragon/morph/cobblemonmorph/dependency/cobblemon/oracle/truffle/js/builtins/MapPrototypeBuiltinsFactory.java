
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.builtins.MapPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSMapObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=MapPrototypeBuiltins.class)
public final class MapPrototypeBuiltinsFactory {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    @GeneratedBy(value=MapPrototypeBuiltins.CreateMapIteratorNode.class)
    public static final class CreateMapIteratorNodeGen
    extends MapPrototypeBuiltins.CreateMapIteratorNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private InteropLibrary mapLib;
        @Node.Child
        private PropertySetNode foreignMap_setEnumerateIteratorNode_;

        private CreateMapIteratorNodeGen(JSContext context, JSBuiltin builtin, int iterationKind, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSMapObject) {
                JSMapObject arguments0Value__ = (JSMapObject)arguments0Value_;
                return this.doMap(arguments0Value__);
            }
            if ((state_0 & 6) != 0) {
                if ((state_0 & 2) != 0 && !JSGuards.isJSMap(arguments0Value_) && MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value_, this.mapLib)) {
                    return this.doForeignMap(arguments0Value_, this.mapLib, this.foreignMap_setEnumerateIteratorNode_);
                }
                if ((state_0 & 4) != 0 && !JSGuards.isJSMap(arguments0Value_) && !MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value_, this.mapLib)) {
                    return this.doIncompatibleReceiver(arguments0Value_, this.mapLib);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                InteropLibrary incompatibleReceiver_mapLib__;
                InteropLibrary foreignMap_mapLib__;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSMapObject) {
                    JSMapObject arguments0Value_ = (JSMapObject)arguments0Value;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.doMap(arguments0Value_);
                    return jSDynamicObject;
                }
                if (!JSGuards.isJSMap(arguments0Value) && MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value, foreignMap_mapLib__ = super.insert(this.mapLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.mapLib))) {
                    Object foreignMap_mapLib___check;
                    if (this.mapLib == null) {
                        foreignMap_mapLib___check = super.insert(foreignMap_mapLib__);
                        if (foreignMap_mapLib___check == null) {
                            throw new AssertionError((Object)"Specialization 'doForeignMap(Object, InteropLibrary, PropertySetNode)' contains a shared cache with name 'mapLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.mapLib = foreignMap_mapLib___check;
                    }
                    this.foreignMap_setEnumerateIteratorNode_ = super.insert(PropertySetNode.createSetHidden(JSRuntime.ENUMERATE_ITERATOR_ID, this.getContext()));
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    foreignMap_mapLib___check = this.doForeignMap(arguments0Value, foreignMap_mapLib__, this.foreignMap_setEnumerateIteratorNode_);
                    return foreignMap_mapLib___check;
                }
                if (!JSGuards.isJSMap(arguments0Value) && !MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value, incompatibleReceiver_mapLib__ = super.insert(this.mapLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.mapLib))) {
                    if (this.mapLib == null) {
                        InteropLibrary incompatibleReceiver_mapLib___check = super.insert(incompatibleReceiver_mapLib__);
                        if (incompatibleReceiver_mapLib___check == null) {
                            throw new AssertionError((Object)"Specialization 'doIncompatibleReceiver(Object, InteropLibrary)' contains a shared cache with name 'mapLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.mapLib = incompatibleReceiver_mapLib___check;
                    }
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.doIncompatibleReceiver(arguments0Value, incompatibleReceiver_mapLib__);
                    return jSDynamicObject;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            ArrayList<List<Node>> cached;
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doMap";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doForeignMap";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Node>>();
                cached.add(Arrays.asList(this.mapLib, this.foreignMap_setEnumerateIteratorNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "doIncompatibleReceiver";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.mapLib));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static MapPrototypeBuiltins.CreateMapIteratorNode create(JSContext context, JSBuiltin builtin, int iterationKind, JavaScriptNode[] arguments) {
            return new CreateMapIteratorNodeGen(context, builtin, iterationKind, arguments);
        }
    }

    @GeneratedBy(value=MapPrototypeBuiltins.JSMapForEachNode.class)
    public static final class JSMapForEachNodeGen
    extends MapPrototypeBuiltins.JSMapForEachNode
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
        private JSFunctionCallNode callNode;
        @Node.Child
        private InteropLibrary mapLib;
        @Node.Child
        private InteropLibrary foreignMap_iteratorLib_;
        @Node.Child
        private InteropLibrary foreignMap_entryLib_;

        private JSMapForEachNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSMapObject) {
                    JSMapObject arguments0Value__ = (JSMapObject)arguments0Value_;
                    if (this.isCallable.executeBoolean(arguments1Value_)) {
                        return this.doMap(arguments0Value__, arguments1Value_, arguments2Value_, this.isCallable, this.callNode);
                    }
                }
                if ((state_0 & 0xE) != 0) {
                    if ((state_0 & 2) != 0 && !JSGuards.isJSMap(arguments0Value_) && MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value_, this.mapLib) && this.isCallable.executeBoolean(arguments1Value_)) {
                        return this.doForeignMap(arguments0Value_, arguments1Value_, arguments2Value_, this.isCallable, this.callNode, this.mapLib, this.foreignMap_iteratorLib_, this.foreignMap_entryLib_);
                    }
                    if ((state_0 & 4) != 0 && (JSGuards.isJSMap(arguments0Value_) || MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value_, this.mapLib)) && !this.isCallable.executeBoolean(arguments1Value_)) {
                        return MapPrototypeBuiltins.JSMapForEachNode.invalidCallback(arguments0Value_, arguments1Value_, arguments2Value_, this.isCallable, this.mapLib);
                    }
                    if ((state_0 & 8) != 0 && !JSGuards.isJSMap(arguments0Value_) && !MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value_, this.mapLib)) {
                        return MapPrototypeBuiltins.JSMapForEachNode.notMap(arguments0Value_, arguments1Value_, arguments2Value_, this.mapLib);
                    }
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
                InteropLibrary notMap_mapLib__;
                Object invalidCallback_mapLib__;
                IsCallableNode foreignMap_isCallable__;
                InteropLibrary foreignMap_mapLib__2;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSMapObject) {
                    Object map_isCallable__;
                    JSMapObject arguments0Value_ = (JSMapObject)arguments0Value;
                    boolean Map_duplicateFound_ = false;
                    if ((state_0 & 1) != 0 && this.isCallable.executeBoolean(arguments1Value)) {
                        Map_duplicateFound_ = true;
                    }
                    if (!Map_duplicateFound_ && ((IsCallableNode)(map_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable))).executeBoolean(arguments1Value) && (state_0 & 1) == 0) {
                        if (this.isCallable == null) {
                            IsCallableNode map_isCallable___check = (IsCallableNode)super.insert(map_isCallable__);
                            if (map_isCallable___check == null) {
                                throw new AssertionError((Object)"Specialization 'doMap(JSMapObject, Object, Object, IsCallableNode, JSFunctionCallNode)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isCallable = map_isCallable___check;
                        }
                        this.callNode = super.insert(this.callNode == null ? JSFunctionCallNode.createCall() : this.callNode);
                        this.state_0_ = state_0 |= 1;
                        Map_duplicateFound_ = true;
                    }
                    if (Map_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        map_isCallable__ = this.doMap(arguments0Value_, arguments1Value, arguments2Value, this.isCallable, this.callNode);
                        return map_isCallable__;
                    }
                }
                boolean ForeignMap_duplicateFound_ = false;
                if ((state_0 & 2) != 0 && !JSGuards.isJSMap(arguments0Value) && MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value, this.mapLib) && this.isCallable.executeBoolean(arguments1Value)) {
                    ForeignMap_duplicateFound_ = true;
                }
                if (!ForeignMap_duplicateFound_ && !JSGuards.isJSMap(arguments0Value) && MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value, foreignMap_mapLib__2 = super.insert(this.mapLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.mapLib)) && (foreignMap_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable)).executeBoolean(arguments1Value) && (state_0 & 2) == 0) {
                    if (this.isCallable == null) {
                        IsCallableNode foreignMap_isCallable___check = super.insert(foreignMap_isCallable__);
                        if (foreignMap_isCallable___check == null) {
                            throw new AssertionError((Object)"Specialization 'doForeignMap(Object, Object, Object, IsCallableNode, JSFunctionCallNode, InteropLibrary, InteropLibrary, InteropLibrary)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.isCallable = foreignMap_isCallable___check;
                    }
                    this.callNode = super.insert(this.callNode == null ? JSFunctionCallNode.createCall() : this.callNode);
                    if (this.mapLib == null) {
                        InteropLibrary foreignMap_mapLib___check = super.insert(foreignMap_mapLib__2);
                        if (foreignMap_mapLib___check == null) {
                            throw new AssertionError((Object)"Specialization 'doForeignMap(Object, Object, Object, IsCallableNode, JSFunctionCallNode, InteropLibrary, InteropLibrary, InteropLibrary)' contains a shared cache with name 'mapLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.mapLib = foreignMap_mapLib___check;
                    }
                    this.foreignMap_iteratorLib_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.foreignMap_entryLib_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.state_0_ = state_0 |= 2;
                    ForeignMap_duplicateFound_ = true;
                }
                if (ForeignMap_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    Object foreignMap_mapLib__2 = this.doForeignMap(arguments0Value, arguments1Value, arguments2Value, this.isCallable, this.callNode, this.mapLib, this.foreignMap_iteratorLib_, this.foreignMap_entryLib_);
                    return foreignMap_mapLib__2;
                }
                boolean InvalidCallback_duplicateFound_ = false;
                if ((state_0 & 4) != 0 && (JSGuards.isJSMap(arguments0Value) || MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value, this.mapLib)) && !this.isCallable.executeBoolean(arguments1Value)) {
                    InvalidCallback_duplicateFound_ = true;
                }
                if (!InvalidCallback_duplicateFound_) {
                    IsCallableNode invalidCallback_isCallable__;
                    invalidCallback_mapLib__ = super.insert(this.mapLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.mapLib);
                    if ((JSGuards.isJSMap(arguments0Value) || MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value, (InteropLibrary)invalidCallback_mapLib__)) && !(invalidCallback_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable)).executeBoolean(arguments1Value) && (state_0 & 4) == 0) {
                        if (this.isCallable == null) {
                            IsCallableNode invalidCallback_isCallable___check = super.insert(invalidCallback_isCallable__);
                            if (invalidCallback_isCallable___check == null) {
                                throw new AssertionError((Object)"Specialization 'invalidCallback(Object, Object, Object, IsCallableNode, InteropLibrary)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.isCallable = invalidCallback_isCallable___check;
                        }
                        if (this.mapLib == null) {
                            InteropLibrary invalidCallback_mapLib___check = (InteropLibrary)super.insert(invalidCallback_mapLib__);
                            if (invalidCallback_mapLib___check == null) {
                                throw new AssertionError((Object)"Specialization 'invalidCallback(Object, Object, Object, IsCallableNode, InteropLibrary)' contains a shared cache with name 'mapLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.mapLib = invalidCallback_mapLib___check;
                        }
                        this.state_0_ = state_0 |= 4;
                        InvalidCallback_duplicateFound_ = true;
                    }
                }
                if (InvalidCallback_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    invalidCallback_mapLib__ = MapPrototypeBuiltins.JSMapForEachNode.invalidCallback(arguments0Value, arguments1Value, arguments2Value, this.isCallable, this.mapLib);
                    return invalidCallback_mapLib__;
                }
                if (!JSGuards.isJSMap(arguments0Value) && !MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value, notMap_mapLib__ = super.insert(this.mapLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.mapLib))) {
                    if (this.mapLib == null) {
                        InteropLibrary notMap_mapLib___check = super.insert(notMap_mapLib__);
                        if (notMap_mapLib___check == null) {
                            throw new AssertionError((Object)"Specialization 'notMap(Object, Object, Object, InteropLibrary)' contains a shared cache with name 'mapLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.mapLib = notMap_mapLib___check;
                    }
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    Object object = MapPrototypeBuiltins.JSMapForEachNode.notMap(arguments0Value, arguments1Value, arguments2Value, notMap_mapLib__);
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
            ArrayList<List<Node>> cached;
            Object[] data = new Object[5];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doMap";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Node>>();
                cached.add(Arrays.asList(this.isCallable, this.callNode));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doForeignMap";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.isCallable, this.callNode, this.mapLib, this.foreignMap_iteratorLib_, this.foreignMap_entryLib_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "invalidCallback";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.isCallable, this.mapLib));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "notMap";
            if ((state_0 & 8) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.mapLib));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        public static MapPrototypeBuiltins.JSMapForEachNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSMapForEachNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=MapPrototypeBuiltins.JSMapHasNode.class)
    public static final class JSMapHasNodeGen
    extends MapPrototypeBuiltins.JSMapHasNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private InteropLibrary mapLib;

        private JSMapHasNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSMapObject) {
                    JSMapObject arguments0Value__ = (JSMapObject)arguments0Value_;
                    return this.doMap(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 6) != 0) {
                    if ((state_0 & 2) != 0 && !JSGuards.isJSMap(arguments0Value_) && MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value_, this.mapLib)) {
                        return this.doForeignMap(arguments0Value_, arguments1Value_, this.mapLib);
                    }
                    if ((state_0 & 4) != 0 && !JSGuards.isJSMap(arguments0Value_) && !MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value_, this.mapLib)) {
                        return MapPrototypeBuiltins.JSMapHasNode.notMap(arguments0Value_, arguments1Value_, this.mapLib);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            if ((state_0 & 2) != 0) {
                return JSTypesGen.expectBoolean(this.execute(frameValue));
            }
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 5) != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSMapObject) {
                    JSMapObject arguments0Value__ = (JSMapObject)arguments0Value_;
                    return this.doMap(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 4) != 0 && !JSGuards.isJSMap(arguments0Value_) && !MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value_, this.mapLib)) {
                    return MapPrototypeBuiltins.JSMapHasNode.notMap(arguments0Value_, arguments1Value_, this.mapLib);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            try {
                if ((state_0 & 2) == 0 && state_0 != 0) {
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
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                InteropLibrary notMap_mapLib__;
                InteropLibrary foreignMap_mapLib__;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSMapObject) {
                    JSMapObject arguments0Value_ = (JSMapObject)arguments0Value;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Boolean bl = this.doMap(arguments0Value_, arguments1Value);
                    return bl;
                }
                if (!JSGuards.isJSMap(arguments0Value) && MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value, foreignMap_mapLib__ = super.insert(this.mapLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.mapLib))) {
                    Object foreignMap_mapLib___check;
                    if (this.mapLib == null) {
                        foreignMap_mapLib___check = super.insert(foreignMap_mapLib__);
                        if (foreignMap_mapLib___check == null) {
                            throw new AssertionError((Object)"Specialization 'doForeignMap(Object, Object, InteropLibrary)' contains a shared cache with name 'mapLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.mapLib = foreignMap_mapLib___check;
                    }
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    foreignMap_mapLib___check = this.doForeignMap(arguments0Value, arguments1Value, foreignMap_mapLib__);
                    return foreignMap_mapLib___check;
                }
                if (!JSGuards.isJSMap(arguments0Value) && !MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value, notMap_mapLib__ = super.insert(this.mapLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.mapLib))) {
                    if (this.mapLib == null) {
                        InteropLibrary notMap_mapLib___check = super.insert(notMap_mapLib__);
                        if (notMap_mapLib___check == null) {
                            throw new AssertionError((Object)"Specialization 'notMap(Object, Object, InteropLibrary)' contains a shared cache with name 'mapLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.mapLib = notMap_mapLib___check;
                    }
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Boolean bl = MapPrototypeBuiltins.JSMapHasNode.notMap(arguments0Value, arguments1Value, notMap_mapLib__);
                    return bl;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            ArrayList<List<InteropLibrary>> cached;
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doMap";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doForeignMap";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<InteropLibrary>>();
                cached.add(Arrays.asList(this.mapLib));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "notMap";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.mapLib));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static MapPrototypeBuiltins.JSMapHasNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSMapHasNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=MapPrototypeBuiltins.JSMapSetNode.class)
    public static final class JSMapSetNodeGen
    extends MapPrototypeBuiltins.JSMapSetNode
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
        private InteropLibrary mapLib;
        @Node.Child
        private ExportValueNode foreignMap_exportValueNode_;

        private JSMapSetNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSMapObject) {
                    JSMapObject arguments0Value__ = (JSMapObject)arguments0Value_;
                    return this.doMap(arguments0Value__, arguments1Value_, arguments2Value_);
                }
                if ((state_0 & 6) != 0) {
                    if ((state_0 & 2) != 0 && !JSGuards.isJSMap(arguments0Value_) && MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value_, this.mapLib)) {
                        return this.doForeignMap(arguments0Value_, arguments1Value_, arguments2Value_, this.mapLib, this.foreignMap_exportValueNode_);
                    }
                    if ((state_0 & 4) != 0 && !JSGuards.isJSMap(arguments0Value_) && !MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value_, this.mapLib)) {
                        return MapPrototypeBuiltins.JSMapSetNode.notMap(arguments0Value_, arguments1Value_, arguments2Value_, this.mapLib);
                    }
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
                InteropLibrary notMap_mapLib__;
                InteropLibrary foreignMap_mapLib__;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSMapObject) {
                    JSMapObject arguments0Value_ = (JSMapObject)arguments0Value;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.doMap(arguments0Value_, arguments1Value, arguments2Value);
                    return jSDynamicObject;
                }
                if (!JSGuards.isJSMap(arguments0Value) && MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value, foreignMap_mapLib__ = super.insert(this.mapLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.mapLib))) {
                    Object foreignMap_mapLib___check;
                    if (this.mapLib == null) {
                        foreignMap_mapLib___check = super.insert(foreignMap_mapLib__);
                        if (foreignMap_mapLib___check == null) {
                            throw new AssertionError((Object)"Specialization 'doForeignMap(Object, Object, Object, InteropLibrary, ExportValueNode)' contains a shared cache with name 'mapLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.mapLib = foreignMap_mapLib___check;
                    }
                    this.foreignMap_exportValueNode_ = super.insert(ExportValueNode.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    foreignMap_mapLib___check = this.doForeignMap(arguments0Value, arguments1Value, arguments2Value, foreignMap_mapLib__, this.foreignMap_exportValueNode_);
                    return foreignMap_mapLib___check;
                }
                if (!JSGuards.isJSMap(arguments0Value) && !MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value, notMap_mapLib__ = super.insert(this.mapLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.mapLib))) {
                    if (this.mapLib == null) {
                        InteropLibrary notMap_mapLib___check = super.insert(notMap_mapLib__);
                        if (notMap_mapLib___check == null) {
                            throw new AssertionError((Object)"Specialization 'notMap(Object, Object, Object, InteropLibrary)' contains a shared cache with name 'mapLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.mapLib = notMap_mapLib___check;
                    }
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = MapPrototypeBuiltins.JSMapSetNode.notMap(arguments0Value, arguments1Value, arguments2Value, notMap_mapLib__);
                    return jSDynamicObject;
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
            ArrayList<List<Node>> cached;
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doMap";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doForeignMap";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Node>>();
                cached.add(Arrays.asList(this.mapLib, this.foreignMap_exportValueNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "notMap";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.mapLib));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static MapPrototypeBuiltins.JSMapSetNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSMapSetNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=MapPrototypeBuiltins.JSMapGetNode.class)
    public static final class JSMapGetNodeGen
    extends MapPrototypeBuiltins.JSMapGetNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private InteropLibrary mapLib;
        @Node.Child
        private ImportValueNode foreignMap_importValue_;

        private JSMapGetNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSMapObject) {
                    JSMapObject arguments0Value__ = (JSMapObject)arguments0Value_;
                    return this.doMap(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 6) != 0) {
                    if ((state_0 & 2) != 0 && !JSGuards.isJSMap(arguments0Value_) && MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value_, this.mapLib)) {
                        return this.doForeignMap(arguments0Value_, arguments1Value_, this.mapLib, this.foreignMap_importValue_);
                    }
                    if ((state_0 & 4) != 0 && !JSGuards.isJSMap(arguments0Value_) && !MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value_, this.mapLib)) {
                        return MapPrototypeBuiltins.JSMapGetNode.notMap(arguments0Value_, arguments1Value_, this.mapLib);
                    }
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                InteropLibrary notMap_mapLib__;
                InteropLibrary foreignMap_mapLib__;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSMapObject) {
                    JSMapObject arguments0Value_ = (JSMapObject)arguments0Value;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.doMap(arguments0Value_, arguments1Value);
                    return object;
                }
                if (!JSGuards.isJSMap(arguments0Value) && MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value, foreignMap_mapLib__ = super.insert(this.mapLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.mapLib))) {
                    Object foreignMap_mapLib___check;
                    if (this.mapLib == null) {
                        foreignMap_mapLib___check = super.insert(foreignMap_mapLib__);
                        if (foreignMap_mapLib___check == null) {
                            throw new AssertionError((Object)"Specialization 'doForeignMap(Object, Object, InteropLibrary, ImportValueNode)' contains a shared cache with name 'mapLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.mapLib = foreignMap_mapLib___check;
                    }
                    this.foreignMap_importValue_ = super.insert(ImportValueNode.create());
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    foreignMap_mapLib___check = this.doForeignMap(arguments0Value, arguments1Value, foreignMap_mapLib__, this.foreignMap_importValue_);
                    return foreignMap_mapLib___check;
                }
                if (!JSGuards.isJSMap(arguments0Value) && !MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value, notMap_mapLib__ = super.insert(this.mapLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.mapLib))) {
                    if (this.mapLib == null) {
                        InteropLibrary notMap_mapLib___check = super.insert(notMap_mapLib__);
                        if (notMap_mapLib___check == null) {
                            throw new AssertionError((Object)"Specialization 'notMap(Object, Object, InteropLibrary)' contains a shared cache with name 'mapLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.mapLib = notMap_mapLib___check;
                    }
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Object object = MapPrototypeBuiltins.JSMapGetNode.notMap(arguments0Value, arguments1Value, notMap_mapLib__);
                    return object;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            ArrayList<List<Node>> cached;
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doMap";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doForeignMap";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Node>>();
                cached.add(Arrays.asList(this.mapLib, this.foreignMap_importValue_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "notMap";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.mapLib));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static MapPrototypeBuiltins.JSMapGetNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSMapGetNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=MapPrototypeBuiltins.JSMapDeleteNode.class)
    public static final class JSMapDeleteNodeGen
    extends MapPrototypeBuiltins.JSMapDeleteNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private InteropLibrary mapLib;

        private JSMapDeleteNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSMapObject) {
                    JSMapObject arguments0Value__ = (JSMapObject)arguments0Value_;
                    return this.doMap(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 6) != 0) {
                    if ((state_0 & 2) != 0 && !JSGuards.isJSMap(arguments0Value_) && MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value_, this.mapLib)) {
                        return this.doForeignMap(arguments0Value_, arguments1Value_, this.mapLib);
                    }
                    if ((state_0 & 4) != 0 && !JSGuards.isJSMap(arguments0Value_) && !MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value_, this.mapLib)) {
                        return MapPrototypeBuiltins.JSMapDeleteNode.notMap(arguments0Value_, arguments1Value_, this.mapLib);
                    }
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
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSMapObject) {
                    JSMapObject arguments0Value__ = (JSMapObject)arguments0Value_;
                    return this.doMap(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 6) != 0) {
                    if ((state_0 & 2) != 0 && !JSGuards.isJSMap(arguments0Value_) && MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value_, this.mapLib)) {
                        return this.doForeignMap(arguments0Value_, arguments1Value_, this.mapLib);
                    }
                    if ((state_0 & 4) != 0 && !JSGuards.isJSMap(arguments0Value_) && !MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value_, this.mapLib)) {
                        return MapPrototypeBuiltins.JSMapDeleteNode.notMap(arguments0Value_, arguments1Value_, this.mapLib);
                    }
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
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                InteropLibrary notMap_mapLib__;
                InteropLibrary foreignMap_mapLib__;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSMapObject) {
                    JSMapObject arguments0Value_ = (JSMapObject)arguments0Value;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.doMap(arguments0Value_, arguments1Value);
                    return bl;
                }
                if (!JSGuards.isJSMap(arguments0Value) && MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value, foreignMap_mapLib__ = super.insert(this.mapLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.mapLib))) {
                    if (this.mapLib == null) {
                        InteropLibrary foreignMap_mapLib___check = super.insert(foreignMap_mapLib__);
                        if (foreignMap_mapLib___check == null) {
                            throw new AssertionError((Object)"Specialization 'doForeignMap(Object, Object, InteropLibrary)' contains a shared cache with name 'mapLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.mapLib = foreignMap_mapLib___check;
                    }
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean foreignMap_mapLib___check = this.doForeignMap(arguments0Value, arguments1Value, foreignMap_mapLib__);
                    return foreignMap_mapLib___check;
                }
                if (!JSGuards.isJSMap(arguments0Value) && !MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value, notMap_mapLib__ = super.insert(this.mapLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.mapLib))) {
                    if (this.mapLib == null) {
                        InteropLibrary notMap_mapLib___check = super.insert(notMap_mapLib__);
                        if (notMap_mapLib___check == null) {
                            throw new AssertionError((Object)"Specialization 'notMap(Object, Object, InteropLibrary)' contains a shared cache with name 'mapLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.mapLib = notMap_mapLib___check;
                    }
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = MapPrototypeBuiltins.JSMapDeleteNode.notMap(arguments0Value, arguments1Value, notMap_mapLib__);
                    return bl;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
            ArrayList<List<InteropLibrary>> cached;
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doMap";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doForeignMap";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<InteropLibrary>>();
                cached.add(Arrays.asList(this.mapLib));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "notMap";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.mapLib));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static MapPrototypeBuiltins.JSMapDeleteNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSMapDeleteNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=MapPrototypeBuiltins.JSMapClearNode.class)
    public static final class JSMapClearNodeGen
    extends MapPrototypeBuiltins.JSMapClearNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private InteropLibrary mapLib;
        @Node.Child
        private InteropLibrary foreignMap_iteratorLib_;
        @CompilerDirectives.CompilationFinal
        private BranchProfile foreignMap_growProfile_;

        private JSMapClearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSMapObject) {
                JSMapObject arguments0Value__ = (JSMapObject)arguments0Value_;
                return MapPrototypeBuiltins.JSMapClearNode.doMap(arguments0Value__);
            }
            if ((state_0 & 6) != 0) {
                if ((state_0 & 2) != 0 && !JSGuards.isJSMap(arguments0Value_) && MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value_, this.mapLib)) {
                    return this.doForeignMap(arguments0Value_, this.mapLib, this.foreignMap_iteratorLib_, this.foreignMap_growProfile_);
                }
                if ((state_0 & 4) != 0 && !JSGuards.isJSMap(arguments0Value_) && !MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value_, this.mapLib)) {
                    return MapPrototypeBuiltins.JSMapClearNode.notMap(arguments0Value_, this.mapLib);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                InteropLibrary notMap_mapLib__;
                InteropLibrary foreignMap_mapLib__;
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSMapObject) {
                    JSMapObject arguments0Value_ = (JSMapObject)arguments0Value;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = MapPrototypeBuiltins.JSMapClearNode.doMap(arguments0Value_);
                    return jSDynamicObject;
                }
                if (!JSGuards.isJSMap(arguments0Value) && MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value, foreignMap_mapLib__ = super.insert(this.mapLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.mapLib))) {
                    Object foreignMap_mapLib___check;
                    if (this.mapLib == null) {
                        foreignMap_mapLib___check = super.insert(foreignMap_mapLib__);
                        if (foreignMap_mapLib___check == null) {
                            throw new AssertionError((Object)"Specialization 'doForeignMap(Object, InteropLibrary, InteropLibrary, BranchProfile)' contains a shared cache with name 'mapLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.mapLib = foreignMap_mapLib___check;
                    }
                    this.foreignMap_iteratorLib_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.foreignMap_growProfile_ = BranchProfile.create();
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    foreignMap_mapLib___check = this.doForeignMap(arguments0Value, foreignMap_mapLib__, this.foreignMap_iteratorLib_, this.foreignMap_growProfile_);
                    return foreignMap_mapLib___check;
                }
                if (!JSGuards.isJSMap(arguments0Value) && !MapPrototypeBuiltins.JSMapOperation.isForeignHash(arguments0Value, notMap_mapLib__ = super.insert(this.mapLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.mapLib))) {
                    if (this.mapLib == null) {
                        InteropLibrary notMap_mapLib___check = super.insert(notMap_mapLib__);
                        if (notMap_mapLib___check == null) {
                            throw new AssertionError((Object)"Specialization 'notMap(Object, InteropLibrary)' contains a shared cache with name 'mapLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                        }
                        this.mapLib = notMap_mapLib___check;
                    }
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = MapPrototypeBuiltins.JSMapClearNode.notMap(arguments0Value, notMap_mapLib__);
                    return jSDynamicObject;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doMap";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doForeignMap";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Cloneable>>();
                cached.add(Arrays.asList(this.mapLib, this.foreignMap_iteratorLib_, this.foreignMap_growProfile_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "notMap";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.mapLib));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static MapPrototypeBuiltins.JSMapClearNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSMapClearNodeGen(context, builtin, arguments);
        }
    }
}

