package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ErrorPrototypeBuiltins.class)
public final class ErrorPrototypeBuiltinsFactory {
   @GeneratedBy(ErrorPrototypeBuiltins.ErrorPrototypeGetStackTraceNode.class)
   public static final class ErrorPrototypeGetStackTraceNodeGen
      extends ErrorPrototypeBuiltins.ErrorPrototypeGetStackTraceNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ErrorPrototypeGetStackTraceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && !JSGuards.isJSObject(arguments0Value_)) {
            return this.getStackTrace(arguments0Value_);
         } else {
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (JSGuards.isJSObject(arguments0Value__)) {
                  return this.getStackTrace(arguments0Value__);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
         int state_0 = this.state_0_;
         if (!JSGuards.isJSObject(arguments0Value)) {
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.getStackTrace(arguments0Value);
         } else {
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSObject(arguments0Value_)) {
                  int var4;
                  this.state_0_ = var4 = state_0 | 2;
                  return this.getStackTrace(arguments0Value_);
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
         Object[] s = new Object[]{"getStackTrace", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"getStackTrace", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ErrorPrototypeBuiltins.ErrorPrototypeGetStackTraceNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ErrorPrototypeBuiltinsFactory.ErrorPrototypeGetStackTraceNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ErrorPrototypeBuiltins.ErrorPrototypeToStringNode.class)
   public static final class ErrorPrototypeToStringNodeGen extends ErrorPrototypeBuiltins.ErrorPrototypeToStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.ConcatNode toStringNonObject_concatNode_;

      private ErrorPrototypeToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && !JSGuards.isJSObject(arguments0Value_)) {
            return this.toStringNonObject(arguments0Value_, this.toStringNonObject_concatNode_);
         } else {
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (JSGuards.isJSObject(arguments0Value__)) {
                  return this.toStringObject(arguments0Value__);
               }
            }

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

         JSDynamicObject arguments0Value_;
         try {
            int state_0 = this.state_0_;
            if (JSGuards.isJSObject(arguments0Value)) {
               if (arguments0Value instanceof JSDynamicObject) {
                  arguments0Value_ = (JSDynamicObject)arguments0Value;
                  if (JSGuards.isJSObject(arguments0Value_)) {
                     int var11;
                     this.state_0_ = var11 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.toStringObject(arguments0Value_);
                  }
               }

               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
            }

            this.toStringNonObject_concatNode_ = super.insert(TruffleString.ConcatNode.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            arguments0Value_ = (JSDynamicObject)this.toStringNonObject(arguments0Value, this.toStringNonObject_concatNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return arguments0Value_;
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
         Object[] s = new Object[]{"toStringNonObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toStringNonObject_concatNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"toStringObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ErrorPrototypeBuiltins.ErrorPrototypeToStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ErrorPrototypeBuiltinsFactory.ErrorPrototypeToStringNodeGen(context, builtin, arguments);
      }
   }
}
