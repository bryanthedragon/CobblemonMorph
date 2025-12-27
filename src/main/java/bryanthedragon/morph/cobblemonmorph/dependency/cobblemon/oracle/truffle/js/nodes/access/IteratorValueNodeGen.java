package com.oracle.truffle.js.nodes.access;

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
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(IteratorValueNode.class)
public final class IteratorValueNodeGen extends IteratorValueNode implements Introspection.Provider {
   private static final IteratorValueNodeGen.Uncached UNCACHED = new IteratorValueNodeGen.Uncached();
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private PropertyGetNode iteratorNext_getValueNode_;
   @Node.Child
   private IteratorValueNodeGen.ForeignObject0Data foreignObject0_cache;
   @Node.Child
   private ImportValueNode foreignObject1_importValueNode_;

   private IteratorValueNodeGen() {
   }

   @ExplodeLoop
   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && arg0Value instanceof JSDynamicObject) {
         JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
         return this.doIteratorNext(arg0Value_, this.iteratorNext_getValueNode_);
      } else {
         if ((state_0 & 6) != 0) {
            if ((state_0 & 2) != 0) {
               for (IteratorValueNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache; s1_ != null; s1_ = s1_.next_) {
                  if (s1_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                     return this.doForeignObject(arg0Value, s1_.interop_, s1_.importValueNode_);
                  }
               }
            }

            if ((state_0 & 4) != 0 && JSGuards.isForeignObject(arg0Value)) {
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
         var6 = this.doForeignObject(arg0Value, foreignObject1_interop__, this.foreignObject1_importValueNode_);
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
         if (arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            this.iteratorNext_getValueNode_ = super.insert(this.createGetValueNode());
            int var20;
            this.state_0_ = var20 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            return this.doIteratorNext(arg0Value_, this.iteratorNext_getValueNode_);
         } else {
            if (exclude == 0) {
               int count1_ = 0;
               IteratorValueNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache;
               if ((state_0 & 2) != 0) {
                  while (s1_ != null && (!s1_.interop_.accepts(arg0Value) || !JSGuards.isForeignObject(arg0Value))) {
                     s1_ = s1_.next_;
                     count1_++;
                  }
               }

               if (s1_ == null && JSGuards.isForeignObject(arg0Value) && count1_ < 5) {
                  s1_ = super.insert(new IteratorValueNodeGen.ForeignObject0Data(this.foreignObject0_cache));
                  s1_.interop_ = s1_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                  s1_.importValueNode_ = s1_.insertAccessor(ImportValueNode.create());
                  VarHandle.storeStoreFence();
                  this.foreignObject0_cache = s1_;
                  this.state_0_ = state_0 |= 2;
               }

               if (s1_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doForeignObject(arg0Value, s1_.interop_, s1_.importValueNode_);
               }
            }

            InteropLibrary foreignObject1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               if (JSGuards.isForeignObject(arg0Value)) {
                  foreignObject1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
                  this.foreignObject1_importValueNode_ = super.insert(ImportValueNode.create());
                  int var21;
                  this.exclude_ = var21 = exclude | 1;
                  this.foreignObject0_cache = null;
                  state_0 &= -3;
                  int var19;
                  this.state_0_ = var19 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.doForeignObject(arg0Value, foreignObject1_interop__, this.foreignObject1_importValueNode_);
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
            IteratorValueNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache;
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
      Object[] s = new Object[]{"doIteratorNext", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.iteratorNext_getValueNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (IteratorValueNodeGen.ForeignObject0Data s1_ = this.foreignObject0_cache; s1_ != null; s1_ = s1_.next_) {
            cached.add(Arrays.asList(s1_.interop_, s1_.importValueNode_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.foreignObject1_importValueNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      return Introspection.Provider.create(data);
   }

   public static IteratorValueNode create() {
      return new IteratorValueNodeGen();
   }

   public static IteratorValueNode getUncached() {
      return UNCACHED;
   }

   @GeneratedBy(IteratorValueNode.class)
   private static final class ForeignObject0Data extends Node {
      @Node.Child
      IteratorValueNodeGen.ForeignObject0Data next_;
      @Node.Child
      InteropLibrary interop_;
      @Node.Child
      ImportValueNode importValueNode_;

      ForeignObject0Data(IteratorValueNodeGen.ForeignObject0Data next_) {
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

   @GeneratedBy(IteratorValueNode.class)
   @DenyReplace
   private static final class Uncached extends IteratorValueNode {
      @CompilerDirectives.TruffleBoundary
      @Override
      public Object execute(Object arg0Value) {
         if (arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            return this.doIteratorNext(arg0Value_, IteratorValueNode.uncachedGetValueNode());
         } else if (JSGuards.isForeignObject(arg0Value)) {
            return this.doForeignObject(arg0Value, IteratorValueNodeGen.INTEROP_LIBRARY_.getUncached(arg0Value), ImportValueNode.getUncached());
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
