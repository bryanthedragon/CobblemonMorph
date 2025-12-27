package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Set;

public abstract class PrivateFieldInNode extends JSBinaryNode {
   protected PrivateFieldInNode(JavaScriptNode left, JavaScriptNode right) {
      super(left, right);
   }

   public static PrivateFieldInNode create(JavaScriptNode left, JavaScriptNode right) {
      return PrivateFieldInNodeGen.create(left, right);
   }

   @Specialization(guards = "isJSObject(haystack)", limit = "3")
   protected boolean doInstance(HiddenKey needle, JSDynamicObject haystack, @CachedLibrary("haystack") DynamicObjectLibrary access) {
      return access.containsKey(haystack, needle);
   }

   @Specialization(guards = "isJSObject(haystack)")
   protected boolean doStatic(JSDynamicObject needle, JSDynamicObject haystack) {
      return needle == haystack;
   }

   @Fallback
   protected boolean doFallback(Object needle, Object haystack, @Cached IsObjectNode isObjectNode) {
      if (isObjectNode.executeBoolean(haystack)) {
         return false;
      } else {
         throw Errors.createTypeErrorNotAnObject(haystack);
      }
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(cloneUninitialized(this.leftNode, materializedTags), cloneUninitialized(this.rightNode, materializedTags));
   }
}
