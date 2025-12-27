package com.oracle.truffle.js.parser;

import com.oracle.js.parser.Lexer;
import com.oracle.js.parser.TokenType;
import com.oracle.js.parser.ir.AccessNode;
import com.oracle.js.parser.ir.BaseNode;
import com.oracle.js.parser.ir.BinaryNode;
import com.oracle.js.parser.ir.Block;
import com.oracle.js.parser.ir.BlockExpression;
import com.oracle.js.parser.ir.BlockStatement;
import com.oracle.js.parser.ir.BreakNode;
import com.oracle.js.parser.ir.CallNode;
import com.oracle.js.parser.ir.CaseNode;
import com.oracle.js.parser.ir.CatchNode;
import com.oracle.js.parser.ir.ClassElement;
import com.oracle.js.parser.ir.ClassNode;
import com.oracle.js.parser.ir.ContinueNode;
import com.oracle.js.parser.ir.DebuggerNode;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.ExpressionStatement;
import com.oracle.js.parser.ir.ForNode;
import com.oracle.js.parser.ir.FunctionNode;
import com.oracle.js.parser.ir.IdentNode;
import com.oracle.js.parser.ir.IfNode;
import com.oracle.js.parser.ir.IndexNode;
import com.oracle.js.parser.ir.JoinPredecessorExpression;
import com.oracle.js.parser.ir.LabelNode;
import com.oracle.js.parser.ir.LexicalContext;
import com.oracle.js.parser.ir.LexicalContextNode;
import com.oracle.js.parser.ir.LiteralNode;
import com.oracle.js.parser.ir.Module;
import com.oracle.js.parser.ir.Node;
import com.oracle.js.parser.ir.ObjectNode;
import com.oracle.js.parser.ir.ParameterNode;
import com.oracle.js.parser.ir.PropertyNode;
import com.oracle.js.parser.ir.Scope;
import com.oracle.js.parser.ir.Statement;
import com.oracle.js.parser.ir.SwitchNode;
import com.oracle.js.parser.ir.Symbol;
import com.oracle.js.parser.ir.TemplateLiteralNode;
import com.oracle.js.parser.ir.TernaryNode;
import com.oracle.js.parser.ir.ThrowNode;
import com.oracle.js.parser.ir.TryNode;
import com.oracle.js.parser.ir.UnaryNode;
import com.oracle.js.parser.ir.VarNode;
import com.oracle.js.parser.ir.WhileNode;
import com.oracle.js.parser.ir.WithNode;
import com.oracle.js.parser.ir.visitor.NodeVisitor;
import com.oracle.js.parser.ir.visitor.TranslatorNodeVisitor;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.frame.FrameSlotKind;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.StandardTags;
import com.oracle.truffle.api.nodes.LoopNode;
import com.oracle.truffle.api.nodes.NodeUtil;
import com.oracle.truffle.api.nodes.RepeatingNode;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.decorators.DecoratorListEvaluationNode;
import com.oracle.truffle.js.nodes.JSFrameDescriptor;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.nodes.ReadNode;
import com.oracle.truffle.js.nodes.RepeatableNode;
import com.oracle.truffle.js.nodes.ScriptNode;
import com.oracle.truffle.js.nodes.access.ArrayLiteralNode;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.nodes.access.DeclareEvalVariableNode;
import com.oracle.truffle.js.nodes.access.DeclareGlobalNode;
import com.oracle.truffle.js.nodes.access.GetIteratorNode;
import com.oracle.truffle.js.nodes.access.GlobalPropertyNode;
import com.oracle.truffle.js.nodes.access.JSConstantNode;
import com.oracle.truffle.js.nodes.access.JSReadFrameSlotNode;
import com.oracle.truffle.js.nodes.access.JSWriteFrameSlotNode;
import com.oracle.truffle.js.nodes.access.ObjectLiteralNode;
import com.oracle.truffle.js.nodes.access.OptionalChainNode;
import com.oracle.truffle.js.nodes.access.ReadElementNode;
import com.oracle.truffle.js.nodes.access.ScopeFrameNode;
import com.oracle.truffle.js.nodes.access.SuperPropertyReferenceNode;
import com.oracle.truffle.js.nodes.access.VarWrapperNode;
import com.oracle.truffle.js.nodes.access.WriteElementNode;
import com.oracle.truffle.js.nodes.access.WriteNode;
import com.oracle.truffle.js.nodes.access.WritePropertyNode;
import com.oracle.truffle.js.nodes.binary.DualNode;
import com.oracle.truffle.js.nodes.binary.JSBinaryNode;
import com.oracle.truffle.js.nodes.binary.JSTypeofIdenticalNode;
import com.oracle.truffle.js.nodes.control.AbstractBlockNode;
import com.oracle.truffle.js.nodes.control.BreakTarget;
import com.oracle.truffle.js.nodes.control.ContinueTarget;
import com.oracle.truffle.js.nodes.control.DiscardResultNode;
import com.oracle.truffle.js.nodes.control.EmptyNode;
import com.oracle.truffle.js.nodes.control.GeneratorWrapperNode;
import com.oracle.truffle.js.nodes.control.ModuleYieldNode;
import com.oracle.truffle.js.nodes.control.ResumableNode;
import com.oracle.truffle.js.nodes.control.ReturnNode;
import com.oracle.truffle.js.nodes.control.ReturnTargetNode;
import com.oracle.truffle.js.nodes.control.SequenceNode;
import com.oracle.truffle.js.nodes.control.StatementNode;
import com.oracle.truffle.js.nodes.control.SuspendNode;
import com.oracle.truffle.js.nodes.function.AbstractFunctionArgumentsNode;
import com.oracle.truffle.js.nodes.function.BlockScopeNode;
import com.oracle.truffle.js.nodes.function.EvalNode;
import com.oracle.truffle.js.nodes.function.FunctionBodyNode;
import com.oracle.truffle.js.nodes.function.FunctionRootNode;
import com.oracle.truffle.js.nodes.function.JSFunctionCallNode;
import com.oracle.truffle.js.nodes.function.JSFunctionExpressionNode;
import com.oracle.truffle.js.nodes.function.JSNewNode;
import com.oracle.truffle.js.nodes.function.SpreadArgumentNode;
import com.oracle.truffle.js.nodes.unary.JSUnaryNode;
import com.oracle.truffle.js.nodes.unary.TypeOfNode;
import com.oracle.truffle.js.nodes.unary.VoidNode;
import com.oracle.truffle.js.parser.env.BlockEnvironment;
import com.oracle.truffle.js.parser.env.DebugEnvironment;
import com.oracle.truffle.js.parser.env.Environment;
import com.oracle.truffle.js.parser.env.EvalEnvironment;
import com.oracle.truffle.js.parser.env.FunctionEnvironment;
import com.oracle.truffle.js.parser.env.GlobalEnvironment;
import com.oracle.truffle.js.parser.env.PrivateEnvironment;
import com.oracle.truffle.js.parser.env.WithEnvironment;
import com.oracle.truffle.js.parser.internal.ir.debug.PrintVisitor;
import com.oracle.truffle.js.runtime.BigInt;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.JSConfig;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSErrorType;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.InternalSlotId;
import com.oracle.truffle.js.runtime.util.Pair;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;
import java.util.function.UnaryOperator;

abstract class GraalJSTranslator extends TranslatorNodeVisitor<LexicalContext, JavaScriptNode> {
   public static final TruffleString SUPER_CALLED_TWICE = Strings.constant("super() called twice");
   public static final TruffleString UNSUPPORTED_REFERENCE_TO_SUPER = Strings.constant("Unsupported reference to 'super'");
   public static final String LINE__ = "__LINE__";
   public static final String FILE__ = "__FILE__";
   public static final String DIR__ = "__DIR__";
   public static final String IMPORT = "import";
   public static final String IMPORT_META = "import.meta";
   public static final TruffleString GENERATOREXPR = Strings.constant("generatorexpr");
   public static final TruffleString GENERATORSTATE = Strings.constant("generatorstate");
   public static final String ARGUMENTS = "arguments";
   public static final JavaScriptNode[] EMPTY_NODE_ARRAY = new JavaScriptNode[0];
   private static final JavaScriptNode ANY_JAVA_SCRIPT_NODE = new JavaScriptNode() {
      @Override
      public Object execute(VirtualFrame frame) {
         CompilerDirectives.transferToInterpreter();
         throw new UnsupportedOperationException();
      }
   };
   private static final SourceSection unavailableInternalSection = Source.newBuilder("js", "<internal>", "<internal>")
      .mimeType("application/javascript")
      .internal(true)
      .build()
      .createUnavailableSection();
   private Environment environment;
   protected final JSContext context;
   protected final NodeFactory factory;
   protected final Source source;
   protected final List<String> argumentNames;
   protected final int sourceLength;
   protected final int prologLength;
   private final boolean isParentStrict;

   protected GraalJSTranslator(
      LexicalContext lc,
      NodeFactory factory,
      JSContext context,
      Source source,
      List<String> argumentNames,
      int prologLength,
      Environment environment,
      boolean isParentStrict
   ) {
      super(lc);
      this.context = context;
      this.environment = environment;
      this.factory = factory;
      this.source = source;
      this.argumentNames = argumentNames;
      this.isParentStrict = isParentStrict;
      this.sourceLength = source.getCharacters().length();
      this.prologLength = prologLength;
   }

   protected final JavaScriptNode transform(Node node) {
      return node != null ? node.accept(this) : null;
   }

   private JavaScriptNode tagStatement(JavaScriptNode resultNode, Node parseNode) {
      if (!resultNode.hasSourceSection()) {
         this.assignSourceSection(resultNode, parseNode);
      }

      assert resultNode.getSourceSection() != null;

      if (resultNode instanceof VarWrapperNode) {
         this.tagStatement(((VarWrapperNode)resultNode).getDelegateNode(), parseNode);
      } else {
         resultNode.addStatementTag();
      }

      return resultNode;
   }

   private JavaScriptNode tagExpression(JavaScriptNode resultNode, Node parseNode) {
      if (!resultNode.hasSourceSection()) {
         this.assignSourceSection(resultNode, parseNode);
      }

      assert resultNode.getSourceSection() != null;

      if (resultNode instanceof VarWrapperNode) {
         this.tagExpression(((VarWrapperNode)resultNode).getDelegateNode(), parseNode);
      } else {
         resultNode.addExpressionTag();
      }

      return resultNode;
   }

   private static JavaScriptNode tagCall(JavaScriptNode resultNode) {
      resultNode.addCallTag();
      return resultNode;
   }

   private JavaScriptNode tagBody(JavaScriptNode resultNode, Node parseNode) {
      if (!resultNode.hasSourceSection()) {
         this.assignSourceSection(resultNode, parseNode);
      }

      assert resultNode.getSourceSection() != null;

      if (resultNode instanceof VarWrapperNode) {
         this.tagBody(((VarWrapperNode)resultNode).getDelegateNode(), parseNode);
      } else if (resultNode instanceof BlockScopeNode) {
         JavaScriptNode blockNode = ((BlockScopeNode)resultNode).getBlock();
         blockNode.addRootBodyTag();
         this.ensureHasSourceSection(blockNode, parseNode);
      } else {
         resultNode.addRootBodyTag();
      }

      return resultNode;
   }

   private FunctionEnvironment currentFunction() {
      return this.environment.function();
   }

   private JavaScriptNode createBlock(JavaScriptNode... statements) {
      return this.createBlock(statements, false, false);
   }

   private JavaScriptNode createBlock(JavaScriptNode[] statements, boolean terminal, boolean expressionBlock) {
      return !terminal && !expressionBlock && !this.currentFunction().returnsLastStatementResult()
         ? this.factory.createVoidBlock(statements)
         : this.factory.createExprBlock(statements);
   }

   protected final ScriptNode translateScript(FunctionNode functionNode) {
      if (!functionNode.isScript()) {
         throw new IllegalArgumentException("root function node is not a script");
      } else {
         JSFunctionExpressionNode functionExpression = (JSFunctionExpressionNode)this.transformFunction(functionNode);
         return ScriptNode.fromFunctionData(this.context, functionExpression.getFunctionData());
      }
   }

   protected final JavaScriptNode transformFunction(FunctionNode functionNode) {
      return this.transform(functionNode);
   }

   protected abstract GraalJSTranslator newTranslator(Environment env, LexicalContext savedLC);

   public JavaScriptNode enterFunctionNode(FunctionNode functionNode) {
      boolean isStrict = functionNode.isStrict()
         || this.isParentStrict
         || this.environment != null && this.environment.function() != null && this.environment.isStrictMode();
      boolean isArrowFunction = functionNode.isArrow();
      boolean isGeneratorFunction = functionNode.isGenerator();
      boolean isAsyncFunction = functionNode.isAsync();
      boolean isDerivedConstructor = functionNode.isDerivedConstructor();
      boolean isMethod = functionNode.isMethod();
      boolean needsNewTarget = functionNode.needsNewTarget();
      boolean isClassConstructor = functionNode.isClassConstructor();
      boolean isConstructor = !isArrowFunction
         && !isGeneratorFunction
         && !isAsyncFunction
         && (!isMethod || this.context.getEcmaScriptVersion() == 5 || isClassConstructor);

      assert !isDerivedConstructor || isConstructor;

      boolean strictFunctionProperties = isStrict || isArrowFunction || isMethod || isGeneratorFunction;
      boolean isBuiltin = false;
      boolean hasSyntheticArguments = functionNode.isScript() && this.argumentNames != null;
      boolean isEval = false;
      boolean isIndirectEval = false;
      boolean inDirectEval = false;
      boolean isGlobal;
      if (this.environment instanceof EvalEnvironment) {
         isEval = true;
         boolean isDirectEval = ((EvalEnvironment)this.environment).isDirectEval();
         isIndirectEval = !isDirectEval;
         Environment evalParent = this.environment.getParent();
         isGlobal = evalParent == null || isDirectEval && !isStrict && evalParent.function().isGlobal();
         inDirectEval = isDirectEval || evalParent != null && evalParent.function().inDirectEval();
      } else if (this.environment instanceof DebugEnvironment) {
         isGlobal = this.environment.getParent() == null;
         isEval = true;
         inDirectEval = true;
      } else {
         isGlobal = this.environment == null && this.argumentNames == null;
         inDirectEval = this.environment != null && this.currentFunction().inDirectEval();
      }

      boolean functionMode = !isGlobal || isStrict && isIndirectEval;
      boolean lazyTranslation = this.context.getContextOptions().isLazyTranslation() && functionMode && !functionNode.isProgram() && !inDirectEval;
      TruffleString functionName = this.getFunctionName(functionNode);
      JSFunctionData functionData;
      FunctionRootNode functionRoot;
      JSFrameSlot blockScopeSlot;
      if (lazyTranslation) {
         assert functionMode && !functionNode.isProgram() && !functionNode.isModule();

         boolean needsParentFrame = functionNode.usesAncestorScope();
         blockScopeSlot = needsParentFrame && this.environment != null ? this.environment.getCurrentBlockScopeSlot() : null;
         functionData = this.factory
            .createFunctionData(
               this.context,
               functionNode.getLength(),
               functionName,
               isConstructor,
               isDerivedConstructor,
               isStrict,
               isBuiltin,
               needsParentFrame,
               isGeneratorFunction,
               isAsyncFunction,
               isClassConstructor,
               strictFunctionProperties,
               needsNewTarget
            );
         LexicalContext savedLC = this.lc.copy();
         Environment parentEnv = this.environment;
         functionData.setLazyInit(fd -> {
            GraalJSTranslator translator = this.newTranslator(parentEnv, savedLC);
            translator.translateFunctionOnDemand(functionNode, fd, isStrict, isGlobal, needsParentFrame, functionName, hasSyntheticArguments);
         });
         functionRoot = null;
      } else {
         Environment prevEnv = this.environment;

         try (GraalJSTranslator.EnvironmentCloseable functionEnv = this.enterFunctionEnvironment(functionNode, isStrict, isGlobal, hasSyntheticArguments)) {
            FunctionEnvironment currentFunction = this.currentFunction();
            currentFunction.setFunctionName(functionName);
            currentFunction.setInternalFunctionName(functionNode.getInternalNameTS());
            currentFunction.setNamedFunctionExpression(functionNode.isNamedFunctionExpression());
            this.declareParameters(functionNode);
            List<JavaScriptNode> declarations;
            if (functionMode) {
               declarations = this.functionEnvInit(functionNode);
            } else if (functionNode.isModule()) {
               assert currentFunction.isGlobal();

               declarations = Collections.emptyList();
            } else {
               assert currentFunction.isGlobal();

               declarations = this.collectGlobalVars(functionNode, isEval);
            }

            if (functionNode.isProgram()) {
               functionNeedsParentFramePass(functionNode, this.context);
            }

            boolean needsParentFrame = functionNode.usesAncestorScope();
            currentFunction.setNeedsParentFrame(needsParentFrame);
            JavaScriptNode body = this.translateFunctionBody(functionNode, declarations);
            needsParentFrame = currentFunction.needsParentFrame();
            blockScopeSlot = needsParentFrame && prevEnv != null ? prevEnv.getCurrentBlockScopeSlot() : null;
            functionData = this.factory
               .createFunctionData(
                  this.context,
                  functionNode.getLength(),
                  functionName,
                  isConstructor,
                  isDerivedConstructor,
                  isStrict,
                  isBuiltin,
                  needsParentFrame,
                  isGeneratorFunction,
                  isAsyncFunction,
                  isClassConstructor,
                  strictFunctionProperties,
                  needsNewTarget
               );
            if (functionNode.isModule()) {
               functionRoot = this.createModuleRoot(functionNode, functionData, currentFunction, body);
            } else {
               functionRoot = this.createFunctionRoot(functionNode, functionData, currentFunction, body);
            }

            currentFunction.freeze();
            if (isEval) {
               functionData.getCallTarget();
            }
         }
      }

      JavaScriptNode functionExpression;
      if (isArrowFunction && functionNode.needsThis() && !this.currentFunction().getNonArrowParentFunction().isDerivedConstructor()) {
         JavaScriptNode thisNode = this.createThisNode();
         functionExpression = this.factory.createFunctionExpressionLexicalThis(functionData, functionRoot, blockScopeSlot, thisNode);
      } else {
         functionExpression = this.factory.createFunctionExpression(functionData, functionRoot, blockScopeSlot);
      }

      if (functionNode.isDeclared()) {
         this.ensureHasSourceSection(functionExpression, functionNode);
      } else {
         functionExpression = this.tagExpression(functionExpression, functionNode);
      }

      return functionExpression;
   }

   JavaScriptNode translateFunctionBody(FunctionNode functionNode, List<JavaScriptNode> declarations) {
      JavaScriptNode body = this.transform(functionNode.getBody());
      if (functionNode.isAsync() && !functionNode.isGenerator()) {
         this.ensureHasSourceSection(body, functionNode);
         body = this.handleAsyncFunctionBody(body);
      }

      if (!declarations.isEmpty()) {
         body = this.prepareDeclarations(declarations, body);
      }

      JSFrameDescriptor fd = this.currentFunction().getFunctionFrameDescriptor();
      List<JSFrameSlot> slotsWithTDZ = new ArrayList<>(fd.getSize());

      for (JSFrameSlot slot : fd.getSlots()) {
         if (JSFrameUtil.hasTemporalDeadZone(slot)) {
            slotsWithTDZ.add(slot);
         }
      }

      if (!slotsWithTDZ.isEmpty()) {
         int[] slots = new int[slotsWithTDZ.size()];

         for (int i = 0; i < slotsWithTDZ.size(); i++) {
            slots[i] = slotsWithTDZ.get(i).getIndex();
         }

         body = this.factory.createExprBlock(this.factory.createClearFrameSlots(this.factory.createScopeFrame(0, 0, null), slots, 0, slots.length), body);
      }

      return body;
   }

   private FunctionRootNode translateFunctionOnDemand(
      FunctionNode functionNode,
      JSFunctionData functionData,
      boolean isStrict,
      boolean isGlobal,
      boolean needsParentFrame,
      TruffleString functionName,
      boolean hasSyntheticArguments
   ) {
      FunctionRootNode var11;
      try (GraalJSTranslator.EnvironmentCloseable functionEnv = this.enterFunctionEnvironment(functionNode, isStrict, isGlobal, hasSyntheticArguments)) {
         FunctionEnvironment currentFunction = this.currentFunction();
         currentFunction.setFunctionName(functionName);
         currentFunction.setInternalFunctionName(functionNode.getInternalNameTS());
         currentFunction.setNamedFunctionExpression(functionNode.isNamedFunctionExpression());
         currentFunction.setNeedsParentFrame(needsParentFrame);
         this.declareParameters(functionNode);
         this.functionEnvInit(functionNode);
         JavaScriptNode body = this.translateFunctionBody(functionNode, Collections.emptyList());
         currentFunction.freeze();

         assert currentFunction.isDeepFrozen();

         var11 = this.createFunctionRoot(functionNode, functionData, currentFunction, body);
      }

      return var11;
   }

   private FunctionRootNode createFunctionRoot(FunctionNode functionNode, JSFunctionData functionData, FunctionEnvironment currentFunction, JavaScriptNode body) {
      SourceSection functionSourceSection = this.createSourceSection(functionNode);
      FunctionBodyNode functionBody = this.factory.createFunctionBody(body);
      return this.factory
         .createFunctionRootNode(
            functionBody,
            this.environment.getFunctionFrameDescriptor().toFrameDescriptor(),
            functionData,
            functionSourceSection,
            currentFunction.getInternalFunctionName()
         );
   }

   private FunctionRootNode createModuleRoot(FunctionNode functionNode, JSFunctionData functionData, FunctionEnvironment currentFunction, JavaScriptNode body) {
      SourceSection moduleSourceSection = this.createSourceSection(functionNode);
      TruffleString internalFunctionName = currentFunction.getInternalFunctionName();
      JavaScriptNode[] statements = null;
      if (body instanceof SequenceNode) {
         statements = ((SequenceNode)body).getStatements();
      } else if (isModuleYieldStatement(body)) {
         statements = new JavaScriptNode[]{body};
      }

      if (statements != null) {
         for (int i = 0; i < statements.length; i++) {
            JavaScriptNode statement = statements[i];
            if (isModuleYieldStatement(statement)) {
               JavaScriptNode[] linkHalf = Arrays.copyOfRange(statements, 0, i);
               JavaScriptNode[] evalHalf = Arrays.copyOfRange(statements, i + 1, statements.length);
               JavaScriptNode linkBlock = this.tagBody(this.factory.createModuleInitializeEnvironment(this.factory.createVoidBlock(linkHalf)), functionNode);
               JavaScriptNode evalBlock = this.handleModuleBody(this.factory.createExprBlock(evalHalf));
               FunctionBodyNode linkBody = this.factory.createFunctionBody(linkBlock);
               FunctionBodyNode evalBody = this.factory.createFunctionBody(evalBlock);
               return this.factory
                  .createModuleRootNode(
                     linkBody,
                     evalBody,
                     this.environment.getFunctionFrameDescriptor().toFrameDescriptor(),
                     functionData,
                     moduleSourceSection,
                     internalFunctionName
                  );
            }
         }
      }

      currentFunction.addYield();
      FunctionBodyNode generatorBody = this.factory.createFunctionBody(this.handleModuleBody(body));
      return this.factory
         .createModuleRootNode(
            generatorBody,
            generatorBody,
            this.environment.getFunctionFrameDescriptor().toFrameDescriptor(),
            functionData,
            moduleSourceSection,
            internalFunctionName
         );
   }

   private static void printAST(com.oracle.truffle.api.nodes.Node functionRoot) {
      NodeUtil.printCompactTree(System.out, functionRoot);
   }

   private static void printParse(FunctionNode functionNode) {
      System.out.printf(new PrintVisitor(functionNode).toString());
   }

   private JavaScriptNode finishDerivedConstructorBody(FunctionNode function, JavaScriptNode body) {
      JavaScriptNode getThisBinding = !function.hasDirectSuper() && !function.hasEval() && !function.hasArrowEval()
         ? this.factory.createConstantUndefined()
         : this.environment.findThisVar().createReadNode();
      return this.factory.createDerivedConstructorResult(body, getThisBinding);
   }

   private JavaScriptNode handleAsyncFunctionBody(JavaScriptNode body) {
      assert this.currentFunction().isAsyncFunction() && !this.currentFunction().isGeneratorFunction();

      Environment.VarRef asyncContextVar = this.environment.findAsyncContextVar();
      Environment.VarRef asyncResultVar = this.environment.findAsyncResultVar();
      JSWriteFrameSlotNode writeResultNode = (JSWriteFrameSlotNode)asyncResultVar.createWriteNode(null);
      JSWriteFrameSlotNode writeContextNode = (JSWriteFrameSlotNode)asyncContextVar.createWriteNode(null);
      JSReadFrameSlotNode readContextNode = (JSReadFrameSlotNode)asyncContextVar.createReadNode();
      JavaScriptNode instrumentedBody = this.instrumentSuspendNodes(body);
      return this.factory.createAsyncFunctionBody(this.context, instrumentedBody, writeContextNode, readContextNode, writeResultNode);
   }

   private JavaScriptNode finishGeneratorBody(JavaScriptNode body) {
      assert this.lc.getCurrentBlock().isFunctionBody();

      assert !this.currentFunction().isModule();

      return this.currentFunction().isAsyncGeneratorFunction() ? this.handleAsyncGeneratorBody(body) : this.handleGeneratorBody(body);
   }

   private JavaScriptNode handleGeneratorBody(JavaScriptNode body) {
      assert this.currentFunction().isGeneratorFunction() && !this.currentFunction().isAsyncGeneratorFunction() && !this.currentFunction().isModule();

      JavaScriptNode instrumentedBody = this.instrumentSuspendNodes(body);
      Environment.VarRef yieldVar = this.environment.findYieldValueVar();
      JSWriteFrameSlotNode writeYieldValueNode = (JSWriteFrameSlotNode)yieldVar.createWriteNode(null);
      JSReadFrameSlotNode readYieldResultNode = (JSReadFrameSlotNode)this.environment.findTempVar(this.currentFunction().getYieldResultSlot()).createReadNode();
      return this.factory.createGeneratorBody(this.context, instrumentedBody, writeYieldValueNode, readYieldResultNode);
   }

   private JavaScriptNode handleAsyncGeneratorBody(JavaScriptNode body) {
      assert this.currentFunction().isAsyncGeneratorFunction() && !this.currentFunction().isModule();

      Environment.VarRef asyncContextVar = this.environment.findAsyncContextVar();
      JavaScriptNode instrumentedBody = this.instrumentSuspendNodes(body);
      Environment.VarRef yieldVar = this.environment.findAsyncResultVar();
      JSWriteFrameSlotNode writeAsyncContextNode = (JSWriteFrameSlotNode)asyncContextVar.createWriteNode(null);
      JSReadFrameSlotNode readAsyncContextNode = (JSReadFrameSlotNode)asyncContextVar.createReadNode();
      JSWriteFrameSlotNode writeYieldValueNode = (JSWriteFrameSlotNode)yieldVar.createWriteNode(null);
      JSReadFrameSlotNode readYieldResultNode = (JSReadFrameSlotNode)this.environment.findTempVar(this.currentFunction().getYieldResultSlot()).createReadNode();
      return this.factory
         .createAsyncGeneratorBody(this.context, instrumentedBody, writeYieldValueNode, readYieldResultNode, writeAsyncContextNode, readAsyncContextNode);
   }

   private JavaScriptNode handleModuleBody(JavaScriptNode body) {
      assert this.currentFunction().isModule();

      if (this.currentFunction().isAsyncGeneratorFunction()) {
         Environment.VarRef asyncContextVar = this.environment.findAsyncContextVar();
         JavaScriptNode instrumentedBody = this.instrumentSuspendNodes(body);
         Environment.VarRef yieldVar = this.environment.findAsyncResultVar();
         JSWriteFrameSlotNode writeAsyncContextNode = (JSWriteFrameSlotNode)asyncContextVar.createWriteNode(null);
         JSWriteFrameSlotNode writeYieldValueNode = (JSWriteFrameSlotNode)yieldVar.createWriteNode(null);
         return this.factory.createTopLevelAsyncModuleBody(this.context, instrumentedBody, writeYieldValueNode, writeAsyncContextNode);
      } else {
         JavaScriptNode instrumentedBody = this.instrumentSuspendNodes(body);
         return this.factory.createModuleBody(instrumentedBody);
      }
   }

   private JavaScriptNode instrumentSuspendNodes(JavaScriptNode body) {
      if (!this.currentFunction().hasYield() && !this.currentFunction().hasAwait()) {
         return body;
      } else {
         JavaScriptNode newBody = (JavaScriptNode)this.instrumentSuspendHelper(body, null);
         Objects.requireNonNull(newBody);
         return newBody;
      }
   }

   private com.oracle.truffle.api.nodes.Node instrumentSuspendHelper(com.oracle.truffle.api.nodes.Node parent, com.oracle.truffle.api.nodes.Node grandparent) {
      boolean hasSuspendChild = false;
      BitSet suspendableIndices = null;
      if (parent instanceof AbstractBlockNode) {
         AbstractBlockNode blockNode = (AbstractBlockNode)parent;
         com.oracle.truffle.api.nodes.Node[] statements = blockNode.getStatements();

         for (int i = 0; i < statements.length; i++) {
            com.oracle.truffle.api.nodes.Node oldChild = statements[i];
            com.oracle.truffle.api.nodes.Node newChild = this.instrumentSuspendHelper(oldChild, parent);
            if (newChild != null) {
               hasSuspendChild = true;
               if (newChild != oldChild) {
                  this.factory.fixBlockNodeChild(blockNode, i, (JavaScriptNode)newChild);
               }

               if (suspendableIndices == null) {
                  suspendableIndices = new BitSet();
               }

               suspendableIndices.set(i);
            }
         }
      } else {
         for (com.oracle.truffle.api.nodes.Node child : getChildrenInExecutionOrder(parent)) {
            com.oracle.truffle.api.nodes.Node newChild = this.instrumentSuspendHelper(child, parent);
            if (newChild != null) {
               hasSuspendChild = true;
               if (child != newChild) {
                  this.factory.fixNodeChild(parent, child, newChild);
               }

               assert !(child instanceof ResumableNode) || newChild instanceof GeneratorWrapperNode || newChild instanceof SuspendNode : "resumable node not wrapped: "
                  + child;
            }
         }
      }

      if (parent instanceof SuspendNode) {
         return parent;
      } else if (!hasSuspendChild) {
         return null;
      } else if (parent instanceof AbstractBlockNode) {
         assert suspendableIndices != null && !suspendableIndices.isEmpty();

         return this.toGeneratorBlockNode((AbstractBlockNode)parent, suspendableIndices);
      } else if (parent instanceof ResumableNode) {
         return this.wrapResumableNode(parent);
      } else if (parent instanceof ReturnNode || parent instanceof ReturnTargetNode || isSideEffectFreeUnaryOpNode(parent)) {
         return parent;
      } else if (!isSupportedDispersibleExpression(parent)) {
         return parent;
      } else {
         List<JavaScriptNode> extracted = new ArrayList<>();
         if (grandparent == null || NodeUtil.isReplacementSafe(grandparent, parent, ANY_JAVA_SCRIPT_NODE)) {
            this.extractChildrenTo(parent, extracted);
         }

         if (!extracted.isEmpty()) {
            extracted.add((JavaScriptNode)parent);
            JavaScriptNode exprBlock = this.wrapResumableNode(this.factory.createExprBlock(extracted.toArray(EMPTY_NODE_ARRAY)));
            tagHiddenExpression(exprBlock);
            return exprBlock;
         } else {
            return parent;
         }
      }
   }

   private JavaScriptNode wrapResumableNode(com.oracle.truffle.api.nodes.Node resumableNode) {
      if (resumableNode instanceof AbstractBlockNode) {
         BitSet all = new BitSet();
         all.set(0, ((AbstractBlockNode)resumableNode).getStatements().length);
         return this.toGeneratorBlockNode((AbstractBlockNode)resumableNode, all);
      } else {
         assert !(resumableNode instanceof SuspendNode) : resumableNode;

         JSFrameSlot stateSlot = this.addGeneratorStateSlot(this.environment.getFunctionFrameDescriptor(), ((ResumableNode)resumableNode).getStateSlotKind());
         return this.factory.createGeneratorWrapper((JavaScriptNode)resumableNode, stateSlot);
      }
   }

   private JavaScriptNode toGeneratorBlockNode(AbstractBlockNode blockNode, BitSet suspendableIndices) {
      JSFrameDescriptor functionFrameDesc = this.environment.getFunctionFrameDescriptor();
      JavaScriptNode[] statements = blockNode.getStatements();
      boolean returnsResult = !blockNode.isResultAlwaysOfType(Undefined.class);
      int resumePoints = suspendableIndices.cardinality() + (suspendableIndices.get(0) ? 0 : 1);
      JavaScriptNode genBlock;
      if (resumePoints == statements.length) {
         JSFrameSlot stateSlot = this.addGeneratorStateSlot(functionFrameDesc, FrameSlotKind.Int);
         if (returnsResult) {
            genBlock = this.factory.createGeneratorExprBlock(statements, stateSlot);
         } else {
            genBlock = this.factory.createGeneratorVoidBlock(statements, stateSlot);
         }
      } else {
         JavaScriptNode[] chunks = new JavaScriptNode[resumePoints];
         int fromIndex = 0;

         for (int chunkI = 0; chunkI < resumePoints; chunkI++) {
            int toIndex = suspendableIndices.nextSetBit(fromIndex + 1);
            if (toIndex < 0) {
               assert chunkI == resumePoints - 1;

               toIndex = statements.length;
            }

            returnsResult = chunkI == resumePoints - 1 && !blockNode.isResultAlwaysOfType(Undefined.class);
            JavaScriptNode chunk;
            if (fromIndex + 1 == toIndex) {
               chunk = statements[fromIndex];
            } else {
               JavaScriptNode[] chunkStatements = Arrays.copyOfRange(statements, fromIndex, toIndex);
               chunk = returnsResult && chunkI == resumePoints - 1
                  ? this.factory.createExprBlock(chunkStatements)
                  : this.factory.createVoidBlock(chunkStatements);
            }

            chunks[chunkI] = chunk;
            fromIndex = toIndex;
         }

         JSFrameSlot stateSlot = this.addGeneratorStateSlot(functionFrameDesc, FrameSlotKind.Int);
         if (returnsResult) {
            genBlock = this.factory.createGeneratorExprBlock(chunks, stateSlot);
         } else {
            genBlock = this.factory.createGeneratorVoidBlock(chunks, stateSlot);
         }
      }

      JavaScriptNode.transferSourceSectionAndTags(blockNode, genBlock);
      return genBlock;
   }

   private static boolean isSideEffectFreeUnaryOpNode(com.oracle.truffle.api.nodes.Node node) {
      return node instanceof DiscardResultNode || node instanceof VoidNode || node instanceof TypeOfNode || node instanceof JSTypeofIdenticalNode;
   }

   private static boolean isSupportedDispersibleExpression(com.oracle.truffle.api.nodes.Node node) {
      return node instanceof JSBinaryNode
         || node instanceof JSUnaryNode
         || node instanceof ArrayLiteralNode
         || node instanceof ObjectLiteralNode
         || node instanceof com.oracle.truffle.js.nodes.access.PropertyNode
         || node instanceof GlobalPropertyNode
         || node instanceof ReadElementNode
         || node instanceof WritePropertyNode
         || node instanceof WriteElementNode
         || node instanceof JSFunctionCallNode
         || node instanceof JSNewNode;
   }

   private static boolean isStatelessExpression(com.oracle.truffle.api.nodes.Node child) {
      return child instanceof JSConstantNode || child instanceof CreateObjectNode || child instanceof RepeatableNode && !(child instanceof ReadNode);
   }

   private static boolean skipOverToChildren(com.oracle.truffle.api.nodes.Node node) {
      return node instanceof ObjectLiteralNode.ObjectLiteralMemberNode
         || node instanceof AbstractFunctionArgumentsNode
         || node instanceof ArrayLiteralNode.SpreadArrayNode
         || node instanceof SpreadArgumentNode;
   }

   private void extractChildTo(com.oracle.truffle.api.nodes.Node child, com.oracle.truffle.api.nodes.Node parent, List<JavaScriptNode> extracted) {
      if (!isStatelessExpression(child)) {
         if (skipOverToChildren(child)) {
            this.extractChildrenTo(child, extracted);
         } else if (child instanceof JavaScriptNode) {
            JavaScriptNode jschild = (JavaScriptNode)child;
            if (NodeUtil.isReplacementSafe(parent, child, ANY_JAVA_SCRIPT_NODE)) {
               JSFrameDescriptor functionFrameDescriptor = this.environment.getFunctionFrameDescriptor();
               InternalSlotId identifier = this.factory.createInternalSlotId(GENERATOREXPR, functionFrameDescriptor.getSize());
               JSFrameSlot frameSlot = functionFrameDescriptor.addFrameSlot(identifier);
               JavaScriptNode readState = this.factory.createReadCurrentFrameSlot(frameSlot);
               if (jschild.hasTag(StandardTags.ExpressionTag.class)
                  || jschild instanceof GeneratorWrapperNode && ((GeneratorWrapperNode)jschild).getResumableNode().hasTag(StandardTags.ExpressionTag.class)) {
                  tagHiddenExpression(readState);
               }

               JavaScriptNode writeState = this.factory.createWriteCurrentFrameSlot(frameSlot, jschild);
               extracted.add(writeState);
               this.factory.fixNodeChild(parent, child, readState);
            } else {
               this.extractChildrenTo(child, extracted);
            }
         }
      }
   }

   private static Iterable<com.oracle.truffle.api.nodes.Node> getChildrenInExecutionOrder(com.oracle.truffle.api.nodes.Node parent) {
      return parent.getChildren();
   }

   private void extractChildrenTo(com.oracle.truffle.api.nodes.Node parent, List<JavaScriptNode> extracted) {
      for (com.oracle.truffle.api.nodes.Node child : getChildrenInExecutionOrder(parent)) {
         this.extractChildTo(child, parent, extracted);
      }
   }

   private JavaScriptNode handleFunctionReturn(FunctionNode functionNode, JavaScriptNode body) {
      FunctionEnvironment currentFunction = this.currentFunction();

      assert (currentFunction.isGlobal() || currentFunction.isEval() || currentFunction.hasSyntheticArguments())
         == (functionNode.isScript() || functionNode.isModule());

      if (currentFunction.hasReturn()) {
         return this.factory.createFrameReturnTarget(body, this.factory.createReadCurrentFrameSlot(currentFunction.getReturnSlot()));
      } else if (currentFunction.returnsLastStatementResult()) {
         return currentFunction.hasReturnSlot() ? this.wrapGetCompletionValue(body) : this.discardResult(body);
      } else {
         return body;
      }
   }

   private GraalJSTranslator.EnvironmentCloseable enterFunctionEnvironment(
      FunctionNode function, boolean isStrict, boolean isGlobal, boolean hasSyntheticArguments
   ) {
      Scope scope = function.getBody().getScope();
      Environment functionEnv;
      if (this.environment instanceof EvalEnvironment) {
         assert !function.isArrow() && !function.isGenerator() && !function.isDerivedConstructor() && !function.isAsync();

         functionEnv = new FunctionEnvironment(
            this.environment.getParent(),
            this.factory,
            this.context,
            scope,
            isStrict,
            true,
            ((EvalEnvironment)this.environment).isDirectEval(),
            false,
            false,
            false,
            false,
            isGlobal,
            hasSyntheticArguments
         );
      } else if (this.environment instanceof DebugEnvironment) {
         assert !function.isArrow() && !function.isGenerator() && !function.isDerivedConstructor() && !function.isAsync();

         functionEnv = new FunctionEnvironment(
            this.environment, this.factory, this.context, scope, isStrict, true, true, false, false, false, false, isGlobal, hasSyntheticArguments
         );
      } else {
         functionEnv = new FunctionEnvironment(
            this.environment,
            this.factory,
            this.context,
            scope,
            isStrict,
            false,
            false,
            function.isArrow(),
            function.isGenerator(),
            function.isDerivedConstructor(),
            function.isAsync(),
            isGlobal,
            hasSyntheticArguments
         );
      }

      return new GraalJSTranslator.EnvironmentCloseable(functionEnv);
   }

   private void declareParameters(FunctionNode functionNode) {
      FunctionEnvironment currentFunction = this.currentFunction();
      currentFunction.setSimpleParameterList(functionNode.hasSimpleParameterList());
      List<IdentNode> parameters = functionNode.getParameters();
      if (parameters.size() > 0 && parameters.get(parameters.size() - 1).isRestParameter()) {
         currentFunction.setRestParameter(true);
      }

      if (functionNode.getNumOfParams() > this.context.getFunctionArgumentsLimit()) {
         throw Errors.createSyntaxError("function has too many arguments");
      }
   }

   private JavaScriptNode prepareDeclarations(List<JavaScriptNode> declarations, JavaScriptNode body) {
      declarations.add(body);
      return this.factory.createExprBlock(declarations.toArray(EMPTY_NODE_ARRAY));
   }

   private static JavaScriptNode[] javaScriptNodeArray(int size) {
      return size == 0 ? EMPTY_NODE_ARRAY : new JavaScriptNode[size];
   }

   private TruffleString getFunctionName(FunctionNode functionNode) {
      if (this.context.getEcmaScriptVersion() < 6 && (functionNode.isGetter() || functionNode.isSetter())) {
         assert !functionNode.isAnonymous();

         String name = functionNode.getName();
         if (functionNode.isGetter() && name.startsWith(Strings.GET_SPC.toJavaStringUncached())
            || functionNode.isSetter() && name.startsWith(Strings.SET_SPC.toJavaStringUncached())) {
            return Strings.substring(this.context, functionNode.getNameTS(), 4);
         }
      }

      return functionNode.getNameTS();
   }

   private void prepareParameters(List<JavaScriptNode> init) {
      FunctionNode function = this.lc.getCurrentFunction();
      FunctionEnvironment currentFunction = this.currentFunction();
      if (this.needsThisSlot(function, currentFunction) && !function.isDerivedConstructor()) {
         assert this.environment.findThisVar() != null;

         init.add(this.prepareThis(function));
      }

      if (function.needsSuper()) {
         assert function.isMethod();

         init.add(this.prepareSuper());
      }

      if (function.needsNewTarget()) {
         init.add(this.prepareNewTarget());
      }

      if (function.needsArguments() && !currentFunction.isDirectArgumentsAccess() && !currentFunction.isDirectEval()) {
         assert !function.isArrow() && !function.isClassFieldInitializer();

         init.add(this.prepareArguments());
      }

      int parameterCount = function.getParameters().size();
      if (parameterCount != 0) {
         int i = 0;
         boolean hasRestParameter = currentFunction.hasRestParameter();
         boolean hasMappedArguments = function.needsArguments() && !function.isStrict() && function.hasSimpleParameterList();

         for (int argIndex = currentFunction.getLeadingArgumentCount(); i < parameterCount; argIndex++) {
            JavaScriptNode valueNode;
            if (hasRestParameter && i == parameterCount - 1) {
               valueNode = tagHiddenExpression(this.factory.createAccessRestArgument(this.context, argIndex));
            } else {
               valueNode = tagHiddenExpression(this.factory.createAccessArgument(argIndex));
            }

            IdentNode param = function.getParameters().get(i);
            if (param.isIgnoredParameter()) {
               assert !currentFunction.isStrictMode();
            } else {
               TruffleString paramName = param.getNameTS();
               Environment.VarRef paramRef = this.environment.findLocalVar(paramName);
               init.add(tagHiddenExpression(paramRef.createWriteNode(valueNode)));
               if (hasMappedArguments) {
                  currentFunction.addMappedParameter(paramRef.getFrameSlot(), i);
               }
            }

            i++;
         }
      }
   }

   private static JavaScriptNode tagHiddenExpression(JavaScriptNode node) {
      node.setSourceSection(unavailableInternalSection);
      if (node instanceof VarWrapperNode) {
         tagHiddenExpression(((VarWrapperNode)node).getDelegateNode());
      } else {
         node.addExpressionTag();
      }

      return node;
   }

   private List<JavaScriptNode> functionEnvInit(FunctionNode functionNode) {
      FunctionEnvironment currentFunction = this.currentFunction();

      assert !currentFunction.isGlobal() || currentFunction.isIndirectEval();

      markTerminalReturnNodes(functionNode.getBody());
      if (functionNode.needsArguments()
         && functionNode.hasApplyArgumentsCall()
         && !functionNode.isArrow()
         && !functionNode.hasEval()
         && !functionNode.hasArrowEval()
         && !currentFunction.isDirectEval()
         && functionNode.getNumOfParams() == 0
         && checkDirectArgumentsAccess(functionNode)) {
         currentFunction.setDirectArgumentsAccess(true);
      }

      if (functionNode.needsDynamicScope() && !currentFunction.isDirectEval()) {
         currentFunction.setIsDynamicallyScoped(true);
      }

      if (functionNode.needsNewTarget()) {
         currentFunction.setNeedsNewTarget(true);
      }

      return Collections.emptyList();
   }

   private static void functionNeedsParentFramePass(FunctionNode rootFunctionNode, JSContext context) {
      if (context.getContextOptions().isLazyTranslation()) {
         NodeVisitor<LexicalContext> visitor = new NodeVisitor<LexicalContext>(new LexicalContext()) {
            @Override
            public boolean enterIdentNode(IdentNode identNode) {
               if (!identNode.isPropertyName()) {
                  String varName = identNode.getName();
                  this.findSymbol(varName);
               }

               return true;
            }

            @Override
            public boolean enterAccessNode(AccessNode accessNode) {
               if (accessNode.isPrivate()) {
                  this.findSymbol(accessNode.getPrivateName());
               }

               return true;
            }

            private void findSymbol(String varName) {
               boolean local = true;
               FunctionNode lastFunction = null;
               Iterator<LexicalContextNode> iterator = this.lc.getAllNodes();

               while (iterator.hasNext()) {
                  LexicalContextNode node = iterator.next();
                  if (node instanceof Block) {
                     Symbol foundSymbol = ((Block)node).getScope().getExistingSymbol(varName);
                     if (foundSymbol != null && !foundSymbol.isGlobal()) {
                        if (!local) {
                           this.markUsesAncestorScopeUntil(lastFunction, true);
                        }
                        break;
                     }
                  } else if (node instanceof ClassNode) {
                     if (((ClassNode)node).getScope().hasSymbol(varName) || ((ClassNode)node).getClassHeadScope().hasSymbol(varName)) {
                        if (!local) {
                           this.markUsesAncestorScopeUntil(lastFunction, true);
                        }
                        break;
                     }
                  } else if (!(node instanceof FunctionNode)) {
                     if (node instanceof WithNode && !local) {
                        this.markUsesAncestorScopeUntil(lastFunction, true);
                     }
                  } else {
                     FunctionNode function = (FunctionNode)node;
                     if (function.isNamedFunctionExpression() && varName.equals(function.getIdent().getName())) {
                        if (!local) {
                           this.markUsesAncestorScopeUntil(lastFunction, true);
                        }
                        break;
                     }

                     if (!function.isProgram() && varName.equals("arguments")) {
                        assert local || lastFunction != null && lastFunction.isArrow();

                        if (!function.isArrow()) {
                           if (!local) {
                              this.markUsesAncestorScopeUntil(lastFunction, true);
                           }
                           break;
                        }
                     } else if (function.isArrow() && this.isVarLexicallyScopedInArrowFunction(varName)) {
                        assert !varName.equals("arguments");

                        FunctionNode nonArrowFunction = this.lc.getCurrentNonArrowFunction();
                        if ((!varName.equals("this") || nonArrowFunction.isDerivedConstructor()) && !nonArrowFunction.isProgram()) {
                           this.markUsesAncestorScopeUntil(nonArrowFunction, false);
                        }
                        break;
                     }

                     if (function.hasEval() && !function.isProgram()) {
                        if (!local) {
                           this.markUsesAncestorScopeUntil(lastFunction, true);
                        }
                     } else if (function.isModule() && this.isImport(varName) && !local) {
                        this.markUsesAncestorScopeUntil(lastFunction, true);
                     }

                     lastFunction = function;
                     local = false;
                  }
               }
            }

            private boolean isVarLexicallyScopedInArrowFunction(String varName) {
               return "arguments".equals(varName) || "new.target".equals(varName) || "super".equals(varName) || "this".equals(varName);
            }

            private boolean isImport(String varName) {
               return "import".equals(varName) || "import.meta".equals(varName);
            }

            private void markUsesAncestorScopeUntil(FunctionNode untilFunction, boolean inclusive) {
               Iterator<FunctionNode> functions = this.lc.getFunctions();

               while (functions.hasNext()) {
                  FunctionNode function = functions.next();
                  if (!inclusive && function == untilFunction) {
                     break;
                  }

                  if (!function.isProgram()) {
                     function.setUsesAncestorScope(true);
                  }

                  if (inclusive && function == untilFunction) {
                     break;
                  }
               }
            }

            @Override
            public boolean enterFunctionNode(FunctionNode functionNode) {
               if (functionNode.hasEval()) {
                  this.markUsesAncestorScopeUntil(null, false);
               }

               return true;
            }
         };
         rootFunctionNode.accept(visitor);
      }
   }

   private static boolean checkDirectArgumentsAccess(FunctionNode functionNode) {
      assert functionNode.needsArguments()
         && functionNode.hasApplyArgumentsCall()
         && !functionNode.isArrow()
         && !functionNode.hasEval()
         && !functionNode.hasArrowEval();

      assert functionNode.getNumOfParams() == 0 || functionNode.isStrict() || !functionNode.hasSimpleParameterList() : "must not have mapped parameters";

      class DirectArgumentsAccessVisitor extends NodeVisitor<LexicalContext> {
         boolean directArgumentsAccess = true;

         DirectArgumentsAccessVisitor(LexicalContext lc) {
            super(lc);
         }

         @Override
         public boolean enterIdentNode(IdentNode identNode) {
            if (!identNode.isPropertyName() && !identNode.isApplyArguments() && identNode.getName().equals("arguments")) {
               this.directArgumentsAccess = false;
            }

            return false;
         }

         @Override
         public boolean enterFunctionNode(FunctionNode nestedFunctionNode) {
            if (nestedFunctionNode == functionNode) {
               return true;
            } else {
               if (nestedFunctionNode.isArrow()) {
                  this.directArgumentsAccess = false;
               }

               return false;
            }
         }
      }

      DirectArgumentsAccessVisitor visitor = new DirectArgumentsAccessVisitor(new LexicalContext());
      functionNode.accept(visitor);
      return visitor.directArgumentsAccess;
   }

   private static void markTerminalReturnNodes(Node node) {
      if (node instanceof Block && ((Block)node).isTerminal()) {
         Statement lastStatement = ((Block)node).getLastStatement();
         if (lastStatement != null) {
            markTerminalReturnNodes(lastStatement);
         }
      } else if (node instanceof BlockStatement && ((BlockStatement)node).isTerminal()) {
         markTerminalReturnNodes(((BlockStatement)node).getBlock());
      } else if (node instanceof IfNode && ((IfNode)node).isTerminal()) {
         markTerminalReturnNodes(((IfNode)node).getPass());
         markTerminalReturnNodes(((IfNode)node).getFail());
      } else if (node instanceof com.oracle.js.parser.ir.ReturnNode) {
         ((com.oracle.js.parser.ir.ReturnNode)node).setInTerminalPosition(true);
      }
   }

   private List<JavaScriptNode> collectGlobalVars(FunctionNode functionNode, boolean configurable) {
      int symbolCount = functionNode.getBody().getSymbolCount();
      if (symbolCount == 0) {
         return Collections.emptyList();
      } else {
         List<DeclareGlobalNode> declarations = new ArrayList<>(symbolCount);

         for (Symbol symbol : functionNode.getBody().getSymbols()) {
            if (symbol.isGlobal() && symbol.isVar()) {
               if (symbol.isHoistableDeclaration()) {
                  declarations.add(this.factory.createDeclareGlobalFunction(symbol.getNameTS(), configurable, null));
               } else {
                  declarations.add(this.factory.createDeclareGlobalVariable(symbol.getNameTS(), configurable));
               }
            } else if (!configurable) {
               assert symbol.isBlockScoped();

               declarations.add(this.factory.createDeclareGlobalLexicalVariable(symbol.getNameTS(), symbol.isConst()));
            }
         }

         List<JavaScriptNode> nodes = new ArrayList<>(2);
         nodes.add(this.factory.createGlobalDeclarationInstantiation(this.context, declarations));
         return nodes;
      }
   }

   private JavaScriptNode prepareArguments() {
      Environment.VarRef argumentsVar = this.environment.findLocalVar(Strings.ARGUMENTS);
      boolean unmappedArgumentsObject = this.currentFunction().isStrictMode() || !this.currentFunction().hasSimpleParameterList();
      JavaScriptNode argumentsObject = this.factory
         .createArgumentsObjectNode(this.context, unmappedArgumentsObject, this.currentFunction().getLeadingArgumentCount());
      if (!unmappedArgumentsObject) {
         argumentsObject = this.environment.findArgumentsVar().createWriteNode(argumentsObject);
      }

      return argumentsVar.createWriteNode(argumentsObject);
   }

   private JavaScriptNode prepareThis(FunctionNode functionNode) {
      assert !this.currentFunction().getNonArrowParentFunction().isDerivedConstructor();

      Environment.VarRef thisVar = this.environment.findThisVar();
      boolean isLexicalThis = functionNode.isArrow();
      JavaScriptNode getThisNode = isLexicalThis ? this.factory.createAccessLexicalThis() : this.factory.createAccessThis();
      if (!this.environment.isStrictMode() && !isLexicalThis) {
         getThisNode = this.factory.createPrepareThisBinding(this.context, getThisNode);
      }

      if (functionNode.isClassConstructor()) {
         getThisNode = this.initializeInstanceElements(getThisNode);
      }

      return thisVar.createWriteNode(getThisNode);
   }

   private JavaScriptNode prepareSuper() {
      JavaScriptNode getHomeObject = this.factory.createAccessHomeObject(this.context);
      return this.environment.findSuperVar().createWriteNode(getHomeObject);
   }

   private JavaScriptNode prepareNewTarget() {
      JavaScriptNode getNewTarget = this.factory.createAccessNewTarget();
      return this.environment.findNewTargetVar().createWriteNode(getNewTarget);
   }

   public JavaScriptNode enterReturnNode(com.oracle.js.parser.ir.ReturnNode returnNode) {
      JavaScriptNode expression;
      if (returnNode.getExpression() != null) {
         expression = this.transform(returnNode.getExpression());
         if (this.currentFunction().isAsyncGeneratorFunction()) {
            expression = this.createAwaitNode(expression);
         }
      } else {
         expression = this.factory.createConstantUndefined();
      }

      ReturnNode returnStatement = returnNode.isInTerminalPosition()
         ? this.factory.createTerminalPositionReturn(expression)
         : this.createReturnNode(expression);
      return this.tagStatement(returnStatement, returnNode);
   }

   private ReturnNode createReturnNode(JavaScriptNode expression) {
      FunctionEnvironment currentFunction = this.currentFunction();
      currentFunction.addReturn();
      JavaScriptNode writeReturnSlotNode = this.environment.findTempVar(currentFunction.getReturnSlot()).createWriteNode(expression);
      return this.factory.createFrameReturn(writeReturnSlotNode);
   }

   public JavaScriptNode enterBlock(Block block) {
      FunctionEnvironment currentFunction = this.currentFunction();

      JavaScriptNode result;
      try (GraalJSTranslator.EnvironmentCloseable blockEnv = this.enterBlockEnvironment(block)) {
         List<Statement> blockStatements = block.getStatements();
         List<JavaScriptNode> scopeInit = new ArrayList<>(block.getSymbolCount());
         if (block.getScope().hasBlockScopedOrRedeclaredSymbols() && !(this.environment instanceof GlobalEnvironment)) {
            this.createTemporalDeadZoneInit(block.getScope(), scopeInit);
         }

         if (block.isModuleBody()) {
            this.createResolveImports(this.lc.getCurrentFunction(), scopeInit);
         }

         if (block.getScope().isFunctionTopScope() || block.getScope().isEvalScope()) {
            this.prepareParameters(scopeInit);
         }

         if (block.isParameterBlock() || block.isFunctionBody()) {
            if (!currentFunction.isGlobal() && currentFunction.isDynamicallyScoped()) {
               this.environment.reserveDynamicScopeSlot();
            }

            if (block.isFunctionBody() && currentFunction.isCallerContextEval()) {
               this.prependDynamicScopeBindingInit(block, scopeInit);
            }
         }

         JavaScriptNode blockNode;
         if (block.isFunctionBody()) {
            blockNode = this.transformStatements(blockStatements, block.isTerminal(), block.isExpressionBlock() || block.isParameterBlock());
            if (block.isModuleBody()) {
               blockNode = this.splitModuleBodyAtYield(blockNode, scopeInit);
            }

            FunctionNode function = this.lc.getCurrentFunction();
            blockNode = this.handleFunctionReturn(function, blockNode);
            if (currentFunction.isDerivedConstructor()) {
               blockNode = this.finishDerivedConstructorBody(function, blockNode);
            }

            this.tagBody(blockNode, block);
            if (!scopeInit.isEmpty()) {
               scopeInit.add(blockNode);
               blockNode = this.factory.createExprBlock(scopeInit.toArray(EMPTY_NODE_ARRAY));
            }
         } else {
            List<Statement> newBlockStatements = null;

            for (Statement statement : blockStatements) {
               if (statement instanceof VarNode) {
                  VarNode varNode = (VarNode)statement;
                  if (varNode.isHoistableDeclaration()) {
                     if (newBlockStatements == null) {
                        newBlockStatements = new ArrayList<>();
                     }

                     newBlockStatements.add(statement);
                  }
               }
            }

            if (newBlockStatements == null) {
               newBlockStatements = blockStatements;
            } else {
               for (Statement statementx : blockStatements) {
                  if (statementx instanceof VarNode) {
                     VarNode varNode = (VarNode)statementx;
                     if (varNode.isHoistableDeclaration()) {
                        if (this.annexBBlockToFunctionTransfer(varNode)) {
                           newBlockStatements.add(varNode.setFlag(32));
                        }
                        continue;
                     }
                  }

                  newBlockStatements.add(statementx);
               }
            }

            blockNode = this.transformStatements(newBlockStatements, block.isTerminal(), block.isExpressionBlock() || block.isParameterBlock(), scopeInit);
         }

         result = blockEnv.wrapBlockScope(blockNode);
      }

      if (block.isFunctionBody() && currentFunction.isGeneratorFunction() && !currentFunction.isModule()) {
         result = this.finishGeneratorBody(result);
      }

      this.ensureHasSourceSection(result, block);
      return result;
   }

   private boolean annexBBlockToFunctionTransfer(VarNode varNode) {
      return this.context.isOptionAnnexB() && !this.environment.isStrictMode() && varNode.isFunctionDeclaration();
   }

   private boolean allowScopeOptimization() {
      return this.context.getContextOptions().isScopeOptimization();
   }

   private boolean allowTDZOptimization() {
      return false;
   }

   private void createTemporalDeadZoneInit(Scope blockScope, List<JavaScriptNode> blockWithInit) {
      assert blockScope.hasBlockScopedOrRedeclaredSymbols() && !(this.environment instanceof GlobalEnvironment);

      List<Environment.FrameSlotVarRef> slotsWithTDZ = new ArrayList<>();

      for (Symbol symbol : blockScope.getSymbols()) {
         if (!symbol.isImportBinding()) {
            if (symbol.isBlockScoped()
               && !symbol.hasBeenDeclared()
               && (
                  symbol.isClosedOver()
                     || symbol.isDeclaredInSwitchBlock()
                     || blockScope.isModuleScope()
                     || blockScope.hasNestedEval()
                     || !this.allowScopeOptimization()
                     || !this.allowTDZOptimization()
               )) {
               Environment.FrameSlotVarRef slotRef = (Environment.FrameSlotVarRef)this.findScopeVar(symbol.getNameTS(), true);

               assert JSFrameUtil.hasTemporalDeadZone(slotRef.getFrameSlot()) : slotRef.getFrameSlot();

               slotsWithTDZ.add(slotRef);
            }

            if (symbol.isVarRedeclaredHere()) {
               assert blockScope.isFunctionBodyScope();

               JavaScriptNode outerVar = this.environment.findBlockScopedVar(symbol.getNameTS()).createReadNode();
               blockWithInit.add(this.findScopeVar(symbol.getNameTS(), true).createWriteNode(outerVar));
            }
         }
      }

      if (!slotsWithTDZ.isEmpty()) {
         slotsWithTDZ.sort(Comparator.comparingInt(Environment.AbstractFrameVarRef::getScopeLevel));
         if (slotsWithTDZ.size() != 1 && slotsWithTDZ.get(0).getScopeLevel() != slotsWithTDZ.get(slotsWithTDZ.size() - 1).getScopeLevel()) {
            int[] slots = new int[slotsWithTDZ.size()];
            int from = 0;

            while (from < slots.length) {
               Environment.FrameSlotVarRef first = slotsWithTDZ.get(from);
               ScopeFrameNode scope = first.createScopeFrameNode();
               slots[from] = slotsWithTDZ.get(from).getFrameSlot().getIndex();
               int to = from + 1;

               while (to < slots.length) {
                  Environment.FrameSlotVarRef next = slotsWithTDZ.get(to);
                  if (next.getScopeLevel() != first.getScopeLevel()) {
                     break;
                  }

                  slots[to++] = next.getFrameSlot().getIndex();
               }

               blockWithInit.add(this.factory.createClearFrameSlots(scope, slots, from, to));
               from = to;
            }
         } else {
            int[] slots = new int[slotsWithTDZ.size()];
            ScopeFrameNode scope = slotsWithTDZ.get(0).createScopeFrameNode();

            for (int i = 0; i < slots.length; i++) {
               slots[i] = slotsWithTDZ.get(i).getFrameSlot().getIndex();
            }

            blockWithInit.add(this.factory.createClearFrameSlots(scope, slots, 0, slots.length));
         }
      }
   }

   private JavaScriptNode wrapTemporalDeadZoneInit(Scope scope, JavaScriptNode blockBody) {
      if (!scope.hasBlockScopedOrRedeclaredSymbols()) {
         return blockBody;
      } else {
         List<JavaScriptNode> init = new ArrayList<>(4);
         this.createTemporalDeadZoneInit(scope, init);
         if (init.isEmpty()) {
            return blockBody;
         } else {
            init.add(blockBody);
            return this.factory.createExprBlock(init.toArray(EMPTY_NODE_ARRAY));
         }
      }
   }

   private void createResolveImports(FunctionNode functionNode, List<JavaScriptNode> declarations) {
      assert functionNode.isModule();

      for (Module.ImportEntry importEntry : functionNode.getModule().getImportEntries()) {
         Module.ModuleRequest moduleRequest = importEntry.getModuleRequest();
         TruffleString localName = importEntry.getLocalName();
         String localNameJS = localName.toJavaStringUncached();
         JSWriteFrameSlotNode writeLocalNode = (JSWriteFrameSlotNode)this.environment.findLocalVar(localName).createWriteNode(null);
         JavaScriptNode thisModule = this.getActiveModule();
         if (importEntry.getImportName().equals(Module.STAR_NAME)) {
            assert functionNode.getBody().getScope().hasSymbol(localNameJS)
               && functionNode.getBody().getScope().getExistingSymbol(localNameJS).hasBeenDeclared();

            declarations.add(this.factory.createResolveStarImport(this.context, thisModule, moduleRequest, writeLocalNode));
         } else {
            assert functionNode.getBody().getScope().hasSymbol(localNameJS)
               && functionNode.getBody().getScope().getExistingSymbol(localNameJS).isImportBinding();

            declarations.add(this.factory.createResolveNamedImport(this.context, thisModule, moduleRequest, importEntry.getImportName(), writeLocalNode));
         }
      }
   }

   private JavaScriptNode splitModuleBodyAtYield(JavaScriptNode blockNode, List<JavaScriptNode> scopeInit) {
      if (blockNode instanceof SequenceNode) {
         JavaScriptNode[] statements = ((SequenceNode)blockNode).getStatements();

         for (int i = 0; i < statements.length; i++) {
            JavaScriptNode statement = statements[i];
            if (isModuleYieldStatement(statement)) {
               scopeInit.addAll(Arrays.asList(statements).subList(0, i + 1));
               return this.factory.createExprBlock(Arrays.copyOfRange(statements, i + 1, statements.length));
            }
         }
      } else if (isModuleYieldStatement(blockNode)) {
         scopeInit.add(blockNode);
         return this.factory.createEmpty();
      }

      return blockNode;
   }

   private static boolean isModuleYieldStatement(JavaScriptNode statement) {
      return statement instanceof ModuleYieldNode
         || statement instanceof JSWriteFrameSlotNode && ((JSWriteFrameSlotNode)statement).getRhs() instanceof ModuleYieldNode;
   }

   private void prependDynamicScopeBindingInit(Block block, List<JavaScriptNode> blockWithInit) {
      assert this.currentFunction().isCallerContextEval();

      for (Symbol symbol : block.getSymbols()) {
         if (symbol.isVar() && !this.environment.getVariableEnvironment().hasLocalVar(symbol.getName())) {
            blockWithInit.add(this.createDynamicScopeBinding(symbol.getNameTS(), true));
         }
      }
   }

   private JavaScriptNode createDynamicScopeBinding(TruffleString varName, boolean deleteable) {
      assert deleteable;

      Environment.VarRef dynamicScopeVar = this.environment.findDynamicScopeVar();
      return new DeclareEvalVariableNode(this.context, varName, dynamicScopeVar.createReadNode(), (WriteNode)dynamicScopeVar.createWriteNode(null));
   }

   private JavaScriptNode transformStatements(List<Statement> blockStatements, boolean terminal, boolean expressionBlock) {
      return this.transformStatements(blockStatements, terminal, expressionBlock, javaScriptNodeArray(blockStatements.size()), 0);
   }

   private JavaScriptNode transformStatements(List<Statement> blockStatements, boolean terminal, boolean expressionBlock, List<JavaScriptNode> prolog) {
      int size = prolog.size() + blockStatements.size();
      JavaScriptNode[] statements = javaScriptNodeArray(size);
      int pos = 0;
      if (!prolog.isEmpty()) {
         while (pos < prolog.size()) {
            statements[pos] = prolog.get(pos);
            pos++;
         }
      }

      return this.transformStatements(blockStatements, terminal, expressionBlock, statements, pos);
   }

   private JavaScriptNode transformStatements(
      List<Statement> blockStatements, boolean terminal, boolean expressionBlock, JavaScriptNode[] statements, int destPos
   ) {
      int pos = destPos;
      int lastNonEmptyIndex = -1;
      boolean returnsLastStatementResult = this.currentFunction().returnsLastStatementResult();

      for (int i = 0; i < blockStatements.size(); i++) {
         Statement statement = blockStatements.get(i);
         JavaScriptNode statementNode = this.transformStatementInBlock(statement);
         if (returnsLastStatementResult) {
            if (statement.isCompletionValueNeverEmpty()) {
               lastNonEmptyIndex = pos;
            } else if (lastNonEmptyIndex >= 0) {
               statements[lastNonEmptyIndex] = this.wrapSetCompletionValue(statements[lastNonEmptyIndex]);
               lastNonEmptyIndex = -1;
            }
         }

         statements[pos++] = statementNode;
      }

      if (returnsLastStatementResult && lastNonEmptyIndex >= 0) {
         statements[lastNonEmptyIndex] = this.wrapSetCompletionValue(statements[lastNonEmptyIndex]);
      }

      assert pos == statements.length;

      return this.createBlock(statements, terminal, expressionBlock);
   }

   private GraalJSTranslator.EnvironmentCloseable enterBlockEnvironment(Block block) {
      if (block.isFunctionBody() && this.lc.getCurrentFunction().isScript() && this.argumentNames == null) {
         FunctionEnvironment currentFunction = this.currentFunction();
         if (!currentFunction.isEval()) {
            GlobalEnvironment globalEnv = new GlobalEnvironment(this.environment, this.factory, this.context);
            setupGlobalEnvironment(globalEnv, block);
            return new GraalJSTranslator.EnvironmentCloseable(globalEnv);
         }

         if (currentFunction.isIndirectEval()) {
            GlobalEnvironment globalEnv = new GlobalEnvironment(this.environment, this.factory, this.context);
            BlockEnvironment blockEnv = new BlockEnvironment(globalEnv, this.factory, this.context, block.getScope());
            blockEnv.addFrameSlotsFromSymbols(block.getScope().getSymbols());
            return new GraalJSTranslator.EnvironmentCloseable(blockEnv);
         }

         assert currentFunction.isDirectEval();
      }

      return this.enterBlockEnvironment(block.getScope());
   }

   private GraalJSTranslator.EnvironmentCloseable enterBlockEnvironment(Scope scope) {
      if (scope != null) {
         if (scope.isFunctionTopScope() || scope.isEvalScope()) {
            assert this.environment instanceof FunctionEnvironment;

            Environment functionEnv = this.environment;
            FunctionNode function = this.lc.getCurrentFunction();

            assert function.hasClosures() || !hasClosures(function.getBody()) : function;

            if (!function.isModule()
               && (this.allowScopeOptimization() ? BlockEnvironment.isScopeCaptured(scope) : function.hasClosures() || function.hasEval())) {
               functionEnv = new BlockEnvironment(this.environment, this.factory, this.context, scope);
            }

            boolean onlyBlockScoped = this.currentFunction().isCallerContextEval();
            this.addFunctionFrameSlots(functionEnv, function);
            functionEnv.addFrameSlotsFromSymbols(scope.getSymbols(), onlyBlockScoped, null);
            return new GraalJSTranslator.EnvironmentCloseable(functionEnv);
         }

         if (scope.hasDeclarations()) {
            BlockEnvironment blockEnv = new BlockEnvironment(this.environment, this.factory, this.context, scope);
            blockEnv.addFrameSlotsFromSymbols(scope.getSymbols());
            return new GraalJSTranslator.EnvironmentCloseable(blockEnv);
         }
      }

      return new GraalJSTranslator.EnvironmentCloseable(this.environment);
   }

   private Environment newPerIterationEnvironment(Scope scope) {
      BlockEnvironment blockEnv = new BlockEnvironment(this.environment, this.factory, this.context, scope);
      blockEnv.addFrameSlotsFromSymbols(scope.getSymbols(), true, !scope.hasNestedEval() && this.allowScopeOptimization() ? Symbol::isClosedOver : null);
      return blockEnv;
   }

   private void addFunctionFrameSlots(Environment env, FunctionNode function) {
      if (function.needsArguments()) {
         assert function.getBody().getScope().hasSymbol("arguments") : function;

         env.reserveArgumentsSlot();
      }

      FunctionEnvironment currentFunction = env.function();
      if (this.needsThisSlot(function, currentFunction)) {
         env.reserveThisSlot();
      }

      if (function.needsSuper()) {
         assert function.isMethod();

         env.reserveSuperSlot();
      }

      if (function.needsNewTarget()) {
         env.reserveNewTargetSlot();
      }
   }

   private boolean needsThisSlot(FunctionNode function, FunctionEnvironment currentFunction) {
      if (currentFunction.isGlobal()) {
         return false;
      } else if (!function.needsThis()
         || (function.isArrow() || currentFunction.isDirectEval()) && currentFunction.getNonArrowParentFunction().isDerivedConstructor()) {
         if (function.needsSuper()) {
            assert function.isMethod();

            return true;
         } else {
            return function.isClassConstructor() && (this.lc.getCurrentClass().hasInstanceFields() || this.lc.getCurrentClass().hasPrivateInstanceMethods());
         }
      } else {
         return true;
      }
   }

   private static void setupGlobalEnvironment(GlobalEnvironment globalEnv, Block block) {
      for (Symbol symbol : block.getSymbols()) {
         if (!symbol.isImportBinding()) {
            if (symbol.isBlockScoped()) {
               globalEnv.addLexicalDeclaration(symbol.getNameTS(), symbol.isConst());
            } else if (symbol.isGlobal() && symbol.isVar()) {
               globalEnv.addVarDeclaration(symbol.getNameTS());
            }
         }
      }
   }

   private JavaScriptNode transformStatementInBlock(Statement statement) {
      return this.transform(statement);
   }

   public JavaScriptNode enterBlockStatement(BlockStatement blockStatement) {
      return this.transform(blockStatement.getBlock());
   }

   public JavaScriptNode enterLiteralNode(LiteralNode<?> literalNode) {
      return literalNode instanceof LiteralNode.ArrayLiteralNode
         ? this.tagExpression(this.createArrayLiteral(((LiteralNode.ArrayLiteralNode)literalNode).getElementExpressions()), literalNode)
         : this.tagExpression(this.enterLiteralDefaultNode(literalNode), literalNode);
   }

   private JavaScriptNode enterLiteralDefaultNode(LiteralNode<?> literalNode) {
      Object value = literalNode.getValue();
      if (value == null) {
         return this.factory.createConstantNull();
      } else if (value instanceof Long) {
         long longValue = (Long)value;
         return JSRuntime.isSafeInteger(longValue) ? this.factory.createConstantSafeInteger(longValue) : this.factory.createConstantDouble(longValue);
      } else if (value instanceof Lexer.RegexToken) {
         return this.factory.createRegExpLiteral(this.context, ((Lexer.RegexToken)value).getExpressionTS(), ((Lexer.RegexToken)value).getOptionsTS());
      } else {
         if (value instanceof BigInteger) {
            value = BigInt.fromBigInteger((BigInteger)value);
         }

         return this.factory.createConstant(value);
      }
   }

   private JavaScriptNode createArrayLiteral(List<? extends Expression> elementExpressions) {
      JavaScriptNode[] elements = javaScriptNodeArray(elementExpressions.size());
      boolean hasSpread = false;

      for (int i = 0; i < elementExpressions.size(); i++) {
         Expression elementExpression = elementExpressions.get(i);
         hasSpread = hasSpread || elementExpression != null && elementExpression.isTokenType(TokenType.SPREAD_ARRAY);
         elements[i] = elementExpression != null ? this.transform(elementExpression) : this.factory.createEmpty();
      }

      return hasSpread ? this.factory.createArrayLiteralWithSpread(this.context, elements) : this.factory.createArrayLiteral(this.context, elements);
   }

   public JavaScriptNode enterIdentNode(IdentNode identNode) {
      assert !identNode.isPropertyName();

      JavaScriptNode result;
      if (identNode.isThis()) {
         result = this.createThisNode();
      } else if (identNode.isSuper()) {
         result = this.enterIdentNodeSuper(identNode);
      } else if (identNode.isNewTarget()) {
         result = this.enterNewTarget();
      } else if (identNode.isImportMeta()) {
         result = this.enterImportMeta();
      } else if (identNode.isPrivateInCheck()) {
         TruffleString privateVarName = identNode.getNameTS();
         Environment.VarRef privateVarRef = this.environment.findLocalVar(privateVarName);
         JavaScriptNode readNode = privateVarRef.createReadNode();
         JSFrameSlot frameSlot = privateVarRef.getFrameSlot();
         if (JSFrameUtil.needsPrivateBrandCheck(frameSlot)) {
            result = this.getPrivateBrandNode(frameSlot, privateVarRef);
         } else {
            result = readNode;
         }
      } else {
         TruffleString varName = identNode.getNameTS();
         Environment.VarRef varRef = this.findScopeVarCheckTDZ(varName, false);
         result = varRef.createReadNode();
      }

      return this.tagExpression(result, identNode);
   }

   private JavaScriptNode enterNewTarget() {
      return this.environment.findNewTargetVar().createReadNode();
   }

   private JavaScriptNode enterIdentNodeSuper(IdentNode identNode) {
      if (!identNode.isDirectSuper()) {
         JavaScriptNode getSuperBase = this.factory.createGetPrototype(this.environment.findSuperVar().createReadNode());
         JavaScriptNode receiver = this.checkThisBindingInitialized(this.environment.findThisVar().createReadNode());
         return this.factory.createSuperPropertyReference(getSuperBase, receiver);
      } else {
         assert identNode.isDirectSuper();

         JavaScriptNode activeFunction = this.factory.createAccessCallee(this.currentFunction().getThisFunctionLevel());
         JavaScriptNode superConstructor = this.factory.createGetPrototype(activeFunction);
         JavaScriptNode receiver = this.environment.findThisVar().createReadNode();
         return this.factory.createTargetableWrapper(superConstructor, receiver);
      }
   }

   private JavaScriptNode createThisNode() {
      return this.currentFunction().isGlobal()
         ? this.factory.createAccessThis()
         : this.checkThisBindingInitialized(this.environment.findThisVar().createReadNode());
   }

   private JavaScriptNode createThisNodeUnchecked() {
      return this.currentFunction().isGlobal() ? this.factory.createAccessThis() : this.environment.findThisVar().createReadNode();
   }

   private JavaScriptNode checkThisBindingInitialized(JavaScriptNode accessThisNode) {
      return this.currentFunction().getNonArrowParentFunction().isDerivedConstructor()
         ? this.factory.createDerivedConstructorThis(accessThisNode)
         : accessThisNode;
   }

   private JavaScriptNode enterImportMeta() {
      return this.factory.createImportMeta(this.getActiveModule());
   }

   private JavaScriptNode getActiveModule() {
      assert this.lc.inModule();

      return this.environment.findActiveModule().createReadNode();
   }

   private JavaScriptNode getActiveScriptOrModule() {
      return this.lc.inModule() ? this.getActiveModule() : null;
   }

   private Environment.VarRef findScopeVar(TruffleString name, boolean skipWith) {
      return this.environment.findVar(name, skipWith);
   }

   private Environment.VarRef findScopeVarCheckTDZ(TruffleString name, boolean initializationAssignment) {
      Environment.VarRef varRef = this.findScopeVar(name, false);
      if (varRef.isFunctionLocal()) {
         if (varRef.hasBeenDeclared()) {
            return varRef;
         } else {
            Symbol symbol = this.lc.getCurrentScope().findBlockScopedSymbolInFunction(varRef.getName().toJavaStringUncached());
            if (symbol == null) {
               return varRef;
            } else if (symbol.hasBeenDeclared()) {
               return varRef;
            } else if (symbol.isDeclaredInSwitchBlock()) {
               return varRef.withTDZCheck();
            } else if (initializationAssignment) {
               varRef.setHasBeenDeclared(true);
               return varRef;
            } else {
               return varRef.withTDZCheck();
            }
         }
      } else {
         return varRef.withTDZCheck();
      }
   }

   public JavaScriptNode enterVarNode(VarNode varNode) {
      TruffleString varName = varNode.getName().getNameTS();

      assert this.currentFunction().isGlobal() && (!varNode.isBlockScoped() || this.lc.getCurrentBlock().isFunctionBody())
         || !this.findScopeVar(varName, true).isGlobal()
         || this.currentFunction().isCallerContextEval() : varNode;

      Symbol symbol = null;
      Environment.VarRef varRef = null;
      if (varNode.isBlockScoped()) {
         symbol = this.lc.getCurrentScope().getExistingSymbol(varNode.getName().getName());
         varRef = this.findScopeVar(varName, true);

         assert symbol != null && varRef != null : varName;
      }

      JavaScriptNode assignment;
      if (varNode.isAssignment()) {
         assignment = this.createVarAssignNode(varNode, varName);
      } else if (symbol != null && (!varNode.isDestructuring() || symbol.isDeclaredInSwitchBlock()) && !symbol.hasBeenDeclared()) {
         assert varNode.isBlockScoped();

         assignment = varRef.createWriteNode(this.factory.createConstantUndefined());
      } else {
         assignment = this.factory.createEmpty();
      }

      if (symbol != null && !symbol.isDeclaredInSwitchBlock() && !varNode.isDestructuring()) {
         assert varNode.isBlockScoped();

         varRef.setHasBeenDeclared(true);
      }

      return assignment;
   }

   private JavaScriptNode createVarAssignNode(VarNode varNode, TruffleString varName) {
      JavaScriptNode assignment = null;
      if (varNode.isBlockScoped() && varNode.isFunctionDeclaration() && this.context.isOptionAnnexB()) {
         FunctionNode fn = this.lc.getCurrentFunction();
         if (!fn.isStrict()) {
            Symbol symbol = this.lc.getCurrentScope().getExistingSymbol(varName.toJavaStringUncached());
            if (symbol.isHoistedBlockFunctionDeclaration() && varNode.getFlag(32)) {
               assert hasVarSymbol(fn.getVarDeclarationBlock().getScope(), varName) : varName;

               JavaScriptNode blockScopeValue = this.findScopeVar(varName, false).createReadNode();
               assignment = this.environment.findVar(varName, true, false, true, false, false).withRequired(false).createWriteNode(blockScopeValue);
               this.tagExpression(assignment, varNode);
            }
         }
      }

      if (assignment == null) {
         JavaScriptNode rhs = this.transform(varNode.getAssignmentSource());
         assignment = this.findScopeVar(varName, false).createWriteNode(rhs);
      }

      if (varNode.isClassDeclaration()) {
         return this.discardResult(assignment);
      } else {
         if (!varNode.isHoistableDeclaration()) {
            this.tagStatement(assignment, varNode);
         }

         this.ensureHasSourceSection(assignment, varNode);
         return this.discardResult(assignment);
      }
   }

   private static boolean hasVarSymbol(Scope scope, TruffleString varName) {
      Symbol varSymbol = scope.getExistingSymbol(varName.toJavaStringUncached());
      return varSymbol != null && varSymbol.isVar() && !varSymbol.isParam();
   }

   public JavaScriptNode enterWhileNode(WhileNode whileNode) {
      JavaScriptNode test = this.transform(whileNode.getTest());
      this.tagStatement(test, whileNode.getTest());

      JavaScriptNode var7;
      try (FunctionEnvironment.JumpTargetCloseable<ContinueTarget> target = this.currentFunction().pushContinueTarget(null)) {
         JavaScriptNode body = this.transform(whileNode.getBody());
         JavaScriptNode wrappedBody = this.wrapClearCompletionValue(target.wrapContinueTargetNode(body));
         JavaScriptNode result;
         if (whileNode.isDoWhile()) {
            result = this.createDoWhile(test, wrappedBody);
         } else {
            result = this.createWhileDo(test, wrappedBody);
         }

         var7 = this.wrapClearAndGetCompletionValue(target.wrapBreakTargetNode(this.ensureHasSourceSection(result, whileNode)));
      }

      return var7;
   }

   private static boolean isConstantFalse(JavaScriptNode condition) {
      return condition instanceof JSConstantNode && !JSRuntime.toBoolean(((JSConstantNode)condition).getValue());
   }

   private JavaScriptNode createDoWhile(JavaScriptNode condition, JavaScriptNode body) {
      if (isConstantFalse(condition)) {
         return body;
      } else {
         RepeatingNode repeatingNode = this.factory.createDoWhileRepeatingNode(condition, body);
         return this.factory.createDoWhile(this.factory.createLoopNode(repeatingNode));
      }
   }

   private JavaScriptNode createWhileDo(JavaScriptNode condition, JavaScriptNode body) {
      if (isConstantFalse(condition)) {
         return this.factory.createEmpty();
      } else {
         RepeatingNode repeatingNode = this.factory.createWhileDoRepeatingNode(condition, body);
         return this.factory.createWhileDo(this.factory.createLoopNode(repeatingNode));
      }
   }

   private JavaScriptNode wrapGetCompletionValue(JavaScriptNode target) {
      if (this.currentFunction().returnsLastStatementResult()) {
         Environment.VarRef returnVar = this.environment.findTempVar(this.currentFunction().getReturnSlot());
         return this.factory.createExprBlock(target, returnVar.createReadNode());
      } else {
         return target;
      }
   }

   private JavaScriptNode wrapSetCompletionValue(JavaScriptNode statement) {
      if (this.currentFunction().returnsLastStatementResult()) {
         Environment.VarRef returnVar = this.environment.findTempVar(this.currentFunction().getReturnSlot());
         return returnVar.createWriteNode(statement);
      } else {
         return statement;
      }
   }

   private JavaScriptNode wrapClearCompletionValue(JavaScriptNode statement) {
      if (this.currentFunction().returnsLastStatementResult()) {
         Environment.VarRef returnVar = this.environment.findTempVar(this.currentFunction().getReturnSlot());
         return this.factory.createExprBlock(returnVar.createWriteNode(this.factory.createConstantUndefined()), statement);
      } else {
         return statement;
      }
   }

   private JavaScriptNode wrapClearAndGetCompletionValue(JavaScriptNode statement) {
      if (this.currentFunction().returnsLastStatementResult()) {
         Environment.VarRef returnVar = this.environment.findTempVar(this.currentFunction().getReturnSlot());
         return this.factory.createExprBlock(returnVar.createWriteNode(this.factory.createConstantUndefined()), statement, returnVar.createReadNode());
      } else {
         return statement;
      }
   }

   private JavaScriptNode wrapSaveAndRestoreCompletionValue(JavaScriptNode statement) {
      if (this.currentFunction().returnsLastStatementResult()) {
         Environment.VarRef returnVar = this.environment.findTempVar(this.currentFunction().getReturnSlot());
         Environment.VarRef tempVar = this.environment.createTempVar();
         return this.factory
            .createExprBlock(tempVar.createWriteNode(returnVar.createReadNode()), statement, returnVar.createWriteNode(tempVar.createReadNode()));
      } else {
         return statement;
      }
   }

   public JavaScriptNode enterForNode(ForNode forNode) {
      JavaScriptNode init = forNode.getInit() != null && !forNode.isForInOrOf()
         ? this.tagStatement(this.transform(forNode.getInit()), forNode.getInit())
         : this.factory.createEmpty();
      JavaScriptNode test = forNode.getTest() != null && forNode.getTest().getExpression() != null
         ? this.tagStatement(this.transform(forNode.getTest()), forNode.getTest())
         : this.factory.createConstantBoolean(true);
      JavaScriptNode modify = forNode.getModify() != null
         ? this.tagStatement(this.transform(forNode.getModify()), forNode.getModify())
         : this.factory.createEmpty();

      JavaScriptNode body;
      try (FunctionEnvironment.JumpTargetCloseable<ContinueTarget> target = this.currentFunction().pushContinueTarget(null)) {
         JavaScriptNode result;
         if (forNode.isForOf()) {
            result = this.desugarForOf(forNode, modify, target);
         } else if (forNode.isForIn()) {
            result = this.desugarForIn(forNode, modify, target);
         } else if (forNode.isForAwaitOf()) {
            result = this.desugarForAwaitOf(forNode, modify, target);
         } else {
            body = this.transform(forNode.getBody());
            JavaScriptNode wrappedBody = this.wrapClearCompletionValue(target.wrapContinueTargetNode(body));
            result = target.wrapBreakTargetNode(this.desugarFor(forNode, init, test, modify, wrappedBody));
         }

         body = this.wrapClearAndGetCompletionValue(result);
      }

      return body;
   }

   private JavaScriptNode desugarFor(ForNode forNode, JavaScriptNode init, JavaScriptNode test, JavaScriptNode modify, JavaScriptNode wrappedBody) {
      if (this.needsPerIterationScope(forNode)) {
         Environment.VarRef firstTempVar = this.environment.createTempVar();
         JSFrameDescriptor iterationBlockFrameDescriptor = this.environment.getBlockFrameDescriptor();
         RepeatingNode repeatingNode = this.factory
            .createForRepeatingNode(
               test,
               wrappedBody,
               modify,
               iterationBlockFrameDescriptor.toFrameDescriptor(),
               firstTempVar.createReadNode(),
               firstTempVar.createWriteNode(this.factory.createConstantBoolean(false)),
               this.environment.getCurrentBlockScopeSlot()
            );
         StatementNode newFor = this.factory.createFor(this.factory.createLoopNode(repeatingNode));
         this.ensureHasSourceSection(newFor, forNode);
         return this.createBlock(init, firstTempVar.createWriteNode(this.factory.createConstantBoolean(true)), newFor);
      } else {
         RepeatingNode repeatingNode = this.factory.createWhileDoRepeatingNode(test, this.createBlock(wrappedBody, modify));
         JavaScriptNode whileDo = this.factory.createDesugaredFor(this.factory.createLoopNode(repeatingNode));
         if (forNode.getTest() == null) {
            this.tagStatement(test, forNode);
         } else {
            this.ensureHasSourceSection(whileDo, forNode);
         }

         return this.createBlock(init, whileDo);
      }
   }

   private JavaScriptNode desugarForIn(ForNode forNode, JavaScriptNode modify, FunctionEnvironment.JumpTargetCloseable<ContinueTarget> jumpTarget) {
      JavaScriptNode createIteratorNode;
      if (forNode.isForEach()) {
         createIteratorNode = this.factory.createEnumerate(this.context, modify, true);
      } else {
         assert forNode.isForIn() && !forNode.isForEach() && !forNode.isForOf();

         createIteratorNode = this.factory.createEnumerate(this.context, modify, false);
      }

      return this.desugarForInOrOfBody(forNode, this.factory.createGetIterator(this.context, createIteratorNode), jumpTarget);
   }

   private JavaScriptNode desugarForOf(ForNode forNode, JavaScriptNode modify, FunctionEnvironment.JumpTargetCloseable<ContinueTarget> jumpTarget) {
      assert forNode.isForOf();

      JavaScriptNode getIterator = this.factory.createGetIterator(this.context, modify);
      return this.desugarForInOrOfBody(forNode, getIterator, jumpTarget);
   }

   private JavaScriptNode desugarForInOrOfBody(ForNode forNode, JavaScriptNode iterator, FunctionEnvironment.JumpTargetCloseable<ContinueTarget> jumpTarget) {
      assert forNode.isForInOrOf();

      Environment.VarRef iteratorVar = this.environment.createTempVar();
      JavaScriptNode iteratorInit = iteratorVar.createWriteNode(iterator);
      Environment.VarRef nextResultVar = this.environment.createTempVar();
      JavaScriptNode iteratorNext = this.factory.createIteratorNext(iteratorVar.createReadNode());
      JavaScriptNode condition = this.factory
         .createDual(
            this.context,
            this.factory.createIteratorSetDone(iteratorVar.createReadNode(), this.factory.createConstantBoolean(true)),
            this.factory
               .createUnary(NodeFactory.UnaryOperation.NOT, this.factory.createIteratorComplete(this.context, nextResultVar.createWriteNode(iteratorNext)))
         );

      JavaScriptNode wrappedBody;
      try (GraalJSTranslator.EnvironmentCloseable blockEnv = new GraalJSTranslator.EnvironmentCloseable(
            this.needsPerIterationScope(forNode) ? this.newPerIterationEnvironment(this.lc.getCurrentBlock().getScope()) : this.environment
         )) {
         Environment.VarRef nextResultVar2 = this.environment.findTempVar(nextResultVar.getFrameSlot());
         Environment.VarRef nextValueVar = this.environment.createTempVar();
         Environment.VarRef iteratorVar2 = this.environment.findTempVar(iteratorVar.getFrameSlot());
         JavaScriptNode nextResult = nextResultVar2.createReadNode();
         JavaScriptNode nextValue = this.factory.createIteratorValue(nextResult);
         JavaScriptNode writeNextValue = nextValueVar.createWriteNode(nextValue);
         JavaScriptNode writeNext = this.tagStatement(this.desugarForHeadAssignment(forNode, nextValueVar.createReadNode()), forNode);
         JavaScriptNode body = this.transform(forNode.getBody());
         wrappedBody = blockEnv.wrapBlockScope(
            this.createBlock(
               writeNextValue, this.factory.createIteratorSetDone(iteratorVar2.createReadNode(), this.factory.createConstantBoolean(false)), writeNext, body
            )
         );
      }

      wrappedBody = jumpTarget.wrapContinueTargetNode(wrappedBody);
      RepeatingNode var22 = this.factory.createWhileDoRepeatingNode(condition, wrappedBody);
      LoopNode var23 = this.factory.createLoopNode(var22);
      JavaScriptNode whileNode = forNode.isForOf() ? this.factory.createDesugaredForOf(var23) : this.factory.createDesugaredForIn(var23);
      JavaScriptNode wrappedWhile = this.factory
         .createIteratorCloseIfNotDone(this.context, jumpTarget.wrapBreakTargetNode(whileNode), iteratorVar.createReadNode());
      JavaScriptNode resetIterator = iteratorVar.createWriteNode(this.factory.createConstant(JSFrameUtil.DEFAULT_VALUE));
      wrappedWhile = this.factory.createTryFinally(wrappedWhile, resetIterator);
      this.ensureHasSourceSection(whileNode, forNode);
      return this.createBlock(iteratorInit, wrappedWhile);
   }

   private JavaScriptNode desugarForHeadAssignment(ForNode forNode, JavaScriptNode next) {
      boolean lexicalBindingInit = forNode.hasPerIterationScope();
      return forNode.getInit() instanceof IdentNode && lexicalBindingInit
         ? this.tagExpression(this.findScopeVarCheckTDZ(((IdentNode)forNode.getInit()).getNameTS(), lexicalBindingInit).createWriteNode(next), forNode)
         : this.tagExpression(this.transformAssignment(forNode.getInit(), forNode.getInit(), next, lexicalBindingInit), forNode);
   }

   private JavaScriptNode desugarForAwaitOf(ForNode forNode, JavaScriptNode modify, FunctionEnvironment.JumpTargetCloseable<ContinueTarget> jumpTarget) {
      assert forNode.isForAwaitOf();

      JavaScriptNode getIterator = this.factory.createGetAsyncIterator(this.context, modify);
      Environment.VarRef iteratorVar = this.environment.createTempVar();
      JavaScriptNode iteratorInit = iteratorVar.createWriteNode(getIterator);
      Environment.VarRef nextResultVar = this.environment.createTempVar();
      this.currentFunction().addAwait();
      JSReadFrameSlotNode asyncResultNode = (JSReadFrameSlotNode)this.environment.findTempVar(this.currentFunction().getAsyncResultSlot()).createReadNode();
      JSReadFrameSlotNode asyncContextNode = (JSReadFrameSlotNode)this.environment.findTempVar(this.currentFunction().getAsyncContextSlot()).createReadNode();
      JSFrameDescriptor functionFrameDesc = this.environment.getFunctionFrameDescriptor();
      JSFrameSlot stateSlot = this.addGeneratorStateSlot(functionFrameDesc, FrameSlotKind.Int);
      JavaScriptNode iteratorNext = this.factory
         .createAsyncIteratorNext(this.context, stateSlot, iteratorVar.createReadNode(), asyncContextNode, asyncResultNode);
      JavaScriptNode condition = this.factory
         .createDual(
            this.context,
            this.factory.createIteratorSetDone(iteratorVar.createReadNode(), this.factory.createConstantBoolean(true)),
            this.factory
               .createUnary(NodeFactory.UnaryOperation.NOT, this.factory.createIteratorComplete(this.context, nextResultVar.createWriteNode(iteratorNext)))
         );

      JavaScriptNode wrappedBody;
      try (GraalJSTranslator.EnvironmentCloseable blockEnv = new GraalJSTranslator.EnvironmentCloseable(
            this.needsPerIterationScope(forNode) ? this.newPerIterationEnvironment(this.lc.getCurrentBlock().getScope()) : this.environment
         )) {
         Environment.VarRef nextResultVar2 = this.environment.findTempVar(nextResultVar.getFrameSlot());
         Environment.VarRef nextValueVar = this.environment.createTempVar();
         Environment.VarRef iteratorVar2 = this.environment.findTempVar(iteratorVar.getFrameSlot());
         JavaScriptNode nextResult = nextResultVar2.createReadNode();
         JavaScriptNode nextValue = this.factory.createIteratorValue(nextResult);
         JavaScriptNode writeNextValue = nextValueVar.createWriteNode(nextValue);
         JavaScriptNode writeNext = this.tagStatement(this.desugarForHeadAssignment(forNode, nextValueVar.createReadNode()), forNode);
         JavaScriptNode body = this.transform(forNode.getBody());
         wrappedBody = blockEnv.wrapBlockScope(
            this.createBlock(
               writeNextValue, this.factory.createIteratorSetDone(iteratorVar2.createReadNode(), this.factory.createConstantBoolean(false)), writeNext, body
            )
         );
      }

      wrappedBody = jumpTarget.wrapContinueTargetNode(wrappedBody);
      RepeatingNode var28 = this.factory.createWhileDoRepeatingNode(condition, wrappedBody);
      LoopNode var29 = this.factory.createLoopNode(var28);
      JavaScriptNode var30 = this.factory.createDesugaredForAwaitOf(var29);
      this.currentFunction().addAwait();
      stateSlot = this.addGeneratorStateSlot(functionFrameDesc, FrameSlotKind.Object);
      JavaScriptNode var31 = this.factory
         .createAsyncIteratorCloseWrapper(
            this.context, stateSlot, jumpTarget.wrapBreakTargetNode(var30), iteratorVar.createReadNode(), asyncContextNode, asyncResultNode
         );
      JavaScriptNode var33 = iteratorVar.createWriteNode(this.factory.createConstant(JSFrameUtil.DEFAULT_VALUE));
      var31 = this.factory.createTryFinally(var31, var33);
      this.ensureHasSourceSection(var30, forNode);
      return this.createBlock(iteratorInit, var31);
   }

   private boolean needsPerIterationScope(ForNode forNode) {
      if (forNode.hasPerIterationScope()) {
         if (this.allowScopeOptimization()) {
            Scope forScope = this.lc.getCurrentScope();
            if (!forScope.hasDeclarations()) {
               return false;
            }

            if (forScope.hasClosures() || forScope.hasNestedEval()) {
               return true;
            }
         } else {
            FunctionNode function = this.lc.getCurrentFunction();
            if (function.hasClosures() && hasClosures(this.lc.getCurrentBlock())) {
               return true;
            }

            if (function.hasEval()) {
               return true;
            }
         }
      }

      return false;
   }

   private static boolean hasClosures(Node node) {
      class HasClosuresVisitor extends NodeVisitor<LexicalContext> {
         boolean hasClosures;

         HasClosuresVisitor(LexicalContext lc) {
            super(lc);
         }

         @Override
         public boolean enterFunctionNode(FunctionNode functionNode) {
            this.hasClosures = true;
            return false;
         }
      }

      HasClosuresVisitor visitor = new HasClosuresVisitor(new LexicalContext());
      node.accept(visitor);
      return visitor.hasClosures;
   }

   public JavaScriptNode enterLabelNode(LabelNode labelNode) {
      JavaScriptNode var4;
      try (FunctionEnvironment.JumpTargetCloseable<BreakTarget> breakTarget = this.currentFunction().pushBreakTarget(labelNode.getLabelName())) {
         JavaScriptNode body = this.transform(labelNode.getBody());
         var4 = breakTarget.wrapLabelBreakTargetNode(body);
      }

      return var4;
   }

   public JavaScriptNode enterBreakNode(BreakNode breakNode) {
      return this.tagStatement(this.factory.createBreak(this.currentFunction().findBreakTarget(breakNode.getLabelName())), breakNode);
   }

   public JavaScriptNode enterContinueNode(ContinueNode continueNode) {
      return this.tagStatement(this.factory.createContinue(this.currentFunction().findContinueTarget(continueNode.getLabelName())), continueNode);
   }

   public JavaScriptNode enterIfNode(IfNode ifNode) {
      JavaScriptNode test = this.transform(ifNode.getTest());
      JavaScriptNode pass = this.transform(ifNode.getPass());
      JavaScriptNode fail = this.transform(ifNode.getFail());
      return this.tagStatement(this.factory.createIf(test, pass, fail), ifNode);
   }

   public JavaScriptNode enterTernaryNode(TernaryNode ternaryNode) {
      JavaScriptNode test = this.transform(ternaryNode.getTest());
      JavaScriptNode pass = this.transform(ternaryNode.getTrueExpression());
      JavaScriptNode fail = this.transform(ternaryNode.getFalseExpression());
      return this.tagExpression(this.factory.createIf(test, pass, fail), ternaryNode);
   }

   public JavaScriptNode enterUnaryNode(UnaryNode unaryNode) {
      switch (unaryNode.tokenType()) {
         case ADD:
         case BIT_NOT:
         case NOT:
         case SUB:
         case VOID:
            return this.enterUnaryDefaultNode(unaryNode);
         case TYPEOF:
            return this.enterTypeofNode(unaryNode);
         case INCPREFIX:
         case INCPOSTFIX:
         case DECPREFIX:
         case DECPOSTFIX:
            return this.enterUnaryIncDecNode(unaryNode);
         case NEW:
            return this.enterNewNode(unaryNode);
         case DELETE:
            return this.enterDelete(unaryNode);
         case SPREAD_ARGUMENT: {
            JavaScriptNode argument = this.transform(unaryNode.getExpression());
            GetIteratorNode getIterator = this.factory.createGetIterator(this.context, argument);
            return this.tagExpression(this.factory.createSpreadArgument(this.context, getIterator), unaryNode);
         }
         case SPREAD_ARRAY: {
            JavaScriptNode array = this.transform(unaryNode.getExpression());
            GetIteratorNode getIterator = this.factory.createGetIterator(this.context, array);
            return this.tagExpression(this.factory.createSpreadArray(this.context, getIterator), unaryNode);
         }
         case YIELD:
         case YIELD_STAR:
            return this.tagExpression(this.createYieldNode(unaryNode), unaryNode);
         case AWAIT:
            return this.tagExpression(this.translateAwaitNode(unaryNode), unaryNode);
         case NAMEDEVALUATION:
            return this.enterNamedEvaluation(unaryNode);
         default:
            throw new UnsupportedOperationException(unaryNode.tokenType().toString());
      }
   }

   public JSFrameSlot addGeneratorStateSlot(JSFrameDescriptor functionFrameDescriptor, FrameSlotKind slotKind) {
      InternalSlotId identifier = this.factory.createInternalSlotId(GENERATORSTATE, functionFrameDescriptor.getSize());
      return functionFrameDescriptor.addFrameSlot(identifier, slotKind);
   }

   private JavaScriptNode translateAwaitNode(UnaryNode unaryNode) {
      JavaScriptNode expression = this.transform(unaryNode.getExpression());
      return this.createAwaitNode(expression);
   }

   private JavaScriptNode createAwaitNode(JavaScriptNode expression) {
      FunctionEnvironment currentFunction = this.currentFunction();
      currentFunction.addAwait();
      JSReadFrameSlotNode asyncContextNode = (JSReadFrameSlotNode)this.environment.findTempVar(currentFunction.getAsyncContextSlot()).createReadNode();
      JSReadFrameSlotNode asyncResultNode = (JSReadFrameSlotNode)this.environment.findTempVar(currentFunction.getAsyncResultSlot()).createReadNode();
      JSFrameSlot stateSlot = this.addGeneratorStateSlot(currentFunction.getFunctionFrameDescriptor(), FrameSlotKind.Int);
      return this.factory.createAwait(this.context, stateSlot, expression, asyncContextNode, asyncResultNode);
   }

   private JavaScriptNode createYieldNode(UnaryNode unaryNode) {
      FunctionEnvironment currentFunction = this.currentFunction();

      assert currentFunction.isGeneratorFunction();

      JSFrameDescriptor functionFrameDesc = currentFunction.getFunctionFrameDescriptor();
      if (this.lc.getCurrentFunction().isModule()) {
         return this.factory.createModuleYield();
      } else {
         boolean asyncGeneratorYield = currentFunction.isAsyncFunction();
         boolean yieldStar = unaryNode.tokenType() == TokenType.YIELD_STAR;
         JavaScriptNode expression = this.transform(unaryNode.getExpression());
         ReturnNode returnNode = this.createReturnNode(null);
         if (asyncGeneratorYield) {
            currentFunction.addAwait();
            JSReadFrameSlotNode asyncContextNode = (JSReadFrameSlotNode)this.environment.findTempVar(currentFunction.getAsyncContextSlot()).createReadNode();
            JSReadFrameSlotNode asyncResultNode = (JSReadFrameSlotNode)this.environment.findTempVar(currentFunction.getAsyncResultSlot()).createReadNode();
            JSFrameSlot stateSlot = this.addGeneratorStateSlot(functionFrameDesc, FrameSlotKind.Int);
            if (yieldStar) {
               JSFrameSlot iteratorTempSlot = this.addGeneratorStateSlot(functionFrameDesc, FrameSlotKind.Object);
               return this.factory
                  .createAsyncGeneratorYieldStar(this.context, stateSlot, iteratorTempSlot, expression, asyncContextNode, asyncResultNode, returnNode);
            } else {
               return this.factory.createAsyncGeneratorYield(this.context, stateSlot, expression, asyncContextNode, asyncResultNode, returnNode);
            }
         } else {
            currentFunction.addYield();
            JSWriteFrameSlotNode writeYieldResultNode = (JSWriteFrameSlotNode)this.environment
               .findTempVar(currentFunction.getYieldResultSlot())
               .createWriteNode(null);
            JSFrameSlot stateSlot = this.addGeneratorStateSlot(functionFrameDesc, yieldStar ? FrameSlotKind.Object : FrameSlotKind.Int);
            return this.factory
               .createYield(
                  this.context, stateSlot, expression, this.environment.findYieldValueVar().createReadNode(), yieldStar, returnNode, writeYieldResultNode
               );
         }
      }
   }

   private JavaScriptNode enterUnaryDefaultNode(UnaryNode unaryNode) {
      assert unaryNode.tokenType() != TokenType.TYPEOF;

      JavaScriptNode operand = this.transform(unaryNode.getExpression());
      return this.tagExpression(this.factory.createUnary(tokenTypeToUnaryOperation(unaryNode.tokenType()), operand), unaryNode);
   }

   private JavaScriptNode enterTypeofNode(UnaryNode unaryNode) {
      assert unaryNode.tokenType() == TokenType.TYPEOF;

      JavaScriptNode operand = null;
      if (unaryNode.getExpression() instanceof IdentNode) {
         IdentNode identNode = (IdentNode)unaryNode.getExpression();
         String identNodeName = identNode.getName();
         TruffleString identNodeNameTS = identNode.getNameTS();
         if (!this.context.isOptionNashornCompatibilityMode()
            || !identNodeName.equals("__LINE__") && !identNodeName.equals("__FILE__") && !identNodeName.equals("__DIR__")) {
            if (!identNode.isThis() && !identNode.isMetaProperty()) {
               operand = this.findScopeVarCheckTDZ(identNodeNameTS, false).withRequired(false).createReadNode();
            }
         } else {
            operand = GlobalPropertyNode.createPropertyNode(this.context, identNodeNameTS);
         }
      }

      if (operand == null) {
         operand = this.transform(unaryNode.getExpression());
      } else {
         this.tagExpression(operand, unaryNode.getExpression());
      }

      return this.tagExpression(this.factory.createUnary(tokenTypeToUnaryOperation(unaryNode.tokenType()), operand), unaryNode);
   }

   private JavaScriptNode enterUnaryIncDecNode(UnaryNode unaryNode) {
      if (unaryNode.getExpression() instanceof IdentNode) {
         IdentNode identNode = (IdentNode)unaryNode.getExpression();

         assert !identNode.isPropertyName() && !identNode.isThis() && !identNode.isMetaProperty() && !identNode.isSuper();

         Environment.VarRef varRef = this.findScopeVarCheckTDZ(identNode.getNameTS(), false);
         if (varRef instanceof Environment.FrameSlotVarRef) {
            Environment.FrameSlotVarRef frameVarRef = (Environment.FrameSlotVarRef)varRef;
            JSFrameSlot frameSlot = frameVarRef.getFrameSlot();
            if (JSFrameUtil.isConst(frameSlot)) {
               return this.tagExpression(this.checkMutableBinding(frameVarRef.createReadNode(), frameSlot.getIdentifier()), unaryNode);
            }

            return this.tagExpression(
               this.factory
                  .createLocalVarInc(tokenTypeToUnaryOperation(unaryNode.tokenType()), frameSlot, frameVarRef.hasTDZCheck(), frameVarRef.createScopeFrameNode()),
               unaryNode
            );
         }
      }

      NodeFactory.BinaryOperation operation = unaryNode.tokenType() != TokenType.INCPREFIX && unaryNode.tokenType() != TokenType.INCPOSTFIX
         ? NodeFactory.BinaryOperation.SUBTRACT
         : NodeFactory.BinaryOperation.ADD;
      boolean isPostfix = unaryNode.tokenType() == TokenType.INCPOSTFIX || unaryNode.tokenType() == TokenType.DECPOSTFIX;
      return this.tagExpression(
         this.transformCompoundAssignment(unaryNode, unaryNode.getExpression(), this.factory.createConstantNumericUnit(), operation, isPostfix, true),
         unaryNode
      );
   }

   private static NodeFactory.UnaryOperation tokenTypeToUnaryOperation(TokenType tokenType) {
      switch (tokenType) {
         case ADD:
            return NodeFactory.UnaryOperation.PLUS;
         case BIT_NOT:
            return NodeFactory.UnaryOperation.BITWISE_COMPLEMENT;
         case NOT:
            return NodeFactory.UnaryOperation.NOT;
         case SUB:
            return NodeFactory.UnaryOperation.MINUS;
         case VOID:
            return NodeFactory.UnaryOperation.VOID;
         case TYPEOF:
            return NodeFactory.UnaryOperation.TYPE_OF;
         case INCPREFIX:
            return NodeFactory.UnaryOperation.PREFIX_LOCAL_INCREMENT;
         case INCPOSTFIX:
            return NodeFactory.UnaryOperation.POSTFIX_LOCAL_INCREMENT;
         case DECPREFIX:
            return NodeFactory.UnaryOperation.PREFIX_LOCAL_DECREMENT;
         case DECPOSTFIX:
            return NodeFactory.UnaryOperation.POSTFIX_LOCAL_DECREMENT;
         case NEW:
         case DELETE:
         default:
            throw new UnsupportedOperationException(tokenType.toString());
      }
   }

   private JavaScriptNode enterNamedEvaluation(UnaryNode unaryNode) {
      return this.factory.createNamedEvaluation(this.transform(unaryNode.getExpression()), this.factory.createAccessArgument(1));
   }

   private JavaScriptNode enterDelete(UnaryNode unaryNode) {
      Expression rhs = unaryNode.getExpression();
      return !(rhs instanceof AccessNode) && !(rhs instanceof IndexNode) ? this.enterDeleteIdent(unaryNode) : this.enterDeleteProperty(unaryNode);
   }

   private JavaScriptNode enterDeleteIdent(UnaryNode unaryNode) {
      Expression rhs = unaryNode.getExpression();
      JavaScriptNode result;
      if (rhs instanceof IdentNode) {
         TruffleString varName = ((IdentNode)rhs).getNameTS();
         Environment.VarRef varRef = this.findScopeVar(varName, varName.equals(Strings.THIS));
         result = varRef.createDeleteNode();
      } else if (rhs instanceof LiteralNode.PrimitiveLiteralNode) {
         result = this.factory.createConstantBoolean(true);
      } else {
         result = this.factory.createDual(this.context, this.transform(rhs), this.factory.createConstantBoolean(true));
      }

      return this.tagExpression(result, unaryNode);
   }

   private JavaScriptNode enterDeleteProperty(UnaryNode deleteNode) {
      BaseNode baseNode = (BaseNode)deleteNode.getExpression();
      if (baseNode.isSuper()) {
         return this.tagExpression(this.factory.createThrowError(JSErrorType.ReferenceError, UNSUPPORTED_REFERENCE_TO_SUPER), deleteNode);
      } else {
         JavaScriptNode target = this.transform(baseNode.getBase());
         JavaScriptNode key;
         if (baseNode instanceof AccessNode) {
            AccessNode accessNode = (AccessNode)baseNode;

            assert !accessNode.isPrivate();

            key = this.factory.createConstantString(accessNode.getPropertyTS());
         } else {
            assert baseNode instanceof IndexNode;

            IndexNode indexNode = (IndexNode)baseNode;
            key = this.transform(indexNode.getIndex());
         }

         if (baseNode.isOptionalChain()) {
            target = this.filterOptionalChainTarget(target, baseNode.isOptional());
         }

         JavaScriptNode delete = this.factory.createDeleteProperty(target, key, this.environment.isStrictMode(), this.context);
         this.tagExpression(delete, deleteNode);
         if (baseNode.isOptionalChain()) {
            delete = this.factory.createOptionalChain(delete);
         }

         return delete;
      }
   }

   private JavaScriptNode filterOptionalChainTarget(JavaScriptNode target, boolean optional) {
      JavaScriptNode innerAccess;
      if (target instanceof OptionalChainNode) {
         innerAccess = ((OptionalChainNode)target).getAccessNode();
      } else if (target instanceof OptionalChainNode.OptionalTargetableNode) {
         innerAccess = ((OptionalChainNode.OptionalTargetableNode)target).getDelegateNode();
      } else {
         innerAccess = target;
      }

      if (optional) {
         innerAccess = this.factory.createOptionalChainShortCircuit(innerAccess);
      }

      return innerAccess;
   }

   private JavaScriptNode[] transformArgs(List<Expression> argList) {
      int len = argList.size();
      if (len > this.context.getFunctionArgumentsLimit()) {
         throw Errors.createSyntaxError("function has too many parameters");
      } else {
         JavaScriptNode[] args = javaScriptNodeArray(len);

         for (int i = 0; i < len; i++) {
            args[i] = this.transform(argList.get(i));
         }

         return args;
      }
   }

   private JavaScriptNode enterNewNode(UnaryNode unaryNode) {
      CallNode callNode = (CallNode)unaryNode.getExpression();
      JavaScriptNode function = this.transform(callNode.getFunction());
      JavaScriptNode[] args = this.transformArgs(callNode.getArgs());
      AbstractFunctionArgumentsNode arguments = this.factory.createFunctionArguments(this.context, args);
      JavaScriptNode call = this.factory.createNew(this.context, function, arguments);
      return this.tagExpression(tagCall(call), unaryNode);
   }

   public JavaScriptNode enterCallNode(CallNode callNode) {
      JavaScriptNode function = this.transform(callNode.getFunction());
      JavaScriptNode[] args = this.transformArgs(callNode.getArgs());
      if (callNode.isOptionalChain()) {
         function = this.filterOptionalChainTarget(function, callNode.isOptional());
      }

      JavaScriptNode call;
      if (callNode.isEval() && args.length >= 1) {
         call = this.createCallEvalNode(function, args);
      } else if (callNode.isApplyArguments() && this.currentFunction().isDirectArgumentsAccess()) {
         call = this.createCallApplyArgumentsNode(function, args);
      } else if (callNode.getFunction() instanceof IdentNode && ((IdentNode)callNode.getFunction()).isDirectSuper()) {
         call = this.createCallDirectSuper(function, args, callNode.isDefaultDerivedConstructorSuperCall());
      } else if (callNode.isImport()) {
         call = this.createImportCallNode(args);
      } else {
         call = this.factory.createFunctionCall(this.context, function, args);
      }

      this.tagExpression(tagCall(call), callNode);
      if (callNode.isOptionalChain()) {
         call = this.factory.createOptionalChain(call);
      }

      return call;
   }

   private JavaScriptNode[] insertNewTargetArg(JavaScriptNode[] args) {
      JavaScriptNode[] result = new JavaScriptNode[args.length + 1];
      result[0] = this.environment.findNewTargetVar().createReadNode();
      System.arraycopy(args, 0, result, 1, args.length);
      return result;
   }

   private JavaScriptNode initializeThis(JavaScriptNode thisValueNode) {
      Environment.VarRef thisVar = this.environment.findThisVar();
      Environment.VarRef tempVar = this.environment.createTempVar();
      JavaScriptNode uninitialized = this.factory
         .createBinary(this.context, NodeFactory.BinaryOperation.IDENTICAL, thisVar.createReadNode(), this.factory.createConstantUndefined());
      return this.factory
         .createIf(
            this.factory.createDual(this.context, tempVar.createWriteNode(thisValueNode), uninitialized),
            this.initializeInstanceElements(thisVar.createWriteNode(tempVar.createReadNode())),
            this.factory.createThrowError(JSErrorType.ReferenceError, SUPER_CALLED_TWICE)
         );
   }

   private JavaScriptNode initializeInstanceElements(JavaScriptNode thisValueNode) {
      ClassNode classNode = this.lc.getCurrentClass();
      if (!classNode.hasInstanceFields() && !classNode.hasPrivateInstanceMethods()) {
         return thisValueNode;
      } else {
         JavaScriptNode constructor = this.factory.createAccessCallee(this.currentFunction().getThisFunctionLevel());
         return this.factory.createInitializeInstanceElements(this.context, thisValueNode, constructor);
      }
   }

   private JavaScriptNode createCallEvalNode(JavaScriptNode function, JavaScriptNode[] args) {
      assert this.currentFunction().isGlobal()
         || this.currentFunction().isStrictMode()
         || this.currentFunction().isDirectEval()
         || this.currentFunction().isDynamicallyScoped();

      for (FunctionEnvironment func = this.currentFunction(); func.getParentFunction() != null; func = func.getParentFunction()) {
         func.setNeedsParentFrame(true);
      }

      return EvalNode.create(
         this.context,
         function,
         args,
         this.createThisNodeUnchecked(),
         new DirectEvalContext(this.lc.getCurrentScope(), this.environment, this.lc.getCurrentClass()),
         this.environment.getCurrentBlockScopeSlot()
      );
   }

   private JavaScriptNode createCallApplyArgumentsNode(JavaScriptNode function, JavaScriptNode[] args) {
      return this.factory.createCallApplyArguments((JSFunctionCallNode)this.factory.createFunctionCall(this.context, function, args));
   }

   private JavaScriptNode createCallDirectSuper(JavaScriptNode function, JavaScriptNode[] args, boolean inDefaultDerivedConstructor) {
      if (inDefaultDerivedConstructor) {
         Environment.VarRef thisVar = this.environment.findThisVar();
         return this.initializeInstanceElements(thisVar.createWriteNode(this.factory.createDefaultDerivedConstructorSuperCall(function)));
      } else {
         return this.initializeThis(this.factory.createFunctionCallWithNewTarget(this.context, function, this.insertNewTargetArg(args)));
      }
   }

   private JavaScriptNode createImportCallNode(JavaScriptNode[] args) {
      assert args.length == 1 || this.context.getContextOptions().isImportAssertions() && args.length == 2;

      return this.context.getContextOptions().isImportAssertions() && args.length == 2
         ? this.factory.createImportCall(this.context, args[0], this.getActiveScriptOrModule(), args[1])
         : this.factory.createImportCall(this.context, args[0], this.getActiveScriptOrModule());
   }

   public JavaScriptNode enterBinaryNode(BinaryNode binaryNode) {
      switch (binaryNode.tokenType()) {
         case ADD:
         case SUB:
         case MUL:
         case EXP:
         case DIV:
         case MOD:
         case EQ:
         case EQ_STRICT:
         case GE:
         case GT:
         case LE:
         case LT:
         case NE:
         case NE_STRICT:
         case BIT_AND:
         case BIT_OR:
         case BIT_XOR:
         case SAR:
         case SHL:
         case SHR:
         case AND:
         case OR:
         case NULLISHCOALESC:
         case INSTANCEOF:
         case IN:
         case COMMARIGHT:
            return this.enterBinaryExpressionNode(binaryNode);
         case BIT_NOT:
         case NOT:
         case VOID:
         case TYPEOF:
         case INCPREFIX:
         case INCPOSTFIX:
         case DECPREFIX:
         case DECPOSTFIX:
         case NEW:
         case DELETE:
         case SPREAD_ARGUMENT:
         case SPREAD_ARRAY:
         case YIELD:
         case YIELD_STAR:
         case AWAIT:
         case NAMEDEVALUATION:
         case COMMALEFT:
         case ARROW:
         default:
            throw new UnsupportedOperationException(binaryNode.tokenType().toString());
         case ASSIGN:
         case ASSIGN_INIT:
            return this.enterBinaryAssignNode(binaryNode);
         case ASSIGN_ADD:
         case ASSIGN_BIT_AND:
         case ASSIGN_BIT_OR:
         case ASSIGN_BIT_XOR:
         case ASSIGN_DIV:
         case ASSIGN_MOD:
         case ASSIGN_MUL:
         case ASSIGN_EXP:
         case ASSIGN_SAR:
         case ASSIGN_SHL:
         case ASSIGN_SHR:
         case ASSIGN_SUB:
         case ASSIGN_AND:
         case ASSIGN_OR:
         case ASSIGN_NULLCOAL:
            return this.enterBinaryTransformNode(binaryNode);
      }
   }

   private JavaScriptNode enterBinaryExpressionNode(BinaryNode binaryNode) {
      Expression lhsExpr = binaryNode.getLhs();
      JavaScriptNode lhs = this.transform(lhsExpr);
      JavaScriptNode rhs = this.transform(binaryNode.getRhs());
      JavaScriptNode result;
      if (lhsExpr instanceof IdentNode && ((IdentNode)lhsExpr).isPrivateInCheck()) {
         result = this.factory.createPrivateFieldIn(lhs, rhs);
      } else {
         result = this.factory.createBinary(this.context, tokenTypeToBinaryOperation(binaryNode.tokenType()), lhs, rhs);
      }

      return this.tagExpression(result, binaryNode);
   }

   private JavaScriptNode enterBinaryTransformNode(BinaryNode binaryNode) {
      JavaScriptNode assignedValue = this.transform(binaryNode.getAssignmentSource());
      return this.tagExpression(
         this.transformCompoundAssignment(
            binaryNode, binaryNode.getAssignmentDest(), assignedValue, tokenTypeToBinaryOperation(binaryNode.tokenType()), false, false
         ),
         binaryNode
      );
   }

   private JavaScriptNode enterBinaryAssignNode(BinaryNode binaryNode) {
      Expression assignmentDest = binaryNode.getAssignmentDest();
      JavaScriptNode assignedValue = this.transform(binaryNode.getAssignmentSource());
      JavaScriptNode assignment = this.transformAssignment(binaryNode, assignmentDest, assignedValue, binaryNode.isTokenType(TokenType.ASSIGN_INIT));

      assert assignedValue != null && (assignedValue.hasTag(StandardTags.ExpressionTag.class) || !assignedValue.isInstrumentable()) : "ExpressionTag expected but not found for: "
         + assignedValue;

      return this.tagExpression(assignment, binaryNode);
   }

   private static NodeFactory.BinaryOperation tokenTypeToBinaryOperation(TokenType tokenType) {
      switch (tokenType) {
         case ADD:
         case ASSIGN_ADD:
            return NodeFactory.BinaryOperation.ADD;
         case BIT_NOT:
         case NOT:
         case VOID:
         case TYPEOF:
         case INCPREFIX:
         case INCPOSTFIX:
         case DECPREFIX:
         case DECPOSTFIX:
         case NEW:
         case DELETE:
         case SPREAD_ARGUMENT:
         case SPREAD_ARRAY:
         case YIELD:
         case YIELD_STAR:
         case AWAIT:
         case NAMEDEVALUATION:
         case ASSIGN:
         case ASSIGN_INIT:
         default:
            throw new UnsupportedOperationException(tokenType.toString());
         case SUB:
         case ASSIGN_SUB:
            return NodeFactory.BinaryOperation.SUBTRACT;
         case ASSIGN_BIT_AND:
         case BIT_AND:
            return NodeFactory.BinaryOperation.BITWISE_AND;
         case ASSIGN_BIT_OR:
         case BIT_OR:
            return NodeFactory.BinaryOperation.BITWISE_OR;
         case ASSIGN_BIT_XOR:
         case BIT_XOR:
            return NodeFactory.BinaryOperation.BITWISE_XOR;
         case ASSIGN_DIV:
         case DIV:
            return NodeFactory.BinaryOperation.DIVIDE;
         case ASSIGN_MOD:
         case MOD:
            return NodeFactory.BinaryOperation.MODULO;
         case ASSIGN_MUL:
         case MUL:
            return NodeFactory.BinaryOperation.MULTIPLY;
         case ASSIGN_EXP:
         case EXP:
            return NodeFactory.BinaryOperation.EXPONENTIATE;
         case ASSIGN_SAR:
         case SAR:
            return NodeFactory.BinaryOperation.BITWISE_RIGHT_SHIFT;
         case ASSIGN_SHL:
         case SHL:
            return NodeFactory.BinaryOperation.BITWISE_LEFT_SHIFT;
         case ASSIGN_SHR:
         case SHR:
            return NodeFactory.BinaryOperation.BITWISE_UNSIGNED_RIGHT_SHIFT;
         case ASSIGN_AND:
         case AND:
            return NodeFactory.BinaryOperation.LOGICAL_AND;
         case ASSIGN_OR:
         case OR:
            return NodeFactory.BinaryOperation.LOGICAL_OR;
         case ASSIGN_NULLCOAL:
         case NULLISHCOALESC:
            return NodeFactory.BinaryOperation.NULLISH_COALESCING;
         case EQ:
            return NodeFactory.BinaryOperation.EQUAL;
         case EQ_STRICT:
            return NodeFactory.BinaryOperation.IDENTICAL;
         case GE:
            return NodeFactory.BinaryOperation.GREATER_OR_EQUAL;
         case GT:
            return NodeFactory.BinaryOperation.GREATER;
         case LE:
            return NodeFactory.BinaryOperation.LESS_OR_EQUAL;
         case LT:
            return NodeFactory.BinaryOperation.LESS;
         case NE:
            return NodeFactory.BinaryOperation.NOT_EQUAL;
         case NE_STRICT:
            return NodeFactory.BinaryOperation.NOT_IDENTICAL;
         case INSTANCEOF:
            return NodeFactory.BinaryOperation.INSTANCEOF;
         case IN:
            return NodeFactory.BinaryOperation.IN;
         case COMMARIGHT:
            return NodeFactory.BinaryOperation.DUAL;
      }
   }

   private JavaScriptNode transformAssignment(
      Expression assignmentExpression, Expression lhsExpression, JavaScriptNode assignedValue, boolean initializationAssignment
   ) {
      return this.transformAssignmentImpl(assignmentExpression, lhsExpression, assignedValue, initializationAssignment, null, false, false);
   }

   private JavaScriptNode transformCompoundAssignment(
      Expression assignmentExpression,
      Expression lhsExpression,
      JavaScriptNode assignedValue,
      NodeFactory.BinaryOperation binaryOp,
      boolean returnOldValue,
      boolean convertLHSToNumeric
   ) {
      return this.transformAssignmentImpl(assignmentExpression, lhsExpression, assignedValue, false, binaryOp, returnOldValue, convertLHSToNumeric);
   }

   private JavaScriptNode transformAssignmentImpl(
      Expression assignmentExpression,
      Expression lhsExpression,
      JavaScriptNode assignedValue,
      boolean initializationAssignment,
      NodeFactory.BinaryOperation binaryOp,
      boolean returnOldValue,
      boolean convertLHSToNumeric
   ) {
      JavaScriptNode assignedNode;
      switch (lhsExpression.tokenType()) {
         case LBRACKET:
            assignedNode = this.transformIndexAssignment((IndexNode)lhsExpression, assignedValue, binaryOp, returnOldValue, convertLHSToNumeric);
            break;
         case PERIOD:
            assignedNode = this.transformPropertyAssignment((AccessNode)lhsExpression, assignedValue, binaryOp, returnOldValue, convertLHSToNumeric);
            break;
         case ARRAY:
            assert binaryOp == null;

            assignedNode = this.transformDestructuringArrayAssignment(lhsExpression, assignedValue, initializationAssignment);
            break;
         case LBRACE:
            assert binaryOp == null;

            assignedNode = this.transformDestructuringObjectAssignment(lhsExpression, assignedValue, initializationAssignment);
            break;
         default:
            if (!(lhsExpression instanceof IdentNode)) {
               throw Errors.unsupported("unsupported assignment to token type: " + lhsExpression.tokenType().toString() + " " + lhsExpression.toString());
            }
         case IDENT:
            assignedNode = this.transformAssignmentIdent(
               (IdentNode)lhsExpression, assignedValue, binaryOp, returnOldValue, convertLHSToNumeric, initializationAssignment
            );
      }

      if (returnOldValue && assignedNode instanceof DualNode) {
         this.ensureHasSourceSection(((DualNode)assignedNode).getLeft(), assignmentExpression);
      }

      return this.tagExpression(assignedNode, assignmentExpression);
   }

   private static boolean isLogicalOp(NodeFactory.BinaryOperation op) {
      return op == NodeFactory.BinaryOperation.LOGICAL_AND
         || op == NodeFactory.BinaryOperation.LOGICAL_OR
         || op == NodeFactory.BinaryOperation.NULLISH_COALESCING;
   }

   private JavaScriptNode transformAssignmentIdent(
      IdentNode identNode,
      JavaScriptNode assignedValue,
      NodeFactory.BinaryOperation binaryOp,
      boolean returnOldValue,
      boolean convertLHSToNumeric,
      boolean initializationAssignment
   ) {
      JavaScriptNode rhs = assignedValue;
      TruffleString ident = identNode.getNameTS();
      Environment.VarRef scopeVar = this.findScopeVarCheckTDZ(ident, initializationAssignment);
      if (!initializationAssignment && scopeVar.isConst()) {
         if (this.context.getContextOptions().isV8LegacyConst() && !this.environment.isStrictMode()) {
            return assignedValue;
         }

         rhs = this.checkMutableBinding(assignedValue, scopeVar.getName());
      }

      if (binaryOp == null) {
         return scopeVar.createWriteNode(rhs);
      } else if (!isLogicalOp(binaryOp)) {
         Pair<Supplier<JavaScriptNode>, UnaryOperator<JavaScriptNode>> pair = scopeVar.createCompoundAssignNode();
         JavaScriptNode readNode = this.tagExpression(pair.getFirst().get(), identNode);
         if (convertLHSToNumeric) {
            readNode = this.factory.createToNumericOperand(readNode);
         }

         Environment.VarRef prevValueTemp = null;
         if (returnOldValue) {
            prevValueTemp = this.environment.createTempVar();
            readNode = prevValueTemp.createWriteNode(readNode);
         }

         JavaScriptNode binOpNode = this.tagExpression(this.factory.createBinary(this.context, binaryOp, readNode, rhs), identNode);
         JavaScriptNode writeNode = pair.getSecond().apply(binOpNode);
         return returnOldValue ? this.factory.createDual(this.context, writeNode, prevValueTemp.createReadNode()) : writeNode;
      } else {
         assert !convertLHSToNumeric && !returnOldValue;

         JavaScriptNode readNodex = this.tagExpression(scopeVar.createReadNode(), identNode);
         JavaScriptNode writeNode = scopeVar.createWriteNode(assignedValue);
         return this.factory.createBinary(this.context, binaryOp, readNodex, writeNode);
      }
   }

   private JavaScriptNode checkMutableBinding(JavaScriptNode rhsNode, Object identifier) {
      if (this.context.getContextOptions().isV8LegacyConst() && !this.environment.isStrictMode()) {
         return rhsNode;
      } else {
         TruffleString message = TruffleString.fromJavaStringUncached(
            this.context.isOptionV8CompatibilityMode() ? "Assignment to constant variable." : "Assignment to constant \"" + identifier + "\"",
            TruffleString.Encoding.UTF_16
         );
         JavaScriptNode throwTypeError = this.factory.createThrowError(JSErrorType.TypeError, message);
         return isPotentiallySideEffecting(rhsNode) ? this.createBlock(rhsNode, throwTypeError) : throwTypeError;
      }
   }

   private JavaScriptNode transformPropertyAssignment(
      AccessNode accessNode, JavaScriptNode assignedValue, NodeFactory.BinaryOperation binaryOp, boolean returnOldValue, boolean convertToNumeric
   ) {
      JavaScriptNode target = this.transform(accessNode.getBase());
      JavaScriptNode assignedNode;
      if (binaryOp == null) {
         assignedNode = this.createWriteProperty(accessNode, target, assignedValue);
      } else {
         JavaScriptNode target1;
         JavaScriptNode target2;
         if (target instanceof RepeatableNode) {
            target1 = target;
            target2 = this.factory.copy(target);
         } else {
            Environment.VarRef targetTemp = this.environment.createTempVar();
            target1 = targetTemp.createWriteNode(target);
            target2 = targetTemp.createReadNode();
         }

         if (isLogicalOp(binaryOp)) {
            assert !convertToNumeric && !returnOldValue;

            JavaScriptNode readNode = this.tagExpression(this.createReadProperty(accessNode, target1), accessNode);
            JavaScriptNode writeNode = this.createWriteProperty(accessNode, target2, assignedValue);
            assignedNode = this.factory.createBinary(this.context, binaryOp, readNode, writeNode);
         } else {
            Environment.VarRef prevValueTemp = null;
            JavaScriptNode readNode = this.tagExpression(this.createReadProperty(accessNode, target2), accessNode);
            if (convertToNumeric) {
               readNode = this.factory.createToNumericOperand(readNode);
            }

            if (returnOldValue) {
               prevValueTemp = this.environment.createTempVar();
               readNode = prevValueTemp.createWriteNode(readNode);
            }

            JavaScriptNode binOpNode = this.tagExpression(this.factory.createBinary(this.context, binaryOp, readNode, assignedValue), accessNode);
            JavaScriptNode writeNode = this.createWriteProperty(accessNode, target1, binOpNode);
            if (returnOldValue) {
               assignedNode = this.factory.createDual(this.context, writeNode, prevValueTemp.createReadNode());
            } else {
               assignedNode = writeNode;
            }
         }
      }

      return assignedNode;
   }

   private JavaScriptNode transformIndexAssignment(
      IndexNode indexNode, JavaScriptNode assignedValue, NodeFactory.BinaryOperation binaryOp, boolean returnOldValue, boolean convertToNumeric
   ) {
      JavaScriptNode target = this.transform(indexNode.getBase());
      JavaScriptNode elem = this.transform(indexNode.getIndex());
      JavaScriptNode assignedNode;
      if (binaryOp == null) {
         assignedNode = this.factory.createWriteElementNode(target, elem, assignedValue, this.context, this.environment.isStrictMode());
      } else {
         Environment.VarRef keyTemp = this.environment.createTempVar();
         JavaScriptNode readIndex = keyTemp.createReadNode();
         JSWriteFrameSlotNode writeIndex = (JSWriteFrameSlotNode)keyTemp.createWriteNode(null);
         JavaScriptNode target1;
         JavaScriptNode target2;
         if ((!(elem instanceof JSConstantNode) || !(target instanceof RepeatableNode)) && !(target instanceof SuperPropertyReferenceNode)) {
            Environment.VarRef targetTemp = this.environment.createTempVar();
            target1 = targetTemp.createWriteNode(target);
            target2 = targetTemp.createReadNode();
         } else {
            target1 = target;
            target2 = this.factory.copy(target);
         }

         if (isLogicalOp(binaryOp)) {
            assert !convertToNumeric && !returnOldValue;

            JavaScriptNode readNode = this.tagExpression(this.factory.createReadElementNode(this.context, target1, keyTemp.createWriteNode(elem)), indexNode);
            JavaScriptNode writeNode = this.factory
               .createCompoundWriteElementNode(target2, readIndex, assignedValue, null, this.context, this.environment.isStrictMode());
            assignedNode = this.factory.createBinary(this.context, binaryOp, readNode, writeNode);
         } else {
            JavaScriptNode readNode = this.tagExpression(this.factory.createReadElementNode(this.context, target2, readIndex), indexNode);
            if (convertToNumeric) {
               readNode = this.factory.createToNumericOperand(readNode);
            }

            Environment.VarRef prevValueTemp = null;
            if (returnOldValue) {
               prevValueTemp = this.environment.createTempVar();
               readNode = prevValueTemp.createWriteNode(readNode);
            }

            JavaScriptNode binOpNode = this.tagExpression(this.factory.createBinary(this.context, binaryOp, readNode, assignedValue), indexNode);
            JavaScriptNode writeNode = this.factory
               .createCompoundWriteElementNode(target1, elem, binOpNode, writeIndex, this.context, this.environment.isStrictMode());
            if (returnOldValue) {
               assignedNode = this.factory.createDual(this.context, writeNode, prevValueTemp.createReadNode());
            } else {
               assignedNode = writeNode;
            }
         }
      }

      return assignedNode;
   }

   private JavaScriptNode transformDestructuringArrayAssignment(Expression lhsExpression, JavaScriptNode assignedValue, boolean initializationAssignment) {
      LiteralNode.ArrayLiteralNode arrayLiteralNode = (LiteralNode.ArrayLiteralNode)lhsExpression;
      List<Expression> elementExpressions = arrayLiteralNode.getElementExpressions();
      JavaScriptNode[] initElements = javaScriptNodeArray(elementExpressions.size());
      Environment.VarRef iteratorTempVar = this.environment.createTempVar();
      Environment.VarRef valueTempVar = this.environment.createTempVar();
      JavaScriptNode initValue = valueTempVar.createWriteNode(assignedValue);
      JavaScriptNode getIterator = this.factory.createGetIterator(this.context, initValue);
      JavaScriptNode initIteratorTempVar = iteratorTempVar.createWriteNode(getIterator);

      for (int i = 0; i < elementExpressions.size(); i++) {
         Expression element = elementExpressions.get(i);
         Expression init = null;
         Expression lhsExpr;
         if (element instanceof IdentNode) {
            lhsExpr = element;
         } else if (element instanceof BinaryNode) {
            assert element.isTokenType(TokenType.ASSIGN) || element.isTokenType(TokenType.ASSIGN_INIT);

            lhsExpr = ((BinaryNode)element).getLhs();
            init = ((BinaryNode)element).getRhs();
         } else {
            lhsExpr = element;
         }

         JavaScriptNode iteratorGetNextValueNode = this.factory
            .createIteratorGetNextValue(this.context, iteratorTempVar.createReadNode(), this.factory.createConstantUndefined(), true, element != null);
         JavaScriptNode iteratorIsDoneNode = this.factory.createIteratorIsDone(iteratorTempVar.createReadNode());
         JavaScriptNode rhsNode = this.factory.createIf(iteratorIsDoneNode, this.factory.createConstantUndefined(), iteratorGetNextValueNode);
         if (init != null) {
            rhsNode = this.factory.createNotUndefinedOr(rhsNode, this.transform(init));
         }

         if (lhsExpr != null && lhsExpr.isTokenType(TokenType.SPREAD_ARRAY)) {
            rhsNode = this.factory.createIteratorToArray(this.context, iteratorTempVar.createReadNode());
            lhsExpr = ((UnaryNode)lhsExpr).getExpression();
         }

         if (lhsExpr != null) {
            initElements[i] = this.transformAssignment(lhsExpr, lhsExpr, rhsNode, initializationAssignment);
         } else {
            initElements[i] = rhsNode;
         }
      }

      JavaScriptNode closeIfNotDone = this.factory.createIteratorCloseIfNotDone(this.context, this.createBlock(initElements), iteratorTempVar.createReadNode());
      return this.factory.createExprBlock(initIteratorTempVar, closeIfNotDone, valueTempVar.createReadNode());
   }

   private JavaScriptNode transformDestructuringObjectAssignment(Expression lhsExpression, JavaScriptNode assignedValue, boolean initializationAssignment) {
      ObjectNode objectLiteralNode = (ObjectNode)lhsExpression;
      List<PropertyNode> propertyExpressions = objectLiteralNode.getElements();
      if (propertyExpressions.isEmpty()) {
         return this.factory.createRequireObjectCoercible(assignedValue);
      } else {
         int numberOfProperties = propertyExpressions.size();
         boolean hasRest = propertyExpressions.get(numberOfProperties - 1).isRest();
         boolean requireObjectCoercible = hasRest && numberOfProperties == 1;
         JavaScriptNode[] initElements = javaScriptNodeArray(numberOfProperties);
         JavaScriptNode[] excludedKeys = hasRest ? javaScriptNodeArray(numberOfProperties - 1) : null;
         Environment.VarRef valueTempVar = this.environment.createTempVar();
         JavaScriptNode initValueTempVar = valueTempVar.createWriteNode(
            requireObjectCoercible ? this.factory.createRequireObjectCoercible(assignedValue) : assignedValue
         );

         for (int i = 0; i < numberOfProperties; i++) {
            PropertyNode property = propertyExpressions.get(i);
            Expression init = null;
            Expression lhsExpr;
            if (property.getValue() instanceof BinaryNode) {
               assert property.getValue().isTokenType(TokenType.ASSIGN) || property.getValue().isTokenType(TokenType.ASSIGN_INIT);

               lhsExpr = ((BinaryNode)property.getValue()).getLhs();
               init = ((BinaryNode)property.getValue()).getRhs();
            } else if (property.isRest()) {
               assert hasRest;

               lhsExpr = ((UnaryNode)property.getKey()).getExpression();
            } else {
               lhsExpr = property.getValue();
            }

            JavaScriptNode toPropertyKey = null;
            JavaScriptNode rhsNode;
            if (property.isRest()) {
               JavaScriptNode excludedItemsArray = excludedKeys.length == 0 ? null : this.factory.createArrayLiteral(this.context, excludedKeys);
               rhsNode = this.factory.createRestObject(this.context, valueTempVar.createReadNode(), excludedItemsArray);
            } else if (property.getKey() instanceof IdentNode && !property.isComputed()) {
               TruffleString keyName = property.getKeyNameTS();
               if (hasRest) {
                  excludedKeys[i] = this.factory.createConstantString(keyName);
               }

               rhsNode = this.factory.createReadProperty(this.context, valueTempVar.createReadNode(), keyName);
            } else {
               JavaScriptNode key = this.transform(property.getKey());
               Environment.VarRef keyTempVar = this.environment.createTempVar();
               if (hasRest) {
                  excludedKeys[i] = keyTempVar.createReadNode();
               }

               toPropertyKey = keyTempVar.createWriteNode(this.factory.createToPropertyKey(key));
               rhsNode = this.factory.createReadElementNode(this.context, valueTempVar.createReadNode(), keyTempVar.createReadNode());
            }

            if (init != null) {
               rhsNode = this.factory.createNotUndefinedOr(rhsNode, this.transform(init));
            }

            JavaScriptNode initElement = this.transformAssignment(lhsExpr, lhsExpr, rhsNode, initializationAssignment);
            initElements[i] = toPropertyKey == null ? initElement : this.factory.createDual(this.context, toPropertyKey, initElement);
         }

         return this.factory.createExprBlock(initValueTempVar, this.createBlock(initElements), valueTempVar.createReadNode());
      }
   }

   public JavaScriptNode enterAccessNode(AccessNode accessNode) {
      JavaScriptNode base = this.transform(accessNode.getBase());
      if (accessNode.isOptionalChain()) {
         return this.createOptionalAccessNode(accessNode, base);
      } else {
         JavaScriptNode read = this.createReadProperty(accessNode, base);
         this.tagExpression(read, accessNode);
         return read;
      }
   }

   private JavaScriptNode createOptionalAccessNode(AccessNode accessNode, JavaScriptNode base) {
      JavaScriptNode innerAccess = this.filterOptionalChainTarget(base, accessNode.isOptional());
      JavaScriptNode read = this.createReadProperty(accessNode, innerAccess);
      this.tagExpression(read, accessNode);
      return this.factory.createOptionalChain(read);
   }

   private JavaScriptNode createReadProperty(AccessNode accessNode, JavaScriptNode base) {
      return (JavaScriptNode)(accessNode.isPrivate()
         ? this.createPrivateFieldGet(accessNode, base)
         : this.factory.createReadProperty(this.context, base, accessNode.getPropertyTS(), accessNode.isFunction()));
   }

   private JavaScriptNode createWriteProperty(AccessNode accessNode, JavaScriptNode base, JavaScriptNode rhs) {
      return (JavaScriptNode)(accessNode.isPrivate()
         ? this.createPrivateFieldSet(accessNode, base, rhs)
         : this.factory.createWriteProperty(base, accessNode.getPropertyTS(), rhs, this.context, this.environment.isStrictMode()));
   }

   private JavaScriptNode createPrivateFieldGet(AccessNode accessNode, JavaScriptNode base) {
      Environment.VarRef privateNameVar = this.environment.findLocalVar(accessNode.getPrivateNameTS());
      JavaScriptNode privateName = privateNameVar.createReadNode();
      return this.factory.createPrivateFieldGet(this.context, this.insertPrivateBrandCheck(base, privateNameVar), privateName);
   }

   private JavaScriptNode createPrivateFieldSet(AccessNode accessNode, JavaScriptNode base, JavaScriptNode rhs) {
      Environment.VarRef privateNameVar = this.environment.findLocalVar(accessNode.getPrivateNameTS());
      JavaScriptNode privateName = privateNameVar.createReadNode();
      return this.factory.createPrivateFieldSet(this.context, this.insertPrivateBrandCheck(base, privateNameVar), privateName, rhs);
   }

   private JavaScriptNode insertPrivateBrandCheck(JavaScriptNode base, Environment.VarRef privateNameVar) {
      JSFrameSlot frameSlot = privateNameVar.getFrameSlot();
      if (JSFrameUtil.needsPrivateBrandCheck(frameSlot)) {
         JavaScriptNode brand = this.getPrivateBrandNode(frameSlot, privateNameVar);
         return this.factory.createPrivateBrandCheck(base, brand);
      } else {
         return base;
      }
   }

   private JSFrameSlot getConstructorFrameSlotForVariable(Environment.VarRef privateNameVar) {
      int frameLevel = ((Environment.AbstractFrameVarRef)privateNameVar).getFrameLevel();
      int scopeLevel = ((Environment.AbstractFrameVarRef)privateNameVar).getScopeLevel();
      Environment memberEnv = this.environment.getParentAt(frameLevel, scopeLevel);
      return memberEnv.findBlockFrameSlot(ClassNode.PRIVATE_CONSTRUCTOR_BINDING_NAME);
   }

   private JavaScriptNode getPrivateBrandNode(JSFrameSlot frameSlot, Environment.VarRef privateNameVar) {
      int frameLevel = ((Environment.AbstractFrameVarRef)privateNameVar).getFrameLevel();
      int scopeLevel = ((Environment.AbstractFrameVarRef)privateNameVar).getScopeLevel();
      Environment memberEnv = this.environment.getParentAt(frameLevel, scopeLevel);
      JSFrameSlot constructorSlot = memberEnv.findBlockFrameSlot(ClassNode.PRIVATE_CONSTRUCTOR_BINDING_NAME);
      JavaScriptNode constructor = this.environment.createLocal(constructorSlot, frameLevel, scopeLevel);
      return JSFrameUtil.isPrivateNameStatic(frameSlot) ? constructor : this.factory.createGetPrivateBrand(this.context, constructor);
   }

   public JavaScriptNode enterIndexNode(IndexNode indexNode) {
      JavaScriptNode base = this.transform(indexNode.getBase());
      JavaScriptNode index = this.transform(indexNode.getIndex());
      return indexNode.isOptionalChain()
         ? this.createOptionalIndexNode(indexNode, base, index)
         : this.tagExpression(this.factory.createReadElementNode(this.context, base, index), indexNode);
   }

   private JavaScriptNode createOptionalIndexNode(IndexNode indexNode, JavaScriptNode base, JavaScriptNode index) {
      JavaScriptNode read = this.factory.createReadElementNode(this.context, this.filterOptionalChainTarget(base, indexNode.isOptional()), index);
      this.tagExpression(read, indexNode);
      return this.factory.createOptionalChain(read);
   }

   public JavaScriptNode enterObjectNode(ObjectNode objectNode) {
      ArrayList<ObjectLiteralNode.ObjectLiteralMemberNode> members = this.transformPropertyDefinitionList(objectNode.getElements(), false, null);
      return this.tagExpression(this.factory.createObjectLiteral(this.context, members), objectNode);
   }

   private ArrayList<ObjectLiteralNode.ObjectLiteralMemberNode> transformPropertyDefinitionList(
      List<? extends PropertyNode> properties, boolean isClass, Symbol classNameSymbol
   ) {
      ArrayList<ObjectLiteralNode.ObjectLiteralMemberNode> members = new ArrayList<>(properties.size());

      for (int i = 0; i < properties.size(); i++) {
         PropertyNode property = properties.get(i);

         assert !property.isCoverInitializedName();

         ObjectLiteralNode.ObjectLiteralMemberNode member;
         if (property.getValue() != null || property.isClassField()) {
            member = this.enterObjectPropertyNode(property, isClass, classNameSymbol);
         } else if (property.isRest()) {
            assert !isClass;

            JavaScriptNode from = this.transform(((UnaryNode)property.getKey()).getExpression());
            member = this.factory.createSpreadObjectMember(property.isStatic(), from);
         } else {
            member = this.enterObjectAccessorNode(property, isClass);
         }

         members.add(member);
      }

      return members;
   }

   private DecoratorListEvaluationNode[] transformClassElementsDecorators(List<ClassElement> elements) {
      DecoratorListEvaluationNode[] decoratedElements = new DecoratorListEvaluationNode[elements.size()];
      int i = 0;

      for (ClassElement element : elements) {
         JavaScriptNode[] decorators = null;
         if (element.getDecorators() != null) {
            List<Expression> d = element.getDecorators();
            decorators = new JavaScriptNode[d.size()];

            for (int j = 0; j < d.size(); j++) {
               decorators[j] = this.transform(d.get(j));
            }
         }

         decoratedElements[i++] = decorators != null ? DecoratorListEvaluationNode.create(decorators) : null;
      }

      return decoratedElements;
   }

   private ObjectLiteralNode.ObjectLiteralMemberNode enterObjectAccessorNode(PropertyNode property, boolean isClass) {
      assert property.getGetter() != null || property.getSetter() != null;

      JavaScriptNode getter = this.getAccessor(property.getGetter());
      JavaScriptNode setter = this.getAccessor(property.getSetter());
      boolean enumerable = !isClass;
      if (property.isComputed()) {
         JavaScriptNode key = this.transform(property.getKey());
         JavaScriptNode keyWrapper = this.factory.createToPropertyKey(key);
         return this.factory.createComputedAccessorMember(keyWrapper, property.isStatic(), enumerable, getter, setter);
      } else if (property.isPrivate()) {
         Environment.VarRef privateVar = this.environment.findLocalVar(property.getPrivateNameTS());
         JSWriteFrameSlotNode writePrivateNode = (JSWriteFrameSlotNode)privateVar.createWriteNode(null);
         JSFrameSlot constructorSlot = this.getConstructorFrameSlotForVariable(privateVar);
         return this.factory.createPrivateAccessorMember(property.isStatic(), getter, setter, writePrivateNode, constructorSlot.getIndex());
      } else {
         return this.factory.createAccessorMember(property.getKeyNameTS(), property.isStatic(), enumerable, getter, setter);
      }
   }

   private JavaScriptNode getAccessor(FunctionNode accessorFunction) {
      if (accessorFunction == null) {
         return null;
      } else {
         JavaScriptNode function = this.transform(accessorFunction);
         if (accessorFunction.needsSuper()) {
            assert accessorFunction.isMethod();

            function = this.factory.createMakeMethod(this.context, function);
         }

         return function;
      }
   }

   private JavaScriptNode transformPropertyValue(Expression propertyValue, Symbol classNameSymbol) {
      if (propertyValue == null) {
         return this.factory.createConstantUndefined();
      } else {
         Environment.VarRef classNameVarRef = null;
         if (classNameSymbol != null) {
            classNameVarRef = this.findScopeVar(classNameSymbol.getNameTS(), true);

            assert classNameVarRef.isFrameVar() : classNameSymbol;

            assert !classNameVarRef.hasBeenDeclared() : classNameSymbol;

            classNameVarRef.setHasBeenDeclared(true);
         }

         JavaScriptNode value = this.transform(propertyValue);
         if (classNameSymbol != null) {
            classNameVarRef.setHasBeenDeclared(false);
         }

         if (propertyValue instanceof FunctionNode && ((FunctionNode)propertyValue).needsSuper()) {
            assert ((FunctionNode)propertyValue).isMethod();

            value = this.factory.createMakeMethod(this.context, value);
         }

         return value;
      }
   }

   private ObjectLiteralNode.ObjectLiteralMemberNode enterObjectPropertyNode(PropertyNode property, boolean isClass, Symbol classNameSymbol) {
      JavaScriptNode value = this.transformPropertyValue(property.getValue(), classNameSymbol);
      boolean enumerable = !isClass || property.isClassField();
      if (property instanceof ClassElement && ((ClassElement)property).isAutoAccessor()) {
         if (property.isComputed()) {
            JavaScriptNode computedKey = this.transform(property.getKey());
            return this.factory.createComputedAutoAccessor(computedKey, property.isStatic(), enumerable, value);
         } else {
            return this.factory.createAutoAccessor(property.getKeyNameTS(), property.isStatic(), enumerable, value);
         }
      } else if (property.isComputed()) {
         JavaScriptNode computedKey = this.transform(property.getKey());
         return this.factory
            .createComputedDataMember(computedKey, property.isStatic(), enumerable, value, property.isClassField(), property.isAnonymousFunctionDefinition());
      } else if (!isClass && property.isProto()) {
         return this.factory.createProtoMember(property.getKeyNameTS(), property.isStatic(), value);
      } else if (property.isPrivate()) {
         Environment.VarRef privateVar = this.environment.findLocalVar(property.getPrivateNameTS());
         if (property.isClassField()) {
            JSWriteFrameSlotNode writePrivateNode = (JSWriteFrameSlotNode)privateVar.createWriteNode(
               this.factory.createNewPrivateName(property.getPrivateNameTS())
            );
            return this.factory.createPrivateFieldMember(privateVar.createReadNode(), property.isStatic(), value, writePrivateNode);
         } else {
            JSWriteFrameSlotNode writePrivateNode = (JSWriteFrameSlotNode)privateVar.createWriteNode(null);
            JSFrameSlot constructorSlot = this.getConstructorFrameSlotForVariable(privateVar);
            return this.factory
               .createPrivateMethodMember(property.getPrivateNameTS(), property.isStatic(), value, writePrivateNode, constructorSlot.getIndex());
         }
      } else if (isClass && property.isClassStaticBlock()) {
         return this.factory.createStaticBlockMember(value);
      } else {
         assert property.getKey() != null;

         return this.factory.createDataMember(property.getKeyNameTS(), property.isStatic(), enumerable, value, property.isClassField());
      }
   }

   public JavaScriptNode enterTryNode(TryNode tryNode) {
      JavaScriptNode tryBlock = this.transform(tryNode.getBody());
      JavaScriptNode result = tryBlock;
      if (!tryNode.getCatchBlocks().isEmpty()) {
         for (Block catchParamBlock : tryNode.getCatchBlocks()) {
            CatchNode catchClause = (CatchNode)catchParamBlock.getLastStatement();
            Expression catchParameter = catchClause.getException();
            Block catchBody = catchClause.getBody();
            Expression pattern = catchClause.getDestructuringPattern();

            try (GraalJSTranslator.EnvironmentCloseable catchParamEnv = this.enterBlockEnvironment(catchParamBlock)) {
               this.lc.push(catchParamBlock);

               try {
                  for (Statement statement : catchParamBlock.getStatements().subList(0, catchParamBlock.getStatementCount() - 1)) {
                     assert statement instanceof VarNode;

                     JavaScriptNode empty = this.transform(statement);

                     assert empty instanceof EmptyNode;
                  }

                  JavaScriptNode writeErrorVar = null;
                  JavaScriptNode destructuring = null;
                  if (catchParameter != null) {
                     TruffleString errorVarName = ((IdentNode)catchParameter).getNameTS();
                     Environment.VarRef errorVar = this.environment.findLocalVar(errorVarName);
                     writeErrorVar = errorVar.createWriteNode(null);
                     if (pattern != null) {
                        destructuring = this.transformAssignment(pattern, pattern, errorVar.createReadNode(), true);
                        destructuring = this.wrapTemporalDeadZoneInit(catchParamBlock.getScope(), destructuring);
                     }
                  }

                  JavaScriptNode catchBlock = this.transform(catchBody);
                  JavaScriptNode conditionExpression;
                  if (catchClause.getExceptionCondition() != null) {
                     conditionExpression = this.transform(catchClause.getExceptionCondition());
                  } else {
                     conditionExpression = null;
                  }

                  BlockScopeNode blockScope = (BlockScopeNode)catchParamEnv.wrapBlockScope(null);
                  result = this.factory.createTryCatch(this.context, result, catchBlock, writeErrorVar, blockScope, destructuring, conditionExpression);
                  this.ensureHasSourceSection(result, tryNode);
               } finally {
                  this.lc.pop(catchParamBlock);
               }
            }
         }
      }

      if (tryNode.getFinallyBody() != null) {
         JavaScriptNode finallyBlock = this.transform(tryNode.getFinallyBody());
         result = this.factory.createTryFinally(result, this.wrapSaveAndRestoreCompletionValue(this.wrapClearCompletionValue(finallyBlock)));
      }

      return this.wrapClearAndGetCompletionValue(result);
   }

   public JavaScriptNode enterThrowNode(ThrowNode throwNode) {
      return this.tagStatement(this.factory.createThrow(this.context, this.transform(throwNode.getExpression())), throwNode);
   }

   public JavaScriptNode enterSwitchNode(SwitchNode switchNode) {
      Block switchBlock = this.lc.getCurrentBlock();

      assert switchBlock.isSwitchBlock();

      InternalSlotId switchVarName = this.makeUniqueTempVarNameForStatement(Strings.SWITCH, switchNode.getLineNumber());
      this.environment.declareLocalVar(switchVarName);
      JavaScriptNode switchExpression = this.transform(switchNode.getExpression());
      boolean isSwitchTypeofString = isSwitchTypeofStringConstant(switchNode, switchExpression);
      if (isSwitchTypeofString) {
         switchExpression = ((TypeOfNode)switchExpression).getOperand();
      }

      Environment.VarRef switchVar = this.environment.findInternalSlot(switchVarName);
      JavaScriptNode writeSwitchNode = switchVar.createWriteNode(switchExpression);

      JavaScriptNode switchBody;
      try (FunctionEnvironment.JumpTargetCloseable<BreakTarget> target = this.currentFunction().pushBreakTarget(null)) {
         if (JSConfig.OptimizeNoFallthroughSwitch && isNoFallthroughSwitch(switchNode)) {
            switchBody = this.ifElseFromSwitch(switchNode, switchVar, isSwitchTypeofString);
         } else {
            switchBody = this.defaultSwitchNode(switchNode, switchVar, isSwitchTypeofString);
         }

         this.tagStatement(switchBody, switchNode);
         switchBody = this.wrapClearAndGetCompletionValue(target.wrapBreakTargetNode(switchBody));
      }

      return this.createBlock(writeSwitchNode, switchBody);
   }

   private JavaScriptNode defaultSwitchNode(SwitchNode switchNode, Environment.VarRef switchVar, boolean isSwitchTypeofString) {
      List<CaseNode> cases = switchNode.getCases();
      int size = cases.size() + (switchNode.hasDefaultCase() ? 0 : 1);
      int[] jumptable = new int[size];
      int defaultpos = -1;
      List<JavaScriptNode> declarationList = new ArrayList<>();
      List<JavaScriptNode> statementList = new ArrayList<>();
      List<JavaScriptNode> caseExprList = new ArrayList<>();
      int lastNonEmptyIndex = -1;

      for (CaseNode switchCase : cases) {
         if (switchCase.getTest() != null) {
            jumptable[caseExprList.size()] = statementList.size();
            JavaScriptNode readSwitchVarNode = switchVar.createReadNode();
            caseExprList.add(this.createSwitchCaseExpr(isSwitchTypeofString, switchCase, readSwitchVarNode));
         } else {
            defaultpos = statementList.size();
         }

         if (!switchCase.getStatements().isEmpty()) {
            List<Statement> statements = switchCase.getStatements();

            for (int i = 0; i < statements.size(); i++) {
               Statement statement = statements.get(i);
               if (statement instanceof VarNode) {
                  VarNode varNode = (VarNode)statement;
                  if (varNode.isHoistableDeclaration()) {
                     declarationList.add(this.transform(statement));
                     if (!this.annexBBlockToFunctionTransfer(varNode)) {
                        continue;
                     }

                     statement = varNode.setFlag(32);
                  }
               }

               JavaScriptNode statementNode = this.transform(statement);
               if (this.currentFunction().returnsLastStatementResult()) {
                  if (!statement.isCompletionValueNeverEmpty()) {
                     if (lastNonEmptyIndex >= 0) {
                        statementList.set(lastNonEmptyIndex, this.wrapSetCompletionValue(statementList.get(lastNonEmptyIndex)));
                        lastNonEmptyIndex = -1;
                     }
                  } else {
                     lastNonEmptyIndex = statementList.size();
                  }
               }

               statementList.add(statementNode);
            }
         }
      }

      if (this.currentFunction().returnsLastStatementResult() && lastNonEmptyIndex >= 0) {
         statementList.set(lastNonEmptyIndex, this.wrapSetCompletionValue(statementList.get(lastNonEmptyIndex)));
      }

      jumptable[jumptable.length - 1] = defaultpos != -1 ? defaultpos : statementList.size();
      return this.factory
         .createSwitch(declarationList.toArray(EMPTY_NODE_ARRAY), caseExprList.toArray(EMPTY_NODE_ARRAY), jumptable, statementList.toArray(EMPTY_NODE_ARRAY));
   }

   private JavaScriptNode createSwitchCaseExpr(boolean isSwitchTypeofString, CaseNode switchCase, JavaScriptNode readSwitchVarNode) {
      tagHiddenExpression(readSwitchVarNode);
      if (isSwitchTypeofString) {
         TruffleString typeString = (TruffleString)((LiteralNode)switchCase.getTest()).getValue();
         return this.tagExpression(this.factory.createTypeofIdentical(readSwitchVarNode, typeString), switchCase);
      } else {
         return this.tagExpression(
            this.factory.createBinary(this.context, NodeFactory.BinaryOperation.IDENTICAL, readSwitchVarNode, this.transform(switchCase.getTest())), switchCase
         );
      }
   }

   private JavaScriptNode ifElseFromSwitch(SwitchNode switchNode, Environment.VarRef switchVar, boolean isSwitchTypeofString) {
      assert isNoFallthroughSwitch(switchNode);

      List<CaseNode> cases = switchNode.getCases();
      CaseNode defaultCase = switchNode.getDefaultCase();
      JavaScriptNode curNode = null;
      if (defaultCase != null) {
         curNode = this.dropTerminalDirectBreakStatement(this.transformStatements(defaultCase.getStatements(), false, false));
         this.ensureHasSourceSection(curNode, defaultCase);
      }

      boolean defaultCascade = false;
      boolean lastCase = true;

      for (int i = cases.size() - 1; i >= 0; i--) {
         CaseNode caseNode = cases.get(i);
         if (caseNode.getTest() == null) {
            defaultCascade = true;
         } else {
            JavaScriptNode readSwitchVarNode = switchVar.createReadNode();
            JavaScriptNode test = this.createSwitchCaseExpr(isSwitchTypeofString, caseNode, readSwitchVarNode);
            if (!caseNode.getStatements().isEmpty() || lastCase) {
               JavaScriptNode pass = this.dropTerminalDirectBreakStatement(this.transformStatements(caseNode.getStatements(), false, false));
               this.ensureHasSourceSection(pass, caseNode);
               curNode = this.factory.createIf(test, pass, curNode);
               defaultCascade = false;
            } else if (defaultCascade) {
               if (isPotentiallySideEffecting(test)) {
                  JavaScriptNode var14 = this.factory.createIf(test, null, null);
                  this.ensureHasSourceSection(var14, caseNode);
                  curNode = curNode == null ? this.discardResult(var14) : this.createBlock(var14, curNode);
               }
            } else {
               assert curNode instanceof com.oracle.truffle.js.nodes.control.IfNode;

               com.oracle.truffle.js.nodes.control.IfNode prevIfNode = (com.oracle.truffle.js.nodes.control.IfNode)curNode;
               curNode = this.factory.copyIfWithCondition(prevIfNode, this.factory.createLogicalOr(test, prevIfNode.getCondition()));
            }

            this.ensureHasSourceSection(curNode, caseNode.getTest());
         }

         lastCase = false;
      }

      return curNode == null ? this.factory.createEmpty() : curNode;
   }

   static boolean isPotentiallySideEffecting(JavaScriptNode test) {
      return test instanceof JSReadFrameSlotNode ? ((JSReadFrameSlotNode)test).hasTemporalDeadZone() : !(test instanceof RepeatableNode);
   }

   private JavaScriptNode dropTerminalDirectBreakStatement(JavaScriptNode pass) {
      if (pass instanceof SequenceNode) {
         JavaScriptNode[] statements = ((SequenceNode)pass).getStatements();
         if (statements.length > 0 && isDirectBreakStatement(statements[statements.length - 1])) {
            return this.createBlock(Arrays.copyOfRange(statements, 0, statements.length - 1));
         }
      }

      return pass;
   }

   private static boolean isDirectBreakStatement(JavaScriptNode statement) {
      return statement instanceof com.oracle.truffle.js.nodes.control.BreakNode && ((com.oracle.truffle.js.nodes.control.BreakNode)statement).isDirectBreak();
   }

   private static boolean isNoFallthroughSwitch(SwitchNode switchNode) {
      List<CaseNode> cases = switchNode.getCases();

      for (int i = 0; i < cases.size() - 1; i++) {
         CaseNode caseNode = cases.get(i);
         List<Statement> statements = caseNode.getStatements();
         if (statements.isEmpty()) {
            if (caseNode.getTest() == null) {
               return false;
            }
         } else {
            Statement lastStatement = statements.get(statements.size() - 1);
            if (!lastStatement.hasTerminalFlags()) {
               return false;
            }
         }
      }

      return true;
   }

   private static boolean isSwitchTypeofStringConstant(SwitchNode switchNode, JavaScriptNode switchExpression) {
      if (!(switchExpression instanceof TypeOfNode)) {
         return false;
      } else {
         for (CaseNode switchCase : switchNode.getCases()) {
            Node test = switchCase.getTest();
            if (test != null && (!(test instanceof LiteralNode) || !Strings.isTString(((LiteralNode)test).getValue()))) {
               return false;
            }
         }

         return true;
      }
   }

   private JavaScriptNode discardResult(JavaScriptNode test) {
      return this.currentFunction().returnsLastStatementResult() ? this.factory.createVoidBlock(test) : test;
   }

   public JavaScriptNode enterEmptyNode(com.oracle.js.parser.ir.EmptyNode emptyNode) {
      return this.factory.createEmpty();
   }

   public JavaScriptNode enterWithNode(WithNode withNode) {
      if (this.context.isOptionDisableWith()) {
         throw Errors.createSyntaxError("with statement is disabled.");
      } else {
         FunctionNode function = this.lc.getCurrentFunction();
         Environment withParentEnv = (Environment)(!function.hasClosures() && !function.hasEval()
            ? this.environment
            : new BlockEnvironment(this.environment, this.factory, this.context));

         JavaScriptNode var17;
         try (GraalJSTranslator.EnvironmentCloseable withParent = new GraalJSTranslator.EnvironmentCloseable(withParentEnv)) {
            JavaScriptNode withExpression = this.transform(withNode.getExpression());
            JavaScriptNode toObject = this.factory.createToObjectFromWith(this.context, withExpression, true);
            InternalSlotId withVarName = this.makeUniqueTempVarNameForStatement(Strings.WITH, withNode.getLineNumber());
            this.environment.declareInternalSlot(withVarName);
            JavaScriptNode writeWith = this.environment.findInternalSlot(withVarName).createWriteNode(toObject);

            JavaScriptNode withStatement;
            try (GraalJSTranslator.EnvironmentCloseable withEnv = this.enterWithEnvironment(withVarName)) {
               JavaScriptNode withBody = this.transform(withNode.getBody());
               withStatement = this.tagStatement(this.factory.createWith(writeWith, this.wrapClearAndGetCompletionValue(withBody)), withNode);
            }

            var17 = withParent.wrapBlockScope(withStatement);
         }

         return var17;
      }
   }

   private GraalJSTranslator.EnvironmentCloseable enterWithEnvironment(Object withVarName) {
      return new GraalJSTranslator.EnvironmentCloseable(new WithEnvironment(this.environment, this.factory, this.context, withVarName));
   }

   public JavaScriptNode enterTemplateLiteralNode(TemplateLiteralNode templateLiteralNode) {
      JavaScriptNode result = null;
      if (templateLiteralNode instanceof TemplateLiteralNode.TaggedTemplateLiteralNode) {
         TemplateLiteralNode.TaggedTemplateLiteralNode tagged = (TemplateLiteralNode.TaggedTemplateLiteralNode)templateLiteralNode;
         result = this.factory
            .createTemplateObject(this.context, this.createArrayLiteral(tagged.getRawStrings()), this.createArrayLiteral(tagged.getCookedStrings()));
      } else {
         List<Expression> expressions = ((TemplateLiteralNode.UntaggedTemplateLiteralNode)templateLiteralNode).getExpressions();

         for (int i = 0; i < expressions.size(); i++) {
            JavaScriptNode expr = this.transform(expressions.get(i));

            assert i % 2 != 0 || expr instanceof JSConstantNode : expr;

            if (i % 2 != 0) {
               expr = this.factory.createToString(expr);
            }

            result = result == null ? expr : this.factory.createBinary(this.context, NodeFactory.BinaryOperation.ADD, result, expr);
         }
      }

      return this.tagExpression(result, templateLiteralNode);
   }

   public JavaScriptNode enterDebuggerNode(DebuggerNode debuggerNode) {
      return this.tagStatement(this.factory.createDebugger(), debuggerNode);
   }

   public JavaScriptNode enterExpressionStatement(ExpressionStatement expressionStatement) {
      JavaScriptNode expression = this.transform(expressionStatement.getExpression());
      return this.tagStatement(expression, expressionStatement);
   }

   public JavaScriptNode enterJoinPredecessorExpression(JoinPredecessorExpression expr) {
      return this.tagExpression(this.transform(expr.getExpression()), expr);
   }

   public JavaScriptNode enterClassNode(ClassNode classNode) {
      Scope classHeadScope = classNode.getClassHeadScope();
      Scope classBodyScope = classNode.getScope();
      boolean needsScope = classHeadScope.hasDeclarations() || classBodyScope.hasDeclarations();

      JavaScriptNode var23;
      try (GraalJSTranslator.EnvironmentCloseable blockEnv = new GraalJSTranslator.EnvironmentCloseable(
            needsScope ? this.newClassEnvironment(classHeadScope) : this.environment
         )) {
         TruffleString className = null;
         Symbol classNameSymbol = null;
         IdentNode ident = classNode.getIdent();
         if (ident != null) {
            className = ident.getNameTS();
            classNameSymbol = classHeadScope.getExistingSymbol(ident.getName());
         }

         JavaScriptNode classHeritage = this.transform(classNode.getClassHeritage());
         GraalJSTranslator.EnvironmentCloseable privateEnv = new GraalJSTranslator.EnvironmentCloseable(this.newPrivateEnvironment(classBodyScope));

         JavaScriptNode classDefinition;
         try {
            JavaScriptNode classFunction = this.transform(classNode.getConstructor().getValue());
            List<ObjectLiteralNode.ObjectLiteralMemberNode> members = this.transformPropertyDefinitionList(classNode.getClassElements(), true, classNameSymbol);
            DecoratorListEvaluationNode[] decoratedElementNodes = this.transformClassElementsDecorators(classNode.getClassElements());
            JavaScriptNode[] classDecorators = this.transformClassDecorators(classNode.getDecorators());
            JSWriteFrameSlotNode writeClassBinding = className == null ? null : (JSWriteFrameSlotNode)this.findScopeVar(className, true).createWriteNode(null);
            JSWriteFrameSlotNode writeInternalConstructorBrand = classNode.hasPrivateMethods()
               ? (JSWriteFrameSlotNode)this.environment.findLocalVar(ClassNode.PRIVATE_CONSTRUCTOR_BINDING_NAME).createWriteNode(null)
               : null;
            classDefinition = this.factory
               .createClassDefinition(
                  this.context,
                  (JSFunctionExpressionNode)classFunction,
                  classHeritage,
                  members.toArray(ObjectLiteralNode.ObjectLiteralMemberNode.EMPTY),
                  writeClassBinding,
                  writeInternalConstructorBrand,
                  classDecorators,
                  decoratedElementNodes,
                  className,
                  classNode.getInstanceFieldCount(),
                  classNode.getStaticElementCount(),
                  classNode.hasPrivateInstanceMethods(),
                  this.environment.getCurrentBlockScopeSlot()
               );
            classDefinition = privateEnv.wrapBlockScope(classDefinition);
         } catch (Throwable var20) {
            try {
               privateEnv.close();
            } catch (Throwable var19) {
               var20.addSuppressed(var19);
            }

            throw var20;
         }

         privateEnv.close();
         if (ident != null) {
            classDefinition = this.wrapTemporalDeadZoneInit(classHeadScope, classDefinition);
         }

         var23 = this.tagExpression(blockEnv.wrapBlockScope(classDefinition), classNode);
      }

      return var23;
   }

   private JavaScriptNode[] transformClassDecorators(List<Expression> decorators) {
      if (decorators == null) {
         return EMPTY_NODE_ARRAY;
      } else {
         JavaScriptNode[] transformedDecorators = new JavaScriptNode[decorators.size()];
         int i = 0;

         for (Expression e : decorators) {
            transformedDecorators[i++] = this.transform(e);
         }

         return transformedDecorators;
      }
   }

   private Environment newClassEnvironment(Scope scope) {
      assert scope.isClassHeadScope();

      BlockEnvironment classEnv = new BlockEnvironment(this.environment, this.factory, this.context, scope);
      classEnv.addFrameSlotsFromSymbols(scope.getSymbols());
      return classEnv;
   }

   private Environment newPrivateEnvironment(Scope scope) {
      assert scope.isClassBodyScope();

      if (scope.hasPrivateNames()) {
         PrivateEnvironment privateEnv = new PrivateEnvironment(this.environment, this.factory, this.context, scope);
         privateEnv.addFrameSlotsFromSymbols(scope.getSymbols());
         return privateEnv;
      } else {
         return this.environment;
      }
   }

   public JavaScriptNode enterBlockExpression(BlockExpression blockExpression) {
      return this.tagExpression(this.transform(blockExpression.getBlock()), blockExpression);
   }

   public JavaScriptNode enterParameterNode(ParameterNode paramNode) {
      FunctionEnvironment currentFunction = this.currentFunction();
      JavaScriptNode valueNode;
      if (paramNode.isRestParameter()) {
         valueNode = this.factory.createAccessRestArgument(this.context, currentFunction.getLeadingArgumentCount() + paramNode.getIndex());
      } else {
         valueNode = this.factory.createAccessArgument(currentFunction.getLeadingArgumentCount() + paramNode.getIndex());
      }

      return this.tagExpression(tagHiddenExpression(valueNode), paramNode);
   }

   protected JavaScriptNode enterDefault(Node node) {
      throw shouldNotReachHere(node);
   }

   private static AssertionError shouldNotReachHere(Node node) {
      throw new AssertionError(String.format("should not reach here. %s(%s)", node.getClass().getSimpleName(), node));
   }

   private SourceSection createSourceSection(FunctionNode functionNode) {
      int start = functionNode.getStartWithoutParens() - this.prologLength;
      int finish = functionNode.getFinishWithoutParens() - this.prologLength;
      int length = this.sourceLength;
      if (finish > 0 && length > start) {
         start = Math.max(0, start);
         finish = Math.min(length, finish);
         return this.source.createSection(start, finish - start);
      } else {
         return this.source.createUnavailableSection();
      }
   }

   private JavaScriptNode ensureHasSourceSection(JavaScriptNode resultNode, Node parseNode) {
      if (!resultNode.hasSourceSection()) {
         this.assignSourceSection(resultNode, parseNode);
         if (resultNode instanceof VarWrapperNode) {
            this.ensureHasSourceSection(((VarWrapperNode)resultNode).getDelegateNode(), parseNode);
         }
      }

      return resultNode;
   }

   private void assignSourceSection(JavaScriptNode resultNode, Node parseNode) {
      int start = parseNode.getStart() - this.prologLength;
      int finish = parseNode.getFinish() - this.prologLength;
      int length = this.sourceLength;
      if (finish > 0 && length > start) {
         start = Math.max(0, start);
         finish = Math.min(length, finish);
         resultNode.setSourceSection(this.source, start, finish - start);
      } else {
         resultNode.setSourceSection(this.source.createUnavailableSection());
      }
   }

   private InternalSlotId makeUniqueTempVarNameForStatement(TruffleString prefix, int lineNumber) {
      InternalSlotId name = this.factory.createInternalSlotId(prefix, lineNumber);

      assert !this.environment.hasLocalVar(name);

      return name;
   }

   private final class EnvironmentCloseable implements AutoCloseable {
      private final Environment prevEnv = GraalJSTranslator.this.environment;
      private final Environment newEnv;
      private int wrappedInBlockScopeNode;

      EnvironmentCloseable(Environment newEnv) {
         this.newEnv = newEnv;
         GraalJSTranslator.this.environment = newEnv;
      }

      public JavaScriptNode wrapBlockScope(JavaScriptNode block) {
         if (this.prevEnv != this.newEnv) {
            this.wrappedInBlockScopeNode++;
            if (this.newEnv instanceof BlockEnvironment) {
               BlockEnvironment blockEnv = (BlockEnvironment)this.newEnv;
               if (blockEnv.hasScopeFrame()) {
                  return GraalJSTranslator.this.factory
                     .createBlockScope(
                        block,
                        blockEnv.getCurrentBlockScopeSlot(),
                        blockEnv.getBlockFrameDescriptor().toFrameDescriptor(),
                        blockEnv.getParentSlot(),
                        blockEnv.isFunctionBlock(),
                        blockEnv.capturesFunctionFrame(),
                        blockEnv.isGeneratorFunctionBlock(),
                        blockEnv.getScopeLevel() > 1,
                        blockEnv.getStart(),
                        blockEnv.getEnd()
                     );
               }

               if (blockEnv.getStart() < blockEnv.getEnd() && !blockEnv.isFunctionBlock()) {
                  return GraalJSTranslator.this.factory.createVirtualBlockScope(block, blockEnv.getStart(), blockEnv.getEnd());
               }
            }
         }

         return block;
      }

      @Override
      public void close() {
         assert GraalJSTranslator.this.environment == this.newEnv;

         assert this.newEnv == this.prevEnv || !(this.newEnv instanceof BlockEnvironment) || this.wrappedInBlockScopeNode == 1;

         GraalJSTranslator.this.environment = this.prevEnv;
      }
   }
}
