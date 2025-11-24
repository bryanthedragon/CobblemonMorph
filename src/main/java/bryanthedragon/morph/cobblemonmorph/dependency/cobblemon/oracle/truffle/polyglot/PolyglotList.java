
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.TruffleSafepoint;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.CachedLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.profiles.BranchProfile;
import com.oracle.truffle.polyglot.HostToGuestRootNode;
import com.oracle.truffle.polyglot.PolyglotContextImpl;
import com.oracle.truffle.polyglot.PolyglotExecuteNode;
import com.oracle.truffle.polyglot.PolyglotExecuteNodeGen;
import com.oracle.truffle.polyglot.PolyglotInteropErrors;
import com.oracle.truffle.polyglot.PolyglotLanguageContext;
import com.oracle.truffle.polyglot.PolyglotLanguageInstance;
import com.oracle.truffle.polyglot.PolyglotListAndFunction;
import com.oracle.truffle.polyglot.PolyglotListFactory;
import com.oracle.truffle.polyglot.PolyglotToHostNode;
import com.oracle.truffle.polyglot.PolyglotWrapper;
import java.lang.reflect.Type;
import java.util.AbstractList;
import java.util.List;
import java.util.Objects;

class PolyglotList<T>
extends AbstractList<T>
implements PolyglotWrapper {
    final Object guestObject;
    final PolyglotLanguageContext languageContext;
    final Cache cache;

    PolyglotList(Class<T> elementClass, Type elementType, Object array, PolyglotLanguageContext languageContext) {
        this.guestObject = array;
        this.languageContext = languageContext;
        this.cache = Cache.lookup(languageContext, array.getClass(), elementClass, elementType);
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

    @CompilerDirectives.TruffleBoundary
    public static <T> List<T> create(PolyglotLanguageContext languageContext, Object array, boolean implementFunction, Class<T> elementClass, Type elementType) {
        if (implementFunction) {
            return new PolyglotListAndFunction<T>(elementClass, elementType, array, languageContext);
        }
        return new PolyglotList<T>(elementClass, elementType, array, languageContext);
    }

    @Override
    public T get(int index) {
        return (T)this.cache.get.call(this.languageContext, this.guestObject, index);
    }

    @Override
    public boolean add(T element) {
        return (Boolean)this.cache.add.call(this.languageContext, this.guestObject, element);
    }

    @Override
    public void add(int index, T element) {
        this.cache.addAtIndex.call(this.languageContext, this.guestObject, index, element);
    }

    @Override
    public T set(int index, T element) {
        T prev = this.get(index);
        this.cache.set.call(this.languageContext, this.guestObject, index, element);
        return prev;
    }

    @Override
    public T remove(int index) {
        T prev = this.get(index);
        this.cache.remove.call(this.languageContext, this.guestObject, index);
        return prev;
    }

    @Override
    public int size() {
        return (Integer)this.cache.size.call(this.languageContext, this.guestObject);
    }

    @Override
    public String toString() {
        return PolyglotWrapper.toString(this);
    }

    @Override
    public int hashCode() {
        return PolyglotWrapper.hashCode(this.languageContext, this.guestObject);
    }

    @Override
    public boolean equals(Object o) {
        if (o instanceof PolyglotList) {
            return PolyglotWrapper.equals(this.languageContext, this.guestObject, ((PolyglotList)o).guestObject);
        }
        return false;
    }

    static final class Cache {
        final PolyglotLanguageInstance languageInstance;
        final Class<?> receiverClass;
        final Class<?> valueClass;
        final Type valueType;
        final CallTarget get;
        final CallTarget add;
        final CallTarget addAtIndex;
        final CallTarget set;
        final CallTarget remove;
        final CallTarget size;
        final CallTarget apply;

        Cache(PolyglotLanguageInstance languageInstance, Class<?> receiverClass, Class<?> valueClass, Type valueType) {
            this.languageInstance = languageInstance;
            this.receiverClass = receiverClass;
            this.valueClass = valueClass;
            this.valueType = valueType;
            this.get = PolyglotListFactory.CacheFactory.GetNodeGen.create(this).getCallTarget();
            this.add = PolyglotListFactory.CacheFactory.AddNodeGen.create(this).getCallTarget();
            this.addAtIndex = PolyglotListFactory.CacheFactory.AddAtIndexNodeGen.create(this).getCallTarget();
            this.size = PolyglotListFactory.CacheFactory.SizeNodeGen.create(this).getCallTarget();
            this.set = PolyglotListFactory.CacheFactory.SetNodeGen.create(this).getCallTarget();
            this.remove = PolyglotListFactory.CacheFactory.RemoveNodeGen.create(this).getCallTarget();
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
        extends PolyglotListNode {
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

        static abstract class RemoveNode
        extends PolyglotListNode {
            RemoveNode(Cache cache) {
                super(cache);
            }

            @Override
            protected String getOperationName() {
                return "remove";
            }

            @Specialization(limit="LIMIT")
            Object doCached(PolyglotLanguageContext languageContext, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary interop, @Cached BranchProfile error) {
                Object key = args[2];
                assert (key instanceof Integer);
                int index = (Integer)key;
                try {
                    interop.removeArrayElement(receiver, index);
                }
                catch (InvalidArrayIndexException e) {
                    error.enter();
                    throw PolyglotInteropErrors.invalidListIndex(languageContext, receiver, this.cache.valueType, index);
                }
                catch (UnsupportedMessageException e) {
                    error.enter();
                    throw PolyglotInteropErrors.listUnsupported(languageContext, receiver, this.cache.valueType, "remove");
                }
                return null;
            }
        }

        static abstract class SetNode
        extends PolyglotListNode {
            SetNode(Cache cache) {
                super(cache);
            }

            @Override
            protected String getOperationName() {
                return "set";
            }

            @Specialization(limit="LIMIT")
            Object doCached(PolyglotLanguageContext languageContext, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary interop, @Cached PolyglotLanguageContext.ToGuestValueNode toGuest, @Cached BranchProfile error) {
                Object key = args[2];
                assert (key instanceof Integer);
                int index = (Integer)key;
                Object value2 = toGuest.execute(languageContext, args[3]);
                try {
                    interop.writeArrayElement(receiver, index, value2);
                }
                catch (InvalidArrayIndexException e) {
                    error.enter();
                    throw PolyglotInteropErrors.invalidListIndex(languageContext, receiver, this.cache.valueType, index);
                }
                catch (UnsupportedMessageException e) {
                    error.enter();
                    throw PolyglotInteropErrors.listUnsupported(languageContext, receiver, this.cache.valueType, "set");
                }
                catch (UnsupportedTypeException e) {
                    error.enter();
                    throw PolyglotInteropErrors.invalidListValue(languageContext, receiver, this.cache.valueType, ((Integer)key).intValue(), value2);
                }
                return null;
            }
        }

        static abstract class AddAtIndexNode
        extends PolyglotListNode {
            AddAtIndexNode(Cache cache) {
                super(cache);
            }

            @Override
            protected String getOperationName() {
                return "add";
            }

            @Specialization(limit="LIMIT")
            Object doCached(PolyglotLanguageContext languageContext, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary interop, @Cached PolyglotLanguageContext.ToGuestValueNode toGuest, @Cached BranchProfile error) {
                Object key = args[2];
                assert (key instanceof Integer);
                int index = (Integer)key;
                if (index < 0) {
                    error.enter();
                    throw PolyglotInteropErrors.invalidListIndex(languageContext, receiver, this.cache.valueType, index);
                }
                Object value2 = toGuest.execute(languageContext, args[3]);
                try {
                    long size = interop.getArraySize(receiver);
                    if (interop.isArrayElementInsertable(receiver, size)) {
                        for (long cur = size; cur > (long)index; --cur) {
                            interop.writeArrayElement(receiver, cur, interop.readArrayElement(receiver, cur - 1L));
                            TruffleSafepoint.poll(interop);
                        }
                    } else {
                        error.enter();
                        throw PolyglotInteropErrors.listUnsupported(languageContext, receiver, this.cache.valueType, "add");
                    }
                    interop.writeArrayElement(receiver, index, value2);
                }
                catch (UnsupportedMessageException e) {
                    error.enter();
                    throw PolyglotInteropErrors.listUnsupported(languageContext, receiver, this.cache.valueType, "add");
                }
                catch (UnsupportedTypeException e) {
                    error.enter();
                    throw PolyglotInteropErrors.invalidListValue(languageContext, receiver, this.cache.valueType, index, value2);
                }
                catch (InvalidArrayIndexException e) {
                    error.enter();
                    throw PolyglotInteropErrors.invalidListIndex(languageContext, receiver, this.cache.valueType, index);
                }
                return true;
            }
        }

        static abstract class AddNode
        extends PolyglotListNode {
            AddNode(Cache cache) {
                super(cache);
            }

            @Override
            protected String getOperationName() {
                return "add";
            }

            @Specialization(limit="LIMIT")
            Object doCached(PolyglotLanguageContext languageContext, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary interop, @Cached PolyglotLanguageContext.ToGuestValueNode toGuest, @Cached BranchProfile error) {
                Object value2 = toGuest.execute(languageContext, args[2]);
                long size = 0L;
                try {
                    size = interop.getArraySize(receiver);
                    if (!interop.isArrayElementInsertable(receiver, size)) {
                        error.enter();
                        throw PolyglotInteropErrors.listUnsupported(languageContext, receiver, this.cache.valueType, "add");
                    }
                    interop.writeArrayElement(receiver, size, value2);
                }
                catch (UnsupportedMessageException e) {
                    error.enter();
                    throw PolyglotInteropErrors.listUnsupported(languageContext, receiver, this.cache.valueType, "add");
                }
                catch (UnsupportedTypeException e) {
                    error.enter();
                    throw PolyglotInteropErrors.invalidListValue(languageContext, receiver, this.cache.valueType, size, value2);
                }
                catch (InvalidArrayIndexException e) {
                    error.enter();
                    throw PolyglotInteropErrors.invalidListIndex(languageContext, receiver, this.cache.valueType, size);
                }
                return true;
            }
        }

        static abstract class GetNode
        extends PolyglotListNode {
            GetNode(Cache cache) {
                super(cache);
            }

            @Specialization(limit="LIMIT")
            Object doCached(PolyglotLanguageContext languageContext, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary interop, @Cached PolyglotToHostNode toHost, @Cached BranchProfile error) {
                Object key = args[2];
                Object result = null;
                assert (key instanceof Integer);
                int index = (Integer)key;
                try {
                    return toHost.execute(languageContext, interop.readArrayElement(receiver, index), this.cache.valueClass, this.cache.valueType);
                }
                catch (InvalidArrayIndexException e) {
                    error.enter();
                    throw PolyglotInteropErrors.invalidListIndex(languageContext, receiver, this.cache.valueType, index);
                }
                catch (UnsupportedMessageException e) {
                    error.enter();
                    throw PolyglotInteropErrors.listUnsupported(languageContext, receiver, this.cache.valueType, "get()");
                }
            }

            @Override
            protected String getOperationName() {
                return "get";
            }
        }

        static abstract class SizeNode
        extends PolyglotListNode {
            SizeNode(Cache cache) {
                super(cache);
            }

            @Specialization(limit="LIMIT")
            Object doCached(PolyglotLanguageContext languageContext, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary interop) {
                try {
                    return (int)interop.getArraySize(receiver);
                }
                catch (UnsupportedMessageException unsupportedMessageException) {
                    return 0;
                }
            }

            @Override
            protected String getOperationName() {
                return "size";
            }
        }

        static abstract class PolyglotListNode
        extends HostToGuestRootNode {
            static final int LIMIT = 5;
            final Cache cache;

            PolyglotListNode(Cache cache) {
                super(cache.languageInstance);
                this.cache = cache;
            }

            protected Class<? extends TruffleObject> getReceiverType() {
                return this.cache.receiverClass;
            }

            @Override
            public final String getName() {
                return "PolyglotList<" + this.cache.receiverClass + ", " + this.cache.valueType + ">." + this.getOperationName();
            }

            protected abstract String getOperationName();
        }

        private static final class Key {
            final Class<?> receiverClass;
            final Class<?> valueClass;
            final Type valueType;

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

