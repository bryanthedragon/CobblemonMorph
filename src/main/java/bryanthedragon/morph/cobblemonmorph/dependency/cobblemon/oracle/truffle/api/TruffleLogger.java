
package com.oracle.truffle.api;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.LanguageAccessor;
import com.oracle.truffle.api.Truffle;
import java.io.Closeable;
import java.lang.ref.Reference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public final class TruffleLogger {
    private static final String ROOT_NAME = "";
    private static final int MAX_CLEANED_REFS = 100;
    private static final int OFF_VALUE = Level.OFF.intValue();
    private static final int DEFAULT_VALUE = Level.INFO.intValue();
    private static final ReferenceQueue<TruffleLogger> loggersRefQueue = new ReferenceQueue();
    private static final Object childrenLock = new Object();
    private final String name;
    private final LoggerCache loggerCache;
    @CompilerDirectives.CompilationFinal
    private volatile int levelNum;
    @CompilerDirectives.CompilationFinal
    private volatile Assumption levelNumStable;
    private volatile Level levelObj;
    private volatile TruffleLogger parent;
    private Collection<ChildLoggerRef> children;

    private TruffleLogger(String loggerName, LoggerCache loggerCache) {
        this.name = loggerName;
        this.loggerCache = loggerCache;
        this.levelNum = DEFAULT_VALUE;
        this.levelNumStable = Truffle.getRuntime().createAssumption("Log Level Value stable for: " + loggerName);
    }

    private TruffleLogger(LoggerCache loggerCache) {
        this(ROOT_NAME, loggerCache);
    }

    public static TruffleLogger getLogger(String id) {
        return TruffleLogger.getLogger(id, null, LoggerCache.getInstance());
    }

    public static TruffleLogger getLogger(String id, Class<?> forClass) {
        Objects.requireNonNull(forClass, "Class must be non null.");
        return TruffleLogger.getLogger(id, forClass.getName());
    }

    public static TruffleLogger getLogger(String id, String loggerName) {
        return TruffleLogger.getLogger(id, loggerName, LoggerCache.getInstance());
    }

    static TruffleLogger getLogger(String id, String loggerName, LoggerCache loggerCache) {
        Objects.requireNonNull(id, "LanguageId must be non null.");
        return loggerCache.getOrCreateLogger(id, loggerName);
    }

    LoggerCache getLoggerCache() {
        return this.loggerCache;
    }

    public void config(String message) {
        this.log(Level.CONFIG, message);
    }

    public void config(Supplier<String> messageSupplier) {
        this.log(Level.CONFIG, messageSupplier);
    }

    public void entering(String sourceClass, String sourceMethod) {
        this.logp(Level.FINER, sourceClass, sourceMethod, "ENTRY");
    }

    public void entering(String sourceClass, String sourceMethod, Object parameter) {
        this.logp(Level.FINER, sourceClass, sourceMethod, "ENTRY {0}", parameter);
    }

    public void entering(String sourceClass, String sourceMethod, Object[] parameters) {
        Object msg = "ENTRY";
        if (parameters == null) {
            this.logp(Level.FINER, sourceClass, sourceMethod, (String)msg);
            return;
        }
        if (!this.isLoggable(Level.FINER)) {
            return;
        }
        for (int i = 0; i < parameters.length; ++i) {
            msg = (String)msg + " {" + i + "}";
        }
        this.logp(Level.FINER, sourceClass, sourceMethod, (String)msg, parameters);
    }

    public void exiting(String sourceClass, String sourceMethod) {
        this.logp(Level.FINER, sourceClass, sourceMethod, "RETURN");
    }

    public void exiting(String sourceClass, String sourceMethod, Object result) {
        this.logp(Level.FINER, sourceClass, sourceMethod, "RETURN {0}", result);
    }

    public void fine(String message) {
        this.log(Level.FINE, message);
    }

    public void fine(Supplier<String> messageSupplier) {
        this.log(Level.FINE, messageSupplier);
    }

    public void finer(String message) {
        this.log(Level.FINER, message);
    }

    public void finer(Supplier<String> messageSupplier) {
        this.log(Level.FINER, messageSupplier);
    }

    public void finest(String message) {
        this.log(Level.FINEST, message);
    }

    public void finest(Supplier<String> messageSupplier) {
        this.log(Level.FINEST, messageSupplier);
    }

    public void info(String message) {
        this.log(Level.INFO, message);
    }

    public void info(Supplier<String> messageSupplier) {
        this.log(Level.INFO, messageSupplier);
    }

    public void severe(String message) {
        this.log(Level.SEVERE, message);
    }

    public void severe(Supplier<String> messageSupplier) {
        this.log(Level.SEVERE, messageSupplier);
    }

    public <T extends Throwable> T throwing(String sourceClass, String sourceMethod, T thrown) {
        this.logp(Level.FINER, sourceClass, sourceMethod, "THROW", thrown);
        return thrown;
    }

    public void warning(String message) {
        this.log(Level.WARNING, message);
    }

    public void warning(Supplier<String> messageSupplier) {
        this.log(Level.WARNING, messageSupplier);
    }

    public void log(Level level, String message) {
        if (!this.isLoggable(level)) {
            return;
        }
        this.doLog(level, message, (String)null, (String)null, (Object[])null);
    }

    public void log(Level level, Supplier<String> messageSupplier) {
        if (!this.isLoggable(level)) {
            return;
        }
        this.doLog(level, messageSupplier, null, null, null);
    }

    public void log(Level level, String message, Object parameter) {
        if (!this.isLoggable(level)) {
            return;
        }
        this.doLog(level, message, null, null, parameter);
    }

    public void log(Level level, String message, Object[] parameters) {
        if (!this.isLoggable(level)) {
            return;
        }
        this.doLog(level, message, (String)null, (String)null, parameters);
    }

    public void log(Level level, String message, Throwable thrown) {
        if (!this.isLoggable(level)) {
            return;
        }
        this.doLog(level, message, null, null, thrown);
    }

    public void log(Level level, Throwable thrown, Supplier<String> messageSupplier) {
        if (!this.isLoggable(level)) {
            return;
        }
        this.doLog(level, messageSupplier, null, null, thrown);
    }

    public void logp(Level level, String sourceClass, String sourceMethod, String message) {
        if (!this.isLoggable(level)) {
            return;
        }
        this.doLog(level, message, sourceClass, sourceMethod, (Object[])null);
    }

    public void logp(Level level, String sourceClass, String sourceMethod, Supplier<String> messageSupplier) {
        if (!this.isLoggable(level)) {
            return;
        }
        this.doLog(level, messageSupplier, sourceClass, sourceMethod, null);
    }

    public void logp(Level level, String sourceClass, String sourceMethod, String message, Object parameter) {
        if (!this.isLoggable(level)) {
            return;
        }
        this.doLog(level, message, sourceClass, sourceMethod, parameter);
    }

    public void logp(Level level, String sourceClass, String sourceMethod, String message, Object[] parameters) {
        if (!this.isLoggable(level)) {
            return;
        }
        this.doLog(level, message, sourceClass, sourceMethod, parameters);
    }

    public void logp(Level level, String sourceClass, String sourceMethod, String message, Throwable thrown) {
        if (!this.isLoggable(level)) {
            return;
        }
        this.doLog(level, message, sourceClass, sourceMethod, thrown);
    }

    public void logp(Level level, String sourceClass, String sourceMethod, Throwable thrown, Supplier<String> messageSupplier) {
        if (!this.isLoggable(level)) {
            return;
        }
        this.doLog(level, messageSupplier, sourceClass, sourceMethod, thrown);
    }

    public String getName() {
        return this.name;
    }

    public TruffleLogger getParent() {
        return this.parent;
    }

    public boolean isLoggable(Level level) {
        int value2 = this.getLevelNum();
        if (level.intValue() < value2 || value2 == OFF_VALUE) {
            return false;
        }
        return this.isLoggableSlowPath(level);
    }

    @CompilerDirectives.TruffleBoundary
    private boolean isLoggableSlowPath(Level level) {
        return this.loggerCache.isLoggable(this.getName(), level);
    }

    @CompilerDirectives.TruffleBoundary
    private void doLog(Level level, String message, String className, String methodName, Object param) {
        this.doLog(level, message, className, methodName, new Object[]{param});
    }

    @CompilerDirectives.TruffleBoundary
    private void doLog(Level level, String message, String className, String methodName, Object[] params) {
        LogRecord logRecord = LanguageAccessor.engineAccess().createLogRecord(this.loggerCache.getSPI(), level, this.getName(), message, className, methodName, params, null);
        this.callHandlers(logRecord);
    }

    @CompilerDirectives.TruffleBoundary
    private void doLog(Level level, String message, String className, String methodName, Throwable thrown) {
        LogRecord logRecord = LanguageAccessor.engineAccess().createLogRecord(this.loggerCache.getSPI(), level, this.getName(), message, className, methodName, null, thrown);
        this.callHandlers(logRecord);
    }

    @CompilerDirectives.TruffleBoundary
    private void doLog(Level level, Supplier<String> messageSupplier, String className, String methodName, Throwable thrown) {
        this.doLog(level, messageSupplier.get(), className, methodName, thrown);
    }

    private void callHandlers(LogRecord record) {
        CompilerAsserts.neverPartOfCompilation("Log handler should never be called from compiled code.");
        for (TruffleLogger current = this; current != null; current = current.getParent()) {
            if (current != this.loggerCache.polyglotRootLogger) continue;
            LanguageAccessor.engineAccess().getLogHandler(this.loggerCache.getSPI()).publish(record);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void removeChild(ChildLoggerRef child) {
        Object object = childrenLock;
        synchronized (object) {
            if (this.children != null) {
                Iterator<ChildLoggerRef> it = this.children.iterator();
                while (it.hasNext()) {
                    if (it.next() != child) continue;
                    it.remove();
                    return;
                }
            }
        }
    }

    private void updateLevelNum(boolean singleContext) {
        int value2;
        if (this.levelObj != null) {
            value2 = this.levelObj.intValue();
            if (this.parent != null && !singleContext) {
                value2 = Math.min(value2, this.parent.getLevelNum());
            }
        } else {
            value2 = this.parent != null ? this.parent.getLevelNum() : DEFAULT_VALUE;
        }
        this.setLevelNum(value2);
        if (this.children != null) {
            for (ChildLoggerRef ref : this.children) {
                TruffleLogger logger = (TruffleLogger)ref.get();
                if (logger == null) continue;
                logger.updateLevelNum(singleContext);
            }
        }
    }

    private int getLevelNum() {
        if (!this.levelNumStable.isValid()) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
        }
        return this.levelNum;
    }

    private boolean setLevelNum(int value2) {
        if (this.levelNum != value2) {
            this.levelNum = value2;
            Assumption currentAssumtion = this.levelNumStable;
            this.levelNumStable = Truffle.getRuntime().createAssumption("Log Level Value stable for: " + this.getName());
            currentAssumtion.invalidate();
            return true;
        }
        return false;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setLevel(Level level, boolean singleContext) {
        Object object = childrenLock;
        synchronized (object) {
            this.levelObj = level;
            this.updateLevelNum(singleContext);
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void setParent(TruffleLogger newParent, boolean singleContext) {
        Objects.requireNonNull(newParent, "Parent must be non null.");
        Object object = childrenLock;
        synchronized (object) {
            ChildLoggerRef found = null;
            if (this.parent != null) {
                Iterator<ChildLoggerRef> it = this.parent.children.iterator();
                while (it.hasNext()) {
                    ChildLoggerRef childRef = it.next();
                    TruffleLogger childLogger = (TruffleLogger)childRef.get();
                    if (childLogger != this) continue;
                    found = childRef;
                    it.remove();
                    break;
                }
            }
            this.parent = newParent;
            if (found == null) {
                found = new ChildLoggerRef(this);
            }
            found.setParent(this.parent);
            if (this.parent.children == null) {
                this.parent.children = new ArrayList<ChildLoggerRef>(2);
            }
            this.parent.children.add(found);
            this.updateLevelNum(singleContext);
        }
    }

    private static void cleanupFreedReferences() {
        AbstractLoggerRef ref;
        for (int i = 0; i < 100 && (ref = (AbstractLoggerRef)loggersRefQueue.poll()) != null; ++i) {
            ref.close();
        }
    }

    static final class LoggerCache {
        private static final ReferenceQueue<Object> contextsRefQueue = new ReferenceQueue();
        private static final LoggerCache INSTANCE = new LoggerCache(LanguageAccessor.engineAccess().createDefaultLoggerCache());
        private final Object loggerCache;
        private final TruffleLogger polyglotRootLogger;
        private final Map<String, NamedLoggerRef> loggers;
        private final LoggerNode root;
        private final Set<ContextWeakReference> activeContexts;
        private Map<String, Level> effectiveLevels;
        private volatile Set<String> knownIds;

        LoggerCache(Object loggerCacheSpi) {
            Objects.requireNonNull(loggerCacheSpi);
            this.loggerCache = loggerCacheSpi;
            this.polyglotRootLogger = new TruffleLogger(this);
            this.loggers = new HashMap<String, NamedLoggerRef>();
            this.loggers.put(TruffleLogger.ROOT_NAME, new NamedLoggerRef(this.polyglotRootLogger, TruffleLogger.ROOT_NAME));
            this.root = new LoggerNode(null, new NamedLoggerRef(this.polyglotRootLogger, TruffleLogger.ROOT_NAME));
            this.activeContexts = new HashSet<ContextWeakReference>();
            this.effectiveLevels = Collections.emptyMap();
        }

        synchronized void addLogLevelsForVMObject(Object vmObject, Map<String, Level> addedLevels) {
            this.activeContexts.add(new ContextWeakReference(vmObject, contextsRefQueue, addedLevels));
            Set<String> toRemove = this.collectRemovedLevels();
            this.reconfigure(addedLevels, toRemove);
        }

        synchronized void removeLogLevelsForVMObject(Object vmObject) {
            Set<String> toRemove = this.removeContext(vmObject);
            this.reconfigure(Collections.emptyMap(), toRemove);
        }

        synchronized void close() {
            Object owner = LanguageAccessor.engineAccess().getLoggerOwner(this.loggerCache);
            if (owner == null) {
                return;
            }
            Set<String> toRemove = this.removeContext(owner);
            if (!toRemove.isEmpty()) {
                this.reconfigure(Collections.emptyMap(), toRemove);
            }
        }

        synchronized boolean isLoggable(String loggerName, Level level) {
            Set<String> toRemove = this.collectRemovedLevels();
            if (!toRemove.isEmpty()) {
                this.reconfigure(Collections.emptyMap(), toRemove);
                return this.getLogger(loggerName).isLoggable(level);
            }
            Map<String, Level> current = LanguageAccessor.engineAccess().getLogLevels(this.getSPI());
            if (current == null) {
                return LoggerCache.noContext();
            }
            if (current.isEmpty()) {
                int currentLevel = DEFAULT_VALUE;
                return level.intValue() >= currentLevel && currentLevel != OFF_VALUE;
            }
            if (this.activeContexts.size() == 1) {
                return true;
            }
            int currentLevel = LoggerCache.computeLevel(loggerName, current);
            return level.intValue() >= currentLevel && currentLevel != OFF_VALUE;
        }

        private static boolean noContext() {
            boolean assertionsEnabled = false;
            if (!$assertionsDisabled) {
                assertionsEnabled = true;
                if (!true) {
                    throw new AssertionError();
                }
            }
            if (assertionsEnabled) {
                throw new IllegalStateException("Thread using TruffleLogger has to have a current context or the TruffleLogger has to be bound to an engine.");
            }
            return false;
        }

        private static int computeLevel(String loggeName, Map<String, Level> levels) {
            String currentName = loggeName;
            while (currentName != null) {
                Level l = levels.get(currentName);
                if (l != null) {
                    return l.intValue();
                }
                if (currentName.isEmpty()) {
                    currentName = null;
                    continue;
                }
                int index = currentName.lastIndexOf(46);
                currentName = index == -1 ? TruffleLogger.ROOT_NAME : currentName.substring(0, index);
            }
            return DEFAULT_VALUE;
        }

        private TruffleLogger getOrCreateLogger(String loggerName) {
            TruffleLogger found = this.getLogger(loggerName);
            if (found == null) {
                TruffleLogger logger = new TruffleLogger(loggerName, this);
                while (found == null) {
                    if (this.addLogger(logger)) {
                        found = logger;
                        break;
                    }
                    found = this.getLogger(loggerName);
                }
            }
            return found;
        }

        private TruffleLogger getOrCreateLogger(String id, String loggerName) {
            Set<String> ids = this.getKnownIds();
            if (!ids.contains(id)) {
                throw new IllegalArgumentException("Unknown language or instrument id " + id + ", known ids: " + String.join((CharSequence)", ", ids));
            }
            String globalLoggerId = loggerName == null || loggerName.isEmpty() ? id : id + "." + loggerName;
            return this.getOrCreateLogger(globalLoggerId);
        }

        private Set<String> getKnownIds() {
            Set<String> result = this.knownIds;
            if (result == null) {
                result = new HashSet<String>();
                result.addAll(LanguageAccessor.engineAccess().getInternalIds());
                result.addAll(LanguageAccessor.engineAccess().getLanguageIds());
                result.addAll(LanguageAccessor.engineAccess().getInstrumentIds());
                this.knownIds = result;
            }
            return result;
        }

        Object getSPI() {
            return this.loggerCache;
        }

        private synchronized TruffleLogger getLogger(String loggerName) {
            TruffleLogger res = null;
            NamedLoggerRef ref = this.loggers.get(loggerName);
            if (ref != null && (res = (TruffleLogger)ref.get()) == null) {
                ref.close();
            }
            return res;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean addLogger(TruffleLogger logger) {
            String loggerName = logger.getName();
            if (loggerName == null) {
                throw new NullPointerException("Logger must have non null name.");
            }
            TruffleLogger.cleanupFreedReferences();
            LoggerCache loggerCache = this;
            synchronized (loggerCache) {
                NamedLoggerRef ref = this.loggers.get(loggerName);
                if (ref != null) {
                    TruffleLogger loggerInstance = (TruffleLogger)ref.get();
                    if (loggerInstance != null) {
                        return false;
                    }
                    ref.close();
                }
                ref = new NamedLoggerRef(logger, loggerName);
                this.loggers.put(loggerName, ref);
                this.setLoggerLevel(logger, loggerName, this.activeContexts.size() <= 1);
                this.createParents(loggerName);
                LoggerNode node = this.findLoggerNode(loggerName);
                node.setLoggerRef(ref);
                TruffleLogger parentLogger = node.findParentLogger();
                if (parentLogger != null) {
                    logger.setParent(parentLogger, this.activeContexts.size() <= 1);
                }
                node.updateChildParents();
                ref.setNode(node);
                return true;
            }
        }

        private Level getEffectiveLevel(String loggerName) {
            return this.effectiveLevels.get(loggerName);
        }

        private Set<String> removeContext(Object vmObject) {
            Set<String> toRemove = this.collectRemovedLevels();
            Iterator<ContextWeakReference> it = this.activeContexts.iterator();
            while (it.hasNext()) {
                ContextWeakReference ref = it.next();
                Object active = ref.get();
                if (!vmObject.equals(active)) continue;
                toRemove.addAll(ref.configuredLoggers.keySet());
                it.remove();
                break;
            }
            return toRemove;
        }

        private Set<String> collectRemovedLevels() {
            ContextWeakReference ref;
            assert (Thread.holdsLock(this));
            HashSet<String> toRemove = new HashSet<String>();
            while ((ref = (ContextWeakReference)contextsRefQueue.poll()) != null) {
                this.activeContexts.remove(ref);
                toRemove.addAll(ref.configuredLoggers.keySet());
            }
            return toRemove;
        }

        private void reconfigure(Map<String, Level> addedLevels, Set<String> toRemove) {
            TruffleLogger logger;
            assert (Thread.holdsLock(this));
            assert (!addedLevels.isEmpty() || !toRemove.isEmpty());
            HashSet loggersWithRemovedLevels = new HashSet();
            HashSet loggersWithChangedLevels = new HashSet();
            this.effectiveLevels = LoggerCache.computeEffectiveLevels(this.effectiveLevels, toRemove, addedLevels, this.activeContexts, loggersWithRemovedLevels, loggersWithChangedLevels);
            boolean singleContext = this.activeContexts.size() <= 1;
            for (String loggerName : loggersWithRemovedLevels) {
                logger = this.getLogger(loggerName);
                if (logger == null) continue;
                logger.setLevel(null, singleContext);
            }
            for (String loggerName : loggersWithChangedLevels) {
                logger = this.getLogger(loggerName);
                if (logger != null) {
                    this.setLoggerLevel(logger, loggerName, singleContext);
                    this.createParents(loggerName);
                    continue;
                }
                this.getOrCreateLogger(loggerName);
            }
        }

        private void setLoggerLevel(TruffleLogger logger, String loggerName, boolean singleContext) {
            Level l = this.getEffectiveLevel(loggerName);
            if (l != null) {
                logger.setLevel(l, singleContext);
            }
        }

        private void createParents(String loggerName) {
            int index = -1;
            int start2 = 1;
            while ((index = loggerName.indexOf(46, start2)) >= 0) {
                String parentName = loggerName.substring(0, index);
                if (this.getEffectiveLevel(parentName) != null) {
                    this.getOrCreateLogger(parentName);
                }
                start2 = index + 1;
            }
        }

        private LoggerNode findLoggerNode(String loggerName) {
            LoggerNode node = this.root;
            String currentName = loggerName;
            while (!currentName.isEmpty()) {
                LoggerNode child;
                String currentNameCompoment;
                int index = currentName.indexOf(46);
                if (index > 0) {
                    currentNameCompoment = currentName.substring(0, index);
                    currentName = currentName.substring(index + 1);
                } else {
                    currentNameCompoment = currentName;
                    currentName = TruffleLogger.ROOT_NAME;
                }
                if (node.children == null) {
                    node.children = new HashMap<String, LoggerNode>();
                }
                if ((child = node.children.get(currentNameCompoment)) == null) {
                    child = new LoggerNode(node, null);
                    node.children.put(currentNameCompoment, child);
                }
                node = child;
            }
            return node;
        }

        static LoggerCache getInstance() {
            return INSTANCE;
        }

        private static Map<String, Level> computeEffectiveLevels(Map<String, Level> currentEffectiveLevels, Set<String> removed, Map<String, Level> added, Collection<? extends ContextWeakReference> contexts, Collection<? super String> removedLevels, Collection<? super String> changedLevels) {
            HashMap<String, Level> newEffectiveLevels = new HashMap<String, Level>(currentEffectiveLevels);
            for (String loggerName : removed) {
                Level level = LoggerCache.findMinLevel(loggerName, contexts);
                if (level == null) {
                    newEffectiveLevels.remove(loggerName);
                    removedLevels.add(loggerName);
                    continue;
                }
                Level currentLevel = (Level)newEffectiveLevels.get(loggerName);
                if (currentLevel == level) continue;
                newEffectiveLevels.put(loggerName, level);
                changedLevels.add(loggerName);
            }
            HashMap<String, Level> addedWithDefaults = new HashMap<String, Level>(added);
            for (String string : newEffectiveLevels.keySet()) {
                addedWithDefaults.putIfAbsent(string, Level.INFO);
            }
            for (Map.Entry entry : addedWithDefaults.entrySet()) {
                String loggerName = (String)entry.getKey();
                Level loggerLevel = (Level)entry.getValue();
                Level currentLevel = (Level)newEffectiveLevels.get(loggerName);
                if (currentLevel != null && LoggerCache.min(loggerLevel, currentLevel) == currentLevel) continue;
                newEffectiveLevels.put(loggerName, loggerLevel);
                changedLevels.add(loggerName);
            }
            return newEffectiveLevels;
        }

        private static Level findMinLevel(String loggerName, Collection<? extends ContextWeakReference> contexts) {
            Level min2 = null;
            for (ContextWeakReference contextWeakReference : contexts) {
                Object context = contextWeakReference.get();
                Level level = context == null ? null : contextWeakReference.configuredLoggers.get(loggerName);
                if (level == null) continue;
                if (min2 == null) {
                    min2 = level;
                    continue;
                }
                min2 = LoggerCache.min(min2, level);
            }
            return min2;
        }

        private static Level min(Level l1, Level l2) {
            return l1.intValue() < l2.intValue() ? l1 : l2;
        }

        private static final class ContextWeakReference
        extends WeakReference<Object> {
            private final Map<String, Level> configuredLoggers;

            ContextWeakReference(Object context, ReferenceQueue<Object> referenceQueue, Map<String, Level> logLevels) {
                super(context, referenceQueue);
                this.configuredLoggers = logLevels;
            }
        }

        private final class LoggerNode {
            final LoggerNode parent;
            Map<String, LoggerNode> children;
            private NamedLoggerRef loggerRef;

            LoggerNode(LoggerNode parent, NamedLoggerRef loggerRef) {
                this.parent = parent;
                this.loggerRef = loggerRef;
            }

            void setLoggerRef(NamedLoggerRef loggerRef) {
                this.loggerRef = loggerRef;
            }

            void updateChildParents() {
                TruffleLogger logger = (TruffleLogger)this.loggerRef.get();
                this.updateChildParentsImpl(logger);
            }

            TruffleLogger findParentLogger() {
                TruffleLogger logger;
                if (this.parent == null) {
                    return null;
                }
                if (this.parent.loggerRef != null && (logger = (TruffleLogger)this.parent.loggerRef.get()) != null) {
                    return logger;
                }
                return this.parent.findParentLogger();
            }

            private void updateChildParentsImpl(TruffleLogger parentLogger) {
                if (this.children == null || this.children.isEmpty()) {
                    return;
                }
                for (LoggerNode child : this.children.values()) {
                    TruffleLogger childLogger;
                    TruffleLogger truffleLogger = childLogger = child.loggerRef != null ? (TruffleLogger)child.loggerRef.get() : null;
                    if (childLogger != null) {
                        childLogger.setParent(parentLogger, LoggerCache.this.activeContexts.size() <= 1);
                        continue;
                    }
                    child.updateChildParentsImpl(parentLogger);
                }
            }
        }

        private final class NamedLoggerRef
        extends AbstractLoggerRef {
            private final String loggerName;
            private LoggerNode node;

            NamedLoggerRef(TruffleLogger logger, String loggerName) {
                super(logger);
                this.loggerName = loggerName;
            }

            void setNode(LoggerNode node) {
                assert (Thread.holdsLock(LoggerCache.this));
                this.node = node;
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            @Override
            public void close() {
                if (this.shouldClose()) {
                    LoggerCache loggerCache = LoggerCache.this;
                    synchronized (loggerCache) {
                        if (this.node != null) {
                            if (this.node.loggerRef == this) {
                                LoggerCache.this.loggers.remove(this.loggerName);
                                this.node.loggerRef = null;
                            }
                            this.node = null;
                        }
                    }
                }
            }
        }
    }

    private static final class ChildLoggerRef
    extends AbstractLoggerRef {
        private volatile Reference<TruffleLogger> parent;

        ChildLoggerRef(TruffleLogger logger) {
            super(logger);
        }

        void setParent(TruffleLogger parent) {
            this.parent = new WeakReference<TruffleLogger>(parent);
        }

        @Override
        public void close() {
            Reference<TruffleLogger> p;
            if (this.shouldClose() && (p = this.parent) != null) {
                TruffleLogger parentLogger = p.get();
                if (parentLogger != null) {
                    parentLogger.removeChild(this);
                }
                this.parent = null;
            }
        }
    }

    private static abstract class AbstractLoggerRef
    extends WeakReference<TruffleLogger>
    implements Closeable {
        private final AtomicBoolean closed = new AtomicBoolean();

        AbstractLoggerRef(TruffleLogger logger) {
            super(logger, loggersRefQueue);
        }

        @Override
        public abstract void close();

        boolean shouldClose() {
            return !this.closed.getAndSet(true);
        }
    }
}

