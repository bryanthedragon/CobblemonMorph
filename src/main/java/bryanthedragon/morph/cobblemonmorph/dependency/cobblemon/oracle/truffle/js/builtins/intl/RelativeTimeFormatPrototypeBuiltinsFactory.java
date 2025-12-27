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
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSRelativeTimeFormatObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(RelativeTimeFormatPrototypeBuiltins.class)
public final class RelativeTimeFormatPrototypeBuiltinsFactory {
   @GeneratedBy(RelativeTimeFormatPrototypeBuiltins.JSRelativeTimeFormatFormatNode.class)
   public static final class JSRelativeTimeFormatFormatNodeGen
      extends RelativeTimeFormatPrototypeBuiltins.JSRelativeTimeFormatFormatNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToStringNode format_toStringNode_;
      @Node.Child
      private JSToNumberNode format_toNumberNode_;

      private JSRelativeTimeFormatFormatNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSRelativeTimeFormatObject) {
               JSRelativeTimeFormatObject arguments0Value__ = (JSRelativeTimeFormatObject)arguments0Value_;
               return this.doFormat(arguments0Value__, arguments1Value_, arguments2Value_, this.format_toStringNode_, this.format_toNumberNode_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSRelativeTimeFormat(arguments0Value_)) {
               return this.throwTypeError(arguments0Value_, arguments1Value_, arguments2Value_);
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

         TruffleString var8;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSRelativeTimeFormatObject)) {
               if (JSGuards.isJSRelativeTimeFormat(arguments0Value)) {
                  throw new UnsupportedSpecializationException(
                     this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
                  );
               }

               int var13;
               this.state_0_ = var13 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.throwTypeError(arguments0Value, arguments1Value, arguments2Value);
            }

            JSRelativeTimeFormatObject arguments0Value_ = (JSRelativeTimeFormatObject)arguments0Value;
            this.format_toStringNode_ = super.insert(JSToStringNode.create());
            this.format_toNumberNode_ = super.insert(JSToNumberNode.create());
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.doFormat(arguments0Value_, arguments1Value, arguments2Value, this.format_toStringNode_, this.format_toNumberNode_);
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
         Object[] s = new Object[]{"doFormat", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.format_toStringNode_, this.format_toNumberNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"throwTypeError", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static RelativeTimeFormatPrototypeBuiltins.JSRelativeTimeFormatFormatNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RelativeTimeFormatPrototypeBuiltinsFactory.JSRelativeTimeFormatFormatNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RelativeTimeFormatPrototypeBuiltins.JSRelativeTimeFormatFormatToPartsNode.class)
   public static final class JSRelativeTimeFormatFormatToPartsNodeGen
      extends RelativeTimeFormatPrototypeBuiltins.JSRelativeTimeFormatFormatToPartsNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToStringNode formatToParts_toStringNode_;
      @Node.Child
      private JSToNumberNode formatToParts_toNumberNode_;

      private JSRelativeTimeFormatFormatToPartsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSRelativeTimeFormatObject) {
               JSRelativeTimeFormatObject arguments0Value__ = (JSRelativeTimeFormatObject)arguments0Value_;
               return this.doFormatToParts(
                  arguments0Value__, arguments1Value_, arguments2Value_, this.formatToParts_toStringNode_, this.formatToParts_toNumberNode_
               );
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSRelativeTimeFormat(arguments0Value_)) {
               return this.throwTypeError(arguments0Value_, arguments1Value_, arguments2Value_);
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

         Object var8;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSRelativeTimeFormatObject)) {
               if (JSGuards.isJSRelativeTimeFormat(arguments0Value)) {
                  throw new UnsupportedSpecializationException(
                     this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
                  );
               }

               int var13;
               this.state_0_ = var13 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.throwTypeError(arguments0Value, arguments1Value, arguments2Value);
            }

            JSRelativeTimeFormatObject arguments0Value_ = (JSRelativeTimeFormatObject)arguments0Value;
            this.formatToParts_toStringNode_ = super.insert(JSToStringNode.create());
            this.formatToParts_toNumberNode_ = super.insert(JSToNumberNode.create());
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.doFormatToParts(arguments0Value_, arguments1Value, arguments2Value, this.formatToParts_toStringNode_, this.formatToParts_toNumberNode_);
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
         Object[] s = new Object[]{"doFormatToParts", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.formatToParts_toStringNode_, this.formatToParts_toNumberNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"throwTypeError", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static RelativeTimeFormatPrototypeBuiltins.JSRelativeTimeFormatFormatToPartsNode create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new RelativeTimeFormatPrototypeBuiltinsFactory.JSRelativeTimeFormatFormatToPartsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RelativeTimeFormatPrototypeBuiltins.JSRelativeTimeFormatResolvedOptionsNode.class)
   public static final class JSRelativeTimeFormatResolvedOptionsNodeGen
      extends RelativeTimeFormatPrototypeBuiltins.JSRelativeTimeFormatResolvedOptionsNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSRelativeTimeFormatResolvedOptionsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSRelativeTimeFormatObject) {
            JSRelativeTimeFormatObject arguments0Value__ = (JSRelativeTimeFormatObject)arguments0Value_;
            return this.doResolvedOptions(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSRelativeTimeFormat(arguments0Value_)) {
            return this.doResolvedOptions(arguments0Value_);
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
         if (arguments0Value instanceof JSRelativeTimeFormatObject) {
            JSRelativeTimeFormatObject arguments0Value_ = (JSRelativeTimeFormatObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doResolvedOptions(arguments0Value_);
         } else if (!JSGuards.isJSRelativeTimeFormat(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doResolvedOptions(arguments0Value);
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
         Object[] s = new Object[]{"doResolvedOptions", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doResolvedOptions", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static RelativeTimeFormatPrototypeBuiltins.JSRelativeTimeFormatResolvedOptionsNode create(
         JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments
      ) {
         return new RelativeTimeFormatPrototypeBuiltinsFactory.JSRelativeTimeFormatResolvedOptionsNodeGen(context, builtin, arguments);
      }
   }
}
