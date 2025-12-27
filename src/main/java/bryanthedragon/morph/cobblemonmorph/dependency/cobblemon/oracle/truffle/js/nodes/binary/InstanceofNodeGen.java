package com.oracle.truffle.js.nodes.binary;

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
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.GetMethodNode;
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.interop.ForeignObjectPrototypeNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(InstanceofNode.class)
public final class InstanceofNodeGen extends InstanceofNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private InstanceofNodeGen.JSObjectData jSObject_cache;
   @Node.Child
   private InstanceofNodeGen.ForeignTargetOther0Data foreignTargetOther0_cache;

   private InstanceofNodeGen(JSContext context, JavaScriptNode left, JavaScriptNode right) {
      super(context, left, right);
   }

   @ExplodeLoop
   @Override
   public boolean executeBoolean(Object leftNodeValue, Object rightNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 127) != 0) {
         if ((state_0 & 3) != 0 && rightNodeValue instanceof JSDynamicObject) {
            JSDynamicObject rightNodeValue_ = (JSDynamicObject)rightNodeValue;
            if ((state_0 & 1) != 0) {
               InstanceofNodeGen.JSObjectData s0_ = this.jSObject_cache;
               if (s0_ != null && s0_.isObjectNode_.executeBoolean(rightNodeValue_)) {
                  return this.doJSObject(
                     leftNodeValue,
                     rightNodeValue_,
                     s0_.isObjectNode_,
                     s0_.getMethodHasInstanceNode_,
                     s0_.toBooleanNode_,
                     s0_.callHasInstanceNode_,
                     s0_.isCallableNode_,
                     s0_.hasInstanceProfile_,
                     s0_.errorBranch_
                  );
               }
            }

            if ((state_0 & 2) != 0 && JSGuards.isNullOrUndefined(rightNodeValue_)) {
               return this.doNullOrUndefinedTarget(leftNodeValue, rightNodeValue_);
            }
         }

         if ((state_0 & 4) != 0 && rightNodeValue instanceof TruffleString) {
            TruffleString rightNodeValue_x = (TruffleString)rightNodeValue;
            return this.doStringTarget(leftNodeValue, rightNodeValue_x);
         }

         if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 15360) >>> 10, rightNodeValue)) {
            double rightNodeValue_x = JSTypesGen.asImplicitDouble((state_0 & 15360) >>> 10, rightNodeValue);
            return this.doDoubleTarget(leftNodeValue, rightNodeValue_x);
         }

         if ((state_0 & 16) != 0 && rightNodeValue instanceof Boolean) {
            boolean rightNodeValue_x = (Boolean)rightNodeValue;
            return this.doBooleanTarget(leftNodeValue, rightNodeValue_x);
         }

         if ((state_0 & 32) != 0 && rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_x = (BigInt)rightNodeValue;
            return this.doBigIntTarget(leftNodeValue, rightNodeValue_x);
         }

         if ((state_0 & 64) != 0 && rightNodeValue instanceof Symbol) {
            Symbol rightNodeValue_x = (Symbol)rightNodeValue;
            return this.doSymbolTarget(leftNodeValue, rightNodeValue_x);
         }
      }

      if ((state_0 & 896) != 0) {
         if ((state_0 & 128) != 0 && leftNodeValue instanceof JSDynamicObject) {
            JSDynamicObject leftNodeValue_ = (JSDynamicObject)leftNodeValue;
            if (JSGuards.isForeignObject(rightNodeValue) && JSGuards.isJSDynamicObject(leftNodeValue_)) {
               return this.doForeignTargetJSType(leftNodeValue_, rightNodeValue);
            }
         }

         if ((state_0 & 768) != 0) {
            if ((state_0 & 256) != 0) {
               for (InstanceofNodeGen.ForeignTargetOther0Data s8_ = this.foreignTargetOther0_cache; s8_ != null; s8_ = s8_.next_) {
                  if (s8_.interop_.accepts(rightNodeValue) && JSGuards.isForeignObject(rightNodeValue) && !JSGuards.isJSDynamicObject(leftNodeValue)) {
                     return this.doForeignTargetOther(leftNodeValue, rightNodeValue, s8_.interop_);
                  }
               }
            }

            if ((state_0 & 512) != 0 && JSGuards.isForeignObject(rightNodeValue) && !JSGuards.isJSDynamicObject(leftNodeValue)) {
               return this.foreignTargetOther1Boundary(state_0, leftNodeValue, rightNodeValue);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
   }

   @CompilerDirectives.TruffleBoundary
   private boolean foreignTargetOther1Boundary(int state_0, Object leftNodeValue, Object rightNodeValue) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      boolean var7;
      try {
         InteropLibrary foreignTargetOther1_interop__ = INTEROP_LIBRARY_.getUncached(rightNodeValue);
         var7 = this.doForeignTargetOther(leftNodeValue, rightNodeValue, foreignTargetOther1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var7;
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1015) == 0 && (state_0 & 1023) != 0) {
         return this.execute_double0(state_0, frameValue);
      } else {
         return (state_0 & 1007) == 0 && (state_0 & 1023) != 0 ? this.execute_boolean1(state_0, frameValue) : this.execute_generic2(state_0, frameValue);
      }
   }

   private Object execute_double0(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      long rightNodeValue_long = 0L;
      int rightNodeValue_int = 0;

      double rightNodeValue_;
      try {
         if ((state_0 & 14336) == 0 && (state_0 & 1023) != 0) {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         } else if ((state_0 & 13312) == 0 && (state_0 & 1023) != 0) {
            rightNodeValue_int = super.rightNode.executeInt(frameValue);
            rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
         } else if ((state_0 & 7168) == 0 && (state_0 & 1023) != 0) {
            rightNodeValue_long = super.rightNode.executeLong(frameValue);
            rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
         } else {
            Object rightNodeValue__ = super.rightNode.execute(frameValue);
            rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 15360) >>> 10, rightNodeValue__);
         }
      } catch (UnexpectedResultException var10) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var10.getResult());
      }

      assert (state_0 & 8) != 0;

      return this.doDoubleTarget(leftNodeValue_, rightNodeValue_);
   }

   private Object execute_boolean1(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);

      boolean rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var6.getResult());
      }

      assert (state_0 & 16) != 0;

      return this.doBooleanTarget(leftNodeValue_, rightNodeValue_);
   }

   @CompilerDirectives.TruffleBoundary
   private Object foreignTargetOther1Boundary0(int state_0, Object leftNodeValue_, Object rightNodeValue_) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      Boolean var7;
      try {
         InteropLibrary foreignTargetOther1_interop__ = INTEROP_LIBRARY_.getUncached(rightNodeValue_);
         var7 = this.doForeignTargetOther(leftNodeValue_, rightNodeValue_, foreignTargetOther1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var7;
   }

   @ExplodeLoop
   private Object execute_generic2(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 127) != 0) {
         if ((state_0 & 3) != 0 && rightNodeValue_ instanceof JSDynamicObject) {
            JSDynamicObject rightNodeValue__ = (JSDynamicObject)rightNodeValue_;
            if ((state_0 & 1) != 0) {
               InstanceofNodeGen.JSObjectData s0_ = this.jSObject_cache;
               if (s0_ != null && s0_.isObjectNode_.executeBoolean(rightNodeValue__)) {
                  return this.doJSObject(
                     leftNodeValue_,
                     rightNodeValue__,
                     s0_.isObjectNode_,
                     s0_.getMethodHasInstanceNode_,
                     s0_.toBooleanNode_,
                     s0_.callHasInstanceNode_,
                     s0_.isCallableNode_,
                     s0_.hasInstanceProfile_,
                     s0_.errorBranch_
                  );
               }
            }

            if ((state_0 & 2) != 0 && JSGuards.isNullOrUndefined(rightNodeValue__)) {
               return this.doNullOrUndefinedTarget(leftNodeValue_, rightNodeValue__);
            }
         }

         if ((state_0 & 4) != 0 && rightNodeValue_ instanceof TruffleString) {
            TruffleString rightNodeValue__x = (TruffleString)rightNodeValue_;
            return this.doStringTarget(leftNodeValue_, rightNodeValue__x);
         }

         if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 15360) >>> 10, rightNodeValue_)) {
            double rightNodeValue__x = JSTypesGen.asImplicitDouble((state_0 & 15360) >>> 10, rightNodeValue_);
            return this.doDoubleTarget(leftNodeValue_, rightNodeValue__x);
         }

         if ((state_0 & 16) != 0 && rightNodeValue_ instanceof Boolean) {
            boolean rightNodeValue__x = (Boolean)rightNodeValue_;
            return this.doBooleanTarget(leftNodeValue_, rightNodeValue__x);
         }

         if ((state_0 & 32) != 0 && rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__x = (BigInt)rightNodeValue_;
            return this.doBigIntTarget(leftNodeValue_, rightNodeValue__x);
         }

         if ((state_0 & 64) != 0 && rightNodeValue_ instanceof Symbol) {
            Symbol rightNodeValue__x = (Symbol)rightNodeValue_;
            return this.doSymbolTarget(leftNodeValue_, rightNodeValue__x);
         }
      }

      if ((state_0 & 896) != 0) {
         if ((state_0 & 128) != 0 && leftNodeValue_ instanceof JSDynamicObject) {
            JSDynamicObject leftNodeValue__ = (JSDynamicObject)leftNodeValue_;
            if (JSGuards.isForeignObject(rightNodeValue_) && JSGuards.isJSDynamicObject(leftNodeValue__)) {
               return this.doForeignTargetJSType(leftNodeValue__, rightNodeValue_);
            }
         }

         if ((state_0 & 768) != 0) {
            if ((state_0 & 256) != 0) {
               for (InstanceofNodeGen.ForeignTargetOther0Data s8_ = this.foreignTargetOther0_cache; s8_ != null; s8_ = s8_.next_) {
                  if (s8_.interop_.accepts(rightNodeValue_) && JSGuards.isForeignObject(rightNodeValue_) && !JSGuards.isJSDynamicObject(leftNodeValue_)) {
                     return this.doForeignTargetOther(leftNodeValue_, rightNodeValue_, s8_.interop_);
                  }
               }
            }

            if ((state_0 & 512) != 0 && JSGuards.isForeignObject(rightNodeValue_) && !JSGuards.isJSDynamicObject(leftNodeValue_)) {
               return this.foreignTargetOther1Boundary0(state_0, leftNodeValue_, rightNodeValue_);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
   }

   @Override
   public boolean executeBoolean(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1015) == 0 && (state_0 & 1023) != 0) {
         return this.executeBoolean_double3(state_0, frameValue);
      } else {
         return (state_0 & 1007) == 0 && (state_0 & 1023) != 0
            ? this.executeBoolean_boolean4(state_0, frameValue)
            : this.executeBoolean_generic5(state_0, frameValue);
      }
   }

   private boolean executeBoolean_double3(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      long rightNodeValue_long = 0L;
      int rightNodeValue_int = 0;

      double rightNodeValue_;
      try {
         if ((state_0 & 14336) == 0 && (state_0 & 1023) != 0) {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         } else if ((state_0 & 13312) == 0 && (state_0 & 1023) != 0) {
            rightNodeValue_int = super.rightNode.executeInt(frameValue);
            rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
         } else if ((state_0 & 7168) == 0 && (state_0 & 1023) != 0) {
            rightNodeValue_long = super.rightNode.executeLong(frameValue);
            rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
         } else {
            Object rightNodeValue__ = super.rightNode.execute(frameValue);
            rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 15360) >>> 10, rightNodeValue__);
         }
      } catch (UnexpectedResultException var10) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var10.getResult());
      }

      assert (state_0 & 8) != 0;

      return this.doDoubleTarget(leftNodeValue_, rightNodeValue_);
   }

   private boolean executeBoolean_boolean4(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);

      boolean rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var6.getResult());
      }

      assert (state_0 & 16) != 0;

      return this.doBooleanTarget(leftNodeValue_, rightNodeValue_);
   }

   @CompilerDirectives.TruffleBoundary
   private boolean foreignTargetOther1Boundary1(int state_0, Object leftNodeValue_, Object rightNodeValue_) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      boolean var7;
      try {
         InteropLibrary foreignTargetOther1_interop__ = INTEROP_LIBRARY_.getUncached(rightNodeValue_);
         var7 = this.doForeignTargetOther(leftNodeValue_, rightNodeValue_, foreignTargetOther1_interop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var7;
   }

   @ExplodeLoop
   private boolean executeBoolean_generic5(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 127) != 0) {
         if ((state_0 & 3) != 0 && rightNodeValue_ instanceof JSDynamicObject) {
            JSDynamicObject rightNodeValue__ = (JSDynamicObject)rightNodeValue_;
            if ((state_0 & 1) != 0) {
               InstanceofNodeGen.JSObjectData s0_ = this.jSObject_cache;
               if (s0_ != null && s0_.isObjectNode_.executeBoolean(rightNodeValue__)) {
                  return this.doJSObject(
                     leftNodeValue_,
                     rightNodeValue__,
                     s0_.isObjectNode_,
                     s0_.getMethodHasInstanceNode_,
                     s0_.toBooleanNode_,
                     s0_.callHasInstanceNode_,
                     s0_.isCallableNode_,
                     s0_.hasInstanceProfile_,
                     s0_.errorBranch_
                  );
               }
            }

            if ((state_0 & 2) != 0 && JSGuards.isNullOrUndefined(rightNodeValue__)) {
               return this.doNullOrUndefinedTarget(leftNodeValue_, rightNodeValue__);
            }
         }

         if ((state_0 & 4) != 0 && rightNodeValue_ instanceof TruffleString) {
            TruffleString rightNodeValue__x = (TruffleString)rightNodeValue_;
            return this.doStringTarget(leftNodeValue_, rightNodeValue__x);
         }

         if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 15360) >>> 10, rightNodeValue_)) {
            double rightNodeValue__x = JSTypesGen.asImplicitDouble((state_0 & 15360) >>> 10, rightNodeValue_);
            return this.doDoubleTarget(leftNodeValue_, rightNodeValue__x);
         }

         if ((state_0 & 16) != 0 && rightNodeValue_ instanceof Boolean) {
            boolean rightNodeValue__x = (Boolean)rightNodeValue_;
            return this.doBooleanTarget(leftNodeValue_, rightNodeValue__x);
         }

         if ((state_0 & 32) != 0 && rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__x = (BigInt)rightNodeValue_;
            return this.doBigIntTarget(leftNodeValue_, rightNodeValue__x);
         }

         if ((state_0 & 64) != 0 && rightNodeValue_ instanceof Symbol) {
            Symbol rightNodeValue__x = (Symbol)rightNodeValue_;
            return this.doSymbolTarget(leftNodeValue_, rightNodeValue__x);
         }
      }

      if ((state_0 & 896) != 0) {
         if ((state_0 & 128) != 0 && leftNodeValue_ instanceof JSDynamicObject) {
            JSDynamicObject leftNodeValue__ = (JSDynamicObject)leftNodeValue_;
            if (JSGuards.isForeignObject(rightNodeValue_) && JSGuards.isJSDynamicObject(leftNodeValue__)) {
               return this.doForeignTargetJSType(leftNodeValue__, rightNodeValue_);
            }
         }

         if ((state_0 & 768) != 0) {
            if ((state_0 & 256) != 0) {
               for (InstanceofNodeGen.ForeignTargetOther0Data s8_ = this.foreignTargetOther0_cache; s8_ != null; s8_ = s8_.next_) {
                  if (s8_.interop_.accepts(rightNodeValue_) && JSGuards.isForeignObject(rightNodeValue_) && !JSGuards.isJSDynamicObject(leftNodeValue_)) {
                     return this.doForeignTargetOther(leftNodeValue_, rightNodeValue_, s8_.interop_);
                  }
               }
            }

            if ((state_0 & 512) != 0 && JSGuards.isForeignObject(rightNodeValue_) && !JSGuards.isJSDynamicObject(leftNodeValue_)) {
               return this.foreignTargetOther1Boundary1(state_0, leftNodeValue_, rightNodeValue_);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
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
            InstanceofNodeGen.JSObjectData s0_ = this.jSObject_cache;
            boolean JSObject_duplicateFound_ = false;
            if ((state_0 & 1) != 0 && s0_.isObjectNode_.executeBoolean(rightNodeValue_)) {
               JSObject_duplicateFound_ = true;
            }

            if (!JSObject_duplicateFound_) {
               IsJSObjectNode isObjectNode__ = super.insert(IsJSObjectNode.create());
               if (isObjectNode__.executeBoolean(rightNodeValue_) && (state_0 & 1) == 0) {
                  s0_ = super.insert(new InstanceofNodeGen.JSObjectData());
                  s0_.isObjectNode_ = s0_.insertAccessor(isObjectNode__);
                  s0_.getMethodHasInstanceNode_ = s0_.insertAccessor(this.createGetMethodHasInstance());
                  s0_.toBooleanNode_ = s0_.insertAccessor(JSToBooleanNode.create());
                  s0_.callHasInstanceNode_ = s0_.insertAccessor(JSFunctionCallNode.createCall());
                  s0_.isCallableNode_ = s0_.insertAccessor(IsCallableNode.create());
                  s0_.hasInstanceProfile_ = ConditionProfile.createBinaryProfile();
                  s0_.errorBranch_ = BranchProfile.create();
                  VarHandle.storeStoreFence();
                  this.jSObject_cache = s0_;
                  this.state_0_ = state_0 |= 1;
                  JSObject_duplicateFound_ = true;
               }
            }

            if (JSObject_duplicateFound_) {
               lock.unlock();
               hasLock = false;
               return this.doJSObject(
                  leftNodeValue,
                  rightNodeValue_,
                  s0_.isObjectNode_,
                  s0_.getMethodHasInstanceNode_,
                  s0_.toBooleanNode_,
                  s0_.callHasInstanceNode_,
                  s0_.isCallableNode_,
                  s0_.hasInstanceProfile_,
                  s0_.errorBranch_
               );
            }

            if (JSGuards.isNullOrUndefined(rightNodeValue_)) {
               int var28;
               this.state_0_ = var28 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doNullOrUndefinedTarget(leftNodeValue, rightNodeValue_);
            }
         }

         if (rightNodeValue instanceof TruffleString) {
            TruffleString rightNodeValue_x = (TruffleString)rightNodeValue;
            int var27;
            this.state_0_ = var27 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doStringTarget(leftNodeValue, rightNodeValue_x);
         } else {
            int doubleCast1;
            if ((doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
               double rightNodeValue_x = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
               state_0 |= doubleCast1 << 10;
               int var26;
               this.state_0_ = var26 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.doDoubleTarget(leftNodeValue, rightNodeValue_x);
            } else if (rightNodeValue instanceof Boolean) {
               boolean rightNodeValue_x = (Boolean)rightNodeValue;
               int var24;
               this.state_0_ = var24 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.doBooleanTarget(leftNodeValue, rightNodeValue_x);
            } else if (rightNodeValue instanceof BigInt) {
               BigInt rightNodeValue_x = (BigInt)rightNodeValue;
               int var23;
               this.state_0_ = var23 = state_0 | 32;
               lock.unlock();
               hasLock = false;
               return this.doBigIntTarget(leftNodeValue, rightNodeValue_x);
            } else if (rightNodeValue instanceof Symbol) {
               Symbol rightNodeValue_x = (Symbol)rightNodeValue;
               int var22;
               this.state_0_ = var22 = state_0 | 64;
               lock.unlock();
               hasLock = false;
               return this.doSymbolTarget(leftNodeValue, rightNodeValue_x);
            } else {
               if (leftNodeValue instanceof JSDynamicObject) {
                  JSDynamicObject leftNodeValue_ = (JSDynamicObject)leftNodeValue;
                  if (JSGuards.isForeignObject(rightNodeValue) && JSGuards.isJSDynamicObject(leftNodeValue_)) {
                     int var21;
                     this.state_0_ = var21 = state_0 | 128;
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignTargetJSType(leftNodeValue_, rightNodeValue);
                  }
               }

               if (exclude == 0) {
                  doubleCast1 = 0;
                  InstanceofNodeGen.ForeignTargetOther0Data s8_ = this.foreignTargetOther0_cache;
                  if ((state_0 & 256) != 0) {
                     while (
                        s8_ != null
                           && (!s8_.interop_.accepts(rightNodeValue) || !JSGuards.isForeignObject(rightNodeValue) || JSGuards.isJSDynamicObject(leftNodeValue))
                     ) {
                        s8_ = s8_.next_;
                        doubleCast1++;
                     }
                  }

                  if (s8_ == null && JSGuards.isForeignObject(rightNodeValue) && !JSGuards.isJSDynamicObject(leftNodeValue) && doubleCast1 < 5) {
                     s8_ = super.insert(new InstanceofNodeGen.ForeignTargetOther0Data(this.foreignTargetOther0_cache));
                     s8_.interop_ = s8_.insertAccessor(INTEROP_LIBRARY_.create(rightNodeValue));
                     VarHandle.storeStoreFence();
                     this.foreignTargetOther0_cache = s8_;
                     this.state_0_ = state_0 |= 256;
                  }

                  if (s8_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignTargetOther(leftNodeValue, rightNodeValue, s8_.interop_);
                  }
               }

               InteropLibrary foreignTargetOther1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (JSGuards.isForeignObject(rightNodeValue) && !JSGuards.isJSDynamicObject(leftNodeValue)) {
                     InteropLibrary var34 = INTEROP_LIBRARY_.getUncached(rightNodeValue);
                     int var29;
                     this.exclude_ = var29 = exclude | 1;
                     this.foreignTargetOther0_cache = null;
                     state_0 &= -257;
                     int var20;
                     this.state_0_ = var20 = state_0 | 512;
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignTargetOther(leftNodeValue, rightNodeValue, var34);
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               throw new UnsupportedSpecializationException(this, new Node[]{super.leftNode, super.rightNode}, leftNodeValue, rightNodeValue);
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
      if ((state_0 & 1023) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & 1023 & (state_0 & 1023) - 1) == 0) {
            InstanceofNodeGen.ForeignTargetOther0Data s8_ = this.foreignTargetOther0_cache;
            if (s8_ == null || s8_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[11];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doJSObject", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         InstanceofNodeGen.JSObjectData s0_ = this.jSObject_cache;
         if (s0_ != null) {
            cached.add(
               Arrays.asList(
                  s0_.isObjectNode_,
                  s0_.getMethodHasInstanceNode_,
                  s0_.toBooleanNode_,
                  s0_.callHasInstanceNode_,
                  s0_.isCallableNode_,
                  s0_.hasInstanceProfile_,
                  s0_.errorBranch_
               )
            );
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doNullOrUndefinedTarget", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doStringTarget", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doDoubleTarget", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doBooleanTarget", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doBigIntTarget", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doSymbolTarget", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doForeignTargetJSType", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doForeignTargetOther", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (InstanceofNodeGen.ForeignTargetOther0Data s8_ = this.foreignTargetOther0_cache; s8_ != null; s8_ = s8_.next_) {
            cached.add(Arrays.asList(s8_.interop_));
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doForeignTargetOther", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList());
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      return Introspection.Provider.create(data);
   }

   public static InstanceofNode create(JSContext context, JavaScriptNode left, JavaScriptNode right) {
      return new InstanceofNodeGen(context, left, right);
   }

   @GeneratedBy(InstanceofNode.class)
   private static final class ForeignTargetOther0Data extends Node {
      @Node.Child
      InstanceofNodeGen.ForeignTargetOther0Data next_;
      @Node.Child
      InteropLibrary interop_;

      ForeignTargetOther0Data(InstanceofNodeGen.ForeignTargetOther0Data next_) {
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

   @GeneratedBy(InstanceofNode.IsBoundFunctionCacheNode.class)
   public static final class IsBoundFunctionCacheNodeGen extends InstanceofNode.IsBoundFunctionCacheNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private JSDynamicObject cachedInstance_cachedFunction_;
      @CompilerDirectives.CompilationFinal
      private boolean cachedInstance_cachedIsBound_;
      @CompilerDirectives.CompilationFinal
      private InstanceofNodeGen.IsBoundFunctionCacheNodeGen.CachedShapeData cachedShape_cache;

      private IsBoundFunctionCacheNodeGen(boolean multiContext) {
         super(multiContext);
      }

      @ExplodeLoop
      @Override
      public boolean executeBoolean(JSDynamicObject arg0Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               assert !this.multiContext;

               if (arg0Value == this.cachedInstance_cachedFunction_) {
                  return InstanceofNode.IsBoundFunctionCacheNode.doCachedInstance(
                     arg0Value, this.cachedInstance_cachedFunction_, this.cachedInstance_cachedIsBound_
                  );
               }
            }

            if ((state_0 & 2) != 0) {
               for (InstanceofNodeGen.IsBoundFunctionCacheNodeGen.CachedShapeData s1_ = this.cachedShape_cache; s1_ != null; s1_ = s1_.next_) {
                  if (s1_.cachedShape_.check(arg0Value)) {
                     return InstanceofNode.IsBoundFunctionCacheNode.doCachedShape(arg0Value, s1_.cachedShape_, s1_.cachedIsBound_);
                  }
               }
            }

            if ((state_0 & 4) != 0) {
               return InstanceofNode.IsBoundFunctionCacheNode.isBoundFunction(arg0Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }

      private boolean executeAndSpecialize(JSDynamicObject arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if ((exclude & 1) == 0) {
               boolean CachedInstance_duplicateFound_ = false;
               if ((state_0 & 1) != 0) {
                  assert !this.multiContext;

                  if (arg0Value == this.cachedInstance_cachedFunction_) {
                     CachedInstance_duplicateFound_ = true;
                  }
               }

               if (!CachedInstance_duplicateFound_ && !this.multiContext && (state_0 & 1) == 0) {
                  this.cachedInstance_cachedFunction_ = arg0Value;
                  this.cachedInstance_cachedIsBound_ = InstanceofNode.IsBoundFunctionCacheNode.isBoundFunction(arg0Value);
                  this.state_0_ = state_0 |= 1;
                  CachedInstance_duplicateFound_ = true;
               }

               if (CachedInstance_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return InstanceofNode.IsBoundFunctionCacheNode.doCachedInstance(
                     arg0Value, this.cachedInstance_cachedFunction_, this.cachedInstance_cachedIsBound_
                  );
               }
            }

            if ((exclude & 2) == 0) {
               int count1_ = 0;
               InstanceofNodeGen.IsBoundFunctionCacheNodeGen.CachedShapeData s1_ = this.cachedShape_cache;
               if ((state_0 & 2) != 0) {
                  while (s1_ != null && !s1_.cachedShape_.check(arg0Value)) {
                     s1_ = s1_.next_;
                     count1_++;
                  }
               }

               if (s1_ == null) {
                  Shape cachedShape__ = arg0Value.getShape();
                  if (cachedShape__.check(arg0Value) && count1_ < 3) {
                     s1_ = new InstanceofNodeGen.IsBoundFunctionCacheNodeGen.CachedShapeData(this.cachedShape_cache);
                     s1_.cachedShape_ = cachedShape__;
                     s1_.cachedIsBound_ = InstanceofNode.IsBoundFunctionCacheNode.isBoundFunction(arg0Value);
                     VarHandle.storeStoreFence();
                     this.cachedShape_cache = s1_;
                     this.exclude_ = exclude |= 1;
                     int var12 = state_0 & -2;
                     this.state_0_ = state_0 = var12 | 2;
                  }
               }

               if (s1_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return InstanceofNode.IsBoundFunctionCacheNode.doCachedShape(arg0Value, s1_.cachedShape_, s1_.cachedIsBound_);
               }
            }

            int var15;
            this.exclude_ = var15 = exclude | 3;
            this.cachedShape_cache = null;
            state_0 &= -4;
            int var14;
            this.state_0_ = var14 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return InstanceofNode.IsBoundFunctionCacheNode.isBoundFunction(arg0Value);
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
               InstanceofNodeGen.IsBoundFunctionCacheNodeGen.CachedShapeData s1_ = this.cachedShape_cache;
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
         Object[] s = new Object[]{"doCachedInstance", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.cachedInstance_cachedFunction_, this.cachedInstance_cachedIsBound_));
            s[2] = cached;
         } else if ((exclude & 1) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doCachedShape", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (InstanceofNodeGen.IsBoundFunctionCacheNodeGen.CachedShapeData s1_ = this.cachedShape_cache; s1_ != null; s1_ = s1_.next_) {
               cached.add(Arrays.asList(s1_.cachedShape_, s1_.cachedIsBound_));
            }

            s[2] = cached;
         } else if ((exclude & 2) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"isBoundFunction", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static InstanceofNode.IsBoundFunctionCacheNode create(boolean multiContext) {
         return new InstanceofNodeGen.IsBoundFunctionCacheNodeGen(multiContext);
      }

      @GeneratedBy(InstanceofNode.IsBoundFunctionCacheNode.class)
      private static final class CachedShapeData {
         @CompilerDirectives.CompilationFinal
         InstanceofNodeGen.IsBoundFunctionCacheNodeGen.CachedShapeData next_;
         @CompilerDirectives.CompilationFinal
         Shape cachedShape_;
         @CompilerDirectives.CompilationFinal
         boolean cachedIsBound_;

         CachedShapeData(InstanceofNodeGen.IsBoundFunctionCacheNodeGen.CachedShapeData next_) {
            this.next_ = next_;
         }
      }
   }

   @GeneratedBy(InstanceofNode.class)
   private static final class JSObjectData extends Node {
      @Node.Child
      IsJSObjectNode isObjectNode_;
      @Node.Child
      GetMethodNode getMethodHasInstanceNode_;
      @Node.Child
      JSToBooleanNode toBooleanNode_;
      @Node.Child
      JSFunctionCallNode callHasInstanceNode_;
      @Node.Child
      IsCallableNode isCallableNode_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile hasInstanceProfile_;
      @CompilerDirectives.CompilationFinal
      BranchProfile errorBranch_;

      JSObjectData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(InstanceofNode.OrdinaryHasInstanceNode.class)
   public static final class OrdinaryHasInstanceNodeGen extends InstanceofNode.OrdinaryHasInstanceNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ForeignObjectPrototypeNode foreignPrototypeNode;
      @CompilerDirectives.CompilationFinal
      private BranchProfile invalidPrototypeBranch;
      @Node.Child
      private InstanceofNode.OrdinaryHasInstanceNode ordinaryHasInstance;
      @Node.Child
      private IsJSObjectNode isObjectNode;
      @Node.Child
      private GetPrototypeNode getPrototype1Node;
      @Node.Child
      private GetPrototypeNode getPrototype2Node;
      @Node.Child
      private GetPrototypeNode getPrototype3Node;
      @CompilerDirectives.CompilationFinal
      private BranchProfile firstTrue;
      @CompilerDirectives.CompilationFinal
      private BranchProfile firstFalse;
      @CompilerDirectives.CompilationFinal
      private BranchProfile need2Hops;
      @CompilerDirectives.CompilationFinal
      private BranchProfile need3Hops;
      @CompilerDirectives.CompilationFinal
      private BranchProfile errorBranch;
      @Node.Child
      private InstanceofNode isBound_instanceofNode_;

      private OrdinaryHasInstanceNodeGen(JSContext context) {
         super(context);
      }

      @Override
      public boolean executeBoolean(Object arg0Value, Object arg1Value) {
         int state_0 = this.state_0_;
         if ((state_0 & 63) != 0) {
            if ((state_0 & 1) != 0 && !this.isCallableNode.executeBoolean(arg1Value)) {
               return this.doNotCallable(arg0Value, arg1Value);
            }

            if ((state_0 & 62) != 0 && arg1Value instanceof JSDynamicObject) {
               JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
               if ((state_0 & 2) != 0 && JSGuards.isJSFunction(arg1Value_) && this.isBoundFunction(arg1Value_)) {
                  return this.doIsBound(arg0Value, arg1Value_, this.isBound_instanceofNode_);
               }

               if ((state_0 & 4) != 0
                  && !JSGuards.isJSObject(arg0Value)
                  && JSGuards.isForeignObject(arg0Value)
                  && JSGuards.isJSFunction(arg1Value_)
                  && !this.isBoundFunction(arg1Value_)) {
                  return this.doForeignObject(arg0Value, arg1Value_, this.foreignPrototypeNode, this.invalidPrototypeBranch, this.ordinaryHasInstance);
               }

               if ((state_0 & 8) != 0
                  && !JSGuards.isJSObject(arg0Value)
                  && !JSGuards.isForeignObject(arg0Value)
                  && JSGuards.isJSFunction(arg1Value_)
                  && !this.isBoundFunction(arg1Value_)) {
                  return this.doNotAnObject(arg0Value, arg1Value_);
               }

               if ((state_0 & 16) != 0
                  && !JSGuards.isJSObject(arg0Value)
                  && JSGuards.isForeignObject(arg0Value)
                  && JSGuards.isJSProxy(arg1Value_)
                  && JSGuards.isCallableProxy(arg1Value_)) {
                  return this.doNotAnObjectProxyForeign(arg0Value, arg1Value_, this.foreignPrototypeNode, this.invalidPrototypeBranch, this.ordinaryHasInstance);
               }

               if ((state_0 & 32) != 0
                  && !JSGuards.isJSObject(arg0Value)
                  && !JSGuards.isForeignObject(arg0Value)
                  && JSGuards.isJSProxy(arg1Value_)
                  && JSGuards.isCallableProxy(arg1Value_)) {
                  return this.doNotAnObjectProxyPrimitive(arg0Value, arg1Value_);
               }
            }
         }

         if ((state_0 & 192) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if (arg1Value instanceof JSDynamicObject) {
               JSDynamicObject arg1Value_x = (JSDynamicObject)arg1Value;
               if ((state_0 & 64) != 0
                  && this.isObjectNode.executeBoolean(arg0Value_)
                  && JSGuards.isJSFunction(arg1Value_x)
                  && !this.isBoundFunction(arg1Value_x)) {
                  return this.doJSObject(
                     arg0Value_,
                     arg1Value_x,
                     this.isObjectNode,
                     this.getPrototype1Node,
                     this.getPrototype2Node,
                     this.getPrototype3Node,
                     this.firstTrue,
                     this.firstFalse,
                     this.need2Hops,
                     this.need3Hops,
                     this.errorBranch,
                     this.invalidPrototypeBranch
                  );
               }

               if ((state_0 & 128) != 0
                  && this.isObjectNode.executeBoolean(arg0Value_)
                  && JSGuards.isJSProxy(arg1Value_x)
                  && JSGuards.isCallableProxy(arg1Value_x)) {
                  return this.doJSObjectProxy(
                     arg0Value_,
                     arg1Value_x,
                     this.isObjectNode,
                     this.getPrototype1Node,
                     this.getPrototype2Node,
                     this.getPrototype3Node,
                     this.firstTrue,
                     this.firstFalse,
                     this.need2Hops,
                     this.need3Hops,
                     this.errorBranch,
                     this.invalidPrototypeBranch
                  );
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private boolean executeAndSpecialize(Object arg0Value, Object arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (!this.isCallableNode.executeBoolean(arg1Value)) {
               int var21;
               this.state_0_ = var21 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doNotCallable(arg0Value, arg1Value);
            } else {
               if (arg1Value instanceof JSDynamicObject) {
                  JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
                  if (JSGuards.isJSFunction(arg1Value_) && this.isBoundFunction(arg1Value_)) {
                     this.isBound_instanceofNode_ = super.insert(InstanceofNode.create(this.context));
                     int var20;
                     this.state_0_ = var20 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.doIsBound(arg0Value, arg1Value_, this.isBound_instanceofNode_);
                  }

                  if (!JSGuards.isJSObject(arg0Value)
                     && JSGuards.isForeignObject(arg0Value)
                     && JSGuards.isJSFunction(arg1Value_)
                     && !this.isBoundFunction(arg1Value_)) {
                     this.foreignPrototypeNode = super.insert(
                        this.foreignPrototypeNode == null ? ForeignObjectPrototypeNode.create() : this.foreignPrototypeNode
                     );
                     this.invalidPrototypeBranch = this.invalidPrototypeBranch == null ? BranchProfile.create() : this.invalidPrototypeBranch;
                     this.ordinaryHasInstance = super.insert(
                        this.ordinaryHasInstance == null ? InstanceofNode.OrdinaryHasInstanceNode.create(this.context) : this.ordinaryHasInstance
                     );
                     int var19;
                     this.state_0_ = var19 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.doForeignObject(arg0Value, arg1Value_, this.foreignPrototypeNode, this.invalidPrototypeBranch, this.ordinaryHasInstance);
                  }

                  if (!JSGuards.isJSObject(arg0Value)
                     && !JSGuards.isForeignObject(arg0Value)
                     && JSGuards.isJSFunction(arg1Value_)
                     && !this.isBoundFunction(arg1Value_)) {
                     int var18;
                     this.state_0_ = var18 = state_0 | 8;
                     lock.unlock();
                     hasLock = false;
                     return this.doNotAnObject(arg0Value, arg1Value_);
                  }

                  if (!JSGuards.isJSObject(arg0Value)
                     && JSGuards.isForeignObject(arg0Value)
                     && JSGuards.isJSProxy(arg1Value_)
                     && JSGuards.isCallableProxy(arg1Value_)) {
                     this.foreignPrototypeNode = super.insert(
                        this.foreignPrototypeNode == null ? ForeignObjectPrototypeNode.create() : this.foreignPrototypeNode
                     );
                     this.invalidPrototypeBranch = this.invalidPrototypeBranch == null ? BranchProfile.create() : this.invalidPrototypeBranch;
                     this.ordinaryHasInstance = super.insert(
                        this.ordinaryHasInstance == null ? InstanceofNode.OrdinaryHasInstanceNode.create(this.context) : this.ordinaryHasInstance
                     );
                     int var17;
                     this.state_0_ = var17 = state_0 | 16;
                     lock.unlock();
                     hasLock = false;
                     return this.doNotAnObjectProxyForeign(
                        arg0Value, arg1Value_, this.foreignPrototypeNode, this.invalidPrototypeBranch, this.ordinaryHasInstance
                     );
                  }

                  if (!JSGuards.isJSObject(arg0Value)
                     && !JSGuards.isForeignObject(arg0Value)
                     && JSGuards.isJSProxy(arg1Value_)
                     && JSGuards.isCallableProxy(arg1Value_)) {
                     int var16;
                     this.state_0_ = var16 = state_0 | 32;
                     lock.unlock();
                     hasLock = false;
                     return this.doNotAnObjectProxyPrimitive(arg0Value, arg1Value_);
                  }
               }

               if (arg0Value instanceof JSDynamicObject) {
                  JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                  if (arg1Value instanceof JSDynamicObject) {
                     JSDynamicObject arg1Value_x = (JSDynamicObject)arg1Value;
                     boolean JSObject_duplicateFound_ = false;
                     if ((state_0 & 64) != 0
                        && this.isObjectNode.executeBoolean(arg0Value_)
                        && JSGuards.isJSFunction(arg1Value_x)
                        && !this.isBoundFunction(arg1Value_x)) {
                        JSObject_duplicateFound_ = true;
                     }

                     if (!JSObject_duplicateFound_) {
                        IsJSObjectNode jSObject_isObjectNode__ = super.insert(this.isObjectNode == null ? IsJSObjectNode.create() : this.isObjectNode);
                        if (jSObject_isObjectNode__.executeBoolean(arg0Value_)
                           && JSGuards.isJSFunction(arg1Value_x)
                           && !this.isBoundFunction(arg1Value_x)
                           && (state_0 & 64) == 0) {
                           if (this.isObjectNode == null) {
                              IsJSObjectNode jSObject_isObjectNode___check = super.insert(jSObject_isObjectNode__);
                              if (jSObject_isObjectNode___check == null) {
                                 throw new AssertionError(
                                    "Specialization 'doJSObject(JSDynamicObject, JSDynamicObject, IsJSObjectNode, GetPrototypeNode, GetPrototypeNode, GetPrototypeNode, BranchProfile, BranchProfile, BranchProfile, BranchProfile, BranchProfile, BranchProfile)' contains a shared cache with name 'isObjectNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                 );
                              }

                              this.isObjectNode = jSObject_isObjectNode___check;
                           }

                           this.getPrototype1Node = super.insert(this.getPrototype1Node == null ? GetPrototypeNode.create() : this.getPrototype1Node);
                           this.getPrototype2Node = super.insert(this.getPrototype2Node == null ? GetPrototypeNode.create() : this.getPrototype2Node);
                           this.getPrototype3Node = super.insert(this.getPrototype3Node == null ? GetPrototypeNode.create() : this.getPrototype3Node);
                           this.firstTrue = this.firstTrue == null ? BranchProfile.create() : this.firstTrue;
                           this.firstFalse = this.firstFalse == null ? BranchProfile.create() : this.firstFalse;
                           this.need2Hops = this.need2Hops == null ? BranchProfile.create() : this.need2Hops;
                           this.need3Hops = this.need3Hops == null ? BranchProfile.create() : this.need3Hops;
                           this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                           this.invalidPrototypeBranch = this.invalidPrototypeBranch == null ? BranchProfile.create() : this.invalidPrototypeBranch;
                           this.state_0_ = state_0 |= 64;
                           JSObject_duplicateFound_ = true;
                        }
                     }

                     if (JSObject_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        return this.doJSObject(
                           arg0Value_,
                           arg1Value_x,
                           this.isObjectNode,
                           this.getPrototype1Node,
                           this.getPrototype2Node,
                           this.getPrototype3Node,
                           this.firstTrue,
                           this.firstFalse,
                           this.need2Hops,
                           this.need3Hops,
                           this.errorBranch,
                           this.invalidPrototypeBranch
                        );
                     }

                     boolean JSObjectProxy_duplicateFound_ = false;
                     if ((state_0 & 128) != 0
                        && this.isObjectNode.executeBoolean(arg0Value_)
                        && JSGuards.isJSProxy(arg1Value_x)
                        && JSGuards.isCallableProxy(arg1Value_x)) {
                        JSObjectProxy_duplicateFound_ = true;
                     }

                     if (!JSObjectProxy_duplicateFound_) {
                        IsJSObjectNode jSObjectProxy_isObjectNode__ = super.insert(this.isObjectNode == null ? IsJSObjectNode.create() : this.isObjectNode);
                        if (jSObjectProxy_isObjectNode__.executeBoolean(arg0Value_)
                           && JSGuards.isJSProxy(arg1Value_x)
                           && JSGuards.isCallableProxy(arg1Value_x)
                           && (state_0 & 128) == 0) {
                           if (this.isObjectNode == null) {
                              IsJSObjectNode jSObjectProxy_isObjectNode___check = super.insert(jSObjectProxy_isObjectNode__);
                              if (jSObjectProxy_isObjectNode___check == null) {
                                 throw new AssertionError(
                                    "Specialization 'doJSObjectProxy(JSDynamicObject, JSDynamicObject, IsJSObjectNode, GetPrototypeNode, GetPrototypeNode, GetPrototypeNode, BranchProfile, BranchProfile, BranchProfile, BranchProfile, BranchProfile, BranchProfile)' contains a shared cache with name 'isObjectNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                                 );
                              }

                              this.isObjectNode = jSObjectProxy_isObjectNode___check;
                           }

                           this.getPrototype1Node = super.insert(this.getPrototype1Node == null ? GetPrototypeNode.create() : this.getPrototype1Node);
                           this.getPrototype2Node = super.insert(this.getPrototype2Node == null ? GetPrototypeNode.create() : this.getPrototype2Node);
                           this.getPrototype3Node = super.insert(this.getPrototype3Node == null ? GetPrototypeNode.create() : this.getPrototype3Node);
                           this.firstTrue = this.firstTrue == null ? BranchProfile.create() : this.firstTrue;
                           this.firstFalse = this.firstFalse == null ? BranchProfile.create() : this.firstFalse;
                           this.need2Hops = this.need2Hops == null ? BranchProfile.create() : this.need2Hops;
                           this.need3Hops = this.need3Hops == null ? BranchProfile.create() : this.need3Hops;
                           this.errorBranch = this.errorBranch == null ? BranchProfile.create() : this.errorBranch;
                           this.invalidPrototypeBranch = this.invalidPrototypeBranch == null ? BranchProfile.create() : this.invalidPrototypeBranch;
                           int var15;
                           this.state_0_ = var15 = state_0 | 128;
                           JSObjectProxy_duplicateFound_ = true;
                        }
                     }

                     if (JSObjectProxy_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        return this.doJSObjectProxy(
                           arg0Value_,
                           arg1Value_x,
                           this.isObjectNode,
                           this.getPrototype1Node,
                           this.getPrototype2Node,
                           this.getPrototype3Node,
                           this.firstTrue,
                           this.firstFalse,
                           this.need2Hops,
                           this.need3Hops,
                           this.errorBranch,
                           this.invalidPrototypeBranch
                        );
                     }
                  }
               }

               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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
         Object[] data = new Object[9];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"doNotCallable", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doIsBound", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.isBound_instanceofNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doForeignObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.foreignPrototypeNode, this.invalidPrototypeBranch, this.ordinaryHasInstance));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"doNotAnObject", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"doNotAnObjectProxyForeign", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.foreignPrototypeNode, this.invalidPrototypeBranch, this.ordinaryHasInstance));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"doNotAnObjectProxyPrimitive", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"doJSObject", null, null};
         if ((state_0 & 64) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(
               Arrays.asList(
                  this.isObjectNode,
                  this.getPrototype1Node,
                  this.getPrototype2Node,
                  this.getPrototype3Node,
                  this.firstTrue,
                  this.firstFalse,
                  this.need2Hops,
                  this.need3Hops,
                  this.errorBranch,
                  this.invalidPrototypeBranch
               )
            );
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         s = new Object[]{"doJSObjectProxy", null, null};
         if ((state_0 & 128) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(
               Arrays.asList(
                  this.isObjectNode,
                  this.getPrototype1Node,
                  this.getPrototype2Node,
                  this.getPrototype3Node,
                  this.firstTrue,
                  this.firstFalse,
                  this.need2Hops,
                  this.need3Hops,
                  this.errorBranch,
                  this.invalidPrototypeBranch
               )
            );
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[8] = s;
         return Introspection.Provider.create(data);
      }

      public static InstanceofNode.OrdinaryHasInstanceNode create(JSContext context) {
         return new InstanceofNodeGen.OrdinaryHasInstanceNodeGen(context);
      }
   }
}
