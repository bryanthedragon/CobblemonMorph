package com.oracle.truffle.js.nodes.array;

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
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSGetLengthNode.class)
public final class JSGetLengthNodeGen extends JSGetLengthNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private ArrayLengthNode.ArrayLengthReadNode getArrayLengthInt_arrayLengthReadNode_;
   @Node.Child
   private ArrayLengthNode.ArrayLengthReadNode getArrayLength_arrayLengthReadNode_;
   @Node.Child
   private PropertyGetNode getNonArrayLength_getLengthPropertyNode_;
   @Node.Child
   private JSGetLengthNodeGen.GetLengthForeign0Data getLengthForeign0_cache;
   @Node.Child
   private ImportValueNode getLengthForeign1_importValueNode_;

   private JSGetLengthNodeGen(JSContext context) {
      super(context);
   }

   @ExplodeLoop
   @Override
   public Object execute(Object arg0Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 3) != 0 && arg0Value instanceof JSArrayObject) {
         JSArrayObject arg0Value_ = (JSArrayObject)arg0Value;
         if ((state_0 & 1) != 0) {
            try {
               return this.getArrayLengthInt(arg0Value_, this.getArrayLengthInt_arrayLengthReadNode_);
            } catch (UnexpectedResultException var10) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 1;
                  this.state_0_ &= -2;
               } finally {
                  lock.unlock();
               }

               return var10.getResult();
            }
         }

         if ((state_0 & 2) != 0) {
            return this.getArrayLength(arg0Value_, this.getArrayLength_arrayLengthReadNode_);
         }
      }

      if ((state_0 & 4) != 0 && arg0Value instanceof JSDynamicObject) {
         JSDynamicObject arg0Value_x = (JSDynamicObject)arg0Value;
         if (!JSGuards.isJSArray(arg0Value_x)) {
            return this.getNonArrayLength(arg0Value_x, this.getNonArrayLength_getLengthPropertyNode_);
         }
      }

      if ((state_0 & 24) != 0) {
         if ((state_0 & 8) != 0) {
            for (JSGetLengthNodeGen.GetLengthForeign0Data s3_ = this.getLengthForeign0_cache; s3_ != null; s3_ = s3_.next_) {
               if (s3_.interop_.accepts(arg0Value) && !JSGuards.isJSDynamicObject(arg0Value)) {
                  return this.getLengthForeign(arg0Value, s3_.interop_, s3_.importValueNode_);
               }
            }
         }

         if ((state_0 & 16) != 0 && !JSGuards.isJSDynamicObject(arg0Value)) {
            return this.getLengthForeign1Boundary(state_0, arg0Value);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value);
   }

   @CompilerDirectives.TruffleBoundary
   private Object getLengthForeign1Boundary(int state_0, Object arg0Value) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      Double var6;
      try {
         InteropLibrary getLengthForeign1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
         var6 = this.getLengthForeign(arg0Value, getLengthForeign1_interop__, this.getLengthForeign1_importValueNode_);
      } finally {
         encapsulating_.set(prev_);
      }

      return var6;
   }

   private Object executeAndSpecialize(Object arg0Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Node prev_;
      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (!(arg0Value instanceof JSArrayObject)) {
            if (arg0Value instanceof JSDynamicObject) {
               JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
               if (!JSGuards.isJSArray(arg0Value_)) {
                  this.getNonArrayLength_getLengthPropertyNode_ = super.insert(this.createLengthProperty());
                  int var33;
                  this.state_0_ = var33 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.getNonArrayLength(arg0Value_, this.getNonArrayLength_getLengthPropertyNode_);
               }
            }

            if ((exclude & 2) == 0) {
               int count3_ = 0;
               JSGetLengthNodeGen.GetLengthForeign0Data s3_ = this.getLengthForeign0_cache;
               if ((state_0 & 8) != 0) {
                  while (s3_ != null && (!s3_.interop_.accepts(arg0Value) || JSGuards.isJSDynamicObject(arg0Value))) {
                     s3_ = s3_.next_;
                     count3_++;
                  }
               }

               if (s3_ == null && !JSGuards.isJSDynamicObject(arg0Value) && count3_ < 3) {
                  s3_ = super.insert(new JSGetLengthNodeGen.GetLengthForeign0Data(this.getLengthForeign0_cache));
                  s3_.interop_ = s3_.insertAccessor(INTEROP_LIBRARY_.create(arg0Value));
                  s3_.importValueNode_ = s3_.insertAccessor(ImportValueNode.create());
                  VarHandle.storeStoreFence();
                  this.getLengthForeign0_cache = s3_;
                  this.state_0_ = state_0 |= 8;
               }

               if (s3_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.getLengthForeign(arg0Value, s3_.interop_, s3_.importValueNode_);
               }
            }

            InteropLibrary getLengthForeign1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            prev_ = encapsulating_.set(this);

            try {
               if (!JSGuards.isJSDynamicObject(arg0Value)) {
                  getLengthForeign1_interop__ = INTEROP_LIBRARY_.getUncached(arg0Value);
                  this.getLengthForeign1_importValueNode_ = super.insert(ImportValueNode.create());
                  int var35;
                  this.exclude_ = var35 = exclude | 2;
                  this.getLengthForeign0_cache = null;
                  state_0 &= -9;
                  int var32;
                  this.state_0_ = var32 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return this.getLengthForeign(arg0Value, getLengthForeign1_interop__, this.getLengthForeign1_importValueNode_);
               }
            } finally {
               encapsulating_.set(prev_);
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
         }

         JSArrayObject arg0Value_ = (JSArrayObject)arg0Value;
         if ((exclude & 1) != 0) {
            this.getArrayLength_arrayLengthReadNode_ = super.insert(ArrayLengthNode.ArrayLengthReadNode.create());
            int var34;
            this.exclude_ = var34 = exclude | 1;
            state_0 &= -2;
            int var30;
            this.state_0_ = var30 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.getArrayLength(arg0Value_, this.getArrayLength_arrayLengthReadNode_);
         }

         this.getArrayLengthInt_arrayLengthReadNode_ = super.insert(ArrayLengthNode.ArrayLengthReadNode.create());
         int var28;
         this.state_0_ = var28 = state_0 | 1;

         try {
            lock.unlock();
            hasLock = false;
            return this.getArrayLengthInt(arg0Value_, this.getArrayLengthInt_arrayLengthReadNode_);
         } catch (UnexpectedResultException var26) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            lock.lock();

            try {
               this.exclude_ |= 1;
               this.state_0_ &= -2;
            } finally {
               lock.unlock();
            }

            prev_ = (Node)var26.getResult();
         }
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return prev_;
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if (state_0 == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & state_0 - 1) == 0) {
            JSGetLengthNodeGen.GetLengthForeign0Data s3_ = this.getLengthForeign0_cache;
            if (s3_ == null || s3_.next_ == null) {
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
      Object[] s = new Object[]{"getArrayLengthInt", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.getArrayLengthInt_arrayLengthReadNode_));
         s[2] = cached;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"getArrayLength", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.getArrayLength_arrayLengthReadNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"getNonArrayLength", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.getNonArrayLength_getLengthPropertyNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"getLengthForeign", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSGetLengthNodeGen.GetLengthForeign0Data s3_ = this.getLengthForeign0_cache; s3_ != null; s3_ = s3_.next_) {
            cached.add(Arrays.asList(s3_.interop_, s3_.importValueNode_));
         }

         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"getLengthForeign", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.getLengthForeign1_importValueNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      return Introspection.Provider.create(data);
   }

   public static JSGetLengthNode create(JSContext context) {
      return new JSGetLengthNodeGen(context);
   }

   @GeneratedBy(JSGetLengthNode.class)
   private static final class GetLengthForeign0Data extends Node {
      @Node.Child
      JSGetLengthNodeGen.GetLengthForeign0Data next_;
      @Node.Child
      InteropLibrary interop_;
      @Node.Child
      ImportValueNode importValueNode_;

      GetLengthForeign0Data(JSGetLengthNodeGen.GetLengthForeign0Data next_) {
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
