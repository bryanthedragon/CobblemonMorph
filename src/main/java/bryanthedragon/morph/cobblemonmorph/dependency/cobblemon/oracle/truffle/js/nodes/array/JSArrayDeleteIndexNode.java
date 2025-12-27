package com.oracle.truffle.js.nodes.array;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.array.ScriptArray;
import com.oracle.truffle.js.runtime.builtins.JSAbstractArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Objects;

public abstract class JSArrayDeleteIndexNode extends JavaScriptBaseNode {
   protected final JSContext context;
   protected final boolean strict;

   protected JSArrayDeleteIndexNode(JSContext context, boolean strict) {
      this.context = Objects.requireNonNull(context);
      this.strict = strict;
   }

   public static JSArrayDeleteIndexNode create(JSContext context, boolean strict) {
      return JSArrayDeleteIndexNodeGen.create(context, strict);
   }

   public abstract boolean execute(JSDynamicObject array, ScriptArray arrayType, long index);

   @Specialization(guards = "cachedArrayType.isInstance(arrayType)", limit = "5")
   protected boolean doCached(JSDynamicObject array, ScriptArray arrayType, long index, @Cached("arrayType") ScriptArray cachedArrayType) {
      assert JSArray.isJSFastArray(array);

      if (cachedArrayType.canDeleteElement(array, index, this.strict)) {
         JSAbstractArray.arraySetArrayType(array, cachedArrayType.deleteElement(array, index, this.strict));
         return true;
      } else {
         return false;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Specialization(replaces = "doCached")
   protected boolean doUncached(JSDynamicObject array, ScriptArray arrayType, long index) {
      return this.doCached(array, arrayType, index, arrayType);
   }
}
