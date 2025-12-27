package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSSetObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(SetPrototypeBuiltins.class)
public final class SetPrototypeBuiltinsFactory {
   @GeneratedBy(SetPrototypeBuiltins.CreateSetIteratorNode.class)
   public static final class CreateSetIteratorNodeGen extends SetPrototypeBuiltins.CreateSetIteratorNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private CreateSetIteratorNodeGen(JSContext context, JSBuiltin builtin, int iterationKind, JavaScriptNode[] arguments) {
         super(context, builtin, iterationKind);
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
            JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
            return this.doSet(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
            return this.doIncompatibleReceiver(arguments0Value_);
         } else {
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
         if (arguments0Value instanceof JSSetObject) {
            JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.doSet(arguments0Value_);
         } else if (!JSGuards.isJSSet(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.doIncompatibleReceiver(arguments0Value);
         } else {
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
         Object[] s = new Object[]{"doSet", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doIncompatibleReceiver", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static SetPrototypeBuiltins.CreateSetIteratorNode create(JSContext context, JSBuiltin builtin, int iterationKind, JavaScriptNode[] arguments) {
         return new SetPrototypeBuiltinsFactory.CreateSetIteratorNodeGen(context, builtin, iterationKind, arguments);
      }
   }

   @GeneratedBy(SetPrototypeBuiltins.JSSetAddNode.class)
   public static final class JSSetAddNodeGen extends SetPrototypeBuiltins.JSSetAddNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSSetAddNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
               JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
               return this.add(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
               return SetPrototypeBuiltins.JSSetAddNode.notSet(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSSetObject) {
            JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.add(arguments0Value_, arguments1Value);
         } else if (!JSGuards.isJSSet(arguments0Value)) {
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return SetPrototypeBuiltins.JSSetAddNode.notSet(arguments0Value, arguments1Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
         Object[] s = new Object[]{"add", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"notSet", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static SetPrototypeBuiltins.JSSetAddNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SetPrototypeBuiltinsFactory.JSSetAddNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(SetPrototypeBuiltins.JSSetClearNode.class)
   public static final class JSSetClearNodeGen extends SetPrototypeBuiltins.JSSetClearNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSSetClearNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
            JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
            return SetPrototypeBuiltins.JSSetClearNode.clear(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
            return SetPrototypeBuiltins.JSSetClearNode.notSet(arguments0Value_);
         } else {
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
         if (arguments0Value instanceof JSSetObject) {
            JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return SetPrototypeBuiltins.JSSetClearNode.clear(arguments0Value_);
         } else if (!JSGuards.isJSSet(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return SetPrototypeBuiltins.JSSetClearNode.notSet(arguments0Value);
         } else {
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
         Object[] s = new Object[]{"clear", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"notSet", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static SetPrototypeBuiltins.JSSetClearNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SetPrototypeBuiltinsFactory.JSSetClearNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(SetPrototypeBuiltins.JSSetDeleteNode.class)
   public static final class JSSetDeleteNodeGen extends SetPrototypeBuiltins.JSSetDeleteNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSSetDeleteNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
               JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
               return this.delete(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
               return SetPrototypeBuiltins.JSSetDeleteNode.notSet(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
               JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
               return this.delete(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
               return SetPrototypeBuiltins.JSSetDeleteNode.notSet(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSSetObject) {
            JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.delete(arguments0Value_, arguments1Value);
         } else if (!JSGuards.isJSSet(arguments0Value)) {
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return SetPrototypeBuiltins.JSSetDeleteNode.notSet(arguments0Value, arguments1Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
         Object[] s = new Object[]{"delete", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"notSet", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static SetPrototypeBuiltins.JSSetDeleteNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SetPrototypeBuiltinsFactory.JSSetDeleteNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(SetPrototypeBuiltins.JSSetDifferenceNode.class)
   public static final class JSSetDifferenceNodeGen extends SetPrototypeBuiltins.JSSetDifferenceNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSSetDifferenceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
               JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
               return this.difference(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
               return this.notSet(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
            return this.notSet(arguments0Value_, arguments1Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 1) == 0 && state_0 != 0) {
               this.executeBoolean(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSSetObject) {
            JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.difference(arguments0Value_, arguments1Value);
         } else if (!JSGuards.isJSSet(arguments0Value)) {
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return this.notSet(arguments0Value, arguments1Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
         Object[] s = new Object[]{"difference", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"notSet", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static SetPrototypeBuiltins.JSSetDifferenceNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SetPrototypeBuiltinsFactory.JSSetDifferenceNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(SetPrototypeBuiltins.JSSetForEachNode.class)
   public static final class JSSetForEachNodeGen extends SetPrototypeBuiltins.JSSetForEachNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private IsCallableNode isCallable;
      @Node.Child
      private JSFunctionCallNode forEachFunction_callNode_;

      private JSSetForEachNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSSetObject) {
               JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
               if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSDynamicObject) {
                  JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
                  if (this.isCallable.executeBoolean(arguments1Value__)) {
                     return this.forEachFunction(arguments0Value__, arguments1Value__, arguments2Value_, this.isCallable, this.forEachFunction_callNode_);
                  }
               }

               if ((state_0 & 2) != 0 && !this.isCallable.executeBoolean(arguments1Value_)) {
                  return SetPrototypeBuiltins.JSSetForEachNode.forEachFunctionNoFunction(arguments0Value__, arguments1Value_, arguments2Value_, this.isCallable);
               }
            }

            if ((state_0 & 4) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
               return SetPrototypeBuiltins.JSSetForEachNode.forEachFunctionNoSet(arguments0Value_, arguments1Value_, arguments2Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSSetObject) {
               JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
               if (arguments1Value instanceof JSDynamicObject) {
                  JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
                  boolean ForEachFunction_duplicateFound_ = false;
                  if ((state_0 & 1) != 0 && this.isCallable.executeBoolean(arguments1Value_)) {
                     ForEachFunction_duplicateFound_ = true;
                  }

                  if (!ForEachFunction_duplicateFound_) {
                     IsCallableNode forEachFunction_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable);
                     if (forEachFunction_isCallable__.executeBoolean(arguments1Value_) && (state_0 & 1) == 0) {
                        if (this.isCallable == null) {
                           IsCallableNode forEachFunction_isCallable___check = super.insert(forEachFunction_isCallable__);
                           if (forEachFunction_isCallable___check == null) {
                              throw new AssertionError(
                                 "Specialization 'forEachFunction(JSSetObject, JSDynamicObject, Object, IsCallableNode, JSFunctionCallNode)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                              );
                           }

                           this.isCallable = forEachFunction_isCallable___check;
                        }

                        this.forEachFunction_callNode_ = super.insert(JSFunctionCallNode.createCall());
                        this.state_0_ = state_0 |= 1;
                        ForEachFunction_duplicateFound_ = true;
                     }
                  }

                  if (ForEachFunction_duplicateFound_) {
                     lock.unlock();
                     hasLock = false;
                     return this.forEachFunction(arguments0Value_, arguments1Value_, arguments2Value, this.isCallable, this.forEachFunction_callNode_);
                  }
               }

               boolean ForEachFunctionNoFunction_duplicateFound_ = false;
               if ((state_0 & 2) != 0 && !this.isCallable.executeBoolean(arguments1Value)) {
                  ForEachFunctionNoFunction_duplicateFound_ = true;
               }

               if (!ForEachFunctionNoFunction_duplicateFound_) {
                  IsCallableNode forEachFunctionNoFunction_isCallable__ = super.insert(this.isCallable == null ? IsCallableNode.create() : this.isCallable);
                  if (!forEachFunctionNoFunction_isCallable__.executeBoolean(arguments1Value) && (state_0 & 2) == 0) {
                     if (this.isCallable == null) {
                        IsCallableNode forEachFunctionNoFunction_isCallable___check = super.insert(forEachFunctionNoFunction_isCallable__);
                        if (forEachFunctionNoFunction_isCallable___check == null) {
                           throw new AssertionError(
                              "Specialization 'forEachFunctionNoFunction(JSSetObject, Object, Object, IsCallableNode)' contains a shared cache with name 'isCallable' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isCallable = forEachFunctionNoFunction_isCallable___check;
                     }

                     this.state_0_ = state_0 |= 2;
                     ForEachFunctionNoFunction_duplicateFound_ = true;
                  }
               }

               if (ForEachFunctionNoFunction_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return SetPrototypeBuiltins.JSSetForEachNode.forEachFunctionNoFunction(arguments0Value_, arguments1Value, arguments2Value, this.isCallable);
               }
            }

            if (JSGuards.isJSSet(arguments0Value)) {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
               );
            } else {
               int var15;
               this.state_0_ = var15 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return SetPrototypeBuiltins.JSSetForEachNode.forEachFunctionNoSet(arguments0Value, arguments1Value, arguments2Value);
            }
         } finally {
            if (hasLock) {
               lock.unlock();
            }
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
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"forEachFunction", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.isCallable, this.forEachFunction_callNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"forEachFunctionNoFunction", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.isCallable));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"forEachFunctionNoSet", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static SetPrototypeBuiltins.JSSetForEachNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SetPrototypeBuiltinsFactory.JSSetForEachNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(SetPrototypeBuiltins.JSSetHasNode.class)
   public static final class JSSetHasNodeGen extends SetPrototypeBuiltins.JSSetHasNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSSetHasNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
               JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
               return this.has(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
               return this.hasNoObject(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
               JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
               return this.has(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
               return this.hasNoObject(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSSetObject) {
            JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.has(arguments0Value_, arguments1Value);
         } else if (!JSGuards.isJSSet(arguments0Value)) {
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return this.hasNoObject(arguments0Value, arguments1Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
         Object[] s = new Object[]{"has", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"hasNoObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static SetPrototypeBuiltins.JSSetHasNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SetPrototypeBuiltinsFactory.JSSetHasNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(SetPrototypeBuiltins.JSSetIntersectionNode.class)
   public static final class JSSetIntersectionNodeGen extends SetPrototypeBuiltins.JSSetIntersectionNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSSetIntersectionNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
               JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
               return this.intersection(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
               return this.notSet(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
            return this.notSet(arguments0Value_, arguments1Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 1) == 0 && state_0 != 0) {
               this.executeBoolean(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSSetObject) {
            JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.intersection(arguments0Value_, arguments1Value);
         } else if (!JSGuards.isJSSet(arguments0Value)) {
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return this.notSet(arguments0Value, arguments1Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
         Object[] s = new Object[]{"intersection", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"notSet", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static SetPrototypeBuiltins.JSSetIntersectionNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SetPrototypeBuiltinsFactory.JSSetIntersectionNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(SetPrototypeBuiltins.JSSetIsDisjointFromNode.class)
   public static final class JSSetIsDisjointFromNodeGen extends SetPrototypeBuiltins.JSSetIsDisjointFromNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSSetIsDisjointFromNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
               JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
               return this.isDisjointFrom(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
               return this.notSet(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
               JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
               return this.isDisjointFrom(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
               return this.notSet(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         try {
            this.executeBoolean(frameValue);
         } catch (UnexpectedResultException var3) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSSetObject) {
            JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.isDisjointFrom(arguments0Value_, arguments1Value);
         } else if (!JSGuards.isJSSet(arguments0Value)) {
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return this.notSet(arguments0Value, arguments1Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
         Object[] s = new Object[]{"isDisjointFrom", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"notSet", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static SetPrototypeBuiltins.JSSetIsDisjointFromNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SetPrototypeBuiltinsFactory.JSSetIsDisjointFromNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(SetPrototypeBuiltins.JSSetIsSubsetOfNode.class)
   public static final class JSSetIsSubsetOfNodeGen extends SetPrototypeBuiltins.JSSetIsSubsetOfNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSSetIsSubsetOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
               JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
               return this.isSubsetOf(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
               return this.notSet(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
               JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
               return this.isSubsetOf(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
               return this.notSet(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         try {
            this.executeBoolean(frameValue);
         } catch (UnexpectedResultException var3) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSSetObject) {
            JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.isSubsetOf(arguments0Value_, arguments1Value);
         } else if (!JSGuards.isJSSet(arguments0Value)) {
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return this.notSet(arguments0Value, arguments1Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
         Object[] s = new Object[]{"isSubsetOf", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"notSet", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static SetPrototypeBuiltins.JSSetIsSubsetOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SetPrototypeBuiltinsFactory.JSSetIsSubsetOfNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(SetPrototypeBuiltins.JSSetIsSupersetOfNode.class)
   public static final class JSSetIsSupersetOfNodeGen extends SetPrototypeBuiltins.JSSetIsSupersetOfNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSSetIsSupersetOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
               JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
               return this.isSupersetOf(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
               return this.notSet(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
               JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
               return this.isSupersetOf(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
               return this.notSet(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         try {
            this.executeBoolean(frameValue);
         } catch (UnexpectedResultException var3) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSSetObject) {
            JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.isSupersetOf(arguments0Value_, arguments1Value);
         } else if (!JSGuards.isJSSet(arguments0Value)) {
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return this.notSet(arguments0Value, arguments1Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
         Object[] s = new Object[]{"isSupersetOf", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"notSet", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static SetPrototypeBuiltins.JSSetIsSupersetOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SetPrototypeBuiltinsFactory.JSSetIsSupersetOfNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(SetPrototypeBuiltins.JSSetSymmetricDifferenceNode.class)
   public static final class JSSetSymmetricDifferenceNodeGen extends SetPrototypeBuiltins.JSSetSymmetricDifferenceNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSSetSymmetricDifferenceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
               JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
               return this.symmetricDifference(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
               return this.notSet(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
            return this.notSet(arguments0Value_, arguments1Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 1) == 0 && state_0 != 0) {
               this.executeBoolean(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSSetObject) {
            JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.symmetricDifference(arguments0Value_, arguments1Value);
         } else if (!JSGuards.isJSSet(arguments0Value)) {
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return this.notSet(arguments0Value, arguments1Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
         Object[] s = new Object[]{"symmetricDifference", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"notSet", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static SetPrototypeBuiltins.JSSetSymmetricDifferenceNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SetPrototypeBuiltinsFactory.JSSetSymmetricDifferenceNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(SetPrototypeBuiltins.JSSetUnionNode.class)
   public static final class JSSetUnionNodeGen extends SetPrototypeBuiltins.JSSetUnionNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSSetUnionNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSSetObject) {
               JSSetObject arguments0Value__ = (JSSetObject)arguments0Value_;
               return this.union(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
               return this.notSet(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 2) != 0 && !JSGuards.isJSSet(arguments0Value_)) {
            return this.notSet(arguments0Value_, arguments1Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectBoolean(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 1) == 0 && state_0 != 0) {
               this.executeBoolean(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSSetObject) {
            JSSetObject arguments0Value_ = (JSSetObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.union(arguments0Value_, arguments1Value);
         } else if (!JSGuards.isJSSet(arguments0Value)) {
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return this.notSet(arguments0Value, arguments1Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
         Object[] s = new Object[]{"union", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"notSet", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static SetPrototypeBuiltins.JSSetUnionNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new SetPrototypeBuiltinsFactory.JSSetUnionNodeGen(context, builtin, arguments);
      }
   }
}
