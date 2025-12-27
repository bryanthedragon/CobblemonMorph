package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.util.CompilableFunction;
import java.util.Set;

public class GetPrototypeFromConstructorNode extends JavaScriptNode {
   private final CompilableFunction<JSRealm, JSDynamicObject> intrinsicDefaultProto;
   @Node.Child
   private JavaScriptNode constructorNode;
   @Node.Child
   private PropertyGetNode getPrototypeNode;
   @Node.Child
   private IsJSObjectNode isObjectNode;

   protected GetPrototypeFromConstructorNode(
      JSContext context, JavaScriptNode constructorNode, CompilableFunction<JSRealm, JSDynamicObject> intrinsicDefaultProto
   ) {
      this.constructorNode = constructorNode;
      this.intrinsicDefaultProto = intrinsicDefaultProto;
      this.getPrototypeNode = PropertyGetNode.create(JSObject.PROTOTYPE, false, context);
      this.isObjectNode = IsJSObjectNode.create();
   }

   public static GetPrototypeFromConstructorNode create(
      JSContext context, JavaScriptNode constructorNode, CompilableFunction<JSRealm, JSDynamicObject> intrinsicDefaultProto
   ) {
      return new GetPrototypeFromConstructorNode(context, constructorNode, intrinsicDefaultProto);
   }

   public JSDynamicObject execute(VirtualFrame frame) {
      Object constructor = this.constructorNode.execute(frame);
      return this.executeWithConstructor((JSDynamicObject)constructor);
   }

   public JSDynamicObject executeWithConstructor(JSDynamicObject constructor) {
      assert JSRuntime.isCallable(constructor);

      Object proto = this.getPrototypeNode.getValue(constructor);
      if (this.isObjectNode.executeBoolean(proto)) {
         assert JSRuntime.isObject(proto);

         return (JSDynamicObject)proto;
      } else {
         JSRealm realm = JSRuntime.getFunctionRealm(constructor, this.getRealm());
         return this.intrinsicDefaultProto.apply(realm);
      }
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return clazz == JSDynamicObject.class;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new GetPrototypeFromConstructorNode(
         this.getPrototypeNode.getContext(), cloneUninitialized(this.constructorNode, materializedTags), this.intrinsicDefaultProto
      );
   }
}
