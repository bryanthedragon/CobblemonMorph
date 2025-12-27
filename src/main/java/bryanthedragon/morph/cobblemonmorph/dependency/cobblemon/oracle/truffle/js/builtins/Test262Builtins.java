package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Fallback;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.js.builtins.helper.GCNodeGen;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSBuiltinNode;
import com.oracle.truffle.js.nodes.function.JSLoadNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.BuiltinEnum;
import com.oracle.truffle.js.runtime.builtins.JSTest262;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.DebugJSAgent;

public final class Test262Builtins extends JSBuiltinsContainer.SwitchEnum<Test262Builtins.Test262> {
   public static final JSBuiltinsContainer BUILTINS = new Test262Builtins();

   protected Test262Builtins() {
      super(JSTest262.CLASS_NAME, Test262Builtins.Test262.class);
   }

   protected Object createNode(JSContext context, JSBuiltin builtin, boolean construct, boolean newTarget, Test262Builtins.Test262 builtinEnum) {
      switch (builtinEnum) {
         case detachArrayBuffer:
            return DebugBuiltinsFactory.DebugTypedArrayDetachBufferNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case createRealm:
            return Test262BuiltinsFactory.Test262CreateRealmNodeGen.create(context, builtin, args().createArgumentNodes(context));
         case evalScript:
            return Test262BuiltinsFactory.Test262EvalScriptNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
         case gc:
            return GCNodeGen.create(context, builtin, args().createArgumentNodes(context));
         default:
            switch (builtinEnum) {
               case agentStart:
                  return Test262BuiltinsFactory.Test262AgentStartNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
               case agentBroadcast:
                  return Test262BuiltinsFactory.Test262AgentBroadcastNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
               case agentGetReport:
                  return Test262BuiltinsFactory.Test262AgentGetReportNodeGen.create(context, builtin, args().fixedArgs(0).createArgumentNodes(context));
               case agentSleep:
                  return Test262BuiltinsFactory.Test262AgentSleepNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
               case agentReceiveBroadcast:
                  return Test262BuiltinsFactory.Test262AgentReceiveBroadcastNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
               case agentReport:
                  return Test262BuiltinsFactory.Test262AgentReportNodeGen.create(context, builtin, args().fixedArgs(1).createArgumentNodes(context));
               case agentLeaving:
                  return Test262BuiltinsFactory.Test262AgentLeavingNodeGen.create(context, builtin, args().fixedArgs(0).createArgumentNodes(context));
               default:
                  return null;
            }
      }
   }

   public static enum Test262 implements BuiltinEnum<Test262Builtins.Test262> {
      createRealm(0),
      evalScript(1),
      detachArrayBuffer(1),
      gc(0),
      agentStart(1),
      agentBroadcast(1),
      agentGetReport(0),
      agentSleep(1),
      agentReceiveBroadcast(1),
      agentReport(1),
      agentLeaving(0);

      private final int length;

      private Test262(int length) {
         this.length = length;
      }

      @Override
      public int getLength() {
         return this.length;
      }
   }

   public abstract static class Test262AgentBroadcast extends JSBuiltinNode {
      public Test262AgentBroadcast(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object broadcast(Object sab) {
         ((DebugJSAgent)this.getRealm().getAgent()).broadcast(sab);
         return Undefined.instance;
      }
   }

   public abstract static class Test262AgentGetReport extends JSBuiltinNode {
      public Test262AgentGetReport(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object getReport() {
         return ((DebugJSAgent)this.getRealm().getAgent()).getReport();
      }
   }

   public abstract static class Test262AgentLeaving extends JSBuiltinNode {
      public Test262AgentLeaving(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object leaving() {
         ((DebugJSAgent)this.getRealm().getAgent()).leaving();
         return Undefined.instance;
      }
   }

   public abstract static class Test262AgentReceiveBroadcast extends JSBuiltinNode {
      public Test262AgentReceiveBroadcast(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object receiveBroadcast(Object lambda) {
         ((DebugJSAgent)this.getRealm().getAgent()).setDebugReceiveBroadcast(lambda);
         return Undefined.instance;
      }
   }

   public abstract static class Test262AgentReport extends JSBuiltinNode {
      @Node.Child
      private JSToStringNode toStringNode = JSToStringNode.create();

      public Test262AgentReport(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object report(Object value) {
         Object message = this.toStringNode.executeString(value);
         ((DebugJSAgent)this.getRealm().getAgent()).report(message);
         return Undefined.instance;
      }
   }

   public abstract static class Test262AgentSleep extends JSBuiltinNode {
      public Test262AgentSleep(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object doSleep(int time) {
         ((DebugJSAgent)this.getRealm().getAgent()).sleep(time);
         return Undefined.instance;
      }

      @Fallback
      protected Object doSleep(Object time) {
         throw Errors.createTypeError("Integer expected");
      }
   }

   public abstract static class Test262AgentStart extends JSBuiltinNode {
      public Test262AgentStart(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object start(Object obj) {
         String sourceText = Strings.toJavaString(JSRuntime.toString(obj));
         ((DebugJSAgent)this.getRealm().getAgent()).startNewAgent(sourceText);
         return Undefined.instance;
      }
   }

   public abstract static class Test262CreateRealmNode extends JSBuiltinNode {
      public Test262CreateRealmNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object createRealm() {
         JSDynamicObject newGlobalObj = this.createChildRealm().getGlobalObject();
         return JSObject.get(newGlobalObj, JSTest262.GLOBAL_PROPERTY_NAME);
      }

      @CompilerDirectives.TruffleBoundary
      private JSRealm createChildRealm() {
         return this.getRealm().createChildRealm();
      }
   }

   public abstract static class Test262EvalScriptNode extends JSBuiltinNode {
      public Test262EvalScriptNode(JSContext context, JSBuiltin builtin) {
         super(context, builtin);
      }

      @Specialization
      protected Object evalScript(Object obj, @Cached("create(getContext())") JSLoadNode loadNode) {
         String sourceText = Strings.toJavaString(JSRuntime.toString(obj));
         this.getContext().checkEvalAllowed();
         Source source = createSource(sourceText);
         JSRealm realm = this.getRealm();
         return loadNode.executeLoad(source, realm);
      }

      @CompilerDirectives.TruffleBoundary
      private static Source createSource(String sourceText) {
         return Source.newBuilder("js", sourceText, "<eval>").cached(false).build();
      }
   }
}
