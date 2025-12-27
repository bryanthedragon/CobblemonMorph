package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(PrivateFieldGetNode.class)
public final class PrivateFieldGetNodeGen extends PrivateFieldGetNode implements Introspection.Provider {
   private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private PrivateFieldGetNodeGen.Field0Data field0_cache;
   @CompilerDirectives.CompilationFinal
   private BranchProfile field1_errorBranch_;
   @Node.Child
   private JSFunctionCallNode accessor_callNode_;
   @CompilerDirectives.CompilationFinal
   private BranchProfile accessor_errorBranch_;

   private PrivateFieldGetNodeGen(JavaScriptNode targetNode, JavaScriptNode keyNode, JSContext context) {
      super(targetNode, keyNode, context);
   }

   @ExplodeLoop
   @Override
   public Object executeWithTarget(VirtualFrame frameValue, Object targetNodeValue) {
      int state_0 = this.state_0_;
      Object keyNodeValue_ = super.keyNode.execute(frameValue);
      if ((state_0 & 15) != 0 && targetNodeValue instanceof JSObject) {
         JSObject targetNodeValue_ = (JSObject)targetNodeValue;
         if ((state_0 & 3) != 0 && keyNodeValue_ instanceof HiddenKey) {
            HiddenKey keyNodeValue__ = (HiddenKey)keyNodeValue_;
            if ((state_0 & 1) != 0) {
               for (PrivateFieldGetNodeGen.Field0Data s0_ = this.field0_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.access_.accepts(targetNodeValue_)) {
                     return this.doField(targetNodeValue_, keyNodeValue__, s0_.access_, s0_.errorBranch_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return this.field1Boundary(state_0, targetNodeValue_, keyNodeValue__);
            }
         }

         if ((state_0 & 4) != 0 && keyNodeValue_ instanceof JSFunctionObject) {
            JSFunctionObject keyNodeValue__x = (JSFunctionObject)keyNodeValue_;
            return this.doMethod(targetNodeValue_, keyNodeValue__x);
         }

         if ((state_0 & 8) != 0 && keyNodeValue_ instanceof Accessor) {
            Accessor keyNodeValue__x = (Accessor)keyNodeValue_;
            return this.doAccessor(targetNodeValue_, keyNodeValue__x, this.accessor_callNode_, this.accessor_errorBranch_);
         }
      }

      if ((state_0 & 16) != 0 && fallbackGuard_(state_0, targetNodeValue, keyNodeValue_)) {
         return this.missing(targetNodeValue, keyNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(targetNodeValue, keyNodeValue_);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object field1Boundary(int state_0, JSObject targetNodeValue_, HiddenKey keyNodeValue__) {
      DynamicObjectLibrary field1_access__ = DYNAMIC_OBJECT_LIBRARY_.getUncached(targetNodeValue_);
      return this.doField(targetNodeValue_, keyNodeValue__, field1_access__, this.field1_errorBranch_);
   }

   @ExplodeLoop
   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object targetNodeValue_ = super.targetNode.execute(frameValue);
      Object keyNodeValue_ = super.keyNode.execute(frameValue);
      if ((state_0 & 15) != 0 && targetNodeValue_ instanceof JSObject) {
         JSObject targetNodeValue__ = (JSObject)targetNodeValue_;
         if ((state_0 & 3) != 0 && keyNodeValue_ instanceof HiddenKey) {
            HiddenKey keyNodeValue__ = (HiddenKey)keyNodeValue_;
            if ((state_0 & 1) != 0) {
               for (PrivateFieldGetNodeGen.Field0Data s0_ = this.field0_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.access_.accepts(targetNodeValue__)) {
                     return this.doField(targetNodeValue__, keyNodeValue__, s0_.access_, s0_.errorBranch_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return this.field1Boundary0(state_0, targetNodeValue__, keyNodeValue__);
            }
         }

         if ((state_0 & 4) != 0 && keyNodeValue_ instanceof JSFunctionObject) {
            JSFunctionObject keyNodeValue__x = (JSFunctionObject)keyNodeValue_;
            return this.doMethod(targetNodeValue__, keyNodeValue__x);
         }

         if ((state_0 & 8) != 0 && keyNodeValue_ instanceof Accessor) {
            Accessor keyNodeValue__x = (Accessor)keyNodeValue_;
            return this.doAccessor(targetNodeValue__, keyNodeValue__x, this.accessor_callNode_, this.accessor_errorBranch_);
         }
      }

      if ((state_0 & 16) != 0 && fallbackGuard_(state_0, targetNodeValue_, keyNodeValue_)) {
         return this.missing(targetNodeValue_, keyNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(targetNodeValue_, keyNodeValue_);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object field1Boundary0(int state_0, JSObject targetNodeValue__, HiddenKey keyNodeValue__) {
      DynamicObjectLibrary field1_access__ = DYNAMIC_OBJECT_LIBRARY_.getUncached(targetNodeValue__);
      return this.doField(targetNodeValue__, keyNodeValue__, field1_access__, this.field1_errorBranch_);
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.execute(frameValue);
   }

   private Object executeAndSpecialize(Object targetNodeValue, Object keyNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (targetNodeValue instanceof JSObject) {
            JSObject targetNodeValue_ = (JSObject)targetNodeValue;
            if (keyNodeValue instanceof HiddenKey) {
               HiddenKey keyNodeValue_ = (HiddenKey)keyNodeValue;
               if (exclude == 0) {
                  int count0_ = 0;
                  PrivateFieldGetNodeGen.Field0Data s0_ = this.field0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && !s0_.access_.accepts(targetNodeValue_)) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && count0_ < 3) {
                     s0_ = super.insert(new PrivateFieldGetNodeGen.Field0Data(this.field0_cache));
                     s0_.access_ = s0_.insertAccessor(DYNAMIC_OBJECT_LIBRARY_.create(targetNodeValue_));
                     s0_.errorBranch_ = BranchProfile.create();
                     VarHandle.storeStoreFence();
                     this.field0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doField(targetNodeValue_, keyNodeValue_, s0_.access_, s0_.errorBranch_);
                  }
               }

               DynamicObjectLibrary field1_access__ = null;
               field1_access__ = DYNAMIC_OBJECT_LIBRARY_.getUncached(targetNodeValue_);
               this.field1_errorBranch_ = BranchProfile.create();
               int var20;
               this.exclude_ = var20 = exclude | 1;
               this.field0_cache = null;
               state_0 &= -2;
               int var19;
               this.state_0_ = var19 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doField(targetNodeValue_, keyNodeValue_, field1_access__, this.field1_errorBranch_);
            }

            if (keyNodeValue instanceof JSFunctionObject) {
               JSFunctionObject keyNodeValue_ = (JSFunctionObject)keyNodeValue;
               int var17;
               this.state_0_ = var17 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doMethod(targetNodeValue_, keyNodeValue_);
            }

            if (keyNodeValue instanceof Accessor) {
               Accessor keyNodeValue_ = (Accessor)keyNodeValue;
               this.accessor_callNode_ = super.insert(JSFunctionCallNode.createCall());
               this.accessor_errorBranch_ = BranchProfile.create();
               int var16;
               this.state_0_ = var16 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doAccessor(targetNodeValue_, keyNodeValue_, this.accessor_callNode_, this.accessor_errorBranch_);
            }
         }

         int var15;
         this.state_0_ = var15 = state_0 | 16;
         lock.unlock();
         hasLock = false;
         return this.missing(targetNodeValue, keyNodeValue);
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
            PrivateFieldGetNodeGen.Field0Data s0_ = this.field0_cache;
            if (s0_ == null || s0_.next_ == null) {
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
      Object[] s = new Object[]{"doField", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (PrivateFieldGetNodeGen.Field0Data s0_ = this.field0_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.access_, s0_.errorBranch_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doField", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.field1_errorBranch_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doMethod", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doAccessor", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.accessor_callNode_, this.accessor_errorBranch_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"missing", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      return Introspection.Provider.create(data);
   }

   private static boolean fallbackGuard_(int state_0, Object targetNodeValue, Object keyNodeValue) {
      if (targetNodeValue instanceof JSObject) {
         if ((state_0 & 2) == 0 && keyNodeValue instanceof HiddenKey) {
            return false;
         }

         if ((state_0 & 4) == 0 && keyNodeValue instanceof JSFunctionObject) {
            return false;
         }

         if ((state_0 & 8) == 0 && keyNodeValue instanceof Accessor) {
            return false;
         }
      }

      return true;
   }

   public static PrivateFieldGetNode create(JavaScriptNode targetNode, JavaScriptNode keyNode, JSContext context) {
      return new PrivateFieldGetNodeGen(targetNode, keyNode, context);
   }

   @GeneratedBy(PrivateFieldGetNode.class)
   private static final class Field0Data extends Node {
      @Node.Child
      PrivateFieldGetNodeGen.Field0Data next_;
      @Node.Child
      DynamicObjectLibrary access_;
      @CompilerDirectives.CompilationFinal
      BranchProfile errorBranch_;

      Field0Data(PrivateFieldGetNodeGen.Field0Data next_) {
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
