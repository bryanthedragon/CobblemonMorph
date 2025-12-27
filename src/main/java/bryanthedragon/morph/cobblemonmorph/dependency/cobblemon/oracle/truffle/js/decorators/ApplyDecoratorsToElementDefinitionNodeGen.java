package com.oracle.truffle.js.decorators;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.nodes.access.IsObjectNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.ClassElementDefinitionRecord;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.function.SetFunctionNameNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(ApplyDecoratorsToElementDefinition.class)
public final class ApplyDecoratorsToElementDefinitionNodeGen extends ApplyDecoratorsToElementDefinition {
   @CompilerDirectives.CompilationFinal
   private volatile int state_0_;
   @Node.Child
   private ApplyDecoratorsToElementDefinitionNodeGen.DecorateFieldData decorateField_cache;
   @Node.Child
   private ApplyDecoratorsToElementDefinitionNodeGen.DecorateMethodData decorateMethod_cache;
   @Node.Child
   private ApplyDecoratorsToElementDefinitionNodeGen.DecorateGetterSetterData decorateGetterSetter_cache;
   @Node.Child
   private ApplyDecoratorsToElementDefinitionNodeGen.DecorateAutoData decorateAuto_cache;

   private ApplyDecoratorsToElementDefinitionNodeGen(JSContext context, boolean isStatic) {
      super(context, isStatic);
   }

   @Override
   public void executeDecorator(VirtualFrame frameValue, JSDynamicObject arg0Value, ClassElementDefinitionRecord arg1Value, List<Object> arg2Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 15) != 0) {
            if ((state_0 & 1) != 0 && !ApplyDecoratorsToElementDefinition.hasDecorators(arg1Value)) {
               this.noDecorators(frameValue, arg0Value, arg1Value, arg2Value);
               return;
            }

            if ((state_0 & 2) != 0) {
               ApplyDecoratorsToElementDefinitionNodeGen.DecorateFieldData s1_ = this.decorateField_cache;
               if (s1_ != null && arg1Value.isField() && ApplyDecoratorsToElementDefinition.hasDecorators(arg1Value)) {
                  this.decorateField(frameValue, arg0Value, arg1Value, arg2Value, s1_.createDecoratorContextNode_, s1_.callNode_, s1_.isCallableNode_);
                  return;
               }
            }

            if ((state_0 & 4) != 0) {
               ApplyDecoratorsToElementDefinitionNodeGen.DecorateMethodData s2_ = this.decorateMethod_cache;
               if (s2_ != null && arg1Value.isMethod() && ApplyDecoratorsToElementDefinition.hasDecorators(arg1Value)) {
                  this.decorateMethod(
                     frameValue, arg0Value, arg1Value, arg2Value, s2_.createDecoratorContextNode_, s2_.callNode_, s2_.setFunctionName_, s2_.isCallableNode_
                  );
                  return;
               }
            }

            if ((state_0 & 8) != 0) {
               ApplyDecoratorsToElementDefinitionNodeGen.DecorateGetterSetterData s3_ = this.decorateGetterSetter_cache;
               if (s3_ != null && ApplyDecoratorsToElementDefinition.hasDecorators(arg1Value) && ApplyDecoratorsToElementDefinition.isGetterOrSetter(arg1Value)
                  )
                {
                  this.decorateGetterSetter(
                     frameValue,
                     arg0Value,
                     arg1Value,
                     arg2Value,
                     s3_.createDecoratorContextNode_,
                     s3_.callNode_,
                     s3_.isCallableNode_,
                     s3_.setFunctionNameNode_,
                     s3_.toStringNode_,
                     s3_.concatNode_
                  );
                  return;
               }
            }
         }

         if ((state_0 & 16) != 0 && arg1Value instanceof ClassElementDefinitionRecord.AutoAccessor) {
            ClassElementDefinitionRecord.AutoAccessor arg1Value_ = (ClassElementDefinitionRecord.AutoAccessor)arg1Value;
            ApplyDecoratorsToElementDefinitionNodeGen.DecorateAutoData s4_ = this.decorateAuto_cache;
            if (s4_ != null && arg1Value_.isAutoAccessor() && ApplyDecoratorsToElementDefinition.hasDecorators(arg1Value_)) {
               this.decorateAuto(
                  frameValue,
                  arg0Value,
                  arg1Value_,
                  arg2Value,
                  s4_.createDecoratorContextNode_,
                  s4_.callNode_,
                  s4_.getGetterNode_,
                  s4_.getSetterNode_,
                  s4_.getInitNode_,
                  s4_.isCallableNode_,
                  s4_.createObjectNode_,
                  s4_.isObjectNode_
               );
               return;
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      this.executeAndSpecialize(frameValue, arg0Value, arg1Value, arg2Value);
   }

   private void executeAndSpecialize(VirtualFrame frameValue, JSDynamicObject arg0Value, ClassElementDefinitionRecord arg1Value, List<Object> arg2Value) {
      Lock lock = this.getLock();
      boolean hasLock = true;
      lock.lock();

      try {
         int state_0 = this.state_0_;
         if (!ApplyDecoratorsToElementDefinition.hasDecorators(arg1Value)) {
            int var17;
            this.state_0_ = var17 = state_0 | 1;
            lock.unlock();
            hasLock = false;
            this.noDecorators(frameValue, arg0Value, arg1Value, arg2Value);
            return;
         }

         if (arg1Value.isField() && ApplyDecoratorsToElementDefinition.hasDecorators(arg1Value)) {
            ApplyDecoratorsToElementDefinitionNodeGen.DecorateFieldData s1_ = super.insert(new ApplyDecoratorsToElementDefinitionNodeGen.DecorateFieldData());
            s1_.createDecoratorContextNode_ = s1_.insertAccessor(this.createDecoratorContextObjectNode());
            s1_.callNode_ = s1_.insertAccessor(JSFunctionCallNode.createCall());
            s1_.isCallableNode_ = s1_.insertAccessor(IsCallableNode.create());
            VarHandle.storeStoreFence();
            this.decorateField_cache = s1_;
            int var16;
            this.state_0_ = var16 = state_0 | 2;
            lock.unlock();
            hasLock = false;
            this.decorateField(frameValue, arg0Value, arg1Value, arg2Value, s1_.createDecoratorContextNode_, s1_.callNode_, s1_.isCallableNode_);
            return;
         }

         if (arg1Value.isMethod() && ApplyDecoratorsToElementDefinition.hasDecorators(arg1Value)) {
            ApplyDecoratorsToElementDefinitionNodeGen.DecorateMethodData s2_ = super.insert(new ApplyDecoratorsToElementDefinitionNodeGen.DecorateMethodData());
            s2_.createDecoratorContextNode_ = s2_.insertAccessor(this.createDecoratorContextObjectNode());
            s2_.callNode_ = s2_.insertAccessor(JSFunctionCallNode.createCall());
            s2_.setFunctionName_ = s2_.insertAccessor(SetFunctionNameNode.create());
            s2_.isCallableNode_ = s2_.insertAccessor(IsCallableNode.create());
            VarHandle.storeStoreFence();
            this.decorateMethod_cache = s2_;
            int var15;
            this.state_0_ = var15 = state_0 | 4;
            lock.unlock();
            hasLock = false;
            this.decorateMethod(
               frameValue, arg0Value, arg1Value, arg2Value, s2_.createDecoratorContextNode_, s2_.callNode_, s2_.setFunctionName_, s2_.isCallableNode_
            );
            return;
         }

         if (!ApplyDecoratorsToElementDefinition.hasDecorators(arg1Value) || !ApplyDecoratorsToElementDefinition.isGetterOrSetter(arg1Value)) {
            if (arg1Value instanceof ClassElementDefinitionRecord.AutoAccessor) {
               ClassElementDefinitionRecord.AutoAccessor arg1Value_ = (ClassElementDefinitionRecord.AutoAccessor)arg1Value;
               if (arg1Value_.isAutoAccessor() && ApplyDecoratorsToElementDefinition.hasDecorators(arg1Value_)) {
                  ApplyDecoratorsToElementDefinitionNodeGen.DecorateAutoData s4_ = super.insert(
                     new ApplyDecoratorsToElementDefinitionNodeGen.DecorateAutoData()
                  );
                  s4_.createDecoratorContextNode_ = s4_.insertAccessor(this.createDecoratorContextObjectNode());
                  s4_.callNode_ = s4_.insertAccessor(JSFunctionCallNode.createCall());
                  s4_.getGetterNode_ = s4_.insertAccessor(this.createGetterNode());
                  s4_.getSetterNode_ = s4_.insertAccessor(this.createSetterNode());
                  s4_.getInitNode_ = s4_.insertAccessor(this.createInitNode());
                  s4_.isCallableNode_ = s4_.insertAccessor(IsCallableNode.create());
                  s4_.createObjectNode_ = s4_.insertAccessor(CreateObjectNode.create(this.context));
                  s4_.isObjectNode_ = s4_.insertAccessor(IsObjectNode.create());
                  VarHandle.storeStoreFence();
                  this.decorateAuto_cache = s4_;
                  int var14;
                  this.state_0_ = var14 = state_0 | 16;
                  lock.unlock();
                  hasLock = false;
                  this.decorateAuto(
                     frameValue,
                     arg0Value,
                     arg1Value_,
                     arg2Value,
                     s4_.createDecoratorContextNode_,
                     s4_.callNode_,
                     s4_.getGetterNode_,
                     s4_.getSetterNode_,
                     s4_.getInitNode_,
                     s4_.isCallableNode_,
                     s4_.createObjectNode_,
                     s4_.isObjectNode_
                  );
                  return;
               }
            }

            throw new UnsupportedSpecializationException(this, new Node[]{null, null, null}, arg0Value, arg1Value, arg2Value);
         }

         ApplyDecoratorsToElementDefinitionNodeGen.DecorateGetterSetterData s3_ = super.insert(
            new ApplyDecoratorsToElementDefinitionNodeGen.DecorateGetterSetterData()
         );
         s3_.createDecoratorContextNode_ = s3_.insertAccessor(this.createDecoratorContextObjectNode());
         s3_.callNode_ = s3_.insertAccessor(JSFunctionCallNode.createCall());
         s3_.isCallableNode_ = s3_.insertAccessor(IsCallableNode.create());
         s3_.setFunctionNameNode_ = s3_.insertAccessor(SetFunctionNameNode.create());
         s3_.toStringNode_ = s3_.insertAccessor(JSToStringNode.createSymbolToString());
         s3_.concatNode_ = s3_.insertAccessor(TruffleString.ConcatNode.create());
         VarHandle.storeStoreFence();
         this.decorateGetterSetter_cache = s3_;
         int var13;
         this.state_0_ = var13 = state_0 | 8;
         lock.unlock();
         hasLock = false;
         this.decorateGetterSetter(
            frameValue,
            arg0Value,
            arg1Value,
            arg2Value,
            s3_.createDecoratorContextNode_,
            s3_.callNode_,
            s3_.isCallableNode_,
            s3_.setFunctionNameNode_,
            s3_.toStringNode_,
            s3_.concatNode_
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

   public static ApplyDecoratorsToElementDefinition create(JSContext context, boolean isStatic) {
      return new ApplyDecoratorsToElementDefinitionNodeGen(context, isStatic);
   }

   @GeneratedBy(ApplyDecoratorsToElementDefinition.class)
   private static final class DecorateAutoData extends Node {
      @Node.Child
      CreateDecoratorContextObjectNode createDecoratorContextNode_;
      @Node.Child
      JSFunctionCallNode callNode_;
      @Node.Child
      PropertyGetNode getGetterNode_;
      @Node.Child
      PropertyGetNode getSetterNode_;
      @Node.Child
      PropertyGetNode getInitNode_;
      @Node.Child
      IsCallableNode isCallableNode_;
      @Node.Child
      CreateObjectNode createObjectNode_;
      @Node.Child
      IsObjectNode isObjectNode_;

      DecorateAutoData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(ApplyDecoratorsToElementDefinition.class)
   private static final class DecorateFieldData extends Node {
      @Node.Child
      CreateDecoratorContextObjectNode createDecoratorContextNode_;
      @Node.Child
      JSFunctionCallNode callNode_;
      @Node.Child
      IsCallableNode isCallableNode_;

      DecorateFieldData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(ApplyDecoratorsToElementDefinition.class)
   private static final class DecorateGetterSetterData extends Node {
      @Node.Child
      CreateDecoratorContextObjectNode createDecoratorContextNode_;
      @Node.Child
      JSFunctionCallNode callNode_;
      @Node.Child
      IsCallableNode isCallableNode_;
      @Node.Child
      SetFunctionNameNode setFunctionNameNode_;
      @Node.Child
      JSToStringNode toStringNode_;
      @Node.Child
      TruffleString.ConcatNode concatNode_;

      DecorateGetterSetterData() {
      }

      @Override
      public NodeCost getCost() {
         return NodeCost.NONE;
      }

      <T extends Node> T insertAccessor(T node) {
         return super.insert(node);
      }
   }

   @GeneratedBy(ApplyDecoratorsToElementDefinition.class)
   private static final class DecorateMethodData extends Node {
      @Node.Child
      CreateDecoratorContextObjectNode createDecoratorContextNode_;
      @Node.Child
      JSFunctionCallNode callNode_;
      @Node.Child
      SetFunctionNameNode setFunctionName_;
      @Node.Child
      IsCallableNode isCallableNode_;

      DecorateMethodData() {
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
