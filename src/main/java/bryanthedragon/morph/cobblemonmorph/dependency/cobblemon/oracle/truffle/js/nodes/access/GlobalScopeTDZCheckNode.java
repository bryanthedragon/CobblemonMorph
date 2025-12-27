package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Executed;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.objects.Dead;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.Set;

abstract class GlobalScopeTDZCheckNode extends GlobalScopeNode {
   final TruffleString varName;
   @Node.Child
   @Executed
   JavaScriptNode scopeNode;

   GlobalScopeTDZCheckNode(JSContext context, TruffleString varName) {
      super(context);
      this.varName = varName;
      this.scopeNode = GlobalScopeNode.create(context);
   }

   @Specialization(guards = "scope.getShape() == cachedShape", assumptions = "cachedShape.getValidAssumption()", limit = "context.getPropertyCacheLimit()")
   final Object doCached(JSDynamicObject scope, @Cached("scope.getShape()") Shape cachedShape, @Cached("isDead(cachedShape)") boolean dead) {
      assert dead == (JSDynamicObject.getOrNull(scope, this.varName) == Dead.instance());

      if (dead) {
         throw Errors.createReferenceErrorNotDefined(this.context, this.varName, this);
      } else {
         return scope;
      }
   }

   @Specialization(replaces = "doCached")
   final Object doUncached(Object scope, @Cached("create(varName, context)") PropertyGetNode getNode, @Cached("create()") BranchProfile deadBranch) {
      if (getNode.getValue(scope) == Dead.instance()) {
         deadBranch.enter();
         throw Errors.createReferenceErrorNotDefined(this.context, this.varName, this);
      } else {
         return scope;
      }
   }

   final boolean isDead(Shape shape) {
      Property property = shape.getProperty(this.varName);
      return property != null && property.getLocation().isConstant() && property.getLocation().getConstantValue() == Dead.instance();
   }

   @Override
   protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
      return GlobalScopeTDZCheckNodeGen.create(this.context, this.varName);
   }
}
