package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSAttributes;

public interface Builtin {
   TruffleString getName();

   Object getKey();

   int getLength();

   int getECMAScriptVersion();

   boolean isAnnexB();

   boolean isWritable();

   boolean isEnumerable();

   boolean isConfigurable();

   default int getAttributeFlags() {
      return JSAttributes.fromConfigurableEnumerableWritable(this.isConfigurable(), this.isEnumerable(), this.isWritable());
   }

   boolean isGetter();

   boolean isSetter();

   JSFunctionData createFunctionData(JSContext context);

   default boolean isIncluded(JSContext context) {
      return this.getECMAScriptVersion() > context.getEcmaScriptVersion() ? false : !this.isAnnexB() || context.isOptionAnnexB();
   }
}
