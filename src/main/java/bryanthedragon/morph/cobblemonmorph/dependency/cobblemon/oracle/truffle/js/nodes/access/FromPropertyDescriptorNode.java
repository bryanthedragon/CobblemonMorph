package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;

@GenerateUncached
public abstract class FromPropertyDescriptorNode extends JavaScriptBaseNode {
   protected static final int SHAPE_LIMIT = 6;

   protected FromPropertyDescriptorNode() {
   }

   public static FromPropertyDescriptorNode create() {
      return FromPropertyDescriptorNodeGen.create();
   }

   public static FromPropertyDescriptorNode getUncached() {
      return FromPropertyDescriptorNodeGen.getUncached();
   }

   public abstract JSDynamicObject execute(PropertyDescriptor desc, JSContext context);

   @Specialization
   final JSDynamicObject toJSObject(
      PropertyDescriptor desc,
      JSContext context,
      @CachedLibrary(limit = "SHAPE_LIMIT") DynamicObjectLibrary putValueNode,
      @CachedLibrary(limit = "SHAPE_LIMIT") DynamicObjectLibrary putWritableNode,
      @CachedLibrary(limit = "SHAPE_LIMIT") DynamicObjectLibrary putGetNode,
      @CachedLibrary(limit = "SHAPE_LIMIT") DynamicObjectLibrary putSetNode,
      @CachedLibrary(limit = "SHAPE_LIMIT") DynamicObjectLibrary putEnumerableNode,
      @CachedLibrary(limit = "SHAPE_LIMIT") DynamicObjectLibrary putConfigurableNode
   ) {
      if (desc == null) {
         return Undefined.instance;
      } else {
         JSObject obj = JSOrdinary.create(context, this.getRealm());
         if (desc.hasValue()) {
            Properties.put(putValueNode, obj, JSAttributes.VALUE, desc.getValue());
         }

         if (desc.hasWritable()) {
            Properties.put(putWritableNode, obj, JSAttributes.WRITABLE, desc.getWritable());
         }

         if (desc.hasGet()) {
            Properties.put(putGetNode, obj, JSAttributes.GET, desc.getGet());
         }

         if (desc.hasSet()) {
            Properties.put(putSetNode, obj, JSAttributes.SET, desc.getSet());
         }

         if (desc.hasEnumerable()) {
            Properties.put(putEnumerableNode, obj, JSAttributes.ENUMERABLE, desc.getEnumerable());
         }

         if (desc.hasConfigurable()) {
            Properties.put(putConfigurableNode, obj, JSAttributes.CONFIGURABLE, desc.getConfigurable());
         }

         return obj;
      }
   }
}
