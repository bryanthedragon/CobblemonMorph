package com.oracle.truffle.js.nodes.promise;

import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;

public class TriggerPromiseReactionsNode extends JavaScriptBaseNode {
   private final JSContext context;
   @Node.Child
   private PromiseReactionJobNode promiseReactionJob;

   protected TriggerPromiseReactionsNode(JSContext context) {
      this.context = context;
      this.promiseReactionJob = PromiseReactionJobNode.create(context);
   }

   public static TriggerPromiseReactionsNode create(JSContext context) {
      return new TriggerPromiseReactionsNode(context);
   }

   public Object execute(Object reactions, Object argument) {
      SimpleArrayList<?> list = (SimpleArrayList<?>)reactions;

      for (int i = 0; i < list.size(); i++) {
         Object reaction = list.get(i);
         JSFunctionObject job = this.promiseReactionJob.execute(reaction, argument);
         this.context.promiseEnqueueJob(this.getRealm(), job);
      }

      return Undefined.instance;
   }
}
