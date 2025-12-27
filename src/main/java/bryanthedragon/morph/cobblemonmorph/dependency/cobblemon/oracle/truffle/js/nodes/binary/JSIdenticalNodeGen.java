package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
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
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSIdenticalNode.class)
public final class JSIdenticalNodeGen extends JSIdenticalNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int state_1_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private InteropLibrary isNullInterop;
   @Node.Child
   private TruffleString.EqualNode truffleString_equalsNode_;
   @CompilerDirectives.CompilationFinal
   private JSIdenticalNodeGen.NumberNotNumberCachedData numberNotNumberCached_cache;
   @Node.Child
   private JSIdenticalNodeGen.ForeignObject0Data foreignObject0_cache;

   private JSIdenticalNodeGen(JavaScriptNode left, JavaScriptNode right, int type) {
      super(left, right, type);
   }

   @ExplodeLoop
   @Override
   public boolean executeBoolean(Object leftNodeValue, Object rightNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 1) != 0 && leftNodeValue instanceof Integer) {
         int leftNodeValue_ = (Integer)leftNodeValue;
         if (rightNodeValue instanceof Integer) {
            int rightNodeValue_ = (Integer)rightNodeValue;
            return JSIdenticalNode.doInt(leftNodeValue_, rightNodeValue_);
         }
      }

      if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 2013265920) >>> 27, leftNodeValue)) {
         double leftNodeValue_ = JSTypesGen.asImplicitDouble((state_0 & 2013265920) >>> 27, leftNodeValue);
         if (JSTypesGen.isImplicitDouble(this.state_1_ >>> 0, rightNodeValue)) {
            double rightNodeValue_ = JSTypesGen.asImplicitDouble(this.state_1_ >>> 0, rightNodeValue);
            return this.doDouble(leftNodeValue_, rightNodeValue_);
         }
      }

      if ((state_0 & 4) != 0 && leftNodeValue instanceof Boolean) {
         boolean leftNodeValue_ = (Boolean)leftNodeValue;
         if (rightNodeValue instanceof Boolean) {
            boolean rightNodeValue_ = (Boolean)rightNodeValue;
            return JSIdenticalNode.doBoolean(leftNodeValue_, rightNodeValue_);
         }
      }

      if ((state_0 & 24) != 0 && leftNodeValue instanceof BigInt) {
         BigInt leftNodeValue_ = (BigInt)leftNodeValue;
         if ((state_0 & 8) != 0 && rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_ = (BigInt)rightNodeValue;
            return JSIdenticalNode.doBigInt(leftNodeValue_, rightNodeValue_);
         }

         if ((state_0 & 16) != 0 && JSTypesGen.isImplicitDouble(this.state_1_ >>> 0, rightNodeValue)) {
            double rightNodeValue_ = JSTypesGen.asImplicitDouble(this.state_1_ >>> 0, rightNodeValue);
            return JSIdenticalNode.doBigIntDouble(leftNodeValue_, rightNodeValue_);
         }
      }

      if ((state_0 & 32) != 0 && JSTypesGen.isImplicitDouble((state_0 & 2013265920) >>> 27, leftNodeValue)) {
         double leftNodeValue_x = JSTypesGen.asImplicitDouble((state_0 & 2013265920) >>> 27, leftNodeValue);
         if (rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_ = (BigInt)rightNodeValue;
            return JSIdenticalNode.doDoubleBigInt(leftNodeValue_x, rightNodeValue_);
         }
      }

      if ((state_0 & 448) != 0) {
         if ((state_0 & 192) != 0) {
            if ((state_0 & 64) != 0 && JSGuards.isUndefined(leftNodeValue)) {
               return JSIdenticalNode.doUndefinedA(leftNodeValue, rightNodeValue);
            }

            if ((state_0 & 128) != 0 && JSGuards.isUndefined(rightNodeValue)) {
               return JSIdenticalNode.doUndefinedB(leftNodeValue, rightNodeValue);
            }
         }

         if ((state_0 & 256) != 0 && leftNodeValue instanceof JSObject) {
            JSObject leftNodeValue_x = (JSObject)leftNodeValue;
            return JSIdenticalNode.doJSObjectA(leftNodeValue_x, rightNodeValue);
         }
      }

      if ((state_0 & 32256) != 0) {
         if ((state_0 & 512) != 0 && rightNodeValue instanceof JSObject) {
            JSObject rightNodeValue_ = (JSObject)rightNodeValue;
            return JSIdenticalNode.doJSObjectB(leftNodeValue, rightNodeValue_);
         }

         if ((state_0 & 31744) != 0) {
            if ((state_0 & 1024) != 0 && JSGuards.isJSNull(leftNodeValue) && JSGuards.isJSNull(rightNodeValue)) {
               return JSIdenticalNode.doNullNull(leftNodeValue, rightNodeValue);
            }

            if ((state_0 & 2048) != 0 && JSGuards.isJSNull(leftNodeValue) && JSGuards.isUndefined(rightNodeValue)) {
               return JSIdenticalNode.doNullUndefined(leftNodeValue, rightNodeValue);
            }

            if ((state_0 & 4096) != 0 && JSGuards.isUndefined(leftNodeValue) && JSGuards.isJSNull(rightNodeValue)) {
               return JSIdenticalNode.doUndefinedNull(leftNodeValue, rightNodeValue);
            }

            if ((state_0 & 8192) != 0 && JSGuards.isJSNull(leftNodeValue) && !JSRuntime.isNullOrUndefined(rightNodeValue)) {
               return JSIdenticalNode.doNullA(leftNodeValue, rightNodeValue, this.isNullInterop);
            }

            if ((state_0 & 16384) != 0 && !JSRuntime.isNullOrUndefined(leftNodeValue) && JSGuards.isJSNull(rightNodeValue)) {
               return JSIdenticalNode.doNullB(leftNodeValue, rightNodeValue, this.isNullInterop);
            }
         }
      }

      if ((state_0 & 98304) != 0 && leftNodeValue instanceof TruffleString) {
         TruffleString leftNodeValue_x = (TruffleString)leftNodeValue;
         if (rightNodeValue instanceof TruffleString) {
            TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
            if ((state_0 & 32768) != 0 && JSGuards.isReferenceEquals(leftNodeValue_x, rightNodeValue_)) {
               return JSIdenticalNode.doTruffleStringIdentity(leftNodeValue_x, rightNodeValue_);
            }

            if ((state_0 & 65536) != 0) {
               return JSIdenticalNode.doTruffleString(leftNodeValue_x, rightNodeValue_, this.truffleString_equalsNode_);
            }
         }
      }

      if ((state_0 & 131072) != 0 && leftNodeValue instanceof Symbol) {
         Symbol leftNodeValue_x = (Symbol)leftNodeValue;
         if (rightNodeValue instanceof Symbol) {
            Symbol rightNodeValue_x = (Symbol)rightNodeValue;
            return JSIdenticalNode.doSymbol(leftNodeValue_x, rightNodeValue_x);
         }
      }

      if ((state_0 & 8126464) != 0) {
         if ((state_0 & 262144) != 0 && JSGuards.isBoolean(leftNodeValue) != JSGuards.isBoolean(rightNodeValue)) {
            return JSIdenticalNode.doBooleanNotBoolean(leftNodeValue, rightNodeValue);
         }

         if ((state_0 & 524288) != 0 && JSGuards.isSymbol(leftNodeValue) != JSGuards.isSymbol(rightNodeValue)) {
            return JSIdenticalNode.doSymbolNotSymbol(leftNodeValue, rightNodeValue);
         }

         if ((state_0 & 1048576) != 0) {
            for (JSIdenticalNodeGen.NumberNotNumberCachedData s20_ = this.numberNotNumberCached_cache; s20_ != null; s20_ = s20_.next_) {
               if (leftNodeValue.getClass() == s20_.cachedClassA_ && rightNodeValue.getClass() == s20_.cachedClassB_) {
                  assert JSIdenticalNode.isJavaNumberType(s20_.cachedClassA_) != JSIdenticalNode.isJavaNumberType(s20_.cachedClassB_);

                  return JSIdenticalNode.doNumberNotNumberCached(leftNodeValue, rightNodeValue, s20_.cachedClassA_, s20_.cachedClassB_);
               }
            }
         }

         if ((state_0 & 2097152) != 0 && JSRuntime.isJavaNumber(leftNodeValue) != JSRuntime.isJavaNumber(rightNodeValue)) {
            return JSIdenticalNode.doNumberNotNumber(leftNodeValue, rightNodeValue);
         }

         if ((state_0 & 4194304) != 0 && JSGuards.isString(leftNodeValue) != JSGuards.isString(rightNodeValue)) {
            return JSIdenticalNode.doStringNotString(leftNodeValue, rightNodeValue);
         }
      }

      if ((state_0 & 8388608) != 0 && leftNodeValue instanceof Number) {
         Number leftNodeValue_x = (Number)leftNodeValue;
         if (rightNodeValue instanceof Number) {
            Number rightNodeValue_x = (Number)rightNodeValue;
            if (JSRuntime.isJavaNumber(leftNodeValue_x) && JSRuntime.isJavaNumber(rightNodeValue_x)) {
               return this.doNumber(leftNodeValue_x, rightNodeValue_x);
            }
         }
      }

      if ((state_0 & 117440512) != 0) {
         if ((state_0 & 16777216) != 0) {
            for (JSIdenticalNodeGen.ForeignObject0Data s24_ = this.foreignObject0_cache; s24_ != null; s24_ = s24_.next_) {
               if (s24_.aInterop_.accepts(leftNodeValue)
                  && s24_.bInterop_.accepts(rightNodeValue)
                  && JSRuntime.isForeignObject(leftNodeValue)
                  && JSRuntime.isForeignObject(rightNodeValue)) {
                  return JSIdenticalNode.doForeignObject(leftNodeValue, rightNodeValue, s24_.aInterop_, s24_.bInterop_);
               }
            }
         }

         if ((state_0 & 33554432) != 0 && JSRuntime.isForeignObject(leftNodeValue) && JSRuntime.isForeignObject(rightNodeValue)) {
            return this.foreignObject1Boundary(state_0, leftNodeValue, rightNodeValue);
         }

         if ((state_0 & 67108864) != 0 && fallbackGuard_(state_0, leftNodeValue, rightNodeValue)) {
            return JSIdenticalNode.doFallback(leftNodeValue, rightNodeValue);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
   }

   @CompilerDirectives.TruffleBoundary
   private boolean foreignObject1Boundary(int state_0, Object leftNodeValue, Object rightNodeValue) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      boolean var8;
      try {
         InteropLibrary foreignObject1_aInterop__ = INTEROP_LIBRARY_.getUncached(leftNodeValue);
         InteropLibrary foreignObject1_bInterop__ = INTEROP_LIBRARY_.getUncached(rightNodeValue);
         var8 = JSIdenticalNode.doForeignObject(leftNodeValue, rightNodeValue, foreignObject1_aInterop__, foreignObject1_bInterop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var8;
   }

   @Override
   public boolean executeBoolean(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 134217726) == 0 && (state_0 & 134217727) != 0) {
         return this.executeBoolean_int_int0(state_0, frameValue);
      } else if ((state_0 & 134217725) == 0 && (state_0 & 134217727) != 0) {
         return this.executeBoolean_double_double1(state_0, frameValue);
      } else if ((state_0 & 134217723) == 0 && (state_0 & 134217727) != 0) {
         return this.executeBoolean_boolean_boolean2(state_0, frameValue);
      } else if ((state_0 & 134217711) == 0 && (state_0 & 134217727) != 0) {
         return this.executeBoolean_double3(state_0, frameValue);
      } else {
         return (state_0 & 134217695) == 0 && (state_0 & 134217727) != 0
            ? this.executeBoolean_double4(state_0, frameValue)
            : this.executeBoolean_generic5(state_0, frameValue);
      }
   }

   private boolean executeBoolean_int_int0(int state_0, VirtualFrame frameValue) {
      int leftNodeValue_;
      try {
         leftNodeValue_ = super.leftNode.executeInt(frameValue);
      } catch (UnexpectedResultException var7) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var7.getResult(), rightNodeValue);
      }

      int rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeInt(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var6.getResult());
      }

      assert (state_0 & 1) != 0;

      return JSIdenticalNode.doInt(leftNodeValue_, rightNodeValue_);
   }

   private boolean executeBoolean_double_double1(int state_0, VirtualFrame frameValue) {
      long leftNodeValue_long = 0L;
      int leftNodeValue_int = 0;

      double leftNodeValue_;
      try {
         if ((state_0 & 1879048192) == 0 && (state_0 & 134217727) != 0) {
            leftNodeValue_ = super.leftNode.executeDouble(frameValue);
         } else if ((state_0 & 1744830464) == 0 && (state_0 & 134217727) != 0) {
            leftNodeValue_int = super.leftNode.executeInt(frameValue);
            leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
         } else if ((state_0 & 939524096) == 0 && (state_0 & 134217727) != 0) {
            leftNodeValue_long = super.leftNode.executeLong(frameValue);
            leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
         } else {
            Object leftNodeValue__ = super.leftNode.execute(frameValue);
            leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 2013265920) >>> 27, leftNodeValue__);
         }
      } catch (UnexpectedResultException var15) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var15.getResult(), rightNodeValue);
      }

      long rightNodeValue_long = 0L;
      int rightNodeValue_int = 0;

      double rightNodeValue_;
      try {
         if ((this.state_1_ & 14) == 0 && (state_0 & 134217727) != 0) {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         } else if ((this.state_1_ & 13) == 0 && (state_0 & 134217727) != 0) {
            rightNodeValue_int = super.rightNode.executeInt(frameValue);
            rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
         } else if ((this.state_1_ & 7) == 0 && (state_0 & 134217727) != 0) {
            rightNodeValue_long = super.rightNode.executeLong(frameValue);
            rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
         } else {
            Object rightNodeValue__ = super.rightNode.execute(frameValue);
            rightNodeValue_ = JSTypesGen.expectImplicitDouble(this.state_1_ >>> 0, rightNodeValue__);
         }
      } catch (UnexpectedResultException var14) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_0 & 1744830464) == 0 && (state_0 & 134217727) != 0
               ? leftNodeValue_int
               : ((state_0 & 939524096) == 0 && (state_0 & 134217727) != 0 ? leftNodeValue_long : leftNodeValue_),
            var14.getResult()
         );
      }

      assert (state_0 & 2) != 0;

      return this.doDouble(leftNodeValue_, rightNodeValue_);
   }

   private boolean executeBoolean_boolean_boolean2(int state_0, VirtualFrame frameValue) {
      boolean leftNodeValue_;
      try {
         leftNodeValue_ = super.leftNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var7) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var7.getResult(), rightNodeValue);
      }

      boolean rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var6.getResult());
      }

      assert (state_0 & 4) != 0;

      return JSIdenticalNode.doBoolean(leftNodeValue_, rightNodeValue_);
   }

   private boolean executeBoolean_double3(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      long rightNodeValue_long = 0L;
      int rightNodeValue_int = 0;

      double rightNodeValue_;
      try {
         if ((this.state_1_ & 14) == 0 && (state_0 & 134217727) != 0) {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         } else if ((this.state_1_ & 13) == 0 && (state_0 & 134217727) != 0) {
            rightNodeValue_int = super.rightNode.executeInt(frameValue);
            rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
         } else if ((this.state_1_ & 7) == 0 && (state_0 & 134217727) != 0) {
            rightNodeValue_long = super.rightNode.executeLong(frameValue);
            rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
         } else {
            Object rightNodeValue__ = super.rightNode.execute(frameValue);
            rightNodeValue_ = JSTypesGen.expectImplicitDouble(this.state_1_ >>> 0, rightNodeValue__);
         }
      } catch (UnexpectedResultException var10) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var10.getResult());
      }

      assert (state_0 & 16) != 0;

      if (leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
         return JSIdenticalNode.doBigIntDouble(leftNodeValue__, rightNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            leftNodeValue_,
            (this.state_1_ & 13) == 0 && (state_0 & 134217727) != 0
               ? rightNodeValue_int
               : ((this.state_1_ & 7) == 0 && (state_0 & 134217727) != 0 ? rightNodeValue_long : rightNodeValue_)
         );
      }
   }

   private boolean executeBoolean_double4(int state_0, VirtualFrame frameValue) {
      long leftNodeValue_long = 0L;
      int leftNodeValue_int = 0;

      double leftNodeValue_;
      try {
         if ((state_0 & 1879048192) == 0 && (state_0 & 134217727) != 0) {
            leftNodeValue_ = super.leftNode.executeDouble(frameValue);
         } else if ((state_0 & 1744830464) == 0 && (state_0 & 134217727) != 0) {
            leftNodeValue_int = super.leftNode.executeInt(frameValue);
            leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
         } else if ((state_0 & 939524096) == 0 && (state_0 & 134217727) != 0) {
            leftNodeValue_long = super.leftNode.executeLong(frameValue);
            leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
         } else {
            Object leftNodeValue__ = super.leftNode.execute(frameValue);
            leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_0 & 2013265920) >>> 27, leftNodeValue__);
         }
      } catch (UnexpectedResultException var10) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var10.getResult(), rightNodeValue);
      }

      Object rightNodeValue_ = super.rightNode.execute(frameValue);

      assert (state_0 & 32) != 0;

      if (rightNodeValue_ instanceof BigInt) {
         BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
         return JSIdenticalNode.doDoubleBigInt(leftNodeValue_, rightNodeValue__);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_0 & 1744830464) == 0 && (state_0 & 134217727) != 0
               ? leftNodeValue_int
               : ((state_0 & 939524096) == 0 && (state_0 & 134217727) != 0 ? leftNodeValue_long : leftNodeValue_),
            rightNodeValue_
         );
      }
   }

   @CompilerDirectives.TruffleBoundary
   private boolean foreignObject1Boundary0(int state_0, Object leftNodeValue_, Object rightNodeValue_) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      boolean var8;
      try {
         InteropLibrary foreignObject1_aInterop__ = INTEROP_LIBRARY_.getUncached(leftNodeValue_);
         InteropLibrary foreignObject1_bInterop__ = INTEROP_LIBRARY_.getUncached(rightNodeValue_);
         var8 = JSIdenticalNode.doForeignObject(leftNodeValue_, rightNodeValue_, foreignObject1_aInterop__, foreignObject1_bInterop__);
      } finally {
         encapsulating_.set(prev_);
      }

      return var8;
   }

   @ExplodeLoop
   private boolean executeBoolean_generic5(int state_0, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 1) != 0 && leftNodeValue_ instanceof Integer) {
         int leftNodeValue__ = (Integer)leftNodeValue_;
         if (rightNodeValue_ instanceof Integer) {
            int rightNodeValue__ = (Integer)rightNodeValue_;
            return JSIdenticalNode.doInt(leftNodeValue__, rightNodeValue__);
         }
      }

      if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 2013265920) >>> 27, leftNodeValue_)) {
         double leftNodeValue__ = JSTypesGen.asImplicitDouble((state_0 & 2013265920) >>> 27, leftNodeValue_);
         if (JSTypesGen.isImplicitDouble(this.state_1_ >>> 0, rightNodeValue_)) {
            double rightNodeValue__ = JSTypesGen.asImplicitDouble(this.state_1_ >>> 0, rightNodeValue_);
            return this.doDouble(leftNodeValue__, rightNodeValue__);
         }
      }

      if ((state_0 & 4) != 0 && leftNodeValue_ instanceof Boolean) {
         boolean leftNodeValue__ = (Boolean)leftNodeValue_;
         if (rightNodeValue_ instanceof Boolean) {
            boolean rightNodeValue__ = (Boolean)rightNodeValue_;
            return JSIdenticalNode.doBoolean(leftNodeValue__, rightNodeValue__);
         }
      }

      if ((state_0 & 24) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
         if ((state_0 & 8) != 0 && rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
            return JSIdenticalNode.doBigInt(leftNodeValue__, rightNodeValue__);
         }

         if ((state_0 & 16) != 0 && JSTypesGen.isImplicitDouble(this.state_1_ >>> 0, rightNodeValue_)) {
            double rightNodeValue__ = JSTypesGen.asImplicitDouble(this.state_1_ >>> 0, rightNodeValue_);
            return JSIdenticalNode.doBigIntDouble(leftNodeValue__, rightNodeValue__);
         }
      }

      if ((state_0 & 32) != 0 && JSTypesGen.isImplicitDouble((state_0 & 2013265920) >>> 27, leftNodeValue_)) {
         double leftNodeValue__x = JSTypesGen.asImplicitDouble((state_0 & 2013265920) >>> 27, leftNodeValue_);
         if (rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
            return JSIdenticalNode.doDoubleBigInt(leftNodeValue__x, rightNodeValue__);
         }
      }

      if ((state_0 & 448) != 0) {
         if ((state_0 & 192) != 0) {
            if ((state_0 & 64) != 0 && JSGuards.isUndefined(leftNodeValue_)) {
               return JSIdenticalNode.doUndefinedA(leftNodeValue_, rightNodeValue_);
            }

            if ((state_0 & 128) != 0 && JSGuards.isUndefined(rightNodeValue_)) {
               return JSIdenticalNode.doUndefinedB(leftNodeValue_, rightNodeValue_);
            }
         }

         if ((state_0 & 256) != 0 && leftNodeValue_ instanceof JSObject) {
            JSObject leftNodeValue__x = (JSObject)leftNodeValue_;
            return JSIdenticalNode.doJSObjectA(leftNodeValue__x, rightNodeValue_);
         }
      }

      if ((state_0 & 32256) != 0) {
         if ((state_0 & 512) != 0 && rightNodeValue_ instanceof JSObject) {
            JSObject rightNodeValue__ = (JSObject)rightNodeValue_;
            return JSIdenticalNode.doJSObjectB(leftNodeValue_, rightNodeValue__);
         }

         if ((state_0 & 31744) != 0) {
            if ((state_0 & 1024) != 0 && JSGuards.isJSNull(leftNodeValue_) && JSGuards.isJSNull(rightNodeValue_)) {
               return JSIdenticalNode.doNullNull(leftNodeValue_, rightNodeValue_);
            }

            if ((state_0 & 2048) != 0 && JSGuards.isJSNull(leftNodeValue_) && JSGuards.isUndefined(rightNodeValue_)) {
               return JSIdenticalNode.doNullUndefined(leftNodeValue_, rightNodeValue_);
            }

            if ((state_0 & 4096) != 0 && JSGuards.isUndefined(leftNodeValue_) && JSGuards.isJSNull(rightNodeValue_)) {
               return JSIdenticalNode.doUndefinedNull(leftNodeValue_, rightNodeValue_);
            }

            if ((state_0 & 8192) != 0 && JSGuards.isJSNull(leftNodeValue_) && !JSRuntime.isNullOrUndefined(rightNodeValue_)) {
               return JSIdenticalNode.doNullA(leftNodeValue_, rightNodeValue_, this.isNullInterop);
            }

            if ((state_0 & 16384) != 0 && !JSRuntime.isNullOrUndefined(leftNodeValue_) && JSGuards.isJSNull(rightNodeValue_)) {
               return JSIdenticalNode.doNullB(leftNodeValue_, rightNodeValue_, this.isNullInterop);
            }
         }
      }

      if ((state_0 & 98304) != 0 && leftNodeValue_ instanceof TruffleString) {
         TruffleString leftNodeValue__x = (TruffleString)leftNodeValue_;
         if (rightNodeValue_ instanceof TruffleString) {
            TruffleString rightNodeValue__ = (TruffleString)rightNodeValue_;
            if ((state_0 & 32768) != 0 && JSGuards.isReferenceEquals(leftNodeValue__x, rightNodeValue__)) {
               return JSIdenticalNode.doTruffleStringIdentity(leftNodeValue__x, rightNodeValue__);
            }

            if ((state_0 & 65536) != 0) {
               return JSIdenticalNode.doTruffleString(leftNodeValue__x, rightNodeValue__, this.truffleString_equalsNode_);
            }
         }
      }

      if ((state_0 & 131072) != 0 && leftNodeValue_ instanceof Symbol) {
         Symbol leftNodeValue__x = (Symbol)leftNodeValue_;
         if (rightNodeValue_ instanceof Symbol) {
            Symbol rightNodeValue__x = (Symbol)rightNodeValue_;
            return JSIdenticalNode.doSymbol(leftNodeValue__x, rightNodeValue__x);
         }
      }

      if ((state_0 & 8126464) != 0) {
         if ((state_0 & 262144) != 0 && JSGuards.isBoolean(leftNodeValue_) != JSGuards.isBoolean(rightNodeValue_)) {
            return JSIdenticalNode.doBooleanNotBoolean(leftNodeValue_, rightNodeValue_);
         }

         if ((state_0 & 524288) != 0 && JSGuards.isSymbol(leftNodeValue_) != JSGuards.isSymbol(rightNodeValue_)) {
            return JSIdenticalNode.doSymbolNotSymbol(leftNodeValue_, rightNodeValue_);
         }

         if ((state_0 & 1048576) != 0) {
            for (JSIdenticalNodeGen.NumberNotNumberCachedData s20_ = this.numberNotNumberCached_cache; s20_ != null; s20_ = s20_.next_) {
               if (leftNodeValue_.getClass() == s20_.cachedClassA_ && rightNodeValue_.getClass() == s20_.cachedClassB_) {
                  assert JSIdenticalNode.isJavaNumberType(s20_.cachedClassA_) != JSIdenticalNode.isJavaNumberType(s20_.cachedClassB_);

                  return JSIdenticalNode.doNumberNotNumberCached(leftNodeValue_, rightNodeValue_, s20_.cachedClassA_, s20_.cachedClassB_);
               }
            }
         }

         if ((state_0 & 2097152) != 0 && JSRuntime.isJavaNumber(leftNodeValue_) != JSRuntime.isJavaNumber(rightNodeValue_)) {
            return JSIdenticalNode.doNumberNotNumber(leftNodeValue_, rightNodeValue_);
         }

         if ((state_0 & 4194304) != 0 && JSGuards.isString(leftNodeValue_) != JSGuards.isString(rightNodeValue_)) {
            return JSIdenticalNode.doStringNotString(leftNodeValue_, rightNodeValue_);
         }
      }

      if ((state_0 & 8388608) != 0 && leftNodeValue_ instanceof Number) {
         Number leftNodeValue__x = (Number)leftNodeValue_;
         if (rightNodeValue_ instanceof Number) {
            Number rightNodeValue__x = (Number)rightNodeValue_;
            if (JSRuntime.isJavaNumber(leftNodeValue__x) && JSRuntime.isJavaNumber(rightNodeValue__x)) {
               return this.doNumber(leftNodeValue__x, rightNodeValue__x);
            }
         }
      }

      if ((state_0 & 117440512) != 0) {
         if ((state_0 & 16777216) != 0) {
            for (JSIdenticalNodeGen.ForeignObject0Data s24_ = this.foreignObject0_cache; s24_ != null; s24_ = s24_.next_) {
               if (s24_.aInterop_.accepts(leftNodeValue_)
                  && s24_.bInterop_.accepts(rightNodeValue_)
                  && JSRuntime.isForeignObject(leftNodeValue_)
                  && JSRuntime.isForeignObject(rightNodeValue_)) {
                  return JSIdenticalNode.doForeignObject(leftNodeValue_, rightNodeValue_, s24_.aInterop_, s24_.bInterop_);
               }
            }
         }

         if ((state_0 & 33554432) != 0 && JSRuntime.isForeignObject(leftNodeValue_) && JSRuntime.isForeignObject(rightNodeValue_)) {
            return this.foreignObject1Boundary0(state_0, leftNodeValue_, rightNodeValue_);
         }

         if ((state_0 & 67108864) != 0 && fallbackGuard_(state_0, leftNodeValue_, rightNodeValue_)) {
            return JSIdenticalNode.doFallback(leftNodeValue_, rightNodeValue_);
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
         int state_1 = this.state_1_;
         int exclude = this.exclude_;
         if (leftNodeValue instanceof Integer) {
            int leftNodeValue_ = (Integer)leftNodeValue;
            if (rightNodeValue instanceof Integer) {
               int rightNodeValue_ = (Integer)rightNodeValue;
               int var52;
               this.state_0_ = var52 = state_0 | 1;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return JSIdenticalNode.doInt(leftNodeValue_, rightNodeValue_);
            }
         }

         int doubleCast0;
         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
            double leftNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
            int doubleCast1;
            if ((doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
               double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
               state_0 |= doubleCast0 << 27;
               state_1 |= doubleCast1 << 0;
               int var51;
               this.state_0_ = var51 = state_0 | 2;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return this.doDouble(leftNodeValue_, rightNodeValue_);
            }
         }

         if (leftNodeValue instanceof Boolean) {
            boolean leftNodeValue_ = (Boolean)leftNodeValue;
            if (rightNodeValue instanceof Boolean) {
               boolean rightNodeValue_ = (Boolean)rightNodeValue;
               int var49;
               this.state_0_ = var49 = state_0 | 4;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return JSIdenticalNode.doBoolean(leftNodeValue_, rightNodeValue_);
            }
         }

         if (leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_ = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
               BigInt rightNodeValue_ = (BigInt)rightNodeValue;
               int var48;
               this.state_0_ = var48 = state_0 | 8;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return JSIdenticalNode.doBigInt(leftNodeValue_, rightNodeValue_);
            }

            int doubleCast1;
            if ((doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
               double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
               state_1 |= doubleCast1 << 0;
               int var47;
               this.state_0_ = var47 = state_0 | 16;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return JSIdenticalNode.doBigIntDouble(leftNodeValue_, rightNodeValue_);
            }
         }

         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
            double leftNodeValue_x = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
            if (rightNodeValue instanceof BigInt) {
               BigInt rightNodeValue_ = (BigInt)rightNodeValue;
               state_0 |= doubleCast0 << 27;
               int var46;
               this.state_0_ = var46 = state_0 | 32;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return JSIdenticalNode.doDoubleBigInt(leftNodeValue_x, rightNodeValue_);
            }
         }

         if (JSGuards.isUndefined(leftNodeValue)) {
            int var44;
            this.state_0_ = var44 = state_0 | 64;
            this.state_1_ = state_1;
            lock.unlock();
            hasLock = false;
            return JSIdenticalNode.doUndefinedA(leftNodeValue, rightNodeValue);
         } else if (JSGuards.isUndefined(rightNodeValue)) {
            int var43;
            this.state_0_ = var43 = state_0 | 128;
            this.state_1_ = state_1;
            lock.unlock();
            hasLock = false;
            return JSIdenticalNode.doUndefinedB(leftNodeValue, rightNodeValue);
         } else if (leftNodeValue instanceof JSObject) {
            JSObject leftNodeValue_x = (JSObject)leftNodeValue;
            int var42;
            this.state_0_ = var42 = state_0 | 256;
            this.state_1_ = state_1;
            lock.unlock();
            hasLock = false;
            return JSIdenticalNode.doJSObjectA(leftNodeValue_x, rightNodeValue);
         } else if (rightNodeValue instanceof JSObject) {
            JSObject rightNodeValue_ = (JSObject)rightNodeValue;
            int var41;
            this.state_0_ = var41 = state_0 | 512;
            this.state_1_ = state_1;
            lock.unlock();
            hasLock = false;
            return JSIdenticalNode.doJSObjectB(leftNodeValue, rightNodeValue_);
         } else if (JSGuards.isJSNull(leftNodeValue) && JSGuards.isJSNull(rightNodeValue)) {
            int var40;
            this.state_0_ = var40 = state_0 | 1024;
            this.state_1_ = state_1;
            lock.unlock();
            hasLock = false;
            return JSIdenticalNode.doNullNull(leftNodeValue, rightNodeValue);
         } else if (JSGuards.isJSNull(leftNodeValue) && JSGuards.isUndefined(rightNodeValue)) {
            int var39;
            this.state_0_ = var39 = state_0 | 2048;
            this.state_1_ = state_1;
            lock.unlock();
            hasLock = false;
            return JSIdenticalNode.doNullUndefined(leftNodeValue, rightNodeValue);
         } else if (JSGuards.isUndefined(leftNodeValue) && JSGuards.isJSNull(rightNodeValue)) {
            int var38;
            this.state_0_ = var38 = state_0 | 4096;
            this.state_1_ = state_1;
            lock.unlock();
            hasLock = false;
            return JSIdenticalNode.doUndefinedNull(leftNodeValue, rightNodeValue);
         } else if (JSGuards.isJSNull(leftNodeValue) && !JSRuntime.isNullOrUndefined(rightNodeValue)) {
            this.isNullInterop = super.insert(this.isNullInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.isNullInterop);
            int var37;
            this.state_0_ = var37 = state_0 | 8192;
            this.state_1_ = state_1;
            lock.unlock();
            hasLock = false;
            return JSIdenticalNode.doNullA(leftNodeValue, rightNodeValue, this.isNullInterop);
         } else if (!JSRuntime.isNullOrUndefined(leftNodeValue) && JSGuards.isJSNull(rightNodeValue)) {
            this.isNullInterop = super.insert(this.isNullInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.isNullInterop);
            int var36;
            this.state_0_ = var36 = state_0 | 16384;
            this.state_1_ = state_1;
            lock.unlock();
            hasLock = false;
            return JSIdenticalNode.doNullB(leftNodeValue, rightNodeValue, this.isNullInterop);
         } else {
            if (leftNodeValue instanceof TruffleString) {
               TruffleString leftNodeValue_x = (TruffleString)leftNodeValue;
               if (rightNodeValue instanceof TruffleString) {
                  TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
                  if ((exclude & 1) != 0 || !JSGuards.isReferenceEquals(leftNodeValue_x, rightNodeValue_)) {
                     this.truffleString_equalsNode_ = super.insert(TruffleString.EqualNode.create());
                     int var57;
                     this.exclude_ = var57 = exclude | 1;
                     state_0 &= -32769;
                     int var35;
                     this.state_0_ = var35 = state_0 | 65536;
                     this.state_1_ = state_1;
                     lock.unlock();
                     hasLock = false;
                     return JSIdenticalNode.doTruffleString(leftNodeValue_x, rightNodeValue_, this.truffleString_equalsNode_);
                  }

                  int var33;
                  this.state_0_ = var33 = state_0 | 32768;
                  this.state_1_ = state_1;
                  lock.unlock();
                  hasLock = false;
                  return JSIdenticalNode.doTruffleStringIdentity(leftNodeValue_x, rightNodeValue_);
               }
            }

            if (leftNodeValue instanceof Symbol) {
               Symbol leftNodeValue_x = (Symbol)leftNodeValue;
               if (rightNodeValue instanceof Symbol) {
                  Symbol rightNodeValue_ = (Symbol)rightNodeValue;
                  int var32;
                  this.state_0_ = var32 = state_0 | 131072;
                  this.state_1_ = state_1;
                  lock.unlock();
                  hasLock = false;
                  return JSIdenticalNode.doSymbol(leftNodeValue_x, rightNodeValue_);
               }
            }

            if (JSGuards.isBoolean(leftNodeValue) != JSGuards.isBoolean(rightNodeValue)) {
               int var31;
               this.state_0_ = var31 = state_0 | 262144;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return JSIdenticalNode.doBooleanNotBoolean(leftNodeValue, rightNodeValue);
            } else if (JSGuards.isSymbol(leftNodeValue) != JSGuards.isSymbol(rightNodeValue)) {
               int var30;
               this.state_0_ = var30 = state_0 | 524288;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return JSIdenticalNode.doSymbolNotSymbol(leftNodeValue, rightNodeValue);
            } else {
               if ((exclude & 2) == 0) {
                  doubleCast0 = 0;
                  JSIdenticalNodeGen.NumberNotNumberCachedData s20_ = this.numberNotNumberCached_cache;
                  if ((state_0 & 1048576) != 0) {
                     while (s20_ != null) {
                        if (leftNodeValue.getClass() == s20_.cachedClassA_ && rightNodeValue.getClass() == s20_.cachedClassB_) {
                           assert JSIdenticalNode.isJavaNumberType(s20_.cachedClassA_) != JSIdenticalNode.isJavaNumberType(s20_.cachedClassB_);
                           break;
                        }

                        s20_ = s20_.next_;
                        doubleCast0++;
                     }
                  }

                  if (s20_ == null) {
                     Class<?> cachedClassA__ = leftNodeValue.getClass();
                     if (leftNodeValue.getClass() == cachedClassA__) {
                        Class<?> cachedClassB__ = rightNodeValue.getClass();
                        if (rightNodeValue.getClass() == cachedClassB__
                           && JSIdenticalNode.isJavaNumberType(cachedClassA__) != JSIdenticalNode.isJavaNumberType(cachedClassB__)
                           && doubleCast0 < 3) {
                           s20_ = new JSIdenticalNodeGen.NumberNotNumberCachedData(this.numberNotNumberCached_cache);
                           s20_.cachedClassA_ = cachedClassA__;
                           s20_.cachedClassB_ = cachedClassB__;
                           VarHandle.storeStoreFence();
                           this.numberNotNumberCached_cache = s20_;
                           this.state_0_ = state_0 |= 1048576;
                           this.state_1_ = state_1;
                        }
                     }
                  }

                  if (s20_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return JSIdenticalNode.doNumberNotNumberCached(leftNodeValue, rightNodeValue, s20_.cachedClassA_, s20_.cachedClassB_);
                  }
               }

               if (JSRuntime.isJavaNumber(leftNodeValue) != JSRuntime.isJavaNumber(rightNodeValue)) {
                  int var56;
                  this.exclude_ = var56 = exclude | 2;
                  this.numberNotNumberCached_cache = null;
                  state_0 &= -1048577;
                  int var29;
                  this.state_0_ = var29 = state_0 | 2097152;
                  this.state_1_ = state_1;
                  lock.unlock();
                  hasLock = false;
                  return JSIdenticalNode.doNumberNotNumber(leftNodeValue, rightNodeValue);
               } else if (JSGuards.isString(leftNodeValue) != JSGuards.isString(rightNodeValue)) {
                  int var27;
                  this.state_0_ = var27 = state_0 | 4194304;
                  this.state_1_ = state_1;
                  lock.unlock();
                  hasLock = false;
                  return JSIdenticalNode.doStringNotString(leftNodeValue, rightNodeValue);
               } else {
                  if (leftNodeValue instanceof Number) {
                     Number leftNodeValue_x = (Number)leftNodeValue;
                     if (rightNodeValue instanceof Number) {
                        Number rightNodeValue_ = (Number)rightNodeValue;
                        if (JSRuntime.isJavaNumber(leftNodeValue_x) && JSRuntime.isJavaNumber(rightNodeValue_)) {
                           int var26;
                           this.state_0_ = var26 = state_0 | 8388608;
                           this.state_1_ = state_1;
                           lock.unlock();
                           hasLock = false;
                           return this.doNumber(leftNodeValue_x, rightNodeValue_);
                        }
                     }
                  }

                  if ((exclude & 4) == 0) {
                     doubleCast0 = 0;
                     JSIdenticalNodeGen.ForeignObject0Data s24_ = this.foreignObject0_cache;
                     if ((state_0 & 16777216) != 0) {
                        while (
                           s24_ != null
                              && (
                                 !s24_.aInterop_.accepts(leftNodeValue)
                                    || !s24_.bInterop_.accepts(rightNodeValue)
                                    || !JSRuntime.isForeignObject(leftNodeValue)
                                    || !JSRuntime.isForeignObject(rightNodeValue)
                              )
                        ) {
                           s24_ = s24_.next_;
                           doubleCast0++;
                        }
                     }

                     if (s24_ == null && JSRuntime.isForeignObject(leftNodeValue) && JSRuntime.isForeignObject(rightNodeValue) && doubleCast0 < 5) {
                        s24_ = super.insert(new JSIdenticalNodeGen.ForeignObject0Data(this.foreignObject0_cache));
                        s24_.aInterop_ = s24_.insertAccessor(INTEROP_LIBRARY_.create(leftNodeValue));
                        s24_.bInterop_ = s24_.insertAccessor(INTEROP_LIBRARY_.create(rightNodeValue));
                        VarHandle.storeStoreFence();
                        this.foreignObject0_cache = s24_;
                        this.state_0_ = state_0 |= 16777216;
                        this.state_1_ = state_1;
                     }

                     if (s24_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return JSIdenticalNode.doForeignObject(leftNodeValue, rightNodeValue, s24_.aInterop_, s24_.bInterop_);
                     }
                  }

                  InteropLibrary foreignObject1_bInterop__ = null;
                  InteropLibrary foreignObject1_aInterop__ = null;
                  EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                  Node prev_ = encapsulating_.set(this);

                  try {
                     if (JSRuntime.isForeignObject(leftNodeValue) && JSRuntime.isForeignObject(rightNodeValue)) {
                        foreignObject1_aInterop__ = INTEROP_LIBRARY_.getUncached(leftNodeValue);
                        InteropLibrary var69 = INTEROP_LIBRARY_.getUncached(rightNodeValue);
                        int var55;
                        this.exclude_ = var55 = exclude | 4;
                        this.foreignObject0_cache = null;
                        state_0 &= -16777217;
                        int var25;
                        this.state_0_ = var25 = state_0 | 33554432;
                        this.state_1_ = state_1;
                        lock.unlock();
                        hasLock = false;
                        return JSIdenticalNode.doForeignObject(leftNodeValue, rightNodeValue, foreignObject1_aInterop__, var69);
                     }
                  } finally {
                     encapsulating_.set(prev_);
                  }

                  int var23;
                  this.state_0_ = var23 = state_0 | 67108864;
                  this.state_1_ = state_1;
                  lock.unlock();
                  hasLock = false;
                  return JSIdenticalNode.doFallback(leftNodeValue, rightNodeValue);
               }
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
      if ((state_0 & 134217727) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         int counter = 0;
         counter += Integer.bitCount(state_0 & 134217727);
         if (counter == 1) {
            JSIdenticalNodeGen.NumberNotNumberCachedData s20_ = this.numberNotNumberCached_cache;
            JSIdenticalNodeGen.ForeignObject0Data s24_ = this.foreignObject0_cache;
            if ((s20_ == null || s20_.next_ == null) && (s24_ == null || s24_.next_ == null)) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[28];
      data[0] = 0;
      int state_0 = this.state_0_;
      int state_1 = this.state_1_;
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
      s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doBigIntDouble", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doDoubleBigInt", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doUndefinedA", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doUndefinedB", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doJSObjectA", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doJSObjectB", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doNullNull", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"doNullUndefined", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[12] = s;
      s = new Object[]{"doUndefinedNull", null, null};
      if ((state_0 & 4096) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[13] = s;
      s = new Object[]{"doNullA", null, null};
      if ((state_0 & 8192) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.isNullInterop));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[14] = s;
      s = new Object[]{"doNullB", null, null};
      if ((state_0 & 16384) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.isNullInterop));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[15] = s;
      s = new Object[]{"doTruffleStringIdentity", null, null};
      if ((state_0 & 32768) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[16] = s;
      s = new Object[]{"doTruffleString", null, null};
      if ((state_0 & 65536) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.truffleString_equalsNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[17] = s;
      s = new Object[]{"doSymbol", null, null};
      if ((state_0 & 131072) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[18] = s;
      s = new Object[]{"doBooleanNotBoolean", null, null};
      if ((state_0 & 262144) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[19] = s;
      s = new Object[]{"doSymbolNotSymbol", null, null};
      if ((state_0 & 524288) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[20] = s;
      s = new Object[]{"doNumberNotNumberCached", null, null};
      if ((state_0 & 1048576) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSIdenticalNodeGen.NumberNotNumberCachedData s20_ = this.numberNotNumberCached_cache; s20_ != null; s20_ = s20_.next_) {
            cached.add(Arrays.asList(s20_.cachedClassA_, s20_.cachedClassB_));
         }

         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[21] = s;
      s = new Object[]{"doNumberNotNumber", null, null};
      if ((state_0 & 2097152) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[22] = s;
      s = new Object[]{"doStringNotString", null, null};
      if ((state_0 & 4194304) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[23] = s;
      s = new Object[]{"doNumber", null, null};
      if ((state_0 & 8388608) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[24] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 16777216) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (JSIdenticalNodeGen.ForeignObject0Data s24_ = this.foreignObject0_cache; s24_ != null; s24_ = s24_.next_) {
            cached.add(Arrays.asList(s24_.aInterop_, s24_.bInterop_));
         }

         s[2] = cached;
      } else if ((exclude & 4) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[25] = s;
      s = new Object[]{"doForeignObject", null, null};
      if ((state_0 & 33554432) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList());
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[26] = s;
      s = new Object[]{"doFallback", null, null};
      if ((state_0 & 67108864) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[27] = s;
      return Introspection.Provider.create(data);
   }

   private static boolean fallbackGuard_(int state_0, Object leftNodeValue, Object rightNodeValue) {
      if (JSTypesGen.isImplicitDouble(leftNodeValue) && JSTypesGen.isImplicitDouble(rightNodeValue)) {
         return false;
      } else if ((state_0 & 4) == 0 && leftNodeValue instanceof Boolean && rightNodeValue instanceof Boolean) {
         return false;
      } else {
         if (leftNodeValue instanceof BigInt) {
            if ((state_0 & 8) == 0 && rightNodeValue instanceof BigInt) {
               return false;
            }

            if (JSTypesGen.isImplicitDouble(rightNodeValue)) {
               return false;
            }
         }

         if (JSTypesGen.isImplicitDouble(leftNodeValue) && rightNodeValue instanceof BigInt) {
            return false;
         } else if ((state_0 & 64) == 0 && JSGuards.isUndefined(leftNodeValue)) {
            return false;
         } else if ((state_0 & 128) == 0 && JSGuards.isUndefined(rightNodeValue)) {
            return false;
         } else if ((state_0 & 256) == 0 && leftNodeValue instanceof JSObject) {
            return false;
         } else if ((state_0 & 512) == 0 && rightNodeValue instanceof JSObject) {
            return false;
         } else if ((state_0 & 1024) == 0 && JSGuards.isJSNull(leftNodeValue) && JSGuards.isJSNull(rightNodeValue)) {
            return false;
         } else if ((state_0 & 2048) == 0 && JSGuards.isJSNull(leftNodeValue) && JSGuards.isUndefined(rightNodeValue)) {
            return false;
         } else if ((state_0 & 4096) == 0 && JSGuards.isUndefined(leftNodeValue) && JSGuards.isJSNull(rightNodeValue)) {
            return false;
         } else if ((state_0 & 8192) == 0 && JSGuards.isJSNull(leftNodeValue) && !JSRuntime.isNullOrUndefined(rightNodeValue)) {
            return false;
         } else if ((state_0 & 16384) == 0 && !JSRuntime.isNullOrUndefined(leftNodeValue) && JSGuards.isJSNull(rightNodeValue)) {
            return false;
         } else if ((state_0 & 65536) == 0 && leftNodeValue instanceof TruffleString && rightNodeValue instanceof TruffleString) {
            return false;
         } else if ((state_0 & 131072) == 0 && leftNodeValue instanceof Symbol && rightNodeValue instanceof Symbol) {
            return false;
         } else if ((state_0 & 262144) == 0 && JSGuards.isBoolean(leftNodeValue) != JSGuards.isBoolean(rightNodeValue)) {
            return false;
         } else if ((state_0 & 524288) == 0 && JSGuards.isSymbol(leftNodeValue) != JSGuards.isSymbol(rightNodeValue)) {
            return false;
         } else if ((state_0 & 2097152) == 0 && JSRuntime.isJavaNumber(leftNodeValue) != JSRuntime.isJavaNumber(rightNodeValue)) {
            return false;
         } else if ((state_0 & 4194304) == 0 && JSGuards.isString(leftNodeValue) != JSGuards.isString(rightNodeValue)) {
            return false;
         } else {
            if (leftNodeValue instanceof Number && rightNodeValue instanceof Number) {
               Number leftNodeValue_ = (Number)leftNodeValue;
               if (JSRuntime.isJavaNumber(leftNodeValue_)) {
                  Number rightNodeValue_ = (Number)rightNodeValue;
                  if (JSRuntime.isJavaNumber(rightNodeValue_)) {
                     return false;
                  }
               }
            }

            return (state_0 & 33554432) != 0 || !JSRuntime.isForeignObject(leftNodeValue) || !JSRuntime.isForeignObject(rightNodeValue);
         }
      }
   }

   public static JSIdenticalNode create(JavaScriptNode left, JavaScriptNode right, int type) {
      return new JSIdenticalNodeGen(left, right, type);
   }

   @GeneratedBy(JSIdenticalNode.class)
   private static final class ForeignObject0Data extends Node {
      @Node.Child
      JSIdenticalNodeGen.ForeignObject0Data next_;
      @Node.Child
      InteropLibrary aInterop_;
      @Node.Child
      InteropLibrary bInterop_;

      ForeignObject0Data(JSIdenticalNodeGen.ForeignObject0Data next_) {
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

   @GeneratedBy(JSIdenticalNode.class)
   private static final class NumberNotNumberCachedData {
      @CompilerDirectives.CompilationFinal
      JSIdenticalNodeGen.NumberNotNumberCachedData next_;
      @CompilerDirectives.CompilationFinal
      Class<?> cachedClassA_;
      @CompilerDirectives.CompilationFinal
      Class<?> cachedClassB_;

      NumberNotNumberCachedData(JSIdenticalNodeGen.NumberNotNumberCachedData next_) {
         this.next_ = next_;
      }
   }
}
