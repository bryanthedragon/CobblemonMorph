package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.cast.JSToNumericNode;
import com.oracle.truffle.js.nodes.cast.JSToOperandNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.builtins.JSOverloadedOperatorsObject;
import com.oracle.truffle.js.runtime.objects.OperatorSet;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSOverloadedBinaryNode.class)
public final class JSOverloadedBinaryNodeGen extends JSOverloadedBinaryNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSOverloadedBinaryNodeGen.ToOperandGenericData toOperandGeneric_cache;
   @Node.Child
   private JSOverloadedBinaryNodeGen.ToOperandAdditionData toOperandAddition_cache;
   @Node.Child
   private JSOverloadedBinaryNodeGen.ToNumericOperandData toNumericOperand_cache;

   private JSOverloadedBinaryNodeGen(TruffleString overloadedOperatorName, boolean numeric, JSToPrimitiveNode.Hint hint, boolean leftToRight) {
      super(overloadedOperatorName, numeric, hint, leftToRight);
   }

   @Override
   public Object execute(Object arg0Value, Object arg1Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0) {
            JSOverloadedBinaryNodeGen.ToOperandGenericData s0_ = this.toOperandGeneric_cache;
            if (s0_ != null) {
               assert !this.isNumeric();

               assert !this.isAddition();

               return this.doToOperandGeneric(arg0Value, arg1Value, s0_.toOperandLeftNode_, s0_.toOperandRightNode_, s0_.dispatchBinaryOperatorNode_);
            }
         }

         if ((state_0 & 2) != 0) {
            JSOverloadedBinaryNodeGen.ToOperandAdditionData s1_ = this.toOperandAddition_cache;
            if (s1_ != null) {
               assert !this.isNumeric();

               assert this.isAddition();

               return this.doToOperandAddition(
                  arg0Value,
                  arg1Value,
                  s1_.toOperandLeftNode_,
                  s1_.toOperandRightNode_,
                  s1_.dispatchBinaryOperatorNode_,
                  s1_.toStringLeftNode_,
                  s1_.toStringRightNode_,
                  s1_.leftStringProfile_,
                  s1_.rightStringProfile_,
                  s1_.addNode_
               );
            }
         }

         if ((state_0 & 4) != 0) {
            JSOverloadedBinaryNodeGen.ToNumericOperandData s2_ = this.toNumericOperand_cache;
            if (s2_ != null) {
               assert this.isNumeric();

               return this.doToNumericOperand(
                  arg0Value, arg1Value, s2_.toNumericOperandLeftNode_, s2_.toNumericOperandRightNode_, s2_.dispatchBinaryOperatorNode_
               );
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value);
   }

   private Object executeAndSpecialize(Object arg0Value, Object arg1Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Object var7;
      try {
         int state_0 = this.state_0_;
         if (this.isNumeric() || this.isAddition()) {
            if (this.isNumeric() || !this.isAddition()) {
               if (!this.isNumeric()) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
               }

               JSOverloadedBinaryNodeGen.ToNumericOperandData s2_ = super.insert(new JSOverloadedBinaryNodeGen.ToNumericOperandData());
               s2_.toNumericOperandLeftNode_ = s2_.insertAccessor(JSToNumericNode.create(true));
               s2_.toNumericOperandRightNode_ = s2_.insertAccessor(JSToNumericNode.create(true));
               s2_.dispatchBinaryOperatorNode_ = s2_.insertAccessor(JSOverloadedBinaryNode.DispatchBinaryOperatorNode.create(this.getOverloadedOperatorName()));
               VarHandle.storeStoreFence();
               this.toNumericOperand_cache = s2_;
               int var13;
               this.state_0_ = var13 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doToNumericOperand(
                  arg0Value, arg1Value, s2_.toNumericOperandLeftNode_, s2_.toNumericOperandRightNode_, s2_.dispatchBinaryOperatorNode_
               );
            }

            JSOverloadedBinaryNodeGen.ToOperandAdditionData s1_ = super.insert(new JSOverloadedBinaryNodeGen.ToOperandAdditionData());
            s1_.toOperandLeftNode_ = s1_.insertAccessor(JSToOperandNode.create(this.getHint()));
            s1_.toOperandRightNode_ = s1_.insertAccessor(JSToOperandNode.create(this.getHint()));
            s1_.dispatchBinaryOperatorNode_ = s1_.insertAccessor(JSOverloadedBinaryNode.DispatchBinaryOperatorNode.create(this.getOverloadedOperatorName()));
            s1_.toStringLeftNode_ = s1_.insertAccessor(JSToStringNode.create());
            s1_.toStringRightNode_ = s1_.insertAccessor(JSToStringNode.create());
            s1_.leftStringProfile_ = ConditionProfile.createBinaryProfile();
            s1_.rightStringProfile_ = ConditionProfile.createBinaryProfile();
            s1_.addNode_ = s1_.insertAccessor(JSAddNode.createUnoptimized());
            VarHandle.storeStoreFence();
            this.toOperandAddition_cache = s1_;
            int var12;
            this.state_0_ = var12 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doToOperandAddition(
               arg0Value,
               arg1Value,
               s1_.toOperandLeftNode_,
               s1_.toOperandRightNode_,
               s1_.dispatchBinaryOperatorNode_,
               s1_.toStringLeftNode_,
               s1_.toStringRightNode_,
               s1_.leftStringProfile_,
               s1_.rightStringProfile_,
               s1_.addNode_
            );
         }

         JSOverloadedBinaryNodeGen.ToOperandGenericData s0_ = super.insert(new JSOverloadedBinaryNodeGen.ToOperandGenericData());
         s0_.toOperandLeftNode_ = s0_.insertAccessor(JSToOperandNode.create(this.getHint(), !this.isEquality()));
         s0_.toOperandRightNode_ = s0_.insertAccessor(JSToOperandNode.create(this.getHint(), !this.isEquality()));
         s0_.dispatchBinaryOperatorNode_ = s0_.insertAccessor(JSOverloadedBinaryNode.DispatchBinaryOperatorNode.create(this.getOverloadedOperatorName()));
         VarHandle.storeStoreFence();
         this.toOperandGeneric_cache = s0_;
         int var11;
         this.state_0_ = var11 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var7 = this.doToOperandGeneric(arg0Value, arg1Value, s0_.toOperandLeftNode_, s0_.toOperandRightNode_, s0_.dispatchBinaryOperatorNode_);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }

      return var7;
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
      Object[] data = new Object[4];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doToOperandGeneric", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSOverloadedBinaryNodeGen.ToOperandGenericData s0_ = this.toOperandGeneric_cache;
         if (s0_ != null) {
            cached.add(Arrays.asList(s0_.toOperandLeftNode_, s0_.toOperandRightNode_, s0_.dispatchBinaryOperatorNode_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doToOperandAddition", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSOverloadedBinaryNodeGen.ToOperandAdditionData s1_ = this.toOperandAddition_cache;
         if (s1_ != null) {
            cached.add(
               Arrays.asList(
                  s1_.toOperandLeftNode_,
                  s1_.toOperandRightNode_,
                  s1_.dispatchBinaryOperatorNode_,
                  s1_.toStringLeftNode_,
                  s1_.toStringRightNode_,
                  s1_.leftStringProfile_,
                  s1_.rightStringProfile_,
                  s1_.addNode_
               )
            );
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doToNumericOperand", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSOverloadedBinaryNodeGen.ToNumericOperandData s2_ = this.toNumericOperand_cache;
         if (s2_ != null) {
            cached.add(Arrays.asList(s2_.toNumericOperandLeftNode_, s2_.toNumericOperandRightNode_, s2_.dispatchBinaryOperatorNode_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      return Introspection.Provider.create(data);
   }

   public static JSOverloadedBinaryNode create(TruffleString overloadedOperatorName, boolean numeric, JSToPrimitiveNode.Hint hint, boolean leftToRight) {
      return new JSOverloadedBinaryNodeGen(overloadedOperatorName, numeric, hint, leftToRight);
   }

   @GeneratedBy(JSOverloadedBinaryNode.DispatchBinaryOperatorNode.class)
   public static final class DispatchBinaryOperatorNodeGen extends JSOverloadedBinaryNode.DispatchBinaryOperatorNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedOverloadedData overloadedOverloaded_cache;
      @Node.Child
      private JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedNumberData overloadedNumber_cache;
      @Node.Child
      private JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedBigIntData overloadedBigInt_cache;
      @Node.Child
      private JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedStringData overloadedString_cache;
      @Node.Child
      private JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.NumberOverloadedData numberOverloaded_cache;
      @Node.Child
      private JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.BigIntOverloadedData bigIntOverloaded_cache;
      @Node.Child
      private JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.StringOverloadedData stringOverloaded_cache;
      @Node.Child
      private JSFunctionCallNode generic_callNode_;

      private DispatchBinaryOperatorNodeGen(TruffleString overloadedOperatorName) {
         super(overloadedOperatorName);
      }

      @ExplodeLoop
      @Override
      protected Object execute(Object arg0Value, Object arg1Value) {
         int state_0 = this.state_0_;
         if ((state_0 & 31) != 0 && arg0Value instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject arg0Value_ = (JSOverloadedOperatorsObject)arg0Value;
            if ((state_0 & 1) != 0 && arg1Value instanceof JSOverloadedOperatorsObject) {
               JSOverloadedOperatorsObject arg1Value_ = (JSOverloadedOperatorsObject)arg1Value;

               for (JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedOverloadedData s0_ = this.overloadedOverloaded_cache;
                  s0_ != null;
                  s0_ = s0_.next_
               ) {
                  if (arg0Value_.matchesOperatorCounter(s0_.leftOperatorCounter_) && arg1Value_.matchesOperatorCounter(s0_.rightOperatorCounter_)) {
                     return this.doOverloadedOverloaded(
                        arg0Value_, arg1Value_, s0_.leftOperatorCounter_, s0_.rightOperatorCounter_, s0_.operatorImplementation_, s0_.callNode_
                     );
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               for (JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedNumberData s1_ = this.overloadedNumber_cache;
                  s1_ != null;
                  s1_ = s1_.next_
               ) {
                  if (arg0Value_.matchesOperatorCounter(s1_.leftOperatorCounter_) && JSGuards.isNumber(arg1Value)) {
                     return this.doOverloadedNumber(arg0Value_, arg1Value, s1_.leftOperatorCounter_, s1_.operatorImplementation_, s1_.callNode_);
                  }
               }
            }

            if ((state_0 & 4) != 0 && arg1Value instanceof BigInt) {
               BigInt arg1Value_ = (BigInt)arg1Value;

               for (JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedBigIntData s2_ = this.overloadedBigInt_cache;
                  s2_ != null;
                  s2_ = s2_.next_
               ) {
                  if (arg0Value_.matchesOperatorCounter(s2_.leftOperatorCounter_)) {
                     return this.doOverloadedBigInt(arg0Value_, arg1Value_, s2_.leftOperatorCounter_, s2_.operatorImplementation_, s2_.callNode_);
                  }
               }
            }

            if ((state_0 & 24) != 0) {
               if ((state_0 & 8) != 0) {
                  for (JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedStringData s3_ = this.overloadedString_cache;
                     s3_ != null;
                     s3_ = s3_.next_
                  ) {
                     if (arg0Value_.matchesOperatorCounter(s3_.leftOperatorCounter_) && JSGuards.isString(arg1Value)) {
                        assert !this.isAddition();

                        return this.doOverloadedString(arg0Value_, arg1Value, s3_.leftOperatorCounter_, s3_.operatorImplementation_, s3_.callNode_);
                     }
                  }
               }

               if ((state_0 & 16) != 0 && JSGuards.isNullOrUndefined(arg1Value)) {
                  return this.doOverloadedNullish(arg0Value_, arg1Value);
               }
            }
         }

         if ((state_0 & 480) != 0 && arg1Value instanceof JSOverloadedOperatorsObject) {
            JSOverloadedOperatorsObject arg1Value_ = (JSOverloadedOperatorsObject)arg1Value;
            if ((state_0 & 32) != 0) {
               for (JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.NumberOverloadedData s5_ = this.numberOverloaded_cache;
                  s5_ != null;
                  s5_ = s5_.next_
               ) {
                  if (arg1Value_.matchesOperatorCounter(s5_.rightOperatorCounter_) && JSGuards.isNumber(arg0Value)) {
                     return this.doNumberOverloaded(arg0Value, arg1Value_, s5_.rightOperatorCounter_, s5_.operatorImplementation_, s5_.callNode_);
                  }
               }
            }

            if ((state_0 & 64) != 0 && arg0Value instanceof BigInt) {
               BigInt arg0Value_x = (BigInt)arg0Value;

               for (JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.BigIntOverloadedData s6_ = this.bigIntOverloaded_cache;
                  s6_ != null;
                  s6_ = s6_.next_
               ) {
                  if (arg1Value_.matchesOperatorCounter(s6_.rightOperatorCounter_)) {
                     return this.doBigIntOverloaded(arg0Value_x, arg1Value_, s6_.rightOperatorCounter_, s6_.operatorImplementation_, s6_.callNode_);
                  }
               }
            }

            if ((state_0 & 384) != 0) {
               if ((state_0 & 128) != 0) {
                  for (JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.StringOverloadedData s7_ = this.stringOverloaded_cache;
                     s7_ != null;
                     s7_ = s7_.next_
                  ) {
                     if (arg1Value_.matchesOperatorCounter(s7_.rightOperatorCounter_) && JSGuards.isString(arg0Value)) {
                        assert !this.isAddition();

                        return this.doStringOverloaded(arg0Value, arg1Value_, s7_.rightOperatorCounter_, s7_.operatorImplementation_, s7_.callNode_);
                     }
                  }
               }

               if ((state_0 & 256) != 0 && JSGuards.isNullOrUndefined(arg0Value)) {
                  return this.doNullishOverloaded(arg0Value, arg1Value_);
               }
            }
         }

         if ((state_0 & 512) != 0) {
            return this.doGeneric(arg0Value, arg1Value, this.generic_callNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arg0Value, arg1Value);
         }
      }

      private Object executeAndSpecialize(Object arg0Value, Object arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            int oldState_0 = state_0;

            try {
               if (arg0Value instanceof JSOverloadedOperatorsObject) {
                  JSOverloadedOperatorsObject arg0Value_ = (JSOverloadedOperatorsObject)arg0Value;
                  if ((exclude & 1) == 0 && arg1Value instanceof JSOverloadedOperatorsObject) {
                     JSOverloadedOperatorsObject arg1Value_ = (JSOverloadedOperatorsObject)arg1Value;
                     int count0_ = 0;
                     JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedOverloadedData s0_ = this.overloadedOverloaded_cache;
                     if ((state_0 & 1) != 0) {
                        while (
                           s0_ != null
                              && (!arg0Value_.matchesOperatorCounter(s0_.leftOperatorCounter_) || !arg1Value_.matchesOperatorCounter(s0_.rightOperatorCounter_))
                        ) {
                           s0_ = s0_.next_;
                           count0_++;
                        }
                     }

                     if (s0_ == null) {
                        int leftOperatorCounter__ = arg0Value_.getOperatorCounter();
                        if (arg0Value_.matchesOperatorCounter(leftOperatorCounter__)) {
                           int rightOperatorCounter__ = arg1Value_.getOperatorCounter();
                           if (arg1Value_.matchesOperatorCounter(rightOperatorCounter__) && count0_ < 3) {
                              s0_ = super.insert(
                                 new JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedOverloadedData(this.overloadedOverloaded_cache)
                              );
                              s0_.leftOperatorCounter_ = leftOperatorCounter__;
                              s0_.rightOperatorCounter_ = rightOperatorCounter__;
                              s0_.operatorImplementation_ = OperatorSet.getOperatorImplementation(arg0Value_, arg1Value_, this.getOverloadedOperatorName());
                              s0_.callNode_ = s0_.insertAccessor(JSFunctionCallNode.createCall());
                              VarHandle.storeStoreFence();
                              this.overloadedOverloaded_cache = s0_;
                              this.state_0_ = state_0 |= 1;
                           }
                        }
                     }

                     if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.doOverloadedOverloaded(
                           arg0Value_, arg1Value_, s0_.leftOperatorCounter_, s0_.rightOperatorCounter_, s0_.operatorImplementation_, s0_.callNode_
                        );
                     }
                  }

                  if ((exclude & 2) == 0) {
                     int count1_ = 0;
                     JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedNumberData s1_ = this.overloadedNumber_cache;
                     if ((state_0 & 2) != 0) {
                        while (s1_ != null && (!arg0Value_.matchesOperatorCounter(s1_.leftOperatorCounter_) || !JSGuards.isNumber(arg1Value))) {
                           s1_ = s1_.next_;
                           count1_++;
                        }
                     }

                     if (s1_ == null) {
                        int leftOperatorCounter__1 = arg0Value_.getOperatorCounter();
                        if (arg0Value_.matchesOperatorCounter(leftOperatorCounter__1) && JSGuards.isNumber(arg1Value) && count1_ < 3) {
                           s1_ = super.insert(new JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedNumberData(this.overloadedNumber_cache));
                           s1_.leftOperatorCounter_ = leftOperatorCounter__1;
                           s1_.operatorImplementation_ = OperatorSet.getOperatorImplementation(arg0Value_, arg1Value, this.getOverloadedOperatorName());
                           s1_.callNode_ = s1_.insertAccessor(JSFunctionCallNode.createCall());
                           VarHandle.storeStoreFence();
                           this.overloadedNumber_cache = s1_;
                           this.state_0_ = state_0 |= 2;
                        }
                     }

                     if (s1_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.doOverloadedNumber(arg0Value_, arg1Value, s1_.leftOperatorCounter_, s1_.operatorImplementation_, s1_.callNode_);
                     }
                  }

                  if ((exclude & 4) == 0 && arg1Value instanceof BigInt) {
                     BigInt arg1Value_x = (BigInt)arg1Value;
                     int count2_ = 0;
                     JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedBigIntData s2_ = this.overloadedBigInt_cache;
                     if ((state_0 & 4) != 0) {
                        while (s2_ != null && !arg0Value_.matchesOperatorCounter(s2_.leftOperatorCounter_)) {
                           s2_ = s2_.next_;
                           count2_++;
                        }
                     }

                     if (s2_ == null) {
                        int leftOperatorCounter__2 = arg0Value_.getOperatorCounter();
                        if (arg0Value_.matchesOperatorCounter(leftOperatorCounter__2) && count2_ < 3) {
                           s2_ = super.insert(new JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedBigIntData(this.overloadedBigInt_cache));
                           s2_.leftOperatorCounter_ = leftOperatorCounter__2;
                           s2_.operatorImplementation_ = OperatorSet.getOperatorImplementation(arg0Value_, arg1Value_x, this.getOverloadedOperatorName());
                           s2_.callNode_ = s2_.insertAccessor(JSFunctionCallNode.createCall());
                           VarHandle.storeStoreFence();
                           this.overloadedBigInt_cache = s2_;
                           this.state_0_ = state_0 |= 4;
                        }
                     }

                     if (s2_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.doOverloadedBigInt(arg0Value_, arg1Value_x, s2_.leftOperatorCounter_, s2_.operatorImplementation_, s2_.callNode_);
                     }
                  }

                  if ((exclude & 8) == 0) {
                     int count3_ = 0;
                     JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedStringData s3_ = this.overloadedString_cache;
                     if ((state_0 & 8) != 0) {
                        while (s3_ != null) {
                           if (arg0Value_.matchesOperatorCounter(s3_.leftOperatorCounter_) && JSGuards.isString(arg1Value)) {
                              assert !this.isAddition();
                              break;
                           }

                           s3_ = s3_.next_;
                           count3_++;
                        }
                     }

                     if (s3_ == null) {
                        int leftOperatorCounter__3 = arg0Value_.getOperatorCounter();
                        if (arg0Value_.matchesOperatorCounter(leftOperatorCounter__3) && JSGuards.isString(arg1Value) && !this.isAddition() && count3_ < 3) {
                           s3_ = super.insert(new JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedStringData(this.overloadedString_cache));
                           s3_.leftOperatorCounter_ = leftOperatorCounter__3;
                           s3_.operatorImplementation_ = OperatorSet.getOperatorImplementation(arg0Value_, arg1Value, this.getOverloadedOperatorName());
                           s3_.callNode_ = s3_.insertAccessor(JSFunctionCallNode.createCall());
                           VarHandle.storeStoreFence();
                           this.overloadedString_cache = s3_;
                           this.state_0_ = state_0 |= 8;
                        }
                     }

                     if (s3_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.doOverloadedString(arg0Value_, arg1Value, s3_.leftOperatorCounter_, s3_.operatorImplementation_, s3_.callNode_);
                     }
                  }

                  if (JSGuards.isNullOrUndefined(arg1Value)) {
                     int var25;
                     this.state_0_ = var25 = state_0 | 16;
                     lock.unlock();
                     hasLock = false;
                     return this.doOverloadedNullish(arg0Value_, arg1Value);
                  }
               }

               if (arg1Value instanceof JSOverloadedOperatorsObject) {
                  JSOverloadedOperatorsObject arg1Value_xx = (JSOverloadedOperatorsObject)arg1Value;
                  if ((exclude & 16) == 0) {
                     int count5_ = 0;
                     JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.NumberOverloadedData s5_ = this.numberOverloaded_cache;
                     if ((state_0 & 32) != 0) {
                        while (s5_ != null && (!arg1Value_xx.matchesOperatorCounter(s5_.rightOperatorCounter_) || !JSGuards.isNumber(arg0Value))) {
                           s5_ = s5_.next_;
                           count5_++;
                        }
                     }

                     if (s5_ == null) {
                        int rightOperatorCounter__1 = arg1Value_xx.getOperatorCounter();
                        if (arg1Value_xx.matchesOperatorCounter(rightOperatorCounter__1) && JSGuards.isNumber(arg0Value) && count5_ < 3) {
                           s5_ = super.insert(new JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.NumberOverloadedData(this.numberOverloaded_cache));
                           s5_.rightOperatorCounter_ = rightOperatorCounter__1;
                           s5_.operatorImplementation_ = OperatorSet.getOperatorImplementation(arg0Value, arg1Value_xx, this.getOverloadedOperatorName());
                           s5_.callNode_ = s5_.insertAccessor(JSFunctionCallNode.createCall());
                           VarHandle.storeStoreFence();
                           this.numberOverloaded_cache = s5_;
                           this.state_0_ = state_0 |= 32;
                        }
                     }

                     if (s5_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.doNumberOverloaded(arg0Value, arg1Value_xx, s5_.rightOperatorCounter_, s5_.operatorImplementation_, s5_.callNode_);
                     }
                  }

                  if ((exclude & 32) == 0 && arg0Value instanceof BigInt) {
                     BigInt arg0Value_x = (BigInt)arg0Value;
                     int count6_ = 0;
                     JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.BigIntOverloadedData s6_ = this.bigIntOverloaded_cache;
                     if ((state_0 & 64) != 0) {
                        while (s6_ != null && !arg1Value_xx.matchesOperatorCounter(s6_.rightOperatorCounter_)) {
                           s6_ = s6_.next_;
                           count6_++;
                        }
                     }

                     if (s6_ == null) {
                        int rightOperatorCounter__2 = arg1Value_xx.getOperatorCounter();
                        if (arg1Value_xx.matchesOperatorCounter(rightOperatorCounter__2) && count6_ < 3) {
                           s6_ = super.insert(new JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.BigIntOverloadedData(this.bigIntOverloaded_cache));
                           s6_.rightOperatorCounter_ = rightOperatorCounter__2;
                           s6_.operatorImplementation_ = OperatorSet.getOperatorImplementation(arg0Value_x, arg1Value_xx, this.getOverloadedOperatorName());
                           s6_.callNode_ = s6_.insertAccessor(JSFunctionCallNode.createCall());
                           VarHandle.storeStoreFence();
                           this.bigIntOverloaded_cache = s6_;
                           this.state_0_ = state_0 |= 64;
                        }
                     }

                     if (s6_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.doBigIntOverloaded(arg0Value_x, arg1Value_xx, s6_.rightOperatorCounter_, s6_.operatorImplementation_, s6_.callNode_);
                     }
                  }

                  if ((exclude & 64) == 0) {
                     int count7_ = 0;
                     JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.StringOverloadedData s7_ = this.stringOverloaded_cache;
                     if ((state_0 & 128) != 0) {
                        while (s7_ != null) {
                           if (arg1Value_xx.matchesOperatorCounter(s7_.rightOperatorCounter_) && JSGuards.isString(arg0Value)) {
                              assert !this.isAddition();
                              break;
                           }

                           s7_ = s7_.next_;
                           count7_++;
                        }
                     }

                     if (s7_ == null) {
                        int rightOperatorCounter__3 = arg1Value_xx.getOperatorCounter();
                        if (arg1Value_xx.matchesOperatorCounter(rightOperatorCounter__3) && JSGuards.isString(arg0Value) && !this.isAddition() && count7_ < 3) {
                           s7_ = super.insert(new JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.StringOverloadedData(this.stringOverloaded_cache));
                           s7_.rightOperatorCounter_ = rightOperatorCounter__3;
                           s7_.operatorImplementation_ = OperatorSet.getOperatorImplementation(arg0Value, arg1Value_xx, this.getOverloadedOperatorName());
                           s7_.callNode_ = s7_.insertAccessor(JSFunctionCallNode.createCall());
                           VarHandle.storeStoreFence();
                           this.stringOverloaded_cache = s7_;
                           this.state_0_ = state_0 |= 128;
                        }
                     }

                     if (s7_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.doStringOverloaded(arg0Value, arg1Value_xx, s7_.rightOperatorCounter_, s7_.operatorImplementation_, s7_.callNode_);
                     }
                  }

                  if (JSGuards.isNullOrUndefined(arg0Value)) {
                     int var24;
                     this.state_0_ = var24 = state_0 | 256;
                     lock.unlock();
                     hasLock = false;
                     return this.doNullishOverloaded(arg0Value, arg1Value_xx);
                  }
               }

               this.generic_callNode_ = super.insert(JSFunctionCallNode.createCall());
               int var26;
               this.exclude_ = var26 = exclude | 127;
               this.overloadedOverloaded_cache = null;
               this.overloadedNumber_cache = null;
               this.overloadedBigInt_cache = null;
               this.overloadedString_cache = null;
               this.numberOverloaded_cache = null;
               this.bigIntOverloaded_cache = null;
               this.stringOverloaded_cache = null;
               state_0 &= -240;
               int var23;
               this.state_0_ = var23 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               return this.doGeneric(arg0Value, arg1Value, this.generic_callNode_);
            } finally {
               if (oldState_0 != 0) {
                  this.checkForPolymorphicSpecialize(oldState_0);
               }
            }
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }
      }

      private void checkForPolymorphicSpecialize(int oldState_0) {
         if ((oldState_0 & 512) == 0 && (this.state_0_ & 512) != 0) {
            this.reportPolymorphicSpecialize();
         }
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         if (state_0 == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & state_0 - 1) == 0) {
               JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedOverloadedData s0_ = this.overloadedOverloaded_cache;
               JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedNumberData s1_ = this.overloadedNumber_cache;
               JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedBigIntData s2_ = this.overloadedBigInt_cache;
               JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedStringData s3_ = this.overloadedString_cache;
               JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.NumberOverloadedData s5_ = this.numberOverloaded_cache;
               JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.BigIntOverloadedData s6_ = this.bigIntOverloaded_cache;
               JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.StringOverloadedData s7_ = this.stringOverloaded_cache;
               if ((s0_ == null || s0_.next_ == null)
                  && (s1_ == null || s1_.next_ == null)
                  && (s2_ == null || s2_.next_ == null)
                  && (s3_ == null || s3_.next_ == null)
                  && (s5_ == null || s5_.next_ == null)
                  && (s6_ == null || s6_.next_ == null)
                  && (s7_ == null || s7_.next_ == null)) {
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
         Object[] s = new Object[]{"doOverloadedOverloaded", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedOverloadedData s0_ = this.overloadedOverloaded_cache;
               s0_ != null;
               s0_ = s0_.next_
            ) {
               cached.add(Arrays.asList(s0_.leftOperatorCounter_, s0_.rightOperatorCounter_, s0_.operatorImplementation_, s0_.callNode_));
            }

            s[2] = cached;
         } else if ((exclude & 1) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doOverloadedNumber", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedNumberData s1_ = this.overloadedNumber_cache; s1_ != null; s1_ = s1_.next_) {
               cached.add(Arrays.asList(s1_.leftOperatorCounter_, s1_.operatorImplementation_, s1_.callNode_));
            }

            s[2] = cached;
         } else if ((exclude & 2) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doOverloadedBigInt", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedBigIntData s2_ = this.overloadedBigInt_cache; s2_ != null; s2_ = s2_.next_) {
               cached.add(Arrays.asList(s2_.leftOperatorCounter_, s2_.operatorImplementation_, s2_.callNode_));
            }

            s[2] = cached;
         } else if ((exclude & 4) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"doOverloadedString", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedStringData s3_ = this.overloadedString_cache; s3_ != null; s3_ = s3_.next_) {
               cached.add(Arrays.asList(s3_.leftOperatorCounter_, s3_.operatorImplementation_, s3_.callNode_));
            }

            s[2] = cached;
         } else if ((exclude & 8) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"doOverloadedNullish", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"doNumberOverloaded", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.NumberOverloadedData s5_ = this.numberOverloaded_cache; s5_ != null; s5_ = s5_.next_) {
               cached.add(Arrays.asList(s5_.rightOperatorCounter_, s5_.operatorImplementation_, s5_.callNode_));
            }

            s[2] = cached;
         } else if ((exclude & 16) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"doBigIntOverloaded", null, null};
         if ((state_0 & 64) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.BigIntOverloadedData s6_ = this.bigIntOverloaded_cache; s6_ != null; s6_ = s6_.next_) {
               cached.add(Arrays.asList(s6_.rightOperatorCounter_, s6_.operatorImplementation_, s6_.callNode_));
            }

            s[2] = cached;
         } else if ((exclude & 32) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         s = new Object[]{"doStringOverloaded", null, null};
         if ((state_0 & 128) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.StringOverloadedData s7_ = this.stringOverloaded_cache; s7_ != null; s7_ = s7_.next_) {
               cached.add(Arrays.asList(s7_.rightOperatorCounter_, s7_.operatorImplementation_, s7_.callNode_));
            }

            s[2] = cached;
         } else if ((exclude & 64) != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[8] = s;
         s = new Object[]{"doNullishOverloaded", null, null};
         if ((state_0 & 256) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[9] = s;
         s = new Object[]{"doGeneric", null, null};
         if ((state_0 & 512) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.generic_callNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[10] = s;
         return Introspection.Provider.create(data);
      }

      public static JSOverloadedBinaryNode.DispatchBinaryOperatorNode create(TruffleString overloadedOperatorName) {
         return new JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen(overloadedOperatorName);
      }

      @GeneratedBy(JSOverloadedBinaryNode.DispatchBinaryOperatorNode.class)
      private static final class BigIntOverloadedData extends Node {
         @Node.Child
         JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.BigIntOverloadedData next_;
         @CompilerDirectives.CompilationFinal
         int rightOperatorCounter_;
         @CompilerDirectives.CompilationFinal
         Object operatorImplementation_;
         @Node.Child
         JSFunctionCallNode callNode_;

         BigIntOverloadedData(JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.BigIntOverloadedData next_) {
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

      @GeneratedBy(JSOverloadedBinaryNode.DispatchBinaryOperatorNode.class)
      private static final class NumberOverloadedData extends Node {
         @Node.Child
         JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.NumberOverloadedData next_;
         @CompilerDirectives.CompilationFinal
         int rightOperatorCounter_;
         @CompilerDirectives.CompilationFinal
         Object operatorImplementation_;
         @Node.Child
         JSFunctionCallNode callNode_;

         NumberOverloadedData(JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.NumberOverloadedData next_) {
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

      @GeneratedBy(JSOverloadedBinaryNode.DispatchBinaryOperatorNode.class)
      private static final class OverloadedBigIntData extends Node {
         @Node.Child
         JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedBigIntData next_;
         @CompilerDirectives.CompilationFinal
         int leftOperatorCounter_;
         @CompilerDirectives.CompilationFinal
         Object operatorImplementation_;
         @Node.Child
         JSFunctionCallNode callNode_;

         OverloadedBigIntData(JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedBigIntData next_) {
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

      @GeneratedBy(JSOverloadedBinaryNode.DispatchBinaryOperatorNode.class)
      private static final class OverloadedNumberData extends Node {
         @Node.Child
         JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedNumberData next_;
         @CompilerDirectives.CompilationFinal
         int leftOperatorCounter_;
         @CompilerDirectives.CompilationFinal
         Object operatorImplementation_;
         @Node.Child
         JSFunctionCallNode callNode_;

         OverloadedNumberData(JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedNumberData next_) {
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

      @GeneratedBy(JSOverloadedBinaryNode.DispatchBinaryOperatorNode.class)
      private static final class OverloadedOverloadedData extends Node {
         @Node.Child
         JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedOverloadedData next_;
         @CompilerDirectives.CompilationFinal
         int leftOperatorCounter_;
         @CompilerDirectives.CompilationFinal
         int rightOperatorCounter_;
         @CompilerDirectives.CompilationFinal
         Object operatorImplementation_;
         @Node.Child
         JSFunctionCallNode callNode_;

         OverloadedOverloadedData(JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedOverloadedData next_) {
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

      @GeneratedBy(JSOverloadedBinaryNode.DispatchBinaryOperatorNode.class)
      private static final class OverloadedStringData extends Node {
         @Node.Child
         JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedStringData next_;
         @CompilerDirectives.CompilationFinal
         int leftOperatorCounter_;
         @CompilerDirectives.CompilationFinal
         Object operatorImplementation_;
         @Node.Child
         JSFunctionCallNode callNode_;

         OverloadedStringData(JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.OverloadedStringData next_) {
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

      @GeneratedBy(JSOverloadedBinaryNode.DispatchBinaryOperatorNode.class)
      private static final class StringOverloadedData extends Node {
         @Node.Child
         JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.StringOverloadedData next_;
         @CompilerDirectives.CompilationFinal
         int rightOperatorCounter_;
         @CompilerDirectives.CompilationFinal
         Object operatorImplementation_;
         @Node.Child
         JSFunctionCallNode callNode_;

         StringOverloadedData(JSOverloadedBinaryNodeGen.DispatchBinaryOperatorNodeGen.StringOverloadedData next_) {
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

   @GeneratedBy(JSOverloadedBinaryNode.class)
   private static final class ToNumericOperandData extends Node {
      @Node.Child
      JSToNumericNode toNumericOperandLeftNode_;
      @Node.Child
      JSToNumericNode toNumericOperandRightNode_;
      @Node.Child
      JSOverloadedBinaryNode.DispatchBinaryOperatorNode dispatchBinaryOperatorNode_;

      ToNumericOperandData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(JSOverloadedBinaryNode.class)
   private static final class ToOperandAdditionData extends Node {
      @Node.Child
      JSToOperandNode toOperandLeftNode_;
      @Node.Child
      JSToOperandNode toOperandRightNode_;
      @Node.Child
      JSOverloadedBinaryNode.DispatchBinaryOperatorNode dispatchBinaryOperatorNode_;
      @Node.Child
      JSToStringNode toStringLeftNode_;
      @Node.Child
      JSToStringNode toStringRightNode_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile leftStringProfile_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile rightStringProfile_;
      @Node.Child
      JSAddNode addNode_;

      ToOperandAdditionData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(JSOverloadedBinaryNode.class)
   private static final class ToOperandGenericData extends Node {
      @Node.Child
      JSToOperandNode toOperandLeftNode_;
      @Node.Child
      JSToOperandNode toOperandRightNode_;
      @Node.Child
      JSOverloadedBinaryNode.DispatchBinaryOperatorNode dispatchBinaryOperatorNode_;

      ToOperandGenericData() {
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
