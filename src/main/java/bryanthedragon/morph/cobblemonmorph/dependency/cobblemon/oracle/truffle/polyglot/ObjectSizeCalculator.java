
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.ContextLocal;
import com.oracle.truffle.api.ContextThreadLocal;
import com.oracle.truffle.api.InstrumentInfo;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.TruffleContext;
import com.oracle.truffle.api.TruffleFile;
import com.oracle.truffle.api.TruffleLanguage;
import com.oracle.truffle.api.TruffleLogger;
import com.oracle.truffle.api.TruffleOptions;
import com.oracle.truffle.api.dsl.NodeFactory;
import com.oracle.truffle.api.impl.DefaultTruffleRuntime;
import com.oracle.truffle.api.instrumentation.AllocationReporter;
import com.oracle.truffle.api.instrumentation.ExecutionEventListener;
import com.oracle.truffle.api.instrumentation.TruffleInstrument;
import com.oracle.truffle.api.io.TruffleProcessBuilder;
import com.oracle.truffle.api.nodes.LanguageInfo;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.polyglot.EngineAccessor;
import com.oracle.truffle.polyglot.PolyglotContextConfig;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotImpl;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotWrapper;
import java.lang.ref.Reference;
import java.lang.reflect.Array;
import java.util.ArrayDeque;
import java.util.Arrays;
import java.util.Collection;
import java.util.Deque;
import java.util.IdentityHashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CancellationException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import org.graalvm.options.OptionValues;
import org.graalvm.polyglot.Context;
import org.graalvm.polyglot.Engine;
import org.graalvm.polyglot.Instrument;
import org.graalvm.polyglot.Language;
import org.graalvm.polyglot.Value;

final class ObjectSizeCalculator {
    private static volatile int staticObjectAlignment = -1;
    private boolean cachedClassInfosInUse;
    private Map<Class<?>, ClassInfo> cachedClassInfos;
    private int alreadyVisitedInitialCapacity = 16384;

    ObjectSizeCalculator() {
    }

    private static int getObjectAlignment() {
        int localObjectAlignment = staticObjectAlignment;
        if (localObjectAlignment < 0) {
            localObjectAlignment = EngineAccessor.RUNTIME.getObjectAlignment();
            assert (localObjectAlignment > -1);
            staticObjectAlignment = localObjectAlignment;
        }
        return localObjectAlignment;
    }

    private static ForcedStop enqueueOrStop(CalculationState calculationState, Object obj) {
        ClassInfo classInfo = ObjectSizeCalculator.canProceed(calculationState.classInfos, obj);
        if (classInfo != StopClassInfo.INSTANCE && calculationState.alreadyVisited.add(obj)) {
            classInfo.increaseByBaseSize(calculationState, obj);
            if (calculationState.dataSize > calculationState.stopAtBytes) {
                return ForcedStop.STOPATBYTES;
            }
            if (calculationState.cancelled.get()) {
                return ForcedStop.CANCELLATION;
            }
            ObjectSizeCalculator.enqueue(calculationState.pending, obj);
        }
        return ForcedStop.NONE;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @CompilerDirectives.TruffleBoundary
    long calculateObjectSize(Object obj, long stopAtBytes, AtomicBoolean cancelled) {
        CalculationState calculationState;
        if (TruffleOptions.AOT || Truffle.getRuntime() instanceof DefaultTruffleRuntime) {
            throw new UnsupportedOperationException("Polyglot context heap size calculation is not supported on this platform.");
        }
        boolean usingCachedClassInfos = false;
        ObjectSizeCalculator objectSizeCalculator = this;
        synchronized (objectSizeCalculator) {
            IdentityHashMap<Class<?>, ClassInfo> classInfosToUse;
            if (!this.cachedClassInfosInUse) {
                if (this.cachedClassInfos == null) {
                    this.cachedClassInfos = new IdentityHashMap();
                }
                classInfosToUse = this.cachedClassInfos;
                this.cachedClassInfosInUse = true;
                usingCachedClassInfos = true;
            } else {
                classInfosToUse = new IdentityHashMap();
            }
            calculationState = new CalculationState(classInfosToUse, new QuickIdentitySet<Object>(this.alreadyVisitedInitialCapacity), stopAtBytes, cancelled);
        }
        try {
            if (cancelled.get()) {
                throw ObjectSizeCalculator.cancel(calculationState.dataSize);
            }
            ForcedStop stop = ObjectSizeCalculator.enqueueOrStop(calculationState, obj);
            Object o = calculationState.pending.pollFirst();
            while (true) {
                if (o != null) {
                    stop = ObjectSizeCalculator.visit(calculationState, o);
                }
                if (calculationState.pending.isEmpty() || stop == ForcedStop.STOPATBYTES) {
                    long l = calculationState.dataSize;
                    return l;
                }
                if (stop == ForcedStop.CANCELLATION) {
                    throw ObjectSizeCalculator.cancel(calculationState.dataSize);
                }
                o = calculationState.pending.pollFirst();
            }
        }
        finally {
            ObjectSizeCalculator objectSizeCalculator2 = this;
            synchronized (objectSizeCalculator2) {
                if (usingCachedClassInfos) {
                    this.cachedClassInfosInUse = false;
                }
                if (calculationState.alreadyVisited.getCapacity() > this.alreadyVisitedInitialCapacity) {
                    this.alreadyVisitedInitialCapacity = calculationState.alreadyVisited.getCapacity();
                }
            }
        }
    }

    private static CancellationException cancel(long dataSize) {
        throw new CancellationException(String.format("cancelled at %d bytes", dataSize));
    }

    private static ClassInfo getClassInfo(Map<Class<?>, ClassInfo> classInfos, final Class<?> clazz) {
        return classInfos.computeIfAbsent(clazz, new Function<Class<?>, ClassInfo>(){

            @Override
            public ClassInfo apply(Class<?> aClass) {
                return clazz.isArray() ? new ArrayClassInfo(aClass) : new ObjectClassInfo(aClass);
            }
        });
    }

    private static ForcedStop visit(CalculationState calculationState, Object obj) {
        Class<?> clazz = obj.getClass();
        if (clazz == ArrayElementsVisitor.class) {
            return ((ArrayElementsVisitor)obj).visit(calculationState);
        }
        return calculationState.classInfos.get(clazz).visit(calculationState, obj);
    }

    private static void increaseByArraySize(CalculationState calculationState, ArrayMemoryLayout layout, int length) {
        ObjectSizeCalculator.increaseSize(calculationState, ObjectSizeCalculator.roundToObjectAlignment(layout.baseOffset + length * layout.indexScale, ObjectSizeCalculator.getObjectAlignment()));
    }

    private static boolean isContextHeapBoundary(Object obj) {
        if (obj == null) {
            return true;
        }
        assert (!(obj instanceof PolyglotImpl.VMObject && !(obj instanceof PolyglotLanguageContext) && !(obj instanceof PolyglotContextImpl) || obj instanceof PolyglotContextConfig || obj instanceof TruffleLanguage.Provider || obj instanceof ExecutionEventListener || obj instanceof ClassValue || obj instanceof ClassLoader || obj instanceof PolyglotWrapper || obj instanceof Value || obj instanceof Context || obj instanceof Engine || obj instanceof Language || obj instanceof Instrument || obj instanceof org.graalvm.polyglot.Source || obj instanceof org.graalvm.polyglot.SourceSection)) : obj.getClass().getName() + " should not be reachable";
        return obj instanceof Thread || EngineAccessor.HOST.isHostBoundaryValue(obj) || obj instanceof Class || obj instanceof OptionValues || obj instanceof TruffleLanguage.ContextReference || obj instanceof TruffleLanguage.LanguageReference || obj instanceof Source || obj instanceof SourceSection || obj instanceof TruffleFile || obj instanceof TruffleLogger || obj instanceof InstrumentInfo || obj instanceof LanguageInfo || obj instanceof TruffleProcessBuilder || obj instanceof CallTarget || obj instanceof Node || obj instanceof NodeFactory || obj instanceof AllocationReporter || obj instanceof Assumption || obj instanceof TruffleLanguage || obj instanceof TruffleLanguage.Env || obj instanceof TruffleInstrument || obj instanceof TruffleInstrument.Env || obj instanceof TruffleContext || obj instanceof ContextLocal || obj instanceof ContextThreadLocal || obj instanceof PolyglotImpl.VMObject || obj instanceof PolyglotContextConfig || obj instanceof TruffleLanguage.Provider || obj instanceof ExecutionEventListener || obj instanceof ClassValue || obj instanceof ClassLoader || obj instanceof PolyglotWrapper || obj instanceof Value || obj instanceof Context || obj instanceof Engine || obj instanceof Language || obj instanceof Instrument || obj instanceof org.graalvm.polyglot.Source || obj instanceof org.graalvm.polyglot.SourceSection;
    }

    private static ClassInfo canProceed(Map<Class<?>, ClassInfo> classInfos, Object obj) {
        boolean eligible;
        if (obj == null) {
            return StopClassInfo.INSTANCE;
        }
        Class<?> clazz = obj.getClass();
        ClassInfo classInfo = classInfos.get(clazz);
        if (classInfo != null) {
            return classInfo;
        }
        boolean bl = eligible = !ObjectSizeCalculator.isContextHeapBoundary(obj);
        if (eligible) {
            classInfo = ObjectSizeCalculator.getClassInfo(classInfos, clazz);
        } else {
            classInfo = StopClassInfo.INSTANCE;
            classInfos.put(clazz, classInfo);
        }
        return classInfo;
    }

    private static void enqueue(Deque<Object> pending, Object obj) {
        pending.addLast(obj);
    }

    private static void increaseSize(CalculationState calculationState, long objectSize) {
        calculationState.dataSize += objectSize;
    }

    private static long roundToObjectAlignment(long x, int objectAlignment) {
        return (x + (long)objectAlignment - 1L) / (long)objectAlignment * (long)objectAlignment;
    }

    private static final class QuickIdentitySet<T>
    implements Set<T> {
        private Object[] data;
        private int size;
        private int capacity;
        private int growLimit;

        QuickIdentitySet(int initialCapacity) {
            if (initialCapacity < 1) {
                throw new IllegalArgumentException();
            }
            this.capacity = initialCapacity;
            this.data = new Object[this.capacity];
            this.updateGrowLimit();
        }

        private void updateGrowLimit() {
            this.growLimit = this.capacity / 2;
        }

        @Override
        public int size() {
            return this.size;
        }

        public int getCapacity() {
            return this.capacity;
        }

        @Override
        public boolean isEmpty() {
            return this.size() == 0;
        }

        @Override
        public boolean contains(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public Iterator<T> iterator() {
            throw new UnsupportedOperationException();
        }

        @Override
        public Object[] toArray() {
            throw new UnsupportedOperationException();
        }

        @Override
        public <T1> T1[] toArray(T1[] a) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean add(T t) {
            if (t == null) {
                throw new IllegalArgumentException();
            }
            int i = System.identityHashCode(t) % this.capacity;
            if (i < 0) {
                i += this.capacity;
            }
            while (this.data[i] != null && this.data[i] != t) {
                if (++i != this.capacity) continue;
                i = 0;
            }
            if (this.data[i] == null) {
                this.data[i] = t;
                ++this.size;
                if (this.size > this.growLimit) {
                    this.grow();
                }
                return true;
            }
            return false;
        }

        private void addFast(Object t) {
            int i = System.identityHashCode(t) % this.capacity;
            if (i < 0) {
                i += this.capacity;
            }
            while (this.data[i] != null) {
                if (++i != this.capacity) continue;
                i = 0;
            }
            this.data[i] = t;
        }

        private void grow() {
            this.capacity = Math.multiplyExact(2, this.capacity);
            Object[] oldData = this.data;
            this.data = new Object[this.capacity];
            for (Object obj : oldData) {
                if (obj == null) continue;
                this.addFast(obj);
            }
            this.updateGrowLimit();
        }

        @Override
        public boolean remove(Object o) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean containsAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean addAll(Collection<? extends T> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean retainAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public boolean removeAll(Collection<?> c) {
            throw new UnsupportedOperationException();
        }

        @Override
        public void clear() {
            if (this.size > 0) {
                Arrays.fill(this.data, null);
            }
            this.size = 0;
        }
    }

    private static final class ObjectClassInfo
    implements ClassInfo {
        private final long objectSize;
        private final Object[] resolvedJavaFields;
        private final boolean isReference;

        ObjectClassInfo(Class<?> clazz) {
            this.resolvedJavaFields = EngineAccessor.RUNTIME.getResolvedFields(clazz, false, true);
            this.objectSize = EngineAccessor.RUNTIME.getBaseInstanceSize(clazz);
            this.isReference = Reference.class.isAssignableFrom(clazz);
        }

        @Override
        public void increaseByBaseSize(CalculationState calculationState, Object obj) {
            ObjectSizeCalculator.increaseSize(calculationState, this.objectSize);
        }

        @Override
        public ForcedStop visit(CalculationState calculationState, Object obj) {
            if (this.isReference) {
                Object nextObj = null;
                try {
                    nextObj = ((Reference)obj).get();
                }
                catch (Exception exception) {
                    // empty catch block
                }
                ForcedStop stop = ObjectSizeCalculator.enqueueOrStop(calculationState, nextObj);
                if (stop != ForcedStop.NONE) {
                    return stop;
                }
            }
            for (Object f : this.resolvedJavaFields) {
                Object nextObj = EngineAccessor.RUNTIME.getFieldValue(f, obj);
                ForcedStop stop = ObjectSizeCalculator.enqueueOrStop(calculationState, nextObj);
                if (stop == ForcedStop.NONE) continue;
                return stop;
            }
            return ForcedStop.NONE;
        }
    }

    private static final class ArrayClassInfo
    implements ClassInfo {
        private final ArrayMemoryLayout arrayMemoryLayout;
        private final boolean isPrimitive;

        ArrayClassInfo(Class<?> clazz) {
            Class<?> componentType = clazz.getComponentType();
            if (componentType.isPrimitive()) {
                this.arrayMemoryLayout = ArrayMemoryLayout.getArrayMemoryLayouts().get(componentType);
                this.isPrimitive = true;
            } else {
                this.arrayMemoryLayout = ArrayMemoryLayout.getArrayMemoryLayouts().get(Object.class);
                this.isPrimitive = false;
            }
        }

        @Override
        public void increaseByBaseSize(CalculationState calculationState, Object obj) {
            int length = Array.getLength(obj);
            ObjectSizeCalculator.increaseByArraySize(calculationState, this.arrayMemoryLayout, length);
        }

        @Override
        public ForcedStop visit(CalculationState calculationState, Object obj) {
            if (!this.isPrimitive) {
                int length = Array.getLength(obj);
                switch (length) {
                    case 0: {
                        break;
                    }
                    case 1: {
                        Object o = Array.get(obj, 0);
                        return ObjectSizeCalculator.enqueueOrStop(calculationState, o);
                    }
                    default: {
                        ObjectSizeCalculator.enqueue(calculationState.pending, new ArrayElementsVisitor((Object[])obj, calculationState.alreadyVisited));
                    }
                }
            }
            return ForcedStop.NONE;
        }
    }

    private static final class StopClassInfo
    implements ClassInfo {
        static final StopClassInfo INSTANCE = new StopClassInfo();

        StopClassInfo() {
        }

        @Override
        public ForcedStop visit(CalculationState calculationState, Object obj) {
            return ForcedStop.NONE;
        }

        @Override
        public void increaseByBaseSize(CalculationState calculationState, Object obj) {
        }
    }

    private static interface ClassInfo {
        public ForcedStop visit(CalculationState var1, Object var2);

        public void increaseByBaseSize(CalculationState var1, Object var2);
    }

    private static final class ArrayElementsVisitor {
        private final Object[] array;
        private final QuickIdentitySet<Object> alreadyVisited;

        ArrayElementsVisitor(Object[] array, QuickIdentitySet<Object> alreadyVisited) {
            this.array = array;
            this.alreadyVisited = alreadyVisited;
        }

        public ForcedStop visit(CalculationState calculationState) {
            for (Object elem : this.array) {
                ClassInfo classInfo = ObjectSizeCalculator.canProceed(calculationState.classInfos, elem);
                if (classInfo == StopClassInfo.INSTANCE || !this.alreadyVisited.add(elem)) continue;
                classInfo.increaseByBaseSize(calculationState, elem);
                if (calculationState.dataSize > calculationState.stopAtBytes) {
                    return ForcedStop.STOPATBYTES;
                }
                if (calculationState.cancelled.get()) {
                    return ForcedStop.CANCELLATION;
                }
                ForcedStop stop = ObjectSizeCalculator.visit(calculationState, elem);
                if (stop == ForcedStop.NONE) continue;
                return stop;
            }
            return ForcedStop.NONE;
        }
    }

    private static final class CalculationState {
        private final Map<Class<?>, ClassInfo> classInfos;
        private final QuickIdentitySet<Object> alreadyVisited;
        private final Deque<Object> pending = new ArrayDeque<Object>(16384);
        private final long stopAtBytes;
        private final AtomicBoolean cancelled;
        private long dataSize;

        CalculationState(Map<Class<?>, ClassInfo> classInfos, QuickIdentitySet<Object> alreadyVisited, long stopAtBytes, AtomicBoolean cancelled) {
            this.classInfos = classInfos;
            this.alreadyVisited = alreadyVisited;
            this.stopAtBytes = stopAtBytes;
            this.cancelled = cancelled;
        }
    }

    private static final class ArrayMemoryLayout {
        private static volatile Map<Class<?>, ArrayMemoryLayout> arrayMemoryLayouts = null;
        final int baseOffset;
        final int indexScale;

        private static Map<Class<?>, ArrayMemoryLayout> getArrayMemoryLayouts() {
            Map<Class<?>, ArrayMemoryLayout> localArrayMemoryLayouts = arrayMemoryLayouts;
            if (localArrayMemoryLayouts == null) {
                localArrayMemoryLayouts = new IdentityHashMap();
                localArrayMemoryLayouts.put(Boolean.TYPE, new ArrayMemoryLayout(Boolean.TYPE));
                localArrayMemoryLayouts.put(Byte.TYPE, new ArrayMemoryLayout(Byte.TYPE));
                localArrayMemoryLayouts.put(Short.TYPE, new ArrayMemoryLayout(Short.TYPE));
                localArrayMemoryLayouts.put(Character.TYPE, new ArrayMemoryLayout(Character.TYPE));
                localArrayMemoryLayouts.put(Integer.TYPE, new ArrayMemoryLayout(Integer.TYPE));
                localArrayMemoryLayouts.put(Float.TYPE, new ArrayMemoryLayout(Float.TYPE));
                localArrayMemoryLayouts.put(Long.TYPE, new ArrayMemoryLayout(Long.TYPE));
                localArrayMemoryLayouts.put(Double.TYPE, new ArrayMemoryLayout(Double.TYPE));
                localArrayMemoryLayouts.put(Object.class, new ArrayMemoryLayout(Object.class));
                arrayMemoryLayouts = localArrayMemoryLayouts;
            }
            return localArrayMemoryLayouts;
        }

        ArrayMemoryLayout(Class<?> componentType) {
            this.baseOffset = EngineAccessor.RUNTIME.getArrayBaseOffset(componentType);
            this.indexScale = EngineAccessor.RUNTIME.getArrayIndexScale(componentType);
        }
    }

    private static enum ForcedStop {
        NONE,
        STOPATBYTES,
        CANCELLATION;

    }
}

