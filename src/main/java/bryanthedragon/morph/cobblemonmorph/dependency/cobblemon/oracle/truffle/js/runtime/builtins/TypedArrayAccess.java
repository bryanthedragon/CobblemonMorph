package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.nio.ByteBuffer;

public class TypedArrayAccess {
   public static final TypedArrayAccess SINGLETON = new TypedArrayAccess();

   protected TypedArrayAccess() {
   }

   public int getLength(JSDynamicObject thisObj) {
      return ((JSArrayBufferViewBase)thisObj).length;
   }

   public void setLength(JSDynamicObject thisObj, int length) {
      ((JSArrayBufferViewBase)thisObj).length = length;
   }

   public int getOffset(JSDynamicObject thisObj) {
      return ((JSArrayBufferViewBase)thisObj).offset;
   }

   public void setOffset(JSDynamicObject thisObj, int offset) {
      ((JSArrayBufferViewBase)thisObj).offset = offset;
   }

   public byte[] getByteArray(JSDynamicObject thisObj) {
      byte[] byteArray = ((JSArrayBufferObject.Heap)this.getArrayBuffer(thisObj)).getByteArray();
      if (byteArray == null) {
         CompilerDirectives.transferToInterpreter();
         throw Errors.createTypeErrorDetachedBuffer();
      } else {
         return byteArray;
      }
   }

   public ByteBuffer getByteBuffer(JSDynamicObject thisObj) {
      ByteBuffer byteBuffer = ((JSArrayBufferObject.DirectBase)this.getArrayBuffer(thisObj)).getByteBuffer();
      if (byteBuffer == null) {
         CompilerDirectives.transferToInterpreter();
         throw Errors.createTypeErrorDetachedBuffer();
      } else {
         return byteBuffer;
      }
   }

   public JSArrayBufferObject getArrayBuffer(JSDynamicObject thisObj) {
      assert JSArrayBufferView.isJSArrayBufferView(thisObj);

      return ((JSArrayBufferViewBase)thisObj).getArrayBuffer();
   }

   public TypedArray getArrayType(Object thisObj) {
      return ((JSTypedArrayObject)thisObj).arrayType;
   }

   public void setArrayType(JSDynamicObject thisObj, TypedArray arrayType) {
      ((JSTypedArrayObject)thisObj).arrayType = arrayType;
   }

   public TruffleString getTypedArrayName(JSDynamicObject thisObj) {
      return this.getArrayType(thisObj).getFactory().getName();
   }
}
