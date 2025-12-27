package com.oracle.truffle.js.runtime.builtins.temporal;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.temporal.TemporalCalendarFunctionBuiltins;
import com.oracle.truffle.js.builtins.temporal.TemporalCalendarPrototypeBuiltins;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSConstructorFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import com.oracle.truffle.js.runtime.util.TemporalErrors;
import com.oracle.truffle.js.runtime.util.TemporalUtil;

public final class JSTemporalCalendar extends JSNonProxy implements JSConstructorFactory.WithFunctionsAndSpecies, PrototypeSupplier {
   public static final JSTemporalCalendar INSTANCE = new JSTemporalCalendar();
   public static final TruffleString CLASS_NAME = Strings.constant("Calendar");
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("Calendar.prototype");
   public static final TruffleString TO_STRING_TAG = Strings.constant("Temporal.Calendar");

   private JSTemporalCalendar() {
   }

   public static JSTemporalCalendarObject create(JSContext context, JSRealm realm, TruffleString id) {
      if (!TemporalUtil.isBuiltinCalendar(id)) {
         throw TemporalErrors.createRangeErrorCalendarNotSupported();
      } else {
         return createIntl(context, realm != null ? realm : JSRealm.get(null), id);
      }
   }

   public static JSTemporalCalendarObject create(JSContext context, JSRealm realm, TruffleString id, BranchProfile errorBranch) {
      if (!TemporalUtil.isBuiltinCalendar(id)) {
         errorBranch.enter();
         throw TemporalErrors.createRangeErrorCalendarNotSupported();
      } else {
         return createIntl(context, realm, id);
      }
   }

   private static JSTemporalCalendarObject createIntl(JSContext context, JSRealm realm, TruffleString id) {
      JSObjectFactory factory = context.getTemporalCalendarFactory();
      JSTemporalCalendarObject obj = factory.initProto(new JSTemporalCalendarObject(factory.getShape(realm), id), realm);
      return context.trackAllocation(obj);
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return TO_STRING_TAG;
   }

   @Override
   public TruffleString getClassName() {
      return CLASS_NAME;
   }

   @Override
   public JSDynamicObject createPrototype(JSRealm realm, JSFunctionObject constructor) {
      JSContext ctx = realm.getContext();
      JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      JSObjectUtil.putConstructorProperty(ctx, prototype, constructor);
      JSObjectUtil.putBuiltinAccessorProperty(
         prototype, TemporalConstants.ID, realm.lookupAccessor(TemporalCalendarPrototypeBuiltins.BUILTINS, TemporalConstants.ID)
      );
      JSObjectUtil.putFunctionsFromContainer(realm, prototype, TemporalCalendarPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putToStringTag(prototype, TO_STRING_TAG);
      return prototype;
   }

   @Override
   public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
      return JSObjectUtil.getProtoChildShape(prototype, INSTANCE, context);
   }

   @Override
   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      return realm.getTemporalCalendarPrototype();
   }

   public static JSConstructor createConstructor(JSRealm realm) {
      return INSTANCE.createConstructorAndPrototype(realm, TemporalCalendarFunctionBuiltins.BUILTINS);
   }

   public static boolean isJSTemporalCalendar(Object obj) {
      return obj instanceof JSTemporalCalendarObject;
   }
}
