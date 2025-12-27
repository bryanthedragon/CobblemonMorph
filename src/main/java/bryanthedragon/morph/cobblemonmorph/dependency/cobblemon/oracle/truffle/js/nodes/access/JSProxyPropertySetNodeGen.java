package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSProxyPropertySetNode.class)
public final class JSProxyPropertySetNodeGen extends JSProxyPropertySetNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile hasTrap_;
   @CompilerDirectives.CompilationFinal
   private JSClassProfile targetClassProfile_;

   private JSProxyPropertySetNodeGen(JSContext context, boolean isStrict) {
      super(context, isStrict);
   }

   @Override
   public boolean executeWithReceiverAndValue(Object arg0Value, Object arg1Value, Object arg2Value, Object arg3Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0 && arg0Value instanceof JSDynamicObject) {
         JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
         return this.doGeneric(arg0Value_, arg1Value, arg2Value, arg3Value, this.hasTrap_, this.targetClassProfile_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }
   }

   @Override
   public boolean executeWithReceiverAndValueInt(Object arg0Value, Object arg1Value, int arg2Value, Object arg3Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0 && arg0Value instanceof JSDynamicObject) {
         JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
         return this.doGeneric(arg0Value_, arg1Value, arg2Value, arg3Value, this.hasTrap_, this.targetClassProfile_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }
   }

   private boolean executeAndSpecialize(Object arg0Value, Object arg1Value, Object arg2Value, Object arg3Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      boolean var9;
      try {
         int state_0 = this.state_0_;
         if (!(arg0Value instanceof JSDynamicObject)) {
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value);
         }

         JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
         this.hasTrap_ = ConditionProfile.createBinaryProfile();
         this.targetClassProfile_ = JSClassProfile.create();
         int var13;
         this.state_0_ = var13 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var9 = this.doGeneric(arg0Value_, arg1Value, arg2Value, arg3Value, this.hasTrap_, this.targetClassProfile_);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var9;
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[]{0, null};
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doGeneric", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.hasTrap_, this.targetClassProfile_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static JSProxyPropertySetNode create(JSContext context, boolean isStrict) {
      return new JSProxyPropertySetNodeGen(context, isStrict);
   }
}
