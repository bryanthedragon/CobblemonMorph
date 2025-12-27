package com.oracle.truffle.js.decorators;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.function.ClassElementDefinitionRecord;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(CreateDecoratorContextObjectNode.class)
public final class CreateDecoratorContextObjectNodeGen extends CreateDecoratorContextObjectNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private CreateDecoratorContextObjectNodeGen.PrivateMethodCachedData privateMethodCached_cache;
   @Node.Child
   private CreateDecoratorContextObjectNodeGen.PublicMethodCachedData publicMethodCached_cache;
   @Node.Child
   private PropertySetNode methodGeneric_setMagic_;
   @Node.Child
   private CreateDecoratorContextObjectNodeGen.FieldCachedData fieldCached_cache;
   @Node.Child
   private PropertySetNode fieldUncached_setMagic_;
   @Node.Child
   private CreateDecoratorContextObjectNodeGen.AutoAccessorCachedData autoAccessorCached_cache;
   @Node.Child
   private PropertySetNode autoAccessor_setMagic_;
   @Node.Child
   private CreateDecoratorContextObjectNodeGen.GetterCachedData getterCached_cache;
   @Node.Child
   private PropertySetNode getter_setMagic_;
   @Node.Child
   private CreateDecoratorContextObjectNodeGen.SetterCachedData setterCached_cache;
   @Node.Child
   private PropertySetNode setter_setMagic_;

   private CreateDecoratorContextObjectNodeGen(JSContext context, boolean isStatic) {
      super(context, isStatic);
   }

   @ExplodeLoop
   @Override
   public JSDynamicObject executeContext(
      VirtualFrame frameValue, ClassElementDefinitionRecord arg0Value, Object arg1Value, CreateDecoratorContextObjectNode.Record arg2Value
   ) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 1) != 0 && arg0Value instanceof ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord) {
            ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord arg0Value_ = (ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord)arg0Value;
            if (arg0Value_.isMethod()) {
               for (CreateDecoratorContextObjectNodeGen.PrivateMethodCachedData s0_ = this.privateMethodCached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (CreateDecoratorContextObjectNode.nameEquals(s0_.strEq_, arg0Value_, s0_.cachedName_)) {
                     assert s0_.privateName_;

                     return this.doPrivateMethodCached(
                        frameValue,
                        arg0Value_,
                        arg1Value,
                        arg2Value,
                        s0_.cachedName_,
                        s0_.description_,
                        s0_.strEq_,
                        s0_.valueGetterFunctionData_,
                        s0_.privateName_
                     );
                  }
               }
            }
         }

         if ((state_0 & 30) != 0) {
            if ((state_0 & 2) != 0 && arg0Value.isMethod()) {
               for (CreateDecoratorContextObjectNodeGen.PublicMethodCachedData s1_ = this.publicMethodCached_cache; s1_ != null; s1_ = s1_.next_) {
                  if (CreateDecoratorContextObjectNode.nameEquals(s1_.strEq_, arg0Value, s1_.cachedName_)) {
                     assert !s1_.privateName_;

                     return this.doPublicMethodCached(
                        frameValue,
                        arg0Value,
                        arg1Value,
                        arg2Value,
                        s1_.cachedName_,
                        s1_.description_,
                        s1_.strEq_,
                        s1_.privateName_,
                        s1_.valueGetterFunctionData_
                     );
                  }
               }
            }

            if ((state_0 & 4) != 0 && arg0Value.isMethod()) {
               return this.doMethodGeneric(frameValue, arg0Value, arg1Value, arg2Value, this.methodGeneric_setMagic_);
            }

            if ((state_0 & 8) != 0 && arg0Value.isField()) {
               for (CreateDecoratorContextObjectNodeGen.FieldCachedData s3_ = this.fieldCached_cache; s3_ != null; s3_ = s3_.next_) {
                  if (CreateDecoratorContextObjectNode.nameEquals(s3_.strEq_, arg0Value, s3_.cachedName_)) {
                     return this.doFieldCached(
                        frameValue,
                        arg0Value,
                        arg1Value,
                        arg2Value,
                        s3_.cachedName_,
                        s3_.description_,
                        s3_.strEq_,
                        s3_.privateName_,
                        s3_.valueGetterFunctionData_,
                        s3_.valueSetterFunctionData_
                     );
                  }
               }
            }

            if ((state_0 & 16) != 0 && arg0Value.isField()) {
               return this.doFieldUncached(frameValue, arg0Value, arg1Value, arg2Value, this.fieldUncached_setMagic_);
            }
         }

         if ((state_0 & 96) != 0 && arg0Value instanceof ClassElementDefinitionRecord.AutoAccessor) {
            ClassElementDefinitionRecord.AutoAccessor arg0Value_ = (ClassElementDefinitionRecord.AutoAccessor)arg0Value;
            if ((state_0 & 32) != 0 && arg0Value_.isAutoAccessor()) {
               for (CreateDecoratorContextObjectNodeGen.AutoAccessorCachedData s5_ = this.autoAccessorCached_cache; s5_ != null; s5_ = s5_.next_) {
                  if (CreateDecoratorContextObjectNode.nameEquals(s5_.strEq_, arg0Value_, s5_.cachedName_)) {
                     return this.doAutoAccessorCached(
                        frameValue,
                        arg0Value_,
                        arg1Value,
                        arg2Value,
                        s5_.cachedName_,
                        s5_.description_,
                        s5_.strEq_,
                        s5_.privateName_,
                        s5_.valueGetterFunctionData_,
                        s5_.valueSetterFunctionData_
                     );
                  }
               }
            }

            if ((state_0 & 64) != 0 && arg0Value_.isAutoAccessor()) {
               return this.doAutoAccessor(frameValue, arg0Value_, arg1Value, arg2Value, this.autoAccessor_setMagic_);
            }
         }

         if ((state_0 & 1920) != 0) {
            if ((state_0 & 128) != 0 && arg0Value.isGetter()) {
               for (CreateDecoratorContextObjectNodeGen.GetterCachedData s7_ = this.getterCached_cache; s7_ != null; s7_ = s7_.next_) {
                  if (CreateDecoratorContextObjectNode.nameEquals(s7_.strEq_, arg0Value, s7_.cachedName_)) {
                     assert !s7_.privateName_;

                     return this.doGetterCached(
                        frameValue,
                        arg0Value,
                        arg1Value,
                        arg2Value,
                        s7_.cachedName_,
                        s7_.description_,
                        s7_.strEq_,
                        s7_.privateName_,
                        s7_.valueGetterFunctionData_
                     );
                  }
               }
            }

            if ((state_0 & 256) != 0 && arg0Value.isGetter()) {
               return this.doGetter(frameValue, arg0Value, arg1Value, arg2Value, this.getter_setMagic_);
            }

            if ((state_0 & 512) != 0 && arg0Value.isSetter()) {
               for (CreateDecoratorContextObjectNodeGen.SetterCachedData s9_ = this.setterCached_cache; s9_ != null; s9_ = s9_.next_) {
                  if (CreateDecoratorContextObjectNode.nameEquals(s9_.strEq_, arg0Value, s9_.cachedName_)) {
                     assert !s9_.privateName_;

                     return this.doSetterCached(
                        frameValue,
                        arg0Value,
                        arg1Value,
                        arg2Value,
                        s9_.cachedName_,
                        s9_.description_,
                        s9_.strEq_,
                        s9_.privateName_,
                        s9_.valueSetterFunctionData_
                     );
                  }
               }
            }

            if ((state_0 & 1024) != 0 && arg0Value.isSetter()) {
               return this.doSetter(frameValue, arg0Value, arg1Value, arg2Value, this.setter_setMagic_);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(frameValue, arg0Value, arg1Value, arg2Value);
   }

   private JSDynamicObject executeAndSpecialize(
      VirtualFrame frameValue, ClassElementDefinitionRecord arg0Value, Object arg1Value, CreateDecoratorContextObjectNode.Record arg2Value
   ) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if ((exclude & 1) == 0 && arg0Value instanceof ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord) {
            ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord arg0Value_ = (ClassElementDefinitionRecord.PrivateFrameBasedElementDefinitionRecord)arg0Value;
            if (arg0Value_.isMethod()) {
               int count0_ = 0;
               CreateDecoratorContextObjectNodeGen.PrivateMethodCachedData s0_ = this.privateMethodCached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null) {
                     if (CreateDecoratorContextObjectNode.nameEquals(s0_.strEq_, arg0Value_, s0_.cachedName_)) {
                        assert s0_.privateName_;
                        break;
                     }

                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null) {
                  Object cachedName__ = arg0Value_.getKey();
                  TruffleString.EqualNode strEq__ = super.insert(TruffleString.EqualNode.create());
                  if (CreateDecoratorContextObjectNode.nameEquals(strEq__, arg0Value_, cachedName__)) {
                     boolean privateName__ = arg0Value_.isPrivate();
                     if (privateName__ && count0_ < 3) {
                        s0_ = super.insert(new CreateDecoratorContextObjectNodeGen.PrivateMethodCachedData(this.privateMethodCached_cache));
                        s0_.cachedName_ = cachedName__;
                        s0_.description_ = this.getName(cachedName__);
                        s0_.strEq_ = s0_.insertAccessor(strEq__);
                        s0_.valueGetterFunctionData_ = this.createMethodGetterFromFrameCached(arg0Value_);
                        s0_.privateName_ = privateName__;
                        VarHandle.storeStoreFence();
                        this.privateMethodCached_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                     }
                  }
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doPrivateMethodCached(
                     frameValue,
                     arg0Value_,
                     arg1Value,
                     arg2Value,
                     s0_.cachedName_,
                     s0_.description_,
                     s0_.strEq_,
                     s0_.valueGetterFunctionData_,
                     s0_.privateName_
                  );
               }
            }
         }

         if ((exclude & 2) == 0 && arg0Value.isMethod()) {
            int count1_ = 0;
            CreateDecoratorContextObjectNodeGen.PublicMethodCachedData s1_ = this.publicMethodCached_cache;
            if ((state_0 & 2) != 0) {
               while (s1_ != null) {
                  if (CreateDecoratorContextObjectNode.nameEquals(s1_.strEq_, arg0Value, s1_.cachedName_)) {
                     assert !s1_.privateName_;
                     break;
                  }

                  s1_ = s1_.next_;
                  count1_++;
               }
            }

            if (s1_ == null) {
               Object cachedName__1 = arg0Value.getKey();
               TruffleString.EqualNode strEq__1 = super.insert(TruffleString.EqualNode.create());
               if (CreateDecoratorContextObjectNode.nameEquals(strEq__1, arg0Value, cachedName__1)) {
                  boolean privateName__1 = arg0Value.isPrivate();
                  if (!privateName__1 && count1_ < 3) {
                     s1_ = super.insert(new CreateDecoratorContextObjectNodeGen.PublicMethodCachedData(this.publicMethodCached_cache));
                     s1_.cachedName_ = cachedName__1;
                     s1_.description_ = this.getName(cachedName__1);
                     s1_.strEq_ = s1_.insertAccessor(strEq__1);
                     s1_.privateName_ = privateName__1;
                     s1_.valueGetterFunctionData_ = this.createValueGetterCached(cachedName__1, false);
                     VarHandle.storeStoreFence();
                     this.publicMethodCached_cache = s1_;
                     this.state_0_ = state_0 |= 2;
                  }
               }
            }

            if (s1_ != null) {
               lock.unlock();
               hasLock = false;
               return this.doPublicMethodCached(
                  frameValue, arg0Value, arg1Value, arg2Value, s1_.cachedName_, s1_.description_, s1_.strEq_, s1_.privateName_, s1_.valueGetterFunctionData_
               );
            }
         }

         if (arg0Value.isMethod()) {
            this.methodGeneric_setMagic_ = super.insert(PropertySetNode.createSetHidden(CreateDecoratorContextObjectNode.MAGIC_KEY, this.context));
            int var32;
            this.exclude_ = var32 = exclude | 3;
            this.privateMethodCached_cache = null;
            this.publicMethodCached_cache = null;
            state_0 &= -4;
            int var27;
            this.state_0_ = var27 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return this.doMethodGeneric(frameValue, arg0Value, arg1Value, arg2Value, this.methodGeneric_setMagic_);
         } else {
            if ((exclude & 4) == 0 && arg0Value.isField()) {
               int count3_ = 0;
               CreateDecoratorContextObjectNodeGen.FieldCachedData s3_ = this.fieldCached_cache;
               if ((state_0 & 8) != 0) {
                  while (s3_ != null && !CreateDecoratorContextObjectNode.nameEquals(s3_.strEq_, arg0Value, s3_.cachedName_)) {
                     s3_ = s3_.next_;
                     count3_++;
                  }
               }

               if (s3_ == null) {
                  Object cachedName__2 = arg0Value.getKey();
                  TruffleString.EqualNode strEq__2 = super.insert(TruffleString.EqualNode.create());
                  if (CreateDecoratorContextObjectNode.nameEquals(strEq__2, arg0Value, cachedName__2) && count3_ < 3) {
                     s3_ = super.insert(new CreateDecoratorContextObjectNodeGen.FieldCachedData(this.fieldCached_cache));
                     s3_.cachedName_ = cachedName__2;
                     s3_.description_ = this.getName(cachedName__2);
                     s3_.strEq_ = s3_.insertAccessor(strEq__2);
                     s3_.privateName_ = arg0Value.isPrivate();
                     s3_.valueGetterFunctionData_ = this.createValueGetterCached(cachedName__2, s3_.privateName_);
                     s3_.valueSetterFunctionData_ = this.createValueSetterCached(cachedName__2, s3_.privateName_);
                     VarHandle.storeStoreFence();
                     this.fieldCached_cache = s3_;
                     this.state_0_ = state_0 |= 8;
                  }
               }

               if (s3_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doFieldCached(
                     frameValue,
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     s3_.cachedName_,
                     s3_.description_,
                     s3_.strEq_,
                     s3_.privateName_,
                     s3_.valueGetterFunctionData_,
                     s3_.valueSetterFunctionData_
                  );
               }
            }

            if (arg0Value.isField()) {
               this.fieldUncached_setMagic_ = super.insert(PropertySetNode.createSetHidden(CreateDecoratorContextObjectNode.MAGIC_KEY, this.context));
               int var31;
               this.exclude_ = var31 = exclude | 4;
               this.fieldCached_cache = null;
               state_0 &= -9;
               int var25;
               this.state_0_ = var25 = state_0 | 16;
               lock.unlock();
               hasLock = false;
               return this.doFieldUncached(frameValue, arg0Value, arg1Value, arg2Value, this.fieldUncached_setMagic_);
            } else {
               if (arg0Value instanceof ClassElementDefinitionRecord.AutoAccessor) {
                  ClassElementDefinitionRecord.AutoAccessor arg0Value_ = (ClassElementDefinitionRecord.AutoAccessor)arg0Value;
                  if ((exclude & 8) == 0 && arg0Value_.isAutoAccessor()) {
                     int count5_ = 0;
                     CreateDecoratorContextObjectNodeGen.AutoAccessorCachedData s5_ = this.autoAccessorCached_cache;
                     if ((state_0 & 32) != 0) {
                        while (s5_ != null && !CreateDecoratorContextObjectNode.nameEquals(s5_.strEq_, arg0Value_, s5_.cachedName_)) {
                           s5_ = s5_.next_;
                           count5_++;
                        }
                     }

                     if (s5_ == null) {
                        Object cachedName__3 = arg0Value_.getKey();
                        TruffleString.EqualNode strEq__3 = super.insert(TruffleString.EqualNode.create());
                        if (CreateDecoratorContextObjectNode.nameEquals(strEq__3, arg0Value_, cachedName__3) && count5_ < 3) {
                           s5_ = super.insert(new CreateDecoratorContextObjectNodeGen.AutoAccessorCachedData(this.autoAccessorCached_cache));
                           s5_.cachedName_ = cachedName__3;
                           s5_.description_ = this.getName(cachedName__3);
                           s5_.strEq_ = s5_.insertAccessor(strEq__3);
                           s5_.privateName_ = arg0Value_.isPrivate();
                           s5_.valueGetterFunctionData_ = this.createValueGetterCached(cachedName__3, s5_.privateName_);
                           s5_.valueSetterFunctionData_ = this.createValueSetterCached(cachedName__3, s5_.privateName_);
                           VarHandle.storeStoreFence();
                           this.autoAccessorCached_cache = s5_;
                           this.state_0_ = state_0 |= 32;
                        }
                     }

                     if (s5_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.doAutoAccessorCached(
                           frameValue,
                           arg0Value_,
                           arg1Value,
                           arg2Value,
                           s5_.cachedName_,
                           s5_.description_,
                           s5_.strEq_,
                           s5_.privateName_,
                           s5_.valueGetterFunctionData_,
                           s5_.valueSetterFunctionData_
                        );
                     }
                  }

                  if (arg0Value_.isAutoAccessor()) {
                     this.autoAccessor_setMagic_ = super.insert(PropertySetNode.createSetHidden(CreateDecoratorContextObjectNode.MAGIC_KEY, this.context));
                     int var30;
                     this.exclude_ = var30 = exclude | 8;
                     this.autoAccessorCached_cache = null;
                     state_0 &= -33;
                     int var23;
                     this.state_0_ = var23 = state_0 | 64;
                     lock.unlock();
                     hasLock = false;
                     return this.doAutoAccessor(frameValue, arg0Value_, arg1Value, arg2Value, this.autoAccessor_setMagic_);
                  }
               }

               if ((exclude & 16) == 0 && arg0Value.isGetter()) {
                  int count7_ = 0;
                  CreateDecoratorContextObjectNodeGen.GetterCachedData s7_ = this.getterCached_cache;
                  if ((state_0 & 128) != 0) {
                     while (s7_ != null) {
                        if (CreateDecoratorContextObjectNode.nameEquals(s7_.strEq_, arg0Value, s7_.cachedName_)) {
                           assert !s7_.privateName_;
                           break;
                        }

                        s7_ = s7_.next_;
                        count7_++;
                     }
                  }

                  if (s7_ == null) {
                     Object cachedName__4 = arg0Value.getKey();
                     TruffleString.EqualNode strEq__4 = super.insert(TruffleString.EqualNode.create());
                     if (CreateDecoratorContextObjectNode.nameEquals(strEq__4, arg0Value, cachedName__4)) {
                        boolean privateName__2 = arg0Value.isPrivate();
                        if (!privateName__2 && count7_ < 3) {
                           s7_ = super.insert(new CreateDecoratorContextObjectNodeGen.GetterCachedData(this.getterCached_cache));
                           s7_.cachedName_ = cachedName__4;
                           s7_.description_ = this.getName(cachedName__4);
                           s7_.strEq_ = s7_.insertAccessor(strEq__4);
                           s7_.privateName_ = privateName__2;
                           s7_.valueGetterFunctionData_ = this.createValueGetterCached(cachedName__4, privateName__2);
                           VarHandle.storeStoreFence();
                           this.getterCached_cache = s7_;
                           this.state_0_ = state_0 |= 128;
                        }
                     }
                  }

                  if (s7_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.doGetterCached(
                        frameValue,
                        arg0Value,
                        arg1Value,
                        arg2Value,
                        s7_.cachedName_,
                        s7_.description_,
                        s7_.strEq_,
                        s7_.privateName_,
                        s7_.valueGetterFunctionData_
                     );
                  }
               }

               if (arg0Value.isGetter()) {
                  this.getter_setMagic_ = super.insert(PropertySetNode.createSetHidden(CreateDecoratorContextObjectNode.MAGIC_KEY, this.context));
                  int var29;
                  this.exclude_ = var29 = exclude | 16;
                  this.getterCached_cache = null;
                  state_0 &= -129;
                  int var21;
                  this.state_0_ = var21 = state_0 | 256;
                  lock.unlock();
                  hasLock = false;
                  return this.doGetter(frameValue, arg0Value, arg1Value, arg2Value, this.getter_setMagic_);
               } else {
                  if ((exclude & 32) == 0 && arg0Value.isSetter()) {
                     int count9_ = 0;
                     CreateDecoratorContextObjectNodeGen.SetterCachedData s9_ = this.setterCached_cache;
                     if ((state_0 & 512) != 0) {
                        while (s9_ != null) {
                           if (CreateDecoratorContextObjectNode.nameEquals(s9_.strEq_, arg0Value, s9_.cachedName_)) {
                              assert !s9_.privateName_;
                              break;
                           }

                           s9_ = s9_.next_;
                           count9_++;
                        }
                     }

                     if (s9_ == null) {
                        Object cachedName__5 = arg0Value.getKey();
                        TruffleString.EqualNode strEq__5 = super.insert(TruffleString.EqualNode.create());
                        if (CreateDecoratorContextObjectNode.nameEquals(strEq__5, arg0Value, cachedName__5)) {
                           boolean privateName__3 = arg0Value.isPrivate();
                           if (!privateName__3 && count9_ < 3) {
                              s9_ = super.insert(new CreateDecoratorContextObjectNodeGen.SetterCachedData(this.setterCached_cache));
                              s9_.cachedName_ = cachedName__5;
                              s9_.description_ = this.getName(cachedName__5);
                              s9_.strEq_ = s9_.insertAccessor(strEq__5);
                              s9_.privateName_ = privateName__3;
                              s9_.valueSetterFunctionData_ = this.createValueSetterCached(cachedName__5, privateName__3);
                              VarHandle.storeStoreFence();
                              this.setterCached_cache = s9_;
                              this.state_0_ = state_0 |= 512;
                           }
                        }
                     }

                     if (s9_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.doSetterCached(
                           frameValue,
                           arg0Value,
                           arg1Value,
                           arg2Value,
                           s9_.cachedName_,
                           s9_.description_,
                           s9_.strEq_,
                           s9_.privateName_,
                           s9_.valueSetterFunctionData_
                        );
                     }
                  }

                  if (!arg0Value.isSetter()) {
                     throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
                  } else {
                     this.setter_setMagic_ = super.insert(PropertySetNode.createSetHidden(CreateDecoratorContextObjectNode.MAGIC_KEY, this.context));
                     int var28;
                     this.exclude_ = var28 = exclude | 32;
                     this.setterCached_cache = null;
                     state_0 &= -513;
                     int var19;
                     this.state_0_ = var19 = state_0 | 1024;
                     lock.unlock();
                     hasLock = false;
                     return this.doSetter(frameValue, arg0Value, arg1Value, arg2Value, this.setter_setMagic_);
                  }
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
      if (state_0 == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         if ((state_0 & state_0 - 1) == 0) {
            CreateDecoratorContextObjectNodeGen.PrivateMethodCachedData s0_ = this.privateMethodCached_cache;
            CreateDecoratorContextObjectNodeGen.PublicMethodCachedData s1_ = this.publicMethodCached_cache;
            CreateDecoratorContextObjectNodeGen.FieldCachedData s3_ = this.fieldCached_cache;
            CreateDecoratorContextObjectNodeGen.AutoAccessorCachedData s5_ = this.autoAccessorCached_cache;
            CreateDecoratorContextObjectNodeGen.GetterCachedData s7_ = this.getterCached_cache;
            CreateDecoratorContextObjectNodeGen.SetterCachedData s9_ = this.setterCached_cache;
            if ((s0_ == null || s0_.next_ == null)
               && (s1_ == null || s1_.next_ == null)
               && (s3_ == null || s3_.next_ == null)
               && (s5_ == null || s5_.next_ == null)
               && (s7_ == null || s7_.next_ == null)
               && (s9_ == null || s9_.next_ == null)) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[12];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doPrivateMethodCached", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (CreateDecoratorContextObjectNodeGen.PrivateMethodCachedData s0_ = this.privateMethodCached_cache; s0_ != null; s0_ = s0_.next_) {
            cached.add(Arrays.asList(s0_.cachedName_, s0_.description_, s0_.strEq_, s0_.valueGetterFunctionData_, s0_.privateName_));
         }

         s[2] = cached;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doPublicMethodCached", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (CreateDecoratorContextObjectNodeGen.PublicMethodCachedData s1_ = this.publicMethodCached_cache; s1_ != null; s1_ = s1_.next_) {
            cached.add(Arrays.asList(s1_.cachedName_, s1_.description_, s1_.strEq_, s1_.privateName_, s1_.valueGetterFunctionData_));
         }

         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doMethodGeneric", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.methodGeneric_setMagic_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doFieldCached", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (CreateDecoratorContextObjectNodeGen.FieldCachedData s3_ = this.fieldCached_cache; s3_ != null; s3_ = s3_.next_) {
            cached.add(
               Arrays.asList(s3_.cachedName_, s3_.description_, s3_.strEq_, s3_.privateName_, s3_.valueGetterFunctionData_, s3_.valueSetterFunctionData_)
            );
         }

         s[2] = cached;
      } else if ((exclude & 4) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doFieldUncached", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.fieldUncached_setMagic_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doAutoAccessorCached", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (CreateDecoratorContextObjectNodeGen.AutoAccessorCachedData s5_ = this.autoAccessorCached_cache; s5_ != null; s5_ = s5_.next_) {
            cached.add(
               Arrays.asList(s5_.cachedName_, s5_.description_, s5_.strEq_, s5_.privateName_, s5_.valueGetterFunctionData_, s5_.valueSetterFunctionData_)
            );
         }

         s[2] = cached;
      } else if ((exclude & 8) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doAutoAccessor", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.autoAccessor_setMagic_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doGetterCached", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (CreateDecoratorContextObjectNodeGen.GetterCachedData s7_ = this.getterCached_cache; s7_ != null; s7_ = s7_.next_) {
            cached.add(Arrays.asList(s7_.cachedName_, s7_.description_, s7_.strEq_, s7_.privateName_, s7_.valueGetterFunctionData_));
         }

         s[2] = cached;
      } else if ((exclude & 16) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doGetter", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.getter_setMagic_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doSetterCached", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (CreateDecoratorContextObjectNodeGen.SetterCachedData s9_ = this.setterCached_cache; s9_ != null; s9_ = s9_.next_) {
            cached.add(Arrays.asList(s9_.cachedName_, s9_.description_, s9_.strEq_, s9_.privateName_, s9_.valueSetterFunctionData_));
         }

         s[2] = cached;
      } else if ((exclude & 32) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doSetter", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.setter_setMagic_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      return Introspection.Provider.create(data);
   }

   public static CreateDecoratorContextObjectNode create(JSContext context, boolean isStatic) {
      return new CreateDecoratorContextObjectNodeGen(context, isStatic);
   }

   @GeneratedBy(CreateDecoratorContextObjectNode.class)
   private static final class AutoAccessorCachedData extends Node {
      @Node.Child
      CreateDecoratorContextObjectNodeGen.AutoAccessorCachedData next_;
      @CompilerDirectives.CompilationFinal
      Object cachedName_;
      @CompilerDirectives.CompilationFinal
      Object description_;
      @Node.Child
      TruffleString.EqualNode strEq_;
      @CompilerDirectives.CompilationFinal
      boolean privateName_;
      @CompilerDirectives.CompilationFinal
      JSFunctionData valueGetterFunctionData_;
      @CompilerDirectives.CompilationFinal
      JSFunctionData valueSetterFunctionData_;

      AutoAccessorCachedData(CreateDecoratorContextObjectNodeGen.AutoAccessorCachedData next_) {
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

   @GeneratedBy(CreateDecoratorContextObjectNode.class)
   private static final class FieldCachedData extends Node {
      @Node.Child
      CreateDecoratorContextObjectNodeGen.FieldCachedData next_;
      @CompilerDirectives.CompilationFinal
      Object cachedName_;
      @CompilerDirectives.CompilationFinal
      Object description_;
      @Node.Child
      TruffleString.EqualNode strEq_;
      @CompilerDirectives.CompilationFinal
      boolean privateName_;
      @CompilerDirectives.CompilationFinal
      JSFunctionData valueGetterFunctionData_;
      @CompilerDirectives.CompilationFinal
      JSFunctionData valueSetterFunctionData_;

      FieldCachedData(CreateDecoratorContextObjectNodeGen.FieldCachedData next_) {
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

   @GeneratedBy(CreateDecoratorContextObjectNode.class)
   private static final class GetterCachedData extends Node {
      @Node.Child
      CreateDecoratorContextObjectNodeGen.GetterCachedData next_;
      @CompilerDirectives.CompilationFinal
      Object cachedName_;
      @CompilerDirectives.CompilationFinal
      Object description_;
      @Node.Child
      TruffleString.EqualNode strEq_;
      @CompilerDirectives.CompilationFinal
      boolean privateName_;
      @CompilerDirectives.CompilationFinal
      JSFunctionData valueGetterFunctionData_;

      GetterCachedData(CreateDecoratorContextObjectNodeGen.GetterCachedData next_) {
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

   @GeneratedBy(CreateDecoratorContextObjectNode.class)
   private static final class PrivateMethodCachedData extends Node {
      @Node.Child
      CreateDecoratorContextObjectNodeGen.PrivateMethodCachedData next_;
      @CompilerDirectives.CompilationFinal
      Object cachedName_;
      @CompilerDirectives.CompilationFinal
      Object description_;
      @Node.Child
      TruffleString.EqualNode strEq_;
      @CompilerDirectives.CompilationFinal
      JSFunctionData valueGetterFunctionData_;
      @CompilerDirectives.CompilationFinal
      boolean privateName_;

      PrivateMethodCachedData(CreateDecoratorContextObjectNodeGen.PrivateMethodCachedData next_) {
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

   @GeneratedBy(CreateDecoratorContextObjectNode.class)
   private static final class PublicMethodCachedData extends Node {
      @Node.Child
      CreateDecoratorContextObjectNodeGen.PublicMethodCachedData next_;
      @CompilerDirectives.CompilationFinal
      Object cachedName_;
      @CompilerDirectives.CompilationFinal
      Object description_;
      @Node.Child
      TruffleString.EqualNode strEq_;
      @CompilerDirectives.CompilationFinal
      boolean privateName_;
      @CompilerDirectives.CompilationFinal
      JSFunctionData valueGetterFunctionData_;

      PublicMethodCachedData(CreateDecoratorContextObjectNodeGen.PublicMethodCachedData next_) {
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

   @GeneratedBy(CreateDecoratorContextObjectNode.class)
   private static final class SetterCachedData extends Node {
      @Node.Child
      CreateDecoratorContextObjectNodeGen.SetterCachedData next_;
      @CompilerDirectives.CompilationFinal
      Object cachedName_;
      @CompilerDirectives.CompilationFinal
      Object description_;
      @Node.Child
      TruffleString.EqualNode strEq_;
      @CompilerDirectives.CompilationFinal
      boolean privateName_;
      @CompilerDirectives.CompilationFinal
      JSFunctionData valueSetterFunctionData_;

      SetterCachedData(CreateDecoratorContextObjectNodeGen.SetterCachedData next_) {
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
