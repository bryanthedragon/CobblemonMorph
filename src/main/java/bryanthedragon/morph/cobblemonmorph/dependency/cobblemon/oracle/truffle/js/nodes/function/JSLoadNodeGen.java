package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSLoadNode.class)
public final class JSLoadNodeGen extends JSLoadNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private ImportValueNode importValue;
   @CompilerDirectives.CompilationFinal
   private Source cachedLoad_cachedSource_;
   @Node.Child
   private DirectCallNode cachedLoad_callNode_;
   @Node.Child
   private IndirectCallNode uncachedLoad_callNode_;

   private JSLoadNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public Object executeLoad(Source arg0Value, JSRealm arg1Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            assert this.cachedLoad_cachedSource_.isCached();

            if (JSLoadNode.equals(arg0Value, this.cachedLoad_cachedSource_)) {
               return JSLoadNode.cachedLoad(arg0Value, arg1Value, this.importValue, this.cachedLoad_cachedSource_, this.cachedLoad_callNode_);
            }
         }

         if ((state_0 & 2) != 0) {
            return JSLoadNode.uncachedLoad(arg0Value, arg1Value, this.importValue, this.uncachedLoad_callNode_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value);
   }

   private Object executeAndSpecialize(Source arg0Value, JSRealm arg1Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (exclude == 0) {
            boolean CachedLoad_duplicateFound_ = false;
            if ((state_0 & 1) != 0) {
               assert this.cachedLoad_cachedSource_.isCached();

               if (JSLoadNode.equals(arg0Value, this.cachedLoad_cachedSource_)) {
                  CachedLoad_duplicateFound_ = true;
               }
            }

            if (!CachedLoad_duplicateFound_ && arg0Value.isCached() && JSLoadNode.equals(arg0Value, arg0Value) && (state_0 & 1) == 0) {
               this.importValue = super.insert(this.importValue == null ? ImportValueNode.create() : this.importValue);
               this.cachedLoad_cachedSource_ = arg0Value;
               this.cachedLoad_callNode_ = super.insert(DirectCallNode.create(JSLoadNode.loadScript(arg0Value, arg1Value)));
               this.state_0_ = state_0 |= 1;
               CachedLoad_duplicateFound_ = true;
            }

            if (CachedLoad_duplicateFound_) {
               lock.unlock();
               hasLock = false;
               return JSLoadNode.cachedLoad(arg0Value, arg1Value, this.importValue, this.cachedLoad_cachedSource_, this.cachedLoad_callNode_);
            }
         }

         this.importValue = super.insert(this.importValue == null ? ImportValueNode.create() : this.importValue);
         this.uncachedLoad_callNode_ = super.insert(IndirectCallNode.create());
         int var14;
         this.exclude_ = var14 = exclude | 1;
         state_0 &= -2;
         int var13;
         this.state_0_ = var13 = state_0 | 2;
         lock.unlock();
         hasLock = false;
         return JSLoadNode.uncachedLoad(arg0Value, arg1Value, this.importValue, this.uncachedLoad_callNode_);
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

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null, null};
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"cachedLoad", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.importValue, this.cachedLoad_cachedSource_, this.cachedLoad_callNode_));
         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"uncachedLoad", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.importValue, this.uncachedLoad_callNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static JSLoadNode create(JSContext context) {
      return new JSLoadNodeGen(context);
   }
}
