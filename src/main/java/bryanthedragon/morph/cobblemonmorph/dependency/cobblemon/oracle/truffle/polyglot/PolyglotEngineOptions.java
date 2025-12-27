package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.Option;
import java.util.function.Function;
import org.graalvm.options.OptionCategory;
import org.graalvm.options.OptionKey;
import org.graalvm.options.OptionStability;
import org.graalvm.options.OptionType;

@Option.Group("engine")
final class PolyglotEngineOptions {
   static final String PREINITIALIZE_CONTEXT_NAME = "PreinitializeContexts";
   private static final String INSTRUMENT_EXCEPTIONS_ARE_THROWN_NAME = "InstrumentExceptionsAreThrown";
   @Option(name = "PreinitializeContexts", category = OptionCategory.EXPERT, deprecated = true, help = "Preinitialize language contexts for given languages.")
   static final OptionKey<String> PreinitializeContexts = new OptionKey<>("");
   @Option(
      name = "InstrumentExceptionsAreThrown",
      category = OptionCategory.INTERNAL,
      help = "Propagates exceptions thrown by instruments. (default: true)",
      usageSyntax = "true|false"
   )
   static final OptionKey<Boolean> InstrumentExceptionsAreThrown = new OptionKey<>(true);
   @Option(
      category = OptionCategory.INTERNAL,
      stability = OptionStability.EXPERIMENTAL,
      help = "Propagates cancel execution exception into UncaughtExceptionHandler. For testing purposes only."
   )
   static final OptionKey<Boolean> TriggerUncaughtExceptionHandlerForCancel = new OptionKey<>(false);
   @Option(
      category = OptionCategory.INTERNAL,
      stability = OptionStability.EXPERIMENTAL,
      help = "Show internal frames specific to the language implementation in stack traces."
   )
   static final OptionKey<Boolean> ShowInternalStackFrames = new OptionKey<>(false);
   @Option(
      category = OptionCategory.INTERNAL,
      stability = OptionStability.EXPERIMENTAL,
      help = "Enables conservative context references. This allows invalid sharing between contexts. For testing purposes only.",
      deprecated = true,
      deprecationMessage = "Has no longer any effect. Scheduled for removal in in 22.1."
   )
   static final OptionKey<Boolean> UseConservativeContextReferences = new OptionKey<>(false);
   @Option(
      category = OptionCategory.INTERNAL,
      stability = OptionStability.EXPERIMENTAL,
      help = "Enables specialization statistics for nodes generated with Truffle DSL and prints the result on exit. In order for this flag to be functional -Atruffle.dsl.GenerateSpecializationStatistics=true needs to be set at build time. Enabling this flag and the compiler option has major implications on the performance and footprint of the interpreter. Do not use in production environments."
   )
   static final OptionKey<Boolean> SpecializationStatistics = new OptionKey<>(false);
   @Option(
      category = OptionCategory.INTERNAL,
      stability = OptionStability.EXPERIMENTAL,
      help = "Traces thread local events and when they are processed on the individual threads.Prints messages with the [engine] [tl] prefix. "
   )
   static final OptionKey<Boolean> TraceThreadLocalActions = new OptionKey<>(false);
   @Option(
      category = OptionCategory.INTERNAL,
      stability = OptionStability.EXPERIMENTAL,
      help = "Repeadly submits thread local actions and collects statistics about safepoint intervals in the process. Prints event and interval statistics when the context is closed for each thread. This option significantly slows down execution and is therefore intended for testing purposes only."
   )
   static final OptionKey<Boolean> SafepointALot = new OptionKey<>(false);
   @Option(
      category = OptionCategory.EXPERT,
      stability = OptionStability.EXPERIMENTAL,
      help = "Prints the stack trace for all threads for a time interval. By default 0, which disables the output.",
      usageSyntax = "[1, inf)"
   )
   static final OptionKey<Long> TraceStackTraceInterval = new OptionKey<>(0L);
   @Option(
      category = OptionCategory.USER,
      stability = OptionStability.STABLE,
      help = "Print warning when the engine is using a default Truffle runtime (default: true).",
      usageSyntax = "true|false"
   )
   static final OptionKey<Boolean> WarnInterpreterOnly = new OptionKey<>(true);
   @Option(
      category = OptionCategory.INTERNAL,
      stability = OptionStability.EXPERIMENTAL,
      help = "Use pre-initialized context when it's available (default: true).",
      usageSyntax = "true|false"
   )
   static final OptionKey<Boolean> UsePreInitializedContext = new OptionKey<>(true);
   @Option(
      category = OptionCategory.EXPERT,
      stability = OptionStability.EXPERIMENTAL,
      help = "On property accesses, the Static Object Model does not perform shape checks and uses unsafe casts"
   )
   static final OptionKey<Boolean> RelaxStaticObjectSafetyChecks = new OptionKey<>(false);
   @Option(
      category = OptionCategory.INTERNAL,
      stability = OptionStability.EXPERIMENTAL,
      help = "Option to force enable code sharing for this engine, even if the context was created with a bound engine. This option is intended for testing purposes only."
   )
   static final OptionKey<Boolean> ForceCodeSharing = new OptionKey<>(false);
   @Option(
      category = OptionCategory.INTERNAL,
      stability = OptionStability.EXPERIMENTAL,
      help = "Option to force disable code sharing for this engine, even if the context was created with an explicit engine. This option is intended for testing purposes only."
   )
   static final OptionKey<Boolean> DisableCodeSharing = new OptionKey<>(false);
   @Option(
      category = OptionCategory.INTERNAL,
      stability = OptionStability.EXPERIMENTAL,
      help = "Enables printing of code sharing related information to the logger. This option is intended to support debugging language implementations."
   )
   static final OptionKey<Boolean> TraceCodeSharing = new OptionKey<>(false);
   @Option(
      category = OptionCategory.INTERNAL,
      stability = OptionStability.EXPERIMENTAL,
      help = "Set the storage strategy used by the Static Object Model. Accepted values are: ['default', 'array-based', 'field-based']",
      usageSyntax = "default|array-based|field-based"
   )
   static final OptionKey<PolyglotEngineOptions.StaticObjectStorageStrategies> StaticObjectStorageStrategy = new OptionKey<>(
      PolyglotEngineOptions.StaticObjectStorageStrategies.DEFAULT,
      new OptionType<>("strategy", new Function<String, PolyglotEngineOptions.StaticObjectStorageStrategies>() {
         public PolyglotEngineOptions.StaticObjectStorageStrategies apply(String s) {
            switch (s) {
               case "default":
                  return PolyglotEngineOptions.StaticObjectStorageStrategies.DEFAULT;
               case "array-based":
                  return PolyglotEngineOptions.StaticObjectStorageStrategies.ARRAY_BASED;
               case "field-based":
                  return PolyglotEngineOptions.StaticObjectStorageStrategies.FIELD_BASED;
               default:
                  throw new IllegalArgumentException("Unexpected value for engine option 'SomStorageStrategy': " + s);
            }
         }
      })
   );

   static enum StaticObjectStorageStrategies {
      DEFAULT,
      ARRAY_BASED,
      FIELD_BASED;
   }
}
