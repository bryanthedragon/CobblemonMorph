package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.intl.ToIntlMathematicalValue;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSNumberFormatObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(NumberFormatPrototypeBuiltins.class)
public final class NumberFormatPrototypeBuiltinsFactory {
   @GeneratedBy(NumberFormatPrototypeBuiltins.JSNumberFormatFormatRangeNode.class)
   public static final class JSNumberFormatFormatRangeNodeGen
      extends NumberFormatPrototypeBuiltins.JSNumberFormatFormatRangeNode
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
      private NumberFormatPrototypeBuiltinsFactory.JSNumberFormatFormatRangeNodeGen.FormatRangeData formatRange_cache;

      private JSNumberFormatFormatRangeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSNumberFormatObject) {
               JSNumberFormatObject arguments0Value__ = (JSNumberFormatObject)arguments0Value_;
               NumberFormatPrototypeBuiltinsFactory.JSNumberFormatFormatRangeNodeGen.FormatRangeData s0_ = this.formatRange_cache;
               if (s0_ != null) {
                  return this.doFormatRange(
                     arguments0Value__, arguments1Value_, arguments2Value_, s0_.startToIntlMVNode_, s0_.endToIntlMVNode_, s0_.errorBranch_
                  );
               }
            }

            if ((state_0 & 2) != 0 && fallbackGuard_(state_0, arguments0Value_, arguments1Value_, arguments2Value_)) {
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

         TruffleString var9;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSNumberFormatObject)) {
               int var14;
               this.state_0_ = var14 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.throwTypeError(arguments0Value, arguments1Value, arguments2Value);
            }

            JSNumberFormatObject arguments0Value_ = (JSNumberFormatObject)arguments0Value;
            NumberFormatPrototypeBuiltinsFactory.JSNumberFormatFormatRangeNodeGen.FormatRangeData s0_ = super.insert(
               new NumberFormatPrototypeBuiltinsFactory.JSNumberFormatFormatRangeNodeGen.FormatRangeData()
            );
            s0_.startToIntlMVNode_ = s0_.insertAccessor(ToIntlMathematicalValue.create(true));
            s0_.endToIntlMVNode_ = s0_.insertAccessor(ToIntlMathematicalValue.create(true));
            s0_.errorBranch_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.formatRange_cache = s0_;
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = this.doFormatRange(arguments0Value_, arguments1Value, arguments2Value, s0_.startToIntlMVNode_, s0_.endToIntlMVNode_, s0_.errorBranch_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var9;
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
         Object[] s = new Object[]{"doFormatRange", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            NumberFormatPrototypeBuiltinsFactory.JSNumberFormatFormatRangeNodeGen.FormatRangeData s0_ = this.formatRange_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.startToIntlMVNode_, s0_.endToIntlMVNode_, s0_.errorBranch_));
            }

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

      private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         return (state_0 & 1) != 0 || !(arguments0Value instanceof JSNumberFormatObject);
      }

      public static NumberFormatPrototypeBuiltins.JSNumberFormatFormatRangeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new NumberFormatPrototypeBuiltinsFactory.JSNumberFormatFormatRangeNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(NumberFormatPrototypeBuiltins.JSNumberFormatFormatRangeNode.class)
      private static final class FormatRangeData extends Node {
         @Node.Child
         ToIntlMathematicalValue startToIntlMVNode_;
         @Node.Child
         ToIntlMathematicalValue endToIntlMVNode_;
         @CompilerDirectives.CompilationFinal
         BranchProfile errorBranch_;

         FormatRangeData() {
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

   @GeneratedBy(NumberFormatPrototypeBuiltins.JSNumberFormatFormatRangeToPartsNode.class)
   public static final class JSNumberFormatFormatRangeToPartsNodeGen
      extends NumberFormatPrototypeBuiltins.JSNumberFormatFormatRangeToPartsNode
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
      private NumberFormatPrototypeBuiltinsFactory.JSNumberFormatFormatRangeToPartsNodeGen.FormatRangeToPartsData formatRangeToParts_cache;

      private JSNumberFormatFormatRangeToPartsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSNumberFormatObject) {
               JSNumberFormatObject arguments0Value__ = (JSNumberFormatObject)arguments0Value_;
               NumberFormatPrototypeBuiltinsFactory.JSNumberFormatFormatRangeToPartsNodeGen.FormatRangeToPartsData s0_ = this.formatRangeToParts_cache;
               if (s0_ != null) {
                  return this.doFormatRangeToParts(
                     arguments0Value__, arguments1Value_, arguments2Value_, s0_.startToIntlMVNode_, s0_.endToIntlMVNode_, s0_.errorBranch_
                  );
               }
            }

            if ((state_0 & 2) != 0 && fallbackGuard_(state_0, arguments0Value_, arguments1Value_, arguments2Value_)) {
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

         Object var9;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSNumberFormatObject)) {
               int var14;
               this.state_0_ = var14 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.throwTypeError(arguments0Value, arguments1Value, arguments2Value);
            }

            JSNumberFormatObject arguments0Value_ = (JSNumberFormatObject)arguments0Value;
            NumberFormatPrototypeBuiltinsFactory.JSNumberFormatFormatRangeToPartsNodeGen.FormatRangeToPartsData s0_ = super.insert(
               new NumberFormatPrototypeBuiltinsFactory.JSNumberFormatFormatRangeToPartsNodeGen.FormatRangeToPartsData()
            );
            s0_.startToIntlMVNode_ = s0_.insertAccessor(ToIntlMathematicalValue.create(true));
            s0_.endToIntlMVNode_ = s0_.insertAccessor(ToIntlMathematicalValue.create(true));
            s0_.errorBranch_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.formatRangeToParts_cache = s0_;
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = this.doFormatRangeToParts(arguments0Value_, arguments1Value, arguments2Value, s0_.startToIntlMVNode_, s0_.endToIntlMVNode_, s0_.errorBranch_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var9;
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
         Object[] s = new Object[]{"doFormatRangeToParts", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            NumberFormatPrototypeBuiltinsFactory.JSNumberFormatFormatRangeToPartsNodeGen.FormatRangeToPartsData s0_ = this.formatRangeToParts_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.startToIntlMVNode_, s0_.endToIntlMVNode_, s0_.errorBranch_));
            }

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

      private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         return (state_0 & 1) != 0 || !(arguments0Value instanceof JSNumberFormatObject);
      }

      public static NumberFormatPrototypeBuiltins.JSNumberFormatFormatRangeToPartsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new NumberFormatPrototypeBuiltinsFactory.JSNumberFormatFormatRangeToPartsNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(NumberFormatPrototypeBuiltins.JSNumberFormatFormatRangeToPartsNode.class)
      private static final class FormatRangeToPartsData extends Node {
         @Node.Child
         ToIntlMathematicalValue startToIntlMVNode_;
         @Node.Child
         ToIntlMathematicalValue endToIntlMVNode_;
         @CompilerDirectives.CompilationFinal
         BranchProfile errorBranch_;

         FormatRangeToPartsData() {
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

   @GeneratedBy(NumberFormatPrototypeBuiltins.JSNumberFormatFormatToPartsNode.class)
   public static final class JSNumberFormatFormatToPartsNodeGen
      extends NumberFormatPrototypeBuiltins.JSNumberFormatFormatToPartsNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSNumberFormatFormatToPartsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSNumberFormatObject) {
               JSNumberFormatObject arguments0Value__ = (JSNumberFormatObject)arguments0Value_;
               return this.doFormatToParts(arguments0Value__, arguments1Value_);
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
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSNumberFormatObject) {
            JSNumberFormatObject arguments0Value_ = (JSNumberFormatObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.doFormatToParts(arguments0Value_, arguments1Value);
         } else {
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return this.throwTypeError(arguments0Value, arguments1Value);
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
         Object[] s = new Object[]{"doFormatToParts", null, null};
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

      private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
         return (state_0 & 1) != 0 || !(arguments0Value instanceof JSNumberFormatObject);
      }

      public static NumberFormatPrototypeBuiltins.JSNumberFormatFormatToPartsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new NumberFormatPrototypeBuiltinsFactory.JSNumberFormatFormatToPartsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(NumberFormatPrototypeBuiltins.JSNumberFormatResolvedOptionsNode.class)
   public static final class JSNumberFormatResolvedOptionsNodeGen
      extends NumberFormatPrototypeBuiltins.JSNumberFormatResolvedOptionsNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSNumberFormatResolvedOptionsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSNumberFormatObject) {
            JSNumberFormatObject arguments0Value__ = (JSNumberFormatObject)arguments0Value_;
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
         if (arguments0Value instanceof JSNumberFormatObject) {
            JSNumberFormatObject arguments0Value_ = (JSNumberFormatObject)arguments0Value;
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
         return (state_0 & 1) != 0 || !(arguments0Value instanceof JSNumberFormatObject);
      }

      public static NumberFormatPrototypeBuiltins.JSNumberFormatResolvedOptionsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new NumberFormatPrototypeBuiltinsFactory.JSNumberFormatResolvedOptionsNodeGen(context, builtin, arguments);
      }
   }
}
