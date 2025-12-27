package com.oracle.truffle.js.runtime;

import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.DateFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.SimpleDateFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.TimeZoneFormat;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.text.TimeZoneNames;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.util.Calendar;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.util.GregorianCalendar;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.util.TimeZone;
import bryanthedragon.morph.cobblemonmorph.dependency.cobblemon.relocations.ibm.icu.util.ULocale;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleContext;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.ArrayIteratorPrototypeBuiltins;
import com.oracle.truffle.js.builtins.AtomicsBuiltins;
import com.oracle.truffle.js.builtins.ConsoleBuiltins;
import com.oracle.truffle.js.builtins.ConstructorBuiltins;
import com.oracle.truffle.js.builtins.DebugBuiltins;
import com.oracle.truffle.js.builtins.GlobalBuiltins;
import com.oracle.truffle.js.builtins.JSBuiltinsContainer;
import com.oracle.truffle.js.builtins.JavaBuiltins;
import com.oracle.truffle.js.builtins.MLEBuiltins;
import com.oracle.truffle.js.builtins.MapIteratorPrototypeBuiltins;
import com.oracle.truffle.js.builtins.ObjectFunctionBuiltins;
import com.oracle.truffle.js.builtins.OperatorsBuiltins;
import com.oracle.truffle.js.builtins.PerformanceBuiltins;
import com.oracle.truffle.js.builtins.PolyglotBuiltins;
import com.oracle.truffle.js.builtins.RealmFunctionBuiltins;
import com.oracle.truffle.js.builtins.ReflectBuiltins;
import com.oracle.truffle.js.builtins.RegExpBuiltins;
import com.oracle.truffle.js.builtins.RegExpStringIteratorPrototypeBuiltins;
import com.oracle.truffle.js.builtins.SetIteratorPrototypeBuiltins;
import com.oracle.truffle.js.builtins.StringIteratorPrototypeBuiltins;
import com.oracle.truffle.js.builtins.commonjs.GlobalCommonJSRequireBuiltins;
import com.oracle.truffle.js.builtins.commonjs.NpmCompatibleESModuleLoader;
import com.oracle.truffle.js.builtins.foreign.ForeignIterablePrototypeBuiltins;
import com.oracle.truffle.js.builtins.temporal.TemporalNowBuiltins;
import com.oracle.truffle.js.lang.JavaScriptLanguage;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.array.TypedArray;
import com.oracle.truffle.js.runtime.array.TypedArrayFactory;
import com.oracle.truffle.js.runtime.builtins.Builtin;
import com.oracle.truffle.js.runtime.builtins.JSAdapter;
import com.oracle.truffle.js.runtime.builtins.JSArray;
import com.oracle.truffle.js.runtime.builtins.JSArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferView;
import com.oracle.truffle.js.runtime.builtins.JSArrayObject;
import com.oracle.truffle.js.runtime.builtins.JSBigInt;
import com.oracle.truffle.js.runtime.builtins.JSBoolean;
import com.oracle.truffle.js.runtime.builtins.JSConstructor;
import com.oracle.truffle.js.runtime.builtins.JSDataView;
import com.oracle.truffle.js.runtime.builtins.JSDate;
import com.oracle.truffle.js.runtime.builtins.JSError;
import com.oracle.truffle.js.runtime.builtins.JSFinalizationRegistry;
import com.oracle.truffle.js.runtime.builtins.JSFunction;
import com.oracle.truffle.js.runtime.builtins.JSFunctionData;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.builtins.JSGlobal;
import com.oracle.truffle.js.runtime.builtins.JSMap;
import com.oracle.truffle.js.runtime.builtins.JSMath;
import com.oracle.truffle.js.runtime.builtins.JSNumber;
import com.oracle.truffle.js.runtime.builtins.JSON;
import com.oracle.truffle.js.runtime.builtins.JSObjectFactory;
import com.oracle.truffle.js.runtime.builtins.JSObjectPrototype;
import com.oracle.truffle.js.runtime.builtins.JSObjectPrototypeObject;
import com.oracle.truffle.js.runtime.builtins.JSOrdinary;
import com.oracle.truffle.js.runtime.builtins.JSPromise;
import com.oracle.truffle.js.runtime.builtins.JSProxy;
import com.oracle.truffle.js.runtime.builtins.JSRegExp;
import com.oracle.truffle.js.runtime.builtins.JSSet;
import com.oracle.truffle.js.runtime.builtins.JSSharedArrayBuffer;
import com.oracle.truffle.js.runtime.builtins.JSString;
import com.oracle.truffle.js.runtime.builtins.JSSymbol;
import com.oracle.truffle.js.runtime.builtins.JSTest262;
import com.oracle.truffle.js.runtime.builtins.JSTestV8;
import com.oracle.truffle.js.runtime.builtins.JSWeakMap;
import com.oracle.truffle.js.runtime.builtins.JSWeakRef;
import com.oracle.truffle.js.runtime.builtins.JSWeakSet;
import com.oracle.truffle.js.runtime.builtins.intl.JSCollator;
import com.oracle.truffle.js.runtime.builtins.intl.JSDateTimeFormat;
import com.oracle.truffle.js.runtime.builtins.intl.JSDisplayNames;
import com.oracle.truffle.js.runtime.builtins.intl.JSIntl;
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
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssembly;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyGlobal;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyInstance;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyMemory;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyMemoryGrowCallback;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyModule;
import com.oracle.truffle.js.runtime.builtins.wasm.JSWebAssemblyTable;
import com.oracle.truffle.js.runtime.interop.DynamicScopeWrapper;
import com.oracle.truffle.js.runtime.interop.JSInteropUtil;
import com.oracle.truffle.js.runtime.interop.TopScopeObject;
import com.oracle.truffle.js.runtime.java.JavaImporter;
import com.oracle.truffle.js.runtime.java.JavaPackage;
import com.oracle.truffle.js.runtime.objects.Accessor;
import com.oracle.truffle.js.runtime.objects.DefaultESModuleLoader;
import com.oracle.truffle.js.runtime.objects.JSAttributes;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.objects.JSModuleLoader;
import com.oracle.truffle.js.runtime.objects.JSObject;
import com.oracle.truffle.js.runtime.objects.JSObjectUtil;
import com.oracle.truffle.js.runtime.objects.Null;
import com.oracle.truffle.js.runtime.objects.PropertyDescriptor;
import com.oracle.truffle.js.runtime.objects.PropertyProxy;
import com.oracle.truffle.js.runtime.objects.Undefined;
import com.oracle.truffle.js.runtime.util.IntlUtil;
import com.oracle.truffle.js.runtime.util.LRUCache;
import com.oracle.truffle.js.runtime.util.PrintWriterWrapper;
import com.oracle.truffle.js.runtime.util.SimpleArrayList;
import com.oracle.truffle.js.runtime.util.TRegexUtil;
import com.oracle.truffle.js.runtime.util.TemporalConstants;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.nio.charset.Charset;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.SplittableRandom;
import java.util.WeakHashMap;
import java.util.Map.Entry;
import org.graalvm.collections.Pair;
import org.graalvm.home.HomeFinder;
import org.graalvm.options.OptionValues;

public class JSRealm {
   public static final TruffleString POLYGLOT_CLASS_NAME = Strings.constant("Polyglot");
   public static final TruffleString REFLECT_CLASS_NAME = Strings.constant("Reflect");
   public static final TruffleString SHARED_ARRAY_BUFFER_CLASS_NAME = Strings.constant("SharedArrayBuffer");
   public static final TruffleString ATOMICS_CLASS_NAME = Strings.constant("Atomics");
   public static final TruffleString REALM_BUILTIN_CLASS_NAME = Strings.constant("Realm");
   public static final TruffleString ARGUMENTS_NAME = Strings.constant("arguments");
   public static final TruffleString JAVA_CLASS_NAME = Strings.constant("Java");
   public static final TruffleString JAVA_CLASS_NAME_NASHORN_COMPAT = Strings.constant("JavaNashornCompat");
   public static final TruffleString PERFORMANCE_CLASS_NAME = Strings.constant("performance");
   public static final TruffleString DEBUG_CLASS_NAME = Strings.constant("Debug");
   public static final TruffleString CONSOLE_CLASS_NAME = Strings.constant("Console");
   public static final TruffleString SYMBOL_ITERATOR_NAME = Strings.constant("[Symbol.iterator]");
   public static final TruffleString MLE_CLASS_NAME = Strings.constant("MLE");
   private static final TruffleString GRAALVM_VERSION = Strings.fromJavaString(HomeFinder.getInstance().getVersion());
   private static final TruffleLanguage.ContextReference<JSRealm> REFERENCE = TruffleLanguage.ContextReference.create(JavaScriptLanguage.class);
   private final JSContext context;
   @CompilerDirectives.CompilationFinal
   private JSDynamicObject globalObject;
   private final JSFunctionObject objectConstructor;
   private final JSObjectPrototypeObject objectPrototype;
   private final JSFunctionObject functionConstructor;
   private final JSFunctionObject functionPrototype;
   private final JSFunctionObject arrayConstructor;
   private final JSArrayObject arrayPrototype;
   private final JSFunctionObject booleanConstructor;
   private final JSDynamicObject booleanPrototype;
   private final JSFunctionObject numberConstructor;
   private final JSDynamicObject numberPrototype;
   private final JSFunctionObject bigIntConstructor;
   private final JSDynamicObject bigIntPrototype;
   private final JSFunctionObject stringConstructor;
   private final JSDynamicObject stringPrototype;
   private final JSFunctionObject regExpConstructor;
   private final JSDynamicObject regExpPrototype;
   private final JSFunctionObject collatorConstructor;
   private final JSDynamicObject collatorPrototype;
   private final JSFunctionObject numberFormatConstructor;
   private final JSDynamicObject numberFormatPrototype;
   private final JSFunctionObject pluralRulesConstructor;
   private final JSDynamicObject pluralRulesPrototype;
   private final JSFunctionObject listFormatConstructor;
   private final JSDynamicObject listFormatPrototype;
   private final JSFunctionObject dateTimeFormatConstructor;
   private final JSDynamicObject dateTimeFormatPrototype;
   private final JSFunctionObject relativeTimeFormatConstructor;
   private final JSDynamicObject relativeTimeFormatPrototype;
   private final JSFunctionObject segmenterConstructor;
   private final JSDynamicObject segmenterPrototype;
   private final JSFunctionObject displayNamesConstructor;
   private final JSDynamicObject displayNamesPrototype;
   private final JSFunctionObject localeConstructor;
   private final JSDynamicObject localePrototype;
   private final JSFunctionObject dateConstructor;
   private final JSDynamicObject datePrototype;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final JSDynamicObject[] errorConstructors;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final JSDynamicObject[] errorPrototypes;
   private final JSFunctionObject callSiteConstructor;
   private final JSDynamicObject callSitePrototype;
   private final JSDynamicObject foreignArrayPrototype;
   private final JSDynamicObject foreignDatePrototype;
   private final JSDynamicObject foreignMapPrototype;
   private final JSDynamicObject foreignStringPrototype;
   private final JSDynamicObject foreignNumberPrototype;
   private final JSDynamicObject foreignBooleanPrototype;
   private final JSDynamicObject foreignFunctionPrototype;
   private final JSDynamicObject foreignObjectPrototype;
   private final Shape initialRegExpPrototypeShape;
   private final JSObjectFactory.RealmData objectFactories;
   private final JSFunctionObject temporalPlainTimeConstructor;
   private final JSDynamicObject temporalPlainTimePrototype;
   private final JSFunctionObject temporalPlainDateConstructor;
   private final JSDynamicObject temporalPlainDatePrototype;
   private final JSFunctionObject temporalPlainDateTimeConstructor;
   private final JSDynamicObject temporalPlainDateTimePrototype;
   private final JSFunctionObject temporalDurationConstructor;
   private final JSDynamicObject temporalDurationPrototype;
   private final JSFunctionObject temporalCalendarConstructor;
   private final JSDynamicObject temporalCalendarPrototype;
   private final JSFunctionObject temporalPlainYearMonthConstructor;
   private final JSDynamicObject temporalPlainYearMonthPrototype;
   private final JSFunctionObject temporalPlainMonthDayConstructor;
   private final JSDynamicObject temporalPlainMonthDayPrototype;
   private final JSFunctionObject temporalInstantConstructor;
   private final JSDynamicObject temporalInstantPrototype;
   private final JSFunctionObject temporalTimeZoneConstructor;
   private final JSDynamicObject temporalTimeZonePrototype;
   private final JSFunctionObject temporalZonedDateTimeConstructor;
   private final JSDynamicObject temporalZonedDateTimePrototype;
   private final JSFunctionObject symbolConstructor;
   private final JSDynamicObject symbolPrototype;
   private final JSFunctionObject mapConstructor;
   private final JSDynamicObject mapPrototype;
   private final JSFunctionObject setConstructor;
   private final JSDynamicObject setPrototype;
   private final JSFunctionObject weakRefConstructor;
   private final JSDynamicObject weakRefPrototype;
   private final JSFunctionObject weakMapConstructor;
   private final JSDynamicObject weakMapPrototype;
   private final JSFunctionObject weakSetConstructor;
   private final JSDynamicObject weakSetPrototype;
   private final JSDynamicObject mathObject;
   private JSDynamicObject realmBuiltinObject;
   private Object evalFunctionObject;
   private final Object applyFunctionObject;
   private final Object callFunctionObject;
   private Object reflectApplyFunctionObject;
   private Object reflectConstructFunctionObject;
   private Object commonJSRequireFunctionObject;
   private Object jsonParseFunctionObject;
   private final JSFunctionObject arrayBufferConstructor;
   private final JSDynamicObject arrayBufferPrototype;
   private final JSFunctionObject sharedArrayBufferConstructor;
   private final JSDynamicObject sharedArrayBufferPrototype;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final JSDynamicObject[] typedArrayConstructors;
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private final JSDynamicObject[] typedArrayPrototypes;
   private final JSFunctionObject dataViewConstructor;
   private final JSDynamicObject dataViewPrototype;
   private final JSFunctionObject jsAdapterConstructor;
   private final JSDynamicObject jsAdapterPrototype;
   private final JSFunctionObject javaImporterConstructor;
   private final JSDynamicObject javaImporterPrototype;
   private final JSFunctionObject proxyConstructor;
   private final JSDynamicObject proxyPrototype;
   private final JSFunctionObject finalizationRegistryConstructor;
   private final JSDynamicObject finalizationRegistryPrototype;
   private final JSDynamicObject iteratorPrototype;
   private final JSDynamicObject arrayIteratorPrototype;
   private final JSDynamicObject setIteratorPrototype;
   private final JSDynamicObject mapIteratorPrototype;
   private final JSDynamicObject segmentsPrototype;
   private final JSDynamicObject segmentIteratorPrototype;
   private final JSDynamicObject stringIteratorPrototype;
   private final JSDynamicObject regExpStringIteratorPrototype;
   private final JSDynamicObject enumerateIteratorPrototype;
   private final JSDynamicObject forInIteratorPrototype;
   private final JSFunctionObject generatorFunctionConstructor;
   private final JSDynamicObject generatorFunctionPrototype;
   private final JSDynamicObject generatorObjectPrototype;
   private final JSFunctionObject asyncFunctionConstructor;
   private final JSDynamicObject asyncFunctionPrototype;
   private final JSDynamicObject asyncIteratorPrototype;
   private final JSDynamicObject asyncFromSyncIteratorPrototype;
   private final JSDynamicObject asyncGeneratorObjectPrototype;
   private final JSFunctionObject asyncGeneratorFunctionConstructor;
   private final JSDynamicObject asyncGeneratorFunctionPrototype;
   private final JSDynamicObject throwerFunction;
   private final Accessor throwerAccessor;
   private final JSFunctionObject promiseConstructor;
   private final JSDynamicObject promisePrototype;
   private JSDynamicObject promiseAllFunctionObject;
   private Object unhandledPromiseRejectionHandler;
   private final JSDynamicObject ordinaryHasInstanceFunction;
   @CompilerDirectives.CompilationFinal
   private JSDynamicObject javaPackageToPrimitiveFunction;
   private final JSDynamicObject arrayProtoValuesIterator;
   @CompilerDirectives.CompilationFinal
   private JSFunctionObject typedArrayConstructor;
   @CompilerDirectives.CompilationFinal
   private JSDynamicObject typedArrayPrototype;
   private JSDynamicObject preinitIntlObject;
   private JSDynamicObject preinitConsoleBuiltinObject;
   private JSDynamicObject preinitPerformanceObject;
   private volatile Map<Object, JSDynamicObject> templateRegistry;
   private final JSDynamicObject globalScope;
   private final JSDynamicObject scriptEngineImportScope;
   @CompilerDirectives.CompilationFinal
   private TopScopeObject topScope;
   private TruffleLanguage.Env truffleLanguageEnv;
   private boolean preparingStackTrace;
   private Object embedderData;
   private Object staticRegexResult;
   private TruffleString staticRegexResultInputString = Strings.EMPTY_STRING;
   private Object staticRegexResultCompiledRegex;
   private boolean staticRegexResultInvalidated;
   private long staticRegexResultFromIndex;
   private TruffleString staticRegexResultOriginalInputString;
   private final Object wasmTableAlloc;
   private final Object wasmTableGrow;
   private final Object wasmTableRead;
   private final Object wasmTableWrite;
   private final Object wasmTableLength;
   private final Object wasmFuncType;
   private final Object wasmMemAlloc;
   private final Object wasmMemGrow;
   private final Object wasmMemAsByteBuffer;
   private final Object wasmGlobalAlloc;
   private final Object wasmGlobalRead;
   private final Object wasmGlobalWrite;
   private final Object wasmModuleDecode;
   private final Object wasmModuleInstantiate;
   private final Object wasmModuleValidate;
   private final Object wasmModuleExports;
   private final Object wasmModuleImports;
   private final Object wasmCustomSections;
   private final Object wasmInstanceExport;
   private final Object wasmEmbedderDataGet;
   private final Object wasmEmbedderDataSet;
   private final JSDynamicObject webAssemblyObject;
   private final JSFunctionObject webAssemblyGlobalConstructor;
   private final JSDynamicObject webAssemblyGlobalPrototype;
   private final JSFunctionObject webAssemblyInstanceConstructor;
   private final JSDynamicObject webAssemblyInstancePrototype;
   private final JSFunctionObject webAssemblyMemoryConstructor;
   private final JSDynamicObject webAssemblyMemoryPrototype;
   private final JSFunctionObject webAssemblyModuleConstructor;
   private final JSDynamicObject webAssemblyModulePrototype;
   private final JSFunctionObject webAssemblyTableConstructor;
   private final JSDynamicObject webAssemblyTablePrototype;
   private final JSWebAssemblyMemoryGrowCallback webAssemblyMemoryGrowCallback;
   private final JSDynamicObject foreignIterablePrototype;
   private ZoneId localTimeZoneId;
   private TimeZone localTimeZone;
   @CompilerDirectives.CompilationFinal
   private DateFormat jsDateFormat;
   @CompilerDirectives.CompilationFinal
   private DateFormat jsDateFormatBeforeYear0;
   @CompilerDirectives.CompilationFinal
   private DateFormat jsDateFormatAfterYear9999;
   @CompilerDirectives.CompilationFinal
   private DateFormat jsDateFormatISO;
   private DateFormat jsShortDateFormat;
   private DateFormat jsShortDateLocalFormat;
   private DateFormat jsShortTimeFormat;
   private DateFormat jsShortTimeLocalFormat;
   private DateFormat jsDateToStringFormat;
   public static final long NANOSECONDS_PER_MILLISECOND = 1000000L;
   private SplittableRandom random;
   private long nanoToZeroTimeOffset;
   private long nanoToCurrentTimeOffset;
   private long lastFuzzyTime = Long.MIN_VALUE;
   private PrintWriterWrapper outputWriter;
   private PrintWriterWrapper errorWriter;
   private final JSConsoleUtil consoleUtil;
   private JSModuleLoader moduleLoader;
   private long lastAsyncEvaluationOrder;
   @CompilerDirectives.CompilationFinal
   private JSAgent agent;
   private List<JSRealm> realmList;
   private final JSRealm parentRealm;
   private JSRealm currentRealm;
   private JSRealm v8RealmCurrent = this;
   Object v8RealmShared = Undefined.instance;
   private JavaScriptBaseNode callNode;
   private final Map<TruffleFile, JSDynamicObject> commonJSRequireCache;
   private final SimpleArrayList<Object> joinStack = new SimpleArrayList<>();
   private Map<Source, Object> compiledRegexCache;
   private Object customEsmPathMappingCallback;
   private JSDynamicObject parentPromise;
   private static final TruffleString REALM_SHARED_NAME = Strings.constant("shared");
   private static final PropertyProxy REALM_SHARED_PROXY = new JSRealm.RealmSharedPropertyProxy();

   protected JSRealm(JSContext context, TruffleLanguage.Env env) {
      this(context, env, null);
   }

   protected JSRealm(JSContext context, TruffleLanguage.Env env, JSRealm parentRealm) {
      this.context = context;
      this.truffleLanguageEnv = env;
      this.parentRealm = parentRealm;
      if (parentRealm == null) {
         this.currentRealm = this;
      } else {
         this.currentRealm = null;
         this.agent = parentRealm.agent;
         if (context.getContextOptions().isV8RealmBuiltin()) {
            JSRealm topLevelRealm = parentRealm;

            while (topLevelRealm.parentRealm != null) {
               topLevelRealm = topLevelRealm.parentRealm;
            }

            topLevelRealm.addToRealmList(this);
         }
      }

      this.objectPrototype = JSObjectPrototype.create(context);
      this.functionPrototype = JSFunction.createFunctionPrototype(this, this.objectPrototype);
      this.objectFactories = context.newObjectFactoryRealmData();
      this.throwerFunction = this.createThrowerFunction();
      this.throwerAccessor = new Accessor(this.throwerFunction, this.throwerFunction);
      if (context.isOptionAnnexB()) {
         putProtoAccessorProperty(this);
      }

      this.globalObject = JSGlobal.create(this, this.objectPrototype);
      this.globalScope = JSGlobal.createGlobalScope(context);
      if (context.getContextOptions().isScriptEngineGlobalScopeImport()) {
         this.scriptEngineImportScope = JSOrdinary.createWithNullPrototypeInit(context);
      } else {
         this.scriptEngineImportScope = null;
      }

      this.topScope = this.createTopScope();
      this.objectConstructor = createObjectConstructor(this, this.objectPrototype);
      JSObjectUtil.putDataProperty(context, this.objectPrototype, JSObject.CONSTRUCTOR, this.objectConstructor, JSAttributes.getDefaultNotEnumerable());
      JSObjectUtil.putFunctionsFromContainer(this, this.objectPrototype, JSObjectPrototype.BUILTINS);
      this.functionConstructor = JSFunction.createFunctionConstructor(this);
      JSFunction.fillFunctionPrototype(this);
      this.applyFunctionObject = JSDynamicObject.getOrNull(this.getFunctionPrototype(), Strings.APPLY);
      this.callFunctionObject = JSDynamicObject.getOrNull(this.getFunctionPrototype(), Strings.CALL);
      JSConstructor ctor = JSArray.createConstructor(this);
      this.arrayConstructor = ctor.getFunctionObject();
      this.arrayPrototype = (JSArrayObject)ctor.getPrototype();
      ctor = JSBoolean.createConstructor(this);
      this.booleanConstructor = ctor.getFunctionObject();
      this.booleanPrototype = ctor.getPrototype();
      ctor = JSNumber.createConstructor(this);
      this.numberConstructor = ctor.getFunctionObject();
      this.numberPrototype = ctor.getPrototype();
      ctor = JSString.createConstructor(this);
      this.stringConstructor = ctor.getFunctionObject();
      this.stringPrototype = ctor.getPrototype();
      ctor = JSRegExp.createConstructor(this);
      this.regExpConstructor = ctor.getFunctionObject();
      this.regExpPrototype = ctor.getPrototype();
      ctor = JSDate.createConstructor(this);
      this.dateConstructor = ctor.getFunctionObject();
      this.datePrototype = ctor.getPrototype();
      this.initialRegExpPrototypeShape = this.regExpPrototype.getShape();
      boolean es6 = context.getContextOptions().getEcmaScriptVersion() >= 6;
      if (es6) {
         ctor = JSSymbol.createConstructor(this);
         this.symbolConstructor = ctor.getFunctionObject();
         this.symbolPrototype = ctor.getPrototype();
         ctor = JSMap.createConstructor(this);
         this.mapConstructor = ctor.getFunctionObject();
         this.mapPrototype = ctor.getPrototype();
         ctor = JSSet.createConstructor(this);
         this.setConstructor = ctor.getFunctionObject();
         this.setPrototype = ctor.getPrototype();
         ctor = JSWeakMap.createConstructor(this);
         this.weakMapConstructor = ctor.getFunctionObject();
         this.weakMapPrototype = ctor.getPrototype();
         ctor = JSWeakSet.createConstructor(this);
         this.weakSetConstructor = ctor.getFunctionObject();
         this.weakSetPrototype = ctor.getPrototype();
         ctor = JSProxy.createConstructor(this);
         this.proxyConstructor = ctor.getFunctionObject();
         this.proxyPrototype = ctor.getPrototype();
         ctor = JSPromise.createConstructor(this);
         this.promiseConstructor = ctor.getFunctionObject();
         this.promisePrototype = ctor.getPrototype();
      } else {
         this.symbolConstructor = null;
         this.symbolPrototype = null;
         this.mapConstructor = null;
         this.mapPrototype = null;
         this.setConstructor = null;
         this.setPrototype = null;
         this.weakMapConstructor = null;
         this.weakMapPrototype = null;
         this.weakSetConstructor = null;
         this.weakSetPrototype = null;
         this.proxyConstructor = null;
         this.proxyPrototype = null;
         this.promiseConstructor = null;
         this.promisePrototype = null;
      }

      this.errorConstructors = new JSDynamicObject[JSErrorType.errorTypes().length];
      this.errorPrototypes = new JSDynamicObject[JSErrorType.errorTypes().length];
      this.initializeErrorConstructors();
      ctor = JSError.createCallSiteConstructor(this);
      this.callSiteConstructor = ctor.getFunctionObject();
      this.callSitePrototype = ctor.getPrototype();
      ctor = JSArrayBuffer.createConstructor(this);
      this.arrayBufferConstructor = ctor.getFunctionObject();
      this.arrayBufferPrototype = ctor.getPrototype();
      this.typedArrayConstructors = new JSDynamicObject[TypedArray.factories(context).length];
      this.typedArrayPrototypes = new JSDynamicObject[TypedArray.factories(context).length];
      this.initializeTypedArrayConstructors();
      ctor = JSDataView.createConstructor(this);
      this.dataViewConstructor = ctor.getFunctionObject();
      this.dataViewPrototype = ctor.getPrototype();
      if (context.getContextOptions().isBigInt()) {
         ctor = JSBigInt.createConstructor(this);
         this.bigIntConstructor = ctor.getFunctionObject();
         this.bigIntPrototype = ctor.getPrototype();
      } else {
         this.bigIntConstructor = null;
         this.bigIntPrototype = null;
      }

      this.iteratorPrototype = this.createIteratorPrototype();
      this.arrayIteratorPrototype = es6 ? this.createArrayIteratorPrototype() : null;
      this.setIteratorPrototype = es6 ? this.createSetIteratorPrototype() : null;
      this.mapIteratorPrototype = es6 ? this.createMapIteratorPrototype() : null;
      this.stringIteratorPrototype = es6 ? this.createStringIteratorPrototype() : null;
      this.regExpStringIteratorPrototype = context.getContextOptions().getEcmaScriptVersion() >= 10 ? this.createRegExpStringIteratorPrototype() : null;
      ctor = JSCollator.createConstructor(this);
      this.collatorConstructor = ctor.getFunctionObject();
      this.collatorPrototype = ctor.getPrototype();
      ctor = JSNumberFormat.createConstructor(this);
      this.numberFormatConstructor = ctor.getFunctionObject();
      this.numberFormatPrototype = ctor.getPrototype();
      ctor = JSDateTimeFormat.createConstructor(this);
      this.dateTimeFormatConstructor = ctor.getFunctionObject();
      this.dateTimeFormatPrototype = ctor.getPrototype();
      ctor = JSPluralRules.createConstructor(this);
      this.pluralRulesConstructor = ctor.getFunctionObject();
      this.pluralRulesPrototype = ctor.getPrototype();
      ctor = JSListFormat.createConstructor(this);
      this.listFormatConstructor = ctor.getFunctionObject();
      this.listFormatPrototype = ctor.getPrototype();
      ctor = JSRelativeTimeFormat.createConstructor(this);
      this.relativeTimeFormatConstructor = ctor.getFunctionObject();
      this.relativeTimeFormatPrototype = ctor.getPrototype();
      ctor = JSSegmenter.createConstructor(this);
      this.segmenterConstructor = ctor.getFunctionObject();
      this.segmenterPrototype = ctor.getPrototype();
      this.segmentsPrototype = JSSegmenter.createSegmentsPrototype(this);
      this.segmentIteratorPrototype = JSSegmenter.createSegmentIteratorPrototype(this);
      ctor = JSDisplayNames.createConstructor(this);
      this.displayNamesConstructor = ctor.getFunctionObject();
      this.displayNamesPrototype = ctor.getPrototype();
      ctor = JSLocale.createConstructor(this);
      this.localeConstructor = ctor.getFunctionObject();
      this.localePrototype = ctor.getPrototype();
      if (es6) {
         ctor = JSFunction.createGeneratorFunctionConstructor(this);
         this.generatorFunctionConstructor = ctor.getFunctionObject();
         this.generatorFunctionPrototype = ctor.getPrototype();
         this.generatorObjectPrototype = (JSDynamicObject)JSDynamicObject.getOrNull(this.generatorFunctionPrototype, JSObject.PROTOTYPE);
      } else {
         this.generatorFunctionConstructor = null;
         this.generatorFunctionPrototype = null;
         this.generatorObjectPrototype = null;
      }

      this.enumerateIteratorPrototype = JSFunction.createEnumerateIteratorPrototype(this);
      this.forInIteratorPrototype = JSFunction.createForInIteratorPrototype(this);
      this.arrayProtoValuesIterator = (JSDynamicObject)JSDynamicObject.getOrDefault(this.getArrayPrototype(), Symbol.SYMBOL_ITERATOR, Undefined.instance);
      if (context.isOptionSharedArrayBuffer()) {
         ctor = JSSharedArrayBuffer.createConstructor(this);
         this.sharedArrayBufferConstructor = ctor.getFunctionObject();
         this.sharedArrayBufferPrototype = ctor.getPrototype();
      } else {
         this.sharedArrayBufferConstructor = null;
         this.sharedArrayBufferPrototype = null;
      }

      this.mathObject = JSMath.create(this);
      boolean es8 = context.getContextOptions().getEcmaScriptVersion() >= 8;
      if (es8) {
         ctor = JSFunction.createAsyncFunctionConstructor(this);
         this.asyncFunctionConstructor = ctor.getFunctionObject();
         this.asyncFunctionPrototype = ctor.getPrototype();
      } else {
         this.asyncFunctionConstructor = null;
         this.asyncFunctionPrototype = null;
      }

      boolean es9 = context.getContextOptions().getEcmaScriptVersion() >= 9;
      if (es9) {
         this.asyncIteratorPrototype = JSFunction.createAsyncIteratorPrototype(this);
         this.asyncFromSyncIteratorPrototype = JSFunction.createAsyncFromSyncIteratorPrototype(this);
         ctor = JSFunction.createAsyncGeneratorFunctionConstructor(this);
         this.asyncGeneratorFunctionConstructor = ctor.getFunctionObject();
         this.asyncGeneratorFunctionPrototype = ctor.getPrototype();
         this.asyncGeneratorObjectPrototype = (JSDynamicObject)JSDynamicObject.getOrNull(this.asyncGeneratorFunctionPrototype, JSObject.PROTOTYPE);
      } else {
         this.asyncIteratorPrototype = null;
         this.asyncFromSyncIteratorPrototype = null;
         this.asyncGeneratorFunctionConstructor = null;
         this.asyncGeneratorFunctionPrototype = null;
         this.asyncGeneratorObjectPrototype = null;
      }

      boolean es12 = context.getContextOptions().getEcmaScriptVersion() >= 12;
      if (es12) {
         ctor = JSWeakRef.createConstructor(this);
         this.weakRefConstructor = ctor.getFunctionObject();
         this.weakRefPrototype = ctor.getPrototype();
         ctor = JSFinalizationRegistry.createConstructor(this);
         this.finalizationRegistryConstructor = ctor.getFunctionObject();
         this.finalizationRegistryPrototype = ctor.getPrototype();
      } else {
         this.weakRefConstructor = null;
         this.weakRefPrototype = null;
         this.finalizationRegistryConstructor = null;
         this.finalizationRegistryPrototype = null;
      }

      this.ordinaryHasInstanceFunction = JSFunction.createOrdinaryHasInstanceFunction(this);
      boolean nashornCompat = context.isOptionNashornCompatibilityMode();
      if (nashornCompat) {
         ctor = JSAdapter.createConstructor(this);
         this.jsAdapterConstructor = ctor.getFunctionObject();
         this.jsAdapterPrototype = ctor.getPrototype();
         ctor = JavaImporter.createConstructor(this);
         this.javaImporterConstructor = ctor.getFunctionObject();
         this.javaImporterPrototype = ctor.getPrototype();
      } else {
         this.jsAdapterConstructor = null;
         this.jsAdapterPrototype = null;
         this.javaImporterConstructor = null;
         this.javaImporterPrototype = null;
      }

      Charset charset = context.getCharset();
      this.outputWriter = new PrintWriterWrapper(env.out(), true, charset);
      this.errorWriter = new PrintWriterWrapper(env.err(), true, charset);
      this.consoleUtil = new JSConsoleUtil();
      if (context.getContextOptions().isCommonJSRequire()) {
         this.commonJSRequireCache = new HashMap<>();
      } else {
         this.commonJSRequireCache = null;
      }

      if (context.getContextOptions().isWebAssembly()) {
         if (!this.isWasmAvailable()) {
            String msg = "WebAssembly API enabled but wasm language cannot be accessed! Did you forget to set the --polyglot flag?";
            if (JSConfig.SubstrateVM) {
               msg = msg + " In native mode, you might have to rebuild libpolyglot with 'gu rebuild-images libpolyglot'.";
            }

            throw new IllegalStateException(msg);
         }

         LanguageInfo wasmLanguageInfo = this.truffleLanguageEnv.getInternalLanguages().get("wasm");
         this.truffleLanguageEnv.initializeLanguage(wasmLanguageInfo);
         Object wasmObject = this.truffleLanguageEnv.importSymbol("WebAssembly");

         Object wasmMemSetGrowCallback;
         try {
            InteropLibrary wasmInterop = InteropLibrary.getUncached(wasmObject);
            this.wasmTableAlloc = wasmInterop.readMember(wasmObject, "table_alloc");
            this.wasmTableGrow = wasmInterop.readMember(wasmObject, "table_grow");
            this.wasmTableRead = wasmInterop.readMember(wasmObject, "table_read");
            this.wasmTableWrite = wasmInterop.readMember(wasmObject, "table_write");
            this.wasmTableLength = wasmInterop.readMember(wasmObject, "table_size");
            this.wasmFuncType = wasmInterop.readMember(wasmObject, "func_type");
            this.wasmMemAlloc = wasmInterop.readMember(wasmObject, "mem_alloc");
            this.wasmMemGrow = wasmInterop.readMember(wasmObject, "mem_grow");
            this.wasmGlobalAlloc = wasmInterop.readMember(wasmObject, "global_alloc");
            this.wasmGlobalRead = wasmInterop.readMember(wasmObject, "global_read");
            this.wasmGlobalWrite = wasmInterop.readMember(wasmObject, "global_write");
            this.wasmModuleDecode = wasmInterop.readMember(wasmObject, "module_decode");
            this.wasmModuleInstantiate = wasmInterop.readMember(wasmObject, "module_instantiate");
            this.wasmModuleValidate = wasmInterop.readMember(wasmObject, "module_validate");
            this.wasmModuleExports = wasmInterop.readMember(wasmObject, "module_exports");
            this.wasmModuleImports = wasmInterop.readMember(wasmObject, "module_imports");
            this.wasmCustomSections = wasmInterop.readMember(wasmObject, "custom_sections");
            this.wasmInstanceExport = wasmInterop.readMember(wasmObject, "instance_export");
            wasmMemSetGrowCallback = wasmInterop.readMember(wasmObject, "mem_set_grow_callback");
            this.wasmEmbedderDataGet = wasmInterop.readMember(wasmObject, "embedder_data_get");
            this.wasmEmbedderDataSet = wasmInterop.readMember(wasmObject, "embedder_data_set");
            this.wasmMemAsByteBuffer = wasmInterop.readMember(wasmObject, "mem_as_byte_buffer");
         } catch (InteropException var15) {
            throw Errors.shouldNotReachHere(var15);
         }

         this.webAssemblyObject = JSWebAssembly.create(this);
         ctor = JSWebAssemblyModule.createConstructor(this);
         this.webAssemblyModuleConstructor = ctor.getFunctionObject();
         this.webAssemblyModulePrototype = ctor.getPrototype();
         ctor = JSWebAssemblyInstance.createConstructor(this);
         this.webAssemblyInstanceConstructor = ctor.getFunctionObject();
         this.webAssemblyInstancePrototype = ctor.getPrototype();
         ctor = JSWebAssemblyMemory.createConstructor(this);
         this.webAssemblyMemoryConstructor = ctor.getFunctionObject();
         this.webAssemblyMemoryPrototype = ctor.getPrototype();
         ctor = JSWebAssemblyTable.createConstructor(this);
         this.webAssemblyTableConstructor = ctor.getFunctionObject();
         this.webAssemblyTablePrototype = ctor.getPrototype();
         ctor = JSWebAssemblyGlobal.createConstructor(this);
         this.webAssemblyGlobalConstructor = ctor.getFunctionObject();
         this.webAssemblyGlobalPrototype = ctor.getPrototype();
         this.webAssemblyMemoryGrowCallback = new JSWebAssemblyMemoryGrowCallback(this, wasmMemSetGrowCallback);
      } else {
         this.wasmTableAlloc = null;
         this.wasmTableGrow = null;
         this.wasmTableRead = null;
         this.wasmTableWrite = null;
         this.wasmTableLength = null;
         this.wasmFuncType = null;
         this.wasmMemAlloc = null;
         this.wasmMemGrow = null;
         this.wasmMemAsByteBuffer = null;
         this.wasmGlobalAlloc = null;
         this.wasmGlobalRead = null;
         this.wasmGlobalWrite = null;
         this.wasmModuleDecode = null;
         this.wasmModuleInstantiate = null;
         this.wasmModuleValidate = null;
         this.wasmModuleExports = null;
         this.wasmModuleImports = null;
         this.wasmCustomSections = null;
         this.wasmInstanceExport = null;
         this.wasmEmbedderDataGet = null;
         this.wasmEmbedderDataSet = null;
         this.webAssemblyObject = null;
         this.webAssemblyGlobalConstructor = null;
         this.webAssemblyGlobalPrototype = null;
         this.webAssemblyInstanceConstructor = null;
         this.webAssemblyInstancePrototype = null;
         this.webAssemblyMemoryConstructor = null;
         this.webAssemblyMemoryPrototype = null;
         this.webAssemblyModuleConstructor = null;
         this.webAssemblyModulePrototype = null;
         this.webAssemblyTableConstructor = null;
         this.webAssemblyTablePrototype = null;
         this.webAssemblyMemoryGrowCallback = null;
      }

      this.foreignIterablePrototype = this.createForeignIterablePrototype();
      if (context.isOptionTemporal()) {
         ctor = JSTemporalPlainTime.createConstructor(this);
         this.temporalPlainTimeConstructor = ctor.getFunctionObject();
         this.temporalPlainTimePrototype = ctor.getPrototype();
         ctor = JSTemporalPlainDate.createConstructor(this);
         this.temporalPlainDateConstructor = ctor.getFunctionObject();
         this.temporalPlainDatePrototype = ctor.getPrototype();
         ctor = JSTemporalPlainDateTime.createConstructor(this);
         this.temporalPlainDateTimeConstructor = ctor.getFunctionObject();
         this.temporalPlainDateTimePrototype = ctor.getPrototype();
         ctor = JSTemporalDuration.createConstructor(this);
         this.temporalDurationConstructor = ctor.getFunctionObject();
         this.temporalDurationPrototype = ctor.getPrototype();
         ctor = JSTemporalCalendar.createConstructor(this);
         this.temporalCalendarConstructor = ctor.getFunctionObject();
         this.temporalCalendarPrototype = ctor.getPrototype();
         ctor = JSTemporalPlainYearMonth.createConstructor(this);
         this.temporalPlainYearMonthConstructor = ctor.getFunctionObject();
         this.temporalPlainYearMonthPrototype = ctor.getPrototype();
         ctor = JSTemporalPlainMonthDay.createConstructor(this);
         this.temporalPlainMonthDayConstructor = ctor.getFunctionObject();
         this.temporalPlainMonthDayPrototype = ctor.getPrototype();
         ctor = JSTemporalInstant.createConstructor(this);
         this.temporalInstantConstructor = ctor.getFunctionObject();
         this.temporalInstantPrototype = ctor.getPrototype();
         ctor = JSTemporalTimeZone.createConstructor(this);
         this.temporalTimeZoneConstructor = ctor.getFunctionObject();
         this.temporalTimeZonePrototype = ctor.getPrototype();
         ctor = JSTemporalZonedDateTime.createConstructor(this);
         this.temporalZonedDateTimeConstructor = ctor.getFunctionObject();
         this.temporalZonedDateTimePrototype = ctor.getPrototype();
      } else {
         this.temporalPlainTimeConstructor = null;
         this.temporalPlainTimePrototype = null;
         this.temporalPlainDateConstructor = null;
         this.temporalPlainDatePrototype = null;
         this.temporalPlainDateTimeConstructor = null;
         this.temporalPlainDateTimePrototype = null;
         this.temporalDurationConstructor = null;
         this.temporalDurationPrototype = null;
         this.temporalCalendarConstructor = null;
         this.temporalCalendarPrototype = null;
         this.temporalPlainYearMonthConstructor = null;
         this.temporalPlainYearMonthPrototype = null;
         this.temporalPlainMonthDayConstructor = null;
         this.temporalPlainMonthDayPrototype = null;
         this.temporalInstantConstructor = null;
         this.temporalInstantPrototype = null;
         this.temporalTimeZoneConstructor = null;
         this.temporalTimeZonePrototype = null;
         this.temporalZonedDateTimeConstructor = null;
         this.temporalZonedDateTimePrototype = null;
      }

      this.foreignArrayPrototype = JSOrdinary.createInit(this, this.arrayPrototype);
      this.foreignDatePrototype = JSOrdinary.createInit(this, this.datePrototype);
      this.foreignMapPrototype = JSOrdinary.createInit(this, this.mapPrototype == null ? Null.instance : this.mapPrototype);
      this.foreignStringPrototype = JSOrdinary.createInit(this, this.stringPrototype);
      this.foreignNumberPrototype = JSOrdinary.createInit(this, this.numberPrototype);
      this.foreignBooleanPrototype = JSOrdinary.createInit(this, this.booleanPrototype);
      this.foreignFunctionPrototype = JSOrdinary.createInit(this, this.functionPrototype);
      this.foreignObjectPrototype = JSOrdinary.createInit(this, this.objectPrototype);
   }

   private void initializeTypedArrayConstructors() {
      JSConstructor taConst = JSArrayBufferView.createTypedArrayConstructor(this);
      this.typedArrayConstructor = taConst.getFunctionObject();
      this.typedArrayPrototype = taConst.getPrototype();

      for (TypedArrayFactory factory : TypedArray.factories(this.context)) {
         JSConstructor constructor = JSArrayBufferView.createConstructor(this, factory, taConst);
         this.typedArrayConstructors[factory.getFactoryIndex()] = constructor.getFunctionObject();
         this.typedArrayPrototypes[factory.getFactoryIndex()] = constructor.getPrototype();
      }
   }

   private void initializeErrorConstructors() {
      for (JSErrorType type : JSErrorType.errorTypes()) {
         JSConstructor errorConstructor = JSError.createErrorConstructor(this, type);
         this.errorConstructors[type.ordinal()] = errorConstructor.getFunctionObject();
         this.errorPrototypes[type.ordinal()] = errorConstructor.getPrototype();
      }
   }

   public final JSContext getContext() {
      return this.context;
   }

   public static JSRealm getMain(Node node) {
      return REFERENCE.get(node);
   }

   public static JSRealm get(Node node) {
      JSRealm mainRealm = REFERENCE.get(node);
      if (CompilerDirectives.inCompiledCode()) {
         if (CompilerDirectives.isPartialEvaluationConstant(node) && node != null && JavaScriptLanguage.get(node).getJSContext().isSingleRealm()) {
            assert mainRealm == mainRealm.currentRealm;

            return mainRealm;
         }
      } else {
         assert mainRealm.currentRealm == mainRealm || !JavaScriptLanguage.get(node).getJSContext().isSingleRealm();
      }

      return mainRealm.currentRealm;
   }

   private boolean allowEnterLeave(Node node, JSRealm otherRealm) {
      assert this.isMainRealm() && getMain(node) == this;

      assert !JavaScriptLanguage.get(node).getJSContext().isSingleRealm() || this.currentRealm == otherRealm;

      return true;
   }

   public JSRealm enterRealm(Node node, JSRealm childRealm) {
      assert this.allowEnterLeave(node, childRealm);

      JSRealm prev = this.currentRealm;
      this.currentRealm = childRealm;
      return prev;
   }

   public void leaveRealm(Node node, JSRealm prevRealm) {
      assert this.allowEnterLeave(node, prevRealm);

      this.currentRealm = prevRealm;
   }

   public final JSFunctionObject lookupFunction(JSBuiltinsContainer container, TruffleString methodName) {
      assert JSRuntime.isPropertyKey(methodName);

      Builtin builtin = Objects.requireNonNull(container.lookupFunctionByName(methodName));
      JSFunctionData functionData = builtin.createFunctionData(this.context);
      return JSFunction.create(this, functionData);
   }

   public final Accessor lookupAccessor(JSBuiltinsContainer container, Object key) {
      Pair<JSBuiltin, JSBuiltin> pair = container.lookupAccessorByKey(key);
      JSBuiltin getterBuiltin = pair.getLeft();
      JSBuiltin setterBulitin = pair.getRight();
      JSFunctionObject getterFunction = null;
      JSFunctionObject setterFunction = null;
      if (getterBuiltin != null) {
         JSFunctionData functionData = getterBuiltin.createFunctionData(this.context);
         getterFunction = JSFunction.create(this, functionData);
      }

      if (setterBulitin != null) {
         JSFunctionData functionData = setterBulitin.createFunctionData(this.context);
         setterFunction = JSFunction.create(this, functionData);
      }

      return new Accessor(getterFunction, setterFunction);
   }

   public static JSFunctionObject createObjectConstructor(JSRealm realm, JSDynamicObject objectPrototype) {
      JSContext context = realm.getContext();
      JSFunctionObject objectConstructor = realm.lookupFunction(ConstructorBuiltins.BUILTINS, JSOrdinary.CLASS_NAME);
      JSObjectUtil.putConstructorPrototypeProperty(context, objectConstructor, objectPrototype);
      JSObjectUtil.putFunctionsFromContainer(realm, objectConstructor, ObjectFunctionBuiltins.BUILTINS);
      if (context.isOptionNashornCompatibilityMode()) {
         JSObjectUtil.putFunctionsFromContainer(realm, objectConstructor, ObjectFunctionBuiltins.BUILTINS_NASHORN_COMPAT);
      }

      return objectConstructor;
   }

   public final JSDynamicObject getErrorConstructor(JSErrorType type) {
      return this.errorConstructors[type.ordinal()];
   }

   public final JSDynamicObject getErrorPrototype(JSErrorType type) {
      return this.errorPrototypes[type.ordinal()];
   }

   public final JSDynamicObject getGlobalObject() {
      return this.globalObject;
   }

   public final void setGlobalObject(JSDynamicObject global) {
      this.context.getGlobalObjectPristineAssumption().invalidate();
      this.globalObject = global;
      this.topScope = this.createTopScope();
   }

   private TopScopeObject createTopScope() {
      return new TopScopeObject(new Object[]{this.scriptEngineImportScope, new DynamicScopeWrapper(this.globalScope), this.globalObject});
   }

   public final void dispose() {
      this.globalObject = Undefined.instance;
      this.topScope = TopScopeObject.empty();
   }

   public final JSFunctionObject getObjectConstructor() {
      return this.objectConstructor;
   }

   public final JSDynamicObject getObjectPrototype() {
      return this.objectPrototype;
   }

   public final JSFunctionObject getFunctionConstructor() {
      return this.functionConstructor;
   }

   public final JSDynamicObject getFunctionPrototype() {
      return this.functionPrototype;
   }

   public final JSFunctionObject getArrayConstructor() {
      return this.arrayConstructor;
   }

   public final JSDynamicObject getArrayPrototype() {
      return this.arrayPrototype;
   }

   public final JSFunctionObject getBooleanConstructor() {
      return this.booleanConstructor;
   }

   public final JSDynamicObject getBooleanPrototype() {
      return this.booleanPrototype;
   }

   public final JSFunctionObject getNumberConstructor() {
      return this.numberConstructor;
   }

   public final JSDynamicObject getNumberPrototype() {
      return this.numberPrototype;
   }

   public final JSFunctionObject getBigIntConstructor() {
      return this.bigIntConstructor;
   }

   public final JSDynamicObject getBigIntPrototype() {
      return this.bigIntPrototype;
   }

   public final JSFunctionObject getStringConstructor() {
      return this.stringConstructor;
   }

   public final JSDynamicObject getStringPrototype() {
      return this.stringPrototype;
   }

   public final JSFunctionObject getRegExpConstructor() {
      return this.regExpConstructor;
   }

   public final JSDynamicObject getRegExpPrototype() {
      return this.regExpPrototype;
   }

   public final JSFunctionObject getCollatorConstructor() {
      return this.collatorConstructor;
   }

   public final JSDynamicObject getCollatorPrototype() {
      return this.collatorPrototype;
   }

   public final JSFunctionObject getNumberFormatConstructor() {
      return this.numberFormatConstructor;
   }

   public final JSDynamicObject getNumberFormatPrototype() {
      return this.numberFormatPrototype;
   }

   public final JSFunctionObject getPluralRulesConstructor() {
      return this.pluralRulesConstructor;
   }

   public final JSDynamicObject getPluralRulesPrototype() {
      return this.pluralRulesPrototype;
   }

   public final JSFunctionObject getListFormatConstructor() {
      return this.listFormatConstructor;
   }

   public final JSDynamicObject getListFormatPrototype() {
      return this.listFormatPrototype;
   }

   public final JSFunctionObject getRelativeTimeFormatConstructor() {
      return this.relativeTimeFormatConstructor;
   }

   public final JSDynamicObject getRelativeTimeFormatPrototype() {
      return this.relativeTimeFormatPrototype;
   }

   public final JSFunctionObject getDateTimeFormatConstructor() {
      return this.dateTimeFormatConstructor;
   }

   public final JSDynamicObject getDateTimeFormatPrototype() {
      return this.dateTimeFormatPrototype;
   }

   public final JSFunctionObject getDateConstructor() {
      return this.dateConstructor;
   }

   public final JSDynamicObject getDatePrototype() {
      return this.datePrototype;
   }

   public final JSFunctionObject getSegmenterConstructor() {
      return this.segmenterConstructor;
   }

   public final JSDynamicObject getSegmenterPrototype() {
      return this.segmenterPrototype;
   }

   public final JSFunctionObject getDisplayNamesConstructor() {
      return this.displayNamesConstructor;
   }

   public final JSDynamicObject getDisplayNamesPrototype() {
      return this.displayNamesPrototype;
   }

   public final JSFunctionObject getLocaleConstructor() {
      return this.localeConstructor;
   }

   public final JSDynamicObject getLocalePrototype() {
      return this.localePrototype;
   }

   public final JSFunctionObject getSymbolConstructor() {
      return this.symbolConstructor;
   }

   public final JSDynamicObject getSymbolPrototype() {
      return this.symbolPrototype;
   }

   public final JSFunctionObject getMapConstructor() {
      return this.mapConstructor;
   }

   public final JSDynamicObject getMapPrototype() {
      return this.mapPrototype;
   }

   public final JSFunctionObject getSetConstructor() {
      return this.setConstructor;
   }

   public final JSDynamicObject getSetPrototype() {
      return this.setPrototype;
   }

   public final JSFunctionObject getWeakRefConstructor() {
      return this.weakRefConstructor;
   }

   public final JSDynamicObject getWeakRefPrototype() {
      return this.weakRefPrototype;
   }

   public final JSFunctionObject getFinalizationRegistryConstructor() {
      return this.finalizationRegistryConstructor;
   }

   public final JSDynamicObject getFinalizationRegistryPrototype() {
      return this.finalizationRegistryPrototype;
   }

   public final JSFunctionObject getWeakMapConstructor() {
      return this.weakMapConstructor;
   }

   public final JSDynamicObject getWeakMapPrototype() {
      return this.weakMapPrototype;
   }

   public final JSFunctionObject getWeakSetConstructor() {
      return this.weakSetConstructor;
   }

   public final JSDynamicObject getWeakSetPrototype() {
      return this.weakSetPrototype;
   }

   public final Shape getInitialRegExpPrototypeShape() {
      return this.initialRegExpPrototypeShape;
   }

   public final JSFunctionObject getArrayBufferConstructor() {
      return this.arrayBufferConstructor;
   }

   public final JSDynamicObject getArrayBufferPrototype() {
      return this.arrayBufferPrototype;
   }

   public final JSFunctionObject getSharedArrayBufferConstructor() {
      assert this.context.isOptionSharedArrayBuffer();

      return this.sharedArrayBufferConstructor;
   }

   public final JSDynamicObject getSharedArrayBufferPrototype() {
      assert this.context.isOptionSharedArrayBuffer();

      return this.sharedArrayBufferPrototype;
   }

   public final JSDynamicObject getArrayBufferViewConstructor(TypedArrayFactory factory) {
      return this.typedArrayConstructors[factory.getFactoryIndex()];
   }

   public final JSDynamicObject getArrayBufferViewPrototype(TypedArrayFactory factory) {
      return this.typedArrayPrototypes[factory.getFactoryIndex()];
   }

   public final JSFunctionObject getDataViewConstructor() {
      return this.dataViewConstructor;
   }

   public final JSDynamicObject getDataViewPrototype() {
      return this.dataViewPrototype;
   }

   public final JSFunctionObject getTypedArrayConstructor() {
      return this.typedArrayConstructor;
   }

   public final JSDynamicObject getTypedArrayPrototype() {
      return this.typedArrayPrototype;
   }

   public final JSDynamicObject getRealmBuiltinObject() {
      return this.realmBuiltinObject;
   }

   public final JSFunctionObject getProxyConstructor() {
      return this.proxyConstructor;
   }

   public final JSDynamicObject getProxyPrototype() {
      return this.proxyPrototype;
   }

   public final JSFunctionObject getGeneratorFunctionConstructor() {
      return this.generatorFunctionConstructor;
   }

   public final JSDynamicObject getGeneratorFunctionPrototype() {
      return this.generatorFunctionPrototype;
   }

   public final JSFunctionObject getAsyncFunctionConstructor() {
      return this.asyncFunctionConstructor;
   }

   public final JSDynamicObject getAsyncFunctionPrototype() {
      return this.asyncFunctionPrototype;
   }

   public final JSFunctionObject getAsyncGeneratorFunctionConstructor() {
      return this.asyncGeneratorFunctionConstructor;
   }

   public final JSDynamicObject getAsyncGeneratorFunctionPrototype() {
      return this.asyncGeneratorFunctionPrototype;
   }

   public final JSDynamicObject getEnumerateIteratorPrototype() {
      return this.enumerateIteratorPrototype;
   }

   public final JSDynamicObject getForInIteratorPrototype() {
      return this.forInIteratorPrototype;
   }

   public final JSDynamicObject getGeneratorObjectPrototype() {
      return this.generatorObjectPrototype;
   }

   public final JSDynamicObject getAsyncGeneratorObjectPrototype() {
      return this.asyncGeneratorObjectPrototype;
   }

   public final JSFunctionObject getJavaImporterConstructor() {
      return this.javaImporterConstructor;
   }

   public final JSDynamicObject getJavaImporterPrototype() {
      return this.javaImporterPrototype;
   }

   public final JSDynamicObject getJavaPackageToPrimitiveFunction() {
      assert this.javaPackageToPrimitiveFunction != null;

      return this.javaPackageToPrimitiveFunction;
   }

   public final JSFunctionObject getTemporalPlainTimeConstructor() {
      return this.temporalPlainTimeConstructor;
   }

   public final JSDynamicObject getTemporalPlainTimePrototype() {
      return this.temporalPlainTimePrototype;
   }

   public final JSFunctionObject getTemporalPlainDateConstructor() {
      return this.temporalPlainDateConstructor;
   }

   public final JSDynamicObject getTemporalPlainDatePrototype() {
      return this.temporalPlainDatePrototype;
   }

   public final JSFunctionObject getTemporalPlainDateTimeConstructor() {
      return this.temporalPlainDateTimeConstructor;
   }

   public final JSDynamicObject getTemporalPlainDateTimePrototype() {
      return this.temporalPlainDateTimePrototype;
   }

   public final JSFunctionObject getTemporalDurationConstructor() {
      return this.temporalDurationConstructor;
   }

   public final JSDynamicObject getTemporalDurationPrototype() {
      return this.temporalDurationPrototype;
   }

   public final JSFunctionObject getTemporalCalendarConstructor() {
      return this.temporalCalendarConstructor;
   }

   public final JSDynamicObject getTemporalCalendarPrototype() {
      return this.temporalCalendarPrototype;
   }

   public final JSFunctionObject getTemporalPlainYearMonthConstructor() {
      return this.temporalPlainYearMonthConstructor;
   }

   public JSDynamicObject getTemporalPlainYearMonthPrototype() {
      return this.temporalPlainYearMonthPrototype;
   }

   public JSFunctionObject getTemporalPlainMonthDayConstructor() {
      return this.temporalPlainMonthDayConstructor;
   }

   public JSDynamicObject getTemporalPlainMonthDayPrototype() {
      return this.temporalPlainMonthDayPrototype;
   }

   public JSFunctionObject getTemporalInstantConstructor() {
      return this.temporalInstantConstructor;
   }

   public JSDynamicObject getTemporalInstantPrototype() {
      return this.temporalInstantPrototype;
   }

   public JSFunctionObject getTemporalTimeZoneConstructor() {
      return this.temporalTimeZoneConstructor;
   }

   public JSDynamicObject getTemporalTimeZonePrototype() {
      return this.temporalTimeZonePrototype;
   }

   public JSFunctionObject getTemporalZonedDateTimeConstructor() {
      return this.temporalZonedDateTimeConstructor;
   }

   public JSDynamicObject getTemporalZonedDateTimePrototype() {
      return this.temporalZonedDateTimePrototype;
   }

   public final JSDynamicObject getForeignArrayPrototype() {
      return this.foreignArrayPrototype;
   }

   public final JSDynamicObject getForeignDatePrototype() {
      return this.foreignDatePrototype;
   }

   public JSDynamicObject getForeignMapPrototype() {
      return this.foreignMapPrototype;
   }

   public JSDynamicObject getForeignStringPrototype() {
      return this.foreignStringPrototype;
   }

   public JSDynamicObject getForeignNumberPrototype() {
      return this.foreignNumberPrototype;
   }

   public JSDynamicObject getForeignBooleanPrototype() {
      return this.foreignBooleanPrototype;
   }

   public JSDynamicObject getForeignFunctionPrototype() {
      return this.foreignFunctionPrototype;
   }

   public JSDynamicObject getForeignObjectPrototype() {
      return this.foreignObjectPrototype;
   }

   public final Map<Object, JSDynamicObject> getTemplateRegistry() {
      if (this.templateRegistry == null) {
         this.createTemplateRegistry();
      }

      return this.templateRegistry;
   }

   @CompilerDirectives.TruffleBoundary
   private void createTemplateRegistry() {
      if (this.templateRegistry == null) {
         this.templateRegistry = new WeakHashMap<>();
      }
   }

   public final Object getEvalFunctionObject() {
      return this.evalFunctionObject;
   }

   public final Object getApplyFunctionObject() {
      return this.applyFunctionObject;
   }

   public final Object getCallFunctionObject() {
      return this.callFunctionObject;
   }

   public final Object getReflectApplyFunctionObject() {
      return this.reflectApplyFunctionObject;
   }

   public final Object getReflectConstructFunctionObject() {
      return this.reflectConstructFunctionObject;
   }

   public final Object getCommonJSRequireFunctionObject() {
      return this.commonJSRequireFunctionObject;
   }

   public final Object getJsonParseFunctionObject() {
      return this.jsonParseFunctionObject;
   }

   public final JSDynamicObject getPromiseAllFunctionObject() {
      return this.promiseAllFunctionObject;
   }

   public final Object getUnhandledPromiseRejectionHandler() {
      return this.unhandledPromiseRejectionHandler;
   }

   private static void putProtoAccessorProperty(final JSRealm realm) {
      JSContext context = realm.getContext();
      JSDynamicObject getProto = JSFunction.create(realm, context.protoGetterFunctionData);
      JSDynamicObject setProto = JSFunction.create(realm, context.protoSetterFunctionData);
      JSObjectUtil.putBuiltinAccessorProperty(realm.getObjectPrototype(), JSObject.PROTO, getProto, setProto);
   }

   public final JSDynamicObject getThrowerFunction() {
      assert this.throwerFunction != null;

      return this.throwerFunction;
   }

   public final Accessor getThrowerAccessor() {
      assert this.throwerAccessor != null;

      return this.throwerAccessor;
   }

   public JSDynamicObject getIteratorPrototype() {
      return this.iteratorPrototype;
   }

   public JSDynamicObject getAsyncIteratorPrototype() {
      return this.asyncIteratorPrototype;
   }

   public JSDynamicObject getAsyncFromSyncIteratorPrototype() {
      return this.asyncFromSyncIteratorPrototype;
   }

   public JSDynamicObject getArrayIteratorPrototype() {
      return this.arrayIteratorPrototype;
   }

   public JSDynamicObject getSetIteratorPrototype() {
      return this.setIteratorPrototype;
   }

   public JSDynamicObject getMapIteratorPrototype() {
      return this.mapIteratorPrototype;
   }

   public JSDynamicObject getStringIteratorPrototype() {
      return this.stringIteratorPrototype;
   }

   public JSDynamicObject getRegExpStringIteratorPrototype() {
      return this.regExpStringIteratorPrototype;
   }

   public JSDynamicObject getSegmentsPrototype() {
      return this.segmentsPrototype;
   }

   public JSDynamicObject getSegmentIteratorPrototype() {
      return this.segmentIteratorPrototype;
   }

   private JSDynamicObject createThrowerFunction() {
      CompilerAsserts.neverPartOfCompilation();
      JSDynamicObject thrower = JSFunction.create(this, this.context.throwerFunctionData);
      JSObject.preventExtensions(thrower);
      JSObject.setIntegrityLevel(thrower, true);
      return thrower;
   }

   public JSFunctionObject getPromiseConstructor() {
      return this.promiseConstructor;
   }

   public JSDynamicObject getPromisePrototype() {
      return this.promisePrototype;
   }

   public final JSObjectFactory.RealmData getObjectFactories() {
      return this.objectFactories;
   }

   public void setupGlobals() {
      CompilerAsserts.neverPartOfCompilation("do not setup globals from compiled code");
      long time = this.context.getContextOptions().isProfileTime() ? System.nanoTime() : 0L;
      JSDynamicObject global = this.getGlobalObject();
      this.putGlobalProperty(JSOrdinary.CLASS_NAME, this.getObjectConstructor());
      this.putGlobalProperty(JSFunction.CLASS_NAME, this.getFunctionConstructor());
      this.putGlobalProperty(JSArray.CLASS_NAME, this.getArrayConstructor());
      this.putGlobalProperty(JSString.CLASS_NAME, this.getStringConstructor());
      this.putGlobalProperty(JSDate.CLASS_NAME, this.getDateConstructor());
      this.putGlobalProperty(JSNumber.CLASS_NAME, this.getNumberConstructor());
      this.putGlobalProperty(JSBoolean.CLASS_NAME, this.getBooleanConstructor());
      this.putGlobalProperty(JSRegExp.CLASS_NAME, this.getRegExpConstructor());
      this.putGlobalProperty(JSMath.CLASS_NAME, this.mathObject);
      this.putGlobalProperty(JSON.CLASS_NAME, JSON.create(this));
      JSObjectUtil.putDataProperty(this.context, global, Strings.NAN, Double.NaN);
      JSObjectUtil.putDataProperty(this.context, global, Strings.INFINITY, Double.POSITIVE_INFINITY);
      JSObjectUtil.putDataProperty(this.context, global, Undefined.NAME, Undefined.instance);
      JSObjectUtil.putFunctionsFromContainer(this, global, GlobalBuiltins.GLOBAL_FUNCTIONS);
      this.evalFunctionObject = JSObject.get(global, JSGlobal.EVAL_NAME);
      JSDynamicObject jsonBuiltin = (JSDynamicObject)JSObject.get(global, Strings.CAPS_JSON);
      this.jsonParseFunctionObject = JSObject.get(jsonBuiltin, Strings.PARSE);
      boolean webassembly = this.context.getContextOptions().isWebAssembly();

      for (JSErrorType type : JSErrorType.errorTypes()) {
         switch (type) {
            case CompileError:
            case LinkError:
            case RuntimeError:
               if (webassembly) {
                  JSObjectUtil.putDataProperty(
                     this.context,
                     this.webAssemblyObject,
                     Strings.fromJavaString(type.name()),
                     this.getErrorConstructor(type),
                     JSAttributes.getDefaultNotEnumerable()
                  );
               }
               break;
            case AggregateError:
               if (this.context.getEcmaScriptVersion() >= 12) {
                  this.putGlobalProperty(Strings.fromJavaString(type.name()), this.getErrorConstructor(type));
               }
               break;
            default:
               this.putGlobalProperty(Strings.fromJavaString(type.name()), this.getErrorConstructor(type));
         }
      }

      this.putGlobalProperty(JSArrayBuffer.CLASS_NAME, this.getArrayBufferConstructor());

      for (TypedArrayFactory factory : TypedArray.factories(this.context)) {
         this.putGlobalProperty(factory.getName(), this.getArrayBufferViewConstructor(factory));
      }

      this.putGlobalProperty(JSDataView.CLASS_NAME, this.getDataViewConstructor());
      if (this.context.getContextOptions().isBigInt()) {
         this.putGlobalProperty(JSBigInt.CLASS_NAME, this.getBigIntConstructor());
      }

      if (this.context.isOptionNashornCompatibilityMode()) {
         this.initGlobalNashornExtensions();
         this.removeNashornIncompatibleBuiltins();
      }

      if (this.context.getContextOptions().isScriptEngineGlobalScopeImport()) {
         TruffleString builtin = Strings.IMPORT_SCRIPT_ENGINE_GLOBAL_BINDINGS;
         JSObjectUtil.putDataProperty(
            this.context,
            this.getScriptEngineImportScope(),
            builtin,
            this.lookupFunction(GlobalBuiltins.GLOBAL_NASHORN_EXTENSIONS, builtin),
            JSAttributes.notConfigurableNotEnumerableNotWritable()
         );
      }

      if (this.context.getContextOptions().isPolyglotBuiltin() && (this.getEnv().isPolyglotEvalAllowed() || this.getEnv().isPolyglotBindingsAccessAllowed())) {
         this.setupPolyglot();
      }

      if (this.context.isOptionDebugBuiltin()) {
         this.putGlobalProperty(Strings.fromJavaString(this.context.getContextOptions().getDebugPropertyName()), this.createDebugObject());
      }

      if (this.context.isOptionMleBuiltin()) {
         this.putGlobalProperty(Strings.fromJavaString("MLE"), this.createMleObject());
      }

      if (this.context.getContextOptions().isTest262Mode()) {
         this.putGlobalProperty(JSTest262.GLOBAL_PROPERTY_NAME, JSTest262.create(this));
      }

      if (this.context.getContextOptions().isTestV8Mode()) {
         this.putGlobalProperty(JSTestV8.CLASS_NAME, JSTestV8.create(this));
      }

      if (this.context.getContextOptions().isV8RealmBuiltin()) {
         this.initRealmBuiltinObject();
      }

      if (this.context.getEcmaScriptVersion() >= 6) {
         Object parseInt = JSObject.get(global, Strings.PARSE_INT);
         Object parseFloat = JSObject.get(global, Strings.PARSE_FLOAT);
         this.putProperty(this.getNumberConstructor(), Strings.PARSE_INT, parseInt);
         this.putProperty(this.getNumberConstructor(), Strings.PARSE_FLOAT, parseFloat);
         this.putGlobalProperty(JSMap.CLASS_NAME, this.getMapConstructor());
         this.putGlobalProperty(JSSet.CLASS_NAME, this.getSetConstructor());
         this.putGlobalProperty(JSWeakMap.CLASS_NAME, this.getWeakMapConstructor());
         this.putGlobalProperty(JSWeakSet.CLASS_NAME, this.getWeakSetConstructor());
         this.putGlobalProperty(JSSymbol.CLASS_NAME, this.getSymbolConstructor());
         setupPredefinedSymbols(this.getSymbolConstructor());
         JSDynamicObject reflectObject = this.createReflect();
         this.putGlobalProperty(REFLECT_CLASS_NAME, reflectObject);
         this.reflectApplyFunctionObject = JSObject.get(reflectObject, Strings.APPLY);
         this.reflectConstructFunctionObject = JSObject.get(reflectObject, Strings.CONSTRUCT);
         this.putGlobalProperty(JSProxy.CLASS_NAME, this.getProxyConstructor());
         this.putGlobalProperty(JSPromise.CLASS_NAME, this.getPromiseConstructor());
         this.promiseAllFunctionObject = (JSDynamicObject)JSObject.get(this.getPromiseConstructor(), Strings.ALL);
      }

      if (this.context.isOptionSharedArrayBuffer()) {
         this.putGlobalProperty(SHARED_ARRAY_BUFFER_CLASS_NAME, this.getSharedArrayBufferConstructor());
      }

      if (this.context.isOptionAtomics()) {
         this.putGlobalProperty(ATOMICS_CLASS_NAME, this.createAtomics());
      }

      if (this.context.getEcmaScriptVersion() >= 10) {
         this.putGlobalProperty(Strings.GLOBAL_THIS, global);
      }

      if (this.context.getEcmaScriptVersion() >= 12) {
         this.putGlobalProperty(JSWeakRef.CLASS_NAME, this.getWeakRefConstructor());
         this.putGlobalProperty(JSFinalizationRegistry.CLASS_NAME, this.getFinalizationRegistryConstructor());
      }

      if (this.context.getContextOptions().isGraalBuiltin()) {
         this.putGraalObject();
      }

      if (webassembly) {
         this.putGlobalProperty(JSWebAssembly.CLASS_NAME, this.webAssemblyObject);
         JSObjectUtil.putDataProperty(
            this.context,
            this.webAssemblyObject,
            JSFunction.getName(this.webAssemblyGlobalConstructor),
            this.webAssemblyGlobalConstructor,
            JSAttributes.getDefaultNotEnumerable()
         );
         JSObjectUtil.putDataProperty(
            this.context,
            this.webAssemblyObject,
            JSFunction.getName(this.webAssemblyInstanceConstructor),
            this.webAssemblyInstanceConstructor,
            JSAttributes.getDefaultNotEnumerable()
         );
         JSObjectUtil.putDataProperty(
            this.context,
            this.webAssemblyObject,
            JSFunction.getName(this.webAssemblyMemoryConstructor),
            this.webAssemblyMemoryConstructor,
            JSAttributes.getDefaultNotEnumerable()
         );
         JSObjectUtil.putDataProperty(
            this.context,
            this.webAssemblyObject,
            JSFunction.getName(this.webAssemblyModuleConstructor),
            this.webAssemblyModuleConstructor,
            JSAttributes.getDefaultNotEnumerable()
         );
         JSObjectUtil.putDataProperty(
            this.context,
            this.webAssemblyObject,
            JSFunction.getName(this.webAssemblyTableConstructor),
            this.webAssemblyTableConstructor,
            JSAttributes.getDefaultNotEnumerable()
         );
      }

      if (this.context.getContextOptions().isOperatorOverloading()) {
         JSObjectUtil.putFunctionsFromContainer(this, global, OperatorsBuiltins.BUILTINS);
      }

      if (this.context.isOptionTemporal()) {
         this.addTemporalGlobals();
      }

      if (this.context.getContextOptions().isProfileTime()) {
         System.out.println("SetupGlobals: " + (System.nanoTime() - time) / 1000000L);
      }
   }

   private void initGlobalNashornExtensions() {
      assert this.getContext().isOptionNashornCompatibilityMode();

      this.putGlobalProperty(JSAdapter.CLASS_NAME, this.jsAdapterConstructor);
      this.putGlobalProperty(Strings.EXIT, this.lookupFunction(GlobalBuiltins.GLOBAL_NASHORN_EXTENSIONS, Strings.EXIT));
      this.putGlobalProperty(Strings.QUIT, this.lookupFunction(GlobalBuiltins.GLOBAL_NASHORN_EXTENSIONS, Strings.QUIT));
      this.putGlobalProperty(Strings.PARSE_TO_JSON, this.lookupFunction(GlobalBuiltins.GLOBAL_NASHORN_EXTENSIONS, Strings.PARSE_TO_JSON));
   }

   private void removeNashornIncompatibleBuiltins() {
      assert this.getContext().isOptionNashornCompatibilityMode();

      JSObject.delete(this.typedArrayPrototype, Strings.JOIN);
   }

   private void addPrintGlobals() {
      if (this.context.getContextOptions().isPrint()) {
         this.putGlobalProperty(Strings.PRINT, this.lookupFunction(GlobalBuiltins.GLOBAL_PRINT, Strings.PRINT));
         this.putGlobalProperty(Strings.PRINT_ERR, this.lookupFunction(GlobalBuiltins.GLOBAL_PRINT, Strings.PRINT_ERR));
      }
   }

   @CompilerDirectives.TruffleBoundary
   private void addCommonJSGlobals() {
      if (this.getContext().getContextOptions().isCommonJSRequire()) {
         String cwdOption = this.getContext().getContextOptions().getRequireCwd();
         TruffleFile cwdFile = this.getEnv().getPublicTruffleFile(cwdOption);

         try {
            if (cwdOption != null && !cwdFile.exists()) {
               throw Errors.createError("Invalid CommonJS root folder: " + cwdOption);
            }
         } catch (SecurityException var9) {
            throw Errors.createError("Access denied to CommonJS root folder: " + cwdOption);
         }

         JSDynamicObject requireFunction = this.lookupFunction(GlobalBuiltins.GLOBAL_COMMONJS_REQUIRE_EXTENSIONS, Strings.REQUIRE_PROPERTY_NAME);
         JSDynamicObject resolveFunction = this.lookupFunction(GlobalBuiltins.GLOBAL_COMMONJS_REQUIRE_EXTENSIONS, Strings.RESOLVE_PROPERTY_NAME);
         JSObject.set(requireFunction, Strings.RESOLVE_PROPERTY_NAME, resolveFunction);
         this.putGlobalProperty(Strings.REQUIRE_PROPERTY_NAME, requireFunction);
         JSDynamicObject dirnameGetter = this.lookupFunction(
            GlobalBuiltins.GLOBAL_COMMONJS_REQUIRE_EXTENSIONS, GlobalCommonJSRequireBuiltins.GlobalRequire.dirnameGetter.getName()
         );
         JSObject.defineOwnProperty(
            this.getGlobalObject(), Strings.DIRNAME_VAR_NAME, PropertyDescriptor.createAccessor(dirnameGetter, Undefined.instance, false, false)
         );
         JSDynamicObject filenameGetter = this.lookupFunction(
            GlobalBuiltins.GLOBAL_COMMONJS_REQUIRE_EXTENSIONS, GlobalCommonJSRequireBuiltins.GlobalRequire.filenameGetter.getName()
         );
         JSObject.defineOwnProperty(
            this.getGlobalObject(), Strings.FILENAME_VAR_NAME, PropertyDescriptor.createAccessor(filenameGetter, Undefined.instance, false, false)
         );
         JSDynamicObject moduleGetter = this.lookupFunction(
            GlobalBuiltins.GLOBAL_COMMONJS_REQUIRE_EXTENSIONS, GlobalCommonJSRequireBuiltins.GlobalRequire.globalModuleGetter.getName()
         );
         JSObject.defineOwnProperty(
            this.getGlobalObject(), Strings.MODULE_PROPERTY_NAME, PropertyDescriptor.createAccessor(moduleGetter, Undefined.instance, false, false)
         );
         JSDynamicObject exportsGetter = this.lookupFunction(
            GlobalBuiltins.GLOBAL_COMMONJS_REQUIRE_EXTENSIONS, GlobalCommonJSRequireBuiltins.GlobalRequire.globalExportsGetter.getName()
         );
         JSObject.defineOwnProperty(
            this.getGlobalObject(), Strings.EXPORTS_PROPERTY_NAME, PropertyDescriptor.createAccessor(exportsGetter, Undefined.instance, false, false)
         );
         this.commonJSRequireFunctionObject = requireFunction;
      }
   }

   private void addLoadGlobals() {
      if (this.getContext().getContextOptions().isLoad()) {
         this.putGlobalProperty(Strings.LOAD, this.lookupFunction(GlobalBuiltins.GLOBAL_LOAD, Strings.LOAD));
         this.putGlobalProperty(Strings.LOAD_WITH_NEW_GLOBAL, this.lookupFunction(GlobalBuiltins.GLOBAL_LOAD, Strings.LOAD_WITH_NEW_GLOBAL));
      }
   }

   private void addPerformanceGlobal() {
      if (this.context.getContextOptions().isPerformance()) {
         this.putGlobalProperty(PERFORMANCE_CLASS_NAME, this.preinitPerformanceObject != null ? this.preinitPerformanceObject : this.createPerformanceObject());
      }
   }

   public void addOptionalGlobals() {
      assert !this.getEnv().isPreInitialization();

      this.addGlobalGlobal();
      this.addShellGlobals();
      this.addScriptingGlobals();
      this.addIntlGlobal();
      this.addLoadGlobals();
      this.addConsoleGlobals();
      this.addPrintGlobals();
      this.addPerformanceGlobal();
      if (this.isJavaInteropEnabled()) {
         this.setupJavaInterop();
      }

      this.addCommonJSGlobals();
   }

   private void addGlobalGlobal() {
      if (this.getContext().getContextOptions().isGlobalProperty()) {
         this.putGlobalProperty(Strings.GLOBAL, this.getGlobalObject());
      }
   }

   private void addShellGlobals() {
      if (this.getContext().getContextOptions().isShell()) {
         GlobalBuiltins.GLOBAL_SHELL.forEachBuiltin(builtin -> {
            JSFunctionData functionData = builtin.createFunctionData(this.getContext());
            this.putGlobalProperty(builtin.getKey(), JSFunction.create(this, functionData), builtin.getAttributeFlags());
         });
      }
   }

   private void addIntlGlobal() {
      if (this.context.isOptionIntl402()) {
         this.putGlobalProperty(JSIntl.CLASS_NAME, this.preinitIntlObject != null ? this.preinitIntlObject : this.createIntlObject());
      }
   }

   private void addTemporalGlobals() {
      assert this.context.isOptionTemporal();

      JSObject temporalObject = JSOrdinary.createInit(this);
      JSObjectUtil.putToStringTag(temporalObject, TemporalConstants.TEMPORAL);
      int flags = JSAttributes.configurableNotEnumerableWritable();
      JSObjectUtil.putDataProperty(this.context, temporalObject, TemporalConstants.GLOBAL_PLAIN_TIME, this.getTemporalPlainTimeConstructor(), flags);
      JSObjectUtil.putDataProperty(this.context, temporalObject, TemporalConstants.GLOBAL_PLAIN_DATE, this.getTemporalPlainDateConstructor(), flags);
      JSObjectUtil.putDataProperty(this.context, temporalObject, TemporalConstants.GLOBAL_PLAIN_DATE_TIME, this.getTemporalPlainDateTimeConstructor(), flags);
      JSObjectUtil.putDataProperty(this.context, temporalObject, TemporalConstants.GLOBAL_DURATION, this.getTemporalDurationConstructor(), flags);
      JSObjectUtil.putDataProperty(this.context, temporalObject, TemporalConstants.GLOBAL_CALENDAR, this.getTemporalCalendarConstructor(), flags);
      JSObjectUtil.putDataProperty(this.context, temporalObject, TemporalConstants.GLOBAL_PLAIN_YEAR_MONTH, this.getTemporalPlainYearMonthConstructor(), flags);
      JSObjectUtil.putDataProperty(this.context, temporalObject, TemporalConstants.GLOBAL_PLAIN_MONTH_DAY, this.getTemporalPlainMonthDayConstructor(), flags);
      JSObjectUtil.putDataProperty(this.context, temporalObject, TemporalConstants.GLOBAL_INSTANT, this.getTemporalInstantConstructor(), flags);
      JSObjectUtil.putDataProperty(this.context, temporalObject, TemporalConstants.GLOBAL_TIME_ZONE, this.getTemporalTimeZoneConstructor(), flags);
      JSObjectUtil.putDataProperty(this.context, temporalObject, TemporalConstants.GLOBAL_ZONED_DATE_TIME, this.getTemporalZonedDateTimeConstructor(), flags);
      JSObject nowObject = JSOrdinary.createInit(this);
      JSObjectUtil.putDataProperty(this.context, temporalObject, TemporalConstants.NOW, nowObject, flags);
      JSObjectUtil.putFunctionsFromContainer(this, nowObject, TemporalNowBuiltins.BUILTINS);
      JSObjectUtil.putToStringTag(nowObject, TemporalConstants.GLOBAL_TEMPORAL_NOW);
      this.putGlobalProperty(TemporalConstants.TEMPORAL, temporalObject);
   }

   private JSDynamicObject createIntlObject() {
      JSObject intlObject = JSIntl.create(this);
      JSFunctionObject collatorFn = this.getCollatorConstructor();
      JSFunctionObject numberFormatFn = this.getNumberFormatConstructor();
      JSFunctionObject dateTimeFormatFn = this.getDateTimeFormatConstructor();
      JSFunctionObject pluralRulesFn = this.getPluralRulesConstructor();
      JSFunctionObject listFormatFn = this.getListFormatConstructor();
      JSFunctionObject relativeTimeFormatFn = this.getRelativeTimeFormatConstructor();
      JSFunctionObject segmenterFn = this.getSegmenterConstructor();
      JSFunctionObject displayNamesFn = this.getDisplayNamesConstructor();
      JSFunctionObject localeFn = this.getLocaleConstructor();
      JSObjectUtil.putDataProperty(this.context, intlObject, JSFunction.getName(collatorFn), collatorFn, JSAttributes.getDefaultNotEnumerable());
      JSObjectUtil.putDataProperty(this.context, intlObject, JSFunction.getName(numberFormatFn), numberFormatFn, JSAttributes.getDefaultNotEnumerable());
      JSObjectUtil.putDataProperty(this.context, intlObject, JSFunction.getName(dateTimeFormatFn), dateTimeFormatFn, JSAttributes.getDefaultNotEnumerable());
      JSObjectUtil.putDataProperty(this.context, intlObject, JSFunction.getName(pluralRulesFn), pluralRulesFn, JSAttributes.getDefaultNotEnumerable());
      JSObjectUtil.putDataProperty(this.context, intlObject, JSFunction.getName(listFormatFn), listFormatFn, JSAttributes.getDefaultNotEnumerable());
      JSObjectUtil.putDataProperty(
         this.context, intlObject, JSFunction.getName(relativeTimeFormatFn), relativeTimeFormatFn, JSAttributes.getDefaultNotEnumerable()
      );
      JSObjectUtil.putDataProperty(this.context, intlObject, JSFunction.getName(segmenterFn), segmenterFn, JSAttributes.getDefaultNotEnumerable());
      JSObjectUtil.putDataProperty(this.context, intlObject, JSFunction.getName(displayNamesFn), displayNamesFn, JSAttributes.getDefaultNotEnumerable());
      JSObjectUtil.putDataProperty(this.context, intlObject, JSFunction.getName(localeFn), localeFn, JSAttributes.getDefaultNotEnumerable());
      return intlObject;
   }

   private void putGraalObject() {
      JSObject graalObject = JSOrdinary.createInit(this);
      int flags = JSAttributes.notConfigurableEnumerableNotWritable();
      JSContextOptions options = this.getContext().getContextOptions();
      int esVersion = options.getEcmaScriptVersion();
      esVersion = esVersion > 6 ? esVersion + 2009 : esVersion;
      JSObjectUtil.putDataProperty(this.context, graalObject, Strings.LANGUAGE, Strings.fromJavaString("JavaScript"), flags);

      assert GRAALVM_VERSION != null;

      JSObjectUtil.putDataProperty(this.context, graalObject, Strings.VERSION_GRAAL_VM, GRAALVM_VERSION, flags);
      JSObjectUtil.putDataProperty(this.context, graalObject, Strings.VERSION_ECMA_SCRIPT, esVersion, flags);
      JSObjectUtil.putDataProperty(this.context, graalObject, Strings.IS_GRAAL_RUNTIME, JSFunction.create(this, isGraalRuntimeFunction(this.context)), flags);
      if (options.getUnhandledRejectionsMode() == JSContextOptions.UnhandledRejectionsTrackingMode.HANDLER) {
         JSFunctionObject registerFunction = JSFunction.create(this, setUnhandledPromiseRejectionHandlerFunction(this.context));
         JSObjectUtil.putDataProperty(this.context, graalObject, Strings.SET_UNHANDLED_PROMISE_REJECTION_HANDLER, registerFunction, flags);
      }

      this.putGlobalProperty(Strings.GRAAL, graalObject);
   }

   private static JSFunctionData setUnhandledPromiseRejectionHandlerFunction(JSContext context) {
      return context.getOrCreateBuiltinFunctionData(
         JSContext.BuiltinFunctionKey.SetUnhandledPromiseRejectionHandler,
         c -> JSFunctionData.createCallOnly(c, (new JavaScriptRootNode(c.getLanguage(), null, null) {
            @Override
            public Object execute(VirtualFrame frame) {
               Object[] args = frame.getArguments();
               Object handler = null;
               if (JSArguments.getUserArgumentCount(args) > 0) {
                  Object arg = JSArguments.getUserArgument(args, 0);
                  if (JSRuntime.isCallable(arg)) {
                     handler = arg;
                  } else if (!JSRuntime.isNullOrUndefined(arg)) {
                     throw Errors.createTypeError("Value provided for the unhandled promise rejection handler is not callable");
                  }
               }

               this.getRealm().unhandledPromiseRejectionHandler = handler;
               return Undefined.instance;
            }
         }).getCallTarget(), 0, Strings.SET_UNHANDLED_PROMISE_REJECTION_HANDLER)
      );
   }

   private static JSFunctionData isGraalRuntimeFunction(JSContext context) {
      return context.getOrCreateBuiltinFunctionData(
         JSContext.BuiltinFunctionKey.IsGraalRuntime, c -> JSFunctionData.createCallOnly(context, (new JavaScriptRootNode(context.getLanguage(), null, null) {
            @Override
            public Object execute(VirtualFrame frame) {
               return this.isGraalRuntime();
            }

            @CompilerDirectives.TruffleBoundary
            private boolean isGraalRuntime() {
               return Truffle.getRuntime().getName().contains("Graal");
            }
         }).getCallTarget(), 0, Strings.IS_GRAAL_RUNTIME)
      );
   }

   private void putGlobalProperty(TruffleString key, Object value) {
      this.putGlobalProperty(key, value, JSAttributes.getDefaultNotEnumerable());
   }

   private void putGlobalProperty(Object key, Object value, int attributes) {
      JSObjectUtil.putDataProperty(this.getContext(), this.getGlobalObject(), key, value, attributes);
   }

   private void putProperty(JSDynamicObject receiver, Object key, Object value) {
      JSObjectUtil.putDataProperty(this.getContext(), receiver, key, value, JSAttributes.getDefaultNotEnumerable());
   }

   private static void setupPredefinedSymbols(JSDynamicObject symbolFunction) {
      putSymbolProperty(symbolFunction, Strings.HAS_INSTANCE, Symbol.SYMBOL_HAS_INSTANCE);
      putSymbolProperty(symbolFunction, Strings.IS_CONCAT_SPREADABLE, Symbol.SYMBOL_IS_CONCAT_SPREADABLE);
      putSymbolProperty(symbolFunction, Strings.ITERATOR, Symbol.SYMBOL_ITERATOR);
      putSymbolProperty(symbolFunction, Strings.ASYNC_ITERATOR, Symbol.SYMBOL_ASYNC_ITERATOR);
      putSymbolProperty(symbolFunction, Strings.MATCH, Symbol.SYMBOL_MATCH);
      putSymbolProperty(symbolFunction, Strings.MATCH_ALL, Symbol.SYMBOL_MATCH_ALL);
      putSymbolProperty(symbolFunction, Strings.REPLACE, Symbol.SYMBOL_REPLACE);
      putSymbolProperty(symbolFunction, Strings.SEARCH, Symbol.SYMBOL_SEARCH);
      putSymbolProperty(symbolFunction, Strings.SPECIES, Symbol.SYMBOL_SPECIES);
      putSymbolProperty(symbolFunction, Strings.SPLIT, Symbol.SYMBOL_SPLIT);
      putSymbolProperty(symbolFunction, Strings.TO_STRING_TAG, Symbol.SYMBOL_TO_STRING_TAG);
      putSymbolProperty(symbolFunction, Strings.TO_PRIMITIVE, Symbol.SYMBOL_TO_PRIMITIVE);
      putSymbolProperty(symbolFunction, Strings.UNSCOPABLES, Symbol.SYMBOL_UNSCOPABLES);
   }

   private static void putSymbolProperty(JSDynamicObject symbolFunction, TruffleString name, Symbol symbol) {
      Properties.putConstantUncached(symbolFunction, name, symbol, JSAttributes.notConfigurableNotEnumerableNotWritable());
   }

   public boolean isJavaInteropEnabled() {
      return this.getEnv() != null && this.getEnv().isHostLookupAllowed();
   }

   private void setupJavaInterop() {
      assert this.isJavaInteropEnabled();

      JSObject java = JSObjectUtil.createOrdinaryPrototypeObject(this);
      JSObjectUtil.putToStringTag(java, JAVA_CLASS_NAME);
      JSObjectUtil.putFunctionsFromContainer(this, java, JavaBuiltins.BUILTINS);
      if (this.context.isOptionNashornCompatibilityMode()) {
         JSObjectUtil.putFunctionsFromContainer(this, java, JavaBuiltins.BUILTINS_NASHORN_COMPAT);
      }

      this.putGlobalProperty(JAVA_CLASS_NAME, java);
      if (this.getEnv() != null && this.getEnv().isHostLookupAllowed() && JSContextOptions.JAVA_PACKAGE_GLOBALS.getValue(this.getEnv().getOptions())) {
         this.javaPackageToPrimitiveFunction = JavaPackage.createToPrimitiveFunction(this.context, this);
         this.putGlobalProperty(Strings.UC_PACKAGES, JavaPackage.createInit(this, Strings.EMPTY_STRING));
         this.putGlobalProperty(Strings.JAVA, JavaPackage.createInit(this, Strings.JAVA));
         this.putGlobalProperty(Strings.JAVAFX, JavaPackage.createInit(this, Strings.JAVAFX));
         this.putGlobalProperty(Strings.JAVAX, JavaPackage.createInit(this, Strings.JAVAX));
         this.putGlobalProperty(Strings.COM, JavaPackage.createInit(this, Strings.COM));
         this.putGlobalProperty(Strings.ORG, JavaPackage.createInit(this, Strings.ORG));
         this.putGlobalProperty(Strings.EDU, JavaPackage.createInit(this, Strings.EDU));
         if (this.context.isOptionNashornCompatibilityMode()) {
            this.putGlobalProperty(JavaImporter.CLASS_NAME, this.getJavaImporterConstructor());
         }
      }
   }

   private void setupPolyglot() {
      JSObject polyglotObject = JSObjectUtil.createOrdinaryPrototypeObject(this);
      JSObjectUtil.putFunctionsFromContainer(this, polyglotObject, PolyglotBuiltins.BUILTINS);
      if (this.getContext().isOptionDebugBuiltin()) {
         JSObjectUtil.putFunctionsFromContainer(this, polyglotObject, PolyglotBuiltins.INTERNAL_BUILTINS);
      } else if (this.getContext().getContextOptions().isPolyglotEvalFile()) {
         JSObjectUtil.putDataProperty(
            this.context,
            polyglotObject,
            Strings.EVAL_FILE,
            this.lookupFunction(PolyglotBuiltins.INTERNAL_BUILTINS, Strings.EVAL_FILE),
            JSAttributes.getDefaultNotEnumerable()
         );
      }

      this.putGlobalProperty(POLYGLOT_CLASS_NAME, polyglotObject);
   }

   private void addConsoleGlobals() {
      if (this.context.getContextOptions().isConsole()) {
         this.putGlobalProperty(Strings.CONSOLE, this.preinitConsoleBuiltinObject != null ? this.preinitConsoleBuiltinObject : this.createConsoleObject());
      }
   }

   private JSDynamicObject createConsoleObject() {
      JSObject console = JSOrdinary.createInit(this);
      JSObjectUtil.putFunctionsFromContainer(this, console, ConsoleBuiltins.BUILTINS);
      return console;
   }

   private JSDynamicObject createPerformanceObject() {
      JSObject obj = JSOrdinary.createInit(this);
      JSObjectUtil.putFunctionsFromContainer(this, obj, PerformanceBuiltins.BUILTINS);
      return obj;
   }

   private JSDynamicObject createIteratorPrototype() {
      JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(this, this.getObjectPrototype());
      JSObjectUtil.putDataProperty(
         this.context, prototype, Symbol.SYMBOL_ITERATOR, createIteratorPrototypeSymbolIteratorFunction(this), JSAttributes.getDefaultNotEnumerable()
      );
      return prototype;
   }

   private static JSDynamicObject createIteratorPrototypeSymbolIteratorFunction(JSRealm realm) {
      return JSFunction.create(realm, realm.getContext().getSymbolIteratorThisGetterFunctionData());
   }

   private JSDynamicObject createArrayIteratorPrototype() {
      JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(this, this.iteratorPrototype);
      JSObjectUtil.putFunctionsFromContainer(this, prototype, ArrayIteratorPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putToStringTag(prototype, JSArray.ITERATOR_CLASS_NAME);
      return prototype;
   }

   private JSDynamicObject createSetIteratorPrototype() {
      JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(this, this.iteratorPrototype);
      JSObjectUtil.putFunctionsFromContainer(this, prototype, SetIteratorPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putToStringTag(prototype, JSSet.ITERATOR_CLASS_NAME);
      return prototype;
   }

   private JSDynamicObject createMapIteratorPrototype() {
      JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(this, this.iteratorPrototype);
      JSObjectUtil.putFunctionsFromContainer(this, prototype, MapIteratorPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putToStringTag(prototype, JSMap.ITERATOR_CLASS_NAME);
      return prototype;
   }

   private JSDynamicObject createStringIteratorPrototype() {
      JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(this, this.iteratorPrototype);
      JSObjectUtil.putFunctionsFromContainer(this, prototype, StringIteratorPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putToStringTag(prototype, JSString.ITERATOR_CLASS_NAME);
      return prototype;
   }

   private JSDynamicObject createRegExpStringIteratorPrototype() {
      JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(this, this.iteratorPrototype);
      JSObjectUtil.putFunctionsFromContainer(this, prototype, RegExpStringIteratorPrototypeBuiltins.BUILTINS);
      JSObjectUtil.putToStringTag(prototype, JSString.REGEXP_ITERATOR_CLASS_NAME);
      return prototype;
   }

   private JSDynamicObject createForeignIterablePrototype() {
      JSObject prototype = JSObjectUtil.createOrdinaryPrototypeObject(this);
      JSObjectUtil.putFunctionsFromContainer(this, prototype, ForeignIterablePrototypeBuiltins.BUILTINS);
      return prototype;
   }

   public JSDynamicObject getArrayProtoValuesIterator() {
      return this.arrayProtoValuesIterator;
   }

   private JSDynamicObject createReflect() {
      JSObject obj = JSObjectUtil.createOrdinaryPrototypeObject(this, this.getObjectPrototype());
      JSObjectUtil.putToStringTag(obj, REFLECT_CLASS_NAME);
      JSObjectUtil.putFunctionsFromContainer(this, obj, ReflectBuiltins.BUILTINS);
      return obj;
   }

   private JSDynamicObject createAtomics() {
      JSObject obj = JSObjectUtil.createOrdinaryPrototypeObject(this, this.getObjectPrototype());
      JSObjectUtil.putToStringTag(obj, ATOMICS_CLASS_NAME);
      JSObjectUtil.putFunctionsFromContainer(this, obj, AtomicsBuiltins.BUILTINS);
      return obj;
   }

   public final JSFunctionObject getCallSiteConstructor() {
      return this.callSiteConstructor;
   }

   public final JSDynamicObject getCallSitePrototype() {
      return this.callSitePrototype;
   }

   public final JSDynamicObject getGlobalScope() {
      return this.globalScope;
   }

   public JSDynamicObject getScriptEngineImportScope() {
      return this.scriptEngineImportScope;
   }

   public Object getTopScopeObject() {
      return this.topScope;
   }

   private void addScriptingGlobals() {
      CompilerAsserts.neverPartOfCompilation();
      if (this.getContext().getParserOptions().isScripting()) {
         String timezone = this.getLocalTimeZoneId().getId();
         JSDynamicObject timezoneObj = JSOrdinary.create(this.context, this);
         JSObjectUtil.putDataProperty(
            this.context, timezoneObj, Strings.CAPS_ID, Strings.fromJavaString(timezone), JSAttributes.configurableEnumerableWritable()
         );
         JSDynamicObject optionsObj = JSOrdinary.create(this.context, this);
         JSObjectUtil.putDataProperty(this.context, optionsObj, Strings._TIMEZONE, timezoneObj, JSAttributes.configurableEnumerableWritable());
         JSObjectUtil.putDataProperty(this.context, optionsObj, Strings._SCRIPTING, true, JSAttributes.configurableEnumerableWritable());
         JSObjectUtil.putDataProperty(this.context, optionsObj, Strings._COMPILE_ONLY, false, JSAttributes.configurableEnumerableWritable());
         this.putGlobalProperty(Strings.$_OPTIONS, optionsObj, JSAttributes.configurableNotEnumerableWritable());
         JSDynamicObject arguments = JSArray.createConstant(this.context, this, Strings.constantArray(this.getEnv().getApplicationArguments()));
         this.putGlobalProperty(Strings.$_ARG, arguments, JSAttributes.configurableNotEnumerableWritable());
         JSDynamicObject envObj = JSOrdinary.create(this.context, this);
         Map<String, String> sysenv = this.getEnv().getEnvironment();

         for (Entry<String, String> entry : sysenv.entrySet()) {
            JSObjectUtil.putDataProperty(
               this.context,
               envObj,
               Strings.fromJavaString(entry.getKey()),
               Strings.fromJavaString(entry.getValue()),
               JSAttributes.configurableEnumerableWritable()
            );
         }

         this.putGlobalProperty(Strings.DOLLAR_ENV, envObj, JSAttributes.configurableNotEnumerableWritable());
         this.putGlobalProperty(Strings.$_EXEC, this.lookupFunction(GlobalBuiltins.GLOBAL_NASHORN_EXTENSIONS, Strings.EXEC));
         this.putGlobalProperty(Strings.READ_FULLY, this.lookupFunction(GlobalBuiltins.GLOBAL_NASHORN_EXTENSIONS, Strings.READ_FULLY));
         this.putGlobalProperty(Strings.READ_LINE, this.lookupFunction(GlobalBuiltins.GLOBAL_NASHORN_EXTENSIONS, Strings.READ_LINE));
         this.putGlobalProperty(Strings.$_EXIT, Undefined.instance);
         this.putGlobalProperty(Strings.$_OUT, Undefined.instance);
         this.putGlobalProperty(Strings.$_ERR, Undefined.instance);
      }
   }

   public void setRealmBuiltinObject(JSDynamicObject realmBuiltinObject) {
      if (this.realmBuiltinObject == null && realmBuiltinObject != null) {
         this.realmBuiltinObject = realmBuiltinObject;
         this.putGlobalProperty(Strings.UC_REALM, realmBuiltinObject);
      }
   }

   public void initRealmBuiltinObject() {
      assert this.context.getContextOptions().isV8RealmBuiltin();

      this.setRealmBuiltinObject(this.createRealmBuiltinObject());
   }

   private JSObject createRealmBuiltinObject() {
      JSObject obj = JSOrdinary.createInit(this);
      JSObjectUtil.putToStringTag(obj, REALM_BUILTIN_CLASS_NAME);
      JSObjectUtil.putProxyProperty(obj, REALM_SHARED_NAME, REALM_SHARED_PROXY, JSAttributes.getDefault());
      JSObjectUtil.putFunctionsFromContainer(this, obj, RealmFunctionBuiltins.BUILTINS);
      return obj;
   }

   private JSObject createDebugObject() {
      JSObject obj = JSOrdinary.createInit(this);
      JSObjectUtil.putToStringTag(obj, DEBUG_CLASS_NAME);
      JSObjectUtil.putFunctionsFromContainer(this, obj, DebugBuiltins.BUILTINS);
      return obj;
   }

   private JSObject createMleObject() {
      JSObject obj = JSOrdinary.createInit(this);
      JSObjectUtil.putToStringTag(obj, MLE_CLASS_NAME);
      JSObjectUtil.putFunctionsFromContainer(this, obj, MLEBuiltins.BUILTINS);
      return obj;
   }

   private void addStaticRegexResultProperties() {
      if (this.context.isOptionRegexpStaticResultInContextInit()) {
         if (this.context.isOptionNashornCompatibilityMode()) {
            this.putRegExpStaticPropertyAccessor(null, Strings.INPUT);
            this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExpMultiLine, Strings.MULTILINE);
            this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExpLastMatch, Strings.LAST_MATCH);
            this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExpLastParen, Strings.LAST_PAREN);
            this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExpLeftContext, Strings.LEFT_CONTEXT);
            this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExpRightContext, Strings.RIGHT_CONTEXT);
         } else {
            this.putRegExpStaticPropertyAccessor(null, Strings.INPUT);
            this.putRegExpStaticPropertyAccessor(null, Strings.INPUT, Strings.$_);
            this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExpLastMatch, Strings.LAST_MATCH);
            this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExpLastMatch, Strings.LAST_MATCH, Strings.$_AMPERSAND);
            this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExpLastParen, Strings.LAST_PAREN);
            this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExpLastParen, Strings.LAST_PAREN, Strings.$_PLUS);
            this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExpLeftContext, Strings.LEFT_CONTEXT);
            this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExpLeftContext, Strings.LEFT_CONTEXT, Strings.$_BACKTICK);
            this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExpRightContext, Strings.RIGHT_CONTEXT);
            this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExpRightContext, Strings.RIGHT_CONTEXT, Strings.$_SQUOT);
         }

         this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExp$1, Strings.$_1);
         this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExp$2, Strings.$_2);
         this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExp$3, Strings.$_3);
         this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExp$4, Strings.$_4);
         this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExp$5, Strings.$_5);
         this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExp$6, Strings.$_6);
         this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExp$7, Strings.$_7);
         this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExp$8, Strings.$_8);
         this.putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey.RegExp$9, Strings.$_9);
      }
   }

   private void putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey builtinKey, TruffleString getterName) {
      this.putRegExpStaticPropertyAccessor(builtinKey, getterName, getterName);
   }

   private void putRegExpStaticPropertyAccessor(JSContext.BuiltinFunctionKey builtinKey, TruffleString getterName, TruffleString propertyName) {
      Pair<JSBuiltin, JSBuiltin> pair = RegExpBuiltins.BUILTINS.lookupAccessorByKey(getterName);
      JSBuiltin getterBuiltin = pair.getLeft();
      JSDynamicObject getter = JSFunction.create(this, getterBuiltin.createFunctionData(this.context));
      JSBuiltin setterBuiltin = pair.getRight();
      JSDynamicObject setter;
      if (setterBuiltin != null) {
         assert Strings.equals(propertyName, Strings.INPUT) || Strings.equals(propertyName, Strings.$_);

         setter = JSFunction.create(this, setterBuiltin.createFunctionData(this.context));
      } else if (this.context.isOptionV8CompatibilityModeInContextInit()) {
         TruffleString setterName = Strings.concat(Strings.SET_SPC, getterName);
         JSFunctionData setterData = this.context
            .getOrCreateBuiltinFunctionData(builtinKey, c -> JSFunctionData.createCallOnly(c, this.context.getEmptyFunctionCallTarget(), 1, setterName));
         setter = JSFunction.create(this, setterData);
      } else {
         setter = Undefined.instance;
      }

      int propertyAttributes = this.context.isOptionNashornCompatibilityMode()
         ? JSAttributes.notConfigurableEnumerableWritable()
         : JSAttributes.configurableNotEnumerableWritable();
      JSObjectUtil.putBuiltinAccessorProperty(this.regExpConstructor, propertyName, getter, setter, propertyAttributes);
   }

   public void setArguments(TruffleString[] arguments) {
      JSObjectUtil.defineDataProperty(
         this.context,
         this.getGlobalObject(),
         ARGUMENTS_NAME,
         JSArray.createConstant(this.context, this, arguments),
         this.context.isOptionV8CompatibilityModeInContextInit() ? JSAttributes.getDefault() : JSAttributes.getDefaultNotEnumerable()
      );
   }

   public final JSDynamicObject getOrdinaryHasInstanceFunction() {
      return this.ordinaryHasInstanceFunction;
   }

   public final JSFunctionObject getJSAdapterConstructor() {
      return this.jsAdapterConstructor;
   }

   public final JSDynamicObject getJSAdapterPrototype() {
      return this.jsAdapterPrototype;
   }

   public final TruffleLanguage.Env getEnv() {
      return this.truffleLanguageEnv;
   }

   public boolean patchContext(TruffleLanguage.Env newEnv) {
      CompilerAsserts.neverPartOfCompilation();
      Objects.requireNonNull(newEnv, "New env cannot be null.");
      this.truffleLanguageEnv = newEnv;
      this.getContext().setAllocationReporter(newEnv);
      this.getContext().getContextOptions().setOptionValues(newEnv.getOptions());
      this.setOutputStreamsFromEnv(newEnv);
      this.addOptionalGlobals();
      this.addArgumentsFromEnv(newEnv);
      if (this.localTimeZoneId != null) {
         this.localTimeZoneId = this.getTimeZoneFromEnv();
      }

      this.initTimeOffsetAndRandom();
      this.addStaticRegexResultProperties();
      this.getContext().resetSymbolUsageMarker();
      return true;
   }

   public void initialize() {
      CompilerAsserts.neverPartOfCompilation();
      if (this.getEnv().isPreInitialization()) {
         this.preinitializeObjects();
      } else {
         this.setOutputStreamsFromEnv(this.getEnv());
         this.addOptionalGlobals();
         this.addArgumentsFromEnv(this.getEnv());
         this.initTimeOffsetAndRandom();
         this.addStaticRegexResultProperties();
      }
   }

   private void setOutputStreamsFromEnv(TruffleLanguage.Env newEnv) {
      if (newEnv.out() != this.outputWriter.getDelegate()) {
         this.setOutputWriter(newEnv.out());
      }

      if (newEnv.err() != this.errorWriter.getDelegate()) {
         this.setErrorWriter(newEnv.err());
      }
   }

   private void preinitializeObjects() {
      this.preinitIntlObject = this.createIntlObject();
      this.preinitConsoleBuiltinObject = this.createConsoleObject();
      this.preinitPerformanceObject = this.createPerformanceObject();
   }

   private void addArgumentsFromEnv(TruffleLanguage.Env newEnv) {
      String[] applicationArguments = newEnv.getApplicationArguments();
      if (this.context.getContextOptions().isGlobalArguments()) {
         TruffleString[] args = new TruffleString[applicationArguments.length];

         for (int i = 0; i < args.length; i++) {
            args[i] = Strings.fromJavaString(applicationArguments[i]);
         }

         this.setArguments(args);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public JSRealm createChildRealm() {
      JSRealm childRealm = this.context.createRealm(this.getEnv(), this);
      childRealm.initialize();
      return childRealm;
   }

   public boolean isPreparingStackTrace() {
      return this.preparingStackTrace;
   }

   public void setPreparingStackTrace(boolean preparingStackTrace) {
      this.preparingStackTrace = preparingStackTrace;
   }

   public final TruffleContext getTruffleContext() {
      return this.getEnv().getContext();
   }

   public final Object getEmbedderData() {
      return this.embedderData;
   }

   public final void setEmbedderData(Object embedderData) {
      this.embedderData = embedderData;
   }

   public Object getStaticRegexResult(JSContext ctx, TRegexUtil.TRegexCompiledRegexAccessor compiledRegexAccessor) {
      CompilerAsserts.partialEvaluationConstant(ctx);

      assert ctx.isOptionRegexpStaticResult();

      if (this.staticRegexResultCompiledRegex != null && ctx.getRegExpStaticResultUnusedAssumption().isValid()) {
         ctx.getRegExpStaticResultUnusedAssumption().invalidate();
         this.staticRegexResult = compiledRegexAccessor.exec(
            this.staticRegexResultCompiledRegex, this.staticRegexResultOriginalInputString, this.staticRegexResultFromIndex
         );
      }

      if (this.staticRegexResult == null) {
         this.staticRegexResult = ctx.getTRegexEmptyResult();
      }

      return this.staticRegexResult;
   }

   public void setStaticRegexResult(JSContext ctx, Object compiledRegex, TruffleString input, long fromIndex, Object result) {
      CompilerAsserts.partialEvaluationConstant(ctx);

      assert ctx.isOptionRegexpStaticResult();

      this.staticRegexResultInvalidated = false;
      this.staticRegexResultCompiledRegex = compiledRegex;
      this.staticRegexResultInputString = input;
      this.staticRegexResultOriginalInputString = input;
      if (ctx.getRegExpStaticResultUnusedAssumption().isValid()) {
         this.staticRegexResultFromIndex = fromIndex;
      } else {
         assert TRegexUtil.InteropReadBooleanMemberNode.getUncached().execute(result, "isMatch");

         this.staticRegexResult = result;
      }
   }

   public void invalidateStaticRegexResult() {
      this.staticRegexResultInvalidated = true;
   }

   public boolean isRegexResultInvalidated() {
      return this.staticRegexResultInvalidated;
   }

   public Object getStaticRegexResultCompiledRegex() {
      return this.staticRegexResultCompiledRegex;
   }

   public TruffleString getStaticRegexResultInputString() {
      return this.staticRegexResultInputString;
   }

   public void setStaticRegexResultInputString(TruffleString inputString) {
      this.staticRegexResultInputString = inputString;
   }

   public TruffleString getStaticRegexResultOriginalInputString() {
      return this.staticRegexResultOriginalInputString;
   }

   public OptionValues getOptions() {
      return this.getEnv().getOptions();
   }

   public final PrintWriter getOutputWriter() {
      return this.outputWriter;
   }

   public final PrintWriter getErrorWriter() {
      return this.errorWriter;
   }

   private void setOutputWriter(OutputStream stream) {
      this.outputWriter.setDelegate(stream);
   }

   private void setErrorWriter(OutputStream stream) {
      this.errorWriter.setDelegate(stream);
   }

   public long nanoTime() {
      return this.nanoTime(this.nanoToZeroTimeOffset);
   }

   public long nanoTimeWallClock() {
      return this.nanoTime(this.nanoToCurrentTimeOffset);
   }

   public long nanoTime(long offset) {
      long ns = System.nanoTime() + offset;
      long resolution = this.getContext().getTimerResolution();
      if (resolution > 0L) {
         return ns / resolution * resolution;
      } else {
         long fuzz = this.random.nextLong(1000000L) + 1L;
         ns -= ns % fuzz;
         long last = this.lastFuzzyTime;
         if (ns > last) {
            this.lastFuzzyTime = ns;
            return ns;
         } else {
            return last;
         }
      }
   }

   public long currentTimeMillis() {
      return this.nanoTime(this.nanoToCurrentTimeOffset) / 1000000L;
   }

   public JSConsoleUtil getConsoleUtil() {
      return this.consoleUtil;
   }

   public JSModuleLoader getModuleLoader() {
      if (this.moduleLoader == null) {
         this.createModuleLoader();
      }

      return this.moduleLoader;
   }

   @CompilerDirectives.TruffleBoundary
   private synchronized void createModuleLoader() {
      if (this.moduleLoader == null) {
         if (this.context.getContextOptions().isCommonJSRequire()) {
            this.moduleLoader = NpmCompatibleESModuleLoader.create(this);
         } else {
            this.moduleLoader = DefaultESModuleLoader.create(this);
         }
      }
   }

   public final JSAgent getAgent() {
      assert this.agent != null;

      return this.agent;
   }

   public void setAgent(JSAgent newAgent) {
      assert newAgent != null : "Cannot set a null agent!";

      CompilerAsserts.neverPartOfCompilation("Assigning agent to context in compiled code");
      this.agent = newAgent;
   }

   public TimeZone getLocalTimeZone() {
      TimeZone timeZone = this.localTimeZone;
      if (CompilerDirectives.injectBranchProbability(1.0E-4, timeZone == null)) {
         timeZone = this.getICUTimeZoneFromEnv();
      }

      return timeZone;
   }

   @CompilerDirectives.TruffleBoundary
   private TimeZone getICUTimeZoneFromEnv() {
      return IntlUtil.getICUTimeZone(this.getLocalTimeZoneId(), this.getContext());
   }

   public ZoneId getLocalTimeZoneId() {
      ZoneId id = this.localTimeZoneId;
      if (CompilerDirectives.injectBranchProbability(1.0E-4, id == null)) {
         id = this.getTimeZoneFromEnv();
         this.localTimeZoneId = id;
      }

      return id;
   }

   @CompilerDirectives.TruffleBoundary
   private ZoneId getTimeZoneFromEnv() {
      OptionValues options = this.getEnv().getOptions();
      String zoneId = JSContextOptions.TIME_ZONE.getValue(options);
      if (!zoneId.isEmpty()) {
         try {
            return ZoneId.of(zoneId);
         } catch (DateTimeException var4) {
            throw CompilerDirectives.shouldNotReachHere(var4);
         }
      } else {
         return this.getEnv().getTimeZone();
      }
   }

   private void initTimeOffsetAndRandom() {
      assert !this.getEnv().isPreInitialization();

      this.random = new SplittableRandom();
      this.nanoToZeroTimeOffset = -System.nanoTime();
      this.nanoToCurrentTimeOffset = System.currentTimeMillis() * 1000000L + this.nanoToZeroTimeOffset;
      this.lastFuzzyTime = Long.MIN_VALUE;
   }

   public final SplittableRandom getRandom() {
      return this.random;
   }

   public JSRealm getParent() {
      return this.parentRealm;
   }

   public boolean isMainRealm() {
      return this.getParent() == null;
   }

   public JavaScriptBaseNode getCallNode() {
      return this.callNode;
   }

   public void setCallNode(JavaScriptBaseNode callNode) {
      this.callNode = callNode;
   }

   void initRealmList() {
      CompilerAsserts.neverPartOfCompilation();

      assert this.isMainRealm();

      this.realmList = new ArrayList<>();
   }

   void addToRealmList(JSRealm newRealm) {
      CompilerAsserts.neverPartOfCompilation();

      assert this.isMainRealm();

      assert !this.realmList.contains(newRealm);

      this.realmList.add(newRealm);
   }

   public JSRealm getFromRealmList(int idx) {
      CompilerAsserts.neverPartOfCompilation();

      assert this.isMainRealm();

      return 0 <= idx && idx < this.realmList.size() ? this.realmList.get(idx) : null;
   }

   public void setInRealmList(int idx, JSRealm realm) {
      CompilerAsserts.neverPartOfCompilation();

      assert this.isMainRealm();

      this.realmList.set(idx, realm);
   }

   public int getIndexFromRealmList(JSRealm rlm) {
      CompilerAsserts.neverPartOfCompilation();

      assert this.isMainRealm();

      return this.realmList.indexOf(rlm);
   }

   public void removeFromRealmList(int idx) {
      CompilerAsserts.neverPartOfCompilation();

      assert this.isMainRealm();

      this.realmList.set(idx, null);
   }

   public JSRealm getCurrentV8Realm() {
      assert this.isMainRealm();

      return this.v8RealmCurrent;
   }

   public void setCurrentV8Realm(JSRealm realm) {
      assert this.isMainRealm();

      this.v8RealmCurrent = realm;
   }

   public void registerCustomEsmPathMappingCallback(Object callback) {
      assert this.context.isOptionMleBuiltin();

      assert JSRuntime.isCallableForeign(callback);

      this.customEsmPathMappingCallback = callback;
   }

   public TruffleString getCustomEsmPathMapping(TruffleString refPath, TruffleString specifier) {
      CompilerAsserts.neverPartOfCompilation();
      if (this.getContext().isOptionMleBuiltin() && this.customEsmPathMappingCallback != null) {
         Object[] args = new Object[]{JSRuntime.toJSNull(refPath), specifier};
         Object custom = JSInteropUtil.call(this.customEsmPathMappingCallback, args);
         InteropLibrary interopLibrary = InteropLibrary.getUncached();
         if (interopLibrary.isString(custom)) {
            try {
               return interopLibrary.asTruffleString(custom);
            } catch (UnsupportedMessageException var7) {
               throw Errors.shouldNotReachHere(var7);
            }
         } else {
            throw Errors.createError("Cannot load ES module: " + specifier);
         }
      } else {
         return null;
      }
   }

   public boolean joinStackPush(Object o, BranchProfile growProfile) {
      InteropLibrary interop = o instanceof JSObject ? null : InteropLibrary.getFactory().getUncached(o);

      for (int i = 0; i < this.joinStack.size(); i++) {
         Object element = this.joinStack.get(i);
         if (interop == null ? o == element : interop.isIdentical(o, element, InteropLibrary.getFactory().getUncached(element))) {
            return false;
         }
      }

      this.joinStack.add(o, growProfile);
      return true;
   }

   public void joinStackPop() {
      this.joinStack.pop();
   }

   public final Map<TruffleFile, JSDynamicObject> getCommonJSRequireCache() {
      assert this.context.getContextOptions().isCommonJSRequire();

      return this.commonJSRequireCache;
   }

   private boolean isWasmAvailable() {
      return this.truffleLanguageEnv.isPolyglotBindingsAccessAllowed() && this.truffleLanguageEnv.getInternalLanguages().get("wasm") != null;
   }

   public Object getWASMModuleDecode() {
      return this.wasmModuleDecode;
   }

   public Object getWASMModuleInstantiate() {
      return this.wasmModuleInstantiate;
   }

   public Object getWASMModuleValidate() {
      return this.wasmModuleValidate;
   }

   public Object getWASMModuleExports() {
      return this.wasmModuleExports;
   }

   public Object getWASMModuleImports() {
      return this.wasmModuleImports;
   }

   public Object getWASMCustomSections() {
      return this.wasmCustomSections;
   }

   public Object getWASMTableAlloc() {
      return this.wasmTableAlloc;
   }

   public Object getWASMTableGrow() {
      return this.wasmTableGrow;
   }

   public Object getWASMTableRead() {
      return this.wasmTableRead;
   }

   public Object getWASMTableWrite() {
      return this.wasmTableWrite;
   }

   public Object getWASMTableLength() {
      return this.wasmTableLength;
   }

   public Object getWASMFuncType() {
      return this.wasmFuncType;
   }

   public Object getWASMMemAlloc() {
      return this.wasmMemAlloc;
   }

   public Object getWASMMemGrow() {
      return this.wasmMemGrow;
   }

   public Object getWASMGlobalAlloc() {
      return this.wasmGlobalAlloc;
   }

   public Object getWASMGlobalRead() {
      return this.wasmGlobalRead;
   }

   public Object getWASMGlobalWrite() {
      return this.wasmGlobalWrite;
   }

   public Object getWASMInstanceExport() {
      return this.wasmInstanceExport;
   }

   public Object getWASMEmbedderDataGet() {
      return this.wasmEmbedderDataGet;
   }

   public Object getWASMEmbedderDataSet() {
      return this.wasmEmbedderDataSet;
   }

   public Object getWASMMemAsByteBuffer() {
      return this.wasmMemAsByteBuffer;
   }

   public JSDynamicObject getWebAssemblyModulePrototype() {
      return this.webAssemblyModulePrototype;
   }

   public JSDynamicObject getWebAssemblyInstancePrototype() {
      return this.webAssemblyInstancePrototype;
   }

   public JSDynamicObject getWebAssemblyMemoryPrototype() {
      return this.webAssemblyMemoryPrototype;
   }

   public JSDynamicObject getWebAssemblyTablePrototype() {
      return this.webAssemblyTablePrototype;
   }

   public JSDynamicObject getWebAssemblyGlobalPrototype() {
      return this.webAssemblyGlobalPrototype;
   }

   public JSDynamicObject getForeignIterablePrototype() {
      return this.foreignIterablePrototype;
   }

   public JSWebAssemblyMemoryGrowCallback getWebAssemblyMemoryGrowCallback() {
      return this.webAssemblyMemoryGrowCallback;
   }

   public DateFormat getJSDateISOFormat(double time) {
      long milliseconds = (long)time;
      if (milliseconds < -62167219200000L) {
         if (CompilerDirectives.injectBranchProbability(1.0E-4, this.jsDateFormatBeforeYear0 == null)) {
            this.enterOncePerContextBranch();
            this.jsDateFormatBeforeYear0 = this.createDateFormat("uuuuuu-MM-dd'T'HH:mm:ss.SSS'Z'", false);
         }

         return this.jsDateFormatBeforeYear0;
      } else if (milliseconds >= 253402300800000L) {
         if (CompilerDirectives.injectBranchProbability(1.0E-4, this.jsDateFormatAfterYear9999 == null)) {
            this.enterOncePerContextBranch();
            this.jsDateFormatAfterYear9999 = this.createDateFormat("+uuuuuu-MM-dd'T'HH:mm:ss.SSS'Z'", false);
         }

         return this.jsDateFormatAfterYear9999;
      } else {
         if (CompilerDirectives.injectBranchProbability(1.0E-4, this.jsDateFormat == null)) {
            this.enterOncePerContextBranch();
            this.jsDateFormat = this.createDateFormat("uuuu-MM-dd'T'HH:mm:ss.SSS'Z'", false);
         }

         return this.jsDateFormat;
      }
   }

   public DateFormat getJSDateUTCFormat() {
      DateFormat dateFormat = this.jsDateFormatISO;
      if (CompilerDirectives.injectBranchProbability(1.0E-4, dateFormat == null)) {
         this.enterOncePerContextBranch();
         this.jsDateFormatISO = dateFormat = this.createDateFormat("EEE, dd MMM uuuu HH:mm:ss 'GMT'", false);
      }

      return dateFormat;
   }

   public DateFormat getJSShortDateFormat() {
      DateFormat dateFormat = this.jsShortDateFormat;
      if (CompilerDirectives.injectBranchProbability(1.0E-4, dateFormat == null)) {
         this.jsShortDateFormat = dateFormat = this.createDateFormat("EEE MMM dd uuuu", true);
      }

      return dateFormat;
   }

   public DateFormat getJSShortDateLocalFormat() {
      DateFormat dateFormat = this.jsShortDateLocalFormat;
      if (CompilerDirectives.injectBranchProbability(1.0E-4, dateFormat == null)) {
         this.jsShortDateLocalFormat = dateFormat = this.createDateFormat("uuuu-MM-dd", true);
      }

      return dateFormat;
   }

   public DateFormat getJSShortTimeFormat() {
      DateFormat dateFormat = this.jsShortTimeFormat;
      if (CompilerDirectives.injectBranchProbability(1.0E-4, dateFormat == null)) {
         this.jsShortTimeFormat = dateFormat = this.createDateFormat(this.appendTimeZoneNameFormat("HH:mm:ss 'GMT'xx"), true);
      }

      return dateFormat;
   }

   public DateFormat getJSShortTimeLocalFormat() {
      DateFormat dateFormat = this.jsShortTimeLocalFormat;
      if (CompilerDirectives.injectBranchProbability(1.0E-4, dateFormat == null)) {
         this.jsShortTimeLocalFormat = dateFormat = this.createDateFormat("HH:mm:ss", true);
      }

      return dateFormat;
   }

   public DateFormat getDateToStringFormat() {
      DateFormat dateFormat = this.jsDateToStringFormat;
      if (CompilerDirectives.injectBranchProbability(1.0E-4, dateFormat == null)) {
         this.jsDateToStringFormat = dateFormat = this.createDateFormat(this.appendTimeZoneNameFormat("EEE MMM dd uuuu HH:mm:ss 'GMT'xx"), true);
      }

      return dateFormat;
   }

   @CompilerDirectives.TruffleBoundary
   private String appendTimeZoneNameFormat(String format) {
      String timeZoneNameFormat = this.getContext().isOptionV8CompatibilityMode() ? "zzzz" : "z";
      return format + " (" + timeZoneNameFormat + ")";
   }

   @CompilerDirectives.TruffleBoundary
   private DateFormat createDateFormat(String pattern, boolean local) {
      SimpleDateFormat format = new SimpleDateFormat(pattern, Locale.US);
      format.setTimeZone(local ? this.getLocalTimeZone() : TimeZone.GMT_ZONE);
      if (!pattern.contains("zzzz")) {
         TimeZoneFormat tzFormat = format.getTimeZoneFormat().cloneAsThawed();
         tzFormat.setTimeZoneNames(TimeZoneNames.getTZDBInstance(ULocale.US));
         format.setTimeZoneFormat(tzFormat);
      }

      Calendar calendar = format.getCalendar();
      if (calendar instanceof GregorianCalendar) {
         ((GregorianCalendar)calendar).setGregorianChange(new Date(Long.MIN_VALUE));
      }

      return format;
   }

   @CompilerDirectives.TruffleBoundary
   public void setLocalTimeZone(String tzId) {
      ZoneId newZoneId;
      TimeZone newTimeZone;
      try {
         if (tzId != null) {
            newZoneId = ZoneId.of(tzId);
            newTimeZone = IntlUtil.getICUTimeZone(tzId, this.getContext());
         } else {
            newZoneId = null;
            newTimeZone = null;
         }
      } catch (DateTimeException var5) {
         return;
      }

      this.localTimeZoneId = newZoneId;
      this.localTimeZone = newTimeZone;
      this.jsDateToStringFormat = null;
      this.jsShortTimeFormat = null;
      this.jsShortTimeLocalFormat = null;
      this.jsShortDateFormat = null;
      this.jsShortDateLocalFormat = null;
   }

   private void enterOncePerContextBranch() {
      if (CompilerDirectives.isPartialEvaluationConstant(this)) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
      }
   }

   public long nextAsyncEvaluationOrder() {
      return ++this.lastAsyncEvaluationOrder;
   }

   @CompilerDirectives.TruffleBoundary
   public void putCachedCompiledRegex(Source regexSource, Object compiledRegex) {
      int regexCacheSize = this.context.getContextOptions().getRegexCacheSize();
      if (regexCacheSize > 0) {
         if (this.compiledRegexCache == null) {
            this.compiledRegexCache = new LRUCache<>(regexCacheSize);
         }

         this.compiledRegexCache.put(regexSource, compiledRegex);
      }
   }

   @CompilerDirectives.TruffleBoundary
   public Object getCachedCompiledRegex(Source regexSource) {
      int regexCacheSize = this.context.getContextOptions().getRegexCacheSize();
      return regexCacheSize > 0 && this.compiledRegexCache != null ? this.compiledRegexCache.get(regexSource) : null;
   }

   public void storeParentPromise(JSDynamicObject promise) {
      this.parentPromise = promise;
   }

   public JSDynamicObject fetchParentPromise() {
      JSDynamicObject parent = this.parentPromise;
      if (parent == null) {
         parent = Undefined.instance;
      } else {
         this.parentPromise = null;
      }

      return parent;
   }

   private static final class RealmSharedPropertyProxy extends PropertyProxy {
      @Override
      public Object get(JSDynamicObject store) {
         return topLevelRealm().v8RealmShared;
      }

      @Override
      public boolean set(JSDynamicObject store, Object value) {
         topLevelRealm().v8RealmShared = value;
         return true;
      }

      private static JSRealm topLevelRealm() {
         return JSRealm.getMain(null);
      }
   }
}
