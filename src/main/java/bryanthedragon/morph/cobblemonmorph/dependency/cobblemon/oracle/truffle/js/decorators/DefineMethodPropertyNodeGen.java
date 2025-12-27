package com.oracle.truffle.js.decorators;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.function.ClassElementDefinitionRecord;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;

@GeneratedBy(DefineMethodPropertyNode.class)
public final class DefineMethodPropertyNodeGen extends DefineMethodPropertyNode {
   @CompilerDirectives.CompilationFinal
   private int state_0_;

   private DefineMethodPropertyNodeGen() {
   }

   @Override
   public void executeDefine(JSDynamicObject arg0Value, ClassElementDefinitionRecord arg1Value, boolean arg2Value) {
      int state_0 = this.state_0_;
      if (state_0 != 0) {
         if ((state_0 & 15) != 0) {
            if ((state_0 & 1) != 0 && arg1Value.isPrivate()) {
               this.doPrivate(arg0Value, arg1Value, arg2Value);
               return;
            }

            if ((state_0 & 2) != 0 && arg1Value.isMethod() && !arg1Value.isPrivate()) {
               this.doMethod(arg0Value, arg1Value, arg2Value);
               return;
            }

            if ((state_0 & 4) != 0 && arg1Value.isGetter() && !arg1Value.isPrivate()) {
               this.doGetter(arg0Value, arg1Value, arg2Value);
               return;
            }

            if ((state_0 & 8) != 0 && arg1Value.isSetter() && !arg1Value.isPrivate()) {
               this.doSetter(arg0Value, arg1Value, arg2Value);
               return;
            }
         }

         if ((state_0 & 16) != 0 && arg1Value instanceof ClassElementDefinitionRecord.AutoAccessor) {
            ClassElementDefinitionRecord.AutoAccessor arg1Value_ = (ClassElementDefinitionRecord.AutoAccessor)arg1Value;
            if (arg1Value_.isAutoAccessor() && !arg1Value_.isPrivate()) {
               this.doAutoAccessor(arg0Value, arg1Value_, arg2Value);
               return;
            }
         }
      }

      CompilerDirectives.transferToInterpreterAndInvalidate();
      this.executeAndSpecialize(arg0Value, arg1Value, arg2Value);
   }

   private void executeAndSpecialize(JSDynamicObject arg0Value, ClassElementDefinitionRecord arg1Value, boolean arg2Value) {
      int state_0 = this.state_0_;
      if (arg1Value.isPrivate()) {
         int var10;
         this.state_0_ = var10 = state_0 | 1;
         this.doPrivate(arg0Value, arg1Value, arg2Value);
      } else if (arg1Value.isMethod() && !arg1Value.isPrivate()) {
         int var9;
         this.state_0_ = var9 = state_0 | 2;
         this.doMethod(arg0Value, arg1Value, arg2Value);
      } else if (arg1Value.isGetter() && !arg1Value.isPrivate()) {
         int var8;
         this.state_0_ = var8 = state_0 | 4;
         this.doGetter(arg0Value, arg1Value, arg2Value);
      } else if (arg1Value.isSetter() && !arg1Value.isPrivate()) {
         int var7;
         this.state_0_ = var7 = state_0 | 8;
         this.doSetter(arg0Value, arg1Value, arg2Value);
      } else {
         if (arg1Value instanceof ClassElementDefinitionRecord.AutoAccessor) {
            ClassElementDefinitionRecord.AutoAccessor arg1Value_ = (ClassElementDefinitionRecord.AutoAccessor)arg1Value;
            if (arg1Value_.isAutoAccessor() && !arg1Value_.isPrivate()) {
               int var6;
               this.state_0_ = var6 = state_0 | 16;
               this.doAutoAccessor(arg0Value, arg1Value_, arg2Value);
               return;
            }
         }

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

   public static DefineMethodPropertyNode create() {
      return new DefineMethodPropertyNodeGen();
   }
}
