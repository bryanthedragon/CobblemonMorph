package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.GenerateUncached;
import com.oracle.truffle.api.dsl.ImportStatic;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.objects.IteratorRecord;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;

@ImportStatic({JSConfig.class, JSRuntime.class, Strings.class})
@GenerateUncached
public abstract class JSInteropGetIteratorNextNode extends JSInteropCallNode {
   JSInteropGetIteratorNextNode() {
   }

   public static JSInteropGetIteratorNextNode create() {
      return JSInteropGetIteratorNextNodeGen.create();
   }

   public final Object getIteratorNextElement(IteratorRecord receiver, JavaScriptLanguage language, Object stopValue) {
      try {
         return this.execute(receiver, language, stopValue);
      } catch (StopIterationException var5) {
         throw Errors.shouldNotReachHere(var5);
      }
   }

   public final Object getIteratorNextElement(IteratorRecord iterator, JavaScriptLanguage language) throws StopIterationException {
      return this.execute(iterator, language, null);
   }

   protected abstract Object execute(IteratorRecord iterator, JavaScriptLanguage language, Object stopValue) throws StopIterationException;

   @Specialization
   Object doDefault(
      IteratorRecord iterator,
      JavaScriptLanguage language,
      Object stopValue,
      @Cached(value = "createCall()", uncached = "getUncachedCall()") JSFunctionCallNode callNode,
      @Cached(value = "create(DONE, language.getJSContext())", uncached = "getUncachedProperty()") PropertyGetNode donePropertyGetNode,
      @Cached(value = "create(VALUE, language.getJSContext())", uncached = "getUncachedProperty()") PropertyGetNode valuePropertyGetNode,
      @Cached JSToBooleanNode toBooleanNode,
      @Cached ExportValueNode exportValueNode,
      @Cached BranchProfile exceptionBranch
   ) throws StopIterationException {
      Object iterResult = callNode.executeCall(JSArguments.createZeroArg(iterator.getIterator(), iterator.getNextMethod()));
      if (iterResult instanceof JSObject) {
         JSObject iterResultObject = (JSObject)iterResult;
         Object doneValue = getProperty(iterResultObject, donePropertyGetNode, Strings.DONE, Boolean.FALSE);
         boolean done = toBooleanNode.executeBoolean(doneValue);
         if (done) {
            if (stopValue != null) {
               return stopValue;
            } else {
               throw StopIterationException.create();
            }
         } else {
            Object value = getProperty(iterResultObject, valuePropertyGetNode, Strings.VALUE, Undefined.instance);
            return exportValueNode.execute(value);
         }
      } else {
         exceptionBranch.enter();
         throw Errors.createTypeErrorIteratorResultNotObject(iterResult, null);
      }
   }
}
