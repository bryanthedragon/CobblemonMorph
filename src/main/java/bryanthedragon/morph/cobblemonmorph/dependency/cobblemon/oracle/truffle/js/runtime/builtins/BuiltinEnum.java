package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.function.BuiltinArgumentBuilder;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Strings;

public interface BuiltinEnum<E extends Enum<? extends BuiltinEnum<E>>> {
   default E asEnum() {
      return (E)this;
   }

   default TruffleString getName() {
      return this.prependAccessorPrefix(stripName(Strings.fromJavaString(this.asEnum().name())));
   }

   default Object getKey() {
      return stripName(Strings.fromJavaString(this.asEnum().name()));
   }

   default boolean isConstructor() {
      return false;
   }

   default boolean isNewTargetConstructor() {
      return false;
   }

   int getLength();

   default boolean isEnabled() {
      return true;
   }

   default boolean isAOTSupported() {
      return true;
   }

   default int getECMAScriptVersion() {
      return 5;
   }

   default boolean isAnnexB() {
      return false;
   }

   default boolean isWritable() {
      return true;
   }

   default boolean isConfigurable() {
      return true;
   }

   default boolean isEnumerable() {
      return false;
   }

   default boolean isGetter() {
      return false;
   }

   default boolean isSetter() {
      return false;
   }

   default Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget) {
      throw new UnsupportedOperationException();
   }

   default BuiltinArgumentBuilder args() {
      return BuiltinArgumentBuilder.builder();
   }

   static TruffleString stripName(TruffleString name) {
      return Strings.endsWith(name, Strings.UNDERSCORE) && !Strings.endsWith(name, Strings.UNDERSCORE_2)
         ? Strings.lazySubstring(name, 0, Strings.length(name) - 1)
         : name;
   }

   default TruffleString prependAccessorPrefix(TruffleString name) {
      if (this.isGetter()) {
         return Strings.concat(Strings.GET_SPC, name);
      } else {
         return this.isSetter() ? Strings.concat(Strings.SET_SPC, name) : name;
      }
   }
}
