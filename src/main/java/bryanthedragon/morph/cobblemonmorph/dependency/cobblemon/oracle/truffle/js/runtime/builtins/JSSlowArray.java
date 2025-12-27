package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.SparseArray;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.util.DefinePropertyUtil;

public final class JSSlowArray extends JSAbstractArray {
   public static final TruffleString CLASS_NAME = Strings.constant("Array");
   public static final JSSlowArray INSTANCE = new JSSlowArray();

   private JSSlowArray() {
   }

   public static boolean isJSSlowArray(Object obj) {
      return JSDynamicObject.isJSDynamicObject(obj) && isJSSlowArray((JSDynamicObject)obj);
   }

   public static boolean isJSSlowArray(JSDynamicObject obj) {
      return isInstance(obj, INSTANCE);
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return CLASS_NAME;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getOwnHelper(JSDynamicObject store, Object thisObj, long index, Node encapsulatingNode) {
      Object indexAsString = Strings.fromLong(index);
      return JSOrdinary.INSTANCE.hasOwnProperty(store, indexAsString)
         ? JSOrdinary.INSTANCE.getOwnHelper(store, thisObj, indexAsString, encapsulatingNode)
         : super.getOwnHelper(store, thisObj, index, encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean set(JSDynamicObject thisObj, long index, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      Object indexAsString = Strings.fromLong(index);
      return JSOrdinary.INSTANCE.hasOwnProperty(thisObj, indexAsString)
         ? ordinarySet(thisObj, indexAsString, value, receiver, isStrict, encapsulatingNode)
         : super.set(thisObj, index, value, receiver, isStrict, encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
      ScriptArray array = arrayAccess().getArrayType(thisObj);
      if (array.hasElement(thisObj, index)) {
         ScriptArray arrayType = arrayGetArrayType(thisObj);
         if (arrayType.canDeleteElement(thisObj, index, isStrict)) {
            arraySetArrayType(thisObj, arrayType.deleteElement(thisObj, index, isStrict));
            return true;
         } else {
            return false;
         }
      } else {
         return JSOrdinary.INSTANCE.delete(thisObj, index, isStrict);
      }
   }

   @Override
   protected JSDynamicObject makeSlowArray(JSDynamicObject thisObj) {
      assert isJSSlowArray(thisObj);

      return thisObj;
   }

   @Override
   protected boolean defineOwnPropertyIndex(JSDynamicObject thisObj, TruffleString name, PropertyDescriptor descriptor, boolean doThrow) {
      assert Strings.isTString(name);

      long index = JSRuntime.toUInt32(name);
      if (index >= this.getLength(thisObj)) {
         PropertyDescriptor desc = this.getOwnProperty(thisObj, LENGTH);
         if (!desc.getWritable()) {
            return DefinePropertyUtil.reject(doThrow, "array length is not writable");
         }
      }

      if (this.getLength(thisObj) <= index) {
         this.setLength(thisObj, index + 1L, doThrow);
      }

      ScriptArray arrayType = arrayGetArrayType(thisObj);
      if (arrayType.hasElement(thisObj, index) && !JSOrdinary.INSTANCE.hasOwnProperty(thisObj, name)) {
         JSContext context = JSObject.getJSContext(thisObj);
         boolean wasNotExtensible = !JSShape.isExtensible(thisObj.getShape());
         JSObjectUtil.putDataProperty(
            context,
            thisObj,
            name,
            this.get(thisObj, index),
            JSAttributes.fromConfigurableEnumerableWritable(!arrayType.isSealed(), true, !arrayType.isFrozen())
         );

         assert !wasNotExtensible || !JSShape.isExtensible(thisObj.getShape());

         arraySetArrayType(thisObj, arrayType.deleteElementImpl(thisObj, index, false));
      }

      boolean succeeded = jsDefineProperty(thisObj, index, descriptor, false);
      if (!succeeded) {
         JSContext context = JavaScriptLanguage.getCurrentLanguage().getJSContext();
         return DefinePropertyUtil.reject(doThrow, context.isOptionNashornCompatibilityMode() ? "cannot set property" : "Cannot redefine property");
      } else {
         return true;
      }
   }

   private static boolean jsDefineProperty(JSDynamicObject thisObj, long index, PropertyDescriptor descriptor, boolean doThrow) {
      ScriptArray internalArray = arrayAccess().getArrayType(thisObj);
      boolean copyValue = internalArray.hasElement(thisObj, index) && !descriptor.hasValue() && !descriptor.hasGet();
      boolean succeed = DefinePropertyUtil.ordinaryDefineOwnProperty(thisObj, Strings.fromLong(index), descriptor, doThrow);
      if (copyValue) {
         JSObject.set(thisObj, index, internalArray.getElement(thisObj, index), doThrow, null);
      }

      return succeed;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean setLength(JSDynamicObject thisObj, long length, boolean doThrow) {
      if (this.testIntegrityLevel(thisObj, true)) {
         throw Errors.createTypeError("cannot set length of a frozen array");
      } else {
         long oldLen = this.getLength(thisObj);
         long newLen = length;
         ScriptArray internalArray = arrayGetArrayType(thisObj);
         boolean sealed = internalArray.isSealed();
         boolean deleteSucceeded = true;
         if (length < oldLen) {
            for (long idx = oldLen - 1L; idx >= newLen; idx--) {
               if (internalArray.hasElement(thisObj, idx)) {
                  deleteSucceeded = !sealed;
               } else {
                  deleteSucceeded = JSOrdinary.INSTANCE.delete(thisObj, idx, false);
               }

               if (!deleteSucceeded) {
                  newLen = idx + 1L;
                  break;
               }
            }
         }

         if (newLen > 2147483647L && !(internalArray instanceof SparseArray)) {
            internalArray = SparseArray.makeSparseArray(thisObj, internalArray);
         }

         arraySetArrayType(thisObj, internalArray.setLength(thisObj, newLen, doThrow));
         if (!deleteSucceeded) {
            JSContext context = JavaScriptLanguage.getCurrentLanguage().getJSContext();
            return DefinePropertyUtil.reject(
               doThrow, context.isOptionNashornCompatibilityMode() ? "cannot set property: length" : "Cannot redefine property: length"
            );
         } else {
            return true;
         }
      }
   }
}
