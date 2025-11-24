
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.profiles.LoopConditionProfile;
import com.oracle.truffle.js.builtins.ArrayPrototypeBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.access.IsArrayNode;
import com.oracle.truffle.js.nodes.access.JSHasPropertyNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.array.JSArrayFirstElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayLastElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayNextElementIndexNode;
import com.oracle.truffle.js.nodes.array.JSArrayPreviousElementIndexNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.JSClassProfile;

public abstract class ForEachIndexCallNode
extends JavaScriptBaseNode {
    @Node.Child
    private IsArrayNode isArrayNode = IsArrayNode.createIsAnyArray();
    protected final JSClassProfile targetClassProfile = JSClassProfile.create();
    protected final LoopConditionProfile loopCond = LoopConditionProfile.createCountingProfile();
    @Node.Child
    private CallbackNode callbackNode;
    @Node.Child
    protected MaybeResultNode maybeResultNode;
    @Node.Child
    private ReadElementNode.ReadElementArrayDispatchNode readElementNode;
    @Node.Child
    private JSArrayFirstElementIndexNode firstElementIndexNode;
    @Node.Child
    private JSArrayLastElementIndexNode lastElementIndexNode;
    @Node.Child
    private JSHasPropertyNode hasPropertyNode;
    @Node.Child
    private ImportValueNode toJSTypeNode;
    @Node.Child
    private InteropLibrary interop;
    protected final JSContext context;
    protected final boolean checkHasProperty;

    protected ForEachIndexCallNode(JSContext context, CallbackNode callbackArgumentsNode, MaybeResultNode maybeResultNode, boolean checkHasProperty) {
        this.callbackNode = callbackArgumentsNode;
        this.maybeResultNode = maybeResultNode;
        this.context = context;
        this.checkHasProperty = checkHasProperty;
        this.readElementNode = ReadElementNode.ReadElementArrayDispatchNode.create();
    }

    public static ForEachIndexCallNode create(JSContext context, CallbackNode callbackArgumentsNode, MaybeResultNode maybeResultNode, boolean forward, boolean checkHasProperty) {
        if (forward) {
            return new ForwardForEachIndexCallNode(context, callbackArgumentsNode, maybeResultNode, checkHasProperty);
        }
        return new BackwardForEachIndexCallNode(context, callbackArgumentsNode, maybeResultNode, checkHasProperty);
    }

    public final Object executeForEachIndex(Object target, Object callback, Object callbackThisArg, long fromIndex, long length, Object initialResult) {
        boolean isArray = this.isArrayNode.execute(target);
        if (isArray && this.context.getArrayPrototypeNoElementsAssumption().isValid()) {
            return this.executeForEachIndexFast((JSDynamicObject)target, callback, callbackThisArg, fromIndex, length, initialResult);
        }
        return this.executeForEachIndexSlow(target, callback, callbackThisArg, fromIndex, length, initialResult);
    }

    protected abstract Object executeForEachIndexFast(JSDynamicObject var1, Object var2, Object var3, long var4, long var6, Object var8);

    protected abstract Object executeForEachIndexSlow(Object var1, Object var2, Object var3, long var4, long var6, Object var8);

    protected final long firstElementIndex(JSDynamicObject target, long length) {
        if (this.firstElementIndexNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.firstElementIndexNode = this.insert(JSArrayFirstElementIndexNode.create(this.context));
        }
        return this.firstElementIndexNode.executeLong(target, length);
    }

    protected final long lastElementIndex(JSDynamicObject target, long length) {
        if (this.lastElementIndexNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.lastElementIndexNode = this.insert(JSArrayLastElementIndexNode.create(this.context));
        }
        return this.lastElementIndexNode.executeLong(target, length);
    }

    protected final InteropLibrary getInterop() {
        if (this.interop == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.interop = this.insert(InteropLibrary.getFactory().createDispatched(5));
        }
        return this.interop;
    }

    protected Object foreignRead(Object target, long index, boolean isForeignArray) {
        if (this.toJSTypeNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.toJSTypeNode = this.insert(ImportValueNode.create());
        }
        if (isForeignArray) {
            return JSInteropUtil.readArrayElementOrDefault(target, index, Undefined.instance, this.getInterop(), this.toJSTypeNode, this);
        }
        return JSInteropUtil.readMemberOrDefault(target, Strings.fromLong(index), Undefined.instance, this.getInterop(), this.toJSTypeNode, this);
    }

    protected Object getElement(Object target, long index, boolean isForeign, boolean isForeignArray) {
        if (!isForeign) {
            assert (JSDynamicObject.isJSDynamicObject(target));
            return JSObject.get((JSDynamicObject)target, index, this.targetClassProfile);
        }
        return this.foreignRead(target, index, isForeignArray);
    }

    protected final boolean hasDetachedBuffer(Object view) {
        return !this.context.getTypedArrayNotDetachedAssumption().isValid() && JSArrayBufferView.isJSArrayBufferView(view) && JSArrayBufferView.hasDetachedBuffer((JSDynamicObject)view);
    }

    protected final Object callback(long index, Object value2, Object target, Object callback, Object callbackThisArg, Object currentResult) {
        if (this.callbackNode == null) {
            TruffleSafepoint.poll(this);
            return callbackThisArg;
        }
        return this.callbackNode.apply(index, value2, target, callback, callbackThisArg, currentResult);
    }

    protected final Object readElementInBounds(JSDynamicObject target, long index) {
        return this.readElementNode.executeArrayGet(target, JSObject.getArray(target), index, target, Undefined.instance, this.context);
    }

    protected final boolean hasProperty(Object target, long index) {
        if (this.hasPropertyNode == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.hasPropertyNode = this.insert(JSHasPropertyNode.create());
        }
        return this.hasPropertyNode.executeBoolean(target, index);
    }

    protected static final class BackwardForEachIndexCallNode
    extends ForEachIndexCallNode {
        @Node.Child
        protected JSArrayPreviousElementIndexNode previousElementIndexNode;

        public BackwardForEachIndexCallNode(JSContext context, CallbackNode callbackArgumentsNode, MaybeResultNode maybeResultNode, boolean checkHasProperty) {
            super(context, callbackArgumentsNode, maybeResultNode, checkHasProperty);
        }

        @Override
        protected Object executeForEachIndexFast(JSDynamicObject target, Object callback, Object callbackThisArg, long fromIndex, long length, Object initialResult) {
            assert (fromIndex < length);
            long index = this.previousElementIndex(target, fromIndex + 1L);
            Object currentResult = initialResult;
            long count = 0L;
            while (!(!this.loopCond.profile(index >= 0L && index >= this.firstElementIndex(target, length)) || this.checkHasProperty && this.hasDetachedBuffer(target))) {
                Object value2 = this.readElementInBounds(target, index);
                Object callbackResult = this.callback(index, value2, target, callback, callbackThisArg, currentResult);
                MaybeResult<Object> maybeResult = this.maybeResultNode.apply(index, value2, callbackResult, currentResult);
                currentResult = maybeResult.get();
                if (maybeResult.isPresent()) break;
                ++count;
                index = this.previousElementIndex(target, index);
            }
            ArrayPrototypeBuiltins.BasicArrayOperation.reportLoopCount(this, count);
            return currentResult;
        }

        @Override
        protected Object executeForEachIndexSlow(Object target, Object callback, Object callbackThisArg, long fromIndex, long length, Object initialResult) {
            Object currentResult = initialResult;
            boolean isForeign = JSRuntime.isForeignObject(target);
            boolean isForeignArray = isForeign && this.getInterop().hasArrayElements(target);
            for (long index = fromIndex; index >= 0L; --index) {
                if (this.checkHasProperty && !this.hasProperty(target, index)) continue;
                Object value2 = this.getElement(target, index, isForeign, isForeignArray);
                Object callbackResult = this.callback(index, value2, target, callback, callbackThisArg, currentResult);
                MaybeResult<Object> maybeResult = this.maybeResultNode.apply(index, value2, callbackResult, currentResult);
                currentResult = maybeResult.get();
                if (maybeResult.isPresent()) break;
            }
            ArrayPrototypeBuiltins.BasicArrayOperation.reportLoopCount(this, fromIndex);
            return currentResult;
        }

        private long previousElementIndex(JSDynamicObject target, long currentIndex) {
            if (this.previousElementIndexNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.previousElementIndexNode = this.insert(JSArrayPreviousElementIndexNode.create(this.context));
            }
            return this.previousElementIndexNode.executeLong(target, currentIndex);
        }
    }

    protected static final class ForwardForEachIndexCallNode
    extends ForEachIndexCallNode {
        private final ConditionProfile fromIndexZero = ConditionProfile.createBinaryProfile();
        @Node.Child
        private JSArrayNextElementIndexNode nextElementIndexNode;

        public ForwardForEachIndexCallNode(JSContext context, CallbackNode callbackArgumentsNode, MaybeResultNode maybeResultNode, boolean checkHasProperty) {
            super(context, callbackArgumentsNode, maybeResultNode, checkHasProperty);
        }

        @Override
        protected Object executeForEachIndexFast(JSDynamicObject target, Object callback, Object callbackThisArg, long fromIndex, long length, Object initialResult) {
            long index = this.fromIndexZero.profile(fromIndex == 0L) ? this.firstElementIndex(target, length) : this.nextElementIndex(target, fromIndex - 1L, length);
            Object currentResult = initialResult;
            long count = 0L;
            while (!(!this.loopCond.profile(index < length && index <= this.lastElementIndex(target, length)) || this.checkHasProperty && this.hasDetachedBuffer(target))) {
                Object value2 = this.readElementInBounds(target, index);
                Object callbackResult = this.callback(index, value2, target, callback, callbackThisArg, currentResult);
                MaybeResult<Object> maybeResult = this.maybeResultNode.apply(index, value2, callbackResult, currentResult);
                currentResult = maybeResult.get();
                if (maybeResult.isPresent()) break;
                ++count;
                index = this.nextElementIndex(target, index, length);
            }
            ArrayPrototypeBuiltins.BasicArrayOperation.reportLoopCount(this, count);
            return currentResult;
        }

        @Override
        protected Object executeForEachIndexSlow(Object target, Object callback, Object callbackThisArg, long fromIndex, long length, Object initialResult) {
            Object currentResult = initialResult;
            boolean isForeign = JSRuntime.isForeignObject(target);
            boolean isForeignArray = isForeign && this.getInterop().hasArrayElements(target);
            for (long index = fromIndex; index < length; ++index) {
                if (this.checkHasProperty && !this.hasProperty(target, index)) continue;
                Object value2 = this.getElement(target, index, isForeign, isForeignArray);
                Object callbackResult = this.callback(index, value2, target, callback, callbackThisArg, currentResult);
                MaybeResult<Object> maybeResult = this.maybeResultNode.apply(index, value2, callbackResult, currentResult);
                currentResult = maybeResult.get();
                if (maybeResult.isPresent()) break;
            }
            ArrayPrototypeBuiltins.BasicArrayOperation.reportLoopCount(this, length - fromIndex);
            return currentResult;
        }

        private long nextElementIndex(JSDynamicObject target, long currentIndex, long length) {
            if (this.nextElementIndexNode == null) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.nextElementIndexNode = this.insert(JSArrayNextElementIndexNode.create(this.context));
            }
            return this.nextElementIndexNode.executeLong(target, currentIndex, length);
        }
    }

    public static abstract class MaybeResultNode
    extends JavaScriptBaseNode {
        public abstract MaybeResult<Object> apply(long var1, Object var3, Object var4, Object var5);
    }

    @CompilerDirectives.ValueType
    public static final class MaybeResult<T> {
        private final T result;
        private final boolean resultPresent;

        public MaybeResult(T result, boolean resultPresent) {
            this.result = result;
            this.resultPresent = resultPresent;
        }

        public static <T> MaybeResult<T> returnResult(T result) {
            return new MaybeResult<T>(result, true);
        }

        public static <T> MaybeResult<T> continueResult(T result) {
            return new MaybeResult<T>(result, false);
        }

        public boolean isPresent() {
            return this.resultPresent;
        }

        public T get() {
            return this.result;
        }
    }

    public static abstract class CallbackNode
    extends JavaScriptBaseNode {
        public abstract Object apply(long var1, Object var3, Object var4, Object var5, Object var6, Object var7);
    }
}

