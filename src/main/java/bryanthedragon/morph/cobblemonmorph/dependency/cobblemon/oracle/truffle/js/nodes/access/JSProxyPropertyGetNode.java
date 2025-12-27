package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.NodeInfo;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.binary.JSIdenticalNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.builtins.JSUncheckedProxyHandlerObject;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSClassProfile;

@NodeInfo(cost = NodeCost.NONE)
public abstract class JSProxyPropertyGetNode extends JavaScriptBaseNode {
   @Node.Child
   protected GetMethodNode trapGet;
   @Node.Child
   private JSFunctionCallNode callNode;
   @Node.Child
   private JSGetOwnPropertyNode getOwnPropertyNode;
   @Node.Child
   private JSIdenticalNode sameValueNode;
   @Node.Child
   private ForeignObjectPrototypeNode foreignObjectPrototypeNode;
   private final BranchProfile errorBranch = BranchProfile.create();

   protected JSProxyPropertyGetNode(JSContext context) {
      this.callNode = JSFunctionCallNode.createCall();
      this.trapGet = GetMethodNode.create(context, JSProxy.GET);
   }

   public static JSProxyPropertyGetNode create(JSContext context) {
      return JSProxyPropertyGetNodeGen.create(context);
   }

   public abstract Object executeWithReceiver(Object proxy, Object receiver, Object key, Object defaultValue);

   @Specialization
   protected Object doGeneric(
      JSDynamicObject proxy,
      Object receiver,
      Object key,
      Object defaultValue,
      @Cached JSToPropertyKeyNode toPropertyKeyNode,
      @Cached("createBinaryProfile()") ConditionProfile hasTrap,
      @Cached JSClassProfile targetClassProfile
   ) {
      assert JSProxy.isJSProxy(proxy);

      assert !(key instanceof HiddenKey);

      Object propertyKey = toPropertyKeyNode.execute(key);
      JSDynamicObject handler = JSProxy.getHandlerChecked(proxy, this.errorBranch);
      Object target = JSProxy.getTarget(proxy);
      Object trapFun = this.trapGet.executeWithTarget(handler);
      if (hasTrap.profile(trapFun == Undefined.instance)) {
         if (JSDynamicObject.isJSDynamicObject(target)) {
            return JSObject.getOrDefault((JSDynamicObject)target, propertyKey, receiver, defaultValue, targetClassProfile, this);
         } else {
            Object result = JSInteropUtil.readMemberOrDefault(target, propertyKey, null);
            if (result == null) {
               result = this.maybeGetFromPrototype(target, propertyKey, receiver, defaultValue, targetClassProfile);
            }

            return result;
         }
      } else {
         Object trapResult = this.callNode.executeCall(JSArguments.create(handler, trapFun, target, propertyKey, receiver));
         if (!(handler instanceof JSUncheckedProxyHandlerObject)) {
            this.checkInvariants(propertyKey, target, trapResult);
         }

         return trapResult;
      }
   }

   private Object maybeGetFromPrototype(Object target, Object propertyKey, Object receiver, Object defaultValue, JSClassProfile protoClassProfile) {
      assert JSRuntime.isPropertyKey(propertyKey);

      if (this.getLanguage().getJSContext().getContextOptions().hasForeignObjectPrototype()) {
         if (this.foreignObjectPrototypeNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.foreignObjectPrototypeNode = this.insert(ForeignObjectPrototypeNode.create());
         }

         JSDynamicObject prototype = this.foreignObjectPrototypeNode.execute(target);
         return JSObject.getOrDefault(prototype, propertyKey, receiver, defaultValue, protoClassProfile, this);
      } else {
         return defaultValue;
      }
   }

   private void checkInvariants(Object propertyKey, Object proxyTarget, Object trapResult) {
      assert JSRuntime.isPropertyKey(propertyKey);

      if (JSDynamicObject.isJSDynamicObject(proxyTarget)) {
         PropertyDescriptor targetDesc = this.getOwnProperty((JSDynamicObject)proxyTarget, propertyKey);
         if (targetDesc != null) {
            if (targetDesc.isDataDescriptor() && !targetDesc.getConfigurable() && !targetDesc.getWritable()) {
               Object targetValue = targetDesc.getValue();
               if (!this.isSameValue(trapResult, targetValue)) {
                  this.errorBranch.enter();
                  throw Errors.createTypeErrorProxyGetInvariantViolated(propertyKey, targetValue, trapResult);
               }
            }

            if (targetDesc.isAccessorDescriptor()
               && !targetDesc.getConfigurable()
               && targetDesc.getGet() == Undefined.instance
               && trapResult != Undefined.instance) {
               this.errorBranch.enter();
               throw Errors.createTypeError(
                  "Trap result must be undefined since the proxy target has a corresponding non-configurable own accessor property with undefined getter"
               );
            }
         }
      }
   }

   private boolean isSameValue(Object trapResult, Object value) {
      if (this.sameValueNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.sameValueNode = this.insert(JSIdenticalNode.createSameValue());
      }

      return this.sameValueNode.executeBoolean(trapResult, value);
   }

   private PropertyDescriptor getOwnProperty(JSDynamicObject target, Object propertyKey) {
      if (this.getOwnPropertyNode == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.getOwnPropertyNode = this.insert(JSGetOwnPropertyNode.create());
      }

      return this.getOwnPropertyNode.execute(target, propertyKey);
   }
}
