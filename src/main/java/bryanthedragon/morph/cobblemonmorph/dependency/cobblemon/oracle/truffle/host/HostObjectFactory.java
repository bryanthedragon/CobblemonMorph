package com.oracle.truffle.host;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.BranchProfile;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(HostObject.class)
final class HostObjectFactory {
   @GeneratedBy(HostObject.ArrayGet.class)
   static final class ArrayGetNodeGen extends HostObject.ArrayGet {
      private static final HostObjectFactory.ArrayGetNodeGen.Uncached UNCACHED = new HostObjectFactory.ArrayGetNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ArrayGetNodeGen() {
      }

      @Override
      protected Object execute(Object arg0Value, int arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value instanceof boolean[]) {
               boolean[] arg0Value_ = (boolean[])arg0Value;
               return HostObject.ArrayGet.doBoolean(arg0Value_, arg1Value);
            }

            if ((state_0 & 2) != 0 && arg0Value instanceof byte[]) {
               byte[] arg0Value_ = (byte[])arg0Value;
               return HostObject.ArrayGet.doByte(arg0Value_, arg1Value);
            }

            if ((state_0 & 4) != 0 && arg0Value instanceof short[]) {
               short[] arg0Value_ = (short[])arg0Value;
               return HostObject.ArrayGet.doShort(arg0Value_, arg1Value);
            }

            if ((state_0 & 8) != 0 && arg0Value instanceof char[]) {
               char[] arg0Value_ = (char[])arg0Value;
               return HostObject.ArrayGet.doChar(arg0Value_, arg1Value);
            }

            if ((state_0 & 16) != 0 && arg0Value instanceof int[]) {
               int[] arg0Value_ = (int[])arg0Value;
               return HostObject.ArrayGet.doInt(arg0Value_, arg1Value);
            }

            if ((state_0 & 32) != 0 && arg0Value instanceof long[]) {
               long[] arg0Value_ = (long[])arg0Value;
               return HostObject.ArrayGet.doLong(arg0Value_, arg1Value);
            }

            if ((state_0 & 64) != 0 && arg0Value instanceof float[]) {
               float[] arg0Value_ = (float[])arg0Value;
               return HostObject.ArrayGet.doFloat(arg0Value_, arg1Value);
            }

            if ((state_0 & 128) != 0 && arg0Value instanceof double[]) {
               double[] arg0Value_ = (double[])arg0Value;
               return HostObject.ArrayGet.doDouble(arg0Value_, arg1Value);
            }

            if ((state_0 & 256) != 0 && arg0Value instanceof Object[]) {
               Object[] arg0Value_ = (Object[])arg0Value;
               return HostObject.ArrayGet.doObject(arg0Value_, arg1Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private Object executeAndSpecialize(Object arg0Value, int arg1Value) {
         int state_0 = this.state_0_;
         if (arg0Value instanceof boolean[]) {
            boolean[] arg0Value_ = (boolean[])arg0Value;
            int var13;
            this.state_0_ = var13 = state_0 | 1;
            return HostObject.ArrayGet.doBoolean(arg0Value_, arg1Value);
         } else if (arg0Value instanceof byte[]) {
            byte[] arg0Value_ = (byte[])arg0Value;
            int var12;
            this.state_0_ = var12 = state_0 | 2;
            return HostObject.ArrayGet.doByte(arg0Value_, arg1Value);
         } else if (arg0Value instanceof short[]) {
            short[] arg0Value_ = (short[])arg0Value;
            int var11;
            this.state_0_ = var11 = state_0 | 4;
            return HostObject.ArrayGet.doShort(arg0Value_, arg1Value);
         } else if (arg0Value instanceof char[]) {
            char[] arg0Value_ = (char[])arg0Value;
            int var10;
            this.state_0_ = var10 = state_0 | 8;
            return HostObject.ArrayGet.doChar(arg0Value_, arg1Value);
         } else if (arg0Value instanceof int[]) {
            int[] arg0Value_ = (int[])arg0Value;
            int var9;
            this.state_0_ = var9 = state_0 | 16;
            return HostObject.ArrayGet.doInt(arg0Value_, arg1Value);
         } else if (arg0Value instanceof long[]) {
            long[] arg0Value_ = (long[])arg0Value;
            int var8;
            this.state_0_ = var8 = state_0 | 32;
            return HostObject.ArrayGet.doLong(arg0Value_, arg1Value);
         } else if (arg0Value instanceof float[]) {
            float[] arg0Value_ = (float[])arg0Value;
            int var7;
            this.state_0_ = var7 = state_0 | 64;
            return HostObject.ArrayGet.doFloat(arg0Value_, arg1Value);
         } else if (arg0Value instanceof double[]) {
            double[] arg0Value_ = (double[])arg0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 128;
            return HostObject.ArrayGet.doDouble(arg0Value_, arg1Value);
         } else if (arg0Value instanceof Object[]) {
            Object[] arg0Value_ = (Object[])arg0Value;
            int var5;
            this.state_0_ = var5 = state_0 | 256;
            return HostObject.ArrayGet.doObject(arg0Value_, arg1Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
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

      public static HostObject.ArrayGet create() {
         return new HostObjectFactory.ArrayGetNodeGen();
      }

      public static HostObject.ArrayGet getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostObject.ArrayGet.class)
      @DenyReplace
      private static final class Uncached extends HostObject.ArrayGet {
         @CompilerDirectives.TruffleBoundary
         @Override
         protected Object execute(Object arg0Value, int arg1Value) {
            if (arg0Value instanceof boolean[]) {
               boolean[] arg0Value_ = (boolean[])arg0Value;
               return HostObject.ArrayGet.doBoolean(arg0Value_, arg1Value);
            } else if (arg0Value instanceof byte[]) {
               byte[] arg0Value_ = (byte[])arg0Value;
               return HostObject.ArrayGet.doByte(arg0Value_, arg1Value);
            } else if (arg0Value instanceof short[]) {
               short[] arg0Value_ = (short[])arg0Value;
               return HostObject.ArrayGet.doShort(arg0Value_, arg1Value);
            } else if (arg0Value instanceof char[]) {
               char[] arg0Value_ = (char[])arg0Value;
               return HostObject.ArrayGet.doChar(arg0Value_, arg1Value);
            } else if (arg0Value instanceof int[]) {
               int[] arg0Value_ = (int[])arg0Value;
               return HostObject.ArrayGet.doInt(arg0Value_, arg1Value);
            } else if (arg0Value instanceof long[]) {
               long[] arg0Value_ = (long[])arg0Value;
               return HostObject.ArrayGet.doLong(arg0Value_, arg1Value);
            } else if (arg0Value instanceof float[]) {
               float[] arg0Value_ = (float[])arg0Value;
               return HostObject.ArrayGet.doFloat(arg0Value_, arg1Value);
            } else if (arg0Value instanceof double[]) {
               double[] arg0Value_ = (double[])arg0Value;
               return HostObject.ArrayGet.doDouble(arg0Value_, arg1Value);
            } else if (arg0Value instanceof Object[]) {
               Object[] arg0Value_ = (Object[])arg0Value;
               return HostObject.ArrayGet.doObject(arg0Value_, arg1Value);
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }

   @GeneratedBy(HostObject.ArraySet.class)
   static final class ArraySetNodeGen extends HostObject.ArraySet {
      private static final HostObjectFactory.ArraySetNodeGen.Uncached UNCACHED = new HostObjectFactory.ArraySetNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ArraySetNodeGen() {
      }

      @Override
      protected void execute(Object arg0Value, int arg1Value, Object arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value instanceof boolean[]) {
               boolean[] arg0Value_ = (boolean[])arg0Value;
               if (arg2Value instanceof Boolean) {
                  boolean arg2Value_ = (Boolean)arg2Value;
                  HostObject.ArraySet.doBoolean(arg0Value_, arg1Value, arg2Value_);
                  return;
               }
            }

            if ((state_0 & 2) != 0 && arg0Value instanceof byte[]) {
               byte[] arg0Value_ = (byte[])arg0Value;
               if (arg2Value instanceof Byte) {
                  byte arg2Value_ = (Byte)arg2Value;
                  HostObject.ArraySet.doByte(arg0Value_, arg1Value, arg2Value_);
                  return;
               }
            }

            if ((state_0 & 4) != 0 && arg0Value instanceof short[]) {
               short[] arg0Value_ = (short[])arg0Value;
               if (arg2Value instanceof Short) {
                  short arg2Value_ = (Short)arg2Value;
                  HostObject.ArraySet.doShort(arg0Value_, arg1Value, arg2Value_);
                  return;
               }
            }

            if ((state_0 & 8) != 0 && arg0Value instanceof char[]) {
               char[] arg0Value_ = (char[])arg0Value;
               if (arg2Value instanceof Character) {
                  char arg2Value_ = (Character)arg2Value;
                  HostObject.ArraySet.doChar(arg0Value_, arg1Value, arg2Value_);
                  return;
               }
            }

            if ((state_0 & 16) != 0 && arg0Value instanceof int[]) {
               int[] arg0Value_ = (int[])arg0Value;
               if (arg2Value instanceof Integer) {
                  int arg2Value_ = (Integer)arg2Value;
                  HostObject.ArraySet.doInt(arg0Value_, arg1Value, arg2Value_);
                  return;
               }
            }

            if ((state_0 & 32) != 0 && arg0Value instanceof long[]) {
               long[] arg0Value_ = (long[])arg0Value;
               if (arg2Value instanceof Long) {
                  long arg2Value_ = (Long)arg2Value;
                  HostObject.ArraySet.doLong(arg0Value_, arg1Value, arg2Value_);
                  return;
               }
            }

            if ((state_0 & 64) != 0 && arg0Value instanceof float[]) {
               float[] arg0Value_ = (float[])arg0Value;
               if (arg2Value instanceof Float) {
                  float arg2Value_ = (Float)arg2Value;
                  HostObject.ArraySet.doFloat(arg0Value_, arg1Value, arg2Value_);
                  return;
               }
            }

            if ((state_0 & 128) != 0 && arg0Value instanceof double[]) {
               double[] arg0Value_ = (double[])arg0Value;
               if (arg2Value instanceof Double) {
                  double arg2Value_ = (Double)arg2Value;
                  HostObject.ArraySet.doDouble(arg0Value_, arg1Value, arg2Value_);
                  return;
               }
            }

            if ((state_0 & 256) != 0 && arg0Value instanceof Object[]) {
               Object[] arg0Value_ = (Object[])arg0Value;
               HostObject.ArraySet.doObject(arg0Value_, arg1Value, arg2Value);
               return;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private void executeAndSpecialize(Object arg0Value, int arg1Value, Object arg2Value) {
         int state_0 = this.state_0_;
         if (arg0Value instanceof boolean[]) {
            boolean[] arg0Value_ = (boolean[])arg0Value;
            if (arg2Value instanceof Boolean) {
               boolean arg2Value_ = (Boolean)arg2Value;
               int var16;
               this.state_0_ = var16 = state_0 | 1;
               HostObject.ArraySet.doBoolean(arg0Value_, arg1Value, arg2Value_);
               return;
            }
         }

         if (arg0Value instanceof byte[]) {
            byte[] arg0Value_ = (byte[])arg0Value;
            if (arg2Value instanceof Byte) {
               byte arg2Value_ = (Byte)arg2Value;
               int var15;
               this.state_0_ = var15 = state_0 | 2;
               HostObject.ArraySet.doByte(arg0Value_, arg1Value, arg2Value_);
               return;
            }
         }

         if (arg0Value instanceof short[]) {
            short[] arg0Value_ = (short[])arg0Value;
            if (arg2Value instanceof Short) {
               short arg2Value_ = (Short)arg2Value;
               int var14;
               this.state_0_ = var14 = state_0 | 4;
               HostObject.ArraySet.doShort(arg0Value_, arg1Value, arg2Value_);
               return;
            }
         }

         if (arg0Value instanceof char[]) {
            char[] arg0Value_ = (char[])arg0Value;
            if (arg2Value instanceof Character) {
               char arg2Value_ = (Character)arg2Value;
               int var13;
               this.state_0_ = var13 = state_0 | 8;
               HostObject.ArraySet.doChar(arg0Value_, arg1Value, arg2Value_);
               return;
            }
         }

         if (arg0Value instanceof int[]) {
            int[] arg0Value_ = (int[])arg0Value;
            if (arg2Value instanceof Integer) {
               int arg2Value_ = (Integer)arg2Value;
               int var12;
               this.state_0_ = var12 = state_0 | 16;
               HostObject.ArraySet.doInt(arg0Value_, arg1Value, arg2Value_);
               return;
            }
         }

         if (arg0Value instanceof long[]) {
            long[] arg0Value_ = (long[])arg0Value;
            if (arg2Value instanceof Long) {
               long arg2Value_ = (Long)arg2Value;
               int var11;
               this.state_0_ = var11 = state_0 | 32;
               HostObject.ArraySet.doLong(arg0Value_, arg1Value, arg2Value_);
               return;
            }
         }

         if (arg0Value instanceof float[]) {
            float[] arg0Value_ = (float[])arg0Value;
            if (arg2Value instanceof Float) {
               float arg2Value_ = (Float)arg2Value;
               int var10;
               this.state_0_ = var10 = state_0 | 64;
               HostObject.ArraySet.doFloat(arg0Value_, arg1Value, arg2Value_);
               return;
            }
         }

         if (arg0Value instanceof double[]) {
            double[] arg0Value_ = (double[])arg0Value;
            if (arg2Value instanceof Double) {
               double arg2Value_ = (Double)arg2Value;
               int var9;
               this.state_0_ = var9 = state_0 | 128;
               HostObject.ArraySet.doDouble(arg0Value_, arg1Value, arg2Value_);
               return;
            }
         }

         if (arg0Value instanceof Object[]) {
            Object[] arg0Value_ = (Object[])arg0Value;
            int var8;
            this.state_0_ = var8 = state_0 | 256;
            HostObject.ArraySet.doObject(arg0Value_, arg1Value, arg2Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
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

      public static HostObject.ArraySet create() {
         return new HostObjectFactory.ArraySetNodeGen();
      }

      public static HostObject.ArraySet getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostObject.ArraySet.class)
      @DenyReplace
      private static final class Uncached extends HostObject.ArraySet {
         @CompilerDirectives.TruffleBoundary
         @Override
         protected void execute(Object arg0Value, int arg1Value, Object arg2Value) {
            if (arg0Value instanceof boolean[]) {
               boolean[] arg0Value_ = (boolean[])arg0Value;
               if (arg2Value instanceof Boolean) {
                  boolean arg2Value_ = (Boolean)arg2Value;
                  HostObject.ArraySet.doBoolean(arg0Value_, arg1Value, arg2Value_);
                  return;
               }
            }

            if (arg0Value instanceof byte[]) {
               byte[] arg0Value_ = (byte[])arg0Value;
               if (arg2Value instanceof Byte) {
                  byte arg2Value_ = (Byte)arg2Value;
                  HostObject.ArraySet.doByte(arg0Value_, arg1Value, arg2Value_);
                  return;
               }
            }

            if (arg0Value instanceof short[]) {
               short[] arg0Value_ = (short[])arg0Value;
               if (arg2Value instanceof Short) {
                  short arg2Value_ = (Short)arg2Value;
                  HostObject.ArraySet.doShort(arg0Value_, arg1Value, arg2Value_);
                  return;
               }
            }

            if (arg0Value instanceof char[]) {
               char[] arg0Value_ = (char[])arg0Value;
               if (arg2Value instanceof Character) {
                  char arg2Value_ = (Character)arg2Value;
                  HostObject.ArraySet.doChar(arg0Value_, arg1Value, arg2Value_);
                  return;
               }
            }

            if (arg0Value instanceof int[]) {
               int[] arg0Value_ = (int[])arg0Value;
               if (arg2Value instanceof Integer) {
                  int arg2Value_ = (Integer)arg2Value;
                  HostObject.ArraySet.doInt(arg0Value_, arg1Value, arg2Value_);
                  return;
               }
            }

            if (arg0Value instanceof long[]) {
               long[] arg0Value_ = (long[])arg0Value;
               if (arg2Value instanceof Long) {
                  long arg2Value_ = (Long)arg2Value;
                  HostObject.ArraySet.doLong(arg0Value_, arg1Value, arg2Value_);
                  return;
               }
            }

            if (arg0Value instanceof float[]) {
               float[] arg0Value_ = (float[])arg0Value;
               if (arg2Value instanceof Float) {
                  float arg2Value_ = (Float)arg2Value;
                  HostObject.ArraySet.doFloat(arg0Value_, arg1Value, arg2Value_);
                  return;
               }
            }

            if (arg0Value instanceof double[]) {
               double[] arg0Value_ = (double[])arg0Value;
               if (arg2Value instanceof Double) {
                  double arg2Value_ = (Double)arg2Value;
                  HostObject.ArraySet.doDouble(arg0Value_, arg1Value, arg2Value_);
                  return;
               }
            }

            if (arg0Value instanceof Object[]) {
               Object[] arg0Value_ = (Object[])arg0Value;
               HostObject.ArraySet.doObject(arg0Value_, arg1Value, arg2Value);
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
            }
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }

   @GeneratedBy(HostObject.ContainsKeyNode.class)
   static final class ContainsKeyNodeGen extends HostObject.ContainsKeyNode {
      private static final HostObjectFactory.ContainsKeyNodeGen.Uncached UNCACHED = new HostObjectFactory.ContainsKeyNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private HostObject.IsMapNode isMap;
      @Node.Child
      private HostToTypeNode map_toHost_;
      @CompilerDirectives.CompilationFinal
      private BranchProfile map_error_;

      private ContainsKeyNodeGen() {
      }

      @Override
      public boolean execute(HostObject arg0Value, Object arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && this.isMap.execute(arg0Value)) {
               return HostObject.ContainsKeyNode.doMap(arg0Value, arg1Value, this.isMap, this.map_toHost_, this.map_error_);
            }

            if ((state_0 & 2) != 0 && !this.isMap.execute(arg0Value)) {
               return HostObject.ContainsKeyNode.doNotMap(arg0Value, arg1Value, this.isMap);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private boolean executeAndSpecialize(HostObject arg0Value, Object arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean NotMap_duplicateFound_;
         try {
            int state_0 = this.state_0_;
            boolean Map_duplicateFound_ = false;
            if ((state_0 & 1) != 0 && this.isMap.execute(arg0Value)) {
               Map_duplicateFound_ = true;
            }

            if (!Map_duplicateFound_) {
               HostObject.IsMapNode map_isMap__ = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap);
               if (map_isMap__.execute(arg0Value) && (state_0 & 1) == 0) {
                  if (this.isMap == null) {
                     HostObject.IsMapNode map_isMap___check = super.insert(map_isMap__);
                     if (map_isMap___check == null) {
                        throw new AssertionError(
                           "Specialization 'doMap(HostObject, Object, IsMapNode, HostToTypeNode, BranchProfile)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                        );
                     }

                     this.isMap = map_isMap___check;
                  }

                  this.map_toHost_ = super.insert(HostToTypeNodeGen.create());
                  this.map_error_ = BranchProfile.create();
                  this.state_0_ = state_0 |= 1;
                  Map_duplicateFound_ = true;
               }
            }

            if (!Map_duplicateFound_) {
               NotMap_duplicateFound_ = false;
               if ((state_0 & 2) != 0 && !this.isMap.execute(arg0Value)) {
                  NotMap_duplicateFound_ = true;
               }

               if (!NotMap_duplicateFound_) {
                  HostObject.IsMapNode notMap_isMap__ = super.insert(this.isMap == null ? HostObjectFactory.IsMapNodeGen.create() : this.isMap);
                  if (!notMap_isMap__.execute(arg0Value) && (state_0 & 2) == 0) {
                     if (this.isMap == null) {
                        HostObject.IsMapNode notMap_isMap___check = super.insert(notMap_isMap__);
                        if (notMap_isMap___check == null) {
                           throw new AssertionError(
                              "Specialization 'doNotMap(HostObject, Object, IsMapNode)' contains a shared cache with name 'isMap' that returned a null value for the cached initializer. Null values are not supported for shared cached initializers because null is reserved for the uninitialized state."
                           );
                        }

                        this.isMap = notMap_isMap___check;
                     }

                     int var13;
                     this.state_0_ = var13 = state_0 | 2;
                     NotMap_duplicateFound_ = true;
                  }
               }

               if (!NotMap_duplicateFound_) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
               }

               lock.unlock();
               hasLock = false;
               return HostObject.ContainsKeyNode.doNotMap(arg0Value, arg1Value, this.isMap);
            }

            lock.unlock();
            hasLock = false;
            NotMap_duplicateFound_ = HostObject.ContainsKeyNode.doMap(arg0Value, arg1Value, this.isMap, this.map_toHost_, this.map_error_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return NotMap_duplicateFound_;
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

      public static HostObject.ContainsKeyNode create() {
         return new HostObjectFactory.ContainsKeyNodeGen();
      }

      public static HostObject.ContainsKeyNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostObject.ContainsKeyNode.class)
      @DenyReplace
      private static final class Uncached extends HostObject.ContainsKeyNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean execute(HostObject arg0Value, Object arg1Value) {
            if (HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.ContainsKeyNode.doMap(
                  arg0Value, arg1Value, HostObjectFactory.IsMapNodeGen.getUncached(), HostToTypeNodeGen.getUncached(), BranchProfile.getUncached()
               );
            } else if (!HostObjectFactory.IsMapNodeGen.getUncached().execute(arg0Value)) {
               return HostObject.ContainsKeyNode.doNotMap(arg0Value, arg1Value, HostObjectFactory.IsMapNodeGen.getUncached());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null, null}, arg0Value, arg1Value);
            }
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }

   @GeneratedBy(HostObject.IsArrayNode.class)
   static final class IsArrayNodeGen extends HostObject.IsArrayNode {
      private static final HostObjectFactory.IsArrayNodeGen.Uncached UNCACHED = new HostObjectFactory.IsArrayNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private boolean default_isArrayAccess_;

      private IsArrayNodeGen() {
      }

      @Override
      public boolean execute(HostObject arg0Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value.obj == null) {
               return this.doNull(arg0Value);
            }

            if ((state_0 & 2) != 0 && arg0Value.obj != null) {
               return this.doDefault(arg0Value, this.default_isArrayAccess_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }

      private boolean executeAndSpecialize(HostObject arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var5;
         try {
            int state_0 = this.state_0_;
            if (arg0Value.obj != null) {
               if (arg0Value.obj == null) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
               }

               this.default_isArrayAccess_ = arg0Value.getHostClassCache().isArrayAccess();
               int var10;
               this.state_0_ = var10 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doDefault(arg0Value, this.default_isArrayAccess_);
            }

            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.doNull(arg0Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var5;
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

      public static HostObject.IsArrayNode create() {
         return new HostObjectFactory.IsArrayNodeGen();
      }

      public static HostObject.IsArrayNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostObject.IsArrayNode.class)
      @DenyReplace
      private static final class Uncached extends HostObject.IsArrayNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean execute(HostObject arg0Value) {
            if (arg0Value.obj == null) {
               return this.doNull(arg0Value);
            } else if (arg0Value.obj != null) {
               return this.doDefault(arg0Value, arg0Value.getHostClassCache().isArrayAccess());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }

   @GeneratedBy(HostObject.IsBufferNode.class)
   static final class IsBufferNodeGen extends HostObject.IsBufferNode {
      private static final HostObjectFactory.IsBufferNodeGen.Uncached UNCACHED = new HostObjectFactory.IsBufferNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private boolean default_isBufferAccess_;

      private IsBufferNodeGen() {
      }

      @Override
      public boolean execute(HostObject arg0Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value.obj == null) {
               return this.doNull(arg0Value);
            }

            if ((state_0 & 2) != 0 && arg0Value.obj != null) {
               return this.doDefault(arg0Value, this.default_isBufferAccess_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }

      private boolean executeAndSpecialize(HostObject arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var5;
         try {
            int state_0 = this.state_0_;
            if (arg0Value.obj != null) {
               if (arg0Value.obj == null) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
               }

               this.default_isBufferAccess_ = arg0Value.getHostClassCache().isBufferAccess();
               int var10;
               this.state_0_ = var10 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doDefault(arg0Value, this.default_isBufferAccess_);
            }

            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.doNull(arg0Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var5;
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

      public static HostObject.IsBufferNode create() {
         return new HostObjectFactory.IsBufferNodeGen();
      }

      public static HostObject.IsBufferNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostObject.IsBufferNode.class)
      @DenyReplace
      private static final class Uncached extends HostObject.IsBufferNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean execute(HostObject arg0Value) {
            if (arg0Value.obj == null) {
               return this.doNull(arg0Value);
            } else if (arg0Value.obj != null) {
               return this.doDefault(arg0Value, arg0Value.getHostClassCache().isBufferAccess());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }

   @GeneratedBy(HostObject.IsIterableNode.class)
   static final class IsIterableNodeGen extends HostObject.IsIterableNode {
      private static final HostObjectFactory.IsIterableNodeGen.Uncached UNCACHED = new HostObjectFactory.IsIterableNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private boolean default_isIterableAccess_;

      private IsIterableNodeGen() {
      }

      @Override
      public boolean execute(HostObject arg0Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value.obj == null) {
               return this.doNull(arg0Value);
            }

            if ((state_0 & 2) != 0 && arg0Value.obj != null) {
               return this.doDefault(arg0Value, this.default_isIterableAccess_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }

      private boolean executeAndSpecialize(HostObject arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var5;
         try {
            int state_0 = this.state_0_;
            if (arg0Value.obj != null) {
               if (arg0Value.obj == null) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
               }

               this.default_isIterableAccess_ = arg0Value.getHostClassCache().isIterableAccess();
               int var10;
               this.state_0_ = var10 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doDefault(arg0Value, this.default_isIterableAccess_);
            }

            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.doNull(arg0Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var5;
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

      public static HostObject.IsIterableNode create() {
         return new HostObjectFactory.IsIterableNodeGen();
      }

      public static HostObject.IsIterableNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostObject.IsIterableNode.class)
      @DenyReplace
      private static final class Uncached extends HostObject.IsIterableNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean execute(HostObject arg0Value) {
            if (arg0Value.obj == null) {
               return this.doNull(arg0Value);
            } else if (arg0Value.obj != null) {
               return this.doDefault(arg0Value, arg0Value.getHostClassCache().isIterableAccess());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }

   @GeneratedBy(HostObject.IsIteratorNode.class)
   static final class IsIteratorNodeGen extends HostObject.IsIteratorNode {
      private static final HostObjectFactory.IsIteratorNodeGen.Uncached UNCACHED = new HostObjectFactory.IsIteratorNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private boolean default_isIteratorAccess_;

      private IsIteratorNodeGen() {
      }

      @Override
      public boolean execute(HostObject arg0Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value.obj == null) {
               return this.doNull(arg0Value);
            }

            if ((state_0 & 2) != 0 && arg0Value.obj != null) {
               return this.doDefault(arg0Value, this.default_isIteratorAccess_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }

      private boolean executeAndSpecialize(HostObject arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var5;
         try {
            int state_0 = this.state_0_;
            if (arg0Value.obj != null) {
               if (arg0Value.obj == null) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
               }

               this.default_isIteratorAccess_ = arg0Value.getHostClassCache().isIteratorAccess();
               int var10;
               this.state_0_ = var10 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doDefault(arg0Value, this.default_isIteratorAccess_);
            }

            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.doNull(arg0Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var5;
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

      public static HostObject.IsIteratorNode create() {
         return new HostObjectFactory.IsIteratorNodeGen();
      }

      public static HostObject.IsIteratorNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostObject.IsIteratorNode.class)
      @DenyReplace
      private static final class Uncached extends HostObject.IsIteratorNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean execute(HostObject arg0Value) {
            if (arg0Value.obj == null) {
               return this.doNull(arg0Value);
            } else if (arg0Value.obj != null) {
               return this.doDefault(arg0Value, arg0Value.getHostClassCache().isIteratorAccess());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }

   @GeneratedBy(HostObject.IsListNode.class)
   static final class IsListNodeGen extends HostObject.IsListNode {
      private static final HostObjectFactory.IsListNodeGen.Uncached UNCACHED = new HostObjectFactory.IsListNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private boolean default_isListAccess_;

      private IsListNodeGen() {
      }

      @Override
      public boolean execute(HostObject arg0Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value.obj == null) {
               return this.doNull(arg0Value);
            }

            if ((state_0 & 2) != 0 && arg0Value.obj != null) {
               return this.doDefault(arg0Value, this.default_isListAccess_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }

      private boolean executeAndSpecialize(HostObject arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var5;
         try {
            int state_0 = this.state_0_;
            if (arg0Value.obj != null) {
               if (arg0Value.obj == null) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
               }

               this.default_isListAccess_ = arg0Value.getHostClassCache().isListAccess();
               int var10;
               this.state_0_ = var10 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doDefault(arg0Value, this.default_isListAccess_);
            }

            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.doNull(arg0Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var5;
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

      public static HostObject.IsListNode create() {
         return new HostObjectFactory.IsListNodeGen();
      }

      public static HostObject.IsListNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostObject.IsListNode.class)
      @DenyReplace
      private static final class Uncached extends HostObject.IsListNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean execute(HostObject arg0Value) {
            if (arg0Value.obj == null) {
               return this.doNull(arg0Value);
            } else if (arg0Value.obj != null) {
               return this.doDefault(arg0Value, arg0Value.getHostClassCache().isListAccess());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }

   @GeneratedBy(HostObject.IsMapEntryNode.class)
   static final class IsMapEntryNodeGen extends HostObject.IsMapEntryNode {
      private static final HostObjectFactory.IsMapEntryNodeGen.Uncached UNCACHED = new HostObjectFactory.IsMapEntryNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private boolean default_isMapAccess_;

      private IsMapEntryNodeGen() {
      }

      @Override
      public boolean execute(HostObject arg0Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value.obj == null) {
               return this.doNull(arg0Value);
            }

            if ((state_0 & 2) != 0 && arg0Value.obj != null) {
               return this.doDefault(arg0Value, this.default_isMapAccess_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }

      private boolean executeAndSpecialize(HostObject arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var5;
         try {
            int state_0 = this.state_0_;
            if (arg0Value.obj != null) {
               if (arg0Value.obj == null) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
               }

               this.default_isMapAccess_ = arg0Value.getHostClassCache().isMapAccess();
               int var10;
               this.state_0_ = var10 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doDefault(arg0Value, this.default_isMapAccess_);
            }

            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.doNull(arg0Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var5;
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

      public static HostObject.IsMapEntryNode create() {
         return new HostObjectFactory.IsMapEntryNodeGen();
      }

      public static HostObject.IsMapEntryNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostObject.IsMapEntryNode.class)
      @DenyReplace
      private static final class Uncached extends HostObject.IsMapEntryNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean execute(HostObject arg0Value) {
            if (arg0Value.obj == null) {
               return this.doNull(arg0Value);
            } else if (arg0Value.obj != null) {
               return this.doDefault(arg0Value, arg0Value.getHostClassCache().isMapAccess());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }

   @GeneratedBy(HostObject.IsMapNode.class)
   static final class IsMapNodeGen extends HostObject.IsMapNode {
      private static final HostObjectFactory.IsMapNodeGen.Uncached UNCACHED = new HostObjectFactory.IsMapNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private boolean default_isMapAccess_;

      private IsMapNodeGen() {
      }

      @Override
      public boolean execute(HostObject arg0Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg0Value.obj == null) {
               return this.doNull(arg0Value);
            }

            if ((state_0 & 2) != 0 && arg0Value.obj != null) {
               return this.doDefault(arg0Value, this.default_isMapAccess_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value);
      }

      private boolean executeAndSpecialize(HostObject arg0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         boolean var5;
         try {
            int state_0 = this.state_0_;
            if (arg0Value.obj != null) {
               if (arg0Value.obj == null) {
                  throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
               }

               this.default_isMapAccess_ = arg0Value.getHostClassCache().isMapAccess();
               int var10;
               this.state_0_ = var10 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doDefault(arg0Value, this.default_isMapAccess_);
            }

            int var9;
            this.state_0_ = var9 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var5 = this.doNull(arg0Value);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return var5;
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

      public static HostObject.IsMapNode create() {
         return new HostObjectFactory.IsMapNodeGen();
      }

      public static HostObject.IsMapNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostObject.IsMapNode.class)
      @DenyReplace
      private static final class Uncached extends HostObject.IsMapNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public boolean execute(HostObject arg0Value) {
            if (arg0Value.obj == null) {
               return this.doNull(arg0Value);
            } else if (arg0Value.obj != null) {
               return this.doDefault(arg0Value, arg0Value.getHostClassCache().isMapAccess());
            } else {
               throw new UnsupportedSpecializationException(this, new Node[]{null}, arg0Value);
            }
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }

   @GeneratedBy(HostObject.LookupConstructorNode.class)
   static final class LookupConstructorNodeGen extends HostObject.LookupConstructorNode {
      private static final HostObjectFactory.LookupConstructorNodeGen.Uncached UNCACHED = new HostObjectFactory.LookupConstructorNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private HostObjectFactory.LookupConstructorNodeGen.CachedData cached_cache;

      private LookupConstructorNodeGen() {
      }

      @ExplodeLoop
      @Override
      public HostMethodDesc execute(HostObject arg0Value, Class<?> arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (HostObjectFactory.LookupConstructorNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (arg1Value == s0_.cachedClazz_) {
                     return this.doCached(arg0Value, arg1Value, s0_.cachedClazz_, s0_.cachedMethod_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return this.doUncached(arg0Value, arg1Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private HostMethodDesc executeAndSpecialize(HostObject arg0Value, Class<?> arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               HostObjectFactory.LookupConstructorNodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && arg1Value != s0_.cachedClazz_) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null && count0_ < 3) {
                  s0_ = new HostObjectFactory.LookupConstructorNodeGen.CachedData(this.cached_cache);
                  s0_.cachedClazz_ = arg1Value;
                  s0_.cachedMethod_ = this.doUncached(arg0Value, arg1Value);
                  VarHandle.storeStoreFence();
                  this.cached_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(arg0Value, arg1Value, s0_.cachedClazz_, s0_.cachedMethod_);
               }
            }

            int var15;
            this.exclude_ = var15 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var14;
            this.state_0_ = var14 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doUncached(arg0Value, arg1Value);
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
               HostObjectFactory.LookupConstructorNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static HostObject.LookupConstructorNode create() {
         return new HostObjectFactory.LookupConstructorNodeGen();
      }

      public static HostObject.LookupConstructorNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostObject.LookupConstructorNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         HostObjectFactory.LookupConstructorNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         Class<?> cachedClazz_;
         @CompilerDirectives.CompilationFinal
         HostMethodDesc cachedMethod_;

         CachedData(HostObjectFactory.LookupConstructorNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(HostObject.LookupConstructorNode.class)
      @DenyReplace
      private static final class Uncached extends HostObject.LookupConstructorNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public HostMethodDesc execute(HostObject arg0Value, Class<?> arg1Value) {
            return this.doUncached(arg0Value, arg1Value);
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }

   @GeneratedBy(HostObject.LookupFieldNode.class)
   static final class LookupFieldNodeGen extends HostObject.LookupFieldNode {
      private static final HostObjectFactory.LookupFieldNodeGen.Uncached UNCACHED = new HostObjectFactory.LookupFieldNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private HostObjectFactory.LookupFieldNodeGen.CachedData cached_cache;

      private LookupFieldNodeGen() {
      }

      @ExplodeLoop
      @Override
      public HostFieldDesc execute(HostObject arg0Value, Class<?> arg1Value, String arg2Value, boolean arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (HostObjectFactory.LookupFieldNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (arg3Value == s0_.cachedStatic_ && arg1Value == s0_.cachedClazz_ && s0_.cachedName_.equals(arg2Value)) {
                     return this.doCached(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedField_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }

      private HostFieldDesc executeAndSpecialize(HostObject arg0Value, Class<?> arg1Value, String arg2Value, boolean arg3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               HostObjectFactory.LookupFieldNodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && (arg3Value != s0_.cachedStatic_ || arg1Value != s0_.cachedClazz_ || !s0_.cachedName_.equals(arg2Value))) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null && count0_ < 3) {
                  s0_ = new HostObjectFactory.LookupFieldNodeGen.CachedData(this.cached_cache);
                  s0_.cachedStatic_ = arg3Value;
                  s0_.cachedClazz_ = arg1Value;
                  s0_.cachedName_ = arg2Value;
                  s0_.cachedField_ = this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value);
                  VarHandle.storeStoreFence();
                  this.cached_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedField_);
               }
            }

            int var17;
            this.exclude_ = var17 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var16;
            this.state_0_ = var16 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value);
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
               HostObjectFactory.LookupFieldNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static HostObject.LookupFieldNode create() {
         return new HostObjectFactory.LookupFieldNodeGen();
      }

      public static HostObject.LookupFieldNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostObject.LookupFieldNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         HostObjectFactory.LookupFieldNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         boolean cachedStatic_;
         @CompilerDirectives.CompilationFinal
         Class<?> cachedClazz_;
         @CompilerDirectives.CompilationFinal
         String cachedName_;
         @CompilerDirectives.CompilationFinal
         HostFieldDesc cachedField_;

         CachedData(HostObjectFactory.LookupFieldNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(HostObject.LookupFieldNode.class)
      @DenyReplace
      private static final class Uncached extends HostObject.LookupFieldNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public HostFieldDesc execute(HostObject arg0Value, Class<?> arg1Value, String arg2Value, boolean arg3Value) {
            return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value);
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }

   @GeneratedBy(HostObject.LookupFunctionalMethodNode.class)
   static final class LookupFunctionalMethodNodeGen extends HostObject.LookupFunctionalMethodNode {
      private static final HostObjectFactory.LookupFunctionalMethodNodeGen.Uncached UNCACHED = new HostObjectFactory.LookupFunctionalMethodNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private HostObjectFactory.LookupFunctionalMethodNodeGen.CachedData cached_cache;

      private LookupFunctionalMethodNodeGen() {
      }

      @ExplodeLoop
      @Override
      public HostMethodDesc execute(HostObject arg0Value, Class<?> arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (HostObjectFactory.LookupFunctionalMethodNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (arg1Value == s0_.cachedClazz_) {
                     return this.doCached(arg0Value, arg1Value, s0_.cachedClazz_, s0_.cachedMethod_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return HostObject.LookupFunctionalMethodNode.doUncached(arg0Value, arg1Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private HostMethodDesc executeAndSpecialize(HostObject arg0Value, Class<?> arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               HostObjectFactory.LookupFunctionalMethodNodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && arg1Value != s0_.cachedClazz_) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null && count0_ < 3) {
                  s0_ = new HostObjectFactory.LookupFunctionalMethodNodeGen.CachedData(this.cached_cache);
                  s0_.cachedClazz_ = arg1Value;
                  s0_.cachedMethod_ = HostObject.LookupFunctionalMethodNode.doUncached(arg0Value, arg1Value);
                  VarHandle.storeStoreFence();
                  this.cached_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(arg0Value, arg1Value, s0_.cachedClazz_, s0_.cachedMethod_);
               }
            }

            int var15;
            this.exclude_ = var15 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var14;
            this.state_0_ = var14 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return HostObject.LookupFunctionalMethodNode.doUncached(arg0Value, arg1Value);
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
               HostObjectFactory.LookupFunctionalMethodNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static HostObject.LookupFunctionalMethodNode create() {
         return new HostObjectFactory.LookupFunctionalMethodNodeGen();
      }

      public static HostObject.LookupFunctionalMethodNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostObject.LookupFunctionalMethodNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         HostObjectFactory.LookupFunctionalMethodNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         Class<?> cachedClazz_;
         @CompilerDirectives.CompilationFinal
         HostMethodDesc cachedMethod_;

         CachedData(HostObjectFactory.LookupFunctionalMethodNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(HostObject.LookupFunctionalMethodNode.class)
      @DenyReplace
      private static final class Uncached extends HostObject.LookupFunctionalMethodNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public HostMethodDesc execute(HostObject arg0Value, Class<?> arg1Value) {
            return HostObject.LookupFunctionalMethodNode.doUncached(arg0Value, arg1Value);
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }

   @GeneratedBy(HostObject.LookupInnerClassNode.class)
   static final class LookupInnerClassNodeGen extends HostObject.LookupInnerClassNode {
      private static final HostObjectFactory.LookupInnerClassNodeGen.Uncached UNCACHED = new HostObjectFactory.LookupInnerClassNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private HostObjectFactory.LookupInnerClassNodeGen.CachedData cached_cache;

      private LookupInnerClassNodeGen() {
      }

      @ExplodeLoop
      @Override
      public Class<?> execute(Class<?> arg0Value, String arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (HostObjectFactory.LookupInnerClassNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (arg0Value == s0_.cachedClazz_ && s0_.cachedName_.equals(arg1Value)) {
                     return this.doCached(arg0Value, arg1Value, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedInnerClass_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return this.doUncached(arg0Value, arg1Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private Class<?> executeAndSpecialize(Class<?> arg0Value, String arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               HostObjectFactory.LookupInnerClassNodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && (arg0Value != s0_.cachedClazz_ || !s0_.cachedName_.equals(arg1Value))) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null && count0_ < 3) {
                  s0_ = new HostObjectFactory.LookupInnerClassNodeGen.CachedData(this.cached_cache);
                  s0_.cachedClazz_ = arg0Value;
                  s0_.cachedName_ = arg1Value;
                  s0_.cachedInnerClass_ = this.doUncached(arg0Value, arg1Value);
                  VarHandle.storeStoreFence();
                  this.cached_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(arg0Value, arg1Value, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedInnerClass_);
               }
            }

            int var15;
            this.exclude_ = var15 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var14;
            this.state_0_ = var14 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doUncached(arg0Value, arg1Value);
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
               HostObjectFactory.LookupInnerClassNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static HostObject.LookupInnerClassNode create() {
         return new HostObjectFactory.LookupInnerClassNodeGen();
      }

      public static HostObject.LookupInnerClassNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostObject.LookupInnerClassNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         HostObjectFactory.LookupInnerClassNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         Class<?> cachedClazz_;
         @CompilerDirectives.CompilationFinal
         String cachedName_;
         @CompilerDirectives.CompilationFinal
         Class<?> cachedInnerClass_;

         CachedData(HostObjectFactory.LookupInnerClassNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(HostObject.LookupInnerClassNode.class)
      @DenyReplace
      private static final class Uncached extends HostObject.LookupInnerClassNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public Class<?> execute(Class<?> arg0Value, String arg1Value) {
            return this.doUncached(arg0Value, arg1Value);
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }

   @GeneratedBy(HostObject.LookupMethodNode.class)
   static final class LookupMethodNodeGen extends HostObject.LookupMethodNode {
      private static final HostObjectFactory.LookupMethodNodeGen.Uncached UNCACHED = new HostObjectFactory.LookupMethodNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @CompilerDirectives.CompilationFinal
      private HostObjectFactory.LookupMethodNodeGen.CachedData cached_cache;

      private LookupMethodNodeGen() {
      }

      @ExplodeLoop
      @Override
      public HostMethodDesc execute(HostObject arg0Value, Class<?> arg1Value, String arg2Value, boolean arg3Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (HostObjectFactory.LookupMethodNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (arg3Value == s0_.cachedStatic_ && arg1Value == s0_.cachedClazz_ && s0_.cachedName_.equals(arg2Value)) {
                     return this.doCached(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedMethod_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
      }

      private HostMethodDesc executeAndSpecialize(HostObject arg0Value, Class<?> arg1Value, String arg2Value, boolean arg3Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               HostObjectFactory.LookupMethodNodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && (arg3Value != s0_.cachedStatic_ || arg1Value != s0_.cachedClazz_ || !s0_.cachedName_.equals(arg2Value))) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null && count0_ < 3) {
                  s0_ = new HostObjectFactory.LookupMethodNodeGen.CachedData(this.cached_cache);
                  s0_.cachedStatic_ = arg3Value;
                  s0_.cachedClazz_ = arg1Value;
                  s0_.cachedName_ = arg2Value;
                  s0_.cachedMethod_ = this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value);
                  VarHandle.storeStoreFence();
                  this.cached_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.doCached(arg0Value, arg1Value, arg2Value, arg3Value, s0_.cachedStatic_, s0_.cachedClazz_, s0_.cachedName_, s0_.cachedMethod_);
               }
            }

            int var17;
            this.exclude_ = var17 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var16;
            this.state_0_ = var16 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value);
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
               HostObjectFactory.LookupMethodNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static HostObject.LookupMethodNode create() {
         return new HostObjectFactory.LookupMethodNodeGen();
      }

      public static HostObject.LookupMethodNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostObject.LookupMethodNode.class)
      private static final class CachedData {
         @CompilerDirectives.CompilationFinal
         HostObjectFactory.LookupMethodNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         boolean cachedStatic_;
         @CompilerDirectives.CompilationFinal
         Class<?> cachedClazz_;
         @CompilerDirectives.CompilationFinal
         String cachedName_;
         @CompilerDirectives.CompilationFinal
         HostMethodDesc cachedMethod_;

         CachedData(HostObjectFactory.LookupMethodNodeGen.CachedData next_) {
            this.next_ = next_;
         }
      }

      @GeneratedBy(HostObject.LookupMethodNode.class)
      @DenyReplace
      private static final class Uncached extends HostObject.LookupMethodNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public HostMethodDesc execute(HostObject arg0Value, Class<?> arg1Value, String arg2Value, boolean arg3Value) {
            return this.doUncached(arg0Value, arg1Value, arg2Value, arg3Value);
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }

   @GeneratedBy(HostObject.ReadFieldNode.class)
   static final class ReadFieldNodeGen extends HostObject.ReadFieldNode {
      private static final HostObjectFactory.ReadFieldNodeGen.Uncached UNCACHED = new HostObjectFactory.ReadFieldNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private HostObjectFactory.ReadFieldNodeGen.CachedData cached_cache;
      @Node.Child
      private HostContext.ToGuestValueNode uncached_toGuest_;

      private ReadFieldNodeGen() {
      }

      @ExplodeLoop
      @Override
      public Object execute(HostFieldDesc arg0Value, HostObject arg1Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (HostObjectFactory.ReadFieldNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (arg0Value == s0_.cachedField_) {
                     return HostObject.ReadFieldNode.doCached(arg0Value, arg1Value, s0_.cachedField_, s0_.toGuest_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return HostObject.ReadFieldNode.doUncached(arg0Value, arg1Value, this.uncached_toGuest_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arg0Value, arg1Value);
      }

      private Object executeAndSpecialize(HostFieldDesc arg0Value, HostObject arg1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               HostObjectFactory.ReadFieldNodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && arg0Value != s0_.cachedField_) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null && count0_ < 3) {
                  s0_ = super.insert(new HostObjectFactory.ReadFieldNodeGen.CachedData(this.cached_cache));
                  s0_.cachedField_ = arg0Value;
                  s0_.toGuest_ = s0_.insertAccessor(HostContextFactory.ToGuestValueNodeGen.create());
                  VarHandle.storeStoreFence();
                  this.cached_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return HostObject.ReadFieldNode.doCached(arg0Value, arg1Value, s0_.cachedField_, s0_.toGuest_);
               }
            }

            this.uncached_toGuest_ = super.insert(HostContextFactory.ToGuestValueNodeGen.create());
            int var15;
            this.exclude_ = var15 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var14;
            this.state_0_ = var14 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return HostObject.ReadFieldNode.doUncached(arg0Value, arg1Value, this.uncached_toGuest_);
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
               HostObjectFactory.ReadFieldNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static HostObject.ReadFieldNode create() {
         return new HostObjectFactory.ReadFieldNodeGen();
      }

      public static HostObject.ReadFieldNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostObject.ReadFieldNode.class)
      private static final class CachedData extends Node {
         @Node.Child
         HostObjectFactory.ReadFieldNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         HostFieldDesc cachedField_;
         @Node.Child
         HostContext.ToGuestValueNode toGuest_;

         CachedData(HostObjectFactory.ReadFieldNodeGen.CachedData next_) {
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

      @GeneratedBy(HostObject.ReadFieldNode.class)
      @DenyReplace
      private static final class Uncached extends HostObject.ReadFieldNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public Object execute(HostFieldDesc arg0Value, HostObject arg1Value) {
            return HostObject.ReadFieldNode.doUncached(arg0Value, arg1Value, HostContextFactory.ToGuestValueNodeGen.getUncached());
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }

   @GeneratedBy(HostObject.WriteFieldNode.class)
   static final class WriteFieldNodeGen extends HostObject.WriteFieldNode {
      private static final HostObjectFactory.WriteFieldNodeGen.Uncached UNCACHED = new HostObjectFactory.WriteFieldNodeGen.Uncached();
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private HostObjectFactory.WriteFieldNodeGen.CachedData cached_cache;
      @Node.Child
      private HostToTypeNode uncached_toHost_;

      private WriteFieldNodeGen() {
      }

      @ExplodeLoop
      @Override
      public void execute(HostFieldDesc arg0Value, HostObject arg1Value, Object arg2Value) throws UnsupportedTypeException, UnknownIdentifierException {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
               for (HostObjectFactory.WriteFieldNodeGen.CachedData s0_ = this.cached_cache; s0_ != null; s0_ = s0_.next_) {
                  if (arg0Value == s0_.cachedField_) {
                     HostObject.WriteFieldNode.doCached(arg0Value, arg1Value, arg2Value, s0_.cachedField_, s0_.toHost_, s0_.error_);
                     return;
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               HostObject.WriteFieldNode.doUncached(arg0Value, arg1Value, arg2Value, this.uncached_toHost_);
               return;
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      private void executeAndSpecialize(HostFieldDesc arg0Value, HostObject arg1Value, Object arg2Value) throws UnsupportedTypeException, UnknownIdentifierException {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
               int count0_ = 0;
               HostObjectFactory.WriteFieldNodeGen.CachedData s0_ = this.cached_cache;
               if ((state_0 & 1) != 0) {
                  while (s0_ != null && arg0Value != s0_.cachedField_) {
                     s0_ = s0_.next_;
                     count0_++;
                  }
               }

               if (s0_ == null && count0_ < 3) {
                  s0_ = super.insert(new HostObjectFactory.WriteFieldNodeGen.CachedData(this.cached_cache));
                  s0_.cachedField_ = arg0Value;
                  s0_.toHost_ = s0_.insertAccessor(HostToTypeNodeGen.create());
                  s0_.error_ = BranchProfile.create();
                  VarHandle.storeStoreFence();
                  this.cached_cache = s0_;
                  this.state_0_ = state_0 |= 1;
               }

               if (s0_ != null) {
                  lock.unlock();
                  hasLock = false;
                  HostObject.WriteFieldNode.doCached(arg0Value, arg1Value, arg2Value, s0_.cachedField_, s0_.toHost_, s0_.error_);
                  return;
               }
            }

            this.uncached_toHost_ = super.insert(HostToTypeNodeGen.create());
            int var15;
            this.exclude_ = var15 = exclude | 1;
            this.cached_cache = null;
            state_0 &= -2;
            int var14;
            this.state_0_ = var14 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            HostObject.WriteFieldNode.doUncached(arg0Value, arg1Value, arg2Value, this.uncached_toHost_);
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
               HostObjectFactory.WriteFieldNodeGen.CachedData s0_ = this.cached_cache;
               if (s0_ == null || s0_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      public static HostObject.WriteFieldNode create() {
         return new HostObjectFactory.WriteFieldNodeGen();
      }

      public static HostObject.WriteFieldNode getUncached() {
         return UNCACHED;
      }

      @GeneratedBy(HostObject.WriteFieldNode.class)
      private static final class CachedData extends Node {
         @Node.Child
         HostObjectFactory.WriteFieldNodeGen.CachedData next_;
         @CompilerDirectives.CompilationFinal
         HostFieldDesc cachedField_;
         @Node.Child
         HostToTypeNode toHost_;
         @CompilerDirectives.CompilationFinal
         BranchProfile error_;

         CachedData(HostObjectFactory.WriteFieldNodeGen.CachedData next_) {
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

      @GeneratedBy(HostObject.WriteFieldNode.class)
      @DenyReplace
      private static final class Uncached extends HostObject.WriteFieldNode {
         @CompilerDirectives.TruffleBoundary
         @Override
         public void execute(HostFieldDesc arg0Value, HostObject arg1Value, Object arg2Value) throws UnsupportedTypeException, UnknownIdentifierException {
            HostObject.WriteFieldNode.doUncached(arg0Value, arg1Value, arg2Value, HostToTypeNodeGen.getUncached());
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
         }

         @Override
         public boolean isAdoptable() {
            return false;
         }
      }
   }
}
