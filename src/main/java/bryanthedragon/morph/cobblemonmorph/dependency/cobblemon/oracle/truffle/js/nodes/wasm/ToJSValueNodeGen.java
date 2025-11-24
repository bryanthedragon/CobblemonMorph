
package com.oracle.truffle.js.nodes.wasm;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.DenyReplace;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.wasm.ToJSValueNode;

@GeneratedBy(value=ToJSValueNode.class)
public final class ToJSValueNodeGen
extends ToJSValueNode
implements Introspection.Provider {
    private static final Uncached UNCACHED = new Uncached();

    private ToJSValueNodeGen() {
    }

    @Override
    public Object execute(Object arg0Value) {
        return this.convert(arg0Value);
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
        s[0] = "convert";
        s[1] = (byte)1;
        data[1] = s;
        return Introspection.Provider.create(data);
    }

    public static ToJSValueNode create() {
        return new ToJSValueNodeGen();
    }

    public static ToJSValueNode getUncached() {
        return UNCACHED;
    }

    @GeneratedBy(value=ToJSValueNode.class)
    @DenyReplace
    private static final class Uncached
    extends ToJSValueNode {
        private Uncached() {
        }

        @Override
        @CompilerDirectives.TruffleBoundary
        public Object execute(Object arg0Value) {
            return this.convert(arg0Value);
        }

        @Override
        public NodeCost getCost() {
            return NodeCost.MEGAMORPHIC;
        }

        @Override
        public boolean isAdoptable() {
            return false;
        }
    }
}

