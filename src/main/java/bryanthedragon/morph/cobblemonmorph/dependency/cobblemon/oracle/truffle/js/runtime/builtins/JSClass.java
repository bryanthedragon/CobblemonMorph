package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import java.util.ArrayList;
import java.util.List;

public abstract class JSClass {
   protected JSClass() {
   }

   @CompilerDirectives.TruffleBoundary
   public abstract JSDynamicObject getPrototypeOf(JSDynamicObject thisObj);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean setPrototypeOf(JSDynamicObject thisObj, JSDynamicObject newPrototype);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean isExtensible(JSDynamicObject thisObj);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean preventExtensions(JSDynamicObject thisObj, boolean doThrow);

   @CompilerDirectives.TruffleBoundary
   public abstract PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean defineOwnProperty(JSDynamicObject thisObj, Object key, PropertyDescriptor value, boolean doThrow);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean hasProperty(JSDynamicObject thisObj, Object key);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean hasProperty(JSDynamicObject thisObj, long index);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean hasOwnProperty(JSDynamicObject thisObj, Object key);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean hasOwnProperty(JSDynamicObject thisObj, long index);

   public final Object get(JSDynamicObject thisObj, Object key) {
      Object value = this.getHelper(thisObj, thisObj, key, null);

      assert !(value instanceof String);

      return JSRuntime.nullToUndefined(value);
   }

   public Object get(JSDynamicObject thisObj, long index) {
      Object value = this.getHelper(thisObj, thisObj, index, null);

      assert !(value instanceof String);

      return JSRuntime.nullToUndefined(value);
   }

   @CompilerDirectives.TruffleBoundary
   public abstract Object getHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode);

   @CompilerDirectives.TruffleBoundary
   public abstract Object getHelper(JSDynamicObject store, Object thisObj, long index, Node encapsulatingNode);

   @CompilerDirectives.TruffleBoundary
   public abstract Object getOwnHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode);

   @CompilerDirectives.TruffleBoundary
   public abstract Object getOwnHelper(JSDynamicObject store, Object thisObj, long index, Node encapsulatingNode);

   @CompilerDirectives.TruffleBoundary
   public abstract Object getMethodHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean set(JSDynamicObject thisObj, Object key, Object value, Object receiver, boolean isStrict, Node encapsulatingNode);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean set(JSDynamicObject thisObj, long index, Object value, Object receiver, boolean isStrict, Node encapsulatingNode);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean delete(JSDynamicObject thisObj, long index, boolean isStrict);

   public final List<Object> ownPropertyKeys(JSDynamicObject obj) {
      return this.getOwnPropertyKeys(obj, true, true);
   }

   @CompilerDirectives.TruffleBoundary
   public abstract List<Object> getOwnPropertyKeys(JSDynamicObject obj, boolean strings, boolean symbols);

   @CompilerDirectives.TruffleBoundary
   public static List<Object> filterOwnPropertyKeys(List<Object> ownPropertyKeys, boolean strings, boolean symbols) {
      if (strings && symbols) {
         return ownPropertyKeys;
      } else {
         List<Object> names = new ArrayList<>();

         for (Object key : ownPropertyKeys) {
            if ((symbols || !(key instanceof Symbol)) && (strings || !Strings.isTString(key))) {
               names.add(key);
            }
         }

         return names;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public abstract boolean hasOnlyShapeProperties(JSDynamicObject obj);

   @CompilerDirectives.TruffleBoundary
   public abstract TruffleString getClassName(JSDynamicObject object);

   @CompilerDirectives.TruffleBoundary
   @Override
   public abstract String toString();

   @CompilerDirectives.TruffleBoundary
   public TruffleString defaultToString(JSDynamicObject object) {
      JSContext context = JSObject.getJSContext(object);
      if (context.getEcmaScriptVersion() <= 5) {
         return this.formatToString(this.getClassName(object));
      } else {
         TruffleString result = this.getToStringTag(object);
         return this.formatToString(result);
      }
   }

   protected TruffleString getToStringTag(JSDynamicObject object) {
      TruffleString result = this.getBuiltinToStringTag(object);
      if (JSRuntime.isObject(object)) {
         Object toStringTag = JSObject.get(object, Symbol.SYMBOL_TO_STRING_TAG);
         if (Strings.isTString(toStringTag)) {
            result = JSRuntime.toStringIsString(toStringTag);
         }
      }

      return result;
   }

   @CompilerDirectives.TruffleBoundary
   public TruffleString getBuiltinToStringTag(JSDynamicObject object) {
      return this.getClassName(object);
   }

   @CompilerDirectives.TruffleBoundary
   protected TruffleString formatToString(TruffleString object) {
      return Strings.concatAll(Strings.BRACKET_OBJECT_SPC, object, Strings.BRACKET_CLOSE);
   }

   @CompilerDirectives.TruffleBoundary
   public abstract TruffleString toDisplayStringImpl(JSDynamicObject object, boolean allowSideEffects, ToDisplayStringFormat format, int depth);

   public final boolean isInstance(JSDynamicObject object) {
      return isInstance(object, this);
   }

   public final boolean isInstance(Object object) {
      return isInstance(object, this);
   }

   public static boolean isInstance(Object object, JSClass jsclass) {
      return JSDynamicObject.isJSDynamicObject(object) && isInstance((JSDynamicObject)object, jsclass);
   }

   public static boolean isInstance(JSDynamicObject object, JSClass jsclass) {
      return object.getShape().getDynamicType() == jsclass;
   }

   @CompilerDirectives.TruffleBoundary
   public boolean testIntegrityLevel(JSDynamicObject obj, boolean frozen) {
      return this.testIntegrityLevelDefault(obj, frozen);
   }

   @CompilerDirectives.TruffleBoundary
   protected final boolean testIntegrityLevelDefault(JSDynamicObject obj, boolean frozen) {
      assert JSRuntime.isObject(obj);

      boolean status = this.isExtensible(obj);
      if (status) {
         return false;
      } else {
         for (Object key : JSObject.ownPropertyKeys(obj)) {
            PropertyDescriptor desc = JSObject.getOwnProperty(obj, key);
            if (desc != null) {
               if (desc.getConfigurable()) {
                  return false;
               }

               if (frozen && desc.isDataDescriptor() && desc.getWritable()) {
                  return false;
               }
            }
         }

         return true;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public boolean setIntegrityLevel(JSDynamicObject obj, boolean freeze, boolean doThrow) {
      return this.setIntegrityLevelDefault(obj, freeze, doThrow);
   }

   @CompilerDirectives.TruffleBoundary
   private boolean setIntegrityLevelDefault(JSDynamicObject obj, boolean freeze, boolean doThrow) {
      assert JSRuntime.isObject(obj);

      if (!this.preventExtensions(obj, doThrow)) {
         return false;
      } else {
         Iterable<Object> keys = JSObject.ownPropertyKeys(obj);
         if (freeze) {
            for (Object key : keys) {
               PropertyDescriptor currentDesc = JSObject.getOwnProperty(obj, key);
               if (currentDesc != null) {
                  PropertyDescriptor newDesc = null;
                  if (currentDesc.isAccessorDescriptor()) {
                     newDesc = JSClass.FreezeHolder.FREEZE_ACC_DESC;
                  } else {
                     newDesc = JSClass.FreezeHolder.FREEZE_DATA_DESC;
                  }

                  JSRuntime.definePropertyOrThrow(obj, key, newDesc);
               }
            }
         } else {
            for (Object keyx : keys) {
               JSRuntime.definePropertyOrThrow(obj, keyx, JSClass.FreezeHolder.FREEZE_ACC_DESC);
            }
         }

         return true;
      }
   }

   public Shape makeInitialShape(JSContext context, JSDynamicObject prototype) {
      throw Errors.shouldNotReachHere(this.getClass().getName());
   }

   public JSDynamicObject getIntrinsicDefaultProto(JSRealm realm) {
      throw Errors.shouldNotReachHere(this.getClass().getName());
   }

   public abstract boolean usesOrdinaryGetOwnProperty();

   public abstract boolean usesOrdinaryIsExtensible();

   private static final class FreezeHolder {
      private static final PropertyDescriptor FREEZE_ACC_DESC = PropertyDescriptor.createEmpty();
      private static final PropertyDescriptor FREEZE_DATA_DESC = PropertyDescriptor.createEmpty();

      static {
         FREEZE_ACC_DESC.setConfigurable(false);
         FREEZE_DATA_DESC.setConfigurable(false);
         FREEZE_DATA_DESC.setWritable(false);
      }
   }
}
