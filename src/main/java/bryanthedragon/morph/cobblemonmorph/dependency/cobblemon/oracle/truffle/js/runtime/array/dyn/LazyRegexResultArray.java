package com.oracle.truffle.js.runtime.array.dyn;

import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TRegexUtil;

public final class LazyRegexResultArray extends AbstractConstantArray {
   public static final LazyRegexResultArray LAZY_REGEX_RESULT_ARRAY = new LazyRegexResultArray(0, createCache()).maybePreinitializeCache();

   public static LazyRegexResultArray createLazyRegexResultArray() {
      return LAZY_REGEX_RESULT_ARRAY;
   }

   private LazyRegexResultArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   private static Object[] getArray(JSDynamicObject object) {
      return (Object[])JSAbstractArray.arrayGetArray(object);
   }

   public static Object materializeGroup(
      JSContext context,
      TRegexUtil.TRegexMaterializeResultNode materializeResultNode,
      JSDynamicObject object,
      int index,
      DynamicObjectLibrary lazyRegexResultNode,
      DynamicObjectLibrary lazyRegexResultOriginalInputNode
   ) {
      Object[] internalArray = getArray(object);
      if (internalArray[index] == null) {
         internalArray[index] = materializeResultNode.materializeGroup(
            context,
            JSAbstractArray.arrayGetRegexResult(object, lazyRegexResultNode),
            index,
            JSAbstractArray.arrayGetRegexResultOriginalInput(object, lazyRegexResultOriginalInputNode)
         );
      }

      return internalArray[index];
   }

   public ScriptArray createWritable(
      JSContext context, TRegexUtil.TRegexMaterializeResultNode materializeResultNode, JSDynamicObject object, long index, Object value
   ) {
      for (int i = 0; i < this.lengthInt(object); i++) {
         materializeGroup(context, materializeResultNode, object, i, DynamicObjectLibrary.getUncached(), DynamicObjectLibrary.getUncached());
      }

      Object[] internalArray = getArray(object);
      AbstractObjectArray newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(
         object, internalArray.length, internalArray.length, internalArray, this.integrityLevel
      );
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   @Override
   public Object getElementInBounds(JSDynamicObject object, int index) {
      Object[] internalArray = getArray(object);
      if (internalArray[index] == null) {
         internalArray[index] = TRegexUtil.TRegexMaterializeResultNode.getUncached()
            .materializeGroup(
               JavaScriptLanguage.get(null).getJSContext(),
               JSAbstractArray.arrayGetRegexResult(object, DynamicObjectLibrary.getUncached()),
               index,
               JSAbstractArray.arrayGetRegexResultOriginalInput(object, DynamicObjectLibrary.getUncached())
            );
      }

      return internalArray[index];
   }

   @Override
   public boolean hasElement(JSDynamicObject object, long index) {
      return index >= 0L && index < this.lengthInt(object);
   }

   @Override
   public int lengthInt(JSDynamicObject object) {
      return (int)JSAbstractArray.arrayGetLength(object);
   }

   public AbstractObjectArray createWriteableObject(JSDynamicObject object, long index, Object value, ScriptArray.ProfileHolder profile) {
      Object[] array = TRegexUtil.TRegexMaterializeResultNode.getUncached()
         .materializeFull(
            JavaScriptLanguage.get(null).getJSContext(),
            JSAbstractArray.arrayGetRegexResult(object, DynamicObjectLibrary.getUncached()),
            this.lengthInt(object),
            JSAbstractArray.arrayGetRegexResultOriginalInput(object, DynamicObjectLibrary.getUncached())
         );
      AbstractObjectArray newArray = ZeroBasedObjectArray.makeZeroBasedObjectArray(object, array.length, array.length, array, this.integrityLevel);
      if (JSConfig.TraceArrayTransitions) {
         traceArrayTransition(this, newArray, index, value);
      }

      return newArray;
   }

   public AbstractObjectArray createWriteableInt(JSDynamicObject object, long index, int value, ScriptArray.ProfileHolder profile) {
      return this.createWriteableObject(object, index, value, profile);
   }

   public AbstractObjectArray createWriteableDouble(JSDynamicObject object, long index, double value, ScriptArray.ProfileHolder profile) {
      return this.createWriteableObject(object, index, value, profile);
   }

   public AbstractObjectArray createWriteableJSObject(JSDynamicObject object, long index, JSDynamicObject value, ScriptArray.ProfileHolder profile) {
      return this.createWriteableObject(object, index, value, profile);
   }

   @Override
   public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
      return this.createWriteableObject(object, index, null, ScriptArray.ProfileHolder.empty()).deleteElementImpl(object, index, strict);
   }

   @Override
   public ScriptArray setLengthImpl(JSDynamicObject object, long length, ScriptArray.ProfileHolder profile) {
      return this.createWriteableObject(object, length - 1L, null, ScriptArray.ProfileHolder.empty()).setLengthImpl(object, length, profile);
   }

   @Override
   public ScriptArray addRangeImpl(JSDynamicObject object, long offset, int size) {
      return this.createWriteableObject(object, offset, null, ScriptArray.ProfileHolder.empty()).addRangeImpl(object, offset, size);
   }

   @Override
   public ScriptArray removeRangeImpl(JSDynamicObject object, long start, long end) {
      return this.createWriteableObject(object, start, null, ScriptArray.ProfileHolder.empty()).removeRangeImpl(object, start, end);
   }

   @Override
   public Object cloneArray(JSDynamicObject object) {
      return getArray(object);
   }

   @Override
   protected DynamicArray withIntegrityLevel(int newIntegrityLevel) {
      return new LazyRegexResultArray(newIntegrityLevel, this.cache);
   }
}
