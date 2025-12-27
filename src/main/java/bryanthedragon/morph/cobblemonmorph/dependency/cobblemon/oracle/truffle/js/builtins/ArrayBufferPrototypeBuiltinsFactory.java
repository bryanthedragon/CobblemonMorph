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
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ArrayBufferPrototypeBuiltins.class)
public final class ArrayBufferPrototypeBuiltinsFactory {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   @GeneratedBy(ArrayBufferPrototypeBuiltins.ByteLengthGetterNode.class)
   public static final class ByteLengthGetterNodeGen extends ArrayBufferPrototypeBuiltins.ByteLengthGetterNode implements Introspection.Provider {
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

            if ((state_0 & 8) != 0 && fallbackGuard_(state_0, arguments0Value_)) {
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

            if ((state_0 & 8) != 0 && fallbackGuard_(state_0, arguments0Value_)) {
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

      private int executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var5;
         try {
            int state_0 = this.state_0_;
            if (JSArrayBuffer.isJSHeapArrayBuffer(arguments0Value)) {
               int var12;
               this.state_0_ = var12 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.heapArrayBuffer(arguments0Value);
            }

            if (JSArrayBuffer.isJSDirectArrayBuffer(arguments0Value)) {
               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.directArrayBuffer(arguments0Value);
            }

            if (!JSArrayBuffer.isJSInteropArrayBuffer(arguments0Value)) {
               int var10;
               this.state_0_ = var10 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return ArrayBufferPrototypeBuiltins.ByteLengthGetterNode.error(arguments0Value);
            }

            this.interopArrayBuffer_interop_ = super.insert(ArrayBufferPrototypeBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
            int var9;
            this.state_0_ = var9 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            var5 = this.interopArrayBuffer(arguments0Value, this.interopArrayBuffer_interop_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var5;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[5];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"heapArrayBuffer", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"directArrayBuffer", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"interopArrayBuffer", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.interopArrayBuffer_interop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"error", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
         if ((state_0 & 1) == 0 && JSArrayBuffer.isJSHeapArrayBuffer(arguments0Value)) {
            return false;
         } else {
            return (state_0 & 2) == 0 && JSArrayBuffer.isJSDirectArrayBuffer(arguments0Value)
               ? false
               : (state_0 & 4) != 0 || !JSArrayBuffer.isJSInteropArrayBuffer(arguments0Value);
         }
      }

      public static ArrayBufferPrototypeBuiltins.ByteLengthGetterNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ArrayBufferPrototypeBuiltinsFactory.ByteLengthGetterNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ArrayBufferPrototypeBuiltins.JSArrayBufferSliceNode.class)
   public static final class JSArrayBufferSliceNodeGen extends ArrayBufferPrototypeBuiltins.JSArrayBufferSliceNode implements Introspection.Provider {
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

         return (state_0 & 32) != 0
            || JSGuards.isJSSharedArrayBuffer(arguments0Value)
            || (state_0 & 64) != 0 && !ArrayBufferPrototypeBuiltins.JSArrayBufferSliceNode.hasBufferElements(arguments0Value, this.srcBufferLib);
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         return (state_0 & 186) == 0 && (state_0 & 191) != 0 ? this.execute_int_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
      }

      private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var8) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments2Value = this.arguments2_.execute(frameValue);
            return this.executeAndSpecialize(arguments0Value_, var8.getResult(), arguments2Value);
         }

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, var7.getResult());
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
         if ((state_0 & 31) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if ((state_0 & 1) != 0 && arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               if (arguments2Value_ instanceof Integer) {
                  int arguments2Value__ = (Integer)arguments2Value_;
                  if (JSArrayBuffer.isJSHeapArrayBuffer(arguments0Value__)) {
                     return this.sliceIntInt(arguments0Value__, arguments1Value__, arguments2Value__);
                  }
               }
            }

            if ((state_0 & 2) != 0 && JSArrayBuffer.isJSHeapArrayBuffer(arguments0Value__)) {
               return this.slice(arguments0Value__, arguments1Value_, arguments2Value_);
            }

            if ((state_0 & 4) != 0 && arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               if (arguments2Value_ instanceof Integer) {
                  int arguments2Value__ = (Integer)arguments2Value_;
                  if (JSArrayBuffer.isJSDirectArrayBuffer(arguments0Value__)) {
                     return this.sliceDirectIntInt(arguments0Value__, arguments1Value__, arguments2Value__);
                  }
               }
            }

            if ((state_0 & 24) != 0) {
               if ((state_0 & 8) != 0 && JSArrayBuffer.isJSDirectArrayBuffer(arguments0Value__)) {
                  return this.sliceDirect(arguments0Value__, arguments1Value_, arguments2Value_);
               }

               if ((state_0 & 16) != 0 && JSArrayBuffer.isJSInteropArrayBuffer(arguments0Value__)) {
                  return this.sliceInterop(arguments0Value__, arguments1Value_, arguments2Value_, this.srcBufferLib, this.dstBufferLib);
               }
            }
         }

         if ((state_0 & 160) != 0) {
            if ((state_0 & 32) != 0
               && !JSGuards.isJSSharedArrayBuffer(arguments0Value_)
               && ArrayBufferPrototypeBuiltins.JSArrayBufferSliceNode.hasBufferElements(arguments0Value_, this.srcBufferLib)) {
               return this.sliceTruffleBuffer(arguments0Value_, arguments1Value_, arguments2Value_, this.srcBufferLib, this.dstBufferLib);
            }

            if ((state_0 & 128) != 0 && this.fallbackGuard_(state_0, arguments0Value_, arguments1Value_, arguments2Value_)) {
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

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if ((exclude & 1) == 0 && arguments1Value instanceof Integer) {
                  int arguments1Value_ = (Integer)arguments1Value;
                  if (arguments2Value instanceof Integer) {
                     int arguments2Value_ = (Integer)arguments2Value;
                     if (JSArrayBuffer.isJSHeapArrayBuffer(arguments0Value_)) {
                        int var23;
                        this.state_0_ = var23 = state_0 | 1;
                        lock.unlock();
                        hasLock = false;
                        return this.sliceIntInt(arguments0Value_, arguments1Value_, arguments2Value_);
                     }
                  }
               }

               if (JSArrayBuffer.isJSHeapArrayBuffer(arguments0Value_)) {
                  int var25;
                  this.exclude_ = var25 = exclude | 1;
                  state_0 &= -2;
                  int var22;
                  this.state_0_ = var22 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.slice(arguments0Value_, arguments1Value, arguments2Value);
               }

               if ((exclude & 2) == 0 && arguments1Value instanceof Integer) {
                  int arguments1Value_ = (Integer)arguments1Value;
                  if (arguments2Value instanceof Integer) {
                     int arguments2Value_ = (Integer)arguments2Value;
                     if (JSArrayBuffer.isJSDirectArrayBuffer(arguments0Value_)) {
                        int var20;
                        this.state_0_ = var20 = state_0 | 4;
                        lock.unlock();
                        hasLock = false;
                        return this.sliceDirectIntInt(arguments0Value_, arguments1Value_, arguments2Value_);
                     }
                  }
               }

               if (JSArrayBuffer.isJSDirectArrayBuffer(arguments0Value_)) {
                  int var24;
                  this.exclude_ = var24 = exclude | 2;
                  state_0 &= -5;
                  int var19;
                  this.state_0_ = var19 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.sliceDirect(arguments0Value_, arguments1Value, arguments2Value);
               }

               if (JSArrayBuffer.isJSInteropArrayBuffer(arguments0Value_)) {
                  this.srcBufferLib = super.insert(
                     this.srcBufferLib == null ? ArrayBufferPrototypeBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.srcBufferLib
                  );
                  this.dstBufferLib = super.insert(
                     this.dstBufferLib == null ? ArrayBufferPrototypeBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.dstBufferLib
                  );
                  int var17;
                  this.state_0_ = var17 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return this.sliceInterop(arguments0Value_, arguments1Value, arguments2Value, this.srcBufferLib, this.dstBufferLib);
               }
            }

            if (!JSGuards.isJSSharedArrayBuffer(arguments0Value)) {
               if ((state_0 & 64) == 0) {
                  if (this.srcBufferLib == null) {
                     InteropLibrary sliceTruffleBuffer_srcBufferLib___check = super.insert(
                        this.srcBufferLib == null ? ArrayBufferPrototypeBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.srcBufferLib
                     );
                     if (sliceTruffleBuffer_srcBufferLib___check == null) {
                        throw new AssertionError(
                           "Specialization 'sliceTruffleBuffer(Object, Object, Object, InteropLibrary, InteropLibrary)' contains a shared cache with name 'srcBufferLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                        );
                     }

                     this.srcBufferLib = sliceTruffleBuffer_srcBufferLib___check;
                  }

                  this.state_0_ = state_0 |= 64;
               }

               if (ArrayBufferPrototypeBuiltins.JSArrayBufferSliceNode.hasBufferElements(arguments0Value, this.srcBufferLib)) {
                  if (this.srcBufferLib == null) {
                     InteropLibrary sliceTruffleBuffer_srcBufferLib___check = super.insert(
                        this.srcBufferLib == null ? ArrayBufferPrototypeBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.srcBufferLib
                     );
                     if (sliceTruffleBuffer_srcBufferLib___check == null) {
                        throw new AssertionError(
                           "Specialization 'sliceTruffleBuffer(Object, Object, Object, InteropLibrary, InteropLibrary)' contains a shared cache with name 'srcBufferLib' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                        );
                     }

                     this.srcBufferLib = sliceTruffleBuffer_srcBufferLib___check;
                  }

                  this.dstBufferLib = super.insert(
                     this.dstBufferLib == null ? ArrayBufferPrototypeBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.dstBufferLib
                  );
                  int var16;
                  this.state_0_ = var16 = state_0 | 32;
                  lock.unlock();
                  hasLock = false;
                  return this.sliceTruffleBuffer(arguments0Value, arguments1Value, arguments2Value, this.srcBufferLib, this.dstBufferLib);
               }
            }

            int var15;
            this.state_0_ = var15 = state_0 | 128;
            lock.unlock();
            hasLock = false;
            return ArrayBufferPrototypeBuiltins.JSArrayBufferSliceNode.error(arguments0Value, arguments1Value, arguments2Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if ((state_0 & 191) == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & 191 & (state_0 & 191) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[8];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"sliceIntInt", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else if ((exclude & 1) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"slice", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"sliceDirectIntInt", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else if ((exclude & 2) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"sliceDirect", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"sliceInterop", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.srcBufferLib, this.dstBufferLib));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"sliceTruffleBuffer", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.srcBufferLib, this.dstBufferLib));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"error", null, null};
         if ((state_0 & 128) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         return Introspection.Provider.create(data);
      }

      public static ArrayBufferPrototypeBuiltins.JSArrayBufferSliceNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ArrayBufferPrototypeBuiltinsFactory.JSArrayBufferSliceNodeGen(context, builtin, arguments);
      }
   }
}
