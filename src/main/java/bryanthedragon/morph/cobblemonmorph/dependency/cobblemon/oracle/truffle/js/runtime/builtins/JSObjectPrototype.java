package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.ObjectPrototypeBuiltins;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import java.util.List;

public final class JSObjectPrototype extends JSNonProxy {
   public static final TruffleString CLASS_NAME = Strings.UC_OBJECT;
   public static final JSObjectPrototype INSTANCE = new JSObjectPrototype();
   public static final JSBuiltinsContainer BUILTINS = ObjectPrototypeBuiltins.BUILTINS;

   private JSObjectPrototype() {
   }

   public static JSObjectPrototypeObject create(JSContext context) {
      return create(context.makeEmptyShapeWithNullPrototype(INSTANCE));
   }

   public static boolean isJSObjectPrototype(Object obj) {
      return obj instanceof JSObjectPrototypeObject;
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return CLASS_NAME;
   }

   @Override
   public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      return this.defaultToString(obj);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean hasOwnProperty(JSDynamicObject thisObj, Object key) {
      if (super.hasOwnProperty(thisObj, key)) {
         return true;
      } else {
         long index = JSRuntime.propertyKeyToArrayIndex(key);
         return JSRuntime.isArrayIndex(index) ? JSObject.getArray(thisObj).hasElement(thisObj, index) : false;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean hasOwnProperty(JSDynamicObject thisObj, long index) {
      ScriptArray array = JSObject.getArray(thisObj);
      return array.hasElement(thisObj, index) ? true : super.hasOwnProperty(thisObj, Strings.fromLong(index));
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getOwnHelper(JSDynamicObject store, Object thisObj, long index, Node encapsulatingNode) {
      ScriptArray array = JSObject.getArray(store);
      return array.hasElement(store, index) ? array.getElement(store, index) : super.getOwnHelper(store, thisObj, Strings.fromLong(index), encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getOwnHelper(JSDynamicObject store, Object thisObj, Object key, Node encapsulatingNode) {
      long idx = JSRuntime.propertyKeyToArrayIndex(key);
      return JSRuntime.isArrayIndex(idx)
         ? this.getOwnHelper(store, thisObj, idx, encapsulatingNode)
         : super.getOwnHelper(store, thisObj, key, encapsulatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean delete(JSDynamicObject thisObj, Object key, boolean isStrict) {
      long index = JSRuntime.propertyKeyToArrayIndex(key);
      return index >= 0L ? this.delete(thisObj, index, isStrict) : super.delete(thisObj, key, isStrict);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
      ScriptArray array = JSObject.getArray(thisObj);
      if (array.hasElement(thisObj, index)) {
         if (array.canDeleteElement(thisObj, index, isStrict)) {
            JSObject.setArray(thisObj, array.deleteElement(thisObj, index, isStrict));
            return true;
         } else {
            return false;
         }
      } else {
         return JSOrdinary.INSTANCE.delete(thisObj, index, isStrict);
      }
   }

   @Override
   public List<Object> getOwnPropertyKeys(JSDynamicObject thisObj, boolean strings, boolean symbols) {
      return strings && JSObject.getArray(thisObj).length(thisObj) != 0L
         ? JSAbstractArray.ownPropertyKeysSlowArray(thisObj, strings, symbols)
         : super.getOwnPropertyKeys(thisObj, strings, symbols);
   }

   @Override
   public boolean hasOnlyShapeProperties(JSDynamicObject thisObj) {
      return JSObject.getArray(thisObj).length(thisObj) == 0L;
   }

   @Override
   public PropertyDescriptor getOwnProperty(JSDynamicObject thisObj, Object key) {
      assert JSRuntime.isPropertyKey(key);

      return JSAbstractArray.ordinaryGetOwnPropertyArray(thisObj, key);
   }

   @Override
   public boolean usesOrdinaryGetOwnProperty() {
      return false;
   }

   @Override
   public boolean setPrototypeOf(JSDynamicObject thisObj, JSDynamicObject newPrototype) {
      return JSObject.getPrototype(thisObj) == newPrototype;
   }

   @Override
   public boolean set(JSDynamicObject thisObj, long index, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      boolean result = super.set(thisObj, index, value, receiver, isStrict, encapsulatingNode);
      JSObject.getJSContext(thisObj).getArrayPrototypeNoElementsAssumption().invalidate("Array.prototype no element assumption");
      return result;
   }

   @Override
   public boolean set(JSDynamicObject thisObj, Object key, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      boolean result = super.set(thisObj, key, value, receiver, isStrict, encapsulatingNode);
      if (JSRuntime.isArrayIndex(key)) {
         JSObject.getJSContext(thisObj).getArrayPrototypeNoElementsAssumption().invalidate("Array.prototype no element assumption");
      }

      return result;
   }

   @Override
   public boolean defineOwnProperty(JSDynamicObject thisObj, Object key, PropertyDescriptor desc, boolean doThrow) {
      boolean result = super.defineOwnProperty(thisObj, key, desc, doThrow);
      if (JSRuntime.isArrayIndex(key)) {
         JSObject.getJSContext(thisObj).getArrayPrototypeNoElementsAssumption().invalidate("Array.prototype no element assumption");
      }

      return result;
   }

   public static JSObjectPrototypeObject create(Shape shape) {
      assert JSShape.getJSClassNoCast(shape) == INSTANCE;

      return new JSObjectPrototypeObject(shape);
   }
}
