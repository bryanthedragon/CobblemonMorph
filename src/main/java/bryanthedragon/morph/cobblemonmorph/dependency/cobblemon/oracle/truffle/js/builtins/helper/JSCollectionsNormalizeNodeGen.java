package com.oracle.truffle.js.builtins.helper;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSCollectionsNormalizeNode.class)
public final class JSCollectionsNormalizeNodeGen extends JSCollectionsNormalizeNode implements Introspection.Provider {
   private static final JSCollectionsNormalizeNodeGen.Uncached UNCACHED = new JSCollectionsNormalizeNodeGen.Uncached();
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSCollectionsNormalizeNodeGen.ForeignObject0Data foreignObject0_cache;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile foreignObject1_primitiveProfile_;
   @Node.Child
   private JSCollectionsNormalizeNode foreignObject1_nestedNormalizeNode_;

   private JSCollectionsNormalizeNodeGen() {
   }

   @ExplodeLoop
   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
         int arg0Value_ = (Integer)arg0Value;
         return this.doInt(arg0Value_);
      } else if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 7680) >>> 9, arg0Value)) {
         double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 7680) >>> 9, arg0Value);
         return this.doDouble(arg0Value_);
      } else if ((state_0 & 4) != 0 && arg0Value instanceof TruffleString) {
         TruffleString arg0Value_ = (TruffleString)arg0Value;
         return this.doString(arg0Value_);
      } else if ((state_0 & 8) != 0 && arg0Value instanceof Boolean) {
         boolean arg0Value_ = (Boolean)arg0Value;
         return this.doBoolean(arg0Value_);
      } else if ((state_0 & 16) != 0 && arg0Value instanceof JSDynamicObject) {
         JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
         return this.doDynamicObject(arg0Value_);
      } else if ((state_0 & 32) != 0 && arg0Value instanceof Symbol) {
         Symbol arg0Value_ = (Symbol)arg0Value;
         return this.doSymbol(arg0Value_);
      } else if ((state_0 & 64) != 0 && arg0Value instanceof BigInt) {
         BigInt arg0Value_ = (BigInt)arg0Value;
         return this.doBigInt(arg0Value_);
      } else {
         if ((state_0 & 384) != 0) {
            if ((state_0 & 128) != 0) {
               for (JSCollectionsNormalizeNodeGen.ForeignObject0Data s7_ = this.foreignObject0_cache; s7_ != null; s7_ = s7_.next_) {
                  if (s7_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                     return this.doForeignObject(arg0Value, s7_.interop_, s7_.primitiveProfile_, s7_.nestedNormalizeNode_);
                  }
               }
            }

            if ((state_0 & 256) != 0 && JSGuards.isForeignObject(arg0Value)) {
               return this.foreignObject1Boundary(state_0, arg0Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object foreignObject1Boundary(int state_0, Object arg0Value) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      Object var6;
      try {
         InteropLibrary foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
         var6 = this.doForeignObject(arg0Value, foreignObject1_interop__, this.foreignObject1_primitiveProfile_, this.foreignObject1_nestedNormalizeNode_);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   private Object executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            int var27;
            this.state_0_ = var27 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doInt(arg0Value_);
         } else {
            int doubleCast0;
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
               double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
               state_0 |= doubleCast0 << 9;
               int var26;
               this.state_0_ = var26 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doDouble(arg0Value_);
            } else if (arg0Value instanceof TruffleString) {
               TruffleString arg0Value_ = (TruffleString)arg0Value;
               int var24;
               this.state_0_ = var24 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doString(arg0Value_);
            } else if (arg0Value instanceof Boolean) {
               boolean arg0Value_ = (Boolean)arg0Value;
               int var23;
               this.state_0_ = var23 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doBoolean(arg0Value_);
            } else if (arg0Value instanceof JSDynamicObject) {
               JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
               int var22;
               this.state_0_ = var22 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.doDynamicObject(arg0Value_);
            } else if (arg0Value instanceof Symbol) {
               Symbol arg0Value_ = (Symbol)arg0Value;
               int var21;
               this.state_0_ = var21 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.doSymbol(arg0Value_);
            } else if (arg0Value instanceof BigInt) {
               BigInt arg0Value_ = (BigInt)arg0Value;
               int var20;
               this.state_0_ = var20 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return this.doBigInt(arg0Value_);
            } else {
               if (exclude == 0) {
                  doubleCast0 = 0;
                  JSCollectionsNormalizeNodeGen.ForeignObject0Data s7_ = this.foreignObject0_cache;
                  if ((state_0 & 128) != 0) {
                     while (s7_ != null && (!s7_.interop_.accepts(arg0Value) || !JSGuards.isForeignObject(arg0Value))) {
                        s7_ = s7_.next_;
                        doubleCast0++;
                     }
                  }

                  if (s7_ == null && JSGuards.isForeignObject(arg0Value) && doubleCast0 < 5) {
                     s7_ = super.insert(new JSCollectionsNormalizeNodeGen.ForeignObject0Data(this.foreignObject0_cache));
                     s7_.interop_ = s7_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                     s7_.primitiveProfile_ = ConditionProfile.createBinaryProfile();
                     s7_.nestedNormalizeNode_ = s7_.insertAccessor(JSCollectionsNormalizeNode.create());
                     VarHandle.storeStoreFence();
                     this.foreignObject0_cache = s7_;
                     this.state_0_ = state_0 |= 128;
                  }

                  if (s7_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignObject(arg0Value, s7_.interop_, s7_.primitiveProfile_, s7_.nestedNormalizeNode_);
                  }
               }

               InteropLibrary foreignObject1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(arg0Value)) {
                     InteropLibrary var31 = INTEROP_LIBRARY_.getUncached(arg0Value);
                     this.foreignObject1_primitiveProfile_ = ConditionProfile.createBinaryProfile();
                     this.foreignObject1_nestedNormalizeNode_ = super.insert(JSCollectionsNormalizeNode.create());
                     int var28;
                     this.exclude_ = var28 = exclude | 1;
                     this.foreignObject0_cache = null;
                     state_0 &= -129;
                     int var19;
                     this.state_0_ = var19 = state_0 | 256;
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignObject(arg0Value, var31, this.foreignObject1_primitiveProfile_, this.foreignObject1_nestedNormalizeNode_);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
      if ((state_0 & 511) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & 511 & (state_0 & 511) - 1) == 0) {
            JSCollectionsNormalizeNodeGen.ForeignObject0Data s7_ = this.foreignObject0_cache;
            if (s7_ == null || s7_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[10];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doInt", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doDouble", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doString", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doDynamicObject", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doSymbol", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSCollectionsNormalizeNodeGen.ForeignObject0Data s7_ = this.foreignObject0_cache; s7_ != null; s7_ = s7_.next_) {
            cached.add(Arrays.asList(s7_.interop_, s7_.primitiveProfile_, s7_.nestedNormalizeNode_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.foreignObject1_primitiveProfile_, this.foreignObject1_nestedNormalizeNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      return Introspection.Provider.create(data);
   }

   public static JSCollectionsNormalizeNode create() {
      return new JSCollectionsNormalizeNodeGen();
   }

   public static JSCollectionsNormalizeNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(JSCollectionsNormalizeNode.class)
   private static final class ForeignObject0Data extends Node {
      @Node.Child
      JSCollectionsNormalizeNodeGen.ForeignObject0Data next_;
      @Node.Child
      InteropLibrary interop_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile primitiveProfile_;
      @Node.Child
      JSCollectionsNormalizeNode nestedNormalizeNode_;

      ForeignObject0Data(JSCollectionsNormalizeNodeGen.ForeignObject0Data next_) {
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

   @GeneratedBy(JSCollectionsNormalizeNode.class)
   @DenyReplace
   private static final class Uncached extends JSCollectionsNormalizeNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public Object execute(Object arg0Value) {
         if (arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return this.doInt(arg0Value_);
         } else if (JSTypesGen.isImplicitDouble(arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble(arg0Value);
            return this.doDouble(arg0Value_);
         } else if (arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return this.doString(arg0Value_);
         } else if (arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return this.doBoolean(arg0Value_);
         } else if (arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            return this.doDynamicObject(arg0Value_);
         } else if (arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return this.doSymbol(arg0Value_);
         } else if (arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return this.doBigInt(arg0Value_);
         } else if (JSGuards.isForeignObject(arg0Value)) {
            return this.doForeignObject(
               arg0Value,
               JSCollectionsNormalizeNodeGen.INTEROP_LIBRARY_.getUncached(arg0Value),
               ConditionProfile.getUncached(),
               JSCollectionsNormalizeNodeGen.getUncached()
            );
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
         }
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
