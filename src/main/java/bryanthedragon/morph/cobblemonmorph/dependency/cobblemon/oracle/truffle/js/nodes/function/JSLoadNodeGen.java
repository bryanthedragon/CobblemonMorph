
package com.oracle.truffle.js.nodes.function;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.DirectCallNode;
import com.oracle.truffle.api.nodes.IndirectCallNode;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.source.Source;
import com.oracle.truffle.js.nodes.function.JSLoadNode;
import com.oracle.truffle.js.nodes.interop.ImportValueNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.JSRealm;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.locks.Lock;

@GeneratedBy(value=JSLoadNode.class)
public final class JSLoadNodeGen
extends JSLoadNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private volatile int state_0_;
    @CompilerDirectives.CompilationFinal
    private volatile int exclude_;
    @Node.Child
    private ImportValueNode importValue;
    @CompilerDirectives.CompilationFinal
    private Source cachedLoad_cachedSource_;
    @Node.Child
    private DirectCallNode cachedLoad_callNode_;
    @Node.Child
    private IndirectCallNode uncachedLoad_callNode_;

    private JSLoadNodeGen(JSContext context) {
        super(context);
    }

    @Override
    public Object executeLoad(Source arg0Value, JSRealm arg1Value) {
        int state_0 = this.state_0_;
        if (state_0 != 0) {
            if ((state_0 & 1) != 0) {
                assert (this.cachedLoad_cachedSource_.isCached());
                if (JSLoadNode.equals(arg0Value, this.cachedLoad_cachedSource_)) {
                    return JSLoadNode.cachedLoad(arg0Value, arg1Value, this.importValue, this.cachedLoad_cachedSource_, this.cachedLoad_callNode_);
                }
            }
            if ((state_0 & 2) != 0) {
                return JSLoadNode.uncachedLoad(arg0Value, arg1Value, this.importValue, this.uncachedLoad_callNode_);
            }
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value, arg1Value);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    private Object executeAndSpecialize(Source arg0Value, JSRealm arg1Value) {
        Lock lock = this.getLock();
        boolean hasLock = true;
        lock.lock();
        try {
            int state_0 = this.state_0_;
            int exclude = this.exclude_;
            if (exclude == 0) {
                Source cachedLoad_cachedSource__;
                boolean CachedLoad_duplicateFound_ = false;
                if ((state_0 & 1) != 0) {
                    assert (this.cachedLoad_cachedSource_.isCached());
                    if (JSLoadNode.equals(arg0Value, this.cachedLoad_cachedSource_)) {
                        CachedLoad_duplicateFound_ = true;
                    }
                }
                if (!CachedLoad_duplicateFound_ && (cachedLoad_cachedSource__ = arg0Value).isCached() && JSLoadNode.equals(arg0Value, cachedLoad_cachedSource__) && (state_0 & 1) == 0) {
                    this.importValue = super.insert(this.importValue == null ? ImportValueNode.create() : this.importValue);
                    this.cachedLoad_cachedSource_ = cachedLoad_cachedSource__;
                    this.cachedLoad_callNode_ = super.insert(DirectCallNode.create(JSLoadNode.loadScript(arg0Value, arg1Value)));
                    this.state_0_ = state_0 |= 1;
                    CachedLoad_duplicateFound_ = true;
                }
                if (CachedLoad_duplicateFound_) {
                    lock.unlock();
                    hasLock = false;
                    Object object = JSLoadNode.cachedLoad(arg0Value, arg1Value, this.importValue, this.cachedLoad_cachedSource_, this.cachedLoad_callNode_);
                    return object;
                }
            }
            this.importValue = super.insert(this.importValue == null ? ImportValueNode.create() : this.importValue);
            this.uncachedLoad_callNode_ = super.insert(IndirectCallNode.create());
            this.exclude_ = exclude |= 1;
            state_0 &= 0xFFFFFFFE;
            this.state_0_ = state_0 |= 2;
            lock.unlock();
            hasLock = false;
            Object object = JSLoadNode.uncachedLoad(arg0Value, arg1Value, this.importValue, this.uncachedLoad_callNode_);
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
        ArrayList<List<Object>> cached;
        Object[] data = new Object[3];
        data[0] = 0;
        int state_0 = this.state_0_;
        int exclude = this.exclude_;
        Object[] s = new Object[3];
        s[0] = "cachedLoad";
        if ((state_0 & 1) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList<List<Object>>();
            cached.add(Arrays.asList(this.importValue, this.cachedLoad_cachedSource_, this.cachedLoad_callNode_));
            s[2] = cached;
        } else {
            s[1] = exclude != 0 ? Byte.valueOf((byte)2) : Byte.valueOf((byte)0);
        }
        data[1] = s;
        s = new Object[3];
        s[0] = "uncachedLoad";
        if ((state_0 & 2) != 0) {
            s[1] = (byte)1;
            cached = new ArrayList();
            cached.add(Arrays.asList(this.importValue, this.uncachedLoad_callNode_));
            s[2] = cached;
        } else {
            s[1] = (byte)0;
        }
        data[2] = s;
        return Introspection.Provider.create(data);
    }

    public static JSLoadNode create(JSContext context) {
        return new JSLoadNodeGen(context);
    }
}

