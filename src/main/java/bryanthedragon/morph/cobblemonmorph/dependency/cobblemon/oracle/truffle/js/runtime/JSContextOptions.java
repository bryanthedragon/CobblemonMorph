package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Option;
import com.oracle.truffle.api.nodes.InvalidAssumptionException;
import com.oracle.truffle.api.utilities.CyclicAssumption;
import java.nio.charset.Charset;
import java.time.DateTimeException;
import java.time.ZoneId;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Function;
import org.graalvm.options.OptionCategory;
import org.graalvm.options.OptionDescriptor;
import org.graalvm.options.OptionKey;
import org.graalvm.options.OptionStability;
import org.graalvm.options.OptionType;
import org.graalvm.options.OptionValues;

public final class JSContextOptions {
   public static final String JS_OPTION_PREFIX = "js.";
   @CompilerDirectives.CompilationFinal
   private JSParserOptions parserOptions;
   @CompilerDirectives.CompilationFinal
   private OptionValues optionValues;
   public static final String ECMASCRIPT_VERSION_LATEST = "latest";
   public static final String ECMASCRIPT_VERSION_STAGING = "staging";
   public static final String ECMASCRIPT_VERSION_NAME = "js.ecmascript-version";
   @Option(
      name = "js.ecmascript-version",
      category = OptionCategory.USER,
      stability = OptionStability.STABLE,
      usageSyntax = "latest|staging|[5, 13]|[2015, 2022]",
      help = "ECMAScript version to be compatible with. Default is 'latest' (latest supported version), staged features are in 'staging'."
   )
   public static final OptionKey<Integer> ECMASCRIPT_VERSION = new OptionKey<>(13, new OptionType<>("ecmascript-version", new Function<String, Integer>() {
      public Integer apply(String in) {
         if ("latest".equals(in)) {
            return 13;
         } else if ("staging".equals(in)) {
            return 14;
         } else {
            try {
               int minVersion = 5;
               int maxVersion = 13;
               int minYearVersion = 2015;
               int maxYearVersion = 2022;
               int version = Integer.parseInt(in);
               if (2015 <= version && version <= 2022) {
                  version -= 2009;
               }

               if (version >= 5 && version <= 13) {
                  return version;
               } else {
                  throw new IllegalArgumentException("Supported values are 5 to 13 or 2015 to 2022.");
               }
            } catch (NumberFormatException var7) {
               throw new IllegalArgumentException(var7.getMessage(), var7);
            }
         }
      }
   }));
   @CompilerDirectives.CompilationFinal
   private int ecmascriptVersion;
   public static final String ANNEX_B_NAME = "js.annex-b";
   @Option(name = "js.annex-b", category = OptionCategory.USER, help = "Enable ECMAScript Annex B features.")
   public static final OptionKey<Boolean> ANNEX_B = new OptionKey<>(true);
   @CompilerDirectives.CompilationFinal
   private boolean annexB;
   public static final String SYNTAX_EXTENSIONS_NAME = "js.syntax-extensions";
   @Option(name = "js.syntax-extensions", category = OptionCategory.USER, help = "Enable Nashorn syntax extensions.")
   public static final OptionKey<Boolean> SYNTAX_EXTENSIONS = new OptionKey<>(false);
   public static final String SCRIPTING_NAME = "js.scripting";
   @Option(name = "js.scripting", category = OptionCategory.USER, help = "Enable scripting features (Nashorn compatibility option).")
   public static final OptionKey<Boolean> SCRIPTING = new OptionKey<>(false);
   public static final String SHEBANG_NAME = "js.shebang";
   @Option(name = "js.shebang", category = OptionCategory.USER, help = "Allow parsing files starting with #!.")
   public static final OptionKey<Boolean> SHEBANG = new OptionKey<>(false);
   public static final String STRICT_NAME = "js.strict";
   @Option(name = "js.strict", category = OptionCategory.USER, stability = OptionStability.STABLE, help = "Enforce strict mode.")
   public static final OptionKey<Boolean> STRICT = new OptionKey<>(false);
   public static final String CONST_AS_VAR_NAME = "js.const-as-var";
   @Option(name = "js.const-as-var", category = OptionCategory.EXPERT, help = "Parse const declarations as a var (legacy compatibility option).")
   public static final OptionKey<Boolean> CONST_AS_VAR = new OptionKey<>(false);
   public static final String FUNCTION_STATEMENT_ERROR_NAME = "js.function-statement-error";
   @Option(
      name = "js.function-statement-error",
      category = OptionCategory.EXPERT,
      help = "Treat hoistable function statements in blocks as an error (in ES5 mode)."
   )
   public static final OptionKey<Boolean> FUNCTION_STATEMENT_ERROR = new OptionKey<>(false);
   public static final String INTL_402_NAME = "js.intl-402";
   @Option(name = "js.intl-402", category = OptionCategory.USER, stability = OptionStability.STABLE, help = "Enable ECMAScript Internationalization API.")
   public static final OptionKey<Boolean> INTL_402 = new OptionKey<>(true);
   @CompilerDirectives.CompilationFinal
   private boolean intl402;
   public static final String REGEXP_MATCH_INDICES_NAME = "js.regexp-match-indices";
   @Option(name = "js.regexp-match-indices", category = OptionCategory.USER, help = "Enable RegExp Match Indices property.", deprecated = true)
   public static final OptionKey<Boolean> REGEXP_MATCH_INDICES = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean regexpMatchIndices;
   public static final String REGEXP_STATIC_RESULT_NAME = "js.regexp-static-result";
   @Option(name = "js.regexp-static-result", category = OptionCategory.USER, help = "Provide last RegExp match in RegExp global var, e.g. RegExp.$1.")
   public static final OptionKey<Boolean> REGEXP_STATIC_RESULT = new OptionKey<>(true);
   private final CyclicAssumption regexpStaticResultCyclicAssumption = new CyclicAssumption("The js.regexp-static-result option is stable.");
   @CompilerDirectives.CompilationFinal
   private Assumption regexpStaticResultCurrentAssumption = this.regexpStaticResultCyclicAssumption.getAssumption();
   @CompilerDirectives.CompilationFinal
   private boolean regexpStaticResult;
   public static final String SHARED_ARRAY_BUFFER_NAME = "js.shared-array-buffer";
   @Option(name = "js.shared-array-buffer", category = OptionCategory.USER, help = "Enable ECMAScript SharedArrayBuffer.")
   public static final OptionKey<Boolean> SHARED_ARRAY_BUFFER = new OptionKey<>(true);
   @CompilerDirectives.CompilationFinal
   private boolean sharedArrayBuffer;
   public static final String ATOMICS_NAME = "js.atomics";
   @Option(name = "js.atomics", category = OptionCategory.USER, help = "Enable ECMAScript Atomics.")
   public static final OptionKey<Boolean> ATOMICS = new OptionKey<>(true);
   public static final String V8_COMPATIBILITY_MODE_NAME = "js.v8-compat";
   @Option(name = "js.v8-compat", category = OptionCategory.USER, help = "Provide compatibility with the Google V8 engine.")
   public static final OptionKey<Boolean> V8_COMPATIBILITY_MODE = new OptionKey<>(false);
   private final CyclicAssumption v8CompatibilityModeCyclicAssumption = new CyclicAssumption("The js.v8-compat option is stable.");
   @CompilerDirectives.CompilationFinal
   private Assumption v8CompatibilityModeCurrentAssumption = this.v8CompatibilityModeCyclicAssumption.getAssumption();
   @CompilerDirectives.CompilationFinal
   private boolean v8CompatibilityMode;
   public static final String V8_REALM_BUILTIN_NAME = "js.v8-realm-builtin";
   @Option(name = "js.v8-realm-builtin", category = OptionCategory.INTERNAL, help = "Provide Realm builtin compatible with V8's d8 shell.")
   public static final OptionKey<Boolean> V8_REALM_BUILTIN = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean v8RealmBuiltin;
   public static final String V8_LEGACY_CONST_NAME = "js.v8-legacy-const";
   @Option(
      name = "js.v8-legacy-const",
      category = OptionCategory.INTERNAL,
      help = "Emulate v8 behavior when trying to mutate const variables in non-strict mode."
   )
   public static final OptionKey<Boolean> V8_LEGACY_CONST = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean v8LegacyConst;
   public static final String NASHORN_COMPATIBILITY_MODE_NAME = "js.nashorn-compat";
   @Option(
      name = "js.nashorn-compat",
      category = OptionCategory.USER,
      help = "Provide compatibility with the OpenJDK Nashorn engine. Do not use with untrusted code."
   )
   public static final OptionKey<Boolean> NASHORN_COMPATIBILITY_MODE = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean nashornCompatibilityMode;
   public static final String STACK_TRACE_LIMIT_NAME = "js.stack-trace-limit";
   @Option(name = "js.stack-trace-limit", category = OptionCategory.USER, usageSyntax = "[0, inf)", help = "Number of stack frames to capture.")
   public static final OptionKey<Integer> STACK_TRACE_LIMIT = new OptionKey<>(10);
   @CompilerDirectives.CompilationFinal
   private int stackTraceLimit;
   public static final String DEBUG_BUILTIN_NAME = "js.debug-builtin";
   @Option(
      name = "js.debug-builtin",
      category = OptionCategory.INTERNAL,
      help = "Provide a non-API Debug builtin. Behaviour will likely change. Don't depend on this in production code."
   )
   public static final OptionKey<Boolean> DEBUG_BUILTIN = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean debug;
   public static final String DIRECT_BYTE_BUFFER_NAME = "js.direct-byte-buffer";
   @Option(name = "js.direct-byte-buffer", category = OptionCategory.USER, help = "Use direct (off-heap) byte buffer for typed arrays.")
   public static final OptionKey<Boolean> DIRECT_BYTE_BUFFER = new OptionKey<>(false);
   private final CyclicAssumption directByteBufferCyclicAssumption = new CyclicAssumption("The js.direct-byte-buffer option is stable.");
   @CompilerDirectives.CompilationFinal
   private Assumption directByteBufferCurrentAssumption = this.directByteBufferCyclicAssumption.getAssumption();
   @CompilerDirectives.CompilationFinal
   private boolean directByteBuffer;
   public static final String PARSE_ONLY_NAME = "js.parse-only";
   @Option(name = "js.parse-only", category = OptionCategory.INTERNAL, help = "Only parse source code, do not run it.")
   public static final OptionKey<Boolean> PARSE_ONLY = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean parseOnly;
   public static final String TIME_ZONE_NAME = "js.timezone";
   @Option(name = "js.timezone", category = OptionCategory.USER, usageSyntax = "<TimeZoneID>", help = "Set custom time zone ID.")
   public static final OptionKey<String> TIME_ZONE = new OptionKey<>("", new OptionType<>("ZoneId", new Function<String, String>() {
      public String apply(String tz) {
         if (tz.isEmpty()) {
            return "";
         } else {
            try {
               return ZoneId.of(tz, ZoneId.SHORT_IDS).getId();
            } catch (DateTimeException var3) {
               throw new IllegalArgumentException(var3);
            }
         }
      }
   }));
   public static final String ZONE_RULES_BASED_TIME_ZONES_NAME = "js.zone-rules-based-time-zones";
   @Option(name = "js.zone-rules-based-time-zones", category = OptionCategory.EXPERT, help = "Use ZoneRulesProvider instead of time-zone data from ICU4J.")
   public static final OptionKey<Boolean> ZONE_RULES_BASED_TIME_ZONES = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean zoneRulesBasedTimeZones;
   public static final String TIMER_RESOLUTION_NAME = "js.timer-resolution";
   @Option(
      name = "js.timer-resolution",
      category = OptionCategory.USER,
      usageSyntax = "<nanoseconds>",
      help = "Resolution of timers (performance.now() and Date built-ins) in nanoseconds. Fuzzy time is used when set to 0."
   )
   public static final OptionKey<Long> TIMER_RESOLUTION = new OptionKey<>(1000000L);
   private final CyclicAssumption timerResolutionCyclicAssumption = new CyclicAssumption("The js.timer-resolution option is stable.");
   @CompilerDirectives.CompilationFinal
   private Assumption timerResolutionCurrentAssumption = this.timerResolutionCyclicAssumption.getAssumption();
   @CompilerDirectives.CompilationFinal
   private long timerResolution;
   public static final String AGENT_CAN_BLOCK_NAME = "js.agent-can-block";
   @Option(name = "js.agent-can-block", category = OptionCategory.INTERNAL, help = "Determines whether agents can block or not.")
   public static final OptionKey<Boolean> AGENT_CAN_BLOCK = new OptionKey<>(true);
   @CompilerDirectives.CompilationFinal
   private boolean agentCanBlock;
   public static final String JAVA_PACKAGE_GLOBALS_NAME = "js.java-package-globals";
   @Option(
      name = "js.java-package-globals",
      category = OptionCategory.USER,
      help = "Provide Java package globals: Packages, java, javafx, javax, com, org, edu."
   )
   public static final OptionKey<Boolean> JAVA_PACKAGE_GLOBALS = new OptionKey<>(true);
   public static final String GLOBAL_PROPERTY_NAME = "js.global-property";
   @Option(name = "js.global-property", category = OptionCategory.USER, help = "Provide 'global' global property.")
   public static final OptionKey<Boolean> GLOBAL_PROPERTY = new OptionKey<>(false);
   public static final String GLOBAL_ARGUMENTS_NAME = "js.global-arguments";
   @Option(name = "js.global-arguments", category = OptionCategory.USER, help = "Provide 'arguments' global property.")
   public static final OptionKey<Boolean> GLOBAL_ARGUMENTS = new OptionKey<>(true);
   public static final String CONSOLE_NAME = "js.console";
   @Option(name = "js.console", category = OptionCategory.USER, help = "Provide 'console' global property.")
   public static final OptionKey<Boolean> CONSOLE = new OptionKey<>(true);
   public static final String PERFORMANCE_NAME = "js.performance";
   @Option(name = "js.performance", category = OptionCategory.USER, help = "Provide 'performance' global property.")
   public static final OptionKey<Boolean> PERFORMANCE = new OptionKey<>(false);
   public static final String SHELL_NAME = "js.shell";
   @Option(name = "js.shell", category = OptionCategory.USER, help = "Provide global functions for js shell.")
   public static final OptionKey<Boolean> SHELL = new OptionKey<>(false);
   public static final String PRINT_NAME = "js.print";
   @Option(name = "js.print", category = OptionCategory.USER, help = "Provide 'print' global function.")
   public static final OptionKey<Boolean> PRINT = new OptionKey<>(true);
   public static final String PRINT_NO_NEWLINE_NAME = "js.print-no-newline";
   @Option(name = "js.print-no-newline", category = OptionCategory.USER, help = "Print function will not print new line char.")
   public static final OptionKey<Boolean> PRINT_NO_NEWLINE = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean printNoNewline;
   public static final String LOAD_NAME = "js.load";
   @Option(name = "js.load", category = OptionCategory.USER, help = "Provide 'load' global function.")
   public static final OptionKey<Boolean> LOAD = new OptionKey<>(true);
   public static final String LOAD_FROM_URL_NAME = "js.load-from-url";
   @Option(name = "js.load-from-url", category = OptionCategory.USER, help = "Allow 'load' to access URLs. Do not use with untrusted code.")
   public static final OptionKey<Boolean> LOAD_FROM_URL = new OptionKey<>(false);
   public static final String LOAD_FROM_CLASSPATH_NAME = "js.load-from-classpath";
   @Option(name = "js.load-from-classpath", category = OptionCategory.USER, help = "Allow 'load' to access 'classpath:' URLs. Do not use with untrusted code.")
   public static final OptionKey<Boolean> LOAD_FROM_CLASSPATH = new OptionKey<>(false);
   public static final String COMMONJS_REQUIRE_NAME = "js.commonjs-require";
   @Option(name = "js.commonjs-require", category = OptionCategory.USER, help = "Enable CommonJS require emulation.")
   public static final OptionKey<Boolean> COMMONJS_REQUIRE = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean commonJSRequire;
   public static final String COMMONJS_REQUIRE_CWD_NAME = "js.commonjs-require-cwd";
   @Option(name = "js.commonjs-require-cwd", category = OptionCategory.USER, usageSyntax = "<path>", help = "CommonJS default current working directory.")
   public static final OptionKey<String> COMMONJS_REQUIRE_CWD = new OptionKey<>("");
   public static final String COMMONJS_CORE_MODULES_REPLACEMENTS_NAME = "js.commonjs-core-modules-replacements";
   @Option(
      name = "js.commonjs-core-modules-replacements",
      category = OptionCategory.USER,
      usageSyntax = "<name>:<module>,...",
      help = "Npm packages used to replace global Node.js builtins."
   )
   public static final OptionKey<Map<String, String>> COMMONJS_CORE_MODULES_REPLACEMENTS = new OptionKey<>(
      Collections.emptyMap(), new OptionType<>("commonjs-require-globals", new Function<String, Map<String, String>>() {
         public Map<String, String> apply(String value) {
            Map<String, String> map = new HashMap<>();
            if ("".equals(value)) {
               return map;
            } else {
               String[] options = value.split(",");

               for (String s : options) {
                  String[] builtin = s.split(":", 2);
                  if (builtin.length != 2) {
                     throw new IllegalArgumentException("Unexpected builtin arguments: " + s);
                  }

                  String key = builtin[0];
                  String val = builtin[1];
                  map.put(key, val);
               }

               return map;
            }
         }
      })
   );
   public static final String GRAAL_BUILTIN_NAME = "js.graal-builtin";
   @Option(name = "js.graal-builtin", category = OptionCategory.USER, help = "Provide 'Graal' global property.")
   public static final OptionKey<Boolean> GRAAL_BUILTIN = new OptionKey<>(true);
   public static final String POLYGLOT_BUILTIN_NAME = "js.polyglot-builtin";
   @Option(name = "js.polyglot-builtin", category = OptionCategory.USER, help = "Provide 'Polyglot' global property.", deprecated = true)
   public static final OptionKey<Boolean> POLYGLOT_BUILTIN = new OptionKey<>(true);
   public static final String POLYGLOT_EVALFILE_NAME = "js.polyglot-evalfile";
   @Option(name = "js.polyglot-evalfile", category = OptionCategory.USER, help = "Provide 'Polyglot.evalFile' function.")
   public static final OptionKey<Boolean> POLYGLOT_EVALFILE = new OptionKey<>(true);
   public static final String AWAIT_OPTIMIZATION_NAME = "js.await-optimization";
   @Option(name = "js.await-optimization", category = OptionCategory.INTERNAL, help = "Use PromiseResolve for Await.")
   public static final OptionKey<Boolean> AWAIT_OPTIMIZATION = new OptionKey<>(true);
   @CompilerDirectives.CompilationFinal
   private boolean awaitOptimization;
   public static final String DISABLE_EVAL_NAME = "js.disable-eval";
   @Option(name = "js.disable-eval", category = OptionCategory.EXPERT, help = "User code is not allowed to parse code via e.g. eval().")
   public static final OptionKey<Boolean> DISABLE_EVAL = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean disableEval;
   public static final String DISABLE_WITH_NAME = "js.disable-with";
   @Option(name = "js.disable-with", category = OptionCategory.EXPERT, help = "User code is not allowed to use the 'with' statement.")
   public static final OptionKey<Boolean> DISABLE_WITH = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean disableWith;
   public static final String BIGINT_NAME = "js.bigint";
   @Option(name = "js.bigint", category = OptionCategory.USER, help = "Provide an implementation of the BigInt proposal.")
   public static final OptionKey<Boolean> BIGINT = new OptionKey<>(true);
   public static final String CLASS_FIELDS_NAME = "js.class-fields";
   @Option(name = "js.class-fields", category = OptionCategory.USER, help = "Enable the class public and private fields proposal.")
   public static final OptionKey<Boolean> CLASS_FIELDS = new OptionKey<>(false);
   public static final int CLASS_FIELDS_ES_VERSION = 12;
   public static final String REGEX_DUMP_AUTOMATA_NAME = "js.regex.dump-automata";
   @Option(name = "js.regex.dump-automata", category = OptionCategory.INTERNAL, help = "Produce ASTs and automata in JSON, DOT (GraphViz) and LaTeX formats.")
   public static final OptionKey<Boolean> REGEX_DUMP_AUTOMATA = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean regexDumpAutomata;
   public static final String REGEX_STEP_EXECUTION_NAME = "js.regex.step-execution";
   @Option(name = "js.regex.step-execution", category = OptionCategory.INTERNAL, help = "Trace the execution of automata in JSON files.")
   public static final OptionKey<Boolean> REGEX_STEP_EXECUTION = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean regexStepExecution;
   public static final String REGEX_ALWAYS_EAGER_NAME = "js.regex.always-eager";
   @Option(name = "js.regex.always-eager", category = OptionCategory.INTERNAL, help = "Always match capture groups eagerly.")
   public static final OptionKey<Boolean> REGEX_ALWAYS_EAGER = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean regexAlwaysEager;
   public static final String SCRIPT_ENGINE_GLOBAL_SCOPE_IMPORT_NAME = "js.script-engine-global-scope-import";
   @Option(
      name = "js.script-engine-global-scope-import",
      deprecated = true,
      stability = OptionStability.STABLE,
      category = OptionCategory.INTERNAL,
      help = "Enable ScriptEngine-specific global scope import function."
   )
   public static final OptionKey<Boolean> SCRIPT_ENGINE_GLOBAL_SCOPE_IMPORT = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean scriptEngineGlobalScopeImport;
   public static final String FOREIGN_OBJECT_PROTOTYPE_NAME = "js.foreign-object-prototype";
   @Option(
      name = "js.foreign-object-prototype",
      category = OptionCategory.EXPERT,
      stability = OptionStability.STABLE,
      help = "Non-JS objects have prototype (Object/Function/Array.prototype) set."
   )
   public static final OptionKey<Boolean> FOREIGN_OBJECT_PROTOTYPE = new OptionKey<>(true);
   @CompilerDirectives.CompilationFinal
   private boolean hasForeignObjectPrototype;
   public static final String FOREIGN_HASH_PROPERTIES_NAME = "js.foreign-hash-properties";
   @Option(
      name = "js.foreign-hash-properties",
      category = OptionCategory.EXPERT,
      help = "Allow getting/setting non-JS hash entries using the `[]` and `.` operators."
   )
   public static final OptionKey<Boolean> FOREIGN_HASH_PROPERTIES = new OptionKey<>(true);
   @CompilerDirectives.CompilationFinal
   private boolean hasForeignHashProperties;
   public static final String FUNCTION_ARGUMENTS_LIMIT_NAME = "js.function-arguments-limit";
   @Option(name = "js.function-arguments-limit", category = OptionCategory.EXPERT, usageSyntax = "<int>", help = "Maximum number of arguments for functions.")
   public static final OptionKey<Long> FUNCTION_ARGUMENTS_LIMIT = new OptionKey<>(65535L);
   @CompilerDirectives.CompilationFinal
   private long functionArgumentsLimit;
   public static final String TEST262_MODE_NAME = "js.test262-mode";
   @Option(name = "js.test262-mode", category = OptionCategory.INTERNAL, help = "Expose global property $262 needed to run the Test262 harness.")
   public static final OptionKey<Boolean> TEST262_MODE = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean test262Mode;
   public static final String TESTV8_MODE_NAME = "js.testV8-mode";
   @Option(name = "js.testV8-mode", category = OptionCategory.INTERNAL, help = "Expose internals needed to run the TestV8 harness.")
   public static final OptionKey<Boolean> TESTV8_MODE = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean testV8Mode;
   public static final String VALIDATE_REGEXP_LITERALS_NAME = "js.validate-regexp-literals";
   @Option(name = "js.validate-regexp-literals", category = OptionCategory.INTERNAL, help = "Validate regexp literals at parse time.")
   public static final OptionKey<Boolean> VALIDATE_REGEXP_LITERALS = new OptionKey<>(true);
   @CompilerDirectives.CompilationFinal
   private boolean validateRegExpLiterals;
   public static final String LOCALE_NAME = "js.locale";
   @Option(
      name = "js.locale",
      category = OptionCategory.EXPERT,
      usageSyntax = "<locale>",
      help = "Use a specific default locale for locale-sensitive operations."
   )
   public static final OptionKey<String> LOCALE = new OptionKey<>("");
   public static final String FUNCTION_CONSTRUCTOR_CACHE_SIZE_NAME = "js.function-constructor-cache-size";
   @Option(
      name = "js.function-constructor-cache-size",
      category = OptionCategory.EXPERT,
      usageSyntax = "<int>",
      help = "Maximum size of the parsing cache used by the Function constructor to avoid re-parsing known sources."
   )
   public static final OptionKey<Integer> FUNCTION_CONSTRUCTOR_CACHE_SIZE = new OptionKey<>(256);
   @CompilerDirectives.CompilationFinal
   private int functionConstructorCacheSize;
   public static final String REGEX_CACHE_SIZE_NAME = "js.regex-cache-size";
   @Option(
      name = "js.regex-cache-size",
      category = OptionCategory.EXPERT,
      usageSyntax = "<int>",
      help = "Maximum size of the regex cache used by the RegExp constructor to avoid re-parsing known sources."
   )
   public static final OptionKey<Integer> REGEX_CACHE_SIZE = new OptionKey<>(128);
   @CompilerDirectives.CompilationFinal
   private int regexCacheSize;
   public static final String STRING_LENGTH_LIMIT_NAME = "js.string-length-limit";
   @Option(name = "js.string-length-limit", category = OptionCategory.EXPERT, usageSyntax = "<chars>", help = "Maximum string length.")
   public static final OptionKey<Integer> STRING_LENGTH_LIMIT = new OptionKey<>(1073741799);
   @CompilerDirectives.CompilationFinal
   private int stringLengthLimit;
   public static final String STRING_LAZY_SUBSTRINGS_NAME = "js.string-lazy-substrings";
   @Option(name = "js.string-lazy-substrings", category = OptionCategory.EXPERT, help = "Allow lazy substrings.")
   public static final OptionKey<Boolean> STRING_LAZY_SUBSTRINGS = new OptionKey<>(true);
   @CompilerDirectives.CompilationFinal
   private boolean stringLazySubstrings;
   public static final String BIND_MEMBER_FUNCTIONS_NAME = "js.bind-member-functions";
   @Option(name = "js.bind-member-functions", category = OptionCategory.EXPERT, help = "Bind functions returned by Value.getMember to the receiver object.")
   public static final OptionKey<Boolean> BIND_MEMBER_FUNCTIONS = new OptionKey<>(true);
   @CompilerDirectives.CompilationFinal
   private boolean bindMemberFunctions;
   public static final String REGEX_REGRESSION_TEST_MODE_NAME = "js.regex-regression-test-mode";
   @Option(name = "js.regex-regression-test-mode", category = OptionCategory.INTERNAL, help = "Test mode for TRegex.")
   public static final OptionKey<Boolean> REGEX_REGRESSION_TEST_MODE = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean regexRegressionTestMode;
   public static final String INTEROP_COMPLETE_PROMISES_NAME = "js.interop-complete-promises";
   @Option(name = "js.interop-complete-promises", category = OptionCategory.EXPERT, help = "Resolve promises when crossing a polyglot language boundary.")
   public static final OptionKey<Boolean> INTEROP_COMPLETE_PROMISES = new OptionKey<>(false);
   public static final String DEBUG_PROPERTY_NAME_NAME = "js.debug-property-name";
   @Option(name = "js.debug-property-name", category = OptionCategory.EXPERT, usageSyntax = "<name>", help = "The name used for the Graal.js debug builtin.")
   public static final OptionKey<String> DEBUG_PROPERTY_NAME = new OptionKey<>(Strings.toJavaString(JSRealm.DEBUG_CLASS_NAME));
   public static final String PROFILE_TIME_NAME = "js.profile-time";
   @Option(name = "js.profile-time", category = OptionCategory.INTERNAL, help = "Enable time profiling.")
   public static final OptionKey<Boolean> PROFILE_TIME = new OptionKey<>(false);
   public static final String PROFILE_TIME_PRINT_CUMULATIVE_NAME = "js.profile-time-print-cumulative";
   @Option(name = "js.profile-time-print-cumulative", category = OptionCategory.INTERNAL, help = "Print cumulative time when time profiling is enabled.")
   public static final OptionKey<Boolean> PROFILE_TIME_PRINT_CUMULATIVE = new OptionKey<>(false);
   public static final String TEST_CLONE_UNINITIALIZED_NAME = "js.test-clone-uninitialized";
   @Option(name = "js.test-clone-uninitialized", category = OptionCategory.INTERNAL, help = "Test uninitialized cloning.")
   public static final OptionKey<Boolean> TEST_CLONE_UNINITIALIZED = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean testCloneUninitialized;
   public static final String LAZY_TRANSLATION_NAME = "js.lazy-translation";
   @Option(name = "js.lazy-translation", category = OptionCategory.INTERNAL, help = "Translate function bodies lazily.")
   public static final OptionKey<Boolean> LAZY_TRANSLATION = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean lazyTranslation;
   public static final String MAX_TYPED_ARRAY_LENGTH_NAME = "js.max-typed-array-length";
   @Option(name = "js.max-typed-array-length", category = OptionCategory.EXPERT, usageSyntax = "<int>", help = "Maximum allowed length for TypedArrays.")
   public static final OptionKey<Integer> MAX_TYPED_ARRAY_LENGTH = new OptionKey<>(1073741823);
   @CompilerDirectives.CompilationFinal
   private int maxTypedArrayLength;
   public static final String MAX_APPLY_ARGUMENT_LENGTH_NAME = "js.max-apply-argument-length";
   @Option(
      name = "js.max-apply-argument-length",
      category = OptionCategory.EXPERT,
      usageSyntax = "<int>",
      help = "Maximum allowed number of arguments allowed in an apply function."
   )
   public static final OptionKey<Integer> MAX_APPLY_ARGUMENT_LENGTH = new OptionKey<>(10000000);
   @CompilerDirectives.CompilationFinal
   private int maxApplyArgumentLength;
   public static final String MAX_PROTOTYPE_CHAIN_LENGTH_NAME = "js.max-prototype-chain-length";
   @Option(
      name = "js.max-prototype-chain-length",
      category = OptionCategory.EXPERT,
      usageSyntax = "<int>",
      help = "Maximum allowed length of a prototype chain."
   )
   public static final OptionKey<Integer> MAX_PROTOTYPE_CHAIN_LENGTH = new OptionKey<>(32766);
   @CompilerDirectives.CompilationFinal
   private int maxPrototypeChainLength;
   public static final String ASYNC_STACK_TRACES_NAME = "js.async-stack-traces";
   @Option(name = "js.async-stack-traces", category = OptionCategory.EXPERT, help = "Include async function frames in stack traces.")
   public static final OptionKey<Boolean> ASYNC_STACK_TRACES = new OptionKey<>(true);
   @CompilerDirectives.CompilationFinal
   private boolean asyncStackTraces;
   public static final String PROPERTY_CACHE_LIMIT_NAME = "js.property-cache-limit";
   @Option(name = "js.property-cache-limit", category = OptionCategory.INTERNAL, usageSyntax = "<int>", help = "Maximum allowed size of a property cache.")
   public static final OptionKey<Integer> PROPERTY_CACHE_LIMIT = new OptionKey<>(5);
   @CompilerDirectives.CompilationFinal
   private int propertyCacheLimit;
   public static final String FUNCTION_CACHE_LIMIT_NAME = "js.function-cache-limit";
   @Option(name = "js.function-cache-limit", category = OptionCategory.INTERNAL, usageSyntax = "<int>", help = "Maximum allowed size of a function cache.")
   public static final OptionKey<Integer> FUNCTION_CACHE_LIMIT = new OptionKey<>(4);
   @CompilerDirectives.CompilationFinal
   private int functionCacheLimit;
   public static final String TOP_LEVEL_AWAIT_NAME = "js.top-level-await";
   @Option(name = "js.top-level-await", category = OptionCategory.EXPERT, help = "Enable top-level-await.")
   protected static final OptionKey<Boolean> TOP_LEVEL_AWAIT = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean topLevelAwait;
   public static final String USE_UTC_FOR_LEGACY_DATES_NAME = "js.use-utc-for-legacy-dates";
   @Option(
      name = "js.use-utc-for-legacy-dates",
      category = OptionCategory.EXPERT,
      stability = OptionStability.STABLE,
      help = "Determines what time zone (UTC or local time zone) should be used when UTC offset is absent in a parsed date."
   )
   public static final OptionKey<Boolean> USE_UTC_FOR_LEGACY_DATES = new OptionKey<>(true);
   @CompilerDirectives.CompilationFinal
   private boolean useUTCForLegacyDates;
   public static final String WEBASSEMBLY_NAME = "js.webassembly";
   @Option(name = "js.webassembly", category = OptionCategory.EXPERT, help = "Enable WebAssembly JavaScript API.")
   public static final OptionKey<Boolean> WEBASSEMBLY = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean webAssembly;
   public static final String NEW_SET_METHODS_NAME = "js.new-set-methods";
   @Option(name = "js.new-set-methods", category = OptionCategory.EXPERT, help = "Enable new Set methods.")
   public static final OptionKey<Boolean> NEW_SET_METHODS = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean newSetMethods;
   public static final String TEMPORAL_NAME = "js.temporal";
   @Option(name = "js.temporal", category = OptionCategory.EXPERT, help = "Enable JavaScript Temporal API.")
   public static final OptionKey<Boolean> TEMPORAL = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean temporal;
   public static final String UNHANDLED_REJECTIONS_NAME = "js.unhandled-rejections";
   @Option(
      name = "js.unhandled-rejections",
      category = OptionCategory.USER,
      help = "Configure unhandled promise rejections tracking. Accepted values: 'none', unhandled rejections are not tracked. 'warn', a warning is printed to stderr when an unhandled rejection is detected. 'throw', an exception is thrown when an unhandled rejection is detected. 'handler', the handler function set with Graal.setUnhandledPromiseRejectionHandler will be called with the rejection value and promise respectively as arguments."
   )
   public static final OptionKey<JSContextOptions.UnhandledRejectionsTrackingMode> UNHANDLED_REJECTIONS = new OptionKey<>(
      JSContextOptions.UnhandledRejectionsTrackingMode.NONE
   );
   @CompilerDirectives.CompilationFinal
   private JSContextOptions.UnhandledRejectionsTrackingMode unhandledRejectionsMode;
   public static final String OPERATOR_OVERLOADING_NAME = "js.operator-overloading";
   @Option(name = "js.operator-overloading", category = OptionCategory.USER, help = "Enable operator overloading")
   public static final OptionKey<Boolean> OPERATOR_OVERLOADING = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean operatorOverloading;
   public static final String ERROR_CAUSE_NAME = "js.error-cause";
   @Option(
      name = "js.error-cause",
      category = OptionCategory.EXPERT,
      help = "Enable the error cause proposal. Allows an error to be chained with a cause using the optional options parameter."
   )
   public static final OptionKey<Boolean> ERROR_CAUSE = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean errorCause;
   public static final String IMPORT_ASSERTIONS_NAME = "js.import-assertions";
   @Option(name = "js.import-assertions", category = OptionCategory.USER, help = "Enable import assertions")
   public static final OptionKey<Boolean> IMPORT_ASSERTIONS = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean importAssertions;
   public static final String JSON_MODULES_NAME = "js.json-modules";
   @Option(name = "js.json-modules", category = OptionCategory.USER, help = "Enable loading of json modules")
   public static final OptionKey<Boolean> JSON_MODULES = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean jsonModules;
   public static final String WASM_BIG_INT_NAME = "js.wasm-bigint";
   @Option(name = "js.wasm-bigint", category = OptionCategory.USER, help = "Enable wasm i64 to javascript BigInt support")
   public static final OptionKey<Boolean> WASM_BIG_INT = new OptionKey<>(true);
   @CompilerDirectives.CompilationFinal
   private boolean wasmBigInt;
   public static final String ESM_EVAL_RETURNS_EXPORTS_NAME = "js.esm-eval-returns-exports";
   @Option(
      name = "js.esm-eval-returns-exports",
      category = OptionCategory.EXPERT,
      help = "Eval of an ES module through the polyglot API returns its exported symbols."
   )
   public static final OptionKey<Boolean> ESM_EVAL_RETURNS_EXPORTS = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean esmEvalReturnsExports;
   public static final String MLE_MODE_NAME = "js.mle-mode";
   @Option(
      name = "js.mle-mode",
      category = OptionCategory.INTERNAL,
      help = "Provide a non-API MLE builtin. Behaviour will likely change. Don't depend on this in production code."
   )
   public static final OptionKey<Boolean> MLE_MODE = new OptionKey<>(false);
   public static final String MLE_PROPERTY_NAME = "MLE";
   @CompilerDirectives.CompilationFinal
   private boolean mleMode;
   public static final String PRIVATE_FIELDS_IN_NAME = "js.private-fields-in";
   @Option(name = "js.private-fields-in", category = OptionCategory.USER, help = "Enable private field in in operator")
   public static final OptionKey<Boolean> PRIVATE_FIELDS_IN = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean privateFieldsIn;
   public static final String ESM_BARE_SPECIFIER_RELATIVE_LOOKUP_NAME = "js.esm-bare-specifier-relative-lookup";
   @Option(
      name = "js.esm-bare-specifier-relative-lookup",
      category = OptionCategory.EXPERT,
      help = "Resolve ESM bare specifiers relative to the importing module's path instead of attempting an absolute path lookup."
   )
   public static final OptionKey<Boolean> ESM_BARE_SPECIFIER_RELATIVE_LOOKUP = new OptionKey<>(false);
   @CompilerDirectives.CompilationFinal
   private boolean esmBareSpecifierRelativeLookup;
   public static final String CHARSET_NAME = "js.charset";
   @Option(
      name = "js.charset",
      category = OptionCategory.EXPERT,
      usageSyntax = "UTF-8|UTF-32|<name>",
      help = "Charset used for decoding/encoding of the input/output streams."
   )
   public static final OptionKey<String> CHARSET = new OptionKey<>("", new OptionType<>("CharsetName", new Function<String, String>() {
      public String apply(String name) {
         if (name.isEmpty()) {
            return "";
         } else {
            try {
               return Charset.forName(name).name();
            } catch (Exception var3) {
               throw new IllegalArgumentException(var3);
            }
         }
      }
   }));
   public static final String SCOPE_OPTIMIZATION_NAME = "js.scope-optimization";
   @Option(name = "js.scope-optimization", category = OptionCategory.INTERNAL, help = "Allow scope optimizations around closures.")
   public static final OptionKey<Boolean> SCOPE_OPTIMIZATION = new OptionKey<>(true);
   @CompilerDirectives.CompilationFinal
   private boolean scopeOptimization;

   JSContextOptions(JSParserOptions parserOptions, OptionValues optionValues) {
      this.parserOptions = parserOptions;
      this.optionValues = optionValues;
      this.setOptionValues(optionValues);
   }

   public static JSContextOptions fromOptionValues(OptionValues optionValues) {
      return new JSContextOptions(new JSParserOptions(), optionValues);
   }

   public JSParserOptions getParserOptions() {
      return this.parserOptions;
   }

   public void setParserOptions(JSParserOptions parserOptions) {
      CompilerAsserts.neverPartOfCompilation();
      this.parserOptions = parserOptions;
   }

   public void setOptionValues(OptionValues newOptions) {
      CompilerAsserts.neverPartOfCompilation();
      this.optionValues = newOptions;
      this.cacheOptions();
      this.parserOptions = this.parserOptions.putOptions(newOptions);
   }

   private void cacheOptions() {
      this.nashornCompatibilityMode = this.readBooleanOption(NASHORN_COMPATIBILITY_MODE);
      this.ecmascriptVersion = this.readIntegerOption(ECMASCRIPT_VERSION);
      if (this.nashornCompatibilityMode && !ECMASCRIPT_VERSION.hasBeenSet(this.optionValues)) {
         this.ecmascriptVersion = 5;
      }

      this.annexB = this.readBooleanOption(ANNEX_B);
      this.intl402 = INTL_402.hasBeenSet(this.optionValues) ? this.readBooleanOption(INTL_402) : !this.nashornCompatibilityMode;
      this.regexpStaticResult = this.patchBooleanOption(REGEXP_STATIC_RESULT, "js.regexp-static-result", this.regexpStaticResult, msg -> {
         this.regexpStaticResultCyclicAssumption.invalidate(msg);
         this.regexpStaticResultCurrentAssumption = this.regexpStaticResultCyclicAssumption.getAssumption();
      });
      this.regexpMatchIndices = REGEXP_MATCH_INDICES.hasBeenSet(this.optionValues)
         ? this.readBooleanOption(REGEXP_MATCH_INDICES)
         : this.getEcmaScriptVersion() >= 13;
      this.sharedArrayBuffer = this.readBooleanOption(SHARED_ARRAY_BUFFER);
      this.v8CompatibilityMode = this.patchBooleanOption(V8_COMPATIBILITY_MODE, "js.v8-compat", this.v8CompatibilityMode, msg -> {
         this.v8CompatibilityModeCyclicAssumption.invalidate(msg);
         this.v8CompatibilityModeCurrentAssumption = this.v8CompatibilityModeCyclicAssumption.getAssumption();
      });
      this.v8RealmBuiltin = this.readBooleanOption(V8_REALM_BUILTIN);
      this.v8LegacyConst = this.readBooleanOption(V8_LEGACY_CONST);
      this.directByteBuffer = this.patchBooleanOption(DIRECT_BYTE_BUFFER, "js.direct-byte-buffer", this.directByteBuffer, msg -> {
         this.directByteBufferCyclicAssumption.invalidate(msg);
         this.directByteBufferCurrentAssumption = this.directByteBufferCyclicAssumption.getAssumption();
      });
      this.parseOnly = this.readBooleanOption(PARSE_ONLY);
      this.debug = this.readBooleanOption(DEBUG_BUILTIN);
      this.zoneRulesBasedTimeZones = this.readBooleanOption(ZONE_RULES_BASED_TIME_ZONES);
      this.timerResolution = this.patchLongOption(TIMER_RESOLUTION, "js.timer-resolution", this.timerResolution, msg -> {
         this.timerResolutionCyclicAssumption.invalidate(msg);
         this.timerResolutionCurrentAssumption = this.timerResolutionCyclicAssumption.getAssumption();
      });
      this.agentCanBlock = this.readBooleanOption(AGENT_CAN_BLOCK);
      this.awaitOptimization = this.readBooleanOption(AWAIT_OPTIMIZATION);
      this.disableEval = this.readBooleanOption(DISABLE_EVAL);
      this.disableWith = this.readBooleanOption(DISABLE_WITH);
      this.regexDumpAutomata = this.readBooleanOption(REGEX_DUMP_AUTOMATA);
      this.regexStepExecution = this.readBooleanOption(REGEX_STEP_EXECUTION);
      this.regexAlwaysEager = this.readBooleanOption(REGEX_ALWAYS_EAGER);
      this.scriptEngineGlobalScopeImport = this.readBooleanOption(SCRIPT_ENGINE_GLOBAL_SCOPE_IMPORT);
      this.hasForeignObjectPrototype = this.readBooleanOption(FOREIGN_OBJECT_PROTOTYPE);
      this.hasForeignHashProperties = this.readBooleanOption(FOREIGN_HASH_PROPERTIES);
      this.functionArgumentsLimit = this.readLongOption(FUNCTION_ARGUMENTS_LIMIT);
      this.test262Mode = this.readBooleanOption(TEST262_MODE);
      this.testV8Mode = this.readBooleanOption(TESTV8_MODE);
      this.validateRegExpLiterals = this.readBooleanOption(VALIDATE_REGEXP_LITERALS);
      this.functionConstructorCacheSize = this.readIntegerOption(FUNCTION_CONSTRUCTOR_CACHE_SIZE);
      this.regexCacheSize = this.readIntegerOption(REGEX_CACHE_SIZE);
      this.stringLengthLimit = this.readIntegerOption(STRING_LENGTH_LIMIT);
      this.stringLazySubstrings = this.readBooleanOption(STRING_LAZY_SUBSTRINGS);
      this.bindMemberFunctions = this.readBooleanOption(BIND_MEMBER_FUNCTIONS);
      this.commonJSRequire = this.readBooleanOption(COMMONJS_REQUIRE);
      this.regexRegressionTestMode = this.readBooleanOption(REGEX_REGRESSION_TEST_MODE);
      this.testCloneUninitialized = this.readBooleanOption(TEST_CLONE_UNINITIALIZED);
      this.lazyTranslation = this.readBooleanOption(LAZY_TRANSLATION);
      this.stackTraceLimit = this.readIntegerOption(STACK_TRACE_LIMIT);
      this.maxTypedArrayLength = this.readIntegerOption(MAX_TYPED_ARRAY_LENGTH);
      this.maxApplyArgumentLength = this.readIntegerOption(MAX_APPLY_ARGUMENT_LENGTH);
      this.maxPrototypeChainLength = this.readIntegerOption(MAX_PROTOTYPE_CHAIN_LENGTH);
      this.asyncStackTraces = this.readBooleanOption(ASYNC_STACK_TRACES);
      this.topLevelAwait = TOP_LEVEL_AWAIT.hasBeenSet(this.optionValues) ? this.readBooleanOption(TOP_LEVEL_AWAIT) : this.getEcmaScriptVersion() >= 13;
      this.useUTCForLegacyDates = USE_UTC_FOR_LEGACY_DATES.hasBeenSet(this.optionValues)
         ? this.readBooleanOption(USE_UTC_FOR_LEGACY_DATES)
         : !this.v8CompatibilityMode;
      this.webAssembly = this.readBooleanOption(WEBASSEMBLY);
      this.unhandledRejectionsMode = this.readUnhandledRejectionsMode();
      this.newSetMethods = this.readBooleanOption(NEW_SET_METHODS);
      this.operatorOverloading = this.readBooleanOption(OPERATOR_OVERLOADING);
      this.errorCause = ERROR_CAUSE.hasBeenSet(this.optionValues) ? this.readBooleanOption(ERROR_CAUSE) : this.getEcmaScriptVersion() >= 13;
      this.importAssertions = this.readBooleanOption(IMPORT_ASSERTIONS);
      this.jsonModules = this.readBooleanOption(JSON_MODULES);
      this.wasmBigInt = this.readBooleanOption(WASM_BIG_INT);
      this.esmEvalReturnsExports = this.readBooleanOption(ESM_EVAL_RETURNS_EXPORTS);
      this.printNoNewline = this.readBooleanOption(PRINT_NO_NEWLINE);
      this.mleMode = this.readBooleanOption(MLE_MODE) || this.readBooleanOption(INTEROP_COMPLETE_PROMISES);
      this.privateFieldsIn = PRIVATE_FIELDS_IN.hasBeenSet(this.optionValues) ? this.readBooleanOption(PRIVATE_FIELDS_IN) : this.getEcmaScriptVersion() >= 13;
      this.esmBareSpecifierRelativeLookup = this.readBooleanOption(ESM_BARE_SPECIFIER_RELATIVE_LOOKUP);
      this.temporal = this.readBooleanOption(TEMPORAL);
      this.propertyCacheLimit = this.readIntegerOption(PROPERTY_CACHE_LIMIT);
      this.functionCacheLimit = this.readIntegerOption(FUNCTION_CACHE_LIMIT);
      this.scopeOptimization = this.readBooleanOption(SCOPE_OPTIMIZATION);
   }

   private boolean patchBooleanOption(OptionKey<Boolean> key, String name, boolean oldValue, Consumer<String> invalidate) {
      boolean newValue = this.readBooleanOption(key);
      if (oldValue != newValue) {
         invalidate.accept(String.format("Option %s was changed from %b to %b.", name, oldValue, newValue));
      }

      return newValue;
   }

   private JSContextOptions.UnhandledRejectionsTrackingMode readUnhandledRejectionsMode() {
      return UNHANDLED_REJECTIONS.getValue(this.optionValues);
   }

   private boolean readBooleanOption(OptionKey<Boolean> key) {
      return key.getValue(this.optionValues);
   }

   private int readIntegerOption(OptionKey<Integer> key) {
      return key.getValue(this.optionValues);
   }

   private long patchLongOption(OptionKey<Long> key, String name, long oldValue, Consumer<String> invalidate) {
      long newValue = this.readLongOption(key);
      if (oldValue != newValue) {
         invalidate.accept(String.format("Option %s was changed from %d to %d.", name, oldValue, newValue));
      }

      return newValue;
   }

   private long readLongOption(OptionKey<Long> key) {
      return key.getValue(this.optionValues);
   }

   public static String helpWithDefault(String helpMessage, OptionKey<? extends Object> key) {
      return helpMessage + " (default:" + key.getDefaultValue() + ")";
   }

   public static OptionDescriptor newOptionDescriptor(
      OptionKey<?> key, String name, OptionCategory category, OptionStability stability, String help, String usageSyntax
   ) {
      return OptionDescriptor.newBuilder(key, name)
         .category(category)
         .help(helpWithDefault(help, (OptionKey<? extends Object>)key))
         .stability(stability)
         .usageSyntax(usageSyntax)
         .build();
   }

   public static void describeOptions(List<OptionDescriptor> options) {
      for (OptionDescriptor desc : new JSContextOptionsOptionDescriptors()) {
         options.add(newOptionDescriptor(desc.getKey(), desc.getName(), desc.getCategory(), desc.getStability(), desc.getHelp(), desc.getUsageSyntax()));
      }
   }

   public <T> boolean optionWillChange(OptionKey<T> option, OptionValues newOptionValues) {
      return !option.getValue(this.optionValues).equals(option.getValue(newOptionValues));
   }

   public int getEcmaScriptVersion() {
      return this.ecmascriptVersion;
   }

   public boolean isAnnexB() {
      return this.annexB;
   }

   public boolean isIntl402() {
      CompilerAsserts.neverPartOfCompilation("Patchable option intl-402 should never be accessed in compiled code.");
      return this.intl402;
   }

   public boolean isRegexpMatchIndices() {
      return this.regexpMatchIndices;
   }

   public boolean isRegexpStaticResult() {
      try {
         this.regexpStaticResultCurrentAssumption.check();
      } catch (InvalidAssumptionException var2) {
      }

      return this.regexpStaticResult;
   }

   public boolean isSharedArrayBuffer() {
      return this.getEcmaScriptVersion() < 8 ? false : this.sharedArrayBuffer;
   }

   public boolean isAtomics() {
      return this.getEcmaScriptVersion() < 8 ? false : ATOMICS.getValue(this.optionValues);
   }

   public boolean isV8CompatibilityMode() {
      try {
         this.v8CompatibilityModeCurrentAssumption.check();
      } catch (InvalidAssumptionException var2) {
      }

      return this.v8CompatibilityMode;
   }

   public boolean isNashornCompatibilityMode() {
      return this.nashornCompatibilityMode;
   }

   public boolean isDebugBuiltin() {
      return this.debug;
   }

   public boolean isMLEMode() {
      return this.mleMode;
   }

   public boolean isDirectByteBuffer() {
      try {
         this.directByteBufferCurrentAssumption.check();
      } catch (InvalidAssumptionException var2) {
      }

      return this.directByteBuffer;
   }

   public boolean isParseOnly() {
      return this.parseOnly;
   }

   public long getTimerResolution() {
      try {
         this.timerResolutionCurrentAssumption.check();
      } catch (InvalidAssumptionException var2) {
      }

      return this.timerResolution;
   }

   public boolean isV8RealmBuiltin() {
      return this.v8RealmBuiltin;
   }

   public boolean isV8LegacyConst() {
      return this.v8LegacyConst;
   }

   public boolean hasZoneRulesBasedTimeZones() {
      return this.zoneRulesBasedTimeZones;
   }

   public boolean canAgentBlock() {
      return this.agentCanBlock;
   }

   public boolean isAwaitOptimization() {
      return this.awaitOptimization;
   }

   public boolean isTopLevelAwait() {
      return this.topLevelAwait;
   }

   public boolean isDisableEval() {
      return this.disableEval;
   }

   public boolean isDisableWith() {
      return this.disableWith;
   }

   public boolean isRegexDumpAutomata() {
      return this.regexDumpAutomata;
   }

   public boolean isRegexStepExecution() {
      return this.regexStepExecution;
   }

   public boolean isRegexAlwaysEager() {
      return this.regexAlwaysEager;
   }

   public boolean isScriptEngineGlobalScopeImport() {
      return this.scriptEngineGlobalScopeImport;
   }

   public boolean hasForeignObjectPrototype() {
      return this.hasForeignObjectPrototype;
   }

   public boolean hasForeignHashProperties() {
      return this.hasForeignHashProperties;
   }

   public boolean isGlobalProperty() {
      CompilerAsserts.neverPartOfCompilation("Context patchable option global-property was assumed not to be accessed in compiled code.");
      return GLOBAL_PROPERTY.getValue(this.optionValues);
   }

   public boolean isGlobalArguments() {
      CompilerAsserts.neverPartOfCompilation("Context patchable option js.global-arguments was assumed not to be accessed in compiled code.");
      return GLOBAL_ARGUMENTS.getValue(this.optionValues);
   }

   public boolean isConsole() {
      CompilerAsserts.neverPartOfCompilation("Context patchable option console was assumed not to be accessed in compiled code.");
      return CONSOLE.getValue(this.optionValues) || !CONSOLE.hasBeenSet(this.optionValues) && this.isShell();
   }

   public boolean isPrint() {
      CompilerAsserts.neverPartOfCompilation("Context patchable option print was assumed not to be accessed in compiled code.");
      return PRINT.getValue(this.optionValues) || !PRINT.hasBeenSet(this.optionValues) && (this.isShell() || this.isNashornCompatibilityMode());
   }

   public boolean isPrintNoNewline() {
      return this.printNoNewline;
   }

   public boolean isLoad() {
      CompilerAsserts.neverPartOfCompilation("Context patchable option load was assumed not to be accessed in compiled code.");
      return LOAD.getValue(this.optionValues) || !LOAD.hasBeenSet(this.optionValues) && (this.isShell() || this.isNashornCompatibilityMode());
   }

   public boolean isCommonJSRequire() {
      return this.commonJSRequire;
   }

   public Map<String, String> getCommonJSRequireBuiltins() {
      CompilerAsserts.neverPartOfCompilation("Context patchable option load was assumed not to be accessed in compiled code.");
      return COMMONJS_CORE_MODULES_REPLACEMENTS.getValue(this.optionValues);
   }

   public String getRequireCwd() {
      CompilerAsserts.neverPartOfCompilation("Context patchable option load was assumed not to be accessed in compiled code.");
      return COMMONJS_REQUIRE_CWD.getValue(this.optionValues);
   }

   public boolean isPerformance() {
      CompilerAsserts.neverPartOfCompilation("Context patchable option performance was assumed not to be accessed in compiled code.");
      return PERFORMANCE.getValue(this.optionValues) || !PERFORMANCE.hasBeenSet(this.optionValues) && this.isShell();
   }

   public boolean isShell() {
      CompilerAsserts.neverPartOfCompilation("Context patchable option shell was assumed not to be accessed in compiled code.");
      return SHELL.getValue(this.optionValues);
   }

   public boolean isGraalBuiltin() {
      return GRAAL_BUILTIN.getValue(this.optionValues);
   }

   public boolean isPolyglotBuiltin() {
      return POLYGLOT_BUILTIN.getValue(this.optionValues);
   }

   public boolean isPolyglotEvalFile() {
      return POLYGLOT_EVALFILE.getValue(this.optionValues);
   }

   public boolean isLoadFromURL() {
      return LOAD_FROM_URL.getValue(this.optionValues);
   }

   public boolean isLoadFromClasspath() {
      return LOAD_FROM_CLASSPATH.getValue(this.optionValues);
   }

   public boolean isBigInt() {
      return this.getEcmaScriptVersion() < 10 ? false : BIGINT.getValue(this.optionValues);
   }

   public long getFunctionArgumentsLimit() {
      return this.functionArgumentsLimit;
   }

   public boolean isTest262Mode() {
      return this.test262Mode;
   }

   public boolean isTestV8Mode() {
      return this.testV8Mode;
   }

   public boolean isValidateRegExpLiterals() {
      return this.validateRegExpLiterals;
   }

   public String getLocale() {
      return LOCALE.getValue(this.optionValues);
   }

   public String getCharset() {
      return CHARSET.getValue(this.optionValues);
   }

   public int getFunctionConstructorCacheSize() {
      return this.functionConstructorCacheSize;
   }

   public int getRegexCacheSize() {
      return this.regexCacheSize;
   }

   public int getStringLengthLimit() {
      return this.stringLengthLimit;
   }

   public boolean isStringLazySubstrings() {
      return this.stringLazySubstrings;
   }

   public boolean bindMemberFunctions() {
      return this.bindMemberFunctions;
   }

   public boolean isRegexRegressionTestMode() {
      return this.regexRegressionTestMode;
   }

   public String getDebugPropertyName() {
      CompilerAsserts.neverPartOfCompilation("Context patchable option debug-property-name was assumed not to be accessed in compiled code.");
      return DEBUG_PROPERTY_NAME.getValue(this.optionValues);
   }

   public boolean isProfileTime() {
      CompilerAsserts.neverPartOfCompilation("Context patchable option profile-time was assumed not to be accessed in compiled code.");
      return PROFILE_TIME.getValue(this.optionValues);
   }

   public boolean isTestCloneUninitialized() {
      return this.testCloneUninitialized;
   }

   public boolean isLazyTranslation() {
      return this.lazyTranslation;
   }

   public boolean isProfileTimePrintCumulative() {
      CompilerAsserts.neverPartOfCompilation("Context patchable option profile-time-print-cumulative was assumed not to be accessed in compiled code.");
      return PROFILE_TIME_PRINT_CUMULATIVE.getValue(this.optionValues);
   }

   public int getStackTraceLimit() {
      return this.stackTraceLimit;
   }

   public int getMaxTypedArrayLength() {
      return this.maxTypedArrayLength;
   }

   public int getMaxApplyArgumentLength() {
      return this.maxApplyArgumentLength;
   }

   public int getMaxPrototypeChainLength() {
      return this.maxPrototypeChainLength;
   }

   public int getPropertyCacheLimit() {
      return this.propertyCacheLimit;
   }

   public int getFunctionCacheLimit() {
      return this.functionCacheLimit;
   }

   public boolean isAsyncStackTraces() {
      return this.asyncStackTraces;
   }

   public boolean shouldUseUTCForLegacyDates() {
      return this.useUTCForLegacyDates;
   }

   public boolean isWebAssembly() {
      return this.webAssembly;
   }

   public boolean isTemporal() {
      return this.temporal;
   }

   public JSContextOptions.UnhandledRejectionsTrackingMode getUnhandledRejectionsMode() {
      return this.unhandledRejectionsMode;
   }

   public boolean isNewSetMethods() {
      return this.newSetMethods;
   }

   public boolean isOperatorOverloading() {
      return this.operatorOverloading;
   }

   public boolean isErrorCauseEnabled() {
      return this.errorCause;
   }

   public boolean isImportAssertions() {
      return this.importAssertions;
   }

   public boolean isJsonModules() {
      return this.jsonModules;
   }

   public boolean isWasmBigInt() {
      return this.wasmBigInt;
   }

   public boolean isEsmEvalReturnsExports() {
      return this.esmEvalReturnsExports;
   }

   public boolean isPrivateFieldsIn() {
      return this.privateFieldsIn;
   }

   public boolean isEsmBareSpecifierRelativeLookup() {
      return this.esmBareSpecifierRelativeLookup;
   }

   public boolean isScopeOptimization() {
      return this.scopeOptimization;
   }

   @Override
   public int hashCode() {
      int hash = 5;
      hash = 53 * hash + Objects.hashCode(this.parserOptions);
      hash = 53 * hash + this.ecmascriptVersion;
      hash = 53 * hash + (this.annexB ? 1 : 0);
      hash = 53 * hash + (this.intl402 ? 1 : 0);
      hash = 53 * hash + (this.regexpMatchIndices ? 1 : 0);
      hash = 53 * hash + (this.regexpStaticResult ? 1 : 0);
      hash = 53 * hash + (this.sharedArrayBuffer ? 1 : 0);
      hash = 53 * hash + (this.v8CompatibilityMode ? 1 : 0);
      hash = 53 * hash + (this.v8RealmBuiltin ? 1 : 0);
      hash = 53 * hash + (this.v8LegacyConst ? 1 : 0);
      hash = 53 * hash + (this.nashornCompatibilityMode ? 1 : 0);
      hash = 53 * hash + (this.debug ? 1 : 0);
      hash = 53 * hash + (this.directByteBuffer ? 1 : 0);
      hash = 53 * hash + (this.parseOnly ? 1 : 0);
      hash = 53 * hash + (this.zoneRulesBasedTimeZones ? 1 : 0);
      hash = 53 * hash + (int)this.timerResolution;
      hash = 53 * hash + (this.agentCanBlock ? 1 : 0);
      hash = 53 * hash + (this.awaitOptimization ? 1 : 0);
      hash = 53 * hash + (this.disableEval ? 1 : 0);
      hash = 53 * hash + (this.disableWith ? 1 : 0);
      hash = 53 * hash + (this.regexDumpAutomata ? 1 : 0);
      hash = 53 * hash + (this.regexStepExecution ? 1 : 0);
      hash = 53 * hash + (this.regexAlwaysEager ? 1 : 0);
      hash = 53 * hash + (this.scriptEngineGlobalScopeImport ? 1 : 0);
      hash = 53 * hash + (this.hasForeignObjectPrototype ? 1 : 0);
      hash = 53 * hash + (this.hasForeignHashProperties ? 1 : 0);
      hash = 53 * hash + (int)this.functionArgumentsLimit;
      hash = 53 * hash + (this.test262Mode ? 1 : 0);
      hash = 53 * hash + (this.testV8Mode ? 1 : 0);
      hash = 53 * hash + (this.validateRegExpLiterals ? 1 : 0);
      hash = 53 * hash + this.functionConstructorCacheSize;
      hash = 53 * hash + this.regexCacheSize;
      hash = 53 * hash + this.stringLengthLimit;
      hash = 53 * hash + (this.stringLazySubstrings ? 1 : 0);
      hash = 53 * hash + (this.bindMemberFunctions ? 1 : 0);
      hash = 53 * hash + (this.commonJSRequire ? 1 : 0);
      hash = 53 * hash + (this.regexRegressionTestMode ? 1 : 0);
      hash = 53 * hash + (this.testCloneUninitialized ? 1 : 0);
      hash = 53 * hash + (this.lazyTranslation ? 1 : 0);
      hash = 53 * hash + this.stackTraceLimit;
      hash = 53 * hash + (this.asyncStackTraces ? 1 : 0);
      hash = 53 * hash + this.maxTypedArrayLength;
      hash = 53 * hash + this.maxApplyArgumentLength;
      hash = 53 * hash + this.maxPrototypeChainLength;
      hash = 53 * hash + this.propertyCacheLimit;
      hash = 53 * hash + this.functionCacheLimit;
      hash = 53 * hash + (this.topLevelAwait ? 1 : 0);
      hash = 53 * hash + (this.useUTCForLegacyDates ? 1 : 0);
      hash = 53 * hash + (this.webAssembly ? 1 : 0);
      hash = 53 * hash + this.unhandledRejectionsMode.ordinal();
      hash = 53 * hash + (this.newSetMethods ? 1 : 0);
      hash = 53 * hash + (this.operatorOverloading ? 1 : 0);
      hash = 53 * hash + (this.errorCause ? 1 : 0);
      hash = 53 * hash + (this.importAssertions ? 1 : 0);
      hash = 53 * hash + (this.jsonModules ? 1 : 0);
      hash = 53 * hash + (this.wasmBigInt ? 1 : 0);
      hash = 53 * hash + (this.esmEvalReturnsExports ? 1 : 0);
      hash = 53 * hash + (this.printNoNewline ? 1 : 0);
      hash = 53 * hash + (this.mleMode ? 1 : 0);
      hash = 53 * hash + (this.privateFieldsIn ? 1 : 0);
      hash = 53 * hash + (this.esmBareSpecifierRelativeLookup ? 1 : 0);
      hash = 53 * hash + (this.temporal ? 1 : 0);
      return 53 * hash + (this.scopeOptimization ? 1 : 0);
   }

   @Override
   public boolean equals(Object obj) {
      if (this == obj) {
         return true;
      } else if (obj == null) {
         return false;
      } else if (this.getClass() != obj.getClass()) {
         return false;
      } else {
         JSContextOptions other = (JSContextOptions)obj;
         if (this.ecmascriptVersion != other.ecmascriptVersion) {
            return false;
         } else if (this.annexB != other.annexB) {
            return false;
         } else if (this.intl402 != other.intl402) {
            return false;
         } else if (this.regexpMatchIndices != other.regexpMatchIndices) {
            return false;
         } else if (this.regexpStaticResult != other.regexpStaticResult) {
            return false;
         } else if (this.sharedArrayBuffer != other.sharedArrayBuffer) {
            return false;
         } else if (this.v8CompatibilityMode != other.v8CompatibilityMode) {
            return false;
         } else if (this.v8RealmBuiltin != other.v8RealmBuiltin) {
            return false;
         } else if (this.v8LegacyConst != other.v8LegacyConst) {
            return false;
         } else if (this.nashornCompatibilityMode != other.nashornCompatibilityMode) {
            return false;
         } else if (this.debug != other.debug) {
            return false;
         } else if (this.directByteBuffer != other.directByteBuffer) {
            return false;
         } else if (this.parseOnly != other.parseOnly) {
            return false;
         } else if (this.zoneRulesBasedTimeZones != other.zoneRulesBasedTimeZones) {
            return false;
         } else if (this.timerResolution != other.timerResolution) {
            return false;
         } else if (this.agentCanBlock != other.agentCanBlock) {
            return false;
         } else if (this.awaitOptimization != other.awaitOptimization) {
            return false;
         } else if (this.disableEval != other.disableEval) {
            return false;
         } else if (this.disableWith != other.disableWith) {
            return false;
         } else if (this.regexDumpAutomata != other.regexDumpAutomata) {
            return false;
         } else if (this.regexStepExecution != other.regexStepExecution) {
            return false;
         } else if (this.regexAlwaysEager != other.regexAlwaysEager) {
            return false;
         } else if (this.scriptEngineGlobalScopeImport != other.scriptEngineGlobalScopeImport) {
            return false;
         } else if (this.hasForeignObjectPrototype != other.hasForeignObjectPrototype) {
            return false;
         } else if (this.hasForeignHashProperties != other.hasForeignHashProperties) {
            return false;
         } else if (this.functionArgumentsLimit != other.functionArgumentsLimit) {
            return false;
         } else if (this.test262Mode != other.test262Mode) {
            return false;
         } else if (this.testV8Mode != other.testV8Mode) {
            return false;
         } else if (this.validateRegExpLiterals != other.validateRegExpLiterals) {
            return false;
         } else if (this.functionConstructorCacheSize != other.functionConstructorCacheSize) {
            return false;
         } else if (this.regexCacheSize != other.regexCacheSize) {
            return false;
         } else if (this.stringLengthLimit != other.stringLengthLimit) {
            return false;
         } else if (this.stringLazySubstrings != other.stringLazySubstrings) {
            return false;
         } else if (this.bindMemberFunctions != other.bindMemberFunctions) {
            return false;
         } else if (this.commonJSRequire != other.commonJSRequire) {
            return false;
         } else if (this.regexRegressionTestMode != other.regexRegressionTestMode) {
            return false;
         } else if (this.testCloneUninitialized != other.testCloneUninitialized) {
            return false;
         } else if (this.lazyTranslation != other.lazyTranslation) {
            return false;
         } else if (this.stackTraceLimit != other.stackTraceLimit) {
            return false;
         } else if (this.asyncStackTraces != other.asyncStackTraces) {
            return false;
         } else if (this.maxTypedArrayLength != other.maxTypedArrayLength) {
            return false;
         } else if (this.maxApplyArgumentLength != other.maxApplyArgumentLength) {
            return false;
         } else if (this.maxPrototypeChainLength != other.maxPrototypeChainLength) {
            return false;
         } else if (this.propertyCacheLimit != other.propertyCacheLimit) {
            return false;
         } else if (this.functionCacheLimit != other.functionCacheLimit) {
            return false;
         } else if (this.topLevelAwait != other.topLevelAwait) {
            return false;
         } else if (this.useUTCForLegacyDates != other.useUTCForLegacyDates) {
            return false;
         } else if (this.webAssembly != other.webAssembly) {
            return false;
         } else if (this.unhandledRejectionsMode != other.unhandledRejectionsMode) {
            return false;
         } else if (this.newSetMethods != other.newSetMethods) {
            return false;
         } else if (this.operatorOverloading != other.operatorOverloading) {
            return false;
         } else if (this.errorCause != other.errorCause) {
            return false;
         } else if (this.importAssertions != other.importAssertions) {
            return false;
         } else if (this.jsonModules != other.jsonModules) {
            return false;
         } else if (this.wasmBigInt != other.wasmBigInt) {
            return false;
         } else if (this.esmEvalReturnsExports != other.esmEvalReturnsExports) {
            return false;
         } else if (this.printNoNewline != other.printNoNewline) {
            return false;
         } else if (this.mleMode != other.mleMode) {
            return false;
         } else if (this.privateFieldsIn != other.privateFieldsIn) {
            return false;
         } else if (this.esmBareSpecifierRelativeLookup != other.esmBareSpecifierRelativeLookup) {
            return false;
         } else if (this.temporal != other.temporal) {
            return false;
         } else {
            return this.scopeOptimization != other.scopeOptimization ? false : Objects.equals(this.parserOptions, other.parserOptions);
         }
      }
   }

   public static enum UnhandledRejectionsTrackingMode {
      NONE,
      WARN,
      THROW,
      HANDLER;

      @Override
      public String toString() {
         return this.name().toLowerCase(Locale.ENGLISH);
      }
   }
}
