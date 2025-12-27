package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;

public class JSUncheckedProxyHandler extends JSNonProxy implements PrototypeSupplier {
   public static final TruffleString CLASS_NAME = Strings.constant("UncheckedProxyHandler");
   public static final JSUncheckedProxyHandler INSTANCE = new JSUncheckedProxyHandler();

   public static JSDynamicObject create(JSContext context, JSRealm realm) {
      JSObjectFactory factory = context.getUncheckedProxyHandlerFactory();
      JSUncheckedProxyHandlerObject obj = new JSUncheckedProxyHandlerObject(factory.getShape(realm));
      factory.initProto(obj, realm);
      return context.trackAllocation(obj);
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return CLASS_NAME;
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getObjectPrototype();
   }

   @Override
   public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
   }
}
