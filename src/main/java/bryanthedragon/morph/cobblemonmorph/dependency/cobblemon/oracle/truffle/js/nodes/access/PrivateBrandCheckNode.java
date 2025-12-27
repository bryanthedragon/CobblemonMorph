package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Set;

public abstract class PrivateBrandCheckNode extends JSTargetableNode {
   @Node.Child
   @Executed
   protected JavaScriptNode targetNode;
   @Node.Child
   @Executed
   protected JavaScriptNode brandNode;

   public static PrivateBrandCheckNode create(JavaScriptNode targetNode, JavaScriptNode brandNode) {
      return PrivateBrandCheckNodeGen.create(targetNode, brandNode);
   }

   protected PrivateBrandCheckNode(JavaScriptNode targetNode, JavaScriptNode brandNode) {
      this.targetNode = targetNode;
      this.brandNode = brandNode;
   }

   @Specialization(limit = "3")
   Object doInstance(JSObject target, HiddenKey brandKey, @CachedLibrary("target") DynamicObjectLibrary access) {
      return Properties.containsKey(access, target, brandKey) ? target : this.denied(target, brandKey);
   }

   @Specialization
   Object doStatic(JSObject target, JSDynamicObject brand) {
      return target == brand ? target : this.denied(target, brand);
   }

   @Fallback
   Object denied(Object target, Object brand) {
      return Undefined.instance;
   }

   @Override
   public final JavaScriptNode getTarget() {
      return this.targetNode;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.targetNode, materializedTags), cloneUninitialized(this.brandNode, materializedTags));
   }
}
