package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
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
   public static JSException createRangeErrorFormat(String message, Node originatingNode, Object... args) {
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
   public static JSException createTypeErrorFormat(String message, Object... args) {
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
      return createTypeError("Cannot mix BigInt and other types, use explicit conversions.", originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createErrorCanNotConvertToBigInt(JSErrorType type, Object x) {
      return JSException.create(type, String.format("Cannot convert %s to a BigInt.", JSRuntime.safeToString(x)));
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotConvertBigIntToNumber(Node originatingNode) {
      return createTypeError("Cannot convert a BigInt value to a number.", originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNotAFunction(Object functionObj) {
      return createTypeErrorNotAFunction(functionObj, null);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNotAFunction(Object functionObj, Node originatingNode) {
      assert !JSFunction.isJSFunction(functionObj);

      return JSException.create(JSErrorType.TypeError, String.format("%s is not a function", JSRuntime.safeToString(functionObj)), originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNotAConstructor(Object object, JSContext context) {
      return createTypeErrorNotAConstructor(object, null, context);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNotAConstructor(Object object, Node originatingNode, JSContext context) {
      String msg = String.format(
         context.isOptionNashornCompatibilityMode() ? "%s is not a constructor function" : "%s is not a constructor", JSRuntime.safeToString(object)
      );
      return JSException.create(JSErrorType.TypeError, msg, originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorTypeXExpected(Object type) {
      return createTypeErrorFormat("%s object expected.", type);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCalledOnNonObject() {
      return createTypeError("called on non-object");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorMethodCalledOnNonObjectOrWrongType(String method) {
      return createTypeErrorFormat("Method %s called on a non-object or on a wrong type of object.", method);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorSegmenterExpected() {
      return createTypeError("Segmenter object expected.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorSegmentsExpected() {
      return createTypeError("Segments object expected.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorDisplayNamesExpected() {
      return createTypeError("DisplayNames object expected.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorLocaleExpected() {
      return createTypeError("Locale object expected.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNoOverloadFound(TruffleString operatorName, Node originatingNode) {
      return createTypeError("No overload found for " + Strings.toJavaString(operatorName), originatingNode);
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
   public static JSException createSyntaxErrorFormat(String message, Node originatingNode, Object... args) {
      return JSException.create(JSErrorType.SyntaxError, String.format(message, args), originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createSyntaxError(String message, SourceSection sourceLocation, boolean isIncompleteSource) {
      return JSException.create(JSErrorType.SyntaxError, message, sourceLocation, isIncompleteSource);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createSyntaxErrorVariableAlreadyDeclared(TruffleString varName, Node originatingNode) {
      return createSyntaxError("Variable \"" + varName + "\" has already been declared", originatingNode);
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
      return createReferenceError("Must call super constructor in derived class before accessing 'this' or returning from derived constructor", originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorDerivedConstructorReturnedIllegalType(Node originatingNode) {
      return createTypeError("Derived constructors may only return object or undefined", originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNotObjectCoercible(Object value, Node originatingNode) {
      JavaScriptLanguage language = JavaScriptLanguage.get(originatingNode);
      return createTypeErrorNotObjectCoercible(value, originatingNode, language.getJSContext());
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNotObjectCoercible(Object value, Node originatingNode, JSContext context) {
      return context.isOptionNashornCompatibilityMode()
         ? createTypeErrorNotAnObject(value, originatingNode)
         : createTypeError("Cannot convert undefined or null to object: " + JSRuntime.safeToString(value), originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNotAnObject(Object value) {
      return createTypeErrorNotAnObject(value, null);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNotAnObject(Object value, Node originatingNode) {
      return createTypeError(JSRuntime.safeToString(value) + " is not an Object", originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorIterResultNotAnObject(Object value, Node originatingNode) {
      return createTypeErrorNotAnObject(value, originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNotIterable(Object value, Node originatingNode) {
      return createTypeError(JSRuntime.safeToString(value) + " is not iterable", originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorInvalidPrototype(Object value) {
      return createTypeError("Object prototype may only be an Object or null: " + JSRuntime.safeToString(value));
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorInvalidInstanceofTarget(Object target, Node originatingNode) {
      if (JSRuntime.isForeignObject(target)) {
         return createTypeError("Right-hand-side of instanceof is not a meta object", originatingNode);
      } else if (!JSRuntime.isObject(target)) {
         return createTypeError("Right-hand-side of instanceof is not an object", originatingNode);
      } else {
         assert !JSRuntime.isCallable(target);

         return createTypeError("Right-hand-side of instanceof is not callable", originatingNode);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotConvertToPrimitiveValue() {
      return createTypeError("Cannot convert object to primitive value");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotConvertToPrimitiveValue(Node originatingNode) {
      return createTypeError("Cannot convert object to primitive value", originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotConvertToString(String what) {
      return createTypeErrorCannotConvertToString(what, null);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotConvertToString(String what, Node originatingNode) {
      return createTypeError("Cannot convert " + what + " to a string", originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotConvertToNumber(String what) {
      return createTypeErrorCannotConvertToNumber(what, null);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotConvertToNumber(String what, Node originatingNode) {
      return createTypeError("Cannot convert " + what + " to a number", originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorIncompatibleReceiver(TruffleString methodName, Object receiver) {
      return createTypeErrorIncompatibleReceiver(Strings.toJavaString(methodName), receiver);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorIncompatibleReceiver(String methodName, Object receiver) {
      return createTypeError("Method " + methodName + " called on incompatible receiver " + JSRuntime.safeToString(receiver));
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorIncompatibleReceiver(Object what) {
      return createTypeError("incompatible receiver: " + JSRuntime.safeToString(what));
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotSetProto(JSDynamicObject thisObj, JSDynamicObject proto) {
      if (!JSNonProxy.checkProtoCycle(thisObj, proto)) {
         return JSObject.getJSContext(thisObj).isOptionNashornCompatibilityMode()
            ? createTypeError("Cannot create__proto__ cycle for " + JSObject.defaultToString(thisObj))
            : createTypeError("Cyclic __proto__ value");
      } else {
         throw createTypeError("Cannot set __proto__ of non-extensible " + JSObject.defaultToString(thisObj));
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNotWritableProperty(Object key, Object thisObj, Node originatingNode) {
      JavaScriptLanguage language = JavaScriptLanguage.get(originatingNode);
      String message;
      if (language.getJSContext().isOptionNashornCompatibilityMode()) {
         message = keyToString(key) + " is not a writable property of " + JSRuntime.safeToString(thisObj);
      } else {
         message = "Cannot assign to read only property '" + key.toString() + "' of " + JSRuntime.safeToString(thisObj);
      }

      return createTypeError(message, originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNotWritableProperty(Object key, Object thisObj) {
      return createTypeErrorNotWritableProperty(key, thisObj, null);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNotWritableIndex(long index, Object thisObj, Node originatingNode) {
      return createTypeErrorNotWritableProperty(Strings.fromLong(index), thisObj, originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorLengthNotWritable() {
      return createTypeError("Cannot assign to read only property 'length'");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNotConfigurableProperty(Object key) {
      return JSException.create(JSErrorType.TypeError, keyToString(key) + " is not a configurable property");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNotExtensible(JSDynamicObject thisObj, Object key) {
      return createTypeError("Cannot add new property " + keyToString(key) + " to non-extensible " + JSObject.defaultToString(thisObj));
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorSetNonObjectReceiver(Object receiver, Object key) {
      return createTypeError("Cannot add property " + keyToString(key) + " to non-object " + JSRuntime.safeToString(receiver));
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorConstReassignment(Object key, Object thisObj, Node originatingNode) {
      if (JSDynamicObject.isJSDynamicObject(thisObj) && JSObject.getJSContext((JSDynamicObject)thisObj).isOptionV8CompatibilityMode()) {
         throw createTypeError("Assignment to constant variable.", originatingNode);
      } else {
         throw createTypeError("Assignment to constant \"" + key + "\"", originatingNode);
      }
   }

   private static String keyToString(Object key) {
      assert JSRuntime.isPropertyKey(key);

      return Strings.isTString(key) ? "\"" + Strings.toJavaString((TruffleString)key) + "\"" : key.toString();
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createReferenceErrorNotDefined(Object key, Node originatingNode) {
      JavaScriptLanguage language = JavaScriptLanguage.get(originatingNode);
      return createReferenceErrorNotDefined(language.getJSContext(), key, originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createReferenceErrorNotDefined(JSContext context, Object key, Node originatingNode) {
      return createReferenceError(quoteKey(context, key) + " is not defined", originatingNode);
   }

   private static String quoteKey(JSContext context, Object key) {
      return context.isOptionNashornCompatibilityMode() ? "\"" + key + "\"" : key.toString();
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotRedefineProperty(Object key) {
      assert JSRuntime.isPropertyKey(key);

      return createTypeErrorFormat("Cannot redefine property: %s", key);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotSetProperty(int index, Object object, Node originatingNode) {
      return createTypeErrorCannotSetProperty(JSRuntime.safeToString(index), object, originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotSetProperty(Object key, Object object, Node originatingNode) {
      JavaScriptLanguage language = JavaScriptLanguage.get(originatingNode);
      return createTypeErrorCannotSetProperty(JSRuntime.safeToString(key), object, originatingNode, language.getJSContext());
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotSetProperty(Object key, Object object, Node originatingNode, JSContext context) {
      assert JSRuntime.isPropertyKey(key);

      String errorMessage;
      if (context.isOptionNashornCompatibilityMode()) {
         errorMessage = "Cannot set property \"" + key + "\" of " + JSRuntime.safeToString(object);
      } else {
         errorMessage = "Cannot set property '" + key + "' of " + JSRuntime.safeToString(object);
      }

      return createTypeError(errorMessage, originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotSetAccessorProperty(Object key, JSDynamicObject store) {
      assert JSRuntime.isPropertyKey(key);

      JavaScriptLanguage language = JavaScriptLanguage.get(null);
      String message = language.getJSContext().isOptionNashornCompatibilityMode()
         ? "Cannot set property \"%s\" of %s that has only a getter"
         : "Cannot set property %s of %s which has only a getter";
      return createTypeErrorFormat(message, key, JSObject.defaultToString(store));
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotGetAccessorProperty(Object key, JSDynamicObject store, Node originatingNode) {
      assert JSRuntime.isPropertyKey(key);

      return createTypeError(String.format("Cannot get property %s of %s which has only a setter", key, JSObject.defaultToString(store)), originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotGetProperty(JSContext context, Object key, Object object, boolean isGetMethod, Node originatingNode) {
      assert JSRuntime.isPropertyKey(key);

      String errorMessage;
      if (context.isOptionNashornCompatibilityMode()) {
         if (isGetMethod) {
            errorMessage = JSRuntime.safeToString(object) + " has no such function \"" + key + "\"";
         } else if (object == Null.instance) {
            errorMessage = "Cannot get property \"" + key + "\" of " + Null.NAME;
         } else {
            errorMessage = "Cannot read property \"" + key + "\" from " + JSRuntime.safeToString(object);
         }
      } else {
         errorMessage = "Cannot read property '" + key + "' of " + JSRuntime.safeToString(object);
      }

      return createTypeError(errorMessage, originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotDeclareGlobalFunction(Object varName, Node originatingNode) {
      return createTypeError("Cannot declare global function '" + varName + "'", originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorCurrencyNotWellFormed(String currencyCode) {
      return createRangeError(String.format("Currency, %s, is not well formed.", currencyCode));
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorInvalidUnitArgument(String functionName, Object unit) {
      return createRangeError(String.format("Invalid unit argument for %s() '%s'", functionName, unit));
   }

   public static JSException createRangeErrorInvalidLanguage(String language) {
      return createRangeErrorFormat("Invalid language subtag: %s", null, language);
   }

   public static JSException createRangeErrorInvalidRegion(String region) {
      return createRangeErrorFormat("Invalid region subtag: %s", null, region);
   }

   public static JSException createRangeErrorInvalidScript(String script) {
      return createRangeErrorFormat("Invalid script subtag: %s", null, script);
   }

   public static JSException createRangeErrorInvalidCalendar(String calendar) {
      return createRangeErrorFormat("Invalid calendar: %s", null, calendar);
   }

   public static JSException createRangeErrorInvalidDateTimeField(String dateTimeField) {
      return createRangeErrorFormat("Invalid date-time field: %s", null, dateTimeField);
   }

   public static JSException createRangeErrorInvalidUnitIdentifier(String unitIdentifier) {
      return createRangeErrorFormat("Invalid unit identifier: %s", null, unitIdentifier);
   }

   public static JSException createRangeErrorInvalidTimeValue() {
      return createRangeError("Invalid time value");
   }

   public static JSException createTypeErrorInvalidTimeValue() {
      return createTypeError("Invalid time value");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorMapExpected() {
      return createTypeError("Map expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorSetExpected() {
      return createTypeError("Set expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorSymbolExpected() {
      return createTypeError("Symbol expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorDetachedBuffer() {
      return createTypeError("Detached buffer");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorReadOnlyBuffer() {
      return createTypeError("Read-only buffer");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorArrayBufferExpected() {
      return createTypeError("ArrayBuffer expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorArrayBufferViewExpected() {
      return createTypeError("TypedArray expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCallableExpected() {
      return createTypeError("Callable expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorGeneratorObjectExpected() {
      return createTypeError("Not a generator object");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorAsyncGeneratorObjectExpected() {
      return createTypeError("Not an async generator object");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNotADataView() {
      return createTypeError("Not a DataView");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNotADate() {
      return createTypeError("not a Date object");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorFinalizationRegistryExpected() {
      return createTypeError("FinalizationRegistry expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNotANumber(Object value) {
      return createTypeError(JSRuntime.safeToString(value) + " is not a Number");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorGlobalObjectNotExtensible(Node originatingNode) {
      return createTypeError("Global object is not extensible", originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorTooManyArguments() {
      return createRangeError("Maximum call stack size exceeded");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorBigIntMaxSizeExceeded() {
      return createRangeError("Maximum BigInt size exceeded");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorStackOverflow() {
      return createRangeError("Maximum call stack size exceeded");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorStackOverflow(Throwable cause, Node originatingNode) {
      return createRangeError("Maximum call stack size exceeded", cause, originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorInvalidStringLength() {
      return createRangeError("Invalid string length");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorInvalidStringLength(Node originatingNode) {
      return createRangeError("Invalid string length", originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorInvalidArrayLength() {
      return createRangeError("Invalid array length");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorIndexNegative(Node originatingNode) {
      return createRangeError("index is negative", originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorIndexTooLarge(Node originatingNode) {
      return createRangeError("index is too large", originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorInvalidBufferSize() {
      return createRangeError("Buffer too large");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorInvalidBufferOffset() {
      return createRangeError("Invalid buffer offset");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createRangeErrorInvalidTimeZone(TruffleString timeZoneName) {
      return createRangeError(String.format("Invalid time zone %s", timeZoneName));
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
      return createTypeError("configurable expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorSameResultExpected() {
      return createTypeError("same result expected");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorYieldStarThrowMethodMissing(Node originatingNode) {
      return createTypeError("yield* protocol violation: iterator does not have a throw method", originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotDeletePropertyOf(Object propertyKey, Object object) {
      assert JSRuntime.isPropertyKey(propertyKey);

      return createTypeError("Cannot delete property " + JSRuntime.quote(propertyKey.toString()) + " of " + JSRuntime.safeToString(object));
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotDeletePropertyOfSealedArray(long index) {
      return createTypeErrorFormat("Cannot delete property \"%d\" of sealed array", index);
   }

   public static JSException createTypeErrorJSObjectExpected() {
      return createTypeError("only JavaScript objects are supported by this operation");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorTrapReturnedFalsish(Object trap, Object propertyKey) {
      return createTypeError("'" + trap + "' on proxy: trap returned falsish for property '" + propertyKey + "'");
   }

   public static JSException createTypeErrorOwnKeysTrapMissingKey(Object propertyKey) {
      return createTypeErrorFormat("'ownKeys' on proxy: trap result did not include '%s'", propertyKey);
   }

   public static JSException createTypeErrorProxyRevoked() {
      return createTypeError("proxy has been revoked");
   }

   public static JSException createTypeErrorProxyTargetNotExtensible() {
      return createTypeError("target is not extensible");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorProxyGetInvariantViolated(Object propertyKey, Object expectedValue, Object actualValue) {
      String propertyName = propertyKey.toString();
      Object expected = JSRuntime.safeToString(expectedValue);
      Object actual = JSRuntime.safeToString(actualValue);
      return createTypeError(
         "'get' on proxy: property '"
            + propertyName
            + "' is a read-only and non-configurable data property on the proxy target but the proxy did not return its actual value (expected '"
            + expected
            + "' but got '"
            + actual
            + "')"
      );
   }

   public static JSException createTypeErrorInteropException(Object receiver, InteropException cause, String message, Node originatingNode) {
      return createTypeErrorInteropException(receiver, cause, message, null, originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorInteropException(
      Object receiver, InteropException cause, String message, Object messageDetails, Node originatingNode
   ) {
      String reason = cause.getMessage();
      if (reason == null) {
         reason = cause.getClass().getSimpleName();
      }

      String receiverStr = toDisplayStringSafe(receiver);
      String messageTxt = messageDetails == null ? message : String.format("%s (%s)", message, messageDetails);
      return JSException.create(JSErrorType.TypeError, messageTxt + " on " + receiverStr + " failed due to: " + reason, cause, originatingNode);
   }

   private static String toDisplayStringSafe(Object receiver) {
      CompilerAsserts.neverPartOfCompilation();
      InteropLibrary interop = InteropLibrary.getUncached();

      try {
         return interop.asString(interop.toDisplayString(receiver, false));
      } catch (Exception var3) {
         return "foreign object";
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorUnboxException(Object receiver, InteropException cause, Node originatingNode) {
      return createTypeErrorInteropException(receiver, cause, "UNBOX", originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorUnsupportedInteropType(Object value) {
      return createTypeError("type " + value.getClass().getSimpleName() + " not supported in JavaScript");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorNotATruffleObject(String message) {
      return createTypeError("cannot call " + message + " on a non-interop object");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorInvalidIdentifier(Object identifier) {
      return createTypeError("Invalid identifier: " + JSRuntime.safeToString(identifier));
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorClassNotFound(Object className) {
      return createTypeErrorFormat("Access to host class %s is not allowed or does not exist.", className);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createNotAFileError(String path) {
      return createTypeError("Not a file: " + path);
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
      return createError(
         "ICU data not found. ICU4J library not properly configured. Set the system property com.ibm.icu.impl.ICUBinary.dataPath to your icudt path."
            + (e.getMessage() != null && !e.getMessage().isEmpty() ? " (" + e.getMessage() + ")" : ""),
         e
      );
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createEvalDisabled() {
      return createEvalError("dynamic evaluation of code is disabled.");
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorIteratorResultNotObject(Object value, Node originatingNode) {
      return createTypeError("Iterator result " + JSRuntime.safeToString(value) + " is not an object", originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotGetPrivateMember(boolean fieldAccess, TruffleString name, Node originatingNode) {
      String message;
      if (fieldAccess) {
         message = String.format("Cannot read private member %s from an object whose class did not declare it.", name);
      } else {
         message = "Object must be an instance of class";
      }

      return createTypeError(message, originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotSetPrivateMember(Object name, Node originatingNode) {
      return createTypeError(String.format("Cannot write private member %s to an object whose class did not declare it.", name), originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorCannotAddPrivateMember(String name, Node originatingNode) {
      return createTypeError(String.format("Duplicate private member %s.", name), originatingNode);
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
      return createTypeError("Class decorator must return undefined or function", originatingNode);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createTypeErrorIllegalAccessorTarget(Node originatingNode) {
      return createTypeError("Illegal accessor target", originatingNode);
   }
}
