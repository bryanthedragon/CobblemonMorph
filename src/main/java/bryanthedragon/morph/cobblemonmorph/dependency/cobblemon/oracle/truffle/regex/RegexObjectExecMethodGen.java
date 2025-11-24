/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.regex;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.interop.ArityException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.interop.UnsupportedMessageException;
import com.oracle.truffle.api.interop.UnsupportedTypeException;
import com.oracle.truffle.api.library.LibraryExport;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.regex.AbstractRegexObjectGen;
import com.oracle.truffle.regex.RegexObject;
import com.oracle.truffle.regex.RegexObjectFactory;
import com.oracle.truffle.regex.runtime.nodes.ExpectStringOrTruffleObjectNode;
import com.oracle.truffle.regex.runtime.nodes.ExpectStringOrTruffleObjectNodeGen;
import com.oracle.truffle.regex.runtime.nodes.ToLongNode;
import com.oracle.truffle.regex.runtime.nodes.ToLongNodeGen;
import java.lang.invoke.VarHandle;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=RegexObject.RegexObjectExecMethod.class)
final class RegexObjectExecMethodGen {
    private RegexObjectExecMethodGen() {
    }

    static {
        LibraryExport.register(RegexObject.RegexObjectExecMethod.class, new InteropLibraryExports());
    }

    @GeneratedBy(value=RegexObject.RegexObjectExecMethod.class)
    private static final class InteropLibraryExports
    extends LibraryExport<InteropLibrary> {
        private InteropLibraryExports() {
            super(InteropLibrary.class, RegexObject.RegexObjectExecMethod.class, false, false, 0);
        }

        @Override
        protected InteropLibrary createUncached(Object receiver) {
            assert (receiver instanceof RegexObject.RegexObjectExecMethod);
            Uncached uncached = new Uncached(receiver);
            return uncached;
        }

        @Override
        protected InteropLibrary createCached(Object receiver) {
            assert (receiver instanceof RegexObject.RegexObjectExecMethod);
            return new Cached(receiver);
        }

        @GeneratedBy(value=RegexObject.RegexObjectExecMethod.class)
        @DenyReplace
        private static final class Uncached
        extends AbstractRegexObjectGen.InteropLibraryExports.Uncached {
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
            public boolean isExecutable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                return ((RegexObject.RegexObjectExecMethod)receiver).isExecutable();
            }

            @Override
            @CompilerDirectives.TruffleBoundary
            public Object execute(Object arg0Value_, Object ... arg1Value) throws ArityException, UnsupportedTypeException, UnsupportedMessageException {
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                RegexObject.RegexObjectExecMethod arg0Value = (RegexObject.RegexObjectExecMethod)arg0Value_;
                return arg0Value.execute(arg1Value, ExpectStringOrTruffleObjectNodeGen.getUncached(), ToLongNodeGen.getUncached(), RegexObjectFactory.ExecCompiledRegexNodeGen.getUncached());
            }
        }

        @GeneratedBy(value=RegexObject.RegexObjectExecMethod.class)
        private static final class Cached
        extends AbstractRegexObjectGen.InteropLibraryExports.Cached {
            @CompilerDirectives.CompilationFinal
            private volatile int state_0_;
            @Node.Child
            private ExecuteData execute_cache;

            protected Cached(Object receiver) {
                super(receiver);
            }

            @Override
            public boolean isExecutable(Object receiver) {
                assert (this.accepts(receiver)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                return ((RegexObject.RegexObjectExecMethod)receiver).isExecutable();
            }

            @Override
            public Object execute(Object arg0Value_, Object ... arg1Value) throws UnsupportedTypeException, ArityException, UnsupportedMessageException {
                ExecuteData s0_;
                assert (this.accepts(arg0Value_)) : "Invalid library usage. Library does not accept given receiver.";
                assert (this.assertAdopted());
                RegexObject.RegexObjectExecMethod arg0Value = (RegexObject.RegexObjectExecMethod)arg0Value_;
                int state_0 = this.state_0_;
                if (state_0 != 0 && (s0_ = this.execute_cache) != null) {
                    return arg0Value.execute(arg1Value, s0_.expectStringOrTruffleObjectNode_, s0_.toLongNode_, s0_.execNode_);
                }
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arg0Value, arg1Value);
            }

            /*
             * WARNING - Removed try catching itself - possible behaviour change.
             */
            private Object executeAndSpecialize(RegexObject.RegexObjectExecMethod arg0Value, Object[] arg1Value) throws ArityException, UnsupportedTypeException, UnsupportedMessageException {
                Lock lock = this.getLock();
                boolean hasLock = true;
                lock.lock();
                try {
                    int state_0 = this.state_0_;
                    ExecuteData s0_ = super.insert(new ExecuteData());
                    s0_.expectStringOrTruffleObjectNode_ = s0_.insertAccessor(ExpectStringOrTruffleObjectNode.create());
                    s0_.toLongNode_ = s0_.insertAccessor(ToLongNode.create());
                    s0_.execNode_ = s0_.insertAccessor(RegexObjectFactory.ExecCompiledRegexNodeGen.create());
                    VarHandle.storeStoreFence();
                    this.execute_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    Object object = arg0Value.execute(arg1Value, s0_.expectStringOrTruffleObjectNode_, s0_.toLongNode_, s0_.execNode_);
                    return object;
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
                if (state_0 == 0) {
                    return NodeCost.UNINITIALIZED;
                }
                return NodeCost.MONOMORPHIC;
            }

            @GeneratedBy(value=RegexObject.RegexObjectExecMethod.class)
            private static final class ExecuteData
            extends Node {
                @Node.Child
                ExpectStringOrTruffleObjectNode expectStringOrTruffleObjectNode_;
                @Node.Child
                ToLongNode toLongNode_;
                @Node.Child
                RegexObject.ExecCompiledRegexNode execNode_;

                ExecuteData() {
                }

                @Override
                public NodeCost getCost() {
                    return NodeCost.NONE;
                }

                <T extends Node> T insertAccessor(T node) {
                    return super.insert(node);
                }
            }
        }
    }
}

