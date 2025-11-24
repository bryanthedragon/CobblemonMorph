
package com.oracle.truffle.object;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.library.DefaultExportProvider;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.object.DynamicObjectLibraryImpl;
import com.oracle.truffle.object.DynamicObjectLibraryImplFactory;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=DynamicObjectLibraryImpl.class)
public final class DynamicObjectLibraryImplGen {
    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);

    private DynamicObjectLibraryImplGen() {
    }

    static {
        LibraryExport.register(DynamicObjectLibraryImpl.class, new DynamicObjectLibraryExports());
    }

    @GeneratedBy(value=DynamicObjectLibraryImpl.class)
    private static final class DynamicObjectLibraryExports
    extends LibraryExport<DynamicObjectLibrary> {
        private DynamicObjectLibraryExports() {
            super(DynamicObjectLibrary.class, DynamicObject.class, false, false, 0);
        }

        @Override
        protected DynamicObjectLibrary createUncached(Object receiver) {
            assert (receiver instanceof DynamicObject);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected DynamicObjectLibrary createCached(Object receiver) {
            assert (receiver instanceof DynamicObject);
            return new Cached(receiver);
        }

        @GeneratedBy(value=DynamicObjectLibraryImpl.class)
        @DenyReplace
        private static final class Uncached
        extends DynamicObjectLibrary {
            private final Class<? extends DynamicObject> receiverClass_;

            protected Uncached(Object receiver) {
                this.receiverClass_ = ((DynamicObject)receiver).getClass();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                return CompilerDirectives.isExact(receiver, this.receiverClass_) && Uncached.accepts_(receiver);
            }

            @Override
            public boolean isAdoptable() {
                return false;
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.MEGAMORPHIC;
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Shape getShape(DynamicObject arg0Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.getShape(arg0Value, arg0Value.getShape());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getOrDefault(DynamicObject arg0Value, Object arg1Value, Object arg2Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.getOrDefault(arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int getIntOrDefault(DynamicObject arg0Value, Object arg1Value, Object arg2Value) throws UnexpectedResultException {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.getIntOrDefault(arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public double getDoubleOrDefault(DynamicObject arg0Value, Object arg1Value, Object arg2Value) throws UnexpectedResultException {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.getDoubleOrDefault(arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long getLongOrDefault(DynamicObject arg0Value, Object arg1Value, Object arg2Value) throws UnexpectedResultException {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.getLongOrDefault(arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean containsKey(DynamicObject arg0Value, Object arg1Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.containsKey(arg0Value, arg1Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void put(DynamicObject arg0Value, Object arg1Value, Object arg2Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                DynamicObjectLibraryImpl.put(arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void putInt(DynamicObject arg0Value, Object arg1Value, int arg2Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                DynamicObjectLibraryImpl.putInt(arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void putLong(DynamicObject arg0Value, Object arg1Value, long arg2Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                DynamicObjectLibraryImpl.putLong(arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void putDouble(DynamicObject arg0Value, Object arg1Value, double arg2Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                DynamicObjectLibraryImpl.putDouble(arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean putIfPresent(DynamicObject arg0Value, Object arg1Value, Object arg2Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.putIfPresent(arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void putWithFlags(DynamicObject arg0Value, Object arg1Value, Object arg2Value, int arg3Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                DynamicObjectLibraryImpl.putWithFlags(arg0Value, arg1Value, arg2Value, arg3Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void putConstant(DynamicObject arg0Value, Object arg1Value, Object arg2Value, int arg3Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                DynamicObjectLibraryImpl.putConstant(arg0Value, arg1Value, arg2Value, arg3Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Property getProperty(DynamicObject arg0Value, Object arg1Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.getProperty(arg0Value, arg1Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean setPropertyFlags(DynamicObject arg0Value, Object arg1Value, int arg2Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.setPropertyFlags(arg0Value, arg1Value, arg2Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean removeKey(DynamicObject arg0Value, Object arg1Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.removeKey(arg0Value, arg1Value, arg0Value.getShape(), DynamicObjectLibraryImpl.KeyCacheNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getDynamicType(DynamicObject arg0Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.getDynamicType(arg0Value, arg0Value.getShape());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean setDynamicType(DynamicObject arg0Value, Object arg1Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.setDynamicType(arg0Value, arg1Value, arg0Value.getShape(), DynamicObjectLibraryImplFactory.SetDynamicTypeNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int getShapeFlags(DynamicObject arg0Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.getShapeFlags(arg0Value, arg0Value.getShape());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean setShapeFlags(DynamicObject arg0Value, int arg1Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.setShapeFlags(arg0Value, arg1Value, arg0Value.getShape(), DynamicObjectLibraryImplFactory.SetFlagsNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isShared(DynamicObject arg0Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.isShared(arg0Value, arg0Value.getShape());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void markShared(DynamicObject arg0Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                DynamicObjectLibraryImpl.markShared(arg0Value, arg0Value.getShape(), DynamicObjectLibraryImplFactory.MakeSharedNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean updateShape(DynamicObject arg0Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.updateShape(arg0Value, arg0Value.getShape());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean resetShape(DynamicObject arg0Value, Shape arg1Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.resetShape(arg0Value, arg1Value, arg0Value.getShape(), DynamicObjectLibraryImplFactory.ResetShapeNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object[] getKeyArray(DynamicObject arg0Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.getKeyArray(arg0Value, arg0Value.getShape());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Property[] getPropertyArray(DynamicObject arg0Value) {
                assert (this.accepts(arg0Value)) : "Invalid library usage. Library does not accept given receiver.";
                return DynamicObjectLibraryImpl.getPropertyArray(arg0Value, arg0Value.getShape());
            }

            @CompilerDirectives.TruffleBoundary
            private static boolean accepts_(Object arg0Value_) {
                DynamicObject arg0Value = (DynamicObject)arg0Value_;
                return DynamicObjectLibraryImpl.accepts(arg0Value, arg0Value.getShape());
            }
        }

        @GeneratedBy(value=DynamicObjectLibraryImpl.class)
        private static final class Cached
        extends DynamicObjectLibrary {
            private final Class<? extends DynamicObject> receiverClass_;
            @Node.Child
            private DynamicObjectLibrary fallback_;
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @CompilerDirectives.CompilationFinal
            private Shape cachedShape;
            @Node.Child
            private DynamicObjectLibraryImpl.KeyCacheNode keyCache;
            @Node.Child
            private DynamicObjectLibraryImpl.SetDynamicTypeNode setDynamicTypeNode__setDynamicType_setCache_;
            @Node.Child
            private DynamicObjectLibraryImpl.SetFlagsNode setShapeFlagsNode__setShapeFlags_setCache_;
            @Node.Child
            private DynamicObjectLibraryImpl.MakeSharedNode markSharedNode__markShared_setCache_;
            @Node.Child
            private DynamicObjectLibraryImpl.ResetShapeNode resetShapeNode__resetShape_setCache_;

            protected Cached(Object receiver) {
                DynamicObject castReceiver = (DynamicObject)receiver;
                this.cachedShape = castReceiver.getShape();
                this.receiverClass_ = castReceiver.getClass();
            }

            @Override
            public boolean accepts(Object receiver) {
                return CompilerDirectives.isExact(receiver, this.receiverClass_) && this.accepts_(receiver);
            }

            private DynamicObjectLibrary getFallback_(DynamicObject receiver) {
                DynamicObjectLibrary localFallback = this.fallback_;
                if (localFallback == null) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.fallback_ = localFallback = this.insert(DYNAMIC_OBJECT_LIBRARY_.createDispatched(5));
                }
                return localFallback;
            }

            private boolean accepts_(Object arg0Value_) {
                DynamicObject arg0Value = CompilerDirectives.castExact(arg0Value_, this.receiverClass_);
                return DynamicObjectLibraryImpl.accepts(arg0Value, this.cachedShape);
            }

            @Override
            public NodeCost getCost() {
                return NodeCost.MONOMORPHIC;
            }

            @Override
            public Shape getShape(DynamicObject arg0Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    return DynamicObjectLibraryImpl.getShape(arg0Value, this.cachedShape);
                }
                return this.getFallback_(arg0Value).getShape(arg0Value);
            }

            @Override
            public Object getOrDefault(DynamicObject arg0Value, Object arg1Value, Object arg2Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 1) != 0) {
                        return DynamicObjectLibraryImpl.getOrDefault(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    return this.getOrDefaultNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
                }
                return this.getFallback_(arg0Value).getOrDefault(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object getOrDefaultNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, Object arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyCache = super.insert(this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache);
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = DynamicObjectLibraryImpl.getOrDefault(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public int getIntOrDefault(DynamicObject arg0Value, Object arg1Value, Object arg2Value) throws UnexpectedResultException {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 2) != 0) {
                        return DynamicObjectLibraryImpl.getIntOrDefault(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    return this.getIntOrDefaultNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
                }
                return this.getFallback_(arg0Value).getIntOrDefault(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private int getIntOrDefaultNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, Object arg2Value) throws UnexpectedResultException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyCache = super.insert(this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache);
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    int n = DynamicObjectLibraryImpl.getIntOrDefault(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                    return n;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public double getDoubleOrDefault(DynamicObject arg0Value, Object arg1Value, Object arg2Value) throws UnexpectedResultException {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 4) != 0) {
                        return DynamicObjectLibraryImpl.getDoubleOrDefault(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    return this.getDoubleOrDefaultNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
                }
                return this.getFallback_(arg0Value).getDoubleOrDefault(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private double getDoubleOrDefaultNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, Object arg2Value) throws UnexpectedResultException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyCache = super.insert(this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache);
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    double d = DynamicObjectLibraryImpl.getDoubleOrDefault(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                    return d;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public long getLongOrDefault(DynamicObject arg0Value, Object arg1Value, Object arg2Value) throws UnexpectedResultException {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 8) != 0) {
                        return DynamicObjectLibraryImpl.getLongOrDefault(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    return this.getLongOrDefaultNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
                }
                return this.getFallback_(arg0Value).getLongOrDefault(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private long getLongOrDefaultNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, Object arg2Value) throws UnexpectedResultException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyCache = super.insert(this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache);
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    long l = DynamicObjectLibraryImpl.getLongOrDefault(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                    return l;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean containsKey(DynamicObject arg0Value, Object arg1Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 0x10) != 0) {
                        return DynamicObjectLibraryImpl.containsKey(arg0Value, arg1Value, this.cachedShape, this.keyCache);
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    return this.containsKeyNode_AndSpecialize(arg0Value, arg1Value);
                }
                return this.getFallback_(arg0Value).containsKey(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean containsKeyNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyCache = super.insert(this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache);
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = DynamicObjectLibraryImpl.containsKey(arg0Value, arg1Value, this.cachedShape, this.keyCache);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void put(DynamicObject arg0Value, Object arg1Value, Object arg2Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 0x20) != 0) {
                        DynamicObjectLibraryImpl.put(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                        return;
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.putNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
                    return;
                }
                this.getFallback_(arg0Value).put(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void putNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, Object arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyCache = super.insert(this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache);
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    DynamicObjectLibraryImpl.put(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void putInt(DynamicObject arg0Value, Object arg1Value, int arg2Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 0x40) != 0) {
                        DynamicObjectLibraryImpl.putInt(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                        return;
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.putIntNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
                    return;
                }
                this.getFallback_(arg0Value).putInt(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void putIntNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, int arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyCache = super.insert(this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache);
                    this.state_0_ = state_0 |= 0x40;
                    lock.unlock();
                    hasLock = false;
                    DynamicObjectLibraryImpl.putInt(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void putLong(DynamicObject arg0Value, Object arg1Value, long arg2Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 0x80) != 0) {
                        DynamicObjectLibraryImpl.putLong(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                        return;
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.putLongNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
                    return;
                }
                this.getFallback_(arg0Value).putLong(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void putLongNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, long arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyCache = super.insert(this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache);
                    this.state_0_ = state_0 |= 0x80;
                    lock.unlock();
                    hasLock = false;
                    DynamicObjectLibraryImpl.putLong(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void putDouble(DynamicObject arg0Value, Object arg1Value, double arg2Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 0x100) != 0) {
                        DynamicObjectLibraryImpl.putDouble(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                        return;
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.putDoubleNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
                    return;
                }
                this.getFallback_(arg0Value).putDouble(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void putDoubleNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, double arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyCache = super.insert(this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache);
                    this.state_0_ = state_0 |= 0x100;
                    lock.unlock();
                    hasLock = false;
                    DynamicObjectLibraryImpl.putDouble(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean putIfPresent(DynamicObject arg0Value, Object arg1Value, Object arg2Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 0x200) != 0) {
                        return DynamicObjectLibraryImpl.putIfPresent(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    return this.putIfPresentNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
                }
                return this.getFallback_(arg0Value).putIfPresent(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean putIfPresentNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, Object arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyCache = super.insert(this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache);
                    this.state_0_ = state_0 |= 0x200;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = DynamicObjectLibraryImpl.putIfPresent(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void putWithFlags(DynamicObject arg0Value, Object arg1Value, Object arg2Value, int arg3Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 0x400) != 0) {
                        DynamicObjectLibraryImpl.putWithFlags(arg0Value, arg1Value, arg2Value, arg3Value, this.cachedShape, this.keyCache);
                        return;
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.putWithFlagsNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
                    return;
                }
                this.getFallback_(arg0Value).putWithFlags(arg0Value, arg1Value, arg2Value, arg3Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void putWithFlagsNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, Object arg2Value, int arg3Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyCache = super.insert(this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache);
                    this.state_0_ = state_0 |= 0x400;
                    lock.unlock();
                    hasLock = false;
                    DynamicObjectLibraryImpl.putWithFlags(arg0Value, arg1Value, arg2Value, arg3Value, this.cachedShape, this.keyCache);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void putConstant(DynamicObject arg0Value, Object arg1Value, Object arg2Value, int arg3Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 0x800) != 0) {
                        DynamicObjectLibraryImpl.putConstant(arg0Value, arg1Value, arg2Value, arg3Value, this.cachedShape, this.keyCache);
                        return;
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.putConstantNode_AndSpecialize(arg0Value, arg1Value, arg2Value, arg3Value);
                    return;
                }
                this.getFallback_(arg0Value).putConstant(arg0Value, arg1Value, arg2Value, arg3Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void putConstantNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, Object arg2Value, int arg3Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyCache = super.insert(this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache);
                    this.state_0_ = state_0 |= 0x800;
                    lock.unlock();
                    hasLock = false;
                    DynamicObjectLibraryImpl.putConstant(arg0Value, arg1Value, arg2Value, arg3Value, this.cachedShape, this.keyCache);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Property getProperty(DynamicObject arg0Value, Object arg1Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 0x1000) != 0) {
                        return DynamicObjectLibraryImpl.getProperty(arg0Value, arg1Value, this.cachedShape, this.keyCache);
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    return this.getPropertyNode_AndSpecialize(arg0Value, arg1Value);
                }
                return this.getFallback_(arg0Value).getProperty(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Property getPropertyNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyCache = super.insert(this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache);
                    this.state_0_ = state_0 |= 0x1000;
                    lock.unlock();
                    hasLock = false;
                    Property property = DynamicObjectLibraryImpl.getProperty(arg0Value, arg1Value, this.cachedShape, this.keyCache);
                    return property;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean setPropertyFlags(DynamicObject arg0Value, Object arg1Value, int arg2Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 0x2000) != 0) {
                        return DynamicObjectLibraryImpl.setPropertyFlags(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    return this.setPropertyFlagsNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
                }
                return this.getFallback_(arg0Value).setPropertyFlags(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean setPropertyFlagsNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value, int arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyCache = super.insert(this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache);
                    this.state_0_ = state_0 |= 0x2000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = DynamicObjectLibraryImpl.setPropertyFlags(arg0Value, arg1Value, arg2Value, this.cachedShape, this.keyCache);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean removeKey(DynamicObject arg0Value, Object arg1Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 0x4000) != 0) {
                        return DynamicObjectLibraryImpl.removeKey(arg0Value, arg1Value, this.cachedShape, this.keyCache);
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    return this.removeKeyNode_AndSpecialize(arg0Value, arg1Value);
                }
                return this.getFallback_(arg0Value).removeKey(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean removeKeyNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.keyCache = super.insert(this.keyCache == null ? DynamicObjectLibraryImpl.KeyCacheNode.create(arg0Value.getShape(), arg1Value) : this.keyCache);
                    this.state_0_ = state_0 |= 0x4000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = DynamicObjectLibraryImpl.removeKey(arg0Value, arg1Value, this.cachedShape, this.keyCache);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object getDynamicType(DynamicObject arg0Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    return DynamicObjectLibraryImpl.getDynamicType(arg0Value, this.cachedShape);
                }
                return this.getFallback_(arg0Value).getDynamicType(arg0Value);
            }

            @Override
            public boolean setDynamicType(DynamicObject arg0Value, Object arg1Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 0x8000) != 0) {
                        return DynamicObjectLibraryImpl.setDynamicType(arg0Value, arg1Value, this.cachedShape, this.setDynamicTypeNode__setDynamicType_setCache_);
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    return this.setDynamicTypeNode_AndSpecialize(arg0Value, arg1Value);
                }
                return this.getFallback_(arg0Value).setDynamicType(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean setDynamicTypeNode_AndSpecialize(DynamicObject arg0Value, Object arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.setDynamicTypeNode__setDynamicType_setCache_ = super.insert(DynamicObjectLibraryImplFactory.SetDynamicTypeNodeGen.create());
                    this.state_0_ = state_0 |= 0x8000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = DynamicObjectLibraryImpl.setDynamicType(arg0Value, arg1Value, this.cachedShape, this.setDynamicTypeNode__setDynamicType_setCache_);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public int getShapeFlags(DynamicObject arg0Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    return DynamicObjectLibraryImpl.getShapeFlags(arg0Value, this.cachedShape);
                }
                return this.getFallback_(arg0Value).getShapeFlags(arg0Value);
            }

            @Override
            public boolean setShapeFlags(DynamicObject arg0Value, int arg1Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 0x10000) != 0) {
                        return DynamicObjectLibraryImpl.setShapeFlags(arg0Value, arg1Value, this.cachedShape, this.setShapeFlagsNode__setShapeFlags_setCache_);
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    return this.setShapeFlagsNode_AndSpecialize(arg0Value, arg1Value);
                }
                return this.getFallback_(arg0Value).setShapeFlags(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean setShapeFlagsNode_AndSpecialize(DynamicObject arg0Value, int arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.setShapeFlagsNode__setShapeFlags_setCache_ = super.insert(DynamicObjectLibraryImplFactory.SetFlagsNodeGen.create());
                    this.state_0_ = state_0 |= 0x10000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = DynamicObjectLibraryImpl.setShapeFlags(arg0Value, arg1Value, this.cachedShape, this.setShapeFlagsNode__setShapeFlags_setCache_);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isShared(DynamicObject arg0Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    return DynamicObjectLibraryImpl.isShared(arg0Value, this.cachedShape);
                }
                return this.getFallback_(arg0Value).isShared(arg0Value);
            }

            @Override
            public void markShared(DynamicObject arg0Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 0x20000) != 0) {
                        DynamicObjectLibraryImpl.markShared(arg0Value, this.cachedShape, this.markSharedNode__markShared_setCache_);
                        return;
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.markSharedNode_AndSpecialize(arg0Value);
                    return;
                }
                this.getFallback_(arg0Value).markShared(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void markSharedNode_AndSpecialize(DynamicObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.markSharedNode__markShared_setCache_ = super.insert(DynamicObjectLibraryImplFactory.MakeSharedNodeGen.create());
                    this.state_0_ = state_0 |= 0x20000;
                    lock.unlock();
                    hasLock = false;
                    DynamicObjectLibraryImpl.markShared(arg0Value, this.cachedShape, this.markSharedNode__markShared_setCache_);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean updateShape(DynamicObject arg0Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    return DynamicObjectLibraryImpl.updateShape(arg0Value, this.cachedShape);
                }
                return this.getFallback_(arg0Value).updateShape(arg0Value);
            }

            @Override
            public boolean resetShape(DynamicObject arg0Value, Shape arg1Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    int state_0 = this.state_0_;
                    if ((state_0 & 0x40000) != 0) {
                        return DynamicObjectLibraryImpl.resetShape(arg0Value, arg1Value, this.cachedShape, this.resetShapeNode__resetShape_setCache_);
                    }
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    return this.resetShapeNode_AndSpecialize(arg0Value, arg1Value);
                }
                return this.getFallback_(arg0Value).resetShape(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean resetShapeNode_AndSpecialize(DynamicObject arg0Value, Shape arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.resetShapeNode__resetShape_setCache_ = super.insert(DynamicObjectLibraryImplFactory.ResetShapeNodeGen.create());
                    this.state_0_ = state_0 |= 0x40000;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = DynamicObjectLibraryImpl.resetShape(arg0Value, arg1Value, this.cachedShape, this.resetShapeNode__resetShape_setCache_);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object[] getKeyArray(DynamicObject arg0Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    return DynamicObjectLibraryImpl.getKeyArray(arg0Value, this.cachedShape);
                }
                return this.getFallback_(arg0Value).getKeyArray(arg0Value);
            }

            @Override
            public Property[] getPropertyArray(DynamicObject arg0Value) {
                assert (CompilerDirectives.isExact(arg0Value, this.receiverClass_)) : "Invalid library usage. Library does not accept given receiver.";
                if (this.accepts(arg0Value)) {
                    assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
                    return DynamicObjectLibraryImpl.getPropertyArray(arg0Value, this.cachedShape);
                }
                return this.getFallback_(arg0Value).getPropertyArray(arg0Value);
            }
        }
    }

    @GeneratedBy(value=DynamicObjectLibraryImpl.class)
    public static final class DynamicObjectLibraryProvider
    implements DefaultExportProvider {
        @Override
        public String getLibraryClassName() {
            return "com.oracle.truffle.api.object.DynamicObjectLibrary";
        }

        @Override
        public Class<?> getDefaultExport() {
            return DynamicObjectLibraryImpl.class;
        }

        @Override
        public Class<?> getReceiverClass() {
            return DynamicObject.class;
        }

        @Override
        public int getPriority() {
            return 10;
        }
    }
}

