/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  java.lang.invoke.VarHandle
 */
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.ExplodeLoop;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.ObjectLiteralNode;
import com.oracle.truffle.js.runtime.JSRealm;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.lang.invoke.VarHandle;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=ObjectLiteralNode.class)
public final class ObjectLiteralNodeFactory {
    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);

    @GeneratedBy(value=ObjectLiteralNode.ComputedObjectLiteralDataMemberNode.class)
    public static final class ComputedObjectLiteralDataMemberNodeGen
    extends ObjectLiteralNode.ComputedObjectLiteralDataMemberNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private NoFieldNoFunctionDef0Data noFieldNoFunctionDef0_cache;

        private ComputedObjectLiteralDataMemberNodeGen(JavaScriptNode key, boolean isStatic, int attributes, JavaScriptNode valueNode, boolean isField, boolean isAnonymousFunctionDefinition) {
            super(key, isStatic, attributes, valueNode, isField, isAnonymousFunctionDefinition);
        }

        @Override
        @ExplodeLoop
        public void executeVoid(VirtualFrame frameValue, JSDynamicObject arg0Value, JSDynamicObject arg1Value, JSRealm arg2Value) {
            int state_0 = this.state_0_;
            if (state_0 != 0) {
                if ((state_0 & 1) != 0) {
                    NoFieldNoFunctionDef0Data s0_ = this.noFieldNoFunctionDef0_cache;
                    while (s0_ != null) {
                        if (s0_.dynamicObject_.accepts(arg0Value)) {
                            assert (!this.isFieldOrStaticBlock);
                            assert (!this.isAnonymousFunctionDefinition);
                            assert (this.setFunctionName == null);
                            assert (!ObjectLiteralNode.ObjectLiteralMemberNode.isMethodNode(this.valueNode));
                            this.doNoFieldNoFunctionDef(frameValue, arg0Value, arg1Value, arg2Value, s0_.dynamicObject_);
                            return;
                        }
                        s0_ = s0_.next_;
                    }
                }
                if ((state_0 & 2) != 0) {
                    DynamicObjectLibrary noFieldNoFunctionDef1_dynamicObject__ = DYNAMIC_OBJECT_LIBRARY_.getUncached();
                    assert (!this.isFieldOrStaticBlock);
                    assert (!this.isAnonymousFunctionDefinition);
                    assert (this.setFunctionName == null);
                    assert (!ObjectLiteralNode.ObjectLiteralMemberNode.isMethodNode(this.valueNode));
                    this.doNoFieldNoFunctionDef(frameValue, arg0Value, arg1Value, arg2Value, noFieldNoFunctionDef1_dynamicObject__);
                    return;
                }
                if ((state_0 & 4) != 0) {
                    this.doGeneric(frameValue, arg0Value, arg1Value, arg2Value);
                    return;
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            this.executeAndSpecialize(frameValue, arg0Value, arg1Value, arg2Value);
        }

        /*
         * WARNING - Removed try catching itself - possible behaviour change.
         */
        private void executeAndSpecialize(VirtualFrame frameValue, JSDynamicObject arg0Value, JSDynamicObject arg1Value, JSRealm arg2Value) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (exclude == 0) {
                    int count0_ = 0;
                    NoFieldNoFunctionDef0Data s0_ = this.noFieldNoFunctionDef0_cache;
                    if ((state_0 & 1) != 0) {
                        while (s0_ != null) {
                            if (s0_.dynamicObject_.accepts(arg0Value)) {
                                assert (!this.isFieldOrStaticBlock);
                                assert (!this.isAnonymousFunctionDefinition);
                                assert (this.setFunctionName == null);
                                assert (!ObjectLiteralNode.ObjectLiteralMemberNode.isMethodNode(this.valueNode));
                                break;
                            }
                            s0_ = s0_.next_;
                            ++count0_;
                        }
                    }
                    if (!(s0_ != null || this.isFieldOrStaticBlock || this.isAnonymousFunctionDefinition || this.setFunctionName != null || ObjectLiteralNode.ObjectLiteralMemberNode.isMethodNode(this.valueNode) || count0_ >= 3)) {
                        s0_ = super.insert(new NoFieldNoFunctionDef0Data(this.noFieldNoFunctionDef0_cache));
                        s0_.dynamicObject_ = s0_.insertAccessor(DYNAMIC_OBJECT_LIBRARY_.create(arg0Value));
                        VarHandle.storeStoreFence();
                        this.noFieldNoFunctionDef0_cache = s0_;
                        this.state_0_ = state_0 |= 1;
                    }
                    if (s0_ != null) {
                        lock.unlock();
                        hasLock = false;
                        this.doNoFieldNoFunctionDef(frameValue, arg0Value, arg1Value, arg2Value, s0_.dynamicObject_);
                        return;
                    }
                }
                DynamicObjectLibrary noFieldNoFunctionDef1_dynamicObject__ = null;
                if (!(this.isFieldOrStaticBlock || this.isAnonymousFunctionDefinition || this.setFunctionName != null || ObjectLiteralNode.ObjectLiteralMemberNode.isMethodNode(this.valueNode))) {
                    noFieldNoFunctionDef1_dynamicObject__ = DYNAMIC_OBJECT_LIBRARY_.getUncached();
                    this.exclude_ = exclude |= 1;
                    this.noFieldNoFunctionDef0_cache = null;
                    state_0 &= 0xFFFFFFFE;
                    this.state_0_ = state_0 |= 2;
                    lock.unlock();
                    hasLock = false;
                    this.doNoFieldNoFunctionDef(frameValue, arg0Value, arg1Value, arg2Value, noFieldNoFunctionDef1_dynamicObject__);
                    return;
                }
                this.state_0_ = state_0 |= 4;
                lock.unlock();
                hasLock = false;
                this.doGeneric(frameValue, arg0Value, arg1Value, arg2Value);
                return;
            }
            finally {
                if (hasLock) {
                    lock.unlock();
                }
            }
        }

        @Override
        public NodeCost getCost() {
            NoFieldNoFunctionDef0Data s0_;
            int state_0 = this.state_0_;
            if (state_0 == 0) {
                return NodeCost.UNINITIALIZED;
            }
            if ((state_0 & state_0 - 1) == 0 && ((s0_ = this.noFieldNoFunctionDef0_cache) == null || s0_.next_ == null)) {
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
            s[0] = "doNoFieldNoFunctionDef";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                NoFieldNoFunctionDef0Data s0_ = this.noFieldNoFunctionDef0_cache;
                while (s0_ != null) {
                    cached.add(Arrays.asList(s0_.dynamicObject_));
                    s0_ = s0_.next_;
                }
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doNoFieldNoFunctionDef";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(new Object[0]));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "doGeneric";
            s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[3] = s;
            return Introspection.Provider.create(data);
        }

        public static ObjectLiteralNode.ComputedObjectLiteralDataMemberNode create(JavaScriptNode key, boolean isStatic, int attributes, JavaScriptNode valueNode, boolean isField, boolean isAnonymousFunctionDefinition) {
            return new ComputedObjectLiteralDataMemberNodeGen(key, isStatic, attributes, valueNode, isField, isAnonymousFunctionDefinition);
        }

        @GeneratedBy(value=ObjectLiteralNode.ComputedObjectLiteralDataMemberNode.class)
        private static final class NoFieldNoFunctionDef0Data
        extends Node {
            @Node.Child
            NoFieldNoFunctionDef0Data next_;
            @Node.Child
            DynamicObjectLibrary dynamicObject_;

            NoFieldNoFunctionDef0Data(NoFieldNoFunctionDef0Data next_) {
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
}

