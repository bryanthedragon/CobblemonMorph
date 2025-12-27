package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSDictionary;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Null;
import java.util.Set;

public abstract class CreateObjectNode extends JavaScriptBaseNode {
   protected final JSContext context;

   protected CreateObjectNode(JSContext context) {
      this.context = context;
   }

   public static CreateObjectNode create(JSContext context) {
      return new CreateObjectNode.CreateOrdinaryObjectNode(context);
   }

   public static CreateObjectNode.CreateObjectWithPrototypeNode createOrdinaryWithPrototype(JSContext context) {
      return createWithPrototype(context, null, JSOrdinary.INSTANCE);
   }

   public static CreateObjectNode.CreateObjectWithPrototypeNode createOrdinaryWithPrototype(JSContext context, JavaScriptNode prototypeExpression) {
      return createWithPrototype(context, prototypeExpression, JSOrdinary.INSTANCE);
   }

   public static CreateObjectNode.CreateObjectWithPrototypeNode createWithPrototype(JSContext context, JavaScriptNode prototypeExpression, JSClass jsclass) {
      return CreateObjectNode.CreateObjectWithCachedPrototypeNode.create(context, prototypeExpression, jsclass);
   }

   static CreateObjectNode createDictionary(JSContext context) {
      return new CreateObjectNode.CreateDictionaryObjectNode(context);
   }

   public JSDynamicObject execute(VirtualFrame frame) {
      return this.executeWithRealm(frame, this.getRealm());
   }

   public abstract JSDynamicObject executeWithRealm(VirtualFrame frame, JSRealm realm);

   protected abstract CreateObjectNode copyUninitialized(Set<Class<? extends Tag>> materializedTags);

   final JSContext getContext() {
      return this.context;
   }

   private static class CreateDictionaryObjectNode extends CreateObjectNode {
      protected CreateDictionaryObjectNode(JSContext context) {
         super(context);
      }

      @Override
      public JSDynamicObject executeWithRealm(VirtualFrame frame, JSRealm realm) {
         return JSDictionary.create(this.context, realm);
      }

      @Override
      protected CreateObjectNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new CreateObjectNode.CreateDictionaryObjectNode(this.context);
      }
   }

   protected abstract static class CreateObjectWithCachedPrototypeNode extends CreateObjectNode.CreateObjectWithPrototypeNode {
      protected final JSClass jsclass;

      protected CreateObjectWithCachedPrototypeNode(JSContext context, JavaScriptNode prototypeExpression, JSClass jsclass) {
         super(context, prototypeExpression);
         this.jsclass = jsclass;

         assert this.isOrdinaryObject() || this.isPromiseObject();
      }

      protected static CreateObjectNode.CreateObjectWithPrototypeNode create(JSContext context, JavaScriptNode prototypeExpression, JSClass jsclass) {
         return CreateObjectNodeFactory.CreateObjectWithCachedPrototypeNodeGen.create(context, prototypeExpression, jsclass);
      }

      @Specialization(guards = {"!context.isMultiContext()", "isValidPrototype(cachedPrototype)", "prototype == cachedPrototype"}, limit = "1")
      final JSDynamicObject doCachedPrototype(
         JSDynamicObject prototype, @Cached("prototype") JSDynamicObject cachedPrototype, @Cached("getProtoChildShape(cachedPrototype)") Shape protoChildShape
      ) {
         if (this.isPromiseObject()) {
            return JSPromise.create(this.context, protoChildShape);
         } else if (this.isOrdinaryObject()) {
            return JSOrdinary.create(this.context, protoChildShape);
         } else {
            throw Errors.unsupported("unsupported object type");
         }
      }

      @Specialization(guards = {"isOrdinaryObject()", "isValidPrototype(prototype)"}, replaces = "doCachedPrototype")
      final JSDynamicObject doOrdinaryInstancePrototype(
         JSDynamicObject prototype, @CachedLibrary(limit = "3") @Cached.Shared("setProtoNode") DynamicObjectLibrary setProtoNode
      ) {
         JSDynamicObject object = JSOrdinary.createWithoutPrototype(this.context);
         Properties.put(setProtoNode, object, JSObject.HIDDEN_PROTO, prototype);
         return object;
      }

      @Specialization(guards = {"isPromiseObject()", "isValidPrototype(prototype)"}, replaces = "doCachedPrototype")
      final JSDynamicObject doPromiseInstancePrototype(
         JSDynamicObject prototype, @CachedLibrary(limit = "3") @Cached.Shared("setProtoNode") DynamicObjectLibrary setProtoNode
      ) {
         JSDynamicObject object = JSPromise.createWithoutPrototype(this.context);
         Properties.put(setProtoNode, object, JSObject.HIDDEN_PROTO, prototype);
         return object;
      }

      @Specialization(guards = {"isOrdinaryObject() || isPromiseObject()", "!isValidPrototype(prototype)"})
      final JSDynamicObject doNotJSObjectOrNull(Object prototype) {
         return JSOrdinary.create(this.context, this.getRealm());
      }

      final Shape getProtoChildShape(JSDynamicObject prototype) {
         return prototype == Null.instance ? this.context.getEmptyShapeNullPrototype() : JSObjectUtil.getProtoChildShape(prototype, this.jsclass, this.context);
      }

      final boolean isOrdinaryObject() {
         return this.jsclass == JSOrdinary.INSTANCE;
      }

      final boolean isPromiseObject() {
         return this.jsclass == JSPromise.INSTANCE;
      }

      @Override
      protected CreateObjectNode.CreateObjectWithPrototypeNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return create(this.context, JavaScriptNode.cloneUninitialized(this.prototypeExpression, materializedTags), this.jsclass);
      }
   }

   public abstract static class CreateObjectWithPrototypeNode extends CreateObjectNode {
      @Node.Child
      @Executed
      protected JavaScriptNode prototypeExpression;

      protected CreateObjectWithPrototypeNode(JSContext context, JavaScriptNode prototypeExpression) {
         super(context);
         this.prototypeExpression = prototypeExpression;
      }

      public abstract JSDynamicObject execute(JSDynamicObject prototype);

      @Override
      public final JSDynamicObject executeWithRealm(VirtualFrame frame, JSRealm realm) {
         return this.execute(frame);
      }

      protected abstract CreateObjectNode.CreateObjectWithPrototypeNode copyUninitialized(Set<Class<? extends Tag>> materializedTags);
   }

   private static class CreateOrdinaryObjectNode extends CreateObjectNode {
      protected CreateOrdinaryObjectNode(JSContext context) {
         super(context);
      }

      @Override
      public JSDynamicObject executeWithRealm(VirtualFrame frame, JSRealm realm) {
         return JSOrdinary.create(this.context, realm);
      }

      @Override
      protected CreateObjectNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new CreateObjectNode.CreateOrdinaryObjectNode(this.context);
      }
   }
}
