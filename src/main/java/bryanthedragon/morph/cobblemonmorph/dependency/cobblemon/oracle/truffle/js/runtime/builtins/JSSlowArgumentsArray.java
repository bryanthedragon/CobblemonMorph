package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

public final class JSSlowArgumentsArray extends JSAbstractArgumentsArray {
   static final JSSlowArgumentsArray INSTANCE = new JSSlowArgumentsArray();

   private JSSlowArgumentsArray() {
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean delete(JSDynamicObject thisObj, long index, boolean isStrict) {
      if (isSealedOrFrozen(thisObj)) {
         return true;
      } else {
         boolean isMappedArguments = isMappedArguments(thisObj);
         boolean indexDisconnected = isMappedArguments && wasIndexDisconnected(thisObj, index);
         Object oldValue = indexDisconnected ? null : this.get(thisObj, index);
         ScriptArray arrayType = arrayGetArrayType(thisObj);
         boolean wasDeleted;
         if (arrayType.hasElement(thisObj, index)) {
            arraySetArrayType(thisObj, arrayType.deleteElement(thisObj, index, false));
            wasDeleted = true;
         } else {
            wasDeleted = JSOrdinary.INSTANCE.delete(thisObj, index, isStrict);
         }

         if (wasDeleted && isMappedArguments && !indexDisconnected) {
            disconnectIndex(thisObj, index, oldValue);
         }

         return wasDeleted;
      }
   }

   private static boolean isSealedOrFrozen(JSDynamicObject thisObj) {
      ScriptArray array = arrayGetArrayType(thisObj);
      return array.isSealed() || array.isFrozen();
   }

   public static boolean isJSSlowArgumentsObject(JSDynamicObject obj) {
      return isInstance(obj, INSTANCE);
   }

   @Override
   protected JSDynamicObject makeSlowArray(JSDynamicObject thisObj) {
      assert isJSSlowArgumentsObject(thisObj);

      return thisObj;
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
   public Object getOwnHelper(JSDynamicObject store, Object thisObj, long index, Node encapsulatingNode) {
      Object indexAsString = Strings.fromLong(index);
      return JSOrdinary.INSTANCE.hasOwnProperty(store, indexAsString)
         ? JSOrdinary.INSTANCE.getOwnHelper(store, thisObj, indexAsString, encapsulatingNode)
         : super.getOwnHelper(store, thisObj, index, encapsulatingNode);
   }
}
