package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.ContextLocal;
import com.oracle.truffle.api.ContextThreadLocal;
import com.oracle.truffle.api.TruffleContext;
import java.util.List;

final class PolyglotLocals {
   static <T> ContextLocal<T> createLanguageContextLocal(Object factory) {
      return new PolyglotLocals.LanguageContextLocal<>(factory);
   }

   static <T> ContextLocal<T> createInstrumentContextLocal(Object factory) {
      return new PolyglotLocals.InstrumentContextLocal<>(factory);
   }

   static <T> ContextThreadLocal<T> createLanguageContextThreadLocal(Object factory) {
      return new PolyglotLocals.LanguageContextThreadLocal<>(factory);
   }

   static <T> ContextThreadLocal<T> createInstrumentContextThreadLocal(Object factory) {
      return new PolyglotLocals.InstrumentContextThreadLocal<>(factory);
   }

   static void initializeInstrumentContextLocals(List<PolyglotLocals.InstrumentContextLocal<?>> locals, PolyglotInstrument polyglotInstrument) {
      PolyglotLocals.LocalLocation[] locations;
      if (locals.isEmpty()) {
         locations = PolyglotEngineImpl.EMPTY_LOCATIONS;
      } else {
         for (PolyglotLocals.InstrumentContextLocal<?> local : locals) {
            local.instrument = polyglotInstrument;
         }

         locations = polyglotInstrument.engine.addContextLocals(locals);
      }

      polyglotInstrument.contextLocalLocations = locations;
   }

   static void initializeLanguageContextLocals(List<PolyglotLocals.LanguageContextLocal<?>> locals, PolyglotLanguageInstance polyglotLanguageInstance) {
      PolyglotLocals.LocalLocation[] locations;
      if (locals.isEmpty()) {
         locations = PolyglotEngineImpl.EMPTY_LOCATIONS;
      } else {
         for (PolyglotLocals.LanguageContextLocal<?> local : locals) {
            local.languageInstance = polyglotLanguageInstance;
         }

         locations = polyglotLanguageInstance.language.previousContextLocalLocations;
         if (locations != null) {
            if (locals.size() != locations.length) {
               throw new IllegalStateException(
                  String.format(
                     "Truffle language %s did not create the same number of context locals. Expected %s locals but were %s.",
                     polyglotLanguageInstance.spi.getClass().getName(),
                     locations.length,
                     locals.size()
                  )
               );
            }

            for (int i = 0; i < locations.length; i++) {
               locals.get(i).initializeLocation(locations[i]);
            }
         } else {
            PolyglotLanguage language = polyglotLanguageInstance.language;
            PolyglotEngineImpl engine = language.engine;
            language.previousContextLocalLocations = locations = engine.addContextLocals(locals);

            assert locations.length == locals.size();
         }
      }

      assert polyglotLanguageInstance.contextLocals == null : "current context locals can only be initialized once";

      polyglotLanguageInstance.contextLocals = locals;
      polyglotLanguageInstance.contextLocalLocations = locations;
   }

   static void initializeInstrumentContextThreadLocals(List<PolyglotLocals.InstrumentContextThreadLocal<?>> locals, PolyglotInstrument polyglotInstrument) {
      PolyglotLocals.LocalLocation[] locations;
      if (locals.isEmpty()) {
         locations = PolyglotEngineImpl.EMPTY_LOCATIONS;
      } else {
         for (PolyglotLocals.InstrumentContextThreadLocal<?> local : locals) {
            local.instrument = polyglotInstrument;
         }

         locations = polyglotInstrument.engine.addContextThreadLocals(locals);
      }

      polyglotInstrument.contextThreadLocalLocations = locations;
   }

   static void initializeLanguageContextThreadLocals(
      List<PolyglotLocals.LanguageContextThreadLocal<?>> locals, PolyglotLanguageInstance polyglotLanguageInstance
   ) {
      PolyglotLocals.LocalLocation[] locations;
      if (locals.isEmpty()) {
         locations = PolyglotEngineImpl.EMPTY_LOCATIONS;
      } else {
         for (PolyglotLocals.LanguageContextThreadLocal<?> local : locals) {
            local.initializeLanguageInstance(polyglotLanguageInstance);
         }

         locations = polyglotLanguageInstance.language.previousContextThreadLocalLocations;
         if (locations != null) {
            if (locals.size() != locations.length) {
               throw new IllegalStateException(
                  String.format(
                     "Truffle language %s did not create the same number of context thread locals. Expected %s locals but were %s.",
                     polyglotLanguageInstance.spi.getClass().getName(),
                     locations.length,
                     locals.size()
                  )
               );
            }

            for (int i = 0; i < locations.length; i++) {
               locals.get(i).initializeLocation(locations[i]);
            }
         } else {
            PolyglotLanguage language = polyglotLanguageInstance.language;
            PolyglotEngineImpl engine = language.engine;
            language.previousContextThreadLocalLocations = locations = engine.addContextThreadLocals(locals);

            assert locations.length == locals.size();
         }
      }

      assert polyglotLanguageInstance.contextThreadLocals == null : "current context locals can only be initialized once";

      polyglotLanguageInstance.contextThreadLocals = locals;
      polyglotLanguageInstance.contextThreadLocalLocations = locations;
   }

   @CompilerDirectives.TruffleBoundary
   static boolean assertLanguageCreated(PolyglotContextImpl context, PolyglotLanguage language) {
      if (context == null) {
         throw new IllegalStateException("No current context is entered.");
      } else if (context.localsCleared) {
         throw new IllegalStateException("Locals have already been cleared.");
      } else if (!context.getContext(language).isCreated()) {
         throw new IllegalStateException(String.format("Language context for language '%s' is not yet created in the context.", language.getId()));
      } else {
         return true;
      }
   }

   @CompilerDirectives.TruffleBoundary
   static boolean assertInstrumentCreated(PolyglotContextImpl context, PolyglotInstrument instrument) {
      if (context == null) {
         throw new IllegalStateException("No current context is entered.");
      } else if (context.localsCleared) {
         throw new IllegalStateException("Locals have already been cleared.");
      } else if (!instrument.isInitialized()) {
         throw new IllegalStateException(String.format("Instrument '%s' is not yet created in the  context.", instrument.getId()));
      } else {
         return true;
      }
   }

   abstract static class AbstractContextLocal<T> extends ContextLocal<T> {
      @CompilerDirectives.CompilationFinal
      PolyglotLocals.LocalLocation location;

      protected AbstractContextLocal() {
         super(PolyglotImpl.getInstance());
      }

      final void initializeLocation(PolyglotLocals.LocalLocation l) {
         assert this.location == null;

         this.location = l;
      }

      abstract PolyglotLocals.LocalLocation createLocation(int localIndex);
   }

   abstract static class AbstractContextThreadLocal<T> extends ContextThreadLocal<T> {
      @CompilerDirectives.CompilationFinal
      PolyglotLocals.LocalLocation location;

      protected AbstractContextThreadLocal() {
         super(PolyglotImpl.getInstance());
      }

      final void initializeLocation(PolyglotLocals.LocalLocation l) {
         assert this.location == null;

         this.location = l;
      }

      abstract PolyglotLocals.LocalLocation createLocation(int localIndex);
   }

   static final class InstrumentContextLocal<T> extends PolyglotLocals.AbstractContextLocal<T> {
      private PolyglotInstrument instrument;
      private final Object factory;

      protected InstrumentContextLocal(Object factory) {
         this.factory = factory;
      }

      @Override
      public T get() {
         assert PolyglotLocals.assertInstrumentCreated(PolyglotFastThreadLocals.getContext(null), this.instrument);

         PolyglotContextImpl c = PolyglotFastThreadLocals.getContextWithEngine(this.location.engine);
         return (T)c.getLocal(this.location);
      }

      @Override
      public T get(TruffleContext context) {
         PolyglotContextImpl c = (PolyglotContextImpl)EngineAccessor.LANGUAGE.getPolyglotContext(context);

         assert PolyglotLocals.assertInstrumentCreated(c, this.instrument);

         return (T)c.getLocal(this.location);
      }

      @Override
      PolyglotLocals.LocalLocation createLocation(int localIndex) {
         return new PolyglotLocals.InstrumentContextLocal.Location(this, localIndex);
      }

      private final class Location extends PolyglotLocals.LocalLocation {
         Location(PolyglotLocals.InstrumentContextLocal<?> local, int index) {
            super(local.instrument.engine, index);
         }

         @Override
         Object invokeFactoryImpl(PolyglotContextImpl context, Thread thread) {
            assert thread == null;

            if (context.engine != InstrumentContextLocal.this.instrument.engine) {
               throw new AssertionError("Invalid sharing of locations.");
            } else {
               return EngineAccessor.INSTRUMENT.invokeContextLocalFactory(InstrumentContextLocal.this.factory, context.creatorTruffleContext);
            }
         }
      }
   }

   static final class InstrumentContextThreadLocal<T> extends PolyglotLocals.AbstractContextThreadLocal<T> {
      private PolyglotInstrument instrument;
      private final Object factory;

      protected InstrumentContextThreadLocal(Object factory) {
         this.factory = factory;
      }

      @Override
      public T get() {
         assert PolyglotLocals.assertInstrumentCreated(PolyglotFastThreadLocals.getContext(null), this.instrument);

         return (T)PolyglotFastThreadLocals.getCurrentThreadEngine(this.location.engine).getThreadLocal(this.location);
      }

      @Override
      public T get(Thread t) {
         assert PolyglotLocals.assertInstrumentCreated(PolyglotFastThreadLocals.getContext(null), this.instrument);

         PolyglotContextImpl c = PolyglotFastThreadLocals.getContextWithEngine(this.location.engine);
         return (T)c.getThreadLocal(this.location, t);
      }

      @Override
      public T get(TruffleContext context) {
         PolyglotContextImpl c = (PolyglotContextImpl)EngineAccessor.LANGUAGE.getPolyglotContext(context);

         assert PolyglotLocals.assertInstrumentCreated(c, this.instrument);

         return (T)c.getThreadLocal(this.location, Thread.currentThread());
      }

      @Override
      public T get(TruffleContext context, Thread t) {
         PolyglotContextImpl c = (PolyglotContextImpl)EngineAccessor.LANGUAGE.getPolyglotContext(context);

         assert PolyglotLocals.assertInstrumentCreated(c, this.instrument);

         return (T)c.getThreadLocal(this.location, t);
      }

      @Override
      PolyglotLocals.LocalLocation createLocation(int localIndex) {
         return new PolyglotLocals.InstrumentContextThreadLocal.Location(localIndex);
      }

      private final class Location extends PolyglotLocals.LocalLocation {
         Location(int index) {
            super(InstrumentContextThreadLocal.this.instrument.engine, index);
         }

         @Override
         Object invokeFactoryImpl(PolyglotContextImpl context, Thread thread) {
            if (context.engine != InstrumentContextThreadLocal.this.instrument.engine) {
               throw new AssertionError("Invalid sharing of locations.");
            } else {
               return EngineAccessor.INSTRUMENT
                  .invokeContextThreadLocalFactory(InstrumentContextThreadLocal.this.factory, context.creatorTruffleContext, thread);
            }
         }
      }
   }

   static final class LanguageContextLocal<T> extends PolyglotLocals.AbstractContextLocal<T> {
      private final Object factory;
      private PolyglotLanguageInstance languageInstance;

      protected LanguageContextLocal(Object factory) {
         this.factory = factory;
      }

      @Override
      PolyglotLocals.LocalLocation createLocation(int index) {
         return new PolyglotLocals.LanguageContextLocal.Location(this.languageInstance.language, index);
      }

      @Override
      public T get() {
         PolyglotLocals.LocalLocation l = this.location;
         PolyglotContextImpl context = PolyglotFastThreadLocals.getContext(this.languageInstance.sharing);

         assert PolyglotLocals.assertLanguageCreated(context, this.languageInstance.language);

         return (T)context.getLocal(l);
      }

      @Override
      public T get(TruffleContext truffleContext) {
         PolyglotContextImpl context = (PolyglotContextImpl)EngineAccessor.LANGUAGE.getPolyglotContext(truffleContext);

         assert PolyglotLocals.assertLanguageCreated(context, this.languageInstance.language);

         return (T)context.getLocal(this.location);
      }

      private static final class Location extends PolyglotLocals.LocalLocation {
         private final PolyglotLanguage language;

         Location(PolyglotLanguage language, int index) {
            super(language.engine, index);
            this.language = language;
         }

         @Override
         Object invokeFactoryImpl(PolyglotContextImpl context, Thread thread) {
            assert thread == null;

            PolyglotLanguageContext languageContext = context.getContext(this.language);

            for (PolyglotLocals.LanguageContextLocal<?> local : languageContext.getLanguageInstance().contextLocals) {
               if (this.index == local.location.index) {
                  return EngineAccessor.LANGUAGE.invokeContextLocalFactory(local.factory, context.getContextImpl(this.language));
               }
            }

            throw new AssertionError("Local index " + this.index + " not found in language instance locals.");
         }
      }
   }

   static final class LanguageContextThreadLocal<T> extends PolyglotLocals.AbstractContextThreadLocal<T> {
      @CompilerDirectives.CompilationFinal
      private PolyglotLanguageInstance languageInstance;
      @CompilerDirectives.CompilationFinal
      private PolyglotSharingLayer sharingLayer;
      private final Object factory;

      protected LanguageContextThreadLocal(Object factory) {
         this.factory = factory;
      }

      void initializeLanguageInstance(PolyglotLanguageInstance instance) {
         this.languageInstance = instance;
         this.sharingLayer = instance.sharing;
      }

      @Override
      public T get() {
         assert PolyglotLocals.assertLanguageCreated(PolyglotFastThreadLocals.getContext(null), this.languageInstance.language);

         return (T)PolyglotFastThreadLocals.getCurrentThread(this.sharingLayer).getThreadLocal(this.location);
      }

      @Override
      public T get(Thread t) {
         PolyglotContextImpl c = PolyglotFastThreadLocals.getContext(this.sharingLayer);

         assert PolyglotLocals.assertLanguageCreated(c, this.languageInstance.language);

         return (T)c.getThreadLocal(this.location, t);
      }

      @Override
      public T get(TruffleContext context) {
         PolyglotContextImpl c = (PolyglotContextImpl)EngineAccessor.LANGUAGE.getPolyglotContext(context);

         assert PolyglotLocals.assertLanguageCreated(c, this.languageInstance.language);

         return (T)c.getThreadLocal(this.location, Thread.currentThread());
      }

      @Override
      public T get(TruffleContext context, Thread t) {
         PolyglotContextImpl c = (PolyglotContextImpl)EngineAccessor.LANGUAGE.getPolyglotContext(context);

         assert PolyglotLocals.assertLanguageCreated(c, this.languageInstance.language);

         return (T)c.getThreadLocal(this.location, t);
      }

      @Override
      PolyglotLocals.LocalLocation createLocation(int index) {
         return new PolyglotLocals.LanguageContextThreadLocal.Location(this.languageInstance.language, index);
      }

      private static final class Location extends PolyglotLocals.LocalLocation {
         private final PolyglotLanguage language;

         Location(PolyglotLanguage language, int index) {
            super(language.engine, index);
            this.language = language;
         }

         @Override
         Object invokeFactoryImpl(PolyglotContextImpl context, Thread thread) {
            PolyglotLanguageContext languageContext = context.getContext(this.language);

            for (PolyglotLocals.LanguageContextThreadLocal<?> local : languageContext.getLanguageInstance().contextThreadLocals) {
               if (this.index == local.location.index) {
                  return EngineAccessor.LANGUAGE.invokeContextThreadLocalFactory(local.factory, context.getContextImpl(this.language), thread);
               }
            }

            throw new AssertionError("Local index " + this.index + " not found in language instance locals.");
         }
      }
   }

   abstract static class LocalLocation {
      final PolyglotEngineImpl engine;
      final int index;
      @CompilerDirectives.CompilationFinal
      private volatile Class<?> profiledType;

      private LocalLocation(PolyglotEngineImpl engine, int index) {
         this.engine = engine;
         this.index = index;
      }

      final Object invokeFactory(PolyglotContextImpl context, Thread thread) {
         Object result = this.invokeFactoryImpl(context, thread);
         Class<?> profileType = this.profiledType;

         assert result != null : "result should already be checked for null";

         if (profileType == null) {
            this.profiledType = result.getClass();
         } else if (profileType != result.getClass()) {
            throw new IllegalStateException(
               String.format(
                  "The return context value type must be stable and exact. Expected %s but got %s for local %s.", profileType, result.getClass(), this
               )
            );
         }

         return result;
      }

      final Object readLocal(PolyglotContextImpl context, Object[] locals, boolean threadLocal) {
         assert locals != null && this.index < locals.length && locals[this.index] != null : this.invalidLocalMessage(context, locals);

         Object result;
         if (CompilerDirectives.inCompiledCode() && CompilerDirectives.isPartialEvaluationConstant(this)) {
            result = this.readLocalFast(locals, threadLocal);
         } else {
            result = locals[this.index];
         }

         assert result.getClass() == this.profiledType : this.invalidLocalMessage(context, locals);

         return result;
      }

      private Object readLocalFast(Object[] locals, boolean threadLocal) {
         PolyglotEngineImpl.StableLocalLocations stableLocations = threadLocal ? this.engine.contextThreadLocalLocations : this.engine.contextLocalLocations;
         PolyglotLocals.LocalLocation[] locations = stableLocations.locations;
         Object result;
         if (!stableLocations.assumption.isValid()) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            result = locals[this.index];
         } else {
            result = EngineAccessor.RUNTIME
               .unsafeCast(EngineAccessor.RUNTIME.castArrayFixedLength(locals, locations.length)[this.index], this.profiledType, true, true, true);
         }

         return result;
      }

      abstract Object invokeFactoryImpl(PolyglotContextImpl context, Thread thread);

      private String invalidLocalMessage(PolyglotContextImpl context, Object[] locals) {
         if (locals == null) {
            return "Invalid local state: Locals is null. Current context: " + context.toString();
         } else if (this.index >= 0 && this.index < locals.length) {
            Object value = locals[this.index];
            if (value == null) {
               return "Invalid local state: Local is not initialized. Engine closed: " + this.engine.closed + ". Current context: " + context.toString();
            } else {
               return locals[this.index].getClass() != this.profiledType
                  ? "Invalid local state: Invalid profiled type. Expected " + this.profiledType.getName() + " but was " + value.getClass().getName()
                  : "Invalid local state: Unknown reason. Current context: " + context.toString();
            }
         } else {
            return "Invalid local state: Locals index is out of bounds " + this.index + ". Current context: " + context.toString();
         }
      }
   }
}
