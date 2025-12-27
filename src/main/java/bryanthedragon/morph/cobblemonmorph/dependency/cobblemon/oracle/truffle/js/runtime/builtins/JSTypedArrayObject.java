package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.interop.InteropArray;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

@ExportLibrary(InteropLibrary.class)
public final class JSTypedArrayObject extends JSArrayBufferViewBase {
   TypedArray arrayType;

   protected JSTypedArrayObject(Shape shape, TypedArray arrayType, JSArrayBufferObject arrayBuffer, int length, int offset) {
      super(shape, arrayBuffer, length, offset);
      this.arrayType = arrayType;
   }

   public TypedArrayAccess typedArrayAccess() {
      return TypedArrayAccess.SINGLETON;
   }

   public TypedArray getArrayType() {
      return this.arrayType;
   }

   public static JSTypedArrayObject create(Shape shape, TypedArray arrayType, JSArrayBufferObject arrayBuffer, int length, int offset) {
      return new JSTypedArrayObject(shape, arrayType, arrayBuffer, length, offset);
   }

   @Override
   public TruffleString getClassName() {
      return this.typedArrayAccess().getTypedArrayName(this);
   }

   @Override
   public TruffleString getBuiltinToStringTag() {
      return Strings.UC_OBJECT;
   }

   @ExportMessage
   public Object getMembers(boolean includeInternal) {
      assert JSObject.getJSClass(this) == JSArrayBufferView.INSTANCE;

      return InteropArray.create(filterEnumerableNames(this, JSNonProxy.ordinaryOwnPropertyKeys(this), JSArrayBufferView.INSTANCE));
   }

   @ExportMessage
   public boolean hasArrayElements() {
      return true;
   }

   @ExportMessage
   public long getArraySize() {
      return JSArrayBufferView.typedArrayGetLength(this);
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

   @ExportMessage.Repeat({@ExportMessage(name = "isArrayElementReadable"), @ExportMessage(name = "isArrayElementModifiable")})
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
      @Cached ImportValueNode castValueNode,
      @Cached(value = "createCachedInterop()", uncached = "getUncachedWrite()") WriteElementNode writeNode,
      @CachedLibrary("this") InteropLibrary thisLibrary
   ) throws InvalidArrayIndexException, UnsupportedMessageException {
      if (index >= 0L && index < thisLibrary.getArraySize(this)) {
         Object importedValue = castValueNode.executeWithTarget(value);
         if (writeNode == null) {
            JSObject.set(this, index, importedValue, true, null);
         } else {
            writeNode.executeWithTargetAndIndexAndValue(this, index, importedValue);
         }
      } else {
         throw InvalidArrayIndexException.create(index);
      }
   }

   @ExportMessage
   public boolean isArrayElementInsertable(long index) {
      return false;
   }
}
