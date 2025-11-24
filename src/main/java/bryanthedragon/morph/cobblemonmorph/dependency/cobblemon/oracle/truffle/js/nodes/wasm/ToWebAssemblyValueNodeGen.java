
package com.oracle.truffle.js.nodes.wasm;

import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.wasm.ToWebAssemblyValueNode;

@GeneratedBy(value=ToWebAssemblyValueNode.class)
public final class ToWebAssemblyValueNodeGen
extends ToWebAssemblyValueNode
implements Introspection.Provider {
    private ToWebAssemblyValueNodeGen() {
    }

    @Override
    public Object execute(Object arg0Value, TruffleString arg1Value) {
        return this.convert(arg0Value, arg1Value);
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

    public static ToWebAssemblyValueNode create() {
        return new ToWebAssemblyValueNodeGen();
    }

    @GeneratedBy(value=ToWebAssemblyValueNode.Uncached.class)
    static final class UncachedNodeGen
    extends ToWebAssemblyValueNode.Uncached
    implements Introspection.Provider {
        private UncachedNodeGen() {
        }

        @Override
        public Object execute(Object arg0Value, TruffleString arg1Value) {
            return this.convert(arg0Value, arg1Value);
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

        public static ToWebAssemblyValueNode.Uncached create() {
            return new UncachedNodeGen();
        }
    }
}

