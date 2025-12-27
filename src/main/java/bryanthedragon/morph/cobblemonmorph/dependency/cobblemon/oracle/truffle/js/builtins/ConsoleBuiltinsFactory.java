package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(ConsoleBuiltins.class)
public final class ConsoleBuiltinsFactory {
   @GeneratedBy(ConsoleBuiltins.JSConsoleAssertNode.class)
   public static final class JSConsoleAssertNodeGen extends ConsoleBuiltins.JSConsoleAssertNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSConsoleAssertNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.assertImpl(arguments0Value__);
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
            return this.assertImpl(arguments0Value_);
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
         Object[] s = new Object[]{"assertImpl", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConsoleBuiltins.JSConsoleAssertNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConsoleBuiltinsFactory.JSConsoleAssertNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConsoleBuiltins.JSConsoleClearNode.class)
   public static final class JSConsoleClearNodeGen extends ConsoleBuiltins.JSConsoleClearNode implements Introspection.Provider {
      private JSConsoleClearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         return this.clear();
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
         Object[] s = new Object[]{"clear", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConsoleBuiltins.JSConsoleClearNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConsoleBuiltinsFactory.JSConsoleClearNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConsoleBuiltins.JSConsoleCountNode.class)
   public static final class JSConsoleCountNodeGen extends ConsoleBuiltins.JSConsoleCountNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSConsoleCountNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.count(arguments0Value_);
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
         Object[] s = new Object[]{"count", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConsoleBuiltins.JSConsoleCountNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConsoleBuiltinsFactory.JSConsoleCountNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConsoleBuiltins.JSConsoleCountResetNode.class)
   public static final class JSConsoleCountResetNodeGen extends ConsoleBuiltins.JSConsoleCountResetNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSConsoleCountResetNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.count(arguments0Value_);
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
         Object[] s = new Object[]{"count", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConsoleBuiltins.JSConsoleCountResetNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConsoleBuiltinsFactory.JSConsoleCountResetNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConsoleBuiltins.JSConsoleGroupEndNode.class)
   public static final class JSConsoleGroupEndNodeGen extends ConsoleBuiltins.JSConsoleGroupEndNode implements Introspection.Provider {
      private JSConsoleGroupEndNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         return this.groupEnd();
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
         Object[] s = new Object[]{"groupEnd", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConsoleBuiltins.JSConsoleGroupEndNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConsoleBuiltinsFactory.JSConsoleGroupEndNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConsoleBuiltins.JSConsoleGroupNode.class)
   public static final class JSConsoleGroupNodeGen extends ConsoleBuiltins.JSConsoleGroupNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSConsoleGroupNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.group(arguments0Value__);
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
            return this.group(arguments0Value_);
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
         Object[] s = new Object[]{"group", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConsoleBuiltins.JSConsoleGroupNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConsoleBuiltinsFactory.JSConsoleGroupNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConsoleBuiltins.JSConsoleTimeEndNode.class)
   public static final class JSConsoleTimeEndNodeGen extends ConsoleBuiltins.JSConsoleTimeEndNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSConsoleTimeEndNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.timeEnd(arguments0Value_);
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
         Object[] s = new Object[]{"timeEnd", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConsoleBuiltins.JSConsoleTimeEndNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConsoleBuiltinsFactory.JSConsoleTimeEndNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConsoleBuiltins.JSConsoleTimeLogNode.class)
   public static final class JSConsoleTimeLogNodeGen extends ConsoleBuiltins.JSConsoleTimeLogNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSConsoleTimeLogNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.timeLog(arguments0Value__);
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
            return this.timeLog(arguments0Value_);
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
         Object[] s = new Object[]{"timeLog", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConsoleBuiltins.JSConsoleTimeLogNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConsoleBuiltinsFactory.JSConsoleTimeLogNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ConsoleBuiltins.JSConsoleTimeNode.class)
   public static final class JSConsoleTimeNodeGen extends ConsoleBuiltins.JSConsoleTimeNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSConsoleTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.time(arguments0Value_);
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
         Object[] s = new Object[]{"time", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ConsoleBuiltins.JSConsoleTimeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ConsoleBuiltinsFactory.JSConsoleTimeNodeGen(context, builtin, arguments);
      }
   }
}
