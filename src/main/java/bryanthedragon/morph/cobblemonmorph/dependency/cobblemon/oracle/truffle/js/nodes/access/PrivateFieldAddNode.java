package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSObject;

public abstract class PrivateFieldAddNode extends JavaScriptBaseNode {
   protected final JSContext context;

   public static PrivateFieldAddNode create(JSContext context) {
      return PrivateFieldAddNodeGen.create(context);
   }

   protected PrivateFieldAddNode(JSContext context) {
      this.context = context;
   }

   public abstract void execute(Object target, Object key, Object value);

   @Specialization(limit = "3")
   void doFieldAdd(JSObject target, HiddenKey key, Object value, @CachedLibrary("target") DynamicObjectLibrary access) {
      if (!Properties.containsKey(access, target, key)) {
         Properties.putWithFlags(access, target, key, value, JSAttributes.getDefaultNotEnumerable());
      } else {
         this.duplicate(key);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object duplicate(HiddenKey key) {
      throw Errors.createTypeErrorCannotAddPrivateMember(key.getName(), this);
   }

   @CompilerDirectives.TruffleBoundary
   @Fallback
   void doFallback(Object target, Object key, Object value) {
      throw Errors.createTypeErrorCannotSetProperty(key.toString(), target, this);
   }
}
