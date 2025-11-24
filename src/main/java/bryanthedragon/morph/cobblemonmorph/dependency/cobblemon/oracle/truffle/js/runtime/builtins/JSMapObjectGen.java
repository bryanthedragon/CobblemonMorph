
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnknownKeyException;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.builtins.helper.JSCollectionsNormalizeNode;
import com.oracle.truffle.js.builtins.helper.JSCollectionsNormalizeNodeGen;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNode;
import com.oracle.truffle.js.nodes.interop.KeyInfoNode;
import com.oracle.truffle.js.runtime.builtins.JSMapObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSMapObject.class)
final class JSMapObjectGen {
    private JSMapObjectGen() {
    }

    static {
        LibraryExport.register(JSMapObject.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JSMapObject.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, JSMapObject.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JSMapObject);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JSMapObject);
            return new Cached(receiver);
        }

        @GeneratedBy(value=JSMapObject.class)
        @DenyReplace
        private static final class Uncached
        extends JSNonProxyObjectGen.InteropLibraryExports.Uncached {
            protected Uncached(Object receiver) {
                super(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean accepts(Object receiver) {
                return super.accepts(receiver);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasHashEntries(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSMapObject)receiver).hasHashEntries();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public long getHashSize(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSMapObject)receiver).getHashSize();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getHashEntriesIterator(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSMapObject)receiver).getHashEntriesIterator();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isHashEntryReadable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSMapObject arg0Value = (JSMapObject)arg0Value_;
                return arg0Value.isHashEntryReadable(arg1Value, ImportValueNode.getUncached(), JSCollectionsNormalizeNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readHashValue(Object arg0Value_, Object arg1Value) throws UnknownKeyException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSMapObject arg0Value = (JSMapObject)arg0Value_;
                return arg0Value.readHashValue(arg1Value, ExportValueNode.getUncached(), ImportValueNode.getUncached(), JSCollectionsNormalizeNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object readHashValueOrDefault(Object arg0Value_, Object arg1Value, Object arg2Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSMapObject arg0Value = (JSMapObject)arg0Value_;
                return arg0Value.readHashValueOrDefault(arg1Value, arg2Value, ExportValueNode.getUncached(), ImportValueNode.getUncached(), JSCollectionsNormalizeNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isHashEntryModifiable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSMapObject arg0Value = (JSMapObject)arg0Value_;
                return arg0Value.isHashEntryModifiable(arg1Value, ImportValueNode.getUncached(), JSCollectionsNormalizeNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isHashEntryRemovable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSMapObject arg0Value = (JSMapObject)arg0Value_;
                return arg0Value.isHashEntryModifiable(arg1Value, ImportValueNode.getUncached(), JSCollectionsNormalizeNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isHashEntryInsertable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSMapObject arg0Value = (JSMapObject)arg0Value_;
                return arg0Value.isHashEntryInsertable(arg1Value, this);
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void writeHashEntry(Object arg0Value_, Object arg1Value, Object arg2Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSMapObject arg0Value = (JSMapObject)arg0Value_;
                arg0Value.writeHashEntry(arg1Value, arg2Value, ImportValueNode.getUncached(), ImportValueNode.getUncached(), JSCollectionsNormalizeNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public void removeHashEntry(Object arg0Value_, Object arg1Value) throws UnknownKeyException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSMapObject arg0Value = (JSMapObject)arg0Value_;
                arg0Value.removeHashEntry(arg1Value, ImportValueNode.getUncached(), JSCollectionsNormalizeNodeGen.getUncached());
            }
        }

        @GeneratedBy(value=JSMapObject.class)
        private static final class Cached
        extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @Node.Child
            private KeyInfoNode keyInfo;
            @Node.Child
            private JSInteropGetIteratorNode getIterator;
            @Node.Child
            private ImportValueNode importKeyNode;
            @Node.Child
            private JSCollectionsNormalizeNode normalizeKeyNode;
            @Node.Child
            private ExportValueNode exportValueNode;
            @Node.Child
            private ImportValueNode writeHashEntryNode__writeHashEntry_importValueNode_;

            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            public boolean hasHashEntries(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSMapObject)receiver).hasHashEntries();
            }

            @Override
            public long getHashSize(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSMapObject)receiver).getHashSize();
            }

            @Override
            public Object getHashEntriesIterator(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSMapObject)receiver).getHashEntriesIterator();
            }

            @Override
            public boolean isHashEntryReadable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSMapObject arg0Value = (JSMapObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 1) != 0) {
                    return arg0Value.isHashEntryReadable(arg1Value, this.importKeyNode, this.normalizeKeyNode);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isHashEntryReadableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isHashEntryReadableNode_AndSpecialize(JSMapObject arg0Value, Object arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.importKeyNode = super.insert(this.importKeyNode == null ? ImportValueNode.create() : this.importKeyNode);
                    this.normalizeKeyNode = super.insert(this.normalizeKeyNode == null ? JSCollectionsNormalizeNode.create() : this.normalizeKeyNode);
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isHashEntryReadable(arg1Value, this.importKeyNode, this.normalizeKeyNode);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public NodeCost getCost() {
                int state_0 = this.state_0_;
                if ((state_0 & 1) == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                return NodeCost.MONOMORPHIC;
            }

            @Override
            public Object readHashValue(Object arg0Value_, Object arg1Value) throws UnsupportedMessageException, UnknownKeyException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSMapObject arg0Value = (JSMapObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 2) != 0) {
                    return arg0Value.readHashValue(arg1Value, this.exportValueNode, this.importKeyNode, this.normalizeKeyNode);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readHashValueNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object readHashValueNode_AndSpecialize(JSMapObject arg0Value, Object arg1Value) throws UnknownKeyException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.exportValueNode = super.insert(this.exportValueNode == null ? ExportValueNode.create() : this.exportValueNode);
                    this.importKeyNode = super.insert(this.importKeyNode == null ? ImportValueNode.create() : this.importKeyNode);
                    this.normalizeKeyNode = super.insert(this.normalizeKeyNode == null ? JSCollectionsNormalizeNode.create() : this.normalizeKeyNode);
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.readHashValue(arg1Value, this.exportValueNode, this.importKeyNode, this.normalizeKeyNode);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public Object readHashValueOrDefault(Object arg0Value_, Object arg1Value, Object arg2Value) throws UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSMapObject arg0Value = (JSMapObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 4) != 0) {
                    return arg0Value.readHashValueOrDefault(arg1Value, arg2Value, this.exportValueNode, this.importKeyNode, this.normalizeKeyNode);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.readHashValueOrDefaultNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object readHashValueOrDefaultNode_AndSpecialize(JSMapObject arg0Value, Object arg1Value, Object arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.exportValueNode = super.insert(this.exportValueNode == null ? ExportValueNode.create() : this.exportValueNode);
                    this.importKeyNode = super.insert(this.importKeyNode == null ? ImportValueNode.create() : this.importKeyNode);
                    this.normalizeKeyNode = super.insert(this.normalizeKeyNode == null ? JSCollectionsNormalizeNode.create() : this.normalizeKeyNode);
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.readHashValueOrDefault(arg1Value, arg2Value, this.exportValueNode, this.importKeyNode, this.normalizeKeyNode);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isHashEntryModifiable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSMapObject arg0Value = (JSMapObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 8) != 0) {
                    return arg0Value.isHashEntryModifiable(arg1Value, this.importKeyNode, this.normalizeKeyNode);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isHashEntryModifiableNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isHashEntryModifiableNode_AndSpecialize(JSMapObject arg0Value, Object arg1Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.importKeyNode = super.insert(this.importKeyNode == null ? ImportValueNode.create() : this.importKeyNode);
                    this.normalizeKeyNode = super.insert(this.normalizeKeyNode == null ? JSCollectionsNormalizeNode.create() : this.normalizeKeyNode);
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isHashEntryModifiable(arg1Value, this.importKeyNode, this.normalizeKeyNode);
                    return bl;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isHashEntryRemovable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSMapObject arg0Value = (JSMapObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 8) != 0) {
                    return arg0Value.isHashEntryModifiable(arg1Value, this.importKeyNode, this.normalizeKeyNode);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isHashEntryModifiableNode_AndSpecialize(arg0Value, arg1Value);
            }

            @Override
            public boolean isHashEntryInsertable(Object arg0Value_, Object arg1Value) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSMapObject arg0Value = (JSMapObject)arg0Value_;
                Cached isHashEntryInsertableNode__isHashEntryInsertable_thisLibrary__ = this;
                return arg0Value.isHashEntryInsertable(arg1Value, isHashEntryInsertableNode__isHashEntryInsertable_thisLibrary__);
            }

            @Override
            public void writeHashEntry(Object arg0Value_, Object arg1Value, Object arg2Value) throws UnsupportedMessageException, UnknownKeyException, UnsupportedTypeException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSMapObject arg0Value = (JSMapObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x10) != 0) {
                    arg0Value.writeHashEntry(arg1Value, arg2Value, this.importKeyNode, this.writeHashEntryNode__writeHashEntry_importValueNode_, this.normalizeKeyNode);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.writeHashEntryNode_AndSpecialize(arg0Value, arg1Value, arg2Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void writeHashEntryNode_AndSpecialize(JSMapObject arg0Value, Object arg1Value, Object arg2Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.importKeyNode = super.insert(this.importKeyNode == null ? ImportValueNode.create() : this.importKeyNode);
                    this.writeHashEntryNode__writeHashEntry_importValueNode_ = super.insert(ImportValueNode.create());
                    this.normalizeKeyNode = super.insert(this.normalizeKeyNode == null ? JSCollectionsNormalizeNode.create() : this.normalizeKeyNode);
                    this.state_0_ = state_0 |= 0x10;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.writeHashEntry(arg1Value, arg2Value, this.importKeyNode, this.writeHashEntryNode__writeHashEntry_importValueNode_, this.normalizeKeyNode);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public void removeHashEntry(Object arg0Value_, Object arg1Value) throws UnsupportedMessageException, UnknownKeyException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSMapObject arg0Value = (JSMapObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 0x20) != 0) {
                    arg0Value.removeHashEntry(arg1Value, this.importKeyNode, this.normalizeKeyNode);
                    return;
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                this.removeHashEntryNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private void removeHashEntryNode_AndSpecialize(JSMapObject arg0Value, Object arg1Value) throws UnknownKeyException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.importKeyNode = super.insert(this.importKeyNode == null ? ImportValueNode.create() : this.importKeyNode);
                    this.normalizeKeyNode = super.insert(this.normalizeKeyNode == null ? JSCollectionsNormalizeNode.create() : this.normalizeKeyNode);
                    this.state_0_ = state_0 |= 0x20;
                    lock.unlock();
                    hasLock = false;
                    arg0Value.removeHashEntry(arg1Value, this.importKeyNode, this.normalizeKeyNode);
                    return;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }
        }
    }
}

