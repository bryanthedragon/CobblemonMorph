package com.oracle.truffle.js.runtime.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;
import java.util.TreeMap;

public final class SparseArray extends DynamicArray {
   private static final SparseArray SPARSE_ARRAY = new SparseArray(0, createCache()).maybePreinitializeCache();

   private SparseArray(int integrityLevel, DynamicArray.DynamicArrayCache cache) {
      super(integrityLevel, cache);
   }

   public static SparseArray createSparseArray() {
      return SPARSE_ARRAY;
   }

   public static SparseArray makeSparseArray(JSDynamicObject object, ScriptArray fromArray) {
      assert !(fromArray instanceof SparseArray);

      TreeMap<Long, Object> arrayMap = createArrayMap();
      copyArrayToMap(object, fromArray, arrayMap);
      JSAbstractArray.arraySetLength(object, fromArray.length(object));
      JSAbstractArray.arraySetArray(object, arrayMap);
      return createSparseArray();
   }

   @CompilerDirectives.TruffleBoundary
   public static TreeMap<Long, Object> createArrayMap() {
      return new TreeMap<>();
   }

   protected static void copyArrayToMap(JSDynamicObject object, ScriptArray fromArray, Map<Long, Object> toMap) {
      for (long index = fromArray.firstElementIndex(object); index <= fromArray.lastElementIndex(object); index = fromArray.nextElementIndex(object, index)) {
         assert fromArray.hasElement(object, index);

         Boundaries.mapPut(toMap, index, fromArray.getElement(object, index));
      }
   }

   private static TreeMap<Long, Object> arrayMap(JSDynamicObject object) {
      return (TreeMap<Long, Object>)JSAbstractArray.arrayGetArray(object);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getElement(JSDynamicObject object, long index) {
      Object value = arrayMap(object).get(index);
      return value != null ? value : Undefined.instance;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getElementInBounds(JSDynamicObject object, long index) {
      Object value = arrayMap(object).get(index);

      assert value != null;

      return value;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public ScriptArray setElementImpl(JSDynamicObject object, long index, Object value, boolean strict) {
      arrayMap(object).put(index, value);
      if (index >= this.length(object)) {
         JSAbstractArray.arraySetLength(object, index + 1L);
      }

      return this;
   }

   @Override
   public long length(JSDynamicObject object) {
      return JSAbstractArray.arrayGetLength(object);
   }

   @Override
   public int lengthInt(JSDynamicObject object) {
      long len = JSAbstractArray.arrayGetLength(object);
      if (len > 2147483647L) {
         throw Errors.unsupported("array length too large");
      } else {
         return (int)len;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public SparseArray setLengthImpl(JSDynamicObject object, long len, ScriptArray.ProfileHolder profile) {
      JSAbstractArray.arraySetLength(object, len);
      arrayMap(object).tailMap(len).clear();
      return this;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public long firstElementIndex(JSDynamicObject object) {
      try {
         return arrayMap(object).firstKey();
      } catch (NoSuchElementException var3) {
         return 0L;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public long lastElementIndex(JSDynamicObject object) {
      try {
         return arrayMap(object).lastKey();
      } catch (NoSuchElementException var3) {
         return -1L;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public long nextElementIndex(JSDynamicObject object, long index) {
      Long nextIndex = arrayMap(object).higherKey(index);
      return nextIndex != null ? nextIndex : JSRuntime.MAX_SAFE_INTEGER_LONG;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public long previousElementIndex(JSDynamicObject object, long index) {
      Long nextIndex = arrayMap(object).lowerKey(index);
      return nextIndex != null ? nextIndex : -1L;
   }

   @Override
   public Object cloneArray(JSDynamicObject object) {
      return arrayMap(object).clone();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public ScriptArray deleteElementImpl(JSDynamicObject object, long index, boolean strict) {
      arrayMap(object).remove(index);
      return this;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean hasElement(JSDynamicObject object, long index) {
      return arrayMap(object).containsKey(index);
   }

   @Override
   public boolean isHolesType() {
      return true;
   }

   @Override
   public boolean hasHoles(JSDynamicObject object) {
      return true;
   }

   @Override
   public ScriptArray removeRangeImpl(JSDynamicObject object, long start, long end) {
      assert start <= end;

      assert start >= 0L;

      assert end < this.length(object);

      long delta = end - start + 1L;
      long pos = start;
      if (!this.hasElement(object, start)) {
         pos = this.nextElementIndex(object, start);
      }

      while (pos <= end) {
         this.deleteElementImpl(object, pos, false);
         pos = this.nextElementIndex(object, pos);
      }

      while (pos < this.length(object)) {
         this.setElement(object, pos - delta, this.getElement(object, pos), false);
         this.deleteElementImpl(object, pos, false);
         pos = this.nextElementIndex(object, pos);
      }

      return this;
   }

   @Override
   public ScriptArray addRangeImpl(JSDynamicObject object, long offset, int size) {
      assert offset < this.length(object);

      long pos = this.length(object);
      if (!this.hasElement(object, pos)) {
         pos = this.previousElementIndex(object, pos);
      }

      while (pos >= offset) {
         this.setElement(object, pos + size, this.getElement(object, pos), false);
         this.deleteElementImpl(object, pos, false);
         pos = this.previousElementIndex(object, pos);
      }

      return this;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public List<Object> ownPropertyKeys(JSDynamicObject object) {
      Set<Long> keySet = arrayMap(object).keySet();
      List<Object> list = new ArrayList<>(keySet.size());

      for (long index : keySet) {
         list.add(Strings.fromLong(index));
      }

      return list;
   }

   @Override
   protected DynamicArray withIntegrityLevel(int newIntegrityLevel) {
      return new SparseArray(newIntegrityLevel, this.cache);
   }
}
