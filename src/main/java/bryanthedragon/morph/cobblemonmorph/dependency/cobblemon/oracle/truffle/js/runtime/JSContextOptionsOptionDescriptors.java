package com.oracle.truffle.js.runtime;

import com.oracle.truffle.api.dsl.GeneratedBy;
import java.util.Arrays;
import java.util.Iterator;
import org.graalvm.options.OptionCategory;
import org.graalvm.options.OptionDescriptor;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.options.OptionStability;

@GeneratedBy(JSContextOptions.class)
final class JSContextOptionsOptionDescriptors implements OptionDescriptors {
   @Override
   public OptionDescriptor get(String optionName) {
      switch (optionName) {
         case "js.agent-can-block":
            return OptionDescriptor.newBuilder(JSContextOptions.AGENT_CAN_BLOCK, "js.agent-can-block")
               .deprecated(false)
               .help("Determines whether agents can block or not.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.annex-b":
            return OptionDescriptor.newBuilder(JSContextOptions.ANNEX_B, "js.annex-b")
               .deprecated(false)
               .help("Enable ECMAScript Annex B features.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.async-stack-traces":
            return OptionDescriptor.newBuilder(JSContextOptions.ASYNC_STACK_TRACES, "js.async-stack-traces")
               .deprecated(false)
               .help("Include async function frames in stack traces.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.atomics":
            return OptionDescriptor.newBuilder(JSContextOptions.ATOMICS, "js.atomics")
               .deprecated(false)
               .help("Enable ECMAScript Atomics.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.await-optimization":
            return OptionDescriptor.newBuilder(JSContextOptions.AWAIT_OPTIMIZATION, "js.await-optimization")
               .deprecated(false)
               .help("Use PromiseResolve for Await.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.bigint":
            return OptionDescriptor.newBuilder(JSContextOptions.BIGINT, "js.bigint")
               .deprecated(false)
               .help("Provide an implementation of the BigInt proposal.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.bind-member-functions":
            return OptionDescriptor.newBuilder(JSContextOptions.BIND_MEMBER_FUNCTIONS, "js.bind-member-functions")
               .deprecated(false)
               .help("Bind functions returned by Value.getMember to the receiver object.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.charset":
            return OptionDescriptor.newBuilder(JSContextOptions.CHARSET, "js.charset")
               .deprecated(false)
               .help("Charset used for decoding/encoding of the input/output streams.")
               .usageSyntax("UTF-8|UTF-32|<name>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.class-fields":
            return OptionDescriptor.newBuilder(JSContextOptions.CLASS_FIELDS, "js.class-fields")
               .deprecated(false)
               .help("Enable the class public and private fields proposal.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.commonjs-core-modules-replacements":
            return OptionDescriptor.newBuilder(JSContextOptions.COMMONJS_CORE_MODULES_REPLACEMENTS, "js.commonjs-core-modules-replacements")
               .deprecated(false)
               .help("Npm packages used to replace global Node.js builtins.")
               .usageSyntax("<name>:<module>,...")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.commonjs-require":
            return OptionDescriptor.newBuilder(JSContextOptions.COMMONJS_REQUIRE, "js.commonjs-require")
               .deprecated(false)
               .help("Enable CommonJS require emulation.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.commonjs-require-cwd":
            return OptionDescriptor.newBuilder(JSContextOptions.COMMONJS_REQUIRE_CWD, "js.commonjs-require-cwd")
               .deprecated(false)
               .help("CommonJS default current working directory.")
               .usageSyntax("<path>")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.console":
            return OptionDescriptor.newBuilder(JSContextOptions.CONSOLE, "js.console")
               .deprecated(false)
               .help("Provide 'console' global property.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.const-as-var":
            return OptionDescriptor.newBuilder(JSContextOptions.CONST_AS_VAR, "js.const-as-var")
               .deprecated(false)
               .help("Parse const declarations as a var (legacy compatibility option).")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.debug-builtin":
            return OptionDescriptor.newBuilder(JSContextOptions.DEBUG_BUILTIN, "js.debug-builtin")
               .deprecated(false)
               .help("Provide a non-API Debug builtin. Behaviour will likely change. Don't depend on this in production code.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.debug-property-name":
            return OptionDescriptor.newBuilder(JSContextOptions.DEBUG_PROPERTY_NAME, "js.debug-property-name")
               .deprecated(false)
               .help("The name used for the Graal.js debug builtin.")
               .usageSyntax("<name>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.direct-byte-buffer":
            return OptionDescriptor.newBuilder(JSContextOptions.DIRECT_BYTE_BUFFER, "js.direct-byte-buffer")
               .deprecated(false)
               .help("Use direct (off-heap) byte buffer for typed arrays.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.disable-eval":
            return OptionDescriptor.newBuilder(JSContextOptions.DISABLE_EVAL, "js.disable-eval")
               .deprecated(false)
               .help("User code is not allowed to parse code via e.g. eval().")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.disable-with":
            return OptionDescriptor.newBuilder(JSContextOptions.DISABLE_WITH, "js.disable-with")
               .deprecated(false)
               .help("User code is not allowed to use the 'with' statement.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.ecmascript-version":
            return OptionDescriptor.newBuilder(JSContextOptions.ECMASCRIPT_VERSION, "js.ecmascript-version")
               .deprecated(false)
               .help("ECMAScript version to be compatible with. Default is 'latest' (latest supported version), staged features are in 'staging'.")
               .usageSyntax("latest|staging|[5, 13]|[2015, 2022]")
               .category(OptionCategory.USER)
               .stability(OptionStability.STABLE)
               .build();
         case "js.error-cause":
            return OptionDescriptor.newBuilder(JSContextOptions.ERROR_CAUSE, "js.error-cause")
               .deprecated(false)
               .help("Enable the error cause proposal. Allows an error to be chained with a cause using the optional options parameter.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.esm-bare-specifier-relative-lookup":
            return OptionDescriptor.newBuilder(JSContextOptions.ESM_BARE_SPECIFIER_RELATIVE_LOOKUP, "js.esm-bare-specifier-relative-lookup")
               .deprecated(false)
               .help("Resolve ESM bare specifiers relative to the importing module's path instead of attempting an absolute path lookup.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.esm-eval-returns-exports":
            return OptionDescriptor.newBuilder(JSContextOptions.ESM_EVAL_RETURNS_EXPORTS, "js.esm-eval-returns-exports")
               .deprecated(false)
               .help("Eval of an ES module through the polyglot API returns its exported symbols.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.foreign-hash-properties":
            return OptionDescriptor.newBuilder(JSContextOptions.FOREIGN_HASH_PROPERTIES, "js.foreign-hash-properties")
               .deprecated(false)
               .help("Allow getting/setting non-JS hash entries using the `[]` and `.` operators.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.foreign-object-prototype":
            return OptionDescriptor.newBuilder(JSContextOptions.FOREIGN_OBJECT_PROTOTYPE, "js.foreign-object-prototype")
               .deprecated(false)
               .help("Non-JS objects have prototype (Object/Function/Array.prototype) set.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.STABLE)
               .build();
         case "js.function-arguments-limit":
            return OptionDescriptor.newBuilder(JSContextOptions.FUNCTION_ARGUMENTS_LIMIT, "js.function-arguments-limit")
               .deprecated(false)
               .help("Maximum number of arguments for functions.")
               .usageSyntax("<int>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.function-cache-limit":
            return OptionDescriptor.newBuilder(JSContextOptions.FUNCTION_CACHE_LIMIT, "js.function-cache-limit")
               .deprecated(false)
               .help("Maximum allowed size of a function cache.")
               .usageSyntax("<int>")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.function-constructor-cache-size":
            return OptionDescriptor.newBuilder(JSContextOptions.FUNCTION_CONSTRUCTOR_CACHE_SIZE, "js.function-constructor-cache-size")
               .deprecated(false)
               .help("Maximum size of the parsing cache used by the Function constructor to avoid re-parsing known sources.")
               .usageSyntax("<int>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.function-statement-error":
            return OptionDescriptor.newBuilder(JSContextOptions.FUNCTION_STATEMENT_ERROR, "js.function-statement-error")
               .deprecated(false)
               .help("Treat hoistable function statements in blocks as an error (in ES5 mode).")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.global-arguments":
            return OptionDescriptor.newBuilder(JSContextOptions.GLOBAL_ARGUMENTS, "js.global-arguments")
               .deprecated(false)
               .help("Provide 'arguments' global property.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.global-property":
            return OptionDescriptor.newBuilder(JSContextOptions.GLOBAL_PROPERTY, "js.global-property")
               .deprecated(false)
               .help("Provide 'global' global property.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.graal-builtin":
            return OptionDescriptor.newBuilder(JSContextOptions.GRAAL_BUILTIN, "js.graal-builtin")
               .deprecated(false)
               .help("Provide 'Graal' global property.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.import-assertions":
            return OptionDescriptor.newBuilder(JSContextOptions.IMPORT_ASSERTIONS, "js.import-assertions")
               .deprecated(false)
               .help("Enable import assertions")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.interop-complete-promises":
            return OptionDescriptor.newBuilder(JSContextOptions.INTEROP_COMPLETE_PROMISES, "js.interop-complete-promises")
               .deprecated(false)
               .help("Resolve promises when crossing a polyglot language boundary.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.intl-402":
            return OptionDescriptor.newBuilder(JSContextOptions.INTL_402, "js.intl-402")
               .deprecated(false)
               .help("Enable ECMAScript Internationalization API.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.STABLE)
               .build();
         case "js.java-package-globals":
            return OptionDescriptor.newBuilder(JSContextOptions.JAVA_PACKAGE_GLOBALS, "js.java-package-globals")
               .deprecated(false)
               .help("Provide Java package globals: Packages, java, javafx, javax, com, org, edu.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.json-modules":
            return OptionDescriptor.newBuilder(JSContextOptions.JSON_MODULES, "js.json-modules")
               .deprecated(false)
               .help("Enable loading of json modules")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.lazy-translation":
            return OptionDescriptor.newBuilder(JSContextOptions.LAZY_TRANSLATION, "js.lazy-translation")
               .deprecated(false)
               .help("Translate function bodies lazily.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.load":
            return OptionDescriptor.newBuilder(JSContextOptions.LOAD, "js.load")
               .deprecated(false)
               .help("Provide 'load' global function.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.load-from-classpath":
            return OptionDescriptor.newBuilder(JSContextOptions.LOAD_FROM_CLASSPATH, "js.load-from-classpath")
               .deprecated(false)
               .help("Allow 'load' to access 'classpath:' URLs. Do not use with untrusted code.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.load-from-url":
            return OptionDescriptor.newBuilder(JSContextOptions.LOAD_FROM_URL, "js.load-from-url")
               .deprecated(false)
               .help("Allow 'load' to access URLs. Do not use with untrusted code.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.locale":
            return OptionDescriptor.newBuilder(JSContextOptions.LOCALE, "js.locale")
               .deprecated(false)
               .help("Use a specific default locale for locale-sensitive operations.")
               .usageSyntax("<locale>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.max-apply-argument-length":
            return OptionDescriptor.newBuilder(JSContextOptions.MAX_APPLY_ARGUMENT_LENGTH, "js.max-apply-argument-length")
               .deprecated(false)
               .help("Maximum allowed number of arguments allowed in an apply function.")
               .usageSyntax("<int>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.max-prototype-chain-length":
            return OptionDescriptor.newBuilder(JSContextOptions.MAX_PROTOTYPE_CHAIN_LENGTH, "js.max-prototype-chain-length")
               .deprecated(false)
               .help("Maximum allowed length of a prototype chain.")
               .usageSyntax("<int>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.max-typed-array-length":
            return OptionDescriptor.newBuilder(JSContextOptions.MAX_TYPED_ARRAY_LENGTH, "js.max-typed-array-length")
               .deprecated(false)
               .help("Maximum allowed length for TypedArrays.")
               .usageSyntax("<int>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.mle-mode":
            return OptionDescriptor.newBuilder(JSContextOptions.MLE_MODE, "js.mle-mode")
               .deprecated(false)
               .help("Provide a non-API MLE builtin. Behaviour will likely change. Don't depend on this in production code.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.nashorn-compat":
            return OptionDescriptor.newBuilder(JSContextOptions.NASHORN_COMPATIBILITY_MODE, "js.nashorn-compat")
               .deprecated(false)
               .help("Provide compatibility with the OpenJDK Nashorn engine. Do not use with untrusted code.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.new-set-methods":
            return OptionDescriptor.newBuilder(JSContextOptions.NEW_SET_METHODS, "js.new-set-methods")
               .deprecated(false)
               .help("Enable new Set methods.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.operator-overloading":
            return OptionDescriptor.newBuilder(JSContextOptions.OPERATOR_OVERLOADING, "js.operator-overloading")
               .deprecated(false)
               .help("Enable operator overloading")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.parse-only":
            return OptionDescriptor.newBuilder(JSContextOptions.PARSE_ONLY, "js.parse-only")
               .deprecated(false)
               .help("Only parse source code, do not run it.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.performance":
            return OptionDescriptor.newBuilder(JSContextOptions.PERFORMANCE, "js.performance")
               .deprecated(false)
               .help("Provide 'performance' global property.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.polyglot-builtin":
            return OptionDescriptor.newBuilder(JSContextOptions.POLYGLOT_BUILTIN, "js.polyglot-builtin")
               .deprecated(true)
               .deprecationMessage("")
               .help("Provide 'Polyglot' global property.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.polyglot-evalfile":
            return OptionDescriptor.newBuilder(JSContextOptions.POLYGLOT_EVALFILE, "js.polyglot-evalfile")
               .deprecated(false)
               .help("Provide 'Polyglot.evalFile' function.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.print":
            return OptionDescriptor.newBuilder(JSContextOptions.PRINT, "js.print")
               .deprecated(false)
               .help("Provide 'print' global function.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.print-no-newline":
            return OptionDescriptor.newBuilder(JSContextOptions.PRINT_NO_NEWLINE, "js.print-no-newline")
               .deprecated(false)
               .help("Print function will not print new line char.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.private-fields-in":
            return OptionDescriptor.newBuilder(JSContextOptions.PRIVATE_FIELDS_IN, "js.private-fields-in")
               .deprecated(false)
               .help("Enable private field in in operator")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.profile-time":
            return OptionDescriptor.newBuilder(JSContextOptions.PROFILE_TIME, "js.profile-time")
               .deprecated(false)
               .help("Enable time profiling.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.profile-time-print-cumulative":
            return OptionDescriptor.newBuilder(JSContextOptions.PROFILE_TIME_PRINT_CUMULATIVE, "js.profile-time-print-cumulative")
               .deprecated(false)
               .help("Print cumulative time when time profiling is enabled.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.property-cache-limit":
            return OptionDescriptor.newBuilder(JSContextOptions.PROPERTY_CACHE_LIMIT, "js.property-cache-limit")
               .deprecated(false)
               .help("Maximum allowed size of a property cache.")
               .usageSyntax("<int>")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.regex-cache-size":
            return OptionDescriptor.newBuilder(JSContextOptions.REGEX_CACHE_SIZE, "js.regex-cache-size")
               .deprecated(false)
               .help("Maximum size of the regex cache used by the RegExp constructor to avoid re-parsing known sources.")
               .usageSyntax("<int>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.regex-regression-test-mode":
            return OptionDescriptor.newBuilder(JSContextOptions.REGEX_REGRESSION_TEST_MODE, "js.regex-regression-test-mode")
               .deprecated(false)
               .help("Test mode for TRegex.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.regex.always-eager":
            return OptionDescriptor.newBuilder(JSContextOptions.REGEX_ALWAYS_EAGER, "js.regex.always-eager")
               .deprecated(false)
               .help("Always match capture groups eagerly.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.regex.dump-automata":
            return OptionDescriptor.newBuilder(JSContextOptions.REGEX_DUMP_AUTOMATA, "js.regex.dump-automata")
               .deprecated(false)
               .help("Produce ASTs and automata in JSON, DOT (GraphViz) and LaTeX formats.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.regex.step-execution":
            return OptionDescriptor.newBuilder(JSContextOptions.REGEX_STEP_EXECUTION, "js.regex.step-execution")
               .deprecated(false)
               .help("Trace the execution of automata in JSON files.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.regexp-match-indices":
            return OptionDescriptor.newBuilder(JSContextOptions.REGEXP_MATCH_INDICES, "js.regexp-match-indices")
               .deprecated(true)
               .deprecationMessage("")
               .help("Enable RegExp Match Indices property.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.regexp-static-result":
            return OptionDescriptor.newBuilder(JSContextOptions.REGEXP_STATIC_RESULT, "js.regexp-static-result")
               .deprecated(false)
               .help("Provide last RegExp match in RegExp global var, e.g. RegExp.$1.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.scope-optimization":
            return OptionDescriptor.newBuilder(JSContextOptions.SCOPE_OPTIMIZATION, "js.scope-optimization")
               .deprecated(false)
               .help("Allow scope optimizations around closures.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.script-engine-global-scope-import":
            return OptionDescriptor.newBuilder(JSContextOptions.SCRIPT_ENGINE_GLOBAL_SCOPE_IMPORT, "js.script-engine-global-scope-import")
               .deprecated(true)
               .deprecationMessage("")
               .help("Enable ScriptEngine-specific global scope import function.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.STABLE)
               .build();
         case "js.scripting":
            return OptionDescriptor.newBuilder(JSContextOptions.SCRIPTING, "js.scripting")
               .deprecated(false)
               .help("Enable scripting features (Nashorn compatibility option).")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.shared-array-buffer":
            return OptionDescriptor.newBuilder(JSContextOptions.SHARED_ARRAY_BUFFER, "js.shared-array-buffer")
               .deprecated(false)
               .help("Enable ECMAScript SharedArrayBuffer.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.shebang":
            return OptionDescriptor.newBuilder(JSContextOptions.SHEBANG, "js.shebang")
               .deprecated(false)
               .help("Allow parsing files starting with #!.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.shell":
            return OptionDescriptor.newBuilder(JSContextOptions.SHELL, "js.shell")
               .deprecated(false)
               .help("Provide global functions for js shell.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.stack-trace-limit":
            return OptionDescriptor.newBuilder(JSContextOptions.STACK_TRACE_LIMIT, "js.stack-trace-limit")
               .deprecated(false)
               .help("Number of stack frames to capture.")
               .usageSyntax("[0, inf)")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.strict":
            return OptionDescriptor.newBuilder(JSContextOptions.STRICT, "js.strict")
               .deprecated(false)
               .help("Enforce strict mode.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.STABLE)
               .build();
         case "js.string-lazy-substrings":
            return OptionDescriptor.newBuilder(JSContextOptions.STRING_LAZY_SUBSTRINGS, "js.string-lazy-substrings")
               .deprecated(false)
               .help("Allow lazy substrings.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.string-length-limit":
            return OptionDescriptor.newBuilder(JSContextOptions.STRING_LENGTH_LIMIT, "js.string-length-limit")
               .deprecated(false)
               .help("Maximum string length.")
               .usageSyntax("<chars>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.syntax-extensions":
            return OptionDescriptor.newBuilder(JSContextOptions.SYNTAX_EXTENSIONS, "js.syntax-extensions")
               .deprecated(false)
               .help("Enable Nashorn syntax extensions.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.temporal":
            return OptionDescriptor.newBuilder(JSContextOptions.TEMPORAL, "js.temporal")
               .deprecated(false)
               .help("Enable JavaScript Temporal API.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.test-clone-uninitialized":
            return OptionDescriptor.newBuilder(JSContextOptions.TEST_CLONE_UNINITIALIZED, "js.test-clone-uninitialized")
               .deprecated(false)
               .help("Test uninitialized cloning.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.test262-mode":
            return OptionDescriptor.newBuilder(JSContextOptions.TEST262_MODE, "js.test262-mode")
               .deprecated(false)
               .help("Expose global property $262 needed to run the Test262 harness.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.testV8-mode":
            return OptionDescriptor.newBuilder(JSContextOptions.TESTV8_MODE, "js.testV8-mode")
               .deprecated(false)
               .help("Expose internals needed to run the TestV8 harness.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.timer-resolution":
            return OptionDescriptor.newBuilder(JSContextOptions.TIMER_RESOLUTION, "js.timer-resolution")
               .deprecated(false)
               .help("Resolution of timers (performance.now() and Date built-ins) in nanoseconds. Fuzzy time is used when set to 0.")
               .usageSyntax("<nanoseconds>")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.timezone":
            return OptionDescriptor.newBuilder(JSContextOptions.TIME_ZONE, "js.timezone")
               .deprecated(false)
               .help("Set custom time zone ID.")
               .usageSyntax("<TimeZoneID>")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.top-level-await":
            return OptionDescriptor.newBuilder(JSContextOptions.TOP_LEVEL_AWAIT, "js.top-level-await")
               .deprecated(false)
               .help("Enable top-level-await.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.unhandled-rejections":
            return OptionDescriptor.newBuilder(JSContextOptions.UNHANDLED_REJECTIONS, "js.unhandled-rejections")
               .deprecated(false)
               .help(
                  "Configure unhandled promise rejections tracking. Accepted values: 'none', unhandled rejections are not tracked. 'warn', a warning is printed to stderr when an unhandled rejection is detected. 'throw', an exception is thrown when an unhandled rejection is detected. 'handler', the handler function set with Graal.setUnhandledPromiseRejectionHandler will be called with the rejection value and promise respectively as arguments."
               )
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.use-utc-for-legacy-dates":
            return OptionDescriptor.newBuilder(JSContextOptions.USE_UTC_FOR_LEGACY_DATES, "js.use-utc-for-legacy-dates")
               .deprecated(false)
               .help("Determines what time zone (UTC or local time zone) should be used when UTC offset is absent in a parsed date.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.STABLE)
               .build();
         case "js.v8-compat":
            return OptionDescriptor.newBuilder(JSContextOptions.V8_COMPATIBILITY_MODE, "js.v8-compat")
               .deprecated(false)
               .help("Provide compatibility with the Google V8 engine.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.v8-legacy-const":
            return OptionDescriptor.newBuilder(JSContextOptions.V8_LEGACY_CONST, "js.v8-legacy-const")
               .deprecated(false)
               .help("Emulate v8 behavior when trying to mutate const variables in non-strict mode.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.v8-realm-builtin":
            return OptionDescriptor.newBuilder(JSContextOptions.V8_REALM_BUILTIN, "js.v8-realm-builtin")
               .deprecated(false)
               .help("Provide Realm builtin compatible with V8's d8 shell.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.validate-regexp-literals":
            return OptionDescriptor.newBuilder(JSContextOptions.VALIDATE_REGEXP_LITERALS, "js.validate-regexp-literals")
               .deprecated(false)
               .help("Validate regexp literals at parse time.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.wasm-bigint":
            return OptionDescriptor.newBuilder(JSContextOptions.WASM_BIG_INT, "js.wasm-bigint")
               .deprecated(false)
               .help("Enable wasm i64 to javascript BigInt support")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.webassembly":
            return OptionDescriptor.newBuilder(JSContextOptions.WEBASSEMBLY, "js.webassembly")
               .deprecated(false)
               .help("Enable WebAssembly JavaScript API.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         case "js.zone-rules-based-time-zones":
            return OptionDescriptor.newBuilder(JSContextOptions.ZONE_RULES_BASED_TIME_ZONES, "js.zone-rules-based-time-zones")
               .deprecated(false)
               .help("Use ZoneRulesProvider instead of time-zone data from ICU4J.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build();
         default:
            return null;
      }
   }

   @Override
   public Iterator<OptionDescriptor> iterator() {
      return Arrays.asList(
            OptionDescriptor.newBuilder(JSContextOptions.AGENT_CAN_BLOCK, "js.agent-can-block")
               .deprecated(false)
               .help("Determines whether agents can block or not.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.ANNEX_B, "js.annex-b")
               .deprecated(false)
               .help("Enable ECMAScript Annex B features.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.ASYNC_STACK_TRACES, "js.async-stack-traces")
               .deprecated(false)
               .help("Include async function frames in stack traces.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.ATOMICS, "js.atomics")
               .deprecated(false)
               .help("Enable ECMAScript Atomics.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.AWAIT_OPTIMIZATION, "js.await-optimization")
               .deprecated(false)
               .help("Use PromiseResolve for Await.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.BIGINT, "js.bigint")
               .deprecated(false)
               .help("Provide an implementation of the BigInt proposal.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.BIND_MEMBER_FUNCTIONS, "js.bind-member-functions")
               .deprecated(false)
               .help("Bind functions returned by Value.getMember to the receiver object.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.CHARSET, "js.charset")
               .deprecated(false)
               .help("Charset used for decoding/encoding of the input/output streams.")
               .usageSyntax("UTF-8|UTF-32|<name>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.CLASS_FIELDS, "js.class-fields")
               .deprecated(false)
               .help("Enable the class public and private fields proposal.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.COMMONJS_CORE_MODULES_REPLACEMENTS, "js.commonjs-core-modules-replacements")
               .deprecated(false)
               .help("Npm packages used to replace global Node.js builtins.")
               .usageSyntax("<name>:<module>,...")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.COMMONJS_REQUIRE, "js.commonjs-require")
               .deprecated(false)
               .help("Enable CommonJS require emulation.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.COMMONJS_REQUIRE_CWD, "js.commonjs-require-cwd")
               .deprecated(false)
               .help("CommonJS default current working directory.")
               .usageSyntax("<path>")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.CONSOLE, "js.console")
               .deprecated(false)
               .help("Provide 'console' global property.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.CONST_AS_VAR, "js.const-as-var")
               .deprecated(false)
               .help("Parse const declarations as a var (legacy compatibility option).")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.DEBUG_BUILTIN, "js.debug-builtin")
               .deprecated(false)
               .help("Provide a non-API Debug builtin. Behaviour will likely change. Don't depend on this in production code.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.DEBUG_PROPERTY_NAME, "js.debug-property-name")
               .deprecated(false)
               .help("The name used for the Graal.js debug builtin.")
               .usageSyntax("<name>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.DIRECT_BYTE_BUFFER, "js.direct-byte-buffer")
               .deprecated(false)
               .help("Use direct (off-heap) byte buffer for typed arrays.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.DISABLE_EVAL, "js.disable-eval")
               .deprecated(false)
               .help("User code is not allowed to parse code via e.g. eval().")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.DISABLE_WITH, "js.disable-with")
               .deprecated(false)
               .help("User code is not allowed to use the 'with' statement.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.ECMASCRIPT_VERSION, "js.ecmascript-version")
               .deprecated(false)
               .help("ECMAScript version to be compatible with. Default is 'latest' (latest supported version), staged features are in 'staging'.")
               .usageSyntax("latest|staging|[5, 13]|[2015, 2022]")
               .category(OptionCategory.USER)
               .stability(OptionStability.STABLE)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.ERROR_CAUSE, "js.error-cause")
               .deprecated(false)
               .help("Enable the error cause proposal. Allows an error to be chained with a cause using the optional options parameter.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.ESM_BARE_SPECIFIER_RELATIVE_LOOKUP, "js.esm-bare-specifier-relative-lookup")
               .deprecated(false)
               .help("Resolve ESM bare specifiers relative to the importing module's path instead of attempting an absolute path lookup.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.ESM_EVAL_RETURNS_EXPORTS, "js.esm-eval-returns-exports")
               .deprecated(false)
               .help("Eval of an ES module through the polyglot API returns its exported symbols.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.FOREIGN_HASH_PROPERTIES, "js.foreign-hash-properties")
               .deprecated(false)
               .help("Allow getting/setting non-JS hash entries using the `[]` and `.` operators.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.FOREIGN_OBJECT_PROTOTYPE, "js.foreign-object-prototype")
               .deprecated(false)
               .help("Non-JS objects have prototype (Object/Function/Array.prototype) set.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.STABLE)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.FUNCTION_ARGUMENTS_LIMIT, "js.function-arguments-limit")
               .deprecated(false)
               .help("Maximum number of arguments for functions.")
               .usageSyntax("<int>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.FUNCTION_CACHE_LIMIT, "js.function-cache-limit")
               .deprecated(false)
               .help("Maximum allowed size of a function cache.")
               .usageSyntax("<int>")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.FUNCTION_CONSTRUCTOR_CACHE_SIZE, "js.function-constructor-cache-size")
               .deprecated(false)
               .help("Maximum size of the parsing cache used by the Function constructor to avoid re-parsing known sources.")
               .usageSyntax("<int>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.FUNCTION_STATEMENT_ERROR, "js.function-statement-error")
               .deprecated(false)
               .help("Treat hoistable function statements in blocks as an error (in ES5 mode).")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.GLOBAL_ARGUMENTS, "js.global-arguments")
               .deprecated(false)
               .help("Provide 'arguments' global property.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.GLOBAL_PROPERTY, "js.global-property")
               .deprecated(false)
               .help("Provide 'global' global property.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.GRAAL_BUILTIN, "js.graal-builtin")
               .deprecated(false)
               .help("Provide 'Graal' global property.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.IMPORT_ASSERTIONS, "js.import-assertions")
               .deprecated(false)
               .help("Enable import assertions")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.INTEROP_COMPLETE_PROMISES, "js.interop-complete-promises")
               .deprecated(false)
               .help("Resolve promises when crossing a polyglot language boundary.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.INTL_402, "js.intl-402")
               .deprecated(false)
               .help("Enable ECMAScript Internationalization API.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.STABLE)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.JAVA_PACKAGE_GLOBALS, "js.java-package-globals")
               .deprecated(false)
               .help("Provide Java package globals: Packages, java, javafx, javax, com, org, edu.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.JSON_MODULES, "js.json-modules")
               .deprecated(false)
               .help("Enable loading of json modules")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.LAZY_TRANSLATION, "js.lazy-translation")
               .deprecated(false)
               .help("Translate function bodies lazily.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.LOAD, "js.load")
               .deprecated(false)
               .help("Provide 'load' global function.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.LOAD_FROM_CLASSPATH, "js.load-from-classpath")
               .deprecated(false)
               .help("Allow 'load' to access 'classpath:' URLs. Do not use with untrusted code.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.LOAD_FROM_URL, "js.load-from-url")
               .deprecated(false)
               .help("Allow 'load' to access URLs. Do not use with untrusted code.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.LOCALE, "js.locale")
               .deprecated(false)
               .help("Use a specific default locale for locale-sensitive operations.")
               .usageSyntax("<locale>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.MAX_APPLY_ARGUMENT_LENGTH, "js.max-apply-argument-length")
               .deprecated(false)
               .help("Maximum allowed number of arguments allowed in an apply function.")
               .usageSyntax("<int>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.MAX_PROTOTYPE_CHAIN_LENGTH, "js.max-prototype-chain-length")
               .deprecated(false)
               .help("Maximum allowed length of a prototype chain.")
               .usageSyntax("<int>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.MAX_TYPED_ARRAY_LENGTH, "js.max-typed-array-length")
               .deprecated(false)
               .help("Maximum allowed length for TypedArrays.")
               .usageSyntax("<int>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.MLE_MODE, "js.mle-mode")
               .deprecated(false)
               .help("Provide a non-API MLE builtin. Behaviour will likely change. Don't depend on this in production code.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.NASHORN_COMPATIBILITY_MODE, "js.nashorn-compat")
               .deprecated(false)
               .help("Provide compatibility with the OpenJDK Nashorn engine. Do not use with untrusted code.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.NEW_SET_METHODS, "js.new-set-methods")
               .deprecated(false)
               .help("Enable new Set methods.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.OPERATOR_OVERLOADING, "js.operator-overloading")
               .deprecated(false)
               .help("Enable operator overloading")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.PARSE_ONLY, "js.parse-only")
               .deprecated(false)
               .help("Only parse source code, do not run it.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.PERFORMANCE, "js.performance")
               .deprecated(false)
               .help("Provide 'performance' global property.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.POLYGLOT_BUILTIN, "js.polyglot-builtin")
               .deprecated(true)
               .deprecationMessage("")
               .help("Provide 'Polyglot' global property.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.POLYGLOT_EVALFILE, "js.polyglot-evalfile")
               .deprecated(false)
               .help("Provide 'Polyglot.evalFile' function.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.PRINT, "js.print")
               .deprecated(false)
               .help("Provide 'print' global function.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.PRINT_NO_NEWLINE, "js.print-no-newline")
               .deprecated(false)
               .help("Print function will not print new line char.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.PRIVATE_FIELDS_IN, "js.private-fields-in")
               .deprecated(false)
               .help("Enable private field in in operator")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.PROFILE_TIME, "js.profile-time")
               .deprecated(false)
               .help("Enable time profiling.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.PROFILE_TIME_PRINT_CUMULATIVE, "js.profile-time-print-cumulative")
               .deprecated(false)
               .help("Print cumulative time when time profiling is enabled.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.PROPERTY_CACHE_LIMIT, "js.property-cache-limit")
               .deprecated(false)
               .help("Maximum allowed size of a property cache.")
               .usageSyntax("<int>")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.REGEX_CACHE_SIZE, "js.regex-cache-size")
               .deprecated(false)
               .help("Maximum size of the regex cache used by the RegExp constructor to avoid re-parsing known sources.")
               .usageSyntax("<int>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.REGEX_REGRESSION_TEST_MODE, "js.regex-regression-test-mode")
               .deprecated(false)
               .help("Test mode for TRegex.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.REGEX_ALWAYS_EAGER, "js.regex.always-eager")
               .deprecated(false)
               .help("Always match capture groups eagerly.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.REGEX_DUMP_AUTOMATA, "js.regex.dump-automata")
               .deprecated(false)
               .help("Produce ASTs and automata in JSON, DOT (GraphViz) and LaTeX formats.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.REGEX_STEP_EXECUTION, "js.regex.step-execution")
               .deprecated(false)
               .help("Trace the execution of automata in JSON files.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.REGEXP_MATCH_INDICES, "js.regexp-match-indices")
               .deprecated(true)
               .deprecationMessage("")
               .help("Enable RegExp Match Indices property.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.REGEXP_STATIC_RESULT, "js.regexp-static-result")
               .deprecated(false)
               .help("Provide last RegExp match in RegExp global var, e.g. RegExp.$1.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.SCOPE_OPTIMIZATION, "js.scope-optimization")
               .deprecated(false)
               .help("Allow scope optimizations around closures.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.SCRIPT_ENGINE_GLOBAL_SCOPE_IMPORT, "js.script-engine-global-scope-import")
               .deprecated(true)
               .deprecationMessage("")
               .help("Enable ScriptEngine-specific global scope import function.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.STABLE)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.SCRIPTING, "js.scripting")
               .deprecated(false)
               .help("Enable scripting features (Nashorn compatibility option).")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.SHARED_ARRAY_BUFFER, "js.shared-array-buffer")
               .deprecated(false)
               .help("Enable ECMAScript SharedArrayBuffer.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.SHEBANG, "js.shebang")
               .deprecated(false)
               .help("Allow parsing files starting with #!.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.SHELL, "js.shell")
               .deprecated(false)
               .help("Provide global functions for js shell.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.STACK_TRACE_LIMIT, "js.stack-trace-limit")
               .deprecated(false)
               .help("Number of stack frames to capture.")
               .usageSyntax("[0, inf)")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.STRICT, "js.strict")
               .deprecated(false)
               .help("Enforce strict mode.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.STABLE)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.STRING_LAZY_SUBSTRINGS, "js.string-lazy-substrings")
               .deprecated(false)
               .help("Allow lazy substrings.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.STRING_LENGTH_LIMIT, "js.string-length-limit")
               .deprecated(false)
               .help("Maximum string length.")
               .usageSyntax("<chars>")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.SYNTAX_EXTENSIONS, "js.syntax-extensions")
               .deprecated(false)
               .help("Enable Nashorn syntax extensions.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.TEMPORAL, "js.temporal")
               .deprecated(false)
               .help("Enable JavaScript Temporal API.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.TEST_CLONE_UNINITIALIZED, "js.test-clone-uninitialized")
               .deprecated(false)
               .help("Test uninitialized cloning.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.TEST262_MODE, "js.test262-mode")
               .deprecated(false)
               .help("Expose global property $262 needed to run the Test262 harness.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.TESTV8_MODE, "js.testV8-mode")
               .deprecated(false)
               .help("Expose internals needed to run the TestV8 harness.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.TIMER_RESOLUTION, "js.timer-resolution")
               .deprecated(false)
               .help("Resolution of timers (performance.now() and Date built-ins) in nanoseconds. Fuzzy time is used when set to 0.")
               .usageSyntax("<nanoseconds>")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.TIME_ZONE, "js.timezone")
               .deprecated(false)
               .help("Set custom time zone ID.")
               .usageSyntax("<TimeZoneID>")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.TOP_LEVEL_AWAIT, "js.top-level-await")
               .deprecated(false)
               .help("Enable top-level-await.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.UNHANDLED_REJECTIONS, "js.unhandled-rejections")
               .deprecated(false)
               .help(
                  "Configure unhandled promise rejections tracking. Accepted values: 'none', unhandled rejections are not tracked. 'warn', a warning is printed to stderr when an unhandled rejection is detected. 'throw', an exception is thrown when an unhandled rejection is detected. 'handler', the handler function set with Graal.setUnhandledPromiseRejectionHandler will be called with the rejection value and promise respectively as arguments."
               )
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.USE_UTC_FOR_LEGACY_DATES, "js.use-utc-for-legacy-dates")
               .deprecated(false)
               .help("Determines what time zone (UTC or local time zone) should be used when UTC offset is absent in a parsed date.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.STABLE)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.V8_COMPATIBILITY_MODE, "js.v8-compat")
               .deprecated(false)
               .help("Provide compatibility with the Google V8 engine.")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.V8_LEGACY_CONST, "js.v8-legacy-const")
               .deprecated(false)
               .help("Emulate v8 behavior when trying to mutate const variables in non-strict mode.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.V8_REALM_BUILTIN, "js.v8-realm-builtin")
               .deprecated(false)
               .help("Provide Realm builtin compatible with V8's d8 shell.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.VALIDATE_REGEXP_LITERALS, "js.validate-regexp-literals")
               .deprecated(false)
               .help("Validate regexp literals at parse time.")
               .usageSyntax("")
               .category(OptionCategory.INTERNAL)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.WASM_BIG_INT, "js.wasm-bigint")
               .deprecated(false)
               .help("Enable wasm i64 to javascript BigInt support")
               .usageSyntax("")
               .category(OptionCategory.USER)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.WEBASSEMBLY, "js.webassembly")
               .deprecated(false)
               .help("Enable WebAssembly JavaScript API.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build(),
            OptionDescriptor.newBuilder(JSContextOptions.ZONE_RULES_BASED_TIME_ZONES, "js.zone-rules-based-time-zones")
               .deprecated(false)
               .help("Use ZoneRulesProvider instead of time-zone data from ICU4J.")
               .usageSyntax("")
               .category(OptionCategory.EXPERT)
               .stability(OptionStability.EXPERIMENTAL)
               .build()
         )
         .iterator();
   }
}
