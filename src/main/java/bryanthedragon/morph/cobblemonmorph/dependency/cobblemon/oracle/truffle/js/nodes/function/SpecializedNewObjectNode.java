package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ReportPolymorphism;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import com.oracle.truffle.js.nodes.access.PropertyNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;

public abstract class SpecializedNewObjectNode extends JavaScriptBaseNode {
   protected final JSContext context;
   protected final boolean isBuiltin;
   protected final boolean isConstructor;
   protected final boolean isGenerator;
   protected final boolean isAsyncGenerator;
   @Node.Child
   private JSTargetableNode getPrototypeNode;
   protected final JSOrdinary instanceLayout;

   public SpecializedNewObjectNode(
      JSContext context, boolean isBuiltin, boolean isConstructor, boolean isGenerator, boolean isAsyncGenerator, JSOrdinary instanceLayout
   ) {
      this.context = context;
      this.isBuiltin = isBuiltin;
      this.isConstructor = isConstructor;
      this.isGenerator = isGenerator;
      this.isAsyncGenerator = isAsyncGenerator;
      this.instanceLayout = instanceLayout;
      this.getPrototypeNode = !isBuiltin && isConstructor ? PropertyNode.createProperty(context, null, JSObject.PROTOTYPE) : null;
   }

   public static SpecializedNewObjectNode create(
      JSContext context, boolean isBuiltin, boolean isConstructor, boolean isGenerator, boolean isAsyncGenerator, JSOrdinary instanceLayout
   ) {
      return SpecializedNewObjectNodeGen.create(context, isBuiltin, isConstructor, isGenerator, isAsyncGenerator, instanceLayout);
   }

   public static SpecializedNewObjectNode create(JSContext context, boolean isBuiltin, boolean isConstructor, boolean isGenerator, boolean isAsyncGenerator) {
      return create(context, isBuiltin, isConstructor, isGenerator, isAsyncGenerator, JSOrdinary.INSTANCE);
   }

   public static SpecializedNewObjectNode create(JSFunctionData functionData, JSOrdinary instanceLayout) {
      return create(
         functionData.getContext(),
         functionData.isBuiltin(),
         functionData.isConstructor(),
         functionData.isGenerator(),
         functionData.isAsyncGenerator(),
         instanceLayout
      );
   }

   public final JSDynamicObject execute(VirtualFrame frame, JSDynamicObject newTarget) {
      Object prototype = this.getPrototypeNode != null ? this.getPrototypeNode.executeWithTarget(frame, newTarget) : Undefined.instance;
      return this.execute(newTarget, prototype);
   }

   protected abstract JSDynamicObject execute(JSDynamicObject newTarget, Object prototype);

   protected Shape getProtoChildShape(Object prototype) {
      CompilerAsserts.neverPartOfCompilation();
      if (JSGuards.isJSObject(prototype)) {
         JSObject jsproto = (JSObject)prototype;
         return JSObjectUtil.getProtoChildShape(jsproto, this.instanceLayout, this.context);
      } else {
         return null;
      }
   }

   protected Shape getShapeWithoutProto() {
      CompilerAsserts.neverPartOfCompilation();
      return JSObjectUtil.getProtoChildShape(null, this.instanceLayout, this.context);
   }

   @Specialization(
      guards = {"!isBuiltin", "isConstructor", "!context.isMultiContext()", "isJSObject(cachedPrototype)", "prototype == cachedPrototype"},
      limit = "context.getPropertyCacheLimit()"
   )
   public JSDynamicObject doCachedProto(
      JSDynamicObject target, Object prototype, @Cached("prototype") Object cachedPrototype, @Cached("getProtoChildShape(prototype)") Shape shape
   ) {
      return JSOrdinary.create(this.context, shape);
   }

   @Specialization(guards = {"!isBuiltin", "isConstructor", "!context.isMultiContext()", "isJSObject(prototype)"}, replaces = "doCachedProto")
   @ReportPolymorphism.Megamorphic
   public JSDynamicObject doUncachedProto(JSDynamicObject target, JSDynamicObject prototype, @Cached("create()") BranchProfile slowBranch) {
      Shape shape = JSObjectUtil.getProtoChildShape(prototype, this.instanceLayout, this.context, slowBranch);
      return JSOrdinary.create(this.context, shape);
   }

   @Specialization(
      guards = {"!isBuiltin", "isConstructor", "context.isMultiContext()", "prototypeClass != null", "prototypeClass.isInstance(prototype)"},
      limit = "1"
   )
   public JSDynamicObject createWithProtoCachedClass(
      JSDynamicObject target,
      Object prototype,
      @CachedLibrary(limit = "3") @Cached.Shared("setProtoNode") DynamicObjectLibrary setProtoNode,
      @Cached("getClassIfJSObject(prototype)") Class<?> prototypeClass,
      @Cached("getShapeWithoutProto()") Shape cachedShape
   ) {
      return this.createWithProto(target, (JSDynamicObject)prototypeClass.cast(prototype), setProtoNode, cachedShape);
   }

   @Specialization(guards = {"!isBuiltin", "isConstructor", "context.isMultiContext()", "isJSObject(prototype)"})
   public JSDynamicObject createWithProto(
      JSDynamicObject target,
      JSDynamicObject prototype,
      @CachedLibrary(limit = "3") @Cached.Shared("setProtoNode") DynamicObjectLibrary setProtoNode,
      @Cached("getShapeWithoutProto()") Shape cachedShape
   ) {
      JSDynamicObject object = JSOrdinary.create(this.context, cachedShape);
      setProtoNode.put(object, JSObject.HIDDEN_PROTO, prototype);
      return object;
   }

   @Specialization(guards = {"!isBuiltin", "isConstructor", "!isJSObject(prototype)"})
   public JSDynamicObject createDefaultProto(JSDynamicObject target, Object prototype) {
      JSRealm realm = JSRuntime.getFunctionRealm(target, this.getRealm());
      if (this.isAsyncGenerator) {
         return JSOrdinary.createWithRealm(this.context, this.context.getAsyncGeneratorObjectFactory(), realm);
      } else {
         return this.isGenerator
            ? JSOrdinary.createWithRealm(this.context, this.context.getGeneratorObjectFactory(), realm)
            : JSOrdinary.create(this.context, realm);
      }
   }

   @Specialization(guards = {"isBuiltin", "isConstructor"})
   static JSDynamicObject builtinConstructor(JSDynamicObject target, Object proto) {
      return JSFunction.CONSTRUCT;
   }

   @Specialization(guards = "!isConstructor")
   public JSDynamicObject throwNotConstructorFunctionTypeError(JSDynamicObject target, Object proto) {
      throw Errors.createTypeErrorNotAConstructor(target, this.context);
   }
}
