
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.builtins.ArrayBufferPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ArrayBufferPrototypeBuiltins.class)
public final class ArrayBufferPrototypeBuiltinsFactory {
    private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

    @GeneratedBy(value=ArrayBufferPrototypeBuiltins.JSArrayBufferSliceNode.class)
    public static final class JSArrayBufferSliceNodeGen
    extends ArrayBufferPrototypeBuiltins.JSArrayBufferSliceNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private InteropLibrary srcBufferLib;
        @Node.Child
        private InteropLibrary dstBufferLib;

        private JSArrayBufferSliceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
        }

        private boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if (JSArrayBuffer.isJSHeapArrayBuffer(arguments0Value_)) {
                    return false;
                }
                arguments0Value_ = (JSDynamicObject)arguments0Value;
                if (JSArrayBuffer.isJSDirectArrayBuffer(arguments0Value_)) {
                    return false;
                }
                arguments0Value_ = (JSDynamicObject)arguments0Value;
                if (JSArrayBuffer.isJSInteropArrayBuffer(arguments0Value_)) {
                    return false;
                }
            }
            return (state_0 & 0x20) != 0 || JSGuards.isJSSharedArrayBuffer(arguments0Value) || (state_0 & 0x40) != 0 && !ArrayBufferPrototypeBuiltins.JSArrayBufferSliceNode.hasBufferElements(arguments0Value, this.srcBufferLib);
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if ((state_0 & 0xBA) == 0 && (state_0 & 0xBF) != 0) {
                return this.execute_int_int0(state_0, frameValue);
            }
            return this.execute_generic1(state_0, frameValue);
        }

        private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
            int arguments2Value_;
            int arguments1Value_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            try {
                arguments1Value_ = this.arguments1_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                Object arguments2Value = this.arguments2_.execute(frameValue);
                return this.executeAndSpecialize(arguments0Value_, ex.getResult(), arguments2Value);
            }
            try {
                arguments2Value_ = this.arguments2_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arguments0Value_, arguments1Value_, ex.getResult());
            }
            if ((state_0 & 5) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if ((state_0 & 1) != 0 && JSArrayBuffer.isJSHeapArrayBuffer(arguments0Value__)) {
                    return this.sliceIntInt(arguments0Value__, arguments1Value_, arguments2Value_);
                }
                if ((state_0 & 4) != 0 && JSArrayBuffer.isJSDirectArrayBuffer(arguments0Value__)) {
                    return this.sliceDirectIntInt(arguments0Value__, arguments1Value_, arguments2Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        private Object execute_generic1(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if ((state_0 & 0x1F) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                int arguments2Value__;
                int arguments1Value__;
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof Integer) {
                    arguments1Value__ = (Integer)arguments1Value_;
                    if (arguments2Value_ instanceof Integer) {
                        arguments2Value__ = (Integer)arguments2Value_;
                        if (JSArrayBuffer.isJSHeapArrayBuffer(arguments0Value__)) {
                            return this.sliceIntInt(arguments0Value__, arguments1Value__, arguments2Value__);
                        }
                    }
                }
                if ((state_0 & 2) != 0 && JSArrayBuffer.isJSHeapArrayBuffer(arguments0Value__)) {
                    return this.slice(arguments0Value__, arguments1Value_, arguments2Value_);
                }
                if ((state_0 & 4) != 0 && arguments1Value_ instanceof Integer) {
                    arguments1Value__ = (Integer)arguments1Value_;
                    if (arguments2Value_ instanceof Integer) {
                        arguments2Value__ = (Integer)arguments2Value_;
                        if (JSArrayBuffer.isJSDirectArrayBuffer(arguments0Value__)) {
                            return this.sliceDirectIntInt(arguments0Value__, arguments1Value__, arguments2Value__);
                        }
                    }
                }
                if ((state_0 & 0x18) != 0) {
                    if ((state_0 & 8) != 0 && JSArrayBuffer.isJSDirectArrayBuffer(arguments0Value__)) {
                        return this.sliceDirect(arguments0Value__, arguments1Value_, arguments2Value_);
                    }
                    if ((state_0 & 0x10) != 0 && JSArrayBuffer.isJSInteropArrayBuffer(arguments0Value__)) {
                        return this.sliceInterop(arguments0Value__, arguments1Value_, arguments2Value_, this.srcBufferLib, this.dstBufferLib);
                    }
                }
            }
            if ((state_0 & 0xA0) != 0) {
                if ((state_0 & 0x20) != 0 && !JSGuards.isJSSharedArrayBuffer(arguments0Value_) && ArrayBufferPrototypeBuiltins.JSArrayBufferSliceNode.hasBufferElements(arguments0Value_, this.srcBufferLib)) {
                    return this.sliceTruffleBuffer(arguments0Value_, arguments1Value_, arguments2Value_, this.srcBufferLib, this.dstBufferLib);
                }
                if ((state_0 & 0x80) != 0 && this.fallbackGuard_(state_0, arguments0Value_, arguments1Value_, arguments2Value_)) {
                    return ArrayBufferPrototypeBuiltins.JSArrayBufferSliceNode.error(arguments0Value_, arguments1Value_, arguments2Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                Object object;
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments0Value instanceof JSDynamicObject) {
                    int arguments2Value_;
                    int arguments1Value_2;
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if ((exclude & 1) == 0 && arguments1Value instanceof Integer) {
                        arguments1Value_2 = (Integer)arguments1Value;
                        if (arguments2Value instanceof Integer) {
                            arguments2Value_ = (Integer)arguments2Value;
                            if (JSArrayBuffer.isJSHeapArrayBuffer(arguments0Value_)) {
                                this.state_0_ = state_0 |= 1;
                                lock.unlock();
                                hasLock = false;
                                JSDynamicObject jSDynamicObject = this.sliceIntInt(arguments0Value_, arguments1Value_2, arguments2Value_);
                                return jSDynamicObject;
                            }
                        }
                    }
                    if (JSArrayBuffer.isJSHeapArrayBuffer(arguments0Value_)) {
                        this.exclude_ = exclude |= 1;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject arguments1Value_2 = this.slice(arguments0Value_, arguments1Value, arguments2Value);
                        return arguments1Value_2;
                    }
                    if ((exclude & 2) == 0 && arguments1Value instanceof Integer) {
                        arguments1Value_2 = (Integer)arguments1Value;
                        if (arguments2Value instanceof Integer) {
                            arguments2Value_ = (Integer)arguments2Value;
                            if (JSArrayBuffer.isJSDirectArrayBuffer(arguments0Value_)) {
                                this.state_0_ = state_0 |= 4;
                                lock.unlock();
                                hasLock = false;
                                JSDynamicObject jSDynamicObject = this.sliceDirectIntInt(arguments0Value_, arguments1Value_2, arguments2Value_);
                                return jSDynamicObject;
                            }
                        }
                    }
                    if (JSArrayBuffer.isJSDirectArrayBuffer(arguments0Value_)) {
                        this.exclude_ = exclude |= 2;
                        state_0 &= 0xFFFFFFFB;
                        this.state_0_ = state_0 |= 8;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.sliceDirect(arguments0Value_, arguments1Value, arguments2Value);
                        return jSDynamicObject;
                    }
                    if (JSArrayBuffer.isJSInteropArrayBuffer(arguments0Value_)) {
                        this.srcBufferLib = super.insert(this.srcBufferLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.srcBufferLib);
                        this.dstBufferLib = super.insert(this.dstBufferLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.dstBufferLib);
                        this.state_0_ = state_0 |= 0x10;
                        lock.unlock();
                        hasLock = false;
                        Object object2 = this.sliceInterop(arguments0Value_, arguments1Value, arguments2Value, this.srcBufferLib, this.dstBufferLib);
                        return object2;
                    }
                }
                if (!JSGuards.isJSSharedArrayBuffer(arguments0Value)) {
                    InteropLibrary sliceTruffleBuffer_srcBufferLib___check;
                    if ((state_0 & 0x40) == 0) {
                        if (this.srcBufferLib == null) {
                            sliceTruffleBuffer_srcBufferLib___check = super.insert(this.srcBufferLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.srcBufferLib);
                            if (sliceTruffleBuffer_srcBufferLib___check == null) {
                                throw new AssertionError((Object)"Specialization 'sliceTruffleBuffer(Object, Object, Object, InteropLibrary, InteropLibrary)' contains a shared cache with name 'srcBufferLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.srcBufferLib = sliceTruffleBuffer_srcBufferLib___check;
                        }
                        this.state_0_ = state_0 |= 0x40;
                    }
                    if (ArrayBufferPrototypeBuiltins.JSArrayBufferSliceNode.hasBufferElements(arguments0Value, this.srcBufferLib)) {
                        if (this.srcBufferLib == null) {
                            sliceTruffleBuffer_srcBufferLib___check = super.insert(this.srcBufferLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.srcBufferLib);
                            if (sliceTruffleBuffer_srcBufferLib___check == null) {
                                throw new AssertionError((Object)"Specialization 'sliceTruffleBuffer(Object, Object, Object, InteropLibrary, InteropLibrary)' contains a shared cache with name 'srcBufferLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state.");
                            }
                            this.srcBufferLib = sliceTruffleBuffer_srcBufferLib___check;
                        }
                        this.dstBufferLib = super.insert(this.dstBufferLib == null ? INTEROP_LIBRARY_.createDispatched(5) : this.dstBufferLib);
                        this.state_0_ = state_0 |= 0x20;
                        lock.unlock();
                        hasLock = false;
                        object = this.sliceTruffleBuffer(arguments0Value, arguments1Value, arguments2Value, this.srcBufferLib, this.dstBufferLib);
                        return object;
                    }
                }
                this.state_0_ = state_0 |= 0x80;
                lock.unlock();
                hasLock = false;
                object = ArrayBufferPrototypeBuiltins.JSArrayBufferSliceNode.error(arguments0Value, arguments1Value, arguments2Value);
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
            if ((state_0 & 0xBF) == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & 0xBF & (state_0 & 0xBF) - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<InteropLibrary>> cached;
            Object[] data = new Object[8];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "sliceIntInt";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : ((exclude & 1) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
            data[1] = s;
            s = new Object[3];
            s[0] = "slice";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "sliceDirectIntInt";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : ((exclude & 2) != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
            data[3] = s;
            s = new Object[3];
            s[0] = "sliceDirect";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            s = new Object[3];
            s[0] = "sliceInterop";
            if ((state_0 & 0x10) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<InteropLibrary>>();
                cached.add(Arrays.asList(this.srcBufferLib, this.dstBufferLib));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[5] = s;
            s = new Object[3];
            s[0] = "sliceTruffleBuffer";
            if ((state_0 & 0x20) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.srcBufferLib, this.dstBufferLib));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[6] = s;
            s = new Object[3];
            s[0] = "error";
            s[1] = (state_0 & 0x80) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[7] = s;
            return Introspection.Provider.create(data);
        }

        public static ArrayBufferPrototypeBuiltins.JSArrayBufferSliceNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSArrayBufferSliceNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=ArrayBufferPrototypeBuiltins.ByteLengthGetterNode.class)
    public static final class ByteLengthGetterNodeGen
    extends ArrayBufferPrototypeBuiltins.ByteLengthGetterNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private InteropLibrary interopArrayBuffer_interop_;

        private ByteLengthGetterNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && JSArrayBuffer.isJSHeapArrayBuffer(arguments0Value_)) {
                    return this.heapArrayBuffer(arguments0Value_);
                }
                if ((state_0 & 2) != 0 && JSArrayBuffer.isJSDirectArrayBuffer(arguments0Value_)) {
                    return this.directArrayBuffer(arguments0Value_);
                }
                if ((state_0 & 4) != 0 && JSArrayBuffer.isJSInteropArrayBuffer(arguments0Value_)) {
                    return this.interopArrayBuffer(arguments0Value_, this.interopArrayBuffer_interop_);
                }
                if ((state_0 & 8) != 0 && ByteLengthGetterNodeGen.fallbackGuard_(state_0, arguments0Value_)) {
                    return ArrayBufferPrototypeBuiltins.ByteLengthGetterNode.error(arguments0Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public int executeInt(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && JSArrayBuffer.isJSHeapArrayBuffer(arguments0Value_)) {
                    return this.heapArrayBuffer(arguments0Value_);
                }
                if ((state_0 & 2) != 0 && JSArrayBuffer.isJSDirectArrayBuffer(arguments0Value_)) {
                    return this.directArrayBuffer(arguments0Value_);
                }
                if ((state_0 & 4) != 0 && JSArrayBuffer.isJSInteropArrayBuffer(arguments0Value_)) {
                    return this.interopArrayBuffer(arguments0Value_, this.interopArrayBuffer_interop_);
                }
                if ((state_0 & 8) != 0 && ByteLengthGetterNodeGen.fallbackGuard_(state_0, arguments0Value_)) {
                    return ArrayBufferPrototypeBuiltins.ByteLengthGetterNode.error(arguments0Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeInt(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private int executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (JSArrayBuffer.isJSHeapArrayBuffer(arguments0Value)) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    int n = this.heapArrayBuffer(arguments0Value);
                    return n;
                }
                if (JSArrayBuffer.isJSDirectArrayBuffer(arguments0Value)) {
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = this.directArrayBuffer(arguments0Value);
                    return n;
                }
                if (JSArrayBuffer.isJSInteropArrayBuffer(arguments0Value)) {
                    this.interopArrayBuffer_interop_ = super.insert(INTEROP_LIBRARY_.createDispatched(5));
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    int n = this.interopArrayBuffer(arguments0Value, this.interopArrayBuffer_interop_);
                    return n;
                }
                this.state_0_ = state_0 |= 8;
                lock.unlock();
                hasLock = false;
                int n = ArrayBufferPrototypeBuiltins.ByteLengthGetterNode.error(arguments0Value);
                return n;
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
            Object[] data = new Object[5];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "heapArrayBuffer";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "directArrayBuffer";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "interopArrayBuffer";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                ArrayList<List<InteropLibrary>> cached = new ArrayList<List<InteropLibrary>>();
                cached.add(Arrays.asList(this.interopArrayBuffer_interop_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "error";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
            if ((state_0 & 1) == 0 && JSArrayBuffer.isJSHeapArrayBuffer(arguments0Value)) {
                return false;
            }
            if ((state_0 & 2) == 0 && JSArrayBuffer.isJSDirectArrayBuffer(arguments0Value)) {
                return false;
            }
            return (state_0 & 4) != 0 || !JSArrayBuffer.isJSInteropArrayBuffer(arguments0Value);
        }

        public static ArrayBufferPrototypeBuiltins.ByteLengthGetterNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ByteLengthGetterNodeGen(context, builtin, arguments);
        }
    }
}

