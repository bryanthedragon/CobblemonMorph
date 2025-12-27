package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.InstrumentableNode;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.instrumentation.DeclareTagProvider;
import com.oracle.truffle.js.nodes.instrumentation.JSTags;
import com.oracle.truffle.js.nodes.instrumentation.NodeObjectDescriptor;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import java.util.Set;

public abstract class JSFunctionExpressionNode extends JavaScriptNode implements FunctionNameHolder {
   protected final JSFunctionData functionData;

   protected JSFunctionExpressionNode(JSFunctionData functionData) {
      this.functionData = functionData;
   }

   public static JSFunctionExpressionNode create(JSFunctionData function) {
      assert !function.needsParentFrame();

      return new JSFunctionExpressionNode.AutonomousFunctionExpressionNode(function);
   }

   public static JSFunctionExpressionNode create(JSFunctionData function, JSFrameSlot blockScopeSlot) {
      return (JSFunctionExpressionNode)(function.needsParentFrame()
         ? new JSFunctionExpressionNode.ClosureFunctionExpressionNode(function, blockScopeSlot != null ? blockScopeSlot.getIndex() : -1)
         : new JSFunctionExpressionNode.AutonomousFunctionExpressionNode(function));
   }

   public static JSFunctionExpressionNode createLexicalThis(JSFunctionData function, JSFrameSlot blockScopeSlot, JavaScriptNode thisNode) {
      return (JSFunctionExpressionNode)(function.needsParentFrame()
         ? new JSFunctionExpressionNode.LexicalThisClosureFunctionExpressionNode(function, blockScopeSlot != null ? blockScopeSlot.getIndex() : -1, thisNode)
         : new JSFunctionExpressionNode.LexicalThisAutonomousFunctionExpressionNode(function, thisNode));
   }

   @Override
   public final Object execute(VirtualFrame frame) {
      return this.executeWithRealm(frame, this.getRealm());
   }

   public abstract Object executeWithRealm(VirtualFrame frame, JSRealm realm);

   @Override
   public boolean hasTag(Class<? extends Tag> tag) {
      if (tag == JSTags.LiteralTag.class) {
         return true;
      } else if (tag == JSTags.InputNodeTag.class) {
         return true;
      } else {
         return tag == JSTags.DeclareTag.class ? !super.hasTag(StandardTags.ExpressionTag.class) : super.hasTag(tag);
      }
   }

   @Override
   public Object getNodeObject() {
      if (super.hasTag(StandardTags.ExpressionTag.class)) {
         return JSTags.createNodeObjectDescriptor("literalType", JSTags.LiteralTag.Type.FunctionLiteral.name());
      } else {
         NodeObjectDescriptor descriptor = DeclareTagProvider.createDeclareNodeObject(this.functionData.getName(), "var");
         descriptor.addProperty("literalType", JSTags.LiteralTag.Type.FunctionLiteral.name());
         return descriptor;
      }
   }

   @Override
   public InstrumentableNode materializeInstrumentableNodes(Set<Class<? extends Tag>> materializedTags) {
      if (!materializedTags.isEmpty()) {
         this.functionData.materialize();
      }

      return this;
   }

   public final JSFunctionData getFunctionData() {
      return this.functionData;
   }

   @Override
   public TruffleString getFunctionName() {
      return this.functionData.getName();
   }

   @Override
   public void setFunctionName(TruffleString name) {
      CompilerAsserts.neverPartOfCompilation();
      this.functionData.setName(name);
   }

   private static final class AutonomousFunctionExpressionNode extends JSFunctionExpressionNode {
      protected AutonomousFunctionExpressionNode(JSFunctionData functionData) {
         super(functionData);
      }

      @Override
      public Object executeWithRealm(VirtualFrame frame, JSRealm realm) {
         return JSFunction.create(realm, this.functionData);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new JSFunctionExpressionNode.AutonomousFunctionExpressionNode(this.functionData);
      }
   }

   private static final class ClosureFunctionExpressionNode extends JSFunctionExpressionNode {
      private final int blockScopeSlot;

      protected ClosureFunctionExpressionNode(JSFunctionData functionData, int blockScopeSlot) {
         super(functionData);
         this.blockScopeSlot = blockScopeSlot;
      }

      @Override
      public Object executeWithRealm(VirtualFrame frame, JSRealm realm) {
         MaterializedFrame closureFrame;
         if (this.blockScopeSlot >= 0) {
            Object blockScope = frame.getObject(this.blockScopeSlot);
            closureFrame = JSFrameUtil.castMaterializedFrame(blockScope);
         } else {
            closureFrame = frame.materialize();
         }

         return JSFunction.create(realm, this.functionData, closureFrame);
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new JSFunctionExpressionNode.ClosureFunctionExpressionNode(this.functionData, this.blockScopeSlot);
      }
   }

   private static final class LexicalThisAutonomousFunctionExpressionNode extends JSFunctionExpressionNode {
      @Node.Child
      private JavaScriptNode thisNode;

      protected LexicalThisAutonomousFunctionExpressionNode(JSFunctionData functionData, JavaScriptNode thisNode) {
         super(functionData);
         this.thisNode = thisNode;
      }

      @Override
      public Object executeWithRealm(VirtualFrame frame, JSRealm realm) {
         return JSFunction.createLexicalThis(realm, this.functionData, JSFrameUtil.NULL_MATERIALIZED_FRAME, this.thisNode.execute(frame));
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new JSFunctionExpressionNode.LexicalThisAutonomousFunctionExpressionNode(this.functionData, cloneUninitialized(this.thisNode, materializedTags));
      }
   }

   private static final class LexicalThisClosureFunctionExpressionNode extends JSFunctionExpressionNode {
      @Node.Child
      private JavaScriptNode thisNode;
      private final int blockScopeSlot;

      protected LexicalThisClosureFunctionExpressionNode(JSFunctionData functionData, int blockScopeSlot, JavaScriptNode thisNode) {
         super(functionData);
         this.blockScopeSlot = blockScopeSlot;
         this.thisNode = thisNode;
      }

      @Override
      public Object executeWithRealm(VirtualFrame frame, JSRealm realm) {
         MaterializedFrame closureFrame;
         if (this.blockScopeSlot >= 0) {
            Object blockScope = frame.getObject(this.blockScopeSlot);
            closureFrame = JSFrameUtil.castMaterializedFrame(blockScope);
         } else {
            closureFrame = frame.materialize();
         }

         return JSFunction.createLexicalThis(realm, this.functionData, closureFrame, this.thisNode.execute(frame));
      }

      @Override
      protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
         return new JSFunctionExpressionNode.LexicalThisClosureFunctionExpressionNode(
            this.functionData, this.blockScopeSlot, cloneUninitialized(this.thisNode, materializedTags)
         );
      }
   }
}
