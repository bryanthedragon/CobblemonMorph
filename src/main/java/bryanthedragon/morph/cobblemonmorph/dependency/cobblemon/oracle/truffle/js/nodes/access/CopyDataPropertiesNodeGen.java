package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.EncapsulatingNodeReference;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.helper.ListGetNode;
import com.oracle.truffle.js.builtins.helper.ListSizeNode;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(CopyDataPropertiesNode.class)
public final class CopyDataPropertiesNodeGen extends CopyDataPropertiesNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @CompilerDirectives.CompilationFinal
   private volatile int exclude_;
   @Node.Child
   private CopyDataPropertiesNodeGen.CopyDataPropertiesData copyDataProperties_cache;
   @Node.Child
   private CopyDataPropertiesNodeGen.CopyDataPropertiesForeign0Data copyDataPropertiesForeign0_cache;
   @Node.Child
   private CopyDataPropertiesNodeGen.CopyDataPropertiesForeign1Data copyDataPropertiesForeign1_cache;

   private CopyDataPropertiesNodeGen(JSContext context) {
      super(context);
   }

   @ExplodeLoop
   @Override
   protected Object executeImpl(Object arg0Value, Object arg1Value, Object[] arg2Value, boolean arg3Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0 && arg0Value instanceof JSDynamicObject) {
         JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
         if ((state_0 & 1) != 0 && JSGuards.isNullOrUndefined(arg1Value)) {
            return CopyDataPropertiesNode.doNullOrUndefined(arg0Value_, arg1Value, arg2Value, arg3Value);
         }

         if ((state_0 & 2) != 0 && arg1Value instanceof JSDynamicObject) {
            JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
            CopyDataPropertiesNodeGen.CopyDataPropertiesData s1_ = this.copyDataProperties_cache;
            if (s1_ != null && JSGuards.isJSObject(arg1Value_)) {
               return CopyDataPropertiesNode.copyDataProperties(
                  arg0Value_,
                  arg1Value_,
                  arg2Value,
                  arg3Value,
                  s1_.getNode_,
                  s1_.getOwnProperty_,
                  s1_.listSize_,
                  s1_.listGet_,
                  s1_.classProfile_,
                  s1_.equalsNode_
               );
            }
         }

         if ((state_0 & 12) != 0) {
            if ((state_0 & 4) != 0) {
               for (CopyDataPropertiesNodeGen.CopyDataPropertiesForeign0Data s2_ = this.copyDataPropertiesForeign0_cache; s2_ != null; s2_ = s2_.next_) {
                  if (s2_.objInterop_.accepts(arg1Value) && !JSGuards.isJSDynamicObject(arg1Value)) {
                     return this.copyDataPropertiesForeign(
                        arg0Value_,
                        arg1Value,
                        arg2Value,
                        arg3Value,
                        s2_.objInterop_,
                        s2_.iteratorInterop_,
                        s2_.arrayInterop_,
                        s2_.stringInterop_,
                        s2_.importValue_,
                        s2_.toString_,
                        s2_.equalsNode_
                     );
                  }
               }
            }

            if ((state_0 & 8) != 0) {
               CopyDataPropertiesNodeGen.CopyDataPropertiesForeign1Data s3_ = this.copyDataPropertiesForeign1_cache;
               if (s3_ != null && !JSGuards.isJSDynamicObject(arg1Value)) {
                  return this.copyDataPropertiesForeign1Boundary(state_0, s3_, arg0Value_, arg1Value, arg2Value, arg3Value);
               }
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
   }

   @CompilerDirectives.TruffleBoundary
   private Object copyDataPropertiesForeign1Boundary(
      int state_0,
      CopyDataPropertiesNodeGen.CopyDataPropertiesForeign1Data s3_,
      JSDynamicObject arg0Value_,
      Object arg1Value,
      Object[] arg2Value,
      boolean arg3Value
   ) {
      EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
      Node prev_ = encapsulating_.set(this);

      JSDynamicObject var10;
      try {
         InteropLibrary objInterop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
         var10 = this.copyDataPropertiesForeign(
            arg0Value_,
            arg1Value,
            arg2Value,
            arg3Value,
            objInterop__,
            s3_.iteratorInterop_,
            s3_.arrayInterop_,
            s3_.stringInterop_,
            s3_.importValue_,
            s3_.toString_,
            s3_.equalsNode_
         );
      } finally {
         encapsulating_.set(prev_);
      }

      return var10;
   }

   private JSDynamicObject executeAndSpecialize(Object arg0Value, Object arg1Value, Object[] arg2Value, boolean arg3Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         if (arg0Value instanceof JSDynamicObject) {
            JSDynamicObject arg0Value_ = (JSDynamicObject)arg0Value;
            if (JSGuards.isNullOrUndefined(arg1Value)) {
               int var26;
               this.state_0_ = var26 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return CopyDataPropertiesNode.doNullOrUndefined(arg0Value_, arg1Value, arg2Value, arg3Value);
            }

            if (arg1Value instanceof JSDynamicObject) {
               JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
               if (JSGuards.isJSObject(arg1Value_)) {
                  CopyDataPropertiesNodeGen.CopyDataPropertiesData s1_ = super.insert(new CopyDataPropertiesNodeGen.CopyDataPropertiesData());
                  s1_.getNode_ = s1_.insertAccessor(ReadElementNode.create(this.context));
                  s1_.getOwnProperty_ = s1_.insertAccessor(JSGetOwnPropertyNode.create(false));
                  s1_.listSize_ = s1_.insertAccessor(ListSizeNode.create());
                  s1_.listGet_ = s1_.insertAccessor(ListGetNode.create());
                  s1_.classProfile_ = JSClassProfile.create();
                  s1_.equalsNode_ = s1_.insertAccessor(TruffleString.EqualNode.create());
                  VarHandle.storeStoreFence();
                  this.copyDataProperties_cache = s1_;
                  int var25;
                  this.state_0_ = var25 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return CopyDataPropertiesNode.copyDataProperties(
                     arg0Value_,
                     arg1Value_,
                     arg2Value,
                     arg3Value,
                     s1_.getNode_,
                     s1_.getOwnProperty_,
                     s1_.listSize_,
                     s1_.listGet_,
                     s1_.classProfile_,
                     s1_.equalsNode_
                  );
               }
            }

            if (exclude == 0) {
               int count2_ = 0;
               CopyDataPropertiesNodeGen.CopyDataPropertiesForeign0Data s2_ = this.copyDataPropertiesForeign0_cache;
               if ((state_0 & 4) != 0) {
                  while (s2_ != null && (!s2_.objInterop_.accepts(arg1Value) || JSGuards.isJSDynamicObject(arg1Value))) {
                     s2_ = s2_.next_;
                     count2_++;
                  }
               }

               if (s2_ == null && !JSGuards.isJSDynamicObject(arg1Value) && count2_ < 5) {
                  s2_ = super.insert(new CopyDataPropertiesNodeGen.CopyDataPropertiesForeign0Data(this.copyDataPropertiesForeign0_cache));
                  s2_.objInterop_ = s2_.insertAccessor(INTEROP_LIBRARY_.create(arg1Value));
                  s2_.iteratorInterop_ = s2_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                  s2_.arrayInterop_ = s2_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                  s2_.stringInterop_ = s2_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                  s2_.importValue_ = s2_.insertAccessor(ImportValueNode.create());
                  s2_.toString_ = s2_.insertAccessor(JSToStringNode.create());
                  s2_.equalsNode_ = s2_.insertAccessor(TruffleString.EqualNode.create());
                  VarHandle.storeStoreFence();
                  this.copyDataPropertiesForeign0_cache = s2_;
                  this.state_0_ = state_0 |= 4;
               }

               if (s2_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.copyDataPropertiesForeign(
                     arg0Value_,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     s2_.objInterop_,
                     s2_.iteratorInterop_,
                     s2_.arrayInterop_,
                     s2_.stringInterop_,
                     s2_.importValue_,
                     s2_.toString_,
                     s2_.equalsNode_
                  );
               }
            }

            InteropLibrary objInterop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               if (!JSGuards.isJSDynamicObject(arg1Value)) {
                  CopyDataPropertiesNodeGen.CopyDataPropertiesForeign1Data s3_ = super.insert(new CopyDataPropertiesNodeGen.CopyDataPropertiesForeign1Data());
                  objInterop__ = INTEROP_LIBRARY_.getUncached(arg1Value);
                  s3_.iteratorInterop_ = s3_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                  s3_.arrayInterop_ = s3_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                  s3_.stringInterop_ = s3_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
                  s3_.importValue_ = s3_.insertAccessor(ImportValueNode.create());
                  s3_.toString_ = s3_.insertAccessor(JSToStringNode.create());
                  s3_.equalsNode_ = s3_.insertAccessor(TruffleString.EqualNode.create());
                  VarHandle.storeStoreFence();
                  this.copyDataPropertiesForeign1_cache = s3_;
                  int var27;
                  this.exclude_ = var27 = exclude | 1;
                  this.copyDataPropertiesForeign0_cache = null;
                  state_0 &= -5;
                  int var24;
                  this.state_0_ = var24 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.copyDataPropertiesForeign(
                     arg0Value_,
                     arg1Value,
                     arg2Value,
                     arg3Value,
                     objInterop__,
                     s3_.iteratorInterop_,
                     s3_.arrayInterop_,
                     s3_.stringInterop_,
                     s3_.importValue_,
                     s3_.toString_,
                     s3_.equalsNode_
                  );
               }
            } finally {
               encapsulating_.set(prev_);
            }
         }

         throw new UnsupportedSpecializationException(this, new Node[]{null, null, null, null}, arg0Value, arg1Value, arg2Value, arg3Value);
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
            CopyDataPropertiesNodeGen.CopyDataPropertiesForeign0Data s2_ = this.copyDataPropertiesForeign0_cache;
            if (s2_ == null || s2_.next_ == null) {
               return NodeCost.MONOMORPHIC;
            }
         }

         return NodeCost.POLYMORPHIC;
      }
   }

   @Override
   public Introspection getIntrospectionData() {
      Object[] data = new Object[5];
      data[0] = 0;
      int state_0 = this.state_0_;
      int exclude = this.exclude_;
      Object[] s = new Object[]{"doNullOrUndefined", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"copyDataProperties", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         CopyDataPropertiesNodeGen.CopyDataPropertiesData s1_ = this.copyDataProperties_cache;
         if (s1_ != null) {
            cached.add(Arrays.asList(s1_.getNode_, s1_.getOwnProperty_, s1_.listSize_, s1_.listGet_, s1_.classProfile_, s1_.equalsNode_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"copyDataPropertiesForeign", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();

         for (CopyDataPropertiesNodeGen.CopyDataPropertiesForeign0Data s2_ = this.copyDataPropertiesForeign0_cache; s2_ != null; s2_ = s2_.next_) {
            cached.add(
               Arrays.asList(s2_.objInterop_, s2_.iteratorInterop_, s2_.arrayInterop_, s2_.stringInterop_, s2_.importValue_, s2_.toString_, s2_.equalsNode_)
            );
         }

         s[2] = cached;
      } else if (exclude != 0) {
         s[1] = (byte)2;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"copyDataPropertiesForeign", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         CopyDataPropertiesNodeGen.CopyDataPropertiesForeign1Data s3_ = this.copyDataPropertiesForeign1_cache;
         if (s3_ != null) {
            cached.add(Arrays.asList(s3_.iteratorInterop_, s3_.arrayInterop_, s3_.stringInterop_, s3_.importValue_, s3_.toString_, s3_.equalsNode_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      return Introspection.Provider.create(data);
   }

   public static CopyDataPropertiesNode create(JSContext context) {
      return new CopyDataPropertiesNodeGen(context);
   }

   @GeneratedBy(CopyDataPropertiesNode.class)
   private static final class CopyDataPropertiesData extends Node {
      @Node.Child
      ReadElementNode getNode_;
      @Node.Child
      JSGetOwnPropertyNode getOwnProperty_;
      @Node.Child
      ListSizeNode listSize_;
      @Node.Child
      ListGetNode listGet_;
      @CompilerDirectives.CompilationFinal
      JSClassProfile classProfile_;
      @Node.Child
      TruffleString.EqualNode equalsNode_;

      CopyDataPropertiesData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(CopyDataPropertiesNode.class)
   private static final class CopyDataPropertiesForeign0Data extends Node {
      @Node.Child
      CopyDataPropertiesNodeGen.CopyDataPropertiesForeign0Data next_;
      @Node.Child
      InteropLibrary objInterop_;
      @Node.Child
      InteropLibrary iteratorInterop_;
      @Node.Child
      InteropLibrary arrayInterop_;
      @Node.Child
      InteropLibrary stringInterop_;
      @Node.Child
      ImportValueNode importValue_;
      @Node.Child
      JSToStringNode toString_;
      @Node.Child
      TruffleString.EqualNode equalsNode_;

      CopyDataPropertiesForeign0Data(CopyDataPropertiesNodeGen.CopyDataPropertiesForeign0Data next_) {
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

   @GeneratedBy(CopyDataPropertiesNode.class)
   private static final class CopyDataPropertiesForeign1Data extends Node {
      @Node.Child
      InteropLibrary iteratorInterop_;
      @Node.Child
      InteropLibrary arrayInterop_;
      @Node.Child
      InteropLibrary stringInterop_;
      @Node.Child
      ImportValueNode importValue_;
      @Node.Child
      JSToStringNode toString_;
      @Node.Child
      TruffleString.EqualNode equalsNode_;

      CopyDataPropertiesForeign1Data() {
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
