package com.oracle.truffle.js.runtime.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.library.ExportLibrary;
import com.oracle.truffle.api.library.ExportMessage;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.runtime.Properties;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.Dead;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSProperty;
import java.util.ArrayList;
import java.util.List;

@ExportLibrary(InteropLibrary.class)
public final class DynamicScopeWrapper implements TruffleObject {
   final JSDynamicObject scope;

   public DynamicScopeWrapper(JSDynamicObject scope) {
      this.scope = scope;
   }

   boolean isConst(TruffleString name, DynamicObjectLibrary access) {
      return JSProperty.isConst(Properties.getProperty(access, this.scope, name));
   }

   @ExportMessage
   boolean hasMembers() {
      return true;
   }

   @ExportMessage
   @CompilerDirectives.TruffleBoundary
   Object getMembers(boolean includeInternal, @CachedLibrary("this.scope") DynamicObjectLibrary access) {
      List<String> keys = new ArrayList<>();

      for (Object key : access.getKeyArray(this.scope)) {
         if (Strings.isTString(key)) {
            Object value = Properties.getOrDefault(access, this.scope, key, null);
            if (value != null && value != Dead.instance()) {
               keys.add(Strings.toJavaString((TruffleString)key));
            }
         }
      }

      return InteropList.create(keys);
   }

   @ExportMessage
   boolean isMemberReadable(
      String name,
      @Cached @Cached.Shared("fromJavaStringNode") TruffleString.FromJavaStringNode fromJavaStringNode,
      @CachedLibrary("this.scope") DynamicObjectLibrary access
   ) {
      TruffleString tsName = Strings.fromJavaString(fromJavaStringNode, name);
      return this.isMemberReadableIntl(tsName, access);
   }

   private boolean isMemberReadableIntl(TruffleString tsName, DynamicObjectLibrary access) {
      Object value = Properties.getOrDefault(access, this.scope, tsName, null);
      return value != null && value != Dead.instance();
   }

   @ExportMessage
   boolean isMemberModifiable(
      String name,
      @Cached @Cached.Shared("fromJavaStringNode") TruffleString.FromJavaStringNode fromJavaStringNode,
      @CachedLibrary("this.scope") DynamicObjectLibrary access
   ) {
      TruffleString tsName = Strings.fromJavaString(fromJavaStringNode, name);
      return this.isMemberReadableIntl(tsName, access) && !this.isConst(tsName, access);
   }

   @ExportMessage
   boolean isMemberInsertable(String name) {
      return false;
   }

   @ExportMessage
   Object readMember(
      String name,
      @Cached @Cached.Shared("fromJavaStringNode") TruffleString.FromJavaStringNode fromJavaStringNode,
      @CachedLibrary("this.scope") DynamicObjectLibrary access,
      @Cached ExportValueNode exportValueNode
   ) throws UnknownIdentifierException {
      TruffleString tsName = Strings.fromJavaString(fromJavaStringNode, name);
      Object value = Properties.getOrDefault(access, this.scope, tsName, null);
      if (value != null && value != Dead.instance()) {
         return exportValueNode.execute(value);
      } else {
         throw UnknownIdentifierException.create(name);
      }
   }

   @ExportMessage
   void writeMember(
      String name,
      Object value,
      @Cached @Cached.Shared("fromJavaStringNode") TruffleString.FromJavaStringNode fromJavaStringNode,
      @CachedLibrary("this.scope") DynamicObjectLibrary access
   ) throws UnsupportedMessageException, UnknownIdentifierException {
      TruffleString tsName = Strings.fromJavaString(fromJavaStringNode, name);
      Object curValue = Properties.getOrDefault(access, this.scope, tsName, null);
      if (curValue == null || curValue == Dead.instance()) {
         throw UnknownIdentifierException.create(name);
      } else if (!this.isConst(tsName, access)) {
         Properties.putIfPresent(access, this.scope, tsName, value);
      } else {
         throw UnsupportedMessageException.create();
      }
   }
}
