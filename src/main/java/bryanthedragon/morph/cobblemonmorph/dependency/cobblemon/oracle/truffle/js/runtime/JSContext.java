package com.oracle.truffle.js.runtime;

import com.oracle.js.parser.ir.Module;
import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.RootCallTarget;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.AllocationReporter;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.access.GetPrototypeNode;
import com.oracle.truffle.js.nodes.cast.JSToObjectNode;
import com.oracle.truffle.js.nodes.promise.BuiltinPromiseRejectionTracker;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.array.TypedArrayFactory;
import com.oracle.truffle.js.runtime.builtins.Builtin;
import com.oracle.truffle.js.runtime.builtins.JSAdapter;
import com.oracle.truffle.js.runtime.builtins.JSArgumentsArray;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSBigInt;
import com.oracle.truffle.js.runtime.builtins.JSBoolean;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.builtins.JSDataView;
import com.oracle.truffle.js.runtime.builtins.JSDate;
import com.oracle.truffle.js.runtime.builtins.JSDictionary;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.builtins.JSFinalizationRegistry;
import com.oracle.truffle.js.runtime.builtins.JSFinalizationRegistryObject;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionFactory;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSGlobal;
import com.oracle.truffle.js.runtime.builtins.JSMap;
import com.oracle.truffle.js.runtime.builtins.JSModuleNamespace;
import com.oracle.truffle.js.runtime.builtins.JSNonProxy;
import com.oracle.truffle.js.runtime.builtins.JSNumber;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.builtins.JSRegExp;
import com.oracle.truffle.js.runtime.builtins.JSSet;
import com.oracle.truffle.js.runtime.builtins.JSSharedArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.builtins.JSSymbol;
import com.oracle.truffle.js.runtime.builtins.JSUncheckedProxyHandler;
import com.oracle.truffle.js.runtime.builtins.JSWeakMap;
import com.oracle.truffle.js.runtime.builtins.JSWeakRef;
import com.oracle.truffle.js.runtime.builtins.JSWeakSet;
import com.oracle.truffle.js.runtime.builtins.PrototypeSupplier;
import com.oracle.truffle.js.runtime.builtins.intl.JSCollator;
import com.oracle.truffle.js.runtime.builtins.intl.JSDateTimeFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSDisplayNames;
import com.oracle.truffle.js.runtime.builtins.intl.JSListFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSLocale;
import com.oracle.truffle.js.runtime.builtins.intl.JSNumberFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSPluralRules;
import com.oracle.truffle.js.runtime.builtins.intl.JSRelativeTimeFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSSegmenter;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalCalendar;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalDuration;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalInstant;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDate;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainDateTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainMonthDay;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainTime;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalPlainYearMonth;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalTimeZone;
import com.oracle.truffle.js.runtime.builtins.temporal.JSTemporalZonedDateTime;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyGlobal;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyInstance;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyMemory;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyModule;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyTable;
import com.oracle.truffle.js.runtime.java.JavaImporter;
import com.oracle.truffle.js.runtime.java.JavaPackage;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSModuleRecord;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSPrototypeData;
import com.oracle.truffle.js.runtime.objects.JSShape;
import com.oracle.truffle.js.runtime.objects.JSShapeData;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.ScriptOrModule;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.CompilableBiFunction;
import com.oracle.truffle.js.runtime.util.DebugJSAgent;
import com.oracle.truffle.js.runtime.util.TRegexUtil;
import com.oracle.truffle.js.runtime.util.TimeProfiler;
import java.lang.invoke.MethodHandles;
import java.lang.invoke.VarHandle;
import java.lang.invoke.MethodHandles.Lookup;
import java.nio.charset.Charset;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.WeakHashMap;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.function.Function;

public class JSContext {
   private static final VarHandle FUNCTION_DATA_ARRAY_VAR_HANDLE = MethodHandles.arrayElementVarHandle(JSFunctionData[].class);
   private final Evaluator evaluator;
   private final JavaScriptLanguage language;
   private TruffleLanguage.Env initialEnvironment;
   private final Shape emptyShape;
   private final Shape emptyShapePrototypeInObject;
   private final Shape promiseShapePrototypeInObject;
   private final Shape globalScopeShape;
   private Object embedderData;
   private final Assumption noSuchPropertyUnusedAssumption;
   private final Assumption noSuchMethodUnusedAssumption;
   private final Assumption arrayPrototypeNoElementsAssumption;
   private final Assumption fastArrayAssumption;
   private final Assumption fastArgumentsObjectAssumption;
   private final Assumption typedArrayNotDetachedAssumption;
   private final Assumption regExpStaticResultUnusedAssumption;
   private final Assumption globalObjectPristineAssumption;
   private volatile Map<TruffleString, Symbol> symbolRegistry;
   private int operatorCounter = 3;
   private final Object nodeFactory;
   private final TimeProfiler timeProfiler;
   private final JSObjectFactory.BoundProto moduleNamespaceFactory;
   @CompilerDirectives.CompilationFinal
   private Object tRegexEmptyResult;
   private final String regexOptions;
   private final String regexValidateOptions;
   private final Shape regExpGroupsEmptyShape;
   private PrepareStackTraceCallback prepareStackTraceCallback;
   private final Assumption prepareStackTraceCallbackNotUsedAssumption;
   private PromiseRejectionTracker promiseRejectionTracker;
   private final Assumption promiseRejectionTrackerNotUsedAssumption;
   private PromiseHook promiseHook;
   private final Assumption promiseHookNotUsedAssumption;
   private ImportMetaInitializer importMetaInitializer;
   private final Assumption importMetaInitializerNotUsedAssumption;
   private ImportModuleDynamicallyCallback importModuleDynamicallyCallback;
   private final Assumption importModuleDynamicallyCallbackNotUsedAssumption;
   private final CallTarget emptyFunctionCallTarget;
   public final JSFunctionData symbolSpeciesThisGetterFunctionData;
   public final JSFunctionData symbolIteratorThisGetterFunctionData;
   private volatile CallTarget notConstructibleCallTargetCache;
   private volatile CallTarget generatorNotConstructibleCallTargetCache;
   private static final VarHandle notConstructibleCallTargetVarHandle;
   private static final VarHandle generatorNotConstructibleCallTargetVarHandle;
   private Object symbolUsageMarker = new Object();
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final JSFunctionData[] builtinFunctionData;
   final JSFunctionData throwerFunctionData;
   final JSFunctionData protoGetterFunctionData;
   final JSFunctionData protoSetterFunctionData;
   private Map<Shape, JSShapeData> shapeDataMap;
   private final Assumption singleRealmAssumption;
   private final boolean isMultiContext;
   private final AtomicInteger realmInit = new AtomicInteger();
   private static final int REALM_UNINITIALIZED = 0;
   private static final int REALM_INITIALIZING = 1;
   private static final int REALM_INITIALIZED = 2;
   @CompilerDirectives.CompilationFinal
   private AllocationReporter allocationReporter;
   private final JSContextOptions contextOptions;
   private final Map<Builtin, JSFunctionData> builtinFunctionDataMap = new ConcurrentHashMap<>();
   private final Map<TruffleString, JSFunctionData> namedEmptyFunctionsDataMap = new ConcurrentHashMap<>();
   private final JSPrototypeData nullPrototypeData = new JSPrototypeData();
   private final JSPrototypeData inObjectPrototypeData = new JSPrototypeData();
   private final JSFunctionFactory functionFactory;
   private final JSFunctionFactory constructorFactory;
   private final JSFunctionFactory strictFunctionFactory;
   private final JSFunctionFactory strictConstructorFactory;
   private final JSFunctionFactory generatorFunctionFactory;
   private final JSFunctionFactory asyncFunctionFactory;
   private final JSFunctionFactory asyncGeneratorFunctionFactory;
   private final JSFunctionFactory boundFunctionFactory;
   static final PrototypeSupplier functionPrototypeSupplier = JSRealm::getFunctionPrototype;
   static final PrototypeSupplier asyncFunctionPrototypeSupplier = JSRealm::getAsyncFunctionPrototype;
   static final PrototypeSupplier generatorFunctionPrototypeSupplier = JSRealm::getGeneratorFunctionPrototype;
   static final PrototypeSupplier asyncGeneratorFunctionPrototypeSupplier = JSRealm::getAsyncGeneratorFunctionPrototype;
   private final JSObjectFactory ordinaryObjectFactory;
   private final JSObjectFactory arrayFactory;
   private final JSObjectFactory lazyRegexArrayFactory;
   private final JSObjectFactory lazyRegexIndicesArrayFactory;
   private final JSObjectFactory booleanFactory;
   private final JSObjectFactory numberFactory;
   private final JSObjectFactory bigIntFactory;
   private final JSObjectFactory stringFactory;
   private final JSObjectFactory regExpFactory;
   private final JSObjectFactory dateFactory;
   private final JSObjectFactory nonStrictArgumentsFactory;
   private final JSObjectFactory strictArgumentsFactory;
   private final JSObjectFactory callSiteFactory;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final JSObjectFactory[] errorObjectFactories;
   private final JSObjectFactory symbolFactory;
   private final JSObjectFactory mapFactory;
   private final JSObjectFactory setFactory;
   private final JSObjectFactory weakRefFactory;
   private final JSObjectFactory weakMapFactory;
   private final JSObjectFactory weakSetFactory;
   private final JSObjectFactory proxyFactory;
   private final JSObjectFactory uncheckedProxyHandlerFactory;
   private final JSObjectFactory promiseFactory;
   private final JSObjectFactory dataViewFactory;
   private final JSObjectFactory arrayBufferFactory;
   private final JSObjectFactory directArrayBufferFactory;
   private final JSObjectFactory sharedArrayBufferFactory;
   private final JSObjectFactory interopArrayBufferFactory;
   private final JSObjectFactory finalizationRegistryFactory;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final JSObjectFactory[] typedArrayFactories;
   private final JSObjectFactory enumerateIteratorFactory;
   private final JSObjectFactory forInIteratorFactory;
   private final JSObjectFactory generatorObjectFactory;
   private final JSObjectFactory asyncGeneratorObjectFactory;
   private final JSObjectFactory asyncFromSyncIteratorFactory;
   private final JSObjectFactory collatorFactory;
   private final JSObjectFactory numberFormatFactory;
   private final JSObjectFactory pluralRulesFactory;
   private final JSObjectFactory dateTimeFormatFactory;
   private final JSObjectFactory listFormatFactory;
   private final JSObjectFactory relativeTimeFormatFactory;
   private final JSObjectFactory segmenterFactory;
   private final JSObjectFactory segmentsFactory;
   private final JSObjectFactory segmentIteratorFactory;
   private final JSObjectFactory displayNamesFactory;
   private final JSObjectFactory localeFactory;
   private final JSObjectFactory javaImporterFactory;
   private final JSObjectFactory javaPackageFactory;
   private final JSObjectFactory jsAdapterFactory;
   private final JSObjectFactory dictionaryObjectFactory;
   private final JSObjectFactory temporalPlainTimeFactory;
   private final JSObjectFactory temporalPlainDateFactory;
   private final JSObjectFactory temporalPlainDateTimeFactory;
   private final JSObjectFactory temporalDurationFactory;
   private final JSObjectFactory temporalCalendarFactory;
   private final JSObjectFactory temporalPlainYearMonthFactory;
   private final JSObjectFactory temporalPlainMonthDayFactory;
   private final JSObjectFactory temporalInstantFactory;
   private final JSObjectFactory temporalTimeZoneFactory;
   private final JSObjectFactory temporalZonedDateTimeFactory;
   private final JSObjectFactory globalObjectFactory;
   private final JSObjectFactory webAssemblyModuleFactory;
   private final JSObjectFactory webAssemblyInstanceFactory;
   private final JSObjectFactory webAssemblyMemoryFactory;
   private final JSObjectFactory webAssemblyTableFactory;
   private final JSObjectFactory webAssemblyGlobalFactory;
   private final int factoryCount;
   @CompilerDirectives.CompilationFinal
   private Locale locale;
   @CompilerDirectives.CompilationFinal
   private Charset charset;
   private final Set<TruffleString> supportedImportAssertions;
   private static final TruffleString TYPE_IMPORT_ASSERTION = Strings.constant("type");
   private final JSContext.SharedRootNode sharedRootNode;
   private static final String REGEX_OPTION_REGRESSION_TEST_MODE = "RegressionTestMode";
   private static final String REGEX_OPTION_DUMP_AUTOMATA = "DumpAutomata";
   private static final String REGEX_OPTION_STEP_EXECUTION = "StepExecution";
   private static final String REGEX_OPTION_ALWAYS_EAGER = "AlwaysEager";
   private static final String REGEX_OPTION_VALIDATE = "Validate=true";

   public void resetSymbolUsageMarker() {
      CompilerAsserts.neverPartOfCompilation();
      this.symbolUsageMarker = new Object();
   }

   public Object getSymbolUsageMarker() {
      return this.symbolUsageMarker;
   }

   protected JSContext(Evaluator evaluator, JSContextOptions contextOptions, JavaScriptLanguage lang, TruffleLanguage.Env env) {
      this.contextOptions = contextOptions;
      if (env != null) {
         this.setAllocationReporter(env);
         this.contextOptions.setOptionValues(env.getOptions());
      }

      this.language = lang;
      this.initialEnvironment = env;
      this.sharedRootNode = new JSContext.SharedRootNode();
      this.emptyShape = this.createEmptyShape();
      this.emptyShapePrototypeInObject = this.createEmptyShapePrototypeInObject();
      this.promiseShapePrototypeInObject = this.createPromiseShapePrototypeInObject();
      this.globalScopeShape = this.createGlobalScopeShape();
      this.noSuchPropertyUnusedAssumption = Truffle.getRuntime().createAssumption("noSuchPropertyUnusedAssumption");
      this.noSuchMethodUnusedAssumption = Truffle.getRuntime().createAssumption("noSuchMethodUnusedAssumption");
      this.arrayPrototypeNoElementsAssumption = Truffle.getRuntime().createAssumption("arrayPrototypeNoElementsAssumption");
      this.typedArrayNotDetachedAssumption = Truffle.getRuntime().createAssumption("typedArrayNotDetachedAssumption");
      this.fastArrayAssumption = Truffle.getRuntime().createAssumption("fastArrayAssumption");
      this.fastArgumentsObjectAssumption = Truffle.getRuntime().createAssumption("fastArgumentsObjectAssumption");
      this.regExpStaticResultUnusedAssumption = Truffle.getRuntime().createAssumption("regExpStaticResultUnusedAssumption");
      this.globalObjectPristineAssumption = Truffle.getRuntime().createAssumption("globalObjectPristineAssumption");
      this.evaluator = evaluator;
      this.nodeFactory = evaluator.getDefaultNodeFactory();
      this.moduleNamespaceFactory = JSObjectFactory.createBound(this, Null.instance, JSModuleNamespace.makeInitialShape(this));
      this.prepareStackTraceCallbackNotUsedAssumption = Truffle.getRuntime().createAssumption("prepareStackTraceCallbackNotUsedAssumption");
      this.promiseHookNotUsedAssumption = Truffle.getRuntime().createAssumption("promiseHookNotUsedAssumption");
      this.promiseRejectionTrackerNotUsedAssumption = Truffle.getRuntime().createAssumption("promiseRejectionTrackerNotUsedAssumption");
      this.importMetaInitializerNotUsedAssumption = Truffle.getRuntime().createAssumption("importMetaInitializerNotUsedAssumption");
      this.importModuleDynamicallyCallbackNotUsedAssumption = Truffle.getRuntime().createAssumption("importModuleDynamicallyCallbackNotUsedAssumption");
      this.emptyFunctionCallTarget = createEmptyFunctionCallTarget(lang);
      this.symbolSpeciesThisGetterFunctionData = JSFunctionData.createCallOnly(this, createReadFrameThisCallTarget(lang), 0, JSNonProxy.GET_SYMBOL_SPECIES_NAME);
      this.symbolIteratorThisGetterFunctionData = JSFunctionData.createCallOnly(this, createReadFrameThisCallTarget(lang), 0, JSRealm.SYMBOL_ITERATOR_NAME);
      this.builtinFunctionData = new JSFunctionData[JSContext.BuiltinFunctionKey.values().length];
      this.timeProfiler = contextOptions.isProfileTime() ? new TimeProfiler() : null;
      this.singleRealmAssumption = Truffle.getRuntime().createAssumption("single realm");
      this.throwerFunctionData = this.throwTypeErrorFunction();
      boolean annexB = this.isOptionAnnexB();
      this.protoGetterFunctionData = annexB ? this.protoGetterFunction() : null;
      this.protoSetterFunctionData = annexB ? this.protoSetterFunction() : null;
      this.isMultiContext = lang.isMultiContext();
      PrototypeSupplier objectPrototypeSupplier = JSOrdinary.INSTANCE;
      CompilableBiFunction<JSContext, JSDynamicObject, Shape> ordinaryObjectShapeSupplier = JSOrdinary.SHAPE_SUPPLIER;
      JSObjectFactory.IntrinsicBuilder builder = new JSObjectFactory.IntrinsicBuilder(this);
      this.functionFactory = builder.function(functionPrototypeSupplier, false, false, false, false, false);
      this.constructorFactory = builder.function(functionPrototypeSupplier, false, true, false, false, false);
      this.strictFunctionFactory = builder.function(functionPrototypeSupplier, true, false, false, false, false);
      this.strictConstructorFactory = builder.function(functionPrototypeSupplier, true, true, false, false, false);
      this.asyncFunctionFactory = builder.function(asyncFunctionPrototypeSupplier, true, false, false, false, true);
      this.generatorFunctionFactory = builder.function(generatorFunctionPrototypeSupplier, true, false, true, false, false);
      this.asyncGeneratorFunctionFactory = builder.function(asyncGeneratorFunctionPrototypeSupplier, true, false, true, false, true);
      this.boundFunctionFactory = builder.function(functionPrototypeSupplier, true, false, false, true, false);
      this.ordinaryObjectFactory = builder.create(JSOrdinary.INSTANCE);
      this.arrayFactory = builder.create(JSArray.INSTANCE);
      this.lazyRegexArrayFactory = builder.create(JSArray.INSTANCE);
      this.lazyRegexIndicesArrayFactory = builder.create(JSArray.INSTANCE);
      this.booleanFactory = builder.create(JSBoolean.INSTANCE);
      this.numberFactory = builder.create(JSNumber.INSTANCE);
      this.bigIntFactory = builder.create(JSBigInt.INSTANCE);
      this.stringFactory = builder.create(JSString.INSTANCE);
      this.regExpFactory = builder.create(JSRegExp.INSTANCE);
      this.dateFactory = builder.create(JSDate.INSTANCE);
      this.symbolFactory = builder.create(JSSymbol.INSTANCE);
      this.mapFactory = builder.create(JSMap.INSTANCE);
      this.setFactory = builder.create(JSSet.INSTANCE);
      this.weakRefFactory = builder.create(JSWeakRef.INSTANCE);
      this.weakMapFactory = builder.create(JSWeakMap.INSTANCE);
      this.weakSetFactory = builder.create(JSWeakSet.INSTANCE);
      this.proxyFactory = builder.create(JSProxy.INSTANCE);
      this.uncheckedProxyHandlerFactory = builder.create(JSUncheckedProxyHandler.INSTANCE);
      this.promiseFactory = builder.create(JSPromise.INSTANCE);
      this.dataViewFactory = builder.create(JSDataView.INSTANCE);
      this.arrayBufferFactory = builder.create(JSArrayBuffer.HEAP_INSTANCE);
      this.directArrayBufferFactory = builder.create(JSArrayBuffer.DIRECT_INSTANCE);
      this.sharedArrayBufferFactory = this.isOptionSharedArrayBuffer() ? builder.create(JSSharedArrayBuffer.INSTANCE) : null;
      this.interopArrayBufferFactory = builder.create(JSArrayBuffer.INTEROP_INSTANCE);
      this.finalizationRegistryFactory = builder.create(JSFinalizationRegistry.INSTANCE);
      this.typedArrayFactories = new JSObjectFactory[TypedArray.factories(this).length];

      for (TypedArrayFactory factory : TypedArray.factories(this)) {
         this.typedArrayFactories[factory.getFactoryIndex()] = builder.create(factory, (c, p) -> JSArrayBufferView.makeInitialArrayBufferViewShape(c, p));
      }

      this.errorObjectFactories = new JSObjectFactory[JSErrorType.errorTypes().length];

      for (JSErrorType type : JSErrorType.errorTypes()) {
         this.errorObjectFactories[type.ordinal()] = builder.create(type, JSError.INSTANCE::makeInitialShape);
      }

      this.callSiteFactory = builder.create(JSRealm::getCallSitePrototype, JSError::makeInitialCallSiteShape);
      this.nonStrictArgumentsFactory = builder.create(objectPrototypeSupplier, JSArgumentsArray.INSTANCE);
      this.strictArgumentsFactory = builder.create(objectPrototypeSupplier, JSArgumentsArray.INSTANCE);
      this.enumerateIteratorFactory = builder.create(JSRealm::getEnumerateIteratorPrototype, JSFunction::makeInitialEnumerateIteratorShape);
      this.forInIteratorFactory = builder.create(JSRealm::getForInIteratorPrototype, JSFunction::makeInitialForInIteratorShape);
      this.generatorObjectFactory = builder.create(JSRealm::getGeneratorObjectPrototype, ordinaryObjectShapeSupplier);
      this.asyncGeneratorObjectFactory = builder.create(JSRealm::getAsyncGeneratorObjectPrototype, ordinaryObjectShapeSupplier);
      this.asyncFromSyncIteratorFactory = builder.create(JSRealm::getAsyncFromSyncIteratorPrototype, ordinaryObjectShapeSupplier);
      this.collatorFactory = builder.create(JSCollator.INSTANCE);
      this.numberFormatFactory = builder.create(JSNumberFormat.INSTANCE);
      this.dateTimeFormatFactory = builder.create(JSDateTimeFormat.INSTANCE);
      this.pluralRulesFactory = builder.create(JSPluralRules.INSTANCE);
      this.listFormatFactory = builder.create(JSListFormat.INSTANCE);
      this.relativeTimeFormatFactory = builder.create(JSRelativeTimeFormat.INSTANCE);
      this.segmenterFactory = builder.create(JSSegmenter.INSTANCE);
      this.segmentsFactory = builder.create(JSRealm::getSegmentsPrototype, JSSegmenter::makeInitialSegmentsShape);
      this.segmentIteratorFactory = builder.create(JSRealm::getSegmentIteratorPrototype, JSSegmenter::makeInitialSegmentIteratorShape);
      this.displayNamesFactory = builder.create(JSDisplayNames.INSTANCE);
      this.localeFactory = builder.create(JSLocale.INSTANCE);
      this.javaPackageFactory = builder.create(objectPrototypeSupplier, JavaPackage.INSTANCE::makeInitialShape);
      boolean nashornCompat = this.isOptionNashornCompatibilityMode();
      this.jsAdapterFactory = nashornCompat ? builder.create(JSAdapter.INSTANCE) : null;
      this.javaImporterFactory = nashornCompat ? builder.create(JavaImporter.instance()) : null;
      this.temporalPlainTimeFactory = builder.create(JSTemporalPlainTime.INSTANCE);
      this.temporalPlainDateFactory = builder.create(JSTemporalPlainDate.INSTANCE);
      this.temporalPlainDateTimeFactory = builder.create(JSTemporalPlainDateTime.INSTANCE);
      this.temporalDurationFactory = builder.create(JSTemporalDuration.INSTANCE);
      this.temporalCalendarFactory = builder.create(JSTemporalCalendar.INSTANCE);
      this.temporalPlainYearMonthFactory = builder.create(JSTemporalPlainYearMonth.INSTANCE);
      this.temporalPlainMonthDayFactory = builder.create(JSTemporalPlainMonthDay.INSTANCE);
      this.temporalInstantFactory = builder.create(JSTemporalInstant.INSTANCE);
      this.temporalTimeZoneFactory = builder.create(JSTemporalTimeZone.INSTANCE);
      this.temporalZonedDateTimeFactory = builder.create(JSTemporalZonedDateTime.INSTANCE);
      this.dictionaryObjectFactory = builder.create(objectPrototypeSupplier, JSDictionary::makeDictionaryShape);
      this.globalObjectFactory = builder.create(objectPrototypeSupplier, JSGlobal::makeGlobalObjectShape);
      this.webAssemblyModuleFactory = builder.create(JSWebAssemblyModule.INSTANCE);
      this.webAssemblyInstanceFactory = builder.create(JSWebAssemblyInstance.INSTANCE);
      this.webAssemblyMemoryFactory = builder.create(JSWebAssemblyMemory.INSTANCE);
      this.webAssemblyTableFactory = builder.create(JSWebAssemblyTable.INSTANCE);
      this.webAssemblyGlobalFactory = builder.create(JSWebAssemblyGlobal.INSTANCE);
      this.factoryCount = builder.finish();
      this.regExpGroupsEmptyShape = JSRegExp.makeInitialGroupsObjectShape(this);
      this.regexOptions = createRegexOptions(contextOptions);
      this.regexValidateOptions = this.regexOptions.isEmpty() ? "Validate=true" : "Validate=true," + this.regexOptions;
      this.supportedImportAssertions = (Set<TruffleString>)(contextOptions.isImportAssertions() ? new HashSet<>() : Collections.emptySet());
      if (contextOptions.isImportAssertions()) {
         this.supportedImportAssertions.add(TYPE_IMPORT_ASSERTION);
      }

      if (contextOptions.getUnhandledRejectionsMode() != JSContextOptions.UnhandledRejectionsTrackingMode.NONE) {
         this.setPromiseRejectionTracker(new BuiltinPromiseRejectionTracker(this, contextOptions.getUnhandledRejectionsMode()));
      }
   }

   public final Evaluator getEvaluator() {
      return this.evaluator;
   }

   public Object getNodeFactory() {
      return this.nodeFactory;
   }

   public final JSParserOptions getParserOptions() {
      return this.contextOptions.getParserOptions();
   }

   public final Object getEmbedderData() {
      return this.embedderData;
   }

   public final void setEmbedderData(Object embedderData) {
      this.embedderData = embedderData;
   }

   public final Assumption getNoSuchPropertyUnusedAssumption() {
      return this.noSuchPropertyUnusedAssumption;
   }

   public final Assumption getNoSuchMethodUnusedAssumption() {
      return this.noSuchMethodUnusedAssumption;
   }

   public final Assumption getArrayPrototypeNoElementsAssumption() {
      return this.arrayPrototypeNoElementsAssumption;
   }

   public final Assumption getFastArrayAssumption() {
      return this.fastArrayAssumption;
   }

   public final Assumption getFastArgumentsObjectAssumption() {
      return this.fastArgumentsObjectAssumption;
   }

   public final Assumption getTypedArrayNotDetachedAssumption() {
      return this.typedArrayNotDetachedAssumption;
   }

   public final Assumption getRegExpStaticResultUnusedAssumption() {
      return this.regExpStaticResultUnusedAssumption;
   }

   public final Assumption getGlobalObjectPristineAssumption() {
      return this.globalObjectPristineAssumption;
   }

   public static JSContext createContext(Evaluator evaluator, JSContextOptions contextOptions, JavaScriptLanguage lang, TruffleLanguage.Env env) {
      return new JSContext(evaluator, contextOptions, lang, env);
   }

   public JSRealm createRealm(TruffleLanguage.Env env) {
      return this.createRealm(env, null);
   }

   protected JSRealm createRealm(TruffleLanguage.Env env, JSRealm parentRealm) {
      boolean isTop = parentRealm == null;
      this.realmInit.compareAndSet(0, 1);
      if (!isTop) {
         this.singleRealmAssumption.invalidate("creating another realm");
      }

      JSRealm newRealm = new JSRealm(this, env, parentRealm);
      newRealm.setupGlobals();
      if (isTop) {
         if (!this.contextOptions.isTest262Mode() && !this.contextOptions.isTestV8Mode()) {
            newRealm.setAgent(new MainJSAgent(this.getPromiseRejectionTracker()));
         } else {
            newRealm.setAgent(new DebugJSAgent(this.getPromiseRejectionTracker(), this.contextOptions.canAgentBlock()));
         }

         if (this.contextOptions.isV8RealmBuiltin()) {
            newRealm.initRealmList();
            newRealm.addToRealmList(newRealm);
         }
      }

      this.realmInit.set(2);
      return newRealm;
   }

   public final Shape createEmptyShape() {
      return this.makeEmptyShapeWithNullPrototype(JSOrdinary.INSTANCE);
   }

   private Shape createEmptyShapePrototypeInObject() {
      return this.makeEmptyShapeWithPrototypeInObject(JSOrdinary.INSTANCE);
   }

   private Shape createPromiseShapePrototypeInObject() {
      return this.makeEmptyShapeWithPrototypeInObject(JSPromise.INSTANCE);
   }

   public final Shape makeEmptyShapeWithNullPrototype(JSClass jsclass) {
      Shape protoChildTree = this.nullPrototypeData.getProtoChildTree(jsclass);
      return protoChildTree != null ? protoChildTree : this.nullPrototypeData.getOrAddProtoChildTree(jsclass, JSShape.makeEmptyRoot(jsclass, this));
   }

   public final Shape makeEmptyShapeWithPrototypeInObject(JSClass jsclass) {
      Shape protoChildTree = this.inObjectPrototypeData.getProtoChildTree(jsclass);
      return protoChildTree != null
         ? protoChildTree
         : this.inObjectPrototypeData.getOrAddProtoChildTree(jsclass, JSShape.makeEmptyRootWithInstanceProto(this, jsclass));
   }

   private Shape createGlobalScopeShape() {
      return JSShape.makeEmptyRoot(JSGlobal.INSTANCE, this);
   }

   public final Map<TruffleString, Symbol> getSymbolRegistry() {
      if (this.symbolRegistry == null) {
         this.createSymbolRegistry();
      }

      return this.symbolRegistry;
   }

   @CompilerDirectives.TruffleBoundary
   private synchronized void createSymbolRegistry() {
      if (this.symbolRegistry == null) {
         this.symbolRegistry = new HashMap<>();
      }
   }

   public int getOperatorCounter() {
      return this.operatorCounter;
   }

   public int incOperatorCounter() {
      return this.operatorCounter++;
   }

   public final void promiseEnqueueJob(JSRealm realm, JSFunctionObject job) {
      this.invalidatePromiseQueueNotUsedAssumption();
      realm.getAgent().enqueuePromiseJob(job);
   }

   public final void signalAsyncWaiterRecordUsage() {
      this.invalidatePromiseQueueNotUsedAssumption();
   }

   private void invalidatePromiseQueueNotUsedAssumption() {
      Assumption promiseJobsQueueEmptyAssumption = this.language.getPromiseJobsQueueEmptyAssumption();
      if (promiseJobsQueueEmptyAssumption.isValid()) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         promiseJobsQueueEmptyAssumption.invalidate();
      }
   }

   public final void processAllPendingPromiseJobs(JSRealm realm) {
      if (!this.language.getPromiseJobsQueueEmptyAssumption().isValid()) {
         realm.getAgent().processAllPromises(false);
      }
   }

   public boolean addWeakRefTargetToSet(Object target) {
      this.invalidatePromiseQueueNotUsedAssumption();
      return this.getJSAgent().addWeakRefTargetToSet(target);
   }

   public void registerFinalizationRegistry(JSFinalizationRegistryObject finalizationRegistry) {
      this.invalidatePromiseQueueNotUsedAssumption();
      this.getJSAgent().registerFinalizationRegistry(finalizationRegistry);
   }

   public TimeProfiler getTimeProfiler() {
      return this.timeProfiler;
   }

   private JSRealm getRealm() {
      assert this.realmInit.get() == 2 : "getRealm() while initializing Realm";

      JSRealm currentRealm = JSRealm.get(null);

      assert currentRealm != null;

      return currentRealm;
   }

   public final Shape getEmptyShapeNullPrototype() {
      return this.emptyShape;
   }

   public final Shape getEmptyShapePrototypeInObject() {
      return this.emptyShapePrototypeInObject;
   }

   public final Shape getPromiseShapePrototypeInObject() {
      return this.promiseShapePrototypeInObject;
   }

   public final Shape getGlobalScopeShape() {
      return this.globalScopeShape;
   }

   public final JSObjectFactory getOrdinaryObjectFactory() {
      return this.ordinaryObjectFactory;
   }

   public final JSObjectFactory getArrayFactory() {
      return this.arrayFactory;
   }

   public final JSObjectFactory getLazyRegexArrayFactory() {
      return this.lazyRegexArrayFactory;
   }

   public final JSObjectFactory getLazyRegexIndicesArrayFactory() {
      return this.lazyRegexIndicesArrayFactory;
   }

   public final JSObjectFactory getStringFactory() {
      return this.stringFactory;
   }

   public final JSObjectFactory getBooleanFactory() {
      return this.booleanFactory;
   }

   public final JSObjectFactory getNumberFactory() {
      return this.numberFactory;
   }

   public final JSObjectFactory getBigIntFactory() {
      return this.bigIntFactory;
   }

   public final JSObjectFactory getSymbolFactory() {
      return this.symbolFactory;
   }

   public final JSObjectFactory getArrayBufferViewFactory(TypedArrayFactory factory) {
      return this.typedArrayFactories[factory.getFactoryIndex()];
   }

   public final JSObjectFactory getArrayBufferFactory() {
      return this.arrayBufferFactory;
   }

   public final JSObjectFactory getDirectArrayBufferFactory() {
      return this.directArrayBufferFactory;
   }

   public final JSObjectFactory getRegExpFactory() {
      return this.regExpFactory;
   }

   public final JSObjectFactory getDateFactory() {
      return this.dateFactory;
   }

   public final JSObjectFactory getEnumerateIteratorFactory() {
      return this.enumerateIteratorFactory;
   }

   public final JSObjectFactory getForInIteratorFactory() {
      return this.forInIteratorFactory;
   }

   public final JSObjectFactory getMapFactory() {
      return this.mapFactory;
   }

   public final JSObjectFactory getFinalizationRegistryFactory() {
      return this.finalizationRegistryFactory;
   }

   public final JSObjectFactory getWeakRefFactory() {
      return this.weakRefFactory;
   }

   public final JSObjectFactory getWeakMapFactory() {
      return this.weakMapFactory;
   }

   public final JSObjectFactory getSetFactory() {
      return this.setFactory;
   }

   public final JSObjectFactory getWeakSetFactory() {
      return this.weakSetFactory;
   }

   public final JSObjectFactory getDataViewFactory() {
      return this.dataViewFactory;
   }

   public final JSObjectFactory getProxyFactory() {
      return this.proxyFactory;
   }

   public final JSObjectFactory getUncheckedProxyHandlerFactory() {
      return this.uncheckedProxyHandlerFactory;
   }

   public final JSObjectFactory getSharedArrayBufferFactory() {
      assert this.isOptionSharedArrayBuffer();

      return this.sharedArrayBufferFactory;
   }

   public JSObjectFactory getInteropArrayBufferFactory() {
      return this.interopArrayBufferFactory;
   }

   public final JSObjectFactory getNonStrictArgumentsFactory() {
      return this.nonStrictArgumentsFactory;
   }

   public final JSObjectFactory getStrictArgumentsFactory() {
      return this.strictArgumentsFactory;
   }

   public final JSObjectFactory getCallSiteFactory() {
      return this.callSiteFactory;
   }

   public final JSObjectFactory getErrorFactory(JSErrorType type) {
      return this.errorObjectFactories[type.ordinal()];
   }

   public final JSObjectFactory getPromiseFactory() {
      return this.promiseFactory;
   }

   public final JSObjectFactory.BoundProto getModuleNamespaceFactory() {
      return this.moduleNamespaceFactory;
   }

   public final JSObjectFactory getGeneratorObjectFactory() {
      return this.generatorObjectFactory;
   }

   public final JSObjectFactory getAsyncGeneratorObjectFactory() {
      return this.asyncGeneratorObjectFactory;
   }

   public final JSObjectFactory getAsyncFromSyncIteratorFactory() {
      return this.asyncFromSyncIteratorFactory;
   }

   public final JSObjectFactory getCollatorFactory() {
      return this.collatorFactory;
   }

   public final JSObjectFactory getNumberFormatFactory() {
      return this.numberFormatFactory;
   }

   public final JSObjectFactory getPluralRulesFactory() {
      return this.pluralRulesFactory;
   }

   public final JSObjectFactory getListFormatFactory() {
      return this.listFormatFactory;
   }

   public final JSObjectFactory getRelativeTimeFormatFactory() {
      return this.relativeTimeFormatFactory;
   }

   public final JSObjectFactory getSegmenterFactory() {
      return this.segmenterFactory;
   }

   public final JSObjectFactory getSegmentsFactory() {
      return this.segmentsFactory;
   }

   public final JSObjectFactory getSegmentIteratorFactory() {
      return this.segmentIteratorFactory;
   }

   public final JSObjectFactory getDisplayNamesFactory() {
      return this.displayNamesFactory;
   }

   public final JSObjectFactory getLocaleFactory() {
      return this.localeFactory;
   }

   public final JSObjectFactory getDateTimeFormatFactory() {
      return this.dateTimeFormatFactory;
   }

   public final JSObjectFactory getJavaImporterFactory() {
      return this.javaImporterFactory;
   }

   public final JSObjectFactory getJSAdapterFactory() {
      return this.jsAdapterFactory;
   }

   public final JSObjectFactory getJavaPackageFactory() {
      return this.javaPackageFactory;
   }

   public final JSObjectFactory getTemporalPlainTimeFactory() {
      return this.temporalPlainTimeFactory;
   }

   public final JSObjectFactory getTemporalPlainDateFactory() {
      return this.temporalPlainDateFactory;
   }

   public final JSObjectFactory getTemporalPlainDateTimeFactory() {
      return this.temporalPlainDateTimeFactory;
   }

   public final JSObjectFactory getTemporalDurationFactory() {
      return this.temporalDurationFactory;
   }

   public final JSObjectFactory getTemporalCalendarFactory() {
      return this.temporalCalendarFactory;
   }

   public JSObjectFactory getTemporalPlainYearMonthFactory() {
      return this.temporalPlainYearMonthFactory;
   }

   public JSObjectFactory getTemporalPlainMonthDayFactory() {
      return this.temporalPlainMonthDayFactory;
   }

   public JSObjectFactory getTemporalInstantFactory() {
      return this.temporalInstantFactory;
   }

   public JSObjectFactory getTemporalZonedDateTimeFactory() {
      return this.temporalZonedDateTimeFactory;
   }

   public JSObjectFactory getTemporalTimeZoneFactory() {
      return this.temporalTimeZoneFactory;
   }

   public JSObjectFactory getDictionaryObjectFactory() {
      return this.dictionaryObjectFactory;
   }

   public JSObjectFactory getGlobalObjectFactory() {
      return this.globalObjectFactory;
   }

   public JSObjectFactory getWebAssemblyModuleFactory() {
      return this.webAssemblyModuleFactory;
   }

   public JSObjectFactory getWebAssemblyInstanceFactory() {
      return this.webAssemblyInstanceFactory;
   }

   public JSObjectFactory getWebAssemblyMemoryFactory() {
      return this.webAssemblyMemoryFactory;
   }

   public JSObjectFactory getWebAssemblyTableFactory() {
      return this.webAssemblyTableFactory;
   }

   public JSObjectFactory getWebAssemblyGlobalFactory() {
      return this.webAssemblyGlobalFactory;
   }

   private static String createRegexOptions(JSContextOptions contextOptions) {
      StringBuilder options = new StringBuilder();
      if (contextOptions.isRegexRegressionTestMode()) {
         options.append("RegressionTestMode").append("=true,");
      }

      if (contextOptions.isRegexDumpAutomata()) {
         options.append("DumpAutomata").append("=true,");
      }

      if (contextOptions.isRegexStepExecution()) {
         options.append("StepExecution").append("=true,");
      }

      if (contextOptions.isRegexAlwaysEager()) {
         options.append("AlwaysEager").append("=true,");
      }

      return options.toString();
   }

   public String getRegexOptions() {
      return this.regexOptions;
   }

   public String getRegexValidateOptions() {
      return this.regexValidateOptions;
   }

   public Object getTRegexEmptyResult() {
      if (this.tRegexEmptyResult == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.tRegexEmptyResult = TRegexUtil.InvokeExecMethodNode.getUncached()
            .execute(RegexCompilerInterface.compile("[]", "", this, JSRealm.get(null)), "", 0L);

         assert !TRegexUtil.TRegexResultAccessor.getUncached().isMatch(this.tRegexEmptyResult);
      }

      return this.tRegexEmptyResult;
   }

   public Shape getRegExpGroupsEmptyShape() {
      return this.regExpGroupsEmptyShape;
   }

   public void setSymbolRegistry(Map<TruffleString, Symbol> newSymbolRegistry) {
      this.symbolRegistry = newSymbolRegistry;
   }

   public Map<Shape, JSShapeData> getShapeDataMap() {
      assert Thread.holdsLock(this);

      Map<Shape, JSShapeData> map = this.shapeDataMap;
      if (map == null) {
         map = this.createShapeDataMap();
      }

      return map;
   }

   private Map<Shape, JSShapeData> createShapeDataMap() {
      CompilerAsserts.neverPartOfCompilation();
      Map<Shape, JSShapeData> map = new WeakHashMap<>();
      this.shapeDataMap = map;
      return map;
   }

   public JavaScriptLanguage getLanguage() {
      return this.language;
   }

   private TruffleLanguage.Env getInitialEnvironment() {
      return this.initialEnvironment;
   }

   public void clearInitialEnvironment() {
      this.initialEnvironment = null;
   }

   public CallTarget getEmptyFunctionCallTarget() {
      return this.emptyFunctionCallTarget;
   }

   public JSFunctionData getNamedEmptyFunctionData(TruffleString name) {
      return this.namedEmptyFunctionsDataMap.computeIfAbsent(name, k -> JSFunctionData.createCallOnly(this, this.emptyFunctionCallTarget, 0, name));
   }

   private static CallTarget createEmptyFunctionCallTarget(JavaScriptLanguage lang) {
      return (new JavaScriptRootNode(lang, null, null) {
         @Override
         public Object execute(VirtualFrame frame) {
            return Undefined.instance;
         }
      }).getCallTarget();
   }

   public JSFunctionData getSymbolIteratorThisGetterFunctionData() {
      return this.symbolIteratorThisGetterFunctionData;
   }

   public JSFunctionData getSymbolSpeciesThisGetterFunctionData() {
      return this.symbolSpeciesThisGetterFunctionData;
   }

   private static CallTarget createReadFrameThisCallTarget(JavaScriptLanguage lang) {
      return (new JavaScriptRootNode(lang, null, null) {
         @Override
         public Object execute(VirtualFrame frame) {
            return JSFrameUtil.getThisObj(frame);
         }
      }).getCallTarget();
   }

   @CompilerDirectives.TruffleBoundary
   public CallTarget getNotConstructibleCallTarget() {
      CallTarget result = this.notConstructibleCallTargetCache;
      if (result != null) {
         return result;
      } else {
         CallTarget var2 = createNotConstructibleCallTarget(this.getLanguage(), false, this);
         if (!notConstructibleCallTargetVarHandle.compareAndSet((JSContext)this, (CallTarget)((CallTarget)null), (CallTarget)var2)) {
            var2 = this.notConstructibleCallTargetCache;
         }

         return Objects.requireNonNull((CallTarget)var2);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public CallTarget getGeneratorNotConstructibleCallTarget() {
      CallTarget result = this.generatorNotConstructibleCallTargetCache;
      if (result != null) {
         return result;
      } else {
         CallTarget var2 = createNotConstructibleCallTarget(this.getLanguage(), true, this);
         if (!generatorNotConstructibleCallTargetVarHandle.compareAndSet((JSContext)this, (CallTarget)((CallTarget)null), (CallTarget)var2)) {
            var2 = this.generatorNotConstructibleCallTargetCache;
         }

         return Objects.requireNonNull((CallTarget)var2);
      }
   }

   private static RootCallTarget createNotConstructibleCallTarget(JavaScriptLanguage lang, boolean generator, JSContext context) {
      return (new JavaScriptRootNode(lang, null, null) {
         @Override
         public Object execute(VirtualFrame frame) {
            if (generator) {
               throw Errors.createTypeError("cannot construct a generator");
            } else {
               throw Errors.createTypeErrorNotAConstructor(JSArguments.getFunctionObject(frame.getArguments()), context);
            }
         }
      }).getCallTarget();
   }

   public JSFunctionData getBoundFunctionData(boolean constructor, boolean async) {
      if (async) {
         return constructor
            ? this.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.BoundConstructorAsync, c -> makeBoundFunctionData(c, true, true))
            : this.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.BoundFunctionAsync, c -> makeBoundFunctionData(c, false, true));
      } else {
         return constructor
            ? this.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.BoundConstructor, c -> makeBoundFunctionData(c, true, false))
            : this.getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey.BoundFunction, c -> makeBoundFunctionData(c, false, false));
      }
   }

   private static JSFunctionData makeBoundFunctionData(JSContext context, boolean constructor, boolean async) {
      CallTarget callTarget;
      CallTarget constructTarget;
      CallTarget constructNewTarget;
      if (!constructor && !async) {
         callTarget = JSFunction.createBoundRootNode(context, false, false).getCallTarget();
         constructTarget = JSFunction.createBoundRootNode(context, true, false).getCallTarget();
         constructNewTarget = JSFunction.createBoundRootNode(context, true, true).getCallTarget();
      } else {
         JSFunctionData template = context.getBoundFunctionData(false, false);
         callTarget = template.getCallTarget();
         constructTarget = template.getConstructTarget();
         constructNewTarget = template.getConstructNewTarget();
      }

      return JSFunctionData.create(
         context,
         callTarget,
         constructTarget,
         constructNewTarget,
         0,
         Strings.BOUND,
         constructor,
         false,
         true,
         false,
         false,
         false,
         async,
         false,
         true,
         false,
         true
      );
   }

   private JSAgent getJSAgent() {
      return this.getRealm().getAgent();
   }

   public int getEcmaScriptVersion() {
      return this.contextOptions.getEcmaScriptVersion();
   }

   public int getPropertyCacheLimit() {
      return this.contextOptions.getPropertyCacheLimit();
   }

   public int getFunctionCacheLimit() {
      return this.contextOptions.getFunctionCacheLimit();
   }

   void setAllocationReporter(TruffleLanguage.Env env) {
      CompilerAsserts.neverPartOfCompilation();
      this.allocationReporter = env.lookup(AllocationReporter.class);
   }

   public final AllocationReporter getAllocationReporter() {
      assert this.realmInit.get() == 2 : "getAllocationReporter() during Realm initialization";

      return this.allocationReporter;
   }

   public final <T> T trackAllocation(T object) {
      AllocationReporter reporter = this.getAllocationReporter();
      if (reporter != null) {
         reporter.onEnter(null, 0L, Long.MIN_VALUE);
         reporter.onReturnValue(object, 0L, Long.MIN_VALUE);
      }

      return object;
   }

   public boolean isOptionAnnexB() {
      return this.contextOptions.isAnnexB();
   }

   public boolean isOptionIntl402() {
      assert this.getInitialEnvironment() == null || !this.getInitialEnvironment().isPreInitialization() : "Patchable option intl-402 accessed during context pre-initialization.";

      return this.contextOptions.isIntl402();
   }

   public boolean isOptionRegexpMatchIndices() {
      return this.contextOptions.isRegexpMatchIndices();
   }

   public boolean isOptionRegexpStaticResult() {
      assert this.getInitialEnvironment() == null || !this.getInitialEnvironment().isPreInitialization() : "Patchable option static-regex-result accessed during context pre-initialization.";

      return this.contextOptions.isRegexpStaticResult();
   }

   public boolean isOptionRegexpStaticResultInContextInit() {
      return this.contextOptions.isRegexpStaticResult();
   }

   public boolean isOptionSharedArrayBuffer() {
      return this.contextOptions.isSharedArrayBuffer();
   }

   public boolean isOptionAtomics() {
      return this.contextOptions.isAtomics();
   }

   public boolean isOptionTemporal() {
      return this.contextOptions.isTemporal();
   }

   public boolean isOptionV8CompatibilityMode() {
      assert this.getInitialEnvironment() == null || !this.getInitialEnvironment().isPreInitialization() : "Patchable option v8-compat accessed during context pre-initialization.";

      return this.contextOptions.isV8CompatibilityMode();
   }

   public boolean isOptionV8CompatibilityModeInContextInit() {
      return this.contextOptions.isV8CompatibilityMode();
   }

   public boolean isOptionNashornCompatibilityMode() {
      return this.contextOptions.isNashornCompatibilityMode();
   }

   public boolean isOptionDebugBuiltin() {
      return this.contextOptions.isDebugBuiltin();
   }

   public boolean isOptionMleBuiltin() {
      return this.contextOptions.isMLEMode();
   }

   public boolean isOptionDirectByteBuffer() {
      assert this.getInitialEnvironment() == null || !this.getInitialEnvironment().isPreInitialization() : "Patchable option direct-byte-buffer accessed during context pre-initialization.";

      return this.contextOptions.isDirectByteBuffer();
   }

   public boolean isOptionParseOnly() {
      return this.contextOptions.isParseOnly();
   }

   public boolean isOptionDisableEval() {
      return this.contextOptions.isDisableEval();
   }

   public boolean isOptionDisableWith() {
      return this.contextOptions.isDisableWith();
   }

   public boolean isOptionAsyncStackTraces() {
      return this.contextOptions.isAsyncStackTraces();
   }

   public boolean isOptionForeignObjectPrototype() {
      return this.contextOptions.hasForeignObjectPrototype();
   }

   public long getTimerResolution() {
      assert this.getInitialEnvironment() == null || !this.getInitialEnvironment().isPreInitialization() : "Patchable option timer-resolution accessed during context pre-initialization.";

      return this.contextOptions.getTimerResolution();
   }

   public long getFunctionArgumentsLimit() {
      return this.contextOptions.getFunctionArgumentsLimit();
   }

   public int getStringLengthLimit() {
      return this.contextOptions.getStringLengthLimit();
   }

   public boolean usePromiseResolve() {
      return this.contextOptions.isAwaitOptimization();
   }

   public final void setPrepareStackTraceCallback(PrepareStackTraceCallback callback) {
      this.invalidatePrepareStackTraceCallbackNotUsedAssumption();
      this.prepareStackTraceCallback = callback;
   }

   public final PrepareStackTraceCallback getPrepareStackTraceCallback() {
      return this.prepareStackTraceCallbackNotUsedAssumption.isValid() ? null : this.prepareStackTraceCallback;
   }

   private void invalidatePrepareStackTraceCallbackNotUsedAssumption() {
      if (this.prepareStackTraceCallbackNotUsedAssumption.isValid()) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.prepareStackTraceCallbackNotUsedAssumption.invalidate("prepare stack trace callback unused");
      }
   }

   public PromiseRejectionTracker getPromiseRejectionTracker() {
      return this.promiseRejectionTracker;
   }

   public final void setPromiseRejectionTracker(PromiseRejectionTracker tracker) {
      this.invalidatePromiseRejectionTrackerNotUsedAssumption();
      this.promiseRejectionTracker = tracker;
   }

   private void invalidatePromiseRejectionTrackerNotUsedAssumption() {
      if (this.promiseRejectionTrackerNotUsedAssumption.isValid()) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.promiseRejectionTrackerNotUsedAssumption.invalidate("promise rejection tracker unused");
      }
   }

   public void notifyPromiseRejectionTracker(JSDynamicObject promise, int operation, Object value) {
      if (!this.promiseRejectionTrackerNotUsedAssumption.isValid() && this.promiseRejectionTracker != null) {
         switch (operation) {
            case 0:
               this.invokePromiseRejected(promise, value);
               break;
            case 1:
               this.invokePromiseRejectionHandled(promise);
               break;
            case 2:
               this.invokePromiseRejectedAfterResolved(promise, value);
               break;
            case 3:
               this.invokePromiseResolvedAfterResolved(promise, value);
               break;
            default:
               assert false : "Unknown operation: " + operation;
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   private void invokePromiseRejected(JSDynamicObject promise, Object value) {
      this.promiseRejectionTracker.promiseRejected(promise, value);
   }

   @CompilerDirectives.TruffleBoundary
   private void invokePromiseRejectionHandled(JSDynamicObject promise) {
      this.promiseRejectionTracker.promiseRejectionHandled(promise);
   }

   @CompilerDirectives.TruffleBoundary
   private void invokePromiseRejectedAfterResolved(JSDynamicObject promise, Object value) {
      this.promiseRejectionTracker.promiseRejectedAfterResolved(promise, value);
   }

   @CompilerDirectives.TruffleBoundary
   private void invokePromiseResolvedAfterResolved(JSDynamicObject promise, Object value) {
      this.promiseRejectionTracker.promiseResolvedAfterResolved(promise, value);
   }

   public final void setPromiseHook(PromiseHook promiseHook) {
      this.invalidatePromiseHookNotUsedAssumption();
      this.promiseHook = promiseHook;
   }

   private void invalidatePromiseHookNotUsedAssumption() {
      if (this.promiseHookNotUsedAssumption.isValid()) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         this.promiseHookNotUsedAssumption.invalidate("promise hook unused");
      }
   }

   public final void notifyPromiseHook(int changeType, JSDynamicObject promise) {
      if (!this.promiseHookNotUsedAssumption.isValid() && this.promiseHook != null) {
         JSRealm realm = JSRealm.getMain(null);
         if (changeType == -1) {
            realm.storeParentPromise(promise);
         } else {
            JSDynamicObject parent = changeType == 0 ? realm.fetchParentPromise() : Undefined.instance;
            this.notifyPromiseHookImpl(changeType, promise, parent);
         }
      }
   }

   @CompilerDirectives.TruffleBoundary
   private void notifyPromiseHookImpl(int changeType, JSDynamicObject promise, JSDynamicObject parent) {
      this.promiseHook.promiseChanged(changeType, promise, parent);
   }

   public final void setImportMetaInitializer(ImportMetaInitializer importMetaInitializer) {
      this.importMetaInitializerNotUsedAssumption.invalidate("ImportMetaInitializer unused");
      this.importMetaInitializer = importMetaInitializer;
   }

   public final boolean hasImportMetaInitializerBeenSet() {
      return !this.importMetaInitializerNotUsedAssumption.isValid();
   }

   @CompilerDirectives.TruffleBoundary
   public final void notifyImportMetaInitializer(JSDynamicObject importMeta, JSModuleRecord module) {
      if (this.importMetaInitializer != null) {
         this.importMetaInitializer.initializeImportMeta(importMeta, module);
      }
   }

   public final void setImportModuleDynamicallyCallback(ImportModuleDynamicallyCallback callback) {
      this.importModuleDynamicallyCallbackNotUsedAssumption.invalidate();
      this.importModuleDynamicallyCallback = callback;
   }

   public final boolean hasImportModuleDynamicallyCallbackBeenSet() {
      return !this.importModuleDynamicallyCallbackNotUsedAssumption.isValid();
   }

   @CompilerDirectives.TruffleBoundary
   public final JSDynamicObject hostImportModuleDynamically(JSRealm realm, ScriptOrModule referrer, Module.ModuleRequest moduleRequest) {
      return this.importModuleDynamicallyCallback != null ? this.importModuleDynamicallyCallback.importModuleDynamically(realm, referrer, moduleRequest) : null;
   }

   public final JSFunctionData getOrCreateBuiltinFunctionData(JSContext.BuiltinFunctionKey key, Function<JSContext, JSFunctionData> factory) {
      int index = key.ordinal();
      JSFunctionData functionData = this.builtinFunctionData[index];
      if (functionData != null) {
         return functionData;
      } else {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         functionData = factory.apply(this);
         if (!FUNCTION_DATA_ARRAY_VAR_HANDLE.compareAndSet(
            (JSFunctionData[])this.builtinFunctionData, (int)index, (JSFunctionData)((JSFunctionData)null), (JSFunctionData)functionData
         )) {
            functionData = (JSFunctionData)FUNCTION_DATA_ARRAY_VAR_HANDLE.getVolatile((JSFunctionData[])this.builtinFunctionData, (int)index);
         }

         return Objects.requireNonNull(functionData);
      }
   }

   public final JSFunctionData getBuiltinFunctionData(Builtin key) {
      CompilerAsserts.neverPartOfCompilation();
      return this.builtinFunctionDataMap.get(key);
   }

   public final void putBuiltinFunctionData(Builtin key, JSFunctionData functionData) {
      CompilerAsserts.neverPartOfCompilation();
      this.builtinFunctionDataMap.putIfAbsent(key, functionData);
   }

   public final boolean neverCreatedChildRealms() {
      return this.singleRealmAssumption.isValid();
   }

   public final boolean isSingleRealm() {
      return !this.isMultiContext() && this.singleRealmAssumption.isValid();
   }

   public final Assumption getSingleRealmAssumption() {
      return this.singleRealmAssumption;
   }

   public JSContextOptions getContextOptions() {
      return this.contextOptions;
   }

   public final boolean isMultiContext() {
      return this.isMultiContext;
   }

   public JSFunctionFactory getFunctionFactory(JSFunctionData functionData) {
      boolean isBuiltin = functionData.isBuiltin();
      boolean strictFunctionProperties = functionData.hasStrictFunctionProperties();
      boolean isConstructor = functionData.isConstructor();
      boolean isGenerator = functionData.isGenerator();
      boolean isAsync = functionData.isAsync();

      assert !isBuiltin || !isGenerator && !isAsync : "built-in functions are never generator or async functions!";

      if (isAsync) {
         return isGenerator ? this.asyncGeneratorFunctionFactory : this.asyncFunctionFactory;
      } else if (isGenerator) {
         return this.generatorFunctionFactory;
      } else if (isConstructor && !isBuiltin) {
         return strictFunctionProperties ? this.strictConstructorFactory : this.constructorFactory;
      } else {
         return strictFunctionProperties ? this.strictFunctionFactory : this.functionFactory;
      }
   }

   public JSFunctionFactory getBoundFunctionFactory(JSFunctionData functionData) {
      assert functionData.isStrict();

      return this.boundFunctionFactory;
   }

   JSObjectFactory.RealmData newObjectFactoryRealmData() {
      return this.isMultiContext() ? null : new JSObjectFactory.RealmData(this.factoryCount);
   }

   private JSFunctionData throwTypeErrorFunction() {
      CallTarget throwTypeErrorCallTarget = (new JavaScriptRootNode(this.getLanguage(), null, null) {
            @Override
            public Object execute(VirtualFrame frame) {
               throw Errors.createTypeError(
                  "'caller', 'callee', and 'arguments' properties may not be accessed on strict mode functions or the arguments objects for calls to them"
               );
            }
         })
         .getCallTarget();
      return JSFunctionData.create(this, throwTypeErrorCallTarget, throwTypeErrorCallTarget, 0, Strings.EMPTY_STRING, false, false, false, true);
   }

   private JSFunctionData protoSetterFunction() {
      CallTarget callTarget = (new JavaScriptRootNode(this.getLanguage(), null, null) {
         @Override
         public Object execute(VirtualFrame frame) {
            Object[] arguments = frame.getArguments();
            Object obj = JSRuntime.requireObjectCoercible(JSArguments.getThisObject(arguments), JSContext.this);
            if (JSArguments.getUserArgumentCount(arguments) < 1) {
               return Undefined.instance;
            } else {
               Object value = JSArguments.getUserArgument(arguments, 0);
               if (!JSDynamicObject.isJSDynamicObject(value) || value == Undefined.instance) {
                  return Undefined.instance;
               } else if (!JSDynamicObject.isJSDynamicObject(obj)) {
                  return Undefined.instance;
               } else {
                  JSDynamicObject thisObj = (JSDynamicObject)obj;
                  if (!JSObject.setPrototype(thisObj, (JSDynamicObject)value)) {
                     throw Errors.createTypeErrorCannotSetProto(thisObj, (JSDynamicObject)value);
                  } else {
                     return Undefined.instance;
                  }
               }
            }
         }
      }).getCallTarget();
      return JSFunctionData.createCallOnly(this, callTarget, 0, Strings.concat(Strings.SET_SPC, JSObject.PROTO));
   }

   private JSFunctionData protoGetterFunction() {
      CallTarget callTarget = (new JavaScriptRootNode(this.getLanguage(), null, null) {
         @Node.Child
         private JSToObjectNode toObjectNode = JSToObjectNode.createToObject(JSContext.this);
         @Node.Child
         private GetPrototypeNode getPrototypeNode = GetPrototypeNode.create();

         @Override
         public Object execute(VirtualFrame frame) {
            Object obj = this.toObjectNode.execute(JSArguments.getThisObject(frame.getArguments()));
            return JSDynamicObject.isJSDynamicObject(obj) ? this.getPrototypeNode.execute(obj) : Null.instance;
         }
      }).getCallTarget();
      return JSFunctionData.createCallOnly(this, callTarget, 0, Strings.concat(Strings.GET_SPC, JSObject.PROTO));
   }

   public void checkEvalAllowed() {
      if (this.isOptionDisableEval()) {
         throw Errors.createEvalDisabled();
      }
   }

   public boolean isOptionLoadFromURL() {
      return this.contextOptions.isLoadFromURL();
   }

   public boolean isOptionLoadFromClasspath() {
      return this.contextOptions.isLoadFromClasspath();
   }

   public Locale getLocale() {
      Locale loc = this.locale;
      if (loc == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         loc = this.getLocaleImpl();
         this.locale = loc;
      }

      return loc;
   }

   @CompilerDirectives.TruffleBoundary
   private Locale getLocaleImpl() {
      String name = this.getContextOptions().getLocale();
      return name.isEmpty() ? Locale.getDefault() : Locale.forLanguageTag(name);
   }

   public Charset getCharset() {
      Charset chrset = this.charset;
      if (chrset == null) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
         chrset = this.getCharsetImpl();
         this.charset = chrset;
      }

      return chrset;
   }

   @CompilerDirectives.TruffleBoundary
   private Charset getCharsetImpl() {
      String name = this.getContextOptions().getCharset();
      return name.isEmpty() ? Charset.defaultCharset() : Charset.forName(name);
   }

   public <T extends Node> T adoptNode(T node) {
      assert node.getParent() == null;

      this.sharedRootNode.insertAccessor(node);
      return node;
   }

   public boolean isOptionTopLevelAwait() {
      return this.getContextOptions().isTopLevelAwait();
   }

   public final Set<TruffleString> getSupportedImportAssertions() {
      return this.supportedImportAssertions;
   }

   public static TruffleString getTypeImportAssertion() {
      return TYPE_IMPORT_ASSERTION;
   }

   static {
      Lookup lookup = MethodHandles.lookup();

      try {
         notConstructibleCallTargetVarHandle = lookup.findVarHandle(JSContext.class, "notConstructibleCallTargetCache", CallTarget.class);
         generatorNotConstructibleCallTargetVarHandle = lookup.findVarHandle(JSContext.class, "generatorNotConstructibleCallTargetCache", CallTarget.class);
      } catch (IllegalAccessException | NoSuchFieldException var2) {
         throw Errors.shouldNotReachHere(var2);
      }
   }

   public static enum BuiltinFunctionKey {
      BoundFunction,
      BoundConstructor,
      BoundFunctionAsync,
      BoundConstructorAsync,
      ArrayFlattenIntoArray,
      AwaitFulfilled,
      AwaitRejected,
      AsyncGeneratorReturnFulfilled,
      AsyncGeneratorReturnRejected,
      AsyncFromSyncIteratorValueUnwrap,
      CollatorCompare,
      DateTimeFormatFormat,
      NumberFormatFormat,
      OrdinaryHasInstance,
      ProxyCall,
      ProxyRevokerFunction,
      PromiseResolveFunction,
      PromiseRejectFunction,
      PromiseGetCapabilitiesExecutor,
      PromiseResolveThenableJob,
      PromiseReactionJob,
      PromiseAllResolveElement,
      PromiseAllSettledResolveElement,
      PromiseAllSettledRejectElement,
      PromiseAnyRejectElement,
      PromiseThenFinally,
      PromiseCatchFinally,
      PromiseValueThunk,
      PromiseThrower,
      ImportModuleDynamically,
      JavaPackageToPrimitive,
      RegExpMultiLine,
      RegExpLastMatch,
      RegExpLastParen,
      RegExpLeftContext,
      RegExpRightContext,
      RegExp$1,
      RegExp$2,
      RegExp$3,
      RegExp$4,
      RegExp$5,
      RegExp$6,
      RegExp$7,
      RegExp$8,
      RegExp$9,
      SymbolGetDescription,
      MapGetSize,
      SetGetSize,
      ArrayBufferViewLength,
      ArrayBufferViewBuffer,
      ArrayBufferViewByteLength,
      ArrayBufferViewByteByteOffset,
      ArrayBufferViewToString,
      DataViewBuffer,
      DataViewByteLength,
      DataViewByteOffset,
      CollatorGetCompare,
      NumberFormatGetFormat,
      DateTimeFormatGetFormat,
      SegmenterBreakType,
      SegmenterPosition,
      FunctionAsyncIterator,
      IsGraalRuntime,
      SetUnhandledPromiseRejectionHandler,
      AsyncModuleExecutionFulfilled,
      AsyncModuleExecutionRejected,
      TopLevelAwaitResolve,
      TopLevelAwaitReject,
      WebAssemblyInstanceGetExports,
      WebAssemblyMemoryGetBuffer,
      WebAssemblyTableGetLength,
      WebAssemblyGlobalGetValue,
      WebAssemblyGlobalSetValue,
      WebAssemblySourceInstantiation,
      FinishImportModuleDynamicallyReject,
      FinishImportModuleDynamicallyResolve,
      TemporalTimeCalendar,
      TemporalTimeHour,
      TemporalTimeMinute,
      TemporalTimeSecond,
      TemporalTimeMillisecond,
      TemporalTimeMicrosecond,
      TemporalTimeNanosecond,
      TemporalDurationYears,
      TemporalDurationMonths,
      TemporalDurationWeeks,
      TemporalDurationDays,
      TemporalDurationHours,
      TemporalDurationMinutes,
      TemporalDurationSeconds,
      TemporalDurationMilliseconds,
      TemporalDurationMicroseconds,
      TemporalDurationNanoseconds,
      TemporalDurationSign,
      TemporalDurationBlank,
      TemporalCalendarId,
      TemporalPlainYearMonthCalendar,
      TemporalPlainYearMonthYear,
      TemporalPlainYearMonthMonth,
      TemporalPlainYearMonthMonthCode,
      TemporalPlainYearMonthDaysInYear,
      TemporalPlainYearMonthDaysInMonth,
      TemporalPlainYearMonthMonthsInYear,
      TemporalPlainYearMonthInLeapYear,
      TemporalPlainMonthDayCalendar,
      TemporalPlainMonthDayMonthCode,
      TemporalPlainMonthDayDay,
      TemporalDateCalendar,
      TemporalDateYear,
      TemporalDateMonth,
      TemporalDateMonthCode,
      TemporalDateDay,
      TemporalDateDayOfWeek,
      TemporalDateDayOfYear,
      TemporalDateWeekOfYear,
      TemporalDateDaysInWeek,
      TemporalDateDaysInMonth,
      TemporalDateDaysInYear,
      TemporalDateMonthsInYear,
      TemporalDateInLeapYear;
   }

   static final class SharedRootNode extends JavaScriptRootNode {
      @Override
      public Object execute(VirtualFrame frame) {
         throw Errors.shouldNotReachHere();
      }

      void insertAccessor(Node node) {
         CompilerAsserts.neverPartOfCompilation();
         super.insert(node);
      }
   }
}
