
package com.oracle.truffle.js.nodes.wasm;

import com.oracle.truffle.api.CompilerDirectives;
import com.oracle.truffle.api.dsl.GeneratedBy;
import com.oracle.truffle.api.dsl.Introspection;
import com.oracle.truffle.api.nodes.NodeCost;
import com.oracle.truffle.js.nodes.wasm.ExportByteSourceNode;
import com.oracle.truffle.js.runtime.JSContext;
import com.oracle.truffle.js.runtime.builtins.JSArrayBufferObject;
import com.oracle.truffle.js.runtime.builtins.JSDataViewObject;
import com.oracle.truffle.js.runtime.builtins.JSTypedArrayObject;

@GeneratedBy(value=ExportByteSourceNode.class)
public final class ExportByteSourceNodeGen
extends ExportByteSourceNode
implements Introspection.Provider {
    @CompilerDirectives.CompilationFinal
    private int state_0_;

    private ExportByteSourceNodeGen(JSContext context, String nonByteSourceMessage, String emptyByteSourceMessage) {
        super(context, nonByteSourceMessage, emptyByteSourceMessage);
    }

    @Override
    public Object execute(Object arg0Value) {
        int state_0 = this.state_0_;
        if ((state_0 & 1) != 0 && arg0Value instanceof JSArrayBufferObject) {
            JSArrayBufferObject arg0Value_ = (JSArrayBufferObject)arg0Value;
            return this.exportBuffer(arg0Value_);
        }
        if ((state_0 & 2) != 0 && arg0Value instanceof JSTypedArrayObject) {
            JSTypedArrayObject arg0Value_ = (JSTypedArrayObject)arg0Value;
            return this.exportTypedArray(arg0Value_);
        }
        if ((state_0 & 4) != 0 && arg0Value instanceof JSDataViewObject) {
            JSDataViewObject arg0Value_ = (JSDataViewObject)arg0Value;
            return this.exportDataView(arg0Value_);
        }
        if ((state_0 & 8) != 0 && ExportByteSourceNodeGen.fallbackGuard_(state_0, arg0Value)) {
            return this.exportOther(arg0Value);
        }
        CompilerDirectives.transferToInterpreterAndInvalidate();
        return this.executeAndSpecialize(arg0Value);
    }

    private Object executeAndSpecialize(Object arg0Value) {
        int state_0 = this.state_0_;
        if (arg0Value instanceof JSArrayBufferObject) {
            JSArrayBufferObject arg0Value_ = (JSArrayBufferObject)arg0Value;
            this.state_0_ = state_0 |= 1;
            return this.exportBuffer(arg0Value_);
        }
        if (arg0Value instanceof JSTypedArrayObject) {
            JSTypedArrayObject arg0Value_ = (JSTypedArrayObject)arg0Value;
            this.state_0_ = state_0 |= 2;
            return this.exportTypedArray(arg0Value_);
        }
        if (arg0Value instanceof JSDataViewObject) {
            JSDataViewObject arg0Value_ = (JSDataViewObject)arg0Value;
            this.state_0_ = state_0 |= 4;
            return this.exportDataView(arg0Value_);
        }
        this.state_0_ = state_0 |= 8;
        return this.exportOther(arg0Value);
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
        Object[] data = new Object[5];
        data[0] = 0;
        int state_0 = this.state_0_;
        Object[] s = new Object[3];
        s[0] = "exportBuffer";
        s[1] = (state_0 & 1) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[1] = s;
        s = new Object[3];
        s[0] = "exportTypedArray";
        s[1] = (state_0 & 2) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[2] = s;
        s = new Object[3];
        s[0] = "exportDataView";
        s[1] = (state_0 & 4) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[3] = s;
        s = new Object[3];
        s[0] = "exportOther";
        s[1] = (state_0 & 8) != 0 ? Byte.valueOf((byte)1) : Byte.valueOf((byte)0);
        data[4] = s;
        return Introspection.Provider.create(data);
    }

    private static boolean fallbackGuard_(int state_0, Object arg0Value) {
        if ((state_0 & 1) == 0 && arg0Value instanceof JSArrayBufferObject) {
            return false;
        }
        if ((state_0 & 2) == 0 && arg0Value instanceof JSTypedArrayObject) {
            return false;
        }
        return (state_0 & 4) != 0 || !(arg0Value instanceof JSDataViewObject);
    }

    public static ExportByteSourceNode create(JSContext context, String nonByteSourceMessage, String emptyByteSourceMessage) {
        return new ExportByteSourceNodeGen(context, nonByteSourceMessage, emptyByteSourceMessage);
    }
}

