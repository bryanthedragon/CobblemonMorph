
package com.oracle.truffle.api.object;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.library.Library;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.library.Message;
import com.oracle.truffle.api.library.ReflectionLibrary;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.object.DynamicObject;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Property;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.api.utilities.FinalBitSet;
import java.util.Arrays;
import java.util.BitSet;
import java.util.Collections;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=DynamicObjectLibrary.class)
final class DynamicObjectLibraryGen
extends LibraryFactory<DynamicObjectLibrary> {
    private static final Class<DynamicObjectLibrary> LIBRARY_CLASS = DynamicObjectLibraryGen.lazyLibraryClass();
    private static final Message GET_SHAPE = new MessageImpl("getShape", 0, Shape.class, DynamicObject.class);
    private static final Message GET_OR_DEFAULT = new MessageImpl("getOrDefault", 1, Object.class, DynamicObject.class, Object.class, Object.class);
    private static final Message GET_INT_OR_DEFAULT = new MessageImpl("getIntOrDefault", 2, Integer.TYPE, DynamicObject.class, Object.class, Object.class);
    private static final Message GET_DOUBLE_OR_DEFAULT = new MessageImpl("getDoubleOrDefault", 3, Double.TYPE, DynamicObject.class, Object.class, Object.class);
    private static final Message GET_LONG_OR_DEFAULT = new MessageImpl("getLongOrDefault", 4, Long.TYPE, DynamicObject.class, Object.class, Object.class);
    private static final Message PUT = new MessageImpl("put", 5, Void.TYPE, DynamicObject.class, Object.class, Object.class);
    private static final Message PUT_INT = new MessageImpl("putInt", 6, Void.TYPE, DynamicObject.class, Object.class, Integer.TYPE);
    private static final Message PUT_DOUBLE = new MessageImpl("putDouble", 7, Void.TYPE, DynamicObject.class, Object.class, Double.TYPE);
    private static final Message PUT_LONG = new MessageImpl("putLong", 8, Void.TYPE, DynamicObject.class, Object.class, Long.TYPE);
    private static final Message PUT_IF_PRESENT = new MessageImpl("putIfPresent", 9, Boolean.TYPE, DynamicObject.class, Object.class, Object.class);
    private static final Message PUT_WITH_FLAGS = new MessageImpl("putWithFlags", 10, Void.TYPE, DynamicObject.class, Object.class, Object.class, Integer.TYPE);
    private static final Message PUT_CONSTANT = new MessageImpl("putConstant", 11, Void.TYPE, DynamicObject.class, Object.class, Object.class, Integer.TYPE);
    private static final Message REMOVE_KEY = new MessageImpl("removeKey", 12, Boolean.TYPE, DynamicObject.class, Object.class);
    private static final Message SET_DYNAMIC_TYPE = new MessageImpl("setDynamicType", 13, Boolean.TYPE, DynamicObject.class, Object.class);
    private static final Message GET_DYNAMIC_TYPE = new MessageImpl("getDynamicType", 14, Object.class, DynamicObject.class);
    private static final Message CONTAINS_KEY = new MessageImpl("containsKey", 15, Boolean.TYPE, DynamicObject.class, Object.class);
    private static final Message GET_SHAPE_FLAGS = new MessageImpl("getShapeFlags", 16, Integer.TYPE, DynamicObject.class);
    private static final Message SET_SHAPE_FLAGS = new MessageImpl("setShapeFlags", 17, Boolean.TYPE, DynamicObject.class, Integer.TYPE);
    private static final Message GET_PROPERTY = new MessageImpl("getProperty", 18, Property.class, DynamicObject.class, Object.class);
    private static final Message SET_PROPERTY_FLAGS = new MessageImpl("setPropertyFlags", 19, Boolean.TYPE, DynamicObject.class, Object.class, Integer.TYPE);
    private static final Message MARK_SHARED = new MessageImpl("markShared", 20, Void.TYPE, DynamicObject.class);
    private static final Message IS_SHARED = new MessageImpl("isShared", 21, Boolean.TYPE, DynamicObject.class);
    private static final Message UPDATE_SHAPE = new MessageImpl("updateShape", 22, Boolean.TYPE, DynamicObject.class);
    private static final Message RESET_SHAPE = new MessageImpl("resetShape", 23, Boolean.TYPE, DynamicObject.class, Shape.class);
    private static final Message GET_KEY_ARRAY = new MessageImpl("getKeyArray", 24, Object[].class, DynamicObject.class);
    private static final Message GET_PROPERTY_ARRAY = new MessageImpl("getPropertyArray", 25, Property[].class, DynamicObject.class);
    private static final DynamicObjectLibraryGen INSTANCE = new DynamicObjectLibraryGen();

    private DynamicObjectLibraryGen() {
        super(LIBRARY_CLASS, Collections.unmodifiableList(Arrays.asList(GET_SHAPE, GET_OR_DEFAULT, GET_INT_OR_DEFAULT, GET_DOUBLE_OR_DEFAULT, GET_LONG_OR_DEFAULT, PUT, PUT_INT, PUT_DOUBLE, PUT_LONG, PUT_IF_PRESENT, PUT_WITH_FLAGS, PUT_CONSTANT, REMOVE_KEY, SET_DYNAMIC_TYPE, GET_DYNAMIC_TYPE, CONTAINS_KEY, GET_SHAPE_FLAGS, SET_SHAPE_FLAGS, GET_PROPERTY, SET_PROPERTY_FLAGS, MARK_SHARED, IS_SHARED, UPDATE_SHAPE, RESET_SHAPE, GET_KEY_ARRAY, GET_PROPERTY_ARRAY)));
    }

    @Override
    protected Class<?> getDefaultClass(Object receiver) {
        return DynamicObjectLibrary.class;
    }

    @Override
    protected DynamicObjectLibrary createProxy(ReflectionLibrary library) {
        return new Proxy(library);
    }

    @Override
    protected FinalBitSet createMessageBitSet(Message ... messages) {
        BitSet bitSet = new BitSet(2);
        for (Message message : messages) {
            bitSet.set(message.getId());
        }
        return FinalBitSet.valueOf(bitSet);
    }

    @Override
    protected DynamicObjectLibrary createDelegate(DynamicObjectLibrary delegateLibrary) {
        return new Delegate(delegateLibrary);
    }

    @Override
    protected Object genericDispatch(Library originalLib, Object receiver, Message message, Object[] args, int offset) throws Exception {
        DynamicObjectLibrary lib = (DynamicObjectLibrary)originalLib;
        if (message.getParameterCount() - 1 != args.length - offset) {
            CompilerDirectives.transferToInterpreterAndInvalidate();
            throw new IllegalArgumentException("Invalid number of arguments.");
        }
        switch (message.getId()) {
            case 0: {
                return lib.getShape((DynamicObject)receiver);
            }
            case 1: {
                return lib.getOrDefault((DynamicObject)receiver, args[offset], args[offset + 1]);
            }
            case 2: {
                return lib.getIntOrDefault((DynamicObject)receiver, args[offset], args[offset + 1]);
            }
            case 3: {
                return lib.getDoubleOrDefault((DynamicObject)receiver, args[offset], args[offset + 1]);
            }
            case 4: {
                return lib.getLongOrDefault((DynamicObject)receiver, args[offset], args[offset + 1]);
            }
            case 5: {
                lib.put((DynamicObject)receiver, args[offset], args[offset + 1]);
                return null;
            }
            case 6: {
                lib.putInt((DynamicObject)receiver, args[offset], (Integer)args[offset + 1]);
                return null;
            }
            case 7: {
                lib.putDouble((DynamicObject)receiver, args[offset], (Double)args[offset + 1]);
                return null;
            }
            case 8: {
                lib.putLong((DynamicObject)receiver, args[offset], (Long)args[offset + 1]);
                return null;
            }
            case 9: {
                return lib.putIfPresent((DynamicObject)receiver, args[offset], args[offset + 1]);
            }
            case 10: {
                lib.putWithFlags((DynamicObject)receiver, args[offset], args[offset + 1], (Integer)args[offset + 2]);
                return null;
            }
            case 11: {
                lib.putConstant((DynamicObject)receiver, args[offset], args[offset + 1], (Integer)args[offset + 2]);
                return null;
            }
            case 12: {
                return lib.removeKey((DynamicObject)receiver, args[offset]);
            }
            case 13: {
                return lib.setDynamicType((DynamicObject)receiver, args[offset]);
            }
            case 14: {
                return lib.getDynamicType((DynamicObject)receiver);
            }
            case 15: {
                return lib.containsKey((DynamicObject)receiver, args[offset]);
            }
            case 16: {
                return lib.getShapeFlags((DynamicObject)receiver);
            }
            case 17: {
                return lib.setShapeFlags((DynamicObject)receiver, (Integer)args[offset]);
            }
            case 18: {
                return lib.getProperty((DynamicObject)receiver, args[offset]);
            }
            case 19: {
                return lib.setPropertyFlags((DynamicObject)receiver, args[offset], (Integer)args[offset + 1]);
            }
            case 20: {
                lib.markShared((DynamicObject)receiver);
                return null;
            }
            case 21: {
                return lib.isShared((DynamicObject)receiver);
            }
            case 22: {
                return lib.updateShape((DynamicObject)receiver);
            }
            case 23: {
                return lib.resetShape((DynamicObject)receiver, (Shape)args[offset]);
            }
            case 24: {
                return lib.getKeyArray((DynamicObject)receiver);
            }
            case 25: {
                return lib.getPropertyArray((DynamicObject)receiver);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        throw new AbstractMethodError(message.toString());
    }

    @Override
    protected DynamicObjectLibrary createDispatchImpl(int limit) {
        return new CachedDispatchFirst(null, null, limit);
    }

    @Override
    protected DynamicObjectLibrary createUncachedDispatch() {
        return new UncachedDispatch();
    }

    private static Class<DynamicObjectLibrary> lazyLibraryClass() {
        try {
            return Class.forName("com.oracle.truffle.api.object.DynamicObjectLibrary", false, DynamicObjectLibraryGen.class.getClassLoader());
        }
        catch (ClassNotFoundException e) {
            throw CompilerDirectives.shouldNotReachHere(e);
        }
    }

    static {
        LibraryExport.register(LIBRARY_CLASS, new Default());
        LibraryFactory.register(LIBRARY_CLASS, INSTANCE);
    }

    @GeneratedBy(value=DynamicObjectLibrary.class)
    private static abstract class CachedDispatch
    extends DynamicObjectLibrary {
        @Node.Child
        DynamicObjectLibrary library;
        @Node.Child
        CachedDispatch next;

        CachedDispatch(DynamicObjectLibrary library, CachedDispatch next) {
            this.library = library;
            this.next = next;
        }

        abstract int getLimit();

        @Override
        @ExplodeLoop
        public Shape getShape(DynamicObject receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getShape(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getOrDefault(DynamicObject receiver_, Object key, Object defaultValue) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getOrDefault(receiver_, key, defaultValue);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public int getIntOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getIntOrDefault(receiver_, key, defaultValue);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public double getDoubleOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getDoubleOrDefault(receiver_, key, defaultValue);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public long getLongOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getLongOrDefault(receiver_, key, defaultValue);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void put(DynamicObject receiver_, Object key, Object value2) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.put(receiver_, key, value2);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void putInt(DynamicObject receiver_, Object key, int value2) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.putInt(receiver_, key, value2);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void putDouble(DynamicObject receiver_, Object key, double value2) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.putDouble(receiver_, key, value2);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void putLong(DynamicObject receiver_, Object key, long value2) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.putLong(receiver_, key, value2);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean putIfPresent(DynamicObject receiver_, Object key, Object value2) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.putIfPresent(receiver_, key, value2);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void putWithFlags(DynamicObject receiver_, Object key, Object value2, int flags) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.putWithFlags(receiver_, key, value2, flags);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void putConstant(DynamicObject receiver_, Object key, Object value2, int flags) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.putConstant(receiver_, key, value2, flags);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean removeKey(DynamicObject receiver_, Object key) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.removeKey(receiver_, key);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean setDynamicType(DynamicObject receiver_, Object type) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.setDynamicType(receiver_, type);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object getDynamicType(DynamicObject receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getDynamicType(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean containsKey(DynamicObject receiver_, Object key) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.containsKey(receiver_, key);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public int getShapeFlags(DynamicObject receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getShapeFlags(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean setShapeFlags(DynamicObject receiver_, int flags) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.setShapeFlags(receiver_, flags);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Property getProperty(DynamicObject receiver_, Object key) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getProperty(receiver_, key);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean setPropertyFlags(DynamicObject receiver_, Object key, int propertyFlags) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.setPropertyFlags(receiver_, key, propertyFlags);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public void markShared(DynamicObject receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    thisLibrary.markShared(receiver_);
                    return;
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean isShared(DynamicObject receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.isShared(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean updateShape(DynamicObject receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.updateShape(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public boolean resetShape(DynamicObject receiver_, Shape otherShape) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.resetShape(receiver_, otherShape);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Object[] getKeyArray(DynamicObject receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getKeyArray(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        @ExplodeLoop
        public Property[] getPropertyArray(DynamicObject receiver_) {
            while (true) {
                CachedDispatch current = this;
                do {
                    DynamicObjectLibrary thisLibrary;
                    if ((thisLibrary = current.library) == null || !thisLibrary.accepts(receiver_)) continue;
                    return thisLibrary.getPropertyArray(receiver_);
                } while ((current = current.next) != null);
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.specialize(receiver_);
            }
        }

        @Override
        public boolean accepts(Object receiver_) {
            return true;
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void specialize(DynamicObject receiver_) {
            Lock lock = this.getLock();
            lock.lock();
            try {
                CachedDispatch current = this;
                DynamicObjectLibrary thisLibrary = current.library;
                if (thisLibrary == null) {
                    this.library = this.insert((DynamicObjectLibrary)INSTANCE.create(receiver_));
                } else {
                    int count = 0;
                    do {
                        DynamicObjectLibrary currentLibrary;
                        if ((currentLibrary = current.library) != null && currentLibrary.accepts(receiver_)) {
                            return;
                        }
                        ++count;
                    } while ((current = current.next) != null);
                    if (count >= this.getLimit()) {
                        this.library = this.insert(new CachedToUncachedDispatch());
                        this.next = null;
                    } else {
                        this.next = this.insert(new CachedDispatchNext((DynamicObjectLibrary)INSTANCE.create(receiver_), this.next));
                    }
                }
            }
            finally {
                lock.unlock();
            }
        }
    }

    @GeneratedBy(value=DynamicObjectLibrary.class)
    private static final class CachedDispatchFirst
    extends CachedDispatch {
        private final int limit_;

        CachedDispatchFirst(DynamicObjectLibrary library, CachedDispatch next, int limit_) {
            super(library, next);
            this.limit_ = limit_;
        }

        @Override
        int getLimit() {
            return this.limit_;
        }

        @Override
        public NodeCost getCost() {
            if (this.library instanceof CachedToUncachedDispatch) {
                return NodeCost.MEGAMORPHIC;
            }
            CachedDispatch current = this;
            int count = 0;
            do {
                if (current.library == null) continue;
                ++count;
            } while ((current = current.next) != null);
            return NodeCost.fromCount(count);
        }
    }

    @GeneratedBy(value=DynamicObjectLibrary.class)
    private static final class CachedDispatchNext
    extends CachedDispatch {
        CachedDispatchNext(DynamicObjectLibrary library, CachedDispatch next) {
            super(library, next);
        }

        @Override
        int getLimit() {
            throw CompilerDirectives.shouldNotReachHere();
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }
    }

    @GeneratedBy(value=DynamicObjectLibrary.class)
    @DenyReplace
    private static final class UncachedDispatch
    extends DynamicObjectLibrary {
        private UncachedDispatch() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Shape getShape(DynamicObject receiver_) {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getShape(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getOrDefault(DynamicObject receiver_, Object key, Object defaultValue) {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getOrDefault(receiver_, key, defaultValue);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public int getIntOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getIntOrDefault(receiver_, key, defaultValue);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public double getDoubleOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getDoubleOrDefault(receiver_, key, defaultValue);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public long getLongOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getLongOrDefault(receiver_, key, defaultValue);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void put(DynamicObject receiver_, Object key, Object value2) {
            ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).put(receiver_, key, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void putInt(DynamicObject receiver_, Object key, int value2) {
            ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).putInt(receiver_, key, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void putDouble(DynamicObject receiver_, Object key, double value2) {
            ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).putDouble(receiver_, key, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void putLong(DynamicObject receiver_, Object key, long value2) {
            ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).putLong(receiver_, key, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean putIfPresent(DynamicObject receiver_, Object key, Object value2) {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).putIfPresent(receiver_, key, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void putWithFlags(DynamicObject receiver_, Object key, Object value2, int flags) {
            ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).putWithFlags(receiver_, key, value2, flags);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void putConstant(DynamicObject receiver_, Object key, Object value2, int flags) {
            ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).putConstant(receiver_, key, value2, flags);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean removeKey(DynamicObject receiver_, Object key) {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).removeKey(receiver_, key);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean setDynamicType(DynamicObject receiver_, Object type) {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).setDynamicType(receiver_, type);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getDynamicType(DynamicObject receiver_) {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getDynamicType(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean containsKey(DynamicObject receiver_, Object key) {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).containsKey(receiver_, key);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public int getShapeFlags(DynamicObject receiver_) {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getShapeFlags(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean setShapeFlags(DynamicObject receiver_, int flags) {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).setShapeFlags(receiver_, flags);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Property getProperty(DynamicObject receiver_, Object key) {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getProperty(receiver_, key);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean setPropertyFlags(DynamicObject receiver_, Object key, int propertyFlags) {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).setPropertyFlags(receiver_, key, propertyFlags);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void markShared(DynamicObject receiver_) {
            ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).markShared(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isShared(DynamicObject receiver_) {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).isShared(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean updateShape(DynamicObject receiver_) {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).updateShape(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean resetShape(DynamicObject receiver_, Shape otherShape) {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).resetShape(receiver_, otherShape);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object[] getKeyArray(DynamicObject receiver_) {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getKeyArray(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Property[] getPropertyArray(DynamicObject receiver_) {
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getPropertyArray(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean accepts(Object receiver_) {
            return true;
        }

        @Override
        public boolean isAdoptable() {
            return false;
        }
    }

    @GeneratedBy(value=DynamicObjectLibrary.class)
    private static final class CachedToUncachedDispatch
    extends DynamicObjectLibrary {
        private CachedToUncachedDispatch() {
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Shape getShape(DynamicObject receiver_) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getShape(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getOrDefault(DynamicObject receiver_, Object key, Object defaultValue) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getOrDefault(receiver_, key, defaultValue);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public int getIntOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getIntOrDefault(receiver_, key, defaultValue);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public double getDoubleOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getDoubleOrDefault(receiver_, key, defaultValue);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public long getLongOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getLongOrDefault(receiver_, key, defaultValue);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void put(DynamicObject receiver_, Object key, Object value2) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).put(receiver_, key, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void putInt(DynamicObject receiver_, Object key, int value2) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).putInt(receiver_, key, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void putDouble(DynamicObject receiver_, Object key, double value2) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).putDouble(receiver_, key, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void putLong(DynamicObject receiver_, Object key, long value2) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).putLong(receiver_, key, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean putIfPresent(DynamicObject receiver_, Object key, Object value2) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).putIfPresent(receiver_, key, value2);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void putWithFlags(DynamicObject receiver_, Object key, Object value2, int flags) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).putWithFlags(receiver_, key, value2, flags);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void putConstant(DynamicObject receiver_, Object key, Object value2, int flags) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).putConstant(receiver_, key, value2, flags);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean removeKey(DynamicObject receiver_, Object key) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).removeKey(receiver_, key);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean setDynamicType(DynamicObject receiver_, Object type) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).setDynamicType(receiver_, type);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object getDynamicType(DynamicObject receiver_) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getDynamicType(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean containsKey(DynamicObject receiver_, Object key) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).containsKey(receiver_, key);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public int getShapeFlags(DynamicObject receiver_) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getShapeFlags(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean setShapeFlags(DynamicObject receiver_, int flags) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).setShapeFlags(receiver_, flags);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Property getProperty(DynamicObject receiver_, Object key) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getProperty(receiver_, key);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean setPropertyFlags(DynamicObject receiver_, Object key, int propertyFlags) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).setPropertyFlags(receiver_, key, propertyFlags);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public void markShared(DynamicObject receiver_) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).markShared(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean isShared(DynamicObject receiver_) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).isShared(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean updateShape(DynamicObject receiver_) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).updateShape(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public boolean resetShape(DynamicObject receiver_, Shape otherShape) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).resetShape(receiver_, otherShape);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object[] getKeyArray(DynamicObject receiver_) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getKeyArray(receiver_);
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Property[] getPropertyArray(DynamicObject receiver_) {
            assert (this.getRootNode() != null) : "Invalid library usage. Cached library must be adopted by a RootNode before it is executed.";
            return ((DynamicObjectLibrary)INSTANCE.getUncached(receiver_)).getPropertyArray(receiver_);
        }

        @Override
        public boolean accepts(Object receiver_) {
            return true;
        }
    }

    @GeneratedBy(value=DynamicObjectLibrary.class)
    private static final class Delegate
    extends DynamicObjectLibrary {
        @Node.Child
        private DynamicObjectLibrary delegateLibrary;

        Delegate(DynamicObjectLibrary delegateLibrary) {
            this.delegateLibrary = delegateLibrary;
        }

        @Override
        public Shape getShape(DynamicObject receiver_) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 0)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getShape((DynamicObject)delegate);
            }
            return this.delegateLibrary.getShape(receiver_);
        }

        @Override
        public Object getOrDefault(DynamicObject receiver_, Object key, Object defaultValue) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 1)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getOrDefault((DynamicObject)delegate, key, defaultValue);
            }
            return this.delegateLibrary.getOrDefault(receiver_, key, defaultValue);
        }

        @Override
        public int getIntOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 2)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getIntOrDefault((DynamicObject)delegate, key, defaultValue);
            }
            return this.delegateLibrary.getIntOrDefault(receiver_, key, defaultValue);
        }

        @Override
        public double getDoubleOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 3)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getDoubleOrDefault((DynamicObject)delegate, key, defaultValue);
            }
            return this.delegateLibrary.getDoubleOrDefault(receiver_, key, defaultValue);
        }

        @Override
        public long getLongOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 4)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getLongOrDefault((DynamicObject)delegate, key, defaultValue);
            }
            return this.delegateLibrary.getLongOrDefault(receiver_, key, defaultValue);
        }

        @Override
        public void put(DynamicObject receiver_, Object key, Object value2) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 5)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).put((DynamicObject)delegate, key, value2);
                return;
            }
            this.delegateLibrary.put(receiver_, key, value2);
        }

        @Override
        public void putInt(DynamicObject receiver_, Object key, int value2) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 6)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).putInt((DynamicObject)delegate, key, value2);
                return;
            }
            this.delegateLibrary.putInt(receiver_, key, value2);
        }

        @Override
        public void putDouble(DynamicObject receiver_, Object key, double value2) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 7)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).putDouble((DynamicObject)delegate, key, value2);
                return;
            }
            this.delegateLibrary.putDouble(receiver_, key, value2);
        }

        @Override
        public void putLong(DynamicObject receiver_, Object key, long value2) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 8)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).putLong((DynamicObject)delegate, key, value2);
                return;
            }
            this.delegateLibrary.putLong(receiver_, key, value2);
        }

        @Override
        public boolean putIfPresent(DynamicObject receiver_, Object key, Object value2) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 9)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).putIfPresent((DynamicObject)delegate, key, value2);
            }
            return this.delegateLibrary.putIfPresent(receiver_, key, value2);
        }

        @Override
        public void putWithFlags(DynamicObject receiver_, Object key, Object value2, int flags) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 10)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).putWithFlags((DynamicObject)delegate, key, value2, flags);
                return;
            }
            this.delegateLibrary.putWithFlags(receiver_, key, value2, flags);
        }

        @Override
        public void putConstant(DynamicObject receiver_, Object key, Object value2, int flags) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 11)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).putConstant((DynamicObject)delegate, key, value2, flags);
                return;
            }
            this.delegateLibrary.putConstant(receiver_, key, value2, flags);
        }

        @Override
        public boolean removeKey(DynamicObject receiver_, Object key) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 12)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).removeKey((DynamicObject)delegate, key);
            }
            return this.delegateLibrary.removeKey(receiver_, key);
        }

        @Override
        public boolean setDynamicType(DynamicObject receiver_, Object type) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 13)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).setDynamicType((DynamicObject)delegate, type);
            }
            return this.delegateLibrary.setDynamicType(receiver_, type);
        }

        @Override
        public Object getDynamicType(DynamicObject receiver_) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 14)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getDynamicType((DynamicObject)delegate);
            }
            return this.delegateLibrary.getDynamicType(receiver_);
        }

        @Override
        public boolean containsKey(DynamicObject receiver_, Object key) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 15)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).containsKey((DynamicObject)delegate, key);
            }
            return this.delegateLibrary.containsKey(receiver_, key);
        }

        @Override
        public int getShapeFlags(DynamicObject receiver_) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 16)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getShapeFlags((DynamicObject)delegate);
            }
            return this.delegateLibrary.getShapeFlags(receiver_);
        }

        @Override
        public boolean setShapeFlags(DynamicObject receiver_, int flags) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 17)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).setShapeFlags((DynamicObject)delegate, flags);
            }
            return this.delegateLibrary.setShapeFlags(receiver_, flags);
        }

        @Override
        public Property getProperty(DynamicObject receiver_, Object key) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 18)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getProperty((DynamicObject)delegate, key);
            }
            return this.delegateLibrary.getProperty(receiver_, key);
        }

        @Override
        public boolean setPropertyFlags(DynamicObject receiver_, Object key, int propertyFlags) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 19)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).setPropertyFlags((DynamicObject)delegate, key, propertyFlags);
            }
            return this.delegateLibrary.setPropertyFlags(receiver_, key, propertyFlags);
        }

        @Override
        public void markShared(DynamicObject receiver_) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 20)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).markShared((DynamicObject)delegate);
                return;
            }
            this.delegateLibrary.markShared(receiver_);
        }

        @Override
        public boolean isShared(DynamicObject receiver_) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 21)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).isShared((DynamicObject)delegate);
            }
            return this.delegateLibrary.isShared(receiver_);
        }

        @Override
        public boolean updateShape(DynamicObject receiver_) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 22)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).updateShape((DynamicObject)delegate);
            }
            return this.delegateLibrary.updateShape(receiver_);
        }

        @Override
        public boolean resetShape(DynamicObject receiver_, Shape otherShape) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 23)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).resetShape((DynamicObject)delegate, otherShape);
            }
            return this.delegateLibrary.resetShape(receiver_, otherShape);
        }

        @Override
        public Object[] getKeyArray(DynamicObject receiver_) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 24)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getKeyArray((DynamicObject)delegate);
            }
            return this.delegateLibrary.getKeyArray(receiver_);
        }

        @Override
        public Property[] getPropertyArray(DynamicObject receiver_) {
            if (DynamicObjectLibraryGen.isDelegated(this.delegateLibrary, 25)) {
                Object delegate = DynamicObjectLibraryGen.readDelegate(this.delegateLibrary, receiver_);
                return ((DynamicObjectLibrary)DynamicObjectLibraryGen.getDelegateLibrary(this.delegateLibrary, delegate)).getPropertyArray((DynamicObject)delegate);
            }
            return this.delegateLibrary.getPropertyArray(receiver_);
        }

        @Override
        public boolean accepts(Object receiver_) {
            return this.delegateLibrary.accepts(receiver_);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.NONE;
        }

        @Override
        public boolean isAdoptable() {
            return this.delegateLibrary.isAdoptable();
        }
    }

    @GeneratedBy(value=DynamicObjectLibrary.class)
    private static final class Proxy
    extends DynamicObjectLibrary {
        @Node.Child
        private ReflectionLibrary lib;

        Proxy(ReflectionLibrary lib) {
            this.lib = lib;
        }

        @Override
        public Shape getShape(DynamicObject receiver_) {
            try {
                return (Shape)this.lib.send(receiver_, GET_SHAPE, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getOrDefault(DynamicObject receiver_, Object key, Object defaultValue) {
            try {
                return this.lib.send(receiver_, GET_OR_DEFAULT, key, defaultValue);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public int getIntOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
            try {
                return (Integer)this.lib.send(receiver_, GET_INT_OR_DEFAULT, key, defaultValue);
            }
            catch (UnexpectedResultException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public double getDoubleOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
            try {
                return (Double)this.lib.send(receiver_, GET_DOUBLE_OR_DEFAULT, key, defaultValue);
            }
            catch (UnexpectedResultException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public long getLongOrDefault(DynamicObject receiver_, Object key, Object defaultValue) throws UnexpectedResultException {
            try {
                return (Long)this.lib.send(receiver_, GET_LONG_OR_DEFAULT, key, defaultValue);
            }
            catch (UnexpectedResultException | RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void put(DynamicObject receiver_, Object key, Object value2) {
            try {
                this.lib.send(receiver_, PUT, key, value2);
                return;
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void putInt(DynamicObject receiver_, Object key, int value2) {
            try {
                this.lib.send(receiver_, PUT_INT, key, value2);
                return;
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void putDouble(DynamicObject receiver_, Object key, double value2) {
            try {
                this.lib.send(receiver_, PUT_DOUBLE, key, value2);
                return;
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void putLong(DynamicObject receiver_, Object key, long value2) {
            try {
                this.lib.send(receiver_, PUT_LONG, key, value2);
                return;
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean putIfPresent(DynamicObject receiver_, Object key, Object value2) {
            try {
                return (Boolean)this.lib.send(receiver_, PUT_IF_PRESENT, key, value2);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void putWithFlags(DynamicObject receiver_, Object key, Object value2, int flags) {
            try {
                this.lib.send(receiver_, PUT_WITH_FLAGS, key, value2, flags);
                return;
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void putConstant(DynamicObject receiver_, Object key, Object value2, int flags) {
            try {
                this.lib.send(receiver_, PUT_CONSTANT, key, value2, flags);
                return;
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean removeKey(DynamicObject receiver_, Object key) {
            try {
                return (Boolean)this.lib.send(receiver_, REMOVE_KEY, key);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean setDynamicType(DynamicObject receiver_, Object type) {
            try {
                return (Boolean)this.lib.send(receiver_, SET_DYNAMIC_TYPE, type);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object getDynamicType(DynamicObject receiver_) {
            try {
                return this.lib.send(receiver_, GET_DYNAMIC_TYPE, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean containsKey(DynamicObject receiver_, Object key) {
            try {
                return (Boolean)this.lib.send(receiver_, CONTAINS_KEY, key);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public int getShapeFlags(DynamicObject receiver_) {
            try {
                return (Integer)this.lib.send(receiver_, GET_SHAPE_FLAGS, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean setShapeFlags(DynamicObject receiver_, int flags) {
            try {
                return (Boolean)this.lib.send(receiver_, SET_SHAPE_FLAGS, flags);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Property getProperty(DynamicObject receiver_, Object key) {
            try {
                return (Property)this.lib.send(receiver_, GET_PROPERTY, key);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean setPropertyFlags(DynamicObject receiver_, Object key, int propertyFlags) {
            try {
                return (Boolean)this.lib.send(receiver_, SET_PROPERTY_FLAGS, key, propertyFlags);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public void markShared(DynamicObject receiver_) {
            try {
                this.lib.send(receiver_, MARK_SHARED, new Object[0]);
                return;
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean isShared(DynamicObject receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, IS_SHARED, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean updateShape(DynamicObject receiver_) {
            try {
                return (Boolean)this.lib.send(receiver_, UPDATE_SHAPE, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean resetShape(DynamicObject receiver_, Shape otherShape) {
            try {
                return (Boolean)this.lib.send(receiver_, RESET_SHAPE, otherShape);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Object[] getKeyArray(DynamicObject receiver_) {
            try {
                return (Object[])this.lib.send(receiver_, GET_KEY_ARRAY, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public Property[] getPropertyArray(DynamicObject receiver_) {
            try {
                return (Property[])this.lib.send(receiver_, GET_PROPERTY_ARRAY, new Object[0]);
            }
            catch (RuntimeException e_) {
                throw e_;
            }
            catch (Exception e_) {
                throw CompilerDirectives.shouldNotReachHere(e_);
            }
        }

        @Override
        public boolean accepts(Object receiver_) {
            return this.lib.accepts(receiver_);
        }
    }

    @GeneratedBy(value=DynamicObjectLibrary.class)
    private static class MessageImpl
    extends Message {
        MessageImpl(String name, int index, Class<?> returnType, Class<?> ... parameters) {
            super(LIBRARY_CLASS, name, index, returnType, parameters);
        }
    }

    @GeneratedBy(value=DynamicObjectLibrary.class)
    private static final class Default
    extends LibraryExport<DynamicObjectLibrary> {
        private Default() {
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

        @GeneratedBy(value=DynamicObjectLibrary.class)
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
                return CompilerDirectives.isExact(receiver, this.receiverClass_);
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
            public Shape getShape(DynamicObject receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getOrDefault(DynamicObject receiver, Object key, Object defaultValue) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int getIntOrDefault(DynamicObject receiver, Object key, Object defaultValue) throws UnexpectedResultException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getIntOrDefault(receiver, key, defaultValue);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public double getDoubleOrDefault(DynamicObject receiver, Object key, Object defaultValue) throws UnexpectedResultException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getDoubleOrDefault(receiver, key, defaultValue);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long getLongOrDefault(DynamicObject receiver, Object key, Object defaultValue) throws UnexpectedResultException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getLongOrDefault(receiver, key, defaultValue);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void put(DynamicObject receiver, Object key, Object value2) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void putInt(DynamicObject receiver, Object key, int value2) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.putInt(receiver, key, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void putDouble(DynamicObject receiver, Object key, double value2) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.putDouble(receiver, key, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void putLong(DynamicObject receiver, Object key, long value2) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.putLong(receiver, key, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean putIfPresent(DynamicObject receiver, Object key, Object value2) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void putWithFlags(DynamicObject receiver, Object key, Object value2, int flags) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void putConstant(DynamicObject receiver, Object key, Object value2, int flags) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean removeKey(DynamicObject receiver, Object key) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean setDynamicType(DynamicObject receiver, Object type) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getDynamicType(DynamicObject receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean containsKey(DynamicObject receiver, Object key) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int getShapeFlags(DynamicObject receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean setShapeFlags(DynamicObject receiver, int flags) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Property getProperty(DynamicObject receiver, Object key) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean setPropertyFlags(DynamicObject receiver, Object key, int propertyFlags) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void markShared(DynamicObject receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isShared(DynamicObject receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean updateShape(DynamicObject receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean resetShape(DynamicObject receiver, Shape otherShape) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object[] getKeyArray(DynamicObject receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Property[] getPropertyArray(DynamicObject receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }
        }

        @GeneratedBy(value=DynamicObjectLibrary.class)
        private static final class Cached
        extends DynamicObjectLibrary {
            private final Class<? extends DynamicObject> receiverClass_;

            protected Cached(Object receiver) {
                DynamicObject castReceiver = (DynamicObject)receiver;
                this.receiverClass_ = castReceiver.getClass();
            }

            @Override
            public boolean accepts(Object receiver) {
                return CompilerDirectives.isExact(receiver, this.receiverClass_);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Shape getShape(DynamicObject receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getOrDefault(DynamicObject receiver, Object key, Object defaultValue) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            public int getIntOrDefault(DynamicObject receiver, Object key, Object defaultValue) throws UnexpectedResultException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getIntOrDefault(CompilerDirectives.castExact(receiver, this.receiverClass_), key, defaultValue);
            }

            @Override
            public double getDoubleOrDefault(DynamicObject receiver, Object key, Object defaultValue) throws UnexpectedResultException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getDoubleOrDefault(CompilerDirectives.castExact(receiver, this.receiverClass_), key, defaultValue);
            }

            @Override
            public long getLongOrDefault(DynamicObject receiver, Object key, Object defaultValue) throws UnexpectedResultException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return super.getLongOrDefault(CompilerDirectives.castExact(receiver, this.receiverClass_), key, defaultValue);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void put(DynamicObject receiver, Object key, Object value2) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            public void putInt(DynamicObject receiver, Object key, int value2) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.putInt(CompilerDirectives.castExact(receiver, this.receiverClass_), key, value2);
            }

            @Override
            public void putDouble(DynamicObject receiver, Object key, double value2) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.putDouble(CompilerDirectives.castExact(receiver, this.receiverClass_), key, value2);
            }

            @Override
            public void putLong(DynamicObject receiver, Object key, long value2) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                super.putLong(CompilerDirectives.castExact(receiver, this.receiverClass_), key, value2);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean putIfPresent(DynamicObject receiver, Object key, Object value2) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void putWithFlags(DynamicObject receiver, Object key, Object value2, int flags) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void putConstant(DynamicObject receiver, Object key, Object value2, int flags) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean removeKey(DynamicObject receiver, Object key) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean setDynamicType(DynamicObject receiver, Object type) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getDynamicType(DynamicObject receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean containsKey(DynamicObject receiver, Object key) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public int getShapeFlags(DynamicObject receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean setShapeFlags(DynamicObject receiver, int flags) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Property getProperty(DynamicObject receiver, Object key) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean setPropertyFlags(DynamicObject receiver, Object key, int propertyFlags) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void markShared(DynamicObject receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isShared(DynamicObject receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean updateShape(DynamicObject receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean resetShape(DynamicObject receiver, Shape otherShape) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object[] getKeyArray(DynamicObject receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Property[] getPropertyArray(DynamicObject receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                throw new AbstractMethodError();
            }
        }
    }
}

