package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.CompilableFunction;
import java.util.Set;

public class OrdinaryCreateFromConstructorNode extends JavaScriptNode {
   @Node.Child
   private GetPrototypeFromConstructorNode getPrototypeFromConstructorNode;
   @Node.Child
   private CreateObjectNode.CreateObjectWithPrototypeNode createObjectNode;

   protected OrdinaryCreateFromConstructorNode(
      JSContext context, JavaScriptNode constructorNode, CompilableFunction<JSRealm, JSDynamicObject> intrinsicDefaultProto, JSClass jsclass
   ) {
      this.getPrototypeFromConstructorNode = GetPrototypeFromConstructorNode.create(context, constructorNode, intrinsicDefaultProto);
      this.createObjectNode = CreateObjectNode.createWithPrototype(context, null, jsclass);
   }

   private OrdinaryCreateFromConstructorNode(
      GetPrototypeFromConstructorNode getPrototypeFromConstructorNode, CreateObjectNode.CreateObjectWithPrototypeNode createObjectNode
   ) {
      this.getPrototypeFromConstructorNode = getPrototypeFromConstructorNode;
      this.createObjectNode = createObjectNode;
   }

   public static OrdinaryCreateFromConstructorNode create(
      JSContext context, JavaScriptNode constructorNode, CompilableFunction<JSRealm, JSDynamicObject> intrinsicDefaultProto, JSClass jsclass
   ) {
      return new OrdinaryCreateFromConstructorNode(context, constructorNode, intrinsicDefaultProto, jsclass);
   }

   public JSDynamicObject execute(VirtualFrame frame) {
      JSDynamicObject proto = this.getPrototypeFromConstructorNode.execute(frame);
      return this.executeWithPrototype(proto);
   }

   public JSDynamicObject executeWithConstructor(JSDynamicObject constructor) {
      JSDynamicObject proto = this.getPrototypeFromConstructorNode.executeWithConstructor(constructor);
      return this.executeWithPrototype(proto);
   }

   private JSDynamicObject executeWithPrototype(JSDynamicObject proto) {
      return this.createObjectNode.execute(proto);
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return clazz == JSDynamicObject.class;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return new OrdinaryCreateFromConstructorNode(
         cloneUninitialized(this.getPrototypeFromConstructorNode, materializedTags), this.createObjectNode.copyUninitialized(materializedTags)
      );
   }
}
