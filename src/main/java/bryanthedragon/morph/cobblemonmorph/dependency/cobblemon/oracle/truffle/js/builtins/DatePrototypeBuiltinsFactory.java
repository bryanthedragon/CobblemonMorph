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

@GeneratedBy(DatePrototypeBuiltins.class)
public final class DatePrototypeBuiltinsFactory {
   @GeneratedBy(DatePrototypeBuiltins.JSDateGetDateNode.class)
   public static final class JSDateGetDateNodeGen extends DatePrototypeBuiltins.JSDateGetDateNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSDateGetDateNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         super(context, builtin, isUTC);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateGetDateNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateGetDateNodeGen(context, builtin, isUTC, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateGetDayNode.class)
   public static final class JSDateGetDayNodeGen extends DatePrototypeBuiltins.JSDateGetDayNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSDateGetDayNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         super(context, builtin, isUTC);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateGetDayNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateGetDayNodeGen(context, builtin, isUTC, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateGetFullYearNode.class)
   public static final class JSDateGetFullYearNodeGen extends DatePrototypeBuiltins.JSDateGetFullYearNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSDateGetFullYearNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         super(context, builtin, isUTC);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateGetFullYearNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateGetFullYearNodeGen(context, builtin, isUTC, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateGetHoursNode.class)
   public static final class JSDateGetHoursNodeGen extends DatePrototypeBuiltins.JSDateGetHoursNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSDateGetHoursNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         super(context, builtin, isUTC);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateGetHoursNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateGetHoursNodeGen(context, builtin, isUTC, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateGetMillisecondsNode.class)
   public static final class JSDateGetMillisecondsNodeGen extends DatePrototypeBuiltins.JSDateGetMillisecondsNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSDateGetMillisecondsNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         super(context, builtin, isUTC);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateGetMillisecondsNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateGetMillisecondsNodeGen(context, builtin, isUTC, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateGetMinutesNode.class)
   public static final class JSDateGetMinutesNodeGen extends DatePrototypeBuiltins.JSDateGetMinutesNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSDateGetMinutesNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         super(context, builtin, isUTC);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateGetMinutesNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateGetMinutesNodeGen(context, builtin, isUTC, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateGetMonthNode.class)
   public static final class JSDateGetMonthNodeGen extends DatePrototypeBuiltins.JSDateGetMonthNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSDateGetMonthNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         super(context, builtin, isUTC);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateGetMonthNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateGetMonthNodeGen(context, builtin, isUTC, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateGetSecondsNode.class)
   public static final class JSDateGetSecondsNodeGen extends DatePrototypeBuiltins.JSDateGetSecondsNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSDateGetSecondsNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         super(context, builtin, isUTC);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateGetSecondsNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateGetSecondsNodeGen(context, builtin, isUTC, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateGetTimezoneOffsetNode.class)
   public static final class JSDateGetTimezoneOffsetNodeGen extends DatePrototypeBuiltins.JSDateGetTimezoneOffsetNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSDateGetTimezoneOffsetNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.getTimezoneOffset(arguments0Value_);
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.getTimezoneOffset(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"getTimezoneOffset", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateGetTimezoneOffsetNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateGetTimezoneOffsetNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateGetYearNode.class)
   public static final class JSDateGetYearNodeGen extends DatePrototypeBuiltins.JSDateGetYearNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSDateGetYearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.doOperation(arguments0Value_);
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateGetYearNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateGetYearNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateSetDateNode.class)
   public static final class JSDateSetDateNodeGen extends DatePrototypeBuiltins.JSDateSetDateNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSDateSetDateNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         super(context, builtin, isUTC);
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
         return this.doOperation(arguments0Value_, arguments1Value_);
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.doOperation(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateSetDateNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateSetDateNodeGen(context, builtin, isUTC, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateSetFullYearNode.class)
   public static final class JSDateSetFullYearNodeGen extends DatePrototypeBuiltins.JSDateSetFullYearNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSDateSetFullYearNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         super(context, builtin, isUTC);
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
            return this.setFullYear(arguments0Value_, arguments1Value__);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
            Object[] arguments1Value__ = (Object[])arguments1Value_;
            return this.setFullYear(arguments0Value_, arguments1Value__);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      private double executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments1Value instanceof Object[]) {
            Object[] arguments1Value_ = (Object[])arguments1Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.setFullYear(arguments0Value, arguments1Value_);
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
         Object[] s = new Object[]{"setFullYear", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateSetFullYearNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateSetFullYearNodeGen(context, builtin, isUTC, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateSetHoursNode.class)
   public static final class JSDateSetHoursNodeGen extends DatePrototypeBuiltins.JSDateSetHoursNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSDateSetHoursNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         super(context, builtin, isUTC);
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
            return this.setHours(arguments0Value_, arguments1Value__);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
            Object[] arguments1Value__ = (Object[])arguments1Value_;
            return this.setHours(arguments0Value_, arguments1Value__);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      private double executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments1Value instanceof Object[]) {
            Object[] arguments1Value_ = (Object[])arguments1Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.setHours(arguments0Value, arguments1Value_);
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
         Object[] s = new Object[]{"setHours", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateSetHoursNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateSetHoursNodeGen(context, builtin, isUTC, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateSetMillisecondsNode.class)
   public static final class JSDateSetMillisecondsNodeGen extends DatePrototypeBuiltins.JSDateSetMillisecondsNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSDateSetMillisecondsNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         super(context, builtin, isUTC);
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
         return this.setMilliseconds(arguments0Value_, arguments1Value_);
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.setMilliseconds(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"setMilliseconds", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateSetMillisecondsNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateSetMillisecondsNodeGen(context, builtin, isUTC, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateSetMinutesNode.class)
   public static final class JSDateSetMinutesNodeGen extends DatePrototypeBuiltins.JSDateSetMinutesNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSDateSetMinutesNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         super(context, builtin, isUTC);
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
            return this.doOperation(arguments0Value_, arguments1Value__);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
            Object[] arguments1Value__ = (Object[])arguments1Value_;
            return this.doOperation(arguments0Value_, arguments1Value__);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      private double executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments1Value instanceof Object[]) {
            Object[] arguments1Value_ = (Object[])arguments1Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doOperation(arguments0Value, arguments1Value_);
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
         Object[] s = new Object[]{"doOperation", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateSetMinutesNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateSetMinutesNodeGen(context, builtin, isUTC, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateSetMonthNode.class)
   public static final class JSDateSetMonthNodeGen extends DatePrototypeBuiltins.JSDateSetMonthNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSDateSetMonthNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         super(context, builtin, isUTC);
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
            return this.setMonth(arguments0Value_, arguments1Value__);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
            Object[] arguments1Value__ = (Object[])arguments1Value_;
            return this.setMonth(arguments0Value_, arguments1Value__);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      private double executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments1Value instanceof Object[]) {
            Object[] arguments1Value_ = (Object[])arguments1Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.setMonth(arguments0Value, arguments1Value_);
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
         Object[] s = new Object[]{"setMonth", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateSetMonthNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateSetMonthNodeGen(context, builtin, isUTC, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateSetSecondsNode.class)
   public static final class JSDateSetSecondsNodeGen extends DatePrototypeBuiltins.JSDateSetSecondsNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSDateSetSecondsNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         super(context, builtin, isUTC);
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
            return this.setSeconds(arguments0Value_, arguments1Value__);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
            Object[] arguments1Value__ = (Object[])arguments1Value_;
            return this.setSeconds(arguments0Value_, arguments1Value__);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      private double executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments1Value instanceof Object[]) {
            Object[] arguments1Value_ = (Object[])arguments1Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.setSeconds(arguments0Value, arguments1Value_);
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
         Object[] s = new Object[]{"setSeconds", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateSetSecondsNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateSetSecondsNodeGen(context, builtin, isUTC, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateSetTimeNode.class)
   public static final class JSDateSetTimeNodeGen extends DatePrototypeBuiltins.JSDateSetTimeNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSDateSetTimeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.doOperation(arguments0Value_, arguments1Value_);
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.doOperation(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateSetTimeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateSetTimeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateSetYearNode.class)
   public static final class JSDateSetYearNodeGen extends DatePrototypeBuiltins.JSDateSetYearNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSDateSetYearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.setYear(arguments0Value_, arguments1Value_);
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.setYear(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"setYear", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateSetYearNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateSetYearNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateToDateStringNode.class)
   public static final class JSDateToDateStringNodeGen extends DatePrototypeBuiltins.JSDateToDateStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSDateToDateStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.doOperation(arguments0Value_);
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
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateToDateStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateToDateStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateToISOStringNode.class)
   public static final class JSDateToISOStringNodeGen extends DatePrototypeBuiltins.JSDateToISOStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSDateToISOStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.doOperation(arguments0Value_);
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
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateToISOStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateToISOStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateToJSONNode.class)
   public static final class JSDateToJSONNodeGen extends DatePrototypeBuiltins.JSDateToJSONNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSDateToJSONNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.toJSON(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"toJSON", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateToJSONNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateToJSONNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateToLocaleDateStringIntlNode.class)
   public static final class JSDateToLocaleDateStringIntlNodeGen
      extends DatePrototypeBuiltins.JSDateToLocaleDateStringIntlNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;

      private JSDateToLocaleDateStringIntlNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.doOperation(arguments0Value_, arguments1Value_, arguments2Value_);
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
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateToLocaleDateStringIntlNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateToLocaleDateStringIntlNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateToLocaleDateStringNode.class)
   public static final class JSDateToLocaleDateStringNodeGen extends DatePrototypeBuiltins.JSDateToLocaleDateStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSDateToLocaleDateStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.doOperation(arguments0Value_);
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
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateToLocaleDateStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateToLocaleDateStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateToLocaleTimeStringIntlNode.class)
   public static final class JSDateToLocaleTimeStringIntlNodeGen
      extends DatePrototypeBuiltins.JSDateToLocaleTimeStringIntlNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;

      private JSDateToLocaleTimeStringIntlNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.doOperation(arguments0Value_, arguments1Value_, arguments2Value_);
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
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateToLocaleTimeStringIntlNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateToLocaleTimeStringIntlNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateToLocaleTimeStringNode.class)
   public static final class JSDateToLocaleTimeStringNodeGen extends DatePrototypeBuiltins.JSDateToLocaleTimeStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSDateToLocaleTimeStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.doOperation(arguments0Value_);
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
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateToLocaleTimeStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateToLocaleTimeStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateToPrimitiveNode.class)
   public static final class JSDateToPrimitiveNodeGen extends DatePrototypeBuiltins.JSDateToPrimitiveNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSDateToPrimitiveNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.toPrimitive(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"toPrimitive", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateToPrimitiveNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateToPrimitiveNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateToStringIntlNode.class)
   public static final class JSDateToStringIntlNodeGen extends DatePrototypeBuiltins.JSDateToStringIntlNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;

      private JSDateToStringIntlNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         super(context, builtin, isUTC);
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
         return this.doOperation(arguments0Value_, arguments1Value_, arguments2Value_);
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
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateToStringIntlNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateToStringIntlNodeGen(context, builtin, isUTC, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateToStringNode.class)
   public static final class JSDateToStringNodeGen extends DatePrototypeBuiltins.JSDateToStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSDateToStringNodeGen(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         super(context, builtin, isUTC);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
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
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateToStringNode create(JSContext context, JSBuiltin builtin, boolean isUTC, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateToStringNodeGen(context, builtin, isUTC, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateToTimeStringNode.class)
   public static final class JSDateToTimeStringNodeGen extends DatePrototypeBuiltins.JSDateToTimeStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSDateToTimeStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.doOperation(arguments0Value_);
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
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateToTimeStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateToTimeStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(DatePrototypeBuiltins.JSDateValueOfNode.class)
   public static final class JSDateValueOfNodeGen extends DatePrototypeBuiltins.JSDateValueOfNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSDateValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.doOperation(arguments0Value_);
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.doOperation(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeDouble(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"doOperation", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static DatePrototypeBuiltins.JSDateValueOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new DatePrototypeBuiltinsFactory.JSDateValueOfNodeGen(context, builtin, arguments);
      }
   }
}
