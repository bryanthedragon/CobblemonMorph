package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.interop.JSIteratorWrapper;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSObject;

@ImportStatic({JSConfig.class, JSRuntime.class, Symbol.class, Strings.class})
@GenerateUncached
public abstract class JSInteropGetIteratorNode extends JSInteropCallNode {
   JSInteropGetIteratorNode() {
   }

   public static JSInteropGetIteratorNode create() {
      return JSInteropGetIteratorNodeGen.create();
   }

   public final boolean hasIterator(JSObject receiver, JavaScriptLanguage language) {
      try {
         return (Boolean)this.execute(receiver, language, true);
      } catch (UnsupportedMessageException var4) {
         throw Errors.shouldNotReachHere(var4);
      }
   }

   public final Object getIterator(JSObject receiver, JavaScriptLanguage language) throws UnsupportedMessageException {
      return this.execute(receiver, language, false);
   }

   protected abstract Object execute(JSObject receiver, JavaScriptLanguage language, boolean hasIteratorCheck) throws UnsupportedMessageException;

   @Specialization
   Object doDefault(
      JSObject receiver,
      JavaScriptLanguage language,
      boolean hasIteratorCheck,
      @Cached(value = "create(SYMBOL_ITERATOR, language.getJSContext())", uncached = "getUncachedProperty()") PropertyGetNode iteratorPropertyGetNode,
      @Cached IsCallableNode isCallableNode,
      @Cached(value = "createCall()", uncached = "getUncachedCall()") JSFunctionCallNode callNode,
      @Cached(value = "create(NEXT, language.getJSContext())", uncached = "getUncachedProperty()") PropertyGetNode nextPropertyGetNode,
      @Cached BranchProfile exceptionBranch
   ) throws UnsupportedMessageException {
      Object method = getProperty(receiver, iteratorPropertyGetNode, Symbol.SYMBOL_ITERATOR, null);
      boolean hasIterator = method != null && isCallableNode.executeBoolean(method);
      if (hasIteratorCheck) {
         return hasIterator;
      } else if (hasIterator) {
         Object iterator = callNode.executeCall(JSArguments.createZeroArg(receiver, method));
         if (iterator instanceof JSObject) {
            JSObject jsIterator = (JSObject)iterator;
            Object nextMethod = getProperty(jsIterator, nextPropertyGetNode, Strings.NEXT, null);
            if (nextMethod != null && isCallableNode.executeBoolean(nextMethod)) {
               return JSIteratorWrapper.create(IteratorRecord.create(jsIterator, nextMethod));
            }
         }

         exceptionBranch.enter();
         throw Errors.createTypeErrorNotIterable(receiver, null);
      } else {
         exceptionBranch.enter();
         throw UnsupportedMessageException.create();
      }
   }
}
