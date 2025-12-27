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
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSProxyObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ObjectPrototypeBuiltins.class)
public final class ObjectPrototypeBuiltinsFactory {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   @GeneratedBy(ObjectPrototypeBuiltins.FormatCacheNode.class)
   public static final class FormatCacheNodeGen extends ObjectPrototypeBuiltins.FormatCacheNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ObjectPrototypeBuiltinsFactory.FormatCacheNodeGen.ExecuteCachedData executeCached_cache;

      private FormatCacheNodeGen() {
      }

      @ExplodeLoop
      @Override
      public TruffleString execute(TruffleString arg0Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (ObjectPrototypeBuiltinsFactory.FormatCacheNodeGen.ExecuteCachedData s0_ = this.executeCached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (JSGuards.stringEquals(s0_.equalsNode_, s0_.cachedName_, arg0Value)) {
                     return this.executeCached(arg0Value, s0_.cachedName_, s0_.cachedResult_, s0_.equalsNode_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return this.executeUncached(arg0Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }

      private TruffleString executeAndSpecialize(TruffleString arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         TruffleString cachedName__;
         try {
            int state_0 = this.state_0_;
            int count0_ = 0;
            ObjectPrototypeBuiltinsFactory.FormatCacheNodeGen.ExecuteCachedData s0_ = this.executeCached_cache;
            if ((state_0 & 1) != 0) {
               while (s0_ != null && !JSGuards.stringEquals(s0_.equalsNode_, s0_.cachedName_, arg0Value)) {
                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null) {
               TruffleString.EqualNode equalsNode__ = super.insert(TruffleString.EqualNode.create());
               if (JSGuards.stringEquals(equalsNode__, arg0Value, arg0Value) && count0_ < 10) {
                  s0_ = super.insert(new ObjectPrototypeBuiltinsFactory.FormatCacheNodeGen.ExecuteCachedData(this.executeCached_cache));
                  s0_.cachedName_ = arg0Value;
                  s0_.cachedResult_ = this.executeUncached(arg0Value);
                  s0_.equalsNode_ = s0_.insertAccessor(equalsNode__);
                  VarHandle.storeStoreFence();
                  this.executeCached_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }
            }

            if (s0_ == null) {
               int var12;
               this.state_0_ = var12 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.executeUncached(arg0Value);
            }

            lock.unlock();
            hasLock = false;
            cachedName__ = this.executeCached(arg0Value, s0_.cachedName_, s0_.cachedResult_, s0_.equalsNode_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return cachedName__;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               ObjectPrototypeBuiltinsFactory.FormatCacheNodeGen.ExecuteCachedData s0_ = this.executeCached_cache;
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
         Object[] s = new Object[]{"executeCached", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (ObjectPrototypeBuiltinsFactory.FormatCacheNodeGen.ExecuteCachedData s0_ = this.executeCached_cache; s0_ != null; s0_ = s0_.next_) {
               cached.add(Arrays.asList(s0_.cachedName_, s0_.cachedResult_, s0_.equalsNode_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"executeUncached", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectPrototypeBuiltins.FormatCacheNode create() {
         return new ObjectPrototypeBuiltinsFactory.FormatCacheNodeGen();
      }

      @GeneratedBy(ObjectPrototypeBuiltins.FormatCacheNode.class)
      private static final class ExecuteCachedData extends Node {
         @Node.Child
         ObjectPrototypeBuiltinsFactory.FormatCacheNodeGen.ExecuteCachedData next_;
         @CompilerDirectives.CompilationFinal
         TruffleString cachedName_;
         @CompilerDirectives.CompilationFinal
         TruffleString cachedResult_;
         @Node.Child
         TruffleString.EqualNode equalsNode_;

         ExecuteCachedData(ObjectPrototypeBuiltinsFactory.FormatCacheNodeGen.ExecuteCachedData next_) {
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

   @GeneratedBy(ObjectPrototypeBuiltins.GetBuiltinToStringTagNode.class)
   public static final class GetBuiltinToStringTagNodeGen extends ObjectPrototypeBuiltins.GetBuiltinToStringTagNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private ObjectPrototypeBuiltinsFactory.GetBuiltinToStringTagNodeGen.CachedData cached_cache;

      private GetBuiltinToStringTagNodeGen() {
      }

      @ExplodeLoop
      @Override
      public TruffleString execute(JSObject arg0Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (ObjectPrototypeBuiltinsFactory.GetBuiltinToStringTagNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  assert s0_.cachedClass_ != null;

                  if (s0_.cachedClass_.isInstance((JSDynamicObject)arg0Value)) {
                     return ObjectPrototypeBuiltins.GetBuiltinToStringTagNode.cached(arg0Value, s0_.cachedClass_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return ObjectPrototypeBuiltins.GetBuiltinToStringTagNode.uncached(arg0Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }

      private TruffleString executeAndSpecialize(JSObject arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               ObjectPrototypeBuiltinsFactory.GetBuiltinToStringTagNodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null) {
                     assert s0_.cachedClass_ != null;

                     if (s0_.cachedClass_.isInstance((JSDynamicObject)arg0Value)) {
                        break;
                     }

                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  JSClass cachedClass__ = JSObject.getJSClass(arg0Value);
                  if (cachedClass__ != null && cachedClass__.isInstance((JSDynamicObject)arg0Value) && count0_ < 5) {
                     s0_ = new ObjectPrototypeBuiltinsFactory.GetBuiltinToStringTagNodeGen.CachedData(this.cached_cache);
                     s0_.cachedClass_ = cachedClass__;
                     VarHandle.storeStoreFence();
                     this.cached_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return ObjectPrototypeBuiltins.GetBuiltinToStringTagNode.cached(arg0Value, s0_.cachedClass_);
               }
            }

            int var14;
            this.exclude_ = var14 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var13;
            this.state_0_ = var13 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return ObjectPrototypeBuiltins.GetBuiltinToStringTagNode.uncached(arg0Value);
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
               ObjectPrototypeBuiltinsFactory.GetBuiltinToStringTagNodeGen.CachedData s0_ = this.cached_cache;
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
         Object[] s = new Object[]{"cached", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (ObjectPrototypeBuiltinsFactory.GetBuiltinToStringTagNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
               cached.add(Arrays.asList(s0_.cachedClass_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"uncached", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectPrototypeBuiltins.GetBuiltinToStringTagNode create() {
         return new ObjectPrototypeBuiltinsFactory.GetBuiltinToStringTagNodeGen();
      }

      @GeneratedBy(ObjectPrototypeBuiltins.GetBuiltinToStringTagNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         ObjectPrototypeBuiltinsFactory.GetBuiltinToStringTagNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         JSClass cachedClass_;

         CachedData(ObjectPrototypeBuiltinsFactory.GetBuiltinToStringTagNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }
   }

   @GeneratedBy(ObjectPrototypeBuiltins.ObjectPrototypeDefineGetterOrSetterNode.class)
   public static final class ObjectPrototypeDefineGetterOrSetterNodeGen
      extends ObjectPrototypeBuiltins.ObjectPrototypeDefineGetterOrSetterNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;

      private ObjectPrototypeDefineGetterOrSetterNodeGen(JSContext context, JSBuiltin builtin, boolean getter, JavaScriptNode[] arguments) {
         super(context, builtin, getter);
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
         return this.define(arguments0Value_, arguments1Value_, arguments2Value_);
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
         Object[] s = new Object[]{"define", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectPrototypeBuiltins.ObjectPrototypeDefineGetterOrSetterNode create(
         JSContext context, JSBuiltin builtin, boolean getter, JavaScriptNode[] arguments
      ) {
         return new ObjectPrototypeBuiltinsFactory.ObjectPrototypeDefineGetterOrSetterNodeGen(context, builtin, getter, arguments);
      }
   }

   @GeneratedBy(ObjectPrototypeBuiltins.ObjectPrototypeHasOwnPropertyNode.class)
   public static final class ObjectPrototypeHasOwnPropertyNodeGen
      extends ObjectPrototypeBuiltins.ObjectPrototypeHasOwnPropertyNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;
      @CompilerDirectives.CompilationFinal
      private int exclude_;

      private ObjectPrototypeHasOwnPropertyNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return (state_0 & 1021) == 0 && state_0 != 0 ? this.execute_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
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

         assert (state_0 & 2) != 0;

         if (arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (JSGuards.isJSObject(arguments0Value__)) {
               return this.doJSObjectIntKey(arguments0Value__, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      private Object execute_generic1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 15) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if ((state_0 & 1) != 0 && arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               if (JSGuards.isJSObject(arguments0Value__)) {
                  return this.doJSObjectTStringKey(arguments0Value__, arguments1Value__);
               }
            }

            if ((state_0 & 2) != 0 && arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               if (JSGuards.isJSObject(arguments0Value__)) {
                  return this.doJSObjectIntKey(arguments0Value__, arguments1Value__);
               }
            }

            if ((state_0 & 12) != 0) {
               if ((state_0 & 4) != 0 && JSGuards.isJSObject(arguments0Value__)) {
                  return this.doJSObjectAnyKey(arguments0Value__, arguments1Value_);
               }

               if ((state_0 & 8) != 0 && JSGuards.isNullOrUndefined(arguments0Value__)) {
                  return this.hasOwnPropertyNullOrUndefined(arguments0Value__, arguments1Value_);
               }
            }
         }

         if ((state_0 & 1008) != 0) {
            if ((state_0 & 16) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__x = (TruffleString)arguments0Value_;
               return this.hasOwnPropertyTString(arguments0Value__x, arguments1Value_);
            }

            if ((state_0 & 32) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
               return this.hasOwnPropertyPrimitive(arguments0Value_, arguments1Value_);
            }

            if ((state_0 & 64) != 0 && arguments0Value_ instanceof Symbol) {
               Symbol arguments0Value__x = (Symbol)arguments0Value_;
               return this.hasOwnPropertySymbol(arguments0Value__x, arguments1Value_);
            }

            if ((state_0 & 128) != 0 && arguments0Value_ instanceof SafeInteger) {
               SafeInteger arguments0Value__x = (SafeInteger)arguments0Value_;
               return this.hasOwnPropertySafeInteger(arguments0Value__x, arguments1Value_);
            }

            if ((state_0 & 256) != 0 && arguments0Value_ instanceof BigInt) {
               BigInt arguments0Value__x = (BigInt)arguments0Value_;
               return this.hasOwnPropertyBigInt(arguments0Value__x, arguments1Value_);
            }

            if ((state_0 & 512) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
               return this.hasOwnPropertyForeign(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         return (state_0 & 1021) == 0 && state_0 != 0 ? this.executeBoolean_int2(state_0, frameValue) : this.executeBoolean_generic3(state_0, frameValue);
      }

      private boolean executeBoolean_int2(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var6) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, var6.getResult());
         }

         assert (state_0 & 2) != 0;

         if (arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (JSGuards.isJSObject(arguments0Value__)) {
               return this.doJSObjectIntKey(arguments0Value__, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      private boolean executeBoolean_generic3(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 15) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if ((state_0 & 1) != 0 && arguments1Value_ instanceof TruffleString) {
               TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
               if (JSGuards.isJSObject(arguments0Value__)) {
                  return this.doJSObjectTStringKey(arguments0Value__, arguments1Value__);
               }
            }

            if ((state_0 & 2) != 0 && arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               if (JSGuards.isJSObject(arguments0Value__)) {
                  return this.doJSObjectIntKey(arguments0Value__, arguments1Value__);
               }
            }

            if ((state_0 & 12) != 0) {
               if ((state_0 & 4) != 0 && JSGuards.isJSObject(arguments0Value__)) {
                  return this.doJSObjectAnyKey(arguments0Value__, arguments1Value_);
               }

               if ((state_0 & 8) != 0 && JSGuards.isNullOrUndefined(arguments0Value__)) {
                  return this.hasOwnPropertyNullOrUndefined(arguments0Value__, arguments1Value_);
               }
            }
         }

         if ((state_0 & 1008) != 0) {
            if ((state_0 & 16) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__x = (TruffleString)arguments0Value_;
               return this.hasOwnPropertyTString(arguments0Value__x, arguments1Value_);
            }

            if ((state_0 & 32) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
               return this.hasOwnPropertyPrimitive(arguments0Value_, arguments1Value_);
            }

            if ((state_0 & 64) != 0 && arguments0Value_ instanceof Symbol) {
               Symbol arguments0Value__x = (Symbol)arguments0Value_;
               return this.hasOwnPropertySymbol(arguments0Value__x, arguments1Value_);
            }

            if ((state_0 & 128) != 0 && arguments0Value_ instanceof SafeInteger) {
               SafeInteger arguments0Value__x = (SafeInteger)arguments0Value_;
               return this.hasOwnPropertySafeInteger(arguments0Value__x, arguments1Value_);
            }

            if ((state_0 & 256) != 0 && arguments0Value_ instanceof BigInt) {
               BigInt arguments0Value__x = (BigInt)arguments0Value_;
               return this.hasOwnPropertyBigInt(arguments0Value__x, arguments1Value_);
            }

            if ((state_0 & 512) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
               return this.hasOwnPropertyForeign(arguments0Value_, arguments1Value_);
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
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if ((exclude & 1) == 0 && arguments1Value instanceof TruffleString) {
                  TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                  if (JSGuards.isJSObject(arguments0Value_)) {
                     int var23;
                     this.state_0_ = var23 = state_0 | 1;
                     lock.unlock();
                     hasLock = false;
                     return this.doJSObjectTStringKey(arguments0Value_, arguments1Value_);
                  }
               }

               if ((exclude & 2) == 0 && arguments1Value instanceof Integer) {
                  int arguments1Value_ = (Integer)arguments1Value;
                  if (JSGuards.isJSObject(arguments0Value_)) {
                     int var22;
                     this.state_0_ = var22 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.doJSObjectIntKey(arguments0Value_, arguments1Value_);
                  }
               }

               if (JSGuards.isJSObject(arguments0Value_)) {
                  int var24;
                  this.exclude_ = var24 = exclude | 3;
                  state_0 &= -4;
                  int var21;
                  this.state_0_ = var21 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.doJSObjectAnyKey(arguments0Value_, arguments1Value);
               }

               if (JSGuards.isNullOrUndefined(arguments0Value_)) {
                  int var19;
                  this.state_0_ = var19 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.hasOwnPropertyNullOrUndefined(arguments0Value_, arguments1Value);
               }
            }

            if (arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_x = (TruffleString)arguments0Value;
               int var18;
               this.state_0_ = var18 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.hasOwnPropertyTString(arguments0Value_x, arguments1Value);
            } else if (!JSGuards.isTruffleObject(arguments0Value)) {
               int var17;
               this.state_0_ = var17 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.hasOwnPropertyPrimitive(arguments0Value, arguments1Value);
            } else if (arguments0Value instanceof Symbol) {
               Symbol arguments0Value_x = (Symbol)arguments0Value;
               int var16;
               this.state_0_ = var16 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return this.hasOwnPropertySymbol(arguments0Value_x, arguments1Value);
            } else if (arguments0Value instanceof SafeInteger) {
               SafeInteger arguments0Value_x = (SafeInteger)arguments0Value;
               int var15;
               this.state_0_ = var15 = state_0 | 128;
               lock.unlock();
               hasLock = false;
               return this.hasOwnPropertySafeInteger(arguments0Value_x, arguments1Value);
            } else if (arguments0Value instanceof BigInt) {
               BigInt arguments0Value_x = (BigInt)arguments0Value;
               int var13;
               this.state_0_ = var13 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               return this.hasOwnPropertyBigInt(arguments0Value_x, arguments1Value);
            } else if (!JSGuards.isForeignObject(arguments0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            } else {
               int var14;
               this.state_0_ = var14 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               return this.hasOwnPropertyForeign(arguments0Value, arguments1Value);
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
         Object[] data = new Object[11];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"doJSObjectTStringKey", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else if ((exclude & 1) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doJSObjectIntKey", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else if ((exclude & 2) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doJSObjectAnyKey", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"hasOwnPropertyNullOrUndefined", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"hasOwnPropertyTString", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"hasOwnPropertyPrimitive", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"hasOwnPropertySymbol", null, null};
         if ((state_0 & 64) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         s = new Object[]{"hasOwnPropertySafeInteger", null, null};
         if ((state_0 & 128) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[8] = s;
         s = new Object[]{"hasOwnPropertyBigInt", null, null};
         if ((state_0 & 256) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[9] = s;
         s = new Object[]{"hasOwnPropertyForeign", null, null};
         if ((state_0 & 512) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[10] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectPrototypeBuiltins.ObjectPrototypeHasOwnPropertyNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectPrototypeBuiltinsFactory.ObjectPrototypeHasOwnPropertyNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ObjectPrototypeBuiltins.ObjectPrototypeIsPrototypeOfNode.class)
   public static final class ObjectPrototypeIsPrototypeOfNodeGen
      extends ObjectPrototypeBuiltins.ObjectPrototypeIsPrototypeOfNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ObjectPrototypeIsPrototypeOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
               if (JSGuards.isJSObject(arguments1Value__)) {
                  return this.isPrototypeOf(arguments0Value_, arguments1Value__);
               }
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments1Value_)) {
               return this.isPrototypeOfNoObject(arguments0Value_, arguments1Value_);
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
            if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
               if (JSGuards.isJSObject(arguments1Value__)) {
                  return this.isPrototypeOf(arguments0Value_, arguments1Value__);
               }
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments1Value_)) {
               return this.isPrototypeOfNoObject(arguments0Value_, arguments1Value_);
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
         if (arguments1Value instanceof JSDynamicObject) {
            JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
            if (JSGuards.isJSObject(arguments1Value_)) {
               int var6;
               this.state_0_ = var6 = state_0 | 1;
               return this.isPrototypeOf(arguments0Value, arguments1Value_);
            }
         }

         if (!JSGuards.isJSObject(arguments1Value)) {
            int var5;
            this.state_0_ = var5 = state_0 | 2;
            return this.isPrototypeOfNoObject(arguments0Value, arguments1Value);
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
         Object[] s = new Object[]{"isPrototypeOf", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"isPrototypeOfNoObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectPrototypeBuiltins.ObjectPrototypeIsPrototypeOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectPrototypeBuiltinsFactory.ObjectPrototypeIsPrototypeOfNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ObjectPrototypeBuiltins.ObjectPrototypeLookupGetterOrSetterNode.class)
   public static final class ObjectPrototypeLookupGetterOrSetterNodeGen
      extends ObjectPrototypeBuiltins.ObjectPrototypeLookupGetterOrSetterNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private ObjectPrototypeLookupGetterOrSetterNodeGen(JSContext context, JSBuiltin builtin, boolean getter, JavaScriptNode[] arguments) {
         super(context, builtin, getter);
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
         return this.lookup(arguments0Value_, arguments1Value_);
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
         Object[] s = new Object[]{"lookup", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectPrototypeBuiltins.ObjectPrototypeLookupGetterOrSetterNode create(
         JSContext context, JSBuiltin builtin, boolean getter, JavaScriptNode[] arguments
      ) {
         return new ObjectPrototypeBuiltinsFactory.ObjectPrototypeLookupGetterOrSetterNodeGen(context, builtin, getter, arguments);
      }
   }

   @GeneratedBy(ObjectPrototypeBuiltins.ObjectPrototypePropertyIsEnumerableNode.class)
   public static final class ObjectPrototypePropertyIsEnumerableNodeGen
      extends ObjectPrototypeBuiltins.ObjectPrototypePropertyIsEnumerableNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private ObjectPrototypePropertyIsEnumerableNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.propertyIsEnumerable(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.propertyIsEnumerable(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"propertyIsEnumerable", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectPrototypeBuiltins.ObjectPrototypePropertyIsEnumerableNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectPrototypeBuiltinsFactory.ObjectPrototypePropertyIsEnumerableNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ObjectPrototypeBuiltins.ObjectPrototypeToLocaleStringNode.class)
   public static final class ObjectPrototypeToLocaleStringNodeGen
      extends ObjectPrototypeBuiltins.ObjectPrototypeToLocaleStringNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private ObjectPrototypeToLocaleStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         return this.toLocaleString(arguments0Value_);
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
         Object[] s = new Object[]{"toLocaleString", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectPrototypeBuiltins.ObjectPrototypeToLocaleStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectPrototypeBuiltinsFactory.ObjectPrototypeToLocaleStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ObjectPrototypeBuiltins.ObjectPrototypeToStringNode.class)
   public static final class ObjectPrototypeToStringNodeGen extends ObjectPrototypeBuiltins.ObjectPrototypeToStringNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private ObjectPrototypeBuiltins.GetBuiltinToStringTagNode builtinTag;
      @Node.Child
      private InteropLibrary foreignObject0_interop_;

      private ObjectPrototypeToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSObject) {
            JSObject arguments0Value__ = (JSObject)arguments0Value_;
            if (!JSGuards.isJSProxy(arguments0Value__)) {
               return this.doJSObject(arguments0Value__, this.builtinTag);
            }
         }

         if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSProxyObject) {
            JSProxyObject arguments0Value__ = (JSProxyObject)arguments0Value_;
            return this.doJSProxy(arguments0Value__, this.builtinTag);
         } else {
            if ((state_0 & 60) != 0) {
               if ((state_0 & 4) != 0 && JSGuards.isJSNull(arguments0Value_)) {
                  return this.doNull(arguments0Value_);
               }

               if ((state_0 & 8) != 0 && JSGuards.isUndefined(arguments0Value_)) {
                  return this.doUndefined(arguments0Value_);
               }

               if ((state_0 & 16) != 0 && this.foreignObject0_interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                  return this.doForeignObject(arguments0Value_, this.foreignObject0_interop_);
               }

               if ((state_0 & 32) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                  return this.foreignObject1Boundary(state_0, arguments0Value_);
               }
            }

            if ((state_0 & 64) != 0 && arguments0Value_ instanceof Symbol) {
               Symbol arguments0Value__ = (Symbol)arguments0Value_;
               return this.doSymbol(arguments0Value__);
            } else if ((state_0 & 128) != 0 && arguments0Value_ instanceof TruffleString) {
               TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
               return this.doString(arguments0Value__);
            } else if ((state_0 & 256) != 0 && arguments0Value_ instanceof SafeInteger) {
               SafeInteger arguments0Value__ = (SafeInteger)arguments0Value_;
               return this.doSafeInteger(arguments0Value__);
            } else if ((state_0 & 512) != 0 && arguments0Value_ instanceof BigInt) {
               BigInt arguments0Value__ = (BigInt)arguments0Value_;
               return this.doBigInt(arguments0Value__);
            } else if ((state_0 & 1024) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
               return this.doObject(arguments0Value_);
            } else {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return this.executeAndSpecialize(arguments0Value_);
            }
         }
      }

      @CompilerDirectives.TruffleBoundary
      private Object foreignObject1Boundary(int state_0, Object arguments0Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         TruffleString var6;
         try {
            InteropLibrary foreignObject1_interop__ = ObjectPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var6 = this.doForeignObject(arguments0Value_, foreignObject1_interop__);
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
            if (arguments0Value instanceof JSObject) {
               JSObject arguments0Value_ = (JSObject)arguments0Value;
               if (!JSGuards.isJSProxy(arguments0Value_)) {
                  this.builtinTag = super.insert(this.builtinTag == null ? ObjectPrototypeBuiltins.GetBuiltinToStringTagNode.create() : this.builtinTag);
                  int var28;
                  this.state_0_ = var28 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.doJSObject(arguments0Value_, this.builtinTag);
               }
            }

            if (arguments0Value instanceof JSProxyObject) {
               JSProxyObject arguments0Value_ = (JSProxyObject)arguments0Value;
               this.builtinTag = super.insert(this.builtinTag == null ? ObjectPrototypeBuiltins.GetBuiltinToStringTagNode.create() : this.builtinTag);
               int var27;
               this.state_0_ = var27 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doJSProxy(arguments0Value_, this.builtinTag);
            } else if (JSGuards.isJSNull(arguments0Value)) {
               int var26;
               this.state_0_ = var26 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doNull(arguments0Value);
            } else if (JSGuards.isUndefined(arguments0Value)) {
               int var25;
               this.state_0_ = var25 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doUndefined(arguments0Value);
            } else {
               if (exclude == 0) {
                  boolean ForeignObject0_duplicateFound_ = false;
                  if ((state_0 & 16) != 0 && this.foreignObject0_interop_.accepts(arguments0Value) && JSGuards.isForeignObject(arguments0Value)) {
                     ForeignObject0_duplicateFound_ = true;
                  }

                  if (!ForeignObject0_duplicateFound_ && JSGuards.isForeignObject(arguments0Value) && (state_0 & 16) == 0) {
                     this.foreignObject0_interop_ = super.insert(ObjectPrototypeBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     this.state_0_ = state_0 |= 16;
                     ForeignObject0_duplicateFound_ = true;
                  }

                  if (ForeignObject0_duplicateFound_) {
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignObject(arguments0Value, this.foreignObject0_interop_);
                  }
               }

               InteropLibrary foreignObject1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arguments0Value)) {
                     foreignObject1_interop__ = ObjectPrototypeBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     int var29;
                     this.exclude_ = var29 = exclude | 1;
                     state_0 &= -17;
                     int var24;
                     this.state_0_ = var24 = state_0 | 32;
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignObject(arguments0Value, foreignObject1_interop__);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               if (arguments0Value instanceof Symbol) {
                  Symbol arguments0Value_ = (Symbol)arguments0Value;
                  int var22;
                  this.state_0_ = var22 = state_0 | 64;
                  lock.unlock();
                  hasLock = false;
                  return this.doSymbol(arguments0Value_);
               } else if (arguments0Value instanceof TruffleString) {
                  TruffleString arguments0Value_ = (TruffleString)arguments0Value;
                  int var21;
                  this.state_0_ = var21 = state_0 | 128;
                  lock.unlock();
                  hasLock = false;
                  return this.doString(arguments0Value_);
               } else if (arguments0Value instanceof SafeInteger) {
                  SafeInteger arguments0Value_ = (SafeInteger)arguments0Value;
                  int var20;
                  this.state_0_ = var20 = state_0 | 256;
                  lock.unlock();
                  hasLock = false;
                  return this.doSafeInteger(arguments0Value_);
               } else if (arguments0Value instanceof BigInt) {
                  BigInt arguments0Value_ = (BigInt)arguments0Value;
                  int var18;
                  this.state_0_ = var18 = state_0 | 512;
                  lock.unlock();
                  hasLock = false;
                  return this.doBigInt(arguments0Value_);
               } else if (JSGuards.isTruffleObject(arguments0Value)) {
                  throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
               } else {
                  int var19;
                  this.state_0_ = var19 = state_0 | 1024;
                  lock.unlock();
                  hasLock = false;
                  return this.doObject(arguments0Value);
               }
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
         Object[] data = new Object[12];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"doJSObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.builtinTag));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doJSProxy", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.builtinTag));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doNull", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"doUndefined", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"doForeignObject", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.foreignObject0_interop_));
            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"doForeignObject", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"doSymbol", null, null};
         if ((state_0 & 64) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         s = new Object[]{"doString", null, null};
         if ((state_0 & 128) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[8] = s;
         s = new Object[]{"doSafeInteger", null, null};
         if ((state_0 & 256) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[9] = s;
         s = new Object[]{"doBigInt", null, null};
         if ((state_0 & 512) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[10] = s;
         s = new Object[]{"doObject", null, null};
         if ((state_0 & 1024) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[11] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectPrototypeBuiltins.ObjectPrototypeToStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectPrototypeBuiltinsFactory.ObjectPrototypeToStringNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ObjectPrototypeBuiltins.ObjectPrototypeValueOfNode.class)
   public static final class ObjectPrototypeValueOfNodeGen extends ObjectPrototypeBuiltins.ObjectPrototypeValueOfNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private InteropLibrary valueOfForeign_interop_;

      private ObjectPrototypeValueOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (JSGuards.isJSDynamicObject(arguments0Value__)) {
               return this.valueOfJSObject(arguments0Value__);
            }
         }

         if ((state_0 & 2) != 0 && arguments0Value_ instanceof Symbol) {
            Symbol arguments0Value__ = (Symbol)arguments0Value_;
            return this.valueOfSymbol(arguments0Value__);
         } else if ((state_0 & 4) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            return this.valueOfLazyString(arguments0Value__);
         } else if ((state_0 & 8) != 0 && arguments0Value_ instanceof SafeInteger) {
            SafeInteger arguments0Value__ = (SafeInteger)arguments0Value_;
            return this.valueOfSafeInteger(arguments0Value__);
         } else if ((state_0 & 16) != 0 && arguments0Value_ instanceof BigInt) {
            BigInt arguments0Value__ = (BigInt)arguments0Value_;
            return this.valueOfBigInt(arguments0Value__);
         } else {
            if ((state_0 & 96) != 0) {
               if ((state_0 & 32) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
                  return this.valueOfOther(arguments0Value_);
               }

               if ((state_0 & 64) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                  return this.valueOfForeign(arguments0Value_, this.valueOfForeign_interop_);
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

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSDynamicObject(arguments0Value_)) {
                  int var16;
                  this.state_0_ = var16 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.valueOfJSObject(arguments0Value_);
               }
            }

            if (arguments0Value instanceof Symbol) {
               Symbol arguments0Value_ = (Symbol)arguments0Value;
               int var15;
               this.state_0_ = var15 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.valueOfSymbol(arguments0Value_);
            } else if (arguments0Value instanceof TruffleString) {
               TruffleString arguments0Value_ = (TruffleString)arguments0Value;
               int var14;
               this.state_0_ = var14 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.valueOfLazyString(arguments0Value_);
            } else if (arguments0Value instanceof SafeInteger) {
               SafeInteger arguments0Value_ = (SafeInteger)arguments0Value;
               int var13;
               this.state_0_ = var13 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.valueOfSafeInteger(arguments0Value_);
            } else if (arguments0Value instanceof BigInt) {
               BigInt arguments0Value_ = (BigInt)arguments0Value;
               int var12;
               this.state_0_ = var12 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.valueOfBigInt(arguments0Value_);
            } else if (!JSGuards.isTruffleObject(arguments0Value)) {
               int var10;
               this.state_0_ = var10 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.valueOfOther(arguments0Value);
            } else if (!JSGuards.isForeignObject(arguments0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
            } else {
               this.valueOfForeign_interop_ = super.insert(ObjectPrototypeBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
               int var11;
               this.state_0_ = var11 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return this.valueOfForeign(arguments0Value, this.valueOfForeign_interop_);
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
         Object[] data = new Object[8];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"valueOfJSObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"valueOfSymbol", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"valueOfLazyString", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"valueOfSafeInteger", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"valueOfBigInt", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"valueOfOther", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"valueOfForeign", null, null};
         if ((state_0 & 64) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.valueOfForeign_interop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectPrototypeBuiltins.ObjectPrototypeValueOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectPrototypeBuiltinsFactory.ObjectPrototypeValueOfNodeGen(context, builtin, arguments);
      }
   }
}
