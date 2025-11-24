
package com.oracle.truffle.js.runtime.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.SourceSection;
import com.oracle.truffle.js.nodes.interop.ExportValueNode;
import com.oracle.truffle.js.nodes.interop.JSInteropExecuteNode;
import com.oracle.truffle.js.nodes.interop.JSInteropExecuteNodeGen;
import com.oracle.truffle.js.nodes.interop.JSInteropGetIteratorNode;
import com.oracle.truffle.js.nodes.interop.JSInteropInstantiateNode;
import com.oracle.truffle.js.nodes.interop.JSInteropInstantiateNodeGen;
import com.oracle.truffle.js.nodes.interop.KeyInfoNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNode;
import com.oracle.truffle.js.nodes.unary.IsCallableNodeGen;
import com.oracle.truffle.js.runtime.builtins.JSFunctionObject;
import com.oracle.truffle.js.runtime.objects.JSNonProxyObjectGen;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSFunctionObject.class)
public final class JSFunctionObjectGen {
    private JSFunctionObjectGen() {
    }

    static {
        LibraryExport.register(JSFunctionObject.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=JSFunctionObject.class)
    public static class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, JSFunctionObject.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof JSFunctionObject);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof JSFunctionObject);
            return new Cached(receiver);
        }

        @GeneratedBy(value=JSFunctionObject.class)
        public static class Uncached
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
            public boolean isExecutable(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSFunctionObject arg0Value = (JSFunctionObject)arg0Value_;
                return arg0Value.isExecutable(IsCallableNodeGen.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object execute(Object arg0Value_, Object ... arg1Value) throws UnsupportedMessageException, UnsupportedTypeException, ArityException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSFunctionObject arg0Value = (JSFunctionObject)arg0Value_;
                return arg0Value.execute(arg1Value, this, JSInteropExecuteNodeGen.getUncached(), ExportValueNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isInstantiable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSFunctionObject)receiver).isInstantiable();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object instantiate(Object arg0Value_, Object ... arg1Value) throws UnsupportedMessageException, UnsupportedTypeException, ArityException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                JSFunctionObject arg0Value = (JSFunctionObject)arg0Value_;
                return arg0Value.instantiate(arg1Value, this, JSInteropInstantiateNodeGen.getUncached(), ExportValueNode.getUncached());
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean hasSourceLocation(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSFunctionObject)receiver).hasSourceLocation();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSFunctionObject)receiver).getSourceLocation();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSFunctionObject)receiver).isMetaObject();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaQualifiedName(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSFunctionObject)receiver).getMetaObjectName();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object getMetaSimpleName(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSFunctionObject)receiver).getMetaObjectName();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public boolean isMetaInstance(Object receiver, Object instance) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((JSFunctionObject)receiver).isMetaInstance(instance);
            }
        }

        @GeneratedBy(value=JSFunctionObject.class)
        public static class Cached
        extends JSNonProxyObjectGen.InteropLibraryExports.Cached {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @Node.Child
            private KeyInfoNode keyInfo;
            @Node.Child
            private JSInteropGetIteratorNode getIterator;
            @Node.Child
            private ExportValueNode exportValue;
            @Node.Child
            private IsCallableNode isExecutableNode__isExecutable_isCallable_;
            @Node.Child
            private JSInteropExecuteNode executeNode__execute_callNode_;
            @Node.Child
            private JSInteropInstantiateNode instantiateNode__instantiate_callNode_;

            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            public boolean isExecutable(Object arg0Value_) {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSFunctionObject arg0Value = (JSFunctionObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 1) != 0) {
                    return arg0Value.isExecutable(this.isExecutableNode__isExecutable_isCallable_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.isExecutableNode_AndSpecialize(arg0Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private boolean isExecutableNode_AndSpecialize(JSFunctionObject arg0Value) {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    this.isExecutableNode__isExecutable_isCallable_ = super.insert(IsCallableNode.create());
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = arg0Value.isExecutable(this.isExecutableNode__isExecutable_isCallable_);
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
            public Object execute(Object arg0Value_, Object ... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSFunctionObject arg0Value = (JSFunctionObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 2) != 0) {
                    Cached executeNode__execute_self__ = this;
                    return arg0Value.execute(arg1Value, executeNode__execute_self__, this.executeNode__execute_callNode_, this.exportValue);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeNode_AndSpecialize(JSFunctionObject arg0Value, Object[] arg1Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached executeNode__execute_self__ = null;
                    executeNode__execute_self__ = this;
                    this.executeNode__execute_callNode_ = super.insert(JSInteropExecuteNodeGen.create());
                    this.exportValue = super.insert(this.exportValue == null ? ExportValueNode.create() : this.exportValue);
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.execute(arg1Value, executeNode__execute_self__, this.executeNode__execute_callNode_, this.exportValue);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean isInstantiable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSFunctionObject)receiver).isInstantiable();
            }

            @Override
            public Object instantiate(Object arg0Value_, Object ... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                JSFunctionObject arg0Value = (JSFunctionObject)arg0Value_;
                int state_0 = this.state_0_;
                if ((state_0 & 4) != 0) {
                    Cached instantiateNode__instantiate_self__ = this;
                    return arg0Value.instantiate(arg1Value, instantiateNode__instantiate_self__, this.instantiateNode__instantiate_callNode_, this.exportValue);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.instantiateNode_AndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object instantiateNode_AndSpecialize(JSFunctionObject arg0Value, Object[] arg1Value) throws UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    Cached instantiateNode__instantiate_self__ = null;
                    instantiateNode__instantiate_self__ = this;
                    this.instantiateNode__instantiate_callNode_ = super.insert(JSInteropInstantiateNodeGen.create());
                    this.exportValue = super.insert(this.exportValue == null ? ExportValueNode.create() : this.exportValue);
                    this.state_0_ = state_0 |= 4;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.instantiate(arg1Value, instantiateNode__instantiate_self__, this.instantiateNode__instantiate_callNode_, this.exportValue);
                    return object;
                }
                finally {
                    if (hasLock) {
                        lock.unlock();
                    }
                }
            }

            @Override
            public boolean hasSourceLocation(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSFunctionObject)receiver).hasSourceLocation();
            }

            @Override
            public SourceSection getSourceLocation(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSFunctionObject)receiver).getSourceLocation();
            }

            @Override
            public boolean isMetaObject(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSFunctionObject)receiver).isMetaObject();
            }

            @Override
            public Object getMetaQualifiedName(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSFunctionObject)receiver).getMetaObjectName();
            }

            @Override
            public Object getMetaSimpleName(Object receiver) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSFunctionObject)receiver).getMetaObjectName();
            }

            @Override
            public boolean isMetaInstance(Object receiver, Object instance) throws UnsupportedMessageException {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((JSFunctionObject)receiver).isMetaInstance(instance);
            }
        }
    }
}

