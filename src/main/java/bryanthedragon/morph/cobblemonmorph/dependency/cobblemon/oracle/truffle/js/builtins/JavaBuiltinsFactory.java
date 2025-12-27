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
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JavaBuiltins.class)
public final class JavaBuiltinsFactory {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   @GeneratedBy(JavaBuiltins.JavaAddToClasspathNode.class)
   static final class JavaAddToClasspathNodeGen extends JavaBuiltins.JavaAddToClasspathNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private JSToStringNode object_toStringNode_;

      private JavaAddToClasspathNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.doString(arguments0Value__);
         } else if ((state_0 & 2) != 0) {
            return this.doObject(arguments0Value_, this.object_toStringNode_);
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

         Object var7;
         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude != 0 || !(arguments0Value instanceof TruffleString)) {
               this.object_toStringNode_ = super.insert(JSToStringNode.create());
               int var14;
               this.exclude_ = var14 = exclude | 1;
               state_0 &= -2;
               int var13;
               this.state_0_ = var13 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doObject(arguments0Value, this.object_toStringNode_);
            }

            TruffleString arguments0Value_ = (TruffleString)arguments0Value;
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.doString(arguments0Value_);
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
         Object[] s = new Object[]{"doString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.object_toStringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static JavaBuiltins.JavaAddToClasspathNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new JavaBuiltinsFactory.JavaAddToClasspathNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(JavaBuiltins.JavaExtendNode.class)
   static final class JavaExtendNodeGen extends JavaBuiltins.JavaExtendNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JavaExtendNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.extend(arguments0Value__);
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
            return this.extend(arguments0Value_);
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
         Object[] s = new Object[]{"extend", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static JavaBuiltins.JavaExtendNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new JavaBuiltinsFactory.JavaExtendNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(JavaBuiltins.JavaFromNode.class)
   static final class JavaFromNodeGen extends JavaBuiltins.JavaFromNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JavaBuiltinsFactory.JavaFromNodeGen.FromData from_cache;

      private JavaFromNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            JavaBuiltinsFactory.JavaFromNodeGen.FromData s0_ = this.from_cache;
            if (s0_ != null) {
               return this.from(arguments0Value_, s0_.interop_, s0_.importValueNode_, s0_.writeNode_, s0_.errorBranch_);
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

         JSDynamicObject var6;
         try {
            int state_0 = this.state_0_;
            JavaBuiltinsFactory.JavaFromNodeGen.FromData s0_ = super.insert(new JavaBuiltinsFactory.JavaFromNodeGen.FromData());
            s0_.interop_ = s0_.insertAccessor(JavaBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
            s0_.importValueNode_ = s0_.insertAccessor(ImportValueNode.create());
            s0_.writeNode_ = s0_.insertAccessor(WriteElementNode.createCachedInterop());
            s0_.errorBranch_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.from_cache = s0_;
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.from(arguments0Value, s0_.interop_, s0_.importValueNode_, s0_.writeNode_, s0_.errorBranch_);
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
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"from", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            JavaBuiltinsFactory.JavaFromNodeGen.FromData s0_ = this.from_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.interop_, s0_.importValueNode_, s0_.writeNode_, s0_.errorBranch_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static JavaBuiltins.JavaFromNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new JavaBuiltinsFactory.JavaFromNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(JavaBuiltins.JavaFromNode.class)
      private static final class FromData extends Node {
         @Node.Child
         InteropLibrary interop_;
         @Node.Child
         ImportValueNode importValueNode_;
         @Node.Child
         WriteElementNode writeNode_;
         @CompilerDirectives.CompilationFinal
         BranchProfile errorBranch_;

         FromData() {
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

   @GeneratedBy(JavaBuiltins.JavaIsJavaFunctionNode.class)
   static final class JavaIsJavaFunctionNodeGen extends JavaBuiltins.JavaIsJavaFunctionNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private InteropLibrary interop_;

      private JavaIsJavaFunctionNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.isJavaFunction(arguments0Value_, this.interop_);
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
            return this.isJavaFunction(arguments0Value_, this.interop_);
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
            this.interop_ = super.insert(JavaBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.isJavaFunction(arguments0Value, this.interop_);
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
         Object[] s = new Object[]{"isJavaFunction", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.interop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static JavaBuiltins.JavaIsJavaFunctionNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new JavaBuiltinsFactory.JavaIsJavaFunctionNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(JavaBuiltins.JavaIsJavaMethodNode.class)
   static final class JavaIsJavaMethodNodeGen extends JavaBuiltins.JavaIsJavaMethodNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JavaIsJavaMethodNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.isJavaMethod(arguments0Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.isJavaMethod(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"isJavaMethod", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static JavaBuiltins.JavaIsJavaMethodNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new JavaBuiltinsFactory.JavaIsJavaMethodNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(JavaBuiltins.JavaIsJavaObject.class)
   static final class JavaIsJavaObjectNodeGen extends JavaBuiltins.JavaIsJavaObject implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JavaIsJavaObjectNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.isJavaObject(arguments0Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.isJavaObject(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"isJavaObject", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static JavaBuiltins.JavaIsJavaObject create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new JavaBuiltinsFactory.JavaIsJavaObjectNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(JavaBuiltins.JavaIsScriptFunctionNode.class)
   static final class JavaIsScriptFunctionNodeGen extends JavaBuiltins.JavaIsScriptFunctionNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JavaIsScriptFunctionNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return JavaBuiltins.JavaIsScriptFunctionNode.isScriptFunction(arguments0Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return JavaBuiltins.JavaIsScriptFunctionNode.isScriptFunction(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"isScriptFunction", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static JavaBuiltins.JavaIsScriptFunctionNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new JavaBuiltinsFactory.JavaIsScriptFunctionNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(JavaBuiltins.JavaIsScriptObjectNode.class)
   static final class JavaIsScriptObjectNodeGen extends JavaBuiltins.JavaIsScriptObjectNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JavaIsScriptObjectNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return JavaBuiltins.JavaIsScriptObjectNode.isScriptObject(arguments0Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return JavaBuiltins.JavaIsScriptObjectNode.isScriptObject(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"isScriptObject", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static JavaBuiltins.JavaIsScriptObjectNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new JavaBuiltinsFactory.JavaIsScriptObjectNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(JavaBuiltins.JavaIsTypeNode.class)
   static final class JavaIsTypeNodeGen extends JavaBuiltins.JavaIsTypeNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JavaIsTypeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.isType(arguments0Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.isType(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"isType", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static JavaBuiltins.JavaIsTypeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new JavaBuiltinsFactory.JavaIsTypeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(JavaBuiltins.JavaSuperNode.class)
   static final class JavaSuperNodeGen extends JavaBuiltins.JavaSuperNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JavaSuperNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.superAdapter(arguments0Value_);
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
         Object[] s = new Object[]{"superAdapter", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static JavaBuiltins.JavaSuperNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new JavaBuiltinsFactory.JavaSuperNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(JavaBuiltins.JavaSynchronizedNode.class)
   static final class JavaSynchronizedNodeGen extends JavaBuiltins.JavaSynchronizedNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JavaSynchronizedNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.doSynchronize(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"doSynchronize", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static JavaBuiltins.JavaSynchronizedNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new JavaBuiltinsFactory.JavaSynchronizedNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(JavaBuiltins.JavaToNode.class)
   static final class JavaToNodeGen extends JavaBuiltins.JavaToNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private InteropLibrary to_interop_;
      @Node.Child
      private JavaBuiltinsFactory.JavaToNodeGen.ToNonObject0Data toNonObject0_cache;
      @Node.Child
      private InteropLibrary toNonObject1_typeInterop_;

      private JavaToNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
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
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && JSGuards.isJSObject(arguments0Value_)) {
               return this.to(arguments0Value_, arguments1Value_, this.to_interop_);
            }

            if ((state_0 & 2) != 0) {
               for (JavaBuiltinsFactory.JavaToNodeGen.ToNonObject0Data s1_ = this.toNonObject0_cache; s1_ != null; s1_ = s1_.next_) {
                  if (s1_.objInterop_.accepts(arguments0Value_) && !JSGuards.isJSObject(arguments0Value_)) {
                     return this.toNonObject(arguments0Value_, arguments1Value_, s1_.objInterop_, s1_.typeInterop_);
                  }
               }
            }

            if ((state_0 & 4) != 0 && !JSGuards.isJSObject(arguments0Value_)) {
               return this.toNonObject1Boundary(state_0, arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object toNonObject1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         Object var7;
         try {
            InteropLibrary toNonObject1_objInterop__ = JavaBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var7 = this.toNonObject(arguments0Value_, arguments1Value_, toNonObject1_objInterop__, this.toNonObject1_typeInterop_);
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
            if (JSGuards.isJSObject(arguments0Value)) {
               this.to_interop_ = super.insert(JavaBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
               int var21;
               this.state_0_ = var21 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.to(arguments0Value, arguments1Value, this.to_interop_);
            } else {
               if (exclude == 0) {
                  int count1_ = 0;
                  JavaBuiltinsFactory.JavaToNodeGen.ToNonObject0Data s1_ = this.toNonObject0_cache;
                  if ((state_0 & 2) != 0) {
                     while (s1_ != null && (!s1_.objInterop_.accepts(arguments0Value) || JSGuards.isJSObject(arguments0Value))) {
                        s1_ = s1_.next_;
                        count1_++;
                     }
                  }

                  if (s1_ == null && !JSGuards.isJSObject(arguments0Value) && count1_ < 5) {
                     s1_ = super.insert(new JavaBuiltinsFactory.JavaToNodeGen.ToNonObject0Data(this.toNonObject0_cache));
                     s1_.objInterop_ = s1_.insertAccessor(JavaBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     s1_.typeInterop_ = s1_.insertAccessor(JavaBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
                     VarHandle.storeStoreFence();
                     this.toNonObject0_cache = s1_;
                     this.state_0_ = state_0 |= 2;
                  }

                  if (s1_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.toNonObject(arguments0Value, arguments1Value, s1_.objInterop_, s1_.typeInterop_);
                  }
               }

               InteropLibrary toNonObject1_objInterop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (!JSGuards.isJSObject(arguments0Value)) {
                     toNonObject1_objInterop__ = JavaBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     this.toNonObject1_typeInterop_ = super.insert(JavaBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
                     int var22;
                     this.exclude_ = var22 = exclude | 1;
                     this.toNonObject0_cache = null;
                     state_0 &= -3;
                     int var20;
                     this.state_0_ = var20 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.toNonObject(arguments0Value, arguments1Value, toNonObject1_objInterop__, this.toNonObject1_typeInterop_);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               JavaBuiltinsFactory.JavaToNodeGen.ToNonObject0Data s1_ = this.toNonObject0_cache;
               if (s1_ == null || s1_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"to", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.to_interop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"toNonObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (JavaBuiltinsFactory.JavaToNodeGen.ToNonObject0Data s1_ = this.toNonObject0_cache; s1_ != null; s1_ = s1_.next_) {
               cached.add(Arrays.asList(s1_.objInterop_, s1_.typeInterop_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"toNonObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toNonObject1_typeInterop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static JavaBuiltins.JavaToNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new JavaBuiltinsFactory.JavaToNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(JavaBuiltins.JavaToNode.class)
      private static final class ToNonObject0Data extends Node {
         @Node.Child
         JavaBuiltinsFactory.JavaToNodeGen.ToNonObject0Data next_;
         @Node.Child
         InteropLibrary objInterop_;
         @Node.Child
         InteropLibrary typeInterop_;

         ToNonObject0Data(JavaBuiltinsFactory.JavaToNodeGen.ToNonObject0Data next_) {
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

   @GeneratedBy(JavaBuiltins.JavaTypeNameNode.class)
   static final class JavaTypeNameNodeGen extends JavaBuiltins.JavaTypeNameNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private InteropLibrary typeNameJavaInteropClass_typeInterop_;
      @Node.Child
      private InteropLibrary typeNameJavaInteropClass_stringInterop_;

      private JavaTypeNameNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      private boolean fallbackGuard_(int state_0, Object arguments0Value) {
         return (state_0 & 1) != 0 || (state_0 & 2) != 0 && !this.isJavaInteropClass(arguments0Value, this.typeNameJavaInteropClass_typeInterop_);
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 5) != 0) {
            if ((state_0 & 1) != 0 && this.isJavaInteropClass(arguments0Value_, this.typeNameJavaInteropClass_typeInterop_)) {
               return this.typeNameJavaInteropClass(arguments0Value_, this.typeNameJavaInteropClass_typeInterop_, this.typeNameJavaInteropClass_stringInterop_);
            }

            if ((state_0 & 4) != 0 && this.fallbackGuard_(state_0, arguments0Value_)) {
               return this.nonType(arguments0Value_);
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

         TruffleString var5;
         try {
            int state_0 = this.state_0_;
            if ((state_0 & 2) == 0) {
               this.typeNameJavaInteropClass_typeInterop_ = super.insert(JavaBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
               this.state_0_ = state_0 |= 2;
            }

            if (!this.isJavaInteropClass(arguments0Value, this.typeNameJavaInteropClass_typeInterop_)) {
               int var10;
               this.state_0_ = var10 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.nonType(arguments0Value);
            }

            this.typeNameJavaInteropClass_typeInterop_ = super.insert(JavaBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
            this.typeNameJavaInteropClass_stringInterop_ = super.insert(JavaBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.typeNameJavaInteropClass(arguments0Value, this.typeNameJavaInteropClass_typeInterop_, this.typeNameJavaInteropClass_stringInterop_);
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
         if ((state_0 & 5) == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & 5 & (state_0 & 5) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"typeNameJavaInteropClass", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.typeNameJavaInteropClass_typeInterop_, this.typeNameJavaInteropClass_stringInterop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"nonType", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static JavaBuiltins.JavaTypeNameNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new JavaBuiltinsFactory.JavaTypeNameNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(JavaBuiltins.JavaTypeNode.class)
   static final class JavaTypeNodeGen extends JavaBuiltins.JavaTypeNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JavaTypeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.type(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isString(arguments0Value_)) {
            return this.typeNoString(arguments0Value_);
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
         if (arguments0Value instanceof TruffleString) {
            TruffleString arguments0Value_ = (TruffleString)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.type(arguments0Value_);
         } else if (!JSGuards.isString(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.typeNoString(arguments0Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
         Object[] s = new Object[]{"type", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"typeNoString", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static JavaBuiltins.JavaTypeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new JavaBuiltinsFactory.JavaTypeNodeGen(context, builtin, arguments);
      }
   }
}
