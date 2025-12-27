package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.SharedArrayBufferFunctionBuiltins;
import com.oracle.truffle.js.builtins.SharedArrayBufferPrototypeBuiltins;
import com.oracle.truffle.js.runtime.JSAgentWaiterList;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.util.DirectByteBufferHelper;
import java.nio.ByteBuffer;

public final class JSSharedArrayBuffer extends JSAbstractBuffer implements JSConstructorFactory.WithFunctionsAndSpecies, PrototypeSupplier {
   public static final TruffleString CLASS_NAME = Strings.constant("SharedArrayBuffer");
   public static final TruffleString PROTOTYPE_NAME = Strings.concat(CLASS_NAME, Strings.DOT_PROTOTYPE);
   public static final JSSharedArrayBuffer INSTANCE = new JSSharedArrayBuffer();

   private JSSharedArrayBuffer() {
   }

   public static JSArrayBufferObject createSharedArrayBuffer(JSContext context, JSRealm realm, int length) {
      return createSharedArrayBuffer(context, realm, DirectByteBufferHelper.allocateDirect(length));
   }

   public static JSArrayBufferObject createSharedArrayBuffer(JSContext context, JSRealm realm, ByteBuffer buffer) {
      assert buffer != null;

      JSObjectFactory factory = context.getSharedArrayBufferFactory();
      JSArrayBufferObject obj = JSArrayBufferObject.createSharedArrayBuffer(factory.getShape(realm), buffer, new JSAgentWaiterList());
      factory.initProto(obj, realm);

      assert isJSSharedArrayBuffer(obj);

      return context.trackAllocation(obj);
   }

   @Override
   public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject ctor) {
      JSContext context = realm.getContext();
      JSObject arrayBufferPrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      JSObjectUtil.putConstructorProperty(context, arrayBufferPrototype, ctor);
      JSObjectUtil.putFunctionsFromContainer(realm, arrayBufferPrototype, SharedArrayBufferPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putBuiltinAccessorProperty(arrayBufferPrototype, BYTE_LENGTH, realm.lookupAccessor(SharedArrayBufferPrototypeBuiltins.BUILTINS, BYTE_LENGTH));
      JSObjectUtil.putToStringTag(arrayBufferPrototype, CLASS_NAME);
      return arrayBufferPrototype;
   }

   @Override
   public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, SharedArrayBufferFunctionBuiltins.BUILTINS);
   }

   @Override
   public TruffleString getClassName() {
      return CLASS_NAME;
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return this.getClassName();
   }

   public static boolean isJSSharedArrayBuffer(Object obj) {
      return obj instanceof JSArrayBufferObject.Shared;
   }

   public static ByteBuffer getDirectByteBuffer(JSDynamicObject thisObj) {
      assert isJSSharedArrayBuffer(thisObj);

      return JSArrayBufferObject.getDirectByteBuffer(thisObj);
   }

   public static JSAgentWaiterList getWaiterList(JSDynamicObject thisObj) {
      assert isJSSharedArrayBuffer(thisObj);

      return JSArrayBufferObject.getWaiterList(thisObj);
   }

   public static void setWaiterList(JSDynamicObject thisObj, JSAgentWaiterList wl) {
      assert isJSSharedArrayBuffer(thisObj);

      JSArrayBufferObject.setWaiterList(thisObj, wl);
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getSharedArrayBufferPrototype();
   }
}
