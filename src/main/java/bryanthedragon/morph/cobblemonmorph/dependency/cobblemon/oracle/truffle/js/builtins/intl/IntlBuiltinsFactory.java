package com.oracle.truffle.js.builtins.intl;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(IntlBuiltins.class)
public final class IntlBuiltinsFactory {
   @GeneratedBy(IntlBuiltins.GetCanonicalLocalesNode.class)
   public static final class GetCanonicalLocalesNodeGen extends IntlBuiltins.GetCanonicalLocalesNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private GetCanonicalLocalesNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.getCanonicalLocales(arguments0Value_);
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
         Object[] s = new Object[]{"getCanonicalLocales", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static IntlBuiltins.GetCanonicalLocalesNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new IntlBuiltinsFactory.GetCanonicalLocalesNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(IntlBuiltins.SupportedValuesOfNode.class)
   public static final class SupportedValuesOfNodeGen extends IntlBuiltins.SupportedValuesOfNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToStringNode toStringNode_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile errorBranch_;

      private SupportedValuesOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.supportedValuesOf(arguments0Value_, this.toStringNode_, this.errorBranch_);
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

         Object var5;
         try {
            int state_0 = this.state_0_;
            this.toStringNode_ = super.insert(JSToStringNode.create());
            this.errorBranch_ = BranchProfile.create();
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.supportedValuesOf(arguments0Value, this.toStringNode_, this.errorBranch_);
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
         Object[] s = new Object[]{"supportedValuesOf", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toStringNode_, this.errorBranch_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static IntlBuiltins.SupportedValuesOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new IntlBuiltinsFactory.SupportedValuesOfNodeGen(context, builtin, arguments);
      }
   }
}
