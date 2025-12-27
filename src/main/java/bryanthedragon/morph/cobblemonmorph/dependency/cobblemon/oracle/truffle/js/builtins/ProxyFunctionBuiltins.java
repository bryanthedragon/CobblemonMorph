package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.access.CreateDataPropertyNode;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.IsConstructorNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.builtins.JSProxyObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;

public final class ProxyFunctionBuiltins extends JSBuiltinsContainer.Lambda {
   public static final JSBuiltinsContainer BUILTINS = new ProxyFunctionBuiltins();

   protected ProxyFunctionBuiltins() {
      super(JSProxy.CLASS_NAME);
      this.defineFunction(
         Strings.REVOCABLE,
         2,
         (context, builtin) -> ProxyFunctionBuiltinsFactory.RevocableNodeGen.create(context, builtin, args().fixedArgs(2).createArgumentNodes(context))
      );
   }

   public abstract static class RevocableNode extends JSBuiltinNode {
      @Node.Child
      private ConstructorBuiltins.ConstructJSProxyNode proxyCreateNode;
      @Node.Child
      private PropertySetNode setRevocableProxySlotNode;
      @Node.Child
      private CreateObjectNode createObjectNode;
      @Node.Child
      private CreateDataPropertyNode createProxyPropertyNode;
      @Node.Child
      private CreateDataPropertyNode createRevokePropertyNode;

      public RevocableNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
         this.proxyCreateNode = ConstructorBuiltinsFactory.ConstructJSProxyNodeGen.create(context, builtin, false, null);
         this.setRevocableProxySlotNode = PropertySetNode.createSetHidden(JSProxy.REVOCABLE_PROXY, context);
         this.createObjectNode = CreateObjectNode.create(context);
         this.createProxyPropertyNode = CreateDataPropertyNode.create(context, Strings.PROXY);
         this.createRevokePropertyNode = CreateDataPropertyNode.create(context, Strings.REVOKE);
      }

      @Specialization
      protected Object doDefault(VirtualFrame frame, Object target, Object handler) {
         JSDynamicObject proxy = this.proxyCreateNode.execute(Undefined.instance, target, handler);
         JSFunctionData revokerFunctionData = this.getContext()
            .getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.ProxyRevokerFunction, c -> createProxyRevokerFunctionImpl(c));
         JSDynamicObject revoker = JSFunction.create(this.getRealm(), revokerFunctionData);
         this.setRevocableProxySlotNode.setValue(revoker, proxy);
         JSDynamicObject result = this.createObjectNode.execute(frame);
         this.createProxyPropertyNode.executeVoid(result, proxy);
         this.createRevokePropertyNode.executeVoid(result, revoker);
         return result;
      }

      private static JSFunctionData createProxyRevokerFunctionImpl(JSContext context) {
         CallTarget callTarget = (new JavaScriptRootNode() {
            @Node.Child
            private PropertyGetNode getRevocableProxyNode = PropertyGetNode.createGetHidden(JSProxy.REVOCABLE_PROXY, context);
            @Node.Child
            private PropertySetNode setRevocableProxyNode = PropertySetNode.createSetHidden(JSProxy.REVOCABLE_PROXY, context);
            @Node.Child
            private IsCallableNode isCallableNode = IsCallableNode.create();
            @Node.Child
            private IsConstructorNode isConstructorNode = IsConstructorNode.create();

            @Override
            public Object execute(VirtualFrame frame) {
               JSDynamicObject functionObject = JSFrameUtil.getFunctionObject(frame);
               Object revocableProxy = this.getRevocableProxyNode.getValue(functionObject);
               if (revocableProxy == Null.instance) {
                  return Undefined.instance;
               } else {
                  this.setRevocableProxyNode.setValue(functionObject, Null.instance);
                  boolean callable = this.isCallableNode.executeBoolean(revocableProxy);
                  boolean constructor = this.isConstructorNode.executeBoolean(revocableProxy);
                  ((JSProxyObject)revocableProxy).revoke(callable, constructor);
                  return Undefined.instance;
               }
            }
         }).getCallTarget();
         return JSFunctionData.createCallOnly(context, callTarget, 0, Strings.EMPTY_STRING);
      }
   }
}
