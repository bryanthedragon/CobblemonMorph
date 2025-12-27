package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.ArrayAllocationSite;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import java.util.Objects;

public abstract class JSArrayBase extends JSNonProxyObject {
   int length;
   int usedLength;
   int indexOffset;
   int arrayOffset;
   int holeCount;
   Object arrayStorage;
   ScriptArray arrayStrategy;
   ArrayAllocationSite allocationSite;

   protected JSArrayBase(
      Shape shape, ScriptArray arrayType, Object array, ArrayAllocationSite site, long length, int usedLength, int indexOffset, int arrayOffset, int holeCount
   ) {
      super(shape);

      assert JSRuntime.isRepresentableAsUnsignedInt(length);

      this.length = (int)length;
      this.usedLength = usedLength;
      this.indexOffset = indexOffset;
      this.arrayOffset = arrayOffset;
      this.holeCount = holeCount;
      this.arrayStorage = Objects.requireNonNull(array);
      this.arrayStrategy = arrayType;
      this.allocationSite = site;
   }

   public final ArrayAccess arrayAccess() {
      return ArrayAccess.SINGLETON;
   }

   public final ScriptArray getArrayType() {
      return this.arrayStrategy;
   }

   public final void setArrayType(ScriptArray arrayType) {
      this.arrayStrategy = arrayType;
   }

   public final Object getArray() {
      return this.arrayStorage;
   }

   public final void setArray(Object array) {
      this.arrayStorage = Objects.requireNonNull(array);
   }
}
