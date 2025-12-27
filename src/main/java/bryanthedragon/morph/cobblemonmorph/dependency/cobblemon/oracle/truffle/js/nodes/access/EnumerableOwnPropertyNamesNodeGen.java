package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.builtins.helper.ListGetNode;
import com.oracle.truffle.js.builtins.helper.ListSizeNode;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import com.oracle.truffle.js.runtime.util.UnmodifiableArrayList;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(EnumerableOwnPropertyNamesNode.class)
public final class EnumerableOwnPropertyNamesNodeGen extends EnumerableOwnPropertyNamesNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesData enumerableOwnPropertyNames_cache;
   @Node.Child
   private EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesForeign0Data enumerableOwnPropertyNamesForeign0_cache;
   @Node.Child
   private EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesForeign1Data enumerableOwnPropertyNamesForeign1_cache;

   private EnumerableOwnPropertyNamesNodeGen(JSContext context, boolean keys, boolean values) {
      super(context, keys, values);
   }

   @ExplodeLoop
   @Override
   public UnmodifiableArrayList<? extends Object> execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof JSDynamicObject) {
         JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
         EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesData s0_ = this.enumerableOwnPropertyNames_cache;
         if (s0_ != null) {
            return this.enumerableOwnPropertyNames(arg0Value_, s0_.jsclassProfile_, s0_.listSize_, s0_.listGet_, s0_.hasOnlyShapeProperties_);
         }
      }

      if ((state_0 & 6) != 0) {
         if ((state_0 & 2) != 0) {
            for (EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesForeign0Data s1_ = this.enumerableOwnPropertyNamesForeign0_cache;
               s1_ != null;
               s1_ = s1_.next_
            ) {
               if (s1_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                  return this.enumerableOwnPropertyNamesForeign(arg0Value, s1_.interop_, s1_.members_, s1_.asString_, s1_.importValue_, s1_.errorBranch_);
               }
            }
         }

         if ((state_0 & 4) != 0) {
            EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesForeign1Data s2_ = this.enumerableOwnPropertyNamesForeign1_cache;
            if (s2_ != null && JSGuards.isForeignObject(arg0Value)) {
               return this.enumerableOwnPropertyNamesForeign1Boundary(state_0, s2_, arg0Value);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value);
   }

   @CompilerDirectives.TruffleBoundary
   private UnmodifiableArrayList<? extends Object> enumerableOwnPropertyNamesForeign1Boundary(
      int state_0, EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesForeign1Data s2_, Object arg0Value
   ) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      UnmodifiableArrayList var7;
      try {
         InteropLibrary interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
         var7 = this.enumerableOwnPropertyNamesForeign(arg0Value, interop__, s2_.members_, s2_.asString_, s2_.importValue_, s2_.errorBranch_);
      } finally {
         encapsulating_.set(prev_);
      }

      return var7;
   }

   private UnmodifiableArrayList<? extends Object> executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesData s0_ = super.insert(
               new EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesData()
            );
            s0_.jsclassProfile_ = JSClassProfile.create();
            s0_.listSize_ = s0_.insertAccessor(ListSizeNode.create());
            s0_.listGet_ = s0_.insertAccessor(ListGetNode.create());
            s0_.hasOnlyShapeProperties_ = s0_.insertAccessor(HasOnlyShapePropertiesNode.create());
            VarHandle.storeStoreFence();
            this.enumerableOwnPropertyNames_cache = s0_;
            int var21;
            this.state_0_ = var21 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.enumerableOwnPropertyNames(arg0Value_, s0_.jsclassProfile_, s0_.listSize_, s0_.listGet_, s0_.hasOnlyShapeProperties_);
         } else {
            if (exclude == 0) {
               int count1_ = 0;
               EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesForeign0Data s1_ = this.enumerableOwnPropertyNamesForeign0_cache;
               if ((state_0 & 2) != 0) {
                  while (s1_ != null && (!s1_.interop_.accepts(arg0Value) || !JSGuards.isForeignObject(arg0Value))) {
                     s1_ = s1_.next_;
                     count1_++;
                  }
               }

               if (s1_ == null && JSGuards.isForeignObject(arg0Value) && count1_ < 5) {
                  s1_ = super.insert(
                     new EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesForeign0Data(this.enumerableOwnPropertyNamesForeign0_cache)
                  );
                  s1_.interop_ = s1_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                  s1_.members_ = s1_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                  s1_.asString_ = s1_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                  s1_.importValue_ = s1_.insertAccessor(ImportValueNode.create());
                  s1_.errorBranch_ = BranchProfile.create();
                  VarHandle.storeStoreFence();
                  this.enumerableOwnPropertyNamesForeign0_cache = s1_;
                  this.state_0_ = state_0 |= 2;
               }

               if (s1_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.enumerableOwnPropertyNamesForeign(arg0Value, s1_.interop_, s1_.members_, s1_.asString_, s1_.importValue_, s1_.errorBranch_);
               }
            }

            InteropLibrary interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               if (JSGuards.isForeignObject(arg0Value)) {
                  EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesForeign1Data s2_ = super.insert(
                     new EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesForeign1Data()
                  );
                  interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
                  s2_.members_ = s2_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                  s2_.asString_ = s2_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                  s2_.importValue_ = s2_.insertAccessor(ImportValueNode.create());
                  s2_.errorBranch_ = BranchProfile.create();
                  VarHandle.storeStoreFence();
                  this.enumerableOwnPropertyNamesForeign1_cache = s2_;
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.enumerableOwnPropertyNamesForeign0_cache = null;
                  state_0 &= -3;
                  int var20;
                  this.state_0_ = var20 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.enumerableOwnPropertyNamesForeign(arg0Value, interop__, s2_.members_, s2_.asString_, s2_.importValue_, s2_.errorBranch_);
               }
            } finally {
               encapsulating_.set(prev_);
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
            EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesForeign0Data s1_ = this.enumerableOwnPropertyNamesForeign0_cache;
            if (s1_ == null || s1_.next_ == null) {
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
      Object[] s = new Object[]{"enumerableOwnPropertyNames", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesData s0_ = this.enumerableOwnPropertyNames_cache;
         if (s0_ != null) {
            cached.add(Arrays.asList(s0_.jsclassProfile_, s0_.listSize_, s0_.listGet_, s0_.hasOnlyShapeProperties_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"enumerableOwnPropertyNamesForeign", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesForeign0Data s1_ = this.enumerableOwnPropertyNamesForeign0_cache;
            s1_ != null;
            s1_ = s1_.next_
         ) {
            cached.add(Arrays.asList(s1_.interop_, s1_.members_, s1_.asString_, s1_.importValue_, s1_.errorBranch_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"enumerableOwnPropertyNamesForeign", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesForeign1Data s2_ = this.enumerableOwnPropertyNamesForeign1_cache;
         if (s2_ != null) {
            cached.add(Arrays.asList(s2_.members_, s2_.asString_, s2_.importValue_, s2_.errorBranch_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      return Introspection.Provider.create(data);
   }

   public static EnumerableOwnPropertyNamesNode create(JSContext context, boolean keys, boolean values) {
      return new EnumerableOwnPropertyNamesNodeGen(context, keys, values);
   }

   @GeneratedBy(EnumerableOwnPropertyNamesNode.class)
   private static final class EnumerableOwnPropertyNamesData extends Node {
      @CompilerDirectives.CompilationFinal
      JSClassProfile jsclassProfile_;
      @Node.Child
      ListSizeNode listSize_;
      @Node.Child
      ListGetNode listGet_;
      @Node.Child
      HasOnlyShapePropertiesNode hasOnlyShapeProperties_;

      EnumerableOwnPropertyNamesData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(EnumerableOwnPropertyNamesNode.class)
   private static final class EnumerableOwnPropertyNamesForeign0Data extends Node {
      @Node.Child
      EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesForeign0Data next_;
      @Node.Child
      InteropLibrary interop_;
      @Node.Child
      InteropLibrary members_;
      @Node.Child
      InteropLibrary asString_;
      @Node.Child
      ImportValueNode importValue_;
      @CompilerDirectives.CompilationFinal
      BranchProfile errorBranch_;

      EnumerableOwnPropertyNamesForeign0Data(EnumerableOwnPropertyNamesNodeGen.EnumerableOwnPropertyNamesForeign0Data next_) {
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

   @GeneratedBy(EnumerableOwnPropertyNamesNode.class)
   private static final class EnumerableOwnPropertyNamesForeign1Data extends Node {
      @Node.Child
      InteropLibrary members_;
      @Node.Child
      InteropLibrary asString_;
      @Node.Child
      ImportValueNode importValue_;
      @CompilerDirectives.CompilationFinal
      BranchProfile errorBranch_;

      EnumerableOwnPropertyNamesForeign1Data() {
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
