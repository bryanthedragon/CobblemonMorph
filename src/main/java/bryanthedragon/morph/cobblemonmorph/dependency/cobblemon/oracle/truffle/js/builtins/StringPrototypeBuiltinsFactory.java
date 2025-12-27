package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.api.strings.TruffleStringBuilder;
import com.oracle.truffle.js.builtins.helper.ReplaceStringParser;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsRegExpNode;
import com.oracle.truffle.js.nodes.access.RequireObjectCoercibleNode;
import com.oracle.truffle.js.nodes.cast.JSToNumberNode;
import com.oracle.truffle.js.nodes.cast.JSToRegExpNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.cast.JSTrimWhitespaceNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(StringPrototypeBuiltins.class)
public final class StringPrototypeBuiltinsFactory {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   @GeneratedBy(StringPrototypeBuiltins.CreateHTMLNode.class)
   static final class CreateHTMLNodeGen extends StringPrototypeBuiltins.CreateHTMLNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private RequireObjectCoercibleNode requireObjectCoercibleNode_;
      @Node.Child
      private JSToStringNode toStringNode_;

      private CreateHTMLNodeGen(JSContext context, JSBuiltin builtin, TruffleString tag, TruffleString attribute, JavaScriptNode[] arguments) {
         super(context, builtin, tag, attribute);
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
            return this.createHTML(arguments0Value_, arguments1Value_, this.requireObjectCoercibleNode_, this.toStringNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var6;
         try {
            int state_0 = this.state_0_;
            this.requireObjectCoercibleNode_ = super.insert(RequireObjectCoercibleNode.create());
            this.toStringNode_ = super.insert(JSToStringNode.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.createHTML(arguments0Value, arguments1Value, this.requireObjectCoercibleNode_, this.toStringNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
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
         Object[] s = new Object[]{"createHTML", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.requireObjectCoercibleNode_, this.toStringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.CreateHTMLNode create(
         JSContext context, JSBuiltin builtin, TruffleString tag, TruffleString attribute, JavaScriptNode[] arguments
      ) {
         return new StringPrototypeBuiltinsFactory.CreateHTMLNodeGen(context, builtin, tag, attribute, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.CreateStringIteratorNode.class)
   public static final class CreateStringIteratorNodeGen extends StringPrototypeBuiltins.CreateStringIteratorNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private RequireObjectCoercibleNode coerce_requireObjectCoercibleNode_;
      @Node.Child
      private JSToStringNode coerce_toStringNode_;

      private CreateStringIteratorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            return this.doString(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isString(arguments0Value_)) {
            return this.doCoerce(arguments0Value_, this.coerce_requireObjectCoercibleNode_, this.coerce_toStringNode_);
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
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var6;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof TruffleString)) {
               if (JSGuards.isString(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               }

               this.coerce_requireObjectCoercibleNode_ = super.insert(RequireObjectCoercibleNode.create());
               this.coerce_toStringNode_ = super.insert(JSToStringNode.create());
               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doCoerce(arguments0Value, this.coerce_requireObjectCoercibleNode_, this.coerce_toStringNode_);
            }

            TruffleString arguments0Value_ = (TruffleString)arguments0Value;
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.doString(arguments0Value_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
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
         Object[] s = new Object[]{"doString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doCoerce", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.coerce_requireObjectCoercibleNode_, this.coerce_toStringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.CreateStringIteratorNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.CreateStringIteratorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringAtNode.class)
   public static final class JSStringAtNodeGen extends StringPrototypeBuiltins.JSStringAtNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.SubstringByteIndexNode substringNode_;

      private JSStringAtNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.at(arguments0Value_, arguments1Value_, this.substringNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var6;
         try {
            int state_0 = this.state_0_;
            this.substringNode_ = super.insert(TruffleString.SubstringByteIndexNode.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.at(arguments0Value, arguments1Value, this.substringNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
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
         Object[] s = new Object[]{"at", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.substringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringAtNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringAtNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringCharAtNode.class)
   public static final class JSStringCharAtNodeGen extends StringPrototypeBuiltins.JSStringCharAtNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSStringCharAtNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return (state_0 & 2) == 0 && state_0 != 0 ? this.execute_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
      }

      private Object execute_int0(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var6) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, var6.getResult());
         }

         assert (state_0 & 1) != 0;

         if (arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            return this.stringCharAt(arguments0Value__, arguments1Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      private Object execute_generic1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               return this.stringCharAt(arguments0Value__, arguments1Value__);
            }
         }

         if ((state_0 & 2) != 0) {
            return this.charAt(arguments0Value_, arguments1Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private TruffleString executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof TruffleString) {
            TruffleString arguments0Value_ = (TruffleString)arguments0Value;
            if (arguments1Value instanceof Integer) {
               int arguments1Value_ = (Integer)arguments1Value;
               int var7;
               this.state_0_ = var7 = state_0 | 1;
               return this.stringCharAt(arguments0Value_, arguments1Value_);
            }
         }

         int var6;
         this.state_0_ = var6 = state_0 | 2;
         return this.charAt(arguments0Value, arguments1Value);
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
         Object[] s = new Object[]{"stringCharAt", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"charAt", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringCharAtNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringCharAtNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(StringPrototypeBuiltins.JSStringCharAtNode.Inlined.class)
      public static final class InlinedNodeGen extends StringPrototypeBuiltins.JSStringCharAtNode.Inlined implements Introspection.Provider {
         @Node.Child
         private JavaScriptNode arguments0_;
         @Node.Child
         private JavaScriptNode arguments1_;
         @CompilerDirectives.CompilationFinal
         private int state_0_;

         private InlinedNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         }

         @Override
         public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
         }

         @Override
         protected Object executeWithArguments(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0 && arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               if (arguments1Value instanceof Integer) {
                  int arguments1Value_ = (Integer)arguments1Value;
                  return this.stringCharAt(arguments0Value_, arguments1Value_);
               }
            }

            if ((state_0 & 2) != 0) {
               return this.charAt(arguments0Value, arguments1Value);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arguments0Value, arguments1Value);
            }
         }

         @Override
         public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            return (state_0 & 2) == 0 && state_0 != 0 ? this.execute_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
         }

         private Object execute_int0(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);

            int arguments1Value_;
            try {
               arguments1Value_ = this.arguments1_.executeInt(frameValue);
            } catch (UnexpectedResultException var6) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arguments0Value_, var6.getResult());
            }

            assert (state_0 & 1) != 0;

            if (arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               return this.stringCharAt(arguments0Value__, arguments1Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
            }
         }

         private Object execute_generic1(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               if (arguments1Value_ instanceof Integer) {
                  int arguments1Value__ = (Integer)arguments1Value_;
                  return this.stringCharAt(arguments0Value__, arguments1Value__);
               }
            }

            if ((state_0 & 2) != 0) {
               return this.charAt(arguments0Value_, arguments1Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
            }
         }

         @Override
         public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
         }

         private TruffleString executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               if (arguments1Value instanceof Integer) {
                  int arguments1Value_ = (Integer)arguments1Value;
                  int var7;
                  this.state_0_ = var7 = state_0 | 1;
                  return this.stringCharAt(arguments0Value_, arguments1Value_);
               }
            }

            int var6;
            this.state_0_ = var6 = state_0 | 2;
            return this.charAt(arguments0Value, arguments1Value);
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
            Object[] s = new Object[]{"stringCharAt", null, null};
            if ((state_0 & 1) != 0) {
               s[1] = (byte)1;
            } else {
               s[1] = (byte)0;
            }

            data[1] = s;
            s = new Object[]{"charAt", null, null};
            if ((state_0 & 2) != 0) {
               s[1] = (byte)1;
            } else {
               s[1] = (byte)0;
            }

            data[2] = s;
            return Introspection.Provider.create(data);
         }

         public static StringPrototypeBuiltins.JSStringCharAtNode.Inlined create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new StringPrototypeBuiltinsFactory.JSStringCharAtNodeGen.InlinedNodeGen(context, builtin, arguments);
         }
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringCharCodeAtNode.class)
   public static final class JSStringCharCodeAtNodeGen extends StringPrototypeBuiltins.JSStringCharCodeAtNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private JSToNumberNode charCodeAtGeneric_toNumberNode_;

      private JSStringCharCodeAtNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return (state_0 & 4) == 0 && state_0 != 0 ? this.execute_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
      }

      private Object execute_int0(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var6) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, var6.getResult());
         }

         if ((state_0 & 3) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if ((state_0 & 1) != 0 && StringPrototypeBuiltins.JSStringCharCodeAtNode.posInBounds(arguments0Value__, arguments1Value_)) {
               return this.charCodeAtInBounds(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && !StringPrototypeBuiltins.JSStringCharCodeAtNode.posInBounds(arguments0Value__, arguments1Value_)) {
               return this.charCodeAtOutOfBounds(arguments0Value__, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      private Object execute_generic1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 3) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               if ((state_0 & 1) != 0 && StringPrototypeBuiltins.JSStringCharCodeAtNode.posInBounds(arguments0Value__, arguments1Value__)) {
                  return this.charCodeAtInBounds(arguments0Value__, arguments1Value__);
               }

               if ((state_0 & 2) != 0 && !StringPrototypeBuiltins.JSStringCharCodeAtNode.posInBounds(arguments0Value__, arguments1Value__)) {
                  return this.charCodeAtOutOfBounds(arguments0Value__, arguments1Value__);
               }
            }
         }

         if ((state_0 & 4) != 0) {
            return this.charCodeAtGeneric(arguments0Value_, arguments1Value_, this.charCodeAtGeneric_toNumberNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 4) != 0) {
            return JSTypesGen.expectDouble(this.execute(frameValue));
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);

            int arguments1Value_;
            try {
               arguments1Value_ = this.arguments1_.executeInt(frameValue);
            } catch (UnexpectedResultException var6) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectDouble(this.executeAndSpecialize(arguments0Value_, var6.getResult()));
            }

            if ((state_0 & 2) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               if (!StringPrototypeBuiltins.JSStringCharCodeAtNode.posInBounds(arguments0Value__, arguments1Value_)) {
                  return this.charCodeAtOutOfBounds(arguments0Value__, arguments1Value_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
         }
      }

      @Override
      public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 4) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);

            int arguments1Value_;
            try {
               arguments1Value_ = this.arguments1_.executeInt(frameValue);
            } catch (UnexpectedResultException var6) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, var6.getResult()));
            }

            if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               if (StringPrototypeBuiltins.JSStringCharCodeAtNode.posInBounds(arguments0Value__, arguments1Value_)) {
                  return this.charCodeAtInBounds(arguments0Value__, arguments1Value_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 6) == 0 && state_0 != 0) {
               this.executeInt(frameValue);
            } else if ((state_0 & 5) == 0 && state_0 != 0) {
               this.executeDouble(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               if (arguments1Value instanceof Integer) {
                  int arguments1Value_ = (Integer)arguments1Value;
                  if ((exclude & 1) == 0 && StringPrototypeBuiltins.JSStringCharCodeAtNode.posInBounds(arguments0Value_, arguments1Value_)) {
                     int var16;
                     this.state_0_ = var16 = state_0 | 1;
                     lock.unlock();
                     hasLock = false;
                     return this.charCodeAtInBounds(arguments0Value_, arguments1Value_);
                  }

                  if ((exclude & 2) == 0 && !StringPrototypeBuiltins.JSStringCharCodeAtNode.posInBounds(arguments0Value_, arguments1Value_)) {
                     int var15;
                     this.state_0_ = var15 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.charCodeAtOutOfBounds(arguments0Value_, arguments1Value_);
                  }
               }
            }

            this.charCodeAtGeneric_toNumberNode_ = super.insert(JSToNumberNode.create());
            int var17;
            this.exclude_ = var17 = exclude | 3;
            state_0 &= -4;
            int var14;
            this.state_0_ = var14 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.charCodeAtGeneric(arguments0Value, arguments1Value, this.charCodeAtGeneric_toNumberNode_);
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
         int exclude = this.exclude_;
         Object[] s = new Object[]{"charCodeAtInBounds", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else if ((exclude & 1) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"charCodeAtOutOfBounds", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else if ((exclude & 2) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"charCodeAtGeneric", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.charCodeAtGeneric_toNumberNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringCharCodeAtNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringCharCodeAtNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(StringPrototypeBuiltins.JSStringCharCodeAtNode.Inlined.class)
      public static final class InlinedNodeGen extends StringPrototypeBuiltins.JSStringCharCodeAtNode.Inlined implements Introspection.Provider {
         @Node.Child
         private JavaScriptNode arguments0_;
         @Node.Child
         private JavaScriptNode arguments1_;
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @Node.Child
         private JSToNumberNode charCodeAtGeneric_toNumberNode_;

         private InlinedNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         }

         @Override
         public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
         }

         @Override
         protected Object executeWithArguments(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 3) != 0 && arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               if (arguments1Value instanceof Integer) {
                  int arguments1Value_ = (Integer)arguments1Value;
                  if ((state_0 & 1) != 0 && StringPrototypeBuiltins.JSStringCharCodeAtNode.posInBounds(arguments0Value_, arguments1Value_)) {
                     return this.charCodeAtInBounds(arguments0Value_, arguments1Value_);
                  }

                  if ((state_0 & 2) != 0 && !StringPrototypeBuiltins.JSStringCharCodeAtNode.posInBounds(arguments0Value_, arguments1Value_)) {
                     return this.charCodeAtOutOfBounds(arguments0Value_, arguments1Value_);
                  }
               }
            }

            if ((state_0 & 4) != 0) {
               return this.charCodeAtGeneric(arguments0Value, arguments1Value, this.charCodeAtGeneric_toNumberNode_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arguments0Value, arguments1Value);
            }
         }

         @Override
         public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            return (state_0 & 4) == 0 && state_0 != 0 ? this.execute_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
         }

         private Object execute_int0(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);

            int arguments1Value_;
            try {
               arguments1Value_ = this.arguments1_.executeInt(frameValue);
            } catch (UnexpectedResultException var6) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arguments0Value_, var6.getResult());
            }

            if ((state_0 & 3) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               if ((state_0 & 1) != 0 && StringPrototypeBuiltins.JSStringCharCodeAtNode.posInBounds(arguments0Value__, arguments1Value_)) {
                  return this.charCodeAtInBounds(arguments0Value__, arguments1Value_);
               }

               if ((state_0 & 2) != 0 && !StringPrototypeBuiltins.JSStringCharCodeAtNode.posInBounds(arguments0Value__, arguments1Value_)) {
                  return this.charCodeAtOutOfBounds(arguments0Value__, arguments1Value_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }

         private Object execute_generic1(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 3) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               if (arguments1Value_ instanceof Integer) {
                  int arguments1Value__ = (Integer)arguments1Value_;
                  if ((state_0 & 1) != 0 && StringPrototypeBuiltins.JSStringCharCodeAtNode.posInBounds(arguments0Value__, arguments1Value__)) {
                     return this.charCodeAtInBounds(arguments0Value__, arguments1Value__);
                  }

                  if ((state_0 & 2) != 0 && !StringPrototypeBuiltins.JSStringCharCodeAtNode.posInBounds(arguments0Value__, arguments1Value__)) {
                     return this.charCodeAtOutOfBounds(arguments0Value__, arguments1Value__);
                  }
               }
            }

            if ((state_0 & 4) != 0) {
               return this.charCodeAtGeneric(arguments0Value_, arguments1Value_, this.charCodeAtGeneric_toNumberNode_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
            }
         }

         @Override
         public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            if ((state_0 & 4) != 0) {
               return JSTypesGen.expectDouble(this.execute(frameValue));
            } else {
               Object arguments0Value_ = this.arguments0_.execute(frameValue);

               int arguments1Value_;
               try {
                  arguments1Value_ = this.arguments1_.executeInt(frameValue);
               } catch (UnexpectedResultException var6) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  return JSTypesGen.expectDouble(this.executeAndSpecialize(arguments0Value_, var6.getResult()));
               }

               if ((state_0 & 2) != 0 && arguments0Value_ instanceof TruffleString) {
                  TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
                  if (!StringPrototypeBuiltins.JSStringCharCodeAtNode.posInBounds(arguments0Value__, arguments1Value_)) {
                     return this.charCodeAtOutOfBounds(arguments0Value__, arguments1Value_);
                  }
               }

               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectDouble(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
            }
         }

         @Override
         public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
            int state_0 = this.state_0_;
            if ((state_0 & 4) != 0) {
               return JSTypesGen.expectInteger(this.execute(frameValue));
            } else {
               Object arguments0Value_ = this.arguments0_.execute(frameValue);

               int arguments1Value_;
               try {
                  arguments1Value_ = this.arguments1_.executeInt(frameValue);
               } catch (UnexpectedResultException var6) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, var6.getResult()));
               }

               if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
                  TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
                  if (StringPrototypeBuiltins.JSStringCharCodeAtNode.posInBounds(arguments0Value__, arguments1Value_)) {
                     return this.charCodeAtInBounds(arguments0Value__, arguments1Value_);
                  }
               }

               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
            }
         }

         @Override
         public void executeVoid(VirtualFrame frameValue) {
            int state_0 = this.state_0_;

            try {
               if ((state_0 & 6) == 0 && state_0 != 0) {
                  this.executeInt(frameValue);
               } else if ((state_0 & 5) == 0 && state_0 != 0) {
                  this.executeDouble(frameValue);
               } else {
                  this.execute(frameValue);
               }
            } catch (UnexpectedResultException var4) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
            }
         }

         private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               if (arguments0Value instanceof TruffleString) {
                  TruffleString arguments0Value_ = (TruffleString)arguments0Value;
                  if (arguments1Value instanceof Integer) {
                     int arguments1Value_ = (Integer)arguments1Value;
                     if (StringPrototypeBuiltins.JSStringCharCodeAtNode.posInBounds(arguments0Value_, arguments1Value_)) {
                        int var14;
                        this.state_0_ = var14 = state_0 | 1;
                        lock.unlock();
                        hasLock = false;
                        return this.charCodeAtInBounds(arguments0Value_, arguments1Value_);
                     }

                     if (!StringPrototypeBuiltins.JSStringCharCodeAtNode.posInBounds(arguments0Value_, arguments1Value_)) {
                        int var13;
                        this.state_0_ = var13 = state_0 | 2;
                        lock.unlock();
                        hasLock = false;
                        return this.charCodeAtOutOfBounds(arguments0Value_, arguments1Value_);
                     }
                  }
               }

               this.charCodeAtGeneric_toNumberNode_ = super.insert(JSToNumberNode.create());
               int var12;
               this.state_0_ = var12 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.charCodeAtGeneric(arguments0Value, arguments1Value, this.charCodeAtGeneric_toNumberNode_);
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
            Object[] s = new Object[]{"charCodeAtInBounds", null, null};
            if ((state_0 & 1) != 0) {
               s[1] = (byte)1;
            } else {
               s[1] = (byte)0;
            }

            data[1] = s;
            s = new Object[]{"charCodeAtOutOfBounds", null, null};
            if ((state_0 & 2) != 0) {
               s[1] = (byte)1;
            } else {
               s[1] = (byte)0;
            }

            data[2] = s;
            s = new Object[]{"charCodeAtGeneric", null, null};
            if ((state_0 & 4) != 0) {
               s[1] = (byte)1;
               ArrayList<Object> cached = new ArrayList<>();
               cached.add(Arrays.asList(this.charCodeAtGeneric_toNumberNode_));
               s[2] = cached;
            } else {
               s[1] = (byte)0;
            }

            data[3] = s;
            return Introspection.Provider.create(data);
         }

         public static StringPrototypeBuiltins.JSStringCharCodeAtNode.Inlined create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new StringPrototypeBuiltinsFactory.JSStringCharCodeAtNodeGen.InlinedNodeGen(context, builtin, arguments);
         }
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringCodePointAtNode.class)
   public static final class JSStringCodePointAtNodeGen extends StringPrototypeBuiltins.JSStringCodePointAtNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.CodePointAtByteIndexNode codePointAtRawNode_;

      private JSStringCodePointAtNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.codePointAt(arguments0Value_, arguments1Value_, this.codePointAtRawNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var6;
         try {
            int state_0 = this.state_0_;
            this.codePointAtRawNode_ = super.insert(TruffleString.CodePointAtByteIndexNode.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.codePointAt(arguments0Value, arguments1Value, this.codePointAtRawNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
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
         Object[] s = new Object[]{"codePointAt", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.codePointAtRawNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringCodePointAtNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringCodePointAtNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringConcatNode.class)
   public static final class JSStringConcatNodeGen extends StringPrototypeBuiltins.JSStringConcatNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private StringPrototypeBuiltinsFactory.JSStringConcatNodeGen.ConcatData concat_cache;

      private JSStringConcatNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
            Object[] arguments1Value__ = (Object[])arguments1Value_;
            StringPrototypeBuiltinsFactory.JSStringConcatNodeGen.ConcatData s0_ = this.concat_cache;
            if (s0_ != null) {
               return this.concat(arguments0Value_, arguments1Value__, s0_.toString2Node_, s0_.appendStringNode_, s0_.sbToStringNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var8;
         try {
            int state_0 = this.state_0_;
            if (!(arguments1Value instanceof Object[])) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            }

            Object[] arguments1Value_ = (Object[])arguments1Value;
            StringPrototypeBuiltinsFactory.JSStringConcatNodeGen.ConcatData s0_ = super.insert(
               new StringPrototypeBuiltinsFactory.JSStringConcatNodeGen.ConcatData()
            );
            s0_.toString2Node_ = s0_.insertAccessor(JSToStringNode.create());
            s0_.appendStringNode_ = s0_.insertAccessor(TruffleStringBuilder.AppendStringNode.create());
            s0_.sbToStringNode_ = s0_.insertAccessor(TruffleStringBuilder.ToStringNode.create());
            VarHandle.storeStoreFence();
            this.concat_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.concat(arguments0Value, arguments1Value_, s0_.toString2Node_, s0_.appendStringNode_, s0_.sbToStringNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var8;
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
         Object[] s = new Object[]{"concat", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            StringPrototypeBuiltinsFactory.JSStringConcatNodeGen.ConcatData s0_ = this.concat_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.toString2Node_, s0_.appendStringNode_, s0_.sbToStringNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringConcatNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringConcatNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(StringPrototypeBuiltins.JSStringConcatNode.class)
      private static final class ConcatData extends Node {
         @Node.Child
         JSToStringNode toString2Node_;
         @Node.Child
         TruffleStringBuilder.AppendStringNode appendStringNode_;
         @Node.Child
         TruffleStringBuilder.ToStringNode sbToStringNode_;

         ConcatData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringEndsWithNode.class)
   public static final class JSStringEndsWithNodeGen extends StringPrototypeBuiltins.JSStringEndsWithNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.RegionEqualByteIndexNode regionEqualsNode;
      @Node.Child
      private JSToStringNode endsWithGeneric_toString2Node_;
      @Node.Child
      private IsRegExpNode endsWithGeneric_isRegExpNode_;

      private JSStringEndsWithNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               if (arguments1Value_ instanceof TruffleString) {
                  TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
                  if (JSGuards.isUndefined(arguments2Value_)) {
                     return this.endsWithStringUndefined(arguments0Value__, arguments1Value__, arguments2Value_, this.regionEqualsNode);
                  }
               }
            }

            if ((state_0 & 2) != 0 && (!JSGuards.isStringString(arguments0Value_, arguments1Value_) || !JSGuards.isUndefined(arguments2Value_))) {
               return this.endsWithGeneric(
                  arguments0Value_,
                  arguments1Value_,
                  arguments2Value_,
                  this.endsWithGeneric_toString2Node_,
                  this.endsWithGeneric_isRegExpNode_,
                  this.regionEqualsNode
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               if (arguments1Value_ instanceof TruffleString) {
                  TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
                  if (JSGuards.isUndefined(arguments2Value_)) {
                     return this.endsWithStringUndefined(arguments0Value__, arguments1Value__, arguments2Value_, this.regionEqualsNode);
                  }
               }
            }

            if ((state_0 & 2) != 0 && (!JSGuards.isStringString(arguments0Value_, arguments1Value_) || !JSGuards.isUndefined(arguments2Value_))) {
               return this.endsWithGeneric(
                  arguments0Value_,
                  arguments1Value_,
                  arguments2Value_,
                  this.endsWithGeneric_toString2Node_,
                  this.endsWithGeneric_isRegExpNode_,
                  this.regionEqualsNode
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               if (arguments1Value instanceof TruffleString) {
                  TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                  if (JSGuards.isUndefined(arguments2Value)) {
                     this.regionEqualsNode = super.insert(
                        this.regionEqualsNode == null ? TruffleString.RegionEqualByteIndexNode.create() : this.regionEqualsNode
                     );
                     int var14;
                     this.state_0_ = var14 = state_0 | 1;
                     lock.unlock();
                     hasLock = false;
                     return this.endsWithStringUndefined(arguments0Value_, arguments1Value_, arguments2Value, this.regionEqualsNode);
                  }
               }
            }

            if (JSGuards.isStringString(arguments0Value, arguments1Value) && JSGuards.isUndefined(arguments2Value)) {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
               );
            } else {
               this.endsWithGeneric_toString2Node_ = super.insert(JSToStringNode.create());
               this.endsWithGeneric_isRegExpNode_ = super.insert(IsRegExpNode.create(this.getContext()));
               this.regionEqualsNode = super.insert(this.regionEqualsNode == null ? TruffleString.RegionEqualByteIndexNode.create() : this.regionEqualsNode);
               int var13;
               this.state_0_ = var13 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.endsWithGeneric(
                  arguments0Value,
                  arguments1Value,
                  arguments2Value,
                  this.endsWithGeneric_toString2Node_,
                  this.endsWithGeneric_isRegExpNode_,
                  this.regionEqualsNode
               );
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
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"endsWithStringUndefined", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.regionEqualsNode));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"endsWithGeneric", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.endsWithGeneric_toString2Node_, this.endsWithGeneric_isRegExpNode_, this.regionEqualsNode));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringEndsWithNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringEndsWithNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringIncludesNode.class)
   public static final class JSStringIncludesNodeGen extends StringPrototypeBuiltins.JSStringIncludesNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.ByteIndexOfStringNode indexOfStringNode;
      @Node.Child
      private JSToStringNode includesGeneric_toString2Node_;
      @Node.Child
      private IsRegExpNode includesGeneric_isRegExpNode_;

      private JSStringIncludesNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               if (arguments1Value_ instanceof TruffleString) {
                  TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
                  if (JSGuards.isUndefined(arguments2Value_)) {
                     return this.includesString(arguments0Value__, arguments1Value__, arguments2Value_, this.indexOfStringNode);
                  }
               }
            }

            if ((state_0 & 2) != 0 && (!JSGuards.isStringString(arguments0Value_, arguments1Value_) || !JSGuards.isUndefined(arguments2Value_))) {
               return this.includesGeneric(
                  arguments0Value_,
                  arguments1Value_,
                  arguments2Value_,
                  this.includesGeneric_toString2Node_,
                  this.includesGeneric_isRegExpNode_,
                  this.indexOfStringNode
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               if (arguments1Value_ instanceof TruffleString) {
                  TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
                  if (JSGuards.isUndefined(arguments2Value_)) {
                     return this.includesString(arguments0Value__, arguments1Value__, arguments2Value_, this.indexOfStringNode);
                  }
               }
            }

            if ((state_0 & 2) != 0 && (!JSGuards.isStringString(arguments0Value_, arguments1Value_) || !JSGuards.isUndefined(arguments2Value_))) {
               return this.includesGeneric(
                  arguments0Value_,
                  arguments1Value_,
                  arguments2Value_,
                  this.includesGeneric_toString2Node_,
                  this.includesGeneric_isRegExpNode_,
                  this.indexOfStringNode
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               if (arguments1Value instanceof TruffleString) {
                  TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                  if (JSGuards.isUndefined(arguments2Value)) {
                     this.indexOfStringNode = super.insert(
                        this.indexOfStringNode == null ? TruffleString.ByteIndexOfStringNode.create() : this.indexOfStringNode
                     );
                     int var14;
                     this.state_0_ = var14 = state_0 | 1;
                     lock.unlock();
                     hasLock = false;
                     return this.includesString(arguments0Value_, arguments1Value_, arguments2Value, this.indexOfStringNode);
                  }
               }
            }

            if (JSGuards.isStringString(arguments0Value, arguments1Value) && JSGuards.isUndefined(arguments2Value)) {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
               );
            } else {
               this.includesGeneric_toString2Node_ = super.insert(JSToStringNode.create());
               this.includesGeneric_isRegExpNode_ = super.insert(IsRegExpNode.create(this.getContext()));
               this.indexOfStringNode = super.insert(this.indexOfStringNode == null ? TruffleString.ByteIndexOfStringNode.create() : this.indexOfStringNode);
               int var13;
               this.state_0_ = var13 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.includesGeneric(
                  arguments0Value,
                  arguments1Value,
                  arguments2Value,
                  this.includesGeneric_toString2Node_,
                  this.includesGeneric_isRegExpNode_,
                  this.indexOfStringNode
               );
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
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"includesString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.indexOfStringNode));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"includesGeneric", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.includesGeneric_toString2Node_, this.includesGeneric_isRegExpNode_, this.indexOfStringNode));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringIncludesNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringIncludesNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringIndexOfNode.class)
   public static final class JSStringIndexOfNodeGen extends StringPrototypeBuiltins.JSStringIndexOfNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private TruffleString.ByteIndexOfStringNode indexOfStringNode;
      @Node.Child
      private JSToStringNode indexOfGeneric_toString2Node_;

      private JSStringIndexOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return (state_0 & 5) == 0 && state_0 != 0 ? this.execute_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
      }

      private Object execute_int0(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var8) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, var8.getResult());
         }

         assert (state_0 & 2) != 0;

         if (arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               return this.indexOfStringInt(arguments0Value__, arguments1Value__, arguments2Value_, this.indexOfStringNode);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      private Object execute_generic1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if ((state_0 & 3) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               if ((state_0 & 1) != 0 && JSGuards.isUndefined(arguments2Value_)) {
                  return this.indexOfStringUndefined(arguments0Value__, arguments1Value__, arguments2Value_, this.indexOfStringNode);
               }

               if ((state_0 & 2) != 0 && arguments2Value_ instanceof Integer) {
                  int arguments2Value__ = (Integer)arguments2Value_;
                  return this.indexOfStringInt(arguments0Value__, arguments1Value__, arguments2Value__, this.indexOfStringNode);
               }
            }
         }

         if ((state_0 & 4) == 0 || JSGuards.isStringString(arguments0Value_, arguments1Value_) && JSGuards.isUndefined(arguments2Value_)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         } else {
            return this.indexOfGeneric(arguments0Value_, arguments1Value_, arguments2Value_, this.indexOfGeneric_toString2Node_, this.indexOfStringNode);
         }
      }

      @Override
      public int executeInt(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         return (state_0 & 5) == 0 && state_0 != 0 ? this.executeInt_int2(state_0, frameValue) : this.executeInt_generic3(state_0, frameValue);
      }

      private int executeInt_int2(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var8) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, var8.getResult());
         }

         assert (state_0 & 2) != 0;

         if (arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               return this.indexOfStringInt(arguments0Value__, arguments1Value__, arguments2Value_, this.indexOfStringNode);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      private int executeInt_generic3(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if ((state_0 & 3) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               if ((state_0 & 1) != 0 && JSGuards.isUndefined(arguments2Value_)) {
                  return this.indexOfStringUndefined(arguments0Value__, arguments1Value__, arguments2Value_, this.indexOfStringNode);
               }

               if ((state_0 & 2) != 0 && arguments2Value_ instanceof Integer) {
                  int arguments2Value__ = (Integer)arguments2Value_;
                  return this.indexOfStringInt(arguments0Value__, arguments1Value__, arguments2Value__, this.indexOfStringNode);
               }
            }
         }

         if ((state_0 & 4) == 0 || JSGuards.isStringString(arguments0Value_, arguments1Value_) && JSGuards.isUndefined(arguments2Value_)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         } else {
            return this.indexOfGeneric(arguments0Value_, arguments1Value_, arguments2Value_, this.indexOfGeneric_toString2Node_, this.indexOfStringNode);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeInt(frameValue);
      }

      private int executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               if (arguments1Value instanceof TruffleString) {
                  TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                  if (JSGuards.isUndefined(arguments2Value)) {
                     this.indexOfStringNode = super.insert(
                        this.indexOfStringNode == null ? TruffleString.ByteIndexOfStringNode.create() : this.indexOfStringNode
                     );
                     int var18;
                     this.state_0_ = var18 = state_0 | 1;
                     lock.unlock();
                     hasLock = false;
                     return this.indexOfStringUndefined(arguments0Value_, arguments1Value_, arguments2Value, this.indexOfStringNode);
                  }

                  if (exclude == 0 && arguments2Value instanceof Integer) {
                     int arguments2Value_ = (Integer)arguments2Value;
                     this.indexOfStringNode = super.insert(
                        this.indexOfStringNode == null ? TruffleString.ByteIndexOfStringNode.create() : this.indexOfStringNode
                     );
                     int var17;
                     this.state_0_ = var17 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.indexOfStringInt(arguments0Value_, arguments1Value_, arguments2Value_, this.indexOfStringNode);
                  }
               }
            }

            if (JSGuards.isStringString(arguments0Value, arguments1Value) && JSGuards.isUndefined(arguments2Value)) {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
               );
            } else {
               this.indexOfGeneric_toString2Node_ = super.insert(JSToStringNode.create());
               this.indexOfStringNode = super.insert(this.indexOfStringNode == null ? TruffleString.ByteIndexOfStringNode.create() : this.indexOfStringNode);
               int var19;
               this.exclude_ = var19 = exclude | 1;
               state_0 &= -3;
               int var16;
               this.state_0_ = var16 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.indexOfGeneric(arguments0Value, arguments1Value, arguments2Value, this.indexOfGeneric_toString2Node_, this.indexOfStringNode);
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
         int exclude = this.exclude_;
         Object[] s = new Object[]{"indexOfStringUndefined", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.indexOfStringNode));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"indexOfStringInt", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.indexOfStringNode));
            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"indexOfGeneric", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.indexOfGeneric_toString2Node_, this.indexOfStringNode));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringIndexOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringIndexOfNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringLastIndexOfNode.class)
   public static final class JSStringLastIndexOfNodeGen extends StringPrototypeBuiltins.JSStringLastIndexOfNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.LastByteIndexOfStringNode lastIndexOfNode;
      @Node.Child
      private JSToStringNode lastIndexOf_toString2Node_;
      @Node.Child
      private JSToNumberNode lastIndexOf_toNumberNode_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile lastIndexOf_posNaN_;

      private JSStringLastIndexOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return (state_0 & 5) == 0 && state_0 != 0 ? this.execute_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
      }

      private Object execute_int0(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var8) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, var8.getResult());
         }

         assert (state_0 & 2) != 0;

         if (arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               return this.lastIndexOfString(arguments0Value__, arguments1Value__, arguments2Value_, this.lastIndexOfNode);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      private Object execute_generic1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if ((state_0 & 3) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               if ((state_0 & 1) != 0 && JSGuards.isUndefined(arguments2Value_)) {
                  return this.lastIndexOfString(arguments0Value__, arguments1Value__, arguments2Value_, this.lastIndexOfNode);
               }

               if ((state_0 & 2) != 0 && arguments2Value_ instanceof Integer) {
                  int arguments2Value__ = (Integer)arguments2Value_;
                  return this.lastIndexOfString(arguments0Value__, arguments1Value__, arguments2Value__, this.lastIndexOfNode);
               }
            }
         }

         if ((state_0 & 4) == 0 || JSGuards.isStringString(arguments0Value_, arguments1Value_) && JSGuards.isUndefined(arguments2Value_)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         } else {
            return this.lastIndexOf(
               arguments0Value_,
               arguments1Value_,
               arguments2Value_,
               this.lastIndexOf_toString2Node_,
               this.lastIndexOf_toNumberNode_,
               this.lastIndexOf_posNaN_,
               this.lastIndexOfNode
            );
         }
      }

      @Override
      public int executeInt(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         return (state_0 & 5) == 0 && state_0 != 0 ? this.executeInt_int2(state_0, frameValue) : this.executeInt_generic3(state_0, frameValue);
      }

      private int executeInt_int2(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var8) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, var8.getResult());
         }

         assert (state_0 & 2) != 0;

         if (arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               return this.lastIndexOfString(arguments0Value__, arguments1Value__, arguments2Value_, this.lastIndexOfNode);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      private int executeInt_generic3(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if ((state_0 & 3) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               if ((state_0 & 1) != 0 && JSGuards.isUndefined(arguments2Value_)) {
                  return this.lastIndexOfString(arguments0Value__, arguments1Value__, arguments2Value_, this.lastIndexOfNode);
               }

               if ((state_0 & 2) != 0 && arguments2Value_ instanceof Integer) {
                  int arguments2Value__ = (Integer)arguments2Value_;
                  return this.lastIndexOfString(arguments0Value__, arguments1Value__, arguments2Value__, this.lastIndexOfNode);
               }
            }
         }

         if ((state_0 & 4) == 0 || JSGuards.isStringString(arguments0Value_, arguments1Value_) && JSGuards.isUndefined(arguments2Value_)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         } else {
            return this.lastIndexOf(
               arguments0Value_,
               arguments1Value_,
               arguments2Value_,
               this.lastIndexOf_toString2Node_,
               this.lastIndexOf_toNumberNode_,
               this.lastIndexOf_posNaN_,
               this.lastIndexOfNode
            );
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeInt(frameValue);
      }

      private int executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               if (arguments1Value instanceof TruffleString) {
                  TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                  if (JSGuards.isUndefined(arguments2Value)) {
                     this.lastIndexOfNode = super.insert(this.lastIndexOfNode == null ? TruffleString.LastByteIndexOfStringNode.create() : this.lastIndexOfNode);
                     int var16;
                     this.state_0_ = var16 = state_0 | 1;
                     lock.unlock();
                     hasLock = false;
                     return this.lastIndexOfString(arguments0Value_, arguments1Value_, arguments2Value, this.lastIndexOfNode);
                  }

                  if (arguments2Value instanceof Integer) {
                     int arguments2Value_ = (Integer)arguments2Value;
                     this.lastIndexOfNode = super.insert(this.lastIndexOfNode == null ? TruffleString.LastByteIndexOfStringNode.create() : this.lastIndexOfNode);
                     int var15;
                     this.state_0_ = var15 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.lastIndexOfString(arguments0Value_, arguments1Value_, arguments2Value_, this.lastIndexOfNode);
                  }
               }
            }

            if (JSGuards.isStringString(arguments0Value, arguments1Value) && JSGuards.isUndefined(arguments2Value)) {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
               );
            } else {
               this.lastIndexOf_toString2Node_ = super.insert(JSToStringNode.create());
               this.lastIndexOf_toNumberNode_ = super.insert(JSToNumberNode.create());
               this.lastIndexOf_posNaN_ = ConditionProfile.create();
               this.lastIndexOfNode = super.insert(this.lastIndexOfNode == null ? TruffleString.LastByteIndexOfStringNode.create() : this.lastIndexOfNode);
               int var14;
               this.state_0_ = var14 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.lastIndexOf(
                  arguments0Value,
                  arguments1Value,
                  arguments2Value,
                  this.lastIndexOf_toString2Node_,
                  this.lastIndexOf_toNumberNode_,
                  this.lastIndexOf_posNaN_,
                  this.lastIndexOfNode
               );
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
         Object[] s = new Object[]{"lastIndexOfString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.lastIndexOfNode));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"lastIndexOfString", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.lastIndexOfNode));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"lastIndexOf", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.lastIndexOf_toString2Node_, this.lastIndexOf_toNumberNode_, this.lastIndexOf_posNaN_, this.lastIndexOfNode));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringLastIndexOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringLastIndexOfNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringLocaleCompareIntlNode.class)
   public static final class JSStringLocaleCompareIntlNodeGen extends StringPrototypeBuiltins.JSStringLocaleCompareIntlNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @Node.Child
      private JavaScriptNode arguments3_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToStringNode toString2Node_;

      private JSStringLocaleCompareIntlNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
         this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         if (state_0 != 0) {
            return this.localeCompare(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, this.toString2Node_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
         }
      }

      @Override
      public int executeInt(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         if (state_0 != 0) {
            return this.localeCompare(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, this.toString2Node_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeInt(frameValue);
      }

      private int executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var8;
         try {
            int state_0 = this.state_0_;
            this.toString2Node_ = super.insert(JSToStringNode.create());
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.localeCompare(arguments0Value, arguments1Value, arguments2Value, arguments3Value, this.toString2Node_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var8;
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
         Object[] s = new Object[]{"localeCompare", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toString2Node_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringLocaleCompareIntlNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringLocaleCompareIntlNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringLocaleCompareNode.class)
   public static final class JSStringLocaleCompareNodeGen extends StringPrototypeBuiltins.JSStringLocaleCompareNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToStringNode toString2Node_;

      private JSStringLocaleCompareNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.localeCompare(arguments0Value_, arguments1Value_, this.toString2Node_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public int executeInt(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            return this.localeCompare(arguments0Value_, arguments1Value_, this.toString2Node_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeInt(frameValue);
      }

      private int executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var6;
         try {
            int state_0 = this.state_0_;
            this.toString2Node_ = super.insert(JSToStringNode.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.localeCompare(arguments0Value, arguments1Value, this.toString2Node_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
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
         Object[] s = new Object[]{"localeCompare", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toString2Node_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringLocaleCompareNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringLocaleCompareNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringMatchES5Node.class)
   public static final class JSStringMatchES5NodeGen extends StringPrototypeBuiltins.JSStringMatchES5Node implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSStringMatchES5NodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.matchRegExpNotGlobal(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"matchRegExpNotGlobal", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringMatchES5Node create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringMatchES5NodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringMatchNode.class)
   public static final class JSStringMatchNodeGen extends StringPrototypeBuiltins.JSStringMatchNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSStringMatchNodeGen(JSContext context, JSBuiltin builtin, boolean matchAll, JavaScriptNode[] arguments) {
         super(context, builtin, matchAll);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.match(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"match", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringMatchNode create(JSContext context, JSBuiltin builtin, boolean matchAll, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringMatchNodeGen(context, builtin, matchAll, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringNormalizeNode.class)
   public static final class JSStringNormalizeNodeGen extends StringPrototypeBuiltins.JSStringNormalizeNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private StringPrototypeBuiltinsFactory.JSStringNormalizeNodeGen.NormalizeData normalize_cache;

      private JSStringNormalizeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            StringPrototypeBuiltinsFactory.JSStringNormalizeNodeGen.NormalizeData s0_ = this.normalize_cache;
            if (s0_ != null) {
               return this.normalize(arguments0Value_, arguments1Value_, s0_.stringEqualsNode_, s0_.toJavaStringNode_, s0_.fromJavaStringNode_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private TruffleString executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString var7;
         try {
            int state_0 = this.state_0_;
            StringPrototypeBuiltinsFactory.JSStringNormalizeNodeGen.NormalizeData s0_ = super.insert(
               new StringPrototypeBuiltinsFactory.JSStringNormalizeNodeGen.NormalizeData()
            );
            s0_.stringEqualsNode_ = s0_.insertAccessor(TruffleString.EqualNode.create());
            s0_.toJavaStringNode_ = s0_.insertAccessor(TruffleString.ToJavaStringNode.create());
            s0_.fromJavaStringNode_ = s0_.insertAccessor(TruffleString.FromJavaStringNode.create());
            VarHandle.storeStoreFence();
            this.normalize_cache = s0_;
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.normalize(arguments0Value, arguments1Value, s0_.stringEqualsNode_, s0_.toJavaStringNode_, s0_.fromJavaStringNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var7;
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
         Object[] s = new Object[]{"normalize", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            StringPrototypeBuiltinsFactory.JSStringNormalizeNodeGen.NormalizeData s0_ = this.normalize_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.stringEqualsNode_, s0_.toJavaStringNode_, s0_.fromJavaStringNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringNormalizeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringNormalizeNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(StringPrototypeBuiltins.JSStringNormalizeNode.class)
      private static final class NormalizeData extends Node {
         @Node.Child
         TruffleString.EqualNode stringEqualsNode_;
         @Node.Child
         TruffleString.ToJavaStringNode toJavaStringNode_;
         @Node.Child
         TruffleString.FromJavaStringNode fromJavaStringNode_;

         NormalizeData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringPadNode.class)
   public static final class JSStringPadNodeGen extends StringPrototypeBuiltins.JSStringPadNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private StringPrototypeBuiltinsFactory.JSStringPadNodeGen.PadData pad_cache;

      private JSStringPadNodeGen(JSContext context, JSBuiltin builtin, boolean atStart, JavaScriptNode[] arguments) {
         super(context, builtin, atStart);
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
         if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
            Object[] arguments1Value__ = (Object[])arguments1Value_;
            StringPrototypeBuiltinsFactory.JSStringPadNodeGen.PadData s0_ = this.pad_cache;
            if (s0_ != null) {
               return this.pad(
                  arguments0Value_, arguments1Value__, s0_.toString2Node_, s0_.appendStringNode_, s0_.appendSubStringNode_, s0_.builderToStringNode_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var8;
         try {
            int state_0 = this.state_0_;
            if (!(arguments1Value instanceof Object[])) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            }

            Object[] arguments1Value_ = (Object[])arguments1Value;
            StringPrototypeBuiltinsFactory.JSStringPadNodeGen.PadData s0_ = super.insert(new StringPrototypeBuiltinsFactory.JSStringPadNodeGen.PadData());
            s0_.toString2Node_ = s0_.insertAccessor(JSToStringNode.create());
            s0_.appendStringNode_ = s0_.insertAccessor(TruffleStringBuilder.AppendStringNode.create());
            s0_.appendSubStringNode_ = s0_.insertAccessor(TruffleStringBuilder.AppendSubstringByteIndexNode.create());
            s0_.builderToStringNode_ = s0_.insertAccessor(TruffleStringBuilder.ToStringNode.create());
            VarHandle.storeStoreFence();
            this.pad_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.pad(arguments0Value, arguments1Value_, s0_.toString2Node_, s0_.appendStringNode_, s0_.appendSubStringNode_, s0_.builderToStringNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var8;
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
         Object[] s = new Object[]{"pad", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            StringPrototypeBuiltinsFactory.JSStringPadNodeGen.PadData s0_ = this.pad_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.toString2Node_, s0_.appendStringNode_, s0_.appendSubStringNode_, s0_.builderToStringNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringPadNode create(JSContext context, JSBuiltin builtin, boolean atStart, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringPadNodeGen(context, builtin, atStart, arguments);
      }

      @GeneratedBy(StringPrototypeBuiltins.JSStringPadNode.class)
      private static final class PadData extends Node {
         @Node.Child
         JSToStringNode toString2Node_;
         @Node.Child
         TruffleStringBuilder.AppendStringNode appendStringNode_;
         @Node.Child
         TruffleStringBuilder.AppendSubstringByteIndexNode appendSubStringNode_;
         @Node.Child
         TruffleStringBuilder.ToStringNode builderToStringNode_;

         PadData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringRepeatNode.class)
   public static final class JSStringRepeatNodeGen extends StringPrototypeBuiltins.JSStringRepeatNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToNumberNode toNumberNode_;
      @Node.Child
      private TruffleString.RepeatNode repeatNode_;

      private JSStringRepeatNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.repeat(arguments0Value_, arguments1Value_, this.toNumberNode_, this.repeatNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var6;
         try {
            int state_0 = this.state_0_;
            this.toNumberNode_ = super.insert(JSToNumberNode.create());
            this.repeatNode_ = super.insert(TruffleString.RepeatNode.create());
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = this.repeat(arguments0Value, arguments1Value, this.toNumberNode_, this.repeatNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
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
         Object[] s = new Object[]{"repeat", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toNumberNode_, this.repeatNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringRepeatNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringRepeatNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringReplaceAllNode.class)
   public static final class JSStringReplaceAllNodeGen extends StringPrototypeBuiltins.JSStringReplaceAllNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private StringPrototypeBuiltinsFactory.JSStringReplaceAllNodeGen.ReplaceStringCachedData replaceStringCached_cache;

      private JSStringReplaceAllNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
      }

      @ExplodeLoop
      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 3) != 0 && arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               if (arguments2Value_ instanceof TruffleString) {
                  TruffleString arguments2Value__ = (TruffleString)arguments2Value_;
                  if ((state_0 & 1) != 0) {
                     for (StringPrototypeBuiltinsFactory.JSStringReplaceAllNodeGen.ReplaceStringCachedData s0_ = this.replaceStringCached_cache;
                        s0_ != null;
                        s0_ = s0_.next_
                     ) {
                        if (JSGuards.stringEquals(s0_.equalsNode_, s0_.cachedReplaceValue_, arguments2Value__)) {
                           return this.replaceStringCached(
                              arguments0Value_, arguments1Value__, arguments2Value__, s0_.cachedReplaceValue_, s0_.cachedParsedReplaceValue_, s0_.equalsNode_
                           );
                        }
                     }
                  }

                  if ((state_0 & 2) != 0) {
                     return this.replaceString(arguments0Value_, arguments1Value__, arguments2Value__);
                  }
               }
            }

            if ((state_0 & 4) != 0 && !JSGuards.isStringString(arguments1Value_, arguments2Value_)) {
               return this.replaceGeneric(arguments0Value_, arguments1Value_, arguments2Value_);
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
            int exclude = this.exclude_;
            if (arguments1Value instanceof TruffleString) {
               TruffleString arguments1Value_ = (TruffleString)arguments1Value;
               if (arguments2Value instanceof TruffleString) {
                  TruffleString arguments2Value_ = (TruffleString)arguments2Value;
                  if (exclude == 0) {
                     int count0_ = 0;
                     StringPrototypeBuiltinsFactory.JSStringReplaceAllNodeGen.ReplaceStringCachedData s0_ = this.replaceStringCached_cache;
                     if ((state_0 & 1) != 0) {
                        while (s0_ != null && !JSGuards.stringEquals(s0_.equalsNode_, s0_.cachedReplaceValue_, arguments2Value_)) {
                           s0_ = s0_.next_;
                           count0_++;
                        }
                     }

                     if (s0_ == null) {
                        TruffleString.EqualNode equalsNode__ = super.insert(TruffleString.EqualNode.create());
                        if (JSGuards.stringEquals(equalsNode__, arguments2Value_, arguments2Value_) && count0_ < 3) {
                           s0_ = super.insert(
                              new StringPrototypeBuiltinsFactory.JSStringReplaceAllNodeGen.ReplaceStringCachedData(this.replaceStringCached_cache)
                           );
                           s0_.cachedReplaceValue_ = arguments2Value_;
                           s0_.cachedParsedReplaceValue_ = this.parseReplaceValue(arguments2Value_);
                           s0_.equalsNode_ = s0_.insertAccessor(equalsNode__);
                           VarHandle.storeStoreFence();
                           this.replaceStringCached_cache = s0_;
                           this.state_0_ = state_0 |= 1;
                        }
                     }

                     if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.replaceStringCached(
                           arguments0Value, arguments1Value_, arguments2Value_, s0_.cachedReplaceValue_, s0_.cachedParsedReplaceValue_, s0_.equalsNode_
                        );
                     }
                  }

                  int var20;
                  this.exclude_ = var20 = exclude | 1;
                  this.replaceStringCached_cache = null;
                  state_0 &= -2;
                  int var19;
                  this.state_0_ = var19 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.replaceString(arguments0Value, arguments1Value_, arguments2Value_);
               }
            }

            if (JSGuards.isStringString(arguments1Value, arguments2Value)) {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
               );
            } else {
               int var17;
               this.state_0_ = var17 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.replaceGeneric(arguments0Value, arguments1Value, arguments2Value);
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
            if ((state_0 & state_0 - 1) == 0) {
               StringPrototypeBuiltinsFactory.JSStringReplaceAllNodeGen.ReplaceStringCachedData s0_ = this.replaceStringCached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"replaceStringCached", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (StringPrototypeBuiltinsFactory.JSStringReplaceAllNodeGen.ReplaceStringCachedData s0_ = this.replaceStringCached_cache;
               s0_ != null;
               s0_ = s0_.next_
            ) {
               cached.add(Arrays.asList(s0_.cachedReplaceValue_, s0_.cachedParsedReplaceValue_, s0_.equalsNode_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"replaceString", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"replaceGeneric", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringReplaceAllNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringReplaceAllNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(StringPrototypeBuiltins.JSStringReplaceAllNode.class)
      private static final class ReplaceStringCachedData extends Node {
         @Node.Child
         StringPrototypeBuiltinsFactory.JSStringReplaceAllNodeGen.ReplaceStringCachedData next_;
         @CompilerDirectives.CompilationFinal
         TruffleString cachedReplaceValue_;
         @CompilerDirectives.CompilationFinal(dimensions = 1)
         ReplaceStringParser.Token[] cachedParsedReplaceValue_;
         @Node.Child
         TruffleString.EqualNode equalsNode_;

         ReplaceStringCachedData(StringPrototypeBuiltinsFactory.JSStringReplaceAllNodeGen.ReplaceStringCachedData next_) {
            this.next_ = next_;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringReplaceES5Node.class)
   public static final class JSStringReplaceES5NodeGen extends StringPrototypeBuiltins.JSStringReplaceES5Node implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;

      private JSStringReplaceES5NodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         return this.replace(arguments0Value_, arguments1Value_, arguments2Value_);
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
         Object[] s = new Object[]{"replace", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringReplaceES5Node create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringReplaceES5NodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringReplaceNode.class)
   public static final class JSStringReplaceNodeGen extends StringPrototypeBuiltins.JSStringReplaceNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private StringPrototypeBuiltinsFactory.JSStringReplaceNodeGen.ReplaceStringCachedData replaceStringCached_cache;

      private JSStringReplaceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
      }

      @ExplodeLoop
      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if ((state_0 & 3) != 0 && arguments1Value_ instanceof TruffleString) {
            TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
            if (arguments2Value_ instanceof TruffleString) {
               TruffleString arguments2Value__ = (TruffleString)arguments2Value_;
               if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
                  TruffleString arguments0Value__ = (TruffleString)arguments0Value_;

                  for (StringPrototypeBuiltinsFactory.JSStringReplaceNodeGen.ReplaceStringCachedData s0_ = this.replaceStringCached_cache;
                     s0_ != null;
                     s0_ = s0_.next_
                  ) {
                     if (JSGuards.stringEquals(s0_.equalsNode_, s0_.cachedReplaceValue_, arguments2Value__)) {
                        return this.replaceStringCached(
                           arguments0Value__, arguments1Value__, arguments2Value__, s0_.cachedReplaceValue_, s0_.cachedParsedReplaceValue_, s0_.equalsNode_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  return this.replaceString(arguments0Value_, arguments1Value__, arguments2Value__);
               }
            }
         }

         if ((state_0 & 4) != 0 && !JSGuards.isStringString(arguments1Value_, arguments2Value_)) {
            return this.replaceGeneric(arguments0Value_, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
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
            int exclude = this.exclude_;
            if (arguments1Value instanceof TruffleString) {
               TruffleString arguments1Value_ = (TruffleString)arguments1Value;
               if (arguments2Value instanceof TruffleString) {
                  TruffleString arguments2Value_ = (TruffleString)arguments2Value;
                  if (exclude == 0 && arguments0Value instanceof TruffleString) {
                     TruffleString arguments0Value_ = (TruffleString)arguments0Value;
                     int count0_ = 0;
                     StringPrototypeBuiltinsFactory.JSStringReplaceNodeGen.ReplaceStringCachedData s0_ = this.replaceStringCached_cache;
                     if ((state_0 & 1) != 0) {
                        while (s0_ != null && !JSGuards.stringEquals(s0_.equalsNode_, s0_.cachedReplaceValue_, arguments2Value_)) {
                           s0_ = s0_.next_;
                           count0_++;
                        }
                     }

                     if (s0_ == null) {
                        TruffleString.EqualNode equalsNode__ = super.insert(TruffleString.EqualNode.create());
                        if (JSGuards.stringEquals(equalsNode__, arguments2Value_, arguments2Value_) && count0_ < 3) {
                           s0_ = super.insert(new StringPrototypeBuiltinsFactory.JSStringReplaceNodeGen.ReplaceStringCachedData(this.replaceStringCached_cache));
                           s0_.cachedReplaceValue_ = arguments2Value_;
                           s0_.cachedParsedReplaceValue_ = this.parseReplaceValue(arguments2Value_);
                           s0_.equalsNode_ = s0_.insertAccessor(equalsNode__);
                           VarHandle.storeStoreFence();
                           this.replaceStringCached_cache = s0_;
                           this.state_0_ = state_0 |= 1;
                        }
                     }

                     if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.replaceStringCached(
                           arguments0Value_, arguments1Value_, arguments2Value_, s0_.cachedReplaceValue_, s0_.cachedParsedReplaceValue_, s0_.equalsNode_
                        );
                     }
                  }

                  int var21;
                  this.exclude_ = var21 = exclude | 1;
                  this.replaceStringCached_cache = null;
                  state_0 &= -2;
                  int var20;
                  this.state_0_ = var20 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.replaceString(arguments0Value, arguments1Value_, arguments2Value_);
               }
            }

            if (JSGuards.isStringString(arguments1Value, arguments2Value)) {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
               );
            } else {
               int var18;
               this.state_0_ = var18 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.replaceGeneric(arguments0Value, arguments1Value, arguments2Value);
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
            if ((state_0 & state_0 - 1) == 0) {
               StringPrototypeBuiltinsFactory.JSStringReplaceNodeGen.ReplaceStringCachedData s0_ = this.replaceStringCached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"replaceStringCached", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (StringPrototypeBuiltinsFactory.JSStringReplaceNodeGen.ReplaceStringCachedData s0_ = this.replaceStringCached_cache;
               s0_ != null;
               s0_ = s0_.next_
            ) {
               cached.add(Arrays.asList(s0_.cachedReplaceValue_, s0_.cachedParsedReplaceValue_, s0_.equalsNode_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"replaceString", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"replaceGeneric", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringReplaceNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringReplaceNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(StringPrototypeBuiltins.JSStringReplaceNode.class)
      private static final class ReplaceStringCachedData extends Node {
         @Node.Child
         StringPrototypeBuiltinsFactory.JSStringReplaceNodeGen.ReplaceStringCachedData next_;
         @CompilerDirectives.CompilationFinal
         TruffleString cachedReplaceValue_;
         @CompilerDirectives.CompilationFinal(dimensions = 1)
         ReplaceStringParser.Token[] cachedParsedReplaceValue_;
         @Node.Child
         TruffleString.EqualNode equalsNode_;

         ReplaceStringCachedData(StringPrototypeBuiltinsFactory.JSStringReplaceNodeGen.ReplaceStringCachedData next_) {
            this.next_ = next_;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringSearchES5Node.class)
   public static final class JSStringSearchES5NodeGen extends StringPrototypeBuiltins.JSStringSearchES5Node implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToRegExpNode toRegExpNode_;

      private JSStringSearchES5NodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
            Object[] arguments1Value__ = (Object[])arguments1Value_;
            return this.search(arguments0Value_, arguments1Value__, this.toRegExpNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public int executeInt(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
            Object[] arguments1Value__ = (Object[])arguments1Value_;
            return this.search(arguments0Value_, arguments1Value__, this.toRegExpNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeInt(frameValue);
      }

      private int executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         int var7;
         try {
            int state_0 = this.state_0_;
            if (!(arguments1Value instanceof Object[])) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            }

            Object[] arguments1Value_ = (Object[])arguments1Value;
            this.toRegExpNode_ = super.insert(JSToRegExpNode.create(this.getContext()));
            int var11;
            this.state_0_ = var11 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var7 = this.search(arguments0Value, arguments1Value_, this.toRegExpNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var7;
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
         Object[] s = new Object[]{"search", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toRegExpNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringSearchES5Node create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringSearchES5NodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringSearchNode.class)
   public static final class JSStringSearchNodeGen extends StringPrototypeBuiltins.JSStringSearchNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSStringSearchNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.search(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"search", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringSearchNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringSearchNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringSliceNode.class)
   public static final class JSStringSliceNodeGen extends StringPrototypeBuiltins.JSStringSliceNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile sliceGeneric_isUndefined_;

      private JSStringSliceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 12) == 0 && state_0 != 0) {
            return this.execute_int_int0(state_0, frameValue);
         } else {
            return (state_0 & 11) == 0 && state_0 != 0 ? this.execute_int1(state_0, frameValue) : this.execute_generic2(state_0, frameValue);
         }
      }

      private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var8) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments2Value = this.arguments2_.execute(frameValue);
            return this.executeAndSpecialize(arguments0Value_, var8.getResult(), arguments2Value);
         }

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, var7.getResult());
         }

         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            return this.sliceStringIntInt(arguments0Value__, arguments1Value_, arguments2Value_);
         } else if ((state_0 & 2) != 0 && !JSGuards.isString(arguments0Value_)) {
            return this.sliceObjectIntInt(arguments0Value_, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      private Object execute_int1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments2Value = this.arguments2_.execute(frameValue);
            return this.executeAndSpecialize(arguments0Value_, var7.getResult(), arguments2Value);
         }

         Object arguments2Value_ = this.arguments2_.execute(frameValue);

         assert (state_0 & 4) != 0;

         if (arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (JSGuards.isUndefined(arguments2Value_)) {
               return this.sliceStringIntUndefined(arguments0Value__, arguments1Value_, arguments2Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      private Object execute_generic2(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if ((state_0 & 7) != 0 && arguments1Value_ instanceof Integer) {
            int arguments1Value__ = (Integer)arguments1Value_;
            if ((state_0 & 3) != 0 && arguments2Value_ instanceof Integer) {
               int arguments2Value__ = (Integer)arguments2Value_;
               if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
                  TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
                  return this.sliceStringIntInt(arguments0Value__, arguments1Value__, arguments2Value__);
               }

               if ((state_0 & 2) != 0 && !JSGuards.isString(arguments0Value_)) {
                  return this.sliceObjectIntInt(arguments0Value_, arguments1Value__, arguments2Value__);
               }
            }

            if ((state_0 & 4) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               if (JSGuards.isUndefined(arguments2Value_)) {
                  return this.sliceStringIntUndefined(arguments0Value__, arguments1Value__, arguments2Value_);
               }
            }
         }

         if ((state_0 & 8) != 0) {
            return this.sliceGeneric(arguments0Value_, arguments1Value_, arguments2Value_, this.sliceGeneric_isUndefined_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
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
            int exclude = this.exclude_;
            if (arguments1Value instanceof Integer) {
               int arguments1Value_ = (Integer)arguments1Value;
               if (arguments2Value instanceof Integer) {
                  int arguments2Value_ = (Integer)arguments2Value;
                  if ((exclude & 1) == 0 && arguments0Value instanceof TruffleString) {
                     TruffleString arguments0Value_ = (TruffleString)arguments0Value;
                     int var20;
                     this.state_0_ = var20 = state_0 | 1;
                     lock.unlock();
                     hasLock = false;
                     return this.sliceStringIntInt(arguments0Value_, arguments1Value_, arguments2Value_);
                  }

                  if ((exclude & 2) == 0 && !JSGuards.isString(arguments0Value)) {
                     int var22;
                     this.exclude_ = var22 = exclude | 1;
                     state_0 &= -2;
                     int var19;
                     this.state_0_ = var19 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.sliceObjectIntInt(arguments0Value, arguments1Value_, arguments2Value_);
                  }
               }

               if ((exclude & 4) == 0 && arguments0Value instanceof TruffleString) {
                  TruffleString arguments0Value_ = (TruffleString)arguments0Value;
                  if (JSGuards.isUndefined(arguments2Value)) {
                     int var17;
                     this.state_0_ = var17 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.sliceStringIntUndefined(arguments0Value_, arguments1Value_, arguments2Value);
                  }
               }
            }

            this.sliceGeneric_isUndefined_ = ConditionProfile.createBinaryProfile();
            int var21;
            this.exclude_ = var21 = exclude | 7;
            state_0 &= -8;
            int var16;
            this.state_0_ = var16 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return this.sliceGeneric(arguments0Value, arguments1Value, arguments2Value, this.sliceGeneric_isUndefined_);
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
         Object[] data = new Object[5];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"sliceStringIntInt", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else if ((exclude & 1) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"sliceObjectIntInt", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else if ((exclude & 2) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"sliceStringIntUndefined", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else if ((exclude & 4) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"sliceGeneric", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.sliceGeneric_isUndefined_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringSliceNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringSliceNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringSplitNode.class)
   public static final class JSStringSplitNodeGen extends StringPrototypeBuiltins.JSStringSplitNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSStringSplitNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0) {
            assert !this.isES6OrNewer();

            return this.splitES5(arguments0Value_, arguments1Value_, arguments2Value_);
         } else {
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               if (arguments1Value_ instanceof TruffleString) {
                  TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
                  if (arguments2Value_ instanceof JSDynamicObject) {
                     JSDynamicObject arguments2Value__ = (JSDynamicObject)arguments2Value_;

                     assert this.isES6OrNewer();

                     if (JSGuards.isUndefined(arguments2Value__)) {
                        return this.splitES6StrStrUndefined(arguments0Value__, arguments1Value__, arguments2Value__);
                     }
                  }
               }
            }

            if ((state_0 & 4) != 0) {
               assert this.isES6OrNewer();

               if (!this.isFastPath(arguments0Value_, arguments1Value_, arguments2Value_)) {
                  return this.splitES6Generic(arguments0Value_, arguments1Value_, arguments2Value_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         int state_0 = this.state_0_;
         if (!this.isES6OrNewer()) {
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            return this.splitES5(arguments0Value, arguments1Value, arguments2Value);
         } else {
            if (arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               if (arguments1Value instanceof TruffleString) {
                  TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                  if (arguments2Value instanceof JSDynamicObject) {
                     JSDynamicObject arguments2Value_ = (JSDynamicObject)arguments2Value;
                     if (this.isES6OrNewer() && JSGuards.isUndefined(arguments2Value_)) {
                        int var9;
                        this.state_0_ = var9 = state_0 | 2;
                        return this.splitES6StrStrUndefined(arguments0Value_, arguments1Value_, arguments2Value_);
                     }
                  }
               }
            }

            if (this.isES6OrNewer() && !this.isFastPath(arguments0Value, arguments1Value, arguments2Value)) {
               int var8;
               this.state_0_ = var8 = state_0 | 4;
               return this.splitES6Generic(arguments0Value, arguments1Value, arguments2Value);
            } else {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
               );
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
         Object[] s = new Object[]{"splitES5", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"splitES6StrStrUndefined", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"splitES6Generic", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringSplitNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringSplitNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringStartsWithNode.class)
   public static final class JSStringStartsWithNodeGen extends StringPrototypeBuiltins.JSStringStartsWithNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.RegionEqualByteIndexNode regionEqualsNode;
      @Node.Child
      private JSToStringNode startsWithGeneric_toString2Node_;
      @Node.Child
      private IsRegExpNode startsWithGeneric_isRegExpNode_;

      private JSStringStartsWithNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               if (arguments2Value_ instanceof JSDynamicObject) {
                  JSDynamicObject arguments2Value__ = (JSDynamicObject)arguments2Value_;
                  if (JSGuards.isUndefined(arguments2Value__)) {
                     return this.startsWithString(arguments0Value__, arguments1Value__, arguments2Value__, this.regionEqualsNode);
                  }
               }
            }
         }

         if ((state_0 & 2) == 0 || JSGuards.isStringString(arguments0Value_, arguments1Value_) && JSGuards.isUndefined(arguments2Value_)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         } else {
            return this.startsWithGeneric(
               arguments0Value_,
               arguments1Value_,
               arguments2Value_,
               this.startsWithGeneric_toString2Node_,
               this.startsWithGeneric_isRegExpNode_,
               this.regionEqualsNode
            );
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               if (arguments2Value_ instanceof JSDynamicObject) {
                  JSDynamicObject arguments2Value__ = (JSDynamicObject)arguments2Value_;
                  if (JSGuards.isUndefined(arguments2Value__)) {
                     return this.startsWithString(arguments0Value__, arguments1Value__, arguments2Value__, this.regionEqualsNode);
                  }
               }
            }
         }

         if ((state_0 & 2) == 0 || JSGuards.isStringString(arguments0Value_, arguments1Value_) && JSGuards.isUndefined(arguments2Value_)) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         } else {
            return this.startsWithGeneric(
               arguments0Value_,
               arguments1Value_,
               arguments2Value_,
               this.startsWithGeneric_toString2Node_,
               this.startsWithGeneric_isRegExpNode_,
               this.regionEqualsNode
            );
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               if (arguments1Value instanceof TruffleString) {
                  TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                  if (arguments2Value instanceof JSDynamicObject) {
                     JSDynamicObject arguments2Value_ = (JSDynamicObject)arguments2Value;
                     if (JSGuards.isUndefined(arguments2Value_)) {
                        this.regionEqualsNode = super.insert(
                           this.regionEqualsNode == null ? TruffleString.RegionEqualByteIndexNode.create() : this.regionEqualsNode
                        );
                        int var15;
                        this.state_0_ = var15 = state_0 | 1;
                        lock.unlock();
                        hasLock = false;
                        return this.startsWithString(arguments0Value_, arguments1Value_, arguments2Value_, this.regionEqualsNode);
                     }
                  }
               }
            }

            if (JSGuards.isStringString(arguments0Value, arguments1Value) && JSGuards.isUndefined(arguments2Value)) {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{this.arguments0_, this.arguments1_, this.arguments2_}, arguments0Value, arguments1Value, arguments2Value
               );
            } else {
               this.startsWithGeneric_toString2Node_ = super.insert(JSToStringNode.create());
               this.startsWithGeneric_isRegExpNode_ = super.insert(IsRegExpNode.create(this.getContext()));
               this.regionEqualsNode = super.insert(this.regionEqualsNode == null ? TruffleString.RegionEqualByteIndexNode.create() : this.regionEqualsNode);
               int var14;
               this.state_0_ = var14 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.startsWithGeneric(
                  arguments0Value,
                  arguments1Value,
                  arguments2Value,
                  this.startsWithGeneric_toString2Node_,
                  this.startsWithGeneric_isRegExpNode_,
                  this.regionEqualsNode
               );
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
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"startsWithString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.regionEqualsNode));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"startsWithGeneric", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.startsWithGeneric_toString2Node_, this.startsWithGeneric_isRegExpNode_, this.regionEqualsNode));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringStartsWithNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringStartsWithNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringSubstrNode.class)
   public static final class JSStringSubstrNodeGen extends StringPrototypeBuiltins.JSStringSubstrNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSStringSubstrNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 6) == 0 && state_0 != 0) {
            return this.execute_int_int0(state_0, frameValue);
         } else {
            return (state_0 & 5) == 0 && state_0 != 0 ? this.execute_int1(state_0, frameValue) : this.execute_generic2(state_0, frameValue);
         }
      }

      private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var8) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments2Value = this.arguments2_.execute(frameValue);
            return this.executeAndSpecialize(arguments0Value_, var8.getResult(), arguments2Value);
         }

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, var7.getResult());
         }

         assert (state_0 & 1) != 0;

         if (arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            return this.substrInt(arguments0Value__, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      private Object execute_int1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments2Value = this.arguments2_.execute(frameValue);
            return this.executeAndSpecialize(arguments0Value_, var7.getResult(), arguments2Value);
         }

         Object arguments2Value_ = this.arguments2_.execute(frameValue);

         assert (state_0 & 2) != 0;

         if (arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (JSGuards.isUndefined(arguments2Value_)) {
               return this.substrLenUndef(arguments0Value__, arguments1Value_, arguments2Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      private Object execute_generic2(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if ((state_0 & 3) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               if ((state_0 & 1) != 0 && arguments2Value_ instanceof Integer) {
                  int arguments2Value__ = (Integer)arguments2Value_;
                  return this.substrInt(arguments0Value__, arguments1Value__, arguments2Value__);
               }

               if ((state_0 & 2) != 0 && JSGuards.isUndefined(arguments2Value_)) {
                  return this.substrLenUndef(arguments0Value__, arguments1Value__, arguments2Value_);
               }
            }
         }

         if ((state_0 & 4) != 0) {
            return this.substrGeneric(arguments0Value_, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof TruffleString) {
            TruffleString arguments0Value_ = (TruffleString)arguments0Value;
            if (arguments1Value instanceof Integer) {
               int arguments1Value_ = (Integer)arguments1Value;
               if (arguments2Value instanceof Integer) {
                  int arguments2Value_ = (Integer)arguments2Value;
                  int var10;
                  this.state_0_ = var10 = state_0 | 1;
                  return this.substrInt(arguments0Value_, arguments1Value_, arguments2Value_);
               }

               if (JSGuards.isUndefined(arguments2Value)) {
                  int var9;
                  this.state_0_ = var9 = state_0 | 2;
                  return this.substrLenUndef(arguments0Value_, arguments1Value_, arguments2Value);
               }
            }
         }

         int var8;
         this.state_0_ = var8 = state_0 | 4;
         return this.substrGeneric(arguments0Value, arguments1Value, arguments2Value);
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
         Object[] s = new Object[]{"substrInt", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"substrLenUndef", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"substrGeneric", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringSubstrNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringSubstrNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringSubstringNode.class)
   public static final class JSStringSubstringNodeGen extends StringPrototypeBuiltins.JSStringSubstringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private StringPrototypeBuiltinsFactory.JSStringSubstringNodeGen.SubstringGenericData substringGeneric_cache;

      private JSStringSubstringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 6) == 0 && state_0 != 0) {
            return this.execute_int_int0(state_0, frameValue);
         } else {
            return (state_0 & 5) == 0 && state_0 != 0 ? this.execute_int1(state_0, frameValue) : this.execute_generic2(state_0, frameValue);
         }
      }

      private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var8) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments2Value = this.arguments2_.execute(frameValue);
            return this.executeAndSpecialize(arguments0Value_, var8.getResult(), arguments2Value);
         }

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, var7.getResult());
         }

         assert (state_0 & 1) != 0;

         if (arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            return this.substring(arguments0Value__, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      private Object execute_int1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments2Value = this.arguments2_.execute(frameValue);
            return this.executeAndSpecialize(arguments0Value_, var7.getResult(), arguments2Value);
         }

         Object arguments2Value_ = this.arguments2_.execute(frameValue);

         assert (state_0 & 2) != 0;

         if (arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (JSGuards.isUndefined(arguments2Value_)) {
               return this.substringStart(arguments0Value__, arguments1Value_, arguments2Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      private Object execute_generic2(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if ((state_0 & 3) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            if (arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               if ((state_0 & 1) != 0 && arguments2Value_ instanceof Integer) {
                  int arguments2Value__ = (Integer)arguments2Value_;
                  return this.substring(arguments0Value__, arguments1Value__, arguments2Value__);
               }

               if ((state_0 & 2) != 0 && JSGuards.isUndefined(arguments2Value_)) {
                  return this.substringStart(arguments0Value__, arguments1Value__, arguments2Value_);
               }
            }
         }

         if ((state_0 & 4) != 0) {
            StringPrototypeBuiltinsFactory.JSStringSubstringNodeGen.SubstringGenericData s2_ = this.substringGeneric_cache;
            if (s2_ != null) {
               return this.substringGeneric(
                  arguments0Value_, arguments1Value_, arguments2Value_, s2_.toNumberNode_, s2_.toNumber2Node_, s2_.startUndefined_, s2_.endUndefined_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private TruffleString executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               if (arguments1Value instanceof Integer) {
                  int arguments1Value_ = (Integer)arguments1Value;
                  if ((exclude & 1) == 0 && arguments2Value instanceof Integer) {
                     int arguments2Value_ = (Integer)arguments2Value;
                     int var18;
                     this.state_0_ = var18 = state_0 | 1;
                     lock.unlock();
                     hasLock = false;
                     return this.substring(arguments0Value_, arguments1Value_, arguments2Value_);
                  }

                  if ((exclude & 2) == 0 && JSGuards.isUndefined(arguments2Value)) {
                     int var17;
                     this.state_0_ = var17 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.substringStart(arguments0Value_, arguments1Value_, arguments2Value);
                  }
               }
            }

            StringPrototypeBuiltinsFactory.JSStringSubstringNodeGen.SubstringGenericData s2_ = super.insert(
               new StringPrototypeBuiltinsFactory.JSStringSubstringNodeGen.SubstringGenericData()
            );
            s2_.toNumberNode_ = s2_.insertAccessor(JSToNumberNode.create());
            s2_.toNumber2Node_ = s2_.insertAccessor(JSToNumberNode.create());
            s2_.startUndefined_ = ConditionProfile.createBinaryProfile();
            s2_.endUndefined_ = ConditionProfile.createBinaryProfile();
            VarHandle.storeStoreFence();
            this.substringGeneric_cache = s2_;
            int var19;
            this.exclude_ = var19 = exclude | 3;
            state_0 &= -4;
            int var16;
            this.state_0_ = var16 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.substringGeneric(
               arguments0Value, arguments1Value, arguments2Value, s2_.toNumberNode_, s2_.toNumber2Node_, s2_.startUndefined_, s2_.endUndefined_
            );
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
         int exclude = this.exclude_;
         Object[] s = new Object[]{"substring", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else if ((exclude & 1) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"substringStart", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else if ((exclude & 2) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"substringGeneric", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            StringPrototypeBuiltinsFactory.JSStringSubstringNodeGen.SubstringGenericData s2_ = this.substringGeneric_cache;
            if (s2_ != null) {
               cached.add(Arrays.asList(s2_.toNumberNode_, s2_.toNumber2Node_, s2_.startUndefined_, s2_.endUndefined_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringSubstringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringSubstringNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(StringPrototypeBuiltins.JSStringSubstringNode.Inlined.class)
      public static final class InlinedNodeGen extends StringPrototypeBuiltins.JSStringSubstringNode.Inlined implements Introspection.Provider {
         @Node.Child
         private JavaScriptNode arguments0_;
         @Node.Child
         private JavaScriptNode arguments1_;
         @Node.Child
         private JavaScriptNode arguments2_;
         @CompilerDirectives.CompilationFinal
         private volatile int state_0_;
         @Node.Child
         private StringPrototypeBuiltinsFactory.JSStringSubstringNodeGen.InlinedNodeGen.SubstringGenericData substringGeneric_cache;

         private InlinedNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         protected Object executeWithArguments(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            int state_0 = this.state_0_;
            if ((state_0 & 3) != 0 && arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               if (arguments1Value instanceof Integer) {
                  int arguments1Value_ = (Integer)arguments1Value;
                  if ((state_0 & 1) != 0 && arguments2Value instanceof Integer) {
                     int arguments2Value_ = (Integer)arguments2Value;
                     return this.substring(arguments0Value_, arguments1Value_, arguments2Value_);
                  }

                  if ((state_0 & 2) != 0 && JSGuards.isUndefined(arguments2Value)) {
                     return this.substringStart(arguments0Value_, arguments1Value_, arguments2Value);
                  }
               }
            }

            if ((state_0 & 4) != 0) {
               StringPrototypeBuiltinsFactory.JSStringSubstringNodeGen.InlinedNodeGen.SubstringGenericData s2_ = this.substringGeneric_cache;
               if (s2_ != null) {
                  return this.substringGeneric(
                     arguments0Value, arguments1Value, arguments2Value, s2_.toNumberNode_, s2_.toNumber2Node_, s2_.startUndefined_, s2_.endUndefined_
                  );
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value, arguments1Value, arguments2Value);
         }

         @Override
         public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if ((state_0 & 6) == 0 && state_0 != 0) {
               return this.execute_int_int0(state_0, frameValue);
            } else {
               return (state_0 & 5) == 0 && state_0 != 0 ? this.execute_int1(state_0, frameValue) : this.execute_generic2(state_0, frameValue);
            }
         }

         private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);

            int arguments1Value_;
            try {
               arguments1Value_ = this.arguments1_.executeInt(frameValue);
            } catch (UnexpectedResultException var8) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Object arguments2Value = this.arguments2_.execute(frameValue);
               return this.executeAndSpecialize(arguments0Value_, var8.getResult(), arguments2Value);
            }

            int arguments2Value_;
            try {
               arguments2Value_ = this.arguments2_.executeInt(frameValue);
            } catch (UnexpectedResultException var7) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arguments0Value_, arguments1Value_, var7.getResult());
            }

            assert (state_0 & 1) != 0;

            if (arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               return this.substring(arguments0Value__, arguments1Value_, arguments2Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
            }
         }

         private Object execute_int1(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);

            int arguments1Value_;
            try {
               arguments1Value_ = this.arguments1_.executeInt(frameValue);
            } catch (UnexpectedResultException var7) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Object arguments2Value = this.arguments2_.execute(frameValue);
               return this.executeAndSpecialize(arguments0Value_, var7.getResult(), arguments2Value);
            }

            Object arguments2Value_ = this.arguments2_.execute(frameValue);

            assert (state_0 & 2) != 0;

            if (arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               if (JSGuards.isUndefined(arguments2Value_)) {
                  return this.substringStart(arguments0Value__, arguments1Value_, arguments2Value_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }

         private Object execute_generic2(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if ((state_0 & 3) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               if (arguments1Value_ instanceof Integer) {
                  int arguments1Value__ = (Integer)arguments1Value_;
                  if ((state_0 & 1) != 0 && arguments2Value_ instanceof Integer) {
                     int arguments2Value__ = (Integer)arguments2Value_;
                     return this.substring(arguments0Value__, arguments1Value__, arguments2Value__);
                  }

                  if ((state_0 & 2) != 0 && JSGuards.isUndefined(arguments2Value_)) {
                     return this.substringStart(arguments0Value__, arguments1Value__, arguments2Value_);
                  }
               }
            }

            if ((state_0 & 4) != 0) {
               StringPrototypeBuiltinsFactory.JSStringSubstringNodeGen.InlinedNodeGen.SubstringGenericData s2_ = this.substringGeneric_cache;
               if (s2_ != null) {
                  return this.substringGeneric(
                     arguments0Value_, arguments1Value_, arguments2Value_, s2_.toNumberNode_, s2_.toNumber2Node_, s2_.startUndefined_, s2_.endUndefined_
                  );
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }

         @Override
         public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
         }

         private TruffleString executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();

            try {
               int state_0 = this.state_0_;
               if (arguments0Value instanceof TruffleString) {
                  TruffleString arguments0Value_ = (TruffleString)arguments0Value;
                  if (arguments1Value instanceof Integer) {
                     int arguments1Value_ = (Integer)arguments1Value;
                     if (arguments2Value instanceof Integer) {
                        int arguments2Value_ = (Integer)arguments2Value;
                        int var16;
                        this.state_0_ = var16 = state_0 | 1;
                        lock.unlock();
                        hasLock = false;
                        return this.substring(arguments0Value_, arguments1Value_, arguments2Value_);
                     }

                     if (JSGuards.isUndefined(arguments2Value)) {
                        int var15;
                        this.state_0_ = var15 = state_0 | 2;
                        lock.unlock();
                        hasLock = false;
                        return this.substringStart(arguments0Value_, arguments1Value_, arguments2Value);
                     }
                  }
               }

               StringPrototypeBuiltinsFactory.JSStringSubstringNodeGen.InlinedNodeGen.SubstringGenericData s2_ = super.insert(
                  new StringPrototypeBuiltinsFactory.JSStringSubstringNodeGen.InlinedNodeGen.SubstringGenericData()
               );
               s2_.toNumberNode_ = s2_.insertAccessor(JSToNumberNode.create());
               s2_.toNumber2Node_ = s2_.insertAccessor(JSToNumberNode.create());
               s2_.startUndefined_ = ConditionProfile.createBinaryProfile();
               s2_.endUndefined_ = ConditionProfile.createBinaryProfile();
               VarHandle.storeStoreFence();
               this.substringGeneric_cache = s2_;
               int var14;
               this.state_0_ = var14 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.substringGeneric(
                  arguments0Value, arguments1Value, arguments2Value, s2_.toNumberNode_, s2_.toNumber2Node_, s2_.startUndefined_, s2_.endUndefined_
               );
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
            Object[] s = new Object[]{"substring", null, null};
            if ((state_0 & 1) != 0) {
               s[1] = (byte)1;
            } else {
               s[1] = (byte)0;
            }

            data[1] = s;
            s = new Object[]{"substringStart", null, null};
            if ((state_0 & 2) != 0) {
               s[1] = (byte)1;
            } else {
               s[1] = (byte)0;
            }

            data[2] = s;
            s = new Object[]{"substringGeneric", null, null};
            if ((state_0 & 4) != 0) {
               s[1] = (byte)1;
               ArrayList<Object> cached = new ArrayList<>();
               StringPrototypeBuiltinsFactory.JSStringSubstringNodeGen.InlinedNodeGen.SubstringGenericData s2_ = this.substringGeneric_cache;
               if (s2_ != null) {
                  cached.add(Arrays.asList(s2_.toNumberNode_, s2_.toNumber2Node_, s2_.startUndefined_, s2_.endUndefined_));
               }

               s[2] = cached;
            } else {
               s[1] = (byte)0;
            }

            data[3] = s;
            return Introspection.Provider.create(data);
         }

         public static StringPrototypeBuiltins.JSStringSubstringNode.Inlined create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new StringPrototypeBuiltinsFactory.JSStringSubstringNodeGen.InlinedNodeGen(context, builtin, arguments);
         }

         @GeneratedBy(StringPrototypeBuiltins.JSStringSubstringNode.Inlined.class)
         private static final class SubstringGenericData extends Node {
            @Node.Child
            JSToNumberNode toNumberNode_;
            @Node.Child
            JSToNumberNode toNumber2Node_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile startUndefined_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile endUndefined_;

            SubstringGenericData() {
            }

            @Override
            public NodeCost getCost() {
               return NodeCost.NONE;
            }

            <T extends Node> T insertAccessor(T node) {
               return super.insert(node);
            }
         }
      }

      @GeneratedBy(StringPrototypeBuiltins.JSStringSubstringNode.class)
      private static final class SubstringGenericData extends Node {
         @Node.Child
         JSToNumberNode toNumberNode_;
         @Node.Child
         JSToNumberNode toNumber2Node_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile startUndefined_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile endUndefined_;

         SubstringGenericData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringToLocaleLowerCaseIntlNode.class)
   public static final class JSStringToLocaleLowerCaseIntlNodeGen
      extends StringPrototypeBuiltins.JSStringToLocaleLowerCaseIntlNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSStringToLocaleLowerCaseIntlNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.toDesiredCase(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"toDesiredCase", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringToLocaleLowerCaseIntlNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringToLocaleLowerCaseIntlNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringToLocaleUpperCaseIntlNode.class)
   public static final class JSStringToLocaleUpperCaseIntlNodeGen
      extends StringPrototypeBuiltins.JSStringToLocaleUpperCaseIntlNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSStringToLocaleUpperCaseIntlNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.toDesiredCase(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"toDesiredCase", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringToLocaleUpperCaseIntlNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringToLocaleUpperCaseIntlNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringToLocaleXCaseIntl.class)
   public static final class JSStringToLocaleXCaseIntlNodeGen extends StringPrototypeBuiltins.JSStringToLocaleXCaseIntl implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private JSStringToLocaleXCaseIntlNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.toDesiredCase(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"toDesiredCase", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringToLocaleXCaseIntl create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringToLocaleXCaseIntlNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringToLowerCaseNode.class)
   public static final class JSStringToLowerCaseNodeGen extends StringPrototypeBuiltins.JSStringToLowerCaseNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSStringToLowerCaseNodeGen(JSContext context, JSBuiltin builtin, boolean locale, JavaScriptNode[] arguments) {
         super(context, builtin, locale);
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            return this.toLowerCaseString(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isString(arguments0Value_)) {
            return this.toLowerCase(arguments0Value_);
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
         if (arguments0Value instanceof TruffleString) {
            TruffleString arguments0Value_ = (TruffleString)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.toLowerCaseString(arguments0Value_);
         } else if (!JSGuards.isString(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.toLowerCase(arguments0Value);
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
         Object[] s = new Object[]{"toLowerCaseString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"toLowerCase", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringToLowerCaseNode create(JSContext context, JSBuiltin builtin, boolean locale, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringToLowerCaseNodeGen(context, builtin, locale, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringToStringNode.class)
   public static final class JSStringToStringNodeGen extends StringPrototypeBuiltins.JSStringToStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private StringPrototypeBuiltinsFactory.JSStringToStringNodeGen.ToStringForeignObject0Data toStringForeignObject0_cache;

      private JSStringToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @ExplodeLoop
      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            return this.toStringTString(arguments0Value__);
         } else {
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (JSGuards.isJSString(arguments0Value__)) {
                  return this.toStringString(arguments0Value__);
               }
            }

            if ((state_0 & 28) != 0) {
               if ((state_0 & 4) != 0) {
                  for (StringPrototypeBuiltinsFactory.JSStringToStringNodeGen.ToStringForeignObject0Data s2_ = this.toStringForeignObject0_cache;
                     s2_ != null;
                     s2_ = s2_.next_
                  ) {
                     if (s2_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                        return this.toStringForeignObject(arguments0Value_, s2_.interop_);
                     }
                  }
               }

               if ((state_0 & 8) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                  return this.toStringForeignObject1Boundary(state_0, arguments0Value_);
               }

               if ((state_0 & 16) != 0 && fallbackGuard_(state_0, arguments0Value_)) {
                  return this.toStringOther(arguments0Value_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @CompilerDirectives.TruffleBoundary
      private Object toStringForeignObject1Boundary(int state_0, Object arguments0Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         TruffleString var6;
         try {
            InteropLibrary toStringForeignObject1_interop__ = StringPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var6 = this.toStringForeignObject(arguments0Value_, toStringForeignObject1_interop__);
         } finally {
            encapsulating_.set(prev_);
         }

         return var6;
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private TruffleString executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               int var22;
               this.state_0_ = var22 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.toStringTString(arguments0Value_);
            } else {
               if (arguments0Value instanceof JSDynamicObject) {
                  JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                  if (JSGuards.isJSString(arguments0Value_)) {
                     int var21;
                     this.state_0_ = var21 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.toStringString(arguments0Value_);
                  }
               }

               if (exclude == 0) {
                  int count2_ = 0;
                  StringPrototypeBuiltinsFactory.JSStringToStringNodeGen.ToStringForeignObject0Data s2_ = this.toStringForeignObject0_cache;
                  if ((state_0 & 4) != 0) {
                     while (s2_ != null && (!s2_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value))) {
                        s2_ = s2_.next_;
                        count2_++;
                     }
                  }

                  if (s2_ == null && JSGuards.isForeignObject(arguments0Value) && count2_ < 5) {
                     s2_ = super.insert(
                        new StringPrototypeBuiltinsFactory.JSStringToStringNodeGen.ToStringForeignObject0Data(this.toStringForeignObject0_cache)
                     );
                     s2_.interop_ = s2_.insertAccessor(StringPrototypeBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     VarHandle.storeStoreFence();
                     this.toStringForeignObject0_cache = s2_;
                     this.state_0_ = state_0 |= 4;
                  }

                  if (s2_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.toStringForeignObject(arguments0Value, s2_.interop_);
                  }
               }

               InteropLibrary toStringForeignObject1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arguments0Value)) {
                     toStringForeignObject1_interop__ = StringPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     int var23;
                     this.exclude_ = var23 = exclude | 1;
                     this.toStringForeignObject0_cache = null;
                     state_0 &= -5;
                     int var20;
                     this.state_0_ = var20 = state_0 | 8;
                     lock.unlock();
                     hasLock = false;
                     return this.toStringForeignObject(arguments0Value, toStringForeignObject1_interop__);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               int var18;
               this.state_0_ = var18 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.toStringOther(arguments0Value);
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
            if ((state_0 & state_0 - 1) == 0) {
               StringPrototypeBuiltinsFactory.JSStringToStringNodeGen.ToStringForeignObject0Data s2_ = this.toStringForeignObject0_cache;
               if (s2_ == null || s2_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[6];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"toStringTString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"toStringString", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"toStringForeignObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (StringPrototypeBuiltinsFactory.JSStringToStringNodeGen.ToStringForeignObject0Data s2_ = this.toStringForeignObject0_cache;
               s2_ != null;
               s2_ = s2_.next_
            ) {
               cached.add(Arrays.asList(s2_.interop_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"toStringForeignObject", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"toStringOther", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(int state_0, Object arguments0Value) {
         if ((state_0 & 1) == 0 && arguments0Value instanceof TruffleString) {
            return false;
         } else {
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSString(arguments0Value_)) {
                  return false;
               }
            }

            return (state_0 & 8) != 0 || !JSGuards.isForeignObject(arguments0Value);
         }
      }

      public static StringPrototypeBuiltins.JSStringToStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringToStringNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(StringPrototypeBuiltins.JSStringToStringNode.class)
      private static final class ToStringForeignObject0Data extends Node {
         @Node.Child
         StringPrototypeBuiltinsFactory.JSStringToStringNodeGen.ToStringForeignObject0Data next_;
         @Node.Child
         InteropLibrary interop_;

         ToStringForeignObject0Data(StringPrototypeBuiltinsFactory.JSStringToStringNodeGen.ToStringForeignObject0Data next_) {
            this.next_ = next_;
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringToUpperCaseNode.class)
   public static final class JSStringToUpperCaseNodeGen extends StringPrototypeBuiltins.JSStringToUpperCaseNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSStringToUpperCaseNodeGen(JSContext context, JSBuiltin builtin, boolean locale, JavaScriptNode[] arguments) {
         super(context, builtin, locale);
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            return this.toUpperCaseString(arguments0Value__);
         } else if ((state_0 & 2) != 0 && !JSGuards.isString(arguments0Value_)) {
            return this.toUpperCaseGeneric(arguments0Value_);
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
         if (arguments0Value instanceof TruffleString) {
            TruffleString arguments0Value_ = (TruffleString)arguments0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 1;
            return this.toUpperCaseString(arguments0Value_);
         } else if (!JSGuards.isString(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.toUpperCaseGeneric(arguments0Value);
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
         Object[] s = new Object[]{"toUpperCaseString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"toUpperCaseGeneric", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringToUpperCaseNode create(JSContext context, JSBuiltin builtin, boolean locale, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringToUpperCaseNodeGen(context, builtin, locale, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringTrimLeftNode.class)
   public static final class JSStringTrimLeftNodeGen extends StringPrototypeBuiltins.JSStringTrimLeftNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.SubstringByteIndexNode substringNode_;
      @Node.Child
      private TruffleString.ReadCharUTF16Node readRawNode_;

      private JSStringTrimLeftNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.trimLeft(arguments0Value_, this.substringNode_, this.readRawNode_);
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
            this.substringNode_ = super.insert(TruffleString.SubstringByteIndexNode.create());
            this.readRawNode_ = super.insert(TruffleString.ReadCharUTF16Node.create());
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.trimLeft(arguments0Value, this.substringNode_, this.readRawNode_);
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
         Object[] s = new Object[]{"trimLeft", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.substringNode_, this.readRawNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringTrimLeftNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringTrimLeftNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringTrimNode.class)
   public static final class JSStringTrimNodeGen extends StringPrototypeBuiltins.JSStringTrimNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSTrimWhitespaceNode trimWhitespace;

      private JSStringTrimNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            return this.trimString(arguments0Value__, this.trimWhitespace);
         } else if ((state_0 & 2) != 0 && !JSGuards.isString(arguments0Value_)) {
            return this.trimObject(arguments0Value_, this.trimWhitespace);
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

         TruffleString arguments0Value_;
         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof TruffleString) {
               arguments0Value_ = (TruffleString)arguments0Value;
               this.trimWhitespace = super.insert(this.trimWhitespace == null ? JSTrimWhitespaceNode.create() : this.trimWhitespace);
               int var11;
               this.state_0_ = var11 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.trimString(arguments0Value_, this.trimWhitespace);
            }

            if (JSGuards.isString(arguments0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
            }

            this.trimWhitespace = super.insert(this.trimWhitespace == null ? JSTrimWhitespaceNode.create() : this.trimWhitespace);
            int var10;
            this.state_0_ = var10 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            arguments0Value_ = (TruffleString)this.trimObject(arguments0Value, this.trimWhitespace);
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
         Object[] s = new Object[]{"trimString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.trimWhitespace));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"trimObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.trimWhitespace));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringTrimNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringTrimNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(StringPrototypeBuiltins.JSStringTrimRightNode.class)
   public static final class JSStringTrimRightNodeGen extends StringPrototypeBuiltins.JSStringTrimRightNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TruffleString.ReadCharUTF16Node readRawNode_;
      @Node.Child
      private TruffleString.SubstringByteIndexNode substringNode_;

      private JSStringTrimRightNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            return this.trimRight(arguments0Value_, this.readRawNode_, this.substringNode_);
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
            this.readRawNode_ = super.insert(TruffleString.ReadCharUTF16Node.create());
            this.substringNode_ = super.insert(TruffleString.SubstringByteIndexNode.create());
            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.trimRight(arguments0Value, this.readRawNode_, this.substringNode_);
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
         Object[] s = new Object[]{"trimRight", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.readRawNode_, this.substringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static StringPrototypeBuiltins.JSStringTrimRightNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new StringPrototypeBuiltinsFactory.JSStringTrimRightNodeGen(context, builtin, arguments);
      }
   }
}
