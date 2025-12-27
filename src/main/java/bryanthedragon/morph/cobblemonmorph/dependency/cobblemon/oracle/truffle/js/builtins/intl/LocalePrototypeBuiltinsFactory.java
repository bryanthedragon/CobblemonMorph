package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSLocaleObject;

@GeneratedBy(LocalePrototypeBuiltins.class)
public final class LocalePrototypeBuiltinsFactory {
   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleBaseNameAccessor.class)
   public static final class JSLocaleBaseNameAccessorNodeGen extends LocalePrototypeBuiltins.JSLocaleBaseNameAccessor implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleBaseNameAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleBaseNameAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleBaseNameAccessorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleCalendarAccessor.class)
   public static final class JSLocaleCalendarAccessorNodeGen extends LocalePrototypeBuiltins.JSLocaleCalendarAccessor implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleCalendarAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleCalendarAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleCalendarAccessorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleCalendarsAccessor.class)
   public static final class JSLocaleCalendarsAccessorNodeGen extends LocalePrototypeBuiltins.JSLocaleCalendarsAccessor implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleCalendarsAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleCalendarsAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleCalendarsAccessorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleCaseFirstAccessor.class)
   public static final class JSLocaleCaseFirstAccessorNodeGen extends LocalePrototypeBuiltins.JSLocaleCaseFirstAccessor implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleCaseFirstAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleCaseFirstAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleCaseFirstAccessorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleCollationAccessor.class)
   public static final class JSLocaleCollationAccessorNodeGen extends LocalePrototypeBuiltins.JSLocaleCollationAccessor implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleCollationAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleCollationAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleCollationAccessorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleCollationsAccessor.class)
   public static final class JSLocaleCollationsAccessorNodeGen extends LocalePrototypeBuiltins.JSLocaleCollationsAccessor implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleCollationsAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleCollationsAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleCollationsAccessorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleHourCycleAccessor.class)
   public static final class JSLocaleHourCycleAccessorNodeGen extends LocalePrototypeBuiltins.JSLocaleHourCycleAccessor implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleHourCycleAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleHourCycleAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleHourCycleAccessorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleHourCyclesAccessor.class)
   public static final class JSLocaleHourCyclesAccessorNodeGen extends LocalePrototypeBuiltins.JSLocaleHourCyclesAccessor implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleHourCyclesAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleHourCyclesAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleHourCyclesAccessorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleLanguageAccessor.class)
   public static final class JSLocaleLanguageAccessorNodeGen extends LocalePrototypeBuiltins.JSLocaleLanguageAccessor implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleLanguageAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleLanguageAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleLanguageAccessorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleMaximizeNode.class)
   public static final class JSLocaleMaximizeNodeGen extends LocalePrototypeBuiltins.JSLocaleMaximizeNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleMaximizeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleMaximizeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleMaximizeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleMinimizeNode.class)
   public static final class JSLocaleMinimizeNodeGen extends LocalePrototypeBuiltins.JSLocaleMinimizeNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleMinimizeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleMinimizeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleMinimizeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleNumberingSystemAccessor.class)
   public static final class JSLocaleNumberingSystemAccessorNodeGen
      extends LocalePrototypeBuiltins.JSLocaleNumberingSystemAccessor
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleNumberingSystemAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleNumberingSystemAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleNumberingSystemAccessorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleNumberingSystemsAccessor.class)
   public static final class JSLocaleNumberingSystemsAccessorNodeGen
      extends LocalePrototypeBuiltins.JSLocaleNumberingSystemsAccessor
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleNumberingSystemsAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleNumberingSystemsAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleNumberingSystemsAccessorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleNumericAccessor.class)
   public static final class JSLocaleNumericAccessorNodeGen extends LocalePrototypeBuiltins.JSLocaleNumericAccessor implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleNumericAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleNumericAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleNumericAccessorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleRegionAccessor.class)
   public static final class JSLocaleRegionAccessorNodeGen extends LocalePrototypeBuiltins.JSLocaleRegionAccessor implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleRegionAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleRegionAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleRegionAccessorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleScriptAccessor.class)
   public static final class JSLocaleScriptAccessorNodeGen extends LocalePrototypeBuiltins.JSLocaleScriptAccessor implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleScriptAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleScriptAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleScriptAccessorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleTextInfoAccessor.class)
   public static final class JSLocaleTextInfoAccessorNodeGen extends LocalePrototypeBuiltins.JSLocaleTextInfoAccessor implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleTextInfoAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleTextInfoAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleTextInfoAccessorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleTimeZonesAccessor.class)
   public static final class JSLocaleTimeZonesAccessorNodeGen extends LocalePrototypeBuiltins.JSLocaleTimeZonesAccessor implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleTimeZonesAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleTimeZonesAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleTimeZonesAccessorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleToStringNode.class)
   public static final class JSLocaleToStringNodeGen extends LocalePrototypeBuiltins.JSLocaleToStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private TruffleString executeAndSpecialize(Object arguments0Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleToStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleToStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(LocalePrototypeBuiltins.JSLocaleWeekInfoAccessor.class)
   public static final class JSLocaleWeekInfoAccessorNodeGen extends LocalePrototypeBuiltins.JSLocaleWeekInfoAccessor implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSLocaleWeekInfoAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value__ = (JSLocaleObject)arguments0Value_;
            return this.doLocale(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSLocale(arguments0Value_)) {
            return this.doOther(arguments0Value_);
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
         if (arguments0Value instanceof JSLocaleObject) {
            JSLocaleObject arguments0Value_ = (JSLocaleObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doLocale(arguments0Value_);
         } else if (!JSGuards.isJSLocale(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doOther(arguments0Value);
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
         Object[] s = new Object[]{"doLocale", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOther", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static LocalePrototypeBuiltins.JSLocaleWeekInfoAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new LocalePrototypeBuiltinsFactory.JSLocaleWeekInfoAccessorNodeGen(context, builtin, arguments);
      }
   }
}
