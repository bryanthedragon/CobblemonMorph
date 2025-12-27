package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSLoadNode;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(Test262Builtins.class)
public final class Test262BuiltinsFactory {
   @GeneratedBy(Test262Builtins.Test262AgentBroadcast.class)
   public static final class Test262AgentBroadcastNodeGen extends Test262Builtins.Test262AgentBroadcast implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private Test262AgentBroadcastNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.broadcast(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"broadcast", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static Test262Builtins.Test262AgentBroadcast create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new Test262BuiltinsFactory.Test262AgentBroadcastNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(Test262Builtins.Test262AgentGetReport.class)
   public static final class Test262AgentGetReportNodeGen extends Test262Builtins.Test262AgentGetReport implements Introspection.Provider {
      private Test262AgentGetReportNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         return this.getReport();
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"getReport", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static Test262Builtins.Test262AgentGetReport create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new Test262BuiltinsFactory.Test262AgentGetReportNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(Test262Builtins.Test262AgentLeaving.class)
   public static final class Test262AgentLeavingNodeGen extends Test262Builtins.Test262AgentLeaving implements Introspection.Provider {
      private Test262AgentLeavingNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         return this.leaving();
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"leaving", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static Test262Builtins.Test262AgentLeaving create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new Test262BuiltinsFactory.Test262AgentLeavingNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(Test262Builtins.Test262AgentReceiveBroadcast.class)
   public static final class Test262AgentReceiveBroadcastNodeGen extends Test262Builtins.Test262AgentReceiveBroadcast implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private Test262AgentReceiveBroadcastNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.receiveBroadcast(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"receiveBroadcast", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static Test262Builtins.Test262AgentReceiveBroadcast create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new Test262BuiltinsFactory.Test262AgentReceiveBroadcastNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(Test262Builtins.Test262AgentReport.class)
   public static final class Test262AgentReportNodeGen extends Test262Builtins.Test262AgentReport implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private Test262AgentReportNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.report(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"report", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static Test262Builtins.Test262AgentReport create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new Test262BuiltinsFactory.Test262AgentReportNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(Test262Builtins.Test262AgentSleep.class)
   public static final class Test262AgentSleepNodeGen extends Test262Builtins.Test262AgentSleep implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private Test262AgentSleepNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         return (state_0 & 2) == 0 && state_0 != 0 ? this.execute_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
      }

      private Object execute_int0(int state_0, VirtualFrame frameValue) {
         int arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var5.getResult());
         }

         assert (state_0 & 1) != 0;

         return this.doSleep(arguments0Value_);
      }

      private Object execute_generic1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
            int arguments0Value__ = (Integer)arguments0Value_;
            return this.doSleep(arguments0Value__);
         } else if ((state_0 & 2) != 0 && fallbackGuard_(state_0, arguments0Value_)) {
            return this.doSleep(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof Integer) {
            int arguments0Value_ = (Integer)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doSleep(arguments0Value_);
         } else {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doSleep(arguments0Value);
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"doSleep", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doSleep", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
         return (state_0 & 1) != 0 || !(arguments0Value instanceof Integer);
      }

      public static Test262Builtins.Test262AgentSleep create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new Test262BuiltinsFactory.Test262AgentSleepNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(Test262Builtins.Test262AgentStart.class)
   public static final class Test262AgentStartNodeGen extends Test262Builtins.Test262AgentStart implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private Test262AgentStartNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.start(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"start", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static Test262Builtins.Test262AgentStart create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new Test262BuiltinsFactory.Test262AgentStartNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(Test262Builtins.Test262CreateRealmNode.class)
   public static final class Test262CreateRealmNodeGen extends Test262Builtins.Test262CreateRealmNode implements Introspection.Provider {
      private Test262CreateRealmNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[0];
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         return this.createRealm();
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"createRealm", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static Test262Builtins.Test262CreateRealmNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new Test262BuiltinsFactory.Test262CreateRealmNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(Test262Builtins.Test262EvalScriptNode.class)
   public static final class Test262EvalScriptNodeGen extends Test262Builtins.Test262EvalScriptNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSLoadNode loadNode_;

      private Test262EvalScriptNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if (state_0 != 0) {
            return this.evalScript(arguments0Value_, this.loadNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var5;
         try {
            int state_0 = this.state_0_;
            this.loadNode_ = super.insert(JSLoadNode.create(this.getContext()));
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.evalScript(arguments0Value, this.loadNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var5;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"evalScript", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.loadNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static Test262Builtins.Test262EvalScriptNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new Test262BuiltinsFactory.Test262EvalScriptNodeGen(context, builtin, arguments);
      }
   }
}
