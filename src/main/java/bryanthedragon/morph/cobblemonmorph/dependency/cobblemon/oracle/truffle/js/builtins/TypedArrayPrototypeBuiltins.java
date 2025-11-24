
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.ValueProfile;
import com.oracle.truffle.js.builtins.ArrayBufferPrototypeBuiltins;
import com.oracle.truffle.js.builtins.ArrayPrototypeBuiltins;
import com.oracle.truffle.js.builtins.ArrayPrototypeBuiltinsFactory;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.TypedArrayPrototypeBuiltinsFactory;
import com.oracle.truffle.js.nodes.access.ForEachIndexCallNode;
import com.oracle.truffle.js.nodes.array.JSGetLengthNode;
import com.oracle.truffle.js.nodes.cast.JSToBigIntNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.control.DeletePropertyNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.runtime.Boundaries;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.array.TypedArrayFactory;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.nio.ByteBuffer;
import java.nio.ByteOrder;
import java.util.EnumSet;

public final class TypedArrayPrototypeBuiltins
extends JSBuiltinsContainer.SwitchEnum<TypedArrayPrototype> {
    public static final JSBuiltinsContainer BUILTINS = new TypedArrayPrototypeBuiltins();

    protected TypedArrayPrototypeBuiltins() {
        super(JSArrayBufferView.PROTOTYPE_NAME, TypedArrayPrototype.class);
    }

    @Override
    protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, TypedArrayPrototype builtinEnum) {
        switch (builtinEnum) {
            case subarray: {
                return TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewSubarrayNodeGen.create(context, builtin, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case set: {
                return TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewSetNodeGen.create(context, builtin, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case forEach: {
                return TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewForEachNodeGen.create(context, builtin, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case find: {
                return ArrayPrototypeBuiltinsFactory.JSArrayFindNodeGen.create(context, builtin, true, false, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case findIndex: {
                return ArrayPrototypeBuiltinsFactory.JSArrayFindIndexNodeGen.create(context, builtin, true, false, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case findLast: {
                return ArrayPrototypeBuiltinsFactory.JSArrayFindNodeGen.create(context, builtin, true, true, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case findLastIndex: {
                return ArrayPrototypeBuiltinsFactory.JSArrayFindIndexNodeGen.create(context, builtin, true, true, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case fill: {
                return context.getEcmaScriptVersion() >= 9 ? TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewFillNodeGen.create(context, builtin, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(3).createArgumentNodes(context)) : ArrayPrototypeBuiltinsFactory.JSArrayFillNodeGen.create(context, builtin, true, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(3).createArgumentNodes(context));
            }
            case reduce: {
                return ArrayPrototypeBuiltinsFactory.JSArrayReduceNodeGen.create(context, builtin, true, true, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(1).varArgs().createArgumentNodes(context));
            }
            case reduceRight: {
                return ArrayPrototypeBuiltinsFactory.JSArrayReduceNodeGen.create(context, builtin, true, false, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(1).varArgs().createArgumentNodes(context));
            }
            case sort: {
                return ArrayPrototypeBuiltinsFactory.JSArraySortNodeGen.create(context, builtin, true, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case slice: {
                return ArrayPrototypeBuiltinsFactory.JSArraySliceNodeGen.create(context, builtin, true, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case every: {
                return ArrayPrototypeBuiltinsFactory.JSArrayEveryNodeGen.create(context, builtin, true, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case copyWithin: {
                return ArrayPrototypeBuiltinsFactory.JSArrayCopyWithinNodeGen.create(context, builtin, true, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(3).createArgumentNodes(context));
            }
            case indexOf: {
                return ArrayPrototypeBuiltinsFactory.JSArrayIndexOfNodeGen.create(context, builtin, true, true, TypedArrayPrototypeBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case lastIndexOf: {
                return ArrayPrototypeBuiltinsFactory.JSArrayIndexOfNodeGen.create(context, builtin, true, false, TypedArrayPrototypeBuiltins.args().withThis().varArgs().createArgumentNodes(context));
            }
            case filter: {
                return ArrayPrototypeBuiltinsFactory.JSArrayFilterNodeGen.create(context, builtin, true, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case some: {
                return ArrayPrototypeBuiltinsFactory.JSArraySomeNodeGen.create(context, builtin, true, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case map: {
                return ArrayPrototypeBuiltinsFactory.JSArrayMapNodeGen.create(context, builtin, true, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case toLocaleString: {
                return ArrayPrototypeBuiltinsFactory.JSArrayToLocaleStringNodeGen.create(context, builtin, true, TypedArrayPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case join: {
                return ArrayPrototypeBuiltinsFactory.JSArrayJoinNodeGen.create(context, builtin, true, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
            case reverse: {
                return TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewReverseNodeGen.create(context, builtin, TypedArrayPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case keys: {
                return TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewIteratorNodeGen.create(context, builtin, 1, TypedArrayPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case values: {
                return TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewIteratorNodeGen.create(context, builtin, 2, TypedArrayPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case entries: {
                return TypedArrayPrototypeBuiltinsFactory.JSArrayBufferViewIteratorNodeGen.create(context, builtin, 3, TypedArrayPrototypeBuiltins.args().withThis().createArgumentNodes(context));
            }
            case includes: {
                return ArrayPrototypeBuiltinsFactory.JSArrayIncludesNodeGen.create(context, builtin, true, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(2).createArgumentNodes(context));
            }
            case at: {
                return ArrayPrototypeBuiltinsFactory.JSArrayAtNodeGen.create(context, builtin, true, TypedArrayPrototypeBuiltins.args().withThis().fixedArgs(1).createArgumentNodes(context));
            }
        }
        return null;
    }

    public static abstract class JSArrayBufferViewIteratorNode
    extends JSBuiltinNode {
        @Node.Child
        private ArrayPrototypeBuiltins.CreateArrayIteratorNode createArrayIteratorNode;
        private final BranchProfile errorBranch = BranchProfile.create();

        public JSArrayBufferViewIteratorNode(JSContext context, JSBuiltin builtin, int iterationKind) {
            super(context, builtin);
            this.createArrayIteratorNode = ArrayPrototypeBuiltins.CreateArrayIteratorNode.create(context, iterationKind);
        }

        @Specialization(guards={"isJSArrayBufferView(thisObj)"})
        protected JSDynamicObject doObject(JSDynamicObject thisObj) {
            this.checkHasDetachedBuffer(thisObj);
            return this.createArrayIteratorNode.execute(thisObj);
        }

        @Specialization(guards={"!isJSArrayBufferView(thisObj)"})
        protected JSDynamicObject doNotObject(Object thisObj) {
            throw Errors.createTypeErrorArrayBufferViewExpected();
        }

        protected final void checkHasDetachedBuffer(JSDynamicObject view) {
            if (JSArrayBufferView.hasDetachedBuffer(view, this.getContext())) {
                this.errorBranch.enter();
                throw Errors.createTypeErrorDetachedBuffer();
            }
        }
    }

    public static abstract class JSArrayBufferViewFillNode
    extends ArrayPrototypeBuiltins.JSArrayOperationWithToInt {
        private final ConditionProfile offsetProfile1 = ConditionProfile.createBinaryProfile();
        private final ConditionProfile offsetProfile2 = ConditionProfile.createBinaryProfile();
        @Node.Child
        private JSToNumberNode toNumberNode;
        @Node.Child
        private JSToBigIntNode toBigIntNode;

        public JSArrayBufferViewFillNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, true);
        }

        @Specialization
        protected JSDynamicObject fill(Object thisObj, Object value2, Object start2, Object end2) {
            this.validateTypedArray(thisObj);
            JSDynamicObject thisJSObj = (JSDynamicObject)thisObj;
            long len = this.getLength(thisJSObj);
            Object convValue = JSArrayBufferView.isBigIntArrayBufferView(thisJSObj) ? this.toBigInt(value2) : this.toNumber(value2);
            long lStart = JSRuntime.getOffset(this.toIntegerAsLong(start2), len, this.offsetProfile1);
            long lEnd = end2 == Undefined.instance ? len : JSRuntime.getOffset(this.toIntegerAsLong(end2), len, this.offsetProfile2);
            this.checkHasDetachedBuffer(thisJSObj);
            for (long idx = lStart; idx < lEnd; ++idx) {
                this.write((Object)thisJSObj, idx, convValue);
                TruffleSafepoint.poll(this);
            }
            return thisJSObj;
        }

        protected Object toNumber(Object value2) {
            if (this.toNumberNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toNumberNode = this.insert(JSToNumberNode.create());
            }
            return this.toNumberNode.execute(value2);
        }

        protected Object toBigInt(Object value2) {
            if (this.toBigIntNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toBigIntNode = this.insert(JSToBigIntNode.create());
            }
            return this.toBigIntNode.execute(value2);
        }
    }

    public static abstract class JSArrayBufferViewReverseNode
    extends ArrayPrototypeBuiltins.JSArrayOperation {
        public JSArrayBufferViewReverseNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin, true);
        }

        @Specialization(guards={"isJSArrayBufferView(thisObj)"})
        protected JSDynamicObject reverse(JSDynamicObject thisObj, @Cached(value="create(THROW_ERROR, getContext())") DeletePropertyNode deletePropertyNode) {
            this.checkHasDetachedBuffer(thisObj);
            long len = this.getLength(thisObj);
            long middle = len / 2L;
            long lower = 0L;
            while (lower != middle) {
                boolean upperExists;
                long upper = len - lower - 1L;
                Object lowerValue = null;
                Object upperValue = null;
                boolean lowerExists = this.hasProperty((Object)thisObj, lower);
                if (lowerExists) {
                    lowerValue = this.read((Object)thisObj, lower);
                }
                if (upperExists = this.hasProperty((Object)thisObj, upper)) {
                    upperValue = this.read((Object)thisObj, upper);
                }
                if (lowerExists && upperExists) {
                    this.write((Object)thisObj, lower, upperValue);
                    this.write((Object)thisObj, upper, lowerValue);
                } else if (upperExists) {
                    this.write((Object)thisObj, lower, upperValue);
                    deletePropertyNode.executeEvaluated(thisObj, upper);
                } else if (lowerExists) {
                    deletePropertyNode.executeEvaluated(thisObj, lower);
                    this.write((Object)thisObj, upper, lowerValue);
                }
                long nextLower = this.nextElementIndex(thisObj, lower, len);
                long nextUpper = this.previousElementIndex(thisObj, upper);
                lower = len - nextLower - 1L >= nextUpper ? nextLower : len - nextUpper - 1L;
                TruffleSafepoint.poll(this);
            }
            return thisObj;
        }

        @Specialization(guards={"!isJSArrayBufferView(thisJSObj)"})
        protected Object reverse(Object thisJSObj) {
            throw Errors.createTypeErrorArrayBufferViewExpected();
        }
    }

    public static abstract class JSArrayBufferViewForEachNode
    extends ArrayPrototypeBuiltins.ArrayForEachIndexCallOperation {
        public JSArrayBufferViewForEachNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSArrayBufferView(thisJSObj)"})
        protected Object forEach(JSDynamicObject thisJSObj, Object callback, Object thisArg) {
            this.checkHasDetachedBuffer(thisJSObj);
            long length = JSArrayBufferView.typedArrayGetArrayType(thisJSObj).length(thisJSObj);
            Object callbackFn = this.checkCallbackIsFunction(callback);
            return this.forEachIndexCall(thisJSObj, callbackFn, thisArg, 0L, length, Undefined.instance);
        }

        @Specialization(guards={"!isJSArrayBufferView(thisJSObj)"})
        protected Object forEachNonTypedArray(Object thisJSObj, Object callback, Object thisArg) {
            throw Errors.createTypeErrorArrayBufferViewExpected();
        }

        @Override
        protected ForEachIndexCallNode.MaybeResultNode makeMaybeResultNode() {
            return new ForEachIndexCallNode.MaybeResultNode(){

                @Override
                public ForEachIndexCallNode.MaybeResult<Object> apply(long index, Object value2, Object callbackResult, Object currentResult) {
                    return ForEachIndexCallNode.MaybeResult.continueResult(currentResult);
                }
            };
        }

        @Override
        protected boolean shouldCheckHasProperty() {
            return false;
        }
    }

    public static abstract class JSArrayBufferViewSetNode
    extends ArrayBufferPrototypeBuiltins.JSArrayBufferOperation {
        private final BranchProfile needErrorBranch = BranchProfile.create();
        private final ConditionProfile sameBufferProf = ConditionProfile.createBinaryProfile();
        private final ValueProfile sourceArrayProf = ValueProfile.createIdentityProfile();
        private final ValueProfile targetArrayProf = ValueProfile.createIdentityProfile();
        private final JSClassProfile sourceArrayClassProfile = JSClassProfile.create();
        private final ConditionProfile srcIsJSObject = ConditionProfile.createBinaryProfile();
        private final ConditionProfile arrayIsFastArray = ConditionProfile.createBinaryProfile();
        private final ConditionProfile arrayIsArrayBufferView = ConditionProfile.createBinaryProfile();
        private final ConditionProfile isDirectProf = ConditionProfile.createBinaryProfile();
        private final BranchProfile intToIntBranch = BranchProfile.create();
        private final BranchProfile floatToFloatBranch = BranchProfile.create();
        private final BranchProfile bigIntToBigIntBranch = BranchProfile.create();
        private final BranchProfile objectToObjectBranch = BranchProfile.create();
        @Node.Child
        private JSToObjectNode toObjectNode;
        @Node.Child
        private JSGetLengthNode getLengthNode;
        @Node.Child
        private InteropLibrary interopLibrary;
        @Node.Child
        private JSToNumberNode toNumberNode;
        @Node.Child
        private JSToBigIntNode toBigIntNode;

        public JSArrayBufferViewSetNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSArrayBufferView(targetObj)"})
        protected Object set(JSDynamicObject targetObj, Object array, Object offset) {
            long targetOffsetLong = this.toInteger(offset);
            if (targetOffsetLong < 0L || targetOffsetLong > Integer.MAX_VALUE) {
                this.needErrorBranch.enter();
                throw Errors.createRangeError("out of bounds");
            }
            this.checkHasDetachedBuffer(targetObj);
            int targetOffset = (int)targetOffsetLong;
            if (this.arrayIsArrayBufferView.profile(JSArrayBufferView.isJSArrayBufferView(array))) {
                this.setArrayBufferView(targetObj, (JSDynamicObject)array, targetOffset);
            } else if (this.arrayIsFastArray.profile(JSArray.isJSFastArray(array))) {
                this.setFastArray(targetObj, (JSDynamicObject)array, targetOffset);
            } else {
                this.setOther(targetObj, array, targetOffset);
            }
            return Undefined.instance;
        }

        @Specialization(guards={"!isJSArrayBufferView(thisObj)"})
        protected Object set(Object thisObj, Object array, Object offset) {
            throw Errors.createTypeErrorIncompatibleReceiver(thisObj);
        }

        private void setFastArray(JSDynamicObject thisObj, JSDynamicObject array, int offset) {
            assert (JSArrayBufferView.isJSArrayBufferView(thisObj));
            assert (JSArray.isJSFastArray(array));
            ScriptArray sourceArray = this.sourceArrayProf.profile(JSAbstractArray.arrayGetArrayType(array));
            TypedArray targetArray = this.targetArrayProf.profile(JSArrayBufferView.typedArrayGetArrayType(thisObj));
            long sourceLen = sourceArray.length(array);
            this.rangeCheck(0L, sourceLen, offset, targetArray.length(thisObj));
            boolean isBigInt = JSArrayBufferView.isBigIntArrayBufferView(thisObj);
            int i = 0;
            int j = offset;
            while ((long)i < sourceLen) {
                Object numValue;
                Object value2 = sourceArray.getElement(array, i);
                Object object = numValue = isBigInt ? this.toBigInt(value2) : this.toNumber(value2);
                if (!JSArrayBufferView.hasDetachedBuffer(thisObj, this.getContext())) {
                    targetArray.setElement(thisObj, j, numValue, false);
                }
                TruffleSafepoint.poll(this);
                ++i;
                ++j;
            }
        }

        private void setOther(JSDynamicObject thisObj, Object array, int offset) {
            assert (JSArrayBufferView.isJSArrayBufferView(thisObj));
            assert (!JSArray.isJSFastArray(array));
            Object src = this.toObject(array);
            long srcLength = this.objectGetLength(src);
            TypedArray targetArray = this.targetArrayProf.profile(JSArrayBufferView.typedArrayGetArrayType(thisObj));
            this.rangeCheck(0L, srcLength, offset, targetArray.length(thisObj));
            boolean isJSObject = JSDynamicObject.isJSDynamicObject(src);
            boolean isBigInt = JSArrayBufferView.isBigIntArrayBufferView(thisObj);
            int i = 0;
            int j = offset;
            while ((long)i < srcLength) {
                Object numValue;
                Object value2 = this.srcIsJSObject.profile(isJSObject) ? JSObject.get((JSDynamicObject)src, i, this.sourceArrayClassProfile) : JSInteropUtil.readArrayElementOrDefault(src, i, Undefined.instance);
                Object object = numValue = isBigInt ? this.toBigInt(value2) : this.toNumber(value2);
                if (!JSArrayBufferView.hasDetachedBuffer(thisObj, this.getContext())) {
                    targetArray.setElement(thisObj, j, numValue, false);
                }
                TruffleSafepoint.poll(this);
                ++i;
                ++j;
            }
        }

        protected Object toNumber(Object value2) {
            if (this.toNumberNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toNumberNode = this.insert(JSToNumberNode.create());
            }
            return this.toNumberNode.execute(value2);
        }

        protected Object toBigInt(Object value2) {
            if (this.toBigIntNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toBigIntNode = this.insert(JSToBigIntNode.create());
            }
            return this.toBigIntNode.execute(value2);
        }

        private void setArrayBufferView(JSDynamicObject targetView, JSDynamicObject sourceView, int offset) {
            int srcByteIndex;
            assert (JSArrayBufferView.isJSArrayBufferView(targetView));
            assert (JSArrayBufferView.isJSArrayBufferView(sourceView));
            this.checkHasDetachedBuffer(sourceView);
            TypedArray sourceArray = this.sourceArrayProf.profile(JSArrayBufferView.typedArrayGetArrayType(sourceView));
            TypedArray targetArray = this.targetArrayProf.profile(JSArrayBufferView.typedArrayGetArrayType(targetView));
            long sourceLength = sourceArray.length(sourceView);
            this.rangeCheck(0L, sourceLength, offset, targetArray.length(targetView));
            int sourceLen = (int)sourceLength;
            JSArrayBufferObject sourceBuffer = JSArrayBufferView.getArrayBuffer(sourceView);
            JSArrayBufferObject targetBuffer = JSArrayBufferView.getArrayBuffer(targetView);
            int srcByteOffset = JSArrayBufferView.typedArrayGetOffset(sourceView);
            int targetByteOffset = JSArrayBufferView.typedArrayGetOffset(targetView);
            if (this.sameBufferProf.profile(sourceBuffer == targetBuffer)) {
                int srcByteLength = sourceLen * sourceArray.bytesPerElement();
                sourceBuffer = this.cloneArrayBuffer(sourceBuffer, sourceArray, srcByteLength, srcByteOffset);
                srcByteIndex = 0;
            } else {
                srcByteIndex = srcByteOffset;
            }
            this.copyTypedArrayElementsDistinctBuffers(targetBuffer, sourceBuffer, targetArray, sourceArray, offset, targetByteOffset, sourceLen, srcByteIndex);
        }

        private void copyTypedArrayElementsDistinctBuffers(JSDynamicObject targetBuffer, JSDynamicObject sourceBuffer, TypedArray targetType, TypedArray sourceType, int targetOffset, int targetByteOffset, int sourceLength, int sourceByteIndex) {
            InteropLibrary interop;
            int targetElementSize = targetType.bytesPerElement();
            int sourceElementSize = sourceType.bytesPerElement();
            int targetByteIndex = targetByteOffset + targetOffset * targetElementSize;
            InteropLibrary interopLibrary = interop = sourceType.isInterop() || targetType.isInterop() ? this.getInterop() : null;
            if (sourceType == targetType && !sourceType.isInterop()) {
                int sourceByteLength = sourceLength * sourceElementSize;
                if (this.isDirectProf.profile(targetType.isDirect())) {
                    Boundaries.byteBufferPutSlice(JSArrayBuffer.getDirectByteBuffer(targetBuffer), targetByteIndex, JSArrayBuffer.getDirectByteBuffer(sourceBuffer), sourceByteIndex, sourceByteIndex + sourceByteLength);
                } else {
                    System.arraycopy(JSArrayBuffer.getByteArray(sourceBuffer), sourceByteIndex, JSArrayBuffer.getByteArray(targetBuffer), targetByteIndex, sourceByteLength);
                }
            } else if (sourceType instanceof TypedArray.TypedIntArray && targetType instanceof TypedArray.TypedIntArray) {
                this.intToIntBranch.enter();
                for (int i = 0; i < sourceLength; ++i) {
                    int value2 = ((TypedArray.TypedIntArray)sourceType).getIntImpl(sourceBuffer, sourceByteIndex, i, interop);
                    ((TypedArray.TypedIntArray)targetType).setIntImpl(targetBuffer, targetByteOffset, i + targetOffset, value2, interop);
                    TruffleSafepoint.poll(this);
                }
            } else if (sourceType instanceof TypedArray.TypedFloatArray && targetType instanceof TypedArray.TypedFloatArray) {
                this.floatToFloatBranch.enter();
                for (int i = 0; i < sourceLength; ++i) {
                    double value3 = ((TypedArray.TypedFloatArray)sourceType).getDoubleImpl(sourceBuffer, sourceByteIndex, i, interop);
                    ((TypedArray.TypedFloatArray)targetType).setDoubleImpl(targetBuffer, targetByteOffset, i + targetOffset, value3, interop);
                    TruffleSafepoint.poll(this);
                }
            } else if (sourceType instanceof TypedArray.TypedBigIntArray && targetType instanceof TypedArray.TypedBigIntArray) {
                this.bigIntToBigIntBranch.enter();
                for (int i = 0; i < sourceLength; ++i) {
                    long value4 = ((TypedArray.TypedBigIntArray)sourceType).getLongImpl(sourceBuffer, sourceByteIndex, i, interop);
                    ((TypedArray.TypedBigIntArray)targetType).setLongImpl(targetBuffer, targetByteOffset, i + targetOffset, value4, interop);
                    TruffleSafepoint.poll(this);
                }
            } else {
                if (sourceType instanceof TypedArray.TypedBigIntArray != targetType instanceof TypedArray.TypedBigIntArray) {
                    this.needErrorBranch.enter();
                    throw Errors.createTypeErrorCannotMixBigIntWithOtherTypes(this);
                }
                this.objectToObjectBranch.enter();
                boolean littleEndian = ByteOrder.LITTLE_ENDIAN == ByteOrder.nativeOrder();
                for (int i = 0; i < sourceLength; ++i) {
                    Object value5 = sourceType.getBufferElement(sourceBuffer, sourceByteIndex + i * sourceElementSize, littleEndian, interop);
                    targetType.setBufferElement(targetBuffer, targetByteIndex + i * targetElementSize, littleEndian, value5, interop);
                    TruffleSafepoint.poll(this);
                }
            }
        }

        private JSArrayBufferObject cloneArrayBuffer(JSDynamicObject sourceBuffer, TypedArray sourceArray, int srcByteLength, int srcByteOffset) {
            JSArrayBufferObject clonedArrayBuffer;
            if (sourceArray.isInterop()) {
                InteropLibrary interop = this.getInterop();
                clonedArrayBuffer = this.cloneInteropArrayBuffer(sourceBuffer, srcByteLength, srcByteOffset, interop);
            } else if (this.isDirectProf.profile(sourceArray.isDirect())) {
                clonedArrayBuffer = JSArrayBuffer.createDirectArrayBuffer(this.getContext(), this.getRealm(), srcByteLength);
                ByteBuffer clonedBackingBuffer = JSArrayBuffer.getDirectByteBuffer(clonedArrayBuffer);
                ByteBuffer sourceBackingBuffer = JSArrayBuffer.getDirectByteBuffer(sourceBuffer);
                Boundaries.byteBufferPutSlice(clonedBackingBuffer, 0, sourceBackingBuffer, srcByteOffset, srcByteOffset + srcByteLength);
            } else {
                clonedArrayBuffer = JSArrayBuffer.createArrayBuffer(this.getContext(), this.getRealm(), srcByteLength);
                byte[] clonedBackingBuffer = JSArrayBuffer.getByteArray(clonedArrayBuffer);
                byte[] sourceBackingBuffer = JSArrayBuffer.getByteArray(sourceBuffer);
                System.arraycopy(sourceBackingBuffer, srcByteOffset, clonedBackingBuffer, 0, srcByteLength);
            }
            return clonedArrayBuffer;
        }

        private JSArrayBufferObject cloneInteropArrayBuffer(JSDynamicObject sourceBuffer, int srcByteLength, int srcByteOffset, InteropLibrary interop) {
            assert (JSArrayBuffer.isJSInteropArrayBuffer(sourceBuffer));
            boolean direct = this.getContext().isOptionDirectByteBuffer();
            TypedArray sourceType = TypedArrayFactory.Int8Array.createArrayType(false, false, true);
            TypedArray clonedType = TypedArrayFactory.Int8Array.createArrayType(direct, false);
            JSArrayBufferObject clonedArrayBuffer = direct ? JSArrayBuffer.createDirectArrayBuffer(this.getContext(), this.getRealm(), srcByteLength) : JSArrayBuffer.createArrayBuffer(this.getContext(), this.getRealm(), srcByteLength);
            for (int i = 0; i < srcByteLength; ++i) {
                int value2 = ((TypedArray.TypedIntArray)sourceType).getIntImpl(sourceBuffer, srcByteOffset, i, interop);
                ((TypedArray.TypedIntArray)clonedType).setIntImpl(clonedArrayBuffer, 0, i, value2, interop);
                TruffleSafepoint.poll(this);
            }
            return clonedArrayBuffer;
        }

        private void rangeCheck(long sourceStart, long sourceLength, long targetStart, long targetLength) {
            if (sourceStart < 0L || targetStart < 0L || sourceStart > sourceLength || targetStart > targetLength || sourceLength - sourceStart > targetLength - targetStart) {
                this.needErrorBranch.enter();
                throw Errors.createRangeError("out of bounds");
            }
        }

        private Object toObject(Object array) {
            if (this.toObjectNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toObjectNode = this.insert(JSToObjectNode.createToObject(this.getContext()));
            }
            return this.toObjectNode.execute(array);
        }

        private long objectGetLength(Object thisObject) {
            if (this.getLengthNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getLengthNode = this.insert(JSGetLengthNode.create(this.getContext()));
            }
            return this.getLengthNode.executeLong(thisObject);
        }

        private void checkHasDetachedBuffer(JSDynamicObject view) {
            if (JSArrayBufferView.hasDetachedBuffer(view, this.getContext())) {
                this.needErrorBranch.enter();
                throw Errors.createTypeErrorDetachedBuffer();
            }
        }

        private InteropLibrary getInterop() {
            InteropLibrary lib = this.interopLibrary;
            if (lib == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.interopLibrary = lib = this.insert(InteropLibrary.getFactory().createDispatched(5));
            }
            return lib;
        }
    }

    public static abstract class JSArrayBufferViewSubarrayNode
    extends ArrayBufferPrototypeBuiltins.JSArrayBufferOperation {
        @Node.Child
        private ArrayPrototypeBuiltins.ArraySpeciesConstructorNode arraySpeciesCreateNode;

        public JSArrayBufferViewSubarrayNode(JSContext context, JSBuiltin builtin) {
            super(context, builtin);
        }

        @Specialization(guards={"isJSArrayBufferView(thisObj)"})
        protected JSTypedArrayObject subarray(JSDynamicObject thisObj, int begin, int end2, @Cached(value="createIdentityProfile()") ValueProfile arrayTypeProfile) {
            TypedArray array = arrayTypeProfile.profile(JSArrayBufferView.typedArrayGetArrayType(thisObj));
            int length = (int)array.length(thisObj);
            int clampedBegin = ArrayBufferPrototypeBuiltins.JSArrayBufferSliceNode.clampIndex(begin, 0, length);
            int clampedEnd = ArrayBufferPrototypeBuiltins.JSArrayBufferSliceNode.clampIndex(end2, clampedBegin, length);
            return this.subarrayImpl(thisObj, array, clampedBegin, clampedEnd);
        }

        @Specialization(guards={"isJSArrayBufferView(thisObj)"})
        protected JSTypedArrayObject subarray(JSDynamicObject thisObj, Object begin0, Object end0, @Cached(value="createIdentityProfile()") ValueProfile arrayTypeProfile, @Cached(value="createBinaryProfile()") ConditionProfile negativeBegin, @Cached(value="createBinaryProfile()") ConditionProfile negativeEnd, @Cached(value="createBinaryProfile()") ConditionProfile smallerEnd) {
            TypedArray array = arrayTypeProfile.profile(JSArrayBufferView.typedArrayGetArrayType(thisObj));
            long len = array.length(thisObj);
            long relativeBegin = this.toInteger(begin0);
            long beginIndex = negativeBegin.profile(relativeBegin < 0L) ? Math.max(len + relativeBegin, 0L) : Math.min(relativeBegin, len);
            long relativeEnd = end0 == Undefined.instance ? len : this.toInteger(end0);
            long endIndex = negativeEnd.profile(relativeEnd < 0L) ? Math.max(len + relativeEnd, 0L) : Math.min(relativeEnd, len);
            if (smallerEnd.profile(endIndex < beginIndex)) {
                endIndex = beginIndex;
            }
            return this.subarrayImpl(thisObj, array, (int)beginIndex, (int)endIndex);
        }

        @Specialization(guards={"!isJSArrayBufferView(thisObj)"})
        protected JSTypedArrayObject subarrayGeneric(Object thisObj, Object begin0, Object end0) {
            throw Errors.createTypeErrorArrayBufferViewExpected();
        }

        protected JSTypedArrayObject subarrayImpl(JSDynamicObject thisObj, TypedArray arrayType, int begin, int end2) {
            assert (arrayType == JSArrayBufferView.typedArrayGetArrayType(thisObj));
            int offset = JSArrayBufferView.typedArrayGetOffset(thisObj);
            JSArrayBufferObject arrayBuffer = JSArrayBufferView.getArrayBuffer(thisObj);
            return this.getArraySpeciesConstructorNode().typedArraySpeciesCreate(thisObj, arrayBuffer, offset + begin * arrayType.bytesPerElement(), end2 - begin);
        }

        protected ArrayPrototypeBuiltins.ArraySpeciesConstructorNode getArraySpeciesConstructorNode() {
            if (this.arraySpeciesCreateNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.arraySpeciesCreateNode = this.insert(ArrayPrototypeBuiltins.ArraySpeciesConstructorNode.create(this.getContext(), true));
            }
            return this.arraySpeciesCreateNode;
        }
    }

    public static enum TypedArrayPrototype implements BuiltinEnum<TypedArrayPrototype>
    {
        subarray(2),
        set(1),
        forEach(1),
        find(1),
        findIndex(1),
        fill(1),
        reduce(1),
        reduceRight(1),
        sort(1),
        slice(2),
        every(1),
        copyWithin(2),
        indexOf(1),
        lastIndexOf(1),
        filter(1),
        some(1),
        map(1),
        toLocaleString(0),
        join(1),
        reverse(0),
        keys(0),
        values(0),
        entries(0),
        includes(1),
        at(1),
        findLast(1),
        findLastIndex(1);

        private final int length;

        private TypedArrayPrototype(int length) {
            this.length = length;
        }

        @Override
        public int getLength() {
            return this.length;
        }

        @Override
        public int getECMAScriptVersion() {
            if (this == includes) {
                return 7;
            }
            if (this == at) {
                return 13;
            }
            if (EnumSet.of(findLast, findLastIndex).contains(this)) {
                return 14;
            }
            return BuiltinEnum.super.getECMAScriptVersion();
        }
    }
}

