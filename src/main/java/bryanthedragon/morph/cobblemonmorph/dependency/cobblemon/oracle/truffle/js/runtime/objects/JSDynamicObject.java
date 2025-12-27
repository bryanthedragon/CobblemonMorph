package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import java.util.List;
import java.util.function.IntUnaryOperator;
import java.util.function.Predicate;

@ExportLibrary(InteropLibrary.class)
public abstract class JSDynamicObject extends DynamicObject implements TruffleObject {
   protected JSDynamicObject(Shape shape) {
      super(shape);
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   public final int identityHashCode() {
      return super.hashCode();
   }

   public final JSContext getJSContext() {
      return getJSSharedData(this).getContext();
   }

   public JSClass getJSClass() {
      return (JSClass)getDynamicType(this);
   }

   @CompilerDirectives.TruffleBoundary
   public abstract JSDynamicObject getPrototypeOf();

   @CompilerDirectives.TruffleBoundary
   public abstract boolean setPrototypeOf(JSDynamicObject newPrototype);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean isExtensible();

   @CompilerDirectives.TruffleBoundary
   public abstract boolean preventExtensions(boolean doThrow);

   @CompilerDirectives.TruffleBoundary
   public abstract PropertyDescriptor getOwnProperty(Object propertyKey);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean defineOwnProperty(Object key, PropertyDescriptor value, boolean doThrow);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean hasProperty(Object key);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean hasProperty(long index);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean hasOwnProperty(Object propName);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean hasOwnProperty(long index);

   public Object getValue(Object key) {
      return JSRuntime.nullToUndefined(this.getHelper(this, key, null));
   }

   public Object getValue(long index) {
      return JSRuntime.nullToUndefined(this.getHelper(this, index, null));
   }

   @CompilerDirectives.TruffleBoundary
   public abstract Object getHelper(Object receiver, Object key, Node encapsulatingNode);

   @CompilerDirectives.TruffleBoundary
   public abstract Object getHelper(Object receiver, long index, Node encapsulatingNode);

   @CompilerDirectives.TruffleBoundary
   public abstract Object getOwnHelper(Object receiver, Object key, Node encapsulatingNode);

   @CompilerDirectives.TruffleBoundary
   public abstract Object getOwnHelper(Object receiver, long index, Node encapsulatingNode);

   @CompilerDirectives.TruffleBoundary
   public abstract Object getMethodHelper(Object receiver, Object key, Node encapsulatingNode);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean set(Object key, Object value, Object receiver, boolean isStrict, Node encapsulatingNode);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean set(long index, Object value, Object receiver, boolean isStrict, Node encapsulatingNode);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean delete(Object key, boolean isStrict);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean delete(long propIdx, boolean isStrict);

   @CompilerDirectives.TruffleBoundary
   public List<Object> ownPropertyKeys() {
      return this.getOwnPropertyKeys(true, true);
   }

   @CompilerDirectives.TruffleBoundary
   public abstract List<Object> getOwnPropertyKeys(boolean strings, boolean symbols);

   @CompilerDirectives.TruffleBoundary
   public abstract boolean hasOnlyShapeProperties();

   @CompilerDirectives.TruffleBoundary
   public abstract TruffleString getClassName();

   boolean isObject() {
      return true;
   }

   @CompilerDirectives.TruffleBoundary
   public TruffleString defaultToString() {
      JSContext context = this.getJSContext();
      if (context.getEcmaScriptVersion() <= 5) {
         return JSObjectUtil.formatToString(this.getClassName());
      } else {
         TruffleString result = null;
         if (this.isObject()) {
            Object toStringTag = this.getValue(Symbol.SYMBOL_TO_STRING_TAG);
            if (Strings.isTString(toStringTag)) {
               result = (TruffleString)toStringTag;
            }
         }

         if (result == null) {
            result = this.getBuiltinToStringTag();
         }

         return JSObjectUtil.formatToString(result);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public TruffleString getBuiltinToStringTag() {
      return this.getClassName();
   }

   @CompilerDirectives.TruffleBoundary
   public abstract TruffleString toDisplayStringImpl(boolean allowSideEffects, ToDisplayStringFormat format, int depth);

   @CompilerDirectives.TruffleBoundary
   public boolean testIntegrityLevel(boolean frozen) {
      assert this.isObject();

      boolean status = this.isExtensible();
      if (status) {
         return false;
      } else {
         for (Object key : this.ownPropertyKeys()) {
            PropertyDescriptor desc = this.getOwnProperty(key);
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
   public boolean setIntegrityLevel(boolean freeze, boolean doThrow) {
      assert this.isObject();

      if (!this.preventExtensions(doThrow)) {
         return false;
      } else {
         Iterable<Object> keys = this.ownPropertyKeys();
         if (freeze) {
            PropertyDescriptor accDesc = PropertyDescriptor.createEmpty();
            accDesc.setConfigurable(false);
            PropertyDescriptor dataDesc = PropertyDescriptor.createEmpty();
            dataDesc.setConfigurable(false);
            dataDesc.setWritable(false);

            for (Object key : keys) {
               PropertyDescriptor currentDesc = this.getOwnProperty(key);
               if (currentDesc != null) {
                  PropertyDescriptor newDesc = null;
                  if (currentDesc.isAccessorDescriptor()) {
                     newDesc = accDesc;
                  } else {
                     newDesc = dataDesc;
                  }

                  this.defineOwnProperty(key, newDesc, true);
               }
            }
         } else {
            PropertyDescriptor desc = PropertyDescriptor.createEmpty();
            desc.setConfigurable(false);

            for (Object keyx : keys) {
               this.defineOwnProperty(keyx, desc, true);
            }
         }

         return true;
      }
   }

   public static boolean isJSDynamicObject(Object object) {
      return object instanceof JSDynamicObject;
   }

   public static JSContext getJSContext(JSDynamicObject obj) {
      return getJSSharedData(obj).getContext();
   }

   public static JSClass getJSClass(JSDynamicObject obj) {
      return (JSClass)getDynamicType(obj);
   }

   public static void setJSClass(JSDynamicObject obj, JSClass jsclass) {
      DynamicObjectLibrary.getUncached().setDynamicType(obj, jsclass);
   }

   public static Object getDynamicType(JSDynamicObject obj) {
      return obj.getShape().getDynamicType();
   }

   public static boolean hasProperty(JSDynamicObject obj, Object key) {
      return Properties.containsKeyUncached(obj, key);
   }

   public static Property getProperty(JSDynamicObject obj, Object key) {
      return Properties.getPropertyUncached(obj, key);
   }

   public static Object[] getKeyArray(JSDynamicObject obj) {
      return obj.getShape().getKeyList().toArray();
   }

   public static Property[] getPropertyArray(JSDynamicObject obj) {
      return obj.getShape().getPropertyList().toArray(new Property[0]);
   }

   public static Object getOrNull(JSDynamicObject obj, Object key) {
      return Properties.getOrDefaultUncached(obj, key, null);
   }

   public static Object getOrDefault(JSDynamicObject obj, Object key, Object defaultValue) {
      return Properties.getOrDefaultUncached(obj, key, defaultValue);
   }

   public static int getIntOrDefault(JSDynamicObject obj, Object key, int defaultValue) {
      try {
         return DynamicObjectLibrary.getUncached().getIntOrDefault(obj, key, defaultValue);
      } catch (UnexpectedResultException var4) {
         throw Errors.shouldNotReachHere();
      }
   }

   public static int getObjectFlags(JSDynamicObject obj) {
      return obj.getShape().getFlags();
   }

   public static void setObjectFlags(JSDynamicObject obj, int flags) {
      DynamicObjectLibrary.getUncached().setShapeFlags(obj, flags);
   }

   public static void setPropertyFlags(JSDynamicObject obj, Object key, int flags) {
      Properties.setPropertyFlagsUncached(obj, key, flags);
   }

   public static int getPropertyFlags(JSDynamicObject obj, Object key) {
      return Properties.getPropertyUncached(obj, key).getFlags();
   }

   public static boolean updatePropertyFlags(JSDynamicObject obj, Object key, IntUnaryOperator updateFunction) {
      DynamicObjectLibrary uncached = DynamicObjectLibrary.getUncached();
      Property property = Properties.getProperty(uncached, obj, key);
      if (property == null) {
         return false;
      } else {
         int oldFlags = property.getFlags();
         int newFlags = updateFunction.applyAsInt(oldFlags);
         return oldFlags == newFlags ? false : uncached.setPropertyFlags(obj, key, newFlags);
      }
   }

   public static boolean testProperties(JSDynamicObject obj, Predicate<Property> predicate) {
      return obj.getShape().allPropertiesMatch(predicate);
   }

   public static boolean removeKey(JSDynamicObject obj, Object key) {
      return Properties.removeKeyUncached(obj, key);
   }

   public static JSSharedData getJSSharedData(JSDynamicObject obj) {
      return JSShape.getSharedData(obj.getShape());
   }

   @ExportMessage
   public static final class IsIdenticalOrUndefined {
      @Specialization
      public static TriState doHostObject(JSDynamicObject receiver, JSDynamicObject other) {
         return TriState.valueOf(receiver == other);
      }

      @Fallback
      public static TriState doOther(JSDynamicObject receiver, Object other) {
         return TriState.UNDEFINED;
      }
   }
}
