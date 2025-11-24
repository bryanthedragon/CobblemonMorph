
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnknownIdentifierException;
import com.oracle.truffle.api.interop.UnknownKeyException;
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
import com.oracle.truffle.polyglot.PolyglotList;
import com.oracle.truffle.polyglot.PolyglotMapAndFunction;
import com.oracle.truffle.polyglot.PolyglotMapFactory;
import com.oracle.truffle.polyglot.PolyglotToHostNode;
import com.oracle.truffle.polyglot.PolyglotWrapper;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.AbstractMap;
import java.util.AbstractSet;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Objects;
import java.util.Set;

class PolyglotMap<K, V>
extends AbstractMap<K, V>
implements PolyglotWrapper {
    final PolyglotLanguageContext languageContext;
    final Object guestObject;
    final Cache cache;

    PolyglotMap(PolyglotLanguageContext languageContext, Object obj, Class<K> keyClass, Type keyType, Class<V> valueClass, Type valueType) {
        this.guestObject = obj;
        this.languageContext = languageContext;
        this.cache = Cache.lookup(languageContext, obj.getClass(), keyClass, keyType, valueClass, valueType);
    }

    static <K, V> Map<K, V> create(PolyglotLanguageContext languageContext, Object foreignObject, boolean implementsFunction, Class<K> keyClass, Type keyType, Class<V> valueClass, Type valueType) {
        if (implementsFunction) {
            return new PolyglotMapAndFunction<K, V>(languageContext, foreignObject, keyClass, keyType, valueClass, valueType);
        }
        return new PolyglotMap<K, V>(languageContext, foreignObject, keyClass, keyType, valueClass, valueType);
    }

    @Override
    public PolyglotLanguageContext getLanguageContext() {
        return this.languageContext;
    }

    @Override
    public Object getGuestObject() {
        return this.guestObject;
    }

    @Override
    public PolyglotContextImpl getContext() {
        return this.languageContext.context;
    }

    @Override
    public boolean containsKey(Object key) {
        return (Boolean)this.cache.containsKey.call(this.languageContext, this.guestObject, key);
    }

    @Override
    public Set<Map.Entry<K, V>> entrySet() {
        return (Set)this.cache.entrySet.call(this.languageContext, this.guestObject, this);
    }

    @Override
    public V get(Object key) {
        return (V)this.cache.get.call(this.languageContext, this.guestObject, key);
    }

    @Override
    public V put(K key, V value2) {
        V prev = this.get(key);
        this.cache.put.call(this.languageContext, this.guestObject, key, value2);
        return prev;
    }

    @Override
    public V remove(Object key) {
        V prev = this.get(key);
        this.cache.remove.call(this.languageContext, this.guestObject, key);
        return prev;
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
        if (o instanceof PolyglotMap) {
            return PolyglotWrapper.equals(this.languageContext, this.guestObject, ((PolyglotMap)o).guestObject);
        }
        return false;
    }

    @CompilerDirectives.TruffleBoundary
    private static int intValue(Object key) {
        return ((Number)key).intValue();
    }

    private static final class ParameterizedTypeImpl
    implements ParameterizedType {
        private final Type rawType;
        private final Type[] typeParameters;

        ParameterizedTypeImpl(Type rawType, Type ... typeParameters) {
            this.rawType = rawType;
            this.typeParameters = typeParameters;
        }

        @Override
        public Type[] getActualTypeArguments() {
            return this.typeParameters;
        }

        @Override
        public Type getRawType() {
            return this.rawType;
        }

        @Override
        public Type getOwnerType() {
            return null;
        }
    }

    static final class Cache {
        final PolyglotLanguageInstance languageInstance;
        final Class<?> receiverClass;
        final Class<?> keyClass;
        final Type keyType;
        final Class<?> valueClass;
        final Type valueType;
        final boolean memberKey;
        final boolean numberKey;
        final CallTarget entrySet;
        final CallTarget get;
        final CallTarget put;
        final CallTarget remove;
        final CallTarget removeBoolean;
        final CallTarget containsKey;
        final CallTarget hashEntriesIterator;
        final CallTarget hashSize;
        final CallTarget apply;

        Cache(PolyglotLanguageInstance languageInstance, Class<?> receiverClass, Class<?> keyClass, Type keyType, Class<?> valueClass, Type valueType) {
            this.languageInstance = languageInstance;
            this.receiverClass = receiverClass;
            this.keyClass = keyClass;
            this.keyType = keyType;
            this.valueClass = valueClass;
            this.valueType = valueType;
            this.memberKey = keyClass == Object.class || keyClass == String.class || keyClass == CharSequence.class;
            this.numberKey = keyClass == Object.class || keyClass == Number.class || keyClass == Integer.class || keyClass == Long.class || keyClass == Short.class || keyClass == Byte.class;
            this.get = PolyglotMapFactory.CacheFactory.GetNodeGen.create(this).getCallTarget();
            this.containsKey = PolyglotMapFactory.CacheFactory.ContainsKeyNodeGen.create(this).getCallTarget();
            this.entrySet = PolyglotMapFactory.CacheFactory.EntrySetNodeGen.create(this).getCallTarget();
            this.put = PolyglotMapFactory.CacheFactory.PutNodeGen.create(this).getCallTarget();
            this.remove = PolyglotMapFactory.CacheFactory.RemoveNodeGen.create(this).getCallTarget();
            this.removeBoolean = PolyglotMapFactory.CacheFactory.RemoveBooleanNodeGen.create(this).getCallTarget();
            this.hashEntriesIterator = PolyglotMapFactory.CacheFactory.HashEntriesIteratorNodeGen.create(this).getCallTarget();
            this.hashSize = PolyglotMapFactory.CacheFactory.HashSizeNodeGen.create(this).getCallTarget();
            this.apply = new Apply(this).getCallTarget();
        }

        static Cache lookup(PolyglotLanguageContext languageContext, Class<?> receiverClass, Class<?> keyClass, Type keyType, Class<?> valueClass, Type valueType) {
            Key cacheKey = new Key(receiverClass, keyClass, keyType, valueClass, valueType);
            Cache cache = HostToGuestRootNode.lookupHostCodeCache(languageContext, cacheKey, Cache.class);
            if (cache == null) {
                cache = HostToGuestRootNode.installHostCodeCache(languageContext, cacheKey, new Cache(languageContext.getLanguageInstance(), receiverClass, keyClass, keyType, valueClass, valueType), Cache.class);
            }
            assert (cache.receiverClass == receiverClass);
            assert (cache.keyClass == keyClass);
            assert (cache.keyType == keyType);
            assert (cache.valueClass == valueClass);
            assert (cache.valueType == valueType);
            return cache;
        }

        private static class Apply
        extends PolyglotMapNode {
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

        static abstract class HashSizeNode
        extends PolyglotMapNode {
            HashSizeNode(Cache cache) {
                super(cache);
            }

            @Override
            protected String getOperationName() {
                return "size";
            }

            @Specialization(limit="LIMIT")
            protected Object doCached(PolyglotLanguageContext languageContext, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary interop, @Cached BranchProfile error) {
                if (interop.hasHashEntries(receiver)) {
                    try {
                        return interop.getHashSize(receiver);
                    }
                    catch (UnsupportedMessageException e) {
                        error.enter();
                        throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "size");
                    }
                }
                error.enter();
                throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "size");
            }
        }

        static abstract class HashEntriesIteratorNode
        extends PolyglotMapNode {
            HashEntriesIteratorNode(Cache cache) {
                super(cache);
            }

            @Override
            protected String getOperationName() {
                return "iterator";
            }

            @Specialization(limit="LIMIT")
            protected Object doCached(PolyglotLanguageContext languageContext, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary interop, @Cached PolyglotToHostNode toHost, @Cached BranchProfile error) {
                if (interop.hasHashEntries(receiver)) {
                    try {
                        Object iterator = interop.getHashEntriesIterator(receiver);
                        Class<Object> useKeyType = this.cache.keyType != null ? this.cache.keyType : Object.class;
                        Class<Object> useValueType = this.cache.valueType != null ? this.cache.valueType : Object.class;
                        ParameterizedTypeImpl genericType = new ParameterizedTypeImpl((Type)((Object)Iterator.class), new ParameterizedTypeImpl((Type)((Object)Map.Entry.class), new Type[]{useKeyType, useValueType}));
                        return toHost.execute(languageContext, iterator, Iterator.class, genericType);
                    }
                    catch (UnsupportedMessageException e) {
                        error.enter();
                        throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "iterator");
                    }
                }
                error.enter();
                throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "iterator");
            }
        }

        static abstract class RemoveBoolean
        extends PolyglotMapNode {
            RemoveBoolean(Cache cache) {
                super(cache);
            }

            @Override
            protected String getOperationName() {
                return "remove";
            }

            @Specialization(limit="LIMIT")
            protected Object doCached(PolyglotLanguageContext languageContext, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary interop, @Cached PolyglotLanguageContext.ToGuestValueNode toGuest, @Cached BranchProfile error) {
                Object key = args[2];
                Object expectedValue = args[3];
                try {
                    boolean supported = false;
                    if (interop.hasHashEntries(receiver)) {
                        Object readValue;
                        Object guestKey = toGuest.execute(languageContext, key);
                        Object guestExcpectedValue = toGuest.execute(languageContext, expectedValue);
                        if (!RemoveBoolean.equalsBoundary(guestExcpectedValue, readValue = interop.readHashValue(receiver, guestKey))) {
                            return false;
                        }
                        interop.removeHashEntry(receiver, guestKey);
                        return true;
                    }
                    if (this.cache.memberKey && interop.hasMembers(receiver)) {
                        supported = true;
                        if (this.isObjectKey(key)) {
                            String member = (String)key;
                            Object readValue = interop.readMember(receiver, member);
                            Object guestExpectedValue = toGuest.execute(languageContext, expectedValue);
                            if (!RemoveBoolean.equalsBoundary(guestExpectedValue, readValue)) {
                                return false;
                            }
                            interop.removeMember(receiver, (String)key);
                            return true;
                        }
                    } else if (this.cache.numberKey && interop.hasArrayElements(receiver)) {
                        supported = true;
                        if (this.isArrayKey(key)) {
                            int index = PolyglotMap.intValue(key);
                            Object readValue = interop.readArrayElement(receiver, index);
                            Object guestExpectedValue = toGuest.execute(languageContext, expectedValue);
                            if (!RemoveBoolean.equalsBoundary(guestExpectedValue, readValue)) {
                                return false;
                            }
                            interop.removeArrayElement(receiver, index);
                            return true;
                        }
                    }
                    error.enter();
                    if (!supported) {
                        throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "remove");
                    }
                    return false;
                }
                catch (InvalidArrayIndexException | UnknownIdentifierException | UnknownKeyException e) {
                    error.enter();
                    return false;
                }
                catch (UnsupportedMessageException e) {
                    error.enter();
                    throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "remove");
                }
            }

            @CompilerDirectives.TruffleBoundary
            private static boolean equalsBoundary(Object expectedValue, Object readValue) {
                return Objects.equals(expectedValue, readValue);
            }
        }

        static abstract class RemoveNode
        extends PolyglotMapNode {
            RemoveNode(Cache cache) {
                super(cache);
            }

            @Override
            protected String getOperationName() {
                return "remove";
            }

            @Specialization(limit="LIMIT")
            protected Object doCached(PolyglotLanguageContext languageContext, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary interop, @Cached PolyglotLanguageContext.ToGuestValueNode toGuest, @Cached BranchProfile error) {
                Object key = args[2];
                try {
                    boolean supported = false;
                    if (interop.hasHashEntries(receiver)) {
                        interop.removeHashEntry(receiver, toGuest.execute(languageContext, key));
                        return null;
                    }
                    if (this.cache.memberKey && interop.hasMembers(receiver)) {
                        supported = true;
                        if (this.isObjectKey(key)) {
                            interop.removeMember(receiver, (String)key);
                            return null;
                        }
                    } else if (this.cache.numberKey && interop.hasArrayElements(receiver)) {
                        supported = true;
                        if (this.isArrayKey(key)) {
                            interop.removeArrayElement(receiver, PolyglotMap.intValue(key));
                            return null;
                        }
                    }
                    error.enter();
                    if (!supported) {
                        throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "remove");
                    }
                    return null;
                }
                catch (InvalidArrayIndexException | UnknownIdentifierException | UnknownKeyException e) {
                    error.enter();
                    return null;
                }
                catch (UnsupportedMessageException e) {
                    error.enter();
                    throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "remove");
                }
            }
        }

        static abstract class Put
        extends PolyglotMapNode {
            Put(Cache cache) {
                super(cache);
            }

            @Override
            protected String getOperationName() {
                return "put";
            }

            @Specialization(limit="LIMIT")
            protected Object doCached(PolyglotLanguageContext languageContext, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary interop, @Cached PolyglotLanguageContext.ToGuestValueNode toGuest, @Cached BranchProfile error) {
                Object key = args[2];
                Object guestValue = toGuest.execute(languageContext, args[3]);
                try {
                    boolean supported = false;
                    if (interop.hasHashEntries(receiver)) {
                        interop.writeHashEntry(receiver, toGuest.execute(languageContext, key), guestValue);
                        return null;
                    }
                    if (this.cache.memberKey && interop.hasMembers(receiver)) {
                        supported = true;
                        if (this.isObjectKey(key)) {
                            interop.writeMember(receiver, (String)key, guestValue);
                            return null;
                        }
                    } else if (this.cache.numberKey && interop.hasArrayElements(receiver)) {
                        supported = true;
                        if (this.isArrayKey(key)) {
                            interop.writeArrayElement(receiver, PolyglotMap.intValue(key), guestValue);
                            return null;
                        }
                    }
                    error.enter();
                    if (!supported) {
                        throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "put");
                    }
                    throw PolyglotInteropErrors.invalidMapIdentifier(languageContext, receiver, this.getKeyType(), this.getValueType(), key);
                }
                catch (InvalidArrayIndexException | UnknownIdentifierException | UnknownKeyException | UnsupportedMessageException | UnsupportedTypeException e) {
                    error.enter();
                    throw this.error(languageContext, receiver, e, key, guestValue);
                }
            }

            @CompilerDirectives.TruffleBoundary
            RuntimeException error(PolyglotLanguageContext languageContext, Object receiver, InteropException e, Object key, Object guestValue) {
                if (e instanceof UnknownIdentifierException || e instanceof InvalidArrayIndexException) {
                    throw PolyglotInteropErrors.invalidMapIdentifier(languageContext, receiver, this.getKeyType(), this.getValueType(), key);
                }
                if (e instanceof UnsupportedMessageException) {
                    throw PolyglotInteropErrors.mapUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), "put");
                }
                if (e instanceof UnsupportedTypeException) {
                    throw PolyglotInteropErrors.invalidMapValue(languageContext, receiver, this.getKeyType(), this.getValueType(), key, guestValue);
                }
                throw CompilerDirectives.shouldNotReachHere("unhandled error");
            }
        }

        static abstract class GetNode
        extends PolyglotMapNode {
            GetNode(Cache cache) {
                super(cache);
            }

            @Override
            protected String getOperationName() {
                return "get";
            }

            @Specialization(limit="LIMIT")
            protected Object doCached(PolyglotLanguageContext languageContext, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary interop, @Cached PolyglotLanguageContext.ToGuestValueNode toGuest, @Cached PolyglotToHostNode toHost, @Cached BranchProfile error) {
                Object result;
                block7: {
                    Object key = args[2];
                    try {
                        if (interop.hasHashEntries(receiver)) {
                            result = interop.readHashValue(receiver, toGuest.execute(languageContext, key));
                            break block7;
                        }
                        if (this.cache.memberKey && interop.hasMembers(receiver)) {
                            if (this.isObjectKey(key)) {
                                result = interop.readMember(receiver, (String)key);
                                break block7;
                            }
                            return null;
                        }
                        if (this.cache.numberKey && interop.hasArrayElements(receiver)) {
                            if (this.isArrayKey(key)) {
                                result = interop.readArrayElement(receiver, PolyglotMap.intValue(key));
                                break block7;
                            }
                            return null;
                        }
                        return null;
                    }
                    catch (InvalidArrayIndexException | UnknownIdentifierException | UnknownKeyException | UnsupportedMessageException e) {
                        error.enter();
                        return null;
                    }
                }
                return toHost.execute(languageContext, result, this.cache.valueClass, this.cache.valueType);
            }
        }

        static abstract class EntrySet
        extends PolyglotMapNode {
            EntrySet(Cache cache) {
                super(cache);
            }

            @Specialization(limit="LIMIT")
            protected Object doCached(PolyglotLanguageContext languageContext, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary interop, @Cached PolyglotToHostNode toHost, @Cached BranchProfile error) {
                PolyglotMap originalMap = (PolyglotMap)args[2];
                if (interop.hasHashEntries(receiver)) {
                    return originalMap.new HashEntries();
                }
                List<String> keys = null;
                int keysSize = 0;
                long elemSize = 0L;
                if (this.cache.memberKey && interop.hasMembers(receiver)) {
                    Object truffleKeys;
                    try {
                        truffleKeys = interop.getMembers(receiver);
                    }
                    catch (UnsupportedMessageException e) {
                        error.enter();
                        return Collections.emptySet();
                    }
                    keys = PolyglotList.create(languageContext, truffleKeys, false, String.class, null);
                    keysSize = keys.size();
                } else if (this.cache.numberKey && interop.hasArrayElements(receiver)) {
                    try {
                        elemSize = interop.getArraySize(receiver);
                    }
                    catch (UnsupportedMessageException e) {
                        error.enter();
                        elemSize = 0L;
                    }
                }
                PolyglotMap polyglotMap = originalMap;
                Objects.requireNonNull(polyglotMap);
                return polyglotMap.new LazyEntries(keys, keysSize, (int)elemSize);
            }

            @Override
            protected String getOperationName() {
                return "entrySet";
            }
        }

        static abstract class ContainsKeyNode
        extends PolyglotMapNode {
            ContainsKeyNode(Cache cache) {
                super(cache);
            }

            @Specialization(limit="LIMIT")
            protected Object doCached(PolyglotLanguageContext languageContext, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary interop, @Cached PolyglotLanguageContext.ToGuestValueNode toGuest) {
                Object key = args[2];
                if (interop.hasHashEntries(receiver)) {
                    return interop.isHashEntryReadable(receiver, toGuest.execute(languageContext, key));
                }
                if (this.cache.memberKey && interop.hasMembers(receiver)) {
                    if (this.isObjectKey(key)) {
                        return interop.isMemberReadable(receiver, (String)key);
                    }
                } else if (this.cache.numberKey && interop.hasArrayElements(receiver) && this.isArrayKey(key)) {
                    return interop.isArrayElementReadable(receiver, PolyglotMap.intValue(key));
                }
                return false;
            }

            @Override
            protected String getOperationName() {
                return "containsKey";
            }
        }

        static abstract class PolyglotMapNode
        extends HostToGuestRootNode {
            static final int LIMIT = 5;
            final Cache cache;

            PolyglotMapNode(Cache cache) {
                super(cache.languageInstance);
                this.cache = cache;
            }

            protected Class<? extends TruffleObject> getReceiverType() {
                return this.cache.receiverClass;
            }

            @Override
            public final String getName() {
                return "PolyglotMap<" + this.cache.receiverClass + ", " + this.getKeyType() + ", " + this.getValueType() + ">." + this.getOperationName();
            }

            protected final boolean isObjectKey(Object key) {
                return this.cache.memberKey && this.cache.keyClass.isInstance(key) && key instanceof String;
            }

            protected final boolean isArrayKey(Object key) {
                return this.cache.numberKey && this.cache.keyClass.isInstance(key) && key instanceof Number;
            }

            protected Type getKeyType() {
                return this.cache.keyType != null ? this.cache.keyType : this.cache.keyClass;
            }

            protected Type getValueType() {
                return this.cache.valueType != null ? this.cache.valueType : this.cache.valueClass;
            }

            protected abstract String getOperationName();
        }

        private static final class Key {
            final Class<?> receiverClass;
            final Class<?> keyClass;
            final Type keyType;
            final Class<?> valueClass;
            final Type valueType;

            Key(Class<?> receiverClass, Class<?> keyClass, Type keyType, Class<?> valueClass, Type valueType) {
                this.receiverClass = Objects.requireNonNull(receiverClass);
                this.keyClass = Objects.requireNonNull(keyClass);
                this.keyType = keyType;
                this.valueClass = Objects.requireNonNull(valueClass);
                this.valueType = valueType;
            }

            public int hashCode() {
                int hashCode = 17;
                hashCode = hashCode * 31 + this.receiverClass.hashCode();
                hashCode = hashCode * 31 + this.keyClass.hashCode();
                hashCode = hashCode * 31 + (this.keyType != null ? this.keyType.hashCode() : 0);
                hashCode = hashCode * 31 + this.valueClass.hashCode();
                hashCode = hashCode * 31 + (this.valueType != null ? this.valueType.hashCode() : 0);
                return hashCode;
            }

            public boolean equals(Object obj) {
                if (this == obj) {
                    return true;
                }
                if (obj == null || this.getClass() != obj.getClass()) {
                    return false;
                }
                Key other = (Key)obj;
                return this.receiverClass == other.receiverClass && this.keyClass == other.keyClass && Objects.equals(this.keyType, other.keyType) && this.valueClass == other.valueClass && Objects.equals(this.valueType, other.valueType);
            }
        }
    }

    private final class EntryImpl
    implements Map.Entry<K, V> {
        private final K key;

        EntryImpl(K key) {
            this.key = key;
        }

        @Override
        public K getKey() {
            return this.key;
        }

        @Override
        public V getValue() {
            return PolyglotMap.this.get(this.key);
        }

        @Override
        public V setValue(V value2) {
            return PolyglotMap.this.put(this.key, value2);
        }

        public String toString() {
            return "Entry[key=" + this.key + ", value=" + PolyglotMap.this.get(this.key) + "]";
        }
    }

    private final class HashEntries
    extends AbstractEntrySet {
        private HashEntries() {
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            return (Iterator)PolyglotMap.this.cache.hashEntriesIterator.call(PolyglotMap.this.languageContext, PolyglotMap.this.guestObject);
        }

        @Override
        public int size() {
            long size = (Long)PolyglotMap.this.cache.hashSize.call(PolyglotMap.this.languageContext, PolyglotMap.this.guestObject);
            return size > Integer.MAX_VALUE ? Integer.MAX_VALUE : (int)size;
        }
    }

    private final class LazyEntries
    extends AbstractEntrySet {
        private final List<?> props;
        private final int keysSize;
        private final int elemSize;

        LazyEntries(List<?> keys, int keysSize, int elemSize) {
            assert (keys != null || keysSize == 0);
            this.props = keys;
            this.keysSize = keysSize;
            this.elemSize = elemSize;
        }

        @Override
        public Iterator<Map.Entry<K, V>> iterator() {
            if (this.keysSize > 0 && this.elemSize > 0) {
                return new CombinedIterator();
            }
            if (this.keysSize > 0) {
                return new LazyKeysIterator();
            }
            return new ElementsIterator();
        }

        @Override
        public int size() {
            return (this.props != null ? this.props.size() : this.keysSize) + this.elemSize;
        }

        private final class CombinedIterator
        implements Iterator<Map.Entry<K, V>> {
            private final Iterator<Map.Entry<K, V>> elemIter;
            private final Iterator<Map.Entry<K, V>> keysIter;
            private boolean isElemCurrent;

            private CombinedIterator() {
                this.elemIter = new ElementsIterator();
                this.keysIter = new LazyKeysIterator();
            }

            @Override
            public boolean hasNext() {
                return this.elemIter.hasNext() || this.keysIter.hasNext();
            }

            @Override
            public Map.Entry<K, V> next() {
                if (this.elemIter.hasNext()) {
                    this.isElemCurrent = true;
                    return this.elemIter.next();
                }
                if (this.keysIter.hasNext()) {
                    this.isElemCurrent = false;
                    return this.keysIter.next();
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                if (this.isElemCurrent) {
                    this.elemIter.remove();
                } else {
                    this.keysIter.remove();
                }
            }
        }

        private final class ElementsIterator
        implements Iterator<Map.Entry<K, V>> {
            private int index = 0;
            private boolean hasCurrentEntry;

            ElementsIterator() {
            }

            @Override
            public boolean hasNext() {
                return this.index < LazyEntries.this.elemSize;
            }

            @Override
            public Map.Entry<K, V> next() {
                if (this.hasNext()) {
                    Number key = PolyglotMap.this.cache.keyClass == Long.class ? (Number)Long.valueOf(this.index) : (Number)this.index;
                    ++this.index;
                    this.hasCurrentEntry = true;
                    return new EntryImpl(PolyglotMap.this.cache.keyClass.cast(key));
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                if (!this.hasCurrentEntry) {
                    throw new IllegalStateException("No current entry.");
                }
                PolyglotMap.this.cache.removeBoolean.call(PolyglotMap.this.languageContext, PolyglotMap.this.guestObject, PolyglotMap.this.cache.keyClass.cast(this.index - 1));
                this.hasCurrentEntry = false;
            }
        }

        private final class LazyKeysIterator
        implements Iterator<Map.Entry<K, V>> {
            private final int size;
            private int index;
            private int currentIndex = -1;

            LazyKeysIterator() {
                this.size = LazyEntries.this.props != null ? LazyEntries.this.props.size() : LazyEntries.this.keysSize;
                this.index = 0;
            }

            @Override
            public boolean hasNext() {
                return this.index < this.size;
            }

            @Override
            public Map.Entry<K, V> next() {
                if (this.hasNext()) {
                    this.currentIndex = this.index;
                    Object key = LazyEntries.this.props.get(this.index++);
                    return new EntryImpl(key);
                }
                throw new NoSuchElementException();
            }

            @Override
            public void remove() {
                if (this.currentIndex >= 0) {
                    LazyEntries.this.props.remove(this.currentIndex);
                    this.currentIndex = -1;
                    --this.index;
                } else {
                    throw new IllegalStateException("No current entry.");
                }
            }
        }
    }

    private abstract class AbstractEntrySet
    extends AbstractSet<Map.Entry<K, V>> {
        private AbstractEntrySet() {
        }

        @Override
        public boolean contains(Object o) {
            return PolyglotMap.this.containsKey(o);
        }

        @Override
        public boolean remove(Object o) {
            if (o instanceof Map.Entry) {
                Map.Entry e = (Map.Entry)o;
                return (Boolean)PolyglotMap.this.cache.removeBoolean.call(PolyglotMap.this.languageContext, PolyglotMap.this.guestObject, e.getKey(), e.getValue());
            }
            return false;
        }
    }
}

