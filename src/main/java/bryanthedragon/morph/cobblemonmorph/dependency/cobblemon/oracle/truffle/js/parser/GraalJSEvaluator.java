package com.oracle.truffle.js.parser;

import com.oracle.js.parser.JSErrorType;
import com.oracle.js.parser.ParserException;
import com.oracle.js.parser.ir.Expression;
import com.oracle.js.parser.ir.FunctionNode;
import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.exception.AbstractTruffleException;
import com.oracle.truffle.api.frame.FrameDescriptor;
import com.oracle.truffle.api.frame.MaterializedFrame;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.NodeLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.RootNode;
import com.oracle.truffle.api.object.HiddenKey;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.JSFrameDescriptor;
import com.oracle.truffle.js.nodes.JSFrameSlot;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.NodeFactory;
import com.oracle.truffle.js.nodes.ScriptNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.arguments.AccessIndexedArgumentNode;
import com.oracle.truffle.js.nodes.function.EvalNode;
import com.oracle.truffle.js.nodes.function.FunctionRootNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.nodes.promise.NewPromiseCapabilityNode;
import com.oracle.truffle.js.nodes.promise.PerformPromiseThenNode;
import com.oracle.truffle.js.parser.date.DateParser;
import com.oracle.truffle.js.parser.env.DebugEnvironment;
import com.oracle.truffle.js.parser.env.Environment;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.GraalJSException;
import com.oracle.truffle.js.runtime.JSArguments;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSException;
import com.oracle.truffle.js.runtime.JSFrameUtil;
import com.oracle.truffle.js.runtime.JSParserOptions;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.JSRuntime;
import com.oracle.truffle.js.runtime.JavaScriptRootNode;
import com.oracle.truffle.js.runtime.Strings;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSModuleNamespace;
import com.oracle.truffle.js.runtime.builtins.JSModuleNamespaceObject;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.objects.ExportResolution;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSModuleData;
import com.oracle.truffle.js.runtime.objects.JSModuleLoader;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.PromiseCapabilityRecord;
import com.oracle.truffle.js.runtime.objects.ScriptOrModule;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.Pair;
import java.nio.ByteBuffer;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.Deque;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;
import java.util.Map.Entry;
import java.util.function.Supplier;

public final class GraalJSEvaluator implements JSParser {
   private static final HiddenKey STORE_MODULE_KEY = new HiddenKey("store-module-key");

   @Override
   public ScriptNode parseEval(JSContext context, Node lastNode, Source source) {
      return parseEval(context, lastNode, source, false, null);
   }

   @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException = false)
   @Override
   public ScriptNode parseFunction(JSContext context, String parameterList, String body, boolean generatorFunction, boolean asyncFunction, String sourceName) {
      String wrappedBody = "\n" + body + "\n";

      try {
         GraalJSParserHelper.checkFunctionSyntax(context, context.getParserOptions(), parameterList, wrappedBody, generatorFunction, asyncFunction, sourceName);
      } catch (ParserException var11) {
         var11.setLineNumber(var11.getLineNumber() - 1);
         throw parserToJSError(null, var11, context);
      }

      StringBuilder code = new StringBuilder();
      if (asyncFunction) {
         code.append("(async function");
      } else {
         code.append("(function");
      }

      if (generatorFunction) {
         code.append("*");
      }

      code.append(' ');
      boolean nashornCompat = context.getEcmaScriptVersion() == 5 && context.isOptionNashornCompatibilityMode();
      if (!nashornCompat) {
         code.append("anonymous");
      }

      code.append('(');
      code.append(parameterList);
      if (!nashornCompat) {
         code.append('\n');
      }

      code.append(") {");
      code.append(wrappedBody);
      code.append("})");
      Source source = Source.newBuilder("js", code.toString(), sourceName).build();
      return parseEval(context, null, source, false, null);
   }

   @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException = false)
   @Override
   public ScriptNode parseDirectEval(JSContext context, Node lastNode, Source source, Object evalEnv) {
      DirectEvalContext directEval = (DirectEvalContext)evalEnv;
      return parseEval(context, lastNode, source, directEval.env.isStrictMode(), directEval);
   }

   @CompilerDirectives.TruffleBoundary(transferToInterpreterOnException = false)
   private static ScriptNode parseEval(JSContext context, Node lastNode, Source source, boolean isStrict, DirectEvalContext directEval) {
      context.checkEvalAllowed();
      NodeFactory nodeFactory = NodeFactory.getInstance(context);

      try {
         return JavaScriptTranslator.translateEvalScript(nodeFactory, context, source, isStrict, directEval);
      } catch (ParserException var7) {
         throw parserToJSError(lastNode, var7, context);
      }
   }

   private static JSException parserToJSError(Node lastNode, ParserException e, JSContext context) {
      CompilerAsserts.neverPartOfCompilation();
      String message = e.getMessage().replace("\r\n", "\n");
      if (e.getErrorType() == JSErrorType.ReferenceError) {
         return Errors.createReferenceError(message, e, lastNode);
      } else {
         assert e.getErrorType() == JSErrorType.SyntaxError;

         if (context.isOptionNashornCompatibilityMode() && lastNode instanceof EvalNode) {
            SourceSection sourceSection = lastNode.getSourceSection();
            String name = sourceSection.getSource().getName();
            int lineNumber = sourceSection.getStartLine();
            int columnNumber = sourceSection.getStartColumn() - 1;
            message = name + "#" + lineNumber + ":" + columnNumber + message;
         }

         return Errors.createSyntaxError(message, e, lastNode);
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public ScriptNode evalCompile(JSContext context, String sourceCode, String name) {
      try {
         context.checkEvalAllowed();
         return JavaScriptTranslator.translateScript(
            NodeFactory.getInstance(context), context, Source.newBuilder("js", sourceCode, name).build(), false, "", ""
         );
      } catch (ParserException var5) {
         throw Errors.createSyntaxError(var5.getMessage());
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public ScriptNode parseScript(JSContext context, Source source, String prolog, String epilog, boolean isStrict, List<String> argumentNames) {
      if (isModuleSource(source)) {
         return this.fakeScriptForModule(context, source);
      } else {
         try {
            return JavaScriptTranslator.translateScript(NodeFactory.getInstance(context), context, source, isStrict, prolog, epilog, argumentNames);
         } catch (ParserException var8) {
            throw Errors.createSyntaxError(var8.getMessage());
         }
      }
   }

   private static boolean isModuleSource(Source source) {
      String mimeType = source.getMimeType();
      return "application/javascript+module".equals(mimeType) || mimeType == null && source.getName().endsWith(".mjs");
   }

   private ScriptNode fakeScriptForModule(JSContext context, Source source) {
      JSModuleData parsedModule = this.parseModule(context, source);
      RootNode rootNode = new GraalJSEvaluator.ModuleScriptRoot(context, parsedModule, source);
      JSFunctionData functionData = JSFunctionData.createCallOnly(context, rootNode.getCallTarget(), 0, Strings.EMPTY_STRING);
      return ScriptNode.fromFunctionData(context, functionData);
   }

   private static JSFunctionObject createTopLevelAwaitReject(JSContext context, JSRealm realm) {
      JSFunctionData functionData = context.getOrCreateBuiltinFunctionData(
         JSContext.BuiltinFunctionKey.TopLevelAwaitReject, c -> createTopLevelAwaitRejectImpl(c)
      );
      return JSFunction.create(realm, functionData);
   }

   private static JSFunctionData createTopLevelAwaitRejectImpl(JSContext context) {
      class TopLevelAwaitRejectedRootNode extends JavaScriptRootNode {
         @Node.Child
         private JavaScriptNode argumentNode = AccessIndexedArgumentNode.create(0);

         @Override
         public Object execute(VirtualFrame frame) {
            Object error = this.argumentNode.execute(frame);
            throw JSRuntime.getException(error);
         }
      }

      return JSFunctionData.createCallOnly(context, new TopLevelAwaitRejectedRootNode().getCallTarget(), 1, Strings.EMPTY_STRING);
   }

   private static JSFunctionObject createTopLevelAwaitResolve(JSContext context, JSRealm realm) {
      JSFunctionData functionData = context.getOrCreateBuiltinFunctionData(
         JSContext.BuiltinFunctionKey.TopLevelAwaitResolve, c -> createTopLevelAwaitResolveImpl(c)
      );
      return JSFunction.create(realm, functionData);
   }

   private static JSFunctionData createTopLevelAwaitResolveImpl(JSContext context) {
      class TopLevelAwaitFulfilledRootNode extends JavaScriptRootNode {
         @Override
         public Object execute(VirtualFrame frame) {
            return Undefined.instance;
         }
      }

      return JSFunctionData.createCallOnly(context, new TopLevelAwaitFulfilledRootNode().getCallTarget(), 1, Strings.EMPTY_STRING);
   }

   @Override
   public ScriptNode parseScript(JSContext context, String sourceCode) {
      try {
         return JavaScriptTranslator.translateScript(
            NodeFactory.getInstance(context), context, Source.newBuilder("js", sourceCode, "<unknown>").build(), false, "", ""
         );
      } catch (ParserException var4) {
         throw Errors.createSyntaxError(var4.getMessage());
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Integer[] parseDate(JSRealm realm, String date, boolean extraLenient) {
      DateParser dateParser = new DateParser(realm, date, extraLenient);
      return dateParser.parse() ? dateParser.getDateFields() : null;
   }

   @Override
   public String parseToJSON(JSContext context, String code, String name, boolean includeLoc) {
      return GraalJSParserHelper.parseToJSON(code, name, includeLoc, context.getParserOptions());
   }

   @Override
   public Object getDefaultNodeFactory() {
      return NodeFactory.getDefaultInstance();
   }

   public static Supplier<ScriptNode> internalParseForTiming(JSContext context, Source source) {
      FunctionNode ast = GraalJSParserHelper.parseScript(context, source, new JSParserOptions());
      return () -> JavaScriptTranslator.translateFunction(NodeFactory.getInstance(context), context, null, source, 0, false, ast);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JSModuleData parseModule(JSContext context, Source source) {
      try {
         return JavaScriptTranslator.translateModule(NodeFactory.getInstance(context), context, source);
      } catch (ParserException var4) {
         throw Errors.createSyntaxError(var4.getMessage(), var4, null);
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JSModuleData envParseModule(JSRealm realm, Source source) {
      assert isModuleSource(source) : source;

      CallTarget parseResult = realm.getEnv().parsePublic(source);
      CallTarget moduleScriptCallTarget = JavaScriptLanguage.getParsedProgramCallTarget(((RootCallTarget)parseResult).getRootNode());
      GraalJSEvaluator.ModuleScriptRoot moduleScriptRoot = (GraalJSEvaluator.ModuleScriptRoot)((RootCallTarget)moduleScriptCallTarget).getRootNode();
      return moduleScriptRoot.getModuleData();
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JSModuleRecord parseJSONModule(JSRealm realm, Source source) {
      assert isModuleSource(source) : source;

      Object json = JSFunction.call(
         JSArguments.createOneArg(Undefined.instance, realm.getJsonParseFunctionObject(), Strings.fromJavaString(source.getCharacters().toString()))
      );
      return createSyntheticJSONModule(realm, source, json);
   }

   private static JSModuleRecord createSyntheticJSONModule(JSRealm realm, Source source, Object hostDefined) {
      TruffleString exportName = Strings.DEFAULT;
      JSFrameDescriptor frameDescBuilder = new JSFrameDescriptor(Undefined.instance);
      final JSFrameSlot slot = frameDescBuilder.addFrameSlot(exportName);
      FrameDescriptor frameDescriptor = frameDescBuilder.toFrameDescriptor();
      List<Module.ExportEntry> localExportEntries = Collections.singletonList(Module.ExportEntry.exportSpecifier(exportName));
      Module moduleNode = new Module(
         Collections.emptyList(), Collections.emptyList(), localExportEntries, Collections.emptyList(), Collections.emptyList(), null, null
      );
      JavaScriptRootNode rootNode = new JavaScriptRootNode(realm.getContext().getLanguage(), source.createUnavailableSection(), frameDescriptor) {
         private final int defaultSlot = slot.getIndex();

         @Override
         public Object execute(VirtualFrame frame) {
            JSModuleRecord module = (JSModuleRecord)JSArguments.getUserArgument(frame.getArguments(), 0);
            if (module.getEnvironment() == null) {
               assert module.getStatus() == JSModuleRecord.Status.Linking;

               module.setEnvironment(frame.materialize());
            } else {
               assert module.getStatus() == JSModuleRecord.Status.Evaluating;

               this.setSyntheticModuleExport(module);
            }

            return Undefined.instance;
         }

         private void setSyntheticModuleExport(JSModuleRecord module) {
            module.getEnvironment().setObject(this.defaultSlot, module.getHostDefined());
         }
      };
      CallTarget callTarget = rootNode.getCallTarget();
      JSFunctionData functionData = JSFunctionData.create(realm.getContext(), callTarget, callTarget, 0, Strings.EMPTY_STRING, false, false, true, true);
      JSModuleData parseModule = new JSModuleData(moduleNode, source, functionData, frameDescriptor);
      return new JSModuleRecord(parseModule, realm.getModuleLoader(), hostDefined);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JSModuleRecord hostResolveImportedModule(JSContext context, ScriptOrModule referrer, Module.ModuleRequest moduleRequest) {
      filterSupportedImportAssertions(context, moduleRequest);
      JSModuleLoader moduleLoader = referrer instanceof JSModuleRecord ? ((JSModuleRecord)referrer).getModuleLoader() : JSRealm.get(null).getModuleLoader();
      return moduleLoader.resolveImportedModule(referrer, moduleRequest);
   }

   private static JSModuleRecord hostResolveImportedModule(JSModuleRecord referencingModule, Module.ModuleRequest moduleRequest) {
      filterSupportedImportAssertions(referencingModule.getContext(), moduleRequest);
      return referencingModule.getModuleLoader().resolveImportedModule(referencingModule, moduleRequest);
   }

   private static void filterSupportedImportAssertions(final JSContext context, final Module.ModuleRequest moduleRequest) {
      if (!moduleRequest.getAssertions().isEmpty()) {
         Map<TruffleString, TruffleString> supportedAssertions = new HashMap<>();

         for (Entry<TruffleString, TruffleString> assertion : moduleRequest.getAssertions().entrySet()) {
            TruffleString key = assertion.getKey();
            TruffleString value = assertion.getValue();
            if (context.getSupportedImportAssertions().contains(key)) {
               supportedAssertions.put(key, value);
            }
         }

         moduleRequest.setAssertions(supportedAssertions);
      }
   }

   Collection<TruffleString> getExportedNames(JSModuleRecord moduleRecord) {
      return this.getExportedNames(moduleRecord, new HashSet<>());
   }

   private Collection<TruffleString> getExportedNames(JSModuleRecord moduleRecord, Set<JSModuleRecord> exportStarSet) {
      if (exportStarSet.contains(moduleRecord)) {
         return Collections.emptySortedSet();
      } else {
         exportStarSet.add(moduleRecord);
         Collection<TruffleString> exportedNames = new HashSet<>();
         Module module = moduleRecord.getModule();

         for (Module.ExportEntry exportEntry : module.getLocalExportEntries()) {
            exportedNames.add(exportEntry.getExportName());
         }

         for (Module.ExportEntry exportEntry : module.getIndirectExportEntries()) {
            exportedNames.add(exportEntry.getExportName());
         }

         for (Module.ExportEntry exportEntry : module.getStarExportEntries()) {
            JSModuleRecord requestedModule = hostResolveImportedModule(moduleRecord, exportEntry.getModuleRequest());

            for (TruffleString starName : this.getExportedNames(requestedModule, exportStarSet)) {
               if (!starName.equals(Module.DEFAULT_NAME) && !exportedNames.contains(starName)) {
                  exportedNames.add(starName);
               }
            }
         }

         return exportedNames;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public ExportResolution resolveExport(JSModuleRecord referencingModule, TruffleString exportName) {
      return this.resolveExport(referencingModule, exportName, new HashSet<>());
   }

   private ExportResolution resolveExport(JSModuleRecord referencingModule, TruffleString exportName, Set<Pair<JSModuleRecord, TruffleString>> resolveSet) {
      Pair<JSModuleRecord, TruffleString> resolved = new Pair<>(referencingModule, exportName);
      if (resolveSet.contains(resolved)) {
         return ExportResolution.notFound();
      } else {
         resolveSet.add(resolved);
         Module module = referencingModule.getModule();

         for (Module.ExportEntry exportEntry : module.getLocalExportEntries()) {
            if (exportEntry.getExportName().equals(exportName)) {
               return ExportResolution.resolved(referencingModule, exportEntry.getLocalName());
            }
         }

         for (Module.ExportEntry exportEntryx : module.getIndirectExportEntries()) {
            if (exportEntryx.getExportName().equals(exportName)) {
               JSModuleRecord importedModule = hostResolveImportedModule(referencingModule, exportEntryx.getModuleRequest());
               if (exportEntryx.getImportName().equals(Module.STAR_NAME)) {
                  return ExportResolution.resolved(importedModule, Module.NAMESPACE_EXPORT_BINDING_NAME);
               }

               return this.resolveExport(importedModule, exportEntryx.getImportName(), resolveSet);
            }
         }

         if (exportName.equals(Module.DEFAULT_NAME)) {
            return ExportResolution.notFound();
         } else {
            ExportResolution starResolution = ExportResolution.notFound();

            for (Module.ExportEntry exportEntryxx : module.getStarExportEntries()) {
               JSModuleRecord importedModule = hostResolveImportedModule(referencingModule, exportEntryxx.getModuleRequest());
               ExportResolution resolution = this.resolveExport(importedModule, exportName, resolveSet);
               if (resolution.isAmbiguous()) {
                  return resolution;
               }

               if (!resolution.isNull()) {
                  if (starResolution.isNull()) {
                     starResolution = resolution;
                  } else if (!resolution.equals(starResolution)) {
                     return ExportResolution.ambiguous();
                  }
               }
            }

            return starResolution;
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public JSDynamicObject getModuleNamespace(JSModuleRecord moduleRecord) {
      if (moduleRecord.getNamespace() != null) {
         return moduleRecord.getNamespace();
      } else {
         assert moduleRecord.getStatus() != JSModuleRecord.Status.Unlinked;

         Collection<TruffleString> exportedNames = this.getExportedNames(moduleRecord);
         List<Pair<TruffleString, ExportResolution>> unambiguousNames = new ArrayList<>();

         for (TruffleString exportedName : exportedNames) {
            ExportResolution resolution = this.resolveExport(moduleRecord, exportedName);
            if (resolution.isNull()) {
               throw Errors.createSyntaxError("Could not resolve export");
            }

            if (!resolution.isAmbiguous()) {
               unambiguousNames.add(new Pair<>(exportedName, resolution));
            }
         }

         Map<TruffleString, ExportResolution> sortedNames = new LinkedHashMap<>();
         unambiguousNames.stream()
            .sorted((a, b) -> a.getFirst().compareCharsUTF16Uncached(b.getFirst()))
            .forEachOrdered(p -> sortedNames.put(p.getFirst(), p.getSecond()));
         JSModuleNamespaceObject namespace = JSModuleNamespace.create(moduleRecord.getContext(), JSRealm.get(null), moduleRecord, sortedNames);
         moduleRecord.setNamespace(namespace);
         return namespace;
      }
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public void moduleLinking(JSRealm realm, JSModuleRecord moduleRecord) {
      assert moduleRecord.getStatus() != JSModuleRecord.Status.Linking && moduleRecord.getStatus() != JSModuleRecord.Status.Evaluating;

      Deque<JSModuleRecord> stack = new ArrayDeque<>(4);

      try {
         this.innerModuleLinking(realm, moduleRecord, stack, 0);
      } catch (AbstractTruffleException var5) {
         handleModuleLinkingError(moduleRecord, stack);
         throw var5;
      }

      assert moduleRecord.getStatus() == JSModuleRecord.Status.Linked
         || moduleRecord.getStatus() == JSModuleRecord.Status.EvaluatingAsync
         || moduleRecord.getStatus() == JSModuleRecord.Status.Evaluated;

      assert stack.isEmpty();
   }

   private static void handleModuleLinkingError(JSModuleRecord moduleRecord, Deque<JSModuleRecord> stack) {
      for (JSModuleRecord m : stack) {
         assert m.getStatus() == JSModuleRecord.Status.Linking;

         m.setUnlinked();
      }

      assert moduleRecord.getStatus() == JSModuleRecord.Status.Unlinked;
   }

   private int innerModuleLinking(JSRealm realm, JSModuleRecord moduleRecord, Deque<JSModuleRecord> stack, int index0) {
      if (moduleRecord.getStatus() == JSModuleRecord.Status.Linking
         || moduleRecord.getStatus() == JSModuleRecord.Status.Linked
         || moduleRecord.getStatus() == JSModuleRecord.Status.EvaluatingAsync
         || moduleRecord.getStatus() == JSModuleRecord.Status.Evaluated) {
         return index0;
      } else {
         assert moduleRecord.getStatus() == JSModuleRecord.Status.Unlinked;

         moduleRecord.setStatus(JSModuleRecord.Status.Linking);
         moduleRecord.setDFSIndex(index0);
         moduleRecord.setDFSAncestorIndex(index0);
         int index = index0 + 1;
         stack.push(moduleRecord);
         Module module = moduleRecord.getModule();

         for (Module.ModuleRequest requestedModule : module.getRequestedModules()) {
            JSModuleRecord requiredModule = hostResolveImportedModule(moduleRecord, requestedModule);
            index = this.innerModuleLinking(realm, requiredModule, stack, index);

            assert requiredModule.getStatus() == JSModuleRecord.Status.Linking
               || requiredModule.getStatus() == JSModuleRecord.Status.Linked
               || requiredModule.getStatus() == JSModuleRecord.Status.EvaluatingAsync
               || requiredModule.getStatus() == JSModuleRecord.Status.Evaluated : requiredModule.getStatus();

            assert requiredModule.getStatus() == JSModuleRecord.Status.Linking == stack.contains(requiredModule);

            if (requiredModule.getStatus() == JSModuleRecord.Status.Linking) {
               moduleRecord.setDFSAncestorIndex(Math.min(moduleRecord.getDFSAncestorIndex(), requiredModule.getDFSAncestorIndex()));
            }
         }

         this.moduleInitializeEnvironment(realm, moduleRecord);

         assert occursExactlyOnce(moduleRecord, stack);

         assert moduleRecord.getDFSAncestorIndex() <= moduleRecord.getDFSIndex();

         JSModuleRecord requiredModulex;
         if (moduleRecord.getDFSAncestorIndex() == moduleRecord.getDFSIndex()) {
            do {
               requiredModulex = stack.pop();
               requiredModulex.setStatus(JSModuleRecord.Status.Linked);
            } while (!requiredModulex.equals(moduleRecord));
         }

         return index;
      }
   }

   private void moduleInitializeEnvironment(JSRealm realm, JSModuleRecord moduleRecord) {
      assert moduleRecord.getStatus() == JSModuleRecord.Status.Linking;

      Module module = moduleRecord.getModule();

      for (Module.ExportEntry exportEntry : module.getIndirectExportEntries()) {
         ExportResolution resolution = this.resolveExport(moduleRecord, exportEntry.getExportName());
         if (resolution.isNull() || resolution.isAmbiguous()) {
            throw Errors.createSyntaxError("Could not resolve indirect export entry");
         }
      }

      JSFunctionObject moduleFunction = JSFunction.create(realm, moduleRecord.getFunctionData());
      Object[] arguments = JSArguments.create(Undefined.instance, moduleFunction, moduleRecord);
      JSFunction.getConstructTarget(moduleFunction).call(arguments);
   }

   @CompilerDirectives.TruffleBoundary
   @Override
   public Object moduleEvaluation(JSRealm realm, JSModuleRecord moduleRecord) {
      JSModuleRecord module = moduleRecord;
      Deque<JSModuleRecord> stack = new ArrayDeque<>(4);
      if (realm.getContext().isOptionTopLevelAwait()) {
         assert moduleRecord.getStatus() == JSModuleRecord.Status.Linked
            || moduleRecord.getStatus() == JSModuleRecord.Status.EvaluatingAsync
            || moduleRecord.getStatus() == JSModuleRecord.Status.Evaluated;

         if (moduleRecord.getStatus() == JSModuleRecord.Status.EvaluatingAsync || moduleRecord.getStatus() == JSModuleRecord.Status.Evaluated) {
            module = moduleRecord.getCycleRoot();
         }

         if (module.getTopLevelCapability() != null) {
            return module.getTopLevelCapability().getPromise();
         } else {
            PromiseCapabilityRecord capability = NewPromiseCapabilityNode.createDefault(realm);
            module.setTopLevelCapability(capability);

            try {
               this.innerModuleEvaluation(realm, module, stack, 0);

               assert module.getStatus() == JSModuleRecord.Status.EvaluatingAsync || module.getStatus() == JSModuleRecord.Status.Evaluated;

               assert module.getEvaluationError() == null;

               if (!module.isAsyncEvaluation()) {
                  assert module.getStatus() == JSModuleRecord.Status.Evaluated;

                  JSFunction.call(JSArguments.create(Undefined.instance, capability.getResolve(), Undefined.instance));
               }

               assert stack.isEmpty();
            } catch (AbstractTruffleException var8) {
               handleModuleEvaluationError(module, stack, var8);
               throw var8;
            }

            return capability.getPromise();
         }
      } else {
         try {
            this.innerModuleEvaluation(realm, module, stack, 0);
         } catch (AbstractTruffleException var7) {
            handleModuleEvaluationError(moduleRecord, stack, var7);
            throw var7;
         }

         assert moduleRecord.getStatus() == JSModuleRecord.Status.EvaluatingAsync || moduleRecord.getStatus() == JSModuleRecord.Status.Evaluated;

         assert moduleRecord.getEvaluationError() == null;

         assert stack.isEmpty();

         Object result = moduleRecord.getExecutionResult();
         return result == null ? Undefined.instance : result;
      }
   }

   private static void handleModuleEvaluationError(JSModuleRecord module, Deque<JSModuleRecord> stack, AbstractTruffleException e) {
      for (JSModuleRecord m : stack) {
         assert m.getStatus() == JSModuleRecord.Status.Evaluating;

         m.setStatus(JSModuleRecord.Status.Evaluated);
         m.setEvaluationError(e);
      }

      assert module.getStatus() == JSModuleRecord.Status.Evaluated && module.getEvaluationError() == e;
   }

   @CompilerDirectives.TruffleBoundary
   private int innerModuleEvaluation(JSRealm realm, JSModuleRecord moduleRecord, Deque<JSModuleRecord> stack, int index0) {
      if (moduleRecord.getStatus() != JSModuleRecord.Status.EvaluatingAsync && moduleRecord.getStatus() != JSModuleRecord.Status.Evaluated) {
         if (moduleRecord.getStatus() == JSModuleRecord.Status.Evaluating) {
            return index0;
         } else {
            assert moduleRecord.getStatus() == JSModuleRecord.Status.Linked;

            moduleRecord.setStatus(JSModuleRecord.Status.Evaluating);
            moduleRecord.setDFSIndex(index0);
            moduleRecord.setDFSAncestorIndex(index0);
            moduleRecord.setPendingAsyncDependencies(0);
            moduleRecord.initAsyncParentModules();
            int index = index0 + 1;
            stack.push(moduleRecord);
            Module module = moduleRecord.getModule();

            for (Module.ModuleRequest requestedModule : module.getRequestedModules()) {
               JSModuleRecord requiredModule = hostResolveImportedModule(moduleRecord, requestedModule);
               index = this.innerModuleEvaluation(realm, requiredModule, stack, index);

               assert requiredModule.getStatus() == JSModuleRecord.Status.Evaluating
                  || requiredModule.getStatus() == JSModuleRecord.Status.EvaluatingAsync
                  || requiredModule.getStatus() == JSModuleRecord.Status.Evaluated : requiredModule.getStatus();

               assert requiredModule.getStatus() == JSModuleRecord.Status.Evaluating == stack.contains(requiredModule);

               if (requiredModule.getStatus() == JSModuleRecord.Status.Evaluating) {
                  moduleRecord.setDFSAncestorIndex(Math.min(moduleRecord.getDFSAncestorIndex(), requiredModule.getDFSAncestorIndex()));
               } else {
                  requiredModule = requiredModule.getCycleRoot();

                  assert requiredModule.getStatus() == JSModuleRecord.Status.EvaluatingAsync || requiredModule.getStatus() == JSModuleRecord.Status.Evaluated;

                  if (requiredModule.getEvaluationError() != null) {
                     throw JSRuntime.rethrow(moduleRecord.getEvaluationError());
                  }
               }

               if (requiredModule.isAsyncEvaluation()) {
                  moduleRecord.incPendingAsyncDependencies();
                  requiredModule.appendAsyncParentModules(moduleRecord);
               }
            }

            if (moduleRecord.getPendingAsyncDependencies() <= 0 && !moduleRecord.hasTLA()) {
               Object result = moduleExecution(realm, moduleRecord, null);
               moduleRecord.setExecutionResult(result);
            } else {
               assert !moduleRecord.isAsyncEvaluation();

               moduleRecord.setAsyncEvaluatingOrder(realm.nextAsyncEvaluationOrder());
               if (moduleRecord.getPendingAsyncDependencies() == 0) {
                  moduleAsyncExecution(realm, moduleRecord);
               }
            }

            assert occursExactlyOnce(moduleRecord, stack);

            assert moduleRecord.getDFSAncestorIndex() <= moduleRecord.getDFSIndex();

            JSModuleRecord requiredModulex;
            if (moduleRecord.getDFSAncestorIndex() == moduleRecord.getDFSIndex()) {
               do {
                  requiredModulex = stack.pop();
                  if (!requiredModulex.isAsyncEvaluation()) {
                     requiredModulex.setStatus(JSModuleRecord.Status.Evaluated);
                  } else {
                     requiredModulex.setStatus(JSModuleRecord.Status.EvaluatingAsync);
                  }

                  requiredModulex.setCycleRoot(moduleRecord);
               } while (!requiredModulex.equals(moduleRecord));
            }

            return index;
         }
      } else if (moduleRecord.getEvaluationError() == null) {
         return index0;
      } else {
         throw JSRuntime.rethrow(moduleRecord.getEvaluationError());
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static void moduleAsyncExecution(JSRealm realm, JSModuleRecord module) {
      assert module.getStatus() == JSModuleRecord.Status.Evaluating || module.getStatus() == JSModuleRecord.Status.EvaluatingAsync;

      assert module.hasTLA();

      PromiseCapabilityRecord capability = NewPromiseCapabilityNode.createDefault(realm);
      JSFunctionObject onFulfilled = createCallAsyncModuleFulfilled(realm, module);
      JSFunctionObject onRejected = createCallAsyncModuleRejected(realm, module);
      Object then = JSObject.get(capability.getPromise(), Strings.THEN);
      JSFunction.call(JSArguments.create(capability.getPromise(), then, onFulfilled, onRejected));
      moduleExecution(realm, module, capability);
   }

   @CompilerDirectives.TruffleBoundary
   private static JSFunctionObject createCallAsyncModuleFulfilled(JSRealm realm, JSModuleRecord module) {
      JSFunctionData functionData = realm.getContext()
         .getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.AsyncModuleExecutionFulfilled, c -> createAsyncModuleExecutionFulfilledImpl(c));
      JSFunctionObject function = JSFunction.create(realm, functionData);
      JSObjectUtil.putHiddenProperty(function, STORE_MODULE_KEY, module);
      return function;
   }

   private static JSFunctionData createAsyncModuleExecutionFulfilledImpl(JSContext context) {
      class AsyncModuleFulfilledRoot extends JavaScriptRootNode {
         @Node.Child
         private PropertyGetNode getModule = PropertyGetNode.createGetHidden(GraalJSEvaluator.STORE_MODULE_KEY, context);

         @Override
         public Object execute(VirtualFrame frame) {
            Object module = this.getModule.getValue(JSArguments.getFunctionObject(frame.getArguments()));
            return GraalJSEvaluator.asyncModuleExecutionFulfilled(this.getRealm(), (JSModuleRecord)module);
         }
      }

      return JSFunctionData.createCallOnly(context, new AsyncModuleFulfilledRoot().getCallTarget(), 1, Strings.EMPTY_STRING);
   }

   @CompilerDirectives.TruffleBoundary
   private static JSFunctionObject createCallAsyncModuleRejected(JSRealm realm, JSModuleRecord module) {
      JSFunctionData functionData = realm.getContext()
         .getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.AsyncModuleExecutionRejected, c -> createAsyncModuleExecutionRejectedImpl(c));
      JSFunctionObject function = JSFunction.create(realm, functionData);
      JSObjectUtil.putHiddenProperty(function, STORE_MODULE_KEY, module);
      return function;
   }

   private static JSFunctionData createAsyncModuleExecutionRejectedImpl(JSContext context) {
      class AsyncModuleExecutionRejectedRoot extends JavaScriptRootNode {
         @Node.Child
         private PropertyGetNode getModule = PropertyGetNode.createGetHidden(GraalJSEvaluator.STORE_MODULE_KEY, context);
         @Node.Child
         private JavaScriptNode errorArgument = AccessIndexedArgumentNode.create(0);

         @Override
         public Object execute(VirtualFrame frame) {
            JSModuleRecord module = (JSModuleRecord)this.getModule.getValue(JSArguments.getFunctionObject(frame.getArguments()));
            Object error = this.errorArgument.execute(frame);
            return GraalJSEvaluator.asyncModuleExecutionRejected(this.getRealm(), module, error);
         }
      }

      return JSFunctionData.createCallOnly(context, new AsyncModuleExecutionRejectedRoot().getCallTarget(), 1, Strings.EMPTY_STRING);
   }

   private static void gatherAvailableAncestors(JSModuleRecord module, Set<JSModuleRecord> execList) {
      for (JSModuleRecord m : module.getAsyncParentModules()) {
         if (!execList.contains(m) && m.getCycleRoot().getEvaluationError() == null) {
            assert m.getStatus() == JSModuleRecord.Status.EvaluatingAsync;

            assert m.getEvaluationError() == null;

            assert m.isAsyncEvaluation();

            assert m.getPendingAsyncDependencies() > 0;

            m.decPendingAsyncDependencies();
            if (m.getPendingAsyncDependencies() == 0) {
               execList.add(m);
               if (!m.hasTLA()) {
                  gatherAvailableAncestors(m, execList);
               }
            }
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static Object asyncModuleExecutionFulfilled(JSRealm realm, JSModuleRecord module) {
      if (module.getStatus() == JSModuleRecord.Status.Evaluated) {
         assert module.getEvaluationError() != null;

         return Undefined.instance;
      } else {
         assert module.getStatus() == JSModuleRecord.Status.EvaluatingAsync;

         assert module.isAsyncEvaluation();

         assert module.getEvaluationError() == null;

         module.setStatus(JSModuleRecord.Status.Evaluated);
         if (module.getTopLevelCapability() != null) {
            assert module.getCycleRoot() == module;

            JSFunction.call(JSArguments.create(Undefined.instance, module.getTopLevelCapability().getResolve(), Undefined.instance));
         }

         Set<JSModuleRecord> execList = new TreeSet<>(new Comparator<JSModuleRecord>() {
            public int compare(JSModuleRecord o1, JSModuleRecord o2) {
               return Long.compare(o1.getAsyncEvaluatingOrder(), o2.getAsyncEvaluatingOrder());
            }
         });
         gatherAvailableAncestors(module, execList);

         for (JSModuleRecord m : execList) {
            if (m.getStatus() == JSModuleRecord.Status.Evaluated) {
               assert m.getEvaluationError() != null;
            } else if (m.hasTLA()) {
               moduleAsyncExecution(realm, m);
            } else {
               try {
                  moduleExecution(realm, m, null);
                  m.setStatus(JSModuleRecord.Status.Evaluated);
                  if (m.getTopLevelCapability() != null) {
                     assert m.getCycleRoot() == m;

                     JSFunction.call(JSArguments.create(Undefined.instance, m.getTopLevelCapability().getResolve(), Undefined.instance));
                  }
               } catch (AbstractTruffleException var7) {
                  Object error = var7 instanceof GraalJSException ? ((GraalJSException)var7).getErrorObject() : var7;
                  asyncModuleExecutionRejected(realm, m, error);
               }
            }
         }

         return Undefined.instance;
      }
   }

   @CompilerDirectives.TruffleBoundary
   private static Object asyncModuleExecutionRejected(JSRealm realm, JSModuleRecord module, Object error) {
      assert error != null : "Cannot reject a module creation with null error";

      if (module.getStatus() == JSModuleRecord.Status.Evaluated) {
         assert module.getEvaluationError() != null;

         return Undefined.instance;
      } else {
         assert module.getStatus() == JSModuleRecord.Status.EvaluatingAsync;

         assert module.isAsyncEvaluation();

         assert module.getEvaluationError() == null;

         module.setEvaluationError(JSRuntime.getException(error));
         module.setStatus(JSModuleRecord.Status.Evaluated);

         for (JSModuleRecord m : module.getAsyncParentModules()) {
            asyncModuleExecutionRejected(realm, m, error);
         }

         if (module.getTopLevelCapability() != null) {
            assert module.getCycleRoot() == module;

            JSFunction.call((JSFunctionObject)module.getTopLevelCapability().getReject(), Undefined.instance, new Object[]{error});
         }

         return Undefined.instance;
      }
   }

   private static Object moduleExecution(JSRealm realm, JSModuleRecord moduleRecord, PromiseCapabilityRecord capability) {
      JSFunctionObject moduleFunction = JSFunction.create(realm, moduleRecord.getFunctionData());
      if (!moduleRecord.hasTLA()) {
         assert capability == null;

         return JSFunction.call(JSArguments.create(Undefined.instance, moduleFunction, moduleRecord));
      } else {
         assert capability != null;

         return JSFunction.call(JSArguments.create(Undefined.instance, moduleFunction, moduleRecord, capability));
      }
   }

   private static boolean occursExactlyOnce(JSModuleRecord moduleRecord, Collection<JSModuleRecord> stack) {
      return stack.stream().filter(moduleRecord::equals).count() == 1L;
   }

   @Override
   public ScriptNode parseScript(JSContext context, Source source, ByteBuffer binary) {
      return ScriptNode.fromFunctionRoot(context, (FunctionRootNode)new BinarySnapshotProvider(binary).apply(NodeFactory.getInstance(context), context, source));
   }

   @Override
   public ScriptNode parseScript(JSContext context, Source source, SnapshotProvider snapshotProvider) {
      return ScriptNode.fromFunctionRoot(context, (FunctionRootNode)snapshotProvider.apply(NodeFactory.getInstance(context), context, source));
   }

   @Override
   public JavaScriptNode parseInlineScript(JSContext context, Source source, MaterializedFrame lexicalContextFrame, boolean isStrict, Node locationNode) {
      Environment env;
      try {
         Object scope = NodeLibrary.getUncached().getScope(locationNode, lexicalContextFrame, true);
         env = new DebugEnvironment(null, NodeFactory.getInstance(context), context, scope);
      } catch (UnsupportedMessageException var9) {
         Object scopex = null;
         env = null;
      }

      ScriptNode script = JavaScriptTranslator.translateInlineScript(NodeFactory.getInstance(context), context, env, source, isStrict);
      return createInlineScriptCallNode(context, script.getFunctionData(), script.getCallTarget(), locationNode);
   }

   private static JavaScriptNode createInlineScriptCallNode(JSContext context, JSFunctionData functionData, RootCallTarget callTarget, Node locationNode) {
      return new JavaScriptNode() {
         @Node.Child
         private DirectCallNode callNode = DirectCallNode.create(callTarget);
         @Node.Child
         private PropertySetNode setScopeNode = PropertySetNode.createSetHidden(JSFunction.DEBUG_SCOPE_ID, context);
         @Node.Child
         private NodeLibrary nodeLibrary = NodeLibrary.getFactory().createDispatched(5);

         @Override
         public Object execute(VirtualFrame frame) {
            JSDynamicObject closure = JSFunction.create(this.getRealm(), functionData);

            try {
               Object scope = this.nodeLibrary.getScope(locationNode, frame, true);
               this.setScopeNode.setValue(closure, scope);
            } catch (UnsupportedMessageException var4) {
            }

            return this.callNode.call(JSArguments.createZeroArg(JSFrameUtil.getThisObj(frame), closure));
         }
      };
   }

   @Override
   public Expression parseExpression(JSContext context, String sourceString) {
      return GraalJSParserHelper.parseExpression(context, Source.newBuilder("js", sourceString, "<unknown>").build(), context.getParserOptions());
   }

   @Override
   public void checkFunctionSyntax(
      JSContext context, JSParserOptions parserOptions, String parameterList, String body, boolean generator, boolean async, String sourceName
   ) {
      try {
         GraalJSParserHelper.checkFunctionSyntax(context, parserOptions, parameterList, body, generator, async, sourceName);
      } catch (ParserException var9) {
         this.parseFunction(context, parameterList, body, false, false, sourceName);
      }
   }

   private final class ModuleScriptRoot extends JavaScriptRootNode {
      private final JSContext context;
      private final JSModuleData parsedModule;
      private final Source source;
      @Node.Child
      private PerformPromiseThenNode performPromiseThenNode;

      private ModuleScriptRoot(JSContext context, JSModuleData parsedModule, Source source) {
         super(context.getLanguage(), JSBuiltin.createSourceSection(), null);
         this.context = context;
         this.parsedModule = parsedModule;
         this.source = source;
         this.performPromiseThenNode = PerformPromiseThenNode.create(context);
      }

      @Override
      public Object execute(VirtualFrame frame) {
         JSRealm realm = JSFunction.getRealm(JSFrameUtil.getFunctionObject(frame));
         return this.evalModule(realm);
      }

      @CompilerDirectives.TruffleBoundary
      private Object evalModule(JSRealm realm) {
         JSModuleRecord moduleRecord = realm.getModuleLoader().loadModule(this.source, this.parsedModule);
         GraalJSEvaluator.this.moduleLinking(realm, moduleRecord);
         Object promise = GraalJSEvaluator.this.moduleEvaluation(realm, moduleRecord);
         boolean isAsync = this.context.isOptionTopLevelAwait() && moduleRecord.isAsyncEvaluation();
         if (isAsync) {
            assert JSPromise.isJSPromise(promise);

            JSFunctionObject onRejected = GraalJSEvaluator.createTopLevelAwaitReject(this.context, realm);
            JSFunctionObject onAccepted = GraalJSEvaluator.createTopLevelAwaitResolve(this.context, realm);
            this.performPromiseThenNode.execute((JSDynamicObject)promise, onAccepted, onRejected, null);
         }

         if (this.context.getContextOptions().isEsmEvalReturnsExports()) {
            JSDynamicObject moduleNamespace = GraalJSEvaluator.this.getModuleNamespace(moduleRecord);

            assert moduleNamespace != null;

            return moduleNamespace;
         } else {
            return isAsync ? promise : moduleRecord.getExecutionResultOrThrow();
         }
      }

      JSModuleData getModuleData() {
         return this.parsedModule;
      }
   }
}
