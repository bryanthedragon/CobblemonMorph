package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.SparseArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBase;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;

@ImportStatic(ScriptArray.class)
public abstract class ArrayLengthNode extends JavaScriptBaseNode {
   protected static final int MAX_TYPE_COUNT = 4;

   protected ArrayLengthNode() {
   }

   protected static ScriptArray getArrayType(JSDynamicObject target) {
      return JSObject.getArray(target);
   }

   public abstract static class ArrayLengthReadNode extends ArrayLengthNode {
      public static ArrayLengthNode.ArrayLengthReadNode create() {
         return ArrayLengthNodeFactory.ArrayLengthReadNodeGen.create();
      }

      public abstract int executeInt(JSDynamicObject target) throws UnexpectedResultException;

      public abstract Object executeObject(JSDynamicObject target);

      public final double executeDouble(JSDynamicObject target) {
         Object result = this.executeObject(target);
         return result instanceof Integer ? ((Integer)result).intValue() : (Double)result;
      }

      @Specialization
      protected static int doTypedArray(JSTypedArrayObject target) {
         return JSArrayBufferView.typedArrayGetLength(target);
      }

      @Specialization(guards = {"arrayType.isInstance(target.getArrayType())", "isLengthAlwaysInt(arrayType)"}, limit = "1")
      protected static int doIntLength(JSArrayBase target, @Cached("getArrayType(target)") ScriptArray arrayType) {
         return arrayType.lengthInt(target);
      }

      @Specialization(replaces = "doIntLength", rewriteOn = UnexpectedResultException.class)
      protected static int doUncachedIntLength(JSArrayBase target) throws UnexpectedResultException {
         long uint32Len = JSAbstractArray.arrayGetLength(target);

         assert uint32Len == getArrayType(target).length(target);

         if (JSRuntime.longIsRepresentableAsInt(uint32Len)) {
            return (int)uint32Len;
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnexpectedResultException((double)uint32Len);
         }
      }

      @Specialization(replaces = "doUncachedIntLength")
      protected static double doUncachedLongLength(JSArrayBase target) {
         long uint32Len = JSAbstractArray.arrayGetLength(target);

         assert uint32Len == getArrayType(target).length(target);

         return uint32Len;
      }

      protected static boolean isLengthAlwaysInt(ScriptArray arrayType) {
         return !(arrayType instanceof SparseArray);
      }
   }

   public abstract static class ArrayLengthWriteNode extends ArrayLengthNode {
      public static ArrayLengthNode.ArrayLengthWriteNode create(boolean strict) {
         return ArrayLengthNodeFactory.SetArrayLengthNodeGen.create(strict);
      }

      public static ArrayLengthNode.ArrayLengthWriteNode createSetOrDelete(boolean strict) {
         return ArrayLengthNodeFactory.SetArrayLengthOrDeleteNodeGen.create(strict);
      }

      public abstract void executeVoid(JSDynamicObject array, int length);
   }

   public abstract static class SetArrayLengthNode extends ArrayLengthNode.ArrayLengthWriteNode {
      private final boolean strict;

      protected SetArrayLengthNode(boolean strict) {
         this.strict = strict;
      }

      @Specialization(guards = "arrayType.isInstance(getArrayType(arrayObj))", limit = "MAX_TYPE_COUNT")
      protected void doCached(
         JSDynamicObject arrayObj,
         int length,
         @Cached("getArrayType(arrayObj)") ScriptArray arrayType,
         @Cached("createSetLengthProfile()") ScriptArray.ProfileHolder setLengthProfile
      ) {
         assert length >= 0;

         if (arrayType.isSealed()) {
            this.setLengthSealed(arrayObj, length, arrayType, setLengthProfile);
         } else {
            JSAbstractArray.arraySetArrayType(arrayObj, arrayType.setLength(arrayObj, length, this.strict, setLengthProfile));
         }
      }

      @Specialization(replaces = "doCached")
      protected void doGeneric(
         JSDynamicObject arrayObj,
         int length,
         @Cached("createBinaryProfile()") ConditionProfile sealedProfile,
         @Cached("createSetLengthProfile()") ScriptArray.ProfileHolder setLengthProfile
      ) {
         assert length >= 0;

         ScriptArray arrayType = getArrayType(arrayObj);
         if (sealedProfile.profile(arrayType.isSealed())) {
            this.setLengthSealed(arrayObj, length, arrayType, setLengthProfile);
         } else {
            JSAbstractArray.arraySetArrayType(arrayObj, arrayType.setLength(arrayObj, length, this.strict, setLengthProfile));
         }
      }

      private void setLengthSealed(JSDynamicObject arrayObj, int length, ScriptArray arrayType, ScriptArray.ProfileHolder setLengthProfile) {
         long minLength = arrayType.lastElementIndex(arrayObj) + 1L;
         if (length < minLength) {
            ScriptArray array = arrayType.setLength(arrayObj, minLength, this.strict, setLengthProfile);
            JSAbstractArray.arraySetArrayType(arrayObj, array);
            array.canDeleteElement(arrayObj, minLength - 1L, this.strict);
         } else {
            JSAbstractArray.arraySetArrayType(arrayObj, arrayType.setLength(arrayObj, length, this.strict, setLengthProfile));
         }
      }
   }

   public abstract static class SetArrayLengthOrDeleteNode extends ArrayLengthNode.ArrayLengthWriteNode {
      private final boolean strict;

      protected SetArrayLengthOrDeleteNode(boolean strict) {
         this.strict = strict;
      }

      @Specialization(guards = "arrayType.isInstance(getArrayType(arrayObj))", limit = "MAX_TYPE_COUNT")
      protected void doCached(
         JSDynamicObject arrayObj,
         int length,
         @Cached("getArrayType(arrayObj)") ScriptArray arrayType,
         @Cached("createSetLengthProfile()") ScriptArray.ProfileHolder setLengthProfile
      ) {
         assert length >= 0;

         if (!arrayType.isLengthNotWritable() && !arrayType.isSealed()) {
            JSAbstractArray.arraySetArrayType(arrayObj, arrayType.setLength(arrayObj, length, this.strict, setLengthProfile));
         } else {
            this.deleteAndSetLength(arrayObj, length, arrayType, setLengthProfile);
         }
      }

      @Specialization(replaces = "doCached")
      protected void doGeneric(
         JSDynamicObject arrayObj,
         int length,
         @Cached("createBinaryProfile()") ConditionProfile mustDeleteProfile,
         @Cached("createSetLengthProfile()") ScriptArray.ProfileHolder setLengthProfile
      ) {
         assert length >= 0;

         ScriptArray arrayType = getArrayType(arrayObj);
         if (mustDeleteProfile.profile(arrayType.isLengthNotWritable() || arrayType.isSealed())) {
            this.deleteAndSetLength(arrayObj, length, arrayType, setLengthProfile);
         } else {
            JSAbstractArray.arraySetArrayType(arrayObj, arrayType.setLength(arrayObj, length, this.strict, setLengthProfile));
         }
      }

      private void deleteAndSetLength(JSDynamicObject arrayObj, int length, ScriptArray arrayType, ScriptArray.ProfileHolder setLengthProfile) {
         ScriptArray array = arrayType;

         for (int i = arrayType.lengthInt(arrayObj) - 1; i >= length; i--) {
            if (array.canDeleteElement(arrayObj, i, this.strict)) {
               array = array.deleteElement(arrayObj, i, this.strict);
               JSAbstractArray.arraySetArrayType(arrayObj, array);
            }
         }

         JSAbstractArray.arraySetArrayType(arrayObj, array.setLength(arrayObj, length, this.strict, setLengthProfile));
      }
   }
}
