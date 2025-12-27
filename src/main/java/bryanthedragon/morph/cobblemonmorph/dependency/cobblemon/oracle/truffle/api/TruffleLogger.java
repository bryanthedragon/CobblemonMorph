package com.oracle.truffle.api;

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
import java.util.Map.Entry;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.LogRecord;

public final class TruffleLogger {
   private static final String ROOT_NAME = "";
   private static final int MAX_CLEANED_REFS = 100;
   private static final int OFF_VALUE = Level.OFF.intValue();
   private static final int DEFAULT_VALUE = Level.INFO.intValue();
   private static final ReferenceQueue<TruffleLogger> loggersRefQueue = new ReferenceQueue<>();
   private static final Object childrenLock = new Object();
   private final String name;
   private final TruffleLogger.LoggerCache loggerCache;
   @CompilerDirectives.CompilationFinal
   private volatile int levelNum;
   @CompilerDirectives.CompilationFinal
   private volatile Assumption levelNumStable;
   private volatile Level levelObj;
   private volatile TruffleLogger parent;
   private Collection<TruffleLogger.ChildLoggerRef> children;

   private TruffleLogger(final String loggerName, final TruffleLogger.LoggerCache loggerCache) {
      this.name = loggerName;
      this.loggerCache = loggerCache;
      this.levelNum = DEFAULT_VALUE;
      this.levelNumStable = Truffle.getRuntime().createAssumption("Log Level Value stable for: " + loggerName);
   }

   private TruffleLogger(TruffleLogger.LoggerCache loggerCache) {
      this("", loggerCache);
   }

   public static TruffleLogger getLogger(final String id) {
      return getLogger(id, null, TruffleLogger.LoggerCache.getInstance());
   }

   public static TruffleLogger getLogger(final String id, final Class<?> forClass) {
      Objects.requireNonNull(forClass, "Class must be non null.");
      return getLogger(id, forClass.getName());
   }

   public static TruffleLogger getLogger(final String id, final String loggerName) {
      return getLogger(id, loggerName, TruffleLogger.LoggerCache.getInstance());
   }

   static TruffleLogger getLogger(String id, String loggerName, TruffleLogger.LoggerCache loggerCache) {
      Objects.requireNonNull(id, "LanguageId must be non null.");
      return loggerCache.getOrCreateLogger(id, loggerName);
   }

   TruffleLogger.LoggerCache getLoggerCache() {
      return this.loggerCache;
   }

   public void config(final String message) {
      this.log(Level.CONFIG, message);
   }

   public void config(final Supplier<String> messageSupplier) {
      this.log(Level.CONFIG, messageSupplier);
   }

   public void entering(final String sourceClass, final String sourceMethod) {
      this.logp(Level.FINER, sourceClass, sourceMethod, "ENTRY");
   }

   public void entering(final String sourceClass, final String sourceMethod, final Object parameter) {
      this.logp(Level.FINER, sourceClass, sourceMethod, "ENTRY {0}", parameter);
   }

   public void entering(final String sourceClass, final String sourceMethod, final Object[] parameters) {
      String msg = "ENTRY";
      if (parameters == null) {
         this.logp(Level.FINER, sourceClass, sourceMethod, msg);
      } else if (this.isLoggable(Level.FINER)) {
         for (int i = 0; i < parameters.length; i++) {
            msg = msg + " {" + i + "}";
         }

         this.logp(Level.FINER, sourceClass, sourceMethod, msg, parameters);
      }
   }

   public void exiting(final String sourceClass, final String sourceMethod) {
      this.logp(Level.FINER, sourceClass, sourceMethod, "RETURN");
   }

   public void exiting(final String sourceClass, final String sourceMethod, final Object result) {
      this.logp(Level.FINER, sourceClass, sourceMethod, "RETURN {0}", result);
   }

   public void fine(final String message) {
      this.log(Level.FINE, message);
   }

   public void fine(final Supplier<String> messageSupplier) {
      this.log(Level.FINE, messageSupplier);
   }

   public void finer(final String message) {
      this.log(Level.FINER, message);
   }

   public void finer(final Supplier<String> messageSupplier) {
      this.log(Level.FINER, messageSupplier);
   }

   public void finest(final String message) {
      this.log(Level.FINEST, message);
   }

   public void finest(final Supplier<String> messageSupplier) {
      this.log(Level.FINEST, messageSupplier);
   }

   public void info(final String message) {
      this.log(Level.INFO, message);
   }

   public void info(final Supplier<String> messageSupplier) {
      this.log(Level.INFO, messageSupplier);
   }

   public void severe(final String message) {
      this.log(Level.SEVERE, message);
   }

   public void severe(final Supplier<String> messageSupplier) {
      this.log(Level.SEVERE, messageSupplier);
   }

   public <T extends Throwable> T throwing(final String sourceClass, final String sourceMethod, final T thrown) {
      this.logp(Level.FINER, sourceClass, sourceMethod, "THROW", thrown);
      return thrown;
   }

   public void warning(final String message) {
      this.log(Level.WARNING, message);
   }

   public void warning(final Supplier<String> messageSupplier) {
      this.log(Level.WARNING, messageSupplier);
   }

   public void log(final Level level, final String message) {
      if (this.isLoggable(level)) {
         this.doLog(level, message, null, null, (Object[])null);
      }
   }

   public void log(final Level level, final Supplier<String> messageSupplier) {
      if (this.isLoggable(level)) {
         this.doLog(level, messageSupplier, null, null, null);
      }
   }

   public void log(final Level level, final String message, final Object parameter) {
      if (this.isLoggable(level)) {
         this.doLog(level, message, null, null, parameter);
      }
   }

   public void log(final Level level, final String message, final Object[] parameters) {
      if (this.isLoggable(level)) {
         this.doLog(level, message, null, null, parameters);
      }
   }

   public void log(final Level level, final String message, final Throwable thrown) {
      if (this.isLoggable(level)) {
         this.doLog(level, message, null, null, thrown);
      }
   }

   public void log(final Level level, final Throwable thrown, final Supplier<String> messageSupplier) {
      if (this.isLoggable(level)) {
         this.doLog(level, messageSupplier, null, null, thrown);
      }
   }

   public void logp(final Level level, final String sourceClass, final String sourceMethod, final String message) {
      if (this.isLoggable(level)) {
         this.doLog(level, message, sourceClass, sourceMethod, (Object[])null);
      }
   }

   public void logp(final Level level, final String sourceClass, final String sourceMethod, final Supplier<String> messageSupplier) {
      if (this.isLoggable(level)) {
         this.doLog(level, messageSupplier, sourceClass, sourceMethod, null);
      }
   }

   public void logp(final Level level, final String sourceClass, final String sourceMethod, final String message, final Object parameter) {
      if (this.isLoggable(level)) {
         this.doLog(level, message, sourceClass, sourceMethod, parameter);
      }
   }

   public void logp(final Level level, final String sourceClass, final String sourceMethod, final String message, Object[] parameters) {
      if (this.isLoggable(level)) {
         this.doLog(level, message, sourceClass, sourceMethod, parameters);
      }
   }

   public void logp(final Level level, final String sourceClass, final String sourceMethod, final String message, final Throwable thrown) {
      if (this.isLoggable(level)) {
         this.doLog(level, message, sourceClass, sourceMethod, thrown);
      }
   }

   public void logp(final Level level, final String sourceClass, final String sourceMethod, final Throwable thrown, final Supplier<String> messageSupplier) {
      if (this.isLoggable(level)) {
         this.doLog(level, messageSupplier, sourceClass, sourceMethod, thrown);
      }
   }

   public String getName() {
      return this.name;
   }

   public TruffleLogger getParent() {
      return this.parent;
   }

   public boolean isLoggable(final Level level) {
      int value = this.getLevelNum();
      return level.intValue() >= value && value != OFF_VALUE ? this.isLoggableSlowPath(level) : false;
   }

   @CompilerDirectives.TruffleBoundary
   private boolean isLoggableSlowPath(final Level level) {
      return this.loggerCache.isLoggable(this.getName(), level);
   }

   @CompilerDirectives.TruffleBoundary
   private void doLog(final Level level, final String message, final String className, final String methodName, final Object param) {
      this.doLog(level, message, className, methodName, new Object[]{param});
   }

   @CompilerDirectives.TruffleBoundary
   private void doLog(final Level level, final String message, final String className, final String methodName, final Object[] params) {
      LogRecord logRecord = LanguageAccessor.engineAccess()
         .createLogRecord(this.loggerCache.getSPI(), level, this.getName(), message, className, methodName, params, null);
      this.callHandlers(logRecord);
   }

   @CompilerDirectives.TruffleBoundary
   private void doLog(final Level level, final String message, final String className, final String methodName, final Throwable thrown) {
      LogRecord logRecord = LanguageAccessor.engineAccess()
         .createLogRecord(this.loggerCache.getSPI(), level, this.getName(), message, className, methodName, null, thrown);
      this.callHandlers(logRecord);
   }

   @CompilerDirectives.TruffleBoundary
   private void doLog(final Level level, final Supplier<String> messageSupplier, final String className, final String methodName, final Throwable thrown) {
      this.doLog(level, messageSupplier.get(), className, methodName, thrown);
   }

   private void callHandlers(final LogRecord record) {
      CompilerAsserts.neverPartOfCompilation("Log handler should never be called from compiled code.");

      for (TruffleLogger current = this; current != null; current = current.getParent()) {
         if (current == this.loggerCache.polyglotRootLogger) {
            LanguageAccessor.engineAccess().getLogHandler(this.loggerCache.getSPI()).publish(record);
         }
      }
   }

   private void removeChild(final TruffleLogger.ChildLoggerRef child) {
      synchronized (childrenLock) {
         if (this.children != null) {
            Iterator<TruffleLogger.ChildLoggerRef> it = this.children.iterator();

            while (it.hasNext()) {
               if (it.next() == child) {
                  it.remove();
                  return;
               }
            }
         }
      }
   }

   private void updateLevelNum(boolean singleContext) {
      int value;
      if (this.levelObj != null) {
         value = this.levelObj.intValue();
         if (this.parent != null && !singleContext) {
            value = Math.min(value, this.parent.getLevelNum());
         }
      } else if (this.parent != null) {
         value = this.parent.getLevelNum();
      } else {
         value = DEFAULT_VALUE;
      }

      this.setLevelNum(value);
      if (this.children != null) {
         for (TruffleLogger.ChildLoggerRef ref : this.children) {
            TruffleLogger logger = ref.get();
            if (logger != null) {
               logger.updateLevelNum(singleContext);
            }
         }
      }
   }

   private int getLevelNum() {
      if (!this.levelNumStable.isValid()) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
      }

      return this.levelNum;
   }

   private boolean setLevelNum(final int value) {
      if (this.levelNum != value) {
         this.levelNum = value;
         Assumption currentAssumtion = this.levelNumStable;
         this.levelNumStable = Truffle.getRuntime().createAssumption("Log Level Value stable for: " + this.getName());
         currentAssumtion.invalidate();
         return true;
      } else {
         return false;
      }
   }

   private void setLevel(final Level level, final boolean singleContext) {
      synchronized (childrenLock) {
         this.levelObj = level;
         this.updateLevelNum(singleContext);
      }
   }

   private void setParent(final TruffleLogger newParent, final boolean singleContext) {
      Objects.requireNonNull(newParent, "Parent must be non null.");
      synchronized (childrenLock) {
         TruffleLogger.ChildLoggerRef found = null;
         if (this.parent != null) {
            Iterator<TruffleLogger.ChildLoggerRef> it = this.parent.children.iterator();

            while (it.hasNext()) {
               TruffleLogger.ChildLoggerRef childRef = it.next();
               TruffleLogger childLogger = childRef.get();
               if (childLogger == this) {
                  found = childRef;
                  it.remove();
                  break;
               }
            }
         }

         this.parent = newParent;
         if (found == null) {
            found = new TruffleLogger.ChildLoggerRef(this);
         }

         found.setParent(this.parent);
         if (this.parent.children == null) {
            this.parent.children = new ArrayList<>(2);
         }

         this.parent.children.add(found);
         this.updateLevelNum(singleContext);
      }
   }

   private static void cleanupFreedReferences() {
      for (int i = 0; i < 100; i++) {
         TruffleLogger.AbstractLoggerRef ref = (TruffleLogger.AbstractLoggerRef)loggersRefQueue.poll();
         if (ref == null) {
            break;
         }

         ref.close();
      }
   }

   private abstract static class AbstractLoggerRef extends WeakReference<TruffleLogger> implements Closeable {
      private final AtomicBoolean closed = new AtomicBoolean();

      AbstractLoggerRef(final TruffleLogger logger) {
         super(logger, TruffleLogger.loggersRefQueue);
      }

      @Override
      public abstract void close();

      boolean shouldClose() {
         return !this.closed.getAndSet(true);
      }
   }

   private static final class ChildLoggerRef extends TruffleLogger.AbstractLoggerRef {
      private volatile Reference<TruffleLogger> parent;

      ChildLoggerRef(final TruffleLogger logger) {
         super(logger);
      }

      void setParent(TruffleLogger parent) {
         this.parent = new WeakReference<>(parent);
      }

      @Override
      public void close() {
         if (this.shouldClose()) {
            Reference<TruffleLogger> p = this.parent;
            if (p != null) {
               TruffleLogger parentLogger = p.get();
               if (parentLogger != null) {
                  parentLogger.removeChild(this);
               }

               this.parent = null;
            }
         }
      }
   }

   static final class LoggerCache {
      private static final ReferenceQueue<Object> contextsRefQueue = new ReferenceQueue<>();
      private static final TruffleLogger.LoggerCache INSTANCE = new TruffleLogger.LoggerCache(LanguageAccessor.engineAccess().createDefaultLoggerCache());
      private final Object loggerCache;
      private final TruffleLogger polyglotRootLogger;
      private final Map<String, TruffleLogger.LoggerCache.NamedLoggerRef> loggers;
      private final TruffleLogger.LoggerCache.LoggerNode root;
      private final Set<TruffleLogger.LoggerCache.ContextWeakReference> activeContexts;
      private Map<String, Level> effectiveLevels;
      private volatile Set<String> knownIds;

      LoggerCache(Object loggerCacheSpi) {
         Objects.requireNonNull(loggerCacheSpi);
         this.loggerCache = loggerCacheSpi;
         this.polyglotRootLogger = new TruffleLogger(this);
         this.loggers = new HashMap<>();
         this.loggers.put("", new TruffleLogger.LoggerCache.NamedLoggerRef(this.polyglotRootLogger, ""));
         this.root = new TruffleLogger.LoggerCache.LoggerNode(null, new TruffleLogger.LoggerCache.NamedLoggerRef(this.polyglotRootLogger, ""));
         this.activeContexts = new HashSet<>();
         this.effectiveLevels = Collections.emptyMap();
      }

      synchronized void addLogLevelsForVMObject(final Object vmObject, final Map<String, Level> addedLevels) {
         this.activeContexts.add(new TruffleLogger.LoggerCache.ContextWeakReference(vmObject, contextsRefQueue, addedLevels));
         Set<String> toRemove = this.collectRemovedLevels();
         this.reconfigure(addedLevels, toRemove);
      }

      synchronized void removeLogLevelsForVMObject(final Object vmObject) {
         Set<String> toRemove = this.removeContext(vmObject);
         this.reconfigure(Collections.emptyMap(), toRemove);
      }

      synchronized void close() {
         Object owner = LanguageAccessor.engineAccess().getLoggerOwner(this.loggerCache);
         if (owner != null) {
            Set<String> toRemove = this.removeContext(owner);
            if (!toRemove.isEmpty()) {
               this.reconfigure(Collections.emptyMap(), toRemove);
            }
         }
      }

      synchronized boolean isLoggable(final String loggerName, final Level level) {
         Set<String> toRemove = this.collectRemovedLevels();
         if (!toRemove.isEmpty()) {
            this.reconfigure(Collections.emptyMap(), toRemove);
            return this.getLogger(loggerName).isLoggable(level);
         } else {
            Map<String, Level> current = LanguageAccessor.engineAccess().getLogLevels(this.getSPI());
            if (current == null) {
               return noContext();
            } else if (current.isEmpty()) {
               int currentLevel = TruffleLogger.DEFAULT_VALUE;
               return level.intValue() >= currentLevel && currentLevel != TruffleLogger.OFF_VALUE;
            } else if (this.activeContexts.size() == 1) {
               return true;
            } else {
               int currentLevel = computeLevel(loggerName, current);
               return level.intValue() >= currentLevel && currentLevel != TruffleLogger.OFF_VALUE;
            }
         }
      }

      private static boolean noContext() {
         boolean assertionsEnabled = false;
         if (!$assertionsDisabled) {
            assertionsEnabled = true;
            if (false) {
               throw new AssertionError();
            }
         }

         if (assertionsEnabled) {
            throw new IllegalStateException("Thread using TruffleLogger has to have a current context or the TruffleLogger has to be bound to an engine.");
         } else {
            return false;
         }
      }

      private static int computeLevel(String loggeName, final Map<String, Level> levels) {
         String currentName = loggeName;

         while (currentName != null) {
            Level l = levels.get(currentName);
            if (l != null) {
               return l.intValue();
            }

            if (currentName.isEmpty()) {
               currentName = null;
            } else {
               int index = currentName.lastIndexOf(46);
               currentName = index == -1 ? "" : currentName.substring(0, index);
            }
         }

         return TruffleLogger.DEFAULT_VALUE;
      }

      private TruffleLogger getOrCreateLogger(final String loggerName) {
         TruffleLogger found = this.getLogger(loggerName);
         if (found == null) {
            for (TruffleLogger logger = new TruffleLogger(loggerName, this); found == null; found = this.getLogger(loggerName)) {
               if (this.addLogger(logger)) {
                  found = logger;
                  break;
               }
            }
         }

         return found;
      }

      private TruffleLogger getOrCreateLogger(final String id, final String loggerName) {
         Set<String> ids = this.getKnownIds();
         if (!ids.contains(id)) {
            throw new IllegalArgumentException("Unknown language or instrument id " + id + ", known ids: " + String.join(", ", ids));
         } else {
            String globalLoggerId = loggerName != null && !loggerName.isEmpty() ? id + "." + loggerName : id;
            return this.getOrCreateLogger(globalLoggerId);
         }
      }

      private Set<String> getKnownIds() {
         Set<String> result = this.knownIds;
         if (result == null) {
            result = new HashSet<>();
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

      private synchronized TruffleLogger getLogger(final String loggerName) {
         TruffleLogger res = null;
         TruffleLogger.LoggerCache.NamedLoggerRef ref = this.loggers.get(loggerName);
         if (ref != null) {
            res = ref.get();
            if (res == null) {
               ref.close();
            }
         }

         return res;
      }

      private boolean addLogger(final TruffleLogger logger) {
         String loggerName = logger.getName();
         if (loggerName == null) {
            throw new NullPointerException("Logger must have non null name.");
         } else {
            TruffleLogger.cleanupFreedReferences();
            synchronized (this) {
               TruffleLogger.LoggerCache.NamedLoggerRef ref = this.loggers.get(loggerName);
               if (ref != null) {
                  TruffleLogger loggerInstance = ref.get();
                  if (loggerInstance != null) {
                     return false;
                  }

                  ref.close();
               }

               ref = new TruffleLogger.LoggerCache.NamedLoggerRef(logger, loggerName);
               this.loggers.put(loggerName, ref);
               this.setLoggerLevel(logger, loggerName, this.activeContexts.size() <= 1);
               this.createParents(loggerName);
               TruffleLogger.LoggerCache.LoggerNode node = this.findLoggerNode(loggerName);
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
      }

      private Level getEffectiveLevel(final String loggerName) {
         return this.effectiveLevels.get(loggerName);
      }

      private Set<String> removeContext(Object vmObject) {
         Set<String> toRemove = this.collectRemovedLevels();
         Iterator<TruffleLogger.LoggerCache.ContextWeakReference> it = this.activeContexts.iterator();

         while (it.hasNext()) {
            TruffleLogger.LoggerCache.ContextWeakReference ref = it.next();
            Object active = ref.get();
            if (vmObject.equals(active)) {
               toRemove.addAll(ref.configuredLoggers.keySet());
               it.remove();
               break;
            }
         }

         return toRemove;
      }

      private Set<String> collectRemovedLevels() {
         assert Thread.holdsLock(this);

         Set<String> toRemove = new HashSet<>();

         TruffleLogger.LoggerCache.ContextWeakReference ref;
         while ((ref = (TruffleLogger.LoggerCache.ContextWeakReference)contextsRefQueue.poll()) != null) {
            this.activeContexts.remove(ref);
            toRemove.addAll(ref.configuredLoggers.keySet());
         }

         return toRemove;
      }

      private void reconfigure(final Map<String, Level> addedLevels, final Set<String> toRemove) {
         assert Thread.holdsLock(this);

         assert !addedLevels.isEmpty() || !toRemove.isEmpty();

         Collection<String> loggersWithRemovedLevels = new HashSet<>();
         Collection<String> loggersWithChangedLevels = new HashSet<>();
         this.effectiveLevels = computeEffectiveLevels(
            this.effectiveLevels, toRemove, addedLevels, this.activeContexts, loggersWithRemovedLevels, loggersWithChangedLevels
         );
         boolean singleContext = this.activeContexts.size() <= 1;

         for (String loggerName : loggersWithRemovedLevels) {
            TruffleLogger logger = this.getLogger(loggerName);
            if (logger != null) {
               logger.setLevel(null, singleContext);
            }
         }

         for (String loggerNamex : loggersWithChangedLevels) {
            TruffleLogger logger = this.getLogger(loggerNamex);
            if (logger != null) {
               this.setLoggerLevel(logger, loggerNamex, singleContext);
               this.createParents(loggerNamex);
            } else {
               this.getOrCreateLogger(loggerNamex);
            }
         }
      }

      private void setLoggerLevel(final TruffleLogger logger, final String loggerName, final boolean singleContext) {
         Level l = this.getEffectiveLevel(loggerName);
         if (l != null) {
            logger.setLevel(l, singleContext);
         }
      }

      private void createParents(final String loggerName) {
         int index = -1;
         int start = 1;

         while (true) {
            index = loggerName.indexOf(46, start);
            if (index < 0) {
               return;
            }

            String parentName = loggerName.substring(0, index);
            if (this.getEffectiveLevel(parentName) != null) {
               this.getOrCreateLogger(parentName);
            }

            start = index + 1;
         }
      }

      private TruffleLogger.LoggerCache.LoggerNode findLoggerNode(final String loggerName) {
         TruffleLogger.LoggerCache.LoggerNode node = this.root;
         String currentName = loggerName;

         while (!currentName.isEmpty()) {
            int index = currentName.indexOf(46);
            String currentNameCompoment;
            if (index > 0) {
               currentNameCompoment = currentName.substring(0, index);
               currentName = currentName.substring(index + 1);
            } else {
               currentNameCompoment = currentName;
               currentName = "";
            }

            if (node.children == null) {
               node.children = new HashMap<>();
            }

            TruffleLogger.LoggerCache.LoggerNode child = node.children.get(currentNameCompoment);
            if (child == null) {
               child = new TruffleLogger.LoggerCache.LoggerNode(node, null);
               node.children.put(currentNameCompoment, child);
            }

            node = child;
         }

         return node;
      }

      static TruffleLogger.LoggerCache getInstance() {
         return INSTANCE;
      }

      private static Map<String, Level> computeEffectiveLevels(
         final Map<String, Level> currentEffectiveLevels,
         final Set<String> removed,
         final Map<String, Level> added,
         final Collection<? extends TruffleLogger.LoggerCache.ContextWeakReference> contexts,
         final Collection<? super String> removedLevels,
         final Collection<? super String> changedLevels
      ) {
         Map<String, Level> newEffectiveLevels = new HashMap<>(currentEffectiveLevels);

         for (String loggerName : removed) {
            Level level = findMinLevel(loggerName, contexts);
            if (level == null) {
               newEffectiveLevels.remove(loggerName);
               removedLevels.add(loggerName);
            } else {
               Level currentLevel = newEffectiveLevels.get(loggerName);
               if (currentLevel != level) {
                  newEffectiveLevels.put(loggerName, level);
                  changedLevels.add(loggerName);
               }
            }
         }

         Map<String, Level> addedWithDefaults = new HashMap<>(added);

         for (String loggerNamex : newEffectiveLevels.keySet()) {
            addedWithDefaults.putIfAbsent(loggerNamex, Level.INFO);
         }

         for (Entry<String, Level> addedLevel : addedWithDefaults.entrySet()) {
            String loggerNamex = addedLevel.getKey();
            Level loggerLevel = addedLevel.getValue();
            Level currentLevel = newEffectiveLevels.get(loggerNamex);
            if (currentLevel == null || min(loggerLevel, currentLevel) != currentLevel) {
               newEffectiveLevels.put(loggerNamex, loggerLevel);
               changedLevels.add(loggerNamex);
            }
         }

         return newEffectiveLevels;
      }

      private static Level findMinLevel(final String loggerName, final Collection<? extends TruffleLogger.LoggerCache.ContextWeakReference> contexts) {
         Level min = null;

         for (TruffleLogger.LoggerCache.ContextWeakReference contextRef : contexts) {
            Object context = contextRef.get();
            Level level = context == null ? null : contextRef.configuredLoggers.get(loggerName);
            if (level != null) {
               if (min == null) {
                  min = level;
               } else {
                  min = min(min, level);
               }
            }
         }

         return min;
      }

      private static Level min(final Level l1, final Level l2) {
         return l1.intValue() < l2.intValue() ? l1 : l2;
      }

      private static final class ContextWeakReference extends WeakReference<Object> {
         private final Map<String, Level> configuredLoggers;

         ContextWeakReference(final Object context, final ReferenceQueue<Object> referenceQueue, final Map<String, Level> logLevels) {
            super(context, referenceQueue);
            this.configuredLoggers = logLevels;
         }
      }

      private final class LoggerNode {
         final TruffleLogger.LoggerCache.LoggerNode parent;
         Map<String, TruffleLogger.LoggerCache.LoggerNode> children;
         private TruffleLogger.LoggerCache.NamedLoggerRef loggerRef;

         LoggerNode(final TruffleLogger.LoggerCache.LoggerNode parent, final TruffleLogger.LoggerCache.NamedLoggerRef loggerRef) {
            this.parent = parent;
            this.loggerRef = loggerRef;
         }

         void setLoggerRef(final TruffleLogger.LoggerCache.NamedLoggerRef loggerRef) {
            this.loggerRef = loggerRef;
         }

         void updateChildParents() {
            TruffleLogger logger = this.loggerRef.get();
            this.updateChildParentsImpl(logger);
         }

         TruffleLogger findParentLogger() {
            if (this.parent == null) {
               return null;
            } else {
               TruffleLogger logger;
               return this.parent.loggerRef != null && (logger = this.parent.loggerRef.get()) != null ? logger : this.parent.findParentLogger();
            }
         }

         private void updateChildParentsImpl(final TruffleLogger parentLogger) {
            if (this.children != null && !this.children.isEmpty()) {
               for (TruffleLogger.LoggerCache.LoggerNode child : this.children.values()) {
                  TruffleLogger childLogger = child.loggerRef != null ? child.loggerRef.get() : null;
                  if (childLogger != null) {
                     childLogger.setParent(parentLogger, LoggerCache.this.activeContexts.size() <= 1);
                  } else {
                     child.updateChildParentsImpl(parentLogger);
                  }
               }
            }
         }
      }

      private final class NamedLoggerRef extends TruffleLogger.AbstractLoggerRef {
         private final String loggerName;
         private TruffleLogger.LoggerCache.LoggerNode node;

         NamedLoggerRef(final TruffleLogger logger, final String loggerName) {
            super(logger);
            this.loggerName = loggerName;
         }

         void setNode(final TruffleLogger.LoggerCache.LoggerNode node) {
            assert Thread.holdsLock(LoggerCache.this);

            this.node = node;
         }

         @Override
         public void close() {
            if (this.shouldClose()) {
               synchronized (LoggerCache.this) {
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
}
