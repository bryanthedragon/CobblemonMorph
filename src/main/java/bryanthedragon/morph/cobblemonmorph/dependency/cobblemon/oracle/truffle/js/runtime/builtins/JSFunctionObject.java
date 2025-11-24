
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.JSInteropExecuteNode;
import com.oracle.truffle.js.nodes.interop.JSInteropInstantiateNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.interop.InteropFunction;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.Undefined;

@ExportLibrary(value=InteropLibrary.class)
public abstract class JSFunctionObject
extends JSNonProxyObject {
    private final JSFunctionData functionData;
    private final MaterializedFrame enclosingFrame;
    private final JSRealm realm;
    private Object classPrototype;

    protected JSFunctionObject(Shape shape, JSFunctionData functionData, MaterializedFrame enclosingFrame, JSRealm realm, Object classPrototype) {
        super(shape);
        this.functionData = functionData;
        this.enclosingFrame = enclosingFrame;
        this.realm = realm;
        this.classPrototype = classPrototype;
    }

    public final JSFunctionData getFunctionData() {
        return this.functionData;
    }

    public final MaterializedFrame getEnclosingFrame() {
        return this.enclosingFrame;
    }

    public final JSRealm getRealm() {
        return this.realm;
    }

    public final Object getClassPrototype() {
        return this.classPrototype;
    }

    public void setClassPrototype(Object classPrototype) {
        this.classPrototype = classPrototype;
    }

    public Object getLexicalThis() {
        return this.classPrototype;
    }

    @Override
    public TruffleString getClassName() {
        return JSFunction.INSTANCE.getClassName(this);
    }

    @Override
    public TruffleString getBuiltinToStringTag() {
        return JSFunction.INSTANCE.getBuiltinToStringTag(this);
    }

    @ExportMessage
    public final boolean isExecutable(@Cached IsCallableNode isCallable) {
        return isCallable.executeBoolean(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ExportMessage
    public final Object execute(Object[] args, @CachedLibrary(value="this") InteropLibrary self, @Cached JSInteropExecuteNode callNode, @Cached.Shared(value="exportValue") @Cached ExportValueNode exportNode) throws UnsupportedMessageException {
        JavaScriptLanguage language = JavaScriptLanguage.get(self);
        language.interopBoundaryEnter(this.realm);
        try {
            Object result = callNode.execute(this, Undefined.instance, args);
            Object object = exportNode.execute(result);
            return object;
        }
        finally {
            language.interopBoundaryExit(this.realm);
        }
    }

    @ExportMessage
    public final boolean isInstantiable() {
        return JSRuntime.isConstructor(this);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @ExportMessage
    public final Object instantiate(Object[] args, @CachedLibrary(value="this") InteropLibrary self, @Cached JSInteropInstantiateNode callNode, @Cached.Shared(value="exportValue") @Cached ExportValueNode exportNode) throws UnsupportedMessageException {
        JavaScriptLanguage language = JavaScriptLanguage.get(self);
        language.interopBoundaryEnter(this.realm);
        try {
            Object result = callNode.execute(this, args);
            Object object = exportNode.execute(result);
            return object;
        }
        finally {
            language.interopBoundaryExit(this.realm);
        }
    }

    @ExportMessage
    public final boolean hasSourceLocation() {
        return JSFunctionObject.getSourceLocationImpl(this) != null;
    }

    @ExportMessage
    public final SourceSection getSourceLocation() throws UnsupportedMessageException {
        SourceSection sourceSection = JSFunctionObject.getSourceLocationImpl(this);
        if (sourceSection == null) {
            throw UnsupportedMessageException.create();
        }
        return sourceSection;
    }

    @CompilerDirectives.TruffleBoundary
    private static SourceSection getSourceLocationImpl(JSDynamicObject receiver) {
        if (JSFunction.isJSFunction(receiver)) {
            JSDynamicObject func = receiver;
            CallTarget ct = JSFunction.getCallTarget(func);
            if (JSFunction.isBoundFunction(func)) {
                func = JSFunction.getBoundTargetFunction(func);
                ct = JSFunction.getCallTarget(func);
            }
            if (ct instanceof RootCallTarget) {
                return ((RootCallTarget)ct).getRootNode().getSourceSection();
            }
        }
        return null;
    }

    @ExportMessage
    public final boolean isMetaObject() {
        return true;
    }

    @CompilerDirectives.TruffleBoundary
    @ExportMessage.Repeat(value={@ExportMessage(name="getMetaQualifiedName"), @ExportMessage(name="getMetaSimpleName")})
    public final Object getMetaObjectName() {
        Object name = JSRuntime.getDataProperty(this, JSFunction.NAME);
        if (Strings.isTString(name)) {
            return name;
        }
        return Strings.EMPTY_STRING;
    }

    @CompilerDirectives.TruffleBoundary
    @ExportMessage
    public final boolean isMetaInstance(Object instance) {
        Object constructorPrototype = JSRuntime.getDataProperty(this, JSObject.PROTOTYPE);
        if (JSGuards.isJSObject(constructorPrototype)) {
            Object obj = instance;
            if (obj instanceof InteropFunction) {
                obj = ((InteropFunction)obj).getFunction();
            }
            if (obj instanceof JSException) {
                obj = ((JSException)obj).getErrorObject();
            }
            if (JSGuards.isJSObject(obj) && !JSProxy.isJSProxy(obj)) {
                JSDynamicObject proto = JSObject.getPrototype((JSDynamicObject)obj);
                while (proto != Null.instance) {
                    if (proto == constructorPrototype) {
                        return true;
                    }
                    if (JSProxy.isJSProxy(proto)) break;
                    proto = JSObject.getPrototype(proto);
                }
            }
        }
        return false;
    }

    public static JSFunctionObject create(Shape shape, JSFunctionData functionData, MaterializedFrame enclosingFrame, JSRealm realm, Object classPrototype) {
        return new Unbound(shape, functionData, enclosingFrame, realm, classPrototype);
    }

    public static JSFunctionObject createBound(Shape shape, JSFunctionData functionData, JSRealm realm, Object classPrototype, JSDynamicObject boundTargetFunction, Object boundThis, Object[] boundArguments) {
        return new Bound(shape, functionData, realm, classPrototype, boundTargetFunction, boundThis, boundArguments);
    }

    public static final class Bound
    extends JSFunctionObject {
        private final JSDynamicObject boundTargetFunction;
        private final Object boundThis;
        private final Object[] boundArguments;
        private final int boundLength;
        private TruffleString boundName;

        protected Bound(Shape shape, JSFunctionData functionData, JSRealm realm, Object classPrototype, JSDynamicObject boundTargetFunction, Object boundThis, Object[] boundArguments) {
            super(shape, functionData, JSFrameUtil.NULL_MATERIALIZED_FRAME, realm, classPrototype);
            this.boundTargetFunction = boundTargetFunction;
            this.boundThis = boundThis;
            this.boundArguments = boundArguments;
            this.boundLength = this.calculateBoundLength();
        }

        public JSDynamicObject getBoundTargetFunction() {
            return this.boundTargetFunction;
        }

        public Object getBoundThis() {
            return this.boundThis;
        }

        public Object[] getBoundArguments() {
            return this.boundArguments;
        }

        public TruffleString getBoundName() {
            if (this.boundName == null) {
                this.initializeBoundName();
            }
            return this.boundName;
        }

        public void setTargetName(TruffleString targetName) {
            this.boundName = Strings.concat(Strings.BOUND_SPC, targetName);
        }

        @CompilerDirectives.TruffleBoundary
        private void initializeBoundName() {
            this.setTargetName(Bound.getFunctionName(this.boundTargetFunction));
        }

        private static TruffleString getFunctionName(JSDynamicObject function) {
            if (JSFunction.isBoundFunction(function)) {
                return ((Bound)function).getBoundName();
            }
            return JSFunction.getName(function);
        }

        public int getBoundLength() {
            return this.boundLength;
        }

        private int calculateBoundLength() {
            return Math.max(0, Bound.getBoundFunctionLength(this.boundTargetFunction) - this.boundArguments.length);
        }

        private static int getBoundFunctionLength(JSDynamicObject function) {
            if (JSFunction.isBoundFunction(function)) {
                return ((Bound)function).getBoundLength();
            }
            return JSFunction.getLength(function);
        }
    }

    public static final class Unbound
    extends JSFunctionObject {
        protected Unbound(Shape shape, JSFunctionData functionData, MaterializedFrame enclosingFrame, JSRealm realm, Object classPrototype) {
            super(shape, functionData, enclosingFrame, realm, classPrototype);
        }
    }
}

