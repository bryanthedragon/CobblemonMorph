package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;

@GeneratedBy(RealmFunctionBuiltins.class)
public final class RealmFunctionBuiltinsFactory {
   @GeneratedBy(RealmFunctionBuiltins.RealmCreateNode.class)
   public static final class RealmCreateNodeGen extends RealmFunctionBuiltins.RealmCreateNode implements Introspection.Provider {
      private RealmCreateNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         return this.createRealm();
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
         Object[] s = new Object[]{"createRealm", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static RealmFunctionBuiltins.RealmCreateNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RealmFunctionBuiltinsFactory.RealmCreateNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RealmFunctionBuiltins.RealmCurrentNode.class)
   public static final class RealmCurrentNodeGen extends RealmFunctionBuiltins.RealmCurrentNode implements Introspection.Provider {
      private RealmCurrentNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         return this.current();
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
         Object[] s = new Object[]{"current", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static RealmFunctionBuiltins.RealmCurrentNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RealmFunctionBuiltinsFactory.RealmCurrentNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RealmFunctionBuiltins.RealmDetachGlobalNode.class)
   public static final class RealmDetachGlobalNodeGen extends RealmFunctionBuiltins.RealmDetachGlobalNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private RealmDetachGlobalNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.detachGlobal(arguments0Value_);
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
         Object[] s = new Object[]{"detachGlobal", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static RealmFunctionBuiltins.RealmDetachGlobalNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RealmFunctionBuiltinsFactory.RealmDetachGlobalNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RealmFunctionBuiltins.RealmDisposeNode.class)
   public static final class RealmDisposeNodeGen extends RealmFunctionBuiltins.RealmDisposeNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private RealmDisposeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.dispose(arguments0Value_);
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
         Object[] s = new Object[]{"dispose", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static RealmFunctionBuiltins.RealmDisposeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RealmFunctionBuiltinsFactory.RealmDisposeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RealmFunctionBuiltins.RealmEvalNode.class)
   public static final class RealmEvalNodeGen extends RealmFunctionBuiltins.RealmEvalNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private RealmEvalNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.eval(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"eval", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static RealmFunctionBuiltins.RealmEvalNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RealmFunctionBuiltinsFactory.RealmEvalNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RealmFunctionBuiltins.RealmGlobalNode.class)
   public static final class RealmGlobalNodeGen extends RealmFunctionBuiltins.RealmGlobalNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private RealmGlobalNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.global(arguments0Value_);
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
         Object[] s = new Object[]{"global", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static RealmFunctionBuiltins.RealmGlobalNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RealmFunctionBuiltinsFactory.RealmGlobalNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RealmFunctionBuiltins.RealmNavigateNode.class)
   public static final class RealmNavigateNodeGen extends RealmFunctionBuiltins.RealmNavigateNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private RealmNavigateNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.navigate(arguments0Value_);
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
         Object[] s = new Object[]{"navigate", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static RealmFunctionBuiltins.RealmNavigateNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RealmFunctionBuiltinsFactory.RealmNavigateNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RealmFunctionBuiltins.RealmOwnerNode.class)
   public static final class RealmOwnerNodeGen extends RealmFunctionBuiltins.RealmOwnerNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private RealmOwnerNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.owner(arguments0Value_);
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
         Object[] s = new Object[]{"owner", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static RealmFunctionBuiltins.RealmOwnerNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RealmFunctionBuiltinsFactory.RealmOwnerNodeGen(context, builtin, arguments);
      }
   }
}
