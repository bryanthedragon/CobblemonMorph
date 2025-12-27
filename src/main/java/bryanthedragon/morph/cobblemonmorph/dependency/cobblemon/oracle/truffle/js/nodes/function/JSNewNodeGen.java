package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(JSNewNode.class)
public final class JSNewNodeGen extends JSNewNode implements Introspection.Provider {
   private static final LibraryFactory<InteropLibrary> INTEROP_LIBRARY_ = LibraryFactory.resolve(InteropLibrary.class);
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSFunctionCallNode callNew;
   @Node.Child
   private JSNewNodeGen.NewForeignObjectData newForeignObject_cache;

   private JSNewNodeGen(JSContext context, JavaScriptNode targetNode, AbstractFunctionArgumentsNode arguments) {
      super(context, targetNode, arguments);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object targetNodeValue_ = super.targetNode.execute(frameValue);
      if ((state_0 & 15) != 0 && targetNodeValue_ instanceof JSDynamicObject) {
         JSDynamicObject targetNodeValue__ = (JSDynamicObject)targetNodeValue_;
         if ((state_0 & 1) != 0 && JSGuards.isJSFunction(targetNodeValue__)) {
            return this.doNewReturnThis(frameValue, targetNodeValue__, this.callNew);
         }

         if ((state_0 & 2) != 0 && JSGuards.isJSProxy(targetNodeValue__)) {
            return this.doNewJSProxy(frameValue, targetNodeValue__, this.callNew);
         }

         if ((state_0 & 4) != 0 && JSGuards.isJSAdapter(targetNodeValue__)) {
            return this.doJSAdapter(frameValue, targetNodeValue__);
         }

         if ((state_0 & 8) != 0 && JSGuards.isJavaPackage(targetNodeValue__)) {
            return this.createClassNotFoundError(frameValue, targetNodeValue__);
         }
      }

      if ((state_0 & 48) != 0) {
         if ((state_0 & 16) != 0) {
            JSNewNodeGen.NewForeignObjectData s4_ = this.newForeignObject_cache;
            if (s4_ != null && JSGuards.isForeignObject(targetNodeValue_)) {
               return this.doNewForeignObject(
                  frameValue, targetNodeValue_, s4_.interop_, s4_.convert_, s4_.toJSType_, s4_.isHostClassProf_, s4_.isAbstractProf_
               );
            }
         }

         if ((state_0 & 32) != 0
            && !JSGuards.isJSFunction(targetNodeValue_)
            && !JSGuards.isJSAdapter(targetNodeValue_)
            && !JSGuards.isJSProxy(targetNodeValue_)
            && !JSGuards.isJavaPackage(targetNodeValue_)
            && !JSGuards.isForeignObject(targetNodeValue_)) {
            return this.createFunctionTypeError(frameValue, targetNodeValue_);
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(frameValue, targetNodeValue_);
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.execute(frameValue);
   }

   private Object executeAndSpecialize(VirtualFrame frameValue, Object targetNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         if (targetNodeValue instanceof JSDynamicObject) {
            JSDynamicObject targetNodeValue_ = (JSDynamicObject)targetNodeValue;
            if (JSGuards.isJSFunction(targetNodeValue_)) {
               this.callNew = super.insert(this.callNew == null ? JSFunctionCallNode.createNew() : this.callNew);
               int var16;
               this.state_0_ = var16 = state_0 | 1;
               lock.unlock();
               hasLock = false;
               return this.doNewReturnThis(frameValue, targetNodeValue_, this.callNew);
            }

            if (JSGuards.isJSProxy(targetNodeValue_)) {
               this.callNew = super.insert(this.callNew == null ? JSFunctionCallNode.createNew() : this.callNew);
               int var15;
               this.state_0_ = var15 = state_0 | 2;
               lock.unlock();
               hasLock = false;
               return this.doNewJSProxy(frameValue, targetNodeValue_, this.callNew);
            }

            if (JSGuards.isJSAdapter(targetNodeValue_)) {
               int var14;
               this.state_0_ = var14 = state_0 | 4;
               lock.unlock();
               hasLock = false;
               return this.doJSAdapter(frameValue, targetNodeValue_);
            }

            if (JSGuards.isJavaPackage(targetNodeValue_)) {
               int var13;
               this.state_0_ = var13 = state_0 | 8;
               lock.unlock();
               hasLock = false;
               return this.createClassNotFoundError(frameValue, targetNodeValue_);
            }
         }

         if (JSGuards.isForeignObject(targetNodeValue)) {
            JSNewNodeGen.NewForeignObjectData s4_ = super.insert(new JSNewNodeGen.NewForeignObjectData());
            s4_.interop_ = s4_.insertAccessor(INTEROP_LIBRARY_.createDispatched(5));
            s4_.convert_ = s4_.insertAccessor(ExportValueNode.create());
            s4_.toJSType_ = s4_.insertAccessor(ImportValueNode.create());
            s4_.isHostClassProf_ = ConditionProfile.createBinaryProfile();
            s4_.isAbstractProf_ = ConditionProfile.createBinaryProfile();
            VarHandle.storeStoreFence();
            this.newForeignObject_cache = s4_;
            int var11;
            this.state_0_ = var11 = state_0 | 16;
            lock.unlock();
            hasLock = false;
            return this.doNewForeignObject(frameValue, targetNodeValue, s4_.interop_, s4_.convert_, s4_.toJSType_, s4_.isHostClassProf_, s4_.isAbstractProf_);
         } else if (JSGuards.isJSFunction(targetNodeValue)
            || JSGuards.isJSAdapter(targetNodeValue)
            || JSGuards.isJSProxy(targetNodeValue)
            || JSGuards.isJavaPackage(targetNodeValue)
            || JSGuards.isForeignObject(targetNodeValue)) {
            throw new UnsupportedSpecializationException(this, new Node[]{super.targetNode}, targetNodeValue);
         } else {
            int var12;
            this.state_0_ = var12 = state_0 | 32;
            lock.unlock();
            hasLock = false;
            return this.createFunctionTypeError(frameValue, targetNodeValue);
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
      Object[] data = new Object[7];
      data[0] = 0;
      int state_0 = this.state_0_;
      Object[] s = new Object[]{"doNewReturnThis", null, null};
      if ((state_0 & 1) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.callNew));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[1] = s;
      s = new Object[]{"doNewJSProxy", null, null};
      if ((state_0 & 2) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.callNew));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doJSAdapter", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      s = new Object[]{"createClassNotFoundError", null, null};
      if ((state_0 & 8) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[4] = s;
      s = new Object[]{"doNewForeignObject", null, null};
      if ((state_0 & 16) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         JSNewNodeGen.NewForeignObjectData s4_ = this.newForeignObject_cache;
         if (s4_ != null) {
            cached.add(Arrays.asList(s4_.interop_, s4_.convert_, s4_.toJSType_, s4_.isHostClassProf_, s4_.isAbstractProf_));
         }

         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[5] = s;
      s = new Object[]{"createFunctionTypeError", null, null};
      if ((state_0 & 32) != 0) {
         s[1] = (byte)1;
      } else {
         s[1] = (byte)0;
      }

      data[6] = s;
      return Introspection.Provider.create(data);
   }

   public static JSNewNode create(JSContext context, JavaScriptNode targetNode, AbstractFunctionArgumentsNode arguments) {
      return new JSNewNodeGen(context, targetNode, arguments);
   }

   @GeneratedBy(JSNewNode.class)
   private static final class NewForeignObjectData extends Node {
      @Node.Child
      InteropLibrary interop_;
      @Node.Child
      ExportValueNode convert_;
      @Node.Child
      ImportValueNode toJSType_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile isHostClassProf_;
      @CompilerDirectives.CompilationFinal
      ConditionProfile isAbstractProf_;

      NewForeignObjectData() {
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
