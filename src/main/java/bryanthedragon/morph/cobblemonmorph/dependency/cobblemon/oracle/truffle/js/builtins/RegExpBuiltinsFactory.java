package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.util.TRegexUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(RegExpBuiltins.class)
public final class RegExpBuiltinsFactory {
   @GeneratedBy(RegExpBuiltins.GetStaticRegExpResultNode.class)
   static final class GetStaticRegExpResultNodeGen extends RegExpBuiltins.GetStaticRegExpResultNode implements Introspection.Provider {
      private GetStaticRegExpResultNodeGen(JSContext context) {
         super(context);
      }

      @Override
      Object execute() {
         return this.get();
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"get", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static RegExpBuiltins.GetStaticRegExpResultNode create(JSContext context) {
         return new RegExpBuiltinsFactory.GetStaticRegExpResultNodeGen(context);
      }
   }

   @GeneratedBy(RegExpBuiltins.JSRegExpStaticResultGetGroupNode.class)
   static final class JSRegExpStaticResultGetGroupNodeGen extends RegExpBuiltins.JSRegExpStaticResultGetGroupNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.SubstringByteIndexNode substringNode_;

      private JSRegExpStaticResultGetGroupNodeGen(JSContext context, JSBuiltin builtin, int groupNumber, JavaScriptNode[] arguments) {
         super(context, builtin, groupNumber);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return this.getGroup(frameValue, this.substringNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private TruffleString executeAndSpecialize(VirtualFrame frameValue) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var5;
         try {
            int state_0 = this.state_0_;
            this.substringNode_ = super.insert(TruffleString.SubstringByteIndexNode.create());
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.getGroup(frameValue, this.substringNode_);
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
         Object[] s = new Object[]{"getGroup", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.substringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static RegExpBuiltins.JSRegExpStaticResultGetGroupNode create(JSContext context, JSBuiltin builtin, int groupNumber, JavaScriptNode[] arguments) {
         return new RegExpBuiltinsFactory.JSRegExpStaticResultGetGroupNodeGen(context, builtin, groupNumber, arguments);
      }
   }

   @GeneratedBy(RegExpBuiltins.JSRegExpStaticResultGetInputNode.class)
   static final class JSRegExpStaticResultGetInputNodeGen extends RegExpBuiltins.JSRegExpStaticResultGetInputNode implements Introspection.Provider {
      private JSRegExpStaticResultGetInputNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         return this.getInputProp(frameValue);
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
         Object[] s = new Object[]{"getInputProp", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static RegExpBuiltins.JSRegExpStaticResultGetInputNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RegExpBuiltinsFactory.JSRegExpStaticResultGetInputNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RegExpBuiltins.JSRegExpStaticResultLastParenNode.class)
   static final class JSRegExpStaticResultLastParenNodeGen extends RegExpBuiltins.JSRegExpStaticResultLastParenNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.SubstringByteIndexNode substringNode_;

      private JSRegExpStaticResultLastParenNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return this.lastParen(frameValue, this.substringNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private TruffleString executeAndSpecialize(VirtualFrame frameValue) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var5;
         try {
            int state_0 = this.state_0_;
            this.substringNode_ = super.insert(TruffleString.SubstringByteIndexNode.create());
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.lastParen(frameValue, this.substringNode_);
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
         Object[] s = new Object[]{"lastParen", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.substringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static RegExpBuiltins.JSRegExpStaticResultLastParenNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RegExpBuiltinsFactory.JSRegExpStaticResultLastParenNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RegExpBuiltins.JSRegExpStaticResultLeftContextNode.class)
   static final class JSRegExpStaticResultLeftContextNodeGen extends RegExpBuiltins.JSRegExpStaticResultLeftContextNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.SubstringByteIndexNode substringNode_;

      private JSRegExpStaticResultLeftContextNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return this.leftContext(frameValue, this.substringNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private TruffleString executeAndSpecialize(VirtualFrame frameValue) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var5;
         try {
            int state_0 = this.state_0_;
            this.substringNode_ = super.insert(TruffleString.SubstringByteIndexNode.create());
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.leftContext(frameValue, this.substringNode_);
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
         Object[] s = new Object[]{"leftContext", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.substringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static RegExpBuiltins.JSRegExpStaticResultLeftContextNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RegExpBuiltinsFactory.JSRegExpStaticResultLeftContextNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RegExpBuiltins.JSRegExpStaticResultMultilineNode.class)
   static final class JSRegExpStaticResultMultilineNodeGen extends RegExpBuiltins.JSRegExpStaticResultMultilineNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private Assumption getMultilineLazy_assumption0_;
      @Node.Child
      private RegExpBuiltins.GetStaticRegExpResultNode getMultilineEager_getResultNode_;
      @Node.Child
      private TRegexUtil.TRegexResultAccessor getMultilineEager_resultAccessor_;

      private JSRegExpStaticResultMultilineNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0) {
            assert this.getContext().isOptionNashornCompatibilityMode();

            return this.getMultilineLazyNashorn();
         } else if ((state_0 & 2) != 0) {
            if (!Assumption.isValidAssumption(this.getMultilineLazy_assumption0_)) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.removeGetMultilineLazy_();
               return this.executeAndSpecialize();
            } else {
               assert !this.getContext().isOptionNashornCompatibilityMode();

               return this.getMultilineLazy();
            }
         } else if ((state_0 & 4) != 0) {
            return this.getMultilineEager(this.getMultilineEager_getResultNode_, this.getMultilineEager_resultAccessor_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize();
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0) {
            assert this.getContext().isOptionNashornCompatibilityMode();

            return this.getMultilineLazyNashorn();
         } else if ((state_0 & 2) != 0) {
            if (!Assumption.isValidAssumption(this.getMultilineLazy_assumption0_)) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               this.removeGetMultilineLazy_();
               return this.executeAndSpecialize();
            } else {
               assert !this.getContext().isOptionNashornCompatibilityMode();

               return this.getMultilineLazy();
            }
         } else if ((state_0 & 4) != 0) {
            return this.getMultilineEager(this.getMultilineEager_getResultNode_, this.getMultilineEager_resultAccessor_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize();
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize() {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (this.getContext().isOptionNashornCompatibilityMode()) {
               int var11;
               this.state_0_ = var11 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.getMultilineLazyNashorn();
            } else {
               if (!this.getContext().isOptionNashornCompatibilityMode()) {
                  Assumption getMultilineLazy_assumption0 = this.getStaticResultUnusedAssumption();
                  if (Assumption.isValidAssumption(getMultilineLazy_assumption0)) {
                     this.getMultilineLazy_assumption0_ = getMultilineLazy_assumption0;
                     int var10;
                     this.state_0_ = var10 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.getMultilineLazy();
                  }
               }

               this.getMultilineEager_getResultNode_ = super.insert(this.createGetResultNode());
               this.getMultilineEager_resultAccessor_ = super.insert(TRegexUtil.TRegexResultAccessor.create());
               int var9;
               this.state_0_ = var9 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.getMultilineEager(this.getMultilineEager_getResultNode_, this.getMultilineEager_resultAccessor_);
            }
         } finally {
            if (hasLock) {
               lock.unlock();
            }
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

      void removeGetMultilineLazy_() {
         Lock lock = this.getLock();
         lock.lock();

         try {
            this.state_0_ &= -3;
         } finally {
            lock.unlock();
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"getMultilineLazyNashorn", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"getMultilineLazy", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"getMultilineEager", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.getMultilineEager_getResultNode_, this.getMultilineEager_resultAccessor_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static RegExpBuiltins.JSRegExpStaticResultMultilineNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RegExpBuiltinsFactory.JSRegExpStaticResultMultilineNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RegExpBuiltins.JSRegExpStaticResultRightContextNode.class)
   static final class JSRegExpStaticResultRightContextNodeGen extends RegExpBuiltins.JSRegExpStaticResultRightContextNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.SubstringByteIndexNode substringNode_;

      private JSRegExpStaticResultRightContextNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            return this.rightContext(frameValue, this.substringNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private TruffleString executeAndSpecialize(VirtualFrame frameValue) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var5;
         try {
            int state_0 = this.state_0_;
            this.substringNode_ = super.insert(TruffleString.SubstringByteIndexNode.create());
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.rightContext(frameValue, this.substringNode_);
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
         Object[] s = new Object[]{"rightContext", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.substringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static RegExpBuiltins.JSRegExpStaticResultRightContextNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RegExpBuiltinsFactory.JSRegExpStaticResultRightContextNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RegExpBuiltins.JSRegExpStaticResultSetInputNode.class)
   static final class JSRegExpStaticResultSetInputNodeGen extends RegExpBuiltins.JSRegExpStaticResultSetInputNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private JSRegExpStaticResultSetInputNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.setInputProp(frameValue, arguments0Value_);
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
         Object[] s = new Object[]{"setInputProp", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static RegExpBuiltins.JSRegExpStaticResultSetInputNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RegExpBuiltinsFactory.JSRegExpStaticResultSetInputNodeGen(context, builtin, arguments);
      }
   }
}
