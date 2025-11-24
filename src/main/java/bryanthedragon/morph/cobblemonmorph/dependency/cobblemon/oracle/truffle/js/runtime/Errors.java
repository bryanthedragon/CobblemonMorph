
package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.builtins.JSErrorObject;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Null;

public final class Errors {
    private Errors() {
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createAggregateError(Object errors, TruffleString message, Node originatingNode) {
        JSContext context = JavaScriptLanguage.get(originatingNode).getJSContext();
        JSRealm realm = JSRealm.get(originatingNode);
        JSErrorObject errorObj = JSError.createErrorObject(context, realm, JSErrorType.AggregateError);
        JSError.setMessage(errorObj, message);
        JSObjectUtil.putDataProperty(context, errorObj, JSError.ERRORS_NAME, errors, JSError.ERRORS_ATTRIBUTES);
        JSException exception = JSException.create(JSErrorType.AggregateError, Strings.toJavaString(message), errorObj, realm);
        JSError.setException(realm, errorObj, exception, false);
        return exception;
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createAggregateError(Object errors, Node originatingNode) {
        JSContext context = JavaScriptLanguage.get(originatingNode).getJSContext();
        JSRealm realm = JSRealm.get(originatingNode);
        JSErrorObject errorObj = JSError.createErrorObject(context, realm, JSErrorType.AggregateError);
        JSObjectUtil.putDataProperty(context, errorObj, JSError.ERRORS_NAME, errors, JSError.ERRORS_ATTRIBUTES);
        JSException exception = JSException.create(JSErrorType.AggregateError, null, errorObj, realm);
        JSError.setException(realm, errorObj, exception, false);
        return exception;
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createError(String message) {
        return JSException.create(JSErrorType.Error, message);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createEvalError(String message) {
        return JSException.create(JSErrorType.EvalError, message);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeError(String message) {
        return JSException.create(JSErrorType.RangeError, message);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeError(String message, Node originatingNode) {
        return JSException.create(JSErrorType.RangeError, message, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeErrorFormat(String message, Node originatingNode, Object ... args) {
        return JSException.create(JSErrorType.RangeError, String.format(message, args), originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createURIError(String message) {
        return JSException.create(JSErrorType.URIError, message);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeError(String message) {
        return JSException.create(JSErrorType.TypeError, message);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorFormat(String message, Object ... args) {
        return JSException.create(JSErrorType.TypeError, String.format(message, args));
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeError(String message, Node originatingNode) {
        return JSException.create(JSErrorType.TypeError, message, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeError(String message, Throwable cause, Node originatingNode) {
        return JSException.create(JSErrorType.TypeError, message, cause, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotMixBigIntWithOtherTypes(Node originatingNode) {
        return Errors.createTypeError("Cannot mix BigInt and other types, use explicit conversions.", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createErrorCanNotConvertToBigInt(JSErrorType type, Object x) {
        return JSException.create(type, String.format("Cannot convert %s to a BigInt.", JSRuntime.safeToString(x)));
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotConvertBigIntToNumber(Node originatingNode) {
        return Errors.createTypeError("Cannot convert a BigInt value to a number.", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNotAFunction(Object functionObj) {
        return Errors.createTypeErrorNotAFunction(functionObj, null);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNotAFunction(Object functionObj, Node originatingNode) {
        assert (!JSFunction.isJSFunction(functionObj));
        return JSException.create(JSErrorType.TypeError, String.format("%s is not a function", JSRuntime.safeToString(functionObj)), originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNotAConstructor(Object object, JSContext context) {
        return Errors.createTypeErrorNotAConstructor(object, null, context);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNotAConstructor(Object object, Node originatingNode, JSContext context) {
        String msg = String.format(context.isOptionNashornCompatibilityMode() ? "%s is not a constructor function" : "%s is not a constructor", JSRuntime.safeToString(object));
        return JSException.create(JSErrorType.TypeError, msg, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorTypeXExpected(Object type) {
        return Errors.createTypeErrorFormat("%s object expected.", type);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCalledOnNonObject() {
        return Errors.createTypeError("called on non-object");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorMethodCalledOnNonObjectOrWrongType(String method) {
        return Errors.createTypeErrorFormat("Method %s called on a non-object or on a wrong type of object.", method);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorSegmenterExpected() {
        return Errors.createTypeError("Segmenter object expected.");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorSegmentsExpected() {
        return Errors.createTypeError("Segments object expected.");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorDisplayNamesExpected() {
        return Errors.createTypeError("DisplayNames object expected.");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorLocaleExpected() {
        return Errors.createTypeError("Locale object expected.");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNoOverloadFound(TruffleString operatorName, Node originatingNode) {
        return Errors.createTypeError("No overload found for " + Strings.toJavaString(operatorName), originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createSyntaxError(String message, Throwable cause, Node originatingNode) {
        return JSException.create(JSErrorType.SyntaxError, message, cause, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createSyntaxError(String message) {
        return JSException.create(JSErrorType.SyntaxError, message);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createSyntaxError(String message, Node originatingNode) {
        return JSException.create(JSErrorType.SyntaxError, message, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createSyntaxErrorFormat(String message, Node originatingNode, Object ... args) {
        return JSException.create(JSErrorType.SyntaxError, String.format(message, args), originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createSyntaxError(String message, SourceSection sourceLocation, boolean isIncompleteSource) {
        return JSException.create(JSErrorType.SyntaxError, message, sourceLocation, isIncompleteSource);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createSyntaxErrorVariableAlreadyDeclared(TruffleString varName, Node originatingNode) {
        return Errors.createSyntaxError("Variable \"" + varName + "\" has already been declared", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createReferenceError(String message, Node originatingNode) {
        return JSException.create(JSErrorType.ReferenceError, message, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createReferenceError(String message, Throwable cause, Node originatingNode) {
        return JSException.create(JSErrorType.ReferenceError, message, cause, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createReferenceError(String message) {
        return JSException.create(JSErrorType.ReferenceError, message);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createReferenceError(String message, SourceSection sourceLocation) {
        return JSException.create(JSErrorType.ReferenceError, message, sourceLocation, false);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createReferenceErrorDerivedConstructorThisNotInitialized(Node originatingNode) {
        return Errors.createReferenceError("Must call super constructor in derived class before accessing 'this' or returning from derived constructor", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorDerivedConstructorReturnedIllegalType(Node originatingNode) {
        return Errors.createTypeError("Derived constructors may only return object or undefined", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNotObjectCoercible(Object value2, Node originatingNode) {
        JavaScriptLanguage language = JavaScriptLanguage.get(originatingNode);
        return Errors.createTypeErrorNotObjectCoercible(value2, originatingNode, language.getJSContext());
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNotObjectCoercible(Object value2, Node originatingNode, JSContext context) {
        if (context.isOptionNashornCompatibilityMode()) {
            return Errors.createTypeErrorNotAnObject(value2, originatingNode);
        }
        return Errors.createTypeError("Cannot convert undefined or null to object: " + JSRuntime.safeToString(value2), originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNotAnObject(Object value2) {
        return Errors.createTypeErrorNotAnObject(value2, null);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNotAnObject(Object value2, Node originatingNode) {
        return Errors.createTypeError(JSRuntime.safeToString(value2) + " is not an Object", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorIterResultNotAnObject(Object value2, Node originatingNode) {
        return Errors.createTypeErrorNotAnObject(value2, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNotIterable(Object value2, Node originatingNode) {
        return Errors.createTypeError(JSRuntime.safeToString(value2) + " is not iterable", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorInvalidPrototype(Object value2) {
        return Errors.createTypeError("Object prototype may only be an Object or null: " + JSRuntime.safeToString(value2));
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorInvalidInstanceofTarget(Object target, Node originatingNode) {
        if (JSRuntime.isForeignObject(target)) {
            return Errors.createTypeError("Right-hand-side of instanceof is not a meta object", originatingNode);
        }
        if (!JSRuntime.isObject(target)) {
            return Errors.createTypeError("Right-hand-side of instanceof is not an object", originatingNode);
        }
        assert (!JSRuntime.isCallable(target));
        return Errors.createTypeError("Right-hand-side of instanceof is not callable", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotConvertToPrimitiveValue() {
        return Errors.createTypeError("Cannot convert object to primitive value");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotConvertToPrimitiveValue(Node originatingNode) {
        return Errors.createTypeError("Cannot convert object to primitive value", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotConvertToString(String what) {
        return Errors.createTypeErrorCannotConvertToString(what, null);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotConvertToString(String what, Node originatingNode) {
        return Errors.createTypeError("Cannot convert " + what + " to a string", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotConvertToNumber(String what) {
        return Errors.createTypeErrorCannotConvertToNumber(what, null);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotConvertToNumber(String what, Node originatingNode) {
        return Errors.createTypeError("Cannot convert " + what + " to a number", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorIncompatibleReceiver(TruffleString methodName, Object receiver) {
        return Errors.createTypeErrorIncompatibleReceiver(Strings.toJavaString(methodName), receiver);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorIncompatibleReceiver(String methodName, Object receiver) {
        return Errors.createTypeError("Method " + methodName + " called on incompatible receiver " + JSRuntime.safeToString(receiver));
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorIncompatibleReceiver(Object what) {
        return Errors.createTypeError("incompatible receiver: " + JSRuntime.safeToString(what));
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotSetProto(JSDynamicObject thisObj, JSDynamicObject proto) {
        if (!JSNonProxy.checkProtoCycle(thisObj, proto)) {
            if (JSObject.getJSContext(thisObj).isOptionNashornCompatibilityMode()) {
                return Errors.createTypeError("Cannot create__proto__ cycle for " + JSObject.defaultToString(thisObj));
            }
            return Errors.createTypeError("Cyclic __proto__ value");
        }
        throw Errors.createTypeError("Cannot set __proto__ of non-extensible " + JSObject.defaultToString(thisObj));
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNotWritableProperty(Object key, Object thisObj, Node originatingNode) {
        JavaScriptLanguage language = JavaScriptLanguage.get(originatingNode);
        String message = language.getJSContext().isOptionNashornCompatibilityMode() ? Errors.keyToString(key) + " is not a writable property of " + JSRuntime.safeToString(thisObj) : "Cannot assign to read only property '" + key.toString() + "' of " + JSRuntime.safeToString(thisObj);
        return Errors.createTypeError(message, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNotWritableProperty(Object key, Object thisObj) {
        return Errors.createTypeErrorNotWritableProperty(key, thisObj, null);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNotWritableIndex(long index, Object thisObj, Node originatingNode) {
        return Errors.createTypeErrorNotWritableProperty(Strings.fromLong(index), thisObj, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorLengthNotWritable() {
        return Errors.createTypeError("Cannot assign to read only property 'length'");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNotConfigurableProperty(Object key) {
        return JSException.create(JSErrorType.TypeError, Errors.keyToString(key) + " is not a configurable property");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNotExtensible(JSDynamicObject thisObj, Object key) {
        return Errors.createTypeError("Cannot add new property " + Errors.keyToString(key) + " to non-extensible " + JSObject.defaultToString(thisObj));
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorSetNonObjectReceiver(Object receiver, Object key) {
        return Errors.createTypeError("Cannot add property " + Errors.keyToString(key) + " to non-object " + JSRuntime.safeToString(receiver));
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorConstReassignment(Object key, Object thisObj, Node originatingNode) {
        if (JSDynamicObject.isJSDynamicObject(thisObj) && JSObject.getJSContext((JSDynamicObject)thisObj).isOptionV8CompatibilityMode()) {
            throw Errors.createTypeError("Assignment to constant variable.", originatingNode);
        }
        throw Errors.createTypeError("Assignment to constant \"" + key + "\"", originatingNode);
    }

    private static String keyToString(Object key) {
        assert (JSRuntime.isPropertyKey(key));
        return Strings.isTString(key) ? "\"" + Strings.toJavaString((TruffleString)key) + "\"" : key.toString();
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createReferenceErrorNotDefined(Object key, Node originatingNode) {
        JavaScriptLanguage language = JavaScriptLanguage.get(originatingNode);
        return Errors.createReferenceErrorNotDefined(language.getJSContext(), key, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createReferenceErrorNotDefined(JSContext context, Object key, Node originatingNode) {
        return Errors.createReferenceError(Errors.quoteKey(context, key) + " is not defined", originatingNode);
    }

    private static String quoteKey(JSContext context, Object key) {
        return context.isOptionNashornCompatibilityMode() ? "\"" + key + "\"" : key.toString();
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotRedefineProperty(Object key) {
        assert (JSRuntime.isPropertyKey(key));
        return Errors.createTypeErrorFormat("Cannot redefine property: %s", key);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotSetProperty(int index, Object object, Node originatingNode) {
        return Errors.createTypeErrorCannotSetProperty(JSRuntime.safeToString(index), object, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotSetProperty(Object key, Object object, Node originatingNode) {
        JavaScriptLanguage language = JavaScriptLanguage.get(originatingNode);
        return Errors.createTypeErrorCannotSetProperty(JSRuntime.safeToString(key), object, originatingNode, language.getJSContext());
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotSetProperty(Object key, Object object, Node originatingNode, JSContext context) {
        assert (JSRuntime.isPropertyKey(key));
        String errorMessage = context.isOptionNashornCompatibilityMode() ? "Cannot set property \"" + key + "\" of " + JSRuntime.safeToString(object) : "Cannot set property '" + key + "' of " + JSRuntime.safeToString(object);
        return Errors.createTypeError(errorMessage, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotSetAccessorProperty(Object key, JSDynamicObject store) {
        assert (JSRuntime.isPropertyKey(key));
        JavaScriptLanguage language = JavaScriptLanguage.get(null);
        String message = language.getJSContext().isOptionNashornCompatibilityMode() ? "Cannot set property \"%s\" of %s that has only a getter" : "Cannot set property %s of %s which has only a getter";
        return Errors.createTypeErrorFormat(message, key, JSObject.defaultToString(store));
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotGetAccessorProperty(Object key, JSDynamicObject store, Node originatingNode) {
        assert (JSRuntime.isPropertyKey(key));
        return Errors.createTypeError(String.format("Cannot get property %s of %s which has only a setter", key, JSObject.defaultToString(store)), originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotGetProperty(JSContext context, Object key, Object object, boolean isGetMethod, Node originatingNode) {
        assert (JSRuntime.isPropertyKey(key));
        String errorMessage = context.isOptionNashornCompatibilityMode() ? (isGetMethod ? JSRuntime.safeToString(object) + " has no such function \"" + key + "\"" : (object == Null.instance ? "Cannot get property \"" + key + "\" of " + Null.NAME : "Cannot read property \"" + key + "\" from " + JSRuntime.safeToString(object))) : "Cannot read property '" + key + "' of " + JSRuntime.safeToString(object);
        return Errors.createTypeError(errorMessage, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotDeclareGlobalFunction(Object varName, Node originatingNode) {
        return Errors.createTypeError("Cannot declare global function '" + varName + "'", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeErrorCurrencyNotWellFormed(String currencyCode) {
        return Errors.createRangeError(String.format("Currency, %s, is not well formed.", currencyCode));
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeErrorInvalidUnitArgument(String functionName, Object unit) {
        return Errors.createRangeError(String.format("Invalid unit argument for %s() '%s'", functionName, unit));
    }

    public static JSException createRangeErrorInvalidLanguage(String language) {
        return Errors.createRangeErrorFormat("Invalid language subtag: %s", null, language);
    }

    public static JSException createRangeErrorInvalidRegion(String region) {
        return Errors.createRangeErrorFormat("Invalid region subtag: %s", null, region);
    }

    public static JSException createRangeErrorInvalidScript(String script) {
        return Errors.createRangeErrorFormat("Invalid script subtag: %s", null, script);
    }

    public static JSException createRangeErrorInvalidCalendar(String calendar) {
        return Errors.createRangeErrorFormat("Invalid calendar: %s", null, calendar);
    }

    public static JSException createRangeErrorInvalidDateTimeField(String dateTimeField) {
        return Errors.createRangeErrorFormat("Invalid date-time field: %s", null, dateTimeField);
    }

    public static JSException createRangeErrorInvalidUnitIdentifier(String unitIdentifier) {
        return Errors.createRangeErrorFormat("Invalid unit identifier: %s", null, unitIdentifier);
    }

    public static JSException createRangeErrorInvalidTimeValue() {
        return Errors.createRangeError("Invalid time value");
    }

    public static JSException createTypeErrorInvalidTimeValue() {
        return Errors.createTypeError("Invalid time value");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorMapExpected() {
        return Errors.createTypeError("Map expected");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorSetExpected() {
        return Errors.createTypeError("Set expected");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorSymbolExpected() {
        return Errors.createTypeError("Symbol expected");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorDetachedBuffer() {
        return Errors.createTypeError("Detached buffer");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorReadOnlyBuffer() {
        return Errors.createTypeError("Read-only buffer");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorArrayBufferExpected() {
        return Errors.createTypeError("ArrayBuffer expected");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorArrayBufferViewExpected() {
        return Errors.createTypeError("TypedArray expected");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCallableExpected() {
        return Errors.createTypeError("Callable expected");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorGeneratorObjectExpected() {
        return Errors.createTypeError("Not a generator object");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorAsyncGeneratorObjectExpected() {
        return Errors.createTypeError("Not an async generator object");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNotADataView() {
        return Errors.createTypeError("Not a DataView");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNotADate() {
        return Errors.createTypeError("not a Date object");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorFinalizationRegistryExpected() {
        return Errors.createTypeError("FinalizationRegistry expected");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNotANumber(Object value2) {
        return Errors.createTypeError(JSRuntime.safeToString(value2) + " is not a Number");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorGlobalObjectNotExtensible(Node originatingNode) {
        return Errors.createTypeError("Global object is not extensible", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeErrorTooManyArguments() {
        return Errors.createRangeError("Maximum call stack size exceeded");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeErrorBigIntMaxSizeExceeded() {
        return Errors.createRangeError("Maximum BigInt size exceeded");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeErrorStackOverflow() {
        return Errors.createRangeError("Maximum call stack size exceeded");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeErrorStackOverflow(Throwable cause, Node originatingNode) {
        return Errors.createRangeError("Maximum call stack size exceeded", cause, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeErrorInvalidStringLength() {
        return Errors.createRangeError("Invalid string length");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeErrorInvalidStringLength(Node originatingNode) {
        return Errors.createRangeError("Invalid string length", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeErrorInvalidArrayLength() {
        return Errors.createRangeError("Invalid array length");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeErrorIndexNegative(Node originatingNode) {
        return Errors.createRangeError("index is negative", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeErrorIndexTooLarge(Node originatingNode) {
        return Errors.createRangeError("index is too large", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeErrorInvalidBufferSize() {
        return Errors.createRangeError("Buffer too large");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeErrorInvalidBufferOffset() {
        return Errors.createRangeError("Invalid buffer offset");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeErrorInvalidTimeZone(TruffleString timeZoneName) {
        return Errors.createRangeError(String.format("Invalid time zone %s", timeZoneName));
    }

    public static RuntimeException unsupported(String message) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException(message);
    }

    public static RuntimeException notImplemented(String message) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new UnsupportedOperationException("not implemented: " + message);
    }

    public static RuntimeException shouldNotReachHere() {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new IllegalStateException("should not reach here");
    }

    public static RuntimeException shouldNotReachHere(String message) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new IllegalStateException("should not reach here: " + message);
    }

    public static RuntimeException shouldNotReachHere(Throwable exception) {
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new IllegalStateException("should not reach here", exception);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorConfigurableExpected() {
        return Errors.createTypeError("configurable expected");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorSameResultExpected() {
        return Errors.createTypeError("same result expected");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorYieldStarThrowMethodMissing(Node originatingNode) {
        return Errors.createTypeError("yield* protocol violation: iterator does not have a throw method", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotDeletePropertyOf(Object propertyKey, Object object) {
        assert (JSRuntime.isPropertyKey(propertyKey));
        return Errors.createTypeError("Cannot delete property " + JSRuntime.quote(propertyKey.toString()) + " of " + JSRuntime.safeToString(object));
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotDeletePropertyOfSealedArray(long index) {
        return Errors.createTypeErrorFormat("Cannot delete property \"%d\" of sealed array", index);
    }

    public static JSException createTypeErrorJSObjectExpected() {
        return Errors.createTypeError("only JavaScript objects are supported by this operation");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorTrapReturnedFalsish(Object trap, Object propertyKey) {
        return Errors.createTypeError("'" + trap + "' on proxy: trap returned falsish for property '" + propertyKey + "'");
    }

    public static JSException createTypeErrorOwnKeysTrapMissingKey(Object propertyKey) {
        return Errors.createTypeErrorFormat("'ownKeys' on proxy: trap result did not include '%s'", propertyKey);
    }

    public static JSException createTypeErrorProxyRevoked() {
        return Errors.createTypeError("proxy has been revoked");
    }

    public static JSException createTypeErrorProxyTargetNotExtensible() {
        return Errors.createTypeError("target is not extensible");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorProxyGetInvariantViolated(Object propertyKey, Object expectedValue, Object actualValue) {
        String propertyName = propertyKey.toString();
        TruffleString expected = JSRuntime.safeToString(expectedValue);
        TruffleString actual = JSRuntime.safeToString(actualValue);
        return Errors.createTypeError("'get' on proxy: property '" + propertyName + "' is a read-only and non-configurable data property on the proxy target but the proxy did not return its actual value (expected '" + expected + "' but got '" + actual + "')");
    }

    public static JSException createTypeErrorInteropException(Object receiver, InteropException cause, String message, Node originatingNode) {
        return Errors.createTypeErrorInteropException(receiver, cause, message, null, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorInteropException(Object receiver, InteropException cause, String message, Object messageDetails, Node originatingNode) {
        String reason = cause.getMessage();
        if (reason == null) {
            reason = cause.getClass().getSimpleName();
        }
        String receiverStr = Errors.toDisplayStringSafe(receiver);
        String messageTxt = messageDetails == null ? message : String.format("%s (%s)", message, messageDetails);
        return JSException.create(JSErrorType.TypeError, messageTxt + " on " + receiverStr + " failed due to: " + reason, cause, originatingNode);
    }

    private static String toDisplayStringSafe(Object receiver) {
        CompilerAsserts.neverPartOfCompilation();
        InteropLibrary interop = InteropLibrary.getUncached();
        try {
            return interop.asString(interop.toDisplayString(receiver, false));
        }
        catch (Exception e) {
            return "foreign object";
        }
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorUnboxException(Object receiver, InteropException cause, Node originatingNode) {
        return Errors.createTypeErrorInteropException(receiver, cause, "UNBOX", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorUnsupportedInteropType(Object value2) {
        return Errors.createTypeError("type " + value2.getClass().getSimpleName() + " not supported in JavaScript");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorNotATruffleObject(String message) {
        return Errors.createTypeError("cannot call " + message + " on a non-interop object");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorInvalidIdentifier(Object identifier) {
        return Errors.createTypeError("Invalid identifier: " + JSRuntime.safeToString(identifier));
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorClassNotFound(Object className) {
        return Errors.createTypeErrorFormat("Access to host class %s is not allowed or does not exist.", className);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createNotAFileError(String path) {
        return Errors.createTypeError("Not a file: " + path);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createErrorFromException(Throwable e) {
        return JSException.create(JSErrorType.Error, e.getMessage(), e, null);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createError(String message, Throwable e) {
        return JSException.create(JSErrorType.Error, message, e, null);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createICU4JDataError(Exception e) {
        return Errors.createError("ICU data not found. ICU4J library not properly configured. Set the system property com.ibm.icu.impl.ICUBinary.dataPath to your icudt path." + (String)(e.getMessage() != null && !e.getMessage().isEmpty() ? " (" + e.getMessage() + ")" : ""), e);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createEvalDisabled() {
        return Errors.createEvalError("dynamic evaluation of code is disabled.");
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorIteratorResultNotObject(Object value2, Node originatingNode) {
        return Errors.createTypeError("Iterator result " + JSRuntime.safeToString(value2) + " is not an object", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotGetPrivateMember(boolean fieldAccess, TruffleString name, Node originatingNode) {
        String message = fieldAccess ? String.format("Cannot read private member %s from an object whose class did not declare it.", name) : "Object must be an instance of class";
        return Errors.createTypeError(message, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotSetPrivateMember(Object name, Node originatingNode) {
        return Errors.createTypeError(String.format("Cannot write private member %s to an object whose class did not declare it.", name), originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorCannotAddPrivateMember(String name, Node originatingNode) {
        return Errors.createTypeError(String.format("Duplicate private member %s.", name), originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeError(Throwable cause, Node originatingNode) {
        return JSException.create(JSErrorType.TypeError, cause.getMessage(), cause, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeError(Throwable cause, Node originatingNode) {
        return JSException.create(JSErrorType.RangeError, cause.getMessage(), cause, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRangeError(String message, Throwable cause, Node originatingNode) {
        return JSException.create(JSErrorType.RangeError, message, cause, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createCompileError(String message, Node originatingNode) {
        return JSException.create(JSErrorType.CompileError, message, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createCompileError(Throwable cause, Node originatingNode) {
        return JSException.create(JSErrorType.CompileError, cause.getMessage(), cause, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createLinkError(String message) {
        return JSException.create(JSErrorType.LinkError, message);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createLinkError(Throwable cause, Node originatingNode) {
        return JSException.create(JSErrorType.LinkError, cause.getMessage(), cause, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createRuntimeError(Throwable cause, Node originatingNode) {
        return JSException.create(JSErrorType.RuntimeError, cause.getMessage(), cause, originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorWrongDecoratorReturn(Node originatingNode) {
        return Errors.createTypeError("Class decorator must return undefined or function", originatingNode);
    }

    @CompilerDirectives.TruffleBoundary
    public static JSException createTypeErrorIllegalAccessorTarget(Node originatingNode) {
        return Errors.createTypeError("Illegal accessor target", originatingNode);
    }
}

