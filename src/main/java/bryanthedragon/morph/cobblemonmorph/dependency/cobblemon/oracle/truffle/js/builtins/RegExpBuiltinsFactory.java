
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.Assumption;
import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.RegExpBuiltins;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.util.TRegexUtil;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=RegExpBuiltins.class)
public final class RegExpBuiltinsFactory {

    @GeneratedBy(value=RegExpBuiltins.JSRegExpStaticResultRightContextNode.class)
    static final class JSRegExpStaticResultRightContextNodeGen
    extends RegExpBuiltins.JSRegExpStaticResultRightContextNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TruffleString.SubstringByteIndexNode substringNode_;

        private JSRegExpStaticResultRightContextNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[0];
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                return this.rightContext(frameValue, this.substringNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(VirtualFrame frameValue) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.substringNode_ = super.insert(TruffleString.SubstringByteIndexNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.rightContext(frameValue, this.substringNode_);
                return truffleString;
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

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "rightContext";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<TruffleString.SubstringByteIndexNode>> cached = new ArrayList<List<TruffleString.SubstringByteIndexNode>>();
                cached.add(Arrays.asList(this.substringNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static RegExpBuiltins.JSRegExpStaticResultRightContextNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRegExpStaticResultRightContextNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RegExpBuiltins.JSRegExpStaticResultLeftContextNode.class)
    static final class JSRegExpStaticResultLeftContextNodeGen
    extends RegExpBuiltins.JSRegExpStaticResultLeftContextNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TruffleString.SubstringByteIndexNode substringNode_;

        private JSRegExpStaticResultLeftContextNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[0];
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                return this.leftContext(frameValue, this.substringNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(VirtualFrame frameValue) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.substringNode_ = super.insert(TruffleString.SubstringByteIndexNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.leftContext(frameValue, this.substringNode_);
                return truffleString;
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

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "leftContext";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<TruffleString.SubstringByteIndexNode>> cached = new ArrayList<List<TruffleString.SubstringByteIndexNode>>();
                cached.add(Arrays.asList(this.substringNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static RegExpBuiltins.JSRegExpStaticResultLeftContextNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRegExpStaticResultLeftContextNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RegExpBuiltins.JSRegExpStaticResultLastParenNode.class)
    static final class JSRegExpStaticResultLastParenNodeGen
    extends RegExpBuiltins.JSRegExpStaticResultLastParenNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TruffleString.SubstringByteIndexNode substringNode_;

        private JSRegExpStaticResultLastParenNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[0];
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                return this.lastParen(frameValue, this.substringNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(VirtualFrame frameValue) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.substringNode_ = super.insert(TruffleString.SubstringByteIndexNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.lastParen(frameValue, this.substringNode_);
                return truffleString;
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

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "lastParen";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<TruffleString.SubstringByteIndexNode>> cached = new ArrayList<List<TruffleString.SubstringByteIndexNode>>();
                cached.add(Arrays.asList(this.substringNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static RegExpBuiltins.JSRegExpStaticResultLastParenNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRegExpStaticResultLastParenNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RegExpBuiltins.JSRegExpStaticResultGetGroupNode.class)
    static final class JSRegExpStaticResultGetGroupNodeGen
    extends RegExpBuiltins.JSRegExpStaticResultGetGroupNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TruffleString.SubstringByteIndexNode substringNode_;

        private JSRegExpStaticResultGetGroupNodeGen(JSContext context, JSBuiltin builtin, int groupNumber, JavaScriptNode[] arguments) {
            super(context, builtin, groupNumber);
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[0];
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                return this.getGroup(frameValue, this.substringNode_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(frameValue);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private TruffleString executeAndSpecialize(VirtualFrame frameValue) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                this.substringNode_ = super.insert(TruffleString.SubstringByteIndexNode.create());
                this.state_0_ = state_0 |= 1;
                lock.unlock();
                hasLock = false;
                TruffleString truffleString = this.getGroup(frameValue, this.substringNode_);
                return truffleString;
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

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "getGroup";
            if (state_0 != 0) {
                s[1] = (byte)1;
                ArrayList<List<TruffleString.SubstringByteIndexNode>> cached = new ArrayList<List<TruffleString.SubstringByteIndexNode>>();
                cached.add(Arrays.asList(this.substringNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static RegExpBuiltins.JSRegExpStaticResultGetGroupNode create(JSContext context, JSBuiltin builtin, int groupNumber, JavaScriptNode[] arguments) {
            return new JSRegExpStaticResultGetGroupNodeGen(context, builtin, groupNumber, arguments);
        }
    }

    @GeneratedBy(value=RegExpBuiltins.JSRegExpStaticResultMultilineNode.class)
    static final class JSRegExpStaticResultMultilineNodeGen
    extends RegExpBuiltins.JSRegExpStaticResultMultilineNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private Assumption getMultilineLazy_assumption0_;
        @Node.Child
        private RegExpBuiltins.GetStaticRegExpResultNode getMultilineEager_getResultNode_;
        @Node.Child
        private TRegexUtil.TRegexResultAccessor getMultilineEager_resultAccessor_;

        private JSRegExpStaticResultMultilineNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[0];
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0) {
                assert (this.getContext().isOptionNashornCompatibilityMode());
                return this.getMultilineLazyNashorn();
            }
            if ((state_0 & 2) != 0) {
                if (!Assumption.isValidAssumption(this.getMultilineLazy_assumption0_)) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.removeGetMultilineLazy_();
                    return this.executeAndSpecialize();
                }
                assert (!this.getContext().isOptionNashornCompatibilityMode());
                return this.getMultilineLazy();
            }
            if ((state_0 & 4) != 0) {
                return this.getMultilineEager(this.getMultilineEager_getResultNode_, this.getMultilineEager_resultAccessor_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize();
        }

        @Override
        public boolean executeBoolean(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if ((state_0 & 1) != 0) {
                assert (this.getContext().isOptionNashornCompatibilityMode());
                return this.getMultilineLazyNashorn();
            }
            if ((state_0 & 2) != 0) {
                if (!Assumption.isValidAssumption(this.getMultilineLazy_assumption0_)) {
                    CompilerDirectives.transferToInterpreterAndInvalidate();
                    this.removeGetMultilineLazy_();
                    return this.executeAndSpecialize();
                }
                assert (!this.getContext().isOptionNashornCompatibilityMode());
                return this.getMultilineLazy();
            }
            if ((state_0 & 4) != 0) {
                return this.getMultilineEager(this.getMultilineEager_getResultNode_, this.getMultilineEager_resultAccessor_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize();
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.executeBoolean(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private boolean executeAndSpecialize() {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                Assumption getMultilineLazy_assumption0;
                int state_0 = this.state_0_;
                if (this.getContext().isOptionNashornCompatibilityMode()) {
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.getMultilineLazyNashorn();
                    return bl;
                }
                if (!this.getContext().isOptionNashornCompatibilityMode() && Assumption.isValidAssumption(getMultilineLazy_assumption0 = this.getStaticResultUnusedAssumption())) {
                    this.getMultilineLazy_assumption0_ = getMultilineLazy_assumption0;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    boolean bl = this.getMultilineLazy();
                    return bl;
                }
                this.getMultilineEager_getResultNode_ = super.insert(this.createGetResultNode());
                this.getMultilineEager_resultAccessor_ = super.insert(TRegexUtil.TRegexResultAccessor.create());
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                boolean bl = this.getMultilineEager(this.getMultilineEager_getResultNode_, this.getMultilineEager_resultAccessor_);
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
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        void removeGetMultilineLazy_() {
            Lock lock = this.getLock();
            lock.lock();
            try {
                this.state_0_ &= 0xFFFFFFFD;
            }
            finally {
                lock.unlock();
            }
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "getMultilineLazyNashorn";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "getMultilineLazy";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "getMultilineEager";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                ArrayList<List<Node>> cached = new ArrayList<List<Node>>();
                cached.add(Arrays.asList(this.getMultilineEager_getResultNode_, this.getMultilineEager_resultAccessor_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static RegExpBuiltins.JSRegExpStaticResultMultilineNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRegExpStaticResultMultilineNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RegExpBuiltins.JSRegExpStaticResultSetInputNode.class)
    static final class JSRegExpStaticResultSetInputNodeGen
    extends RegExpBuiltins.JSRegExpStaticResultSetInputNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;

        private JSRegExpStaticResultSetInputNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            return this.setInputProp(frameValue, arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "setInputProp";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static RegExpBuiltins.JSRegExpStaticResultSetInputNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRegExpStaticResultSetInputNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RegExpBuiltins.JSRegExpStaticResultGetInputNode.class)
    static final class JSRegExpStaticResultGetInputNodeGen
    extends RegExpBuiltins.JSRegExpStaticResultGetInputNode
    implements Introspection.Provider {
        private JSRegExpStaticResultGetInputNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[0];
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            return this.getInputProp(frameValue);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "getInputProp";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static RegExpBuiltins.JSRegExpStaticResultGetInputNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRegExpStaticResultGetInputNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RegExpBuiltins.GetStaticRegExpResultNode.class)
    static final class GetStaticRegExpResultNodeGen
    extends RegExpBuiltins.GetStaticRegExpResultNode
    implements Introspection.Provider {
        private GetStaticRegExpResultNodeGen(JSContext context) {
            super(context);
        }

        @Override
        Object execute() {
            return this.get();
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MONOMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[2];
            data[0] = 0;
            Object[] s = new Object[3];
            s[0] = "get";
            s[1] = (byte)1;
            data[1] = s;
            return Introspection.Provider.create(data);
        }

        public static RegExpBuiltins.GetStaticRegExpResultNode create(JSContext context) {
            return new GetStaticRegExpResultNodeGen(context);
        }
    }
}

