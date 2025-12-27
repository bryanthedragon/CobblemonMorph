package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToDoubleNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(GlobalBuiltins.class)
public final class GlobalBuiltinsFactory {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   @GeneratedBy(GlobalBuiltins.GlobalNashornExtensionParseToJSONNode.class)
   public static final class GlobalNashornExtensionParseToJSONNodeGen
      extends GlobalBuiltins.GlobalNashornExtensionParseToJSONNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;

      private GlobalNashornExtensionParseToJSONNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         return this.parseToJSON(arguments0Value_, arguments1Value_, arguments2Value_);
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
         Object[] s = new Object[]{"parseToJSON", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static GlobalBuiltins.GlobalNashornExtensionParseToJSONNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new GlobalBuiltinsFactory.GlobalNashornExtensionParseToJSONNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(GlobalBuiltins.GlobalScriptingEXECNode.class)
   public static final class GlobalScriptingEXECNodeGen extends GlobalBuiltins.GlobalScriptingEXECNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private GlobalScriptingEXECNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.exec(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"exec", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static GlobalBuiltins.GlobalScriptingEXECNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new GlobalBuiltinsFactory.GlobalScriptingEXECNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(GlobalBuiltins.JSGlobalDecodeURINode.class)
   public static final class JSGlobalDecodeURINodeGen extends GlobalBuiltins.JSGlobalDecodeURINode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSGlobalDecodeURINodeGen(JSContext context, JSBuiltin builtin, boolean isSpecial, JavaScriptNode[] arguments) {
         super(context, builtin, isSpecial);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.decodeURI(arguments0Value_);
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
         Object[] s = new Object[]{"decodeURI", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static GlobalBuiltins.JSGlobalDecodeURINode create(JSContext context, JSBuiltin builtin, boolean isSpecial, JavaScriptNode[] arguments) {
         return new GlobalBuiltinsFactory.JSGlobalDecodeURINodeGen(context, builtin, isSpecial, arguments);
      }
   }

   @GeneratedBy(GlobalBuiltins.JSGlobalEncodeURINode.class)
   public static final class JSGlobalEncodeURINodeGen extends GlobalBuiltins.JSGlobalEncodeURINode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSGlobalEncodeURINodeGen(JSContext context, JSBuiltin builtin, boolean isSpecial, JavaScriptNode[] arguments) {
         super(context, builtin, isSpecial);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.encodeURI(arguments0Value_);
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
         Object[] s = new Object[]{"encodeURI", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static GlobalBuiltins.JSGlobalEncodeURINode create(JSContext context, JSBuiltin builtin, boolean isSpecial, JavaScriptNode[] arguments) {
         return new GlobalBuiltinsFactory.JSGlobalEncodeURINodeGen(context, builtin, isSpecial, arguments);
      }
   }

   @GeneratedBy(GlobalBuiltins.JSGlobalExitNode.class)
   public static final class JSGlobalExitNodeGen extends GlobalBuiltins.JSGlobalExitNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToNumberNode exit2_toNumberNode_;

      private JSGlobalExitNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return (state_0 & 5) == 0 && state_0 != 0 ? this.execute_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
      }

      private Object execute_int0(int state_0, VirtualFrame frameValue) {
         int arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var5.getResult());
         }

         assert (state_0 & 2) != 0;

         return this.exit(arguments0Value_);
      }

      private Object execute_generic1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && JSGuards.isUndefined(arguments0Value_)) {
            return this.exit(arguments0Value_);
         } else if ((state_0 & 2) != 0 && arguments0Value_ instanceof Integer) {
            int arguments0Value__ = (Integer)arguments0Value_;
            return this.exit(arguments0Value__);
         } else if ((state_0 & 4) != 0) {
            return this.exit(arguments0Value_, this.exit2_toNumberNode_);
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
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var6;
         try {
            int state_0 = this.state_0_;
            if (JSGuards.isUndefined(arguments0Value)) {
               int var12;
               this.state_0_ = var12 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.exit(arguments0Value);
            }

            if (!(arguments0Value instanceof Integer)) {
               this.exit2_toNumberNode_ = super.insert(JSToNumberNode.create());
               int var11;
               this.state_0_ = var11 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.exit(arguments0Value, this.exit2_toNumberNode_);
            }

            int arguments0Value_ = (Integer)arguments0Value;
            int var10;
            this.state_0_ = var10 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            var6 = this.exit(arguments0Value_);
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
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"exit", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"exit", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"exit", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.exit2_toNumberNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static GlobalBuiltins.JSGlobalExitNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new GlobalBuiltinsFactory.JSGlobalExitNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(GlobalBuiltins.JSGlobalImportScriptEngineGlobalBindingsNode.class)
   static final class JSGlobalImportScriptEngineGlobalBindingsNodeGen
      extends GlobalBuiltins.JSGlobalImportScriptEngineGlobalBindingsNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSGlobalImportScriptEngineGlobalBindingsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.importGlobalContext(arguments0Value_);
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
         Object[] s = new Object[]{"importGlobalContext", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static GlobalBuiltins.JSGlobalImportScriptEngineGlobalBindingsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new GlobalBuiltinsFactory.JSGlobalImportScriptEngineGlobalBindingsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(GlobalBuiltins.JSGlobalIndirectEvalNode.class)
   public static final class JSGlobalIndirectEvalNodeGen extends GlobalBuiltins.JSGlobalIndirectEvalNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private GlobalBuiltinsFactory.JSGlobalIndirectEvalNodeGen.IndirectEvalForeignObject0Data indirectEvalForeignObject0_cache;

      private JSGlobalIndirectEvalNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 2039) == 0 && (state_0 & 2047) != 0) {
            return this.execute_int0(state_0, frameValue);
         } else if ((state_0 & 2015) == 0 && (state_0 & 2047) != 0) {
            return this.execute_long1(state_0, frameValue);
         } else if ((state_0 & 1983) == 0 && (state_0 & 2047) != 0) {
            return this.execute_double2(state_0, frameValue);
         } else {
            return (state_0 & 1919) == 0 && (state_0 & 2047) != 0 ? this.execute_boolean3(state_0, frameValue) : this.execute_generic4(state_0, frameValue);
         }
      }

      private Object execute_int0(int state_0, VirtualFrame frameValue) {
         int arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var5.getResult());
         }

         assert (state_0 & 8) != 0;

         return this.indirectEvalInt(arguments0Value_);
      }

      private Object execute_long1(int state_0, VirtualFrame frameValue) {
         long arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeLong(frameValue);
         } catch (UnexpectedResultException var6) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var6.getResult());
         }

         assert (state_0 & 32) != 0;

         return this.indirectEvalLong(arguments0Value_);
      }

      private Object execute_double2(int state_0, VirtualFrame frameValue) {
         long arguments0Value_long = 0L;
         int arguments0Value_int = 0;

         double arguments0Value_;
         try {
            if ((state_0 & 28672) == 0 && (state_0 & 2047) != 0) {
               arguments0Value_ = this.arguments0_.executeDouble(frameValue);
            } else if ((state_0 & 26624) == 0 && (state_0 & 2047) != 0) {
               arguments0Value_int = this.arguments0_.executeInt(frameValue);
               arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
            } else if ((state_0 & 14336) == 0 && (state_0 & 2047) != 0) {
               arguments0Value_long = this.arguments0_.executeLong(frameValue);
               arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
            } else {
               Object arguments0Value__ = this.arguments0_.execute(frameValue);
               arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 30720) >>> 11, arguments0Value__);
            }
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var9.getResult());
         }

         assert (state_0 & 64) != 0;

         return this.indirectEvalDouble(arguments0Value_);
      }

      private Object execute_boolean3(int state_0, VirtualFrame frameValue) {
         boolean arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeBoolean(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var5.getResult());
         }

         assert (state_0 & 128) != 0;

         return this.indirectEvalBoolean(arguments0Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object indirectEvalForeignObject1Boundary(int state_0, Object arguments0Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Object var6;
         try {
            InteropLibrary indirectEvalForeignObject1_interop__ = GlobalBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var6 = this.indirectEvalForeignObject(arguments0Value_, indirectEvalForeignObject1_interop__);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @ExplodeLoop
      private Object execute_generic4(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            return this.indirectEvalString(arguments0Value__);
         } else {
            if ((state_0 & 6) != 0) {
               if ((state_0 & 2) != 0) {
                  for (GlobalBuiltinsFactory.JSGlobalIndirectEvalNodeGen.IndirectEvalForeignObject0Data s1_ = this.indirectEvalForeignObject0_cache;
                     s1_ != null;
                     s1_ = s1_.next_
                  ) {
                     if (s1_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                        return this.indirectEvalForeignObject(arguments0Value_, s1_.interop_);
                     }
                  }
               }

               if ((state_0 & 4) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                  return this.indirectEvalForeignObject1Boundary(state_0, arguments0Value_);
               }
            }

            if ((state_0 & 8) != 0 && arguments0Value_ instanceof Integer) {
               int arguments0Value__ = (Integer)arguments0Value_;
               return this.indirectEvalInt(arguments0Value__);
            } else if ((state_0 & 16) != 0 && arguments0Value_ instanceof SafeInteger) {
               SafeInteger arguments0Value__ = (SafeInteger)arguments0Value_;
               return this.indirectEvalSafeInteger(arguments0Value__);
            } else if ((state_0 & 32) != 0 && arguments0Value_ instanceof Long) {
               long arguments0Value__ = (Long)arguments0Value_;
               return this.indirectEvalLong(arguments0Value__);
            } else if ((state_0 & 64) != 0 && JSTypesGen.isImplicitDouble((state_0 & 30720) >>> 11, arguments0Value_)) {
               double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 30720) >>> 11, arguments0Value_);
               return this.indirectEvalDouble(arguments0Value__);
            } else if ((state_0 & 128) != 0 && arguments0Value_ instanceof Boolean) {
               boolean arguments0Value__ = (Boolean)arguments0Value_;
               return this.indirectEvalBoolean(arguments0Value__);
            } else if ((state_0 & 256) != 0 && arguments0Value_ instanceof Symbol) {
               Symbol arguments0Value__ = (Symbol)arguments0Value_;
               return this.indirectEvalSymbol(arguments0Value__);
            } else if ((state_0 & 512) != 0 && arguments0Value_ instanceof BigInt) {
               BigInt arguments0Value__ = (BigInt)arguments0Value_;
               return this.indirectEvalBigInt(arguments0Value__);
            } else {
               if ((state_0 & 1024) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                  JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                  if (JSGuards.isJSDynamicObject(arguments0Value__)) {
                     return this.indirectEvalJSType(arguments0Value__);
                  }
               }

               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arguments0Value_);
            }
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 7) != 0) {
            return JSTypesGen.expectBoolean(this.execute(frameValue));
         } else {
            boolean arguments0Value_;
            try {
               arguments0Value_ = this.arguments0_.executeBoolean(frameValue);
            } catch (UnexpectedResultException var5) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectBoolean(this.executeAndSpecialize(var5.getResult()));
            }

            if ((state_0 & 128) != 0) {
               return this.indirectEvalBoolean(arguments0Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_));
            }
         }
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 7) != 0) {
            return JSTypesGen.expectDouble(this.execute(frameValue));
         } else {
            long arguments0Value_long = 0L;
            int arguments0Value_int = 0;

            double arguments0Value_;
            try {
               if ((state_0 & 28672) == 0 && (state_0 & 2047) != 0) {
                  arguments0Value_ = this.arguments0_.executeDouble(frameValue);
               } else if ((state_0 & 26624) == 0 && (state_0 & 2047) != 0) {
                  arguments0Value_int = this.arguments0_.executeInt(frameValue);
                  arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
               } else if ((state_0 & 14336) == 0 && (state_0 & 2047) != 0) {
                  arguments0Value_long = this.arguments0_.executeLong(frameValue);
                  arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
               } else {
                  Object arguments0Value__ = this.arguments0_.execute(frameValue);
                  arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 30720) >>> 11, arguments0Value__);
               }
            } catch (UnexpectedResultException var9) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectDouble(this.executeAndSpecialize(var9.getResult()));
            }

            if ((state_0 & 64) != 0) {
               return this.indirectEvalDouble(arguments0Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectDouble(
                  this.executeAndSpecialize(
                     (state_0 & 26624) == 0 && (state_0 & 2047) != 0
                        ? arguments0Value_int
                        : ((state_0 & 14336) == 0 && (state_0 & 2047) != 0 ? arguments0Value_long : arguments0Value_)
                  )
               );
            }
         }
      }

      @Override
      public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 7) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
         } else {
            int arguments0Value_;
            try {
               arguments0Value_ = this.arguments0_.executeInt(frameValue);
            } catch (UnexpectedResultException var5) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectInteger(this.executeAndSpecialize(var5.getResult()));
            }

            if ((state_0 & 8) != 0) {
               return this.indirectEvalInt(arguments0Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_));
            }
         }
      }

      @Override
      public long executeLong(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 7) != 0) {
            return JSTypesGen.expectLong(this.execute(frameValue));
         } else {
            long arguments0Value_;
            try {
               arguments0Value_ = this.arguments0_.executeLong(frameValue);
            } catch (UnexpectedResultException var6) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectLong(this.executeAndSpecialize(var6.getResult()));
            }

            if ((state_0 & 32) != 0) {
               return this.indirectEvalLong(arguments0Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectLong(this.executeAndSpecialize(arguments0Value_));
            }
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 2015) == 0 && (state_0 & 2047) != 0) {
               this.executeLong(frameValue);
            } else if ((state_0 & 2039) == 0 && (state_0 & 2047) != 0) {
               this.executeInt(frameValue);
            } else if ((state_0 & 1983) == 0 && (state_0 & 2047) != 0) {
               this.executeDouble(frameValue);
            } else if ((state_0 & 1919) == 0 && (state_0 & 2047) != 0) {
               this.executeBoolean(frameValue);
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
            int exclude = this.exclude_;
            if (arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               int var29;
               this.state_0_ = var29 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.indirectEvalString(arguments0Value_);
            } else {
               if (exclude == 0) {
                  int count1_ = 0;
                  GlobalBuiltinsFactory.JSGlobalIndirectEvalNodeGen.IndirectEvalForeignObject0Data s1_ = this.indirectEvalForeignObject0_cache;
                  if ((state_0 & 2) != 0) {
                     while (s1_ != null && (!s1_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value))) {
                        s1_ = s1_.next_;
                        count1_++;
                     }
                  }

                  if (s1_ == null && JSGuards.isForeignObject(arguments0Value) && count1_ < 3) {
                     s1_ = super.insert(
                        new GlobalBuiltinsFactory.JSGlobalIndirectEvalNodeGen.IndirectEvalForeignObject0Data(this.indirectEvalForeignObject0_cache)
                     );
                     s1_.interop_ = s1_.insertAccessor(GlobalBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     VarHandle.storeStoreFence();
                     this.indirectEvalForeignObject0_cache = s1_;
                     this.state_0_ = state_0 |= 2;
                  }

                  if (s1_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.indirectEvalForeignObject(arguments0Value, s1_.interop_);
                  }
               }

               InteropLibrary indirectEvalForeignObject1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arguments0Value)) {
                     indirectEvalForeignObject1_interop__ = GlobalBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     int var30;
                     this.exclude_ = var30 = exclude | 1;
                     this.indirectEvalForeignObject0_cache = null;
                     state_0 &= -3;
                     int var28;
                     this.state_0_ = var28 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.indirectEvalForeignObject(arguments0Value, indirectEvalForeignObject1_interop__);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               if (arguments0Value instanceof Integer) {
                  int arguments0Value_ = (Integer)arguments0Value;
                  int var26;
                  this.state_0_ = var26 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.indirectEvalInt(arguments0Value_);
               } else if (arguments0Value instanceof SafeInteger) {
                  SafeInteger arguments0Value_ = (SafeInteger)arguments0Value;
                  int var25;
                  this.state_0_ = var25 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return this.indirectEvalSafeInteger(arguments0Value_);
               } else if (arguments0Value instanceof Long) {
                  long arguments0Value_ = (Long)arguments0Value;
                  int var24;
                  this.state_0_ = var24 = state_0 | 32;
                  lock.unlock();
                  hasLock = false;
                  return this.indirectEvalLong(arguments0Value_);
               } else {
                  int doubleCast0;
                  if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value)) != 0) {
                     double arguments0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
                     state_0 |= doubleCast0 << 11;
                     int var23;
                     this.state_0_ = var23 = state_0 | 64;
                     lock.unlock();
                     hasLock = false;
                     return this.indirectEvalDouble(arguments0Value_);
                  } else if (arguments0Value instanceof Boolean) {
                     boolean arguments0Value_ = (Boolean)arguments0Value;
                     int var21;
                     this.state_0_ = var21 = state_0 | 128;
                     lock.unlock();
                     hasLock = false;
                     return this.indirectEvalBoolean(arguments0Value_);
                  } else if (arguments0Value instanceof Symbol) {
                     Symbol arguments0Value_ = (Symbol)arguments0Value;
                     int var20;
                     this.state_0_ = var20 = state_0 | 256;
                     lock.unlock();
                     hasLock = false;
                     return this.indirectEvalSymbol(arguments0Value_);
                  } else if (!(arguments0Value instanceof BigInt)) {
                     if (arguments0Value instanceof JSDynamicObject) {
                        JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                        if (JSGuards.isJSDynamicObject(arguments0Value_)) {
                           int var19;
                           this.state_0_ = var19 = state_0 | 1024;
                           lock.unlock();
                           hasLock = false;
                           return this.indirectEvalJSType(arguments0Value_);
                        }
                     }

                     throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
                  } else {
                     BigInt arguments0Value_ = (BigInt)arguments0Value;
                     int var18;
                     this.state_0_ = var18 = state_0 | 512;
                     lock.unlock();
                     hasLock = false;
                     return this.indirectEvalBigInt(arguments0Value_);
                  }
               }
            }
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if ((state_0 & 2047) == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & 2047 & (state_0 & 2047) - 1) == 0) {
               GlobalBuiltinsFactory.JSGlobalIndirectEvalNodeGen.IndirectEvalForeignObject0Data s1_ = this.indirectEvalForeignObject0_cache;
               if (s1_ == null || s1_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[12];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"indirectEvalString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"indirectEvalForeignObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (GlobalBuiltinsFactory.JSGlobalIndirectEvalNodeGen.IndirectEvalForeignObject0Data s1_ = this.indirectEvalForeignObject0_cache;
               s1_ != null;
               s1_ = s1_.next_
            ) {
               cached.add(Arrays.asList(s1_.interop_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"indirectEvalForeignObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"indirectEvalInt", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"indirectEvalSafeInteger", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"indirectEvalLong", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"indirectEvalDouble", null, null};
         if ((state_0 & 64) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         s = new Object[]{"indirectEvalBoolean", null, null};
         if ((state_0 & 128) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[8] = s;
         s = new Object[]{"indirectEvalSymbol", null, null};
         if ((state_0 & 256) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[9] = s;
         s = new Object[]{"indirectEvalBigInt", null, null};
         if ((state_0 & 512) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[10] = s;
         s = new Object[]{"indirectEvalJSType", null, null};
         if ((state_0 & 1024) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[11] = s;
         return Introspection.Provider.create(data);
      }

      public static GlobalBuiltins.JSGlobalIndirectEvalNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new GlobalBuiltinsFactory.JSGlobalIndirectEvalNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(GlobalBuiltins.JSGlobalIndirectEvalNode.class)
      private static final class IndirectEvalForeignObject0Data extends Node {
         @Node.Child
         GlobalBuiltinsFactory.JSGlobalIndirectEvalNodeGen.IndirectEvalForeignObject0Data next_;
         @Node.Child
         InteropLibrary interop_;

         IndirectEvalForeignObject0Data(GlobalBuiltinsFactory.JSGlobalIndirectEvalNodeGen.IndirectEvalForeignObject0Data next_) {
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

   @GeneratedBy(GlobalBuiltins.JSGlobalIsFiniteNode.class)
   public static final class JSGlobalIsFiniteNodeGen extends GlobalBuiltins.JSGlobalIsFiniteNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToDoubleNode isFiniteGeneric_toDoubleNode_;

      private JSGlobalIsFiniteNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 14) == 0 && (state_0 & 15) != 0) {
            return this.execute_int0(state_0, frameValue);
         } else {
            return (state_0 & 13) == 0 && (state_0 & 15) != 0 ? this.execute_double1(state_0, frameValue) : this.execute_generic2(state_0, frameValue);
         }
      }

      private Object execute_int0(int state_0, VirtualFrame frameValue) {
         int arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var5.getResult());
         }

         assert (state_0 & 1) != 0;

         return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteInt(arguments0Value_);
      }

      private Object execute_double1(int state_0, VirtualFrame frameValue) {
         long arguments0Value_long = 0L;
         int arguments0Value_int = 0;

         double arguments0Value_;
         try {
            if ((state_0 & 224) == 0 && (state_0 & 15) != 0) {
               arguments0Value_ = this.arguments0_.executeDouble(frameValue);
            } else if ((state_0 & 208) == 0 && (state_0 & 15) != 0) {
               arguments0Value_int = this.arguments0_.executeInt(frameValue);
               arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
            } else if ((state_0 & 112) == 0 && (state_0 & 15) != 0) {
               arguments0Value_long = this.arguments0_.executeLong(frameValue);
               arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
            } else {
               Object arguments0Value__ = this.arguments0_.execute(frameValue);
               arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 240) >>> 4, arguments0Value__);
            }
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var9.getResult());
         }

         assert (state_0 & 2) != 0;

         return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteDouble(arguments0Value_);
      }

      private Object execute_generic2(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
            int arguments0Value__ = (Integer)arguments0Value_;
            return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteInt(arguments0Value__);
         } else if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 240) >>> 4, arguments0Value_)) {
            double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 240) >>> 4, arguments0Value_);
            return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteDouble(arguments0Value__);
         } else {
            if ((state_0 & 12) != 0) {
               if ((state_0 & 4) != 0 && !JSGuards.isUndefined(arguments0Value_)) {
                  return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteGeneric(arguments0Value_, this.isFiniteGeneric_toDoubleNode_);
               }

               if ((state_0 & 8) != 0 && JSGuards.isUndefined(arguments0Value_)) {
                  return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteUndefined(arguments0Value_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         if ((state_0 & 14) == 0 && (state_0 & 15) != 0) {
            return this.executeBoolean_int3(state_0, frameValue);
         } else {
            return (state_0 & 13) == 0 && (state_0 & 15) != 0
               ? this.executeBoolean_double4(state_0, frameValue)
               : this.executeBoolean_generic5(state_0, frameValue);
         }
      }

      private boolean executeBoolean_int3(int state_0, VirtualFrame frameValue) {
         int arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var5.getResult());
         }

         assert (state_0 & 1) != 0;

         return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteInt(arguments0Value_);
      }

      private boolean executeBoolean_double4(int state_0, VirtualFrame frameValue) {
         long arguments0Value_long = 0L;
         int arguments0Value_int = 0;

         double arguments0Value_;
         try {
            if ((state_0 & 224) == 0 && (state_0 & 15) != 0) {
               arguments0Value_ = this.arguments0_.executeDouble(frameValue);
            } else if ((state_0 & 208) == 0 && (state_0 & 15) != 0) {
               arguments0Value_int = this.arguments0_.executeInt(frameValue);
               arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
            } else if ((state_0 & 112) == 0 && (state_0 & 15) != 0) {
               arguments0Value_long = this.arguments0_.executeLong(frameValue);
               arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
            } else {
               Object arguments0Value__ = this.arguments0_.execute(frameValue);
               arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 240) >>> 4, arguments0Value__);
            }
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var9.getResult());
         }

         assert (state_0 & 2) != 0;

         return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteDouble(arguments0Value_);
      }

      private boolean executeBoolean_generic5(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
            int arguments0Value__ = (Integer)arguments0Value_;
            return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteInt(arguments0Value__);
         } else if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 240) >>> 4, arguments0Value_)) {
            double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 240) >>> 4, arguments0Value_);
            return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteDouble(arguments0Value__);
         } else {
            if ((state_0 & 12) != 0) {
               if ((state_0 & 4) != 0 && !JSGuards.isUndefined(arguments0Value_)) {
                  return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteGeneric(arguments0Value_, this.isFiniteGeneric_toDoubleNode_);
               }

               if ((state_0 & 8) != 0 && JSGuards.isUndefined(arguments0Value_)) {
                  return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteUndefined(arguments0Value_);
               }
            }

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

         int arguments0Value_;
         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof Integer) {
               arguments0Value_ = (Integer)arguments0Value;
               int var16;
               this.state_0_ = var16 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteInt(arguments0Value_);
            }

            if ((arguments0Value_ = JSTypesGen.specializeImplicitDouble(arguments0Value)) != 0) {
               double arguments0Value_x = JSTypesGen.asImplicitDouble(arguments0Value_, arguments0Value);
               state_0 |= arguments0Value_ << 4;
               int var15;
               this.state_0_ = var15 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteDouble(arguments0Value_x);
            }

            if (JSGuards.isUndefined(arguments0Value)) {
               if (!JSGuards.isUndefined(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               }

               int var13;
               this.state_0_ = var13 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteUndefined(arguments0Value);
            }

            this.isFiniteGeneric_toDoubleNode_ = super.insert(JSToDoubleNode.create());
            int var12;
            this.state_0_ = var12 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            arguments0Value_ = GlobalBuiltins.JSGlobalIsFiniteNode.isFiniteGeneric(arguments0Value, this.isFiniteGeneric_toDoubleNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return (boolean)arguments0Value_;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if ((state_0 & 15) == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & 15 & (state_0 & 15) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[5];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"isFiniteInt", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"isFiniteDouble", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"isFiniteGeneric", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.isFiniteGeneric_toDoubleNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"isFiniteUndefined", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         return Introspection.Provider.create(data);
      }

      public static GlobalBuiltins.JSGlobalIsFiniteNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new GlobalBuiltinsFactory.JSGlobalIsFiniteNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(GlobalBuiltins.JSGlobalIsNaNNode.class)
   public static final class JSGlobalIsNaNNodeGen extends GlobalBuiltins.JSGlobalIsNaNNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToDoubleNode isNaNGeneric_toDoubleNode_;

      private JSGlobalIsNaNNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 14) == 0 && (state_0 & 15) != 0) {
            return this.execute_int0(state_0, frameValue);
         } else {
            return (state_0 & 13) == 0 && (state_0 & 15) != 0 ? this.execute_double1(state_0, frameValue) : this.execute_generic2(state_0, frameValue);
         }
      }

      private Object execute_int0(int state_0, VirtualFrame frameValue) {
         int arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var5.getResult());
         }

         assert (state_0 & 1) != 0;

         return GlobalBuiltins.JSGlobalIsNaNNode.isNaNInt(arguments0Value_);
      }

      private Object execute_double1(int state_0, VirtualFrame frameValue) {
         long arguments0Value_long = 0L;
         int arguments0Value_int = 0;

         double arguments0Value_;
         try {
            if ((state_0 & 224) == 0 && (state_0 & 15) != 0) {
               arguments0Value_ = this.arguments0_.executeDouble(frameValue);
            } else if ((state_0 & 208) == 0 && (state_0 & 15) != 0) {
               arguments0Value_int = this.arguments0_.executeInt(frameValue);
               arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
            } else if ((state_0 & 112) == 0 && (state_0 & 15) != 0) {
               arguments0Value_long = this.arguments0_.executeLong(frameValue);
               arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
            } else {
               Object arguments0Value__ = this.arguments0_.execute(frameValue);
               arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 240) >>> 4, arguments0Value__);
            }
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var9.getResult());
         }

         assert (state_0 & 2) != 0;

         return GlobalBuiltins.JSGlobalIsNaNNode.isNaNDouble(arguments0Value_);
      }

      private Object execute_generic2(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
            int arguments0Value__ = (Integer)arguments0Value_;
            return GlobalBuiltins.JSGlobalIsNaNNode.isNaNInt(arguments0Value__);
         } else if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 240) >>> 4, arguments0Value_)) {
            double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 240) >>> 4, arguments0Value_);
            return GlobalBuiltins.JSGlobalIsNaNNode.isNaNDouble(arguments0Value__);
         } else {
            if ((state_0 & 12) != 0) {
               if ((state_0 & 4) != 0 && !JSGuards.isUndefined(arguments0Value_)) {
                  return GlobalBuiltins.JSGlobalIsNaNNode.isNaNGeneric(arguments0Value_, this.isNaNGeneric_toDoubleNode_);
               }

               if ((state_0 & 8) != 0 && JSGuards.isUndefined(arguments0Value_)) {
                  return GlobalBuiltins.JSGlobalIsNaNNode.isNaNUndefined(arguments0Value_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         if ((state_0 & 14) == 0 && (state_0 & 15) != 0) {
            return this.executeBoolean_int3(state_0, frameValue);
         } else {
            return (state_0 & 13) == 0 && (state_0 & 15) != 0
               ? this.executeBoolean_double4(state_0, frameValue)
               : this.executeBoolean_generic5(state_0, frameValue);
         }
      }

      private boolean executeBoolean_int3(int state_0, VirtualFrame frameValue) {
         int arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var5.getResult());
         }

         assert (state_0 & 1) != 0;

         return GlobalBuiltins.JSGlobalIsNaNNode.isNaNInt(arguments0Value_);
      }

      private boolean executeBoolean_double4(int state_0, VirtualFrame frameValue) {
         long arguments0Value_long = 0L;
         int arguments0Value_int = 0;

         double arguments0Value_;
         try {
            if ((state_0 & 224) == 0 && (state_0 & 15) != 0) {
               arguments0Value_ = this.arguments0_.executeDouble(frameValue);
            } else if ((state_0 & 208) == 0 && (state_0 & 15) != 0) {
               arguments0Value_int = this.arguments0_.executeInt(frameValue);
               arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
            } else if ((state_0 & 112) == 0 && (state_0 & 15) != 0) {
               arguments0Value_long = this.arguments0_.executeLong(frameValue);
               arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
            } else {
               Object arguments0Value__ = this.arguments0_.execute(frameValue);
               arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 240) >>> 4, arguments0Value__);
            }
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var9.getResult());
         }

         assert (state_0 & 2) != 0;

         return GlobalBuiltins.JSGlobalIsNaNNode.isNaNDouble(arguments0Value_);
      }

      private boolean executeBoolean_generic5(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
            int arguments0Value__ = (Integer)arguments0Value_;
            return GlobalBuiltins.JSGlobalIsNaNNode.isNaNInt(arguments0Value__);
         } else if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 240) >>> 4, arguments0Value_)) {
            double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 240) >>> 4, arguments0Value_);
            return GlobalBuiltins.JSGlobalIsNaNNode.isNaNDouble(arguments0Value__);
         } else {
            if ((state_0 & 12) != 0) {
               if ((state_0 & 4) != 0 && !JSGuards.isUndefined(arguments0Value_)) {
                  return GlobalBuiltins.JSGlobalIsNaNNode.isNaNGeneric(arguments0Value_, this.isNaNGeneric_toDoubleNode_);
               }

               if ((state_0 & 8) != 0 && JSGuards.isUndefined(arguments0Value_)) {
                  return GlobalBuiltins.JSGlobalIsNaNNode.isNaNUndefined(arguments0Value_);
               }
            }

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

         int arguments0Value_;
         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof Integer) {
               arguments0Value_ = (Integer)arguments0Value;
               int var16;
               this.state_0_ = var16 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return GlobalBuiltins.JSGlobalIsNaNNode.isNaNInt(arguments0Value_);
            }

            if ((arguments0Value_ = JSTypesGen.specializeImplicitDouble(arguments0Value)) != 0) {
               double arguments0Value_x = JSTypesGen.asImplicitDouble(arguments0Value_, arguments0Value);
               state_0 |= arguments0Value_ << 4;
               int var15;
               this.state_0_ = var15 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return GlobalBuiltins.JSGlobalIsNaNNode.isNaNDouble(arguments0Value_x);
            }

            if (JSGuards.isUndefined(arguments0Value)) {
               if (!JSGuards.isUndefined(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               }

               int var13;
               this.state_0_ = var13 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return GlobalBuiltins.JSGlobalIsNaNNode.isNaNUndefined(arguments0Value);
            }

            this.isNaNGeneric_toDoubleNode_ = super.insert(JSToDoubleNode.create());
            int var12;
            this.state_0_ = var12 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            arguments0Value_ = GlobalBuiltins.JSGlobalIsNaNNode.isNaNGeneric(arguments0Value, this.isNaNGeneric_toDoubleNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return (boolean)arguments0Value_;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if ((state_0 & 15) == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & 15 & (state_0 & 15) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[5];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"isNaNInt", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"isNaNDouble", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"isNaNGeneric", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.isNaNGeneric_toDoubleNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"isNaNUndefined", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         return Introspection.Provider.create(data);
      }

      public static GlobalBuiltins.JSGlobalIsNaNNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new GlobalBuiltinsFactory.JSGlobalIsNaNNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(GlobalBuiltins.JSGlobalLoadNode.class)
   public static final class JSGlobalLoadNodeGen extends GlobalBuiltins.JSGlobalLoadNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private InteropLibrary loadTruffleObject_interop_;

      private JSGlobalLoadNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
            Object[] arguments1Value__ = (Object[])arguments1Value_;
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               return this.loadString(arguments0Value__, arguments1Value__);
            }

            if ((state_0 & 2) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
               return this.loadTruffleObject(arguments0Value_, arguments1Value__, this.loadTruffleObject_interop_);
            }

            if ((state_0 & 4) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (JSGuards.isJSObject(arguments0Value__)) {
                  return this.loadScriptObj(arguments0Value__, arguments1Value__);
               }
            }

            if ((state_0 & 8) != 0
               && !JSGuards.isString(arguments0Value_)
               && !JSGuards.isForeignObject(arguments0Value_)
               && !JSGuards.isJSObject(arguments0Value_)) {
               return this.loadConvertToString(arguments0Value_, arguments1Value__);
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
            int state_0 = this.state_0_;
            if (arguments1Value instanceof Object[]) {
               Object[] arguments1Value_ = (Object[])arguments1Value;
               if (arguments0Value instanceof TruffleString) {
                  TruffleString arguments0Value_ = (TruffleString)arguments0Value;
                  int var15;
                  this.state_0_ = var15 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.loadString(arguments0Value_, arguments1Value_);
               }

               if (JSGuards.isForeignObject(arguments0Value)) {
                  this.loadTruffleObject_interop_ = super.insert(GlobalBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
                  int var14;
                  this.state_0_ = var14 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.loadTruffleObject(arguments0Value, arguments1Value_, this.loadTruffleObject_interop_);
               }

               if (arguments0Value instanceof JSDynamicObject) {
                  JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                  if (JSGuards.isJSObject(arguments0Value_)) {
                     int var13;
                     this.state_0_ = var13 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.loadScriptObj(arguments0Value_, arguments1Value_);
                  }
               }

               if (!JSGuards.isString(arguments0Value) && !JSGuards.isForeignObject(arguments0Value) && !JSGuards.isJSObject(arguments0Value)) {
                  int var12;
                  this.state_0_ = var12 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.loadConvertToString(arguments0Value, arguments1Value_);
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
         Object[] data = new Object[5];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"loadString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"loadTruffleObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.loadTruffleObject_interop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"loadScriptObj", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"loadConvertToString", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         return Introspection.Provider.create(data);
      }

      public static GlobalBuiltins.JSGlobalLoadNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new GlobalBuiltinsFactory.JSGlobalLoadNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(GlobalBuiltins.JSGlobalLoadWithNewGlobalNode.class)
   public static final class JSGlobalLoadWithNewGlobalNodeGen extends GlobalBuiltins.JSGlobalLoadWithNewGlobalNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private InteropLibrary loadTruffleObject_interop_;

      private JSGlobalLoadWithNewGlobalNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
            Object[] arguments1Value__ = (Object[])arguments1Value_;
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               return this.loadString(arguments0Value__, arguments1Value__);
            }

            if ((state_0 & 2) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
               return this.loadTruffleObject(arguments0Value_, arguments1Value__, this.loadTruffleObject_interop_);
            }

            if ((state_0 & 4) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (JSGuards.isJSObject(arguments0Value__)) {
                  return this.loadScriptObj(arguments0Value__, arguments1Value__);
               }
            }

            if ((state_0 & 8) != 0
               && !JSGuards.isString(arguments0Value_)
               && !JSGuards.isForeignObject(arguments0Value_)
               && !JSGuards.isJSObject(arguments0Value_)) {
               return this.loadConvertToString(arguments0Value_, arguments1Value__);
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
            int state_0 = this.state_0_;
            if (arguments1Value instanceof Object[]) {
               Object[] arguments1Value_ = (Object[])arguments1Value;
               if (arguments0Value instanceof TruffleString) {
                  TruffleString arguments0Value_ = (TruffleString)arguments0Value;
                  int var15;
                  this.state_0_ = var15 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.loadString(arguments0Value_, arguments1Value_);
               }

               if (JSGuards.isForeignObject(arguments0Value)) {
                  this.loadTruffleObject_interop_ = super.insert(GlobalBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
                  int var14;
                  this.state_0_ = var14 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.loadTruffleObject(arguments0Value, arguments1Value_, this.loadTruffleObject_interop_);
               }

               if (arguments0Value instanceof JSDynamicObject) {
                  JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                  if (JSGuards.isJSObject(arguments0Value_)) {
                     int var13;
                     this.state_0_ = var13 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.loadScriptObj(arguments0Value_, arguments1Value_);
                  }
               }

               if (!JSGuards.isString(arguments0Value) && !JSGuards.isForeignObject(arguments0Value) && !JSGuards.isJSObject(arguments0Value)) {
                  int var12;
                  this.state_0_ = var12 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.loadConvertToString(arguments0Value, arguments1Value_);
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
         Object[] data = new Object[5];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"loadString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"loadTruffleObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.loadTruffleObject_interop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"loadScriptObj", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"loadConvertToString", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         return Introspection.Provider.create(data);
      }

      public static GlobalBuiltins.JSGlobalLoadWithNewGlobalNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new GlobalBuiltinsFactory.JSGlobalLoadWithNewGlobalNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(GlobalBuiltins.JSGlobalParseFloatNode.class)
   public static final class JSGlobalParseFloatNodeGen extends GlobalBuiltins.JSGlobalParseFloatNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile parseFloatDouble_negativeZero_;

      private JSGlobalParseFloatNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 126) == 0 && (state_0 & 127) != 0) {
            return this.execute_int0(state_0, frameValue);
         } else if ((state_0 & 125) == 0 && (state_0 & 127) != 0) {
            return this.execute_double1(state_0, frameValue);
         } else {
            return (state_0 & 123) == 0 && (state_0 & 127) != 0 ? this.execute_boolean2(state_0, frameValue) : this.execute_generic3(state_0, frameValue);
         }
      }

      private Object execute_int0(int state_0, VirtualFrame frameValue) {
         int arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var5.getResult());
         }

         assert (state_0 & 1) != 0;

         return this.parseFloatInt(arguments0Value_);
      }

      private Object execute_double1(int state_0, VirtualFrame frameValue) {
         long arguments0Value_long = 0L;
         int arguments0Value_int = 0;

         double arguments0Value_;
         try {
            if ((state_0 & 1792) == 0 && (state_0 & 127) != 0) {
               arguments0Value_ = this.arguments0_.executeDouble(frameValue);
            } else if ((state_0 & 1664) == 0 && (state_0 & 127) != 0) {
               arguments0Value_int = this.arguments0_.executeInt(frameValue);
               arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
            } else if ((state_0 & 896) == 0 && (state_0 & 127) != 0) {
               arguments0Value_long = this.arguments0_.executeLong(frameValue);
               arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
            } else {
               Object arguments0Value__ = this.arguments0_.execute(frameValue);
               arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 1920) >>> 7, arguments0Value__);
            }
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var9.getResult());
         }

         assert (state_0 & 2) != 0;

         return this.parseFloatDouble(arguments0Value_, this.parseFloatDouble_negativeZero_);
      }

      private Object execute_boolean2(int state_0, VirtualFrame frameValue) {
         boolean arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeBoolean(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var5.getResult());
         }

         assert (state_0 & 4) != 0;

         return this.parseFloatBoolean(arguments0Value_);
      }

      private Object execute_generic3(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
            int arguments0Value__ = (Integer)arguments0Value_;
            return this.parseFloatInt(arguments0Value__);
         } else if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 1920) >>> 7, arguments0Value_)) {
            double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 1920) >>> 7, arguments0Value_);
            return this.parseFloatDouble(arguments0Value__, this.parseFloatDouble_negativeZero_);
         } else if ((state_0 & 4) != 0 && arguments0Value_ instanceof Boolean) {
            boolean arguments0Value__ = (Boolean)arguments0Value_;
            return this.parseFloatBoolean(arguments0Value__);
         } else {
            if ((state_0 & 24) != 0) {
               if ((state_0 & 8) != 0 && JSGuards.isUndefined(arguments0Value_)) {
                  return this.parseFloatUndefined(arguments0Value_);
               }

               if ((state_0 & 16) != 0 && JSGuards.isJSNull(arguments0Value_)) {
                  return this.parseFloatNull(arguments0Value_);
               }
            }

            if ((state_0 & 32) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               return this.parseFloat(arguments0Value__);
            } else {
               if ((state_0 & 64) != 0 && arguments0Value_ instanceof TruffleObject) {
                  TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
                  if (!JSGuards.isJSNull(arguments0Value__) && !JSGuards.isUndefined(arguments0Value__) && !JSGuards.isString(arguments0Value__)) {
                     return this.parseFloat(arguments0Value__);
                  }
               }

               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arguments0Value_);
            }
         }
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 124) == 0 && (state_0 & 126) != 0) {
            return this.executeDouble_double4(state_0, frameValue);
         } else {
            return (state_0 & 122) == 0 && (state_0 & 126) != 0
               ? this.executeDouble_boolean5(state_0, frameValue)
               : this.executeDouble_generic6(state_0, frameValue);
         }
      }

      private double executeDouble_double4(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
         long arguments0Value_long = 0L;
         int arguments0Value_int = 0;

         double arguments0Value_;
         try {
            if ((state_0 & 1792) == 0 && (state_0 & 127) != 0) {
               arguments0Value_ = this.arguments0_.executeDouble(frameValue);
            } else if ((state_0 & 1664) == 0 && (state_0 & 127) != 0) {
               arguments0Value_int = this.arguments0_.executeInt(frameValue);
               arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
            } else if ((state_0 & 896) == 0 && (state_0 & 127) != 0) {
               arguments0Value_long = this.arguments0_.executeLong(frameValue);
               arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
            } else {
               Object arguments0Value__ = this.arguments0_.execute(frameValue);
               arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 1920) >>> 7, arguments0Value__);
            }
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize(var9.getResult()));
         }

         assert (state_0 & 2) != 0;

         return this.parseFloatDouble(arguments0Value_, this.parseFloatDouble_negativeZero_);
      }

      private double executeDouble_boolean5(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
         boolean arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeBoolean(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize(var5.getResult()));
         }

         assert (state_0 & 4) != 0;

         return this.parseFloatBoolean(arguments0Value_);
      }

      private double executeDouble_generic6(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 1920) >>> 7, arguments0Value_)) {
            double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 1920) >>> 7, arguments0Value_);
            return this.parseFloatDouble(arguments0Value__, this.parseFloatDouble_negativeZero_);
         } else if ((state_0 & 4) != 0 && arguments0Value_ instanceof Boolean) {
            boolean arguments0Value__ = (Boolean)arguments0Value_;
            return this.parseFloatBoolean(arguments0Value__);
         } else {
            if ((state_0 & 24) != 0) {
               if ((state_0 & 8) != 0 && JSGuards.isUndefined(arguments0Value_)) {
                  return this.parseFloatUndefined(arguments0Value_);
               }

               if ((state_0 & 16) != 0 && JSGuards.isJSNull(arguments0Value_)) {
                  return this.parseFloatNull(arguments0Value_);
               }
            }

            if ((state_0 & 32) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               return this.parseFloat(arguments0Value__);
            } else {
               if ((state_0 & 64) != 0 && arguments0Value_ instanceof TruffleObject) {
                  TruffleObject arguments0Value__ = (TruffleObject)arguments0Value_;
                  if (!JSGuards.isJSNull(arguments0Value__) && !JSGuards.isUndefined(arguments0Value__) && !JSGuards.isString(arguments0Value__)) {
                     return this.parseFloat(arguments0Value__);
                  }
               }

               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectDouble(this.executeAndSpecialize(arguments0Value_));
            }
         }
      }

      @Override
      public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;

         int arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(var5.getResult()));
         }

         if ((state_0 & 1) != 0) {
            return this.parseFloatInt(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_));
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 126) == 0 && (state_0 & 127) != 0) {
               this.executeInt(frameValue);
            } else if ((state_0 & 1) == 0 && (state_0 & 127) != 0) {
               this.executeDouble(frameValue);
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

         Double arguments0Value_;
         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof Integer) {
               int arguments0Value_x = (Integer)arguments0Value;
               int var19;
               this.state_0_ = var19 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.parseFloatInt(arguments0Value_x);
            }

            int doubleCast0;
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value)) != 0) {
               double arguments0Value_x = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
               this.parseFloatDouble_negativeZero_ = ConditionProfile.createBinaryProfile();
               state_0 |= doubleCast0 << 7;
               int var18;
               this.state_0_ = var18 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.parseFloatDouble(arguments0Value_x, this.parseFloatDouble_negativeZero_);
            }

            if (arguments0Value instanceof Boolean) {
               boolean arguments0Value_x = (Boolean)arguments0Value;
               int var16;
               this.state_0_ = var16 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.parseFloatBoolean(arguments0Value_x);
            }

            if (JSGuards.isUndefined(arguments0Value)) {
               int var15;
               this.state_0_ = var15 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.parseFloatUndefined(arguments0Value);
            }

            if (JSGuards.isJSNull(arguments0Value)) {
               int var14;
               this.state_0_ = var14 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.parseFloatNull(arguments0Value);
            }

            if (!(arguments0Value instanceof TruffleString)) {
               if (arguments0Value instanceof TruffleObject) {
                  TruffleObject arguments0Value_x = (TruffleObject)arguments0Value;
                  if (!JSGuards.isJSNull(arguments0Value_x) && !JSGuards.isUndefined(arguments0Value_x) && !JSGuards.isString(arguments0Value_x)) {
                     int var13;
                     this.state_0_ = var13 = state_0 | 64;
                     lock.unlock();
                     hasLock = false;
                     return this.parseFloat(arguments0Value_x);
                  }
               }

               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
            }

            TruffleString arguments0Value_x = (TruffleString)arguments0Value;
            int var12;
            this.state_0_ = var12 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            arguments0Value_ = this.parseFloat(arguments0Value_x);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return arguments0Value_;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if ((state_0 & 127) == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & 127 & (state_0 & 127) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[8];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"parseFloatInt", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"parseFloatDouble", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.parseFloatDouble_negativeZero_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"parseFloatBoolean", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"parseFloatUndefined", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"parseFloatNull", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"parseFloat", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"parseFloat", null, null};
         if ((state_0 & 64) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         return Introspection.Provider.create(data);
      }

      public static GlobalBuiltins.JSGlobalParseFloatNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new GlobalBuiltinsFactory.JSGlobalParseFloatNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(GlobalBuiltins.JSGlobalParseIntNode.class)
   public static final class JSGlobalParseIntNodeGen extends GlobalBuiltins.JSGlobalParseIntNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile parseIntInt_needsRadixConversion_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile parseIntDouble_needsRadixConversion_;
      @Node.Child
      private TruffleString.ReadCharUTF16Node parseIntStringInt10_readRawNode_;
      @Node.Child
      private GlobalBuiltinsFactory.JSGlobalParseIntNodeGen.ParseIntGenericData parseIntGeneric_cache;

      private JSGlobalParseIntNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 124) == 0 && (state_0 & 127) != 0) {
            return this.execute_int0(state_0, frameValue);
         } else if ((state_0 & 99) == 0 && (state_0 & 127) != 0) {
            return this.execute_double1(state_0, frameValue);
         } else {
            return (state_0 & 95) == 0 && (state_0 & 127) != 0 ? this.execute_int2(state_0, frameValue) : this.execute_generic3(state_0, frameValue);
         }
      }

      private Object execute_int0(int state_0, VirtualFrame frameValue) {
         int arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
         } catch (UnexpectedResultException var6) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments1Value = this.arguments1_.execute(frameValue);
            return this.executeAndSpecialize(var6.getResult(), arguments1Value);
         }

         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 3) != 0) {
            if ((state_0 & 1) != 0 && JSGuards.isUndefined(arguments1Value_)) {
               return this.parseIntNoRadix(arguments0Value_, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isUndefined(arguments1Value_)) {
               return this.parseIntInt(arguments0Value_, arguments1Value_, this.parseIntInt_needsRadixConversion_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      private Object execute_double1(int state_0, VirtualFrame frameValue) {
         long arguments0Value_long = 0L;
         int arguments0Value_int = 0;

         double arguments0Value_;
         try {
            if ((state_0 & 1792) == 0 && (state_0 & 127) != 0) {
               arguments0Value_ = this.arguments0_.executeDouble(frameValue);
            } else if ((state_0 & 1664) == 0 && (state_0 & 127) != 0) {
               arguments0Value_int = this.arguments0_.executeInt(frameValue);
               arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
            } else if ((state_0 & 896) == 0 && (state_0 & 127) != 0) {
               arguments0Value_long = this.arguments0_.executeLong(frameValue);
               arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
            } else {
               Object arguments0Value__ = this.arguments0_.execute(frameValue);
               arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 1920) >>> 7, arguments0Value__);
            }
         } catch (UnexpectedResultException var10) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments1Value = this.arguments1_.execute(frameValue);
            return this.executeAndSpecialize(var10.getResult(), arguments1Value);
         }

         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 28) != 0) {
            if ((state_0 & 4) != 0
               && GlobalBuiltins.JSGlobalParseIntNode.hasRegularToStringInInt32Range(arguments0Value_)
               && JSGuards.isUndefined(arguments1Value_)) {
               return this.parseIntDoubleToInt(arguments0Value_, arguments1Value_);
            }

            if ((state_0 & 8) != 0 && GlobalBuiltins.JSGlobalParseIntNode.hasRegularToString(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
               return this.parseIntDoubleNoRadix(arguments0Value_, arguments1Value_);
            }

            if ((state_0 & 16) != 0 && GlobalBuiltins.JSGlobalParseIntNode.hasRegularToString(arguments0Value_)) {
               return this.parseIntDouble(arguments0Value_, arguments1Value_, this.parseIntDouble_needsRadixConversion_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_0 & 1664) == 0 && (state_0 & 127) != 0
               ? arguments0Value_int
               : ((state_0 & 896) == 0 && (state_0 & 127) != 0 ? arguments0Value_long : arguments0Value_),
            arguments1Value_
         );
      }

      private Object execute_int2(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var6) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, var6.getResult());
         }

         assert (state_0 & 32) != 0;

         if (arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (arguments1Value_ == 10 && JSGuards.stringLength(arguments0Value__) < 15) {
               return this.parseIntStringInt10(arguments0Value__, arguments1Value_, this.parseIntStringInt10_readRawNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      private Object execute_generic3(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 31) != 0) {
            if ((state_0 & 3) != 0 && arguments0Value_ instanceof Integer) {
               int arguments0Value__ = (Integer)arguments0Value_;
               if ((state_0 & 1) != 0 && JSGuards.isUndefined(arguments1Value_)) {
                  return this.parseIntNoRadix(arguments0Value__, arguments1Value_);
               }

               if ((state_0 & 2) != 0 && !JSGuards.isUndefined(arguments1Value_)) {
                  return this.parseIntInt(arguments0Value__, arguments1Value_, this.parseIntInt_needsRadixConversion_);
               }
            }

            if ((state_0 & 28) != 0 && JSTypesGen.isImplicitDouble((state_0 & 1920) >>> 7, arguments0Value_)) {
               double arguments0Value__x = JSTypesGen.asImplicitDouble((state_0 & 1920) >>> 7, arguments0Value_);
               if ((state_0 & 4) != 0
                  && GlobalBuiltins.JSGlobalParseIntNode.hasRegularToStringInInt32Range(arguments0Value__x)
                  && JSGuards.isUndefined(arguments1Value_)) {
                  return this.parseIntDoubleToInt(arguments0Value__x, arguments1Value_);
               }

               if ((state_0 & 8) != 0 && GlobalBuiltins.JSGlobalParseIntNode.hasRegularToString(arguments0Value__x) && JSGuards.isUndefined(arguments1Value_)) {
                  return this.parseIntDoubleNoRadix(arguments0Value__x, arguments1Value_);
               }

               if ((state_0 & 16) != 0 && GlobalBuiltins.JSGlobalParseIntNode.hasRegularToString(arguments0Value__x)) {
                  return this.parseIntDouble(arguments0Value__x, arguments1Value_, this.parseIntDouble_needsRadixConversion_);
               }
            }
         }

         if ((state_0 & 32) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__xx = (TruffleString)arguments0Value_;
            if (arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               if (arguments1Value__ == 10 && JSGuards.stringLength(arguments0Value__xx) < 15) {
                  return this.parseIntStringInt10(arguments0Value__xx, arguments1Value__, this.parseIntStringInt10_readRawNode_);
               }
            }
         }

         if ((state_0 & 64) != 0) {
            GlobalBuiltinsFactory.JSGlobalParseIntNodeGen.ParseIntGenericData s6_ = this.parseIntGeneric_cache;
            if (s6_ != null && !GlobalBuiltins.JSGlobalParseIntNode.isShortStringInt10(arguments0Value_, arguments1Value_)) {
               return this.parseIntGeneric(
                  arguments0Value_, arguments1Value_, s6_.toStringNode_, s6_.needsRadix16_, s6_.needsDontFitLong_, s6_.readRawNode_, s6_.substringNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 98) != 0) {
            return JSTypesGen.expectDouble(this.execute(frameValue));
         } else {
            long arguments0Value_long = 0L;
            int arguments0Value_int = 0;

            double arguments0Value_;
            try {
               if ((state_0 & 1792) == 0 && (state_0 & 127) != 0) {
                  arguments0Value_ = this.arguments0_.executeDouble(frameValue);
               } else if ((state_0 & 1664) == 0 && (state_0 & 127) != 0) {
                  arguments0Value_int = this.arguments0_.executeInt(frameValue);
                  arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
               } else if ((state_0 & 896) == 0 && (state_0 & 127) != 0) {
                  arguments0Value_long = this.arguments0_.executeLong(frameValue);
                  arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
               } else {
                  Object arguments0Value__ = this.arguments0_.execute(frameValue);
                  arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 1920) >>> 7, arguments0Value__);
               }
            } catch (UnexpectedResultException var10) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Object arguments1Value = this.arguments1_.execute(frameValue);
               return JSTypesGen.expectDouble(this.executeAndSpecialize(var10.getResult(), arguments1Value));
            }

            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 24) != 0) {
               if ((state_0 & 8) != 0 && GlobalBuiltins.JSGlobalParseIntNode.hasRegularToString(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
                  return this.parseIntDoubleNoRadix(arguments0Value_, arguments1Value_);
               }

               if ((state_0 & 16) != 0 && GlobalBuiltins.JSGlobalParseIntNode.hasRegularToString(arguments0Value_)) {
                  return this.parseIntDouble(arguments0Value_, arguments1Value_, this.parseIntDouble_needsRadixConversion_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(
               this.executeAndSpecialize(
                  (state_0 & 1664) == 0 && (state_0 & 127) != 0
                     ? arguments0Value_int
                     : ((state_0 & 896) == 0 && (state_0 & 127) != 0 ? arguments0Value_long : arguments0Value_),
                  arguments1Value_
               )
            );
         }
      }

      @Override
      public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 98) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
         } else if ((state_0 & 4) == 0 && (state_0 & 5) != 0) {
            return this.executeInt_int4(state_0, frameValue);
         } else {
            return (state_0 & 1) == 0 && (state_0 & 5) != 0 ? this.executeInt_double5(state_0, frameValue) : this.executeInt_generic6(state_0, frameValue);
         }
      }

      private int executeInt_int4(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
         int arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
         } catch (UnexpectedResultException var6) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments1Value = this.arguments1_.execute(frameValue);
            return JSTypesGen.expectInteger(this.executeAndSpecialize(var6.getResult(), arguments1Value));
         }

         Object arguments1Value_ = this.arguments1_.execute(frameValue);

         assert (state_0 & 1) != 0;

         if (JSGuards.isUndefined(arguments1Value_)) {
            return this.parseIntNoRadix(arguments0Value_, arguments1Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
         }
      }

      private int executeInt_double5(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
         long arguments0Value_long = 0L;
         int arguments0Value_int = 0;

         double arguments0Value_;
         try {
            if ((state_0 & 1792) == 0 && (state_0 & 127) != 0) {
               arguments0Value_ = this.arguments0_.executeDouble(frameValue);
            } else if ((state_0 & 1664) == 0 && (state_0 & 127) != 0) {
               arguments0Value_int = this.arguments0_.executeInt(frameValue);
               arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
            } else if ((state_0 & 896) == 0 && (state_0 & 127) != 0) {
               arguments0Value_long = this.arguments0_.executeLong(frameValue);
               arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
            } else {
               Object arguments0Value__ = this.arguments0_.execute(frameValue);
               arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 1920) >>> 7, arguments0Value__);
            }
         } catch (UnexpectedResultException var10) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments1Value = this.arguments1_.execute(frameValue);
            return JSTypesGen.expectInteger(this.executeAndSpecialize(var10.getResult(), arguments1Value));
         }

         Object arguments1Value_ = this.arguments1_.execute(frameValue);

         assert (state_0 & 4) != 0;

         if (GlobalBuiltins.JSGlobalParseIntNode.hasRegularToStringInInt32Range(arguments0Value_) && JSGuards.isUndefined(arguments1Value_)) {
            return this.parseIntDoubleToInt(arguments0Value_, arguments1Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(
               this.executeAndSpecialize(
                  (state_0 & 1664) == 0 && (state_0 & 127) != 0
                     ? arguments0Value_int
                     : ((state_0 & 896) == 0 && (state_0 & 127) != 0 ? arguments0Value_long : arguments0Value_),
                  arguments1Value_
               )
            );
         }
      }

      private int executeInt_generic6(int state_0, VirtualFrame frameValue) throws UnexpectedResultException {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 5) != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
               int arguments0Value__ = (Integer)arguments0Value_;
               if (JSGuards.isUndefined(arguments1Value_)) {
                  return this.parseIntNoRadix(arguments0Value__, arguments1Value_);
               }
            }

            if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_0 & 1920) >>> 7, arguments0Value_)) {
               double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 1920) >>> 7, arguments0Value_);
               if (GlobalBuiltins.JSGlobalParseIntNode.hasRegularToStringInInt32Range(arguments0Value__) && JSGuards.isUndefined(arguments1Value_)) {
                  return this.parseIntDoubleToInt(arguments0Value__, arguments1Value_);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 122) == 0 && (state_0 & 127) != 0) {
               this.executeInt(frameValue);
            } else if ((state_0 & 103) == 0 && (state_0 & 127) != 0) {
               this.executeDouble(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof Integer) {
               int arguments0Value_ = (Integer)arguments0Value;
               if (JSGuards.isUndefined(arguments1Value)) {
                  int var22;
                  this.state_0_ = var22 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.parseIntNoRadix(arguments0Value_, arguments1Value);
               }

               if (!JSGuards.isUndefined(arguments1Value)) {
                  this.parseIntInt_needsRadixConversion_ = BranchProfile.create();
                  int var21;
                  this.state_0_ = var21 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.parseIntInt(arguments0Value_, arguments1Value, this.parseIntInt_needsRadixConversion_);
               }
            }

            int doubleCast0;
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value)) != 0) {
               double arguments0Value_x = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
               if (GlobalBuiltins.JSGlobalParseIntNode.hasRegularToStringInInt32Range(arguments0Value_x) && JSGuards.isUndefined(arguments1Value)) {
                  state_0 |= doubleCast0 << 7;
                  int var20;
                  this.state_0_ = var20 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.parseIntDoubleToInt(arguments0Value_x, arguments1Value);
               }

               if (GlobalBuiltins.JSGlobalParseIntNode.hasRegularToString(arguments0Value_x) && JSGuards.isUndefined(arguments1Value)) {
                  state_0 |= doubleCast0 << 7;
                  int var18;
                  this.state_0_ = var18 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.parseIntDoubleNoRadix(arguments0Value_x, arguments1Value);
               }

               if (GlobalBuiltins.JSGlobalParseIntNode.hasRegularToString(arguments0Value_x)) {
                  this.parseIntDouble_needsRadixConversion_ = BranchProfile.create();
                  state_0 |= doubleCast0 << 7;
                  int var16;
                  this.state_0_ = var16 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return this.parseIntDouble(arguments0Value_x, arguments1Value, this.parseIntDouble_needsRadixConversion_);
               }
            }

            if (arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_xx = (TruffleString)arguments0Value;
               if (arguments1Value instanceof Integer) {
                  int arguments1Value_ = (Integer)arguments1Value;
                  if (arguments1Value_ == 10 && JSGuards.stringLength(arguments0Value_xx) < 15) {
                     this.parseIntStringInt10_readRawNode_ = super.insert(TruffleString.ReadCharUTF16Node.create());
                     int var14;
                     this.state_0_ = var14 = state_0 | 32;
                     lock.unlock();
                     hasLock = false;
                     return this.parseIntStringInt10(arguments0Value_xx, arguments1Value_, this.parseIntStringInt10_readRawNode_);
                  }
               }
            }

            if (GlobalBuiltins.JSGlobalParseIntNode.isShortStringInt10(arguments0Value, arguments1Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            } else {
               GlobalBuiltinsFactory.JSGlobalParseIntNodeGen.ParseIntGenericData s6_ = super.insert(
                  new GlobalBuiltinsFactory.JSGlobalParseIntNodeGen.ParseIntGenericData()
               );
               s6_.toStringNode_ = s6_.insertAccessor(JSToStringNode.create());
               s6_.needsRadix16_ = BranchProfile.create();
               s6_.needsDontFitLong_ = BranchProfile.create();
               s6_.readRawNode_ = s6_.insertAccessor(TruffleString.ReadCharUTF16Node.create());
               s6_.substringNode_ = s6_.insertAccessor(TruffleString.SubstringByteIndexNode.create());
               VarHandle.storeStoreFence();
               this.parseIntGeneric_cache = s6_;
               int var13;
               this.state_0_ = var13 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return this.parseIntGeneric(
                  arguments0Value, arguments1Value, s6_.toStringNode_, s6_.needsRadix16_, s6_.needsDontFitLong_, s6_.readRawNode_, s6_.substringNode_
               );
            }
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if ((state_0 & 127) == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & 127 & (state_0 & 127) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[8];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"parseIntNoRadix", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"parseIntInt", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.parseIntInt_needsRadixConversion_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"parseIntDoubleToInt", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"parseIntDoubleNoRadix", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"parseIntDouble", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.parseIntDouble_needsRadixConversion_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"parseIntStringInt10", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.parseIntStringInt10_readRawNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"parseIntGeneric", null, null};
         if ((state_0 & 64) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            GlobalBuiltinsFactory.JSGlobalParseIntNodeGen.ParseIntGenericData s6_ = this.parseIntGeneric_cache;
            if (s6_ != null) {
               cached.add(Arrays.asList(s6_.toStringNode_, s6_.needsRadix16_, s6_.needsDontFitLong_, s6_.readRawNode_, s6_.substringNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         return Introspection.Provider.create(data);
      }

      public static GlobalBuiltins.JSGlobalParseIntNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new GlobalBuiltinsFactory.JSGlobalParseIntNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(GlobalBuiltins.JSGlobalParseIntNode.class)
      private static final class ParseIntGenericData extends Node {
         @Node.Child
         JSToStringNode toStringNode_;
         @CompilerDirectives.CompilationFinal
         BranchProfile needsRadix16_;
         @CompilerDirectives.CompilationFinal
         BranchProfile needsDontFitLong_;
         @Node.Child
         TruffleString.ReadCharUTF16Node readRawNode_;
         @Node.Child
         TruffleString.SubstringByteIndexNode substringNode_;

         ParseIntGenericData() {
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

   @GeneratedBy(GlobalBuiltins.JSGlobalPrintNode.class)
   public static final class JSGlobalPrintNodeGen extends GlobalBuiltins.JSGlobalPrintNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSGlobalPrintNodeGen(JSContext context, JSBuiltin builtin, boolean useErr, boolean noNewline, JavaScriptNode[] arguments) {
         super(context, builtin, useErr, noNewline);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object executeObjectArray(Object[] arguments0Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return this.print(arguments0Value);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value);
         }
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if (state_0 != 0 && arguments0Value_ instanceof Object[]) {
            Object[] arguments0Value__ = (Object[])arguments0Value_;
            return this.print(arguments0Value__);
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
            return this.print(arguments0Value_);
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
         Object[] s = new Object[]{"print", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static GlobalBuiltins.JSGlobalPrintNode create(JSContext context, JSBuiltin builtin, boolean useErr, boolean noNewline, JavaScriptNode[] arguments) {
         return new GlobalBuiltinsFactory.JSGlobalPrintNodeGen(context, builtin, useErr, noNewline, arguments);
      }
   }

   @GeneratedBy(GlobalBuiltins.JSGlobalReadBufferNode.class)
   public static final class JSGlobalReadBufferNodeGen extends GlobalBuiltins.JSGlobalReadBufferNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSGlobalReadBufferNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.readbuffer(arguments0Value_);
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
         Object[] s = new Object[]{"readbuffer", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static GlobalBuiltins.JSGlobalReadBufferNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new GlobalBuiltinsFactory.JSGlobalReadBufferNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(GlobalBuiltins.JSGlobalReadFullyNode.class)
   public static final class JSGlobalReadFullyNodeGen extends GlobalBuiltins.JSGlobalReadFullyNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSGlobalReadFullyNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.read(arguments0Value_);
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
         Object[] s = new Object[]{"read", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static GlobalBuiltins.JSGlobalReadFullyNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new GlobalBuiltinsFactory.JSGlobalReadFullyNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(GlobalBuiltins.JSGlobalReadLineNode.class)
   public static final class JSGlobalReadLineNodeGen extends GlobalBuiltins.JSGlobalReadLineNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSGlobalReadLineNodeGen(JSContext context, JSBuiltin builtin, boolean returnNullWhenEmpty, JavaScriptNode[] arguments) {
         super(context, builtin, returnNullWhenEmpty);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.readLine(arguments0Value_);
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
         Object[] s = new Object[]{"readLine", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static GlobalBuiltins.JSGlobalReadLineNode create(JSContext context, JSBuiltin builtin, boolean returnNullWhenEmpty, JavaScriptNode[] arguments) {
         return new GlobalBuiltinsFactory.JSGlobalReadLineNodeGen(context, builtin, returnNullWhenEmpty, arguments);
      }
   }

   @GeneratedBy(GlobalBuiltins.JSGlobalUnEscapeNode.class)
   public static final class JSGlobalUnEscapeNodeGen extends GlobalBuiltins.JSGlobalUnEscapeNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSGlobalUnEscapeNodeGen(JSContext context, JSBuiltin builtin, boolean unescape, JavaScriptNode[] arguments) {
         super(context, builtin, unescape);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.escape(arguments0Value_);
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
         Object[] s = new Object[]{"escape", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static GlobalBuiltins.JSGlobalUnEscapeNode create(JSContext context, JSBuiltin builtin, boolean unescape, JavaScriptNode[] arguments) {
         return new GlobalBuiltinsFactory.JSGlobalUnEscapeNodeGen(context, builtin, unescape, arguments);
      }
   }
}
