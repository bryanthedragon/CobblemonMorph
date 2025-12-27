package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.utilities.AssumedValue;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ScriptNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.access.IsRegExpNode;
import com.oracle.truffle.js.nodes.access.IterableToListNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.array.ArrayCreateNode;
import com.oracle.truffle.js.nodes.cast.JSNumberToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSNumericToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToIndexNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerThrowOnInfinityNode;
import com.oracle.truffle.js.nodes.cast.JSToIntegerWithoutRoundingNode;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.cast.ToArrayLengthNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalCalendarWithISODefaultNode;
import com.oracle.truffle.js.nodes.temporal.ToTemporalTimeZoneNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSAbstractBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyModuleObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.LRUCache;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ConstructorBuiltins.class)
public final class ConstructorBuiltinsFactory {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   @GeneratedBy(ConstructorBuiltins.CallBigIntNode.class)
   public static final class CallBigIntNodeGen extends ConstructorBuiltins.CallBigIntNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSNumberToBigIntNode callBigInt_numberToBigIntNode_;
      @Node.Child
      private JSToBigIntNode callBigInt_toBigIntNode_;

      private CallBigIntNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 2) != 0 && arguments0Value_ instanceof Object[]) {
            Object[] arguments0Value__ = (Object[])arguments0Value_;
            if (arguments0Value__.length > 0) {
               return this.callBigInt(arguments0Value__, this.callBigInt_numberToBigIntNode_, this.callBigInt_toBigIntNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         if ((state_0 & 2) != 0) {
            this.execute(frameValue);
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Object[]) {
               Object[] arguments0Value__ = (Object[])arguments0Value_;
               if (arguments0Value__.length == 0) {
                  this.callBigIntZero(arguments0Value__);
                  return;
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(arguments0Value_);
         }
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof Object[]) {
               Object[] arguments0Value_ = (Object[])arguments0Value;
               if (arguments0Value_.length == 0) {
                  int var11;
                  this.state_0_ = var11 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  this.callBigIntZero(arguments0Value_);
                  return null;
               }

               if (arguments0Value_.length > 0) {
                  this.callBigInt_numberToBigIntNode_ = super.insert(JSNumberToBigIntNode.create());
                  this.callBigInt_toBigIntNode_ = super.insert(JSToBigIntNode.create());
                  int var10;
                  this.state_0_ = var10 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.callBigInt(arguments0Value_, this.callBigInt_numberToBigIntNode_, this.callBigInt_toBigIntNode_);
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
         } finally {
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
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"callBigIntZero", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"callBigInt", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.callBigInt_numberToBigIntNode_, this.callBigInt_toBigIntNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.CallBigIntNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.CallBigIntNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.CallBooleanNode.class)
   public static final class CallBooleanNodeGen extends ConstructorBuiltins.CallBooleanNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToBooleanNode toBoolean_;

      private CallBooleanNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.callBoolean(arguments0Value_, this.toBoolean_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if (state_0 != 0) {
            return this.callBoolean(arguments0Value_, this.toBoolean_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var5;
         try {
            int state_0 = this.state_0_;
            this.toBoolean_ = super.insert(JSToBooleanNode.create());
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.callBoolean(arguments0Value, this.toBoolean_);
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
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"callBoolean", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toBoolean_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.CallBooleanNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.CallBooleanNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.CallCollatorNode.class)
   public static final class CallCollatorNodeGen extends ConstructorBuiltins.CallCollatorNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private CallCollatorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.callCollator(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"callCollator", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.CallCollatorNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.CallCollatorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.CallDateNode.class)
   public static final class CallDateNodeGen extends ConstructorBuiltins.CallDateNode implements Introspection.Provider {
      private CallDateNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         return this.callDate();
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"callDate", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.CallDateNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.CallDateNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.CallDateTimeFormatNode.class)
   public static final class CallDateTimeFormatNodeGen extends ConstructorBuiltins.CallDateTimeFormatNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private CallDateTimeFormatNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.callDateTimeFormat(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"callDateTimeFormat", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.CallDateTimeFormatNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.CallDateTimeFormatNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.CallNumberFormatNode.class)
   public static final class CallNumberFormatNodeGen extends ConstructorBuiltins.CallNumberFormatNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private CallNumberFormatNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.callNumberFormat(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"callNumberFormat", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.CallNumberFormatNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.CallNumberFormatNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.CallNumberNode.class)
   public static final class CallNumberNodeGen extends ConstructorBuiltins.CallNumberNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToNumericNode callNumber_toNumericNode_;
      @Node.Child
      private JSNumericToNumberNode callNumber_toNumberFromNumericNode_;

      private CallNumberNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if (state_0 != 0 && arguments0Value_ instanceof Object[]) {
            Object[] arguments0Value__ = (Object[])arguments0Value_;
            if ((state_0 & 1) != 0 && arguments0Value__.length == 0) {
               return this.callNumberZero(arguments0Value__);
            }

            if ((state_0 & 2) != 0 && arguments0Value__.length > 0) {
               return this.callNumber(arguments0Value__, this.callNumber_toNumericNode_, this.callNumber_toNumberFromNumericNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }

      @Override
      public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 2) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Object[]) {
               Object[] arguments0Value__ = (Object[])arguments0Value_;
               if (arguments0Value__.length == 0) {
                  return this.callNumberZero(arguments0Value__);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_));
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 2) == 0 && state_0 != 0) {
               this.executeInt(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof Object[]) {
               Object[] arguments0Value_ = (Object[])arguments0Value;
               if (arguments0Value_.length == 0) {
                  int var11;
                  this.state_0_ = var11 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.callNumberZero(arguments0Value_);
               }

               if (arguments0Value_.length > 0) {
                  this.callNumber_toNumericNode_ = super.insert(JSToNumericNode.create());
                  this.callNumber_toNumberFromNumericNode_ = super.insert(JSNumericToNumberNode.create());
                  int var10;
                  this.state_0_ = var10 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.callNumber(arguments0Value_, this.callNumber_toNumericNode_, this.callNumber_toNumberFromNumericNode_);
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
         } finally {
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
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"callNumberZero", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"callNumber", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.callNumber_toNumericNode_, this.callNumber_toNumberFromNumericNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.CallNumberNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.CallNumberNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.CallRequiresNewNode.class)
   public static final class CallRequiresNewNodeGen extends ConstructorBuiltins.CallRequiresNewNode implements Introspection.Provider {
      private CallRequiresNewNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         return this.call();
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"call", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.CallRequiresNewNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.CallRequiresNewNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.CallStringNode.class)
   public static final class CallStringNodeGen extends ConstructorBuiltins.CallStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToStringNode callStringGeneric_toStringNode_;

      private CallStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if (state_0 != 0 && arguments0Value_ instanceof Object[]) {
            Object[] arguments0Value__ = (Object[])arguments0Value_;
            if ((state_0 & 1) != 0 && arguments0Value__.length == 0) {
               return this.callStringInt0(arguments0Value__);
            }

            if ((state_0 & 2) != 0 && arguments0Value__.length != 0) {
               return this.callStringGeneric(arguments0Value__, this.callStringGeneric_toStringNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof Object[]) {
               Object[] arguments0Value_ = (Object[])arguments0Value;
               if (arguments0Value_.length == 0) {
                  int var11;
                  this.state_0_ = var11 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.callStringInt0(arguments0Value_);
               }

               if (arguments0Value_.length != 0) {
                  this.callStringGeneric_toStringNode_ = super.insert(JSToStringNode.createSymbolToString());
                  int var10;
                  this.state_0_ = var10 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.callStringGeneric(arguments0Value_, this.callStringGeneric_toStringNode_);
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
         } finally {
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
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"callStringInt0", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"callStringGeneric", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.callStringGeneric_toStringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.CallStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.CallStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.CallSymbolNode.class)
   public static final class CallSymbolNodeGen extends ConstructorBuiltins.CallSymbolNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToStringNode callSymbolGeneric_toStringNode_;

      private CallSymbolNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            return this.callSymbolString(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isString(arguments0Value_)) {
            return this.callSymbolGeneric(arguments0Value_, this.callSymbolGeneric_toStringNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Symbol executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Symbol var6;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof TruffleString)) {
               if (JSGuards.isString(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               }

               this.callSymbolGeneric_toStringNode_ = super.insert(JSToStringNode.create());
               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.callSymbolGeneric(arguments0Value, this.callSymbolGeneric_toStringNode_);
            }

            TruffleString arguments0Value_ = (TruffleString)arguments0Value;
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.callSymbolString(arguments0Value_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
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
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"callSymbolString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"callSymbolGeneric", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.callSymbolGeneric_toStringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.CallSymbolNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.CallSymbolNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(ConstructorBuiltins.CallSymbolNode.Inlined.class)
      public static final class InlinedNodeGen extends ConstructorBuiltins.CallSymbolNode.Inlined implements Introspection.Provider {
         @Node.Child
         private JavaScriptNode arguments0_;
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @Node.Child
         private JSToStringNode callSymbolGeneric_toStringNode_;
         @Node.Child
         private ConstructorBuiltinsFactory.CallSymbolNodeGen.InlinedNodeGen.CallSymbolSingletonData callSymbolSingleton_cache;

         private InlinedNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         }

         @Override
         public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
         }

         @ExplodeLoop
         @Override
         protected Object executeWithArguments(Object arguments0Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0 && !JSGuards.isString(arguments0Value)) {
               return this.callSymbolGeneric(arguments0Value, this.callSymbolGeneric_toStringNode_);
            } else {
               if ((state_0 & 6) != 0 && arguments0Value instanceof TruffleString) {
                  TruffleString arguments0Value_ = (TruffleString)arguments0Value;
                  if ((state_0 & 2) != 0) {
                     for (ConstructorBuiltinsFactory.CallSymbolNodeGen.InlinedNodeGen.CallSymbolSingletonData s1_ = this.callSymbolSingleton_cache;
                        s1_ != null;
                        s1_ = s1_.next_
                     ) {
                        if (this.acceptCache(s1_.equalNode_, arguments0Value_, s1_.cachedValue_, s1_.symbolUsageMarker_)) {
                           return this.callSymbolSingleton(arguments0Value_, s1_.cachedValue_, s1_.equalNode_, s1_.symbolUsageMarker_, s1_.cachedSymbol_);
                        }
                     }
                  }

                  if ((state_0 & 4) != 0) {
                     return this.callSymbolString(arguments0Value_);
                  }
               }

               if ((state_0 & 8) != 0) {
                  return this.callInlinedSymbolGeneric(arguments0Value);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  return this.executeAndSpecialize(arguments0Value);
               }
            }
         }

         @ExplodeLoop
         @Override
         public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && !JSGuards.isString(arguments0Value_)) {
               return this.callSymbolGeneric(arguments0Value_, this.callSymbolGeneric_toStringNode_);
            } else {
               if ((state_0 & 6) != 0 && arguments0Value_ instanceof TruffleString) {
                  TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
                  if ((state_0 & 2) != 0) {
                     for (ConstructorBuiltinsFactory.CallSymbolNodeGen.InlinedNodeGen.CallSymbolSingletonData s1_ = this.callSymbolSingleton_cache;
                        s1_ != null;
                        s1_ = s1_.next_
                     ) {
                        if (this.acceptCache(s1_.equalNode_, arguments0Value__, s1_.cachedValue_, s1_.symbolUsageMarker_)) {
                           return this.callSymbolSingleton(arguments0Value__, s1_.cachedValue_, s1_.equalNode_, s1_.symbolUsageMarker_, s1_.cachedSymbol_);
                        }
                     }
                  }

                  if ((state_0 & 4) != 0) {
                     return this.callSymbolString(arguments0Value__);
                  }
               }

               if ((state_0 & 8) != 0) {
                  return this.callInlinedSymbolGeneric(arguments0Value_);
               } else {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  return this.executeAndSpecialize(arguments0Value_);
               }
            }
         }

         @Override
         public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
         }

         private Object executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            Symbol cachedValue__;
            try {
               int state_0 = this.state_0_;
               if (!JSGuards.isString(arguments0Value)) {
                  this.callSymbolGeneric_toStringNode_ = super.insert(JSToStringNode.create());
                  int var16;
                  this.state_0_ = var16 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.callSymbolGeneric(arguments0Value, this.callSymbolGeneric_toStringNode_);
               }

               if (!(arguments0Value instanceof TruffleString)) {
                  int var15;
                  this.state_0_ = var15 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.callInlinedSymbolGeneric(arguments0Value);
               }

               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               int count1_ = 0;
               ConstructorBuiltinsFactory.CallSymbolNodeGen.InlinedNodeGen.CallSymbolSingletonData s1_ = this.callSymbolSingleton_cache;
               if ((state_0 & 2) != 0) {
                  while (s1_ != null && !this.acceptCache(s1_.equalNode_, arguments0Value_, s1_.cachedValue_, s1_.symbolUsageMarker_)) {
                     s1_ = s1_.next_;
                     count1_++;
                  }
               }

               if (s1_ == null) {
                  TruffleString.EqualNode equalNode__ = super.insert(TruffleString.EqualNode.create());
                  AtomicReference<Object> symbolUsageMarker__ = this.createSymbolUsageMarker();
                  if (this.acceptCache(equalNode__, arguments0Value_, arguments0Value_, symbolUsageMarker__) && count1_ < 3) {
                     s1_ = super.insert(new ConstructorBuiltinsFactory.CallSymbolNodeGen.InlinedNodeGen.CallSymbolSingletonData(this.callSymbolSingleton_cache));
                     s1_.cachedValue_ = arguments0Value_;
                     s1_.equalNode_ = s1_.insertAccessor(equalNode__);
                     s1_.symbolUsageMarker_ = symbolUsageMarker__;
                     s1_.cachedSymbol_ = this.createCachedSingletonSymbol(arguments0Value_);
                     VarHandle.storeStoreFence();
                     this.callSymbolSingleton_cache = s1_;
                     this.state_0_ = state_0 |= 2;
                  }
               }

               if (s1_ == null) {
                  int var14;
                  this.state_0_ = var14 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.callSymbolString(arguments0Value_);
               }

               lock.unlock();
               hasLock = false;
               cachedValue__ = this.callSymbolSingleton(arguments0Value_, s1_.cachedValue_, s1_.equalNode_, s1_.symbolUsageMarker_, s1_.cachedSymbol_);
            } finally {
               if (hasLock) {
                  lock.unlock();
               }
            }

            return cachedValue__;
         }

         @Override
         public NodeCost getCost() {
            int state_0 = this.state_0_;
            if (state_0 == 0) {
               return NodeCost.UNINITIALIZED;
            } else {
               if ((state_0 & state_0 - 1) == 0) {
                  ConstructorBuiltinsFactory.CallSymbolNodeGen.InlinedNodeGen.CallSymbolSingletonData s1_ = this.callSymbolSingleton_cache;
                  if (s1_ == null || s1_.next_ == null) {
                     return NodeCost.MONOMORPHIC;
                  }
               }

               return NodeCost.POLYMORPHIC;
            }
         }

         @Override
         public Introspection getIntrospectionData() {
            Object[] data = new Object[5];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[]{"callSymbolGeneric", null, null};
            if ((state_0 & 1) != 0) {
               s[1] = (byte)1;
               ArrayList<Object> cached = new ArrayList<>();
               cached.add(Arrays.asList(this.callSymbolGeneric_toStringNode_));
               s[2] = cached;
            } else {
               s[1] = (byte)0;
            }

            data[1] = s;
            s = new Object[]{"callSymbolSingleton", null, null};
            if ((state_0 & 2) != 0) {
               s[1] = (byte)1;
               ArrayList<Object> cached = new ArrayList<>();

               for (ConstructorBuiltinsFactory.CallSymbolNodeGen.InlinedNodeGen.CallSymbolSingletonData s1_ = this.callSymbolSingleton_cache;
                  s1_ != null;
                  s1_ = s1_.next_
               ) {
                  cached.add(Arrays.asList(s1_.cachedValue_, s1_.equalNode_, s1_.symbolUsageMarker_, s1_.cachedSymbol_));
               }

               s[2] = cached;
            } else {
               s[1] = (byte)0;
            }

            data[2] = s;
            s = new Object[]{"callSymbolString", null, null};
            if ((state_0 & 4) != 0) {
               s[1] = (byte)1;
            } else {
               s[1] = (byte)0;
            }

            data[3] = s;
            s = new Object[]{"callInlinedSymbolGeneric", null, null};
            if ((state_0 & 8) != 0) {
               s[1] = (byte)1;
            } else {
               s[1] = (byte)0;
            }

            data[4] = s;
            return Introspection.Provider.create(data);
         }

         public static ConstructorBuiltins.CallSymbolNode.Inlined create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new ConstructorBuiltinsFactory.CallSymbolNodeGen.InlinedNodeGen(context, builtin, arguments);
         }

         @GeneratedBy(ConstructorBuiltins.CallSymbolNode.Inlined.class)
         private static final class CallSymbolSingletonData extends Node {
            @Node.Child
            ConstructorBuiltinsFactory.CallSymbolNodeGen.InlinedNodeGen.CallSymbolSingletonData next_;
            @CompilerDirectives.CompilationFinal
            TruffleString cachedValue_;
            @Node.Child
            TruffleString.EqualNode equalNode_;
            @CompilerDirectives.CompilationFinal
            AtomicReference<Object> symbolUsageMarker_;
            @CompilerDirectives.CompilationFinal
            Symbol cachedSymbol_;

            CallSymbolSingletonData(ConstructorBuiltinsFactory.CallSymbolNodeGen.InlinedNodeGen.CallSymbolSingletonData next_) {
               this.next_ = next_;
            }

            @Override
            public NodeCost getCost() {
               return NodeCost.NONE;
            }

            <T extends Node> T insertAccessor(T node) {
               return super.insert(node);
            }
         }
      }
   }

   @GeneratedBy(ConstructorBuiltins.CallTypedArrayNode.class)
   public static final class CallTypedArrayNodeGen extends ConstructorBuiltins.CallTypedArrayNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private CallTypedArrayNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if (state_0 != 0 && arguments0Value_ instanceof Object[]) {
            Object[] arguments0Value__ = (Object[])arguments0Value_;
            return this.callTypedArray(arguments0Value__);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof Object[]) {
            Object[] arguments0Value_ = (Object[])arguments0Value;
            int var4;
            this.state_0_ = var4 = state_0 | 1;
            return this.callTypedArray(arguments0Value_);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"callTypedArray", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.CallTypedArrayNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.CallTypedArrayNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructAggregateErrorNode.class)
   public static final class ConstructAggregateErrorNodeGen extends ConstructorBuiltins.ConstructAggregateErrorNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @Node.Child
      private JavaScriptNode arguments3_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ConstructorBuiltinsFactory.ConstructAggregateErrorNodeGen.ConstructErrorData constructError_cache;

      private ConstructAggregateErrorNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
         this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            ConstructorBuiltinsFactory.ConstructAggregateErrorNodeGen.ConstructErrorData s0_ = this.constructError_cache;
            if (s0_ != null) {
               return this.constructError(
                  arguments0Value__,
                  arguments1Value_,
                  arguments2Value_,
                  arguments3Value_,
                  s0_.toStringNode_,
                  s0_.getIteratorMethodNode_,
                  s0_.iteratorCallNode_,
                  s0_.isObjectNode_,
                  s0_.iterableToListNode_,
                  s0_.getNextMethodNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var10;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSDynamicObject)) {
               throw new UnsupportedSpecializationException(
                  this,
                  new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_},
                  arguments0Value,
                  arguments1Value,
                  arguments2Value,
                  arguments3Value
               );
            }

            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            ConstructorBuiltinsFactory.ConstructAggregateErrorNodeGen.ConstructErrorData s0_ = super.insert(
               new ConstructorBuiltinsFactory.ConstructAggregateErrorNodeGen.ConstructErrorData()
            );
            s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
            s0_.getIteratorMethodNode_ = s0_.insertAccessor(this.createGetIteratorMethod());
            s0_.iteratorCallNode_ = s0_.insertAccessor(JSFunctionCallNode.createCall());
            s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
            s0_.iterableToListNode_ = s0_.insertAccessor(IterableToListNode.create());
            s0_.getNextMethodNode_ = s0_.insertAccessor(PropertyGetNode.create(Strings.NEXT, this.getContext()));
            VarHandle.storeStoreFence();
            this.constructError_cache = s0_;
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var10 = this.constructError(
               arguments0Value_,
               arguments1Value,
               arguments2Value,
               arguments3Value,
               s0_.toStringNode_,
               s0_.getIteratorMethodNode_,
               s0_.iteratorCallNode_,
               s0_.isObjectNode_,
               s0_.iterableToListNode_,
               s0_.getNextMethodNode_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var10;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructError", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            ConstructorBuiltinsFactory.ConstructAggregateErrorNodeGen.ConstructErrorData s0_ = this.constructError_cache;
            if (s0_ != null) {
               cached.add(
                  Arrays.asList(
                     s0_.toStringNode_, s0_.getIteratorMethodNode_, s0_.iteratorCallNode_, s0_.isObjectNode_, s0_.iterableToListNode_, s0_.getNextMethodNode_
                  )
               );
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructAggregateErrorNode create(
         JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructAggregateErrorNodeGen(context, builtin, isNewTargetCase, arguments);
      }

      @GeneratedBy(ConstructorBuiltins.ConstructAggregateErrorNode.class)
      private static final class ConstructErrorData extends Node {
         @Node.Child
         JSToStringNode toStringNode_;
         @Node.Child
         GetMethodNode getIteratorMethodNode_;
         @Node.Child
         JSFunctionCallNode iteratorCallNode_;
         @Node.Child
         IsJSObjectNode isObjectNode_;
         @Node.Child
         IterableToListNode iterableToListNode_;
         @Node.Child
         PropertyGetNode getNextMethodNode_;

         ConstructErrorData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructArrayBufferNode.class)
   public static final class ConstructArrayBufferNodeGen extends ConstructorBuiltins.ConstructArrayBufferNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile errorBranch;
      @Node.Child
      private InteropLibrary bufferInterop;
      @Node.Child
      private JSToIndexNode constructFromLength_toIndexNode_;

      private ConstructArrayBufferNodeGen(JSContext context, JSBuiltin builtin, boolean useShared, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, useShared, isNewTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if ((state_0 & 1) != 0 && !this.bufferInterop.hasBufferElements(arguments1Value_)) {
               return this.constructFromLength(arguments0Value__, arguments1Value_, this.constructFromLength_toIndexNode_, this.errorBranch, this.bufferInterop);
            }

            if ((state_0 & 2) != 0 && this.bufferInterop.hasBufferElements(arguments1Value_)) {
               return this.constructFromInteropBuffer(arguments0Value__, arguments1Value_, this.errorBranch, this.bufferInterop);
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
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               InteropLibrary constructFromLength_bufferInterop__ = super.insert(
                  this.bufferInterop == null ? ConstructorBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.bufferInterop
               );
               if (!constructFromLength_bufferInterop__.hasBufferElements(arguments1Value)) {
                  this.constructFromLength_toIndexNode_ = super.insert(JSToIndexNode.create());
                  this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                  if (this.bufferInterop == null) {
                     InteropLibrary constructFromLength_bufferInterop___check = super.insert(constructFromLength_bufferInterop__);
                     if (constructFromLength_bufferInterop___check == null) {
                        throw new AssertionError(
                           "Specialization 'constructFromLength(JSDynamicObject, Object, JSToIndexNode, BranchProfile, InteropLibrary)' contains a shared cache with name 'bufferInterop' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                        );
                     }

                     this.bufferInterop = constructFromLength_bufferInterop___check;
                  }

                  int var13;
                  this.state_0_ = var13 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.constructFromLength(
                     arguments0Value_, arguments1Value, this.constructFromLength_toIndexNode_, this.errorBranch, constructFromLength_bufferInterop__
                  );
               }

               constructFromLength_bufferInterop__ = super.insert(
                  this.bufferInterop == null ? ConstructorBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.bufferInterop
               );
               if (constructFromLength_bufferInterop__.hasBufferElements(arguments1Value)) {
                  this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                  if (this.bufferInterop == null) {
                     InteropLibrary constructFromInteropBuffer_bufferInterop___check = super.insert(constructFromLength_bufferInterop__);
                     if (constructFromInteropBuffer_bufferInterop___check == null) {
                        throw new AssertionError(
                           "Specialization 'constructFromInteropBuffer(JSDynamicObject, Object, BranchProfile, InteropLibrary)' contains a shared cache with name 'bufferInterop' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                        );
                     }

                     this.bufferInterop = constructFromInteropBuffer_bufferInterop___check;
                  }

                  int var12;
                  this.state_0_ = var12 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.constructFromInteropBuffer(arguments0Value_, arguments1Value, this.errorBranch, constructFromLength_bufferInterop__);
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
         } finally {
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
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructFromLength", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.constructFromLength_toIndexNode_, this.errorBranch, this.bufferInterop));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"constructFromInteropBuffer", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.errorBranch, this.bufferInterop));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructArrayBufferNode create(
         JSContext context, JSBuiltin builtin, boolean useShared, boolean isNewTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructArrayBufferNodeGen(context, builtin, useShared, isNewTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructArrayNode.class)
   public static final class ConstructArrayNodeGen extends ConstructorBuiltins.ConstructArrayNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithLengthData constructWithLength_cache;
      @Node.Child
      private ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithForeignArg0Data constructWithForeignArg0_cache;
      @Node.Child
      private ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithForeignArg1Data constructWithForeignArg1_cache;
      @CompilerDirectives.CompilationFinal
      private ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructArrayVarargsData constructArrayVarargs_cache;

      private ConstructArrayNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @ExplodeLoop
      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (arguments1Value_ instanceof Object[]) {
               Object[] arguments1Value__ = (Object[])arguments1Value_;
               if ((state_0 & 1) != 0 && arguments1Value__.length == 0) {
                  return this.constructArray0(arguments0Value__, arguments1Value__);
               }

               if ((state_0 & 2) != 0 && ConstructorBuiltins.ConstructArrayNode.isOneIntegerArg(arguments1Value__)) {
                  return this.constructArrayWithIntLength(arguments0Value__, arguments1Value__);
               }

               if ((state_0 & 4) != 0 && arguments1Value__.length == 1) {
                  for (ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithLengthData s2_ = this.constructWithLength_cache;
                     s2_ != null;
                     s2_ = s2_.next_
                  ) {
                     long len__ = s2_.toArrayLengthNode_.executeLong(ConstructorBuiltins.ConstructArrayNode.firstArg(arguments1Value__));
                     if (s2_.toArrayLengthNode_.isTypeNumber(len__)) {
                        return this.constructWithLength(arguments0Value__, arguments1Value__, s2_.toArrayLengthNode_, s2_.arrayCreateNode_, len__);
                     }
                  }
               }

               if ((state_0 & 8) != 0) {
                  for (ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithForeignArg0Data s3_ = this.constructWithForeignArg0_cache;
                     s3_ != null;
                     s3_ = s3_.next_
                  ) {
                     if (s3_.interop_.accepts(ConstructorBuiltins.ConstructArrayNode.firstArg(arguments1Value__))
                        && ConstructorBuiltins.ConstructArrayNode.isOneForeignArg(arguments1Value__)) {
                        return this.constructWithForeignArg(
                           arguments0Value__, arguments1Value__, s3_.interop_, s3_.arrayCreateNode_, s3_.isNumber_, s3_.rangeErrorProfile_
                        );
                     }
                  }
               }

               if ((state_0 & 16) != 0) {
                  ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithForeignArg1Data s4_ = this.constructWithForeignArg1_cache;
                  if (s4_ != null && ConstructorBuiltins.ConstructArrayNode.isOneForeignArg(arguments1Value__)) {
                     return this.constructWithForeignArg1Boundary(state_0, s4_, arguments0Value__, arguments1Value__);
                  }
               }

               if ((state_0 & 32) != 0) {
                  ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructArrayVarargsData s5_ = this.constructArrayVarargs_cache;
                  if (s5_ != null
                     && !ConstructorBuiltins.ConstructArrayNode.isOneNumberArg(arguments1Value__)
                     && !ConstructorBuiltins.ConstructArrayNode.isOneForeignArg(arguments1Value__)) {
                     return this.constructArrayVarargs(
                        arguments0Value__, arguments1Value__, s5_.isIntegerCase_, s5_.isDoubleCase_, s5_.isObjectCase_, s5_.isLengthZero_
                     );
                  }
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object constructWithForeignArg1Boundary(
         int state_0,
         ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithForeignArg1Data s4_,
         JSDynamicObject arguments0Value__,
         Object[] arguments1Value__
      ) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         JSDynamicObject var8;
         try {
            InteropLibrary interop__ = ConstructorBuiltinsFactory.INTEROP_LIBRARY_
               .getUncached(ConstructorBuiltins.ConstructArrayNode.firstArg(arguments1Value__));
            var8 = this.constructWithForeignArg(arguments0Value__, arguments1Value__, interop__, s4_.arrayCreateNode_, s4_.isNumber_, s4_.rangeErrorProfile_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var8;
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (arguments1Value instanceof Object[]) {
                  Object[] arguments1Value_ = (Object[])arguments1Value;
                  if (arguments1Value_.length == 0) {
                     int var27;
                     this.state_0_ = var27 = state_0 | 1;
                     lock.unlock();
                     hasLock = false;
                     return this.constructArray0(arguments0Value_, arguments1Value_);
                  }

                  if ((exclude & 1) == 0 && ConstructorBuiltins.ConstructArrayNode.isOneIntegerArg(arguments1Value_)) {
                     int var26;
                     this.state_0_ = var26 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.constructArrayWithIntLength(arguments0Value_, arguments1Value_);
                  }

                  long len__ = 0L;
                  if (arguments1Value_.length == 1) {
                     int count2_ = 0;
                     ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithLengthData s2_ = this.constructWithLength_cache;
                     if ((state_0 & 4) != 0) {
                        while (s2_ != null) {
                           len__ = s2_.toArrayLengthNode_.executeLong(ConstructorBuiltins.ConstructArrayNode.firstArg(arguments1Value_));
                           if (s2_.toArrayLengthNode_.isTypeNumber(len__)) {
                              break;
                           }

                           s2_ = s2_.next_;
                           count2_++;
                        }
                     }

                     if (s2_ == null) {
                        ToArrayLengthNode toArrayLengthNode__ = super.insert(ToArrayLengthNode.create());
                        len__ = toArrayLengthNode__.executeLong(ConstructorBuiltins.ConstructArrayNode.firstArg(arguments1Value_));
                        if (toArrayLengthNode__.isTypeNumber(len__) && count2_ < 3) {
                           s2_ = super.insert(new ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithLengthData(this.constructWithLength_cache));
                           s2_.toArrayLengthNode_ = s2_.insertAccessor(toArrayLengthNode__);
                           s2_.arrayCreateNode_ = s2_.insertAccessor(ArrayCreateNode.create(this.getContext()));
                           VarHandle.storeStoreFence();
                           this.constructWithLength_cache = s2_;
                           this.exclude_ = exclude |= 1;
                           int var22 = state_0 & -3;
                           this.state_0_ = state_0 = var22 | 4;
                        }
                     }

                     if (s2_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.constructWithLength(arguments0Value_, arguments1Value_, s2_.toArrayLengthNode_, s2_.arrayCreateNode_, len__);
                     }
                  }

                  if ((exclude & 2) == 0) {
                     int count3_ = 0;
                     ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithForeignArg0Data s3_ = this.constructWithForeignArg0_cache;
                     if ((state_0 & 8) != 0) {
                        while (
                           s3_ != null
                              && (
                                 !s3_.interop_.accepts(ConstructorBuiltins.ConstructArrayNode.firstArg(arguments1Value_))
                                    || !ConstructorBuiltins.ConstructArrayNode.isOneForeignArg(arguments1Value_)
                              )
                        ) {
                           s3_ = s3_.next_;
                           count3_++;
                        }
                     }

                     if (s3_ == null && ConstructorBuiltins.ConstructArrayNode.isOneForeignArg(arguments1Value_) && count3_ < 5) {
                        s3_ = super.insert(
                           new ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithForeignArg0Data(this.constructWithForeignArg0_cache)
                        );
                        s3_.interop_ = s3_.insertAccessor(
                           ConstructorBuiltinsFactory.INTEROP_LIBRARY_.create(ConstructorBuiltins.ConstructArrayNode.firstArg(arguments1Value_))
                        );
                        s3_.arrayCreateNode_ = s3_.insertAccessor(ArrayCreateNode.create(this.getContext()));
                        s3_.isNumber_ = ConditionProfile.createBinaryProfile();
                        s3_.rangeErrorProfile_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.constructWithForeignArg0_cache = s3_;
                        this.state_0_ = state_0 |= 8;
                     }

                     if (s3_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.constructWithForeignArg(
                           arguments0Value_, arguments1Value_, s3_.interop_, s3_.arrayCreateNode_, s3_.isNumber_, s3_.rangeErrorProfile_
                        );
                     }
                  }

                  InteropLibrary interop__ = null;
                  EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                  Node prev_ = encapsulating_.set(this);

                  try {
                     if (ConstructorBuiltins.ConstructArrayNode.isOneForeignArg(arguments1Value_)) {
                        ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithForeignArg1Data s4_ = super.insert(
                           new ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithForeignArg1Data()
                        );
                        InteropLibrary var32 = ConstructorBuiltinsFactory.INTEROP_LIBRARY_
                           .getUncached(ConstructorBuiltins.ConstructArrayNode.firstArg(arguments1Value_));
                        s4_.arrayCreateNode_ = s4_.insertAccessor(ArrayCreateNode.create(this.getContext()));
                        s4_.isNumber_ = ConditionProfile.createBinaryProfile();
                        s4_.rangeErrorProfile_ = BranchProfile.create();
                        VarHandle.storeStoreFence();
                        this.constructWithForeignArg1_cache = s4_;
                        int var28;
                        this.exclude_ = var28 = exclude | 2;
                        this.constructWithForeignArg0_cache = null;
                        state_0 &= -9;
                        int var25;
                        this.state_0_ = var25 = state_0 | 16;
                        lock.unlock();
                        hasLock = false;
                        return this.constructWithForeignArg(
                           arguments0Value_, arguments1Value_, var32, s4_.arrayCreateNode_, s4_.isNumber_, s4_.rangeErrorProfile_
                        );
                     }
                  } finally {
                     encapsulating_.set(prev_);
                  }

                  if (!ConstructorBuiltins.ConstructArrayNode.isOneNumberArg(arguments1Value_)
                     && !ConstructorBuiltins.ConstructArrayNode.isOneForeignArg(arguments1Value_)) {
                     ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructArrayVarargsData s5_ = new ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructArrayVarargsData();
                     s5_.isIntegerCase_ = BranchProfile.create();
                     s5_.isDoubleCase_ = BranchProfile.create();
                     s5_.isObjectCase_ = BranchProfile.create();
                     s5_.isLengthZero_ = ConditionProfile.createBinaryProfile();
                     VarHandle.storeStoreFence();
                     this.constructArrayVarargs_cache = s5_;
                     int var23;
                     this.state_0_ = var23 = state_0 | 32;
                     lock.unlock();
                     hasLock = false;
                     return this.constructArrayVarargs(
                        arguments0Value_, arguments1Value_, s5_.isIntegerCase_, s5_.isDoubleCase_, s5_.isObjectCase_, s5_.isLengthZero_
                     );
                  }
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
         } finally {
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
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithLengthData s2_ = this.constructWithLength_cache;
               ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithForeignArg0Data s3_ = this.constructWithForeignArg0_cache;
               if ((s2_ == null || s2_.next_ == null) && (s3_ == null || s3_.next_ == null)) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[7];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"constructArray0", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"constructArrayWithIntLength", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else if ((exclude & 1) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"constructWithLength", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithLengthData s2_ = this.constructWithLength_cache; s2_ != null; s2_ = s2_.next_) {
               cached.add(Arrays.asList(s2_.toArrayLengthNode_, s2_.arrayCreateNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"constructWithForeignArg", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithForeignArg0Data s3_ = this.constructWithForeignArg0_cache;
               s3_ != null;
               s3_ = s3_.next_
            ) {
               cached.add(Arrays.asList(s3_.interop_, s3_.arrayCreateNode_, s3_.isNumber_, s3_.rangeErrorProfile_));
            }

            s[2] = cached;
         } else if ((exclude & 2) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"constructWithForeignArg", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithForeignArg1Data s4_ = this.constructWithForeignArg1_cache;
            if (s4_ != null) {
               cached.add(Arrays.asList(s4_.arrayCreateNode_, s4_.isNumber_, s4_.rangeErrorProfile_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"constructArrayVarargs", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructArrayVarargsData s5_ = this.constructArrayVarargs_cache;
            if (s5_ != null) {
               cached.add(Arrays.asList(s5_.isIntegerCase_, s5_.isDoubleCase_, s5_.isObjectCase_, s5_.isLengthZero_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructArrayNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructArrayNodeGen(context, builtin, isNewTargetCase, arguments);
      }

      @GeneratedBy(ConstructorBuiltins.ConstructArrayNode.class)
      private static final class ConstructArrayVarargsData {
         @CompilerDirectives.CompilationFinal
         BranchProfile isIntegerCase_;
         @CompilerDirectives.CompilationFinal
         BranchProfile isDoubleCase_;
         @CompilerDirectives.CompilationFinal
         BranchProfile isObjectCase_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile isLengthZero_;

         ConstructArrayVarargsData() {
         }
      }

      @GeneratedBy(ConstructorBuiltins.ConstructArrayNode.class)
      private static final class ConstructWithForeignArg0Data extends Node {
         @Node.Child
         ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithForeignArg0Data next_;
         @Node.Child
         InteropLibrary interop_;
         @Node.Child
         ArrayCreateNode arrayCreateNode_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile isNumber_;
         @CompilerDirectives.CompilationFinal
         BranchProfile rangeErrorProfile_;

         ConstructWithForeignArg0Data(ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithForeignArg0Data next_) {
            this.next_ = next_;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(ConstructorBuiltins.ConstructArrayNode.class)
      private static final class ConstructWithForeignArg1Data extends Node {
         @Node.Child
         ArrayCreateNode arrayCreateNode_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile isNumber_;
         @CompilerDirectives.CompilationFinal
         BranchProfile rangeErrorProfile_;

         ConstructWithForeignArg1Data() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(ConstructorBuiltins.ConstructArrayNode.class)
      private static final class ConstructWithLengthData extends Node {
         @Node.Child
         ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithLengthData next_;
         @Node.Child
         ToArrayLengthNode toArrayLengthNode_;
         @Node.Child
         ArrayCreateNode arrayCreateNode_;

         ConstructWithLengthData(ConstructorBuiltinsFactory.ConstructArrayNodeGen.ConstructWithLengthData next_) {
            this.next_ = next_;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructBigIntNode.class)
   public static final class ConstructBigIntNodeGen extends ConstructorBuiltins.ConstructBigIntNode implements Introspection.Provider {
      private ConstructBigIntNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         return ConstructorBuiltins.ConstructBigIntNode.construct();
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"construct", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructBigIntNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructBigIntNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructBooleanNode.class)
   public static final class ConstructBooleanNodeGen extends ConstructorBuiltins.ConstructBooleanNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToBooleanNode toBoolean_;

      private ConstructBooleanNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructBoolean(arguments0Value__, arguments1Value_, this.toBoolean_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var7;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSDynamicObject)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            }

            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            this.toBoolean_ = super.insert(JSToBooleanNode.create());
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.constructBoolean(arguments0Value_, arguments1Value, this.toBoolean_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var7;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructBoolean", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toBoolean_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructBooleanNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructBooleanNodeGen(context, builtin, isNewTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructCollatorNode.class)
   public static final class ConstructCollatorNodeGen extends ConstructorBuiltins.ConstructCollatorNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConstructCollatorNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, newTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructCollator(arguments0Value__, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.constructCollator(arguments0Value_, arguments1Value, arguments2Value);
         } else {
            throw new UnsupportedSpecializationException(
               this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
            );
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructCollator", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructCollatorNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructCollatorNodeGen(context, builtin, newTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructDataViewNode.class)
   public static final class ConstructDataViewNodeGen extends ConstructorBuiltins.ConstructDataViewNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @Node.Child
      private JavaScriptNode arguments3_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile errorBranch;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile byteLengthCondition;
      @Node.Child
      private JSToIndexNode offsetToIndexNode;
      @Node.Child
      private JSToIndexNode lengthToIndexNode;
      @Node.Child
      private InteropLibrary bufferInterop;

      private ConstructDataViewNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
         this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if ((state_0 & 7) != 0 && arguments1Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
               if ((state_0 & 1) != 0 && JSArrayBuffer.isJSHeapArrayBuffer(arguments1Value__)) {
                  return this.ofHeapArrayBuffer(
                     arguments0Value__,
                     arguments1Value__,
                     arguments2Value_,
                     arguments3Value_,
                     this.errorBranch,
                     this.byteLengthCondition,
                     this.offsetToIndexNode,
                     this.lengthToIndexNode
                  );
               }

               if ((state_0 & 2) != 0 && JSArrayBuffer.isJSDirectOrSharedArrayBuffer(arguments1Value__)) {
                  return this.ofDirectArrayBuffer(
                     arguments0Value__,
                     arguments1Value__,
                     arguments2Value_,
                     arguments3Value_,
                     this.errorBranch,
                     this.byteLengthCondition,
                     this.offsetToIndexNode,
                     this.lengthToIndexNode
                  );
               }

               if ((state_0 & 4) != 0 && JSArrayBuffer.isJSInteropArrayBuffer(arguments1Value__)) {
                  return this.ofInteropArrayBuffer(
                     arguments0Value__,
                     arguments1Value__,
                     arguments2Value_,
                     arguments3Value_,
                     this.errorBranch,
                     this.byteLengthCondition,
                     this.offsetToIndexNode,
                     this.lengthToIndexNode,
                     this.bufferInterop
                  );
               }
            }

            if ((state_0 & 24) != 0) {
               if ((state_0 & 8) != 0 && !JSAbstractBuffer.isJSAbstractBuffer(arguments1Value_) && this.bufferInterop.hasBufferElements(arguments1Value_)) {
                  return this.ofInteropBuffer(
                     arguments0Value__,
                     arguments1Value_,
                     arguments2Value_,
                     arguments3Value_,
                     this.errorBranch,
                     this.byteLengthCondition,
                     this.offsetToIndexNode,
                     this.lengthToIndexNode,
                     this.bufferInterop
                  );
               }

               if ((state_0 & 16) != 0 && !JSAbstractBuffer.isJSAbstractBuffer(arguments1Value_) && !this.bufferInterop.hasBufferElements(arguments1Value_)) {
                  return ConstructorBuiltins.ConstructDataViewNode.error(
                     arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, this.bufferInterop
                  );
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (arguments1Value instanceof JSDynamicObject) {
                  JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
                  if (JSArrayBuffer.isJSHeapArrayBuffer(arguments1Value_)) {
                     this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                     this.byteLengthCondition = this.byteLengthCondition == null ? ConditionProfile.createBinaryProfile() : this.byteLengthCondition;
                     this.offsetToIndexNode = super.insert(this.offsetToIndexNode == null ? JSToIndexNode.create() : this.offsetToIndexNode);
                     this.lengthToIndexNode = super.insert(this.lengthToIndexNode == null ? JSToIndexNode.create() : this.lengthToIndexNode);
                     int var18;
                     this.state_0_ = var18 = state_0 | 1;
                     lock.unlock();
                     hasLock = false;
                     return this.ofHeapArrayBuffer(
                        arguments0Value_,
                        arguments1Value_,
                        arguments2Value,
                        arguments3Value,
                        this.errorBranch,
                        this.byteLengthCondition,
                        this.offsetToIndexNode,
                        this.lengthToIndexNode
                     );
                  }

                  if (JSArrayBuffer.isJSDirectOrSharedArrayBuffer(arguments1Value_)) {
                     this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                     this.byteLengthCondition = this.byteLengthCondition == null ? ConditionProfile.createBinaryProfile() : this.byteLengthCondition;
                     this.offsetToIndexNode = super.insert(this.offsetToIndexNode == null ? JSToIndexNode.create() : this.offsetToIndexNode);
                     this.lengthToIndexNode = super.insert(this.lengthToIndexNode == null ? JSToIndexNode.create() : this.lengthToIndexNode);
                     int var17;
                     this.state_0_ = var17 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.ofDirectArrayBuffer(
                        arguments0Value_,
                        arguments1Value_,
                        arguments2Value,
                        arguments3Value,
                        this.errorBranch,
                        this.byteLengthCondition,
                        this.offsetToIndexNode,
                        this.lengthToIndexNode
                     );
                  }

                  if (JSArrayBuffer.isJSInteropArrayBuffer(arguments1Value_)) {
                     this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                     this.byteLengthCondition = this.byteLengthCondition == null ? ConditionProfile.createBinaryProfile() : this.byteLengthCondition;
                     this.offsetToIndexNode = super.insert(this.offsetToIndexNode == null ? JSToIndexNode.create() : this.offsetToIndexNode);
                     this.lengthToIndexNode = super.insert(this.lengthToIndexNode == null ? JSToIndexNode.create() : this.lengthToIndexNode);
                     this.bufferInterop = super.insert(
                        this.bufferInterop == null ? ConstructorBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.bufferInterop
                     );
                     int var16;
                     this.state_0_ = var16 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.ofInteropArrayBuffer(
                        arguments0Value_,
                        arguments1Value_,
                        arguments2Value,
                        arguments3Value,
                        this.errorBranch,
                        this.byteLengthCondition,
                        this.offsetToIndexNode,
                        this.lengthToIndexNode,
                        this.bufferInterop
                     );
                  }
               }

               if (!JSAbstractBuffer.isJSAbstractBuffer(arguments1Value)) {
                  InteropLibrary ofInteropBuffer_bufferInterop__ = super.insert(
                     this.bufferInterop == null ? ConstructorBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.bufferInterop
                  );
                  if (ofInteropBuffer_bufferInterop__.hasBufferElements(arguments1Value)) {
                     this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                     this.byteLengthCondition = this.byteLengthCondition == null ? ConditionProfile.createBinaryProfile() : this.byteLengthCondition;
                     this.offsetToIndexNode = super.insert(this.offsetToIndexNode == null ? JSToIndexNode.create() : this.offsetToIndexNode);
                     this.lengthToIndexNode = super.insert(this.lengthToIndexNode == null ? JSToIndexNode.create() : this.lengthToIndexNode);
                     if (this.bufferInterop == null) {
                        InteropLibrary ofInteropBuffer_bufferInterop___check = super.insert(ofInteropBuffer_bufferInterop__);
                        if (ofInteropBuffer_bufferInterop___check == null) {
                           throw new AssertionError(
                              "Specialization 'ofInteropBuffer(JSDynamicObject, Object, Object, Object, BranchProfile, ConditionProfile, JSToIndexNode, JSToIndexNode, InteropLibrary)' contains a shared cache with name 'bufferInterop' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.bufferInterop = ofInteropBuffer_bufferInterop___check;
                     }

                     int var15;
                     this.state_0_ = var15 = state_0 | 8;
                     lock.unlock();
                     hasLock = false;
                     return this.ofInteropBuffer(
                        arguments0Value_,
                        arguments1Value,
                        arguments2Value,
                        arguments3Value,
                        this.errorBranch,
                        this.byteLengthCondition,
                        this.offsetToIndexNode,
                        this.lengthToIndexNode,
                        ofInteropBuffer_bufferInterop__
                     );
                  }
               }

               if (!JSAbstractBuffer.isJSAbstractBuffer(arguments1Value)) {
                  InteropLibrary error_bufferInterop__ = super.insert(
                     this.bufferInterop == null ? ConstructorBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5) : this.bufferInterop
                  );
                  if (!error_bufferInterop__.hasBufferElements(arguments1Value)) {
                     if (this.bufferInterop == null) {
                        InteropLibrary error_bufferInterop___check = super.insert(error_bufferInterop__);
                        if (error_bufferInterop___check == null) {
                           throw new AssertionError(
                              "Specialization 'error(JSDynamicObject, Object, Object, Object, InteropLibrary)' contains a shared cache with name 'bufferInterop' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.bufferInterop = error_bufferInterop___check;
                     }

                     int var14;
                     this.state_0_ = var14 = state_0 | 16;
                     lock.unlock();
                     hasLock = false;
                     return ConstructorBuiltins.ConstructDataViewNode.error(
                        arguments0Value_, arguments1Value, arguments2Value, arguments3Value, error_bufferInterop__
                     );
                  }
               }
            }

            throw new UnsupportedSpecializationException(
               this,
               new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_},
               arguments0Value,
               arguments1Value,
               arguments2Value,
               arguments3Value
            );
         } finally {
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
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[6];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"ofHeapArrayBuffer", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.errorBranch, this.byteLengthCondition, this.offsetToIndexNode, this.lengthToIndexNode));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"ofDirectArrayBuffer", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.errorBranch, this.byteLengthCondition, this.offsetToIndexNode, this.lengthToIndexNode));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"ofInteropArrayBuffer", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.errorBranch, this.byteLengthCondition, this.offsetToIndexNode, this.lengthToIndexNode, this.bufferInterop));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"ofInteropBuffer", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.errorBranch, this.byteLengthCondition, this.offsetToIndexNode, this.lengthToIndexNode, this.bufferInterop));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"error", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.bufferInterop));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructDataViewNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructDataViewNodeGen(context, builtin, isNewTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructDateNode.class)
   public static final class ConstructDateNodeGen extends ConstructorBuiltins.ConstructDateNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile constructDateOne_isSpecialCase_;
      @Node.Child
      private InteropLibrary constructDateOne_interop_;

      private ConstructDateNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (arguments1Value_ instanceof Object[]) {
               Object[] arguments1Value__ = (Object[])arguments1Value_;
               if ((state_0 & 1) != 0 && arguments1Value__.length == 0) {
                  return this.constructDateZero(arguments0Value__, arguments1Value__);
               }

               if ((state_0 & 2) != 0 && arguments1Value__.length == 1) {
                  return this.constructDateOne(arguments0Value__, arguments1Value__, this.constructDateOne_isSpecialCase_, this.constructDateOne_interop_);
               }

               if ((state_0 & 4) != 0 && arguments1Value__.length >= 2) {
                  return this.constructDateMult(arguments0Value__, arguments1Value__);
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

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (arguments1Value instanceof Object[]) {
                  Object[] arguments1Value_ = (Object[])arguments1Value;
                  if (arguments1Value_.length == 0) {
                     int var14;
                     this.state_0_ = var14 = state_0 | 1;
                     lock.unlock();
                     hasLock = false;
                     return this.constructDateZero(arguments0Value_, arguments1Value_);
                  }

                  if (arguments1Value_.length == 1) {
                     this.constructDateOne_isSpecialCase_ = ConditionProfile.createBinaryProfile();
                     this.constructDateOne_interop_ = super.insert(ConstructorBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
                     int var13;
                     this.state_0_ = var13 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.constructDateOne(arguments0Value_, arguments1Value_, this.constructDateOne_isSpecialCase_, this.constructDateOne_interop_);
                  }

                  if (arguments1Value_.length >= 2) {
                     int var12;
                     this.state_0_ = var12 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.constructDateMult(arguments0Value_, arguments1Value_);
                  }
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
         } finally {
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
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructDateZero", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"constructDateOne", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.constructDateOne_isSpecialCase_, this.constructDateOne_interop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"constructDateMult", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructDateNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructDateNodeGen(context, builtin, isNewTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructDateTimeFormatNode.class)
   public static final class ConstructDateTimeFormatNodeGen extends ConstructorBuiltins.ConstructDateTimeFormatNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConstructDateTimeFormatNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, newTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructDateTimeFormat(arguments0Value__, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.constructDateTimeFormat(arguments0Value_, arguments1Value, arguments2Value);
         } else {
            throw new UnsupportedSpecializationException(
               this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
            );
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructDateTimeFormat", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructDateTimeFormatNode create(
         JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructDateTimeFormatNodeGen(context, builtin, newTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructDisplayNamesNode.class)
   public static final class ConstructDisplayNamesNodeGen extends ConstructorBuiltins.ConstructDisplayNamesNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConstructDisplayNamesNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, newTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructDisplayNames(arguments0Value__, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.constructDisplayNames(arguments0Value_, arguments1Value, arguments2Value);
         } else {
            throw new UnsupportedSpecializationException(
               this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
            );
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructDisplayNames", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructDisplayNamesNode create(
         JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructDisplayNamesNodeGen(context, builtin, newTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructErrorNode.class)
   public static final class ConstructErrorNodeGen extends ConstructorBuiltins.ConstructErrorNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToStringNode constructError1_toStringNode_;

      private ConstructErrorNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if ((state_0 & 1) != 0 && arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               return this.constructError(arguments0Value__, arguments1Value__, arguments2Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isString(arguments1Value_)) {
               return this.constructError(arguments0Value__, arguments1Value_, arguments2Value_, this.constructError1_toStringNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (arguments1Value instanceof TruffleString) {
                  TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                  int var14;
                  this.state_0_ = var14 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.constructError(arguments0Value_, arguments1Value_, arguments2Value);
               }

               if (!JSGuards.isString(arguments1Value)) {
                  this.constructError1_toStringNode_ = super.insert(JSToStringNode.create());
                  int var13;
                  this.state_0_ = var13 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.constructError(arguments0Value_, arguments1Value, arguments2Value, this.constructError1_toStringNode_);
               }
            }

            throw new UnsupportedSpecializationException(
               this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
            );
         } finally {
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
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructError", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"constructError", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.constructError1_toStringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructErrorNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructErrorNodeGen(context, builtin, isNewTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructFinalizationRegistryNode.class)
   public static final class ConstructFinalizationRegistryNodeGen
      extends ConstructorBuiltins.ConstructFinalizationRegistryNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConstructFinalizationRegistryNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, newTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if ((state_0 & 1) != 0 && this.isCallableNode.executeBoolean(arguments1Value_)) {
               return this.constructFinalizationRegistry(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !this.isCallableNode.executeBoolean(arguments1Value_)) {
               return this.constructFinalizationRegistryNonObject(arguments0Value__, arguments1Value_);
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
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if (this.isCallableNode.executeBoolean(arguments1Value)) {
               int var6;
               this.state_0_ = var6 = state_0 | 1;
               return this.constructFinalizationRegistry(arguments0Value_, arguments1Value);
            }

            if (!this.isCallableNode.executeBoolean(arguments1Value)) {
               int var5;
               this.state_0_ = var5 = state_0 | 2;
               return this.constructFinalizationRegistryNonObject(arguments0Value_, arguments1Value);
            }
         }

         throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructFinalizationRegistry", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"constructFinalizationRegistryNonObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructFinalizationRegistryNode create(
         JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructFinalizationRegistryNodeGen(context, builtin, newTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructFunctionNode.class)
   public static final class ConstructFunctionNodeGen extends ConstructorBuiltins.ConstructFunctionNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile hasArgsProfile_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile hasParamsProfile_;

      private ConstructFunctionNodeGen(
         JSContext context, JSBuiltin builtin, boolean generatorFunction, boolean asyncFunction, boolean isNewTargetCase, JavaScriptNode[] arguments
      ) {
         super(context, builtin, generatorFunction, asyncFunction, isNewTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (arguments1Value_ instanceof Object[]) {
               Object[] arguments1Value__ = (Object[])arguments1Value_;
               return this.constructFunction(arguments0Value__, arguments1Value__, this.hasArgsProfile_, this.hasParamsProfile_);
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
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (arguments1Value instanceof Object[]) {
                  Object[] arguments1Value_ = (Object[])arguments1Value;
                  this.hasArgsProfile_ = ConditionProfile.createBinaryProfile();
                  this.hasParamsProfile_ = ConditionProfile.createBinaryProfile();
                  int var12;
                  this.state_0_ = var12 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.constructFunction(arguments0Value_, arguments1Value_, this.hasArgsProfile_, this.hasParamsProfile_);
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructFunction", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.hasArgsProfile_, this.hasParamsProfile_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructFunctionNode create(
         JSContext context, JSBuiltin builtin, boolean generatorFunction, boolean asyncFunction, boolean isNewTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructFunctionNodeGen(context, builtin, generatorFunction, asyncFunction, isNewTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructJSAdapterNode.class)
   public static final class ConstructJSAdapterNodeGen extends ConstructorBuiltins.ConstructJSAdapterNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConstructJSAdapterNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 7) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if ((state_0 & 3) != 0) {
               if ((state_0 & 1) != 0
                  && JSGuards.isJSObject(arguments0Value__)
                  && JSGuards.isUndefined(arguments1Value_)
                  && JSGuards.isUndefined(arguments2Value_)) {
                  return this.constructJSAdapter(arguments0Value__, arguments1Value_, arguments2Value_);
               }

               if ((state_0 & 2) != 0 && arguments1Value_ instanceof JSDynamicObject) {
                  JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
                  if (JSGuards.isJSObject(arguments0Value__) && JSGuards.isJSObject(arguments1Value__) && JSGuards.isUndefined(arguments2Value_)) {
                     return this.constructJSAdapter(arguments0Value__, arguments1Value__, arguments2Value_);
                  }
               }
            }

            if ((state_0 & 4) != 0 && arguments1Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
               if (arguments2Value_ instanceof JSDynamicObject) {
                  JSDynamicObject arguments2Value__ = (JSDynamicObject)arguments2Value_;
                  if (JSGuards.isJSObject(arguments0Value__) && JSGuards.isJSObject(arguments1Value__) && JSGuards.isJSObject(arguments2Value__)) {
                     return this.constructJSAdapter(arguments0Value__, arguments1Value__, arguments2Value__);
                  }
               }
            }
         }

         if ((state_0 & 8) != 0 && fallbackGuard_(arguments0Value_, arguments1Value_, arguments2Value_)) {
            return this.constructJSAdapter(arguments0Value_, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if (JSGuards.isJSObject(arguments0Value_) && JSGuards.isUndefined(arguments1Value) && JSGuards.isUndefined(arguments2Value)) {
               int var11;
               this.state_0_ = var11 = state_0 | 1;
               return this.constructJSAdapter(arguments0Value_, arguments1Value, arguments2Value);
            }

            if (arguments1Value instanceof JSDynamicObject) {
               JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
               if (JSGuards.isJSObject(arguments0Value_) && JSGuards.isJSObject(arguments1Value_) && JSGuards.isUndefined(arguments2Value)) {
                  int var10;
                  this.state_0_ = var10 = state_0 | 2;
                  return this.constructJSAdapter(arguments0Value_, arguments1Value_, arguments2Value);
               }
            }

            if (arguments1Value instanceof JSDynamicObject) {
               JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
               if (arguments2Value instanceof JSDynamicObject) {
                  JSDynamicObject arguments2Value_ = (JSDynamicObject)arguments2Value;
                  if (JSGuards.isJSObject(arguments0Value_) && JSGuards.isJSObject(arguments1Value_) && JSGuards.isJSObject(arguments2Value_)) {
                     int var9;
                     this.state_0_ = var9 = state_0 | 4;
                     return this.constructJSAdapter(arguments0Value_, arguments1Value_, arguments2Value_);
                  }
               }
            }
         }

         int var8;
         this.state_0_ = var8 = state_0 | 8;
         return this.constructJSAdapter(arguments0Value, arguments1Value, arguments2Value);
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
         Object[] s = new Object[]{"constructJSAdapter", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"constructJSAdapter", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"constructJSAdapter", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"constructJSAdapter", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if (JSGuards.isJSObject(arguments0Value_) && JSGuards.isUndefined(arguments1Value) && JSGuards.isUndefined(arguments2Value)) {
               return false;
            }

            if (arguments1Value instanceof JSDynamicObject) {
               arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSObject(arguments0Value_)) {
                  JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
                  if (JSGuards.isJSObject(arguments1Value_) && JSGuards.isUndefined(arguments2Value)) {
                     return false;
                  }
               }
            }

            if (arguments1Value instanceof JSDynamicObject && arguments2Value instanceof JSDynamicObject) {
               arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSObject(arguments0Value_)) {
                  JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
                  if (JSGuards.isJSObject(arguments1Value_)) {
                     JSDynamicObject arguments2Value_ = (JSDynamicObject)arguments2Value;
                     if (JSGuards.isJSObject(arguments2Value_)) {
                        return false;
                     }
                  }
               }
            }
         }

         return true;
      }

      public static ConstructorBuiltins.ConstructJSAdapterNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructJSAdapterNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructJSProxyNode.class)
   public static final class ConstructJSProxyNodeGen extends ConstructorBuiltins.ConstructJSProxyNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConstructJSProxyNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
      }

      @Override
      public JSDynamicObject execute(JSDynamicObject arguments0Value, Object arguments1Value, Object arguments2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return this.constructJSProxy(arguments0Value, arguments1Value, arguments2Value);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value, arguments1Value, arguments2Value);
         }
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructJSProxy(arguments0Value__, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.constructJSProxy(arguments0Value_, arguments1Value, arguments2Value);
         } else {
            throw new UnsupportedSpecializationException(
               this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
            );
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructJSProxy", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructJSProxyNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructJSProxyNodeGen(context, builtin, isNewTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructJavaImporterNode.class)
   public static final class ConstructJavaImporterNodeGen extends ConstructorBuiltins.ConstructJavaImporterNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConstructJavaImporterNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if (state_0 != 0 && arguments0Value_ instanceof Object[]) {
            Object[] arguments0Value__ = (Object[])arguments0Value_;
            return this.constructJavaImporter(arguments0Value__);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof Object[]) {
            Object[] arguments0Value_ = (Object[])arguments0Value;
            int var4;
            this.state_0_ = var4 = state_0 | 1;
            return this.constructJavaImporter(arguments0Value_);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructJavaImporter", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructJavaImporterNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructJavaImporterNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructListFormatNode.class)
   public static final class ConstructListFormatNodeGen extends ConstructorBuiltins.ConstructListFormatNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConstructListFormatNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, newTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructListFormat(arguments0Value__, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.constructListFormat(arguments0Value_, arguments1Value, arguments2Value);
         } else {
            throw new UnsupportedSpecializationException(
               this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
            );
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructListFormat", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructListFormatNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructListFormatNodeGen(context, builtin, newTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructLocaleNode.class)
   public static final class ConstructLocaleNodeGen extends ConstructorBuiltins.ConstructLocaleNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConstructLocaleNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, newTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructLocale(arguments0Value__, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.constructLocale(arguments0Value_, arguments1Value, arguments2Value);
         } else {
            throw new UnsupportedSpecializationException(
               this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
            );
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructLocale", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructLocaleNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructLocaleNodeGen(context, builtin, newTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructMapNode.class)
   public static final class ConstructMapNodeGen extends ConstructorBuiltins.ConstructMapNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ConstructorBuiltinsFactory.ConstructMapNodeGen.ConstructMapFromIterableData constructMapFromIterable_cache;

      private ConstructMapNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if ((state_0 & 1) != 0 && JSGuards.isNullOrUndefined(arguments1Value_)) {
               return this.constructEmptyMap(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0) {
               ConstructorBuiltinsFactory.ConstructMapNodeGen.ConstructMapFromIterableData s1_ = this.constructMapFromIterable_cache;
               if (s1_ != null && !JSGuards.isNullOrUndefined(arguments1Value_)) {
                  return this.constructMapFromIterable(arguments0Value__, arguments1Value_, s1_.readElementNode_, s1_.isObjectNode_, s1_.isCallableNode_);
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

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isNullOrUndefined(arguments1Value)) {
                  int var13;
                  this.state_0_ = var13 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.constructEmptyMap(arguments0Value_, arguments1Value);
               }

               if (!JSGuards.isNullOrUndefined(arguments1Value)) {
                  ConstructorBuiltinsFactory.ConstructMapNodeGen.ConstructMapFromIterableData s1_ = super.insert(
                     new ConstructorBuiltinsFactory.ConstructMapNodeGen.ConstructMapFromIterableData()
                  );
                  s1_.readElementNode_ = s1_.insertAccessor(ReadElementNode.create(this.getContext()));
                  s1_.isObjectNode_ = s1_.insertAccessor(IsObjectNode.create());
                  s1_.isCallableNode_ = s1_.insertAccessor(IsCallableNode.create());
                  VarHandle.storeStoreFence();
                  this.constructMapFromIterable_cache = s1_;
                  int var12;
                  this.state_0_ = var12 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.constructMapFromIterable(arguments0Value_, arguments1Value, s1_.readElementNode_, s1_.isObjectNode_, s1_.isCallableNode_);
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
         } finally {
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
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructEmptyMap", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"constructMapFromIterable", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            ConstructorBuiltinsFactory.ConstructMapNodeGen.ConstructMapFromIterableData s1_ = this.constructMapFromIterable_cache;
            if (s1_ != null) {
               cached.add(Arrays.asList(s1_.readElementNode_, s1_.isObjectNode_, s1_.isCallableNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructMapNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructMapNodeGen(context, builtin, isNewTargetCase, arguments);
      }

      @GeneratedBy(ConstructorBuiltins.ConstructMapNode.class)
      private static final class ConstructMapFromIterableData extends Node {
         @Node.Child
         ReadElementNode readElementNode_;
         @Node.Child
         IsObjectNode isObjectNode_;
         @Node.Child
         IsCallableNode isCallableNode_;

         ConstructMapFromIterableData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructNumberFormatNode.class)
   public static final class ConstructNumberFormatNodeGen extends ConstructorBuiltins.ConstructNumberFormatNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConstructNumberFormatNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, newTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructNumberFormat(arguments0Value__, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.constructNumberFormat(arguments0Value_, arguments1Value, arguments2Value);
         } else {
            throw new UnsupportedSpecializationException(
               this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
            );
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructNumberFormat", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructNumberFormatNode create(
         JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructNumberFormatNodeGen(context, builtin, newTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructNumberNode.class)
   public static final class ConstructNumberNodeGen extends ConstructorBuiltins.ConstructNumberNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToNumericNode constructNumber_toNumericNode_;
      @Node.Child
      private JSNumericToNumberNode constructNumber_toNumberFromNumericNode_;

      private ConstructNumberNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (arguments1Value_ instanceof Object[]) {
               Object[] arguments1Value__ = (Object[])arguments1Value_;
               if ((state_0 & 1) != 0 && arguments1Value__.length == 0) {
                  return this.constructNumberZero(arguments0Value__, arguments1Value__);
               }

               if ((state_0 & 2) != 0 && arguments1Value__.length > 0) {
                  return this.constructNumber(
                     arguments0Value__, arguments1Value__, this.constructNumber_toNumericNode_, this.constructNumber_toNumberFromNumericNode_
                  );
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

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (arguments1Value instanceof Object[]) {
                  Object[] arguments1Value_ = (Object[])arguments1Value;
                  if (arguments1Value_.length == 0) {
                     int var13;
                     this.state_0_ = var13 = state_0 | 1;
                     lock.unlock();
                     hasLock = false;
                     return this.constructNumberZero(arguments0Value_, arguments1Value_);
                  }

                  if (arguments1Value_.length > 0) {
                     this.constructNumber_toNumericNode_ = super.insert(JSToNumericNode.create());
                     this.constructNumber_toNumberFromNumericNode_ = super.insert(JSNumericToNumberNode.create());
                     int var12;
                     this.state_0_ = var12 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.constructNumber(
                        arguments0Value_, arguments1Value_, this.constructNumber_toNumericNode_, this.constructNumber_toNumberFromNumericNode_
                     );
                  }
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
         } finally {
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
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructNumberZero", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"constructNumber", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.constructNumber_toNumericNode_, this.constructNumber_toNumberFromNumericNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructNumberNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructNumberNodeGen(context, builtin, isNewTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructObjectNode.class)
   public static final class ConstructObjectNodeGen extends ConstructorBuiltins.ConstructObjectNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private ConstructorBuiltinsFactory.ConstructObjectNodeGen.ConstructObjectJSObject0Data constructObjectJSObject0_cache;
      @Node.Child
      private JSToObjectNode constructObjectJSObject1_toObjectNode_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile constructObjectJSObject1_isNull_;

      private ConstructObjectNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @ExplodeLoop
      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (arguments1Value_ instanceof Object[]) {
               Object[] arguments1Value__ = (Object[])arguments1Value_;
               if ((state_0 & 1) != 0) {
                  assert this.isNewTargetCase;

                  return this.constructObjectNewTarget(arguments0Value__, arguments1Value__);
               }

               if ((state_0 & 2) != 0 && arguments1Value__.length == 0) {
                  return this.constructObject0(arguments0Value__, arguments1Value__);
               }

               if ((state_0 & 4) != 0) {
                  for (ConstructorBuiltinsFactory.ConstructObjectNodeGen.ConstructObjectJSObject0Data s2_ = this.constructObjectJSObject0_cache;
                     s2_ != null;
                     s2_ = s2_.next_
                  ) {
                     if (s2_.interop_.accepts(ConstructorBuiltins.ConstructObjectNode.firstArgument(arguments1Value__))) {
                        assert !this.isNewTargetCase;

                        if (arguments1Value__.length > 0 && !ConstructorBuiltins.ConstructObjectNode.arg0NullOrUndefined(arguments1Value__)) {
                           return this.constructObjectJSObject(arguments0Value__, arguments1Value__, s2_.toObjectNode_, s2_.interop_, s2_.isNull_);
                        }
                     }
                  }
               }

               if ((state_0 & 8) != 0) {
                  assert !this.isNewTargetCase;

                  if (arguments1Value__.length > 0 && !ConstructorBuiltins.ConstructObjectNode.arg0NullOrUndefined(arguments1Value__)) {
                     return this.constructObjectJSObject1Boundary(state_0, arguments0Value__, arguments1Value__);
                  }
               }

               if ((state_0 & 16) != 0 && arguments1Value__.length > 0 && ConstructorBuiltins.ConstructObjectNode.arg0NullOrUndefined(arguments1Value__)) {
                  return this.constructObjectNullOrUndefined(arguments0Value__, arguments1Value__);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object constructObjectJSObject1Boundary(int state_0, JSDynamicObject arguments0Value__, Object[] arguments1Value__) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Object var7;
         try {
            InteropLibrary constructObjectJSObject1_interop__ = ConstructorBuiltinsFactory.INTEROP_LIBRARY_
               .getUncached(ConstructorBuiltins.ConstructObjectNode.firstArgument(arguments1Value__));
            var7 = this.constructObjectJSObject(
               arguments0Value__,
               arguments1Value__,
               this.constructObjectJSObject1_toObjectNode_,
               constructObjectJSObject1_interop__,
               this.constructObjectJSObject1_isNull_
            );
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
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
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (arguments1Value instanceof Object[]) {
                  Object[] arguments1Value_ = (Object[])arguments1Value;
                  if (this.isNewTargetCase) {
                     int var25;
                     this.state_0_ = var25 = state_0 | 1;
                     lock.unlock();
                     hasLock = false;
                     return this.constructObjectNewTarget(arguments0Value_, arguments1Value_);
                  }

                  if (arguments1Value_.length == 0) {
                     int var24;
                     this.state_0_ = var24 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.constructObject0(arguments0Value_, arguments1Value_);
                  }

                  if (exclude == 0) {
                     int count2_ = 0;
                     ConstructorBuiltinsFactory.ConstructObjectNodeGen.ConstructObjectJSObject0Data s2_ = this.constructObjectJSObject0_cache;
                     if ((state_0 & 4) != 0) {
                        while (s2_ != null) {
                           if (s2_.interop_.accepts(ConstructorBuiltins.ConstructObjectNode.firstArgument(arguments1Value_))) {
                              assert !this.isNewTargetCase;

                              if (arguments1Value_.length > 0 && !ConstructorBuiltins.ConstructObjectNode.arg0NullOrUndefined(arguments1Value_)) {
                                 break;
                              }
                           }

                           s2_ = s2_.next_;
                           count2_++;
                        }
                     }

                     if (s2_ == null
                        && !this.isNewTargetCase
                        && arguments1Value_.length > 0
                        && !ConstructorBuiltins.ConstructObjectNode.arg0NullOrUndefined(arguments1Value_)
                        && count2_ < 5) {
                        s2_ = super.insert(
                           new ConstructorBuiltinsFactory.ConstructObjectNodeGen.ConstructObjectJSObject0Data(this.constructObjectJSObject0_cache)
                        );
                        s2_.toObjectNode_ = s2_.insertAccessor(JSToObjectNode.createToObject(this.getContext()));
                        s2_.interop_ = s2_.insertAccessor(
                           ConstructorBuiltinsFactory.INTEROP_LIBRARY_.create(ConstructorBuiltins.ConstructObjectNode.firstArgument(arguments1Value_))
                        );
                        s2_.isNull_ = ConditionProfile.createBinaryProfile();
                        VarHandle.storeStoreFence();
                        this.constructObjectJSObject0_cache = s2_;
                        this.state_0_ = state_0 |= 4;
                     }

                     if (s2_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.constructObjectJSObject(arguments0Value_, arguments1Value_, s2_.toObjectNode_, s2_.interop_, s2_.isNull_);
                     }
                  }

                  InteropLibrary constructObjectJSObject1_interop__ = null;
                  EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                  Node prev_ = encapsulating_.set(this);

                  try {
                     if (!this.isNewTargetCase && arguments1Value_.length > 0 && !ConstructorBuiltins.ConstructObjectNode.arg0NullOrUndefined(arguments1Value_)
                        )
                      {
                        this.constructObjectJSObject1_toObjectNode_ = super.insert(JSToObjectNode.createToObject(this.getContext()));
                        constructObjectJSObject1_interop__ = ConstructorBuiltinsFactory.INTEROP_LIBRARY_
                           .getUncached(ConstructorBuiltins.ConstructObjectNode.firstArgument(arguments1Value_));
                        this.constructObjectJSObject1_isNull_ = ConditionProfile.createBinaryProfile();
                        int var26;
                        this.exclude_ = var26 = exclude | 1;
                        this.constructObjectJSObject0_cache = null;
                        state_0 &= -5;
                        int var23;
                        this.state_0_ = var23 = state_0 | 8;
                        lock.unlock();
                        hasLock = false;
                        return this.constructObjectJSObject(
                           arguments0Value_,
                           arguments1Value_,
                           this.constructObjectJSObject1_toObjectNode_,
                           constructObjectJSObject1_interop__,
                           this.constructObjectJSObject1_isNull_
                        );
                     }
                  } finally {
                     encapsulating_.set(prev_);
                  }

                  if (arguments1Value_.length > 0 && ConstructorBuiltins.ConstructObjectNode.arg0NullOrUndefined(arguments1Value_)) {
                     int var21;
                     this.state_0_ = var21 = state_0 | 16;
                     lock.unlock();
                     hasLock = false;
                     return this.constructObjectNullOrUndefined(arguments0Value_, arguments1Value_);
                  }
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
         } finally {
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
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               ConstructorBuiltinsFactory.ConstructObjectNodeGen.ConstructObjectJSObject0Data s2_ = this.constructObjectJSObject0_cache;
               if (s2_ == null || s2_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[6];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"constructObjectNewTarget", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"constructObject0", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"constructObjectJSObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (ConstructorBuiltinsFactory.ConstructObjectNodeGen.ConstructObjectJSObject0Data s2_ = this.constructObjectJSObject0_cache;
               s2_ != null;
               s2_ = s2_.next_
            ) {
               cached.add(Arrays.asList(s2_.toObjectNode_, s2_.interop_, s2_.isNull_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"constructObjectJSObject", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.constructObjectJSObject1_toObjectNode_, this.constructObjectJSObject1_isNull_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"constructObjectNullOrUndefined", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructObjectNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructObjectNodeGen(context, builtin, isNewTargetCase, arguments);
      }

      @GeneratedBy(ConstructorBuiltins.ConstructObjectNode.class)
      private static final class ConstructObjectJSObject0Data extends Node {
         @Node.Child
         ConstructorBuiltinsFactory.ConstructObjectNodeGen.ConstructObjectJSObject0Data next_;
         @Node.Child
         JSToObjectNode toObjectNode_;
         @Node.Child
         InteropLibrary interop_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile isNull_;

         ConstructObjectJSObject0Data(ConstructorBuiltinsFactory.ConstructObjectNodeGen.ConstructObjectJSObject0Data next_) {
            this.next_ = next_;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructPluralRulesNode.class)
   public static final class ConstructPluralRulesNodeGen extends ConstructorBuiltins.ConstructPluralRulesNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConstructPluralRulesNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, newTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructPluralRules(arguments0Value__, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.constructPluralRules(arguments0Value_, arguments1Value, arguments2Value);
         } else {
            throw new UnsupportedSpecializationException(
               this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
            );
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructPluralRules", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructPluralRulesNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructPluralRulesNodeGen(context, builtin, newTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructRegExpNode.class)
   public static final class ConstructRegExpNodeGen extends ConstructorBuiltins.ConstructRegExpNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private IsRegExpNode isRegExpNode_;

      private ConstructRegExpNodeGen(JSContext context, JSBuiltin builtin, boolean isCall, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isCall, isNewTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructRegExp(arguments0Value__, arguments1Value_, arguments2Value_, this.isRegExpNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var8;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSDynamicObject)) {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
               );
            }

            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            this.isRegExpNode_ = super.insert(IsRegExpNode.create(this.getContext()));
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.constructRegExp(arguments0Value_, arguments1Value, arguments2Value, this.isRegExpNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var8;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructRegExp", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.isRegExpNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructRegExpNode create(
         JSContext context, JSBuiltin builtin, boolean isCall, boolean isNewTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructRegExpNodeGen(context, builtin, isCall, isNewTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructRelativeTimeFormatNode.class)
   public static final class ConstructRelativeTimeFormatNodeGen extends ConstructorBuiltins.ConstructRelativeTimeFormatNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConstructRelativeTimeFormatNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, newTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructRelativeTimeFormat(arguments0Value__, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.constructRelativeTimeFormat(arguments0Value_, arguments1Value, arguments2Value);
         } else {
            throw new UnsupportedSpecializationException(
               this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
            );
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructRelativeTimeFormat", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructRelativeTimeFormatNode create(
         JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructRelativeTimeFormatNodeGen(context, builtin, newTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructSegmenterNode.class)
   public static final class ConstructSegmenterNodeGen extends ConstructorBuiltins.ConstructSegmenterNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConstructSegmenterNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, newTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructSegmenter(arguments0Value__, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.constructSegmenter(arguments0Value_, arguments1Value, arguments2Value);
         } else {
            throw new UnsupportedSpecializationException(
               this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
            );
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructSegmenter", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructSegmenterNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructSegmenterNodeGen(context, builtin, newTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructSetNode.class)
   public static final class ConstructSetNodeGen extends ConstructorBuiltins.ConstructSetNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private IsCallableNode constructSetFromIterable_isCallableNode_;

      private ConstructSetNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if ((state_0 & 1) != 0 && JSGuards.isNullOrUndefined(arguments1Value_)) {
               return this.constructEmptySet(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isNullOrUndefined(arguments1Value_)) {
               return this.constructSetFromIterable(arguments0Value__, arguments1Value_, this.constructSetFromIterable_isCallableNode_);
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
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isNullOrUndefined(arguments1Value)) {
                  int var12;
                  this.state_0_ = var12 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.constructEmptySet(arguments0Value_, arguments1Value);
               }

               if (!JSGuards.isNullOrUndefined(arguments1Value)) {
                  this.constructSetFromIterable_isCallableNode_ = super.insert(IsCallableNode.create());
                  int var11;
                  this.state_0_ = var11 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.constructSetFromIterable(arguments0Value_, arguments1Value, this.constructSetFromIterable_isCallableNode_);
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
         } finally {
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
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructEmptySet", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"constructSetFromIterable", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.constructSetFromIterable_isCallableNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructSetNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructSetNodeGen(context, builtin, isNewTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructStringNode.class)
   public static final class ConstructStringNodeGen extends ConstructorBuiltins.ConstructStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToStringNode constructString_toStringNode_;

      private ConstructStringNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, newTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (arguments1Value_ instanceof Object[]) {
               Object[] arguments1Value__ = (Object[])arguments1Value_;
               if ((state_0 & 1) != 0 && arguments1Value__.length == 0) {
                  return this.constructStringInt0(arguments0Value__, arguments1Value__);
               }

               if ((state_0 & 2) != 0 && arguments1Value__.length != 0) {
                  return this.constructString(arguments0Value__, arguments1Value__, this.constructString_toStringNode_);
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

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (arguments1Value instanceof Object[]) {
                  Object[] arguments1Value_ = (Object[])arguments1Value;
                  if (arguments1Value_.length == 0) {
                     int var13;
                     this.state_0_ = var13 = state_0 | 1;
                     lock.unlock();
                     hasLock = false;
                     return this.constructStringInt0(arguments0Value_, arguments1Value_);
                  }

                  if (arguments1Value_.length != 0) {
                     this.constructString_toStringNode_ = super.insert(JSToStringNode.create());
                     int var12;
                     this.state_0_ = var12 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.constructString(arguments0Value_, arguments1Value_, this.constructString_toStringNode_);
                  }
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
         } finally {
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
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructStringInt0", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"constructString", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.constructString_toStringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructStringNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructStringNodeGen(context, builtin, newTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructSymbolNode.class)
   public static final class ConstructSymbolNodeGen extends ConstructorBuiltins.ConstructSymbolNode implements Introspection.Provider {
      private ConstructSymbolNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         return ConstructorBuiltins.ConstructSymbolNode.construct();
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"construct", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructSymbolNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructSymbolNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructTemporalCalendar.class)
   public static final class ConstructTemporalCalendarNodeGen extends ConstructorBuiltins.ConstructTemporalCalendar implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile errorBranch_;
      @Node.Child
      private JSToStringNode toString_;

      private ConstructTemporalCalendarNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructTemporalCalendar(arguments0Value__, arguments1Value_, this.errorBranch_, this.toString_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var7;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSDynamicObject)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            }

            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            this.errorBranch_ = BranchProfile.create();
            this.toString_ = super.insert(JSToStringNode.create());
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.constructTemporalCalendar(arguments0Value_, arguments1Value, this.errorBranch_, this.toString_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var7;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructTemporalCalendar", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.errorBranch_, this.toString_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructTemporalCalendar create(
         JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructTemporalCalendarNodeGen(context, builtin, isNewTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructTemporalDurationNode.class)
   public static final class ConstructTemporalDurationNodeGen extends ConstructorBuiltins.ConstructTemporalDurationNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @Node.Child
      private JavaScriptNode arguments3_;
      @Node.Child
      private JavaScriptNode arguments4_;
      @Node.Child
      private JavaScriptNode arguments5_;
      @Node.Child
      private JavaScriptNode arguments6_;
      @Node.Child
      private JavaScriptNode arguments7_;
      @Node.Child
      private JavaScriptNode arguments8_;
      @Node.Child
      private JavaScriptNode arguments9_;
      @Node.Child
      private JavaScriptNode arguments10_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToIntegerWithoutRoundingNode toIntegerNode_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile errorBranch_;

      private ConstructTemporalDurationNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
         this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
         this.arguments4_ = arguments != null && 4 < arguments.length ? arguments[4] : null;
         this.arguments5_ = arguments != null && 5 < arguments.length ? arguments[5] : null;
         this.arguments6_ = arguments != null && 6 < arguments.length ? arguments[6] : null;
         this.arguments7_ = arguments != null && 7 < arguments.length ? arguments[7] : null;
         this.arguments8_ = arguments != null && 8 < arguments.length ? arguments[8] : null;
         this.arguments9_ = arguments != null && 9 < arguments.length ? arguments[9] : null;
         this.arguments10_ = arguments != null && 10 < arguments.length ? arguments[10] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{
            this.arguments0_,
            this.arguments1_,
            this.arguments2_,
            this.arguments3_,
            this.arguments4_,
            this.arguments5_,
            this.arguments6_,
            this.arguments7_,
            this.arguments8_,
            this.arguments9_,
            this.arguments10_
         };
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         Object arguments4Value_ = this.arguments4_.execute(frameValue);
         Object arguments5Value_ = this.arguments5_.execute(frameValue);
         Object arguments6Value_ = this.arguments6_.execute(frameValue);
         Object arguments7Value_ = this.arguments7_.execute(frameValue);
         Object arguments8Value_ = this.arguments8_.execute(frameValue);
         Object arguments9Value_ = this.arguments9_.execute(frameValue);
         Object arguments10Value_ = this.arguments10_.execute(frameValue);
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructTemporalDuration(
               arguments0Value__,
               arguments1Value_,
               arguments2Value_,
               arguments3Value_,
               arguments4Value_,
               arguments5Value_,
               arguments6Value_,
               arguments7Value_,
               arguments8Value_,
               arguments9Value_,
               arguments10Value_,
               this.toIntegerNode_,
               this.errorBranch_
            );
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(
               arguments0Value_,
               arguments1Value_,
               arguments2Value_,
               arguments3Value_,
               arguments4Value_,
               arguments5Value_,
               arguments6Value_,
               arguments7Value_,
               arguments8Value_,
               arguments9Value_,
               arguments10Value_
            );
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(
         Object arguments0Value,
         Object arguments1Value,
         Object arguments2Value,
         Object arguments3Value,
         Object arguments4Value,
         Object arguments5Value,
         Object arguments6Value,
         Object arguments7Value,
         Object arguments8Value,
         Object arguments9Value,
         Object arguments10Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var16;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSDynamicObject)) {
               throw new UnsupportedSpecializationException(
                  this,
                  new Node[]{
                     this.arguments0_,
                     this.arguments1_,
                     this.arguments2_,
                     this.arguments3_,
                     this.arguments4_,
                     this.arguments5_,
                     this.arguments6_,
                     this.arguments7_,
                     this.arguments8_,
                     this.arguments9_,
                     this.arguments10_
                  },
                  arguments0Value,
                  arguments1Value,
                  arguments2Value,
                  arguments3Value,
                  arguments4Value,
                  arguments5Value,
                  arguments6Value,
                  arguments7Value,
                  arguments8Value,
                  arguments9Value,
                  arguments10Value
               );
            }

            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            this.toIntegerNode_ = super.insert(JSToIntegerWithoutRoundingNode.create());
            this.errorBranch_ = BranchProfile.create();
            int var20;
            this.state_0_ = var20 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var16 = this.constructTemporalDuration(
               arguments0Value_,
               arguments1Value,
               arguments2Value,
               arguments3Value,
               arguments4Value,
               arguments5Value,
               arguments6Value,
               arguments7Value,
               arguments8Value,
               arguments9Value,
               arguments10Value,
               this.toIntegerNode_,
               this.errorBranch_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var16;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructTemporalDuration", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toIntegerNode_, this.errorBranch_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructTemporalDurationNode create(
         JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructTemporalDurationNodeGen(context, builtin, isNewTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructTemporalInstant.class)
   public static final class ConstructTemporalInstantNodeGen extends ConstructorBuiltins.ConstructTemporalInstant implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile errorBranch_;

      private ConstructTemporalInstantNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructTemporalInstant(arguments0Value__, arguments1Value_, this.errorBranch_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var7;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSDynamicObject)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            }

            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            this.errorBranch_ = BranchProfile.create();
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.constructTemporalInstant(arguments0Value_, arguments1Value, this.errorBranch_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var7;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructTemporalInstant", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.errorBranch_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructTemporalInstant create(
         JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructTemporalInstantNodeGen(context, builtin, isNewTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructTemporalPlainDateNode.class)
   public static final class ConstructTemporalPlainDateNodeGen extends ConstructorBuiltins.ConstructTemporalPlainDateNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @Node.Child
      private JavaScriptNode arguments3_;
      @Node.Child
      private JavaScriptNode arguments4_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ConstructorBuiltinsFactory.ConstructTemporalPlainDateNodeGen.ConstructTemporalPlainDateData constructTemporalPlainDate_cache;

      private ConstructTemporalPlainDateNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
         this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
         this.arguments4_ = arguments != null && 4 < arguments.length ? arguments[4] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         Object arguments4Value_ = this.arguments4_.execute(frameValue);
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            ConstructorBuiltinsFactory.ConstructTemporalPlainDateNodeGen.ConstructTemporalPlainDateData s0_ = this.constructTemporalPlainDate_cache;
            if (s0_ != null) {
               return this.constructTemporalPlainDate(
                  arguments0Value__,
                  arguments1Value_,
                  arguments2Value_,
                  arguments3Value_,
                  arguments4Value_,
                  s0_.toIntegerNode_,
                  s0_.toTemporalCalendarWithISODefaultNode_,
                  s0_.errorBranch_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, arguments4Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(
         Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value, Object arguments4Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var11;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSDynamicObject)) {
               throw new UnsupportedSpecializationException(
                  this,
                  new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_},
                  arguments0Value,
                  arguments1Value,
                  arguments2Value,
                  arguments3Value,
                  arguments4Value
               );
            }

            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            ConstructorBuiltinsFactory.ConstructTemporalPlainDateNodeGen.ConstructTemporalPlainDateData s0_ = super.insert(
               new ConstructorBuiltinsFactory.ConstructTemporalPlainDateNodeGen.ConstructTemporalPlainDateData()
            );
            s0_.toIntegerNode_ = s0_.insertAccessor(JSToIntegerThrowOnInfinityNode.create());
            s0_.toTemporalCalendarWithISODefaultNode_ = s0_.insertAccessor(ToTemporalCalendarWithISODefaultNode.create(this.getContext()));
            s0_.errorBranch_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.constructTemporalPlainDate_cache = s0_;
            int var15;
            this.state_0_ = var15 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var11 = this.constructTemporalPlainDate(
               arguments0Value_,
               arguments1Value,
               arguments2Value,
               arguments3Value,
               arguments4Value,
               s0_.toIntegerNode_,
               s0_.toTemporalCalendarWithISODefaultNode_,
               s0_.errorBranch_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var11;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructTemporalPlainDate", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            ConstructorBuiltinsFactory.ConstructTemporalPlainDateNodeGen.ConstructTemporalPlainDateData s0_ = this.constructTemporalPlainDate_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.toIntegerNode_, s0_.toTemporalCalendarWithISODefaultNode_, s0_.errorBranch_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructTemporalPlainDateNode create(
         JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructTemporalPlainDateNodeGen(context, builtin, isNewTargetCase, arguments);
      }

      @GeneratedBy(ConstructorBuiltins.ConstructTemporalPlainDateNode.class)
      private static final class ConstructTemporalPlainDateData extends Node {
         @Node.Child
         JSToIntegerThrowOnInfinityNode toIntegerNode_;
         @Node.Child
         ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode_;
         @CompilerDirectives.CompilationFinal
         BranchProfile errorBranch_;

         ConstructTemporalPlainDateData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructTemporalPlainDateTimeNode.class)
   public static final class ConstructTemporalPlainDateTimeNodeGen
      extends ConstructorBuiltins.ConstructTemporalPlainDateTimeNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @Node.Child
      private JavaScriptNode arguments3_;
      @Node.Child
      private JavaScriptNode arguments4_;
      @Node.Child
      private JavaScriptNode arguments5_;
      @Node.Child
      private JavaScriptNode arguments6_;
      @Node.Child
      private JavaScriptNode arguments7_;
      @Node.Child
      private JavaScriptNode arguments8_;
      @Node.Child
      private JavaScriptNode arguments9_;
      @Node.Child
      private JavaScriptNode arguments10_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ConstructorBuiltinsFactory.ConstructTemporalPlainDateTimeNodeGen.ConstructTemporalPlainDateTimeData constructTemporalPlainDateTime_cache;

      private ConstructTemporalPlainDateTimeNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
         this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
         this.arguments4_ = arguments != null && 4 < arguments.length ? arguments[4] : null;
         this.arguments5_ = arguments != null && 5 < arguments.length ? arguments[5] : null;
         this.arguments6_ = arguments != null && 6 < arguments.length ? arguments[6] : null;
         this.arguments7_ = arguments != null && 7 < arguments.length ? arguments[7] : null;
         this.arguments8_ = arguments != null && 8 < arguments.length ? arguments[8] : null;
         this.arguments9_ = arguments != null && 9 < arguments.length ? arguments[9] : null;
         this.arguments10_ = arguments != null && 10 < arguments.length ? arguments[10] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{
            this.arguments0_,
            this.arguments1_,
            this.arguments2_,
            this.arguments3_,
            this.arguments4_,
            this.arguments5_,
            this.arguments6_,
            this.arguments7_,
            this.arguments8_,
            this.arguments9_,
            this.arguments10_
         };
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         Object arguments4Value_ = this.arguments4_.execute(frameValue);
         Object arguments5Value_ = this.arguments5_.execute(frameValue);
         Object arguments6Value_ = this.arguments6_.execute(frameValue);
         Object arguments7Value_ = this.arguments7_.execute(frameValue);
         Object arguments8Value_ = this.arguments8_.execute(frameValue);
         Object arguments9Value_ = this.arguments9_.execute(frameValue);
         Object arguments10Value_ = this.arguments10_.execute(frameValue);
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            ConstructorBuiltinsFactory.ConstructTemporalPlainDateTimeNodeGen.ConstructTemporalPlainDateTimeData s0_ = this.constructTemporalPlainDateTime_cache;
            if (s0_ != null) {
               return this.constructTemporalPlainDateTime(
                  arguments0Value__,
                  arguments1Value_,
                  arguments2Value_,
                  arguments3Value_,
                  arguments4Value_,
                  arguments5Value_,
                  arguments6Value_,
                  arguments7Value_,
                  arguments8Value_,
                  arguments9Value_,
                  arguments10Value_,
                  s0_.toIntegerNode_,
                  s0_.toTemporalCalendarWithISODefaultNode_,
                  s0_.errorBranch_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            arguments0Value_,
            arguments1Value_,
            arguments2Value_,
            arguments3Value_,
            arguments4Value_,
            arguments5Value_,
            arguments6Value_,
            arguments7Value_,
            arguments8Value_,
            arguments9Value_,
            arguments10Value_
         );
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(
         Object arguments0Value,
         Object arguments1Value,
         Object arguments2Value,
         Object arguments3Value,
         Object arguments4Value,
         Object arguments5Value,
         Object arguments6Value,
         Object arguments7Value,
         Object arguments8Value,
         Object arguments9Value,
         Object arguments10Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var17;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSDynamicObject)) {
               throw new UnsupportedSpecializationException(
                  this,
                  new Node[]{
                     this.arguments0_,
                     this.arguments1_,
                     this.arguments2_,
                     this.arguments3_,
                     this.arguments4_,
                     this.arguments5_,
                     this.arguments6_,
                     this.arguments7_,
                     this.arguments8_,
                     this.arguments9_,
                     this.arguments10_
                  },
                  arguments0Value,
                  arguments1Value,
                  arguments2Value,
                  arguments3Value,
                  arguments4Value,
                  arguments5Value,
                  arguments6Value,
                  arguments7Value,
                  arguments8Value,
                  arguments9Value,
                  arguments10Value
               );
            }

            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            ConstructorBuiltinsFactory.ConstructTemporalPlainDateTimeNodeGen.ConstructTemporalPlainDateTimeData s0_ = super.insert(
               new ConstructorBuiltinsFactory.ConstructTemporalPlainDateTimeNodeGen.ConstructTemporalPlainDateTimeData()
            );
            s0_.toIntegerNode_ = s0_.insertAccessor(JSToIntegerThrowOnInfinityNode.create());
            s0_.toTemporalCalendarWithISODefaultNode_ = s0_.insertAccessor(ToTemporalCalendarWithISODefaultNode.create(this.getContext()));
            s0_.errorBranch_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.constructTemporalPlainDateTime_cache = s0_;
            int var21;
            this.state_0_ = var21 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var17 = this.constructTemporalPlainDateTime(
               arguments0Value_,
               arguments1Value,
               arguments2Value,
               arguments3Value,
               arguments4Value,
               arguments5Value,
               arguments6Value,
               arguments7Value,
               arguments8Value,
               arguments9Value,
               arguments10Value,
               s0_.toIntegerNode_,
               s0_.toTemporalCalendarWithISODefaultNode_,
               s0_.errorBranch_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var17;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructTemporalPlainDateTime", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            ConstructorBuiltinsFactory.ConstructTemporalPlainDateTimeNodeGen.ConstructTemporalPlainDateTimeData s0_ = this.constructTemporalPlainDateTime_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.toIntegerNode_, s0_.toTemporalCalendarWithISODefaultNode_, s0_.errorBranch_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructTemporalPlainDateTimeNode create(
         JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructTemporalPlainDateTimeNodeGen(context, builtin, isNewTargetCase, arguments);
      }

      @GeneratedBy(ConstructorBuiltins.ConstructTemporalPlainDateTimeNode.class)
      private static final class ConstructTemporalPlainDateTimeData extends Node {
         @Node.Child
         JSToIntegerThrowOnInfinityNode toIntegerNode_;
         @Node.Child
         ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode_;
         @CompilerDirectives.CompilationFinal
         BranchProfile errorBranch_;

         ConstructTemporalPlainDateTimeData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructTemporalPlainMonthDay.class)
   public static final class ConstructTemporalPlainMonthDayNodeGen extends ConstructorBuiltins.ConstructTemporalPlainMonthDay implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @Node.Child
      private JavaScriptNode arguments3_;
      @Node.Child
      private JavaScriptNode arguments4_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ConstructorBuiltinsFactory.ConstructTemporalPlainMonthDayNodeGen.ConstructTemporalPlainMonthDayData constructTemporalPlainMonthDay_cache;

      private ConstructTemporalPlainMonthDayNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
         this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
         this.arguments4_ = arguments != null && 4 < arguments.length ? arguments[4] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         Object arguments4Value_ = this.arguments4_.execute(frameValue);
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            ConstructorBuiltinsFactory.ConstructTemporalPlainMonthDayNodeGen.ConstructTemporalPlainMonthDayData s0_ = this.constructTemporalPlainMonthDay_cache;
            if (s0_ != null) {
               return this.constructTemporalPlainMonthDay(
                  arguments0Value__,
                  arguments1Value_,
                  arguments2Value_,
                  arguments3Value_,
                  arguments4Value_,
                  s0_.errorBranch_,
                  s0_.toInt_,
                  s0_.toTemporalCalendarWithISODefaultNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, arguments4Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(
         Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value, Object arguments4Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var11;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSDynamicObject)) {
               throw new UnsupportedSpecializationException(
                  this,
                  new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_},
                  arguments0Value,
                  arguments1Value,
                  arguments2Value,
                  arguments3Value,
                  arguments4Value
               );
            }

            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            ConstructorBuiltinsFactory.ConstructTemporalPlainMonthDayNodeGen.ConstructTemporalPlainMonthDayData s0_ = super.insert(
               new ConstructorBuiltinsFactory.ConstructTemporalPlainMonthDayNodeGen.ConstructTemporalPlainMonthDayData()
            );
            s0_.errorBranch_ = BranchProfile.create();
            s0_.toInt_ = s0_.insertAccessor(JSToIntegerThrowOnInfinityNode.create());
            s0_.toTemporalCalendarWithISODefaultNode_ = s0_.insertAccessor(ToTemporalCalendarWithISODefaultNode.create(this.getContext()));
            VarHandle.storeStoreFence();
            this.constructTemporalPlainMonthDay_cache = s0_;
            int var15;
            this.state_0_ = var15 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var11 = this.constructTemporalPlainMonthDay(
               arguments0Value_,
               arguments1Value,
               arguments2Value,
               arguments3Value,
               arguments4Value,
               s0_.errorBranch_,
               s0_.toInt_,
               s0_.toTemporalCalendarWithISODefaultNode_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var11;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructTemporalPlainMonthDay", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            ConstructorBuiltinsFactory.ConstructTemporalPlainMonthDayNodeGen.ConstructTemporalPlainMonthDayData s0_ = this.constructTemporalPlainMonthDay_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.errorBranch_, s0_.toInt_, s0_.toTemporalCalendarWithISODefaultNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructTemporalPlainMonthDay create(
         JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructTemporalPlainMonthDayNodeGen(context, builtin, isNewTargetCase, arguments);
      }

      @GeneratedBy(ConstructorBuiltins.ConstructTemporalPlainMonthDay.class)
      private static final class ConstructTemporalPlainMonthDayData extends Node {
         @CompilerDirectives.CompilationFinal
         BranchProfile errorBranch_;
         @Node.Child
         JSToIntegerThrowOnInfinityNode toInt_;
         @Node.Child
         ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode_;

         ConstructTemporalPlainMonthDayData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructTemporalPlainTimeNode.class)
   public static final class ConstructTemporalPlainTimeNodeGen extends ConstructorBuiltins.ConstructTemporalPlainTimeNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @Node.Child
      private JavaScriptNode arguments3_;
      @Node.Child
      private JavaScriptNode arguments4_;
      @Node.Child
      private JavaScriptNode arguments5_;
      @Node.Child
      private JavaScriptNode arguments6_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile errorBranch_;
      @Node.Child
      private JSToIntegerThrowOnInfinityNode toIntegerNode_;

      private ConstructTemporalPlainTimeNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
         this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
         this.arguments4_ = arguments != null && 4 < arguments.length ? arguments[4] : null;
         this.arguments5_ = arguments != null && 5 < arguments.length ? arguments[5] : null;
         this.arguments6_ = arguments != null && 6 < arguments.length ? arguments[6] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{
            this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_, this.arguments5_, this.arguments6_
         };
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         Object arguments4Value_ = this.arguments4_.execute(frameValue);
         Object arguments5Value_ = this.arguments5_.execute(frameValue);
         Object arguments6Value_ = this.arguments6_.execute(frameValue);
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructTemporalPlainTime(
               arguments0Value__,
               arguments1Value_,
               arguments2Value_,
               arguments3Value_,
               arguments4Value_,
               arguments5Value_,
               arguments6Value_,
               this.errorBranch_,
               this.toIntegerNode_
            );
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(
               arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, arguments4Value_, arguments5Value_, arguments6Value_
            );
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(
         Object arguments0Value,
         Object arguments1Value,
         Object arguments2Value,
         Object arguments3Value,
         Object arguments4Value,
         Object arguments5Value,
         Object arguments6Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var12;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSDynamicObject)) {
               throw new UnsupportedSpecializationException(
                  this,
                  new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_, this.arguments5_, this.arguments6_},
                  arguments0Value,
                  arguments1Value,
                  arguments2Value,
                  arguments3Value,
                  arguments4Value,
                  arguments5Value,
                  arguments6Value
               );
            }

            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            this.errorBranch_ = BranchProfile.create();
            this.toIntegerNode_ = super.insert(JSToIntegerThrowOnInfinityNode.create());
            int var16;
            this.state_0_ = var16 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var12 = this.constructTemporalPlainTime(
               arguments0Value_,
               arguments1Value,
               arguments2Value,
               arguments3Value,
               arguments4Value,
               arguments5Value,
               arguments6Value,
               this.errorBranch_,
               this.toIntegerNode_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var12;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructTemporalPlainTime", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.errorBranch_, this.toIntegerNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructTemporalPlainTimeNode create(
         JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructTemporalPlainTimeNodeGen(context, builtin, isNewTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructTemporalPlainYearMonth.class)
   public static final class ConstructTemporalPlainYearMonthNodeGen
      extends ConstructorBuiltins.ConstructTemporalPlainYearMonth
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @Node.Child
      private JavaScriptNode arguments3_;
      @Node.Child
      private JavaScriptNode arguments4_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ConstructorBuiltinsFactory.ConstructTemporalPlainYearMonthNodeGen.ConstructTemporalPlainYearMonthData constructTemporalPlainYearMonth_cache;

      private ConstructTemporalPlainYearMonthNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
         this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
         this.arguments4_ = arguments != null && 4 < arguments.length ? arguments[4] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         Object arguments4Value_ = this.arguments4_.execute(frameValue);
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            ConstructorBuiltinsFactory.ConstructTemporalPlainYearMonthNodeGen.ConstructTemporalPlainYearMonthData s0_ = this.constructTemporalPlainYearMonth_cache;
            if (s0_ != null) {
               return this.constructTemporalPlainYearMonth(
                  arguments0Value__,
                  arguments1Value_,
                  arguments2Value_,
                  arguments3Value_,
                  arguments4Value_,
                  s0_.errorBranch_,
                  s0_.toInteger_,
                  s0_.toTemporalCalendarWithISODefaultNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, arguments4Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(
         Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value, Object arguments4Value
      ) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var11;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSDynamicObject)) {
               throw new UnsupportedSpecializationException(
                  this,
                  new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_, this.arguments4_},
                  arguments0Value,
                  arguments1Value,
                  arguments2Value,
                  arguments3Value,
                  arguments4Value
               );
            }

            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            ConstructorBuiltinsFactory.ConstructTemporalPlainYearMonthNodeGen.ConstructTemporalPlainYearMonthData s0_ = super.insert(
               new ConstructorBuiltinsFactory.ConstructTemporalPlainYearMonthNodeGen.ConstructTemporalPlainYearMonthData()
            );
            s0_.errorBranch_ = BranchProfile.create();
            s0_.toInteger_ = s0_.insertAccessor(JSToIntegerThrowOnInfinityNode.create());
            s0_.toTemporalCalendarWithISODefaultNode_ = s0_.insertAccessor(ToTemporalCalendarWithISODefaultNode.create(this.getContext()));
            VarHandle.storeStoreFence();
            this.constructTemporalPlainYearMonth_cache = s0_;
            int var15;
            this.state_0_ = var15 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var11 = this.constructTemporalPlainYearMonth(
               arguments0Value_,
               arguments1Value,
               arguments2Value,
               arguments3Value,
               arguments4Value,
               s0_.errorBranch_,
               s0_.toInteger_,
               s0_.toTemporalCalendarWithISODefaultNode_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var11;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructTemporalPlainYearMonth", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            ConstructorBuiltinsFactory.ConstructTemporalPlainYearMonthNodeGen.ConstructTemporalPlainYearMonthData s0_ = this.constructTemporalPlainYearMonth_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.errorBranch_, s0_.toInteger_, s0_.toTemporalCalendarWithISODefaultNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructTemporalPlainYearMonth create(
         JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructTemporalPlainYearMonthNodeGen(context, builtin, isNewTargetCase, arguments);
      }

      @GeneratedBy(ConstructorBuiltins.ConstructTemporalPlainYearMonth.class)
      private static final class ConstructTemporalPlainYearMonthData extends Node {
         @CompilerDirectives.CompilationFinal
         BranchProfile errorBranch_;
         @Node.Child
         JSToIntegerThrowOnInfinityNode toInteger_;
         @Node.Child
         ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode_;

         ConstructTemporalPlainYearMonthData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructTemporalTimeZone.class)
   public static final class ConstructTemporalTimeZoneNodeGen extends ConstructorBuiltins.ConstructTemporalTimeZone implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToStringNode toStringNode_;

      private ConstructTemporalTimeZoneNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructTemporalTimeZone(arguments0Value__, arguments1Value_, this.toStringNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var7;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSDynamicObject)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            }

            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            this.toStringNode_ = super.insert(JSToStringNode.create());
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.constructTemporalTimeZone(arguments0Value_, arguments1Value, this.toStringNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var7;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructTemporalTimeZone", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toStringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructTemporalTimeZone create(
         JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructTemporalTimeZoneNodeGen(context, builtin, isNewTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructTemporalZonedDateTime.class)
   public static final class ConstructTemporalZonedDateTimeNodeGen extends ConstructorBuiltins.ConstructTemporalZonedDateTime implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @Node.Child
      private JavaScriptNode arguments3_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ConstructorBuiltinsFactory.ConstructTemporalZonedDateTimeNodeGen.ConstructTemporalZonedDateTimeData constructTemporalZonedDateTime_cache;

      private ConstructTemporalZonedDateTimeNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
         this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            ConstructorBuiltinsFactory.ConstructTemporalZonedDateTimeNodeGen.ConstructTemporalZonedDateTimeData s0_ = this.constructTemporalZonedDateTime_cache;
            if (s0_ != null) {
               return this.constructTemporalZonedDateTime(
                  arguments0Value__,
                  arguments1Value_,
                  arguments2Value_,
                  arguments3Value_,
                  s0_.toTemporalTimeZone_,
                  s0_.toTemporalCalendarWithISODefaultNode_,
                  s0_.toBigIntNode_,
                  s0_.errorBranch_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var10;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSDynamicObject)) {
               throw new UnsupportedSpecializationException(
                  this,
                  new Node[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_},
                  arguments0Value,
                  arguments1Value,
                  arguments2Value,
                  arguments3Value
               );
            }

            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            ConstructorBuiltinsFactory.ConstructTemporalZonedDateTimeNodeGen.ConstructTemporalZonedDateTimeData s0_ = super.insert(
               new ConstructorBuiltinsFactory.ConstructTemporalZonedDateTimeNodeGen.ConstructTemporalZonedDateTimeData()
            );
            s0_.toTemporalTimeZone_ = s0_.insertAccessor(ToTemporalTimeZoneNode.create(this.getContext()));
            s0_.toTemporalCalendarWithISODefaultNode_ = s0_.insertAccessor(ToTemporalCalendarWithISODefaultNode.create(this.getContext()));
            s0_.toBigIntNode_ = s0_.insertAccessor(JSToBigIntNode.create());
            s0_.errorBranch_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.constructTemporalZonedDateTime_cache = s0_;
            int var14;
            this.state_0_ = var14 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var10 = this.constructTemporalZonedDateTime(
               arguments0Value_,
               arguments1Value,
               arguments2Value,
               arguments3Value,
               s0_.toTemporalTimeZone_,
               s0_.toTemporalCalendarWithISODefaultNode_,
               s0_.toBigIntNode_,
               s0_.errorBranch_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var10;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructTemporalZonedDateTime", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            ConstructorBuiltinsFactory.ConstructTemporalZonedDateTimeNodeGen.ConstructTemporalZonedDateTimeData s0_ = this.constructTemporalZonedDateTime_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.toTemporalTimeZone_, s0_.toTemporalCalendarWithISODefaultNode_, s0_.toBigIntNode_, s0_.errorBranch_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructTemporalZonedDateTime create(
         JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructTemporalZonedDateTimeNodeGen(context, builtin, isNewTargetCase, arguments);
      }

      @GeneratedBy(ConstructorBuiltins.ConstructTemporalZonedDateTime.class)
      private static final class ConstructTemporalZonedDateTimeData extends Node {
         @Node.Child
         ToTemporalTimeZoneNode toTemporalTimeZone_;
         @Node.Child
         ToTemporalCalendarWithISODefaultNode toTemporalCalendarWithISODefaultNode_;
         @Node.Child
         JSToBigIntNode toBigIntNode_;
         @CompilerDirectives.CompilationFinal
         BranchProfile errorBranch_;

         ConstructTemporalZonedDateTimeData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructWeakMapNode.class)
   public static final class ConstructWeakMapNodeGen extends ConstructorBuiltins.ConstructWeakMapNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ConstructorBuiltinsFactory.ConstructWeakMapNodeGen.ConstructMapFromIterableData constructMapFromIterable_cache;

      private ConstructWeakMapNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if ((state_0 & 1) != 0 && JSGuards.isNullOrUndefined(arguments1Value_)) {
               return this.constructEmptyMap(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0) {
               ConstructorBuiltinsFactory.ConstructWeakMapNodeGen.ConstructMapFromIterableData s1_ = this.constructMapFromIterable_cache;
               if (s1_ != null && !JSGuards.isNullOrUndefined(arguments1Value_)) {
                  return this.constructMapFromIterable(arguments0Value__, arguments1Value_, s1_.readElementNode_, s1_.isObjectNode_, s1_.isCallableNode_);
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

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isNullOrUndefined(arguments1Value)) {
                  int var13;
                  this.state_0_ = var13 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.constructEmptyMap(arguments0Value_, arguments1Value);
               }

               if (!JSGuards.isNullOrUndefined(arguments1Value)) {
                  ConstructorBuiltinsFactory.ConstructWeakMapNodeGen.ConstructMapFromIterableData s1_ = super.insert(
                     new ConstructorBuiltinsFactory.ConstructWeakMapNodeGen.ConstructMapFromIterableData()
                  );
                  s1_.readElementNode_ = s1_.insertAccessor(ReadElementNode.create(this.getContext()));
                  s1_.isObjectNode_ = s1_.insertAccessor(IsObjectNode.create());
                  s1_.isCallableNode_ = s1_.insertAccessor(IsCallableNode.create());
                  VarHandle.storeStoreFence();
                  this.constructMapFromIterable_cache = s1_;
                  int var12;
                  this.state_0_ = var12 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.constructMapFromIterable(arguments0Value_, arguments1Value, s1_.readElementNode_, s1_.isObjectNode_, s1_.isCallableNode_);
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
         } finally {
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
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructEmptyMap", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"constructMapFromIterable", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            ConstructorBuiltinsFactory.ConstructWeakMapNodeGen.ConstructMapFromIterableData s1_ = this.constructMapFromIterable_cache;
            if (s1_ != null) {
               cached.add(Arrays.asList(s1_.readElementNode_, s1_.isObjectNode_, s1_.isCallableNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructWeakMapNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructWeakMapNodeGen(context, builtin, isNewTargetCase, arguments);
      }

      @GeneratedBy(ConstructorBuiltins.ConstructWeakMapNode.class)
      private static final class ConstructMapFromIterableData extends Node {
         @Node.Child
         ReadElementNode readElementNode_;
         @Node.Child
         IsObjectNode isObjectNode_;
         @Node.Child
         IsCallableNode isCallableNode_;

         ConstructMapFromIterableData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructWeakRefNode.class)
   public static final class ConstructWeakRefNodeGen extends ConstructorBuiltins.ConstructWeakRefNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConstructWeakRefNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, newTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if ((state_0 & 1) != 0 && JSGuards.isJSObject(arguments1Value_)) {
               return this.constructWeakRef(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments1Value_)) {
               return this.constructWeakRefNonObject(arguments0Value__, arguments1Value_);
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
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if (JSGuards.isJSObject(arguments1Value)) {
               int var6;
               this.state_0_ = var6 = state_0 | 1;
               return this.constructWeakRef(arguments0Value_, arguments1Value);
            }

            if (!JSGuards.isJSObject(arguments1Value)) {
               int var5;
               this.state_0_ = var5 = state_0 | 2;
               return this.constructWeakRefNonObject(arguments0Value_, arguments1Value);
            }
         }

         throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructWeakRef", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"constructWeakRefNonObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructWeakRefNode create(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructWeakRefNodeGen(context, builtin, newTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructWeakSetNode.class)
   public static final class ConstructWeakSetNodeGen extends ConstructorBuiltins.ConstructWeakSetNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private IsCallableNode constructSetFromIterable_isCallableNode_;

      private ConstructWeakSetNodeGen(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, isNewTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if ((state_0 & 1) != 0 && JSGuards.isNullOrUndefined(arguments1Value_)) {
               return this.constructEmptySet(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isNullOrUndefined(arguments1Value_)) {
               return this.constructSetFromIterable(arguments0Value__, arguments1Value_, this.constructSetFromIterable_isCallableNode_);
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
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isNullOrUndefined(arguments1Value)) {
                  int var12;
                  this.state_0_ = var12 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.constructEmptySet(arguments0Value_, arguments1Value);
               }

               if (!JSGuards.isNullOrUndefined(arguments1Value)) {
                  this.constructSetFromIterable_isCallableNode_ = super.insert(IsCallableNode.create());
                  int var11;
                  this.state_0_ = var11 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.constructSetFromIterable(arguments0Value_, arguments1Value, this.constructSetFromIterable_isCallableNode_);
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
         } finally {
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
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructEmptySet", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"constructSetFromIterable", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.constructSetFromIterable_isCallableNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructWeakSetNode create(JSContext context, JSBuiltin builtin, boolean isNewTargetCase, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.ConstructWeakSetNodeGen(context, builtin, isNewTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructWebAssemblyGlobalNode.class)
   public static final class ConstructWebAssemblyGlobalNodeGen extends ConstructorBuiltins.ConstructWebAssemblyGlobalNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConstructWebAssemblyGlobalNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, newTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructGlobal(arguments0Value__, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.constructGlobal(arguments0Value_, arguments1Value, arguments2Value);
         } else {
            throw new UnsupportedSpecializationException(
               this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
            );
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructGlobal", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructWebAssemblyGlobalNode create(
         JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructWebAssemblyGlobalNodeGen(context, builtin, newTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructWebAssemblyInstanceNode.class)
   public static final class ConstructWebAssemblyInstanceNodeGen extends ConstructorBuiltins.ConstructWebAssemblyInstanceNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConstructWebAssemblyInstanceNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, newTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSWebAssemblyModuleObject) {
               JSWebAssemblyModuleObject arguments1Value__ = (JSWebAssemblyModuleObject)arguments1Value_;
               return this.constructInstanceFromModule(arguments0Value__, arguments1Value__, arguments2Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSWebAssemblyModule(arguments1Value_)) {
               return this.constructInstanceFromOther(arguments0Value__, arguments1Value_, arguments2Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if (arguments1Value instanceof JSWebAssemblyModuleObject) {
               JSWebAssemblyModuleObject arguments1Value_ = (JSWebAssemblyModuleObject)arguments1Value;
               int var8;
               this.state_0_ = var8 = state_0 | 1;
               return this.constructInstanceFromModule(arguments0Value_, arguments1Value_, arguments2Value);
            }

            if (!JSGuards.isJSWebAssemblyModule(arguments1Value)) {
               int var7;
               this.state_0_ = var7 = state_0 | 2;
               return this.constructInstanceFromOther(arguments0Value_, arguments1Value, arguments2Value);
            }
         }

         throw new UnsupportedSpecializationException(
            this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
         );
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
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructInstanceFromModule", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"constructInstanceFromOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructWebAssemblyInstanceNode create(
         JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructWebAssemblyInstanceNodeGen(context, builtin, newTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructWebAssemblyMemoryNode.class)
   public static final class ConstructWebAssemblyMemoryNodeGen extends ConstructorBuiltins.ConstructWebAssemblyMemoryNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConstructWebAssemblyMemoryNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, newTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructMemory(arguments0Value__, arguments1Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.constructMemory(arguments0Value_, arguments1Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructMemory", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructWebAssemblyMemoryNode create(
         JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructWebAssemblyMemoryNodeGen(context, builtin, newTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructWebAssemblyModuleNode.class)
   public static final class ConstructWebAssemblyModuleNodeGen extends ConstructorBuiltins.ConstructWebAssemblyModuleNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ConstructWebAssemblyModuleNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, newTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructModule(arguments0Value__, arguments1Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.constructModule(arguments0Value_, arguments1Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructModule", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructWebAssemblyModuleNode create(
         JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructWebAssemblyModuleNodeGen(context, builtin, newTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.ConstructWebAssemblyTableNode.class)
   public static final class ConstructWebAssemblyTableNodeGen extends ConstructorBuiltins.ConstructWebAssemblyTableNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.EqualNode stringEqualsNode_;

      private ConstructWebAssemblyTableNodeGen(JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments) {
         super(context, builtin, newTargetCase);
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.constructTable(arguments0Value__, arguments1Value_, this.stringEqualsNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var7;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSDynamicObject)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            }

            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            this.stringEqualsNode_ = super.insert(TruffleString.EqualNode.create());
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.constructTable(arguments0Value_, arguments1Value, this.stringEqualsNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var7;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"constructTable", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.stringEqualsNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.ConstructWebAssemblyTableNode create(
         JSContext context, JSBuiltin builtin, boolean newTargetCase, JavaScriptNode[] arguments
      ) {
         return new ConstructorBuiltinsFactory.ConstructWebAssemblyTableNodeGen(context, builtin, newTargetCase, arguments);
      }
   }

   @GeneratedBy(ConstructorBuiltins.CreateDynamicFunctionNode.class)
   static final class CreateDynamicFunctionNodeGen extends ConstructorBuiltins.CreateDynamicFunctionNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private ConstructorBuiltinsFactory.CreateDynamicFunctionNodeGen.CachedData cached_cache;
      @CompilerDirectives.CompilationFinal
      private LRUCache<ConstructorBuiltins.CreateDynamicFunctionNode.CachedSourceKey, ScriptNode> uncached_cache_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile uncached_cacheHit_;

      private CreateDynamicFunctionNodeGen(JSContext context, boolean generatorFunction, boolean asyncFunction) {
         super(context, generatorFunction, asyncFunction);
      }

      @Override
      protected JSDynamicObject executeFunction(String arg0Value, String arg1Value, String arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               ConstructorBuiltinsFactory.CreateDynamicFunctionNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ != null
                  && ConstructorBuiltins.CreateDynamicFunctionNode.equals(s0_.cachedParamList_, arg0Value)
                  && ConstructorBuiltins.CreateDynamicFunctionNode.equals(s0_.cachedBody_, arg1Value)
                  && ConstructorBuiltins.CreateDynamicFunctionNode.equals(s0_.cachedSourceName_, arg2Value)) {
                  return this.doCached(arg0Value, arg1Value, arg2Value, s0_.cachedParamList_, s0_.cachedBody_, s0_.cachedSourceName_, s0_.cachedParsedFunction_);
               }
            }

            if ((state_0 & 2) != 0) {
               return this.doUncached(arg0Value, arg1Value, arg2Value, this.uncached_cache_, this.uncached_cacheHit_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private JSDynamicObject executeAndSpecialize(String arg0Value, String arg1Value, String arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               ConstructorBuiltinsFactory.CreateDynamicFunctionNodeGen.CachedData s0_ = this.cached_cache;
               boolean Cached_duplicateFound_ = false;
               if ((state_0 & 1) != 0
                  && ConstructorBuiltins.CreateDynamicFunctionNode.equals(s0_.cachedParamList_, arg0Value)
                  && ConstructorBuiltins.CreateDynamicFunctionNode.equals(s0_.cachedBody_, arg1Value)
                  && ConstructorBuiltins.CreateDynamicFunctionNode.equals(s0_.cachedSourceName_, arg2Value)) {
                  Cached_duplicateFound_ = true;
               }

               if (!Cached_duplicateFound_
                  && ConstructorBuiltins.CreateDynamicFunctionNode.equals(arg0Value, arg0Value)
                  && ConstructorBuiltins.CreateDynamicFunctionNode.equals(arg1Value, arg1Value)
                  && ConstructorBuiltins.CreateDynamicFunctionNode.equals(arg2Value, arg2Value)
                  && (state_0 & 1) == 0) {
                  s0_ = new ConstructorBuiltinsFactory.CreateDynamicFunctionNodeGen.CachedData();
                  s0_.cachedParamList_ = arg0Value;
                  s0_.cachedBody_ = arg1Value;
                  s0_.cachedSourceName_ = arg2Value;
                  s0_.cachedParsedFunction_ = this.createAssumedValue();
                  VarHandle.storeStoreFence();
                  this.cached_cache = s0_;
                  this.state_0_ = state_0 |= 1;
                  Cached_duplicateFound_ = true;
               }

               if (Cached_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(arg0Value, arg1Value, arg2Value, s0_.cachedParamList_, s0_.cachedBody_, s0_.cachedSourceName_, s0_.cachedParsedFunction_);
               }
            }

            this.uncached_cache_ = this.createCache();
            this.uncached_cacheHit_ = ConditionProfile.createCountingProfile();
            int var18;
            this.exclude_ = var18 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var17;
            this.state_0_ = var17 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doUncached(arg0Value, arg1Value, arg2Value, this.uncached_cache_, this.uncached_cacheHit_);
         } finally {
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
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"doCached", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            ConstructorBuiltinsFactory.CreateDynamicFunctionNodeGen.CachedData s0_ = this.cached_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.cachedParamList_, s0_.cachedBody_, s0_.cachedSourceName_, s0_.cachedParsedFunction_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doUncached", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.uncached_cache_, this.uncached_cacheHit_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.CreateDynamicFunctionNode create(JSContext context, boolean generatorFunction, boolean asyncFunction) {
         return new ConstructorBuiltinsFactory.CreateDynamicFunctionNodeGen(context, generatorFunction, asyncFunction);
      }

      @GeneratedBy(ConstructorBuiltins.CreateDynamicFunctionNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         String cachedParamList_;
         @CompilerDirectives.CompilationFinal
         String cachedBody_;
         @CompilerDirectives.CompilationFinal
         String cachedSourceName_;
         @CompilerDirectives.CompilationFinal
         AssumedValue<ScriptNode> cachedParsedFunction_;

         CachedData() {
         }
      }
   }

   @GeneratedBy(ConstructorBuiltins.PromiseConstructorNode.class)
   public static final class PromiseConstructorNodeGen extends ConstructorBuiltins.PromiseConstructorNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private PromiseConstructorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if (state_0 != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if ((state_0 & 1) != 0 && this.isCallable.executeBoolean(arguments1Value_)) {
               return this.construct(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !this.isCallable.executeBoolean(arguments1Value_)) {
               return this.notCallable(arguments0Value__, arguments1Value_);
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
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if (this.isCallable.executeBoolean(arguments1Value)) {
               int var6;
               this.state_0_ = var6 = state_0 | 1;
               return this.construct(arguments0Value_, arguments1Value);
            }

            if (!this.isCallable.executeBoolean(arguments1Value)) {
               int var5;
               this.state_0_ = var5 = state_0 | 2;
               return this.notCallable(arguments0Value_, arguments1Value);
            }
         }

         throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"construct", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"notCallable", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ConstructorBuiltins.PromiseConstructorNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConstructorBuiltinsFactory.PromiseConstructorNodeGen(context, builtin, arguments);
      }
   }
}
