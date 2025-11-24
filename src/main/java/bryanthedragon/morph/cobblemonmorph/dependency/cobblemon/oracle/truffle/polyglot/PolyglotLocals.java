
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.ContextLocal;
import com.oracle.truffle.api.ContextThreadLocal;
import com.oracle.truffle.api.TruffleContext;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotEngineImpl;
import com.oracle.truffle.polyglot.PolyglotFastThreadLocals;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotInstrument;
import com.oracle.truffle.polyglot.PolyglotLanguage;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotLanguageInstance;
import com.oracle.truffle.polyglot.PolyglotSharingLayer;
import java.util.List;

final class PolyglotLocals {
    PolyglotLocals() {
    }

    static <T> ContextLocal<T> createLanguageContextLocal(Object factory) {
        return new LanguageContextLocal(factory);
    }

    static <T> ContextLocal<T> createInstrumentContextLocal(Object factory) {
        return new InstrumentContextLocal(factory);
    }

    static <T> ContextThreadLocal<T> createLanguageContextThreadLocal(Object factory) {
        return new LanguageContextThreadLocal(factory);
    }

    static <T> ContextThreadLocal<T> createInstrumentContextThreadLocal(Object factory) {
        return new InstrumentContextThreadLocal(factory);
    }

    static void initializeInstrumentContextLocals(List<InstrumentContextLocal<?>> locals, PolyglotInstrument polyglotInstrument) {
        LocalLocation[] locations;
        if (locals.isEmpty()) {
            locations = PolyglotEngineImpl.EMPTY_LOCATIONS;
        } else {
            for (InstrumentContextLocal<?> local : locals) {
                local.instrument = polyglotInstrument;
            }
            locations = polyglotInstrument.engine.addContextLocals(locals);
        }
        polyglotInstrument.contextLocalLocations = locations;
    }

    static void initializeLanguageContextLocals(List<LanguageContextLocal<?>> locals, PolyglotLanguageInstance polyglotLanguageInstance) {
        LocalLocation[] locations;
        if (locals.isEmpty()) {
            locations = PolyglotEngineImpl.EMPTY_LOCATIONS;
        } else {
            for (LanguageContextLocal<?> local : locals) {
                local.languageInstance = polyglotLanguageInstance;
            }
            locations = polyglotLanguageInstance.language.previousContextLocalLocations;
            if (locations != null) {
                if (locals.size() != locations.length) {
                    throw new IllegalStateException(String.format("Truffle language %s did not create the same number of context locals. Expected %s locals but were %s.", polyglotLanguageInstance.spi.getClass().getName(), locations.length, locals.size()));
                }
                for (int i = 0; i < locations.length; ++i) {
                    locals.get(i).initializeLocation(locations[i]);
                }
            } else {
                PolyglotLanguage language = polyglotLanguageInstance.language;
                PolyglotEngineImpl engine = language.engine;
                locations = engine.addContextLocals(locals);
                language.previousContextLocalLocations = locations;
                assert (locations.length == locals.size());
            }
        }
        assert (polyglotLanguageInstance.contextLocals == null) : "current context locals can only be initialized once";
        polyglotLanguageInstance.contextLocals = locals;
        polyglotLanguageInstance.contextLocalLocations = locations;
    }

    static void initializeInstrumentContextThreadLocals(List<InstrumentContextThreadLocal<?>> locals, PolyglotInstrument polyglotInstrument) {
        LocalLocation[] locations;
        if (locals.isEmpty()) {
            locations = PolyglotEngineImpl.EMPTY_LOCATIONS;
        } else {
            for (InstrumentContextThreadLocal<?> local : locals) {
                local.instrument = polyglotInstrument;
            }
            locations = polyglotInstrument.engine.addContextThreadLocals(locals);
        }
        polyglotInstrument.contextThreadLocalLocations = locations;
    }

    static void initializeLanguageContextThreadLocals(List<LanguageContextThreadLocal<?>> locals, PolyglotLanguageInstance polyglotLanguageInstance) {
        LocalLocation[] locations;
        if (locals.isEmpty()) {
            locations = PolyglotEngineImpl.EMPTY_LOCATIONS;
        } else {
            for (LanguageContextThreadLocal<?> local : locals) {
                local.initializeLanguageInstance(polyglotLanguageInstance);
            }
            locations = polyglotLanguageInstance.language.previousContextThreadLocalLocations;
            if (locations != null) {
                if (locals.size() != locations.length) {
                    throw new IllegalStateException(String.format("Truffle language %s did not create the same number of context thread locals. Expected %s locals but were %s.", polyglotLanguageInstance.spi.getClass().getName(), locations.length, locals.size()));
                }
                for (int i = 0; i < locations.length; ++i) {
                    locals.get(i).initializeLocation(locations[i]);
                }
            } else {
                PolyglotLanguage language = polyglotLanguageInstance.language;
                PolyglotEngineImpl engine = language.engine;
                locations = engine.addContextThreadLocals(locals);
                language.previousContextThreadLocalLocations = locations;
                assert (locations.length == locals.size());
            }
        }
        assert (polyglotLanguageInstance.contextThreadLocals == null) : "current context locals can only be initialized once";
        polyglotLanguageInstance.contextThreadLocals = locals;
        polyglotLanguageInstance.contextThreadLocalLocations = locations;
    }

    @CompilerDirectives.TruffleBoundary
    static boolean assertLanguageCreated(PolyglotContextImpl context, PolyglotLanguage language) {
        if (context == null) {
            throw new IllegalStateException("No current context is entered.");
        }
        if (context.localsCleared) {
            throw new IllegalStateException("Locals have already been cleared.");
        }
        if (!context.getContext(language).isCreated()) {
            throw new IllegalStateException(String.format("Language context for language '%s' is not yet created in the context.", language.getId()));
        }
        return true;
    }

    @CompilerDirectives.TruffleBoundary
    static boolean assertInstrumentCreated(PolyglotContextImpl context, PolyglotInstrument instrument) {
        if (context == null) {
            throw new IllegalStateException("No current context is entered.");
        }
        if (context.localsCleared) {
            throw new IllegalStateException("Locals have already been cleared.");
        }
        if (!instrument.isInitialized()) {
            throw new IllegalStateException(String.format("Instrument '%s' is not yet created in the  context.", instrument.getId()));
        }
        return true;
    }

    static abstract class LocalLocation {
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
            assert (result != null) : "result should already be checked for null";
            if (profileType == null) {
                this.profiledType = result.getClass();
            } else if (profileType != result.getClass()) {
                throw new IllegalStateException(String.format("The return context value type must be stable and exact. Expected %s but got %s for local %s.", profileType, result.getClass(), this));
            }
            return result;
        }

        final Object readLocal(PolyglotContextImpl context, Object[] locals, boolean threadLocal) {
            assert (locals != null && this.index < locals.length && locals[this.index] != null) : this.invalidLocalMessage(context, locals);
            Object result = CompilerDirectives.inCompiledCode() && CompilerDirectives.isPartialEvaluationConstant(this) ? this.readLocalFast(locals, threadLocal) : locals[this.index];
            assert (result.getClass() == this.profiledType) : this.invalidLocalMessage(context, locals);
            return result;
        }

        private Object readLocalFast(Object[] locals, boolean threadLocal) {
            Object result;
            PolyglotEngineImpl.StableLocalLocations stableLocations = threadLocal ? this.engine.contextThreadLocalLocations : this.engine.contextLocalLocations;
            LocalLocation[] locations = stableLocations.locations;
            if (!stableLocations.assumption.isValid()) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                result = locals[this.index];
            } else {
                result = EngineAccessor.RUNTIME.unsafeCast(EngineAccessor.RUNTIME.castArrayFixedLength(locals, locations.length)[this.index], this.profiledType, true, true, true);
            }
            return result;
        }

        abstract Object invokeFactoryImpl(PolyglotContextImpl var1, Thread var2);

        private String invalidLocalMessage(PolyglotContextImpl context, Object[] locals) {
            if (locals == null) {
                return "Invalid local state: Locals is null. Current context: " + context.toString();
            }
            if (this.index < 0 || this.index >= locals.length) {
                return "Invalid local state: Locals index is out of bounds " + this.index + ". Current context: " + context.toString();
            }
            Object value2 = locals[this.index];
            if (value2 == null) {
                return "Invalid local state: Local is not initialized. Engine closed: " + this.engine.closed + ". Current context: " + context.toString();
            }
            if (locals[this.index].getClass() != this.profiledType) {
                return "Invalid local state: Invalid profiled type. Expected " + this.profiledType.getName() + " but was " + value2.getClass().getName();
            }
            return "Invalid local state: Unknown reason. Current context: " + context.toString();
        }
    }

    static final class InstrumentContextThreadLocal<T>
    extends AbstractContextThreadLocal<T> {
        private PolyglotInstrument instrument;
        private final Object factory;

        protected InstrumentContextThreadLocal(Object factory) {
            this.factory = factory;
        }

        @Override
        public T get() {
            assert (PolyglotLocals.assertInstrumentCreated(PolyglotFastThreadLocals.getContext(null), this.instrument));
            return (T)PolyglotFastThreadLocals.getCurrentThreadEngine(this.location.engine).getThreadLocal(this.location);
        }

        @Override
        public T get(Thread t) {
            assert (PolyglotLocals.assertInstrumentCreated(PolyglotFastThreadLocals.getContext(null), this.instrument));
            PolyglotContextImpl c = PolyglotFastThreadLocals.getContextWithEngine(this.location.engine);
            return (T)c.getThreadLocal(this.location, t);
        }

        @Override
        public T get(TruffleContext context) {
            PolyglotContextImpl c = (PolyglotContextImpl)EngineAccessor.LANGUAGE.getPolyglotContext(context);
            assert (PolyglotLocals.assertInstrumentCreated(c, this.instrument));
            return (T)c.getThreadLocal(this.location, Thread.currentThread());
        }

        @Override
        public T get(TruffleContext context, Thread t) {
            PolyglotContextImpl c = (PolyglotContextImpl)EngineAccessor.LANGUAGE.getPolyglotContext(context);
            assert (PolyglotLocals.assertInstrumentCreated(c, this.instrument));
            return (T)c.getThreadLocal(this.location, t);
        }

        @Override
        LocalLocation createLocation(int localIndex) {
            return new Location(localIndex);
        }

        private final class Location
        extends LocalLocation {
            Location(int index) {
                super(InstrumentContextThreadLocal.this.instrument.engine, index);
            }

            @Override
            Object invokeFactoryImpl(PolyglotContextImpl context, Thread thread) {
                if (context.engine != InstrumentContextThreadLocal.this.instrument.engine) {
                    throw new AssertionError((Object)"Invalid sharing of locations.");
                }
                return EngineAccessor.INSTRUMENT.invokeContextThreadLocalFactory(InstrumentContextThreadLocal.this.factory, context.creatorTruffleContext, thread);
            }
        }
    }

    static final class LanguageContextThreadLocal<T>
    extends AbstractContextThreadLocal<T> {
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
            assert (PolyglotLocals.assertLanguageCreated(PolyglotFastThreadLocals.getContext(null), this.languageInstance.language));
            return (T)PolyglotFastThreadLocals.getCurrentThread(this.sharingLayer).getThreadLocal(this.location);
        }

        @Override
        public T get(Thread t) {
            PolyglotContextImpl c = PolyglotFastThreadLocals.getContext(this.sharingLayer);
            assert (PolyglotLocals.assertLanguageCreated(c, this.languageInstance.language));
            return (T)c.getThreadLocal(this.location, t);
        }

        @Override
        public T get(TruffleContext context) {
            PolyglotContextImpl c = (PolyglotContextImpl)EngineAccessor.LANGUAGE.getPolyglotContext(context);
            assert (PolyglotLocals.assertLanguageCreated(c, this.languageInstance.language));
            return (T)c.getThreadLocal(this.location, Thread.currentThread());
        }

        @Override
        public T get(TruffleContext context, Thread t) {
            PolyglotContextImpl c = (PolyglotContextImpl)EngineAccessor.LANGUAGE.getPolyglotContext(context);
            assert (PolyglotLocals.assertLanguageCreated(c, this.languageInstance.language));
            return (T)c.getThreadLocal(this.location, t);
        }

        @Override
        LocalLocation createLocation(int index) {
            return new Location(this.languageInstance.language, index);
        }

        private static final class Location
        extends LocalLocation {
            private final PolyglotLanguage language;

            Location(PolyglotLanguage language, int index) {
                super(language.engine, index);
                this.language = language;
            }

            @Override
            Object invokeFactoryImpl(PolyglotContextImpl context, Thread thread) {
                PolyglotLanguageContext languageContext = context.getContext(this.language);
                List<LanguageContextThreadLocal<?>> locals = languageContext.getLanguageInstance().contextThreadLocals;
                for (LanguageContextThreadLocal<?> local : locals) {
                    if (this.index != local.location.index) continue;
                    return EngineAccessor.LANGUAGE.invokeContextThreadLocalFactory(local.factory, context.getContextImpl(this.language), thread);
                }
                throw new AssertionError((Object)("Local index " + this.index + " not found in language instance locals."));
            }
        }
    }

    static abstract class AbstractContextThreadLocal<T>
    extends ContextThreadLocal<T> {
        @CompilerDirectives.CompilationFinal
        LocalLocation location;

        protected AbstractContextThreadLocal() {
            super(PolyglotImpl.getInstance());
        }

        final void initializeLocation(LocalLocation l) {
            assert (this.location == null);
            this.location = l;
        }

        abstract LocalLocation createLocation(int var1);
    }

    static final class LanguageContextLocal<T>
    extends AbstractContextLocal<T> {
        private final Object factory;
        private PolyglotLanguageInstance languageInstance;

        protected LanguageContextLocal(Object factory) {
            this.factory = factory;
        }

        @Override
        LocalLocation createLocation(int index) {
            return new Location(this.languageInstance.language, index);
        }

        @Override
        public T get() {
            LocalLocation l = this.location;
            PolyglotContextImpl context = PolyglotFastThreadLocals.getContext(this.languageInstance.sharing);
            assert (PolyglotLocals.assertLanguageCreated(context, this.languageInstance.language));
            return (T)context.getLocal(l);
        }

        @Override
        public T get(TruffleContext truffleContext) {
            PolyglotContextImpl context = (PolyglotContextImpl)EngineAccessor.LANGUAGE.getPolyglotContext(truffleContext);
            assert (PolyglotLocals.assertLanguageCreated(context, this.languageInstance.language));
            return (T)context.getLocal(this.location);
        }

        private static final class Location
        extends LocalLocation {
            private final PolyglotLanguage language;

            Location(PolyglotLanguage language, int index) {
                super(language.engine, index);
                this.language = language;
            }

            @Override
            Object invokeFactoryImpl(PolyglotContextImpl context, Thread thread) {
                assert (thread == null);
                PolyglotLanguageContext languageContext = context.getContext(this.language);
                List<LanguageContextLocal<?>> locals = languageContext.getLanguageInstance().contextLocals;
                for (LanguageContextLocal<?> local : locals) {
                    if (this.index != local.location.index) continue;
                    return EngineAccessor.LANGUAGE.invokeContextLocalFactory(local.factory, context.getContextImpl(this.language));
                }
                throw new AssertionError((Object)("Local index " + this.index + " not found in language instance locals."));
            }
        }
    }

    static final class InstrumentContextLocal<T>
    extends AbstractContextLocal<T> {
        private PolyglotInstrument instrument;
        private final Object factory;

        protected InstrumentContextLocal(Object factory) {
            this.factory = factory;
        }

        @Override
        public T get() {
            assert (PolyglotLocals.assertInstrumentCreated(PolyglotFastThreadLocals.getContext(null), this.instrument));
            PolyglotContextImpl c = PolyglotFastThreadLocals.getContextWithEngine(this.location.engine);
            return (T)c.getLocal(this.location);
        }

        @Override
        public T get(TruffleContext context) {
            PolyglotContextImpl c = (PolyglotContextImpl)EngineAccessor.LANGUAGE.getPolyglotContext(context);
            assert (PolyglotLocals.assertInstrumentCreated(c, this.instrument));
            return (T)c.getLocal(this.location);
        }

        @Override
        LocalLocation createLocation(int localIndex) {
            return new Location(this, localIndex);
        }

        private final class Location
        extends LocalLocation {
            Location(InstrumentContextLocal<?> local, int index) {
                super(local.instrument.engine, index);
            }

            @Override
            Object invokeFactoryImpl(PolyglotContextImpl context, Thread thread) {
                assert (thread == null);
                if (context.engine != InstrumentContextLocal.this.instrument.engine) {
                    throw new AssertionError((Object)"Invalid sharing of locations.");
                }
                return EngineAccessor.INSTRUMENT.invokeContextLocalFactory(InstrumentContextLocal.this.factory, context.creatorTruffleContext);
            }
        }
    }

    static abstract class AbstractContextLocal<T>
    extends ContextLocal<T> {
        @CompilerDirectives.CompilationFinal
        LocalLocation location;

        protected AbstractContextLocal() {
            super(PolyglotImpl.getInstance());
        }

        final void initializeLocation(LocalLocation l) {
            assert (this.location == null);
            this.location = l;
        }

        abstract LocalLocation createLocation(int var1);
    }
}

