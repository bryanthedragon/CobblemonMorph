package com.oracle.truffle.js.nodes.binary;

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
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(PrivateFieldInNode.class)
public final class PrivateFieldInNodeGen extends PrivateFieldInNode implements Introspection.Provider {
   private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private PrivateFieldInNodeGen.Instance0Data instance0_cache;
   @Node.Child
   private IsObjectNode fallback_isObjectNode_;

   private PrivateFieldInNodeGen(JavaScriptNode left, JavaScriptNode right) {
      super(left, right);
   }

   @ExplodeLoop
   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 7) != 0 && rightNodeValue_ instanceof JSDynamicObject) {
         JSDynamicObject rightNodeValue__ = (JSDynamicObject)rightNodeValue_;
         if ((state_0 & 3) != 0 && leftNodeValue_ instanceof HiddenKey) {
            HiddenKey leftNodeValue__ = (HiddenKey)leftNodeValue_;
            if ((state_0 & 1) != 0) {
               for (PrivateFieldInNodeGen.Instance0Data s0_ = this.instance0_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.access_.accepts(rightNodeValue__) && JSGuards.isJSObject(rightNodeValue__)) {
                     return this.doInstance(leftNodeValue__, rightNodeValue__, s0_.access_);
                  }
               }
            }

            if ((state_0 & 2) != 0 && JSGuards.isJSObject(rightNodeValue__)) {
               return this.instance1Boundary(state_0, leftNodeValue__, rightNodeValue__);
            }
         }

         if ((state_0 & 4) != 0 && leftNodeValue_ instanceof JSDynamicObject) {
            JSDynamicObject leftNodeValue__x = (JSDynamicObject)leftNodeValue_;
            if (JSGuards.isJSObject(rightNodeValue__)) {
               return this.doStatic(leftNodeValue__x, rightNodeValue__);
            }
         }
      }

      if ((state_0 & 8) != 0 && fallbackGuard_(leftNodeValue_, rightNodeValue_)) {
         return this.doFallback(leftNodeValue_, rightNodeValue_, this.fallback_isObjectNode_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private Object instance1Boundary(int state_0, HiddenKey leftNodeValue__, JSDynamicObject rightNodeValue__) {
      DynamicObjectLibrary instance1_access__ = DYNAMIC_OBJECT_LIBRARY_.getUncached(rightNodeValue__);
      return this.doInstance(leftNodeValue__, rightNodeValue__, instance1_access__);
   }

   @ExplodeLoop
   @Override
   public boolean executeBoolean(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 7) != 0 && rightNodeValue_ instanceof JSDynamicObject) {
         JSDynamicObject rightNodeValue__ = (JSDynamicObject)rightNodeValue_;
         if ((state_0 & 3) != 0 && leftNodeValue_ instanceof HiddenKey) {
            HiddenKey leftNodeValue__ = (HiddenKey)leftNodeValue_;
            if ((state_0 & 1) != 0) {
               for (PrivateFieldInNodeGen.Instance0Data s0_ = this.instance0_cache; s0_ != null; s0_ = s0_.next_) {
                  if (s0_.access_.accepts(rightNodeValue__) && JSGuards.isJSObject(rightNodeValue__)) {
                     return this.doInstance(leftNodeValue__, rightNodeValue__, s0_.access_);
                  }
               }
            }

            if ((state_0 & 2) != 0 && JSGuards.isJSObject(rightNodeValue__)) {
               return this.instance1Boundary0(state_0, leftNodeValue__, rightNodeValue__);
            }
         }

         if ((state_0 & 4) != 0 && leftNodeValue_ instanceof JSDynamicObject) {
            JSDynamicObject leftNodeValue__x = (JSDynamicObject)leftNodeValue_;
            if (JSGuards.isJSObject(rightNodeValue__)) {
               return this.doStatic(leftNodeValue__x, rightNodeValue__);
            }
         }
      }

      if ((state_0 & 8) != 0 && fallbackGuard_(leftNodeValue_, rightNodeValue_)) {
         return this.doFallback(leftNodeValue_, rightNodeValue_, this.fallback_isObjectNode_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
      }
   }

   @CompilerDirectives.TruffleBoundary
   private boolean instance1Boundary0(int state_0, HiddenKey leftNodeValue__, JSDynamicObject rightNodeValue__) {
      DynamicObjectLibrary instance1_access__ = DYNAMIC_OBJECT_LIBRARY_.getUncached(rightNodeValue__);
      return this.doInstance(leftNodeValue__, rightNodeValue__, instance1_access__);
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.executeBoolean(frameValue);
   }

   private boolean executeAndSpecialize(Object leftNodeValue, Object rightNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (rightNodeValue instanceof JSDynamicObject) {
            JSDynamicObject rightNodeValue_ = (JSDynamicObject)rightNodeValue;
            if (leftNodeValue instanceof HiddenKey) {
               HiddenKey leftNodeValue_ = (HiddenKey)leftNodeValue;
               if (exclude == 0) {
                  int count0_ = 0;
                  PrivateFieldInNodeGen.Instance0Data s0_ = this.instance0_cache;
                  if ((state_0 & 1) != 0) {
                     while (s0_ != null && (!s0_.access_.accepts(rightNodeValue_) || !JSGuards.isJSObject(rightNodeValue_))) {
                        s0_ = s0_.next_;
                        count0_++;
                     }
                  }

                  if (s0_ == null && JSGuards.isJSObject(rightNodeValue_) && count0_ < 3) {
                     s0_ = super.insert(new PrivateFieldInNodeGen.Instance0Data(this.instance0_cache));
                     s0_.access_ = s0_.insertAccessor(DYNAMIC_OBJECT_LIBRARY_.create(rightNodeValue_));
                     VarHandle.storeStoreFence();
                     this.instance0_cache = s0_;
                     this.state_0_ = state_0 |= 1;
                  }

                  if (s0_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doInstance(leftNodeValue_, rightNodeValue_, s0_.access_);
                  }
               }

               DynamicObjectLibrary instance1_access__ = null;
               if (JSGuards.isJSObject(rightNodeValue_)) {
                  instance1_access__ = DYNAMIC_OBJECT_LIBRARY_.getUncached(rightNodeValue_);
                  int var19;
                  this.exclude_ = var19 = exclude | 1;
                  this.instance0_cache = null;
                  state_0 &= -2;
                  int var18;
                  this.state_0_ = var18 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.doInstance(leftNodeValue_, rightNodeValue_, instance1_access__);
               }
            }

            if (leftNodeValue instanceof JSDynamicObject) {
               JSDynamicObject leftNodeValue_x = (JSDynamicObject)leftNodeValue;
               if (JSGuards.isJSObject(rightNodeValue_)) {
                  int var16;
                  this.state_0_ = var16 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.doStatic(leftNodeValue_x, rightNodeValue_);
               }
            }
         }

         this.fallback_isObjectNode_ = super.insert(IsObjectNode.create());
         int var15;
         this.state_0_ = var15 = state_0 | 8;
         lock.unlock();
         hasLock = false;
         return this.doFallback(leftNodeValue, rightNodeValue, this.fallback_isObjectNode_);
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
            PrivateFieldInNodeGen.Instance0Data s0_ = this.instance0_cache;
            if (s0_ == null || s0_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[5];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doInstance", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (PrivateFieldInNodeGen.Instance0Data s0_ = this.instance0_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.access_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doInstance", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList());
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doStatic", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doFallback", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.fallback_isObjectNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      return Introspection.Provider.create(data);
   }

   private static boolean fallbackGuard_(Object leftNodeValue, Object rightNodeValue) {
      if (rightNodeValue instanceof JSDynamicObject) {
         if (leftNodeValue instanceof HiddenKey) {
            JSDynamicObject rightNodeValue_ = (JSDynamicObject)rightNodeValue;
            if (JSGuards.isJSObject(rightNodeValue_)) {
               return false;
            }
         }

         if (leftNodeValue instanceof JSDynamicObject) {
            JSDynamicObject rightNodeValue_ = (JSDynamicObject)rightNodeValue;
            if (JSGuards.isJSObject(rightNodeValue_)) {
               return false;
            }
         }
      }

      return true;
   }

   public static PrivateFieldInNode create(JavaScriptNode left, JavaScriptNode right) {
      return new PrivateFieldInNodeGen(left, right);
   }

   @GeneratedBy(PrivateFieldInNode.class)
   private static final class Instance0Data extends Node {
      @Node.Child
      PrivateFieldInNodeGen.Instance0Data next_;
      @Node.Child
      DynamicObjectLibrary access_;

      Instance0Data(PrivateFieldInNodeGen.Instance0Data next_) {
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
