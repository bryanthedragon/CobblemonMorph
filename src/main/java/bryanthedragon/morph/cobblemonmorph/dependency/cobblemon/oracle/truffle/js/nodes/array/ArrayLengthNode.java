
package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.array.ArrayLengthNodeFactory;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.SparseArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBase;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;

@ImportStatic(value={ScriptArray.class})
public abstract class ArrayLengthNode
extends JavaScriptBaseNode {
    protected static final int MAX_TYPE_COUNT = 4;

    protected ArrayLengthNode() {
    }

    protected static ScriptArray getArrayType(JSDynamicObject target) {
        return JSObject.getArray(target);
    }

    public static abstract class SetArrayLengthOrDeleteNode
    extends ArrayLengthWriteNode {
        private final boolean strict;

        protected SetArrayLengthOrDeleteNode(boolean strict) {
            this.strict = strict;
        }

        @Specialization(guards={"arrayType.isInstance(getArrayType(arrayObj))"}, limit="MAX_TYPE_COUNT")
        protected void doCached(JSDynamicObject arrayObj, int length, @Cached(value="getArrayType(arrayObj)") ScriptArray arrayType, @Cached(value="createSetLengthProfile()") ScriptArray.ProfileHolder setLengthProfile) {
            assert (length >= 0);
            if (arrayType.isLengthNotWritable() || arrayType.isSealed()) {
                this.deleteAndSetLength(arrayObj, length, arrayType, setLengthProfile);
                return;
            }
            JSAbstractArray.arraySetArrayType(arrayObj, arrayType.setLength(arrayObj, length, this.strict, setLengthProfile));
        }

        @Specialization(replaces={"doCached"})
        protected void doGeneric(JSDynamicObject arrayObj, int length, @Cached(value="createBinaryProfile()") ConditionProfile mustDeleteProfile, @Cached(value="createSetLengthProfile()") ScriptArray.ProfileHolder setLengthProfile) {
            assert (length >= 0);
            ScriptArray arrayType = SetArrayLengthOrDeleteNode.getArrayType(arrayObj);
            if (mustDeleteProfile.profile(arrayType.isLengthNotWritable() || arrayType.isSealed())) {
                this.deleteAndSetLength(arrayObj, length, arrayType, setLengthProfile);
                return;
            }
            JSAbstractArray.arraySetArrayType(arrayObj, arrayType.setLength(arrayObj, length, this.strict, setLengthProfile));
        }

        private void deleteAndSetLength(JSDynamicObject arrayObj, int length, ScriptArray arrayType, ScriptArray.ProfileHolder setLengthProfile) {
            ScriptArray array = arrayType;
            for (int i = array.lengthInt(arrayObj) - 1; i >= length; --i) {
                if (!array.canDeleteElement(arrayObj, i, this.strict)) continue;
                array = array.deleteElement(arrayObj, i, this.strict);
                JSAbstractArray.arraySetArrayType(arrayObj, array);
            }
            JSAbstractArray.arraySetArrayType(arrayObj, array.setLength(arrayObj, length, this.strict, setLengthProfile));
        }
    }

    public static abstract class SetArrayLengthNode
    extends ArrayLengthWriteNode {
        private final boolean strict;

        protected SetArrayLengthNode(boolean strict) {
            this.strict = strict;
        }

        @Specialization(guards={"arrayType.isInstance(getArrayType(arrayObj))"}, limit="MAX_TYPE_COUNT")
        protected void doCached(JSDynamicObject arrayObj, int length, @Cached(value="getArrayType(arrayObj)") ScriptArray arrayType, @Cached(value="createSetLengthProfile()") ScriptArray.ProfileHolder setLengthProfile) {
            assert (length >= 0);
            if (arrayType.isSealed()) {
                this.setLengthSealed(arrayObj, length, arrayType, setLengthProfile);
                return;
            }
            JSAbstractArray.arraySetArrayType(arrayObj, arrayType.setLength(arrayObj, length, this.strict, setLengthProfile));
        }

        @Specialization(replaces={"doCached"})
        protected void doGeneric(JSDynamicObject arrayObj, int length, @Cached(value="createBinaryProfile()") ConditionProfile sealedProfile, @Cached(value="createSetLengthProfile()") ScriptArray.ProfileHolder setLengthProfile) {
            assert (length >= 0);
            ScriptArray arrayType = SetArrayLengthNode.getArrayType(arrayObj);
            if (sealedProfile.profile(arrayType.isSealed())) {
                this.setLengthSealed(arrayObj, length, arrayType, setLengthProfile);
                return;
            }
            JSAbstractArray.arraySetArrayType(arrayObj, arrayType.setLength(arrayObj, length, this.strict, setLengthProfile));
        }

        private void setLengthSealed(JSDynamicObject arrayObj, int length, ScriptArray arrayType, ScriptArray.ProfileHolder setLengthProfile) {
            long minLength = arrayType.lastElementIndex(arrayObj) + 1L;
            if ((long)length < minLength) {
                ScriptArray array = arrayType.setLength(arrayObj, minLength, this.strict, setLengthProfile);
                JSAbstractArray.arraySetArrayType(arrayObj, array);
                array.canDeleteElement(arrayObj, minLength - 1L, this.strict);
                return;
            }
            JSAbstractArray.arraySetArrayType(arrayObj, arrayType.setLength(arrayObj, length, this.strict, setLengthProfile));
        }
    }

    public static abstract class ArrayLengthWriteNode
    extends ArrayLengthNode {
        public static ArrayLengthWriteNode create(boolean strict) {
            return ArrayLengthNodeFactory.SetArrayLengthNodeGen.create(strict);
        }

        public static ArrayLengthWriteNode createSetOrDelete(boolean strict) {
            return ArrayLengthNodeFactory.SetArrayLengthOrDeleteNodeGen.create(strict);
        }

        public abstract void executeVoid(JSDynamicObject var1, int var2);
    }

    public static abstract class ArrayLengthReadNode
    extends ArrayLengthNode {
        public static ArrayLengthReadNode create() {
            return ArrayLengthNodeFactory.ArrayLengthReadNodeGen.create();
        }

        public abstract int executeInt(JSDynamicObject var1) throws UnexpectedResultException;

        public abstract Object executeObject(JSDynamicObject var1);

        public final double executeDouble(JSDynamicObject target) {
            Object result = this.executeObject(target);
            if (result instanceof Integer) {
                return ((Integer)result).intValue();
            }
            return (Double)result;
        }

        @Specialization
        protected static int doTypedArray(JSTypedArrayObject target) {
            return JSArrayBufferView.typedArrayGetLength(target);
        }

        @Specialization(guards={"arrayType.isInstance(target.getArrayType())", "isLengthAlwaysInt(arrayType)"}, limit="1")
        protected static int doIntLength(JSArrayBase target, @Cached(value="getArrayType(target)") ScriptArray arrayType) {
            return arrayType.lengthInt(target);
        }

        @Specialization(replaces={"doIntLength"}, rewriteOn={UnexpectedResultException.class})
        protected static int doUncachedIntLength(JSArrayBase target) throws UnexpectedResultException {
            long uint32Len = JSAbstractArray.arrayGetLength(target);
            assert (uint32Len == ArrayLengthReadNode.getArrayType(target).length(target));
            if (JSRuntime.longIsRepresentableAsInt(uint32Len)) {
                return (int)uint32Len;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnexpectedResultException(uint32Len);
        }

        @Specialization(replaces={"doUncachedIntLength"})
        protected static double doUncachedLongLength(JSArrayBase target) {
            long uint32Len = JSAbstractArray.arrayGetLength(target);
            assert (uint32Len == ArrayLengthReadNode.getArrayType(target).length(target));
            return uint32Len;
        }

        protected static boolean isLengthAlwaysInt(ScriptArray arrayType) {
            return !(arrayType instanceof SparseArray);
        }
    }
}

