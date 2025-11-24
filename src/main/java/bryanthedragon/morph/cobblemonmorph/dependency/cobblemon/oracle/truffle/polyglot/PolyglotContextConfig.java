
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.polyglot.FileSystems;
import com.oracle.truffle.polyglot.OptionValuesImpl;
import com.oracle.truffle.polyglot.PolyglotEngineException;
import com.oracle.truffle.polyglot.PolyglotEngineImpl;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotInstrument;
import com.oracle.truffle.polyglot.PolyglotLanguage;
import com.oracle.truffle.polyglot.PolyglotLimits;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.function.Consumer;
import java.util.function.Predicate;
import java.util.logging.Handler;
import java.util.logging.Level;
import org.graalvm.collections.UnmodifiableEconomicSet;
import org.graalvm.polyglot.EnvironmentAccess;
import org.graalvm.polyglot.HostAccess;
import org.graalvm.polyglot.PolyglotAccess;
import org.graalvm.polyglot.io.FileSystem;
import org.graalvm.polyglot.io.ProcessHandler;

final class PolyglotContextConfig {
    private static final String[] EMPTY_STRING_ARRAY = new String[0];
    final OutputStream out;
    final OutputStream err;
    final InputStream in;
    final boolean hostLookupAllowed;
    final boolean nativeAccessAllowed;
    final boolean createThreadAllowed;
    final boolean hostClassLoadingAllowed;
    final boolean innerContextOptionsAllowed;
    final boolean createProcessAllowed;
    final Predicate<String> classFilter;
    private final Map<String, String[]> applicationArguments;
    final Set<String> onlyLanguages;
    final Set<String> allowedPublicLanguages;
    final Map<String, String> originalOptions;
    private final Map<String, OptionValuesImpl> optionsById;
    @CompilerDirectives.CompilationFinal
    FileSystem fileSystem;
    @CompilerDirectives.CompilationFinal
    FileSystem internalFileSystem;
    final Map<String, Level> logLevels;
    final Handler logHandler;
    final PolyglotAccess polyglotAccess;
    final ProcessHandler processHandler;
    final EnvironmentAccess environmentAccess;
    final Map<String, String> customEnvironment;
    private volatile Map<String, String> resolvedEnvironment;
    final ZoneId timeZone;
    final PolyglotLimits limits;
    final ClassLoader hostClassLoader;
    private final List<PolyglotInstrument> configuredInstruments;
    private final Set<PolyglotLanguage> configuredLanguages;
    final HostAccess hostAccess;
    final Boolean forceCodeSharing;
    final boolean allowValueSharing;
    final boolean useSystemExit;
    final boolean allowExperimentalOptions;
    final Map<String, Object> creatorArguments;
    final Runnable onCancelled;
    final Consumer<Integer> onExited;
    final Runnable onClosed;

    PolyglotContextConfig(PolyglotEngineImpl engine, FileSystem fs, FileSystem internalFs, PreinitConfig sharableConfig) {
        this(engine, null, System.out, System.err, System.in, false, sharableConfig.polyglotAccess, sharableConfig.nativeAccessAllowed, sharableConfig.createThreadAllowed, false, false, false, null, Collections.emptyMap(), Collections.emptySet(), sharableConfig.originalOptions, fs, internalFs, engine.logHandler, sharableConfig.createProcessAllowed, null, EnvironmentAccess.INHERIT, null, sharableConfig.timeZone, null, null, null, sharableConfig.allowValueSharing, sharableConfig.useSystemExit, null, null, null, null);
    }

    PolyglotContextConfig(PolyglotEngineImpl engine, Boolean forceSharing, OutputStream out, OutputStream err, InputStream in, boolean hostLookupAllowed, PolyglotAccess polyglotAccess, boolean nativeAccessAllowed, boolean createThreadAllowed, boolean hostClassLoadingAllowed, boolean contextOptionsAllowed, boolean allowExperimentalOptions, Predicate<String> classFilter, Map<String, String[]> applicationArguments, Set<String> onlyLanguages, Map<String, String> options, FileSystem publicFileSystem, FileSystem internalFileSystem, Handler logHandler, boolean createProcessAllowed, ProcessHandler processHandler, EnvironmentAccess environmentAccess, Map<String, String> environment, ZoneId timeZone, PolyglotLimits limits, ClassLoader hostClassLoader, HostAccess hostAccess, boolean allowValueSharing, boolean useSystemExit, Map<String, Object> creatorArguments, Runnable onCancelled, Consumer<Integer> onExited, Runnable onClosed) {
        assert (out != null);
        assert (err != null);
        assert (in != null);
        assert (environmentAccess != null);
        this.forceCodeSharing = forceSharing;
        this.out = out;
        this.err = err;
        this.in = in;
        this.hostLookupAllowed = hostLookupAllowed;
        this.polyglotAccess = polyglotAccess;
        this.nativeAccessAllowed = nativeAccessAllowed;
        this.createThreadAllowed = createThreadAllowed;
        this.hostClassLoadingAllowed = hostClassLoadingAllowed;
        this.innerContextOptionsAllowed = contextOptionsAllowed;
        this.allowExperimentalOptions = allowExperimentalOptions;
        this.createProcessAllowed = createProcessAllowed;
        this.classFilter = classFilter;
        this.applicationArguments = applicationArguments;
        this.onlyLanguages = onlyLanguages;
        this.allowedPublicLanguages = onlyLanguages.isEmpty() ? engine.getLanguages().keySet() : onlyLanguages;
        this.fileSystem = publicFileSystem;
        this.internalFileSystem = internalFileSystem;
        this.optionsById = new HashMap<String, OptionValuesImpl>();
        this.logHandler = logHandler;
        this.timeZone = timeZone;
        this.limits = limits;
        this.logLevels = new HashMap<String, Level>(engine.logLevels);
        this.allowValueSharing = allowValueSharing;
        this.originalOptions = options;
        ArrayList<PolyglotInstrument> instruments = null;
        LinkedHashSet<PolyglotLanguage> languages = new LinkedHashSet<PolyglotLanguage>();
        for (String id : onlyLanguages) {
            this.addConfiguredLanguage(engine, languages, engine.idToLanguage.get(id));
        }
        for (String optionKey : options.keySet()) {
            OptionValuesImpl engineOptionValues;
            String id;
            String group = PolyglotEngineImpl.parseOptionGroup(optionKey);
            if (group.equals("log")) {
                this.logLevels.put(PolyglotEngineImpl.parseLoggerName(optionKey), Level.parse(options.get(optionKey)));
                continue;
            }
            PolyglotImpl.VMObject object = PolyglotContextConfig.findObjectForContextOption(engine, optionKey, group);
            if (object instanceof PolyglotLanguage) {
                PolyglotLanguage language = (PolyglotLanguage)object;
                id = language.getId();
                engineOptionValues = language.getOptionValues();
                this.addConfiguredLanguage(engine, languages, language);
            } else if (object instanceof PolyglotInstrument) {
                PolyglotInstrument instrument = (PolyglotInstrument)object;
                id = instrument.getId();
                engineOptionValues = instrument.getEngineOptionValues();
                if (instruments == null) {
                    instruments = new ArrayList<PolyglotInstrument>();
                }
                instruments.add(instrument);
            } else {
                throw new AssertionError((Object)"invalid vm object");
            }
            OptionValuesImpl targetOptions = this.optionsById.get(id);
            if (targetOptions == null) {
                targetOptions = engineOptionValues.copy();
                this.optionsById.put(id, targetOptions);
            }
            targetOptions.put(engine, optionKey, options.get(optionKey), allowExperimentalOptions);
        }
        this.configuredInstruments = instruments == null ? Collections.emptyList() : instruments;
        this.configuredLanguages = languages == null ? Collections.emptySet() : languages;
        this.processHandler = processHandler;
        this.environmentAccess = environmentAccess;
        this.customEnvironment = environment == null || environment.isEmpty() ? Collections.emptyMap() : new HashMap<String, String>(environment);
        this.hostAccess = hostAccess;
        this.hostClassLoader = hostClassLoader;
        this.useSystemExit = useSystemExit;
        this.creatorArguments = creatorArguments;
        this.onCancelled = onCancelled;
        this.onExited = onExited;
        this.onClosed = onClosed;
    }

    boolean isCodeSharingForced() {
        return this.forceCodeSharing != null && this.forceCodeSharing != false;
    }

    boolean isCodeSharingDisabled() {
        return this.forceCodeSharing != null && this.forceCodeSharing == false;
    }

    void addConfiguredLanguage(PolyglotEngineImpl engine, Set<PolyglotLanguage> languages, PolyglotLanguage language) {
        if (language != null && languages.add(language)) {
            this.collectDependentLanguages(engine, language.cache.getDependentLanguages(), languages);
        }
    }

    private void collectDependentLanguages(PolyglotEngineImpl engine, Collection<String> languageIds, Collection<PolyglotLanguage> foundLanguages) {
        for (String id : languageIds) {
            PolyglotLanguage language = engine.idToLanguage.get(id);
            if (language == null || !foundLanguages.add(language)) continue;
            this.collectDependentLanguages(engine, language.cache.getDependentLanguages(), foundLanguages);
        }
    }

    boolean isAllowIO() {
        return !FileSystems.hasNoAccess(this.fileSystem);
    }

    ZoneId getTimeZone() {
        ZoneId zone = this.timeZone;
        if (zone == null) {
            zone = ZoneId.systemDefault();
        }
        return zone;
    }

    boolean isAccessPermitted(PolyglotLanguage from, PolyglotLanguage to) {
        if (to.isHost() || to.cache.isInternal()) {
            return true;
        }
        if (from == to) {
            return true;
        }
        if (from == null) {
            if (this.allowedPublicLanguages.contains(to.info.getId())) {
                return true;
            }
        } else {
            if (this.polyglotAccess == PolyglotAccess.ALL) {
                if (this.allowedPublicLanguages.contains(to.info.getId())) {
                    return true;
                }
            } else {
                if (from == to) {
                    return true;
                }
                UnmodifiableEconomicSet<String> configuredAccess = from.engine.getAPIAccess().getEvalAccess(this.polyglotAccess, from.getId());
                if (configuredAccess != null && configuredAccess.contains(to.getId())) {
                    return true;
                }
            }
            if (from.dependsOn(to)) {
                return true;
            }
        }
        return false;
    }

    String[] getApplicationArguments(PolyglotLanguage lang) {
        String[] args = this.applicationArguments.get(lang.getId());
        if (args == null) {
            args = EMPTY_STRING_ARRAY;
        }
        return args;
    }

    OptionValuesImpl getLanguageOptionValues(PolyglotLanguage lang) {
        OptionValuesImpl values = this.optionsById.get(lang.getId());
        if (values == null) {
            values = lang.getOptionValues();
        }
        return values;
    }

    OptionValuesImpl getInstrumentOptionValues(PolyglotInstrument instrument) {
        OptionValuesImpl values = this.optionsById.get(instrument.getId());
        if (values == null) {
            values = instrument.getEngineOptionValues();
        }
        return values.copy();
    }

    Set<PolyglotLanguage> getConfiguredLanguages() {
        return this.configuredLanguages;
    }

    Collection<? extends PolyglotInstrument> getConfiguredInstruments() {
        return this.configuredInstruments;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    Map<String, String> getEnvironment() {
        Map<String, String> result = this.resolvedEnvironment;
        if (result == null) {
            PolyglotContextConfig polyglotContextConfig = this;
            synchronized (polyglotContextConfig) {
                result = this.resolvedEnvironment;
                if (result == null) {
                    if (this.environmentAccess == EnvironmentAccess.NONE) {
                        result = Collections.unmodifiableMap(this.customEnvironment);
                    } else if (PolyglotEngineImpl.ALLOW_ENVIRONMENT_ACCESS && this.environmentAccess == EnvironmentAccess.INHERIT) {
                        result = System.getenv();
                        if (!this.customEnvironment.isEmpty()) {
                            result = new HashMap<String, String>(result);
                            result.putAll(this.customEnvironment);
                            result = Collections.unmodifiableMap(result);
                        }
                    } else {
                        throw PolyglotEngineException.unsupported(String.format("Unsupported EnvironmentAccess: %s", this.environmentAccess));
                    }
                    this.resolvedEnvironment = result;
                }
            }
        }
        return result;
    }

    private static PolyglotImpl.VMObject findObjectForContextOption(PolyglotEngineImpl engine, String optionKey, String group) {
        PolyglotLanguage language = engine.idToLanguage.get(group);
        if (language == null) {
            PolyglotInstrument instrument = engine.idToInstrument.get(group);
            if (instrument != null) {
                if (instrument.getEngineOptionsInternal().get(optionKey) != null) {
                    throw PolyglotEngineException.illegalArgument("Option " + optionKey + " is an engine level instrument option. Engine level instrument options can only be configured for contexts without an explicit engine set. To resolve this, configure the option when creating the Engine or create a context without a shared engine.");
                }
                return instrument;
            }
            if (group.equals("engine") && engine.getAllOptions().get(optionKey) != null) {
                throw PolyglotEngineException.illegalArgument("Option " + optionKey + " is an engine option. Engine level options can only be configured for contexts without a shared engine set. To resolve this, configure the option when creating the Engine or create a context without a shared engine.");
            }
            throw OptionValuesImpl.failNotFound(engine.getAllOptions(), optionKey);
        }
        assert (!group.equals("engine"));
        return language;
    }

    static class PreinitConfig {
        static final PreinitConfig DEFAULT = new PreinitConfig();
        static final PreinitConfig DEFAULT_WITH_NATIVE_ACCESS = new PreinitConfig(true);
        final boolean nativeAccessAllowed;
        final boolean createThreadAllowed;
        final boolean createProcessAllowed;
        final Map<String, String> originalOptions;
        final PolyglotAccess polyglotAccess;
        final ZoneId timeZone;
        final boolean allowValueSharing;
        final boolean useSystemExit;

        private PreinitConfig() {
            this(false);
        }

        private PreinitConfig(boolean nativeAccessAllowed) {
            this.nativeAccessAllowed = nativeAccessAllowed;
            this.createThreadAllowed = false;
            this.createProcessAllowed = false;
            this.originalOptions = Collections.emptyMap();
            this.polyglotAccess = PolyglotAccess.ALL;
            this.timeZone = null;
            this.allowValueSharing = true;
            this.useSystemExit = false;
        }

        PreinitConfig(PolyglotContextConfig config) {
            this.nativeAccessAllowed = config.nativeAccessAllowed;
            this.createThreadAllowed = config.createThreadAllowed;
            this.createProcessAllowed = config.createProcessAllowed;
            this.originalOptions = config.originalOptions;
            this.polyglotAccess = config.polyglotAccess;
            this.timeZone = config.timeZone;
            this.allowValueSharing = config.allowValueSharing;
            this.useSystemExit = config.useSystemExit;
        }

        PreinitConfig(PreinitConfig prev, PolyglotContextConfig config) {
            this.nativeAccessAllowed = prev.nativeAccessAllowed == config.nativeAccessAllowed ? config.nativeAccessAllowed : PreinitConfig.DEFAULT.nativeAccessAllowed;
            this.createThreadAllowed = prev.createThreadAllowed == config.createThreadAllowed ? config.createThreadAllowed : PreinitConfig.DEFAULT.createThreadAllowed;
            this.createProcessAllowed = prev.createProcessAllowed == config.createProcessAllowed ? config.createProcessAllowed : PreinitConfig.DEFAULT.createProcessAllowed;
            this.originalOptions = Objects.equals(prev.originalOptions, config.originalOptions) ? config.originalOptions : PreinitConfig.computeCommonOptions(prev.originalOptions, config.originalOptions);
            this.polyglotAccess = Objects.equals(prev.polyglotAccess, config.polyglotAccess) ? config.polyglotAccess : PreinitConfig.DEFAULT.polyglotAccess;
            this.timeZone = Objects.equals(prev.timeZone, config.timeZone) ? config.timeZone : PreinitConfig.DEFAULT.timeZone;
            this.allowValueSharing = prev.allowValueSharing == config.allowValueSharing ? config.allowValueSharing : PreinitConfig.DEFAULT.allowValueSharing;
            this.useSystemExit = prev.useSystemExit == config.useSystemExit ? config.useSystemExit : PreinitConfig.DEFAULT.useSystemExit;
        }

        private static Map<String, String> computeCommonOptions(Map<String, String> options1, Map<String, String> options2) {
            if (options1.isEmpty() || options2.isEmpty()) {
                return PreinitConfig.DEFAULT.originalOptions;
            }
            HashMap<String, String> commonOptions = new HashMap<String, String>();
            for (Map.Entry<String, String> entry1 : options1.entrySet()) {
                String key1 = entry1.getKey();
                String value2 = options2.get(key1);
                if (value2 == null || !Objects.equals(entry1.getValue(), value2)) continue;
                commonOptions.put(key1, value2);
            }
            return commonOptions;
        }
    }
}

