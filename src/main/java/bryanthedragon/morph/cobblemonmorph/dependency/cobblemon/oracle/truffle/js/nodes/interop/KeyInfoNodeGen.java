package com.oracle.truffle.js.nodes.interop;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
import com.oracle.truffle.js.nodes.access.GetPrototypeNodeGen;
import com.oracle.truffle.js.nodes.access.IsExtensibleNode;
import com.oracle.truffle.js.nodes.access.IsExtensibleNodeGen;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNodeGen;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(KeyInfoNode.class)
public final class KeyInfoNodeGen extends KeyInfoNode implements Introspection.Provider {
   private static final KeyInfoNodeGen.Uncached UNCACHED = new KeyInfoNodeGen.Uncached();
   private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private KeyInfoNodeGen.CachedOwnPropertyData cachedOwnProperty_cache;
   @Node.Child
   private KeyInfoNodeGen.MemberData member_cache;

   private KeyInfoNodeGen() {
   }

   @ExplodeLoop
   @Override
   public boolean execute(JSDynamicObject arg0Value, String arg1Value, int arg2Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            for (KeyInfoNodeGen.CachedOwnPropertyData s0_ = this.cachedOwnProperty_cache; s0_ != null; s0_ = s0_.next_) {
               if (s0_.objectLibrary_.accepts(arg0Value) && !JSGuards.isJSProxy(arg0Value)) {
                  Property property__ = s0_.objectLibrary_.getProperty(arg0Value, arg1Value);
                  if (property__ != null) {
                     return KeyInfoNode.cachedOwnProperty(
                        arg0Value, arg1Value, arg2Value, s0_.objectLibrary_, property__, s0_.isCallable_, s0_.proxyBranch_, s0_.fromJavaStringNode_
                     );
                  }
               }
            }
         }

         if ((state_0 & 2) != 0) {
            KeyInfoNodeGen.MemberData s1_ = this.member_cache;
            if (s1_ != null) {
               return KeyInfoNode.member(arg0Value, arg1Value, arg2Value, s1_.getPrototype_, s1_.isCallable_, s1_.isExtensible_, s1_.fromJavaStringNode_);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
   }

   private boolean executeAndSpecialize(JSDynamicObject arg0Value, String arg1Value, int arg2Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Property property__ = null;
         if (exclude == 0) {
            int count0_ = 0;
            KeyInfoNodeGen.CachedOwnPropertyData s0_ = this.cachedOwnProperty_cache;
            if ((state_0 & 1) != 0) {
               while (s0_ != null) {
                  if (s0_.objectLibrary_.accepts(arg0Value) && !JSGuards.isJSProxy(arg0Value)) {
                     property__ = s0_.objectLibrary_.getProperty(arg0Value, arg1Value);
                     if (property__ != null) {
                        break;
                     }
                  }

                  s0_ = s0_.next_;
                  count0_++;
               }
            }

            if (s0_ == null && !JSGuards.isJSProxy(arg0Value)) {
               DynamicObjectLibrary objectLibrary__ = super.insert(DYNAMIC_OBJECT_LIBRARY_.create(arg0Value));
               property__ = objectLibrary__.getProperty(arg0Value, arg1Value);
               if (property__ != null && count0_ < 2) {
                  s0_ = super.insert(new KeyInfoNodeGen.CachedOwnPropertyData(this.cachedOwnProperty_cache));
                  s0_.objectLibrary_ = s0_.insertAccessor(objectLibrary__);
                  s0_.isCallable_ = s0_.insertAccessor(IsCallableNode.create());
                  s0_.proxyBranch_ = BranchProfile.create();
                  s0_.fromJavaStringNode_ = s0_.insertAccessor(TruffleString.FromJavaStringNode.create());
                  VarHandle.storeStoreFence();
                  this.cachedOwnProperty_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }
            }

            if (s0_ != null) {
               lock.unlock();
               hasLock = false;
               return KeyInfoNode.cachedOwnProperty(
                  arg0Value, arg1Value, arg2Value, s0_.objectLibrary_, property__, s0_.isCallable_, s0_.proxyBranch_, s0_.fromJavaStringNode_
               );
            }
         }

         KeyInfoNodeGen.MemberData s1_ = super.insert(new KeyInfoNodeGen.MemberData());
         s1_.getPrototype_ = s1_.insertAccessor(GetPrototypeNode.create());
         s1_.isCallable_ = s1_.insertAccessor(IsCallableNode.create());
         s1_.isExtensible_ = s1_.insertAccessor(IsExtensibleNode.create());
         s1_.fromJavaStringNode_ = s1_.insertAccessor(TruffleString.FromJavaStringNode.create());
         VarHandle.storeStoreFence();
         this.member_cache = s1_;
         int var17;
         this.exclude_ = var17 = exclude | 1;
         this.cachedOwnProperty_cache = null;
         state_0 &= -2;
         int var16;
         this.state_0_ = var16 = state_0 | 2;
         lock.unlock();
         hasLock = false;
         return KeyInfoNode.member(arg0Value, arg1Value, arg2Value, s1_.getPrototype_, s1_.isCallable_, s1_.isExtensible_, s1_.fromJavaStringNode_);
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
            KeyInfoNodeGen.CachedOwnPropertyData s0_ = this.cachedOwnProperty_cache;
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
      Object[] s = new Object[]{"cachedOwnProperty", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (KeyInfoNodeGen.CachedOwnPropertyData s0_ = this.cachedOwnProperty_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.objectLibrary_, s0_.isCallable_, s0_.proxyBranch_, s0_.fromJavaStringNode_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"member", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         KeyInfoNodeGen.MemberData s1_ = this.member_cache;
         if (s1_ != null) {
            cached.add(Arrays.asList(s1_.getPrototype_, s1_.isCallable_, s1_.isExtensible_, s1_.fromJavaStringNode_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static KeyInfoNode create() {
      return new KeyInfoNodeGen();
   }

   public static KeyInfoNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(KeyInfoNode.class)
   private static final class CachedOwnPropertyData extends Node {
      @Node.Child
      KeyInfoNodeGen.CachedOwnPropertyData next_;
      @Node.Child
      DynamicObjectLibrary objectLibrary_;
      @Node.Child
      IsCallableNode isCallable_;
      @CompilerDirectives.CompilationFinal
      BranchProfile proxyBranch_;
      @Node.Child
      TruffleString.FromJavaStringNode fromJavaStringNode_;

      CachedOwnPropertyData(KeyInfoNodeGen.CachedOwnPropertyData next_) {
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

   @GeneratedBy(KeyInfoNode.class)
   private static final class MemberData extends Node {
      @Node.Child
      GetPrototypeNode getPrototype_;
      @Node.Child
      IsCallableNode isCallable_;
      @Node.Child
      IsExtensibleNode isExtensible_;
      @Node.Child
      TruffleString.FromJavaStringNode fromJavaStringNode_;

      MemberData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(KeyInfoNode.class)
   @DenyReplace
   private static final class Uncached extends KeyInfoNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public boolean execute(JSDynamicObject arg0Value, String arg1Value, int arg2Value) {
         return KeyInfoNode.member(
            arg0Value,
            arg1Value,
            arg2Value,
            GetPrototypeNodeGen.getUncached(),
            IsCallableNodeGen.getUncached(),
            IsExtensibleNodeGen.getUncached(),
            TruffleString.FromJavaStringNode.getUncached()
         );
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MEGAMORPHIC;
      }

      @Override
      public boolean isAdoptable() {
         return false;
      }
   }
}
