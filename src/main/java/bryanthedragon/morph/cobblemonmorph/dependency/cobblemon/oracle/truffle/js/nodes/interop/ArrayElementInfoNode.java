package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBase;

@GenerateUncached
public abstract class ArrayElementInfoNode extends JavaScriptBaseNode {
   public static final int READABLE = 1;
   public static final int MODIFIABLE = 2;
   public static final int INSERTABLE = 4;
   public static final int REMOVABLE = 8;
   public static final int WRITABLE = 6;

   ArrayElementInfoNode() {
   }

   public abstract TriState execute(JSArrayBase receiver, long index, int query);

   public final boolean executeBoolean(JSArrayBase receiver, long index, int query) {
      return this.execute(receiver, index, query) == TriState.TRUE;
   }

   public final void executeCheck(JSArrayBase receiver, long index, int query) throws UnsupportedMessageException, InvalidArrayIndexException {
      TriState result = this.execute(receiver, index, query);
      if (result != TriState.TRUE) {
         if (result == TriState.UNDEFINED) {
            throw UnsupportedMessageException.create();
         } else {
            throw InvalidArrayIndexException.create(index);
         }
      }
   }

   @Specialization(guards = "arrayType.isInstance(target.getArrayType())", limit = "5")
   static TriState doCached(JSArrayBase target, long index, int query, @Cached("target.getArrayType()") ScriptArray arrayType) {
      if ((query & 14) != 0 && arrayType.isFrozen()) {
         return TriState.UNDEFINED;
      } else if (index >= 0L && index < arrayType.length(target)) {
         if ((query & 1) != 0) {
            return TriState.TRUE;
         } else if ((query & 2) != 0) {
            assert !arrayType.isFrozen();

            return TriState.TRUE;
         } else {
            return (query & 8) != 0 && !arrayType.isSealed() && !arrayType.isLengthNotWritable() ? TriState.TRUE : TriState.FALSE;
         }
      } else {
         return (query & 4) != 0 && JSRuntime.isArrayIndex(index) && !arrayType.isSealed() && !arrayType.isLengthNotWritable() ? TriState.TRUE : TriState.FALSE;
      }
   }

   @Specialization(replaces = "doCached")
   static TriState doUncached(JSArrayBase target, long index, int query) {
      return doCached(target, index, query, target.getArrayType());
   }
}
