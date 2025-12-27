package com.oracle.truffle.js.nodes.control;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsArrayNode;
import com.oracle.truffle.js.nodes.array.JSArrayDeleteIndexNode;
import com.oracle.truffle.js.nodes.cast.JSToPropertyKeyNode;
import com.oracle.truffle.js.nodes.cast.ToArrayIndexNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(DeletePropertyNode.class)
public final class DeletePropertyNodeGen extends DeletePropertyNode implements Introspection.Provider {
   private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private JSToPropertyKeyNode toPropertyKey;
   @Node.Child
   private ToArrayIndexNode toArrayIndex;
   @Node.Child
   private InteropLibrary interop;
   @Node.Child
   private DynamicObjectLibrary jSOrdinaryObject_dynamicObjectLib_;
   @Node.Child
   private IsArrayNode jSObject_isArrayNode_;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile jSObject_arrayProfile_;
   @Node.Child
   private ToArrayIndexNode jSObject_toArrayIndexNode_;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile jSObject_arrayIndexProfile_;
   @Node.Child
   private JSArrayDeleteIndexNode jSObject_deleteArrayIndexNode_;
   @CompilerDirectives.CompilationFinal
   private JSClassProfile jSObject_jsclassProfile_;
   @Node.Child
   private TruffleString.EqualNode string_equalsNode_;

   private DeletePropertyNodeGen(boolean strict, JSContext context, JavaScriptNode targetNode, JavaScriptNode propertyNode) {
      super(strict, context, targetNode, propertyNode);
   }

   @Override
   public boolean executeEvaluated(Object targetNodeValue, Object propertyNodeValue) {
      int state_0 = this.state_0_;
      if ((state_0 & 63) != 0) {
         if ((state_0 & 3) != 0 && targetNodeValue instanceof JSDynamicObject) {
            JSDynamicObject targetNodeValue_ = (JSDynamicObject)targetNodeValue;
            if ((state_0 & 1) != 0 && JSGuards.isJSOrdinaryObject(targetNodeValue_)) {
               return this.doJSOrdinaryObject(targetNodeValue_, propertyNodeValue, this.toPropertyKey, this.jSOrdinaryObject_dynamicObjectLib_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSOrdinaryObject(targetNodeValue_)) {
               return this.doJSObject(
                  targetNodeValue_,
                  propertyNodeValue,
                  this.jSObject_isArrayNode_,
                  this.jSObject_arrayProfile_,
                  this.jSObject_toArrayIndexNode_,
                  this.jSObject_arrayIndexProfile_,
                  this.jSObject_deleteArrayIndexNode_,
                  this.jSObject_jsclassProfile_,
                  this.toPropertyKey
               );
            }
         }

         if ((state_0 & 4) != 0 && targetNodeValue instanceof Symbol) {
            Symbol targetNodeValue_x = (Symbol)targetNodeValue;
            return DeletePropertyNode.doSymbol(targetNodeValue_x, propertyNodeValue, this.toPropertyKey);
         }

         if ((state_0 & 8) != 0 && targetNodeValue instanceof SafeInteger) {
            SafeInteger targetNodeValue_x = (SafeInteger)targetNodeValue;
            return DeletePropertyNode.doSafeInteger(targetNodeValue_x, propertyNodeValue, this.toPropertyKey);
         }

         if ((state_0 & 16) != 0 && targetNodeValue instanceof BigInt) {
            BigInt targetNodeValue_x = (BigInt)targetNodeValue;
            return DeletePropertyNode.doBigInt(targetNodeValue_x, propertyNodeValue, this.toPropertyKey);
         }

         if ((state_0 & 32) != 0 && targetNodeValue instanceof TruffleString) {
            TruffleString targetNodeValue_x = (TruffleString)targetNodeValue;
            return this.doString(targetNodeValue_x, propertyNodeValue, this.toArrayIndex, this.string_equalsNode_);
         }
      }

      if ((state_0 & 960) != 0) {
         if ((state_0 & 64) != 0 && propertyNodeValue instanceof TruffleString) {
            TruffleString propertyNodeValue_ = (TruffleString)propertyNodeValue;
            if (JSGuards.isForeignObject(targetNodeValue) && !this.interop.hasArrayElements(targetNodeValue)) {
               return this.member(targetNodeValue, propertyNodeValue_, this.interop);
            }
         }

         if ((state_0 & 128) != 0 && propertyNodeValue instanceof Integer) {
            int propertyNodeValue_ = (Integer)propertyNodeValue;
            if (JSGuards.isForeignObject(targetNodeValue) && this.interop.hasArrayElements(targetNodeValue)) {
               return this.arrayElementInt(targetNodeValue, propertyNodeValue_, this.interop);
            }
         }

         if ((state_0 & 768) != 0) {
            if ((state_0 & 256) != 0 && JSGuards.isForeignObject(targetNodeValue)) {
               return this.foreignObject(targetNodeValue, propertyNodeValue, this.interop, this.toArrayIndex, this.toPropertyKey);
            }

            if ((state_0 & 512) != 0 && !JSGuards.isTruffleObject(targetNodeValue) && !JSGuards.isString(targetNodeValue)) {
               return this.doOther(targetNodeValue, propertyNodeValue, this.toPropertyKey);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(targetNodeValue, propertyNodeValue);
   }

   @Override
   public Object executeWithTarget(VirtualFrame frameValue, Object targetNodeValue) {
      int state_0 = this.state_0_;
      return (state_0 & 895) == 0 && state_0 != 0
         ? this.executeWithTarget_int0(state_0, frameValue, targetNodeValue)
         : this.executeWithTarget_generic1(state_0, frameValue, targetNodeValue);
   }

   private Object executeWithTarget_int0(int state_0, VirtualFrame frameValue, Object targetNodeValue) {
      int propertyNodeValue_;
      try {
         propertyNodeValue_ = super.propertyNode.executeInt(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(targetNodeValue, var6.getResult());
      }

      assert (state_0 & 128) != 0;

      if (JSGuards.isForeignObject(targetNodeValue) && this.interop.hasArrayElements(targetNodeValue)) {
         return this.arrayElementInt(targetNodeValue, propertyNodeValue_, this.interop);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(targetNodeValue, propertyNodeValue_);
      }
   }

   private Object executeWithTarget_generic1(int state_0, VirtualFrame frameValue, Object targetNodeValue) {
      Object propertyNodeValue_ = super.propertyNode.execute(frameValue);
      if ((state_0 & 63) != 0) {
         if ((state_0 & 3) != 0 && targetNodeValue instanceof JSDynamicObject) {
            JSDynamicObject targetNodeValue_ = (JSDynamicObject)targetNodeValue;
            if ((state_0 & 1) != 0 && JSGuards.isJSOrdinaryObject(targetNodeValue_)) {
               return this.doJSOrdinaryObject(targetNodeValue_, propertyNodeValue_, this.toPropertyKey, this.jSOrdinaryObject_dynamicObjectLib_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSOrdinaryObject(targetNodeValue_)) {
               return this.doJSObject(
                  targetNodeValue_,
                  propertyNodeValue_,
                  this.jSObject_isArrayNode_,
                  this.jSObject_arrayProfile_,
                  this.jSObject_toArrayIndexNode_,
                  this.jSObject_arrayIndexProfile_,
                  this.jSObject_deleteArrayIndexNode_,
                  this.jSObject_jsclassProfile_,
                  this.toPropertyKey
               );
            }
         }

         if ((state_0 & 4) != 0 && targetNodeValue instanceof Symbol) {
            Symbol targetNodeValue_x = (Symbol)targetNodeValue;
            return DeletePropertyNode.doSymbol(targetNodeValue_x, propertyNodeValue_, this.toPropertyKey);
         }

         if ((state_0 & 8) != 0 && targetNodeValue instanceof SafeInteger) {
            SafeInteger targetNodeValue_x = (SafeInteger)targetNodeValue;
            return DeletePropertyNode.doSafeInteger(targetNodeValue_x, propertyNodeValue_, this.toPropertyKey);
         }

         if ((state_0 & 16) != 0 && targetNodeValue instanceof BigInt) {
            BigInt targetNodeValue_x = (BigInt)targetNodeValue;
            return DeletePropertyNode.doBigInt(targetNodeValue_x, propertyNodeValue_, this.toPropertyKey);
         }

         if ((state_0 & 32) != 0 && targetNodeValue instanceof TruffleString) {
            TruffleString targetNodeValue_x = (TruffleString)targetNodeValue;
            return this.doString(targetNodeValue_x, propertyNodeValue_, this.toArrayIndex, this.string_equalsNode_);
         }
      }

      if ((state_0 & 960) != 0) {
         if ((state_0 & 64) != 0 && propertyNodeValue_ instanceof TruffleString) {
            TruffleString propertyNodeValue__ = (TruffleString)propertyNodeValue_;
            if (JSGuards.isForeignObject(targetNodeValue) && !this.interop.hasArrayElements(targetNodeValue)) {
               return this.member(targetNodeValue, propertyNodeValue__, this.interop);
            }
         }

         if ((state_0 & 128) != 0 && propertyNodeValue_ instanceof Integer) {
            int propertyNodeValue__ = (Integer)propertyNodeValue_;
            if (JSGuards.isForeignObject(targetNodeValue) && this.interop.hasArrayElements(targetNodeValue)) {
               return this.arrayElementInt(targetNodeValue, propertyNodeValue__, this.interop);
            }
         }

         if ((state_0 & 768) != 0) {
            if ((state_0 & 256) != 0 && JSGuards.isForeignObject(targetNodeValue)) {
               return this.foreignObject(targetNodeValue, propertyNodeValue_, this.interop, this.toArrayIndex, this.toPropertyKey);
            }

            if ((state_0 & 512) != 0 && !JSGuards.isTruffleObject(targetNodeValue) && !JSGuards.isString(targetNodeValue)) {
               return this.doOther(targetNodeValue, propertyNodeValue_, this.toPropertyKey);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(targetNodeValue, propertyNodeValue_);
   }

   @Override
   public boolean executeBoolean(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      return (state_0 & 895) == 0 && state_0 != 0 ? this.executeBoolean_int2(state_0, frameValue) : this.executeBoolean_generic3(state_0, frameValue);
   }

   private boolean executeBoolean_int2(int state_0, VirtualFrame frameValue) {
      Object targetNodeValue_ = super.targetNode.execute(frameValue);

      int propertyNodeValue_;
      try {
         propertyNodeValue_ = super.propertyNode.executeInt(frameValue);
      } catch (UnexpectedResultException var6) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(targetNodeValue_, var6.getResult());
      }

      assert (state_0 & 128) != 0;

      if (JSGuards.isForeignObject(targetNodeValue_) && this.interop.hasArrayElements(targetNodeValue_)) {
         return this.arrayElementInt(targetNodeValue_, propertyNodeValue_, this.interop);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(targetNodeValue_, propertyNodeValue_);
      }
   }

   private boolean executeBoolean_generic3(int state_0, VirtualFrame frameValue) {
      Object targetNodeValue_ = super.targetNode.execute(frameValue);
      Object propertyNodeValue_ = super.propertyNode.execute(frameValue);
      if ((state_0 & 63) != 0) {
         if ((state_0 & 3) != 0 && targetNodeValue_ instanceof JSDynamicObject) {
            JSDynamicObject targetNodeValue__ = (JSDynamicObject)targetNodeValue_;
            if ((state_0 & 1) != 0 && JSGuards.isJSOrdinaryObject(targetNodeValue__)) {
               return this.doJSOrdinaryObject(targetNodeValue__, propertyNodeValue_, this.toPropertyKey, this.jSOrdinaryObject_dynamicObjectLib_);
            }

            if ((state_0 & 2) != 0 && !JSGuards.isJSOrdinaryObject(targetNodeValue__)) {
               return this.doJSObject(
                  targetNodeValue__,
                  propertyNodeValue_,
                  this.jSObject_isArrayNode_,
                  this.jSObject_arrayProfile_,
                  this.jSObject_toArrayIndexNode_,
                  this.jSObject_arrayIndexProfile_,
                  this.jSObject_deleteArrayIndexNode_,
                  this.jSObject_jsclassProfile_,
                  this.toPropertyKey
               );
            }
         }

         if ((state_0 & 4) != 0 && targetNodeValue_ instanceof Symbol) {
            Symbol targetNodeValue__x = (Symbol)targetNodeValue_;
            return DeletePropertyNode.doSymbol(targetNodeValue__x, propertyNodeValue_, this.toPropertyKey);
         }

         if ((state_0 & 8) != 0 && targetNodeValue_ instanceof SafeInteger) {
            SafeInteger targetNodeValue__x = (SafeInteger)targetNodeValue_;
            return DeletePropertyNode.doSafeInteger(targetNodeValue__x, propertyNodeValue_, this.toPropertyKey);
         }

         if ((state_0 & 16) != 0 && targetNodeValue_ instanceof BigInt) {
            BigInt targetNodeValue__x = (BigInt)targetNodeValue_;
            return DeletePropertyNode.doBigInt(targetNodeValue__x, propertyNodeValue_, this.toPropertyKey);
         }

         if ((state_0 & 32) != 0 && targetNodeValue_ instanceof TruffleString) {
            TruffleString targetNodeValue__x = (TruffleString)targetNodeValue_;
            return this.doString(targetNodeValue__x, propertyNodeValue_, this.toArrayIndex, this.string_equalsNode_);
         }
      }

      if ((state_0 & 960) != 0) {
         if ((state_0 & 64) != 0 && propertyNodeValue_ instanceof TruffleString) {
            TruffleString propertyNodeValue__ = (TruffleString)propertyNodeValue_;
            if (JSGuards.isForeignObject(targetNodeValue_) && !this.interop.hasArrayElements(targetNodeValue_)) {
               return this.member(targetNodeValue_, propertyNodeValue__, this.interop);
            }
         }

         if ((state_0 & 128) != 0 && propertyNodeValue_ instanceof Integer) {
            int propertyNodeValue__ = (Integer)propertyNodeValue_;
            if (JSGuards.isForeignObject(targetNodeValue_) && this.interop.hasArrayElements(targetNodeValue_)) {
               return this.arrayElementInt(targetNodeValue_, propertyNodeValue__, this.interop);
            }
         }

         if ((state_0 & 768) != 0) {
            if ((state_0 & 256) != 0 && JSGuards.isForeignObject(targetNodeValue_)) {
               return this.foreignObject(targetNodeValue_, propertyNodeValue_, this.interop, this.toArrayIndex, this.toPropertyKey);
            }

            if ((state_0 & 512) != 0 && !JSGuards.isTruffleObject(targetNodeValue_) && !JSGuards.isString(targetNodeValue_)) {
               return this.doOther(targetNodeValue_, propertyNodeValue_, this.toPropertyKey);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(targetNodeValue_, propertyNodeValue_);
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.executeBoolean(frameValue);
   }

   private boolean executeAndSpecialize(Object targetNodeValue, Object propertyNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (targetNodeValue instanceof JSDynamicObject) {
            JSDynamicObject targetNodeValue_ = (JSDynamicObject)targetNodeValue;
            if (JSGuards.isJSOrdinaryObject(targetNodeValue_)) {
               this.toPropertyKey = super.insert(this.toPropertyKey == null ? JSToPropertyKeyNode.create() : this.toPropertyKey);
               this.jSOrdinaryObject_dynamicObjectLib_ = super.insert(DYNAMIC_OBJECT_LIBRARY_.createDispatched(5));
               int var23;
               this.state_0_ = var23 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doJSOrdinaryObject(targetNodeValue_, propertyNodeValue, this.toPropertyKey, this.jSOrdinaryObject_dynamicObjectLib_);
            }

            if (!JSGuards.isJSOrdinaryObject(targetNodeValue_)) {
               this.jSObject_isArrayNode_ = super.insert(IsArrayNode.createIsFastArray());
               this.jSObject_arrayProfile_ = ConditionProfile.createBinaryProfile();
               this.jSObject_toArrayIndexNode_ = super.insert(ToArrayIndexNode.create());
               this.jSObject_arrayIndexProfile_ = ConditionProfile.createBinaryProfile();
               this.jSObject_deleteArrayIndexNode_ = super.insert(JSArrayDeleteIndexNode.create(this.context, this.strict));
               this.jSObject_jsclassProfile_ = JSClassProfile.create();
               this.toPropertyKey = super.insert(this.toPropertyKey == null ? JSToPropertyKeyNode.create() : this.toPropertyKey);
               int var22;
               this.state_0_ = var22 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doJSObject(
                  targetNodeValue_,
                  propertyNodeValue,
                  this.jSObject_isArrayNode_,
                  this.jSObject_arrayProfile_,
                  this.jSObject_toArrayIndexNode_,
                  this.jSObject_arrayIndexProfile_,
                  this.jSObject_deleteArrayIndexNode_,
                  this.jSObject_jsclassProfile_,
                  this.toPropertyKey
               );
            }
         }

         if (targetNodeValue instanceof Symbol) {
            Symbol targetNodeValue_x = (Symbol)targetNodeValue;
            this.toPropertyKey = super.insert(this.toPropertyKey == null ? JSToPropertyKeyNode.create() : this.toPropertyKey);
            int var21;
            this.state_0_ = var21 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            return DeletePropertyNode.doSymbol(targetNodeValue_x, propertyNodeValue, this.toPropertyKey);
         } else if (targetNodeValue instanceof SafeInteger) {
            SafeInteger targetNodeValue_x = (SafeInteger)targetNodeValue;
            this.toPropertyKey = super.insert(this.toPropertyKey == null ? JSToPropertyKeyNode.create() : this.toPropertyKey);
            int var20;
            this.state_0_ = var20 = state_0 | 8;
            lock.unlock();
            hasLock = false;
            return DeletePropertyNode.doSafeInteger(targetNodeValue_x, propertyNodeValue, this.toPropertyKey);
         } else if (targetNodeValue instanceof BigInt) {
            BigInt targetNodeValue_x = (BigInt)targetNodeValue;
            this.toPropertyKey = super.insert(this.toPropertyKey == null ? JSToPropertyKeyNode.create() : this.toPropertyKey);
            int var19;
            this.state_0_ = var19 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return DeletePropertyNode.doBigInt(targetNodeValue_x, propertyNodeValue, this.toPropertyKey);
         } else if (targetNodeValue instanceof TruffleString) {
            TruffleString targetNodeValue_x = (TruffleString)targetNodeValue;
            this.toArrayIndex = super.insert(this.toArrayIndex == null ? ToArrayIndexNode.create() : this.toArrayIndex);
            this.string_equalsNode_ = super.insert(TruffleString.EqualNode.create());
            int var18;
            this.state_0_ = var18 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return this.doString(targetNodeValue_x, propertyNodeValue, this.toArrayIndex, this.string_equalsNode_);
         } else {
            if ((exclude & 1) == 0 && propertyNodeValue instanceof TruffleString) {
               TruffleString propertyNodeValue_ = (TruffleString)propertyNodeValue;
               if (JSGuards.isForeignObject(targetNodeValue)) {
                  InteropLibrary member_interop__ = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                  if (!member_interop__.hasArrayElements(targetNodeValue)) {
                     if (this.interop == null) {
                        InteropLibrary member_interop___check = super.insert(member_interop__);
                        if (member_interop___check == null) {
                           throw new AssertionError(
                              "Specialization 'member(Object, TruffleString, InteropLibrary)' contains a shared cache with name 'interop' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.interop = member_interop___check;
                     }

                     int var17;
                     this.state_0_ = var17 = state_0 | 64;
                     lock.unlock();
                     hasLock = false;
                     return this.member(targetNodeValue, propertyNodeValue_, member_interop__);
                  }
               }
            }

            if ((exclude & 2) == 0 && propertyNodeValue instanceof Integer) {
               int propertyNodeValue_ = (Integer)propertyNodeValue;
               if (JSGuards.isForeignObject(targetNodeValue)) {
                  InteropLibrary arrayElementInt_interop__ = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop);
                  if (arrayElementInt_interop__.hasArrayElements(targetNodeValue)) {
                     if (this.interop == null) {
                        InteropLibrary arrayElementInt_interop___check = super.insert(arrayElementInt_interop__);
                        if (arrayElementInt_interop___check == null) {
                           throw new AssertionError(
                              "Specialization 'arrayElementInt(Object, int, InteropLibrary)' contains a shared cache with name 'interop' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.interop = arrayElementInt_interop___check;
                     }

                     int var16;
                     this.state_0_ = var16 = state_0 | 128;
                     lock.unlock();
                     hasLock = false;
                     return this.arrayElementInt(targetNodeValue, propertyNodeValue_, arrayElementInt_interop__);
                  }
               }
            }

            if (JSGuards.isForeignObject(targetNodeValue)) {
               this.interop = super.insert(this.interop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.interop);
               this.toArrayIndex = super.insert(this.toArrayIndex == null ? ToArrayIndexNode.create() : this.toArrayIndex);
               this.toPropertyKey = super.insert(this.toPropertyKey == null ? JSToPropertyKeyNode.create() : this.toPropertyKey);
               int var24;
               this.exclude_ = var24 = exclude | 3;
               state_0 &= -193;
               int var15;
               this.state_0_ = var15 = state_0 | 256;
               lock.unlock();
               hasLock = false;
               return this.foreignObject(targetNodeValue, propertyNodeValue, this.interop, this.toArrayIndex, this.toPropertyKey);
            } else if (!JSGuards.isTruffleObject(targetNodeValue) && !JSGuards.isString(targetNodeValue)) {
               this.toPropertyKey = super.insert(this.toPropertyKey == null ? JSToPropertyKeyNode.create() : this.toPropertyKey);
               int var13;
               this.state_0_ = var13 = state_0 | 512;
               lock.unlock();
               hasLock = false;
               return this.doOther(targetNodeValue, propertyNodeValue, this.toPropertyKey);
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{super.targetNode, super.propertyNode}, targetNodeValue, propertyNodeValue);
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
         return (state_0 & state_0 - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[11];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doJSOrdinaryObject", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.toPropertyKey, this.jSOrdinaryObject_dynamicObjectLib_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doJSObject", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(
            Arrays.asList(
               this.jSObject_isArrayNode_,
               this.jSObject_arrayProfile_,
               this.jSObject_toArrayIndexNode_,
               this.jSObject_arrayIndexProfile_,
               this.jSObject_deleteArrayIndexNode_,
               this.jSObject_jsclassProfile_,
               this.toPropertyKey
            )
         );
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doSymbol", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.toPropertyKey));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"doSafeInteger", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.toPropertyKey));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doBigInt", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.toPropertyKey));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doString", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.toArrayIndex, this.string_equalsNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"member", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.interop));
         s[2] = cached;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"arrayElementInt", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.interop));
         s[2] = cached;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"foreignObject", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.interop, this.toArrayIndex, this.toPropertyKey));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doOther", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.toPropertyKey));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      return Introspection.Provider.create(data);
   }

   public static DeletePropertyNode create(boolean strict, JSContext context, JavaScriptNode targetNode, JavaScriptNode propertyNode) {
      return new DeletePropertyNodeGen(strict, context, targetNode, propertyNode);
   }
}
