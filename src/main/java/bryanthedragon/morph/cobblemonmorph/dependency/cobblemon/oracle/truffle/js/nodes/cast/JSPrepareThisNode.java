package com.oracle.truffle.js.nodes.cast;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSBigInt;
import com.oracle.truffle.js.runtime.builtins.JSBoolean;
import com.oracle.truffle.js.runtime.builtins.JSNumber;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.builtins.JSSymbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.Set;

@ImportStatic({CompilerDirectives.class, JSConfig.class})
public abstract class JSPrepareThisNode extends JSUnaryNode {
   final JSContext context;

   protected JSPrepareThisNode(JSContext context, JavaScriptNode child) {
      super(child);
      this.context = context;
   }

   public static JSPrepareThisNode createPrepareThisBinding(JSContext context, JavaScriptNode child) {
      return JSPrepareThisNodeGen.create(context, child);
   }

   @Specialization(guards = "isNullOrUndefined(object)")
   protected JSDynamicObject doJSObject(Object object) {
      return this.getRealm().getGlobalObject();
   }

   @Specialization(guards = {"cachedClass != null", "isExact(object, cachedClass)"}, limit = "1")
   protected Object doJSObjectCached(Object object, @Cached("getClassIfJSObject(object)") Class<?> cachedClass) {
      return object;
   }

   @Specialization(replaces = "doJSObjectCached")
   protected JSObject doJSObject(JSObject object) {
      return object;
   }

   @Specialization
   protected JSObject doBoolean(boolean value) {
      return JSBoolean.create(this.context, this.getRealm(), value);
   }

   @Specialization
   protected JSObject doString(TruffleString value) {
      return JSString.create(this.context, this.getRealm(), value);
   }

   @Specialization
   protected JSObject doInt(int value) {
      return JSNumber.create(this.context, this.getRealm(), value);
   }

   @Specialization
   protected JSObject doDouble(double value) {
      return JSNumber.create(this.context, this.getRealm(), value);
   }

   @Specialization
   protected JSObject doBigInt(BigInt value) {
      return JSBigInt.create(this.context, this.getRealm(), value);
   }

   @Specialization(guards = "isJavaNumber(value)")
   protected JSObject doNumber(Object value) {
      return JSNumber.create(this.context, this.getRealm(), (Number)value);
   }

   @Specialization
   protected JSObject doSymbol(Symbol value) {
      return JSSymbol.create(this.context, this.getRealm(), value);
   }

   @Specialization(guards = "isForeignObject(object)", limit = "InteropLibraryLimit")
   protected Object doForeignObject(Object object, @CachedLibrary("object") InteropLibrary interop) {
      return interop.isNull(object) ? this.getRealm().getGlobalObject() : object;
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return JSPrepareThisNodeGen.create(this.context, cloneUninitialized(this.getOperand(), materializedTags));
   }
}
