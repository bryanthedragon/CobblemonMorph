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
import org.graalvm.polyglot.Source;
import org.graalvm.polyglot.SourceSection;
import org.graalvm.polyglot.Value;

final class ObjectSizeCalculator {
   private static volatile int staticObjectAlignment = -1;
   private boolean cachedClassInfosInUse;
   private Map<Class<?>, ObjectSizeCalculator.ClassInfo> cachedClassInfos;
   private int alreadyVisitedInitialCapacity = 16384;

   private static int getObjectAlignment() {
      int localObjectAlignment = staticObjectAlignment;
      if (localObjectAlignment < 0) {
         localObjectAlignment = EngineAccessor.RUNTIME.getObjectAlignment();

         assert localObjectAlignment > -1;

         staticObjectAlignment = localObjectAlignment;
      }

      return localObjectAlignment;
   }

   private static ObjectSizeCalculator.ForcedStop enqueueOrStop(ObjectSizeCalculator.CalculationState calculationState, Object obj) {
      ObjectSizeCalculator.ClassInfo classInfo = canProceed(calculationState.classInfos, obj);
      if (classInfo != ObjectSizeCalculator.StopClassInfo.INSTANCE && calculationState.alreadyVisited.add(obj)) {
         classInfo.increaseByBaseSize(calculationState, obj);
         if (calculationState.dataSize > calculationState.stopAtBytes) {
            return ObjectSizeCalculator.ForcedStop.STOPATBYTES;
         }

         if (calculationState.cancelled.get()) {
            return ObjectSizeCalculator.ForcedStop.CANCELLATION;
         }

         enqueue(calculationState.pending, obj);
      }

      return ObjectSizeCalculator.ForcedStop.NONE;
   }

   @CompilerDirectives.TruffleBoundary
   long calculateObjectSize(final Object obj, long stopAtBytes, AtomicBoolean cancelled) {
      if (!TruffleOptions.AOT && !(Truffle.getRuntime() instanceof DefaultTruffleRuntime)) {
         boolean usingCachedClassInfos = false;
         ObjectSizeCalculator.CalculationState calculationState;
         synchronized (this) {
            Map<Class<?>, ObjectSizeCalculator.ClassInfo> classInfosToUse;
            if (!this.cachedClassInfosInUse) {
               if (this.cachedClassInfos == null) {
                  this.cachedClassInfos = new IdentityHashMap<>();
               }

               classInfosToUse = this.cachedClassInfos;
               this.cachedClassInfosInUse = true;
               usingCachedClassInfos = true;
            } else {
               classInfosToUse = new IdentityHashMap<>();
            }

            calculationState = new ObjectSizeCalculator.CalculationState(
               classInfosToUse, new ObjectSizeCalculator.QuickIdentitySet<>(this.alreadyVisitedInitialCapacity), stopAtBytes, cancelled
            );
         }

         try {
            if (cancelled.get()) {
               throw cancel(calculationState.dataSize);
            } else {
               ObjectSizeCalculator.ForcedStop stop = enqueueOrStop(calculationState, obj);
               Object o = calculationState.pending.pollFirst();

               while (true) {
                  if (o != null) {
                     stop = visit(calculationState, o);
                  }

                  if (calculationState.pending.isEmpty() || stop == ObjectSizeCalculator.ForcedStop.STOPATBYTES) {
                     return calculationState.dataSize;
                  }

                  if (stop == ObjectSizeCalculator.ForcedStop.CANCELLATION) {
                     throw cancel(calculationState.dataSize);
                  }

                  o = calculationState.pending.pollFirst();
               }
            }
         } finally {
            synchronized (this) {
               if (usingCachedClassInfos) {
                  this.cachedClassInfosInUse = false;
               }

               if (calculationState.alreadyVisited.getCapacity() > this.alreadyVisitedInitialCapacity) {
                  this.alreadyVisitedInitialCapacity = calculationState.alreadyVisited.getCapacity();
               }
            }
         }
      } else {
         throw new UnsupportedOperationException("Polyglot context heap size calculation is not supported on this platform.");
      }
   }

   private static CancellationException cancel(long dataSize) {
      throw new CancellationException(String.format("cancelled at %d bytes", dataSize));
   }

   private static ObjectSizeCalculator.ClassInfo getClassInfo(Map<Class<?>, ObjectSizeCalculator.ClassInfo> classInfos, Class<?> clazz) {
      return classInfos.computeIfAbsent(
         clazz,
         new Function<Class<?>, ObjectSizeCalculator.ClassInfo>() {
            public ObjectSizeCalculator.ClassInfo apply(Class<?> aClass) {
               return (ObjectSizeCalculator.ClassInfo)(clazz.isArray()
                  ? new ObjectSizeCalculator.ArrayClassInfo(aClass)
                  : new ObjectSizeCalculator.ObjectClassInfo(aClass));
            }
         }
      );
   }

   private static ObjectSizeCalculator.ForcedStop visit(ObjectSizeCalculator.CalculationState calculationState, Object obj) {
      Class<?> clazz = obj.getClass();
      return clazz == ObjectSizeCalculator.ArrayElementsVisitor.class
         ? ((ObjectSizeCalculator.ArrayElementsVisitor)obj).visit(calculationState)
         : calculationState.classInfos.get(clazz).visit(calculationState, obj);
   }

   private static void increaseByArraySize(ObjectSizeCalculator.CalculationState calculationState, ObjectSizeCalculator.ArrayMemoryLayout layout, int length) {
      increaseSize(calculationState, roundToObjectAlignment(layout.baseOffset + length * layout.indexScale, getObjectAlignment()));
   }

   private static boolean isContextHeapBoundary(Object obj) {
      if (obj == null) {
         return true;
      } else {
         assert (!(obj instanceof PolyglotImpl.VMObject) || obj instanceof PolyglotLanguageContext || obj instanceof PolyglotContextImpl)
            && !(obj instanceof PolyglotContextConfig)
            && !(obj instanceof TruffleLanguage.Provider)
            && !(obj instanceof ExecutionEventListener)
            && !(obj instanceof ClassValue)
            && !(obj instanceof ClassLoader)
            && !(obj instanceof PolyglotWrapper)
            && !(obj instanceof Value)
            && !(obj instanceof Context)
            && !(obj instanceof Engine)
            && !(obj instanceof Language)
            && !(obj instanceof Instrument)
            && !(obj instanceof Source)
            && !(obj instanceof SourceSection) : obj.getClass().getName() + " should not be reachable";

         return obj instanceof Thread
            || EngineAccessor.HOST.isHostBoundaryValue(obj)
            || obj instanceof Class
            || obj instanceof OptionValues
            || obj instanceof TruffleLanguage.ContextReference
            || obj instanceof TruffleLanguage.LanguageReference
            || obj instanceof com.oracle.truffle.api.source.Source
            || obj instanceof com.oracle.truffle.api.source.SourceSection
            || obj instanceof TruffleFile
            || obj instanceof TruffleLogger
            || obj instanceof InstrumentInfo
            || obj instanceof LanguageInfo
            || obj instanceof TruffleProcessBuilder
            || obj instanceof CallTarget
            || obj instanceof Node
            || obj instanceof NodeFactory
            || obj instanceof AllocationReporter
            || obj instanceof Assumption
            || obj instanceof TruffleLanguage
            || obj instanceof TruffleLanguage.Env
            || obj instanceof TruffleInstrument
            || obj instanceof TruffleInstrument.Env
            || obj instanceof TruffleContext
            || obj instanceof ContextLocal
            || obj instanceof ContextThreadLocal
            || obj instanceof PolyglotImpl.VMObject
            || obj instanceof PolyglotContextConfig
            || obj instanceof TruffleLanguage.Provider
            || obj instanceof ExecutionEventListener
            || obj instanceof ClassValue
            || obj instanceof ClassLoader
            || obj instanceof PolyglotWrapper
            || obj instanceof Value
            || obj instanceof Context
            || obj instanceof Engine
            || obj instanceof Language
            || obj instanceof Instrument
            || obj instanceof Source
            || obj instanceof SourceSection;
      }
   }

   private static ObjectSizeCalculator.ClassInfo canProceed(Map<Class<?>, ObjectSizeCalculator.ClassInfo> classInfos, Object obj) {
      if (obj == null) {
         return ObjectSizeCalculator.StopClassInfo.INSTANCE;
      } else {
         Class<?> clazz = obj.getClass();
         ObjectSizeCalculator.ClassInfo classInfo = classInfos.get(clazz);
         if (classInfo != null) {
            return classInfo;
         } else {
            boolean eligible = !isContextHeapBoundary(obj);
            if (eligible) {
               classInfo = getClassInfo(classInfos, clazz);
            } else {
               classInfo = ObjectSizeCalculator.StopClassInfo.INSTANCE;
               classInfos.put(clazz, ObjectSizeCalculator.StopClassInfo.INSTANCE);
            }

            return classInfo;
         }
      }
   }

   private static void enqueue(Deque<Object> pending, Object obj) {
      pending.addLast(obj);
   }

   private static void increaseSize(ObjectSizeCalculator.CalculationState calculationState, long objectSize) {
      calculationState.dataSize += objectSize;
   }

   private static long roundToObjectAlignment(long x, int objectAlignment) {
      return (x + objectAlignment - 1L) / objectAlignment * objectAlignment;
   }

   private static final class ArrayClassInfo implements ObjectSizeCalculator.ClassInfo {
      private final ObjectSizeCalculator.ArrayMemoryLayout arrayMemoryLayout;
      private final boolean isPrimitive;

      ArrayClassInfo(Class<?> clazz) {
         Class<?> componentType = clazz.getComponentType();
         if (componentType.isPrimitive()) {
            this.arrayMemoryLayout = ObjectSizeCalculator.ArrayMemoryLayout.getArrayMemoryLayouts().get(componentType);
            this.isPrimitive = true;
         } else {
            this.arrayMemoryLayout = ObjectSizeCalculator.ArrayMemoryLayout.getArrayMemoryLayouts().get(Object.class);
            this.isPrimitive = false;
         }
      }

      @Override
      public void increaseByBaseSize(ObjectSizeCalculator.CalculationState calculationState, Object obj) {
         int length = Array.getLength(obj);
         ObjectSizeCalculator.increaseByArraySize(calculationState, this.arrayMemoryLayout, length);
      }

      @Override
      public ObjectSizeCalculator.ForcedStop visit(ObjectSizeCalculator.CalculationState calculationState, Object obj) {
         if (!this.isPrimitive) {
            int length = Array.getLength(obj);
            switch (length) {
               case 0:
                  break;
               case 1:
                  Object o = Array.get(obj, 0);
                  return ObjectSizeCalculator.enqueueOrStop(calculationState, o);
               default:
                  ObjectSizeCalculator.enqueue(
                     calculationState.pending, new ObjectSizeCalculator.ArrayElementsVisitor((Object[])obj, calculationState.alreadyVisited)
                  );
            }
         }

         return ObjectSizeCalculator.ForcedStop.NONE;
      }
   }

   private static final class ArrayElementsVisitor {
      private final Object[] array;
      private final ObjectSizeCalculator.QuickIdentitySet<Object> alreadyVisited;

      ArrayElementsVisitor(final Object[] array, ObjectSizeCalculator.QuickIdentitySet<Object> alreadyVisited) {
         this.array = array;
         this.alreadyVisited = alreadyVisited;
      }

      public ObjectSizeCalculator.ForcedStop visit(ObjectSizeCalculator.CalculationState calculationState) {
         for (Object elem : this.array) {
            ObjectSizeCalculator.ClassInfo classInfo = ObjectSizeCalculator.canProceed(calculationState.classInfos, elem);
            if (classInfo != ObjectSizeCalculator.StopClassInfo.INSTANCE && this.alreadyVisited.add(elem)) {
               classInfo.increaseByBaseSize(calculationState, elem);
               if (calculationState.dataSize > calculationState.stopAtBytes) {
                  return ObjectSizeCalculator.ForcedStop.STOPATBYTES;
               }

               if (calculationState.cancelled.get()) {
                  return ObjectSizeCalculator.ForcedStop.CANCELLATION;
               }

               ObjectSizeCalculator.ForcedStop stop = ObjectSizeCalculator.visit(calculationState, elem);
               if (stop != ObjectSizeCalculator.ForcedStop.NONE) {
                  return stop;
               }
            }
         }

         return ObjectSizeCalculator.ForcedStop.NONE;
      }
   }

   private static final class ArrayMemoryLayout {
      private static volatile Map<Class<?>, ObjectSizeCalculator.ArrayMemoryLayout> arrayMemoryLayouts = null;
      final int baseOffset;
      final int indexScale;

      private static Map<Class<?>, ObjectSizeCalculator.ArrayMemoryLayout> getArrayMemoryLayouts() {
         Map<Class<?>, ObjectSizeCalculator.ArrayMemoryLayout> localArrayMemoryLayouts = arrayMemoryLayouts;
         if (localArrayMemoryLayouts == null) {
            localArrayMemoryLayouts = new IdentityHashMap<>();
            localArrayMemoryLayouts.put(boolean.class, new ObjectSizeCalculator.ArrayMemoryLayout(boolean.class));
            localArrayMemoryLayouts.put(byte.class, new ObjectSizeCalculator.ArrayMemoryLayout(byte.class));
            localArrayMemoryLayouts.put(short.class, new ObjectSizeCalculator.ArrayMemoryLayout(short.class));
            localArrayMemoryLayouts.put(char.class, new ObjectSizeCalculator.ArrayMemoryLayout(char.class));
            localArrayMemoryLayouts.put(int.class, new ObjectSizeCalculator.ArrayMemoryLayout(int.class));
            localArrayMemoryLayouts.put(float.class, new ObjectSizeCalculator.ArrayMemoryLayout(float.class));
            localArrayMemoryLayouts.put(long.class, new ObjectSizeCalculator.ArrayMemoryLayout(long.class));
            localArrayMemoryLayouts.put(double.class, new ObjectSizeCalculator.ArrayMemoryLayout(double.class));
            localArrayMemoryLayouts.put(Object.class, new ObjectSizeCalculator.ArrayMemoryLayout(Object.class));
            arrayMemoryLayouts = localArrayMemoryLayouts;
         }

         return localArrayMemoryLayouts;
      }

      ArrayMemoryLayout(Class<?> componentType) {
         this.baseOffset = EngineAccessor.RUNTIME.getArrayBaseOffset(componentType);
         this.indexScale = EngineAccessor.RUNTIME.getArrayIndexScale(componentType);
      }
   }

   private static final class CalculationState {
      private final Map<Class<?>, ObjectSizeCalculator.ClassInfo> classInfos;
      private final ObjectSizeCalculator.QuickIdentitySet<Object> alreadyVisited;
      private final Deque<Object> pending = new ArrayDeque<>(16384);
      private final long stopAtBytes;
      private final AtomicBoolean cancelled;
      private long dataSize;

      CalculationState(
         Map<Class<?>, ObjectSizeCalculator.ClassInfo> classInfos,
         ObjectSizeCalculator.QuickIdentitySet<Object> alreadyVisited,
         long stopAtBytes,
         AtomicBoolean cancelled
      ) {
         this.classInfos = classInfos;
         this.alreadyVisited = alreadyVisited;
         this.stopAtBytes = stopAtBytes;
         this.cancelled = cancelled;
      }
   }

   private interface ClassInfo {
      ObjectSizeCalculator.ForcedStop visit(ObjectSizeCalculator.CalculationState calculationState, Object obj);

      void increaseByBaseSize(ObjectSizeCalculator.CalculationState calculationState, Object obj);
   }

   private static enum ForcedStop {
      NONE,
      STOPATBYTES,
      CANCELLATION;
   }

   private static final class ObjectClassInfo implements ObjectSizeCalculator.ClassInfo {
      private final long objectSize;
      private final Object[] resolvedJavaFields;
      private final boolean isReference;

      ObjectClassInfo(Class<?> clazz) {
         this.resolvedJavaFields = EngineAccessor.RUNTIME.getResolvedFields(clazz, false, true);
         this.objectSize = EngineAccessor.RUNTIME.getBaseInstanceSize(clazz);
         this.isReference = Reference.class.isAssignableFrom(clazz);
      }

      @Override
      public void increaseByBaseSize(ObjectSizeCalculator.CalculationState calculationState, Object obj) {
         ObjectSizeCalculator.increaseSize(calculationState, this.objectSize);
      }

      @Override
      public ObjectSizeCalculator.ForcedStop visit(ObjectSizeCalculator.CalculationState calculationState, Object obj) {
         if (this.isReference) {
            Object nextObj = null;

            try {
               nextObj = ((Reference)obj).get();
            } catch (Exception var9) {
            }

            ObjectSizeCalculator.ForcedStop stop = ObjectSizeCalculator.enqueueOrStop(calculationState, nextObj);
            if (stop != ObjectSizeCalculator.ForcedStop.NONE) {
               return stop;
            }
         }

         for (Object f : this.resolvedJavaFields) {
            Object nextObj = EngineAccessor.RUNTIME.getFieldValue(f, obj);
            ObjectSizeCalculator.ForcedStop stop = ObjectSizeCalculator.enqueueOrStop(calculationState, nextObj);
            if (stop != ObjectSizeCalculator.ForcedStop.NONE) {
               return stop;
            }
         }

         return ObjectSizeCalculator.ForcedStop.NONE;
      }
   }

   private static final class QuickIdentitySet<T> implements Set<T> {
      private Object[] data;
      private int size;
      private int capacity;
      private int growLimit;

      QuickIdentitySet(int initialCapacity) {
         if (initialCapacity < 1) {
            throw new IllegalArgumentException();
         } else {
            this.capacity = initialCapacity;
            this.data = new Object[this.capacity];
            this.updateGrowLimit();
         }
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
         } else {
            int i = System.identityHashCode(t) % this.capacity;
            if (i < 0) {
               i += this.capacity;
            }

            while (this.data[i] != null && this.data[i] != t) {
               if (++i == this.capacity) {
                  i = 0;
               }
            }

            if (this.data[i] == null) {
               this.data[i] = t;
               this.size++;
               if (this.size > this.growLimit) {
                  this.grow();
               }

               return true;
            } else {
               return false;
            }
         }
      }

      private void addFast(Object t) {
         int i = System.identityHashCode(t) % this.capacity;
         if (i < 0) {
            i += this.capacity;
         }

         while (this.data[i] != null) {
            if (++i == this.capacity) {
               i = 0;
            }
         }

         this.data[i] = t;
      }

      private void grow() {
         this.capacity = Math.multiplyExact(2, this.capacity);
         Object[] oldData = this.data;
         this.data = new Object[this.capacity];

         for (Object obj : oldData) {
            if (obj != null) {
               this.addFast(obj);
            }
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

   private static final class StopClassInfo implements ObjectSizeCalculator.ClassInfo {
      static final ObjectSizeCalculator.StopClassInfo INSTANCE = new ObjectSizeCalculator.StopClassInfo();

      StopClassInfo() {
      }

      @Override
      public ObjectSizeCalculator.ForcedStop visit(ObjectSizeCalculator.CalculationState calculationState, Object obj) {
         return ObjectSizeCalculator.ForcedStop.NONE;
      }

      @Override
      public void increaseByBaseSize(ObjectSizeCalculator.CalculationState calculationState, Object obj) {
      }
   }
}
