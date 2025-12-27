package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

@NodeInfo(cost = NodeCost.NONE)
@ImportStatic(JSProxy.class)
public abstract class JSProxyHasPropertyNode extends JavaScriptBaseNode {
   @Node.Child
   protected GetMethodNode trapGetter;
   @Node.Child
   private JSFunctionCallNode callNode;
   @Node.Child
   private JSToBooleanNode toBooleanNode;
   @Node.Child
   private JSToPropertyKeyNode toPropertyKeyNode;
   @Node.Child
   private ForeignObjectPrototypeNode foreignObjectPrototypeNode;
   private final BranchProfile errorBranch = BranchProfile.create();

   public JSProxyHasPropertyNode(JSContext context) {
      this.callNode = JSFunctionCallNode.createCall();
      this.trapGetter = GetMethodNode.create(context, JSProxy.HAS);
      this.toPropertyKeyNode = JSToPropertyKeyNode.create();
      this.toBooleanNode = JSToBooleanNode.create();
   }

   public static JSProxyHasPropertyNode create(JSContext context) {
      return JSProxyHasPropertyNodeGen.create(context);
   }

   public abstract boolean executeWithTargetAndKeyBoolean(Object shared, Object key);

   @Specialization
   protected boolean doGeneric(JSDynamicObject proxy, Object key, @Cached("createBinaryProfile()") ConditionProfile trapFunProfile) {
      assert JSProxy.isJSProxy(proxy);

      Object propertyKey = this.toPropertyKeyNode.execute(key);
      JSDynamicObject handler = JSProxy.getHandlerChecked(proxy, this.errorBranch);
      Object target = JSProxy.getTarget(proxy);
      Object trapFun = this.trapGetter.executeWithTarget(handler);
      if (trapFunProfile.profile(trapFun == Undefined.instance)) {
         if (JSDynamicObject.isJSDynamicObject(target)) {
            return JSObject.hasProperty((JSDynamicObject)target, propertyKey);
         } else {
            boolean result = JSInteropUtil.hasProperty(target, propertyKey);
            if (!result) {
               result = this.maybeHasInPrototype(target, propertyKey);
            }

            return result;
         }
      } else {
         Object callResult = this.callNode.executeCall(JSArguments.create(handler, trapFun, target, propertyKey));
         boolean trapResult = this.toBooleanNode.executeBoolean(callResult);
         if (!trapResult && !JSProxy.checkPropertyIsSettable(target, propertyKey)) {
            this.errorBranch.enter();
            throw Errors.createTypeError("Proxy can't successfully access a non-writable, non-configurable property", this);
         } else {
            return trapResult;
         }
      }
   }

   private boolean maybeHasInPrototype(Object target, Object propertyKey) {
      assert JSRuntime.isPropertyKey(propertyKey);

      if (this.getLanguage().getJSContext().getContextOptions().hasForeignObjectPrototype()) {
         if (this.foreignObjectPrototypeNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.foreignObjectPrototypeNode = this.insert(ForeignObjectPrototypeNode.create());
         }

         JSDynamicObject prototype = this.foreignObjectPrototypeNode.execute(target);
         return JSObject.hasProperty(prototype, propertyKey);
      } else {
         return false;
      }
   }
}
