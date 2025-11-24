
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.JSConstructTypedArrayNodeGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.GetIteratorNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.GetPrototypeFromConstructorNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.IterableToListNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.array.JSGetLengthNode;
import com.oracle.truffle.js.nodes.cast.JSToIndexNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.array.TypedArrayFactory;
import com.oracle.truffle.js.runtime.builtins.JSAbstractBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import java.nio.ByteBuffer;
import java.util.NoSuchElementException;

@ImportStatic(value={JSArrayBuffer.class, JSRuntime.class, JSConfig.class, Strings.class})
public abstract class JSConstructTypedArrayNode
extends JSBuiltinNode {
    @Node.Child
    private JSToIndexNode toIndexNode;
    @Node.Child
    private GetPrototypeFromConstructorNode getPrototypeFromConstructorViewNode;
    @Node.Child
    private IntegerIndexedObjectCreateNode integerIndexObjectCreateNode;
    private final BranchProfile errorBranch = BranchProfile.create();
    private final TypedArrayFactory factory;

    public JSConstructTypedArrayNode(JSContext context, JSBuiltin builtin) {
        super(context, builtin);
        this.factory = JSConstructTypedArrayNode.findTypedArrayFactory(builtin.getName());
    }

    private static TypedArrayFactory findTypedArrayFactory(TruffleString name) {
        for (TypedArrayFactory typedArrayFactory : TypedArray.factories()) {
            if (!Strings.equals(typedArrayFactory.getName(), name)) continue;
            return typedArrayFactory;
        }
        throw new NoSuchElementException(Strings.toJavaString(name));
    }

    private long toIndex(Object target) {
        if (this.toIndexNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toIndexNode = this.insert(JSToIndexNode.create());
        }
        return this.toIndexNode.executeLong(target);
    }

    private JSDynamicObject getPrototypeFromConstructorView(JSDynamicObject newTarget) {
        if (this.getPrototypeFromConstructorViewNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.getPrototypeFromConstructorViewNode = this.insert(GetPrototypeFromConstructorNode.create(this.getContext(), null, realm -> realm.getArrayBufferViewPrototype(this.factory)));
        }
        return this.getPrototypeFromConstructorViewNode.executeWithConstructor(newTarget);
    }

    private JSDynamicObject integerIndexedObjectCreate(JSDynamicObject arrayBuffer, TypedArray typedArray, int offset, int length, JSDynamicObject proto) {
        if (this.integerIndexObjectCreateNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.integerIndexObjectCreateNode = this.insert(JSConstructTypedArrayNodeGen.IntegerIndexedObjectCreateNodeGen.create(this.getContext(), this.factory));
        }
        return this.integerIndexObjectCreateNode.execute(arrayBuffer, typedArray, offset, length, proto);
    }

    private void checkDetachedBuffer(JSDynamicObject buffer) {
        if (!this.getContext().getTypedArrayNotDetachedAssumption().isValid() && JSArrayBuffer.isDetachedBuffer(buffer)) {
            throw Errors.createTypeErrorDetachedBuffer();
        }
    }

    @Specialization(guards={"!isUndefined(newTarget)", "isJSHeapArrayBuffer(arrayBuffer)"})
    protected JSDynamicObject doArrayBuffer(JSDynamicObject newTarget, JSDynamicObject arrayBuffer, Object byteOffset0, Object length0, @Cached(value="createBinaryProfile()") @Cached.Shared(value="lengthIsUndefined") ConditionProfile lengthIsUndefined) {
        this.checkDetachedBuffer(arrayBuffer);
        byte[] byteArray = JSArrayBuffer.getByteArray(arrayBuffer);
        int arrayBufferLength = byteArray.length;
        return this.doArrayBufferImpl(arrayBuffer, byteOffset0, length0, newTarget, arrayBufferLength, false, false, lengthIsUndefined);
    }

    @Specialization(guards={"!isUndefined(newTarget)", "isJSDirectArrayBuffer(arrayBuffer)"})
    protected JSDynamicObject doDirectArrayBuffer(JSDynamicObject newTarget, JSDynamicObject arrayBuffer, Object byteOffset0, Object length0, @Cached(value="createBinaryProfile()") @Cached.Shared(value="lengthIsUndefined") ConditionProfile lengthIsUndefined) {
        this.checkDetachedBuffer(arrayBuffer);
        ByteBuffer byteBuffer = JSArrayBuffer.getDirectByteBuffer(arrayBuffer);
        int arrayBufferLength = byteBuffer.limit();
        return this.doArrayBufferImpl(arrayBuffer, byteOffset0, length0, newTarget, arrayBufferLength, true, false, lengthIsUndefined);
    }

    private JSDynamicObject doArrayBufferImpl(JSDynamicObject arrayBuffer, Object byteOffset0, Object length0, JSDynamicObject newTarget, long bufferByteLength, boolean direct, boolean isInteropBuffer, ConditionProfile lengthIsUndefinedProfile) {
        int elementSize = this.factory.getBytesPerElement();
        long byteOffset = this.toIndex(byteOffset0);
        this.rangeCheckIsMultipleOfElementSize(byteOffset % (long)elementSize == 0L, "start offset", this.factory.getName(), elementSize);
        long length = 0L;
        if (!lengthIsUndefinedProfile.profile(length0 == Undefined.instance)) {
            length = this.toIndex(length0);
            assert (length >= 0L);
        }
        this.checkDetachedBuffer(arrayBuffer);
        if (lengthIsUndefinedProfile.profile(length0 == Undefined.instance)) {
            this.rangeCheckIsMultipleOfElementSize(bufferByteLength % (long)elementSize == 0L, "buffer.byteLength", this.factory.getName(), elementSize);
            length = (bufferByteLength - byteOffset) / (long)elementSize;
            this.rangeCheck(length >= 0L, "length < 0");
        }
        this.checkLengthLimit(length, elementSize);
        int byteLength = JSConstructTypedArrayNode.toByteLength((int)length, elementSize);
        this.rangeCheck(byteOffset + (long)byteLength <= bufferByteLength, "length exceeds buffer bounds");
        assert (byteOffset <= Integer.MAX_VALUE && length <= Integer.MAX_VALUE);
        TypedArray typedArray = this.factory.createArrayType(direct, byteOffset != 0L, isInteropBuffer);
        return this.createTypedArray(arrayBuffer, typedArray, (int)byteOffset, (int)length, newTarget);
    }

    @Specialization(guards={"!isUndefined(newTarget)", "isJSSharedArrayBuffer(arrayBuffer)"})
    protected JSDynamicObject doSharedArrayBuffer(JSDynamicObject newTarget, JSDynamicObject arrayBuffer, Object byteOffset0, Object length0, @Cached(value="createBinaryProfile()") @Cached.Shared(value="lengthIsUndefined") ConditionProfile lengthIsUndefined) {
        return this.doDirectArrayBuffer(newTarget, arrayBuffer, byteOffset0, length0, lengthIsUndefined);
    }

    @Specialization(guards={"!isUndefined(newTarget)", "isJSInteropArrayBuffer(arrayBuffer)"})
    protected JSDynamicObject doInteropArrayBuffer(JSDynamicObject newTarget, JSDynamicObject arrayBuffer, Object byteOffset0, Object length0, @Cached(value="createBinaryProfile()") @Cached.Shared(value="lengthIsUndefined") ConditionProfile lengthIsUndefined, @CachedLibrary(limit="InteropLibraryLimit") InteropLibrary interop) {
        Object buffer = JSArrayBuffer.getInteropBuffer(arrayBuffer);
        long arrayBufferLength = JSConstructTypedArrayNode.getBufferSizeSafe(buffer, interop);
        return this.doArrayBufferImpl(arrayBuffer, byteOffset0, length0, newTarget, arrayBufferLength, false, true, lengthIsUndefined);
    }

    @Specialization(guards={"!isUndefined(newTarget)", "isJSArrayBufferView(arrayBufferView)"})
    protected JSDynamicObject doArrayBufferView(JSDynamicObject newTarget, JSDynamicObject arrayBufferView, Object byteOffset0, Object length0) {
        JSArrayBufferObject srcData = JSArrayBufferView.getArrayBuffer(arrayBufferView);
        this.checkDetachedBuffer(srcData);
        TypedArray sourceType = JSArrayBufferView.typedArrayGetArrayType(arrayBufferView);
        long length = sourceType.length(arrayBufferView);
        JSArrayBufferObject arrayBuffer = this.createTypedArrayBuffer(length);
        boolean elementTypeIsBig = JSRuntime.isTypedArrayBigIntFactory(this.factory);
        boolean sourceTypeIsBig = sourceType instanceof TypedArray.TypedBigIntArray;
        if (elementTypeIsBig != sourceTypeIsBig) {
            throw Errors.createTypeErrorCannotMixBigIntWithOtherTypes(this);
        }
        TypedArray typedArray = this.factory.createArrayType(this.getContext().isOptionDirectByteBuffer(), false);
        JSDynamicObject result = this.createTypedArray(arrayBuffer, typedArray, 0, (int)length, newTarget);
        assert (typedArray == JSArrayBufferView.typedArrayGetArrayType(result));
        for (long i = 0L; i < length; ++i) {
            Object element = sourceType.getElement(arrayBufferView, i);
            typedArray.setElement(result, i, element, false);
        }
        return result;
    }

    @Specialization(guards={"!isUndefined(newTarget)", "isUndefined(arg0)"})
    protected JSDynamicObject doEmpty(JSDynamicObject newTarget, JSDynamicObject arg0, Object byteOffset0, Object length0) {
        return this.createTypedArrayWithLength(0L, newTarget);
    }

    @Specialization(guards={"!isUndefined(newTarget)", "length >= 0"})
    protected JSDynamicObject doIntLength(JSDynamicObject newTarget, int length, Object byteOffset0, Object length0) {
        return this.createTypedArrayWithLength(length, newTarget);
    }

    @Specialization(guards={"!isUndefined(newTarget)", "!isJSObject(arg0)", "!isForeignObject(arg0)"}, replaces={"doIntLength"})
    protected JSDynamicObject doLength(JSDynamicObject newTarget, Object arg0, Object byteOffset0, Object length0) {
        return this.createTypedArrayWithLength(this.toIndex(arg0), newTarget);
    }

    @Specialization(guards={"!isUndefined(newTarget)", "isJSObject(object)", "!isJSAbstractBuffer(object)", "!isJSArrayBufferView(object)"})
    protected JSDynamicObject doObject(JSDynamicObject newTarget, JSDynamicObject object, Object byteOffset0, Object length0, @Cached(value="createGetIteratorMethod()") GetMethodNode getIteratorMethodNode, @Cached(value="createBinaryProfile()") ConditionProfile isIterableProfile, @Cached(value="createWriteOwn()") WriteElementNode writeOwnNode, @Cached(value="createCall()") JSFunctionCallNode iteratorCallNode, @Cached IsJSObjectNode isObjectNode, @Cached(value="create(NEXT, getContext())") PropertyGetNode getNextMethodNode, @Cached IterableToListNode iterableToListNode, @Cached(value="createGetLength()") JSGetLengthNode getLengthNode, @Cached(value="create(getContext())") ReadElementNode readNode) {
        assert (JSRuntime.isObject(object) && !JSArrayBufferView.isJSArrayBufferView(object) && !JSAbstractBuffer.isJSAbstractBuffer(object));
        JSDynamicObject proto = this.getPrototypeFromConstructorView(newTarget);
        assert (JSRuntime.isObject(proto));
        Object usingIterator = getIteratorMethodNode.executeWithTarget(object);
        if (isIterableProfile.profile(usingIterator != Undefined.instance)) {
            SimpleArrayList<Object> values = iterableToListNode.execute(GetIteratorNode.getIterator(object, usingIterator, iteratorCallNode, isObjectNode, getNextMethodNode, this));
            int len = values.size();
            JSArrayBufferObject arrayBuffer = this.createTypedArrayBuffer(len);
            TypedArray typedArray = this.factory.createArrayType(this.getContext().isOptionDirectByteBuffer(), false);
            JSDynamicObject obj = this.integerIndexedObjectCreate(arrayBuffer, typedArray, 0, len, proto);
            for (int k = 0; k < len; ++k) {
                Object kValue = values.get(k);
                writeOwnNode.executeWithTargetAndIndexAndValue((Object)obj, k, kValue);
            }
            return obj;
        }
        long len = getLengthNode.executeLong(object);
        JSArrayBufferObject arrayBuffer = this.createTypedArrayBuffer(len);
        assert (len <= Integer.MAX_VALUE);
        TypedArray typedArray = this.factory.createArrayType(this.getContext().isOptionDirectByteBuffer(), false);
        JSDynamicObject obj = this.integerIndexedObjectCreate(arrayBuffer, typedArray, 0, (int)len, proto);
        int k = 0;
        while ((long)k < len) {
            Object kValue = readNode.executeWithTargetAndIndex((Object)object, k);
            writeOwnNode.executeWithTargetAndIndexAndValue((Object)obj, k, kValue);
            ++k;
        }
        return obj;
    }

    @Specialization(guards={"!isUndefined(newTarget)", "isForeignObject(object)"}, limit="InteropLibraryLimit")
    protected JSDynamicObject doForeignObject(JSDynamicObject newTarget, Object object, Object byteOffset0, Object length0, @CachedLibrary(value="object") InteropLibrary interop, @Cached(value="createWriteOwn()") WriteElementNode writeOwnNode, @Cached ImportValueNode importValue, @Cached(value="createBinaryProfile()") ConditionProfile lengthIsUndefined) {
        long length;
        if (interop.hasBufferElements(object)) {
            JSArrayBufferObject arrayBuffer = JSArrayBuffer.createInteropArrayBuffer(this.getContext(), this.getRealm(), object);
            long bufferByteLength = JSConstructTypedArrayNode.getBufferSizeSafe(object, interop);
            return this.doArrayBufferImpl(arrayBuffer, byteOffset0, length0, newTarget, bufferByteLength, false, true, lengthIsUndefined);
        }
        boolean fromArray = interop.hasArrayElements(object);
        if (fromArray) {
            length = this.toIndex(JSInteropUtil.getArraySize(object, interop, this));
        } else if (interop.fitsInInt(object)) {
            try {
                length = this.toIndex(interop.asInt(object));
            }
            catch (UnsupportedMessageException e) {
                length = 0L;
            }
        } else {
            length = 0L;
        }
        JSDynamicObject obj = this.createTypedArrayWithLength(length, newTarget);
        if (fromArray) {
            assert (length <= Integer.MAX_VALUE);
            int k = 0;
            while ((long)k < length) {
                Object kValue = JSInteropUtil.readArrayElementOrDefault(object, k, 0, interop, importValue, this);
                writeOwnNode.executeWithTargetAndIndexAndValue((Object)obj, k, kValue);
                ++k;
            }
        }
        return obj;
    }

    private static long getBufferSizeSafe(Object object, InteropLibrary interop) {
        try {
            return interop.getBufferSize(object);
        }
        catch (UnsupportedMessageException e) {
            return 0L;
        }
    }

    GetMethodNode createGetIteratorMethod() {
        return GetMethodNode.create(this.getContext(), Symbol.SYMBOL_ITERATOR);
    }

    WriteElementNode createWriteOwn() {
        return WriteElementNode.create(this.getContext(), true, true);
    }

    JSGetLengthNode createGetLength() {
        return JSGetLengthNode.create(this.getContext());
    }

    @Specialization(guards={"isUndefined(newTarget)"})
    protected JSDynamicObject doUndefinedNewTarget(Object newTarget, Object arg0, Object byteOffset0, Object length0) {
        throw Errors.createTypeError("newTarget is not a function");
    }

    private JSArrayBufferObject createTypedArrayBuffer(long length) {
        assert (length >= 0L);
        int elementSize = this.factory.getBytesPerElement();
        this.checkLengthLimit(length, elementSize);
        int byteLength = JSConstructTypedArrayNode.toByteLength((int)length, elementSize);
        assert (length <= Integer.MAX_VALUE && byteLength >= 0 && byteLength <= Integer.MAX_VALUE);
        JSRealm realm = this.getRealm();
        if (this.getContext().isOptionDirectByteBuffer()) {
            return JSArrayBuffer.createDirectArrayBuffer(this.getContext(), realm, byteLength);
        }
        return JSArrayBuffer.createArrayBuffer(this.getContext(), realm, byteLength);
    }

    private JSDynamicObject createTypedArrayWithLength(long length, JSDynamicObject newTarget) {
        JSArrayBufferObject arrayBuffer = this.createTypedArrayBuffer(length);
        TypedArray typedArray = this.factory.createArrayType(this.getContext().isOptionDirectByteBuffer(), false);
        return this.createTypedArray(arrayBuffer, typedArray, 0, (int)length, newTarget);
    }

    private JSDynamicObject createTypedArray(JSDynamicObject arrayBuffer, TypedArray typedArray, int offset, int length, JSDynamicObject newTarget) {
        JSDynamicObject proto = this.getPrototypeFromConstructorView(newTarget);
        assert (JSRuntime.isObject(proto));
        return this.integerIndexedObjectCreate(arrayBuffer, typedArray, offset, length, proto);
    }

    private int checkLengthLimit(long length, int elementSize) {
        if (length > (long)(this.getContext().getContextOptions().getMaxTypedArrayLength() / elementSize)) {
            this.errorBranch.enter();
            throw this.throwInappropriateLengthError(length);
        }
        return (int)length;
    }

    private static int toByteLength(int length, int elementSize) {
        int byteLength = length * elementSize;
        assert (byteLength >= 0 && byteLength / elementSize == length);
        return byteLength;
    }

    @CompilerDirectives.TruffleBoundary
    private RuntimeException throwInappropriateLengthError(long length) {
        if (this.getContext().isOptionNashornCompatibilityMode()) {
            throw Errors.createRangeError("inappropriate array buffer length: " + length);
        }
        throw Errors.createRangeError("Invalid typed array length: " + length);
    }

    private boolean rangeCheck(boolean condition2, String message) {
        if (!condition2) {
            this.errorBranch.enter();
            throw Errors.createRangeError(message);
        }
        return condition2;
    }

    private boolean rangeCheckIsMultipleOfElementSize(boolean condition2, String what, TruffleString name, int bytesPerElement) {
        if (!condition2) {
            this.errorBranch.enter();
            throw JSConstructTypedArrayNode.createRangeErrorNotMultipleOfElementSize(what, name, bytesPerElement);
        }
        return condition2;
    }

    @CompilerDirectives.TruffleBoundary
    private static RuntimeException createRangeErrorNotMultipleOfElementSize(String what, TruffleString name, int bytesPerElement) {
        return Errors.createRangeError(String.format("%s of %s should be a multiple of %d", what, name, bytesPerElement));
    }

    static abstract class IntegerIndexedObjectCreateNode
    extends JavaScriptBaseNode {
        final JSContext context;
        final TypedArrayFactory factory;

        IntegerIndexedObjectCreateNode(JSContext context, TypedArrayFactory factory) {
            this.context = context;
            this.factory = factory;
        }

        abstract JSDynamicObject execute(JSDynamicObject var1, TypedArray var2, int var3, int var4, JSDynamicObject var5);

        @Specialization(guards={"isDefaultPrototype(proto)"})
        JSDynamicObject doDefaultProto(JSDynamicObject arrayBuffer, TypedArray typedArray, int offset, int length, JSDynamicObject proto) {
            JSObjectFactory objectFactory = this.context.getArrayBufferViewFactory(this.factory);
            return JSArrayBufferView.createArrayBufferView(this.context, this.getRealm(), objectFactory, arrayBuffer, typedArray, offset, length);
        }

        @Specialization(guards={"!isDefaultPrototype(proto)", "context.isMultiContext()"})
        JSDynamicObject doMultiContext(JSDynamicObject arrayBuffer, TypedArray typedArray, int offset, int length, JSDynamicObject proto) {
            JSObjectFactory objectFactory = this.context.getArrayBufferViewFactory(this.factory);
            return JSArrayBufferView.createArrayBufferViewWithProto(this.context, this.getRealm(), objectFactory, arrayBuffer, typedArray, offset, length, proto);
        }

        @Specialization(guards={"!isDefaultPrototype(proto)", "!context.isMultiContext()", "proto == cachedProto"}, limit="1")
        JSDynamicObject doCachedProto(JSDynamicObject arrayBuffer, TypedArray typedArray, int offset, int length, JSDynamicObject proto, @Cached(value="proto") JSDynamicObject cachedProto, @Cached(value="makeObjectFactory(cachedProto)") JSObjectFactory objectFactory) {
            return JSArrayBufferView.createArrayBufferView(this.context, this.getRealm(), objectFactory, arrayBuffer, typedArray, offset, length);
        }

        @Specialization(guards={"!isDefaultPrototype(proto)", "!context.isMultiContext()"}, replaces={"doCachedProto"})
        JSDynamicObject doUncachedProto(JSDynamicObject arrayBuffer, TypedArray typedArray, int offset, int length, JSDynamicObject proto) {
            return JSArrayBufferView.createArrayBufferView(this.context, this.getRealm(), this.makeObjectFactory(proto), arrayBuffer, typedArray, offset, length);
        }

        boolean isDefaultPrototype(JSDynamicObject proto) {
            return proto == this.getRealm().getArrayBufferViewPrototype(this.factory);
        }

        @CompilerDirectives.TruffleBoundary
        JSObjectFactory makeObjectFactory(JSDynamicObject prototype) {
            return JSObjectFactory.createBound(this.context, prototype, JSObjectUtil.getProtoChildShape(prototype, JSArrayBufferView.INSTANCE, this.context));
        }
    }
}

