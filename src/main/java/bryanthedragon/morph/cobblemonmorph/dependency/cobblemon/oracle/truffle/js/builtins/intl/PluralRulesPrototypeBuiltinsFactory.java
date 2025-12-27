package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.intl.JSPluralRulesObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(PluralRulesPrototypeBuiltins.class)
public final class PluralRulesPrototypeBuiltinsFactory {
   @GeneratedBy(PluralRulesPrototypeBuiltins.JSPluralRulesResolvedOptionsNode.class)
   public static final class JSPluralRulesResolvedOptionsNodeGen
      extends PluralRulesPrototypeBuiltins.JSPluralRulesResolvedOptionsNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSPluralRulesResolvedOptionsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSPluralRulesObject) {
            JSPluralRulesObject arguments0Value__ = (JSPluralRulesObject)arguments0Value_;
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
         if (arguments0Value instanceof JSPluralRulesObject) {
            JSPluralRulesObject arguments0Value_ = (JSPluralRulesObject)arguments0Value;
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
         return (state_0 & 1) != 0 || !(arguments0Value instanceof JSPluralRulesObject);
      }

      public static PluralRulesPrototypeBuiltins.JSPluralRulesResolvedOptionsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PluralRulesPrototypeBuiltinsFactory.JSPluralRulesResolvedOptionsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PluralRulesPrototypeBuiltins.JSPluralRulesSelectNode.class)
   public static final class JSPluralRulesSelectNodeGen extends PluralRulesPrototypeBuiltins.JSPluralRulesSelectNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSPluralRulesSelectNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSPluralRulesObject) {
               JSPluralRulesObject arguments0Value__ = (JSPluralRulesObject)arguments0Value_;
               return this.doSelect(arguments0Value__, arguments1Value_);
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
         if (arguments0Value instanceof JSPluralRulesObject) {
            JSPluralRulesObject arguments0Value_ = (JSPluralRulesObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.doSelect(arguments0Value_, arguments1Value);
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
         Object[] s = new Object[]{"doSelect", null, null};
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
         return (state_0 & 1) != 0 || !(arguments0Value instanceof JSPluralRulesObject);
      }

      public static PluralRulesPrototypeBuiltins.JSPluralRulesSelectNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PluralRulesPrototypeBuiltinsFactory.JSPluralRulesSelectNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(PluralRulesPrototypeBuiltins.JSPluralRulesSelectRangeNode.class)
   public static final class JSPluralRulesSelectRangeNodeGen
      extends PluralRulesPrototypeBuiltins.JSPluralRulesSelectRangeNode
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
      private PluralRulesPrototypeBuiltinsFactory.JSPluralRulesSelectRangeNodeGen.SelectRangeData selectRange_cache;

      private JSPluralRulesSelectRangeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSPluralRulesObject) {
               JSPluralRulesObject arguments0Value__ = (JSPluralRulesObject)arguments0Value_;
               PluralRulesPrototypeBuiltinsFactory.JSPluralRulesSelectRangeNodeGen.SelectRangeData s0_ = this.selectRange_cache;
               if (s0_ != null) {
                  return this.doSelectRange(arguments0Value__, arguments1Value_, arguments2Value_, s0_.startToNumber_, s0_.endToNumber_, s0_.errorBranch_);
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
            if (!(arguments0Value instanceof JSPluralRulesObject)) {
               int var14;
               this.state_0_ = var14 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.throwTypeError(arguments0Value, arguments1Value, arguments2Value);
            }

            JSPluralRulesObject arguments0Value_ = (JSPluralRulesObject)arguments0Value;
            PluralRulesPrototypeBuiltinsFactory.JSPluralRulesSelectRangeNodeGen.SelectRangeData s0_ = super.insert(
               new PluralRulesPrototypeBuiltinsFactory.JSPluralRulesSelectRangeNodeGen.SelectRangeData()
            );
            s0_.startToNumber_ = s0_.insertAccessor(JSToNumberNode.create());
            s0_.endToNumber_ = s0_.insertAccessor(JSToNumberNode.create());
            s0_.errorBranch_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.selectRange_cache = s0_;
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = this.doSelectRange(arguments0Value_, arguments1Value, arguments2Value, s0_.startToNumber_, s0_.endToNumber_, s0_.errorBranch_);
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
         Object[] s = new Object[]{"doSelectRange", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            PluralRulesPrototypeBuiltinsFactory.JSPluralRulesSelectRangeNodeGen.SelectRangeData s0_ = this.selectRange_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.startToNumber_, s0_.endToNumber_, s0_.errorBranch_));
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
         return (state_0 & 1) != 0 || !(arguments0Value instanceof JSPluralRulesObject);
      }

      public static PluralRulesPrototypeBuiltins.JSPluralRulesSelectRangeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new PluralRulesPrototypeBuiltinsFactory.JSPluralRulesSelectRangeNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(PluralRulesPrototypeBuiltins.JSPluralRulesSelectRangeNode.class)
      private static final class SelectRangeData extends Node {
         @Node.Child
         JSToNumberNode startToNumber_;
         @Node.Child
         JSToNumberNode endToNumber_;
         @CompilerDirectives.CompilationFinal
         BranchProfile errorBranch_;

         SelectRangeData() {
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
}
