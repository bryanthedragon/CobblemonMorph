package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.helper.JSRegExpExecIntlNode;
import com.oracle.truffle.js.builtins.helper.ReplaceStringParser;
import com.oracle.truffle.js.nodes.CompileRegexNode;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.cast.JSToLengthNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSRegExp;
import com.oracle.truffle.js.runtime.builtins.JSRegExpObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TRegexUtil;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(RegExpPrototypeBuiltins.class)
public final class RegExpPrototypeBuiltinsFactory {
   @GeneratedBy(RegExpPrototypeBuiltins.CompiledRegexFlagPropertyAccessor.class)
   static final class CompiledRegexFlagPropertyAccessorNodeGen
      extends RegExpPrototypeBuiltins.CompiledRegexFlagPropertyAccessor
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private CompiledRegexFlagPropertyAccessorNodeGen(JSContext context, JSBuiltin builtin, String flagName, JavaScriptNode[] arguments) {
         super(context, builtin, flagName);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      private boolean fallbackGuard_(int state_0, Object arguments0Value) {
         if ((state_0 & 1) == 0 && arguments0Value instanceof JSRegExpObject) {
            return false;
         } else {
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (this.isRegExpPrototype(arguments0Value_)) {
                  return false;
               }
            }

            return true;
         }
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSRegExpObject) {
            JSRegExpObject arguments0Value__ = (JSRegExpObject)arguments0Value_;
            return this.doRegExp(arguments0Value__);
         } else {
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (this.isRegExpPrototype(arguments0Value__)) {
                  return this.doPrototype(arguments0Value__);
               }
            }

            if ((state_0 & 4) != 0 && this.fallbackGuard_(state_0, arguments0Value_)) {
               return this.doObject(arguments0Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arguments0Value_);
            }
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSRegExpObject) {
            JSRegExpObject arguments0Value_ = (JSRegExpObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.doRegExp(arguments0Value_);
         } else {
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (this.isRegExpPrototype(arguments0Value_)) {
                  int var5;
                  this.state_0_ = var5 = state_0 | 2;
                  return this.doPrototype(arguments0Value_);
               }
            }

            int var4;
            this.state_0_ = var4 = state_0 | 4;
            return this.doObject(arguments0Value);
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
         Object[] s = new Object[]{"doRegExp", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doPrototype", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static RegExpPrototypeBuiltins.CompiledRegexFlagPropertyAccessor create(
         JSContext context, JSBuiltin builtin, String flagName, JavaScriptNode[] arguments
      ) {
         return new RegExpPrototypeBuiltinsFactory.CompiledRegexFlagPropertyAccessorNodeGen(context, builtin, flagName, arguments);
      }
   }

   @GeneratedBy(RegExpPrototypeBuiltins.CompiledRegexPatternAccessor.class)
   static final class CompiledRegexPatternAccessorNodeGen extends RegExpPrototypeBuiltins.CompiledRegexPatternAccessor implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private CompiledRegexPatternAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      private boolean fallbackGuard_(int state_0, Object arguments0Value) {
         if ((state_0 & 1) == 0 && arguments0Value instanceof JSRegExpObject) {
            return false;
         } else {
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (this.isRegExpPrototype(arguments0Value_)) {
                  return false;
               }
            }

            return true;
         }
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSRegExpObject) {
            JSRegExpObject arguments0Value__ = (JSRegExpObject)arguments0Value_;
            return this.doRegExp(arguments0Value__);
         } else {
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (this.isRegExpPrototype(arguments0Value__)) {
                  return this.doPrototype(arguments0Value__);
               }
            }

            if ((state_0 & 4) != 0 && this.fallbackGuard_(state_0, arguments0Value_)) {
               return this.doObject(arguments0Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arguments0Value_);
            }
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSRegExpObject) {
            JSRegExpObject arguments0Value_ = (JSRegExpObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.doRegExp(arguments0Value_);
         } else {
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (this.isRegExpPrototype(arguments0Value_)) {
                  int var5;
                  this.state_0_ = var5 = state_0 | 2;
                  return this.doPrototype(arguments0Value_);
               }
            }

            int var4;
            this.state_0_ = var4 = state_0 | 4;
            return this.doObject(arguments0Value);
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
         Object[] s = new Object[]{"doRegExp", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doPrototype", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static RegExpPrototypeBuiltins.CompiledRegexPatternAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RegExpPrototypeBuiltinsFactory.CompiledRegexPatternAccessorNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RegExpPrototypeBuiltins.JSRegExpCompileNode.class)
   public static final class JSRegExpCompileNodeGen extends RegExpPrototypeBuiltins.JSRegExpCompileNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private RegExpPrototypeBuiltinsFactory.JSRegExpCompileNodeGen.CompileData compile_cache;

      private JSRegExpCompileNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSRegExpObject) {
               JSRegExpObject arguments0Value__ = (JSRegExpObject)arguments0Value_;
               RegExpPrototypeBuiltinsFactory.JSRegExpCompileNodeGen.CompileData s0_ = this.compile_cache;
               if (s0_ != null) {
                  return this.compile(
                     arguments0Value__,
                     arguments1Value_,
                     arguments2Value_,
                     s0_.compileRegexNode_,
                     s0_.toStringNode_,
                     s0_.isRegExpProfile_,
                     s0_.compiledRegexAccessor_,
                     s0_.flagsAccessor_
                  );
               }
            }

            if ((state_0 & 2) != 0 && fallbackGuard_(state_0, arguments0Value_, arguments1Value_, arguments2Value_)) {
               return this.compile(arguments0Value_, arguments1Value_, arguments2Value_);
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

         JSRegExpObject var9;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof JSRegExpObject)) {
               int var14;
               this.state_0_ = var14 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.compile(arguments0Value, arguments1Value, arguments2Value);
            }

            JSRegExpObject arguments0Value_ = (JSRegExpObject)arguments0Value;
            RegExpPrototypeBuiltinsFactory.JSRegExpCompileNodeGen.CompileData s0_ = super.insert(
               new RegExpPrototypeBuiltinsFactory.JSRegExpCompileNodeGen.CompileData()
            );
            s0_.compileRegexNode_ = s0_.insertAccessor(CompileRegexNode.create(this.getContext()));
            s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.createUndefinedToEmpty());
            s0_.isRegExpProfile_ = ConditionProfile.createBinaryProfile();
            s0_.compiledRegexAccessor_ = s0_.insertAccessor(TRegexUtil.TRegexCompiledRegexAccessor.create());
            s0_.flagsAccessor_ = s0_.insertAccessor(TRegexUtil.TRegexFlagsAccessor.create());
            VarHandle.storeStoreFence();
            this.compile_cache = s0_;
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = this.compile(
               arguments0Value_,
               arguments1Value,
               arguments2Value,
               s0_.compileRegexNode_,
               s0_.toStringNode_,
               s0_.isRegExpProfile_,
               s0_.compiledRegexAccessor_,
               s0_.flagsAccessor_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var9;
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
         Object[] s = new Object[]{"compile", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            RegExpPrototypeBuiltinsFactory.JSRegExpCompileNodeGen.CompileData s0_ = this.compile_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.compileRegexNode_, s0_.toStringNode_, s0_.isRegExpProfile_, s0_.compiledRegexAccessor_, s0_.flagsAccessor_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"compile", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         return (state_0 & 1) != 0 || !(arguments0Value instanceof JSRegExpObject);
      }

      public static RegExpPrototypeBuiltins.JSRegExpCompileNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RegExpPrototypeBuiltinsFactory.JSRegExpCompileNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(RegExpPrototypeBuiltins.JSRegExpCompileNode.class)
      private static final class CompileData extends Node {
         @Node.Child
         CompileRegexNode compileRegexNode_;
         @Node.Child
         JSToStringNode toStringNode_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile isRegExpProfile_;
         @Node.Child
         TRegexUtil.TRegexCompiledRegexAccessor compiledRegexAccessor_;
         @Node.Child
         TRegexUtil.TRegexFlagsAccessor flagsAccessor_;

         CompileData() {
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

   @GeneratedBy(RegExpPrototypeBuiltins.JSRegExpExecES5Node.class)
   public static final class JSRegExpExecES5NodeGen extends RegExpPrototypeBuiltins.JSRegExpExecES5Node implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSRegExpExecES5NodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSRegExpObject) {
               JSRegExpObject arguments0Value__ = (JSRegExpObject)arguments0Value_;
               return this.exec(arguments0Value__, arguments1Value_);
            }

            if ((state_0 & 2) != 0 && fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
               return this.exec(arguments0Value_, arguments1Value_);
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
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSRegExpObject) {
            JSRegExpObject arguments0Value_ = (JSRegExpObject)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 1;
            return this.exec(arguments0Value_, arguments1Value);
         } else {
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return this.exec(arguments0Value, arguments1Value);
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
         Object[] s = new Object[]{"exec", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"exec", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
         return (state_0 & 1) != 0 || !(arguments0Value instanceof JSRegExpObject);
      }

      public static RegExpPrototypeBuiltins.JSRegExpExecES5Node create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RegExpPrototypeBuiltinsFactory.JSRegExpExecES5NodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RegExpPrototypeBuiltins.JSRegExpExecNode.class)
   public static final class JSRegExpExecNodeGen extends RegExpPrototypeBuiltins.JSRegExpExecNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private JSToStringNode object_toStringNode_;

      private JSRegExpExecNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSRegExpObject) {
            JSRegExpObject arguments0Value__ = (JSRegExpObject)arguments0Value_;
            if ((state_0 & 1) != 0 && arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               return this.doString(arguments0Value__, arguments1Value__);
            }

            if ((state_0 & 2) != 0) {
               return this.doObject(arguments0Value__, arguments1Value_, this.object_toStringNode_);
            }
         }

         if ((state_0 & 4) != 0 && fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
            return this.doNoRegExp(arguments0Value_, arguments1Value_);
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

         JSDynamicObject var9;
         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (!(arguments0Value instanceof JSRegExpObject)) {
               int var16;
               this.state_0_ = var16 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doNoRegExp(arguments0Value, arguments1Value);
            }

            JSRegExpObject arguments0Value_ = (JSRegExpObject)arguments0Value;
            if (exclude != 0 || !(arguments1Value instanceof TruffleString)) {
               this.object_toStringNode_ = super.insert(JSToStringNode.create());
               int var17;
               this.exclude_ = var17 = exclude | 1;
               state_0 &= -2;
               int var15;
               this.state_0_ = var15 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doObject(arguments0Value_, arguments1Value, this.object_toStringNode_);
            }

            TruffleString arguments1Value_ = (TruffleString)arguments1Value;
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = this.doString(arguments0Value_, arguments1Value_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var9;
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
         Object[] s = new Object[]{"doString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.object_toStringNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doNoRegExp", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
         return (state_0 & 2) != 0 || !(arguments0Value instanceof JSRegExpObject);
      }

      public static RegExpPrototypeBuiltins.JSRegExpExecNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RegExpPrototypeBuiltinsFactory.JSRegExpExecNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RegExpPrototypeBuiltins.JSRegExpMatchAllNode.class)
   public static final class JSRegExpMatchAllNodeGen extends RegExpPrototypeBuiltins.JSRegExpMatchAllNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private RegExpPrototypeBuiltinsFactory.JSRegExpMatchAllNodeGen.MatchAllData matchAll_cache;

      private JSRegExpMatchAllNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      private boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if ((state_0 & 2) == 0 || this.matchAll_cache.isObjectNode_.executeBoolean(arguments0Value_)) {
               return false;
            }
         }

         return true;
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 5) != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               RegExpPrototypeBuiltinsFactory.JSRegExpMatchAllNodeGen.MatchAllData s0_ = this.matchAll_cache;
               if (s0_ != null && s0_.isObjectNode_.executeBoolean(arguments0Value__)) {
                  return this.matchAll(
                     arguments0Value__,
                     arguments1Value_,
                     s0_.toStringNodeForInput_,
                     s0_.speciesConstructNode_,
                     s0_.getFlagsNode_,
                     s0_.toStringNodeForFlags_,
                     s0_.getLastIndexNode_,
                     s0_.toLengthNode_,
                     s0_.setLastIndexNode_,
                     s0_.createRegExpStringIteratorNode_,
                     s0_.isObjectNode_,
                     s0_.indexInIntRangeProf_,
                     s0_.stringIndexOfNode_
                  );
               }
            }

            if ((state_0 & 4) != 0 && this.fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
               return this.matchAll(arguments0Value_, arguments1Value_);
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

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               RegExpPrototypeBuiltinsFactory.JSRegExpMatchAllNodeGen.MatchAllData s0_ = this.matchAll_cache;
               boolean MatchAll_duplicateFound_ = false;
               if ((state_0 & 1) != 0 && s0_.isObjectNode_.executeBoolean(arguments0Value_)) {
                  MatchAll_duplicateFound_ = true;
               }

               if (!MatchAll_duplicateFound_) {
                  if ((state_0 & 2) == 0) {
                     s0_ = super.insert(new RegExpPrototypeBuiltinsFactory.JSRegExpMatchAllNodeGen.MatchAllData());
                     VarHandle.storeStoreFence();
                     this.matchAll_cache = s0_;
                     s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
                     this.state_0_ = state_0 |= 2;
                  }

                  if (s0_.isObjectNode_.executeBoolean(arguments0Value_) && (state_0 & 1) == 0) {
                     s0_ = super.insert(new RegExpPrototypeBuiltinsFactory.JSRegExpMatchAllNodeGen.MatchAllData());
                     s0_.toStringNodeForInput_ = s0_.insertAccessor(JSToStringNode.create());
                     s0_.speciesConstructNode_ = s0_.insertAccessor(this.createSpeciesConstructNode());
                     s0_.getFlagsNode_ = s0_.insertAccessor(PropertyGetNode.create(JSRegExp.FLAGS, this.getContext()));
                     s0_.toStringNodeForFlags_ = s0_.insertAccessor(JSToStringNode.create());
                     s0_.getLastIndexNode_ = s0_.insertAccessor(PropertyGetNode.create(JSRegExp.LAST_INDEX, this.getContext()));
                     s0_.toLengthNode_ = s0_.insertAccessor(JSToLengthNode.create());
                     s0_.setLastIndexNode_ = s0_.insertAccessor(PropertySetNode.create(JSRegExp.LAST_INDEX, false, this.getContext(), true));
                     s0_.createRegExpStringIteratorNode_ = s0_.insertAccessor(this.createCreateRegExpStringIteratorNode());
                     s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
                     s0_.indexInIntRangeProf_ = ConditionProfile.create();
                     s0_.stringIndexOfNode_ = s0_.insertAccessor(TruffleString.ByteIndexOfCodePointNode.create());
                     VarHandle.storeStoreFence();
                     this.matchAll_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                     MatchAll_duplicateFound_ = true;
                  }
               }

               if (MatchAll_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return this.matchAll(
                     arguments0Value_,
                     arguments1Value,
                     s0_.toStringNodeForInput_,
                     s0_.speciesConstructNode_,
                     s0_.getFlagsNode_,
                     s0_.toStringNodeForFlags_,
                     s0_.getLastIndexNode_,
                     s0_.toLengthNode_,
                     s0_.setLastIndexNode_,
                     s0_.createRegExpStringIteratorNode_,
                     s0_.isObjectNode_,
                     s0_.indexInIntRangeProf_,
                     s0_.stringIndexOfNode_
                  );
               }
            }

            int var13;
            this.state_0_ = var13 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.matchAll(arguments0Value, arguments1Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if ((state_0 & 5) == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & 5 & (state_0 & 5) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"matchAll", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            RegExpPrototypeBuiltinsFactory.JSRegExpMatchAllNodeGen.MatchAllData s0_ = this.matchAll_cache;
            if (s0_ != null) {
               cached.add(
                  Arrays.asList(
                     s0_.toStringNodeForInput_,
                     s0_.speciesConstructNode_,
                     s0_.getFlagsNode_,
                     s0_.toStringNodeForFlags_,
                     s0_.getLastIndexNode_,
                     s0_.toLengthNode_,
                     s0_.setLastIndexNode_,
                     s0_.createRegExpStringIteratorNode_,
                     s0_.isObjectNode_,
                     s0_.indexInIntRangeProf_,
                     s0_.stringIndexOfNode_
                  )
               );
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"matchAll", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static RegExpPrototypeBuiltins.JSRegExpMatchAllNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RegExpPrototypeBuiltinsFactory.JSRegExpMatchAllNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(RegExpPrototypeBuiltins.JSRegExpMatchAllNode.class)
      private static final class MatchAllData extends Node {
         @Node.Child
         JSToStringNode toStringNodeForInput_;
         @Node.Child
         ArrayPrototypeBuiltins.ArraySpeciesConstructorNode speciesConstructNode_;
         @Node.Child
         PropertyGetNode getFlagsNode_;
         @Node.Child
         JSToStringNode toStringNodeForFlags_;
         @Node.Child
         PropertyGetNode getLastIndexNode_;
         @Node.Child
         JSToLengthNode toLengthNode_;
         @Node.Child
         PropertySetNode setLastIndexNode_;
         @Node.Child
         StringPrototypeBuiltins.CreateRegExpStringIteratorNode createRegExpStringIteratorNode_;
         @Node.Child
         IsJSObjectNode isObjectNode_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile indexInIntRangeProf_;
         @Node.Child
         TruffleString.ByteIndexOfCodePointNode stringIndexOfNode_;

         MatchAllData() {
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

   @GeneratedBy(RegExpPrototypeBuiltins.JSRegExpMatchNode.class)
   public static final class JSRegExpMatchNodeGen extends RegExpPrototypeBuiltins.JSRegExpMatchNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private RegExpPrototypeBuiltinsFactory.JSRegExpMatchNodeGen.MatchData match_cache;

      private JSRegExpMatchNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      private boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if ((state_0 & 2) == 0 || this.match_cache.isObjectNode_.executeBoolean(arguments0Value_)) {
               return false;
            }
         }

         return true;
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 5) != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               RegExpPrototypeBuiltinsFactory.JSRegExpMatchNodeGen.MatchData s0_ = this.match_cache;
               if (s0_ != null && s0_.isObjectNode_.executeBoolean(arguments0Value__)) {
                  return this.match(arguments0Value__, arguments1Value_, s0_.isObjectNode_, s0_.toString1Node_, s0_.toString2Node_);
               }
            }

            if ((state_0 & 4) != 0 && this.fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
               return this.match(arguments0Value_, arguments1Value_);
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

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               RegExpPrototypeBuiltinsFactory.JSRegExpMatchNodeGen.MatchData s0_ = this.match_cache;
               boolean Match_duplicateFound_ = false;
               if ((state_0 & 1) != 0 && s0_.isObjectNode_.executeBoolean(arguments0Value_)) {
                  Match_duplicateFound_ = true;
               }

               if (!Match_duplicateFound_) {
                  if ((state_0 & 2) == 0) {
                     s0_ = super.insert(new RegExpPrototypeBuiltinsFactory.JSRegExpMatchNodeGen.MatchData());
                     VarHandle.storeStoreFence();
                     this.match_cache = s0_;
                     s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
                     this.state_0_ = state_0 |= 2;
                  }

                  if (s0_.isObjectNode_.executeBoolean(arguments0Value_) && (state_0 & 1) == 0) {
                     s0_ = super.insert(new RegExpPrototypeBuiltinsFactory.JSRegExpMatchNodeGen.MatchData());
                     s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
                     s0_.toString1Node_ = s0_.insertAccessor(JSToStringNode.create());
                     s0_.toString2Node_ = s0_.insertAccessor(JSToStringNode.create());
                     VarHandle.storeStoreFence();
                     this.match_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                     Match_duplicateFound_ = true;
                  }
               }

               if (Match_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return this.match(arguments0Value_, arguments1Value, s0_.isObjectNode_, s0_.toString1Node_, s0_.toString2Node_);
               }
            }

            int var13;
            this.state_0_ = var13 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.match(arguments0Value, arguments1Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if ((state_0 & 5) == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & 5 & (state_0 & 5) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"match", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            RegExpPrototypeBuiltinsFactory.JSRegExpMatchNodeGen.MatchData s0_ = this.match_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.isObjectNode_, s0_.toString1Node_, s0_.toString2Node_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"match", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static RegExpPrototypeBuiltins.JSRegExpMatchNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RegExpPrototypeBuiltinsFactory.JSRegExpMatchNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(RegExpPrototypeBuiltins.JSRegExpMatchNode.class)
      private static final class MatchData extends Node {
         @Node.Child
         IsJSObjectNode isObjectNode_;
         @Node.Child
         JSToStringNode toString1Node_;
         @Node.Child
         JSToStringNode toString2Node_;

         MatchData() {
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

   @GeneratedBy(RegExpPrototypeBuiltins.JSRegExpReplaceNode.class)
   public static final class JSRegExpReplaceNodeGen extends RegExpPrototypeBuiltins.JSRegExpReplaceNode implements Introspection.Provider {
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
      private RegExpPrototypeBuiltinsFactory.JSRegExpReplaceNodeGen.ReplaceCachedData replaceCached_cache;
      @Node.Child
      private JSToStringNode replaceDynamic_toString1Node_;

      private JSRegExpReplaceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if ((state_0 & 1) != 0 && arguments2Value_ instanceof TruffleString) {
                  TruffleString arguments2Value__ = (TruffleString)arguments2Value_;

                  for (RegExpPrototypeBuiltinsFactory.JSRegExpReplaceNodeGen.ReplaceCachedData s0_ = this.replaceCached_cache; s0_ != null; s0_ = s0_.next_) {
                     if (JSGuards.stringEquals(s0_.equalsNode_, s0_.cachedReplaceValue_, arguments2Value__)) {
                        return this.replaceCached(
                           arguments0Value__,
                           arguments1Value_,
                           arguments2Value__,
                           s0_.cachedReplaceValue_,
                           s0_.cachedParsedReplaceValueWithNamedCG_,
                           s0_.cachedParsedReplaceValueWithoutNamedCG_,
                           s0_.toString1Node_,
                           s0_.equalsNode_
                        );
                     }
                  }
               }

               if ((state_0 & 2) != 0) {
                  return this.replaceDynamic(arguments0Value__, arguments1Value_, arguments2Value_, this.replaceDynamic_toString1Node_);
               }
            }

            if ((state_0 & 4) != 0 && fallbackGuard_(state_0, arguments0Value_, arguments1Value_, arguments2Value_)) {
               return this.doNoObject(arguments0Value_, arguments1Value_, arguments2Value_);
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
            if (!(arguments0Value instanceof JSDynamicObject)) {
               int var19;
               this.state_0_ = var19 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doNoObject(arguments0Value, arguments1Value, arguments2Value);
            } else {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (exclude == 0 && arguments2Value instanceof TruffleString) {
                  TruffleString arguments2Value_ = (TruffleString)arguments2Value;
                  int count0_ = 0;
                  RegExpPrototypeBuiltinsFactory.JSRegExpReplaceNodeGen.ReplaceCachedData s0_ = this.replaceCached_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !JSGuards.stringEquals(s0_.equalsNode_, s0_.cachedReplaceValue_, arguments2Value_)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null) {
                     TruffleString.EqualNode equalsNode__ = super.insert(TruffleString.EqualNode.create());
                     if (JSGuards.stringEquals(equalsNode__, arguments2Value_, arguments2Value_) && count0_ < 3) {
                        s0_ = super.insert(new RegExpPrototypeBuiltinsFactory.JSRegExpReplaceNodeGen.ReplaceCachedData(this.replaceCached_cache));
                        s0_.cachedReplaceValue_ = arguments2Value_;
                        s0_.cachedParsedReplaceValueWithNamedCG_ = this.parseReplaceValueWithNCG(arguments2Value_);
                        s0_.cachedParsedReplaceValueWithoutNamedCG_ = this.parseReplaceValueWithoutNCG(arguments2Value_);
                        s0_.toString1Node_ = s0_.insertAccessor(JSToStringNode.create());
                        s0_.equalsNode_ = s0_.insertAccessor(equalsNode__);
                        VarHandle.storeStoreFence();
                        this.replaceCached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                     }
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.replaceCached(
                        arguments0Value_,
                        arguments1Value,
                        arguments2Value_,
                        s0_.cachedReplaceValue_,
                        s0_.cachedParsedReplaceValueWithNamedCG_,
                        s0_.cachedParsedReplaceValueWithoutNamedCG_,
                        s0_.toString1Node_,
                        s0_.equalsNode_
                     );
                  }
               }

               this.replaceDynamic_toString1Node_ = super.insert(JSToStringNode.create());
               int var20;
               this.exclude_ = var20 = exclude | 1;
               this.replaceCached_cache = null;
               state_0 &= -2;
               int var18;
               this.state_0_ = var18 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.replaceDynamic(arguments0Value_, arguments1Value, arguments2Value, this.replaceDynamic_toString1Node_);
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
               RegExpPrototypeBuiltinsFactory.JSRegExpReplaceNodeGen.ReplaceCachedData s0_ = this.replaceCached_cache;
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
         Object[] s = new Object[]{"replaceCached", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (RegExpPrototypeBuiltinsFactory.JSRegExpReplaceNodeGen.ReplaceCachedData s0_ = this.replaceCached_cache; s0_ != null; s0_ = s0_.next_) {
               cached.add(
                  Arrays.asList(
                     s0_.cachedReplaceValue_,
                     s0_.cachedParsedReplaceValueWithNamedCG_,
                     s0_.cachedParsedReplaceValueWithoutNamedCG_,
                     s0_.toString1Node_,
                     s0_.equalsNode_
                  )
               );
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"replaceDynamic", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.replaceDynamic_toString1Node_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doNoObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         return (state_0 & 2) != 0 || !(arguments0Value instanceof JSDynamicObject);
      }

      public static RegExpPrototypeBuiltins.JSRegExpReplaceNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RegExpPrototypeBuiltinsFactory.JSRegExpReplaceNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(RegExpPrototypeBuiltins.JSRegExpReplaceNode.class)
      private static final class ReplaceCachedData extends Node {
         @Node.Child
         RegExpPrototypeBuiltinsFactory.JSRegExpReplaceNodeGen.ReplaceCachedData next_;
         @CompilerDirectives.CompilationFinal
         TruffleString cachedReplaceValue_;
         @CompilerDirectives.CompilationFinal(dimensions = 1)
         ReplaceStringParser.Token[] cachedParsedReplaceValueWithNamedCG_;
         @CompilerDirectives.CompilationFinal(dimensions = 1)
         ReplaceStringParser.Token[] cachedParsedReplaceValueWithoutNamedCG_;
         @Node.Child
         JSToStringNode toString1Node_;
         @Node.Child
         TruffleString.EqualNode equalsNode_;

         ReplaceCachedData(RegExpPrototypeBuiltinsFactory.JSRegExpReplaceNodeGen.ReplaceCachedData next_) {
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

   @GeneratedBy(RegExpPrototypeBuiltins.JSRegExpSearchNode.class)
   public static final class JSRegExpSearchNodeGen extends RegExpPrototypeBuiltins.JSRegExpSearchNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private IsJSObjectNode search_isObjectNode_;
      @Node.Child
      private JSToStringNode search_toString1Node_;

      private JSRegExpSearchNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      private boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if ((state_0 & 2) == 0 || this.search_isObjectNode_.executeBoolean(arguments0Value_)) {
               return false;
            }
         }

         return true;
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 5) != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (this.search_isObjectNode_.executeBoolean(arguments0Value__)) {
                  return this.search(arguments0Value__, arguments1Value_, this.search_isObjectNode_, this.search_toString1Node_);
               }
            }

            if ((state_0 & 4) != 0 && this.fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
               return this.search(arguments0Value_, arguments1Value_);
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

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               boolean Search_duplicateFound_ = false;
               if ((state_0 & 1) != 0 && this.search_isObjectNode_.executeBoolean(arguments0Value_)) {
                  Search_duplicateFound_ = true;
               }

               if (!Search_duplicateFound_) {
                  if ((state_0 & 2) == 0) {
                     this.search_isObjectNode_ = super.insert(IsJSObjectNode.create());
                     this.state_0_ = state_0 |= 2;
                  }

                  if (this.search_isObjectNode_.executeBoolean(arguments0Value_) && (state_0 & 1) == 0) {
                     this.search_isObjectNode_ = super.insert(IsJSObjectNode.create());
                     this.search_toString1Node_ = super.insert(JSToStringNode.create());
                     this.state_0_ = state_0 |= 1;
                     Search_duplicateFound_ = true;
                  }
               }

               if (Search_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return this.search(arguments0Value_, arguments1Value, this.search_isObjectNode_, this.search_toString1Node_);
               }
            }

            int var12;
            this.state_0_ = var12 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.search(arguments0Value, arguments1Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if ((state_0 & 5) == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & 5 & (state_0 & 5) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"search", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.search_isObjectNode_, this.search_toString1Node_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"search", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static RegExpPrototypeBuiltins.JSRegExpSearchNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RegExpPrototypeBuiltinsFactory.JSRegExpSearchNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RegExpPrototypeBuiltins.JSRegExpSplitNode.class)
   public static final class JSRegExpSplitNodeGen extends RegExpPrototypeBuiltins.JSRegExpSplitNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private JSRegExpSplitNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 30) == 0 && state_0 != 0) {
            return this.execute_int0(state_0, frameValue);
         } else {
            return (state_0 & 29) == 0 && state_0 != 0 ? this.execute_long1(state_0, frameValue) : this.execute_generic2(state_0, frameValue);
         }
      }

      private Object execute_int0(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, var7.getResult());
         }

         assert (state_0 & 1) != 0;

         if (arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.splitIntLimit(arguments0Value__, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      private Object execute_long1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);

         long arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeLong(frameValue);
         } catch (UnexpectedResultException var8) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, var8.getResult());
         }

         assert (state_0 & 2) != 0;

         if (arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            return this.splitLongLimit(arguments0Value__, arguments1Value_, arguments2Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      private Object execute_generic2(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 15) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if ((state_0 & 1) != 0 && arguments2Value_ instanceof Integer) {
                  int arguments2Value__ = (Integer)arguments2Value_;
                  return this.splitIntLimit(arguments0Value__, arguments1Value_, arguments2Value__);
               }

               if ((state_0 & 2) != 0 && arguments2Value_ instanceof Long) {
                  long arguments2Value__ = (Long)arguments2Value_;
                  return this.splitLongLimit(arguments0Value__, arguments1Value_, arguments2Value__);
               }

               if ((state_0 & 12) != 0) {
                  if ((state_0 & 4) != 0 && JSGuards.isUndefined(arguments2Value_)) {
                     return this.splitUndefinedLimit(arguments0Value__, arguments1Value_, arguments2Value_);
                  }

                  if ((state_0 & 8) != 0 && !JSGuards.isUndefined(arguments2Value_)) {
                     return this.splitObjectLimit(arguments0Value__, arguments1Value_, arguments2Value_);
                  }
               }
            }

            if ((state_0 & 16) != 0 && fallbackGuard_(state_0, arguments0Value_, arguments1Value_, arguments2Value_)) {
               return this.doNoObject(arguments0Value_, arguments1Value_, arguments2Value_);
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
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if (arguments2Value instanceof Integer) {
               int arguments2Value_ = (Integer)arguments2Value;
               int var12;
               this.state_0_ = var12 = state_0 | 1;
               return this.splitIntLimit(arguments0Value_, arguments1Value, arguments2Value_);
            }

            if (arguments2Value instanceof Long) {
               long arguments2Value_ = (Long)arguments2Value;
               int var11;
               this.state_0_ = var11 = state_0 | 2;
               return this.splitLongLimit(arguments0Value_, arguments1Value, arguments2Value_);
            }

            if (JSGuards.isUndefined(arguments2Value)) {
               int var10;
               this.state_0_ = var10 = state_0 | 4;
               return this.splitUndefinedLimit(arguments0Value_, arguments1Value, arguments2Value);
            }

            if (!JSGuards.isUndefined(arguments2Value)) {
               int var9;
               this.state_0_ = var9 = state_0 | 8;
               return this.splitObjectLimit(arguments0Value_, arguments1Value, arguments2Value);
            }
         }

         int var8;
         this.state_0_ = var8 = state_0 | 16;
         return this.doNoObject(arguments0Value, arguments1Value, arguments2Value);
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
         Object[] data = new Object[6];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"splitIntLimit", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"splitLongLimit", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"splitUndefinedLimit", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"splitObjectLimit", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"doNoObject", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         return Introspection.Provider.create(data);
      }

      private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         if (arguments0Value instanceof JSDynamicObject) {
            if ((state_0 & 1) == 0 && arguments2Value instanceof Integer) {
               return false;
            }

            if ((state_0 & 2) == 0 && arguments2Value instanceof Long) {
               return false;
            }

            if ((state_0 & 4) == 0 && JSGuards.isUndefined(arguments2Value)) {
               return false;
            }

            if ((state_0 & 8) == 0 && !JSGuards.isUndefined(arguments2Value)) {
               return false;
            }
         }

         return true;
      }

      public static RegExpPrototypeBuiltins.JSRegExpSplitNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RegExpPrototypeBuiltinsFactory.JSRegExpSplitNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(RegExpPrototypeBuiltins.JSRegExpTestNode.class)
   public static final class JSRegExpTestNodeGen extends RegExpPrototypeBuiltins.JSRegExpTestNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private RegExpPrototypeBuiltinsFactory.JSRegExpTestNodeGen.TestGenericData testGeneric_cache;

      private JSRegExpTestNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      private boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if ((state_0 & 2) == 0 || this.testGeneric_cache.isObjectNode_.executeBoolean(arguments0Value_)) {
               return false;
            }
         }

         return true;
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 5) != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               RegExpPrototypeBuiltinsFactory.JSRegExpTestNodeGen.TestGenericData s0_ = this.testGeneric_cache;
               if (s0_ != null && s0_.isObjectNode_.executeBoolean(arguments0Value__)) {
                  return this.testGeneric(arguments0Value__, arguments1Value_, s0_.isObjectNode_, s0_.toStringNode_, s0_.regExpNode_);
               }
            }

            if ((state_0 & 4) != 0 && this.fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
               return this.testError(arguments0Value_, arguments1Value_);
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

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               RegExpPrototypeBuiltinsFactory.JSRegExpTestNodeGen.TestGenericData s0_ = this.testGeneric_cache;
               boolean TestGeneric_duplicateFound_ = false;
               if ((state_0 & 1) != 0 && s0_.isObjectNode_.executeBoolean(arguments0Value_)) {
                  TestGeneric_duplicateFound_ = true;
               }

               if (!TestGeneric_duplicateFound_) {
                  if ((state_0 & 2) == 0) {
                     s0_ = super.insert(new RegExpPrototypeBuiltinsFactory.JSRegExpTestNodeGen.TestGenericData());
                     VarHandle.storeStoreFence();
                     this.testGeneric_cache = s0_;
                     s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
                     this.state_0_ = state_0 |= 2;
                  }

                  if (s0_.isObjectNode_.executeBoolean(arguments0Value_) && (state_0 & 1) == 0) {
                     s0_ = super.insert(new RegExpPrototypeBuiltinsFactory.JSRegExpTestNodeGen.TestGenericData());
                     s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
                     s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
                     s0_.regExpNode_ = s0_.insertAccessor(JSRegExpExecIntlNode.create(this.getContext()));
                     VarHandle.storeStoreFence();
                     this.testGeneric_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                     TestGeneric_duplicateFound_ = true;
                  }
               }

               if (TestGeneric_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return this.testGeneric(arguments0Value_, arguments1Value, s0_.isObjectNode_, s0_.toStringNode_, s0_.regExpNode_);
               }
            }

            int var13;
            this.state_0_ = var13 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.testError(arguments0Value, arguments1Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if ((state_0 & 5) == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & 5 & (state_0 & 5) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"testGeneric", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            RegExpPrototypeBuiltinsFactory.JSRegExpTestNodeGen.TestGenericData s0_ = this.testGeneric_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.isObjectNode_, s0_.toStringNode_, s0_.regExpNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"testError", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static RegExpPrototypeBuiltins.JSRegExpTestNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RegExpPrototypeBuiltinsFactory.JSRegExpTestNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(RegExpPrototypeBuiltins.JSRegExpTestNode.class)
      private static final class TestGenericData extends Node {
         @Node.Child
         IsJSObjectNode isObjectNode_;
         @Node.Child
         JSToStringNode toStringNode_;
         @Node.Child
         JSRegExpExecIntlNode regExpNode_;

         TestGenericData() {
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

   @GeneratedBy(RegExpPrototypeBuiltins.JSRegExpToStringNode.class)
   public static final class JSRegExpToStringNodeGen extends RegExpPrototypeBuiltins.JSRegExpToStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private RegExpPrototypeBuiltinsFactory.JSRegExpToStringNodeGen.ToStringData toString_cache;

      private JSRegExpToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      private boolean fallbackGuard_(int state_0, Object arguments0Value) {
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if ((state_0 & 2) == 0 || this.toString_cache.isObjectNode_.executeBoolean(arguments0Value_)) {
               return false;
            }
         }

         return true;
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            RegExpPrototypeBuiltinsFactory.JSRegExpToStringNodeGen.ToStringData s0_ = this.toString_cache;
            if (s0_ != null && s0_.isObjectNode_.executeBoolean(arguments0Value__)) {
               return this.toString(arguments0Value__, s0_.isObjectNode_, s0_.toString1Node_, s0_.toString2Node_);
            }
         }

         if ((state_0 & 4) != 0 && this.fallbackGuard_(state_0, arguments0Value_)) {
            return this.toString(arguments0Value_);
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

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               RegExpPrototypeBuiltinsFactory.JSRegExpToStringNodeGen.ToStringData s0_ = this.toString_cache;
               boolean ToString_duplicateFound_ = false;
               if ((state_0 & 1) != 0 && s0_.isObjectNode_.executeBoolean(arguments0Value_)) {
                  ToString_duplicateFound_ = true;
               }

               if (!ToString_duplicateFound_) {
                  if ((state_0 & 2) == 0) {
                     s0_ = super.insert(new RegExpPrototypeBuiltinsFactory.JSRegExpToStringNodeGen.ToStringData());
                     VarHandle.storeStoreFence();
                     this.toString_cache = s0_;
                     s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
                     this.state_0_ = state_0 |= 2;
                  }

                  if (s0_.isObjectNode_.executeBoolean(arguments0Value_) && (state_0 & 1) == 0) {
                     s0_ = super.insert(new RegExpPrototypeBuiltinsFactory.JSRegExpToStringNodeGen.ToStringData());
                     s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
                     s0_.toString1Node_ = s0_.insertAccessor(JSToStringNode.create());
                     s0_.toString2Node_ = s0_.insertAccessor(JSToStringNode.create());
                     VarHandle.storeStoreFence();
                     this.toString_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                     ToString_duplicateFound_ = true;
                  }
               }

               if (ToString_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return this.toString(arguments0Value_, s0_.isObjectNode_, s0_.toString1Node_, s0_.toString2Node_);
               }
            }

            int var12;
            this.state_0_ = var12 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.toString(arguments0Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if ((state_0 & 5) == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & 5 & (state_0 & 5) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"toString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            RegExpPrototypeBuiltinsFactory.JSRegExpToStringNodeGen.ToStringData s0_ = this.toString_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.isObjectNode_, s0_.toString1Node_, s0_.toString2Node_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"toString", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static RegExpPrototypeBuiltins.JSRegExpToStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RegExpPrototypeBuiltinsFactory.JSRegExpToStringNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(RegExpPrototypeBuiltins.JSRegExpToStringNode.class)
      private static final class ToStringData extends Node {
         @Node.Child
         IsJSObjectNode isObjectNode_;
         @Node.Child
         JSToStringNode toString1Node_;
         @Node.Child
         JSToStringNode toString2Node_;

         ToStringData() {
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

   @GeneratedBy(RegExpPrototypeBuiltins.RegExpFlagsGetterNode.class)
   public static final class RegExpFlagsGetterNodeGen extends RegExpPrototypeBuiltins.RegExpFlagsGetterNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private IsJSObjectNode object_isObjectNode_;
      @Node.Child
      private TruffleString.FromCharArrayUTF16Node object_fromCharArrayNode_;

      private RegExpFlagsGetterNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      private boolean fallbackGuard_(int state_0, Object arguments0Value) {
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if ((state_0 & 2) == 0 || this.object_isObjectNode_.executeBoolean(arguments0Value_)) {
               return false;
            }
         }

         return true;
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (this.object_isObjectNode_.executeBoolean(arguments0Value__)) {
               return this.doObject(arguments0Value__, this.object_isObjectNode_, this.object_fromCharArrayNode_);
            }
         }

         if ((state_0 & 4) != 0 && this.fallbackGuard_(state_0, arguments0Value_)) {
            return this.doNotObject(arguments0Value_);
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

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               boolean Object_duplicateFound_ = false;
               if ((state_0 & 1) != 0 && this.object_isObjectNode_.executeBoolean(arguments0Value_)) {
                  Object_duplicateFound_ = true;
               }

               if (!Object_duplicateFound_) {
                  if ((state_0 & 2) == 0) {
                     this.object_isObjectNode_ = super.insert(IsJSObjectNode.create());
                     this.state_0_ = state_0 |= 2;
                  }

                  if (this.object_isObjectNode_.executeBoolean(arguments0Value_) && (state_0 & 1) == 0) {
                     this.object_isObjectNode_ = super.insert(IsJSObjectNode.create());
                     this.object_fromCharArrayNode_ = super.insert(TruffleString.FromCharArrayUTF16Node.create());
                     this.state_0_ = state_0 |= 1;
                     Object_duplicateFound_ = true;
                  }
               }

               if (Object_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return this.doObject(arguments0Value_, this.object_isObjectNode_, this.object_fromCharArrayNode_);
               }
            }

            int var11;
            this.state_0_ = var11 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doNotObject(arguments0Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if ((state_0 & 5) == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            return (state_0 & 5 & (state_0 & 5) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"doObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.object_isObjectNode_, this.object_fromCharArrayNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doNotObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static RegExpPrototypeBuiltins.RegExpFlagsGetterNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new RegExpPrototypeBuiltinsFactory.RegExpFlagsGetterNodeGen(context, builtin, arguments);
      }
   }
}
