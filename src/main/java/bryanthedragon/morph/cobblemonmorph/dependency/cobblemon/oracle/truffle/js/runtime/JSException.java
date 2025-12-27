package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.ExceptionType;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import com.oracle.truffle.js.runtime.objects.Undefined;
import java.util.Objects;

@ExportLibrary(InteropLibrary.class)
@ImportStatic(JSConfig.class)
public final class JSException extends GraalJSException {
   private static final long serialVersionUID = -2139936643139844157L;
   private final JSErrorType type;
   private JSDynamicObject exceptionObj;
   private final JSRealm realm;
   private final boolean isIncompleteSource;

   private JSException(JSErrorType type, String message, Throwable cause, Node originatingNode, JSRealm realm, int stackTraceLimit) {
      super(message, cause, originatingNode, stackTraceLimit);
      CompilerAsserts.neverPartOfCompilation("JSException constructor");
      this.type = type;
      this.exceptionObj = null;
      this.realm = Objects.requireNonNull(realm);
      this.isIncompleteSource = false;
   }

   private JSException(JSErrorType type, String message, Node originatingNode, JSDynamicObject exceptionObj, JSRealm realm, int stackTraceLimit) {
      super(message, originatingNode, stackTraceLimit);
      CompilerAsserts.neverPartOfCompilation("JSException constructor");
      this.type = type;
      this.exceptionObj = exceptionObj;
      this.realm = Objects.requireNonNull(realm);
      this.isIncompleteSource = false;
   }

   private JSException(JSErrorType type, String message, SourceSection sourceLocation, JSRealm realm, int stackTraceLimit, boolean isIncompleteSource) {
      super(message, sourceLocation, stackTraceLimit);
      CompilerAsserts.neverPartOfCompilation("JSException constructor");
      this.type = type;
      this.exceptionObj = null;
      this.realm = Objects.requireNonNull(realm);
      this.isIncompleteSource = isIncompleteSource;
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createCapture(
      JSErrorType type, String message, JSDynamicObject exceptionObj, JSRealm realm, int stackTraceLimit, JSDynamicObject skipFramesUpTo, boolean customSkip
   ) {
      return fillInStackTrace(new JSException(type, message, null, exceptionObj, realm, stackTraceLimit), true, skipFramesUpTo, customSkip);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException createCapture(JSErrorType type, String message, JSDynamicObject exceptionObj, JSRealm realm) {
      return createCapture(type, message, exceptionObj, realm, getStackTraceLimit(realm), Undefined.instance, false);
   }

   public static JSException create(JSErrorType type, String message, JSDynamicObject exceptionObj, JSRealm realm) {
      return create(type, message, (Node)null, exceptionObj, realm);
   }

   @CompilerDirectives.TruffleBoundary
   public static JSException create(JSErrorType type, String message, Node originatingNode, JSDynamicObject exceptionObj, JSRealm realm) {
      return fillInStackTrace(new JSException(type, message, originatingNode, exceptionObj, realm, getStackTraceLimit(realm)), false);
   }

   public static JSException create(JSErrorType type, String message) {
      return create(type, message, (Node)null);
   }

   public static JSException create(JSErrorType type, String message, Node originatingNode) {
      JSRealm realm = JSRealm.get(originatingNode);
      return fillInStackTrace(new JSException(type, message, originatingNode, null, realm, getStackTraceLimit(realm)), false);
   }

   public static JSException create(JSErrorType type, String message, Throwable cause, Node originatingNode) {
      JSRealm realm = JSRealm.get(originatingNode);
      return fillInStackTrace(new JSException(type, message, cause, originatingNode, realm, getStackTraceLimit(realm)), false);
   }

   public static JSException create(JSErrorType type, String message, SourceSection sourceLocation, boolean isIncompleteSource) {
      JSRealm realm = JavaScriptLanguage.getCurrentJSRealm();
      return fillInStackTrace(new JSException(type, message, sourceLocation, realm, getStackTraceLimit(realm), isIncompleteSource), false);
   }

   public static int getStackTraceLimit(JSRealm realm) {
      JSDynamicObject errorConstructor = realm.getErrorConstructor(JSErrorType.Error);
      DynamicObjectLibrary lib = DynamicObjectLibrary.getUncached();
      if (JSProperty.isData(lib.getPropertyFlagsOrDefault(errorConstructor, JSError.STACK_TRACE_LIMIT_PROPERTY_NAME, 8))) {
         Object stackTraceLimit = lib.getOrDefault(errorConstructor, JSError.STACK_TRACE_LIMIT_PROPERTY_NAME, Undefined.instance);
         if (JSRuntime.isNumber(stackTraceLimit)) {
            long limit = JSRuntime.toInteger(stackTraceLimit);
            return (int)Math.max(0L, Math.min(limit, 2147483647L));
         }
      }

      return 0;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String getMessage() {
      String message = this.getRawMessage();
      return message != null && !message.isEmpty() ? this.type.name() + ": " + message : this.type.name();
   }

   public String getRawMessage() {
      return super.getMessage();
   }

   public JSErrorType getErrorType() {
      return this.type;
   }

   public JSDynamicObject getErrorObjectLazy() {
      return this.exceptionObj;
   }

   public void setErrorObject(JSDynamicObject exceptionObj) {
      this.exceptionObj = exceptionObj;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getErrorObject() {
      JSDynamicObject jserror = this.exceptionObj;
      if (jserror == null) {
         String message = this.getRawMessage();
         this.exceptionObj = jserror = JSError.createFromJSException(this, this.realm, message == null ? "" : message);
      }

      return jserror;
   }

   public JSRealm getRealm() {
      return this.realm;
   }

   @ExportMessage
   public ExceptionType getExceptionType() {
      return this.type == JSErrorType.SyntaxError ? ExceptionType.PARSE_ERROR : ExceptionType.RUNTIME_ERROR;
   }

   @ExportMessage
   public boolean isExceptionIncompleteSource() {
      return this.isIncompleteSource;
   }

   @ExportMessage
   public boolean hasMembers() {
      return true;
   }

   @ExportMessage
   public Object getMembers(boolean internal, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("delegateLib") InteropLibrary delegateLib) throws UnsupportedMessageException {
      return delegateLib.getMembers(this.getErrorObject(), internal);
   }

   @ExportMessage
   public boolean isMemberReadable(String key, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("delegateLib") InteropLibrary delegateLib) {
      return delegateLib.isMemberReadable(this.getErrorObject(), key);
   }

   @ExportMessage
   public boolean isMemberModifiable(String key, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("delegateLib") InteropLibrary delegateLib) {
      return delegateLib.isMemberModifiable(this.getErrorObject(), key);
   }

   @ExportMessage
   public boolean isMemberInsertable(String key, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("delegateLib") InteropLibrary delegateLib) {
      return delegateLib.isMemberInsertable(this.getErrorObject(), key);
   }

   @ExportMessage
   public boolean isMemberRemovable(String key, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("delegateLib") InteropLibrary delegateLib) {
      return delegateLib.isMemberRemovable(this.getErrorObject(), key);
   }

   @ExportMessage
   public boolean isMemberInvocable(String key, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("delegateLib") InteropLibrary delegateLib) {
      return delegateLib.isMemberInvocable(this.getErrorObject(), key);
   }

   @ExportMessage
   public boolean hasMemberReadSideEffects(String key, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("delegateLib") InteropLibrary delegateLib) {
      return delegateLib.hasMemberReadSideEffects(this.getErrorObject(), key);
   }

   @ExportMessage
   public boolean hasMemberWriteSideEffects(String key, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("delegateLib") InteropLibrary delegateLib) {
      return delegateLib.hasMemberWriteSideEffects(this.getErrorObject(), key);
   }

   @ExportMessage
   public Object readMember(String key, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("delegateLib") InteropLibrary delegateLib) throws UnknownIdentifierException, UnsupportedMessageException {
      return delegateLib.readMember(this.getErrorObject(), key);
   }

   @ExportMessage
   public void writeMember(String key, Object value, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("delegateLib") InteropLibrary delegateLib) throws UnsupportedMessageException, UnknownIdentifierException, UnsupportedTypeException {
      delegateLib.writeMember(this.getErrorObject(), key, value);
   }

   @ExportMessage
   public void removeMember(String key, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("delegateLib") InteropLibrary delegateLib) throws UnsupportedMessageException, UnknownIdentifierException {
      delegateLib.removeMember(this.getErrorObject(), key);
   }

   @ExportMessage
   public Object invokeMember(String key, Object[] args, @CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("delegateLib") InteropLibrary delegateLib) throws UnsupportedMessageException, UnknownIdentifierException, ArityException, UnsupportedTypeException {
      return delegateLib.invokeMember(this.getErrorObject(), key, args);
   }

   @ExportMessage
   public boolean hasMetaObject(@CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("delegateLib") InteropLibrary delegateLib) {
      return delegateLib.hasMetaObject(this.getErrorObject());
   }

   @ExportMessage
   public Object getMetaObject(@CachedLibrary(limit = "InteropLibraryLimit") @Cached.Shared("delegateLib") InteropLibrary delegateLib) throws UnsupportedMessageException {
      return delegateLib.getMetaObject(this.getErrorObject());
   }

   public static void ensureInitialized() throws ClassNotFoundException {
      Class.forName(JSExceptionGen.class.getName());
      Class.forName(GraalJSExceptionGen.class.getName());
      Class.forName(UserScriptExceptionGen.class.getName());
   }
}
