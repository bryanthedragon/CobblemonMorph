
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.dsl.UnsupportedSpecializationException;
import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.library.LibraryFactory;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.object.DynamicObjectLibrary;
import com.oracle.truffle.api.object.Shape;
import com.oracle.truffle.js.nodes.JSGuards;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.access.CreateObjectNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSClass;
import com.oracle.truffle.js.runtime.objects.JSDynamicObject;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=CreateObjectNode.class)
public final class CreateObjectNodeFactory {
    private static final LibraryFactory<DynamicObjectLibrary> DYNAMIC_OBJECT_LIBRARY_ = LibraryFactory.resolve(DynamicObjectLibrary.class);

    @GeneratedBy(value=CreateObjectNode.CreateObjectWithCachedPrototypeNode.class)
    protected static final class CreateObjectWithCachedPrototypeNodeGen
    extends CreateObjectNode.CreateObjectWithCachedPrototypeNode
    implements Introspection.Provider {
        @CompilerDirectives.CompilationFinal
        private volatile int state_0_;
        @CompilerDirectives.CompilationFinal
        private volatile int exclude_;
        @Node.Child
        private DynamicObjectLibrary setProtoNode;
        @CompilerDirectives.CompilationFinal
        private JSDynamicObject cachedPrototype_cachedPrototype_;
        @CompilerDirectives.CompilationFinal
        private Shape cachedPrototype_protoChildShape_;

        private CreateObjectWithCachedPrototypeNodeGen(JSContext context, JavaScriptNode prototypeExpression, JSClass jsclass) {
            super(context, prototypeExpression, jsclass);
        }

        @Override
        public JSDynamicObject execute(JSDynamicObject prototypeExpressionValue) {
            int state_0 = this.state_0_;
            if ((state_0 & 7) != 0) {
                if ((state_0 & 1) != 0) {
                    assert (!this.context.isMultiContext());
                    assert (JSGuards.isValidPrototype(this.cachedPrototype_cachedPrototype_));
                    if (prototypeExpressionValue == this.cachedPrototype_cachedPrototype_) {
                        return this.doCachedPrototype(prototypeExpressionValue, this.cachedPrototype_cachedPrototype_, this.cachedPrototype_protoChildShape_);
                    }
                }
                if ((state_0 & 2) != 0) {
                    assert (this.isOrdinaryObject());
                    if (JSGuards.isValidPrototype(prototypeExpressionValue)) {
                        return this.doOrdinaryInstancePrototype(prototypeExpressionValue, this.setProtoNode);
                    }
                }
                if ((state_0 & 4) != 0) {
                    assert (this.isPromiseObject());
                    if (JSGuards.isValidPrototype(prototypeExpressionValue)) {
                        return this.doPromiseInstancePrototype(prototypeExpressionValue, this.setProtoNode);
                    }
                }
            }
            if ((state_0 & 8) != 0) {
                assert (this.isOrdinaryObject() || this.isPromiseObject());
                if (!JSGuards.isValidPrototype(prototypeExpressionValue)) {
                    return this.doNotJSObjectOrNull(prototypeExpressionValue);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(prototypeExpressionValue);
        }

        @Override
        public JSDynamicObject execute(VirtualFrame frameValue) {
            int state_0 = this.state_0_;
            Object prototypeExpressionValue_ = this.prototypeExpression.execute(frameValue);
            if ((state_0 & 7) != 0 && prototypeExpressionValue_ instanceof JSDynamicObject) {
                JSDynamicObject prototypeExpressionValue__ = (JSDynamicObject)prototypeExpressionValue_;
                if ((state_0 & 1) != 0) {
                    assert (!this.context.isMultiContext());
                    assert (JSGuards.isValidPrototype(this.cachedPrototype_cachedPrototype_));
                    if (prototypeExpressionValue__ == this.cachedPrototype_cachedPrototype_) {
                        return this.doCachedPrototype(prototypeExpressionValue__, this.cachedPrototype_cachedPrototype_, this.cachedPrototype_protoChildShape_);
                    }
                }
                if ((state_0 & 2) != 0) {
                    assert (this.isOrdinaryObject());
                    if (JSGuards.isValidPrototype(prototypeExpressionValue__)) {
                        return this.doOrdinaryInstancePrototype(prototypeExpressionValue__, this.setProtoNode);
                    }
                }
                if ((state_0 & 4) != 0) {
                    assert (this.isPromiseObject());
                    if (JSGuards.isValidPrototype(prototypeExpressionValue__)) {
                        return this.doPromiseInstancePrototype(prototypeExpressionValue__, this.setProtoNode);
                    }
                }
            }
            if ((state_0 & 8) != 0) {
                assert (this.isOrdinaryObject() || this.isPromiseObject());
                if (!JSGuards.isValidPrototype(prototypeExpressionValue_)) {
                    return this.doNotJSObjectOrNull(prototypeExpressionValue_);
                }
            }
            CompilerDirectives.transferToInterpreterAndInvalidate();
            return this.executeAndSpecialize(prototypeExpressionValue_);
        }

        private JSDynamicObject executeAndSpecialize(Object prototypeExpressionValue) {
            Lock lock = this.getLock();
            boolean hasLock = true;
            lock.lock();
            try {
                int state_0 = this.state_0_;
                int exclude = this.exclude_;
                if (prototypeExpressionValue instanceof JSDynamicObject) {
                    JSDynamicObject prototypeExpressionValue_ = (JSDynamicObject)prototypeExpressionValue;
                    if (exclude == 0) {
                        JSDynamicObject cachedPrototype_cachedPrototype__;
                        boolean CachedPrototype_duplicateFound_ = false;
                        if ((state_0 & 1) != 0) {
                            assert (!this.context.isMultiContext());
                            assert (JSGuards.isValidPrototype(this.cachedPrototype_cachedPrototype_));
                            if (prototypeExpressionValue_ == this.cachedPrototype_cachedPrototype_) {
                                CachedPrototype_duplicateFound_ = true;
                            }
                        }
                        if (!CachedPrototype_duplicateFound_ && !this.context.isMultiContext() && JSGuards.isValidPrototype(cachedPrototype_cachedPrototype__ = prototypeExpressionValue_) && (state_0 & 1) == 0) {
                            this.cachedPrototype_cachedPrototype_ = cachedPrototype_cachedPrototype__;
                            this.cachedPrototype_protoChildShape_ = this.getProtoChildShape(cachedPrototype_cachedPrototype__);
                            this.state_0_ = state_0 |= 1;
                            CachedPrototype_duplicateFound_ = true;
                        }
                        if (CachedPrototype_duplicateFound_) {
                            lock.unlock();
                            hasLock = false;
                            JSDynamicObject jSDynamicObject = this.doCachedPrototype(prototypeExpressionValue_, this.cachedPrototype_cachedPrototype_, this.cachedPrototype_protoChildShape_);
                            return jSDynamicObject;
                        }
                    }
                    if (this.isOrdinaryObject() && JSGuards.isValidPrototype(prototypeExpressionValue_)) {
                        this.setProtoNode = super.insert(this.setProtoNode == null ? DYNAMIC_OBJECT_LIBRARY_.createDispatched(3) : this.setProtoNode);
                        this.exclude_ = exclude |= 1;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 2;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.doOrdinaryInstancePrototype(prototypeExpressionValue_, this.setProtoNode);
                        return jSDynamicObject;
                    }
                    if (this.isPromiseObject() && JSGuards.isValidPrototype(prototypeExpressionValue_)) {
                        this.setProtoNode = super.insert(this.setProtoNode == null ? DYNAMIC_OBJECT_LIBRARY_.createDispatched(3) : this.setProtoNode);
                        this.exclude_ = exclude |= 1;
                        state_0 &= 0xFFFFFFFE;
                        this.state_0_ = state_0 |= 4;
                        lock.unlock();
                        hasLock = false;
                        JSDynamicObject jSDynamicObject = this.doPromiseInstancePrototype(prototypeExpressionValue_, this.setProtoNode);
                        return jSDynamicObject;
                    }
                }
                if ((this.isOrdinaryObject() || this.isPromiseObject()) && !JSGuards.isValidPrototype(prototypeExpressionValue)) {
                    this.state_0_ = state_0 |= 8;
                    lock.unlock();
                    hasLock = false;
                    JSDynamicObject jSDynamicObject = this.doNotJSObjectOrNull(prototypeExpressionValue);
                    return jSDynamicObject;
                }
                throw new UnsupportedSpecializationException(this, new Node[]{this.prototypeExpression}, prototypeExpressionValue);
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
            ArrayList<List<Object>> cached;
            Object[] data = new Object[5];
            data[0] = 0;
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            Object[] s = new Object[3];
            s[0] = "doCachedPrototype";
            if ((state_0 & 1) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList<List<Object>>();
                cached.add(Arrays.asList(this.cachedPrototype_cachedPrototype_, this.cachedPrototype_protoChildShape_));
                s[2] = cached;
            } else {
                s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
            }
            data[1] = s;
            s = new Object[3];
            s[0] = "doOrdinaryInstancePrototype";
            if ((state_0 & 2) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.setProtoNode));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[2] = s;
            s = new Object[3];
            s[0] = "doPromiseInstancePrototype";
            if ((state_0 & 4) != 0) {
                s[1] = (byte)1;
                cached = new ArrayList();
                cached.add(Arrays.asList(this.setProtoNode));
                s[2] = cached;
            } else {
                s[1] = (byte)0;
            }
            data[3] = s;
            s = new Object[3];
            s[0] = "doNotJSObjectOrNull";
            s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
            data[4] = s;
            return Introspection.Provider.create(data);
        }

        public static CreateObjectNode.CreateObjectWithCachedPrototypeNode create(JSContext context, JavaScriptNode prototypeExpression, JSClass jsclass) {
            return new CreateObjectWithCachedPrototypeNodeGen(context, prototypeExpression, jsclass);
        }
    }
}

