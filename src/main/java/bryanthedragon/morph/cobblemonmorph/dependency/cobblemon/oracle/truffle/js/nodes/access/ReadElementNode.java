
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.helper.ListGetNode;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ReadNode;
import com.oracle.truffle.js.nodes.access.CachedGetPropertyNode;
import com.oracle.truffle.js.nodes.access.IsArrayNode;
import com.oracle.truffle.js.nodes.access.IsJSDynamicObjectNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import com.oracle.truffle.js.nodes.access.PropertyCacheNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.cast.ToArrayIndexNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTaggedExecutionNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.array.ArrayAllocationSite;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractConstantArray;
import com.oracle.truffle.js.runtime.array.dyn.AbstractWritableArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantEmptyArray;
import com.oracle.truffle.js.runtime.array.dyn.ConstantObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesDoubleArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesIntArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesJSObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.HolesObjectArray;
import com.oracle.truffle.js.runtime.array.dyn.LazyArray;
import com.oracle.truffle.js.runtime.array.dyn.LazyRegexResultArray;
import com.oracle.truffle.js.runtime.array.dyn.LazyRegexResultIndicesArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSBigInt;
import com.oracle.truffle.js.runtime.builtins.JSBoolean;
import com.oracle.truffle.js.runtime.builtins.JSNumber;
import com.oracle.truffle.js.runtime.builtins.JSSlowArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSSlowArray;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.builtins.JSSymbol;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import com.oracle.truffle.js.runtime.util.TRegexUtil;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.locks.Lock;

public class ReadElementNode
extends JSTargetableNode
implements ReadNode {
    @Node.Child
    private JavaScriptNode targetNode;
    @Node.Child
    private JavaScriptNode indexNode;
    @Node.Child
    private ReadElementTypeCacheNode typeCacheNode;
    protected final JSContext context;
    @CompilerDirectives.CompilationFinal
    private byte indexState;
    private static final byte INDEX_INT = 1;
    private static final byte INDEX_OBJECT = 2;

    public static ReadElementNode create(JSContext context) {
        return new ReadElementNode(null, null, context);
    }

    public static ReadElementNode create(JavaScriptNode targetNode, JavaScriptNode indexNode, JSContext context) {
        return new ReadElementNode(targetNode, indexNode, context);
    }

    protected ReadElementNode(JavaScriptNode targetNode, JavaScriptNode indexNode, JSContext context) {
        this.targetNode = targetNode;
        this.indexNode = indexNode;
        this.context = context;
    }

    @Override
    public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
        if (materializedTags.contains(JSTags.ReadElementTag.class) && this.materializationNeeded()) {
            JavaScriptNode clonedIndex;
            JavaScriptNode clonedTarget = this.targetNode == null || this.targetNode.hasSourceSection() ? this.targetNode : JSTaggedExecutionNode.createForInput(this.targetNode, this, materializedTags);
            JavaScriptNode javaScriptNode = clonedIndex = this.indexNode == null || this.indexNode.hasSourceSection() ? this.indexNode : JSTaggedExecutionNode.createForInput(this.indexNode, this, materializedTags);
            if (clonedTarget == this.targetNode && clonedIndex == this.indexNode) {
                return this;
            }
            if (clonedTarget == this.targetNode) {
                clonedTarget = ReadElementNode.cloneUninitialized(this.targetNode, materializedTags);
            }
            if (clonedIndex == this.indexNode) {
                clonedIndex = ReadElementNode.cloneUninitialized(this.indexNode, materializedTags);
            }
            ReadElementNode cloned = ReadElementNode.create(clonedTarget, clonedIndex, this.getContext());
            ReadElementNode.transferSourceSectionAndTags(this, cloned);
            return cloned;
        }
        return this;
    }

    private boolean materializationNeeded() {
        return this.targetNode != null && !this.targetNode.hasSourceSection() || this.indexNode != null && !this.indexNode.hasSourceSection();
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        if (tag == JSTags.ReadElementTag.class) {
            return true;
        }
        return super.hasTag(tag);
    }

    @Override
    public Object evaluateTarget(VirtualFrame frame) {
        return this.targetNode.execute(frame);
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object target = this.evaluateTarget(frame);
        return this.executeWithTarget(frame, target, ReadElementNode.evaluateReceiver(this.targetNode, frame, target));
    }

    @Override
    public int executeInt(VirtualFrame frame) throws UnexpectedResultException {
        Object target = this.evaluateTarget(frame);
        return this.executeWithTargetInt(frame, target, ReadElementNode.evaluateReceiver(this.targetNode, frame, target));
    }

    @Override
    public double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
        Object target = this.evaluateTarget(frame);
        return this.executeWithTargetDouble(frame, target, ReadElementNode.evaluateReceiver(this.targetNode, frame, target));
    }

    @Override
    public Object executeWithTarget(VirtualFrame frame, Object target) {
        return this.executeWithTarget(frame, target, target);
    }

    public Object executeWithTarget(VirtualFrame frame, Object target, Object receiver) {
        byte is = this.indexState;
        if (is == 0) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object index = this.getIndexNode().execute(frame);
            if (index instanceof Integer) {
                this.indexState = 1;
                return this.executeWithTargetAndIndex(target, (Integer)index, receiver);
            }
            this.indexState = (byte)2;
            return this.executeWithTargetAndIndex(target, index, receiver);
        }
        if (is == 1) {
            int index;
            try {
                index = this.getIndexNode().executeInt(frame);
            }
            catch (UnexpectedResultException e) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.indexState = (byte)2;
                return this.executeWithTargetAndIndex(target, e.getResult(), receiver);
            }
            return this.executeWithTargetAndIndex(target, index);
        }
        assert (is == 2);
        Object index = this.getIndexNode().execute(frame);
        return this.executeWithTargetAndIndex(target, index, receiver);
    }

    public int executeWithTargetInt(VirtualFrame frame, Object target, Object receiver) throws UnexpectedResultException {
        byte is = this.indexState;
        if (is == 0) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object index = this.getIndexNode().execute(frame);
            if (index instanceof Integer) {
                this.indexState = 1;
                return this.executeWithTargetAndIndexInt(target, (Integer)index, receiver);
            }
            this.indexState = (byte)2;
            return this.executeWithTargetAndIndexInt(target, index, receiver);
        }
        if (is == 1) {
            int index;
            try {
                index = this.getIndexNode().executeInt(frame);
            }
            catch (UnexpectedResultException e) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.indexState = (byte)2;
                return this.executeWithTargetAndIndexInt(target, e.getResult(), receiver);
            }
            return this.executeWithTargetAndIndexInt(target, index, receiver);
        }
        assert (is == 2);
        Object index = this.getIndexNode().execute(frame);
        return this.executeWithTargetAndIndexInt(target, index, receiver);
    }

    public double executeWithTargetDouble(VirtualFrame frame, Object target, Object receiver) throws UnexpectedResultException {
        byte is = this.indexState;
        if (is == 0) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object index = this.getIndexNode().execute(frame);
            if (index instanceof Integer) {
                this.indexState = 1;
                return this.executeWithTargetAndIndexDouble(target, (Integer)index, receiver);
            }
            this.indexState = (byte)2;
            return this.executeWithTargetAndIndexDouble(target, index, receiver);
        }
        if (is == 1) {
            int index;
            try {
                index = this.getIndexNode().executeInt(frame);
            }
            catch (UnexpectedResultException e) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.indexState = (byte)2;
                return this.executeWithTargetAndIndexDouble(target, e.getResult(), receiver);
            }
            return this.executeWithTargetAndIndexDouble(target, index, receiver);
        }
        assert (is == 2);
        Object index = this.getIndexNode().execute(frame);
        return this.executeWithTargetAndIndexDouble(target, index, receiver);
    }

    public final Object executeWithTargetAndIndex(Object target, Object index) {
        return this.executeTypeDispatch(target, index, target, (Object)Undefined.instance);
    }

    public final Object executeWithTargetAndIndex(Object target, int index) {
        return this.executeTypeDispatch(target, index, target, (Object)Undefined.instance);
    }

    public final Object executeWithTargetAndIndex(Object target, long index) {
        return this.executeTypeDispatch(target, index, target, (Object)Undefined.instance);
    }

    public final Object executeWithTargetAndIndex(Object target, Object index, Object receiver) {
        return this.executeTypeDispatch(target, index, receiver, (Object)Undefined.instance);
    }

    public final Object executeWithTargetAndIndex(Object target, int index, Object receiver) {
        return this.executeTypeDispatch(target, index, receiver, (Object)Undefined.instance);
    }

    public final int executeWithTargetAndIndexInt(Object target, Object index, Object receiver) throws UnexpectedResultException {
        return this.executeTypeDispatchInt(target, index, receiver, (Object)Undefined.instance);
    }

    public final int executeWithTargetAndIndexInt(Object target, int index, Object receiver) throws UnexpectedResultException {
        return this.executeTypeDispatchInt(target, index, receiver, (Object)Undefined.instance);
    }

    public final double executeWithTargetAndIndexDouble(Object target, Object index, Object receiver) throws UnexpectedResultException {
        return this.executeTypeDispatchDouble(target, index, receiver, (Object)Undefined.instance);
    }

    public final double executeWithTargetAndIndexDouble(Object target, int index, Object receiver) throws UnexpectedResultException {
        return this.executeTypeDispatchDouble(target, index, receiver, (Object)Undefined.instance);
    }

    public final Object executeWithTargetAndIndexOrDefault(Object target, Object index, Object defaultValue) {
        return this.executeTypeDispatch(target, index, target, defaultValue);
    }

    @ExplodeLoop
    protected final Object executeTypeDispatch(Object target, Object index, Object receiver, Object defaultValue) {
        ReadElementTypeCacheNode c = this.typeCacheNode;
        while (c != null) {
            boolean guard = c.guard(target);
            if (guard) {
                return c.executeWithTargetAndIndexUnchecked(target, index, receiver, defaultValue, this);
            }
            c = c.typeCacheNext;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        ReadElementTypeCacheNode specialization = this.specialize(target);
        return specialization.executeWithTargetAndIndexUnchecked(target, index, receiver, defaultValue, this);
    }

    @ExplodeLoop
    protected final Object executeTypeDispatch(Object target, int index, Object receiver, Object defaultValue) {
        ReadElementTypeCacheNode c = this.typeCacheNode;
        while (c != null) {
            boolean guard = c.guard(target);
            if (guard) {
                return c.executeWithTargetAndIndexUnchecked(target, index, receiver, defaultValue, this);
            }
            c = c.typeCacheNext;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        ReadElementTypeCacheNode specialization = this.specialize(target);
        return specialization.executeWithTargetAndIndexUnchecked(target, index, receiver, defaultValue, this);
    }

    @ExplodeLoop
    protected final Object executeTypeDispatch(Object target, long index, Object receiver, Object defaultValue) {
        ReadElementTypeCacheNode c = this.typeCacheNode;
        while (c != null) {
            boolean guard = c.guard(target);
            if (guard) {
                return c.executeWithTargetAndIndexUnchecked(target, index, receiver, defaultValue, this);
            }
            c = c.typeCacheNext;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        ReadElementTypeCacheNode specialization = this.specialize(target);
        return specialization.executeWithTargetAndIndexUnchecked(target, index, receiver, defaultValue, this);
    }

    @ExplodeLoop
    protected final int executeTypeDispatchInt(Object target, Object index, Object receiver, Object defaultValue) throws UnexpectedResultException {
        ReadElementTypeCacheNode c = this.typeCacheNode;
        while (c != null) {
            boolean guard = c.guard(target);
            if (guard) {
                return c.executeWithTargetAndIndexUncheckedInt(target, index, receiver, defaultValue, this);
            }
            c = c.typeCacheNext;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        ReadElementTypeCacheNode specialization = this.specialize(target);
        return specialization.executeWithTargetAndIndexUncheckedInt(target, index, receiver, defaultValue, this);
    }

    @ExplodeLoop
    protected final int executeTypeDispatchInt(Object target, int index, Object receiver, Object defaultValue) throws UnexpectedResultException {
        ReadElementTypeCacheNode c = this.typeCacheNode;
        while (c != null) {
            boolean guard = c.guard(target);
            if (guard) {
                return c.executeWithTargetAndIndexUncheckedInt(target, index, receiver, defaultValue, this);
            }
            c = c.typeCacheNext;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        ReadElementTypeCacheNode specialization = this.specialize(target);
        return specialization.executeWithTargetAndIndexUncheckedInt(target, index, receiver, defaultValue, this);
    }

    @ExplodeLoop
    protected final double executeTypeDispatchDouble(Object target, Object index, Object receiver, Object defaultValue) throws UnexpectedResultException {
        ReadElementTypeCacheNode c = this.typeCacheNode;
        while (c != null) {
            boolean guard = c.guard(target);
            if (guard) {
                return c.executeWithTargetAndIndexUncheckedDouble(target, index, receiver, defaultValue, this);
            }
            c = c.typeCacheNext;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        ReadElementTypeCacheNode specialization = this.specialize(target);
        return specialization.executeWithTargetAndIndexUncheckedDouble(target, index, receiver, defaultValue, this);
    }

    @ExplodeLoop
    protected final double executeTypeDispatchDouble(Object target, int index, Object receiver, Object defaultValue) throws UnexpectedResultException {
        ReadElementTypeCacheNode c = this.typeCacheNode;
        while (c != null) {
            boolean guard = c.guard(target);
            if (guard) {
                return c.executeWithTargetAndIndexUncheckedDouble(target, index, receiver, defaultValue, this);
            }
            c = c.typeCacheNext;
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        ReadElementTypeCacheNode specialization = this.specialize(target);
        return specialization.executeWithTargetAndIndexUncheckedDouble(target, index, receiver, defaultValue, this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private ReadElementTypeCacheNode specialize(Object target) {
        CompilerAsserts.neverPartOfCompilation();
        Lock lock = this.getLock();
        lock.lock();
        try {
            ReadElementTypeCacheNode currentHead;
            ReadElementTypeCacheNode c = currentHead = this.typeCacheNode;
            while (c != null) {
                if (c.guard(target)) {
                    ReadElementTypeCacheNode readElementTypeCacheNode = c;
                    return readElementTypeCacheNode;
                }
                c = c.typeCacheNext;
            }
            ReadElementTypeCacheNode newCacheNode = this.makeTypeCacheNode(target, currentHead);
            this.insert(newCacheNode);
            this.typeCacheNode = newCacheNode;
            if (currentHead != null && currentHead.typeCacheNext != null && currentHead.typeCacheNext.typeCacheNext != null) {
                this.reportPolymorphicSpecialize();
            }
            if (!newCacheNode.guard(target)) {
                throw Errors.shouldNotReachHere();
            }
            ReadElementTypeCacheNode readElementTypeCacheNode = newCacheNode;
            return readElementTypeCacheNode;
        }
        finally {
            lock.unlock();
        }
    }

    private ReadElementTypeCacheNode makeTypeCacheNode(Object target, ReadElementTypeCacheNode next) {
        if (JSDynamicObject.isJSDynamicObject(target)) {
            return new JSObjectReadElementTypeCacheNode(next);
        }
        if (Strings.isTString(target)) {
            return new StringReadElementTypeCacheNode(this.context, next);
        }
        if (target instanceof Boolean) {
            return new BooleanReadElementTypeCacheNode(next);
        }
        if (target instanceof Number) {
            return new NumberReadElementTypeCacheNode(target.getClass(), next);
        }
        if (target instanceof Symbol) {
            return new SymbolReadElementTypeCacheNode(next);
        }
        if (target instanceof BigInt) {
            return new BigIntReadElementTypeCacheNode(next);
        }
        if (target instanceof TruffleObject) {
            assert (JSRuntime.isForeignObject(target));
            return new ForeignObjectReadElementTypeCacheNode(target.getClass(), next);
        }
        assert (JSRuntime.isJavaPrimitive(target)) : target;
        return new JavaObjectReadElementTypeCacheNode(target.getClass(), next);
    }

    protected static ArrayReadElementCacheNode makeArrayCacheNode(JSDynamicObject target, ScriptArray array, ArrayReadElementCacheNode next) {
        if (array instanceof ConstantEmptyArray) {
            return new EmptyArrayReadElementCacheNode(array, next);
        }
        if (array instanceof ConstantObjectArray && array.isHolesType()) {
            return new ConstantObjectArrayReadElementCacheNode(array, next);
        }
        if (array instanceof LazyRegexResultArray) {
            return new LazyRegexResultArrayReadElementCacheNode(array, next);
        }
        if (array instanceof LazyRegexResultIndicesArray) {
            return new LazyRegexResultIndicesArrayReadElementCacheNode(array, next);
        }
        if (array instanceof LazyArray) {
            return new LazyArrayReadElementCacheNode(array, next);
        }
        if (array instanceof AbstractConstantArray) {
            return new ConstantArrayReadElementCacheNode(array, next);
        }
        if (array instanceof HolesIntArray) {
            return new HolesIntArrayReadElementCacheNode(array, next);
        }
        if (array instanceof HolesDoubleArray) {
            return new HolesDoubleArrayReadElementCacheNode(array, next);
        }
        if (array instanceof HolesJSObjectArray) {
            return new HolesJSObjectArrayReadElementCacheNode(array, next);
        }
        if (array instanceof HolesObjectArray) {
            return new HolesObjectArrayReadElementCacheNode(array, next);
        }
        if (array instanceof AbstractWritableArray) {
            return new WritableArrayReadElementCacheNode(array, next);
        }
        if (array instanceof TypedArray) {
            if (array instanceof TypedArray.AbstractUint32Array) {
                return new Uint32ArrayReadElementCacheNode((TypedArray)array, next);
            }
            if (array instanceof TypedArray.TypedIntArray) {
                return new TypedIntArrayReadElementCacheNode((TypedArray)array, next);
            }
            if (array instanceof TypedArray.TypedFloatArray) {
                return new TypedFloatArrayReadElementCacheNode((TypedArray)array, next);
            }
            if (array instanceof TypedArray.TypedBigIntArray) {
                return new TypedBigIntArrayReadElementCacheNode((TypedArray)array, next);
            }
            throw Errors.shouldNotReachHere();
        }
        return new ExactArrayReadElementCacheNode(array, next);
    }

    @Override
    public final JavaScriptNode getTarget() {
        return this.targetNode;
    }

    public final JavaScriptNode getElement() {
        return this.getIndexNode();
    }

    public final JSContext getContext() {
        return this.context;
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return ReadElementNode.create(ReadElementNode.cloneUninitialized(this.targetNode, materializedTags), ReadElementNode.cloneUninitialized(this.getIndexNode(), materializedTags), this.getContext());
    }

    @Override
    public String expressionToString() {
        if (this.targetNode != null && this.indexNode != null) {
            return Objects.toString(this.targetNode.expressionToString(), "(intermediate value)") + "[" + Objects.toString(this.indexNode.expressionToString(), "(intermediate value)") + "]";
        }
        return null;
    }

    public JavaScriptNode getIndexNode() {
        return this.indexNode;
    }

    static class ForeignObjectReadElementTypeCacheNode
    extends ReadElementTypeCacheNode {
        private final Class<?> targetClass;
        @Node.Child
        private InteropLibrary interop;
        @Node.Child
        private JSToPropertyKeyNode toPropertyKeyNode;
        @Node.Child
        private ImportValueNode importValueNode;
        @Node.Child
        private InteropLibrary getterInterop;
        @Node.Child
        private ForeignObjectPrototypeNode foreignObjectPrototypeNode;
        @Node.Child
        private ReadElementNode readFromPrototypeNode;
        @Node.Child
        private ToArrayIndexNode toArrayIndexNode;
        private final BranchProfile errorBranch = BranchProfile.create();
        @CompilerDirectives.CompilationFinal
        private boolean optimistic = true;

        ForeignObjectReadElementTypeCacheNode(Class<?> targetClass, ReadElementTypeCacheNode next) {
            super(next);
            assert (!JSDynamicObject.class.isAssignableFrom(targetClass)) : targetClass;
            this.targetClass = targetClass;
            this.importValueNode = ImportValueNode.create();
            this.interop = InteropLibrary.getFactory().createDispatched(5);
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
            Object truffleObject = CompilerDirectives.castExact(target, this.targetClass);
            if (this.interop.isNull(truffleObject)) {
                this.errorBranch.enter();
                throw Errors.createTypeErrorCannotGetProperty(root.getContext(), JSRuntime.safeToString(index), target, false, this);
            }
            Object foreignResult = this.getImpl(truffleObject, index, root);
            return this.importValueNode.executeWithTarget(foreignResult);
        }

        private Object getImpl(Object truffleObject, Object key, ReadElementNode root) {
            Object result;
            Object propertyKey;
            boolean hasArrayElements;
            block18: {
                hasArrayElements = this.interop.hasArrayElements(truffleObject);
                if (hasArrayElements) {
                    try {
                        Object indexOrPropertyKey = this.toArrayIndex(key);
                        if (indexOrPropertyKey instanceof Long) {
                            return this.interop.readArrayElement(truffleObject, (Long)indexOrPropertyKey);
                        }
                        propertyKey = indexOrPropertyKey;
                        assert (JSRuntime.isPropertyKey(propertyKey));
                        break block18;
                    }
                    catch (InvalidArrayIndexException | UnsupportedMessageException e) {
                        return Undefined.instance;
                    }
                }
                propertyKey = this.toPropertyKey(key);
            }
            if (root.context.getContextOptions().hasForeignHashProperties() && this.interop.hasHashEntries(truffleObject)) {
                try {
                    return this.interop.readHashValue(truffleObject, propertyKey);
                }
                catch (UnknownKeyException e) {
                }
                catch (UnsupportedMessageException e) {
                    return Undefined.instance;
                }
            }
            if (propertyKey instanceof Symbol) {
                return this.maybeReadFromPrototype(truffleObject, propertyKey, root.context);
            }
            TruffleString exportedKeyStr = (TruffleString)propertyKey;
            if (hasArrayElements && Strings.equals(JSAbstractArray.LENGTH, exportedKeyStr)) {
                return this.getSize(truffleObject);
            }
            if (root.context.isOptionNashornCompatibilityMode() && (result = this.tryGetters(truffleObject, exportedKeyStr, root.context)) != null) {
                return result;
            }
            String stringKey = Strings.toJavaString(exportedKeyStr);
            if (this.optimistic) {
                try {
                    return this.interop.readMember(truffleObject, stringKey);
                }
                catch (UnknownIdentifierException | UnsupportedMessageException e) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.optimistic = false;
                    return this.maybeReadFromPrototype(truffleObject, exportedKeyStr, root.context);
                }
            }
            if (this.interop.isMemberReadable(truffleObject, stringKey)) {
                try {
                    return this.interop.readMember(truffleObject, stringKey);
                }
                catch (UnknownIdentifierException | UnsupportedMessageException e) {
                    return Undefined.instance;
                }
            }
            return this.maybeReadFromPrototype(truffleObject, exportedKeyStr, root.context);
        }

        private Object tryGetters(Object thisObj, TruffleString key, JSContext context) {
            assert (context.isOptionNashornCompatibilityMode());
            TruffleLanguage.Env env = this.getRealm().getEnv();
            if (env.isHostObject(thisObj)) {
                Object result = this.tryInvokeGetter(thisObj, Strings.GET, key);
                if (result != null) {
                    return result;
                }
                result = this.tryInvokeGetter(thisObj, Strings.IS, key);
                if (result != null) {
                    return result;
                }
            }
            return null;
        }

        private Object tryInvokeGetter(Object thisObj, TruffleString prefix, TruffleString key) {
            TruffleString getterKey = PropertyCacheNode.getAccessorKey(prefix, key);
            if (getterKey == null) {
                return null;
            }
            if (this.getterInterop == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getterInterop = this.insert(InteropLibrary.getFactory().createDispatched(5));
            }
            if (!this.getterInterop.isMemberInvocable(thisObj, Strings.toJavaString(getterKey))) {
                return null;
            }
            try {
                return this.getterInterop.invokeMember(thisObj, Strings.toJavaString(getterKey), JSArguments.EMPTY_ARGUMENTS_ARRAY);
            }
            catch (ArityException | UnknownIdentifierException | UnsupportedMessageException | UnsupportedTypeException e) {
                return null;
            }
        }

        private Object getSize(Object truffleObject) {
            try {
                return JSRuntime.longToIntOrDouble(this.interop.getArraySize(truffleObject));
            }
            catch (UnsupportedMessageException e) {
                this.errorBranch.enter();
                throw Errors.createTypeErrorInteropException(truffleObject, e, "getArraySize", this);
            }
        }

        private Object maybeReadFromPrototype(Object truffleObject, Object key, JSContext context) {
            assert (JSRuntime.isPropertyKey(key));
            if (context.getContextOptions().hasForeignObjectPrototype() || key instanceof Symbol || JSInteropUtil.isBoxedPrimitive(truffleObject, this.interop)) {
                if (this.readFromPrototypeNode == null || this.foreignObjectPrototypeNode == null) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.readFromPrototypeNode = this.insert(ReadElementNode.create(context));
                    this.foreignObjectPrototypeNode = this.insert(ForeignObjectPrototypeNode.create());
                }
                JSDynamicObject prototype = this.foreignObjectPrototypeNode.execute(truffleObject);
                return this.readFromPrototypeNode.executeWithTargetAndIndex((Object)prototype, key);
            }
            return Undefined.instance;
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root) {
            return this.executeWithTargetAndIndexUnchecked(target, (Object)index, receiver, defaultValue, root);
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root) {
            return this.executeWithTargetAndIndexUnchecked(target, (Object)index, receiver, defaultValue, root);
        }

        @Override
        public boolean guard(Object target) {
            return CompilerDirectives.isExact(target, this.targetClass);
        }

        private Object toArrayIndex(Object maybeIndex) {
            if (this.toArrayIndexNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toArrayIndexNode = this.insert(ToArrayIndexNode.create());
            }
            return this.toArrayIndexNode.execute(maybeIndex);
        }

        private Object toPropertyKey(Object index) {
            if (this.toPropertyKeyNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toPropertyKeyNode = this.insert(JSToPropertyKeyNode.create());
            }
            return this.toPropertyKeyNode.execute(index);
        }
    }

    private static class BigIntReadElementTypeCacheNode
    extends ToPropertyKeyCachedReadElementTypeCacheNode {
        BigIntReadElementTypeCacheNode(ReadElementTypeCacheNode next) {
            super(next);
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
            BigInt bigInt = (BigInt)target;
            return JSObject.getOrDefault((JSDynamicObject)JSBigInt.create(root.context, this.getRealm(), bigInt), this.toPropertyKey(index), receiver, defaultValue, this.jsclassProfile, (Node)root);
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root) {
            BigInt bigInt = (BigInt)target;
            return JSObject.getOrDefault((JSDynamicObject)JSBigInt.create(root.context, this.getRealm(), bigInt), index, receiver, defaultValue, this.jsclassProfile, (Node)root);
        }

        @Override
        public boolean guard(Object target) {
            return target instanceof BigInt;
        }
    }

    private static class SymbolReadElementTypeCacheNode
    extends ToPropertyKeyCachedReadElementTypeCacheNode {
        SymbolReadElementTypeCacheNode(ReadElementTypeCacheNode next) {
            super(next);
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
            Symbol symbol = (Symbol)target;
            return JSObject.getOrDefault((JSDynamicObject)JSSymbol.create(root.context, this.getRealm(), symbol), this.toPropertyKey(index), receiver, defaultValue, this.jsclassProfile, (Node)root);
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root) {
            Symbol symbol = (Symbol)target;
            return JSObject.getOrDefault((JSDynamicObject)JSSymbol.create(root.context, this.getRealm(), symbol), index, receiver, defaultValue, this.jsclassProfile, (Node)root);
        }

        @Override
        public boolean guard(Object target) {
            return target instanceof Symbol;
        }
    }

    private static class BooleanReadElementTypeCacheNode
    extends ToPropertyKeyCachedReadElementTypeCacheNode {
        BooleanReadElementTypeCacheNode(ReadElementTypeCacheNode next) {
            super(next);
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
            Boolean bool = (Boolean)target;
            return JSObject.getOrDefault((JSDynamicObject)JSBoolean.create(root.context, this.getRealm(), bool), this.toPropertyKey(index), receiver, defaultValue, this.jsclassProfile, (Node)root);
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root) {
            Boolean bool = (Boolean)target;
            return JSObject.getOrDefault((JSDynamicObject)JSBoolean.create(root.context, this.getRealm(), bool), index, receiver, defaultValue, this.jsclassProfile, (Node)root);
        }

        @Override
        public boolean guard(Object target) {
            return target instanceof Boolean;
        }
    }

    private static class NumberReadElementTypeCacheNode
    extends ToPropertyKeyCachedReadElementTypeCacheNode {
        private final Class<?> numberClass;

        NumberReadElementTypeCacheNode(Class<?> stringClass, ReadElementTypeCacheNode next) {
            super(next);
            this.numberClass = stringClass;
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
            Number charSequence = (Number)CompilerDirectives.castExact(target, this.numberClass);
            return JSObject.getOrDefault((JSDynamicObject)JSNumber.create(root.context, this.getRealm(), charSequence), this.toPropertyKey(index), receiver, defaultValue, this.jsclassProfile, (Node)root);
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root) {
            Number charSequence = (Number)CompilerDirectives.castExact(target, this.numberClass);
            return JSObject.getOrDefault((JSDynamicObject)JSNumber.create(root.context, this.getRealm(), charSequence), index, receiver, defaultValue, this.jsclassProfile, (Node)root);
        }

        @Override
        public boolean guard(Object target) {
            return CompilerDirectives.isExact(target, this.numberClass);
        }
    }

    private static class StringReadElementTypeCacheNode
    extends ToPropertyKeyCachedReadElementTypeCacheNode {
        private final JSContext context;
        private final ConditionProfile arrayIndexProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile stringIndexInBounds = ConditionProfile.createBinaryProfile();
        @Node.Child
        private ToArrayIndexNode toArrayIndexNode;
        @Node.Child
        private TruffleString.SubstringByteIndexNode substringByteIndexNode;

        StringReadElementTypeCacheNode(JSContext context, ReadElementTypeCacheNode next) {
            super(next);
            this.context = context;
            this.toArrayIndexNode = ToArrayIndexNode.createNoToPropertyKey();
            this.substringByteIndexNode = TruffleString.SubstringByteIndexNode.create();
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
            int intIndex;
            TruffleString string = (TruffleString)target;
            Object convertedIndex = this.toArrayIndexNode.execute(index);
            if (this.arrayIndexProfile.profile(convertedIndex instanceof Long) && this.stringIndexInBounds.profile((intIndex = ((Long)convertedIndex).intValue()) >= 0 && intIndex < Strings.length(string))) {
                return Strings.substring(this.context, this.substringByteIndexNode, string, intIndex, 1);
            }
            return JSObject.getOrDefault((JSDynamicObject)JSString.create(root.context, this.getRealm(), string), this.toPropertyKey(index), receiver, defaultValue, this.jsclassProfile, (Node)root);
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root) {
            TruffleString string = (TruffleString)target;
            if (this.stringIndexInBounds.profile(index >= 0 && index < Strings.length(string))) {
                return Strings.substring(this.context, this.substringByteIndexNode, string, index, 1);
            }
            return JSObject.getOrDefault((JSDynamicObject)JSString.create(root.context, this.getRealm(), string), index, receiver, defaultValue, this.jsclassProfile, (Node)root);
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root) {
            TruffleString string = (TruffleString)target;
            if (this.stringIndexInBounds.profile(index >= 0L && index < (long)Strings.length(string))) {
                return Strings.substring(this.context, this.substringByteIndexNode, string, (int)index, 1);
            }
            return JSObject.getOrDefault((JSDynamicObject)JSString.create(root.context, this.getRealm(), string), index, receiver, defaultValue, this.jsclassProfile, (Node)root);
        }

        @Override
        public boolean guard(Object target) {
            return target instanceof TruffleString;
        }
    }

    private static abstract class ToPropertyKeyCachedReadElementTypeCacheNode
    extends ReadElementTypeCacheNode {
        @Node.Child
        private JSToPropertyKeyNode indexToPropertyKeyNode;
        protected final JSClassProfile jsclassProfile = JSClassProfile.create();

        ToPropertyKeyCachedReadElementTypeCacheNode(ReadElementTypeCacheNode next) {
            super(next);
        }

        protected final Object toPropertyKey(Object index) {
            if (this.indexToPropertyKeyNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.indexToPropertyKeyNode = this.insert(JSToPropertyKeyNode.create());
            }
            return this.indexToPropertyKeyNode.execute(index);
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root) {
            return this.executeWithTargetAndIndexUnchecked(target, (long)index, receiver, defaultValue, root);
        }
    }

    private static class TypedBigIntArrayReadElementCacheNode
    extends AbstractTypedArrayReadElementCacheNode {
        TypedBigIntArrayReadElementCacheNode(TypedArray arrayType, ArrayReadElementCacheNode next) {
            super(arrayType, next);
        }

        @Override
        protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
            TypedArray.TypedBigIntArray typedArray = (TypedArray.TypedBigIntArray)this.cast(array);
            if (!JSArrayBufferView.hasDetachedBuffer(target, context) && this.inBounds.profile(typedArray.hasElement(target, index))) {
                return typedArray.getBigInt(target, (int)index, this.interop);
            }
            return defaultValue;
        }
    }

    private static class TypedFloatArrayReadElementCacheNode
    extends AbstractTypedArrayReadElementCacheNode {
        TypedFloatArrayReadElementCacheNode(TypedArray arrayType, ArrayReadElementCacheNode next) {
            super(arrayType, next);
        }

        @Override
        protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
            TypedArray.TypedFloatArray typedArray = (TypedArray.TypedFloatArray)this.cast(array);
            if (!JSArrayBufferView.hasDetachedBuffer(target, context) && this.inBounds.profile(typedArray.hasElement(target, index))) {
                return typedArray.getDouble(target, (int)index, this.interop);
            }
            return defaultValue;
        }

        @Override
        protected double executeArrayGetDouble(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) throws UnexpectedResultException {
            TypedArray.TypedFloatArray typedArray = (TypedArray.TypedFloatArray)this.cast(array);
            if (!JSArrayBufferView.hasDetachedBuffer(target, context) && this.inBounds.profile(typedArray.hasElement(target, index))) {
                return typedArray.getDouble(target, (int)index, this.interop);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnexpectedResultException(defaultValue);
        }
    }

    private static class Uint32ArrayReadElementCacheNode
    extends AbstractTypedArrayReadElementCacheNode {
        private final ConditionProfile isSignedProfile = ConditionProfile.createBinaryProfile();

        Uint32ArrayReadElementCacheNode(TypedArray arrayType, ArrayReadElementCacheNode next) {
            super(arrayType, next);
        }

        @Override
        protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
            TypedArray.TypedIntArray typedArray = (TypedArray.TypedIntArray)this.cast(array);
            if (!JSArrayBufferView.hasDetachedBuffer(target, context) && this.inBounds.profile(typedArray.hasElement(target, index))) {
                int intValue = typedArray.getInt(target, (int)index, this.interop);
                if (this.isSignedProfile.profile(intValue >= 0)) {
                    return intValue;
                }
                return (double)((long)intValue & 0xFFFFFFFFL);
            }
            return defaultValue;
        }

        @Override
        protected int executeArrayGetInt(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) throws UnexpectedResultException {
            TypedArray.TypedIntArray typedArray = (TypedArray.TypedIntArray)this.cast(array);
            if (!JSArrayBufferView.hasDetachedBuffer(target, context) && this.inBounds.profile(typedArray.hasElement(target, index))) {
                int intValue = typedArray.getInt(target, (int)index, this.interop);
                if (this.isSignedProfile.profile(intValue >= 0)) {
                    return intValue;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                throw new UnexpectedResultException((long)intValue & 0xFFFFFFFFL);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnexpectedResultException(defaultValue);
        }

        @Override
        protected double executeArrayGetDouble(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) throws UnexpectedResultException {
            TypedArray.TypedIntArray typedArray = (TypedArray.TypedIntArray)this.cast(array);
            if (!JSArrayBufferView.hasDetachedBuffer(target, context) && this.inBounds.profile(typedArray.hasElement(target, index))) {
                return (long)typedArray.getInt(target, (int)index, this.interop) & 0xFFFFFFFFL;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnexpectedResultException(defaultValue);
        }
    }

    private static class TypedIntArrayReadElementCacheNode
    extends AbstractTypedArrayReadElementCacheNode {
        TypedIntArrayReadElementCacheNode(TypedArray arrayType, ArrayReadElementCacheNode next) {
            super(arrayType, next);
        }

        @Override
        protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
            TypedArray.TypedIntArray typedArray = (TypedArray.TypedIntArray)this.cast(array);
            if (!JSArrayBufferView.hasDetachedBuffer(target, context) && this.inBounds.profile(typedArray.hasElement(target, index))) {
                return typedArray.getInt(target, (int)index, this.interop);
            }
            return defaultValue;
        }

        @Override
        protected int executeArrayGetInt(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) throws UnexpectedResultException {
            TypedArray.TypedIntArray typedArray = (TypedArray.TypedIntArray)this.cast(array);
            if (!JSArrayBufferView.hasDetachedBuffer(target, context) && this.inBounds.profile(typedArray.hasElement(target, index))) {
                return typedArray.getInt(target, (int)index, this.interop);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnexpectedResultException(defaultValue);
        }

        @Override
        protected double executeArrayGetDouble(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) throws UnexpectedResultException {
            TypedArray.TypedIntArray typedArray = (TypedArray.TypedIntArray)this.cast(array);
            if (!JSArrayBufferView.hasDetachedBuffer(target, context) && this.inBounds.profile(typedArray.hasElement(target, index))) {
                return typedArray.getInt(target, (int)index, this.interop);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new UnexpectedResultException(defaultValue);
        }
    }

    private static abstract class AbstractTypedArrayReadElementCacheNode
    extends ArrayClassGuardCachedArrayReadElementCacheNode {
        @Node.Child
        protected InteropLibrary interop;

        AbstractTypedArrayReadElementCacheNode(TypedArray arrayType, ArrayReadElementCacheNode next) {
            super(arrayType, next);
            this.interop = arrayType.isInterop() ? InteropLibrary.getFactory().createDispatched(5) : InteropLibrary.getUncached();
        }
    }

    private static class HolesObjectArrayReadElementCacheNode
    extends ArrayClassGuardCachedArrayReadElementCacheNode {
        private final ConditionProfile holeProfile = ConditionProfile.createBinaryProfile();

        HolesObjectArrayReadElementCacheNode(ScriptArray arrayType, ArrayReadElementCacheNode next) {
            super(arrayType, next);
        }

        @Override
        protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
            Object value2;
            HolesObjectArray holesArray = (HolesObjectArray)this.cast(array);
            if (this.inBounds.profile(holesArray.isInBoundsFast(target, index)) && this.holeProfile.profile(!HolesObjectArray.isHoleValue(value2 = holesArray.getInBoundsFastObject(target, (int)index)))) {
                return value2;
            }
            return this.readOutOfBounds(target, index, receiver, defaultValue, context);
        }
    }

    private static class HolesJSObjectArrayReadElementCacheNode
    extends ArrayClassGuardCachedArrayReadElementCacheNode {
        private final ConditionProfile holeProfile = ConditionProfile.createBinaryProfile();

        HolesJSObjectArrayReadElementCacheNode(ScriptArray arrayType, ArrayReadElementCacheNode next) {
            super(arrayType, next);
        }

        @Override
        protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
            JSDynamicObject value2;
            HolesJSObjectArray holesArray = (HolesJSObjectArray)this.cast(array);
            if (this.inBounds.profile(holesArray.isInBoundsFast(target, index)) && this.holeProfile.profile(!HolesJSObjectArray.isHoleValue(value2 = holesArray.getInBoundsFastJSObject(target, (int)index)))) {
                return value2;
            }
            return this.readOutOfBounds(target, index, receiver, defaultValue, context);
        }
    }

    private static class HolesDoubleArrayReadElementCacheNode
    extends ArrayClassGuardCachedArrayReadElementCacheNode {
        private final ConditionProfile holeProfile = ConditionProfile.createBinaryProfile();

        HolesDoubleArrayReadElementCacheNode(ScriptArray arrayType, ArrayReadElementCacheNode next) {
            super(arrayType, next);
        }

        @Override
        protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
            double value2;
            HolesDoubleArray holesDoubleArray = (HolesDoubleArray)this.cast(array);
            if (this.inBounds.profile(holesDoubleArray.isInBoundsFast(target, index)) && this.holeProfile.profile(!HolesDoubleArray.isHoleValue(value2 = holesDoubleArray.getInBoundsFastDouble(target, (int)index)))) {
                return value2;
            }
            return this.readOutOfBounds(target, index, receiver, defaultValue, context);
        }
    }

    private static class HolesIntArrayReadElementCacheNode
    extends ArrayClassGuardCachedArrayReadElementCacheNode {
        private final ConditionProfile holeProfile = ConditionProfile.createBinaryProfile();

        HolesIntArrayReadElementCacheNode(ScriptArray arrayType, ArrayReadElementCacheNode next) {
            super(arrayType, next);
        }

        @Override
        protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
            int value2;
            HolesIntArray holesIntArray = (HolesIntArray)this.cast(array);
            if (this.inBounds.profile(holesIntArray.isInBoundsFast(target, index)) && this.holeProfile.profile(!HolesIntArray.isHoleValue(value2 = holesIntArray.getInBoundsFastInt(target, (int)index)))) {
                return value2;
            }
            return this.readOutOfBounds(target, index, receiver, defaultValue, context);
        }
    }

    private static class WritableArrayReadElementCacheNode
    extends ArrayClassGuardCachedArrayReadElementCacheNode {
        WritableArrayReadElementCacheNode(ScriptArray arrayType, ArrayReadElementCacheNode next) {
            super(arrayType, next);
        }

        @Override
        protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
            AbstractWritableArray writableArray = (AbstractWritableArray)this.cast(array);
            if (this.inBounds.profile(writableArray.isInBoundsFast(target, index))) {
                return writableArray.getInBoundsFast(target, (int)index);
            }
            return this.readOutOfBounds(target, index, receiver, defaultValue, context);
        }

        @Override
        protected int executeArrayGetInt(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) throws UnexpectedResultException {
            AbstractWritableArray writableArray = (AbstractWritableArray)this.cast(array);
            if (this.inBounds.profile(writableArray.isInBoundsFast(target, index))) {
                return writableArray.getInBoundsFastInt(target, (int)index);
            }
            return JSTypesGen.expectInteger(this.readOutOfBounds(target, index, receiver, defaultValue, context));
        }

        @Override
        protected double executeArrayGetDouble(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) throws UnexpectedResultException {
            AbstractWritableArray writableArray = (AbstractWritableArray)this.cast(array);
            if (this.inBounds.profile(writableArray.isInBoundsFast(target, index))) {
                return writableArray.getInBoundsFastDouble(target, (int)index);
            }
            return JSTypesGen.expectDouble(this.readOutOfBounds(target, index, receiver, defaultValue, context));
        }
    }

    private static class LazyArrayReadElementCacheNode
    extends ArrayClassGuardCachedArrayReadElementCacheNode {
        @Node.Child
        private ListGetNode listGetNode = ListGetNode.create();

        LazyArrayReadElementCacheNode(ScriptArray arrayType, ArrayReadElementCacheNode next) {
            super(arrayType, next);
        }

        @Override
        protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
            LazyArray lazyRegexResultArray = (LazyArray)array;
            int intIndex = (int)index;
            if (this.inBounds.profile(lazyRegexResultArray.hasElement(target, intIndex))) {
                return lazyRegexResultArray.getElementInBounds(target, intIndex, this.listGetNode);
            }
            return this.readOutOfBounds(target, index, receiver, defaultValue, context);
        }
    }

    private static class LazyRegexResultIndicesArrayReadElementCacheNode
    extends ArrayClassGuardCachedArrayReadElementCacheNode {
        @Node.Child
        TRegexUtil.TRegexResultAccessor resultAccessor;

        LazyRegexResultIndicesArrayReadElementCacheNode(ScriptArray arrayType, ArrayReadElementCacheNode next) {
            super(arrayType, next);
        }

        private TRegexUtil.TRegexResultAccessor getResultAccessor() {
            if (this.resultAccessor == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.resultAccessor = this.insert(TRegexUtil.TRegexResultAccessor.create());
            }
            return this.resultAccessor;
        }

        @Override
        protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
            LazyRegexResultIndicesArray lazyRegexResultIndicesArray = (LazyRegexResultIndicesArray)array;
            if (this.inBounds.profile(lazyRegexResultIndicesArray.hasElement(target, (int)index))) {
                return LazyRegexResultIndicesArray.materializeGroup(context, this.getResultAccessor(), target, (int)index);
            }
            return this.readOutOfBounds(target, index, receiver, defaultValue, context);
        }
    }

    private static class LazyRegexResultArrayReadElementCacheNode
    extends ArrayClassGuardCachedArrayReadElementCacheNode {
        @Node.Child
        private TRegexUtil.TRegexMaterializeResultNode materializeResultNode;
        @Node.Child
        private DynamicObjectLibrary lazyRegexResultNode;
        @Node.Child
        private DynamicObjectLibrary lazyRegexResultOriginalInputNode;

        LazyRegexResultArrayReadElementCacheNode(ScriptArray arrayType, ArrayReadElementCacheNode next) {
            super(arrayType, next);
        }

        private TRegexUtil.TRegexMaterializeResultNode getMaterializeResultNode() {
            if (this.materializeResultNode == null || this.lazyRegexResultNode == null || this.lazyRegexResultOriginalInputNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.materializeResultNode = this.insert(TRegexUtil.TRegexMaterializeResultNode.create());
                this.lazyRegexResultNode = this.insert(DynamicObjectLibrary.getFactory().createDispatched(5));
                this.lazyRegexResultOriginalInputNode = this.insert(DynamicObjectLibrary.getFactory().createDispatched(5));
            }
            return this.materializeResultNode;
        }

        @Override
        protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
            LazyRegexResultArray lazyRegexResultArray = (LazyRegexResultArray)array;
            if (this.inBounds.profile(lazyRegexResultArray.hasElement(target, (int)index))) {
                return LazyRegexResultArray.materializeGroup(context, this.getMaterializeResultNode(), target, (int)index, this.lazyRegexResultNode, this.lazyRegexResultOriginalInputNode);
            }
            return this.readOutOfBounds(target, index, receiver, defaultValue, context);
        }
    }

    private static class ConstantObjectArrayReadElementCacheNode
    extends ArrayClassGuardCachedArrayReadElementCacheNode {
        private final ConditionProfile holeArrayProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile holeProfile = ConditionProfile.createBinaryProfile();

        ConstantObjectArrayReadElementCacheNode(ScriptArray arrayType, ArrayReadElementCacheNode next) {
            super(arrayType, next);
        }

        @Override
        protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
            ConstantObjectArray constantObjectArray = (ConstantObjectArray)this.cast(array);
            if (this.inBounds.profile(constantObjectArray.isInBoundsFast(target, index))) {
                Object value2 = ConstantObjectArray.getElementInBoundsDirect(target, (int)index);
                if (this.holeArrayProfile.profile(!constantObjectArray.hasHoles(target))) {
                    return value2;
                }
                if (this.holeProfile.profile(!HolesObjectArray.isHoleValue(value2))) {
                    return value2;
                }
            }
            return this.readOutOfBounds(target, index, receiver, defaultValue, context);
        }
    }

    private static class EmptyArrayReadElementCacheNode
    extends ArrayClassGuardCachedArrayReadElementCacheNode {
        EmptyArrayReadElementCacheNode(ScriptArray arrayType, ArrayReadElementCacheNode next) {
            super(arrayType, next);
            assert (arrayType.getClass() == ConstantEmptyArray.class);
        }

        @Override
        protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
            return this.readOutOfBounds(target, index, receiver, defaultValue, context);
        }
    }

    private static class ConstantArrayReadElementCacheNode
    extends ArrayClassGuardCachedArrayReadElementCacheNode {
        ConstantArrayReadElementCacheNode(ScriptArray arrayType, ArrayReadElementCacheNode next) {
            super(arrayType, next);
        }

        @Override
        protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
            AbstractConstantArray constantArray = (AbstractConstantArray)this.cast(array);
            if (this.inBounds.profile(constantArray.hasElement(target, index))) {
                return constantArray.getElementInBounds(target, (int)index);
            }
            return this.readOutOfBounds(target, index, receiver, defaultValue, context);
        }
    }

    private static class ExactArrayReadElementCacheNode
    extends ArrayClassGuardCachedArrayReadElementCacheNode {
        private final JSClassProfile classProfile = JSClassProfile.create();

        ExactArrayReadElementCacheNode(ScriptArray arrayType, ArrayReadElementCacheNode next) {
            super(arrayType, next);
        }

        @Override
        protected Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) {
            return JSObject.getOrDefault(target, index, receiver, defaultValue, this.classProfile, (Node)this);
        }
    }

    private static abstract class ArrayClassGuardCachedArrayReadElementCacheNode
    extends ArrayReadElementCacheNode {
        private final ScriptArray arrayType;
        protected final ConditionProfile inBounds = ConditionProfile.createBinaryProfile();
        private final ConditionProfile needGetProperty = ConditionProfile.createBinaryProfile();
        private final JSClassProfile outOfBoundsClassProfile = JSClassProfile.create();

        ArrayClassGuardCachedArrayReadElementCacheNode(ScriptArray arrayType, ArrayReadElementCacheNode next) {
            super(next);
            this.arrayType = arrayType;
        }

        @Override
        protected final boolean guard(Object target, ScriptArray array) {
            return this.arrayType.isInstance(array);
        }

        protected final ScriptArray cast(ScriptArray array) {
            return this.arrayType.cast(array);
        }

        protected final ScriptArray getArrayType() {
            return this.arrayType;
        }

        protected Object readOutOfBounds(JSDynamicObject target, long index, Object receiver, Object defaultValue, JSContext context) {
            if (this.needGetProperty.profile(ArrayClassGuardCachedArrayReadElementCacheNode.needsSlowGet(target, context))) {
                return JSObject.getOrDefault(target, index, receiver, defaultValue, this.outOfBoundsClassProfile, (Node)this);
            }
            return defaultValue;
        }

        private static boolean needsSlowGet(JSDynamicObject target, JSContext context) {
            return !context.getArrayPrototypeNoElementsAssumption().isValid() || !context.getFastArrayAssumption().isValid() && JSSlowArray.isJSSlowArray(target) || !context.getFastArgumentsObjectAssumption().isValid() && JSSlowArgumentsArray.isJSSlowArgumentsObject(target);
        }
    }

    static abstract class ArrayReadElementCacheNode
    extends JavaScriptBaseNode {
        @Node.Child
        ArrayReadElementCacheNode arrayCacheNext;

        protected ArrayReadElementCacheNode(ArrayReadElementCacheNode next) {
            this.arrayCacheNext = next;
        }

        protected abstract Object executeArrayGet(JSDynamicObject var1, ScriptArray var2, long var3, Object var5, Object var6, JSContext var7);

        protected int executeArrayGetInt(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) throws UnexpectedResultException {
            return JSTypesGen.expectInteger(this.executeArrayGet(target, array, index, receiver, defaultValue, context));
        }

        protected double executeArrayGetDouble(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext context) throws UnexpectedResultException {
            return JSTypesGen.expectDouble(this.executeArrayGet(target, array, index, receiver, defaultValue, context));
        }

        protected abstract boolean guard(Object var1, ScriptArray var2);
    }

    private static class JavaObjectReadElementTypeCacheNode
    extends ToPropertyKeyCachedReadElementTypeCacheNode {
        protected final Class<?> targetClass;

        JavaObjectReadElementTypeCacheNode(Class<?> targetClass, ReadElementTypeCacheNode next) {
            super(next);
            this.targetClass = targetClass;
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
            this.toPropertyKey(index);
            return Undefined.instance;
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root) {
            return Undefined.instance;
        }

        @Override
        public final boolean guard(Object target) {
            return CompilerDirectives.isExact(target, this.targetClass);
        }
    }

    private static class JSObjectReadElementNonArrayTypeCacheNode
    extends JavaScriptBaseNode {
        @Node.Child
        private CachedGetPropertyNode getPropertyCachedNode;

        JSObjectReadElementNonArrayTypeCacheNode() {
        }

        public Object execute(JSDynamicObject targetObject, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
            if (this.getPropertyCachedNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.getPropertyCachedNode = this.insert(CachedGetPropertyNode.create(root.context));
            }
            return this.getPropertyCachedNode.execute(targetObject, index, receiver, defaultValue);
        }
    }

    private static class JSObjectReadElementTypeCacheNode
    extends ReadElementArrayDispatchNode {
        @Node.Child
        private IsArrayNode isArrayNode;
        @Node.Child
        private ToArrayIndexNode toArrayIndexNode;
        @Node.Child
        private JSObjectReadElementNonArrayTypeCacheNode nonArrayCaseNode;
        @Node.Child
        private IsJSDynamicObjectNode isObjectNode;
        private final ConditionProfile arrayProfile = ConditionProfile.createBinaryProfile();
        private final ConditionProfile arrayIndexProfile = ConditionProfile.createBinaryProfile();
        private final JSClassProfile jsclassProfile = JSClassProfile.create();

        JSObjectReadElementTypeCacheNode(ReadElementTypeCacheNode next) {
            super(next);
            this.isArrayNode = IsArrayNode.createIsAnyArray();
            this.isObjectNode = IsJSDynamicObjectNode.create();
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
            JSDynamicObject targetObject = (JSDynamicObject)target;
            boolean arrayCondition = this.isArrayNode.execute(targetObject);
            if (this.arrayProfile.profile(arrayCondition)) {
                ScriptArray array = JSObject.getArray(targetObject);
                Object objIndex = this.toArrayIndex(index);
                if (this.arrayIndexProfile.profile(objIndex instanceof Long)) {
                    long longIndex = (Long)objIndex;
                    return this.executeArrayGet(targetObject, array, longIndex, receiver, defaultValue, root.context);
                }
                return this.getProperty(targetObject, objIndex, receiver, defaultValue);
            }
            return this.readNonArrayObjectIndex(targetObject, index, receiver, defaultValue, root);
        }

        private Object toArrayIndex(Object index) {
            if (this.toArrayIndexNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.toArrayIndexNode = this.insert(ToArrayIndexNode.create());
            }
            return this.toArrayIndexNode.execute(index);
        }

        private Object readNonArrayObjectIndex(JSDynamicObject targetObject, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
            return this.getNonArrayNode().execute(targetObject, index, receiver, defaultValue, root);
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root) {
            JSDynamicObject targetObject = (JSDynamicObject)target;
            boolean arrayCondition = this.isArrayNode.execute(targetObject);
            if (this.arrayProfile.profile(arrayCondition)) {
                ScriptArray array = JSObject.getArray(targetObject);
                if (this.arrayIndexProfile.profile(JSRuntime.isArrayIndex(index))) {
                    return this.executeArrayGet(targetObject, array, index, receiver, defaultValue, root.context);
                }
                return this.getProperty(targetObject, Strings.fromInt(index), receiver, defaultValue);
            }
            return this.readNonArrayObjectIndex(targetObject, index, receiver, defaultValue, root);
        }

        @Override
        protected Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root) {
            JSDynamicObject targetObject = (JSDynamicObject)target;
            boolean arrayCondition = this.isArrayNode.execute(targetObject);
            if (this.arrayProfile.profile(arrayCondition)) {
                ScriptArray array = JSObject.getArray(targetObject);
                if (this.arrayIndexProfile.profile(JSRuntime.isArrayIndex(index))) {
                    return this.executeArrayGet(targetObject, array, index, receiver, defaultValue, root.context);
                }
                return this.getProperty(targetObject, Strings.fromLong(index), receiver, defaultValue);
            }
            return this.readNonArrayObjectIndex(targetObject, index, receiver, defaultValue, root);
        }

        @Override
        protected int executeWithTargetAndIndexUncheckedInt(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) throws UnexpectedResultException {
            JSDynamicObject targetObject = (JSDynamicObject)target;
            boolean arrayCondition = this.isArrayNode.execute(targetObject);
            if (this.arrayProfile.profile(arrayCondition)) {
                ScriptArray array = JSObject.getArray(targetObject);
                Object objIndex = this.toArrayIndex(index);
                if (this.arrayIndexProfile.profile(objIndex instanceof Long)) {
                    long longIndex = (Long)objIndex;
                    return this.executeArrayGetInt(targetObject, array, longIndex, receiver, defaultValue, root.context);
                }
                return JSTypesGen.expectInteger(this.getProperty(targetObject, objIndex, receiver, defaultValue));
            }
            return JSTypesGen.expectInteger(this.readNonArrayObjectIndex(targetObject, index, receiver, defaultValue, root));
        }

        @Override
        protected int executeWithTargetAndIndexUncheckedInt(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root) throws UnexpectedResultException {
            JSDynamicObject targetObject = (JSDynamicObject)target;
            boolean arrayCondition = this.isArrayNode.execute(targetObject);
            if (this.arrayProfile.profile(arrayCondition)) {
                ScriptArray array = JSObject.getArray(targetObject);
                if (this.arrayIndexProfile.profile(JSRuntime.isArrayIndex(index))) {
                    return this.executeArrayGetInt(targetObject, array, index, receiver, defaultValue, root.context);
                }
                return JSTypesGen.expectInteger(this.getProperty(targetObject, Strings.fromInt(index), receiver, defaultValue));
            }
            return JSTypesGen.expectInteger(this.readNonArrayObjectIndex(targetObject, index, receiver, defaultValue, root));
        }

        @Override
        protected double executeWithTargetAndIndexUncheckedDouble(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) throws UnexpectedResultException {
            JSDynamicObject targetObject = (JSDynamicObject)target;
            boolean arrayCondition = this.isArrayNode.execute(targetObject);
            if (this.arrayProfile.profile(arrayCondition)) {
                ScriptArray array = JSObject.getArray(targetObject);
                Object objIndex = this.toArrayIndex(index);
                if (this.arrayIndexProfile.profile(objIndex instanceof Long)) {
                    long longIndex = (Long)objIndex;
                    return this.executeArrayGetDouble(targetObject, array, longIndex, receiver, defaultValue, root.context);
                }
                return JSTypesGen.expectDouble(this.getProperty(targetObject, objIndex, receiver, defaultValue));
            }
            return JSTypesGen.expectDouble(this.readNonArrayObjectIndex(targetObject, index, receiver, defaultValue, root));
        }

        @Override
        protected double executeWithTargetAndIndexUncheckedDouble(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root) throws UnexpectedResultException {
            JSDynamicObject targetObject = (JSDynamicObject)target;
            boolean arrayCondition = this.isArrayNode.execute(targetObject);
            if (this.arrayProfile.profile(arrayCondition)) {
                ScriptArray array = JSObject.getArray(targetObject);
                if (this.arrayIndexProfile.profile(JSRuntime.isArrayIndex(index))) {
                    return this.executeArrayGetDouble(targetObject, array, index, receiver, defaultValue, root.context);
                }
                return JSTypesGen.expectDouble(this.getProperty(targetObject, Strings.fromInt(index), receiver, defaultValue));
            }
            return JSTypesGen.expectDouble(this.readNonArrayObjectIndex(targetObject, index, receiver, defaultValue, root));
        }

        @Override
        public boolean guard(Object target) {
            return this.isObjectNode.executeBoolean(target);
        }

        private Object getProperty(JSDynamicObject targetObject, Object objIndex, Object receiver, Object defaultValue) {
            return JSObject.getOrDefault(targetObject, objIndex, receiver, defaultValue, this.jsclassProfile, (Node)this);
        }

        private JSObjectReadElementNonArrayTypeCacheNode getNonArrayNode() {
            if (this.nonArrayCaseNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.nonArrayCaseNode = this.insert(new JSObjectReadElementNonArrayTypeCacheNode());
            }
            return this.nonArrayCaseNode;
        }
    }

    protected static abstract class ReadElementArrayDispatchNode
    extends ReadElementTypeCacheNode {
        @Node.Child
        private ArrayReadElementCacheNode arrayReadElementNode;

        protected ReadElementArrayDispatchNode(ReadElementTypeCacheNode next) {
            super(next);
        }

        @ExplodeLoop
        protected final Object executeArrayGet(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext root) {
            ArrayReadElementCacheNode c = this.arrayReadElementNode;
            while (c != null) {
                boolean guard = c.guard(target, array);
                if (guard) {
                    return c.executeArrayGet(target, array, index, receiver, defaultValue, root);
                }
                c = c.arrayCacheNext;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            ArrayReadElementCacheNode specialization = this.specialize(target, array);
            return specialization.executeArrayGet(target, array, index, receiver, defaultValue, root);
        }

        @ExplodeLoop
        protected final int executeArrayGetInt(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext root) throws UnexpectedResultException {
            ArrayReadElementCacheNode c = this.arrayReadElementNode;
            while (c != null) {
                boolean guard = c.guard(target, array);
                if (guard) {
                    return c.executeArrayGetInt(target, array, index, receiver, defaultValue, root);
                }
                c = c.arrayCacheNext;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            ArrayReadElementCacheNode specialization = this.specialize(target, array);
            return specialization.executeArrayGetInt(target, array, index, receiver, defaultValue, root);
        }

        @ExplodeLoop
        protected final double executeArrayGetDouble(JSDynamicObject target, ScriptArray array, long index, Object receiver, Object defaultValue, JSContext root) throws UnexpectedResultException {
            ArrayReadElementCacheNode c = this.arrayReadElementNode;
            while (c != null) {
                boolean guard = c.guard(target, array);
                if (guard) {
                    return c.executeArrayGetDouble(target, array, index, receiver, defaultValue, root);
                }
                c = c.arrayCacheNext;
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            ArrayReadElementCacheNode specialization = this.specialize(target, array);
            return specialization.executeArrayGetDouble(target, array, index, receiver, defaultValue, root);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private ArrayReadElementCacheNode specialize(JSDynamicObject target, ScriptArray array) {
            CompilerAsserts.neverPartOfCompilation();
            Lock lock = this.getLock();
            lock.lock();
            try {
                ArrayReadElementCacheNode currentHead;
                ArrayReadElementCacheNode c = currentHead = this.arrayReadElementNode;
                while (c != null) {
                    if (c.guard(target, array)) {
                        ArrayReadElementCacheNode arrayReadElementCacheNode = c;
                        return arrayReadElementCacheNode;
                    }
                    c = c.arrayCacheNext;
                }
                currentHead = ReadElementArrayDispatchNode.purgeStaleCacheEntries(currentHead, target);
                ArrayReadElementCacheNode newCacheNode = ReadElementNode.makeArrayCacheNode(target, array, currentHead);
                this.insert(newCacheNode);
                this.arrayReadElementNode = newCacheNode;
                if (!newCacheNode.guard(target, array)) {
                    throw Errors.shouldNotReachHere();
                }
                ArrayReadElementCacheNode arrayReadElementCacheNode = newCacheNode;
                return arrayReadElementCacheNode;
            }
            finally {
                lock.unlock();
            }
        }

        private static ArrayReadElementCacheNode purgeStaleCacheEntries(ArrayReadElementCacheNode head5, JSDynamicObject target) {
            ArrayAllocationSite allocationSite;
            if (JSConfig.TrackArrayAllocationSites && head5 != null && JSArray.isJSArray(target) && (allocationSite = JSAbstractArray.arrayGetAllocationSite(target)) != null && allocationSite.getInitialArrayType() != null) {
                ArrayReadElementCacheNode c = head5;
                ArrayReadElementCacheNode prev = null;
                while (c != null) {
                    if (c instanceof ConstantArrayReadElementCacheNode) {
                        ConstantArrayReadElementCacheNode existingNode = (ConstantArrayReadElementCacheNode)c;
                        ScriptArray initialArrayType = allocationSite.getInitialArrayType();
                        if (!(initialArrayType instanceof ConstantEmptyArray) && existingNode.getArrayType() instanceof ConstantEmptyArray) {
                            if (JSConfig.TraceArrayTransitions) {
                                System.out.println("purging " + existingNode + ": " + existingNode.getArrayType() + " => " + JSAbstractArray.arrayGetArrayType(target));
                            }
                            if (prev == null) {
                                return existingNode.arrayCacheNext;
                            }
                            prev.arrayCacheNext = existingNode.arrayCacheNext;
                            return head5;
                        }
                    }
                    prev = c;
                    c = c.arrayCacheNext;
                }
            }
            return head5;
        }

        protected static ReadElementArrayDispatchNode create() {
            return new ReadElementArrayDispatchNode(null){

                @Override
                public boolean guard(Object target) {
                    return true;
                }

                @Override
                protected Object executeWithTargetAndIndexUnchecked(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) {
                    throw Errors.shouldNotReachHere();
                }

                @Override
                protected Object executeWithTargetAndIndexUnchecked(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root) {
                    throw Errors.shouldNotReachHere();
                }

                @Override
                protected Object executeWithTargetAndIndexUnchecked(Object target, long index, Object receiver, Object defaultValue, ReadElementNode root) {
                    throw Errors.shouldNotReachHere();
                }
            };
        }
    }

    static abstract class ReadElementTypeCacheNode
    extends JavaScriptBaseNode {
        @Node.Child
        private ReadElementTypeCacheNode typeCacheNext;

        protected ReadElementTypeCacheNode(ReadElementTypeCacheNode next) {
            this.typeCacheNext = next;
        }

        public abstract boolean guard(Object var1);

        protected abstract Object executeWithTargetAndIndexUnchecked(Object var1, Object var2, Object var3, Object var4, ReadElementNode var5);

        protected abstract Object executeWithTargetAndIndexUnchecked(Object var1, int var2, Object var3, Object var4, ReadElementNode var5);

        protected abstract Object executeWithTargetAndIndexUnchecked(Object var1, long var2, Object var4, Object var5, ReadElementNode var6);

        protected int executeWithTargetAndIndexUncheckedInt(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) throws UnexpectedResultException {
            return JSTypesGen.expectInteger(this.executeWithTargetAndIndexUnchecked(target, index, receiver, defaultValue, root));
        }

        protected int executeWithTargetAndIndexUncheckedInt(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root) throws UnexpectedResultException {
            return this.executeWithTargetAndIndexUncheckedInt(target, (Object)index, receiver, defaultValue, root);
        }

        protected double executeWithTargetAndIndexUncheckedDouble(Object target, Object index, Object receiver, Object defaultValue, ReadElementNode root) throws UnexpectedResultException {
            return JSTypesGen.expectDouble(this.executeWithTargetAndIndexUnchecked(target, index, receiver, defaultValue, root));
        }

        protected double executeWithTargetAndIndexUncheckedDouble(Object target, int index, Object receiver, Object defaultValue, ReadElementNode root) throws UnexpectedResultException {
            return this.executeWithTargetAndIndexUncheckedDouble(target, (Object)index, receiver, defaultValue, root);
        }
    }
}

