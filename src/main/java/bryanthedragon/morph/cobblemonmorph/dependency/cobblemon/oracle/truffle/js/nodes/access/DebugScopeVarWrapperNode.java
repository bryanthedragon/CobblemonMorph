
package com.oracle.truffle.js.nodes.access;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.Tag;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.strings.TruffleString;
import com.oracle.truffle.js.nodes.JSNodeUtil;
import com.oracle.truffle.js.nodes.JavaScriptNode;
import com.oracle.truffle.js.nodes.ReadNode;
import com.oracle.truffle.js.nodes.access.JSTargetableNode;
import com.oracle.truffle.js.nodes.access.VarWrapperNode;
import com.oracle.truffle.js.nodes.access.WriteNode;
import com.oracle.truffle.js.nodes.access.WritePropertyNode;
import com.oracle.truffle.js.runtime.Errors;
import com.oracle.truffle.js.runtime.Strings;
import java.util.Objects;
import java.util.Set;

public final class DebugScopeVarWrapperNode
extends VarWrapperNode
implements ReadNode,
WriteNode {
    private final TruffleString varName;
    @Node.Child
    private JavaScriptNode dynamicScopeNode;
    @Node.Child
    private JavaScriptNode defaultDelegate;
    @Node.Child
    private JSTargetableNode scopeAccessNode;
    @Node.Child
    private InteropLibrary scopeInterop;

    public DebugScopeVarWrapperNode(TruffleString varName, JavaScriptNode defaultDelegate, JavaScriptNode dynamicScope, JSTargetableNode scopeAccessNode) {
        this.varName = varName;
        this.dynamicScopeNode = dynamicScope;
        this.defaultDelegate = Objects.requireNonNull(defaultDelegate);
        this.scopeAccessNode = scopeAccessNode;
        this.scopeInterop = InteropLibrary.getFactory().createDispatched(5);
    }

    @Override
    public JavaScriptNode getDelegateNode() {
        return this.defaultDelegate;
    }

    @Override
    public boolean hasTag(Class<? extends Tag> tag) {
        return false;
    }

    @Override
    public boolean isInstrumentable() {
        return false;
    }

    private boolean isWrite() {
        return this.scopeAccessNode instanceof WritePropertyNode;
    }

    @Override
    public Object execute(VirtualFrame frame) {
        Object dynamicScope = this.dynamicScopeNode.execute(frame);
        if (this.scopeInterop.isMemberReadable(dynamicScope, Strings.toJavaString(this.varName))) {
            if (this.isWrite()) {
                Object value2 = this.getRhs().execute(frame);
                ((WritePropertyNode)this.scopeAccessNode).executeWithValue(dynamicScope, value2);
                return value2;
            }
            return this.scopeAccessNode.executeWithTarget(frame, dynamicScope);
        }
        return this.defaultDelegate.execute(frame);
    }

    @Override
    public void executeWrite(VirtualFrame frame, Object value2) {
        throw Errors.shouldNotReachHere();
    }

    @Override
    public JavaScriptNode getRhs() {
        return ((WriteNode)((Object)JSNodeUtil.getWrappedNode(this.defaultDelegate))).getRhs();
    }

    @Override
    protected JavaScriptNode copyUninitialized(Set<Class<? extends Tag>> materializedTags) {
        return new DebugScopeVarWrapperNode(this.varName, DebugScopeVarWrapperNode.cloneUninitialized(this.defaultDelegate, materializedTags), DebugScopeVarWrapperNode.cloneUninitialized(this.dynamicScopeNode, materializedTags), DebugScopeVarWrapperNode.cloneUninitialized(this.scopeAccessNode, materializedTags));
    }
}

