package com.oracle.truffle.js.nodes.function;

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
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(EvalNode.class)
public final class EvalNodeGen extends EvalNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSFunctionCallNode evalOverridden_redirectCall_;

   private EvalNodeGen(JSContext context, JavaScriptNode function, JavaScriptNode[] args, JavaScriptNode thisObject, Object env, int blockScopeSlot) {
      super(context, function, args, thisObject, env, blockScopeSlot);
   }

   private EvalNodeGen(JSContext context, JavaScriptNode functionNode, AbstractFunctionArgumentsNode arguments, EvalNode.DirectEvalNode directEvalNode) {
      super(context, functionNode, arguments, directEvalNode);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object functionNodeValue_ = super.functionNode.execute(frameValue);
      if (state_0 != 0) {
         if ((state_0 & 1) != 0 && !this.isEvalOverridden(functionNodeValue_)) {
            return this.evalNotOverridden(frameValue, functionNodeValue_);
         }

         if ((state_0 & 2) != 0 && this.isEvalOverridden(functionNodeValue_)) {
            return this.evalOverridden(frameValue, functionNodeValue_, this.evalOverridden_redirectCall_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(frameValue, functionNodeValue_);
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.execute(frameValue);
   }

   private Object executeAndSpecialize(VirtualFrame frameValue, Object functionNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      Object var6;
      try {
         int state_0 = this.state_0_;
         if (this.isEvalOverridden(functionNodeValue)) {
            if (!this.isEvalOverridden(functionNodeValue)) {
               throw new UnsupportedSpecializationException(this, new Node[]{super.functionNode}, functionNodeValue);
            }

            this.evalOverridden_redirectCall_ = super.insert(JSFunctionCallNode.createCall());
            int var11;
            this.state_0_ = var11 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.evalOverridden(frameValue, functionNodeValue, this.evalOverridden_redirectCall_);
         }

         int var10;
         this.state_0_ = var10 = state_0 | 1;
         lock.unlock();
         hasLock = false;
         var6 = this.evalNotOverridden(frameValue, functionNodeValue);
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
      Object[] s = new Object[]{"evalNotOverridden", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"evalOverridden", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.evalOverridden_redirectCall_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      return Introspection.Provider.create(data);
   }

   public static EvalNode create(JSContext context, JavaScriptNode function, JavaScriptNode[] args, JavaScriptNode thisObject, Object env, int blockScopeSlot) {
      return new EvalNodeGen(context, function, args, thisObject, env, blockScopeSlot);
   }

   public static EvalNode create(
      JSContext context, JavaScriptNode functionNode, AbstractFunctionArgumentsNode arguments, EvalNode.DirectEvalNode directEvalNode
   ) {
      return new EvalNodeGen(context, functionNode, arguments, directEvalNode);
   }

   @GeneratedBy(EvalNode.DirectEvalNode.class)
   protected static final class DirectEvalNodeGen extends EvalNode.DirectEvalNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private EvalNodeGen.DirectEvalNodeGen.DirectEvalForeignObject0Data directEvalForeignObject0_cache;

      private DirectEvalNodeGen(JSContext context, JavaScriptNode thisNode, Object currEnv, int blockScopeSlot) {
         super(context, thisNode, currEnv, blockScopeSlot);
      }

      @ExplodeLoop
      @Override
      public Object executeWithSource(VirtualFrame frameValue, Object arg0Value) {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0 && arg0Value instanceof Integer) {
            int arg0Value_ = (Integer)arg0Value;
            return this.directEvalInt(arg0Value_);
         } else if ((state_0 & 2) != 0 && arg0Value instanceof SafeInteger) {
            SafeInteger arg0Value_ = (SafeInteger)arg0Value;
            return this.directEvalSafeInteger(arg0Value_);
         } else if ((state_0 & 4) != 0 && arg0Value instanceof Long) {
            long arg0Value_ = (Long)arg0Value;
            return this.directEvalLong(arg0Value_);
         } else if ((state_0 & 8) != 0 && JSTypesGen.isImplicitDouble((state_0 & 30720) >>> 11, arg0Value)) {
            double arg0Value_ = JSTypesGen.asImplicitDouble((state_0 & 30720) >>> 11, arg0Value);
            return this.directEvalDouble(arg0Value_);
         } else if ((state_0 & 16) != 0 && arg0Value instanceof Boolean) {
            boolean arg0Value_ = (Boolean)arg0Value;
            return this.directEvalBoolean(arg0Value_);
         } else if ((state_0 & 32) != 0 && arg0Value instanceof Symbol) {
            Symbol arg0Value_ = (Symbol)arg0Value;
            return this.directEvalSymbol(arg0Value_);
         } else if ((state_0 & 64) != 0 && arg0Value instanceof BigInt) {
            BigInt arg0Value_ = (BigInt)arg0Value;
            return this.directEvalBigInt(arg0Value_);
         } else if ((state_0 & 128) != 0 && arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            return this.directEvalJSType(arg0Value_);
         } else if ((state_0 & 256) != 0 && arg0Value instanceof TruffleString) {
            TruffleString arg0Value_ = (TruffleString)arg0Value;
            return this.directEvalCharSequence(frameValue, arg0Value_);
         } else {
            if ((state_0 & 1536) != 0) {
               if ((state_0 & 512) != 0) {
                  for (EvalNodeGen.DirectEvalNodeGen.DirectEvalForeignObject0Data s9_ = this.directEvalForeignObject0_cache; s9_ != null; s9_ = s9_.next_) {
                     if (s9_.interop_.accepts(arg0Value) && JSGuards.isForeignObject(arg0Value)) {
                        return this.directEvalForeignObject(frameValue, arg0Value, s9_.interop_);
                     }
                  }
               }

               if ((state_0 & 1024) != 0 && JSGuards.isForeignObject(arg0Value)) {
                  EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                  Node prev_ = encapsulating_.set(this);

                  Object var7;
                  try {
                     InteropLibrary directEvalForeignObject1_interop__ = EvalNodeGen.INTEROP_LIBRARY_.getUncached();
                     var7 = this.directEvalForeignObject(frameValue, arg0Value, directEvalForeignObject1_interop__);
                  } finally {
                     encapsulating_.set(prev_);
                  }

                  return var7;
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue, arg0Value);
         }
      }

      private Object executeAndSpecialize(VirtualFrame frameValue, Object arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg0Value instanceof Integer) {
               int arg0Value_ = (Integer)arg0Value;
               int var30;
               this.state_0_ = var30 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.directEvalInt(arg0Value_);
            } else if (arg0Value instanceof SafeInteger) {
               SafeInteger arg0Value_ = (SafeInteger)arg0Value;
               int var29;
               this.state_0_ = var29 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.directEvalSafeInteger(arg0Value_);
            } else if (arg0Value instanceof Long) {
               long arg0Value_ = (Long)arg0Value;
               int var28;
               this.state_0_ = var28 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.directEvalLong(arg0Value_);
            } else {
               int doubleCast0;
               if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arg0Value)) != 0) {
                  double arg0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arg0Value);
                  state_0 |= doubleCast0 << 11;
                  int var27;
                  this.state_0_ = var27 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.directEvalDouble(arg0Value_);
               } else if (arg0Value instanceof Boolean) {
                  boolean arg0Value_ = (Boolean)arg0Value;
                  int var25;
                  this.state_0_ = var25 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  return this.directEvalBoolean(arg0Value_);
               } else if (arg0Value instanceof Symbol) {
                  Symbol arg0Value_ = (Symbol)arg0Value;
                  int var24;
                  this.state_0_ = var24 = state_0 | 32;
                  lock.unlock();
                  hasLock = false;
                  return this.directEvalSymbol(arg0Value_);
               } else if (arg0Value instanceof BigInt) {
                  BigInt arg0Value_ = (BigInt)arg0Value;
                  int var23;
                  this.state_0_ = var23 = state_0 | 64;
                  lock.unlock();
                  hasLock = false;
                  return this.directEvalBigInt(arg0Value_);
               } else if (arg0Value instanceof JSDynamicObject) {
                  JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
                  int var22;
                  this.state_0_ = var22 = state_0 | 128;
                  lock.unlock();
                  hasLock = false;
                  return this.directEvalJSType(arg0Value_);
               } else if (arg0Value instanceof TruffleString) {
                  TruffleString arg0Value_ = (TruffleString)arg0Value;
                  int var21;
                  this.state_0_ = var21 = state_0 | 256;
                  lock.unlock();
                  hasLock = false;
                  return this.directEvalCharSequence(frameValue, arg0Value_);
               } else {
                  if (exclude == 0) {
                     doubleCast0 = 0;
                     EvalNodeGen.DirectEvalNodeGen.DirectEvalForeignObject0Data s9_ = this.directEvalForeignObject0_cache;
                     if ((state_0 & 512) != 0) {
                        while (s9_ != null && (!s9_.interop_.accepts(arg0Value) || !JSGuards.isForeignObject(arg0Value))) {
                           s9_ = s9_.next_;
                           doubleCast0++;
                        }
                     }

                     if (s9_ == null && JSGuards.isForeignObject(arg0Value) && doubleCast0 < 3) {
                        s9_ = super.insert(new EvalNodeGen.DirectEvalNodeGen.DirectEvalForeignObject0Data(this.directEvalForeignObject0_cache));
                        s9_.interop_ = s9_.insertAccessor(EvalNodeGen.INTEROP_LIBRARY_.create(arg0Value));
                        VarHandle.storeStoreFence();
                        this.directEvalForeignObject0_cache = s9_;
                        this.state_0_ = state_0 |= 512;
                     }

                     if (s9_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.directEvalForeignObject(frameValue, arg0Value, s9_.interop_);
                     }
                  }

                  InteropLibrary directEvalForeignObject1_interop__ = null;
                  EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                  Node prev_ = encapsulating_.set(this);

                  try {
                     if (JSGuards.isForeignObject(arg0Value)) {
                        InteropLibrary var34 = EvalNodeGen.INTEROP_LIBRARY_.getUncached();
                        int var31;
                        this.exclude_ = var31 = exclude | 1;
                        this.directEvalForeignObject0_cache = null;
                        state_0 &= -513;
                        int var20;
                        this.state_0_ = var20 = state_0 | 1024;
                        lock.unlock();
                        hasLock = false;
                        return this.directEvalForeignObject(frameValue, arg0Value, var34);
                     }
                  } finally {
                     encapsulating_.set(prev_);
                  }

                  throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
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
         if ((state_0 & 2047) == 0) {
            return NodeCost.UNINITIALIZED;
         } else {
            if ((state_0 & 2047 & (state_0 & 2047) - 1) == 0) {
               EvalNodeGen.DirectEvalNodeGen.DirectEvalForeignObject0Data s9_ = this.directEvalForeignObject0_cache;
               if (s9_ == null || s9_.next_ == null) {
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
         Object[] s = new Object[]{"directEvalInt", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"directEvalSafeInteger", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"directEvalLong", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"directEvalDouble", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"directEvalBoolean", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"directEvalSymbol", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"directEvalBigInt", null, null};
         if ((state_0 & 64) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         s = new Object[]{"directEvalJSType", null, null};
         if ((state_0 & 128) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[8] = s;
         s = new Object[]{"directEvalCharSequence", null, null};
         if ((state_0 & 256) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[9] = s;
         s = new Object[]{"directEvalForeignObject", null, null};
         if ((state_0 & 512) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (EvalNodeGen.DirectEvalNodeGen.DirectEvalForeignObject0Data s9_ = this.directEvalForeignObject0_cache; s9_ != null; s9_ = s9_.next_) {
               cached.add(Arrays.asList(s9_.interop_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[10] = s;
         s = new Object[]{"directEvalForeignObject", null, null};
         if ((state_0 & 1024) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList());
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[11] = s;
         return Introspection.Provider.create(data);
      }

      public static EvalNode.DirectEvalNode create(JSContext context, JavaScriptNode thisNode, Object currEnv, int blockScopeSlot) {
         return new EvalNodeGen.DirectEvalNodeGen(context, thisNode, currEnv, blockScopeSlot);
      }

      @GeneratedBy(EvalNode.DirectEvalNode.class)
      private static final class DirectEvalForeignObject0Data extends Node {
         @Node.Child
         EvalNodeGen.DirectEvalNodeGen.DirectEvalForeignObject0Data next_;
         @Node.Child
         InteropLibrary interop_;

         DirectEvalForeignObject0Data(EvalNodeGen.DirectEvalNodeGen.DirectEvalForeignObject0Data next_) {
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
}
