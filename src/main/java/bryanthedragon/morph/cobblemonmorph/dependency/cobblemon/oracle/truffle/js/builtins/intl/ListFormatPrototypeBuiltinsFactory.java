package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSStringListFromIterableNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSListFormatObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ListFormatPrototypeBuiltins.class)
public final class ListFormatPrototypeBuiltinsFactory {
   @GeneratedBy(ListFormatPrototypeBuiltins.JSListFormatFormatNode.class)
   public static final class JSListFormatFormatNodeGen extends ListFormatPrototypeBuiltins.JSListFormatFormatNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSStringListFromIterableNode format_strListFromIterableNode_;

      private JSListFormatFormatNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSListFormatObject) {
               JSListFormatObject arguments0Value__ = (JSListFormatObject)arguments0Value_;
               return this.doFormat(arguments0Value__, arguments1Value_, this.format_strListFromIterableNode_);
            }

            if ((state_0 & 2) != 0 && fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
               return this.throwTypeError(arguments0Value_, arguments1Value_);
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

         TruffleString var7;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSListFormatObject)) {
               int var12;
               this.state_0_ = var12 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.throwTypeError(arguments0Value, arguments1Value);
            }

            JSListFormatObject arguments0Value_ = (JSListFormatObject)arguments0Value;
            this.format_strListFromIterableNode_ = super.insert(JSStringListFromIterableNode.create(this.getContext()));
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.doFormat(arguments0Value_, arguments1Value, this.format_strListFromIterableNode_);
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
         Object[] s = new Object[]{"doFormat", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.format_strListFromIterableNode_));
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

      private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
         return (state_0 & 1) != 0 || !(arguments0Value instanceof JSListFormatObject);
      }

      public static ListFormatPrototypeBuiltins.JSListFormatFormatNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ListFormatPrototypeBuiltinsFactory.JSListFormatFormatNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ListFormatPrototypeBuiltins.JSListFormatFormatToPartsNode.class)
   public static final class JSListFormatFormatToPartsNodeGen
      extends ListFormatPrototypeBuiltins.JSListFormatFormatToPartsNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSStringListFromIterableNode formatToParts_strListFromIterableNode_;

      private JSListFormatFormatToPartsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSListFormatObject) {
               JSListFormatObject arguments0Value__ = (JSListFormatObject)arguments0Value_;
               return this.doFormatToParts(arguments0Value__, arguments1Value_, this.formatToParts_strListFromIterableNode_);
            }

            if ((state_0 & 2) != 0 && fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
               return this.throwTypeError(arguments0Value_, arguments1Value_);
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

         Object var7;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSListFormatObject)) {
               int var12;
               this.state_0_ = var12 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.throwTypeError(arguments0Value, arguments1Value);
            }

            JSListFormatObject arguments0Value_ = (JSListFormatObject)arguments0Value;
            this.formatToParts_strListFromIterableNode_ = super.insert(JSStringListFromIterableNode.create(this.getContext()));
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.doFormatToParts(arguments0Value_, arguments1Value, this.formatToParts_strListFromIterableNode_);
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
         Object[] s = new Object[]{"doFormatToParts", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.formatToParts_strListFromIterableNode_));
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

      private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
         return (state_0 & 1) != 0 || !(arguments0Value instanceof JSListFormatObject);
      }

      public static ListFormatPrototypeBuiltins.JSListFormatFormatToPartsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ListFormatPrototypeBuiltinsFactory.JSListFormatFormatToPartsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ListFormatPrototypeBuiltins.JSListFormatResolvedOptionsNode.class)
   public static final class JSListFormatResolvedOptionsNodeGen
      extends ListFormatPrototypeBuiltins.JSListFormatResolvedOptionsNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSListFormatResolvedOptionsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSListFormatObject) {
            JSListFormatObject arguments0Value__ = (JSListFormatObject)arguments0Value_;
            return this.doResolvedOptions(arguments0Value__);
         } else if ((state_0 & 2) != 0 && fallbackGuard_(state_0, arguments0Value_)) {
            return this.throwTypeError(arguments0Value_);
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
         if (arguments0Value instanceof JSListFormatObject) {
            JSListFormatObject arguments0Value_ = (JSListFormatObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doResolvedOptions(arguments0Value_);
         } else {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.throwTypeError(arguments0Value);
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
         s = new Object[]{"throwTypeError", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
         return (state_0 & 1) != 0 || !(arguments0Value instanceof JSListFormatObject);
      }

      public static ListFormatPrototypeBuiltins.JSListFormatResolvedOptionsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ListFormatPrototypeBuiltinsFactory.JSListFormatResolvedOptionsNodeGen(context, builtin, arguments);
      }
   }
}
