package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.interop.ArrayElementInfoNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.array.ArrayAllocationSite;
import com.oracle.truffle.js.runtime.array.DynamicArray;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantEmptyArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantObjectArray;
import com.oracle.truffle.js.runtime.interop.InteropArray;
import com.oracle.truffle.js.runtime.objects.JSCopyableObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

@ExportLibrary(InteropLibrary.class)
public final class JSArrayObject extends JSArrayBase implements JSCopyableObject {
   protected JSArrayObject(
      Shape shape, ScriptArray arrayType, Object array, ArrayAllocationSite site, long length, int usedLength, int indexOffset, int arrayOffset, int holeCount
   ) {
      super(shape, arrayType, array, site, length, usedLength, indexOffset, arrayOffset, holeCount);
   }

   public static JSArrayObject create(
      Shape shape, ScriptArray arrayType, Object array, ArrayAllocationSite site, long length, int usedLength, int indexOffset, int arrayOffset, int holeCount
   ) {
      return new JSArrayObject(shape, arrayType, array, site, length, usedLength, indexOffset, arrayOffset, holeCount);
   }

   public static JSArrayObject createEmpty(Shape shape, ScriptArray arrayType) {
      assert arrayType instanceof AbstractConstantEmptyArray || arrayType instanceof ConstantObjectArray || arrayType instanceof AbstractObjectArray;

      return new JSArrayObject(shape, arrayType, ScriptArray.EMPTY_OBJECT_ARRAY, null, 0L, 0, 0, 0, 0);
   }

   public JSAbstractArray getJSClass() {
      return (JSAbstractArray)super.getJSClass();
   }

   @Override
   protected JSObject copyWithoutProperties(Shape shape) {
      Object clonedArray = ((DynamicArray)this.getArrayType()).cloneArray(this);
      return new JSArrayObject(shape, this.getArrayType(), clonedArray, null, this.length, this.usedLength, this.indexOffset, this.arrayOffset, this.holeCount);
   }

   @ExportMessage
   public boolean hasArrayElements() {
      return true;
   }

   @ExportMessage
   public long getArraySize() {
      return JSArray.arrayGetLength(this);
   }

   @ExportMessage
   public Object readArrayElement(
      long index,
      @CachedLibrary("this") InteropLibrary self,
      @Cached(value = "create(language(self).getJSContext())", uncached = "getUncachedRead()") ReadElementNode readNode,
      @Cached ExportValueNode exportNode
   ) throws InvalidArrayIndexException, UnsupportedMessageException {
      if (index >= 0L && index < self.getArraySize(this)) {
         Object result;
         if (readNode == null) {
            result = JSObject.getOrDefault(this, index, this, Undefined.instance);
         } else {
            result = readNode.executeWithTargetAndIndexOrDefault(this, index, Undefined.instance);
         }

         return exportNode.execute(result);
      } else {
         throw InvalidArrayIndexException.create(index);
      }
   }

   @ExportMessage
   public boolean isArrayElementReadable(long index, @CachedLibrary("this") InteropLibrary thisLibrary) {
      try {
         return index >= 0L && index < thisLibrary.getArraySize(this);
      } catch (UnsupportedMessageException var5) {
         throw Errors.shouldNotReachHere(var5);
      }
   }

   @ExportMessage
   public void writeArrayElement(
      long index,
      Object value,
      @Cached.Shared("elementInfo") @Cached ArrayElementInfoNode elements,
      @Cached ImportValueNode castValueNode,
      @Cached(value = "createCachedInterop()", uncached = "getUncachedWrite()") WriteElementNode writeNode
   ) throws InvalidArrayIndexException, UnsupportedMessageException {
      elements.executeCheck(this, index, 6);
      Object importedValue = castValueNode.executeWithTarget(value);
      if (writeNode == null) {
         JSObject.set(this, index, importedValue, true, null);
      } else {
         writeNode.executeWithTargetAndIndexAndValue(this, index, importedValue);
      }
   }

   @ExportMessage
   public boolean isArrayElementModifiable(long index, @Cached.Shared("elementInfo") @Cached ArrayElementInfoNode elements) {
      return elements.executeBoolean(this, index, 2);
   }

   @ExportMessage
   public boolean isArrayElementInsertable(long index, @Cached.Shared("elementInfo") @Cached ArrayElementInfoNode elements) {
      return elements.executeBoolean(this, index, 4);
   }

   @ExportMessage
   public boolean isArrayElementRemovable(long index, @Cached.Shared("elementInfo") @Cached ArrayElementInfoNode elements) {
      return elements.executeBoolean(this, index, 8);
   }

   @ExportMessage
   public void removeArrayElement(long index, @Cached.Shared("elementInfo") @Cached ArrayElementInfoNode elements) throws UnsupportedMessageException, InvalidArrayIndexException {
      elements.executeCheck(this, index, 8);
      ScriptArray strategy = this.getArrayType();
      long len = strategy.length(this);
      strategy = strategy.removeRange(this, index, index + 1L);
      JSObject.setArray(this, strategy);
      strategy = strategy.setLength(this, len - 1L, true);
      JSObject.setArray(this, strategy);
   }

   @ExportMessage
   @ImportStatic({JSGuards.class, JSObject.class})
   abstract static class GetMembers {
      @Specialization(guards = "isJSFastArray(target)")
      public static Object fastArray(JSArrayObject target, boolean internal) {
         return InteropArray.create(JSArrayObject.filterEnumerableNames(target, JSNonProxy.ordinaryOwnPropertyKeys(target), JSObject.getJSClass(target)));
      }

      @Specialization(guards = "!isJSFastArray(target)")
      public static Object slowArray(JSArrayObject target, boolean internal) {
         return InteropArray.create(JSArrayObject.filterEnumerableNames(target, JSObject.ownPropertyKeys(target), JSObject.getJSClass(target)));
      }
   }
}
