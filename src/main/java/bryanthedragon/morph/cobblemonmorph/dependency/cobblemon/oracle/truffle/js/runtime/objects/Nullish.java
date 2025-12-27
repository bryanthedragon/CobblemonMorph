package com.oracle.truffle.js.runtime.objects;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.ToDisplayStringFormat;
import com.oracle.truffle.js.runtime.interop.JSMetaType;
import java.util.Collections;
import java.util.List;

@ExportLibrary(InteropLibrary.class)
public final class Nullish extends JSDynamicObject {
   public Nullish() {
      super(Null.SHAPE);
   }

   @ExportMessage
   boolean isNull() {
      return true;
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
   Object toDisplayString(boolean allowSideEffects) {
      return this == Undefined.instance ? Undefined.NAME : Null.NAME;
   }

   @ExportMessage
   boolean hasMetaObject() {
      return true;
   }

   @ExportMessage
   Object getMetaObject() {
      return JSGuards.isUndefined(this) ? JSMetaType.JS_UNDEFINED : JSMetaType.JS_NULL;
   }

   @Override
   public TruffleString getClassName() {
      return this == Undefined.instance ? Undefined.NAME : Null.NAME;
   }

   @Override
   public TruffleString toDisplayStringImpl(boolean allowSideEffects, ToDisplayStringFormat format, int depth) {
      return this == Undefined.instance ? Null.DISPLAY_STRING_UNDEFINED : Null.DISPLAY_STRING_NULL;
   }

   @Override
   public TruffleString defaultToString() {
      return this == Undefined.instance ? Undefined.NAME : Null.NAME;
   }

   @Override
   boolean isObject() {
      return false;
   }

   static JSException typeError() {
      return Errors.createTypeError("not an object");
   }

   @CompilerDirectives.TruffleBoundary
   static JSException cannotDoPropertyOf(String doWhat, Object index, Object thisObj) {
      return Errors.createTypeErrorFormat("Cannot %s property \"%s\" of %s", doWhat, index, JSRuntime.safeToString(thisObj));
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getOwnHelper(Object thisObj, Object name, Node encapsulatingNode) {
      throw cannotDoPropertyOf("get", name, thisObj);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object getOwnHelper(Object thisObj, long index, Node encapsulatingNode) {
      throw cannotDoPropertyOf("get", index, thisObj);
   }

   @Override
   public Object getMethodHelper(Object thisObj, Object name, Node encapsulatingNode) {
      return this.getHelper(thisObj, name, encapsulatingNode);
   }

   @Override
   public Object getHelper(Object thisObj, Object name, Node encapsulatingNode) {
      return this.getOwnHelper(thisObj, name, encapsulatingNode);
   }

   @Override
   public Object getHelper(Object thisObj, long index, Node encapsulatingNode) {
      return this.getOwnHelper(thisObj, index, encapsulatingNode);
   }

   @Override
   public boolean hasOwnProperty(Object propName) {
      throw typeError();
   }

   @Override
   public boolean hasOwnProperty(long propIdx) {
      throw typeError();
   }

   @Override
   public boolean hasProperty(Object propName) {
      return this.hasOwnProperty(propName);
   }

   @Override
   public boolean hasProperty(long propIdx) {
      return this.hasOwnProperty(propIdx);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean set(Object key, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      throw cannotDoPropertyOf("set", key, this);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean set(long index, Object value, Object receiver, boolean isStrict, Node encapsulatingNode) {
      throw cannotDoPropertyOf("set", index, this);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean delete(Object index, boolean isStrict) {
      throw cannotDoPropertyOf("delete", index, this);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public boolean delete(long index, boolean isStrict) {
      throw cannotDoPropertyOf("delete", index, this);
   }

   @Override
   public List<Object> getOwnPropertyKeys(boolean string, boolean symbols) {
      return Collections.emptyList();
   }

   @Override
   public boolean defineOwnProperty(Object key, PropertyDescriptor desc, boolean doThrow) {
      if (doThrow) {
         throw Errors.createTypeErrorCannotSetProperty(key, this, null);
      } else {
         return false;
      }
   }

   @Override
   public boolean preventExtensions(boolean doThrow) {
      throw typeError();
   }

   @Override
   public boolean isExtensible() {
      throw typeError();
   }

   @Override
   public boolean hasOnlyShapeProperties() {
      return false;
   }

   @Override
   public JSDynamicObject getPrototypeOf() {
      return Null.instance;
   }

   @Override
   public boolean setPrototypeOf(JSDynamicObject newPrototype) {
      return true;
   }

   @Override
   public PropertyDescriptor getOwnProperty(Object propertyKey) {
      throw typeError();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public String toString() {
      if (this == Undefined.instance) {
         return "JSUndefined";
      } else {
         return this == Null.instance ? "JSNull" : super.toString();
      }
   }
}
