package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.access.IsJSClassNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSRegExp;
import com.oracle.truffle.js.runtime.builtins.JSRegExpObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TRegexUtil;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSRegExpExecIntlNode.class)
public final class JSRegExpExecIntlNodeGen extends JSRegExpExecIntlNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private JSRegExpExecIntlNodeGen(JSContext context) {
      super(context);
   }

   @Override
   public Object execute(JSDynamicObject arg0Value, Object arg1Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0 && arg1Value instanceof TruffleString) {
         TruffleString arg1Value_ = (TruffleString)arg1Value;
         return this.doGeneric(arg0Value, arg1Value_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }
   }

   private Object executeAndSpecialize(JSDynamicObject arg0Value, Object arg1Value) {
      int state_0 = this.state_0_;
      if (arg1Value instanceof TruffleString) {
         TruffleString arg1Value_ = (TruffleString)arg1Value;
         int var5;
         this.state_0_ = var5 = state_0 | 1;
         return this.doGeneric(arg0Value, arg1Value_);
      } else {
         throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
      }
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
      Object[] s = new Object[]{"doGeneric", null, null};
      if (state_0 != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      return Introspection.Provider.create(data);
   }

   public static JSRegExpExecIntlNode create(JSContext context) {
      return new JSRegExpExecIntlNodeGen(context);
   }

   @GeneratedBy(JSRegExpExecIntlNode.BuildGroupsObjectNode.class)
   public static final class BuildGroupsObjectNodeGen extends JSRegExpExecIntlNode.BuildGroupsObjectNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSRegExpExecIntlNodeGen.BuildGroupsObjectNodeGen.CachedGroupsFactoryData cachedGroupsFactory_cache;

      private BuildGroupsObjectNodeGen() {
      }

      @ExplodeLoop
      @Override
      public JSDynamicObject execute(JSContext arg0Value, JSDynamicObject arg1Value, Object arg2Value, Object arg3Value, boolean arg4Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0 && arg3Value instanceof TruffleString) {
            TruffleString arg3Value_ = (TruffleString)arg3Value;
            if ((state_0 & 1) != 0) {
               for (JSRegExpExecIntlNodeGen.BuildGroupsObjectNodeGen.CachedGroupsFactoryData s0_ = this.cachedGroupsFactory_cache; s0_ != null; s0_ = s0_.next_) {
                  if (JSRegExp.getGroupsFactory(arg1Value) == s0_.cachedGroupsFactory_ || JSRegExp.getCompiledRegex(arg1Value) == s0_.cachedCompiledRegex_) {
                     return this.doCachedGroupsFactory(
                        arg0Value, arg1Value, arg2Value, arg3Value_, arg4Value, s0_.cachedCompiledRegex_, s0_.cachedGroupsFactory_, s0_.isJSRegExpNode_
                     );
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return this.doVaryingGroupsFactory(arg0Value, arg1Value, arg2Value, arg3Value_, arg4Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value, arg4Value);
      }

      private JSDynamicObject executeAndSpecialize(JSContext arg0Value, JSDynamicObject arg1Value, Object arg2Value, Object arg3Value, boolean arg4Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject var18;
         try {
            int state_0 = this.state_0_;
            if (!(arg3Value instanceof TruffleString)) {
               throw new UnsupportedSpecializationException(
                  this, new Node[]{null, null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value, arg4Value
               );
            }

            TruffleString arg3Value_ = (TruffleString)arg3Value;
            int count0_ = 0;
            JSRegExpExecIntlNodeGen.BuildGroupsObjectNodeGen.CachedGroupsFactoryData s0_ = this.cachedGroupsFactory_cache;
            if ((state_0 & 1) != 0) {
               while (
                  s0_ != null
                     && JSRegExp.getGroupsFactory(arg1Value) != s0_.cachedGroupsFactory_
                     && JSRegExp.getCompiledRegex(arg1Value) != s0_.cachedCompiledRegex_
               ) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null) {
               Object cachedCompiledRegex__ = JSRegExp.getCompiledRegex(arg1Value);
               JSObjectFactory cachedGroupsFactory__ = JSRegExp.getGroupsFactory(arg1Value);
               if ((JSRegExp.getGroupsFactory(arg1Value) == cachedGroupsFactory__ || JSRegExp.getCompiledRegex(arg1Value) == cachedCompiledRegex__)
                  && count0_ < 3) {
                  s0_ = super.insert(new JSRegExpExecIntlNodeGen.BuildGroupsObjectNodeGen.CachedGroupsFactoryData(this.cachedGroupsFactory_cache));
                  s0_.cachedCompiledRegex_ = cachedCompiledRegex__;
                  s0_.cachedGroupsFactory_ = cachedGroupsFactory__;
                  s0_.isJSRegExpNode_ = s0_.insertAccessor(JSRegExpExecIntlNode.createIsJSRegExpNode());
                  VarHandle.storeStoreFence();
                  this.cachedGroupsFactory_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }
            }

            if (s0_ == null) {
               int var17;
               this.state_0_ = var17 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doVaryingGroupsFactory(arg0Value, arg1Value, arg2Value, arg3Value_, arg4Value);
            }

            lock.unlock();
            hasLock = false;
            var18 = this.doCachedGroupsFactory(
               arg0Value, arg1Value, arg2Value, arg3Value_, arg4Value, s0_.cachedCompiledRegex_, s0_.cachedGroupsFactory_, s0_.isJSRegExpNode_
            );
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var18;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               JSRegExpExecIntlNodeGen.BuildGroupsObjectNodeGen.CachedGroupsFactoryData s0_ = this.cachedGroupsFactory_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"doCachedGroupsFactory", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (JSRegExpExecIntlNodeGen.BuildGroupsObjectNodeGen.CachedGroupsFactoryData s0_ = this.cachedGroupsFactory_cache; s0_ != null; s0_ = s0_.next_) {
               cached.add(Arrays.asList(s0_.cachedCompiledRegex_, s0_.cachedGroupsFactory_, s0_.isJSRegExpNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doVaryingGroupsFactory", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static JSRegExpExecIntlNode.BuildGroupsObjectNode create() {
         return new JSRegExpExecIntlNodeGen.BuildGroupsObjectNodeGen();
      }

      @GeneratedBy(JSRegExpExecIntlNode.BuildGroupsObjectNode.class)
      private static final class CachedGroupsFactoryData extends Node {
         @Node.Child
         JSRegExpExecIntlNodeGen.BuildGroupsObjectNodeGen.CachedGroupsFactoryData next_;
         @CompilerDirectives.CompilationFinal
         Object cachedCompiledRegex_;
         @CompilerDirectives.CompilationFinal
         JSObjectFactory cachedGroupsFactory_;
         @Node.Child
         IsJSClassNode isJSRegExpNode_;

         CachedGroupsFactoryData(JSRegExpExecIntlNodeGen.BuildGroupsObjectNodeGen.CachedGroupsFactoryData next_) {
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

   @GeneratedBy(JSRegExpExecIntlNode.JSRegExpExecBuiltinNode.class)
   public static final class JSRegExpExecBuiltinNodeGen extends JSRegExpExecIntlNode.JSRegExpExecBuiltinNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private JSRegExpExecIntlNodeGen.JSRegExpExecBuiltinNodeGen.CachedData cached_cache;

      private JSRegExpExecBuiltinNodeGen(JSContext context) {
         super(context);
      }

      @ExplodeLoop
      @Override
      public Object execute(JSRegExpObject arg0Value, TruffleString arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (JSRegExpExecIntlNodeGen.JSRegExpExecBuiltinNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (JSRegExp.getCompiledRegex(arg0Value) == s0_.cachedCompiledRegex_) {
                     return this.doCached(arg0Value, arg1Value, s0_.cachedCompiledRegex_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return this.doDynamic(arg0Value, arg1Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private Object executeAndSpecialize(JSRegExpObject arg0Value, TruffleString arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               JSRegExpExecIntlNodeGen.JSRegExpExecBuiltinNodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && JSRegExp.getCompiledRegex(arg0Value) != s0_.cachedCompiledRegex_) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  Object cachedCompiledRegex__ = JSRegExp.getCompiledRegex(arg0Value);
                  if (JSRegExp.getCompiledRegex(arg0Value) == cachedCompiledRegex__ && count0_ < 3) {
                     s0_ = new JSRegExpExecIntlNodeGen.JSRegExpExecBuiltinNodeGen.CachedData(this.cached_cache);
                     s0_.cachedCompiledRegex_ = cachedCompiledRegex__;
                     VarHandle.storeStoreFence();
                     this.cached_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(arg0Value, arg1Value, s0_.cachedCompiledRegex_);
               }
            }

            int var15;
            this.exclude_ = var15 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var14;
            this.state_0_ = var14 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doDynamic(arg0Value, arg1Value);
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
               JSRegExpExecIntlNodeGen.JSRegExpExecBuiltinNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"doCached", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (JSRegExpExecIntlNodeGen.JSRegExpExecBuiltinNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               cached.add(Arrays.asList(s0_.cachedCompiledRegex_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doDynamic", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static JSRegExpExecIntlNode.JSRegExpExecBuiltinNode create(JSContext context) {
         return new JSRegExpExecIntlNodeGen.JSRegExpExecBuiltinNodeGen(context);
      }

      @GeneratedBy(JSRegExpExecIntlNode.JSRegExpExecBuiltinNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         JSRegExpExecIntlNodeGen.JSRegExpExecBuiltinNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         Object cachedCompiledRegex_;

         CachedData(JSRegExpExecIntlNodeGen.JSRegExpExecBuiltinNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }
   }

   @GeneratedBy(JSRegExpExecIntlNode.JSRegExpExecIntlIgnoreLastIndexNode.class)
   public static final class JSRegExpExecIntlIgnoreLastIndexNodeGen
      extends JSRegExpExecIntlNode.JSRegExpExecIntlIgnoreLastIndexNode
      implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private TRegexUtil.TRegexCompiledRegexAccessor compiledRegexAccessor_;
      @Node.Child
      private TRegexUtil.TRegexResultAccessor regexResultAccessor_;

      private JSRegExpExecIntlIgnoreLastIndexNodeGen(JSContext context, boolean doStaticResultUpdate) {
         super(context, doStaticResultUpdate);
      }

      @Override
      public Object execute(JSDynamicObject arg0Value, Object arg1Value, long arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0 && arg1Value instanceof TruffleString) {
            TruffleString arg1Value_ = (TruffleString)arg1Value;
            return this.doGeneric(arg0Value, arg1Value_, arg2Value, this.compiledRegexAccessor_, this.regexResultAccessor_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
         }
      }

      private Object executeAndSpecialize(JSDynamicObject arg0Value, Object arg1Value, long arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var9;
         try {
            int state_0 = this.state_0_;
            if (!(arg1Value instanceof TruffleString)) {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            }

            TruffleString arg1Value_ = (TruffleString)arg1Value;
            this.compiledRegexAccessor_ = super.insert(TRegexUtil.TRegexCompiledRegexAccessor.create());
            this.regexResultAccessor_ = super.insert(TRegexUtil.TRegexResultAccessor.create());
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var9 = this.doGeneric(arg0Value, arg1Value_, arg2Value, this.compiledRegexAccessor_, this.regexResultAccessor_);
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
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"doGeneric", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.compiledRegexAccessor_, this.regexResultAccessor_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static JSRegExpExecIntlNode.JSRegExpExecIntlIgnoreLastIndexNode create(JSContext context, boolean doStaticResultUpdate) {
         return new JSRegExpExecIntlNodeGen.JSRegExpExecIntlIgnoreLastIndexNodeGen(context, doStaticResultUpdate);
      }
   }
}
