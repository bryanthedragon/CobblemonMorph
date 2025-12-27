package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.LanguageInfo;
import java.lang.ref.Reference;
import java.lang.ref.WeakReference;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

public final class AllocationReporter {
   public static final long SIZE_UNKNOWN = Long.MIN_VALUE;
   final LanguageInfo language;
   private final List<Consumer<Boolean>> activeListeners = new CopyOnWriteArrayList<>();
   private final ThreadLocal<LinkedList<Reference<Object>>> valueCheck;
   @CompilerDirectives.CompilationFinal
   private volatile Assumption listenersNotChangedAssumption = Truffle.getRuntime().createAssumption();
   @CompilerDirectives.CompilationFinal(dimensions = 1)
   private volatile AllocationListener[] listeners = null;

   AllocationReporter(LanguageInfo language) {
      this.language = language;
      boolean assertions = false;
      if (!$assertionsDisabled) {
         assertions = true;
         if (false) {
            throw new AssertionError();
         }
      }

      this.valueCheck = assertions ? new ThreadLocal<>() : null;
   }

   public void addActiveListener(Consumer<Boolean> listener) {
      this.activeListeners.add(listener);
   }

   public void removeActiveListener(Consumer<Boolean> listener) {
      this.activeListeners.remove(listener);
   }

   public boolean isActive() {
      if (!this.listenersNotChangedAssumption.isValid()) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
      }

      return this.listeners != null;
   }

   void addListener(AllocationListener l) {
      CompilerAsserts.neverPartOfCompilation();
      boolean hadListeners;
      synchronized (this) {
         if (this.listeners == null) {
            this.listeners = new AllocationListener[]{l};
            hadListeners = false;
         } else {
            int index = this.listeners.length;
            AllocationListener[] newListeners = Arrays.copyOf(this.listeners, index + 1);
            newListeners[index] = l;
            this.listeners = newListeners;
            hadListeners = true;
         }

         Assumption assumption = this.listenersNotChangedAssumption;
         this.listenersNotChangedAssumption = Truffle.getRuntime().createAssumption();
         assumption.invalidate();
      }

      if (!hadListeners) {
         for (Consumer<Boolean> listener : this.activeListeners) {
            listener.accept(true);
         }
      }
   }

   void removeListener(AllocationListener l) {
      CompilerAsserts.neverPartOfCompilation();
      boolean hasListeners = true;
      synchronized (this) {
         int len = this.listeners.length;
         if (len == 1) {
            if (this.listeners[0] == l) {
               this.listeners = null;
               hasListeners = false;
            }
         } else {
            for (int i = 0; i < len; i++) {
               if (this.listeners[i] == l) {
                  if (i == len - 1) {
                     this.listeners = Arrays.copyOf(this.listeners, i);
                  } else if (i == 0) {
                     this.listeners = Arrays.copyOfRange(this.listeners, 1, len);
                  } else {
                     AllocationListener[] newListeners = new AllocationListener[len - 1];
                     System.arraycopy(this.listeners, 0, newListeners, 0, i);
                     System.arraycopy(this.listeners, i + 1, newListeners, i, len - i - 1);
                     this.listeners = newListeners;
                  }
                  break;
               }
            }
         }

         Assumption assumption = this.listenersNotChangedAssumption;
         this.listenersNotChangedAssumption = Truffle.getRuntime().createAssumption();
         assumption.invalidate();
      }

      if (!hasListeners) {
         for (Consumer<Boolean> listener : this.activeListeners) {
            listener.accept(false);
         }
      }
   }

   public void onEnter(Object valueToReallocate, long oldSize, long newSizeEstimate) {
      if (this.valueCheck != null) {
         this.onEnterCheck(valueToReallocate, oldSize, newSizeEstimate);
      }

      this.notifyAllocateOrReallocate(valueToReallocate, oldSize, newSizeEstimate);
   }

   @CompilerDirectives.TruffleBoundary
   private void onEnterCheck(Object valueToReallocate, long oldSize, long newSizeEstimate) {
      enterSizeCheck(valueToReallocate, oldSize, newSizeEstimate);
      if (valueToReallocate != null) {
         allocateValueCheck(valueToReallocate);
      }

      this.setValueCheck(valueToReallocate);
   }

   @ExplodeLoop
   private void notifyAllocateOrReallocate(Object value, long oldSize, long newSizeEstimate) {
      CompilerAsserts.partialEvaluationConstant(this);
      if (!this.listenersNotChangedAssumption.isValid()) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
      }

      AllocationListener[] ls = this.listeners;
      if (ls != null) {
         AllocationEvent event = new AllocationEvent(this.language, value, oldSize, newSizeEstimate);

         for (AllocationListener l : ls) {
            l.onEnter(event);
         }
      }
   }

   public void onReturnValue(Object value, long oldSize, long newSize) {
      if (this.valueCheck != null) {
         this.onReturnValueCheck(value, oldSize, newSize);
      }

      this.notifyAllocated(value, oldSize, newSize);
   }

   @CompilerDirectives.TruffleBoundary
   private void onReturnValueCheck(Object value, long oldSize, long newSize) {
      allocateValueCheck(value);
      this.allocatedCheck(value, oldSize, newSize);
   }

   @ExplodeLoop
   private void notifyAllocated(Object value, long oldSize, long newSize) {
      CompilerAsserts.partialEvaluationConstant(this);
      if (!this.listenersNotChangedAssumption.isValid()) {
         CompilerDirectives.transferToInterpreterAndInvalidate();
      }

      AllocationListener[] ls = this.listeners;
      if (ls != null) {
         AllocationEvent event = new AllocationEvent(this.language, value, oldSize, newSize);

         for (AllocationListener l : ls) {
            l.onReturnValue(event);
         }
      }
   }

   private static void enterSizeCheck(Object valueToReallocate, long oldSize, long newSizeEstimate) {
      CompilerAsserts.neverPartOfCompilation();

      assert newSizeEstimate == Long.MIN_VALUE || newSizeEstimate > 0L : "Wrong new size estimate = " + newSizeEstimate;

      assert valueToReallocate != null || oldSize == 0L : "Old size must be 0 for new allocations. Was: " + oldSize;

      assert valueToReallocate == null || oldSize > 0L || oldSize == Long.MIN_VALUE : "Old size of a re-allocated value must be positive or unknown. Was: "
         + oldSize;
   }

   private boolean setValueCheck(Object value) {
      CompilerAsserts.neverPartOfCompilation();
      LinkedList<Reference<Object>> list = this.valueCheck.get();
      if (list == null) {
         list = new LinkedList<>();
         this.valueCheck.set(list);
      }

      list.add(new WeakReference<>(value));
      return true;
   }

   private static void allocateValueCheck(Object value) {
      CompilerAsserts.neverPartOfCompilation();
      if (value == null) {
         throw new NullPointerException("No allocated value.");
      } else if (!(value instanceof String)) {
         if (!(value instanceof Boolean)
            && !(value instanceof Byte)
            && !(value instanceof Character)
            && !(value instanceof Short)
            && !(value instanceof Integer)
            && !(value instanceof Long)
            && !(value instanceof Float)
            && !(value instanceof Double)) {
            boolean isTO = InstrumentAccessor.ACCESSOR.isTruffleObject(value);

            assert isTO : "Wrong value class, TruffleObject is required. Was: " + value.getClass().getName();
         }
      }
   }

   private void allocatedCheck(Object value, long oldSize, long newSize) {
      CompilerAsserts.neverPartOfCompilation();

      assert value != null : "Allocated value must not be null.";

      LinkedList<Reference<Object>> list = this.valueCheck.get();

      assert list != null && !list.isEmpty() : "onEnter() was not called";

      Object orig = list.removeLast().get();

      assert orig == null || orig == value : "A different reallocated value. Was: " + orig + " now is: " + value;

      assert orig == null && oldSize == 0L || orig != null : "Old size must be 0 for new allocations. Was: " + oldSize;

      assert orig != null && (oldSize > 0L || oldSize == Long.MIN_VALUE) || orig == null : "Old size of a re-allocated value must be positive or unknown. Was: "
         + oldSize;

      assert newSize == Long.MIN_VALUE || newSize > 0L : "New value size must be positive or unknown. Was: " + newSize;
   }
}
