package com.oracle.truffle.js.nodes.binary;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsPrimitiveNode;
import com.oracle.truffle.js.nodes.cast.JSToBooleanNode;
import com.oracle.truffle.js.nodes.cast.JSToPrimitiveNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSEqualNode.class)
public final class JSEqualNodeGen extends JSEqualNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int state_1_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private InteropLibrary bInterop;
   @Node.Child
   private InteropLibrary aInterop;
   @Node.Child
   private JSToPrimitiveNode toPrimitive;
   @Node.Child
   private IsPrimitiveNode isPrimitive;
   @Node.Child
   private JSEqualNode equal;
   @Node.Child
   private TruffleString.EqualNode string_equalsNode_;
   @Node.Child
   private JSOverloadedBinaryNode overloaded_overloadedOperatorNode_;
   @Node.Child
   private JSToBooleanNode overloaded_toBooleanNode_;

   private JSEqualNodeGen(JavaScriptNode left, JavaScriptNode right) {
      super(left, right);
   }

   private boolean fallbackGuard_(int state_0, int state_1, Object leftNodeValue, Object rightNodeValue) {
      if (JSTypesGen.isImplicitDouble(leftNodeValue) && JSTypesGen.isImplicitDouble(rightNodeValue)) {
         return false;
      } else if ((state_0 & 8) == 0 && leftNodeValue instanceof BigInt && rightNodeValue instanceof BigInt) {
         return false;
      } else {
         if (JSTypesGen.isImplicitDouble(leftNodeValue)) {
            if ((state_0 & 16) == 0 && rightNodeValue instanceof TruffleString) {
               return false;
            }

            if ((state_0 & 32) == 0 && rightNodeValue instanceof Boolean) {
               return false;
            }
         }

         if (leftNodeValue instanceof Boolean) {
            if ((state_0 & 64) == 0 && rightNodeValue instanceof Boolean) {
               return false;
            }

            if (JSTypesGen.isImplicitDouble(rightNodeValue)) {
               return false;
            }

            if ((state_0 & 512) == 0 && rightNodeValue instanceof TruffleString) {
               return false;
            }
         }

         if (leftNodeValue instanceof TruffleString) {
            if ((state_0 & 2048) == 0 && rightNodeValue instanceof TruffleString) {
               return false;
            }

            if (JSTypesGen.isImplicitDouble(rightNodeValue)) {
               return false;
            }

            if ((state_0 & 8192) == 0 && rightNodeValue instanceof Boolean) {
               return false;
            }

            if ((state_0 & 16384) == 0 && rightNodeValue instanceof BigInt) {
               return false;
            }
         }

         if ((state_0 & 32768) == 0 && leftNodeValue instanceof BigInt && rightNodeValue instanceof TruffleString) {
            return false;
         } else if ((state_0 & 65536) == 0 && leftNodeValue instanceof Boolean && rightNodeValue instanceof BigInt) {
            return false;
         } else if ((state_0 & 131072) == 0 && leftNodeValue instanceof BigInt && rightNodeValue instanceof Boolean) {
            return false;
         } else if ((state_0 & 262144) == 0 && JSRuntime.isNullOrUndefined(leftNodeValue) && JSRuntime.isNullOrUndefined(rightNodeValue)) {
            return false;
         } else if ((state_0 & 524288) == 0 && JSRuntime.isNullOrUndefined(leftNodeValue)) {
            return false;
         } else if ((state_0 & 1048576) == 0 && JSRuntime.isNullOrUndefined(rightNodeValue)) {
            return false;
         } else if ((state_0 & 2097152) != 0 || !this.hasOverloadedOperators(leftNodeValue) && !this.hasOverloadedOperators(rightNodeValue)) {
            if (leftNodeValue instanceof JSObject && rightNodeValue instanceof JSDynamicObject) {
               JSObject leftNodeValue_ = (JSObject)leftNodeValue;
               if (!this.hasOverloadedOperators(leftNodeValue_)) {
                  JSDynamicObject rightNodeValue_ = (JSDynamicObject)rightNodeValue;
                  if (!this.hasOverloadedOperators(rightNodeValue_)) {
                     return false;
                  }
               }
            }

            if (leftNodeValue instanceof JSDynamicObject && rightNodeValue instanceof JSObject) {
               JSDynamicObject leftNodeValue_ = (JSDynamicObject)leftNodeValue;
               if (!this.hasOverloadedOperators(leftNodeValue_)) {
                  JSObject rightNodeValue_ = (JSObject)rightNodeValue;
                  if (!this.hasOverloadedOperators(rightNodeValue_)) {
                     return false;
                  }
               }
            }

            if (leftNodeValue instanceof JSObject) {
               JSObject leftNodeValue_ = (JSObject)leftNodeValue;
               if (!this.hasOverloadedOperators(leftNodeValue_) && ((state_0 & 33554432) == 0 || this.isPrimitive.executeBoolean(rightNodeValue))) {
                  return false;
               }
            }

            if (rightNodeValue instanceof JSObject) {
               JSObject rightNodeValue_ = (JSObject)rightNodeValue;
               if (!this.hasOverloadedOperators(rightNodeValue_) && ((state_0 & 134217728) == 0 || this.isPrimitive.executeBoolean(leftNodeValue))) {
                  return false;
               }
            }

            if (leftNodeValue instanceof BigInt && JSTypesGen.isImplicitDouble(rightNodeValue)) {
               return false;
            } else if (JSTypesGen.isImplicitDouble(leftNodeValue) && rightNodeValue instanceof BigInt) {
               return false;
            } else {
               if (leftNodeValue instanceof Symbol) {
                  if ((state_1 & 1) == 0 && rightNodeValue instanceof Symbol) {
                     return false;
                  }

                  if ((state_1 & 2) == 0 && !JSGuards.isSymbol(rightNodeValue) && !JSRuntime.isObject(rightNodeValue)) {
                     return false;
                  }
               }

               if ((state_1 & 4) == 0 && rightNodeValue instanceof Symbol && !JSGuards.isSymbol(leftNodeValue) && !JSRuntime.isObject(leftNodeValue)) {
                  return false;
               } else if ((state_1 & 8) != 0 || !JSRuntime.isForeignObject(leftNodeValue) && !JSRuntime.isForeignObject(rightNodeValue)) {
                  if (leftNodeValue instanceof Number && rightNodeValue instanceof Number) {
                     Number leftNodeValue_ = (Number)leftNodeValue;
                     if (JSRuntime.isJavaNumber(leftNodeValue_)) {
                        Number rightNodeValue_ = (Number)rightNodeValue;
                        if (JSRuntime.isJavaNumber(rightNodeValue_)) {
                           return false;
                        }
                     }
                  }

                  return (state_1 & 32) == 0 && rightNodeValue instanceof TruffleString && JSRuntime.isJavaNumber(leftNodeValue)
                     ? false
                     : (state_1 & 64) != 0 || !(leftNodeValue instanceof TruffleString) || !JSRuntime.isJavaNumber(rightNodeValue);
               } else {
                  return false;
               }
            }
         } else {
            return false;
         }
      }
   }

   @Override
   public boolean executeBoolean(Object leftNodeValue, Object rightNodeValue) {
      int state_0 = this.state_0_;
      int state_1 = this.state_1_;
      if ((state_0 & 3) != 0 && leftNodeValue instanceof Integer) {
         int leftNodeValue_ = (Integer)leftNodeValue;
         if ((state_0 & 1) != 0 && rightNodeValue instanceof Integer) {
            int rightNodeValue_ = (Integer)rightNodeValue;
            return JSEqualNode.doInt(leftNodeValue_, rightNodeValue_);
         }

         if ((state_0 & 2) != 0 && rightNodeValue instanceof Boolean) {
            boolean rightNodeValue_ = (Boolean)rightNodeValue;
            return JSEqualNode.doIntBoolean(leftNodeValue_, rightNodeValue_);
         }
      }

      if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_1 & 3840) >>> 8, leftNodeValue)) {
         double leftNodeValue_x = JSTypesGen.asImplicitDouble((state_1 & 3840) >>> 8, leftNodeValue);
         if (JSTypesGen.isImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue)) {
            double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue);
            return JSEqualNode.doDouble(leftNodeValue_x, rightNodeValue_);
         }
      }

      if ((state_0 & 8) != 0 && leftNodeValue instanceof BigInt) {
         BigInt leftNodeValue_x = (BigInt)leftNodeValue;
         if (rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_ = (BigInt)rightNodeValue;
            return JSEqualNode.doBigInt(leftNodeValue_x, rightNodeValue_);
         }
      }

      if ((state_0 & 48) != 0 && JSTypesGen.isImplicitDouble((state_1 & 3840) >>> 8, leftNodeValue)) {
         double leftNodeValue_x = JSTypesGen.asImplicitDouble((state_1 & 3840) >>> 8, leftNodeValue);
         if ((state_0 & 16) != 0 && rightNodeValue instanceof TruffleString) {
            TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
            return this.doDoubleString(leftNodeValue_x, rightNodeValue_);
         }

         if ((state_0 & 32) != 0 && rightNodeValue instanceof Boolean) {
            boolean rightNodeValue_ = (Boolean)rightNodeValue;
            return JSEqualNode.doDoubleBoolean(leftNodeValue_x, rightNodeValue_);
         }
      }

      if ((state_0 & 960) != 0 && leftNodeValue instanceof Boolean) {
         boolean leftNodeValue_xx = (Boolean)leftNodeValue;
         if ((state_0 & 64) != 0 && rightNodeValue instanceof Boolean) {
            boolean rightNodeValue_ = (Boolean)rightNodeValue;
            return JSEqualNode.doBoolean(leftNodeValue_xx, rightNodeValue_);
         }

         if ((state_0 & 128) != 0 && rightNodeValue instanceof Integer) {
            int rightNodeValue_ = (Integer)rightNodeValue;
            return JSEqualNode.doBooleanInt(leftNodeValue_xx, rightNodeValue_);
         }

         if ((state_0 & 256) != 0 && JSTypesGen.isImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue)) {
            double rightNodeValue_ = JSTypesGen.asImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue);
            return JSEqualNode.doBooleanDouble(leftNodeValue_xx, rightNodeValue_);
         }

         if ((state_0 & 512) != 0 && rightNodeValue instanceof TruffleString) {
            TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
            return this.doBooleanString(leftNodeValue_xx, rightNodeValue_);
         }
      }

      if ((state_0 & 31744) != 0 && leftNodeValue instanceof TruffleString) {
         TruffleString leftNodeValue_xxx = (TruffleString)leftNodeValue;
         if ((state_0 & 3072) != 0 && rightNodeValue instanceof TruffleString) {
            TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
            if ((state_0 & 1024) != 0 && JSGuards.isReferenceEquals(leftNodeValue_xxx, rightNodeValue_)) {
               return JSEqualNode.doStringIdentity(leftNodeValue_xxx, rightNodeValue_);
            }

            if ((state_0 & 2048) != 0) {
               return JSEqualNode.doString(leftNodeValue_xxx, rightNodeValue_, this.string_equalsNode_);
            }
         }

         if ((state_0 & 4096) != 0 && JSTypesGen.isImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue)) {
            double rightNodeValue_x = JSTypesGen.asImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue);
            return this.doStringDouble(leftNodeValue_xxx, rightNodeValue_x);
         }

         if ((state_0 & 8192) != 0 && rightNodeValue instanceof Boolean) {
            boolean rightNodeValue_x = (Boolean)rightNodeValue;
            return this.doStringBoolean(leftNodeValue_xxx, rightNodeValue_x);
         }

         if ((state_0 & 16384) != 0 && rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_x = (BigInt)rightNodeValue;
            return this.doStringBigInt(leftNodeValue_xxx, rightNodeValue_x);
         }
      }

      if ((state_0 & 32768) != 0 && leftNodeValue instanceof BigInt) {
         BigInt leftNodeValue_xxxx = (BigInt)leftNodeValue;
         if (rightNodeValue instanceof TruffleString) {
            TruffleString rightNodeValue_x = (TruffleString)rightNodeValue;
            return this.doBigIntString(leftNodeValue_xxxx, rightNodeValue_x);
         }
      }

      if ((state_0 & 65536) != 0 && leftNodeValue instanceof Boolean) {
         boolean leftNodeValue_xxxx = (Boolean)leftNodeValue;
         if (rightNodeValue instanceof BigInt) {
            BigInt rightNodeValue_x = (BigInt)rightNodeValue;
            return this.doBooleanBigInt(leftNodeValue_xxxx, rightNodeValue_x);
         }
      }

      if ((state_0 & 131072) != 0 && leftNodeValue instanceof BigInt) {
         BigInt leftNodeValue_xxxx = (BigInt)leftNodeValue;
         if (rightNodeValue instanceof Boolean) {
            boolean rightNodeValue_x = (Boolean)rightNodeValue;
            return this.doBigIntBoolean(leftNodeValue_xxxx, rightNodeValue_x);
         }
      }

      if ((state_0 & 3932160) != 0) {
         if ((state_0 & 262144) != 0 && JSRuntime.isNullOrUndefined(leftNodeValue) && JSRuntime.isNullOrUndefined(rightNodeValue)) {
            return JSEqualNode.doBothNullOrUndefined(leftNodeValue, rightNodeValue);
         }

         if ((state_0 & 524288) != 0 && JSRuntime.isNullOrUndefined(leftNodeValue)) {
            return JSEqualNode.doLeftNullOrUndefined(leftNodeValue, rightNodeValue, this.bInterop);
         }

         if ((state_0 & 1048576) != 0 && JSRuntime.isNullOrUndefined(rightNodeValue)) {
            return JSEqualNode.doRightNullOrUndefined(leftNodeValue, rightNodeValue, this.aInterop);
         }

         if ((state_0 & 2097152) != 0 && (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue))) {
            return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_, this.overloaded_toBooleanNode_);
         }
      }

      if ((state_0 & 4194304) != 0 && leftNodeValue instanceof JSObject) {
         JSObject leftNodeValue_xxxx = (JSObject)leftNodeValue;
         if (rightNodeValue instanceof JSDynamicObject) {
            JSDynamicObject rightNodeValue_x = (JSDynamicObject)rightNodeValue;
            if (!this.hasOverloadedOperators(leftNodeValue_xxxx) && !this.hasOverloadedOperators(rightNodeValue_x)) {
               return JSEqualNode.doJSObject(leftNodeValue_xxxx, rightNodeValue_x);
            }
         }
      }

      if ((state_0 & 8388608) != 0 && leftNodeValue instanceof JSDynamicObject) {
         JSDynamicObject leftNodeValue_xxxx = (JSDynamicObject)leftNodeValue;
         if (rightNodeValue instanceof JSObject) {
            JSObject rightNodeValue_x = (JSObject)rightNodeValue;
            if (!this.hasOverloadedOperators(leftNodeValue_xxxx) && !this.hasOverloadedOperators(rightNodeValue_x)) {
               return JSEqualNode.doJSObject(leftNodeValue_xxxx, rightNodeValue_x);
            }
         }
      }

      if ((state_0 & 16777216) != 0 && leftNodeValue instanceof JSObject) {
         JSObject leftNodeValue_xxxx = (JSObject)leftNodeValue;
         if (!this.hasOverloadedOperators(leftNodeValue_xxxx) && this.isPrimitive.executeBoolean(rightNodeValue)) {
            return this.doJSObjectVsPrimitive(leftNodeValue_xxxx, rightNodeValue, this.bInterop, this.toPrimitive, this.isPrimitive, this.equal);
         }
      }

      if ((state_0 & 67108864) != 0 && rightNodeValue instanceof JSObject) {
         JSObject rightNodeValue_x = (JSObject)rightNodeValue;
         if (!this.hasOverloadedOperators(rightNodeValue_x) && this.isPrimitive.executeBoolean(leftNodeValue)) {
            return this.doJSObjectVsPrimitive(leftNodeValue, rightNodeValue_x, this.aInterop, this.toPrimitive, this.isPrimitive, this.equal);
         }
      }

      if ((state_0 & 805306368) != 0 && leftNodeValue instanceof BigInt) {
         BigInt leftNodeValue_xxxx = (BigInt)leftNodeValue;
         if ((state_0 & 268435456) != 0 && rightNodeValue instanceof Integer) {
            int rightNodeValue_x = (Integer)rightNodeValue;
            return this.doBigIntAndInt(leftNodeValue_xxxx, rightNodeValue_x);
         }

         if ((state_0 & 536870912) != 0 && JSTypesGen.isImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue)) {
            double rightNodeValue_x = JSTypesGen.asImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue);
            return this.doBigIntAndNumber(leftNodeValue_xxxx, rightNodeValue_x);
         }
      }

      if ((state_0 & -1073741824) != 0 && rightNodeValue instanceof BigInt) {
         BigInt rightNodeValue_x = (BigInt)rightNodeValue;
         if ((state_0 & 1073741824) != 0 && leftNodeValue instanceof Integer) {
            int leftNodeValue_xxxxx = (Integer)leftNodeValue;
            return this.doIntAndBigInt(leftNodeValue_xxxxx, rightNodeValue_x);
         }

         if ((state_0 & -2147483648) != 0 && JSTypesGen.isImplicitDouble((state_1 & 3840) >>> 8, leftNodeValue)) {
            double leftNodeValue_xxxxx = JSTypesGen.asImplicitDouble((state_1 & 3840) >>> 8, leftNodeValue);
            return this.doNumberAndBigInt(leftNodeValue_xxxxx, rightNodeValue_x);
         }
      }

      if ((state_1 & 3) != 0 && leftNodeValue instanceof Symbol) {
         Symbol leftNodeValue_xxxxx = (Symbol)leftNodeValue;
         if ((state_1 & 1) != 0 && rightNodeValue instanceof Symbol) {
            Symbol rightNodeValue_xx = (Symbol)rightNodeValue;
            return JSEqualNode.doSymbol(leftNodeValue_xxxxx, rightNodeValue_xx);
         }

         if ((state_1 & 2) != 0 && !JSGuards.isSymbol(rightNodeValue) && !JSRuntime.isObject(rightNodeValue)) {
            return JSEqualNode.doSymbolNotSymbol(leftNodeValue_xxxxx, rightNodeValue);
         }
      }

      if ((state_1 & 12) != 0) {
         if ((state_1 & 4) != 0 && rightNodeValue instanceof Symbol) {
            Symbol rightNodeValue_xx = (Symbol)rightNodeValue;
            if (!JSGuards.isSymbol(leftNodeValue) && !JSRuntime.isObject(leftNodeValue)) {
               return JSEqualNode.doSymbolNotSymbol(leftNodeValue, rightNodeValue_xx);
            }
         }

         if ((state_1 & 8) != 0) {
            boolean foreign_isAForeign__ = JSRuntime.isForeignObject(leftNodeValue);
            boolean foreign_isBForeign__ = JSRuntime.isForeignObject(rightNodeValue);
            if (foreign_isAForeign__ || foreign_isBForeign__) {
               return this.doForeign(
                  leftNodeValue,
                  rightNodeValue,
                  foreign_isAForeign__,
                  foreign_isBForeign__,
                  this.aInterop,
                  this.bInterop,
                  this.toPrimitive,
                  this.isPrimitive,
                  this.equal
               );
            }
         }
      }

      if ((state_1 & 16) != 0 && leftNodeValue instanceof Number) {
         Number leftNodeValue_xxxxxx = (Number)leftNodeValue;
         if (rightNodeValue instanceof Number) {
            Number rightNodeValue_xx = (Number)rightNodeValue;
            if (JSRuntime.isJavaNumber(leftNodeValue_xxxxxx) && JSRuntime.isJavaNumber(rightNodeValue_xx)) {
               return JSEqualNode.doNumber(leftNodeValue_xxxxxx, rightNodeValue_xx);
            }
         }
      }

      if ((state_1 & 32) != 0 && rightNodeValue instanceof TruffleString) {
         TruffleString rightNodeValue_xx = (TruffleString)rightNodeValue;
         if (JSRuntime.isJavaNumber(leftNodeValue)) {
            return this.doNumberString(leftNodeValue, rightNodeValue_xx);
         }
      }

      if ((state_1 & 192) != 0) {
         if ((state_1 & 64) != 0 && leftNodeValue instanceof TruffleString) {
            TruffleString leftNodeValue_xxxxxx = (TruffleString)leftNodeValue;
            if (JSRuntime.isJavaNumber(rightNodeValue)) {
               return this.doStringNumber(leftNodeValue_xxxxxx, rightNodeValue);
            }
         }

         if ((state_1 & 128) != 0 && this.fallbackGuard_(state_0, state_1, leftNodeValue, rightNodeValue)) {
            return JSEqualNode.doFallback(leftNodeValue, rightNodeValue);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(leftNodeValue, rightNodeValue);
   }

   @Override
   public boolean executeBoolean(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      int state_1 = this.state_1_;
      if ((state_0 & -167772162) != 0 || (state_1 & 0xFF) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
         if ((state_0 & -167772163) != 0 || (state_1 & 0xFF) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
            if ((state_0 & -167772165) != 0 || (state_1 & 0xFF) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
               if ((state_0 & -167772193) != 0 || (state_1 & 0xFF) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
                  if ((state_0 & -167772225) != 0 || (state_1 & 0xFF) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
                     if ((state_0 & -167772289) != 0 || (state_1 & 0xFF) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
                        if ((state_0 & -167772417) != 0 || (state_1 & 0xFF) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
                           if ((state_0 & 1979711439) != 0 || (state_1 & 0xFF) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
                              if ((state_0 & -167838209) != 0 || (state_1 & 0xFF) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
                                 if ((state_0 & -704647169) != 0 || (state_1 & 0xFF) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
                                    if ((state_0 & -167911425) != 0 || (state_1 & 0xFF) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
                                       if ((state_0 & -436207617) != 0 || (state_1 & 0xFF) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
                                          return (state_0 & -1241513985) != 0 || (state_1 & 0xFF) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0
                                             ? this.executeBoolean_generic13(state_0, state_1, frameValue)
                                             : this.executeBoolean_int12(state_0, state_1, frameValue);
                                       } else {
                                          return this.executeBoolean_int11(state_0, state_1, frameValue);
                                       }
                                    } else {
                                       return this.executeBoolean_boolean10(state_0, state_1, frameValue);
                                    }
                                 } else {
                                    return this.executeBoolean_double9(state_0, state_1, frameValue);
                                 }
                              } else {
                                 return this.executeBoolean_boolean8(state_0, state_1, frameValue);
                              }
                           } else {
                              return this.executeBoolean_double7(state_0, state_1, frameValue);
                           }
                        } else {
                           return this.executeBoolean_boolean_double6(state_0, state_1, frameValue);
                        }
                     } else {
                        return this.executeBoolean_boolean_int5(state_0, state_1, frameValue);
                     }
                  } else {
                     return this.executeBoolean_boolean_boolean4(state_0, state_1, frameValue);
                  }
               } else {
                  return this.executeBoolean_double_boolean3(state_0, state_1, frameValue);
               }
            } else {
               return this.executeBoolean_double_double2(state_0, state_1, frameValue);
            }
         } else {
            return this.executeBoolean_int_boolean1(state_0, state_1, frameValue);
         }
      } else {
         return this.executeBoolean_int_int0(state_0, state_1, frameValue);
      }
   }

   private boolean executeBoolean_int_int0(int state_0, int state_1, VirtualFrame frameValue) {
      int leftNodeValue_;
      try {
         leftNodeValue_ = super.leftNode.executeInt(frameValue);
      } catch (UnexpectedResultException var8) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var8.getResult(), rightNodeValue);
      }

      int rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeInt(frameValue);
      } catch (UnexpectedResultException var7) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var7.getResult());
      }

      assert (state_0 & 1) != 0;

      return JSEqualNode.doInt(leftNodeValue_, rightNodeValue_);
   }

   private boolean executeBoolean_int_boolean1(int state_0, int state_1, VirtualFrame frameValue) {
      int leftNodeValue_;
      try {
         leftNodeValue_ = super.leftNode.executeInt(frameValue);
      } catch (UnexpectedResultException var8) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var8.getResult(), rightNodeValue);
      }

      boolean rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var7) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var7.getResult());
      }

      assert (state_0 & 2) != 0;

      return JSEqualNode.doIntBoolean(leftNodeValue_, rightNodeValue_);
   }

   private boolean executeBoolean_double_double2(int state_0, int state_1, VirtualFrame frameValue) {
      long leftNodeValue_long = 0L;
      int leftNodeValue_int = 0;

      double leftNodeValue_;
      try {
         if ((state_1 & 3584) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
            if ((state_1 & 3328) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
               if ((state_1 & 1792) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
                  Object leftNodeValue__ = super.leftNode.execute(frameValue);
                  leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_1 & 3840) >>> 8, leftNodeValue__);
               } else {
                  leftNodeValue_long = super.leftNode.executeLong(frameValue);
                  leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
               }
            } else {
               leftNodeValue_int = super.leftNode.executeInt(frameValue);
               leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            }
         } else {
            leftNodeValue_ = super.leftNode.executeDouble(frameValue);
         }
      } catch (UnexpectedResultException var16) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var16.getResult(), rightNodeValue);
      }

      long rightNodeValue_long = 0L;
      int rightNodeValue_int = 0;

      double rightNodeValue_;
      try {
         if ((state_1 & 57344) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
            if ((state_1 & 53248) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
               if ((state_1 & 28672) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
                  Object rightNodeValue__ = super.rightNode.execute(frameValue);
                  rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue__);
               } else {
                  rightNodeValue_long = super.rightNode.executeLong(frameValue);
                  rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
               }
            } else {
               rightNodeValue_int = super.rightNode.executeInt(frameValue);
               rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            }
         } else {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         }
      } catch (UnexpectedResultException var15) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_1 & 3328) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0
               ? ((state_1 & 1792) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0 ? leftNodeValue_ : leftNodeValue_long)
               : leftNodeValue_int,
            var15.getResult()
         );
      }

      assert (state_0 & 4) != 0;

      return JSEqualNode.doDouble(leftNodeValue_, rightNodeValue_);
   }

   private boolean executeBoolean_double_boolean3(int state_0, int state_1, VirtualFrame frameValue) {
      long leftNodeValue_long = 0L;
      int leftNodeValue_int = 0;

      double leftNodeValue_;
      try {
         if ((state_1 & 3584) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
            if ((state_1 & 3328) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
               if ((state_1 & 1792) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
                  Object leftNodeValue__ = super.leftNode.execute(frameValue);
                  leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_1 & 3840) >>> 8, leftNodeValue__);
               } else {
                  leftNodeValue_long = super.leftNode.executeLong(frameValue);
                  leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
               }
            } else {
               leftNodeValue_int = super.leftNode.executeInt(frameValue);
               leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            }
         } else {
            leftNodeValue_ = super.leftNode.executeDouble(frameValue);
         }
      } catch (UnexpectedResultException var12) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var12.getResult(), rightNodeValue);
      }

      boolean rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var11) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_1 & 3328) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0
               ? ((state_1 & 1792) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0 ? leftNodeValue_ : leftNodeValue_long)
               : leftNodeValue_int,
            var11.getResult()
         );
      }

      assert (state_0 & 32) != 0;

      return JSEqualNode.doDoubleBoolean(leftNodeValue_, rightNodeValue_);
   }

   private boolean executeBoolean_boolean_boolean4(int state_0, int state_1, VirtualFrame frameValue) {
      boolean leftNodeValue_;
      try {
         leftNodeValue_ = super.leftNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var8) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var8.getResult(), rightNodeValue);
      }

      boolean rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var7) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var7.getResult());
      }

      assert (state_0 & 64) != 0;

      return JSEqualNode.doBoolean(leftNodeValue_, rightNodeValue_);
   }

   private boolean executeBoolean_boolean_int5(int state_0, int state_1, VirtualFrame frameValue) {
      boolean leftNodeValue_;
      try {
         leftNodeValue_ = super.leftNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var8) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var8.getResult(), rightNodeValue);
      }

      int rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeInt(frameValue);
      } catch (UnexpectedResultException var7) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var7.getResult());
      }

      assert (state_0 & 128) != 0;

      return JSEqualNode.doBooleanInt(leftNodeValue_, rightNodeValue_);
   }

   private boolean executeBoolean_boolean_double6(int state_0, int state_1, VirtualFrame frameValue) {
      boolean leftNodeValue_;
      try {
         leftNodeValue_ = super.leftNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var11) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var11.getResult(), rightNodeValue);
      }

      long rightNodeValue_long = 0L;
      int rightNodeValue_int = 0;

      double rightNodeValue_;
      try {
         if ((state_1 & 57344) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
            if ((state_1 & 53248) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
               if ((state_1 & 28672) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
                  Object rightNodeValue__ = super.rightNode.execute(frameValue);
                  rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue__);
               } else {
                  rightNodeValue_long = super.rightNode.executeLong(frameValue);
                  rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
               }
            } else {
               rightNodeValue_int = super.rightNode.executeInt(frameValue);
               rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            }
         } else {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         }
      } catch (UnexpectedResultException var12) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var12.getResult());
      }

      assert (state_0 & 256) != 0;

      return JSEqualNode.doBooleanDouble(leftNodeValue_, rightNodeValue_);
   }

   private boolean executeBoolean_double7(int state_0, int state_1, VirtualFrame frameValue) {
      long leftNodeValue_long = 0L;
      int leftNodeValue_int = 0;

      double leftNodeValue_;
      try {
         if ((state_1 & 3584) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
            if ((state_1 & 3328) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
               if ((state_1 & 1792) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
                  Object leftNodeValue__ = super.leftNode.execute(frameValue);
                  leftNodeValue_ = JSTypesGen.expectImplicitDouble((state_1 & 3840) >>> 8, leftNodeValue__);
               } else {
                  leftNodeValue_long = super.leftNode.executeLong(frameValue);
                  leftNodeValue_ = JSTypes.longToDouble(leftNodeValue_long);
               }
            } else {
               leftNodeValue_int = super.leftNode.executeInt(frameValue);
               leftNodeValue_ = JSTypes.intToDouble(leftNodeValue_int);
            }
         } else {
            leftNodeValue_ = super.leftNode.executeDouble(frameValue);
         }
      } catch (UnexpectedResultException var11) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var11.getResult(), rightNodeValue);
      }

      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 16) != 0 && rightNodeValue_ instanceof TruffleString) {
         TruffleString rightNodeValue__ = (TruffleString)rightNodeValue_;
         return this.doDoubleString(leftNodeValue_, rightNodeValue__);
      } else if ((state_0 & 32) != 0 && rightNodeValue_ instanceof Boolean) {
         boolean rightNodeValue__ = (Boolean)rightNodeValue_;
         return JSEqualNode.doDoubleBoolean(leftNodeValue_, rightNodeValue__);
      } else if ((state_0 & -2147483648) != 0 && rightNodeValue_ instanceof BigInt) {
         BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
         return this.doNumberAndBigInt(leftNodeValue_, rightNodeValue__);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_1 & 3328) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0
               ? ((state_1 & 1792) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0 ? leftNodeValue_ : leftNodeValue_long)
               : leftNodeValue_int,
            rightNodeValue_
         );
      }
   }

   private boolean executeBoolean_boolean8(int state_0, int state_1, VirtualFrame frameValue) {
      boolean leftNodeValue_;
      try {
         leftNodeValue_ = super.leftNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var7) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var7.getResult(), rightNodeValue);
      }

      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 512) != 0 && rightNodeValue_ instanceof TruffleString) {
         TruffleString rightNodeValue__ = (TruffleString)rightNodeValue_;
         return this.doBooleanString(leftNodeValue_, rightNodeValue__);
      } else if ((state_0 & 65536) != 0 && rightNodeValue_ instanceof BigInt) {
         BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
         return this.doBooleanBigInt(leftNodeValue_, rightNodeValue__);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
      }
   }

   private boolean executeBoolean_double9(int state_0, int state_1, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      long rightNodeValue_long = 0L;
      int rightNodeValue_int = 0;

      double rightNodeValue_;
      try {
         if ((state_1 & 57344) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
            if ((state_1 & 53248) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
               if ((state_1 & 28672) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
                  Object rightNodeValue__ = super.rightNode.execute(frameValue);
                  rightNodeValue_ = JSTypesGen.expectImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue__);
               } else {
                  rightNodeValue_long = super.rightNode.executeLong(frameValue);
                  rightNodeValue_ = JSTypes.longToDouble(rightNodeValue_long);
               }
            } else {
               rightNodeValue_int = super.rightNode.executeInt(frameValue);
               rightNodeValue_ = JSTypes.intToDouble(rightNodeValue_int);
            }
         } else {
            rightNodeValue_ = super.rightNode.executeDouble(frameValue);
         }
      } catch (UnexpectedResultException var11) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var11.getResult());
      }

      if ((state_0 & 4096) != 0 && leftNodeValue_ instanceof TruffleString) {
         TruffleString leftNodeValue__ = (TruffleString)leftNodeValue_;
         return this.doStringDouble(leftNodeValue__, rightNodeValue_);
      } else if ((state_0 & 536870912) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
         return this.doBigIntAndNumber(leftNodeValue__, rightNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            leftNodeValue_,
            (state_1 & 53248) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0
               ? ((state_1 & 28672) != 0 || (state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0 ? rightNodeValue_ : rightNodeValue_long)
               : rightNodeValue_int
         );
      }
   }

   private boolean executeBoolean_boolean10(int state_0, int state_1, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);

      boolean rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeBoolean(frameValue);
      } catch (UnexpectedResultException var7) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var7.getResult());
      }

      if ((state_0 & 8192) != 0 && leftNodeValue_ instanceof TruffleString) {
         TruffleString leftNodeValue__ = (TruffleString)leftNodeValue_;
         return this.doStringBoolean(leftNodeValue__, rightNodeValue_);
      } else if ((state_0 & 131072) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
         return this.doBigIntBoolean(leftNodeValue__, rightNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
      }
   }

   private boolean executeBoolean_int11(int state_0, int state_1, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);

      int rightNodeValue_;
      try {
         rightNodeValue_ = super.rightNode.executeInt(frameValue);
      } catch (UnexpectedResultException var7) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, var7.getResult());
      }

      assert (state_0 & 268435456) != 0;

      if (leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__ = (BigInt)leftNodeValue_;
         return this.doBigIntAndInt(leftNodeValue__, rightNodeValue_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
      }
   }

   private boolean executeBoolean_int12(int state_0, int state_1, VirtualFrame frameValue) {
      int leftNodeValue_;
      try {
         leftNodeValue_ = super.leftNode.executeInt(frameValue);
      } catch (UnexpectedResultException var7) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object rightNodeValue = super.rightNode.execute(frameValue);
         return this.executeAndSpecialize(var7.getResult(), rightNodeValue);
      }

      Object rightNodeValue_ = super.rightNode.execute(frameValue);

      assert (state_0 & 1073741824) != 0;

      if (rightNodeValue_ instanceof BigInt) {
         BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
         return this.doIntAndBigInt(leftNodeValue_, rightNodeValue__);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(leftNodeValue_, rightNodeValue_);
      }
   }

   private boolean executeBoolean_generic13(int state_0, int state_1, VirtualFrame frameValue) {
      Object leftNodeValue_ = super.leftNode.execute(frameValue);
      Object rightNodeValue_ = super.rightNode.execute(frameValue);
      if ((state_0 & 3) != 0 && leftNodeValue_ instanceof Integer) {
         int leftNodeValue__ = (Integer)leftNodeValue_;
         if ((state_0 & 1) != 0 && rightNodeValue_ instanceof Integer) {
            int rightNodeValue__ = (Integer)rightNodeValue_;
            return JSEqualNode.doInt(leftNodeValue__, rightNodeValue__);
         }

         if ((state_0 & 2) != 0 && rightNodeValue_ instanceof Boolean) {
            boolean rightNodeValue__ = (Boolean)rightNodeValue_;
            return JSEqualNode.doIntBoolean(leftNodeValue__, rightNodeValue__);
         }
      }

      if ((state_0 & 4) != 0 && JSTypesGen.isImplicitDouble((state_1 & 3840) >>> 8, leftNodeValue_)) {
         double leftNodeValue__x = JSTypesGen.asImplicitDouble((state_1 & 3840) >>> 8, leftNodeValue_);
         if (JSTypesGen.isImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue_)) {
            double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue_);
            return JSEqualNode.doDouble(leftNodeValue__x, rightNodeValue__);
         }
      }

      if ((state_0 & 8) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__x = (BigInt)leftNodeValue_;
         if (rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__ = (BigInt)rightNodeValue_;
            return JSEqualNode.doBigInt(leftNodeValue__x, rightNodeValue__);
         }
      }

      if ((state_0 & 48) != 0 && JSTypesGen.isImplicitDouble((state_1 & 3840) >>> 8, leftNodeValue_)) {
         double leftNodeValue__x = JSTypesGen.asImplicitDouble((state_1 & 3840) >>> 8, leftNodeValue_);
         if ((state_0 & 16) != 0 && rightNodeValue_ instanceof TruffleString) {
            TruffleString rightNodeValue__ = (TruffleString)rightNodeValue_;
            return this.doDoubleString(leftNodeValue__x, rightNodeValue__);
         }

         if ((state_0 & 32) != 0 && rightNodeValue_ instanceof Boolean) {
            boolean rightNodeValue__ = (Boolean)rightNodeValue_;
            return JSEqualNode.doDoubleBoolean(leftNodeValue__x, rightNodeValue__);
         }
      }

      if ((state_0 & 960) != 0 && leftNodeValue_ instanceof Boolean) {
         boolean leftNodeValue__xx = (Boolean)leftNodeValue_;
         if ((state_0 & 64) != 0 && rightNodeValue_ instanceof Boolean) {
            boolean rightNodeValue__ = (Boolean)rightNodeValue_;
            return JSEqualNode.doBoolean(leftNodeValue__xx, rightNodeValue__);
         }

         if ((state_0 & 128) != 0 && rightNodeValue_ instanceof Integer) {
            int rightNodeValue__ = (Integer)rightNodeValue_;
            return JSEqualNode.doBooleanInt(leftNodeValue__xx, rightNodeValue__);
         }

         if ((state_0 & 256) != 0 && JSTypesGen.isImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue_)) {
            double rightNodeValue__ = JSTypesGen.asImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue_);
            return JSEqualNode.doBooleanDouble(leftNodeValue__xx, rightNodeValue__);
         }

         if ((state_0 & 512) != 0 && rightNodeValue_ instanceof TruffleString) {
            TruffleString rightNodeValue__ = (TruffleString)rightNodeValue_;
            return this.doBooleanString(leftNodeValue__xx, rightNodeValue__);
         }
      }

      if ((state_0 & 31744) != 0 && leftNodeValue_ instanceof TruffleString) {
         TruffleString leftNodeValue__xxx = (TruffleString)leftNodeValue_;
         if ((state_0 & 3072) != 0 && rightNodeValue_ instanceof TruffleString) {
            TruffleString rightNodeValue__ = (TruffleString)rightNodeValue_;
            if ((state_0 & 1024) != 0 && JSGuards.isReferenceEquals(leftNodeValue__xxx, rightNodeValue__)) {
               return JSEqualNode.doStringIdentity(leftNodeValue__xxx, rightNodeValue__);
            }

            if ((state_0 & 2048) != 0) {
               return JSEqualNode.doString(leftNodeValue__xxx, rightNodeValue__, this.string_equalsNode_);
            }
         }

         if ((state_0 & 4096) != 0 && JSTypesGen.isImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue_)) {
            double rightNodeValue__x = JSTypesGen.asImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue_);
            return this.doStringDouble(leftNodeValue__xxx, rightNodeValue__x);
         }

         if ((state_0 & 8192) != 0 && rightNodeValue_ instanceof Boolean) {
            boolean rightNodeValue__x = (Boolean)rightNodeValue_;
            return this.doStringBoolean(leftNodeValue__xxx, rightNodeValue__x);
         }

         if ((state_0 & 16384) != 0 && rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__x = (BigInt)rightNodeValue_;
            return this.doStringBigInt(leftNodeValue__xxx, rightNodeValue__x);
         }
      }

      if ((state_0 & 32768) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__xxxx = (BigInt)leftNodeValue_;
         if (rightNodeValue_ instanceof TruffleString) {
            TruffleString rightNodeValue__x = (TruffleString)rightNodeValue_;
            return this.doBigIntString(leftNodeValue__xxxx, rightNodeValue__x);
         }
      }

      if ((state_0 & 65536) != 0 && leftNodeValue_ instanceof Boolean) {
         boolean leftNodeValue__xxxx = (Boolean)leftNodeValue_;
         if (rightNodeValue_ instanceof BigInt) {
            BigInt rightNodeValue__x = (BigInt)rightNodeValue_;
            return this.doBooleanBigInt(leftNodeValue__xxxx, rightNodeValue__x);
         }
      }

      if ((state_0 & 131072) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__xxxx = (BigInt)leftNodeValue_;
         if (rightNodeValue_ instanceof Boolean) {
            boolean rightNodeValue__x = (Boolean)rightNodeValue_;
            return this.doBigIntBoolean(leftNodeValue__xxxx, rightNodeValue__x);
         }
      }

      if ((state_0 & 3932160) != 0) {
         if ((state_0 & 262144) != 0 && JSRuntime.isNullOrUndefined(leftNodeValue_) && JSRuntime.isNullOrUndefined(rightNodeValue_)) {
            return JSEqualNode.doBothNullOrUndefined(leftNodeValue_, rightNodeValue_);
         }

         if ((state_0 & 524288) != 0 && JSRuntime.isNullOrUndefined(leftNodeValue_)) {
            return JSEqualNode.doLeftNullOrUndefined(leftNodeValue_, rightNodeValue_, this.bInterop);
         }

         if ((state_0 & 1048576) != 0 && JSRuntime.isNullOrUndefined(rightNodeValue_)) {
            return JSEqualNode.doRightNullOrUndefined(leftNodeValue_, rightNodeValue_, this.aInterop);
         }

         if ((state_0 & 2097152) != 0 && (this.hasOverloadedOperators(leftNodeValue_) || this.hasOverloadedOperators(rightNodeValue_))) {
            return this.doOverloaded(leftNodeValue_, rightNodeValue_, this.overloaded_overloadedOperatorNode_, this.overloaded_toBooleanNode_);
         }
      }

      if ((state_0 & 4194304) != 0 && leftNodeValue_ instanceof JSObject) {
         JSObject leftNodeValue__xxxx = (JSObject)leftNodeValue_;
         if (rightNodeValue_ instanceof JSDynamicObject) {
            JSDynamicObject rightNodeValue__x = (JSDynamicObject)rightNodeValue_;
            if (!this.hasOverloadedOperators(leftNodeValue__xxxx) && !this.hasOverloadedOperators(rightNodeValue__x)) {
               return JSEqualNode.doJSObject(leftNodeValue__xxxx, rightNodeValue__x);
            }
         }
      }

      if ((state_0 & 8388608) != 0 && leftNodeValue_ instanceof JSDynamicObject) {
         JSDynamicObject leftNodeValue__xxxx = (JSDynamicObject)leftNodeValue_;
         if (rightNodeValue_ instanceof JSObject) {
            JSObject rightNodeValue__x = (JSObject)rightNodeValue_;
            if (!this.hasOverloadedOperators(leftNodeValue__xxxx) && !this.hasOverloadedOperators(rightNodeValue__x)) {
               return JSEqualNode.doJSObject(leftNodeValue__xxxx, rightNodeValue__x);
            }
         }
      }

      if ((state_0 & 16777216) != 0 && leftNodeValue_ instanceof JSObject) {
         JSObject leftNodeValue__xxxx = (JSObject)leftNodeValue_;
         if (!this.hasOverloadedOperators(leftNodeValue__xxxx) && this.isPrimitive.executeBoolean(rightNodeValue_)) {
            return this.doJSObjectVsPrimitive(leftNodeValue__xxxx, rightNodeValue_, this.bInterop, this.toPrimitive, this.isPrimitive, this.equal);
         }
      }

      if ((state_0 & 67108864) != 0 && rightNodeValue_ instanceof JSObject) {
         JSObject rightNodeValue__x = (JSObject)rightNodeValue_;
         if (!this.hasOverloadedOperators(rightNodeValue__x) && this.isPrimitive.executeBoolean(leftNodeValue_)) {
            return this.doJSObjectVsPrimitive(leftNodeValue_, rightNodeValue__x, this.aInterop, this.toPrimitive, this.isPrimitive, this.equal);
         }
      }

      if ((state_0 & 805306368) != 0 && leftNodeValue_ instanceof BigInt) {
         BigInt leftNodeValue__xxxx = (BigInt)leftNodeValue_;
         if ((state_0 & 268435456) != 0 && rightNodeValue_ instanceof Integer) {
            int rightNodeValue__x = (Integer)rightNodeValue_;
            return this.doBigIntAndInt(leftNodeValue__xxxx, rightNodeValue__x);
         }

         if ((state_0 & 536870912) != 0 && JSTypesGen.isImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue_)) {
            double rightNodeValue__x = JSTypesGen.asImplicitDouble((state_1 & 61440) >>> 12, rightNodeValue_);
            return this.doBigIntAndNumber(leftNodeValue__xxxx, rightNodeValue__x);
         }
      }

      if ((state_0 & -1073741824) != 0 && rightNodeValue_ instanceof BigInt) {
         BigInt rightNodeValue__x = (BigInt)rightNodeValue_;
         if ((state_0 & 1073741824) != 0 && leftNodeValue_ instanceof Integer) {
            int leftNodeValue__xxxxx = (Integer)leftNodeValue_;
            return this.doIntAndBigInt(leftNodeValue__xxxxx, rightNodeValue__x);
         }

         if ((state_0 & -2147483648) != 0 && JSTypesGen.isImplicitDouble((state_1 & 3840) >>> 8, leftNodeValue_)) {
            double leftNodeValue__xxxxx = JSTypesGen.asImplicitDouble((state_1 & 3840) >>> 8, leftNodeValue_);
            return this.doNumberAndBigInt(leftNodeValue__xxxxx, rightNodeValue__x);
         }
      }

      if ((state_1 & 3) != 0 && leftNodeValue_ instanceof Symbol) {
         Symbol leftNodeValue__xxxxx = (Symbol)leftNodeValue_;
         if ((state_1 & 1) != 0 && rightNodeValue_ instanceof Symbol) {
            Symbol rightNodeValue__xx = (Symbol)rightNodeValue_;
            return JSEqualNode.doSymbol(leftNodeValue__xxxxx, rightNodeValue__xx);
         }

         if ((state_1 & 2) != 0 && !JSGuards.isSymbol(rightNodeValue_) && !JSRuntime.isObject(rightNodeValue_)) {
            return JSEqualNode.doSymbolNotSymbol(leftNodeValue__xxxxx, rightNodeValue_);
         }
      }

      if ((state_1 & 12) != 0) {
         if ((state_1 & 4) != 0 && rightNodeValue_ instanceof Symbol) {
            Symbol rightNodeValue__xx = (Symbol)rightNodeValue_;
            if (!JSGuards.isSymbol(leftNodeValue_) && !JSRuntime.isObject(leftNodeValue_)) {
               return JSEqualNode.doSymbolNotSymbol(leftNodeValue_, rightNodeValue__xx);
            }
         }

         if ((state_1 & 8) != 0) {
            boolean foreign_isAForeign__ = JSRuntime.isForeignObject(leftNodeValue_);
            boolean foreign_isBForeign__ = JSRuntime.isForeignObject(rightNodeValue_);
            if (foreign_isAForeign__ || foreign_isBForeign__) {
               return this.doForeign(
                  leftNodeValue_,
                  rightNodeValue_,
                  foreign_isAForeign__,
                  foreign_isBForeign__,
                  this.aInterop,
                  this.bInterop,
                  this.toPrimitive,
                  this.isPrimitive,
                  this.equal
               );
            }
         }
      }

      if ((state_1 & 16) != 0 && leftNodeValue_ instanceof Number) {
         Number leftNodeValue__xxxxxx = (Number)leftNodeValue_;
         if (rightNodeValue_ instanceof Number) {
            Number rightNodeValue__xx = (Number)rightNodeValue_;
            if (JSRuntime.isJavaNumber(leftNodeValue__xxxxxx) && JSRuntime.isJavaNumber(rightNodeValue__xx)) {
               return JSEqualNode.doNumber(leftNodeValue__xxxxxx, rightNodeValue__xx);
            }
         }
      }

      if ((state_1 & 32) != 0 && rightNodeValue_ instanceof TruffleString) {
         TruffleString rightNodeValue__xx = (TruffleString)rightNodeValue_;
         if (JSRuntime.isJavaNumber(leftNodeValue_)) {
            return this.doNumberString(leftNodeValue_, rightNodeValue__xx);
         }
      }

      if ((state_1 & 192) != 0) {
         if ((state_1 & 64) != 0 && leftNodeValue_ instanceof TruffleString) {
            TruffleString leftNodeValue__xxxxxx = (TruffleString)leftNodeValue_;
            if (JSRuntime.isJavaNumber(rightNodeValue_)) {
               return this.doStringNumber(leftNodeValue__xxxxxx, rightNodeValue_);
            }
         }

         if ((state_1 & 128) != 0 && this.fallbackGuard_(state_0, state_1, leftNodeValue_, rightNodeValue_)) {
            return JSEqualNode.doFallback(leftNodeValue_, rightNodeValue_);
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
               int var46;
               this.state_0_ = var46 = state_0 | 1;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return JSEqualNode.doInt(leftNodeValue_, rightNodeValue_);
            }

            if (rightNodeValue instanceof Boolean) {
               boolean rightNodeValue_ = (Boolean)rightNodeValue;
               int var45;
               this.state_0_ = var45 = state_0 | 2;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return JSEqualNode.doIntBoolean(leftNodeValue_, rightNodeValue_);
            }
         }

         int doubleCast0;
         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
            double leftNodeValue_x = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
            int doubleCast1;
            if ((doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
               double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
               state_1 |= doubleCast0 << 8;
               state_1 |= doubleCast1 << 12;
               int var44;
               this.state_0_ = var44 = state_0 | 4;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return JSEqualNode.doDouble(leftNodeValue_x, rightNodeValue_);
            }
         }

         if (leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_x = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
               BigInt rightNodeValue_ = (BigInt)rightNodeValue;
               int var43;
               this.state_0_ = var43 = state_0 | 8;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return JSEqualNode.doBigInt(leftNodeValue_x, rightNodeValue_);
            }
         }

         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
            double leftNodeValue_x = JSTypesGen.asImplicitDouble(doubleCast0, leftNodeValue);
            if (rightNodeValue instanceof TruffleString) {
               TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
               state_1 |= doubleCast0 << 8;
               int var42;
               this.state_0_ = var42 = state_0 | 16;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return this.doDoubleString(leftNodeValue_x, rightNodeValue_);
            }

            if (rightNodeValue instanceof Boolean) {
               boolean rightNodeValue_ = (Boolean)rightNodeValue;
               state_1 |= doubleCast0 << 8;
               int var41;
               this.state_0_ = var41 = state_0 | 32;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return JSEqualNode.doDoubleBoolean(leftNodeValue_x, rightNodeValue_);
            }
         }

         if (leftNodeValue instanceof Boolean) {
            boolean leftNodeValue_xx = (Boolean)leftNodeValue;
            if (rightNodeValue instanceof Boolean) {
               boolean rightNodeValue_ = (Boolean)rightNodeValue;
               int var40;
               this.state_0_ = var40 = state_0 | 64;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return JSEqualNode.doBoolean(leftNodeValue_xx, rightNodeValue_);
            }

            if (rightNodeValue instanceof Integer) {
               int rightNodeValue_ = (Integer)rightNodeValue;
               int var39;
               this.state_0_ = var39 = state_0 | 128;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return JSEqualNode.doBooleanInt(leftNodeValue_xx, rightNodeValue_);
            }

            int doubleCast1;
            if ((doubleCast1 = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
               double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1, rightNodeValue);
               state_1 |= doubleCast1 << 12;
               int var38;
               this.state_0_ = var38 = state_0 | 256;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return JSEqualNode.doBooleanDouble(leftNodeValue_xx, rightNodeValue_);
            }

            if (rightNodeValue instanceof TruffleString) {
               TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
               int var37;
               this.state_0_ = var37 = state_0 | 512;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return this.doBooleanString(leftNodeValue_xx, rightNodeValue_);
            }
         }

         if (leftNodeValue instanceof TruffleString) {
            TruffleString leftNodeValue_xxx = (TruffleString)leftNodeValue;
            if (rightNodeValue instanceof TruffleString) {
               TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
               if (exclude != 0 || !JSGuards.isReferenceEquals(leftNodeValue_xxx, rightNodeValue_)) {
                  this.string_equalsNode_ = super.insert(TruffleString.EqualNode.create());
                  int var63;
                  this.exclude_ = var63 = exclude | 1;
                  state_0 &= -1025;
                  int var36;
                  this.state_0_ = var36 = state_0 | 2048;
                  this.state_1_ = state_1;
                  lock.unlock();
                  hasLock = false;
                  return JSEqualNode.doString(leftNodeValue_xxx, rightNodeValue_, this.string_equalsNode_);
               }

               int var34;
               this.state_0_ = var34 = state_0 | 1024;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return JSEqualNode.doStringIdentity(leftNodeValue_xxx, rightNodeValue_);
            }

            int doubleCast1x;
            if ((doubleCast1x = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
               double rightNodeValue_ = JSTypesGen.asImplicitDouble(doubleCast1x, rightNodeValue);
               state_1 |= doubleCast1x << 12;
               int var33;
               this.state_0_ = var33 = state_0 | 4096;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return this.doStringDouble(leftNodeValue_xxx, rightNodeValue_);
            }

            if (rightNodeValue instanceof Boolean) {
               boolean rightNodeValue_ = (Boolean)rightNodeValue;
               int var32;
               this.state_0_ = var32 = state_0 | 8192;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return this.doStringBoolean(leftNodeValue_xxx, rightNodeValue_);
            }

            if (rightNodeValue instanceof BigInt) {
               BigInt rightNodeValue_ = (BigInt)rightNodeValue;
               int var31;
               this.state_0_ = var31 = state_0 | 16384;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return this.doStringBigInt(leftNodeValue_xxx, rightNodeValue_);
            }
         }

         if (leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_xxxx = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof TruffleString) {
               TruffleString rightNodeValue_ = (TruffleString)rightNodeValue;
               int var30;
               this.state_0_ = var30 = state_0 | 32768;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return this.doBigIntString(leftNodeValue_xxxx, rightNodeValue_);
            }
         }

         if (leftNodeValue instanceof Boolean) {
            boolean leftNodeValue_xxxx = (Boolean)leftNodeValue;
            if (rightNodeValue instanceof BigInt) {
               BigInt rightNodeValue_ = (BigInt)rightNodeValue;
               int var29;
               this.state_0_ = var29 = state_0 | 65536;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return this.doBooleanBigInt(leftNodeValue_xxxx, rightNodeValue_);
            }
         }

         if (leftNodeValue instanceof BigInt) {
            BigInt leftNodeValue_xxxx = (BigInt)leftNodeValue;
            if (rightNodeValue instanceof Boolean) {
               boolean rightNodeValue_ = (Boolean)rightNodeValue;
               int var28;
               this.state_0_ = var28 = state_0 | 131072;
               this.state_1_ = state_1;
               lock.unlock();
               hasLock = false;
               return this.doBigIntBoolean(leftNodeValue_xxxx, rightNodeValue_);
            }
         }

         if (JSRuntime.isNullOrUndefined(leftNodeValue) && JSRuntime.isNullOrUndefined(rightNodeValue)) {
            int var27;
            this.state_0_ = var27 = state_0 | 262144;
            this.state_1_ = state_1;
            lock.unlock();
            hasLock = false;
            return JSEqualNode.doBothNullOrUndefined(leftNodeValue, rightNodeValue);
         } else if (JSRuntime.isNullOrUndefined(leftNodeValue)) {
            this.bInterop = super.insert(this.bInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.bInterop);
            int var26;
            this.state_0_ = var26 = state_0 | 524288;
            this.state_1_ = state_1;
            lock.unlock();
            hasLock = false;
            return JSEqualNode.doLeftNullOrUndefined(leftNodeValue, rightNodeValue, this.bInterop);
         } else if (JSRuntime.isNullOrUndefined(rightNodeValue)) {
            this.aInterop = super.insert(this.aInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.aInterop);
            int var25;
            this.state_0_ = var25 = state_0 | 1048576;
            this.state_1_ = state_1;
            lock.unlock();
            hasLock = false;
            return JSEqualNode.doRightNullOrUndefined(leftNodeValue, rightNodeValue, this.aInterop);
         } else if (this.hasOverloadedOperators(leftNodeValue) || this.hasOverloadedOperators(rightNodeValue)) {
            this.overloaded_overloadedOperatorNode_ = super.insert(JSOverloadedBinaryNode.createHintDefault(JSEqualNode.getOverloadedOperatorName()));
            this.overloaded_toBooleanNode_ = super.insert(JSToBooleanNode.create());
            int var24;
            this.state_0_ = var24 = state_0 | 2097152;
            this.state_1_ = state_1;
            lock.unlock();
            hasLock = false;
            return this.doOverloaded(leftNodeValue, rightNodeValue, this.overloaded_overloadedOperatorNode_, this.overloaded_toBooleanNode_);
         } else {
            if (leftNodeValue instanceof JSObject) {
               JSObject leftNodeValue_xxxx = (JSObject)leftNodeValue;
               if (rightNodeValue instanceof JSDynamicObject) {
                  JSDynamicObject rightNodeValue_ = (JSDynamicObject)rightNodeValue;
                  if (!this.hasOverloadedOperators(leftNodeValue_xxxx) && !this.hasOverloadedOperators(rightNodeValue_)) {
                     int var23;
                     this.state_0_ = var23 = state_0 | 4194304;
                     this.state_1_ = state_1;
                     lock.unlock();
                     hasLock = false;
                     return JSEqualNode.doJSObject(leftNodeValue_xxxx, rightNodeValue_);
                  }
               }
            }

            if (leftNodeValue instanceof JSDynamicObject) {
               JSDynamicObject leftNodeValue_xxxx = (JSDynamicObject)leftNodeValue;
               if (rightNodeValue instanceof JSObject) {
                  JSObject rightNodeValue_ = (JSObject)rightNodeValue;
                  if (!this.hasOverloadedOperators(leftNodeValue_xxxx) && !this.hasOverloadedOperators(rightNodeValue_)) {
                     int var22;
                     this.state_0_ = var22 = state_0 | 8388608;
                     this.state_1_ = state_1;
                     lock.unlock();
                     hasLock = false;
                     return JSEqualNode.doJSObject(leftNodeValue_xxxx, rightNodeValue_);
                  }
               }
            }

            if (leftNodeValue instanceof JSObject) {
               JSObject leftNodeValue_xxxx = (JSObject)leftNodeValue;
               boolean JSObjectVsPrimitive0_duplicateFound_ = false;
               if ((state_0 & 16777216) != 0 && !this.hasOverloadedOperators(leftNodeValue_xxxx) && this.isPrimitive.executeBoolean(rightNodeValue)) {
                  JSObjectVsPrimitive0_duplicateFound_ = true;
               }

               if (!JSObjectVsPrimitive0_duplicateFound_ && !this.hasOverloadedOperators(leftNodeValue_xxxx)) {
                  if ((state_0 & 33554432) == 0) {
                     if (this.isPrimitive == null) {
                        IsPrimitiveNode jSObjectVsPrimitive0_isPrimitiveNode___check = super.insert(
                           this.isPrimitive == null ? IsPrimitiveNode.create() : this.isPrimitive
                        );
                        if (jSObjectVsPrimitive0_isPrimitiveNode___check == null) {
                           throw new AssertionError(
                              "Specialization 'doJSObjectVsPrimitive(JSObject, Object, InteropLibrary, JSToPrimitiveNode, IsPrimitiveNode, JSEqualNode)' contains a shared cache with name 'isPrimitiveNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isPrimitive = jSObjectVsPrimitive0_isPrimitiveNode___check;
                     }

                     this.state_0_ = state_0 |= 33554432;
                     this.state_1_ = state_1;
                  }

                  if (this.isPrimitive.executeBoolean(rightNodeValue) && (state_0 & 16777216) == 0) {
                     this.bInterop = super.insert(this.bInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.bInterop);
                     this.toPrimitive = super.insert(this.toPrimitive == null ? JSToPrimitiveNode.createHintDefault() : this.toPrimitive);
                     if (this.isPrimitive == null) {
                        IsPrimitiveNode jSObjectVsPrimitive0_isPrimitiveNode___check = super.insert(
                           this.isPrimitive == null ? IsPrimitiveNode.create() : this.isPrimitive
                        );
                        if (jSObjectVsPrimitive0_isPrimitiveNode___check == null) {
                           throw new AssertionError(
                              "Specialization 'doJSObjectVsPrimitive(JSObject, Object, InteropLibrary, JSToPrimitiveNode, IsPrimitiveNode, JSEqualNode)' contains a shared cache with name 'isPrimitiveNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isPrimitive = jSObjectVsPrimitive0_isPrimitiveNode___check;
                     }

                     this.equal = super.insert(this.equal == null ? JSEqualNode.create() : this.equal);
                     this.state_0_ = state_0 |= 16777216;
                     this.state_1_ = state_1;
                     JSObjectVsPrimitive0_duplicateFound_ = true;
                  }
               }

               if (JSObjectVsPrimitive0_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return this.doJSObjectVsPrimitive(leftNodeValue_xxxx, rightNodeValue, this.bInterop, this.toPrimitive, this.isPrimitive, this.equal);
               }
            }

            if (rightNodeValue instanceof JSObject) {
               JSObject rightNodeValue_ = (JSObject)rightNodeValue;
               boolean JSObjectVsPrimitive1_duplicateFound_ = false;
               if ((state_0 & 67108864) != 0 && !this.hasOverloadedOperators(rightNodeValue_) && this.isPrimitive.executeBoolean(leftNodeValue)) {
                  JSObjectVsPrimitive1_duplicateFound_ = true;
               }

               if (!JSObjectVsPrimitive1_duplicateFound_ && !this.hasOverloadedOperators(rightNodeValue_)) {
                  if ((state_0 & 134217728) == 0) {
                     if (this.isPrimitive == null) {
                        IsPrimitiveNode jSObjectVsPrimitive1_isPrimitiveNode___check = super.insert(
                           this.isPrimitive == null ? IsPrimitiveNode.create() : this.isPrimitive
                        );
                        if (jSObjectVsPrimitive1_isPrimitiveNode___check == null) {
                           throw new AssertionError(
                              "Specialization 'doJSObjectVsPrimitive(Object, JSObject, InteropLibrary, JSToPrimitiveNode, IsPrimitiveNode, JSEqualNode)' contains a shared cache with name 'isPrimitiveNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isPrimitive = jSObjectVsPrimitive1_isPrimitiveNode___check;
                     }

                     this.state_0_ = state_0 |= 134217728;
                     this.state_1_ = state_1;
                  }

                  if (this.isPrimitive.executeBoolean(leftNodeValue) && (state_0 & 67108864) == 0) {
                     this.aInterop = super.insert(this.aInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.aInterop);
                     this.toPrimitive = super.insert(this.toPrimitive == null ? JSToPrimitiveNode.createHintDefault() : this.toPrimitive);
                     if (this.isPrimitive == null) {
                        IsPrimitiveNode jSObjectVsPrimitive1_isPrimitiveNode___check = super.insert(
                           this.isPrimitive == null ? IsPrimitiveNode.create() : this.isPrimitive
                        );
                        if (jSObjectVsPrimitive1_isPrimitiveNode___check == null) {
                           throw new AssertionError(
                              "Specialization 'doJSObjectVsPrimitive(Object, JSObject, InteropLibrary, JSToPrimitiveNode, IsPrimitiveNode, JSEqualNode)' contains a shared cache with name 'isPrimitiveNode' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isPrimitive = jSObjectVsPrimitive1_isPrimitiveNode___check;
                     }

                     this.equal = super.insert(this.equal == null ? JSEqualNode.create() : this.equal);
                     this.state_0_ = state_0 |= 67108864;
                     this.state_1_ = state_1;
                     JSObjectVsPrimitive1_duplicateFound_ = true;
                  }
               }

               if (JSObjectVsPrimitive1_duplicateFound_) {
                  lock.unlock();
                  hasLock = false;
                  return this.doJSObjectVsPrimitive(leftNodeValue, rightNodeValue_, this.aInterop, this.toPrimitive, this.isPrimitive, this.equal);
               }
            }

            if (leftNodeValue instanceof BigInt) {
               BigInt leftNodeValue_xxxxx = (BigInt)leftNodeValue;
               if (rightNodeValue instanceof Integer) {
                  int rightNodeValue_x = (Integer)rightNodeValue;
                  int var21;
                  this.state_0_ = var21 = state_0 | 268435456;
                  this.state_1_ = state_1;
                  lock.unlock();
                  hasLock = false;
                  return this.doBigIntAndInt(leftNodeValue_xxxxx, rightNodeValue_x);
               }

               int doubleCast1xx;
               if ((doubleCast1xx = JSTypesGen.specializeImplicitDouble(rightNodeValue)) != 0) {
                  double rightNodeValue_x = JSTypesGen.asImplicitDouble(doubleCast1xx, rightNodeValue);
                  state_1 |= doubleCast1xx << 12;
                  int var20;
                  this.state_0_ = var20 = state_0 | 536870912;
                  this.state_1_ = state_1;
                  lock.unlock();
                  hasLock = false;
                  return this.doBigIntAndNumber(leftNodeValue_xxxxx, rightNodeValue_x);
               }
            }

            if (rightNodeValue instanceof BigInt) {
               BigInt rightNodeValue_x = (BigInt)rightNodeValue;
               if (leftNodeValue instanceof Integer) {
                  int leftNodeValue_xxxxxx = (Integer)leftNodeValue;
                  int var19;
                  this.state_0_ = var19 = state_0 | 1073741824;
                  this.state_1_ = state_1;
                  lock.unlock();
                  hasLock = false;
                  return this.doIntAndBigInt(leftNodeValue_xxxxxx, rightNodeValue_x);
               }

               int doubleCast0x;
               if ((doubleCast0x = JSTypesGen.specializeImplicitDouble(leftNodeValue)) != 0) {
                  double leftNodeValue_xxxxxx = JSTypesGen.asImplicitDouble(doubleCast0x, leftNodeValue);
                  state_1 |= doubleCast0x << 8;
                  int var18;
                  this.state_0_ = var18 = state_0 | -2147483648;
                  this.state_1_ = state_1;
                  lock.unlock();
                  hasLock = false;
                  return this.doNumberAndBigInt(leftNodeValue_xxxxxx, rightNodeValue_x);
               }
            }

            if (leftNodeValue instanceof Symbol) {
               Symbol leftNodeValue_xxxxxx = (Symbol)leftNodeValue;
               if (rightNodeValue instanceof Symbol) {
                  Symbol rightNodeValue_xx = (Symbol)rightNodeValue;
                  this.state_0_ = state_0;
                  int var54;
                  this.state_1_ = var54 = state_1 | 1;
                  lock.unlock();
                  hasLock = false;
                  return JSEqualNode.doSymbol(leftNodeValue_xxxxxx, rightNodeValue_xx);
               }

               if (!JSGuards.isSymbol(rightNodeValue) && !JSRuntime.isObject(rightNodeValue)) {
                  this.state_0_ = state_0;
                  int var53;
                  this.state_1_ = var53 = state_1 | 2;
                  lock.unlock();
                  hasLock = false;
                  return JSEqualNode.doSymbolNotSymbol(leftNodeValue_xxxxxx, rightNodeValue);
               }
            }

            if (rightNodeValue instanceof Symbol) {
               Symbol rightNodeValue_xx = (Symbol)rightNodeValue;
               if (!JSGuards.isSymbol(leftNodeValue) && !JSRuntime.isObject(leftNodeValue)) {
                  this.state_0_ = state_0;
                  int var52;
                  this.state_1_ = var52 = state_1 | 4;
                  lock.unlock();
                  hasLock = false;
                  return JSEqualNode.doSymbolNotSymbol(leftNodeValue, rightNodeValue_xx);
               }
            }

            boolean foreign_isBForeign__ = false;
            boolean foreign_isAForeign__ = false;
            foreign_isAForeign__ = JSRuntime.isForeignObject(leftNodeValue);
            foreign_isBForeign__ = JSRuntime.isForeignObject(rightNodeValue);
            if (foreign_isAForeign__ || foreign_isBForeign__) {
               this.aInterop = super.insert(this.aInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.aInterop);
               this.bInterop = super.insert(this.bInterop == null ? INTEROP_LIBRARY_.createDispatched(5) : this.bInterop);
               this.toPrimitive = super.insert(this.toPrimitive == null ? JSToPrimitiveNode.createHintDefault() : this.toPrimitive);
               this.isPrimitive = super.insert(this.isPrimitive == null ? IsPrimitiveNode.create() : this.isPrimitive);
               this.equal = super.insert(this.equal == null ? JSEqualNode.create() : this.equal);
               this.state_0_ = state_0;
               int var51;
               this.state_1_ = var51 = state_1 | 8;
               lock.unlock();
               hasLock = false;
               return this.doForeign(
                  leftNodeValue,
                  rightNodeValue,
                  foreign_isAForeign__,
                  foreign_isBForeign__,
                  this.aInterop,
                  this.bInterop,
                  this.toPrimitive,
                  this.isPrimitive,
                  this.equal
               );
            } else {
               if (leftNodeValue instanceof Number) {
                  Number leftNodeValue_xxxxxxx = (Number)leftNodeValue;
                  if (rightNodeValue instanceof Number) {
                     Number rightNodeValue_xx = (Number)rightNodeValue;
                     if (JSRuntime.isJavaNumber(leftNodeValue_xxxxxxx) && JSRuntime.isJavaNumber(rightNodeValue_xx)) {
                        this.state_0_ = state_0;
                        int var50;
                        this.state_1_ = var50 = state_1 | 16;
                        lock.unlock();
                        hasLock = false;
                        return JSEqualNode.doNumber(leftNodeValue_xxxxxxx, rightNodeValue_xx);
                     }
                  }
               }

               if (rightNodeValue instanceof TruffleString) {
                  TruffleString rightNodeValue_xx = (TruffleString)rightNodeValue;
                  if (JSRuntime.isJavaNumber(leftNodeValue)) {
                     this.state_0_ = state_0;
                     int var49;
                     this.state_1_ = var49 = state_1 | 32;
                     lock.unlock();
                     hasLock = false;
                     return this.doNumberString(leftNodeValue, rightNodeValue_xx);
                  }
               }

               if (leftNodeValue instanceof TruffleString) {
                  TruffleString leftNodeValue_xxxxxxx = (TruffleString)leftNodeValue;
                  if (JSRuntime.isJavaNumber(rightNodeValue)) {
                     this.state_0_ = state_0;
                     int var48;
                     this.state_1_ = var48 = state_1 | 64;
                     lock.unlock();
                     hasLock = false;
                     return this.doStringNumber(leftNodeValue_xxxxxxx, rightNodeValue);
                  }
               }

               this.state_0_ = state_0;
               int var47;
               this.state_1_ = var47 = state_1 | 128;
               lock.unlock();
               hasLock = false;
               return JSEqualNode.doFallback(leftNodeValue, rightNodeValue);
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
      int state_1 = this.state_1_;
      if ((state_0 & -167772161) == 0 && (state_1 & 0xFF) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         int counter = 0;
         counter += Integer.bitCount(state_0 & -167772161);
         counter += Integer.bitCount(state_1 & 0xFF);
         return counter == 1 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[39];
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
      s = new Object[]{"doIntBoolean", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doDouble", null, null};
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
      s = new Object[]{"doDoubleString", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"doDoubleBoolean", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      s = new Object[]{"doBoolean", null, null};
      if ((state_0 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[7] = s;
      s = new Object[]{"doBooleanInt", null, null};
      if ((state_0 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[8] = s;
      s = new Object[]{"doBooleanDouble", null, null};
      if ((state_0 & 256) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[9] = s;
      s = new Object[]{"doBooleanString", null, null};
      if ((state_0 & 512) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[10] = s;
      s = new Object[]{"doStringIdentity", null, null};
      if ((state_0 & 1024) != 0) {
         s[1] = (byte)1;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[11] = s;
      s = new Object[]{"doString", null, null};
      if ((state_0 & 2048) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.string_equalsNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[12] = s;
      s = new Object[]{"doStringDouble", null, null};
      if ((state_0 & 4096) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[13] = s;
      s = new Object[]{"doStringBoolean", null, null};
      if ((state_0 & 8192) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[14] = s;
      s = new Object[]{"doStringBigInt", null, null};
      if ((state_0 & 16384) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[15] = s;
      s = new Object[]{"doBigIntString", null, null};
      if ((state_0 & 32768) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[16] = s;
      s = new Object[]{"doBooleanBigInt", null, null};
      if ((state_0 & 65536) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[17] = s;
      s = new Object[]{"doBigIntBoolean", null, null};
      if ((state_0 & 131072) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[18] = s;
      s = new Object[]{"doBothNullOrUndefined", null, null};
      if ((state_0 & 262144) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[19] = s;
      s = new Object[]{"doLeftNullOrUndefined", null, null};
      if ((state_0 & 524288) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.bInterop));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[20] = s;
      s = new Object[]{"doRightNullOrUndefined", null, null};
      if ((state_0 & 1048576) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.aInterop));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[21] = s;
      s = new Object[]{"doOverloaded", null, null};
      if ((state_0 & 2097152) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.overloaded_overloadedOperatorNode_, this.overloaded_toBooleanNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[22] = s;
      s = new Object[]{"doJSObject", null, null};
      if ((state_0 & 4194304) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[23] = s;
      s = new Object[]{"doJSObject", null, null};
      if ((state_0 & 8388608) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[24] = s;
      s = new Object[]{"doJSObjectVsPrimitive", null, null};
      if ((state_0 & 16777216) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.bInterop, this.toPrimitive, this.isPrimitive, this.equal));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[25] = s;
      s = new Object[]{"doJSObjectVsPrimitive", null, null};
      if ((state_0 & 67108864) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.aInterop, this.toPrimitive, this.isPrimitive, this.equal));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[26] = s;
      s = new Object[]{"doBigIntAndInt", null, null};
      if ((state_0 & 268435456) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[27] = s;
      s = new Object[]{"doBigIntAndNumber", null, null};
      if ((state_0 & 536870912) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[28] = s;
      s = new Object[]{"doIntAndBigInt", null, null};
      if ((state_0 & 1073741824) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[29] = s;
      s = new Object[]{"doNumberAndBigInt", null, null};
      if ((state_0 & -2147483648) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[30] = s;
      s = new Object[]{"doSymbol", null, null};
      if ((state_1 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[31] = s;
      s = new Object[]{"doSymbolNotSymbol", null, null};
      if ((state_1 & 2) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[32] = s;
      s = new Object[]{"doSymbolNotSymbol", null, null};
      if ((state_1 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[33] = s;
      s = new Object[]{"doForeign", null, null};
      if ((state_1 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.aInterop, this.bInterop, this.toPrimitive, this.isPrimitive, this.equal));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[34] = s;
      s = new Object[]{"doNumber", null, null};
      if ((state_1 & 16) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[35] = s;
      s = new Object[]{"doNumberString", null, null};
      if ((state_1 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[36] = s;
      s = new Object[]{"doStringNumber", null, null};
      if ((state_1 & 64) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[37] = s;
      s = new Object[]{"doFallback", null, null};
      if ((state_1 & 128) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[38] = s;
      return Introspection.Provider.create(data);
   }

   public static JSEqualNode create(JavaScriptNode left, JavaScriptNode right) {
      return new JSEqualNodeGen(left, right);
   }
}
