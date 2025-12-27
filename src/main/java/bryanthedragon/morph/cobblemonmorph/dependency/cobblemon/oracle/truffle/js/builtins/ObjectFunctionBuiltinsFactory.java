package com.oracle.truffle.js.builtins;

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
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.helper.ListGetNode;
import com.oracle.truffle.js.builtins.helper.ListSizeNode;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JSTypes;
import com.oracle.truffle.js.nodes.JSTypesGen;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.EnumerableOwnPropertyNamesNode;
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
import com.oracle.truffle.js.nodes.access.IsExtensibleNode;
import com.oracle.truffle.js.nodes.access.JSGetOwnPropertyNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.binary.JSIdenticalNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.SafeInteger;
import com.oracle.truffle.js.runtime.Symbol;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.util.JSClassProfile;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ObjectFunctionBuiltins.class)
public final class ObjectFunctionBuiltinsFactory {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);

   @GeneratedBy(ObjectFunctionBuiltins.AssignPropertiesNode.class)
   static final class AssignPropertiesNodeGen extends ObjectFunctionBuiltins.AssignPropertiesNode implements Introspection.Provider {
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private ObjectFunctionBuiltinsFactory.AssignPropertiesNodeGen.CopyPropertiesFromJSObjectData copyPropertiesFromJSObject_cache;
      @Node.Child
      private ObjectFunctionBuiltinsFactory.AssignPropertiesNodeGen.Object0Data object0_cache;
      @Node.Child
      private InteropLibrary object1_keysInterop_;
      @Node.Child
      private InteropLibrary object1_stringInterop_;

      private AssignPropertiesNodeGen(JSContext context) {
         super(context);
      }

      @ExplodeLoop
      @Override
      void executeVoid(Object arg0Value, Object arg1Value, WriteElementNode arg2Value) {
         int state_0 = this.state_0_;
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arg1Value instanceof JSDynamicObject) {
               JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
               ObjectFunctionBuiltinsFactory.AssignPropertiesNodeGen.CopyPropertiesFromJSObjectData s0_ = this.copyPropertiesFromJSObject_cache;
               if (s0_ != null && JSGuards.isJSObject(arg1Value_)) {
                  ObjectFunctionBuiltins.AssignPropertiesNode.copyPropertiesFromJSObject(
                     arg0Value, arg1Value_, arg2Value, s0_.read_, s0_.getOwnProperty_, s0_.listSize_, s0_.listGet_, s0_.classProfile_
                  );
                  return;
               }
            }

            if ((state_0 & 6) != 0) {
               if ((state_0 & 2) != 0) {
                  for (ObjectFunctionBuiltinsFactory.AssignPropertiesNodeGen.Object0Data s1_ = this.object0_cache; s1_ != null; s1_ = s1_.next_) {
                     if (s1_.fromInterop_.accepts(arg1Value) && !JSGuards.isJSObject(arg1Value)) {
                        this.doObject(arg0Value, arg1Value, arg2Value, s1_.fromInterop_, s1_.keysInterop_, s1_.stringInterop_);
                        return;
                     }
                  }
               }

               if ((state_0 & 4) != 0 && !JSGuards.isJSObject(arg1Value)) {
                  this.object1Boundary(state_0, arg0Value, arg1Value, arg2Value);
                  return;
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
      }

      @CompilerDirectives.TruffleBoundary
      private void object1Boundary(int state_0, Object arg0Value, Object arg1Value, WriteElementNode arg2Value) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         try {
            InteropLibrary object1_fromInterop__ = ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
            this.doObject(arg0Value, arg1Value, arg2Value, object1_fromInterop__, this.object1_keysInterop_, this.object1_stringInterop_);
         } finally {
            encapsulating_.set(prev_);
         }
      }

      private void executeAndSpecialize(Object arg0Value, Object arg1Value, WriteElementNode arg2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arg1Value instanceof JSDynamicObject) {
               JSDynamicObject arg1Value_ = (JSDynamicObject)arg1Value;
               if (JSGuards.isJSObject(arg1Value_)) {
                  ObjectFunctionBuiltinsFactory.AssignPropertiesNodeGen.CopyPropertiesFromJSObjectData s0_ = super.insert(
                     new ObjectFunctionBuiltinsFactory.AssignPropertiesNodeGen.CopyPropertiesFromJSObjectData()
                  );
                  s0_.read_ = s0_.insertAccessor(ReadElementNode.create(this.context));
                  s0_.getOwnProperty_ = s0_.insertAccessor(JSGetOwnPropertyNode.create(false));
                  s0_.listSize_ = s0_.insertAccessor(ListSizeNode.create());
                  s0_.listGet_ = s0_.insertAccessor(ListGetNode.create());
                  s0_.classProfile_ = JSClassProfile.create();
                  VarHandle.storeStoreFence();
                  this.copyPropertiesFromJSObject_cache = s0_;
                  int var21;
                  this.state_0_ = var21 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  ObjectFunctionBuiltins.AssignPropertiesNode.copyPropertiesFromJSObject(
                     arg0Value, arg1Value_, arg2Value, s0_.read_, s0_.getOwnProperty_, s0_.listSize_, s0_.listGet_, s0_.classProfile_
                  );
                  return;
               }
            }

            if (exclude == 0) {
               int count1_ = 0;
               ObjectFunctionBuiltinsFactory.AssignPropertiesNodeGen.Object0Data s1_ = this.object0_cache;
               if ((state_0 & 2) != 0) {
                  while (s1_ != null && (!s1_.fromInterop_.accepts(arg1Value) || JSGuards.isJSObject(arg1Value))) {
                     s1_ = s1_.next_;
                     count1_++;
                  }
               }

               if (s1_ == null && !JSGuards.isJSObject(arg1Value) && count1_ < 5) {
                  s1_ = super.insert(new ObjectFunctionBuiltinsFactory.AssignPropertiesNodeGen.Object0Data(this.object0_cache));
                  s1_.fromInterop_ = s1_.insertAccessor(ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.create(arg1Value));
                  s1_.keysInterop_ = s1_.insertAccessor(ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
                  s1_.stringInterop_ = s1_.insertAccessor(ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
                  VarHandle.storeStoreFence();
                  this.object0_cache = s1_;
                  this.state_0_ = state_0 |= 2;
               }

               if (s1_ != null) {
                  lock.unlock();
                  hasLock = false;
                  this.doObject(arg0Value, arg1Value, arg2Value, s1_.fromInterop_, s1_.keysInterop_, s1_.stringInterop_);
                  return;
               }
            }

            InteropLibrary object1_fromInterop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               if (!JSGuards.isJSObject(arg1Value)) {
                  object1_fromInterop__ = ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arg1Value);
                  this.object1_keysInterop_ = super.insert(ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
                  this.object1_stringInterop_ = super.insert(ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
                  int var22;
                  this.exclude_ = var22 = exclude | 1;
                  this.object0_cache = null;
                  state_0 &= -3;
                  int var20;
                  this.state_0_ = var20 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  this.doObject(arg0Value, arg1Value, arg2Value, object1_fromInterop__, this.object1_keysInterop_, this.object1_stringInterop_);
                  return;
               }
            } finally {
               encapsulating_.set(prev_);
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
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
               ObjectFunctionBuiltinsFactory.AssignPropertiesNodeGen.Object0Data s1_ = this.object0_cache;
               if (s1_ == null || s1_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"copyPropertiesFromJSObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            ObjectFunctionBuiltinsFactory.AssignPropertiesNodeGen.CopyPropertiesFromJSObjectData s0_ = this.copyPropertiesFromJSObject_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.read_, s0_.getOwnProperty_, s0_.listSize_, s0_.listGet_, s0_.classProfile_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"doObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (ObjectFunctionBuiltinsFactory.AssignPropertiesNodeGen.Object0Data s1_ = this.object0_cache; s1_ != null; s1_ = s1_.next_) {
               cached.add(Arrays.asList(s1_.fromInterop_, s1_.keysInterop_, s1_.stringInterop_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"doObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.object1_keysInterop_, this.object1_stringInterop_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.AssignPropertiesNode create(JSContext context) {
         return new ObjectFunctionBuiltinsFactory.AssignPropertiesNodeGen(context);
      }

      @GeneratedBy(ObjectFunctionBuiltins.AssignPropertiesNode.class)
      private static final class CopyPropertiesFromJSObjectData extends Node {
         @Node.Child
         ReadElementNode read_;
         @Node.Child
         JSGetOwnPropertyNode getOwnProperty_;
         @Node.Child
         ListSizeNode listSize_;
         @Node.Child
         ListGetNode listGet_;
         @CompilerDirectives.CompilationFinal
         JSClassProfile classProfile_;

         CopyPropertiesFromJSObjectData() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(ObjectFunctionBuiltins.AssignPropertiesNode.class)
      private static final class Object0Data extends Node {
         @Node.Child
         ObjectFunctionBuiltinsFactory.AssignPropertiesNodeGen.Object0Data next_;
         @Node.Child
         InteropLibrary fromInterop_;
         @Node.Child
         InteropLibrary keysInterop_;
         @Node.Child
         InteropLibrary stringInterop_;

         Object0Data(ObjectFunctionBuiltinsFactory.AssignPropertiesNodeGen.Object0Data next_) {
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

   @GeneratedBy(ObjectFunctionBuiltins.ObjectAssignNode.class)
   public static final class ObjectAssignNodeGen extends ObjectFunctionBuiltins.ObjectAssignNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ObjectFunctionBuiltinsFactory.ObjectAssignNodeGen.AssignData assign_cache;

      private ObjectAssignNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0 && arguments1Value_ instanceof Object[]) {
            Object[] arguments1Value__ = (Object[])arguments1Value_;
            ObjectFunctionBuiltinsFactory.ObjectAssignNodeGen.AssignData s0_ = this.assign_cache;
            if (s0_ != null) {
               return this.assign(arguments0Value_, arguments1Value__, s0_.toObjectNode_, s0_.write_, s0_.assignProperties_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         Object var8;
         try {
            int state_0 = this.state_0_;
            if (!(arguments1Value instanceof Object[])) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            }

            Object[] arguments1Value_ = (Object[])arguments1Value;
            ObjectFunctionBuiltinsFactory.ObjectAssignNodeGen.AssignData s0_ = super.insert(new ObjectFunctionBuiltinsFactory.ObjectAssignNodeGen.AssignData());
            s0_.toObjectNode_ = s0_.insertAccessor(JSToObjectNode.createToObject(this.getContext()));
            s0_.write_ = s0_.insertAccessor(WriteElementNode.create(this.getContext(), true));
            s0_.assignProperties_ = s0_.insertAccessor(ObjectFunctionBuiltinsFactory.AssignPropertiesNodeGen.create(this.getContext()));
            VarHandle.storeStoreFence();
            this.assign_cache = s0_;
            int var12;
            this.state_0_ = var12 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            var8 = this.assign(arguments0Value, arguments1Value_, s0_.toObjectNode_, s0_.write_, s0_.assignProperties_);
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
         Object[] s = new Object[]{"assign", null, null};
         if (state_0 != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            ObjectFunctionBuiltinsFactory.ObjectAssignNodeGen.AssignData s0_ = this.assign_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.toObjectNode_, s0_.write_, s0_.assignProperties_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectAssignNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectFunctionBuiltinsFactory.ObjectAssignNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(ObjectFunctionBuiltins.ObjectAssignNode.class)
      private static final class AssignData extends Node {
         @Node.Child
         JSToObjectNode toObjectNode_;
         @Node.Child
         WriteElementNode write_;
         @Node.Child
         ObjectFunctionBuiltins.AssignPropertiesNode assignProperties_;

         AssignData() {
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

   @GeneratedBy(ObjectFunctionBuiltins.ObjectBindPropertiesNode.class)
   public static final class ObjectBindPropertiesNodeGen extends ObjectFunctionBuiltins.ObjectBindPropertiesNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private ObjectFunctionBuiltinsFactory.ObjectBindPropertiesNodeGen.BindProperties5Data bindProperties5_cache;
      @Node.Child
      private InteropLibrary bindProperties6_members_;

      private ObjectBindPropertiesNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @ExplodeLoop
      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 1) != 0 && !JSGuards.isJSObject(arguments0Value_)) {
            return this.bindPropertiesInvalidTarget(arguments0Value_, arguments1Value_);
         } else {
            if ((state_0 & 510) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if ((state_0 & 2) != 0 && arguments1Value_ instanceof JSDynamicObject) {
                  JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
                  if (JSGuards.isJSObject(arguments0Value__) && JSGuards.isJSDynamicObject(arguments1Value__)) {
                     return this.bindPropertiesDynamicObject(arguments0Value__, arguments1Value__);
                  }
               }

               if ((state_0 & 4) != 0 && arguments1Value_ instanceof Symbol) {
                  Symbol arguments1Value__ = (Symbol)arguments1Value_;
                  if (JSGuards.isJSObject(arguments0Value__)) {
                     return this.bindProperties(arguments0Value__, arguments1Value__);
                  }
               }

               if ((state_0 & 8) != 0 && arguments1Value_ instanceof TruffleString) {
                  TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
                  if (JSGuards.isJSObject(arguments0Value__)) {
                     return this.bindProperties(arguments0Value__, arguments1Value__);
                  }
               }

               if ((state_0 & 16) != 0 && arguments1Value_ instanceof SafeInteger) {
                  SafeInteger arguments1Value__ = (SafeInteger)arguments1Value_;
                  if (JSGuards.isJSObject(arguments0Value__)) {
                     return this.bindProperties(arguments0Value__, arguments1Value__);
                  }
               }

               if ((state_0 & 32) != 0 && arguments1Value_ instanceof BigInt) {
                  BigInt arguments1Value__ = (BigInt)arguments1Value_;
                  if (JSGuards.isJSObject(arguments0Value__)) {
                     return this.bindProperties(arguments0Value__, arguments1Value__);
                  }
               }

               if ((state_0 & 448) != 0) {
                  if ((state_0 & 64) != 0 && JSGuards.isJSObject(arguments0Value__) && !JSGuards.isTruffleObject(arguments1Value_)) {
                     return this.bindProperties(arguments0Value__, arguments1Value_);
                  }

                  if ((state_0 & 128) != 0) {
                     for (ObjectFunctionBuiltinsFactory.ObjectBindPropertiesNodeGen.BindProperties5Data s7_ = this.bindProperties5_cache;
                        s7_ != null;
                        s7_ = s7_.next_
                     ) {
                        if (s7_.interop_.accepts(arguments1Value_) && JSGuards.isJSObject(arguments0Value__) && JSGuards.isForeignObject(arguments1Value_)) {
                           return this.bindProperties(arguments0Value__, arguments1Value_, s7_.interop_, s7_.members_);
                        }
                     }
                  }

                  if ((state_0 & 256) != 0 && JSGuards.isJSObject(arguments0Value__) && JSGuards.isForeignObject(arguments1Value_)) {
                     return this.bindProperties6Boundary(state_0, arguments0Value__, arguments1Value_);
                  }
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @CompilerDirectives.TruffleBoundary
      private Object bindProperties6Boundary(int state_0, JSDynamicObject arguments0Value__, Object arguments1Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         JSDynamicObject var7;
         try {
            InteropLibrary bindProperties6_interop__ = ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments1Value_);
            var7 = this.bindProperties(arguments0Value__, arguments1Value_, bindProperties6_interop__, this.bindProperties6_members_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (!JSGuards.isJSObject(arguments0Value)) {
               int var28;
               this.state_0_ = var28 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.bindPropertiesInvalidTarget(arguments0Value, arguments1Value);
            } else {
               if (arguments0Value instanceof JSDynamicObject) {
                  JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                  if (arguments1Value instanceof JSDynamicObject) {
                     JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
                     if (JSGuards.isJSObject(arguments0Value_) && JSGuards.isJSDynamicObject(arguments1Value_)) {
                        int var27;
                        this.state_0_ = var27 = state_0 | 2;
                        lock.unlock();
                        hasLock = false;
                        return this.bindPropertiesDynamicObject(arguments0Value_, arguments1Value_);
                     }
                  }

                  if (arguments1Value instanceof Symbol) {
                     Symbol arguments1Value_ = (Symbol)arguments1Value;
                     if (JSGuards.isJSObject(arguments0Value_)) {
                        int var26;
                        this.state_0_ = var26 = state_0 | 4;
                        lock.unlock();
                        hasLock = false;
                        return this.bindProperties(arguments0Value_, arguments1Value_);
                     }
                  }

                  if (arguments1Value instanceof TruffleString) {
                     TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                     if (JSGuards.isJSObject(arguments0Value_)) {
                        int var25;
                        this.state_0_ = var25 = state_0 | 8;
                        lock.unlock();
                        hasLock = false;
                        return this.bindProperties(arguments0Value_, arguments1Value_);
                     }
                  }

                  if (arguments1Value instanceof SafeInteger) {
                     SafeInteger arguments1Value_ = (SafeInteger)arguments1Value;
                     if (JSGuards.isJSObject(arguments0Value_)) {
                        int var24;
                        this.state_0_ = var24 = state_0 | 16;
                        lock.unlock();
                        hasLock = false;
                        return this.bindProperties(arguments0Value_, arguments1Value_);
                     }
                  }

                  if (arguments1Value instanceof BigInt) {
                     BigInt arguments1Value_ = (BigInt)arguments1Value;
                     if (JSGuards.isJSObject(arguments0Value_)) {
                        int var23;
                        this.state_0_ = var23 = state_0 | 32;
                        lock.unlock();
                        hasLock = false;
                        return this.bindProperties(arguments0Value_, arguments1Value_);
                     }
                  }

                  if (JSGuards.isJSObject(arguments0Value_) && !JSGuards.isTruffleObject(arguments1Value)) {
                     int var22;
                     this.state_0_ = var22 = state_0 | 64;
                     lock.unlock();
                     hasLock = false;
                     return this.bindProperties(arguments0Value_, arguments1Value);
                  }

                  if (exclude == 0) {
                     int count7_ = 0;
                     ObjectFunctionBuiltinsFactory.ObjectBindPropertiesNodeGen.BindProperties5Data s7_ = this.bindProperties5_cache;
                     if ((state_0 & 128) != 0) {
                        while (
                           s7_ != null
                              && (
                                 !s7_.interop_.accepts(arguments1Value) || !JSGuards.isJSObject(arguments0Value_) || !JSGuards.isForeignObject(arguments1Value)
                              )
                        ) {
                           s7_ = s7_.next_;
                           count7_++;
                        }
                     }

                     if (s7_ == null && JSGuards.isJSObject(arguments0Value_) && JSGuards.isForeignObject(arguments1Value) && count7_ < 5) {
                        s7_ = super.insert(new ObjectFunctionBuiltinsFactory.ObjectBindPropertiesNodeGen.BindProperties5Data(this.bindProperties5_cache));
                        s7_.interop_ = s7_.insertAccessor(ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.create(arguments1Value));
                        s7_.members_ = s7_.insertAccessor(ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
                        VarHandle.storeStoreFence();
                        this.bindProperties5_cache = s7_;
                        this.state_0_ = state_0 |= 128;
                     }

                     if (s7_ != null) {
                        lock.unlock();
                        hasLock = false;
                        return this.bindProperties(arguments0Value_, arguments1Value, s7_.interop_, s7_.members_);
                     }
                  }

                  InteropLibrary bindProperties6_interop__ = null;
                  EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
                  Node prev_ = encapsulating_.set(this);

                  try {
                     if (JSGuards.isJSObject(arguments0Value_) && JSGuards.isForeignObject(arguments1Value)) {
                        bindProperties6_interop__ = ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments1Value);
                        this.bindProperties6_members_ = super.insert(ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
                        int var29;
                        this.exclude_ = var29 = exclude | 1;
                        this.bindProperties5_cache = null;
                        state_0 &= -129;
                        int var21;
                        this.state_0_ = var21 = state_0 | 256;
                        lock.unlock();
                        hasLock = false;
                        return this.bindProperties(arguments0Value_, arguments1Value, bindProperties6_interop__, this.bindProperties6_members_);
                     }
                  } finally {
                     encapsulating_.set(prev_);
                  }
               }

               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
               ObjectFunctionBuiltinsFactory.ObjectBindPropertiesNodeGen.BindProperties5Data s7_ = this.bindProperties5_cache;
               if (s7_ == null || s7_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[10];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"bindPropertiesInvalidTarget", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"bindPropertiesDynamicObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"bindProperties", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"bindProperties", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"bindProperties", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"bindProperties", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"bindProperties", null, null};
         if ((state_0 & 64) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         s = new Object[]{"bindProperties", null, null};
         if ((state_0 & 128) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (ObjectFunctionBuiltinsFactory.ObjectBindPropertiesNodeGen.BindProperties5Data s7_ = this.bindProperties5_cache; s7_ != null; s7_ = s7_.next_) {
               cached.add(Arrays.asList(s7_.interop_, s7_.members_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[8] = s;
         s = new Object[]{"bindProperties", null, null};
         if ((state_0 & 256) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.bindProperties6_members_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[9] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectBindPropertiesNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectFunctionBuiltinsFactory.ObjectBindPropertiesNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(ObjectFunctionBuiltins.ObjectBindPropertiesNode.class)
      private static final class BindProperties5Data extends Node {
         @Node.Child
         ObjectFunctionBuiltinsFactory.ObjectBindPropertiesNodeGen.BindProperties5Data next_;
         @Node.Child
         InteropLibrary interop_;
         @Node.Child
         InteropLibrary members_;

         BindProperties5Data(ObjectFunctionBuiltinsFactory.ObjectBindPropertiesNodeGen.BindProperties5Data next_) {
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

   @GeneratedBy(ObjectFunctionBuiltins.ObjectCreateNode.class)
   public static final class ObjectCreateNodeGen extends ObjectFunctionBuiltins.ObjectCreateNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private ObjectFunctionBuiltinsFactory.ObjectCreateNodeGen.CreateForeignNullOrInvalidPrototype0Data createForeignNullOrInvalidPrototype0_cache;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile createForeignNullOrInvalidPrototype1_isNull_;

      private ObjectCreateNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @ExplodeLoop
      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 7) != 0) {
            if ((state_0 & 1) != 0 && JSGuards.isJSNull(arguments0Value_)) {
               return this.createPrototypeNull(arguments0Value_, arguments1Value_);
            }

            if ((state_0 & 2) != 0) {
               for (ObjectFunctionBuiltinsFactory.ObjectCreateNodeGen.CreateForeignNullOrInvalidPrototype0Data s1_ = this.createForeignNullOrInvalidPrototype0_cache;
                  s1_ != null;
                  s1_ = s1_.next_
               ) {
                  if (s1_.interop_.accepts(arguments0Value_) && !JSGuards.isJSNull(arguments0Value_) && !JSGuards.isJSObject(arguments0Value_)) {
                     return this.createForeignNullOrInvalidPrototype(arguments0Value_, arguments1Value_, s1_.interop_, s1_.isNull_);
                  }
               }
            }

            if ((state_0 & 4) != 0 && !JSGuards.isJSNull(arguments0Value_) && !JSGuards.isJSObject(arguments0Value_)) {
               return this.createForeignNullOrInvalidPrototype1Boundary(state_0, arguments0Value_, arguments1Value_);
            }
         }

         if ((state_0 & 56) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if ((state_0 & 8) != 0 && arguments1Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
               if (JSGuards.isJSObject(arguments0Value__) && JSGuards.isJSObject(arguments1Value__)) {
                  return this.createObjectObject(arguments0Value__, arguments1Value__);
               }
            }

            if ((state_0 & 48) != 0) {
               if ((state_0 & 16) != 0 && JSGuards.isJSObject(arguments0Value__) && !JSGuards.isJSNull(arguments1Value_)) {
                  return this.createObjectNotNull(arguments0Value__, arguments1Value_);
               }

               if ((state_0 & 32) != 0 && JSGuards.isJSObject(arguments0Value__) && JSGuards.isJSNull(arguments1Value_)) {
                  return this.createObjectNull(arguments0Value__, arguments1Value_);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object createForeignNullOrInvalidPrototype1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         JSDynamicObject var7;
         try {
            InteropLibrary createForeignNullOrInvalidPrototype1_interop__ = ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var7 = this.createForeignNullOrInvalidPrototype(
               arguments0Value_, arguments1Value_, createForeignNullOrInvalidPrototype1_interop__, this.createForeignNullOrInvalidPrototype1_isNull_
            );
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (JSGuards.isJSNull(arguments0Value)) {
               int var24;
               this.state_0_ = var24 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.createPrototypeNull(arguments0Value, arguments1Value);
            } else {
               if (exclude == 0) {
                  int count1_ = 0;
                  ObjectFunctionBuiltinsFactory.ObjectCreateNodeGen.CreateForeignNullOrInvalidPrototype0Data s1_ = this.createForeignNullOrInvalidPrototype0_cache;
                  if ((state_0 & 2) != 0) {
                     while (
                        s1_ != null && (!s1_.interop_.accepts(arguments0Value) || JSGuards.isJSNull(arguments0Value) || JSGuards.isJSObject(arguments0Value))
                     ) {
                        s1_ = s1_.next_;
                        count1_++;
                     }
                  }

                  if (s1_ == null && !JSGuards.isJSNull(arguments0Value) && !JSGuards.isJSObject(arguments0Value) && count1_ < 5) {
                     s1_ = super.insert(
                        new ObjectFunctionBuiltinsFactory.ObjectCreateNodeGen.CreateForeignNullOrInvalidPrototype0Data(
                           this.createForeignNullOrInvalidPrototype0_cache
                        )
                     );
                     s1_.interop_ = s1_.insertAccessor(ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                     s1_.isNull_ = ConditionProfile.createBinaryProfile();
                     VarHandle.storeStoreFence();
                     this.createForeignNullOrInvalidPrototype0_cache = s1_;
                     this.state_0_ = state_0 |= 2;
                  }

                  if (s1_ != null) {
                     lock.unlock();
                     hasLock = false;
                     return this.createForeignNullOrInvalidPrototype(arguments0Value, arguments1Value, s1_.interop_, s1_.isNull_);
                  }
               }

               InteropLibrary createForeignNullOrInvalidPrototype1_interop__ = null;
               EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
               Node prev_ = encapsulating_.set(this);

               try {
                  if (!JSGuards.isJSNull(arguments0Value) && !JSGuards.isJSObject(arguments0Value)) {
                     createForeignNullOrInvalidPrototype1_interop__ = ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                     this.createForeignNullOrInvalidPrototype1_isNull_ = ConditionProfile.createBinaryProfile();
                     int var25;
                     this.exclude_ = var25 = exclude | 1;
                     this.createForeignNullOrInvalidPrototype0_cache = null;
                     state_0 &= -3;
                     int var23;
                     this.state_0_ = var23 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.createForeignNullOrInvalidPrototype(
                        arguments0Value, arguments1Value, createForeignNullOrInvalidPrototype1_interop__, this.createForeignNullOrInvalidPrototype1_isNull_
                     );
                  }
               } finally {
                  encapsulating_.set(prev_);
               }

               if (arguments0Value instanceof JSDynamicObject) {
                  JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                  if (arguments1Value instanceof JSDynamicObject) {
                     JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
                     if (JSGuards.isJSObject(arguments0Value_) && JSGuards.isJSObject(arguments1Value_)) {
                        int var21;
                        this.state_0_ = var21 = state_0 | 8;
                        lock.unlock();
                        hasLock = false;
                        return this.createObjectObject(arguments0Value_, arguments1Value_);
                     }
                  }

                  if (JSGuards.isJSObject(arguments0Value_) && !JSGuards.isJSNull(arguments1Value)) {
                     int var20;
                     this.state_0_ = var20 = state_0 | 16;
                     lock.unlock();
                     hasLock = false;
                     return this.createObjectNotNull(arguments0Value_, arguments1Value);
                  }

                  if (JSGuards.isJSObject(arguments0Value_) && JSGuards.isJSNull(arguments1Value)) {
                     int var19;
                     this.state_0_ = var19 = state_0 | 32;
                     lock.unlock();
                     hasLock = false;
                     return this.createObjectNull(arguments0Value_, arguments1Value);
                  }
               }

               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
               ObjectFunctionBuiltinsFactory.ObjectCreateNodeGen.CreateForeignNullOrInvalidPrototype0Data s1_ = this.createForeignNullOrInvalidPrototype0_cache;
               if (s1_ == null || s1_.next_ == null) {
                  return NodeCost.MONOMORPHIC;
               }
            }

            return NodeCost.POLYMORPHIC;
         }
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[7];
         data[0] = 0;
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"createPrototypeNull", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"createForeignNullOrInvalidPrototype", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (ObjectFunctionBuiltinsFactory.ObjectCreateNodeGen.CreateForeignNullOrInvalidPrototype0Data s1_ = this.createForeignNullOrInvalidPrototype0_cache;
               s1_ != null;
               s1_ = s1_.next_
            ) {
               cached.add(Arrays.asList(s1_.interop_, s1_.isNull_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"createForeignNullOrInvalidPrototype", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.createForeignNullOrInvalidPrototype1_isNull_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"createObjectObject", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"createObjectNotNull", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"createObjectNull", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectCreateNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectFunctionBuiltinsFactory.ObjectCreateNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(ObjectFunctionBuiltins.ObjectCreateNode.class)
      private static final class CreateForeignNullOrInvalidPrototype0Data extends Node {
         @Node.Child
         ObjectFunctionBuiltinsFactory.ObjectCreateNodeGen.CreateForeignNullOrInvalidPrototype0Data next_;
         @Node.Child
         InteropLibrary interop_;
         @CompilerDirectives.CompilationFinal
         ConditionProfile isNull_;

         CreateForeignNullOrInvalidPrototype0Data(ObjectFunctionBuiltinsFactory.ObjectCreateNodeGen.CreateForeignNullOrInvalidPrototype0Data next_) {
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

   @GeneratedBy(ObjectFunctionBuiltins.ObjectDefinePropertiesNode.class)
   public static final class ObjectDefinePropertiesNodeGen extends ObjectFunctionBuiltins.ObjectDefinePropertiesNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;
      @CompilerDirectives.CompilationFinal
      private int exclude_;

      private ObjectDefinePropertiesNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (arguments1Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
               if (JSGuards.isJSObject(arguments0Value__) && JSGuards.isJSObject(arguments1Value__)) {
                  return this.definePropertiesObjectObject(arguments0Value__, arguments1Value__);
               }
            }
         }

         if ((state_0 & 2) != 0) {
            return this.definePropertiesGeneric(arguments0Value_, arguments1Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0 && arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (arguments1Value instanceof JSDynamicObject) {
                  JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
                  if (JSGuards.isJSObject(arguments0Value_) && JSGuards.isJSObject(arguments1Value_)) {
                     int var15;
                     this.state_0_ = var15 = state_0 | 1;
                     lock.unlock();
                     hasLock = false;
                     return this.definePropertiesObjectObject(arguments0Value_, arguments1Value_);
                  }
               }
            }

            int var16;
            this.exclude_ = var16 = exclude | 1;
            state_0 &= -2;
            int var14;
            this.state_0_ = var14 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.definePropertiesGeneric(arguments0Value, arguments1Value);
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
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"definePropertiesObjectObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"definePropertiesGeneric", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectDefinePropertiesNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectFunctionBuiltinsFactory.ObjectDefinePropertiesNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ObjectFunctionBuiltins.ObjectDefinePropertyNode.class)
   public static final class ObjectDefinePropertyNodeGen extends ObjectFunctionBuiltins.ObjectDefinePropertyNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @Node.Child
      private JavaScriptNode arguments2_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;
      @CompilerDirectives.CompilationFinal
      private int exclude_;

      private ObjectDefinePropertyNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (arguments1Value_ instanceof TruffleString) {
                  TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
                  if (JSGuards.isJSObject(arguments0Value__)) {
                     return this.definePropertyJSObjectTString(arguments0Value__, arguments1Value__, arguments2Value_);
                  }
               }
            }

            if ((state_0 & 2) != 0) {
               return this.definePropertyGeneric(arguments0Value_, arguments1Value_, arguments2Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0 && arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (arguments1Value instanceof TruffleString) {
                  TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                  if (JSGuards.isJSObject(arguments0Value_)) {
                     int var16;
                     this.state_0_ = var16 = state_0 | 1;
                     lock.unlock();
                     hasLock = false;
                     return this.definePropertyJSObjectTString(arguments0Value_, arguments1Value_, arguments2Value);
                  }
               }
            }

            int var17;
            this.exclude_ = var17 = exclude | 1;
            state_0 &= -2;
            int var15;
            this.state_0_ = var15 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            return this.definePropertyGeneric(arguments0Value, arguments1Value, arguments2Value);
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
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         int exclude = this.exclude_;
         Object[] s = new Object[]{"definePropertyJSObjectTString", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"definePropertyGeneric", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectDefinePropertyNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectFunctionBuiltinsFactory.ObjectDefinePropertyNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ObjectFunctionBuiltins.ObjectFromEntriesNode.class)
   public static final class ObjectFromEntriesNodeGen extends ObjectFunctionBuiltins.ObjectFromEntriesNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private ObjectFromEntriesNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.entries(arguments0Value_);
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
         Object[] s = new Object[]{"entries", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectFromEntriesNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectFunctionBuiltinsFactory.ObjectFromEntriesNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorNode.class)
   public static final class ObjectGetOwnPropertyDescriptorNodeGen
      extends ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorNodeGen.GetForeignObject0Data getForeignObject0_cache;
      @Node.Child
      private ImportValueNode getForeignObject1_toJSType_;
      @Node.Child
      private TruffleString.ReadCharUTF16Node getForeignObject1_charAtNode_;

      private ObjectGetOwnPropertyDescriptorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
         this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
      }

      @ExplodeLoop
      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if (state_0 != 0) {
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (JSGuards.isJSObject(arguments0Value__)) {
                  return this.getJSObject(arguments0Value__, arguments1Value_);
               }
            }

            if ((state_0 & 14) != 0) {
               if ((state_0 & 2) != 0) {
                  for (ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorNodeGen.GetForeignObject0Data s1_ = this.getForeignObject0_cache;
                     s1_ != null;
                     s1_ = s1_.next_
                  ) {
                     if (s1_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                        return this.getForeignObject(arguments0Value_, arguments1Value_, s1_.interop_, s1_.toJSType_, s1_.charAtNode_);
                     }
                  }
               }

               if ((state_0 & 4) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                  return this.getForeignObject1Boundary(state_0, arguments0Value_, arguments1Value_);
               }

               if ((state_0 & 8) != 0 && !JSGuards.isJSObject(arguments0Value_) && !JSGuards.isForeignObject(arguments0Value_)) {
                  return this.getDefault(arguments0Value_, arguments1Value_);
               }
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object getForeignObject1Boundary(int state_0, Object arguments0Value_, Object arguments1Value_) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         JSDynamicObject var7;
         try {
            InteropLibrary getForeignObject1_interop__ = ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var7 = this.getForeignObject(
               arguments0Value_, arguments1Value_, getForeignObject1_interop__, this.getForeignObject1_toJSType_, this.getForeignObject1_charAtNode_
            );
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSObject(arguments0Value_)) {
                  int var22;
                  this.state_0_ = var22 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.getJSObject(arguments0Value_, arguments1Value);
               }
            }

            if (exclude == 0) {
               int count1_ = 0;
               ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorNodeGen.GetForeignObject0Data s1_ = this.getForeignObject0_cache;
               if ((state_0 & 2) != 0) {
                  while (s1_ != null && (!s1_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value))) {
                     s1_ = s1_.next_;
                     count1_++;
                  }
               }

               if (s1_ == null && JSGuards.isForeignObject(arguments0Value) && count1_ < 5) {
                  s1_ = super.insert(
                     new ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorNodeGen.GetForeignObject0Data(this.getForeignObject0_cache)
                  );
                  s1_.interop_ = s1_.insertAccessor(ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                  s1_.toJSType_ = s1_.insertAccessor(ImportValueNode.create());
                  s1_.charAtNode_ = s1_.insertAccessor(TruffleString.ReadCharUTF16Node.create());
                  VarHandle.storeStoreFence();
                  this.getForeignObject0_cache = s1_;
                  this.state_0_ = state_0 |= 2;
               }

               if (s1_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.getForeignObject(arguments0Value, arguments1Value, s1_.interop_, s1_.toJSType_, s1_.charAtNode_);
               }
            }

            InteropLibrary getForeignObject1_interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               if (JSGuards.isForeignObject(arguments0Value)) {
                  getForeignObject1_interop__ = ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                  this.getForeignObject1_toJSType_ = super.insert(ImportValueNode.create());
                  this.getForeignObject1_charAtNode_ = super.insert(TruffleString.ReadCharUTF16Node.create());
                  int var23;
                  this.exclude_ = var23 = exclude | 1;
                  this.getForeignObject0_cache = null;
                  state_0 &= -3;
                  int var21;
                  this.state_0_ = var21 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.getForeignObject(
                     arguments0Value, arguments1Value, getForeignObject1_interop__, this.getForeignObject1_toJSType_, this.getForeignObject1_charAtNode_
                  );
               }
            } finally {
               encapsulating_.set(prev_);
            }

            if (JSGuards.isJSObject(arguments0Value) || JSGuards.isForeignObject(arguments0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            } else {
               int var19;
               this.state_0_ = var19 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.getDefault(arguments0Value, arguments1Value);
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
               ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorNodeGen.GetForeignObject0Data s1_ = this.getForeignObject0_cache;
               if (s1_ == null || s1_.next_ == null) {
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
         Object[] s = new Object[]{"getJSObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"getForeignObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorNodeGen.GetForeignObject0Data s1_ = this.getForeignObject0_cache;
               s1_ != null;
               s1_ = s1_.next_
            ) {
               cached.add(Arrays.asList(s1_.interop_, s1_.toJSType_, s1_.charAtNode_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"getForeignObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.getForeignObject1_toJSType_, this.getForeignObject1_charAtNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"getDefault", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorNode.class)
      private static final class GetForeignObject0Data extends Node {
         @Node.Child
         ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorNodeGen.GetForeignObject0Data next_;
         @Node.Child
         InteropLibrary interop_;
         @Node.Child
         ImportValueNode toJSType_;
         @Node.Child
         TruffleString.ReadCharUTF16Node charAtNode_;

         GetForeignObject0Data(ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorNodeGen.GetForeignObject0Data next_) {
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

   @GeneratedBy(ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorsNode.class)
   public static final class ObjectGetOwnPropertyDescriptorsNodeGen
      extends ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorsNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private volatile int exclude_;
      @Node.Child
      private ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetJSObjectData getJSObject_cache;
      @Node.Child
      private ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetForeignObject0Data getForeignObject0_cache;
      @Node.Child
      private ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetForeignObject1Data getForeignObject1_cache;
      @Node.Child
      private ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorsNode getDefault_recursive_;

      private ObjectGetOwnPropertyDescriptorsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         super(context, builtin);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @ExplodeLoop
      @Override
      protected JSDynamicObject executeEvaluated(Object arguments0Value) {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0 && arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetJSObjectData s0_ = this.getJSObject_cache;
            if (s0_ != null && JSGuards.isJSObject(arguments0Value_)) {
               return this.getJSObject(arguments0Value_, s0_.getOwnPropertyNode_, s0_.listSize_, s0_.listGet_, s0_.classProfile_);
            }
         }

         if ((state_0 & 14) != 0) {
            if ((state_0 & 2) != 0) {
               for (ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetForeignObject0Data s1_ = this.getForeignObject0_cache;
                  s1_ != null;
                  s1_ = s1_.next_
               ) {
                  if (s1_.interop_.accepts(arguments0Value) && JSGuards.isForeignObject(arguments0Value)) {
                     return this.getForeignObject(arguments0Value, s1_.interop_, s1_.members_, s1_.toJSType_, s1_.errorBranch_);
                  }
               }
            }

            if ((state_0 & 4) != 0) {
               ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetForeignObject1Data s2_ = this.getForeignObject1_cache;
               if (s2_ != null && JSGuards.isForeignObject(arguments0Value)) {
                  return this.getForeignObject1Boundary(state_0, s2_, arguments0Value);
               }
            }

            if ((state_0 & 8) != 0 && !JSGuards.isJSObject(arguments0Value) && !JSGuards.isForeignObject(arguments0Value)) {
               return this.getDefault(arguments0Value, this.getDefault_recursive_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value);
      }

      @CompilerDirectives.TruffleBoundary
      private JSDynamicObject getForeignObject1Boundary(
         int state_0, ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetForeignObject1Data s2_, Object arguments0Value
      ) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         JSDynamicObject var7;
         try {
            InteropLibrary interop__ = ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
            var7 = this.getForeignObject(arguments0Value, interop__, s2_.members_, s2_.toJSType_, s2_.errorBranch_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      @ExplodeLoop
      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetJSObjectData s0_ = this.getJSObject_cache;
            if (s0_ != null && JSGuards.isJSObject(arguments0Value__)) {
               return this.getJSObject(arguments0Value__, s0_.getOwnPropertyNode_, s0_.listSize_, s0_.listGet_, s0_.classProfile_);
            }
         }

         if ((state_0 & 14) != 0) {
            if ((state_0 & 2) != 0) {
               for (ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetForeignObject0Data s1_ = this.getForeignObject0_cache;
                  s1_ != null;
                  s1_ = s1_.next_
               ) {
                  if (s1_.interop_.accepts(arguments0Value_) && JSGuards.isForeignObject(arguments0Value_)) {
                     return this.getForeignObject(arguments0Value_, s1_.interop_, s1_.members_, s1_.toJSType_, s1_.errorBranch_);
                  }
               }
            }

            if ((state_0 & 4) != 0) {
               ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetForeignObject1Data s2_ = this.getForeignObject1_cache;
               if (s2_ != null && JSGuards.isForeignObject(arguments0Value_)) {
                  return this.getForeignObject1Boundary0(state_0, s2_, arguments0Value_);
               }
            }

            if ((state_0 & 8) != 0 && !JSGuards.isJSObject(arguments0Value_) && !JSGuards.isForeignObject(arguments0Value_)) {
               return this.getDefault(arguments0Value_, this.getDefault_recursive_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }

      @CompilerDirectives.TruffleBoundary
      private Object getForeignObject1Boundary0(
         int state_0, ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetForeignObject1Data s2_, Object arguments0Value_
      ) {
         EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
         Node prev_ = encapsulating_.set(this);

         JSDynamicObject var7;
         try {
            InteropLibrary interop__ = ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value_);
            var7 = this.getForeignObject(arguments0Value_, interop__, s2_.members_, s2_.toJSType_, s2_.errorBranch_);
         } finally {
            encapsulating_.set(prev_);
         }

         return var7;
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSObject(arguments0Value_)) {
                  ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetJSObjectData s0_ = super.insert(
                     new ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetJSObjectData()
                  );
                  s0_.getOwnPropertyNode_ = s0_.insertAccessor(JSGetOwnPropertyNode.create());
                  s0_.listSize_ = s0_.insertAccessor(ListSizeNode.create());
                  s0_.listGet_ = s0_.insertAccessor(ListGetNode.create());
                  s0_.classProfile_ = JSClassProfile.create();
                  VarHandle.storeStoreFence();
                  this.getJSObject_cache = s0_;
                  int var22;
                  this.state_0_ = var22 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.getJSObject(arguments0Value_, s0_.getOwnPropertyNode_, s0_.listSize_, s0_.listGet_, s0_.classProfile_);
               }
            }

            if (exclude == 0) {
               int count1_ = 0;
               ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetForeignObject0Data s1_ = this.getForeignObject0_cache;
               if ((state_0 & 2) != 0) {
                  while (s1_ != null && (!s1_.interop_.accepts(arguments0Value) || !JSGuards.isForeignObject(arguments0Value))) {
                     s1_ = s1_.next_;
                     count1_++;
                  }
               }

               if (s1_ == null && JSGuards.isForeignObject(arguments0Value) && count1_ < 5) {
                  s1_ = super.insert(
                     new ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetForeignObject0Data(this.getForeignObject0_cache)
                  );
                  s1_.interop_ = s1_.insertAccessor(ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.create(arguments0Value));
                  s1_.members_ = s1_.insertAccessor(ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
                  s1_.toJSType_ = s1_.insertAccessor(ImportValueNode.create());
                  s1_.errorBranch_ = BranchProfile.create();
                  VarHandle.storeStoreFence();
                  this.getForeignObject0_cache = s1_;
                  this.state_0_ = state_0 |= 2;
               }

               if (s1_ != null) {
                  lock.unlock();
                  hasLock = false;
                  return this.getForeignObject(arguments0Value, s1_.interop_, s1_.members_, s1_.toJSType_, s1_.errorBranch_);
               }
            }

            InteropLibrary interop__ = null;
            EncapsulatingNodeReference encapsulating_ = EncapsulatingNodeReference.getCurrent();
            Node prev_ = encapsulating_.set(this);

            try {
               if (JSGuards.isForeignObject(arguments0Value)) {
                  ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetForeignObject1Data s2_ = super.insert(
                     new ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetForeignObject1Data()
                  );
                  interop__ = ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.getUncached(arguments0Value);
                  s2_.members_ = s2_.insertAccessor(ObjectFunctionBuiltinsFactory.INTEROP_LIBRARY_.createDispatched(5));
                  s2_.toJSType_ = s2_.insertAccessor(ImportValueNode.create());
                  s2_.errorBranch_ = BranchProfile.create();
                  VarHandle.storeStoreFence();
                  this.getForeignObject1_cache = s2_;
                  int var23;
                  this.exclude_ = var23 = exclude | 1;
                  this.getForeignObject0_cache = null;
                  state_0 &= -3;
                  int var21;
                  this.state_0_ = var21 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.getForeignObject(arguments0Value, interop__, s2_.members_, s2_.toJSType_, s2_.errorBranch_);
               }
            } finally {
               encapsulating_.set(prev_);
            }

            if (JSGuards.isJSObject(arguments0Value) || JSGuards.isForeignObject(arguments0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
            } else {
               this.getDefault_recursive_ = super.insert(this.createRecursive());
               int var19;
               this.state_0_ = var19 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.getDefault(arguments0Value, this.getDefault_recursive_);
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
               ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetForeignObject0Data s1_ = this.getForeignObject0_cache;
               if (s1_ == null || s1_.next_ == null) {
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
         Object[] s = new Object[]{"getJSObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetJSObjectData s0_ = this.getJSObject_cache;
            if (s0_ != null) {
               cached.add(Arrays.asList(s0_.getOwnPropertyNode_, s0_.listSize_, s0_.listGet_, s0_.classProfile_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"getForeignObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();

            for (ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetForeignObject0Data s1_ = this.getForeignObject0_cache;
               s1_ != null;
               s1_ = s1_.next_
            ) {
               cached.add(Arrays.asList(s1_.interop_, s1_.members_, s1_.toJSType_, s1_.errorBranch_));
            }

            s[2] = cached;
         } else if (exclude != 0) {
            s[1] = (byte)2;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"getForeignObject", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetForeignObject1Data s2_ = this.getForeignObject1_cache;
            if (s2_ != null) {
               cached.add(Arrays.asList(s2_.members_, s2_.toJSType_, s2_.errorBranch_));
            }

            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"getDefault", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.getDefault_recursive_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen(context, builtin, arguments);
      }

      @GeneratedBy(ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorsNode.class)
      private static final class GetForeignObject0Data extends Node {
         @Node.Child
         ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetForeignObject0Data next_;
         @Node.Child
         InteropLibrary interop_;
         @Node.Child
         InteropLibrary members_;
         @Node.Child
         ImportValueNode toJSType_;
         @CompilerDirectives.CompilationFinal
         BranchProfile errorBranch_;

         GetForeignObject0Data(ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyDescriptorsNodeGen.GetForeignObject0Data next_) {
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

      @GeneratedBy(ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorsNode.class)
      private static final class GetForeignObject1Data extends Node {
         @Node.Child
         InteropLibrary members_;
         @Node.Child
         ImportValueNode toJSType_;
         @CompilerDirectives.CompilationFinal
         BranchProfile errorBranch_;

         GetForeignObject1Data() {
         }

         @Override
         public NodeCost getCost() {
            return NodeCost.NONE;
         }

         <T extends Node> T insertAccessor(T node) {
            return super.insert(node);
         }
      }

      @GeneratedBy(ObjectFunctionBuiltins.ObjectGetOwnPropertyDescriptorsNode.class)
      private static final class GetJSObjectData extends Node {
         @Node.Child
         JSGetOwnPropertyNode getOwnPropertyNode_;
         @Node.Child
         ListSizeNode listSize_;
         @Node.Child
         ListGetNode listGet_;
         @CompilerDirectives.CompilationFinal
         JSClassProfile classProfile_;

         GetJSObjectData() {
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

   @GeneratedBy(ObjectFunctionBuiltins.ObjectGetOwnPropertyNamesOrSymbolsNode.class)
   public static final class ObjectGetOwnPropertyNamesOrSymbolsNodeGen
      extends ObjectFunctionBuiltins.ObjectGetOwnPropertyNamesOrSymbolsNode
      implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private JSClassProfile jsclassProfile;
      @Node.Child
      private ListSizeNode listSize;
      @Node.Child
      private EnumerableOwnPropertyNamesNode getForeignObjectNames_enumerableOwnPropertyNamesNode_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile getForeignObjectNames_hasElements_;

      private ObjectGetOwnPropertyNamesOrSymbolsNodeGen(JSContext context, JSBuiltin builtin, boolean symbols, JavaScriptNode[] arguments) {
         super(context, builtin, symbols);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (JSGuards.isJSObject(arguments0Value__)) {
               return this.getJSObject(arguments0Value__, this.jsclassProfile, this.listSize);
            }
         }

         if ((state_0 & 14) != 0) {
            if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments0Value_) && !JSGuards.isForeignObject(arguments0Value_)) {
               return this.getDefault(arguments0Value_, this.jsclassProfile, this.listSize);
            }

            if ((state_0 & 4) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
               assert this.symbols;

               return this.getForeignObjectSymbols(arguments0Value_);
            }

            if ((state_0 & 8) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
               assert !this.symbols;

               return this.getForeignObjectNames(
                  arguments0Value_, this.getForeignObjectNames_enumerableOwnPropertyNamesNode_, this.getForeignObjectNames_hasElements_
               );
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject arguments0Value_;
         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSObject(arguments0Value_)) {
                  this.jsclassProfile = this.jsclassProfile == null ? JSClassProfile.create() : this.jsclassProfile;
                  this.listSize = super.insert(this.listSize == null ? ListSizeNode.create() : this.listSize);
                  int var13;
                  this.state_0_ = var13 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.getJSObject(arguments0Value_, this.jsclassProfile, this.listSize);
               }
            }

            if (JSGuards.isJSObject(arguments0Value) || JSGuards.isForeignObject(arguments0Value)) {
               if (!JSGuards.isForeignObject(arguments0Value) || !this.symbols) {
                  if (!JSGuards.isForeignObject(arguments0Value) || this.symbols) {
                     throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
                  }

                  this.getForeignObjectNames_enumerableOwnPropertyNamesNode_ = super.insert(EnumerableOwnPropertyNamesNode.createKeys(this.getContext()));
                  this.getForeignObjectNames_hasElements_ = ConditionProfile.create();
                  int var12;
                  this.state_0_ = var12 = state_0 | 8;
                  lock.unlock();
                  hasLock = false;
                  return this.getForeignObjectNames(
                     arguments0Value, this.getForeignObjectNames_enumerableOwnPropertyNamesNode_, this.getForeignObjectNames_hasElements_
                  );
               }

               int var11;
               this.state_0_ = var11 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.getForeignObjectSymbols(arguments0Value);
            }

            this.jsclassProfile = this.jsclassProfile == null ? JSClassProfile.create() : this.jsclassProfile;
            this.listSize = super.insert(this.listSize == null ? ListSizeNode.create() : this.listSize);
            int var10;
            this.state_0_ = var10 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            arguments0Value_ = this.getDefault(arguments0Value, this.jsclassProfile, this.listSize);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return arguments0Value_;
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
         Object[] data = new Object[5];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"getJSObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.jsclassProfile, this.listSize));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"getDefault", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.jsclassProfile, this.listSize));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"getForeignObjectSymbols", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"getForeignObjectNames", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.getForeignObjectNames_enumerableOwnPropertyNamesNode_, this.getForeignObjectNames_hasElements_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectGetOwnPropertyNamesOrSymbolsNode create(
         JSContext context, JSBuiltin builtin, boolean symbols, JavaScriptNode[] arguments
      ) {
         return new ObjectFunctionBuiltinsFactory.ObjectGetOwnPropertyNamesOrSymbolsNodeGen(context, builtin, symbols, arguments);
      }
   }

   @GeneratedBy(ObjectFunctionBuiltins.ObjectGetPrototypeOfNode.class)
   public static final class ObjectGetPrototypeOfNodeGen extends ObjectFunctionBuiltins.ObjectGetPrototypeOfNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @CompilerDirectives.CompilationFinal
      private ConditionProfile getPrototypeOfNonObject_isForeignProfile_;
      @Node.Child
      private GetPrototypeNode getPrototypeOfJSObject_getPrototypeNode_;

      private ObjectGetPrototypeOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && !JSGuards.isJSObject(arguments0Value_)) {
            return this.getPrototypeOfNonObject(arguments0Value_, this.getPrototypeOfNonObject_isForeignProfile_);
         } else {
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
               if (JSGuards.isJSObject(arguments0Value__)) {
                  return this.getPrototypeOfJSObject(arguments0Value__, this.getPrototypeOfJSObject_getPrototypeNode_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         JSDynamicObject arguments0Value_;
         try {
            int state_0 = this.state_0_;
            if (JSGuards.isJSObject(arguments0Value)) {
               if (arguments0Value instanceof JSDynamicObject) {
                  arguments0Value_ = (JSDynamicObject)arguments0Value;
                  if (JSGuards.isJSObject(arguments0Value_)) {
                     this.getPrototypeOfJSObject_getPrototypeNode_ = super.insert(GetPrototypeNode.create());
                     int var11;
                     this.state_0_ = var11 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.getPrototypeOfJSObject(arguments0Value_, this.getPrototypeOfJSObject_getPrototypeNode_);
                  }
               }

               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
            }

            this.getPrototypeOfNonObject_isForeignProfile_ = ConditionProfile.createBinaryProfile();
            int var10;
            this.state_0_ = var10 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            arguments0Value_ = this.getPrototypeOfNonObject(arguments0Value, this.getPrototypeOfNonObject_isForeignProfile_);
         } finally {
            if (hasLock) {
               lock.unlock();
            }
         }

         return arguments0Value_;
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
         Object[] s = new Object[]{"getPrototypeOfNonObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.getPrototypeOfNonObject_isForeignProfile_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"getPrototypeOfJSObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.getPrototypeOfJSObject_getPrototypeNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectGetPrototypeOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectFunctionBuiltinsFactory.ObjectGetPrototypeOfNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ObjectFunctionBuiltins.ObjectHasOwnNode.class)
   public static final class ObjectHasOwnNodeGen extends ObjectFunctionBuiltins.ObjectHasOwnNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;

      private ObjectHasOwnNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.hasOwn(arguments0Value_, arguments1Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         return this.hasOwn(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"hasOwn", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectHasOwnNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectFunctionBuiltinsFactory.ObjectHasOwnNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ObjectFunctionBuiltins.ObjectIsExtensibleNode.class)
   public static final class ObjectIsExtensibleNodeGen extends ObjectFunctionBuiltins.ObjectIsExtensibleNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private IsExtensibleNode isExtensibleObject_isExtensibleNode_;

      private ObjectIsExtensibleNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (JSGuards.isJSObject(arguments0Value__)) {
               return this.isExtensibleObject(arguments0Value__, this.isExtensibleObject_isExtensibleNode_);
            }
         }

         if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments0Value_)) {
            return this.isExtensibleNonObject(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (JSGuards.isJSObject(arguments0Value__)) {
               return this.isExtensibleObject(arguments0Value__, this.isExtensibleObject_isExtensibleNode_);
            }
         }

         if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments0Value_)) {
            return this.isExtensibleNonObject(arguments0Value_);
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

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSObject(arguments0Value_)) {
                  this.isExtensibleObject_isExtensibleNode_ = super.insert(IsExtensibleNode.create());
                  int var11;
                  this.state_0_ = var11 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.isExtensibleObject(arguments0Value_, this.isExtensibleObject_isExtensibleNode_);
               }
            }

            if (JSGuards.isJSObject(arguments0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
            } else {
               int var10;
               this.state_0_ = var10 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.isExtensibleNonObject(arguments0Value);
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
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"isExtensibleObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.isExtensibleObject_isExtensibleNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"isExtensibleNonObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectIsExtensibleNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectFunctionBuiltinsFactory.ObjectIsExtensibleNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ObjectFunctionBuiltins.ObjectIsNode.class)
   public static final class ObjectIsNodeGen extends ObjectFunctionBuiltins.ObjectIsNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private JSIdenticalNode isNumberNumber_doIdenticalNode_;
      @Node.Child
      private JSIdenticalNode isObject_doIdenticalNode_;

      private ObjectIsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         if ((state_0 & 14) == 0 && (state_0 & 15) != 0) {
            return this.execute_int_int0(state_0, frameValue);
         } else {
            return (state_0 & 13) == 0 && (state_0 & 15) != 0 ? this.execute_double_double1(state_0, frameValue) : this.execute_generic2(state_0, frameValue);
         }
      }

      private Object execute_int_int0(int state_0, VirtualFrame frameValue) {
         int arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments1Value = this.arguments1_.execute(frameValue);
            return this.executeAndSpecialize(var7.getResult(), arguments1Value);
         }

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var6) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, var6.getResult());
         }

         assert (state_0 & 1) != 0;

         return this.isInt(arguments0Value_, arguments1Value_);
      }

      private Object execute_double_double1(int state_0, VirtualFrame frameValue) {
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
         } catch (UnexpectedResultException var15) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments1Value = this.arguments1_.execute(frameValue);
            return this.executeAndSpecialize(var15.getResult(), arguments1Value);
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
         } catch (UnexpectedResultException var14) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(
               (state_0 & 208) == 0 && (state_0 & 15) != 0
                  ? arguments0Value_int
                  : ((state_0 & 112) == 0 && (state_0 & 15) != 0 ? arguments0Value_long : arguments0Value_),
               var14.getResult()
            );
         }

         assert (state_0 & 2) != 0;

         return this.isDouble(arguments0Value_, arguments1Value_);
      }

      private Object execute_generic2(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
            int arguments0Value__ = (Integer)arguments0Value_;
            if (arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               return this.isInt(arguments0Value__, arguments1Value__);
            }
         }

         if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 240) >>> 4, arguments0Value_)) {
            double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 240) >>> 4, arguments0Value_);
            if (JSTypesGen.isImplicitDouble((state_0 & 3840) >>> 8, arguments1Value_)) {
               double arguments1Value__ = JSTypesGen.asImplicitDouble((state_0 & 3840) >>> 8, arguments1Value_);
               return this.isDouble(arguments0Value__, arguments1Value__);
            }
         }

         if ((state_0 & 4) != 0 && arguments0Value_ instanceof Number) {
            Number arguments0Value__ = (Number)arguments0Value_;
            if (arguments1Value_ instanceof Number) {
               Number arguments1Value__ = (Number)arguments1Value_;
               if (this.isNumberNumber(arguments0Value__, arguments1Value__)) {
                  return this.isNumberNumber(arguments0Value__, arguments1Value__, this.isNumberNumber_doIdenticalNode_);
               }
            }
         }

         if ((state_0 & 8) != 0 && !this.isNumberNumber(arguments0Value_, arguments1Value_)) {
            return this.isObject(arguments0Value_, arguments1Value_, this.isObject_doIdenticalNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         if ((state_0 & 14) == 0 && (state_0 & 15) != 0) {
            return this.executeBoolean_int_int3(state_0, frameValue);
         } else {
            return (state_0 & 13) == 0 && (state_0 & 15) != 0
               ? this.executeBoolean_double_double4(state_0, frameValue)
               : this.executeBoolean_generic5(state_0, frameValue);
         }
      }

      private boolean executeBoolean_int_int3(int state_0, VirtualFrame frameValue) {
         int arguments0Value_;
         try {
            arguments0Value_ = this.arguments0_.executeInt(frameValue);
         } catch (UnexpectedResultException var7) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments1Value = this.arguments1_.execute(frameValue);
            return this.executeAndSpecialize(var7.getResult(), arguments1Value);
         }

         int arguments1Value_;
         try {
            arguments1Value_ = this.arguments1_.executeInt(frameValue);
         } catch (UnexpectedResultException var6) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, var6.getResult());
         }

         assert (state_0 & 1) != 0;

         return this.isInt(arguments0Value_, arguments1Value_);
      }

      private boolean executeBoolean_double_double4(int state_0, VirtualFrame frameValue) {
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
         } catch (UnexpectedResultException var15) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            Object arguments1Value = this.arguments1_.execute(frameValue);
            return this.executeAndSpecialize(var15.getResult(), arguments1Value);
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
         } catch (UnexpectedResultException var14) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(
               (state_0 & 208) == 0 && (state_0 & 15) != 0
                  ? arguments0Value_int
                  : ((state_0 & 112) == 0 && (state_0 & 15) != 0 ? arguments0Value_long : arguments0Value_),
               var14.getResult()
            );
         }

         assert (state_0 & 2) != 0;

         return this.isDouble(arguments0Value_, arguments1Value_);
      }

      private boolean executeBoolean_generic5(int state_0, VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof Integer) {
            int arguments0Value__ = (Integer)arguments0Value_;
            if (arguments1Value_ instanceof Integer) {
               int arguments1Value__ = (Integer)arguments1Value_;
               return this.isInt(arguments0Value__, arguments1Value__);
            }
         }

         if ((state_0 & 2) != 0 && JSTypesGen.isImplicitDouble((state_0 & 240) >>> 4, arguments0Value_)) {
            double arguments0Value__ = JSTypesGen.asImplicitDouble((state_0 & 240) >>> 4, arguments0Value_);
            if (JSTypesGen.isImplicitDouble((state_0 & 3840) >>> 8, arguments1Value_)) {
               double arguments1Value__ = JSTypesGen.asImplicitDouble((state_0 & 3840) >>> 8, arguments1Value_);
               return this.isDouble(arguments0Value__, arguments1Value__);
            }
         }

         if ((state_0 & 4) != 0 && arguments0Value_ instanceof Number) {
            Number arguments0Value__ = (Number)arguments0Value_;
            if (arguments1Value_ instanceof Number) {
               Number arguments1Value__ = (Number)arguments1Value_;
               if (this.isNumberNumber(arguments0Value__, arguments1Value__)) {
                  return this.isNumberNumber(arguments0Value__, arguments1Value__, this.isNumberNumber_doIdenticalNode_);
               }
            }
         }

         if ((state_0 & 8) != 0 && !this.isNumberNumber(arguments0Value_, arguments1Value_)) {
            return this.isObject(arguments0Value_, arguments1Value_, this.isObject_doIdenticalNode_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      private boolean executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof Integer) {
               int arguments0Value_ = (Integer)arguments0Value;
               if (arguments1Value instanceof Integer) {
                  int arguments1Value_ = (Integer)arguments1Value;
                  int var21;
                  this.state_0_ = var21 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.isInt(arguments0Value_, arguments1Value_);
               }
            }

            int doubleCast0;
            if ((doubleCast0 = JSTypesGen.specializeImplicitDouble(arguments0Value)) != 0) {
               double arguments0Value_ = JSTypesGen.asImplicitDouble(doubleCast0, arguments0Value);
               int doubleCast1;
               if ((doubleCast1 = JSTypesGen.specializeImplicitDouble(arguments1Value)) != 0) {
                  double arguments1Value_ = JSTypesGen.asImplicitDouble(doubleCast1, arguments1Value);
                  state_0 |= doubleCast0 << 4;
                  state_0 |= doubleCast1 << 8;
                  int var20;
                  this.state_0_ = var20 = state_0 | 2;
                  lock.unlock();
                  hasLock = false;
                  return this.isDouble(arguments0Value_, arguments1Value_);
               }
            }

            if (arguments0Value instanceof Number) {
               Number arguments0Value_ = (Number)arguments0Value;
               if (arguments1Value instanceof Number) {
                  Number arguments1Value_ = (Number)arguments1Value;
                  if (this.isNumberNumber(arguments0Value_, arguments1Value_)) {
                     this.isNumberNumber_doIdenticalNode_ = super.insert(JSIdenticalNode.createSameValue());
                     int var17;
                     this.state_0_ = var17 = state_0 | 4;
                     lock.unlock();
                     hasLock = false;
                     return this.isNumberNumber(arguments0Value_, arguments1Value_, this.isNumberNumber_doIdenticalNode_);
                  }
               }
            }

            if (this.isNumberNumber(arguments0Value, arguments1Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
            } else {
               this.isObject_doIdenticalNode_ = super.insert(JSIdenticalNode.createSameValue());
               int var16;
               this.state_0_ = var16 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.isObject(arguments0Value, arguments1Value, this.isObject_doIdenticalNode_);
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
         Object[] s = new Object[]{"isInt", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"isDouble", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"isNumberNumber", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.isNumberNumber_doIdenticalNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"isObject", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.isObject_doIdenticalNode_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectIsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectFunctionBuiltinsFactory.ObjectIsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ObjectFunctionBuiltins.ObjectKeysNode.class)
   public static final class ObjectKeysNodeGen extends ObjectFunctionBuiltins.ObjectKeysNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ObjectKeysNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (JSGuards.isJSDynamicObject(arguments0Value__)) {
               return this.keysDynamicObject(arguments0Value__);
            }
         }

         if ((state_0 & 2) != 0 && arguments0Value_ instanceof Symbol) {
            Symbol arguments0Value__ = (Symbol)arguments0Value_;
            return this.keysSymbol(arguments0Value__);
         } else if ((state_0 & 4) != 0 && arguments0Value_ instanceof TruffleString) {
            TruffleString arguments0Value__ = (TruffleString)arguments0Value_;
            return this.keysString(arguments0Value__);
         } else if ((state_0 & 8) != 0 && arguments0Value_ instanceof SafeInteger) {
            SafeInteger arguments0Value__ = (SafeInteger)arguments0Value_;
            return this.keysSafeInt(arguments0Value__);
         } else if ((state_0 & 16) != 0 && arguments0Value_ instanceof BigInt) {
            BigInt arguments0Value__ = (BigInt)arguments0Value_;
            return this.keysBigInt(arguments0Value__);
         } else {
            if ((state_0 & 96) != 0) {
               if ((state_0 & 32) != 0 && !JSGuards.isTruffleObject(arguments0Value_)) {
                  return this.keysOther(arguments0Value_);
               }

               if ((state_0 & 64) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
                  return this.keysForeign(arguments0Value_);
               }
            }

            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if (JSGuards.isJSDynamicObject(arguments0Value_)) {
               int var10;
               this.state_0_ = var10 = state_0 | 1;
               return this.keysDynamicObject(arguments0Value_);
            }
         }

         if (arguments0Value instanceof Symbol) {
            Symbol arguments0Value_ = (Symbol)arguments0Value;
            int var9;
            this.state_0_ = var9 = state_0 | 2;
            return this.keysSymbol(arguments0Value_);
         } else if (arguments0Value instanceof TruffleString) {
            TruffleString arguments0Value_ = (TruffleString)arguments0Value;
            int var8;
            this.state_0_ = var8 = state_0 | 4;
            return this.keysString(arguments0Value_);
         } else if (arguments0Value instanceof SafeInteger) {
            SafeInteger arguments0Value_ = (SafeInteger)arguments0Value;
            int var7;
            this.state_0_ = var7 = state_0 | 8;
            return this.keysSafeInt(arguments0Value_);
         } else if (arguments0Value instanceof BigInt) {
            BigInt arguments0Value_ = (BigInt)arguments0Value;
            int var6;
            this.state_0_ = var6 = state_0 | 16;
            return this.keysBigInt(arguments0Value_);
         } else if (!JSGuards.isTruffleObject(arguments0Value)) {
            int var5;
            this.state_0_ = var5 = state_0 | 32;
            return this.keysOther(arguments0Value);
         } else if (JSGuards.isForeignObject(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 64;
            return this.keysForeign(arguments0Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
         Object[] data = new Object[8];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"keysDynamicObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"keysSymbol", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"keysString", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"keysSafeInt", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"keysBigInt", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         s = new Object[]{"keysOther", null, null};
         if ((state_0 & 32) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[6] = s;
         s = new Object[]{"keysForeign", null, null};
         if ((state_0 & 64) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[7] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectKeysNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectFunctionBuiltinsFactory.ObjectKeysNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ObjectFunctionBuiltins.ObjectPreventExtensionsNode.class)
   public static final class ObjectPreventExtensionsNodeGen extends ObjectFunctionBuiltins.ObjectPreventExtensionsNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ObjectPreventExtensionsNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (JSGuards.isJSObject(arguments0Value__)) {
               return this.preventExtensionsObject(arguments0Value__);
            }
         }

         if ((state_0 & 2) != 0 && !JSGuards.isJSObject(arguments0Value_)) {
            return this.preventExtensionsNonObject(arguments0Value_);
         } else {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
         }
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if (JSGuards.isJSObject(arguments0Value_)) {
               int var5;
               this.state_0_ = var5 = state_0 | 1;
               return this.preventExtensionsObject(arguments0Value_);
            }
         }

         if (!JSGuards.isJSObject(arguments0Value)) {
            int var4;
            this.state_0_ = var4 = state_0 | 2;
            return this.preventExtensionsNonObject(arguments0Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
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
         Object[] data = new Object[]{0, null, null};
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"preventExtensionsObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"preventExtensionsNonObject", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectPreventExtensionsNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectFunctionBuiltinsFactory.ObjectPreventExtensionsNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ObjectFunctionBuiltins.ObjectSetIntegrityLevelNode.class)
   public static final class ObjectSetIntegrityLevelNodeGen extends ObjectFunctionBuiltins.ObjectSetIntegrityLevelNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private ObjectSetIntegrityLevelNodeGen(JSContext context, JSBuiltin builtin, boolean freeze, JavaScriptNode[] arguments) {
         super(context, builtin, freeze);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.setIntegrityLevel(arguments0Value_);
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
         Object[] s = new Object[]{"setIntegrityLevel", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectSetIntegrityLevelNode create(JSContext context, JSBuiltin builtin, boolean freeze, JavaScriptNode[] arguments) {
         return new ObjectFunctionBuiltinsFactory.ObjectSetIntegrityLevelNodeGen(context, builtin, freeze, arguments);
      }
   }

   @GeneratedBy(ObjectFunctionBuiltins.ObjectSetPrototypeOfNode.class)
   public static final class ObjectSetPrototypeOfNodeGen extends ObjectFunctionBuiltins.ObjectSetPrototypeOfNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @Node.Child
      private JavaScriptNode arguments1_;
      @CompilerDirectives.CompilationFinal
      private int state_0_;

      private ObjectSetPrototypeOfNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
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
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         Object arguments1Value_ = this.arguments1_.execute(frameValue);
         if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSObject) {
            JSObject arguments0Value__ = (JSObject)arguments0Value_;
            if ((state_0 & 1) != 0 && arguments1Value_ instanceof JSDynamicObject) {
               JSDynamicObject arguments1Value__ = (JSDynamicObject)arguments1Value_;
               if (JSGuards.isValidPrototype(arguments1Value__)) {
                  return this.setPrototypeOfJSObject(arguments0Value__, arguments1Value__);
               }
            }

            if ((state_0 & 2) != 0 && !JSGuards.isValidPrototype(arguments1Value_)) {
               return ObjectFunctionBuiltins.ObjectSetPrototypeOfNode.setPrototypeOfJSObjectToInvalidNewProto(arguments0Value__, arguments1Value_);
            }
         }

         if ((state_0 & 28) != 0) {
            if ((state_0 & 4) != 0 && JSGuards.isNullOrUndefined(arguments0Value_)) {
               return this.setPrototypeOfNonObjectCoercible(arguments0Value_, arguments1Value_);
            }

            if ((state_0 & 8) != 0
               && !JSGuards.isJSObject(arguments0Value_)
               && !JSGuards.isNullOrUndefined(arguments0Value_)
               && !JSGuards.isForeignObject(arguments0Value_)) {
               return ObjectFunctionBuiltins.ObjectSetPrototypeOfNode.setPrototypeOfValue(arguments0Value_, arguments1Value_);
            }

            if ((state_0 & 16) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
               return this.setPrototypeOfForeignObject(arguments0Value_, arguments1Value_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
         int state_0 = this.state_0_;
         if (arguments0Value instanceof JSObject) {
            JSObject arguments0Value_ = (JSObject)arguments0Value;
            if (arguments1Value instanceof JSDynamicObject) {
               JSDynamicObject arguments1Value_ = (JSDynamicObject)arguments1Value;
               if (JSGuards.isValidPrototype(arguments1Value_)) {
                  int var10;
                  this.state_0_ = var10 = state_0 | 1;
                  return this.setPrototypeOfJSObject(arguments0Value_, arguments1Value_);
               }
            }

            if (!JSGuards.isValidPrototype(arguments1Value)) {
               int var9;
               this.state_0_ = var9 = state_0 | 2;
               return ObjectFunctionBuiltins.ObjectSetPrototypeOfNode.setPrototypeOfJSObjectToInvalidNewProto(arguments0Value_, arguments1Value);
            }
         }

         if (JSGuards.isNullOrUndefined(arguments0Value)) {
            int var8;
            this.state_0_ = var8 = state_0 | 4;
            return this.setPrototypeOfNonObjectCoercible(arguments0Value, arguments1Value);
         } else if (!JSGuards.isJSObject(arguments0Value) && !JSGuards.isNullOrUndefined(arguments0Value) && !JSGuards.isForeignObject(arguments0Value)) {
            int var7;
            this.state_0_ = var7 = state_0 | 8;
            return ObjectFunctionBuiltins.ObjectSetPrototypeOfNode.setPrototypeOfValue(arguments0Value, arguments1Value);
         } else if (JSGuards.isForeignObject(arguments0Value)) {
            int var6;
            this.state_0_ = var6 = state_0 | 16;
            return this.setPrototypeOfForeignObject(arguments0Value, arguments1Value);
         } else {
            throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_, this.arguments1_}, arguments0Value, arguments1Value);
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
         Object[] data = new Object[6];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"setPrototypeOfJSObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"setPrototypeOfJSObjectToInvalidNewProto", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"setPrototypeOfNonObjectCoercible", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         s = new Object[]{"setPrototypeOfValue", null, null};
         if ((state_0 & 8) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[4] = s;
         s = new Object[]{"setPrototypeOfForeignObject", null, null};
         if ((state_0 & 16) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[5] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectSetPrototypeOfNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
         return new ObjectFunctionBuiltinsFactory.ObjectSetPrototypeOfNodeGen(context, builtin, arguments);
      }
   }

   @GeneratedBy(ObjectFunctionBuiltins.ObjectTestIntegrityLevelNode.class)
   public static final class ObjectTestIntegrityLevelNodeGen extends ObjectFunctionBuiltins.ObjectTestIntegrityLevelNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;

      private ObjectTestIntegrityLevelNodeGen(JSContext context, JSBuiltin builtin, boolean frozen, JavaScriptNode[] arguments) {
         super(context, builtin, frozen);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.testIntegrityLevel(arguments0Value_);
      }

      @Override
      public boolean executeBoolean(VirtualFrame frameValue) {
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         return this.testIntegrityLevel(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.executeBoolean(frameValue);
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.MONOMORPHIC;
      }

      @Override
      public Introspection getIntrospectionData() {
         Object[] data = new Object[]{0, null};
         Object[] s = new Object[]{"testIntegrityLevel", (byte)1, null};
         data[1] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectTestIntegrityLevelNode create(JSContext context, JSBuiltin builtin, boolean frozen, JavaScriptNode[] arguments) {
         return new ObjectFunctionBuiltinsFactory.ObjectTestIntegrityLevelNodeGen(context, builtin, frozen, arguments);
      }
   }

   @GeneratedBy(ObjectFunctionBuiltins.ObjectValuesOrEntriesNode.class)
   public static final class ObjectValuesOrEntriesNodeGen extends ObjectFunctionBuiltins.ObjectValuesOrEntriesNode implements Introspection.Provider {
      @Node.Child
      private JavaScriptNode arguments0_;
      @CompilerDirectives.CompilationFinal
      private volatile int state_0_;
      @Node.Child
      private ObjectFunctionBuiltins.ObjectValuesOrEntriesNode valuesOrEntriesGeneric_recursive_;

      private ObjectValuesOrEntriesNodeGen(JSContext context, JSBuiltin builtin, boolean entries, JavaScriptNode[] arguments) {
         super(context, builtin, entries);
         this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
      }

      @Override
      public JavaScriptNode[] getArguments() {
         return new JavaScriptNode[]{this.arguments0_};
      }

      @Override
      protected JSDynamicObject executeEvaluated(Object arguments0Value) {
         int state_0 = this.state_0_;
         if ((state_0 & 1) != 0 && arguments0Value instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
            if (JSGuards.isJSObject(arguments0Value_)) {
               return this.valuesOrEntriesJSObject(arguments0Value_);
            }
         }

         if ((state_0 & 6) != 0) {
            if ((state_0 & 2) != 0 && JSGuards.isForeignObject(arguments0Value)) {
               return this.valuesOrEntriesForeign(arguments0Value);
            }

            if ((state_0 & 4) != 0 && !JSGuards.isJSObject(arguments0Value) && !JSGuards.isForeignObject(arguments0Value)) {
               return this.valuesOrEntriesGeneric(arguments0Value, this.valuesOrEntriesGeneric_recursive_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value);
      }

      @Override
      public Object execute(VirtualFrame frameValue) {
         int state_0 = this.state_0_;
         Object arguments0Value_ = this.arguments0_.execute(frameValue);
         if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
            JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
            if (JSGuards.isJSObject(arguments0Value__)) {
               return this.valuesOrEntriesJSObject(arguments0Value__);
            }
         }

         if ((state_0 & 6) != 0) {
            if ((state_0 & 2) != 0 && JSGuards.isForeignObject(arguments0Value_)) {
               return this.valuesOrEntriesForeign(arguments0Value_);
            }

            if ((state_0 & 4) != 0 && !JSGuards.isJSObject(arguments0Value_) && !JSGuards.isForeignObject(arguments0Value_)) {
               return this.valuesOrEntriesGeneric(arguments0Value_, this.valuesOrEntriesGeneric_recursive_);
            }
         }

         CompilerDirectives.transferToInterpreterAndInvalidate();
         return this.executeAndSpecialize(arguments0Value_);
      }

      @Override
      public void executeVoid(VirtualFrame frameValue) {
         this.execute(frameValue);
      }

      private JSDynamicObject executeAndSpecialize(Object arguments0Value) {
         Lock lock = this.getLock();
         boolean hasLock = true;
         lock.lock();

         try {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
               JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
               if (JSGuards.isJSObject(arguments0Value_)) {
                  int var12;
                  this.state_0_ = var12 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return this.valuesOrEntriesJSObject(arguments0Value_);
               }
            }

            if (JSGuards.isForeignObject(arguments0Value)) {
               int var10;
               this.state_0_ = var10 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.valuesOrEntriesForeign(arguments0Value);
            } else if (JSGuards.isJSObject(arguments0Value) || JSGuards.isForeignObject(arguments0Value)) {
               throw new UnsupportedSpecializationException(this, new Node[]{this.arguments0_}, arguments0Value);
            } else {
               this.valuesOrEntriesGeneric_recursive_ = super.insert(this.createRecursive());
               int var11;
               this.state_0_ = var11 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.valuesOrEntriesGeneric(arguments0Value, this.valuesOrEntriesGeneric_recursive_);
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
         Object[] data = new Object[4];
         data[0] = 0;
         int state_0 = this.state_0_;
         Object[] s = new Object[]{"valuesOrEntriesJSObject", null, null};
         if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[1] = s;
         s = new Object[]{"valuesOrEntriesForeign", null, null};
         if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
         } else {
            s[1] = (byte)0;
         }

         data[2] = s;
         s = new Object[]{"valuesOrEntriesGeneric", null, null};
         if ((state_0 & 4) != 0) {
            s[1] = (byte)1;
            ArrayList<Object> cached = new ArrayList<>();
            cached.add(Arrays.asList(this.valuesOrEntriesGeneric_recursive_));
            s[2] = cached;
         } else {
            s[1] = (byte)0;
         }

         data[3] = s;
         return Introspection.Provider.create(data);
      }

      public static ObjectFunctionBuiltins.ObjectValuesOrEntriesNode create(JSContext context, JSBuiltin builtin, boolean entries, JavaScriptNode[] arguments) {
         return new ObjectFunctionBuiltinsFactory.ObjectValuesOrEntriesNodeGen(context, builtin, entries, arguments);
      }
   }
}
