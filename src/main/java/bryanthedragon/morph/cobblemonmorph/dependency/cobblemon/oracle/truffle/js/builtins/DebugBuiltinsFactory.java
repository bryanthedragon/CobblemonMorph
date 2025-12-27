package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(DebugBuiltins.class)
public final class DebugBuiltinsFactory {
   @GeneratedBy(DebugBuiltins.DebugArrayTypeNode.class)
   public static final class DebugArrayTypeNodeGen extends DebugBuiltins.DebugArrayTypeNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private DebugArrayTypeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.arraytype(arguments0Value_);
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
         Object[] s = new Object[]{"arraytype", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugArrayTypeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugArrayTypeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugAssertIntNode.class)
   public static final class DebugAssertIntNodeGen extends DebugBuiltins.DebugAssertIntNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private DebugAssertIntNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.assertInt(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"assertInt", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugAssertIntNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugAssertIntNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugClassNameNode.class)
   public static final class DebugClassNameNodeGen extends DebugBuiltins.DebugClassNameNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private DebugClassNameNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return DebugBuiltins.DebugClassNameNode.clazz(arguments0Value_);
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
         Object[] s = new Object[]{"clazz", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugClassNameNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugClassNameNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugClassNode.class)
   public static final class DebugClassNodeGen extends DebugBuiltins.DebugClassNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private DebugClassNodeGen(JSContext context, JSBuiltin builtin, boolean getName, JavaScriptNode[] arguments) {
         super(context, builtin, getName);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.clazz(arguments0Value_);
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
         Object[] s = new Object[]{"clazz", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugClassNode create(JSContext context, JSBuiltin builtin, boolean getName, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugClassNodeGen(context, builtin, getName, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugContinueInInterpreter.class)
   public static final class DebugContinueInInterpreterNodeGen extends DebugBuiltins.DebugContinueInInterpreter implements Introspection.Provider {
      private DebugContinueInInterpreterNodeGen(JSContext context, JSBuiltin builtin, boolean invalidate, JavaScriptNode[] arguments) {
         super(context, builtin, invalidate);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         return this.continueInInterpreter();
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
         Object[] s = new Object[]{"continueInInterpreter", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugContinueInInterpreter create(JSContext context, JSBuiltin builtin, boolean invalidate, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugContinueInInterpreterNodeGen(context, builtin, invalidate, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugCreateSafeInteger.class)
   public static final class DebugCreateSafeIntegerNodeGen extends DebugBuiltins.DebugCreateSafeInteger implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private DebugCreateSafeIntegerNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return (state_0 & 6) == 0 && state_0 != 0 ? this.execute_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
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

         return DebugBuiltins.DebugCreateSafeInteger.createSafeInteger(arguments0Value_);
      }

      private Object execute_generic1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
            int arguments0Value__ = (Integer)arguments0Value_;
            return DebugBuiltins.DebugCreateSafeInteger.createSafeInteger(arguments0Value__);
         } else if ((state_0 & 2) != 0 && arguments0Value_ instanceof SafeInteger) {
            SafeInteger arguments0Value__ = (SafeInteger)arguments0Value_;
            return DebugBuiltins.DebugCreateSafeInteger.createSafeInteger(arguments0Value__);
         } else if ((state_0 & 4) != 0) {
            return DebugBuiltins.DebugCreateSafeInteger.createSafeInteger(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private SafeInteger executeAndSpecialize(Object arguments0Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof Integer) {
            int arguments0Value_ = (Integer)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return DebugBuiltins.DebugCreateSafeInteger.createSafeInteger(arguments0Value_);
         } else if (arguments0Value instanceof SafeInteger) {
            SafeInteger arguments0Value_ = (SafeInteger)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return DebugBuiltins.DebugCreateSafeInteger.createSafeInteger(arguments0Value_);
         } else {
            int var4;
            this.state_0_ = var4 = state_0 | 4;
            return DebugBuiltins.DebugCreateSafeInteger.createSafeInteger(arguments0Value);
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
         Object[] s = new Object[]{"createSafeInteger", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"createSafeInteger", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"createSafeInteger", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugCreateSafeInteger create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugCreateSafeIntegerNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugDumpCountersNode.class)
   public static final class DebugDumpCountersNodeGen extends DebugBuiltins.DebugDumpCountersNode implements Introspection.Provider {
      private DebugDumpCountersNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         return DebugBuiltins.DebugDumpCountersNode.dumpCounters();
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
         Object[] s = new Object[]{"dumpCounters", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugDumpCountersNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugDumpCountersNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugDumpFunctionTreeNode.class)
   public static final class DebugDumpFunctionTreeNodeGen extends DebugBuiltins.DebugDumpFunctionTreeNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private DebugDumpFunctionTreeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.dumpFunctionTree(arguments0Value_);
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
         Object[] s = new Object[]{"dumpFunctionTree", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugDumpFunctionTreeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugDumpFunctionTreeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugHeapDumpNode.class)
   public static final class DebugHeapDumpNodeGen extends DebugBuiltins.DebugHeapDumpNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private DebugHeapDumpNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.heapDump(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"heapDump", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugHeapDumpNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugHeapDumpNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugIsHolesArrayNode.class)
   public static final class DebugIsHolesArrayNodeGen extends DebugBuiltins.DebugIsHolesArrayNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private DebugIsHolesArrayNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public boolean executeBoolean(Object arguments0Value) {
         return this.isHolesArray(arguments0Value);
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.isHolesArray(arguments0Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.isHolesArray(arguments0Value_);
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
         Object[] s = new Object[]{"isHolesArray", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugIsHolesArrayNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugIsHolesArrayNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugJSStackNode.class)
   public static final class DebugJSStackNodeGen extends DebugBuiltins.DebugJSStackNode implements Introspection.Provider {
      private DebugJSStackNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         return this.printJSStack();
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
         Object[] s = new Object[]{"printJSStack", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugJSStackNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugJSStackNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugLoadModuleNode.class)
   public static final class DebugLoadModuleNodeGen extends DebugBuiltins.DebugLoadModuleNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private DebugLoadModuleNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.loadModule(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"loadModule", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugLoadModuleNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugLoadModuleNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugNeverPartOfCompilationNode.class)
   public static final class DebugNeverPartOfCompilationNodeGen extends DebugBuiltins.DebugNeverPartOfCompilationNode implements Introspection.Provider {
      private DebugNeverPartOfCompilationNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         return DebugBuiltins.DebugNeverPartOfCompilationNode.neverPartOfCompilation();
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
         Object[] s = new Object[]{"neverPartOfCompilation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugNeverPartOfCompilationNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugNeverPartOfCompilationNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugPrintObjectNode.class)
   public static final class DebugPrintObjectNodeGen extends DebugBuiltins.DebugPrintObjectNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private DebugPrintObjectNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.printObject(arguments0Value__, arguments1Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.printObject(arguments0Value_, arguments1Value);
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
         Object[] s = new Object[]{"printObject", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugPrintObjectNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugPrintObjectNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugPrintSourceAttribution.class)
   public static final class DebugPrintSourceAttributionNodeGen extends DebugBuiltins.DebugPrintSourceAttribution implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private DebugPrintSourceAttributionNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSFunctionObject) {
            JSFunctionObject arguments0Value__ = (JSFunctionObject)arguments0Value_;
            return this.printSourceAttribution(arguments0Value__);
         } else if ((state_0 & 2) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            return this.printSourceAttribution(arguments0Value__);
         } else if ((state_0 & 4) != 0 && fallbackGuard_(state_0, arguments0Value_)) {
            return this.illegalArgument(arguments0Value_);
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
         if (arguments0Value instanceof JSFunctionObject) {
            JSFunctionObject arguments0Value_ = (JSFunctionObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.printSourceAttribution(arguments0Value_);
         } else if (arguments0Value instanceof TruffleString) {
            TruffleString arguments0Value_ = (TruffleString)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return this.printSourceAttribution(arguments0Value_);
         } else {
            int var4;
            this.state_0_ = var4 = state_0 | 4;
            return this.illegalArgument(arguments0Value);
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
         Object[] s = new Object[]{"printSourceAttribution", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"printSourceAttribution", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"illegalArgument", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
         return (state_0 & 1) == 0 && arguments0Value instanceof JSFunctionObject ? false : (state_0 & 2) != 0 || !(arguments0Value instanceof TruffleString);
      }

      public static DebugBuiltins.DebugPrintSourceAttribution create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugPrintSourceAttributionNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugShapeNode.class)
   public static final class DebugShapeNodeGen extends DebugBuiltins.DebugShapeNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private DebugShapeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return DebugBuiltins.DebugShapeNode.shape(arguments0Value_);
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
         Object[] s = new Object[]{"shape", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugShapeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugShapeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugStringCompareNode.class)
   public static final class DebugStringCompareNodeGen extends DebugBuiltins.DebugStringCompareNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.CompareCharsUTF16Node compareNode_;

      private DebugStringCompareNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.stringCompare(arguments0Value_, arguments1Value_, this.compareNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public int executeInt(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            return this.stringCompare(arguments0Value_, arguments1Value_, this.compareNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeInt(frameValue);
      }

      private int executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var6;
         try {
            int state_0 = this.state_0_;
            this.compareNode_ = super.insert(TruffleString.CompareCharsUTF16Node.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.stringCompare(arguments0Value, arguments1Value, this.compareNode_);
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
         Object[] s = new Object[]{"stringCompare", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.compareNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugStringCompareNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugStringCompareNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugSystemProperties.class)
   public static final class DebugSystemPropertiesNodeGen extends DebugBuiltins.DebugSystemProperties implements Introspection.Provider {
      private DebugSystemPropertiesNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         return this.systemProperties();
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
         Object[] s = new Object[]{"systemProperties", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugSystemProperties create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugSystemPropertiesNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugSystemProperty.class)
   public static final class DebugSystemPropertyNodeGen extends DebugBuiltins.DebugSystemProperty implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private DebugSystemPropertyNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return DebugBuiltins.DebugSystemProperty.systemProperty(arguments0Value_);
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
         Object[] s = new Object[]{"systemProperty", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugSystemProperty create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugSystemPropertyNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugToJavaStringNode.class)
   public static final class DebugToJavaStringNodeGen extends DebugBuiltins.DebugToJavaStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private DebugToJavaStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return DebugBuiltins.DebugToJavaStringNode.toJavaString(arguments0Value_);
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
         Object[] s = new Object[]{"toJavaString", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugToJavaStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugToJavaStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DebugBuiltins.DebugTypedArrayDetachBufferNode.class)
   public static final class DebugTypedArrayDetachBufferNodeGen extends DebugBuiltins.DebugTypedArrayDetachBufferNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private DebugTypedArrayDetachBufferNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return DebugBuiltins.DebugTypedArrayDetachBufferNode.detachBuffer(arguments0Value_);
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
         Object[] s = new Object[]{"detachBuffer", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DebugBuiltins.DebugTypedArrayDetachBufferNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DebugBuiltinsFactory.DebugTypedArrayDetachBufferNodeGen(context, builtin, arguments);
      }
   }
}
