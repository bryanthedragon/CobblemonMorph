
package com.oracle.truffle.polyglot;

import com.oracle.truffle.api.CallTarget;
import com.oracle.truffle.api.dsl.Cached;
import com.oracle.truffle.api.dsl.Specialization;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.InvalidArrayIndexException;
import com.oracle.truffle.api.interop.TruffleObject;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
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
import com.oracle.truffle.polyglot.PolyglotMapEntryAndFunction;
import com.oracle.truffle.polyglot.PolyglotMapEntryFactory;
import com.oracle.truffle.polyglot.PolyglotToHostNode;
import com.oracle.truffle.polyglot.PolyglotWrapper;
import java.lang.reflect.Type;
import java.util.Map;
import java.util.Objects;

class PolyglotMapEntry<K, V>
implements Map.Entry<K, V>,
PolyglotWrapper {
    final PolyglotLanguageContext languageContext;
    final Object guestObject;
    final Cache cache;

    PolyglotMapEntry(PolyglotLanguageContext languageContext, Object obj, Class<K> keyClass, Type keyType, Class<V> valueClass, Type valueType) {
        this.languageContext = languageContext;
        this.guestObject = obj;
        this.cache = Cache.lookup(languageContext, obj.getClass(), keyClass, keyType, valueClass, valueType);
    }

    static <K, V> PolyglotMapEntry<K, V> create(PolyglotLanguageContext languageContext, Object foreignObject, boolean implementsFunction, Class<K> keyClass, Type keyType, Class<V> valueClass, Type valueType) {
        if (implementsFunction) {
            return new PolyglotMapEntryAndFunction<K, V>(languageContext, foreignObject, keyClass, keyType, valueClass, valueType);
        }
        return new PolyglotMapEntry<K, V>(languageContext, foreignObject, keyClass, keyType, valueClass, valueType);
    }

    @Override
    public final K getKey() {
        return (K)this.cache.getKey.call(this.languageContext, this.guestObject);
    }

    @Override
    public final V getValue() {
        return (V)this.cache.getValue.call(this.languageContext, this.guestObject);
    }

    @Override
    public final V setValue(V value2) {
        throw new UnsupportedOperationException();
    }

    @Override
    public final Object getGuestObject() {
        return this.guestObject;
    }

    @Override
    public final PolyglotContextImpl getContext() {
        return this.languageContext.context;
    }

    @Override
    public final PolyglotLanguageContext getLanguageContext() {
        return this.languageContext;
    }

    @Override
    public final boolean equals(Object o) {
        if (o instanceof PolyglotMapEntry) {
            return PolyglotWrapper.equals(this.languageContext, this.guestObject, ((PolyglotMapEntry)o).guestObject);
        }
        return false;
    }

    @Override
    public final int hashCode() {
        return PolyglotWrapper.hashCode(this.languageContext, this.guestObject);
    }

    public final String toString() {
        return PolyglotWrapper.toString(this);
    }

    static final class Cache {
        final PolyglotLanguageInstance languageInstance;
        final Class<?> receiverClass;
        final Class<?> keyClass;
        final Type keyType;
        final Class<?> valueClass;
        final Type valueType;
        final CallTarget getKey;
        final CallTarget getValue;
        final CallTarget apply;

        Cache(PolyglotLanguageInstance languageInstance, Class<?> receiverClass, Class<?> keyClass, Type keyType, Class<?> valueClass, Type valueType) {
            this.languageInstance = languageInstance;
            this.receiverClass = receiverClass;
            this.keyClass = keyClass;
            this.keyType = keyType;
            this.valueClass = valueClass;
            this.valueType = valueType;
            this.getKey = PolyglotMapEntryFactory.CacheFactory.GetNodeGen.create(this, "getKey", 0L, keyClass, keyType).getCallTarget();
            this.getValue = PolyglotMapEntryFactory.CacheFactory.GetNodeGen.create(this, "getValue", 1L, valueClass, valueType).getCallTarget();
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
        extends PolyglotMapEntryNode {
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

        static abstract class GetNode
        extends PolyglotMapEntryNode {
            private final String name;
            private final long index;
            private final Class<?> elementClass;
            private final Type elementType;

            GetNode(Cache cache, String name, long index, Class<?> elementClass, Type elementType) {
                super(cache);
                this.name = name;
                this.index = index;
                this.elementClass = elementClass;
                this.elementType = elementType;
            }

            @Override
            protected String getOperationName() {
                return this.name;
            }

            @Specialization(limit="LIMIT")
            protected Object doCached(PolyglotLanguageContext languageContext, Object receiver, Object[] args, @CachedLibrary(value="receiver") InteropLibrary interop, @Cached PolyglotToHostNode toHost, @Cached BranchProfile error) {
                if (interop.hasArrayElements(receiver)) {
                    Object result;
                    try {
                        result = interop.readArrayElement(receiver, this.index);
                    }
                    catch (UnsupportedMessageException e) {
                        error.enter();
                        throw this.unsupported(languageContext, receiver);
                    }
                    catch (InvalidArrayIndexException e) {
                        error.enter();
                        throw this.invalidArrayIndex(languageContext, receiver, e.getInvalidIndex());
                    }
                    return toHost.execute(languageContext, result, this.elementClass, this.elementType);
                }
                error.enter();
                throw this.unsupported(languageContext, receiver);
            }
        }

        static abstract class PolyglotMapEntryNode
        extends HostToGuestRootNode {
            static final int LIMIT = 5;
            final Cache cache;

            PolyglotMapEntryNode(Cache cache) {
                super(cache.languageInstance);
                this.cache = cache;
            }

            protected abstract String getOperationName();

            protected final Class<? extends TruffleObject> getReceiverType() {
                return this.cache.receiverClass;
            }

            @Override
            public final String getName() {
                return "PolyglotMapEntry<" + this.cache.receiverClass + ", " + this.getKeyType() + ", " + this.getValueType() + ">." + this.getOperationName();
            }

            protected final RuntimeException unsupported(PolyglotLanguageContext languageContext, Object receiver) {
                throw PolyglotInteropErrors.mapEntryUnsupported(languageContext, receiver, this.getKeyType(), this.getValueType(), this.getOperationName());
            }

            protected final RuntimeException invalidArrayIndex(PolyglotLanguageContext languageContext, Object receiver, long index) {
                throw PolyglotInteropErrors.invalidMapEntryArrayIndex(languageContext, receiver, this.getKeyType(), this.getValueType(), index);
            }

            protected final Type getKeyType() {
                return this.cache.keyType != null ? this.cache.keyType : this.cache.keyClass;
            }

            protected final Type getValueType() {
                return this.cache.valueType != null ? this.cache.valueType : this.cache.valueClass;
            }
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
}

