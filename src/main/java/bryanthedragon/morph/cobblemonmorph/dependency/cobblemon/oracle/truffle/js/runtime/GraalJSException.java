
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleStackTrace;
import com.oracle.truffle.api.TruffleStackTraceElement;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.function.FunctionRootNode;
import com.oracle.truffle.js.nodes.promise.PerformPromiseAllNode;
import com.oracle.truffle.js.nodes.promise.PromiseReactionJobNode;
import com.oracle.truffle.js.runtime.Evaluator;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptFunctionCallNode;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.UserScriptException;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.ArrayList;
import java.util.List;

@ExportLibrary(value=InteropLibrary.class)
@ImportStatic(value={JSConfig.class})
public abstract class GraalJSException
extends AbstractTruffleException {
    private static final long serialVersionUID = -6624166672101791072L;
    private static final JSStackTraceElement[] EMPTY_STACK_TRACE = new JSStackTraceElement[0];
    private JSStackTraceElement[] jsStackTrace;
    private Object location;
    private int stackTraceLimit;

    protected GraalJSException(String message, Throwable cause, Node node, int stackTraceLimit) {
        super(message, cause, stackTraceLimit, node);
        this.location = node;
        this.stackTraceLimit = stackTraceLimit;
        this.jsStackTrace = stackTraceLimit == 0 ? EMPTY_STACK_TRACE : null;
    }

    protected GraalJSException(String message, Node node, int stackTraceLimit) {
        super(message, null, stackTraceLimit, node);
        this.location = node;
        this.stackTraceLimit = stackTraceLimit;
        this.jsStackTrace = stackTraceLimit == 0 ? EMPTY_STACK_TRACE : null;
    }

    protected GraalJSException(String message, SourceSection location, int stackTraceLimit) {
        super(message, null, stackTraceLimit, null);
        this.location = location;
        this.stackTraceLimit = stackTraceLimit;
        this.jsStackTrace = stackTraceLimit == 0 ? EMPTY_STACK_TRACE : null;
    }

    protected static <T extends GraalJSException> T fillInStackTrace(T exception, boolean capture, JSDynamicObject skipFramesUpTo, boolean customSkip) {
        exception.fillInStackTrace(capture, skipFramesUpTo, customSkip);
        return exception;
    }

    protected static <T extends GraalJSException> T fillInStackTrace(T exception, boolean capture) {
        exception.fillInStackTrace(capture, Undefined.instance, false);
        return exception;
    }

    protected final GraalJSException fillInStackTrace(boolean capture, JSDynamicObject skipFramesUpTo, boolean customSkip) {
        assert (capture || skipFramesUpTo == Undefined.instance);
        assert (this.jsStackTrace == (this.stackTraceLimit == 0 ? EMPTY_STACK_TRACE : null));
        if (capture && this.stackTraceLimit > 0) {
            this.jsStackTrace = this.getJSStackTrace(skipFramesUpTo, customSkip);
        }
        return this;
    }

    @ExportMessage
    public boolean hasSourceLocation() {
        if (this.location instanceof SourceSection) {
            return true;
        }
        Node locationNode = this.getLocation();
        SourceSection sourceSection = locationNode != null ? locationNode.getEncapsulatingSourceSection() : null;
        return sourceSection != null;
    }

    @ExportMessage(name="getSourceLocation")
    public SourceSection getSourceLocationInterop() throws UnsupportedMessageException {
        SourceSection sourceSection;
        if (this.location instanceof SourceSection) {
            return (SourceSection)this.location;
        }
        Node locationNode = this.getLocation();
        SourceSection sourceSection2 = sourceSection = locationNode != null ? locationNode.getEncapsulatingSourceSection() : null;
        if (sourceSection == null) {
            throw UnsupportedMessageException.create();
        }
        return sourceSection;
    }

    public abstract Object getErrorObjectLazy();

    public abstract Object getErrorObject();

    public JSStackTraceElement[] getJSStackTrace() {
        if (this.jsStackTrace != null) {
            return this.jsStackTrace;
        }
        this.jsStackTrace = this.materializeJSStackTrace();
        return this.jsStackTrace;
    }

    @CompilerDirectives.TruffleBoundary
    private JSStackTraceElement[] materializeJSStackTrace() {
        return this.getJSStackTrace(Undefined.instance, false);
    }

    @CompilerDirectives.TruffleBoundary
    private JSStackTraceElement[] getJSStackTrace(JSDynamicObject skipUpTo, boolean customSkip) {
        assert (this.stackTraceLimit > 0);
        JSContext context = JavaScriptLanguage.getCurrentLanguage().getJSContext();
        boolean nashornMode = context.isOptionNashornCompatibilityMode();
        JSDynamicObject skipFramesUpTo = nashornMode ? Undefined.instance : skipUpTo;
        boolean skippingFrames = JSFunction.isJSFunction(skipFramesUpTo);
        if (skippingFrames && customSkip) {
            FunctionRootNode.setOmitFromStackTrace(JSFunction.getFunctionData((JSFunctionObject)skipFramesUpTo));
        }
        List<TruffleStackTraceElement> stackTrace = TruffleStackTrace.getStackTrace(this);
        if (skippingFrames && customSkip) {
            FunctionRootNode.setOmitFromStackTrace(null);
        }
        if (stackTrace == null) {
            return EMPTY_STACK_TRACE;
        }
        FrameVisitorImpl visitor = new FrameVisitorImpl(this.getLocation(), this.stackTraceLimit, skipFramesUpTo, nashornMode);
        boolean asyncStackTraces = context.isOptionAsyncStackTraces();
        ArrayList<List<TruffleStackTraceElement>> asyncStacks = null;
        for (TruffleStackTraceElement truffleStackTraceElement : stackTrace) {
            List<TruffleStackTraceElement> asyncStack;
            if (!visitor.visitFrame(truffleStackTraceElement)) {
                asyncStacks = null;
                break;
            }
            if (!asyncStackTraces || (asyncStack = GraalJSException.getAsynchronousStackTrace(truffleStackTraceElement)) == null || asyncStack.isEmpty()) continue;
            if (asyncStacks == null) {
                asyncStacks = new ArrayList<List<TruffleStackTraceElement>>();
            }
            asyncStacks.add(asyncStack);
        }
        if (asyncStacks != null && !asyncStacks.isEmpty()) {
            block1: for (List list : asyncStacks) {
                visitor.async = true;
                for (TruffleStackTraceElement element : list) {
                    if (visitor.visitFrame(element)) continue;
                    break block1;
                }
            }
        }
        return visitor.getStackTrace().toArray(EMPTY_STACK_TRACE);
    }

    private static List<TruffleStackTraceElement> getAsynchronousStackTrace(TruffleStackTraceElement element) {
        if (element.getFrame() == null) {
            return null;
        }
        RootNode rootNode = element.getTarget().getRootNode();
        if (rootNode.getLanguageInfo() == null) {
            return null;
        }
        if (rootNode instanceof JavaScriptRootNode) {
            if (rootNode instanceof PromiseReactionJobNode.PromiseReactionJobRootNode) {
                return JavaScriptRootNode.findAsynchronousFrames((JavaScriptRootNode)rootNode, element.getFrame());
            }
            return null;
        }
        return TruffleStackTrace.getAsynchronousStackTrace(element.getTarget(), element.getFrame());
    }

    public void setJSStackTrace(JSStackTraceElement[] jsStackTrace) {
        this.jsStackTrace = jsStackTrace;
    }

    @CompilerDirectives.TruffleBoundary
    public static JSStackTraceElement[] getJSStackTrace(Node originatingNode) {
        int stackTraceLimit = JavaScriptLanguage.get(originatingNode).getJSContext().getContextOptions().getStackTraceLimit();
        return UserScriptException.createCapture("", originatingNode, stackTraceLimit).getJSStackTrace();
    }

    private static JSStackTraceElement processJSFrame(RootNode rootNode, Node node, Object thisObj, JSFunctionObject functionObj, boolean inStrictMode, boolean inNashornMode, boolean async, int promiseIndex) {
        Node callNode = node;
        while (callNode.getSourceSection() == null) {
            callNode = callNode.getParent();
        }
        SourceSection callNodeSourceSection = callNode.getSourceSection();
        Source source = callNodeSourceSection.getSource();
        TruffleString fileName = GraalJSException.getFileName(source);
        TruffleString functionName = JSFunction.isBuiltin(functionObj) ? JSFunction.getName(functionObj) : (rootNode instanceof FunctionRootNode ? ((FunctionRootNode)rootNode).getNameTString() : Strings.fromJavaString(rootNode.getName()));
        boolean eval = false;
        if (GraalJSException.isEvalSource(source)) {
            functionName = Strings.EVAL;
            eval = true;
        } else if (functionName == null || GraalJSException.isInternalFunctionName(functionName)) {
            functionName = Strings.EMPTY_STRING;
        }
        SourceSection targetSourceSection = null;
        if (!inNashornMode && callNode instanceof JavaScriptFunctionCallNode) {
            Node target = ((JavaScriptFunctionCallNode)((Object)callNode)).getTarget();
            targetSourceSection = target == null ? null : target.getSourceSection();
        }
        boolean global = JSRuntime.isNullOrUndefined(thisObj) && !JSFunction.isStrict(functionObj) || GraalJSException.isGlobalObject(thisObj, JSFunction.getRealm(functionObj));
        return new JSStackTraceElement(fileName, functionName, callNodeSourceSection, thisObj, functionObj, targetSourceSection, inStrictMode, eval, global, inNashornMode, async, promiseIndex);
    }

    private static boolean isEvalSource(Source source) {
        return source != null && source.getName().startsWith("eval at ");
    }

    private static boolean isInternalFunctionName(TruffleString functionName) {
        return Strings.length(functionName) >= 1 && Strings.charAt(functionName, 0) == ':';
    }

    private static boolean isGlobalObject(Object object, JSRealm realm) {
        return JSDynamicObject.isJSDynamicObject(object) && realm != null && realm.getGlobalObject() == object;
    }

    private static JSStackTraceElement processForeignFrame(Node node, boolean strict, boolean inNashornMode, boolean async) {
        RootNode rootNode = node.getRootNode();
        SourceSection sourceSection = rootNode.getSourceSection();
        if (sourceSection == null) {
            return null;
        }
        TruffleString fileName = GraalJSException.getFileName(sourceSection.getSource());
        TruffleString functionName = Strings.fromJavaString(rootNode.getName());
        Object thisObj = null;
        Object functionObj = null;
        return new JSStackTraceElement(fileName, functionName, sourceSection, thisObj, functionObj, null, strict, false, false, inNashornMode, async, -1);
    }

    private static TruffleString getPrimitiveConstructorName(Object thisObj) {
        assert (JSRuntime.isJSPrimitive(thisObj));
        if (thisObj instanceof Boolean) {
            return Strings.UC_BOOLEAN;
        }
        if (JSRuntime.isNumber(thisObj)) {
            return Strings.UC_NUMBER;
        }
        if (Strings.isTString(thisObj)) {
            return Strings.UC_STRING;
        }
        if (thisObj instanceof Symbol) {
            return Strings.UC_SYMBOL;
        }
        return null;
    }

    private static int sourceSectionOffset(SourceSection callNodeSourceSection, SourceSection targetSourceSection) {
        int index;
        String targetCode;
        int index2;
        int offset = 0;
        String code = callNodeSourceSection.getCharacters().toString();
        if (targetSourceSection != null && (index2 = code.indexOf(targetCode = targetSourceSection.getCharacters().toString())) != -1) {
            offset += (index2 += targetCode.length());
            code = code.substring(index2);
        }
        if ((index = code.indexOf(40)) != -1) {
            int i;
            for (i = --index; i >= 0 && Character.isWhitespace(code.charAt(i)); --i) {
            }
            if (i >= 0 && Character.isJavaIdentifierPart(code.charAt(i))) {
                while (--i >= 0 && Character.isJavaIdentifierPart(code.charAt(i))) {
                }
                index = i;
            }
            offset += index + 1;
        }
        return offset;
    }

    private static TruffleString getFileName(Source source) {
        return source != null ? Strings.fromJavaString(source.getName()) : Strings.UNKNOWN_FILENAME;
    }

    public void printJSStackTrace() {
        System.err.println(this.getMessage());
        for (JSStackTraceElement jsste : this.jsStackTrace) {
            System.err.println(jsste);
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static void printJSStackTrace(Node originatingNode) {
        JSStackTraceElement[] jsstes;
        for (JSStackTraceElement jsste : jsstes = GraalJSException.getJSStackTrace(originatingNode)) {
            System.err.println(jsste);
        }
    }

    @ExportMessage
    public final boolean hasLanguage() {
        return true;
    }

    @ExportMessage
    public final Class<? extends TruffleLanguage<?>> getLanguage() {
        return JavaScriptLanguage.class;
    }

    @ExportMessage
    public final Object toDisplayString(boolean allowSideEffects) {
        return JSRuntime.toDisplayString(this, allowSideEffects);
    }

    @ExportMessage
    @CompilerDirectives.TruffleBoundary
    public final int identityHashCode(@CachedLibrary(limit="InteropLibraryLimit") InteropLibrary delegateLib) throws UnsupportedMessageException {
        return delegateLib.identityHashCode(this.getErrorObject());
    }

    public static final class JSStackTraceElement {
        private final TruffleString fileName;
        private final TruffleString functionName;
        private final SourceSection sourceSection;
        private final Object thisObj;
        private final Object functionObj;
        private final SourceSection targetSourceSection;
        private final boolean strict;
        private final boolean eval;
        private final boolean global;
        private final boolean inNashornMode;
        private final boolean async;
        private final int promiseIndex;

        private JSStackTraceElement(TruffleString fileName, TruffleString functionName, SourceSection sourceSection, Object thisObj, Object functionObj, SourceSection targetSourceSection, boolean strict, boolean eval, boolean global, boolean inNashornMode, boolean async, int promiseIndex) {
            CompilerAsserts.neverPartOfCompilation();
            this.fileName = fileName;
            this.functionName = functionName;
            this.sourceSection = sourceSection;
            this.thisObj = thisObj;
            this.functionObj = functionObj;
            this.targetSourceSection = targetSourceSection;
            this.strict = strict;
            this.eval = eval;
            this.global = global;
            this.inNashornMode = inNashornMode;
            this.async = async;
            this.promiseIndex = promiseIndex;
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleString getFileName() {
            if (Strings.startsWith(this.fileName, Evaluator.TS_EVAL_AT_SOURCE_NAME_PREFIX)) {
                return Evaluator.TS_EVAL_SOURCE_NAME;
            }
            return this.fileName;
        }

        public TruffleString getClassName() {
            return this.getTypeName(false);
        }

        public TruffleString getTypeName() {
            return this.getTypeName(true);
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleString getTypeName(boolean checkGlobal) {
            if (this.inNashornMode) {
                return Strings.concatAll(Strings.ANGLE_BRACKET_OPEN, this.fileName, Strings.ANGLE_BRACKET_CLOSE);
            }
            if (checkGlobal && this.global) {
                return Strings.GLOBAL;
            }
            Object thisObject = this.getThis();
            if (thisObject == JSFunction.CONSTRUCT) {
                return this.getFunctionName();
            }
            if (!JSRuntime.isNullOrUndefined(thisObject) && !this.global) {
                if (JSDynamicObject.isJSDynamicObject(thisObject)) {
                    return JSRuntime.getConstructorName((JSDynamicObject)thisObject);
                }
                if (JSRuntime.isJSPrimitive(thisObject)) {
                    return GraalJSException.getPrimitiveConstructorName(thisObject);
                }
            }
            return null;
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleString getFunctionName() {
            TruffleString dynamicName;
            if (!(!JSFunction.isJSFunction(this.functionObj) || (dynamicName = JSStackTraceElement.findFunctionName((JSDynamicObject)this.functionObj)) == null || Strings.isEmpty(dynamicName) || this.isEval() && Strings.equals(Strings.DYNAMIC_FUNCTION_NAME, dynamicName) && JSObject.getJSContext((JSDynamicObject)this.functionObj).isOptionV8CompatibilityMode())) {
                return dynamicName;
            }
            return this.functionName;
        }

        private static TruffleString findFunctionName(JSDynamicObject functionObj) {
            Object name;
            assert (JSFunction.isJSFunction(functionObj));
            PropertyDescriptor desc = JSObject.getOwnProperty(functionObj, JSFunction.NAME);
            if (desc != null && desc.isDataDescriptor() && Strings.isTString(name = desc.getValue())) {
                return (TruffleString)name;
            }
            return null;
        }

        @CompilerDirectives.TruffleBoundary
        public String getMethodName() {
            return Strings.toJavaString(this.getMethodName(JavaScriptLanguage.getCurrentLanguage().getJSContext()));
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleString getMethodName(JSContext context) {
            TruffleString name;
            if (context.isOptionNashornCompatibilityMode()) {
                return JSError.correctMethodName(this.functionName, context);
            }
            if (JSRuntime.isNullOrUndefined(this.thisObj) || !JSDynamicObject.isJSDynamicObject(this.thisObj)) {
                return null;
            }
            if (!JSFunction.isJSFunction(this.functionObj)) {
                return null;
            }
            JSDynamicObject receiver = (JSDynamicObject)this.thisObj;
            JSFunctionObject function = (JSFunctionObject)this.functionObj;
            if (this.functionName != null && !Strings.isEmpty(this.functionName) && (name = JSStackTraceElement.findMethodPropertyNameByFunctionName(receiver, this.functionName, function)) != null) {
                return name;
            }
            return JSStackTraceElement.findMethodPropertyName(receiver, function);
        }

        private static TruffleString findMethodPropertyNameByFunctionName(JSDynamicObject receiver, TruffleString functionName, JSFunctionObject functionObj) {
            TruffleString propertyName = functionName;
            boolean accessor = false;
            if (Strings.startsWith(propertyName, Strings.GET_SPC) || Strings.startsWith(propertyName, Strings.SET_SPC)) {
                propertyName = Strings.lazySubstring(propertyName, 4);
                accessor = true;
            }
            if (propertyName.isEmpty()) {
                return null;
            }
            JSDynamicObject current = receiver;
            while (current != Null.instance && !JSProxy.isJSProxy(current)) {
                PropertyDescriptor desc = JSObject.getOwnProperty(current, propertyName);
                if (desc != null) {
                    if (desc.isAccessorDescriptor() != accessor || desc.getValue() != functionObj && desc.getGet() != functionObj && desc.getSet() != functionObj) break;
                    return propertyName;
                }
                current = JSObject.getPrototype(current);
            }
            return null;
        }

        private static TruffleString findMethodPropertyName(JSDynamicObject receiver, JSDynamicObject functionObj) {
            TruffleString name = null;
            JSDynamicObject current = receiver;
            while (current != Null.instance && !JSProxy.isJSProxy(current)) {
                for (TruffleString key : JSObject.enumerableOwnNames(current)) {
                    PropertyDescriptor desc = JSObject.getOwnProperty(current, key);
                    if (desc.getValue() != functionObj && desc.getGet() != functionObj && desc.getSet() != functionObj) continue;
                    if (name == null) {
                        name = key;
                        continue;
                    }
                    return null;
                }
                current = JSObject.getPrototype(current);
            }
            return name;
        }

        @CompilerDirectives.TruffleBoundary
        public int getLineNumber() {
            if (this.sourceSection == null) {
                return -1;
            }
            int lineNumber = this.sourceSection.getStartLine();
            if (!this.inNashornMode && this.targetSourceSection != null) {
                int offset = GraalJSException.sourceSectionOffset(this.sourceSection, this.targetSourceSection);
                CharSequence chars = this.sourceSection.getCharacters();
                for (int pos = 0; pos < offset; ++pos) {
                    if (chars.charAt(pos) != '\n') continue;
                    ++lineNumber;
                }
            }
            return lineNumber;
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleString getLine() {
            int lineNumber = this.getLineNumber();
            if (this.sourceSection == null || this.sourceSection.getSource() == null || lineNumber <= 0) {
                return Strings.UNKNOWN_FILENAME;
            }
            return Strings.fromJavaString(this.sourceSection.getSource().getCharacters(lineNumber).toString());
        }

        @CompilerDirectives.TruffleBoundary
        public int getColumnNumber() {
            if (this.sourceSection == null) {
                return -1;
            }
            int columnNumber = this.sourceSection.getStartColumn();
            if (!this.inNashornMode && this.targetSourceSection != null) {
                int offset = GraalJSException.sourceSectionOffset(this.sourceSection, this.targetSourceSection);
                CharSequence chars = this.sourceSection.getCharacters();
                for (int pos = 0; pos < offset; ++pos) {
                    if (chars.charAt(pos) == '\n') {
                        columnNumber = 1;
                        continue;
                    }
                    ++columnNumber;
                }
            }
            return columnNumber;
        }

        public int getPosition() {
            return this.sourceSection != null ? this.sourceSection.getCharIndex() : -1;
        }

        public Object getThis() {
            return this.thisObj;
        }

        @CompilerDirectives.TruffleBoundary
        public Object getThisOrGlobal() {
            if (this.global) {
                if (JSRuntime.isNullOrUndefined(this.thisObj)) {
                    return JSFunction.getRealm((JSFunctionObject)this.functionObj).getGlobalObject();
                }
                assert (this.thisObj == JSFunction.getRealm((JSFunctionObject)this.functionObj).getGlobalObject());
                return this.thisObj;
            }
            return this.thisObj == JSFunction.CONSTRUCT ? Undefined.instance : this.thisObj;
        }

        public Object getFunction() {
            return this.functionObj;
        }

        public boolean isStrict() {
            return this.strict;
        }

        @CompilerDirectives.TruffleBoundary
        public boolean isConstructor() {
            if (this.thisObj == JSFunction.CONSTRUCT) {
                return true;
            }
            if (!JSRuntime.isNullOrUndefined(this.thisObj) && JSDynamicObject.isJSDynamicObject(this.thisObj)) {
                Object constructor = JSRuntime.getDataProperty((JSDynamicObject)this.thisObj, JSObject.CONSTRUCTOR);
                return constructor != null && constructor == this.functionObj;
            }
            return false;
        }

        public boolean isEval() {
            return this.eval;
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleString getEvalOrigin() {
            if (Strings.startsWith(this.fileName, Strings.ANGLE_BRACKET_OPEN)) {
                return null;
            }
            return this.fileName;
        }

        public int getPromiseIndex() {
            return this.promiseIndex;
        }

        public boolean isPromiseAll() {
            return this.promiseIndex >= 0;
        }

        public boolean isAsync() {
            return this.async;
        }

        @CompilerDirectives.TruffleBoundary
        public String toString() {
            JSContext context = JavaScriptLanguage.getCurrentJSRealm().getContext();
            return Strings.toJavaString(this.toString(context));
        }

        @CompilerDirectives.TruffleBoundary
        public TruffleString toString(JSContext context) {
            boolean includeMethodName;
            TruffleStringBuilder sb = Strings.builderCreate();
            if (this.isPromiseAll()) {
                Strings.builderAppend(sb, Strings.ASYNC_PROMISE_ALL_BEGIN);
                Strings.builderAppend(sb, this.promiseIndex);
                Strings.builderAppend(sb, Strings.PAREN_CLOSE);
                return Strings.builderToString(sb);
            }
            TruffleString className = this.getClassName();
            TruffleString methodName = JSError.correctMethodName(this.getFunctionName(), context);
            if (methodName == null || Strings.isEmpty(methodName)) {
                TruffleString name = this.getMethodName(context);
                methodName = name == null ? JSError.getAnonymousFunctionNameStackTrace(context) : name;
            }
            boolean bl = includeMethodName = className != null || !JSError.getAnonymousFunctionNameStackTrace(context).equals(methodName);
            if (includeMethodName) {
                if (this.async) {
                    Strings.builderAppend(sb, Strings.ASYNC_SPC);
                }
                if (className != null) {
                    if (className.equals(methodName)) {
                        if (this.isConstructor()) {
                            Strings.builderAppend(sb, Strings.NEW_SPACE);
                        }
                    } else {
                        Strings.builderAppend(sb, className);
                        Strings.builderAppend(sb, Strings.DOT);
                    }
                }
                Strings.builderAppend(sb, methodName);
                Strings.builderAppend(sb, Strings.SPACE_PAREN_OPEN);
            }
            if (JSFunction.isBuiltinSourceSection(this.sourceSection)) {
                Strings.builderAppend(sb, Strings.NATIVE);
            } else {
                TruffleString evalOrigin = this.getEvalOrigin();
                TruffleString sourceName = evalOrigin != null ? evalOrigin : this.getFileName();
                Strings.builderAppend(sb, sourceName);
                if (this.eval) {
                    Strings.builderAppend(sb, Strings.COMMA_ANONYMOUS_BRACKETS);
                }
                Strings.builderAppend(sb, Strings.COLON);
                Strings.builderAppend(sb, this.getLineNumber());
                Strings.builderAppend(sb, Strings.COLON);
                Strings.builderAppend(sb, this.getColumnNumber());
            }
            if (includeMethodName) {
                Strings.builderAppend(sb, Strings.PAREN_CLOSE);
            }
            return Strings.builderToString(sb);
        }
    }

    @ExportMessage
    @ImportStatic(value={JSConfig.class})
    public static final class IsIdenticalOrUndefined {
        @Specialization
        public static TriState doException(GraalJSException receiver, GraalJSException other, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="thisLib") InteropLibrary thisLib, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="otherLib") InteropLibrary otherLib) {
            if (receiver == other) {
                return TriState.TRUE;
            }
            Object thisObj = receiver.getErrorObjectLazy();
            if (thisObj == null) {
                return TriState.FALSE;
            }
            Object otherObj = other.getErrorObjectLazy();
            if (otherObj == null) {
                return TriState.FALSE;
            }
            if (thisLib.hasIdentity(thisObj) && otherLib.hasIdentity(other)) {
                return TriState.valueOf(thisLib.isIdentical(thisObj, other, otherLib));
            }
            return TriState.UNDEFINED;
        }

        @Specialization
        public static TriState doJSObject(GraalJSException receiver, JSDynamicObject other) {
            Object thisObj = receiver.getErrorObjectLazy();
            if (thisObj == null) {
                return TriState.FALSE;
            }
            return TriState.valueOf(thisObj == other);
        }

        @Specialization(guards={"!isGraalJSException(other)"}, replaces={"doJSObject"})
        public static TriState doOther(GraalJSException receiver, Object other, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="thisLib") InteropLibrary thisLib, @CachedLibrary(limit="InteropLibraryLimit") @Cached.Shared(value="otherLib") InteropLibrary otherLib) {
            Object thisObj = receiver.getErrorObjectLazy();
            if (thisObj == null) {
                return other instanceof JSDynamicObject ? TriState.FALSE : TriState.UNDEFINED;
            }
            if (thisLib.hasIdentity(thisObj) && otherLib.hasIdentity(other)) {
                return TriState.valueOf(thisLib.isIdentical(thisObj, other, otherLib));
            }
            return TriState.UNDEFINED;
        }

        static boolean isGraalJSException(Object value2) {
            return value2 instanceof GraalJSException;
        }
    }

    private static final class FrameVisitorImpl {
        private static final int STACK_FRAME_SKIP = 0;
        private static final int STACK_FRAME_JS = 1;
        private static final int STACK_FRAME_FOREIGN = 2;
        private final List<JSStackTraceElement> stackTrace = new ArrayList<JSStackTraceElement>();
        private final Node originatingNode;
        private final int stackTraceLimit;
        private final JSDynamicObject skipFramesUpTo;
        private final boolean inNashornMode;
        private boolean inStrictMode;
        private boolean skippingFrames;
        private boolean first = true;
        boolean async;

        FrameVisitorImpl(Node originatingNode, int stackTraceLimit, JSDynamicObject skipFramesUpTo, boolean nashornMode) {
            this.originatingNode = originatingNode;
            this.stackTraceLimit = stackTraceLimit;
            this.skipFramesUpTo = skipFramesUpTo;
            this.skippingFrames = skipFramesUpTo != Undefined.instance;
            this.inNashornMode = nashornMode;
        }

        private int stackFrameType(Node callNode) {
            if (callNode == null) {
                return 0;
            }
            SourceSection sourceSection = callNode.getEncapsulatingSourceSection();
            if (sourceSection == null) {
                return 0;
            }
            if (JSFunction.isBuiltinSourceSection(sourceSection)) {
                return this.inNashornMode ? 0 : 1;
            }
            if (sourceSection.getSource().isInternal() || !sourceSection.isAvailable()) {
                return 0;
            }
            if (JSRuntime.isJSRootNode(callNode.getRootNode())) {
                return 1;
            }
            return 2;
        }

        private static RootNode rootNode(TruffleStackTraceElement element) {
            RootCallTarget callTarget = element.getTarget();
            return callTarget instanceof RootCallTarget ? callTarget.getRootNode() : null;
        }

        public boolean visitFrame(TruffleStackTraceElement element) {
            Node callNode = element.getLocation();
            if (this.first) {
                this.first = false;
                if (JSRuntime.isJSRootNode(FrameVisitorImpl.rootNode(element))) {
                    callNode = this.originatingNode;
                }
            }
            if (callNode == null) {
                callNode = FrameVisitorImpl.rootNode(element);
            }
            if (callNode != null) {
                switch (this.stackFrameType(callNode)) {
                    case 1: {
                        Object[] arguments;
                        RootNode rootNode = callNode.getRootNode();
                        assert (JSRuntime.isJSRootNode(rootNode));
                        int promiseIndex = -1;
                        if (element.getFrame() == null) break;
                        if (JSRuntime.isJSFunctionRootNode(rootNode)) {
                            arguments = element.getFrame().getArguments();
                        } else if (((JavaScriptRootNode)rootNode).isResumption()) {
                            arguments = element.getFrame().getArguments();
                        } else {
                            Object promiseIndexArg;
                            if (!(rootNode instanceof PerformPromiseAllNode.PromiseAllMarkerRootNode)) break;
                            arguments = element.getFrame().getArguments();
                            if (JSArguments.getUserArgumentCount(arguments) > 0 && (promiseIndexArg = JSArguments.getUserArgument(arguments, 0)) instanceof Integer) {
                                promiseIndex = (Integer)promiseIndexArg;
                            }
                        }
                        Object thisObj = JSArguments.getThisObject(arguments);
                        Object functionObj = JSArguments.getFunctionObject(arguments);
                        if (!JSFunction.isJSFunction(functionObj)) break;
                        JSFunctionObject function = (JSFunctionObject)functionObj;
                        JSFunctionData functionData = JSFunction.getFunctionData(function);
                        if (functionData.isBuiltin()) {
                            if (JSFunction.isStrictBuiltin(function, JSRealm.get(null))) {
                                this.inStrictMode = true;
                            }
                        } else if (functionData.isStrict()) {
                            this.inStrictMode = true;
                        }
                        if (this.skippingFrames && function == this.skipFramesUpTo) {
                            this.skippingFrames = false;
                            return true;
                        }
                        JSRealm realm = JSFunction.getRealm(function);
                        if (JSFunction.isBuiltinThatShouldNotAppearInStackTrace(realm, function)) {
                            return true;
                        }
                        if (this.skippingFrames) break;
                        if (functionData.isAsync() && !functionData.isGenerator() && JSRuntime.isJSFunctionRootNode(rootNode)) {
                            return true;
                        }
                        this.stackTrace.add(GraalJSException.processJSFrame(rootNode, callNode, thisObj, function, this.inStrictMode, this.inNashornMode, this.async, promiseIndex));
                        break;
                    }
                    case 2: {
                        JSStackTraceElement elem;
                        if (this.skippingFrames || (elem = GraalJSException.processForeignFrame(callNode, this.inStrictMode, this.inNashornMode, this.async)) == null) break;
                        this.stackTrace.add(elem);
                    }
                }
            }
            return this.stackTrace.size() < this.stackTraceLimit;
        }

        public List<JSStackTraceElement> getStackTrace() {
            return this.stackTrace;
        }
    }
}

