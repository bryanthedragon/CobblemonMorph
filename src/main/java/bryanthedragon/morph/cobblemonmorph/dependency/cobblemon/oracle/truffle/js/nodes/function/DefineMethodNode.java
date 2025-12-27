package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public class DefineMethodNode extends JavaScriptBaseNode {
   private final JSFunctionData functionData;
   @Node.Child
   private DefineMethodNode.FunctionCreateNode functionCreateNode;
   @Node.Child
   private PropertySetNode makeMethodNode;

   protected DefineMethodNode(JSContext context, JSFunctionData functionData, int blockScopeSlot) {
      this.functionData = functionData;
      this.functionCreateNode = DefineMethodNode.FunctionCreateNode.create(context, functionData, blockScopeSlot);
      this.makeMethodNode = PropertySetNode.createSetHidden(JSFunction.HOME_OBJECT_ID, context);
   }

   public static DefineMethodNode create(JSContext context, JSFunctionExpressionNode functionExpressionNode, int blockScopeSlot) {
      return new DefineMethodNode(context, functionExpressionNode.functionData, blockScopeSlot);
   }

   public JSFunctionData getFunctionData() {
      return this.functionData;
   }

   public JSFunctionObject execute(VirtualFrame frame, JSDynamicObject homeObject, JSDynamicObject functionPrototype) {
      assert JSRuntime.isObject(functionPrototype);

      assert JSRuntime.isObject(homeObject);

      JSFunctionObject closure = this.functionCreateNode.executeWithPrototype(frame, functionPrototype);
      this.makeMethodNode.setValue(closure, homeObject);
      return closure;
   }

   int getBlockScopeSlot() {
      return this.functionCreateNode.blockScopeSlot;
   }

   protected abstract static class FunctionCreateNode extends JavaScriptBaseNode {
      private final JSFunctionData functionData;
      @Node.Child
      private InitFunctionNode initFunctionNode;
      final int blockScopeSlot;

      protected FunctionCreateNode(JSContext context, JSFunctionData functionData, int blockScopeSlot) {
         assert context == functionData.getContext();

         this.functionData = functionData;
         this.initFunctionNode = InitFunctionNode.create(functionData);
         this.blockScopeSlot = blockScopeSlot;
      }

      public static DefineMethodNode.FunctionCreateNode create(JSContext context, JSFunctionData functionData, int blockScopeSlot) {
         return DefineMethodNodeFactory.FunctionCreateNodeGen.create(context, functionData, blockScopeSlot);
      }

      public abstract JSFunctionObject executeWithPrototype(VirtualFrame frame, Object prototype);

      @Specialization(
         guards = {"!getContext().isMultiContext()", "prototype == cachedPrototype", "isJSObject(cachedPrototype)"},
         limit = "getContext().getPropertyCacheLimit()"
      )
      protected final JSFunctionObject doCached(
         VirtualFrame frame,
         JSDynamicObject prototype,
         @Cached("prototype") JSDynamicObject cachedPrototype,
         @Cached("makeFactory(prototype)") JSFunctionFactory factory
      ) {
         return this.makeFunction(frame, factory, cachedPrototype);
      }

      @Specialization(guards = {"!getContext().isMultiContext()", "isJSObject(prototype)"}, replaces = "doCached")
      protected final JSFunctionObject doUncached(VirtualFrame frame, JSDynamicObject prototype) {
         JSFunctionFactory factory = this.makeFactory(prototype);
         return this.makeFunction(frame, factory, prototype);
      }

      @Specialization(guards = {"getContext().isMultiContext()", "isJSObject(prototype)"})
      protected final JSFunctionObject doMultiContext(
         VirtualFrame frame, JSDynamicObject prototype, @Cached("makeFactoryMultiContext()") JSFunctionFactory factory
      ) {
         return this.makeFunction(frame, factory, prototype);
      }

      @CompilerDirectives.TruffleBoundary
      protected final JSFunctionFactory makeFactory(JSDynamicObject prototype) {
         return JSFunctionFactory.create(this.getContext(), prototype);
      }

      protected final JSFunctionFactory makeFactoryMultiContext() {
         return this.makeFactory(null);
      }

      protected final JSFunctionObject makeFunction(VirtualFrame frame, JSFunctionFactory factory, JSDynamicObject prototype) {
         MaterializedFrame enclosingFrame;
         if (this.functionData.needsParentFrame()) {
            if (this.blockScopeSlot >= 0) {
               Object blockScope = frame.getObject(this.blockScopeSlot);
               enclosingFrame = JSFrameUtil.castMaterializedFrame(blockScope);
            } else {
               enclosingFrame = frame.materialize();
            }
         } else {
            enclosingFrame = JSFrameUtil.NULL_MATERIALIZED_FRAME;
         }

         JSRealm realm = this.getRealm();
         JSFunctionObject function = factory.createWithPrototype(this.functionData, enclosingFrame, JSFunction.CLASS_PROTOTYPE_PLACEHOLDER, realm, prototype);
         this.initFunctionNode.execute(function);
         return function;
      }

      final JSContext getContext() {
         return this.functionData.getContext();
      }

      @Specialization(guards = "!isJSObject(prototype)")
      protected final JSFunctionObject doNonObject(Object prototype) {
         throw Errors.createTypeError("functionPrototype not an object", this);
      }
   }
}
