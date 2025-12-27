package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.concurrent.locks.Lock;

@GeneratedBy(RestObjectWithExcludedNode.class)
final class RestObjectWithExcludedNodeGen extends RestObjectWithExcludedNode implements Introspection.Provider {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private JSToObjectNode other_toObjectNode_;

   private RestObjectWithExcludedNodeGen(JSContext context, JavaScriptNode targetNode, JavaScriptNode sourceNode, JavaScriptNode excludedNode) {
      super(context, targetNode, sourceNode, excludedNode);
   }

   @Override
   public Object execute(VirtualFrame frameValue) {
      int state_0 = this.state_0_;
      Object targetNodeValue_ = super.targetNode.execute(frameValue);
      Object sourceNodeValue_ = super.sourceNode.execute(frameValue);
      Object excludedNodeValue_ = super.excludedNode.execute(frameValue);
      if (state_0 != 0 && targetNodeValue_ instanceof JSDynamicObject) {
         JSDynamicObject targetNodeValue__ = (JSDynamicObject)targetNodeValue_;
         if (excludedNodeValue_ instanceof Object[]) {
            Object[] excludedNodeValue__ = (Object[])excludedNodeValue_;
            if ((state_0 & 1) != 0 && JSGuards.isNullOrUndefined(sourceNodeValue_)) {
               return RestObjectWithExcludedNode.doNullOrUndefined(targetNodeValue__, sourceNodeValue_, excludedNodeValue__);
            }

            if ((state_0 & 2) != 0 && sourceNodeValue_ instanceof JSDynamicObject) {
               JSDynamicObject sourceNodeValue__ = (JSDynamicObject)sourceNodeValue_;
               if (JSGuards.isJSObject(sourceNodeValue__)) {
                  return this.copyDataProperties(targetNodeValue__, sourceNodeValue__, excludedNodeValue__);
               }
            }

            if ((state_0 & 4) != 0 && !JSGuards.isJSDynamicObject(sourceNodeValue_)) {
               return this.doOther(targetNodeValue__, sourceNodeValue_, excludedNodeValue__, this.other_toObjectNode_);
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      return this.executeAndSpecialize(targetNodeValue_, sourceNodeValue_, excludedNodeValue_);
   }

   @Override
   public void executeVoid(VirtualFrame frameValue) {
      this.execute(frameValue);
   }

   private Object executeAndSpecialize(Object targetNodeValue, Object sourceNodeValue, Object excludedNodeValue) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         if (targetNodeValue instanceof JSDynamicObject) {
            JSDynamicObject targetNodeValue_ = (JSDynamicObject)targetNodeValue;
            if (excludedNodeValue instanceof Object[]) {
               Object[] excludedNodeValue_ = (Object[])excludedNodeValue;
               if (JSGuards.isNullOrUndefined(sourceNodeValue)) {
                  int var16;
                  this.state_0_ = var16 = state_0 | 1;
                  lock.unlock();
                  hasLock = false;
                  return RestObjectWithExcludedNode.doNullOrUndefined(targetNodeValue_, sourceNodeValue, excludedNodeValue_);
               }

               if (sourceNodeValue instanceof JSDynamicObject) {
                  JSDynamicObject sourceNodeValue_ = (JSDynamicObject)sourceNodeValue;
                  if (JSGuards.isJSObject(sourceNodeValue_)) {
                     int var15;
                     this.state_0_ = var15 = state_0 | 2;
                     lock.unlock();
                     hasLock = false;
                     return this.copyDataProperties(targetNodeValue_, sourceNodeValue_, excludedNodeValue_);
                  }
               }

               if (!JSGuards.isJSDynamicObject(sourceNodeValue)) {
                  this.other_toObjectNode_ = super.insert(JSToObjectNode.createToObjectNoCheck(this.context));
                  int var14;
                  this.state_0_ = var14 = state_0 | 4;
                  lock.unlock();
                  hasLock = false;
                  return this.doOther(targetNodeValue_, sourceNodeValue, excludedNodeValue_, this.other_toObjectNode_);
               }
            }
         }

         throw new UnsupportedSpecializationException(
            this, new Node[]{super.targetNode, super.sourceNode, super.excludedNode}, targetNodeValue, sourceNodeValue, excludedNodeValue
         );
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
      } else {
         s[1] = (byte)0;
      }

      data[2] = s;
      s = new Object[]{"doOther", null, null};
      if ((state_0 & 4) != 0) {
         s[1] = (byte)1;
         ArrayList<Object> cached = new ArrayList<>();
         cached.add(Arrays.asList(this.other_toObjectNode_));
         s[2] = cached;
      } else {
         s[1] = (byte)0;
      }

      data[3] = s;
      return Introspection.Provider.create(data);
   }

   public static RestObjectWithExcludedNode create(JSContext context, JavaScriptNode targetNode, JavaScriptNode sourceNode, JavaScriptNode excludedNode) {
      return new RestObjectWithExcludedNodeGen(context, targetNode, sourceNode, excludedNode);
   }
}
