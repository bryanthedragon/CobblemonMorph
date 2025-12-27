package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.builtins.CallSitePrototypeBuiltins;
import com.oracle.truffle.js.builtins.ConstructorBuiltins;
import com.oracle.truffle.js.builtins.ErrorFunctionBuiltins;
import com.oracle.truffle.js.builtins.ErrorPrototypeBuiltins;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSContextOptions;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.PrepareStackTraceCallback;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.PropertyProxy;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Objects;

public final class JSError extends JSNonProxy {
   public static final TruffleString MESSAGE = Strings.constant("message");
   public static final int MESSAGE_ATTRIBUTES = JSAttributes.getDefaultNotEnumerable();
   public static final TruffleString NAME = Strings.constant("name");
   public static final TruffleString CLASS_NAME = Strings.constant("Error");
   public static final TruffleString PROTOTYPE_NAME = Strings.constant("Error.prototype");
   public static final HiddenKey EXCEPTION_PROPERTY_NAME = new HiddenKey("Exception");
   public static final TruffleString STACK_NAME = Strings.constant("stack");
   public static final HiddenKey FORMATTED_STACK_NAME = new HiddenKey("FormattedStack");
   public static final TruffleString ERRORS_NAME = Strings.constant("errors");
   public static final int ERRORS_ATTRIBUTES = JSAttributes.getDefaultNotEnumerable();
   public static final TruffleString PREPARE_STACK_TRACE_NAME = Strings.constant("prepareStackTrace");
   public static final TruffleString LINE_NUMBER_PROPERTY_NAME = Strings.constant("lineNumber");
   public static final TruffleString COLUMN_NUMBER_PROPERTY_NAME = Strings.constant("columnNumber");
   public static final int DEFAULT_COLUMN_NUMBER = -1;
   public static final TruffleString STACK_TRACE_LIMIT_PROPERTY_NAME = Strings.constant("stackTraceLimit");
   public static final TruffleString ANONYMOUS_FUNCTION_NAME_NASHORN = Strings.constant("<program>");
   public static final TruffleString ANONYMOUS_FUNCTION_NAME = Strings.constant("<anonymous>");
   public static final TruffleString TAB_AT = Strings.constant("\tat ");
   public static final TruffleString SPACES_AT = Strings.constant("    at ");
   public static final JSError INSTANCE = new JSError();
   private static final TruffleString CALL_SITE_CLASS_NAME = Strings.constant("CallSite");
   public static final TruffleString CALL_SITE_PROTOTYPE_NAME = Strings.constant("CallSite.prototype");
   public static final HiddenKey STACK_TRACE_ELEMENT_PROPERTY_NAME = new HiddenKey("StackTraceElement");
   public static final PropertyProxy STACK_PROXY = new PropertyProxy() {
      @Override
      public Object get(JSDynamicObject store) {
         Object value = JSObjectUtil.getHiddenProperty(store, JSError.FORMATTED_STACK_NAME);
         if (value == null) {
            GraalJSException truffleException = JSError.getException(store);
            if (truffleException == null) {
               value = Undefined.instance;
            } else {
               JSRealm realm = JSRealm.get(null);
               value = JSError.prepareStack(realm, store, truffleException);
            }

            Object currentValue = JSObjectUtil.getHiddenProperty(store, JSError.FORMATTED_STACK_NAME);
            if (currentValue == null) {
               JSObjectUtil.putHiddenProperty(store, JSError.FORMATTED_STACK_NAME, value);
            } else {
               value = currentValue;
            }
         }

         return value;
      }

      @Override
      public boolean set(JSDynamicObject store, Object value) {
         JSObjectUtil.putHiddenProperty(store, JSError.FORMATTED_STACK_NAME, value);
         return true;
      }
   };

   private JSError() {
   }

   public static JSErrorObject createErrorObject(JSContext context, JSRealm realm, JSErrorType errorType) {
      JSObjectFactory factory = context.getErrorFactory(errorType);
      JSErrorObject obj = JSErrorObject.create(realm, factory);
      factory.initProto(obj, realm);
      return context.trackAllocation(obj);
   }

   public static void setMessage(JSDynamicObject obj, TruffleString message) {
      assert !JSDynamicObject.hasProperty(obj, MESSAGE);

      JSObjectUtil.putDataProperty(obj, MESSAGE, message, MESSAGE_ATTRIBUTES);
   }

   public static JSErrorObject create(JSErrorType errorType, JSRealm realm, Object message) {
      assert Strings.isTString(message) || message == Undefined.instance;

      JSErrorObject obj = createErrorObject(realm.getContext(), realm, errorType);
      String msg;
      if (message == Undefined.instance) {
         msg = null;
      } else {
         assert Strings.isTString(message);

         setMessage(obj, (TruffleString)message);
         msg = Strings.toJavaString((TruffleString)message);
      }

      setException(realm, obj, JSException.createCapture(errorType, msg, obj, realm), false);
      return obj;
   }

   public static JSErrorObject createFromJSException(JSException exception, JSRealm realm, String message) {
      Objects.requireNonNull(message);
      JSContext context = realm.getContext();
      JSErrorType errorType = exception.getErrorType();
      JSErrorObject obj = createErrorObject(context, realm, errorType);
      setMessage(obj, Strings.fromJavaString(message));
      setException(realm, obj, exception, context.isOptionNashornCompatibilityMode());
      return obj;
   }

   @CompilerDirectives.TruffleBoundary
   public static JSErrorObject createAggregateError(JSRealm realm, Object errors, String msg) {
      JSErrorObject errorObj = createErrorObject(realm.getContext(), realm, JSErrorType.AggregateError);
      if (msg != null) {
         setMessage(errorObj, Strings.fromJavaString(msg));
      }

      JSObjectUtil.putDataProperty(errorObj, ERRORS_NAME, errors, ERRORS_ATTRIBUTES);
      setException(realm, errorObj, JSException.createCapture(JSErrorType.AggregateError, msg, errorObj, realm), false);
      return errorObj;
   }

   private static JSDynamicObject createErrorPrototype(JSRealm realm, JSErrorType errorType) {
      JSContext ctx = realm.getContext();
      JSDynamicObject proto = errorType == JSErrorType.Error ? realm.getObjectPrototype() : realm.getErrorPrototype(JSErrorType.Error);
      JSObject errorPrototype;
      if (ctx.getEcmaScriptVersion() < 6) {
         errorPrototype = JSErrorObject.create(JSShape.createPrototypeShape(ctx, INSTANCE, proto));
         JSObjectUtil.setOrVerifyPrototype(ctx, errorPrototype, proto);
      } else {
         errorPrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm, proto);
      }

      JSObjectUtil.putDataProperty(ctx, errorPrototype, MESSAGE, Strings.EMPTY_STRING, MESSAGE_ATTRIBUTES);
      if (errorType == JSErrorType.Error) {
         JSObjectUtil.putFunctionsFromContainer(realm, errorPrototype, ErrorPrototypeBuiltins.BUILTINS);
         if (ctx.isOptionNashornCompatibilityMode()) {
            JSObjectUtil.putFunctionsFromContainer(realm, errorPrototype, ErrorPrototypeBuiltins.ErrorPrototypeNashornCompatBuiltins.BUILTINS);
         }
      }

      return errorPrototype;
   }

   public static JSConstructor createErrorConstructor(JSRealm realm, JSErrorType errorType) {
      JSContext context = realm.getContext();
      TruffleString name = Strings.fromJavaString(errorType.toString());
      JSFunctionObject errorConstructor = realm.lookupFunction(ConstructorBuiltins.BUILTINS, name);
      JSDynamicObject classPrototype = createErrorPrototype(realm, errorType);
      if (errorType != JSErrorType.Error) {
         JSObject.setPrototype(errorConstructor, realm.getErrorConstructor(JSErrorType.Error));
      }

      JSObjectUtil.putConstructorProperty(context, classPrototype, errorConstructor);
      JSObjectUtil.putDataProperty(context, classPrototype, NAME, name, MESSAGE_ATTRIBUTES);
      JSObjectUtil.putConstructorPrototypeProperty(context, errorConstructor, classPrototype);
      if (errorType == JSErrorType.Error) {
         JSObjectUtil.putFunctionsFromContainer(realm, errorConstructor, ErrorFunctionBuiltins.BUILTINS);
         JSObjectUtil.putDataProperty(
            context,
            errorConstructor,
            STACK_TRACE_LIMIT_PROPERTY_NAME,
            JSContextOptions.STACK_TRACE_LIMIT.getValue(realm.getOptions()),
            JSAttributes.getDefault()
         );
      }

      return new JSConstructor(errorConstructor, classPrototype);
   }

   @Override
   public Shape makeInitialShape(JSContext context, JSDynamicObject errorPrototype) {
      return JSObjectUtil.getProtoChildShape(errorPrototype, INSTANCE, context);
   }

   private static JSDynamicObject createCallSitePrototype(JSRealm realm) {
      JSDynamicObject callSitePrototype = JSObjectUtil.createOrdinaryPrototypeObject(realm);
      JSObjectUtil.putFunctionsFromContainer(realm, callSitePrototype, CallSitePrototypeBuiltins.BUILTINS);
      return callSitePrototype;
   }

   public static JSConstructor createCallSiteConstructor(JSRealm realm) {
      JSContext context = realm.getContext();
      JSFunctionObject constructor = JSFunction.createNamedEmptyFunction(realm, CALL_SITE_CLASS_NAME);
      JSDynamicObject prototype = createCallSitePrototype(realm);
      JSObjectUtil.putConstructorProperty(context, prototype, constructor);
      JSObjectUtil.putConstructorPrototypeProperty(context, constructor, prototype);
      return new JSConstructor(constructor, prototype);
   }

   public static Shape makeInitialCallSiteShape(JSContext context, JSDynamicObject callSitePrototype) {
      return JSObjectUtil.getProtoChildShape(callSitePrototype, JSOrdinary.INSTANCE, context);
   }

   public static void setLineNumber(JSContext context, JSDynamicObject errorObj, Object lineNumber) {
      setErrorProperty(context, errorObj, LINE_NUMBER_PROPERTY_NAME, lineNumber);
   }

   public static void setColumnNumber(JSContext context, JSDynamicObject errorObj, Object columnNumber) {
      setErrorProperty(context, errorObj, COLUMN_NUMBER_PROPERTY_NAME, columnNumber);
   }

   public static GraalJSException getException(JSDynamicObject errorObj) {
      Object exception = JSDynamicObject.getOrNull(errorObj, EXCEPTION_PROPERTY_NAME);
      return exception instanceof GraalJSException ? (GraalJSException)exception : null;
   }

   @CompilerDirectives.TruffleBoundary
   public static JSDynamicObject setException(JSRealm realm, JSDynamicObject errorObj, GraalJSException exception, boolean defaultColumnNumber) {
      assert isJSError(errorObj);

      defineStackProperty(realm, errorObj, exception);
      JSContext context = realm.getContext();
      if (context.isOptionNashornCompatibilityMode() && exception.getJSStackTrace().length > 0) {
         GraalJSException.JSStackTraceElement topStackTraceElement = exception.getJSStackTrace()[0];
         setLineNumber(context, errorObj, topStackTraceElement.getLineNumber());
         setColumnNumber(context, errorObj, defaultColumnNumber ? -1 : topStackTraceElement.getColumnNumber());
      }

      return errorObj;
   }

   private static void setErrorProperty(JSContext context, JSDynamicObject errorObj, Object key, Object value) {
      JSObjectUtil.defineDataProperty(context, errorObj, key, value, JSAttributes.getDefaultNotEnumerable());
   }

   private static void defineStackProperty(JSRealm realm, JSDynamicObject errorObj, GraalJSException exception) {
      JSContext context = realm.getContext();
      setErrorProperty(context, errorObj, EXCEPTION_PROPERTY_NAME, exception);
      JSObjectUtil.putHiddenProperty(errorObj, FORMATTED_STACK_NAME, null);
      JSObjectUtil.defineProxyProperty(errorObj, STACK_NAME, STACK_PROXY, MESSAGE_ATTRIBUTES | 16);
   }

   @CompilerDirectives.TruffleBoundary
   public static Object prepareStack(JSRealm realm, JSDynamicObject errorObj, GraalJSException exception) {
      GraalJSException.JSStackTraceElement[] stackTrace = exception.getJSStackTrace();
      if (realm.isPreparingStackTrace()) {
         return formatStackTrace(stackTrace, errorObj, realm);
      } else {
         Object var5;
         try {
            realm.setPreparingStackTrace(true);
            PrepareStackTraceCallback prepareStackTraceCallback = realm.getContext().getPrepareStackTraceCallback();
            if (prepareStackTraceCallback != null) {
               return prepareStackTraceWithCallback(realm, prepareStackTraceCallback, errorObj, stackTrace);
            }

            var5 = prepareStackNoCallback(realm, errorObj, stackTrace);
         } finally {
            realm.setPreparingStackTrace(false);
         }

         return var5;
      }
   }

   @CompilerDirectives.TruffleBoundary
   public static Object prepareStackNoCallback(JSRealm realm, JSDynamicObject errorObj, GraalJSException.JSStackTraceElement[] jsStackTrace) {
      JSDynamicObject error = realm.getErrorConstructor(JSErrorType.Error);
      Object prepareStackTrace = JSObject.get(error, PREPARE_STACK_TRACE_NAME);
      return JSFunction.isJSFunction(prepareStackTrace)
         ? prepareStackWithUserFunction(realm, (JSFunctionObject)prepareStackTrace, errorObj, jsStackTrace)
         : formatStackTrace(jsStackTrace, errorObj, realm);
   }

   @CompilerDirectives.TruffleBoundary
   private static Object prepareStackTraceWithCallback(
      JSRealm realm, PrepareStackTraceCallback callback, JSDynamicObject errorObj, GraalJSException.JSStackTraceElement[] stackTrace
   ) {
      try {
         return callback.prepareStackTrace(realm, errorObj, toStructuredStackTrace(realm, stackTrace));
      } catch (Exception var5) {
         return formatStackTrace(stackTrace, errorObj, realm);
      }
   }

   private static Object prepareStackWithUserFunction(
      JSRealm realm, JSFunctionObject prepareStackTraceFun, JSDynamicObject errorObj, GraalJSException.JSStackTraceElement[] stackTrace
   ) {
      return JSFunction.call(prepareStackTraceFun, errorObj, new Object[]{errorObj, toStructuredStackTrace(realm, stackTrace)});
   }

   private static JSDynamicObject toStructuredStackTrace(JSRealm realm, GraalJSException.JSStackTraceElement[] stackTrace) {
      Object[] elements = new Object[stackTrace.length];

      for (int i = 0; i < stackTrace.length; i++) {
         elements[i] = prepareStackElement(realm, stackTrace[i]);
      }

      return JSArray.createConstant(realm.getContext(), realm, elements);
   }

   private static Object prepareStackElement(JSRealm realm, GraalJSException.JSStackTraceElement stackTraceElement) {
      JSContext context = realm.getContext();
      JSDynamicObject callSite = JSOrdinary.createWithRealm(context, context.getCallSiteFactory(), realm);
      JSObjectUtil.putHiddenProperty(callSite, STACK_TRACE_ELEMENT_PROPERTY_NAME, stackTraceElement);
      return callSite;
   }

   private static TruffleString getMessage(JSDynamicObject errorObj) {
      Object message = JSObject.get(errorObj, MESSAGE);
      return message == Undefined.instance ? null : JSRuntime.toString(message);
   }

   private static TruffleString getName(JSDynamicObject errorObj) {
      Object name = JSObject.get(errorObj, NAME);
      return name == Undefined.instance ? null : JSRuntime.toString(name);
   }

   private static boolean isInstanceOfJSError(JSDynamicObject errorObj, JSRealm realm) {
      JSDynamicObject errorPrototype = realm.getErrorPrototype(JSErrorType.Error);
      return JSRuntime.isPrototypeOf(errorObj, errorPrototype);
   }

   @CompilerDirectives.TruffleBoundary
   private static TruffleString formatStackTrace(GraalJSException.JSStackTraceElement[] stackTrace, JSDynamicObject errObj, JSRealm realm) {
      TruffleStringBuilder builder = Strings.builderCreate();
      if (realm.getContext().isOptionNashornCompatibilityMode() && !isInstanceOfJSError(errObj, realm)) {
         Strings.builderAppend(builder, JSObject.defaultToString(errObj));
      } else {
         TruffleString name = getName(errObj);
         TruffleString message = getMessage(errObj);
         if (name != null) {
            Strings.builderAppend(builder, name);
         } else {
            Strings.builderAppend(builder, Strings.UC_ERROR);
         }

         if (message != null && !message.isEmpty()) {
            if (Strings.builderLength(builder) != 0) {
               Strings.builderAppend(builder, Strings.COLON_SPACE);
            }

            Strings.builderAppend(builder, message);
         }
      }

      formatStackTraceIntl(stackTrace, builder, realm.getContext());
      return Strings.builderToString(builder);
   }

   private static void formatStackTraceIntl(GraalJSException.JSStackTraceElement[] stackTrace, TruffleStringBuilder builder, JSContext context) {
      boolean nashornCompatibilityMode = context.isOptionNashornCompatibilityMode();

      for (GraalJSException.JSStackTraceElement elem : stackTrace) {
         Strings.builderAppend(builder, Strings.LINE_SEPARATOR);
         Strings.builderAppend(builder, nashornCompatibilityMode ? TAB_AT : SPACES_AT);
         if (!nashornCompatibilityMode) {
            Strings.builderAppend(builder, elem.toString(context));
         } else {
            TruffleString methodName = correctMethodName(elem.getFunctionName(), context);
            Strings.builderAppend(builder, methodName);
            Strings.builderAppend(builder, Strings.SPACE_PAREN_OPEN);
            TruffleString fileName = elem.getFileName();
            if (Strings.equals(JSFunction.TS_BUILTIN_SOURCE_NAME, fileName)) {
               Strings.builderAppend(builder, Strings.NATIVE);
            } else {
               Strings.builderAppend(builder, fileName);
               Strings.builderAppend(builder, Strings.COLON);
               Strings.builderAppend(builder, elem.getLineNumber());
            }

            Strings.builderAppend(builder, Strings.PAREN_CLOSE);
         }
      }
   }

   public static TruffleString correctMethodName(TruffleString methodName, JSContext context) {
      if (methodName == null) {
         return Strings.EMPTY_STRING;
      } else if (Strings.isEmpty(methodName)) {
         return getAnonymousFunctionNameStackTrace(context);
      } else {
         if (Strings.endsWith(methodName, Strings.BRACKET_CLOSE)) {
            int idx = Strings.lastIndexOf(methodName, 91);
            if (idx >= 0) {
               return Strings.substring(context, methodName, idx);
            }
         }

         int idx = Strings.lastIndexOf(methodName, 46);
         return idx >= 0 ? Strings.substring(context, methodName, idx + 1) : methodName;
      }
   }

   @Override
   public TruffleString getClassName(JSDynamicObject object) {
      return CLASS_NAME;
   }

   @Override
   public TruffleString getBuiltinToStringTag(JSDynamicObject object) {
      return this.getClassName(object);
   }

   public static boolean isJSError(Object obj) {
      return obj instanceof JSErrorObject;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public TruffleString toDisplayStringImpl(JSDynamicObject obj, boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      if (JavaScriptLanguage.get(null).getJSContext().isOptionNashornCompatibilityMode()) {
         return super.toDisplayStringImpl(obj, allowSideEffects, format, depth);
      } else {
         Object name = getPropertyWithoutSideEffect(obj, NAME);
         Object message = getPropertyWithoutSideEffect(obj, MESSAGE);
         TruffleString nameStr = name != null
            ? JSRuntime.toDisplayStringImpl(name, allowSideEffects, ToDisplayStringFormat.getDefaultFormat(), depth + 1, obj)
            : CLASS_NAME;
         TruffleString messageStr = message != null
            ? JSRuntime.toDisplayStringImpl(message, allowSideEffects, ToDisplayStringFormat.getDefaultFormat(), depth + 1, obj)
            : Strings.EMPTY_STRING;
         if (nameStr.isEmpty()) {
            return messageStr.isEmpty() ? CLASS_NAME : messageStr;
         } else {
            return Strings.isEmpty(messageStr) ? nameStr : Strings.concatAll(nameStr, Strings.COLON_SPACE, messageStr);
         }
      }
   }

   private static Object getPropertyWithoutSideEffect(JSDynamicObject obj, Object key) {
      assert JSRuntime.isPropertyKey(key);

      Object value = JSDynamicObject.getOrNull(obj, key);
      if (value == null) {
         return !JSProxy.isJSProxy(obj) ? getPropertyWithoutSideEffect(JSObject.getPrototype(obj), key) : null;
      } else if (value instanceof Accessor) {
         return "{Accessor}";
      } else {
         return value instanceof PropertyProxy ? null : value;
      }
   }

   @Override
   public boolean hasOnlyShapeProperties(JSDynamicObject obj) {
      return true;
   }

   public static TruffleString getAnonymousFunctionNameStackTrace(JSContext context) {
      return context.isOptionNashornCompatibilityMode() ? ANONYMOUS_FUNCTION_NAME_NASHORN : ANONYMOUS_FUNCTION_NAME;
   }
}
