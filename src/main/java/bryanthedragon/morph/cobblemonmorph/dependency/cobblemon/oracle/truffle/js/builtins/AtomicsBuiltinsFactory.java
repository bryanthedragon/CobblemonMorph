package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToIndexNode;
import com.oracle.truffle.js.nodes.cast.JSToInt32Node;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(AtomicsBuiltins.class)
public final class AtomicsBuiltinsFactory {
   @GeneratedBy(AtomicsBuiltins.AtomicsCompareExchangeNode.class)
   public static final class AtomicsCompareExchangeNodeGen extends AtomicsBuiltins.AtomicsCompareExchangeNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @Node.Child
      private JavaScriptNode arguments3_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToIndexNode toIndex;
      @CompilerDirectives.CompilationFinal
      private BranchProfile generic_notSharedArrayBuffer_;

      private AtomicsCompareExchangeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
         this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         if ((state_0 & 4048) == 0 && state_0 != 0) {
            return this.execute_int_int_int0(state_0, frameValue);
         } else if ((state_0 & 3967) == 0 && state_0 != 0) {
            return this.execute_int_int1(state_0, frameValue);
         } else {
            return (state_0 & 3983) == 0 && state_0 != 0 ? this.execute_int2(state_0, frameValue) : this.execute_generic3(state_0, frameValue);
         }
      }

      private Object execute_int_int_int0(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var11) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments2Value = this.arguments2_.execute(frameValue);
            Object arguments3Value = this.arguments3_.execute(frameValue);
            return this.executeAndSpecialize(arguments0Value_, var11.getResult(), arguments2Value, arguments3Value);
         }

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var10) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments3Value = this.arguments3_.execute(frameValue);
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, var10.getResult(), arguments3Value);
         }

         int arguments3Value_;
         try {
            arguments3Value_ = this.arguments3_.executeInt(frameValue);
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, var9.getResult());
         }

         if ((state_0 & 47) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray int8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8Array_ta__) && int8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)
                  )
                {
                  return this.doInt8Array(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int8Array_ta__);
               }
            }

            if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray uint8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8Array_ta__)
                  && uint8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doUint8Array(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, uint8Array_ta__);
               }
            }

            if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray int16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16Array_ta__)
                  && int16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doInt16Array(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int16Array_ta__);
               }
            }

            if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray uint16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16Array_ta__)
                  && uint16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doUint16Array(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, uint16Array_ta__);
               }
            }

            if ((state_0 & 32) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray int32ArrayInt_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayInt_ta__)
                  && int32ArrayInt_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doInt32ArrayInt(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int32ArrayInt_ta__);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
      }

      private Object execute_int_int1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var10) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments3Value = this.arguments3_.execute(frameValue);
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, var10.getResult(), arguments3Value);
         }

         int arguments3Value_;
         try {
            arguments3Value_ = this.arguments3_.executeInt(frameValue);
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, var9.getResult());
         }

         assert (state_0 & 128) != 0;

         if (arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray int32ArrayIntObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayIntObjIdx_ta__)) {
                  return this.doInt32ArrayIntObjIdx(
                     arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int32ArrayIntObjIdx_ta__, this.toIndex
                  );
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
      }

      private Object execute_int2(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var11) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments2Value = this.arguments2_.execute(frameValue);
            Object arguments3Value = this.arguments3_.execute(frameValue);
            return this.executeAndSpecialize(arguments0Value_, var11.getResult(), arguments2Value, arguments3Value);
         }

         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         if ((state_0 & 112) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if ((state_0 & 16) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray uint32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(uint32Array_ta__)
                  && uint32Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doUint32Array(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, uint32Array_ta__);
               }
            }

            if ((state_0 & 32) != 0 && arguments2Value_ instanceof Integer) {
               int arguments2Value__ = (Integer)arguments2Value_;
               if (arguments3Value_ instanceof Integer) {
                  int arguments3Value__ = (Integer)arguments3Value_;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray int32ArrayInt_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayInt_ta__)
                        && int32ArrayInt_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                        return this.doInt32ArrayInt(arguments0Value__, arguments1Value_, arguments2Value__, arguments3Value__, int32ArrayInt_ta__);
                     }
                  }
               }
            }

            if ((state_0 & 64) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__)
                  && int32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doInt32ArrayObj(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int32ArrayObj_ta__);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
      }

      private Object execute_generic3(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         if ((state_0 & 2047) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if ((state_0 & 127) != 0 && arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               if ((state_0 & 15) != 0 && arguments2Value_ instanceof Integer) {
                  int arguments2Value__ = (Integer)arguments2Value_;
                  if (arguments3Value_ instanceof Integer) {
                     int arguments3Value__ = (Integer)arguments3Value_;
                     if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                        TypedArray int8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8Array_ta__)
                           && int8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                           return this.doInt8Array(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, int8Array_ta__);
                        }
                     }

                     if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                        TypedArray uint8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8Array_ta__)
                           && uint8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                           return this.doUint8Array(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, uint8Array_ta__);
                        }
                     }

                     if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                        TypedArray int16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16Array_ta__)
                           && int16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                           return this.doInt16Array(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, int16Array_ta__);
                        }
                     }

                     if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                        TypedArray uint16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16Array_ta__)
                           && uint16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                           return this.doUint16Array(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, uint16Array_ta__);
                        }
                     }
                  }
               }

               if ((state_0 & 16) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray uint32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(uint32Array_ta__)
                     && uint32Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doUint32Array(arguments0Value__, arguments1Value__, arguments2Value_, arguments3Value_, uint32Array_ta__);
                  }
               }

               if ((state_0 & 32) != 0 && arguments2Value_ instanceof Integer) {
                  int arguments2Value__ = (Integer)arguments2Value_;
                  if (arguments3Value_ instanceof Integer) {
                     int arguments3Value__x = (Integer)arguments3Value_;
                     if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                        TypedArray int32ArrayInt_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayInt_ta__)
                           && int32ArrayInt_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                           return this.doInt32ArrayInt(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__x, int32ArrayInt_ta__);
                        }
                     }
                  }
               }

               if ((state_0 & 64) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__)
                     && int32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doInt32ArrayObj(arguments0Value__, arguments1Value__, arguments2Value_, arguments3Value_, int32ArrayObj_ta__);
                  }
               }
            }

            if ((state_0 & 1920) != 0) {
               if ((state_0 & 128) != 0 && arguments2Value_ instanceof Integer) {
                  int arguments2Value__ = (Integer)arguments2Value_;
                  if (arguments3Value_ instanceof Integer) {
                     int arguments3Value__x = (Integer)arguments3Value_;
                     if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                        TypedArray int32ArrayIntObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayIntObjIdx_ta__)) {
                           return this.doInt32ArrayIntObjIdx(
                              arguments0Value__, arguments1Value_, arguments2Value__, arguments3Value__x, int32ArrayIntObjIdx_ta__, this.toIndex
                           );
                        }
                     }
                  }
               }

               if ((state_0 & 1792) != 0) {
                  if ((state_0 & 256) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray int32ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObjObjIdx_ta__)) {
                        return this.doInt32ArrayObjObjIdx(
                           arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int32ArrayObjObjIdx_ta__, this.toIndex
                        );
                     }
                  }

                  if ((state_0 & 512) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray bigInt64ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(bigInt64ArrayObjObjIdx_ta__)) {
                        return this.doBigInt64ArrayObjObjIdx(
                           arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, bigInt64ArrayObjObjIdx_ta__, this.toIndex
                        );
                     }
                  }

                  if ((state_0 & 1024) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray bigUint64ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(bigUint64ArrayObjObjIdx_ta__)) {
                        return this.doBigUint64ArrayObjObjIdx(
                           arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, bigUint64ArrayObjObjIdx_ta__, this.toIndex
                        );
                     }
                  }
               }
            }
         }

         if ((state_0 & 2048) != 0) {
            return this.doGeneric(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, this.toIndex, this.generic_notSharedArrayBuffer_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
         }
      }

      @Override
      public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 2064) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 448) == 0 && (state_0 & 495) != 0) {
               return this.executeInt_int_int_int4(state_0, frameValue, arguments0Value_);
            } else if ((state_0 & 367) == 0 && (state_0 & 495) != 0) {
               return this.executeInt_int_int5(state_0, frameValue, arguments0Value_);
            } else {
               return (state_0 & 431) == 0 && (state_0 & 495) != 0
                  ? this.executeInt_int6(state_0, frameValue, arguments0Value_)
                  : this.executeInt_generic7(state_0, frameValue, arguments0Value_);
            }
         }
      }

      private int executeInt_int_int_int4(int state_0, VirtualFrame frameValue, Object arguments0Value_) throws UnexpectedResultException {
         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var11) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments2Value = this.arguments2_.execute(frameValue);
            Object arguments3Value = this.arguments3_.execute(frameValue);
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, var11.getResult(), arguments2Value, arguments3Value));
         }

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var10) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments3Value = this.arguments3_.execute(frameValue);
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, var10.getResult(), arguments3Value));
         }

         int arguments3Value_;
         try {
            arguments3Value_ = this.arguments3_.executeInt(frameValue);
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, var9.getResult()));
         }

         if ((state_0 & 47) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray int8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8Array_ta__) && int8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)
                  )
                {
                  return this.doInt8Array(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int8Array_ta__);
               }
            }

            if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray uint8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8Array_ta__)
                  && uint8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doUint8Array(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, uint8Array_ta__);
               }
            }

            if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray int16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16Array_ta__)
                  && int16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doInt16Array(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int16Array_ta__);
               }
            }

            if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray uint16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16Array_ta__)
                  && uint16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doUint16Array(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, uint16Array_ta__);
               }
            }

            if ((state_0 & 32) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray int32ArrayInt_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayInt_ta__)
                  && int32ArrayInt_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doInt32ArrayInt(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int32ArrayInt_ta__);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_));
      }

      private int executeInt_int_int5(int state_0, VirtualFrame frameValue, Object arguments0Value_) throws UnexpectedResultException {
         Object arguments1Value_ = this.arguments1_.execute(frameValue);

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var10) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments3Value = this.arguments3_.execute(frameValue);
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, var10.getResult(), arguments3Value));
         }

         int arguments3Value_;
         try {
            arguments3Value_ = this.arguments3_.executeInt(frameValue);
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, var9.getResult()));
         }

         assert (state_0 & 128) != 0;

         if (arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray int32ArrayIntObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayIntObjIdx_ta__)) {
                  return this.doInt32ArrayIntObjIdx(
                     arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int32ArrayIntObjIdx_ta__, this.toIndex
                  );
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_));
      }

      private int executeInt_int6(int state_0, VirtualFrame frameValue, Object arguments0Value_) throws UnexpectedResultException {
         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments2Value = this.arguments2_.execute(frameValue);
            Object arguments3Value = this.arguments3_.execute(frameValue);
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, var9.getResult(), arguments2Value, arguments3Value));
         }

         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);

         assert (state_0 & 64) != 0;

         if (arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__)
                  && int32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doInt32ArrayObj(arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int32ArrayObj_ta__);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_));
      }

      private int executeInt_generic7(int state_0, VirtualFrame frameValue, Object arguments0Value_) throws UnexpectedResultException {
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         if ((state_0 & 495) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if ((state_0 & 111) != 0 && arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               if ((state_0 & 47) != 0 && arguments2Value_ instanceof Integer) {
                  int arguments2Value__ = (Integer)arguments2Value_;
                  if (arguments3Value_ instanceof Integer) {
                     int arguments3Value__ = (Integer)arguments3Value_;
                     if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                        TypedArray int8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8Array_ta__)
                           && int8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                           return this.doInt8Array(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, int8Array_ta__);
                        }
                     }

                     if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                        TypedArray uint8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8Array_ta__)
                           && uint8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                           return this.doUint8Array(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, uint8Array_ta__);
                        }
                     }

                     if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                        TypedArray int16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16Array_ta__)
                           && int16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                           return this.doInt16Array(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, int16Array_ta__);
                        }
                     }

                     if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                        TypedArray uint16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16Array_ta__)
                           && uint16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                           return this.doUint16Array(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, uint16Array_ta__);
                        }
                     }

                     if ((state_0 & 32) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                        TypedArray int32ArrayInt_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayInt_ta__)
                           && int32ArrayInt_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                           return this.doInt32ArrayInt(arguments0Value__, arguments1Value__, arguments2Value__, arguments3Value__, int32ArrayInt_ta__);
                        }
                     }
                  }
               }

               if ((state_0 & 64) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__)
                     && int32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doInt32ArrayObj(arguments0Value__, arguments1Value__, arguments2Value_, arguments3Value_, int32ArrayObj_ta__);
                  }
               }
            }

            if ((state_0 & 384) != 0) {
               if ((state_0 & 128) != 0 && arguments2Value_ instanceof Integer) {
                  int arguments2Value__ = (Integer)arguments2Value_;
                  if (arguments3Value_ instanceof Integer) {
                     int arguments3Value__x = (Integer)arguments3Value_;
                     if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                        TypedArray int32ArrayIntObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayIntObjIdx_ta__)) {
                           return this.doInt32ArrayIntObjIdx(
                              arguments0Value__, arguments1Value_, arguments2Value__, arguments3Value__x, int32ArrayIntObjIdx_ta__, this.toIndex
                           );
                        }
                     }
                  }
               }

               if ((state_0 & 256) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray int32ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObjObjIdx_ta__)) {
                     return this.doInt32ArrayObjObjIdx(
                        arguments0Value__, arguments1Value_, arguments2Value_, arguments3Value_, int32ArrayObjObjIdx_ta__, this.toIndex
                     );
                  }
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_));
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 3600) == 0 && state_0 != 0) {
               this.executeInt(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value, Object arguments3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSTypedArrayObject) {
               JSTypedArrayObject arguments0Value_ = (JSTypedArrayObject)arguments0Value;
               if (arguments1Value instanceof Integer) {
                  int arguments1Value_ = (Integer)arguments1Value;
                  if (arguments2Value instanceof Integer) {
                     int arguments2Value_ = (Integer)arguments2Value;
                     if (arguments3Value instanceof Integer) {
                        int arguments3Value_ = (Integer)arguments3Value;
                        TypedArray int8Array_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                           int8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                           if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8Array_ta__)
                              && int8Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                              int var28;
                              this.state_0_ = var28 = state_0 | 1;
                              lock.unlock();
                              hasLock = false;
                              return this.doInt8Array(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, int8Array_ta__);
                           }
                        }

                        int8Array_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                           int8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                           if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(int8Array_ta__)
                              && int8Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                              int var27;
                              this.state_0_ = var27 = state_0 | 2;
                              lock.unlock();
                              hasLock = false;
                              return this.doUint8Array(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, int8Array_ta__);
                           }
                        }

                        int8Array_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                           int8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                           if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int8Array_ta__)
                              && int8Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                              int var26;
                              this.state_0_ = var26 = state_0 | 4;
                              lock.unlock();
                              hasLock = false;
                              return this.doInt16Array(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, int8Array_ta__);
                           }
                        }

                        int8Array_ta__ = null;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                           int8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                           if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(int8Array_ta__)
                              && int8Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                              int var25;
                              this.state_0_ = var25 = state_0 | 8;
                              lock.unlock();
                              hasLock = false;
                              return this.doUint16Array(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_, int8Array_ta__);
                           }
                        }
                     }
                  }

                  TypedArray uint32Array_ta__ = null;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                     uint32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(uint32Array_ta__)
                        && uint32Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        int var24;
                        this.state_0_ = var24 = state_0 | 16;
                        lock.unlock();
                        hasLock = false;
                        return this.doUint32Array(arguments0Value_, arguments1Value_, arguments2Value, arguments3Value, uint32Array_ta__);
                     }
                  }

                  uint32Array_ta__ = null;
                  if (arguments2Value instanceof Integer) {
                     int arguments2Value_ = (Integer)arguments2Value;
                     if (arguments3Value instanceof Integer) {
                        int arguments3Value_x = (Integer)arguments3Value;
                        if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                           uint32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                           if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(uint32Array_ta__)
                              && uint32Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                              int var23;
                              this.state_0_ = var23 = state_0 | 32;
                              lock.unlock();
                              hasLock = false;
                              return this.doInt32ArrayInt(arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_x, uint32Array_ta__);
                           }
                        }
                     }
                  }

                  uint32Array_ta__ = null;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                     uint32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(uint32Array_ta__)
                        && uint32Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        int var22;
                        this.state_0_ = var22 = state_0 | 64;
                        lock.unlock();
                        hasLock = false;
                        return this.doInt32ArrayObj(arguments0Value_, arguments1Value_, arguments2Value, arguments3Value, uint32Array_ta__);
                     }
                  }
               }

               TypedArray int32ArrayIntObjIdx_ta__ = null;
               if (arguments2Value instanceof Integer) {
                  int arguments2Value_ = (Integer)arguments2Value;
                  if (arguments3Value instanceof Integer) {
                     int arguments3Value_x = (Integer)arguments3Value;
                     if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                        int32ArrayIntObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayIntObjIdx_ta__)) {
                           this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                           int var21;
                           this.state_0_ = var21 = state_0 | 128;
                           lock.unlock();
                           hasLock = false;
                           return this.doInt32ArrayIntObjIdx(
                              arguments0Value_, arguments1Value, arguments2Value_, arguments3Value_x, int32ArrayIntObjIdx_ta__, this.toIndex
                           );
                        }
                     }
                  }
               }

               int32ArrayIntObjIdx_ta__ = null;
               if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                  int32ArrayIntObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayIntObjIdx_ta__)) {
                     this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                     int var20;
                     this.state_0_ = var20 = state_0 | 256;
                     lock.unlock();
                     hasLock = false;
                     return this.doInt32ArrayObjObjIdx(
                        arguments0Value_, arguments1Value, arguments2Value, arguments3Value, int32ArrayIntObjIdx_ta__, this.toIndex
                     );
                  }
               }

               int32ArrayIntObjIdx_ta__ = null;
               if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                  int32ArrayIntObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(int32ArrayIntObjIdx_ta__)) {
                     this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                     int var19;
                     this.state_0_ = var19 = state_0 | 512;
                     lock.unlock();
                     hasLock = false;
                     return this.doBigInt64ArrayObjObjIdx(
                        arguments0Value_, arguments1Value, arguments2Value, arguments3Value, int32ArrayIntObjIdx_ta__, this.toIndex
                     );
                  }
               }

               int32ArrayIntObjIdx_ta__ = null;
               if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                  int32ArrayIntObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(int32ArrayIntObjIdx_ta__)) {
                     this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                     int var18;
                     this.state_0_ = var18 = state_0 | 1024;
                     lock.unlock();
                     hasLock = false;
                     return this.doBigUint64ArrayObjObjIdx(
                        arguments0Value_, arguments1Value, arguments2Value, arguments3Value, int32ArrayIntObjIdx_ta__, this.toIndex
                     );
                  }
               }
            }

            this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
            this.generic_notSharedArrayBuffer_ = BranchProfile.create();
            int var17;
            this.state_0_ = var17 = state_0 | 2048;
            lock.unlock();
            hasLock = false;
            return this.doGeneric(arguments0Value, arguments1Value, arguments2Value, arguments3Value, this.toIndex, this.generic_notSharedArrayBuffer_);
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
         Object[] data = new Object[13];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"doInt8Array", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doUint8Array", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doInt16Array", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"doUint16Array", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"doUint32Array", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"doInt32ArrayInt", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"doInt32ArrayObj", null, null};
         if ((state_0 & 64) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         s = new Object[]{"doInt32ArrayIntObjIdx", null, null};
         if ((state_0 & 128) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toIndex));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[8] = s;
         s = new Object[]{"doInt32ArrayObjObjIdx", null, null};
         if ((state_0 & 256) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toIndex));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[9] = s;
         s = new Object[]{"doBigInt64ArrayObjObjIdx", null, null};
         if ((state_0 & 512) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toIndex));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[10] = s;
         s = new Object[]{"doBigUint64ArrayObjObjIdx", null, null};
         if ((state_0 & 1024) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toIndex));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[11] = s;
         s = new Object[]{"doGeneric", null, null};
         if ((state_0 & 2048) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toIndex, this.generic_notSharedArrayBuffer_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[12] = s;
         return Introspection.Provider.create(data);
      }

      public static AtomicsBuiltins.AtomicsCompareExchangeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new AtomicsBuiltinsFactory.AtomicsCompareExchangeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(AtomicsBuiltins.AtomicsComputeNode.class)
   public static final class AtomicsComputeNodeGen extends AtomicsBuiltins.AtomicsComputeNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToIndexNode toIndex;
      @CompilerDirectives.CompilationFinal
      private BranchProfile generic_notSharedArrayBuffer_;

      private AtomicsComputeNodeGen(
         JSContext context,
         JSBuiltin builtin,
         AtomicsBuiltins.AtomicIntBinaryOperator intOperator,
         AtomicsBuiltins.AtomicBinaryOperator<BigInt> bigIntOperator,
         JavaScriptNode[] arguments
      ) {
         super(context, builtin, intOperator, bigIntOperator);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         if ((state_0 & 960) == 0 && state_0 != 0) {
            return this.execute_int_int0(state_0, frameValue);
         } else {
            return (state_0 & 959) == 0 && state_0 != 0 ? this.execute_int1(state_0, frameValue) : this.execute_generic2(state_0, frameValue);
         }
      }

      private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments2Value = this.arguments2_.execute(frameValue);
            return this.executeAndSpecialize(arguments0Value_, var9.getResult(), arguments2Value);
         }

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var8) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, var8.getResult());
         }

         if ((state_0 & 63) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedInt8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array_ta__)
                  && sharedInt8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doSharedInt8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt8Array_ta__);
               }
            }

            if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedUint8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array_ta__)
                  && sharedUint8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doSharedUint8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint8Array_ta__);
               }
            }

            if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedInt16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array_ta__)
                  && sharedInt16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doSharedInt16Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt16Array_ta__);
               }
            }

            if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedUint16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array_ta__)
                  && sharedUint16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doSharedUint16Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint16Array_ta__);
               }
            }

            if ((state_0 & 16) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedInt32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array_ta__)
                  && sharedInt32Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doSharedInt32Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt32Array_ta__);
               }
            }

            if ((state_0 & 32) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedUint32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array_ta__)
                  && sharedUint32Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doSharedUint32Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint32Array_ta__);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      private Object execute_int1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var8) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, var8.getResult());
         }

         assert (state_0 & 64) != 0;

         if (arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedInt32ArrayObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32ArrayObjIdx_ta__)) {
                  return this.doSharedInt32ArrayObjIdx(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt32ArrayObjIdx_ta__, this.toIndex);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      private Object execute_generic2(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if ((state_0 & 511) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if ((state_0 & 127) != 0 && arguments2Value_ instanceof Integer) {
               int arguments2Value__ = (Integer)arguments2Value_;
               if ((state_0 & 63) != 0 && arguments1Value_ instanceof Integer) {
                  int arguments1Value__ = (Integer)arguments1Value_;
                  if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedInt8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array_ta__)
                        && sharedInt8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedInt8Array(arguments0Value__, arguments1Value__, arguments2Value__, sharedInt8Array_ta__);
                     }
                  }

                  if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedUint8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array_ta__)
                        && sharedUint8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedUint8Array(arguments0Value__, arguments1Value__, arguments2Value__, sharedUint8Array_ta__);
                     }
                  }

                  if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedInt16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array_ta__)
                        && sharedInt16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedInt16Array(arguments0Value__, arguments1Value__, arguments2Value__, sharedInt16Array_ta__);
                     }
                  }

                  if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedUint16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array_ta__)
                        && sharedUint16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedUint16Array(arguments0Value__, arguments1Value__, arguments2Value__, sharedUint16Array_ta__);
                     }
                  }

                  if ((state_0 & 16) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedInt32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array_ta__)
                        && sharedInt32Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedInt32Array(arguments0Value__, arguments1Value__, arguments2Value__, sharedInt32Array_ta__);
                     }
                  }

                  if ((state_0 & 32) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedUint32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array_ta__)
                        && sharedUint32Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedUint32Array(arguments0Value__, arguments1Value__, arguments2Value__, sharedUint32Array_ta__);
                     }
                  }
               }

               if ((state_0 & 64) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedInt32ArrayObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32ArrayObjIdx_ta__)) {
                     return this.doSharedInt32ArrayObjIdx(arguments0Value__, arguments1Value_, arguments2Value__, sharedInt32ArrayObjIdx_ta__, this.toIndex);
                  }
               }
            }

            if ((state_0 & 384) != 0) {
               if ((state_0 & 128) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedBigInt64Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(sharedBigInt64Array_ta__)) {
                     return this.doSharedBigInt64Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedBigInt64Array_ta__, this.toIndex);
                  }
               }

               if ((state_0 & 256) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedBigUint64Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(sharedBigUint64Array_ta__)) {
                     return this.doSharedBigUint64Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedBigUint64Array_ta__, this.toIndex);
                  }
               }
            }
         }

         if ((state_0 & 512) != 0) {
            return this.doGeneric(arguments0Value_, arguments1Value_, arguments2Value_, this.toIndex, this.generic_notSharedArrayBuffer_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 512) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 64) == 0 && (state_0 & 95) != 0) {
               return this.executeInt_int_int3(state_0, frameValue, arguments0Value_);
            } else {
               return (state_0 & 31) == 0 && (state_0 & 95) != 0
                  ? this.executeInt_int4(state_0, frameValue, arguments0Value_)
                  : this.executeInt_generic5(state_0, frameValue, arguments0Value_);
            }
         }
      }

      private int executeInt_int_int3(int state_0, VirtualFrame frameValue, Object arguments0Value_) throws UnexpectedResultException {
         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments2Value = this.arguments2_.execute(frameValue);
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, var9.getResult(), arguments2Value));
         }

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var8) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, var8.getResult()));
         }

         if ((state_0 & 31) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedInt8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array_ta__)
                  && sharedInt8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doSharedInt8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt8Array_ta__);
               }
            }

            if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedUint8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array_ta__)
                  && sharedUint8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doSharedUint8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint8Array_ta__);
               }
            }

            if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedInt16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array_ta__)
                  && sharedInt16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doSharedInt16Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt16Array_ta__);
               }
            }

            if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedUint16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array_ta__)
                  && sharedUint16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doSharedUint16Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint16Array_ta__);
               }
            }

            if ((state_0 & 16) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedInt32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array_ta__)
                  && sharedInt32Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doSharedInt32Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt32Array_ta__);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_));
      }

      private int executeInt_int4(int state_0, VirtualFrame frameValue, Object arguments0Value_) throws UnexpectedResultException {
         Object arguments1Value_ = this.arguments1_.execute(frameValue);

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var8) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, var8.getResult()));
         }

         assert (state_0 & 64) != 0;

         if (arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedInt32ArrayObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32ArrayObjIdx_ta__)) {
                  return this.doSharedInt32ArrayObjIdx(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt32ArrayObjIdx_ta__, this.toIndex);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_));
      }

      private int executeInt_generic5(int state_0, VirtualFrame frameValue, Object arguments0Value_) throws UnexpectedResultException {
         Object arguments1Value_ = this.arguments1_.execute(frameValue);

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, var9.getResult()));
         }

         if ((state_0 & 95) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if ((state_0 & 31) != 0 && arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedInt8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array_ta__)
                     && sharedInt8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doSharedInt8Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedInt8Array_ta__);
                  }
               }

               if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedUint8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array_ta__)
                     && sharedUint8Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doSharedUint8Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedUint8Array_ta__);
                  }
               }

               if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedInt16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array_ta__)
                     && sharedInt16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doSharedInt16Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedInt16Array_ta__);
                  }
               }

               if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedUint16Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array_ta__)
                     && sharedUint16Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doSharedUint16Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedUint16Array_ta__);
                  }
               }

               if ((state_0 & 16) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedInt32Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array_ta__)
                     && sharedInt32Array_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doSharedInt32Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedInt32Array_ta__);
                  }
               }
            }

            if ((state_0 & 64) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedInt32ArrayObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32ArrayObjIdx_ta__)) {
                  return this.doSharedInt32ArrayObjIdx(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt32ArrayObjIdx_ta__, this.toIndex);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_));
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 928) == 0 && state_0 != 0) {
               this.executeInt(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSTypedArrayObject) {
               JSTypedArrayObject arguments0Value_ = (JSTypedArrayObject)arguments0Value;
               if (arguments2Value instanceof Integer) {
                  int arguments2Value_ = (Integer)arguments2Value;
                  if (arguments1Value instanceof Integer) {
                     int arguments1Value_ = (Integer)arguments1Value;
                     TypedArray sharedInt8Array_ta__ = null;
                     if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                        sharedInt8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array_ta__)
                           && sharedInt8Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                           int var24;
                           this.state_0_ = var24 = state_0 | 1;
                           lock.unlock();
                           hasLock = false;
                           return this.doSharedInt8Array(arguments0Value_, arguments1Value_, arguments2Value_, sharedInt8Array_ta__);
                        }
                     }

                     sharedInt8Array_ta__ = null;
                     if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                        sharedInt8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedInt8Array_ta__)
                           && sharedInt8Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                           int var23;
                           this.state_0_ = var23 = state_0 | 2;
                           lock.unlock();
                           hasLock = false;
                           return this.doSharedUint8Array(arguments0Value_, arguments1Value_, arguments2Value_, sharedInt8Array_ta__);
                        }
                     }

                     sharedInt8Array_ta__ = null;
                     if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                        sharedInt8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt8Array_ta__)
                           && sharedInt8Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                           int var22;
                           this.state_0_ = var22 = state_0 | 4;
                           lock.unlock();
                           hasLock = false;
                           return this.doSharedInt16Array(arguments0Value_, arguments1Value_, arguments2Value_, sharedInt8Array_ta__);
                        }
                     }

                     sharedInt8Array_ta__ = null;
                     if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                        sharedInt8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedInt8Array_ta__)
                           && sharedInt8Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                           int var21;
                           this.state_0_ = var21 = state_0 | 8;
                           lock.unlock();
                           hasLock = false;
                           return this.doSharedUint16Array(arguments0Value_, arguments1Value_, arguments2Value_, sharedInt8Array_ta__);
                        }
                     }

                     sharedInt8Array_ta__ = null;
                     if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                        sharedInt8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt8Array_ta__)
                           && sharedInt8Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                           int var20;
                           this.state_0_ = var20 = state_0 | 16;
                           lock.unlock();
                           hasLock = false;
                           return this.doSharedInt32Array(arguments0Value_, arguments1Value_, arguments2Value_, sharedInt8Array_ta__);
                        }
                     }

                     sharedInt8Array_ta__ = null;
                     if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                        sharedInt8Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedInt8Array_ta__)
                           && sharedInt8Array_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                           int var19;
                           this.state_0_ = var19 = state_0 | 32;
                           lock.unlock();
                           hasLock = false;
                           return this.doSharedUint32Array(arguments0Value_, arguments1Value_, arguments2Value_, sharedInt8Array_ta__);
                        }
                     }
                  }

                  TypedArray sharedInt32ArrayObjIdx_ta__ = null;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                     sharedInt32ArrayObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32ArrayObjIdx_ta__)) {
                        this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                        int var18;
                        this.state_0_ = var18 = state_0 | 64;
                        lock.unlock();
                        hasLock = false;
                        return this.doSharedInt32ArrayObjIdx(arguments0Value_, arguments1Value, arguments2Value_, sharedInt32ArrayObjIdx_ta__, this.toIndex);
                     }
                  }
               }

               TypedArray sharedBigInt64Array_ta__ = null;
               if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                  sharedBigInt64Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(sharedBigInt64Array_ta__)) {
                     this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                     int var17;
                     this.state_0_ = var17 = state_0 | 128;
                     lock.unlock();
                     hasLock = false;
                     return this.doSharedBigInt64Array(arguments0Value_, arguments1Value, arguments2Value, sharedBigInt64Array_ta__, this.toIndex);
                  }
               }

               sharedBigInt64Array_ta__ = null;
               if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                  sharedBigInt64Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(sharedBigInt64Array_ta__)) {
                     this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                     int var16;
                     this.state_0_ = var16 = state_0 | 256;
                     lock.unlock();
                     hasLock = false;
                     return this.doSharedBigUint64Array(arguments0Value_, arguments1Value, arguments2Value, sharedBigInt64Array_ta__, this.toIndex);
                  }
               }
            }

            this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
            this.generic_notSharedArrayBuffer_ = BranchProfile.create();
            int var15;
            this.state_0_ = var15 = state_0 | 512;
            lock.unlock();
            hasLock = false;
            return this.doGeneric(arguments0Value, arguments1Value, arguments2Value, this.toIndex, this.generic_notSharedArrayBuffer_);
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
         Object[] s = new Object[]{"doSharedInt8Array", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doSharedUint8Array", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doSharedInt16Array", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"doSharedUint16Array", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"doSharedInt32Array", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"doSharedUint32Array", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"doSharedInt32ArrayObjIdx", null, null};
         if ((state_0 & 64) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toIndex));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         s = new Object[]{"doSharedBigInt64Array", null, null};
         if ((state_0 & 128) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toIndex));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[8] = s;
         s = new Object[]{"doSharedBigUint64Array", null, null};
         if ((state_0 & 256) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toIndex));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[9] = s;
         s = new Object[]{"doGeneric", null, null};
         if ((state_0 & 512) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toIndex, this.generic_notSharedArrayBuffer_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[10] = s;
         return Introspection.Provider.create(data);
      }

      public static AtomicsBuiltins.AtomicsComputeNode create(
         JSContext context,
         JSBuiltin builtin,
         AtomicsBuiltins.AtomicIntBinaryOperator intOperator,
         AtomicsBuiltins.AtomicBinaryOperator<BigInt> bigIntOperator,
         JavaScriptNode[] arguments
      ) {
         return new AtomicsBuiltinsFactory.AtomicsComputeNodeGen(context, builtin, intOperator, bigIntOperator, arguments);
      }
   }

   @GeneratedBy(AtomicsBuiltins.AtomicsIsLockFreeNode.class)
   public static final class AtomicsIsLockFreeNodeGen extends AtomicsBuiltins.AtomicsIsLockFreeNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToInt32Node generic_toInt32Node_;

      private AtomicsIsLockFreeNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         return (state_0 & 2) == 0 && state_0 != 0 ? this.execute_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
      }

      private Object execute_int0(int state_0, VirtualFrame frameValue) {
         int arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var5.getResult());
         }

         assert (state_0 & 1) != 0;

         return AtomicsBuiltins.AtomicsIsLockFreeNode.doInt(arguments0Value_);
      }

      private Object execute_generic1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
            int arguments0Value__ = (Integer)arguments0Value_;
            return AtomicsBuiltins.AtomicsIsLockFreeNode.doInt(arguments0Value__);
         } else if ((state_0 & 2) != 0) {
            return AtomicsBuiltins.AtomicsIsLockFreeNode.doGeneric(arguments0Value_, this.generic_toInt32Node_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         return (state_0 & 2) == 0 && state_0 != 0 ? this.executeBoolean_int2(state_0, frameValue) : this.executeBoolean_generic3(state_0, frameValue);
      }

      private boolean executeBoolean_int2(int state_0, VirtualFrame frameValue) {
         int arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
         } catch (UnexpectedResultException var5) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(var5.getResult());
         }

         assert (state_0 & 1) != 0;

         return AtomicsBuiltins.AtomicsIsLockFreeNode.doInt(arguments0Value_);
      }

      private boolean executeBoolean_generic3(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
            int arguments0Value__ = (Integer)arguments0Value_;
            return AtomicsBuiltins.AtomicsIsLockFreeNode.doInt(arguments0Value__);
         } else if ((state_0 & 2) != 0) {
            return AtomicsBuiltins.AtomicsIsLockFreeNode.doGeneric(arguments0Value_, this.generic_toInt32Node_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var6;
         try {
            int state_0 = this.state_0_;
            if (!(arguments0Value instanceof Integer)) {
               this.generic_toInt32Node_ = super.insert(JSToInt32Node.create());
               int var11;
               this.state_0_ = var11 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return AtomicsBuiltins.AtomicsIsLockFreeNode.doGeneric(arguments0Value, this.generic_toInt32Node_);
            }

            int arguments0Value_ = (Integer)arguments0Value;
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var6 = AtomicsBuiltins.AtomicsIsLockFreeNode.doInt(arguments0Value_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var6;
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
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"doInt", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doGeneric", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.generic_toInt32Node_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static AtomicsBuiltins.AtomicsIsLockFreeNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new AtomicsBuiltinsFactory.AtomicsIsLockFreeNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(AtomicsBuiltins.AtomicsLoadNode.class)
   public static final class AtomicsLoadNodeGen extends AtomicsBuiltins.AtomicsLoadNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToIndexNode toIndex;

      private AtomicsLoadNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @Override
      public Object executeWithBufferAndIndex(VirtualFrame frameValue, Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if ((state_0 & 511) != 0 && arguments0Value instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value_ = (JSTypedArrayObject)arguments0Value;
            if ((state_0 & 0xFF) != 0 && arguments1Value instanceof Integer) {
               int arguments1Value_ = (Integer)arguments1Value;
               if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                  TypedArray int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8ArrayObj_ta__)
                     && int8ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                     return this.doInt8ArrayObj(arguments0Value_, arguments1Value_, int8ArrayObj_ta__);
                  }
               }

               if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                  TypedArray uint8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8ArrayObj_ta__)
                     && uint8ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                     return this.doUint8ArrayObj(arguments0Value_, arguments1Value_, uint8ArrayObj_ta__);
                  }
               }

               if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                  TypedArray int16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16ArrayObj_ta__)
                     && int16ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                     return this.doInt16ArrayObj(arguments0Value_, arguments1Value_, int16ArrayObj_ta__);
                  }
               }

               if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                  TypedArray uint16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16ArrayObj_ta__)
                     && uint16ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                     return this.doUint16ArrayObj(arguments0Value_, arguments1Value_, uint16ArrayObj_ta__);
                  }
               }

               if ((state_0 & 16) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                  TypedArray int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__)
                     && int32ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                     return this.doInt32ArrayObj(arguments0Value_, arguments1Value_, int32ArrayObj_ta__);
                  }
               }

               if ((state_0 & 32) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                  TypedArray uint32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(uint32ArrayObj_ta__)
                     && uint32ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                     return this.doUint32ArrayObj(arguments0Value_, arguments1Value_, uint32ArrayObj_ta__);
                  }
               }

               if ((state_0 & 64) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                  TypedArray bigInt64ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(bigInt64ArrayObj_ta__)
                     && bigInt64ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                     return this.doBigInt64ArrayObj(arguments0Value_, arguments1Value_, bigInt64ArrayObj_ta__);
                  }
               }

               if ((state_0 & 128) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                  TypedArray bigUint64ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(bigUint64ArrayObj_ta__)
                     && bigUint64ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                     return this.doBigUint64ArrayObj(arguments0Value_, arguments1Value_, bigUint64ArrayObj_ta__);
                  }
               }
            }

            if ((state_0 & 256) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
               TypedArray int32ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObjObjIdx_ta__)) {
                  return this.doInt32ArrayObjObjIdx(arguments0Value_, arguments1Value, int32ArrayObjObjIdx_ta__, this.toIndex);
               }
            }
         }

         if ((state_0 & 512) != 0) {
            return this.doGeneric(arguments0Value, arguments1Value, this.toIndex);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value, arguments1Value);
         }
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         return (state_0 & 768) == 0 && state_0 != 0 ? this.execute_int0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
      }

      private Object execute_int0(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, var7.getResult());
         }

         if ((state_0 & 0xFF) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8ArrayObj_ta__)
                  && int8ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doInt8ArrayObj(arguments0Value__, arguments1Value_, int8ArrayObj_ta__);
               }
            }

            if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray uint8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8ArrayObj_ta__)
                  && uint8ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doUint8ArrayObj(arguments0Value__, arguments1Value_, uint8ArrayObj_ta__);
               }
            }

            if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray int16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16ArrayObj_ta__)
                  && int16ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doInt16ArrayObj(arguments0Value__, arguments1Value_, int16ArrayObj_ta__);
               }
            }

            if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray uint16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16ArrayObj_ta__)
                  && uint16ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doUint16ArrayObj(arguments0Value__, arguments1Value_, uint16ArrayObj_ta__);
               }
            }

            if ((state_0 & 16) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__)
                  && int32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doInt32ArrayObj(arguments0Value__, arguments1Value_, int32ArrayObj_ta__);
               }
            }

            if ((state_0 & 32) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray uint32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(uint32ArrayObj_ta__)
                  && uint32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doUint32ArrayObj(arguments0Value__, arguments1Value_, uint32ArrayObj_ta__);
               }
            }

            if ((state_0 & 64) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray bigInt64ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(bigInt64ArrayObj_ta__)
                  && bigInt64ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doBigInt64ArrayObj(arguments0Value__, arguments1Value_, bigInt64ArrayObj_ta__);
               }
            }

            if ((state_0 & 128) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray bigUint64ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(bigUint64ArrayObj_ta__)
                  && bigUint64ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doBigUint64ArrayObj(arguments0Value__, arguments1Value_, bigUint64ArrayObj_ta__);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      private Object execute_generic1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 511) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if ((state_0 & 0xFF) != 0 && arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8ArrayObj_ta__)
                     && int8ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doInt8ArrayObj(arguments0Value__, arguments1Value__, int8ArrayObj_ta__);
                  }
               }

               if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray uint8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8ArrayObj_ta__)
                     && uint8ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doUint8ArrayObj(arguments0Value__, arguments1Value__, uint8ArrayObj_ta__);
                  }
               }

               if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray int16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16ArrayObj_ta__)
                     && int16ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doInt16ArrayObj(arguments0Value__, arguments1Value__, int16ArrayObj_ta__);
                  }
               }

               if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray uint16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16ArrayObj_ta__)
                     && uint16ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doUint16ArrayObj(arguments0Value__, arguments1Value__, uint16ArrayObj_ta__);
                  }
               }

               if ((state_0 & 16) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__)
                     && int32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doInt32ArrayObj(arguments0Value__, arguments1Value__, int32ArrayObj_ta__);
                  }
               }

               if ((state_0 & 32) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray uint32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(uint32ArrayObj_ta__)
                     && uint32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doUint32ArrayObj(arguments0Value__, arguments1Value__, uint32ArrayObj_ta__);
                  }
               }

               if ((state_0 & 64) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray bigInt64ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(bigInt64ArrayObj_ta__)
                     && bigInt64ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doBigInt64ArrayObj(arguments0Value__, arguments1Value__, bigInt64ArrayObj_ta__);
                  }
               }

               if ((state_0 & 128) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray bigUint64ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(bigUint64ArrayObj_ta__)
                     && bigUint64ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doBigUint64ArrayObj(arguments0Value__, arguments1Value__, bigUint64ArrayObj_ta__);
                  }
               }
            }

            if ((state_0 & 256) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray int32ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObjObjIdx_ta__)) {
                  return this.doInt32ArrayObjObjIdx(arguments0Value__, arguments1Value_, int32ArrayObjObjIdx_ta__, this.toIndex);
               }
            }
         }

         if ((state_0 & 512) != 0) {
            return this.doGeneric(arguments0Value_, arguments1Value_, this.toIndex);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 512) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return (state_0 & 256) == 0 && (state_0 & 287) != 0
               ? this.executeInt_int2(state_0, frameValue, arguments0Value_)
               : this.executeInt_generic3(state_0, frameValue, arguments0Value_);
         }
      }

      private int executeInt_int2(int state_0, VirtualFrame frameValue, Object arguments0Value_) throws UnexpectedResultException {
         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, var7.getResult()));
         }

         if ((state_0 & 31) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8ArrayObj_ta__)
                  && int8ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doInt8ArrayObj(arguments0Value__, arguments1Value_, int8ArrayObj_ta__);
               }
            }

            if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray uint8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8ArrayObj_ta__)
                  && uint8ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doUint8ArrayObj(arguments0Value__, arguments1Value_, uint8ArrayObj_ta__);
               }
            }

            if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray int16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16ArrayObj_ta__)
                  && int16ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doInt16ArrayObj(arguments0Value__, arguments1Value_, int16ArrayObj_ta__);
               }
            }

            if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray uint16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16ArrayObj_ta__)
                  && uint16ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doUint16ArrayObj(arguments0Value__, arguments1Value_, uint16ArrayObj_ta__);
               }
            }

            if ((state_0 & 16) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__)
                  && int32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doInt32ArrayObj(arguments0Value__, arguments1Value_, int32ArrayObj_ta__);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
      }

      private int executeInt_generic3(int state_0, VirtualFrame frameValue, Object arguments0Value_) throws UnexpectedResultException {
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 287) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if ((state_0 & 31) != 0 && arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8ArrayObj_ta__)
                     && int8ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doInt8ArrayObj(arguments0Value__, arguments1Value__, int8ArrayObj_ta__);
                  }
               }

               if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray uint8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(uint8ArrayObj_ta__)
                     && uint8ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doUint8ArrayObj(arguments0Value__, arguments1Value__, uint8ArrayObj_ta__);
                  }
               }

               if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray int16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int16ArrayObj_ta__)
                     && int16ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doInt16ArrayObj(arguments0Value__, arguments1Value__, int16ArrayObj_ta__);
                  }
               }

               if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray uint16ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(uint16ArrayObj_ta__)
                     && uint16ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doUint16ArrayObj(arguments0Value__, arguments1Value__, uint16ArrayObj_ta__);
                  }
               }

               if ((state_0 & 16) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray int32ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObj_ta__)
                     && int32ArrayObj_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                     return this.doInt32ArrayObj(arguments0Value__, arguments1Value__, int32ArrayObj_ta__);
                  }
               }
            }

            if ((state_0 & 256) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray int32ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObjObjIdx_ta__)) {
                  return this.doInt32ArrayObjObjIdx(arguments0Value__, arguments1Value_, int32ArrayObjObjIdx_ta__, this.toIndex);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 736) == 0 && state_0 != 0) {
               this.executeInt(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSTypedArrayObject) {
               JSTypedArrayObject arguments0Value_ = (JSTypedArrayObject)arguments0Value;
               if (arguments1Value instanceof Integer) {
                  int arguments1Value_ = (Integer)arguments1Value;
                  TypedArray int8ArrayObj_ta__ = null;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                     int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(int8ArrayObj_ta__)
                        && int8ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        int var22;
                        this.state_0_ = var22 = state_0 | 1;
                        lock.unlock();
                        hasLock = false;
                        return this.doInt8ArrayObj(arguments0Value_, arguments1Value_, int8ArrayObj_ta__);
                     }
                  }

                  int8ArrayObj_ta__ = null;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                     int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(int8ArrayObj_ta__)
                        && int8ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        int var21;
                        this.state_0_ = var21 = state_0 | 2;
                        lock.unlock();
                        hasLock = false;
                        return this.doUint8ArrayObj(arguments0Value_, arguments1Value_, int8ArrayObj_ta__);
                     }
                  }

                  int8ArrayObj_ta__ = null;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                     int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(int8ArrayObj_ta__)
                        && int8ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        int var20;
                        this.state_0_ = var20 = state_0 | 4;
                        lock.unlock();
                        hasLock = false;
                        return this.doInt16ArrayObj(arguments0Value_, arguments1Value_, int8ArrayObj_ta__);
                     }
                  }

                  int8ArrayObj_ta__ = null;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                     int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(int8ArrayObj_ta__)
                        && int8ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        int var19;
                        this.state_0_ = var19 = state_0 | 8;
                        lock.unlock();
                        hasLock = false;
                        return this.doUint16ArrayObj(arguments0Value_, arguments1Value_, int8ArrayObj_ta__);
                     }
                  }

                  int8ArrayObj_ta__ = null;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                     int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int8ArrayObj_ta__)
                        && int8ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        int var18;
                        this.state_0_ = var18 = state_0 | 16;
                        lock.unlock();
                        hasLock = false;
                        return this.doInt32ArrayObj(arguments0Value_, arguments1Value_, int8ArrayObj_ta__);
                     }
                  }

                  int8ArrayObj_ta__ = null;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                     int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(int8ArrayObj_ta__)
                        && int8ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        int var17;
                        this.state_0_ = var17 = state_0 | 32;
                        lock.unlock();
                        hasLock = false;
                        return this.doUint32ArrayObj(arguments0Value_, arguments1Value_, int8ArrayObj_ta__);
                     }
                  }

                  int8ArrayObj_ta__ = null;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                     int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(int8ArrayObj_ta__)
                        && int8ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        int var16;
                        this.state_0_ = var16 = state_0 | 64;
                        lock.unlock();
                        hasLock = false;
                        return this.doBigInt64ArrayObj(arguments0Value_, arguments1Value_, int8ArrayObj_ta__);
                     }
                  }

                  int8ArrayObj_ta__ = null;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                     int8ArrayObj_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(int8ArrayObj_ta__)
                        && int8ArrayObj_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        int var15;
                        this.state_0_ = var15 = state_0 | 128;
                        lock.unlock();
                        hasLock = false;
                        return this.doBigUint64ArrayObj(arguments0Value_, arguments1Value_, int8ArrayObj_ta__);
                     }
                  }
               }

               TypedArray int32ArrayObjObjIdx_ta__ = null;
               if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                  int32ArrayObjObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(int32ArrayObjObjIdx_ta__)) {
                     this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                     int var14;
                     this.state_0_ = var14 = state_0 | 256;
                     lock.unlock();
                     hasLock = false;
                     return this.doInt32ArrayObjObjIdx(arguments0Value_, arguments1Value, int32ArrayObjObjIdx_ta__, this.toIndex);
                  }
               }
            }

            this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
            int var13;
            this.state_0_ = var13 = state_0 | 512;
            lock.unlock();
            hasLock = false;
            return this.doGeneric(arguments0Value, arguments1Value, this.toIndex);
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
         Object[] s = new Object[]{"doInt8ArrayObj", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doUint8ArrayObj", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doInt16ArrayObj", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"doUint16ArrayObj", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"doInt32ArrayObj", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"doUint32ArrayObj", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"doBigInt64ArrayObj", null, null};
         if ((state_0 & 64) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         s = new Object[]{"doBigUint64ArrayObj", null, null};
         if ((state_0 & 128) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[8] = s;
         s = new Object[]{"doInt32ArrayObjObjIdx", null, null};
         if ((state_0 & 256) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toIndex));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[9] = s;
         s = new Object[]{"doGeneric", null, null};
         if ((state_0 & 512) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toIndex));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[10] = s;
         return Introspection.Provider.create(data);
      }

      public static AtomicsBuiltins.AtomicsLoadNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new AtomicsBuiltinsFactory.AtomicsLoadNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(AtomicsBuiltins.AtomicsNotifyNode.class)
   public static final class AtomicsNotifyNodeGen extends AtomicsBuiltins.AtomicsNotifyNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private AtomicsBuiltinsFactory.AtomicsNotifyNodeGen.NotifyData notify_cache;

      private AtomicsNotifyNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if (state_0 != 0) {
            AtomicsBuiltinsFactory.AtomicsNotifyNodeGen.NotifyData s0_ = this.notify_cache;
            if (s0_ != null) {
               return this.doNotify(arguments0Value_, arguments1Value_, arguments2Value_, s0_.toIndexNode_, s0_.toInt32Node_, s0_.notSharedArrayBuffer_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var8;
         try {
            int state_0 = this.state_0_;
            AtomicsBuiltinsFactory.AtomicsNotifyNodeGen.NotifyData s0_ = super.insert(new AtomicsBuiltinsFactory.AtomicsNotifyNodeGen.NotifyData());
            s0_.toIndexNode_ = s0_.insertAccessor(JSToIndexNode.create());
            s0_.toInt32Node_ = s0_.insertAccessor(JSToInt32Node.create());
            s0_.notSharedArrayBuffer_ = BranchProfile.create();
            VarHandle.storeStoreFence();
            this.notify_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.doNotify(arguments0Value, arguments1Value, arguments2Value, s0_.toIndexNode_, s0_.toInt32Node_, s0_.notSharedArrayBuffer_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var8;
      }

      @Override
      public NodeCost getCost() {
         int state_0 = this.state_0_;
         return state_0 == 0 ? NodeCost.UNINITIALIZED : NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"doNotify", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            AtomicsBuiltinsFactory.AtomicsNotifyNodeGen.NotifyData s0_ = this.notify_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.toIndexNode_, s0_.toInt32Node_, s0_.notSharedArrayBuffer_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static AtomicsBuiltins.AtomicsNotifyNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new AtomicsBuiltinsFactory.AtomicsNotifyNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(AtomicsBuiltins.AtomicsNotifyNode.class)
      private static final class NotifyData extends Node {
         @Node.Child
         JSToIndexNode toIndexNode_;
         @Node.Child
         JSToInt32Node toInt32Node_;
         @CompilerDirectives.CompilationFinal
         BranchProfile notSharedArrayBuffer_;

         NotifyData() {
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

   @GeneratedBy(AtomicsBuiltins.AtomicsStoreNode.class)
   public static final class AtomicsStoreNodeGen extends AtomicsBuiltins.AtomicsStoreNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSToIndexNode toIndex;

      private AtomicsStoreNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         if ((state_0 & 64716) == 0 && state_0 != 0) {
            return this.execute_int_int0(state_0, frameValue);
         } else if ((state_0 & 61443) == 0 && state_0 != 0) {
            return this.execute_int1(state_0, frameValue);
         } else {
            return (state_0 & 61439) == 0 && state_0 != 0 ? this.execute_int2(state_0, frameValue) : this.execute_generic3(state_0, frameValue);
         }
      }

      private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments2Value = this.arguments2_.execute(frameValue);
            return this.executeAndSpecialize(arguments0Value_, var9.getResult(), arguments2Value);
         }

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var8) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, var8.getResult());
         }

         if ((state_0 & 819) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedInt8Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array0_ta__)
                  && sharedInt8Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doSharedInt8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt8Array0_ta__);
               }
            }

            if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedUint8Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array0_ta__)
                  && sharedUint8Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doSharedUint8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint8Array0_ta__);
               }
            }

            if ((state_0 & 16) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedInt16Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array0_ta__)
                  && sharedInt16Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doSharedInt16Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt16Array0_ta__);
               }
            }

            if ((state_0 & 32) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedUint16Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array0_ta__)
                  && sharedUint16Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doSharedUint16Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint16Array0_ta__);
               }
            }

            if ((state_0 & 256) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedInt32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array0_ta__)
                  && sharedInt32Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doSharedInt32Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt32Array0_ta__);
               }
            }

            if ((state_0 & 512) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedUint32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array0_ta__)
                  && sharedUint32Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                  return this.doSharedUint32Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint32Array0_ta__);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      private Object execute_int1(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var9) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments2Value = this.arguments2_.execute(frameValue);
            return this.executeAndSpecialize(arguments0Value_, var9.getResult(), arguments2Value);
         }

         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if ((state_0 & 4092) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if ((state_0 & 12) != 0) {
               if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedInt8Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array1_ta__)
                     && sharedInt8Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                     return this.doSharedInt8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt8Array1_ta__);
                  }
               }

               if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedUint8Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array1_ta__)
                     && sharedUint8Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                     return this.doSharedUint8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint8Array1_ta__);
                  }
               }
            }

            if ((state_0 & 48) != 0 && arguments2Value_ instanceof Integer) {
               int arguments2Value__ = (Integer)arguments2Value_;
               if ((state_0 & 16) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedInt16Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array0_ta__)
                     && sharedInt16Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                     return this.doSharedInt16Array(arguments0Value__, arguments1Value_, arguments2Value__, sharedInt16Array0_ta__);
                  }
               }

               if ((state_0 & 32) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedUint16Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array0_ta__)
                     && sharedUint16Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                     return this.doSharedUint16Array(arguments0Value__, arguments1Value_, arguments2Value__, sharedUint16Array0_ta__);
                  }
               }
            }

            if ((state_0 & 192) != 0) {
               if ((state_0 & 64) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedInt16Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array1_ta__)
                     && sharedInt16Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                     return this.doSharedInt16Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt16Array1_ta__);
                  }
               }

               if ((state_0 & 128) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedUint16Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array1_ta__)
                     && sharedUint16Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                     return this.doSharedUint16Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint16Array1_ta__);
                  }
               }
            }

            if ((state_0 & 768) != 0 && arguments2Value_ instanceof Integer) {
               int arguments2Value__x = (Integer)arguments2Value_;
               if ((state_0 & 256) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedInt32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array0_ta__)
                     && sharedInt32Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                     return this.doSharedInt32Array(arguments0Value__, arguments1Value_, arguments2Value__x, sharedInt32Array0_ta__);
                  }
               }

               if ((state_0 & 512) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedUint32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array0_ta__)
                     && sharedUint32Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                     return this.doSharedUint32Array(arguments0Value__, arguments1Value_, arguments2Value__x, sharedUint32Array0_ta__);
                  }
               }
            }

            if ((state_0 & 3072) != 0) {
               if ((state_0 & 1024) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedInt32Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array1_ta__)
                     && sharedInt32Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                     return this.doSharedInt32Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt32Array1_ta__);
                  }
               }

               if ((state_0 & 2048) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedUint32Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array1_ta__)
                     && sharedUint32Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                     return this.doSharedUint32Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint32Array1_ta__);
                  }
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      private Object execute_int2(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);

         int arguments2Value_;
         try {
            arguments2Value_ = this.arguments2_.executeInt(frameValue);
         } catch (UnexpectedResultException var8) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, var8.getResult());
         }

         assert (state_0 & 4096) != 0;

         if (arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
               TypedArray sharedInt32ArrayObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
               if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32ArrayObjIdx_ta__)) {
                  return this.doSharedInt32ArrayObjIdx(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt32ArrayObjIdx_ta__, this.toIndex);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      private Object execute_generic3(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         if ((state_0 & 32767) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
            JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
            if ((state_0 & 4095) != 0 && arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               if ((state_0 & 3) != 0 && arguments2Value_ instanceof Integer) {
                  int arguments2Value__ = (Integer)arguments2Value_;
                  if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedInt8Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array0_ta__)
                        && sharedInt8Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedInt8Array(arguments0Value__, arguments1Value__, arguments2Value__, sharedInt8Array0_ta__);
                     }
                  }

                  if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedUint8Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array0_ta__)
                        && sharedUint8Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedUint8Array(arguments0Value__, arguments1Value__, arguments2Value__, sharedUint8Array0_ta__);
                     }
                  }
               }

               if ((state_0 & 12) != 0) {
                  if ((state_0 & 4) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedInt8Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array1_ta__)
                        && sharedInt8Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedInt8Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedInt8Array1_ta__);
                     }
                  }

                  if ((state_0 & 8) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedUint8Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array1_ta__)
                        && sharedUint8Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedUint8Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedUint8Array1_ta__);
                     }
                  }
               }

               if ((state_0 & 48) != 0 && arguments2Value_ instanceof Integer) {
                  int arguments2Value__x = (Integer)arguments2Value_;
                  if ((state_0 & 16) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedInt16Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array0_ta__)
                        && sharedInt16Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedInt16Array(arguments0Value__, arguments1Value__, arguments2Value__x, sharedInt16Array0_ta__);
                     }
                  }

                  if ((state_0 & 32) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedUint16Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array0_ta__)
                        && sharedUint16Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedUint16Array(arguments0Value__, arguments1Value__, arguments2Value__x, sharedUint16Array0_ta__);
                     }
                  }
               }

               if ((state_0 & 192) != 0) {
                  if ((state_0 & 64) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedInt16Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array1_ta__)
                        && sharedInt16Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedInt16Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedInt16Array1_ta__);
                     }
                  }

                  if ((state_0 & 128) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedUint16Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedUint16Array1_ta__)
                        && sharedUint16Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedUint16Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedUint16Array1_ta__);
                     }
                  }
               }

               if ((state_0 & 768) != 0 && arguments2Value_ instanceof Integer) {
                  int arguments2Value__xx = (Integer)arguments2Value_;
                  if ((state_0 & 256) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedInt32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array0_ta__)
                        && sharedInt32Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedInt32Array(arguments0Value__, arguments1Value__, arguments2Value__xx, sharedInt32Array0_ta__);
                     }
                  }

                  if ((state_0 & 512) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedUint32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array0_ta__)
                        && sharedUint32Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedUint32Array(arguments0Value__, arguments1Value__, arguments2Value__xx, sharedUint32Array0_ta__);
                     }
                  }
               }

               if ((state_0 & 3072) != 0) {
                  if ((state_0 & 1024) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedInt32Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array1_ta__)
                        && sharedInt32Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedInt32Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedInt32Array1_ta__);
                     }
                  }

                  if ((state_0 & 2048) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedUint32Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array1_ta__)
                        && sharedUint32Array1_ta__.isInBoundsFast(arguments0Value__, arguments1Value__)) {
                        return this.doSharedUint32Array(arguments0Value__, arguments1Value__, arguments2Value_, sharedUint32Array1_ta__);
                     }
                  }
               }
            }

            if ((state_0 & 28672) != 0) {
               if ((state_0 & 4096) != 0 && arguments2Value_ instanceof Integer) {
                  int arguments2Value__xxx = (Integer)arguments2Value_;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedInt32ArrayObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32ArrayObjIdx_ta__)) {
                        return this.doSharedInt32ArrayObjIdx(
                           arguments0Value__, arguments1Value_, arguments2Value__xxx, sharedInt32ArrayObjIdx_ta__, this.toIndex
                        );
                     }
                  }
               }

               if ((state_0 & 24576) != 0) {
                  if ((state_0 & 8192) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedBigInt64Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(sharedBigInt64Array_ta__)) {
                        return this.doSharedBigInt64Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedBigInt64Array_ta__, this.toIndex);
                     }
                  }

                  if ((state_0 & 16384) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                     TypedArray sharedBigUint64Array_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(sharedBigUint64Array_ta__)) {
                        return this.doSharedBigUint64Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedBigUint64Array_ta__, this.toIndex);
                     }
                  }
               }
            }
         }

         if ((state_0 & 32768) != 0) {
            return this.doGeneric(arguments0Value_, arguments1Value_, arguments2Value_, this.toIndex);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
         }
      }

      @Override
      public int executeInt(VirtualFrame frameValue) throws UnexpectedResultException {
         int state_0 = this.state_0_;
         if ((state_0 & 64764) != 0) {
            return JSTypesGen.expectInteger(this.execute(frameValue));
         } else {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);

            int arguments1Value_;
            try {
               arguments1Value_ = this.arguments1_.executeInt(frameValue);
            } catch (UnexpectedResultException var9) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Object arguments2Value = this.arguments2_.execute(frameValue);
               return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, var9.getResult(), arguments2Value));
            }

            int arguments2Value_;
            try {
               arguments2Value_ = this.arguments2_.executeInt(frameValue);
            } catch (UnexpectedResultException var8) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, var8.getResult()));
            }

            if ((state_0 & 771) != 0 && arguments0Value_ instanceof JSTypedArrayObject) {
               JSTypedArrayObject arguments0Value__ = (JSTypedArrayObject)arguments0Value_;
               if ((state_0 & 1) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedInt8Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array0_ta__)
                     && sharedInt8Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                     return this.doSharedInt8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt8Array0_ta__);
                  }
               }

               if ((state_0 & 2) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedUint8Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedUint8Array0_ta__)
                     && sharedUint8Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                     return this.doSharedUint8Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint8Array0_ta__);
                  }
               }

               if ((state_0 & 256) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedInt32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array0_ta__)
                     && sharedInt32Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                     return this.doSharedInt32Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedInt32Array0_ta__);
                  }
               }

               if ((state_0 & 512) != 0 && AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value__)) {
                  TypedArray sharedUint32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value__);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedUint32Array0_ta__)
                     && sharedUint32Array0_ta__.isInBoundsFast(arguments0Value__, arguments1Value_)) {
                     return this.doSharedUint32Array(arguments0Value__, arguments1Value_, arguments2Value_, sharedUint32Array0_ta__);
                  }
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectInteger(this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_));
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         int state_0 = this.state_0_;

         try {
            if ((state_0 & 64764) == 0 && state_0 != 0) {
               this.executeInt(frameValue);
            } else {
               this.execute(frameValue);
            }
         } catch (UnexpectedResultException var4) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
         }
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSTypedArrayObject) {
               JSTypedArrayObject arguments0Value_ = (JSTypedArrayObject)arguments0Value;
               if (arguments1Value instanceof Integer) {
                  int arguments1Value_ = (Integer)arguments1Value;
                  if (arguments2Value instanceof Integer) {
                     int arguments2Value_ = (Integer)arguments2Value;
                     TypedArray sharedInt8Array0_ta__ = null;
                     if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                        sharedInt8Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array0_ta__)
                           && sharedInt8Array0_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                           int var30;
                           this.state_0_ = var30 = state_0 | 1;
                           lock.unlock();
                           hasLock = false;
                           return this.doSharedInt8Array(arguments0Value_, arguments1Value_, arguments2Value_, sharedInt8Array0_ta__);
                        }
                     }

                     sharedInt8Array0_ta__ = null;
                     if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                        sharedInt8Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedInt8Array0_ta__)
                           && sharedInt8Array0_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                           int var29;
                           this.state_0_ = var29 = state_0 | 2;
                           lock.unlock();
                           hasLock = false;
                           return this.doSharedUint8Array(arguments0Value_, arguments1Value_, arguments2Value_, sharedInt8Array0_ta__);
                        }
                     }
                  }

                  TypedArray sharedInt8Array1_ta__ = null;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                     sharedInt8Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt8Array(sharedInt8Array1_ta__)
                        && sharedInt8Array1_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        int var28;
                        this.state_0_ = var28 = state_0 | 4;
                        lock.unlock();
                        hasLock = false;
                        return this.doSharedInt8Array(arguments0Value_, arguments1Value_, arguments2Value, sharedInt8Array1_ta__);
                     }
                  }

                  sharedInt8Array1_ta__ = null;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                     sharedInt8Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint8Array(sharedInt8Array1_ta__)
                        && sharedInt8Array1_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        int var27;
                        this.state_0_ = var27 = state_0 | 8;
                        lock.unlock();
                        hasLock = false;
                        return this.doSharedUint8Array(arguments0Value_, arguments1Value_, arguments2Value, sharedInt8Array1_ta__);
                     }
                  }

                  if (arguments2Value instanceof Integer) {
                     int arguments2Value_x = (Integer)arguments2Value;
                     TypedArray sharedInt16Array0_ta__ = null;
                     if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                        sharedInt16Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt16Array0_ta__)
                           && sharedInt16Array0_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                           int var26;
                           this.state_0_ = var26 = state_0 | 16;
                           lock.unlock();
                           hasLock = false;
                           return this.doSharedInt16Array(arguments0Value_, arguments1Value_, arguments2Value_x, sharedInt16Array0_ta__);
                        }
                     }

                     sharedInt16Array0_ta__ = null;
                     if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                        sharedInt16Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedInt16Array0_ta__)
                           && sharedInt16Array0_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                           int var25;
                           this.state_0_ = var25 = state_0 | 32;
                           lock.unlock();
                           hasLock = false;
                           return this.doSharedUint16Array(arguments0Value_, arguments1Value_, arguments2Value_x, sharedInt16Array0_ta__);
                        }
                     }
                  }

                  sharedInt8Array1_ta__ = null;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                     sharedInt8Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt16Array(sharedInt8Array1_ta__)
                        && sharedInt8Array1_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        int var24;
                        this.state_0_ = var24 = state_0 | 64;
                        lock.unlock();
                        hasLock = false;
                        return this.doSharedInt16Array(arguments0Value_, arguments1Value_, arguments2Value, sharedInt8Array1_ta__);
                     }
                  }

                  sharedInt8Array1_ta__ = null;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                     sharedInt8Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint16Array(sharedInt8Array1_ta__)
                        && sharedInt8Array1_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        int var23;
                        this.state_0_ = var23 = state_0 | 128;
                        lock.unlock();
                        hasLock = false;
                        return this.doSharedUint16Array(arguments0Value_, arguments1Value_, arguments2Value, sharedInt8Array1_ta__);
                     }
                  }

                  if (arguments2Value instanceof Integer) {
                     int arguments2Value_xx = (Integer)arguments2Value;
                     TypedArray sharedInt32Array0_ta__ = null;
                     if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                        sharedInt32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32Array0_ta__)
                           && sharedInt32Array0_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                           int var22;
                           this.state_0_ = var22 = state_0 | 256;
                           lock.unlock();
                           hasLock = false;
                           return this.doSharedInt32Array(arguments0Value_, arguments1Value_, arguments2Value_xx, sharedInt32Array0_ta__);
                        }
                     }

                     sharedInt32Array0_ta__ = null;
                     if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                        sharedInt32Array0_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                        if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedInt32Array0_ta__)
                           && sharedInt32Array0_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                           int var21;
                           this.state_0_ = var21 = state_0 | 512;
                           lock.unlock();
                           hasLock = false;
                           return this.doSharedUint32Array(arguments0Value_, arguments1Value_, arguments2Value_xx, sharedInt32Array0_ta__);
                        }
                     }
                  }

                  sharedInt8Array1_ta__ = null;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                     sharedInt8Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt8Array1_ta__)
                        && sharedInt8Array1_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        int var20;
                        this.state_0_ = var20 = state_0 | 1024;
                        lock.unlock();
                        hasLock = false;
                        return this.doSharedInt32Array(arguments0Value_, arguments1Value_, arguments2Value, sharedInt8Array1_ta__);
                     }
                  }

                  sharedInt8Array1_ta__ = null;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                     sharedInt8Array1_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectUint32Array(sharedInt8Array1_ta__)
                        && sharedInt8Array1_ta__.isInBoundsFast(arguments0Value_, arguments1Value_)) {
                        int var19;
                        this.state_0_ = var19 = state_0 | 2048;
                        lock.unlock();
                        hasLock = false;
                        return this.doSharedUint32Array(arguments0Value_, arguments1Value_, arguments2Value, sharedInt8Array1_ta__);
                     }
                  }
               }

               TypedArray sharedInt32ArrayObjIdx_ta__ = null;
               if (arguments2Value instanceof Integer) {
                  int arguments2Value_xxx = (Integer)arguments2Value;
                  if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                     sharedInt32ArrayObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                     if (AtomicsBuiltins.AtomicsOperationNode.isDirectInt32Array(sharedInt32ArrayObjIdx_ta__)) {
                        this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                        int var18;
                        this.state_0_ = var18 = state_0 | 4096;
                        lock.unlock();
                        hasLock = false;
                        return this.doSharedInt32ArrayObjIdx(arguments0Value_, arguments1Value, arguments2Value_xxx, sharedInt32ArrayObjIdx_ta__, this.toIndex);
                     }
                  }
               }

               sharedInt32ArrayObjIdx_ta__ = null;
               if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                  sharedInt32ArrayObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigInt64Array(sharedInt32ArrayObjIdx_ta__)) {
                     this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                     int var17;
                     this.state_0_ = var17 = state_0 | 8192;
                     lock.unlock();
                     hasLock = false;
                     return this.doSharedBigInt64Array(arguments0Value_, arguments1Value, arguments2Value, sharedInt32ArrayObjIdx_ta__, this.toIndex);
                  }
               }

               sharedInt32ArrayObjIdx_ta__ = null;
               if (AtomicsBuiltins.AtomicsOperationNode.isSharedBufferView(arguments0Value_)) {
                  sharedInt32ArrayObjIdx_ta__ = JSArrayBufferView.typedArrayGetArrayType(arguments0Value_);
                  if (AtomicsBuiltins.AtomicsOperationNode.isDirectBigUint64Array(sharedInt32ArrayObjIdx_ta__)) {
                     this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
                     int var16;
                     this.state_0_ = var16 = state_0 | 16384;
                     lock.unlock();
                     hasLock = false;
                     return this.doSharedBigUint64Array(arguments0Value_, arguments1Value, arguments2Value, sharedInt32ArrayObjIdx_ta__, this.toIndex);
                  }
               }
            }

            this.toIndex = super.insert(this.toIndex == null ? JSToIndexNode.create() : this.toIndex);
            int var15;
            this.state_0_ = var15 = state_0 | 32768;
            lock.unlock();
            hasLock = false;
            return this.doGeneric(arguments0Value, arguments1Value, arguments2Value, this.toIndex);
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
         Object[] data = new Object[17];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"doSharedInt8Array", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doSharedUint8Array", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doSharedInt8Array", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"doSharedUint8Array", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"doSharedInt16Array", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"doSharedUint16Array", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"doSharedInt16Array", null, null};
         if ((state_0 & 64) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         s = new Object[]{"doSharedUint16Array", null, null};
         if ((state_0 & 128) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[8] = s;
         s = new Object[]{"doSharedInt32Array", null, null};
         if ((state_0 & 256) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[9] = s;
         s = new Object[]{"doSharedUint32Array", null, null};
         if ((state_0 & 512) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[10] = s;
         s = new Object[]{"doSharedInt32Array", null, null};
         if ((state_0 & 1024) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[11] = s;
         s = new Object[]{"doSharedUint32Array", null, null};
         if ((state_0 & 2048) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[12] = s;
         s = new Object[]{"doSharedInt32ArrayObjIdx", null, null};
         if ((state_0 & 4096) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toIndex));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[13] = s;
         s = new Object[]{"doSharedBigInt64Array", null, null};
         if ((state_0 & 8192) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toIndex));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[14] = s;
         s = new Object[]{"doSharedBigUint64Array", null, null};
         if ((state_0 & 16384) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toIndex));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[15] = s;
         s = new Object[]{"doGeneric", null, null};
         if ((state_0 & 32768) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.toIndex));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[16] = s;
         return Introspection.Provider.create(data);
      }

      public static AtomicsBuiltins.AtomicsStoreNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new AtomicsBuiltinsFactory.AtomicsStoreNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(AtomicsBuiltins.AtomicsWaitAsyncNode.class)
   public static final class AtomicsWaitAsyncNodeGen extends AtomicsBuiltins.AtomicsWaitAsyncNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @Node.Child
      private JavaScriptNode arguments3_;

      private AtomicsWaitAsyncNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
         this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         return this.doGeneric(frameValue, arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"doGeneric", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static AtomicsBuiltins.AtomicsWaitAsyncNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new AtomicsBuiltinsFactory.AtomicsWaitAsyncNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(AtomicsBuiltins.AtomicsWaitNode.class)
   public static final class AtomicsWaitNodeGen extends AtomicsBuiltins.AtomicsWaitNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @Node.Child
      private JavaScriptNode arguments3_;

      private AtomicsWaitNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
         this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
         this.arguments3_ = arguments != null && 3 < arguments.length ? arguments[3] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_, this.arguments3_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         Object arguments2Value_ = this.arguments2_.execute(frameValue);
         Object arguments3Value_ = this.arguments3_.execute(frameValue);
         return this.doGeneric(frameValue, arguments0Value_, arguments1Value_, arguments2Value_, arguments3Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"doGeneric", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static AtomicsBuiltins.AtomicsWaitNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new AtomicsBuiltinsFactory.AtomicsWaitNodeGen(context, builtin, arguments);
      }
   }
}
