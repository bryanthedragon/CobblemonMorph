package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.CompileRegexNode;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSRegExpObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSToRegExpNode.class)
public final class JSToRegExpNodeGen extends JSToRegExpNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSToStringNode createRegExp_toStringNode_;
   @Node.Child
   private CompileRegexNode createRegExp_compileRegexNode_;

   private JSToRegExpNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public JSRegExpObject execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof JSRegExpObject) {
         JSRegExpObject arg0Value_ = (JSRegExpObject)arg0Value;
         return this.returnRegExp(arg0Value_);
      } else if ((state_0 & 2) != 0 && !JSGuards.isJSRegExp(arg0Value)) {
         return this.createRegExp(arg0Value, this.createRegExp_toStringNode_, this.createRegExp_compileRegexNode_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   private JSRegExpObject executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      JSRegExpObject var6;
      try {
         int state_0 = this.state_0_;
         if (!(arg0Value instanceof JSRegExpObject)) {
            if (JSGuards.isJSRegExp(arg0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }

            this.createRegExp_toStringNode_ = super.insert(JSToStringNode.createUndefinedToEmpty());
            this.createRegExp_compileRegexNode_ = super.insert(CompileRegexNode.create(this.context));
            int var11;
            this.state_0_ = var11 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.createRegExp(arg0Value, this.createRegExp_toStringNode_, this.createRegExp_compileRegexNode_);
         }

         JSRegExpObject arg0Value_ = (JSRegExpObject)arg0Value;
         int var10;
         this.state_0_ = var10 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var6 = this.returnRegExp(arg0Value_);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var6;
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
      Object[] s = new Object[]{"returnRegExp", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"createRegExp", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.createRegExp_toStringNode_, this.createRegExp_compileRegexNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static JSToRegExpNode create(JSContext context) {
      return new JSToRegExpNodeGen(context);
   }
}
