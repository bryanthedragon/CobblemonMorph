
package com.oracle.truffle.api.instrumentation;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerAsserts;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.Truffle;
import com.oracle.truffle.api.instrumentation.AllocationEvent;
import com.oracle.truffle.api.instrumentation.AllocationListener;
import com.oracle.truffle.api.instrumentation.InstrumentAccessor;
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
    private final List<Consumer<Boolean>> activeListeners = new CopyOnWriteArrayList<Consumer<Boolean>>();
    private final ThreadLocal<LinkedList<Reference<Object>>> valueCheck;
    @CompilerDirectives.CompilationFinal
    private volatile Assumption listenersNotChangedAssumption = Truffle.getRuntime().createAssumption();
    @CompilerDirectives.CompilationFinal(dimensions=1)
    private volatile AllocationListener[] listeners = null;

    AllocationReporter(LanguageInfo language) {
        this.language = language;
        boolean assertions = false;
        if (!$assertionsDisabled) {
            assertions = true;
            if (!true) {
                throw new AssertionError();
            }
        }
        this.valueCheck = assertions ? new ThreadLocal() : null;
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

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void addListener(AllocationListener l) {
        boolean hadListeners;
        CompilerAsserts.neverPartOfCompilation();
        AllocationReporter allocationReporter = this;
        synchronized (allocationReporter) {
            if (this.listeners == null) {
                this.listeners = new AllocationListener[]{l};
                hadListeners = false;
            } else {
                int n = this.listeners.length;
                AllocationListener[] newListeners = Arrays.copyOf(this.listeners, n + 1);
                newListeners[n] = l;
                this.listeners = newListeners;
                hadListeners = true;
            }
            Assumption assumption = this.listenersNotChangedAssumption;
            this.listenersNotChangedAssumption = Truffle.getRuntime().createAssumption();
            assumption.invalidate();
        }
        if (!hadListeners) {
            for (Consumer consumer : this.activeListeners) {
                consumer.accept(true);
            }
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    void removeListener(AllocationListener l) {
        CompilerAsserts.neverPartOfCompilation();
        boolean hasListeners = true;
        AllocationReporter allocationReporter = this;
        synchronized (allocationReporter) {
            block9: {
                int n;
                block8: {
                    n = this.listeners.length;
                    if (n != 1) break block8;
                    if (this.listeners[0] != l) break block9;
                    this.listeners = null;
                    hasListeners = false;
                    break block9;
                }
                for (int i = 0; i < n; ++i) {
                    if (this.listeners[i] != l) continue;
                    if (i == n - 1) {
                        this.listeners = Arrays.copyOf(this.listeners, i);
                        break;
                    }
                    if (i == 0) {
                        this.listeners = Arrays.copyOfRange(this.listeners, 1, n);
                        break;
                    }
                    AllocationListener[] newListeners = new AllocationListener[n - 1];
                    System.arraycopy(this.listeners, 0, newListeners, 0, i);
                    System.arraycopy(this.listeners, i + 1, newListeners, i, n - i - 1);
                    this.listeners = newListeners;
                    break;
                }
            }
            Assumption assumption = this.listenersNotChangedAssumption;
            this.listenersNotChangedAssumption = Truffle.getRuntime().createAssumption();
            assumption.invalidate();
        }
        if (!hasListeners) {
            for (Consumer consumer : this.activeListeners) {
                consumer.accept(false);
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
        AllocationReporter.enterSizeCheck(valueToReallocate, oldSize, newSizeEstimate);
        if (valueToReallocate != null) {
            AllocationReporter.allocateValueCheck(valueToReallocate);
        }
        this.setValueCheck(valueToReallocate);
    }

    @ExplodeLoop
    private void notifyAllocateOrReallocate(Object value2, long oldSize, long newSizeEstimate) {
        AllocationListener[] ls;
        CompilerAsserts.partialEvaluationConstant(this);
        if (!this.listenersNotChangedAssumption.isValid()) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
        }
        if ((ls = this.listeners) != null) {
            AllocationEvent event = new AllocationEvent(this.language, value2, oldSize, newSizeEstimate);
            for (AllocationListener l : ls) {
                l.onEnter(event);
            }
        }
    }

    public void onReturnValue(Object value2, long oldSize, long newSize) {
        if (this.valueCheck != null) {
            this.onReturnValueCheck(value2, oldSize, newSize);
        }
        this.notifyAllocated(value2, oldSize, newSize);
    }

    @CompilerDirectives.TruffleBoundary
    private void onReturnValueCheck(Object value2, long oldSize, long newSize) {
        AllocationReporter.allocateValueCheck(value2);
        this.allocatedCheck(value2, oldSize, newSize);
    }

    @ExplodeLoop
    private void notifyAllocated(Object value2, long oldSize, long newSize) {
        AllocationListener[] ls;
        CompilerAsserts.partialEvaluationConstant(this);
        if (!this.listenersNotChangedAssumption.isValid()) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
        }
        if ((ls = this.listeners) != null) {
            AllocationEvent event = new AllocationEvent(this.language, value2, oldSize, newSize);
            for (AllocationListener l : ls) {
                l.onReturnValue(event);
            }
        }
    }

    private static void enterSizeCheck(Object valueToReallocate, long oldSize, long newSizeEstimate) {
        CompilerAsserts.neverPartOfCompilation();
        assert (newSizeEstimate == Long.MIN_VALUE || newSizeEstimate > 0L) : "Wrong new size estimate = " + newSizeEstimate;
        assert (valueToReallocate != null || oldSize == 0L) : "Old size must be 0 for new allocations. Was: " + oldSize;
        assert (valueToReallocate == null || oldSize > 0L || oldSize == Long.MIN_VALUE) : "Old size of a re-allocated value must be positive or unknown. Was: " + oldSize;
    }

    private boolean setValueCheck(Object value2) {
        CompilerAsserts.neverPartOfCompilation();
        LinkedList<Reference<Object>> list = this.valueCheck.get();
        if (list == null) {
            list = new LinkedList();
            this.valueCheck.set(list);
        }
        list.add(new WeakReference<Object>(value2));
        return true;
    }

    private static void allocateValueCheck(Object value2) {
        CompilerAsserts.neverPartOfCompilation();
        if (value2 == null) {
            throw new NullPointerException("No allocated value.");
        }
        if (value2 instanceof String) {
            return;
        }
        if (value2 instanceof Boolean || value2 instanceof Byte || value2 instanceof Character || value2 instanceof Short || value2 instanceof Integer || value2 instanceof Long || value2 instanceof Float || value2 instanceof Double) {
            return;
        }
        boolean isTO = InstrumentAccessor.ACCESSOR.isTruffleObject(value2);
        assert (isTO) : "Wrong value class, TruffleObject is required. Was: " + value2.getClass().getName();
    }

    private void allocatedCheck(Object value2, long oldSize, long newSize) {
        CompilerAsserts.neverPartOfCompilation();
        assert (value2 != null) : "Allocated value must not be null.";
        LinkedList<Reference<Object>> list = this.valueCheck.get();
        assert (list != null && !list.isEmpty()) : "onEnter() was not called";
        Object orig = list.removeLast().get();
        assert (orig == null || orig == value2) : "A different reallocated value. Was: " + orig + " now is: " + value2;
        assert (orig == null && oldSize == 0L || orig != null) : "Old size must be 0 for new allocations. Was: " + oldSize;
        assert (orig != null && (oldSize > 0L || oldSize == Long.MIN_VALUE) || orig == null) : "Old size of a re-allocated value must be positive or unknown. Was: " + oldSize;
        assert (newSize == Long.MIN_VALUE || newSize > 0L) : "New value size must be positive or unknown. Was: " + newSize;
    }
}

