package com.oracle.truffle.js.builtins.math;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.SlowPathException;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(PowNode.class)
public final class PowNodeGen extends PowNode implements Introspection.Provider {
   @Node.Child
   private JavaScriptNode arguments0_;
   @Node.Child
   private JavaScriptNode arguments1_;
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile pow3_branch1_;
   @CompilerDirectives.CompilationFinal
   private ConditionProfile pow3_branch2_;
   @Node.Child
   private PowNode pow1_powNode_;

   private PowNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
      super(context, builtin);
      this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
   }

   @Override
   public JavaScriptNode[] getArguments() {
      return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      return (state_0 & 8) == 0 && (state_0 & 15) != 0 ? this.execute_double_double0(state_0, frameValue) : this.execute_generic1(state_0, frameValue);
   }

   private Object execute_double_double0(int state_0, VirtualFrame frameValue) {
      long arguments0Value_long = 0L;
      int arguments0Value_int = 0;

      double arguments0Value_;
      try {
         if ((state_0 & 224) == 0 && (state_0 & 15) != 0) {
            arguments0Value_ = this.arguments0_.executeDouble(frameValue);
         } else if ((state_0 & 208) == 0 && (state_0 & 15) != 0) {
            arguments0Value_int = this.arguments0_.executeInt(frameValue);
            arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
         } else if ((state_0 & 112) == 0 && (state_0 & 15) != 0) {
            arguments0Value_long = this.arguments0_.executeLong(frameValue);
            arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
         } else {
            Object arguments0Value__ = this.arguments0_.execute(frameValue);
            arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 240) >>> 4, arguments0Value__);
         }
      } catch (UnexpectedResultException var34) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         Object arguments1Value = this.arguments1_.execute(frameValue);
         return this.executeAndSpecialize(var34.getResult(), arguments1Value);
      }

      long arguments1Value_long = 0L;
      int arguments1Value_int = 0;

      double arguments1Value_;
      try {
         if ((state_0 & 3584) == 0 && (state_0 & 15) != 0) {
            arguments1Value_ = this.arguments1_.executeDouble(frameValue);
         } else if ((state_0 & 3328) == 0 && (state_0 & 15) != 0) {
            arguments1Value_int = this.arguments1_.executeInt(frameValue);
            arguments1Value_ = JSTypes.intToDouble(arguments1Value_int);
         } else if ((state_0 & 1792) == 0 && (state_0 & 15) != 0) {
            arguments1Value_long = this.arguments1_.executeLong(frameValue);
            arguments1Value_ = JSTypes.longToDouble(arguments1Value_long);
         } else {
            Object arguments1Value__ = this.arguments1_.execute(frameValue);
            arguments1Value_ = JSTypesGen.expectImplicitDouble((state_0 & 3840) >>> 8, arguments1Value__);
         }
      } catch (UnexpectedResultException var33) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_0 & 208) == 0 && (state_0 & 15) != 0
               ? arguments0Value_int
               : ((state_0 & 112) == 0 && (state_0 & 15) != 0 ? arguments0Value_long : arguments0Value_),
            var33.getResult()
         );
      }

      if ((state_0 & 1) != 0) {
         try {
            return this.pow(arguments0Value_, arguments1Value_);
         } catch (SlowPathException var31) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Lock lock = this.getLock();
            lock.lock();

            try {
               this.exclude_ |= 1;
               this.state_0_ &= -2;
            } finally {
               lock.unlock();
            }

            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      } else if ((state_0 & 2) != 0) {
         try {
            return this.pow2(arguments0Value_, arguments1Value_);
         } catch (SlowPathException var32) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Lock lock = this.getLock();
            lock.lock();

            try {
               this.exclude_ |= 2;
               this.state_0_ &= -3;
            } finally {
               lock.unlock();
            }

            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      } else if ((state_0 & 4) != 0) {
         return this.pow3(arguments0Value_, arguments1Value_, this.pow3_branch1_, this.pow3_branch2_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(
            (state_0 & 208) == 0 && (state_0 & 15) != 0
               ? arguments0Value_int
               : ((state_0 & 112) == 0 && (state_0 & 15) != 0 ? arguments0Value_long : arguments0Value_),
            (state_0 & 3328) == 0 && (state_0 & 15) != 0
               ? arguments1Value_int
               : ((state_0 & 1792) == 0 && (state_0 & 15) != 0 ? arguments1Value_long : arguments1Value_)
         );
      }
   }

   private Object execute_generic1(int state_0, VirtualFrame frameValue) {
      Object arguments0Value_ = this.arguments0_.execute(frameValue);
      Object arguments1Value_ = this.arguments1_.execute(frameValue);
      if ((state_0 & 7) != 0 && JSTypesGen.isImplicitDouble((state_0 & 240) >>> 4, arguments0Value_)) {
         double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 240) >>> 4, arguments0Value_);
         if (JSTypesGen.isImplicitDouble((state_0 & 3840) >>> 8, arguments1Value_)) {
            double arguments1Value__ = JSTypesGen.asImplicitDouble((state_0 & 3840) >>> 8, arguments1Value_);
            if ((state_0 & 1) != 0) {
               try {
                  return this.pow(arguments0Value__, arguments1Value__);
               } catch (SlowPathException var23) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  Lock lock = this.getLock();
                  lock.lock();

                  try {
                     this.exclude_ |= 1;
                     this.state_0_ &= -2;
                  } finally {
                     lock.unlock();
                  }

                  return this.executeAndSpecialize(arguments0Value__, arguments1Value__);
               }
            }

            if ((state_0 & 2) != 0) {
               try {
                  return this.pow2(arguments0Value__, arguments1Value__);
               } catch (SlowPathException var24) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  Lock lock = this.getLock();
                  lock.lock();

                  try {
                     this.exclude_ |= 2;
                     this.state_0_ &= -3;
                  } finally {
                     lock.unlock();
                  }

                  return this.executeAndSpecialize(arguments0Value__, arguments1Value__);
               }
            }

            if ((state_0 & 4) != 0) {
               return this.pow3(arguments0Value__, arguments1Value__, this.pow3_branch1_, this.pow3_branch2_);
            }
         }
      }

      if ((state_0 & 8) != 0) {
         return this.pow(arguments0Value_, arguments1Value_, this.pow1_powNode_);
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }
   }

   @Override
   public double executeDouble(VirtualFrame frameValue) throws UnexpectedResultException {
      int state_0 = this.state_0_;
      if ((state_0 & 8) != 0) {
         return JSTypesGen.expectDouble(this.execute(frameValue));
      } else {
         long arguments0Value_long = 0L;
         int arguments0Value_int = 0;

         double arguments0Value_;
         try {
            if ((state_0 & 224) == 0 && (state_0 & 15) != 0) {
               arguments0Value_ = this.arguments0_.executeDouble(frameValue);
            } else if ((state_0 & 208) == 0 && (state_0 & 15) != 0) {
               arguments0Value_int = this.arguments0_.executeInt(frameValue);
               arguments0Value_ = JSTypes.intToDouble(arguments0Value_int);
            } else if ((state_0 & 112) == 0 && (state_0 & 15) != 0) {
               arguments0Value_long = this.arguments0_.executeLong(frameValue);
               arguments0Value_ = JSTypes.longToDouble(arguments0Value_long);
            } else {
               Object arguments0Value__ = this.arguments0_.execute(frameValue);
               arguments0Value_ = JSTypesGen.expectImplicitDouble((state_0 & 240) >>> 4, arguments0Value__);
            }
         } catch (UnexpectedResultException var34) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments1Value = this.arguments1_.execute(frameValue);
            return JSTypesGen.expectDouble(this.executeAndSpecialize(var34.getResult(), arguments1Value));
         }

         long arguments1Value_long = 0L;
         int arguments1Value_int = 0;

         double arguments1Value_;
         try {
            if ((state_0 & 3584) == 0 && (state_0 & 15) != 0) {
               arguments1Value_ = this.arguments1_.executeDouble(frameValue);
            } else if ((state_0 & 3328) == 0 && (state_0 & 15) != 0) {
               arguments1Value_int = this.arguments1_.executeInt(frameValue);
               arguments1Value_ = JSTypes.intToDouble(arguments1Value_int);
            } else if ((state_0 & 1792) == 0 && (state_0 & 15) != 0) {
               arguments1Value_long = this.arguments1_.executeLong(frameValue);
               arguments1Value_ = JSTypes.longToDouble(arguments1Value_long);
            } else {
               Object arguments1Value__ = this.arguments1_.execute(frameValue);
               arguments1Value_ = JSTypesGen.expectImplicitDouble((state_0 & 3840) >>> 8, arguments1Value__);
            }
         } catch (UnexpectedResultException var33) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(
               this.executeAndSpecialize(
                  (state_0 & 208) == 0 && (state_0 & 15) != 0
                     ? arguments0Value_int
                     : ((state_0 & 112) == 0 && (state_0 & 15) != 0 ? arguments0Value_long : arguments0Value_),
                  var33.getResult()
               )
            );
         }

         if ((state_0 & 1) != 0) {
            try {
               return this.pow(arguments0Value_, arguments1Value_);
            } catch (SlowPathException var31) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 1;
                  this.state_0_ &= -2;
               } finally {
                  lock.unlock();
               }

               return JSTypesGen.expectDouble(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
            }
         } else if ((state_0 & 2) != 0) {
            try {
               return this.pow2(arguments0Value_, arguments1Value_);
            } catch (SlowPathException var32) {
               CompilerDirectives.transferToInterpreterAndInvalidate();
               Lock lock = this.getLock();
               lock.lock();

               try {
                  this.exclude_ |= 2;
                  this.state_0_ &= -3;
               } finally {
                  lock.unlock();
               }

               return JSTypesGen.expectDouble(this.executeAndSpecialize(arguments0Value_, arguments1Value_));
            }
         } else if ((state_0 & 4) != 0) {
            return this.pow3(arguments0Value_, arguments1Value_, this.pow3_branch1_, this.pow3_branch2_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return JSTypesGen.expectDouble(
               this.executeAndSpecialize(
                  (state_0 & 208) == 0 && (state_0 & 15) != 0
                     ? arguments0Value_int
                     : ((state_0 & 112) == 0 && (state_0 & 15) != 0 ? arguments0Value_long : arguments0Value_),
                  (state_0 & 3328) == 0 && (state_0 & 15) != 0
                     ? arguments1Value_int
                     : ((state_0 & 1792) == 0 && (state_0 & 15) != 0 ? arguments1Value_long : arguments1Value_)
               )
            );
         }
      }
   }

   @Override
   public double execute(Object arguments0Value, Object arguments1Value) {
      int state_0 = this.state_0_;
      if ((state_0 & 7) != 0 && JSTypesGen.isImplicitDouble((state_0 & 240) >>> 4, arguments0Value)) {
         double arguments0Value_ = JSTypesGen.asImplicitDouble((state_0 & 240) >>> 4, arguments0Value);
         if (JSTypesGen.isImplicitDouble((state_0 & 3840) >>> 8, arguments1Value)) {
            double arguments1Value_ = JSTypesGen.asImplicitDouble((state_0 & 3840) >>> 8, arguments1Value);
            if ((state_0 & 1) != 0) {
               try {
                  return this.pow(arguments0Value_, arguments1Value_);
               } catch (SlowPathException var22) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  Lock lock = this.getLock();
                  lock.lock();

                  try {
                     this.exclude_ |= 1;
                     this.state_0_ &= -2;
                  } finally {
                     lock.unlock();
                  }

                  return (Double)this.executeAndSpecialize(arguments0Value_, arguments1Value_);
               }
            }

            if ((state_0 & 2) != 0) {
               try {
                  return this.pow2(arguments0Value_, arguments1Value_);
               } catch (SlowPathException var23) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  Lock lock = this.getLock();
                  lock.lock();

                  try {
                     this.exclude_ |= 2;
                     this.state_0_ &= -3;
                  } finally {
                     lock.unlock();
                  }

                  return (Double)this.executeAndSpecialize(arguments0Value_, arguments1Value_);
               }
            }

            if ((state_0 & 4) != 0) {
               return this.pow3(arguments0Value_, arguments1Value_, this.pow3_branch1_, this.pow3_branch2_);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return (Double)this.executeAndSpecialize(arguments0Value, arguments1Value);
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      int state_0 = this.state_0_;

      try {
         if ((state_0 & 8) == 0 && (state_0 & 15) != 0) {
            this.executeDouble(frameValue);
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
         int exclude = this.exclude_;
         int doubleCast0;
         if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value)) != 0) {
            double arguments0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
            int doubleCast1;
            if ((doubleCast1 = JSTypesGen.specializeImplicitDouble(arguments1Value)) != 0) {
               double arguments1Value_ = JSTypesGen.asImplicitDouble(doubleCast1, arguments1Value);
               if ((exclude & 1) != 0) {
                  if ((exclude & 2) != 0) {
                     this.pow3_branch1_ = ConditionProfile.createBinaryProfile();
                     this.pow3_branch2_ = ConditionProfile.createBinaryProfile();
                     state_0 |= doubleCast0 << 4;
                     state_0 |= doubleCast1 << 8;
                     int var46;
                     this.state_0_ = var46 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.pow3(arguments0Value_, arguments1Value_, this.pow3_branch1_, this.pow3_branch2_);
                  }

                  state_0 |= doubleCast0 << 4;
                  state_0 |= doubleCast1 << 8;
                  int var43;
                  this.state_0_ = var43 = state_0 | 2;

                  try {
                     lock.unlock();
                     hasLock = false;
                     return this.pow2(arguments0Value_, arguments1Value_);
                  } catch (SlowPathException var34) {
                     CompilerDirectives.transferToInterpreterAndInvalidate();
                     lock.lock();

                     try {
                        this.exclude_ |= 2;
                        this.state_0_ &= -3;
                     } finally {
                        lock.unlock();
                     }

                     return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
                  }
               }

               state_0 |= doubleCast0 << 4;
               state_0 |= doubleCast1 << 8;
               int var40;
               this.state_0_ = var40 = state_0 | 1;

               try {
                  lock.unlock();
                  hasLock = false;
                  return this.pow(arguments0Value_, arguments1Value_);
               } catch (SlowPathException var35) {
                  CompilerDirectives.transferToInterpreterAndInvalidate();
                  lock.lock();

                  try {
                     this.exclude_ |= 1;
                     this.state_0_ &= -2;
                  } finally {
                     lock.unlock();
                  }

                  return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
               }
            }
         }

         this.pow1_powNode_ = super.insert(this.create(this.getContext()));
         int var37;
         this.state_0_ = var37 = state_0 | 8;
         lock.unlock();
         hasLock = false;
         return this.pow(arguments0Value, arguments1Value, this.pow1_powNode_);
      } finally {
         if (hasLock) {
            lock.unlock();
         }
      }
   }

   @Override
   public NodeCost getCost() {
      int state_0 = this.state_0_;
      if ((state_0 & 15) == 0) {
         return NodeCost.UNINITIALIZED;
      } else {
         return (state_0 & 15 & (state_0 & 15) - 1) == 0 ? NodeCost.MONOMORPHIC : NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[5];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"pow", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 1) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"pow2", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
      } else if ((exclude & 2) != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"pow3", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.pow3_branch1_, this.pow3_branch2_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"pow", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.pow1_powNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      return Introspection.Provider.create(data);
   }

   public static PowNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
      return new PowNodeGen(context, builtin, arguments);
   }
}
