package com.oracle.truffle.js.nodes;

import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.codec.NodeDecoder;
import com.oracle.truffle.js.decorators.DecoratorListEvaluationNode;
import com.oracle.truffle.js.nodes.access.DeclareGlobalNode;
import com.oracle.truffle.js.nodes.access.GetIteratorNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.access.ObjectLiteralNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.control.AbstractBlockNode;
import com.oracle.truffle.js.nodes.control.BreakTarget;
import com.oracle.truffle.js.nodes.control.ContinueTarget;
import com.oracle.truffle.js.nodes.control.IfNode;
import com.oracle.truffle.js.nodes.control.ReturnNode;
import com.oracle.truffle.js.nodes.function.AbstractBodyNode;
import com.oracle.truffle.js.nodes.function.AbstractFunctionArgumentsNode;
import com.oracle.truffle.js.nodes.function.BlockScopeNode;
import com.oracle.truffle.js.nodes.function.FunctionRootNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.function.JSFunctionExpressionNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import java.util.ArrayList;
import java.util.List;

@GeneratedBy(NodeFactory.class)
public class NodeFactoryDecoderGen implements NodeDecoder<NodeFactory> {
   private static final Class<?>[] CLASSES = new Class[]{
      int.class,
      NodeFactory.UnaryOperation.class,
      NodeFactory.BinaryOperation.class,
      JSErrorType.class,
      JavaScriptNode.class,
      ObjectLiteralNode.ObjectLiteralMemberNode.class,
      DecoratorListEvaluationNode.class
   };

   private NodeFactoryDecoderGen() {
   }

   public static NodeFactoryDecoderGen create() {
      return new NodeFactoryDecoderGen();
   }

   @Override
   public Class<?>[] getClasses() {
      return CLASSES;
   }

   public Object decodeNode(NodeDecoder.DecoderState decoder, NodeFactory nodeFactory) {
      switch (decoder.getUInt()) {
         case 0:
            return nodeFactory.createUnary((NodeFactory.UnaryOperation)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 1:
            return nodeFactory.createLocalVarInc(
               (NodeFactory.UnaryOperation)decoder.getObject(),
               (JSFrameSlot)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (ScopeFrameNode)decoder.getObject()
            );
         case 2:
            return nodeFactory.createToNumericOperand((JavaScriptNode)decoder.getObject());
         case 3:
            return nodeFactory.createDual((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 4:
            return nodeFactory.createBinary(
               (JSContext)decoder.getObject(),
               (NodeFactory.BinaryOperation)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject()
            );
         case 5:
            return nodeFactory.createTypeofIdentical((JavaScriptNode)decoder.getObject(), (TruffleString)decoder.getObject());
         case 6:
            return nodeFactory.createLogicalOr((JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 7:
            return nodeFactory.createNotUndefinedOr((JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 8:
            return nodeFactory.createConstant(decoder.getObject());
         case 9:
            return nodeFactory.createConstantBoolean((Boolean)decoder.getObject());
         case 10:
            return nodeFactory.createConstantInteger((Integer)decoder.getObject());
         case 11:
            return nodeFactory.createConstantSafeInteger((Long)decoder.getObject());
         case 12:
            return nodeFactory.createConstantNumericUnit();
         case 13:
            return nodeFactory.createConstantDouble((Double)decoder.getObject());
         case 14:
            return nodeFactory.createConstantString((TruffleString)decoder.getObject());
         case 15:
            return nodeFactory.createConstantUndefined();
         case 16:
            return nodeFactory.createConstantNull();
         case 17:
            return nodeFactory.createIf((JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 18:
            return nodeFactory.createSwitch(
               (JavaScriptNode[])decoder.getObject(), (JavaScriptNode[])decoder.getObject(), (int[])decoder.getObject(), (JavaScriptNode[])decoder.getObject()
            );
         case 19:
            return nodeFactory.createLoopNode((RepeatingNode)decoder.getObject());
         case 20:
            return nodeFactory.createWhileDoRepeatingNode((JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 21:
            return nodeFactory.createWhileDo((LoopNode)decoder.getObject());
         case 22:
            return nodeFactory.fixBlockNodeChild((AbstractBlockNode)decoder.getObject(), (Integer)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 23:
            return nodeFactory.fixNodeChild((Node)decoder.getObject(), (Node)decoder.getObject(), (Node)decoder.getObject());
         case 24:
            return nodeFactory.createDoWhileRepeatingNode((JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 25:
            return nodeFactory.createDoWhile((LoopNode)decoder.getObject());
         case 26:
            return nodeFactory.createDesugaredFor((LoopNode)decoder.getObject());
         case 27:
            return nodeFactory.createDesugaredForOf((LoopNode)decoder.getObject());
         case 28:
            return nodeFactory.createDesugaredForIn((LoopNode)decoder.getObject());
         case 29:
            return nodeFactory.createDesugaredForAwaitOf((LoopNode)decoder.getObject());
         case 30:
            return nodeFactory.createForRepeatingNode(
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (FrameDescriptor)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSFrameSlot)decoder.getObject()
            );
         case 31:
            return nodeFactory.createFor((LoopNode)decoder.getObject());
         case 32:
            return nodeFactory.createIterationScope((FrameDescriptor)decoder.getObject(), (JSFrameSlot)decoder.getObject());
         case 33:
            return nodeFactory.createBreak((BreakTarget)decoder.getObject());
         case 34:
            return nodeFactory.createContinue((ContinueTarget)decoder.getObject());
         case 35:
            return nodeFactory.createLabel((JavaScriptNode)decoder.getObject(), (BreakTarget)decoder.getObject());
         case 36:
            return nodeFactory.createEmpty();
         case 37:
            return nodeFactory.createVoidBlock((JavaScriptNode[])decoder.getObject());
         case 38:
            return nodeFactory.createExprBlock((JavaScriptNode[])decoder.getObject());
         case 39:
            return nodeFactory.createReturnTarget((JavaScriptNode)decoder.getObject());
         case 40:
            return nodeFactory.createFrameReturnTarget((JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 41:
            return nodeFactory.createContinueTarget((JavaScriptNode)decoder.getObject(), (ContinueTarget)decoder.getObject());
         case 42:
            return nodeFactory.createDirectBreakTarget((JavaScriptNode)decoder.getObject());
         case 43:
            return nodeFactory.createDebugger();
         case 44:
            return nodeFactory.createLocal((JSFrameSlot)decoder.getObject(), (Integer)decoder.getObject(), (Integer)decoder.getObject());
         case 45:
            return nodeFactory.createReadFrameSlot((JSFrameSlot)decoder.getObject(), (ScopeFrameNode)decoder.getObject());
         case 46:
            return nodeFactory.createReadFrameSlot((JSFrameSlot)decoder.getObject(), (ScopeFrameNode)decoder.getObject(), (Boolean)decoder.getObject());
         case 47:
            return nodeFactory.createReadCurrentFrameSlot((JSFrameSlot)decoder.getObject());
         case 48:
            return nodeFactory.createWriteFrameSlot((JSFrameSlot)decoder.getObject(), (ScopeFrameNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 49:
            return nodeFactory.createWriteFrameSlot(
               (JSFrameSlot)decoder.getObject(), (ScopeFrameNode)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (Boolean)decoder.getObject()
            );
         case 50:
            return nodeFactory.createWriteCurrentFrameSlot((JSFrameSlot)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 51:
            return nodeFactory.createScopeFrame((Integer)decoder.getObject(), (Integer)decoder.getObject(), (JSFrameSlot)decoder.getObject());
         case 52:
            return nodeFactory.createReadLexicalGlobal((TruffleString)decoder.getObject(), (Boolean)decoder.getObject(), (JSContext)decoder.getObject());
         case 53:
            return nodeFactory.createGlobalScope((JSContext)decoder.getObject());
         case 54:
            return nodeFactory.createGlobalScopeTDZCheck((JSContext)decoder.getObject(), (TruffleString)decoder.getObject(), (Boolean)decoder.getObject());
         case 55:
            return nodeFactory.createGlobalVarWrapper(
               (TruffleString)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSTargetableNode)decoder.getObject()
            );
         case 56:
            return nodeFactory.createClearFrameSlots((ScopeFrameNode)decoder.getObject(), (int[])decoder.getObject());
         case 57:
            return nodeFactory.createClearFrameSlotRange((ScopeFrameNode)decoder.getObject(), (Integer)decoder.getObject(), (Integer)decoder.getObject());
         case 58:
            return nodeFactory.createThrow((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 59:
            return nodeFactory.createTryCatch(
               (JSContext)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (BlockScopeNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject()
            );
         case 60:
            return nodeFactory.createTryFinally((JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 61:
            return nodeFactory.createFunctionCall((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (JavaScriptNode[])decoder.getObject());
         case 62:
            return nodeFactory.createFunctionCallWithNewTarget(
               (JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (JavaScriptNode[])decoder.getObject()
            );
         case 63:
            return nodeFactory.createFunctionArguments((JSContext)decoder.getObject(), (JavaScriptNode[])decoder.getObject());
         case 64:
            return nodeFactory.createNew(
               (JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (AbstractFunctionArgumentsNode)decoder.getObject()
            );
         case 65:
            return nodeFactory.createAccessThis();
         case 66:
            return nodeFactory.createAccessCallee((Integer)decoder.getObject());
         case 67:
            return nodeFactory.createAccessLexicalThis();
         case 68:
            return nodeFactory.createAccessArgument((Integer)decoder.getObject());
         case 69:
            return nodeFactory.createAccessVarArgs((Integer)decoder.getObject());
         case 70:
            return nodeFactory.createAccessRestArgument((JSContext)decoder.getObject(), (Integer)decoder.getObject());
         case 71:
            return nodeFactory.createAccessNewTarget();
         case 72:
            return nodeFactory.createAccessFrameArgument((ScopeFrameNode)decoder.getObject(), (Integer)decoder.getObject());
         case 73:
            return nodeFactory.createAccessHomeObject((JSContext)decoder.getObject());
         case 74:
            return nodeFactory.createReadElementNode((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 75:
            return nodeFactory.createWriteElementNode(
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSContext)decoder.getObject(),
               (Boolean)decoder.getObject()
            );
         case 76:
            return nodeFactory.createCompoundWriteElementNode(
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSWriteFrameSlotNode)decoder.getObject(),
               (JSContext)decoder.getObject(),
               (Boolean)decoder.getObject()
            );
         case 77:
            return nodeFactory.createReadProperty((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (TruffleString)decoder.getObject());
         case 78:
            return nodeFactory.createReadProperty(
               (JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (TruffleString)decoder.getObject(), (Boolean)decoder.getObject()
            );
         case 79:
            return nodeFactory.createWriteProperty(
               (JavaScriptNode)decoder.getObject(),
               (TruffleString)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSContext)decoder.getObject(),
               (Boolean)decoder.getObject()
            );
         case 80:
            return nodeFactory.createWriteProperty(
               (JavaScriptNode)decoder.getObject(),
               (TruffleString)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSContext)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject()
            );
         case 81:
            return nodeFactory.createWriteConstantVariable((JavaScriptNode)decoder.getObject(), (Boolean)decoder.getObject());
         case 82:
            return nodeFactory.createReadGlobalProperty((JSContext)decoder.getObject(), (TruffleString)decoder.getObject());
         case 83:
            return nodeFactory.createDeleteProperty(
               (JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (Boolean)decoder.getObject(), (JSContext)decoder.getObject()
            );
         case 84:
            return nodeFactory.createFunctionRootNode(
               (AbstractBodyNode)decoder.getObject(),
               (FrameDescriptor)decoder.getObject(),
               (JSFunctionData)decoder.getObject(),
               (SourceSection)decoder.getObject(),
               (TruffleString)decoder.getObject()
            );
         case 85:
            return nodeFactory.createModuleRootNode(
               (AbstractBodyNode)decoder.getObject(),
               (AbstractBodyNode)decoder.getObject(),
               (FrameDescriptor)decoder.getObject(),
               (JSFunctionData)decoder.getObject(),
               (SourceSection)decoder.getObject(),
               (TruffleString)decoder.getObject()
            );
         case 86:
            return nodeFactory.createConstructorRootNode((JSFunctionData)decoder.getObject(), (CallTarget)decoder.getObject(), (Boolean)decoder.getObject());
         case 87:
            return nodeFactory.createFunctionBody((JavaScriptNode)decoder.getObject());
         case 88:
            return nodeFactory.createFunctionExpression(
               (JSFunctionData)decoder.getObject(), (FunctionRootNode)decoder.getObject(), (JSFrameSlot)decoder.getObject()
            );
         case 89:
            return nodeFactory.createFunctionExpressionLexicalThis(
               (JSFunctionData)decoder.getObject(),
               (FunctionRootNode)decoder.getObject(),
               (JSFrameSlot)decoder.getObject(),
               (JavaScriptNode)decoder.getObject()
            );
         case 90:
            return nodeFactory.createPrepareThisBinding((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 91:
            return nodeFactory.createGlobalObject();
         case 92:
            return nodeFactory.createArgumentsObjectNode((JSContext)decoder.getObject(), (Boolean)decoder.getObject(), (Integer)decoder.getObject());
         case 93:
            return nodeFactory.createThrowError((JSErrorType)decoder.getObject(), (TruffleString)decoder.getObject());
         case 94:
            return nodeFactory.createObjectLiteral((JSContext)decoder.getObject(), (ArrayList<ObjectLiteralNode.ObjectLiteralMemberNode>)decoder.getObject());
         case 95:
            return nodeFactory.createArrayLiteral((JSContext)decoder.getObject(), (JavaScriptNode[])decoder.getObject());
         case 96:
            return nodeFactory.createArrayLiteralWithSpread((JSContext)decoder.getObject(), (JavaScriptNode[])decoder.getObject());
         case 97:
            return nodeFactory.createAccessorMember(
               (TruffleString)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject()
            );
         case 98:
            return nodeFactory.createDataMember(
               (TruffleString)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (Boolean)decoder.getObject()
            );
         case 99:
            return nodeFactory.createAutoAccessor(
               (TruffleString)decoder.getObject(), (Boolean)decoder.getObject(), (Boolean)decoder.getObject(), (JavaScriptNode)decoder.getObject()
            );
         case 100:
            return nodeFactory.createComputedAutoAccessor(
               (JavaScriptNode)decoder.getObject(), (Boolean)decoder.getObject(), (Boolean)decoder.getObject(), (JavaScriptNode)decoder.getObject()
            );
         case 101:
            return nodeFactory.createProtoMember((TruffleString)decoder.getObject(), (Boolean)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 102:
            return nodeFactory.createComputedDataMember(
               (JavaScriptNode)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject()
            );
         case 103:
            return nodeFactory.createComputedAccessorMember(
               (JavaScriptNode)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject()
            );
         case 104:
            return nodeFactory.createSpreadObjectMember((Boolean)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 105:
            return nodeFactory.createStaticBlockMember((JavaScriptNode)decoder.getObject());
         case 106:
            return nodeFactory.createClassDefinition(
               (JSContext)decoder.getObject(),
               (JSFunctionExpressionNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (ObjectLiteralNode.ObjectLiteralMemberNode[])decoder.getObject(),
               (JSWriteFrameSlotNode)decoder.getObject(),
               (JSWriteFrameSlotNode)decoder.getObject(),
               (JavaScriptNode[])decoder.getObject(),
               (DecoratorListEvaluationNode[])decoder.getObject(),
               (TruffleString)decoder.getObject(),
               (Integer)decoder.getObject(),
               (Integer)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (JSFrameSlot)decoder.getObject()
            );
         case 107:
            return nodeFactory.createMakeMethod((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 108:
            return nodeFactory.createSpreadArgument((JSContext)decoder.getObject(), (GetIteratorNode)decoder.getObject());
         case 109:
            return nodeFactory.createSpreadArray((JSContext)decoder.getObject(), (GetIteratorNode)decoder.getObject());
         case 110:
            return nodeFactory.createReturn((JavaScriptNode)decoder.getObject());
         case 111:
            return nodeFactory.createFrameReturn((JavaScriptNode)decoder.getObject());
         case 112:
            return nodeFactory.createTerminalPositionReturn((JavaScriptNode)decoder.getObject());
         case 113:
            return nodeFactory.createFunctionData(
               (JSContext)decoder.getObject(),
               (Integer)decoder.getObject(),
               (TruffleString)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject()
            );
         case 114:
            return nodeFactory.createAwait(
               (JSContext)decoder.getObject(),
               (JSFrameSlot)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSReadFrameSlotNode)decoder.getObject(),
               (JSReadFrameSlotNode)decoder.getObject()
            );
         case 115:
            return nodeFactory.createYield(
               (JSContext)decoder.getObject(),
               (JSFrameSlot)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (ReturnNode)decoder.getObject(),
               (JSWriteFrameSlotNode)decoder.getObject()
            );
         case 116:
            return nodeFactory.createAsyncGeneratorYield(
               (JSContext)decoder.getObject(),
               (JSFrameSlot)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSReadFrameSlotNode)decoder.getObject(),
               (JSReadFrameSlotNode)decoder.getObject(),
               (ReturnNode)decoder.getObject()
            );
         case 117:
            return nodeFactory.createAsyncGeneratorYieldStar(
               (JSContext)decoder.getObject(),
               (JSFrameSlot)decoder.getObject(),
               (JSFrameSlot)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSReadFrameSlotNode)decoder.getObject(),
               (JSReadFrameSlotNode)decoder.getObject(),
               (ReturnNode)decoder.getObject()
            );
         case 118:
            return nodeFactory.createAsyncFunctionBody(
               (JSContext)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSWriteFrameSlotNode)decoder.getObject(),
               (JSReadFrameSlotNode)decoder.getObject(),
               (JSWriteFrameSlotNode)decoder.getObject()
            );
         case 119:
            return nodeFactory.createGeneratorBody(
               (JSContext)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSWriteFrameSlotNode)decoder.getObject(),
               (JSReadFrameSlotNode)decoder.getObject()
            );
         case 120:
            return nodeFactory.createAsyncGeneratorBody(
               (JSContext)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSWriteFrameSlotNode)decoder.getObject(),
               (JSReadFrameSlotNode)decoder.getObject(),
               (JSWriteFrameSlotNode)decoder.getObject(),
               (JSReadFrameSlotNode)decoder.getObject()
            );
         case 121:
            return nodeFactory.createGeneratorWrapper((JavaScriptNode)decoder.getObject(), (JSFrameSlot)decoder.getObject());
         case 122:
            return nodeFactory.createGeneratorVoidBlock((JavaScriptNode[])decoder.getObject(), (JSFrameSlot)decoder.getObject());
         case 123:
            return nodeFactory.createGeneratorExprBlock((JavaScriptNode[])decoder.getObject(), (JSFrameSlot)decoder.getObject());
         case 124:
            return nodeFactory.createBlockScope(
               (JavaScriptNode)decoder.getObject(),
               (JSFrameSlot)decoder.getObject(),
               (FrameDescriptor)decoder.getObject(),
               (JSFrameSlot)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Integer)decoder.getObject(),
               (Integer)decoder.getObject()
            );
         case 125:
            return nodeFactory.createVirtualBlockScope((JavaScriptNode)decoder.getObject(), (Integer)decoder.getObject(), (Integer)decoder.getObject());
         case 126:
            return nodeFactory.createTemplateObject((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 127:
            return nodeFactory.createToString((JavaScriptNode)decoder.getObject());
         case 128:
            return nodeFactory.createRegExpLiteral((JSContext)decoder.getObject(), (TruffleString)decoder.getObject(), (TruffleString)decoder.getObject());
         case 129:
            return nodeFactory.createGetIterator((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 130:
            return nodeFactory.createGetAsyncIterator((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 131:
            return nodeFactory.createEnumerate((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (Boolean)decoder.getObject());
         case 132:
            return nodeFactory.createIteratorNext((JavaScriptNode)decoder.getObject());
         case 133:
            return nodeFactory.createIteratorComplete((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 134:
            return nodeFactory.createIteratorGetNextValue(
               (JSContext)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (Boolean)decoder.getObject()
            );
         case 135:
            return nodeFactory.createIteratorSetDone((JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 136:
            return nodeFactory.createIteratorIsDone((JavaScriptNode)decoder.getObject());
         case 137:
            return nodeFactory.createAsyncIteratorNext(
               (JSContext)decoder.getObject(),
               (JSFrameSlot)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSReadFrameSlotNode)decoder.getObject(),
               (JSReadFrameSlotNode)decoder.getObject()
            );
         case 138:
            return nodeFactory.createIteratorValue((JavaScriptNode)decoder.getObject());
         case 139:
            return nodeFactory.createAsyncIteratorCloseWrapper(
               (JSContext)decoder.getObject(),
               (JSFrameSlot)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSReadFrameSlotNode)decoder.getObject(),
               (JSReadFrameSlotNode)decoder.getObject()
            );
         case 140:
            return nodeFactory.createIteratorCloseIfNotDone(
               (JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject()
            );
         case 141:
            return nodeFactory.createIteratorToArray((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 142:
            return nodeFactory.createGetPrototype((JavaScriptNode)decoder.getObject());
         case 143:
            return nodeFactory.createSuperPropertyReference((JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 144:
            return nodeFactory.createTargetableWrapper((JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 145:
            return nodeFactory.createWith((JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 146:
            return nodeFactory.createWithVarWrapper(
               (TruffleString)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSTargetableNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject()
            );
         case 147:
            return nodeFactory.createWithTarget((JSContext)decoder.getObject(), (TruffleString)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 148:
            return nodeFactory.createNewTargetConstruct((JSContext)decoder.getObject(), (CallTarget)decoder.getObject());
         case 149:
            return nodeFactory.createNewTargetCall((JSContext)decoder.getObject(), (CallTarget)decoder.getObject());
         case 150:
            return nodeFactory.createDropNewTarget((JSContext)decoder.getObject(), (CallTarget)decoder.getObject());
         case 151:
            return nodeFactory.createConstructorRequiresNewRoot((JSFunctionData)decoder.getObject(), (SourceSection)decoder.getObject());
         case 152:
            return nodeFactory.createDerivedConstructorResult((JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 153:
            return nodeFactory.createDerivedConstructorThis((JavaScriptNode)decoder.getObject());
         case 154:
            return nodeFactory.createDefaultDerivedConstructorSuperCall((JavaScriptNode)decoder.getObject());
         case 155:
            return nodeFactory.createRequireObjectCoercible((JavaScriptNode)decoder.getObject());
         case 156:
            return nodeFactory.createFunctionFrameDescriptor();
         case 157:
            return nodeFactory.createBlockFrameDescriptor();
         case 158:
            return nodeFactory.createDeclareGlobalVariable((TruffleString)decoder.getObject(), (Boolean)decoder.getObject());
         case 159:
            return nodeFactory.createDeclareGlobalFunction(
               (TruffleString)decoder.getObject(), (Boolean)decoder.getObject(), (JavaScriptNode)decoder.getObject()
            );
         case 160:
            return nodeFactory.createDeclareGlobalLexicalVariable((TruffleString)decoder.getObject(), (Boolean)decoder.getObject());
         case 161:
            return nodeFactory.createGlobalDeclarationInstantiation((JSContext)decoder.getObject(), (List<DeclareGlobalNode>)decoder.getObject());
         case 162:
            return nodeFactory.copy((JavaScriptNode)decoder.getObject());
         case 163:
            return nodeFactory.createToObject((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 164:
            return nodeFactory.createToObjectFromWith((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (Boolean)decoder.getObject());
         case 165:
            return nodeFactory.createAccessArgumentsArrayDirectly(
               (JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (Integer)decoder.getObject()
            );
         case 166:
            return nodeFactory.createCallApplyArguments((JSFunctionCallNode)decoder.getObject());
         case 167:
            return nodeFactory.createGuardDisconnectedArgumentRead(
               (Integer)decoder.getObject(), (ReadElementNode)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (JSFrameSlot)decoder.getObject()
            );
         case 168:
            return nodeFactory.createGuardDisconnectedArgumentWrite(
               (Integer)decoder.getObject(),
               (WriteElementNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSFrameSlot)decoder.getObject()
            );
         case 169:
            return nodeFactory.createModuleBody((JavaScriptNode)decoder.getObject());
         case 170:
            return nodeFactory.createModuleInitializeEnvironment((JavaScriptNode)decoder.getObject());
         case 171:
            return nodeFactory.createModuleYield();
         case 172:
            return nodeFactory.createTopLevelAsyncModuleBody(
               (JSContext)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSWriteFrameSlotNode)decoder.getObject(),
               (JSWriteFrameSlotNode)decoder.getObject()
            );
         case 173:
            return nodeFactory.createImportMeta((JavaScriptNode)decoder.getObject());
         case 174:
            return nodeFactory.createResolveStarImport(
               (JSContext)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (Module.ModuleRequest)decoder.getObject(),
               (JSWriteFrameSlotNode)decoder.getObject()
            );
         case 175:
            return nodeFactory.createResolveNamedImport(
               (JSContext)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (Module.ModuleRequest)decoder.getObject(),
               (TruffleString)decoder.getObject(),
               (JSWriteFrameSlotNode)decoder.getObject()
            );
         case 176:
            return nodeFactory.createReadImportBinding((JavaScriptNode)decoder.getObject());
         case 177:
            return nodeFactory.createImportCall((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 178:
            return nodeFactory.createImportCall(
               (JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject()
            );
         case 179:
            return nodeFactory.createRestObject((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 180:
            return nodeFactory.createInitializeInstanceElements(
               (JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject()
            );
         case 181:
            return nodeFactory.createNewPrivateName((TruffleString)decoder.getObject());
         case 182:
            return nodeFactory.createPrivateFieldGet((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 183:
            return nodeFactory.createPrivateFieldSet(
               (JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject()
            );
         case 184:
            return nodeFactory.createPrivateFieldMember(
               (JavaScriptNode)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSWriteFrameSlotNode)decoder.getObject()
            );
         case 185:
            return nodeFactory.createPrivateMethodMember(
               (TruffleString)decoder.getObject(),
               (Boolean)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSWriteFrameSlotNode)decoder.getObject(),
               (Integer)decoder.getObject()
            );
         case 186:
            return nodeFactory.createPrivateAccessorMember(
               (Boolean)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSWriteFrameSlotNode)decoder.getObject(),
               (Integer)decoder.getObject()
            );
         case 187:
            return nodeFactory.createPrivateBrandCheck((JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 188:
            return nodeFactory.createGetPrivateBrand((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 189:
            return nodeFactory.createToPropertyKey((JavaScriptNode)decoder.getObject());
         case 190:
            return nodeFactory.createOptionalChain((JavaScriptNode)decoder.getObject());
         case 191:
            return nodeFactory.createOptionalChainShortCircuit((JavaScriptNode)decoder.getObject());
         case 192:
            return nodeFactory.createNamedEvaluation((JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 193:
            return nodeFactory.copyIfWithCondition((IfNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 194:
            return nodeFactory.createDebugScope((JSContext)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         case 195:
            return nodeFactory.createDebugVarWrapper(
               (TruffleString)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JavaScriptNode)decoder.getObject(),
               (JSTargetableNode)decoder.getObject()
            );
         case 196:
            return nodeFactory.createInternalSlotId((TruffleString)decoder.getObject(), (Integer)decoder.getObject());
         case 197:
            return nodeFactory.createPrivateFieldIn((JavaScriptNode)decoder.getObject(), (JavaScriptNode)decoder.getObject());
         default:
            throw new IllegalArgumentException("unknown node id");
      }
   }

   @Override
   public int getMethodIdFromSignature(String signature) {
      return NodeFactoryDecoderGen.EncoderSupport.getMethodIdFromSignature(signature);
   }

   @Override
   public int getChecksum() {
      return -1357490753;
   }

   private static class EncoderSupport {
      static int getMethodIdFromSignature(String signature) {
         switch (signature) {
            case "createUnary(com.oracle.truffle.js.nodes.NodeFactory.UnaryOperation,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 0;
            case "createLocalVarInc(com.oracle.truffle.js.nodes.NodeFactory.UnaryOperation,com.oracle.truffle.js.nodes.JSFrameSlot,boolean,com.oracle.truffle.js.nodes.access.ScopeFrameNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 1;
            case "createToNumericOperand(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 2;
            case "createDual(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 3;
            case "createBinary(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.NodeFactory.BinaryOperation,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 4;
            case "createTypeofIdentical(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.api.strings.TruffleString)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 5;
            case "createLogicalOr(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 6;
            case "createNotUndefinedOr(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 7;
            case "createConstant(java.lang.Object)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 8;
            case "createConstantBoolean(boolean)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 9;
            case "createConstantInteger(int)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 10;
            case "createConstantSafeInteger(long)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 11;
            case "createConstantNumericUnit()com.oracle.truffle.js.nodes.JavaScriptNode":
               return 12;
            case "createConstantDouble(double)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 13;
            case "createConstantString(com.oracle.truffle.api.strings.TruffleString)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 14;
            case "createConstantUndefined()com.oracle.truffle.js.nodes.JavaScriptNode":
               return 15;
            case "createConstantNull()com.oracle.truffle.js.nodes.JavaScriptNode":
               return 16;
            case "createIf(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.control.IfNode":
               return 17;
            case "createSwitch(com.oracle.truffle.js.nodes.JavaScriptNode[],com.oracle.truffle.js.nodes.JavaScriptNode[],int[],com.oracle.truffle.js.nodes.JavaScriptNode[])com.oracle.truffle.js.nodes.control.SwitchNode":
               return 18;
            case "createLoopNode(com.oracle.truffle.api.nodes.RepeatingNode)com.oracle.truffle.api.nodes.LoopNode":
               return 19;
            case "createWhileDoRepeatingNode(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.api.nodes.RepeatingNode":
               return 20;
            case "createWhileDo(com.oracle.truffle.api.nodes.LoopNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 21;
            case "fixBlockNodeChild(com.oracle.truffle.js.nodes.control.AbstractBlockNode,int,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.control.AbstractBlockNode":
               return 22;
            case "fixNodeChild(com.oracle.truffle.api.nodes.Node,com.oracle.truffle.api.nodes.Node,com.oracle.truffle.api.nodes.Node)com.oracle.truffle.api.nodes.Node":
               return 23;
            case "createDoWhileRepeatingNode(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.api.nodes.RepeatingNode":
               return 24;
            case "createDoWhile(com.oracle.truffle.api.nodes.LoopNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 25;
            case "createDesugaredFor(com.oracle.truffle.api.nodes.LoopNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 26;
            case "createDesugaredForOf(com.oracle.truffle.api.nodes.LoopNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 27;
            case "createDesugaredForIn(com.oracle.truffle.api.nodes.LoopNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 28;
            case "createDesugaredForAwaitOf(com.oracle.truffle.api.nodes.LoopNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 29;
            case "createForRepeatingNode(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.api.frame.FrameDescriptor,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JSFrameSlot)com.oracle.truffle.api.nodes.RepeatingNode":
               return 30;
            case "createFor(com.oracle.truffle.api.nodes.LoopNode)com.oracle.truffle.js.nodes.control.StatementNode":
               return 31;
            case "createIterationScope(com.oracle.truffle.api.frame.FrameDescriptor,com.oracle.truffle.js.nodes.JSFrameSlot)com.oracle.truffle.js.nodes.function.IterationScopeNode":
               return 32;
            case "createBreak(com.oracle.truffle.js.nodes.control.BreakTarget)com.oracle.truffle.js.nodes.control.BreakNode":
               return 33;
            case "createContinue(com.oracle.truffle.js.nodes.control.ContinueTarget)com.oracle.truffle.js.nodes.control.ContinueNode":
               return 34;
            case "createLabel(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.control.BreakTarget)com.oracle.truffle.js.nodes.control.LabelNode":
               return 35;
            case "createEmpty()com.oracle.truffle.js.nodes.JavaScriptNode":
               return 36;
            case "createVoidBlock(com.oracle.truffle.js.nodes.JavaScriptNode[])com.oracle.truffle.js.nodes.JavaScriptNode":
               return 37;
            case "createExprBlock(com.oracle.truffle.js.nodes.JavaScriptNode[])com.oracle.truffle.js.nodes.JavaScriptNode":
               return 38;
            case "createReturnTarget(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.control.ReturnTargetNode":
               return 39;
            case "createFrameReturnTarget(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.control.ReturnTargetNode":
               return 40;
            case "createContinueTarget(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.control.ContinueTarget)com.oracle.truffle.js.nodes.control.ContinueTargetNode":
               return 41;
            case "createDirectBreakTarget(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.control.DirectBreakTargetNode":
               return 42;
            case "createDebugger()com.oracle.truffle.js.nodes.JavaScriptNode":
               return 43;
            case "createLocal(com.oracle.truffle.js.nodes.JSFrameSlot,int,int)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 44;
            case "createReadFrameSlot(com.oracle.truffle.js.nodes.JSFrameSlot,com.oracle.truffle.js.nodes.access.ScopeFrameNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 45;
            case "createReadFrameSlot(com.oracle.truffle.js.nodes.JSFrameSlot,com.oracle.truffle.js.nodes.access.ScopeFrameNode,boolean)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 46;
            case "createReadCurrentFrameSlot(com.oracle.truffle.js.nodes.JSFrameSlot)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 47;
            case "createWriteFrameSlot(com.oracle.truffle.js.nodes.JSFrameSlot,com.oracle.truffle.js.nodes.access.ScopeFrameNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode":
               return 48;
            case "createWriteFrameSlot(com.oracle.truffle.js.nodes.JSFrameSlot,com.oracle.truffle.js.nodes.access.ScopeFrameNode,com.oracle.truffle.js.nodes.JavaScriptNode,boolean)com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode":
               return 49;
            case "createWriteCurrentFrameSlot(com.oracle.truffle.js.nodes.JSFrameSlot,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode":
               return 50;
            case "createScopeFrame(int,int,com.oracle.truffle.js.nodes.JSFrameSlot)com.oracle.truffle.js.nodes.access.ScopeFrameNode":
               return 51;
            case "createReadLexicalGlobal(com.oracle.truffle.api.strings.TruffleString,boolean,com.oracle.truffle.js.runtime.JSContext)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 52;
            case "createGlobalScope(com.oracle.truffle.js.runtime.JSContext)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 53;
            case "createGlobalScopeTDZCheck(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.api.strings.TruffleString,boolean)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 54;
            case "createGlobalVarWrapper(com.oracle.truffle.api.strings.TruffleString,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.access.JSTargetableNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 55;
            case "createClearFrameSlots(com.oracle.truffle.js.nodes.access.ScopeFrameNode,int[])com.oracle.truffle.js.nodes.JavaScriptNode":
               return 56;
            case "createClearFrameSlotRange(com.oracle.truffle.js.nodes.access.ScopeFrameNode,int,int)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 57;
            case "createThrow(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 58;
            case "createTryCatch(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.function.BlockScopeNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 59;
            case "createTryFinally(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 60;
            case "createFunctionCall(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode[])com.oracle.truffle.js.nodes.JavaScriptNode":
               return 61;
            case "createFunctionCallWithNewTarget(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode[])com.oracle.truffle.js.nodes.JavaScriptNode":
               return 62;
            case "createFunctionArguments(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode[])com.oracle.truffle.js.nodes.function.AbstractFunctionArgumentsNode":
               return 63;
            case "createNew(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.function.AbstractFunctionArgumentsNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 64;
            case "createAccessThis()com.oracle.truffle.js.nodes.JavaScriptNode":
               return 65;
            case "createAccessCallee(int)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 66;
            case "createAccessLexicalThis()com.oracle.truffle.js.nodes.JavaScriptNode":
               return 67;
            case "createAccessArgument(int)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 68;
            case "createAccessVarArgs(int)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 69;
            case "createAccessRestArgument(com.oracle.truffle.js.runtime.JSContext,int)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 70;
            case "createAccessNewTarget()com.oracle.truffle.js.nodes.JavaScriptNode":
               return 71;
            case "createAccessFrameArgument(com.oracle.truffle.js.nodes.access.ScopeFrameNode,int)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 72;
            case "createAccessHomeObject(com.oracle.truffle.js.runtime.JSContext)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 73;
            case "createReadElementNode(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.access.ReadElementNode":
               return 74;
            case "createWriteElementNode(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.runtime.JSContext,boolean)com.oracle.truffle.js.nodes.access.WriteElementNode":
               return 75;
            case "createCompoundWriteElementNode(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode,com.oracle.truffle.js.runtime.JSContext,boolean)com.oracle.truffle.js.nodes.access.WriteElementNode":
               return 76;
            case "createReadProperty(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.api.strings.TruffleString)com.oracle.truffle.js.nodes.access.JSTargetableNode":
               return 77;
            case "createReadProperty(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.api.strings.TruffleString,boolean)com.oracle.truffle.js.nodes.access.JSTargetableNode":
               return 78;
            case "createWriteProperty(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.api.strings.TruffleString,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.runtime.JSContext,boolean)com.oracle.truffle.js.nodes.access.WritePropertyNode":
               return 79;
            case "createWriteProperty(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.api.strings.TruffleString,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.runtime.JSContext,boolean,boolean,boolean)com.oracle.truffle.js.nodes.access.WritePropertyNode":
               return 80;
            case "createWriteConstantVariable(com.oracle.truffle.js.nodes.JavaScriptNode,boolean)com.oracle.truffle.js.nodes.access.ConstantVariableWriteNode":
               return 81;
            case "createReadGlobalProperty(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.api.strings.TruffleString)com.oracle.truffle.js.nodes.access.JSTargetableNode":
               return 82;
            case "createDeleteProperty(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,boolean,com.oracle.truffle.js.runtime.JSContext)com.oracle.truffle.js.nodes.access.JSTargetableNode":
               return 83;
            case "createFunctionRootNode(com.oracle.truffle.js.nodes.function.AbstractBodyNode,com.oracle.truffle.api.frame.FrameDescriptor,com.oracle.truffle.js.runtime.builtins.JSFunctionData,com.oracle.truffle.api.source.SourceSection,com.oracle.truffle.api.strings.TruffleString)com.oracle.truffle.js.nodes.function.FunctionRootNode":
               return 84;
            case "createModuleRootNode(com.oracle.truffle.js.nodes.function.AbstractBodyNode,com.oracle.truffle.js.nodes.function.AbstractBodyNode,com.oracle.truffle.api.frame.FrameDescriptor,com.oracle.truffle.js.runtime.builtins.JSFunctionData,com.oracle.truffle.api.source.SourceSection,com.oracle.truffle.api.strings.TruffleString)com.oracle.truffle.js.nodes.function.FunctionRootNode":
               return 85;
            case "createConstructorRootNode(com.oracle.truffle.js.runtime.builtins.JSFunctionData,com.oracle.truffle.api.CallTarget,boolean)com.oracle.truffle.js.nodes.function.ConstructorRootNode":
               return 86;
            case "createFunctionBody(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.function.FunctionBodyNode":
               return 87;
            case "createFunctionExpression(com.oracle.truffle.js.runtime.builtins.JSFunctionData,com.oracle.truffle.js.nodes.function.FunctionRootNode,com.oracle.truffle.js.nodes.JSFrameSlot)com.oracle.truffle.js.nodes.function.JSFunctionExpressionNode":
               return 88;
            case "createFunctionExpressionLexicalThis(com.oracle.truffle.js.runtime.builtins.JSFunctionData,com.oracle.truffle.js.nodes.function.FunctionRootNode,com.oracle.truffle.js.nodes.JSFrameSlot,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.function.JSFunctionExpressionNode":
               return 89;
            case "createPrepareThisBinding(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 90;
            case "createGlobalObject()com.oracle.truffle.js.nodes.JavaScriptNode":
               return 91;
            case "createArgumentsObjectNode(com.oracle.truffle.js.runtime.JSContext,boolean,int)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 92;
            case "createThrowError(com.oracle.truffle.js.runtime.JSErrorType,com.oracle.truffle.api.strings.TruffleString)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 93;
            case "createObjectLiteral(com.oracle.truffle.js.runtime.JSContext,java.util.ArrayList)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 94;
            case "createArrayLiteral(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode[])com.oracle.truffle.js.nodes.JavaScriptNode":
               return 95;
            case "createArrayLiteralWithSpread(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode[])com.oracle.truffle.js.nodes.JavaScriptNode":
               return 96;
            case "createAccessorMember(com.oracle.truffle.api.strings.TruffleString,boolean,boolean,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.access.ObjectLiteralNode.ObjectLiteralMemberNode":
               return 97;
            case "createDataMember(com.oracle.truffle.api.strings.TruffleString,boolean,boolean,com.oracle.truffle.js.nodes.JavaScriptNode,boolean)com.oracle.truffle.js.nodes.access.ObjectLiteralNode.ObjectLiteralMemberNode":
               return 98;
            case "createAutoAccessor(com.oracle.truffle.api.strings.TruffleString,boolean,boolean,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.access.ObjectLiteralNode.ObjectLiteralMemberNode":
               return 99;
            case "createComputedAutoAccessor(com.oracle.truffle.js.nodes.JavaScriptNode,boolean,boolean,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.access.ObjectLiteralNode.ObjectLiteralMemberNode":
               return 100;
            case "createProtoMember(com.oracle.truffle.api.strings.TruffleString,boolean,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.access.ObjectLiteralNode.ObjectLiteralMemberNode":
               return 101;
            case "createComputedDataMember(com.oracle.truffle.js.nodes.JavaScriptNode,boolean,boolean,com.oracle.truffle.js.nodes.JavaScriptNode,boolean,boolean)com.oracle.truffle.js.nodes.access.ObjectLiteralNode.ObjectLiteralMemberNode":
               return 102;
            case "createComputedAccessorMember(com.oracle.truffle.js.nodes.JavaScriptNode,boolean,boolean,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.access.ObjectLiteralNode.ObjectLiteralMemberNode":
               return 103;
            case "createSpreadObjectMember(boolean,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.access.ObjectLiteralNode.ObjectLiteralMemberNode":
               return 104;
            case "createStaticBlockMember(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.access.ObjectLiteralNode.ObjectLiteralMemberNode":
               return 105;
            case "createClassDefinition(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.function.JSFunctionExpressionNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.access.ObjectLiteralNode.ObjectLiteralMemberNode[],com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode,com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode,com.oracle.truffle.js.nodes.JavaScriptNode[],com.oracle.truffle.js.decorators.DecoratorListEvaluationNode[],com.oracle.truffle.api.strings.TruffleString,int,int,boolean,com.oracle.truffle.js.nodes.JSFrameSlot)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 106;
            case "createMakeMethod(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 107;
            case "createSpreadArgument(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.access.GetIteratorNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 108;
            case "createSpreadArray(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.access.GetIteratorNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 109;
            case "createReturn(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.control.ReturnNode":
               return 110;
            case "createFrameReturn(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.control.ReturnNode":
               return 111;
            case "createTerminalPositionReturn(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.control.ReturnNode":
               return 112;
            case "createFunctionData(com.oracle.truffle.js.runtime.JSContext,int,com.oracle.truffle.api.strings.TruffleString,boolean,boolean,boolean,boolean,boolean,boolean,boolean,boolean,boolean,boolean)com.oracle.truffle.js.runtime.builtins.JSFunctionData":
               return 113;
            case "createAwait(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JSFrameSlot,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode,com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 114;
            case "createYield(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JSFrameSlot,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,boolean,com.oracle.truffle.js.nodes.control.ReturnNode,com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 115;
            case "createAsyncGeneratorYield(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JSFrameSlot,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode,com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode,com.oracle.truffle.js.nodes.control.ReturnNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 116;
            case "createAsyncGeneratorYieldStar(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JSFrameSlot,com.oracle.truffle.js.nodes.JSFrameSlot,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode,com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode,com.oracle.truffle.js.nodes.control.ReturnNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 117;
            case "createAsyncFunctionBody(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode,com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode,com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 118;
            case "createGeneratorBody(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode,com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 119;
            case "createAsyncGeneratorBody(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode,com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode,com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode,com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 120;
            case "createGeneratorWrapper(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JSFrameSlot)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 121;
            case "createGeneratorVoidBlock(com.oracle.truffle.js.nodes.JavaScriptNode[],com.oracle.truffle.js.nodes.JSFrameSlot)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 122;
            case "createGeneratorExprBlock(com.oracle.truffle.js.nodes.JavaScriptNode[],com.oracle.truffle.js.nodes.JSFrameSlot)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 123;
            case "createBlockScope(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JSFrameSlot,com.oracle.truffle.api.frame.FrameDescriptor,com.oracle.truffle.js.nodes.JSFrameSlot,boolean,boolean,boolean,boolean,int,int)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 124;
            case "createVirtualBlockScope(com.oracle.truffle.js.nodes.JavaScriptNode,int,int)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 125;
            case "createTemplateObject(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 126;
            case "createToString(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 127;
            case "createRegExpLiteral(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.api.strings.TruffleString,com.oracle.truffle.api.strings.TruffleString)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 128;
            case "createGetIterator(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.access.GetIteratorNode":
               return 129;
            case "createGetAsyncIterator(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 130;
            case "createEnumerate(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,boolean)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 131;
            case "createIteratorNext(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 132;
            case "createIteratorComplete(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 133;
            case "createIteratorGetNextValue(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,boolean,boolean)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 134;
            case "createIteratorSetDone(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 135;
            case "createIteratorIsDone(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 136;
            case "createAsyncIteratorNext(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JSFrameSlot,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode,com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 137;
            case "createIteratorValue(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 138;
            case "createAsyncIteratorCloseWrapper(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JSFrameSlot,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode,com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 139;
            case "createIteratorCloseIfNotDone(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 140;
            case "createIteratorToArray(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.access.IteratorToArrayNode":
               return 141;
            case "createGetPrototype(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 142;
            case "createSuperPropertyReference(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.access.JSTargetableNode":
               return 143;
            case "createTargetableWrapper(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.access.JSTargetableNode":
               return 144;
            case "createWith(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 145;
            case "createWithVarWrapper(com.oracle.truffle.api.strings.TruffleString,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.access.JSTargetableNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 146;
            case "createWithTarget(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.api.strings.TruffleString,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 147;
            case "createNewTargetConstruct(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.api.CallTarget)com.oracle.truffle.js.runtime.JavaScriptRootNode":
               return 148;
            case "createNewTargetCall(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.api.CallTarget)com.oracle.truffle.js.runtime.JavaScriptRootNode":
               return 149;
            case "createDropNewTarget(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.api.CallTarget)com.oracle.truffle.js.runtime.JavaScriptRootNode":
               return 150;
            case "createConstructorRequiresNewRoot(com.oracle.truffle.js.runtime.builtins.JSFunctionData,com.oracle.truffle.api.source.SourceSection)com.oracle.truffle.js.runtime.JavaScriptRootNode":
               return 151;
            case "createDerivedConstructorResult(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 152;
            case "createDerivedConstructorThis(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 153;
            case "createDefaultDerivedConstructorSuperCall(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 154;
            case "createRequireObjectCoercible(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 155;
            case "createFunctionFrameDescriptor()com.oracle.truffle.js.nodes.JSFrameDescriptor":
               return 156;
            case "createBlockFrameDescriptor()com.oracle.truffle.js.nodes.JSFrameDescriptor":
               return 157;
            case "createDeclareGlobalVariable(com.oracle.truffle.api.strings.TruffleString,boolean)com.oracle.truffle.js.nodes.access.DeclareGlobalNode":
               return 158;
            case "createDeclareGlobalFunction(com.oracle.truffle.api.strings.TruffleString,boolean,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.access.DeclareGlobalNode":
               return 159;
            case "createDeclareGlobalLexicalVariable(com.oracle.truffle.api.strings.TruffleString,boolean)com.oracle.truffle.js.nodes.access.DeclareGlobalNode":
               return 160;
            case "createGlobalDeclarationInstantiation(com.oracle.truffle.js.runtime.JSContext,java.util.List)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 161;
            case "copy(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 162;
            case "createToObject(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 163;
            case "createToObjectFromWith(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,boolean)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 164;
            case "createAccessArgumentsArrayDirectly(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,int)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 165;
            case "createCallApplyArguments(com.oracle.truffle.js.nodes.function.JSFunctionCallNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 166;
            case "createGuardDisconnectedArgumentRead(int,com.oracle.truffle.js.nodes.access.ReadElementNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JSFrameSlot)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 167;
            case "createGuardDisconnectedArgumentWrite(int,com.oracle.truffle.js.nodes.access.WriteElementNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JSFrameSlot)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 168;
            case "createModuleBody(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 169;
            case "createModuleInitializeEnvironment(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 170;
            case "createModuleYield()com.oracle.truffle.js.nodes.JavaScriptNode":
               return 171;
            case "createTopLevelAsyncModuleBody(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode,com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 172;
            case "createImportMeta(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 173;
            case "createResolveStarImport(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.js.parser.ir.Module.ModuleRequest,com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 174;
            case "createResolveNamedImport(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.js.parser.ir.Module.ModuleRequest,com.oracle.truffle.api.strings.TruffleString,com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 175;
            case "createReadImportBinding(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 176;
            case "createImportCall(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 177;
            case "createImportCall(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 178;
            case "createRestObject(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 179;
            case "createInitializeInstanceElements(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 180;
            case "createNewPrivateName(com.oracle.truffle.api.strings.TruffleString)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 181;
            case "createPrivateFieldGet(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 182;
            case "createPrivateFieldSet(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 183;
            case "createPrivateFieldMember(com.oracle.truffle.js.nodes.JavaScriptNode,boolean,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode)com.oracle.truffle.js.nodes.access.ObjectLiteralNode.ObjectLiteralMemberNode":
               return 184;
            case "createPrivateMethodMember(com.oracle.truffle.api.strings.TruffleString,boolean,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode,int)com.oracle.truffle.js.nodes.access.ObjectLiteralNode.ObjectLiteralMemberNode":
               return 185;
            case "createPrivateAccessorMember(boolean,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode,int)com.oracle.truffle.js.nodes.access.ObjectLiteralNode.ObjectLiteralMemberNode":
               return 186;
            case "createPrivateBrandCheck(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 187;
            case "createGetPrivateBrand(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 188;
            case "createToPropertyKey(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 189;
            case "createOptionalChain(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 190;
            case "createOptionalChainShortCircuit(com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 191;
            case "createNamedEvaluation(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 192;
            case "copyIfWithCondition(com.oracle.truffle.js.nodes.control.IfNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.control.IfNode":
               return 193;
            case "createDebugScope(com.oracle.truffle.js.runtime.JSContext,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 194;
            case "createDebugVarWrapper(com.oracle.truffle.api.strings.TruffleString,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.access.JSTargetableNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 195;
            case "createInternalSlotId(com.oracle.truffle.api.strings.TruffleString,int)com.oracle.truffle.js.runtime.util.InternalSlotId":
               return 196;
            case "createPrivateFieldIn(com.oracle.truffle.js.nodes.JavaScriptNode,com.oracle.truffle.js.nodes.JavaScriptNode)com.oracle.truffle.js.nodes.JavaScriptNode":
               return 197;
            default:
               throw new IllegalArgumentException("unknown method: " + signature);
         }
      }
   }
}
