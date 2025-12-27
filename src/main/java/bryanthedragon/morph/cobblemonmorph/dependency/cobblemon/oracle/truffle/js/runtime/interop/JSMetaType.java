package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Symbol;

@ExportLibrary(InteropLibrary.class)
public final class JSMetaType implements TruffleObject {
   public static final JSMetaType NULL = new JSMetaType("null", InteropLibrary::isNull);
   public static final JSMetaType BOOLEAN = new JSMetaType("boolean", InteropLibrary::isBoolean);
   public static final JSMetaType STRING = new JSMetaType("string", InteropLibrary::isString);
   public static final JSMetaType NUMBER = new JSMetaType("number", InteropLibrary::isNumber);
   public static final JSMetaType FUNCTION = new JSMetaType("function", (l, v) -> l.isExecutable(v) || l.isInstantiable(v));
   public static final JSMetaType DATE = new JSMetaType("date", InteropLibrary::isInstant);
   public static final JSMetaType ARRAY = new JSMetaType("array", InteropLibrary::hasArrayElements);
   public static final JSMetaType OBJECT = new JSMetaType("object", InteropLibrary::hasMembers);
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   static final JSMetaType[] KNOWN_TYPES = new JSMetaType[]{NULL, BOOLEAN, STRING, NUMBER, FUNCTION, DATE, ARRAY, OBJECT};
   public static final JSMetaType JS_NULL = new JSMetaType("null", (l, v) -> JSGuards.isJSNull(v));
   public static final JSMetaType JS_UNDEFINED = new JSMetaType("undefined", (l, v) -> JSGuards.isUndefined(v));
   public static final JSMetaType JS_BIGINT = new JSMetaType("bigint", (l, v) -> v instanceof BigInt);
   public static final JSMetaType JS_SYMBOL = new JSMetaType("symbol", (l, v) -> v instanceof Symbol);
   public static final JSMetaType JS_PROXY = new JSMetaType("Proxy", (l, v) -> JSGuards.isJSProxy(v));
   private final String typeName;
   private final JSMetaType.TypeCheck isInstance;

   public JSMetaType(String typeName, JSMetaType.TypeCheck isInstance) {
      this.typeName = typeName;
      this.isInstance = isInstance;
   }

   public boolean isInstance(Object instance, InteropLibrary interop) {
      CompilerAsserts.partialEvaluationConstant(this);
      return this.isInstance.check(interop, instance);
   }

   @ExportMessage
   boolean hasLanguage() {
      return true;
   }

   @ExportMessage
   Class<? extends TruffleLanguage<?>> getLanguage() {
      return JavaScriptLanguage.class;
   }

   @ExportMessage
   boolean isMetaObject() {
      return true;
   }

   @ExportMessage.Repeat({@ExportMessage(name = "getMetaQualifiedName"), @ExportMessage(name = "getMetaSimpleName")})
   public String getTypeName() {
      return this.typeName;
   }

   @ExportMessage(name = "toDisplayString")
   Object toDisplayString(boolean allowSideEffects) {
      return this.typeName;
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      return "JSMetaType[" + this.typeName + "]";
   }

   @ExportMessage
   static class IsMetaInstance {
      @Specialization(guards = "type == cachedType", limit = "3")
      static boolean doCached(JSMetaType type, Object value, @Cached("type") JSMetaType cachedType, @CachedLibrary("value") InteropLibrary valueLib) {
         return cachedType.isInstance.check(valueLib, value);
      }

      @CompilerDirectives.TruffleBoundary
      @Specialization(replaces = "doCached")
      static boolean doGeneric(JSMetaType type, Object value) {
         return type.isInstance.check(InteropLibrary.getUncached(), value);
      }
   }

   @FunctionalInterface
   public interface TypeCheck {
      boolean check(InteropLibrary lib, Object value);
   }
}
