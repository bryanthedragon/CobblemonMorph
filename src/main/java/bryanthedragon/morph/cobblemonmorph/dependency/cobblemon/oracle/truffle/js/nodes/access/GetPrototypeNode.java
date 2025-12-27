package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Location;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.RepeatableNode;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.util.Set;

@GenerateUncached
public abstract class GetPrototypeNode extends JavaScriptBaseNode {
   static final int MAX_SHAPE_COUNT = 2;

   GetPrototypeNode() {
   }

   public abstract JSDynamicObject execute(JSDynamicObject obj);

   public abstract JSDynamicObject execute(Object obj);

   public static GetPrototypeNode create() {
      return GetPrototypeNodeGen.create();
   }

   public static JavaScriptNode create(JavaScriptNode object) {
      assert object instanceof RepeatableNode;

      class GetPrototypeOfNode extends JavaScriptNode implements RepeatableNode {
         @Node.Child
         private JavaScriptNode objectNode = object;
         @Node.Child
         private GetPrototypeNode getPrototypeNode = GetPrototypeNode.create();

         @Override
         public Object execute(VirtualFrame frame) {
            return this.getPrototypeNode.execute(this.objectNode.execute(frame));
         }

         @Override
         protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
            return new GetPrototypeOfNode();
         }
      }

      return new GetPrototypeOfNode();
   }

   static Location getPrototypeLocation(Shape shape) {
      if (JSShape.getJSClass(shape) == JSProxy.INSTANCE) {
         return null;
      } else {
         Property prototypeProperty = JSShape.getPrototypeProperty(shape);
         return prototypeProperty != null ? prototypeProperty.getLocation() : null;
      }
   }

   @Specialization(guards = {"obj.getShape() == shape", "prototypeLocation != null"}, limit = "MAX_SHAPE_COUNT")
   static JSDynamicObject doCachedShape(
      JSDynamicObject obj, @Cached("obj.getShape()") Shape shape, @Cached("getPrototypeLocation(shape)") Location prototypeLocation
   ) {
      assert !JSGuards.isJSProxy(obj);

      return (JSDynamicObject)prototypeLocation.get(obj, shape);
   }

   @Specialization(guards = "!isJSProxy(obj)", replaces = "doCachedShape")
   static JSDynamicObject doGeneric(JSDynamicObject obj) {
      return JSObjectUtil.getPrototype(obj);
   }

   @Specialization(guards = "isJSProxy(obj)")
   static JSDynamicObject doProxy(JSDynamicObject obj, @Cached("create()") JSClassProfile jsclassProfile) {
      return JSObject.getPrototype(obj, jsclassProfile);
   }

   @Specialization(guards = "!isJSDynamicObject(obj)")
   static JSDynamicObject doNotObject(Object obj) {
      return Null.instance;
   }
}
