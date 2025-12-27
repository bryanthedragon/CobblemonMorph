package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.source.Source;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Deque;
import java.util.Map;
import java.util.WeakHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.function.BiConsumer;

final class SourceInstrumentationHandler {
   private final InstrumentationHandler.CopyOnWriteList<EventBinding.Source<?>> bindings = new InstrumentationHandler.CopyOnWriteList<>(
      new EventBinding.Source[0]
   );
   private final WeakHashMap<Source, Void> sources = new WeakHashMap<>();
   private final InstrumentationHandler.WeakAsyncList<Source> sourcesList = new InstrumentationHandler.WeakAsyncList<>(16);
   private final AtomicBoolean sourcesInitialized = new AtomicBoolean();
   private final ReentrantReadWriteLock bindingsLock = new ReentrantReadWriteLock();
   private final BiConsumer<EventBinding.Source<?>[], Source> notificationConsumer;
   private SourceInstrumentationHandler.SourcesNotificationQueue notifications = new SourceInstrumentationHandler.SourcesNotificationQueue();

   SourceInstrumentationHandler(BiConsumer<EventBinding.Source<?>[], Source> notificationConsumer) {
      this.notificationConsumer = notificationConsumer;
   }

   private SourceInstrumentationHandler.SourcesNotificationQueue addInitializeSourcesNotification() {
      assert this.bindingsLock.getWriteHoldCount() > 0;

      assert this.bindings.size() == 1;

      this.notifications.enqueue(new SourceInstrumentationHandler.InitializeSourcesNotification());

      assert this.notifications.shouldProcess() : "Thread that added InitializeSourcesNotification is not the one to process the notification queue.";

      assert this.notifications.isSourcesInitializationRequired();

      return this.notifications;
   }

   private SourceInstrumentationHandler.SourcesNotificationQueue addAllSourcesNotification(EventBinding.Source<?> binding) {
      assert this.bindingsLock.getWriteHoldCount() > 0;

      this.notifications.enqueue(new SourceInstrumentationHandler.AllSourcesNotification(new EventBinding.Source[]{binding}));
      if (!this.notifications.shouldProcess()) {
         return null;
      } else {
         assert !this.notifications.isSourcesInitializationRequired() && this.bindings.size() > 1
            || this.notifications.isSourcesInitializationRequired() && this.bindings.size() == 1;

         return this.notifications;
      }
   }

   private SourceInstrumentationHandler.SourcesNotificationQueue addNotification(Map<Source, Void> collectedSources, EventBinding.Source<?>[] bindingsToNotify) {
      assert this.bindingsLock.getReadHoldCount() > 0;

      assert Thread.holdsLock(this.sources);

      assert !this.bindings.isEmpty();

      this.notifications.enqueue(new SourceInstrumentationHandler.NewSourcesNotification(bindingsToNotify, collectedSources.keySet()));
      if (this.notifications.shouldProcess()) {
         assert !this.notifications.isSourcesInitializationRequired();

         return this.notifications;
      } else {
         return null;
      }
   }

   void setInitialized() {
      this.sourcesInitialized.set(true);
   }

   boolean hasBindings() {
      return !this.bindings.isEmpty();
   }

   EventBinding.Source<?>[] getBindingsArray() {
      return this.bindings.getArray();
   }

   void clearForDisposedBinding(EventBinding.Source<?> disposedBinding) {
      Lock lock = this.bindingsLock.writeLock();
      lock.lock();

      try {
         this.bindings.remove(disposedBinding);
         if (this.bindings.isEmpty()) {
            this.clearAllInternal();
         }
      } finally {
         lock.unlock();
      }
   }

   void clearForDisposedInstrumenter(InstrumentationHandler.AbstractInstrumenter disposedInstrumenter) {
      Lock lock = this.bindingsLock.writeLock();
      lock.lock();

      try {
         Collection<EventBinding<?>> disposedSourceLoadedBindings = InstrumentationHandler.filterBindingsForInstrumenter(this.bindings, disposedInstrumenter);
         InstrumentationHandler.disposeBindingsBulk(disposedSourceLoadedBindings);
         this.bindings.removeAll(disposedSourceLoadedBindings);
         if (this.bindings.isEmpty()) {
            this.clearAllInternal();
         }
      } finally {
         lock.unlock();
      }
   }

   void clearAll() {
      Lock lock = this.bindingsLock.writeLock();
      lock.lock();

      try {
         this.clearAllInternal();
      } finally {
         lock.unlock();
      }
   }

   private void clearAllInternal() {
      assert this.bindingsLock.getWriteHoldCount() > 0;

      this.bindings.clear();
      this.sources.clear();
      this.sourcesList.clear();
      this.sourcesInitialized.set(false);
      this.notifications.clear();
      this.notifications.invalidate();
      this.notifications = new SourceInstrumentationHandler.SourcesNotificationQueue();
   }

   SourceInstrumentationHandler.SourcesNotificationQueue addBinding(EventBinding.Source<?> binding, boolean notify) {
      SourceInstrumentationHandler.SourcesNotificationQueue notificationsToProcess = null;
      Lock lock = this.bindingsLock.writeLock();
      lock.lock();

      try {
         boolean initializeSources = false;
         if (this.bindings.isEmpty()) {
            initializeSources = true;
         }

         this.bindings.add(binding);
         binding.attachedSemaphore.release();
         if (notify) {
            notificationsToProcess = this.addAllSourcesNotification(binding);
         } else if (initializeSources) {
            notificationsToProcess = this.addInitializeSourcesNotification();
         }
      } finally {
         lock.unlock();
      }

      return notificationsToProcess;
   }

   SourceInstrumentationHandler.SourcesNotificationQueue addNewSources(Map<Source, Void> newSources, boolean notify) {
      SourceInstrumentationHandler.SourcesNotificationQueue notificationsToProcess = null;
      Lock lock = this.bindingsLock.readLock();
      lock.lock();

      try {
         if (!this.bindings.isEmpty()) {
            synchronized (this.sources) {
               if (notify) {
                  notificationsToProcess = this.addNotification(newSources, this.bindings.getArray());
               } else {
                  for (Source src : newSources.keySet()) {
                     if (!this.sources.containsKey(src)) {
                        this.sources.put(src, null);
                        this.sourcesList.add(src);
                     }
                  }
               }
            }
         }
      } finally {
         lock.unlock();
      }

      return notificationsToProcess;
   }

   private class AllSourcesNotification extends SourceInstrumentationHandler.InitializeSourcesNotification {
      protected final EventBinding.Source<?>[] bindingsToNotify;
      protected Collection<Source> sourcesForNotification;

      AllSourcesNotification(EventBinding.Source<?>[] bindingsToNotify) {
         this.bindingsToNotify = bindingsToNotify;
      }

      @Override
      protected void resolveSources() {
         assert SourceInstrumentationHandler.this.bindingsLock.getReadHoldCount() > 0;

         assert Thread.holdsLock(SourceInstrumentationHandler.this.sources);

         assert SourceInstrumentationHandler.this.sourcesInitialized.get();

         boolean firstCall = this.sourcesResolved.compareAndSet(false, true);

         assert firstCall : "resolveSources called more than once.";

         this.sourcesForNotification = new ArrayList<>(SourceInstrumentationHandler.this.sourcesList.getNextInsertionIndex());

         for (Source source : SourceInstrumentationHandler.this.sourcesList) {
            this.sourcesForNotification.add(source);
         }
      }

      @Override
      protected final void runNotifications() {
         assert SourceInstrumentationHandler.this.bindingsLock.getReadHoldCount() + SourceInstrumentationHandler.this.bindingsLock.getWriteHoldCount() == 0;

         boolean firstCall = this.notificationsRun.compareAndSet(false, true);

         assert firstCall : "runNotifications called more than once.";

         if (this.sourcesForNotification != null) {
            for (Source src : this.sourcesForNotification) {
               SourceInstrumentationHandler.this.notificationConsumer.accept(this.bindingsToNotify, src);
            }
         }
      }
   }

   private class InitializeSourcesNotification extends SourceInstrumentationHandler.SourcesNotification {
      @Override
      protected void runNotifications() {
         boolean firstCall = this.notificationsRun.compareAndSet(false, true);

         assert firstCall : "runNotifications called more than once.";
      }

      @Override
      protected void resolveSources() {
         assert SourceInstrumentationHandler.this.bindingsLock.getReadHoldCount() > 0;

         assert Thread.holdsLock(SourceInstrumentationHandler.this.sources);

         assert SourceInstrumentationHandler.this.sourcesInitialized.get();

         boolean firstCall = this.sourcesResolved.compareAndSet(false, true);

         assert firstCall : "resolveSources called more than once.";
      }
   }

   private class NewSourcesNotification extends SourceInstrumentationHandler.AllSourcesNotification {
      protected final Collection<Source> collectedSources;

      NewSourcesNotification(EventBinding.Source<?>[] bindingsToNotify, Collection<Source> collectedSources) {
         super(bindingsToNotify);
         this.collectedSources = collectedSources;
      }

      @Override
      protected void resolveSources() {
         assert SourceInstrumentationHandler.this.bindingsLock.getReadHoldCount() > 0;

         assert Thread.holdsLock(SourceInstrumentationHandler.this.sources);

         assert SourceInstrumentationHandler.this.sourcesInitialized.get();

         boolean firstCall = this.sourcesResolved.compareAndSet(false, true);

         assert firstCall : "resolveSources called more than once.";

         this.sourcesForNotification = new ArrayList<>();

         for (Source src : this.collectedSources) {
            if (!SourceInstrumentationHandler.this.sources.containsKey(src)) {
               SourceInstrumentationHandler.this.sources.put(src, null);
               SourceInstrumentationHandler.this.sourcesList.add(src);
               this.sourcesForNotification.add(src);
            }
         }
      }
   }

   private abstract static class SourcesNotification {
      protected final AtomicBoolean sourcesResolved = new AtomicBoolean();
      protected final AtomicBoolean notificationsRun = new AtomicBoolean();

      protected abstract void resolveSources();

      protected abstract void runNotifications();
   }

   final class SourcesNotificationQueue {
      private boolean sourcesInitializationRequired;
      private boolean valid = true;
      private final Deque<SourceInstrumentationHandler.SourcesNotification> notificationQueue = new ArrayDeque<>();

      SourcesNotificationQueue() {
         this.sourcesInitializationRequired = true;
      }

      private boolean shouldProcess() {
         assert SourceInstrumentationHandler.this.bindingsLock.getWriteHoldCount() > 0
            || SourceInstrumentationHandler.this.bindingsLock.getReadHoldCount() > 0 && Thread.holdsLock(SourceInstrumentationHandler.this.sources);

         return this.notificationQueue.size() == 1;
      }

      private void enqueue(SourceInstrumentationHandler.SourcesNotification notification) {
         assert SourceInstrumentationHandler.this.bindingsLock.getWriteHoldCount() > 0
            || SourceInstrumentationHandler.this.bindingsLock.getReadHoldCount() > 0 && Thread.holdsLock(SourceInstrumentationHandler.this.sources);

         this.notificationQueue.add(notification);
      }

      private SourceInstrumentationHandler.SourcesNotification resolveFirst() {
         SourceInstrumentationHandler.SourcesNotification notification = null;
         Lock lock = SourceInstrumentationHandler.this.bindingsLock.readLock();
         lock.lock();

         try {
            if (this.valid) {
               synchronized (SourceInstrumentationHandler.this.sources) {
                  notification = this.notificationQueue.peekFirst();
                  this.sourcesInitializationRequired = false;
                  if (notification != null) {
                     notification.resolveSources();
                  }
               }
            }
         } finally {
            lock.unlock();
         }

         return notification;
      }

      private boolean removeFirst() {
         boolean queueNotEmpty = false;
         Lock lock = SourceInstrumentationHandler.this.bindingsLock.readLock();
         lock.lock();

         try {
            if (this.valid) {
               synchronized (SourceInstrumentationHandler.this.sources) {
                  this.notificationQueue.removeFirst();
                  if (!this.notificationQueue.isEmpty()) {
                     queueNotEmpty = true;
                  }
               }
            }
         } finally {
            lock.unlock();
         }

         return queueNotEmpty;
      }

      private void clear() {
         assert SourceInstrumentationHandler.this.bindingsLock.getWriteHoldCount() > 0;

         this.notificationQueue.clear();
      }

      private void invalidate() {
         assert SourceInstrumentationHandler.this.bindingsLock.getWriteHoldCount() > 0;

         this.valid = false;
      }

      void process() {
         do {
            SourceInstrumentationHandler.SourcesNotification notification = this.resolveFirst();
            if (notification != null) {
               try {
                  notification.runNotifications();
               } catch (Throwable var8) {
                  Lock writeLock = SourceInstrumentationHandler.this.bindingsLock.writeLock();
                  writeLock.lock();

                  try {
                     this.clear();
                  } finally {
                     writeLock.unlock();
                  }

                  throw var8;
               }
            }
         } while (this.removeFirst());
      }

      boolean isSourcesInitializationRequired() {
         return this.sourcesInitializationRequired;
      }
   }
}
