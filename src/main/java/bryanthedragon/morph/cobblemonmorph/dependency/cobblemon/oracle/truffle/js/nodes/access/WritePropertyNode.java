package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTaggedExecutionNode;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.instrumentation.NodeObjectDescriptor;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Set;

public class WritePropertyNode extends JSTargetableWriteNode {
   @Node.Child
   protected JavaScriptNode targetNode;
   @Node.Child
   protected JavaScriptNode rhsNode;
   @Node.Child
   protected PropertySetNode cache;
   @CompilerDirectives.CompilationFinal
   private byte valueState;
   private static final byte VALUE_INT = 1;
   private static final byte VALUE_DOUBLE = 2;
   private static final byte VALUE_OBJECT = 3;
   private final boolean verifyHasProperty;
   @CompilerDirectives.CompilationFinal
   private BranchProfile referenceErrorBranch;
   @Node.Child
   protected HasPropertyCacheNode hasProperty;

   protected WritePropertyNode(
      JavaScriptNode target, JavaScriptNode rhs, Object propertyKey, boolean isGlobal, JSContext context, boolean isStrict, boolean verifyHasProperty
   ) {
      this.targetNode = target;
      this.rhsNode = rhs;
      boolean superProperty = target instanceof SuperPropertyReferenceNode;
      this.cache = PropertySetNode.createImpl(propertyKey, isGlobal, context, isStrict, false, JSAttributes.getDefault(), false, superProperty);
      this.verifyHasProperty = verifyHasProperty;
      if (verifyHasProperty) {
         this.referenceErrorBranch = BranchProfile.create();
         if (isStrict || !isGlobal) {
            this.hasProperty = HasPropertyCacheNode.create(propertyKey, context);
         }
      }
   }

   public static WritePropertyNode create(JavaScriptNode target, Object propertyKey, JavaScriptNode rhs, JSContext ctx, boolean isStrict) {
      return create(target, propertyKey, rhs, ctx, isStrict, false, false);
   }

   public static WritePropertyNode create(
      JavaScriptNode target, Object propertyKey, JavaScriptNode rhs, JSContext ctx, boolean isStrict, boolean isGlobal, boolean verifyHasProperty
   ) {
      return new WritePropertyNode(target, rhs, propertyKey, isGlobal, ctx, isStrict, verifyHasProperty);
   }

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      if ((tag == JSTags.WriteVariableTag.class || tag == StandardTags.WriteVariableTag.class) && this.isScopeAccess()) {
         return true;
      } else if (tag == JSTags.WritePropertyTag.class && !this.isScopeAccess()) {
         return true;
      } else {
         return tag == JSTags.InputNodeTag.class ? true : super.hasTag(tag);
      }
   }

   private boolean isScopeAccess() {
      return this.targetNode instanceof GlobalScopeNode;
   }

   @Override
   public Object getNodeObject() {
      if (this.isScopeAccess()) {
         NodeObjectDescriptor descriptor = JSTags.createNodeObjectDescriptor("name", this.getKey());
         descriptor.addProperty("writeVariableName", this.getKey());
         return descriptor;
      } else {
         return JSTags.createNodeObjectDescriptor("key", this.getKey());
      }
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      if (materializedTags.contains(JSTags.WritePropertyTag.class) && !this.isScopeAccess() && this.materializationNeeded()) {
         JavaScriptNode clonedTarget = this.targetNode != null && !this.targetNode.hasSourceSection()
            ? JSTaggedExecutionNode.createForInput(this.targetNode, this, materializedTags)
            : this.targetNode;
         JavaScriptNode clonedRhs = this.rhsNode != null && !this.rhsNode.hasSourceSection()
            ? JSTaggedExecutionNode.createForInput(this.rhsNode, this, materializedTags)
            : this.rhsNode;
         if (clonedTarget == this.targetNode && clonedRhs == this.rhsNode) {
            return this;
         } else {
            if (clonedTarget == this.targetNode) {
               clonedTarget = cloneUninitialized(this.targetNode, materializedTags);
            }

            if (clonedRhs == this.rhsNode) {
               clonedRhs = cloneUninitialized(this.rhsNode, materializedTags);
            }

            WritePropertyNode clone = create(
               clonedTarget, this.cache.getKey(), clonedRhs, this.cache.getContext(), this.cache.isStrict(), this.cache.isGlobal(), this.verifyHasProperty
            );
            transferSourceSectionAndTags(this, clone);
            return clone;
         }
      } else {
         return this;
      }
   }

   private boolean materializationNeeded() {
      return this.targetNode != null && !this.targetNode.hasSourceSection() || this.rhsNode != null && !this.rhsNode.hasSourceSection();
   }

   @Override
   public final JavaScriptNode getTarget() {
      return this.targetNode;
   }

   @Override
   public final JavaScriptNode getRhs() {
      return this.rhsNode;
   }

   public final Object getKey() {
      return this.cache.getKey();
   }

   public final boolean isGlobal() {
      return this.cache.isGlobal();
   }

   public final Object executeWithValue(Object obj, Object value) {
      this.verifyBindingStillExists(obj);
      this.cache.setValue(obj, value);
      return value;
   }

   public final int executeIntWithValue(Object obj, int value) {
      this.verifyBindingStillExists(obj);
      this.cache.setValueInt(obj, value);
      return value;
   }

   public final double executeDoubleWithValue(Object obj, double value) {
      this.verifyBindingStillExists(obj);
      this.cache.setValueDouble(obj, value);
      return value;
   }

   private void executeEvaluated(Object obj, Object value, Object receiver) {
      this.verifyBindingStillExists(obj);
      this.cache.setValue(obj, value, receiver);
   }

   private int executeIntEvaluated(Object obj, int value, Object receiver) {
      this.verifyBindingStillExists(obj);
      this.cache.setValueInt(obj, value, receiver);
      return value;
   }

   private double executeDoubleEvaluated(Object obj, double value, Object receiver) {
      this.verifyBindingStillExists(obj);
      this.cache.setValueDouble(obj, value, receiver);
      return value;
   }

   @Override
   public final Object execute(VirtualFrame frame) {
      Object target = this.evaluateTarget(frame);
      Object receiver = evaluateReceiver(this.targetNode, frame, target);
      Object value = this.rhsNode.execute(frame);
      this.executeEvaluated(target, value, receiver);
      return value;
   }

   @Override
   public final int executeInt(VirtualFrame frame) throws UnexpectedResultException {
      Object target = this.evaluateTarget(frame);
      Object receiver = evaluateReceiver(this.targetNode, frame, target);

      try {
         int value = this.rhsNode.executeInt(frame);
         return this.executeIntEvaluated(target, value, receiver);
      } catch (UnexpectedResultException var5) {
         this.executeEvaluated(target, var5.getResult(), receiver);
         throw var5;
      }
   }

   @Override
   public final double executeDouble(VirtualFrame frame) throws UnexpectedResultException {
      Object target = this.evaluateTarget(frame);
      Object receiver = evaluateReceiver(this.targetNode, frame, target);

      try {
         double value = this.rhsNode.executeDouble(frame);
         return this.executeDoubleEvaluated(target, value, receiver);
      } catch (UnexpectedResultException var6) {
         this.executeEvaluated(target, var6.getResult(), receiver);
         throw var6;
      }
   }

   @Override
   public final void executeVoid(VirtualFrame frame) {
      Object target = this.evaluateTarget(frame);
      Object receiver = evaluateReceiver(this.targetNode, frame, target);
      byte vs = this.valueState;
      if (vs == 0) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.executeAndSpecialize(frame, target, receiver);
      } else {
         if (vs == 1) {
            try {
               int value = this.rhsNode.executeInt(frame);
               this.executeIntEvaluated(target, value, receiver);
            } catch (UnexpectedResultException var8) {
               this.valueState = 3;
               this.executeEvaluated(target, var8.getResult(), receiver);
            }
         } else if (vs == 2) {
            try {
               double value = this.rhsNode.executeDouble(frame);
               this.executeDoubleEvaluated(target, value, receiver);
            } catch (UnexpectedResultException var7) {
               this.valueState = 3;
               this.executeEvaluated(target, var7.getResult(), receiver);
            }
         } else {
            assert vs == 3;

            Object value = this.rhsNode.execute(frame);
            this.executeEvaluated(target, value, receiver);
         }
      }
   }

   private void executeAndSpecialize(VirtualFrame frame, Object target, Object receiver) {
      CompilerAsserts.neverPartOfCompilation();
      Object value = this.rhsNode.execute(frame);
      if (value instanceof Integer) {
         this.valueState = 1;
         this.executeIntEvaluated(target, (Integer)value, receiver);
      } else if (value instanceof Double) {
         this.valueState = 2;
         this.executeDoubleEvaluated(target, (Double)value, receiver);
      } else {
         this.valueState = 3;
         this.executeEvaluated(target, value, receiver);
      }
   }

   @Override
   public final void executeWrite(VirtualFrame frame, Object value) {
      Object target = this.evaluateTarget(frame);
      Object receiver = evaluateReceiver(this.targetNode, frame, target);
      this.executeEvaluated(target, value, receiver);
   }

   @Override
   public final Object executeWithTarget(VirtualFrame frame, Object target) {
      Object value = this.rhsNode.execute(frame);
      this.executeEvaluated(target, value, target);
      return value;
   }

   @Override
   public final Object evaluateTarget(VirtualFrame frame) {
      Object target = this.targetNode.execute(frame);
      return this.verifyHasProperty && this.isUnresolvableReference(target) ? null : target;
   }

   private boolean isUnresolvableReference(Object target) {
      assert this.verifyHasProperty;

      return !this.bindingExists(target);
   }

   private void verifyBindingStillExists(Object target) {
      if (this.verifyHasProperty && (target == null || !this.bindingExists(target))) {
         this.unresolvablePropertyInStrictMode(target);
      }
   }

   private boolean bindingExists(Object target) {
      assert this.verifyHasProperty;

      if (!this.cache.isStrict() && this.cache.isGlobal() && this.cache.getContext().getGlobalObjectPristineAssumption().isValid()) {
         return true;
      } else {
         if (this.hasProperty == null) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.hasProperty = this.insert(HasPropertyCacheNode.create(this.cache.getKey(), this.cache.getContext()));
         }

         return this.hasProperty.hasProperty(target) || !this.cache.isStrict();
      }
   }

   private void unresolvablePropertyInStrictMode(Object thisObj) {
      this.referenceErrorBranch.enter();

      assert !this.cache.isGlobal() || thisObj == null || JSDynamicObject.isJSDynamicObject(thisObj) && thisObj == this.getRealm().getGlobalObject();

      throw Errors.createReferenceErrorNotDefined(this.cache.getContext(), this.getKey(), this);
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return create(
         cloneUninitialized(this.targetNode, materializedTags),
         this.cache.getKey(),
         cloneUninitialized(this.rhsNode, materializedTags),
         this.cache.getContext(),
         this.cache.isStrict(),
         this.cache.isGlobal(),
         this.verifyHasProperty
      );
   }

   @Override
   public boolean isResultAlwaysOfType(Class<?> clazz) {
      return this.getRhs().isResultAlwaysOfType(clazz);
   }
}
