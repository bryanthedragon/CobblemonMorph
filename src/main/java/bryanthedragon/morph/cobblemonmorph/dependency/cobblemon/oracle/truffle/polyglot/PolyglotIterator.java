
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.StopIterationException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.api.utilities.TriState;
import com.oracle.truffle.polyglot.HostToGuestRootNode;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotExecuteNode;
import com.oracle.truffle.polyglot.PolyglotExecuteNodeGen;
import com.oracle.truffle.polyglot.PolyglotInteropErrors;
import com.oracle.truffle.polyglot.PolyglotIteratorAndFunction;
import com.oracle.truffle.polyglot.PolyglotIteratorFactory;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotLanguageInstance;
import com.oracle.truffle.polyglot.PolyglotToHostNode;
import com.oracle.truffle.polyglot.PolyglotWrapper;
import java.lang.reflect.Type;
import java.util.ConcurrentModificationException;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Objects;

class PolyglotIterator<T>
implements Iterator<T>,
PolyglotWrapper {
    final Object guestObject;
    final PolyglotLanguageContext languageContext;
    final Cache cache;
    private TriState lastHasNext;
    private boolean concurrentlyModified;

    PolyglotIterator(Class<T> elementClass, Type elementType, Object array, PolyglotLanguageContext languageContext) {
        this.guestObject = array;
        this.languageContext = languageContext;
        this.cache = Cache.lookup(languageContext, array.getClass(), elementClass, elementType);
        this.lastHasNext = TriState.UNDEFINED;
    }

    @Override
    public Object getGuestObject() {
        return this.guestObject;
    }

    @Override
    public PolyglotLanguageContext getLanguageContext() {
        return this.languageContext;
    }

    @Override
    public PolyglotContextImpl getContext() {
        return this.languageContext.context;
    }

    @Override
    public boolean hasNext() {
        if (this.lastHasNext == TriState.UNDEFINED) {
            this.lastHasNext = TriState.valueOf((Boolean)this.cache.hasNext.call(this.languageContext, this.guestObject));
        }
        return this.lastHasNext == TriState.TRUE;
    }

    @Override
    public T next() {
        if (this.concurrentlyModified) {
            throw new ConcurrentModificationException();
        }
        try {
            TriState prevHasNext = this.lastHasNext;
            if (this.lastHasNext == TriState.TRUE) {
                this.lastHasNext = TriState.UNDEFINED;
            }
            return (T)this.cache.next.call(new Object[]{this.languageContext, this.guestObject, prevHasNext});
        }
        catch (NoSuchElementException noSuchElementException) {
            this.lastHasNext = TriState.FALSE;
            throw noSuchElementException;
        }
        catch (ConcurrentModificationException concurrentModificationException) {
            this.concurrentlyModified = true;
            throw concurrentModificationException;
        }
    }

    public String toString() {
        return PolyglotWrapper.toString(this);
    }

    public int hashCode() {
        return PolyglotWrapper.hashCode(this.languageContext, this.guestObject);
    }

    public boolean equals(Object o) {
        if (o instanceof PolyglotIterator) {
            return PolyglotWrapper.equals(this.languageContext, this.guestObject, ((PolyglotIterator)o).guestObject);
        }
        return false;
    }

    @CompilerDirectives.TruffleBoundary
    static <T> PolyglotIterator<T> create(PolyglotLanguageContext languageContext, Object iterable, boolean implementFunction, Class<T> elementClass, Type elementType) {
        if (implementFunction) {
            return new PolyglotIteratorAndFunction<T>(elementClass, elementType, iterable, languageContext);
        }
        return new PolyglotIterator<T>(elementClass, elementType, iterable, languageContext);
    }

    static final class Cache {
        final PolyglotLanguageInstance languageInstance;
        final Class<?> receiverClass;
        final Class<?> valueClass;
        final Type valueType;
        final CallTarget hasNext;
        final CallTarget next;
        final CallTarget apply;

        private Cache(PolyglotLanguageInstance languageInstance, Class<?> receiverClass, Class<?> valueClass, Type valueType) {
            this.languageInstance = languageInstance;
            this.receiverClass = receiverClass;
            this.valueClass = valueClass;
            this.valueType = valueType;
            this.hasNext = PolyglotIteratorFactory.CacheFactory.HasNextNodeGen.create(this).getCallTarget();
            this.next = PolyglotIteratorFactory.CacheFactory.NextNodeGen.create(this).getCallTarget();
            this.apply = new Apply(this).getCallTarget();
        }

        static Cache lookup(PolyglotLanguageContext languageContext, Class<?> receiverClass, Class<?> valueClass, Type valueType) {
            Key cacheKey = new Key(receiverClass, valueClass, valueType);
            Cache cache = HostToGuestRootNode.lookupHostCodeCache(languageContext, cacheKey, Cache.class);
            if (cache == null) {
                cache = HostToGuestRootNode.installHostCodeCache(languageContext, cacheKey, new Cache(languageContext.getLanguageInstance(), receiverClass, valueClass, valueType), Cache.class);
            }
            assert (cache.receiverClass == receiverClass);
            assert (cache.valueClass == valueClass);
            assert (cache.valueType == valueType);
            return cache;
        }

        private static class Apply
        extends PolyglotIteratorNode {
            @Node.Child
            private PolyglotExecuteNode apply = PolyglotExecuteNodeGen.create();

            Apply(Cache cache) {
                super(cache);
            }

            @Override
            protected String getOperationName() {
                return "apply";
            }

            @Override
            protected Object executeImpl(PolyglotLanguageContext languageContext, Object receiver, Object[] args) {
                return this.apply.execute(languageContext, receiver, args[2]);
            }
        }

        static abstract class NextNode
        extends PolyglotIteratorNode {
            NextNode(Cache cache) {
                super(cache);
            }

            @Override
            protected String getOperationName() {
                return "next";
            }

            @Specialization(limit="LIMIT")
            Object doCached(PolyglotLanguageContext languageContext, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary iterators, @Cached PolyglotToHostNode toHost, @Cached BranchProfile error, @Cached BranchProfile stop) {
                TriState lastHasNext = (TriState)((Object)args[2]);
                try {
                    Object next = iterators.getIteratorNextElement(receiver);
                    if (lastHasNext == TriState.FALSE) {
                        error.enter();
                        throw PolyglotInteropErrors.iteratorConcurrentlyModified(languageContext, receiver, this.cache.valueType);
                    }
                    return toHost.execute(languageContext, next, this.cache.valueClass, this.cache.valueType);
                }
                catch (StopIterationException e) {
                    stop.enter();
                    if (lastHasNext == TriState.TRUE) {
                        throw PolyglotInteropErrors.iteratorConcurrentlyModified(languageContext, receiver, this.cache.valueType);
                    }
                    throw PolyglotInteropErrors.stopIteration(languageContext, receiver, this.cache.valueType);
                }
                catch (UnsupportedMessageException e) {
                    error.enter();
                    throw PolyglotInteropErrors.iteratorElementUnreadable(languageContext, receiver, this.cache.valueType);
                }
            }
        }

        static abstract class HasNextNode
        extends PolyglotIteratorNode {
            HasNextNode(Cache cache) {
                super(cache);
            }

            @Override
            protected String getOperationName() {
                return "hasNext";
            }

            @Specialization(limit="LIMIT")
            Object doCached(PolyglotLanguageContext languageContext, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary iterators, @Cached BranchProfile error) {
                try {
                    return iterators.hasIteratorNextElement(receiver);
                }
                catch (UnsupportedMessageException e) {
                    error.enter();
                    throw PolyglotInteropErrors.iteratorUnsupported(languageContext, receiver, this.cache.valueType, "hasNext");
                }
            }
        }

        static abstract class PolyglotIteratorNode
        extends HostToGuestRootNode {
            static final int LIMIT = 5;
            final Cache cache;

            PolyglotIteratorNode(Cache cache) {
                super(cache.languageInstance);
                this.cache = cache;
            }

            protected Class<? extends TruffleObject> getReceiverType() {
                return this.cache.receiverClass;
            }

            @Override
            public final String getName() {
                return "PolyglotIterator<" + this.cache.receiverClass + ", " + this.cache.valueType + ">." + this.getOperationName();
            }

            protected abstract String getOperationName();
        }

        private static final class Key {
            private final Class<?> receiverClass;
            private final Class<?> valueClass;
            private final Type valueType;

            Key(Class<?> receiverClass, Class<?> valueClass, Type valueType) {
                this.receiverClass = Objects.requireNonNull(receiverClass);
                this.valueClass = Objects.requireNonNull(valueClass);
                this.valueType = valueType;
            }

            public int hashCode() {
                int res = this.receiverClass.hashCode();
                res = res * 31 + this.valueClass.hashCode();
                res = res * 31 + (this.valueType == null ? 0 : this.valueType.hashCode());
                return res;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || this.getClass() != obj.getClass()) {
                    return false;
                }
                Key other = (Key)obj;
                return this.receiverClass == other.receiverClass && this.valueClass == other.valueClass && Objects.equals(this.valueType, other.valueType);
            }
        }
    }
}

