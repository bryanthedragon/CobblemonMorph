
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.InstrumentInfo;
import com.oracle.truffle.api.instrumentation.TruffleInstrument;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.InstrumentCache;
import com.oracle.truffle.polyglot.OptionValuesImpl;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotEngineImpl;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotLocals;
import java.util.function.Supplier;
import org.graalvm.options.OptionDescriptor;
import org.graalvm.options.OptionDescriptors;
import org.graalvm.polyglot.Instrument;

class PolyglotInstrument
implements PolyglotImpl.VMObject {
    Instrument api;
    InstrumentInfo info;
    final InstrumentCache cache;
    final PolyglotEngineImpl engine;
    private final Object instrumentLock = new Object();
    private volatile OptionDescriptors engineOptions;
    private volatile OptionDescriptors contextOptions;
    private volatile OptionDescriptors allOptions;
    private volatile OptionValuesImpl optionValues;
    private volatile boolean initialized;
    private volatile boolean created;
    private volatile boolean closed;
    int requestedAsyncStackDepth = 0;
    PolyglotLocals.LocalLocation[] contextLocalLocations;
    PolyglotLocals.LocalLocation[] contextThreadLocalLocations;

    PolyglotInstrument(PolyglotEngineImpl engine, InstrumentCache cache) {
        this.engine = engine;
        this.cache = cache;
    }

    public OptionDescriptors getOptions() {
        try {
            this.engine.checkState();
            return this.getAllOptionsInternal();
        }
        catch (Throwable t) {
            throw PolyglotImpl.guestToHostException(this.engine, t);
        }
    }

    OptionDescriptors getAllOptionsInternal() {
        this.ensureInitialized();
        return this.allOptions;
    }

    OptionDescriptors getEngineOptionsInternal() {
        this.ensureInitialized();
        return this.engineOptions;
    }

    OptionDescriptors getContextOptionsInternal() {
        this.ensureInitialized();
        return this.contextOptions;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    OptionValuesImpl getEngineOptionValues() {
        if (this.optionValues == null) {
            Object object = this.instrumentLock;
            synchronized (object) {
                if (this.optionValues == null) {
                    this.optionValues = new OptionValuesImpl(this.getAllOptionsInternal(), false);
                }
            }
        }
        return this.optionValues;
    }

    OptionValuesImpl getOptionValuesIfExists() {
        return this.optionValues;
    }

    @Override
    public PolyglotEngineImpl getEngine() {
        return this.engine;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private void ensureInitialized() {
        if (!this.initialized) {
            Object object = this.instrumentLock;
            synchronized (object) {
                if (!this.initialized) {
                    try {
                        EngineAccessor.INSTRUMENT.initializeInstrument(this.engine.instrumentationHandler, this, this.cache.getClassName(), (Supplier<? extends Object>)new Supplier<TruffleInstrument>(){

                            @Override
                            public TruffleInstrument get() {
                                return PolyglotInstrument.this.cache.loadInstrument();
                            }
                        });
                        OptionDescriptors eOptions = EngineAccessor.INSTRUMENT.describeEngineOptions(this.engine.instrumentationHandler, this, this.cache.getId());
                        OptionDescriptors cOptions = EngineAccessor.INSTRUMENT.describeContextOptions(this.engine.instrumentationHandler, this, this.cache.getId());
                        assert (PolyglotInstrument.verifyNoOverlap(eOptions, cOptions));
                        this.engineOptions = eOptions;
                        this.contextOptions = cOptions;
                        this.allOptions = OptionDescriptors.createUnion(eOptions, cOptions);
                    }
                    catch (Exception e) {
                        throw new IllegalStateException(String.format("Error initializing instrument '%s' using class '%s'. Message: %s.", this.cache.getId(), this.cache.getClassName(), e.getMessage()), e);
                    }
                    assert (this.contextLocalLocations != null) : "context local locations not initialized";
                    this.initialized = true;
                }
            }
        }
    }

    private static boolean verifyNoOverlap(OptionDescriptors engineOptions, OptionDescriptors contextOptions) {
        for (OptionDescriptor engineDescriptor : engineOptions) {
            if (contextOptions.get(engineDescriptor.getName()) != null) {
                throw new AssertionError((Object)("Overlapping descriptor name " + engineDescriptor.getName() + " between context and engine options detected."));
            }
        }
        return true;
    }

    public boolean isInitialized() {
        return this.initialized;
    }

    public boolean isCreated() {
        return this.created;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void ensureCreated() {
        if (!this.created) {
            PolyglotContextImpl[] contexts = null;
            Object object = this.instrumentLock;
            synchronized (object) {
                if (!this.created) {
                    if (!this.initialized) {
                        this.ensureInitialized();
                    }
                    if (this.contextLocalLocations.length > 0) {
                        Object object2 = this.engine.lock;
                        synchronized (object2) {
                            contexts = this.engine.collectAliveContexts().toArray(new PolyglotContextImpl[0]);
                        }
                    }
                    EngineAccessor.INSTRUMENT.createInstrument(this.engine.instrumentationHandler, this, this.cache.services(), this.getEngineOptionValues());
                    this.created = true;
                }
            }
            if (contexts != null) {
                for (PolyglotContextImpl context : contexts) {
                    context.invokeLocalsFactories(this.contextLocalLocations, this.contextThreadLocalLocations);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void notifyClosing() {
        if (this.created && !this.closed) {
            Object object = this.instrumentLock;
            synchronized (object) {
                if (this.created && !this.closed) {
                    EngineAccessor.INSTRUMENT.finalizeInstrument(this.engine.instrumentationHandler, this);
                }
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void ensureClosed() {
        if (this.created && !this.closed) {
            Object object = this.instrumentLock;
            synchronized (object) {
                if (this.created && !this.closed) {
                    EngineAccessor.INSTRUMENT.disposeInstrument(this.engine.instrumentationHandler, this, false);
                }
                this.closed = true;
                this.engineOptions = null;
                this.optionValues = null;
            }
        }
    }

    public <T> T lookup(Class<T> serviceClass) {
        try {
            this.engine.checkState();
            return this.lookupInternal(serviceClass);
        }
        catch (Throwable t) {
            throw PolyglotImpl.guestToHostException(this.engine, t);
        }
    }

    <T> T lookupInternal(Class<T> serviceClass) {
        if (this.cache.supportsService(serviceClass)) {
            this.ensureCreated();
            return EngineAccessor.INSTRUMENT.getInstrumentationHandlerService(this.engine.instrumentationHandler, this, serviceClass);
        }
        return null;
    }

    public String getId() {
        return this.cache.getId();
    }

    public String getName() {
        return this.cache.getName();
    }

    public String getVersion() {
        String version = this.cache.getVersion();
        if (version.equals("inherit")) {
            return this.engine.getVersion();
        }
        return version;
    }

    public String getWebsite() {
        return this.cache.getWebsite();
    }
}

