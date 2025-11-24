/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.builtins;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.nodes.UnexpectedResultException;
import com.oracle.truffle.api.profiles.ConditionProfile;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.builtins.ArrayPrototypeBuiltins;
import com.oracle.truffle.js.builtins.RegExpPrototypeBuiltins;
import com.oracle.truffle.js.builtins.StringPrototypeBuiltins;
import com.oracle.truffle.js.builtins.helper.JSRegExpExecIntlNode;
import com.oracle.truffle.js.builtins.helper.ReplaceStringParser;
import com.oracle.truffle.js.nodes.CompileRegexNode;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptBaseNode;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.IsJSObjectNode;
import com.oracle.truffle.js.nodes.access.PropertyGetNode;
import com.oracle.truffle.js.nodes.access.PropertySetNode;
import com.oracle.truffle.js.nodes.cast.JSToLengthNode;
import com.oracle.truffle.js.nodes.cast.JSToStringNode;
import com.oracle.truffle.js.nodes.function.JSBuiltin;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSRegExp;
import com.oracle.truffle.js.runtime.builtins.JSRegExpObject;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import com.oracle.truffle.js.runtime.util.TRegexUtil;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=RegExpPrototypeBuiltins.class)
public final class RegExpPrototypeBuiltinsFactory {

    @GeneratedBy(value=RegExpPrototypeBuiltins.CompiledRegexPatternAccessor.class)
    static final class CompiledRegexPatternAccessorNodeGen
    extends RegExpPrototypeBuiltins.CompiledRegexPatternAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private CompiledRegexPatternAccessorNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        private boolean fallbackGuard_(int state_0, Object arguments0Value) {
            JSDynamicObject arguments0Value_;
            if ((state_0 & 1) == 0 && arguments0Value instanceof JSRegExpObject) {
                return false;
            }
            return !(arguments0Value instanceof JSDynamicObject) || !this.isRegExpPrototype(arguments0Value_ = (JSDynamicObject)arguments0Value);
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSRegExpObject) {
                JSRegExpObject arguments0Value__2 = (JSRegExpObject)arguments0Value_;
                return this.doRegExp(arguments0Value__2);
            }
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject && this.isRegExpPrototype(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.doPrototype(arguments0Value__);
            }
            if ((state_0 & 4) != 0 && this.fallbackGuard_(state_0, arguments0Value_)) {
                return this.doObject(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            JSDynamicObject arguments0Value_;
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSRegExpObject) {
                JSRegExpObject arguments0Value_2 = (JSRegExpObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doRegExp(arguments0Value_2);
            }
            if (arguments0Value instanceof JSDynamicObject && this.isRegExpPrototype(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doPrototype(arguments0Value_);
            }
            this.state_0_ = state_0 |= 4;
            return this.doObject(arguments0Value);
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

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doRegExp";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doPrototype";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "doObject";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static RegExpPrototypeBuiltins.CompiledRegexPatternAccessor create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new CompiledRegexPatternAccessorNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RegExpPrototypeBuiltins.CompiledRegexFlagPropertyAccessor.class)
    static final class CompiledRegexFlagPropertyAccessorNodeGen
    extends RegExpPrototypeBuiltins.CompiledRegexFlagPropertyAccessor
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private CompiledRegexFlagPropertyAccessorNodeGen(JSContext context, JSBuiltin builtin, String flagName, JavaScriptNode[] arguments) {
            super(context, builtin, flagName);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        private boolean fallbackGuard_(int state_0, Object arguments0Value) {
            JSDynamicObject arguments0Value_;
            if ((state_0 & 1) == 0 && arguments0Value instanceof JSRegExpObject) {
                return false;
            }
            return !(arguments0Value instanceof JSDynamicObject) || !this.isRegExpPrototype(arguments0Value_ = (JSDynamicObject)arguments0Value);
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSRegExpObject) {
                JSRegExpObject arguments0Value__2 = (JSRegExpObject)arguments0Value_;
                return this.doRegExp(arguments0Value__2);
            }
            if ((state_0 & 2) != 0 && arguments0Value_ instanceof JSDynamicObject && this.isRegExpPrototype(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.doPrototype(arguments0Value__);
            }
            if ((state_0 & 4) != 0 && this.fallbackGuard_(state_0, arguments0Value_)) {
                return this.doObject(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value) {
            JSDynamicObject arguments0Value_;
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSRegExpObject) {
                JSRegExpObject arguments0Value_2 = (JSRegExpObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.doRegExp(arguments0Value_2);
            }
            if (arguments0Value instanceof JSDynamicObject && this.isRegExpPrototype(arguments0Value_ = (JSDynamicObject)arguments0Value)) {
                this.state_0_ = state_0 |= 2;
                return this.doPrototype(arguments0Value_);
            }
            this.state_0_ = state_0 |= 4;
            return this.doObject(arguments0Value);
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

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doRegExp";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "doPrototype";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "doObject";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static RegExpPrototypeBuiltins.CompiledRegexFlagPropertyAccessor create(JSContext context, JSBuiltin builtin, String flagName, JavaScriptNode[] arguments) {
            return new CompiledRegexFlagPropertyAccessorNodeGen(context, builtin, flagName, arguments);
        }
    }

    @GeneratedBy(value=RegExpPrototypeBuiltins.RegExpFlagsGetterNode.class)
    public static final class RegExpFlagsGetterNodeGen
    extends RegExpPrototypeBuiltins.RegExpFlagsGetterNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private IsJSObjectNode object_isObjectNode_;
        @Node.Child
        private TruffleString.FromCharArrayUTF16Node object_fromCharArrayNode_;

        private RegExpFlagsGetterNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        private boolean fallbackGuard_(int state_0, Object arguments0Value) {
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if ((state_0 & 2) == 0 || this.object_isObjectNode_.executeBoolean(arguments0Value_)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            JSDynamicObject arguments0Value__;
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && this.object_isObjectNode_.executeBoolean(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                return this.doObject(arguments0Value__, this.object_isObjectNode_, this.object_fromCharArrayNode_);
            }
            if ((state_0 & 4) != 0 && this.fallbackGuard_(state_0, arguments0Value_)) {
                return this.doNotObject(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    boolean Object_duplicateFound_ = false;
                    if ((state_0 & 1) != 0 && this.object_isObjectNode_.executeBoolean(arguments0Value_)) {
                        Object_duplicateFound_ = true;
                    }
                    if (!Object_duplicateFound_) {
                        if ((state_0 & 2) == 0) {
                            this.object_isObjectNode_ = super.insert(IsJSObjectNode.create());
                            this.state_0_ = state_0 |= 2;
                        }
                        if (this.object_isObjectNode_.executeBoolean(arguments0Value_) && (state_0 & 1) == 0) {
                            this.object_isObjectNode_ = super.insert(IsJSObjectNode.create());
                            this.object_fromCharArrayNode_ = super.insert(TruffleString.FromCharArrayUTF16Node.create());
                            this.state_0_ = state_0 |= 1;
                            Object_duplicateFound_ = true;
                        }
                    }
                    if (Object_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        Object object = this.doObject(arguments0Value_, this.object_isObjectNode_, this.object_fromCharArrayNode_);
                        return object;
                    }
                }
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Object object = this.doNotObject(arguments0Value);
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
            if ((state_0 & 5) == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & 5 & (state_0 & 5) - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "doObject";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<Node>> cached = new ArrayList<List<Node>>();
                cached.add(Arrays.asList(this.object_isObjectNode_, this.object_fromCharArrayNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doNotObject";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static RegExpPrototypeBuiltins.RegExpFlagsGetterNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new RegExpFlagsGetterNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RegExpPrototypeBuiltins.JSRegExpMatchAllNode.class)
    public static final class JSRegExpMatchAllNodeGen
    extends RegExpPrototypeBuiltins.JSRegExpMatchAllNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private MatchAllData matchAll_cache;

        private JSRegExpMatchAllNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        private boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if ((state_0 & 2) == 0 || this.matchAll_cache.isObjectNode_.executeBoolean(arguments0Value_)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 5) != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                    MatchAllData s0_ = this.matchAll_cache;
                    if (s0_ != null && s0_.isObjectNode_.executeBoolean(arguments0Value__)) {
                        return this.matchAll(arguments0Value__, arguments1Value_, s0_.toStringNodeForInput_, s0_.speciesConstructNode_, s0_.getFlagsNode_, s0_.toStringNodeForFlags_, s0_.getLastIndexNode_, s0_.toLengthNode_, s0_.setLastIndexNode_, s0_.createRegExpStringIteratorNode_, s0_.isObjectNode_, s0_.indexInIntRangeProf_, s0_.stringIndexOfNode_);
                    }
                }
                if ((state_0 & 4) != 0 && this.fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
                    return this.matchAll(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    MatchAllData s0_ = this.matchAll_cache;
                    boolean MatchAll_duplicateFound_ = false;
                    if ((state_0 & 1) != 0 && s0_.isObjectNode_.executeBoolean(arguments0Value_)) {
                        MatchAll_duplicateFound_ = true;
                    }
                    if (!MatchAll_duplicateFound_) {
                        if ((state_0 & 2) == 0) {
                            s0_ = super.insert(new MatchAllData());
                            VarHandle.storeStoreFence();
                            this.matchAll_cache = s0_;
                            s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
                            this.state_0_ = state_0 |= 2;
                        }
                        if (s0_.isObjectNode_.executeBoolean(arguments0Value_) && (state_0 & 1) == 0) {
                            s0_ = super.insert(new MatchAllData());
                            s0_.toStringNodeForInput_ = s0_.insertAccessor(JSToStringNode.create());
                            s0_.speciesConstructNode_ = s0_.insertAccessor(this.createSpeciesConstructNode());
                            s0_.getFlagsNode_ = s0_.insertAccessor(PropertyGetNode.create(JSRegExp.FLAGS, this.getContext()));
                            s0_.toStringNodeForFlags_ = s0_.insertAccessor(JSToStringNode.create());
                            s0_.getLastIndexNode_ = s0_.insertAccessor(PropertyGetNode.create(JSRegExp.LAST_INDEX, this.getContext()));
                            s0_.toLengthNode_ = s0_.insertAccessor(JSToLengthNode.create());
                            s0_.setLastIndexNode_ = s0_.insertAccessor(PropertySetNode.create(JSRegExp.LAST_INDEX, false, this.getContext(), true));
                            s0_.createRegExpStringIteratorNode_ = s0_.insertAccessor(this.createCreateRegExpStringIteratorNode());
                            s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
                            s0_.indexInIntRangeProf_ = ConditionProfile.create();
                            s0_.stringIndexOfNode_ = s0_.insertAccessor(TruffleString.ByteIndexOfCodePointNode.create());
                            VarHandle.storeStoreFence();
                            this.matchAll_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                            MatchAll_duplicateFound_ = true;
                        }
                    }
                    if (MatchAll_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        Object object = this.matchAll(arguments0Value_, arguments1Value, s0_.toStringNodeForInput_, s0_.speciesConstructNode_, s0_.getFlagsNode_, s0_.toStringNodeForFlags_, s0_.getLastIndexNode_, s0_.toLengthNode_, s0_.setLastIndexNode_, s0_.createRegExpStringIteratorNode_, s0_.isObjectNode_, s0_.indexInIntRangeProf_, s0_.stringIndexOfNode_);
                        return object;
                    }
                }
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Object object = this.matchAll(arguments0Value, arguments1Value);
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
            if ((state_0 & 5) == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & 5 & (state_0 & 5) - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "matchAll";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                MatchAllData s0_ = this.matchAll_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.toStringNodeForInput_, s0_.speciesConstructNode_, s0_.getFlagsNode_, s0_.toStringNodeForFlags_, s0_.getLastIndexNode_, s0_.toLengthNode_, s0_.setLastIndexNode_, s0_.createRegExpStringIteratorNode_, s0_.isObjectNode_, s0_.indexInIntRangeProf_, s0_.stringIndexOfNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "matchAll";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static RegExpPrototypeBuiltins.JSRegExpMatchAllNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRegExpMatchAllNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=RegExpPrototypeBuiltins.JSRegExpMatchAllNode.class)
        private static final class MatchAllData
        extends Node {
            @Node.Child
            JSToStringNode toStringNodeForInput_;
            @Node.Child
            ArrayPrototypeBuiltins.ArraySpeciesConstructorNode speciesConstructNode_;
            @Node.Child
            PropertyGetNode getFlagsNode_;
            @Node.Child
            JSToStringNode toStringNodeForFlags_;
            @Node.Child
            PropertyGetNode getLastIndexNode_;
            @Node.Child
            JSToLengthNode toLengthNode_;
            @Node.Child
            PropertySetNode setLastIndexNode_;
            @Node.Child
            StringPrototypeBuiltins.CreateRegExpStringIteratorNode createRegExpStringIteratorNode_;
            @Node.Child
            IsJSObjectNode isObjectNode_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile indexInIntRangeProf_;
            @Node.Child
            TruffleString.ByteIndexOfCodePointNode stringIndexOfNode_;

            MatchAllData() {
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

    @GeneratedBy(value=RegExpPrototypeBuiltins.JSRegExpSearchNode.class)
    public static final class JSRegExpSearchNodeGen
    extends RegExpPrototypeBuiltins.JSRegExpSearchNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private IsJSObjectNode search_isObjectNode_;
        @Node.Child
        private JSToStringNode search_toString1Node_;

        private JSRegExpSearchNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        private boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if ((state_0 & 2) == 0 || this.search_isObjectNode_.executeBoolean(arguments0Value_)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 5) != 0) {
                JSDynamicObject arguments0Value__;
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject && this.search_isObjectNode_.executeBoolean(arguments0Value__ = (JSDynamicObject)arguments0Value_)) {
                    return this.search(arguments0Value__, arguments1Value_, this.search_isObjectNode_, this.search_toString1Node_);
                }
                if ((state_0 & 4) != 0 && this.fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
                    return this.search(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    boolean Search_duplicateFound_ = false;
                    if ((state_0 & 1) != 0 && this.search_isObjectNode_.executeBoolean(arguments0Value_)) {
                        Search_duplicateFound_ = true;
                    }
                    if (!Search_duplicateFound_) {
                        if ((state_0 & 2) == 0) {
                            this.search_isObjectNode_ = super.insert(IsJSObjectNode.create());
                            this.state_0_ = state_0 |= 2;
                        }
                        if (this.search_isObjectNode_.executeBoolean(arguments0Value_) && (state_0 & 1) == 0) {
                            this.search_isObjectNode_ = super.insert(IsJSObjectNode.create());
                            this.search_toString1Node_ = super.insert(JSToStringNode.create());
                            this.state_0_ = state_0 |= 1;
                            Search_duplicateFound_ = true;
                        }
                    }
                    if (Search_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        Object object = this.search(arguments0Value_, arguments1Value, this.search_isObjectNode_, this.search_toString1Node_);
                        return object;
                    }
                }
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Object object = this.search(arguments0Value, arguments1Value);
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
            if ((state_0 & 5) == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & 5 & (state_0 & 5) - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "search";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                cached.add(Arrays.asList(this.search_isObjectNode_, this.search_toString1Node_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "search";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static RegExpPrototypeBuiltins.JSRegExpSearchNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRegExpSearchNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RegExpPrototypeBuiltins.JSRegExpMatchNode.class)
    public static final class JSRegExpMatchNodeGen
    extends RegExpPrototypeBuiltins.JSRegExpMatchNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private MatchData match_cache;

        private JSRegExpMatchNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        private boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if ((state_0 & 2) == 0 || this.match_cache.isObjectNode_.executeBoolean(arguments0Value_)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 5) != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                    MatchData s0_ = this.match_cache;
                    if (s0_ != null && s0_.isObjectNode_.executeBoolean(arguments0Value__)) {
                        return this.match(arguments0Value__, arguments1Value_, s0_.isObjectNode_, s0_.toString1Node_, s0_.toString2Node_);
                    }
                }
                if ((state_0 & 4) != 0 && this.fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
                    return this.match(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    MatchData s0_ = this.match_cache;
                    boolean Match_duplicateFound_ = false;
                    if ((state_0 & 1) != 0 && s0_.isObjectNode_.executeBoolean(arguments0Value_)) {
                        Match_duplicateFound_ = true;
                    }
                    if (!Match_duplicateFound_) {
                        if ((state_0 & 2) == 0) {
                            s0_ = super.insert(new MatchData());
                            VarHandle.storeStoreFence();
                            this.match_cache = s0_;
                            s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
                            this.state_0_ = state_0 |= 2;
                        }
                        if (s0_.isObjectNode_.executeBoolean(arguments0Value_) && (state_0 & 1) == 0) {
                            s0_ = super.insert(new MatchData());
                            s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
                            s0_.toString1Node_ = s0_.insertAccessor(JSToStringNode.create());
                            s0_.toString2Node_ = s0_.insertAccessor(JSToStringNode.create());
                            VarHandle.storeStoreFence();
                            this.match_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                            Match_duplicateFound_ = true;
                        }
                    }
                    if (Match_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        Object object = this.match(arguments0Value_, arguments1Value, s0_.isObjectNode_, s0_.toString1Node_, s0_.toString2Node_);
                        return object;
                    }
                }
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Object object = this.match(arguments0Value, arguments1Value);
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
            if ((state_0 & 5) == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & 5 & (state_0 & 5) - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "match";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                MatchData s0_ = this.match_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.isObjectNode_, s0_.toString1Node_, s0_.toString2Node_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "match";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static RegExpPrototypeBuiltins.JSRegExpMatchNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRegExpMatchNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=RegExpPrototypeBuiltins.JSRegExpMatchNode.class)
        private static final class MatchData
        extends Node {
            @Node.Child
            IsJSObjectNode isObjectNode_;
            @Node.Child
            JSToStringNode toString1Node_;
            @Node.Child
            JSToStringNode toString2Node_;

            MatchData() {
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

    @GeneratedBy(value=RegExpPrototypeBuiltins.JSRegExpReplaceNode.class)
    public static final class JSRegExpReplaceNodeGen
    extends RegExpPrototypeBuiltins.JSRegExpReplaceNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private ReplaceCachedData replaceCached_cache;
        @Node.Child
        private JSToStringNode replaceDynamic_toString1Node_;

        private JSRegExpReplaceNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
        }

        @Override
        @ExplodeLoop
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                    if ((state_0 & 1) != 0 && arguments2Value_ instanceof TruffleString) {
                        TruffleString arguments2Value__ = (TruffleString)arguments2Value_;
                        ReplaceCachedData s0_ = this.replaceCached_cache;
                        while (s0_ != null) {
                            if (JSGuards.stringEquals(s0_.equalsNode_, s0_.cachedReplaceValue_, arguments2Value__)) {
                                return this.replaceCached(arguments0Value__, arguments1Value_, arguments2Value__, s0_.cachedReplaceValue_, s0_.cachedParsedReplaceValueWithNamedCG_, s0_.cachedParsedReplaceValueWithoutNamedCG_, s0_.toString1Node_, s0_.equalsNode_);
                            }
                            s0_ = s0_.next_;
                        }
                    }
                    if ((state_0 & 2) != 0) {
                        return this.replaceDynamic(arguments0Value__, arguments1Value_, arguments2Value_, this.replaceDynamic_toString1Node_);
                    }
                }
                if ((state_0 & 4) != 0 && JSRegExpReplaceNodeGen.fallbackGuard_(state_0, arguments0Value_, arguments1Value_, arguments2Value_)) {
                    return this.doNoObject(arguments0Value_, arguments1Value_, arguments2Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    if (exclude == 0 && arguments2Value instanceof TruffleString) {
                        TruffleString arguments2Value_ = (TruffleString)arguments2Value;
                        int count0_ = 0;
                        ReplaceCachedData s0_ = this.replaceCached_cache;
                        if ((state_0 & 1) != 0) {
                            while (s0_ != null && !JSGuards.stringEquals(s0_.equalsNode_, s0_.cachedReplaceValue_, arguments2Value_)) {
                                s0_ = s0_.next_;
                                ++count0_;
                            }
                        }
                        if (s0_ == null) {
                            TruffleString cachedReplaceValue__ = arguments2Value_;
                            TruffleString.EqualNode equalsNode__ = super.insert(TruffleString.EqualNode.create());
                            if (JSGuards.stringEquals(equalsNode__, cachedReplaceValue__, arguments2Value_) && count0_ < 3) {
                                s0_ = super.insert(new ReplaceCachedData(this.replaceCached_cache));
                                s0_.cachedReplaceValue_ = cachedReplaceValue__;
                                s0_.cachedParsedReplaceValueWithNamedCG_ = this.parseReplaceValueWithNCG(arguments2Value_);
                                s0_.cachedParsedReplaceValueWithoutNamedCG_ = this.parseReplaceValueWithoutNCG(arguments2Value_);
                                s0_.toString1Node_ = s0_.insertAccessor(JSToStringNode.create());
                                s0_.equalsNode_ = s0_.insertAccessor(equalsNode__);
                                VarHandle.storeStoreFence();
                                this.replaceCached_cache = s0_;
                                this.state_0_ = state_0 |= 1;
                            }
                        }
                        if (s0_ != null) {
                            lock.unlock();
                            hasLock = false;
                            Object object = this.replaceCached(arguments0Value_, arguments1Value, arguments2Value_, s0_.cachedReplaceValue_, s0_.cachedParsedReplaceValueWithNamedCG_, s0_.cachedParsedReplaceValueWithoutNamedCG_, s0_.toString1Node_, s0_.equalsNode_);
                            return object;
                        }
                    }
                    this.replaceDynamic_toString1Node_ = super.insert(JSToStringNode.create());
                    this.exclude_ = exclude |= 1;
                    this.replaceCached_cache = null;
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    Object object = this.replaceDynamic(arguments0Value_, arguments1Value, arguments2Value, this.replaceDynamic_toString1Node_);
                    return object;
                }
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Object object = this.doNoObject(arguments0Value, arguments1Value, arguments2Value);
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
            ReplaceCachedData s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.replaceCached_cache) == null || s0_.next_ == null)) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            ArrayList<List<Object>> cached;
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "replaceCached";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                ReplaceCachedData s0_ = this.replaceCached_cache;
                while (s0_ != null) {
                    cached.add(Arrays.asList(s0_.cachedReplaceValue_, s0_.cachedParsedReplaceValueWithNamedCG_, s0_.cachedParsedReplaceValueWithoutNamedCG_, s0_.toString1Node_, s0_.equalsNode_));
                    s0_ = s0_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "replaceDynamic";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.replaceDynamic_toString1Node_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "doNoObject";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            return (state_0 & 2) != 0 || !(arguments0Value instanceof JSDynamicObject);
        }

        public static RegExpPrototypeBuiltins.JSRegExpReplaceNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRegExpReplaceNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=RegExpPrototypeBuiltins.JSRegExpReplaceNode.class)
        private static final class ReplaceCachedData
        extends Node {
            @Node.Child
            ReplaceCachedData next_;
            @CompilerDirectives.CompilationFinal
            TruffleString cachedReplaceValue_;
            @CompilerDirectives.CompilationFinal(dimensions=1)
            ReplaceStringParser.Token[] cachedParsedReplaceValueWithNamedCG_;
            @CompilerDirectives.CompilationFinal(dimensions=1)
            ReplaceStringParser.Token[] cachedParsedReplaceValueWithoutNamedCG_;
            @Node.Child
            JSToStringNode toString1Node_;
            @Node.Child
            TruffleString.EqualNode equalsNode_;

            ReplaceCachedData(ReplaceCachedData next_) {
                this.next_ = next_;
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

    @GeneratedBy(value=RegExpPrototypeBuiltins.JSRegExpSplitNode.class)
    public static final class JSRegExpSplitNodeGen
    extends RegExpPrototypeBuiltins.JSRegExpSplitNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSRegExpSplitNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            if ((state_0 & 0x1E) == 0 && state_0 != 0) {
                return this.execute_int0(state_0, frameValue);
            }
            if ((state_0 & 0x1D) == 0 && state_0 != 0) {
                return this.execute_long1(state_0, frameValue);
            }
            return this.execute_generic2(state_0, frameValue);
        }

        private Object execute_int0(int state_0, VirtualFrame frameValue) {
            int arguments2Value_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            try {
                arguments2Value_ = this.arguments2_.executeInt(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arguments0Value_, arguments1Value_, ex.getResult());
            }
            assert ((state_0 & 1) != 0);
            if (arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.splitIntLimit(arguments0Value__, arguments1Value_, arguments2Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        private Object execute_long1(int state_0, VirtualFrame frameValue) {
            long arguments2Value_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            try {
                arguments2Value_ = this.arguments2_.executeLong(frameValue);
            }
            catch (UnexpectedResultException ex) {
                CompilerDirectives.transferToInterpreterAndInvalidate();
                return this.executeAndSpecialize(arguments0Value_, arguments1Value_, ex.getResult());
            }
            assert ((state_0 & 2) != 0);
            if (arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                return this.splitLongLimit(arguments0Value__, arguments1Value_, arguments2Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        private Object execute_generic2(int state_0, VirtualFrame frameValue) {
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 0xF) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                    if ((state_0 & 1) != 0 && arguments2Value_ instanceof Integer) {
                        int arguments2Value__ = (Integer)arguments2Value_;
                        return this.splitIntLimit(arguments0Value__, arguments1Value_, arguments2Value__);
                    }
                    if ((state_0 & 2) != 0 && arguments2Value_ instanceof Long) {
                        long arguments2Value__ = (Long)arguments2Value_;
                        return this.splitLongLimit(arguments0Value__, arguments1Value_, arguments2Value__);
                    }
                    if ((state_0 & 0xC) != 0) {
                        if ((state_0 & 4) != 0 && JSGuards.isUndefined(arguments2Value_)) {
                            return this.splitUndefinedLimit(arguments0Value__, arguments1Value_, arguments2Value_);
                        }
                        if ((state_0 & 8) != 0 && !JSGuards.isUndefined(arguments2Value_)) {
                            return this.splitObjectLimit(arguments0Value__, arguments1Value_, arguments2Value_);
                        }
                    }
                }
                if ((state_0 & 0x10) != 0 && JSRegExpSplitNodeGen.fallbackGuard_(state_0, arguments0Value_, arguments1Value_, arguments2Value_)) {
                    return this.doNoObject(arguments0Value_, arguments1Value_, arguments2Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if (arguments2Value instanceof Integer) {
                    int arguments2Value_ = (Integer)arguments2Value;
                    this.state_0_ = state_0 |= 1;
                    return this.splitIntLimit(arguments0Value_, arguments1Value, arguments2Value_);
                }
                if (arguments2Value instanceof Long) {
                    long arguments2Value_ = (Long)arguments2Value;
                    this.state_0_ = state_0 |= 2;
                    return this.splitLongLimit(arguments0Value_, arguments1Value, arguments2Value_);
                }
                if (JSGuards.isUndefined(arguments2Value)) {
                    this.state_0_ = state_0 |= 4;
                    return this.splitUndefinedLimit(arguments0Value_, arguments1Value, arguments2Value);
                }
                if (!JSGuards.isUndefined(arguments2Value)) {
                    this.state_0_ = state_0 |= 8;
                    return this.splitObjectLimit(arguments0Value_, arguments1Value, arguments2Value);
                }
            }
            this.state_0_ = state_0 |= 0x10;
            return this.doNoObject(arguments0Value, arguments1Value, arguments2Value);
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

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[6];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "splitIntLimit";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "splitLongLimit";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            s = new Object[3];
            s[0] = "splitUndefinedLimit";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            s = new Object[3];
            s[0] = "splitObjectLimit";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            s = new Object[3];
            s[0] = "doNoObject";
            s[1] = (state_0 & 0x10) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[5] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            if (arguments0Value instanceof JSDynamicObject) {
                if ((state_0 & 1) == 0 && arguments2Value instanceof Integer) {
                    return false;
                }
                if ((state_0 & 2) == 0 && arguments2Value instanceof Long) {
                    return false;
                }
                if ((state_0 & 4) == 0 && JSGuards.isUndefined(arguments2Value)) {
                    return false;
                }
                if ((state_0 & 8) == 0 && !JSGuards.isUndefined(arguments2Value)) {
                    return false;
                }
            }
            return true;
        }

        public static RegExpPrototypeBuiltins.JSRegExpSplitNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRegExpSplitNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RegExpPrototypeBuiltins.JSRegExpToStringNode.class)
    public static final class JSRegExpToStringNodeGen
    extends RegExpPrototypeBuiltins.JSRegExpToStringNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private ToStringData toString_cache;

        private JSRegExpToStringNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_};
        }

        private boolean fallbackGuard_(int state_0, Object arguments0Value) {
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if ((state_0 & 2) == 0 || this.toString_cache.isObjectNode_.executeBoolean(arguments0Value_)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                ToStringData s0_ = this.toString_cache;
                if (s0_ != null && s0_.isObjectNode_.executeBoolean(arguments0Value__)) {
                    return this.toString(arguments0Value__, s0_.isObjectNode_, s0_.toString1Node_, s0_.toString2Node_);
                }
            }
            if ((state_0 & 4) != 0 && this.fallbackGuard_(state_0, arguments0Value_)) {
                return this.toString(arguments0Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arguments0Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    ToStringData s0_ = this.toString_cache;
                    boolean ToString_duplicateFound_ = false;
                    if ((state_0 & 1) != 0 && s0_.isObjectNode_.executeBoolean(arguments0Value_)) {
                        ToString_duplicateFound_ = true;
                    }
                    if (!ToString_duplicateFound_) {
                        if ((state_0 & 2) == 0) {
                            s0_ = super.insert(new ToStringData());
                            VarHandle.storeStoreFence();
                            this.toString_cache = s0_;
                            s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
                            this.state_0_ = state_0 |= 2;
                        }
                        if (s0_.isObjectNode_.executeBoolean(arguments0Value_) && (state_0 & 1) == 0) {
                            s0_ = super.insert(new ToStringData());
                            s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
                            s0_.toString1Node_ = s0_.insertAccessor(JSToStringNode.create());
                            s0_.toString2Node_ = s0_.insertAccessor(JSToStringNode.create());
                            VarHandle.storeStoreFence();
                            this.toString_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                            ToString_duplicateFound_ = true;
                        }
                    }
                    if (ToString_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        Object object = this.toString(arguments0Value_, s0_.isObjectNode_, s0_.toString1Node_, s0_.toString2Node_);
                        return object;
                    }
                }
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Object object = this.toString(arguments0Value);
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
            if ((state_0 & 5) == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & 5 & (state_0 & 5) - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "toString";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                ToStringData s0_ = this.toString_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.isObjectNode_, s0_.toString1Node_, s0_.toString2Node_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "toString";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static RegExpPrototypeBuiltins.JSRegExpToStringNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRegExpToStringNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=RegExpPrototypeBuiltins.JSRegExpToStringNode.class)
        private static final class ToStringData
        extends Node {
            @Node.Child
            IsJSObjectNode isObjectNode_;
            @Node.Child
            JSToStringNode toString1Node_;
            @Node.Child
            JSToStringNode toString2Node_;

            ToStringData() {
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

    @GeneratedBy(value=RegExpPrototypeBuiltins.JSRegExpTestNode.class)
    public static final class JSRegExpTestNodeGen
    extends RegExpPrototypeBuiltins.JSRegExpTestNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private TestGenericData testGeneric_cache;

        private JSRegExpTestNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        private boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
            if (arguments0Value instanceof JSDynamicObject) {
                JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                if ((state_0 & 2) == 0 || this.testGeneric_cache.isObjectNode_.executeBoolean(arguments0Value_)) {
                    return false;
                }
            }
            return true;
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 5) != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value__ = (JSDynamicObject)arguments0Value_;
                    TestGenericData s0_ = this.testGeneric_cache;
                    if (s0_ != null && s0_.isObjectNode_.executeBoolean(arguments0Value__)) {
                        return this.testGeneric(arguments0Value__, arguments1Value_, s0_.isObjectNode_, s0_.toStringNode_, s0_.regExpNode_);
                    }
                }
                if ((state_0 & 4) != 0 && this.fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
                    return this.testError(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSDynamicObject) {
                    JSDynamicObject arguments0Value_ = (JSDynamicObject)arguments0Value;
                    TestGenericData s0_ = this.testGeneric_cache;
                    boolean TestGeneric_duplicateFound_ = false;
                    if ((state_0 & 1) != 0 && s0_.isObjectNode_.executeBoolean(arguments0Value_)) {
                        TestGeneric_duplicateFound_ = true;
                    }
                    if (!TestGeneric_duplicateFound_) {
                        if ((state_0 & 2) == 0) {
                            s0_ = super.insert(new TestGenericData());
                            VarHandle.storeStoreFence();
                            this.testGeneric_cache = s0_;
                            s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
                            this.state_0_ = state_0 |= 2;
                        }
                        if (s0_.isObjectNode_.executeBoolean(arguments0Value_) && (state_0 & 1) == 0) {
                            s0_ = super.insert(new TestGenericData());
                            s0_.isObjectNode_ = s0_.insertAccessor(IsJSObjectNode.create());
                            s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.create());
                            s0_.regExpNode_ = s0_.insertAccessor(JSRegExpExecIntlNode.create(this.getContext()));
                            VarHandle.storeStoreFence();
                            this.testGeneric_cache = s0_;
                            this.state_0_ = state_0 |= 1;
                            TestGeneric_duplicateFound_ = true;
                        }
                    }
                    if (TestGeneric_duplicateFound_) {
                        lock.unlock();
                        hasLock = false;
                        Object object = this.testGeneric(arguments0Value_, arguments1Value, s0_.isObjectNode_, s0_.toStringNode_, s0_.regExpNode_);
                        return object;
                    }
                }
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Object object = this.testError(arguments0Value, arguments1Value);
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
            if ((state_0 & 5) == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & 5 & (state_0 & 5) - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "testGeneric";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JavaScriptBaseNode>> cached = new ArrayList<List<JavaScriptBaseNode>>();
                TestGenericData s0_ = this.testGeneric_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.isObjectNode_, s0_.toStringNode_, s0_.regExpNode_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "testError";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        public static RegExpPrototypeBuiltins.JSRegExpTestNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRegExpTestNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=RegExpPrototypeBuiltins.JSRegExpTestNode.class)
        private static final class TestGenericData
        extends Node {
            @Node.Child
            IsJSObjectNode isObjectNode_;
            @Node.Child
            JSToStringNode toStringNode_;
            @Node.Child
            JSRegExpExecIntlNode regExpNode_;

            TestGenericData() {
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

    @GeneratedBy(value=RegExpPrototypeBuiltins.JSRegExpExecES5Node.class)
    public static final class JSRegExpExecES5NodeGen
    extends RegExpPrototypeBuiltins.JSRegExpExecES5Node
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private int state_0_;

        private JSRegExpExecES5NodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSRegExpObject) {
                    JSRegExpObject arguments0Value__ = (JSRegExpObject)arguments0Value_;
                    return this.exec(arguments0Value__, arguments1Value_);
                }
                if ((state_0 & 2) != 0 && JSRegExpExecES5NodeGen.fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
                    return this.exec(arguments0Value_, arguments1Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            int state_0 = this.state_0_;
            if (arguments0Value instanceof JSRegExpObject) {
                JSRegExpObject arguments0Value_ = (JSRegExpObject)arguments0Value;
                this.state_0_ = state_0 |= 1;
                return this.exec(arguments0Value_, arguments1Value);
            }
            this.state_0_ = state_0 |= 2;
            return this.exec(arguments0Value, arguments1Value);
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

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "exec";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[1] = s;
            s = new Object[3];
            s[0] = "exec";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
            return (state_0 & 1) != 0 || !(arguments0Value instanceof JSRegExpObject);
        }

        public static RegExpPrototypeBuiltins.JSRegExpExecES5Node create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRegExpExecES5NodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RegExpPrototypeBuiltins.JSRegExpExecNode.class)
    public static final class JSRegExpExecNodeGen
    extends RegExpPrototypeBuiltins.JSRegExpExecNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private JSToStringNode object_toStringNode_;

        private JSRegExpExecNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            if ((state_0 & 3) != 0 && arguments0Value_ instanceof JSRegExpObject) {
                JSRegExpObject arguments0Value__ = (JSRegExpObject)arguments0Value_;
                if ((state_0 & 1) != 0 && arguments1Value_ instanceof TruffleString) {
                    TruffleString arguments1Value__ = (TruffleString)arguments1Value_;
                    return this.doString(arguments0Value__, arguments1Value__);
                }
                if ((state_0 & 2) != 0) {
                    return this.doObject(arguments0Value__, arguments1Value_, this.object_toStringNode_);
                }
            }
            if ((state_0 & 4) != 0 && JSRegExpExecNodeGen.fallbackGuard_(state_0, arguments0Value_, arguments1Value_)) {
                return this.doNoRegExp(arguments0Value_, arguments1Value_);
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (arguments0Value instanceof JSRegExpObject) {
                    JSRegExpObject arguments0Value_ = (JSRegExpObject)arguments0Value;
                    if (exclude == 0 && arguments1Value instanceof TruffleString) {
                        TruffleString arguments1Value_ = (TruffleString)arguments1Value;
                        this.state_0_ = state_0 |= 1;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.doString(arguments0Value_, arguments1Value_);
                        return jSDynamicObject;
                    }
                    this.object_toStringNode_ = super.insert(JSToStringNode.create());
                    this.exclude_ = exclude |= 1;
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.doObject(arguments0Value_, arguments1Value, this.object_toStringNode_);
                    return jSDynamicObject;
                }
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                Object object = this.doNoRegExp(arguments0Value, arguments1Value);
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
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[4];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "doString";
            s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : (exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0));
            data[1] = s;
            s = new Object[3];
            s[0] = "doObject";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                ArrayList<List<JSToStringNode>> cached = new ArrayList<List<JSToStringNode>>();
                cached.add(Arrays.asList(this.object_toStringNode_));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "doNoRegExp";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value) {
            return (state_0 & 2) != 0 || !(arguments0Value instanceof JSRegExpObject);
        }

        public static RegExpPrototypeBuiltins.JSRegExpExecNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRegExpExecNodeGen(context, builtin, arguments);
        }
    }

    @GeneratedBy(value=RegExpPrototypeBuiltins.JSRegExpCompileNode.class)
    public static final class JSRegExpCompileNodeGen
    extends RegExpPrototypeBuiltins.JSRegExpCompileNode
    implements Introspection.Provider {
        @Node.Child
        private JavaScriptNode arguments0_;
        @Node.Child
        private JavaScriptNode arguments1_;
        @Node.Child
        private JavaScriptNode arguments2_;
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @Node.Child
        private CompileData compile_cache;

        private JSRegExpCompileNodeGen(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            super(context, builtin);
            this.arguments0_ = arguments != null && 0 < arguments.length ? arguments[0] : null;
            this.arguments1_ = arguments != null && 1 < arguments.length ? arguments[1] : null;
            this.arguments2_ = arguments != null && 2 < arguments.length ? arguments[2] : null;
        }

        @Override
        public JavaScriptNode[] getArguments() {
            return new JavaScriptNode[]{this.arguments0_, this.arguments1_, this.arguments2_};
        }

        @Override
        public Object execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object arguments0Value_ = this.arguments0_.execute(frameValue);
            Object arguments1Value_ = this.arguments1_.execute(frameValue);
            Object arguments2Value_ = this.arguments2_.execute(frameValue);
            if (state_0 != 0) {
                if ((state_0 & 1) != 0 && arguments0Value_ instanceof JSRegExpObject) {
                    JSRegExpObject arguments0Value__ = (JSRegExpObject)arguments0Value_;
                    CompileData s0_ = this.compile_cache;
                    if (s0_ != null) {
                        return this.compile(arguments0Value__, arguments1Value_, arguments2Value_, s0_.compileRegexNode_, s0_.toStringNode_, s0_.isRegExpProfile_, s0_.compiledRegexAccessor_, s0_.flagsAccessor_);
                    }
                }
                if ((state_0 & 2) != 0 && JSRegExpCompileNodeGen.fallbackGuard_(state_0, arguments0Value_, arguments1Value_, arguments2Value_)) {
                    return this.compile(arguments0Value_, arguments1Value_, arguments2Value_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(arguments0Value_, arguments1Value_, arguments2Value_);
        }

        @Override
        public void executeVoid(VirtualFrame frameValue) {
            this.execute(frameValue);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private Object executeAndSpecialize(Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                if (arguments0Value instanceof JSRegExpObject) {
                    JSRegExpObject arguments0Value_ = (JSRegExpObject)arguments0Value;
                    CompileData s0_ = super.insert(new CompileData());
                    s0_.compileRegexNode_ = s0_.insertAccessor(CompileRegexNode.create(this.getContext()));
                    s0_.toStringNode_ = s0_.insertAccessor(JSToStringNode.createUndefinedToEmpty());
                    s0_.isRegExpProfile_ = ConditionProfile.createBinaryProfile();
                    s0_.compiledRegexAccessor_ = s0_.insertAccessor(TRegexUtil.TRegexCompiledRegexAccessor.create());
                    s0_.flagsAccessor_ = s0_.insertAccessor(TRegexUtil.TRegexFlagsAccessor.create());
                    VarHandle.storeStoreFence();
                    this.compile_cache = s0_;
                    this.state_0_ = state_0 |= 1;
                    lock.unlock();
                    hasLock = false;
                    JSRegExpObject jSRegExpObject = this.compile(arguments0Value_, arguments1Value, arguments2Value, s0_.compileRegexNode_, s0_.toStringNode_, s0_.isRegExpProfile_, s0_.compiledRegexAccessor_, s0_.flagsAccessor_);
                    return jSRegExpObject;
                }
                this.state_0_ = state_0 |= 2;
                lock.unlock();
                hasLock = false;
                Object object = this.compile(arguments0Value, arguments1Value, arguments2Value);
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
            if ((state_0 & state_0 - 1) == 0) {
                return NodeCost.MONOMORPHIC;
            }
            return NodeCost.POLYMORPHIC;
        }

        @Override
        public Introspection getIntrospectionData() {
            Object[] data = new Object[3];
            data[0] = 0;
            int state_0 = this.state_0_;
            Object[] s = new Object[3];
            s[0] = "compile";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                ArrayList<List<Cloneable>> cached = new ArrayList<List<Cloneable>>();
                CompileData s0_ = this.compile_cache;
                if (s0_ != null) {
                    cached.add(Arrays.asList(s0_.compileRegexNode_, s0_.toStringNode_, s0_.isRegExpProfile_, s0_.compiledRegexAccessor_, s0_.flagsAccessor_));
                }
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "compile";
            s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[2] = s;
            return Introspection.Provider.create(data);
        }

        private static boolean fallbackGuard_(int state_0, Object arguments0Value, Object arguments1Value, Object arguments2Value) {
            return (state_0 & 1) != 0 || !(arguments0Value instanceof JSRegExpObject);
        }

        public static RegExpPrototypeBuiltins.JSRegExpCompileNode create(JSContext context, JSBuiltin builtin, JavaScriptNode[] arguments) {
            return new JSRegExpCompileNodeGen(context, builtin, arguments);
        }

        @GeneratedBy(value=RegExpPrototypeBuiltins.JSRegExpCompileNode.class)
        private static final class CompileData
        extends Node {
            @Node.Child
            CompileRegexNode compileRegexNode_;
            @Node.Child
            JSToStringNode toStringNode_;
            @CompilerDirectives.CompilationFinal
            ConditionProfile isRegExpProfile_;
            @Node.Child
            TRegexUtil.TRegexCompiledRegexAccessor compiledRegexAccessor_;
            @Node.Child
            TRegexUtil.TRegexFlagsAccessor flagsAccessor_;

            CompileData() {
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

